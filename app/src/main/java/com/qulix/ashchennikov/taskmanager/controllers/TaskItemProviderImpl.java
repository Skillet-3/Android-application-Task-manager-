package com.qulix.ashchennikov.taskmanager.controllers;

import com.qulix.ashchennikov.taskmanager.R;
import com.qulix.ashchennikov.taskmanager.models.ITaskItemProvider;
import com.qulix.ashchennikov.taskmanager.models.Status;
import com.qulix.ashchennikov.taskmanager.models.TaskItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TaskItemProviderImpl implements ITaskItemProvider {

    private List<TaskItem> taskItemList;

    public TaskItemProviderImpl() {
        taskItemList = new ArrayList<TaskItem>();
        initList();
    }

    @Override
    public void downloadTaskItems() {
    }

    private void initList() {
        for (int i = 1; i <= 20; i++) {
            TaskItem tempTaskItem = new TaskItem();
            tempTaskItem.setName("TaskItem " + i);
            tempTaskItem.setId(i);
            tempTaskItem.setWorkTime(10 + i);
            tempTaskItem.setStatus(Status.IN_PROCESS);
            tempTaskItem.setIdImg(R.drawable.in_process);
            Calendar calendar = Calendar.getInstance();
            tempTaskItem.setDateStart(calendar.getTime());
            tempTaskItem.setDateEnd(calendar.getTime());
            taskItemList.add(tempTaskItem);
        }
    }

    @Override
    public void createTaskItem(TaskItem taskItem) {
        taskItem.setId(findEmptyId());
        taskItemList.add(taskItem);
    }

    @Override
    public void updateTaskItem(TaskItem newTaskItem) {
        TaskItem taskItem = getTaskItem(newTaskItem.getId());
        taskItem.setWorkTime(newTaskItem.getWorkTime());
        taskItem.setDateEnd(newTaskItem.getDateEnd());
        taskItem.setDateStart(newTaskItem.getDateStart());
        taskItem.setName(newTaskItem.getName());
        taskItem.setStatus(newTaskItem.getStatus());
        taskItem.setIdImg(newTaskItem.getIdImg());
    }

    private TaskItem getTaskItem(int id){
        for (TaskItem taskItem: taskItemList){
            if (taskItem.getId() == id){
                return taskItem;
            }
        }
        return null;
    }

    private int findEmptyId(){
        int i = 1;
        for (boolean isEmpty; i < taskItemList.size()+1; i++){
            isEmpty = true;
            for (TaskItem taskItem: taskItemList){
                if (taskItem.getId() == i){
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty){
                break;
            }
        }
        return i;
    }

    @Override
    public void deleteTaskItem(TaskItem taskItem) {
        taskItemList.remove(taskItem);
    }

    public TaskItem getTaskItemByPosition(int position){
        return taskItemList.get(position);
    }

    public List<TaskItem> getTaskItemList() {
        return taskItemList;
    }
}
