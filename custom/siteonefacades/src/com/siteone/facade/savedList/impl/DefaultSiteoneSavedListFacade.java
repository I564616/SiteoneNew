/**
 *
 */
package com.siteone.facade.savedList.impl;

import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.impl.DefaultWishlist2Service;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.ShareListEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.model.UploadErrorProductDetailModel;
import com.siteone.core.model.UploadListErrorInfoModel;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.savedList.data.ListHeaderData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.savedList.data.UploadProductListErrorData;
import com.siteone.facades.savedList.populator.SavedListReversePopulator;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.wishlist.data.WishlistAddData;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;


/**
 * @author 1003567
 *
 */
public class DefaultSiteoneSavedListFacade implements SiteoneSavedListFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteoneSavedListFacade.class);
	private static final String LIST_NAME_EXISTS = "Listname exists";
	private static final String ADDED_TO_NEW_LIST = "Added to new list";
	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.totalprice.digits", 2);
	private static final String MORE_ON_THE_WAY_CATEGORIES = Config.getString("category.backorder.moreontheway",
			"Manufactured Hardscape Products,Natural Stone,Outdoor Living");

	@Resource(name = "savedListReversePopulator")
	private SavedListReversePopulator savedListReversePopulator;

	@Resource(name = "wishlistService")
	private DefaultWishlist2Service wishlistService;

	@Resource(name = "siteoneSavedListConverter")
	private Converter<Wishlist2Model, SavedListData> siteoneSavedListConverter;

	@Resource(name = "siteoneAllSavedListConverter")
	private Converter<Wishlist2Model, SavedListData> siteoneAllSavedListConverter;

	@Resource(name = "siteoneSavedListEntryConverter")
	private Converter<Wishlist2EntryModel, SavedListEntryData> siteoneSavedListEntryConverter;

	@Resource(name = "siteoneSavedListService")
	private SiteoneSavedListService siteoneSavedListService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	private static String SUCCESS_MESSAGE = "pdp.message.addToList";

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "productService")
	private DefaultProductService productService;

	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteoneHeaderListConverter")
	private Converter<Wishlist2Model, ListHeaderData> siteoneHeaderListConverter;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "cartService")
	private CartService cartService;

	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public void setSiteoneSavedListService(final SiteoneSavedListService siteoneSavedListService)
	{
		this.siteoneSavedListService = siteoneSavedListService;
	}

	public void setSiteoneHeaderListConverter(final Converter<Wishlist2Model, ListHeaderData> siteoneHeaderListConverter)
	{
		this.siteoneHeaderListConverter = siteoneHeaderListConverter;
	}


	private static String VARIANT_PRODUCT = "baseproduct";
	private static String HIDDEN_UPC = "No Unit Of Measure available for this product";
	private static String PRODUCT_NOT_FOUND = "Product not found";
	private static String UOM_PRODUCT = "uomproduct";
	private static final String COMMA = ",";
	private static final String PIPE = "\\|";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;

	@Override
	public String createdDate()
	{
		final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		final long millis = System.currentTimeMillis();
		return dateFormat.format(millis);
	}

	@Override
	public String createSavedList(final SavedListData savedListData, final String productCodes, final boolean isAssembly)
	{
		if (!checkifSavedListAlreadyExists(savedListData.getName(), isAssembly))
		{
			final Wishlist2Model savedListModel = new Wishlist2Model();
			savedListReversePopulator.populate(savedListData, savedListModel);
			return siteoneSavedListService.createSavedList(savedListModel, productCodes, isAssembly);
		}

		return null;
	}

	@Override
	public boolean checkDuplicate(final String name, final boolean isAssembly)
	{
		return checkifSavedListAlreadyExists(name, isAssembly);
	}

	@Override
	public String addSelectedToNewWishlist(final String wishListName, final String productCodes, final String currentWishlist,
			final boolean isAssembly)
	{
		if (checkifSavedListAlreadyExists(wishListName, isAssembly))
		{
			return LIST_NAME_EXISTS;
		}
		else
		{
			if (!StringUtils.isEmpty(productCodes))
			{
				final String list = productCodes.trim();
				final String productsToPass = list.replace(" ", ",");
				final SavedListData savedListData = new SavedListData();
				if (!StringUtils.isEmpty(wishListName))
				{
					savedListData.setName(wishListName);
				}
				final String wishListCode = createSavedList(savedListData, productsToPass, isAssembly);
				if (null != wishListCode)
				{
					updateUOMIdForProducts(wishListName, currentWishlist);
					moveToMultipleSaveList(currentWishlist, productsToPass, false);
					return wishListCode;
				}
			}
		}
		return null;
	}

	protected void moveToMultipleSaveList(final String wishListCode, final String productCodesToPass, final boolean isAssembly)
	{
		final List<String> productCodes = new ArrayList<>(Arrays.asList(productCodesToPass.split(",")));
		for (final String product : productCodes)
		{
			final String[] arr = product.split(SiteoneFacadesConstants.PIPE);
			moveToSaveList(wishListCode, arr[SiteoneFacadesConstants.ZERO], isAssembly);
		}
	}

	@Override
	public void shareListEmail(final String code, final boolean cPrice, final boolean rPrice, final String[] emailList)
	{
		final SavedListData savedListData = this.getDetailsPage(code);

		final String listName = savedListData.getName();
		final String userName = (customerFacade.getCurrentCustomer()).getFirstName();
		final String customerName = (customerFacade.getCurrentCustomer().getName());
		final String accountNumber = (customerFacade.getCurrentCustomer().getUnit().getUid().trim().split("[_]")[0].trim());
		final String senderEmail = (customerFacade.getCurrentCustomer().getEmail());
		final Map<String, String> retailPriceList = new HashMap<>(savedListData.getEntries().size());
		final Map<String, String> custPriceList = new HashMap<>(savedListData.getEntries().size());
		Double retPrc = null;
		Double cusPrc = null;
		for (final SavedListEntryData entry : savedListData.getEntries())
		{
			final InventoryUPCModel inventoryUPCModel = siteOneProductUOMService
					.getInventoryUOMById(String.valueOf(entry.getInventoryUom()));
			if (null != entry.getProduct().getPrice())
			{
				retPrc = (inventoryUPCModel.getInventoryMultiplier())
						* (Double.valueOf(entry.getProduct().getPrice().getValue().toString()));
				retailPriceList.put(entry.getProduct().getCode(), retPrc.toString());
			}
			if (null != entry.getProduct().getCustomerPrice())
			{
				cusPrc = (inventoryUPCModel.getInventoryMultiplier())
						* (Double.valueOf(entry.getProduct().getCustomerPrice().getValue().toString()));
				custPriceList.put(entry.getProduct().getCode(), cusPrc.toString());
			}
		}

		for (final String email : emailList)
		{
			eventService.publishEvent(initializeEvent(new ShareListEvent(), code, email, userName, listName, rPrice, cPrice,
					custPriceList, retailPriceList, customerName, accountNumber, senderEmail));
		}
	}

	public ShareListEvent initializeEvent(final ShareListEvent event, final String code, final String email, final String userName,
			final String listName, final boolean rPrice, final boolean cPrice, final Map cPriceList, final Map rPriceList,
			final String customerName, final String accountNumber, final String senderEmail)
	{

		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		else
		{
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}

		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		event.setCustomer((CustomerModel) userService.getCurrentUser());
		event.setListCode(code);
		event.setListName(listName);
		event.setUserName(userName);
		event.setEmail(email);
		event.setShowRetailPrice(rPrice);
		event.setShowCustPrice(cPrice);
		event.setCustPriceList(cPriceList);
		event.setRetailPriceList(rPriceList);
		event.setStoreId(((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId());
		event.setAccountNumber(accountNumber);
		event.setCustomerName(customerName);
		event.setSenderEmail(senderEmail);
		return event;
	}

	@Override
	public SavedListData getDetailsPage(final String code)
	{
		return getListDataForDetailsPage(code, "detailPage");
	}

	protected void updateUomIdForListEntriesWithLatestUOM(final Wishlist2Model savedListModel, final SavedListData savedListData)
	{
		if ((!CollectionUtils.isEmpty(savedListData.getEntries())) && (!CollectionUtils.isEmpty(savedListModel.getEntries())))
		{
			for (final SavedListEntryData entryData : savedListData.getEntries())
			{
				for (final Wishlist2EntryModel entryModel : savedListModel.getEntries())
				{
					if (entryData.getProduct().getCode().equalsIgnoreCase(entryModel.getProduct().getCode()))
					{
						Integer inventoryUOMId = entryModel.getUomId();
						if (!CollectionUtils.isEmpty(entryData.getProduct().getSellableUoms()))
						{
							if (entryData.getProduct().getSellableUoms().size() == 1)
							{
								inventoryUOMId = Integer.valueOf(entryData.getProduct().getSellableUoms().get(0).getInventoryUOMID());
							}
							else
							{
								inventoryUOMId = (siteOneProductService.getInventoryUOMIdForUOMProductsForList(entryModel.getProduct(),
										String.valueOf(entryModel.getUomId())) == null ? null
												: Integer.valueOf(siteOneProductService.getInventoryUOMIdForUOMProductsForList(
														entryModel.getProduct(), String.valueOf(entryModel.getUomId()))));
							}
							if (null != inventoryUOMId)
							{
								entryModel.setUomId(inventoryUOMId);
								modelService.save(entryModel);
								modelService.refresh(entryModel);
							}
						}
						else if (entryModel.getProduct().getInventoryUPCID() != null)
						{
							entryModel.setUomId(Integer.valueOf(entryModel.getProduct().getInventoryUPCID()));
							modelService.save(entryModel);
							modelService.refresh(entryModel);
						}
					}
				}
			}
		}
	}

	@Override
	public SearchPageData<SavedListEntryData> getPagedDetailsPage(final PageableData pageableData, final String code)
	{
		final SearchPageData<Wishlist2EntryModel> savedListEntryModel = siteoneSavedListService
				.getPagedSavedListDetail(pageableData, code);

		return this.convertPageData(savedListEntryModel, this.siteoneSavedListEntryConverter);
	}


	@Override
	public SearchPageData<SavedListData> getAllSavedList(final PageableData pageableData, final boolean isAssembly,
			final String sortOrder)
	{

		final SearchPageData<Wishlist2Model> savedListModel = siteoneSavedListService.getAllLists(pageableData, isAssembly,
				sortOrder);
		return this.convertPageData(savedListModel, this.siteoneAllSavedListConverter);
	}

	@Override
	public ProductData getProductDatatoDisplay(final String productCode)
	{
		final List<ProductOption> options = new ArrayList<>(
				Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.SUMMARY,
						ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES, ProductOption.REVIEW,
						ProductOption.STOCK, ProductOption.CUSTOMER_PRICE, ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION,
						ProductOption.VARIANT_FULL, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE,
						ProductOption.DELIVERY_MODE_AVAILABILITY, ProductOption.DATA_SHEET, ProductOption.AVAILABILITY_MESSAGE));

		return siteOneProductFacade.getProductForList(productCode, options);

	}




	@Override
	public boolean updateSavedList(final SavedListData savedListData, final String listName, final boolean isAssembly)
	{
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListbyName(listName, isAssembly);
		if (savedListData.getName().equals(savedListModel.getName()))
		{
			savedListReversePopulator.populate(savedListData, savedListModel);
			siteoneSavedListService.updateSavedList(savedListModel);
			return true;
		}
		else if (!checkifSavedListAlreadyExists(savedListData.getName(), isAssembly))
		{
			savedListReversePopulator.populate(savedListData, savedListModel);
			siteoneSavedListService.updateSavedList(savedListModel);
			return true;
		}
		return false;
	}

	@Override
	public List<CustomerData> getAllCustomersForSharedList()
	{
		return ((SiteOneB2BUnitFacade) b2bUnitFacade).getUsersforCustomerUnit();
	}

	@Override
	public List<CustomerData> getAllCustomersForSharedList(final String searchTerm, final String searchType)
	{
		return ((SiteOneB2BUnitFacade) b2bUnitFacade).getUsersforCustomerUnit(searchTerm, searchType);
	}

	@Override
	public void deleteSavedList(final String code)
	{
		siteoneSavedListService.deleteSavedList(code);
	}

	@Override
	public List<Wishlist2Model> getAllwishlist(final UserModel user)
	{
		return wishlistService.getWishlists(user);
	}

	@Override
	public boolean addAssemblyListToCart(final String wishListCode, final int assemblyCount)
			throws CommerceCartModificationException
	{
		Wishlist2Model wishlist = null;
		boolean addAssemblyListToCartStatus = false;
		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}
		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{
				if (null != entry.getProduct())
				{
					final Integer qty = entry.getDesired();
					if (qty.longValue() > 0 && assemblyCount > 0)
					{
						if (null != entry.getProduct().getInventoryUPCID())
						{
							siteOneCartFacade.addToCart(entry.getProduct().getCode(), (qty.longValue() * assemblyCount),
									entry.getProduct().getInventoryUPCID(), false,
									((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId());
							siteoneSavedListService.initProductQty(entry, Integer.valueOf(1));
							addAssemblyListToCartStatus = true;
						}
					}
				}
			}
		}
		return addAssemblyListToCartStatus;

	}


	@Override
	public WishlistAddData addtoWishlist(final String productCode, final String quantity, final String wishListCode,
			final boolean prodQtyFlag, String inventoryUOMId)
	{

		final WishlistAddData wishlistAddData = new WishlistAddData();
		final ProductModel productModel = siteOneProductService.getProductForList(productCode);
		if (null != productModel)
		{
			final Collection<VariantProductModel> variants = productModel.getVariants();
			if (null != variants && !variants.isEmpty())
			{
				final int variantCount = variants.size();
				if (variantCount >= 1)
				{
					wishlistAddData.setMessage(VARIANT_PRODUCT);
					return wishlistAddData;
				}
			}
			else if (StringUtils.isBlank(inventoryUOMId))
			{
				inventoryUOMId = siteOneProductService.getInventoryUOMIdForUOMProductsForList(productModel, null);
				if (StringUtils.isBlank(inventoryUOMId))
				{
					wishlistAddData.setMessage(HIDDEN_UPC);
					return wishlistAddData;
				}
			}
		}
		else
		{
			wishlistAddData.setMessage(PRODUCT_NOT_FOUND);
			return wishlistAddData;
		}

		Wishlist2Model wishlist = null;
		boolean productExist = false;

		boolean productSaveList = true;
		int productQty = 1;

		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}


		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{

			Wishlist2EntryModel modifiedentry = new Wishlist2EntryModel();


			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{


				if (null != entry.getProduct())
				{
					if (entry.getProduct().getItemNumber().equalsIgnoreCase(productCode))
					{
						productExist = true;
					}



					if (productExist)
					{
						productSaveList = false;
						if (prodQtyFlag)
						{
							productQty = Integer.valueOf(quantity);
						}
						else
						{
							productQty = entry.getDesired() + Integer.valueOf(quantity);
						}
						if (null != inventoryUOMId)
						{
							entry.setUomId(Integer.valueOf(inventoryUOMId));
						}
						modifiedentry = entry;
						// wishlistService.removeWishlistEntry(wishlist, entry);
						//  wishlistService.addWishlistEntry(wishlist, productService.getProductForCode(productCode), productQty,
						//	Wishlist2EntryPriority.MEDIUM, entry.getComment());
						break;

					}


				}



			}

			if (modifiedentry != null && modifiedentry.getProduct() != null)
			{
				modifiedentry.setDesired(productQty);
				modelService.save(modifiedentry);
			}
		}

		if (productSaveList)
		{
			productQty = Integer.valueOf(quantity);

			siteoneSavedListService.addSiteoneWishlistEntry(wishlist, productModel, productQty, Wishlist2EntryPriority.MEDIUM, "",
					Integer.valueOf(inventoryUOMId));

		}

		wishlistAddData.setMessage(getMessageSource().getMessage(SUCCESS_MESSAGE, null, getI18nService().getCurrentLocale()));
		return wishlistAddData;

	}


	@SuppressWarnings("javadoc")
	protected <S, T> SearchPageData<T> convertPageData(final SearchPageData<S> source, final Converter<S, T> converter)
	{
		final SearchPageData<T> result = new SearchPageData<T>();
		result.setPagination(source.getPagination());
		result.setSorts(source.getSorts());
		result.setResults(Converters.convertAll(source.getResults(), converter));
		return result;
	}

	@Override
	public boolean saveShareListUser(final String note, final String user, final String code, final boolean isAssembly,
			final boolean isViewEdit)
	{
		siteoneSavedListService.saveShareListUser(note, user, code, isAssembly, isViewEdit);
		return false;
	}

	@Override
	public boolean updateUOMforEntries(final String wishListCode, final String inventoryUomId, final String productCode)
	{

		final Wishlist2Model wishlistModel = siteoneSavedListService.getSavedListDetail(wishListCode);
		boolean flag = false;
		if (CollectionUtils.isNotEmpty(wishlistModel.getEntries()))
		{

			for (final Wishlist2EntryModel entry : wishlistModel.getEntries())
			{
				if (null != entry.getProduct())
				{
					if (entry.getProduct().getCode().equalsIgnoreCase(productCode))
					{
						entry.setUomId(Integer.valueOf(inventoryUomId));
						modelService.save(entry);
						modelService.refresh(entry);
						flag = true;
					}

				}
			}
		}
		return flag;
	}


	@Override
	public void addProductComment(final String productCode, final String comment, final String listCode)
	{

		final Wishlist2Model wishlist = siteoneSavedListService.getSavedListDetail(listCode);

		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{

				if (null != entry.getProduct() && entry.getProduct().getCode().equals(productCode))
				{
					final Integer quantity = entry.getDesired();
					wishlistService.removeWishlistEntry(wishlist, entry);
					wishlistService.addWishlistEntry(wishlist, productService.getProductForCode(productCode), quantity,
							Wishlist2EntryPriority.MEDIUM, comment);

				}
			}
		}
	}




	protected boolean checkifSavedListAlreadyExists(final String name, final boolean isAssembly)
	{
		if (siteoneSavedListService.getSavedListbyName(name, isAssembly) != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean removeProductComment(final String productCode, final String listCode)
	{
		final Wishlist2Model wishlist = siteoneSavedListService.getSavedListDetail(listCode);

		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{

				if (null != entry.getProduct() && entry.getProduct().getCode().equals(productCode))
				{
					final Integer quantity = entry.getDesired();
					wishlistService.removeWishlistEntry(wishlist, entry);
					wishlistService.addWishlistEntry(wishlist, productService.getProductForCode(productCode), quantity,
							Wishlist2EntryPriority.MEDIUM, "");

				}
			}
		}
		return true;
	}



	@Override
	public void moveToSaveList(final String wishListCode, final String productCode, final boolean isAssembly)
	{
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListDetail(wishListCode);
		final ProductModel productModel = siteOneProductService.getProductForList(productCode);
		if (null != productModel && null != savedListModel)
		{
			wishlistService.removeWishlistEntryForProduct(productModel, savedListModel);
		}
	}

	@Override
	public List<SavedListData> getAllSavedListForEdit(final boolean isAssembly)
	{
		final List<Wishlist2Model> savedListModel = siteoneSavedListService.getAllSavedListForEdit(isAssembly);
		return siteoneAllSavedListConverter.convertAll(savedListModel);
	}

	@Override
	public Integer getAllSavedListCount(final boolean isAssembly)
	{
		return siteoneSavedListService.getAllSavedListCount(isAssembly);
	}


	@Override
	public boolean addListToCart(final String wishListCode) throws CommerceCartModificationException
	{
		Wishlist2Model wishlist = null;
		boolean quantityFlag = false;
		final List<CommerceCartParameter> parameterList = new ArrayList<>();
		final CartModel cartModel = cartService.getSessionCart();
		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}
		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{
				if (null != entry.getProduct())
				{
					final Integer qty = entry.getDesired();
					if (qty.intValue() > 0)
					{
						String uomId = null;
						if (null != entry.getUomId())
						{
							uomId = String.valueOf(entry.getUomId());
						}
						else if (null != entry.getProduct().getInventoryUPCID())
						{
							uomId = entry.getProduct().getInventoryUPCID();
						}

						if (!StringUtils.isBlank(uomId))
						{
							siteOneCartFacade.updateShipToAndPOS(cartModel);
							final CommerceCartParameter parameter = siteOneCartFacade.populateParameterData(cartModel, qty.longValue(),
									uomId, ((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId(),
									entry.getProduct());
							parameterList.add(parameter);
							quantityFlag = true;
						}
					}
				}
			}
		}
		if (!parameterList.isEmpty())
		{
			siteOneCartFacade.addMultipleProductsToCart(parameterList);
			updateUomForCartEntries(cartModel, wishlist);
		}
		return quantityFlag;
	}

	@Override
	public boolean addSelectedProductToCart(final String wishListCode, final List<String> products)
			throws CommerceCartModificationException
	{
		Wishlist2Model wishlist = null;
		boolean quantityFlag = false;
		final CartModel cartModel = cartService.getSessionCart();
		final List<CommerceCartParameter> parameterList = new ArrayList<>();
		CommerceCartParameter parameter;
		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}
		if (StringUtils.isBlank(wishListCode))
		{
			String productDetail[] = null;
			for (final String product : products)
			{
				productDetail = product.split(SiteoneFacadesConstants.PIPE);
				final ProductModel productModel = siteOneProductService
						.getProductByProductCode(productDetail[SiteoneFacadesConstants.ZERO]);
				if (productModel != null)
				{
					final Integer quantity = Integer.valueOf(productDetail[SiteoneFacadesConstants.ONE]);
					if (quantity.intValue() > 0)
					{
						siteOneCartFacade.updateShipToAndPOS(cartModel);
						if (productDetail.length > 2)
						{
							final Integer uomId = Integer.valueOf(productDetail[SiteoneFacadesConstants.TWO]);
							parameter = siteOneCartFacade.populateParameterData(cartModel, quantity.longValue(), uomId.toString(),
									((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId(), productModel);
						}
						else
						{
							parameter = siteOneCartFacade.populateParameterData(cartModel, quantity.longValue(), null,
									((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId(), productModel);
						}

						parameterList.add(parameter);
						quantityFlag = true;
					}

				}
			}
		}
		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{
				if (null != entry.getProduct())
				{
					for (final String productCode : products)
					{
						if (StringUtils.isNotEmpty(productCode))
						{
							final String arr[] = productCode.split(SiteoneFacadesConstants.PIPE);
							if (arr[SiteoneFacadesConstants.ZERO].equals(entry.getProduct().getCode()))
							{
								final Integer qty = entry.getDesired();
								if (qty.intValue() > 0)
								{
									siteOneCartFacade.updateShipToAndPOS(cartModel);
									if (arr.length > 2)
									{
										final Integer uomId = Integer.valueOf(arr[SiteoneFacadesConstants.TWO]);
										parameter = siteOneCartFacade.populateParameterData(cartModel, qty.longValue(), uomId.toString(),
												((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId(),
												entry.getProduct());
									}
									else
									{
										parameter = siteOneCartFacade.populateParameterData(cartModel, qty.longValue(), null,
												((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId(),
												entry.getProduct());
									}
									parameterList.add(parameter);
									quantityFlag = true;
								}
							}
						}
					}
				}
			}
		}
		if (!parameterList.isEmpty())
		{
			siteOneCartFacade.addMultipleProductsToCart(parameterList);
			updateUomForCartEntries(cartModel, wishlist);
		}
		return quantityFlag;
	}

	protected void updateUomForCartEntries(final CartModel cartModel, final Wishlist2Model wishlist)
	{
		if (null != cartModel && !CollectionUtils.isEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel cartEntry : cartModel.getEntries())
			{
				InventoryUPCModel inventoryUPCModel = null;
				if (null != wishlist)
				{
					for (final Wishlist2EntryModel entry : wishlist.getEntries())
					{
						if (cartEntry.getProduct().getCode().equalsIgnoreCase(entry.getProduct().getCode()))
						{
							if (null != entry.getUomId())
							{
								inventoryUPCModel = siteOneProductUOMService.getInventoryUOMById(entry.getUomId().toString());
								if (null != inventoryUPCModel && null != cartEntry.getInventoryUOM() && !(cartEntry.getInventoryUOM()
										.getInventoryUPCID().equalsIgnoreCase(inventoryUPCModel.getInventoryUPCID())))
								{
									cartEntry.setInventoryUOM(inventoryUPCModel);
									cartEntry.setBasePrice(cartEntry.getBasePrice() * inventoryUPCModel.getInventoryMultiplier());
									cartEntry.setListPrice(cartEntry.getListPrice() * inventoryUPCModel.getInventoryMultiplier());
									cartEntry.setTotalPrice(cartEntry.getTotalPrice() * inventoryUPCModel.getInventoryMultiplier());
									if (inventoryUPCModel.getInventoryMultiplier() != 1)
									{
										cartEntry.setIsBaseUom(false);
									}
									modelService.save(cartEntry);
									modelService.refresh(cartEntry);
								}
							}
						}
					}
				}
				else
				{
					if (null != cartEntry.getInventoryUOM())
					{
						inventoryUPCModel = cartEntry.getInventoryUOM();
						cartEntry.setBasePrice(cartEntry.getBasePrice() * inventoryUPCModel.getInventoryMultiplier());
						cartEntry.setListPrice(cartEntry.getListPrice() * inventoryUPCModel.getInventoryMultiplier());
						cartEntry.setTotalPrice(cartEntry.getTotalPrice() * inventoryUPCModel.getInventoryMultiplier());
						if (inventoryUPCModel.getInventoryMultiplier() != 1)
						{
							cartEntry.setIsBaseUom(false);
						}
						modelService.save(cartEntry);
						modelService.refresh(cartEntry);
					}
				}
			}
		}
	}

	@Override
	public void clearQuantities(final String wishListCode) throws CommerceCartModificationException
	{
		Wishlist2Model wishlist = null;
		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}
		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{
				if (null != entry.getProduct())
				{
					siteoneSavedListService.initProductQty(entry, Integer.valueOf(1));
				}
			}
		}
	}

	@Override
	public boolean updateProductQuantity(final String productCode, final String quantity, final String wishListCode)
			throws CommerceCartModificationException
	{
		Wishlist2Model wishlist = null;
		if (null != wishListCode)
		{
			wishlist = siteoneSavedListService.getSavedListDetail(wishListCode);
		}
		if (null != wishlist && !wishlist.getEntries().isEmpty())
		{
			for (final Wishlist2EntryModel entry : wishlist.getEntries())
			{
				if (null != entry.getProduct())
				{
					if (productCode.equals(entry.getProduct().getCode()))
					{
						siteoneSavedListService.initProductQty(entry, Integer.valueOf(quantity));
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isListWithNameExist(final String wishListName, final boolean isAssembly)
	{
		return (null != siteoneSavedListService.getSavedListbyName(wishListName, isAssembly));
	}

	@Override
	public PriceData setItemTotal(final SavedListEntryData savedListEntryData)
	{
		BigDecimal totalPrice = new BigDecimal(0);
		if (BooleanUtils.isTrue(savedListEntryData.getProduct().getHideCSP())
				|| BooleanUtils.isTrue(savedListEntryData.getProduct().getInventoryFlag())
				|| (BooleanUtils.isTrue(savedListEntryData.getProduct().getInventoryCheck())
						&& BooleanUtils.isTrue(savedListEntryData.getProduct().getIsEligibleForBackorder())
						&& BooleanUtils.isTrue(categoryMatch(savedListEntryData.getProduct().getLevel2Category()))))
		{
			savedListEntryData.setHidePrice(true);
		}
		if (BooleanUtils.isTrue(savedListEntryData.getProduct().getInventoryCheck())
				&& BooleanUtils.isTrue(savedListEntryData.getProduct().getIsEligibleForBackorder())
				&& BooleanUtils.isTrue(categoryMatch(savedListEntryData.getProduct().getLevel2Category())))
		{
			// Total price will be zero
		}
		else if (savedListEntryData.getProduct().getCustomerPrice() != null)
		{
			totalPrice = savedListEntryData.getProduct().getCustomerPrice().getValue();
		}
		else if (savedListEntryData.getProduct().getPrice() != null)
		{
			totalPrice = savedListEntryData.getProduct().getPrice().getValue();
		}
		totalPrice = totalPrice.multiply(new BigDecimal(savedListEntryData.getQty()));
		savedListEntryData.setTotalPrice(createPrice(totalPrice, commonI18NService.getCurrentCurrency().getIsocode()));
		return savedListEntryData.getTotalPrice();
	}


	@Override
	public Boolean categoryMatch(final String level2Category)
	{
		final String[] categories = MORE_ON_THE_WAY_CATEGORIES.split(",");
		for (final String category : categories)
		{
			if (category.equalsIgnoreCase(level2Category))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public PriceData setListTotal(final List<SavedListEntryData> savedListEntries)
	{
		PriceData listtotalpricedata = null;
		if (savedListEntries != null)
		{
			BigDecimal listtotalPrice = new BigDecimal(0);
			for (final SavedListEntryData savedListEntryData : savedListEntries)
			{
				if (savedListEntryData.getTotalPrice() != null)
				{

					if (!BooleanUtils.isTrue(savedListEntryData.getHidePrice())
							&& (savedListEntryData.getProduct().getCustomerPrice() != null
									|| !BooleanUtils.isTrue(savedListEntryData.getProduct().getHideList())))

					{
						listtotalPrice = listtotalPrice.add(savedListEntryData.getTotalPrice().getValue());
					}
				}
			}
			listtotalpricedata = createPrice(listtotalPrice, commonI18NService.getCurrentCurrency().getIsocode());
		}
		return listtotalpricedata;
	}

	protected PriceData createPrice(final BigDecimal val, final String currencyIso)
	{
		return priceDataFactory.create(PriceDataType.BUY, val, currencyIso);
	}

	@Override
	public StringBuilder createSavedListFromUploadedCSV(final InputStream csvInputStream, final SavedListData savedListData)
			throws IOException
	{

		final StringBuilder listCodes = new StringBuilder();
		String listCode = null;
		final boolean isAssembly = false;
		final StringBuilder failedProducts = new StringBuilder();
		final StringBuilder products = new StringBuilder();
		final StringBuilder productNos = new StringBuilder();
		final List<String> duplicateProducts = new ArrayList<String>();
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvInputStream));
		try
		{
			final Stream<String> lines = bufferedReader.lines();
			lines.filter(line -> StringUtils.isNotBlank(line)).forEach(line -> {
				final String[] listAttributes = line.split(",");
				if (listAttributes.length == 2)
				{
					final String productCode = StringUtils.trim(listAttributes[SiteoneFacadesConstants.ZERO]);
					final String qty = StringUtils.trim(listAttributes[SiteoneFacadesConstants.ONE]);
					final boolean isDuplicate = duplicateProducts.stream().anyMatch(productCode::equalsIgnoreCase);


					if (!productCode.contains("ProductId") && !qty.contains("Quantity"))
					{
						if (!isDuplicate)
						{
							ProductModel productModel = siteOneProductService.getProductForList(productCode);
							if (productModel == null && NumberUtils.isNumber(productCode))
							{
								productModel = siteOneProductService.getProductByItemNumberWithZero(productCode);
							}
							if (productModel != null)
							{
								final String product = productModel.getCode() + "|" + qty;
								products.append(product + ",");
								final String productNo = productCode + "|" + qty;
								productNos.append(productNo + ",");
							}
							else
							{
								final String product = productCode + "|" + qty;
								failedProducts.append(product + ",");
							}
						}
						duplicateProducts.add(productCode);
					}
				}

			});
			if (checkifSavedListAlreadyExists(savedListData.getName(), false))
			{
				if (StringUtils.isNotEmpty(productNos.toString()))
				{
					listCode = getaddToWishlist(productNos.toString(), savedListData.getName());
				}
			}
			else
			{
				if (StringUtils.isNotEmpty(products.toString()) || StringUtils.isNotEmpty(failedProducts.toString()))
				{
					listCode = createSavedList(savedListData, products.toString(), isAssembly);
				}
			}
		}
		finally
		{
			bufferedReader.close();
		}

		listCodes.append(listCode);
		listCodes.append(";" + failedProducts);
		return listCodes;
	}

	@Override
	public StringBuilder createCartEntryFromUploadedCSV(final InputStream csvInputStream)
			throws IOException, CommerceCartModificationException
	{

		final StringBuilder listCodes = new StringBuilder();
		final StringBuilder failedProducts = new StringBuilder();
		final StringBuilder productNos = new StringBuilder();
		final List<String> duplicateProducts = new ArrayList<String>();
		final boolean maxProductExceeded = false;
		long count = 0L;

		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvInputStream));
		try
		{
			final Stream<String> lines = bufferedReader.lines();
			lines.filter(line -> StringUtils.isNotBlank(line)).forEach(line -> {
				final String[] listAttributes = line.split(",");
				if (listAttributes.length == 2)
				{
					final String productCode = StringUtils.trim(listAttributes[SiteoneFacadesConstants.ZERO]);
					final String qty = StringUtils.trim(listAttributes[SiteoneFacadesConstants.ONE]);
					final boolean isDuplicate = duplicateProducts.stream().anyMatch(productCode::equalsIgnoreCase);

					LOG.error("Inside import seperate");
					if (!productCode.contains("ProductId") && !qty.contains("Quantity"))
					{
						if (!isDuplicate)
						{
							//ProductModel productModel = siteOneProductService.getProductForList(productCode);
							final ProductData productdata = siteOneProductFacade.getProductBySearchTermForSearch(productCode,
									Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.CUSTOMER_PRICE,
											ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));

							if (productdata != null)
							{
								String productNo = productdata.getCode() + "|" + qty + "|null";
								if (productdata.getInventoryUPCID() != null && (!BooleanUtils.isTrue(productdata.getOutOfStockImage())))
								{
									productNo = productdata.getCode() + "|" + qty + "|" + productdata.getInventoryUPCID();
								}
								if (BooleanUtils.isTrue(productdata.getOutOfStockImage()))
								{
									failedProducts.append(productdata.getItemNumber() + "|" + "Not Available in Branch" + ",");
								}

								productNos.append(productNo + ",");
							}
							else
							{
								final String product = productCode + "|" + "Invalid item";
								failedProducts.append(product + ",");
							}
						}
						duplicateProducts.add(productCode);
					}
				}

			});
			if (StringUtils.isNotEmpty(productNos.toString()))
			{
				try
				{
					final List<String> products = new ArrayList<String>(Arrays.asList(productNos.toString().split(COMMA)));
					for (final String product : products)
					{
						final String arr[] = product.split(PIPE);
						final String code = arr[ZERO].trim();
						final String quantity = arr[ONE].trim();
						final String upcId = arr[TWO].trim();
						LOG.error("UPCID in cart " + upcId);
						if (null != upcId && !(upcId.equalsIgnoreCase("null")))
						{
							LOG.error("inside upc cartupload");
							final CartModificationData cartModification = siteOneCartFacade.addToCart(code, Long.valueOf(quantity),
									upcId, false, ((PointOfServiceData) sessionService.getAttribute("sessionStore")).getStoreId());
							count++;
						}
					}
				}
				catch (final CommerceCartModificationException e)
				{
					LOG.error(e);
				}
			}
		}
		finally
		{
			bufferedReader.close();
		}
		listCodes.append(productNos);
		listCodes.append(";" + failedProducts);
		if (count > 0)
		{
			listCodes.append(";" + count);
		}

		return listCodes;
	}

	public String getaddToWishlist(final String value, final String saveListName)
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(value.split(COMMA)));
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListbyName(saveListName, false);
		final List<UploadProductListErrorData> failedProducts = new ArrayList<>();
		for (final String product : products)
		{
			final String arr[] = product.split(PIPE);
			final String code = arr[ZERO].trim();
			final String quantity = arr[ONE].trim();
			WishlistAddData wishlistAddResponseData = new WishlistAddData();
			try
			{
				wishlistAddResponseData = addtoWishlist(code, quantity, savedListModel.getPk().toString(), false, null);
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.debug("Product Code not present: " + code);
				LOG.error(e.getMessage(), e);
			}
		}
		return savedListModel.getPk().toString();
	}

	@Override
	public String getUploadListErrorProducts(final String value)
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(value.split(COMMA)));
		final UploadListErrorInfoModel listErrorInfo = new UploadListErrorInfoModel();
		listErrorInfo.setErrorId(UUID.randomUUID().toString());
		final List<UploadErrorProductDetailModel> failedProducts = new ArrayList<>();
		for (final String product : products)
		{
			final String arr[] = product.split(PIPE);
			final UploadErrorProductDetailModel failedProduct = new UploadErrorProductDetailModel();
			failedProduct.setProductCode(arr[ZERO].trim());
			failedProduct.setQty(arr[ONE].trim());
			failedProduct.setErrorException("Product #SKU or ItemNumber not present in database. Please update the product.");
			failedProducts.add(failedProduct);
		}
		listErrorInfo.setErrorId(UUID.randomUUID().toString());
		listErrorInfo.setErrorData(failedProducts);
		modelService.save(listErrorInfo);
		modelService.refresh(listErrorInfo);

		return listErrorInfo.getErrorId();
	}

	@Override
	public SavedListData getListDataBasedOnCode(final String code)
	{
		final SavedListData savedListData = getDetailsPage(code);
		if (null != savedListData && !CollectionUtils.isEmpty(savedListData.getEntries()))
		{
			for (final SavedListEntryData entry : savedListData.getEntries())
			{
				updatePriceBasedOnUOM(entry);
				setItemTotal(entry);
			}
			savedListData.setListTotalPrice(setListTotal(savedListData.getEntries()));
		}
		return savedListData;
	}

	@Override
	public void updatePriceBasedOnUOM(final SavedListEntryData entryData)
	{
		if (null != entryData.getInventoryUom())
		{
			final InventoryUPCModel inventoryUPCModel = siteOneProductUOMService
					.getInventoryUOMById(entryData.getInventoryUom().toString());
			if (null != inventoryUPCModel)
			{
				final float inventoryMultiplier = inventoryUPCModel.getInventoryMultiplier();
				if (entryData.getProduct().getCustomerPrice() != null)
				{
					entryData.setInventoryUomDesc(inventoryUPCModel.getInventoryUPCDesc());
					final double uomPrice = commonI18NService.roundCurrency(
							entryData.getProduct().getCustomerPrice().getValue().floatValue() * inventoryMultiplier,
							CURRENCY_UNIT_PRICE_DIGITS);
					if (entryData.getProduct().getPrice() != null)
					{
						final double listuomPrice = commonI18NService.roundCurrency(
								entryData.getProduct().getPrice().getValue().floatValue() * inventoryMultiplier,
								CURRENCY_UNIT_PRICE_DIGITS);

						entryData.getProduct().getPrice().setValue(BigDecimal.valueOf(listuomPrice).setScale(2));
						entryData.getProduct().getPrice()
								.setFormattedValue("$".concat((entryData.getProduct().getPrice().getValue()).toString()));
						entryData.getProduct().getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(2));
						entryData.getProduct().getCustomerPrice()
								.setFormattedValue("$".concat((entryData.getProduct().getCustomerPrice().getValue()).toString()));
					}
					else
					{
						final PriceData priceData = new PriceData();
						priceData.setValue(BigDecimal.valueOf(uomPrice).setScale(2));
						entryData.getProduct().setPrice(priceData);
						entryData.getProduct().getPrice()
								.setFormattedValue("$".concat((entryData.getProduct().getPrice().getValue()).toString()));
						entryData.getProduct().getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(2));
						entryData.getProduct().getCustomerPrice()
								.setFormattedValue("$".concat((entryData.getProduct().getCustomerPrice().getValue()).toString()));
					}
				}
				else if (entryData.getProduct().getPrice() != null)
				{
					final double uomPrice = commonI18NService.roundCurrency(
							entryData.getProduct().getPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_UNIT_PRICE_DIGITS);
					entryData.getProduct().getPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(2));
					entryData.getProduct().getPrice()
							.setFormattedValue("$".concat((entryData.getProduct().getPrice().getValue()).toString()));
				}
			}
		}
	}


	protected void updateUOMIdForProducts(final String destinationListName, final String sourceWishlistCode)
	{
		final Wishlist2Model sourceList = siteoneSavedListService.getSavedListDetail(sourceWishlistCode);
		final Wishlist2Model destinationList = siteoneSavedListService.getSavedListbyName(destinationListName, false);
		siteoneSavedListService.updateUOMIdForProducts(sourceList, destinationList);
	}

	public List<CustomerPriceData> getPriceListForCustomer(final List<Wishlist2EntryModel> entries, final String branchRetailID)
	{
		final List<CustomerPriceData> customerPriceDataList = new ArrayList<>();
		final List<CustomerPriceData> retailPriceDataList = new ArrayList<>();
		final Map<String, String> productCodeWithStore = new HashMap<>();
		final Map<String, Integer> productCodeWithQunatity = new HashMap<>();
		final Map<String, String> productCodeWithUom = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> cspResponse = null;
		SiteOneWsPriceResponseData siteOneCspResponse = null;
		final List<String> productCodeList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(entries))
		{
			final int savedListLen = entries.size();
			for (int i = 0; i < savedListLen; i++)
			{
				final ProductData productData = productFacade.getProductForCodeAndOptions(entries.get(i).getProduct().getCode(),
						Arrays.asList(ProductOption.STOCK));
				productCodeList.add(productData.getCode());
				float inventoryMultiplier = 1.0f;
				String inventoryUpcId = null;
				if (null != entries.get(i).getUomId())
				{
					final InventoryUPCModel inventoryUPCModel = siteOneProductUOMService
							.getInventoryUOMById(entries.get(i).getUomId().toString());
					if (null != inventoryUPCModel)
					{
						inventoryMultiplier = inventoryUPCModel.getInventoryMultiplier();
						inventoryUpcId = inventoryUPCModel.getInventoryUPCID();
					}
				}
				final Integer qunatity = Integer.valueOf(Math.round(entries.get(i).getDesired() * inventoryMultiplier));
				productCodeWithQunatity.put(productData.getCode(), qunatity);
				productCodeWithUom.put(productData.getCode(), inventoryUpcId);
				if (null != storeSessionFacade.getSessionStore())
				{
					if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
							storeSessionFacade.getSessionStore().getStoreId()) && productData.getStock() != null)
					{
						if (StringUtils.isNotEmpty(productData.getStock().getFullfillmentStoreId()))
						{
							productCodeWithStore.put(productData.getCode(), productData.getStock().getFullfillmentStoreId());
						}
						else
						{
							productCodeWithStore.put(productData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
						}
					}
					else
					{
						productCodeWithStore.put(productData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
			}
			String storeId = null;
			if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
			{
				cspResponse = siteOneProductFacade.getCSPResponse(productCodeList, productCodeWithStore, productCodeWithQunatity,
						productCodeWithUom, branchRetailID);
				if (!cspResponse.isEmpty())
				{
					for (final Wishlist2EntryModel entry : entries)
					{
						storeId = productCodeWithStore.get(entry.getProduct().getCode());
						siteOneCspResponse = cspResponse.get(storeId);
						if (null != siteOneCspResponse && null != siteOneCspResponse.getPrices())
						{
							if (StringUtils.isNotEmpty(branchRetailID))
							{
								siteOneCspResponse.getPrices().forEach(csp -> {
									if (csp.getSkuId().equalsIgnoreCase(entry.getProduct().getCode()))
									{
										final CustomerPriceData customerPriceData = new CustomerPriceData();
										customerPriceData.setCode(entry.getProduct().getCode());
										customerPriceData.setPrice(csp.getPrice());
										retailPriceDataList.add(customerPriceData);
									}
								});
							}
							else
							{
								siteOneCspResponse.getPrices().forEach(csp -> {
									if (csp.getSkuId().equalsIgnoreCase(entry.getProduct().getCode()))
									{
										final CustomerPriceData customerPriceData = new CustomerPriceData();
										customerPriceData.setCode(entry.getProduct().getCode());
										customerPriceData.setPrice(csp.getPrice());
										customerPriceDataList.add(customerPriceData);
									}
								});
							}
						}
					}

				}
				if (StringUtils.isNotEmpty(branchRetailID))
				{
					return retailPriceDataList;
				}
			}
		}
		return customerPriceDataList;
	}

	@Override
	public Map<String, String> getCspCall(final List<Wishlist2EntryModel> entries)
	{
		final Map<String, String> productCodeWithStore = new HashMap<>();
		final Map<String, String> productCodeWithPrice = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> cspResponse = null;
		SiteOneWsPriceResponseData siteOneCspResponse = null;
		final List<String> productCodeList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(entries))
		{
			final int savedListLen = entries.size();
			for (int i = 0; i < savedListLen; i++)
			{
				final ProductData productData = productFacade.getProductForCodeAndOptions(entries.get(i).getProduct().getCode(),
						Arrays.asList(ProductOption.STOCK));
				productCodeList.add(productData.getCode());
				if (null != storeSessionFacade.getSessionStore())
				{
					if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
							storeSessionFacade.getSessionStore().getStoreId()) && productData.getStock() != null)
					{
						if (StringUtils.isNotEmpty(productData.getStock().getFullfillmentStoreId()))
						{
							productCodeWithStore.put(productData.getCode(), productData.getStock().getFullfillmentStoreId());
						}
						else
						{
							productCodeWithStore.put(productData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
						}
					}
					else
					{
						productCodeWithStore.put(productData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
			}
			String storeId = null;
			if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
			{
				cspResponse = siteOneProductFacade.getCSPResponseForUoms(productCodeList, productCodeWithStore, null, null);
				if (!cspResponse.isEmpty())
				{
					for (final Wishlist2EntryModel entry : entries)
					{
						storeId = productCodeWithStore.get(entry.getProduct().getCode());
						siteOneCspResponse = cspResponse.get(storeId);
						if (null != siteOneCspResponse && null != siteOneCspResponse.getPrices())
						{
							siteOneCspResponse.getPrices().forEach(csp -> {
								if (csp.getSkuId().equalsIgnoreCase(entry.getProduct().getCode()))
								{
									productCodeWithPrice.put(entry.getProduct().getCode(), csp.getPrice());
								}
							});

						}
					}

				}
			}
		}
		return productCodeWithPrice;
	}

	@Override
	public void setPriceForEntryData(final List<CustomerPriceData> customerPriceDataList,
			final List<SavedListEntryData> savedListEntryDataList, final List<CustomerPriceData> retailPriceDataList)
	{
		for (final SavedListEntryData savedListEntryData : savedListEntryDataList)
		{
			final ProductData productData = savedListEntryData.getProduct();
			if (!CollectionUtils.isEmpty(customerPriceDataList))
			{
				for (final CustomerPriceData customerPriceData : customerPriceDataList)
				{
					if (customerPriceData != null && productData.getCode().equals(customerPriceData.getCode()))
					{

						final String price = customerPriceData.getPrice();

						if (null != price)
						{
							final PriceData priceData = createPrice(new BigDecimal(price),
									commonI18NService.getCurrentCurrency().getIsocode());
							productData.setCustomerPrice(priceData);
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(retailPriceDataList))
			{
				for (final CustomerPriceData retailPriceData : retailPriceDataList)
				{
					if (retailPriceData != null && productData.getCode().equals(retailPriceData.getCode()))
					{

						final String price = retailPriceData.getPrice();

						if (null != price)
						{
							final PriceData priceData = createPrice(new BigDecimal(price),
									commonI18NService.getCurrentCurrency().getIsocode());
							productData.setPrice(priceData);
						}
					}
				}
			}
			final ProductModel productModel = productService.getProductForCode(productData.getCode());
			productConfiguredPopulator.populate(productModel, productData,
					Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.SUMMARY,
							ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES, ProductOption.REVIEW,
							ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.STOCK,
							ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE, ProductOption.DELIVERY_MODE_AVAILABILITY,
							ProductOption.DATA_SHEET, ProductOption.AVAILABILITY_MESSAGE));
			if(productData.getLevel1Category() != null && (productData.getLevel1Category().equalsIgnoreCase("Nursery")
						|| productData.getLevel1Category().equalsIgnoreCase("vivero")) && productData.getIsEligibleForBackorder() != null
						&& productData.getIsEligibleForBackorder())
			{
				productData.setInventoryCheck(Boolean.FALSE);
				productData.setIsSellable(Boolean.TRUE);
			}
			savedListEntryData.setProduct(productData);
		}
	}

	@Override
	public List<CustomerPriceData> updatePriceListBasedOnUOM(final SavedListData savedListData,
			final List<SavedListEntryData> savedListEntryDataList)
	{
		final List<CustomerPriceData> customerPriceDataList = savedListData.getCustomerPriceData();
		if (!CollectionUtils.isEmpty(customerPriceDataList))
		{
			for (final CustomerPriceData customerPriceData : customerPriceDataList)
			{
				for (final SavedListEntryData savedListEntryData : savedListEntryDataList)
				{
					if (customerPriceData != null && savedListEntryData.getProduct().getCode().equals(customerPriceData.getCode()))
					{
						customerPriceData.setPrice(savedListEntryData.getProduct().getCustomerPrice().getValue().toString());
					}
				}
			}
		}
		return customerPriceDataList;
	}

	@Override
	public SavedListData getCSPListDetailsPage(final String code, final List<String> productCodes)
	{
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListDetail(code);

		final SavedListData savedListData = new SavedListData();

		if (null != savedListModel)
		{
			final List<Wishlist2EntryModel> entries = new ArrayList<Wishlist2EntryModel>(savedListModel.getEntries());

			final List<Wishlist2EntryModel> newEntries = new ArrayList<Wishlist2EntryModel>();

			if (CollectionUtils.isNotEmpty(entries) && CollectionUtils.isNotEmpty(productCodes))
			{
				for (final Wishlist2EntryModel entry : entries)
				{
					for (final String productId : productCodes)
					{
						if (null != entry && null != entry.getProduct() && entry.getProduct().getCode().equalsIgnoreCase(productId))
						{
							if (Boolean.FALSE.equals(entry.getProduct().getIsProductOffline()))
							{
								newEntries.add(entry);
							}
						}
					}
				}
			}
			if (null != storeSessionFacade.getSessionStore()
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
							storeSessionFacade.getSessionStore().getStoreId())
					&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
			{

				savedListData.setRetailPriceData(
						getPriceListForCustomer(newEntries, storeSessionFacade.getSessionStore().getCustomerRetailId()));
			}
			savedListData.setCustomerPriceData(getPriceListForCustomer(newEntries, null));
		}
		return savedListData;
	}

	@Override
	public SavedListData getListDetailsPage(final String code)
	{
		return getListDataForDetailsPage(code, "cspDetailPage");
	}

	public SavedListData getListDataForDetailsPage(final String code, final String name)
	{
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListDetail(code);

		final SavedListData savedListData = new SavedListData();

		final List<String> removedProductNameList = new ArrayList<String>();


		if (null != savedListModel)
		{
			if (null != savedListModel.getCreatedBy()
					&& savedListModel.getCreatedBy().equalsIgnoreCase(userService.getCurrentUser().getUid()))
			{
				savedListData.setIsModified(Boolean.TRUE);
			}
			else
			{
				savedListData.setIsModified(Boolean.FALSE);
			}
			final List<Wishlist2EntryModel> entries = new ArrayList<Wishlist2EntryModel>(savedListModel.getEntries());

			final List<Wishlist2EntryModel> newEntries = new ArrayList<Wishlist2EntryModel>();
			String retailBranchId = null;
			if (CollectionUtils.isNotEmpty(entries))
			{
				for (final Wishlist2EntryModel entry : entries)
				{
					if (null != entry && null != entry.getProduct())
					{
						if (Boolean.FALSE.equals(entry.getProduct().getIsProductOffline()))
						{
							newEntries.add(entry);
						}
						else
						{
							removedProductNameList.add(entry.getProduct().getName());
						}
					}
				}

				savedListModel.setEntries(newEntries);
				modelService.save(savedListModel);
			}

			LOG.error("Entries Size" + entries.size());
			if (null != storeSessionFacade.getSessionStore()
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
							storeSessionFacade.getSessionStore().getStoreId())
					&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
			{
				retailBranchId = storeSessionFacade.getSessionStore().getCustomerRetailId();
			}
			if (null != name && name.equalsIgnoreCase("cspDetailPage"))
			{
				if (entries.size() < 50)
				{
					savedListData.setCustomerPriceData(getPriceListForCustomer(newEntries, null));
					if (StringUtils.isNotEmpty(retailBranchId))
					{
						savedListData.setRetailPriceData(getPriceListForCustomer(newEntries, retailBranchId));
					}
				}
			}
			else
			{
				savedListData.setCustomerPriceData(getPriceListForCustomer(newEntries, null));
				if (StringUtils.isNotEmpty(retailBranchId))
				{
					savedListData.setRetailPriceData(getPriceListForCustomer(newEntries, retailBranchId));
				}
			}


			siteoneSavedListConverter.convert(savedListModel, savedListData);
			updateUomIdForListEntriesWithLatestUOM(savedListModel, savedListData);
			savedListData.setRemovedProductNameList(removedProductNameList);
		}
		return savedListData;
	}

	@Override
	public SearchPageData<ListHeaderData> getRecentLists(final PageableData pageableData, final boolean isAssembly,
			final String sortOrder)
	{
		final SearchPageData<Wishlist2Model> savedListModel = siteoneSavedListService.getAllLists(pageableData, isAssembly,
				sortOrder);
		return this.convertPageData(savedListModel, this.siteoneHeaderListConverter);
	}

}
