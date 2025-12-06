<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "mobileFlag" value = "false"/>
<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
<!-- If Mobile -->
<c:set var = "mobileFlag" value = "true"/>
</c:if>
<input type="hidden" id="appStoreUrlId" name="appStoreUrlId" value="${component.getITunesUrl()}">
<input type="hidden" id="playStoreUrlId" name="playStoreUrlId" value="${component.playStoreUrl}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:if test="${component.visible}">
	<div class="container-fluid container-lg footer-wrapper ${not product.multidimensional?'pdpredesignfooter':''}">
	    <div class="footer__top">
            <div class="footer__left col-xs-12 col-sm-12 col-md-12">
                <div class="footer-margin">
                	<c:set var="largeCount" value="0" />
                	<c:forEach items="${component.navigationNode.children}" var="childLevel1">
	                	<c:set var="largeCount" value="${largeCount+1}" />
	                	<c:forEach items="${childLevel1.children}" step="${component.wrapAfter}" varStatus="i">
						   <div class="footer__nav--container col-xs-12 col-sm-12 col-md-3">
						    <div class="footer-inner">
	                	       <c:if test="${component.wrapAfter > i.index}">
	                	       		<div class="js-footer-dropdown">
	                	       			<div class="title col-md-12 col-xs-12">${childLevel1.title}</div>
                                   		<span class="glyphicon glyphicon-triangle-right hidden-md hidden-lg"></span>
	                	       		</div>
                               </c:if>
                               <ul class="footer__nav--links col-md-12 col-xs-12 dropdown-mobile">
                                   <c:forEach items="${childLevel1.children}" var="childLevel2" begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
                                        <c:forEach items="${childLevel2.entries}" var="childlink" >
	                                        <cms:component component="${childlink.item}" evaluateRestriction="true" element="li" class="footer__link"/>
                                        </c:forEach>
                                   </c:forEach>
                               </ul>
                               <div class="cl"></div>
                		    </div>
                		   </div>
                		 
						</c:forEach>
						<div  class="visible-md visible-lg hidden-sm hidden-xs">
					     <c:if test="${largeCount eq '4' }">
                		  		<div class="cl"></div>
								<c:set var="largeCount" value="0" />
							</c:if>
						</div>
                	</c:forEach>
               </div>
           </div>
           
            <div class="footer__left col-xs-12 col-sm-12 col-md-12 footer--lower">
            <div class="footer-margin">
            	<div class="footer__nav--container col-md-3 col-xs-12 col-sm-12 ${currentBaseStoreId eq 'siteoneCA' ? 'hidden' : ''}">
	            	<div class="footer-inner">
	            		<div class="title col-xs-12 footer-title"><spring:theme code="footerNavigationComponent.download" /></div>
						<div class="footer__nav--links col-md-12 row col-xs-12 app-icons-div">
							<div class="row">
								<div  class="col-xs-6 col-sm-6 col-md-12">
									<a target="_blank" href="${component.getITunesUrl()}" id="appStore" class="icon-apple" type="submit" title="App Store"><common:footerIcon iconName="appleIcon" width="${!mobileFlag ? '284.17':'160'}" height="${!mobileFlag ? '83':'50'}" viewBox="0 0 273 74"/></a>
								</div>	
								<div  class="col-xs-6 col-sm-6 col-md-12">
									<a target="_blank" href="${component.playStoreUrl}" id="playStore" class="icon-android" type="submit" title="Android Market"><common:footerIcon iconName="androidIcon" width="${!mobileFlag ? '284.17':'160'}" height="${!mobileFlag ? '83':'50'}" viewBox="0 0 273 74"/></a> 
								</div>
							</div>
						</div>
	            	</div>
            	</div>
            	<div class="footer__nav--container col-md-3 col-xs-12 col-sm-12">
            		<div class="footer-inner">
            			<div class="title col-xs-12 col-md-12 footer-title2"><label for="query" style='color:#ffffff;margin-bottom:0px'><spring:theme code="footerNavigationComponent.findABranch" /></label></div>
					    <span class="footer__nav--links col-xs-12 col-md-12 link-margin">
					    
					     <form id="footerBranchSearch" action="<c:url value="/store-finder/footer"/>" method="GET">
							<input type="text" id="query" aria-label="findBranch" name="query" class="form-control  footer-store-value" placeholder="<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'footerNavigationComponent.findABranch.placeholder' : 'footerNavigationComponent.findABranch.placeholder.ca' }"/>"/>
							<input type="hidden" id="miles" name="miles" value="100"/><BR>
							<button type="submit" class="btn btn-primary btn-block footer-title js-footer-search-store"><spring:theme code="footerNavigationComponent.findABranch" /></button>
							<a class="link-directory" href="<c:url value="/store-directory"/>"><spring:theme code="footerNavigationComponent.siteoneBranchDirectory" /></a>
						 </form>
						 </span> 
            		</div>
	            </div>
	            
            <%-- 	<div class="footer__nav--container col-md-3 col-xs-12 col-sm-12">
					<div class="footer-inner">
            			<div class="title col-xs-6 col-md-12 footer-title"><label for="emailFooter" style='color:#ffffff;margin-bottom:0px'><spring:theme code="footerNavigationComponent.signUp" /></label></div>
	            		<span class="footer__nav--links col-xs-12 col-md-12 link-margin">
	            			<p><spring:theme code="text.headline.footer.newletter"/></p>
						<form:form action="${homelink}emailsubscription" method="get" id="emailSignUp">
							<input type="text" id="emailFooter"  aria-label="emaiId" class="form-control" name="email" style="color:black" placeholder="<spring:theme code="footerNavigationComponent.signUp.placeHolder"/>" autocomplete="off"/><BR>
							<input  type="button" id="signUpFooter" class="btn btn-primary btn-block marginTop-btn marginBottom"  value="<spring:theme code="footerNavigationComponent.signUp.button"/>">
							
							<div class="message"></div>
							
							<%-- <span class="input-group-btn">
								<button id="signUp" class="btn btn-primary" type="button" data-search-empty="<spring:theme code="storelocator.error.no.results.subtitle" text="Check that you entered a valid email id."/>">
									<span>Sign Up</span>
								</button>
							</span> 
						 </form:form>
						 </span> 
            		</div>
            		
            	</div>--%>
            	<div class="footer__nav--container col-md-3 col-xs-12 col-sm-12">
            		<div class="footer-inner">
            			<div class="title col-xs-12 col-md-12 footer-title"><spring:theme code="footerNavigationComponent.connect" /></div>
			           	<c:choose>
			           		<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
					           	<span class="footer__nav--links col-xs-12 col-md-12 link-margin icons-social">
						            <a target="_blank" href="https://www.facebook.com/profile.php?id=61573840903687" title="Facebook"><common:footerIcon iconName="facebookIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://x.com/SiteOneCanada" title="Twitter"><common:footerIcon iconName="twitterIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.youtube.com/@SiteOneLandscapeSupply" title="You Tube"><common:footerIcon iconName="youtubeIcon" width="38.67" height="27" viewBox="0 0 106 74" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.linkedin.com/company/siteone-landscape-supply-canada/" title="LinkedIn"><common:footerIcon iconName="linkedinIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.instagram.com/Siteonesupplycanada/" title="Instagram"><common:footerIcon iconName="instagramIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
					            </span>
				          	</c:when>
							<c:otherwise>
								<span class="footer__nav--links col-xs-12 col-md-12 link-margin icons-social">
						            <a target="_blank" href="https://www.facebook.com/SiteOneLandscape/" title="Facebook"><common:footerIcon iconName="facebookIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://x.com/SiteOneSupply" title="Twitter"><common:footerIcon iconName="twitterIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.youtube.com/channel/UC3D13yPhsL5XJHXgnJFDAmA" title="You Tube"><common:footerIcon iconName="youtubeIcon" width="38.67" height="27" viewBox="0 0 106 74" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.linkedin.com/company/10366520/" title="LinkedIn"><common:footerIcon iconName="linkedinIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
						            <a target="_blank" href="https://www.instagram.com/siteonesupply/" title="Instagram"><common:footerIcon iconName="instagramIcon" width="27" height="27" viewBox="0 0 89 89" display="m-r-10 m-r-10-xs" /></a>
					            </span>
			            	</c:otherwise>
						</c:choose>
            		</div>
            	</div>
           
         		</div>
	        </div>
	        <div class="cl"></div>
	        <div class="footer__bottom">
			    <div class="footer__copyright text-capitalize">
			         &#169; ${fn:replace(component.extendedNotice,'<CURRENT_YEAR>','<span id="current_year"></span>')}
			         <div class="cl"></div><br/>
			          <a href="<c:url value="/termsandconditions"/>"><spring:theme code="footerNavigationComponent.terms&condition" /></a> &nbsp; | &nbsp; <a href="<c:url value="/privacypolicy"/>"> <spring:theme code="footerNavigationComponent.privacyPolicy" /></a> &nbsp; | &nbsp; <a href="<c:url value="/privacyrequest"/>"> <spring:theme code="footerNavigationComponent.privacyRequest" /></a>
				    <div class="cl"></div><br/>
			    </div>
			</div>
	       </div>
	
	</div>
