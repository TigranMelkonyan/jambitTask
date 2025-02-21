package com.jambit.infrastructure.outbound.persistence.validation;

import com.jambit.domain.common.exception.RecordPersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 3:27 PM
 */
public abstract class PersistenceErrorProcessor {

    protected void handlePersistenceException(String operation, Exception e) {
        if (e instanceof DataIntegrityViolationException) {
            throw new RecordPersistenceException("Data integrity violation while " + operation + ": " + e.getMessage());
        } else if (e instanceof JpaSystemException) {
            throw new RecordPersistenceException("JPA system error while " + operation + ": " + e.getMessage());
        } else {
            throw new RecordPersistenceException("Unexpected error while " + operation + ": " + e.getMessage());
        }
    }
}
