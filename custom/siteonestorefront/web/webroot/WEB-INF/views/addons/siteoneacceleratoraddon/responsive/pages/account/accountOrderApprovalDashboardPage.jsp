<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-account/orderApprovalDetails/" var="orderApprovalDetailsUrl"/>
<c:set var="searchUrl" value="/my-account/approval-dashboard?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}" />

<div class="account-section-header no-border">
	<spring:theme code="text.myaccount.orderApprovalDashboard" />
</div>

<c:if test="${empty searchPageData.results}">
	<div class="row">
		<div class="col-md-6 col-md-push-3">
			<div class="account-section-content content-empty">
				<ycommerce:testId code="orderHistory_noOrders_label">
					<spring:theme code="text.account.orderHistory.noOrders" />
				</ycommerce:testId>
			</div>
		</div>
	</div>
</c:if>

<c:if test="${not empty searchPageData.results}">
	<div class="account-section-content">
		<div class="account-orderhistory">
			
			<div class="marginBottom40">
				<div class="orderhistoryDetails row">
					<div class="col-xs-12 hidden-xs hidden-sm orderhistoryDetails-header">
						<div class="title-bar">
							<div class="col-md-2 no-padding-rgt-md">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.orderNumber"/>
							</div>
							<div class="col-md-1 no-padding-md">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.purchaseOrderNumber"/>
							</div>
							<div class="col-md-2">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.orderStatus"/>
							</div>
							<div class="col-md-2">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.fulfilment.method" />
							</div>
							<div class="col-md-2">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.datePlaced"/>
							</div>
							<div class="col-md-1 no-padding-md text-right">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.total"/>
							</div>
							<div class="col-md-2 text-center">
								<spring:theme code="text.myaccount.orderApprovalDashBoard.paymentType"/>
							</div>
							<div class="cl"></div>
						</div>
					</div>
					<div class="col-xs-12 orderhistoryDetails-orders">
						<div class="hidden-md orderhistoryDetails-bottomBorder hidden"></div>
						<c:forEach items="${searchPageData.results}" var="order">
							<div class="col-xs-12 orderhistoryDetails-singleOrder">
								<div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
		                        	<div class="mobile-title-header">
		                               <div class="col-md-12 col-sm-12 col-xs-12 col-sm-12 sm-center xs-center data-margin">
		                                <ycommerce:testId code="orderHistoryItem_orderDetails_link">
		                                   <a href="${orderApprovalDetailsUrl}${ycommerce:encodeUrl(order.workflowActionModelCode)}" class="link-green responsive-table-link">
		                                    	${fn:escapeXml(order.b2bOrderData.code)}
		                                    </a>
		                                </ycommerce:testId>
		                            </div>
		                            <div class="cl"></div>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-1 col-sm-12 padding-LeftZero singleOrder-padding">
		                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.purchaseOrderNumber"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 no-padding-md data-data data-margin" title="${fn:escapeXml(order.b2bOrderData.purchaseOrderNumber)}">
		                            	<ycommerce:testId code="orderApprovalDashboard_purchaseOrderNumber_label">
			                            	<c:choose>
												<c:when test="${empty order.b2bOrderData.purchaseOrderNumber}">
													&nbsp;
												</c:when>
												<c:otherwise>
													${fn:escapeXml(order.b2bOrderData.purchaseOrderNumber)}
												</c:otherwise>
											</c:choose>
										</ycommerce:testId>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero singleOrder-padding">
		                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.orderStatus"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin" title="${fn:escapeXml(order.b2bOrderData.statusDisplay)}">
		                            	<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
											<spring:theme code="text.account.order.status.display.${order.b2bOrderData.statusDisplay}" />
										</ycommerce:testId>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero singleOrder-padding">
		                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.fulfilment.method"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin" title="${fn:escapeXml(order.b2bOrderData.orderType)}">
		                            	<spring:theme code="text.account.order.type.display.${fn:escapeXml(order.b2bOrderData.orderType)}"/>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero singleOrder-padding">
		                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.datePlaced"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin" title="${fn:escapeXml(order.b2bOrderData.created)}">
		                            	<fmt:formatDate value="${order.b2bOrderData.created}" dateStyle="LONG" timeStyle="short" type="date" />
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-1 col-sm-12 padding-LeftZero singleOrder-padding no-padding-md">
		                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.total"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 no-padding-md data-data data-margin text-right" title="${fn:escapeXml(order.b2bOrderData.totalPrice.formattedValue)}">
		                            	${fn:escapeXml(order.b2bOrderData.totalPrice.formattedValue)}
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero singleOrder-padding no-padding-md">
		                            <div class="hidden-md hidden-lg col-xs-6 col-sm-6 data-title">
		                            	<spring:theme code="text.myaccount.orderApprovalDashBoard.paymentType"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin text-center" title="${fn:escapeXml(order.b2bOrderData.siteOnePaymentInfoData.paymentType)}">
		                            	<c:choose>
		                                <c:when test="${order.b2bOrderData.siteOnePaymentInfoData.paymentType eq 3}">
	                               			<div><spring:theme code="Credit Card / Ewallet"/></div>
		                              	</c:when>
		                              	<c:when test="${order.b2bOrderData.siteOnePOAPaymentInfoData.paymentType eq 1}">
		                               		<div><spring:theme code="Siteone Online Account"/></div>
		                              	</c:when>
		                              	<c:otherwise>
		                              		<div><spring:theme code="Pay At Branch"/></div>
		                              	</c:otherwise>
		                              	</c:choose>
		                            </div>
		                        </div>
	                        </div>
						</c:forEach>
					</div>
				</div>
			</div>

		</div>
	</div>
</c:if>