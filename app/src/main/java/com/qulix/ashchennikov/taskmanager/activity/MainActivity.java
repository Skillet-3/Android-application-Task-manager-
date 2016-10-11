package com.qulix.ashchennikov.taskmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.qulix.ashchennikov.taskmanager.R;
import com.qulix.ashchennikov.taskmanager.controllers.TaskItemProviderImpl;
import com.qulix.ashchennikov.taskmanager.controllers.TaskItemService;
import com.qulix.ashchennikov.taskmanager.models.TaskItem;
import com.qulix.ashchennikov.taskmanager.controllers.ListAdapter;

/**
 * Главная форма: “Список задач”
 */
public class MainActivity extends Activity {

    private ListAdapter listAdapter;
    private ProgressBar progressBar;
    private ListView listview;
    private TaskItemProviderImpl taskItemProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskItemProvider = new TaskItemProviderImpl();

        Intent intent = new Intent(this, TaskItemService.class);
        intent.setAction(TaskItemService.ACTION_DOWNLOAD);
        startService(intent);


        View header = LayoutInflater.from(this).inflate(R.layout.list_header, null, false);
        progressBar = (ProgressBar)header.findViewById(R.id.progressBar);

        listview = (ListView)findViewById(R.id.listItem);
        listview.addHeaderView(header);

        registerForContextMenu(listview);
        initializingOnItemClickListener();

        listAdapter = new ListAdapter(taskItemProvider.getTaskItemList(), this);
        listview.setAdapter(listAdapter);

        initializingOnButtonClickListener();
     }

    private void update(){
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 2000);

        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == EditTaskActivity.EDIT_TASK_KEY_RESULT)&&(resultCode == RESULT_OK)) {
            TaskItem newTaskItem = (TaskItem) data.getSerializableExtra(EditTaskActivity.KEY_STRING_TASK_ITEM);
            if (newTaskItem.getId() == 0){
                taskItemProvider.createTaskItem(newTaskItem);
            }
            else{
                taskItemProvider.updateTaskItem(newTaskItem);
            }
            update();
        }
    }

    private void initializingOnItemClickListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickOnEditTaskItem(position);
            }
        });
    }

    private void initializingOnButtonClickListener() {

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                startActivityForResult(intent, EditTaskActivity.EDIT_TASK_KEY_RESULT);
            }
        });

        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void clickOnEditTaskItem(int position){
        TaskItem taskItem = taskItemProvider.getTaskItemByPosition(position-1);
        Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
        intent.putExtra(EditTaskActivity.KEY_STRING_TASK_ITEM, taskItem);
        startActivityForResult(intent, EditTaskActivity.EDIT_TASK_KEY_RESULT);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);

        MenuItem editItem = menu.findItem(R.id.editItem);
        editItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                clickOnEditTaskItem(adapterContextMenuInfo.position);
                return true;
            }
        });

        MenuItem delItem = menu.findItem(R.id.delItem);
        delItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                TaskItem taskItem = taskItemProvider.getTaskItemByPosition(adapterContextMenuInfo.position-1);
                taskItemProvider.deleteTaskItem(taskItem);
                update();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, AppPreferencesActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

}
