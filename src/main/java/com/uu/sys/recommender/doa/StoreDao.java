package com.uu.sys.recommender.doa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.mahout.math.Matrix;
import org.jumpmind.symmetric.csv.CsvWriter;

public class StoreDao 
{
	static String url = Conn.url;
	static String dbName = Conn.dbName;
	static String driver = Conn.driver;
	static String userName = Conn.userName;
	static String password = Conn.password;
	
	
	public static boolean storeSkippedItems(long uId, long mId) 
	{
		boolean status = false;
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "INSERT INTO skipped_items (userID, movieID) VALUES (?,?)";
			
			pst = conn.prepareStatement(sql);
			pst.setLong(1,uId);
			pst.setLong(2,mId);

			pst.execute();
			
			System.out.println("Skipped Item stored");
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
		
		return status;
	}
	
	public static boolean storeUserInfo(long uId, long mId, double r) 
	{
		boolean status = false;
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			String sql = "INSERT INTO movie_rating (userid, movieid, rating) VALUES (?,?,?)";

			System.out.println("Rating="+r);
			pst = conn.prepareStatement(sql);
			pst.setLong(1,uId);
			pst.setLong(2,mId);
			pst.setDouble(3,r);

			pst.execute();
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
		
		return status;
	}
	
	@SuppressWarnings("resource")
	public static boolean storeRecommenderRating(long uId, Integer r) 
	{
		boolean status = false;
		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			/*
			 * Check if user already rated recommender
			 */
			 
			boolean rated = true;
			String sql_count = "SELECT COUNT(*) FROM recommender_rating WHERE userId=?";
			pst = conn.prepareStatement(sql_count);
			pst.setLong(1,uId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				if (rs.getInt(1) == 0)
					rated = false;
			}
			
			if (rated == false)
			{
				String sql = "INSERT INTO recommender_rating (userId, rec_rating) VALUES (?,?)";
	
				System.out.println("Stored Recommender Rating="+r);
				pst = conn.prepareStatement(sql);
				pst.setLong(1,uId);
				pst.setInt(2,r);
			}
			else
			{
				String sql = "UPDATE recommender_rating SET rec_rating = ? WHERE userId = ?";
				
				System.out.println("Update Recommender Rating="+r);
				pst = conn.prepareStatement(sql);
				pst.setInt(1,r);
				pst.setLong(2,uId);
			
			}

			pst.execute();
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
		
		return status;
	}
	
	public static void storeMatrixW(Matrix matrix) 
	{
		System.out.println("New storeMatrixW() executed!");
		
		try
		{
			File MatrixWfile = new File("MatrixW.csv");
			CsvWriter csvOutput = new CsvWriter(new FileWriter(MatrixWfile), ',');
			
			for (int i=0;i<matrix.rowSize();i++)
			{
				for (int j=0;j<matrix.columnSize();j++)
				{
					csvOutput.write(Double.toString(matrix.get(i,j)));
				}
				csvOutput.endRecord();
			}	
			csvOutput.close();
    	}
    	catch(FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}
	
/*	public static void storeMatrixW(Matrix matrix) 
	{

		Connection conn = null;
		PreparedStatement pst = null;		
		
		try 
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			

			//Check if Matrix is already stored
		
			 
			boolean matrix_empty = false;
			String sql_count = "SELECT COUNT(*) FROM Matrix_W";
			pst = conn.prepareStatement(sql_count);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				if (rs.getInt(1) == 0)
					matrix_empty = true;
			}	
			
			for (int i=0;i<matrix.rowSize();i++)
			{
				for (int j=0;j<matrix.columnSize();j++)
				{
					double cell_value = matrix.get(i,j);
					
					if (matrix_empty == true)
					{
						String sql = "INSERT INTO Matrix_W (row, col, cell_value) VALUES (?,?,?)";
			
						pst = conn.prepareStatement(sql);
						pst.setLong(1,i);
						pst.setLong(2,j);
						pst.setDouble(3,cell_value);
					}
					else
					{
						String sql = "UPDATE Matrix_W SET cell_value = ? WHERE row = ? AND col = ?";
						
						pst = conn.prepareStatement(sql);
						pst.setDouble(1,cell_value);
						pst.setLong(2,i);
						pst.setLong(3,j);
						
					}
		
					pst.execute();
				}
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
	}*/
}
