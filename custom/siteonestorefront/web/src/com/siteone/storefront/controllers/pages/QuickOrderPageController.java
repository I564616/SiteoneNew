/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.SiteOneProductWrapperData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 *
 */
@Controller
@RequestMapping(value = "/quickOrder")
public class QuickOrderPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(QuickOrderPageController.class);

	@Resource
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "userService")
	private UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping
	public String getQuickOrderPage(final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId("quickOrderPage"));
		model.addAttribute("quickOrderMinRows", Integer.valueOf(Config.getInt("siteonestorefront.quick.order.rows.min", 4)));
		model.addAttribute("quickOrderMaxRows", Integer.valueOf(Config.getInt("siteonestorefront.quick.order.rows.max", 25)));
		//model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("breadcrumb.quickOrder"));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.QuickOrder.QuickOrderPage;
	}

	@GetMapping(value = "/productInfo", produces = "application/json")
	@ResponseBody
	public SiteOneProductWrapperData getProductInfo(@RequestParam("code") final String code)
	{
		String errorMsg = null;
		final List<ProductData> productDataList = siteOneProductFacade.getProductsForQuickOrder(code, Arrays
				.asList(ProductOption.BASIC, ProductOption.CATEGORIES, ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE,
						ProductOption.PROMOTIONS, ProductOption.URL, ProductOption.VARIANT_MATRIX_BASE,
						ProductOption.VARIANT_MATRIX_URL, ProductOption.IMAGES, ProductOption.DESCRIPTION,
						ProductOption.AVAILABILITY_MESSAGE));
		siteOneProductFacade.updateParcelShippingForProducts(productDataList,
				storeSessionFacade.getSessionStore() != null ? storeSessionFacade.getSessionStore().getStoreId() : null);
		if (CollectionUtils.isNotEmpty(productDataList) && productDataList.size() == 1)
		{
			if (Config.getBoolean(SiteoneintegrationConstants.TRACK_RETAILCSP_ENABLE_KEY, true))
			{
				for (final ProductData productData : productDataList)
				{
					if (productData.getCustomerPrice() != null && productData.getPrice() != null
							&& productData.getCustomerPrice().getValue().compareTo(productData.getPrice().getValue()) == 1)
					{
						final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
						siteOneProductFacade.trackRetailCSPPrice(productData.getItemNumber(), b2bCustomer.getDefaultB2BUnit().getId(),
								b2bCustomer.getEmail(), productData.getPrice().getValue().toString(),
								productData.getCustomerPrice().getValue().toString(),
								storeSessionFacade.getSessionStore() != null ? storeSessionFacade.getSessionStore().getStoreId() : null);
					}
				}
			}
			final Boolean offlineProduct = productDataList.get(0).getIsProductOffline();
			if (null != offlineProduct && Boolean.TRUE.equals(offlineProduct))
			{
				errorMsg = getErrorMessage("text.quickOrder.product.not.found", null);
			}
			else if (Boolean.FALSE.equals(productDataList.get(0).getPurchasable()))
			{
				errorMsg = getErrorMessage("text.quickOrder.product.not.purchaseable", null);
			}

		}
		else if (CollectionUtils.isEmpty(productDataList))
		{
			errorMsg = getErrorMessage("text.quickOrder.product.not.found", null);
		}
		return createProductWrapperData(productDataList, errorMsg);
	}

	@GetMapping(value = "/getCustomerSpecificPrice", produces = "application/json")
	@ResponseBody
	public String getCustomerSpecificPrice(@RequestParam("code")
	final String code, @RequestParam(value = "quantity", required = false)
	final String quantity)
	{
		final String price = siteOneProductFacade.getCustomerSpecificPrice(code, Integer.valueOf(quantity));
		return price;
	}

	protected void logDebugException(final Exception ex)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug(ex);
		}
	}

	protected String getErrorMessage(final String messageKey, final Object[] args)
	{
		return getMessageSource().getMessage(messageKey, args, getI18nService().getCurrentLocale());
	}

	protected SiteOneProductWrapperData createProductWrapperData(final List<ProductData> productData, final String errorMsg)
	{
		final SiteOneProductWrapperData siteOneProductWrapperData = new SiteOneProductWrapperData();
		siteOneProductWrapperData.setProductDataList(productData);
		siteOneProductWrapperData.setErrorMsg(errorMsg);
		return siteOneProductWrapperData;
	}
}
