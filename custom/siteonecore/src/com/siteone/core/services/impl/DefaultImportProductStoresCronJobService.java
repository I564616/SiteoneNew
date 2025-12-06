/**
 *
 */
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

import com.siteone.core.cronjob.dao.ImportProductStoresCronJobDao;
import com.siteone.core.model.ImportProductStoresCronJobModel;
import com.siteone.core.services.ImportProductStoresCronJobService;
import com.siteone.core.services.SiteOneProductService;


/**
 * @author 1197861
 *
 */
public class DefaultImportProductStoresCronJobService implements ImportProductStoresCronJobService
{
	final Logger LOG = Logger.getLogger(DefaultImportProductStoresCronJobService.class);
	private static final String CA_SITE = "CA";
	private ImportProductStoresCronJobDao importProductStoresCronJobDao;
	private ProductService productService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
	private CatalogVersionService catalogVersionService;
	private ModelService modelService;

	@Override
	public void importProductStore(final ImportProductStoresCronJobModel importProductStoresCronJobModel)
	{
		final Date date = new Date();
		List<StockLevelModel> stockLevels;
		Set<String> productCodesList;
		try
		{
			stockLevels = getImportProductStoresCronJobDao().findProductCodeForModifiedStockLevels();
			Set<String> productCACodesList = new HashSet<>(stockLevels.stream()
							.filter(e ->e.getWarehouse().getPointsOfService().iterator().hasNext() 
									&& e.getWarehouse().getPointsOfService().iterator().next().getBaseStore().getUid()
									.equalsIgnoreCase("siteoneCA"))
					.map(e -> e.getProductCode().concat(CA_SITE)).collect(Collectors.toList()));
            LOG.info("ProductCodeList Updated");
			 productCodesList = new HashSet<>(
					stockLevels.stream().map(StockLevelModel::getProductCode).collect(Collectors.toList()));
			 productCodesList.addAll(productCACodesList);
		}
		finally
		{

			stockLevels = null;
		}

		for (String productCode : productCodesList)
		{
			LOG.info("Starting for product code -" + productCode);
			try
			{
				ProductModel product = null;
				if (productCode.contains(CA_SITE))
				{
					productCode = productCode.replace(CA_SITE, "");
					LOG.info("INside CA for product code -" + productCode);
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneCAProductCatalog", "Online"), productCode);
				}
				else
				{
					product = getProductService().getProductForCode(
							getCatalogVersionService().getCatalogVersion("siteoneProductCatalog", "Online"), productCode);
				}

				LOG.info("ImportProductStoresCronJob: valid code -" + product.getCode());
				final Set<PointOfServiceModel> stores = new CopyOnWriteArraySet<>(product.getStores());
				final List<StockLevelModel> eligibleStores = getImportProductStoresCronJobDao().findEligibleStockLevels(productCode);

				//loop for onHand plus invhit eligible stock levels
				for (final StockLevelModel eligibleStockLevelModel : eligibleStores)
				{
					final WarehouseModel eligibleWarehouse = eligibleStockLevelModel.getWarehouse();
					if (null != eligibleWarehouse)
					{
						final PointOfServiceModel store = eligibleWarehouse.getPointsOfService().iterator().next();
						stores.add(store);
						LOG.info("Adding StoreID - " + store.getStoreId());
					}
				}
				LOG.info("product code adding -" + productCode + " Finished.");

				final List<StockLevelModel> nonEligibleStores = getImportProductStoresCronJobDao()
						.findNonEligibleStockLevels(productCode);
				//loop for Ineligible onHand plus invhit stock levels
				for (final StockLevelModel noneligiblestockLevelModel : nonEligibleStores)
				{
					final WarehouseModel nonEligibleWarehouse = noneligiblestockLevelModel.getWarehouse();
					if (null != nonEligibleWarehouse)
					{
						final PointOfServiceModel store = nonEligibleWarehouse.getPointsOfService().iterator().next();
						LOG.info("Removing StoreID - " + store.getStoreId());
						stores.remove(store);
					}
				}

				LOG.info("Removal for product code  -" + productCode + " finished.");


				final List<PointOfServiceModel> remainingStoresAfterRemoval = new ArrayList<>(stores);
				product.setStores(remainingStoresAfterRemoval);

				LOG.info("Saving product code -" + productCode + " finished.");
				getModelService().save(product);
				getModelService().refresh(product);
				LOG.info("Product Code -" + productCode + " finally finished.");

			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error("Exception occurred in finding the product :ImportProductStoresCronJob code:" + productCode);
			}
			catch (final Exception e)
			{
				LOG.error("Exception occured ImportProductStoresCronJob ", e);
				importProductStoresCronJobModel.setResult(CronJobResult.FAILURE);
				importProductStoresCronJobModel.setStatus(CronJobStatus.ABORTED);
				LOG.error("ImportProductStoresCronJob failed!");
			}
		}
		importProductStoresCronJobModel.setLastExecutionTime(date);
		getModelService().save(importProductStoresCronJobModel);

		LOG.info("ImportProductStoresCronJob executed successfully!");
	}


	/**
	 * @return the importProductStoresCronJobDao
	 */
	public ImportProductStoresCronJobDao getImportProductStoresCronJobDao()
	{
		return importProductStoresCronJobDao;
	}

	/**
	 * @param importProductStoresCronJobDao
	 *           the importProductStoresCronJobDao to set
	 */
	public void setImportProductStoresCronJobDao(final ImportProductStoresCronJobDao importProductStoresCronJobDao)
	{
		this.importProductStoresCronJobDao = importProductStoresCronJobDao;
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
