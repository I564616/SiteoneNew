/**
 *
 */
package com.siteone.core.savedList.service.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.impl.DefaultWishlist2Service;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.core.enums.SiteoneListTypeEnum;
import com.siteone.core.model.UploadListErrorInfoModel;
import com.siteone.core.savedList.dao.SiteoneSavedListDao;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.core.services.SiteOneProductService;


/**
 * @author 1003567
 *
 */
public class DefaultSiteoneSavedListService extends DefaultWishlist2Service implements SiteoneSavedListService
{
	@Resource(name = "siteoneSavedListDao")
	private SiteoneSavedListDao siteoneSavedListDao;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	private static final String COMMA = ",";
	private static final String PIPE = "\\|";
	private static final Integer ZERO = 0;
	private static final Integer ONE = 1;
	private static final Integer TWO = 2;



	private List<Wishlist2EntryModel> getSavedListEntries(final String productCodes)
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(productCodes.split(COMMA)));
		Collections.reverse(products);
		List<Wishlist2EntryModel> entries = null;


		entries = new ArrayList<Wishlist2EntryModel>();
		for (final String product : products)
		{
			final Wishlist2EntryModel wishlist2EntryModel = new Wishlist2EntryModel();
			final String arr[] = product.split(PIPE);
			final String code = arr[ZERO].trim();
			final String quantity = arr[ONE].trim();
			String inventoryUom = null;
			final int size = arr.length;
			if (size > 2)
			{
				inventoryUom = arr[2].trim();
			}
			ProductModel productModel = siteOneProductService.getProductByProductCode(code);
			wishlist2EntryModel.setProduct(productModel);
			inventoryUom = siteOneProductService.getInventoryUOMIdForUOMProductsForList(productModel, inventoryUom);
			if (null != inventoryUom && StringUtils.isNumeric(inventoryUom))
			{
				wishlist2EntryModel.setUomId(Integer.valueOf(inventoryUom));
			}
			wishlist2EntryModel.setDesired(Integer.valueOf(quantity));
			wishlist2EntryModel.setAddedDate(new Date());
			wishlist2EntryModel.setPriority(Wishlist2EntryPriority.MEDIUM);
			modelService.save(wishlist2EntryModel);
			entries.add(wishlist2EntryModel);

		}
		return entries;
	}


	private List<Wishlist2EntryModel> getSavedListEntriesForBulk(final String productCodes)
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(productCodes.split(COMMA)));
		Collections.reverse(products);
		List<Wishlist2EntryModel> entries = null;


		entries = new ArrayList<Wishlist2EntryModel>();
		for (final String product : products)
		{
			final Wishlist2EntryModel wishlist2EntryModel = new Wishlist2EntryModel();
			final String arr[] = product.split(PIPE);
			final String code = arr[ZERO].trim();
			final String quantity = arr[ONE].trim();
			String inventoryUom = null;
			final int size = arr.length;
			if (size > 2)
			{
				inventoryUom = arr[2].trim();
			}
			final ProductModel productModel = siteOneProductService.getProductForListForBulk(code);
			if (productModel != null)
			{
				wishlist2EntryModel.setProduct(productModel);
				inventoryUom = siteOneProductService.getInventoryUOMIdForUOMProductsForList(productModel, inventoryUom);
				if (null != inventoryUom && StringUtils.isNumeric(inventoryUom))
				{
					wishlist2EntryModel.setUomId(Integer.valueOf(inventoryUom));
				}
				wishlist2EntryModel.setDesired(Integer.valueOf(quantity));
				wishlist2EntryModel.setAddedDate(new Date());
				wishlist2EntryModel.setPriority(Wishlist2EntryPriority.MEDIUM);
				modelService.save(wishlist2EntryModel);
				entries.add(wishlist2EntryModel);
			}

		}
		return entries;
	}

	@Override
	public String createSavedList(final Wishlist2Model savedListModel, final String productCodes, final boolean isAssembly)
	{

		savedListModel.setUser(userService.getCurrentUser());
		if (!StringUtils.isEmpty(productCodes))
		{
			savedListModel.setEntries(getSavedListEntries(productCodes));
		}
		savedListModel.setCreatedBy(userService.getCurrentUser().getUid());
		if (isAssembly)
		{
			savedListModel.setListType(SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			savedListModel.setListType(SiteoneListTypeEnum.SAVEDLIST);
		}
		modelService.save(savedListModel);
		final Wishlist2Model wishlist = siteoneSavedListDao.getSavedListbyName(savedListModel.getName(),
				userService.getCurrentUser(), isAssembly);
		return wishlist.getPk().toString();
	}

	@Override
	public String createSavedListForBulk(final Wishlist2Model savedListModel, final String productCodes, final boolean isAssembly,
			final String user)
	{
		final UserModel userModel = userService.getUserForUID(user);
		if (null != userModel)
		{
			savedListModel.setUser(userModel);
		}
		if (!StringUtils.isEmpty(productCodes))
		{
			savedListModel.setEntries(getSavedListEntriesForBulk(productCodes));
		}
		savedListModel.setCreatedBy(user);
		if (isAssembly)
		{
			savedListModel.setListType(SiteoneListTypeEnum.ASSEMBLY);
		}
		else
		{
			savedListModel.setListType(SiteoneListTypeEnum.SAVEDLIST);
		}
		modelService.save(savedListModel);
		final Wishlist2Model wishlist = siteoneSavedListDao.getSavedListbyName(savedListModel.getName(), userModel, isAssembly);
		return wishlist.getPk().toString();

	}

	@Override
	public void updateSavedList(final Wishlist2Model savedListModel)
	{
		modelService.save(savedListModel);
	}

	@Override
	public void deleteSavedList(final String code)
	{
		final Wishlist2Model savedListModel = getSavedListDetail(code);
		modelService.remove(savedListModel);
	}

	@Override
	public Wishlist2Model getSavedListbyName(final String name, final boolean isAssembly)
	{
		return siteoneSavedListDao.getSavedListbyName(name, userService.getCurrentUser(), isAssembly);
	}

	@Override
	public Wishlist2Model getSavedListbyNameForBulk(final String name, final boolean isAssembly, final String user)
	{
		final UserModel userModel = userService.getUserForUID(user);
		if (null != userModel)
		{
			return siteoneSavedListDao.getSavedListbyName(name, userModel, isAssembly);
		}
		return null;
	}


	@Override
	public SearchPageData<Wishlist2Model> getAllLists(final PageableData pageableData, final boolean isAssembly,
			final String sortOrder)
	{
		return siteoneSavedListDao.getAllLists(userService.getCurrentUser(), pageableData, isAssembly, sortOrder);

	}

	@Override
	public List<Wishlist2Model> getAllSavedListForEdit(final boolean isAssembly)
	{
		return siteoneSavedListDao.getAllSavedListForEdit(userService.getCurrentUser(), isAssembly);
	}

	@Override
	public Integer getAllSavedListCount(final boolean isAssembly)
	{
		return siteoneSavedListDao.getAllSavedListCount(userService.getCurrentUser(), isAssembly);
	}

	@Override
	public boolean saveShareListUser(final String note, final String user, final String code, final boolean isAssembly,
			final boolean isViewEdit)
	{
		List<String> usersList = null;
		List<UserModel> listOwners = null;
		List<UserModel> existingOwners = null;
		List<UserModel> existingViewEditOwners = null;
		List<UserModel> removeViewEditOwners = new ArrayList<UserModel>();
		List<UserModel> listViewEditOwners = null;

		final Wishlist2Model wishlist2Model = siteoneSavedListDao.getSavedListDetail(code);
		if (wishlist2Model != null)
		{
			existingOwners = wishlist2Model.getOwners();
			existingViewEditOwners = new ArrayList<UserModel>(wishlist2Model.getViewEditOwners());

		}
		if (existingOwners != null)
		{
			listOwners = new ArrayList<UserModel>();
			for (final UserModel users : existingOwners)
			{
				listOwners.add(users);
			}
		}
		else
		{

			listOwners = new ArrayList<UserModel>();
		}
		usersList = new ArrayList<String>(Arrays.asList(user.split(COMMA)));
		if (isViewEdit)
		{
			if (existingViewEditOwners != null)
			{
				listViewEditOwners = new ArrayList<UserModel>();
				listViewEditOwners.addAll(existingViewEditOwners);
			}
			else
			{
				listViewEditOwners = new ArrayList<UserModel>();
			}
		}
		else
		{
			if (CollectionUtils.isNotEmpty(existingViewEditOwners))
			{
				for (final UserModel users : existingViewEditOwners)
				{
					if (users.getUid().contains(user))
					{
						removeViewEditOwners.add(users);

					}
				}
				existingViewEditOwners.removeAll(removeViewEditOwners);
				wishlist2Model.setViewEditOwners(existingViewEditOwners);
				modelService.save(wishlist2Model);
			}
		}
		for (final String users : usersList)
		{
			final UserModel userModel = userService.getUserForUID(users);
			listOwners.add(userModel);
			if (isViewEdit)
			{
				listViewEditOwners.add(userModel);
			}
		}

		if (null != wishlist2Model)
		{
			wishlist2Model.setOwners(listOwners);
			if (isViewEdit)
			{
				wishlist2Model.setViewEditOwners(listViewEditOwners);
			}
			wishlist2Model.setShareNotes(note);
			wishlist2Model.setIsShared(Boolean.TRUE);
			modelService.save(wishlist2Model);
		}
		return true;

	}

	@Override
	public Wishlist2Model getSavedListDetail(final String code)
	{
		final Wishlist2Model wishlist2Model = siteoneSavedListDao.getSavedListDetail(code);

		return wishlist2Model;
	}

	@Override
	public UploadListErrorInfoModel getErrorListbyId(final String uid)
	{
		return siteoneSavedListDao.getUploadListErrorInfo(uid);
	}

	@Override
	public SearchPageData<Wishlist2EntryModel> getPagedSavedListDetail(final PageableData pageableData, final String code)
	{
		final SearchPageData<Wishlist2EntryModel> wishlist2EntryModel = siteoneSavedListDao.getPagedSavedListDetail(pageableData,
				code);

		return wishlist2EntryModel;
	}

	@Override
	public void initProductQty(final Wishlist2EntryModel entry, final Integer productQuantity)
	{
		entry.setDesired(productQuantity);
		modelService.save(entry);
	}

	/**
	 * @return the siteOneProductService
	 */
	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	/**
	 * @param siteOneProductService
	 *           the siteOneProductService to set
	 */
	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}

	@Override
	public void addSiteoneWishlistEntry(final Wishlist2Model wishlist, final ProductModel product, final Integer desired,
			final Wishlist2EntryPriority priority, final String comment, final Integer uomId)
	{
		final Wishlist2EntryModel entry = new Wishlist2EntryModel();
		entry.setProduct(product);
		entry.setDesired(desired);
		entry.setPriority(priority);
		entry.setComment(comment);
		entry.setAddedDate(new Date());
		entry.setUomId(uomId);
		super.addWishlistEntry(wishlist, entry);
	}

	@Override
	public void updateUOMIdForProducts(final Wishlist2Model sourceList, final Wishlist2Model destinationList)
	{
		if ((!CollectionUtils.isEmpty(sourceList.getEntries())) && (!CollectionUtils.isEmpty(destinationList.getEntries())))
		{
			for (final Wishlist2EntryModel sourceEntry : sourceList.getEntries())
			{
				for (final Wishlist2EntryModel destinationEntry : destinationList.getEntries())
				{
					if (null != destinationEntry.getProduct() && null != sourceEntry.getProduct()
							&& destinationEntry.getProduct().getCode().equalsIgnoreCase(sourceEntry.getProduct().getCode()))
					{
						destinationEntry.setUomId(sourceEntry.getUomId());
						modelService.save(destinationEntry);
						modelService.refresh(destinationEntry);
					}
				}
			}
		}
	}

}
