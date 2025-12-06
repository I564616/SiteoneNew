<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<style>


</style>

<spring:url value="/" var="homelink" htmlEscape="false"/>
<div id="store_details" class="col-sm-12">


    <input type="hidden" id="lat" value="${latitude}"/>

    <input type="hidden" id="lng" value="${longitude}"/>


    <input type="hidden" id="storeId" value="${storeId}"/>


    <input type="hidden" id="storeNotes" value="${storeNotes}"/>


    <p class="breadcrumb-section text-primary"><span class="breadcrumb"><b><spring:theme code="storeDirectoryCityPage.find.branch" /></b></span></p>
    <div class="row">
    <div class="col-md-9 col-sm-7 col-xs-12">
    <h1 class="h3">
        <span class="store_details_name headline">${store.name}</span>
        <c:if test="${not empty store.title}">
            <span c:out class="store_details_Sname">  </br>${store.title}</span>
        </c:if>
    </h1>
    <%-- <a href="JavaScript:Void(0);" onclick="window.history.back();"><spring:theme code="text.store.Back" /></a> --%>

    <c:choose>
        <c:when test="${not empty searchtext and not empty miles}">
            <a href="<c:url value="/store-finder/?searchtext="/>${fn:escapeXml(searchtext)}&distance=${fn:escapeXml(miles)}"><spring:theme code="text.store.Back"/>&#8594;</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/store-finder"/>"><spring:theme code="text.store.Back"/>&#8594;</a>
        </c:otherwise>
    </c:choose>
    <div class="cl"></div>
    <br/>
    </div>
<div class="col-md-3 col-sm-5 col-xs-12">
    <c:if test="${store.enableOnlineFulfillment eq true or store.enableOnlineFulfillment eq 'true'}">
 <c:choose>
                        <c:when
                                test="${store.isPreferredStore eq false && !showRemoveMyStore && store.storeId != cookie['csc'].getValue()}">
                            <form:form method="get"  
                                       action="${homelink}store-finder/make-my-store/${store.storeId}">
                                 <input type="submit" value="<spring:theme code='storeDirectoryListPage.make.branch' />"
                                                                       class="btn btn-primary btn-block makeMyBranch marginBottom20"> 

                            </form:form>
                        </c:when>
                        <c:otherwise>
                            <%-- <a href="/store-finder/remove-my-store/${store.storeId}" id="${store.storeId}" --%>
                                <a href="#" id="removeStore" data-store-id="${store.storeId}"
                                   data-name="${store.name}" data-line1="${store.address.line1}"
                                   data-line2="${store.address.line2}"
                                   data-town="${store.address.town}"
                                   data-region-code="${store.address.region.isocodeShort}"
                                   data-postal-code="${store.address.postalCode}" class="btn btn-block  btn-default marginBottom20"><spring:theme code="storeDetailsContent.remove.branch" /></a>
                                <c:set var="showRemoveMyStore" value="true"/>

                        </c:otherwise>
                    </c:choose>
</c:if>
                     <div class="cl hidden-md hidden-lg"> </div>
                     <c:choose>
                     <c:when test="${not fn:contains(store.description, 'Stone Center')}">
                     <c:url var="contactUs" value="/contactus"/>
					</c:when>
					<c:otherwise>
					<c:url var="contactUs" value="/contactus/hardscapes"/>
					</c:otherwise>
					</c:choose>
					
					<a class="btn btn-primary btn-block btn-white ${store.enableOnlineFulfillment eq false or store.enableOnlineFulfillment eq 'false' ? 'inbranchshoppingonlymargintop':''}"  href="${contactUs}"><spring:theme code='help.contactUs.askanexpert' /></a>
                    <c:if test="${!isAnonymous && isNurseryStore}">
                    <div class="col-md-12 padding0 m-t-15 nursery-inventeryp3 hidden-md hidden-lg">
                        <a class="btn btn-primary btn-block btn-white inventoryDownload"  href="/my-account/downloadNurseryInventoryCSV?storeId=${storeId}">Download Nursery Inventory</a>
                    </div>
                    </c:if>
                    <c:if test="${isAnonymous && isNurseryStore}">
                    <div class="col-md-12 padding0 m-t-15 nursery-inventeryp3 hidden-md hidden-lg">
                        <a class="btn btn-primary btn-block btn-white signInOverlay"  onclick="ACC.global.gustInventoryDownload()">Log in to Download Nursery Inventory</a>
                    </div>
                    </c:if>

