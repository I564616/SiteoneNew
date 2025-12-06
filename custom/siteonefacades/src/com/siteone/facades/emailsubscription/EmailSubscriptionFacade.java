/**
 *
 */
package com.siteone.facades.emailsubscription;

import java.io.IOException;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;


/**
 * @author 1091124
 *
 */
public interface EmailSubscriptionFacade
{
	public void subscribeEmail(EmailSubscriptionsData emailSubscriptionsData) throws ResourceAccessException, IOException;
}
