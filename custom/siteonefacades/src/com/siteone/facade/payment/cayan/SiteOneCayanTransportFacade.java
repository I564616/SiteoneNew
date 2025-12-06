/**
 *
 */
package com.siteone.facade.payment.cayan;

import de.hybris.platform.commercefacades.order.data.CartData;


/**
 * @author pelango
 *
 */
public interface SiteOneCayanTransportFacade
{

	public String getBoardCardTransportKey(boolean isCheckout);

	public String getAuthorizeTransportKey(CartData cartData);

	public String getBoardCardTransportKeyForMobile();

}
