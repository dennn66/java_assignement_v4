package com.dennn66.gwt.client;

import com.dennn66.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class AddTaskFormWidget extends Composite {

    private UsersClient usersClient;

    @UiField
    FormPanel form;

    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox assigneeListBox;

    private TasksTableWidget tasksTableWidget;

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddTaskFormBinder.class);

    public AddTaskFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("v1/tasks"));
        this.tasksTableWidget = tasksTableWidget;
        usersClient = GWT.create(UsersClient.class);
        refreshUsers();
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {

        if (titleText.getText().length() < 4) {
            Window.alert("Название задачи должно быть не менее 4 символов");
            event.cancel();
        }
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        titleText.setText("");
        descriptionText.setText("");
        assigneeListBox.setSelectedIndex(0);
        tasksTableWidget.refresh();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }

    public void refreshUsers(){
        usersClient.getAllUsers(new MethodCallback<List<UserDto>>() {
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
                    assigneeListBox.addItem(userDto.getName(), userDto.getId().toString());
                });
            }
        });
    }
}
