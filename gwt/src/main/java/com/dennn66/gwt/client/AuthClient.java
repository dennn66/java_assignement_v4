package com.dennn66.gwt.client;

import com.dennn66.gwt.common.JwtAuthRequestDto;
import com.dennn66.gwt.common.JwtAuthResponseDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface AuthClient extends RestService {
    @POST
    @Path("http://localhost:8189/gwt-rest/authenticate")
    void authenticate(@BeanParam() JwtAuthRequestDto authRequest, MethodCallback<JwtAuthResponseDto> result);
}