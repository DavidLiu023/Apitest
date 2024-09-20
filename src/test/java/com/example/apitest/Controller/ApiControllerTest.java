package com.example.apitest.Controller;


import com.example.apitest.Service.MovieService;
import com.example.apitest.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 注意包名应与您的项目结构一致

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testGetMovieByImdbID() throws Exception {
        // 准备测试数据
        Movie movie = new Movie();
        movie.setTitle("Waterworld");
        movie.setYear(1995);
        movie.setImdbID("tt0114898");


        // Mock 行为
        when(movieService.getMovieByImdbID("tt0114898")).thenReturn(movie);

        // 发送 GET 请求并验证响应
        mockMvc.perform(get("/movies/tt0114898"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Waterworld"))
                .andExpect(jsonPath("$.year").value("1995"))
                .andExpect(jsonPath("$.imdbID").value("tt0114898"))
                .andExpect(jsonPath("$.type").value("movie"))
                .andExpect(jsonPath("$.poster").value("N/A"));
    }

    @Test
    public void testGetMovieByImdbID_NotFound() throws Exception {
        // Mock 行为，返回 null 表示未找到
        when(movieService.getMovieByImdbID("tt0000000")).thenReturn(null);

        // 发送 GET 请求并验证响应
        mockMvc.perform(get("/movies/tt0000000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testShowAllMovies() throws Exception {
        // 准备测试数据
        Movie movie1 = new Movie();
        movie1.setTitle("Waterworld");
        movie1.setYear(1995);
        movie1.setImdbID("tt0114898");


        Movie movie2 = new Movie();
        movie2.setTitle("The Making of 'Waterworld'");
        movie2.setYear(1995);
        movie2.setImdbID("tt2670548");


        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock 行为
        when(movieService.getAllMovies()).thenReturn(movies);

        // 发送 GET 请求并验证响应
        mockMvc.perform(get("/movies/showall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].imdbID").value("tt0114898"))
                .andExpect(jsonPath("$[1].imdbID").value("tt2670548"));
    }

    @Test
    public void testGetMoviesByTitle() throws Exception {
        // 准备测试数据
        Movie movie1 = new Movie();
        movie1.setTitle("Waterworld");
        movie1.setYear(1995);
        movie1.setImdbID("tt0114898");

        Movie movie2 = new Movie();
        movie2.setTitle("Waterworld");
        movie2.setYear(1995);
        movie2.setImdbID("tt0189200");


        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock 行为
        when(movieService.getMoviesByTitle("Waterworld")).thenReturn(movies);

        // 发送 GET 请求并验证响应
        mockMvc.perform(get("/movies/title/Waterworld"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].imdbID").value("tt0114898"))
                .andExpect(jsonPath("$[1].imdbID").value("tt0189200"));
    }

    @Test
    public void testGetMoviesByTitle_NotFound() throws Exception {
        // Mock 行为，返回空列表表示未找到
        when(movieService.getMoviesByTitle("UnknownTitle")).thenReturn(Arrays.asList());

        // 发送 GET 请求并验证响应
        mockMvc.perform(get("/movies/title/UnknownTitle"))
                .andExpect(status().isNotFound());
    }
}
