/**
 *
 */
package com.siteone.core.savedList.dao.impl;

import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.wishlist2.impl.daos.impl.DefaultWishlist2Dao;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import com.siteone.core.enums.SiteoneListTypeEnum;
import com.siteone.core.model.UploadListErrorInfoModel;
import com.siteone.core.savedList.dao.SiteoneSavedListDao;


/**
 * @author 1003567
 *
 */
public class DefaultSiteoneSavedListDao extends DefaultWishlist2Dao implements SiteoneSavedListDao
{
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	private static final String ASC = "asc";

	@Override
	public Wishlist2Model getSavedListbyName(final String name, final UserModel user, final boolean isAssembly)
	{
		final String queryString = "SELECT {pk} FROM {Wishlist2} WHERE ({viewEditOwners} like ?owner OR {user} = ?user) AND {name} = ?name AND {listType}=?listType";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("user", user);
		fQuery.addQueryParameter("owner", "%" + user.getPk() + "%");
		fQuery.addQueryParameter("name", name);
		if (isAssembly)
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.SAVEDLIST);
		}
		final SearchResult result = this.search(fQuery);
		return result.getCount() > 0 ? (Wishlist2Model) result.getResult().iterator().next() : null;
	}


	@Override
	public SearchPageData<Wishlist2Model> getAllLists(final UserModel user, final PageableData pageableData,
			final boolean isAssembly, final String sortOrder)
	{
		String queryString = null;
		if (sortOrder.equalsIgnoreCase(ASC))
		{
			queryString = "SELECT {pk} FROM {Wishlist2} WHERE ({owners} like ?owner OR {user}=?user) AND {listType}=?listType order by {modifiedtime} asc";
		}
		else
		{
			queryString = "SELECT {pk} FROM {Wishlist2} WHERE ({owners} like ?owner OR {user}=?user) AND {listType}=?listType order by {modifiedtime} desc";
		}

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("owner", "%" + user.getPk() + "%");
		queryParams.put("user", user);
		if (isAssembly)
		{
			queryParams.put("listType", SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			queryParams.put("listType", SiteoneListTypeEnum.SAVEDLIST);
		}

		return this.pagedFlexibleSearchService.search(queryString.toString(), queryParams, pageableData);

	}

	@Override
	public List<Wishlist2Model> getAllSavedListForEdit(final UserModel user, final boolean isAssembly)
	{
		final String queryString = "SELECT {pk} FROM {Wishlist2} WHERE  {user}=?user AND {listType}=?listType order by {modifiedtime} desc";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("user", user);
		/* fQuery.addQueryParameter("owner", "%" + user.getPk() + "%"); */
		if (isAssembly)
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.SAVEDLIST);
		}

		final SearchResult result = search(fQuery);
		return result.getResult();

	}

	@Override
	public Integer getAllSavedListCount(final UserModel user, final boolean isAssembly)
	{
		final String queryString = "SELECT count({pk}) FROM {Wishlist2} WHERE  ({owners} like ?owner OR {user}=?user) AND {listType}=?listType";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("user", user);
		fQuery.addQueryParameter("owner", "%" + user.getPk() + "%");
		if (isAssembly)
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			fQuery.addQueryParameter("listType", SiteoneListTypeEnum.SAVEDLIST);
		}

		fQuery.setResultClassList(Arrays.asList(Integer.class));
		final SearchResult<Integer> result = this.search(fQuery);
		return result.getResult().get(0);
	}

	@Override
	public Wishlist2Model getSavedListDetail(final String code)
	{
		final String queryString = "SELECT {pk} FROM {Wishlist2} WHERE  {pk}=?code";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("code", code);
		final SearchResult result = this.search(fQuery);
		return result.getCount() > 0 ? (Wishlist2Model) result.getResult().iterator().next() : null;
	}

	@Override
	public SearchPageData<Wishlist2EntryModel> getPagedSavedListDetail(final PageableData pageableData, final String code)
	{
		final String queryString = "SELECT {pk} FROM {Wishlist2Entry},{Product} WHERE  {Wishlist2Entry.product}={Product.pk} and {Wishlist2Entry.wishlist} = ?code and {Product.isProductOffline} = 0 order by {creationtime} desc";
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("code", code);
		return pagedFlexibleSearchService.search(queryString.toString(), queryParams, pageableData);
	}


	@Override
	public UploadListErrorInfoModel getUploadListErrorInfo(final String uid)
	{
		final String queryString = "SELECT {pk} FROM {UploadListErrorInfo} WHERE {errorId}=?code";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("code", uid);
		final SearchResult<UploadListErrorInfoModel> result = this.search(fQuery);
		return result.getCount() > 0 ? (UploadListErrorInfoModel) result.getResult().iterator().next() : null;
	}

}
