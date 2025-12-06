package com.siteone.integration.services.cayanclient;

import com.siteone.integration.webserviceclient.cayanclient.Authorize;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCard;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.Capture;
import com.siteone.integration.webserviceclient.cayanclient.CaptureResponse;
import com.siteone.integration.webserviceclient.cayanclient.Sale;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCard;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCard;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCardResponse;

public interface SiteOneCayanPaymentWebService {

	public BoardCardResponse boardCard(final BoardCard boardCard);
	
	public UnboardCardResponse cayanUnboardCard(final UnboardCard unboardCard);

	public UpdateBoardedCardResponse cayanUpdateBoardedCard(final UpdateBoardedCard updateBoardedCard);
	
	public AuthorizeResponse cayanAuthorize(final Authorize authorize);
	
	public SaleResponse cayanSale(final Sale sale);
	
	public CaptureResponse cayanCapture(final Capture capture);
}
