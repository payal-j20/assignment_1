/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author shiv
 */
@Controller
public class test1 {
    @RequestMapping("/")
    public String abc(){
        return "reg";
    }
    
    @RequestMapping("/log")
    public String abc2(){
        
        return "log";
    }
    @RequestMapping("/test")
    public String abc3(@RequestParam("p_n") String p_n,@RequestParam("quantity") String qn){
        System.out.println("-------------------------------------"+p_n);
        System.out.println("-------------------------------------"+p_n);
        System.out.println("-------------------------------------"+p_n);
        System.out.println("-------------------------------------"+qn);
        System.out.println("-------------------------------------"+qn);
        System.out.println("-------------------------------------"+qn);
        
        return "reg";
    }
}
