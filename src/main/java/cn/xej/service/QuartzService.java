package cn.xej.service;


import org.quartz.JobDataMap;
import org.springframework.scheduling.quartz.QuartzJobBean;

public interface QuartzService {

    /**
     * 新增一个定时任务
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param tName 触发器名称
     * @param tGroup 触发器组
     * @param cron cron表达式
     * @param jobClass 任务类
     * @param jobDataMap 任务类使用的数据
     */
    void addJob(String jName, String jGroup, String tName, String tGroup, String cron, Class<? extends QuartzJobBean> jobClass, JobDataMap jobDataMap);

    /**
     * 暂停定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    void pauseJob(String jName, String jGroup);

    /**
     * 继续定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    void resumeJob(String jName, String jGroup);

    /**
     * 删除定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    void deleteJob(String jName, String jGroup);
}
