/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author shiv
 */
public class inventory {
    int ordered,price,available;
    public inventory() throws ClassNotFoundException, SQLException{
        db a=new db();
        Connection con=a.init();
        Statement stmt = con.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from stock");
        while(rs.next()){
            ordered=rs.getInt(1);
            price=rs.getInt(2);
            available=rs.getInt(3);
            
        }
        
    }
    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "inventory{" + "ordered=" + ordered + ", price=" + price + ", available=" + available + '}';
    }
   
}
