package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by AdiE on 4/2/2016.
 */
public class ToDoDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo_db";
    private static final String TABLE_TODO = "todo";

    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DUE = "due";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_DUE};
    private int counter = 1;

    public ToDoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE todo ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "due LONG )";

        // create todo table
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ");
        this.onCreate(db);
    }

    public void addTask(TaskObject task){
        SQLiteDatabase db = this.getWritableDatabase();
        task.setId(counter);
        counter=counter+1;
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_DUE, task.getDate());
        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public TaskObject getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_TODO,COLUMNS," id = ?",new String[] { String.valueOf(id) },null,null,null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TaskObject task = new TaskObject();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setTile(cursor.getString(1));
        task.setDate(cursor.getString(2));
        return task;
    }

    public ArrayList<TaskObject> getAllTask() {
        ArrayList<TaskObject> taskList = new ArrayList<TaskObject>();
        String query = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TaskObject currentObj = null;
        if (cursor.moveToFirst()) {
            do {
                currentObj = new TaskObject();
                currentObj.setId(Integer.parseInt(cursor.getString(0)));
                currentObj.setTile(cursor.getString(1));
                currentObj.setDate(cursor.getString(2));
                taskList.add(currentObj);
            } while (cursor.moveToNext());
        }
        return taskList;
    }


    public void deleteTask(TaskObject task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO,KEY_ID + " = ?",new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
