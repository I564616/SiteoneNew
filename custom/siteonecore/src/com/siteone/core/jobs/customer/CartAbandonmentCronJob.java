/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.event.CartAbandonmentEvent;
import com.siteone.core.jobs.customer.dao.CartAbandonmentCronJobDao;
import com.siteone.core.model.CartAbandonmentCronJobModel;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1219341
 *
 */
public class CartAbandonmentCronJob extends AbstractJobPerformable<CartAbandonmentCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(CartAbandonmentCronJob.class);

	private CartAbandonmentCronJobDao cartAbandonmentCronJobDao;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private CommonI18NService commonI18NService;



	@Override
	public PerformResult perform(final CartAbandonmentCronJobModel cartAbandonmentCronJobModel)
	{
		LOG.info("Inside cart abandonment cron job ");
		try
		{
			final List<CartModel> inActiveCarts = getCartAbandonmentCronJobDao().findInActiveCarts();
			for (final CartModel inActiveCart : inActiveCarts)
			{
				if (CollectionUtils.isNotEmpty(inActiveCart.getEntries()))
				{
					eventService.publishEvent(initializeEvent(new CartAbandonmentEvent(), inActiveCart));
				}
			}
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in cart abandonment cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}



	public CartAbandonmentEvent initializeEvent(final CartAbandonmentEvent event, final CartModel cartModel)
	{
		if (cartModel.getSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
		}
		else 
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
		}
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());

		event.setCustomer((CustomerModel) cartModel.getUser());
		event.setCart(cartModel);
		return event;
	}




	public CartAbandonmentCronJobDao getCartAbandonmentCronJobDao()
	{
		return cartAbandonmentCronJobDao;
	}




	public void setCartAbandonmentCronJobDao(final CartAbandonmentCronJobDao cartAbandonmentCronJobDao)
	{
		this.cartAbandonmentCronJobDao = cartAbandonmentCronJobDao;
	}




	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}




	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}






}
