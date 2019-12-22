package com.dennn66.gwt.client;


import com.dennn66.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/v1/tasks")
public interface TasksClient extends RestService {
    @GET
    void getAllTasks(
            @HeaderParam("Authorization") String token,
            @HeaderParam("Access-Control-Request-Headers") String origin,
            @QueryParam("creatorFilter") String creatorFilter,
            @QueryParam("nameFilter") String nameFilter,
            @QueryParam("statusFilter") String statusFilter,
            MethodCallback<List<TaskDto>> tasks);
    @DELETE
    @Path("/{id}")
    void removeTask(@PathParam("id") String id, MethodCallback<Void> result);
    @PUT
    @Path("/{id}")
    void updateTask(@PathParam("id") String id,
                    @QueryParam("assigneeId") String assigneeId,
                    @QueryParam("title") String title,
                    @QueryParam("description") String description,
                    @QueryParam("statusId") String statusId,
                    MethodCallback<Void> result);
}
