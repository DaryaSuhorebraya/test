package by.epam.movierating.bean;

import java.io.Serializable;

public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMovie;
    private int idUser;
    private int mark;

    public Rating() {
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rating rating = (Rating) o;

        if (idMovie != rating.idMovie) {
            return false;
        }
        if (idUser != rating.idUser) {
            return false;
        }
        if (mark!=rating.mark){
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = idMovie;
        result = 31 * result + idUser;
        result = 31 * result + mark;
        return result;
    }
}
