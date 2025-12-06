<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<h3 class="headline curatedplp-heading">${component.title}</h3>
<div class="promotion-sect">
	<c:forEach var="store" items="${stores}" varStatus="loopStatus">
		<c:if test="${loopStatus.index % 3==0}">
			<div class="cl"></div>
		</c:if>

		<div class="col-md-4 col-xs-12 marginbottomp30" >
			<div class="store">			
				<p class="storeName">${store.name}</p>
				<p class="storeTitle">${store.title}</p>
				<span class="line1">${store.address.line1}</span>&nbsp;<span class="line2">${store.address.line2}</span><BR>
								<span class="town">${store.address.town}</span>,
								<span class="region">${store.address.region.isocodeShort}</span>&nbsp;<span class="postalCode">${store.address.postalCode}</span><BR>
								<c:if test="${not empty store.address.phone}">
									<p class="">${store.address.phone}</p>

								</c:if>
								<c:if test="${empty store.address.phone}">
									<spring:theme code="storeDetails.phone.unvailable"/>
								</c:if>
								<span class=" store-view-branch curatedplp-branch"><a href="<c:url value="/store/"/>${store.storeId}"><spring:theme code="siteoneStoreDetailsComponent.viewBranchDetails" /> &#8594;</a></span>
								<div class="marginTop10 homebranchselection-cplp">
								  <c:set var="urlButton" value=""/>
								<c:forEach var="buttonUrl" items="${buttonUrls}" varStatus="loopStatus">
								<c:set var="url" value="${fn:split(buttonUrl, '|')}" />
								<c:if test="${store.storeId eq url[0]}">
								  <c:set var="urlButton" value="${url[1]}"/>
								</c:if>
								</c:forEach>
								<c:choose>
								<c:when test="${urlButton ne ''}">
								<button class="cplp-btn" onclick="ACC.product.cplpredirectUrlPopup('<c:url value="/"/>${urlButton}',${store.storeId})">${component.buttonLabel}</button>
								</c:when>
								<c:otherwise>
								<button class="cplp-btn" onclick="">${component.buttonLabel}</button>
								</c:otherwise>
								</c:choose>
								
								</div>
			</div>
			<div class="cl"></div>
		</div>
	</c:forEach>
	<div class="cl"></div>
</div>
