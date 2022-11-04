package com.redhat.dto;

/**
 * @author Mahdi Sharifi
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    @JsonProperty("customer_id")
    private Integer customerId;

    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    private String suffix;

    @Email
    private String email;

    private String phone;

}