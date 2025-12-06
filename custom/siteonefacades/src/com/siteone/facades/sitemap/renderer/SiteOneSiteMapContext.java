/**
 *
 */
package com.siteone.facades.sitemap.renderer;

import de.hybris.platform.acceleratorservices.enums.SiteMapChangeFrequencyEnum;
import de.hybris.platform.acceleratorservices.enums.SiteMapPageEnum;
import de.hybris.platform.acceleratorservices.model.SiteMapPageModel;
import de.hybris.platform.acceleratorservices.sitemap.renderer.SiteMapContext;
import de.hybris.platform.cms2.model.site.CMSSiteModel;

import java.util.Collection;
import java.util.function.Predicate;


/**
 * @author 1219341
 *
 */
public class SiteOneSiteMapContext extends SiteMapContext
{
	@Override
	public void init(final CMSSiteModel site, final SiteMapPageEnum siteMapPageEnum)
	{
		final String currentUrlEncodingPattern = getUrlEncoderService().getCurrentUrlEncodingPattern();
		this.put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(site, currentUrlEncodingPattern, true, ""));
		this.put(MEDIA_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(site, false, ""));


		final Collection<SiteMapPageModel> siteMapPages = site.getSiteMapConfig().getSiteMapPages();
		final SiteMapPageModel siteMapPageModel = (SiteMapPageModel) siteMapPages.stream().filter(o -> ((SiteMapPageModel) o).getCode().equals(siteMapPageEnum)).findFirst().orElse(null);

		if (siteMapPageModel != null)
		{
			this.put(CHANGE_FREQ, siteMapPageModel.getFrequency().getCode());
			this.put(PRIORITY, siteMapPageModel.getPriority());
		}
		else
		{
			this.put(CHANGE_FREQ, SiteMapChangeFrequencyEnum.DAILY.getCode());
			this.put(PRIORITY, Double.valueOf(0.5D));
		}
	}

}
