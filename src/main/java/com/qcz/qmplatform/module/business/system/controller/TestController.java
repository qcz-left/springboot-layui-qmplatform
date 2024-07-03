package com.qcz.qmplatform.module.business.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/nnl/stepsDemoPage")
    public String stepsDemoPage() {
        return "steps_demo";
    }

}
