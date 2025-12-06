<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>

<div class="product__listing product__list">
    <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
     <div class="product-item">
     <div class="product-item-box">
    <c:if test="${!product.multidimensional || product.variantCount == 1}">
    	<div class="colored" style="height:25px;border:red solid 1px;">
		<span class="hidden-xs hidden-sm visible-md visible-lg"><input id="compareType${status.index}" class ="product__compare__checkbox"  type="checkbox"   value="Compare" data-role="${status.index}"> <spring:theme code="productGridPage.compare"/><BR></span>
		</div>
        <input id="prodCode${status.index}" type="hidden"  name="code" value="${product.code}" > <br>
        <input type="hidden" id="categoryCode" value="${categoryCode}">
         <c:set var="image" value="${product.images[0].url}"/>
          <c:choose>
           <c:when test="${not empty image}">
           <input type="hidden" id="prodImage${status.index}" value="${image}">
           </c:when>
            <c:otherwise>
            <input type="hidden" id="prodImage${status.index}" value="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg">
            </c:otherwise>
             </c:choose>
	
	</c:if>
        <product:productListerItem product="${product}"/>
        </div>
        </div>
    </c:forEach>
</div>

<div id="addToCartTitle" class="display-none">
    <div class="add-to-cart-header">
        <div class="headline">
            <span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
        </div>
    </div>
</div>

<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>

<storepickup:pickupStorePopup/>