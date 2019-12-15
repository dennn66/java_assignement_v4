package com.dennn66.gwt.client;


import com.dennn66.gwt.common.TaskDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class TasksTableWidget extends Composite {
    @UiField
    CellTable<TaskDto> table;
    SimplePager pager;

    private String creatorFilter;
    private String nameFilter;
    private String statusFilter;

    public void setCreatorFilter(String creatorFilter) {
        this.creatorFilter = creatorFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public void setStatusFilter(String statusFilter) {
        this.statusFilter = statusFilter;
    }



    private TasksClient client;

    @UiTemplate("TasksTable.ui.xml")
    interface TasksTableBinder extends UiBinder<Widget, TasksTableWidget> {
    }

    private static TasksTableBinder uiBinder = GWT.create(TasksTableBinder.class);

    public TasksTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        client = GWT.create(TasksClient.class);

        UpdateTaskDialog m = new UpdateTaskDialog(this);
        m.hide();

//         table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        Column<TaskDto, String> nameColumn = new Column<TaskDto, String>(new ClickableTextCell()) {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getName();
            }
            @Override
            public void render(Cell.Context context, TaskDto taskDto, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<a>" + taskDto.getName() + "</a>");
            }
        };
        nameColumn.setFieldUpdater(new FieldUpdater<TaskDto, String>() {
            @Override
            public void update(int i, TaskDto taskDto, String s) {
                m.setTaskDto(taskDto);
                m.show();
            }
        });
        table.addColumn(nameColumn, "Title");

        TextColumn<TaskDto> descriptionColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getDescription();
            }
        };
        table.addColumn(descriptionColumn, "Description");

        TextColumn<TaskDto> assigneeColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getAssignee();
            }
        };
        table.addColumn(assigneeColumn, "Assignee");

        TextColumn<TaskDto> creatorColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getCreator();
            }
        };
        table.addColumn(creatorColumn, "Creator");

        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(nameColumn, 400, Style.Unit.PX);
        table.setColumnWidth(descriptionColumn, 400, Style.Unit.PX);
        table.setColumnWidth(assigneeColumn, 200, Style.Unit.PX);
        table.setColumnWidth(creatorColumn, 200, Style.Unit.PX);

        refresh();
    }

    public void refresh() {
        client.getAllTasks(creatorFilter, nameFilter, statusFilter, new MethodCallback<List<TaskDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список задач: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDto> i) {
                GWT.log("Received " + i.size() + " tasks");
                List<TaskDto> tasks = new ArrayList<>();
                tasks.addAll(i);
                table.setRowData(tasks);
            }
        });
    }
}
