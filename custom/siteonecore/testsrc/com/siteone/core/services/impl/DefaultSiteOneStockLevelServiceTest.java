/**
 *
 */
package com.siteone.core.services.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.stock.dao.SiteOneStockLevelDao;

import junit.framework.Assert;


/**
 * @author SM04392
 *
 */
@UnitTest
public class DefaultSiteOneStockLevelServiceTest
{

	@Mock
	private SiteOneStockLevelDao siteOneStockLevelDao;
	private DefaultSiteOneStockLevelService defaultSiteOneStockLevelService;

	private final float multiplier = 1.0f;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteOneStockLevelService = new DefaultSiteOneStockLevelService();
		defaultSiteOneStockLevelService.setSiteOneStockLevelDao(siteOneStockLevelDao);
	}


	@Test
	public final void testIsForceInStock()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(false);
		objList.add(false);
		objList.add(false);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
				.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		forceStockFromDAO.add(false);
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		Assert.assertEquals(forceStockFromDAO, forceStockMethod);
	}

	@Test
	public final void testIsForceInStockOne()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(false);
		objList.add(false);
		objList.add(true);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
				.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		forceStockFromDAO.add(false);
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		Assert.assertEquals(forceStockFromDAO, forceStockMethod);
	}

	@Test
	public final void testIsForceInStockTwo()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		final boolean forceStock = forceStockMethod.get(0);
		Assert.assertEquals(false, forceStock);
	}

	@Test
	public final void testIsForceInStockThree()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(false);
		objList.add(false);
		objList.add(false);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		final List<Object> objList_1 = new ArrayList<>();
		objList_1.add(true);
		objList_1.add(false);
		objList_1.add(false);
		objList_1.add(false);
		objList_1.add(multiplier);
		objList_1.add(true);
		objList_1.add("636");
		objList_main.add(objList_1);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", "636")).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
				.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList_1) && BooleanUtils.isTrue((Boolean) objList_1.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList_1.get(2)) || BooleanUtils.isNotTrue((Boolean) objList_1.get(1))));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", "636", multiplier);
		Assert.assertEquals(forceStockFromDAO, forceStockMethod);
	}

	@Test
	public final void testIsForceInStockFour()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(false);
		objList.add(false);
		objList.add(false);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		final List<Object> objList_1 = new ArrayList<>();
		given(siteOneStockLevelDao.isForceInStock("98222", "172", "636")).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
				.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList_1) && BooleanUtils.isTrue((Boolean) objList_1.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList_1.get(2)) || BooleanUtils.isNotTrue((Boolean) objList_1.get(1))));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", "636", multiplier);
		Assert.assertEquals(forceStockFromDAO, forceStockMethod);
	}
	
	@Test
	public final void testIsForceInStockFive()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(false);
		objList.add(false);
		objList.add(false);
		objList.add(multiplier);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		final boolean forceStock = forceStockMethod.get(4);
		Assert.assertEquals(false, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForBackorder()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(10.0f);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		final boolean forceStock = forceStockMethod.get(2);
		Assert.assertEquals(true, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForBackordertwo()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(10.0f);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= 25);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, 25.0f);
		final boolean forceStock = forceStockMethod.get(2);
		Assert.assertEquals(false, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForBackorderThree()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(1.0f);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= multiplier);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, multiplier);
		final boolean forceStock = forceStockMethod.get(2);
		Assert.assertEquals(true, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForNurseryBackorder()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(100.0f);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= 5);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, 25.0f);
		final boolean forceStock = forceStockMethod.get(2) && forceStockMethod.get(3);
		Assert.assertEquals(true, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForNurseryBackorderTwo()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(100.0f);
		objList.add(false);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= 10);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, 25.0f);
		final boolean forceStock = forceStockMethod.get(2) && forceStockMethod.get(3);
		Assert.assertEquals(false, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForNurseryBackorderThree()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(10.0f);
		objList.add(true);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= 10);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, 25.0f);
		final boolean forceStock = forceStockMethod.get(2) && forceStockMethod.get(3);
		Assert.assertEquals(false, forceStock);
	}
	
	@Test
	public final void testIsForceInStockForNurseryBackorderFour()
	{
		final List<List<Object>> objList_main = new ArrayList<>();
		final List<Object> objList = new ArrayList<>();
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(true);
		objList.add(10.0f);
		objList.add(false);
		objList.add("172");
		objList_main.add(objList);
		given(siteOneStockLevelDao.isForceInStock("98222", "172", null)).willReturn(objList_main);
		final List<Boolean> forceStockFromDAO = new ArrayList<>();
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) objList.get(2)) || BooleanUtils.isNotTrue((Boolean) objList.get(1))));
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(3)));
		forceStockFromDAO
		.add(CollectionUtils.isNotEmpty(objList) && objList.get(4) != null && ((float) objList.get(4)) >= 10);
		forceStockFromDAO.add(CollectionUtils.isNotEmpty(objList) && BooleanUtils.isTrue((Boolean) objList.get(5)));
		final List<Boolean> forceStockMethod = defaultSiteOneStockLevelService.isForceInStock("98222", "172", null, 25.0f);
		final boolean forceStock = forceStockMethod.get(2) && forceStockMethod.get(3);
		Assert.assertEquals(false, forceStock);
	}

}