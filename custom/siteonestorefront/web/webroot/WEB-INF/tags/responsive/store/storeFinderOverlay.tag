<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div id="failedgeolocationOverlay" style="display: none;">
							<div id="noGeoStoreResultsMsg"></div><br/>
		  <div class="cl"></div>
		 	<span class="hidden-xs"><br/></span>
		<div id="geoSearchAgain" class="black-title store-specialty-heading" style="display:none"><label class="bold-text search-again-label" for="geoStorelocator-query"><spring:theme code="pickup.find.search.again"/></label></div>
			
					<div id="storeFinder_search"></div>
					<div class="row" id="geo-store-heading">
				  <div class="headline"><spring:theme code="text.failedlocation-title"/></div>
					<p class="margin20"> <spring:theme code="text.failedlocation-intro"/> </p>
                    </div>
                    <div class="row">
                    <div class="col-sm-12 col-md-12">
				
				    <div class="row">
				
					 <form:form action="${storeFinderFormAction}" method="get" modelAttribute="siteOneStorePopupForm">
						<ycommerce:testId code="storeFinder_search_box">
							 
							<div class="store-search-input">
								<formElement:formInputBox idKey="geoStorelocator-query" labelKey="storelocator.query" path="q" labelCSS="sr-only" inputCSS="form-control js-geo-finder-search-input geoFinder"  mandatory="true" placeholder="pickup.search.message" />
							</div>	
					<div class="search-store-btn col-md-2">	
							  <form:select id="geostorelocatormiles-query" path="miles" cssClass="form-control miles-dropdown" title="Search within">
							  		<option value="100">
									    <spring:theme code="tag.store.miles.100"/>
									</option>
									<option value="50">
										<spring:theme code="tag.store.miles.50"/>
									</option>
									<option value="20">
										<spring:theme code="tag.store.miles.20"/>
									</option>
								</form:select>
							</div>
							<div class="cl"></div>
							 
							<div class="col-md-3 col-sm-12 col-xs-12">
							 <div class="row">
									<button id ="GoeStoreOverLay" class="btn btn-primary btn-block margin20" type="submit">
										<spring:theme code="pickup.find.search"/>
									</button>
									</div>
									</div>
							 
						</ycommerce:testId>
					</form:form>  
					<div class="cl"></div>
					<div class="col-sm-12 row">
						<a href="<c:url value="/store-directory"/>" ><spring:theme code="storeFinder.store.directory" />&#8594;</a><br/>
						<a href="<c:url value="/request-account/form"/>" ><spring:theme code="storeHeader.requestAccount" /> &#8594;</a><br/>
				    </div>
				    </div>
					</div>
				</div>
				<div id="geostoreResultsMsg" class="headline text-center"></div>
					</div>
