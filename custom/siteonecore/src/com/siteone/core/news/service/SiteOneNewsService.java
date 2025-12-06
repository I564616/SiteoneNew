/**
 *
 */
package com.siteone.core.news.service;

import com.siteone.core.model.SiteOneNewsModel;

/**
 * @author 191179
 *
 */
public interface SiteOneNewsService
{
	public SiteOneNewsModel getNewsByCode(final String newsCode);
}
