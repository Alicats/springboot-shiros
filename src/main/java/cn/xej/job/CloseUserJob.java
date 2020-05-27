package cn.xej.job;

import cn.xej.service.UserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class CloseUserJob extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("用户注册任务计划结束..");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String userId = (String) jobDataMap.get("userId");

        log.info("用户注册id结束: "+userId);
        userService.closeUpdateUserStatus(userId);

    }
}
