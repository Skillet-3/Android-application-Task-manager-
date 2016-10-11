package com.qulix.ashchennikov.taskmanager.models;


public interface ITaskItemProvider {

    public void downloadTaskItems();
    public void createTaskItem(TaskItem taskItem);
    public void updateTaskItem(TaskItem newTaskItem);
    public void deleteTaskItem(TaskItem taskItem);
}
