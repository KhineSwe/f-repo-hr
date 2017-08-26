package com.frobom.hr.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.frobom.hr.entity.LoginUserAccount;
import com.frobom.hr.entity.SystemList;
import com.frobom.hr.entity.TemplateCalendar;
import com.frobom.hr.form.TemplateCalendarForm;
import com.frobom.hr.service.LoginUserAccountService;
import com.frobom.hr.service.SystemListService;
import com.frobom.hr.service.TemplateCalendarService;
import com.frobom.hr.validator.SystemListFormValidator;
import com.google.common.io.Files;

@Controller
public class SystemListController {

    @Autowired
    private SystemListService systemListService;

    @Autowired
    private LoginUserAccountService loginUserAccountService;

    @Autowired
    private TemplateCalendarService templateCalendarService;

    @Autowired
    @Qualifier("systemListFormValidator")
    private SystemListFormValidator systemListFormValidator;

    public void setSystemListService(SystemListService systemListService) {
        this.systemListService = systemListService;
    }

    public void setTemplateCalendarService(TemplateCalendarService templateCalendarService) {
        this.templateCalendarService = templateCalendarService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminTopPage(Model model, HttpServletRequest request) throws ParseException {
        List<SystemList> systems = systemListService.findAll();
        List<TemplateCalendar> templateCalendars = templateCalendarService.findAll();
        for (SystemList system : systems) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String createdDate = dateFormat.format(system.getCreated());
            system.setCreatedDate(createdDate);
        }
        model.addAttribute("systems", systems);
        model.addAttribute("templateCalendars", templateCalendars);
        return "adminTopPage";
    }

    @RequestMapping(value = "/admin/systems/new", method = RequestMethod.GET)
    public String showAddSystemForm(Model model, HttpServletRequest request) {

        model.addAttribute("systemList", new SystemList());
        return "addSystemForm";
    }

    @RequestMapping(value = "/admin/systems/new", method = RequestMethod.POST)
    public String addSystem(@ModelAttribute("systemList") @Valid SystemList systemList, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Invalid Input! Please try again.");
            return "addSystemForm";
        }

        systemListFormValidator.validate(systemList, result);
        if (result.hasErrors()) {
            model.addAttribute("error", "Exist Input! Please try again.");
            return "addSystemForm";
        }

        LoginUserAccount user = new LoginUserAccount();
        user.setUserId("admin");
        user.setUserPwd("froboadmin");
        user.setSystemList(systemList);
        user.setDeleted("0");
        loginUserAccountService.save(user);

        Calendar cal = Calendar.getInstance();
        systemList.setCreated(cal.getTime());
        systemList.setDeleted("0");

        systemListService.save(systemList);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/systems/edit/{id}", method = RequestMethod.GET)
    public String showEditSystemForm(@PathVariable("id") int systemId, Model model) {
        SystemList currentSystem = systemListService.findById(systemId);
        model.addAttribute("systemList", currentSystem);
        model.addAttribute("systemId" + systemId);
        return "editSystemForm";
    }

    @RequestMapping(value = "/admin/systems/edit/{id}", method = RequestMethod.POST)
    public String editSystem(@Validated @ModelAttribute("systemList") SystemList currentSystem, BindingResult result, @PathVariable("id") int id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Invalid Input! Please try again.");
            model.addAttribute("systemList" + currentSystem);
            return "editSystemForm";
        }
        systemListFormValidator.validateForEdit(currentSystem, result);
        if (result.hasErrors()) {
            model.addAttribute("error", "Existed Input! Please try again.");
            model.addAttribute("systemList" + currentSystem);
            return "editSystemForm";
        }

        Calendar cal = Calendar.getInstance();
        Date updatedDate = cal.getTime();
        SystemList current = systemListService.findById(id);
        current.setUpdated(updatedDate);
        current.setCompanyName(currentSystem.getCompanyName());
        current.setSystemId(currentSystem.getSystemId());

        systemListService.save(current);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/systems/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int id = Integer.parseInt(request.getParameter("id"));

        SystemList system = systemListService.findById(id);
        systemListService.delete(system);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/calendar_template/new", method = RequestMethod.GET)
    public String showAddCalendarTemplateForm(Model model) {
        model.addAttribute("templateCalendarForm", new TemplateCalendarForm());
        return "addCalendarTemplate";
    }

