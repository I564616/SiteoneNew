/**
 *
 */
package com.siteone.facades.customer.populator;


import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;


/**
 * @author 205210
 *
 */
public class SiteOneCustomerDetailPopulator implements Populator<B2BCustomerModel, CustomerData>
{
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

	private UserService userService;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final B2BCustomerModel source, final CustomerData target) throws ConversionException
	{


		// YTODO Auto-generated method stub
		target.setName(source.getName());

		target.setEmail(source.getEmail());

		target.setDisplayUid(source.getEmail());

		target.setEncodedPass(source.getEncodedPassword());

		target.setLangPreference(source.getLanguagePreference());

		target.setVaultToken(source.getVaultToken());

		final UserGroupModel approverGroup = getUserService().getUserGroupForUID(B2BConstants.B2BADMINGROUP);



		if (getUserService().isMemberOfGroup(source, approverGroup))
		{
			target.setIsAdmin(Boolean.valueOf(true));
		}


		//PhoneContactInfoModel phoneContactInfo = new PhoneContactInfoModel();
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


		if (source.getIsTransactionEmailOpted() != null && source.getIsTransactionEmailOpted().booleanValue())
		{

			target.setEmailOptIn("true");

		}
		if (source.getIsSmsTextOpted() != null && source.getIsSmsTextOpted().booleanValue())
		{
			target.setSmsOptIn("true");
		}


	}

}