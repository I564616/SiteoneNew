<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
 
<div class="promotion-filter">
<div class="container-lg">
<div class="col-md-12 col-xs-12" ><h1 class="headline"><spring:theme code="newsComponent.news&events" /></h1></div>
<c:if test="${not empty component}">
<div class="col-md-12 col-xs-12 news-border">
	<c:forEach items="${newsDataList}" var="news">
	<div>
		<a href="<c:url value="/news/"/>${news.newsCode}"><u>${news.title}</u></a>
		<br>
		<c:if test="${not empty news.shortDesc}">
				<p class="black-title">${news.shortDesc}</p>
		</c:if>
		<c:if test="${not empty news.newsPublishDate}">
			<fmt:formatDate pattern="M/d/yyyy" value="${news.newsPublishDate}"  />
		</c:if>
		</div>
	</c:forEach>
	</div>
	</c:if>
	<p class="cl"></p>	 
</div>
</div>
