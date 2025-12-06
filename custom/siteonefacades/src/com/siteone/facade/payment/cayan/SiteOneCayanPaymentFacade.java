/**
 *
 */
package com.siteone.facade.payment.cayan;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;

import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.CaptureResponse;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;


/**
 * @author pelango
 *
 */
public interface SiteOneCayanPaymentFacade
{

	public BoardCardResponse boardCard(final String authToken);

	public String unboardCard(String vaultToken);

	public String updateBoardedCard(String vaultToken, String expDate);

	public AuthorizeResponse cayanAuthorize(final String vaultToken, final CartData cartData);

	public SaleResponse cayanSale(final String vaultToken, final CartData cartData, final String orderCode);

	public CaptureResponse cayanCapture(final String vaultToken, final ConsignmentModel consignment);
}
