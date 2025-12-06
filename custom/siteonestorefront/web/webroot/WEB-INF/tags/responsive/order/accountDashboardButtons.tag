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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="accountorders" value="/my-account/orders" />

								<div class="col-md-4 hidden">
									<form method="GET"
										action="<c:url value="/my-account/order"/>/${unit.uid}/${orderData.code}">
										<a href="<c:url value="/my-account/order"/>/${unit.uid}/${orderData.code}" class="btn btn-primary btn-block">
										<c:choose>
											<c:when
												test="${orderData.status eq 'SUBMITTED' or orderData.status eq 'PROCESSING' or orderData.status eq 'SCHEDULED_FOR_DELIVERY' or orderData.status eq 'CHECKED_VALID' or orderData.status eq 'CHECKED_INVALID'}">
												<input type="hidden" class="orderType" name="orderType"
													value="open-orders">
											
													 <spring:theme
														code="accountDashboardPage.details.tracking" />
												
											</c:when>
											<c:otherwise>
												 <spring:theme
														code="accountDashboardPage.details.pickup" />
											
											</c:otherwise>
										</c:choose>
										</a>
									</form>
									
								</div>
														
								
								<c:choose>
									<c:when
										test="${orderData.status eq 'SUBMITTED' or orderData.status eq 'PROCESSING' or orderData.status eq 'SCHEDULED_FOR_DELIVERY' or orderData.status eq 'READY_FOR_PICKUP' or orderData.status eq 'CHECKED_VALID' or orderData.status eq 'CHECKED_INVALID'}">
										<div class="text-right col-md-8  margin20">
											<a href="${accountorders}/${unit.uid}"
												 data-key="orderhistorypage" data-active="orderHistoryTab"><spring:theme
													code="accountDashboardPage.view.orders" /></a>
										</div>
									</c:when>
									<c:otherwise>
										<div class="text-right col-md-8  margin20">
											<a href="${accountorders}/${unit.uid}"
												 data-key="orderhistorypage" data-active="orderHistoryTab"><spring:theme
													code="accountDashboardPage.view.orders" /></a>
										</div>
									</c:otherwise>
								</c:choose>
						