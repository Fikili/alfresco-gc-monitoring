package com.tieto.alfresco.gc.monitor.service;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tieto.alfresco.gc.monitor.storage.ReportStorage;
import com.tieto.alfresco.gc.monitor.scheduler.Type;

/**
 * Implements handling of reports.
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 * 
 */
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private ReportStorage reportStorage;
	private RetryingTransactionHelper transactionHelper;
	private Map<Type, String> containers;
	
	public void setContainers(Map<Type, String> containers) {
		this.containers = containers;
	}

	public void setReportStorage(ReportStorage reportStorage) {
		this.reportStorage = reportStorage;
	}

	public void setTransactionHelper(RetryingTransactionHelper transactionHelper) {
		this.transactionHelper = transactionHelper;
	}

	@Override
	public NodeRef createReport(final Type type, final InputStream reportContent) {
		return transactionHelper.doInTransaction(new RetryingTransactionCallback<NodeRef>() {
			@Override
			public NodeRef execute() throws Throwable {
				// Create a report
				final NodeRef reportNode = reportStorage.createReportNode(ContentModel.TYPE_CONTENT, containers.get(type));
				reportStorage.setReport(reportNode, reportContent);
				LOGGER.debug("Created new ReportNode: {}", reportNode);
				return reportNode;
			}
		}, false, true);
	}
}