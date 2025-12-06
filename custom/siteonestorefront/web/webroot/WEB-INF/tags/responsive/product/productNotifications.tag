<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ attribute name="product" type="de.hybris.platform.commercefacades.product.data.ProductData" %>


<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>

                 <%-- 	 <c:set var="isMyStoreProduct" value="false" />
			           <c:forEach items="${product.stores}" var="store">
				          <c:if test="${store eq sessionStore.storeId}">
					         <c:set var="isMyStoreProduct" value="true" />
				          </c:if>
			           </c:forEach>
                 	<c:if test="${product.variantCount==0}">
				    <c:if test="${product.isRegulateditem}">
				      <c:set var="isProductSellable" value="false" />
				   <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
					  <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
						<c:set var="isProductSellable" value="true" />
					 </c:if>
				   </c:forEach>
					<c:if test="${isMyStoreProduct && !isProductSellable}"><br/><spring:theme code="text.product.regulatedItem.notApproved" arguments="${sessionStore.address.phone}"/></c:if>
					<c:if test="${!isMyStoreProduct && isProductSellable}"><br/><spring:theme code="text.product.regulatedItem.approved.notSelectedStore" arguments="${sessionStore.address.phone}"/></c:if>
					<c:if test="${isMyStoreProduct && isProductSellable}">
						<c:choose>
							<c:when test="${product.stock.stockLevel gt 0}">
							<c:if test="${product.stock.stockLevel gt 0 and empty product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel le product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel gt product.stock.stockThreshold}">
                            	<c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                            </c:if>
							</c:when>
							<c:otherwise>
							<c:if test="${product.purchasable and product.stock.stockLevel le 0}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.out.of.stock"/></c:set>
                           </c:if>
							</c:otherwise>	
						</c:choose>
					</c:if>
			   	</c:if>
			   	 
			   	<c:if test="${!product.isRegulateditem  && product.isProductDiscontinued}">
			   		<c:choose>
							<c:when test="${product.stock.stockLevel gt 0}">
							<c:if test="${product.stock.stockLevel gt 0 and empty product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel le product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel gt product.stock.stockThreshold}">
                            	<c:set var="productStockLevel"><spring:theme code="product.variants.in.stock"/> </c:set>
                            </c:if>
							</c:when>
							<c:otherwise>
							<c:if test="${product.purchasable and product.stock.stockLevel le 0}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.out.of.stock"/></c:set>
                           </c:if>
							</c:otherwise>	
						</c:choose>
			   	</c:if>
			   	
			   	<c:if test="${!product.isRegulateditem   && !product.isProductDiscontinued}">
			   		<c:choose>
							<c:when test="${product.stock.stockLevel gt 0}">
							<c:if test="${product.stock.stockLevel gt 0 and empty product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel">${product.stock.stockLevel}<spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel le product.stock.stockThreshold}">
                   	           <c:set var="productStockLevel">${product.stock.stockLevel}<spring:theme code="product.variants.in.stock"/> </c:set>
                           </c:if>
                            <c:if test="${product.stock.stockLevel gt 0 and product.stock.stockLevel gt product.stock.stockThreshold}">
                            	<c:set var="productStockLevel">${product.stock.stockThreshold}+<spring:theme code="product.variants.in.stock"/> </c:set>
                            </c:if>
							</c:when>
							<c:otherwise>
							<c:if test="${product.purchasable and product.stock.stockLevel le 0}">
                   	           <c:set var="productStockLevel"><spring:theme code="product.variants.out.of.stock"/></c:set>
                           </c:if>
							</c:otherwise>	
						</c:choose>
			   	</c:if>
			</c:if>

				
				  <!-- Price Unavailability message for SimpleProduct,Base Product and Variant Product -->
				  <c:set var="hideList" value="${product.hideList}"/>
				  <c:set var="hideCSP" value="${product.hideCSP}"/>
				  <c:if test="${(empty product.baseProduct && product.variantCount eq 0 && (empty product.price && empty product.customerPrice)) 
				  || (empty product.baseProduct && product.variantCount > 0 && (empty product.price && empty product.customerPrice && (empty product.priceRange.minPrice || product.priceRange.minPrice.value <= 0)))
				  || (not empty product.baseProduct && ((empty product.price && empty product.customerPrice)))}"> 
				 	<div class="price-unavailable-wrapper"><h5><spring:theme text="${priceUnavailableMsg}" arguments="${contactNo}"/></h5><br/></div>
				  </c:if>
				   --%>

  <c:if test="${hideCSP ne true}"> 
	<span class="bold-text"><spring:theme
			text="${product.stockAvailabilityMessage}"
			arguments="${contactNo}" /></span>
	<span class="stock-status bold-text">${productStockLevel}</span>
 </c:if> 


                
