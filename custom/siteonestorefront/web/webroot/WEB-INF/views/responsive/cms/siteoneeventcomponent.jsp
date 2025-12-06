<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>



<div style="background:#fff;box-shadow:0px 2px 7px 3px #e0e0e0;padding:0px 10px 20px 10px;margin:0px;">
 <div class="col-sm-7" style="margin:0px;">
			<img title="${component.event.image.altText}" alt="${component.event.image.altText}"
				src="${component.event.image.url}" width="100%">
				
</div>
				
 <div class="col-sm-5 margin20">
<div class="store-specialty-heading">${component.event.name}</div></br>
${component.event.type.name}<br>
<fmt:formatDate type = "date"  pattern = "EEEE, MMMM dd"  value = "${component.event.date}"/><br>
${component.event.time}<br>
${component.event.venue},${component.event.location}<br>
${component.event.shortDescription}<br>
<div class="margin20">
 <a href="<c:url value="/events/"/>${component.event.code}" class="btn btn-primary">
               <spring:theme code="event.text.viewEventDetails" />
 </a>
 </div>
</div>
<div class="cl"></div>	
</div>
