/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import com.siteone.core.cronjob.dao.SiteOneFeedFileInfoCronJobDao;
import com.siteone.core.model.SiteOneFeedFileInfoModel;



/**
 * @author rpalanisamy
 *
 */
public class DefaultSiteOneFeedFileInfoCronJobDao extends AbstractItemDao implements SiteOneFeedFileInfoCronJobDao
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneFeedFileInfoCronJobDao.class);

	@Override
	public List<String> getFeedFilesInfo()
	{

		final String SEARCH_QUERY = "SELECT {" + SiteOneFeedFileInfoModel.PK + "} FROM {" + SiteOneFeedFileInfoModel._TYPECODE
				+ "} WHERE {" + SiteOneFeedFileInfoModel.CREATIONTIME + "} > ?timestamp ";

		final Map queryParams = new HashMap();
		queryParams.put("timestamp", getTimestamp());
		final FlexibleSearchQuery queryObj = new FlexibleSearchQuery(SEARCH_QUERY, queryParams);
		final SearchResult<SiteOneFeedFileInfoModel> result = getFlexibleSearchService().search(queryObj);

		final List<String> feedfiles = new ArrayList();
		final List<SiteOneFeedFileInfoModel> feedFilesList = result.getResult();

		if (!ObjectUtils.isEmpty(feedFilesList))
		{
			for (final SiteOneFeedFileInfoModel feedfile : feedFilesList)
			{
				try
				{
					if (!feedfile.getFileName().contains("somedia"))
					{
						final String fileNameArray[] = feedfile.getFileName().split("_");
						final String fileName = fileNameArray[0] + "_" + fileNameArray[2].substring(0, 2);
						feedfiles.add(fileName);
					}
				}
				catch (final Exception e)
				{
					LOG.error("Exception occured in DefaultSiteOneFeedFileInfoCronJobDao ", e);
				}
			}
		}

		return feedfiles;
	}

	private Date getTimestamp()
	{
		final Calendar c = Calendar.getInstance();
		final Date currentTime = new Date();
		c.setTime(currentTime);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

}
