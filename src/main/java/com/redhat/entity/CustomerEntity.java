package com.redhat.entity;

/**
 * @author Mahdi Sharifi
 */
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity(name = "customer")
@Table(name = "customer")
@Data
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone")
    private String phone;

}