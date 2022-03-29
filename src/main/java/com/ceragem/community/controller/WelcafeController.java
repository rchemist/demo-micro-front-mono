package com.ceragem.community.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/welcafe")
public class WelcafeController {
	@RequestMapping
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		String viewName = "web/welcafe/main";
		
		model.setViewName(viewName);
		return model;
	}
	
	@RequestMapping("/{cafeId}/info")
	public ModelAndView getInfoMain(HttpServletRequest request, HttpServletResponse response,@PathVariable Long cafeId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/welcafe/infoMain");
		return model;
	}
	@RequestMapping("/{cafeId}/community")
	public ModelAndView getCommunityMain(HttpServletRequest request, HttpServletResponse response,@PathVariable Long cafeId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/welcafe/communityMain");
		return model;
	}
	@RequestMapping("/{cafeId}/community/{boardId}")
	public ModelAndView getCommunityDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Long cafeId,@PathVariable Long boardId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/welcafe/communityDetail");
		return model;
	}
	@RequestMapping("/{cafeId}/community/edit")
	public ModelAndView getCommunityEdit(HttpServletRequest request, HttpServletResponse response,@PathVariable Long cafeId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/welcafe/communityEdit");
		return model;
	}
}
