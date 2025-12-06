<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="userLocation" required="true" type="de.hybris.platform.acceleratorservices.store.data.UserLocationData" %>
<%@ attribute name="searchPageData" required="false" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>
<%@ attribute name="cmsPage" required="false" type="java.lang.String" %>
<%@ attribute name="pageUid" required="false" type="java.lang.String" %>
<%@ attribute name="toggle" required="false" type="java.lang.String" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>


<c:url value="/store-finder" var="searchUserLocationUrl"/>
<c:url value="/store-finder/position" var="autoUserLocationUrl"/>

<c:set var = "checkNearbyOn" value = "false"/>
<c:set var = "nearbyStoresFound" value = "false"/>

<c:forEach items="${sessionNearbyStores}" var="nearbyStoreValue" varStatus="loop">
	<c:if test="${nearbyStoreValue.isNearbyStoreSelected && nearbyStoreValue.storeId ne sessionStore.storeId}">
		<c:set var = "checkNearbyOn" value = "true"/>
	</c:if>
	<c:if test="${nearbyStoreValue.storeId ne sessionStore.storeId}">
		<c:set var = "nearbyStoresFound" value = "true"/>
	</c:if>
</c:forEach>

<input type="hidden" name="curatedplppagename" id="curatedplppagename" value="${cmsPage}">
<input type="hidden" name="curatedplppageid" id="curatedplppageid" value="${pageUid}">
<input type="hidden" name="curatedplptoggle" id="curatedplptoggle" value="${toggle}">
<input type="hidden" name="isNurseryBuyingGroupBranch" value="${isNurseryBuyingGroupBranch}">

<c:set var="isNurseryBuyingGroupBranchFe" value="false" />
<input type="hidden" id="breadcrumbCategry${loop.index}" value="${fn:escapeXml(breadcrumbs[1].name)}"/>
<c:set var="isNurseryCategory" value="false" />
<c:set var="nbgl1category" value="${fn:escapeXml(breadcrumbs[1].name)}" />


<c:if test="${nbgl1category eq 'Nursery' ||  nbgl1category eq 'Vivero'}" >
	<c:set value="true" var="isNurseryCategory" />
</c:if>
<input type="hidden" name="isNurseryCategory" value="${isNurseryCategory}">

<c:if test="${not empty isNurseryBuyingGroupBranch && isNurseryBuyingGroupBranch ne null && isNurseryCategory }" >
	<c:set value="${isNurseryBuyingGroupBranch}" var="isNurseryBuyingGroupBranchFe"></c:set>
</c:if>
<input type="hidden" name="isNurseryBuyingGroupBranch" value="${isNurseryBuyingGroupBranch}">
<input type="hidden" name="isNurseryBuyingGroupBranchFe" value="${isNurseryBuyingGroupBranchFe}">
<input name="isNurseryBuyingGroupBranchs" value="${sessionNurseryNearbyStores[0].displayName}" type="hidden">

