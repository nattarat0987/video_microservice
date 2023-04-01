package com.youtubeclonebynattarat.nattaratprojects.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class Testget {

    @GetMapping
    public String Login()  {

        return "Test";
    }
}
