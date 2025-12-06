<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ attribute name="consignments" required="false" type="java.util.List" %>
<c:if test="${not empty consignments and consignments.size() eq 1}">
	<c:forEach items="${consignments}" var="consignment" varStatus="loop">
		<c:if test="${consignment.trackingUrl ne null  }">
			<a href="${fn:escapeXml(consignment.trackingUrl)}" target="_blank" class="tms-pill">
				<spring:theme code="text.consignment.${ consignment.statusDisplay eq 'SHIPPED' ? 'trackshiping' : 'trackdelivery' }" />
			</a>
		</c:if>
	</c:forEach>
</c:if>