/**
 *
 */
package com.siteone.facades.savedList;

import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.savedList.data.ListHeaderData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.wishlist.data.WishlistAddData;



/**
 * @author 1003567
 *
 */
public interface SiteoneSavedListFacade
{
	public String createdDate();

	public SearchPageData<SavedListData> getAllSavedList(PageableData pageableData, boolean isAssembly, String sortOrder);

	public String createSavedList(SavedListData savedListData, String productCodes, boolean isAssembly);

	public boolean checkDuplicate(String name, boolean isAssembly);

	public ProductData getProductDatatoDisplay(String productCode);

	public SavedListData getCSPListDetailsPage(String name, List<String> productCodes);

	public SavedListData getDetailsPage(String name);

	public SavedListData getListDetailsPage(String code);

	public SearchPageData<SavedListEntryData> getPagedDetailsPage(PageableData pageableData, String code);

	public boolean updateSavedList(SavedListData savedListData, String listName, boolean isAssembly);

	public void deleteSavedList(String code);

	public List<CustomerData> getAllCustomersForSharedList();

	public List<CustomerData> getAllCustomersForSharedList(String searchTerm, String searchType);

	public boolean saveShareListUser(String note, String user, String code, boolean isAssembly, boolean isViewEdit);

	public void addProductComment(String productCode, String comment, String listCode);

	public boolean removeProductComment(String productCode, String listCode);

	public List<Wishlist2Model> getAllwishlist(final UserModel user);

	public WishlistAddData addtoWishlist(final String productCode, final String quantity, final String wishListCode,
			final boolean prodQtyFlag, final String inventoryUOMId);



	public void moveToSaveList(final String wishListCode, final String productCode, boolean isAssembly);

	public List<SavedListData> getAllSavedListForEdit(boolean isAssembly);

	/**
	 * @param productDatas
	 * @throws CommerceCartModificationException
	 */
	public boolean addAssemblyListToCart(final String wishListCode, final int assemblyCount)
			throws CommerceCartModificationException;

	public boolean addListToCart(final String wishListCode) throws CommerceCartModificationException;

	public boolean addSelectedProductToCart(final String wishListCode, final List<String> productCodeList)
			throws CommerceCartModificationException;

	public void clearQuantities(final String wishListCode) throws CommerceCartModificationException;

	public boolean updateProductQuantity(final String productCode, final String quantity, final String wishListCode)
			throws CommerceCartModificationException;

	/**
	 * @param wishListName
	 * @param isAssembly
	 * @return
	 */
	boolean isListWithNameExist(String wishListName, boolean isAssembly);

	public void shareListEmail(String code, boolean cPrice, boolean rPrice, String[] emailList);

	public String addSelectedToNewWishlist(final String wishListName, String productCodes, final String currentWishlist,
			boolean isAssembly);

	public StringBuilder createSavedListFromUploadedCSV(final InputStream csvInputStream, SavedListData savedListData)
			throws IOException;

	public StringBuilder createCartEntryFromUploadedCSV(final InputStream csvInputStream)
			throws IOException, CommerceCartModificationException;

	String getUploadListErrorProducts(String value);

	public PriceData setListTotal(List<SavedListEntryData> savedListEntryData);

	public PriceData setItemTotal(SavedListEntryData savedListEntryData);

	public SavedListData getListDataBasedOnCode(final String code);

	/**
	 * @param entryData
	 * @param entryModel
	 */
	void updatePriceBasedOnUOM(SavedListEntryData entryData);

	public boolean updateUOMforEntries(final String wishListCode, final String inventoryUomId, final String productCode);

	public void setPriceForEntryData(final List<CustomerPriceData> customerPriceDataList,
			final List<SavedListEntryData> savedListEntryDataList, final List<CustomerPriceData> retailPriceDataList);

	public List<CustomerPriceData> updatePriceListBasedOnUOM(final SavedListData savedListData,
			final List<SavedListEntryData> savedListEntryDataList);

	/**
	 * @param entries
	 * @return
	 */
	Map<String, String> getCspCall(List<Wishlist2EntryModel> entries);

	public Integer getAllSavedListCount(boolean isAssembly);

	public Boolean categoryMatch(String level2Category);

	SearchPageData<ListHeaderData> getRecentLists(final PageableData pageableData, final boolean isAssembly,
			final String sortOrder);


}