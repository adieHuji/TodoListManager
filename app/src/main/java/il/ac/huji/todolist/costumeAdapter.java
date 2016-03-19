package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AdiE on 3/10/2016.
 */
public class costumeAdapter<T> extends ArrayAdapter<TaskObject> {
    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<TaskObject> tasks = null;

    public costumeAdapter(Context context, int resource, ArrayList<TaskObject> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
        this.tasks = objects;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }
    @Override
    public TaskObject getItem(int pos) {
        return tasks.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class TextViewsPlaceHolder {
        public TextView title;
        public TextView date;
    }

    private boolean checkOverdue(String dateStr) {
        Calendar cal = Calendar.getInstance();
        int curDay = cal.get(Calendar.DAY_OF_MONTH);
        int curMonth = cal.get(Calendar.MONTH)+1;
        int curYear = cal.get(Calendar.YEAR);

        String taskDayStr = dateStr.substring(0, dateStr.indexOf("-"));
        dateStr = dateStr.substring(dateStr.indexOf("-")+1);
        String taskMonthStr = dateStr.substring(0, dateStr.indexOf("-"));
        dateStr = dateStr.substring(dateStr.indexOf("-")+1);
        String taskYearStr = dateStr;

        int taskDay = Integer.parseInt(taskDayStr);
        int taskMonth = Integer.parseInt(taskMonthStr);
        int taskYear = Integer.parseInt(taskYearStr);

        //taskYear is bigger then current year, no problem
        if(taskYear > curYear)
        {
            return false;
        }
        //taskYear is smaller then current year, means Overdue
        if(taskYear < curYear)
        {
            return true;
        }
        //else taskYear=current year, check about month:
        //taskMonth is bigger then current month, no problem
        else if(taskMonth > curMonth)
        {
            return false;
        }
        //taskYear=current year and taskMonth is smaller then current month, means Overdue
        else if(taskDay < curDay)
        {
            return true;
        }
        //else taskYear=current year AND taskMonth=current month, check about day:
        //taskDay is bigger then current day, no problem
        else if(taskDay > curDay)
        {
            return false;
        }
        //taskYear=current year AND taskMonth=current month AND taskDay is smaller then current day, means Overdue
        else if(taskDay < curDay)
        {
            return true;
        }
        //taskYear=current year AND taskMonth=current month AND taskDay=current day, no problem
        return false;




        //String curDateString = String.valueOf(curDay)+'-'+String.valueOf(curMonth)+'-'+String.valueOf(curYear);
//        DateFormat dateFormat1;
//        Date curDate;
//        dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//        curDate = (Date)dateFormat1.parse(curDateString);

//        DateFormat dateFormat2 ;
//        Date taskDate;
//        dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
//        taskDate = (Date)dateFormat2.parse(dateStr);
//
//        return curDate.compareTo(taskDate)>0;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        final TextViewsPlaceHolder place;

        if (convertView == null) {
            newView = inflater.inflate(R.layout.list_view_row, null);
            place = new TextViewsPlaceHolder();
            place.title = (TextView) newView.findViewById(R.id.txtTodoTitle);
            place.date = (TextView) newView.findViewById(R.id.txtTodoDueDate);
            newView.setTag(place);
        }
        else
        {
            place = (TextViewsPlaceHolder) newView.getTag();
        }

        place.title.setText(tasks.get(position).getTitle());
        place.date.setText(tasks.get(position).getDate());


        if (checkOverdue(tasks.get(position).getDate()))
        {
            place.title.setTextColor(Color.RED);
            place.date.setTextColor(Color.RED);
        }
        else
        {
            place.title.setTextColor(Color.BLACK);
            place.date.setTextColor(Color.BLACK);
        }

        return newView;
    }
}
