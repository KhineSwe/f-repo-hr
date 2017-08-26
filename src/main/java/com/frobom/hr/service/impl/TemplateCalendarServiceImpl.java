package com.frobom.hr.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.hr.dto.TemplateCalendarDto;
import com.frobom.hr.entity.TemplateCalendar;
import com.frobom.hr.repository.TemplateCalendarRepository;
import com.frobom.hr.service.TemplateCalendarService;

@Service
public class TemplateCalendarServiceImpl implements TemplateCalendarService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TemplateCalendarRepository templateCalendarRepository;

    @Override
    @Transactional
    public void save(TemplateCalendar templateCalendar) {
        this.templateCalendarRepository.save(templateCalendar);
    }

    @Override
    @Transactional
    public void delete(TemplateCalendar templateCalendar) {
        this.templateCalendarRepository.delete(templateCalendar);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateCalendar> findAll() {
        return this.templateCalendarRepository.findAll();
    }

    @Override
    @Transactional
    public List<TemplateCalendar> findByCalendarId(int id) {
        return this.templateCalendarRepository.findByCalendarId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateCalendar findById(int id) {
        return this.templateCalendarRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateCalendar> findAllTemplates() {
        return this.templateCalendarRepository.findAllTemplates();
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateCalendar findByCalendarIdAndEventIdAndYear(long calendarId, long eventId, String year) {
        return this.templateCalendarRepository.findByCalendarIdAndEventIdAndYear(calendarId, eventId, year);
    }

    @Override
    public List<TemplateCalendar> find(long calendar_id) {
        return this.templateCalendarRepository.find(calendar_id);
    }

    @Override
    public TemplateCalendar findCalendarId(long id) {
        return this.templateCalendarRepository.findCalendarId(id);
    }

    @Override
    public TemplateCalendar findEventId(long id) {
        return this.templateCalendarRepository.findEventId(id);
    }

    @Override
    public List<TemplateCalendar> find(long calendarId, String year) {
        return this.templateCalendarRepository.find(calendarId, year);
    }

    @Override
    public TemplateCalendarDto convertDto(TemplateCalendar templateCalendar) {
        return modelMapper.map(templateCalendar, TemplateCalendarDto.class);
    }

    @Override
    public TemplateCalendar convertDtoToEntity(TemplateCalendarDto templateCalendarDto) {
        return modelMapper.map(templateCalendarDto, TemplateCalendar.class);
    }

    @Override
    public List<TemplateCalendar> findByCalendarId(long id) {
        return this.templateCalendarRepository.findByCalendarId(id);
    }

    @Override
    public TemplateCalendar findUniqueEventTitle(int id, Date date, String year) {
        return this.templateCalendarRepository.findUniqueEventTitle(id, date, year);
    }
}
