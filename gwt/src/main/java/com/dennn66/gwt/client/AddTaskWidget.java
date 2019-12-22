package com.dennn66.gwt.client;

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

//    @UiTemplate("AddTaskWidget.ui.xml")
//    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskWidget> {
//    }

    private static final Binder binder = GWT.create(Binder.class);
    interface Binder extends UiBinder<Widget, AddTaskWidget> {
    }

//    private static AddTaskWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskWidget.AddTaskFormBinder.class);

    public AddTaskWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(binder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        usersClient = GWT.create(UsersClient.class);
        tasksClient = GWT.create(TasksClient.class);
        refreshUsers();
    }
//
//    @UiHandler("form")
//    public void onSubmit(FormPanel.SubmitEvent event) {
//
//        if (titleText.getText().length() < 4) {
//            Window.alert("Название задачи должно быть не менее 4 символов");
//            event.cancel();
//        }
//    }
//
//    @UiHandler("form")
//    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
//        titleText.setText("");
//        descriptionText.setText("");
//        assigneeListBox.setSelectedIndex(0);
//        tasksTableWidget.refresh();
//    }
//
//    @UiHandler("btnSubmit")
//    public void submitClick(ClickEvent event) {
//        form.submit();
//    }

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
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("getAllUsers STORAGE: " + token);
        if(token == null) {

        } else {
            //taskDto.setAssignee(assigneeListBox.getSelectedItemText());
//            taskDto.setAssigneeId(Long.parseLong(assigneeListBox.getSelectedValue()));
//            taskDto.setName(titleText.getText());
//            taskDto.setDescription(descriptionText.getText());
//            taskDto.setStatusId(statusListBox.getSelectedValue());

            tasksClient.addTask(token,
                    assigneeListBox.getSelectedValue(),
                    titleText.getText(),
                    descriptionText.getText(),
                    new MethodCallback<Void>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            GWT.log(throwable.toString());
                            GWT.log(throwable.getMessage());
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
