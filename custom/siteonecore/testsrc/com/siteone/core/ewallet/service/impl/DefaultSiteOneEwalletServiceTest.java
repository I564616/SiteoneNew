/**
 *
 */
package com.siteone.core.ewallet.service.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.featureswitch.dao.impl.DefaultSiteOneFeatureSwitchDao;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultSiteOneEwalletServiceTest
{
	@Mock
	private DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;
	@Mock
	private DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao;
	private DefaultSiteOneEwalletService service;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		service = new DefaultSiteOneEwalletService();
		service.setDefaultSiteOneEwalletDao(defaultSiteOneEwalletDao);
		service.setDefaultSiteOneFeatureSwitchDao(defaultSiteOneFeatureSwitchDao);
	}

	@Test
	public void getValueForSwitchTest()
	{
		final String key = "BranchesDeliveryThreshold";
		final String value = "100";
		given(defaultSiteOneFeatureSwitchDao.getSwitchLongValue(key)).willReturn(value);
		final Object actual = service.getValueForSwitch(key);
		Assert.assertEquals(actual, value);
	}

	@Test
	public void getValueForSwitchTestForNull()
	{
		final String key = "BranchesDeliveryThresholdTest";
		final String value = "100";
		final String key1 = "BranchesDeliveryThreshold";
		final String value1 = "10";
		given(defaultSiteOneFeatureSwitchDao.getSwitchLongValue(key)).willReturn(value);
		given(defaultSiteOneFeatureSwitchDao.getSwitchValue(key1)).willReturn(value1);
		final Object actual = service.getValueForSwitch(key1);
		Assert.assertEquals(actual, null);
	}
}
