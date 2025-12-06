<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="searchUrl" required="true"%>
<%@ attribute name="searchPageData" required="true"
	type="com.siteone.contentsearch.ContentSearchPageData"%>
<%@ attribute name="top" required="true" type="java.lang.Boolean"%>
<%@ attribute name="showTopTotals" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="supportShowAll" required="true"
	type="java.lang.Boolean"%>
<%@ attribute name="supportShowPaged" required="true"
	type="java.lang.Boolean"%>
<%@ attribute name="additionalParams" required="false"
	type="java.util.HashMap"%>
<%@ attribute name="msgKey" required="false"%>
<%@ attribute name="showCurrentPageInfo" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="hideRefineButton" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="numberPagesShown" required="false"
	type="java.lang.Integer"%>
<%@ taglib prefix="pagination"
	tagdir="/WEB-INF/tags/responsive/nav/pagination"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<c:set var="themeMsgKey"
	value="${not empty msgKey ? msgKey : 'search.page'}" />
<c:set var="showCurrPage"
	value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}" />
<c:set var="showTotals"
	value="${empty showTopTotals ? true : showTopTotals}" />

<c:if
	test="${searchPageData.pagination.totalNumberOfResults == 0 && top && showTotals}">
	<div class="paginationBar top clearfix">
		<ycommerce:testId code="searchResults_productsFound_label">
			<div class="totalResults">
			<span class="bold-text">There is no related content for "${searchPageData.freeTextSearch}".</span>
			<%--  ${searchPageData.pagination.totalNumberOfResults}&nbsp<spring:theme code="content.no.results"/> --%>
			</div>
		</ycommerce:testId>
	</div>
</c:if>
<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
	<div class="pagination-bar ${(top)?"top":"bottom"}">
		<div class="pagination-toolbar">
			<c:if test="${not empty searchPageData.sorts}">
				<div class="helper clearfix hidden-md hidden-lg"></div>
				<div class="sort-refine-bar">
					<div class="row">
					<c:if test="${not hideRefBtn}">
	                            <div class="col-xs-4 col-sm-2 col-md-4 hidden-md hidden-lg mb-sorting-sec">
	                                <ycommerce:testId code="searchResults_refine_button">
	                                    <product:productRefineButton styleClass="btn btn-default js-show-facets"/>
	                                </ycommerce:testId>
	                            </div>
                        	</c:if> 
						<div class="col-xs-4 sort-by-container">
							<div class="form-group">
								<form id="contentSortForm${top ? '1' : '2'}"
									name="contentSortForm${top ? '1' : '2'}" method="get"
									action="#">
									<span class="control-label hidden-xs hidden-sm visible-md visible-lg"
										for="contentSortForm${top ? '1' : '2'}" style="float: left;">
										<label for="sortOptions${top ? '1' : '2'}">
										<b><spring:theme code="${themeMsgKey}.sortTitle" /></b>
										</label>
									</span> 
									<span class="sort-option-wrapper">
									<select id="sortOptions${top ? '1' : '2'}" name="sort" class="form-control sort-by-select-box sortSelect">
										<option disabled><spring:theme code="${themeMsgKey}.sortTitle"/></option>
										<c:forEach items="${searchPageData.sorts}" var="sort">
											<option value="${sort.code}"
												${sort.selected? 'selected="selected"' : ''}>
                                                <c:choose>
                                                    <c:when test="${not empty sort.name}">
                                                        ${sort.name}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <spring:theme code="${themeMsgKey}.sort.${sort.code}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </option>
										</c:forEach>
									</select>
									</span>
									<c:catch var="errorException">
										<spring:eval expression="searchPageData.currentQuery.query"
											var="dummyVar" />
										<%-- This will throw an exception is it is not supported --%>
										<input type="hidden" name="q"
											value="${searchPageData.currentQuery.query.value}" />
										<input type="hidden" name="searchtype" value="content"/>
									</c:catch>

									<c:if test="${supportShowAll}">
										<ycommerce:testId code="searchResults_showAll_link">
											<input type="hidden" name="show" value="Page" />
										</ycommerce:testId>
									</c:if>
									<c:if test="${supportShowPaged}">
										<ycommerce:testId code="searchResults_showPage_link">
											<input type="hidden" name="show" value="All" />
										</ycommerce:testId>
									</c:if>
									<c:if test="${not empty additionalParams}">
										<c:forEach items="${additionalParams}" var="entry">
											<input type="hidden" name="${entry.key}"
												value="${entry.value}" />
										</c:forEach>
									</c:if>
								</form>
							</div>
						</div>


						<div class="pagination-wrap">
							<pagination:contentPageSelectionPagination
								searchUrl="${searchUrl}" searchPageData="${searchPageData}"
								numberPagesShown="${numberPagesShown}"
								themeMsgKey="${themeMsgKey}" />
						</div>


						<c:if
							test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'productList'}">
							<div class="pageSizeDropDown view-pagination">
								<form action="<c:url value="/search"/>" method="get"
									id="changePageSizeContentForm${top ? '1' : '2'}">
									<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
								<input type="hidden" name="searchtype"  value="content"/>
									<span>
										<label for="pageSizeContent${top ? '1' : '2'}"><b><spring:theme code="invoiceListPagePagination.view" /></b></label>
									 </span><select
										id="pageSizeContent${top ? '1' : '2'}" name="pagesize"
										onChange="" class="form-control">
										<option value="24"
											<c:if test="${searchPageData.pagination.pageSize == 24}"><c:out value="selected='selected'"/></c:if>>24</option>
									<option value="48"
										<c:if test="${searchPageData.pagination.pageSize == 48}"><c:out value="selected='selected'"/></c:if>>48</option>
									<option value="72"
										<c:if test="${searchPageData.pagination.pageSize == 72}"><c:out value="selected='selected'"/></c:if>>72</option>
								</select>
								
							</form>
						</div>
						
						<div class="hidden-xs visible-md visible-lg itemCount">
                                    <c:choose>
                                        <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                           Items ${searchPageData.pagination.totalNumberOfResults}<span> of </span>${searchPageData.pagination.totalNumberOfResults} 
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="currentPageItems"
                                                   value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                            <c:set var="upTo"
                                                   value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                            <c:set var="currentPage"
                                                   value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                            Items ${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}-${upTo}<span> of </span>${searchPageData.pagination.totalNumberOfResults} 
                                        </c:otherwise>
                                    </c:choose>
						</div>
						 </c:if>
					</div>
                </div>
            </c:if>
        </div>
       
        <c:if test="${top && showTotals}">
            <div class="row">
                <div class="col-xs-12">
                    <div class="pagination-bar-results">
                  
                </div>
                </div>
            </div>
        </c:if>
</div>
</c:if>




