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
	//Get sorted recommendations from servlet if that has not been done
	if (session.getAttribute("sortedItemIDs")==null)
	{
	   String site = new String("GetMoviesServlet");
	   response.sendRedirect(site); 
	}

%>
<%
	//Get current movie to recommend to user
	Long movieId = session.getAttribute("currentMovie")==null?10000:(Long)session.getAttribute("currentMovie");

	Movie movie1 = new Movie(); JsonObject movieJsonObj1 = movie1.movieInfo(movieId);
	JsonElement movieTitle1 = movieJsonObj1.get("title");
	JsonElement movieYear1 = movieJsonObj1.get("year");
	JsonElement movieSynopsis1 = movieJsonObj1.get("synopsis");
	//JsonObject postersJsonObj = movieJsonObj.getAsJsonObject("posters");
	
	String skip="Skip";
	String back="&lt;&lt; Back";
%>
<%
	//Get movie titles for sidebar, movie id 10000 is a dummy one(does not exist in dataset)
	Long movieId2 = session.getAttribute("movie2")==null?10000:(Long)session.getAttribute("movie2");
	Long movieId3 = session.getAttribute("movie3")==null?10000:(Long)session.getAttribute("movie3");
	Long movieId4 = session.getAttribute("movie4")==null?10000:(Long)session.getAttribute("movie4");
	Long movieId5 = session.getAttribute("movie5")==null?10000:(Long)session.getAttribute("movie5");
	Long movieId6 = session.getAttribute("movie6")==null?10000:(Long)session.getAttribute("movie6");
	Long movieId7 = session.getAttribute("movie7")==null?10000:(Long)session.getAttribute("movie7");
	Long movieId8 = session.getAttribute("movie8")==null?10000:(Long)session.getAttribute("movie8");
	Long movieId9 = session.getAttribute("movie9")==null?10000:(Long)session.getAttribute("movie9");
	Long movieId10 = session.getAttribute("movie10")==null?10000:(Long)session.getAttribute("movie10");

	Movie movie2 = new Movie(); JsonObject movieJsonObj2 = movie2.movieInfo(movieId2);
	Movie movie3 = new Movie(); JsonObject movieJsonObj3 = movie3.movieInfo(movieId3);
	Movie movie4 = new Movie(); JsonObject movieJsonObj4 = movie4.movieInfo(movieId4);
	Movie movie5 = new Movie(); JsonObject movieJsonObj5 = movie5.movieInfo(movieId5);
	Movie movie6 = new Movie(); JsonObject movieJsonObj6 = movie6.movieInfo(movieId6);
	Movie movie7 = new Movie(); JsonObject movieJsonObj7 = movie7.movieInfo(movieId7);
	Movie movie8 = new Movie(); JsonObject movieJsonObj8 = movie8.movieInfo(movieId8);
	Movie movie9 = new Movie(); JsonObject movieJsonObj9 = movie9.movieInfo(movieId9);
	Movie movie10 = new Movie(); JsonObject movieJsonObj10 = movie10.movieInfo(movieId10);
	
	//Get movie titles for sidebar
	JsonElement movieTitle2 = movieJsonObj2.get("title");
	JsonElement movieTitle3 = movieJsonObj3.get("title");
	JsonElement movieTitle4 = movieJsonObj4.get("title");
	JsonElement movieTitle5 = movieJsonObj5.get("title");
	JsonElement movieTitle6 = movieJsonObj6.get("title");
	JsonElement movieTitle7 = movieJsonObj7.get("title");
	JsonElement movieTitle8 = movieJsonObj8.get("title");
	JsonElement movieTitle9 = movieJsonObj9.get("title");
	JsonElement movieTitle10 = movieJsonObj10.get("title");
%>
<%
	//Keep track of ratings
	Integer rating_str = session.getAttribute("currentRating")==null?0:(Integer)session.getAttribute("currentRating");
	Integer r = Integer.valueOf(rating_str);
	
	//Keep track of number of movies rated by user
	Integer numOfMoviesRated = session.getAttribute("numOfMoviesRated")==null?0:(Integer)session.getAttribute("numOfMoviesRated");
	
	String rating0= new String("");
	String rating1= new String("");
	String rating2= new String("");
	String rating3= new String("");
	String rating4= new String("");
	String rating5= new String("");
	
	if (r==0){rating0="checked";}
	if (r==1){rating1="checked";}
	if (r==2){rating2="checked";}
	if (r==3){rating3="checked";}
	if (r==4){rating4="checked";}
	if (r==5){rating5="checked";}
%>
<%
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
	if(movieId2==10000){displayTitle2="style=\"display: none\"";}
	if(movieId3==10000){displayTitle3="style=\"display: none\"";}
	if(movieId4==10000){displayTitle4="style=\"display: none\"";}
	if(movieId5==10000){displayTitle5="style=\"display: none\"";}
	if(movieId6==10000){displayTitle6="style=\"display: none\"";}
	if(movieId7==10000){displayTitle7="style=\"display: none\"";}
	if(movieId8==10000){displayTitle8="style=\"display: none\"";}
	if(movieId9==10000){displayTitle9="style=\"display: none\"";}
	if(movieId10==10000){displayTitle10="style=\"display: none\"";}
