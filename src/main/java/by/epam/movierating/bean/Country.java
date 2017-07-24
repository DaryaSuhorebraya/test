package by.epam.movierating.bean;

import java.io.Serializable;

public class Country implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String name;

    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        if (code != null ? !code.equals(country.code) : country.code != null) {
            return false;
        }
        return name != null ? name.equals(country.name) : country.name == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