</div>
</div>
<div class="cl"></div>

    <div class="item-sec-border"></div>
<div class="margin20 store-specificDetails">
    <div class="row">
    <div class="col-md-7 col-sm-8 col-xs-12">
    <p><span class="bold-text">${store.description}</span>&nbsp;${store.longDescription}</p>
    </div>
     <div class="col-md-4 col-sm-4 col-xs-12 col-md-offset-1">
     <div class="row">
     	<c:forEach items="${store.storeImages}" var="storeImage" varStatus="status">
            <c:if test="${storeImage.format eq 'store'}">
                <div class="col-md-12 col-xs-12  ${status.first?'':'img-border'}"><img src="${storeImage.url}"  class="img-responsive pull-right" alt="cobranded logo"/></div>
            </c:if>
   	 	</c:forEach>   
     </div>
     </div>

    </div>
    </div>
    <div class="cl"></div>
    <div class="col-xs-12 col-sm-12 col-md-4 store-detail-sec">
        <div class="row">
            <ul class="media-list">
                 <c:if test="${store.enableOnlineFulfillment eq false or store.enableOnlineFulfillment eq 'false'}">
                <li>
                    <span class="inBranchShoppingOnly"><spring:theme code="global.header.branchShopping"/></span>
                    <p></p>
                </li>
                </c:if>
                <li>
                    <div class="media-body store_time">${store.storeStatus}</div>
                    <p></p>
                </li>

                <li>
                    <c:if test="${not empty store.specialText}">
                        <p class="specialTextDescription">${store.specialText}</p>
                    </c:if>
                    <c:if test="${not empty store.address.phone}">
                        <p><a class="tel-phone content-product-title"
                              href="tel:${store.address.phone}"><u>${store.address.phone}</u></a></p>

                    </c:if>
                    <c:if test="${empty store.address.phone}">
                        <spring:theme code="storeDetails.phone.unvailable"/>
                    </c:if>


                </li>

                <li class="row">

                    <div class="col-md-6 col-xs-12">
                        <button class="btn btn-primary btn-block hidden-lg hidden-md  margin20">
                            <a class="tel-phone content-product-title" href="tel:${store.address.phone}"><spring:theme code="storeDetailsContent.call.now" /></a>
                        </button>
                        <div class="visible-xs marginTop35">
                            <div class="item-sec-border visible-xs"></div>
                        </div>
                    </div>


                </li>


                <li>
                    <!-- <div class="media-left">
                        <a href="#"> <img alt="64x64" class="media-object"
                            data-src="holder.js/64x64"
                            src="https://dummyimage.com/20x20/7a7a7a/"
                            data-holder-rendered="true">
                        </a>
                    </div> -->

                    <c:if test="${not empty store.address}">
                    <p></p>${store.address.line1}&nbsp;${store.address.line2} </br>
                        ${store.address.town},&nbsp;${store.address.region.isocodeShort}&nbsp;${store.address.postalCode}
                
                </c:if>
                <c:if test="${empty store.address}">
                    <spring:theme code="storeDetails.address.unvailable"/>
                </c:if>


                </li>


                <li>

                    <!-- <div class="media-left">
                        <a href="#"> <img alt="64x64" class="media-object"
                            data-src="holder.js/64x64"
                            src="https://dummyimage.com/20x20/7a7a7a/"
                            data-holder-rendered="true">
                        </a>
                    </div> -->
                    <br/>
                    <c:if test="${not empty store.openingHours}">
                    	<c:if test="${not empty store.openingHours.specialDayOpeningList}">
                             <div class="storeDetailPageSpecialhrs text-default bold m-b-15">
                                   <store:siteoneSpecialHours openingSchedule="${store.openingHours}" />
                             </div>
                         </c:if>
                        <div class="detailSection row">

                            <div class="col-md-12 row"><store:siteoneOpeningSchedule
                                    openingSchedule="${store.openingHours}"/>
                            </div>

                        </div>

                    </c:if>

                    <c:if test="${empty store.openingHours}">
                        <div class="detailSection">

                            <spring:theme code="storeDetails.hours.unvailable"/>

                        </div>
                    </c:if>
                    <input type="hidden" id="isNurseryStore" value="${isNurseryStore}"/>
                    <c:if test="${!isAnonymous && isNurseryStore}">
                    <div class="col-md-8 padding0 m-t-35 nursery-inventeryp3 hidden-xs hidden-sm">
                        <a class="btn btn-primary btn-block btn-white inventoryDownload" href="/my-account/downloadNurseryInventoryCSV?storeId=${storeId}">Download Nursery Inventory</a>
                    </div>
                    </c:if>
                    <c:if test="${isAnonymous && isNurseryStore}">
                    <div class="col-md-9 padding0 m-t-35 nursery-inventeryp3 hidden-xs hidden-sm">
                        <a class="btn btn-primary btn-block btn-white signInOverlay" onclick="ACC.global.gustInventoryDownload()">Log in to Download Nursery Inventory</a>
                    </div>
                    </c:if>

                </li>
                <%--  <p>${store.specialText}</p> --%>
                <!--   <li class="media">
                    <a href="JavaScript:Void(0);" onclick="window.history.back();">Back to store locator</a>
                </li> -->

                <li>


                    <c:set var="showRemoveMyStore" value="false"/>
                    <c:forEach items="${myStoresIdList}" var="mystore">
                        <c:if test="${mystore eq store.storeId}">
                            <c:set var="showRemoveMyStore" value="true"/>
                        </c:if>
                    </c:forEach>

                    <div class="cl margin20"></div>

                    <c:if test="${not empty store.storeNotes}">
                        <p>${store.storeNotes}</p>
                    </c:if>

                    <br/><br/>
                    

                    <BR>


                </li>

            </ul>
        </div>
    </div>
    <div class="cl visible-xs visible-sm item-sec-border"><br/><br/></div>
    <div class="visible-xs visible-sm store-specialty-heading"><span class="bold-text"><spring:theme code="storeDetailsContent.map.direction" /></span></div>
    <div class="col-xs-12 col-sm-6 col-md-8 store-detail-map pull-right">
        <p class="text-right hidden-xs hidden-sm"><a href="javascript:void(0)" onclick="window.print()"><span
                class="glyphicon glyphicon-print"></span></a> <a href="javascript:void(0)" onclick="window.print()"><spring:theme code="storeDetailsContent.print" /></a>
        </p>

        <div id="storedetailMap" class="myMap mobile-detail-map" data-myLatitude="${store.geoPoint.latitude}"
             data-myLongitude="${store.geoPoint.longitude}" data-sitedata="${sitedata }"
             data-store-id="${store.storeId}"
             data-name="${store.name}" data-line1="${store.address.line1}"
             data-line2="${store.address.line2}"
             data-town="${store.address.town}"
             data-regioncode="${store.address.region.isocodeShort}"
             data-postalcode="${store.address.postalCode}" data-phone="${store.address.phone}">

            <div id="googleMap" style="width:100%;height:500px;"></div>
        </div>
        <div class="col-md-12">
				<span class="pull-left">
								
				<a id="getDirection" href=""
                   data-url="${store.address.line1},${store.address.line2},${store.address.town},${store.address.region.isocodeShort},${store.address.postalCode}"><spring:theme code="storeDetailsContent.directions" /> &#8594;</a>
				</span>
            <span class="pull-right">${store.formattedDistance} </span>
        </div>
    </div>
    <div class="cl"></div>


