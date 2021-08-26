package com.qcz.qmplatform.module.screen;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 大屏
 */
@Controller
@RequestMapping("/screen")
public class ScreenController {

    @RequestMapping("/index")
    public String screenPage() {
        return "/module/screen/index";
    }

}
