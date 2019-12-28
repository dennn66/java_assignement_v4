package com.dennn66.gwt.client;

import com.dennn66.gwt.common.TaskCreateDto;
import com.dennn66.gwt.common.UserReferenceDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class AddTaskWidget extends Composite {

    private UsersClient usersClient;
    private TasksClient tasksClient;


    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox assigneeListBox;

    private TasksTableWidget tasksTableWidget;

    private static final Binder binder = GWT.create(Binder.class);
    interface Binder extends UiBinder<Widget, AddTaskWidget> {
    }

    public AddTaskWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(binder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        usersClient = GWT.create(UsersClient.class);
        tasksClient = GWT.create(TasksClient.class);
        refreshUsers();
    }

    public void refreshUsers(){
        String token = Utils.getToken();
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            usersClient.getAllUsers(token, new MethodCallback<List<UserReferenceDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log(throwable.toString());
                    GWT.log(throwable.getMessage());

                    Window.alert("Невозможно получить список пользователей: " + method.getResponse().getText());
                }

                @Override
                public void onSuccess(Method method, List<UserReferenceDto> i) {
                    GWT.log("Received " + i.size() + " users");
                    List<UserReferenceDto> users = new ArrayList<>();
                    users.addAll(i);
                    assigneeListBox.clear();
                    assigneeListBox.addItem("", "-1");
                    assigneeListBox.setSelectedIndex(0);
                    users.stream().forEach(userDto -> {
                        assigneeListBox.addItem(userDto.getUsername(), userDto.getId().toString());
                    });
                }
            });
        }
    }

    @UiHandler("btnCreate")
    public void createClick(ClickEvent event) {
        String token = Utils.getToken();
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            tasksClient.addTask(
                    token,
                    new TaskCreateDto(
                            titleText.getText(),
                            new UserReferenceDto(
                                    Long.parseLong(assigneeListBox.getSelectedValue())),
                        descriptionText.getText()),
                    new MethodCallback<Void>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            GWT.log(throwable.toString());
                            GWT.log(throwable.getMessage());
                            Window.alert("Невозможно создать задачу: " + method.getResponse().getText());
                        }

                        @Override
                        public void onSuccess(Method method, Void result) {
                            titleText.setText("");
                            descriptionText.setText("");
                            assigneeListBox.setSelectedIndex(0);
                            tasksTableWidget.refresh();
                        }
                    });
        }
    }
}
