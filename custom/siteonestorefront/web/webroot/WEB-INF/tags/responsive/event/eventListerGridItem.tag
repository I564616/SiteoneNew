<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="event" required="true" type="com.siteone.facade.EventData"%>
<%@ taglib prefix="event" tagdir="/WEB-INF/tags/responsive/event" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
	<div class="col-md-4 col-sm-6 col-xs-12 product-item">
	 <div class="product-item-box">
			<div class="details">
							<div><a class="green-title bold-text" href="<c:url value="/events"/>/${event.code}">${event.name}</a></div>
							<div class="event-type"><br/>${event.type}</div>
							<div class="event-location"><br>${event.eventStartDate}	
							<br>${event.time}
							<br>${event.location}
							</div>	
							<div class="cl"></div><br/>
							<div class="four-line-text">${event.shortDescription}</div>
										
			</div>
			<div class="view-event-btn">
				<a href="<c:url value="/events"/>/${event.code}" class="btn btn-primary btn-block">
            		<spring:theme code="event.text.viewEventDetails" />
            	</a>
			</div>	
				
			<div class="cl"></div>
		</div>	
	</div>
