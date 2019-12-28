package com.dennn66.gwt.client;


import com.dennn66.gwt.common.UserReferenceDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import java.util.List;

@Path("/v1/users")
public interface UsersClient extends RestService {
    @GET
    void getAllUsers(
            @HeaderParam("Authorization") String token,
            MethodCallback<List<UserReferenceDto>> users);

}
