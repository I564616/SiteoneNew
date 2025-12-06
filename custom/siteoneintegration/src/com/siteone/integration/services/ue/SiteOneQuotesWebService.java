package com.siteone.integration.services.ue;

import java.util.List;

import com.siteone.integration.order.data.QuoteApprovalHistoryResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoRequestData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoResponseData;
import com.siteone.integration.quotes.order.data.SiteOneQuotesListResponseData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesRequestData;
import com.siteone.integration.order.data.SiteOneRequestQuoteRequestData;
import com.siteone.integration.order.data.SiteOneRequestQuoteResponseData;

public interface SiteOneQuotesWebService {

	SiteOneQuoteDetailResponseData getQuotesDetails(boolean isNewBoomiEnv, String quoteHeaderID);
	
	List<SiteOneQuotesListResponseData> getQuotes(SiteoneQuotesRequestData siteoneQuotesRequestData);	
	
    SiteOneRequestQuoteResponseData updateRequestQuote(SiteOneRequestQuoteRequestData siteoneQuotesRequestData);
    
    void updateQuote(SiteOneQuoteDetailResponseData quoteDetails, String quoteHeaderID);
    
    void updateQuoteDetail(SiteOneQuoteDetailResponseData quoteDetails, String quoteHeaderID);
   
    List<SiteOneQuoteShiptoResponseData> shiptoQuote(SiteOneQuoteShiptoRequestData siteOneQuoteShiptoRequestData);

	List<QuoteApprovalHistoryResponseData> quoteApprovalHistory(boolean isNewBoomiEnv, String quoteDetailID);

    
}
