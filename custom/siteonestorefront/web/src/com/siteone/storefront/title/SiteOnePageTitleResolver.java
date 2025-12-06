/**
 *
 */
package com.siteone.storefront.title;

import de.hybris.platform.acceleratorservices.storefront.util.PageTitleResolver;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import com.siteone.core.cms.service.SiteOneCMSPageService;


/**
 * @author 1091124
 *
 */
public class SiteOnePageTitleResolver extends PageTitleResolver
{
	private ProductService productService;
	private CommerceCategoryService commerceCategoryService;
	private CMSSiteService cmsSiteService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

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

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	private String primaryHierarchy;

	@Override
	protected CommerceCategoryService getCommerceCategoryService()
	{
		return commerceCategoryService;
	}

	@Override
	public void setCommerceCategoryService(final CommerceCategoryService commerceCategoryService)
	{
		this.commerceCategoryService = commerceCategoryService;
	}

	@Override
	protected ProductService getProductService()
	{
		return productService;
	}

	@Override
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	@Override
	protected CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}

	@Override
	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	/**
	 * @return the primaryHierarchy
	 */
	public String getPrimaryHierarchy()
	{
		return primaryHierarchy;
	}

	/**
	 * @param primaryHierarchy
	 *           the primaryHierarchy to set
	 */
	public void setPrimaryHierarchy(final String primaryHierarchy)
	{
		this.primaryHierarchy = primaryHierarchy;
	}


	@Override
	public String resolveContentPageTitle(final String title)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();

		final StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(title))
		{
			builder.append(title);
		}
		else
		{
			builder.append(currentSite.getName());
		}
		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	@Override
	public String resolveHomePageTitle(final String title)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(title))
		{
			builder.append(title);
		}
		else
		{
			builder.append(currentSite.getName());
		}

		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	@Override
	public <STATE> String resolveSearchPageTitle(final String searchText, final List<BreadcrumbData<STATE>> appliedFacets)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();

		final StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(searchText))
		{
			builder.append(searchText).append(TITLE_WORD_SEPARATOR);
		}
		for (final BreadcrumbData pathElement : appliedFacets)
		{
			builder.append(pathElement.getFacetValueName()).append(TITLE_WORD_SEPARATOR);
		}
		builder.append(currentSite.getName());
		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	@Override
	public String resolveCategoryPageTitle(final CategoryModel category)
	{
		final StringBuilder stringBuilder = new StringBuilder();

		if (category.getCode().length() == 4)
		{
			getCategoryPageTitleForL1(category, stringBuilder);
		}
		else
		{
			getPageTitleFromCategoryModel(category, stringBuilder);
		}

		return StringEscapeUtils.escapeHtml4(stringBuilder.toString());
	}

	/**
	 * creates page title for given code and facets
	 */
	@Override
	public <STATE> String resolveCategoryPageTitle(final String categoryCode, final List<BreadcrumbData<STATE>> appliedFacets)
	{
		final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		return resolveCategoryPageTitle(category, appliedFacets);
	}

	/**
	 * creates page title for given code
	 */
	@Override
	public String resolveProductPageTitle(final ProductModel product)
	{
		// Lookup site (or store)
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		// Construct page Meta title
		if (product.getPageTitle() != null && !product.getPageTitle().isEmpty())
		{
			builder.append(product.getPageTitle());
		}
		else if (null != product.getProductShortDesc())
		{
			String fullTitle = getProductTitle(product);

			if (fullTitle != null && !fullTitle.isEmpty())

			{
				if (fullTitle.endsWith("."))
				{
					fullTitle = fullTitle.substring(0, fullTitle.length() - 1);
				}

				builder.append(fullTitle);
			}
			builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		}
		else
		{
			builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		}

		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	protected final CategoryModel getL2CategoryForProduct(final ProductModel product)
	{
		final Collection<CategoryModel> leafLevelCategories = product.getSupercategories();
		final CategoryModel category1 = getPrimaryCategory(leafLevelCategories);

		if (null != category1)
		{
			if (!isLevel1Category(category1.getSupercategories()))
			{
				final CategoryModel category2 = getPrimaryCategory(category1.getSupercategories());

				if (!isLevel1Category(category2.getSupercategories()))
				{
					final CategoryModel category3 = getPrimaryCategory(category2.getSupercategories());

					if (!isLevel1Category(category3.getSupercategories()))
					{
						final CategoryModel category4 = getPrimaryCategory(category3.getSupercategories());

						if (!isLevel1Category(category4.getSupercategories()))
						{
							return category3;
						}
						else
						{
							return category3;
						}
					}
					else
					{
						return category2;
					}
				}
				else
				{
					return category1;
				}
			}
			else
			{
				return category1;
			}
		}
		else
		{
			return null;
		}

	}


	protected CategoryModel getPrimaryCategory(final Collection<CategoryModel> supercategories)
	{
		for (final CategoryModel category : supercategories)
		{
			if (!(category instanceof ClassificationClassModel) && !(category instanceof VariantCategoryModel)
					&& !(category instanceof VariantValueCategoryModel) && category.getCode().startsWith("SH"))
			{
				return category;
			}
		}
		return null;
	}

	protected boolean isLevel1Category(final Collection<CategoryModel> supercategories)
	{
		boolean isPartOfPrimaryHierarchy = false;
		for (final CategoryModel superCategory : supercategories)
		{
			if (Config.getString("primary.hierarchy.root.category", null).equalsIgnoreCase(superCategory.getCode()))
			{
				isPartOfPrimaryHierarchy = true;
				break;
			}
		}
		return isPartOfPrimaryHierarchy;
	}

	private final String getProductTitle(final ProductModel product)
	{
		final String value = product.getProductShortDesc();
		if (value == null && product instanceof VariantProductModel)
		{
			final ProductModel baseProduct = ((VariantProductModel) product).getBaseProduct();
			if (baseProduct != null)
			{
				return baseProduct.getProductShortDesc();
			}
		}
		return value;
	}

	@Override
	public final String resolveProductPageTitle(final String productCode)
	{
		// Lookup the product
		final ProductModel product = getProductService().getProductForCode(productCode);
		return resolveProductPageTitle(product);
	}

	public final String resolveStoreDirectoryCityPageTitle(final String cityName)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		final String title = getMessageSource().getMessage("city.state.Page.Title", null, getI18nService().getCurrentLocale());
		builder.append(title).append(" ");
		if (!StringUtils.isEmpty(cityName))
		{
			builder.append(cityName);
		}
		builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	public final String resolveStoreDirectoryStatePageTitle(final String stateName)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		final String title = getMessageSource().getMessage("city.state.Page.Title", null, getI18nService().getCurrentLocale());
		builder.append(title).append(" ");
		if (!StringUtils.isEmpty(stateName))
		{
			builder.append(stateName);
		}
		builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	@Override
	protected final List<CategoryModel> getCategoryPath(final ProductModel product)
	{
		final CategoryModel category = getPrimaryCategoryForProduct(product);
		if (category != null)
		{
			return getCategoryPath(category);
		}
		return Collections.emptyList();
	}

	@Override
	protected final List<CategoryModel> getCategoryPath(final CategoryModel category)
	{
		final Collection<List<CategoryModel>> paths = getCommerceCategoryService().getPathsForCategory(category);
		// Return first - there will always be at least 1
		final List<CategoryModel> cat2ret = paths.iterator().next();
		Collections.reverse(cat2ret);
		return cat2ret;
	}

	@Override
	protected final CategoryModel getPrimaryCategoryForProduct(final ProductModel product)
	{
		final String displayRootCategoryCode = Config.getString("display.hierarchy.root.category", null);
		// Get the first super-category from the product that isn't a classification category
		for (final CategoryModel category : product.getSupercategories())
		{
			if (!category.getCode().startsWith(displayRootCategoryCode) && (!(category instanceof ClassificationClassModel)
					&& !(category instanceof VariantCategoryModel) && !(category instanceof VariantValueCategoryModel)))
			{
				return category;
			}
		}
		return null;
	}

	public String resolveLocationPagesTitle(final PointOfServiceData pointOfServiceData, final Model model)
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		final String title = getMessageSource().getMessage("locationPages.Title", null, getI18nService().getCurrentLocale());
		builder.append(title).append(" ");
		if (null != pointOfServiceData && null != pointOfServiceData.getAddress())
		{
			builder.append(pointOfServiceData.getStoreId()).append("# ")
					.append(pointOfServiceData.getAddress().getTown()).append(", ")
					.append(pointOfServiceData.getAddress().getRegion().getIsocodeShort());
		}
		builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	public String resolveAddressBookPageTitle()
	{
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();
		final StringBuilder builder = new StringBuilder();
		final String addressBookPageTitle = getMessageSource().getMessage("billingaddress.Title", null,
				getI18nService().getCurrentLocale());
		builder.append(addressBookPageTitle);
		builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		/*
		 * if (!StringUtils.isEmpty(title)) { builder.append(TITLE_WORD_SEPARATOR).append(title); }
		 */

		return StringEscapeUtils.escapeHtml4(builder.toString());
	}

	/**
	 * @param category
	 * @param stringBuilder
	 * @param categoryTitle
	 */
	public void getPageTitleFromCategoryModel(final CategoryModel category, final StringBuilder stringBuilder)
	{
		final String categoryTitle = getMessageSource().getMessage("category.Title", null, getI18nService().getCurrentLocale());
		final CMSSiteModel currentSite = getCmsSiteService().getCurrentSite();

		if (category.getPageTitle() != null && !category.getPageTitle().isEmpty())
		{
			stringBuilder.append(category.getPageTitle());
		}
		else
		{
			stringBuilder.append(category.getName());
			stringBuilder.append(" ");
			stringBuilder.append(categoryTitle);
			stringBuilder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
		}
	}

	public void getCategoryPageTitleForL1(final CategoryModel category, final StringBuilder stringBuilder)
	{
		try
		{
			final AbstractPageModel cmsPage = getCategoryLandingPage(category);
			if ((cmsPage.getTitle() != null && !cmsPage.getTitle().isEmpty()))
			{
				stringBuilder.append(cmsPage.getTitle());
			}
			else
			{
				getPageTitleFromCategoryModel(category, stringBuilder);
			}
		}

		catch (final CMSItemNotFoundException e)
		{
			getPageTitleFromCategoryModel(category, stringBuilder);
		}
	}

	protected ContentPageModel getCategoryLandingPage(final CategoryModel category) throws CMSItemNotFoundException
	{
		return ((SiteOneCMSPageService) cmsPageService).getCategoryLandingPage(category);
	}

}
