package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Custom error handling logic
        return "error"; // Replace with the name of your error page view template
    }


    public String getErrorPath() {
        return "/error";
    }
}
