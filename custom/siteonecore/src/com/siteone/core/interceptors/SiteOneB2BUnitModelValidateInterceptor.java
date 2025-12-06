/**
 *
 */
package com.siteone.core.interceptors;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.interceptor.B2BUnitModelValidateInterceptor;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;




public class SiteOneB2BUnitModelValidateInterceptor extends B2BUnitModelValidateInterceptor
{
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;
	private UserService userService;
	private ModelService modelService;
	private L10NService l10NService;

	private static final Logger LOG = Logger.getLogger(SiteOneB2BUnitModelValidateInterceptor.class);

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{

		if (model instanceof B2BUnitModel)
		{
			final B2BUnitModel unit = (B2BUnitModel) model;
			// ensure all approvers of the unit are members of the b2bapprovergroup
			if (unit.getApprovers() != null)
			{
				final HashSet<B2BCustomerModel> unitApprovers = new HashSet<>(unit.getApprovers());
				if (CollectionUtils.isNotEmpty(unitApprovers))
				{
					final UserGroupModel b2bApproverGroup = getUserService().getUserGroupForUID(B2BConstants.B2BAPPROVERGROUP);

					final Iterator<B2BCustomerModel> iterator = unitApprovers.iterator();
					while (iterator.hasNext())
					{
						final B2BCustomerModel approver = iterator.next();
						if (!getUserService().isMemberOfGroup(approver, b2bApproverGroup))
						{
							// remove approvers who are not in the b2bapprovergroup.
							iterator.remove();
							LOG.warn(String.format("Removed approver %s from unit %s due to lack of membership of group %s",
									approver.getUid(), unit.getUid(), B2BConstants.B2BAPPROVERGROUP));

						}
					}
					unit.setApprovers(unitApprovers);
				}
			}

			//ensures that all of a deactivated unit's subunit's are also deactivated (except in case of new unit).
			if (!unit.getActive().booleanValue() && !ctx.getModelService().isNew(model))
			{
				final Set<B2BUnitModel> childUnits = getB2bUnitService().getB2BUnits(unit);

				for (final B2BUnitModel child : childUnits)
				{
					if (child.getActive().booleanValue())
					{
						child.setActive(Boolean.FALSE);
						getModelService().save(child);
					}
				}
			}
		}
	}

	@Override
	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	@Override
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Override
	protected ModelService getModelService()
	{
		return modelService;
	}

	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	@Override
	protected UserService getUserService()
	{
		return userService;
	}

	@Override
	public void setL10NService(final L10NService l10NService)
	{
		this.l10NService = l10NService;
	}

	@Override
	protected L10NService getL10NService()
	{
		return l10NService;
	}
}


