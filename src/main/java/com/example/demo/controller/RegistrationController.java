package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class RegistrationController {
	
	private final UserService userService;
	
	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.addObject("/registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		
		if (userService.findByEmail(user.getEmail()).isPresent()) {
			bindingResult.rejectValue("email", "error.user"
					, "This email is used.");
		}
		
		if (userService.findByUsername(user.getUsername()).isPresent()) {
			bindingResult.rejectValue("username", "error.user"
					, "This username is used.");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/registration");
		} else {
			userService.saveUser(user);
			
			modelAndView.addObject("successMessage", "Regist Successfully !");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("/registration");		
		}
		return modelAndView; 		
	}
	
	
	
}
