/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author payal
 */
@RestController
public class request_api {
    //LIST ALL PRODUCT DETAILS
    @RequestMapping(value="inventory",method=GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String inventory() throws ClassNotFoundException, SQLException{
        inventory ab=new inventory();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status Code"+HttpStatus.OK,new JSONObject(ab) );
        return jsonObject.toString();
    }
    //LIST ALL THE AVAILABLE COUPONS
    @RequestMapping(value="fetchCoupons",method=GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String couponlist() throws ClassNotFoundException, SQLException{
        int i=1;
        db ob=new db();
        Connection con=ob.init();
        JSONObject list=new JSONObject();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from coupons");
        while(rs.next()){
            
            list.put(rs.getString(i), (rs.getString(i).replaceAll("[^0-9]", "")));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status Code"+HttpStatus.OK,new JSONObject(list.toString().replaceAll("\\[", "").replaceAll("\\]","")) );
        return jsonObject.toString();
    }
    
    
    //CHECK WHTHER PRODUCT IS AVAILABLE IN STOCK OR NOT
    @RequestMapping(value="{userid}/order",method=POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public String productvalidate(@PathVariable("userid") String ui,@RequestParam("qty") String qty,@RequestParam("coupon") String coupon) throws ClassNotFoundException, SQLException, JsonProcessingException, InterruptedException{
        //Orders ob=new Orders();
        int qnt=Integer.parseInt(qty);
        int quantity=Integer.parseInt(qty);
        int tq=0,price=0,prev_oid=0,prev_tid=0;
        float amount;
        Boolean coupon_exist=false; 
        db obj =new db();
        Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from stock");
        int i=0;
        while(rs.next()){
            tq=rs.getInt("available");
            price=rs.getInt("price");
        }
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+ui+"'");
        int total_coupons=0;
        Statement stmt3 = con.createStatement();  
        ResultSet rs_3 = stmt3.executeQuery("select count(*) from order_details where userid='"+ui+"'");
        while(rs_3.next()){
            total_coupons=rs_3.getInt(1);
        }
        String[] coupon_list=new String[total_coupons];
        while(rs2.next()){
            coupon_list[i]=rs2.getString("coupon");
            i++;
        }
        if(tq>=Integer.parseInt(qty)){
            System.out.println("INSTOCK                -----");
            for(int j=0;j<coupon_list.length;j++){
                if(coupon_list[j].equals(coupon)){
                    coupon_exist=true;
                    System.out.println("    coupon used !!");
                }
            }
            if(coupon_exist){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Status Code",""+HttpStatus.NOT_FOUND);
                jsonObject.put("description","User has already used the coupon before, or coupon does not exist");
                
                return jsonObject.toString();
            }
            else{
                ResultSet rs3 = stmt.executeQuery("select * from order_details where  id=(select max(id) from order_details)");
                while(rs3.next()){
                    prev_oid=rs3.getInt("order_id");
                    prev_tid=rs3.getInt("transaction_id");
                }
                String cp=coupon.replaceAll("[^0-9]", "");   
                System.out.println("regex done");
                float c=Integer.parseInt(cp);
                amount=quantity*price-((price*quantity)*(c/100));
                int amt=(int)amount;
                System.out.println("amt done");
                //st.close(); 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Status Code",""+HttpStatus.OK);
                jsonObject.put("userId",ui);
                jsonObject.put("quantity",qty);
                jsonObject.put("amount",amt);
                jsonObject.put("coupon",coupon);
                
                return jsonObject.toString();   
        }
        }
        else{
                Map <String,String> rt3=new HashMap<>();
                rt3.put("description","Quantity is either less than 1 or more than the maximum quantity of product available");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Status Code",""+HttpStatus.NOT_FOUND);
                jsonObject.put("description","Quantity is either less than 1 or more than the maximum quantity of product available");
                return jsonObject.toString();
                //return "Orders{" +"Status_code"+"404"+ "description="+"Quantity not in stock "+"}";
        }
       
    }
    //MAKE PAYMENT
    @RequestMapping(value="{userid}/pay",method=POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public String payment(@PathVariable("userid") String ui,@RequestParam("amount") String amount,@RequestParam("qty") String qty,@RequestParam("coupon") String coupon) throws SQLException, ClassNotFoundException{
        int prev_oid=0,prev_tid=0,tq=0,price=0;
        int qt=Integer.parseInt(qty);
        int amt=Integer.parseInt(amount);
        db ob=new db();
        Connection con=ob.init();
        Statement stmt = con.createStatement();  
        ResultSet rs1 = stmt.executeQuery("select * from stock");
        System.out.println("rs3 finished ---");
        int ordered=0;
        while(rs1.next()){
            ordered=rs1.getInt("ordered");
            tq=rs1.getInt("available");
        }
        System.out.println("ordered : "+ordered);
        ResultSet rs3 = stmt.executeQuery("select * from order_details where  id=(select max(id) from order_details)");
        while(rs3.next()){
            prev_oid=rs3.getInt("order_id");
            prev_tid=rs3.getInt("transaction_id");
        } 
        PreparedStatement st2 = con.prepareStatement("update stock set available="+(tq-qt)+",ordered="+(ordered+qt)+"where available="+tq);
        
        System.out.println("tq : ----------------->"+tq);
        System.out.println("qt : "+qt);
        
        System.out.println("available : "+(tq-qt));
        System.out.println("ordered : "+(ordered+qt));
        
        
        
        
        st2.executeUpdate();
        PreparedStatement st = con.prepareStatement("insert into order_details(order_date,userid,order_id,quantity,amount,coupon,transaction_id,status) values(?,?,?,?,?,?,?,?)");
        LocalDate c_date = LocalDate.now();
        Date sq_date=Date.valueOf(c_date);
        String cp=coupon.replaceAll("[^0-9]", "");   
        st.setDate(1,sq_date);
        st.setString(2, ui);
        st.setInt(3, prev_oid+1);
        st.setInt(4, qt);
        st.setInt(5, (int)amt);
        st.setString(6,coupon);
        st.setInt(7,prev_tid+1);
        st.setString(8,"successfull");
        int rows=st.executeUpdate();
        if(rows>0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Status Code",""+HttpStatus.OK);
            jsonObject.put("userId",ui);
            jsonObject.put("orderId",prev_oid+1);
            jsonObject.put("transactionId",prev_tid+1);
            jsonObject.put("status","successful");
            return jsonObject.toString();
            }
        else if(amt<0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Status Code",""+HttpStatus.NOT_FOUND);
            jsonObject.put("userId",ui);
            jsonObject.put("orderId",prev_oid+1);
            jsonObject.put("transactionId",prev_tid+1);
            jsonObject.put("status","failed");
            jsonObject.put("description","Payment Failed as amount is invalid");
            return jsonObject.toString();
        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Status Code",""+HttpStatus.NOT_FOUND);
            jsonObject.put("userId",ui);
            jsonObject.put("orderId",prev_oid+1);
            jsonObject.put("transactionId",prev_tid+1);
            jsonObject.put("status","failed");
            jsonObject.put("description","Payment Failed From Bank");
            return jsonObject.toString();
        }
    }
    //LIST ALL ORDERS MADE BY USER
    @RequestMapping(value="{userid}/orders",method=GET,headers = "Accept=application/json")
    public String order_details(@PathVariable("userid") String ui) throws JsonProcessingException, SQLException, ClassNotFoundException, InterruptedException{
        Orders ob=new Orders();
        List rt=ob.order_list(ui);
        System.out.println("values of lis is :       "+rt.toString());
        //String json = new ObjectMapper().writeValueAsString(rt);
        return rt.toString();
        
    }
    //CHECK STATUS OF ORDER
    @RequestMapping(value="{userid}/orders/{orderid}",method=GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String  order_status(@PathVariable("userid") String ui,@PathVariable("orderid") String oid) throws SQLException, ClassNotFoundException{
        
        Orders ob=new Orders();
        List rt=ob.order_status(ui,Integer.parseInt(oid));
        System.out.println("values of lis is :       "+rt.toString());
        //String json = new ObjectMapper().writeValueAsString(rt);
        String rg=rt.toString();
        rg=rg.replaceAll("\\[", "").replaceAll("\\]","");
        System.out.println("values -------- :       "+rg);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status Code"+HttpStatus.OK,new JSONObject(rg) );

        //jsonObject.put("0", rg);
        return jsonObject.toString();
    }
    
}
