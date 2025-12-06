/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.fulfilmentprocess.test;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;

import com.siteone.fulfilmentprocess.impl.DefaultCheckOrderService;

import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.order.services.SiteOneOrderService;


@UnitTest
public class DefaultCheckOrderServiceTest
{
	private final DefaultCheckOrderService defaultCheckOrderService = new DefaultCheckOrderService();

	private OrderModel order;
	
	@Mock
	private ModelService modelService;
	@Mock
	private SiteOneOrderService siteOneOrderService;

	/**
	 * 
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultCheckOrderService.setModelService(modelService);
		defaultCheckOrderService.setSiteOneOrderService(siteOneOrderService);
		order = new OrderModel();
		order.setCalculated(Boolean.TRUE);
		final B2BCustomerModel user = new B2BCustomerModel();
		order.setUser(user);
		order.setEntries(Arrays.<AbstractOrderEntryModel> asList(new OrderEntryModel()));
		order.setDeliveryAddress(new AddressModel());
		order.setPaymentInfo(new PaymentInfoModel());
	}

	@Test
	public void testCheck()
	{
		Assertions.assertThat(defaultCheckOrderService.check(order)).isFalse();
	}

	@Test
	public void testNotCalculated()
	{
		order.setCalculated(Boolean.FALSE);
		Assertions.assertThat(defaultCheckOrderService.check(order)).isFalse();
	}

	@Test
	public void testNoEntries()
	{
		order.setEntries(Collections.EMPTY_LIST);
		Assertions.assertThat(defaultCheckOrderService.check(order)).isFalse();
	}

	@Test
	public void testNoPaymentInfo()
	{
		order.setPaymentInfo(null);
		Assertions.assertThat(defaultCheckOrderService.check(order)).isFalse();
	}
}
