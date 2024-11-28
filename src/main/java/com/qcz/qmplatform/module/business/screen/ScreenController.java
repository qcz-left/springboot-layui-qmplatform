package com.qcz.qmplatform.module.business.screen;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 大屏
 */
@Controller
@RequestMapping("/screen")
public class ScreenController {

    @GetMapping("/index")
    public String screenPage() {
        return "/module/screen/index";
    }

}
