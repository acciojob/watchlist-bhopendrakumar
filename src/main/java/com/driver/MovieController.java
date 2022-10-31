package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("movies")
@RestController
public class MovieController {
    HashMap<String,Movie> allmovie=new HashMap<>();
    HashMap<String,Director> allDirecter=new HashMap<>();
    HashMap<String,List<String>> pairs=new HashMap<>();


    // travers all movies
    @GetMapping("/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies(){
        List<String> nameofMovies=new ArrayList<String>();
     for(String moviename: allmovie.keySet()){
         nameofMovies.add(moviename);
     }
        return  new ResponseEntity<>(nameofMovies, HttpStatus.CREATED);
    }

    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name){
        Movie search=null;
        if(allmovie.containsKey(name))
            search=allmovie.get(name);
        return new ResponseEntity<>(search, HttpStatus.CREATED);
    }

    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name){
        Director search_director=null;
        if(allDirecter.containsKey(name))
            search_director=allDirecter.get(name);
        return new ResponseEntity<>(search_director, HttpStatus.CREATED);
    }

    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String director){
        List<String> listofMovies=new ArrayList<String>();
        if(pairs.containsKey(director))
            return  new ResponseEntity<>(pairs.get(director), HttpStatus.CREATED);
        return  new ResponseEntity<>(listofMovies, HttpStatus.CREATED);
    }


    @PostMapping("/add-movie")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie){
        allmovie.put(movie.getName(),movie);
        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }
    @PostMapping("/add-director")
    public ResponseEntity<String> addDirector(@RequestBody Director director){
        allDirecter.put(director.getName(),director);
        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }


    @PutMapping("/add-movie-director-pair/{movie_name}/{director_name}")
    public ResponseEntity<String> addMovieDirectorPair(@PathVariable String movie_name,@PathVariable String director_name){
   if(allmovie.containsKey(movie_name) && allDirecter.containsKey(director_name)){
       List<String> cur_movie=new ArrayList<>();
       if(pairs.containsKey(director_name))
           cur_movie=pairs.get(director_name);
           cur_movie.add(movie_name);
            pairs.put(director_name,cur_movie);
        }
        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-director-by-name/{director}")
    public ResponseEntity<String> deleteDirectorByName(@PathVariable String director){
        List<String> movies=new ArrayList<>();
        if(pairs.containsKey(director)){
            movies=pairs.get(director);
            for(String delmovie:movies){
                if(allmovie.containsKey(delmovie)){
                    allmovie.remove(delmovie);
                }
            }
            pairs.remove(director);
        }
        if(allDirecter.containsKey(director))
            allDirecter.containsKey(director);

        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-all-directors")
    public ResponseEntity<String> deleteAllDirectors(){
      for(List<String> temp: pairs.values()){
          for(int i=0;i<temp.size();i++) {
              if (allmovie.containsKey(temp.get(i)))
                  allmovie.remove(temp.get(i));
          }
      }
      allDirecter.clear();
      pairs.clear();
        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }

}
