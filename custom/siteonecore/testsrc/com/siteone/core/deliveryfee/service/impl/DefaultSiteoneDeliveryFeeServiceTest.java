package com.siteone.core.deliveryfee.service.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.deliveryfee.dao.SiteoneDeliveryFeeDao;
import com.siteone.core.model.SiteoneDeliveryFeesModel;
import com.siteone.core.model.SiteoneShippingFeesModel;


/**
 * @author SM04392
 *
 */
@UnitTest
public class DefaultSiteoneDeliveryFeeServiceTest
{

	@Mock
	private SiteoneDeliveryFeeDao siteoneDeliveryFeeDao;
	private DefaultSiteoneDeliveryFeeService defaultSiteoneDeliveryFeeService;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteoneDeliveryFeeService = new DefaultSiteoneDeliveryFeeService();
		defaultSiteoneDeliveryFeeService.setSiteoneDeliveryFeeDao(siteoneDeliveryFeeDao);
	}

	@Test
	public void testGetShippingFee()
	{
		final String branchId = "124";
		final Set<String> productList = new HashSet<>();
		final String result = defaultSiteoneDeliveryFeeService.getShippingFee(branchId, productList);
		Assert.assertNull(result);
	}

	@Test
	public void testGetShippingFeeTwo()
	{
		final String branchId = null;
		final Set<String> productList = new HashSet<>();
		productList.add("98222");
		productList.add("100089");
		final String result = defaultSiteoneDeliveryFeeService.getShippingFee(branchId, productList);
		Assert.assertNull(result);
	}

	@Test
	public void testGetShippingFeeThree()
	{
		final String branchId = "124";
		final Set<String> productList = new HashSet<>();
		productList.add("98222");
		productList.add("100089");
		final List<SiteoneShippingFeesModel> shippingFeeList = new ArrayList();
		final SiteoneShippingFeesModel model1 = new SiteoneShippingFeesModel();
		model1.setBranchId(branchId);
		model1.setProductSku("98222");
		model1.setShippingFee(25.0d);
		shippingFeeList.add(model1);
		given(siteoneDeliveryFeeDao.getShippingFee(branchId, productList)).willReturn(shippingFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getShippingFee(branchId, productList);
		Assert.assertEquals(result, "25.00");
	}

	@Test
	public void testGetShippingFeeFour()
	{
		final String branchId = "124";
		final Set<String> productList = new HashSet<>();
		productList.add("98222");
		productList.add("100089");
		final List<SiteoneShippingFeesModel> shippingFeeList = new ArrayList();
		final SiteoneShippingFeesModel model = new SiteoneShippingFeesModel();
		model.setBranchId(branchId);
		model.setProductSku("98222");
		model.setShippingFee(25.0d);
		shippingFeeList.add(model);
		final SiteoneShippingFeesModel model1 = new SiteoneShippingFeesModel();
		model1.setBranchId(branchId);
		model1.setProductSku("100089");
		model1.setShippingFee(27.0d);
		shippingFeeList.add(model1);
		given(siteoneDeliveryFeeDao.getShippingFee(branchId, productList)).willReturn(shippingFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getShippingFee(branchId, productList);
		Assert.assertEquals(result, "27.00");
	}

	@Test
	public void testGetShippingFeeFive()
	{
		final String branchId = "124";
		final Set<String> productList = new HashSet<>();
		productList.add("98222");
		productList.add("100089");
		productList.add("98714");
		final List<SiteoneShippingFeesModel> shippingFeeList = new ArrayList();
		final SiteoneShippingFeesModel model = new SiteoneShippingFeesModel();
		model.setBranchId(branchId);
		model.setProductSku("98222");
		model.setShippingFee(25.0d);
		shippingFeeList.add(model);
		final SiteoneShippingFeesModel model1 = new SiteoneShippingFeesModel();
		model1.setBranchId(branchId);
		model1.setProductSku("100089");
		model1.setShippingFee(27.0d);
		shippingFeeList.add(model1);
		final SiteoneShippingFeesModel model2 = new SiteoneShippingFeesModel();
		model2.setBranchId(branchId);
		model2.setProductSku("98714");
		model2.setShippingFee(27.0d);
		shippingFeeList.add(model1);
		given(siteoneDeliveryFeeDao.getShippingFee(branchId, productList)).willReturn(shippingFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getShippingFee(branchId, productList);
		Assert.assertEquals(result, "27.00");
	}

	@Test
	public void testGetDeliveryFee()
	{
		final String branchId = "124";
		final boolean isProUser = false;
		final Set<String> lobList = new HashSet<>();
		final String result = defaultSiteoneDeliveryFeeService.getDeliveryFee(branchId, isProUser, lobList);
		Assert.assertNull(result);
	}

	@Test
	public void testGetDeliveryFeeTwo()
	{
		final String branchId = "124";
		final boolean isProUser = false;
		final Set<String> lobList = new HashSet<>();
		lobList.add("Nursery");
		final List<SiteoneDeliveryFeesModel> deliveryFeeList = new ArrayList<>();
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel.setBranchId(branchId);
		siteoneDeliveryFeesModel.setLineOfBusiness("Nursery");
		siteoneDeliveryFeesModel.setProFee(50.0d);
		siteoneDeliveryFeesModel.setRetailFee(100.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel);
		given(siteoneDeliveryFeeDao.getDeliveryFee(branchId, isProUser, lobList)).willReturn(deliveryFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getDeliveryFee(branchId, isProUser, lobList);
		Assert.assertEquals(result, "100.0");
	}

	@Test
	public void testGetDeliveryFeeThree()
	{
		final String branchId = "124";
		final boolean isProUser = true;
		final Set<String> lobList = new HashSet<>();
		lobList.add("Nursery");
		final List<SiteoneDeliveryFeesModel> deliveryFeeList = new ArrayList<>();
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel.setBranchId(branchId);
		siteoneDeliveryFeesModel.setLineOfBusiness("Nursery");
		siteoneDeliveryFeesModel.setProFee(50.0d);
		siteoneDeliveryFeesModel.setRetailFee(100.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel);
		given(siteoneDeliveryFeeDao.getDeliveryFee(branchId, isProUser, lobList)).willReturn(deliveryFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getDeliveryFee(branchId, isProUser, lobList);
		Assert.assertEquals(result, "50.0");
	}

	@Test
	public void testGetDeliveryFeeFour()
	{
		final String branchId = "124";
		final boolean isProUser = true;
		final Set<String> lobList = new HashSet<>();
		lobList.add("Nursery");
		lobList.add("Lanscape");
		final List<SiteoneDeliveryFeesModel> deliveryFeeList = new ArrayList<>();
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel.setBranchId(branchId);
		siteoneDeliveryFeesModel.setLineOfBusiness("Landscape");
		siteoneDeliveryFeesModel.setProFee(500.0d);
		siteoneDeliveryFeesModel.setRetailFee(1000.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel);
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel1 = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel1.setBranchId(branchId);
		siteoneDeliveryFeesModel1.setLineOfBusiness("Nursery");
		siteoneDeliveryFeesModel1.setProFee(250.0d);
		siteoneDeliveryFeesModel1.setRetailFee(500.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel1);
		given(siteoneDeliveryFeeDao.getDeliveryFee(branchId, isProUser, lobList)).willReturn(deliveryFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getDeliveryFee(branchId, isProUser, lobList);
		Assert.assertEquals(result, "500.0");
	}

	@Test
	public void testGetDeliveryFeeFive()
	{
		final String branchId = "124";
		final boolean isProUser = true;
		final Set<String> lobList = new HashSet<>();
		lobList.add("Nursery");
		lobList.add("Lanscape");
		lobList.add("Hardscape");
		final List<SiteoneDeliveryFeesModel> deliveryFeeList = new ArrayList<>();
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel.setBranchId(branchId);
		siteoneDeliveryFeesModel.setLineOfBusiness("Landscape");
		siteoneDeliveryFeesModel.setProFee(500.0d);
		siteoneDeliveryFeesModel.setRetailFee(1000.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel);
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel1 = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel1.setBranchId(branchId);
		siteoneDeliveryFeesModel1.setLineOfBusiness("Nursery");
		siteoneDeliveryFeesModel1.setProFee(250.0d);
		siteoneDeliveryFeesModel1.setRetailFee(500.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel1);
		final SiteoneDeliveryFeesModel siteoneDeliveryFeesModel2 = new SiteoneDeliveryFeesModel();
		siteoneDeliveryFeesModel2.setBranchId(branchId);
		siteoneDeliveryFeesModel2.setLineOfBusiness("Hardscape");
		siteoneDeliveryFeesModel2.setProFee(500.0d);
		siteoneDeliveryFeesModel2.setRetailFee(1000.0d);
		deliveryFeeList.add(siteoneDeliveryFeesModel2);
		given(siteoneDeliveryFeeDao.getDeliveryFee(branchId, isProUser, lobList)).willReturn(deliveryFeeList);
		final String result = defaultSiteoneDeliveryFeeService.getDeliveryFee(branchId, isProUser, lobList);
		Assert.assertEquals(result, "500.0");
	}

}
