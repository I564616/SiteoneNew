/**
 *
 */
package com.siteone.core.news.dao;

import java.util.List;

import com.siteone.core.model.SiteOneNewsModel;


/**
 * @author 191179
 *
 */
public interface SiteOneNewsDao
{
	public List<SiteOneNewsModel> findNewsByCode(final String code);
}
