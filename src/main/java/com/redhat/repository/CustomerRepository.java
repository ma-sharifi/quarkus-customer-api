package com.redhat.repository;

/**
 * @author Mahdi Sharifi
 */
import com.redhat.entity.CustomerEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<CustomerEntity, Integer> {

}