package com.example.bookshop;

public class Review {

    private String bookName;
    private String username;
    private String isbn;
    private String review;
    private float rating;
    private String whereToBuy;

    // Constructors, Getters and Setters

    public Review() {
        // Needed for Firestore
    }

    public Review(String username, String bookName, String isbn, String review, float rating, String whereToBuy) {
        this.username = username;
        this.bookName = bookName;
        this.isbn = isbn;
        this.review = review;
        this.rating = rating;
        this.whereToBuy = whereToBuy;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWhereToBuy() {
        return whereToBuy;
    }

    public void setWhereToBuy(String whereToBuy) {
        this.whereToBuy = whereToBuy;
    }
}
