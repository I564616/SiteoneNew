<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>

<div class="product__list--wrapper">
	<ul id="headerTabs" class="tabs">
		<li class="seletedTab tabheading col-md-6  col-xs-6" data-tab="productTab"><a href=""><span class="pull-left"><spring:theme code="searchresultslistcomponent.products" /> </span> <span class="pull-left result-no"> (${searchPageData.pagination.totalNumberOfResults} results)</span></a></li>
		<c:if test="${contentSearchPageData.pagination.totalNumberOfResults > 0}">
		<li class="tabheading col-md-6 col-xs-6"  data-tab="contentTab"><a href=""><span class="pull-left"><spring:theme code="searchresultslistcomponent.relatedContent" /></span> <span class="pull-left result-no">(${contentSearchPageData.pagination.totalNumberOfResults} results)</span></a></li>
		</c:if>
	<div class="cl"></div>
	</ul> 
		 
        
        
        
	<c:if test="${null != searchPageData.results}">
	<div id="productTab" class="hidden">
	    <%-- <nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" /> --%>
	<span class="mb-pagination">
	    <nav:pagination top="true"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
	 	 
		</span>
			<ul class="product__listing product__grid">
				 <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
				  <div class="product-item col-xs-6 col-sm-4">
				   <div class="product-item-box">
  							 <product:productListerGridItem product="${product}"/>
  							 </div>
  							 </div>
					 </c:forEach>
			</ul>
		
	
	    <div id="addToCartTitle" class="display-none">
	        <div class="add-to-cart-header">
	            <div class="headline">
	                <span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
	            </div>
	        </div>
        </div>
        <span class="mb-sorting">
	    <nav:pagination top="false"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
		</span>
	</div>
	</c:if>
	
	<div id="contentTab" class="hidden">
	
		<c:if test="${null != contentSearchPageData.results}">
			<nav:searchSpellingSuggestion spellingSuggestion="${contentSearchPageData.spellingSuggestion}" />
		 	
		 	<nav:contentPagination top="true"  supportShowPaged="${content_isShowPageAllowed}" supportShowAll="${content_isShowAllAllowed}"  searchPageData="${contentSearchPageData}" searchUrl="${contentSearchPageData.currentQuery.url}"  numberPagesShown="${content_numberPagesShown}"/>
			
			<c:forEach items="${contentSearchPageData.results}" var="content" varStatus="status">
				<content:contentListerGrid content="${content}"/>
			</c:forEach>
			
			<nav:contentPagination top="false"  supportShowPaged="${content_isShowPageAllowed}" supportShowAll="${content_isShowAllAllowed}"  searchPageData="${contentSearchPageData}" searchUrl="${contentSearchPageData.currentQuery.url}"  numberPagesShown="${content_numberPagesShown}"/>
		
		</c:if>
	
	</div>


</div>