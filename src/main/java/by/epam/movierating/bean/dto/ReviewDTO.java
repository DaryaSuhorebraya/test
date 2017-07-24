package by.epam.movierating.bean.dto;

import java.io.Serializable;
import java.util.Date;

public class ReviewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMovie;
    private int idUser;
    private String movieTitle;
    private String userLogin;
    private int rating;
    private String title;
    private String review;
    private Date publishDate;

    public ReviewDTO() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;

        if (idMovie != reviewDTO.idMovie) {
            return false;
        }
        if (idUser != reviewDTO.idUser) {
            return false;
        }
        if (rating != reviewDTO.rating) {
            return false;
        }
        if (movieTitle != null ? !movieTitle.equals(reviewDTO.movieTitle) : reviewDTO.movieTitle != null) {
            return false;
        }
        if (userLogin != null ? !userLogin.equals(reviewDTO.userLogin) : reviewDTO.userLogin != null) {
            return false;
        }
        if (title != null ? !title.equals(reviewDTO.title) : reviewDTO.title != null) {
            return false;
        }
        if (review != null ? !review.equals(reviewDTO.review) : reviewDTO.review != null) {
            return false;
        }
        return publishDate != null ? publishDate.equals(reviewDTO.publishDate) : reviewDTO.publishDate == null;

    }

    @Override
    public int hashCode() {
        int result = idMovie;
        result = 31 * result + idUser;
        result = 31 * result + (movieTitle != null ? movieTitle.hashCode() : 0);
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
