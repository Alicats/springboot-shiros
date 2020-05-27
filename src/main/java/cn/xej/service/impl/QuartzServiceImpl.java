package cn.xej.service.impl;


import cn.xej.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;


@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 新增一个定时任务
     *
     * @param jName    任务名称
     * @param jGroup   任务组
     * @param tName    触发器名称
     * @param tGroup   触发器组
     * @param cron     cron表达式
     * @param jobClass 任务类
     * @param jobDataMap 任务类使用的数据
     */
    @Override
    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron, Class<? extends QuartzJobBean> jobClass,JobDataMap jobDataMap) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jName, jGroup)
                    .usingJobData(jobDataMap)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tName, tGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void pauseJob(String jName, String jGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jName, jGroup));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 继续定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void resumeJob(String jName, String jGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void deleteJob(String jName, String jGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
