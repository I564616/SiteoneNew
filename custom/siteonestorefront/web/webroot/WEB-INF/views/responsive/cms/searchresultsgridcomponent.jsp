<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="productlistView" tagdir="/WEB-INF/tags/responsive/product/listView" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="allWishlist" value="${allWishlist}" scope="session" />
<c:set var="cmsPage" value="${cmsPage}" scope="session" />
<c:set var="quotesFeatureSwitch" value="${quotesFeatureSwitch}" scope="session" />
<c:set var="isMixedCartEnabled" value="${isMixedCartEnabled}" scope="session" />

<div class="product__list--wrapper">
	<ul id="headerTabs" class="tabs ">
		<c:choose>
			<c:when test="${searchPageData.pagination.totalNumberOfResults eq 1}">
				<li  class="tabheading col-md-6 col-xs-12 col-xs-6" data-tab="productTab" id="selectedProductTab"><a href="" id="productSearchType" data-free-text-search="${searchPageData.freeTextSearch}"><span class="pull-left bold-text"><spring:theme code="featureProductList.products" />&nbsp;</span> <span class="pull-left result-no 123">(<fmt:formatNumber type = "number" 
           		groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}" var="resultsPlp"/>
           		<c:set var = "resultsCount" value = "${fn:replace(resultsPlp, '.', ',')}" />
           		${resultsCount}&nbsp;<spring:theme code="searchResultGridComponent.result" />) </span></a></li>
			</c:when>
			<c:otherwise>
<li  class="tabheading col-md-6 col-xs-12 col-xs-6" data-tab="productTab" id="selectedProductTab"><a href="" id="productSearchType" data-free-text-search="${searchPageData.freeTextSearch}"><span class="pull-left bold-text"><spring:theme code="featureProductList.products" />&nbsp;</span> <span class="pull-left result-no 123">(<fmt:formatNumber type = "number" 
            groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}" var="resultsPlpCount"/>
            <c:set var = "resultsCount" value = "${fn:replace(resultsPlpCount, '.', ',')}" />
            ${resultsCount}&nbsp;<spring:theme code="searchResultGridComponent.result" />) </span></a></li>
            </c:otherwise>
		</c:choose>
		
         <c:if test="${contentSearchPageData.pagination.totalNumberOfResults > 0}">  
		<li  class="tabheading col-md-5 col-md-offset-1 col-xs-6"  data-tab="contentTab" id="selectedContentTab"><a href="" id="contentSearchType" data-free-text="${searchPageData.freeTextSearch}"><span class="pull-left"><b><spring:theme code="searchResultPage.relatedContent" /></b>&nbsp;</span> <span class="pull-left result-no">(<fmt:formatNumber type = "number" 
           groupingUsed="true"  value = "${contentSearchPageData.pagination.totalNumberOfResults}"/><spring:theme code="productGridPage.results" />) </span></a></li>
        </c:if>   
       
	</ul> 

	<c:if test="${null != searchPageData.results}">
	     
	     <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">			
			<div id="loginId" style="display: none">
				<c:url value="/login/auth" var="oktaLoginActionUrl" />
				<c:url value="/j_spring_security_check" var="loginActionUrl" />
				<div class="login-section">
					<user:login actionNameKey="login.login" action="${loginActionUrl}" />
				</div>
			</div>
		</sec:authorize> 
		
		<div id="productTab">
			<ul class="product__listing product__grid">
			<c:set var="totalSearchData" value="${fn:length(searchPageData.results)}"	/>
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
			</ul>
		
		
	    <div id="addToCartTitle" class="display-none">
	        <div class="add-to-cart-header">
	            <div class="headline">
	                <span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
	            </div>
	        </div>
	    </div>
	<span class="mb-sorting">
	    <nav:pagination top="false"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
	</span>
	</div>
	</c:if>
</div>