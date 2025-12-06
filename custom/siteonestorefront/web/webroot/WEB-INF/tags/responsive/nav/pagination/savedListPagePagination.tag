<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
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
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ attribute name="promotions" required="false" type="java.lang.Boolean" %>
<%@ attribute name="footer" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showTotals" value="${empty showTopTotals ? true : showTopTotals}"/>
<input type="hidden" id="filterCount" value="${searchPageData.pagination.totalNumberOfResults}">

		
		<%-- <c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
		<span class="pull-left listShow-label"><label style="line-height:45px;font-size: 10pt;"><b><spring:theme code ="savedList.pagination.show"/></b></label></span>
		<div class="custom_dropdown col-md-10 col-sm-6 col-xs-10">
		
		<select id="showImage">
			<option value="listWithImages"><spring:theme code="savedList.withImages"/></option>
			<option value="listWithNoImages"><spring:theme code="savedList.withoutImages"/></option>
		</select>
		</c:if> --%>
		<c:if test="${searchPageData.pagination.totalNumberOfResults <= 0 && top}">
		<div class=" list-filter">
		<div class="col-md-3 col-sm-10 col-xs-9 ">
		<div class="row">
			<strong><spring:theme code ="savedList.pagination.noProductsAdded"/></strong>
			</div>
			</div>
		</div>
		</c:if>
		
		
		
<div class="productResults col-md-12 col-sm-2 col-xs-3 padding-zero">
 

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
<c:set var="totalNumberOfResults" value="${searchPageData.pagination.totalNumberOfResults}" />
<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
<div class="row">
 	<div class="pagination-bar ${(top)?"top":"bottom"}">
 		<div class="pagination-toolbar">
        	<div class="helper clearfix hidden-md hidden-lg">
        	</div>
        	 <%-- <div class="visible-xs displayResultsCount-mobile">
	            	${searchPageData.pagination.totalNumberOfResults} 
	         </div> --%>
            <div class="sort-refine-bar black-title">
                
                    <div>
                        <c:if test="${promotions ne 'true'}">
                     		<c:if test="${not hideRefBtn}">
	                            <div class="col-xs-4 col-sm-2 col-md-4 hidden-md hidden-lg mb-sorting-sec">
	                                <ycommerce:testId code="searchResults_refine_button">
	                                    <product:productRefineButton styleClass="btn btn-default js-show-facets" hideArrowSpan="true"/>
	                                </ycommerce:testId>
	                            </div>
                        	</c:if>
                        	
						</c:if>
						
						<c:if test="${not empty searchPageData.results and totalNumberOfResults > 25}">
							<div class="pageSizeDropDown col-md-3 custom_dropdown padding-zero hidden-print">
							<form action="${homelink}savedList/listDetails?code=${code}" method="get" id="changePageSizeFormSavedListDetail${top ? '1' : '2'}">
								<input type="hidden" id="code" name="code" value="${code}">
								<input type="hidden" id="ViewPaginationList" class="viewtype" name="viewtype" value="All"> 
								<input type="hidden" name="viewByPageSize" id="ViewPaginationSavedListDetail" value="${viewByPageSize}" />	
								<input type="hidden" name="selectedProducts" id="selectedProducts" value="${selectedProducts}" />			
									<c:set var="pageSize25" value=""></c:set>
									<c:set var="pageSize50" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 25}">
										<c:set var="pageSize25" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 50}">
										<c:set var="pageSize50" value="selected='selected'"></c:set>
									</c:if>
								<span><label class="sortByFocus bold-text hidden-print" for="pageSize${top ? '1' : '2'}"><spring:theme code="savedList.pagination.view"/></label></span>
								<div class="col-md-6 col-sm-6 col-xs-7 padding-zero">
								<select id="pageSizeSavedListDetail${top ? '1' : '2'}" name="pagesize" onChange="" class="form-control">
										<option value="25"
										<c:if test="${viewByPageSize == 25}"><c:out value="selected='selected'"/></c:if>>25</option>
										<c:if test="${totalNumberOfResults > 25 }">
										<option value="50" <c:if test="${viewByPageSize == 50}"><c:out value="selected='selected'"/></c:if>>50</option>
										</c:if>
									</select>
									</div>
								</form>
							</div>
							<div class="itemCount hidden">
							
								<input type="hidden" name="pagesize" id="pagesize" value="${viewByPageSize}" />
                                <c:choose>
                                    <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                       Showing ${searchPageData.pagination.totalNumberOfResults}<span> of </span>${searchPageData.pagination.totalNumberOfResults} 
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentPageItems"
                                               value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                        <c:set var="upTo"
                                               value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                        <c:set var="currentPage"
                                               value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        Showing <fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${upTo}"/><span> of </span><fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}"/>
                                    </c:otherwise>
                                </c:choose>
							</div>
	                        <div class="pagination-wrap product-page-wrap savedlistdetails-pagination">
                            	<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
                                                                numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
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
