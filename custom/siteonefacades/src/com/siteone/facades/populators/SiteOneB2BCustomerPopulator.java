/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCostCenterModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.model.B2BUserGroupModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2b.strategies.B2BUserGroupsLookUpStrategy;
import de.hybris.platform.b2bcommercefacades.company.converters.populators.B2BCustomerPopulator;
import de.hybris.platform.b2bcommercefacades.company.data.B2BCostCenterData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.util.SiteOneB2BUnitUtil;



/**
 * @author 1190626
 *
 */
public class SiteOneB2BCustomerPopulator extends B2BCustomerPopulator
{
	private CommonI18NService commonI18NService;
	private Converter<CurrencyModel, CurrencyData> currencyConverter;
	private B2BUnitService<B2BUnitModel, UserModel> b2bUnitService;
	private B2BUserGroupsLookUpStrategy b2BUserGroupsLookUpStrategy;
	private Populator<MediaModel, ImageData> imagePopulator;

	@Override
	public void populate(final CustomerModel source, final CustomerData target) throws ConversionException
	{
		ServicesUtil.validateParameterNotNull(source, "Parameter source cannot be null.");
		ServicesUtil.validateParameterNotNull(target, "Parameter target cannot be null.");
		if (source instanceof B2BCustomerModel)
		{
			final B2BCustomerModel customer = (B2BCustomerModel) source;
			if (customer.getTitle() != null)
			{
				target.setTitleCode(customer.getTitle().getCode());
			}

			target.setUid(customer.getUid());
			target.setName(customer.getName());
			target.setActive(Boolean.TRUE.equals(customer.getActive()));
			target.setCurrency(this.getCurrencyConverter().convert(this.getCommonI18NService().getCurrentCurrency()));
			target.setInvoicePermissions(customer.getInvoicePermissions());
			target.setPartnerProgramPermissions(customer.getPartnerProgramPermissions());
			target.setAccountOverviewForParent(customer.getAccountOverviewForParent());
			target.setAccountOverviewForShipTos(customer.getAccountOverviewForShipTos());
			target.setLangPreference(customer.getLanguagePreference());
			target.setVaultToken(((B2BCustomerModel) source).getVaultToken());
			target.setHomeBranch(customer.getHomeBranch());
			target.setEnableAddModifyDeliveryAddress(customer.getEnableAddModifyDeliveryAddress());
			if (customer.getPayBillOnline() == null)
			{
				target.setPayBillOnline(false);
			}
			else
			{
				target.setPayBillOnline(customer.getPayBillOnline());
			}
			target.setPlaceOrder(customer.getPlaceOrder());
			target.setGuid(customer.getGuid());
			target.setRecentCartIds(customer.getRecentCartIds());
			target.setCurrentCartId(customer.getCurrentCarId());
			this.populateUnit(customer, target);
			this.populateRoles(customer, target);
			this.populateAssignedShipTos(customer, target);
			super.populatePermissionGroups(customer, target);

			if (customer.getOriginalUid() != null)
			{
				target.setDisplayUid(customer.getOriginalUid());
			}

			if (null != source.getContactInfos())
			{
				source.getContactInfos().forEach(info -> {
					if (info instanceof PhoneContactInfoModel)
					{
						final PhoneContactInfoModel phoneInfo = (PhoneContactInfoModel) info;
						if (PhoneContactInfoType.MOBILE.equals(phoneInfo.getType()))
						{
							target.setContactNumber(phoneInfo.getPhoneNumber());
						}
					}
				});
			}
			if (null != customer)
			{
				final boolean isAdminUser = customer.getGroups().stream()
						.anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));

				target.setIsAdmin(isAdminUser);
				if (isAdminUser && BooleanUtils.isTrue(customer.getIsAccountOwner()))
				{
					target.setRoleName("Account Owner");
				}
				else if (isAdminUser && BooleanUtils.isFalse(customer.getIsAccountOwner()))
				{
					target.setRoleName("Online Admin");
				}
				else
				{
					target.setRoleName("Team Member");
				}
			}
			target.setNeedsOrderApproval(BooleanUtils.isTrue(customer.getNeedsOrderApproval()));
		}
	}

	protected void populateUnit(final B2BCustomerModel customer, final CustomerData target)
	{
		final B2BUnitModel parent = (B2BUnitModel) this.getB2bUnitService().getParent(customer);
		if (parent != null)
		{
			final B2BUnitData b2BUnitData = new B2BUnitData();
			b2BUnitData.setUid(parent.getUid());
			b2BUnitData.setName(parent.getLocName());
			b2BUnitData.setActive(Boolean.TRUE.equals(parent.getActive()));
			if (CollectionUtils.isNotEmpty(parent.getCostCenters()))
			{
				final List<B2BCostCenterData> costCenterDataCollection = new ArrayList();
				final Iterator var7 = parent.getCostCenters().iterator();

				while (var7.hasNext())
				{
					final B2BCostCenterModel costCenterModel = (B2BCostCenterModel) var7.next();
					final B2BCostCenterData costCenterData = new B2BCostCenterData();
					costCenterData.setCode(costCenterModel.getCode());
					costCenterData.setName(costCenterModel.getName());
					costCenterDataCollection.add(costCenterData);
				}

				b2BUnitData.setCostCenters(costCenterDataCollection);
			}
			if (null != parent.getReportingOrganization())
			{
				final B2BUnitData reportOrg = new B2BUnitData();
				reportOrg.setUid(parent.getReportingOrganization().getUid());
				reportOrg.setDisplayId(SiteOneB2BUnitUtil.unitIdWithoutDivision(parent.getReportingOrganization().getUid()));
				b2BUnitData.setReportingOrganization(reportOrg);
			}
			if(null != parent.getCustomerLogo())
			{
				final ImageData customerLogo = new ImageData();
				getImagePopulator().populate(parent.getCustomerLogo(), customerLogo);
				b2BUnitData.setCustomerLogo(customerLogo);
			}

			target.setUnit(b2BUnitData);
		}

	}

	/**
	 * @param customer
	 * @param target
	 */
	protected void populateAssignedShipTos(final B2BCustomerModel customer, final CustomerData target)
	{
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

		target.setAssignedShipTos(assignedShipTos);

	}

	@Override
	protected void populateRoles(final B2BCustomerModel source, final CustomerData target)
	{
		final ArrayList roles = new ArrayList();
		final HashSet roleModels = new HashSet(source.getGroups());
		roleModels.removeIf(Predicate.not(PredicateUtils.notPredicate(PredicateUtils.instanceofPredicate(B2BUnitModel.class))));
		roleModels.removeIf(Predicate.not(PredicateUtils.notPredicate(PredicateUtils.instanceofPredicate(B2BUserGroupModel.class))));
		final Iterator arg5 = roleModels.iterator();
		while (arg5.hasNext())
		{
			final PrincipalGroupModel role = (PrincipalGroupModel) arg5.next();

			if (this.getB2BUserGroupsLookUpStrategy().getUserGroups().contains(role.getUid())
					&& role.getUid().contains("b2badmingroup") || role.getUid().contains("b2bcustomergroup"))
			{
				roles.add(role.getUid());
			}
		}
		target.setRoles(roles);
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
	 * @return the currencyConverter
	 */
	@Override
	public Converter<CurrencyModel, CurrencyData> getCurrencyConverter()
	{
		return currencyConverter;
	}

	/**
	 * @param currencyConverter
	 *           the currencyConverter to set
	 */
	@Override
	public void setCurrencyConverter(final Converter<CurrencyModel, CurrencyData> currencyConverter)
	{
		this.currencyConverter = currencyConverter;
	}

	/**
	 * @return the b2bUnitService
	 */
	@Override
	public B2BUnitService<B2BUnitModel, UserModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	/**
	 * @param b2bUnitService
	 *           the b2bUnitService to set
	 */
	@Override
	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, UserModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	/**
	 * @return the b2BUserGroupsLookUpStrategy
	 */
	@Override
	public B2BUserGroupsLookUpStrategy getB2BUserGroupsLookUpStrategy()
	{
		return b2BUserGroupsLookUpStrategy;
	}

	/**
	 * @param b2bUserGroupsLookUpStrategy
	 *           the b2BUserGroupsLookUpStrategy to set
	 */
	@Override
	public void setB2BUserGroupsLookUpStrategy(final B2BUserGroupsLookUpStrategy b2bUserGroupsLookUpStrategy)
	{
		b2BUserGroupsLookUpStrategy = b2bUserGroupsLookUpStrategy;
	}

	public Populator<MediaModel, ImageData> getImagePopulator()
	{
		return imagePopulator;
	}

	public void setImagePopulator(Populator<MediaModel, ImageData> imagePopulator)
	{
		this.imagePopulator = imagePopulator;
	}

}
