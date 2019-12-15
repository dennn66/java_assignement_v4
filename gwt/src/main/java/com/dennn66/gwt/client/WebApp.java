package com.dennn66.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        TasksTableWidget tasksTableWidget = new TasksTableWidget();
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        VerticalPanel leftVerticalPanel = new VerticalPanel();
        leftVerticalPanel.add(new AddTaskFormWidget(tasksTableWidget));
        leftVerticalPanel.add(new FilterFormWidget(tasksTableWidget));
        VerticalPanel rightVerticalPanel = new VerticalPanel();
        rightVerticalPanel.add(tasksTableWidget);
        horizontalPanel.add(leftVerticalPanel);
        horizontalPanel.add(rightVerticalPanel);
        RootPanel.get().add(horizontalPanel);
    }
}