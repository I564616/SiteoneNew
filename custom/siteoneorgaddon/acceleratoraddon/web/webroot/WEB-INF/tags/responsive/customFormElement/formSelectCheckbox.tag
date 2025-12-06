<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/fSelect.css"/>
<script type="text/javascript" src="${commonResourcePath}/js/fSelect.js"></script>

<script>
(function($) {
    $(function() {
        $('.test').fSelect();
    });
})(jQuery);
</script>

<select class="test" multiple="multiple">
<c:choose>
	<c:when test="${not empty b2BCustomerForm.assignedShipTo}">
		<c:forEach items="${shipTos}" var="shipTo">
			<c:choose>
				<c:when test="${fn:contains(b2BCustomerForm.assignedShipTo, shipTo.code)}">
					<option value='${shipTo.code}' selected>${shipTo.name}</option>
				</c:when>
				<c:otherwise>
					<option value='${shipTo.code}'>${shipTo.name}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	
	<c:otherwise>
		<c:forEach items="${shipTos}" var="shipTo">
			<option value='${shipTo.code}'>${shipTo.name}</option>
		</c:forEach>
	</c:otherwise>
</c:choose>

</select>
  						
  						 