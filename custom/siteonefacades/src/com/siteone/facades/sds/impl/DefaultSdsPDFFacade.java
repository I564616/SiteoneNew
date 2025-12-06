/**
 *
 */
package com.siteone.facades.sds.impl;

import jakarta.annotation.Resource;

import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.sds.SdsPdfFacade;
import com.siteone.integration.services.ue.SiteOneSDSPDFWebService;
import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;



/**
 * @author VenkatB
 *
 */
public class DefaultSdsPDFFacade implements SdsPdfFacade
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSdsPDFFacade.class);

	@Resource(name = "siteOneSDSPDFWebService")
	private SiteOneSDSPDFWebService siteOneSDSPDFWebService;

	private static final Logger LOG = Logger.getLogger(DefaultSdsPDFFacade.class);

	@Override
	public byte[] getPDFByResourceId(final String skuId, final String resourceId) throws PdfNotAvailableException, ServiceUnavailableException
	{
		try
		{
			final byte[] pdf = siteOneSDSPDFWebService.getPDFByResourceId(skuId, resourceId);

			if (null == pdf)
			{
				throw new PdfNotAvailableException("Pdf Byte Array is null");
			}
			return pdf;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to read pdf", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}
	}
}
