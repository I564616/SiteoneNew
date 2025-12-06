/**
 *
 */
package com.siteone.core.order.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.OrderReadyToPickUpEvent;
import com.siteone.core.event.OrderScheduledForDeliveryEvent;
import com.siteone.core.event.OrderTrackingLinkEvent;
import com.siteone.core.event.QuoteToOrderStatusEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.OrderReadyForPickUpRemainderEmailCronJobModel;
import com.siteone.core.model.OrderStatusEmailCronJobModel;
import com.siteone.core.model.OrderStatusNotificationCronJobModel;
import com.siteone.core.model.QuoteToOrderStatusEmailCronJobModel;
import com.siteone.core.model.QuoteToOrderStatusProcessModel;
import com.siteone.core.model.SiteoneOrderEmailStatusModel;
import com.siteone.core.order.dao.SiteOneOrderDao;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.integration.azure.pushnotification.SendNotificationApi;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.services.ue.SiteOneQuotesWebService;
import com.windowsazure.messaging.NotificationHub;


/**
 * @author 1099417
 *
 */
public class DefaultSiteOneOrderService implements SiteOneOrderService
{
	private SiteOneOrderDao siteOneOrderDao;

	private ModelService modelService;

	private EventService eventService;

	private BaseStoreService baseStoreService;

