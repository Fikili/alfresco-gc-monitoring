package com.tieto.alfresco.gc.monitor.storage;

import java.io.InputStream;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

/**
 * Interface defines operations available for manipulate with GClog analysis
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 *
 */
public interface ReportStorage {

	/**
	 * Create new fetched report
	 * 
	 * @param typeType    of report
	 * @param containerId Container Id
	 * 
	 * @return report NodeRef
	 */
	public NodeRef createReportNode(final QName type, final String containerId);
	
	/**
	 * Set report content to report node
	 * @param reportNode report node
	 * @param content binary data of report 
	 */
	public void setReport(final NodeRef reportNode, final InputStream content);
}