package com.siteone.integration.watson;

import java.io.IOException;

//import org.codehaus.jackson.JsonParseException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.integration.customer.data.UserEmailData;

import de.hybris.platform.b2b.model.B2BCustomerModel;

public interface SiteOneUserPreferenceService {
	
	public UserEmailData fetchPreferenceData(final B2BCustomerModel customer ) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException;	
	
	public String saveEmailPreference(String emailType,String email,String emailTopicPreference, String orderPromo) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException;
	
	
}