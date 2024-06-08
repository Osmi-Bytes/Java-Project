package model;

public class Note {
    private int id;
    private int userId;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        if (content != null) {
            return content.length() > 20 ? content.substring(0, 20) + "..." : content;
        } else {
            return ""; 
        }
    }

}
