  
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<template:page pageTitle="${pageTitle}">
<c:set var="showGenerateLabel" value="<%=de.hybris.platform.util.Config.getParameter(\"storefront.savedlist.labelsheet.show\")%>"/>
<c:set var="showGenerateLabels" value="${fn:toLowerCase(showGenerateLabel)}"/>
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "mobileFlag" value = "false"/>
<c:set var = "requestQuoteList" value="${quotesFeatureSwitch eq true and account_classification ne 'retailer'}" />
<input type="hidden" class="requestQuoteList" value="${requestQuoteList}" />
<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
	<!-- If Mobile -->
	<c:set var = "mobileFlag" value = "true"/>
</c:if>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:choose>
<c:when test="${empty savedListData.code}">
<div class="row m-t-25">
	<div class="col-md-3">
		<div class="alert alert-danger">
     		<spring:theme code="saveListDetailsPage.list.notavailable"/>
		</div>
	</div>
</div>
</c:when>
<c:otherwise>
<c:set var="savedListName" value="${savedListData.name}"/>
<c:set var="savedListSize" value="${savedListData.entries.size()}"/>
<c:set var="searchUrl" value="/savedList/listDetails?code=${code}"/>
<spring:url value="/savedList/downloadListCSV?code=${code}" var="downloadCSVUrl" htmlEscape="false"/>
<spring:url value="/savedList/editList?code=${savedListData.code}" var="editListURL" htmlEscape="false"/> 
<div class="row p-y-15-xs hidden-print">
	<div class="col-md-8 col-xs-12 xs-center">
		<h1 class="headline list-headline">${savedListName}</h1>
		<span class="black-title"><strong><spring:theme code="text.savedList.description"/>:</strong></span>
		<c:choose>
			<c:when test="${not empty savedListData.description}">
				${savedListData.description}
			</c:when>
			<c:otherwise>
				<a href="/savedList/editList?code=${savedListData.code}"><spring:theme code="assemblyDetailsPage.add.desc"/></a>
			</c:otherwise>
		</c:choose>
		<p class="bold f-s-12 text-muted text-uppercase margin0 m-t-5"><span class="list-page-counter">${savedListSize}</span> ITEM${savedListSize > 1 ? 'S' : ''}<span class="m-l-5 m-r-5">|</span>EDITED <fmt:formatDate value="${savedListData.modifiedTime}" type="date" timeStyle="long" /></p>
 	</div>
	<!--Links on details page  -->
	<c:if test="${not empty savedListData.entries}">
		<div class="col-sm-12 col-xs-12 col-md-4 p-t-20 xs-center">
			<div class="btn-group btn-group-sm pull-right list-details-gruplinks" role="group" aria-label="...">
				<a href="#" class="btn btn-gp-link print-list" data-toggle="popover" data-content="<spring:theme code="assemblyDetailsPage.print" />">
					<common:globalIcon iconName="print" iconFill="none" iconColor="#50A0C5" width="13" height="13" viewBox="0 0 13 13" />
				</a>
				<a href="#" class="btn btn-gp-link" id="sharelistemail" data-toggle="popover" data-content="<spring:theme code="assemblyDetailsPage.email" />">
					<common:globalIcon iconName="email" iconFill="none" iconColor="#50A0C5" width="14" height="12" viewBox="0 0 14 12" />
					<input type="hidden" value="${savedListData.code}" id="savedListCode" />
				</a>
				<a href="#" class="btn btn-gp-link${savedListData.isModified? '' : ' disableOption'}" id="${savedListData.isModified? 'share_Saved_List' : ''}" data-toggle="popover" data-content="<spring:theme code="assemblyDetailsPage.share" />">
					<common:globalIcon iconName="share" iconFill="none" iconColor="#50A0C5" width="12" height="13" viewBox="0 0 12 13" />
				</a>
				<a href="${downloadCSVUrl}" class="btn btn-gp-link downloadListCSV" id="downloadlistcsv" data-toggle="popover" data-content="<spring:theme code="saveListDetailsPage.csv" />">
					<common:globalIcon iconName="download" iconFill="none" iconColor="#50A0C5" width="12" height="13" viewBox="0 0 12 13" />
				</a>
				<a href="${savedListData.isModified? editListURL : '#'}" class="btn btn-gp-link${savedListData.isModified? '' : ' disableOption'}" data-toggle="popover" data-content="<spring:theme code="assemblyDetailsPage.edit" />">
					<span class="glyphicon glyphicon-edit"></span>
				</a>
			</div>
			<div class="p-t-40 m-t-15 text-align-right xs-center p-t-25-xs m-t-0-xs">
				<c:if test="${searchPageData.pagination.totalNumberOfResults > 0 and savedListSize <= searchPageData.pagination.pageSize}">
					<p class="bold f-s-12 margin0 text-muted text-right display-ib valign-m xs-center hidden-print">
						<spring:theme code="saveListDetailsPage.list.total" /><br/>
						<span class="f-s-22 text-default margin0 text-right xs-center print-flex print-fs-14 print-m-0 print-p-0 cumulative-tot-price"><span class="hidden pad-rgt-10 print-flex"><spring:theme code="saveListDetailsPage.list.total" /></span><strong>${savedListData.listTotalPrice.formattedValue}</strong></span>	
					</p>
				</c:if>
				<c:if test="${not empty savedListData.entries}">
					<div class="b-l-grey m-l-15 p-l-15 display-ib width-150-px empty-listfilter-hide hidden">
						<button class="btn btn-white btn-block transition-3s no-margin-xs flex-center justify-center quote-filter-btn" onclick="ACC.myquotes.filterPopup('show',500,'.list-filter-popup')">
							<common:globalIcon iconName="filter" iconFill="#f90" iconColor="#78a22f" width="16" height="17" viewBox="0 0 16 17" display="m-r-10" />
							<span class="bold-text f-s-16-xs-px"><spring:theme code="myquotes.filter"/></span>
						</button>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>
	<!-- ./Links on detais page  -->
