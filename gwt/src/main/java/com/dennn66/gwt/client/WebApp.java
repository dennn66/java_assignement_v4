package com.dennn66.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void refresh(){
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("STORAGE: " + token);
        if(token == null) {
            RootPanel.get().clear();
            LoginForm loginForm = new LoginForm(this);
            RootPanel.get().add(loginForm);
        } else {
            RootPanel.get().clear();
            TasksTableWidget tasksTableWidget = new TasksTableWidget(this);
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
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        refresh();
    }
}