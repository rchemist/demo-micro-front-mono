package io.rchemist.controller;

import io.rchemist.common.util.CollectionUtil;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@RequestMapping("/test")
@Controller
public class ThymeleafTestController {

  @RequestMapping("/list")
  public ModelAndView thymeleafTest(
      HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView){
    Article article = new Article();
    article.setName("이름");
    article.setTitle("제목");
    article.setContent("내용");

    modelAndView.getModelMap().put("article", article);
    modelAndView.addObject("article", article);
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @Data
  public static class Article implements Serializable {
    protected String name;
    protected String title;
    protected String content;
  }

}
