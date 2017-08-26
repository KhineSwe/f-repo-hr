package com.frobom.hr.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.frobom.hr.dto.TemplateCalendarDto;
import com.frobom.hr.entity.SystemList;
import com.frobom.hr.entity.TemplateCalendar;
import com.frobom.hr.service.TemplateCalendarService;

@Component("templateCalendarFormValidator")
public class TemplaeCalendarFormValidator {

    @Autowired
    private TemplateCalendarService templateCalendarService;

    public boolean supports(Class<?> clazz) {
        return TemplateCalendarDto.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        System.out.println("Enter Validate Edit Method:");
        TemplateCalendarDto templateCalendar = (TemplateCalendarDto) target;
        int id = Integer.parseInt(templateCalendar.getId());
        String date = templateCalendar.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateformat = null;
        Calendar cal = Calendar.getInstance();
        Date editDate = cal.getTime();
        try {
            dateformat = formatter.parse(date);
            System.out.println("Event datechange is:" + dateformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (templateCalendarService.findUniqueEventTitle(id,dateformat,templateCalendar.getYear()) != null) {
            errors.rejectValue("eventTitle", "eventTitle.alreadyExists", "Event Title already taken.");
        }
    }
}
