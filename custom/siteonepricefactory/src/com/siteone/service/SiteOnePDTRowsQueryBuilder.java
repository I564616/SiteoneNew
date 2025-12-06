package com.siteone.service;

import de.hybris.platform.core.PK;
import de.hybris.platform.europe1.jalo.PDTRowsQueryBuilder;

import com.siteone.service.impl.DefaultSiteOnePDTRowsQueryBuilder;

public interface SiteOnePDTRowsQueryBuilder extends PDTRowsQueryBuilder{
	SiteOnePDTRowsQueryBuilder withAnyProduct();

	SiteOnePDTRowsQueryBuilder withProduct(PK var1);

	SiteOnePDTRowsQueryBuilder withProductGroup(PK var1);

	SiteOnePDTRowsQueryBuilder withProductId(String var1);

	SiteOnePDTRowsQueryBuilder withAnyUser();

	SiteOnePDTRowsQueryBuilder withUser(PK var1);

	SiteOnePDTRowsQueryBuilder withUserGroup(PK var1);
	
	SiteOnePDTRowsQueryBuilder withPOS(PK var1);

	//QueryWithParams build();
	
	static SiteOnePDTRowsQueryBuilder defaultBuilder(String type) {
		return new DefaultSiteOnePDTRowsQueryBuilder(type);
	}
}
