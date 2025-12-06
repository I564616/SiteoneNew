<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty component}">
<c:set var="content" value="${component.content}"/>
<c:set var="buttonUrl" value="${component.buttonUrl}${oktaSessionToken}"/>
		<div class="row">
			<c:if test="${not empty component.buttonLabel}">				
					${component.content}<div class="custombutton" style="width: 180px; margin-top: 15px;" onclick="window.open('${buttonUrl}')">${component.buttonLabel}</div>	
			</c:if>
		</div>
	<br aria-hidden="true">	

</c:if>
