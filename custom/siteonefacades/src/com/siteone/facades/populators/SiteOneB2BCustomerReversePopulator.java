/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.converters.populators.B2BCustomerReversePopulator;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;


/**
 * @author 1190626
 *
 */
public class SiteOneB2BCustomerReversePopulator extends B2BCustomerReversePopulator
{

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	private CommonI18NService commonI18NService;


	@Override
	public void populate(final CustomerData source, final B2BCustomerModel target) throws ConversionException
	{
		super.populate(source, target);
		target.setInvoicePermissions(source.getInvoicePermissions());
		target.setPartnerProgramPermissions(source.getPartnerProgramPermissions());
		target.setAccountOverviewForParent(source.getAccountOverviewForParent());
		target.setAccountOverviewForShipTos(source.getAccountOverviewForShipTos());
		target.setPayBillOnline(source.getPayBillOnline());
		target.setPlaceOrder(source.getPlaceOrder());

		if (null != source.getNeedsOrderApproval())
		{
			target.setNeedsOrderApproval(source.getNeedsOrderApproval());
		}

		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setPasswordEncoding(Config.getString("customer.password.encoding", "okta"));
		target.setEnableAddModifyDeliveryAddress(source.getEnableAddModifyDeliveryAddress());
		if (StringUtils.isNotBlank(source.getHomeBranch()))
		{
			target.setHomeBranch(source.getHomeBranch());
		}
		//Reverting as part of BOL User roles
		/*
		 * target.setLanguagePreference( (null != source.getLangPreference()) ? source.getLangPreference() :
		 * commonI18NService.getLanguage("en"));
		 */
		populateAssignedShipTos(source.getAssignedShipTos(), target);
		target.setVaultToken(source.getVaultToken());

		//		if (StringUtils.isEmpty(source.getUid()))
		//		{
		//			//Generating guid for new customer
		//			target.setGuid(UUID.randomUUID().toString());
		//		}

	}

	/**
	 * @param assignedShipTos
	 * @param target
	 */
	protected void populateAssignedShipTos(final Collection<String> assignedShipTos, final B2BCustomerModel target)
	{
		final Set<B2BUnitModel> shipTos = new HashSet<>();
		final Set<PrincipalGroupModel> groups = target.getGroups();

		assignedShipTos.forEach(assignedShipTo -> {
			final B2BUnitModel shipTo = (B2BUnitModel) b2bUnitService.getUnitForUid(assignedShipTo);
			shipTos.add(shipTo);
		});
		groups.addAll(shipTos);
		target.setGroups(groups);
	}

}
