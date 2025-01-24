package com.web.infrastruture.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AccountBO {
    @Id
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "COUNT_QUERY_CASH")
    private Long countQueryCash;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;

    @Column(name = "CREATED_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "ACCOUNT_STATE_ID")
    private Long accountStateId;

    @Column(name = "ACTIVE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activeTime;

    @Column(name = "RE_ACTIVE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reActiveTime;

    @Column(name = "ACCOUNT_TYPE_ID")
    private Long accountTypeId;

    @Column(name = "IS_CURRENT")
    private Long isCurrent;

    @Column(name = "MODIFIED_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;

    @Column(name = "VPAN")
    private String vpan;

    @Column(name = "BALANCE")
    private String balance;

    @Column(name = "LAST_RESET_PIN")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastResetPin;

    @Column(name = "HOLDING_BALANCE")
    private String holdingBalance;

    @Column(name = "AVAILABLE_BALANCE")
    private String availableBalance;

    @Column(name = "LAST_CHANGE_BALANCE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastChangeBalanceTime;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "PARTY_ROLE_ID")
    private Long partyRoleId;

    @Column(name = "PARTITION_KEY")
    private String partitionKey;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "LAST_TRANS_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTransTime;


}
