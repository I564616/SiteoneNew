<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<c:if test="${not empty validationData}">
	<c:set var="productLinkValidationTextDecoration" value="style=\"text-decoration: underline\""/>
	<c:forEach items="${validationData}" var="modification">
				<c:url value="${modification.entry.product.url}" var="entryUrl"/>
	</c:forEach>
	<div class="gc-error-msg marginTop20 js-gc-problem-error flex-center row">	
	<div class="col-xs-12">
		<div class="flex-center gc-cart-global-error-msg">
			<span><common:exclamation-triangle iconColor="#bc0000"/></span>
			<span class="pad-lft-10"><spring:theme code="basket.error.quantity.invalid"></spring:theme></span>
		</div>
	</div>
	</div>
</c:if>


