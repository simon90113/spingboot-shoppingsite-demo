package com.example.demo.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobleExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GlobleExceptionHandler.class);
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView exception(final Throwable throwable) {
//	public ModelAndView exception(final Throwable throwable, final Model model) {
		logger.error("Exception during execution of StringSecurity application", throwable);
	
		ModelAndView modelAndView = new ModelAndView("/error");
		String errorMessage = throwable != null ? throwable.toString() : "Unexpected error";
		modelAndView.addObject("errorMessage", errorMessage);
		return modelAndView;
	}
	
}