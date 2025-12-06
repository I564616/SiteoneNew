package com.siteone.integration.translation.model;

import java.util.List;

public class GoogleTranslateRequest {
	private List<String> q;
	private String target;
	
	public GoogleTranslateRequest() {}
	
	public GoogleTranslateRequest(List<String> text, String targetLanguage) {
        this.q = text;
        this.target = targetLanguage;
    }
	
	
    
    public List<String> getQ() {
		return q;
	}

	public void setQ(List<String> q) {
		this.q = q;
	}

	public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    

}