</div>
<div class="cl"></div>


<!--
To use this code on your website, get a free API key from Google.
Read more at: https://www.w3schools.com/graphics/google_maps_basic.asp
-->

<div class="cl"></div>
<br/>
<div class="hidden-xs hidden-sm"><br/><br/> <br/></div>
<br/><br/>

<c:if test="${not empty store.storeSpecialityDetails}">
 <div class="row shadow-box">
 	<div class="text-center visible-xs" style="padding-top: 5%;">	
		 	<h2 class="store-specialty-heading"><strong><spring:theme code="storeDetailsContent.branch.special" /></strong></h2>
	 </div>
	<div  class="col-md-12 icon-group_store">
   		 <div class="hidden-xs Icon-StoreSpeciality" style="cursor: default;"> 
   		 	<h2 class="text-center store-specialty-heading"><strong><spring:theme code="storeDetailsContent.branch.special" /></strong></h2>
   		  </div>
   		  <c:set var="storeSpecialityNum" value="1"/> 
   		  <c:forEach items="${store.storeSpecialityDetails}" var="storeSpeciality">  
   		    <c:if test="${not empty storeSpeciality.description}">
   		    <div class="text-center Icon-StoreSpeciality" onclick="ACC.storefinder.bannerStoreFinder('${storeSpecialityNum}')">
     			<img alt="${storeSpeciality.icon.altText}" src="${storeSpeciality.icon.url}" class="${storeSpecialityNum eq 1 ? 'Icon-StoreSpeciality-border': ''}"  />     
     		</div>
   		 	<c:set var="storeSpecialityNum" value="${storeSpecialityNum+1}"/>
			</c:if>
		 </c:forEach> 		
    </div>
 </div>
