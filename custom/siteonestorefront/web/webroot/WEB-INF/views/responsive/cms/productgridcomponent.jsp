<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="productlistView" tagdir="/WEB-INF/tags/responsive/product/listView" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="allWishlist" value="${allWishlist}" scope="session" />
<c:set var="cmsPage" value="${cmsPage}" scope="session" />
<c:set var="quotesFeatureSwitch" value="${quotesFeatureSwitch}" scope="session" />
<c:set var="isMixedCartEnabled" value="${isMixedCartEnabled}" scope="session" />

<c:if test="${cmsPage.uid ne 'productGrid'}">
    <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                    searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"
                    numberPagesShown="${numberPagesShown}"/>
</c:if>
<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
    <div id="loginId" style="display: none">
        <c:url value="/login/auth" var="oktaLoginActionUrl"/>
        <c:url value="/j_spring_security_check" var="loginActionUrl"/>
        <div class="login-section">
            <user:login actionNameKey="login.login" action="${loginActionUrl}"/>
        </div>
    </div>
</sec:authorize>

<div class="product__listing product__grid">
    <c:set var="totalSearchData" value="${fn:length(searchPageData.results)}"/>
    <c:set var="totalContentData" value="${fn:length(contentSearchPageData.results)}"/>
    <input type="hidden" class="plpviewtype" value="${viewType}">
    <input type="hidden" class="isloggeduser" value="${!isAnonymous}">
   
    <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
        <c:if test="${viewType eq 'card'}">    
            <div class="product-item col-xs-12 col-sm-6 col-md-3" data-product-id="${product.code}" data-cardvariantcount="${product.variantCount}">
                <div class="plp-card product-item-box">
                    <product:productListerGridItem product="${product}" loop="${status.index}"/>
                </div>
           </div>
        </c:if>
        <c:if test="${viewType eq 'list'}">
            <div class="product-item print col-md-12 col-sm-12 col-xs-12" data-product-id="${product.code}" data-variantcount="${product.variantCount}">
                <div class="productlistView product-item-box">
                    <productlistView:productListViewItem product="${product}" loop="${status.index}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${(status.index eq 3 or totalSearchData le 4) and (totalSearchData ge 8) and (totalContentData ge 2) }">
            <div class="col-xs-12 margin20 related-article-container hidden hidden-xs ">
                <product:searchRelatedArticle totalContent="${totalContentData}"/>
            </div>
        </c:if>
        <c:if test="${(status.index eq 1 or totalSearchData le 2) and (totalSearchData ge 4) and (totalContentData ge 2)}">
            <div class="col-xs-12 hidden hidden-sm hidden-md hidden-lg">
                <div class="row xs-grid-related-article">
                    <div class="col-xs-12 margin20 related-article-container">
                        <product:searchRelatedArticle totalContent="${totalContentData}"/>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
    <common:requestQuotePopupPLPPDP />
</div>


<div id="addToCartTitle" class="display-none">
    <div class="add-to-cart-header">
        <div class="headline">
            <span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
        </div>
    </div>
</div>

<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"
                numberPagesShown="${numberPagesShown}"/>


<c:set var="currentPageItems"
       value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
<c:set var="totalData" value="${fn:length(searchPageData.results)}"/>
<c:if test="{totalData le 0}">

<script type="application/ld+json" id="plp-schema">
{
  "@context": "http://schema.org",
  "@type": "ItemList",
<c:forEach items="${searchPageData.sorts}" var="sort">
    <c:if test="${sort.selected eq true}">
        "ItemListOrder": "${sort.name}" ,
    </c:if>
</c:forEach>
"itemListElement": [
 <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
    {
    "@type": "ListItem",
    "position": "${status.count + (searchPageData.pagination.currentPage * searchPageData.pagination.pageSize)}",
    "item": {
        "@type": "Product",
        "name": "${fn:replace(fn:trim(product.productShortDesc),'\"', '\\\"')}",
        "url": "${request.contextPath}${product.url}",
        "offers": {
          }
        }
    }<c:if test="${!status.last}">,</c:if>


</c:forEach>
]
}



</script>
</c:if>
