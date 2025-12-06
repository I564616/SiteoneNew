<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<div class="row marginTopBVottom30">
	<div class="col-md-10 col-sm-12 col-xs-12"><h1 class="headline"><spring:theme code="hardScape.headline"/></h1></div>
	<div class="col-md-2 hidden-xs hidden-sm"><img src="/_ui/responsive/theme-lambda/images/SiteOne_StoneCenter-Logo.png" alt="SiteOne Stone Center Logo" class="img-responsive"/></div>
<div class="cl"></div>
</div>
<input type="hidden" name="hardscapeSpecialty" value="Hardscape">
<div class="cl"></div>
<div class="green-background">
 
<div class="col-md-6 col-sm-12 col-xs-12">
<%-- <div class="row">
 <img src="${media.url}" width="100%"  class="img-responsive" alt="Hardscape Main Image."/></div>--%>
 <div class="row">
					<div  class="carousel slide" data-ride="carousel">

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
      <div class="item <c:if test='${status.count eq 1}'>active</c:if>">
		<c:choose>
		<c:when test="${not empty encodeUrl }">
        	<a tabindex="-1" href="${encodedUrl}"<c:if test="${banner.external}"> target="_blank"</c:if>><img src="${banner.media.url}" alt="${not empty banner.headline ? banner.headline : banner.media.altText}" title="${not empty banner.headline ? banner.headline : banner.media.altText}" style="width:100%;"/>
			</a>
		</c:when>
		<c:otherwise>
			<img src="${banner.media.url}" alt="${not empty banner.headline ? banner.headline : banner.media.altText}" title="${not empty banner.headline ? banner.headline : banner.media.altText}" style="width:100%;"/>
		</c:otherwise>
		</c:choose>
      </div>
	  </c:forEach>
    </div>
    </div>
</div>
</div>
<div class="col-md-6 col-sm-12 col-xs-12"> 
  <div class="hardscpae_content">
<h2 class="headline">${component.headline}</h2>
<div class="row">
<div class="col-md-12 col-xs-12 col-sm-12">
<p>${component.content} </p>
<div class="cl"></div>
 <div class="dropdown whereToBuy">
  <button class="btn btn-storeList dropdown-toggle" type="button" data-toggle="dropdown">
   <span id="selected"> <spring:theme code="gallery.header.banner.wheretobuy"/></span><span class="caret"></span> 
  </button>
  
  <ul class="dropdown-menu hardscape_storeList">
  <c:forEach var="states" items="${statesList}" varStatus="loopStatus">
    <li><a id="stateData" data-index="${loopStatus.index}">${states}</a></li>
    </c:forEach>
  </ul>
</div>
</div>
</div>
<div class="cl"></div>
</div>


</div>
 
<div class="cl"></div>
</div>
<div class="cl"></div>

<div class="green_border locationtitle" style="display:none;"><span class="headline2"><span class="state_title"></span> <spring:theme code="locations.text"/></span>
<span class="tier-headline pull-right">
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-green.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier1.legendtext"/></span></span>
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-blue.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier2.legendtext"/></span></span>
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-gray.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier3.legendtext"/></span></span>
</span>
<div class="cl"></div>
</div>

<store:hardscapeStoreList/>