package com.example.demo_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Demo3Application {
        @RequestMapping("/")
	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
                
                test1 ab=new test1();
                ab.abc();
	}

}