package org.indyoracle.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.indyoracle.annotations.Service;
import org.indyoracle.beans.UserBean;
import org.indyoracle.beans.ValidateResult;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

/**
 * Controller for the 'User Profile' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class UserInfoController {

	// Options for the 'Phone Carrier' dropdown:
	private final ArrayList<String> options = new ArrayList<String>() {{
		add("AT&T");
		add("Boost Mobile");
		add("Sprint");
		add("T-Mobile");
		add("Verizon");
		add("Virgin Mobile");
	}};
	
	/**
	 * Builds a populated UserBean object for use in 'User Profile' forms.
	 * 
	 * @param request
	 * @return the populated UserBean object.
	 */
	private UserBean buildUserBean(HttpServletRequest request) {
		Account account = UserManager.getCurrentUser(request.getSession());
    	
    	UserBean userBean = new UserBean();
    	userBean.setFirstName(account.getGivenName());
    	userBean.setLastName(account.getSurname());
    	String phoneNumber = "";
    	if (account.getCustomData().get("phoneNumber") != null) {
    		phoneNumber = account.getCustomData().get("phoneNumber").toString();
    	}
    	userBean.setPhoneNumber(phoneNumber);
    	String phoneCarrier = "";
    	if (account.getCustomData().get("phoneCarrier") != null) {
    		phoneCarrier = account.getCustomData().get("phoneCarrier").toString();
    	}
    	userBean.setPhoneCarrier(phoneCarrier);
    	String uniqueId = "";
    	if (account.getCustomData().get("uniqueId") != null) {
    		uniqueId = account.getCustomData().get("uniqueId").toString();
    	}
    	userBean.setUniqueId(uniqueId);
    	
    	return userBean;
	}
	
	private void setModelAttributes(HttpServletRequest request, Model model) {
		UserBean bean = buildUserBean(request);
		model.addAttribute("userBean", bean);
		model.addAttribute("options", options);
	}
	
    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String userView(HttpServletRequest request, Model model) {
    	setModelAttributes(request, model);
    	return "user_profile";
    }
    
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String userSet(@Valid @ModelAttribute UserBean userBean, BindingResult result, HttpServletRequest request, Model model) {
    	// Form had validation errors:
    	if (result.hasErrors()) {
    		model.addAttribute("errors", result.getAllErrors());
    		setModelAttributes(request, model);
    		return "user_profile";
    	}
    	
    	Account account = UserManager.getCurrentUser(request.getSession());
    	UserManager.updateUser(account, userBean.getFirstName(), userBean.getLastName(), userBean.getPhoneNumber(), userBean.getPhoneCarrier());
    	
    	
    	setModelAttributes(request, model);
    	return "user_profile";
    }
    
    @RequestMapping(value = "/user/optout", method = RequestMethod.POST)
    public String optOut(HttpServletRequest request, Model model) {
    	Account account = UserManager.getCurrentUser(request.getSession());
    	
    	CustomData customData = account.getCustomData();
    	customData.remove("phoneNumber");
    	customData.remove("phoneCarrier");
    	customData.save();
    	
    	return "redirect:/";
    }
    
    /**
     * AJAX service call for validating 'Phone Number' field on 'User Profile' screen.
     * 
     * @param phoneNumber
     * @return
     */
    @RequestMapping(value = "/user/validate")
    @Service
    public @ResponseBody ValidateResult validatePhoneNumberAPI(@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
    	ValidateResult result = new ValidateResult();
    	
    	result.setResult(phoneNumber.substring(0, Math.min(phoneNumber.length(), 10)));
    	
    	return result;
    }
}
