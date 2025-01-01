package com.example.demo.model;

public class Review {
    private String id;
    private String review;
    public String getId() {
        return id;
    }
    public void setId(Object id) {
        this.id = (String)id;
    }
    public String getReview() {
        return review;
    }
    public void setReview(Object review) {
        this.review = (String)review;
    }
    public Review(Object id, Object review) {
        this.id = (String)id;
        this.review = (String)review;
    }

    
}
