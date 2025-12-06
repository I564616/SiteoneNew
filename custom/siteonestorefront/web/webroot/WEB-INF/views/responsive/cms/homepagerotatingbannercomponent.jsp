<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<div class="col-md-9 col-xs-12">
<div class="mb-margin15">
					<div id="siteoneHomepageBanner" class="carousel slide" data-ride="carousel">
					   
					    <ol class="desktop carousel-indicators hidden-xs hidden-sm">
					     <c:forEach items="${rotatingBanner}" var="banner" varStatus="status">
					     <c:choose>
					       <c:when test="${status.count eq 1}">
					        <li data-target="#siteoneHomepageBanner" data-slide-to="${banner.icon}" class="active"></li>
					        </c:when>
					        <c:otherwise>
					           <li data-target="#siteoneHomepageBanner" data-slide-to="${banner.icon}"></li>
					        </c:otherwise>
					        </c:choose>
					         </c:forEach>
					    </ol>
					     <ol class="mobile carousel-indicators  hidden-md hidden-lg">
					     <c:forEach items="${rotatingBanner}" var="banner" varStatus="status">
					     <c:choose>
					       <c:when test="${status.count eq 1}">
					        <li data-target="#siteoneHomepageBanner" data-slide-to="${banner.icon}" class="active"></li>
					        </c:when>
					        <c:otherwise>
					           <li data-target="#siteoneHomepageBanner" data-slide-to="${banner.icon}"></li>
					        </c:otherwise>
					        </c:choose>
					         </c:forEach>
					    </ol>
					    
					   
					 
					
    <!-- Wrapper for slides -->
    <div class="carousel-inner">
    
      <c:forEach items="${rotatingBanner}" var="banner" varStatus="status">
       <c:choose>
      <c:when test="${not empty banner.urlForLink}">
      <c:url value="${banner.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
      <c:url value="${banner.urlLink}" var="encodedUrl" />
      </c:otherwise>
      </c:choose>
      <div class="item img-container <c:if test='${status.count eq 1}'>active</c:if>">
       
        <a tabindex="-1" href="${encodedUrl}"<c:if test="${banner.external}"> target="_blank"</c:if>><img src="${banner.media.url}" alt="${not empty banner.headline ? banner.headline : banner.media.altText}" title="${not empty banner.headline ? banner.headline : banner.media.altText}" style="width:100%;"  <c:if test='${status.count eq 1}'>fetchpriority="high"</c:if>/>
         <div class="col-md-6 carousel-caption">
							        <div class="row">
							        <c:if test="${banner.headline ne null}">
							            <h3 class="homepage-banner-heading">${banner.headline}</h3>
							          </c:if>
							          <c:if test="${banner.promotionalText ne null}">
							            <p> ${banner.promotionalText}</p>
							         </c:if>
							        
							         <c:if test="${(banner.buttonURL ne null) && (banner.buttonLabel ne null)}">
            <a href="${banner.buttonURL}"> <button class="btn btn-primary">${banner.buttonLabel}</button> </a>
            </c:if>
					 </div>
			</div>
		</a>
      </div>
	  </c:forEach>
    </div>
    </div>
</div>
</div>    
 