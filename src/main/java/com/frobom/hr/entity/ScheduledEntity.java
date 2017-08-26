package com.frobom.hr.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class ScheduledEntity extends BaseEntity {

    @Column(name = "scheduled_startdate")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date scheduledStartDate;

    @Column(name = "scheduled_enddate")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date scheduledFinishedDate;

    @Column(name = "actual_startdate")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date actualStartDate;

    @Column(name = "actual_enddate")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date actualFinishedDate;

    public Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public Date getScheduledFinishedDate() {
        return scheduledFinishedDate;
    }

    public void setScheduledFinishedDate(Date scheduledFinishedDate) {
        this.scheduledFinishedDate = scheduledFinishedDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualFinishedDate() {
        return actualFinishedDate;
    }

    public void setActualFinishedDate(Date actualFinishedDate) {
        this.actualFinishedDate = actualFinishedDate;
    }
}
