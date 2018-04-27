package epam.javalab22.pchardware.entity;

import epam.javalab22.pchardware.util.TimeAndDateSupport;

public class Review implements TimeAndDateSupport {

    private String username;
    private String review;
    private long time;
    private String date;

    public Review() {}

    public Review(String username, String review, long time) {
        this.username = username;
        this.review = review;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }
}