</div>
<input type="hidden" id="wishListListName" name="wishListListName" value="${savedListData.code}" />
<c:if test="${not empty savedListData.entries}">
  <section class="filter-tags m-b-15 m-t-25" style="display:none;"></section>
</c:if>
<c:if test="${empty savedListData.entries}">
	<section class="row hidden-print m-r-0 m-l-0 m-t-10 m-b-10 bg-light-grey border-grayish padding-20 p-15-xs">
		<div class="col-md-12 col-xs-12">
			<div class="row flex-center justify-center show-xs">
				<div class="col-md-2 f-s-19 f-w-500 font-Geogrotesque p-r-0 text-align-right text-capitalize text-default hidden-xs"><spring:theme code="text.savedList.addtoitemlist" />
				</div>
				<div class="col-md-6 col-xs-8 p-l-0-xs ">
				<c:choose>
					<c:when test="${savedListData.isModified}">
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />'id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
							</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" disabled="disabled" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />' id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
							</span>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
				<div class="col-md-1 flex-center plp-qty-container p-r-0 p-l-0 col-xs-4  p-r-0-xs">
					<div class="qtyHeading bg-white">QTY</div>
					<c:choose>
						<c:when test="${savedListData.isModified}">
							<input class="form-control" type="text" name="quantity" id="quantityId" maxlength="5" value="1" autocomplete="off">
						</c:when>
						<c:otherwise>
							<input class="form-control" disabled="disabled" type="text" name="quantity" id="quantityId" maxlength="5" value="1" autocomplete="off" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-2 col-xs-12 m-t-10-xs p-l-0-xs p-r-0-xs">
					<input type="button" value="Add" class="btn btn-white btn-block bold saved_add_to_cart_btn p-x-60-imp paddingTB10 ${savedListData.isModified?'':' disabled'}" id="add_Product_to_wishList">
				</div>
			</div>
		</div>
		<div class="col-md-offset-2 col-md-6 col-xs-12 flex-center p-t-5 p-l-0-xs p-r-0-xs p-l-70 f-s-12 f-w-700 text-red list-add-product-error list-add-product-error-box empty-savedlist-scenario" style="display: none;">
			<common:globalIcon iconName="exclamation" iconFill="none" iconColor="#E40101" iconColorSecond="white" width="16" height="14" viewBox="0 0 16 14" display="m-r-5" /><span class="p-t-3">Error:</span>&nbsp;
			<span id="base_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.base.variant"/></span>
			<span id="uom_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom"/></span>
			<span id="uom_hidden_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom.hidden"/></span>
			<span id="invalid_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.not.found"/></span>  
			<span id="empty_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.code.empty"/></span>  
			<span id="empty_quantity" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.quantity.empty"/></span>
		</div>
	</section>
