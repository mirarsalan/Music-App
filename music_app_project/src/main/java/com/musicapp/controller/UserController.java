package com.musicapp.controller;

import com.musicapp.model.User;
import com.musicapp.service.SecurityService;
import com.musicapp.service.UserService;
import com.musicapp.validator.UserValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	private final Logger	logger	= LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService		userService;

	@Autowired
	private SecurityService	securityService;

	@Autowired
	private UserValidator	userValidator;


	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {

		logger.info("Displaying registration page");
		model.addAttribute("userForm", new User());
		return "registration";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

		logger.info("Attempting to register a user");
		userValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		userService.save(userForm);
		securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
		return "redirect:/index";
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {

		logger.info("Attempting to log in");
		if (error != null)
			model.addAttribute("error", "Your username or password is invalid.");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");
		return "login";
	}


	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {

		logger.info("< index()");
		return "index";
	}
}
