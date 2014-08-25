<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.uu.sys.recommender.movies.*" %>
<%@page import="com.google.gson.JsonObject" %>
<%@page import="com.google.gson.JsonElement" %>
<%@page import="java.util.*" %>
<%
   // Direct user to login page if not logged in or session expired
	if (session.getAttribute("userId")==null)
	{   
	   String site = new String("index.jsp");
	   response.sendRedirect(site); 
	}
%>
<%
	//Get movie titles for sidebar, movie id 10000 is a dummy one(does not exist in dataset)
	Long movieId1 = session.getAttribute("currentMovie")==null?10000:(Long)session.getAttribute("currentMovie");
	Long movieId2 = session.getAttribute("movie2")==null?10000:(Long)session.getAttribute("movie2");
	Long movieId3 = session.getAttribute("movie3")==null?10000:(Long)session.getAttribute("movie3");
	Long movieId4 = session.getAttribute("movie4")==null?10000:(Long)session.getAttribute("movie4");
	Long movieId5 = session.getAttribute("movie5")==null?10000:(Long)session.getAttribute("movie5");
	Long movieId6 = session.getAttribute("movie6")==null?10000:(Long)session.getAttribute("movie6");
	Long movieId7 = session.getAttribute("movie7")==null?10000:(Long)session.getAttribute("movie7");
	Long movieId8 = session.getAttribute("movie8")==null?10000:(Long)session.getAttribute("movie8");
	Long movieId9 = session.getAttribute("movie9")==null?10000:(Long)session.getAttribute("movie9");
	Long movieId10 = session.getAttribute("movie10")==null?10000:(Long)session.getAttribute("movie10");

	Movie movie1 = new Movie(); JsonObject movieJsonObj1 = movie1.movieInfo(movieId1);
	Movie movie2 = new Movie(); JsonObject movieJsonObj2 = movie2.movieInfo(movieId2);
	Movie movie3 = new Movie(); JsonObject movieJsonObj3 = movie3.movieInfo(movieId3);
	Movie movie4 = new Movie(); JsonObject movieJsonObj4 = movie4.movieInfo(movieId4);
	Movie movie5 = new Movie(); JsonObject movieJsonObj5 = movie5.movieInfo(movieId5);
	Movie movie6 = new Movie(); JsonObject movieJsonObj6 = movie6.movieInfo(movieId6);
	Movie movie7 = new Movie(); JsonObject movieJsonObj7 = movie7.movieInfo(movieId7);
	Movie movie8 = new Movie(); JsonObject movieJsonObj8 = movie8.movieInfo(movieId8);
	Movie movie9 = new Movie(); JsonObject movieJsonObj9 = movie9.movieInfo(movieId9);
	Movie movie10 = new Movie(); JsonObject movieJsonObj10 = movie10.movieInfo(movieId10);
	
	//Get movie titles
	JsonElement movieTitle1 = movieJsonObj1.get("title");
	JsonElement movieTitle2 = movieJsonObj2.get("title");
	JsonElement movieTitle3 = movieJsonObj3.get("title");
	JsonElement movieTitle4 = movieJsonObj4.get("title");
	JsonElement movieTitle5 = movieJsonObj5.get("title");
	JsonElement movieTitle6 = movieJsonObj6.get("title");
	JsonElement movieTitle7 = movieJsonObj7.get("title");
	JsonElement movieTitle8 = movieJsonObj8.get("title");
	JsonElement movieTitle9 = movieJsonObj9.get("title");
	JsonElement movieTitle10 = movieJsonObj10.get("title");
	
	//Get movie years
	JsonElement movieYear1 = movieJsonObj1.get("year");
	JsonElement movieYear2 = movieJsonObj2.get("year");
	JsonElement movieYear3 = movieJsonObj3.get("year");
	JsonElement movieYear4 = movieJsonObj4.get("year");
	JsonElement movieYear5 = movieJsonObj5.get("year");
	JsonElement movieYear6 = movieJsonObj6.get("year");
	JsonElement movieYear7 = movieJsonObj7.get("year");
	JsonElement movieYear8 = movieJsonObj8.get("year");
	JsonElement movieYear9 = movieJsonObj9.get("year");
	JsonElement movieYear10 = movieJsonObj10.get("year");
	
	String displayTitle1 = new String();
	String displayTitle2 = new String();
	String displayTitle3 = new String();
	String displayTitle4 = new String();
	String displayTitle5 = new String();
	String displayTitle6 = new String();
	String displayTitle7 = new String();
	String displayTitle8 = new String();
	String displayTitle9 = new String();
	String displayTitle10 = new String();

	//Check which titles to display in Side bar
	if(movieId1==10000){displayTitle2="style=\"display: none\"";}
	if(movieId2==10000){displayTitle2="style=\"display: none\"";}
	if(movieId3==10000){displayTitle3="style=\"display: none\"";}
	if(movieId4==10000){displayTitle4="style=\"display: none\"";}
	if(movieId5==10000){displayTitle5="style=\"display: none\"";}
	if(movieId6==10000){displayTitle6="style=\"display: none\"";}
	if(movieId7==10000){displayTitle7="style=\"display: none\"";}
	if(movieId8==10000){displayTitle8="style=\"display: none\"";}
	if(movieId9==10000){displayTitle9="style=\"display: none\"";}
	if(movieId10==10000){displayTitle10="style=\"display: none\"";}
	
	//Keep track of recommender rating value
	Integer r = session.getAttribute("recommenderRating")==null?0:(Integer)session.getAttribute("recommenderRating");
	
	String recommenderRating0= new String("");
	String recommenderRating1= new String("");
	String recommenderRating2= new String("");
	String recommenderRating3= new String("");
	String recommenderRating4= new String("");
	String recommenderRating5= new String("");
	
	if (r==0){recommenderRating0="checked";}
	if (r==1){recommenderRating1="checked";}
	if (r==2){recommenderRating2="checked";}
	if (r==3){recommenderRating3="checked";}
	if (r==4){recommenderRating4="checked";}
	if (r==5){recommenderRating5="checked";}
	
	//Keep track of number of movies rated by user
	Integer numOfMoviesRated = session.getAttribute("numOfMoviesRated")==null?0:(Integer)session.getAttribute("numOfMoviesRated");
	
