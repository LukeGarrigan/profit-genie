package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.AbstractEntity;

import java.io.Serializable;

public interface DtoDomainConversion<T extends Serializable, E extends AbstractEntity> {

    /**
     * Perform a conversion from a domain object to a data transfer object
     * in an attempt to reduce the number of domain entities at the controller level
     *
     * @param domain
     * @return a dto
     */
    T toDto(E domain);
}
