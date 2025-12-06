<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
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
<div class="row invoice-filter-popup f-s-26 f-s-20-xs-px text-bold text-default font-Geogrotesque">
	<button class="text-green invoice-filter-popup-close" onclick="ACC.myquotes.filterPopup('hide', 500, '.invoice-filter-popup')"><common:globalIcon iconName="close" iconFill="none" iconColor="#78A22F" width="15" height="16" viewBox="0 0 12 12" display="" /></button>
	<div class="b-b-grey f-s-20 text-center font-GeogrotesqueSemiBold p-b-25 list-filter-popup-header">
		<common:globalIcon iconName="filter" iconFill="#f90" iconColor="#222222" width="16" height="17" viewBox="0 0 16 17" display="hidden m-r-15" />
		Sort
	</div>
	<div class="scroll-bar invoice-filter-popup-body">
		<div class="col-md-12 b-b-grey p-y-20 p-l-20 p-r-25 pointer invoice-fliter-options" onclick="ACC.global.openCloseAccordion(this,'open',1,'invoice-list-filter', 'chevron-down,chevron-up')" data-acconum="1">Sort by<span class="f-s-15 glyphicon glyphicon-chevron-up m-t-10 pull-right"></span></div>
		<div class="col-md-12 b-b-grey f-s-18 f-w- font-Arial text-capitalize p-t-15 p-b-10 p-l-20 p-r-0 f-s-16-xs-px invoice-list-filter-data-1">
			<c:set var="selectName" value="sort" />
			<c:forEach items="${searchPageData.sorts}" var="sort">
				<label class="row flex-center p-t-5 p-b-5 transition-3s pointer" for="filter-${selectName}">
					<div class="col-xs-11">${sort.name}</div>
					<div class="col-xs-1 p-l-0 p-r-10 radio-button-circle text-align-right"><input data-selected="${sort.selected}" class="m-r-0-xs-imp" data-status="" data-listfilter="${sort.name}" id="filter-${selectName}" name="filter-${selectName}" type="radio" value="${sort.code}" ${searchPageData.pagination.sort == sort.code? 'checked' : ''}></div>
				</label>
			</c:forEach>
		</div>
		<div class="col-md-12 b-b-grey p-y-20 p-l-20 p-r-25 pointer invoice-fliter-options hidden" onclick="ACC.global.openCloseAccordion(this,'close',2,'invoice-list-filter', 'chevron-down,chevron-up')" data-acconum="2">
			2
			<span class="f-s-15 glyphicon glyphicon-chevron-down m-t-10 pull-right"></span>
		</div>
		<div class="col-md-12 b-b-grey f-s-18 f-w- font-Arial p-y-15 p-l-20 p-r-0 f-s-16-xs-px invoice-list-filter-data-2" style="display: none;">
			2
		</div>
	</div>
	<div class="padding-20 pad-md-rgt-30 bg-white invoice-filter-popup-footer">
		<div class="col-md-7 col-xs-6 p-l-0">
			<button class="btn btn-default btn-block no-margin font-Geogrotesque-bold" onclick="ACC.myquotes.filterPopup('hide', 500, '.invoice-filter-popup')"><spring:theme code="myquotes.clear.all.filter"/></button>
		</div>
		<div class="col-md-5 col-xs-6 p-r-0">
			<button class="btn btn-primary btn-block no-margin font-Geogrotesque-bold" onclick="ACC.invoicepage.invoiceSortFilterApply()">Apply</button>
		</div>
	</div>
</div>