</c:if>
<c:if test="${empty savedListData.entries}">
	<div class="row margin0">            
		<div class="col-md-12 p-a-15 b-t-grey bold">
			<spring:theme code ="savedList.pagination.noProductsAdded"/>
		</div>
	</div>
</c:if>
<div class="row">
	<div class="col-xs-6 printedTimeWrapper hidden show-print">
		<p id="printedTime"></p>
		<h3 class="font-Geogrotesque-bold marginTop10">${savedListName}</h3>
		<p><spring:theme code="saveListDetailsPage.print.items" />: <strong>${savedListEntryData.size()}</strong>
			<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
				<span class="padding10">|</span><spring:theme code="saveListDetailsPage.print.listtotal" />:
				<span class="cumulative-tot-price-print"><strong>${savedListData.listTotalPrice.formattedValue}</strong></span>
			</c:if>
		</p>
	</div>
</div>
<section class="row border-grey f-s-12 hidden-print m-xs-hrz--15 margin0 m-t-25 p-y-65 text-center text-default empty-listfilter-show hidden">
	<div class="col-md-6 col-md-offset-3 f-s-22-xs f-s-22-xs-px f-s-28 font-Geogrotesque">The combination of filters you've selected has resulted in no matches.</div>
	<div class="col-md-12 f-s-16-xs f-s-16-xs-px f-s-18 m-y-15 m-y-20-xs">Consider reviewing your filters and adjusting them to broaden your search.</div>
	<div class="col-md-12"><button class="btn btn-primary bold-text" onclick="ACC.savedlist.resetListFilter('listfilter', '.list-filter-popup')">Clear all Filters</button></div>
</section>
<c:forEach items="${savedListEntryData}" var="products">
	<c:set var="productList" value="${index.first ? '' : productList} ${products.product.code}|${products.qty}|${products.inventoryUom}" />
	<c:set var="/savedList/addProductComment" value="productComment"/>
</c:forEach>
<c:if test="${not empty savedListData.entries}">
<div class="row m-b-15 p-r-15 listtop_pagination empty-listfilter-hide hidden">
	<pag:savedListPagePagination top="true" msgKey="${messageKey}" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}"/>
