package com.siteone.core.cart.strategies;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

public class DefaultSiteOneCommerceCartService extends DefaultCommerceCartService
{
    private static final Logger LOG = Logger.getLogger(DefaultSiteOneCommerceCartService.class);

    @Override
    public CommerceCartModification addToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException, ResourceAccessException
    {
        return this.getCommerceAddToCartStrategy().addToCart(parameter);
    }

    @Override
    public void recalculateCart(final CommerceCartParameter parameters)
    {
        validateParameterNotNull(parameters.getCart(), "Cart model cannot be null");
        try
        {
            getCommerceCartCalculationStrategy().recalculateCart(parameters);
        }
        catch (final ResourceAccessException ex)
        {
            LOG.error("Unable to get customer specific price", ex);
        }
    }

}
