/**
 *
 */
package com.siteone.core.adapter.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import org.apache.log4j.Logger;

import com.siteone.core.adapter.FirstTimeUserImportAdapter;



/**
 * @author 1099417
 *
 */
public class DefaultFirstTimeUserImportAdapter implements FirstTimeUserImportAdapter
{
	private UserService userService;
	private ModelService modelService;
	private static final Logger LOGGER = Logger.getLogger(DefaultFirstTimeUserImportAdapter.class);


	@Override
	public boolean isFirstTimeUser(final String userId)
	{

		B2BCustomerModel existingB2bCustomerModel = null;

		try
		{

			existingB2bCustomerModel = getUserService().getUserForUID(userId.toLowerCase(), B2BCustomerModel.class);


		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOGGER.error(unknownIdentifierException);
		}


		if (null != existingB2bCustomerModel)
		{
			if (null != existingB2bCustomerModel.getIsFirstTimeUser())
			{
				return existingB2bCustomerModel.getIsFirstTimeUser().booleanValue();
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
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
