<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="event" tagdir="/WEB-INF/tags/responsive/event" %>

<div class="product__list--wrapper">
<c:set value="${eventSearchPageData.currentQuery.url}" var="eventQuery"/>
  <c:set value='${eventQuery.replace("/search?", "/events?")}' var="eventQueryUrl"/>
  
   <div class="event-pagination pagination-hidden"><nav:eventPagination top="true" hideRefineButton="false" supportShowPaged="${event_isShowPageAllowed}" supportShowAll="${event_isShowAllAllowed}"  searchPageData="${eventSearchPageData}" searchUrl="${eventQueryUrl}"  numberPagesShown="${event_numberPagesShown}"/> </div>
    <div class="cl"></div>
    <div class="events-list">
    <ul class="product__listing product__grid">
        <c:forEach items="${eventSearchPageData.results}" var="event" varStatus="status">
            <event:eventListerGridItem event="${event}"/>
        </c:forEach>
    </ul>
    </div>
     <div class="event-pagination"><nav:eventPagination top="false" hideRefineButton="true" supportShowPaged="${event_isShowPageAllowed}" supportShowAll="${event_isShowAllAllowed}"  searchPageData="${eventSearchPageData}" searchUrl="${eventQueryUrl}"  numberPagesShown="${event_numberPagesShown}" eventFooter="true"/> </div>
</div>