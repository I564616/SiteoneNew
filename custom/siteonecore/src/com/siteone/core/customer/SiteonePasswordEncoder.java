package com.siteone.core.customer;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.persistence.security.EJBCannotDecodePasswordException;
import de.hybris.platform.persistence.security.PasswordEncoder;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;



public class SiteonePasswordEncoder implements PasswordEncoder
{

	private static final Logger LOG = Logger.getLogger(SiteonePasswordEncoder.class);

	@Resource(name = "oktaAPI")
	private OKTAAPI oktaAPI;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	private UserService userService;



	public OKTAAPI getOktaAPI()
	{
		return oktaAPI;
	}

	public void setOktaAPI(final OKTAAPI oktaAPI)
	{
		this.oktaAPI = oktaAPI;
	}

	@Override
	public String encode(final String s, final String s1)
	{
		return null;
	}

	@Override
	public boolean check(final String uid, final String encodedPassword, final String plainPassword)
	{
		final SiteOneOktaSessionData data = oktaAPI.doAuth(uid, plainPassword);
		LOG.info("OKTA AUTH result for " + uid + " >>> " + data.getIsSuccess() + " -- " + data.getHttpStatusCode() + " -- "
				+ data.getMessage());
		if (BooleanUtils.isTrue(data.getIsSuccess()) && BooleanUtils.isTrue(sessionService.getAttribute("agroWebEnabled")))
		{
			B2BCustomerModel customer = null;
			B2BUnitModel b2bUnitModel = null;
			customer = getUserService().getUserForUID(uid, B2BCustomerModel.class);
			if (null != customer)
			{
				b2bUnitModel = customer.getDefaultB2BUnit();
				if (null == b2bUnitModel)
				{
					final B2BUnitModel shipTo = (B2BUnitModel) CollectionUtils.find(customer.getGroups(),
							PredicateUtils.instanceofPredicate(B2BUnitModel.class));
					if (null != shipTo)
					{
						b2bUnitModel = shipTo.getReportingOrganization() != null ? (B2BUnitModel) shipTo.getReportingOrganization()
								: shipTo;
					}

				}
			}
			if (null != b2bUnitModel && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("EnabledB2BUnitForAgroAI",
					b2bUnitModel.getUid()))
			{
				oktaAPI.getOAuth(uid, plainPassword);
			}


		}

		if ("401".equals(data.getHttpStatusCode()))
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTAUSERSTATUS_KEY + uid, SiteoneCoreConstants.OKTAUSER_UNAUTHORIZED);
		}

		if (SiteoneCoreConstants.OKTAUSER_LOCKED_OUT.equals(data.getMessage()))
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTAUSERSTATUS_KEY + uid, SiteoneCoreConstants.OKTAUSER_LOCKED_OUT);
		}

		if (StringUtils.isNotEmpty(data.getSessionToken()))
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN, data.getSessionToken());
		}
		if (null != data.getIsBTApp())
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTA_ISBT_APP, data.getIsBTApp());
		}
		if (null != data.getIsProjectServicesApp())
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTA_ISPS_APP, data.getIsProjectServicesApp());
		}
		if (null != data.getIsNextLevelApp())
		{
			sessionService.setAttribute(SiteoneCoreConstants.OKTA_NXTLevel_APP, data.getIsNextLevelApp());
		}


		LOG.info("OktaId==" + data.getOktaId());
		sessionService.setAttribute("user_okta_id", data.getOktaId());

		return data.getIsSuccess();
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public String decode(final String s) throws EJBCannotDecodePasswordException
	{
		throw new EJBCannotDecodePasswordException(null, "OKTA passwords can not be decoded", -1);
	}
}
