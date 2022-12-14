package com.redhat.service;

/**
 * @author Mahdi Sharifi
 */

import com.redhat.dto.CustomerDto;
import com.redhat.entity.CustomerEntity;
import com.redhat.exception.ServiceException;
import com.redhat.mapper.CustomerMapper;
import com.redhat.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDto> findAll() {
        return this.customerMapper.toDomainList(customerRepository.findAll().list());
    }

    public List<CustomerDto> findByFirsName(String firstName) {
        return this.customerMapper.toDomainList(customerRepository.findByFirsName(firstName));
    }

    public Long count() {
        return customerRepository.count();
    }

    public Optional<CustomerDto> findById(@NonNull Integer customerId) {
        return customerRepository.findByIdOptional(customerId)
                .map(customerMapper::toDomain);
    }

    @Transactional
    public void save(@Valid CustomerDto customer) {
        log.debug("Saving CustomerDto: {}", customer);
        CustomerEntity entity = customerMapper.toEntity(customer);
        customerRepository.persist(entity);
        customerMapper.updateDomainFromEntity(entity, customer);
    }

    @Transactional
    public void update(@Valid CustomerDto customer) {
        log.debug("Updating CustomerDto: {}", customer);
        if (Objects.isNull(customer.getCustomerId())) {
            throw new ServiceException("CustomerDto does not have a customerId");
        }
        CustomerEntity entity = customerRepository.findByIdOptional(customer.getCustomerId())
                .orElseThrow(() -> new ServiceException("No CustomerDto found for customerId[%s]", customer.getCustomerId()));
        customerMapper.updateEntityFromDomain(customer, entity);
        customerRepository.persist(entity);
        customerMapper.updateDomainFromEntity(entity, customer);
    }

}