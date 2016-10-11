package com.qulix.ashchennikov.taskmanager.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qulix.ashchennikov.taskmanager.R;
import com.qulix.ashchennikov.taskmanager.controllers.TaskItemProviderImpl;
import com.qulix.ashchennikov.taskmanager.models.Status;
import com.qulix.ashchennikov.taskmanager.models.TaskItem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Форма ввода задачи
 */
public class EditTaskActivity extends Activity {

    public static final String KEY_STRING_TASK_ITEM = EditTaskActivity.class + "task_item_id";
    public static final int EDIT_TASK_KEY_RESULT = 1;

    private TaskItem taskItem;

    private EditText nameEditText;
    private EditText workTimeEditText;
    private DatePickerDialog dateStartDatePickerDialog;
    private DatePickerDialog dateEndDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initializingComponents();
    }

    private void initializingComponents(){
        taskItem = getIntentTaskItem();

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText.setText(taskItem.getName());
        workTimeEditText = (EditText) findViewById(R.id.workTimeEditText);
        workTimeEditText.setText(String.valueOf(taskItem.getWorkTime()));

        initializingDatePickerDialog();
        initializingSpinner();
        initializingOnClickListener();
    }

    private TaskItem getIntentTaskItem(){
        TextView isCreateEditText = (TextView) findViewById(R.id.isCreateTextView);

        taskItem = (TaskItem) getIntent().getSerializableExtra(KEY_STRING_TASK_ITEM);
        if (taskItem == null){
            taskItem = new TaskItem();
            isCreateEditText.setText(getResources().getString(R.string.create));
        }
        else{
            isCreateEditText.setText(getResources().getString(R.string.edit));
        }
        return taskItem;
    }

    private void  initializingSpinner() {
        final String[] spinnerItem = {
                getResources().getString(R.string.NOT_STARTED),
                getResources().getString(R.string.IN_PROCESS),
                getResources().getString(R.string.COMPLETED),
                getResources().getString(R.string.POSTPONED)};

        Spinner spinner = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditTaskActivity.this,
                android.R.layout.simple_spinner_item, spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(taskItem.getStatus().ordinal());
    }

    private void  initializingOnClickListener() {

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataIsValidate()){
                    Intent answerIntent = new Intent();
                    answerIntent.putExtra(KEY_STRING_TASK_ITEM, taskItem);
                    setResult(Activity.RESULT_OK, answerIntent);
                    finish();
                }
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        Button dateStartButton = (Button) findViewById(R.id.dateStartButton);
        dateStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStartDatePickerDialog.show();
            }
        });
        dateStartButton.setText(getDateToString(dateStartDatePickerDialog));

        Button dateEndButton = (Button) findViewById(R.id.dateEndButton);
        dateEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEndDatePickerDialog.show();
            }
        });
        dateEndButton.setText(getDateToString(dateEndDatePickerDialog));
    }

    private void initializingDatePickerDialog(){
        dateStartDatePickerDialog = new DatePickerDialog(this, null ,0,0,0);
        dateEndDatePickerDialog = new DatePickerDialog(this, null ,0,0,0);
        setDateInDatePickerDialog(dateStartDatePickerDialog ,taskItem.getDateStart());
        setDateInDatePickerDialog(dateEndDatePickerDialog ,taskItem.getDateEnd());
    }

    private void setDateInDatePickerDialog(DatePickerDialog datePickerDialog ,Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        datePickerDialog.updateDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Date getDateInDatePickerDialog(DatePickerDialog datePickerDialog){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
        calendar.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
        calendar.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());
        return calendar.getTime();
    }

    private String getDateToString(DatePickerDialog datePickerDialog){
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(getDateInDatePickerDialog(datePickerDialog));
    }

    private boolean dataIsValidate(){
        String name = nameEditText.getText().toString();
        String workTime = workTimeEditText.getText().toString();
        if ((workTime.equals(""))||(name.equals(""))){
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.isEmpty),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        Date startDate = getDateInDatePickerDialog(dateStartDatePickerDialog);
        Date endDate = getDateInDatePickerDialog(dateEndDatePickerDialog);
        if (startDate.compareTo(endDate) > 0 ) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.isDateValidate),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        taskItem.setDateStart(startDate);
        taskItem.setDateEnd(endDate);
        taskItem.setName(name);
        taskItem.setWorkTime(Integer.valueOf(workTime));
        Spinner spinner = (Spinner) findViewById(R.id.statusSpinner);
        taskItem.setStatus(spinner.getSelectedItemPosition());
        taskItem.setIdImg(getImageResourceId(taskItem.getStatus()));

        return true;
    }

    private int getImageResourceId(Status status){
        switch(status){
            case COMPLETED:
                return R.drawable.completed;
            case IN_PROCESS:
                return R.drawable.in_process;
            case POSTPONED:
                return R.drawable.postponed;
            case NOT_STARTED:
                return R.drawable.not_started;
        }
        return 0;
    }

}
