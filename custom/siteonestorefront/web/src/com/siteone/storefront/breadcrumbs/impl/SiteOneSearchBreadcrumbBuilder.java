/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.SearchBreadcrumbBuilder;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;


/**
 * @author 1219341
 *
 */
public class SiteOneSearchBreadcrumbBuilder extends SearchBreadcrumbBuilder
{

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

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


	private static final String LAST_LINK_CLASS = "active";
	private static final String SITEONE_ROOT_CATEGORY = "sales.hierarchy.root.category";

	@Override
	public List<Breadcrumb> getBreadcrumbs(final String categoryCode, final String searchText, final boolean emptyBreadcrumbs)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		if (categoryCode == null)
		{
			final Breadcrumb breadcrumb = new Breadcrumb("#",
					getMessageSource().getMessage("pickup.find.search", null, getI18nService().getCurrentLocale()),
					emptyBreadcrumbs ? LAST_LINK_CLASS : "");
			breadcrumbs.add(breadcrumb);

			final Breadcrumb breadcrumbForSearchText = new Breadcrumb("/search?text=" + getEncodedUrl(searchText),
					getMessageSource().getMessage("results.for", null, getI18nService().getCurrentLocale()) + '"' + searchText + '"',
					emptyBreadcrumbs ? LAST_LINK_CLASS : "");
			breadcrumbs.add(breadcrumbForSearchText);
		}
		else
		{
			createBreadcrumbCategoryHierarchyPath(categoryCode, emptyBreadcrumbs, breadcrumbs);
		}
		return breadcrumbs;
	}

	@Override
	protected void createBreadcrumbCategoryHierarchyPath(final String categoryCode, final boolean emptyBreadcrumbs,
			final List<Breadcrumb> breadcrumbs)
	{
		// Create category hierarchy path for breadcrumb
		final List<Breadcrumb> categoryBreadcrumbs = new ArrayList<>();
		final Collection<CategoryModel> categoryModels = new ArrayList<>();
		final CategoryModel lastCategoryModel = getCommerceCategoryService().getCategoryForCode(categoryCode);
		categoryBreadcrumbs.add(getCategoryBreadcrumb(lastCategoryModel));
		categoryModels.addAll(lastCategoryModel.getSupercategories());

		while (!categoryModels.isEmpty())
		{
			final CategoryModel categoryModel = categoryModels.iterator().next();
			final String rootCategoryCode = configurationService.getConfiguration().getString(SITEONE_ROOT_CATEGORY);

			if (!(categoryModel instanceof ClassificationClassModel) && !(categoryModel instanceof VariantCategoryModel)
					&& !(categoryModel instanceof VariantValueCategoryModel)
					&& !(rootCategoryCode.equalsIgnoreCase(categoryModel.getCode())))

			{
				categoryBreadcrumbs.add(getCategoryBreadcrumb(categoryModel));
				categoryModels.clear();
				categoryModels.addAll(categoryModel.getSupercategories());
			}
			else
			{
				categoryModels.remove(categoryModel);
			}
		}
		categoryBreadcrumbs.add(getAllProductBreadcrumb());
		Collections.reverse(categoryBreadcrumbs);
		breadcrumbs.addAll(categoryBreadcrumbs);
	}

	protected Breadcrumb getAllProductBreadcrumb()
	{
		return new Breadcrumb("#", getMessageSource().getMessage("siteOneProductBreadCrumbBuilder.allProducts", null,
				getI18nService().getCurrentLocale()), "");
	}


}
