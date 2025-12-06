<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>




	
	<div class="carousel__component">
			<div class="carousel__component--headline">${title}</div>

			
			<div class="carousel__component--carousel js-owl-carousel js-owl-default">						
						
<c:forEach items="${component.eventList}" var="event">

<div class="carousel__item">
			<img title="${event.image.altText}" alt="${event.image.altText}"
				src="${event.image.url}" width="200" height="200"><br>
				${event.name}<br>
				${event.date}<br>
				${event.time}<br>
                ${event.venue},${event.location}<br>
                ${event.shortDescription}<br>
                <a href="<c:url value="/events/"/>${event.code}" class="btn btn-primary">
               <spring:theme code="event.text.viewEventDetails" />
 </a>
</div>
<%-- <div class="carousel__item--name">${event.name}<br></div> --%>
</c:forEach>

</div>
</div>

