package com.dennn66.gwt.client;



import com.dennn66.gwt.common.TaskDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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

    private TasksClient client;

    @UiTemplate("TasksTable.ui.xml")
    interface TasksTableBinder extends UiBinder<Widget, TasksTableWidget> {
    }

    private static TasksTableBinder uiBinder = GWT.create(TasksTableBinder.class);

    public TasksTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));
//         table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TaskDto> nameColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        client = GWT.create(TasksClient.class);

        Column<TaskDto, TaskDto> actionColumn = new Column<TaskDto, TaskDto>(
                new ActionCell<TaskDto>("REMOVE", new ActionCell.Delegate<TaskDto>() {
                    @Override
                    public void execute(TaskDto taskDto) {
                        client.removeTask(taskDto.getId().toString(), new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable throwable) {
                                GWT.log(throwable.toString());
                                GWT.log(throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(Method method, Void result) {
                                refresh();
                            }
                        });
                    }
                })) {
            @Override
            public TaskDto getValue(TaskDto task) {
                return task;
            }
        };

        table.addColumn(actionColumn, "Actions");

        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(nameColumn, 400, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 200, Style.Unit.PX);

        refresh();
    }

    public void refresh() {
        client.getAllTasks(new MethodCallback<List<TaskDto>>() {
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
