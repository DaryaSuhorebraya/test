package by.epam.movierating.bean;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private int releaseYear;
    private String description;
    private double rating;
    private String posterPath;
    private boolean isDeleted;

    private List<Review> reviewList;
    private List<Rating> ratingList;
    private List<Actor> actorList;
    private List<Country> countryList;
    private List<Genre> genreList;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Movie movie = (Movie) o;

        if (id != movie.id) {
            return false;
        }
        if (title != null ? !title.equals(movie.title) : movie.title != null) {
            return false;
        }
        if (description != null ? !description.equals(movie.description) : movie.description != null) {
            return false;
        }
        if (reviewList != null ? !reviewList.equals(movie.reviewList) : movie.reviewList != null) {
            return false;
        }
        if (ratingList != null ? !ratingList.equals(movie.ratingList) : movie.ratingList != null) {
            return false;
        }
        if (actorList != null ? !actorList.equals(movie.actorList) : movie.actorList != null) {
            return false;
        }
        if (countryList != null ? !countryList.equals(movie.countryList) : movie.countryList != null) {
            return false;
        }
        return genreList != null ? genreList.equals(movie.genreList) : movie.genreList == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (reviewList != null ? reviewList.hashCode() : 0);
        result = 31 * result + (ratingList != null ? ratingList.hashCode() : 0);
        result = 31 * result + (actorList != null ? actorList.hashCode() : 0);
        result = 31 * result + (countryList != null ? countryList.hashCode() : 0);
        result = 31 * result + (genreList != null ? genreList.hashCode() : 0);
        return result;
    }
}
