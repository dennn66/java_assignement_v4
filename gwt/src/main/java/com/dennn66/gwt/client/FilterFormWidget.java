package com.dennn66.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class FilterFormWidget extends Composite {

    private TasksClient client;


    @UiField
    TextBox creatorText;

    @UiField
    TextBox titleText;

    @UiField
    ListBox statusListBox;

    private TasksTableWidget tasksTableWidget;

    @UiTemplate("FilterForm.ui.xml")
    interface FilterFormBinder extends UiBinder<Widget, FilterFormWidget> {
    }

    private static FilterFormWidget.FilterFormBinder uiBinder = GWT.create(FilterFormWidget.FilterFormBinder.class);

    public FilterFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        client = GWT.create(TasksClient.class);
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        tasksTableWidget.setCreatorFilter(creatorText.getText());
        tasksTableWidget.setNameFilter(titleText.getText());
        tasksTableWidget.setStatusFilter(statusListBox.getSelectedValue());
        tasksTableWidget.refresh();
    }
    @UiHandler("btnClear")
    public void clearClick(ClickEvent event) {
        creatorText.setText("");
        titleText.setText("");
        statusListBox.setSelectedIndex(0);
        tasksTableWidget.setCreatorFilter(creatorText.getText());
        tasksTableWidget.setNameFilter(titleText.getText());
        tasksTableWidget.setStatusFilter(statusListBox.getSelectedValue());
        tasksTableWidget.refresh();
    }
}