%>
<html>
<head>
	<link id="theme" rel="stylesheet" type="text/css" href="style.css" title="theme" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Movie Recommender - Recommender</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<link rel="stylesheet" type="text/css" href="js/fancybox/jquery.fancybox-1.2.6.css" media="screen" />
	<script type="text/javascript" src="js/fancybox/jquery.fancybox-1.2.6.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("a.zoom").fancybox();

			$("a.zoom1").fancybox({
				'overlayOpacity'	:	0.7,
				'overlayColor'		:	'#000'
			});

			$("a.zoom2").fancybox({
				'zoomSpeedIn'		:	500,
				'zoomSpeedOut'		:	500
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function () {
		    // Handler for .ready() called.
		    $('html, body').animate({
		        scrollTop: $('#nav1').offset().top
		    }, 'slow');
		});
	</script>
	<style>
		table {
		    *border-collapse: collapse; /* IE7 and lower */
		    border-spacing: 0;
		    width: 100%; 
		    font-family: 'trebuchet MS', 'Lucida sans', Arial;
    		font-size: 14px;
    		color: #444;   
		}
		
		.bordered {
		    border: solid #ccc 1px;
		    -moz-border-radius: 6px;
		    -webkit-border-radius: 6px;
		    border-radius: 6px;
		    -webkit-box-shadow: 0 1px 1px #ccc; 
		    -moz-box-shadow: 0 1px 1px #ccc; 
		    box-shadow: 0 1px 1px #ccc;         
		}
		
		.bordered tr:hover {
		    background: #fbf8e9;
		    -o-transition: all 0.1s ease-in-out;
		    -webkit-transition: all 0.1s ease-in-out;
		    -moz-transition: all 0.1s ease-in-out;
		    -ms-transition: all 0.1s ease-in-out;
		    transition: all 0.1s ease-in-out;     
		}    
		    
		.bordered td, .bordered th {
		    border-left: 1px solid #ccc;
		    border-top: 1px solid #ccc;
		    padding: 10px;
		    text-align: left;    
		}
		
		.bordered th {
		    background-color: #dce9f9;
		    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
		    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
		    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
		    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
		    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
		    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
		    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
		    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
		    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
		    border-top: none;
		    text-shadow: 0 1px 0 rgba(255,255,255,.5); 
		}
		
		.bordered td:first-child, .bordered th:first-child {
		    border-left: none;
		}
		
		.bordered th:first-child {
		    -moz-border-radius: 6px 0 0 0;
		    -webkit-border-radius: 6px 0 0 0;
		    border-radius: 6px 0 0 0;
		}
		
		.bordered th:last-child {
		    -moz-border-radius: 0 6px 0 0;
		    -webkit-border-radius: 0 6px 0 0;
		    border-radius: 0 6px 0 0;
		}
		
		.bordered th:only-child{
		    -moz-border-radius: 6px 6px 0 0;
		    -webkit-border-radius: 6px 6px 0 0;
		    border-radius: 6px 6px 0 0;
		}
		
		.bordered tr:last-child td:first-child {
		    -moz-border-radius: 0 0 0 6px;
		    -webkit-border-radius: 0 0 0 6px;
		    border-radius: 0 0 0 6px;
		}
		
		.bordered tr:last-child td:last-child {
		    -moz-border-radius: 0 0 6px 0;
		    -webkit-border-radius: 0 0 6px 0;
		    border-radius: 0 0 6px 0;
		}
	</style>
</head>
<body>
    <div id="wrapper"> 
      <div id="bg"> 
        <div id="header"></div>  
        <div id="page"> 
          <div id="container" style="min-height:900px"> 
            <!-- horizontal navigation -->  
            <div id="nav1"> 
              <ul>
                <li>
                  <a href="index.jsp">Home</a>
                </li>
                <li id="current" style="border:none">
                  <a href="ParseMoviesServlet?parseMovies=recommendations&offset=0">Your Recommendations</a>
                </li>
                 <li><a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                  <li><a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                 <li><a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                 <li><a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li>
                  <a href="logoutServlet">Logout </a>
                </li>
              </ul> 
            </div>  
            <!-- end horizontal navigation -->  
            <!--  content -->  
            <div id="content"> 
              <div id="center" style="width:700px;padding:10px 20px 20px 90px;"> 
                <div id="welcome">  
                  <h3>Your Predicted Movie Preferences</h3> 
                  <h4>You have rated <b style="color:red"><%=numOfMoviesRated%></b> movies out of a recommended minimum of <b>15</b></h4>
                  <h4>So, based on your ratings, we think you really like the movies below!
                   How much do you agree?<br>(<i style="color:blue">Remember: Being brutally honest is totally cool :) </i>) </h4>
                <form method="post" action="PostFeedBackServlet">
		        Your Perceived Quality of Movie Predictor:
		        	<span class="rating">
					    <input type="radio" name="recommenderRating" value=0 <%=recommenderRating0%>/><span id="hide"></span>
					    <input type="radio" name="recommenderRating" value=1 <%=recommenderRating1%>/><span></span>
					    <input type="radio" name="recommenderRating" value=2 <%=recommenderRating2%>/><span></span>
					    <input type="radio" name="recommenderRating" value=3 <%=recommenderRating3%>/><span></span>
					    <input type="radio" name="recommenderRating" value=4	<%=recommenderRating4%>/><span></span>
					    <input type="radio" name="recommenderRating" value=5 <%=recommenderRating5%>/><span></span>
					</span>
					<input type="submit" name="submit" value="Submit Quality Level!">
					<input type="submit" name="submit" value="Reset Level">
		        </form>
                  
                  <p>*To see a trailer of each movie, click on it. You will 
                  be directed to another page. Hit the <b>back button on your browser</b>
                  to come back here</p>
					<table class="bordered">
					    <thead>
					
					    <tr>
					        <th>#</th>        
					        <th>Predicted Movie Preferences</th>
					    </tr>
					    </thead>
					    <tr>
					        <td>
							1
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle1 %>&year=<%=movieYear1 %>' 
					        		style="text-decoration:none" <%=displayTitle1 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle1%></font>
					        	</a>
					        </td>
					    </tr>        
					    <tr>
					        <td>
							2
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle2 %>&year=<%=movieYear2 %>' 
					        		style="text-decoration:none" <%=displayTitle2 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle2%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							3
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle3 %>&year=<%=movieYear3 %>' 
					        		style="text-decoration:none" <%=displayTitle3 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle3%></font>
					        	</a>
					        </td>
					    </tr>    
					    <tr>
					        <td>
							4
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle4 %>&year=<%=movieYear4 %>' 
					        		style="text-decoration:none" <%=displayTitle4 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle4%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							5
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle5 %>&year=<%=movieYear5 %>' 
					        		style="text-decoration:none" <%=displayTitle5 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle5%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							6
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle6 %>&year=<%=movieYear6 %>' 
					        		style="text-decoration:none" <%=displayTitle6 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle6%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							7
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle7 %>&year=<%=movieYear7 %>' 
					        		style="text-decoration:none" <%=displayTitle7 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle7%></font>
					        	</a>
					        </td>
					    </tr>    
					    <tr>
					        <td>
							8
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle8 %>&year=<%=movieYear8 %>' 
					        		style="text-decoration:none" <%=displayTitle8 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle8%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							9
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle9 %>&year=<%=movieYear9 %>' 
					        		style="text-decoration:none" <%=displayTitle9 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle9%></font>
					        	</a>
					        </td>
					    </tr>
					    <tr>
					        <td>
							10
					        </td>        
					        <td>
					        	<a href='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle10 %>&year=<%=movieYear10 %>' 
					        		style="text-decoration:none" <%=displayTitle10 %>>
					        		<font color="#1E67A8" title="Click to watch trailer"><%=movieTitle10%></font>
					        	</a>
					        </td>
					    </tr> 
					
					</table>  
                </div> 
              </div>  
            </div>  
            <!-- end content --> 
          </div>  
          <!-- end container --> 
        </div>  
        <div id="footerWrapper"> 
          <div id="footer">
            <p style="padding-top:10px"><a href="index.jsp">Master Project: "A Smart Real-time Movie Recommender" done by Jeff Nkandu&copy;2014</a></p>
            <p style="padding-top:10px"> </p> 
          </div> 
        </div> 
      </div> 
    </div> 
</body>
</html>