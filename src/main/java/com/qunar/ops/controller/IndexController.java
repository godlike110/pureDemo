package com.qunar.ops.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

	public static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
}
