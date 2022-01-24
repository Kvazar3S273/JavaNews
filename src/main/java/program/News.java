package program;

import lombok.Data;

@Data
public class News {
    private int id;
    private String title;
    private String text;

    public News() {
    }

    public News(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
