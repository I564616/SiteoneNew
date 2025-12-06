<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="quantity" required="true" %>
<%@ attribute name="recommendedproduct" required="false" %>
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
<c:set var="showUpdatequantity" value="false"/>
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${recommendedproduct ne true}">
		<c:set var="cartImg" value="col-md-12 col-xs-12 col-sm-12 padding0"/>
		<c:set var="cartImgThumb" value="col-xs-offset-3 col-xs-6"/>
		<c:set var="cartDetails" value="col-md-12 col-xs-12 col-sm-12 marginTop35"/>
		<c:set var="cartpricewrapper" value="add-card-price-popup-wrapper-rec"/>
		<c:set var="productnamerec" value="name-rec"/>
		<c:set var="hideContentforMixedcart" value="hidden"/>
		<c:set var="fullWidthContent" value="col-md-12 col-xs-12 col-sm-12"/>
		<c:set var="mobileTitle" value="col-xs-12 col-sm-12"/>
		<c:set var="mobileHideMixedcart" value="hidden"/>
		<c:set var="productnologintoseeyourprice" value=""/>
</c:when>
<c:otherwise>
		<c:set var="cartImg" value="col-md-4 col-xs-4 col-sm-4"/>	
		<c:set var="cartImgThumb" value="col-md-12 col-xs-12 col-sm-12"/>
		<c:set var="cartDetails" value="col-md-7 col-xs-8 col-sm-8 product-Textalign"/>
		<c:set var="cartpricewrapper" value="add-card-price-popup-wrapper"/>
		<c:set var="productnamerec" value="name"/>
		<c:set var="hideContentforMixedcart" value="hidden"/>
		<c:set var="fullWidthContent" value="col-md-12 col-xs-12 col-sm-12"/>
		<c:set var="mobileTitle" value="col-xs-12"/>
		<c:set var="mobileHideMixedcart" value="hidden"/>
		<c:set var="productnologintoseeyourprice" value="hidden"/>
</c:otherwise>
</c:choose>
                <input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
  				

