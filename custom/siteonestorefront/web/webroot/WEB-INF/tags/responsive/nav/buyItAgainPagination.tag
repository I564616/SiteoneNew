<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="com.siteone.facade.BuyItAgainSearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTopTotals" required="false" type="java.lang.Boolean" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="additionalParams" required="false" type="java.util.HashMap" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="showCurrentPageInfo" required="false" type="java.lang.Boolean" %>
<%@ attribute name="hideRefineButton" required="false" type="java.lang.Boolean" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="buyItAgainFooter" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<span class="buyItAgainTotalResults" data-results="${searchPageData.pagination.totalNumberOfResults}"></span>
<input type="hidden" id="filterCount" value="${searchPageData.pagination.totalNumberOfResults}">

<div class="buy-again-filter-container">
 
<div class="margin15">
<form:form  id="searchForm" method='GET' modelAttribute="searchForm" class="col-md-6 col-xs-12">
 
                     <div class="invoice-serach-sec buy-again-search-container">
                     <label class="print-hidden"><spring:theme code="buyitagain.search"/></label>
                     <label class="print-visible hidden"><spring:theme code="order.history.dates"/></label>
                     <div class="cl hidden-md hidden-sm hidden-lg print-hidden"></div>

                     <div class="col-md-7 col-sm-8 col-xs-9 print-hidden padding0"><input type="text" type="text" id="searchBuyItAgain" name="searchText" value="${searchPageData.freeTextSearch}" placeholder="<spring:theme code="buyitagain.placeholder"/>" class="form-control searchBuyAgain"/></div>
                     <div class="col-md-1 col-xs-2">                     
                   <input type="hidden" id="queryParam" value=":lastpurchaseddate-desc:soLastPurchasedDateFilter:Past 60 Days" />
                   <input type="hidden" id="searchTermWithQueryParam" name="searchParam" value="" />
				        <button class="btn btn-primary print-hidden" onclick="ACC.global.bindBuyItAgain(this)"><spring:theme code="invoicelistingpage.search.go"/></button>
                     </div>
                   </div>
                     </form:form>

								<form action="<c:url value="/my-account/buy-again"/>/${fn:escapeXml(unitId)}" method="get" class="col-md-4 col-xs-12 bia-sort-filters-form padding-LeftZero" id="changePageSizebuyAgainForm${top ? '1' : '2'}">
								<div class="bia-sort-filters-container">
								<div class="bia-sortby-date">
    <span  class="bia-sort-label">  
<label class="sortByFocus bold-text" for="pageSize${top ? '1' : '2'}"><spring:theme code="buyitagain.show" /></label></span>
									<select name="q" class="form-control bia-show-filter" id="filterByDateBuyagain">
									<c:set value=":lastpurchaseddate-desc:soLastPurchasedDateFilter:" var="qParam" />
									<c:set var="isViewAlreadySelected" value="${false}" />
									<c:forEach items="${buyItAgainFilter}" var="facetEntry" varStatus="loop">
										<c:set var="encodedFacetEntry" value="${fn:replace(facetEntry, ' ', '%2B')}" />
										<c:if test="${fn:contains(searchUrl, encodedFacetEntry)}">
											<c:set var="isViewAlreadySelected" value="${true}" />
										</c:if>
										<option value="${searchPageData.freeTextSearch}${qParam}${facetEntry}" data-index="${loop.index}" ${fn:contains(searchUrl, encodedFacetEntry) eq true or (isViewAlreadySelected eq false and loop.index eq 4)? "selected='selected'" : ""}>
										<c:if test="${facetEntry eq 'Past 30 Days'}">
										<spring:theme code="order.datesort30" />
										</c:if>
										<c:if test="${facetEntry eq 'Past 60 Days'}">
										<spring:theme code="order.datesort60" />
										</c:if>
										<c:if test="${facetEntry eq 'Past 90 Days'}">
										<spring:theme code="order.datesort90" />
										</c:if>
										<c:if test="${facetEntry eq 'Past 6 months'}">
										<spring:theme code="order.datesort183" />
										</c:if>
										<c:if test="${facetEntry eq 'Past Year'}">
										<spring:theme code="order.datesort365" />
										</c:if>
										</option>
									</c:forEach>
									</select>
</div>

									<c:set var="pageSize10" value=""></c:set>
									<c:set var="pageSize25" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 10}">
										<c:set var="pageSize10" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 25}">
										<c:set var="pageSize25" value="selected='selected'"></c:set>
									</c:if>
									
									<div class="bia-pagesize">
									<span class="bia-sort-label">  <label class="sortByFocus bold-text"
										for="pageSize${top ? '1' : '2'}"><spring:theme code="invoiceListPagePagination.view" /></label></span><select
										id="pageSizeBuyagain${top ? '1' : '2'}" name="pagesize"
										onChange="" class="form-control order-viewSelect">
										<option value="10"
										<c:if test="${viewByPageSize == 10}"><c:out value="selected='selected'"/></c:if>>10</option>
										<option value="25"
										<c:if test="${viewByPageSize == 25}"><c:out value="selected='selected'"/></c:if>>25</option>
									</select>
									</div> 
										<input id="ViewPaginationbuyagain" type="hidden" class="viewtype" name="viewtype"
										value="All"> 
										 <input type="hidden" name="viewByPageSize" id="pagesize" value="${viewByPageSize}" />
										 <input type="hidden" name="sort" id="sortbuyagain" value="${sort}" />
										 <input type="hidden" class="viewtype" name="viewtype" value="All">
										 <input type="hidden" name="viewByPageSize" id="ViewPaginationbuyagain" value="${viewByPageSize}" />
										 <input type="hidden" name="qval" value="${q}" />
										</div>
								</form>
								</div>
								 <div class="cl"></div>
	</div>
	
<c:if test="${not empty searchPageData.freeTextSearch && not empty searchPageData.results}">
	<div class="col-md-6 col-xs-12 col-sm-12">
    <div class="row results">
    <h3 class="headline2">
    <fmt:formatNumber type = "number" 
				groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}" var="results" />
				<c:set var = "resultsCount" value = "${fn:replace(results, '.', ',')}" />
${resultsCount}&nbsp;<spring:theme code="results.for.lower"/><span class="highlightedSearchText">${searchPageData.freeTextSearch}</span>
</h3>
</div>
</div>
</c:if>
								
<div class="text-right">
<div class="pageSizeDropDown bia-pagination form-group">
						<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">	
                            <pagination:buyItAgainPageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
                                                                numberPagesShown="${numberPagesShown}"
                                                                themeMsgKey="${themeMsgKey}"/>

						</c:if>
                        </div>
                     </div>   