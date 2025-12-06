/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;

import com.siteone.core.model.process.PimBatchFailureEmailProcessModel;


/**
 * @author 1230514
 *
 */

public class PimBatchFailureEmailContext extends AbstractEmailContext<PimBatchFailureEmailProcessModel>
{

	private static final Logger LOG = Logger.getLogger(PimBatchFailureEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	public static final String ENVIRONMENT = "environment";
	public static final String ERROR_MESSAGE = "errorMessage";

	@Override
	protected BaseSiteModel getSite(final PimBatchFailureEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	public void init(final PimBatchFailureEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + businessProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			setUrlEncodingAttributes(getUrlEncoderService().getUrlEncodingPatternForEmail(businessProcessModel));
			// Lookup the site specific URLs
			put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL,
					getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);

		}
		if (null != businessProcessModel && null != emailPageModel)
		{
			
			put(ENVIRONMENT, Config.getString("website.siteone.https", Config.getString("website.siteone.http", "NA")));
			put(FROM_EMAIL, emailPageModel.getFromEmail());
			put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
			put(EMAIL, businessProcessModel.getToEmails());
			put(DISPLAY_NAME, "PIM Batch Failure Email");
			put(EMAIL_SUBJECT, "PIM Batch Failure Email");
			put(ERROR_MESSAGE, businessProcessModel.getFailedBatch());
			final LanguageModel language = businessProcessModel.getLanguage();
			LOG.info(businessProcessModel.getLanguage().getIsocode());
			if (language != null)
			{
				put(EMAIL_LANGUAGE, language);
			}
			
		}

	}

	@Override
	protected CustomerModel getCustomer(final PimBatchFailureEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	@Override
	protected LanguageModel getEmailLanguage(final PimBatchFailureEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getLanguage();
	}

}

