<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component"%>
  <span class="hidden-xs hidden-sm">
 	<a href="<c:url value="/c/${component.category.code}"/>" class="categories-list hidden-xs hidden-sm">
	<div class="home-category">
		<c:choose>
			<c:when test="${not empty component.category}">
			<input type="hidden" value="/c/${component.category.code}" class="categorybox"/>
			 <img title="${component.category.name}" alt="${component.media.altText}" src="${component.media.url}">
			 <p class="text-center emptyCartText black-title"><b>${component.category.name}</b></p> 
			</c:when>
			<c:otherwise>
	  <component:emptyComponent />  
			</c:otherwise>
		</c:choose>
		
   </div>
   </a>
   </span>
   <span class="hidden-md hidden-lg">
	<div class="home-category">
		<c:choose>
			<c:when test="${not empty component.category}">
				<a href = "<c:url value="/c/${component.category.code}"/>"><img title="${component.category.name}" alt="${component.media.altText}" src="${component.media.url}"></a>
			 <a class ="categories-list" href = "<c:url value="/c/${component.category.code}"/>"><p class="text-center emptyCartText black-title"><b>${component.category.name}</b></p></a>
			</c:when>
			<c:otherwise>
				<component:emptyComponent />
			</c:otherwise>
		</c:choose>
   </div>
  </span>
 
 
