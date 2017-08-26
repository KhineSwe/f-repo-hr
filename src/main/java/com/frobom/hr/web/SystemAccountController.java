package com.frobom.hr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.frobom.hr.entity.SystemAccount;
import com.frobom.hr.form.ChangePasswordForm;
import com.frobom.hr.service.SystemAccountService;

@Controller
public class SystemAccountController {

    @Autowired
    private SystemAccountService systemAccountService;

    public void setSystemAccountService(SystemAccountService systemAccountService) {
		this.systemAccountService = systemAccountService;
	}

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String adminLoginPage(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		model.addAttribute("systemAccount", new SystemAccount());
		if (error != null) {
			model.addAttribute("loginError", "Invalid user name or password.");
		}
		if (logout != null) {
			model.addAttribute("logout", "You have been logged out.");
		}
		return "adminLogin";
	}

    @RequestMapping(value = "/admin/change_password", method = RequestMethod.GET)
    public String showChangePasswordForm(Model model) {
		model.addAttribute("changepasswordform", new ChangePasswordForm());
		return "changePasswordForm";
	}

    @RequestMapping(value = "/admin/change_password", method = RequestMethod.POST)
    public String changePassword(@Validated @ModelAttribute("changepasswordform") ChangePasswordForm changepasswordform,
			BindingResult result, Model model) {

		SystemAccount systemAccount = systemAccountService.findById(1);
		if (result.hasErrors()) {
			model.addAttribute("error", "Input must be 4 to 8 characters!");
		} else if (!(changepasswordform.getNewPassword().equals(changepasswordform.getConfirmPassword()))) {
			model.addAttribute("error", "Passwords do not match!");
		} else if (systemAccount != null) {
			if (systemAccount.getLoginPassword().equals(changepasswordform.getLoginPassword())) {
				systemAccount.setLoginPassword(changepasswordform.getNewPassword());
				systemAccountService.save(systemAccount);
				return "redirect:/admin";
			} else {
				model.addAttribute("error", "Incorrect old password!");
			}
		}
		return "changePasswordForm";
	}

    @RequestMapping(value = "/adminLogout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/admin/login?logout";
	}

}