</div>
<c:if test="${mobileFlag}">
	<section class="row empty-listfilter-hide hidden-print m-b-25 m-l-0 m-r-0 m-t-10 hidden">
		<div class="col-md-12 col-xs-12">
			<div class="row flex-center justify-center show-xs list-qty-btn-set">
				<div class="col-md-6 col-xs-9 p-l-0-xs">
					<span class="font-Geogrotesque text-capitalize text-default"><spring:theme code="text.savedList.addtoitemlist" /></span>
				<c:choose>
					<c:when test="${savedListData.isModified}">
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />'id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
						</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" disabled="disabled" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />' id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
							</span>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
				<div class="font-Geogrotesque text-align-right text-capitalize text-default">Quantity</div>
				<div class="col-md-1 col-md-7pe border-grey add-border-radius text-align-center flex-center justify-center p-r-0 p-l-0 col-xs-3 p-r-0-xs list-item-qty">
					<div class="bold f-s-10 text-default b-l-grey bg-white add-border-radius qtyHeading">QTY</div>
					<button class="bg-white text-green js-add-entry-quantity-list-btn plusQty hidden-print" onclick="ACC.savedlist.quantityHandler('plus','.js-add-entry-quantity-input')">+</button>
					<c:choose>
						<c:when test="${savedListData.isModified}">
							<input class="form-control js-add-entry-quantity-input" type="text" name="quantity" id="quantityId" maxlength="5" value="1">
						</c:when>
						<c:otherwise>
							<input class="form-control js-add-entry-quantity-input" disabled="disabled" type="text" name="quantity" id="quantityId" maxlength="5" value="1" />
						</c:otherwise>
					</c:choose>
					<button class="bg-white text-green js-add-entry-quantity-list-btn minusQty minusQtyBtn hidden-print" onclick="ACC.savedlist.quantityHandler('minus','.js-add-entry-quantity-input')">-</button>
				</div>
				<div class="col-md-offset-2 col-md-6 col-xs-12 flex-center p-t-5 p-l-0-xs p-r-0-xs p-l-70 f-s-12 f-w-700 text-red list-add-product-error list-add-product-error-box mobile-savedlist-scenario" style="display: none;">
					<common:globalIcon iconName="exclamation" iconFill="none" iconColor="#E40101" iconColorSecond="white" width="16" height="14" viewBox="0 0 16 14" display="m-r-5" /><span class="p-t-3">Error:</span>&nbsp;
					<span id="base_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.base.variant"/></span>
					<span id="uom_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom"/></span>
					<span id="uom_hidden_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom.hidden"/></span>
					<span id="invalid_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.not.found"/></span>  
					<span id="empty_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.code.empty"/></span>  
					<span id="empty_quantity" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.quantity.empty"/></span>
				</div>
				<div class="col-md-2 col-md-18pe col-xs-12 p-l-5 m-t-10-xs p-l-0-xs p-r-0-xs">
					<input type="button" value="Add" class="btn btn-block btn-white bold saved_add_to_cart_btn p-x-45-imp paddingTB10 ${savedListData.isModified?'':' disabled'}" id="add_Product_to_wishList">
				</div>
			</div>
		</div>
	</section>
