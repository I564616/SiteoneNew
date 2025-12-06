<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix ="fmt" uri ="http://java.sun.com/jsp/jstl/fmt"%>

<%-- 
<script type="text/javascript" src="${sharedResourcePath}/js/analyticsmediator.js"></script>
<analytics:googleAnalytics/>
<analytics:jirafe/>  --%>
   
<c:set var="searchAttribute" value="product-search" scope="application"/>
<c:set var="channel" value='${breadcrumbs[1].name}'/>
<c:if test='${empty breadcrumbs[1].name}'>
<c:set var="channel" value='${breadcrumbs[0].name}'/>
</c:if>

<c:if test="${cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'cartPage' || cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage' }">
<c:set var="channel" value="Checkout"/>	
</c:if>
 <c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'searchEmpty'}">
   <c:set var="channel" value='${breadcrumbs[0].name}'/>
   </c:if>
   <c:if test="${cmsPage.uid eq 'detailsSavedListPage' || cmsPage.uid eq 'savedListPage'}">
   <c:set var="channel" value='${breadcrumbs[0].name}'/>
   </c:if>
   <c:if test="${cmsPage.uid eq 'siteonestoredetailsPage'||cmsPage.uid eq 'storefinderPage '}">
   <c:set var="channel" value='Find A Branch'/>
   </c:if>
   <c:if test="${cmsPage.uid eq 'quickOrderPage'}">
   <c:set var="channel" value="Quick Order"/>
   </c:if>
<%--    <c:if test="${cmsPage.uid eq 'pressRoomPage'||cmsPage.uid eq 'pressRoomDetailPage'}">
   <c:set var="channel" value="Company Info"/>
   </c:if> --%>
   
   <c:if test="${cmsPage.uid eq 'requestaccount' || cmsPage.uid eq 'requestaccountsuccess'}">
    <c:set var="channel" value='Account Request'/>
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'accountDashboardPage'}">
   <c:set var="channel" value='My Account'/>
   </c:if>
	
<c:if test="${cmsPage.name eq 'HomePage'}">
<c:set var="channel" value="${cmsPage.name}"/>
</c:if>

<c:if test="${cmsPage.masterTemplate.uid eq 'SiteOneContentPageTemplate' or cmsPage.masterTemplate.uid eq 'HomeOwnerPageTemplate'  or cmsPage.masterTemplate.uid eq 'SiteOneEventPageTemplate' or cmsPage.uid eq 'sds-search'}">
<c:set var="channel" value=''/>
</c:if>
	
