<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix ="fmt" uri ="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="isLoggedIn" value='${loginFlagModel ne null}' />
<c:set var="currentUrl" value="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}" />
<c:if test="${requestScope['jakarta.servlet.forward.query_string'] != null}">
	<c:set var="currentUrl" value="${currentUrl}?${requestScope['jakarta.servlet.forward.query_string']}" />
</c:if>

<c:set var="refererUrl" value="${request.getHeader('referer')}" />
<c:set var="pageTitle" value="${not empty pageTitle ? pageTitle : not empty cmsPage.title ? cmsPage.title : 'Accelerator Title'}" />

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

<c:choose>
	<c:when test="${empty channel}">
		<c:set var="pageType" value="${cmsPage.name}" />
	</c:when>
	<c:otherwise>
		<c:set var="pageType" value="${channel}:${cmsPage.name}" />
	</c:otherwise>
</c:choose>

<c:if test="${cmsPage.uid eq 'compareProductPage'}">
	<c:set var="pageType" value='Compare:${cmsPage.name}'/>
</c:if>

<c:if test="${cmsPage.uid eq 'notFound'}">
	<c:set var="errorPage" value='page not found'/>
	<c:when test="${empty channel}">
		<c:set var="pageType" value="${errorPage}" />
	</c:when>
	<c:otherwise>
		<c:set var="pageType" value="${channel}:${errorPage}" />
	</c:otherwise>
</c:if>

<script> 
var dataLayer = dataLayer || []; 

dataLayer.push({
	'event': 'page_view',
	'page_location': '${currentUrl}',
	'page_title' : '${fn:escapeXml(pageTitle)}',
	'page_referrer' : '${refererUrl}',
	'page_type' : '${pageType}',
	'user_id': '${sessionShipTo.uid}'
});

var ga4analytics = {
	currency: "${currentBaseStoreId eq 'siteoneCA' ? 'CAD' : 'USD'}",
	<c:choose>
		<c:when test="${isLoggedIn}">
			isloggedin: true,
			visitor_status: 'logged in',
			account_classification: '${account_classification}',
		</c:when>
		<c:otherwise>
			isloggedin: false,
			visitor_status: 'guest',
			account_classification: '',
		</c:otherwise>
	</c:choose>
};
	
