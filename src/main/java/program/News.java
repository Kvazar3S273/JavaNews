package program;

import lombok.Data;

@Data
public class News {
    private int id;
    private String title;
    private String text;
    private int categoryId;

    public News() {
    }

    public News(int id, String title, String text, int categoryId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
