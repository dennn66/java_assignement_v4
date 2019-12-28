package com.dennn66.gwt.client;


import com.dennn66.gwt.common.TaskDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
    private WebApp webApp;
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

    public TasksTableWidget(WebApp webApp) {
        this.webApp = webApp;
        initWidget(uiBinder.createAndBindUi(this));
        client = GWT.create(TasksClient.class);

        UpdateTaskDialog m = new UpdateTaskDialog(this);
        m.hide();

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
                return (task.getAssignee() == null)?"":task.getAssignee().getUsername();
            }
        };
        table.addColumn(assigneeColumn, "Assignee");

        TextColumn<TaskDto> creatorColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getCreator().getUsername();
            }
        };
        table.addColumn(creatorColumn, "Creator");

        TextColumn<TaskDto> statusColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getStatus();
            }
        };
        table.addColumn(statusColumn, "Status");

        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(nameColumn, 400, Style.Unit.PX);
        table.setColumnWidth(descriptionColumn, 400, Style.Unit.PX);
        table.setColumnWidth(assigneeColumn, 200, Style.Unit.PX);
        table.setColumnWidth(creatorColumn, 200, Style.Unit.PX);
        table.setColumnWidth(statusColumn, 200, Style.Unit.PX);

        refresh();
    }

    public void refresh() {
        String token = Utils.getToken();
        GWT.log("getAllTasks: STORAGE: " + token);
        if(token == null) {
            webApp.refresh();
        }else {
            client.getAllTasks(token, creatorFilter, nameFilter, statusFilter, new MethodCallback<List<TaskDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log(throwable.toString());
                    GWT.log(throwable.getMessage());
                    GWT.log(Integer.toString(method.getResponse().getStatusCode()));
                    if(method.getResponse().getStatusCode() == 401){
                        Utils.removeToken();
                        webApp.refresh();
                    } else
                        Window.alert("Невозможно получить список задач: " + method.getResponse().getText() + " Status code: " + method.getResponse().getStatusCode());
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

    @UiHandler("btnLogout")
    public void logoutClick(ClickEvent event) {
        Utils.removeToken();
        webApp.refresh();
    }
}
