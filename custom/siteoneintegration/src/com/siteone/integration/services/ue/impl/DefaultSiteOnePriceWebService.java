package com.siteone.integration.services.ue.impl;

import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.price.data.CustVend;
import com.siteone.integration.price.data.DebugInfo;
import com.siteone.integration.price.data.DocumentLines;
import com.siteone.integration.price.data.FieldNameValue;
import com.siteone.integration.price.data.HeaderExtensions;
import com.siteone.integration.price.data.HeaderFieldNameValues;
import com.siteone.integration.price.data.LineExtensions;
import com.siteone.integration.price.data.LineFieldNameValues;
import com.siteone.integration.price.data.LineFields;
import com.siteone.integration.price.data.PriceSingle;
import com.siteone.integration.price.data.Product;
import com.siteone.integration.price.data.ServiceParameter;
import com.siteone.integration.price.data.SiteOneWsPriceRequestData;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.price.data.UnitOfMeasure;
import com.siteone.integration.price.data.WcfDocumentLine;
import com.siteone.integration.product.price.data.SiteOneCSPProductRequestData;
import com.siteone.integration.product.price.data.SiteOneCSPRequestData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOnePriceWebService;
import jakarta.annotation.Resource;
import de.hybris.platform.site.BaseSiteService;
import com.siteone.integration.util.ResilienceHandler;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import com.siteone.core.model.InventoryUPCModel;
/**
 * @author 1230514
 *
 */
public class DefaultSiteOnePriceWebService implements SiteOnePriceWebService {

	private ProductService productService;

	private Converter<ProductModel, SiteOneCSPProductRequestData> siteOneCSPProductRequestConverter;

	private Converter<List<SiteOneCSPProductRequestData>, SiteOneCSPRequestData> siteOneCSPRequestConverter;

	private SiteOneRestClient<SiteOneWsPriceRequestData, SiteOneWsPriceResponseData> siteOneRestClient;
	
	@Resource(name = "b2bUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2BUnitConverter;

	private SessionService sessionService;

	private ProductModel productModel;

	private boolean isD365csp = false;
	
	private static final String SESSION_SHIPTO = "shipTo";

	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePriceWebService.class.getName());
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	private B2BUnitData sessionShipTo = null;
	
	@Override
	public SiteOneWsPriceResponseData getPrice(Map<ProductModel,Integer> products,Map<ProductModel,String> productsUoms,String storeId,List<PointOfServiceData> nearbyStoresList,B2BCustomerModel b2bCustomerModel,String currencyIso,boolean isNewBoomiEnv, List<String> d365Branches, boolean isD365NewUrl, String branchRetailID) {

		sessionShipTo = (B2BUnitData) getSessionService().getAttribute(SESSION_SHIPTO);
		
		if(StringUtils.isEmpty(branchRetailID) && (null == sessionShipTo || null == sessionShipTo.getUid())) {
			LOG.error("Unable to find Session Unit ID.");
			if (b2bCustomerModel != null)
			{
				LOG.error("The b2bCustomerModel for user is " + b2bCustomerModel.getUid());
				sessionShipTo = getB2BUnitConverter().convert(b2bCustomerModel.getDefaultB2BUnit());
				LOG.error("default Session Unit ID "+sessionShipTo.getUid());
			}else {
				LOG.error("Unable to find Session Unit ID and b2bCustomerModel");
				return null;
			}
			
		}
		int maximumProductToBeSent = Integer.parseInt(Config.getString(SiteoneintegrationConstants.PRICE_SERVICE_MAX_PRODUCT, "50"));
		int totalProduct = products.size();
		SiteOneWsPriceResponseData mainSiteOneWsPriceResponseData = new SiteOneWsPriceResponseData();
		ConcurrentHashMap<ProductModel,Integer> productsCopy = new ConcurrentHashMap<>(products);
		//ConcurrentHashMap<ProductModel,String> productsForUOMDataCodeCopy = new ConcurrentHashMap<>(productsForUOMDataCode);
		mainSiteOneWsPriceResponseData.setPrices(new ArrayList<>());
		if(totalProduct > maximumProductToBeSent ) {
			while(totalProduct >= maximumProductToBeSent) {
				Map<ProductModel, Integer> maximumProductsFromMainMap = new HashMap<>();
				for (ProductModel key : productsCopy.keySet()) {
					maximumProductsFromMainMap.put(key, products.get(key));
					productsCopy.remove(key);
					if (maximumProductToBeSent-- == 1)
						break;
				}
				maximumProductToBeSent = Integer.parseInt(Config.getString(SiteoneintegrationConstants.PRICE_SERVICE_MAX_PRODUCT, "50"));
				totalProduct = productsCopy.size();
				makePriceCallWithProducts(mainSiteOneWsPriceResponseData,maximumProductsFromMainMap, productsUoms, storeId, nearbyStoresList, b2bCustomerModel, currencyIso, isNewBoomiEnv, d365Branches, isD365NewUrl,branchRetailID);
			}
			if (productsCopy.size() > 0 && productsCopy.size() < products.size()) {
				makePriceCallWithProducts(mainSiteOneWsPriceResponseData,productsCopy, productsUoms, storeId, nearbyStoresList, b2bCustomerModel, currencyIso, isNewBoomiEnv, d365Branches, isD365NewUrl,branchRetailID);
			}
		}
		else{
			makePriceCallWithProducts(mainSiteOneWsPriceResponseData,products, productsUoms, storeId, nearbyStoresList, b2bCustomerModel, currencyIso, isNewBoomiEnv, d365Branches, isD365NewUrl,branchRetailID);
		}
		return mainSiteOneWsPriceResponseData;
	
	}

