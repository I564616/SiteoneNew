<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<spring:htmlEscape defaultHtmlEscape="true" />

<spring:url value="/my-account/approval-dashboard" var="orderApprovalDashboardUrl" htmlEscape="false" />
<%-- <common:headline url="${orderApprovalDashboardUrl}" labelKey="text.account.orderApprovalDetails.label" /> --%>
<div class="row print-hidden">
	<div class="col-md-12">
	<c:choose>
		<c:when test="${orderApprovalData.b2bOrderData.status eq 'REJECTED'}">
			<h1 class="headline"><spring:theme code="text.account.orderApprovalDetails.heading.rejected" /></h1>
		</c:when>
		<c:when test="${orderApprovalData.b2bOrderData.status eq 'PENDING_APPROVAL'}">
			<h1 class="headline">${fn:replace(user.firstName, '%20', ' ')},&nbsp;<spring:theme code="text.account.orderApprovalDetails.heading.pending_approval" /></h1>
		</c:when>
		<c:otherwise>
			<h1 class="headline"><spring:theme code="text.account.orderApprovalDetails.heading.approved" /></h1>
		</c:otherwise>
	</c:choose>
	</div>
</div>