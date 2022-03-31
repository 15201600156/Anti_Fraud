package com.study.anti_fraud.antifaud_reptile_service.untils;


import com.study.anti_fraud.antifraud_common.untils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnowflakeComponentUtil {
    @Value("${server.datacenterId}")
    private long datacenterId;

    @Value("${server.workId}")
    private long workId;


    private static volatile SnowflakeIdWorkerUtil instance;

    public SnowflakeIdWorkerUtil getInstance() {
        if (instance == null) {
            synchronized (SnowflakeIdWorkerUtil.class) {
                if (instance == null) {
                    log.info("when instance, workId = {}, datacenterId = {}", workId, datacenterId);
                    instance = new SnowflakeIdWorkerUtil(workId, datacenterId);
                }
            }
        }
        return instance;
    }
}
