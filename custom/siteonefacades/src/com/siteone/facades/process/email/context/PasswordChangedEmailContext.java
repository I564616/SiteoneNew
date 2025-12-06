/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.util.Config;

import java.util.Calendar;

import org.apache.log4j.Logger;


/**
 * @author 1099417
 *
 */
public class PasswordChangedEmailContext extends CustomerEmailContext
{
	private static final Logger LOG = Logger.getLogger(PasswordChangedEmailContext.class);
	public static final String SUPPORT_EMAIL = "supportEmail";
	private static final String COPY_RIGHT_YEAR = "copyRightYear";
	private static final String FIRST_NAME = "firstName";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Override
	public void init(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(storeFrontCustomerProcessModel, emailPageModel);
		final BaseSiteModel baseSite = getSite(storeFrontCustomerProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + storeFrontCustomerProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}
		put(SUPPORT_EMAIL, Config.getString("siteone.support.email", null));
		put(COPY_RIGHT_YEAR, "©" + Calendar.getInstance().get(Calendar.YEAR) + " ");
		put(DISPLAY_NAME, ((B2BCustomerModel) storeFrontCustomerProcessModel.getCustomer()).getFirstName());
		put(FIRST_NAME, ((B2BCustomerModel) storeFrontCustomerProcessModel.getCustomer()).getFirstName());
	}
}
