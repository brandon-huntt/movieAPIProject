package api.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private Integer id; // We assume the ID is inserted from your script

    private String title;
    private String genre;
    private String description;
    private Integer releaseYear;
    private Double averageRating; // Recalculated when reviews come in

    // No-arguments constructor
    public Movie() {
    }

    // All-arguments constructor
    public Movie(Integer id, String title, String genre, String description, Integer releaseYear, Double averageRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
