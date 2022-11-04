package com.redhat.resource;

/**
 * @author Mahdi Sharifi
 */
import com.redhat.dto.CustomerDto;
import com.redhat.exception.ServiceException;
import com.redhat.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "customer", description = "CustomerDto Operations")
@AllArgsConstructor
@Slf4j
public class CustomerResource {

    private final CustomerService customerService;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Customers",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = CustomerDto.class)
            )
    )
    public Response get() {
        log.info("Get All Customers called!");
        return Response.ok(customerService.findAll()).build();
    }

    @GET
    @Path("search")
    @APIResponse(
            responseCode = "200",
            description = "Search by Customer name",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = CustomerDto.class)
            )
    )
    public Response searchByFirstName(@RestQuery String firstName) {
        log.info("searchByFirstName called!");
        return Response.ok(customerService.findByFirsName(firstName)).build();
    }

    @GET
    @Path("/{customerId}")
    @APIResponse(
            responseCode = "200",
            description = "Get CustomerDto by customerId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = CustomerDto.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "CustomerDto does not exist for customerId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getById(@Parameter(name = "customerId", required = true) @PathParam("customerId") Integer customerId) {
        return customerService.findById(customerId)
                .map(customer -> Response.ok(customer).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "CustomerDto Created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = CustomerDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid CustomerDto",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "CustomerDto already exists for customerId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response post(@NotNull @Valid CustomerDto customer, @Context UriInfo uriInfo) {
        customerService.save(customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(customer.getCustomerId())).build();
        return Response.created(uri).entity(customer).build();
    }

    @PUT
    @Path("/{customerId}")
    @APIResponse(
            responseCode = "204",
            description = "CustomerDto updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = CustomerDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid CustomerDto",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "CustomerDto object does not have customerId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Path variable customerId does not match CustomerDto.customerId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "No CustomerDto found for customerId provided",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response put(@Parameter(name = "customerId", required = true) @PathParam("customerId") Integer customerId, @NotNull @Valid CustomerDto customer) {
        if (!Objects.equals(customerId, customer.getCustomerId())) {
            throw new ServiceException("Path variable customerId does not match CustomerDto.customerId");
        }
        customerService.update(customer);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}