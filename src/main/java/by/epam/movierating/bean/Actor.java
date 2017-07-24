package by.epam.movierating.bean;

import java.io.Serializable;
import java.util.List;

public class Actor implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private String imagePath;

    private List<Movie> movieList;

    public Actor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Actor actor = (Actor) o;

        if (id != actor.id) {
            return false;
        }
        if (firstName != null ? !firstName.equals(actor.firstName) : actor.firstName != null) {
            return false;
        }
        return lastName != null ? lastName.equals(actor.lastName) : actor.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