</c:if>
<section class="row margin0 text-white f-s-12 hidden-print js-sticky-listpage m-xs-hrz--25 b-radius-0-xs empty-listfilter-hide hidden">
	<div class="container-lg">	
		<div class="flex-center bg-off-grey add-border-radius">
			<div class="col-md-2 p-l-0">
				<label class="btn select_Listall hidden-xs hidden-sm" htmlfor="SelectedProductAll">
					<span class="colored"><input aria-label="Checkbox" id="selectedProdTypeAll" class="select_product_checkbox_all" type="checkbox" value="SelectedProductAll"/></span>
				</label>
				<div class="btn p-a-25 b-r-grey bold controls-mobile">
					<span class="hidden-xs hidden-sm">Actions<i class="glyphicon glyphicon-chevron-down p-l-5 f-s-10"></i></span>
					<span class="hidden-md hidden-lg">
						<svg xmlns="http://www.w3.org/2000/svg" id="Controls_-_Mobile" data-name="Controls - Mobile" width="24.667" height="6" viewBox="0 0 24.667 6">
							<circle id="Ellipse_60" data-name="Ellipse 60" cx="3" cy="3" r="3" fill="#ffffff"/>
							<circle id="Ellipse_61" data-name="Ellipse 61" cx="3" cy="3" r="3" transform="translate(9.333)" fill="#ffffff"/>
							<circle id="Ellipse_62" data-name="Ellipse 62" cx="3" cy="3" r="3" transform="translate(18.667)" fill="#ffffff"/>
						</svg>
					</span>
				</div>
				<div class="row p-x-15 paddingTopB10 bold elemOverlayParent list-filters-row hidden">
					<div class="elemOverlay hidden"></div>
					<div class="col-md-12 p-l-0 paddingTopB10 hidden-md hidden-lg list-filters-link" onclick="ACC.savedlist.selectAllList(this)">Select All</div>
					<div class="col-md-12 p-l-0 list-filters-link de_selected_filter disabled" onclick="ACC.savedlist.deSelectAllList(this)">Deselect All</div>
					<c:choose>
						<c:when test="${savedListData.isModified}">
							<div class="col-md-12 paddingTopB10 list-filters-link remove_selected_filter disabled"><spring:theme code="saveListDetailsPage.remove.selected" /></div>
						</c:when>
						<c:otherwise>
							<div class="col-md-12 paddingTopB10 list-filters-link disabled"><spring:theme code="saveListDetailsPage.remove.selected" /></div>
						</c:otherwise>
					</c:choose>					
					<div class="col-md-12 p-l-0 list-filters-link disabled SavedSelectedProduct${orderOnlinePermissions eq true? '' : '2'}" id="${orderOnlinePermissions eq true? '' : 'orderOnlineATC'}"><spring:theme code="saveListDetailsPage.selected.to.cart" /></div>
					<div class="col-md-12 paddingTopB10 list-filters-link moveselected_list_filter disabled">
						<div class="dropdown-entirelist" >
							<div class="add-cart_selected_filter moveEntireSaveList_list" data-state="expand"><spring:theme code="saveListDetailsPage.move.selected.to.list" /></div>
							<div class="movedselectedtolist hidden" >
								<h1 class="hidden-lg hidden-md"><spring:theme code="saveListDetailsPage.move.selected.to.list" /></h1>
								<div class="scroll-bar">
									<c:forEach var="wishlists" items="${savedLists}">
										<a href="#" class="listname" id="${wishlists.code}" title="${wishlists.name}">${wishlists.name}</a>
									</c:forEach>
								</div>
								<div class="col-md-12">
									<p id="status"></p>
									<input type="hidden" name="createWishList" class="createWishListVal" value="${createWishList}"/> 
									<input type="hidden" name="wishlist" id="wishListId" value="${wishlistName}"/>
									<form:form class="createWishlistForm" action="#">
										<!--give the name of controller-->
										<div id="empty_listName" style="display: none">
											<p class="panel-body" style="margin-bottom: -13px; color: red; padding-left: 0px">
												<spring:theme code="saved.list.empty" />
											</p>
										</div>
										<div class="entirelist-name-alreadyexits-error" style="display: none">
											<p class="panel-body listName-alreadyexits-error-mesg">
											<spring:theme code="saveListDetailsPage.saved.list.duplicate" />
											</p>
										</div> 
										<div class="row">
											<div class="col-md-10 col-xs-10">
												<div class="row">
													<input class="form-control" id="newWishListName" name="wishListName" placeholder="Create a new List"/>
												</div>
											</div>
											<div class="col-md-2 col-xs-2 create-wish-list-btn">
												<div class="row">
													<button type="button" id="createWishlistforentireList" class="btn btn-primary">
														<spring:theme code="saveListDetailsPage.total.ok" />
													</button>
												</div>
											</div> 
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${!mobileFlag}">
				<div class="col-md-${(requestQuoteList ne 'true' and showGenerateLabels eq 'off') ? '6' : (requestQuoteList eq 'true') ? '2' : '4'}"><span id="counter-text" class="list-item-counter-text hidden">You have  <span class="list-item-counter"></span> item<span class="list-item-counter-s"></span> selected</span></div>
			</c:if>
			<input type="hidden" class="showGenerateLabels" value="${showGenerateLabels}" />
			<c:if test="${showGenerateLabels ne 'off'}">
				<div class="col-md-2 p-l-0-xs text-align-right hidden-xs hidden-sm">
					<button data-listtitle="${savedListSize}"
						onclick="ACC.savedlist.generateLabelSheetPopup()"
						class="btn btn-gray bold btn-block" data-toggle="popover"
						data-content="Click here to generate label sheets">
						<span><spring:theme code="text.lists.generateLabel.generateLabels" /></span>
					</button>
				</div>
			</c:if>			
			<c:if test="${requestQuoteList eq 'true'}">
				<div class="col-md-2 p-l-0 m-l-auto p-r-0-sm p-r-0-xs text-align-right">
					<button data-listtitle="${savedListName}"
						onclick="ACC.savedlist.requestQuotePopup(this,'${savedListData.code}','${savedListSize}');ACC.adobelinktracking.requestQuote(this,'','','my account: lists: list detail')"
						class="btn btn-gray bold btn-block" data-toggle="popover"
						data-content="Click here to request a quote of your selected products from a nearby SiteOne branch.">
						<span class="hidden-xs hidden-sm"><spring:theme code="text.lists.requestQuote" /></span>
						<span class="hidden-md hidden-lg"><spring:theme code="text.lists.getQuote" /></span>
					</button>
				</div>
			</c:if>

			<div class="col-md-2 padding0 hidden-xs hidden-sm ${(requestQuoteList eq 'true' and showGenerateLabels eq 'off') ? '' : 'm-l-auto'}">
				<a href="${homelink}savedList/generateEstimate/?wishListCode=${savedListData.code}" class="btn btn-block btn-gray bold ${currentLanguage.isocode eq 'es' ? 'estimateBtnAlign-es' : ''}" data-toggle="popover" data-content="Click here to generate a customer facing estimate on your selected products. Our award winning estimate feature is FREE for all SiteOne customers!"><spring:theme code="saveListDetailsPage.convert.list.estimate" /></a>
			</div>
			<div class="col-md-2 p-l-0-xs list_addlisttocart_wrapper addtocart addlisttocart-disabled ${requestQuoteList eq 'true' ? '' : 'm-l-auto'}">
				<div class="addListToCart_filter_sec" id="addListToCart_filter">
					<input type="hidden" class="saveList" value="${productList}">
          <a href="${homelink}savedList/addListToCart?wishListCode=" onclick="if($(this).attr('href').indexOf('#')==-1){loading.start()}" class="btn btn-primary btn-block bold addListToCarts_filter paddingatlist ${currentLanguage.isocode eq 'es' ? 'addListToCarts-es' : ''}" disabled="disabled" data-toggle="popover" data-content="You have items in your list that are unable to be purchased online through your selected branch."><spring:theme code="saveListDetailsPage.list.to.cart" /></a>
				</div>
			</div>
		</div>
	</div>
