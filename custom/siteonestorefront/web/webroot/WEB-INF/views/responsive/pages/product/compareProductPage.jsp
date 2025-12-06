<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<h1 class="headline"><spring:theme code="text.compare.Heading"/></h1>
<c:url var="CUrl" value="${url}"/>
<a href="${CUrl}"><spring:theme code="text.compare.back" /></a>
<br><br><br> 
<div class="row productCompareGrid">
<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
	<hr class="separation-line" />
	<div class="col-md-3"><h5 class="bold-text black-title"><spring:theme code="text.compare.Description"/></h5></div>
	<div class="col-md-9">
	<div class="product__listing product__grid">
	<input type="hidden" value="${categoryCode}" id="compare_category_code"/>
		<c:forEach items="${productList}" var="product" >
		<c:set var="productCodes" value="${index.first ? '' : productCodes}${product.code}," />
			<product:productListerCompare product="${product}" />
			</c:forEach>
			
	 	</div>
	 	<input type="hidden" value="${fn:substring(productCodes,0,fn:length(productCodes)-1)}" id="compareProductCodes"/>
	 	<input type="hidden" value="${fn:length(productList)}" id="number_of_products"/>
	</div>


<div class="product-classifications col-md-12">

<c:set var = "product" value ="${productList.get(0)}"/>
<c:if test="${not empty product.classifications}">
	<hr/>
	<h5 class="margin40"><b style="color:black;"><spring:theme code="text.compare.Subheading"/></b></h5>
<%-- <table class="table product-compare">
	<tbody>
		<c:forEach items="${product.classifications}" var="classification" varStatus="classLoop">			
			<c:forEach items="${classification.features}" var="feature" varStatus="loop">
				<tr>
					<td class="attrib">${feature.name}</td>
						<c:forEach items="${productList}" var="product1" >
					       <c:if test="${not empty product1.classifications}">
					           <c:forEach items="${product1.classifications}" var="classification1">
				              	 <c:if test="${classification1.name eq classification.name}">
					            	<c:forEach items="${classification1.features}" var="feature1" varStatus="loop">	
                                    	<c:if test="${feature1.name eq feature.name}">
											<td>
												<c:forEach items="${feature1.featureValues}" var="value" varStatus="status">
													${value.value}
													<c:choose>
														<c:when test="${feature1.range}">
															${not status.last ? '-' : feature1.featureUnit.symbol}
														</c:when>
														<c:otherwise>
														${feature1.featureUnit.symbol}
														${not status.last ? '<br/>' : ''}
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</td>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
				</tr>
		  </c:forEach>
		</c:forEach>
	</tbody>
</table> --%>

<div class="product-compare">
	<c:forEach items="${product.classifications}" var="classification" varStatus="classLoop">
		<c:forEach items="${classification.features}" var="feature" varStatus="loop">
			<div class="col-md-12 rowbg">
				<div class="col-md-3 tabledesc">${feature.name}</div>
				<c:forEach items="${productList}" var="product1" >
					<c:if test="${not empty product1.classifications}">
						<c:forEach items="${product1.classifications}" var="classification1">
							<c:if test="${classification1.name eq classification.name}">
							   <c:forEach items="${classification1.features}" var="feature1" varStatus="loop">
								  <c:if test="${feature1.name eq feature.name}">
									<div class="col-md-3 tablefeatures">
										<c:forEach items="${feature1.featureValues}" var="value" varStatus="status">
											${value.value}
											<c:choose>
												<c:when test="${feature1.range}">
													${not status.last ? '-' : feature1.featureUnit.symbol}
												</c:when>
												<c:otherwise>
													${feature1.featureUnit.symbol}
													${not status.last ? '<br/>' : ''}
												</c:otherwise>
											</c:choose>
										</c:forEach>								  
									</div>
								  </c:if>
                           </c:forEach>
                        </c:if>
                     </c:forEach>
                  </c:if>
               </c:forEach>
			</div>
		</c:forEach>
	</c:forEach>
</div>
</c:if>
</div>