package cn.xej.job;

import cn.xej.service.UserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class StartUserJob extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("用户注册任务计划开始..");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String userId = (String) jobDataMap.get("userId");
        Integer time = (Integer) jobDataMap.get("time");
        String date = (String) jobDataMap.get("date");

        log.info("用户注册开始id: "+userId+" 时长: "+time+" 日期: "+date);
        userService.startUpdateUserStatus(userId,time,date);
    }
}
