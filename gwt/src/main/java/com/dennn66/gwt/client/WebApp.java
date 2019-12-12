package com.dennn66.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        TasksTableWidget tasksTableWidget = new TasksTableWidget();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new AddTaskFormWidget(tasksTableWidget));
        verticalPanel.add(new FilterFormWidget(tasksTableWidget));
        verticalPanel.add(tasksTableWidget);
        RootPanel.get().add(verticalPanel);
    }
}