</c:if>
<c:set var="storeSpecialityBanner" value="1"/> 
<c:forEach items="${store.storeSpecialityDetails}" var="storeSpecialities">
    <c:if test="${not empty storeSpecialities.description}">
	<div class="col-xs-12 col-sm-12 col-md-12 speciality-banner" data-speciality="${storeSpecialityBanner}">
   		 <c:set var="storeSpecialityBanner" value="${storeSpecialityBanner+1}"/>
			<div style="background-image: url(${storeSpecialities.image.url});background-size: cover;" class="speciality-banner-design">
				<div class="col-md-12 hidden-xs">
				<div class="row text-container border-white">
              		<div class="col-md-10 col-sm-10 speciality-banner-content">
						<div class="row flex-center">
    						<div class="col-md-10"><h2>${storeSpecialities.name}</h2></div>
    					 </div>
			     		 <p class="marginTop10">${storeSpecialities.description}</p>
			  		 </div>
				<div class="col-md-2 col-sm-2 text-center text-white svg-bg" style="height: 100%;">
							<c:if test="${fn:contains(storeSpecialities.code,'Agronomics')}">
								<common:agronomicsIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Delivery')}">
 								<common:delivery_Icon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Hardscapes')}">
 								<common:hardscapesIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Irrigation')}">
 								<common:irrigationIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Licensed Pesticide Distributor')}">
 								<common:pesticideIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Nursery Center')}">
 								<common:nurseryIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Outdoor Lighting')}">
 								<common:outdoor-lightingIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Golf Course Maintenance')}">
 								<common:golfcoursemaintenanceIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Tool Rental')}">
 								<common:toolrentalIcon />
 							</c:if>
 						</div>
				</div>
			</div>
	   <div class="col-xs-12 visible-xs">
	   	<div class="row text-container mobile-border-white">
              <div class="col-xs-12">
              	<div class="row mobile-speciality-header">
    					<div class="col-xs-9 mobile-speciality-name"><h2>${storeSpecialities.name}</h2></div>
    					<div class="col-xs-3 text-center text-white mobile-svg-bg">
    						<c:if test="${fn:contains(storeSpecialities.code,'Agronomics')}">
                            <common:agronomicsIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Delivery')}">
 								<common:delivery_Icon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Hardscapes')}">
 								<common:hardscapesIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Irrigation')}">
 								<common:irrigationIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Licensed Pesticide Distributor')}">
 								<common:pesticideIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Nursery Center')}">
 								<common:nurseryIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Outdoor Lighting')}">
 								<common:outdoor-lightingIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Golf Course Maintenance')}">
 								<common:golfcoursemaintenanceIcon />
 							</c:if>
 							<c:if test="${fn:contains(storeSpecialities.code,'Tool Rental')}">
 								 <common:toolrentalIcon />
 							</c:if>
 						</div>
    			 </div>
			     		<p class="marginTop10">${storeSpecialities.description}</p>
			   </div>
			</div>
		</div>
	</div>
   </div>
   </c:if>
