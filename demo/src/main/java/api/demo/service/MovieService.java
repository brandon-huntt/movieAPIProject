package api.demo.service;

import api.demo.domain.Movie;
import api.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.http.HttpStatus;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found"));
    }

    // ADMIN can add
    public Movie createMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Movie with this ID already exists.");
        }
        return movieRepository.save(movie);
    }

    // ADMIN can delete
    public void deleteMovie(Integer id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }

    public void updateAverageRating(Integer movieId, double newAverage) {
        Movie movie = findById(movieId);
        movie.setAverageRating(newAverage);
        movieRepository.save(movie);
    }
}
