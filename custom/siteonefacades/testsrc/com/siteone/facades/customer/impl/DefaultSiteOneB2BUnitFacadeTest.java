/**
 *
 */
package com.siteone.facades.customer.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.servicelayer.session.SessionService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.b2bunit.service.impl.DefaultSiteOneB2BUnitService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;

import junit.framework.Assert;


/**
 * @author LS06223
 *
 */
@UnitTest
public class DefaultSiteOneB2BUnitFacadeTest
{


	@Mock
	private DefaultSiteOneB2BUnitFacade b2bUnitFacade;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private SessionService sessionService;

	@Mock
	private DefaultSiteOneB2BUnitService defaultSiteOneB2BUnitService;


	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		b2bUnitFacade = new DefaultSiteOneB2BUnitFacade();
		b2bUnitFacade.setSessionService(sessionService);
		b2bUnitFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
		b2bUnitFacade.setDefaultSiteOneB2BUnitService(defaultSiteOneB2BUnitService);
	}

	/**
	 * Test method for
	 * {@link com.siteone.facades.customer.impl.DefaultSiteOneB2BUnitFacade#setIsSegmentLevelShippingEnabled(java.lang.String)}.
	 */

	@Test
	public final void testSetIsSegmentLevelShippingEnabledOne()
	{
		final String featureSwitch = "guest,contractor,homeowner";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("409033");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledTwo()
	{
		final String featureSwitch = "guest,contractor";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("409033");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledThree()
	{
		final String featureSwitch = "guest";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("409033");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledFour()
	{
		final String featureSwitch = "guest,contractor,homeowner";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledFive()
	{
		final String featureSwitch = "guest,contractor,homeowner";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledSix()
	{
		final String featureSwitch = "guest,contractor";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledSeven()
	{
		final String featureSwitch = "guest";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledEight()
	{
		final String featureSwitch = null;
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("409033");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledNine()
	{
		final String featureSwitch = null;
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledTen()
	{
		final String featureSwitch = "";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("409033");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testSetIsSegmentLevelShippingEnabledEleven()
	{
		final String featureSwitch = "";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled")).willReturn(featureSwitch);
		final boolean actual = b2bUnitFacade.setIsSegmentLevelShippingEnabled("22000");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testCheckIsParentUnitEnabledForAgroAI()
	{
		final String b2bUnit = "112435_US";
		final B2BUnitModel parent = new B2BUnitModel();
		parent.setUid("12345_US");
		given(defaultSiteOneB2BUnitService.getParentForUnit(b2bUnit)).willReturn(parent);
		given(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI", parent.getUid()))
				.willReturn(true);
		final boolean actual = b2bUnitFacade.checkIsParentUnitEnabledForAgroAI("112435_US");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testCheckIsParentUnitEnabledForAgroAI_01()
	{
		final String b2bUnit = "112435-0001_US";
		final B2BUnitModel parent = new B2BUnitModel();
		parent.setUid("12345_US");
		given(defaultSiteOneB2BUnitService.getParentForUnit(b2bUnit)).willReturn(parent);
		given(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI", parent.getUid()))
				.willReturn(true);
		final boolean actual = b2bUnitFacade.checkIsParentUnitEnabledForAgroAI("112435-0001_US");
		Assert.assertEquals(true, actual);
	}

	@Test
	public final void testCheckIsParentUnitEnabledForAgroAI_02()
	{
		final String b2bUnit = "1124361_US";
		final B2BUnitModel parent = new B2BUnitModel();
		parent.setUid("1124361_US");
		given(defaultSiteOneB2BUnitService.getParentForUnit(b2bUnit)).willReturn(parent);
		given(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI", parent.getUid()))
				.willReturn(false);
		final boolean actual = b2bUnitFacade.checkIsParentUnitEnabledForAgroAI("1124361_US");
		Assert.assertEquals(false, actual);
	}

	@Test
	public final void testCheckIsParentUnitEnabledForAgroAI_03()
	{
		final String b2bUnit = "1124361-0001_US";
		final B2BUnitModel parent = new B2BUnitModel();
		parent.setUid("1124361_US");
		given(defaultSiteOneB2BUnitService.getParentForUnit(b2bUnit)).willReturn(parent);
		given(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI", parent.getUid()))
				.willReturn(false);
		final boolean actual = b2bUnitFacade.checkIsParentUnitEnabledForAgroAI("1124361-0001_US");
		Assert.assertEquals(false, actual);
	}
}