<script type="text/javascript">
   var oldURL = document.referrer;
   var rPath = oldURL.substring(oldURL.lastIndexOf('/') + 1);
   var analytics = {};
   var channel = '${channel}';
   var _AAData = {};
   var digitalData = {};
   _AAData.page = {};
   _AAData.language = {};
   var fireSearchLandingPage = false;
   
   _AAData.page.pageName = channel != "" ? '${channel}:${cmsPage.name}' : '${cmsPage.name}';
   
   <c:if test="${cmsPage.uid eq 'requestaccountsuccess'}">
   _AAData.applicationuuid='${siteOneRequestAccountForm.uuid}'
   </c:if>
   
   <c:if test="${loginFlagModel eq null}">
   _AAData.visitorStatus = 'not logged in';
   _AAData.agentID='';
   _AAData.pEmail = '';
    _AAData.pAccount = '';
    _AAData.pArea = '';
    _AAData.pRegion = '';
   </c:if>
   
   <c:if test="${loginFlagModel ne null}">
   _AAData.hashedEmailID='${hashedEmailID}';
   _AAData.encryptedEmailID='${encryptedEmailID}';
   _AAData.visitorStatus = 'logged in';
   _AAData.tradeClass ='${tradeClass}';
   _AAData.customerSegment='${customerSegment}';
   _AAData.customerType='${customerType}';
   _AAData.agentID='${asmAgentId ne null?asmAgentId : ''}';
   _AAData.pEmail = '${fn:replace(user.displayUid,"\'","")}';
    _AAData.pAccount = '${fn:replace(sessionShipTo.name,"\'","")}';
    _AAData.pArea = '${fn:replace(sessionStore.areaId,"\'","")}';
    _AAData.pRegion = '${fn:replace(sessionStore.regionId,"\'","")}';
   </c:if> 
   
   
   <c:if test="${cmsPage.uid eq 'notFound'}">
   <c:set var="errorPage" value='page not found'/>
   _AAData.page.pageName = channel != "" ? '${channel}:${errorPage}' : '${errorPage}';
   </c:if>
   _AAData.page.template = '${cmsPage.masterTemplate.name}';
   _AAData.channel = channel;
   if($('html').attr('lang')==='en'){
   _AAData.language.LanguageSelection = "english"; 
   }
   else if($('html').attr('lang')==='es'){
   _AAData.language.LanguageSelection = "spanish"; 
   }
    
   <c:if test="${cmsPage.uid eq 'address-book'}">
   _AAData.page.template = 'addresses';
   </c:if>
   <c:if test="${cmsPage.uid eq 'siteOneCheckoutPage'}">
   _AAData.page.template = 'Multi Step Checkout Summary Page Template';
  </c:if>  
   
   <c:if test="${cmsPage.uid eq 'requestaccount' || cmsPage.uid eq 'requestaccountsuccess'}">
    _AAData.page.template = 'account/registration';
  </c:if>

  
   <c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'searchEmpty'}"> 
      _AAData.GUID="${user.guid}";
      _AAData.companyID = "${sessionShipTo.uid}";
      
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'linkConfirmationPage'}">
   _AAData.orderAmount="${linkOrderAmount}";
   _AAData.orderNumber="${linkOrderNumber}";
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'searchGrid'}">

  <c:set var="searchText" value='${fn:trim(fn:replace(breadcrumbs[1].name,"Results for",""))}'/> 
  <c:set var="searchText" value='${fn:replace(searchText,"\'","")}'/>
  <c:set var="searchText" value="${fn:replace(searchText,'\"','')}"/>
  <c:set var="searchText" value='${fn:replace(searchText,";","")}'/>
  <c:set var="searchText" value='${fn:replace(searchText,"//","")}'/>
    _AAData.productSearchTerm='${searchText}';
      <c:forEach items="${searchPageData.breadcrumbs}" var="facets">
      <c:if test="${facets.facetValueCode ne true}">
	    <c:set var="facetsName" value="${index.first ? '' : facetsName}${facets.facetValueName}|" />
	    </c:if>
	  </c:forEach>
	 <c:forEach items="${searchPageData.results}" var="product">
	   <c:set var="productSKU" value="${index.first ? '' :productSKU} ${product.code}|" />
	   <c:set var="productName" value="${index.first ? '' :productName} ${product.productShortDesc}|" />
	 <c:if test ="${not empty product.productBrandName}">
	  <c:set var="productBrand" value="${index.first ? '' :productBrand} ${product.productBrandName}|" />
	  </c:if>
	   </c:forEach>
		<c:set var="SKU" value= "${fn:replace(productSKU,' ','')}"/>
	   <c:set var="prodName" value= '${fn:replace(fn:replace(productName,"\\"","\'")," ","")}'/>
	   <c:set var="brands" value= '${fn:replace(fn:replace(productBrand," ",""),"\\"","\'")}'/>
	   _AAData.productSKU="${fn:substring(SKU,0,fn:length(SKU)-1)}";
	    
   _AAData.productSearchFilter='${fn:substring(facetsName,0,fn:length(facetsName)-1)}';  
   _AAData.searchIndex="${searchPageData.pagination.currentPage + 1}";
   _AAData.eventType = "search-product";
   _AAData.productSearchNoOfResults= "${searchPageData.pagination.totalNumberOfResults}";
  </c:if> 
   
  <c:if test="${cmsPage.uid eq 'searchEmpty'}">
  <c:set var="failedSearchText" value='${fn:trim(fn:replace(breadcrumbs[1].name,"Results for",""))}'/> 
  <c:set var="failedSearchText" value='${fn:replace(failedSearchText,"\'","")}'/>
  <c:set var="failedSearchText" value="${fn:replace(failedSearchText,'\"','')}"/>
  <c:set var="failedSearchText" value='${fn:replace(failedSearchText,";","")}'/>
  <c:set var="failedSearchTerm" value='${fn:replace(failedSearchText,"//","")}'/>
   _AAData.failedProductSearchTerm='${failedSearchTerm}';
   _AAData.eventType = "search-product";	 
   _AAData.productSearchTerm= '${failedSearchTerm}';
   _AAData.productSearchNoOfResults= "Zero"
  </c:if> 
  
   <c:if test="${cmsPage.uid eq 'productDetails'}">
   <c:set var="productBrand" value='${fn:replace(fn:replace(product.productBrandName," ",""),"\\"","\'")}'/>
   <c:set var="productName" value='${fn:replace(fn:replace(product.name," ",""),"\\"","\'")}'/>
   _AAData.productSKU = "${product.code}";
  try{
   _satellite.track("dtm-event-user");
   }catch (e) {}
   <c:if test = "${not empty searchText}">
	 _AAData.eventType = "search-product"; 
	 _AAData.productSearchTerm = "${searchText}";
	 _AAData.productSearchNoOfResults="1";
	</c:if>
	
	<c:if test = "${empty searchText}">
	_AAData.eventType = "prod-details"; 
	_AAData.productSearchTerm = "";
	_AAData.productSearchNoOfResults="";
	</c:if>
   </c:if>

  _AAData.storeNumber = "${sessionStore.storeId}";
  _AAData.storeState =  "${sessionStore.address.region.isocodeShort}";
   <c:if test="${cmsPage.uid eq 'storefinderPage'}">
   _AAData.userStoreNumber = "${sessionStore.storeId}";
   _AAData.userStoreState =  "${sessionStore.address.region.isocodeShort}";
   <c:if test="${param.confirmStore eq true}"> 
   try{
   _satellite.track("makeMyStore"); 
   }catch (e) {}
   </c:if>
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'lightingCategoryLandingPage' || cmsPage.uid eq 'LandscapeSupplyCategoryLandingPage' || cmsPage.uid eq 'AgronomicMaintenanceCategoryLandingPage' || cmsPage.uid eq 'IrrigationCategoryLandingPage' || cmsPage.uid eq 'Hardscapes&OutdoorLivingCategoryLandingPage' || cmsPage.uid eq 'NurseryCategoryLandingPage' || cmsPage.uid eq 'ToolsEquipment&SafetyCategoryLandingPage' }">
   <c:forEach items="${categoryData.subCategories}" var="subCategories">
   <c:set var="prodSubcategory" value="${index.first ? '' :prodSubcategory} ${subCategories.name}|" />
   </c:forEach> 
   <c:set var="subcategory" value='${fn:replace(fn:replace(prodSubcategory," ",""),"\\"","\'")}'/>
   <c:if test = "${not empty searchText}">
	 _AAData.eventType = "search-product"; 
	 _AAData.productSearchTerm = "${searchText}";
	 _AAData.productSearchNoOfResults="ZERO";
	</c:if>
	
	<c:if test = "${empty searchText}">
	_AAData.eventType = "catalog"; 
	_AAData.productSearchTerm = "";
	_AAData.productSearchNoOfResults="";
	</c:if>
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'productGrid'}">
   <c:forEach items="${searchPageData.results}" var="product">
   <c:set var="productSKU" value="${index.first ? '' :productSKU} ${product.code}|" />
   <c:set var="productName" value='${index.first ? "" :productName} ${product.productShortDesc}|' />
   <c:if test ="${not empty product.productBrandName}">
   <c:set var="productBrand" value="${index.first ? '' :productBrand} ${product.productBrandName}|" />
   </c:if>
   <c:set var="productUnit" value="${index.first ? '' :productUnit} 1|" />
   <c:choose>
   <c:when test="${product.multidimensional and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
   <c:set var="price" value="${product.priceRange.minPrice.value}-${product.priceRange.maxPrice.value}"/>
   </c:when>
   <c:when test="${product.multidimensional and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
   <c:set var="price" value="${product.priceRange.minPrice.value}"/>
   </c:when>
   <c:otherwise>
   <c:set var="price" value="${product.price.formattedValue}"/>
   </c:otherwise>
   </c:choose>
   <c:set var="productPrice" value="${index.first ? '' :productPrice} ${price}|"/> 
   </c:forEach>   
   <c:set var="SKU" value= "${fn:replace(productSKU,' ','')}"/>
   <c:set var="prodName" value='${fn:replace(fn:replace(productName," ",""),"\\"","\'")}'/>
   <c:set var="brands" value= '${fn:replace(fn:replace(productBrand," ",""),"\\"","\'")}'/>
   <c:set var="unit" value= "${fn:replace(productUnit,' ','')}"/>
   <c:set var="price" value= "${fn:replace(fn:replace(fn:replace(productPrice, '$', ''),',',''),' ','')}"/>
   _AAData.productSKU="${fn:substring(SKU,0,fn:length(SKU)-1)}";
   _AAData.productUnit="${fn:substring(unit,0,fn:length(unit)-1)}";
   _AAData.productPrice="${fn:substring(price,0,fn:length(price)-1)}";
   try{ 
   _satellite.track("dtm-event-user"); 
   }catch (e) {}
   <c:if test = "${not empty searchText}">
	 _AAData.eventType = "search-product"; 
	 _AAData.productSearchTerm = "${searchText}";
	 _AAData.productSearchNoOfResults="${searchPageData.pagination.totalNumberOfResults}";
	</c:if>
	
	<c:if test = "${empty searchText}">
	_AAData.eventType = "catalog"; 
	_AAData.productSearchTerm = "";
	 _AAData.productSearchNoOfResults="";
	</c:if>
	
   </c:if>

  <c:if test="${cmsPage.uid eq 'articleLandingPage' || fn:contains(pageBodyCssClasses, 'template-pages-layout-siteOneContentLayout')}">
	 	<c:if test = "${not empty searchText}">
			 _AAData.eventType = "search-product"; 
			 _AAData.productSearchTerm = "${searchText}";
			 _AAData.productSearchNoOfResults="1";
       fireSearchLandingPage = true;
			
		</c:if>
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'compareProductPage'}">
   _AAData.channel = "Compare";
   _AAData.page.pageName = 'Compare:${cmsPage.name}'
   <c:forEach items="${productList}" var="product"> 
   <c:if test ="${not empty product.productBrandName}">
   <c:set var="productBrand" value="${index.first ? '' :productBrand} ${product.productBrandName}|" />
   </c:if>
   <c:set var="productSKU" value="${index.first ? '' :productSKU} ${product.code}|" />
   <c:set var="productName" value="${index.first ? '' :productName} ${product.name}|" /> 
   <c:set var="productUnit" value="${index.first ? '' :productUnit} 1|" />
   <c:choose>
   <c:when test="${product.multidimensional and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
   <c:set var="price" value="${product.priceRange.minPrice.formattedValue}-${product.priceRange.maxPrice.formattedValue}"/>
   </c:when>
   <c:when test="${product.multidimensional and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
   <c:set var="price" value="${product.priceRange.minPrice.formattedValue}"/>
   </c:when>
   <c:otherwise>
   <c:set var="price" value="${product.price.formattedValue}"/>
   </c:otherwise>
   </c:choose>
   <c:set var="productPrice" value="${index.first ? '' :productPrice} ${price}|"/>
   <c:set var="productCategory" value="${index.first ? '' :productCategory}${product.categories[0].parentCategoryName}|" />
   <c:set var="productSubCategory" value="${index.first ? '' :productSubCategory}${product.categories[0].name}|" />
   </c:forEach>   
   <c:set var="brands" value= '${fn:replace(fn:replace(productBrand," ",""),"\\"","\'")}'/>
   <c:set var="SKU" value= "${fn:replace(productSKU,' ','')}"/>
   <c:set var="prodName" value= '${fn:replace(fn:replace(productName," ",""),"\\"","\'")}'/>
   <c:set var="unit" value= "${fn:replace(productUnit,' ','')}"/>
   <c:set var="price" value= "${fn:replace(fn:replace(fn:replace(productPrice, '$', ''),',',''),' ','')}"/>
   <c:set var="prodCategory" value= '${fn:replace(fn:replace(productCategory," ",""),"\\"","\'")}'/>
   <c:set var="prodSubCategory" value= '${fn:replace(fn:replace(productSubCategory," ",""),"\\"","\'")}'/>
   _AAData.productSKU="${fn:substring(SKU,0,fn:length(SKU)-1)}";
   _AAData.productUnit="${fn:substring(unit,0,fn:length(unit)-1)}";
   _AAData.productPrice="${fn:substring(price,0,fn:length(price)-1)}";
   _AAData.eventType='compare';
   try{
   _satellite.track("dtm-event-user"); 
   }catch (e) {}
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'orderConfirmationPage'}">
   var item = [];
   <c:choose>
		<c:when test="${isMixedCartEnabled}">
			<c:set var="revenue" value="${fn:replace(fn:replace(orderData.totalPrice.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalTax.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.deliveryFreight, '$', ''),',','') + fn:replace(fn:replace(orderData.shippingFreight, '$', ''),',','')}"/>
			<c:set var="deliveryCostVar" value="${orderData.deliveryFreight}"/>
			<c:set var="shippingCostVar" value="${orderData.shippingFreight}"/>
		</c:when>
		<c:otherwise>
			<c:set var="revenue" value="${fn:replace(fn:replace(orderData.subTotal.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalTax.formattedValue, '$', ''),',','')-fn:replace(fn:replace(orderData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.deliveryCost.formattedValue, '$', ''),',','')}"/>
			
			
				<c:if test="${orderData.orderType eq 'DELIVERY'}">
			<c:set var="deliveryCostVar" value="${fn:replace(orderData.deliveryCost.formattedValue, '$', '')}"/>
		</c:if>
		<c:if test="${orderData.orderType eq 'PARCEL_SHIPPING'}">
			<c:set var="shippingCostVar" value="${fn:replace(orderData.deliveryCost.formattedValue, '$', '')}"/>
		</c:if>
		</c:otherwise>
	</c:choose>
   <c:choose>
		<c:when test="${orderData.siteOnePaymentInfoData.paymentType eq '3'}">
			<c:set var="confirmationPaymentMethod" value="Pay with Credit or Debit Card"/>	
		</c:when>
		<c:when test="${orderData.siteOnePaymentInfoData.paymentType eq '2'}">	
			<c:set var="confirmationPaymentMethod" value="Pay at branch"/>
		</c:when>
		<c:when test="${orderData.siteOnePOAPaymentInfoData.paymentType eq '1'}">			
			<c:set var="confirmationPaymentMethod" value="SiteOne Online Account"/>
		</c:when>
		<c:otherwise>	
			<c:set var="confirmationPaymentMethod" value="Pay at branch"/>	
		</c:otherwise>
	</c:choose>
   _AAData.orderNumber ="${orderData.code}";
   _AAData.orderType =  "Standard";   
   _AAData.deliveryCost="${deliveryCostVar}";
   _AAData.paymentMethod="${confirmationPaymentMethod}";
   <c:set var="accountExist" value="YES"/>
   <c:if test="${' ' eq order.orderingAccount.uid}">
   <c:set var="accountExist" value="NO"/>
   </c:if>
   <c:forEach items="${orderData.unconsignedEntries}" var="product">
   <c:set var="productName" value="${index.first ? '' :productName}${product.product.name}|" />
   <c:set var="productCode" value="${index.first ? '' :productCode}${product.product.code}|" />
   <c:set var="productBrandName" value="${index.first ? '' :productBrandName}${product.product.productBrandName}|" />
   <c:set var="productUnits" value="${index.first ? '' :productUnits}${product.quantity}|"/>
   <c:set var="productPrice" value="${index.first ? '' :productPrice}${product.totalPrice.formattedValue}|"/>
   <c:set var="productCategory" value="${index.first ? '' :productCategory}${product.product.categories[0].parentCategoryName}|" />
   <c:set var="productSubCategory" value="${index.first ? '' :productSubCategory}${product.product.categories[0].name}|" />
   <c:set var="units" value="${units+product.quantity}" />
   <c:set var="purchaseProducts" value="${index.first ? '' :purchaseProducts}${product.product.code}:${product.quantity}:${product.totalPrice.formattedValue}:${orderData.pointOfService.address.town}:${orderData.pointOfService.address.region.isocodeShort}:${orderData.pointOfService.name}:${orderData.pointOfService.storeId}:${orderData.code}|"/>
   <c:set var="productFulfillment" value="${product.deliveryMode.code}" />
   <c:if test="${empty orderData.orderType}">
   <c:choose>
   <c:when test="${productFulfillment eq 'pickup'}">
	   <c:set var="fulfillmentInstruction" value="${orderData.pickupInstruction}"/>
   </c:when>
   <c:when test="${productFulfillment eq 'standard-net'}">
   	   <c:set var="fulfillmentInstruction" value="${orderData.deliveryInstruction}"/>
   </c:when>
   <c:when test="${productFulfillment eq 'free-standard-shipping'}">
		<c:set var="fulfillmentInstruction" value="${orderData.shippingInstruction}"/>
	</c:when>
   </c:choose>
   </c:if>
   <c:if test="${not empty orderData.orderType}">
       <c:set var="fulfillmentInstruction" value="${pickupInstruction}"/>
   </c:if>
   <c:set var="storeStockAvailabilityMsg" value="${product.product.storeStockAvailabilityMsg.replaceAll('<[^>]*>', '')}"/>
   
  <fmt:formatNumber var="formattedBasePrice" maxFractionDigits="2" minFractionDigits="2" value="${product.totalPrice.value/product.quantity}"/>
	  <c:if test="${productFulfillment eq 'pickup'}">
  <c:choose>
  <c:when test="${isMixedCartEnabled}">
  <c:choose>
  <c:when test="${orderData.pointOfService.storeId == product.deliveryPointOfService.storeId}">
  	<c:set var="productFulfillmentModified" value="pickuphome" />
  </c:when>
  <c:otherwise>
  	<c:set var="productFulfillmentModified" value="pickupnearby" />
  </c:otherwise>
  </c:choose>
  </c:when>
  <c:otherwise>
  	<c:set var="productFulfillmentModified" value="PICKUP" />
  </c:otherwise>
  </c:choose>
  </c:if>
  	<c:if test="${productFulfillment eq 'standard-net'}">
  <c:choose>
  	<c:when test="${isMixedCartEnabled}">
  <c:set var="productFulfillmentModified" value="delivery" />
  </c:when>
  <c:otherwise>
  	<c:set var="productFulfillmentModified" value="DELIVERY" />
  </c:otherwise>
  </c:choose>
  </c:if>
  <c:if test="${productFulfillment eq 'free-standard-shipping'}">
	  <c:choose>
	<c:when test="${isMixedCartEnabled}">
	<c:set var="productFulfillmentModified" value="shipping" />
	</c:when>
	<c:otherwise>
		<c:set var="productFulfillmentModified" value="PARCEL_SHIPPING" />
	</c:otherwise>
	</c:choose>
  
  </c:if>
  <c:set var="productPricecheck" value="${product.basePrice.formattedValue.replace('$', '')}" />
	   
	 
  
   item.push({
	      "quantity": "${product.quantity}",
	      "price": {
	    	  "basePrice": "${formattedBasePrice}"
	      },
	      "productInfo": {
	          "productID": "${product.product.code}",
	          "productCategory": "${product.product.level1Category}",
	          "productSubCategory": "${product.product.level2Category}",
	          "productName": "${fn:replace(product.product.name, '\"', '\\\"')}",
	          "productPrice": "${productPricecheck}",
	          "productFulfillment": "${productFulfillmentModified}",
	          "productStore": "${product.deliveryPointOfService.storeId}",
	          "fulfillmentInstruction": "${fulfillmentInstruction}",
	          "productStockStatus":	"${storeStockAvailabilityMsg}",
	      }
	  });
   
   </c:forEach>
    <c:set var="prodName" value= '${fn:replace(fn:replace(productName," ",""),"\\"","\'")}'/>
    <c:set var="prodCategory" value= '${fn:replace(fn:replace(productCategory," ",""),"\\"","\'")}'/>
    <c:set var="prodSubCategory" value= '${fn:replace(fn:replace(productSubCategory," ",""),"\\"","\'")}'/>
    <c:set var="prodBrand" value='${fn:replace(fn:replace(productBrandName," ",""),"\\"","\'")}'/>
    <c:set var="price" value= "${fn:replace(fn:replace(fn:replace(productPrice, '$', ''),',',''),' ','')}"/>
    <c:set var="discountAmount" value="${fn:replace(orderData.totalDiscounts.formattedValue,'$','')}"/>
    
    <c:set var="subTotal" value="${fn:replace(orderData.subTotal.formattedValue,'$','')}"/>
    <c:set var="accountNo" value="${orderData.orderingAccount.displayId}"/>
    
    	
    
    <c:forEach items="${orderData.appliedVouchers}" var="voucher" varStatus="loop">
	<c:choose>
		<c:when test="${fn:length(order.appliedVouchers) gt 1 and !loop.last}">
		<c:set var="promotionCode" value="${voucher},"/>
		</c:when>
		<c:when test="${fn:length(order.appliedVouchers) gt 1 and loop.last}">
		<c:set var="promotionCode" value="${voucher}"/>
		</c:when>
		<c:otherwise>
		<c:set var="promotionCode" value="${voucher}"/>
		</c:otherwise>
   	</c:choose>
	</c:forEach>
	
	  
		
    _AAData.productSKU="${fn:substring(productCode,0,fn:length(productCode)-1)}";
    _AAData.units="${fn:substring(productUnits,0,fn:length(productUnits)-1)}";
    _AAData.productPrice="${fn:substring(price,0,fn:length(price)-1)}";
   _AAData.purchasedProducts="${fn:substring(purchaseProducts,0,fn:length(purchaseProducts)-1)}";
   _AAData.accountNumberAvailable="${accountExist}";
   _AAData.deliveryMethod="${orderData.orderType}";
   _AAData.poNumber="${orderData.purchaseOrderNumber}";
   
   _AAData.productAvailabilityStatus = "${availabiltyStatus}",
   
   <c:choose>
	   <c:when test="${orderData.chooseLift eq  true}">
			<c:set var="fulfillmentOption" value="Lift"/>
		</c:when>
		<c:otherwise>
		<c:set var="fulfillmentOption" value="No Lift"/>
		</c:otherwise>
	</c:choose>
		
	 
		
   _AAData.fulfillmentOption="${fulfillmentOption}",
  
   <c:set var="totalTaxValue" value="${fn:replace(orderData.totalTax.formattedValue,'$','')}"/>
   <c:set var="formattedtax" value="${fn:replace(totalTaxValue,',','')}"/>
   _AAData.taxAmount="${formattedtax}";
  
   <c:if test="${discountAmount ne '0.00'}">
   _AAData.discountAmount="${discountAmount}";
   </c:if>
   <c:if test="${discountAmount eq '0.00'}">
   _AAData.discountAmount="";
   </c:if>
   _AAData.shippingFee="${shippingCostVar}";
   _AAData.totalPurchaseAmount="${fn:replace(fn:replace(orderData.totalPrice.formattedValue,'$',''),',','')}";
   <c:if test="${not empty orderData.appliedVouchers}">
   _AAData.discountCode="${orderData.appliedVouchers}"
   </c:if>
   <c:if test="${empty orderData.appliedVouchers}">
   _AAData.discountCode="";
   </c:if>
   <fmt:formatNumber var="formattedrevenue" type="number" minFractionDigits="2" maxFractionDigits="2" value="${revenue}" />
   _AAData.subTotal="${formattedrevenue}";
      
   digitalData.transaction = [{
	    "accountNo": "${accountNo}",
	    "poNumber": _AAData.poNumber,
	    "promotionCode": "${promotionCode}",
	    "discountAmount": _AAData.discountAmount,
	    "deliveryMethod": _AAData.deliveryMethod,
	    "tax": _AAData.taxAmount,
	    "subTotal": "${subTotal}",
	    "shippingFee": _AAData.shippingFee,
	    "totalPurchaseAmount": _AAData.subTotal,
	    "item": item
	}];
	
   digitalData.cart= {
		   cartAge:"${cartAge}",
		   event: "purchase"
		   
   };
   
   _AAData.subTotal="${orderData.subTotal.value}";
   _AAData.totalPurchaseAmount="${fn:replace(formattedrevenue,',','')}";
   
   <c:if test="${orderData.productDiscounts.value > 0}">
   _AAData.discount=${orderData.productDiscounts.value}
   </c:if>
   <c:if test="${orderData.orderDiscounts.value > 0}">
   _AAData.couponDiscount=${orderData.orderDiscounts.value}
   </c:if>
   
   <c:if test="${orderData.orderType eq 'PICKUP' || order.orderType eq 'FUTURE_PICKUP' }">
   _AAData.fulfillmentMethod="Branch Pickup";
   </c:if>
   <c:if test="${orderData.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY' || order.orderType eq 'DIRECT_SHIP' }">
   _AAData.fulfillmentMethod="Local Delivery";
   </c:if>
   <c:if test="${orderData.orderType eq 'PARCEL_SHIPPING'}">
   _AAData.fulfillmentMethod="Shipping";
   </c:if>
   
   _AAData.eventType="order-confirmation";
   try{
   _satellite.track("dtm-event-user"); 
   }catch (e) {}
 </c:if>
   
   <c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
   _AAData.orderType =  "Standard";  
   <c:choose>
   		<c:when test="${isMixedCartEnabled}">
   			<c:set var="revenue" value="${fn:replace(fn:replace(cartData.totalPrice.formattedValue, '$', ''),',','')+fn:replace(fn:replace(cartData.totalTax.formattedValue, '$', ''),',','')+fn:replace(fn:replace(cartData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(cartData.deliveryFreight, '$', ''),',','') + fn:replace(fn:replace(cartData.shippingFreight, '$', ''),',','')}"/>
   			<c:set var="deliveryCostVar" value="${cartData.deliveryFreight}"/>
   			<c:set var="shippingCostVar" value="${cartData.shippingFreight}"/>
   		</c:when>
   		<c:otherwise>
   			<c:set var="revenue" value="${fn:replace(fn:replace(cartData.subTotal.formattedValue, '$', ''),',','')+fn:replace(fn:replace(cartData.totalTax.formattedValue, '$', ''),',','')-fn:replace(fn:replace(cartData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(cartData.deliveryCost.formattedValue, '$', ''),',','')}"/>
   			<c:if test="${cartData.orderType eq 'DELIVERY'}">
   				<c:set var="deliveryCostVar" value="${fn:replace(cartData.deliveryCost.formattedValue, '$', '')}"/>
   			</c:if>
   			<c:if test="${cartData.orderType eq 'PARCEL_SHIPPING'}">
   				<c:set var="shippingCostVar" value="${fn:replace(cartData.deliveryCost.formattedValue, '$', '')}"/>
   			</c:if>
   			
   		</c:otherwise>
   </c:choose>
   _AAData.deliveryCost="${deliveryCostVar}";  
   <c:set var="accountExist" value="YES"/>
   <c:if test="${' ' eq order.orderingAccount.uid}">
   <c:set var="accountExist" value="NO"/>
   </c:if>
   <c:forEach items="${allItems}" var="product">
   <c:set var="productName" value="${index.first ? '' :productName}${product.product.name}|" />
   <c:set var="productCode" value="${index.first ? '' :productCode}${product.product.code}|" />
   <c:set var="productBrandName" value="${index.first ? '' :productBrandName}${product.product.productBrandName}|" />
   <c:set var="productUnits" value="${index.first ? '' :productUnits}${product.quantity}|"/>
   <c:set var="productPrice" value="${index.first ? '' :productPrice}${product.totalPrice.formattedValue}|"/>
   <c:set var="purchaseProducts" value="${index.first ? '' :purchaseProducts}${product.product.code}:${product.quantity}:${product.totalPrice.formattedValue}:${cartData.pointOfService.address.town}:${cartData.pointOfService.address.region.isocodeShort}:${cartData.pointOfService.name}:${cartData.pointOfService.storeId}:${cartData.code}|"/>
   <c:set var="productCategory" value="${index.first ? '' :productCategory}${product.product.categories[0].parentCategoryName}|" />
   <c:set var="productSubCategory" value="${index.first ? '' :productSubCategory}${product.product.categories[0].name}|" />
    </c:forEach>
    <c:set var="prodName" value= '${fn:replace(fn:replace(productName," ",""),"\\"","\'")}'/>
    <c:set var="price" value= "${fn:replace(fn:replace(fn:replace(productPrice, '$', ''),',',''),' ','')}"/>
    <c:set var="prodCategory" value= '${fn:replace(fn:replace(productCategory," ",""),"\\"","\'")}'/>
   <c:set var="prodSubCategory" value= '${fn:replace(fn:replace(productSubCategory," ",""),"\\"","\'")}'/>
    <c:set var="prodBrand" value='${fn:replace(fn:replace(productBrandName," ",""),"\\"","\'")}'/>
   _AAData.productSKU="${fn:substring(productCode,0,fn:length(productCode)-1)}";
   _AAData.units="${fn:substring(productUnits,0,fn:length(productUnits)-1)}";
   _AAData.productPrice="${fn:substring(price,0,fn:length(price)-1)}";
   _AAData.purchasedProducts="${fn:substring(purchaseProducts,0,fn:length(purchaseProducts)-1)}";
   _AAData.accountNumberAvailable="${accountExist}";
   _AAData.deliveryMethod="${cartData.orderType}";
   _AAData.poNumber="${cartData.purchaseOrderNumber}";
   _AAData.taxAmount="${fn:replace(cartData.totalTax.formattedValue,'$','')}";
   <c:set var="discountAmount" value="${fn:replace(cartData.totalDiscounts.formattedValue,'$','')}"/>
   <c:if test="${discountAmount ne '0.00'}">
   _AAData.discountAmount= "${discountAmount}";
   </c:if>
   <c:if test="${discountAmount eq '0.00'}">
	_AAData.discountAmount="";
   </c:if>
   _AAData.shippingFee="${shippingCostVar}";
   <fmt:formatNumber var="formattedrevenue" type="number" minFractionDigits="2" maxFractionDigits="2" value="${revenue}" />
   _AAData.totalPurchaseAmount="${fn:replace(formattedrevenue,',','')}";
   <c:if test="${not empty cartData.appliedVouchers}">
   _AAData.discountCode="${cartData.appliedVouchers}"
   </c:if>
   <c:if test="${empty cartData.appliedVouchers}">
   _AAData.discountCode="";
   </c:if>
   
   _AAData.subTotal="${fn:replace(fn:replace(cartData.subTotal.formattedValue,'$',''),',','')}";
   try{
   _satellite.track("dtm-event-user"); 
   }catch (e) {}
 </c:if>
 
 <c:if test="${cmsPage.uid eq 'requestaccount'}">
 _AAData.eventType= "acct-request-confirmation";
 try{
 _satellite.track("dtm-event-user");
	 }
 	catch (e) {}
