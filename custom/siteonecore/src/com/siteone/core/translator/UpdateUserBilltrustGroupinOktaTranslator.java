/**
 *
 */
package com.siteone.core.translator;

import com.siteone.core.adapter.UpdateEmailStatusAdapter;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.enums.UpdateEmailStatusEnum;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.okta.data.SiteoneOktaResponseData;
import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaUserResponseData;
import de.hybris.platform.util.Config;
import org.springframework.web.client.ResourceAccessException;
import com.siteone.integration.services.okta.data.SiteoneOktaResponseData;
/**
 * @author Venkat Bharatham
 *
 */
public class UpdateUserBilltrustGroupinOktaTranslator extends AbstractValueTranslator
{
	private static final Logger LOGGER = Logger.getLogger(UpdateUserBilltrustGroupinOktaTranslator.class);
	private OKTAAPI oktaAPI ;
	private SiteOneCustomerAccountService siteOneCustomerAccountService ;

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.impex.jalo.translators.AbstractValueTranslator#importValue(java.lang.String,
	 * de.hybris.platform.jalo.Item)
	 */
	@Override
	public Object importValue(final String paybillonlineflag, final Item b2bCustomer) throws JaloInvalidParameterException
	{
		boolean updatedPaybillOnlineFlag = false;
		if (b2bCustomer != null && b2bCustomer.getPK() != null) {
		final B2BCustomerModel feedB2BCustomerModel = siteOneCustomerAccountService.getCustomerByPK(b2bCustomer.getPK().toString());

		if( StringUtils.isNotEmpty(paybillonlineflag) &&  paybillonlineflag.equalsIgnoreCase("true")) {
			updatedPaybillOnlineFlag = true;
		}
		LOGGER.debug("paybillonelinefalg==" + paybillonlineflag);
		LOGGER.debug("feedb2bCustomerModel.getIsActiveInOkta()==" + feedB2BCustomerModel.getIsActiveInOkta());
				try {
				final String groupId = Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_GROUPID, StringUtils.EMPTY);
				LOGGER.debug("Group Id :" + groupId);
				if(StringUtils.isNotEmpty(groupId)) {
					final OktaCreateOrUpdateUserResponseData OktaCreateOrUpdateUserResponseData = oktaAPI.getUser(feedB2BCustomerModel.getUid());
					final String userOktaId = OktaCreateOrUpdateUserResponseData.getId();
					LOGGER.debug("user_okta_id==" + userOktaId);
					if (StringUtils.isNotEmpty(userOktaId)) {
						oktaAPI.updateUser(feedB2BCustomerModel); //this method is to udpate the main account number in the okta customer profile .
						if (null != paybillonlineflag) {
							if (paybillonlineflag.equalsIgnoreCase("true")) {
								oktaAPI.addUserToGroup(groupId, userOktaId);
							} else {
								oktaAPI.removeUserFromGroup(groupId, userOktaId);
							}
						} else {
							LOGGER.error("Unable to add or remove the user - " + feedB2BCustomerModel.getUid() + " with UserOktaId - "
									+ userOktaId + " to or from the BillTrust group - " + groupId);
						}
					}
				}
				} catch (final ResourceAccessException resourceAccessException) {
				LOGGER.error("Userid not found in Okta...:" + feedB2BCustomerModel.getUid());
				} catch (final Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return updatedPaybillOnlineFlag;
	}
	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		oktaAPI  = (OKTAAPI) Registry.getApplicationContext().getBean("oktaAPI");
		siteOneCustomerAccountService = (SiteOneCustomerAccountService) Registry.getApplicationContext().getBean("customerAccountService");
	}
}