</section>
<c:if test="${!mobileFlag}">
	<section class="row hidden-print m-r-0 m-l-0 m-t-10 m-b-10 bg-light-grey border-grayish padding-20 empty-listfilter-hide hidden">
		<div class="col-md-12">
			<div class="row flex-center justify-center list-qty-btn-set">
				<div class="col-md-2 f-s-19 f-w-500 font-Geogrotesque p-r-0 text-align-right text-capitalize text-default"><spring:theme code="text.savedList.addtoitemlist" />
				</div>
				<div class="col-md-6">
				<c:choose>
					<c:when test="${savedListData.isModified}">
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />'id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
							</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="input-group">
							<input type="text" class="form-control border-grey-imp b-r-0-imp" disabled="disabled" name="serachBox" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />' id="savedListSearch">
							<span  class="input-group-addon bg-white padding0 border-grey-imp b-l-0-imp">
								<button class="btn-link p-r-15" type="button" onclick="$('.saved_add_to_cart_btn').trigger('click')"> </button>
							</span>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
				<div class="col-md-1 col-md-7pe border-grey add-border-radius text-align-center flex-center justify-center p-r-0 p-l-0 list-item-qty">
					<div class="bold f-s-10 text-default b-l-grey bg-white add-border-radius qtyHeading">QTY</div>
					<button class="bg-white text-green js-add-entry-quantity-list-btn plusQty hidden-print" onclick="ACC.savedlist.quantityHandler('plus','.js-add-entry-quantity-input')">+</button>
					<c:choose>
						<c:when test="${savedListData.isModified}">
							<input class="form-control js-add-entry-quantity-input" type="text" name="quantity" id="quantityId" maxlength="5" value="1">
						</c:when>
						<c:otherwise>
							<input class="form-control js-add-entry-quantity-input" disabled="disabled" type="text" name="quantity" id="quantityId" maxlength="5" value="1" />
						</c:otherwise>
					</c:choose>
					<button class="bg-white text-green js-add-entry-quantity-list-btn minusQty minusQtyBtn hidden-print" onclick="ACC.savedlist.quantityHandler('minus','.js-add-entry-quantity-input')">-</button>
				</div>
				<div class="col-md-2 col-md-18pe">
					<input type="button" value="Add" class="btn btn-white bold saved_add_to_cart_btn p-x-45-imp paddingTB10 ${savedListData.isModified?'':' disabled'}" id="add_Product_to_wishList">
				</div>
			</div>
		</div>
		<div class="col-md-offset-2 col-md-6 col-xs-12 flex-center p-t-5 p-l-0-xs p-r-0-xs p-l-70 f-s-12 f-w-700 text-red list-add-product-error list-add-product-error-box desktop-savedlist-scenario" style="display: none;">
			<common:globalIcon iconName="exclamation" iconFill="none" iconColor="#E40101" iconColorSecond="white" width="16" height="14" viewBox="0 0 16 14" display="m-r-5" /><span class="p-t-3">Error:</span>&nbsp;
			<span id="base_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.base.variant"/></span>
			<span id="uom_product_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom"/></span>
			<span id="uom_hidden_error" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.uom.hidden"/></span>
			<span id="invalid_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.not.found"/></span>  
			<span id="empty_code" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.code.empty"/></span>  
			<span id="empty_quantity" class="list-add-product-error p-t-3 margin0" style="display: none;"><spring:theme code="saved.list.product.quantity.empty"/></span>
		</div>
	</section>
