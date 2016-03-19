package il.ac.huji.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewTodoItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        Button ok = (Button)findViewById(R.id.btnOK);
        Button cancel = (Button)findViewById(R.id.btnCancel);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText title = (EditText) findViewById(R.id.edtNewItem);
                DatePicker date = (DatePicker) findViewById(R.id.datePicker);
                Intent intent = new Intent();
                intent.putExtra("title", title.getText().toString());
                int day = date.getDayOfMonth();
                int month = date.getMonth();
                int year = date.getYear();
                String dateStr = String.valueOf(day) + '-' + String.valueOf(month + 1) + '-' + String.valueOf(year);
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date finalDate = dateFormater.parse(dateStr);
                    intent.putExtra("dueDate", finalDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                setResult(AppCompatActivity.RESULT_OK, intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}
