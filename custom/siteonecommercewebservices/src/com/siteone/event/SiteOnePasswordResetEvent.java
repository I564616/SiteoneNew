package com.siteone.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class SiteOnePasswordResetEvent extends AbstractCommerceUserEvent<BaseSiteModel> implements ClusterAwareEvent{

	private String email;

	public SiteOnePasswordResetEvent(final String email)
	{
		super();
		this.email = email;
	}
	
	@Override
	public boolean publish(int sourceNodeId, int targetNodeId) {
		return (sourceNodeId == targetNodeId);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
