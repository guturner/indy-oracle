package org.indyoracle.controllers;

import static org.indyoracle.logging.LoggingHelper.log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.indyoracle.annotations.Service;
import org.indyoracle.beans.Field;
import org.indyoracle.beans.StormpathRegisterBean;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

/**
 * Controller for the 'Register' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class RegisterController {

	private void addVisibleFields(Model model) {
		ArrayList<Field> visibleFields = new ArrayList<Field>() {{
    		add(new Field("First Name", "givenName", "text", "First Name", "true"));
    		add(new Field("Last Name", "surname", "text", "Last Name", "true"));
    		add(new Field("Email", "email", "text", "Email", "true"));
    		add(new Field("Password", "password", "password", "Password", "true"));
    		add(new Field("Confirm Password", "confirmPassword", "password", "Confirm Password", "true"));
    		add(new Field("Approver's Email", "approverEmail", "text", "Approver Email", "true"));
    		add(new Field("Approver's Unique ID", "approverId", "text", "Approver ID", "true"));
    	}};
    	
    	model.addAttribute("visibleFields", visibleFields);
	}
	
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(HttpServletRequest request, Model model) {
    	addVisibleFields(model);
    	model.addAttribute("stormpathRegisterBean", new StormpathRegisterBean());
    	return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String setRegister(@Valid @ModelAttribute StormpathRegisterBean stormpathRegisterBean, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttr, Model model) {
    	// Form had validation errors:
    	if (result.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for (ObjectError e : result.getAllErrors()) {
    			errors.add(e.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	if (UserManager.isEmailInUse(request, stormpathRegisterBean.getEmail())) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("That email is already in use."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	if (!stormpathRegisterBean.getPassword().equals(stormpathRegisterBean.getConfirmPassword())) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("Passwords don't match."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	Pattern p = Pattern.compile("^(?=.*[AZ])(?=.*\\d).{8,100}$");
    	Matcher m = p.matcher(stormpathRegisterBean.getPassword());
    	m.find();
    	if (!m.matches()) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("Password doesn't meet requirements (8+ chars, 1 capital, 1 number)."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	Account approverAccount = UserManager.getAccountByEmail(request, stormpathRegisterBean.getApproverEmail());
    	if (approverAccount == null) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("No approver with that email."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	CustomData approverCustomData = approverAccount.getCustomData();
    	if ( approverCustomData.get("uniqueId") == null ||
    		 !approverCustomData.get("uniqueId").equals(stormpathRegisterBean.getApproverId())) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("Approver ID is incorrect."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "register";
    	}
    	
    	Account a = UserManager.createNewAccount(stormpathRegisterBean.getGivenName(),
    			                     stormpathRegisterBean.getSurname(), 
    			                     stormpathRegisterBean.getEmail(),
    			                     stormpathRegisterBean.getPassword(), 
    			                     stormpathRegisterBean.getApproverEmail(), 
    			                     stormpathRegisterBean.getApproverId());
    	
    	if (a != null) {
    		redirectAttr.addFlashAttribute("newUser", true);
    		log(a.getEmail() + " has successfully registered.");
    	} else {
    		redirectAttr.addFlashAttribute("newUserFailed", true);
    	}
    	
    	return "redirect:/?skip=y";
    }
}
