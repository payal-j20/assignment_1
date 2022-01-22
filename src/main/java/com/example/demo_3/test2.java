/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shiv
 */
public class test2 {
    Map <String,String> nm;
    
    public Map<String, String> getNm() {
        return nm;
    }

    public void setNm(Map<String, String> nm) {
        
        nm.put("hii", "nm");
        nm.put("hii2", "nm3");
        
        this.nm = nm;
    }
    
    public Map chk(){
        //Map nl<String,String>=new HashMap<>();
        Map<String,String> nl = new HashMap<>();
        nl.put("hii", "nm");
        nl.put("hii2", "nm3");
        return nl;
    }
    

   
}
