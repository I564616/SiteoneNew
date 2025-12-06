/**
 *
 */
package com.siteone.facades.consignment.populator;


import de.hybris.platform.commercefacades.order.converters.populator.OrderConsignmentPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.consignmenttrackingservices.delivery.data.ConsignmentEventData;
import de.hybris.platform.consignmenttrackingservices.service.ConsignmentTrackingService;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

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
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;


/**
 * Class for Populating custom consignment attributes from consignment model.
 *
 * @author Ravi P(RP01944)
 *
 */
public class SiteOneOrderConsignmentPopulator extends OrderConsignmentPopulator<OrderModel, OrderData>
{

	private static final Logger LOG = Logger.getLogger(SiteOneOrderConsignmentPopulator.class);
	
	@Resource(name = "consignmentTrackingService")
	private ConsignmentTrackingService consignmentTrackingService;

	@Resource(name = "siteOneCheckoutRequestedDateUtils")
	private SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils;
	
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	private PriceDataFactory priceDataFactory;

	private Converter<AddressModel, AddressData> addressConverter;


	/**
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


	/**
	 * @return the addressConverter
	 */
	public Converter<AddressModel, AddressData> getAddressConverter()
	{
		return addressConverter;
	}


	/**
	 * @param addressConverter
	 *           the addressConverter to set
	 */
	public void setAddressConverter(final Converter<AddressModel, AddressData> addressConverter)
	{
		this.addressConverter = addressConverter;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final OrderModel source, final OrderData target) throws ConversionException
	{
		super.populate(source, target);

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
						if(!trackingUrl.isEmpty()) {
							consignmentData.setTrackingUrl(trackingUrl.get(0));
						}
						consignmentData.setTrackingLink(consignmentModel.getTrackingLink());
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
						else if(null != consignmentModel.getDeliveryMode() && null != consignmentModel.getDeliveryMode().getCode())
						{
							consignmentData.setDeliveryMode(consignmentModel.getDeliveryMode().getCode());
						}
						if (null != consignmentModel.getSpecialInstructions())
						{
							consignmentData.setSpecialInstructions(consignmentModel.getSpecialInstructions());
						}
						if (null != consignmentModel.getFreight())
						{
							final Double freight = Double.valueOf(Double.parseDouble(consignmentModel.getFreight()));
							final PriceData freightPriceData = createPrice(source, freight);
							consignmentData.setFreight(freightPriceData.getFormattedValue());
						}
						if (null != consignmentModel.getTax())
						{
							final Double tax = Double.valueOf(Double.parseDouble(consignmentModel.getTax()));
							final PriceData taxPriceData = createPrice(source, tax);
							consignmentData.setTax(taxPriceData.getFormattedValue());
						}
						if (null != consignmentModel.getSubTotal())
						{
							final Double subTotal = Double.valueOf(Double.parseDouble(consignmentModel.getSubTotal()));
							final PriceData subTotalPriceData = createPrice(source, subTotal);
							consignmentData.setSubTotal(subTotalPriceData.getFormattedValue());
						}
						if (null != consignmentModel.getDeliveryPointOfService())
						{
							consignmentData.setPointOfService(((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(consignmentModel.getDeliveryPointOfService().getStoreId()));
						}
						else if(null != consignmentModel.getWarehouse().getPointsOfService())
						{
							List<PointOfServiceModel> stores = (List<PointOfServiceModel>) consignmentModel.getWarehouse().getPointsOfService();
							consignmentData.setPointOfService(((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(stores.get(0).getStoreId()));
						}
						if (null != consignmentModel.getConsignmentAddress())
						{
							consignmentData
									.setConsignmentAddress(getAddressConverter().convert(consignmentModel.getConsignmentAddress()));
						}
					}
				});
			});
		}

		if (CollectionUtils.isNotEmpty(source.getEntries()))
		{
			target.setUnconsignedEntries(Converters.convertAll(source.getEntries(), getOrderEntryConverter()));
		}

	}

	protected PriceData createPrice(final OrderModel source, final Double val)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("source order must not be null");
		}

		final CurrencyModel currency = source.getCurrency();
		if (currency == null)
		{
			throw new IllegalArgumentException("source order currency must not be null");
		}

		// Get double value, handle null as zero
		final double priceValue = val != null ? val.doubleValue() : 0d;

		return getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(priceValue), currency);
	}


	/**
	 * @return the priceDataFactory
	 */
	public PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}


	/**
	 * @param priceDataFactory
	 *           the priceDataFactory to set
	 */
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

}
