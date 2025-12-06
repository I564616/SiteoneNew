package com.siteone.punchout.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.cxml.CXML;
import org.cxml.Credential;
import org.cxml.Header;
import org.cxml.SharedSecret;

import de.hybris.platform.b2b.punchout.services.CXMLElementBrowser;

import com.siteone.core.model.PunchOutAuditLogModel;
import com.siteone.punchout.services.SiteOnePunchOutAuditLogService;

import de.hybris.platform.b2b.punchout.PunchOutUtils;
import de.hybris.platform.servicelayer.model.ModelService;

public class DefaultSiteOnePunchOutAuditLogService implements SiteOnePunchOutAuditLogService 
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchOutAuditLogService.class);
	
	private ModelService modelService;
	
	public void saveAuditLog(final CXML request, final CXML response, final String emailId,final String operation, final String sessionId, final Date requestTimeStamp)
	{
		LOG.info("Entering saveAuditLog");
		final PunchOutAuditLogModel punchOutAuditLog =  (PunchOutAuditLogModel) getModelService().create(PunchOutAuditLogModel.class);
		
		punchOutAuditLog.setSessionId(sessionId+"_"+System.currentTimeMillis());
		punchOutAuditLog.setEmailId(emailId);

		if(request != null)
		{
			final CXMLElementBrowser cXmlBrowser = new CXMLElementBrowser(request);
			final Header header = cXmlBrowser.findHeader();

			maskCredential(header.getFrom().getCredential());
			maskCredential(header.getTo().getCredential());
			maskCredential(header.getSender().getCredential());

			punchOutAuditLog.setRequestLog(PunchOutUtils.marshallFromBeanTree(request));
		}
		if(response != null)
		{
			punchOutAuditLog.setResponseLog(PunchOutUtils.marshallFromBeanTree(response));
		}
		punchOutAuditLog.setOperationName(operation);
		punchOutAuditLog.setRequestTimeStamp(requestTimeStamp);
		
		getModelService().save(punchOutAuditLog);
		
		LOG.info("Exiting saveAuditLog");
	}
	
	protected void maskCredential(List<Credential> credentials)
	{
		for(final Credential credential: credentials)
		{
			if(credential.getIdentity() != null && credential.getIdentity().getContent()!= null)
			{
				String identityValue = ((String)credential.getIdentity().getContent().get(0));
				identityValue = identityValue.replaceAll(identityValue, "*****");
				credential.getIdentity().getContent().set(0, identityValue);
			}

			if(credential.getSharedSecretOrDigitalSignatureOrCredentialMac() != null)
			{
				for (final Object obj : credential.getSharedSecretOrDigitalSignatureOrCredentialMac())
				{
					if (obj instanceof SharedSecret)
					{
						final SharedSecret sharedSecret = (SharedSecret) obj;
						String sharedSecretValue = (String) sharedSecret.getContent().get(0);
						sharedSecretValue = sharedSecretValue.replaceAll(sharedSecretValue, "*****");
						sharedSecret.getContent().set(0, sharedSecretValue);
					}
				}
			}
		}
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
	
	

}
