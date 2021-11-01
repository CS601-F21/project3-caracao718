package project1;

import java.util.ArrayList;

/**
 * A class that stores the JSON in Review file
 */
public class Review extends Item{
    private String reviewerID;
    private String reviewerName;
    private ArrayList<Integer> helpful;
    private String reviewText;
    private double overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;

    public Review(String reviewerID, String asin, String reviewerName, ArrayList<Integer> helpful, String reviewText, double overall, String summary, int unixReviewTime, String reviewTime) {
        this.reviewerID = reviewerID;
        this.asin = asin;
        this.reviewerName = reviewerName;
        this.helpful = helpful;
        this.reviewText = reviewText;
        this.overall = overall;
        this.summary = summary;
        this.unixReviewTime = unixReviewTime;
        this.reviewTime = reviewTime;
    }

    /**
     * Get the text in review
     * @return String
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * get the asin number for the object
     * @return String
     */
    public String getAsin() {
        return asin;
    }


    /**
     * print the object with all it's fields
     * @return String
     */
    @Override
    public String toString() {
        return "Review{" +
                "reviewerID='" + reviewerID + '\'' +
                ", asin='" + asin + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", helpful=" + helpful +
                ", reviewText='" + reviewText + '\'' +
                ", overall=" + overall +
                ", summary='" + summary + '\'' +
                ", unixReviewTime=" + unixReviewTime +
                ", reviewTime='" + reviewTime + '\'' +
                '}';
    }
}
