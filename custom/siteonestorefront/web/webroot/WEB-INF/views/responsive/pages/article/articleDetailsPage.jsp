<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<template:page pageTitle="${pageTitle}">
<div class="col-md-7 row">
<h1 class="headline">${articleData.articleName}</h1>
<br/>
 
<p>${articleData.shortDesc}</p>
<p class="article-publish-detail"><b class="black-title">${articleData.publisherName}</b> <span><fmt:formatDate value ="${articleData.publishDate}" type="date" timeStyle="long" dateStyle="long"/></span></p>
<div class="article-icons-container col-md-12 col-xs-12">
<div class="row text-right">
<c:choose>
        <c:when test="${currentBaseStoreId eq 'siteone'}">
            <a href="https://www.facebook.com/SiteOneLandscape/" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Facebook.svg" alt="Facebook" title="Facebook" class="article-icon"></a>
            <a href="https://x.com/SiteOneSupply" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Twitter.svg"  alt="Twitter" title="Twitter" class="article-icon"></a>
            <a href="mailto:customersupport@siteone.com" class="glyphicon glyphicon-envelope article-icons email-icon article-email-icon" aria-hidden="true" title="Email Us" title="Email Us"></a>
      </c:when>
      <c:otherwise>
             <a href="https://www.facebook.com/siteonesupplycanada" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Facebook.svg" alt="Facebook" title="Facebook" class="article-icon"></a>
            <a href="https://x.com/i/flow/login?redirect_after_login=%2FSiteOneCanada" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Twitter.svg"  alt="Twitter" title="Twitter" class="article-icon"></a>
            <a href="mailto:customersupport@siteone.com" class="glyphicon glyphicon-envelope article-icons email-icon article-email-icon" aria-hidden="true" title="Email Us" title="Email Us"></a>
      </c:otherwise>
       </c:choose></div>
<div class="col-md-12 col-xs-12"> 
<div class="row">          
<img src="${articleData.articleMedia.url}" height="auto" width="100%"/>
</div>
</div>
<div class="cl"></div>
<br/>
<c:set var="count" value="0"/>
<c:forEach items="${articleData.articleContent}" var="content">

<c:choose>
<c:when test="${not empty content.image}">
<c:set var="count" value="${count+1}"/>
<c:if test="${count<=4}">
<c:if test="${content.alignment eq 'left'}">
<div class="col-md-6 row article-detail-content"><img src="${content.image.url}"  width="100%" height="auto;"/></div>
<div class="col-md-6 row article-detail-content">${content.content}</div>

</c:if>
<c:if test="${content.alignment eq 'right'}">
<div class="col-md-6 row article-detail-content">${content.content}</div>
<div class="col-md-6 row article-detail-content"><img src="${content.image.url}" width="100%;" height="auto;"/></div>

</c:if>
</c:if>
</c:when>
<c:when test="${not empty content.pullQuote}">
<div class="col-md-8 row article-detail-content"><br/>${content.content}</div><div class="col-md-4"><hr/> ${content.pullQuote}</div>
</c:when>
<c:otherwise>
<div class="col-md-12 row article-detail-content">
<c:choose>
<c:when test="${content.headlineType eq 'h3'}">
	<h3 class="headline">${content.headLine}</h3>
</c:when>
<c:otherwise>
	<c:if test="${not empty content.headLine}">
		<h2 class="headline">${content.headLine}</h2>
	</c:if>
</c:otherwise>
</c:choose>
${content.articleParagraph}</div>
</c:otherwise>
</c:choose>
<div class="cl"></div>
</c:forEach>

 

 
<h3 class="store-specialty-heading"><b><spring:theme code="articleDetailsPage.tags" />:</b></h3> <br/>
<div class="col-md-6 row">
<c:forEach items="${articleData.articleTags}" var="tags">
<a href="/search/?searchtype=product&text=${tags}" class="tag-sec">${tags}</a>
</c:forEach>
<br/><br/><br/><br/>
</div>
<div class="cl"></div>
 
</div>
<div class="cl"></div>
<hr/>
<div class="cl"></div>
	<div class="col-md-7 row">
		<div class="col-xs-12 col-md-12 row article-detail-footer">
			<div class="margin20">
				<c:if test="${not empty articleData.previousArticle}">
					<div class="col-md-3 col-xs-6 pull-left">
						<a href="<c:url value="/articles/article"/>/${articleData.previousArticle.articleCode}">&#8592;<spring:theme code="articleDetailsPage.previous" /><%-- <br /><br />${articleData.previousArticle.articleName} --%></a>
					</div>
				</c:if>
				<c:if test="${not empty articleData.nextArticle}">
					<div class="col-md-3 col-xs-6 pull-right text-right">
						<a href="<c:url value="/articles/article"/>/${articleData.nextArticle.articleCode}"><spring:theme code="articleDetailsPage.next" />&#8594;<%-- <br /><br />${articleData.nextArticle.articleName} --%></a>
					</div>
				</c:if>
				<div class="cl"></div>
			</div>
		</div>
	</div>
</template:page>
