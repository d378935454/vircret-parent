package com.talentcenter.controller;

import com.talentcenter.entity.Demo;
import com.talentcenter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return demos;
    }
}
