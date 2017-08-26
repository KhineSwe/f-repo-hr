package com.frobom.hr.form;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class TemplateCalendarForm {

	@Size(max = 50, message = "Max 50 characters.")
	private String calendarName;

	@Transient
	private MultipartFile file;

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
