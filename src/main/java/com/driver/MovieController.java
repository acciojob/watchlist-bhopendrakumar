package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class MovieController {
    HashMap<String,Movie> allmovie=new HashMap<>();
    HashMap<String,Director> allDirecter=new HashMap<>();
    HashMap<String,ArrayList<String>> pairs=new HashMap<>();
    ArrayList<Movie> allMoviein=new ArrayList<>();

    // travers all movies
    @GetMapping("/movies/get-all-movies")
    public ArrayList<String> findAllMovies(){
        ArrayList<String> nameofMovies=new ArrayList<String>();
     for(String moviename: allmovie.keySet()){
         nameofMovies.add(moviename);
     }
        return nameofMovies;
    }

    @GetMapping("/movies/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name){
        Movie search=null;
        if(allmovie.containsKey(name))
            search=allmovie.get(name);
        return new ResponseEntity<>(search, HttpStatus.ACCEPTED);
    }

    @GetMapping("/movies/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name){
        Director search_director=null;
        if(allDirecter.containsKey(name))
            search_director=allDirecter.get(name);
        return new ResponseEntity<>(search_director, HttpStatus.ACCEPTED);
    }

    @GetMapping("/movies/get-movies-by-director-name/{director}")
    public ArrayList<String> getMoviesByDirectorName(@PathVariable String director){
        ArrayList<String> listofMovies=new ArrayList<String>();
        if(pairs.containsKey(director))
            return pairs.get(director);
        return listofMovies;
    }


    @PostMapping("/movies/add-movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        allmovie.put(movie.getName(),movie);
        return  new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    @PostMapping("/movies/add-director")
    public ResponseEntity<Director> addDirector(@RequestBody Director director){
        allDirecter.put(director.getName(),director);
        return new ResponseEntity<>(director, HttpStatus.CREATED);
    }

    @PutMapping("/movies/add-movie-director-pair/{movie_name}/{director_name}")
    public String addMovieDirectorPair(@PathVariable String movie_name,@PathVariable String director_name){
        if(pairs.containsKey(director_name))
            pairs.get(director_name).add(movie_name);
        else{
            ArrayList<String> temp=new ArrayList<>();
            temp.add(movie_name);
            pairs.put(director_name,temp);
        }
        return "sucess";
    }

    @DeleteMapping("/movies/delete-director-by-name/{director}")
    public String deleteDirectorByName(@PathVariable String director){
        ArrayList<String> deletingmovies=new ArrayList<>();
        if(pairs.containsKey(director)) {
            deletingmovies=pairs.get(director);
            pairs.remove(director);

            // if any case we remove it then we want to pull it also
            if(allDirecter.containsKey(director))
                allmovie.remove(director);
        }
        for(int i=0;i<deletingmovies.size();i++){
            if(allmovie.containsKey(deletingmovies.get(i)))
                allmovie.remove(deletingmovies.get(i));
        }
          return "sucess";
    }

    @DeleteMapping("/movies/delete-all-directors")
    public String deleteAllDirectors(){
      for(ArrayList<String> temp: pairs.values()){
          for(int i=0;i<temp.size();i++) {
              if (allmovie.containsKey(temp.get(i)))
                  allmovie.remove(temp.get(i));
          }
      }
        pairs=new HashMap<>();
        allDirecter=new HashMap<>();
      return "sucess";
    }

}