%>
<html>
<head>
	<link id="theme" rel="stylesheet" type="text/css" href="style.css" title="theme" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Movie Recommender - Welcome</title>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
	<script type="text/javascript">
	$('.rating input[type="radio"]').hover(function () {
		    $(this).nextAll('span').removeClass().addClass('jshoverNext');
		    $(this).next('span').removeClass().addClass('jshover');
		    $(this).prevAll('span').removeClass().addClass('jshover');
		}, function () {
		    $('.rating input[type="radio"] + span').removeClass();
		});
	
	$(document).ready(function () {
	    // Handler for .ready() called.
	    $('html, body').animate({
	        scrollTop: $('#nav1').offset().top
	    }, 'slow');
	});
	</script>
	
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
                <li id="current" style="border:none">
                  <a href="index.jsp">Home</a>
                </li>
                <li>
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
              <div id="center"> 
                <div id="welcome"> 
                  <h4>You have rated <b style="color:red"><%=numOfMoviesRated%></b> movies out of a recommended minimum of <b>15</b></h4>
                  <p>Like the movie below? Rate it!. If you don't like it,
                    <a href="ParseMoviesServlet?parseMovies=next&offset=0">Skip</a> to the next movie, or check out movies on the right-hand side of this page! 
                  </p> 
        <h3><%=movieTitle1 %>
        	(<%=movieYear1 %>)
		</h3>
        <form method="post" action="PostFeedBackServlet">
        <!-- <a href="ParseMoviesServlet?parseMovies=back"><%=back %></a>&nbsp; -->
        <b><a href="ParseMoviesServlet?parseMovies=next&offset=0"><%=skip %></a> </b> &gt;&gt; 
        &nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;
        Your Rating:
        	<span class="rating">
			    <input type="radio" name="rating" value=0 <%=rating0%>/><span id="hide"></span>
			    <input type="radio" name="rating" value=1 <%=rating1%>/><span></span>
			    <input type="radio" name="rating" value=2 <%=rating2%>/><span></span>
			    <input type="radio" name="rating" value=3 <%=rating3%>/><span></span>
			    <input type="radio" name="rating" value=4	<%=rating4%>/><span></span>
			    <input type="radio" name="rating" value=5 <%=rating5%>/><span></span>
			</span>
			<input type="submit" name="submit" value="Rate it!">
			<input type="submit" name="submit" value="Reset">
        </form>
        <div>
        	<iframe style="width:100%;height:59vh;" frameBorder="0" Border="0" frameSpacing="0" scrolling="no"
        	src='http://quiet-coast-7194.herokuapp.com/movietrailer.php?movie=<%=movieTitle1 %>&year=<%=movieYear1 %>'> </iframe>
    	</div>
          <b>Synopsis</b> : <%=movieSynopsis1 %><br>  
                  <p style="clear:both" />        
                </div> 
              </div>  
              <div id="right" style="width:230px;padding:10px 10px 20px 0px;"> 
                <div id="sidebar"> 
                  <h4>Your full Recommendation List:</h4> 
                  <h5 style="color:blue">Like any movie in the list below? Click on it to see the trailer and rate it</h5>  
                  <ul class="vmenu">
                    <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=1" <%=displayTitle2 %>><%=movieTitle2%></a>
                    </li>
                    <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=2" <%=displayTitle3 %>><%=movieTitle3 %></a>
                    </li>
                    <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=3"<%=displayTitle4 %>><%=movieTitle4 %></a>
                    </li>
                    <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=4"<%=displayTitle5 %>><%=movieTitle5 %></a>
                    </li>
                    <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=5"<%=displayTitle6 %>><%=movieTitle6 %></a>
                    </li>    
                                        <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=6"<%=displayTitle7 %>><%=movieTitle7 %></a>
                    </li> 
                                        <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=7"<%=displayTitle8 %>><%=movieTitle8 %></a>
                    </li> 
                                        <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=8"<%=displayTitle9 %>><%=movieTitle9 %></a>
                    </li> 
                                        <li>
                      <a href="ParseMoviesServlet?parseMovies=next&offset=9"<%=displayTitle10 %>><%=movieTitle10 %></a>
                    </li>               
                  </ul>  
                  <h3 style="margin-top:20px">Instructions</h3>
                  <div style="text-align:justify">  
			<b>Your Feedback is Important!</b> Once logged in, you will be presented with movies to 
			   rate. Please rate at least 15 movies. Next, click on 
			   <a href="ParseMoviesServlet?parseMovies=recommendations&offset=0">Your Recommendations</a> to check your 
			   <b>movie recommendations</b> calculated by the system. Take time to review these 
				   predictions and then rate how relevant they are to you. Done!
			   </div>
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