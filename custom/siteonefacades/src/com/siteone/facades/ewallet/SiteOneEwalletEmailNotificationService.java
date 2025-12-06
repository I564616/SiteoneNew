/**
 *
 */
package com.siteone.facades.ewallet;

import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.util.List;


/**
 * @author PElango
 *
 */
public interface SiteOneEwalletEmailNotificationService
{

	public void sendEmailNotification(String cardType, String cardNumber, String operation);

	public void sendEmailNotification(String cardType, String cardNumber, String operation, String nickName,
			List<B2BCustomerModel> b2bModel);
}
