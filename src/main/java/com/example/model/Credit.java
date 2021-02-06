package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "credits")
public class Credit extends Model {

    private Integer limit;
    private Double percentage;

    public Credit() {

    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
