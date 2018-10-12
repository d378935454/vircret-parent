package com.talentcenter.controller;

import com.talentcenter.entity.Demo;
import com.talentcenter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping("/demos")
    public List<Demo> demos(){
        List<Demo> demos = demoService.demos();
        System.out.println("kekekkdkdkd");
        System.out.println("kekekkdkdkd");
        return demos;
    }

    @RequestMapping("test")
    public String test(Model model){
        List<Demo> demos = demoService.demos();
        String test = "abac";
        model.addAttribute("test",test);
        model.addAttribute("demos",demos);
        return "/test/test.html";
    }
}
