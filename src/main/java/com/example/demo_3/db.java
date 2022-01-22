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
        
        String dbURL ="";
        String dbName = "";
        String dbUsername = "";
        String dbPassword = "";
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
            
        return con;
    }
   
}
