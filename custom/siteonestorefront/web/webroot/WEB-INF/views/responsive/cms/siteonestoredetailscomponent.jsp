<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<p></p>

<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.isPreferredStore eq false && empty cookie['csc']}">
		<c:choose>
			<c:when test="${fn:contains(header['User-Agent'],'SiteOneEcomApp') && not empty sessionPos}">
				<c:set var="contactNo" value="${sessionPos.address.phone}"/>		
			</c:when>
			<c:otherwise>
				<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>

<div class="col-md-5 store-details-wrapper">
	<div class="row">
		<c:if test="${not empty sessionPos}">
			<span class="bold-text"><spring:theme code="text.contactUs.storeHead"/></span><br/>
			<span class="line1">${sessionPos.address.line1}</span>&nbsp;
			<span class="line2">${sessionPos.address.line2}</span><BR>
			<span class="town">${sessionPos.address.town}</span>,
			<span class="region">${sessionPos.address.region.isocodeShort}</span>
			<span class="postalCode">${sessionPos.address.postalCode}</span><BR>
			<span class="phone"><a class="tel-phone" href="<c:url value="tel:"/>${contactNo}">${contactNo}</a></span>
		</c:if>
	</div>
</div> 
<div class="row"> 
<div class="cl"></div>
<br/>
<c:if test="${cmsPage.uid ne 'ccpa' && cmsPage.uid ne 'ccpaConfirmation'}">

<div class="col-md-5 store-view-branch"><a href="<c:url value="/store/"/>${sessionPos.storeId}"><spring:theme code="siteoneStoreDetailsComponent.viewBranchDetails" /> &#8594;</a></div>
 </c:if>
</div> 