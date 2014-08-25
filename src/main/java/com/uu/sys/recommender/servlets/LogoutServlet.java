package com.uu.sys.recommender.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		
		if(session!=null)
		{
			session.setAttribute("userName", null); //Log user out
			session.setAttribute("userId", null);
			session.setAttribute("sortedItemIDs", null);
			session.setAttribute("logoutConfirm","<div style=\"color:blue\">You have been logged out. Thank you for your time!</div>");
			session.setAttribute("W", null);
			session.setAttribute("uVector", null);
			session.setAttribute("pVector", null);
			session.setAttribute("currentRating", null);
			session.setAttribute("numOfMoviesRated", null);
			session.setAttribute("recommenderRating",0);
			session.setAttribute("SignupSuccess",null);
			session.setAttribute("SignupFailed",null);
			session.setAttribute("skippedItems", null);
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
	}

}