</c:if>
<c:if test="${mobileFlag}">
	<div class="row f-s-13 m-t-15 text-center">
		<div class="col-md-12"><span class="list-item-counter-text text-white">You have <span class="list-item-counter"></span> item<span class="list-item-counter-s"></span> selected</span></div>
	</div>
</c:if>
</c:if>
<div class="row hidden show-print paddingBottom5 no-margin print-table-heading">
	<div class="col-xs-1"><strong><spring:theme code="saveListDetailsPage.print.product" /></strong></div>
	<div class="col-xs-6"> </div>
	<div class="col-xs-2"><strong><spring:theme code="saveListDetailsPage.print.price" /></strong></div>
	<div class="col-xs-2 text-align-center"><strong><spring:theme code="saveListDetailsPage.print.quantity" /></strong></div>
	<div class="col-xs-1 text-align-right"><strong><spring:theme code="saveListDetailsPage.print.totals" /></strong></div>
</div>				
<div class="row">
	<div class="product__list col-md-12 col-sm-12 col-xs-12">
		<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
		<!--division for Adding product  -->
		<c:forEach items="${savedListEntryData}" var="products" varStatus="index">
			<div class="saved-list-singleProduct sl_singleProduct_${products.product.code}">
				<c:set var="productList" value="${index.first ? '' : productList} ${products.product.code}|${products.qty}|${products.inventoryUom}" />
				<c:set var="/savedList/addProductComment" value="productComment"/>
				<div class="row margintop15 margin0 p-y-20 flex-center show-xs saved-list-sec ${products.product.isRegulatoryLicenseCheckFailed eq false? ' top-alert-msg' : ' '}" data-nontransferableFlag="${products.product.isTransferrable eq false and products.product.isStockInNearbyBranch eq true}" data-outOfStockImage="${products.product.outOfStockImage}" data-isTransferrable="${products.product.isTransferrable}" data-isStockInNearbyBranch="${products.product.isStockInNearbyBranch}"  data-isRegulatoryLicenseCheckFailed="${products.product.isRegulatoryLicenseCheckFailed}">
					<input id="prodCode${index.index}" type="hidden"  name="code" value="${products.product.code}" class="productcode" >
					<input id="prodQty_${products.product.code}" type="hidden"  name="qty" value="${products.qty}" class="	ValueId">  
					<input id="prodItemNumber${index.index}" type="hidden"  name="code" value="${products.product.itemNumber}" class="productitemnumber">
					<input id="prodQtyItem_${products.product.itemNumber}" type="hidden"  name="qty" value="${products.qty}" class="productqtyItem"> 
					<input type="hidden" class="whishlistcode" value="${savedListData.code}"/>
					<input type="hidden" class="inventryuom" value="${products.inventoryUom}"/>
					<product:savedListproductListerItem isModified="${savedListData.isModified}" product="${products.product}" quantity="${products.qty}" totalPrice="${products.totalPrice}" hidePrice="${products.hidePrice}" code="${savedListData.code}" comment="${products.entryComment}" indexvalue="${index.index}" uomid="${products.inventoryUom}" uomDesc="${products.inventoryUomDesc}"/>
				</div>
			</div>
		</c:forEach>
		<input type="hidden" id="filterCount" value="${searchPageData.pagination.totalNumberOfResults}">
	</div>
