package com.autodesk.mp.dispatch.app.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;

import com.autodesk.mp.dispatch.app.DailySnapShotTask;
import com.autodesk.mp.dispatch.app.HIdeNeedsTask;
import com.autodesk.mp.dispatch.app.QuartzManager;
import com.autodesk.mp.dispatch.app.ScheduleConfig;
import com.autodesk.mp.dispatch.app.WithdrawFromAccountTask;
import com.autodesk.mp.dispatch.app.ContractTask;
/**
 * 交易日志计划任务web启动的context listener
 * @author leiyang
 *
 */
public class ScheduleContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			QuartzManager.shutdown();
		} catch (SchedulerException e) {
			//e.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		//每日记录账户快照
		String transaction_host=ScheduleConfig.SCHEDULE_HOST;
		String design_host=	ScheduleConfig.DESIGN_HOST;
		
				try {
					
					QuartzManager.addJob("dailySnapShot", DailySnapShotTask.class, ScheduleConfig.DAILY_SNAPSHOT_CRON, "dailySnapShotTrigger");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//14天做提现操作
				try {
					QuartzManager.addJob("withdrawFromAccount", WithdrawFromAccountTask.class, ScheduleConfig.WITHDRAW_FROM_ACCOUNT_CRON, "withdrawFromAccountTrigger");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					QuartzManager.addJob("contract", ContractTask.class, ScheduleConfig.CONTRACT_CRON, "contractTrigger");
			} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					QuartzManager.addJob("needs", HIdeNeedsTask.class, ScheduleConfig.NEEDS_CRON, "hideneedsTrigger");
			} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					while(!QuartzManager.triggerScheduler()){
						Thread.sleep(300);
					};
				} catch (SchedulerException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	}

}
