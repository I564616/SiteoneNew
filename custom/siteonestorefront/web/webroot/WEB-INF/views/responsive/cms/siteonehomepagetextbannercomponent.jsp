<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:url value="${buttonURL}" var="encodedUrl" />

<div onclick="location.href='${encodedUrl}';" class="pointer outer-link">
<h2>${title}</h2>
<br>
<p>${description}</p>
<br>

<c:choose>
	<c:when test="${not empty buttonLabel}">
		<a href="${encodedUrl}" class="btn btn-primary">${buttonLabel}</a>
	</c:when>
	<c:otherwise>
		<a href="${encodedUrl}" class="arrow-mark">
											<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
												<defs><style>.a{fill:#78a22f;}</style></defs><path class="a" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
											</svg>
		</a>
	</c:otherwise>
</c:choose>
</div>