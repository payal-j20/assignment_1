/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author payal
 */
@Controller
public class Validate {
    /*@RequestMapping("orders")
    public String ad(@RequestParam("qty") String qt){
        //String qt=req.getParameter("qty");
        System.out.println("hi-----------------------"+qt);
        return "log";
    }*/
    @RequestMapping("/")
    public String abc(){
        return "reg";
    }
    
    @RequestMapping("/log")
    public String abc2(){
        
        return "log";
    }
    @RequestMapping("regvalidate")
    public ModelAndView chk(@RequestParam("email") String em,@RequestParam("userid") String ui,@RequestParam("password") String pas) throws ClassNotFoundException, SQLException{
        
        ModelAndView mv=new ModelAndView();
        if (em==""){
            mv.setViewName("reg");
            mv.addObject("em","It can't be empty");
            return mv;
        }
        else if(ui==""){
            mv.setViewName("reg");
            mv.addObject("un","It can't be empty");
            return mv;
        }
        else if(pas==""){
            mv.setViewName("reg");
            mv.addObject("pas","It can't be empty");
            return mv;
        }
        else{
            db ob=new db();
            Connection con=ob.init();
            Statement stmt = con.createStatement();  
            //CHECK IF USERID IS UNIQUE OR ALREADY EXIST
            ResultSet rs3 = stmt.executeQuery("select * from user1 where userid='"+ui+"'");
            if(rs3.next()){
                mv.setViewName("reg");
                mv.addObject("userid","Userid already exist Try Other Unique Userid");
                return mv;
            }
            else{
                PreparedStatement st = con.prepareStatement("insert into user1 values(?,?,?)");
                st.setString(1, ui);
                st.setString(2, em);
                st.setString(3, pas);
                st.executeUpdate();
                mv.setViewName("dis");
                mv.addObject("userid",ui);
                return mv;
            }
       
        }
    }
    @RequestMapping("logvalidate")
    public ModelAndView chk2(@RequestParam("userid") String ui,@RequestParam("password") String pas) throws ClassNotFoundException, SQLException{
        ModelAndView mv=new ModelAndView();
        if(ui==""){
            mv.setViewName("log");
            mv.addObject("ui","It can't be empty");
            return mv;
        }
        else if(pas==""){
            mv.setViewName("log");
            mv.addObject("pas","It can't be empty");
            return mv;
        }
        else{
            
            
            
            db ob=new db();
            Connection con=ob.init();
            Statement stmt = con.createStatement(); 
            //CHECK WHETHER USER IS EXIST OR NOT
            ResultSet rs3 = stmt.executeQuery("select * from user1 where userid='"+ui+"' and password='"+pas+"'");
            if(rs3.next()){
                System.out.println("ui"+rs3.getString("userid"));
                System.out.println("ui"+rs3.getString("password"));
                
                mv.setViewName("dis");
                mv.addObject("userid",ui);
                return mv;
            }
            else{
                mv.setViewName("log");
                mv.addObject("lc","Login credentials doesn't match");
                return mv;
            }
        }
    }
    @RequestMapping(value="{userid}/order_confirm",method=POST)
    public ModelAndView confirm(@PathVariable("userid") String ui,@RequestParam("qty") String qty,@RequestParam("coupon") String coupon) throws ClassNotFoundException, SQLException, JsonProcessingException, InterruptedException{
        //CALL API TO CHECK WHETHER QUANTITY IS AVAILABLE OR NOT
        request_api ob=new request_api();    
        int qt=Integer.parseInt(qty);
        JSONObject ab=new JSONObject(ob.productvalidate(ui, qty, coupon));
        String sc=ab.get("Status Code").toString();
        System.out.print("sc:"+sc);
        System.out.print("HttpStatus.OK:"+HttpStatus.OK.toString());
        if(sc.equals(HttpStatus.OK.toString())){
            int am=Integer.parseInt(ab.get("amount").toString());
            //RETURN AMOUNT TO BE PAID
            ModelAndView mv=new ModelAndView("payment");
            mv.setViewName("payment");
            mv.addObject("userid",ui);
            mv.addObject("qty",qty);
            mv.addObject("coupon",coupon);
            mv.addObject("amount",am);
            return mv;
        }
        else{
            String dc=ab.get("description").toString();
            ModelAndView mv2=new ModelAndView();
            mv2.setViewName("error");
            mv2.addObject("description",dc);
            return mv2;
        }
        
    }
     
}
