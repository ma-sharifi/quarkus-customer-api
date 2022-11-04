package com.redhat.repository;

/**
 * @author Mahdi Sharifi
 */
import com.redhat.entity.CustomerEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<CustomerEntity, Integer> {

    // put your custom logic here as instance methods
    public List<CustomerEntity> findByFirsName(String firsName){
        return find("first_name", firsName).list();
    }
}