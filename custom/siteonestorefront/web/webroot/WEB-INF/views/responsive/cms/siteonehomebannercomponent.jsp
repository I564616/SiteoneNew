<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<div class="col-xs-12 col-sm-12 col-md-12 promo-wrapper">
<div class="row">
<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="UrlLink" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="UrlLink" />
      </c:otherwise>
 </c:choose>	
<a href="${UrlLink}"> <img src="${media.url}"  alt="Promotion Spot" height="auto" width="100%" class="img-responsive"/></a>
		</div>
		</div>
	<div class="cl visible-sm visible-xs"><br/></div>
