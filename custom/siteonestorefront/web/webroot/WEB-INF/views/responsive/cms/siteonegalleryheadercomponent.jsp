<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>

<div class="marginTop35"></div>
<c:url var="buttonUrl" value="${component.buttonURL}"/>
<c:choose>
	<c:when test="${isImageRightAligned}">

	<div class="row marginTopBVottom30 title-sec">
		<div class="col-md-10 col-sm-12 col-xs-12"><h1 class="headline"><spring:theme code="hardScape.headline"/></h1></div>
		<div class="col-md-2 hidden-xs hidden-sm"><img src="/_ui/responsive/theme-lambda/images/SiteOne_StoneCenter-Logo.png" class="img-responsive" alt="SiteOne Stone Center Logo"/></div>
		<div class="cl"></div>
	</div>
	
		<div class="green-background">
		<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="row">
		<img src="${media.url}" width="100%"  class="img-responsive" alt="StoneCenter Main Image"/>
		<div class="col-md-6 col-sm-12 col-xs-12 stoneCenter_banner2"> 
  			<div class="row">
			<h2 class="headline" style="font-size:30pt">${component.headline}</h2>
			<div class="row">
			<div class="col-md-9 col-xs-12 col-sm-12">
			<p>${component.content} </p>
			<c:choose>
				<c:when test="${not empty component.buttonLabel && component.buttonLabel ne ''}">
				<br/>
				<div><a href="${buttonUrl}" class="btn banner-btn">${component.buttonLabel}</a></div>
				</c:when>
			</c:choose>
			</div>
			</div>
			<div class="cl"></div>
			</div>
 		</div>
 		</div>
		</div>
		<div class="cl"></div>
		</div>
		
	</c:when>


	<c:otherwise>
	
	<c:choose>
	<c:when test="${cmsPage.uid eq 'plantsonmoveLander'}">
	<div class="pom-data">
	<h3 class="headline">${component.headline}</h3>
	<p>${component.content} </p>
	
	<div class="select-location-btn">
	<div class="dropdown whereToBuy  margin15">
  			<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
   			<span id="selected"> <spring:theme code="gallery.header.banner.selectlocation"/></span>&nbsp;<span class="caret"></span> 
  			</button>
  			<ul class="dropdown-menu hardscape_storeList">
  			<c:forEach var="states" items="${statesList}" varStatus="loopStatus">
    			<li><a id="stateData" data-index="${loopStatus.index}">${states}</a></li>
    		</c:forEach>
  			</ul>
			</div>
			</div>
	</div>
	</c:when>
	<c:otherwise>
	<div class="green-background">
		<input type="hidden" name="hardscapeSpecialty" value="Hardscape">
		<div class="col-md-5 col-sm-12 col-xs-12">
			<div class="row">
 			<img src="${media.url}" width="100%"  class="img-responsive" alt="StoneCenter Main Image."/></div>
		</div>
		<div class="col-md-7 col-sm-12 col-xs-12"> 
  			<div class="hardscpae_content">
			<h3 class="headline">${component.headline}</h3>
			<div class="row">
			<div class="col-md-12 col-xs-12 col-sm-12">
			<p>${component.content} </p>
			<div class="cl"></div>
			<br/>
			<c:choose>
			<c:when test="${not empty component.buttonLabel && component.buttonLabel ne ''}">
				<div class="col-md-3 askExpert-btn row"><a href="${buttonUrl}" class="btn banner-btn">${component.buttonLabel}</a></div>
			</c:when>
			</c:choose>
 			<c:if test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp')}"> 
 			<div class="dropdown whereToBuy col-md-4 margin15">
  			<button class="btn btn-storeList dropdown-toggle" type="button" data-toggle="dropdown">
   			<span id="selected"> <spring:theme code="gallery.header.banner.wheretobuy"/></span><span class="caret"></span> 
  			</button>
  			<ul class="dropdown-menu hardscape_storeList">
  			<c:forEach var="states" items="${statesList}" varStatus="loopStatus">
    			<li><a id="stateData" data-index="${loopStatus.index}">${states}</a></li>
    		</c:forEach>
  			</ul>
			</div>
			</c:if>
			</div>
			</div>
 
			</div>
		</div>
		<div class="cl"></div>
		</div>
	</c:otherwise>
	</c:choose>
	
	
	
	</c:otherwise>
</c:choose> 
<div class="cl"></div>
 
 

 
<c:if test="${not isImageRightAligned}">
<div class="green_border locationtitle" style="display:none;"><span class="headline2"><span class="state_title"></span> <spring:theme code="locations.text"/></span>
<span class="tier-headline pull-right">
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-green.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier1.legendtext"/></span></span>
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-blue.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier2.legendtext"/></span></span>
<span class="tier-name"><img class="pull-left" src="${themeResourcePath}/images/pinpoint-gray.png" alt=""></img><span class="tier-title pull-left"><spring:theme code="storeDetailsContent.hardscape.tier3.legendtext"/></span></span>
</span>
<div class="cl"></div>
</div>
<store:hardscapeStoreList/>
</c:if>



