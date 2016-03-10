package il.ac.huji.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by AdiE on 3/10/2016.
 */
public class costumeAdapter<T> extends ArrayAdapter<String> {

    private int[] colors = new int[] {0x30FF0000, 0x300000FF};

    public costumeAdapter(Context todoListManagerActivity, int simple_list_item_1, ArrayList<String> listItems)  {
        super(todoListManagerActivity, simple_list_item_1, listItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if(position % 2 == 0)
        {
            view.setBackgroundColor(colors[0]);
        }
        else
        {
            view.setBackgroundColor(colors[1]);
        }
        return view;
    }
}
