package com.siteone.facades.checkout.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import de.hybris.platform.commercefacades.order.data.CartData;

import org.junit.Before;
import org.junit.Test;

public class DefaultSiteOneB2BCheckoutFacadeNewTest {

    private DefaultSiteOneB2BCheckoutFacade checkoutFacade;

    @Before
    public void setUp() {
        checkoutFacade = spy(new DefaultSiteOneB2BCheckoutFacade());

        // Mock getCart() to return null
        doReturn(false).when(checkoutFacade).hasCheckoutCart();
    }

    @Test
    public void testUpdateCheckoutCart_CartModelIsNull_ReturnsNull() {
        CartData cartData = new CartData();

        CartData result = checkoutFacade.updateCheckoutCart(cartData);

        assertEquals(null, result);
    }
}