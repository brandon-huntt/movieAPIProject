package api.demo.repository;

import api.demo.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    // If you want to search by partial title, you might do:
    // List<Movie> findByTitleContainingIgnoreCase(String title);
}
