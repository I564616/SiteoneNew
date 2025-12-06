<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<template:page pageTitle="${pageTitle}">
	<spring:url value="/" var="homelink" htmlEscape="false"/>
		<div class="row">
		<div class="col-md-10 col-sm-9 col-xs-12">
		 
				<h1 class="headline">${cityName}</h1>
			</div>
		 
		<div class="col-md-2 col-sm-3 col-xs-12">
<form action="${homelink}store-finder">
					<input type="submit" value="Find a Branch" class="btn btn-primary btn-block" />
				</form>
				<div class="cl"></div>
</div>
<div class="cl"></div>
<br/><br/>
<div class="cl"></div>
			<c:forEach items="${siteOnePOS}" var="store" varStatus="rowCounter">
				<div class="col-xs-12 col-sm-6 col-md-4">
					<div class="address-box">
						<c:if test="${rowCounter.count % 5 == 1}">

						</c:if>
						
					 
						 <a href="<c:url value="/store"/>/${store.storeId}" class="heading" style="text-decoration:none;">
									${store.name}&nbsp;<br>
									<c:if test="${not empty store.title}">
									${store.title}&nbsp;<br>
									</c:if>
									</a>
									 
						<br/> 
    					<c:if test="${store.enableOnlineFulfillment eq false or store.enableOnlineFulfillment eq 'false'}">	
							<div class="m-b-10"><span class="inBranchShoppingOnly"><spring:theme code="global.header.branchShopping"/></span></div>
						</c:if>
					<div>
						<span class="line2">${store.address.line2}</span> <span
							class="line1">${store.address.line1}</span>
					</div>

					<div>
						<span class="town">${store.address.town},</span> <span
							class="region">${store.address.region.isocodeShort}</span> <span
							class="postalCode">${store.address.postalCode}</span>
					</div>

					<div>
					<p><a class="tel-phone" href="tel:${store.address.phone}">${store.address.phone}</a></p>
								
					</div>

					<div>
						<span class="storeStatus">${store.storeStatus}</span>
					</div>
<br/><br/>
    			<c:if test="${store.enableOnlineFulfillment eq true or store.enableOnlineFulfillment eq 'true'}">
					<div class="col-md-8 col-sm-12 col-xs-12 row">
						<c:set var="showRemoveMyStore" value="false" />
						<c:forEach items="${myStoresIdList}" var="mystore">
							<c:if test="${mystore eq store.storeId}">
								<c:set var="showRemoveMyStore" value="true" />
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when
								test="${store.isPreferredStore eq false && !showRemoveMyStore && store.storeId != cookie['csc'].getValue()}">
								<form:form method="get" action="${homelink}store-finder/make-my-store/${store.storeId}">
                    				<input type="submit" value="<spring:theme code='storeDirectoryListPage.make.branch' />" class="bg-transparent border-none btn-store-directory"> 
                				</form:form>
								 
							</c:when>
							<c:otherwise>
								<a href="<c:url value="/store-finder/remove-my-store"/>/${store.storeId}"
									id="removeStore" 
									data-store-id="${store.storeId}"
									data-name="${store.name}"
									data-line1="${store.address.line1}"
									data-line2="${store.address.line2}"
									data-town="${store.address.town},"
									data-region-code="${store.address.region.isocodeShort}"
									data-postal-code="${store.address.postalCode}"
									 ><spring:theme code="storeDirectoryListPage.remove.branch" /></a>
								<BR>
							</c:otherwise>
						</c:choose>
						</div>
						</c:if>
						<div class="col-md-4 col-sm-12 col-xs-12 row">
						 
						<a id="getDirection" href="" data-url="${store.address.line1},${store.address.line2},${store.address.town},${store.address.region.isocodeShort},${store.address.postalCode}"><spring:theme code="Directions &#8594;" /></a>
						 
					</div>
						<c:if
							test="${rowCounter.count % 5 == 0||rowCounter.count == fn:length(siteOnePOS)}">

						</c:if>
							 <div class="cl"></div>
					</div>
				</div>
			</c:forEach>
			 <div class="cl"></div>
		</div>
	 <div class="cl"></div>
<br/><br/><br/><br/><br/>
</template:page>