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
 * @author shiv
 */
public class db {
    public Connection init() throws ClassNotFoundException, SQLException{
        
        String dbDriver = "org.apache.derby.jdbc.ClientDriver1212";
        String dbURL = "jdbc:derby://localhost:1527/12";
        String dbName = "g12";
        String dbUsername = "2gr";
        String dbPassword = "121g";
        
        Class.forName(dbDriver);
        //DriverManager.registerDriver(new org.postgresql.Driver());
        Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
        return con;
    }
   
}
