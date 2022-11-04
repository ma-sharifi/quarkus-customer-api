package com.redhat.exception;

import com.redhat.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Mahdi Sharifi
 */

@Provider
@Slf4j
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }

}