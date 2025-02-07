package api.demo.service;

import api.demo.domain.Movie;
import api.demo.domain.Review;
import api.demo.repository.MovieRepository;
import api.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<Review> findByMovieId(Integer movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

    public Review createReview(Review review, String currentUserName) {
        // Check if movie exists
        Movie movie = movieRepository.findById(review.getMovieId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found"));

        // Set the author to the current user
        review.setAuthorName(currentUserName);

        Review saved = reviewRepository.save(review);
        recalcAverageRating(review.getMovieId());
        return saved;
    }

    public Review updateReview(Long reviewId, Review updates, String currentUserName, boolean isAdmin) {
        Review existing = findById(reviewId);
        // Only the author or admin can update
        if (!isAdmin && !existing.getAuthorName().equals(currentUserName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to edit this review");
        }
        existing.setRating(updates.getRating());
        existing.setComment(updates.getComment());
        Review saved = reviewRepository.save(existing);
        recalcAverageRating(existing.getMovieId());
        return saved;
    }

    public void deleteReview(Long reviewId, String currentUserName, boolean isAdmin) {
        Review existing = findById(reviewId);
        // Only the author or admin can delete
        if (!isAdmin && !existing.getAuthorName().equals(currentUserName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to delete this review");
        }
        reviewRepository.delete(existing);
        recalcAverageRating(existing.getMovieId());
    }

    private void recalcAverageRating(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        if (reviews.isEmpty()) {
            movieService.updateAverageRating(movieId, 0.0);
            return;
        }
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        movieService.updateAverageRating(movieId, avg);
    }
}
