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
 * @author shiv
 */
@Controller
public class Orders {
    int Status_Code;
    int orderId;
    String userId;
    int quantity;
    float amount;
    
    public Map order_confirm(String userid,int quantity,String coupon) throws ClassNotFoundException, SQLException{
        
        Map <String,String> rt=new HashMap<>();
        this.quantity=quantity;
        this.userId=userid;
        int tq=0,price=0;
        int prev_tid=0,prev_oid=0;
        Boolean coupon_exist=false; 
        System.out.println("quantity **************-------");
        db obj =new db();
        Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from stock");
        int i=0;
        while(rs.next()){
            tq=rs.getInt("available");
            price=rs.getInt("price");
        }
        //ResultSet rs2 = stmt.executeQuery("select coupon from order_details where userid='"+userid+"'");
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"'");
        int total_coupons=0;
        
        Statement stmt3 = con.createStatement();  
        ResultSet rs_3 = stmt3.executeQuery("select count(*) from order_details where userid='"+userid+"'");
        while(rs_3.next()){
            total_coupons=rs_3.getInt(1);
            System.out.println("count is : "+total_coupons);
        }
        
        String[] coupon_list=new String[total_coupons];
        while(rs2.next()){
            coupon_list[i]=rs2.getString("coupon");
            System.out.println("coupons :  "+rs2.getString("coupon"));
            System.out.println("input coupon is :  "+coupon);
            i++;
        }
        
        System.out.println("coupon fetch finished");
        
