package com.frobom.hr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Template_Calendar")
public class TemplateCalendar extends BaseEntity {

	@Column(name = "calendar_id")
	private long calendarId;

	@Column(name = "calendar_name")
	@Size(max = 50, message = "Max 50 characters.")
	private String calendarName;

	@Column(name = "event_id")
	private long eventId;

	@Column(name = "event_title")
	@Size(max = 50, message = "Max 50 characters.")
	private String eventTitle;

	@Column(name = "year")
	private String year;

	@Column(name = "date")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date date;

	@Column(name = "created")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date created;

	@Column(name = "modified")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date modified;

	@Column(name = "deleted")
	private String deleted;

	@Column(name = "modified_flag")
	private String modifiedFlag;

	public long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(long calendarId) {
		this.calendarId = calendarId;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getModifiedFlag() {
		return modifiedFlag;
	}

	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}
}
