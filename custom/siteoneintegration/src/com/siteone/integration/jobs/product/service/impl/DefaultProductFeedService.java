package com.siteone.integration.jobs.product.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.jobs.product.dao.ProductFeedCronJobDao;
import com.siteone.integration.jobs.product.dao.impl.DefaultProductFeedCronJobDao;
import com.siteone.integration.jobs.product.service.ProductFeedService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.integration.util.GenerateNurseryFeedExcelUtil;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.CSVWriter;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.catalog.model.ProductFeatureModel;

public class DefaultProductFeedService implements ProductFeedService{

	private static final Logger LOG = Logger.getLogger(DefaultProductFeedService.class);
	private static final String CATALOG_ID = "siteoneProductCatalog";
	private static final String CATALOGID = "siteoneContentCatalog";
	private static final String VERSION_ONLINE = "Online";
	private ProductFeedCronJobDao productFeedCronJobDao;
	private ConfigurationService configurationService;
	private SiteOneBlobDataImportService blobDataImportService;
	private EnumerationService enumerationService;
	private ModelService modelService;
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Resource
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	@Resource(name = "mediaService")
	private MediaService mediaService;
	private static final String LANGUAGE_TAG = "language_tag";
	private static final String TITLE_TAG = "title";
	private static final String[] PRODUCT_FILE_HEADER = { "id", TITLE_TAG, "description", "image_link", "mpn",
			"brand", "excluded_destination", "condition","price","link","link_template","custom_label_0","custom_label_1","custom_label_2","custom_label_3","custom_label_4"};
	private static final String[] VARIANT_PRODUCT_FILE_HEADER = { "id", TITLE_TAG, "description", "image_link", "mpn",
			"brand", "excluded_destination", "condition","price","link","link_template","custom_label_0","custom_label_1","custom_label_2","custom_label_3","custom_label_4","Base SKU"};
	private static final String[] BASE_PRODUCT_FILE_HEADER = { "id", TITLE_TAG, "description", "image_link", "mpn",
			"brand", "excluded_destination", "condition","price","link","link_template","custom_label_0","custom_label_1","custom_label_2","custom_label_3","custom_label_4"};
	private static final String[] FULL_PRODUCT_FILE_HEADER = { "id", TITLE_TAG, "description", "image_link", "itemnumber",
			"brand", "excluded_destination", "condition","price","link","link_template","custom_label_0","custom_label_1","custom_label_2","custom_label_3","custom_label_4"};
	private static final String[] LOB_FULL_PRODUCT_FILE_HEADER = { "id", TITLE_TAG, "description", "image_link", "itemnumber",
			"brand", "link","link_template","custom_label_1","custom_label_2","custom_label_3","custom_label_4",
			"Item Image Count","Information Guide Count","Product_Type"};
	private static final String[] INVENTORY_FILE_HEADER = {"store_code", "id", "price", "quantity"};
	private static final String[] DC_INVENTORY_FILE_HEADER = {"store_code", "id", "quantity"};
	private static final String[] REGION_FILE_HEADER = {"region_id", TITLE_TAG, LANGUAGE_TAG, "price_multiplier"};
	private static final String[] PRODUCT_REGION_FILE_HEADER = {"product_id", "region_id", "price","quantity"};
	private static final String[] CATEGORY_FILE_HEADER = {"category_id", "name", LANGUAGE_TAG};
	private static final String[] LOCALIZED_PRODUCT_FILE_HEADER= {"product_id", LANGUAGE_TAG, "name"};
	private static final String EXCLUDED_DESTINATION="Free_listings";
	private static final String CONDITION="new";
	
	private static final String LANGUAGE_TAG_EN = "en-US";
	private static final String ENCODING="UTF-8";
	private GenerateNurseryFeedExcelUtil excelUtil=new GenerateNurseryFeedExcelUtil();

	private static final String LOGO_MEDIACODE = "siteone-logo-nursery-feed";
	
