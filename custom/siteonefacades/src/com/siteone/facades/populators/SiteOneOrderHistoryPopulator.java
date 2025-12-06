/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.order.converters.populator.OrderHistoryPopulator;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.consignmenttrackingservices.service.ConsignmentTrackingService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.checkout.facades.utils.SiteOneCheckoutRequestedDateUtils;
import com.siteone.core.customer.SiteOneCustomerAccountService;


/**
 * @author 1219341
 *
 */
public class SiteOneOrderHistoryPopulator extends OrderHistoryPopulator
{
	
	private static final Logger LOG = Logger.getLogger(SiteOneOrderHistoryPopulator.class);
	
	final int digits = 2;
	@Resource(name = "consignmentConverter")
	private Converter<ConsignmentModel, ConsignmentData> consignmentConverter;

	@Resource(name = "consignmentTrackingService")
	private ConsignmentTrackingService consignmentTrackingService;

	@Resource(name = "siteOneCheckoutRequestedDateUtils")
	private SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils;

	@Resource(name = "siteOnePaymentInfoDataConverter")
	private Converter<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData> siteOnePaymentInfoDataConverter;

	@Resource(name = "siteOnePOAPaymentInfoDataConverter")
	private Converter<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData> siteOnePOAPaymentInfoDataConverter;
	
	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	/**
	 * 
	 * @return the siteOneCheckoutRequestedDateUtils
	 */
	public SiteOneCheckoutRequestedDateUtils getSiteOneCheckoutRequestedDateUtils()
	{
		return siteOneCheckoutRequestedDateUtils;
	}


	/**
	 * @param siteOneCheckoutRequestedDateUtils
	 *           the siteOneCheckoutRequestedDateUtils to set
	 */
	public void setSiteOneCheckoutRequestedDateUtils(final SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils)
	{
		this.siteOneCheckoutRequestedDateUtils = siteOneCheckoutRequestedDateUtils;
	}


	@Override
	public void populate(final OrderModel source, final OrderHistoryData target)
	{
		super.populate(source, target);
		if (source.getTotalPrice() != null)
		{
			final BigDecimal totalPrice = BigDecimal.valueOf(source.getTotalPrice().doubleValue());
			target.setTotal(getPriceDataFactory().create(PriceDataType.BUY, totalPrice, source.getCurrency()));
		}
		if(source.getOrderType() != null) {
			target.setOrderType(source.getOrderType().getCode());
		}
		if(source.getHybrisOrderNumber() != null) {
			target.setHybrisOrderNumber(source.getHybrisOrderNumber());
			final List<OrderModel> orderModelList = ((SiteOneCustomerAccountService) customerAccountService).getOrdersWithSameHybrisOrderNumber(source.getHybrisOrderNumber());
			if(!CollectionUtils.isEmpty(orderModelList) && orderModelList.size() > 1)
			{
				target.setIsPartOfMasterHybrisOrder(true);
			}
			else
			{
				target.setIsPartOfMasterHybrisOrder(false);
			}
		}
		if(source.getIsHybrisOrder() != null) {
			target.setIsHybrisOrder(source.getIsHybrisOrder());
		}
		if (source.getConsignments().size() == 1)
		{
			target.setInvoiceNumber(source.getConsignments().iterator().next().getInvoiceNumber());

		}
		target.setTotalPriceWithTax(
				(getPriceDataFactory().create(PriceDataType.BUY, target.getTotal().getValue(), source.getCurrency())));
		target.getTotalPriceWithTax().setValue(target.getTotal().getValue());
		target.getTotalPriceWithTax().setFormattedValue("$".concat(target.getTotal().getValue().toString()));


		//Consignment population
		if (CollectionUtils.isNotEmpty(source.getConsignments()))
		{
			target.setConsignments(consignmentConverter.convertAll(source.getConsignments()));
		}
		if (target.getConsignments() != null && !target.getConsignments().isEmpty())
		{
			source.getConsignments().forEach(consignmentModel -> {
				final List<URL> trackingUrl = new ArrayList<URL>();
				if(consignmentModel.getTrackingLink()!=null) {
					try
					{
						trackingUrl.add(URI.create(consignmentModel.getTrackingLink()).toURL());
						LOG.info("Tracking URL "+ trackingUrl);
					}
					catch (final MalformedURLException e)
					{
						LOG.error("Invalid Tracking URL", e);
					}
					
				}else {
					trackingUrl.add(consignmentTrackingService.getTrackingUrlForConsignment(consignmentModel));
				}
				target.getConsignments().forEach(consignmentData -> {
					if (consignmentData.getCode().equalsIgnoreCase(consignmentModel.getCode()))
					{
						consignmentData.setTrackingUrl(trackingUrl.get(0));
						if (consignmentModel.getTotal() != null)
						{
							consignmentData.setTotal(consignmentModel.getTotal());
						}
						if (consignmentModel.getInvoiceNumber() != null)
						{
							consignmentData.setInvoiceNumber(consignmentModel.getInvoiceNumber());
						}
						if (null != consignmentModel.getRequestedDate())
						{
							consignmentData.setRequestedDate(
									siteOneCheckoutRequestedDateUtils.convertDateToUTC(consignmentModel.getRequestedDate()));
						}
						if (null != consignmentModel.getRequestedMeridian())
						{
							consignmentData.setRequestedMeridian(consignmentModel.getRequestedMeridian().getCode());
						}
						if (null != consignmentModel.getFullfilmentStatus())
						{
							consignmentData.setDeliveryMode(consignmentModel.getFullfilmentStatus());
						}
						if (null != consignmentModel.getSpecialInstructions())
						{
							consignmentData.setSpecialInstructions(consignmentModel.getSpecialInstructions());
						}
					}
				});
			});

		}

		if (CollectionUtils.isNotEmpty(source.getPaymentInfoList()))
		{
			final SiteoneCreditCardPaymentInfoModel paymentInfo = source.getPaymentInfoList().get(0);
			target.setSiteOnePaymentInfoData(siteOnePaymentInfoDataConverter.convert(paymentInfo));
		}
		else if (CollectionUtils.isNotEmpty(source.getPoaPaymentInfoList()))
		{
			final SiteonePOAPaymentInfoModel poaPaymentInfo = source.getPoaPaymentInfoList().get(0);
			target.setSiteOnePOAPaymentInfoData(siteOnePOAPaymentInfoDataConverter.convert(poaPaymentInfo));
		}
		else
		{
			final SiteOnePaymentInfoData paymentData = new SiteOnePaymentInfoData();
			paymentData.setPaymentType("2");
			target.setSiteOnePaymentInfoData(paymentData);
		}
	}


	/**
	 * @return the consignmentConverter
	 */
	public Converter<ConsignmentModel, ConsignmentData> getConsignmentConverter()
	{
		return consignmentConverter;
	}

	/**
	 * @param consignmentConverter
	 *           the consignmentConverter to set
	 */
	public void setConsignmentConverter(final Converter<ConsignmentModel, ConsignmentData> consignmentConverter)
	{
		this.consignmentConverter = consignmentConverter;
	}

}
