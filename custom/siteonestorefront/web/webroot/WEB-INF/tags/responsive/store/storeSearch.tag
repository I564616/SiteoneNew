<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="errorNoResults" required="true" type="java.lang.String"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<input id="myStoresIdList" type="hidden" value='${ycommerce:getJSONFromList(myStoresIdList)}'> 
<c:url value="/store-finder" var="storeFinderFormAction" />
<input type="hidden" value="${query}" id="query"/>
<div class="row">
		<div id="noStoreResultsMsg"></div>
		 <div class="cl"></div>
		<div id="searchAgain" class="black-title store-specialty-heading" style="display:none"><label class="bold-text" for="storelocator-query"><spring:theme code="pickup.find.search.again"/></label></div>
</div>
<div>
 <form:form action="${storeFinderFormAction}" name="storesearch" method="get" modelAttribute="siteOneStoreFinderForm">
<div class="row store-finder-margin">
		<div class="col-md-2 col-xs-12 col-sm-12 store-finder-grey" id="store-heading">
			<div class="row">
				<div class="col-md-offset-6 col-sm-offset-6 col-xs-8">
					<h1 class="find-branch m-b-0"> <spring:theme code="storeFinder.find.a.store" /> </h1>
		    		<input type="button" id="printOption" class="btn btn-primary btn-block" value="Print" onclick="window.print()" style="display:none"/>
				</div>
				<div class="col-xs-4 visible-xs">
					 	<form:select id="storelocatormiles-query-mobile" path="miles" cssClass="form-control" title="Search within">
					 	<c:choose>
							<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
								<option value="100">
								    <spring:theme code="store.km.100"/>
								</option>
								<option value="50">
									<spring:theme code="store.km.50"/>
								</option>
								<option value="20">
									<spring:theme code="store.km.20"/>
								</option>
							</c:when>
							<c:otherwise>
							  		<option value="100">
									    <spring:theme code="store.miles.100"/>
									</option>
									<option value="50">
										<spring:theme code="store.miles.50"/>
									</option>
									<option value="20">
										<spring:theme code="store.miles.20"/>
									</option>
							</c:otherwise>
						</c:choose>
						</form:select>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-xs-12 col-sm-8 store-finder-green store-finder-group1">
			<div>
				 <div class="col-md-1 col-xs-2 col-sm-1 store-finder-padding">
				 	  <ycommerce:testId code="storeFinder_nearMe_button">
						 <button id="findStoresNearMe" type="button" class="store-finder-button location"> 
						 		<svg id="Group_10665" data-name="Group 10665" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="34" height="34" viewBox="0 0 34 34">
  									<defs>
   										 <clipPath id="clip-path">
      											<rect id="Rectangle_2131" data-name="Rectangle 2131" width="34" height="34" fill="#fff"/>
   										 </clipPath>
  									</defs>
  									<g id="Group_10664" data-name="Group 10664" clip-path="url(#clip-path)">
    									<path id="Path_1605" data-name="Path 1605" d="M31.91,16.014A14.9,14.9,0,0,0,18.041,2.144V0H16.013V2.089A14.9,14.9,0,0,0,2.144,15.958H0v2.028H2.088A14.9,14.9,0,0,0,15.957,31.856V34h2.028V31.911A14.9,14.9,0,0,0,31.855,18.042H34V16.014Zm-2.028,1.973A12.915,12.915,0,0,1,18.041,29.827V27.086H16.013v2.741A12.915,12.915,0,0,1,4.172,17.986H6.913V15.958H4.172A12.915,12.915,0,0,1,16.013,4.117V6.858h2.028V4.117A12.915,12.915,0,0,1,29.882,15.958H27.141v2.028Z" transform="translate(0.001)" fill="#fff"/>
    									<path id="Path_1606" data-name="Path 1606" d="M62.228,55.481a6.747,6.747,0,1,0,6.747,6.747,6.737,6.737,0,0,0-6.747-6.747m0,11.35a4.657,4.657,0,1,1,4.657-4.657,4.676,4.676,0,0,1-4.657,4.657" transform="translate(-45.228 -45.228)" fill="#fff"/>
 								   </g>
								</svg>
						  </button>
					  </ycommerce:testId>
				 	 
				 </div>
			       <ycommerce:testId code="storeFinder_search_box">
				 	 <div class="col-md-6 col-xs-8 col-sm-7 store-finder-padding">
				 		<formElement:formInputBox idKey="storelocator-query" labelKey="storelocator.query" path="q" labelCSS="sr-only" inputCSS="form-control js-store-finder-search-input" mandatory="true"  placeholder="${currentBaseStoreId eq 'siteone' ? 'pickup.search.storemessage' : 'pickup.search.storemessage.canada'}" dtm="store-locator-term"/>
					 </div>
					 <div class="col-md-3 col-xs-8 col-sm-3 store-finder-border store-finder-padding hidden-xs">
					 	<form:select id="storelocatormiles-query" path="miles" cssClass="form-control" title="Search within">
					 		<c:choose>
							<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
								<option value="100">
								    <spring:theme code="store.km.100"/>
								</option>
								<option value="50">
									<spring:theme code="store.km.50"/>
								</option>
								<option value="20">
									<spring:theme code="store.km.20"/>
								</option>
							</c:when>
							<c:otherwise>
							  		<option value="100">
									    <spring:theme code="store.miles.100"/>
									</option>
									<option value="50">
										<spring:theme code="store.miles.50"/>
									</option>
									<option value="20">
										<spring:theme code="store.miles.20"/>
									</option>
							</c:otherwise>
							</c:choose>
						</form:select>
					 </div>
					 <div class="col-md-1 col-xs-2 col-sm-1 store-finder-padding search-mobile">
						 <button type="submit" id="store-finder-button" class="store-finder-button search" dtm="store-locator" data-search-empty="<spring:theme code="storelocator.error.no.results.subtitle" text="Please enter a city and state, ZIP or branch number."/>">
							    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="30" height="30" viewBox="0 0 30 30">
  									<defs>
    									<clipPath id="clip-path">
      										<rect id="Rectangle_2133" data-name="Rectangle 2133" width="30" height="30" fill="#fff"/>
   										 </clipPath>
  									</defs>
  									<g id="Group_10668" data-name="Group 10668" clip-path="url(#clip-path)">
    										<path id="Path_1608" data-name="Path 1608" d="M22.888,21.118a12.9,12.9,0,1,0-9.964,4.729,12.83,12.83,0,0,0,8.2-2.959L28.232,30,30,28.232Zm.391-8.194a10.4,10.4,0,1,1-10.4-10.4,10.415,10.415,0,0,1,10.4,10.4" transform="translate(0 0)" fill="#fff"/>
  									</g>
								</svg>
						</button>
					 </div>
					 </ycommerce:testId>
			</div>
		</div>
		<div class="col-md-4 col-xs-12 col-sm-4 store-finder-green">
		   <div class="col-md-4"></div>
		 		<div class="col-md-7 col-xs-12 store-finder-specialties-dropdown marginTop20">
		 			<div class="flex-center specialties-dropdown store-finder-details-input-field reqacc-dropdown" style="background-color: #fff !important;" onclick="ACC.storefinder.showSpecialtiesOption()">
                    <img src="/_ui/responsive/theme-lambda/images/branchspecialty_StoreFilter.png" alt="store-speciality-filter" class="pull-left" height="24" />
                    <div class="specialties-filter font-size-14 hidden-xs"><spring:theme code="search.nav.refine.button" /></div>
                    <div class="specialties-filter font-size-14 visible-xs"><spring:theme code="requestaccount.filter.text.mobile" /></div>
                </div>
                <div class="specialties-option" style="width:calc(100% - 30px);">
                    <label for="Agronomics" class="specialty-name flex-center">
                        <span>
							<img  alt="Agronomics" title="<spring:theme code="storeDetailsContent.agronomics"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Agronomics.png"/>
							<spring:theme code="storeDetailsContent.agronomics" />
						</span>
                        <span class="colored"><input id="Agronomics" name="Agronomics" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Delivery" class="specialty-name flex-center">
                        <span>
							<img alt="Delivery" title="<spring:theme code="storeDetailsContent.delivery"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Delivery.png"/>
							<spring:theme code="storeDetailsContent.delivery" />
						</span>
                        <span class="colored"><input id="Delivery" name="Delivery" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Hardscapes" class="specialty-name flex-center">
                        <span>
							<img alt="Hardscapes" title="<spring:theme code="storeDetailsContent.hardscape.tier"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Hardscapes.png"/>
							<spring:theme code="storeDetailsContent.hardscape.tier" />
						</span>
                        <span class="colored"><input id="Hardscapes" name="Hardscapes,Stone Center" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Irrigation" class="specialty-name flex-center">
                        <span>
							<img alt="Irrigation" title="<spring:theme code="storeDetailsContent.irrigation"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Irrigation.png"/>
							<spring:theme code="storeDetailsContent.irrigation" />
						</span>
                        <span class="colored"><input id="Irrigation" name="Irrigation" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Pesticide" class="specialty-name flex-center">
                        <span>
							<img alt="Pesticide" title="<spring:theme code="storeDetailsContent.pestmanagement"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Pesticide.png"/>
							<spring:theme code="storeDetailsContent.pestmanagement" />
						</span>
                        <span class="colored"><input id="Pesticide" name="Pest Management" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Nursery" class="specialty-name flex-center">
                        <span>
							<img alt="Nursery" title="<spring:theme code="storeDetailsContent.nursery"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Nursery.png"/>
							<spring:theme code="storeDetailsContent.nursery" />
						</span>
                        <span class="colored"><input id="Nursery" name="Nursery" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Lighting" class="specialty-name flex-center">
                        <span>
							<img  alt="Lighting" title="<spring:theme code="storeDetailsContent.outdoor"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Lighting.png"/>
							<spring:theme code="storeDetailsContent.outdoor" />
						</span>
                        <span class="colored"><input id="Lighting" name="Outdoor Lighting" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <div class="cl"></div>
                    <div  class="btn btn-block btn-primary" onclick="ACC.storefinder.createFilters()"><spring:theme code="estimate.update" /></div>
               	  </div>
		 		</div>
		   </div>
</div>
</form:form>
<div class="col-md-12 padding0">
                <div class="row specialties"></div>
</div>
</div>
<div class="row">
	<div class="col-md-3 col-xs-6">
		 <!-- <a href="<c:url value="/store-directory"/>"><spring:theme code="storeFinder.store.directory" />&#8594;</a><br/>-->
		<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
		<a href="<c:url value="/my-account/my-stores"/>"><spring:theme code="storeFinder.my.stores" /> &#8594;</a><br/>
		</sec:authorize> 
	</div>
</div>
<div class="row no-Store">
	<div class="col-lg-12">
		<div class="store-spinner text-center" style="padding: 80px 0px 100px 0px;border-top: 1px solid #e0e0e0;margin-top: 30px;display: none">
				<img src="${commonResourcePath}/images/spinner.gif" alt="" aria-hidden="true"/>
		</div>
		<div id="storeResultsMsg" class="headline text-center"></div>
	</div>
</div>