	@Override
	public void exportProductFeed() throws IOException {
		List<ProductModel> productModel = getProductFeedCronJobDao().getProductsForGoogleFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.PRODUCT_FEED_TARGET_LOCATION);	
		File file = new File(Config.getString("product.feed.fileName",StringUtils.EMPTY)+".csv");
		sessionService.setAttribute("currentSite", baseSiteService.getBaseSiteForUID("siteone"));		
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator(',');
			csvWriter.write(printHeader(PRODUCT_FILE_HEADER));
			for (final ProductModel product : productModel)
			{			
				csvWriter.write(getProductLine(product,false));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	@Override
	public void exportVariantProductFeed() throws IOException {
		List<ProductModel> productModel = getProductFeedCronJobDao().getVariantProductsForGoogleFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.PRODUCT_FEED_TARGET_LOCATION);	
		File file = new File(Config.getString("variant.feed.fileName",StringUtils.EMPTY)+".csv");
		sessionService.setAttribute("currentSite", baseSiteService.getBaseSiteForUID("siteone"));		
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator(',');
			csvWriter.write(printHeader(VARIANT_PRODUCT_FILE_HEADER));
			for (final ProductModel product : productModel)
			{		
			csvWriter.write(getProductLine(product,true));
			}
			
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	@Override
	public void exportBaseProductFeed() throws IOException {
		List<ProductModel> productModel = getProductFeedCronJobDao().getBaseProductsForGoogleFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.PRODUCT_FEED_TARGET_LOCATION);	
		File file = new File(Config.getString("baseproduct.feed.fileName",StringUtils.EMPTY)+".csv");
		sessionService.setAttribute("currentSite", baseSiteService.getBaseSiteForUID("siteone"));		
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator(',');
			csvWriter.write(printHeader(BASE_PRODUCT_FILE_HEADER));
			for (final ProductModel product : productModel)
			{			
				csvWriter.write(getProductLine(product,false));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	@Override
	public void exportFullProductFeed() throws IOException {
		List<ProductModel> productModel = getProductFeedCronJobDao().getDataForFullProductFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.FULL_PRODUCT_FEED_TARGET_LOCATION);
		Map<String, String> lobIdentifier = new HashMap();
		lobIdentifier.put("PH11", "lght");
		lobIdentifier.put("PH12", "lndscp");
		lobIdentifier.put("PH13", "Maintenance");
		lobIdentifier.put("PH14", "irg");
		lobIdentifier.put("PH15", "hrdscp");
		lobIdentifier.put("PH16", "nrsry");
		sessionService.setAttribute("currentSite", baseSiteService.getBaseSiteForUID("siteone"));
		for(Map.Entry<String, String> entry : lobIdentifier.entrySet()) {
			List<ProductModel> product_line = productModel.stream().filter(product -> product.getSupercategories() != null ? product.getSupercategories().stream()
					   .anyMatch(cat -> cat.getCode().startsWith(entry.getKey()) || cat.getCode().startsWith(entry.getValue())) : null)
					   .collect(Collectors.toList());
			File file_line = new File(Config.getString("fullproduct.feed.fileName." + entry.getValue().toLowerCase(),StringUtils.EMPTY)+".csv");
			List<String> lobAttributeList = getLOBAttribute(entry.getValue()+"%");
			List<String> lob_header = new ArrayList(Arrays.asList(LOB_FULL_PRODUCT_FILE_HEADER));
			lob_header.addAll(lobAttributeList);
			String[] LOB_FULL_PRODUCT_FILE_HEADER_MODIFIED = lob_header.toArray(new String[0]);
			try (CSVWriter csvWriter = new CSVWriter(file_line, ENCODING, false)){
				csvWriter.setFieldseparator('|');
				csvWriter.write(printHeader(LOB_FULL_PRODUCT_FILE_HEADER_MODIFIED));
				for (final ProductModel product : product_line)
				{	if(null != product) 
					{
						csvWriter.write(getProductLineBasedOnLOB(product,false,lobAttributeList));
					}
				}
			} finally {
				getBlobDataImportService().writeBlob(file_line, productFeedContainer);
			}
		}
		File file = new File(Config.getString("fullproduct.feed.fileName",StringUtils.EMPTY)+".csv");
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator('|');
			csvWriter.write(printHeader(FULL_PRODUCT_FILE_HEADER));
			for (final ProductModel product : productModel)
			{			
				csvWriter.write(getProductLine(product,false));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	@Override
	public void exportInventoryFeed() throws IOException {
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.PRODUCT_FEED_TARGET_LOCATION);
		File file1 = new File(Config.getString("inventory.feed.fileName",StringUtils.EMPTY)+".csv");
		File file2 = new File(Config.getString("variantinventory.feed.fileName",StringUtils.EMPTY)+".csv");
		File file3 = new File(Config.getString("dcinventory.feed.fileName",StringUtils.EMPTY)+".csv");
		List<String> productCodeList = getProductFeedCronJobDao().getProductCodesForInventoryFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		List<String> variantProductCodeList = getProductFeedCronJobDao().getvariantProductCodesForInventoryFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));	
		try {
			exportFeedFiles(file1,productCodeList,productFeedContainer);
			exportFeedFiles(file2,variantProductCodeList,productFeedContainer);
			exportFeedFiles(file3,productFeedContainer);
		}
		catch(Exception ex){
			LOG.error(ex);
		}		
	}
	
