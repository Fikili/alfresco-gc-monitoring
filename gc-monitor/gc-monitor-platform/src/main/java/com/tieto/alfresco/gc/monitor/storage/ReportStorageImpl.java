package com.tieto.alfresco.gc.monitor.storage;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.model.Repository;
import org.alfresco.repo.node.SystemNodeUtils;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default implementation of operations available for manipulate with reports on storage
 * where are stored.
 * 
 * @author Vitezslav Sliz (vitezslav.sliz@tieto.com)
 * 
 *
 */
public class ReportStorageImpl implements ReportStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private Repository repositoryHelper;
	private NamespaceService namespaceService;
	private NodeService nodeService;
	private ContentService contentService;
	private PermissionService permissionService;

	public void setRepositoryHelper(Repository repositoryHelper) {
		this.repositoryHelper = repositoryHelper;
	}

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/* (non-Javadoc)
	 * @see com.tieto.ecm.alfresco.module.job_manager.storage.JobStorage#createJobkNode(org.alfresco.service.namespace.QName, java.lang.String)
	 */
	@Override
	public NodeRef createReportNode(final QName type, final String containerId) {
		final NodeRef jobContainer = getOrCreateJobContainer(containerId);

		final ChildAssociationRef newChildAssoc = nodeService.createNode(jobContainer, ContentModel.ASSOC_CHILDREN,	ContentModel.ASSOC_CHILDREN, type, new HashMap<>());

		final NodeRef reportNodeRef = newChildAssoc.getChildRef();

		// MNT-11911 fix, add ASPECT_INDEX_CONTROL and property that not create
		// indexes for search and not visible files/folders at 'My Documents'
		// dashlet
		final Map<QName, Serializable> aspectProperties = new HashMap<>(2);
		aspectProperties.put(ContentModel.PROP_IS_INDEXED, Boolean.FALSE);
		aspectProperties.put(ContentModel.PROP_IS_CONTENT_INDEXED, Boolean.FALSE);
		nodeService.addAspect(reportNodeRef, ContentModel.ASPECT_INDEX_CONTROL, aspectProperties);

		LOGGER.debug("Created report node. Report-NodeRef={} of type={}", reportNodeRef, type);

		return reportNodeRef;
	}
	
	@Override
	public void setReport(final NodeRef reportNode, final InputStream content) {
		final ContentWriter writer = contentService.getWriter(reportNode, ContentModel.PROP_CONTENT, true);
		writer.putContent(content);
	}
	
	private NodeRef getOrCreateJobContainer(final String containerId) {
		return AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<NodeRef>() {
			public NodeRef doWork() throws Exception {
				final QName containerNameQname = QName.createQName(containerId, namespaceService);
				NodeRef jobContainer = SystemNodeUtils.getSystemChildContainer(containerNameQname, nodeService, repositoryHelper);

				if (jobContainer == null) {
					LOGGER.info("Lazy creating the Job System Container ");
					jobContainer = SystemNodeUtils.getOrCreateSystemChildContainer(containerNameQname, nodeService, repositoryHelper).getFirst();
					permissionService.setPermission(jobContainer, PermissionService.ALL_AUTHORITIES,PermissionService.CREATE_CHILDREN, true);
				}
				return jobContainer;
			}
		}, AuthenticationUtil.getSystemUserName());
	}
}