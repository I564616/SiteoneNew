/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.SiteOneInvoiceEntryModel;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.InvoiceEntryData;


/**
 * @author 1219341
 *
 */
public class SiteOneInvoiceDetailsPopulator extends SiteOneInvoiceListPopulator
{
	private Converter<AddressModel, AddressData> addressConverter;
	private Converter<SiteOneInvoiceEntryModel, InvoiceEntryData> siteOneInvoiceEntryConverter;
	private static final Logger LOG = Logger.getLogger(SiteOneInvoiceDetailsPopulator.class);
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	private SiteOneStoreFinderService siteOneStoreFinderService;
	
	@Resource(name = "siteOnePaymentInfoDataConverter")
	private Converter<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData> siteOnePaymentInfoDataConverter;
	
	@Resource(name = "siteOnePOAPaymentInfoDataConverter")
	private Converter<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData> siteOnePOAPaymentInfoDataConverter;
	
	@Override
	public void populate(final SiteOneInvoiceModel source, final InvoiceData target) throws ConversionException
	{
		super.populate(source, target);
		target.setUser(source.getUser());
		if (null != source.getUser())
		{
			final B2BCustomerModel userModel = (B2BCustomerModel) b2bCustomerService.getUserForUID(source.getUser());
			if (null != userModel)
			{
				target.setUserName(userModel.getFirstName() + " " + userModel.getLastName());
			}
		}
		target.setBillingTerms(source.getBillingTerms());
		if (null != source.getAddress())
		{
			target.setAddress(getAddressConverter().convert(source.getAddress()));
		}
		target.setContact_firstName(source.getContact_firstName());
		target.setContact_phone(source.getContact_phone());
		//target.setInstructions(source.getInstructions());
		target.setInstructions(source.getInstructionsText());
		target.setContact_emailId(source.getContact_emailid());

		List<SiteOneInvoiceEntryModel> sortedInvoiceEntries = null;

		if (null != source.getEntries())
		{
			sortedInvoiceEntries = source.getEntries().stream()
					.sorted((entry1, entry2) -> extractInt(entry1.getEntryNumber()).compareTo(extractInt(entry2.getEntryNumber())))
					.collect(Collectors.toList());
		}

		target.setInvoiceEntryList(getSiteOneInvoiceEntryConverter().convertAll(sortedInvoiceEntries));

		target.setSubTotal(truncatePrice(source.getSubTotal()));
		target.setTotalTax(truncatePrice(source.getTotalTax()));
		target.setOrderTotalPrice(truncatePrice(source.getOrderTotalPrice()));

		target.setDeliveryMode(source.getDeliveryMode());
		target.setStoreId(source.getStoreId());
		target.setStoreName(source.getStoreName());
		target.setPickupOrDeliveryDateTime(source.getPickupOrDeliveryDateTime());

		target.setSalesAssociate(source.getSalesAssociate());
		try
		{
			if (null != source.getTotalPayment())
			{
				final double totalPayment = Double.parseDouble(source.getTotalPayment());
				target.setTotalPayment(truncatePrice(totalPayment));
			}

			if (null != source.getAmountDue() && (source.getAmountDue().matches("-?[0-9.]*")))
			{
				final double amountDue = Double.parseDouble(source.getAmountDue());
				target.setAmountDue(truncatePrice(amountDue));
			}

			if (null != source.getFreight())
			{
				final double freight = Double.parseDouble(source.getFreight());
				target.setFreight(truncatePrice(freight));
			}
		}
		catch (final NumberFormatException numberFormatException)
		{
			LOG.error("Number format exception occured", numberFormatException);
		}





		if (null != source.getRemitaddress())
		{
			target.setRemitaddress(getAddressConverter().convert(source.getRemitaddress()));
		}
		target.setTerms(source.getTerms());


		target.setCreditCardType(source.getCreditCardType());
		if (null != source.getCcNumberLastFour())
		{
			target.setCcNumberLastFour(Integer.toString(source.getCcNumberLastFour()));
		}
		target.setOrderedDate(source.getOrderedDate());
		target.setRequestedFor(source.getRequestedFor());
		target.setShipVia(source.getShipVia());
		target.setAuthNumber(source.getAuthNumber());
		target.setPaymentType(source.getPaymentType());
		if (null != source.getBillingaddress())
		{
			target.setBillingaddress(getAddressConverter().convert(source.getBillingaddress()));
		}
		target.setBranchNumber(source.getBranchNumber());
		if (null != source.getBranchAddress())
		{
			target.setBranchAddress(getAddressConverter().convert(source.getBranchAddress()));
		}
		target.setDeliveryBranchNumber(source.getDeliveryBranchNumber());
		target.setCustomerObsessedContactName(source.getCustomerObsessedContactName());
		target.setCustomerObsessedContactTitle(source.getCustomerObsessedContactTitle());
		target.setCustomerObsessedEmail(source.getCustomerObsessedEmail());
		target.setCustomerObsessedPhoneNumber(source.getCustomerObsessedPhoneNumber());
		target.setBillingAccNumber(source.getBillingAccNumber());

		target.setOrderShipmentActualId(source.getOrderShipmentActualId());
		target.setAccountDisplay(source.getAccountDisplay());
		final PointOfServiceModel posModel = siteOneStoreFinderService.getStoreForId(source.getStoreId());
		if (null != posModel)
		{
			target.setStoreTitle(posModel.getTitle());
		}
		
		if (CollectionUtils.isNotEmpty(source.getPaymentInfoList()))
		{
			final SiteoneCreditCardPaymentInfoModel paymentInfo = source.getPaymentInfoList().get(0);
			target.setSiteOnePaymentInfoData(siteOnePaymentInfoDataConverter.convert(paymentInfo));
		}else if(CollectionUtils.isNotEmpty(source.getPoaPaymentInfoList())){
			final SiteonePOAPaymentInfoModel poaPaymentInfo = source.getPoaPaymentInfoList().get(0);
			target.setSiteOnePOAPaymentInfoData(siteOnePOAPaymentInfoDataConverter.convert(poaPaymentInfo));
		}else{
			final SiteOnePaymentInfoData paymentData = new SiteOnePaymentInfoData();
			paymentData.setPaymentType("2");
			target.setSiteOnePaymentInfoData(paymentData);
		}


	}

