package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AccountService implementation class of Service for AccountDto with Integer id
 */
public class AccountServiceImpl implements Service<AccountDto, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private final AccountDao accountDao = new AccountDao();
    private final Validator<AccountDto, Integer> validator = new AccountValidator();
    private final Converter<Account, AccountDto, Integer> converter = new AccountConverter();

    @Override
    public AccountDto create(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        try {
            return converter.convert(accountDao.save(converter.convert(accountDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        try {
            return accountDao.update(converter.convert(accountDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        try {
            return accountDao.delete(converter.convert(accountDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        Account result;
        try {
            result = accountDao.findById(id);
            if (Objects.isNull(result)) {
                throw new DaoException(MessageException.ACCOUNT_NOT_FOUND_EXCEPTION);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return converter.convert(result);
    }

    @Override
    public List<AccountDto> getAll() throws ServiceException {
        List<Account> accountList;
        List<AccountDto> accountDtoList = new ArrayList<>();
        try {
            accountList = accountDao.findAll();
            accountList.forEach(account -> accountDtoList.add(converter.convert(account)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return accountDtoList;
    }

    /**
     * Method which gets account by user id
     *
     * @param id - current user id
     * @return - AccountDto with user id
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public AccountDto getAccountByUserId(Integer id) throws ServiceException {
        validator.validateId(id);
        try {
            return converter.convert(accountDao.getAccountByUserId(id));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Method which gets all teacher accounts
     *
     * @return - list of teacher accounts
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public List<AccountDto> getAllTeachers() throws ServiceException {
        List<Account> accountList;
        List<AccountDto> accountDtoList = new ArrayList<>();
        try {
            accountList = accountDao.findAllTeachers();
            accountList.forEach(account -> accountDtoList.add(converter.convert(account)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return accountDtoList;
    }

    /**
     * Method which updates user role
     *
     * @param accountId - current account id
     * @param id        - user role
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public void updateRole(Integer accountId, Integer id) throws ServiceException {
        validator.validateId(id);
        validator.validateId(accountId);
        try {
            accountDao.updateRole(accountId, id);
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }
}
