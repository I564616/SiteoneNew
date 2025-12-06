/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;

import com.siteone.facades.populators.PromotionPriceUtils;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author BS
 *
 */
public class OrderAdminApprovalEmailContext extends AbstractEmailContext<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(OrderAdminApprovalEmailContext.class);
	public static final String Discounted_Value = "discountedValue";
	public static final String Unit = "unit";
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private CouponData couponData;
	private B2BCustomerModel customerModel;

	public static final String CC_EMAIL = "ccEmail";
	public static final String CC_DISPLAY_NAME = "ccDisplayName";

	public static final String CC_EMAIL_SITEONE = "ccEmailSiteone";
	public static final String CC_SITEONE_DISPLAY_NAME = "ccSiteoneDisplayName";
	public static final String ACC_NO = "accountNum";

	private static final String FIRST_NAME = "firstName";
	private static final String SHOW_COUPON = "showCoupon";
	private static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(orderProcessModel, emailPageModel);
		storeSessionFacade.setCurrentBaseStore(orderProcessModel.getOrder().getPointOfService().getDivision().getUid());
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{

			customerModel = (B2BCustomerModel) orderProcessModel.getOrder().getUser();
			if (null != orderProcessModel.getOrder().getContactPerson()
					&& null != orderProcessModel.getOrder().getContactPerson().getFirstName())
			{
				put(FIRST_NAME, orderProcessModel.getOrder().getContactPerson().getFirstName());
			}
			if (null != customerModel && CollectionUtils.isNotEmpty(customerModel.getApprovers()))
			{
				final List<String> approverUids = new ArrayList<>();
				for (final B2BCustomerModel b2bAdmin : customerModel.getApprovers())
				{
					approverUids.add(b2bAdmin.getUid());
				}
				final String emailRecipients = approverUids.stream().collect(Collectors.joining(";"));
				orderProcessModel.setToEmails(emailRecipients);
				put(EMAIL, emailRecipients);
			}

		}

		modelService.save(orderProcessModel);
		modelService.refresh(orderProcessModel);

		final Optional<PromotionResultData> optional = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes())).findFirst();
		if (optional.isPresent())
		{
			final PromotionResultData giveAwayCouponPromotion = optional.get();
			final List<CouponData> giftCoupons = giveAwayCouponPromotion.getGiveAwayCouponCodes();
			couponData = giftCoupons.get(0);
		}

		final String discounts = orderData.getTotalDiscounts().getFormattedValue();
		put(Discounted_Value, discounts.substring(1, discounts.length()));
		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			put(Unit, orderProcessModel.getOrder().getOrderingAccount());
			String accNo = "";
			if (null != orderData.getOrderingAccount() && orderData.getOrderingAccount().getUid().contains("_"))
			{
				accNo = orderData.getOrderingAccount().getUid().trim().split("_")[0];
			}
			put(ACC_NO, accNo);
		}

		orderData.getEntries().forEach(entries -> {

			if (null != entries && StringUtils.isNotEmpty(entries.getQuantityText()))
			{
				try
				{
					final double quantity = Double.parseDouble(entries.getQuantityText());
					final DecimalFormat formatter = new DecimalFormat("#0.00");
					entries.setQuantityText(formatter.format(quantity));
				}
				catch (final NumberFormatException numberFormatException)
				{
					LOG.error("Number Format Exception occured", numberFormatException);
				}
			}
		});

		if (null != orderData)
		{
			final List<OrderEntryData> orderDataEntries = orderData.getEntries();
			final List<String> showVoucherList = new ArrayList<>();
			if (null != orderData.getAppliedVouchers())
			{
				showVoucherList.addAll(orderData.getAppliedVouchers());
			}
			if (orderData.getEntries() != null && !orderData.getEntries().isEmpty())
			{
				orderData.getEntries().forEach(entry -> {
					final String productCode = entry.getProduct().getCode();
					final ProductData product = getProductFacade().getProductForCodeAndOptions(productCode,
							Arrays.asList(ProductOption.PROMOTIONS));
					entry.getProduct().setCouponCode(product.getCouponCode());
					if (null != entry.getProduct().getCouponCode() && showVoucherList.contains(entry.getProduct().getCouponCode()))
					{
						showVoucherList.remove(entry.getProduct().getCouponCode());
					}
				});
			}
			put(SHOW_COUPON, showVoucherList);

			final List<PromotionResultData> appliedPromotions = orderData.getAppliedProductPromotions();
			if (null != orderDataEntries && null != appliedPromotions)
			{
				PromotionPriceUtils.findSalePriceForOrderEntries(orderDataEntries, orderData.getAppliedProductPromotions());
			}
		}

		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		storeSessionFacade.removeCurrentBaseStore();
	}


	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel)
	{
		if (null == orderProcessModel.getOrder().getGuestContactPerson())
		{
			return (CustomerModel) orderProcessModel.getOrder().getUser();
		}
		else
		{
			return orderProcessModel.getOrder().getGuestContactPerson();
		}
	}

	protected Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}

	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}

	public OrderData getOrder()
	{
		return orderData;
	}

	public Boolean getOrderPromo()
	{
		return customerModel.getOrderPromoOption();
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getLanguage();
	}

	public CouponData getCoupon()
	{
		return couponData;
	}


	/**
	 * @return the productFacade
	 */
	public ProductFacade getProductFacade()
	{
		return productFacade;
	}


	/**
	 * @param productFacade
	 *           the productFacade to set
	 */
	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

}
