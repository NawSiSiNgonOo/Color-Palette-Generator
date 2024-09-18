package com.colorpalettegenerator.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upload")
    public String userimage() {
        return "upload-image";
    }

    @GetMapping("/")
    public String index(){return "index";}

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }



}
