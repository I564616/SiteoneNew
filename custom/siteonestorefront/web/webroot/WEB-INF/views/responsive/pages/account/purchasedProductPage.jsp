<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page pageTitle="${pageTitle}">

<c:set var="searchUrl" value="/my-account/purchased-products/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<c:choose>
<c:when test="${not empty purchasedProducts}">
<h1 class="headline"><spring:theme code="purchasedProductPage.top" /></h1>
<p class="prod_desc"><!-- Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras gravida facilisis
sapien, ac vulputate tortor. Proin dictum ligula sit amet orci. --></p>

 <div class="account-section-content">
                    <div class="purchased-pagination mb-pagination">
 <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" 
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                                            <div class="cl"></div>
      </div> 
    <div class="cl"></div>
      </div>                                   

    <div class="cl"></div>
    <span class="visible-xs"><br/> <br/></span>
   <ul class="product__listing product__grid"> 
  <c:forEach var="product" items="${purchasedProducts}">
		<div class="product-item col-xs-6 col-sm-4 col-md-3">
			<div class="product-item-box">
				<c:choose>
					<c:when test="${product.availableStatus eq false}">
						<a class="thumb"> 
							<product:productPrimaryImage product="${product}" format="product" />
						</a>
					</c:when>
					<c:otherwise>
					<c:url var="producturl" value="${product.url}"/>
						<a href="${producturl}" class="thumb">
							<product:productPrimaryImage product="${product}" format="product" />
						</a>
					</c:otherwise>
				</c:choose>
			
			<div class="details">
				<div class="product-stock stock">
					<span> ${product.itemNumber}</span>
					<%-- <product:productNotifications product="${product}"/> <br/> --%>
	
	
					<c:choose>
						<c:when test="${product.stockAvailabilityMessage == 'In stock'}">
							<span> | <spring:theme
									text="${product.stockAvailabilityMessage}"
									arguments="${sessionStore.address.phone}" /></span>
						</c:when>
						<c:otherwise>
							<div class="fontitalic">
								<spring:theme text="${product.stockAvailabilityMessage}"
									arguments="${sessionStore.address.phone}" />
							</div>
						</c:otherwise>
					</c:choose>
	
				</div>

				<div class="productName-wrapper">
				<c:url var="producturl" value="${product.url}"/>
				<c:choose>
					<c:when test="${product.availableStatus eq false}">
						<span>
								${fn:escapeXml(product.name)}
						</span>
					</c:when>
					<c:otherwise>
						<a class="name" href="${producturl}">
							<span>
								${fn:escapeXml(product.name)}
							</span>
						</a>
					</c:otherwise>
				</c:choose>
				</div>

				<c:choose>
					<c:when test="${product.availableStatus eq false}">
						<div class="purchased-product-button">
							<div class="btn btn-block btn-primary mb-btn" disabled="disabled"><spring:theme code="purchasedProductPage.view" /></div>
						</div>
					</c:when>
		
					<c:otherwise>
						<div class="purchased-product-button">
						<c:url var="producturl" value="${product.url}"/>
							<a href="${producturl}"
								class="btn btn-block btn-primary mb-btn"><spring:theme code="purchasedProductPage.view" /></a>
						</div>
					</c:otherwise>
				</c:choose>
		</div>
		</div>
	</div>

				</c:forEach> 
 </ul>
 
   <div class="cl"></div>

<div class="account-orderhistory-pagination purchased-pagination">
 
                       <div class="mb-sorting"><nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" 
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                                        <div class="cl"></div>
                                        </div> 
   </div>
 </c:when>
 <c:otherwise>
   <div align="center" > <b><spring:theme code="purchasedProductPage.no.products" /> </b></div>
 </c:otherwise>
   </c:choose>
</template:page>