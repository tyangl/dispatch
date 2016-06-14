package com.autodesk.mp.dispatch.app;

import java.io.IOException;
import java.util.Properties;

/**
 * quartz定时任务配置常量类
 * @author leiyang
 *
 */
public class ScheduleConfig {

	public static final String SCHEDULE_HOST;
	public static final String DAILY_SNAPSHOT_CRON;
    public static final String WITHDRAW_FROM_ACCOUNT_CRON;
    public static final String DESIGN_HOST;
    public static final String CONTRACT_CRON;
    public static final String CONTRACT_LIFE_TIME;
    public static final String NEEDS_LIFE_TIME;
    public static final String NEEDS_CRON;
    
    static {
    	synchronized(ScheduleConfig.class){
    		
		Properties pros=new Properties();
		try {
			pros.load(ScheduleConfig.class.getResourceAsStream("/schedule-config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SCHEDULE_HOST=pros.getProperty("transaction.host");
		DAILY_SNAPSHOT_CRON=pros.getProperty("dailysnapshot.cron");
		WITHDRAW_FROM_ACCOUNT_CRON=pros.getProperty("withdrawfromaccount.cron");
		DESIGN_HOST=pros.getProperty("design.host");
		CONTRACT_CRON=pros.getProperty("contract.cron");
		CONTRACT_LIFE_TIME=pros.getProperty("contract.lifetime");
		NEEDS_LIFE_TIME=pros.getProperty("needs.lifetime");
		NEEDS_CRON=pros.getProperty("needs.cron");
    	}
    }
}
