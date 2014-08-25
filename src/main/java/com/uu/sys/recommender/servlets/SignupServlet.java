package com.uu.sys.recommender.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uu.sys.recommender.doa.SignupDao;

/**
 * Servlet implementation class Signup
 */
public class SignupServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		String u=request.getParameter("username");
		String p=request.getParameter("userpass"); 
		String p2=request.getParameter("userpass2"); 
		
		HttpSession session = request.getSession(false);
		
		if (p.equals(p2))
		{
			if(SignupDao.doSignup(u, p))
			{
				session.setAttribute("SignupSuccess","<div style=\"color:blue\">Sign up successful! Please login now</div>");
				
				RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}
			else
			{
				session.setAttribute("SignupFailed","<div style=\"color:red\">Sign up failed! Try again or contact Administrator</div>");
				
				RequestDispatcher rd=request.getRequestDispatcher("signup.jsp");
				rd.forward(request,response);
			}
		}
		else
		{
			session.setAttribute("passwordMismatch","<div style=\"color:red\">Password Mismatch! Please check and try again</div>");
			
			RequestDispatcher rd=request.getRequestDispatcher("signup.jsp");
			rd.forward(request,response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
	}

}
