<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTopTotals" required="false" type="java.lang.Boolean" %>
<%@ attribute name="accountPageId" required="false" type="String" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="additionalParams" required="false" type="java.util.HashMap" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="showCurrentPageInfo" required="false" type="java.lang.Boolean" %>
<%@ attribute name="hideRefineButton" required="false" type="java.lang.Boolean" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ attribute name="promotions" required="false" type="java.lang.Boolean" %>
<%@ attribute name="footer" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>



<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showTotals" value="${empty showTopTotals ? true : showTopTotals}"/>

<div class="productResults curatedplp-filter">
<c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top && showTotals}">
    <div class="paginationBar top clearfix">
        <ycommerce:testId code="searchResults_productsFound_label">
             <c:if test="${promotions eq 'true' && footer ne 'true'}">
             	<div class="totalResults">
             		<spring:theme code="${themeMsgKey}.totalResults"
                        arguments="${searchPageData.pagination.totalNumberOfResults}"/>
                </div>
             </c:if>
        </ycommerce:testId>
    </div>
</c:if>
<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
 	<div class="pagination-bar ${(top)?"top":"bottom"}">
 		<div class="pagination-toolbar">
        <%--  <c:if test="${not empty searchPageData.sorts}"> --%>
        	<div class="helper clearfix hidden-md hidden-lg">
        	</div>
        	<c:if test="${cmsPage.uid eq 'accountOrdersPage'}">
	        	<div class="visible-xs displayResultsCount-mobile">
	            	${searchPageData.pagination.totalNumberOfResults} results
	            </div>
            </c:if>
            <div class="p-r-15 sort-refine-bar">
                <div class="row">
					
                    <div class="mobile-filter-align">
                        <c:if test="${promotions ne 'true'}">
                     		<c:if test="${not hideRefBtn}">
								<c:set var="hideArrowSpanFlag" value="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid'}" />
	                            <div class="col-xs-6 col-sm-2 col-md-4 hidden-md hidden-lg mb-sorting-sec">
	                                <ycommerce:testId code="searchResults_refine_button">
	                                    <product:productRefineButton styleClass="btn btn-default js-show-facets" hideArrowSpan="${!hideArrowSpanFlag}"/>
	                                </ycommerce:testId>
	                            </div>
                        	</c:if>
                        	<div class="visible-xs col-xs-6 bold padding0 purchased-hidden mobile-sort-align-plp" >
                        <c:if test="${not empty searchPageData.sorts}">
                       
                            <div class="form-group">
                        <c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'productList'}">
	                          <form id="mobileSortForm${top ? '1' : '2'}" name="mobileSortForm${top ? '1' : '2'}" method="get"
	                                      action="#"><span class="control-label " for="mobileSortForm${top ? '1' : '2'}" style="float:right;margin-right:5px;">
	                             <input type="hidden" class="viewtype" name="viewtype" value="All">
	                             <input type="hidden" id="sort_nearby" name="nearby" value="${fn:contains(currentUrl, 'nearby=on') ? 'on' : ''}">
								<input type="hidden" id="sort_inStock" name="inStock" value="${fn:contains(currentUrl, 'inStock=on') ? 'on' : ''}">
								<input type="hidden" id="sort_expressShipping" name="expressShipping" value="${fn:contains(currentUrl, 'expressShipping=on') ? 'on' : ''}">
	                             <input type="hidden" name="searchParam" id="search-invoicenew"/>
							 	 <input type="hidden" name="dateFrom" id="dateFromnew"/>
								 <input type="hidden" name="dateTo" id="dateTonew"/>
								
	                         <select id="mobilesortOptions${top ? '1' : '2'}"  class="mobile-sort" aria-label="sortOption${top ? 'top' : 'bottom'}" name="sort" class="form-control">
	                          <option disabled> <spring:theme code="${themeMsgKey}.sortTitle"/> </option> 
	                          <c:forEach items="${searchPageData.sorts}" var="sort">
	                           <option value="${sort.code}" ${sort.selected? 'selected="selected"' : ''}>
								 <c:choose> <c:when test="${not empty sort.name}"> <c:choose> <c:when test="${sort.name.indexOf('Default') > -1}"> Sort </c:when> <c:otherwise> ${sort.name} </c:otherwise> 
                        	 </c:choose> </c:when> <c:otherwise> <spring:theme code="${themeMsgKey}.sort.${sort.code}"/> </c:otherwise> </c:choose>
							 </option> 
							 </c:forEach>
	                          </select> 
                                    
                                    <c:catch var="errorException">
                                        <spring:eval expression="searchPageData.currentQuery.query"
                                                     var="dummyVar"/><%-- This will throw an exception is it is not supported --%>
                                        <input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
                                    </c:catch>

                                    <c:if test="${supportShowAll}">
                                        <ycommerce:testId code="searchResults_showAll_link">
                                            <input type="hidden" name="show" value="Page"/>
                                        </ycommerce:testId>
                                    </c:if>
                                    <c:if test="${supportShowPaged}">
                                        <ycommerce:testId code="searchResults_showPage_link">
                                            <input type="hidden" name="show" value="All"/>
                                        </ycommerce:testId>
                                    </c:if>
                                    <c:if test="${not empty additionalParams}">
                                        <c:forEach items="${additionalParams}" var="entry">
                                            <input type="hidden" name="${entry.key}" value="${entry.value}"/>
                                        </c:forEach>
                                    </c:if>
	                          </form>
                          </c:if>
                          </div>
                          </c:if>
                          </div>
                          
                        	<div class="col-xs-4 short-option">
                            	<div class="form-group">
                            		<c:if test="${not empty searchPageData.sorts}">
                            			<c:if test="${cmsPage.uid eq 'search' || cmsPage.uid eq 'productList'}">
                                   			<span><spring:theme code="${themeMsgKey}.sortTitle"/></span>
                                   		</c:if>
                               			<form id="sortForm${top ? '1' : '2'}" name="sortForm${top ? '1' : '2'}" method="get" action="#">
                                      		<span class="control-label sort-div" for="sortForm${top ? '1' : '2'}">
                                  			<div>
			                                   	<c:if test = "${cmsPage.uid eq 'orders' ||   ((cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage') && top)}">   
			                                  	 	<span class="prchased-label sortByLabel" style="float: left;"><label class="filterByLabel" for="sortOptions${top ? '1' : '2'}"><spring:theme code="filter.sortTitle" /></label></span>
			                                   	</c:if>
			                                   	<c:if test = "${cmsPage.uid eq 'invoicelistingpage'}">   
			                                   		<span class="col-xs-4 col-md-3 prchased-label hidden-xs hidden-sm visible-md visible-lg sortByLabel"><label class="sortByFocus bold-text" for="sortOptions${top ? '1' : '2'}"><spring:theme code="text.pagination.sortby"/></label></span>
			                                   	</c:if>
			                                   	<input type="hidden" class="viewtype" name="viewtype" value="All">
			                                   	<input type="hidden" name="searchParam" id="search-invoicenew"/>
												<input type="hidden" name="dateFrom" id="dateFromnew"/>
												<input type="hidden" name="dateTo" id="dateTonew"/>
			                                   	
			                                   	<c:if test="${not (accountPageId eq 'openorderspage' && not top)}">
			                                   	<span class="col-xs-8 sortSelectSpan"> 
				                                   <select id="sortOptions${top ? '1' : '2'}" name="sort" class="form-control <c:if test = "${cmsPage.uid ne 'invoicelistingpage'}">hidden-xs</c:if> sortSelectSpan sortSelect  <c:if test = "${cmsPage.uid eq 'orderShipToPage' || cmsPage.uid eq 'invoiceShipToPage'}">bg-white-imp border-grey-imp</c:if>" >
				                                       
				                                         <c:forEach items="${searchPageData.sorts}" var="sort" varStatus="count">
				                                               <c:if test="${count.index==0 && cmsPage.uid ne 'accountOrdersPage'}">
				                                                	<option disabled selected="selected"><spring:theme code="${themeMsgKey}.sortTitle"/></option>
	                          	                          		</c:if>
	                          									<option value="${sort.code}" ${cmsPage.uid ne accountOrdersPage && sort.code eq 'byLast90Days'? 'selected=selected' : ''}>
																<c:choose>
																	<c:when test="${not empty sort.name}">
																		<c:choose>
																			<c:when test="${sort.name.indexOf('Default') > -1}"> Default </c:when>
																			<c:otherwise> 
																				<c:choose>
																					<c:when test = "${cmsPage.uid eq 'orders' ||   cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage' || accountPageId eq 'orderhistorypage'}">
																						
																						${fn:toUpperCase(fn:substring(sort.name, 0, 1))}${fn:toLowerCase(fn:substring(sort.name, 1,fn:length(sort.name)))}
																					</c:when>
																					<c:otherwise> ${sort.name} </c:otherwise> 
                         														</c:choose>
																			</c:otherwise> 
                         												</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test = "${cmsPage.uid eq 'orders' ||   cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage' || accountPageId eq 'orderhistorypage'}">
																				<c:set var="orderByOptionValue">
																					<spring:theme code="${themeMsgKey}.sort.${sort.code}"/>
																				</c:set>
																				<c:if test="${not empty orderByOptionValue}">
																					${fn:toUpperCase(fn:substring(orderByOptionValue, 0, 1))}${fn:toLowerCase(fn:substring(orderByOptionValue, 1,fn:length(orderByOptionValue)))}
																				</c:if>
																			</c:when>
																			<c:otherwise> <spring:theme code="${themeMsgKey}.sort.${sort.code}"/> </c:otherwise> 
                         												</c:choose>
																		
																	</c:otherwise>
																</c:choose>
                                            				</option>
				                                        </c:forEach>
				                                    </select>
			                                    </span>
			                                    </c:if>
			                                  	
			                                    <c:catch var="errorException">
			                                        <spring:eval expression="searchPageData.currentQuery.query" var="dummyVar"/>
			                                        <%-- This will throw an exception is it is not supported --%>
			                                        <input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
			                                    </c:catch>
			                                    <c:if test="${supportShowAll}">
			                                        <ycommerce:testId code="searchResults_showAll_link">
			                                            <input type="hidden" name="show" value="Page"/>
			                                        </ycommerce:testId>
			                                    </c:if>
			                                    <c:if test="${supportShowPaged}">
			                                        <ycommerce:testId code="searchResults_showPage_link">
			                                            <input type="hidden" name="show" value="All"/>
			                                        </ycommerce:testId>
			                                    </c:if>
			                                    <c:if test="${not empty additionalParams}">
			                                        <c:forEach items="${additionalParams}" var="entry">
			                                            <input type="hidden" name="${entry.key}" value="${entry.value}"/>
			                                        </c:forEach>
			                                    </c:if>
                                 	 		</div>
                                		</form>
                                	</c:if>
                            	</div>
                        	</div>
						</c:if>
						
						<c:if test="${not empty searchPageData.results }">
						<c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'search'}">
							<div class="pageSizeDropDown view-pagination">
								<form action="<c:url value="/search"/>" method="get"
									id="changePageSizeForm${top ? '1' : '2'}">
									<c:set var="pageSize24" value=""></c:set>
									<c:set var="pageSize48" value=""></c:set>
									<c:set var="pageSize72" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 24}">
										<c:set var="pageSize24" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 48}">
										<c:set var="pageSize48" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 72}">
										<c:set var="pageSize72" value="selected='selected'"></c:set>
									</c:if>
									<span><label class="sortByFocus bold-text" for="pageSize${top ? '1' : '2'}"><spring:theme code="invoiceListPagePagination.view" /></label></span><select id="pageSize${top ? '1' : '2'}" name="pagesize" onChange="" class="form-control">
										<option value="24" <c:out value="${pageSize24}"/>>24</option>
										<option value="48" <c:out value="${pageSize48}"/>>48</option>
										<option value="72" <c:out value="${pageSize72}"/>>72</option>
									</select>
									<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
								    <input type="hidden" name="searchtype"  value="product"/>
								</form>
							</div>
							<div class="itemCount">
                                <c:choose>
                                    <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                       <spring:theme code="invoiceListPagePagination.showing" />${searchPageData.pagination.totalNumberOfResults}<span><spring:theme code="text.pagination.of" /> </span>${searchPageData.pagination.totalNumberOfResults} 
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentPageItems"
                                               value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                        <c:set var="upTo"
                                               value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                        <c:set var="currentPage"
                                               value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        <spring:theme code="invoiceListPagePagination.showing" /> <fmt:formatNumber type = "number" 
                                        
                                         groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${upTo}"/><span> <spring:theme code="text.pagination.of" /> </span><fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}" var="resultsTotal"/>
                                         <c:set var = "resultsCount" value = "${fn:replace(resultsTotal, '.', ',')}" />
                                          ${resultsCount}
                                    </c:otherwise>
                                </c:choose>
							</div>
						</c:if>
						
						<c:if test="${cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'productList'}">
							<div class="pageSizeDropDown view-pagination">
								<form action="<c:url value="/c"/>/${categoryCode}" method="get" id="changePageSizeForm${top ? '1' : '2'}">
								<input type="hidden" id="viewtype" name="viewtype" value="All">
									<c:set var="pageSize24" value=""></c:set>
									<c:set var="pageSize48" value=""></c:set>
									<c:set var="pageSize72" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 24}">
										<c:set var="pageSize24" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 48}">
										<c:set var="pageSize48" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 72}">
										<c:set var="pageSize72" value="selected='selected'"></c:set>
									</c:if>
								<span><label class="sortByFocus bold-text" for="pageSize${top ? '1' : '2'}"><spring:theme code="invoiceListPagePagination.view" /></label></span><select id="pageSize${top ? '1' : '2'}" name="pagesize" onChange="" class="form-control">
										<option value="24" <c:out value="${pageSize24}"/>>24</option>
										<option value="48" <c:out value="${pageSize48}"/>>48</option>
										<option value="72" <c:out value="${pageSize72}"/>>72</option>
									</select>
									<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
								</form>
							</div>
							<div class="itemCount">
                                <c:choose>
                                    <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                       <spring:theme code="invoiceListPagePagination.showing" /> ${searchPageData.pagination.totalNumberOfResults}<span> <spring:theme code="text.pagination.of" /> </span>${searchPageData.pagination.totalNumberOfResults} 
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentPageItems"
                                               value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                        <c:set var="upTo"
                                               value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                        <c:set var="currentPage"
                                               value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        <spring:theme code="invoiceListPagePagination.showing" /> <fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${upTo}"/><span> <spring:theme code="text.pagination.of" /> </span><fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}"/>
                                    </c:otherwise>
                                </c:choose>
							</div>
						</c:if>
                        <div class="pagination-wrap product-page-wrap">
							<c:choose>
								<c:when test="${cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'searchGrid' || fn:contains(cmsPage.uid, 'cmsitem_')}">
									<pagination:pageSelectionPaginationPLP searchUrl="${searchUrl}" searchPageData="${searchPageData}"
									numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
								</c:when>
								<c:otherwise>
									<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
									numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
								</c:otherwise>
							</c:choose>
                        </div>
                         </c:if>
					</div>
					<c:if test="${top && showTotals}">
			           
        			</c:if>
        		</div>
			</div>
		</div>
	</div>
</c:if>
</div>
   
 
 
