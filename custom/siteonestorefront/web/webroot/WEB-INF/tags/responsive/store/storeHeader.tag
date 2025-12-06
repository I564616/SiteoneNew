<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="loggedIn" required="false" %>
<%@ attribute name="hasShipTo" required="false" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="icons" tagdir="/WEB-INF/tags/responsive/icons"  %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<input id="myStoresIdList" type="hidden" value='${ycommerce:getJSONFromList(myStoresIdList)}' />
<spring:url value="/" var="homelink" htmlEscape="false"/>
<input type="hidden" value="${query}" id="query"/>
<div class="p-r-20 col-xs-8 p-l-40-xs text-left-xs p-l-40-sm text-left-sm ${loggedIn && hasShipTo gt 1 ? 'col-md-4 text-align-right' : 'col-md-6 text-align-center' } store-padding top-hat-col-2">
	<div data-global-linkname="top nav: change branch" class="flex-center ${loggedIn && hasShipTo gt 1 ? 'justify-flex-end' : 'justify-center' } justify-flex-start-sm justify-flex-start-xs storeHeader">
		<common:headerIcon iconName="marker" iconFill="#f90" iconColor="#fff" width="15" height="18" viewBox="0 0 15 18" display="m-r-5 mob-header-marker" />
		<div class="pos-relative-xs pos-relative-sm">
			<input id="sessionStore" type="hidden" value="${sessionStore.storeId}" />
			<input id="sessionStoreName" type="hidden" value="${sessionStore.name}" />
			<input id="isPreferredStore" type="hidden" value="${sessionStore.isPreferredStore}" />
			<input id="isHeadSessionName" type="hidden" value="${empty sessionStore.name}" />
			<input id="sessionStoreLat" type="hidden" value="${sessionStore.geoPoint.latitude}" />
			<input id="sessionStoreLong" type="hidden" value="${sessionStore.geoPoint.longitude}" />
			<input id="sessionStoreNotes" type="hidden" value="${sessionStore.storeNotes}" />
			<input id="sessionStoreLine1" type="hidden" value="${sessionStore.address.line1}" />
			<input id="sessionStoreLine2" type="hidden" value="${sessionStore.address.line2}" />
			<input id="sessionStoreTown"  type="hidden" value="${sessionStore.address.town}" />
			<input id="sessionStoreRegion" type="hidden" value="${sessionStore.address.region.isocodeShort}" />
			<input id="sessionStorePostal" type="hidden" value="${sessionStore.address.postalCode}" />
			<input id="sessionStorePhone" type="hidden" value="${sessionStore.address.phone}" />
			<c:set var="ipGeolocationUrl" value="<%=de.hybris.platform.util.Config.getParameter(\"ip.geolocation.url\")%>"/>
			<input type="hidden" value="${ipGeolocationUrl}" id="ipGeolocationUrl"/>
			<c:set var="ipGeolocationKey" value="<%=de.hybris.platform.util.Config.getParameter(\"ip.geolocation.key\")%>"/>
			<input type="hidden" value="${ipGeolocationKey}" id="ipGeolocationKey"/>
			<c:choose>
				<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
					<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>
				</c:when>
				<c:otherwise>
					<c:set var="contactNo" value="${sessionStore.address.phone}"/>
				</c:otherwise>
			</c:choose>
			<div id="js-mobile-flyoutlink" class="branchpopup_click">
				<div class="js-store-address store-address ${empty sessionStore.name ?'hidden':''}" style="line-height:12px;">
					<c:choose>
						<c:when test="${sessionStore.isPreferredStore || (null != cookie['csc'] && cookie['csc'].value == sessionStore.storeId)}">
							<div class="display-ib text-white text-white-hover headerLabel bold f-w-n-xs f-w-n-sm  f-s-14-xs-px f-s-14-sm-px visible-md-lg-ib data-toggle="popover">
								<spring:theme code="header.nearestStore"/>:
							</div>
						</c:when>
						<c:otherwise>
							<div class="display-ib text-white text-white-hover headerLabel bold f-w-n-xs f-w-n-sm  f-s-14-xs-px f-s-14-sm-px visible-md-lg-ib" data-toggle="popover">
								<spring:theme code="header.nearestStore"/>:
							</div>
						</c:otherwise>
					</c:choose><span class="hidden-xs hidden-sm">&nbsp;</span><span class="town f-s-14-xs-px f-s-14-sm-px l-h-16">${sessionStore.name}</span><span class="storeStatus hidden"></span><span class="glyphicon glyphicon-chevron-down p-l-5 f-s-7 f-s-9-xs-px f-s-9-sm-px mob-header-chevron"></span>
				</div>
				<div class="js-select-location ${empty sessionStore.name ?'':'hidden'}">
					<div class="headerLabel bold f-w-n-xs f-w-n-sm f-s-14-xs-px f-s-14-sm-px f-store-link-margin">
						<spring:theme code="text.select.location"/>
					</div>
				</div>
			</div>
			<!-- Header Store Overlay Starts -->
			<div id="popupstoreOverlay" style="display: none">
				<div id="storeOverlay" class="storeDetails-overlay col-sm-12">
					<div class="row">
						<div class="store-display-wrapper col-xs-12 col-sm-6 col-md-6">
							<span class="storeDisplayName">
								<a href="${homelink}store/${sessionStore.storeId}" class="black-title" style="text-decoration:none;">
									<input type="hidden" id="analyticsstore" value="${sessionStore.name}"/>
									<div class="title">${sessionStore.name}</div>
									<div class="title"> ${sessionStore.title}</div>
								</a>
							</span>
							<br/>
							<span class="line1">${sessionStore.address.line1}</span>&nbsp;<span class="line2">${sessionStore.address.line2}</span>
							<br/>
							<span class="town">${sessionStore.address.town}</span>,
							<span class="region">${sessionStore.address.region.isocodeShort}</span>&nbsp;<span class="postalCode">${sessionStore.address.postalCode}</span>
							<br/>
							<span class="phone">
								<a class="tel-phone" href="tel:${contactNo}">${contactNo}</a>
							</span>
							<br/>
							<br/>
							<span class="storeDirection">
								<a id="getDirection" href="" data-url="${sessionStore.address.line1},${sessionStore.address.line2},${sessionStore.address.town},${sessionStore.address.region.isocodeShort},${sessionStore.address.postalCode}">
									<spring:theme code="js.mystores.directions"/>
								</a>
							</span>
						</div>
						<div class="store-hours-overlay col-xs-12 col-sm-6 col-md-6">
							<div class="">
								<span class="hidden-sm hidden-md hidden-lg">
									<br/>
								</span>
								<div class="title">
									<spring:theme code="header.storeHours"/>
								</div>
								<span class="storeStatus">${sessionStore.storeStatus}</span>
								<c:forEach items="${sessionStore.openingHours.weekDayOpeningList}" var="weekDayOpening" varStatus="status">
									<div class="row store-timings">
										<span class="storeDay">
											<span class="weekDay${status.index}">
												<c:if test="${weekDayOpening.weekDay eq 'Monday'}">
													<spring:theme code="header.monday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Tuesday'}">
													<spring:theme code="header.tuesday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Wednesday'}">
													<spring:theme code="header.wednesday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Thursday'}">
													<spring:theme code="header.thursday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Friday'}">
													<spring:theme code="header.friday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Saturday'}">
													<spring:theme code="header.saturday"/>
												</c:if>
												<c:if test="${weekDayOpening.weekDay eq 'Sunday'}">
													<spring:theme code="header.sunday"/>
												</c:if>
											</span>
										</span>
										<span class="storeTime${status.index}">
											<c:choose>
												<c:when test="${!weekDayOpening.closed}">
												${weekDayOpening.openingTime.formattedHour} - ${weekDayOpening.closingTime.formattedHour}
												</c:when>
												<c:otherwise>
													<spring:theme code="header.storeClosed"/>
												</c:otherwise>
											</c:choose>
										</span>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="cl"></div>
						<div class="">
							<div class="">
								<span class="geolocatedLabel" style="display: none">
									<spring:theme code="header.geolocatedStore.message"/>
								</span>
							</div>
						</div>
					</div>
					<div class="col-sm-12 store-locator-actions">
						<div class="row">
							<c:if test="${!sessionStore.isPreferredStore}">
								<c:if test="${null == cookie['csc'] || (null != cookie['csc'] && cookie['csc'].value != sessionStore.storeId)}">
									<span class="confirmStoreButton">
										<a class="confirmStoreId" href="${homelink}store-finder/make-my-store/${sessionStore.storeId}">
											<input type="submit" class="btn btn-primary" id="confirmBranch" value="<spring:theme code="header.storeConfirm"/>" />
										</a>
									</span>
								</c:if>
							</c:if>
							<a href="${homelink}store-finder" class="changeStoreLink">
								<spring:theme code="header.changeStore"/>
							</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Header Store Overlay Ends -->
			<div class="changebranch_popup" style="display: none">
				<input type="hidden" class="latitude_val" value="${sessionStore.geoPoint.latitude}"/>
				<input type="hidden" class="longitude_val" value="${sessionStore.geoPoint.longitude}"/>
				<div class="row margin0 m-t-30 changebranch_top">
				  <div class="yourshopping">
					<div class="col-xs-12 storeDisplayName_popup font-Geogrotesque">
						<input type="hidden" id="analyticsstore" value="${sessionStore.name}"/>
						<div class="f-s-18 text-green">
							<spring:theme code="js.storefinder.my.branch1"/>
						</div>
						<div class="m-b-5 f-s-24 f-w-600 text-default">${sessionStore.name}</div>
					</div>
					<div class="col-xs-12 font-size-14 m-b-20">
						<p class="m-b-0">${sessionStore.address.line1}&nbsp;${sessionStore.address.line2}</p>
						<p class="">${sessionStore.address.town},&nbsp;${sessionStore.address.region.isocodeShort}&nbsp;${sessionStore.address.postalCode}</p>
						<a class="tel-phone" href="tel:${contactNo}">${contactNo}</a>
						<p class="m-t-10">${sessionStore.storeStatus}</p>
						<a class="btn btn-primary btn-small storeDetails" href="${homelink}store/${sessionStore.storeId}"><spring:theme code="headerstoreoverlay.branchDetails"/></a>
					</div>
					<c:if test="${not empty sessionStore.openingHours.specialDayOpeningList}">
						<div class="storeBranchPopUpSpecialhrs">
							<store:siteoneSpecialHours openingSchedule="${sessionStore.openingHours}" />
						</div>
					</c:if>
					<c:if test="${not empty sessionStore.storeSpecialityDetails}">
						<c:forEach items="${sessionStore.storeSpecialityDetails}" var="speciality" varStatus="status">
							<div class="col-xs-6 f-s-12 flex-center p-r-5 store-speciality-details store-speciality-details-yourbranch">
								<img class="m-r-5 speciality-icon" src="${speciality.icon.url}" alt="${speciality.code}" />
								<span>${speciality.name}</span>
							</div>
						</c:forEach>
					</c:if>
				  </div>
					<div class="yourshopping_plp">
						<div class="yourshopping_plp_productinfo">
						</div>
						<div class="seperator">
						</div>
						<div class="f-s-18 m-t-40 text-green">
							<spring:theme code="js.storefinder.my.branch1"/>
						</div>
						<div class="row bg-light-grey border-radius-5 p-t-10 margin0 m-b-10 m-t-10 branch_item">
							<div class="col-md-12 f-s-20 f-w-600 font-Geogrotesque p-r-45 text-default branch_item_name">${sessionStore.name}</div>
							<div class="col-md-12 m-t-10 font-size-14 branch_item_address">
								<span class="line1">${sessionStore.address.line1}</span>&nbsp;<span class="line2">${sessionStore.address.line2}</span>
								<p class="address_area">
									<span class="town">${sessionStore.address.town}</span>,<span class="region">${sessionStore.address.region.isocodeShort}</span>&nbsp;<span class="postalCode">${sessionStore.address.postalCode}</span>
								</p>
							</div>
							<div
								class="col-md-12 m-b-10 f-w-400 font-size-14 text-blue text-gray-1 margin-ph">
								<a class="underline-text" href="phone:${contactNo}">${contactNo}</a>
							</div>
							<c:if test="${not empty sessionStore.storeSpecialityDetails}">
							<div class="col-xs-12">
								<div class="row margin0">
								<c:forEach items="${sessionStore.storeSpecialityDetails}"
									var="speciality" varStatus="status">
									<div
										class="col-xs-6 f-s-12 flex-center p-l-5 p-r-5 padding0 store-speciality-details">
										<img class="m-r-5 speciality-icon" alt="${speciality.code}"
											src="${speciality.icon.url}"><span>${speciality.name}</span>
									</div>
								</c:forEach>
								</div>
							</div>
							</c:if>
							<div class="col-xs-12 m-y-15">
								<div class="row margin0 p-l-5">
									<div class="col-xs-5 padding0">
										<a class="btn btn-primary btn-small btn-block storeDetails_branches" href="${homelink}store/${sessionStore.storeId}">Store Details</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row margin0 b-b-grey b-t-grey bg-light-grey m-y-30 p-y-20 branchSearch">
					<div class="col-xs-12 f-s-18 f-w-500 font-Geogrotesque text-align-left text-gray-1">
						<spring:theme code="storeFinder.find.a.store"/>
					</div>
					<div class="col-xs-12 flex">
						<input class="branchSearchBox font-size-14" type="text" id="query" name="query" aria-label="findBranch" class="form-control" placeholder="<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'headerstoreoverlay.searchbox.placeholder' : 'headerstoreoverlay.searchbox.placeholder.canada'}"/>"/>
						<input type="hidden" id="miles" name="miles" value="100"/>
						<div onClick="ACC.mystores.storeSearch('search')" class="pointer branchSearchButton">
							<span class="text-white glyphicon glyphicon-search search_icon_button"></span>
						</div>
						<button onClick="ACC.mystores.storeSearch('location')" class="b-r-l-3 border-none branchGeoLocation js-geolocation-icon btn btn-white text-align-center hidden">
							<common:headerIcon iconName="location" iconFill="#77A12E" iconColor="#77A12E" width="21" height="21" viewBox="0 0 21 21" display="" />
						</button>
					</div>
				</div>
				<div class="searchresult_branches">
					<div class="row margin0 searched_container">
						<div class="col-xs-12 searched_branch">
						</div>
					</div>
					<div class="row margin0 nearby_container">
						<div class="col-xs-7 f-s-18 f-w-500 font-Geogrotesque text-gray-1">
							<spring:theme code="headerstoreoverlay.nearbybranches"/>
						</div>
						
						<div class="col-xs-5 text-align-right inStockfilterSection">
							<input type="checkbox" checked="checked" class="myinput" id="instockcheckbox"/><span class="instockcheckbox-text"><spring:theme code="product.grid.in.stock"/></span>
						</div>
					
						<div class="col-xs-5 text-align-right milesSelectorSection">
							<select id="milesSelector" class="btn btn-link btn-small font-Geogrotesque milesOption">
							<c:choose>
							<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
								<option value="20">
									<spring:theme code="store.km.20"/>
								</option>
								<option value="50">
									<spring:theme code="store.km.50"/>
								</option>
								<option value="100">
								    <spring:theme code="store.km.100"/>
								</option>
							</c:when>
							<c:otherwise>
								<option value="20">
									<spring:theme code="store.miles.20"/>
								</option>
								<option value="50">
									<spring:theme code="store.miles.50"/>
								</option>
								<option value="100">
									<spring:theme code="store.miles.100"/>
								</option>
							</c:otherwise>
							</c:choose>
							</select>
						</div>
							
						
						
						
						
						<div class="col-xs-12 p-t-15 nearby_branches">
						</div>
						<div class="col-xs-12 text-center">
							<span class="nearby_arrow"> ></span>
						</div>
					</div>
					<div class="hidden">
						<div class="text-primary bold header_instock_status">
							<common:globalIcon iconName="checkmarkIcon" iconFill="#fff" iconColor="#78a22f" width="16" height="16" viewBox="0 0 24 23" display="m-r-5" />
							<spring:theme code="product.variants.in.stock"/>
						</div>
						<div class="text-orange header__status">
							<common:globalIcon iconName="checkmarkIcon" iconFill="#fff" iconColor="#ED8606" width="16" height="16" viewBox="0 0 24 23" display="m-r-5" />
							<spring:theme code="headerstoreoverlay.availableforbackorder"/>
						</div>
						<div class="text-primary header_instockforshipping_status">
							<common:globalIcon iconName="checkmarkIcon" iconFill="#fff" iconColor="#78a22f" width="16" height="16" viewBox="0 0 24 23" display="m-r-5" />
							<spring:theme code="headerstoreoverlay.availableforshippingonly"/>
						</div>
						<div class="text-green header_forceinstock_status">
							<common:globalIcon iconName="checkmarkIcon" iconFill="#fff" iconColor="#78a22f" width="16" height="16" viewBox="0 0 24 23" display="m-r-5" />
							<spring:theme code="product.invhit.availabletoorder"/>
						</div>
						<div class="text-dark-gray header_outofstock_status">
							<common:globalIcon iconName="crossMarkIcon" iconFill="none" iconColor="#5A5B5D" width="16" height="16" viewBox="0 0 35 35" display="m-r-5" />
							<spring:theme code="headerstoreoverlay.outofstock"/>
						</div>
						<div class="text-gray header_notavailable_status">
							<common:globalIcon iconName="crossMarkIcon" iconFill="none" iconColor="#5A5B5D" width="16" height="16" viewBox="0 0 35 35" display="m-r-5" />
							<spring:theme code="text.contact.your.branch"/>
						</div>
					</div>
					<div class="row margin0 p-t-20 saved_container">
						<div class="col-xs-12 f-s-18 f-w-500 font-Geogrotesque text-gray-1">
							<spring:theme code="headerstoreoverlay.favoritebranches"/>
						</div>
						<c:if test="${isAnonymous}">
							<div class="col-xs-12 guest_user ">
								<spring:theme code="headerstoreoverlay.gustuser.savedbranches"/>
							</div>
						</c:if>
						<div class="col-xs-12 p-t-15 saved_branches"></div>
						<div class="col-xs-12 text-center">
							<span class="saved_arrow"> ></span>
						</div>
					</div>
				</div>
			</div>
			<!-- Geo Location failed Starts -->
			<div id="failedgeolocationOverlay" style="display: none;">
				<div id="noGeoStoreResultsMsg"></div>
				<br/>
				<div class="cl"></div>
				<span class="hidden-xs">
					<br/>
				</span>
				<div id="geoSearchAgain" class="black-title store-specialty-heading" style="display:none">
					<label class="bold-text search-again-label" for="geoStorelocator-query">
						<spring:theme code="pickup.find.search.again"/>
					</label>
				</div>
				<div id="storeFinder_search"></div>
				<div class="row" id="geo-store-heading">
					<div class="headline">
						<spring:theme code="text.failedlocation-title"/>
					</div>
					<p class="margin20">
						<spring:theme code="text.failedlocation-intro"/>
					</p>
				</div>
				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="row">
							<form:form action="${homelink}store-finder/GeoPOS" method="get" modelAttribute="siteOneStorePopupForm">
								<ycommerce:testId code="storeFinder_search_box">
									<div class="store-search-input">
										<formElement:formInputBox idKey="geoStorelocator-query" labelKey="storelocator.query" path="q" labelCSS="sr-only" inputCSS="form-control js-geo-finder-search-input geoFinder" mandatory="true" placeholder="pickup.search.message" />
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
								<a href="${homelink}store-directory">
									<spring:theme code="storeFinder.store.directory" />&#8594;
								</a>
								<br/>
								<a href="${homelink}request-account/form">
									<spring:theme code="storeHeader.requestAccount" />&#8594;
								</a>
								<br/>
							</div>
						</div>
					</div>
				</div>
				<div id="geostoreResultsMsg" class="headline text-center"></div>
			</div>
			<!-- Geo Location failed Overlay Ends -->
		</div>
	</div>
</div>