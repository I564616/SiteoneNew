<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>

<c:forEach items="${breadcrumbs}" var="breadcrumb">
	<c:set var="searchTextFromBreadcrumb" value="${breadcrumb.name}"/>  
</c:forEach>



<template:page pageTitle="${pageTitle}">

	<c:url value="/" var="homePageUrl" />
	<c:url value="/search/" var="searchUrl" />
	<cms:pageSlot position="SideContent" var="feature" element="div" class="side-content-slot cms_disp-img_slot searchEmptyPageTop">
		<cms:component component="${feature}" element="div" class="no-space yComponentWrapper searchEmptyPageTop-component"/>
	</cms:pageSlot>
	
	<%-- <div class="breadcrumb-section">
		<ol class="breadcrumb">
		<li>
			Search
		</li>
		<li class="active">results for ${searchText[0]}</li>
		</ol>
	</div> --%>
	<div class="text-center">
	<div class="row">
	<div class="search-empty col-xs-12">
		<h1 class="headline text-center">
		<c:choose>
		   <c:when test="${offlineProductId ne null}">
		      <spring:theme code="search.no.results"/>"${offlineProductId}" 
		      	<input type="hidden" value="${offlineProductId}" class="emptySearchTerm"/>
		   </c:when> 
		   <c:otherwise>
			  <spring:theme code="search.no.results"/>"${searchPageData.freeTextSearch}"
			  	<input type="hidden" value="${searchPageData.freeTextSearch}" class="emptySearchTerm"/> 
		   </c:otherwise>
		</c:choose>
			${searchTextFromBreadcrumb.split("Results For")[1]}
			 
		</h1>
	</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12 col-md-12" >
			<cms:pageSlot position="MiddleContent" var="comp" element="div">
				<cms:component component="${comp}" element="div"/>
			</cms:pageSlot>
		</div>
		<div class="cl"></div>

		<div class="col-xs-12 col-md-6 col-md-offset-3 empty-search-container flex-center justify-center">
		<h5 class="bold black-title no-margin search-again-text"><spring:theme code="searchEmptyPage.search.again" /></h5>
			
			<div class="search-box-wrapper">
				 
				 <form method="get" action="${searchUrl}" id="searchBoxempty">		
						
						 
						 	<div class="pseudo-search-icon"><input type="text" class="form-control search-empty-input" name="text" value=""></div>
						 	<div class="search-erro-msg"><span class="errormessage hidden text-red"><spring:theme code="search.emptySearchBox.errorMessage" /></span></div>
						
						
					</form>
					 	
					
			  </div>
		</div>
		<div class="cl"></div>
		<div class="col-xs-12 col-md-12">
			<h5 class="margin40 bold">
				<spring:theme code="searchEmptyPage.text1" />
				<a class="show-xs show-sm no-text-decoration" href="<c:url value="/sdssearch/results"/>">
					<spring:theme code="searchEmptyPage.text2"/>.
				</a>
			</h5>
		</div>
		
		</div>
		
	</div>	
	
	
	
		<div class="cl"></div>
		 
		<div class="col-xs-12 col-md-6 col-md-offset-3 text-center let-us-help-section">
			<h5 class="store-specialty-heading font-Geogrotesque-bold margin20"><spring:theme code="searchEmptyPage.let.us.help" /></h5>
			<ul style="list-style-type:none;">
				<li class="bold"><spring:theme code="search.no.results.helpContactNumber"/></li>
				<li><a class="no-text-decoration bold" href="<c:url value="/contactus"/>"><spring:theme code="searchEmptyPage.contact.here" /></a></li>
			</ul>
		</div>
	<div class="cl"></div>
	<c:if test="${algonomyRecommendationEnabled}">
		<div class="cl"></div>
		<div class="mb-margin15">
		<div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotCategory">

		</div>
		</div>
	</c:if>
	<div class="cl"></div>
	
	<c:if test="${contentSearchPageData.results.size() ne 0}">
		<div class="row related-article-container">
			<div class="col-xs-12">
					<div class="row search-related-text-container">
						<div class="bold col-xs-12 search-related-articles-text">
							<spring:theme code="text.related.article.search.content"/>
						</div>
						<div class="col-xs-12 col-md-6 col-md-offset-3 text-center search-not-found-text" style="font-size: 18px;line-height: 26px;">
						<spring:theme code="searchEmptyPage.notFound.text" arguments="${offlineProductId ne null?offlineProductId:searchPageData.freeTextSearch}"/>
						</div>
					</div>
					<content:contentListerGrid/>
					<div class="col-xs-12 col-md-6 col-md-offset-3 text-center more-btn-container">
						<a href="/articles" class="btn no-margin btn-primary btn-block search-article-button"><spring:theme code="text.related.article.more.content"/></a>
		     		</div>
			</div>
		</div>
	</c:if>


	<cms:pageSlot position="BottomContent" var="comp" element="div" class="searchEmptyPageBottom">
		<cms:component component="${comp}" element="div" class="yComponentWrapper searchEmptyPageBottom-component"/>
	</cms:pageSlot>
	
</template:page>