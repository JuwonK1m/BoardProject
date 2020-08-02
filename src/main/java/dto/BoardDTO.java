package dto;

public class BoardDTO {
    private int number;
    private String title;
    private String writer;
    private String content;
    private String date;
    private int hit;
    private int commentCount;
    private int recommendationCount;
    private int decommendationCount;

    public void setNumber(int number) {
        this.number = number;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setHit(int hit) {
        this.hit = hit;
    }
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    public void setRecommendationCount(int recommendationCount) {
        this.recommendationCount = recommendationCount;
    }
    public void setDecommendationCount(int decommendationCount) {
        this.decommendationCount = decommendationCount;
    }

    public int getNumber() {
        return number;
    }
    public String getTitle() {
        return title;
    }
    public String getWriter() {
        return writer;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
    public int getHit() {
        return hit;
    }
    public int getCommentCount() { return commentCount; }
    public int getRecommendationCount() {
        return recommendationCount;
    }
    public int getDecommendationCount() {
        return decommendationCount;
    }
}
