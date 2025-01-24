package com.web.infrastruture.store;


import com.web.infrastruture.entities.AccountBO;

import java.util.List;


public interface AccountStore {

    AccountBO findByAccountId(Long accountId);

    List<AccountBO> findBy();

}
