package com.example.apitest.Controller;




import com.example.apitest.Exception.MovieNotFoundException;
import com.example.apitest.Service.MovieService;
import com.example.apitest.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class ApiController {

    @Autowired
    private MovieService movieService;

    // 根据 imdbID 获取电影信息
    @GetMapping("/{imdbID}")
    public Movie getMovieByImdbID(@PathVariable String imdbID) {
        Movie movie = movieService.getMovieByImdbID(imdbID);
        if (movie != null) {
            return movie;
        } else {
            throw new MovieNotFoundException("Movie not found with imdbID: " + imdbID);
        }
    }

    // 获取所有电影数据
    @GetMapping("/showall")
    public List<Movie> showAllMovies() {
        return movieService.getAllMovies();
    }

    // 根据 Title 获取电影信息
    @GetMapping("/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable String title) {
        List<Movie> movies = movieService.getMoviesByTitle(title);
        if (!movies.isEmpty()) {
            return movies;
        } else {
            throw new MovieNotFoundException("No movies found with title: " + title);
        }
    }
}
