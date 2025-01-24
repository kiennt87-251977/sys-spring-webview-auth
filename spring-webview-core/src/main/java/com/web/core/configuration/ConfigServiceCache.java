package com.web.core.configuration;

import org.springframework.beans.factory.annotation.Value;

public class ConfigServiceCache {

    @Value("${service.thread.excute.numb}")
    protected Integer threadExcuteNumb;

    @Value("${service.thread.queue.numb}")
    protected Integer threadQueueNumb;

    @Value("${trans.batch.size}")
    protected Integer transBatchSize;

    @Value("${service.test.mode}")
    protected Boolean testMode;


    public Integer getTransBatchSize() {
        return transBatchSize;
    }

    public void setTransBatchSize(Integer transBatchSize) {
        this.transBatchSize = transBatchSize;
    }

    public Boolean getTestMode() {
        return testMode;
    }

    public void setTestMode(Boolean testMode) {
        this.testMode = testMode;
    }

    public Integer getThreadExcuteNumb() {
        return threadExcuteNumb;
    }

    public void setThreadExcuteNumb(Integer threadExcuteNumb) {
        this.threadExcuteNumb = threadExcuteNumb;
        if (this.threadExcuteNumb < 1) {
            this.threadExcuteNumb = 1;
        }
        if (this.threadExcuteNumb > 10) {
            this.threadExcuteNumb = 10;
        }
    }

    public Integer getThreadQueueNumb() {
        return threadQueueNumb;
    }

    public void setThreadQueueNumb(Integer threadQueueNumb) {
        this.threadQueueNumb = threadQueueNumb;
    }
}
