package com.myringle.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Controller
@RequestMapping("/app")
public class Usertroller {

    @GetMapping("/getUser")
    @ResponseBody
    public String getUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletContext sc = request.getServletContext();
        String app = (String)sc.getAttribute("app");
        log.info("[app: {}]",app);
        return  "getUser";
    }
}
