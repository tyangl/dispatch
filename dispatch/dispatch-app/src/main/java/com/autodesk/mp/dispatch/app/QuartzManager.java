package com.autodesk.mp.dispatch.app;

//简单的任务管理类  
//QuartzManager.java  

import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** *//** 
* @Title:Quartz管理类 
*  
* @Description: 
*  
* @Copyright:  
* @author zz  2008-10-8 14:19:01 
* @version 1.00.000 
* 
*/  
public class QuartzManager {  
 private static SchedulerFactory sf = new StdSchedulerFactory();  
 private static String JOB_GROUP_NAME = "transactionLog";  
 private static String TRIGGER_GROUP_NAME = "transactionTrigger";  
 private final static Logger logger=LoggerFactory.getLogger(QuartzManager.class);
   
 /** *//** 
  *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
  * @param jobName 任务名 
  * @param job     任务 
  * @param time    时间设置，参考quartz说明文档 
  * @throws SchedulerException 
  * @throws ParseException 
  */  
 public static void addJob(String jobName,Class<? extends Job> jobClass,String time,String triggerName)   
                             throws SchedulerException, ParseException{  
     Scheduler sched = sf.getScheduler();  
     JobDetail jobDetail =JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
     //触发器  
     CronTrigger  trigger =TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(triggerName, TRIGGER_GROUP_NAME)
    		 .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
          
    sched.scheduleJob(jobDetail,trigger);  
    logger.info("add job:"+jobName);
 }  
   
 /**
  * 定时器开关
  * @throws SchedulerException
  */
 public static boolean triggerScheduler() throws SchedulerException{
	 Scheduler sched = sf.getScheduler();  
	 if(sched.isInStandbyMode()){
		 sched.start();
		 logger.info("trigger scheduler start");
	 }
		 
	 else{
		 sched.standby();
		 logger.info("trigger scheduler standby");
	 }
	return !sched.isInStandbyMode();
 }
 
 
   
 /** *//** 
  * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
  * @param jobName 
  * @throws SchedulerException 
  */  
 public static void removeJob(String jobName,String triggerName)   
                             throws SchedulerException{  
     Scheduler sched = sf.getScheduler(); 
     JobKey jobKey=new JobKey(jobName);
     sched.pauseJob(jobKey);
     sched.unscheduleJob(new TriggerKey(triggerName));
     sched.deleteJob(jobKey);
 }  
 
 /**
  * 关闭定时器
  * @throws SchedulerException
  */
 public static void shutdown() throws SchedulerException{
	 Scheduler sched = sf.getScheduler(); 
	 sched.shutdown();
	 logger.info("scheduler shutdown");
 }
}  