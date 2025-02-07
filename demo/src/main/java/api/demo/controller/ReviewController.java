package api.demo.controller;

import api.demo.domain.Review;
import api.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Anyone can GET reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsForMovie(@PathVariable Integer movieId) {
        return reviewService.findByMovieId(movieId);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    // Only logged-in user can create (auth != null)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public Review createReview(@RequestBody Review review, Authentication auth) {
        String username = auth.getName();
        return reviewService.createReview(review, username);
    }

    // Only author or admin can update
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Review updateReview(@PathVariable Long id,
                               @RequestBody Review updates,
                               Authentication auth) {
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return reviewService.updateReview(id, updates, username, isAdmin);
    }

    // Only author or admin can delete
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        reviewService.deleteReview(id, username, isAdmin);
    }
}