$(document).ready(function() {

	/* start event login */
	$(document).on("submit", "#loginForm", function(e){
		$.cookie('submitlogin', "login", {path:'/'});
	});
	
	var loginErrorMsg = $("#loginErrorMsg").val();
	if($.cookie("submitlogin") === "login" && (loginErrorMsg == null || loginErrorMsg.trim() == "")){
		ACC.ga4analytics.login();
   		$.cookie("submitlogin", null, { path: '/' });
	}else{
		$.cookie("submitlogin", null, { path: '/' });
	}
	
	/* end event login */
	/* start event view_item */
	<c:if test="${cmsPage.uid eq 'productDetails'}">
		<c:set var="productPrice" value='${fn:replace(product.price.formattedValue,"$","")}'/>
	   	<c:set var="productName" value="${fn:replace(product.name,'\"','')}"/>
	   	<c:choose>
		   	<c:when test="${product.multidimensional}">
		   		var productPrice = 0;
		   	</c:when>
		   	<c:otherwise>
		   		<c:choose>
			   		<c:when test="${isLoggedIn}">
			   			<c:set var="productCSPPrice" value='${fn:replace(product.customerPrice.formattedValue,"$","")}'/>
			   	 		var productPrice = parseFloat('${productCSPPrice}') && '${productCSPPrice}' !="" > 0 ? parseFloat('${productCSPPrice}') : 0;
			   		</c:when>
			   		<c:otherwise>
			   	 		var productPrice = 0;
			   		</c:otherwise>
		   		</c:choose>
		   	</c:otherwise>
	   	</c:choose>
	    
		ACC.ga4analytics.view_item(
			'${product.code}',
			'${fn:escapeXml(productName)}',
			productPrice,
			'${fn:escapeXml(product.productBrandName)}',
			'${fn:escapeXml(product.level1Category)}',
			'${fn:escapeXml(product.level2Category)}',
			'${fn:escapeXml(product.categories[0].name)}'
		);
	</c:if>
	/* end event view_item */
	/* start event view_item_list */
	<c:if test="${cmsPage.uid eq 'productGrid'}">
		var PlpItems = [{
			'item_list_id' : '${(categoryData.getCode()).toLowerCase()}',
			'item_list_name' : '${categoryData.getName()}'
		}];
	
		ACC.ga4analytics.view_item_list(PlpItems);
	</c:if>
	/* end event view_item_list */
	/* start event purchase */
	<c:if test="${cmsPage.uid eq 'orderConfirmationPage'}">
		var OrderConfirmItems = [];
		<c:set var="shipping" value=""/>
		<c:set var="coupon" value=""/>
		<c:choose>
			<c:when test="${isMixedCartEnabled}">
				<c:set var="revenue" value="${fn:replace(fn:replace(orderData.totalPrice.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalTax.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.deliveryFreight, '$', ''),',','') + fn:replace(fn:replace(orderData.shippingFreight, '$', ''),',','')}"/>
				<c:set var="shipping" value="${orderData.shippingFreight}"/>
			</c:when>
			<c:otherwise>
				<c:set var="revenue" value="${fn:replace(fn:replace(orderData.subTotal.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.totalTax.formattedValue, '$', ''),',','')-fn:replace(fn:replace(orderData.totalDiscounts.formattedValue, '$', ''),',','')+fn:replace(fn:replace(orderData.deliveryCost.formattedValue, '$', ''),',','')}"/>
				
				<c:if test="${orderData.orderType eq 'PARCEL_SHIPPING'}">
					<c:set var="shipping" value="${fn:replace(orderData.deliveryCost.formattedValue, '$', '')}"/>
					<c:set var="shipping" value="${fn:replace(shipping,',','')}"/>
				</c:if>
			</c:otherwise>
		</c:choose>
	
	
		<fmt:formatNumber var="total_cart_value" type="number" minFractionDigits="2" maxFractionDigits="2" value="${revenue}" />
		<c:set var="total_cart_value" value="${fn:replace(total_cart_value,',','')}"/>
		<c:set var="totalTaxValue" value="${fn:replace(orderData.totalTax.formattedValue,'$','')}"/>
		<c:set var="formattedtax" value="${fn:replace(totalTaxValue,',','')}"/>
		
		<c:if test="${not empty orderData.appliedVouchers}">
			<c:set var="coupon" value="${orderData.appliedVouchers}"/>
		</c:if>

		<c:forEach items="${orderData.unconsignedEntries}" var="product" varStatus="loop">
		
			<c:set var="productPrice" value="${product.basePrice.formattedValue.replace('$', '')}"/>
			<c:set var="productPrice" value="${fn:replace(productPrice,',','')}"/>
	
			OrderConfirmItems.push(Object.assign({},
	   			{
	   				'quantity' : parseInt('${product.quantity}'),
	   			},
				ACC.ga4analytics.generate_item_payload(
					'${product.product.code}',
					'${fn:escapeXml(product.product.name)}',
					parseFloat('${productPrice}'),
					'${fn:escapeXml(product.product.productBrandName)}',
					'${fn:escapeXml(product.product.level1Category)}',
					'${fn:escapeXml(product.product.level2Category)}',
					'${fn:escapeXml(product.product.categories[0].name)}'
				)
			));
		</c:forEach>
	
		ACC.ga4analytics.purchase(
			OrderConfirmItems,
			'${orderData.code}',
			parseFloat('${total_cart_value}'),
			parseFloat('${formattedtax}'),
			parseFloat('${empty shipping ? "0" : shipping}'),
			'${coupon}'
		);
	
	</c:if>
	/* end event purchase */
});

</script>