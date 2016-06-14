package com.autodesk.mp.dispatch.app;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



/**
 * 每日凌晨导出�?份所有账户的金额信息记录
 * @author Administrator
 *
 */
public class DailySnapShotTask implements Job{

	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String requestUrl=ScheduleConfig.SCHEDULE_HOST+"/v1/api/schedule/dailysnapshot";
		try {
			Response res=Request.Get(requestUrl).execute();
			context.setResult("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.setResult("failed");
		}
	}

	

	
}
