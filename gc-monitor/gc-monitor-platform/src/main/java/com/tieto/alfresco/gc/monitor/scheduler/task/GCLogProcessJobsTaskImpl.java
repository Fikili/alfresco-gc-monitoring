package com.tieto.alfresco.gc.monitor.scheduler.task;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tieto.alfresco.gc.monitor.scheduler.Type;

/**
 * Implements planned task which Initiate request GC log analysis processing
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 *
 */
public class GCLogProcessJobsTaskImpl implements ScheduledTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String TASK_NAME = "GC-log processing";

	private Type type;
	
	@Override
	public void executeTask() {
		LOGGER.info("Initiate processing of GC log - Method: {}", type);
		//TODO write call against some service class.
		LOGGER.error("Not implemented yet");
		LOGGER.info("Processing of GC log finished.");
	}

	@Override
	public String getTaskName() {
		return GCLogProcessJobsTaskImpl.TASK_NAME + " - " + type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}