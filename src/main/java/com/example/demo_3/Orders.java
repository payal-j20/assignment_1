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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author payal
 */
@Controller
public class Orders {
    int Status_Code;
    int orderId;
    String userId;
    int quantity;
    float amount;
    
    
    public List order_status(String userid,int orderid) throws SQLException, ClassNotFoundException{
        String query="select count(*) from order_details where userid='"+userid+"'";
        db ob=new db();
        Connection con=ob.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery(query);
        
        int count=0;
        while(rs.next()){
            count=rs.getInt(1);
        }
        //SELECT ALL ORDERS WITH ORDERID,AMOUNT,DATE,COUPON,TRANSACTION,STATUS FROM ORDERS WITH USERID 
        //CREATE LIST OF JSONOBJECTS WITH ABOVE FEILDS AND AND RETURN TO REST API 
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"' and order_id="+orderid);
        List<JSONObject> myJSONObjects = new  ArrayList<JSONObject> (3);
        for(int i=0; i<2; i++) {
        while(rs2.next()){
            JSONObject obj = new JSONObject();
            obj.put("orderId",rs2.getString("order_id"));
            obj.put("amount",rs2.getInt("amount"));
            obj.put("date",rs2.getDate("order_date"));
            obj.put("coupon",rs2.getString("coupon"));
            obj.put("transactionid",rs2.getInt("transaction_id"));
            obj.put("status",rs2.getString("status") );
            myJSONObjects.add(i,obj);
            
        } 
        }
        return myJSONObjects;
    }
    
    public List order_list(String userid) throws SQLException, ClassNotFoundException{
        String query="select count(*) from order_details where userid='"+userid+"'";
        db ob=new db();
        Connection con=ob.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("above count  ---");
        int count=0;
        while(rs.next()){
            count=rs.getInt(1);
        }
        //SELECT ALL ORDER WITH USERID
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"'");
        List<JSONObject> myJSONObjects = new  ArrayList<JSONObject> (3);
        for(int i=0; i<2; i++) {
        while(rs2.next()){
            JSONObject obj = new JSONObject();
            obj.put("orderId",rs2.getString("order_id"));
            obj.put("amount",rs2.getInt("amount"));
            obj.put("date",rs2.getDate("order_date"));
            obj.put("coupon",rs2.getString("coupon"));
            myJSONObjects.add(i,obj);
        }       
    }
        return myJSONObjects;
        
    }
    
    
}
