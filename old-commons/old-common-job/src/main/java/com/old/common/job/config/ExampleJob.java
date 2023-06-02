package com.old.common.job.config;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ExampleJob extends IJobHandler {
    @XxlJob("exampleJob")
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        log.debug("样例定时任务执行，执行参数：{}", param);

        return ReturnT.SUCCESS;
    }
}
