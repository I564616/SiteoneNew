/**
 *
 */
package com.siteone.core.adapter.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.store.dao.SiteOneStoreUtilityDao;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultPointOfServiceAdaptorTest
{
	@Mock
	private SiteOneStoreUtilityDao siteOneStoreUtilityDao;
	private DefaultPointOfServiceAdaptor posAdaptor;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		posAdaptor = new DefaultPointOfServiceAdaptor();
		posAdaptor.setSiteOneStoreUtilityDao(siteOneStoreUtilityDao);
	}

	@Test
	public void testGetStoreDetailByPK()
	{
		final PointOfServiceModel posModel = new PointOfServiceModel();
		posModel.setStoreId("8201");
		posModel.setIsDCBranch(Boolean.TRUE);
		posModel.setIsActive(Boolean.TRUE);
		final String pk = "123214214";
		given(siteOneStoreUtilityDao.getStoreForPK(pk)).willReturn(posModel);
		final Object actual = posAdaptor.getStoreDetailByPK(pk);
		Assert.assertEquals(posModel, actual);
	}

}
