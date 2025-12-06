<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>

<%@ attribute name="openingSchedule" required="true" type="de.hybris.platform.commercefacades.storelocator.data.OpeningScheduleData" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>




<c:if test="${not empty openingSchedule}">
<c:if test="${not empty openingSchedule.specialDayOpeningList}">
<div class="row specialEventHours">
        <div class="col-md-12 specialHourTitle font-Geogrotesque f-s-22 m-b-5">
           <spring:theme code="store.special.event.hours" />
        </div>
        <div class="col-md-12 row f-s-16 font-Arial specialDayList">
            <c:forEach items="${openingSchedule.specialDayOpeningList}" var="specialDay">
                <div class="col-sm-4 col-xs-5">
                ${specialDay.formattedDate}
                </div>
                <div class="col-sm-8 col-xs-7">
                    <c:choose>
                        <c:when test="${specialDay.closed}" >
                            <spring:theme code="storeDetails.table.opening.closed" />
                     </c:when>
                        <c:otherwise>
                            ${specialDay.openingTime.formattedHour} - ${specialDay.closingTime.formattedHour}
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
</div>
</c:if>
</c:if>

    
