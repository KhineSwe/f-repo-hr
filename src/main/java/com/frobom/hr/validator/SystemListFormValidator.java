package com.frobom.hr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.frobom.hr.entity.SystemList;
import com.frobom.hr.service.SystemListService;

@Component("systemListFormValidator")
public class SystemListFormValidator {

    @Autowired
    private SystemListService systemListService;

    public boolean supports(Class<?> clazz) {
        return SystemList.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        SystemList system = (SystemList) target;
        if (systemListService.findBySystemId(system.getSystemId()) != null) {
            errors.rejectValue("systemId", "systemId.alreadyExists", "SystemId already taken.");
        }
    }

    public void validateForEdit(Object target, Errors errors) {
        SystemList system = (SystemList) target;
        int id = system.getId();
        String systemId = system.getSystemId();
        if (systemListService.findExistedBySystemId(id, systemId) != null) {
            errors.rejectValue("systemId", "systemId.alreadyExists", "SystemId already taken.");
        }

    }

}
