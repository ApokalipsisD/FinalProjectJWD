package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.impl.AccountDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountServiceImpl implements Service<AccountDto, Integer> {
    private final AccountDao accountDao = new AccountDao();
    private final Validator<AccountDto, Integer> validator = new AccountValidator();
    private final Converter<Account, AccountDto, Integer> converter = new AccountConverter();

    @Override
    public AccountDto create(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        return converter.convert(accountDao.save(converter.convert(accountDto)));
    }

    @Override
    public boolean update(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        return accountDao.update(converter.convert(accountDto));
    }

    @Override
    public boolean delete(AccountDto accountDto) throws ServiceException {
        validator.validate(accountDto);
        return accountDao.delete(converter.convert(accountDto));
    }

    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        Account result = accountDao.findById(id);
        if (Objects.isNull(result)) {
            throw new ServiceException(MessageException.ACCOUNT_NOT_FOUND_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<AccountDto> getAll() {
        List<Account> accountList = accountDao.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();
        // validate?
        accountList.forEach(account -> accountDtoList.add(converter.convert(account)));
        return accountDtoList;
    }

    //todo validation
    public AccountDto getAccountByUserId(Integer id) {
        return converter.convert(accountDao.getAccountByUserId(id));
    }

    public List<AccountDto> getAllTeachers() {
        List<Account> accountList = accountDao.findAllTeachers();
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountList.forEach(account -> accountDtoList.add(converter.convert(account)));
        return accountDtoList;
    }

    public void updateRole(Integer accountId, Integer id) {
        accountDao.updateRole(accountId, id);
    }
}
