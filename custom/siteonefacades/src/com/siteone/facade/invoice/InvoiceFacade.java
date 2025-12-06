/**
 *
 */
package com.siteone.facade.invoice;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.Date;
import java.util.List;

import com.siteone.core.enums.InvoiceStatus;
import com.siteone.facade.InvoiceData;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;


/**
 * @author 1219341
 *
 */
public interface InvoiceFacade
{

	InvoiceData getInvoiceDetailsForCode(String invoiceNumber, String uid, String orderShipmentActualId, Boolean fromEmail);

	InvoiceData getInvoiceShipmentActualId(String invoiceNumber, String uid);

	/**
	 * @param pageableData
	 * @param shipToId
	 * @param statuses
	 * @return
	 */
	SearchPageData<InvoiceData> getPagedInvoiceListForStatuses(PageableData pageableData, String shipToId,
			String trimmedSearchParam, Date dateFromFinal, Date dateToFinal, InvoiceStatus... statuses);

	SearchPageData<InvoiceData> getPagedInvoiceListForStatusesForAll(PageableData pageableData, String shipToId,
			String trimmedSearchParam, Date dateFromFinal, Date dateToFinal, InvoiceStatus... statuses);

	SearchPageData<InvoiceData> getListOfInvoiceData(SiteoneInvoiceRequestData invoiceRequest, String customerNumber);

	byte[] getInvoiceByOrderShipmentActualId(String orderShipmentActualId)
			throws PdfNotAvailableException, ServiceUnavailableException;

	/**
	 * @param invoiceRequest
	 * @param customerNumber
	 * @return
	 */
	List<InvoiceData> getDownloadListOfInvoiceData(SiteoneInvoiceRequestData invoiceRequest, String customerNumber);

}
