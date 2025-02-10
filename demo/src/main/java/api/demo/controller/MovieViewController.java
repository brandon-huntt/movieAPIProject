package api.demo.controller;

import api.demo.domain.Movie;
import api.demo.domain.Review;
import api.demo.service.MovieService;
import api.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movies") // This stays as /movies for web pages
public class MovieViewController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String listMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "movies"; // Resolves to movies.html in src/main/resources/templates
    }

    @GetMapping("/{id}")
    public String movieDetails(@PathVariable Integer id, Model model) {
        Movie movie = movieService.findById(id);
        List<Review> reviews = reviewService.findByMovieId(id);

        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);

        return "movie-details"; // maps to movie-details.html
    }
}
