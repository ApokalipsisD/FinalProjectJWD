package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.AccountDto;

public class AccountConverter implements Converter<Account, AccountDto, Integer> {
    @Override
    public Account convert(AccountDto accountDto) {
        return new Account(accountDto.getId(),
                accountDto.getFirstName(),
                accountDto.getLastName(),
                accountDto.getEmail(),
                accountDto.getBirthDate(),
                accountDto.getRoleId(),
                accountDto.getUserId());
    }

    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getBirthDate(),
                account.getRoleId(),
                account.getUserId());
    }
}
