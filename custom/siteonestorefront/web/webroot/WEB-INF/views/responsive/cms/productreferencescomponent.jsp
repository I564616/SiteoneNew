<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:choose> 
	<c:when
		test="${not empty productReferences and component.maximumNumberProducts > 0}">
		<div class="youMayAlsoLike">

			<div class="headline">${component.title}</div>

			<div class="">
				<c:forEach end="${component.maximumNumberProducts}"
					items="${productReferences}" var="productReference" varStatus="loop">
					<div class="item-wrapper col-xs-6 col-sm-4 col-md-3">
					<div class="item-inner-wrapper col-xs-12">
						   <div class="thumb">
						   <a href="${productReference.target.url}" >
                                <product:productPrimaryReferenceImage product="${productReference.target}" format="product" />
                              </a>
                          </div>
                      
                          <div class="item-info-wrapper">
                          <c:if test="${component.displayProductTitles}">
                          	
                          	<div class="item-name">
                          		<div>${productReference.target.itemNumber}</div>
                             	<a class="name" href="${productReference.target.url}" >
                             		<span>${productReference.target.name}</span>
                             	</a>
                            </div>
                            <div class="item-code">
                             	<div class="stock-wrapper clearfix">
									<product:productNotifications product="${productReference.target}"/>
                  				</div>
                            </div>
                          
                               
                         </c:if>
                               
                            <c:if test="${component.displayProductPrices}">
                                <div class="priceContainer">
								<c:if test="${not empty productReference.target.price || not empty productReference.target.priceRange.minPrice}">
					             <h5><spring:theme code="text.product.siteOnelistprice"/></h5>
				               </c:if>
	                    <c:choose>
	                             <c:when test="${productReference.target.multidimensional and (productReference.target.priceRange.minPrice.value ne productReference.target.priceRange.maxPrice.value)}">
	  	                              <format:price priceData="${productReference.target.priceRange.minPrice}"/><c:if test="${productReference.target.sellableUomsCount == 0}"> - <format:price priceData="${productReference.target.priceRange.maxPrice}"/></c:if>
	                             </c:when>
	                             <c:when test="${productReference.target.multidimensional and (productReference.target.priceRange.minPrice.value eq productReference.target.priceRange.maxPrice.value)}">
	  	                              <format:price priceData="${productReference.target.priceRange.minPrice}"/> 
	                             </c:when>
	                             <c:when test="${productReference.target.sellableUomsCount >=1 && (not empty productReference.target.price || not empty productReference.target.priceRange.minPrice)}">
	                                 <c:if test="${productReference.target.salePrice == null }">
	                                  <span class="black-title"> Starting from <format:price priceData="${productReference.target.price}"/></span>
	                                 </c:if>
	                                 <span class="black-title"> Starting from <del><format:price priceData="${productReference.target.price}"/></del></span>
	                                  <div class="col-md-2 col-sm-4 col-xs-4 actualPrice"><p class="price" style="color: red;"> $<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${productReference.target.salePrice}"/></p></div><br>
	                             </c:when>
	                        <c:otherwise>
	                                <c:if test="${productReference.target.salePrice !=null && productReference.target.price != null}">
								 	  <div class="col-md-2 col-sm-4 col-xs-4 prePrice"><del><product:productPricePanel product="${productReference.target}" /></del></div><br>
								 	  <div class="col-md-2 col-sm-4 col-xs-4 actualPrice"><p class="price" style="color: red;"> $<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${productReference.target.salePrice}"/></p></div><br>
							        </c:if>
							        <c:if test="${productReference.target.salePrice ==null}">
							           <product:productPricePanel product="${productReference.target}" />
							        </c:if>
	  	                    </c:otherwise>
	                   </c:choose>
                                    <br>
                                    <c:if test="${!productReference.target.multidimensional}">
			                           <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
			                       		 <span id="customerPriceError${productReference.target.code}"></span>
			                          	  <a href="#" id="customerPrice${productReference.target.code}"  data-code="${productReference.target.code}" data-is-buyable="${productReference.target.isBuyable}"><spring:theme code="productReferencesComponent.click" /></a>
		                           		    <span id="cspSection${productReference.target.code}" style="display:none">
		                               		<br><spring:theme code="text.product.your.price"/>
		                               		<br><span id="csp${productReference.target.code}"></span><br>
		                              	</span>
		                              	<br>
			                           </sec:authorize>
			                      </c:if>
                                </div>
                            </c:if>
                      </div>
                      <!-- <br><br> -->
                     <%--  <product:productPromotionSection product="${productReference.target}"/>  --%>
           <form:form method="Post" id="addToCartForm" class="add_to_cart_form" action="${homelink}cart/add">
	             <input type="hidden" name="productCodePost" value="${productReference.target.code}" />
	             <input type="hidden" name="productNamePost" value="${productReference.target.name}"/>
				 <input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
	      	     <c:if test="${productReference.target.purchasable}">
	                <input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="1">
                 </c:if> 
	         <c:if test="${empty showAddToCart ? true : showAddToCart}">
	           <c:set var="buttonType">button</c:set>
	         <c:if test="${productReference.target.purchasable and productReference.target.variantType ne 'GenericVariantProduct' and productReference.target.price ne '' }">
	      	<c:set var="buttonType">submit</c:set>
	        </c:if>
	      <c:choose>
	       	<c:when test="${fn:contains(buttonType, 'button')&& productReference.target.variantType eq 'GenericVariantProduct' && productReference.target.variantCount != 1}">
		           <div class="variantButton ">
		            <a href="${productReference.target.url}" style="color: white; text-decoration: none;" id="variantButton" class="btn btn-block btn-default" >
		                  <spring:theme code="productReferencesComponent.selectFrom" /> ${productReference.target.variantCount} Products
		               </a>
		           </div>
	      	</c:when>
	          <c:otherwise>
	      	  <ycommerce:testId code="addToCartButton">
	      		    <%-- <button id="addToCartButton" type="${buttonType}" class="btn btn-primary btn-block js-add-to-cart js-enable-btn" disabled="disabled">
	      				<spring:theme code="basket.add.to.basket"/>
	      				</button>  --%>
	      					<c:set var="addIsMyStoreProduct" value="false" />
			<c:forEach items="${productReference.target.stores}" var="store">
				<c:if test="${store eq sessionStore.storeId}">
					<c:set var="addIsMyStoreProduct" value="true" />
				</c:if>
			</c:forEach>
			<c:set var="addIsProductSellable" value="false" />
			<c:if test="${productReference.target.isRegulateditem}">
				<c:forEach items="${productReference.target.regulatoryStates}" var="regulatoryStates">
					<c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
						<c:set var="addIsProductSellable" value="true" />
					</c:if>
				</c:forEach>
			</c:if>
			
			<c:set var="isSellableInventoryHit" value="true" />
			<c:if test="${productReference.target.stock.stockLevel <= 0 && productReference.target.stock.inventoryHit < 5}">
				<c:set var="isSellableInventoryHit" value="false" />
			</c:if>
			
            
            <c:choose>
            	<c:when test="${productReference.target.sellableUomsCount == 0}"> 
            		<c:choose>
		            	<c:when test="${(!productReference.target.isSellable)}"> 
		                    <button type="submit" id="carosuelPageAddToCart_${productReference.target.code}" class="btn btn-primary pull-left btn-block addtoCart"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="productReferencesComponent.addToCart" />
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span>${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <button type="submit" id="carosuelPageAddToCart_${productReference.target.code}" class="btn btn-primary btn-block js-enable-btn"
		                            disabled="disabled"><spring:theme code="productReferencesComponent.addToCart" />
		                    </button>
		                </c:otherwise>
		            </c:choose>
            	</c:when>
            	<c:otherwise>
            		<c:if test="${productReference.target.sellableUomsCount>=1}">
	                  	
		              <button type="button" class="btn btn-block btn-default" name="sellableUomsCountValue">
		                <a href="${productReference.target.url}" style="color: white; text-decoration: none;"><spring:theme code="productReferencesComponent.selectFrom" /> ${productReference.target.sellableUomsCount} <spring:theme code="featureProductList.products" /> </a>
		               </button>
	                  </c:if>
                    <c:choose>
		            	<c:when test="${(!productReference.target.isSellable)}"> 
		                    <button type="submit" id="carosuelPageAddToCart_${productReference.target.code}" class="btn btn-primary pull-left btn-block" style="display:none;margin-top:0px"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="productReferencesComponent.addToCart" />
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span>${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <button type="submit" id="carosuelPageAddToCart_${productReference.target.code}" class="btn btn-primary btn-block js-enable-btn" style="display:none;margin-top:0px"
		                            disabled="disabled"><spring:theme code="productReferencesComponent.addToCart" />
		                    </button>
		                </c:otherwise>
		            </c:choose>
            	</c:otherwise>
            </c:choose>
            
            
            
            
            
	      				
	      				 <%-- <a href="#" class="wishlistAddProLink"><spring:theme code="text.product.listingOfProducts"/></a> --%>
	      				 <product:addToSaveList product="${productReference.target}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/> 
<!-- 	      				<div class="js-owl-carousel-reference"> -->
<%-- 							<c:url value="${productReference.target.url}/quickView" var="productUrl"/> --%>
<%-- 							<a class="js-reference-item js-quick-view" href="${productUrl}">Quick View</a> --%>
<!-- 						</div> -->
	      	  </ycommerce:testId>
              </c:otherwise>
	       </c:choose>
	      </c:if>
	      	</form:form>
	      	</div>	
	      	</div>
	      </c:forEach>
	     </div>
	   </div>
	    </c:when>
          
	  <c:otherwise>
	    <component:emptyComponent />
	  </c:otherwise>
</c:choose>