</c:if>

<c:if test="${cmsPage.uid eq 'requestaccountsuccess'}">
_AAData.eventType="acct-create-confirmation";
try{
_satellite.track("dtm-event-user");
}catch (e) {}
</c:if>

<c:if test="${cmsPage.uid eq 'siteonehomepage'}">
_AAData.eventType= "homepage";

</c:if>


  
  //POST PAGE EVENTS - Called after page load 
  function postPageEvent() {
	  <c:if test="${cmsPage.uid eq 'siteonestoredetailsPage'}">
	     _AAData.userStoreNumber = "${store.storeId}";
	     _AAData.userStoreState =  "${store.address.region.isocodeShort}";
	     _AAData.eventType  = "confirm-store";
	     try{
	     _satellite.track("dtm-event-user"); 
	     }catch (e) {}
	   </c:if> 
	  
	  if (window.location.pathname == '/login'){
		 try{
		  _satellite.track("dtm-event-user"); 
	  	}catch (e) {}
	  }
  
	   

   var eventType = [
   <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
 
   {referrer:"savedList/createList", eventType:"create-list"},
   {referrer:"assembly/createList", eventType:"create-assembly"},
   {referrer:"checkout/multi/order-type/choose", eventType:"submit-purchase"} 
   </sec:authorize>
   ];

      
     for(var i=0;i<eventType.length;++i){
        if(document.referrer.indexOf(eventType[i].referrer) > 0){
             <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
              _AAData.GUID="${user.guid}";
              _AAData.companyID = "${sessionShipTo.uid}";
              _AAData.userType = "${isNewCustomer}";
             </sec:authorize>  
         _AAData.userStoreNumber = "${sessionStore.storeId}";
	     _AAData.userStoreState =  "${sessionStore.address.region.isocodeShort}";
          <c:choose>
          <c:when test="${not empty isNewCustomer}">
          _AAData.userType = "${isNewCustomer}";
        	</c:when>
          <c:otherwise>
        	  _AAData.userType = "returning";
        	</c:otherwise>
        	</c:choose> 
          _AAData.eventType = eventType[i].eventType;
        try{
          _satellite.track("dtm-event-user"); 
        	}catch (e) {}
          
        }
     }
     <c:if test="${loginFlagModelValue eq false && cmsPage.uid eq 'sds-search'}">
      _AAData.eventType="search-sds-label";
      </c:if>
     
      <c:if test="${cmsPage.uid eq 'notFound'}">
        _AAData.systemErrors = "page is not found";
        try{
        _satellite.track("dtm-event-error");
        }catch (e) {}
      </c:if>
  } 

    <c:if test="${cmsPage.uid eq 'emailSignUpPage'}">
   _AAData.eventType="email-signup";
   </c:if>
   
   <c:if test="${cmsPage.uid eq 'pointsForEquipmentSuccess'}">
   _AAData.eventType="partner-reg-confirmation";
   </c:if>
	
  <c:if test="${cmsPage.uid eq 'sds-search'}">
  <c:set var="label" value="${metatags.get(0).content}"/>;
  _AAData.searchLabel="${label}";
  
  </c:if>
  <c:if test="${cmsPage.uid eq 'siteOnePromotionSearchPage'}">
  _AAData.eventType="promo";
  _AAData.promoName="${promotionData.code}";
  </c:if>
	<c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
	_AAData.error="${errorMsg}";
	</c:if>
	
	   <c:if test="${cmsPage.uid eq 'detailsSavedListPage'}">	  
	   _AAData.eventType="list-page";
	   </c:if>
	   
	   <c:if test="${cmsPage.uid eq 'detailsAssemblyPage'}">	  
	   _AAData.eventType="assembly-page";
	   </c:if>
	   <c:if test="${cmsPage.uid eq 'order'}">	  
	   _AAData.eventType="Order Detail";
	   </c:if>
	 <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')"> 
	    <c:if test="${user.uid ne 'anonymous'}">
	   _AAData.GUID="${user.guid}";
	   _AAData.companyID = "${sessionShipTo.uid}";
	   _AAData.userStoreNumber = "${sessionStore.storeId}";
	   _AAData.userStoreState =  "${sessionStore.address.region.isocodeShort}";
	  <c:choose>
      <c:when test="${not empty isNewCustomer}">
      _AAData.userType = "${isNewCustomer}";
    	</c:when>
      <c:otherwise>
    	  _AAData.userType = 'returning';
    	</c:otherwise>
    	</c:choose>
	  </c:if>
	  </sec:authorize>
	  
	  <%  Boolean loginFlagModelValue = (Boolean) ((session.getAttribute("loginFlagModel") != null) ? session.getAttribute("loginFlagModel") : false );
		if (loginFlagModelValue) {
	          session.setAttribute("loginFlagModel", false); %>
	          _AAData.eventType= 'login';
	          
		<%	
		}
	  %>
	
</script>

<c:set var="isAdobeAnalyticsEnable" value="true" />
<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
	<c:set var="isAdobeAnalyticsEnable" value="false" />
</c:if>

<c:if test="${isAdobeAnalyticsEnable eq true}">
<c:if test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp')}"> 
<c:set var="prodAnalytics" value="<%=de.hybris.platform.util.Config.getParameter(\"prodAnalytics\")%>"/>
<c:choose>
  <c:when test="${prodAnalytics}">
      <script src="//assets.adobedtm.com/launch-EN5dfbd141e22f428c9d7b74e9899499cb.min.js" async></script>
    </c:when>
  <c:otherwise>
    <script src="//assets.adobedtm.com/launch-EN137fa872e4554425b4c285c1a6443e53-development.min.js" async></script> 
  </c:otherwise>
</c:choose>
</c:if>
</c:if>

<c:if test="${isAdobeAnalyticsEnable eq false}">
<script type="text/javascript">
window._satellite={
	track: function(){console.log("adobe track disabled")}
}
</script>
</c:if>