	private Integer extractInt(final String entryNumber)
	{
		if (entryNumber.isEmpty())
		{
			return Integer.valueOf(0);
		}
		else
		{
			try
			{
				return Integer.valueOf(Integer.parseInt(entryNumber));
			}
			catch (final NumberFormatException numberFormatException)
			{
				return Integer.valueOf(0);
			}

		}
	}


	/**
	 * @return the siteOneInvoiceEntryConverter
	 */
	public Converter<SiteOneInvoiceEntryModel, InvoiceEntryData> getSiteOneInvoiceEntryConverter()
	{
		return siteOneInvoiceEntryConverter;
	}


	/**
	 * @param siteOneInvoiceEntryConverter
	 *           the siteOneInvoiceEntryConverter to set
	 */
	public void setSiteOneInvoiceEntryConverter(
			final Converter<SiteOneInvoiceEntryModel, InvoiceEntryData> siteOneInvoiceEntryConverter)
	{
		this.siteOneInvoiceEntryConverter = siteOneInvoiceEntryConverter;
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


	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}


	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}


	/**
	 * This method is used to truncate the price into 2.
	 *
	 * @param priceToTruncate
	 *           - price needs to be truncated.
	 *
	 * @return truncated price
	 */
	private String truncatePrice(final Double priceToTruncate)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat = new DecimalFormat("0.00");
			decimalFormat.setRoundingMode(RoundingMode.DOWN);

			final String truncatedPrice = decimalFormat.format(priceToTruncate);
			return truncatedPrice;
		}
		else
		{
			return null;
		}

	}

}
