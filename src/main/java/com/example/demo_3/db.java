/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author payal
 */
public class db {
    public Connection init() throws ClassNotFoundException, SQLException{
        
        String dbURL ="jdbc:postgresql://ec2-34-194-171-47.compute-1.amazonaws.com:5432/";
        String dbName = "d80qshevosrskt";
        String dbUsername = "nqboosghvprlse";
        String dbPassword = "7f043a231c1d841becef331deae630bd04e4d60b0816473a7503354730b35ec8";
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
            
        return con;
    }
   
}
