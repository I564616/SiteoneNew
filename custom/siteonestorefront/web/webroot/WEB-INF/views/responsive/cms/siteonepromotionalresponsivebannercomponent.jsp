<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<c:forEach items="${medias}" var="media">
	<c:choose>
		<c:when test="${empty imagerData}">
			<c:set var="imagerData">"${media.width}":"${media.url}"</c:set>
		</c:when>
		<c:otherwise>
			<c:set var="imagerData">${imagerData},"${media.width}":"${media.url}"</c:set>
		</c:otherwise>
	</c:choose>
	<c:if test="${empty altText}">
		<c:set var="altText" value="${media.altText}"/>
	</c:if>
</c:forEach>

<c:url value="${urlLink}" var="encodedUrl" />

<div class="banner banner__component--responsive">
	<c:choose>
	
		<c:when test="${not empty buttonName && buttonName ne ''}">
			<c:if test="${not empty imagerData}">
				<img class="js-responsive-image"  data-media='{${imagerData}}' alt='${altText}' title='${altText}' style="">
			</c:if>
			<h1 class="headline">${promoText}</h1>
			<c:choose>
				<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
					<a href="#">${buttonName} &#8594;</a>
				</c:when>
				<c:otherwise>
					<a href="${encodedUrl}">${buttonName} &#8594;</a>
				</c:otherwise>
			</c:choose>
		</c:when>
		
		<c:otherwise>
			<c:choose>
				<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
					<c:if test="${not empty imagerData}">
						<img class="js-responsive-image"  data-media='{${imagerData}}' alt='${altText}' title='${altText}' style="">
					</c:if>
					${promoText}
				</c:when>
				<c:otherwise>
					<a href="${encodedUrl}">
						<c:if test="${not empty imagerData}">
							<img class="js-responsive-image"  data-media='{${imagerData}}' title='${altText}' alt='${altText}' style="">
						</c:if>
					</a>
					${promoText}
				</c:otherwise>
			</c:choose>
		</c:otherwise>
		
	</c:choose>
	
</div>