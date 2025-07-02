

package com.eazybytes.news.aggregator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeRedirectController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login.html";
    }
}

