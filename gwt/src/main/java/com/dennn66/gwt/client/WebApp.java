package com.dennn66.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void refresh(){
        String token = Utils.getToken();//Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("STORAGE: " + token);
        if(token == null) {
            History.newItem("Login");
            RootPanel.get().clear();
            LoginForm loginForm = new LoginForm(this);
            RootPanel.get().add(loginForm);
        } else {
            History.newItem("TaskTableWidget");
            RootPanel.get().clear();
            TasksTableWidget tasksTableWidget = new TasksTableWidget(this);
            HorizontalPanel horizontalPanel = new HorizontalPanel();
            VerticalPanel leftVerticalPanel = new VerticalPanel();
            leftVerticalPanel.add(new AddTaskWidget(tasksTableWidget));
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
        History.addValueChangeHandler(event -> {
            String historyToken = event.getValue();
            try {
                if (historyToken.equals("Login")) {
                    Utils.removeToken();
                }
            } catch (IndexOutOfBoundsException e) {
                GWT.log("History operation error: " + e);
            }
        });
        refresh();
    }
}