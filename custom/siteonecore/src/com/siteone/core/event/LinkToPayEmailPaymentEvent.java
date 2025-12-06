/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;

import com.siteone.core.model.LinkToPayPaymentProcessModel;


/**
 * @author SJ08640
 *
 */
public class LinkToPayEmailPaymentEvent extends AbstractEvent /* AbstractCommerceUserEvent<BaseSiteModel> */
{
	

	private final LinkToPayPaymentProcessModel process;

	public LinkToPayEmailPaymentEvent(final LinkToPayPaymentProcessModel process)
	{
		this.process = process;
	}

	public LinkToPayPaymentProcessModel getProcess()
	{
		return process;
	}





}

