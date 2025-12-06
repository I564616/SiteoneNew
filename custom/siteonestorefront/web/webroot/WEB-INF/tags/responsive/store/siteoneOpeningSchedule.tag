<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>

<%@ attribute name="openingSchedule" required="true" type="de.hybris.platform.commercefacades.storelocator.data.OpeningScheduleData" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>


<c:if test="${not empty openingSchedule}">
		<c:if test="${not empty openingSchedule.specialDayOpeningList}">
			<div class="col-md-12 font-Geogrotesque f-s-22 m-b-5 text-default bold"><spring:theme code="store.regular.hours" /></div>
		</c:if>
		<c:forEach items="${openingSchedule.weekDayOpeningList}" var="weekDay" varStatus="weekDayNumber">
				<c:choose>
					<c:when test="${weekDay.closed}" >
					<span class="col-sm-4 col-xs-5"><c:if test="${weekDay.weekDay eq 'Monday'}">
										 <spring:theme code="header.monday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Tuesday'}">
										 <spring:theme code="header.tuesday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Wednesday'}">
										 <spring:theme code="header.wednesday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Thursday'}">
										 <spring:theme code="header.thursday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Friday'}">
										 <spring:theme code="header.friday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Saturday'}">
										 <spring:theme code="header.saturday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Sunday'}">
										 <spring:theme code="header.sunday"/> 
										</c:if></span><span class="col-sm-8 col-xs-7"><spring:theme code="storeDetails.table.opening.closed" /></span><br><c:if test="${!weekDayNumber.last}"></c:if>
					</c:when>
					<c:otherwise>
						<span class="col-sm-4 col-xs-5"><c:if test="${weekDay.weekDay eq 'Monday'}">
										 <spring:theme code="header.monday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Tuesday'}">
										 <spring:theme code="header.tuesday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Wednesday'}">
										 <spring:theme code="header.wednesday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Thursday'}">
										 <spring:theme code="header.thursday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Friday'}">
										 <spring:theme code="header.friday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Saturday'}">
										 <spring:theme code="header.saturday"/> 
										</c:if>
										<c:if test="${weekDay.weekDay eq 'Sunday'}">
										 <spring:theme code="header.sunday"/> 
										</c:if></span><span class="col-sm-8 col-xs-7">${weekDay.openingTime.formattedHour}- ${weekDay.closingTime.formattedHour} <br>
						<c:if test="${!weekDayNumber.last}"></c:if>
						</span>
					</c:otherwise>
				</c:choose>
		</c:forEach>
		
</c:if>
