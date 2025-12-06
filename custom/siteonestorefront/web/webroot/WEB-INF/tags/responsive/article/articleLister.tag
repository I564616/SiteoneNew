<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="article" required="true" type="com.siteone.contentsearch.ContentData"%>
<%@ attribute name="category" required="true" type="String"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="product-item col-xs-12 col-sm-4 article-box"><c:url value="/articles/${fn:toLowerCase(category)}/${article.uid}" var="articles"/>
		<div class="product-item-box">
		  <a class="article-link" href="${articles}" title="${article.previewTitle}">
			<div class="details">
				<c:choose>
					<c:when test="${not empty article.previewImage.url}">
						<img src="${article.previewImage.url}" alt="alt" title="${article.name}" />
						<div class="cl"></div>
					</c:when>
					<c:otherwise>
						<img src="https://dummyimage.com/600x400/000/fff"/>
						<div class="cl"></div>
					</c:otherwise>
				</c:choose>
				<div class="product-item-detail">
				<h2 class="store-specialty-heading article-heading two-line-text">${article.previewTitle}</h2>
				<div class="four-line-text">${article.description} <div class="cl"></div></div>
					<div class="col-md-8 col-xs-12 col-sm-12 article-learn-more-btn">
						<button class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12"><spring:theme code="articleLister.learnMore" /></button>
					</div>
			</div>
			</div>
			<div class="cl"></div>
		  </a>
		</div>
		<div class="cl"></div>
</div>