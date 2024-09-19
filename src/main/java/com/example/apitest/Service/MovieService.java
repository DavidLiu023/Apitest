package com.example.apitest.Service;




import com.example.apitest.model.ApiResponse;
import com.example.apitest.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private static final String URL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=waterworld";

    private List<Movie> movies;

    public MovieService() {
        fetchMovies();
    }

    // 从外部 API 获取电影数据
    private void fetchMovies() {
        RestTemplate restTemplate = new RestTemplate();
        List<Movie> allMovies = new ArrayList<>();

        // 获取第一页的数据
        ApiResponse response = restTemplate.getForObject(URL, ApiResponse.class);
        if (response != null) {
            allMovies.addAll(response.getData());

            int totalPages = response.getTotalPages();

            // 如果有多页，循环获取
            for (int page = 2; page <= totalPages; page++) {
                String pagedUrl = URL + "&page=" + page;
                ApiResponse pagedResponse = restTemplate.getForObject(pagedUrl, ApiResponse.class);
                if (pagedResponse != null) {
                    allMovies.addAll(pagedResponse.getData());
                }
            }
        }

        this.movies = allMovies;
    }

    // 获取所有电影
    public List<Movie> getAllMovies() {
        return movies;
    }

    // 根据 imdbID 查找电影
    public Movie getMovieByImdbID(String imdbID) {
        return movies.stream()
                .filter(movie -> movie.getImdbID().equalsIgnoreCase(imdbID))
                .findFirst()
                .orElse(null);
    }

    // 根据 Title 查找电影
    public List<Movie> getMoviesByTitle(String title) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                result.add(movie);
            }
        }
        return result;
    }
}
