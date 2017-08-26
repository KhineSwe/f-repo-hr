package com.frobom.hr.form;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordResetEmailForm {

    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
