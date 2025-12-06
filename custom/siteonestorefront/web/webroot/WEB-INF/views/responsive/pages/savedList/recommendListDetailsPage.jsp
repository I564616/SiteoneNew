<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<template:page pageTitle="${not empty pageTitle && pageTitle ne null ? pageTitle : ''}">
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "mobileFlag" value = "false"/>
<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
<!-- If Mobile -->
<c:set var = "mobileFlag" value = "true"/>
</c:if>
<!-- Recommended List -->
<c:set var="productListDataSize" value="${not empty productListData && productListData ne null ? productListData.size() : 0 }" />
<c:set var="categoryName" value="${not empty CategoryNameData && CategoryNameData ne null ? CategoryNameData : '' }" />
<div class="recomannded-page">
    <div class="row margin0 p-t-50 p-b-25 p-y-15-xs text-center-xs">
        <div class="col-md-12 f-s-28 f-s-24-xs-px p-l-0 text-bold text-default font-Geogrotesque"><spring:theme code="text.lists.recommended.listNameYour" />&nbsp; ${categoryName} &nbsp;<spring:theme code="text.lists.recommended.listNameList" /></div>
        <div class="col-md-12 f-s-18 font-14-xs p-l-0 m-b-10 m-t-10-xs text-default"><spring:theme code="text.lists.recommendedDetails.paragraph" /></div>
        <div class="col-md-12 f-s-12 p-l-0 m-b-10-xs m-t-25-xs bold-text text-gray text-uppercase hidden"><spring:theme code="text.lists.recommendedDetails.intemsinfo" /></div>
    </div>
    <section class="filter-tags m-b-15"></section>
    <section class="row margin0 m-xs-hrz--20 text-white f-s-12 hidden-print js-sticky-listpage">
        <div class="container-lg">	
            <div class="flex-center bg-off-grey add-border-radius b-radius-0-xs">
                <div class="col-md-3 p-l-0">
                    <label class="btn select_Listall hidden-xs hidden-sm" htmlfor="SelectedProductAll">
                        <span class="colored"><input aria-label="Checkbox" id="selectedProdTypeAll" class="select_product_checkbox_all" type="checkbox" value="SelectedProductAll"/></span>
                    </label>
                    <div class="btn p-a-25 b-r-grey bold controls-mobile">
                        <span class="hidden-xs hidden-sm"><spring:theme code="text.lists.recommended.actions" /><i class="glyphicon glyphicon-chevron-down p-l-5 f-s-10"></i></span>
                        <span class="hidden-md hidden-lg">
                            <svg xmlns="http://www.w3.org/2000/svg" id="Controls_-_Mobile" data-name="Controls - Mobile" width="24.667" height="6" viewBox="0 0 24.667 6">
                                <circle cx="3" cy="3" r="3" fill="#ffffff"/>
                                <circle cx="3" cy="3" r="3" transform="translate(9.333)" fill="#ffffff"/>
                                <circle cx="3" cy="3" r="3" transform="translate(18.667)" fill="#ffffff"/>
                            </svg>
                        </span>
                    </div>
                    <div class="row p-x-15 paddingTopB10 bold elemOverlayParent list-filters-row hidden">
                        <div class="elemOverlay hidden"></div>
                        <div class="col-md-12 p-l-0 paddingTopB10 hidden-md hidden-lg list-filters-link" onclick="ACC.savedlist.selectAllList(this)"><spring:theme code="text.lists.recommendedDetails.selectAll" /></div>
                        <div class="col-md-12 p-l-0 p-b-10 list-filters-link de_selected_filter disabled" onclick="ACC.savedlist.deSelectAllList(this)"><spring:theme code="text.lists.recommendedDetails.deselectAll" /></div>
                        <div class="col-md-12 hidden paddingTopB10 list-filters-link disabled SavedSelectedProduct${orderOnlinePermissions eq true? '' : '2'}" id="${orderOnlinePermissions eq true? '' : 'orderOnlineATC'}"><spring:theme code="saveListDetailsPage.selected.to.cart" /></div>
                        <div class="col-md-12 p-l-0 m-b-5 list-filters-link moveselected_list_filter disabled" onclick="ACC.savedlist.recommendedCopyList('${categoryName}',${productListDataSize},'selectedCopyList', this)" ><spring:theme code="text.lists.recommendedDetails.copySelectedToList" /></div>
                    </div>
                    <div class="btn p-a-25 b-r-grey recomannded-filter-btn">
                        <button class="bold p-a-10 recomannded-filter-box-button" onclick="ACC.global.toggleOffElems(this, '.recomannded-filter-box' , 'active' , '.list-filters-row')">Filter<i class="glyphicon glyphicon-chevron-down p-l-5 f-s-10"></i></button>
                        <button class="recomannded-filter-box-overlay" onclick="ACC.global.toggleOffElems(this, '.recomannded-filter-box' , 'active' , '.list-filters-row')"></button>
                        <div class="row text-align-left recomannded-filter-box">
                            <ul class="col-md-5 col-xs-5 padding0 nav nav-tabs" role="tablist">
                                <li role="presentation" class="f-s-16 font-Geogrotesque active"><button class="btn btn-block text-align-left" href="#r-brand" aria-controls="r-brand" role="tab" data-toggle="tab">Brand</button></li>
                                <li role="presentation" class="f-s-16 font-Geogrotesque"><button class="btn btn-block text-align-left" href="#r-category" aria-controls="r-category" role="tab" data-toggle="tab">${categoryName}</button></li>
                            </ul>
                            <div class="col-md-7 col-xs-7 tab-content">
                                <div role="tabpanel" class="mini-cart-scroll tab-pane active" id="r-brand">
                                    <div class="list-group">
                                        <label class="font-size-14 flex-center pointer transition-3s list-group-item list-group-item-hidden hidden" for="selected-brand0">
                                            <span class="colored-primary hidden-print p-r-10">
                                                <input aria-label="Checkbox" id="selected-brand0" class="text-align-center list-group-item-check" onchange="ACC.recommendedlist.filterSelectAllCheck(this, 0 , 'brand' , 'Select All')" type="checkbox" value="selected-brand0">
                                            </span>Select All<span class="list-group-item-badge"></span>
                                        </label>
                                    </div>
                                </div>
                                <div role="tabpanel" class="mini-cart-scroll tab-pane" id="r-category">
                                    <div class="list-group">
                                        <label class="font-size-14 flex-center pointer transition-3s list-group-item list-group-item-hidden hidden" for="selected-category0">
                                            <span class="colored-primary hidden-print p-r-10">
                                                <input aria-label="Checkbox" id="selected-category0" class="text-align-center list-group-item-check" onchange="ACC.recommendedlist.filterSelectAllCheck(this, 0 , 'category' , 'Select All')" type="checkbox" value="Select All">
                                            </span>Select All<span class="list-group-item-badge"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 p-l-0 f-s-15 hidden-xs hidden-sm"><span class="list-item-counter-text hidden"><spring:theme code="text.lists.recommendedDetails.youhave" />&nbsp;<span class="list-item-counter"></span> <spring:theme code="text.lists.recommendedDetails.item" /><span class="list-item-counter-s"></span> <spring:theme code="text.lists.recommendedDetails.selected" /></span></div>
                <div class="col-md-2 col-xs-5 pad-md-rgt-20 p-r-0-xs p-l-0-xs">
                    <button class="btn btn-block btn-gray m-t-0-xs-imp bold ${currentLanguage.isocode eq 'es' ? 'estimateBtnAlign-es' : ''} recomDetailCopyListBtn" data-toggle="popover" data-content="Click here to generate a customer facing estimate on your selected products. Our award winning estimate feature is FREE for all SiteOne customers!"  onclick="ACC.savedlist.recommendedCopyList('${categoryName}',${productListDataSize},'', this)"><spring:theme code="text.lists.recommended.copytoMyLists" /></button>
                   
                    <c:if test="${!mobileFlag}">
                        <button class="btn btn-block btn-gray m-t-0-xs-imp bold hidden ${currentLanguage.isocode eq 'es' ? 'estimateBtnAlign-es-copylist' : 'estimateBtnAlign-en-copylist'} recomDetailSelectedCopyListBtn"
                        onclick="ACC.savedlist.recommendedCopyList('${categoryName}',${productListDataSize},'selectedCopyList', this)"><spring:theme code="text.lists.recommendedDetails.copySelectedToLists" /></button>
                    </c:if>
                    <c:if test="${mobileFlag}">
                        <button class="btn btn-block btn-gray m-t-0-xs-imp bold hidden ${currentLanguage.isocode eq 'es' ? 'estimateBtnAlign-es-copylistMob' : ''} recomDetailSelectedCopyListBtn"
                        onclick="ACC.savedlist.recommendedCopyList('${categoryName}',${productListDataSize},'selectedCopyList', this)"><spring:theme code="text.lists.recommendedDetails.copySelectedToLists" /></button>
                    </c:if>
                </div>
                <div class="col-md-2 col-xs-10 p-l-0-xs hidden list_addlisttocart_wrapper addtocart addlisttocart-disabled">
                    <div class="addListToCart_filter_sec" id="addListToCart_filter">
                        <a href="${homelink}savedList/addListToCart?wishListCode=" class="btn btn-primary btn-block bold addListToCarts_filter paddingatlist ${currentLanguage.isocode eq 'es' ? 'addListToCarts-es' : ''}" disabled="disabled" data-toggle="popover" data-content="You have items in your list that are unable to be purchased online through your selected branch."><spring:theme code="saveListDetailsPage.list.to.cart" /></a>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="row hidden-md hidden-lg f-s-13 m-t-15 text-center list-item-counter-wrapper">
        <div class="col-md-12"><span class="list-item-counter-text hidden"><spring:theme code="text.lists.recommendedDetails.youhave" />&nbsp;<span class="list-item-counter"></span> <spring:theme code="text.lists.recommendedDetails.item" /><span class="list-item-counter-s"></span> <spring:theme code="text.lists.recommendedDetails.selected" /></span></div>
    </div>
    <c:forEach items="${productListData}" var="entry" varStatus="index">
        <div class="row margintop15 margin0 p-y-18 flex-center show-xs saved-list-sec${entry.isRegulatoryLicenseCheckFailed eq false? ' top-alert-msg' : ''}" data-nontransferableFlag="${entry.isTransferrable eq false and entry.isStockInNearbyBranch eq true}" data-outOfStockImage="${entry.outOfStockImage}" data-isTransferrable="${entry.isTransferrable}" data-isStockInNearbyBranch="${entry.isStockInNearbyBranch}" data-isRegulatoryLicenseCheckFailed="${entry.isRegulatoryLicenseCheckFailed}" data-rbrand="${entry.productBrandName eq null or entry.productBrandName eq '' ? 'No Brand' : entry.productBrandName}" data-rcategory="${entry.level2Category eq null or entry.level2Category eq '' ? 'No Category' : entry.level2Category}">
            <product:recommendedListproductListerItem indexvalue="${index.index}" productData="${entry}" />
        </div>
    </c:forEach>
</div>
<common:copyToListPopUp pageName="rec" className="recommendedlist" recomendedDetailPage="true"/>
<common:requestQuotePopupPLPPDP />
<!-- ./Recommended List -->
</template:page>