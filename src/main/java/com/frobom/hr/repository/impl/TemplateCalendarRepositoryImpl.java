package com.frobom.hr.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frobom.hr.dto.TemplateCalendarDto;
import com.frobom.hr.entity.SystemList;
import com.frobom.hr.entity.TemplateCalendar;
import com.frobom.hr.repository.TemplateCalendarRepository;

@Repository
public class TemplateCalendarRepositoryImpl implements TemplateCalendarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(TemplateCalendar templateCalendar) {

        if (templateCalendar.getId() == null) {
            this.entityManager.persist(templateCalendar);
        } else {
            this.entityManager.merge(templateCalendar);
        }
    }

    @Override
    public TemplateCalendar convertDtoToEntity(TemplateCalendarDto templateCalendarDto) {
        return modelMapper.map(templateCalendarDto, TemplateCalendar.class);
    }

    @Override
    public void delete(TemplateCalendar templateCalendar) {
        Query query = entityManager.createQuery("update TemplateCalendar t set  t.deleted =:deleted where t.id=:id");
        query.setParameter("id", templateCalendar.getId());
        query.setParameter("deleted", "1");
        query.executeUpdate();
    }

    @Override
    public List<TemplateCalendar> findAll() {
        Query query = entityManager.createQuery("SELECT c FROM TemplateCalendar c where c.deleted='0' GROUP BY c.calendarName ORDER BY c.calendarId ASC", TemplateCalendar.class);
        return query.getResultList();
    }

    @Override
    public List<TemplateCalendar> findByCalendarId(int id) {

        return null;
    }

    @Override
    public TemplateCalendar findById(int id) {
        return entityManager.find(TemplateCalendar.class, id);
    }

    @Override
    public List<TemplateCalendar> findAllTemplates() {
        Query query = entityManager.createQuery("SELECT c FROM TemplateCalendar c", TemplateCalendar.class);
        return query.getResultList();
    }

    @Override
    public TemplateCalendar findByCalendarIdAndEventIdAndYear(long calendarId, long eventId, String year) {
        Query query = entityManager.createQuery("select c from TemplateCalendar c where c.calendarId = :calendarId AND c.eventId=:eventId AND c.year=:year", TemplateCalendar.class);
        query.setParameter("calendarId", calendarId); 
        query.setParameter("eventId", eventId); 
        query.setParameter("year", year);
        TemplateCalendar templateCalendar = null;
        try {
            templateCalendar = (TemplateCalendar) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return templateCalendar;
    }

    @Override
    public List<TemplateCalendar> find(long calendar_id) {
        Query query = entityManager.createQuery("SELECT t FROM TemplateCalendar t " + "WHERE t.calendarId = ? AND t.deleted = ?", TemplateCalendar.class);
        query.setParameter(1, calendar_id);
        query.setParameter(2, "0");
        return query.getResultList();
    }

    @Override
    public TemplateCalendar findCalendarId(long id) {
        Query query = entityManager.createQuery("SELECT c FROM TemplateCalendar c where c.deleted='0' and c.calendarId=? GROUP BY c.calendarId ORDER BY c.calendarId ASC",
                TemplateCalendar.class);
        query.setParameter(1, id);
        return (TemplateCalendar) query.getSingleResult();
    }

    @Override
    public TemplateCalendar findEventId(long id) {
        Query query = entityManager.createQuery("SELECT t FROM TemplateCalendar t " + "WHERE t.eventId = ?", TemplateCalendar.class);
        query.setParameter(1, id);
        return (TemplateCalendar) query.getSingleResult();
    }

    @Override
    public List<TemplateCalendar> find(long calendarId, String year) {
        Query query = entityManager.createQuery("SELECT t FROM TemplateCalendar t " + "WHERE t.calendarId = ? AND t.year = ? AND t.deleted = ?", TemplateCalendar.class);
        query.setParameter(1, calendarId);
        query.setParameter(2, year);
        query.setParameter(3, "0");
        return query.getResultList();
    }

    @Override
    public List<TemplateCalendar> findByCalendarId(long id) {
        Query query = entityManager.createQuery("SELECT t FROM TemplateCalendar t WHERE t.calendarId=?", TemplateCalendar.class);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public TemplateCalendar findUniqueEventTitle(int id, Date date, String year) {
        Query query = this.entityManager.createQuery("SELECT t from TemplateCalendar t WHERE t.id != ? AND t.date = ? AND t.year = ? AND t.deleted = ?", TemplateCalendar.class);
        query.setParameter(1, id);
        query.setParameter(2, date);
        query.setParameter(3, year);
        query.setParameter(4, "0");
        TemplateCalendar templateCalendarResult = null;
        try {
            templateCalendarResult = (TemplateCalendar) query.getSingleResult();
        } catch (Exception e) {

        }
        return templateCalendarResult;
    }
}
