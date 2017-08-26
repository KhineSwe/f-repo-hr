package com.frobom.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Template_Calendar_Sync")
public class TemplateCalendarSync extends BaseEntity{
    @NotEmpty
    @Column(name = "system_id")
    public long systemId;
    
    @NotEmpty
    @Column(name = "calendar_id")
    public long calendarId;

    public long getSystemId() {
        return systemId;
    }

    public void setSystemId(long systemId) {
        this.systemId = systemId;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }
}