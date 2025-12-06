/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.jobs.customer.dao.RegulatoryStatesCronJobDao;
import com.siteone.core.location.service.SiteOneRegionService;
import com.siteone.core.model.RegulatoryStatesCronJobModel;
import com.siteone.core.model.RegulatoryStatesModel;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOneProductService;


/**
 * @author 1124932
 *
 */
public class DefaultRegulatoryStatesCronJobService implements RegulatoryStatesCronJobService
{
	final Logger LOG = Logger.getLogger(DefaultRegulatoryStatesCronJobService.class);
	private RegulatoryStatesCronJobDao regulatoryStatesCronJobDao;
	private ModelService modelService;
	private ProductService productService;
	private CatalogVersionService catalogVersionService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
	private SiteOneRegionService siteOneRegionService;

	@SuppressWarnings(
	{ "unlikely-arg-type", "boxing" })
	@Override
	public void importProductRegulatoryStates(final RegulatoryStatesCronJobModel regulatoryStatesCronJobModel)
			throws UnknownIdentifierException, ModelSavingException
	{

		final List<RegulatoryStatesModel> regulatoryStatesList = getRegulatoryStatesCronJobDao().getAllRegulatoryStates();
		final List<RegionModel> regionList = siteOneRegionService.getRegionsForCountryCode("US");
		final List<RegionModel> regionCAList = siteOneRegionService.getRegionsForCountryCode("CA");
		final Map<ProductModel, Set<RegionModel>> regulatorySkuStateMap = new HashMap<>();
		final Map<ProductModel, Set<RegionModel>> allowedSkuStateMap = new HashMap<>();
		final Map<ProductModel, Set<RegionModel>> nonRegulatorySkuStateMap = new HashMap<>();
		final Set<ProductModel> regulatedProducts = new HashSet<>(getRegulatoryStatesCronJobDao().getRegulatedProductCodes());

		for (final ProductModel regulatedProduct : regulatedProducts)
		{
			if (regulatedProduct.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneProductCatalog"))
			{
				nonRegulatorySkuStateMap.put(regulatedProduct, new HashSet<>(regionList));
			}
			else
			{
				nonRegulatorySkuStateMap.put(regulatedProduct, new HashSet<>(regionCAList));
			}
		}

		for (final RegulatoryStatesModel regulatoryState : regulatoryStatesList)
		{
			try
			{
				ProductModel product = null;
				if (regulatoryState.getState().getIsocode().contains("CA"))
				{
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneCAProductCatalog", "Online"), regulatoryState.getSku());
				}
				else
				{
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), regulatoryState.getSku());
				}
				Set<RegionModel> states = null;
				Set<RegionModel> allowedStates = null;
				if (product.getIsRegulatedItem() && null != regulatoryState.getExpirationDate()
						&& (regulatoryState.getExpirationDate().after(new Date())
								|| regulatoryState.getExpirationDate().equals(new Date())))
				{
					if (CollectionUtils.isNotEmpty(regulatorySkuStateMap.get(product)))
					{
						states = regulatorySkuStateMap.get(product);
					}
					else
					{
						states = new HashSet<>();
					}
					states.add(regulatoryState.getState());
					regulatorySkuStateMap.put(product, states);
					LOG.info("DefaultRegulatoryStatesCronJobService - product code : " + regulatoryState.getSku());
					LOG.info("State which are not expired and in the feed " + regulatoryState.getState().getIsocode());
				}

				if (CollectionUtils.isNotEmpty(allowedSkuStateMap.get(product)))
				{
					allowedStates = allowedSkuStateMap.get(product);
				}
				else
				{
					allowedStates = new HashSet<>();
				}
				allowedStates.add(regulatoryState.getState());
				allowedSkuStateMap.put(product, allowedStates);
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error("Exception occured in finding the product : " + regulatoryState.getSku());
				regulatoryStatesCronJobModel.setResult(CronJobResult.FAILURE);
				regulatoryStatesCronJobModel.setStatus(CronJobStatus.ABORTED);
			}
		}


		for (final Map.Entry<ProductModel, Set<RegionModel>> allowedSkuStateMapEntry : allowedSkuStateMap.entrySet())
		{
			final ProductModel product = allowedSkuStateMapEntry.getKey();
			try
			{
				Set<RegionModel> allowedStatesList = null;
				if (product.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneProductCatalog"))
				{
					allowedStatesList = regionList.stream()
							.filter(e -> (allowedSkuStateMapEntry.getValue().stream()
									.filter(d -> d.getIsocodeShort().equalsIgnoreCase(e.getIsocodeShort())).count()) < 1)
							.collect(Collectors.toSet());
				}
				else
				{
					allowedStatesList = regionCAList.stream()
							.filter(e -> (allowedSkuStateMapEntry.getValue().stream()
									.filter(d -> d.getIsocodeShort().equalsIgnoreCase(e.getIsocodeShort())).count()) < 1)
							.collect(Collectors.toSet());
				}

				if (CollectionUtils.isNotEmpty(regulatorySkuStateMap.get(product)))
				{
					allowedStatesList.addAll(regulatorySkuStateMap.get(product));
				}

				product.setRegulatoryStates(new ArrayList<>(allowedStatesList));
				getModelService().save(product);
				getModelService().refresh(product);
			}
			catch (final ModelSavingException e)
			{
				LOG.error("Exception occured in finding the variant product categories : " + product.getCode());
			}

		}

		for (final Map.Entry<ProductModel, Set<RegionModel>> nonRegulatorySkuStateMapEntry : nonRegulatorySkuStateMap.entrySet())
		{
			final ProductModel product = nonRegulatorySkuStateMapEntry.getKey();
			try
			{
				product.setRegulatoryStates(new ArrayList<>(nonRegulatorySkuStateMap.get(product)));
				getModelService().save(product);
				getModelService().refresh(product);
			}
			catch (final ModelSavingException e)
			{
				LOG.error("Exception occured in finding the variant product categories : " + product.getCode());
			}

		}

		LOG.info("RegulatoryStatesCronJobModel executed successfully!");

	}

	@Override
	public Boolean getRupBySkuAndState(final String sku, final RegionModel state)
	{
		return regulatoryStatesCronJobDao.getRupBySkuAndState(sku, state);
	}
	
	@Override
	public Map<String, Boolean> getRupForPLPByState(List<String> productList, RegionModel region)
	{
		return regulatoryStatesCronJobDao.getRupForPLPByState(productList, region);
	}

	/**
	 * @return the regulatoryStatesCronJobDao
	 */
	public RegulatoryStatesCronJobDao getRegulatoryStatesCronJobDao()
	{
		return regulatoryStatesCronJobDao;
	}

	/**
	 * @param regulatoryStatesCronJobDao
	 *           the regulatoryStatesCronJobDao to set
	 */
	public void setRegulatoryStatesCronJobDao(final RegulatoryStatesCronJobDao regulatoryStatesCronJobDao)
	{
		this.regulatoryStatesCronJobDao = regulatoryStatesCronJobDao;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}


	/**
	 * @return the siteOneRegionService
	 */
	public SiteOneRegionService getSiteOneRegionService()
	{
		return siteOneRegionService;
	}


	/**
	 * @param siteOneRegionService
	 *           the siteOneRegionService to set
	 */
	public void setSiteOneRegionService(final SiteOneRegionService siteOneRegionService)
	{
		this.siteOneRegionService = siteOneRegionService;
	}

	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}
}