<div class="col-md-12 add-to-cart-item">
		 <c:url value="${product.url}" var="entryProductUrl"/>
		 <c:set var="hideList" value="${product.hideList}"/>
		 <c:set var="hideCSP" value="${product.hideCSP}"/>
    <c:if test="${product.singleUom eq true}">
        <c:set var="singleUom" value="true"/>
        <c:set var="uomDescription" value="${product.singleUomDescription}"/>
        <c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
    </c:if>
    <c:if test="${showUpdatequantity}">
	    <div class="global-alerts ">			
			<div class="alert status-message alert-info">
						
						Product has been added to your cart will last ordered quantity.
			</div>
		</div>
	</c:if>
 
	<input type="hidden" class="atc-prodName" value="${fn:escapeXml(product.name)}"/>
	<input type="hidden" class="atc-prodCode" value="${product.code}"/>
	<input type="hidden" class="atc-prodBrand" value="${fn:escapeXml(entry.product.productBrandName)}"/>
	<input type="hidden" class="atc-productQty" value="${quantity}"/>
	<input type="hidden" class="atc-categoryLeve1" value="${fn:escapeXml(entry.product.level1Category)}"/>
	<input type="hidden" class="atc-categoryLeve2" value="${fn:escapeXml(entry.product.level2Category)}"/>
	<input type="hidden" class="atc-categoryLeve3" value="${fn:escapeXml(entry.product.categories[0].name)}"/>
	<input type="hidden" class="atc-storeStock" value="${entry.product.storeStockAvailabilityMsg}"/>
	<input type="hidden" class="atc-backorderMsg" value="${entry.product.isEligibleForBackorder}"/>
	
	<div class=" cart-detail ${cartImg}">
		<div class="thumb ${cartImgThumb} padding0"><a class="product-image-cart-popup" href="${entryProductUrl}" data-prdcode="${product.code}">
 
			<product:productPrimaryImage product="${entry.product}" format="product"/>
		</a>
		</div>
	</div>
	<c:if test="${not empty entry.product.sellableUoms}">
		   <c:forEach items="${entry.product.sellableUoms}" var="sellableUom">
				 <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		   </c:forEach>
	</c:if>
	<c:set var="uomMeasure" value="${entry.uomMeasure}"/>
	<div class="cart-detail ${cartDetails} padding-rightZero pad-topBot-border">
		<div class="hidden-xs hidden-sm desktop-name">
			<div class="qty ${productnologintoseeyourprice}">${fn:escapeXml(product.itemNumber)}</div>
			<div class="prod-name-popup-overlay"><a title="${fn:escapeXml(product.name)}" class="${productnamerec}" href="${entryProductUrl}">
				<span>
					<c:choose>
						<c:when test="${fn:length(product.name) > 50}">
							<c:out value="${fn:substring(product.name, 0, 50)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${product.name}" />
						</c:otherwise>
					</c:choose>
					</span>
			</a></div>
		</div>	
		<div class="hidden-md hidden-lg mobile-name">
		<div class="${mobileTitle} padding0">
			<div class="qty ${productnologintoseeyourprice}">${fn:escapeXml(product.itemNumber)}</div>
			<div class="prod-name-popup-overlay"><a title="${fn:escapeXml(product.name)}" class="name" href="${entryProductUrl}">
				<span>
					<c:choose>
						<c:when test="${fn:length(product.name) > 55}">
							<c:out value="${fn:substring(product.name, 0, 55)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${product.name}" />
						</c:otherwise>
					</c:choose>
					</span>
			</a></div>
		</div>
		</div>
		<div class="col-md-12 col-xs-12 col-sm-12 productDetailCart-atc marginbottom0 padding0">
		<div class="row">
			<div class="col-md-12 col-xs-6 total-atc ${recommendedproduct? '' : 'hidden'}"><format:price priceData="${totalPrice}"/></div>
			<div class="col-md-12 each-atc ${recommendedproduct? 'col-xs-6' : 'col-xs-12'}">
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
                    <div class="price add-cart-price"><span class="atc-price-analytics hidden">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value}"/></span><b>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value * quantity}"/> / ${uomMeasure}</b></div>
               </c:when>
               <c:otherwise>
                    <div class="price add-cart-price"><span class="atc-price-analytics hidden">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value}"/></span><b>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.basePrice.value * quantity}"/></b></div>
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
               		<div class="${cartpricewrapper} padding0">
                    	<p class="siteOneListPrice ${productnologintoseeyourprice}"><b><spring:theme code="text.product.siteOnelistprice"/></b></p>
                 		<div class="productrecs-add-card-price-popup-wrappers"><format:price priceData="${entry.basePrice}"/> / ${uomMeasure}</div>
                 	</div>
                    <c:if test="${hideCSP ne true}">
                    	<div class="${fullWidthContent} cart-popup-login-wrapper ${productnologintoseeyourprice}">
                    	<a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay"><spring:theme code="text.product.logInToSeeYourPrice"/></a> </div>
                    </c:if>
               </c:when>
               <c:otherwise>
                   <div class="${cartpricewrapper} padding0">
                       <p class="siteOneListPrice ${productnologintoseeyourprice}"><b><spring:theme code="text.product.siteOnelistprice"/></b></p>
                	   <div class=" productrecs-add-card-price-popup-wrappers"><format:price priceData="${entry.basePrice}"/> / ${uomMeasure}</div>
                   </div>
                   <c:if test="${hideCSP ne true}">
                  	   <div class="${fullWidthContent} cart-popup-login-wrapper ${productnologintoseeyourprice}">
                  	   <a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay"><spring:theme code="text.product.logInToSeeYourPrice"/></a></div> 
                   </c:if>
               </c:otherwise>
             </c:choose>
             </c:if>
             <c:if test="${hideList eq true && hideCSP ne true}">
             	  <div class="${fullWidthContent} cart-popup-login-wrapper">
                      <a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay"><spring:theme code="text.product.logInToSeeYourPrice"/></a>  
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
	                <div class="login-add-cart-your-price-popup padding0">
	                    <div class="yourPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.your.price"/></div>
	                    <p class="customerSpecificPrice"><span class="atc-price-analytics"><format:fromPrice priceData="${entry.basePrice}"/></span> <span class="${recommendedproduct?'':'hidden'}"> / ${uomMeasure}</span> </p></div>
	                </c:when>
	                <c:otherwise>
	                <div class="recommendedproduct padding0">
	                  <div class="yourPrice ${productnologintoseeyourprice}"><spring:theme code="text.product.your.price"/></div>
	                    <p class="customerSpecificPrice"><span class="atc-price-analytics"><format:fromPrice priceData="${entry.basePrice}"/></span> <span class="${recommendedproduct?'':'hidden'}"> / ${uomMeasure}</span> </p></div>
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
      </div>
      <div class="cl"></div>
      	<%-- 	<c:if test="${showUpdatequantity ne true}">
			<div class="qty productDetailCart-atc"><spring:theme code="popup.cart.quantity.added"/>&nbsp;${quantity}</div>
			</c:if>
			<div class="qty">${fn:escapeXml(product.itemNumber)}</div>--%>
			<%-- <c:if test="${product.stock.stockLevelStatus.code eq 'inStock'}">
			<div class="qty"><spring:theme code="product.variants.in.stock"/></div></c:if> --%>
        <div class="hidden-xs hidden-sm atc-product-stock-col ${hideContentforMixedcart}">
	        <cart:addToCartInventoryMessages/>
        </div>
        <div class="hidden-md hidden-lg ${mobileHideMixedcart}">
        <div class="row mob-atc-icon-wrapper ">
        	<div class="col-xs-8 atc-product-stock-col marginrgt3 padding0">
	        	<cart:addToCartInventoryMessages/>
	        </div>
	        <div class="col-xs-4 atc-icon-detail-wrapper padding0">
	        	<div class="row mob-atc-icon-wrapper">
					<div class="col-xs-4 atc-icon-details marginrgt3 padding0">
						<c:choose>
						<c:when test="${product.outOfStockImage || entry.product.stockAvailableOnlyHubStore}">
						<common:pickUpIcon height="11" width="13" iconColor="#ccc" />
						</c:when>
						<c:otherwise>
						<common:pickUpIcon height="11" width="13" iconColor="#000" />
						</c:otherwise>
						</c:choose>
		        	</div>
		        	<div class="col-xs-4 atc-icon-details marginrgt3 padding0 ">
		        		<c:choose>
						<c:when test="${product.outOfStockImage || entry.product.stockAvailableOnlyHubStore}">
						<common:deliveryIcon height="10" width="20" iconColor="#ccc" />
						</c:when>
						<c:otherwise>
						<common:deliveryIcon height="10" width="20" iconColor="#000" />
						</c:otherwise>
						</c:choose>
					</div>
		        	<div class=" col-xs-4 atc-icon-details padding0">
		        	    <c:choose>
		        	    <c:when test="${product.isShippable eq true || entry.product.stockAvailableOnlyHubStore}">
		        		<common:parcelIcon height="12" width="18" iconColor="#000"/>
		        	    </c:when>
		        		<c:otherwise>
	        			<common:parcelIcon height="12" width="18" iconColor="#ccc"/>
	        			</c:otherwise>
	        			</c:choose>
	        		</div>
        		</div>
	        </div>
	    </div>
        </div> 
        
        	<div class="atc-icon-detail-wrapper col-md-12 padding0 hidden-xs hidden-sm ${hideContentforMixedcart}">
				<div class="col-md-4 padding0 marginrgt5">
					<div class="atc-icon-details">
	        		<c:choose>
					<c:when test="${product.outOfStockImage || entry.product.stockAvailableOnlyHubStore}">
					<common:pickUpIcon height="18" width="22" iconColor="#ccc" />
					</c:when>
					<c:otherwise>
					<common:pickUpIcon height="18" width="22" iconColor="#000" />
					</c:otherwise>
					</c:choose>
					</div>
				</div>
	        	<div class="col-md-4 padding0 marginrgt5">
	        		<div class="atc-icon-details">
		        	<c:choose>
					<c:when test="${product.outOfStockImage || entry.product.stockAvailableOnlyHubStore}">
					<common:deliveryIcon height="18" width="37" iconColor="#ccc" />
					</c:when>
					<c:otherwise>
					<common:deliveryIcon height="18" width="37" iconColor="#000" />
					</c:otherwise>
					</c:choose>
					</div>
				</div>
	        	<div class="col-md-4 padding0">
	        		<div class="atc-icon-details">
	        		<c:choose>
		        	<c:when test="${product.isShippable eq true || entry.product.stockAvailableOnlyHubStore}">
		        	<common:parcelIcon height="18" width="26" iconColor="#000"/>
		        	</c:when>
		        	<c:otherwise>
	        		<common:parcelIcon height="18" width="26" iconColor="#ccc"/>
	        		</c:otherwise>
	        		</c:choose>
	        		</div>
	        	</div>
	        </div>
        
			<c:forEach items="${product.baseOptions}" var="baseOptions">
				<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
					<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
						<div class="itemColor">
							<span class="label"><spring:theme code="product.variants.colour"/></span>
							<img src="${baseOptionQualifier.image.url}"  alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
						</div>
					</c:if>
					<c:if test="${baseOptionQualifier.qualifier eq 'size'}">
						<div class="itemSize">
							<span class="label"><spring:theme code="product.variants.size"/></span>
								${baseOptionQualifier.value}
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>
			<c:if test="${not empty entry.deliveryPointOfService.name}">
				<div class="itemPickup hidden"><span class="itemPickupLabel"><spring:theme code="popup.cart.pickup"/></span>&nbsp;${entry.deliveryPointOfService.name}</div>
			</c:if>
			
			<c:if test="${showUpdatequantity}">

			<div class="margin20 atc-qty-btn-container">
				<input type="text" class="order-qtybox form-control" value=""/>
				<button class="btn btn-primary update-qty-btn"><spring:theme code="update.quantity"/></button>
			
				<div class="cl"></div>
			</div>
		
			</c:if>
			
			
			
	</div> 
</div>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>