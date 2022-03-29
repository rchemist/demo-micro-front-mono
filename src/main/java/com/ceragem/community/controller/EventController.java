package com.ceragem.community.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/event")
public class EventController {
	@RequestMapping
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/event/main");
		return model;
	}
	
	@GetMapping("/challenge/{eventId}")
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response,@PathVariable Long eventId) {
		ModelAndView model = new ModelAndView();
		String challengeType = "challenge1";
		model.setViewName("web/event/"+challengeType+"/challengeMain");
		return model;
	}
	@GetMapping("/challenge/{eventId}/{boardId}")
	public ModelAndView getStoryDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Long eventId,@PathVariable Long boardId) {
		ModelAndView model = new ModelAndView();
		String challengeType = "challenge1";
		model.setViewName("web/event/"+challengeType+"/boardDetail");
		return model;
	}

}