</c:if>

<div class="hide app-suggestion-btn-container">
	<div>
	 	<h3 class='header-signinoverlay'><spring:theme code="app.suggestion.header" /></h3>
	 	<div class="margin20"><spring:theme code="app.suggestion.body" /></div>
		<div class="row"><div class="col-xs-12 ios-app-btn hide">
			<a target="_blank" href="${component.getITunesUrl()}" id="appStore" class="btn btn-primary full-width flex-center justify-center" type="submit" title="App Store" >
				<common:footerIcon iconName="iconawesomeapple" width="${!mobileFlag ? '16.807':'31.8'}" height="${!mobileFlag ? '20':'19'}" viewBox="0 0 16.807 20" display="m-r-15" />
				<spring:theme code="app.suggestion.ios" />
			</a>
		</div><div class="col-xs-12 android-app-btn hide">
			<a target="_blank" href="${component.playStoreUrl}" id="playStore" class="btn full-width btn-primary flex-center justify-center" type="submit" title="Android Market" >
				<common:footerIcon iconName="iconawesomegoogleplay" width="${!mobileFlag ? '18.029':'33.03'}" height="${!mobileFlag ? '20':'20'}" viewBox="0 0 18.029 20" display="m-r-15" />
				<spring:theme code="app.suggestion.android" />
			</a> 
		</div><div class="col-xs-12 margin-top-20">
			<a href="#" id="playStore" class="btn btn-primary full-width continue-with-webapp" type="submit" title="Continue With Webapp"><spring:theme code="app.suggestion.continue" /></a> 
		</div></div>
	</div>
</div>
