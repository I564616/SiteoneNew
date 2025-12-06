/**
 *
 */
package com.siteone.facades.sds;

import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;


/**
 * @author VenkatB
 *
 */
public interface SdsPdfFacade
{

	byte[] getPDFByResourceId(String skuId, String resourceId) throws PdfNotAvailableException, ServiceUnavailableException;

}
