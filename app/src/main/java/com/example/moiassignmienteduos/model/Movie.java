package com.example.moiassignmienteduos.model;

import kotlin.OverloadResolutionByLambdaReturnType;

//Creating a movie class to create movie objects from the raw api data given to us by the repository class
//here the constructor will set those raw var data from repo class to the vars made below
//and the getters of those vars will allow any class in this package to have access to the data they need (i.e. title, plot etc)
public class Movie {
    //when the user clicks on a movie, the movie's plot, rating (rotton tomatoes), language, year, and Poster should be displayed
    //all the relevant info We could think of.
    private String title;
    private String year;
    private String imdbID;
    private String poster;
    private String plot;
    private String rating;
    private String language;

    //creating a constructor that will be used by the repo class to pass the appropriate json data into the movie class to create the corresponding movie obj)
    public Movie(String titleParam, String yearParam, String posterParam, String plotParam, String ratingParam, String languageParam){
        this.title = titleParam;
        this.year = yearParam;
        this.poster = posterParam;
        this.plot = plotParam;
        this.rating = ratingParam;
        this.language = languageParam;
    }

    //overloading constructor for the searchMovie Constructor
    public Movie(String titleParam, String yearParam, String posterParam, String imdbIdParam){
        this.title = titleParam;
        this.year = yearParam;
        this.poster = posterParam;
        this.imdbID = imdbIdParam;
    }

    //Apparently need this empty constructor for pulling Movie data from DB
    //Something to do with serialization/deserialization
    public Movie() {

    }

    //generating getters for each attribute
    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }

    public  String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String id) {
        this.imdbID = id;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }
}


