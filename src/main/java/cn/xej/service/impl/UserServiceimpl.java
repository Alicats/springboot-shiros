package cn.xej.service.impl;

import cn.xej.common.Constance;
import cn.xej.common.RespObj;
import cn.xej.common.Utils;
import cn.xej.job.CloseUserJob;
import cn.xej.job.StartUserJob;
import cn.xej.mapper.UserDao;
import cn.xej.pojo.User;
import cn.xej.service.QuartzService;
import cn.xej.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceimpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private QuartzService quartzService;

    @Override
    public User findByUserId(String userId) {

        return userDao.findByUserId(userId);
    }

    @Override
    public List<String> getRolesIdByUserId(String userId) {
        return userDao.queryRolesIdByUserId(userId);
    }

    /**
     * 图片下载
     * @param response
     */
    @Override
    public void imageDownload(HttpServletResponse response) {
        try {
            File file = ResourceUtils.getFile( "classpath:static/statics/images/2.png");


            if(file.exists()){ //判断文件是否存在
                response.setContentType("image/png");
                response.setCharacterEncoding("UTF-8");
                // response.setContentType("application/force-download");

                response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(file.getName(),"UTF-8"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null; //文件输入流
                BufferedInputStream bis = null;

                OutputStream os = null; //输出流
                try {
                    os = response.getOutputStream();
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int i = bis.read(buffer);
                    while(i != -1){
                        os.write(buffer);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    bis.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public RespObj addUser(String userId, String password,String name,String date,Integer time) {
        User user = new User();
        user.setUserId(userId);

        String pwd = new SimpleHash("MD5",password,userId,1024).toString();
        user.setPassword(pwd);
        user.setName(name);
        user.setStatus(0);

        /*
        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            Date date1 = sf.parse(date);
            //打印Date
            System.out.println(date1);
            user.setDate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar calendar = new GregorianCalendar();

        try{
            calendar.setTime(sdf.parse(date));
            //calendar.add(Calendar.DAY_OF_YEAR, 1);
            //calendar.add(Calendar.HOUR_OF_DAY,1);
            Date date1 = calendar.getTime();
            user.setDate(date1);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        user.setTime(time);
        // 添加注册的用户
        userDao.addUser(user);

        // 添加定时任务
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId",userId);
        jobDataMap.put("time",time);
        jobDataMap.put("date",date);
        String cronExpression = Utils.dateStrConvertCronExpression(date,null);
        quartzService.addJob(Constance.USER_JOB_START+userId,Constance.USER_JOB_GROUP,Constance.USER_TRIGGER_START+userId,Constance.USER_TRIGGER_GROUP,cronExpression, StartUserJob.class,jobDataMap);

        return RespObj.build(200,"success",null);
    }

    @Override
    public RespObj startUpdateUserStatus(String userId,Integer time,String date) {
        log.info("开始改变用户status为1："+userId);
//        修改用户状态为：1
        userDao.startUpdateUserStatus(userId);
//        删除开始注册的定时任务
        quartzService.deleteJob(Constance.USER_JOB_START+userId,Constance.USER_JOB_GROUP);
//        新增一个注册结束的定时任务
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId",userId);
        String cronExpression = Utils.dateStrConvertCronExpression(date, time);
        quartzService.addJob(Constance.USER_JOB_CLOSE+userId,Constance.USER_JOB_GROUP,Constance.USER_TRIGGER_CLOSE+userId,Constance.USER_TRIGGER_GROUP,cronExpression, CloseUserJob.class,jobDataMap);

        return RespObj.build(200,"success",null);
    }

    @Override
    public RespObj closeUpdateUserStatus(String userId) {
        log.info("用户注册结束用户状态为2.："+userId);
//        修改用户状态为：2
        userDao.closeUpdateUserStatus(userId);
//        删除考试结束的定时任务
        quartzService.deleteJob(Constance.USER_JOB_CLOSE+userId,Constance.USER_JOB_GROUP);

        return RespObj.build(200,"success",null);
    }

    @Override
    public RespObj stopJob(String userId) {
        User user = userDao.findByUserId(userId);
        if(user.getStatus()==2){
            return RespObj.build(400,"fail","任务已结束");
        }

        quartzService.pauseJob(Constance.USER_JOB_CLOSE+userId,Constance.USER_JOB_GROUP); // 实现暂停
        return RespObj.build(200,"success",null);
    }

    @Override
    public RespObj resumeJob(String userId) {
        User user = userDao.findByUserId(userId);
        if(user.getStatus()==2){
            return RespObj.build(400,"fail","任务已结束");
        }
        quartzService.resumeJob(Constance.USER_JOB_CLOSE+userId,Constance.USER_JOB_GROUP);
        return RespObj.build(200,"success",null);
    }
}
