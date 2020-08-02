package dto;

public class TodoDTO {
    private int number;
    private String writer;
    private String content;
    private String date;

    public void setNumber(int number) {
        this.number = number;
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

    public int getNumber() {
        return number;
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
}