</div>
<div class="row p-r-15 listbottom_pagination hidden-print">
	<pag:savedListPagePagination top="false" msgKey="${messageKey}" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}"/>
</div>
<!--division for share functionality  -->
<div id="share_overlay" >
	<form:form  modelAttribute="siteoneShareSavedListForm" id="siteoneShareSavedListForm">
		<div class="modalpopup">
			<button type="button" class="comment-overlay" id="cboxClose"><span class="icon-close"></span></button>
			<form:hidden path="code" id="code" value="${code}"/>
			<form:hidden path="listname" id="listname" value="${savedListName}"/>
			<div class="font-Geogrotesque text-center f-s-28 f-s-22-xs-px f-w-500 black-title p-y-15">
				<spring:theme code="saveListDetailsPage.shareList" />
			</div>
			<div class="share-assembly-description b-b-lighter-grey b-t-lighter-grey p-y-15 margin0 text-dark-gray text-align-center black-title f-s-18 f-s-16-xs-px">
				<spring:theme code="saveListDetailsPage.text.1" />
			</div>
			<p class="black-title share-list-name bold f-s-18 share-list-name text-center-xs">${savedListName}</p>
			<p class="m-b-5 m-t-15 share-list-title black-title"><spring:theme code="saveListDetailsPage.share.with" /></p>
			<div class="textBox">
				<div class="cl"></div>
				<div class="share-list-user js-share-email-error hidden"><spring:theme code="register.email.invalid"/></div>
				<input class="inputTextBox form-control" type='text' onkeydown="return (event.keyCode!=13);" required="required"/>
				<ul id="listValues"></ul>
				<form:hidden path="users" id="users" value=""/>
			</div>
			<p class="m-b-10 m-t-10  share-list-title black-title" id="allow-text"><spring:theme code="saveListDetailsPage.share.allow" /></p>
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-6"> 
					<div class="radio-wrapper">
						<label class="radio-inline">
						<span class="colored-radio">
							<input type="radio" name="allow-label" class="label-radio" value="false" checked/>
						</span>
						</label> 
						<span class="radio-label p-l-20"><spring:theme code="saveListDetailsPage.share.viewOnly" /></span>
					</div>				
				</div>
				<div class="col-md-9 col-sm-6 col-xs-6">
					<div class="radio-wrapper">
						<label class="radio-inline">
						<span class="colored-radio">
							<input type="radio" name="allow-label" class="label-radio" value="true" />
						</span>
						</label> 
						<span class="radio-label p-l-20"><spring:theme code="saveListDetailsPage.share.viewEdit" /></span>
					</div>
				</div>
			</div>
			<div class="form-group m-t-5 add-note-wrapper ">
				<form:label path="note"><p class="m-b-0 m-t-10 share-list-title black-title"><spring:theme code="saveListDetailsPage.add.note" /></p></form:label> 
				<form:textarea path="note" rows="5" cols="30" maxlength="200" class="form-control share-comment" style="min-height:80px;resize:none;"/> 
			</div>
			<div class="f-s-14 f-w-400 m-b-30 m-t-10 text-area-disclaimer text-dark-gray"></div>
			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-12"> <input type="button" value='<spring:theme code="basket.save.cart.action.cancel" />' class="btn btn-default btn-block cancelBtn bold-text"/></div>
				<div class="col-md-6 col-sm-6 col-xs-12"> <input type="button" value='<spring:theme code="saveListDetailsPage.share" />' class="btn btn-primary btn-block shareListDetailBtn bold-text" disabled="disabled"/></div>
			</div>
		</div>
	</form:form>
</div>
<div class="overlayBackground" ></div>
<common:requestQuotePopup />
<common:requestQuotePopupPLPPDP />
<common:generateLabelSheetPopup  className="generateLabelSheet"/>
<!--division for share functionality  -->
<c:if test="${not empty listSearchPageData}">
	<product:savedListFilter listSearchPageData="${listSearchPageData}" />
</c:if>
</c:otherwise>
</c:choose>
</template:page>