	private List<String> getLOBAttribute(String code) {
		List<String> lobAttributeList = getProductFeedCronJobDao().getLOBAttributes(code);
		return lobAttributeList;
	}
	
	private Map<Integer, String> getProductLineBasedOnLOB(ProductModel product, boolean Variant, List<String> lobAttributeList) {
		LOG.info("LOB Product Data Report2 : ");
		final ProductData productData = new ProductData();
		 productConfiguredPopulator.populate(product, productData,
			 Arrays.asList(ProductOption.URL,ProductOption.CATEGORIES,ProductOption.PROMOTIONS));		
		 final String websiteUrl=getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.WEBSITE_SITEONE_SERVER);
		 String categoryCode="";
		 String categoryLevel1=""; 
		 String categoryLevel2=""; 
		 String categoryLevel3=""; 
		 String categoryLevel4=""; 	   
		
		 for(CategoryData category:productData.getCategories())
		 {
		 if(category.getCode().startsWith("PH1"))
		 {
			 categoryCode=category.getCode();
		 }
		 }		
		 
		 if(StringUtils.isNotBlank(categoryCode))
		 {
			 categoryLevel1=categoryCode.length()>=4?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,4)):""; 
			 categoryLevel2=categoryCode.length()>=6?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,6)):"";
			 categoryLevel3=categoryCode.length()>=9?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,9)):"";
			 categoryLevel4=categoryCode.length()>=12?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,12)):""; 
			    
		 }		 

		if (null != product.getProductBrandName())
		{
			productData.setProductBrandName(getEnumerationService()
					.getEnumerationName((HybrisEnumValue) getProductAttribute(product, ProductModel.PRODUCTBRANDNAME)));
		}
		String price=getProductFeedCronJobDao().getPriceForProduct(product.getCode());
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), product.getCode()!=null?product.getCode():"");
		line.put(Integer.valueOf(1), product.getProductShortDesc()!=null?product.getProductShortDesc():"No Product Name");
		line.put(Integer.valueOf(2), product.getProductLongDesc()!=null?product.getProductLongDesc():"");
		line.put(Integer.valueOf(3), product.getPicture()!=null?websiteUrl+product.getPicture().getURL():"");
		line.put(Integer.valueOf(4), product.getItemNumber()!=null?product.getItemNumber():"");
		line.put(Integer.valueOf(5), productData.getProductBrandName()!=null?productData.getProductBrandName():"");
		line.put(Integer.valueOf(6), productData.getUrl()!=null?websiteUrl+productData.getUrl():"");
		line.put(Integer.valueOf(7),productData.getUrl()!=null?websiteUrl+productData.getUrl()+"?store={store_code}":"" );
		line.put(Integer.valueOf(8),categoryLevel1!=null?categoryLevel1:"" );
		line.put(Integer.valueOf(9),categoryLevel2!=null?categoryLevel2:"" );
		line.put(Integer.valueOf(10),categoryLevel3!=null?categoryLevel3:"" );	
		line.put(Integer.valueOf(11),categoryLevel4!=null?categoryLevel4:"" );
		line.put(Integer.valueOf(12), Integer.valueOf(product.getGalleryImages() != null ? product.getGalleryImages().size() : 0).toString());
		line.put(Integer.valueOf(13), Integer.valueOf(product.getData_sheet() != null ? product.getData_sheet().size() : 0).toString());
		line.put(Integer.valueOf(14), product.getProductType());
		int i=line.size();
		for (String attr : lobAttributeList) {
			LOG.info("LOB Product Data header attr : " + attr);
			line.put(Integer.valueOf(i), featureValue(product, attr));
			i++;
		}
		
		return line;
	}
	
	private String featureValue(ProductModel product,String featureName) {
		ProductFeatureModel feature = product.getFeatures().stream().filter(f -> f.getQualifier().toLowerCase().contains(featureName.toLowerCase()) && f.getQualifier().contains(product.getClassificationClasses().get(0).getCode())).findFirst().orElse(null);
		return feature != null ? feature.getValue().toString() : StringUtils.EMPTY;
	}
	
	private void exportFeedFiles(File file, String productFeedContainer) throws IOException{
		
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator(',');
			csvWriter.write(printHeader(DC_INVENTORY_FILE_HEADER));
			List<List<Object>> inventoryDataList = getProductFeedCronJobDao()
					.getInventoryForDcBranch(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
			for(List<Object> inventoryData:inventoryDataList)
			{		
				csvWriter.write(getDcInventoryLine(inventoryData));
			}
		}
	 finally {
		getBlobDataImportService().writeBlob(file, productFeedContainer);
	}
		
	}

	public void exportFeedFiles(File file,List<String> productCodeList,String productFeedContainer ) throws IOException {
		LOG.error("Inventory feed product count "+productCodeList.size());
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator(',');
			csvWriter.write(printHeader(INVENTORY_FILE_HEADER));
			for(String productCode : productCodeList) {
				List<List<Object>> inventoryDataList = getProductFeedCronJobDao()
						.getInventoryDataForGoogleFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE), productCode);
				for(List<Object> inventoryData:inventoryDataList)
				{		
					csvWriter.write(getInventoryLine(inventoryData));
				}
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	@Override
	public void exportNurseryInventoryFeedData() throws IOException {
		final String nurseryFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.NURSERY_INVENTORY_FEED_TARGET_LOCATION);
		List<PointOfServiceModel> pointOfServiceModel = getProductFeedCronJobDao().getRegions();
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(CATALOGID, CatalogManager.ONLINE_VERSION);
		final String websiteUrl=getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.WEBSITE_SITEONE_SERVER);
		final MediaModel media = mediaService.getMedia(catalogVersion, LOGO_MEDIACODE);
	    InputStream inputStream = mediaService.getStreamFromMedia(media);
		byte[] inputImageBytes = IOUtils.toByteArray(inputStream);
		for(PointOfServiceModel store: pointOfServiceModel ) {
			List<List<Object>> nurseryInventoryDataList =  getProductFeedCronJobDao().getInventoryDataForNurseryFeed(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE), store.getStoreId());
			File file = new File(Config.getString("nursery.feed.fileName",StringUtils.EMPTY)+store.getStoreId()+".xlsx");
			List<Map<Integer,String>> nurseryInventoryMapList = new ArrayList<>();
			for(List<Object> NurseryData:nurseryInventoryDataList)
			{		
				nurseryInventoryMapList.add(getNurseryInventoryLine(NurseryData,store.getStoreId()));
			}
			excelUtil.createNurseryFeedExcelFile(file.getName(), nurseryInventoryMapList, inputImageBytes, websiteUrl);
			getBlobDataImportService().writeBlob(file, nurseryFeedContainer);
		}	
		inputStream.close();
	}
	
	
	@Override
	public void exportRegionFeed() throws IOException {
		List<PointOfServiceModel> pointOfServiceModel = getProductFeedCronJobDao().getRegions();
		final String regionFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ALGONOMY_FEED_TARGET_LOCATION);
		File file = new File(getFileName("region.feed.fileName")+".txt");
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator('|');
			csvWriter.write(printHeader(REGION_FILE_HEADER));
			for (final PointOfServiceModel pointOfService : pointOfServiceModel)
			{			
				csvWriter.write(getRegionLine(pointOfService));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, regionFeedContainer);
		}
		
	}
	
	@Override
	public void exportCategoryFeed() throws IOException {
		List<CategoryModel> categoryModel = getProductFeedCronJobDao().getCategory(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ALGONOMY_FEED_TARGET_LOCATION);	
		File file = new File(getFileName("category.feed.fileName")+".txt");
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator('|');
			csvWriter.write(printHeader(CATEGORY_FILE_HEADER));		
				for(final CategoryModel category:categoryModel)
			{		
				csvWriter.write(getCategoryLine(category));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}

	}
	
	@Override
	public void exportLocalizedProductFeed() throws IOException {
		List<ProductModel> productModel = getProductFeedCronJobDao().getProducts(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ALGONOMY_FEED_TARGET_LOCATION);		
		File file = new File(getFileName("localized.product.feed.fileName")+".txt");
		try (CSVWriter csvWriter = new CSVWriter(file, ENCODING, false)){
			csvWriter.setFieldseparator('|');
			csvWriter.write(printHeader(LOCALIZED_PRODUCT_FILE_HEADER));
			for (final ProductModel product : productModel)
			{			
				csvWriter.write(getLocalizedProductLine(product));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	private String getFileName(String fileName) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_DD");
		return Config.getString(fileName,StringUtils.EMPTY)
				+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + dateFormat.format(cal.getTime());
	}
	
	private Map<Integer, String> getCategoryLine(CategoryModel category) {
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), category.getPimCategoryId()!=null?category.getPimCategoryId():"");
		line.put(Integer.valueOf(1), category.getName()!=null?category.getName():"");
		line.put(Integer.valueOf(2), LANGUAGE_TAG_EN);
		return line;	
	}
	
	private Map<Integer, String> getRegionLine(PointOfServiceModel pointOfService) {
		
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), pointOfService.getStoreId()!=null?pointOfService.getStoreId():"");
		line.put(Integer.valueOf(1), pointOfService.getName()!=null?pointOfService.getName():"");
		line.put(Integer.valueOf(2), LANGUAGE_TAG_EN);
		line.put(Integer.valueOf(3), "100");

		return line;
	}

	@Override
	public void exportProductRegionFeed() throws IOException {
		List<List<Object>> inventoryDataList = getProductFeedCronJobDao().getInventoryData(catalogVersionService.getCatalogVersion(CATALOG_ID, VERSION_ONLINE));
		final String productFeedContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.ALGONOMY_FEED_TARGET_LOCATION);
		File file = new File(getFileName("product.region.feed.fileName")+".txt");
		try (CSVWriter csvWriter = new CSVWriter(file, "UTF-8", false)){
			csvWriter.setFieldseparator('|');
			csvWriter.write(printHeader(PRODUCT_REGION_FILE_HEADER));
			for(List<Object> inventoryData:inventoryDataList)
			{		
				csvWriter.write(getProductRegionLine(inventoryData));
			}
		} finally {
			getBlobDataImportService().writeBlob(file, productFeedContainer);
		}
		
	}
	
	private Map<Integer, String> getProductRegionLine(List<Object> inventoryData) {
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), inventoryData.get(1)!=null?(String) inventoryData.get(1):"");
		line.put(Integer.valueOf(1), inventoryData.get(0)!=null?(String) inventoryData.get(0):"");
		line.put(Integer.valueOf(2), inventoryData.get(2)!=null?inventoryData.get(2).toString():"");
		line.put(Integer.valueOf(3), inventoryData.get(3)!=null?(String)inventoryData.get(3):"");
		return line;
	}
	
	private Map<Integer, String> getInventoryLine(List<Object> inventoryData) {
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), inventoryData.get(0)!=null?(String) inventoryData.get(0):"");
		line.put(Integer.valueOf(1), inventoryData.get(1)!=null?(String) inventoryData.get(1):"");
		line.put(Integer.valueOf(2), inventoryData.get(2)!=null?inventoryData.get(2)+" USD":"");
		line.put(Integer.valueOf(3), inventoryData.get(3)!=null?(String) inventoryData.get(3):"");
		return line;
	}
	private Map<Integer, String> getDcInventoryLine(List<Object> inventoryData) {
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), inventoryData.get(0)!=null?(String) inventoryData.get(0):"");
		line.put(Integer.valueOf(1), inventoryData.get(1)!=null?(String) inventoryData.get(1):"");
		line.put(Integer.valueOf(2), inventoryData.get(2)!=null?(String) inventoryData.get(2):"");
		return line;
	}
	
	private Map<Integer,String> getNurseryInventoryLine(List<Object> nurseryData, String storeId){
		ProductModel product = (ProductModel) nurseryData.get(3);
		if(product instanceof VariantProductModel)
		{
			final ProductModel baseProduct = ((VariantProductModel) product).getBaseProduct();
			if (baseProduct != null) {
				product=baseProduct;
			}
		}
		final ProductData productData = new ProductData();
		 productConfiguredPopulator.populate(product, productData,
			 Arrays.asList(ProductOption.URL,ProductOption.CATEGORIES,ProductOption.PROMOTIONS));
		 final String websiteUrl=getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.WEBSITE_SITEONE_SERVER);
		 String categoryCode="";
		 String categoryLevel1=""; 
		 String categoryLevel2=""; 
		 String categoryLevel3=""; 
		 String categoryLevel4="";  
		   
		
		 for(CategoryData category:productData.getCategories())
		 {
		 if(category.getCode().startsWith("PH1"))
		 {
			 categoryCode=category.getCode();
		 }
		 }
		
		 
		 if(StringUtils.isNotBlank(categoryCode))
		 {
			 categoryLevel1=categoryCode.length()>=4?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,4)):""; 
			 categoryLevel2=categoryCode.length()>=6?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,6)):"";
			 categoryLevel3=categoryCode.length()>=9?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,9)):"";
			 categoryLevel4=categoryCode.length()>=12?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,12)):""; 
			    
		 }
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), nurseryData.get(0)!=null?(String) nurseryData.get(0):"");
		line.put(Integer.valueOf(1), nurseryData.get(1)!=null?(String) nurseryData.get(1):"");
		line.put(Integer.valueOf(2), nurseryData.get(2)!=null?(String) nurseryData.get(2):"");
		line.put(Integer.valueOf(3), productData.getUrl()!=null?websiteUrl+productData.getUrl()+"?cid=nurseryinventory&branch"+storeId+"&store="+storeId:"");
		line.put(Integer.valueOf(4),categoryLevel1!=null?categoryLevel1:"" );
		line.put(Integer.valueOf(5),categoryLevel2!=null?categoryLevel2:"" );
		line.put(Integer.valueOf(6),categoryLevel3!=null?categoryLevel3:"" );	
		line.put(Integer.valueOf(7),categoryLevel4!=null?categoryLevel4:"" );
		return line;
		
	}
	
	private Map<Integer, String> getLocalizedProductLine(ProductModel product) {
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), product.getCode()!=null?product.getCode():"");
		line.put(Integer.valueOf(1), LANGUAGE_TAG_EN);
		line.put(Integer.valueOf(2), "");
		return line;
	}
		
	private Map<Integer, String> getProductLine(ProductModel product,boolean Variant) {
		final ProductData productData = new ProductData();
		 productConfiguredPopulator.populate(product, productData,
			 Arrays.asList(ProductOption.URL,ProductOption.CATEGORIES,ProductOption.PROMOTIONS));		
		 final String websiteUrl=getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.WEBSITE_SITEONE_SERVER);
		 String categoryCode="";
		 String categoryLevel1=""; 
		 String categoryLevel2=""; 
		 String categoryLevel3=""; 
		 String categoryLevel4="";  
		   
		
		 for(CategoryData category:productData.getCategories())
		 {
		 if(category.getCode().startsWith("PH1"))
		 {
			 categoryCode=category.getCode();
		 }
		 }
		
		 
		 if(StringUtils.isNotBlank(categoryCode))
		 {
			 categoryLevel1=categoryCode.length()>=4?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,4)):""; 
			 categoryLevel2=categoryCode.length()>=6?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,6)):"";
			 categoryLevel3=categoryCode.length()>=9?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,9)):"";
			 categoryLevel4=categoryCode.length()>=12?getProductFeedCronJobDao().getCategoryName(categoryCode.substring(0,12)):""; 
			    
		 }
		 

		if (null != product.getProductBrandName())
		{
			productData.setProductBrandName(getEnumerationService()
					.getEnumerationName((HybrisEnumValue) getProductAttribute(product, ProductModel.PRODUCTBRANDNAME)));
		}
		String price=getProductFeedCronJobDao().getPriceForProduct(product.getCode());
		Map<Integer, String> line = new HashMap<>();
		line.put(Integer.valueOf(0), product.getCode()!=null?product.getCode():"");
		line.put(Integer.valueOf(1), product.getProductShortDesc()!=null?product.getProductShortDesc():"No Product Name");
		line.put(Integer.valueOf(2), product.getProductLongDesc()!=null?product.getProductLongDesc():"");
		line.put(Integer.valueOf(3), product.getPicture()!=null?websiteUrl+product.getPicture().getURL():"");
		line.put(Integer.valueOf(4), product.getItemNumber()!=null?product.getItemNumber():"");
		line.put(Integer.valueOf(5), productData.getProductBrandName()!=null?productData.getProductBrandName():"");
		line.put(Integer.valueOf(6), EXCLUDED_DESTINATION);
		line.put(Integer.valueOf(7), CONDITION);
		line.put(Integer.valueOf(8), price!=null?price+" USD":"");
		line.put(Integer.valueOf(9), productData.getUrl()!=null?websiteUrl+productData.getUrl():"");
		line.put(Integer.valueOf(10),productData.getUrl()!=null?websiteUrl+productData.getUrl()+"?store={store_code}":"" );
		line.put(Integer.valueOf(11),categoryLevel1!=null?categoryLevel1:"" );
		line.put(Integer.valueOf(12),categoryLevel2!=null?categoryLevel2:"" );
		line.put(Integer.valueOf(13),categoryLevel3!=null?categoryLevel3:"" );	
		line.put(Integer.valueOf(14),categoryLevel4!=null?categoryLevel4:"" );
		if(null != productData.getCouponCode())
		{
			line.put(Integer.valueOf(15),"coupon");	
		}
		else
		{
			line.put(Integer.valueOf(15),"");
		}
		if(Variant) {
		ProductModel currentProduct = product;
		if(currentProduct instanceof VariantProductModel) {
			currentProduct = ((VariantProductModel) currentProduct).getBaseProduct();
				line.put(Integer.valueOf(16), currentProduct.getCode()!=null?currentProduct.getCode():"");
		}
		}
		return line;
	}
		
	private Map<Integer, String> printHeader(String[] fileHeader) {
		Map<Integer, String> line = new HashMap<>();
		int i=0;
		for (String header : fileHeader) {
			line.put(Integer.valueOf(i), header);
			i++;
		}
		return line;
	}	
	
	protected Object getProductAttribute(final ProductModel productModel, final String attribute)
	{
		final Object value = getModelService().getAttributeValue(productModel, attribute);
		if (value == null && productModel instanceof VariantProductModel)
		{
			final ProductModel baseProduct = ((VariantProductModel) productModel).getBaseProduct();
			if (baseProduct != null)
			{
				return getProductAttribute(baseProduct, attribute);
			}
		}
		return value;
	}
	
	
	public ProductFeedCronJobDao getProductFeedCronJobDao() {
		return productFeedCronJobDao;
	}

	public void setProductFeedCronJobDao(ProductFeedCronJobDao productFeedCronJobDao) {
		this.productFeedCronJobDao = productFeedCronJobDao;
	}

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	/**
	 * @param configurationService the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}



	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}



	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	/**
	 * @return the blobDataImportService
	 */
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}

	/**
	 * @param blobDataImportService the blobDataImportService to set
	 */
	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService() {
		return commonI18NService;
	}

	/**
	 * @param commonI18NService the commonI18NService to set
	 */
	public void setCommonI18NService(CommonI18NService commonI18NService) {
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the enumerationService
	 */
	public EnumerationService getEnumerationService() {
		return enumerationService;
	}

	/**
	 * @param enumerationService the enumerationService to set
	 */
	public void setEnumerationService(EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}

}
