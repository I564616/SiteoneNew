/**
 *
 */
package com.siteone.core.cronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.ProductPromotionsCronJobModel;
import com.siteone.core.services.ProductPromotionsCronJobService;


/**
 * @author 1124932
 *
 */
public class ProductPromotionsCronJob extends AbstractJobPerformable<ProductPromotionsCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(ProductPromotionsCronJob.class);
	private ProductPromotionsCronJobService productPromotionsCronJobService;

	@Override
	public PerformResult perform(final ProductPromotionsCronJobModel model)
	{
		try
		{
			getProductPromotionsCronJobService().updateProductPromotions(model);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Product Promotions CronJob ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the productPromotionsCronJobService
	 */
	public ProductPromotionsCronJobService getProductPromotionsCronJobService()
	{
		return productPromotionsCronJobService;
	}

	/**
	 * @param productPromotionsCronJobService
	 *           the productPromotionsCronJobService to set
	 */
	public void setProductPromotionsCronJobService(final ProductPromotionsCronJobService productPromotionsCronJobService)
	{
		this.productPromotionsCronJobService = productPromotionsCronJobService;
	}
}
