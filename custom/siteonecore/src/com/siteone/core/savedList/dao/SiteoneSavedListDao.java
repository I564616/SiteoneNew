/**
 *
 */
package com.siteone.core.savedList.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.List;

import com.siteone.core.model.UploadListErrorInfoModel;


/**
 * @author 1003567
 *
 */
public interface SiteoneSavedListDao
{
	public Wishlist2Model getSavedListbyName(String name, UserModel user, boolean isAssembly);

	public Wishlist2Model getSavedListDetail(String code);

	public SearchPageData<Wishlist2EntryModel> getPagedSavedListDetail(PageableData pageableData, String code);

	public SearchPageData<Wishlist2Model> getAllLists(UserModel user, PageableData pageableData, boolean isAssembly,
			String sortOrder);

	public List<Wishlist2Model> getAllSavedListForEdit(UserModel user, boolean isAssembly);

	/**
	 * @param uid
	 * @return
	 */
	public UploadListErrorInfoModel getUploadListErrorInfo(String uid);
	
	public Integer getAllSavedListCount(UserModel user, boolean isAssembly);

}