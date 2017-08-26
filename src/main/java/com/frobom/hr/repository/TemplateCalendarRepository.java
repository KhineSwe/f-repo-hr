package com.frobom.hr.repository;

import java.util.Date;
import java.util.List;

import com.frobom.hr.dto.TemplateCalendarDto;
import com.frobom.hr.entity.TemplateCalendar;

public interface TemplateCalendarRepository {

    void save(TemplateCalendar templateCalendar);

    void delete(TemplateCalendar templateCalendar);

    List<TemplateCalendar> findAll();

    List<TemplateCalendar> findByCalendarId(int id);

    TemplateCalendar findById(int id);

    List<TemplateCalendar> findAllTemplates();

    TemplateCalendar findByCalendarIdAndEventIdAndYear(long calendarId, long eventId, String year);

    public List<TemplateCalendar> find(long calendar_id);

    public TemplateCalendar findCalendarId(long id);

    public TemplateCalendar findEventId(long id);

    public List<TemplateCalendar> find(long calendarId, String year);

    List<TemplateCalendar> findByCalendarId(long id);

    public TemplateCalendar findUniqueEventTitle(int id, Date date, String year);

    TemplateCalendar convertDtoToEntity(TemplateCalendarDto templateCalendarDto);
}
