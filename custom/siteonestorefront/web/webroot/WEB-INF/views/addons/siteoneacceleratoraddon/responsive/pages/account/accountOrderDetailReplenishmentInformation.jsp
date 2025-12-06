<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>

<c:if test="${not empty orderData.triggerData}">
	<order:replenishmentScheduleInformation order="${orderData}"/>
</c:if>
