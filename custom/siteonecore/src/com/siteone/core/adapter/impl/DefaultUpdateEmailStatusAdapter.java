/**
 *
 */
package com.siteone.core.adapter.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import org.apache.log4j.Logger;

import com.siteone.core.adapter.UpdateEmailStatusAdapter;


/**
 * @author ASaha
 *
 */
public class DefaultUpdateEmailStatusAdapter implements UpdateEmailStatusAdapter
{
	private UserService userService;
	private ModelService modelService;
	private static final Logger LOGGER = Logger.getLogger(DefaultUpdateEmailStatusAdapter.class);

	@Override
	public void saveOldEmailId(final String userId, final String oldEmailId)
	{
		try
		{
			if (!userId.equalsIgnoreCase(oldEmailId))
			{
				final B2BCustomerModel oldB2bCustomerModel = getUserService().getUserForUID(oldEmailId.toLowerCase(),
						B2BCustomerModel.class);
				oldB2bCustomerModel.setOldContactEmail(oldEmailId);

				modelService.save(oldB2bCustomerModel);
			}
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOGGER.error(unknownIdentifierException);
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


}