    @RequestMapping(value = "/admin/calendar_template/new", method = RequestMethod.POST)
    public String addCalendarTemplate(@Validated @ModelAttribute TemplateCalendarForm templateCalendarForm, BindingResult result, HttpServletRequest request,
            final @RequestParam("file") MultipartFile file, Model model) throws ParseException, IOException {
        if (result.hasErrors()) {
            model.addAttribute("calendarNameMaxError", "Invalid Input! Please try again.");
            return "addCalendarTemplate";
        }
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        File directory = new File(rootPath + File.separator + "uploadedfile");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File fileName = new File(directory.getAbsolutePath() + File.separator + file.getOriginalFilename());
        String fileExtension = Files.getFileExtension(file.getOriginalFilename());
        if (fileExtension.equals("csv")) {
            try {
                try (InputStream is = file.getInputStream(); BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileName))) {
                    int i;
                    while ((i = is.read()) != -1) {
                        stream.write(i);
                    }
                    stream.flush();
                }
            } catch (IOException e) {
            }
            try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE)) {
                TemplateCalendar templateCalendar = new TemplateCalendar();
                String[] beanReaderHeader = beanReader.getHeader(true);
                final String[] headers = new String[] { "calendarId", "date", "eventId", "eventTitle", "year" };
                final CellProcessor[] processors = new CellProcessor[] { new NotNull(new ParseLong()), // calendarId
                        new NotNull(new ParseDate("MM/dd/yyyy")), // date
                        new NotNull(new ParseLong()), // eventId
                        new NotNull(), // eventTitle
                        new NotNull() // year
                };
                TemplateCalendar csvTemplateCalendarData;
                if (beanReaderHeader.length == headers.length) {
                    while ((csvTemplateCalendarData = beanReader.read(TemplateCalendar.class, headers, processors)) != null) {
                        Date createdDate = new Date();
                        templateCalendar.setId(null);
                        templateCalendar.setCalendarId(csvTemplateCalendarData.getCalendarId());
                        templateCalendar.setCalendarName(templateCalendarForm.getCalendarName());
                        templateCalendar.setEventId(csvTemplateCalendarData.getEventId());
                        templateCalendar.setEventTitle(csvTemplateCalendarData.getEventTitle());
                        templateCalendar.setYear(csvTemplateCalendarData.getYear());
                        templateCalendar.setDate(csvTemplateCalendarData.getDate());
                        templateCalendar.setCreated(createdDate);
                        templateCalendar.setModified(null);
                        templateCalendar.setDeleted("0");
                        templateCalendar.setModifiedFlag("0");

                        TemplateCalendar templateCalendarResult = templateCalendarService.findByCalendarIdAndEventIdAndYear(csvTemplateCalendarData.getCalendarId(),
                                csvTemplateCalendarData.getEventId(), csvTemplateCalendarData.getYear());
                        if (templateCalendarResult == null) {
                            templateCalendarService.save(templateCalendar);
                        } else {
                            model.addAttribute("templateAddError", "Same Data and Cannot Add.");
                            return "addCalendarTemplate";
                        }
                    }

                } else {
                    model.addAttribute("templateCalendarCSVError", "Invalid file path. Please try again");
                    return "addCalendarTemplate";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("templateCalendarCSVError", "Invalid file path. Please try again");
            return "addCalendarTemplate";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/calendar_template/download", method = RequestMethod.GET)
    public void exportOrganizationMembersAsCSV(Model model, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        List<TemplateCalendar> templateCalendars = templateCalendarService.findAllTemplates();
        response.setHeader("Content-Disposition", "attachment; filename=\"Template_Calendar_CSV.csv\"");
        final String[] header = { "id", "calendarId", "calendarName", "created", "date", "deleted", "eventId", "eventTitle", "modified", "modifiedFlag", "year" };
        final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // id
                new NotNull(new ParseLong()), // calendarId
                new NotNull(), // calendarName
                new FmtDate("MM/dd/yyyy"), // created
                new FmtDate("MM/dd/yyyy"), // date
                new Optional(), // deleted
                new NotNull(new ParseLong()), // eventId
                new NotNull(), // eventTitle
                new Optional(), // modified
                new NotNull(), // modified
                new NotNull() // year
        };
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        csvWriter.writeHeader(header);
        for (TemplateCalendar templateCalendar : templateCalendars) {
            csvWriter.write(templateCalendar, header, processors);
        }
        csvWriter.close();
    }
}
