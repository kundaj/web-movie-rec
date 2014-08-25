package com.uu.sys.recommender.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uu.sys.recommender.doa.LoginDao;



public class LoginServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);	
		
		session.setAttribute("loginError","");
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.include(request,response);
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		String n=request.getParameter("username");
		String p=request.getParameter("userpass"); 

		HttpSession session = request.getSession(false);

		if(LoginDao.validate(n, p))
		{
			if(session!=null)
			{
				session.setAttribute("userName", n);
				session.setAttribute("userId", LoginDao.userId);
			}
			
			System.out.println("Login...");
			
			RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");
			rd.forward(request,response);
		}
		else
		{
			if(session!=null)
				session.setAttribute("loginError","<p style=\"color:red\">Sorry username or password my be incorrect</p>");
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.include(request,response);
		} 
	}
}