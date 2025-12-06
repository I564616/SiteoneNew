package com.siteone.integration.translation.impl;

import com.siteone.facades.product.data.DataSheetData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.translation.SiteOneTranslationService;
import com.siteone.integration.translation.SiteOneTranslationWebService;
import com.siteone.integration.translation.model.GoogleTranslateRequest;
import com.siteone.integration.translation.model.GoogleTranslateResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import de.hybris.platform.commercefacades.product.data.ClassificationData;
import de.hybris.platform.commercefacades.product.data.FeatureData;
import de.hybris.platform.commercefacades.product.data.FeatureValueData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;

public class DefaultSiteOneTranslationService implements SiteOneTranslationService{

	  
    private static final Logger LOG = Logger.getLogger(DefaultSiteOneTranslationService.class);
    
    private SiteOneTranslationWebService translationWebService;
    
    
    @Override
    public Map<String, String> translateMultipleTexts(List<String> texts, String targetLanguage) {
        Map<String, String> translations = new HashMap<>();


        if (CollectionUtils.isEmpty(texts)) {
            return translations;
        }


        try {
            List<String> uniqueTexts = texts.stream()
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());


            GoogleTranslateRequest request = new GoogleTranslateRequest(uniqueTexts, targetLanguage);
            GoogleTranslateResponse response = translationWebService.translateTextBatch(request);
            LOG.error("Batch translation executed " + uniqueTexts.size());

            if (response != null && response.getData() != null) {
                List<GoogleTranslateResponse.Translation> translatedList = response.getData().getTranslations();


                for (int i = 0; i < uniqueTexts.size(); i++) {
                    String original = uniqueTexts.get(i);
                    String translated = translatedList.get(i).getTranslatedText();
                    translations.put(original, translated);
                    LOG.error("Original: " + original + " -> Translated: " + translated);
                }
            }
            

        } catch (Exception e) {
            LOG.error("Batch translation failed", e);


            // fallback
            for (String text : texts) {
                translations.put(text, text);
            }
        }
        


