package api.demo.controller;

import api.demo.domain.Movie;
import api.demo.domain.Review;
import api.demo.service.MovieService;
import api.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews/view")
public class ReviewViewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        List<Movie> movies = movieService.findAll();

        // Map movie IDs to names for easy lookup
        Map<Integer, String> movieNames = movies.stream()
                .collect(Collectors.toMap(Movie::getId, Movie::getTitle));

        // Attach movie names to reviews
        reviews.forEach(review -> review.setMovieName(movieNames.get(review.getMovieId())));

        model.addAttribute("reviews", reviews);
        return "reviews"; // src/main/resources/templates/reviews.html
    }
}
