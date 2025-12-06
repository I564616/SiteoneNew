<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="listSearchPageData" required="true" type="com.siteone.facade.ListSearchPageData" %>
<%@ attribute name="searchType" required="false" type="java.lang.String" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<div class="row list-filter-popup f-s-26 f-s-20-xs-px text-bold text-default font-Geogrotesque">
	<button class="text-green list-filter-popup-close" onclick="ACC.myquotes.filterPopup('hide', 500, '.list-filter-popup')"><common:globalIcon iconName="close" iconFill="none" iconColor="#78A22F" width="15" height="16" viewBox="0 0 12 12" display="" /></button>
	<div class="b-b-grey f-s-20 text-center font-GeogrotesqueSemiBold m-b-15 p-b-20 list-filter-popup-header">
		<common:globalIcon iconName="filter" iconFill="#f90" iconColor="#222222" width="16" height="17" viewBox="0 0 16 17" display="hidden m-r-15" />
		<spring:theme code="myquotes.filter"/>
	</div>
	<div class="m-t-15 scroll-bar list-filter-popup-body">
		<div class="col-md-12 b-b-grey p-b-10">
			Search
			<div class="from-input">
			  <input id="filter-Search" data-listfilter="searchfilter" data-status="" type="text" class="form-control m-b-15 m-t-10 m-y-10-xs list-filter-search" name="company name" value="${not empty listSearchPageData.freeTextSearch && listSearchPageData.freeTextSearch ne null? listSearchPageData.freeTextSearch : ''}" />
			  <label class="from-input-label text-muted italic-text f-s-16 f-s-14-xs">Search by #Itemnumber</label>
			  <label class="from-input-label text-muted italic-text f-s-16 f-s-14-xs">Search by #Description</label>
			</div>
		</div>
		<div class="col-md-12 b-b-grey p-y-25 p-y-20-xs pointer list-fliter-options hidden" onclick="ACC.global.openCloseAccordion(this,'close',1,'saved-list-filter', 'chevron-down,chevron-up')" data-acconum="1">
			Availability
			<span class="f-s-13 glyphicon glyphicon-chevron-down m-t-10 pull-right"></span>
		</div>
		<div class="col-md-12 b-b-grey f-s-18 f-w- font-Arial p-b-10 p-t-20 f-s-16-xs-px saved-list-filter-data-1" style="display: none;">
			<c:set var="selectName" value="availability" />
			<c:forEach var="i" begin="1" end="2" >
				<label class="row flex-center p-t-5 p-b-5 transition-3s text-dark-gray pointer" for="filter-${selectName}-${i}">
					<div class="col-xs-9">${i==1? 'In Stock' : 'Out of Stock'}</div>
					<div class="col-xs-2 text-right f-s-16 p-r-15 text-gray font-small-xs p-t-5-xs">${500 - i*163}</div>
					<div class="col-xs-1 p-l-0 radio-button-circle"><input data-status="" data-listfilter="${i==1? 'In Stock' : 'Out of Stock'}" id="filter-${selectName}-${i}" name="filter-${selectName}" type="radio" value="${i==1?'Available':'Out Of Stock'}"></div>
				</label>
			</c:forEach>
		</div>
		<c:set var="isFirstLOne" value="${true}" />
		<c:forEach items="${listSearchPageData.facets}" var="facetData" varStatus="loop" >
			<c:set var="parentName" value="${not empty facetData.name && facetData.name ne null? fn:trim(facetData.name) : 'ABC'}" />
			<c:set var="selectName" value="${parentName}" />
			<c:set var="num" value="${loop.index + 2}" />
			<c:choose>
				<c:when test="${parentName == 'Filter by Category'}">
					<c:forEach items="${facetData.values}" var="facetValue" varStatus="index" >
						<c:set var="i" value="${index.index + 2}" />
						<c:set var="count" value="${not empty facetValue.count && facetValue.count ne null? facetValue.count : 1}" />
						<c:set var="categoryName" value="${not empty facetValue.name && facetValue.name ne null? fn:trim(facetValue.name) : 'XYZ'}" />
						<c:set var="categoryCode" value="${not empty facetValue.code && facetValue.code ne null? fn:trim(facetValue.code) : 'SH11'}" />
						<c:choose>
							<c:when test='${categoryCode == "SH10" || categoryCode == "SH11" || categoryCode == "SH12" || categoryCode == "SH13" || categoryCode == "SH14" || categoryCode == "SH15" || categoryCode == "SH16" || categoryCode == "SH17"}'>
								<c:set var="num" value="${index.index + 3}" />
								<c:if test='${!isFirstLOne}'>
									</div>
								</c:if>
								<c:set var="isFirstLOne" value="${false}" />
								<c:set var="selectName" value="${categoryCode}" />
								<div class="col-md-12 b-b-grey p-y-25 p-y-20-xs pointer list-fliter-options" onclick="ACC.global.openCloseAccordion(this,'close',${num},'saved-list-filter', 'chevron-down,chevron-up')" data-acconum="${num}">
									${categoryName}
									<span class="f-s-13 glyphicon glyphicon-chevron-down m-t-10 pull-right"></span>
								</div>
								<div class="col-md-12 b-b-grey f-s-18 f-w- font-Arial p-b-10 p-t-20 f-s-16-xs-px saved-list-filter-data-${num}" style="display: none;">
									<label class="row flex-center p-t-5 p-b-5 transition-3s text-dark-gray pointer" for="filter-${selectName}">
										<div class="col-xs-9">Select All</div>
										<div class="col-xs-2 text-right f-s-16 p-r-15 text-gray font-small-xs p-t-5-xs">${count}</div>
										<div class="col-xs-1 p-l-0"><input data-isParent="${true}" data-category="${selectName}" data-name="${categoryCode}" data-listfilter="${categoryName}" data-status="" aria-label="Checkbox" id="filter-${selectName}" class="green-check filter-category-checkbox" onchange="ACC.savedlist.filterPopupSelect(this, 'all', '${selectName}')" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} ></div>
									</label>
							</c:when>
							<c:otherwise>
								<label class="row flex-center p-t-5 p-b-5 transition-3s text-dark-gray pointer" for="filter-${selectName}-${i}">
									<div class="col-xs-9">${categoryName}</div>
									<div class="col-xs-2 text-right f-s-16 p-r-15 text-gray font-small-xs p-t-5-xs">${count}</div>
									<div class="col-xs-1 p-l-0"><input data-isParent="${false}" data-category="${selectName}" data-name="${categoryCode}" data-listfilter="${categoryName}" data-status="" aria-label="Checkbox" id="filter-${selectName}-${i}" class="green-check filter-category-checkbox" onchange="ACC.savedlist.filterPopupSelect(this, 'item', '${selectName}')" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} ></div>
								</label>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-md-12 b-b-grey p-y-25 p-y-20-xs pointer list-fliter-options" onclick="ACC.global.openCloseAccordion(this,'close',${num},'saved-list-filter', 'chevron-down,chevron-up')" data-acconum="${num}">
						${parentName}
						<span class="f-s-13 glyphicon glyphicon-chevron-down m-t-10 pull-right"></span>
					</div>
					<div class="col-md-12 b-b-grey f-s-18 f-w- font-Arial p-b-10 p-t-20 f-s-16-xs-px saved-list-filter-data-${num}" style="display: none;">
						<c:forEach items="${facetData.values}" var="facetValue" varStatus="index" >
							<c:set var="i" value="${index.index + 1}" />
							<c:set var="count" value="${not empty facetValue.count && facetValue.count ne null? facetValue.count : 1}" />
							<c:set var="categoryName" value="${not empty facetValue.name && facetValue.name ne null? fn:trim(facetValue.name) : 'XYZ'}" />
							<c:set var="categoryCode" value="${not empty facetValue.code && facetValue.code ne null? fn:trim(facetValue.code) : 'SH11'}" />
							<label class="row flex-center p-t-5 p-b-5 transition-3s text-dark-gray pointer" for="filter-${selectName}-${i}">
								<div class="col-xs-9">${categoryName}</div>
								<div class="col-xs-2 text-right f-s-16 p-r-15 text-gray font-small-xs p-t-5-xs">${count}</div>
								<div class="col-xs-1 p-l-0"><input data-isParent="${false}" data-category="${selectName}" data-name="${categoryCode}" data-listfilter="${categoryName}" data-status="" aria-label="Checkbox" id="filter-${selectName}-${i}" class="green-check filter-category-checkbox" onchange="ACC.savedlist.filterPopupSelect(this, 'item', '${selectName}')" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} ></div>
							</label>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<div class="padding-20 pad-md-rgt-30 bg-white list-filter-popup-footer">
		<div class="col-md-7 col-xs-7">
			<button class="btn btn-default btn-block font-Geogrotesque-bold" onclick="ACC.savedlist.resetListFilter('listfilter', '.list-filter-popup')"><spring:theme code="myquotes.clear.all.filter"/></button>
		</div>
		<div class="col-md-5 col-xs-5">
			<button class="btn btn-primary btn-block font-Geogrotesque-bold" onclick="ACC.savedlist.updateListFilter()">Apply</button>
		</div>
	</div>
</div>