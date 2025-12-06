/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.ProductBreadcrumbBuilder;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;


/**
 * @author 1124932
 *
 */
public class SiteOneProductBreadCrumbBuilder extends ProductBreadcrumbBuilder
{
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	private static final String SALES_SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";
	private static final String DISPLAY_SITEONE_ROOT_CATEGORY = "display.hierarchy.root.category";

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

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

	@Override
	public List<Breadcrumb> getBreadcrumbs(final String productCode)
	{
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Collection<CategoryModel> categoryModels = new ArrayList<>();

		final ProductModel baseProductModel = getBaseProduct(productModel);
		categoryModels.addAll(baseProductModel.getSupercategories());


		while (!categoryModels.isEmpty())
		{

			final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);

			CategoryModel toDisplay = null;
			toDisplay = processCategoryModels(categoryModels, toDisplay);
			categoryModels.clear();
			if (toDisplay != null && !salesRootCategoryCode.equalsIgnoreCase(toDisplay.getCode()))
			{
				breadcrumbs.add(getCategoryBreadcrumb(toDisplay));
				categoryModels.addAll(toDisplay.getSupercategories());
			}
		}
		Collections.reverse(breadcrumbs);
		breadcrumbs.add(0, new Breadcrumb("#", getMessageSource().getMessage("siteOneProductBreadCrumbBuilder.allProducts", null,
				getI18nService().getCurrentLocale()), null));
		return breadcrumbs;
	}

	@Override
	protected CategoryModel processCategoryModels(final Collection<CategoryModel> categoryModels, final CategoryModel toDisplay)
	{
		CategoryModel categoryToDisplay = toDisplay;
		final String displayRootCategoryCode = configurationService.getConfiguration().getString(DISPLAY_SITEONE_ROOT_CATEGORY);

		for (final CategoryModel categoryModel : categoryModels)
		{
			if ((!categoryModel.getCode().startsWith(displayRootCategoryCode))
					&& (!(categoryModel instanceof ClassificationClassModel) && !(categoryModel instanceof VariantCategoryModel)
							&& !(categoryModel instanceof VariantValueCategoryModel)))
			{
				if (categoryToDisplay == null)
				{
					categoryToDisplay = categoryModel;
				}
				if (getBrowseHistory().findEntryMatchUrlEndsWith(categoryModel.getCode()) != null)
				{
					break;
				}
			}
		}
		return categoryToDisplay;
	}
	
	public List<String> getFullPagePath(final String productCode, final CategoryModel category)
	{
		List<String> fullPagePath = new ArrayList<String>();
		final Collection<CategoryModel> categoryModels = new ArrayList<>();
		if(!ObjectUtils.isEmpty(productCode))
		{
			final ProductModel productModel = getProductService().getProductForCode(productCode);
			final ProductModel baseProductModel = getBaseProduct(productModel);
			categoryModels.addAll(baseProductModel.getSupercategories());
		}
		else
		{
			categoryModels.addAll(category.getSupercategories());
		}
		

		while (!categoryModels.isEmpty())
		{

			final String salesRootCategoryCode = configurationService.getConfiguration().getString(SALES_SITEONE_ROOT_CATEGORY);

			CategoryModel toDisplay = null;
			toDisplay = processCategoryModels(categoryModels, toDisplay);
			categoryModels.clear();
			if (toDisplay != null && !salesRootCategoryCode.equalsIgnoreCase(toDisplay.getCode()))
			{
				fullPagePath.add(toDisplay.getName(Locale.ENGLISH).toLowerCase());
				categoryModels.addAll(toDisplay.getSupercategories());
			}
		}
		Collections.reverse(fullPagePath);		
		
		return fullPagePath;
	}
	
}
