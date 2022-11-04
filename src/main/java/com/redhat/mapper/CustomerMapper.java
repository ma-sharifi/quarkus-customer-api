package com.redhat.mapper;

/**
 * @author Mahdi Sharifi
 */
import com.redhat.dto.CustomerDto;
import com.redhat.entity.CustomerEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {

    List<CustomerDto> toDomainList(List<CustomerEntity> entities);

    CustomerDto toDomain(CustomerEntity entity);

    @InheritInverseConfiguration(name = "toDomain")
    CustomerEntity toEntity(CustomerDto domain);

    void updateEntityFromDomain(CustomerDto domain, @MappingTarget CustomerEntity entity);

    void updateDomainFromEntity(CustomerEntity entity, @MappingTarget CustomerDto domain);

}
