package com.uu.sys.recommender.movies;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.uu.sys.recommender.doa.Conn;

public class MovieIDs 
{
	static String url = Conn.url;
	static String dbName = Conn.dbName;
	static String driver = Conn.driver;
	static String userName = Conn.userName;
	static String password = Conn.password;
	
/*	public void main(String args[]) throws IOException
	{
		getMovieIds();
	}*/
	
	public ArrayList<Long> getMovieIds() throws IOException
	{
		ArrayList<Long> itemIDs = new ArrayList<Long>(); //Store all item IDs from files

		Connection conn = null;
		PreparedStatement pst = null;	
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "SELECT movie_id FROM movies";

			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Long movieID = rs.getLong("movie_id");
				itemIDs.add(movieID);				
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
		
		System.out.println("Number of movies retrieved from the database: " + itemIDs.size());
		
		return itemIDs;
	}
}
