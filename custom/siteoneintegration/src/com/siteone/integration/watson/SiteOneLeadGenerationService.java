package com.siteone.integration.watson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.facade.LeadGenarationData;

/**
 * @author 1190626
 *
 */

public interface SiteOneLeadGenerationService {
	
	public String saveLeadGenerationData(final LeadGenarationData leadGenarationData) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException;

}