        if(tq>quantity){
            for(int j=0;j<coupon_list.length;j++){
                //System.out.println("coupon list"+coupon_list[j]+" : "+j);
                //System.out.println("coupon"+coupon);
                if(coupon_list[j].equals(coupon)){
                    coupon_exist=true;
                    System.out.println("!!!!!!!!!!!!!!!!!!!!       coupon used !!");
                }
                System.out.println("ls : "+coupon_list[j]+" coupon : "+coupon);
            }
            if(coupon_exist){
                Map <String,String> rt2=new HashMap<>();
                rt2.put("Status_Code","404");
                rt2.put("description", "User has already used the coupon before, or coupon does not exist");
                return rt2;
                //return "Orders{" +"Status_code"+"404"+ "description"+"coupon already used"+"}";
            }
            else{
                ResultSet rs3 = stmt.executeQuery("select * from order_details where  id=(select max(id) from order_details)");
                System.out.println("rs3 finished ---");
                while(rs3.next()){
                    prev_oid=rs3.getInt("order_id");
                    prev_tid=rs3.getInt("transaction_id");
                }
                System.out.println("st2 upper---------");
                int chg=tq-quantity;
                
                String cp=coupon.replaceAll("[^0-9]", "");   
            System.out.println("regex done");
            float c=Integer.parseInt(cp);
            this.amount=quantity*price-((price*quantity)*(c/100));
            int amt=(int)this.amount;
            System.out.println("amt done");
            //st.close(); 
            
            this.Status_Code=200;
            System.out.println("status done");
            rt.put("Status_Code","200");
            rt.put("amount", ""+amt);
            System.out.println("initialized map ");
            return rt;
            
        }
        }
        
        
        /*int tq=0;
        db obj =new db();
        Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select qty from stock where p_n='"+p_n+"'");
        while(rs.next()){
            tq=rs.getInt("qty");
        }
        int qnt=Integer.parseInt(qn);
        if(qnt<=tq){
            PreparedStatement st = con.prepareStatement("insert into cart values(?,?,?)");
            st.setString(1,"pay");
            st.setString(2,p_n);
            st.setInt(3,qnt);
        }
        */
        else{
                Map <String,String> rt3=new HashMap<>();
                rt3.put("Status_Code","404");
                rt3.put("description","Quantity is either less than 1 or more than the maximum quantity of product available");
                return rt3;
                //return "Orders{" +"Status_code"+"404"+ "description="+"Quantity not in stock "+"}";
        }
    
        
        
        
    }
    
    public List order_status(String userid,int orderid) throws SQLException, ClassNotFoundException{
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
        //int count=rs.getInt(1);
        System.out.println("above rs2  ---"+count);
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"' and order_id="+orderid);
        System.out.println("rs2 finished   -----");
        List<JSONObject> myJSONObjects = new  ArrayList<JSONObject> (3);
        //JSONObject jo = new JSONObject();
        for(int i=0; i<2; i++) {
        
        while(rs2.next()){
            JSONObject obj = new JSONObject();
            //System.out.println("values :  "+rs2.getString("order_id"));
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
        //int count=rs.getInt(1);
        System.out.println("above rs2  ---"+count);
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"'");
        System.out.println("rs2 finished   -----");
        List<JSONObject> myJSONObjects = new  ArrayList<JSONObject> (3);
        //JSONObject jo = new JSONObject();
        for(int i=0; i<2; i++) {
        
        while(rs2.next()){
            JSONObject obj = new JSONObject();
            //System.out.println("values :  "+rs2.getString("order_id"));
            obj.put("orderId",rs2.getString("order_id"));
            obj.put("amount",rs2.getInt("amount"));
            obj.put("date",rs2.getDate("order_date"));
            obj.put("coupon",rs2.getString("coupon"));
            myJSONObjects.add(i,obj);
            
            
        }
        
        
}
        
       
        return myJSONObjects;
        //String ab[]=new String[4];
        /*Map <String,String> rt=new HashMap<>();
        List<Map<String , String>> myMap  = new ArrayList<Map<String,String>>();
        rt.put("oid","12");
        rt.put("tid","23");
        rt.put("fid","143");
        rt.put("did","1545");
        myMap.add(rt);
        System.out.println(myMap.toString());
        rt.clear();
        rt.put("oid","2");
        rt.put("tid","3");
        rt.put("fid","4");
        rt.put("did","5");
        System.out.println(myMap.toString());
        System.out.println("size is : "+myMap.size());
        myMap.add(rt);
        Iterator <Map<String,String>> it=myMap.iterator();
        while(it.hasNext()){
            System.out.println("values are :"+it.next());
        }
        return myMap;*/
        /*Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from user_details");
        while(rs.next);*/
    }
    /*public List order_tract(String userid,int orderid){
        
    }*/
    
    
   
    
    //@RequestMapping("orders2")
    public Map cartvalidat(String userid,int quantity,String coupon) throws ClassNotFoundException, SQLException {
        Map <String,String> rt=new HashMap<>();
        
        
        this.quantity=quantity;
        this.userId=userid;
        
        int tq=0,price=0;
        int prev_tid=0,prev_oid=0;
        Boolean coupon_exist=false; 
        System.out.println("quantity **************-------");
        db obj =new db();
        Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from stock");
        
        int i=0;
        while(rs.next()){
            tq=rs.getInt("available");
            price=rs.getInt("price");
        }
        
        //ResultSet rs2 = stmt.executeQuery("select coupon from order_details where userid='"+userid+"'");
        ResultSet rs2 = stmt.executeQuery("select * from order_details where userid='"+userid+"'");
        int total_coupons=0;
        
        Statement stmt3 = con.createStatement();  
        ResultSet rs_3 = stmt3.executeQuery("select count(*) from order_details where userid='"+userid+"'");
        while(rs_3.next()){
            total_coupons=rs_3.getInt(1);
            System.out.println("count is : "+total_coupons);
        }
        
        String[] coupon_list=new String[total_coupons];
        while(rs2.next()){
            coupon_list[i]=rs2.getString("coupon");
            System.out.println("coupons :  "+rs2.getString("coupon"));
            System.out.println("input coupon is :  "+coupon);
            i++;
        }
        
        System.out.println("coupon fetch finished");
        
        if(tq>quantity){
            for(int j=0;j<coupon_list.length;j++){
                //System.out.println("coupon list"+coupon_list[j]+" : "+j);
                //System.out.println("coupon"+coupon);
                if(coupon_list[j].equals(coupon)){
                    coupon_exist=true;
                    System.out.println("!!!!!!!!!!!!!!!!!!!!       coupon used !!");
                }
                System.out.println("ls : "+coupon_list[j]+" coupon : "+coupon);
            }
            if(coupon_exist){
                Map <String,String> rt2=new HashMap<>();
                rt2.put("description", "User has already used the coupon before, or coupon does not exist");
                return rt2;
                //return "Orders{" +"Status_code"+"404"+ "description"+"coupon already used"+"}";
            }
            else{
                ResultSet rs3 = stmt.executeQuery("select * from order_details where  id=(select max(id) from order_details)");
                System.out.println("rs3 finished ---");
                while(rs3.next()){
                    prev_oid=rs3.getInt("order_id");
                    prev_tid=rs3.getInt("transaction_id");
                }
                System.out.println("st2 upper---------");
                int chg=tq-quantity;
                PreparedStatement st2 = con.prepareStatement("update stock set available="+(tq-quantity)+",ordered="+quantity+"where available="+tq+"");
                st2.executeUpdate();
                System.out.println("st2 finished --------------");
            PreparedStatement st = con.prepareStatement("insert into order_details(order_date,userid,order_id,quantity,amount,coupon,transaction_id,status) values(?,?,?,?,?,?,?,?)");
            System.out.println("st down --------------");
            LocalDate c_date = LocalDate.now();
            Date sq_date=Date.valueOf(c_date);
            String cp=coupon.replaceAll("[^0-9]", "");   
            
            float c=Integer.parseInt(cp);
            this.amount=quantity*price-((price*quantity)*(c/100));
            int amt=(int)this.amount;
            
            this.orderId=prev_oid+1;
            st.setDate(1,sq_date);
            st.setString(2, userid);
            st.setInt(3, prev_oid+1);
            st.setInt(4, quantity);
            st.setInt(5, amt);
            st.setString(6,coupon);
            st.setInt(7,prev_tid+1);
            st.setString(8,"successfull");
            st.executeUpdate();
            //st.close(); 
            List<String> list=new ArrayList<String>();
            this.Status_Code=200;
            rt.put("orderId", ""+(prev_oid+1));
            rt.put("quantity", ""+quantity);
            rt.put("+userid", ""+userid);
            rt.put("amount", ""+amt);
            rt.put("coupon", ""+coupon);
            return rt;
            
        }
        }
        
        
        /*int tq=0;
        db obj =new db();
        Connection con=obj.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select qty from stock where p_n='"+p_n+"'");
        while(rs.next()){
            tq=rs.getInt("qty");
        }
        int qnt=Integer.parseInt(qn);
        if(qnt<=tq){
            PreparedStatement st = con.prepareStatement("insert into cart values(?,?,?)");
            st.setString(1,"pay");
            st.setString(2,p_n);
            st.setInt(3,qnt);
        }
        */
        else{
                Map <String,String> rt3=new HashMap<>();
                rt3.put("description","Quantity is either less than 1 or more than the maximum quantity of product available");
                return rt3;
                //return "Orders{" +"Status_code"+"404"+ "description="+"Quantity not in stock "+"}";
        }
    }
}
