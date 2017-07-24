package by.epam.movierating.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Date dateRegister;
    private String email;
    private String status;
    private boolean isAdmin;
    private boolean isBanned;

    private List<Review> reviewList;
    private List<Rating> ratingList;

    public User() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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

    public String getStatus() {return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (id != user.id) {
            return false;
        }
        if (isAdmin != user.isAdmin) {
            return false;
        }
        if (isBanned != user.isBanned) {
            return false;
        }
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (password != null ? !password.equals(user.password) : user.password != null) {
            return false;
        }
        if (dateRegister != null ? !dateRegister.equals(user.dateRegister) : user.dateRegister != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (status != null ? !status.equals(user.status) : user.status != null) {
            return false;
        }
        if (reviewList != null ? !reviewList.equals(user.reviewList) : user.reviewList != null) {
            return false;
        }
        return ratingList != null ? ratingList.equals(user.ratingList) : user.ratingList == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dateRegister != null ? dateRegister.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        result = 31 * result + (reviewList != null ? reviewList.hashCode() : 0);
        result = 31 * result + (ratingList != null ? ratingList.hashCode() : 0);
        return result;
    }
}
