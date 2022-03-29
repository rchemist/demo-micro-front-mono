package com.ceragem.community.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ceragem.common.profile.CustomerDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@RequestMapping("/login")
	public String getLogin(HttpServletRequest request
			, HttpServletResponse response
			, @ModelAttribute("loginForm") CustomerDTO loginForm
			, ModelMap model) {
		return "web/member/loginPage";
	}
}
