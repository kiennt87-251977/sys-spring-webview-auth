package com.auth.core.aop.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
public class GlobalTraceCountObject {

    private String traceId;
    private AtomicInteger numbRate;
    private AtomicInteger level;
    private Date dateCreated;
    private Boolean isFinish;

}
