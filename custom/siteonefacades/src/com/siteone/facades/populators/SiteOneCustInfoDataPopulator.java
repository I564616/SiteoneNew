/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.customer.info.PartnerPointsProgramsDevInfo;
import com.siteone.facade.customer.info.CustomerCreditInfoData;
import com.siteone.facade.customer.info.CustomerInfoStatusData;
import com.siteone.facade.customer.info.CustomerLoyaltyAttributesData;
import com.siteone.facade.customer.info.CustomerLoyaltyProfileData;
import com.siteone.facade.customer.info.CustomerRewardsPointsInfoData;
import com.siteone.facade.customer.info.CustomerSalesInfoData;
import com.siteone.facade.customer.info.LoyaltyProgramInfoData;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;


/**
 * @author 1197861
 *
 */
public class SiteOneCustInfoDataPopulator implements Populator<SiteOneCustInfoResponeData, SiteOneCustInfoData>
{
	private static final Logger LOG = Logger.getLogger(SiteOneCustInfoDataPopulator.class);


	@Override
	public void populate(final SiteOneCustInfoResponeData source, final SiteOneCustInfoData target) throws ConversionException
	{

		if (null != source)
		{

			target.setCustTreeNodeID(source.getCustTreeNodeID());

			if (null != source.getCustomerCreditInfo())
			{
				final CustomerCreditInfoData customerCreditInfoData = new CustomerCreditInfoData();
				customerCreditInfoData.setArType(source.getCustomerCreditInfo().getArType());
				customerCreditInfoData.setBalance(source.getCustomerCreditInfo().getBalance());
				customerCreditInfoData.setCreditTermDescription(source.getCustomerCreditInfo().getCreditTermDescription());

				customerCreditInfoData.setArFor0to30Days(source.getCustomerCreditInfo().getArFor0to30Days());
				customerCreditInfoData.setArFor30to60Days(source.getCustomerCreditInfo().getArFor30to60Days());
				customerCreditInfoData.setArFor60to90Days(source.getCustomerCreditInfo().getArFor60to90Days());
				customerCreditInfoData.setArFor90to120Days(source.getCustomerCreditInfo().getArFor90to120Days());
				customerCreditInfoData.setArForOver120Days(source.getCustomerCreditInfo().getArForOver120Days());
				customerCreditInfoData.setFutureAR(source.getCustomerCreditInfo().getFutureAR());

				if(StringUtils.isNotBlank(source.getCustomerCreditInfo().getLastInvoiceOn())) {
				customerCreditInfoData.setLastInvoiceOn(getFormattedDate(source.getCustomerCreditInfo().getLastInvoiceOn()));	}
				if(StringUtils.isNotBlank(source.getCustomerCreditInfo().getLastPaymentReceivedOn())) {
				customerCreditInfoData
						.setLastPaymentReceivedOn(getFormattedDate(source.getCustomerCreditInfo().getLastPaymentReceivedOn())); }
				customerCreditInfoData.setCreditLimit(source.getCustomerCreditInfo().getCreditLimit());
				customerCreditInfoData.setCreditCode(source.getCustomerCreditInfo().getCreditCode());
				customerCreditInfoData.setOpenToBuy(source.getCustomerCreditInfo().getOpenToBuy());
				customerCreditInfoData.setCreditRepName(source.getCustomerCreditInfo().getCreditRepName());
				customerCreditInfoData.setCredRepContact(source.getCustomerCreditInfo().getCredRepContact());
				customerCreditInfoData.setCreditTermCode(source.getCustomerCreditInfo().getCreditTermCode());
				customerCreditInfoData.setMaxOpenToBuy(source.getCustomerCreditInfo().getMaxOpenToBuy());

				target.setCustomerCreditInfo(customerCreditInfoData);
			}
			if (null != source.getCustomerRewardsPointsInfo())
			{

				final CustomerRewardsPointsInfoData customerRewardsPointsInfoData = new CustomerRewardsPointsInfoData();
				customerRewardsPointsInfoData.setAvailablePoints(source.getCustomerRewardsPointsInfo().getAvailablePoints());
				customerRewardsPointsInfoData
						.setEnrolledInCurrentYearProgram(source.getCustomerRewardsPointsInfo().getEnrolledInCurrentYearProgram());
				customerRewardsPointsInfoData.setExpiringPoints(source.getCustomerRewardsPointsInfo().getExpiringPoints());
				customerRewardsPointsInfoData
						.setLastYearPendingPoints(source.getCustomerRewardsPointsInfo().getLastYearPendingPoints());
				customerRewardsPointsInfoData
						.setTotalAvailablePoints(source.getCustomerRewardsPointsInfo().getTotalAvailablePoints());
				customerRewardsPointsInfoData.setPendingPoints(source.getCustomerRewardsPointsInfo().getPendingPoints());
				target.setCustomerRewardsPointsInfo(customerRewardsPointsInfoData);
			}
			if (null != source.getLoyaltyProgramInfo())
			{

				final LoyaltyProgramInfoData loyaltyProgramInfoData = new LoyaltyProgramInfoData();

				if (null != source.getLoyaltyProgramInfo().getPrograms())
				{
					final PartnerPointsProgramsDevInfo partnersProgramInfo = (null != source.getLoyaltyProgramInfo().getPrograms()
							.getPartnersProgramDev()) ? source.getLoyaltyProgramInfo().getPrograms().getPartnersProgramDev()
									: source.getLoyaltyProgramInfo().getPrograms().getPartnersProgram();
					if (partnersProgramInfo.getSubLedgers() != null)
					{
						loyaltyProgramInfoData.setAvailablePoints((null != partnersProgramInfo.getSubLedgers().getAvailablePoints())
								? partnersProgramInfo.getSubLedgers().getAvailablePoints().getCurrentBalance()
								: 0.00F);
						loyaltyProgramInfoData
								.setMinimumSpentThreshold((null != partnersProgramInfo.getSubLedgers().getMinimumSpentThreshold())
										? partnersProgramInfo.getSubLedgers().getMinimumSpentThreshold().getCurrentBalance()
										: 0.00F);
						loyaltyProgramInfoData.setExpiringPoints((null != partnersProgramInfo.getSubLedgers().getExpiringPoints())
								? partnersProgramInfo.getSubLedgers().getExpiringPoints().getCurrentBalance()
								: 0.00F);
						loyaltyProgramInfoData
								.setLastYearPendingPoints((null != partnersProgramInfo.getSubLedgers().getPendinPointsFromLastYear())
										? partnersProgramInfo.getSubLedgers().getPendinPointsFromLastYear().getCurrentBalance()
										: 0.00F);
						loyaltyProgramInfoData
								.setTotalAvailablePoints((null != partnersProgramInfo.getSubLedgers().getTotalAvailablePoints())
										? partnersProgramInfo.getSubLedgers().getTotalAvailablePoints().getCurrentBalance()
										: 0.00F);
						loyaltyProgramInfoData.setPendingPoints((null != partnersProgramInfo.getSubLedgers().getPendingPoints())
								? partnersProgramInfo.getSubLedgers().getPendingPoints().getCurrentBalance()
								: 0.00F);
						loyaltyProgramInfoData.setValue((null != partnersProgramInfo.getSubLedgers().getValue())
								? partnersProgramInfo.getSubLedgers().getValue().getCurrentBalance()
								: 0.00F);
						target.setLoyaltyProgramInfo(loyaltyProgramInfoData);
					}

				}


			}

			if (null != source.getProfile())
			{
				final CustomerLoyaltyProfileData profile = new CustomerLoyaltyProfileData();
				profile.setId(source.getProfile().getId());
				final CustomerLoyaltyAttributesData attributes = new CustomerLoyaltyAttributesData();
				if (null != source.getProfile().getAttributes())
				{
					attributes.setCustomerID(source.getProfile().getAttributes().getCustomerID());
					attributes.setPartnerProgramAdminEmail(source.getProfile().getAttributes().getPartnerProgramAdminEmail());
					attributes.setTradeClass(source.getProfile().getAttributes().getTradeClass());
				}
				profile.setAttributes(attributes);
				target.setProfile(profile);
			}

			if (null != source.getCustomerSalesInfo())
			{
				final CustomerSalesInfoData customerSalesInfoData = new CustomerSalesInfoData();
				customerSalesInfoData.setLastYtdSales(source.getCustomerSalesInfo().getLastYtdSales());
				customerSalesInfoData.setTwelveMonthSales(source.getCustomerSalesInfo().getTwelveMonthSales());
				customerSalesInfoData.setYtdSales(source.getCustomerSalesInfo().getYtdSales());
				target.setCustomerSalesInfo(customerSalesInfoData);
			}

			if (null != source.getStatus())
			{
				final CustomerInfoStatusData customerInfoStatusData = new CustomerInfoStatusData();
				customerInfoStatusData.setCode(source.getStatus().getCode());
				customerInfoStatusData.setDescription(source.getStatus().getDescription());
				customerInfoStatusData.setInternalCode(source.getStatus().getInternalCode());
				target.setStatus(customerInfoStatusData);
			}

		}

	}

	private String getFormattedDate(final String inputDateStr)
	{
		String outputDate = null;
		final DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		final DateFormat dateFormatTo = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
			final Date inputDate = dateFormatFrom.parse(inputDateStr, new ParsePosition(0));
			outputDate = dateFormatTo.format(inputDate);
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
		}

		return outputDate;
	}
}