	public void makePriceCallWithProducts(SiteOneWsPriceResponseData mainSiteOneWsPriceResponseData, Map<ProductModel, Integer> products, Map<ProductModel, String> productsUoms,
			  String storeId, List<PointOfServiceData> nearbyStoresList, B2BCustomerModel b2bCustomerModel,
			  String currencyIso, boolean isNewBoomiEnv, List<String> d365Branches, boolean isD365NewUrl,String branchRetailID){

		final SiteOneWsPriceRequestData siteOneWsPriceRequestData = createPriceRequestData(products, productsUoms, storeId, nearbyStoresList, b2bCustomerModel, currencyIso, d365Branches,branchRetailID);
		
		if(siteOneWsPriceRequestData!=null) {
			SiteOneWsPriceResponseData siteOneWsPriceResponseData = executeCSPCall(siteOneWsPriceRequestData, isNewBoomiEnv, isD365NewUrl);
			/*
			int cspCallMaxRetryCount = Config.getInt("hybris.price.csp.maxRetryCount", 1);
		
			int retryCount=0;
		
			while(siteOneWsPriceResponseData==null && retryCount<cspCallMaxRetryCount){
				retryCount++;
				LOG.error("Retrying CSP Call for customer- "+b2bCustomerModel.getUid()+"- and sessionID -"+siteOneWsPriceRequestData.getPriceSingle().getServiceParameter().getDebugInfo().getSessionId()+"...retry# "+retryCount);
				siteOneWsPriceResponseData = executeCSPCall(siteOneWsPriceRequestData,isNewBoomiEnv, isD365NewUrl);
			}
			*/
			SiteOneWsPriceResponseData roundRes = roundOffPriceResponse(siteOneWsPriceResponseData);
			if (null != roundRes && CollectionUtils.isNotEmpty(roundRes.getPrices())) {
				roundRes.getPrices().forEach(prices -> mainSiteOneWsPriceResponseData.getPrices().add(prices));
			}
		}
	}
	

