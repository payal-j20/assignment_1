/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author shiv
 */
@Controller
public class payment {
    @RequestMapping(value="*/payment_confirm",method=POST)
    @ResponseBody
    public String payment(HttpServletRequest req ,@RequestParam("userid") String userid,@RequestParam("qty") String qty,@RequestParam("coupon") String coupon,@RequestParam("amount") String amount) throws ClassNotFoundException, SQLException{
        System.out.println("payment supported "+userid);
        int prev_oid=0,prev_tid=0,tq=0,price=0;
        int qt=Integer.parseInt(qty);
        int amt=Integer.parseInt(amount);
        db ob=new db();
        Connection con=ob.init();
        Statement stmt = con.createStatement();  
        ResultSet rs1 = stmt.executeQuery("select * from stock");
        System.out.println("rs3 finished ---");
        while(rs1.next()){
            tq=rs1.getInt("available");
            price=rs1.getInt("price");
        }
        
        
        ResultSet rs3 = stmt.executeQuery("select * from order_details where  id=(select max(id) from order_details)");
        System.out.println("rs3 finished ---");
        while(rs3.next()){
            prev_oid=rs3.getInt("order_id");
            prev_tid=rs3.getInt("transaction_id");
        }
                System.out.println("st2 upper---------");
                int chg=tq-qt;
                PreparedStatement st2 = con.prepareStatement("update stock set available="+(tq-qt)+",ordered="+qt+"where available="+tq+"");
                st2.executeUpdate();
                System.out.println("st2 finished --------------");
            PreparedStatement st = con.prepareStatement("insert into order_details(order_date,userid,order_id,quantity,amount,coupon,transaction_id,status) values(?,?,?,?,?,?,?,?)");
            System.out.println("st down --------------");
            LocalDate c_date = LocalDate.now();
            Date sq_date=Date.valueOf(c_date);
            String cp=coupon.replaceAll("[^0-9]", "");   
            
            //float c=Integer.parseInt(cp);
            //amt=qt*price-((price*qt)*(c/100));
            //int amt=(int)this.amount;
            
            
            st.setDate(1,sq_date);
            st.setString(2, userid);
            st.setInt(3, prev_oid+1);
            st.setInt(4, qt);
            st.setInt(5, (int)amt);
            st.setString(6,coupon);
            st.setInt(7,prev_tid+1);
            st.setString(8,"successfull");
            st.executeUpdate();
            System.out.println("order id is "+prev_oid+1);
            RequestDispatcher rd = req.getRequestDispatcher("log");
            
            request_api ab1=new request_api();
            
            
            System.out.println("%%%%%%%%%%%%%%%%"+ab1.order_status(userid, Integer.toString(prev_oid+1).toString()));
            return "Your Order id is :"+prev_oid+1;
            //return "log";
    }
}
