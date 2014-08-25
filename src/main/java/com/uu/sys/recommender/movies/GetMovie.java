package com.uu.sys.recommender.movies;

import com.google.gson.JsonElement;

public class GetMovie 
{
	private JsonElement title;
	private JsonElement year;
	private JsonElement synopsis;
	private JsonElement poster;

	public JsonElement getYear() {
		return year;
	}

	public void setYear(JsonElement year) {
		this.year = year;
	}

	public JsonElement getTitle() {
		return title;
	}

	public void setTitle(JsonElement title) {
		this.title = title;
	}

	public JsonElement getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(JsonElement synopsis) {
		this.synopsis = synopsis;
	}

	public JsonElement getPoster() {
		return poster;
	}

	public void setPoster(JsonElement poster) {
		this.poster = poster;
	}
}
