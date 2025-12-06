/**
 *
 */
package com.siteone.facades.checkout.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.externaltax.ExternalTaxesService;
import de.hybris.platform.commerceservices.order.hook.CommercePlaceOrderMethodHook;
import de.hybris.platform.commerceservices.order.impl.DefaultCommercePlaceOrderStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CalculationService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.OrderService;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.payment.PaymentService;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.promotions.model.PromotionResultModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.checkout.facades.utils.SiteOnePaymentUtil;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;


/**
 *
 */
public class DefaultSiteOneCommercePlaceOrderStrategy extends DefaultCommercePlaceOrderStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCommercePlaceOrderStrategy.class);

	private ModelService modelService;
	private DeliveryService deliveryService;
	private CommonI18NService commonI18NService;
	private PaymentService paymentService;
	private OrderService orderService;
	private BaseSiteService baseSiteService;
	private BaseStoreService baseStoreService;
	private PromotionsService promotionsService;
	private CalculationService calculationService;
	private ExternalTaxesService externalTaxesService;
	private List<CommercePlaceOrderMethodHook> commercePlaceOrderMethodHooks;
	private ConfigurationService configurationService;
	private TimeService timeService;

	@Resource(name = "siteOnePaymentUtil")
	private SiteOnePaymentUtil siteOnePaymentUtil;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "siteOnePaymentInfoDataReverseConverter")
	private Converter<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel> siteOnePaymentInfoDataReverseConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Override
	public CommerceOrderResult placeOrder(final CommerceCheckoutParameter parameter) throws InvalidCartException
	{
		LOG.error("Elaa Inside custom DefaultSiteOneCommercePlaceOrderStrategy");
		final CartModel cartModel = parameter.getCart();
		validateParameterNotNull(cartModel, "Cart model cannot be null");
		final CommerceOrderResult result = new CommerceOrderResult();
		try
		{
			beforePlaceOrder(parameter);
			if (calculationService.requiresCalculation(cartModel))
			{
				// does not make sense to fail here especially since we don't fail below when we calculate order.
				// throw new IllegalArgumentException(String.format("Cart [%s] must be calculated", cartModel.getCode()));
				LOG.error(String.format("CartModel's [%s] calculated flag was false", cartModel.getCode()));
			}

			final CustomerModel customer = (CustomerModel) cartModel.getUser();
			validateParameterNotNull(customer, "Customer model cannot be null");

			final OrderModel orderModel = getOrderService().createOrderFromCart(cartModel);
			if (orderModel != null)
			{
				// Credit card sale transaction for DC stores
				processSalesCreditCardPayment(orderModel);

				// Reset the Date attribute for use in determining when the order was placed
				orderModel.setDate(timeService.getCurrentTime());

				// Store the current site and store on the order
				orderModel.setSite(getBaseSiteService().getCurrentBaseSite());
				orderModel.setStore(getBaseStoreService().getCurrentBaseStore());
				orderModel.setLanguage(getCommonI18NService().getCurrentLanguage());

				if (parameter.getSalesApplication() != null)
				{
					orderModel.setSalesApplication(parameter.getSalesApplication());
				}

				// clear the promotionResults that where cloned from cart PromotionService.transferPromotionsToOrder will copy them over bellow.
				orderModel.setAllPromotionResults(Collections.<PromotionResultModel> emptySet());

				getModelService().saveAll(customer, orderModel);

				if (cartModel.getPaymentInfo() != null && cartModel.getPaymentInfo().getBillingAddress() != null)
				{
					final AddressModel billingAddress = cartModel.getPaymentInfo().getBillingAddress();
					orderModel.setPaymentAddress(billingAddress);
					orderModel.getPaymentInfo().setBillingAddress(getModelService().clone(billingAddress));
					getModelService().save(orderModel.getPaymentInfo());
				}
				getModelService().save(orderModel);
				// Transfer promotions to the order
				getPromotionsService().transferPromotionsToOrder(cartModel, orderModel, false);

				// Calculate the order now that it has been copied
				try
				{
					getCalculationService().calculateTotals(orderModel, false);
					getExternalTaxesService().calculateExternalTaxes(orderModel);
				}
				catch (final CalculationException ex)
				{
					LOG.error("Failed to calculate order [" + orderModel + "]", ex);
				}

				getModelService().refresh(orderModel);
				getModelService().refresh(customer);

				result.setOrder(orderModel);

				this.beforeSubmitOrder(parameter, result);

				getOrderService().submitOrder(orderModel);
			}
			else
			{
				throw new IllegalArgumentException(String.format("Order was not properly created from cart %s", cartModel.getCode()));
			}
		}
		finally
		{
			getExternalTaxesService().clearSessionTaxDocument();
		}

		this.afterPlaceOrder(parameter, result);
		return result;
	}

	public void processSalesCreditCardPayment(final OrderModel orderModel) throws InvalidCartException
	{
		final PointOfServiceData pos = sessionService.getAttribute("sessionStore");
		final boolean flag = pos != null && pos.getHubStores() != null && pos.getHubStores().get(0) != null
				&& orderModel.getOrderType() != null && orderModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING")
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("CCEnabledDCShippingBranches",
						pos.getHubStores().get(0));
		if (flag && sessionService.getAttribute("vaultToken") != null)
		{
			final CartData cartData = cartFacade.getSessionCart();
			final SiteOnePaymentInfoData siteOnePaymentData = siteOnePaymentUtil.processCayanSalePayment(cartData,
					orderModel.getCode());
			sessionService.removeAttribute("vaultToken");
			sessionService.removeAttribute("ewalletData");
			sessionService.removeAttribute("isEwalletCard");
			if (null == siteOnePaymentData)
			{
				LOG.error("Invalid cayan response");
				throw new InvalidCartException("Invalid cayan response");
			}
			final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = setCreditCardPaymentInfo(siteOnePaymentData, orderModel);
			final List<SiteoneCreditCardPaymentInfoModel> paymentInfoList = new ArrayList();
			paymentInfoList.add(creditCardPaymentInfo);
			orderModel.setPaymentInfoList(paymentInfoList);
		}

	}

	public SiteoneCreditCardPaymentInfoModel setCreditCardPaymentInfo(final SiteOnePaymentInfoData siteOnePaymentData,
			final OrderModel orderModel)
	{

		final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = siteOnePaymentInfoDataReverseConverter
				.convert(siteOnePaymentData);
		creditCardPaymentInfo.setUser(orderModel.getUser());
		creditCardPaymentInfo.setCode(orderModel.getCode());


		getModelService().save(creditCardPaymentInfo);
		return creditCardPaymentInfo;
	}

	/**
	 * @return the modelService
	 */
	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the deliveryService
	 */
	@Override
	public DeliveryService getDeliveryService()
	{
		return deliveryService;
	}

	/**
	 * @param deliveryService
	 *           the deliveryService to set
	 */
	@Override
	public void setDeliveryService(final DeliveryService deliveryService)
	{
		this.deliveryService = deliveryService;
	}

	/**
	 * @return the commonI18NService
	 */
	@Override
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	@Override
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the paymentService
	 */
	@Override
	public PaymentService getPaymentService()
	{
		return paymentService;
	}

	/**
	 * @param paymentService
	 *           the paymentService to set
	 */
	@Override
	public void setPaymentService(final PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	/**
	 * @return the orderService
	 */
	@Override
	public OrderService getOrderService()
	{
		return orderService;
	}

	/**
	 * @param orderService
	 *           the orderService to set
	 */
	@Override
	public void setOrderService(final OrderService orderService)
	{
		this.orderService = orderService;
	}

	/**
	 * @return the baseSiteService
	 */
	@Override
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	@Override
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the baseStoreService
	 */
	@Override
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	@Override
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the promotionsService
	 */
	@Override
	public PromotionsService getPromotionsService()
	{
		return promotionsService;
	}

	/**
	 * @param promotionsService
	 *           the promotionsService to set
	 */
	@Override
	public void setPromotionsService(final PromotionsService promotionsService)
	{
		this.promotionsService = promotionsService;
	}

	/**
	 * @return the calculationService
	 */
	@Override
	public CalculationService getCalculationService()
	{
		return calculationService;
	}

	/**
	 * @param calculationService
	 *           the calculationService to set
	 */
	@Override
	public void setCalculationService(final CalculationService calculationService)
	{
		this.calculationService = calculationService;
	}

	/**
	 * @return the externalTaxesService
	 */
	@Override
	public ExternalTaxesService getExternalTaxesService()
	{
		return externalTaxesService;
	}

	/**
	 * @param externalTaxesService
	 *           the externalTaxesService to set
	 */
	@Override
	public void setExternalTaxesService(final ExternalTaxesService externalTaxesService)
	{
		this.externalTaxesService = externalTaxesService;
	}

	/**
	 * @return the commercePlaceOrderMethodHooks
	 */
	@Override
	public List<CommercePlaceOrderMethodHook> getCommercePlaceOrderMethodHooks()
	{
		return commercePlaceOrderMethodHooks;
	}

	/**
	 * @param commercePlaceOrderMethodHooks
	 *           the commercePlaceOrderMethodHooks to set
	 */
	@Override
	public void setCommercePlaceOrderMethodHooks(final List<CommercePlaceOrderMethodHook> commercePlaceOrderMethodHooks)
	{
		this.commercePlaceOrderMethodHooks = commercePlaceOrderMethodHooks;
	}

	/**
	 * @return the configurationService
	 */
	@Override
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService
	 *           the configurationService to set
	 */
	@Override
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	/**
	 * @return the timeService
	 */
	@Override
	public TimeService getTimeService()
	{
		return timeService;
	}

	/**
	 * @param timeService
	 *           the timeService to set
	 */
	@Override
	public void setTimeService(final TimeService timeService)
	{
		this.timeService = timeService;
	}

	/**
	 * @return the siteOnePaymentUtil
	 */
	public SiteOnePaymentUtil getSiteOnePaymentUtil()
	{
		return siteOnePaymentUtil;
	}

	/**
	 * @param siteOnePaymentUtil
	 *           the siteOnePaymentUtil to set
	 */
	public void setSiteOnePaymentUtil(final SiteOnePaymentUtil siteOnePaymentUtil)
	{
		this.siteOnePaymentUtil = siteOnePaymentUtil;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @return the cartFacade
	 */
	public CartFacade getCartFacade()
	{
		return cartFacade;
	}

	/**
	 * @param cartFacade
	 *           the cartFacade to set
	 */
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	/**
	 * @return the siteOnePaymentInfoDataReverseConverter
	 */
	public Converter<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel> getSiteOnePaymentInfoDataReverseConverter()
	{
		return siteOnePaymentInfoDataReverseConverter;
	}

	/**
	 * @param siteOnePaymentInfoDataReverseConverter
	 *           the siteOnePaymentInfoDataReverseConverter to set
	 */
	public void setSiteOnePaymentInfoDataReverseConverter(
			final Converter<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel> siteOnePaymentInfoDataReverseConverter)
	{
		this.siteOnePaymentInfoDataReverseConverter = siteOnePaymentInfoDataReverseConverter;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService
	 *           the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}



}
