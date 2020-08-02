package dto;

public class FavoriteBoardDTO {
    private int number;
    private String id;
    private int boardNumber;

    public void setNumber(int number) {
        this.number = number;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public int getNumber() {
        return number;
    }
    public String getId() {
        return id;
    }
    public int getBoardNumber() {
        return boardNumber;
    }
}