<ycommerce:testId code="facetNav_title_inStock">
	<div class="instock-heading">
		<spring:theme code="product.PLP.instock" />&nbsp;<spring:theme code="facet.todayat.text" />
	</div>
	<div class="facet js-facet js-shop-stores-facet">
		<div class='facet__values js-facet-values js-facet-form' id="facetsinStock">
			<div class="facet__results js-facet-container" role="group" aria-label="inStock">
				<ul class="facet__list js-facet-list indent">
					<li>
						<form action="#" method="get" class="storeFilterForm">
							<input type="hidden" id="availableInStoreQ" name="q" value="${pageData.currentQuery.query.value}"/>
                            <input type="hidden" name="text" value="${pageData.freeTextSearch}"/>
							<input type="hidden" class="viewtype" name="viewtype" value="All">
							<input type="hidden" name="sort" value="${param.sort}">
							<input type="hidden" name="nearbyDisabled" value="">
							<div class="instock-sec">
								<label class="${inStockAtNearbyStores ? 'storesEnabled' : 'storesDisabled'}">
									<input name="inStock" class="facet__list__checkbox js-facet-checkbox js-facet-checkbox-stores sr-only"
										type="checkbox" ${(not empty param.inStock) ? 'checked="checked"' : '' }>
									<span class="facet__list__label">
										<span class="facet__list__mark"></span>
										<span class="facet__list__text flex-center justify-between" style="text-transform: initial;">
											<span>
												${sessionStore.address.town}&nbsp;<spring:theme code="and.nearby.text"/>
											</span>
											<span class="store-count"><span class="js-storeavailable-count">${availableInStoresCount}</span></span>
										</span>
									</span>
								</label>
							</div>
							<div class="facet__list__nearby">
								<div class="facet__list__nearby" style="position:relative">
									<label class="${(expressShippingAvailable && segmentLevelShippingEnabled) ? 'storesEnabled' : 'storesDisabled'}"> 
									<input name="expressShipping" class="js__facet__expressShipping facet__list__checkbox sr-only" type="checkbox" ${(not empty param.expressShipping) ? 'checked="checked"' : ''} >
										<span class="facet__list__label">
											<span class="facet__list__mark"></span>
											<span class="facet__list__text flex-center justify-between" style="text-transform: initial;">
												<span><spring:theme code="product.express.shipping"/></span>
												<c:if test="${segmentLevelShippingEnabled}">
													<span class="shipping-count"><span class="js-shippingavailable-count">${shippingAvailableCount}</span></span>
												</c:if>
											</span>
										</span>													
									</label>						
								</div>
								
								<div class="check-nearby-branches-section nearby_locale js-change-branch-btn">
									<button type="button" class="btn btn-default btn-block check-nearby-branches flex-center justify-between">
										<span><spring:theme code="check.nearby.branches"/></span>
										 <common:angleRight_checkotherbranch />
									</button>
								</div>
								<div class="nearby_more_locations">
									<span class="nearby_more_locations_text">You're shopping ${sessionStore.address.town}, ${sessionStore.address.region.isocodeShort}</span>
									<span><a href="<c:url value="/store-finder"/>" id="change_preferred"><spring:theme code="change.preferred.branch"/></a></span>
									<ul class="nearby_store_list here">
										<c:choose>
											<c:when test="${(nearbyStoresFound)}">
												<li class="search_nearby_enabled m-b-10">
													<input name="nearby" class="facet__list__checkbox js-facet-checkbox sr-only" type="checkbox" ${(checkNearbyOn) ? 'checked="checked"' : ''}>
													<span class="facet__list__mark"></span>
													&nbsp;<spring:theme code="all.check.nearby.branches"/>
												</li>
												<input name="selectedNearbyStores" value="<c:forEach items="${sessionNearbyStores}" var="nearbyStoreValue" varStatus="loop" step="1" begin="1"><c:if test="${nearbyStoreValue.isNearbyStoreSelected}">${nearbyStoreValue.storeId}<c:if test="${!loop.last}">,</c:if></c:if></c:forEach>"  type="hidden">
												<c:if test="${!isNurseryBuyingGroupBranchFe}">
													<c:forEach items="${sessionNearbyStores}" var="nearbyStoreValue" varStatus="loop" step="1" begin="1">
														<li class="list-of-nearby-stores-plp" data-branch-name="${nearbyStoreValue.displayName}">
															<input name="" value="${nearbyStoreValue.storeId}" ${(nearbyStoreValue.isNearbyStoreSelected) ? 'checked="checked" class="nl-enabled' : 'class="'} facet__list__checkbox js-facet-checkbox js-nearby-search-filter sr-only" type="checkbox">
															<span class="facet__list__mark"></span>
															
															<c:set var="distanceUnit" value="miles.text" />
															<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
																<c:set var="distanceUnit" value="km.text" />
															</c:if>
															${nearbyStoreValue.displayName}&nbsp; <strong>${nearbyStoreValue.formattedDistance}&nbsp;<spring:theme code="${distanceUnit}"/></strong>
														</li>
													</c:forEach>
												</c:if>
												<c:if test="${isNurseryBuyingGroupBranchFe}">
													<c:forEach items="${sessionNurseryNearbyStores}" var="nearbyNurseryStoreValue" varStatus="loop" step="1" begin="1">
														<li class="list-of-nearby-stores-plp" data-branch-name="${nearbyNurseryStoreValue.displayName}">
															<input name="" value="${nearbyNurseryStoreValue.storeId}" ${(nearbyNurseryStoreValue.isNearbyStoreSelected) ? 'checked="checked" class="nl-enabled' : 'class="'} facet__list__checkbox js-facet-checkbox js-nearby-search-filter sr-only" type="checkbox">
															<span class="facet__list__mark"></span>
															${nearbyNurseryStoreValue.displayName}
														</li>
													</c:forEach>
												</c:if>
												<button id="nearby_apply_btn" class="btn btn-primary nearby_apply_btn">Apply</button>
											</c:when>
											<c:otherwise>
												<li>
													<span>
														<c:choose>
															<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
																<spring:theme code="no.nearby.store.msg.km"/>
															</c:when>
															<c:otherwise>
																<spring:theme code="no.nearby.store.msg"/>
															</c:otherwise>
														</c:choose>
													</span>
												</li>
											</c:otherwise>
										</c:choose>
									</ul>
								</div>
								
								
							</div>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
</ycommerce:testId>
