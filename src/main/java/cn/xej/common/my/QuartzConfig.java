package cn.xej.common.my;

import cn.xej.test.Task1;

import cn.xej.test.Task2;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testQuartz1(){
        return JobBuilder.newJob(Task1.class).withIdentity("task1").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        //5秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("task1")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail testQuartz2() {
        return JobBuilder.newJob(Task2.class).withIdentity("task2").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        //cron方式，每隔5秒执行一次
        return TriggerBuilder.newTrigger().forJob(testQuartz2())
                .withIdentity("task2")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * * * ?"))
                .build();
    }

}
