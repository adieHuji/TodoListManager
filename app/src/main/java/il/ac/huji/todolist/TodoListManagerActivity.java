package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoListManagerActivity extends AppCompatActivity {

    ArrayList<TaskObject> listItems=new ArrayList<TaskObject>();
    costumeAdapter<TaskObject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        adapter = new costumeAdapter(this,0,listItems);
        ListView lv = (ListView) findViewById(R.id.lstTodoItems);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                final String curItem = listItems.get(pos).getTitle();
                if(curItem.indexOf("Call")!=0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                    builder.setTitle(curItem)
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
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                    builder.setTitle(curItem)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    listItems.remove(pos);
                                    adapter.notifyDataSetChanged();
                                }
                            }).setNeutralButton(curItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            String number = curItem.substring(curItem.indexOf(" "));
                            callIntent.setData(Uri.parse("tel:" + number));
                            startActivity(callIntent);

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }

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
        Intent intent =new Intent(TodoListManagerActivity.this, AddNewTodoItemActivity.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == AppCompatActivity.RESULT_OK){
                String title = (String)data.getStringExtra("title");
                Date date = (Date)data.getSerializableExtra("dueDate");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH)+1;
                int year = cal.get(Calendar.YEAR);
                String curDate = String.valueOf(day)+'-'+String.valueOf(month)+'-'+String.valueOf(year);
                TaskObject curTask = new TaskObject (title, curDate);
                listItems.add(curTask);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
