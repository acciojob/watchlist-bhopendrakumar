package com.driver;

public class Movie {

    private String name;
    private int durationInMinutes;
    private double imdbRating;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Movie(String name,int durationInMinutes,double imdbRating){
        this.name=name;
        this.durationInMinutes=durationInMinutes;
        this.imdbRating=imdbRating;
    }
}
