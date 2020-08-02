package dto;

public class CommentDTO {
    private int number;
    private String writer;
    private String date;
    private String content;
    private int boardNumber;

    public void setNumber(int number) {
        this.number = number;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public int getNumber() {
        return number;
    }
    public String getWriter() {
        return writer;
    }
    public String getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }
    public int getBoardNumber() {
        return boardNumber;
    }
}