        return translations;
    }
    

    
    @Override
    public boolean isTranslationEnabled() {
        boolean flag = Config.getBoolean(SiteoneintegrationConstants.GOOGLE_TRANSLATE_ENABLED, false);
        LOG.error("flag " +flag );
        return flag;
    }
    

    
    @Override
    public void translateProductFieldsAndFeatures(ProductData productData,
                                                  Collection<ClassificationData> classifications,
                                                  String lang) {
        try {
            if (productData == null || StringUtils.isBlank(lang)) return;


            Map<String, String> originalMap = new LinkedHashMap<>();
            List<String> textsToTranslate = new ArrayList<>();


            // --- Product fields ---
			/*
			 * if (StringUtils.isNotBlank(productData.getName())) { String name =
			 * productData.getName().trim(); originalMap.put("name|" + name, name);
			 * textsToTranslate.add(name); }
			 */

            if (StringUtils.isNotBlank(productData.getProductLongDesc())) {
                String desc = productData.getProductLongDesc().replaceAll("&nbsp;", " ").trim();
                originalMap.put("longDesc|" + desc, desc);
                textsToTranslate.add(desc);
            }


            if (StringUtils.isNotBlank(productData.getFeatureBullets())) {
                String bullets = productData.getFeatureBullets().trim();
                originalMap.put("bullets|" + bullets, bullets);
                textsToTranslate.add(bullets);
            }


            // --- Brand label + value ---
            String brandLabel = "Brand Name";
            String brandValue = productData.getProductBrandName();


            if (StringUtils.isNotBlank(brandLabel)) {
                originalMap.put("brandLabel|" + brandLabel, brandLabel);
                textsToTranslate.add(brandLabel);
            }


            if (StringUtils.isNotBlank(brandValue)) {
                originalMap.put("brandValue|" + brandValue, brandValue);
                textsToTranslate.add(brandValue);
            }
            
            if (MapUtils.isNotEmpty(productData.getDataSheetList())) {
                for (Map.Entry<String, List<DataSheetData>> entry : productData.getDataSheetList().entrySet()) {
                    for (DataSheetData dataSheet : entry.getValue()) {

                        if (StringUtils.isNotBlank(dataSheet.getSds())) {
                            String sds = dataSheet.getSds().trim();
                            originalMap.put("datasheet.sds|" + sds, sds);
                            textsToTranslate.add(sds);
                        }

                        if (StringUtils.isNotBlank(dataSheet.getSdsLabel())) {
                            String sdsLabel = dataSheet.getSdsLabel().trim();
                            originalMap.put("datasheet.sdsLabel|" + sdsLabel, sdsLabel);
                            textsToTranslate.add(sdsLabel);
                        }
                    }
                }
            }


            // --- Classification feature names + values ---
            if (CollectionUtils.isNotEmpty(classifications)) {
                for (ClassificationData classification : classifications) {
                    if (classification != null && CollectionUtils.isNotEmpty(classification.getFeatures())) {
                        for (FeatureData feature : classification.getFeatures()) {
                            if (feature != null) {


                                // Feature name
                                if (StringUtils.isNotBlank(feature.getName())) {
                                    String featureName = feature.getName().trim();
                                    originalMap.put("featureName|" + featureName, featureName);
                                    textsToTranslate.add(featureName);
                                }


                                // Feature values
                                if (CollectionUtils.isNotEmpty(feature.getFeatureValues())) {
                                    for (FeatureValueData value : feature.getFeatureValues()) {
                                        if (value != null && StringUtils.isNotBlank(value.getValue())) {
                                            String val = value.getValue().trim();
                                            originalMap.put("featureVal|" + val, val);
                                            textsToTranslate.add(val);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            // --- Make batch translation call ---
            Map<String, String> translatedMap = translateMultipleTexts(textsToTranslate, lang);


            // --- Apply translations back ---


            // Product fields
            if (productData.getName() != null) {
                productData.setName(translatedMap.getOrDefault(productData.getName().trim(), productData.getName()));
            }


            if (productData.getProductLongDesc() != null) {
                productData.setProductLongDesc(
                    translatedMap.getOrDefault(productData.getProductLongDesc().replaceAll("&nbsp;", " ").trim(), productData.getProductLongDesc())
                );
            }


            if (productData.getFeatureBullets() != null) {
                productData.setFeatureBullets(
                    translatedMap.getOrDefault(productData.getFeatureBullets().trim(), productData.getFeatureBullets())
                );
            }


            // Brand value
            if (StringUtils.isNotBlank(brandValue)) {
                productData.setProductBrandName(
                    translatedMap.getOrDefault(brandValue.trim(), brandValue)
                );
            }
            if (MapUtils.isNotEmpty(productData.getDataSheetList())) {
                for (Map.Entry<String, List<DataSheetData>> entry : productData.getDataSheetList().entrySet()) {
                    for (DataSheetData dataSheet : entry.getValue()) {

                        if (StringUtils.isNotBlank(dataSheet.getSds())) {
                            String sds = dataSheet.getSds().trim();
                            String translated = translatedMap.getOrDefault(sds, sds);
                            dataSheet.setSds(translated);
                        }

                        if (StringUtils.isNotBlank(dataSheet.getSdsLabel())) {
                            String sdsLabel = dataSheet.getSdsLabel().trim();
                            String translated = translatedMap.getOrDefault(sdsLabel, sdsLabel);
                            dataSheet.setSdsLabel(translated);
                        }
                    }
                }
            }




            // Feature names + values
            for (ClassificationData classification : classifications) {
                if (classification != null && CollectionUtils.isNotEmpty(classification.getFeatures())) {
                    for (FeatureData feature : classification.getFeatures()) {
                        if (feature != null) {


                            // Feature name
                            if (StringUtils.isNotBlank(feature.getName())) {
                                String originalName = feature.getName().trim();
                                feature.setName(translatedMap.getOrDefault(originalName, originalName));
                            }


                            // Feature values
                            if (CollectionUtils.isNotEmpty(feature.getFeatureValues())) {
                                for (FeatureValueData value : feature.getFeatureValues()) {
                                    if (value != null && StringUtils.isNotBlank(value.getValue())) {
                                        String originalVal = value.getValue().trim();
                                        value.setValue(translatedMap.getOrDefault(originalVal, originalVal));
                                    }
                                }
                            }
                        }
                    }
                }
            }


            LOG.info("Batch translation executed for " + textsToTranslate.size() + " items");
            translatedMap.forEach((orig, trans) ->
                LOG.info("Original: " + orig + " -> Translated: " + trans)
            );


        } catch (Exception e) {
            LOG.error("Translation failed — using fallback values", e);
        }
    }
    
 
    public SiteOneTranslationWebService getTranslationWebService() {
        return translationWebService;
    }
    
    public void setTranslationWebService(SiteOneTranslationWebService translationWebService) {
        this.translationWebService = translationWebService;
    }
}
