/**
 *
 */
package com.siteone.core.ewallet.service.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.ewallet.service.SiteOneEwalletService;
import com.siteone.core.exceptions.EwalletNotFoundException;
import com.siteone.core.featureswitch.dao.impl.DefaultSiteOneFeatureSwitchDao;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;


/**
 * @author RSUBATHR
 *
 */
public class DefaultSiteOneEwalletService implements SiteOneEwalletService
{

	@Resource(name = "defaultSiteOneEwalletDao")
	DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;

	@Resource(name = "defaultSiteOneFeatureSwitchDao")
	DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.ewallet.service.SiteOneEwalletService#getCreditCardDetails(java.lang.String)
	 */
	@Override
	public SiteoneEwalletCreditCardModel getCreditCardDetails(final String vaultToken) throws EwalletNotFoundException
	{

		final SiteoneEwalletCreditCardModel creditCardModel = defaultSiteOneEwalletDao.getCreditCardDetails(vaultToken);

		if (creditCardModel == null)
		{
			throw new EwalletNotFoundException("Unable to fetch ewallet details");
		}

		return creditCardModel;
	}

	@Override
	public SearchPageData<SiteoneEwalletCreditCardModel> getEWalletCardDetails(final PageableData pageableData,
			final String userUnitId, final String trimmedSearchParam, final String sortCode, final Boolean shipToFlag)
	{
		final SearchPageData<SiteoneEwalletCreditCardModel> eWalletCards = defaultSiteOneEwalletDao
				.getEWalletCardDetails(pageableData, userUnitId, trimmedSearchParam, sortCode, shipToFlag);

		return eWalletCards;
	}

	@Override
	public List<SiteoneEwalletCreditCardModel> getEWalletCardDetailsForCheckout(final String userUnitId)
	{
		final List<SiteoneEwalletCreditCardModel> eWalletCards = defaultSiteOneEwalletDao
				.getEWalletCardDetailsForCheckout(userUnitId);

		return eWalletCards;
	}

	@Override
	public List<Object> getEwalletOrderStatus(final String vaultToken)
	{
		final List<Object> ewalletOrderStatus = defaultSiteOneEwalletDao.getEwalletOrderStatus(vaultToken);

		return ewalletOrderStatus;
	}

	@Override
	public String getValueForSwitch(final String switchKey)
	{
		if (switchKey.equalsIgnoreCase("GuestChekoutEnabledBranches")
				|| switchKey.equalsIgnoreCase("ShippingandDeliveryFeeBranches")
				|| switchKey.equalsIgnoreCase("ShippingandDeliveryLAFeeBranches")
				|| switchKey.equalsIgnoreCase("MixedCartEnabledBranches")
				|| switchKey.equalsIgnoreCase("SiteoneDeliveryFeeFeatureSwitch") || switchKey.equalsIgnoreCase("CartPageSize")
				|| switchKey.equalsIgnoreCase("QuotesFeatureSwitch") || switchKey.equalsIgnoreCase("ApproveOrders")
				|| switchKey.equalsIgnoreCase("d365cspPriceBranches") || switchKey.equalsIgnoreCase("GuestOrRetailCheckoutDisabledBranches")
				||switchKey.equalsIgnoreCase("EnabledB2BUnitForAgroAI") || switchKey.equalsIgnoreCase("BulkDeliveryBranches")
				|| switchKey.equalsIgnoreCase("SplitMixedPickupBranches") || switchKey.equalsIgnoreCase("BulkBigBag")
				|| switchKey.equalsIgnoreCase("RetailBranches") || switchKey.equalsIgnoreCase("ItemLevelShippingFeeBranches")
				|| switchKey.equalsIgnoreCase("BranchesDeliveryThreshold")
				|| switchKey.equalsIgnoreCase("SolrIndexedPropertyToBeRemovedUS")
				|| switchKey.equalsIgnoreCase("SolrIndexedPropertyToBeRemovedCA"))
		{
			return defaultSiteOneFeatureSwitchDao.getSwitchLongValue(switchKey);
		}
		else
		{
			return defaultSiteOneFeatureSwitchDao.getSwitchValue(switchKey);
		}

	}

	@Override
	public SearchPageData<B2BCustomerModel> getPagedAssignRevokeUsers(final PageableData pageableData, final String userUnitId,
			final String sortCode, final String action, final String vaultToken)
	{
		final SearchPageData<B2BCustomerModel> eWalletCards = defaultSiteOneEwalletDao.getPagedAssignRevokeUsers(pageableData,
				userUnitId, sortCode, action, vaultToken);

		return eWalletCards;
	}

	/**
	 * @return the defaultSiteOneEwalletDao
	 */
	public DefaultSiteOneEwalletDao getDefaultSiteOneEwalletDao()
	{
		return defaultSiteOneEwalletDao;
	}

	/**
	 * @param defaultSiteOneEwalletDao the defaultSiteOneEwalletDao to set
	 */
	public void setDefaultSiteOneEwalletDao(DefaultSiteOneEwalletDao defaultSiteOneEwalletDao)
	{
		this.defaultSiteOneEwalletDao = defaultSiteOneEwalletDao;
	}

	/**
	 * @return the defaultSiteOneFeatureSwitchDao
	 */
	public DefaultSiteOneFeatureSwitchDao getDefaultSiteOneFeatureSwitchDao()
	{
		return defaultSiteOneFeatureSwitchDao;
	}

	/**
	 * @param defaultSiteOneFeatureSwitchDao the defaultSiteOneFeatureSwitchDao to set
	 */
	public void setDefaultSiteOneFeatureSwitchDao(DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao)
	{
		this.defaultSiteOneFeatureSwitchDao = defaultSiteOneFeatureSwitchDao;
	}

}