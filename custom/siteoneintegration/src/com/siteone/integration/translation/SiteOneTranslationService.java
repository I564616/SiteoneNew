package com.siteone.integration.translation;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.hybris.platform.commercefacades.product.data.ClassificationData;
import de.hybris.platform.commercefacades.product.data.ProductData;

public interface SiteOneTranslationService {

    boolean isTranslationEnabled();
	void translateProductFieldsAndFeatures(ProductData productData,  Collection<ClassificationData> classifications,
			String lang);
	Map<String, String> translateMultipleTexts(List<String> texts, String targetLanguage);
}
