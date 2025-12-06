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

<input type="hidden" name="sortinvoice" id="sortOptions1" value="${sort}" />
<input type="hidden" name="searchParam" id="search-invoice-id"  value="${searchParam}"/>
<input type="hidden" name="searchParam" id="search-order-id"  value="${searchParam}"/>
<input type="hidden" id="filterCount" value="${searchPageData.pagination.totalNumberOfResults}">
<input type="hidden" name="searchParam" id="manager-user-voice" value="${searchParam}"/>


<input type="hidden" id="pageSizeOrder1" value="${viewByPageSize}" />

<c:set var="shipToAccount" value="${unitId}"/>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:if test="${not empty accountShiptos}">
<c:set var="shipToAccount" value="${accountShiptos}"/>
</c:if>

<input type="hidden" id="shipToSelected_order" value="${shipToAccount}" />

  
<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showTotals" value="${empty showTopTotals ? true : showTopTotals}"/>

<div class="productResults">
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
 	<div class="pagination-bar ${(top)?"top":"bottom"}">
 		<div class="pagination-toolbar">
        <%--  <c:if test="${not empty searchPageData.sorts}"> --%>
        	<div class="helper clearfix hidden-md hidden-lg">
        	</div>
        	<c:if test="${cmsPage.uid eq 'purchasedProductPage' || accountPageId eq 'purchasedproducts'}">
	        	<div class="visible-xs displayResultsCount-mobile">
	            	${searchPageData.pagination.totalNumberOfResults} results
	            </div>
            </c:if>
			<!-- old invoice filter -->
            <div class="sort-refine-bar">
                <div class="row m-t-10-xs m-t-10-sm">
                    <div>
                        <c:if test="${promotions ne 'true'}">
                     		<c:if test="${not hideRefBtn}">
	                            <div class="col-xs-4 col-sm-2 col-md-4 hidden-md hidden-lg mb-sorting-sec">
	                                <ycommerce:testId code="searchResults_refine_button">
	                                    <product:productRefineButton styleClass="btn btn-default js-show-facets" hideArrowSpan="true"/>
	                                </ycommerce:testId>
	                            </div>
                        	</c:if>
                        	<div class="visible-xs col-xs-4 short-option purchased-hidden">
                        <c:if test="${not empty searchPageData.sorts}">
                       
                            <div class="form-group">
                        <c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'productList'}">
	                          <form id="mobileSortForm${top ? '1' : '2'}" name="mobileSortForm${top ? '1' : '2'}" method="get"
	                                      action="#"><span class="control-label " for="mobileSortForm${top ? '1' : '2'}" style="margin-right:5px;">
	                             <input type="hidden" class="viewtype" name="viewtype" value="All">
	                             
	                             <input type="hidden" name="searchParam" id="search-invoicenew" />
							 	 <input type="hidden" name="dateFrom" id="dateFromnew"/>
								 <input type="hidden" name="dateTo" id="dateTonew"/>
								<input type="hidden" name="pagesize" id="ViewPaginationInvoice" value="${viewByPageSize}" />
	                         <select id="mobilesortOptions${top ? '1' : '2'}" aria-label="sortOption${top ? 'top' : 'bottom'}" name="sort" class="form-control invoice_sort order_sort">
	                          <option disabled> <spring:theme code="${themeMsgKey}.sortTitle"/> </option> 
	                          <c:forEach items="${searchPageData.sorts}" var="sort">
	                           <option value="${sort.code}" ${sort.selected? 'selected="selected"' : ''}>
								 <c:choose> <c:when test="${not empty sort.name}"> <c:choose> <c:when test="${sort.name.indexOf('Default') > -1}"> Sort </c:when> <c:otherwise> ${sort.name} </c:otherwise> 
                        	 </c:choose> </c:when> <c:otherwise> <spring:theme code="${themeMsgKey}.sort.${sort.code}"/> </c:otherwise> </c:choose>
							 </option> 
							 </c:forEach>
	                          </select> 
                                    
                                     <c:set var="attributeSearchpageDataCurrentQuery">
			                            <c:catch var="errorException">${searchPageData.currentQuery.query}</c:catch>
			                         </c:set>
			                        <c:if test="${empty errorException}">
			                          	<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
			                        </c:if>

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
                          <c:set var="shipToopenPopupNew" />
                        	<div class="col-xs-12 col-md-${cmsPage.uid eq 'invoicelistingpage' ? '6' : '5'} invoice-filter" <c:if test="${cmsPage.uid ne 'purchasedProductPage' || accountPageId ne 'purchasedproducts'}"></c:if>>	
                            	<div class="form-group">
                            		<c:if test="${not empty searchPageData.sorts}">
                            			<c:if test="${cmsPage.uid eq 'search' || cmsPage.uid eq 'productList'}">
                                   			<span><spring:theme code="${themeMsgKey}.sortTitle"/></span>
                                   		</c:if>
                               			<form id="sortForm${top ? '1' : '2'}" name="sortForm${top ? '1' : '2'}" method="get" action="#" class="orderHistory-form">
                               			<span class="col-md-2 col-sm-2 col-xs-3 row invoice-mb-label show-invoice"><label class="bold-text" style=" font-size: 14px;color: #000;"><spring:theme code="invoiceListingPagePagination.show"/></label></span>
			                                   		<c:if test = "${cmsPage.uid eq 'invoicelistingpage'}">
			                                   		<span class="col-md-5 col-sm-10 col-xs-6 shipto-dropdown p-l-35"> 
				                                    
				                                    
				                                    <select class="form-control ship-TosInvoice ship-TosInvoice-Selected no-margin" id="shipToSelected_inv" name="invoiceShiptos">
				                                    <option value="All"><spring:theme code="invoiceListPagePagination.allAccount" /></option>
				                                   
				                                    <c:forEach items="${listOfShipTos}" var="shipTo" begin = "0" end = "2">
        												<option value="${shipTo}">${shipTo}</option>
    												</c:forEach>
    												<c:if test="${listOfShipTos.size() gt 3}">
				                                      <option value="shipToopenPopupNew" id="ShipToInvoice_popup" style="color: #50a0c5;"><spring:theme code="invoiceListPagePagination.searchMore" /></option>
				                                    </c:if>
				                                      </select>
				                                      
				                                      </span>
				                                      </c:if>
				                                      <c:if test = "${cmsPage.uid eq 'orders'}">
			                                   		<span class="col-md-5 col-sm-10 col-xs-6 shipto-dropdown padding0"> 
				                                    
				                                    <select class="form-control ship-TosOrder ship-TosOrder-Selected" id="shipToSelected_order" name="accountShiptos">
				                                   
				                                    <c:forEach items="${listOfShipTos}" var="shipTo" begin = "0" end = "2">
        												<option value="${shipTo}">${shipTo}</option>
    												</c:forEach>
    												<c:if test="${listOfShipTos.size() gt 3}">
				                                      <option value="shipToopenPopupNew" id="ShipToOrder_popup" style="color: #50a0c5;"><spring:theme code="invoiceListPagePagination.searchMore" /></option>
				                                    </c:if>
				                                      </select>
				                                      
				                                      </span>
				                                      </c:if>
				                                      <c:if test="${accountPageId eq 'orderhistorypage'}">
				                                      <input type="hidden" id="accountShiptos" name="accountShiptos" value="${accountShiptos}" />
				                                      </c:if>
				                                      <c:if test = "${cmsPage.uid eq 'my-company'}">
			                                   		<span class="col-md-5 col-sm-12 col-xs-12 shipto-dropdown padding0"> 
				                                      
				                                      <select class="form-control ship-TosUser ship-TosUser-Selected ship-TosUsers" id="shipToSelected_user" name="shiptounit">
				                                    <option value="All"><spring:theme code="invoiceListPagePagination.allAccount" /></option>
				                                   
				                                    <c:forEach items="${listOfShipTos}" var="shipTo" begin = "0" end = "2">
        												<option value="${shipTo.key}">${shipTo.value}</option>
    												</c:forEach>
    												<c:if test="${listOfShipTos.size() gt 3}">
				                                      <option value="shipToopenPopupNew" id="ShipToUsers_popup" style="color: #50a0c5;"><spring:theme code="invoiceListPagePagination.searchMore" /></option>
				                                    </c:if>
				                                      </select>
				                                      
				                                      </span>
				                                      </c:if>
				                                      <c:if test = "${cmsPage.uid eq 'ewalletdetailspage'}">
			                                   		<span class="col-md-5 col-sm-12 col-xs-12 shipto-dropdown padding0"> 
				                                      
				                                      <select class="form-control ship-ToseWallet ship-ToseWallet-Selected " id="shipToSelected_eWallet" name="shiptounit">
				                                    <option value="All"><spring:theme code="invoiceListPagePagination.allAccount" /></option>
				                                   
				                                    <c:forEach items="${listOfShipTos}" var="shipTo" begin = "0" end = "2">
        												<option value="${shipTo.key}">${shipTo.value}</option>
    												</c:forEach>
    												<c:if test="${listOfShipTos.size() gt 3}">
				                                      <option value="shipToopenPopupNew" id="ShipToeWallet_popup" style="color: #50a0c5;"><spring:theme code="invoiceListPagePagination.searchMore" /></option>
				                                    </c:if>
				                                      </select>
				                                      
				                                      </span>
				                                      </c:if>
													  
													  
                                      		<span class="control-label ${cmsPage.uid eq 'accountOrdersPage' ? 'hidden' : ''}" for="sortForm${top ? '1' : '2'}" style="width:100%;display: block;margin-right:5px;">
                                  			<div class="sort-section-invoice">
			                                   	<c:if test = "${ cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage'}">   
			                                  	 	<span class="prchased-label sortByLabel" style="float: left;"><label class="filterByLabel" for="sortOptions${top ? '1' : '2'}"><spring:theme code="filter.sortTitle" /></label></span>
			                                   	</c:if>
			                                   	<c:if test = "${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid'|| accountPageId eq 'purchasedproducts' || cmsPage.uid eq 'purchasedProductPage' || cmsPage.uid eq 'orders' || accountPageId eq 'orderhistorypage' || cmsPage.uid eq 'my-company' || cmsPage.uid eq 'ewalletdetailspage'}">   
			                                   		
			                                   		<span class="col-md-5  col-sm-2 col-xs-3 prchased-label  invoice-mb-label sortby-invoice sortByLabel"><label class="sortByFocus bold-text" for="sortOptions${top ? '1' : '2'}" style=" font-size: 14px;color: #000;" ><spring:theme code="invoiceListingPagePagination.sortBy"/></label></span>
			                                   	</c:if>
			                                   	
			                                   	<c:if test = "${cmsPage.uid eq 'invoicelistingpage'}">
			                                   	<input type="hidden" class="viewtype" name="viewtype" value="All">
			                                   	<input type="hidden" name="searchParam" id="search-invoicenew"  value="${searchParam}"/>
												<input type="hidden" name="dateFrom" id="dateFromnew"/>
												<input type="hidden" name="dateTo" id="dateTonew"/>
												<input type="hidden" name="pagesize" id="ViewPaginationInvoice" value="${viewByPageSize}" />
												</c:if>
												<c:if test = "${cmsPage.uid eq 'orders'|| accountPageId eq 'orderhistorypage'}">
												<input type="hidden" class="viewtype" name="viewtype" value="All">
			                                   	<input type="hidden" name="searchParam" id="search-ordernew"  value="${searchParam}"/>
												<input type="hidden" name="dateSort" id="dateSortOrder" value="${dateSort}"/>
												<input type="hidden" name="paymentType" id="paymentTypeOrder" value="${paymentType}" />
												<input type="hidden" name="dateFrom" id="dateFromOrder"/>
												<input type="hidden" name="dateTo" id="dateToOrder"/>
												<input type="hidden" name="pagesize" id="ViewPaginationOrder" value="${viewByPageSize}" />
												</c:if>
			                                   	<c:if test = "${cmsPage.uid eq 'my-company'}">

			                                  	<input type="hidden" class="viewtype" name="viewtype" value="All">
			                                   	<input type="hidden" name="searchParam" id="manager-user-voice"  value="${searchParam}"/>							
												<input type="hidden" name="pagesize" id="ViewPaginationmanager" value="${viewByPageSize}" />
												<input type="hidden" name="filterAdmin" id="admin_User" value="${filterAdmin}"/>
												
			                                   	
			                                   </c:if>
			                                   
			                                   <c:if test = "${cmsPage.uid eq 'ewalletdetailspage'}">
												<input type="hidden" class="viewtype" name="viewtype" value="All">
			                                  	<input type="hidden" name="searchParam" id="ewallet-page-card"  value="${searchParam}"/>
												<input type="hidden" name="pagesize" id="ViewPaginationeWallet" value="${viewByPageSize}" />										
												
			                                   </c:if>
			                                	<c:if test = "${cmsPage.uid ne 'invoicelistingpage'}">
			                                   	<span class="col-xs-5 sortSelectSpan ewallet_changes" style="padding-left: 0px;"> 
				                                    <select id="sortOptions${top ? '1' : '2'}" name="sort" class="form-control invoice_sort order_sort user_sort<c:if test = "${cmsPage.uid ne 'invoicelistingpage' || cmsPage.uid ne 'accountOrdersPage' || cmsPage.uid ne 'orders' || cmsPage.uid ne 'my-company' || cmsPage.uid ne 'ewalletdetailspage' }">  </c:if> sortSelect js-page-sort" <c:if test="${cmsPage.uid ne 'purchasedProductPage' &&  cmsPage.uid ne 'openorderspage'}"></c:if>>
				                                       
				                                         <c:forEach items="${searchPageData.sorts}" var="sort" varStatus="count">
				                                               <c:if test="${count.index==0 && (cmsPage.uid ne 'invoicelistingpage' ||  cmsPage.uid ne 'orders'|| cmsPage.uid ne 'accountOrdersPage' || cmsPage.uid ne 'my-company'|| cmsPage.uid ne 'ewalletdetailspage')}">
				                                                	<option disabled selected="selected"><spring:theme code="${themeMsgKey}.newsortTitle"/></option>
	                          	                               </c:if>
	                          											 <option value="${sort.code}" ${searchPageData.pagination.sort == sort.code  ? 'selected' : '' }>
				                                           
																<c:choose>
																	<c:when test="${not empty sort.name}">
																		<c:choose>
																			<c:when test="${sort.name.indexOf('Default') > -1}"> Default </c:when>
																			<c:otherwise> 
																				<c:choose>
																					<c:when test = "${ cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage'}">
																						
																						${fn:toUpperCase(fn:substring(sort.name, 0, 1))}${fn:toLowerCase(fn:substring(sort.name, 1,fn:length(sort.name)))}
																					</c:when>
																					<c:otherwise> ${sort.name} </c:otherwise> 
                         														</c:choose>
																			</c:otherwise> 
                         												</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test = "${cmsPage.uid eq 'openorderspage' || accountPageId eq 'openorderspage'}">
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
			                                    <c:set var="attributeSearchpageDataCurrentQuery">
			                                    	<c:catch var="errorException">${searchPageData.currentQuery.query}</c:catch>
			                                    </c:set>
			                                    <c:if test="${empty errorException}">
			                                    	<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
			                                    </c:if>
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
                                			</span>
                                		</form>
                                	</c:if>
                            	</div>
                        	</div>
						</c:if>
						<c:if test="${cmsPage.uid eq 'purchasedProductPage' || accountPageId eq 'purchasedproducts'}">
							<div class="pageSizeDropDown view-pagination">
								<form action="<c:url value="/search/changePageSize"/>" method="get"
									id="changePageSizeForm${top ? '1' : '2'}">

								</form>
							</div>
							<div class="displayResultsCount hidden-xs">
								<c:choose>
	                                <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
	                                 <spring:theme code="text.pagination.displayResult" /> ${searchPageData.pagination.totalNumberOfResults} <span> <spring:theme code="text.pagination.of" /> </span>${searchPageData.pagination.totalNumberOfResults}
	                                </c:when>
	                                    <c:otherwise>
	                                        <c:set var="currentPageItems"
	                                                value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
	                                        <c:set var="upTo"
	                                                value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
	                                        <c:set var="currentPage"
	                                                value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
	                                         Display Results <fmt:formatNumber type = "number"
	                                          groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number"
	                                          groupingUsed="true"  value = "${upTo}"/><span> <spring:theme code="text.pagination.of"/> </span><fmt:formatNumber type = "number"
	                                          groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}"/>
	                                    </c:otherwise>
	                            </c:choose>
                            </div>
						</c:if>
						
						<!-- Invoice Listing Page -->
						<c:if test="${cmsPage.uid eq 'invoicelistingpage'}">
							<div class="pageSizeDropDown view-pagination form-group col-md-2 hidden">
								<form action="<c:url value="/my-account/invoices"/>/${unitId}" method="get" id="changePageSizeFormInvoice${top ? '1' : '2'}" class="display-block-sm display-block-xs flex-center" >
									<c:set var="pageSize25" value=""></c:set>
									<c:set var="pageSize50" value=""></c:set>
									<c:set var="pageSize100" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 25}">
										<c:set var="pageSize25" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 50}">
										<c:set var="pageSize50" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 100}">
										<c:set var="pageSize100" value="selected='selected'"></c:set>
									</c:if>
									<span class="col-md-5 col-xs-3 padding0">  <label class="sortByFocus bold-text"
										for="pageSize${top ? '1' : '2'}"><spring:theme code="invoiceListPagePagination.view" /></label></span><select
										id="pageSizeInvoice${top ? '1' : '2'}" name="pagesize"
										onChange="" class="form-control">
										
										<option value="25"
										<c:if test="${viewByPageSize == 25}"><c:out value="selected='selected'"/></c:if>>25</option>
										<option value="50" <c:if test="${viewByPageSize == 50}"><c:out value="selected='selected'"/></c:if>>50</option>
										<option value="100"
										<c:if test="${viewByPageSize == 100}"><c:out value="selected='selected'"/></c:if>>100</option>
										
									</select> <input type="hidden" class="viewtype" name="viewtype"
										value="All"> 
										<input id="ViewPaginationInvoice" type="hidden" class="viewtype" name="viewtype"
										value="All"> 
										 <input type="hidden" name="searchParam" id="search-invoicenew" />
										 <input type="hidden" name="dateFrom" id="dateFromnew" />
										 <input type="hidden" name="dateTo" id="dateTonew" />
										 <input type="hidden" name="viewByPageSize" id="pagesize" value="${viewByPageSize}" />
										 <input type="hidden" name="sort" id="sortinvoice" value="${sort}" />
										 <input type="hidden" name="invoiceShiptos" id="shipToSelected_inv" value="${listOfShipTos}"/> 
								</form>
															
							</div>
						</c:if>
						
						<!-- Manager User Page -->
						<c:if test="${cmsPage.uid eq 'my-company'}">
						
							<div class="pageSizeDropDown view-pagination form-group"  >
								<form action="${homelink}my-company/organization-management/manage-users/${unit}" method="get"

									id="changePageSizeFormManagerUser${top ? '1' : '2'}" style=" margin-top: 13px;">


									<c:set var="pageSize10" value=""></c:set>
									<c:set var="pageSize20" value=""></c:set>
									<c:set var="pageSize30" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 10}">
										<c:set var="pageSize10" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 20}">
										<c:set var="pageSize20" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 30}">
										<c:set var="pageSize30" value="selected='selected'"></c:set>
									</c:if>
									<span>  <label class="sortByFocus bold-text"
										for="pageSize${top ? '1' : '2'}" style="font-size: 14px; color: #000;"><spring:theme code="invoiceListPagePagination.view" /></label></span><select

										

										id="pageSizeManagerUser${top ? '1' : '2'}" name="pagesize" 
										onChange="" class="form-control user-manager ">

										
										<option value="10"<c:if test="${pagesize == 10}"><c:out value="selected='selected'"/></c:if>>10</option>
										<option value="20" <c:if test="${pagesize == 20}"><c:out value="selected='selected'"/></c:if>>20</option>

										<option value="30" <c:if test="${pagesize == 30}"><c:out value="selected='selected'"/></c:if>>30</option>


										
									</select> 
										<input id="ViewPaginationmanager" type="hidden" class="viewtype" name="viewtype"
										value="All" > 

										 <input type="hidden" name="searchParam" id="manager-user-voice"  value="${searchParam}"/>
										<input type="hidden" name="pageSize" id="ViewPaginationmanager" value="${pagesize}" />										
										<input type="hidden" class="viewtype" name="viewtype" value="All">
										 <input type="hidden" name="sort" id="sortinvoice" value="${sort}" />
										 <input type="hidden" name="shiptounit" id="shipToSelected_user" value="${listOfShipTos}"/>
										 <input type="hidden" name="filterAdmin" id="admin_User" value="${filterAdmin}"/>

								</form>
															
							</div>

						</c:if>
						<!-- Ewallet page -->
								<c:if test="${cmsPage.uid eq 'ewalletdetailspage'}">
						
							<div class="pageSizeDropDown view-pagination form-group"  >
								<form action="${homelink}my-account/ewallet/${unitId}" method="get"

									id="changePageSizeFormEwallet${top ? '1' : '2'}" style=" margin-top: 13px;">


									<c:set var="pageSize10" value=""></c:set>
									<c:set var="pageSize20" value=""></c:set>
									<c:set var="pageSize30" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 10}">
										<c:set var="pageSize10" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 20}">
										<c:set var="pageSize20" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 30}">
										<c:set var="pageSize30" value="selected='selected'"></c:set>
									</c:if>
									<span>  <label class="sortByFocus bold-text"
										for="pageSize${top ? '1' : '2'}" style="font-size: 14px; color: #000;"><spring:theme code="invoiceListPagePagination.view" /></label></span><select

										
										id="pageSizeeWallet${top ? '1' : '2'}" name="pagesize" 
										onChange="" class="form-control user-manager ">

										
										<option value="10"<c:if test="${pagesize == 10}"><c:out value="selected='selected'"/></c:if>>10</option>
										<option value="20" <c:if test="${pagesize == 20}"><c:out value="selected='selected'"/></c:if>>20</option>

										<option value="30" <c:if test="${pagesize == 30}"><c:out value="selected='selected'"/></c:if>>30</option>


										
									</select> 
									<input id="ViewPaginationeWallet" type="hidden" class="viewtype" name="viewtype"
										value="All" >
										 <input type="hidden" name="searchParam" id="ewallet-page-card"  value="${searchParam}"/>
										<input type="hidden" name="pageSize" id="ViewPaginationeWallet" value="${pagesize}" />										
										<input type="hidden" class="viewtype" name="viewtype" value="All">
										 <input type="hidden" name="sort" id="sortinvoice" value="${sort}" />
										 <input type="hidden" name="shiptounit" id="shipToSelected_eWallet" value="${listOfShipTos}"/>

								</form>
															
							</div>

						</c:if>
						<!-- Invoice Listing Page -->
						
						<!-- Orders Listing Page -->
						<c:if test="${cmsPage.uid eq 'orders'}">
							<div class="pageSizeDropDown view-pagination form-group">
								<form action="<c:url value="/my-account/orders"/>/${unitId}" method="get"
									id="changePageSizeFormOrder${top ? '1' : '2'}">
									<c:set var="pageSize10" value=""></c:set>
									<c:set var="pageSize25" value=""></c:set>
									<c:if test="${searchPageData.pagination.pageSize == 10}">
										<c:set var="pageSize10" value="selected='selected'"></c:set>
									</c:if>
									<c:if test="${searchPageData.pagination.pageSize == 25}">
										<c:set var="pageSize25" value="selected='selected'"></c:set>
									</c:if>
									
									<span>  <label class="sortByFocus bold-text"
										for="pageSize${top ? '1' : '2'}"><spring:theme code="invoiceListPagePagination.view" /></label></span><select
										id="pageSizeOrder${top ? '1' : '2'}" name="pagesize"
										onChange="" class="form-control order-viewSelect">
										
										<option value="10"
										<c:if test="${viewByPageSize == 10}"><c:out value="selected='selected'"/></c:if>>10</option>
										
										
										<option value="25"
										<c:if test="${viewByPageSize == 25}"><c:out value="selected='selected'"/></c:if>>25</option>
										
									</select> <input type="hidden" class="viewtype" name="viewtype"
										value="All"> 
										<input id="ViewPaginationOrder" type="hidden" class="viewtype" name="viewtype"
										value="All"> 
										<input type="hidden" name="searchParam" id="search-ordernew"/>
										<input type="hidden" name="dateFrom" id="dateFromOrder"/>
										<input type="hidden" name="dateTo" id="dateToOrder"/>
											<input type="hidden" name="dateSort" id="dateSortOrder"/>
											<input type="hidden" name="paymentType" id="paymentTypeOrder" />
										 <input type="hidden" name="viewByPageSize" id="pagesize" value="${viewByPageSize}" />
										 <input type="hidden" name="sort" id="sortinvoice" value="${sort}" />
										 <input type="hidden" class="viewtype" name="viewtype" value="All">
										 <input type="hidden" name="viewByPageSize" id="ViewPaginationOrder" value="${viewByPageSize}" />
										 <input type="hidden" name="listOfShipTos" id="shipToSelected_order" value="${listOfShipTos}"/>
			                                   	
								</form>
															
							</div>
						</c:if>
						<!-- Orders Listing Page -->
						
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
							<div class="itemCount hidden-xs visible-md visible-lg"> 
                                <c:choose>
                                    <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                       <spring:theme code="invoiceListPagePagination.show" /> ${searchPageData.pagination.totalNumberOfResults}<span> <spring:theme code="text.pagination.of" /> </span>${searchPageData.pagination.totalNumberOfResults} 
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentPageItems"
                                               value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                        <c:set var="upTo"
                                               value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                        <c:set var="currentPage"
                                               value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        <spring:theme code="invoiceListPagePagination.show" /> <fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${upTo}"/><span> <spring:theme code="text.pagination.of" /> </span><fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}"/>
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
							<div class="itemCount hidden-xs visible-md visible-lg">
                                <c:choose>
                                    <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                       <spring:theme code="invoiceListPagePagination.show" /> ${searchPageData.pagination.totalNumberOfResults}<span> <spring:theme code="text.pagination.of" /> </span>${searchPageData.pagination.totalNumberOfResults} 
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentPageItems"
                                               value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                        <c:set var="upTo"
                                               value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                        <c:set var="currentPage"
                                               value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        <spring:theme code="invoiceListPagePagination.show" /> <fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1}"/>-<fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${upTo}"/><span> of </span><fmt:formatNumber type = "number" 
                                         groupingUsed="true"  value = "${searchPageData.pagination.totalNumberOfResults}"/>
                                    </c:otherwise>
                                </c:choose>
							</div>
						</c:if>
                        <div class="pagination-wrap product-page-wrap ${cmsPage.uid eq 'my-company' ? 'hidden' : ''}" id="${cmsPage.uid eq 'accountOrdersPage' ? 'order-page' : 'invoice-page'}">
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
</div>
