/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
/**
 * @author 1085284
 *
 */
public class StoreContactCellDecoratorGuest implements CSVCellDecorator
{

	private static final String SEPERATOR = ":";
	private UserService userService;
	private static final Logger LOGGER = Logger.getLogger(StoreContactCellDecoratorGuest.class);
	private SiteOneCustomerAccountService  siteOneCustomerAccountService ;

	@Override
	public String decorate(final int Data, final Map srcLine)
	{
		final String userDatas = (String) srcLine.get(Integer.valueOf(Data));
		final String[] userData = userDatas.split(SEPERATOR);
		siteOneCustomerAccountService = (SiteOneCustomerAccountService) Registry.getApplicationContext().getBean("defaultSiteOneCustomerAccountService");

		if (userData.length == 4)
		{
			final String userId = userData[0];
			final String unitId = userData[1];
			final String divisionCode = userData[2];
			final String hybrisOrdernumber = userData[3];
			CustomerModel customer = null;
			String divisionId = null;
			String guestGuid = null;

			userService = (UserService) Registry.getApplicationContext().getBean("userService");

			if ("1".equalsIgnoreCase(divisionCode) || "US".equalsIgnoreCase(divisionCode) || "JDL".equalsIgnoreCase(divisionCode))
			{
				divisionId = SiteoneCoreConstants.US_ISO_CODE;
			}
			else if ("2".equalsIgnoreCase(divisionCode) || "CA".equalsIgnoreCase(divisionCode)
					|| "JDLC".equalsIgnoreCase(divisionCode))
			{
				divisionId = SiteoneCoreConstants.CA_ISO_CODE;
			}

			try
			{
				guestGuid = getSiteOneCustomerAccountService().getGuestCustomerUID(hybrisOrdernumber.concat("_bak"));

				if (guestGuid != null)
				{

					customer = (CustomerModel) getUserService().getUserForUID(guestGuid);
				}

			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOGGER.error(unknownIdentifierException);
			}


			if (null != customer)
			{
				return customer.getUid();
			}
			else
			{
				final String userUid = "storecontact-" + unitId + "_" + divisionId;
				return userUid.toLowerCase();
			}
		}
		else
		{
			return userDatas;
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

	public SiteOneCustomerAccountService getSiteOneCustomerAccountService() {
		return siteOneCustomerAccountService;
	}

	public void setSiteOneCustomerAccountService(final SiteOneCustomerAccountService siteOneCustomerAccountService) {
		this.siteOneCustomerAccountService = siteOneCustomerAccountService;
	}
}
