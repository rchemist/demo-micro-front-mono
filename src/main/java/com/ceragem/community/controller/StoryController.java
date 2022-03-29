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
@RequestMapping("/story")
public class StoryController {
	@GetMapping("/type/{category}")
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response,@PathVariable String category) {
		ModelAndView model = new ModelAndView();
		Integer storyItemCount = 10;
		String viewName = "web/story/generalList";
		
		if ("main".equals(category)) {
			viewName = "web/story/main";
		}
		else if ("subscribe".equals(category)) {
			viewName = "web/story/subscribe";
		}
		model.addObject("storyItemCount", storyItemCount);
		model.addObject("category", category);
		
		model.setViewName(viewName);
		return model;
	}
	@GetMapping("/{storyId}")
	public ModelAndView getStoryDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Long storyId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/story/storyDetail");
		return model;
	}
	@GetMapping("/writer/{writerId}")
	public ModelAndView getWriterHome(HttpServletRequest request, HttpServletResponse response,@PathVariable Long writerId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/story/writerHome");
		return model;
	}
	@GetMapping("/edit")
	public ModelAndView getEditForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/story/editForm");
		return model;
	}
}
