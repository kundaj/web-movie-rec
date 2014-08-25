package com.uu.sys.recommender.movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.uu.sys.recommender.doa.Conn;

public class Movie 
{
	static String url = Conn.url;
	static String dbName = Conn.dbName;
	static String driver = Conn.driver;
	static String userName = Conn.userName;
	static String password = Conn.password;
	
/*	public static void main(String[] args) 
	{
		long movieID = 770681152;
		JsonObject movieObj = movieInfo(movieID);
		
		System.out.println(movieObj.get("title")+"\t"+movieObj.get("year")+"\t"+movieObj.get("synopsis"));
	}*/

	public JsonObject movieInfo(Long ID)
	{
		JsonObject movie = new JsonObject(); 
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "SELECT * FROM movies WHERE movie_id =?";

			pst = conn.prepareStatement(sql);
			pst.setLong(1,ID);

			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				String title = rs.getString("title");
				String year = rs.getString("year");
				String synopsis = rs.getString("synopsis");
				
				movie.addProperty("title", title);
				movie.addProperty("year", year);
				movie.addProperty("synopsis", synopsis);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e);
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
			
		}
		return movie;
	}
}
