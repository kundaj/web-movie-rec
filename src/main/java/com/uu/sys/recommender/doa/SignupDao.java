package com.uu.sys.recommender.doa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupDao
{
	
	public static boolean doSignup(String username, String pass) 
	{
		boolean status = true;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String url = Conn.url;
		String dbName = Conn.dbName;
		String driver = Conn.driver;
		String userName = Conn.userName;
		String password = Conn.password;
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);

			String sql = "INSERT INTO login (username, password) VALUES (?,?)";

			pst = conn.prepareStatement(sql);
			pst.setString(1,username);
			pst.setString(2,pass);

			pst.execute();
			System.out.println("Signed up: "+username);

		} 
		catch (Exception e) 
		{
			System.out.println(e);
			status =false;
		} 
		
		finally 
		{
			if (conn!= null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			if (pst != null) 
			{
				try
				{
					pst.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			if (rs != null) 
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return status;
	}
}
