package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest() {
        return "Hi, getMethod";
    }

    @GetMapping("/getParameter")
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd)  {
        String password = "1234";
        return id+password+pwd;
    }

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiPara(SearchParam searchParam) {
        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader() {
        // {"resultCode : "OK", description : OK"}
        return Header.builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK").build();
    }
}
