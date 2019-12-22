package com.dennn66.gwt.client;

import com.dennn66.gwt.common.TaskDto;
import com.dennn66.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class UpdateTaskDialog extends DialogBox {

    private TasksClient tasksClient;
    private TaskDto taskDto;
    private UsersClient usersClient;
    private TasksTableWidget tasksTableWidget;

    public void setTaskDto(TaskDto taskDto) {
        this.taskDto = taskDto;
    }

    @UiField
    Label creatorText;

    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox statusListBox;

    @UiField
    ListBox assigneeListBox;

    private static final Binder binder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, UpdateTaskDialog> {
    }
    public UpdateTaskDialog(TasksTableWidget tasksTableWidget) {
        setWidget(binder.createAndBindUi(this));
        setAutoHideEnabled(false);
        setText("Task");
        setGlassEnabled(true);
        center();
        tasksClient = GWT.create(TasksClient.class);
        usersClient = GWT.create(UsersClient.class);
        this.tasksTableWidget = tasksTableWidget;

    }
    @Override
    public void show() {
        if(taskDto != null) {
            creatorText.setText(taskDto.getCreator());
            titleText.setText(taskDto.getName());
            descriptionText.setText(taskDto.getDescription());
            for (int index = 0; index < statusListBox.getItemCount(); index++) {
                if (statusListBox.getValue(index).equals(taskDto.getStatusId())) {
                    statusListBox.setSelectedIndex(index);
                    break;
                }
            }
            assigneeListBox.clear();
            assigneeListBox.addItem("", "1");
            if(taskDto.getAssigneeId() > 0) {
                assigneeListBox.addItem(taskDto.getAssignee(), taskDto.getAssigneeId().toString());
                assigneeListBox.setSelectedIndex(1);
            }
            refreshUsers();
            super.show();
        }
    }
    public void refreshUsers(){
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            usersClient.getAllUsers(token, new MethodCallback<List<UserDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log(throwable.toString());
                    GWT.log(throwable.getMessage());
                    Window.alert("Невозможно получить список пользователей: Сервер не отвечает");
                }

                @Override
                public void onSuccess(Method method, List<UserDto> i) {
                    GWT.log("Received " + i.size() + " users");
                    List<UserDto> users = new ArrayList<>();
                    users.addAll(i);
                    users.stream().forEach(userDto -> {
                        if (userDto.getId() != taskDto.getAssigneeId())
                            assigneeListBox.addItem(userDto.getUsername(), userDto.getId().toString());
                    });
                }
            });
        }
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            //taskDto.setAssignee(assigneeListBox.getSelectedItemText());
            taskDto.setAssigneeId(Long.parseLong(assigneeListBox.getSelectedValue()));
            taskDto.setName(titleText.getText());
            taskDto.setDescription(descriptionText.getText());
            taskDto.setStatusId(statusListBox.getSelectedValue());

            tasksClient.updateTask(token, taskDto.getId().toString(),
                    assigneeListBox.getSelectedValue(),
                    titleText.getText(),
                    descriptionText.getText(),
                    statusListBox.getSelectedValue(),
                    new MethodCallback<Void>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            GWT.log(throwable.toString());
                            GWT.log(throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(Method method, Void result) {
                            tasksTableWidget.refresh();
                        }
                    });
            this.hide();
        }
    }
    @UiHandler("btnCancel")
    public void cancelClick(ClickEvent event) {

        this.hide();
    }
    @UiHandler("btnDelete")
    public void deleteClick(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            tasksClient.removeTask(token, taskDto.getId().toString(), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log(throwable.toString());
                    GWT.log(throwable.getMessage());
                }

                @Override
                public void onSuccess(Method method, Void result) {
                    tasksTableWidget.refresh();
                }
            });
            this.hide();
            //tasksTableWidget.refresh();
        }
    }
}
