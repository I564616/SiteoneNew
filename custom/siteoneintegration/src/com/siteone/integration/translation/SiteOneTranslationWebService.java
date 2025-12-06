package com.siteone.integration.translation;

import com.siteone.integration.translation.model.GoogleTranslateRequest;
import com.siteone.integration.translation.model.GoogleTranslateResponse;

public interface SiteOneTranslationWebService {
	
	GoogleTranslateResponse translateTextBatch(GoogleTranslateRequest request);

}
