package il.ac.huji.todolist;

/**
 * Created by AdiE on 3/17/2016.
 */
public class TaskObject {
    private String title;
    private String date;
    private int id;

    public TaskObject(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public TaskObject() {

    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTile(String title) {
        this.title = title;
    }

    public String toString() {
        return super.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
