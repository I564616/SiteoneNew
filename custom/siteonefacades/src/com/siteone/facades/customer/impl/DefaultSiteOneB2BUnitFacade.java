/**
 *
 */
package com.siteone.facades.customer.impl;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2bcommercefacades.company.impl.DefaultB2BUnitFacade;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.model.OrgUnitModel;
import de.hybris.platform.commerceservices.organization.services.impl.DefaultOrgUnitHierarchyService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.core.requestaccount.service.SiteoneRequestAccountService;
import com.siteone.facade.customer.info.MyAccountUserInfo;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.SiteOneB2BUnitBasicPopulator;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.TalonOne.info.LoyaltyProgramStatusInfo;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;
import com.siteone.integration.services.ue.SiteOneCustInfoWebService;
import com.siteone.integration.services.ue.SiteOnePurchasedProductWebService;
import com.siteone.integration.services.ue.SiteOneTalonOneLoyaltyEnrollmentStatusWebService;





public class DefaultSiteOneB2BUnitFacade extends DefaultB2BUnitFacade implements SiteOneB2BUnitFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneB2BUnitFacade.class);
	private static final String HOMEOWNER_TRADECLASSNAME = "Homeowner/Retail";
	private static final String HOMEOWNER_TRADECLASS = "409033";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String RESELLER_TRADECLASS = "204029";
	private static final String GOVERNMENT_TRADECLASS = "26000";
	private static final String RETAIL_TRADECLASS = "409033";
	private static final String SEGMENT_LEVEL_SHIPPING_ENABLED = "segmentLevelShippingEnabled";


	@Resource(name = "siteOneCustInfoDataConverter")
	private Converter<SiteOneCustInfoResponeData, SiteOneCustInfoData> siteOneCustInfoDataConverter;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "siteOneB2BUnitBasicPopulator")
	private SiteOneB2BUnitBasicPopulator siteOneB2BUnitBasicPopulator;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "defaultSiteOneB2BUnitService")
	private SiteOneB2BUnitService defaultSiteOneB2BUnitService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteOneCustInfoWebService")
	private SiteOneCustInfoWebService siteOneCustInfoWebService;

	@Resource(name = "siteonecustomerConverter")
	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;

	@Resource(name = "b2bUnitReverseConverter")
	private Converter<B2BUnitData, B2BUnitModel> siteoneb2BUnitReverseConverter;


	@Resource(name = "siteOnePurchasedProductWebService")
	private SiteOnePurchasedProductWebService siteOnePurchasedProductWebService;


	//	@Resource(name = "siteonePurchProdConverter")
	//	private Converter<PurchasedProduct, ProductData> siteonePurchProdConverter;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteoneRequestAccountService")
	private SiteoneRequestAccountService siteoneRequestAccountService;

	@Resource(name = "i18NService")
	private I18NService i18NService;

	@Resource(name = "defaultOrgUnitHierarchyService")
	private DefaultOrgUnitHierarchyService defaultOrgUnitHierarchyService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneTalonOneLoyaltyEnrollmentStatusWebService")
	private SiteOneTalonOneLoyaltyEnrollmentStatusWebService siteOneTalonOneLoyaltyEnrollmentStatusWebService;

	@Resource(name = "siteOneB2BUnitWithAdministratorConvertor")
	private Converter<B2BUnitModel, B2BUnitData> siteOneB2BUnitWithAdministratorConvertor;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	public SessionService getSessionService()
	{
		return sessionService;
	}

	@Override
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
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

	private B2BUnitService<B2BUnitModel, B2BCustomerModel> unitB2BService;


	/**
	 * @return the unitB2BService
	 */
	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getUnitB2BService()
	{
		return unitB2BService;
	}

	/**
	 * @param unitB2BService
	 *           the unitB2BService to set
	 */
	public void setUnitB2BService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> unitB2BService)
	{
		this.unitB2BService = unitB2BService;
	}

	/**
	 * @return the i18NService
	 */
	public I18NService getI18NService()
	{
		return i18NService;
	}

	/**
	 * @param i18nService
	 *           the i18NService to set
	 */
	public void setI18NService(final I18NService i18nService)
	{
		i18NService = i18nService;
	}


	/**
	 * @return the defaultSiteOneB2BUnitService
	 */
	public SiteOneB2BUnitService getDefaultSiteOneB2BUnitService()
	{
		return defaultSiteOneB2BUnitService;
	}

	/**
	 * @param defaultSiteOneB2BUnitService
	 *           the defaultSiteOneB2BUnitService to set
	 */
	public void setDefaultSiteOneB2BUnitService(final SiteOneB2BUnitService defaultSiteOneB2BUnitService)
	{
		this.defaultSiteOneB2BUnitService = defaultSiteOneB2BUnitService;
	}

	@Override
	public List<B2BUnitData> getShipTosForUnit(final String uid)
	{
		final List<B2BUnitData> childB2BUnitDataList = new ArrayList<>();
		final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		final Collection<B2BUnitModel> childB2BUnits = b2bUnitService.getBranch(b2bUnitModel);
		if (CollectionUtils.isNotEmpty(childB2BUnits))
		{
			for (final B2BUnitModel childB2BUnit : childB2BUnits)
			{
				final B2BUnitData childB2BUnitData = new B2BUnitData();
				if (childB2BUnit.getActive().booleanValue())
				{
					siteOneB2BUnitBasicPopulator.populate(childB2BUnit, childB2BUnitData);
					if (childB2BUnit.getUid().equalsIgnoreCase(childB2BUnit.getReportingOrganization().getUid()))
					{
						childB2BUnitDataList.add(0, childB2BUnitData);
					}
					else
					{
						childB2BUnitDataList.add(childB2BUnitData);
					}
					//sub-account list
					/*
					 * final Collection<B2BUnitModel> subAccountB2BUnits = b2bUnitService.getBranch(childB2BUnit); if
					 * (CollectionUtils.isNotEmpty(subAccountB2BUnits)) { for (final B2BUnitModel subAccountB2BUnit :
					 * subAccountB2BUnits) { final B2BUnitData subAccountB2BUnitData = new B2BUnitData(); if
					 * (subAccountB2BUnit.getActive().booleanValue()) {
					 * siteOneB2BUnitBasicPopulator.populate(subAccountB2BUnit, subAccountB2BUnitData);
					 *
					 * childB2BUnitDataList.add(subAccountB2BUnitData); } } }
					 */
				}
			}
		}
		return childB2BUnitDataList;
	}

	/**
	 * Get unit's based the customer role
	 */
	@Override
	public List<B2BUnitData> getShipTosForUnitBasedOnCustomer(final String uid)
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		Set<PrincipalGroupModel> groups = null;
		final List<B2BUnitData> childB2BUnitDataList = new ArrayList<>();
		final Collection<String> assignedShipTos = this.getModifiedAssignedShipTos();
		final List<B2BUnitData> dataList = new ArrayList<>();

		if (null != customerModel)
		{
			groups = customerModel.getGroups();
			childB2BUnitDataList.addAll(this.getShipTosForUnit(uid));

			if (CollectionUtils.isNotEmpty(assignedShipTos))
			{
				for (final B2BUnitData shipTo : childB2BUnitDataList)
				{
					for (final String assignedShipTo : assignedShipTos)
					{
						if (shipTo.getUid().equalsIgnoreCase(assignedShipTo))
						{
							dataList.addAll(this.getShipTosForUnit(shipTo.getUid()));
						}
					}
				}
				if (!dataList.isEmpty())
				{
					return dataList;
				}
			}
			//final boolean isAdmin = groups.stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));

			/*
			 * if (isAdmin) {
			 */

			/*
			 * } else { final List<B2BUnitModel> shipTos = ((SiteOneB2BUnitService)
			 * b2bUnitService).getShipTosForCurrentCustomer();
			 *
			 * if (CollectionUtils.isNotEmpty(shipTos)) { for (final B2BUnitModel childB2BUnit : shipTos) { final
			 * B2BUnitData childB2BUnitData = new B2BUnitData(); siteOneB2BUnitBasicPopulator.populate(childB2BUnit,
			 * childB2BUnitData); childB2BUnitDataList.add(childB2BUnitData); } } }
			 */
		}

		return childB2BUnitDataList;
	}

	@Override
	public B2BUnitData getDefaultUnitForCurrentCustomer()
	{
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (null != unit)
		{
			return getB2BUnitConverter().convert(unit);
		}
		return null;
	}

	@Override
	public boolean setIsSegmentLevelShippingEnabled(final String tradeClass)
	{
		String customerClass = "";
		if (tradeClass != null && !tradeClass.equalsIgnoreCase("guest"))
		{
			if (tradeClass.equalsIgnoreCase(RETAIL_TRADECLASS))
			{
				customerClass = "homeowner";
			}
			else if (!tradeClass.equalsIgnoreCase(RETAIL_TRADECLASS))
			{
				customerClass = "contractor";
			}
		}
		else if (tradeClass != null && tradeClass.equalsIgnoreCase("guest"))
		{
			customerClass = tradeClass;
		}
		else
		{
			sessionService.setAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED, true);
			return true;
		}

		if (!StringUtils.isEmpty(customerClass)
				&& siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled") != null)
		{
			boolean segmentLevelShippingEnabled = false;
			final String isSegmentLevelShipping = siteOneFeatureSwitchCacheService.getValueForSwitch("SegmentLevelShippingEnabled");
			if (isSegmentLevelShipping != null)
			{
				final String segmentLevelShipping[] = isSegmentLevelShipping.split(",");
				for (final String trade : segmentLevelShipping)
				{
					if (trade.equalsIgnoreCase(customerClass))
					{
						segmentLevelShippingEnabled = true;
						break;
					}
				}
			}
			sessionService.setAttribute(SEGMENT_LEVEL_SHIPPING_ENABLED, segmentLevelShippingEnabled);
			return segmentLevelShippingEnabled;
		}
		return true;
	}


	@Override
	public B2BUnitData getDefaultUnitWithAdministratorInfo()
	{
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (null != unit)
		{
			return siteOneB2BUnitWithAdministratorConvertor.convert(unit);
		}
		return null;
	}

	@Override
	public B2BUnitData getParentUnitForCustomer()
	{
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();

		if (null != parent)
		{
			return getB2BUnitConverter().convert(parent);
		}

		return null;
	}

	@Override
	public List<B2BUnitData> getShipTosForCustomer(final String uid)
	{
		final List<B2BUnitData> childB2BUnitDataList = new ArrayList<>();

		final List<B2BUnitModel> shipTos = ((SiteOneB2BUnitService) b2bUnitService).getShipTosForCustomer(uid);

		if (CollectionUtils.isNotEmpty(shipTos))
		{
			for (final B2BUnitModel childB2BUnit : shipTos)
			{
				final B2BUnitData childB2BUnitData = new B2BUnitData();
				siteOneB2BUnitBasicPopulator.populate(childB2BUnit, childB2BUnitData);
				childB2BUnitDataList.add(childB2BUnitData);

			}
		}

		return childB2BUnitDataList;
	}

	@Override
	public Set<CustomerData> getAdminUserForUnit(final String unitId)
	{
		final Set<CustomerData> customerData = new HashSet<>();
		final B2BUnitModel unit = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		if (null != unit)
		{
			final Set<B2BCustomerModel> adminUser = ((SiteOneB2BUnitService) b2bUnitService).getAdminUserForUnit(unit);

			for (final B2BCustomerModel b2bAdmin : adminUser)
			{
				final CustomerData customer = getB2BUserConverter().convert(b2bAdmin);
				customerData.add(customer);
			}
		}

		return customerData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.customer.SiteOneB2BUnitFacade#getCustomersForUnit(java.lang.String)
	 */
	@Override
	public Set<CustomerData> getCustomersForUnit(final String unitId)
	{
		final Set<CustomerData> customerData = new HashSet<>();
		final B2BUnitModel unit = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
		if (null != unit)
		{
			final Set<B2BCustomerModel> b2bCustomers = b2bUnitService.getB2BCustomers(unit);
			for (final B2BCustomerModel customer : b2bCustomers)
			{
				if (customer.getActive())
				{
					final CustomerData customers = siteonecustomerConverter.convert(customer);
					customerData.add(customers);
				}

			}
		}

		return customerData;
	}

	@Override
	public List<CustomerData> getUsersforCustomerUnit()
	{
		final B2BUnitModel unitModel = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		return getB2BUserConverter().convertAll(b2bUnitService.getB2BCustomers(unitModel));
	}

	@Override
	public List<CustomerData> getUsersforCustomerUnit(final String searchTerm, final String searchType)
	{
		final B2BUnitModel unitModel = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		return getB2BUserConverter().convertAll(defaultSiteOneB2BUnitService.getB2BCustomers(unitModel, searchTerm, searchType));
	}

	@Override
	public SiteOneCustInfoData getAccountInformation(final String uid) throws ServiceUnavailableException
	{

		final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		final SiteOneCustInfoResponeData data = siteOneCustInfoWebService.getCustInfo(b2BUnitModel,
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		if (data == null)
		{
			throw new ServiceUnavailableException("404");
		}

		final SiteOneCustInfoData siteOneCustInfoData = siteOneCustInfoDataConverter.convert(data);

		return siteOneCustInfoData;
	}

	@Override
	public SiteOneCustInfoData getLoyaltyInformation(final String uid) throws ServiceUnavailableException
	{

		final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		SiteOneCustInfoResponeData data = null;
		if (null != b2BUnitModel)
		{
			data = siteOneCustInfoWebService.getPartnerPointsInfo(b2BUnitModel,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		}
		if (data == null)
		{
			throw new ServiceUnavailableException("404");
		}

		final SiteOneCustInfoData siteOneCustInfoData = siteOneCustInfoDataConverter.convert(data);

		return siteOneCustInfoData;
	}

	//	public List<ProductData> getPurchasedProducts()
	//	{
	//
	//		final B2BUnitData b2BUnitData = storeSessionFacade.getSessionShipTo();
	//		final B2BUnitModel unitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(b2BUnitData.getUid());
	//		final List<PurchasedProduct> purchasedProducts = siteOnePurchasedProductWebService.getPurchasedProducts(unitModel.getGuid(),
	//				!b2BUnitData.getIsOrderingAccount().booleanValue());
	//		return siteonePurchProdConverter.convertAll(purchasedProducts);
	//	}


	@Override
	public B2BUnitModel updateB2BUnit(final SiteoneRequestAccountModel siteoneRequestAccountModel,
			final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE)
	{
		final B2BUnitModel unitModel = (B2BUnitModel) this.getModelService().create(B2BUnitModel.class);
		final Locale currentLocale = getI18NService().getCurrentLocale();
		unitModel.setActive(Boolean.TRUE);
		if (!isAccountExistsInUE && "Contractor".equalsIgnoreCase(siteoneRequestAccountModel.getTypeOfCustomer())
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
						.getIdentityResolutionResponseCode() != null
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse().getIdentityResolutionResponseCode()
						.intValue() == 1)
		{
			unitModel.setLocName(siteoneRequestAccountModel.getCompanyName(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getCompanyName());
			unitModel.setTradeClass(siteoneRequestAccountModel.getContrPrimaryBusiness());
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getContrPrimaryChildBusiness());
			unitModel.setTradeClassName(siteoneRequestAccountModel.getTradeClassName());
			unitModel.setSubTradeClassName(siteoneRequestAccountModel.getSubTradeClassName());
			unitModel.setDunsScore(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getDuns());
			unitModel.setConfidenceCode(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getConfidenceCode());
			unitModel.setMatchGrade(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getMatchGrade());
			unitModel.setYearsInBusiness(siteoneRequestAccountModel.getContrYearsInBusiness());
			unitModel.setNumberOfEmployees(siteoneRequestAccountModel.getContrEmpCount());
		}
		else if (isAccountExistsInUE && "Contractor".equalsIgnoreCase(siteoneRequestAccountModel.getTypeOfCustomer()))
		{
			unitModel.setLocName(siteoneRequestAccountModel.getCompanyName(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getCompanyName());
			unitModel.setTradeClass(siteoneRequestAccountModel.getContrPrimaryBusiness());
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getContrPrimaryChildBusiness());
			unitModel.setTradeClassName(siteoneRequestAccountModel.getTradeClassName());
			unitModel.setSubTradeClassName(siteoneRequestAccountModel.getSubTradeClassName());
			unitModel.setYearsInBusiness(siteoneRequestAccountModel.getContrYearsInBusiness());
			unitModel.setNumberOfEmployees(siteoneRequestAccountModel.getContrEmpCount());
		}
		else
		{
			unitModel.setLocName(siteoneRequestAccountModel.getTypeOfCustomer(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getTypeOfCustomer());
			unitModel.setTradeClass(HOMEOWNER_TRADECLASS);
			unitModel.setTradeClassName(HOMEOWNER_TRADECLASSNAME);
		}
		String b2bUnitSuffix = "_US";
		if (null != siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getDivisionId())
		{
			if (siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getDivisionId().equalsIgnoreCase("1"))
			{
				b2bUnitSuffix = "_US";
			}
			else if (siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getDivisionId().equalsIgnoreCase("2"))
			{
				b2bUnitSuffix = "_CA";
			}
		}
		unitModel.setUid(siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber() + b2bUnitSuffix);
		if (siteoneRequestAccountModel.getState() != null)
		{
			unitModel.setDivision(defaultSiteOneB2BUnitService
					.getDivisionForUnit(StringUtils.substringBeforeLast(siteoneRequestAccountModel.getState().trim(), "-")));
		}
		unitModel.setGuid(siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getCustTreeNodeID().toUpperCase());
		unitModel.setPriceClassCode(siteOneWsCreateCustomerResponseData.getResult().getPriceClass().getPriceClassCode());
		unitModel.setNurseryClassCode(siteOneWsCreateCustomerResponseData.getResult().getPriceClass().getNurseryClassCode());
		unitModel.setIsOrderingAccount(Boolean.TRUE);
		unitModel.setIsBillingAccount(Boolean.TRUE);
		unitModel.setCreditCode(siteoneRequestAccountModel.getCreditCode());
		unitModel.setCreditTermCode(siteoneRequestAccountModel.getCreditTerms());
		unitModel.setAccountGroupCode(siteoneRequestAccountModel.getAcctGroupCode());
		if (StringUtils.isNotBlank(siteoneRequestAccountModel.getPhoneNumber()))
		{
			unitModel.setPhoneNumber(siteoneRequestAccountModel.getPhoneNumber());
		}
		if (!StringUtils.isNotBlank(unitModel.getTradeClass()))
		{
			unitModel.setTradeClass(siteoneRequestAccountModel.getTradeClass());
		}
		if (!StringUtils.isNotBlank(unitModel.getSubTradeClass()))
		{
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getSubTradeClass());
		}
		final AddressModel address = createBillingAddress(siteoneRequestAccountModel, unitModel,
				siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getBillingAddressID().toUpperCase());
		try
		{
			if (null != address)
			{
				this.getModelService().save(address);
			}

			unitModel.setBillingAddress(address);
			getModelService().save(unitModel);
			if (null != siteOneWsCreateCustomerResponseData.getResult()
					&& null != siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo()
					&& null != siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber())
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(
						siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber() + b2bUnitSuffix);
				unitModel.setReportingOrganization(b2BUnitModel);
				final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(unitModel.getGroups());
				groups.add(b2BUnitModel);
				unitModel.setGroups(groups);
				getModelService().save(unitModel);
				final OrgUnitModel orgUnitModel = defaultSiteOneB2BUnitService.getDivisionForUnit(
						siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber() + b2bUnitSuffix);
				if (orgUnitModel != null)
				{
					defaultOrgUnitHierarchyService.saveChangesAndUpdateUnitPath(orgUnitModel);
				}
			}
			else if (null != siteOneWsCreateCustomerResponseData.getResult()
					&& null != siteOneWsCreateCustomerResponseData.getResult().getAccountInfo()
					&& null != siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber())
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(
						siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber() + b2bUnitSuffix);
				b2BUnitModel.setReportingOrganization(b2BUnitModel);
				getModelService().save(b2BUnitModel);
				final OrgUnitModel orgUnitModel = defaultSiteOneB2BUnitService.getDivisionForUnit(
						siteOneWsCreateCustomerResponseData.getResult().getAccountInfo().getAccountNumber() + b2bUnitSuffix);
				if (orgUnitModel != null)
				{
					defaultOrgUnitHierarchyService.saveChangesAndUpdateUnitPath(orgUnitModel);
				}
			}
		}
		catch (final ModelSavingException modelSavingException)
		{
			LOG.error(modelSavingException.getMessage(), modelSavingException);
		}
		return unitModel;

	}

	public AddressModel createBillingAddress(final SiteoneRequestAccountModel siteoneRequestAccountModel,
			final B2BUnitModel b2bUnitModel, final String guid)
	{
		final AddressModel addressModel = this.getModelService().create(AddressModel.class);
		addressModel.setLine1(siteoneRequestAccountModel.getAddressLine1());
		if (!StringUtils.isEmpty(siteoneRequestAccountModel.getAddressLine2()))
		{
			addressModel.setLine2(siteoneRequestAccountModel.getAddressLine2());
		}
		addressModel.setTown(siteoneRequestAccountModel.getCity());
		addressModel.setPostalcode(siteoneRequestAccountModel.getZipcode());
		if (!StringUtils.isEmpty(siteoneRequestAccountModel.getState()))
		{
			final String countryCode = StringUtils.substringBeforeLast(siteoneRequestAccountModel.getState().trim(), "-");
			final CountryModel country = siteoneRequestAccountService.getCountryByCountryCode(countryCode);
			addressModel.setCountry(country);
			final RegionModel region = siteoneRequestAccountService.getRegionByIsoCode(country,
					siteoneRequestAccountModel.getState());
			addressModel.setRegion(region);
		}
		if (!StringUtils.isEmpty(siteoneRequestAccountModel.getPhoneNumber())
				&& siteoneRequestAccountModel.getPhoneNumber().length() == 12)
		{
			addressModel.setPhone1(siteoneRequestAccountModel.getPhoneNumber());
		}
		addressModel.setBillingAddress(Boolean.TRUE);
		addressModel.setOwner(b2bUnitModel);
		addressModel.setGuid(guid);
		return addressModel;
	}

	@Override
	public boolean getLoyaltyProgramInfoStatus()
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final LoyaltyProgramStatusInfo response = siteOneTalonOneLoyaltyEnrollmentStatusWebService.getLoyaltyProgramInfoStatus(
				customerModel.getDefaultB2BUnit().getUid(),
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

		if (null != response)
		{
			customerModel.setPartnerProgramPermissions(null != response.getResult()
					&& null != response.getResult().getCustomerLoyaltyProgramEnrollmentDetail()
					&& null != response.getResult().getCustomerLoyaltyProgramEnrollmentDetail().get(0)
					&& null != response.getResult().getCustomerLoyaltyProgramEnrollmentDetail().get(0).getStatus()
					&& (response.getResult().getCustomerLoyaltyProgramEnrollmentDetail().get(0).getStatus().equalsIgnoreCase("INC")
							|| response.getResult().getCustomerLoyaltyProgramEnrollmentDetail().get(0).getStatus()
									.equalsIgnoreCase("CMP")));
			getModelService().save(customerModel);

		}

		return customerModel.getPartnerProgramPermissions();

	}

	@Override
	public MyAccountUserInfo getUserAccountInfo()
	{
		boolean isPartnerProgramRetail = false;
		final MyAccountUserInfo myAccountUserInfo = new MyAccountUserInfo();
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		if (null != customer)
		{
			myAccountUserInfo
					.setIsAdminUser(customer.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")));
			if (customer.getDefaultB2BUnit().getTradeClass() != null
					&& (customer.getDefaultB2BUnit().getTradeClass().equals(RESELLER_TRADECLASS)
							|| customer.getDefaultB2BUnit().getTradeClass().equals(GOVERNMENT_TRADECLASS)
							|| customer.getDefaultB2BUnit().getTradeClass().equals(RETAIL_TRADECLASS)))
			{
				isPartnerProgramRetail = true;
			}
			else
			{
				isPartnerProgramRetail = false;
			}


			myAccountUserInfo.setIsPartnersProgramEnrolled(getLoyaltyProgramInfoStatus());
			myAccountUserInfo.setIsPartnersProgramRetail(isPartnerProgramRetail);
		}


		return myAccountUserInfo;
	}

	@Override
	public void uploadImage(final String[] filename, final InputStream inputStream)
	{
		((SiteOneB2BUnitService) b2bUnitService).uploadImage(filename, inputStream);
	}

	@Override
	public boolean deleteImage()
	{
		return ((SiteOneB2BUnitService) b2bUnitService).deleteImage();
	}

	@Override
	public Collection<String> getModifiedAssignedShipTos()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final List<String> assignedShipTos = new ArrayList<>();

		customer.getGroups().stream().forEach(group -> {
			if (group instanceof B2BUnitModel)
			{
				final B2BUnitModel shipto = (B2BUnitModel) group;
				if (null != shipto.getReportingOrganization()
						&& !shipto.getReportingOrganization().getUid().equalsIgnoreCase(shipto.getUid()))
				{
					assignedShipTos.add(shipto.getUid());
				}
			}
		});

		return assignedShipTos;
	}

	@Override
	public List<B2BUnitData> getModifiedShipTos(final B2BUnitData child, final Collection<String> assignedShipTos)
	{

		if (CollectionUtils.isNotEmpty(assignedShipTos))
		{
			for (final String assignedShipTo : assignedShipTos)
			{
				if (child.getUid().equalsIgnoreCase(assignedShipTo))
				{
					final List<B2BUnitData> currentShipTos = this.getShipTosForUnit(assignedShipTo);

					if (CollectionUtils.isNotEmpty(currentShipTos))
					{
						return currentShipTos;
					}
				}
			}
		}
		return Collections.emptyList();

	}

	@Override
	public boolean checkIsUnitForApproveOrders(final String uid)
	{
		return siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ApproveOrders", uid);
	}

	@Override
	public boolean checkIsParentUnitEnabledForAgroAI(final String uid)
	{
		final B2BUnitModel parentB2BUnit = defaultSiteOneB2BUnitService.getParentForUnit(uid);
		return siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI",
				parentB2BUnit.getUid());
	}

	@Override
	public void assignToGroup(final B2BCustomerModel admin, final UserGroupModel userGroup)
	{
		final Set<PrincipalGroupModel> groupModelSet = admin.getGroups();
		if (!groupModelSet.contains(userGroup))
		{
			final Set<PrincipalGroupModel> groups = new HashSet<>(groupModelSet);
			groups.add(userGroup);
			admin.setGroups(groups);
			getModelService().save(admin);
		}
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

	@Override
	public void setApproverGroupsForAdmin()
	{
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();

		if (checkIsUnitForApproveOrders(parent.getUid()))
		{
			final List<B2BCustomerModel> b2bAdminGroupUsers = new ArrayList(
					unitB2BService.getUsersOfUserGroup(parent, B2BConstants.B2BADMINGROUP, true));

			if (CollectionUtils.isNotEmpty(b2bAdminGroupUsers))
			{
				for (final B2BCustomerModel b2bAdminGroupUser : b2bAdminGroupUsers)
				{
					assignToGroup(b2bAdminGroupUser, userService.getUserGroupForUID(B2BConstants.B2BAPPROVERGROUP));
				}
			}

		}
	}

	@Override
	public B2BUnitModel updateParentB2BUnit(final SiteoneRequestAccountModel siteoneRequestAccountModel,
			final SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE)
	{

		final B2BUnitModel unitModel = (B2BUnitModel) this.getModelService().create(B2BUnitModel.class);
		final Locale currentLocale = getI18NService().getCurrentLocale();
		unitModel.setActive(Boolean.TRUE);
		if (!isAccountExistsInUE && "Contractor".equalsIgnoreCase(siteoneRequestAccountModel.getTypeOfCustomer())
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse() != null
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
						.getIdentityResolutionResponseCode() != null
				&& siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
						.getIdentityResolutionResponseCode() == 1)
		{
			unitModel.setLocName(siteoneRequestAccountModel.getCompanyName(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getCompanyName());
			unitModel.setTradeClass(siteoneRequestAccountModel.getContrPrimaryBusiness());
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getContrPrimaryChildBusiness());
			unitModel.setTradeClassName(siteoneRequestAccountModel.getTradeClassName());
			unitModel.setSubTradeClassName(siteoneRequestAccountModel.getSubTradeClassName());
			unitModel.setDunsScore(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getDuns());
			unitModel.setConfidenceCode(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getConfidenceCode());
			unitModel.setMatchGrade(siteOneWsCreateCustomerResponseData.getResult().getIdentityResolutionResponse()
					.getMatchCandidates().get(0).getMatchGrade());
		}
		else if (isAccountExistsInUE && "Contractor".equalsIgnoreCase(siteoneRequestAccountModel.getTypeOfCustomer()))
		{
			unitModel.setLocName(siteoneRequestAccountModel.getCompanyName(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getCompanyName());
			unitModel.setTradeClass(siteoneRequestAccountModel.getContrPrimaryBusiness());
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getContrPrimaryChildBusiness());
			unitModel.setTradeClassName(siteoneRequestAccountModel.getTradeClassName());
			unitModel.setSubTradeClassName(siteoneRequestAccountModel.getSubTradeClassName());
		}
		else
		{
			unitModel.setLocName(siteoneRequestAccountModel.getTypeOfCustomer(), currentLocale);
			unitModel.setName(siteoneRequestAccountModel.getTypeOfCustomer());
			unitModel.setTradeClass(HOMEOWNER_TRADECLASS);
			unitModel.setTradeClassName(HOMEOWNER_TRADECLASSNAME);
		}
		String b2bUnitSuffix = "_US";
		if (null != siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getDivisionId())
		{
			if (siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getDivisionId().equalsIgnoreCase("1"))
			{
				b2bUnitSuffix = "_US";
			}
			else if (siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getDivisionId().equalsIgnoreCase("2"))
			{
				b2bUnitSuffix = "_CA";
			}
		}
		unitModel.setUid(siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber() + b2bUnitSuffix);
		if (siteoneRequestAccountModel.getState() != null)
		{
			unitModel.setDivision(defaultSiteOneB2BUnitService
					.getDivisionForUnit(StringUtils.substringBeforeLast(siteoneRequestAccountModel.getState().trim(), "-")));
		}
		unitModel.setGuid(siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getCustTreeNodeID().toUpperCase());
		unitModel.setPriceClassCode(siteOneWsCreateCustomerResponseData.getResult().getPriceClass().getPriceClassCode());
		unitModel.setNurseryClassCode(siteOneWsCreateCustomerResponseData.getResult().getPriceClass().getNurseryClassCode());
		unitModel.setIsOrderingAccount(Boolean.TRUE);
		unitModel.setIsBillingAccount(Boolean.TRUE);
		unitModel.setCreditCode(siteoneRequestAccountModel.getCreditCode());
		unitModel.setCreditTermCode(siteoneRequestAccountModel.getCreditTerms());
		unitModel.setAccountGroupCode(siteoneRequestAccountModel.getAcctGroupCode());
		if (!StringUtils.isNotBlank(unitModel.getTradeClass()))
		{
			unitModel.setTradeClass(siteoneRequestAccountModel.getTradeClass());
		}
		if (!StringUtils.isNotBlank(unitModel.getSubTradeClass()))
		{
			unitModel.setSubTradeClass(siteoneRequestAccountModel.getSubTradeClass());
		}
		final AddressModel address = createBillingAddress(siteoneRequestAccountModel, unitModel,
				siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getBillingAddressID().toUpperCase());
		try
		{
			if (null != address)
			{
				this.getModelService().save(address);
			}

			unitModel.setBillingAddress(address);
			getModelService().save(unitModel);
			if (null != siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber())
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber() + b2bUnitSuffix);
				b2BUnitModel.setReportingOrganization(b2BUnitModel);
				getModelService().save(b2BUnitModel);
				final OrgUnitModel orgUnitModel = defaultSiteOneB2BUnitService.getDivisionForUnit(siteOneWsCreateCustomerResponseData.getResult().getParentAccountInfo().getAccountNumber() + b2bUnitSuffix);
				if (orgUnitModel != null)
				{
					defaultOrgUnitHierarchyService.saveChangesAndUpdateUnitPath(orgUnitModel);
				}
			}
		}
		catch (final ModelSavingException modelSavingException)
		{
			LOG.error(modelSavingException.getMessage(), modelSavingException);
		}
		return unitModel;
	}

	@Override
	public PointOfServiceData getHomeBranchForUnit(final String unitId)
	{
		if (StringUtils.isNotEmpty(unitId))
		{
			final B2BUnitModel unitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(unitId);
			return (unitModel.getHomeBranch() != null ? storeFinderFacade.getStoreForId(unitModel.getHomeBranch()) : null);
		}
		return null;
	}

	public String setCustomerNoWithDivision(final String customerNumber)
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			return customerNumber.concat("_US");
		}
		else
		{
			return customerNumber.concat("_CA");
		}
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

}
