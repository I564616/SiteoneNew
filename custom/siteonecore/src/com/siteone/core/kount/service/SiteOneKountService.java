/**
 *
 */
package com.siteone.core.kount.service;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.core.model.order.CartModel;

import java.net.UnknownHostException;

import com.kount.ris.Inquiry;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;


/**
 * @author TCS
 *
 */
public interface SiteOneKountService
{

	public String evaluateInquiry(final CartData cartData) throws Exception;

	public void setMerchantInformation(final Inquiry inquiry, final CartModel cartModel);

	public void setCustomerInformation(final Inquiry inquiry, final CartData cartData, final CartModel cartModel)
			throws UnknownHostException;

	public void setPurchaseInformation(final Inquiry inquiry, final CartModel cartModel);

	public void updateKountDetails(final String status, final String avsStatus, final String cvv, String orderNumber)
			throws Exception;

	public void updateKountDetails(String orderCode) throws Exception;

	public void updateUserKountDetails(final String orderCode, final String transactionId, final String kountSessionId);

	public String linkToPayEvaluateInquiry(final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData,
			final String kountSessionID);

	public void setLinkToPayCustomerInformation(final Inquiry inquiry,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData, final String kountSessionID)
			throws UnknownHostException;

	public void setLinkToPayPurchaseInformation(final Inquiry inquiry,
			final SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData);

	public SiteoneCCPaymentAuditLogModel getSiteoneCCAuditDetails(final String orderNumber);

	public SiteoneKountDataModel getKountInquiryCallDetails(final String orderNumber);


}
