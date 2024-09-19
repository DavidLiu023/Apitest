package com.example.apitest.Service;




import com.example.apitest.model.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private static final String URL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=waterworld";

    public ApiResponse fetchMovies() {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.getForObject(URL, ApiResponse.class);
        return response;
    }
}
