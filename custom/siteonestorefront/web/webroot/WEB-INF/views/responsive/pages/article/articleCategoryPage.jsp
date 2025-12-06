<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="article" tagdir="/WEB-INF/tags/responsive/article" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<c:forEach items="${searchPageData.contentCategoryData}" var="articleCategory">
<c:if test="${articleCategory!=null}">

<div class="account-orderhistory-pagination purchased-pagination mb-pagination">
 <%-- <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" 
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/> --%>
                                            <div class="cl"></div>
</div> 
<div class="product__listing product__grids">
<c:forEach items="${searchPageData.results}" var="articles">
<article:articleLister article="${articles}" category="${articleCategory.category}"/>
</c:forEach>
</div>

<div class="account-orderhistory-pagination purchased-pagination mb-pagination">
<%-- <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" 
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/> --%>
                                            <div class="cl"></div> 
</div> 
<c:set var="featuredArticle" value="${articleCategory.featuredArticle}"/>
<div class="row">
<div class="col-md-8 col-xs-12">
<h2 class="headline2"><spring:theme code="text.article.featureHeading"/></h2>

<c:if test="${not empty featuredArticle.previewImage}">
<%--<c:set var="mime" value="${featuredArticle.previewImage.format}"/>
<c:set var="type" value ="${fn:split(mime, '/')[0]}" /> --%>

<a href="<c:url value="/articles/${fn:toLowerCase(articleCategory.category)}"/>/${featuredArticle.uid}"><img src="${featuredArticle.previewImage.url}" width="100%" height="100%" alt=""/></a>

<%--<c:choose>
<c:when test="${type eq 'image'}">
<a href="<c:url value="/articles/${fn:toLowerCase(articleCategory.category)}"/>/${featuredArticle.uid}"><img src="${featuredArticle.previewImage.url}" width="100%" height="100%"/ alt=""></a>
</c:when>
<c:otherwise>
<video width="500" height="200" controls>
   <source src="${featuredArticle.previewImage.url}" type="${featuredArticle.previewImage.format}">  
</video> 
</c:otherwise>
</c:choose> --%>
</c:if>

</div>


<div class="col-md-4 col-xs-12">
<br/><br/>
<h3 class="green-title">${featuredArticle.previewImage.altText}</h3>
<br/>
<p>${featuredArticle.description}</p>
<div class="margin40">
<a href="<c:url value="/articles/${fn:toLowerCase(articleCategory.category)}"/>/${featuredArticle.uid}" class="btn btn-primary"><spring:theme code="articleCategoryPage.learn" /></a>
</div>
</div>
</div>
</c:if>
</c:forEach>