</c:forEach>


<%-- <store:store	Map store="${store}"/> --%>


<div class="cl"></div>
<br/><br/>
<div class="nearByStores">
<div class="row col-md-12 col-xs-12">
 <c:if test="${not empty nearByStores}">
 	<div class="col-md-12 col-xs-12 textH1">
 		<spring:theme code="storeFinder.nearby.locations"/>
	</div>
	<c:forEach items="${nearByStores}" var="nearByStoreData"> 
		<div class="col-md-4 col-xs-12">
 			<div>
 				<div class="distance">${nearByStoreData.formattedDistance}</div>
                 <form:form method="get" action="${homelink}store-finder/make-my-store/${nearByStoreData.storeId}">
                    <input type="submit" value="<spring:theme code='storeDirectoryListPage.make.branch' />" class="mybranch-mobile visible-xs border-none bg-transparent"> 
                </form:form>
 			</div>
 			<div id="storeName${nearByStoreData.name}" class="store-name">
 					<script>
 						splitName = "${nearByStoreData.name}".split("#")[0];
 						document.getElementById("storeName${nearByStoreData.name}").innerHTML = splitName;
 					</script>
 			</div>
 			<div class="store-id">
 				Store&nbsp;#${nearByStoreData.storeId}
 			</div>
            
            <c:if test="${nearByStoreData.enableOnlineFulfillment eq false or nearByStoreData.enableOnlineFulfillment eq 'false'}">	
                <div class="m-b-10"><span class="inBranchShoppingOnly"><spring:theme code="global.header.branchShopping"/></span></div>
            </c:if>
 			<div class="store-address">
 				${nearByStoreData.address.line1}&nbsp;${nearByStoreData.address.line2}<br>
 				${nearByStoreData.address.town},&nbsp;${nearByStoreData.address.region.isocodeShort}&nbsp;${nearByStoreData.address.postalCode}<br>
                <span class="store-direction">			 
 					<a onclick="ACC.storefinder.nearbyStoreGeoLocation('${nearByStoreData.address.line1}','${nearByStoreData.address.town}','${nearByStoreData.address.region.isocodeShort}','${nearByStoreData.address.postalCode}')" style="text-decoration: none;">
 						<spring:theme code="storeFinder.get.direction"/>
 					</a>			
 				</span>
 			</div>
 			<div class="store-contact">
 				${nearByStoreData.address.phone}<br>
 				${nearByStoreData.storeStatus} 
 			</div>
 			<c:if test="${not empty nearByStoreData.openingHours.specialDayOpeningList}">
                  <store:siteoneSpecialHours openingSchedule="${nearByStoreData.openingHours}" />
             </c:if>
 			<c:if test="${not empty nearByStoreData.storeSpecialityDetails}">
 			<div>
 				<div onClick="ACC.storefinder.expandSpeciality(expandspeciality${nearByStoreData.storeId}, expandicon${nearByStoreData.storeId}, expandname${nearByStoreData.storeId}, hideicon${nearByStoreData.storeId}, hidename${nearByStoreData.storeId})">
 					<img id="expandicon${nearByStoreData.storeId}" class="branch-card-expand-icon" alt="" src="/_ui/responsive/theme-lambda/images/plus-icon.svg"/>
 					<img id="hideicon${nearByStoreData.storeId}" class="branch-card-expand-icon hidden"  alt="" src="/_ui/responsive/theme-lambda/images/hide-icon.svg"/>
 					<div id="expandname${nearByStoreData.storeId}" class="branch-card-expand-name">
 						<spring:theme code="storeFinder.Show.branch.specialties"/>
 					</div>
 					<div id="hidename${nearByStoreData.storeId}" class="branch-card-expand-name hidden">
 						 <spring:theme code="js.storefinder.hide.specialties"/>
 					</div>
 				</div>
 			</div>
 		    <div class="row expand-container hidden" id="expandspeciality${nearByStoreData.storeId}">
 		    	<div class="row col-md-12 col-xs-12 store-container-icon">
 						 <c:forEach items="${nearByStoreData.storeSpecialityDetails}" var="storeSpecialityDetails">
 						 	<div class="store-container-icon-width">
								<img class="branch-container-img" alt="${storeSpecialityDetails.name}" src="${storeSpecialityDetails.icon.url}"/>
							</div>
 						 </c:forEach>	
 				</div><br>
 				<c:forEach items="${nearByStoreData.storeSpecialityDetails}" var="storeSpecialityDetails"> 
					<div class="branch-container-item col-md-4 col-xs-4">
						<img class="branch-container-img" alt="${storeSpecialityDetails.name}" src="${storeSpecialityDetails.icon.url}"/>
							<span class="store-icon-label">${storeSpecialityDetails.name}</span>
					</div>
 				</c:forEach>	
 			</div>
 			</c:if>
 			<div class="store-detail-mobile">
                <c:if test="${nearByStoreData.enableOnlineFulfillment eq true or nearByStoreData.enableOnlineFulfillment eq 'true'}">
                <form:form method="get" action="${homelink}store-finder/make-my-store/${nearByStoreData.storeId}">
                    <input type="submit" value="<spring:theme code='storeDirectoryListPage.make.branch' />" class="mybranch-button hidden-xs"> 
                </form:form>
                </c:if>
 				<a href="/store/${nearByStoreData.storeId}?searchtext=${nearByStoreData.storeId}&miles=100" class="store-details"><spring:theme code="storeFinder.view.store.details"/></a>
 			</div>
 		</div>
	</c:forEach>
  </c:if>
