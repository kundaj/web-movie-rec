package com.uu.sys.recommender.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uu.sys.recommender.doa.StoreDao;


/**
 * Servlet implementation class ParseMoviesServlet
 */
public class ParseMoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,IOException 
	{
		System.out.println("****ParseMoviesServlet Executed***");
		
		response.setContentType("text/html");
		String parseMovies=request.getParameter("parseMovies");
		
		HttpSession session = request.getSession(false);
	
		if(session!=null)
		{
			String uID = (String) session.getAttribute("userId");
			Long userID = Long.parseLong(uID);
			
			//Matrix uVector = (Matrix) session.getAttribute("uVector");
			Long currentMovie = session.getAttribute("currentMovie")==null?10000:(Long)session.getAttribute("currentMovie");
			@SuppressWarnings("unchecked")
			ArrayList<Long> sortedItemIDs = (ArrayList<Long>) (session.getAttribute("sortedItemIDs")==null?0:(ArrayList<Long>)session.getAttribute("sortedItemIDs"));
			@SuppressWarnings("unchecked")
			ArrayList<Long> skippedItems = (ArrayList<Long>) (session.getAttribute("skippedItems")==null?0:(ArrayList<Long>)session.getAttribute("skippedItems"));
			
			int currentIndex = sortedItemIDs.indexOf(currentMovie); //Current movie index
			Integer offset = Integer.parseInt(request.getParameter("offset"));
			
			if (offset.equals(0))// If user clicks "Next" button
				offset=1;
			
			if (parseMovies.equals("next") && currentIndex<=(sortedItemIDs.size()-2))
			{
				// Add current movie to list of skipped items
				skippedItems.add(currentMovie);
				session.setAttribute("skippedItems",skippedItems);
				StoreDao.storeSkippedItems(userID, currentMovie);
				
				//put sorted Item list in session variables
				try
				{
					session.setAttribute("currentMovie", sortedItemIDs.get(currentIndex+offset).longValue()); 
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("currentMovie", null);
				}	
				try
				{
					session.setAttribute("movie2", sortedItemIDs.get(currentIndex+offset+1).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie2", null);
				}
				try
				{
					session.setAttribute("movie3", sortedItemIDs.get(currentIndex+offset+2).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie3", null);
				}
				try
				{
					session.setAttribute("movie4", sortedItemIDs.get(currentIndex+offset+3).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie4", null);
				}
				try
				{
					session.setAttribute("movie5", sortedItemIDs.get(currentIndex+offset+4).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie5", null);
				}
				try
				{
					session.setAttribute("movie6", sortedItemIDs.get(currentIndex+offset+5).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie6", null);
				}
				try
				{
					session.setAttribute("movie7", sortedItemIDs.get(currentIndex+offset+6).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie7", null);
				}
				try
				{
					session.setAttribute("movie8", sortedItemIDs.get(currentIndex+offset+7).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie8", null);
				}
				try
				{
					session.setAttribute("movie9", sortedItemIDs.get(currentIndex+offset+8).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie9", null);
				}
				try
				{
					session.setAttribute("movie10", sortedItemIDs.get(currentIndex+offset+9).longValue());
				}catch(IndexOutOfBoundsException e)
				{
					session.setAttribute("movie10", null);
				}
				
				//Remove skipped item from sortedItemIDs list
				sortedItemIDs.remove(currentMovie);
				session.setAttribute("sortedItemIDs",sortedItemIDs);
			}
			else
			{   
				/*
				 *  Items on recommender.jsp are also allocated here
				 */
				
				//System.out.println("End of recommendation list reached");
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
		}
		
		if(parseMovies.equals("recommendations"))
		{
			RequestDispatcher rd=request.getRequestDispatcher("recommender1.jsp");
			rd.forward(request,response);
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
			rd.forward(request,response);
		}
	}
}
