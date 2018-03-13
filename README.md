The Movie Recommender web application
=====================================

This project was developed as part of my master thesis project work. 

The main aim of the movie recommender web application was to test the applicability of the adaptive
predictor algorithm in a real recommender system environment on actual users. Input from users was 
analyzed and the results where included in the final thesis report which was published here:
http://uu.diva-portal.org/smash/get/diva2:758781/FULLTEXT01.pdf.

This web app was build on top of a console application and used the following web technology:
HTML/CSS/JQuery/JSP on the client-side and Java Servlets on the server side. Servlets invoke
classes from the console application. User information, as well as the collected feedback, is stored in a MySQL database.

The web application collects two kinds of user feedback. These are:
	1. ratings of recommended movies, and
	2. rating of the overall quality of the recommendations provided by the system
As such the system has two use-cases:
	1. Movie rating
	2. System quality rating
	
The user is asked to rate at least 15 movies. The list of movies presented to the user is updated
using the adaptive predictor every time they rate a movie. This is so because
the adaptive predictor scheme is designed to compute recommendations online
and as such requires updating every time user feedback is received.

The web-based movie recommender is hosted at the following URL: [http://thesisapp.herokuapp.com/, online as of 2016-11-21].
All the source code has been published to GitHub [https://github.com/KundaJ/web-movie-rec.git].

I will continue to push new commits as, and when, I have time as a hobby project (recommender systems are cool!) and 
because I feel I can evolve this project a lot, e.g. 
	<ul><li>1. improve the algorithm to incorporate latent features (age, sex, words in movie synopsis etc.)</li>
	<li>2. improve computation by distributing math operations with Apache Hadoop/Spark</li>
	<li>3. improve scalability</li></ul>
