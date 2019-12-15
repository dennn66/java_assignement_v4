package com.dennn66.gwt.client;


import com.dennn66.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/v1/tasks")
public interface TasksClient extends RestService {
    @GET
    void getAllTasks(MethodCallback<List<TaskDto>> items);

    @DELETE
    @Path("{id}")
    void removeTask(@PathParam("id") String id, MethodCallback<Void> result);
}
