<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<hr>
<div class="row margin20">
<c:forEach items="${component.eventList}" var="event">
	 <div class="col-sm-4">
		<div class="store-specialty-heading">${event.name}</div></br>
		<p>${event.type.name}<br>  
		${event.eventStartDate}<br>
		${event.time}<br>
		${event.venue},${event.location}<br>
		${event.shortDescription}<br>
	</p>
	<a href="<c:url value="/events/"/>${event.code}" class="btn btn-primary margin20">
                 <spring:theme code="event.text.viewEventDetails" />
                 
      </a>           
	</div>
	
</c:forEach>				
	
</div>
<hr>