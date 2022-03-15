package com.cruiser.quasarfire.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cruiser.quasarfire.exception.QuasarFireException;

@ControllerAdvice
public class QuasarFireControllerAdvice {

	@ResponseBody
	@ExceptionHandler(QuasarFireException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void quasarFireNotFoundHandler(QuasarFireException exception) {
		
	}
}
