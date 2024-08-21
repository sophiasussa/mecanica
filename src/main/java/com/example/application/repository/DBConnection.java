package com.example.application.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static DBConnection instance = null;
	private Connection conn;

	private String url = "jdbc:mysql://localhost:3306/mecanica";
	private String usuario = "root";
	private String senha = "H123asd@";
	
	private DBConnection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, usuario, senha);
		} catch (Exception ex) {
	}}

    public Connection getConnection() {
        return conn;
    }

    public static DBConnection getInstance() throws SQLException{
        if(instance == null) {
            instance = new DBConnection();
        }else if(instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }
}
