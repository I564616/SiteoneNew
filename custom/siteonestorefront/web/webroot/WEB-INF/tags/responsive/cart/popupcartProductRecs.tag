<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isrc" type="java.lang.String"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:if test="${product.couponEnabled eq true}">
	<div class="col-md-12 atcPopUpCoupon flex p-l-30 pad-xs-lft-15">
        <common:atcCouponClip></common:atcCouponClip>
	</div>
</c:if>
<div class="row popupproductrecomment-container produc-recomm-item">
<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="fullWidthContent" value="col-md-12 col-xs-12 col-sm-12"/>
		<c:set var="mobileTitle" value="col-xs-12 col-sm-12"/>
		<c:set var="productnologintoseeyourprice" value="hidden"/>
	</c:when>
	<c:otherwise>
		<c:set var="fullWidthContent" value="col-md-12 col-xs-12 col-sm-12"/>
		<c:set var="mobileTitle" value="col-xs-12 col-sm-12"/>
		<c:set var="productnologintoseeyourprice" value="hidden"/>
	</c:otherwise>
</c:choose>
<input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
<input type="hidden" id="productrecscode" name="productrecscode" value="${product.code}">
<input type="hidden" id="productrecsqty" name="productrecsqty" value="${quantity}">
<input type="hidden" id="productrecsPostPrice" name="productrecsPostPrice" value="${product.price.value}" />
<input type="hidden" id="productrecsPostBrand" name="productrecsPostBrand" value="${fn:escapeXml(product.productBrandName)}" />
<input type="hidden" id="productrecsPostCategoryLeve1" name="productrecsPostCategoryLeve1" value="${fn:escapeXml(product.categories[0].name)}" />
<input type="hidden" id="productrecsPostCategoryLeve2" name="productrecsPostCategoryLeve2" value="${fn:escapeXml(product.categories[1].name)}" />
<input type="hidden" id="productrecsPostCategoryLeve3" name="productrecsPostCategoryLeve3" value="${fn:escapeXml(product.categories[2].name)}" />
<input type="hidden" id="productrecsNamePost" name="productrecsNamePost" value="${fn:escapeXml(product.name)}"/>
<input type="hidden" id="productrecsstoreId" name="productrecsstoreId" value="${product.stock.fullfillmentStoreId}"/>
<input type="hidden" id="productrecsisSellable" name="productrecsisSellable" value="${(!product.isSellable)}"/>
<input type="hidden" id="clickTrackingURL" name="clickTrackingURL" value="${(product.clickTrackingURL)}"/>
<div class=" col-md-12 add-to-cart-item popupproductrecomment-wrapper padding0">
		 <c:url value="${product.url}" var="entryProductUrl"/>
		 <c:set var="hideList" value="${product.hideList}"/>
		 <c:set var="hideCSP" value="${product.hideCSP}"/>
    <c:if test="${product.singleUom eq true}">
        <c:set var="singleUom" value="true"/>
        <c:set var="uomDescription" value="${product.singleUomDescription}"/>
        <c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
    </c:if>

	<div class=" cart-detail col-md-4 col-xs-4 col-sm-5 paddingtop10">
		<div class="thumb col-md-12 padding0 col-xs-12 col-sm-12"><a class="product-image-cart-popup js-click-tracking recomm-analytics recom-popup-tiles" href="${entryProductUrl}${fn:escapeXml(isrc)}" data-prdcode="${product.code}">
			<product:productPrimaryImage product="${product}" format="product"/>
		</a>
		</div>
	</div>
	<c:if test="${not empty entry.product.sellableUoms}">
		   <c:forEach items="${entry.product.sellableUoms}" var="sellableUom">
				 <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		   </c:forEach>
	</c:if>
	<c:set var="uomMeasure" value="${entry.uomMeasure}"/>
	<div class="cart-detail col-md-6 col-xs-6 col-sm-6 padding-rightZero pad-topBot-border product-Textalign paddingtop10 ">
		<div class="hidden-md hidden-lg mobile-name">
		<div class="${mobileTitle} padding0">
			<div class="prod-name-popup-overlay recomm-popupcart-analytics"><a title="${fn:escapeXml(product.productShortDesc)}" class="name js-click-tracking recomm-analytics" href="${entryProductUrl}${fn:escapeXml(isrc)}">
				<span>
					<c:choose>
						<c:when test="${fn:length(product.productShortDesc) > 30}">
							<c:out value="${fn:substring(product.productShortDesc, 0, 40)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${product.productShortDesc}" />
						</c:otherwise>
					</c:choose>
					</span>
			</a></div>
		</div>
		</div>
		<div class="hidden-xs hidden-sm desktop-name marginleft20">
			<div class="prod-name-popup-overlay recomm-popupcart-analytics"><a title="${fn:escapeXml(product.productShortDesc)}" class="name js-click-tracking recomm-analytics" href="${entryProductUrl}${fn:escapeXml(isrc)}">
				<span>
					<c:choose>
						<c:when test="${fn:length(product.productShortDesc) > 50}">
							<c:out value="${fn:substring(product.productShortDesc, 0, 50)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${product.productShortDesc}" />
						</c:otherwise>
					</c:choose>
					</span>
			</a></div>
		</div>	
		<div class="col-md-12 col-xs-12 col-sm-12 productDetailCart-atc marginbottom0 marginleft20">
		<div class="row">
	<c:if test="${pageSection == null}">
		<c:choose>
            <c:when test="${entry.product.salePrice != null}">
             <c:choose>
               <c:when test="${not empty uomDescription}">
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/> / ${uomMeasure}</p><del><b><format:price priceData="${entry.basePrice}"/> / ${uomMeasure}</b></del>
               </c:when>
               <c:otherwise>
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/></p><del><b><format:price priceData="${entry.basePrice}"/></b></del>
               </c:otherwise>
             </c:choose>
            </c:when>
           <c:otherwise>
               <c:choose>
               <c:when test="${not empty uomDescription}">
                    <div class="price add-cart-price"><b>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value * quantity}"/> / ${uomMeasure}</b></div>
               </c:when>
               <c:otherwise>
                    <div class="price add-cart-price"><b>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value * quantity}"/></b></div>
               </c:otherwise>
               </c:choose>
           </c:otherwise>	
       </c:choose> 
	</c:if>
	<c:if test="${pageSection == true}">
        <!-- code for cart popup -->
        <ycommerce:testId
			code="productDetails_productNamePrice_label_${product.code}">
			<input type="hidden" id="listPrice" value="${product.price}"/>
			<input type="hidden" id="customerPrice" value="${product.customerPrice}"/>
		<c:choose>
        <c:when test="${product.customerPrice == null || product.customerPrice.formattedValue eq '$0.00'}">
        <c:choose>
            <c:when test="${product.salePrice != null}">
            <c:choose>
               <c:when test="${not empty uomDescription }">
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/> / ${uomMeasure}</p><del><b><format:price priceData="${entry.basePrice}"/> / ${uomMeasure}</b></del>
               </c:when>
               <c:otherwise>
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/></p><del><b><format:price priceData="${entry.basePrice}"/></b></del>
               </c:otherwise>
            </c:choose>
            </c:when>
            <c:otherwise>
            <c:if test="${hideList ne true}">
             <c:choose>
				<c:when test="${not empty uomDescription}">
               		<div class=" add-card-price-popup-wrapper padding0">
                    	<p class="siteOneListPrice ${productnologintoseeyourprice}"><b><spring:theme code="text.product.siteOnelistprice"/></b></p>
                 		<div class="add-card-price-popup-wrappers"><format:price priceData="${product.price}"/><spring:theme code="product.recommendation.addtocart.price.ea"/></div>
                 	</div>
                    <c:if test="${hideCSP ne true}">
                    	<div class="${fullWidthContent} cart-popup-login-wrapper ${productnologintoseeyourprice}">
                    	<a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay js-click-tracking"><spring:theme code="text.product.logInToSeeYourPrice js-click-tracking"/></a> </div>
                    </c:if>
               </c:when>
               <c:otherwise>
                   <div class=" add-card-price-popup-wrapper padding0">
                       <p class="siteOneListPrice ${productnologintoseeyourprice}"><b><spring:theme code="text.product.siteOnelistprice"/></b></p>
                	   <div class=" add-card-price-popup-wrappers"><format:price priceData="${product.price}"/><spring:theme code="product.recommendation.addtocart.price.ea"/></div>
                   </div>
                   <c:if test="${hideCSP ne true}">
                  	   <div class="${fullWidthContent} cart-popup-login-wrapper ${productnologintoseeyourprice}">
                  	   <a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay js-click-tracking"><spring:theme code="text.product.logInToSeeYourPrice"/></a></div> 
                   </c:if>
               </c:otherwise>
             </c:choose>
             </c:if>
             <c:if test="${hideList eq true && hideCSP ne true}">
             	  <div class="${fullWidthContent} cart-popup-login-wrapper ${productnologintoseeyourprice}">
                      <a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay js-click-tracking"><spring:theme code="text.product.logInToSeeYourPrice"/></a>  
                  </div>               
             </c:if>
           </c:otherwise>	
           </c:choose>
           </c:when>
           <c:otherwise>
           <c:choose>
            <c:when test="${product.salePrice != null}">
              <c:choose>
                <c:when test="${not empty uomDescription}">
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/> / ${uomMeasure}</p><del><b><format:price priceData="${entry.basePrice}"/> / ${uomMeasure}</b></del>
                </c:when>
                <c:otherwise>
                    <p class="price add-cart-discount-price">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/></p><del><b><format:price priceData="${entry.basePrice}"/></b></del>
                </c:otherwise>
              </c:choose>

            </c:when>
           <c:otherwise>
				<c:if test="${hideCSP ne true}">
	               <c:choose>
	                <c:when test="${not empty uomDescription }">
	                <div class=" login-add-cart-your-price-popup padding0">
	                    <div class="yourPrice hidden"><spring:theme code="text.product.your.price"/></div>
	                    <p class="customerSpecificPrice"><format:price priceData="${product.customerPrice}"/><spring:theme code="product.recommendation.addtocart.price.ea"/></p></div>
	                </c:when>
	                <c:otherwise>
	                <div class=" login-add-cart-your-price-popup padding0">
	                  <div class="yourPrice hidden"><spring:theme code="text.product.your.price"/></div>
	                    <p class="customerSpecificPrice"><format:price priceData="${product.customerPrice}"/><spring:theme code="product.recommendation.addtocart.price.ea"/></p></div>
	                </c:otherwise>
	              </c:choose>
                </c:if>
				 <c:if test="${hideList ne true && (product.price.value gt product.customerPrice.value)}">
	               <c:choose>
	                <c:when test="${not empty uomDescription }">
	                <c:choose>
	                  <c:when test="${not empty entry.uomPrice}">
	                    <div class=" login-add-cart-retail-price-popup padding0 hidden">
	                    <p class="login-siteOneListPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.siteOnelistprice"/></p>
	                    <span class="listSpecificPrice"><del>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.uomPrice}"/> </del></span>
	                  </div>
	                  </c:when>
	                  <c:otherwise>
	                     <div class=" login-add-cart-retail-price-popup padding0 hidden">
	                     <p class="login-siteOneListPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.siteOnelistprice"/></p>
	                     <span class="listSpecificPrice"><del>$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${entry.getListPrice()}"/> </del></span>
	                     </div>
	                  </c:otherwise>
	                </c:choose>
	                </c:when>
	                <c:otherwise>
	                <c:choose>
	                  <c:when test="${not empty entry.uomPrice}">
	                    <div class=" login-add-cart-retail-price-popup padding0 hidden">
	                    	<p class="login-siteOneListPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.siteOnelistprice"/></p>
	                    	<span class="listSpecificPrice">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.uomPrice}"/></span>
	                    </div>
	                  </c:when>
	                  <c:otherwise>
	                      <div class="login-add-cart-retail-price-popup padding0 hidden">
	                      <p class="login-siteOneListPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.siteOnelistprice"/></p>
	                      <span class="listSpecificPrice">$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${entry.getListPrice()}"/></span>
	                      </div>
	                </c:otherwise>
	                </c:choose>
	                </c:otherwise>
	              </c:choose>
              </c:if>

           </c:otherwise>	
           </c:choose>
            </c:otherwise>
       </c:choose> 
       </ycommerce:testId>
     <!--   code ends -->
       </c:if>
      </div>
      </div>
      <div class="cl"></div>

	</div>
	<div class="col-md-2 col-xs-2 col-sm-2 add-button-wrapper">
	<c:choose>
	<c:when test="${!isOrderingAccount}">
		<button class="btn btn-primary addtocart-productrecommendation js-addtocart-productrecs" aria-disabled="true" disabled="disabled"><spring:theme code="product.recommendation.addtocart.add.text"/></button>
	</c:when>
	<c:otherwise>
		<button class="addtocart-productrecommendation js-addtocart-productrecs"><spring:theme code="product.recommendation.addtocart.add.text"/></button>
	</c:otherwise>
	</c:choose>	
	</div> 
<div class="productrecs-Itemadded hidden"> 
<common:productrecsItemAddedIcon width="23" height="23"></common:productrecsItemAddedIcon>
<span style="margin-left:10px">Item added</span></div>
</div>

</div>
