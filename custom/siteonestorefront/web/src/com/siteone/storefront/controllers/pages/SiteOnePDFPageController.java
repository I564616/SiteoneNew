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

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import java.io.IOException;
import java.util.Optional;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.sds.SdsPdfFacade;


@Controller
@RequestMapping("/pdf")
public class SiteOnePDFPageController extends AbstractPageController
{
	private static final String RESOURCE_ID_PATH_VARIABLE_PATTERN = "{resourceId:.*}";
	private static final String SKU_ID_PATH_VARIABLE_PATTERN = "{skuId:.*}";
	private static final Logger LOG = Logger.getLogger(SiteOnePDFPageController.class);

	@Resource(name = "sdsPdfFacade")
	private SdsPdfFacade sdsPdfFacade;

	@GetMapping("/sdsPDF")
	public void getSDSPDF(@RequestParam("resourceId") final String resourceId, @RequestParam("skuId") final Optional<String>  skuId,
						  final HttpServletResponse response) {
		try
		{
	      String SkuNumber = skuId.isPresent()? skuId.get():null;

			final byte[] pdfContent = sdsPdfFacade.getPDFByResourceId(SkuNumber, resourceId);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			final String encodedResourceId = StringUtils.normalizeSpace(resourceId);
			response.setHeader("Content-Disposition", "inline; filename=SDS_OR_Label_" + encodedResourceId + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(pdfContent.length);
			response.getOutputStream().write(pdfContent);
			response.getOutputStream().flush();
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating PDF : " + e);
		}

	}

	/**
	 * @return the sdsPdfFacade
	 */
	public SdsPdfFacade getSdsPdfFacade()
	{
		return sdsPdfFacade;
	}

	/**
	 * @param sdsPdfFacade
	 *           the sdsPdfFacade to set
	 */
	public void setSdsPdfFacade(final SdsPdfFacade sdsPdfFacade)
	{
		this.sdsPdfFacade = sdsPdfFacade;
	}


}
