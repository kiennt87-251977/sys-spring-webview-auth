package com.web.infrastruture.repository;

import com.web.infrastruture.entities.AccountBO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends BaseJPARepository<AccountBO, Long> {
    AccountBO findByAccountId(Long accountId);

    List<AccountBO> findBy();

}