</div>
</div>
<div class="cl"></div>

<%-- <c:forEach items="${store.openingHours.weekDayOpeningList}" var="weekDayOpening" varStatus="loop">
    <input type="hidden" id="openingTime${loop.index}" value="${weekDayOpening.openingTime.formattedHour}"/>
    <input type="hidden" id="closingTime${loop.index}" value="${weekDayOpening.closingTime.formattedHour}"/>
    <input type="hidden" id="weekDay${loop.index}" value="${weekDayOpening.weekDay}"/>
</c:forEach>  --%>


<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "LocalBusiness",
   <c:if test="${not empty store.storeImages}">
    "image" : "https://www.siteone.com/${store.storeImages.get(0).url}",
</c:if>
  "name": "${store.title}",
  "branchCode": "${storeId}",
   "geo": {
    "@type": "GeoCoordinates",
    "latitude": "${store.geoPoint.latitude}",
    "longitude": "${store.geoPoint.longitude}"
  },
  "address":{
  
  "@type": "PostalAddress",
  "streetAddress": "${store.address.line1}",
  "addressLocality": "${store.address.town}",
  "addressRegion" : "${store.address.region.isocodeShort}",
  "addressCountry": "${store.address.country.isocode}",
  "postalCode": "${store.address.postalCode}",
  "telephone": "${store.address.phone}"

  },
 "openingHoursSpecification":[
   <c:forEach items="${store.openingHours.weekDayOpeningList}" var="weekDayOpening" varStatus="loop">
    {
    "@type": "OpeningHoursSpecification",
    "opens": "${weekDayOpening.formattedOpeningTime}",
    "dayOfWeek": "${weekDayOpening.formattedWeekDay}",
    "closes": "${weekDayOpening.formattedClosingTime}"
    } <c:if test="${!loop.last}">,</c:if>
</c:forEach>]
}
</script>