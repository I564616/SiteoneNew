package com.siteone.integration.services.carriers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.integration.carrier.data.SiteOneCarrierTrackingResponseData;

import de.hybris.platform.consignmenttrackingservices.delivery.data.ConsignmentEventData;


/**
 * Interface for consume Dispatch Track API
 *  
 * @author RP01944
 *
 */
public interface CarrierTrackingService {	
	
		/**
		 * Retrieves the consignment events from Dispatch Track API
		 * @param trackingId the tracking id of the consignment
		 * 
		 * @return list of consignment events data
		 */	
		List<ConsignmentEventData> getConsignmentEvents(final String trackingId);
		
}
