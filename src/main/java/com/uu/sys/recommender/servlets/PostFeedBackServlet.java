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

import com.uu.sys.recommender.doa.StoreDao;
import com.uu.sys.recommender.movies.Recommender;
import com.uu.sys.recommender.movies.Updater;

/**
 * Servlet implementation class PostFeedBackServlet
 */
public class PostFeedBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		String submit=request.getParameter("submit");
		HttpSession session = request.getSession(false);
	
		if(session!=null)
		{
			
			if (submit.equals("Rate it!"))
			{
				String rating_str=(String) (request.getParameter("rating")==null?0:request.getParameter("rating")); 
				double rating = Double.valueOf(rating_str);
				
				Long currentMovie = session.getAttribute("currentMovie")==null?0:(Long)session.getAttribute("currentMovie");
				@SuppressWarnings("unchecked")
				ArrayList<Long> sortedItemIDs = (ArrayList<Long>) (session.getAttribute("sortedItemIDs")==null?2:(ArrayList<Long>)session.getAttribute("sortedItemIDs"));
				@SuppressWarnings("unchecked")
				ArrayList<Long> itemIDs = (ArrayList<Long>) (session.getAttribute("itemIDs")==null?2:(ArrayList<Long>)session.getAttribute("itemIDs"));
				@SuppressWarnings("unchecked")
				ArrayList<Long> skippedItems = (ArrayList<Long>) (session.getAttribute("skippedItems")==null?0:(ArrayList<Long>)session.getAttribute("skippedItems"));
				
				
				int currentIndex = sortedItemIDs.indexOf(currentMovie);
				
				if (currentIndex<=(sortedItemIDs.size()-2))
				{
					/*
					 * re-initialize sortedItemID list
					 */
					sortedItemIDs = new ArrayList<Long>();
					
					/*
					 * Update all variables
					 */
					Matrix uVector = (Matrix) session.getAttribute("uVector");
					Matrix pVector = (Matrix) session.getAttribute("pVector");
					Matrix fVector = new DenseMatrix(itemIDs.size(),1);
					fVector.assign(0); //Initialize the feedback vector
					Matrix W = (Matrix) session.getAttribute("W");
					String uId = (String)session.getAttribute("userId");
					
					Updater updater = new Updater(Long.parseLong(uId),itemIDs,itemIDs.indexOf(currentMovie));
					
					uVector.set(itemIDs.indexOf(currentMovie),0,(double)rating/updater.MAX_RATING); //Update the user side information Vector 
					fVector.set(itemIDs.indexOf(currentMovie),0,(double)1); 
					
					System.out.println("Updating variables in PostFeedbackServlet...");
					W.assign(updater.updateVariables(W, fVector, pVector, uVector,rating));//Update/Store W, store uVector
					System.out.println("Updating done!");
					/*
					 * Create new recommendation list, remove items already rated by user 
					 * The variable sortedItemIDs contains sorted list of items/recommendations unrated by user
					 */
					System.out.println("Get recommendations in PostFeedbackServlet...");
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
								System.out.println("Rated by User"+uId+": "+recList.get(i)+","+uVector.get(itemIDs.indexOf(recList.get(i)),0));
								
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
					System.out.println("Recommendations done...");
					
					/*
					 * Update p Vector
					 */
					pVector.assign(movieRecommender.getpVector());
					
					session.setAttribute("sortedItemIDs", sortedItemIDs);
					session.setAttribute("W", W);
					session.setAttribute("uVector", uVector);
					session.setAttribute("pVector", pVector);
					session.setAttribute("currentRating", 0);
					session.setAttribute("numOfMoviesRated", numOfMoviesRated);
					
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
				else
				{
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
				
				RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
				rd.forward(request,response);
			}
			else
			if(submit.equals("Submit Quality Level!"))
			{
				String rating_str=(String) (request.getParameter("recommenderRating")==null?0:request.getParameter("recommenderRating")); 
				Integer recommenderRating = Integer.valueOf(rating_str);
				
				String uId = (String)session.getAttribute("userId");
				
				session.setAttribute("recommenderRating", recommenderRating);
				
				//Store recommender rating
				StoreDao.storeRecommenderRating(Long.parseLong(uId), recommenderRating);
				
				System.out.println("Recommender rating "+session.getAttribute("recommenderRating"));
				
				RequestDispatcher rd=request.getRequestDispatcher("recommender1.jsp");
				rd.forward(request,response);
			}
			if(submit.equals("Reset"))
			{
				session.setAttribute("rating",0);
				System.out.println("Rating for movie "+session.getAttribute("currentMovie")+":" + session.getAttribute("rating"));
			
				RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
				rd.forward(request,response);
			}
			else
			if(submit.equals("Reset Level"))
			{	
				session.setAttribute("recommenderRating",0);
				System.out.println("Recommender rating "+session.getAttribute("recommenderRating"));
			
				RequestDispatcher rd=request.getRequestDispatcher("recommender1.jsp");
				rd.forward(request,response);
			}
					
		}
		
		System.out.println("PostFeedBackServlet");
		
	}

}
