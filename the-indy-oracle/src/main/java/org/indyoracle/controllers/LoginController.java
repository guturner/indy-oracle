package org.indyoracle.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.indyoracle.logging.LoggingHelper.log;

import org.indyoracle.beans.Field;
import org.indyoracle.beans.StormpathLoginBean;
import org.indyoracle.security.AccountManager;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequests;

/**
 * Controller for the 'Login' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class LoginController {

	private void addVisibleFields(Model model) {
		ArrayList<Field> visibleFields = new ArrayList<Field>() {{
    		add(new Field("Email", "email", "text", "Email", "true"));
    		add(new Field("Password", "password", "password", "Password", "true"));
    	}};
    	
    	model.addAttribute("visibleFields", visibleFields);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLogin(HttpServletRequest request, Model model) {
		addVisibleFields(model);
    	model.addAttribute("stormpathLoginBean", new StormpathLoginBean());
		
		return "login";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String viewLogout(HttpServletRequest request, Model model) {
		Account a = UserManager.getCurrentUser(request.getSession());
		log(a.getEmail() + " is logging out.");
		
		if (a != null) {
			request.getSession().removeAttribute("account");
		}
		
		return "redirect:/?skip=y";
    }
	
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String viewAuth(HttpServletRequest request, Model model) {
    	addVisibleFields(model);
    	model.addAttribute("stormpathLoginBean", new StormpathLoginBean());
    	
    	return "login";
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String setLogin(@Valid @ModelAttribute StormpathLoginBean stormpathLoginBean, BindingResult result, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttr, Model model) {
    	// Form had validation errors:
    	if (result.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for (ObjectError e : result.getAllErrors()) {
    			errors.add(e.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "login";
    	}
    	
    	Application app = AccountManager.getApplication();
    	AuthenticationRequest authRequest = UsernamePasswordRequests.builder().setUsernameOrEmail(stormpathLoginBean.getEmail()).setPassword(stormpathLoginBean.getPassword()).build();

    	Account a = null;
    	try {
    		AuthenticationResult authResult = app.authenticateAccount(authRequest);
    		a = authResult.getAccount();
    	} catch (Exception e) {
    		System.err.println(e);
    	}
    	
    	if (a == null) {
    		ArrayList<String> errors = new ArrayList<String>() {{ add("Invalid credentials."); }};
    		model.addAttribute("errors", errors);
    		addVisibleFields(model);
    		return "login";
    	}
    	
    	redirectAttr.addFlashAttribute("user", a);
    	session.setAttribute("account", a);
    	
    	log(a.getEmail() + " is logging in.");
    	
    	return "redirect:/?skip=y";
    }
}
