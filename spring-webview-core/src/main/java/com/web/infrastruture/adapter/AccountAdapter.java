package com.web.infrastruture.adapter;


import com.web.infrastruture.entities.AccountBO;
import com.web.infrastruture.repository.AccountRepository;
import com.web.infrastruture.store.AccountStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountAdapter implements AccountStore {

//    @Autowired
//    private AccountRepository accountRepository;


    public AccountBO findByAccountId(Long accountId) {
//        return accountRepository.findByAccountId(accountId);
        return null;
    }

    public List<AccountBO> findBy() {
//        return accountRepository.findBy();
        return null;
    }

}
