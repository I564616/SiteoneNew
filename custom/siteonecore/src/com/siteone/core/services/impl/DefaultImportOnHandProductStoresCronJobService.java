package com.siteone.core.services.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.ImportOnHandProductStoresCronJobDao;
import com.siteone.core.model.ImportOnHandProductStoresCronJobModel;
import com.siteone.core.services.ImportOnHandProductStoresCronJobService;
import com.siteone.core.services.SiteOneProductService;


/**
 * @author nmangal
 *
 */
public class DefaultImportOnHandProductStoresCronJobService implements ImportOnHandProductStoresCronJobService
{
	final Logger LOG = Logger.getLogger(DefaultImportOnHandProductStoresCronJobService.class);
	private static final String CA_SITE = "CA";
	private ImportOnHandProductStoresCronJobDao importOnHandProductStoresCronJobDao;
	private ProductService productService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
	private CatalogVersionService catalogVersionService;
	private ModelService modelService;

	@Override
	public void importOnHandProductStore(final ImportOnHandProductStoresCronJobModel importOnHandProductStoresCronJobModel)
	{
		final Date date = new Date();
		List<StockLevelModel> stockLevels;
		Set<String> productCodesList;
		try
		{
			stockLevels = getImportOnHandProductStoresCronJobDao().findOnHandProductCodeForModifiedStockLevels();

			Set<String> productCACodesList = new HashSet<>(stockLevels.stream()
							.filter(e -> e.getWarehouse().getPointsOfService().iterator().hasNext() 
									&& e.getWarehouse().getPointsOfService().iterator().next().getBaseStore().getUid()
									.equalsIgnoreCase("siteoneCA"))
							.map(e -> e.getProductCode().concat(CA_SITE)).collect(Collectors.toList()));
					productCodesList = new HashSet<>(
							stockLevels.stream().map(StockLevelModel::getProductCode).collect(Collectors.toList()));
					 productCodesList.addAll(productCACodesList);
		}
		finally
		{
			stockLevels = null;
		}
		LOG.info("ImportOnHandProductStoresCronJob Started");
		for (String productCode : productCodesList)
		{
			LOG.info("Starting for onhand product code -" + productCode);
			try
			{
				ProductModel product = null;
				if (productCode.contains(CA_SITE))
				{
					productCode = productCode.replace(CA_SITE, "");
					LOG.info("Inside CA for product code -" + productCode);
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneCAProductCatalog", "Online"), productCode);
				}
				else
				{
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), productCode);
				}
				LOG.info("ImportOnHandProductStoresCronJob: valid code -" + product.getCode());

				final Set<PointOfServiceModel> onHandStores = new CopyOnWriteArraySet<>(product.getOnHandStores());
				final List<StockLevelModel> eligibleOnHandStores = getImportOnHandProductStoresCronJobDao()
						.findEligibleOnHandStockLevels(productCode);
				//loop for only onHand eligible stock levels.
				for (final StockLevelModel eligibleStockLevelModel : eligibleOnHandStores)
				{
					final WarehouseModel eligibleWarehouse = eligibleStockLevelModel.getWarehouse();
					if (null != eligibleWarehouse)
					{
						final PointOfServiceModel store = eligibleWarehouse.getPointsOfService().iterator().next();
						onHandStores.add(store);
						LOG.info("Adding Onhand StoreID - " + store.getStoreId());
					}
				}
				LOG.info("Onhand product code adding -" + productCode + " finished.");

				final List<StockLevelModel> nonEligibleOnHandStores = getImportOnHandProductStoresCronJobDao()
						.findNonEligibleOnHandStockLevels(productCode);
				//loop for only onHand Ineligible stock levels.
				for (final StockLevelModel noneligiblestockLevelModel : nonEligibleOnHandStores)
				{
					final WarehouseModel nonEligibleWarehouse = noneligiblestockLevelModel.getWarehouse();
					if (null != nonEligibleWarehouse)
					{
						final PointOfServiceModel store = nonEligibleWarehouse.getPointsOfService().iterator().next();
						onHandStores.remove(store);

						LOG.info("Removing onhand StoreID - " + store.getStoreId());
					}
				}

				final List<PointOfServiceModel> remainingOnHandStoresAfterRemoval = new ArrayList<>(onHandStores);
				product.setOnHandStores(remainingOnHandStoresAfterRemoval);

				LOG.info("Removal for onhand product code  -" + productCode + " finished.");
				LOG.info("Saving onhand product code -" + productCode + " finished.");
				getModelService().save(product);
				getModelService().refresh(product);
				LOG.info("onhand Product Code -" + productCode + " finally finished.");

			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error("Exception occurred in finding the product :ImportOnHandProductStoresCronJob code:" + productCode);
			}
			catch (final Exception e)
			{
				LOG.error("Exception occured ImportOnHandProductStoresCronJob ", e);
				importOnHandProductStoresCronJobModel.setResult(CronJobResult.FAILURE);
				importOnHandProductStoresCronJobModel.setStatus(CronJobStatus.ABORTED);
				LOG.error("ImportOnHandProductStoresCronJob failed!");
			}
		}
		importOnHandProductStoresCronJobModel.setLastExecutionTime(date);
		getModelService().save(importOnHandProductStoresCronJobModel);

		LOG.info("ImportOnHandProductStoresCronJob executed successfully!");
	}

	public ImportOnHandProductStoresCronJobDao getImportOnHandProductStoresCronJobDao()
	{
		return importOnHandProductStoresCronJobDao;
	}

	public void setImportOnHandProductStoresCronJobDao(
			final ImportOnHandProductStoresCronJobDao importOnHandProductStoresCronJobDao)
	{
		this.importOnHandProductStoresCronJobDao = importOnHandProductStoresCronJobDao;
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

	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}

}
