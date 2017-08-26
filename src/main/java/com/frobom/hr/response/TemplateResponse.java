package com.frobom.hr.response;

import java.util.List;

import com.frobom.hr.dto.TemplateCalendarDto;

public class TemplateResponse {

    private boolean isError;

    private List<TemplateCalendarDto> templateCalendarDtos;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public List<TemplateCalendarDto> getTemplateCalendarDtos() {
        return templateCalendarDtos;
    }

    public void setTemplateCalendarDtos(List<TemplateCalendarDto> templateCalendarDtos) {
        this.templateCalendarDtos = templateCalendarDtos;
    }
}
