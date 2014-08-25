package com.uu.sys.recommender.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;

import com.uu.sys.recommender.doa.RetrieveDao;
import com.uu.sys.recommender.movies.MovieIDs;
import com.uu.sys.recommender.movies.Recommender;

/**
 * Servlet implementation class GetMoviesServlet
 */
public class GetMoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("****GetMoviesServlet Executed***");
		
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);

		String uID = (String) session.getAttribute("userId");
		Long userID = Long.parseLong(uID);
		ArrayList<Long> itemIDs = new ArrayList<Long>(); //list of Item IDs
		ArrayList<Long> sortedItemIDs = new ArrayList<Long>(); //list of sorted Item IDs
		MovieIDs movieIDs =  new MovieIDs(); //Get all Item IDs in the system
		ArrayList<Long> skippedItems = new ArrayList<Long>();
		itemIDs.addAll(movieIDs.getMovieIds()); 
		
		Matrix uVector = new DenseMatrix(itemIDs.size(),1);
		Matrix W = new DenseMatrix(itemIDs.size(),itemIDs.size());
		Matrix pVector = new DenseMatrix(itemIDs.size(),1);
		pVector.assign((double)1/itemIDs.size()); //Initialize probability of all elements to (1/number of items)
		
		uVector.assign(RetrieveDao.retrieveUserInfo(Long.toString(userID),itemIDs.size(),itemIDs));
		W.assign(RetrieveDao.retrieveMatrixW(itemIDs.size())); //Get W from data store
		skippedItems.addAll(RetrieveDao.retrieveSkippedItems(userID.toString()));
		
		
		/*
		 * Create new recommendation list, remove items already rated,skipped by user 
		 * The variable sortedItemIDs contains sorted list of items/recommendations unrated by user
		 */	
		System.out.println("Get recommendations in GetMoviesServlet...");
		int numOfMoviesRated = 0;
		Recommender movieRecommender = new Recommender(pVector,uVector,W,itemIDs);
		try 
		{
			ArrayList<Long> recList =  movieRecommender.getRecommendations();//Get a sorted recommendation list
			
			for (int i=0;i<itemIDs.size();i++)
			{
				if(uVector.get(itemIDs.indexOf(recList.get(i)),0) == (double)0 && !skippedItems.contains(recList.get(i)))
					sortedItemIDs.add(recList.get(i));
				else
				{
					System.out.println("Rated or Skipped by User"+uID+": "+recList.get(i)+","+uVector.get(itemIDs.indexOf(recList.get(i)),0));
					
					if (!skippedItems.contains(recList.get(i)))
						numOfMoviesRated++;
				}
			}
			System.out.println("Size of recommendation list:"+sortedItemIDs.size());
		} 
		catch (TasteException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Get recommendations done...");
		
		/*
		 * Get an updated p Vector
		 */
		pVector.assign(movieRecommender.getpVector());
		
		
		/*
		 * Create movie sessions
		 */
		if(session!=null)
		{
			session.setAttribute("itemIDs", itemIDs);
			session.setAttribute("sortedItemIDs", sortedItemIDs);
			session.setAttribute("W", W);
			session.setAttribute("uVector", uVector);
			session.setAttribute("pVector", pVector);
			session.setAttribute("currentRating", 0);
			session.setAttribute("numOfMoviesRated", numOfMoviesRated);
			session.setAttribute("skippedItems", skippedItems);

			//put sorted Item list in session variables
			try
			{
				session.setAttribute("currentMovie", sortedItemIDs.get(0).longValue()); 
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("currentMovie", null);
			}	
			try
			{
				session.setAttribute("movie2", sortedItemIDs.get(1).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie2", null);
			}
			try
			{
				session.setAttribute("movie3", sortedItemIDs.get(2).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie3", null);
			}
			try
			{
				session.setAttribute("movie4", sortedItemIDs.get(3).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie4", null);
			}
			try
			{
				session.setAttribute("movie5", sortedItemIDs.get(4).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie5", null);
			}
			try
			{
				session.setAttribute("movie6", sortedItemIDs.get(5).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie6", null);
			}
			try
			{
				session.setAttribute("movie7", sortedItemIDs.get(6).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie7", null);
			}
			try
			{
				session.setAttribute("movie8", sortedItemIDs.get(7).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie8", null);
			}
			try
			{
				session.setAttribute("movie9", sortedItemIDs.get(8).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie9", null);
			}
			try
			{
				session.setAttribute("movie10", sortedItemIDs.get(9).longValue());
			}catch(IndexOutOfBoundsException e)
			{
				session.setAttribute("movie10", null);
			}
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
		rd.forward(request,response);
	}

}
