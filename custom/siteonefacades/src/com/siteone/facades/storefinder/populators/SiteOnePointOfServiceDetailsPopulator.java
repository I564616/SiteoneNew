package com.siteone.facades.storefinder.populators;

import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.commercefacades.storelocator.converters.populator.PointOfServicePopulator;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.storefinder.helpers.DistanceHelper;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.customer.SiteOneCustomerAccountService;



/**
 * @author 532681
 */
public class SiteOnePointOfServiceDetailsPopulator extends PointOfServicePopulator
{

	private CustomerLocationService customerLocationService;
	private DistanceHelper distanceHelper;
	private CustomerAccountService customerAccountService;
	private ConfigurationService configurationService;

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target) throws ConversionException
	{
		super.populate(source, target);
		target.setStoreNotes(source.getStoreNotes());
		target.setSpecialText(source.getSpecialText());
		target.setPickupfullfillment(source.getPickupfullfillment());
		target.setDeliveryfullfillment(source.getDeliveryfullfillment());
		target.setShippingfullfillment(source.getShippingfullfillment());
		target.setIsPreferredStore(((SiteOneCustomerAccountService) getCustomerAccountService()).isPreferredStore(source));
		target.setIsInMyStore(((SiteOneCustomerAccountService) getCustomerAccountService()).isInMyStore(source));
		target.setStoreId(source.getStoreId());
		target.setTitle(source.getTitle());
		target.setNurseryOfferURL(source.getNurseryOfferURL());
		target.setExcludeBranches(source.getExcludeBranches());
		target.setEnableOnlineFulfillment(source.getEnableOnlineFulfillment() != null ? source.getEnableOnlineFulfillment() : Boolean.TRUE);
		if(source.getStoreSpecialities()!=null && !CollectionUtils.isEmpty(source.getStoreSpecialities()))
		{
			target.setStoreSpecialities(source.getStoreSpecialities());
		}
		if (getCustomerLocationService().getUserLocation() != null)
		{
			target.setDistanceKm(Double.valueOf(getCustomerLocationService()
					.calculateDistance(getCustomerLocationService().getUserLocation().getPoint(), source)));
			calculateFormattedDistance(source, target);
		}

		target.setLongDescription(source.getLongDescription());
		target.setSupportProductScanner(source.getSupportProductScanner());
		final Date currentDate = new Date();
		if (null != source.getLicenseEndDate() && currentDate.before(source.getLicenseEndDate()))
		{
			target.setIsLicensed(Boolean.TRUE);
		}
		else
		{
			target.setIsLicensed(Boolean.FALSE);
		}
		if (CollectionUtils.isNotEmpty(source.getShippingHubBranches()))
		{
			final List<String> hubStores = source.getShippingHubBranches().stream().map(hubStore -> hubStore.getStoreId())
					.collect(Collectors.toList());
			target.setHubStores(CollectionUtils.isNotEmpty(hubStores) ? hubStores : Collections.EMPTY_LIST);
		}
		target.setAcquisitionOrCoBrandedBranch(source.getAcquisitionOrCoBrandedBranch());
	}

	protected void calculateFormattedDistance(final PointOfServiceModel pointOfServiceModel,
			final PointOfServiceData pointOfServiceData)
	{
		final String formattedDistance = getDistanceHelper().getDistanceStringForStore(pointOfServiceModel.getBaseStore(),
				pointOfServiceData.getDistanceKm().doubleValue());
		pointOfServiceData.setFormattedDistance(formattedDistance.toLowerCase());
	}


	/**
	 * @return the customerLocationService
	 */
	public CustomerLocationService getCustomerLocationService()
	{
		return customerLocationService;
	}

	/**
	 * @param customerLocationService
	 *           the customerLocationService to set
	 */
	public void setCustomerLocationService(final CustomerLocationService customerLocationService)
	{
		this.customerLocationService = customerLocationService;
	}

	/**
	 * @return the distanceHelper
	 */
	public DistanceHelper getDistanceHelper()
	{
		return distanceHelper;
	}

	/**
	 * @param distanceHelper
	 *           the distanceHelper to set
	 */
	public void setDistanceHelper(final DistanceHelper distanceHelper)
	{
		this.distanceHelper = distanceHelper;
	}


	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}


	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService
	 *           the configurationService to set
	 */
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

}
