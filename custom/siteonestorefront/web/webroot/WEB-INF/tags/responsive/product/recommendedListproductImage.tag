<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="productData" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="${product.url}" var="productUrl"/>
<c:set value="${productData.images}" var="imageData" />
<c:set value="${not empty productData.productBrandName ? productData.productBrandName : '' }" var="imageAltText" />
<c:set value="${not empty productData.name and productData.name ne '' ? productData.name : imageAltText }" var="imageTitle" />
<c:set value="${imageAltText ne '' ? imageAltText : imageTitle }" var="imageAltText" />
<span class="f-s-10 bg-lightGrey hidden">
productUrl:${productUrl};
imageData:${imageData};
imageAltText:${imageAltText};
imageTitle:${imageTitle};
</span>
<c:set var="entryFound" value="${false}" />
<c:choose>
	<c:when test="${not empty imageData}">
		<c:forEach items="${imageData}" var="image">
			<c:if test="${entryFound eq false}">
				<c:set var="entryFound" value="${true}" />
				<c:set value="${not empty image.altText and image.altText ne '' ? fn:escapeXml(image.altText) : fn:escapeXml(imageAltText) }" var="imageAltText" />
				<c:set value="${imageTitle ne '' ? fn:escapeXml(imageTitle) : fn:escapeXml(imageAltText) }" var="imageTitle" />
				<c:choose>
					<c:when test="${not empty image.url and image.url ne ''}">
						<img src="${image.url}" alt="${imageAltText}" title="${imageTitle}"/>
					</c:when>
					<c:otherwise>
						<theme:image code="img.missingProductImage.responsive.thumbnail" alt="${imageAltText}" title="${imageTitle}" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<theme:image code="img.missingProductImage.responsive.thumbnail" alt="${imageAltText}" title="${imageTitle}" />
	</c:otherwise>
</c:choose>