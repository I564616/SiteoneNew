<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${cmsPage.uid eq 'accountOverviewPage'}">
		<c:if test="${contPayBillOnline && acctPayBillOnline}">
			<div class="col-md-2 hidden-xs hidden-sm">
				<a href="<c:url value="/my-account/pay-account-online"/>" class="btn btn-primary btn-block" target="redirectToBT">
					<spring:theme code="text.account.payBillOnline.btn" />
				</a> 
			</div>
			<div class="col-xs-12 hidden-md hidden-lg"><br/>
				<a href="<c:url value="/my-account/pay-account-online"/>" class="btn btn-primary btn-block" target="redirectToBT">
					<spring:theme code="text.account.payBillOnline.btn" />
				</a> 
			</div>
		</c:if>
		<div class="cl"></div>
</c:if>

<c:if test="${cmsPage.uid eq 'invoicedetailspage'}">
	<c:if test="${contPayBillOnline && acctPayBillOnline}">
			<div class="col-md-2 pull-right payonline-btn hidden-xs hidden-sm">
				<a href="<c:url value="/my-account/pay-account-online"/>" class="btn btn-primary btn-block" target="redirectToBT">
					<spring:theme code="text.account.payBillOnline.btn" />
				</a> 
			</div>
			
			<div class="col-xs-12 pull-right payonline-btn hidden-md hidden-lg">
				
				<a href="<c:url value="/my-account/pay-account-online"/>" class="btn btn-primary btn-block" target="redirectToBT">
					<spring:theme code="text.account.payBillOnline.btn" />
				</a> 
			</div> 
	</c:if>
</c:if>