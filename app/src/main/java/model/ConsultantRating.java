package model;

import java.util.List;

public class ConsultantRating {
    private String id;
    private Double score;
    private List<String> review;

    public void updateReview(String newreview){
        this.review.add(newreview);
    }
    public void calculateScore(Double newgivenscore){
        double temp=this.score*(review.size()-1);
        this.score=(temp+newgivenscore)/review.size();
    }
}
