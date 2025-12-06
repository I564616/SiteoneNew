package com.siteone.integration.translation.model;

import java.util.List;

public class GoogleTranslateResponse {

	 private Data data;
	    
	    public static class Data {
	        private List<Translation> translations;
	        
	        public List<Translation> getTranslations() { return translations; }
	        public void setTranslations(List<Translation> translations) { this.translations = translations; }
	    }
	    
	    public static class Translation {
	        private String translatedText;
	        private String detectedSourceLanguage;
	        
	        public String getTranslatedText() { return translatedText; }
	        public void setTranslatedText(String translatedText) { this.translatedText = translatedText; }
	        
	        public String getDetectedSourceLanguage() { return detectedSourceLanguage; }
	        public void setDetectedSourceLanguage(String detectedSourceLanguage) { 
	            this.detectedSourceLanguage = detectedSourceLanguage; 
	        }
	    }
	    
	    public Data getData() { return data; }
	    public void setData(Data data) { this.data = data; }
	    
	    // Utility method to easily get translated text
	    public String getTranslatedText() {
	        if (data != null && data.getTranslations() != null && !data.getTranslations().isEmpty()) {
	            return data.getTranslations().get(0).getTranslatedText();
	        }
	        return null;
	    }
}
