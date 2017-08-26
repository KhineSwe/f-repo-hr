package com.frobom.hr.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.frobom.hr.dto.TemplateCalendarDto;
import com.frobom.hr.entity.TemplateCalendar;
import com.frobom.hr.response.TemplateResponse;
import com.frobom.hr.service.TemplateCalendarService;
import com.frobom.hr.validator.TemplaeCalendarFormValidator;

@Controller
public class TemplateCalendarController {

    @Autowired
    private TemplateCalendarService templateCalendarService;

    @Autowired
    @Qualifier("templateCalendarFormValidator")
    private TemplaeCalendarFormValidator templateCalendarFormValidator;

    public void setTemplateCalendarService(TemplateCalendarService templateCalendarService) {
        this.templateCalendarService = templateCalendarService;
    }

    @RequestMapping(value = "/admin/calendar_template/view/{calendarId}", method = RequestMethod.GET)
    public String showCalendarTemplate(Model model, @PathVariable("calendarId") long calendarId) {
        model.addAttribute("calendarId", calendarId);
        String year = "2017";
        model.addAttribute("currentyear", year);
        System.out.println("Enter show calendar template:");
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(calendarId, year);
        model.addAttribute("calendarLists", caldendarLists);
        model.addAttribute("templateCalendar", new TemplateCalendar());
        return "showCalendarTemplate";
    }

    @RequestMapping(value = "/admin/calendar_template/{calendarId}/forwardyear/{forwardyear}/events", method = RequestMethod.GET)
    @ResponseBody
    public List<TemplateCalendarDto> getEventsForwardYears(Model model, @PathVariable("calendarId") long calendarId, @PathVariable("forwardyear") String forwardyear) {
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(calendarId, forwardyear);
        List<TemplateCalendarDto> calendarDtos = caldendarLists.stream().map(caldendarList -> templateCalendarService.convertDto(caldendarList)).collect(Collectors.toList());
        return calendarDtos;
    }

    @RequestMapping(value = "/admin/calendar_template/{calendarId}/backwardyear/{backwardyear}/events", method = RequestMethod.GET)
    @ResponseBody
    public List<TemplateCalendarDto> getEventsBackwardYears(Model model, @PathVariable("calendarId") long calendarId, @PathVariable("backwardyear") String backwardyear) {
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(calendarId, backwardyear);
        List<TemplateCalendarDto> calendarDtos = caldendarLists.stream().map(caldendarList -> templateCalendarService.convertDto(caldendarList)).collect(Collectors.toList());
        return calendarDtos;
    }

    @RequestMapping(value = "/admin/calendar_template/{calendarId}/year/{year}/events", method = RequestMethod.GET)
    @ResponseBody
    public List<TemplateCalendarDto> getEvents(Model model, @PathVariable("calendarId") long calendarId, @PathVariable("year") String year) {
        System.out.println("Enter get events method::::::::::::");
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(calendarId, year);
        List<TemplateCalendarDto> calendarDtos = caldendarLists.stream().map(caldendarList -> templateCalendarService.convertDto(caldendarList)).collect(Collectors.toList());
        return calendarDtos;
    }

    @RequestMapping(value = "/admin/calendar_template/new_event/{calendarId}", method = RequestMethod.GET)
    public String showAddEventForm(Model model, @PathVariable("calendarId") long calendarId) {
        TemplateCalendar templateCalendar = new TemplateCalendar();
        templateCalendar.setCalendarId(calendarId);
        model.addAttribute("templateCalendar", templateCalendar);
        return "addEventForm";
    }

    @RequestMapping(value = "/admin/calendar_template/new_event", method = RequestMethod.POST)
    public String addEvent(@Validated @ModelAttribute TemplateCalendar templateCalendar, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titleError", "Invalid Input! Please try again.");
            return "addEventForm";
        }
        if (templateCalendar.getDate() == null) {
            model.addAttribute("blank", "Please Choose Date");
            return "addEventForm";
        }
        TemplateCalendar calendarName = templateCalendarService.findCalendarId(templateCalendar.getCalendarId());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datestring = dateFormat.format(templateCalendar.getDate());
        String date[] = datestring.split("/");
        List<TemplateCalendar> templateCalendars = templateCalendarService.findByCalendarId(templateCalendar.getCalendarId());
        int size = templateCalendars.size();
        int eventSize = size + 1;
        Date dateTime = new Date();
        TemplateCalendar templateCalendarObject = new TemplateCalendar();
        templateCalendarObject.setCalendarId(templateCalendar.getCalendarId());
        templateCalendarObject.setCalendarName(calendarName.getCalendarName());
        templateCalendarObject.setEventId(eventSize);
        templateCalendarObject.setEventTitle(templateCalendar.getEventTitle());
        templateCalendarObject.setDate(templateCalendar.getDate());
        templateCalendarObject.setCreated(dateTime);
        templateCalendarObject.setDeleted("0");
        templateCalendarObject.setModifiedFlag("0");
        templateCalendarObject.setYear(date[2]);
        templateCalendarService.save(templateCalendarObject);
        return "redirect:/admin/calendar_template/view/" + templateCalendar.getCalendarId();
    }

    @RequestMapping(value = "/admin/calendar_template/edit", method = RequestMethod.POST)
    @ResponseBody
    public List<TemplateCalendarDto> editEvent(Model model, HttpServletRequest request, @Validated @RequestBody TemplateCalendarDto calendarDto, BindingResult result) {
        String id = calendarDto.getId();
        int templateId = Integer.parseInt(id);
        String date = calendarDto.getDate();
        String dateList[] = date.split("/");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateformat = null;
        Calendar cal = Calendar.getInstance();
        Date editDate = cal.getTime();
        try {
            dateformat = formatter.parse(date);
            System.out.println("Event datechange is:" + dateformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TemplateCalendar templateCalendar = templateCalendarService.findById(templateId);
        templateCalendar.setEventTitle(calendarDto.getEventTitle());
        templateCalendar.setDate(dateformat);
        templateCalendar.setModified(editDate);
        templateCalendar.setModifiedFlag("1");
        templateCalendar.setYear(dateList[2]);
        templateCalendarService.save(templateCalendar);
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(calendarDto.getCalendarId(), calendarDto.getYear());
        List<TemplateCalendarDto> calendarDtos = caldendarLists.stream().map(caldendarList -> templateCalendarService.convertDto(caldendarList)).collect(Collectors.toList());
        return calendarDtos;
    }

    @RequestMapping(value = "/admin/calendar_template/delete", method = RequestMethod.POST)
    @ResponseBody
    public List<TemplateCalendarDto> delete(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody TemplateCalendarDto calendarDto) throws Exception {
        String id = calendarDto.getId();
        int templateId = Integer.parseInt(id);
        TemplateCalendar template = templateCalendarService.findById(templateId);
        template.setDeleted("1");
        templateCalendarService.delete(template);
        List<TemplateCalendar> caldendarLists = templateCalendarService.find(1, calendarDto.getYear());
        List<TemplateCalendarDto> calendarDtos = caldendarLists.stream().map(caldendarList -> templateCalendarService.convertDto(caldendarList)).collect(Collectors.toList());
        return calendarDtos;
    }
}