	private SiteOneWsPriceResponseData executeCSPCall(SiteOneWsPriceRequestData siteOneWsPriceRequestData, boolean isNewBoomiEnv, boolean isD365NewUrl){
		SiteOneWsPriceResponseData siteOneWsPriceResponseData = null;
		String apiUrl=SiteoneintegrationConstants.PRICE_SERVICE_URL_KEY;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		if(isD365NewUrl) 
		{
			apiUrl=SiteoneintegrationConstants.PRICE_SERVICE_D365_NEW_URL_KEY;
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_D365, null));
		}
		else 
		{
			if(isD365csp)
			{
				apiUrl=SiteoneintegrationConstants.PRICE_SERVICE_D365_URL_KEY;
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_D365, null));
			}
			else if(isNewBoomiEnv)
			{
				apiUrl=SiteoneintegrationConstants.PRICE_SERVICE_NEW_URL_KEY;
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_PRICE, null));
			}
		}
		/*try {
			siteOneWsPriceResponseData = getSiteOneRestClient().execute(
					Config.getString(apiUrl, null), HttpMethod.POST, siteOneWsPriceRequestData,
					SiteOneWsPriceResponseData.class, UUID.randomUUID().toString(), SiteoneintegrationConstants.PRICE_SERVICE_NAME, httpHeaders);
		} catch(final ResourceAccessException resourceAccessException) {
			LOG.error("Did not receive CSP response within -"+Config.getInt("rest.price.connection.timeout", 20000)+ "- milliSeconds.",resourceAccessException );
		}
		//return siteOneWsPriceResponseData;*/
		return ResilienceHandler.applyResiliencePatterns(siteOneWsPriceRequestData, apiUrl, httpHeaders);
	}


	public SiteOneWsPriceResponseData callPricingService(SiteOneWsPriceRequestData siteOneWsPriceRequestData,
														 String apiUrl, HttpHeaders httpHeaders) {
		LOG.error("Calling 1st Service");
		SiteOneWsPriceResponseData siteOneWsPriceResponseData = null;
		siteOneWsPriceResponseData = getSiteOneRestClient().execute(
				Config.getString(apiUrl, null), HttpMethod.POST, siteOneWsPriceRequestData,
				SiteOneWsPriceResponseData.class, UUID.randomUUID().toString(), SiteoneintegrationConstants.PRICE_SERVICE_NAME, httpHeaders);
		LOG.error("Calling 1st Service :::: siteOneWsPriceResponseData==> " + siteOneWsPriceResponseData);
		if(siteOneWsPriceResponseData==null) {
			throw new RuntimeException();
		}
		return siteOneWsPriceResponseData;
	}

	public SiteOneWsPriceResponseData callFallbackPricingService(SiteOneWsPriceRequestData siteOneWsPriceRequestData) {
		LOG.error("Calling 2nd Service");
		SiteOneWsPriceResponseData siteOneWsPriceResponseData = null;
		String apiUrl = SiteoneintegrationConstants.PRICE_SERVICE_FALLBACK_URL_KEY;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_PRICE_FALLBACK, null));

		try {
			siteOneWsPriceResponseData = getSiteOneRestClient().execute(
					Config.getString(apiUrl, null), HttpMethod.POST, siteOneWsPriceRequestData,
					SiteOneWsPriceResponseData.class, UUID.randomUUID().toString(), SiteoneintegrationConstants.PRICE_SERVICE_NAME, httpHeaders);
		} catch(final ResourceAccessException resourceAccessException) {
			LOG.error("Did not receive CSP response within -"+Config.getInt("rest.price.connection.timeout", 20000) + "- milliSeconds.", resourceAccessException );
		}
		return siteOneWsPriceResponseData;
	}

	/**
	 * This method is used to round off the csp price into 3 digits.
	 * @param response - response from price service.
	 * @return response - response from price service after rounding off.
	 */
	private SiteOneWsPriceResponseData roundOffPriceResponse(SiteOneWsPriceResponseData response) {
		if (null != response && null != response.getPrices()) {
			DecimalFormat decimalFormat = new DecimalFormat("#.###");
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			Pattern pt = Pattern.compile("^([0-9]+\\.?[0-9]*|\\.[0-9]+)$");
			response.getPrices().removeIf(price-> null != price && null != price.getPrice() && !(pt.matcher(price.getPrice()).find()));
			response.getPrices().forEach(price -> {
				if (null != price && null != price.getPrice()) {
					try {
						final Double actualPrice = Double.valueOf(price.getPrice());
						String roundedPrice = decimalFormat.format(actualPrice);
						price.setPrice(roundedPrice);
					} catch (final NumberFormatException numberFormatException) {
						LOG.error("Number format exception occured", numberFormatException);
					}
				}				
			});
		}
		return response;
	}


	private SiteOneWsPriceRequestData createPriceRequestData(Map<ProductModel,Integer> products, Map<ProductModel,String> productsUoms,String storeId,List<PointOfServiceData> nearbyStoresList,B2BCustomerModel b2bCustomerModel,String currencyIso, List<String> d365Branches,String branchRetailID) {
		final SiteOneWsPriceRequestData siteOneWsPriceRequestData = new SiteOneWsPriceRequestData();
		final SecureRandom rand = new SecureRandom();
		final PriceSingle priceSingle = new PriceSingle();
		final ServiceParameter serviceParameter = new ServiceParameter();

		if(null !=b2bCustomerModel && null!= b2bCustomerModel.getDefaultB2BUnit()){

			if(null != b2bCustomerModel.getDefaultB2BUnit().getDivision()) {
				if (SiteoneintegrationConstants.DIVISION_US.equalsIgnoreCase(b2bCustomerModel.getDefaultB2BUnit().getDivision().getUid())) {
					serviceParameter.setCompany(SiteoneintegrationConstants.PRICE_SERVICE_COMPANY_NAME);
					serviceParameter.setCurrency(currencyIso);
				}
				else if(SiteoneintegrationConstants.DIVISION_CA.equalsIgnoreCase(b2bCustomerModel.getDefaultB2BUnit().getDivision().getUid()))
				{
				serviceParameter.setCompany(SiteoneintegrationConstants.PRICE_SERVICE_COMPANY_NAME_CA);
				serviceParameter.setCurrency("CAD");
				} 
			}
		}
		
		serviceParameter.setDocumentType(SiteoneintegrationConstants.PRICE_SERVICE_DOCUMENT_TYPE);
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_US))
				{
			serviceParameter.setCurrency(currencyIso);
				}
		else{
			serviceParameter.setCurrency("CAD");
			}
		final CustVend custVend = new CustVend();	
		if(StringUtils.isNotBlank(branchRetailID))
		{
			custVend.setCode(branchRetailID);
			if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_US))
			{
				serviceParameter.setCompany(SiteoneintegrationConstants.PRICE_SERVICE_COMPANY_NAME);
			}
	    else{
	    	    serviceParameter.setCompany(SiteoneintegrationConstants.PRICE_SERVICE_COMPANY_NAME_CA);
		    }
			
		}
		else
		{
			if (sessionShipTo == null)
			{
				sessionShipTo = (B2BUnitData) getSessionService().getAttribute(SESSION_SHIPTO);
			}
			custVend.setCode(StringUtils.substringBeforeLast(sessionShipTo.getUid().trim(), SiteoneintegrationConstants.SEPARATOR_UNDERSCORE));			
		}
		custVend.setEntityType(SiteoneintegrationConstants.PRICE_SERVICE_ENTITY_TYPE);
		serviceParameter.setCustVend(custVend);

		final DebugInfo debugInfo = new DebugInfo();
		debugInfo.setComputerName(SiteoneintegrationConstants.PRICE_SERVICE_COMPUTER_NAME);

		//Random Number for session
		int generatedId = (int) Math.floor(rand.nextDouble() * SiteoneintegrationConstants.PRICE_SESSION_ID_MIN) + SiteoneintegrationConstants.PRICE_SESSION_ID_MAX;
		debugInfo.setSessionId(String.valueOf(generatedId));

		serviceParameter.setDebugInfo(debugInfo);
		
		List<String> innerStores=new ArrayList<>();
		innerStores.add(storeId);

		List<WcfDocumentLine> wcfDocumentLines = new ArrayList<WcfDocumentLine>();
		
		products.forEach((productModel, quantity) -> {
			String upcCode = null;
			if(productsUoms != null) {
				for(Map.Entry<ProductModel,String>  productsUom : productsUoms.entrySet()) {
					ProductModel productUomData = productsUom.getKey();
					if(productUomData.getCode().equals(productModel.getCode())) {
						upcCode = productsUom.getValue();
					}
				}
			}
			InventoryUPCModel inventoryUPC = productModel.getUpcData().stream().filter(upc->upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
			if(null != inventoryUPC) {
			StringBuilder innerStoreId = new StringBuilder();
			final WcfDocumentLine wcfDocumentLine = new WcfDocumentLine();
			final Product product = new Product();
			product.setCode(productModel.getCode());
			final LineFields lineFields = new LineFields();
			lineFields.setProduct(product);

			lineFields.setQuantity(String.valueOf(quantity));

			final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

			unitOfMeasure.setUnitOfMeasureCode(inventoryUPC.getCode());

			lineFields.setUnitOfMeasure(unitOfMeasure);

			final LineExtensions lineExtensions = new LineExtensions();

			List<FieldNameValue> lineExtensionField = new ArrayList<FieldNameValue>();
			FieldNameValue lineExtensionFieldValue = new FieldNameValue();
			lineExtensionFieldValue.setName(SiteoneintegrationConstants.PRICE_SERVICE_MARGIN_FLOOR);
			lineExtensionFieldValue.setValue(SiteoneintegrationConstants.PRICE_SERVICE_REPLACEMENT_COST);
			lineExtensionField.add(lineExtensionFieldValue);
			lineExtensions.setFieldNameValue(lineExtensionField);

			LineFieldNameValues lineFieldNameValue = new LineFieldNameValues();
			List<FieldNameValue> lineFieldNameValues = new ArrayList<FieldNameValue>();
			FieldNameValue deliveryModefieldNameValue = new FieldNameValue();
			deliveryModefieldNameValue.setName(SiteoneintegrationConstants.PRICE_SERVICE_DELIVERY_MODE);
			deliveryModefieldNameValue.setValue(SiteoneintegrationConstants.PRICE_SERVICE_DELIVERY_MODE_VALUE);
			lineFieldNameValues.add(deliveryModefieldNameValue);
			
			if (sessionService.getAttribute("isMixedCartEnabled") == null && 
					null != productModel && BooleanUtils.isFalse(productModel.getIsTransferrable()))
			{
					innerStoreId = getNearbyStoreId(productModel,storeId,nearbyStoresList);							
		    }
			
			if(innerStoreId.length()>0) {
				innerStores.add(innerStoreId.toString());
			}
			FieldNameValue salesUnitfieldNameValue = new FieldNameValue();
			if(upcCode != null) {
				salesUnitfieldNameValue.setName(SiteoneintegrationConstants.SALES_UNIT);
				salesUnitfieldNameValue.setValue(upcCode);
				lineFieldNameValues.add(salesUnitfieldNameValue);
			}
			
			FieldNameValue inventSiteIDfieldNameValue = new FieldNameValue();
			inventSiteIDfieldNameValue.setName(SiteoneintegrationConstants.PRICE_SERVICE_INVENTSITEID);
			inventSiteIDfieldNameValue.setValue(innerStoreId.length()>0?innerStoreId.toString():storeId);
			lineFieldNameValues.add(inventSiteIDfieldNameValue);
			//ends
			lineFieldNameValue.setFieldNameValue(lineFieldNameValues);

			lineFields.setLineFieldNameValues(lineFieldNameValue);
			lineFields.setLineExtensions(lineExtensions);
			wcfDocumentLine.setLineFields(lineFields);
			wcfDocumentLines.add(wcfDocumentLine);
			}
		});
		
		if(wcfDocumentLines.isEmpty()) {
			LOG.error("There is no product with inventory uom");
			return null;
		}
		isD365csp = d365Branches.contains("All")||d365Branches.containsAll(innerStores);

		final DocumentLines documentLines = new DocumentLines();
		documentLines.setWcfDocumentLine(wcfDocumentLines);
		serviceParameter.setDocumentLines(documentLines);

		HeaderExtensions headerExtensions = new HeaderExtensions();
		List<FieldNameValue> headerFieldNameValue = new ArrayList<FieldNameValue>();
		FieldNameValue headerExtensionValues = new FieldNameValue();
		headerExtensionValues.setName(SiteoneintegrationConstants.PRICE_SERVICE_NURSERY);
		headerExtensionValues.setValue(SiteoneintegrationConstants.PRICE_SERVICE_NURSERY_VALUE);
		headerFieldNameValue.add(headerExtensionValues);
		headerExtensions.setFieldNameValue(headerFieldNameValue);
		serviceParameter.setHeaderExtensions(headerExtensions);

		HeaderFieldNameValues headerFieldNameValues = new HeaderFieldNameValues();
		headerFieldNameValue = new ArrayList<FieldNameValue>();
		FieldNameValue headerNameValue = new FieldNameValue();
		headerNameValue.setName(SiteoneintegrationConstants.MULTIPLE_INVENTSITEID_LABEL);
		headerNameValue.setValue(SiteoneintegrationConstants.MULTIPLE_INVENTSITEID_VALUE);
		headerFieldNameValue.add(headerNameValue);
		headerFieldNameValues.setFieldNameValue(headerFieldNameValue);
		serviceParameter.setHeaderFieldNameValues(headerFieldNameValues);

		serviceParameter.setIdentifier(UUID.randomUUID().toString());

		priceSingle.setServiceParameter(serviceParameter);

		siteOneWsPriceRequestData.setPriceSingle(priceSingle);

		return siteOneWsPriceRequestData;
	}


	protected StringBuilder getNearbyStoreId (final ProductModel product,String storeId,List<PointOfServiceData> nearbyStoresList)
	{
		final StringBuilder innerStoreId = new StringBuilder();
		LOG.info("Nearby store id"+product.getCode());
		List<String> availabiliStoresList = product.getOnHandStores().stream().map(store->store.getStoreId()).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(availabiliStoresList)  && CollectionUtils.isNotEmpty(nearbyStoresList) && !availabiliStoresList.contains(storeId)) {
			for(PointOfServiceData store:nearbyStoresList){
				if(store.getIsNearbyStoreSelected()!=null && store.getIsNearbyStoreSelected().booleanValue() && availabiliStoresList.contains(store.getStoreId())){
					innerStoreId.append(store.getStoreId());
					LOG.info("++found stock in a nearby store - "+product.getCode()+"Nearby Store -"+store.getStoreId());
					break;
				}
			}
		}
		return innerStoreId;

	}

	/**
	 * @return the productService
	 */
	public ProductService getProductService() {
		return productService;
	}

	/**
	 * @param productService the productService to set
	 */
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * @return the siteOneCSPProductRequestConverter
	 */
	public Converter<ProductModel, SiteOneCSPProductRequestData> getSiteOneCSPProductRequestConverter() {
		return siteOneCSPProductRequestConverter;
	}

	/**
	 * @param siteOneCSPProductRequestConverter the siteOneCSPProductRequestConverter to set
	 */
	public void setSiteOneCSPProductRequestConverter(
			Converter<ProductModel, SiteOneCSPProductRequestData> siteOneCSPProductRequestConverter) {
		this.siteOneCSPProductRequestConverter = siteOneCSPProductRequestConverter;
	}

	/**
	 * @return the siteOneCSPRequestConverter
	 */
	public Converter<List<SiteOneCSPProductRequestData>, SiteOneCSPRequestData> getSiteOneCSPRequestConverter() {
		return siteOneCSPRequestConverter;
	}

	/**
	 * @param siteOneCSPRequestConverter the siteOneCSPRequestConverter to set
	 */
	public void setSiteOneCSPRequestConverter(
			Converter<List<SiteOneCSPProductRequestData>, SiteOneCSPRequestData> siteOneCSPRequestConverter) {
		this.siteOneCSPRequestConverter = siteOneCSPRequestConverter;
	}


	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<SiteOneWsPriceRequestData, SiteOneWsPriceResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	/**
	 * @param siteOneRestClient
	 *            the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			final SiteOneRestClient<SiteOneWsPriceRequestData, SiteOneWsPriceResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}



	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService() {
		return sessionService;
	}



	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public Converter<B2BUnitModel, B2BUnitData> getB2BUnitConverter() {
		return b2BUnitConverter;
	}

	public void setB2BUnitConverter(Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter) {
		b2BUnitConverter = b2bUnitConverter;
	}
	

}
