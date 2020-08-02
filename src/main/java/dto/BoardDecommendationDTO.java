package dto;

public class BoardDecommendationDTO {
    private String userId;
    private int boardNumber;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public String getUserId() {
        return userId;
    }
    public int getBoardNumber() {
        return boardNumber;
    }
}
