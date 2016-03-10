package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class TodoListManagerActivity extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    costumeAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        adapter = new costumeAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        ListView lv = (ListView) findViewById(R.id.lstTodoItems);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                builder.setTitle(listItems.get(pos).toString())
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                listItems.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                newAdd();
                return true;

        }
        return true;
    }

    private void newAdd() {
        EditText item = (EditText) findViewById(R.id.edtNewItem);
        String itemSTR = item.getText().toString();
        if(itemSTR.isEmpty())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
            builder.setTitle("Alert")
                    .setMessage("Please enter Todo item.")
                    .setNeutralButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            listItems.add(itemSTR);
            adapter.notifyDataSetChanged();
        }
    }


}
