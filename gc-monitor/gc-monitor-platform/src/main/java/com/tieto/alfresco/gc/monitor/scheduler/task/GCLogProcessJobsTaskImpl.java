package com.tieto.alfresco.gc.monitor.scheduler.task;

import com.tieto.alfresco.gc.monitor.scheduler.Type;
import com.tieto.alfresco.gc.monitor.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

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
	public static final String GC_LOG_PATH = "/usr/local/tomcat/logs/gc.log";

	private Type type;
	private String gcAppUrl;
	private String gcAppToken;
	private ReportService reportService;

	@Override
	public void executeTask() {
		LOGGER.info("Initiate processing of GC log - Method: {}", type);
		//TODO write call against some service class.

		final String url = String.format("%s%s",gcAppUrl,gcAppToken);
		LOGGER.info("GC app url = {} | token = {}", gcAppUrl,gcAppToken);
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = null;
		try {
			request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.timeout(Duration.ofMinutes(1))
					.header("Content-Type", "text")
					.POST(HttpRequest.BodyPublishers.ofFile(Paths.get(GC_LOG_PATH)))
					.build();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HttpResponse<InputStream> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Send response as InputStream
		reportService.createReport(type,response.body());
		LOGGER.info("Processing of GC log finished.");
	}

	@Override
	public String getTaskName() {
		return GCLogProcessJobsTaskImpl.TASK_NAME + " - " + type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setGcAppUrl(String gcAppUrl) {
		this.gcAppUrl = gcAppUrl;
	}

	public void setGcAppToken(String gcAppToken) {
		this.gcAppToken = gcAppToken;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
}