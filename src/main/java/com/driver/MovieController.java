package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class MovieController {
    HashMap<String,Movie> allmovie=new HashMap<>();
    HashMap<String,Director> allDirecter=new HashMap<>();
    HashMap<String,String> forupdate=new HashMap<>();
    HashMap<String,ArrayList<String>> pairs=new HashMap<>();
    ArrayList<Movie> allMoviein=new ArrayList<>();

    // travers all movies
    @GetMapping("/movies/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies(){
        List<String> nameofMovies=new ArrayList<String>();
     for(String moviename: allmovie.keySet()){
         nameofMovies.add(moviename);
     }
        return  new ResponseEntity<>(nameofMovies, HttpStatus.CREATED);
    }

    @GetMapping("/movies/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name){
        Movie search=null;
        if(allmovie.containsKey(name))
            search=allmovie.get(name);
        return new ResponseEntity<>(search, HttpStatus.CREATED);
    }

    @GetMapping("/movies/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name){
        Director search_director=null;
        if(allDirecter.containsKey(name))
            search_director=allDirecter.get(name);
        return new ResponseEntity<>(search_director, HttpStatus.CREATED);
    }

    @GetMapping("/movies/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String director){
        List<String> listofMovies=new ArrayList<String>();
        if(pairs.containsKey(director))
            return  new ResponseEntity<>(pairs.get(director), HttpStatus.CREATED);
        return  new ResponseEntity<>(listofMovies, HttpStatus.CREATED);
    }


    @PostMapping("/movies/add-movie")
    public String addMovie(@RequestBody Movie movie){
        allmovie.put(movie.getName(),movie);
        return  "success";
    }
    @PostMapping("/movies/add-director")
    public String addDirector(@RequestBody Director director){
        allDirecter.put(director.getName(),director);
        return "success";
    }


    @PutMapping("/movies/add-movie-director-pair/{movie_name}/{director_name}")
    public String addMovieDirectorPair(@PathVariable String movie_name,@PathVariable String director_name){

        forupdate.put(director_name,movie_name);

        if(pairs.containsKey(director_name))
            pairs.get(director_name).add(movie_name);
        else{
            ArrayList<String> temp=new ArrayList<>();
            temp.add(movie_name);
            pairs.put(director_name,temp);
        }
        return "success";
    }

    @DeleteMapping("/movies/delete-director-by-name/{director}")
    public String deleteDirectorByName(@PathVariable String director){

        if(forupdate.containsKey(director)){
            if(allmovie.containsKey(forupdate.get(director)))
              allmovie.remove(forupdate.get(director));
            forupdate.remove(director);
        }
        if(allDirecter.containsKey(director)){
            allDirecter.remove(director);
        }

          return "sucess";
    }

    @DeleteMapping("/movies/delete-all-directors")
    public String deleteAllDirectors(){
      for(List<String> temp: pairs.values()){
          for(int i=0;i<temp.size();i++) {
              if (allmovie.containsKey(temp.get(i)))
                  allmovie.remove(temp.get(i));
          }
      }
        pairs.clear();
        allDirecter.clear();
      return "sucess";
    }

}
