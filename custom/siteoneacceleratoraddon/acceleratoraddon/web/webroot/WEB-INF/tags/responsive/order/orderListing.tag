<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" type="String" %>
<%@ attribute name="messageKey" required="true" type="String" %>
<%@ attribute name="accountpageId" required="true" type="String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>
<spring:url value="/my-account/invoice/" var="invoiceDetailsUrl" htmlEscape="false"/>
<spring:url value="/my-account/masterOrder/" var="masterOrderDetailsUrl" htmlEscape="false"/>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:set var="searchUrl" value="/my-account/orders/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>
<input type="hidden" id="cmspageuid" value="${cmsPage.uid}">
<input type="hidden" id="unitId" value="${unitId}"/>
<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId}"/>

<c:set var="shipToAccount" value="${unitId}"/>

<c:if test="${not empty accountShiptos}">
<c:set var="shipToSplit" value="${fn:split(accountShiptos,' ')}"/>
<c:set var="shipToAccount" value="${shipToSplit[0]}"/>

<c:if test="${currentBaseStoreId eq 'siteone'}">
<c:if test="${not fn:contains(shipToAccount,'_US')}">
<c:set var="shipToAccount" value="${shipToSplit[0]}_US"/>
</c:if>

</c:if>
    <c:if test="${currentBaseStoreId eq 'siteoneCA'}">
    <c:if test="${not fn:contains(shipToAccount,'_CA')}">
   <c:set var="shipToAccount" value="${shipToSplit[0]}_CA"/>
   </c:if>
    </c:if>
</c:if>
<input type="hidden" id="businessUnitId" value="${shipToAccount}"/>
<input type="hidden" id="trans_trackshiping" value="<spring:theme code="text.consignment.trackshiping" />"/>
<input type="hidden" id="trans_trackdelivery" value="<spring:theme code="text.consignment.trackdelivery" />"/>
<form:form id="searchForm" method='GET' modelAttribute="searchForm">
<input type="hidden" name="accountShiptos" value="${shipToAccount}"/>
<input type="hidden" id="paymentType" name="paymentType" value="ALL"/>
<div class="black-title f-s-28 f-w-500 flex font-Geogrotesque m-y-20"><spring:theme code="text.account.orderHistory.myorders"/> (${searchPageData.pagination.totalNumberOfResults} &nbsp;<spring:theme code="text.account.orderHistory.results"/>)</div>
<common:orderlisting-tabs shipToAccount="${shipToAccount}"/>
<c:if test="false">
	<div class="row invoice-serach-sec">
		<c:if test="${childUnits.size() gt 1}">
			<div class="italic-text padding-bottom-15">
				<spring:theme code="account.shipto.select.message" />
			</div>
		</c:if>
		<label class="print-hidden">
			<spring:theme code="myorder.search" />
		</label>
		<label class="print-visible hidden">
			<spring:theme code="order.history.dates" />
		</label>
		<div class="col-md-2 col-sm-6 col-xs-12 print-hidden m-b-15-xs dropdown order-dropdown-box">
			<button class="btn btn-white-border btn-block dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				<span class="order-dropdown-select">Order #</span>
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
				<li class="b-b-grey disabled" data-type="dropdown-menu-order"><a class="font-size-14" href="#" onclick="ACC.order.orderDropdownSearchType(this, 'Order', 'oSearchParam', '#searchOrderHistory')">Order #</a></li>
				<li class="b-b-grey" data-type="dropdown-menu-invoice"><a class="font-size-14" href="#" onclick="ACC.order.orderDropdownSearchType(this, 'Invoice', 'iSearchParam', '#searchOrderHistory')">Invoice #</a></li>
				<li data-type="dropdown-menu-po"><a class="font-size-14" href="#" onclick="ACC.order.orderDropdownSearchType(this, 'PO', 'pnSearchParam', '#searchOrderHistory')">PO #</a></li>
			</ul>
		</div>
		<div class="col-md-2 col-sm-6 col-xs-6 m-b-15-xs print-hidden"><input type="text" type="text" id="searchOrderHistory" name="oSearchParam" value="${searchParam}" placeholder="<spring:theme code="orderlisting.placeholder" />" class="form-control"/></div>
		<div class="col-md-2 col-sm-6 col-xs-6 m-b-15-xs">
			<select class="form-control order-dateSort dateSort-Selected sortSelectSpan date-sort-my-orders" id="dateSort" name="dateSort" data-datesort="${dateSort}">
				<option value="by30days">
					<spring:theme code="order.datesort30" />
				</option>
				<option value="by60days">
					<spring:theme code="order.datesort60" />
				</option>
				<option value="by90days" selected>
					<spring:theme code="order.datesort90" />
				</option>
				<option value="by184days">
					<spring:theme code="order.datesort183" />
				</option>
				<option value="by365days">
					<spring:theme code="order.datesort365" />
				</option>
				<option value="by730days">
					<spring:theme code="order.datesort730" />
				</option>
			</select>
		</div>
		<div class="col-md-2 col-sm-9 col-xs-6 hidden">
			<select class="form-control order-dateSort dateSort-Selected sortSelectSpan date-sort-my-orders" id="paymentType" name="paymentType" data-paymentType="${paymentType}">
				<option value="ALL" <c:if test="${paymentType eq 'ALL' or paymentType == ''}"><c:out value="selected='selected'" /></c:if>>
					<spring:theme code="order.paymentTypeALL" />
				</option>
				<option value="3" <c:if test="${paymentType ne 'ALL' and paymentType == 3}"><c:out value="selected='selected'" /></c:if>>
					<spring:theme code="order.creditCardPtype" />
				</option>
				<option value="2" <c:if test="${paymentType ne 'ALL' and paymentType == 2}"><c:out value="selected='selected'" /></c:if>>
					<spring:theme code="order.payAtBranchPtype" />
				</option>
				<option value="1" <c:if test="${paymentType ne 'ALL' and paymentType == 1}"><c:out value="selected='selected'" /></c:if>>
					<spring:theme code="order.poaPtype" />
				</option>
			</select>
		</div>
		<div class="col-md-2 col-xs-12 print-hidden">
			<button class="btn btn-primary orderHistoryButton">
				<spring:theme code="invoicelistingpage.search.go" />
			</button>
		</div>
		<input type="hidden" id="hidDateSort" value="${dateSort}">
	</div>
	</c:if>
