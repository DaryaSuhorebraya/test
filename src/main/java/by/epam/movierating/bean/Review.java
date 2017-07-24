package by.epam.movierating.bean;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMovie;
    private int idUser;
    private String title;
    private String review;
    private Date publishDate;

    public Review() {
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

        Review review1 = (Review) o;

        if (idMovie != review1.idMovie) {
            return false;
        }
        if (idUser != review1.idUser) {
            return false;
        }
        if (title != null ? !title.equals(review1.title) : review1.title != null) {
            return false;
        }
        if (review != null ? !review.equals(review1.review) : review1.review != null) {
            return false;
        }
        return publishDate != null ? publishDate.equals(review1.publishDate) : review1.publishDate == null;

    }

    @Override
    public int hashCode() {
        int result = idMovie;
        result = 31 * result + idUser;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
