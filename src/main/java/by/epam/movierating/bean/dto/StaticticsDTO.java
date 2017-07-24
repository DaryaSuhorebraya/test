package by.epam.movierating.bean.dto;

import java.io.Serializable;

public class StaticticsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String label;
    private int value;


    public StaticticsDTO() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StaticticsDTO that = (StaticticsDTO) o;

        if (value != that.value) {
            return false;
        }
        return label != null ? label.equals(that.label) : that.label == null;

    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + value;
        return result;
    }
}
