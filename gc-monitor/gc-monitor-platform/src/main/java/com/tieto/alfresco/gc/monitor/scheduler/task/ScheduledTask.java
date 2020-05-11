package com.tieto.alfresco.gc.monitor.scheduler.task;

/**
 * Interface for define scheduled jobs which can be run by scheduler.
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 */
public interface ScheduledTask {

	/**
	 * Execute planned task.
	 */
	public void executeTask();

	/**
	 * Returns name of task.
	 * 
	 * @return name of task
	 */
	public String getTaskName();
}