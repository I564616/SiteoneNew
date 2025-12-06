package com.siteone.integration.watson;

import java.io.IOException;

//import org.codehaus.jackson.JsonParseException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.facade.LeadGenarationData;
import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;

/**
 * @author 1190626
 *
 */
public interface EmailSignUpService {
	
	public String emailSignUp(final EmailSubscriptionsData emailSubscriptionsData) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException;


}
