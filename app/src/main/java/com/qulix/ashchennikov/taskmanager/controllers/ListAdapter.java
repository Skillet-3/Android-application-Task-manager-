package com.qulix.ashchennikov.taskmanager.controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qulix.ashchennikov.taskmanager.R;
import com.qulix.ashchennikov.taskmanager.models.TaskItem;

import java.util.List;

/**
 * ListAdapter
 */
public class ListAdapter extends BaseAdapter {

    private List<TaskItem> taskItemList;
    private LayoutInflater inflater;

    public ListAdapter(List<TaskItem> taskItemList, Context context) {
        this.taskItemList = taskItemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.taskItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return  this.taskItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        final TaskItem taskItem = taskItemList.get(position);
        TextView taskTextView = (TextView) view.findViewById(R.id.taskTextView);
        taskTextView.setText(taskItem.getName());
        ImageView taskImageView = (ImageView) view.findViewById(R.id.taskImageView);
        taskImageView.setImageResource(taskItem.getIdImg());
        return view;
    }
}