	private BaseSiteService baseSiteService;

	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneOrderEntriesProductConverter")
	private Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "SiteOneProductConverter")
	private Converter<ProductModel, ProductData> siteOneProductConverter;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;

	@Resource(name = "sendNotificationApi")
	private SendNotificationApi sendNotificationApi;

	@Resource(name = "siteOneQuotesWebService")
	private SiteOneQuotesWebService siteOneQuotesWebService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneOrderService.class);

	@Override
	public void sendOrderStatusEmail(final OrderStatusEmailCronJobModel orderStatusEmailCronJobModel) throws IOException
	{
		final List<OrderModel> orders = getSiteOneOrderDao().getOrdersToSendEmail();
      
		if (null != orders)
		{
			orders.forEach(order -> {
				if (null != order)
				{
					order.getConsignments().forEach(consignment -> {
						
						if (CollectionUtils.isEmpty(getSiteOneOrderDao().getEntryFromSiteoneOrderEmailStatus(consignment.getCode())))
						{
							boolean emailSent = false;
							if (ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode().equalsIgnoreCase(consignment.getStatus().getCode()) && consignment.getTrackingLink() != null 
									&& order.getHybrisOrderNumber() != null)
							{
								getEventService().publishEvent(
										initializeEvent(new OrderTrackingLinkEvent(order, consignment), (CustomerModel) order.getUser(), order));
								emailSent = true;
							}
							else if (ConsignmentStatus.READY_FOR_PICKUP.getCode().equalsIgnoreCase(consignment.getStatus().getCode()))
							{
								if(order.getHybrisOrderNumber() != null)
								{
									getEventService().publishEvent(
											initializeEvent(new OrderReadyToPickUpEvent(order, consignment), (CustomerModel) order.getUser(), order));
									emailSent = true;
								}
								else if(order.getQuoteNumber() != null)
								{
									final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
											Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
											order.getQuoteNumber());

									if (quoteDetails != null && StringUtils.isNotBlank(quoteDetails.getLastApproverEmail()) && BooleanUtils.isTrue(quoteDetails.getShowOnline()))
									{
										getEventService().publishEvent(
												initializeEvent(new OrderReadyToPickUpEvent(order, consignment), (CustomerModel) order.getUser(), order));
										emailSent = true;
									}	
								}

							}
							else if (ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode().equalsIgnoreCase(consignment.getStatus().getCode()))
							{
								if(order.getHybrisOrderNumber() != null)
								{
									getEventService().publishEvent(
											initializeEvent(new OrderScheduledForDeliveryEvent(order, consignment), (CustomerModel) order.getUser(), order));
									emailSent = true;
								}
								else if(order.getQuoteNumber() != null)
								{
									final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
											Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
											order.getQuoteNumber());

									if (quoteDetails != null && StringUtils.isNotBlank(quoteDetails.getLastApproverEmail()) && BooleanUtils.isTrue(quoteDetails.getShowOnline()))
									{
										getEventService().publishEvent(
												initializeEvent(new OrderScheduledForDeliveryEvent(order, consignment), (CustomerModel) order.getUser(), order));
										emailSent = true;
									}	
								}
							}

							if (emailSent)
							{
								final SiteoneOrderEmailStatusModel siteoneOrderEmailStatus = new SiteoneOrderEmailStatusModel();
								siteoneOrderEmailStatus.setOrderId(order.getCode());
								siteoneOrderEmailStatus.setConsignmentId(consignment.getCode());
								siteoneOrderEmailStatus.setStatus(consignment.getStatus().getCode());
								siteoneOrderEmailStatus.setIsEmailsent(true);
								modelService.save(siteoneOrderEmailStatus);
								order.setIsOrderStatusEmailSent(true);
								modelService.save(order);
							}
						}
					});
				}
				
			});
		}
		



	}

	@Override
	public void sendQuoteToOrderStatusEmail(final QuoteToOrderStatusEmailCronJobModel quoteToOrderStatusEmailCronJobModel)
			throws IOException
	{
		final List<OrderModel> orders = getSiteOneOrderDao().getQuoteToOrderSendEmail();

		if (null != orders)
		{
			orders.forEach(order -> {
				if (null != order)
				{
					order.getConsignments().forEach(consignment -> {

						if (CollectionUtils
								.isEmpty(getSiteOneOrderDao().getEntryFromSiteoneQuoteToOrderEmailStatus(consignment.getCode())))
						{
							boolean emailSent = false;
							final String quoteNumber = order.getQuoteNumber();
							if (StringUtils.isNotEmpty(quoteNumber))
							{
								final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
										Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
										quoteNumber);

								if (quoteDetails != null && StringUtils.isNotBlank(quoteDetails.getLastApproverEmail()) && BooleanUtils.isTrue(quoteDetails.getShowOnline()))
								{
									eventService
									.publishEvent(initializeEvent(new QuoteToOrderStatusEvent(), order, consignment, quoteDetails, quoteNumber));
							      emailSent = true;
      							order.setIsQuoteToOrderStatusEmailSent(true);
      							modelService.save(order);
								}								

							}
						}
					});
				}
			});
		}
	}


	public QuoteToOrderStatusEvent initializeEvent(final QuoteToOrderStatusEvent event, final OrderModel order,
			ConsignmentModel consignment, final SiteOneQuoteDetailResponseData quoteDetails, final String quoteNumber)
	{
		if (order != null)
		{
			if (order.getUnit() != null)
			{
				if (order.getUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			}
		}
     
		if (quoteDetails != null && quoteDetails.getLastApproverEmail() != null) 
		{
			event.setToMail(quoteDetails.getLastApproverEmail());
		}
		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		if (order != null && order.getCode() != null)
		{
			event.setOrderNumber(order.getCode());
		}
				
		if (order != null && order.getPurchaseOrderNumber() != null)
		{
			event.setPoNumber(order.getPurchaseOrderNumber());
		}
		if (quoteDetails != null && quoteDetails.getJobName() != null)
		{
			event.setJobName(quoteDetails.getJobName());
		}
		if (quoteNumber != null)
		{
			event.setQuoteNumber(quoteNumber);
		}
		if (quoteDetails != null && quoteDetails.getWriter() != null)
		{
			event.setQuoteWriter(quoteDetails.getWriter());
		}
		if (quoteDetails != null && quoteDetails.getAccountManager() != null)
		{
			event.setAccountManager(quoteDetails.getAccountManager());
		}
		if (quoteDetails != null && quoteDetails.getAccountManagerEmail() != null)
		{
			event.setAccountManagerEmail(quoteDetails.getAccountManagerEmail());
		}
		if (quoteDetails != null && quoteDetails.getAccountManagerMobile() != null)
		{
			event.setAccountManagerPhone(quoteDetails.getAccountManagerMobile());		
		}
		if (order != null && order.getUnit() != null && order.getUnit().getUid() != null)
		{
			event.setAccountNumber(order.getUnit().getUid());
		}
		if (quoteDetails != null && quoteDetails.getLastApproverName() != null)
		{
			event.setFirstName(quoteDetails.getLastApproverName().trim().split(" ")[0]);
		}
		if (order != null && order.getCode() != null)
		{
			event.setOrderId(order.getCode());
		}
		if (consignment != null && consignment.getCode() != null)
		{
			event.setConsignmentId(consignment.getCode());
		}
		if (consignment != null && consignment.getStatus() != null && consignment.getStatus().getCode() != null)
		{
			event.setStatus(consignment.getStatus().getCode());
		}
		return event;
	}



	@Override
	public void sendOrderReadyForPickUpRemainderEmail(
			final OrderReadyForPickUpRemainderEmailCronJobModel orderReadyForPickUpRemainderEmailCronJobModel) throws IOException
	{
		final int noOfDaysOld = orderReadyForPickUpRemainderEmailCronJobModel.getFiveDaysOld();
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -noOfDaysOld);
		final Date oldDate = cal.getTime();
		final List<OrderModel> orders = getSiteOneOrderDao().getOrdersToSendReadyForPickUpRemainderEmail();

		orders.forEach(order -> {
			order.getConsignments().forEach(consignment -> {
				if (null != consignment
						&& ConsignmentStatus.READY_FOR_PICKUP.getCode().equalsIgnoreCase(consignment.getStatus().getCode()))
				{
					order.setIsRemainderEmailSent(true);
					modelService.save(order);
					getEventService().publishEvent(
							initializeEvent(new OrderReadyToPickUpEvent(order, consignment), (CustomerModel) order.getUser(), order));
					final SiteoneOrderEmailStatusModel siteoneOrderEmailStatus = getSiteOneOrderDao()
							.getSiteoneConsignmentEmailStatus(consignment.getCode());
					if (null != siteoneOrderEmailStatus)
					{
						siteoneOrderEmailStatus.setIsReminderEmailsent(true);
						modelService.save(siteoneOrderEmailStatus);
					}
				}
			});
		});
	}


	@Override
	public void sendOrderStatusNotification(final OrderStatusNotificationCronJobModel orderStatusNotificationCronJobModel)
			throws IOException
	{
		final List<OrderModel> orders = getSiteOneOrderDao().getOrdersToSendNotification();

		final NotificationHub hub = sendNotificationApi.createNotificationHubClient();

		orders.forEach(order -> {
			order.getConsignments().forEach(consignment -> {
				if (ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode().equalsIgnoreCase(consignment.getStatus().getCode()))
				{
					sendNotificationApi.sendNotification(hub, ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode(), consignment.getCode(), order);
				}
				else if (ConsignmentStatus.READY_FOR_PICKUP.getCode().equalsIgnoreCase(consignment.getStatus().getCode()))
				{
					sendNotificationApi.sendNotification(hub, ConsignmentStatus.READY_FOR_PICKUP.getCode(), consignment.getCode(), order);
				}
				order.setIsOrderStatusNotificationSent(true);
				modelService.save(order);
			});
		});


	}

	@Override
	public String getOrderingAccountforOrder(final String orderId)
	{
		final B2BUnitModel orderingAccount = getSiteOneOrderDao().getOrderingAccountforOrder(orderId);
		if (orderingAccount != null)
		{
			return orderingAccount.getUid();
		}
		return null;
	}

	@Override
	public Date getPurchasedDateforOrder(final String orderId)
	{
		final OrderModel order = getSiteOneOrderDao().getPurchasedDateforOrder(orderId);
		Date purchasedDate = null;
		if (order != null)
		{
			purchasedDate = order.getDate();
		}
		return purchasedDate;
	}


	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event, final CustomerModel customerModel, final OrderModel order)
	{
		if(order.getPointOfService().getBaseStore().getUid().equalsIgnoreCase(SiteoneCoreConstants.SITEONE_CA_BASESTORE)) {
			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}
		else {
			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		event.setCustomer(customerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	@Override
	public void updateHubStorePosForParcelShippingOrder(final CartModel cartModel)
	{
		if (cartModel.getOrderType() != null && "PARCEL_SHIPPING".equalsIgnoreCase(cartModel.getOrderType().getCode()))
		{
			if (CollectionUtils.isNotEmpty(cartModel.getEntries()))
			{
				final AbstractOrderEntryModel entry = cartModel.getEntries().stream().findFirst().get();
				if (entry != null && entry.getProduct() != null)
				{
					final ProductData productData = siteOneOrderEntriesProductConverter.convert(entry.getProduct());
					final PointOfServiceModel hubPos = getInStockHubBranch(productData, cartModel.getPointOfService().getStoreId());
					if (hubPos != null)
					{
						cartModel.setPointOfService(hubPos);
					}
				}

			}
		}

	}

	/**
	 * Checks the stock availability in Hub branches for the input product and current store id
	 *
	 * @target the input product
	 * @param storeId
	 * @return the product stock availability at Hub branch
	 */
	private PointOfServiceModel getInStockHubBranch(final ProductData productData, final String storeId)
	{

		String inStockHubStoreId = null;
		PointOfServiceModel hubPointOfService = null;
		List<PointOfServiceData> posHubStoresDataList = new ArrayList<>();
		final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(storeId);

		if (CollectionUtils.isNotEmpty(pos.getShippingHubBranches()))
		{
			posHubStoresDataList = pointOfServiceConverter.convertAll(pos.getShippingHubBranches());

			final List<List<Object>> stockLevelData = siteOneStockLevelService.getStockLevelsForNearByStores(productData.getCode(),
					posHubStoresDataList);

			if (CollectionUtils.isNotEmpty(stockLevelData))
			{
				final List<Object> stockData = stockLevelData.stream()
						.filter(stockLevelRowData -> ((stockLevelRowData != null && stockLevelRowData.get(2) != null
								&& Long.valueOf(stockLevelRowData.get(2).toString()) > 0)
								|| (BooleanUtils.isNotTrue(productData.getInventoryCheck()) && stockLevelRowData.get(5) != null
										&& BooleanUtils.isTrue((Boolean) stockLevelRowData.get(5)))))
						.findAny().orElse(null);

				inStockHubStoreId = CollectionUtils.isNotEmpty(stockData) ? stockData.get(0).toString() : null;

				hubPointOfService = siteOneStoreFinderService.getStoreForId(inStockHubStoreId);

			}

		}

		return hubPointOfService;
	}

	public Map<String, Boolean> populateFulfilmentStatus(final OrderModel orderModel)
	{
		final Map<String, Boolean> fulfilmentStatus = new HashMap<>();
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_PICKUP, false);
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_DELIVERY, false);
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_SHIPPING, false);

		for (final AbstractOrderEntryModel orderEntry : orderModel.getEntries())
		{
			if (orderEntry.getDeliveryMode().getCode().equals(SiteoneCoreConstants.DELIVERYMODE_PICKUP)
					|| orderEntry.getDeliveryMode().getCode().equals(SiteoneCoreConstants.DELIVERYMODE_DELIVERY)
					|| orderEntry.getDeliveryMode().getCode().equals(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				fulfilmentStatus.put(orderEntry.getDeliveryMode().getCode(), true);
			}
		}
		return fulfilmentStatus;
	}

	@Override
	public OrderModel getOrderForCode(final String orderNumber)
	{
		return getSiteOneOrderDao().getOrderForCode(orderNumber);
	}


	/**
	 * @return the siteOneOrderDao
	 */
	public SiteOneOrderDao getSiteOneOrderDao()
	{
		return siteOneOrderDao;
	}

	/**
	 * @param siteOneOrderDao
	 *           the siteOneOrderDao to set
	 */
	public void setSiteOneOrderDao(final SiteOneOrderDao siteOneOrderDao)
	{
		this.siteOneOrderDao = siteOneOrderDao;
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
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	@Override
	public List<OrderModel> getLastTwoHybrisOrdersForCustomer(final UserModel userModel)
	{
		return getSiteOneOrderDao().getLastTwoHybrisOrdersForCustomer(userModel);
	}

}

