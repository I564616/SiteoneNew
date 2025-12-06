/**
 *
 */
package com.siteone.storefront.util;



import de.hybris.platform.commercefacades.order.data.CartData;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;


/**
 * @author 1190626
 *
 */
@Component("siteOneCheckoutUtils")
public class SiteOneCheckoutUtils
{
	private static final Logger LOG = Logger.getLogger(SiteOneCheckoutUtils.class);

	@Resource(name = "b2bCheckoutFacade")
	private SiteOneB2BCheckoutFacade b2bCheckoutFacade;

	public boolean isCartValidForCheckout(final CartData cartData)
	{
		return b2bCheckoutFacade.isCartValidForCheckout(cartData);
	}

	public boolean isPosValidForCheckout(final CartData cartData)
	{
		return b2bCheckoutFacade.isPosValidForCheckout(cartData);
	}

	public boolean isAccountValidForCheckout(final CartData cartData)
	{
		return b2bCheckoutFacade.isAccountValidForCheckout(cartData);
	}



}