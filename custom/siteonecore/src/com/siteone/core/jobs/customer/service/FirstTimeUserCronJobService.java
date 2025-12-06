package com.siteone.core.jobs.customer.service;

import java.io.IOException;

import com.siteone.core.model.FirstTimeUserCronJobModel;


public interface FirstTimeUserCronJobService
{

	public void exportFirstTimeUsers(FirstTimeUserCronJobModel firstTimeUserCronJobModel) throws IOException;
}
