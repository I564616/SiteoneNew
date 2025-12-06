<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%-- <c:if test="${component.promotionalBanner.code eq categoryCode }"> --%>
<%-- <c:set var="categoryPromotion" value="${component.media}"/> --%>
<c:set var="categoryPromotionBanner" value="${component.media}"/>
<c:set var="categoryPromotionText" value="${component.promotionalText}"/>
<c:set var="mime" value="${categoryPromotionBanner.mime }"/>
<c:set var="type" value ="${fn:split(mime, '/')[0]}" />
<%-- <c:if test="${categoryPromotion!=null}"> --%>
<c:if test="${categoryPromotionBanner!=null}">
<c:choose>
<c:when test="${categoryPromotionBanner !=null}">
<c:choose>
<c:when test="${type eq 'image'}">
<a href="${component.urlLink}"><img src="${categoryPromotionBanner.url}" height="200" width="500"/></a>
</c:when>
<c:otherwise>
 <video width="500" height="200" controls>
  <source src="${categoryPromotionBanner.url }" type="${mime}">
</video>
</c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
<h6>${categoryPromotionText}</h6>
</c:otherwise>

</c:choose>
<br/>
<h4><b>${categoryPromotionText}</b></h4>
<h6><a href="#"><spring:theme code="siteonePromotionalCategoryComponent.learnMore" /></a></h6>

</c:if>
