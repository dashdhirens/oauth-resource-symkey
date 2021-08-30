package com.example.oauthresourcesymkey.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoMainController {

    @GetMapping("/demo")
    public String demo() {
        return "Demo success!";
    }
}
