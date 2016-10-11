package com.qulix.ashchennikov.taskmanager.controllers;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class TaskItemService extends IntentService {

    public static final String ACTION_DOWNLOAD = "com.qulix.ashchennikov.taskmanager.controllers.action.DOWNLOAD";
    private static final String ACTION_BAZ = "com.qulix.ashchennikov.taskmanager.controllers.action.BAZ";

    public TaskItemService() {
        super("TaskItemService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                handleActionDownload();

            } else if (ACTION_BAZ.equals(action)) {

            }
        }
    }

    private void handleActionDownload() {

    }


}
