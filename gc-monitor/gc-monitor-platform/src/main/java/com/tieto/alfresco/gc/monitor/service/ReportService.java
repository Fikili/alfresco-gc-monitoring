package com.tieto.alfresco.gc.monitor.service;

import java.io.InputStream;

import com.tieto.alfresco.gc.monitor.scheduler.Type;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 * @version 1.0
 *
 */
public interface ReportService {

	/**
	 * Create new report
	 * 
	 * @param reportContent binary data of report content
	 * 
	 * @return {@link NodeRef} of report.
	 */
	public NodeRef createReport(final Type type, final InputStream reportContent);
}