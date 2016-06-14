package com.autodesk.mp.dispatch.app;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ContractTask implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String requestUrl=ScheduleConfig.DESIGN_HOST+"/v1/api/admin/contracts?days="+ScheduleConfig.CONTRACT_LIFE_TIME;
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
