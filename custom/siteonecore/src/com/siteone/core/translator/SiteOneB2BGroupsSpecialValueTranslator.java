/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.security.PrincipalGroup;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;


public class SiteOneB2BGroupsSpecialValueTranslator extends AbstractSpecialValueTranslator
{
	private static final String B2BADMINGROUP = "b2badmingroup";

	private static final String B2BCUSTOMERGROUP = "b2bcustomergroup";

	private static final String PUNCHOUT_ADMINGROUP = "punchoutadmingroup";

	private static final String PUNCHOUT_CUSTOMERGROUP = "punchoutcustomergroup";
	
	private static final String PUNCHOUT_B2BUNIT = "PunchoutB2BUnit";
	
	public UserService getUserService()
	{
		return (UserService) Registry.getApplicationContext().getBean("userService");
	}

	public ModelService getModelService()
	{
		return (ModelService) Registry.getApplicationContext().getBean("modelService");
	}

	public B2BCustomerService getB2BCustomerService()
	{
		return (B2BCustomerService) Registry.getApplicationContext().getBean("b2bCustomerService");
	}

	public B2BUnitService getB2BUnitService()
	{
		return (B2BUnitService) Registry.getApplicationContext().getBean("b2bUnitService");
	}

	public SiteOneCustomerAccountService getSiteOneCustomerAccountService()
	{
		return (SiteOneCustomerAccountService) Registry.getApplicationContext().getBean("customerAccountService");
	}
	
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchService()
	{
		return (SiteOneFeatureSwitchCacheService) Registry.getApplicationContext().getBean("cachingSiteOneFeatureSwitchService");
	}
	
	@Override
	public void performImport(final String cellValue, final Item processedItem) throws ImpExException
	{
		if (cellValue != null && !cellValue.trim().startsWith("<ignore>") && processedItem != null && processedItem.isAlive()
				&& processedItem instanceof B2BCustomer)
		{
			final B2BCustomer customer = (B2BCustomer) processedItem;

			final UserGroupModel b2bUnitModel = getUserService().getUserGroupForUID(cellValue);


			//final UserGroup b2bUnitGroup = getModelService().getSource(b2bUnitModel);

			final UserGroupModel b2bAdminGroupModel = getUserService().getUserGroupForUID(B2BADMINGROUP);
			final UserGroupModel b2bCustomerGroupModel = getUserService().getUserGroupForUID(B2BCUSTOMERGROUP);

			final B2BCustomerModel b2bCustomerModel = getSiteOneCustomerAccountService()
					.getCustomerByPK(customer.getPK().toString());

			//final Set<PrincipalGroup> groups = new HashSet<PrincipalGroup>();
			final Set<PrincipalGroupModel> groups = new HashSet<>();
			groups.add(b2bUnitModel);
			
			if (getUserService().isMemberOfGroup(b2bCustomerModel, b2bAdminGroupModel))
			{
				UserGroupModel userGroupModel = isPunchOutGroup(b2bUnitModel,true);
				
				if (userGroupModel == null)
				{
					//final UserGroup b2bAdminUnitGroup = getModelService().getSource(b2bAdminGroupModel);
				    //groups.add(b2bAdminUnitGroup);
				    groups.add(b2bAdminGroupModel);
				}
				else
				{
					//final UserGroup punchoutCustomerGroup = getModelService().getSource(userGroupModel);
				    //groups.add(punchoutCustomerGroup);
				    groups.add(userGroupModel);
				}
			}
			else
			{
				UserGroupModel userGroupModel = isPunchOutGroup(b2bUnitModel,false);
				
				if (userGroupModel == null)
				{
					//final UserGroup b2bCustomerUnitGroup = getModelService().getSource(b2bCustomerGroupModel);
					groups.add(b2bCustomerGroupModel);
				}
				else
				{
					//final UserGroup punchoutCustomerGroup = getModelService().getSource(userGroupModel);
					groups.add(userGroupModel);
				}
			}
			//customer.setGroups(groups);
			b2bCustomerModel.setGroups(groups);
		    getModelService().save(b2bCustomerModel);
		}
		

	}
	
	protected UserGroupModel isPunchOutGroup(final UserGroupModel b2bUnitModel,boolean isAdmin)
	{
		B2BUnitModel mainAccount = null;

		if (b2bUnitModel != null && (b2bUnitModel instanceof B2BUnitModel))
		{
			B2BUnitModel b2BUnit = (B2BUnitModel) b2bUnitModel;

			mainAccount = ((SiteOneB2BUnitService) getB2BUnitService()).getMainAccountForUnit(b2BUnit.getUid());
		}

		if (mainAccount != null && mainAccount.getIsPunchOutAccount() != null 
				&& mainAccount.getIsPunchOutAccount().booleanValue())
		{
			Map<String, String> punchoutB2BUnitMapping = getSiteOneFeatureSwitchService()
					.getPunchoutB2BUnitMapping(PUNCHOUT_B2BUNIT);
			String accountName  = punchoutB2BUnitMapping.get(mainAccount.getUid());
			if(accountName!=null) {
				accountName=accountName.trim();
				if(isAdmin) 
				{
					return getUserService().getUserGroupForUID(accountName.trim()+PUNCHOUT_ADMINGROUP);
				}else
				{
					return getUserService().getUserGroupForUID(accountName.trim()+PUNCHOUT_CUSTOMERGROUP);
				}
			}
		}
		return null;
	}

}
