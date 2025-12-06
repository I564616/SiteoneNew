/**
 *
 */
package com.siteone.core.savedList.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.List;

import com.siteone.core.model.UploadListErrorInfoModel;


/**
 * @author 1003567
 *
 */
public interface SiteoneSavedListService
{

	public String createSavedList(Wishlist2Model savedListModel, String productCodes, boolean isAssembly);

	public String createSavedListForBulk(Wishlist2Model savedListModel, String productCodes, boolean isAssembly, String user);

	public Wishlist2Model getSavedListbyName(String name, boolean isAssembly);

	public Wishlist2Model getSavedListbyNameForBulk(String name, boolean isAssembly, String user);

	public UploadListErrorInfoModel getErrorListbyId(String uid);

	public void updateSavedList(final Wishlist2Model savedListModel);

	public void deleteSavedList(String code);

	public SearchPageData<Wishlist2Model> getAllLists(PageableData pageableData, boolean isAssembly, String sortOrder);

	public boolean saveShareListUser(String note, String user, String code, boolean isAssembly, boolean isViewEdit);

	public Wishlist2Model getSavedListDetail(String code);

	public SearchPageData<Wishlist2EntryModel> getPagedSavedListDetail(PageableData pageableData, String code);

	public List<Wishlist2Model> getAllSavedListForEdit(boolean isAssembly);

	public void initProductQty(Wishlist2EntryModel entry, Integer productQuantity);

	/**
	 * @param wishlist
	 * @param product
	 * @param desired
	 * @param priority
	 * @param comment
	 * @param uomId
	 */
	void addSiteoneWishlistEntry(Wishlist2Model wishlist, ProductModel product, Integer desired, Wishlist2EntryPriority priority,
			String comment, Integer uomId);

	/**
	 * @param sourceList
	 * @param destinationList
	 */
	void updateUOMIdForProducts(Wishlist2Model sourceList, Wishlist2Model destinationList);

	Integer getAllSavedListCount(boolean isAssembly);

}