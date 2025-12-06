<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<option class="allLists" value="" selected><spring:theme
									code="assemblyDetailsPage.list.name" /></option>
<c:forEach var="wishlists" items="${allLists}">
								<option value="${wishlists.code}" label="${wishlists.name}">${wishlists.name}</option>
</c:forEach>