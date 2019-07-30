package com.be.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class TsController {

    @RequestMapping("/test")
    public String tset() {
        return "Connon test";
    }
}
