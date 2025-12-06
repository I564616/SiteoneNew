/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.siteone.core.model.BulkListUploadCronJobModel;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.wishlist.data.WishlistAddData;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;


/**
 * @author PP10513
 *
 */
public class BulkListUploadCronJob extends AbstractJobPerformable<BulkListUploadCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(BulkListUploadCronJob.class);
	private static String VARIANT_PRODUCT = "baseproduct";
	private static String HIDDEN_UPC = "No Unit Of Measure available for this product";
	private static String PRODUCT_NOT_FOUND = "Product not found";
	private static final String COMMA = ",";
	private static final String PIPE = "\\|";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	public static final String FILENAME = "BulkUploadErrorReport_";


	@Resource(name = "configurationService")
	private ConfigurationService configurationService;


	private SiteOneProductService siteOneProductService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListService")
	private SiteoneSavedListService siteoneSavedListService;

	@Resource(name = "blobDataImportService")
	private SiteOneBlobDataImportService blobDataImportService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@SuppressWarnings("boxing")
	@Override
	public PerformResult perform(final BulkListUploadCronJobModel bulkListUploadCronJobModel)
	{
		LOG.info("Inside Bulk List Upload cron job");

		final String bulkContainer = configurationService.getConfiguration().getString("bulk.files.upload.target.location");
		final String containerName = configurationService.getConfiguration().getString("azure.outbound.storage.container.name");
		try
		{
			final SavedListData savedListData = new SavedListData();

			final File filePath_products = blobDataImportService.readBlobToFile("ProductList.csv", bulkContainer, containerName);
			final File filePath_users = blobDataImportService.readBlobToFile("Users.csv", bulkContainer, containerName);

			final File errorFile = blobDataImportService.readBlobToFile("Error.csv", bulkContainer, containerName);

			final FileInputStream productStream = new FileInputStream(filePath_products);
			final FileInputStream userStream = new FileInputStream(filePath_users);


			final boolean isAssembly = false;
			final StringBuilder failedProducts = new StringBuilder();
			final StringBuilder products = new StringBuilder();
			final StringBuilder productNos = new StringBuilder();
			final List<String> duplicateProducts = new ArrayList<String>();
			final BufferedReader productReader = new BufferedReader(new InputStreamReader(productStream));
			final BufferedReader userReader = new BufferedReader(new InputStreamReader(userStream));

			final PrintWriter printWriter = new PrintWriter(errorFile);

			try
			{
				final String header = "UserId,ItemNumber,Error\n";
				printWriter.write(header);

				final Stream<String> users = userReader.lines();
				users.filter(user -> StringUtils.isNotBlank(user)).forEach(user -> {
					if (!user.contains("Email"))
					{

						if (!isValidUser(user))
						{
							final String msg = user + ",,User may be inactive or not present in databse.Please check the emailId. \n";
							printWriter.write(msg);
						}
						else
						{
							final String maxProductLimit = configurationService.getConfiguration()
									.getString("bulk.upload.file.max.products.limits", "100");
							final Long maxNum = Long.parseLong(maxProductLimit);
							final Stream<String> lines = productReader.lines();

							final Long noOfLines = lines.filter(StringUtils::isNotBlank).count();

							if (noOfLines > maxNum + 1)
							{
								final String msg = " , ,File exceeds maximum number of products limit. Please reduce number of products.\n";
								printWriter.write(msg);
							}
							else
							{

								String eachline;

								BufferedReader br = null;
								try
								{
									br = new BufferedReader(new FileReader(filePath_products));
								}
								catch (final IOException e)
								{
									LOG.error("FileNotFoundException Exception occurred in BulkListUploadCronJob ", e);
									LOG.error(e.getMessage(), e);
								}

								try
								{
									String listName = br.readLine();
									savedListData.setName(listName);
									savedListData.setDescription(listName);

									while ((eachline = br.readLine()) != null)
									{

										final String[] listAttributes = eachline.split(",");

										if (listAttributes.length == 2)
										{
											final String productCode = StringUtils.trim(listAttributes[ZERO]);
											final String qty = StringUtils.trim(listAttributes[ONE]);
											final boolean isDuplicate = duplicateProducts.stream().anyMatch(productCode::equalsIgnoreCase);

											if (!productCode.contains("ProductId") && !qty.contains("Quantity"))
											{
												if (!isDuplicate)
												{
													ProductModel productModel = siteOneProductService.getProductForListForBulk(productCode);

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
														final String msg = " ," + productCode
																+ ",Product #SKU or ItemNumber not present in database. Please update the product.\n";
														printWriter.write(msg);
														final String product = productCode + "|" + qty;
														failedProducts.append(product + ",");
													}
												}
												duplicateProducts.add(productCode);
											}
										}
									}

								}
								catch (final IOException e)
								{
									LOG.error("IOException Exception occurred in BulkListUploadCronJob ", e);
									LOG.error(e.getMessage(), e);
								}
							}
							if (isValidUser(user))
							{
								if (checkifSavedListAlreadyExists(savedListData.getName(), false, user))
								{
									if (StringUtils.isNotEmpty(productNos.toString()))
									{
										final String listCode = getaddToWishlist(productNos.toString(), savedListData.getName(), user);
									}
								}
								else
								{
									if (StringUtils.isNotEmpty(products.toString()) || StringUtils.isNotEmpty(failedProducts.toString()))
									{
										final String listCode = createSavedList(savedListData, products.toString(), isAssembly, user);
									}
								}
							}
						}
					}
				});
			}
			finally
			{
				productStream.close();
				userStream.close();
				productReader.close();
				userReader.close();
				printWriter.close();

			}

			blobDataImportService.writeBlob(errorFile, bulkContainer + FILENAME);
		}
		catch (final Exception e)
		{
			LOG.error("Exception occurred in Bulk List Upload Cron Job ", e);
			LOG.error(e.getMessage(), e);
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected boolean checkifSavedListAlreadyExists(final String name, final boolean isAssembly, final String user)
	{
		if (siteoneSavedListService.getSavedListbyNameForBulk(name, isAssembly, user) != null)
		{
			return true;
		}
		return false;
	}

	public String getaddToWishlist(final String value, final String saveListName, final String user)
	{
		final List<String> products = new ArrayList<String>(Arrays.asList(value.split(COMMA)));
		final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListbyNameForBulk(saveListName, false, user);
		for (final String product : products)
		{
			final String arr[] = product.split(PIPE);
			final String code = arr[ZERO].trim();
			final String quantity = arr[ONE].trim();

			try
			{
				addtoWishlist(code, quantity, savedListModel.getPk().toString(), false, null);
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.debug("Product Code not present: " + code);
				LOG.error(e.getMessage(), e);
			}
		}
		return savedListModel.getPk().toString();
	}

	public WishlistAddData addtoWishlist(final String productCode, final String quantity, final String wishListCode,
			final boolean prodQtyFlag, String inventoryUOMId)
	{

		final WishlistAddData wishlistAddData = new WishlistAddData();
		final ProductModel productModel = siteOneProductService.getProductForListForBulk(productCode);
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
		return wishlistAddData;
	}


	public String createSavedList(final SavedListData savedListData, final String productCodes, final boolean isAssembly,
			final String user)
	{
		if (!checkifSavedListAlreadyExists(savedListData.getName(), isAssembly, user))
		{
			final Wishlist2Model savedListModel = new Wishlist2Model();
			savedListModel.setName((StringUtils.isEmpty(savedListData.getName())? "BulkList" : savedListData.getName()));
			savedListModel.setDescription((StringUtils.isEmpty(savedListData.getDescription())? "Bulk List Description" : savedListData.getDescription()));
			return siteoneSavedListService.createSavedListForBulk(savedListModel, productCodes, isAssembly, user);
		}

		return null;
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


	private boolean isValidUser(final String user)
	{
		boolean activeFlag = false;

		UserModel userModel = null;
		try
		{
			userModel = userService.getUserForUID(user);
		}
		catch (final Exception e)
		{
			LOG.error("Exception occurred in Bulk List Upload Cron Job ", e);
			LOG.error(e.getMessage(), e);
		}

		if (userModel instanceof B2BCustomerModel)
		{
			activeFlag = ((B2BCustomerModel) userModel).getActive().booleanValue();
		}

		return activeFlag;
	}

}
