package com.dennn66.gwt.client;


import com.dennn66.gwt.common.TaskCreateDto;
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
            @QueryParam("creatorFilter") String creatorFilter,
            @QueryParam("nameFilter") String nameFilter,
            @QueryParam("statusFilter") String statusFilter,
            MethodCallback<List<TaskDto>> tasks);
    @DELETE
    @Path("/{id}")
    void removeTask(
            @HeaderParam("Authorization") String token,
            @PathParam("id") String id, MethodCallback<Void> result);
    @PUT
    @Path("/{id}")
    void updateTask(@HeaderParam("Authorization") String token,
                    @BeanParam TaskDto TaskDto,
                    MethodCallback<Void> result);
    @POST
    void addTask(@HeaderParam("Authorization") String token,
                 @BeanParam TaskCreateDto taskCreateDto,
                 MethodCallback<Void> result);
}
