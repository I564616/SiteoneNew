<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<c:if test="${not empty component}">
<c:set var="title" value="${component.headline}"/>
<c:set var="content" value="${component.content}"/>

		<div class="row">
		<div class="col-xs-12">
			<c:if test="${not empty title}">				
					<div class="article-promo"><p class="store-specialty-heading"><h2>${title}</h2></p>	</div>			
			</c:if>
			<c:if test="${not empty content}">				
					<div class="article-promo"><p>${content}</p></div>				
			</c:if>
			</div>
		</div>
	<br aria-hidden="true">	

</c:if>

