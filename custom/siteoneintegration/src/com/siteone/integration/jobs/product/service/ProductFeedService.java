package com.siteone.integration.jobs.product.service;

import java.io.IOException;

public interface ProductFeedService {
  
	public void exportProductFeed() throws IOException;
	
	public void exportInventoryFeed() throws IOException;

	public void exportProductRegionFeed() throws IOException;
	
	public void exportRegionFeed() throws IOException;
	
	public void exportCategoryFeed() throws IOException;
	
	public void exportLocalizedProductFeed() throws IOException;

	public void exportNurseryInventoryFeedData() throws IOException;

	public void exportFullProductFeed() throws IOException;

	public void exportVariantProductFeed() throws IOException;

	public void exportBaseProductFeed() throws IOException;
	
}
