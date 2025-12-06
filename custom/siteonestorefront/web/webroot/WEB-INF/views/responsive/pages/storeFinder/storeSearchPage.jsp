<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>


	
	<c:if test="${searchedStore ne null}">
	{
	"searchedStore"	: "${searchedStore}"
	}
	</c:if>
	
	<c:if test="${searchedStore eq null}">
	{	
	<c:if test="${pointOfServiceDataSelected ne null}">
	"searchedStore":
		{"data":[
		<c:set value="${ycommerce:storeImage(pos, 'cartIcon')}" var="storeImage"/>
		<c:url value="${pointOfServiceDataSelected.url}" var="storeUrl" scope="request"/>
		{
			"displayName" : "${pointOfServiceDataSelected.displayName}",
			"name" : "${pointOfServiceDataSelected.name}",
			"url" : "${pointOfServiceDataSelected.url}",
			 "phone" : "${pointOfServiceDataSelected.address.phone}", 
			"formattedDistance" : "${pointOfServiceDataSelected.formattedDistance}",
			"line1" : "${pointOfServiceDataSelected.address.line1}",
			"line2" : "${pointOfServiceDataSelected.address.line2}",
			"town" : "${pointOfServiceDataSelected.address.town}",
			"regionCode" : "${pointOfServiceDataSelected.address.region.isocodeShort}",
			"postalCode" : "${pointOfServiceDataSelected.address.postalCode}",
			"latitude" : "${pointOfServiceDataSelected.geoPoint.latitude}",
			"longitude" : "${pointOfServiceDataSelected.geoPoint.longitude}",
			"storeStatus" : "${pointOfServiceDataSelected.storeStatus}",
			"storeId"     : "${pointOfServiceDataSelectedpointOfServiceDataSelected.storeId}",
			"isPreferredStore" : "${pointOfServiceDataSelected.isPreferredStore}",
			"title"       : "${pointOfServiceDataSelected.title}",
			"storeNotes"       : "${pointOfServiceDataSelected.storeNotes}",
			"regionName"    :"${pointOfServiceDataSelected.address.region.name}",
			"enableOnlineFulfillment" : "${pointOfServiceDataSelected.enableOnlineFulfillment}",
			<c:if test="${not empty pointOfServiceDataSelected.openingHours}">
				"openings":<store:openingSchedule openingSchedule="${pointOfServiceDataSelected.openingHours}" />
			</c:if>
			<c:if test="${not empty pointOfServiceDataSelected.storeSpecialityDetails}">
				"storeSpecialityDetails" :[
					<c:forEach items="${pointOfServiceDataSelected.storeSpecialityDetails}" var="storeSpecialityDetails" varStatus="storeSpecialty">
						{"name":"${storeSpecialityDetails.name}",
						"icon":"${storeSpecialityDetails.icon.url}"
						}<c:if test="${!storeSpecialty.last}">,</c:if>
					</c:forEach>
					],
			</c:if>
			<c:if test="${not empty pointOfServiceDataSelected.features}">
				"features" :[
						<c:forEach items="${pointOfServiceDataSelected.features}" var="feature" varStatus="featureNumber">
							"${feature.value}"<c:if test="${!featureNumber.last}">,</c:if>
						</c:forEach>
						],
					
			</c:if>
			"image" : "${storeImage.url}",
			"stockDetail" : "${pointOfServiceDataSelected.stockDetail}"
		}
	]}
	</c:if>
	
	<c:if test="${pointOfServiceDataSelected eq null and searchPageData ne null and !empty searchPageData.results}">
	"nearBy":
		{"total":${searchPageData.pagination.totalNumberOfResults},"data":[
	<c:forEach items="${searchPageData.results}" var="pos" varStatus="loopStatus">
		<c:set value="${ycommerce:storeImage(pos, 'cartIcon')}" var="storeImage"/>
		<c:url value="${pos.url}" var="storeUrl" scope="request"/>
		{
			"displayName" : "${pos.displayName}",
			"name" : "${pos.name}",
			"url" : "${pos.url}",
			 "phone" : "${pos.address.phone}", 
			"formattedDistance" : "${pos.formattedDistance}",
			"line1" : "${pos.address.line1}",
			"line2" : "${pos.address.line2}",
			"town" : "${pos.address.town}",
			"regionCode" : "${pos.address.region.isocodeShort}",
			"postalCode" : "${pos.address.postalCode}",
			"latitude" : "${pos.geoPoint.latitude}",
			"longitude" : "${pos.geoPoint.longitude}",
			"storeStatus" : "${pos.storeStatus}",
			"storeId"     : "${pos.storeId}",
			"isPreferredStore" : "${pos.isPreferredStore}",
			"title"       : "${pos.title}",
			"storeNotes"       : "${pos.storeNotes}",
			"regionName"    :"${pos.address.region.name}",
			"enableOnlineFulfillment" : "${pos.enableOnlineFulfillment}",
			<c:if test="${not empty pos.openingHours}">
				"openings":<store:openingSchedule openingSchedule="${pos.openingHours}" />
			</c:if>
			<c:if test="${not empty pos.storeSpecialityDetails}">
				"storeSpecialityDetails" :[
					<c:forEach items="${pos.storeSpecialityDetails}" var="storeSpecialityDetails" varStatus="storeSpecialty">
						{"name":"${storeSpecialityDetails.name}",
						"icon":"${storeSpecialityDetails.icon.url}"
						}<c:if test="${!storeSpecialty.last}">,</c:if>
					</c:forEach>
					],
			</c:if>
			<c:if test="${not empty pos.features}">
				"features" :[
						<c:forEach items="${pos.features}" var="feature" varStatus="featureNumber">
							"${feature.value}"<c:if test="${!featureNumber.last}">,</c:if>
						</c:forEach>
						],
					
			</c:if>
			"image" : "${storeImage.url}",
			"stockDetail" : "${pos.stockDetail}"
		}<c:if test="${!loopStatus.last}">,</c:if>
	</c:forEach>
	]}
	</c:if>
	<c:if test="${(pointOfServiceDataSelected ne null or (searchPageData ne null and !empty searchPageData.results)) and pointOfServiceData ne null and !empty pointOfServiceData}">
	,
	</c:if>
	<c:if test="${pointOfServiceData ne null and !empty pointOfServiceData}">
	"savedBranches":
	{"total":${pointOfServiceData.size()},"data":[
	<c:forEach items="${pointOfServiceData}" var="pos" varStatus="loopStatus">
		<c:set value="${ycommerce:storeImage(pos, 'cartIcon')}" var="storeImage"/>
		<c:url value="${pos.url}" var="storeUrl" scope="request"/>
		{
			"displayName" : "${pos.displayName}",
			"name" : "${pos.name}",
			"url" : "${pos.url}",
			 "phone" : "${pos.address.phone}", 
			"formattedDistance" : "${pos.formattedDistance}",
			"line1" : "${pos.address.line1}",
			"line2" : "${pos.address.line2}",
			"town" : "${pos.address.town}",
			"regionCode" : "${pos.address.region.isocodeShort}",
			"postalCode" : "${pos.address.postalCode}",
			"latitude" : "${pos.geoPoint.latitude}",
			"longitude" : "${pos.geoPoint.longitude}",
			"storeStatus" : "${pos.storeStatus}",
			"storeId"     : "${pos.storeId}",
			"isPreferredStore" : "${pos.isPreferredStore}",
			"title"       : "${pos.title}",
			"storeNotes"       : "${pos.storeNotes}",
			"regionName"    :"${pos.address.region.name}",
			"enableOnlineFulfillment"	: "${pos.enableOnlineFulfillment}",
			<c:if test="${not empty pos.openingHours}">
				"openings":<store:openingSchedule openingSchedule="${pos.openingHours}" />
			</c:if>
			<c:if test="${not empty pos.storeSpecialityDetails}">
				"storeSpecialityDetails" :[
					<c:forEach items="${pos.storeSpecialityDetails}" var="storeSpecialityDetails" varStatus="storeSpecialty">
						{"name":"${storeSpecialityDetails.name}",
						"icon":"${storeSpecialityDetails.icon.url}"
						}<c:if test="${!storeSpecialty.last}">,</c:if>
					</c:forEach>
					],
			</c:if>
			<c:if test="${not empty pos.features}">
				"features" :[
						<c:forEach items="${pos.features}" var="feature" varStatus="featureNumber">
							"${feature.value}"<c:if test="${!featureNumber.last}">,</c:if>
						</c:forEach>
						],
					
			</c:if>
			"image" : "${storeImage.url}",
			"stockDetail" : "${pos.stockDetail}"
		}<c:if test="${!loopStatus.last}">,</c:if>
	</c:forEach>
	]}
	</c:if>
	}
	</c:if>

