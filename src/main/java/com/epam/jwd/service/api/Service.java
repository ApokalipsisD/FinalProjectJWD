package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * Interface with service representation of DAO methods
 *
 * @param <T> - Service abstract DTO entity
 * @param <K> - type of id field
 */
public interface Service<T extends AbstractDto<K>, K> {
    /**
     * Method for saving DTO object to DB
     *
     * @param entity - DTO entity to save
     * @return - created entity
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    T create(T entity) throws ServiceException;

    /**
     * Method for updating entity in DB
     *
     * @param entity - entity to update
     * @return - true if data was updated, otherwise false or exception
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    boolean update(T entity) throws ServiceException;

    /**
     * Method for deleting entity from DB
     *
     * @param entity - entity to delete
     * @return - true if entity was deleted from DB, otherwise false or exception
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    boolean delete(T entity) throws ServiceException;

    /**
     * Method for getting entity object by id from DB
     *
     * @param id - entity's id
     * @return - entity with provided id
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    T getById(K id) throws ServiceException;

    /**
     * Method for extracting all entities from DB
     *
     * @return - List of entities
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    List<T> getAll() throws ServiceException;
}