</form:form>
         <div class="account-section-content my-order">
             <div class="account-orderhistory">
                 <div class="row hidden">
                   <div class="account-orderhistory-pagination paginationBtm invoiceTop-section invoiceTop-pagination order-pagination orders-top-pagination">
                <pag:invoiceListPagePagination top="true" msgKey="${messageKey}" showCurrentPageInfo="true" hideRefineButton="true"
                                supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                                numberPagesShown="${numberPagesShown}" accountPageId="${accountpageId}"/>
            </div>
             </div>
             <c:if test="${not empty searchPageData.results}">
            <div class="orderhistoryDetails">
                <div class="row">
				<div class="col-md-12 hidden-xs hidden-sm orderhistoryDetails-header">
					<div class="orderlisting-title-bar flex-center f-s-12 f-w-700 ${esOrderHeader}">
						<div class="col-md-3 text-uppercase">
							<a href="#" onclick="ACC.order.orderSortBy('byOrderNumber')" class="no-text-decoration text-white text-white-hover">
							<spring:theme code="text.account.orderHistory.orderNumber"/>
							<common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
							</a>
						</div>
						<div class="col-md-2 text-uppercase">
						<a href="#" onclick="ACC.order.orderSortBy('byInvoiceNumber')" class="no-text-decoration text-white text-white-hover">
							<spring:theme code="text.account.orderHistory.invoice"/>
							<common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
						</a>
						</div>
						 <div class="col-md-1 hidden text-uppercase">
							<spring:theme code="text.account.orderHistory.contact"/>
						</div>
						<div class="col-md-2 text-uppercase">
							<a href="#" onclick="ACC.order.orderSortBy('byDate')" class="no-text-decoration text-white text-white-hover">
							<spring:theme code="text.account.orderHistory.datePlaced"/>
							<common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
							</a>
						</div>
						<div class="col-md-1 padding-LeftZero text-uppercase">
						<a href="#" onclick="ACC.order.orderSortBy('byAmount')" class="no-text-decoration text-white text-white-hover">
							<spring:theme code="text.account.orderHistory.total"/>
							<common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
						</a>
						</div>
						<div class="col-md-1 text-uppercase padding-LeftZero">
							<spring:theme code="text.account.orderHistory.orderStatus" />
						</div>
						<div class="col-md-1 hidden text-uppercase padding-LeftZero">
							<spring:theme code="text.account.orderHistory.item"/>
						</div>
						<div class="col-md-2 text-uppercase">
							<spring:theme code="text.account.orderHistory.fulfilment" />
						</div>
						<div class="col-md-1 text-uppercase">
							<spring:theme code="text.account.orderHistory.branch"/>
						</div>
						
					</div>
				</div>
				<div class="col-md-12 hidden-xs hidden-sm orderhistoryDetails-orders">
					<div class="hidden-md orderhistoryDetails-bottomBorder"></div>
						<c:forEach items="${searchPageData.results}" var="order">	
						<div class="order-section">
						<c:set var="invoiceNumber" value="${fn:escapeXml(order.invoiceNumber)}"/>
						<c:set var="shipmentNumber" value="${fn:escapeXml(order.shipmentNumber)}"/>
						<c:set var="branchNumber" value="${fn:escapeXml(order.branchNumber)}"/>
						<c:set var="trackdeliveryURL" value="${(order.orderType == 'PICKUP')? 'isPickup':'notPickup'}"/>
						<c:set var="consignmentSize" value="${order.consignments.size()}" />
						<c:forEach items="${order.consignments}" var="consignment" varStatus="loop">
						<c:set var="consignmentUrl" value="${consignment.trackingUrl}" />
						</c:forEach>
						<div class="col-md-12 black-title flex-center orderlisting-singleOrder">
	                        <div class="col-md-3">
	                        	<div class="mobile-title-header">
	                               <div class="f-s-16 f-w-600 sm-center xs-center data-margin">
	                                <ycommerce:testId code="orderHistoryItem_orderDetails_link">
	                                	<a href="${orderDetailsUrl}${shipToAccount}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${branchNumber}&shipmentCount=${order.consignments.size()}" class="link-green responsive-table-link">
	                                    	${fn:escapeXml(order.code)}
	                                	</a>
	                                </ycommerce:testId>
	                            </div>
	                            <div class="cl"></div>
	                            <div class="f-s-14 f-w-400">${fn:escapeXml(order.purchaseOrderNumber)}</div>
	                            </div>
	                        </div>
	                         <div class="col-md-2 padding-LeftZero">
	                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6  f-s-14 f-w-400 data-data data-margin" title="${invoiceNumber}">
	                                ${invoiceNumber}
	                            </div>
	                        </div>
	                        
	                          <div class="col-md-1 hidden">
	                            <div class="f-s-14 f-w-400 data-data data-margin" title="${invoiceNumber}">
	                                Dianne McNaughton
	                            </div>
	                        </div>
	                        
	                         <div class="col-md-2">   
	                            <div class="f-s-14 f-w-400 data-data data-margin">
	                                <fmt:formatDate value="${order.placed}" dateStyle="long" timeStyle="short"/>
	                            </div>
	                        </div>
	                        <div class="col-md-1 p-l-0 total_subCol padding-rightZero">
	                            <div class="f-s-14 f-w-400 p-l-0 data-data data-margin padding-LeftZero total-order">
	                                <div>$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${fn:escapeXml(order.total.value)}" minFractionDigits="2"  maxFractionDigits="2" /></div>
	                            </div>
	                        </div>
	                        <div class="col-md-1 padding-LeftZero">
	                            <div class="f-s-14 f-w-400 statusDisplay data-data data-margin status_col">
	                                <spring:theme code="text.account.order.status.display.${fn:escapeXml(order.statusDisplay)}"/>
	                            </div>
	                        </div>
	                          <div class="col-md-1 hidden padding-LeftZero">
	                            <div class="f-s-14 f-w-400 no-padding-md data-data data-margin" title="${fn:escapeXml(order.purchaseOrderNumber)}">
	                                    50 items
	                            </div>
	                        </div>
							
	                        <div class="col-md-2">    
	                            <div class="f-s-14 f-w-400 data-data data-margin">
	                            	<c:if test="${trackdeliveryURL eq 'isPickup'}">
		                            <spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                            </c:if>
		                            <c:if test="${trackdeliveryURL eq 'notPickup'}">
		                            <c:choose>
		                            <c:when test="${consignmentSize gt 1 }">
		                             <span class="tracking-btn">
		                            	<a href="${orderDetailsUrl}${shipToAccount}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${branchNumber}&shipmentCount=${order.consignments.size()}" class="tms-pill">Multiple Shipments <span class="multi-shipment-badges text-align-center">${consignmentSize}</span></a>
		                             </span>
		                            </c:when>
		                            <c:when test="${order.orderType ne 'PICKUP' && consignmentUrl eq null}">
		                             <spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                            </c:when>
		                            <c:otherwise>
		                            <span class="tracking-btn">
		                            <order:trackShippingPill consignments="${order.consignments}"/>
		                            </span>
		                            </c:otherwise>
		                            </c:choose>
		                            </c:if>
	                            </div>
	                        </div>
	                        <div class="col-md-1">
	                            <div class="f-s-14 f-w-400 no-padding-md data-data data-margin" title="${fn:escapeXml(order.purchaseOrderNumber)}">
	                                    ${fn:escapeXml(order.branchNumber)}
	                            </div>
	                        </div>
	                       
	                        <div class="cl"></div>
	                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero collapse p-l-5-xs-imp" id="${ycommerce:encodeUrl(order.code)}_consignmentmobile">
	                            
	                            <div class="col-md-12 col-sm-12  col-sm-6 col-xs-6 statusDisplay data-data data-margin status_col">
	                                <span class="hidden-xs hidden-sm"><order:trackShippingPill consignments="${order.consignments}"/></span>
	                            </div>
	                        </div>
	                       </div>
	                       <div class="cl"></div>
	                      
	                       <div id="${ycommerce:encodeUrl(order.code)}" class="my-orders-product-details collapse col-md-12">
		                       
		                       <div class="cl"></div>
		                       <div class="orderhistoryDetails-header hidden-xs hidden-sm">
		                       <div class="title-bar">
			                       <div class="col-md-4"><spring:theme code="basket.page.itemInfo"/></div>
			                       <div class="col-md-2"><spring:theme code="basket.page.availability"/></div>
			                       <div class="col-md-2"><spring:theme code="basket.page.price"/></div>
			                       <div class="col-md-1"><spring:theme code="basket.page.quantity"/></div>
			                       <div class="col-md-1"><spring:theme code="basket.page.total"/></div>
			                       <div class="col-md-2"></div>
		                       </div>
		                       </div>
		                        <div class="cl"></div>
		                        <c:if test="${order.isPartOfMasterHybrisOrder eq true}">
					              	<div class="col-md-12 col-xs-12 no-padding-md">
					              	<div class="margin-xs-hrz--25">
						                  <div class="marginTop10 col-md-2 col-sm-12 col-md-offset-10 pad-md-lft-30">
						                  <div class="row">
						                  	<a href="${masterOrderDetailsUrl}${unitId}/${ycommerce:encodeUrl(order.hybrisOrderNumber)}" class="btn btn-default btn-block pull-right"><spring:theme code="view.entire.order.btn" /></a>
						                  </div>
						                  </div>
						                  </div>
					                  </div>
					               </c:if>
								 <div class="cl"></div>
								 <div class="row marginTop35">
								 <div class="col-md-6 col-xs-6"><span class="blueLink close-collapse"  data-toggle="collapse" data-target="#${ycommerce:encodeUrl(order.code)}"><span class="glyphicon glyphicon-triangle-top"></span> <spring:theme code="myorders.collapse"/></span></div>
								 <div class="col-md-6 col-xs-6 text-right">
								 <ycommerce:testId code="orderHistoryItem_orderDetails_link">
								 <a href="${orderDetailsUrl}${unitId}/${ycommerce:encodeUrl(order.code)}" class="more-link" style="display:none"><spring:theme code="myorders.more.items"/></a>
								 </ycommerce:testId>
								 </div>
								 </div>
								 <div class="cl"></div>
	                       </div>
	                       <div class="cl"></div>
	                       </div>
	                    </c:forEach>
				</div>
            	<!-- For Mobile -->
            	<div class="col-xs-12 hidden-lg hidden-md">
            		<c:forEach items="${searchPageData.results}" var="order">	
            		<c:set var="invoiceNumber" value="${fn:escapeXml(order.invoiceNumber)}"/>
					<c:set var="shipmentNumber" value="${fn:escapeXml(order.shipmentNumber)}"/>
					<c:set var="branchNumber" value="${fn:escapeXml(order.branchNumber)}"/>
					<c:set var="trackdeliveryURL" value="${(order.orderType == 'PICKUP')? 'isPickup':'notPickup'}"/>
					<c:set var="consignmentSize" value="${order.consignments.size()}" />
            		<c:forEach items="${order.consignments}" var="consignment" varStatus="loop">
					<c:set var="consignmentUrl" value="${consignment.trackingUrl}" />
					</c:forEach>
            		<div class="m-x-20 m-b-15 orderList-mob-container">
            		<div class="row white-bg">
            			<div class="border-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">ORDER NUMBER</div>
            			<div class="f-s-14 f-w-700 m-b-5 text-primary min-height-div">
	                      <a href="${orderDetailsUrl}${shipToAccount}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${branchNumber}&shipmentCount=${order.consignments.size()}" class="link-green responsive-table-link">
	                        ${fn:escapeXml(order.code)}
	                      </a>
            			</div>
            			</div>
            			<div class="b-b-grey b-r-grey b-t-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">PO NUMBER</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">${fn:escapeXml(not empty order.purchaseOrderNumber and order.purchaseOrderNumber ne '' ? order.purchaseOrderNumber : '-')}</div>
            			</div>
            		</div>
            		<div class="row white-bg">
            			<div class="b-b-grey b-r-grey b-l-grey col-xs-12">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">INVOICE NUMBER</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">${not empty invoiceNumber and invoiceNumber ne '' ? invoiceNumber : '-'}</div>
            			</div>
            			<div class="b-b-grey hidden b-r-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">CONTACT</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">Dianne McNaughton</div>
            			</div>
            		</div>
            		<div class="row white-bg">
            			<div class="b-b-grey b-r-grey b-l-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">DATE PLACED</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div"><fmt:formatDate value="${order.placed}" pattern="MM/dd/YYYY" /></div>
            			</div>
            			<div class="b-b-grey b-r-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">TOTAL</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${fn:escapeXml(order.total.value)}" minFractionDigits="2"  maxFractionDigits="2" /></div>
            			</div>
            		</div>
            		<div class="row white-bg">
            			<div class="b-b-grey b-r-grey b-l-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">STATUS</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div"><spring:theme code="text.account.order.status.display.${fn:escapeXml(order.statusDisplay)}"/></div>
            			</div>
            			<div class="b-b-grey b-r-grey hidden col-xs-4">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">ITEMS</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">50 items</div>
            			</div>
            			<div class="b-b-grey b-r-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">BRANCH</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">${fn:escapeXml(order.branchNumber)}</div>
            			</div>
            		</div>
            		<div class="row white-bg">
            			<div class="b-b-grey b-r-grey b-l-grey col-xs-6">
            			<div class="f-s-10 f-w-700 m-b-5 m-t-10 text-gray">FULFILLMENT</div>
            			<div class="f-s-14 f-w-400 m-b-5 black-title min-height-div">
            			<c:if test="${trackdeliveryURL eq 'isPickup'}">
		                    <spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                 </c:if>
		                 <c:if test="${trackdeliveryURL eq 'notPickup'}">
		                 <c:choose>
		                 <c:when test="${consignmentSize gt 1 }">
		                 <span class="tracking-btn">
		                   <a href="${orderDetailsUrl}${shipToAccount}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${branchNumber}&shipmentCount=${order.consignments.size()}" target="_blank" class="tms-pill">Multiple Shipments&nbsp;(${consignmentSize})</a>
		                 </span>
		                 </c:when>
		                 <c:when test="${order.orderType ne 'PICKUP' && consignmentUrl eq null}">
		                  <spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                  </c:when>
		                 <c:otherwise>
		                  <span class="tracking-btn">
		                  <order:trackShippingPill consignments="${order.consignments}"/>
		                 </span>
		                 </c:otherwise>
		                 </c:choose>
		                 </c:if>
            			</div>
            			</div>
            			<div class="b-b-grey b-r-grey btn-view col-xs-6">
            			<a href="${orderDetailsUrl}${shipToAccount}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${branchNumber}&shipmentCount=${order.consignments.size()}" class="btn btn-primary btn-small full-width m-y-5 f-s-14 f-w-700">
						View
						</a>
            			</div>
            		</div>
            		</div>
            		</c:forEach>
            	</div>
            	<!-- For Mobile -->
            	</div>
            </div>
            </c:if>
        </div>
        <div class="account-orderhistory-pagination invoiceBottom-section order-pagination">
           <div class="row">
            <pag:invoiceListPagePagination top="false" msgKey="${messageKey}" showCurrentPageInfo="true" hideRefineButton="true"
                            supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                            searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                            numberPagesShown="${numberPagesShown}" accountPageId="${accountpageId}"/>
            </div>
        </div>
    </div>

<c:if test="${empty searchPageData.results && noorderflag}">
           <div class="cl"></div>
             <br/><br/> 
            <div class="row">
                <div class="col-md-12">
                   <div class="alert alert-danger margin15">
                             <spring:theme code="text.account.invoicelist.noOrders.help"/><a href='/contactus'><spring:theme code="homePageCustomerEventsComponent.contact"/></a>
                    </div>
                </div>
            </div>
        </c:if> 
        <c:if test="${empty searchPageData.results && !noorderflag}">
            <div class="cl"></div>
            <div class="hidden-xs hidden-sm"><br/><br/></div>
            <div class="row">
                <div class="col-md-12">
                   <div class="alert alert-danger margin15">
                            <spring:theme code="text.account.invoicelist.noOrders"/>
                    </div>
                </div>
            </div>
        </c:if>            
        
