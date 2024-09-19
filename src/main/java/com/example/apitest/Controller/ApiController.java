package com.example.apitest.Controller;

import com.example.apitest.Service.MovieService;
import com.example.apitest.model.ApiResponse;
import com.example.apitest.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public ApiResponse getMovies() {
        ApiResponse response = movieService.fetchMovies();

        // 去掉 page 字段，可以设置为 null，或者在返回前创建一个新的响应对象，不包含 page
        // 这里假设不返回 page 字段，因此在 ApiResponse 类中已去掉 page 字段

        return response;
    }
}
