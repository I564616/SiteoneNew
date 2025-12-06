package com.siteone.integration.services.cayanclient;

import com.siteone.integration.webserviceclient.cayantransportclient.CreateTransaction;

public interface SiteOneCayanTransportWebService {

	public String getTransportKey(CreateTransaction createTransaction);
	
}
