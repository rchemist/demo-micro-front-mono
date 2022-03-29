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
@RequestMapping("/community")
public class CommunityController {
	@GetMapping("/type/{category}")
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response,@PathVariable String category) {
		ModelAndView model = new ModelAndView();
		String viewName = "web/community/generalList";
		Integer itemCount = 10;
		
		if ("main".equals(category)) {
			viewName = "web/community/main";
		}
		else if ("interest".equals(category)) {
			viewName = "web/community/interest";
		}
		
		model.addObject("category", category);
		model.addObject("itemCount", itemCount);
		
		model.setViewName(viewName);
		return model;
	}
	@GetMapping("/type/{category}/setting")
	public ModelAndView getMainSetting(HttpServletRequest request, HttpServletResponse response,@PathVariable String category) {
		ModelAndView model = new ModelAndView();
		String viewName = "web/community/setting";
		
		model.setViewName(viewName);
		return model;
	}
	
	@GetMapping("/{boardId}")
	public ModelAndView getDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Long boardId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/community/detail");
		return model;
	}
	@GetMapping("/notice/{boardId}")
	public ModelAndView getNoticeDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Long boardId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/community/noticeDetail");
		return model;
	}

	@GetMapping("/edit")
	public ModelAndView getEditForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/community/editForm");
		return model;
	}
}
