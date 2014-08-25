package com.uu.sys.recommender.doa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;

import com.uu.sys.recommender.movies.Updater;

public class RetrieveDao 
{
	static String url = Conn.url;
	static String dbName = Conn.dbName;
	static String driver = Conn.driver;
	static String userName = Conn.userName;
	static String password = Conn.password;
	
	/***
	 * Retrieve items skipped by user
	 * uid is user id and m is number of items,mIds is item IDs
	 */
	public static ArrayList<Long> retrieveSkippedItems(String uId)
	{
		ArrayList<Long> skippedItems = new ArrayList<Long>();
		skippedItems.add((long)0); //Ensure list has at least one item
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "SELECT * FROM skipped_items WHERE userid =?";

			pst = conn.prepareStatement(sql);
			pst.setString(1,uId);

			ResultSet rs = pst.executeQuery();
			
			System.out.println("Items skipped by "+uId+" from database:");
			
			while(rs.next())
			{
				long mId = rs.getLong("movieID");
				skippedItems.add(mId);
				
				System.out.println(mId);
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
		
		return skippedItems;
	}
	
	/***
	 * Get current user's previous ratings from database
	 * uid is user id and m is number of items,mIds is item IDs
	 */
	public static Matrix retrieveUserInfo(String uId,int m, ArrayList<Long>  mIds)
	{
		Matrix u = new DenseMatrix(m,1); 
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "SELECT * FROM movie_rating WHERE userid =?";

			pst = conn.prepareStatement(sql);
			pst.setString(1,uId);

			ResultSet rs = pst.executeQuery();
			
			System.out.println("Ratings for User "+uId+" from database");
			System.out.println("Movie ID\t\tRating");
			System.out.println("========\t\t======");
			
			u.assign(0.0);
			
			while(rs.next())
			{
				long mId = rs.getLong("movieid");
				double rating = rs.getDouble("rating");
				System.out.println(mId+"\t\t\t"+rating);
				
				Updater updater =new Updater();
				
				//Store normalized ratings in u Vector
				u.set(mIds.indexOf(mId), 0, rating/updater.MAX_RATING); //Put rating at correct place in uVector
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
		
		return u;
	}
	
	/*
	 * Store and Retrieve Matrix W in Database
	 * m is the number of items in the data set
	 */
	public static Matrix retrieveMatrixW(int m)
	{
		File MatrixWfile = new File("MatrixW.csv");
		
		Matrix matrix = new DenseMatrix(m,m); 
		matrix.assign(0); //Initialize matrix	
		
		System.out.println("New retrieveMatrixW() executed!");
		
		try 
		{	
			
    		BufferedReader br = new BufferedReader(new FileReader(MatrixWfile));
    		
    		String line = "";
    		String [] row; 
    		
			for (int i=0;i<m;i++)
			{
				line=br.readLine(); 
				row = line.split(","); 
				
				for(int j=0;j<m;j++)
				{
					double value = Double.parseDouble(row[j]);	
					matrix.set(i, j, value);
				}
			}
			
			br.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		} 
		
		return matrix;
	}
}
	 
/*	public static Matrix retrieveMatrixW(int m)
	{
		Matrix matrix = new DenseMatrix(m,m); 
		matrix.assign(0); //Initialize matrix
		Connection conn = null;
		PreparedStatement pst = null;	
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "SELECT * FROM Matrix_W";

			pst = conn.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int i = rs.getInt("row");
				int j = rs.getInt("col");
				double value = rs.getDouble("cell_value");
				
				matrix.set(i, j, value);
				
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
		
		return matrix;
	}
}*/
