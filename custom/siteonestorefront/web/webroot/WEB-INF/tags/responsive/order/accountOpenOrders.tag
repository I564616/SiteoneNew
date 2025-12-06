<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ attribute name="accountpageId" required="false" type="String" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<c:set var="searchUrl" value="/my-account/orders/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>
<spring:url value="/my-account/masterOrder/" var="masterOrderDetailsUrl" htmlEscape="false"/>
<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>

    <div class="account-section-content open-order">
        <div class="account-orderhistory margin15">
            <div class="account-orderhistory-pagination top-section orders-top-pagination">
                <nav:pagination top="true" msgKey="filter" showCurrentPageInfo="true" hideRefineButton="true"
                                supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                                numberPagesShown="${numberPagesShown}" accountPageId="${accountpageId}"/>
            </div>
            <c:if test="${not empty searchPageData.results}">
        <div class="margin20 marginBottom40">
	            <div class="orderhistoryDetails row">
					<div class="col-xs-12 hidden-xs hidden-sm orderhistoryDetails-header">
						<div class="title-bar ${esOrderHeader}">
							<div class="col-xs-2">
							
								<spring:theme code="text.account.orderHistory.orderNumber"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.purchaseOrderNumber"/>
							</div>
							<div class="col-xs-3">
								<spring:theme code="text.account.orderHistory.orderStatus"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.deliverytype"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.datePlaced"/>
							</div>
							<div class="col-xs-1 text-right">
								<spring:theme code="text.account.orderHistory.total"/>
							</div>
						</div>
					</div>
					<div class="col-xs-12 orderhistoryDetails-orders">
						<div class="hidden-md orderhistoryDetails-bottomBorder"></div>
							<c:forEach items="${searchPageData.results}" var="order">
							<c:set var="invoiceNumber" value="${fn:escapeXml(order.invoiceNumber)}"/>
							<c:set var="shipmentNumber" value="${fn:escapeXml(order.shipmentNumber)}"/>	
							<div class="col-xs-12 orderhistoryDetails-singleOrder">
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.account.orderHistory.orderNumber"/></div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                                <ycommerce:testId code="orderHistoryItem_orderDetails_link">
		                                <span data-toggle="collapse" data-link="${unitId}/${shipmentNumber}" data-target="#${ycommerce:encodeUrl(order.code)}" class="product-row hidden-xs hidden-sm" aria-expanded="false">
	                                   		<span class="icon-Expand icon-plus-circle"></span>
	                                    	</span>
		                                <c:choose>
		                                <c:when test="${order.isHybrisOrder eq false}">
		                                    <a href="${orderDetailsUrl}${unitId}/${shipmentNumber}" class="link-green responsive-table-link">
		                                    	${fn:escapeXml(order.code)}
		                                    </a>
		                                 </c:when>
		                                 <c:otherwise>
		                                       <a href="${orderDetailsUrl}${unitId}/${shipmentNumber}" class="link-green responsive-table-link">
		                                    	<spring:theme code="openorderspage.pending" />
		                                    </a>
		                                 </c:otherwise>
		                                 </c:choose>
		                                </ycommerce:testId>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title">
		                            	<spring:theme code="text.account.orderHistory.purchaseOrderNumber"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data  data-margin" title="${fn:escapeXml(order.purchaseOrderNumber)}">
		                                    ${fn:escapeXml(order.purchaseOrderNumber)}
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-3 col-sm-12">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title padding-LeftZero">
		                            	<spring:theme code="text.account.orderHistory.orderStatus"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6 col-xs-6 statusDisplay data-data data-margin">
		                                <spring:theme code="text.account.order.status.display.${fn:escapeXml(order.statusDisplay)}"/>
		                                <span class="hidden-xs hidden-sm"><order:trackShippingPill consignments="${order.consignments}"/></span>
		                            </div>
		                            <div class="col-xs-12 show-pill-xs hidden-lg hidden-md">
                        				<order:trackShippingPill consignments="${order.consignments}"/>
                        			</div>
		                            <div class="hidden-lg hidden-md my-orders-mobile-tms">
		                       		<c:if test="${not empty order.consignments and order.consignments.size() gt 1}">
							<c:forEach items="${order.consignments}" var="consignments" varStatus="loop">
							<order:orderConsignmentsData consignments="${consignments}" orderNumber="${order.code}"/>
							</c:forEach>
		                      		</c:if>
		                      		</div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">    
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title">
		                            	<spring:theme code="text.account.orderHistory.deliverytype"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                                <c:choose>
		                            		<c:when test="${not empty order.orderType}">
		                            			<spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                            		</c:when>
		                            		<c:otherwise>
		                            			<spring:theme code="text.account.order.status.display.processing"/>
		                            		</c:otherwise>
		                            	</c:choose>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">   
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6 data-title">
		                            	<spring:theme code="text.account.orderHistory.datePlaced"/>
		                           	</div>
		                            <div class="col-md-12 col-sm-6 col-xs-6 data-data data-margin">
		                                <fmt:formatDate value="${order.placed}" dateStyle="long" timeStyle="short"/>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-1 col-sm-12 p-l-0">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6 data-title padding-LeftZero">
		                            	<spring:theme code="text.account.orderHistory.total"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6 col-xs-6 p-l-0 data-data data-margin text-right padding-rightZero total-order">
										<fmt:setLocale value="en_US" scope="session"/>
										<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
										$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
		                            </div>
		                        </div>
		                        <div class="cl"></div>
	                          <div data-toggle="collapse" data-link="${unitId}/${shipmentNumber}" data-target="#${ycommerce:encodeUrl(order.code)}" class="product-row hidden-md hidden-lg" aria-expanded="false">
	                                   		<span class="icon-Expand icon-plus-circle"></span> 
	                                   		<span id="showhide-text">
	                                   		<a class="showtext"><spring:theme code="show.order.details"/></a>
	                                   		<a class="hidetext hidden"><spring:theme code="hide.order.details"/></a>
	                                   		</span>

	                             </div>
		                        <div class="hidden-xs hidden-sm">
							       <c:if test="${not empty order.consignments and order.consignments.size() gt 1}">
										<c:forEach items="${order.consignments}" var="consignments" varStatus="loop">
										<order:orderConsignmentsData consignments="${consignments}" orderNumber="${order.code}"/>
										</c:forEach>
		                     		 </c:if>
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
		                    </c:forEach>
					</div>
	            </div>
		    </div>
            </c:if>
        </div>
        <div class="account-orderhistory-pagination bottom-section">
            <nav:pagination top="false" msgKey="filter" showCurrentPageInfo="true" hideRefineButton="true"
                            supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                            searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                            numberPagesShown="${numberPagesShown}" accountPageId="${accountpageId}"/>
        </div>
    </div>


<c:if test="${empty searchPageData.results}">
    <div class="row">
        <div class="col-md-6 col-md-push-3">
            <div class="account-section-content content-empty">
                <ycommerce:testId code="orderHistory_noOrders_label">
                    <spring:theme code="text.account.orderHistory.noOrders"/>
                </ycommerce:testId>
            </div>
        </div>
    </div>
</c:if>
