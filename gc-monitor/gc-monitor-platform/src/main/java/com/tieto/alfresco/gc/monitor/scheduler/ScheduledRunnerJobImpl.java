package com.tieto.alfresco.gc.monitor.scheduler;

import java.lang.invoke.MethodHandles;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.schedule.AbstractScheduledLockedJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tieto.alfresco.gc.monitor.scheduler.exception.InvalidSchedulerJobException;
import com.tieto.alfresco.gc.monitor.scheduler.task.ScheduledTask;

/**
 * Implements runner for periodical job which request GC statistics generation.
 * Job task needs to be instance of {@link ScheduledTask} and run under system
 * user.
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 *
 */
@DisallowConcurrentExecution
public class ScheduledRunnerJobImpl extends AbstractScheduledLockedJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public void executeJob(JobExecutionContext jobContext) throws JobExecutionException {
		final JobDataMap jobData = jobContext.getJobDetail().getJobDataMap();
		// Extract the Job executer to use
		final Object job = jobData.get("jobExecuter");
		if (!(job instanceof ScheduledTask)) {
			throw new InvalidSchedulerJobException();
		}

		final ScheduledTask jobExecuter = (ScheduledTask) job;

		LOGGER.info("Executing planned job: '{}'", jobExecuter.getTaskName());

		AuthenticationUtil.runAs(() -> {
			jobExecuter.executeTask();
			return Void.TYPE;
		}, AuthenticationUtil.getSystemUserName());
	}
}