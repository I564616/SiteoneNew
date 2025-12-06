<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<template:page pageTitle="${pageTitle}" >
<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
<div class="recommendedlists-tabs-container f-s-18 font-14-xs font-Geogrotesque">
	<div class="row container-lg margin0 p-l-0-xs p-y-15-xs">
		<a href="${homelink}savedList" class="col-md-1 padding-20 p-15-xs recommendedlist-tab tab-mylist"><spring:theme code="text.lists.myLists" /> <span class="f-w-500 text-default bold-xs">&nbsp[${savedListCount}]</span> </a>
		<a href="${homelink}savedList/recommendedList" class="col-md-1 padding-20 p-15-xs recommendedlist-tab tab-recommended active-list-tab"><spring:theme code="text.lists.recommended.titleRecommended" /> <span class=" f-w-500 text-default bold-xs">&nbsp[${recommendedList.size()}]</span>
		</a>
	</div>
	<div class="tab-wrapper-border"></div>
</div>
<div class="recommended-list-page tab-list-data tab-data-recommended">
	<c:choose>
		<c:when test="${not empty recommendedList}">
			<div class="row p-t-50 p-b-25 p-l-30">
				<div class="col-md-12 f-s-28 font-Geogrotesque text-bold text-default f-s-20-xs-px p-t-60"><spring:theme code="text.lists.recommended.titleRecommended" /></div>
				<div class="col-md-12 f-s-18 font-Arial m-t-5 f-s-13-xs-px"><spring:theme code="text.lists.recommended.paragraph" /></div>
			</div>
			<div class="row hidden-xs hidden-sm margin0 p-l-15 p-r-15 p-y-25 bg-off-grey add-border-radius text-white bold-text f-s-12 text-uppercase">
				<div class="col-md-2 col-md-14pe p-l-25 pointer flex-stretch recommendedlist-sort-button recommended-list-ascending" onclick="ACC.savedlist.getSortedDate()">
					<spring:theme code="text.lists.recommended.dateSubmitted" /><common:globalIcon iconName="sort" iconFill="#f90" iconColor="#fff" width="8" height="14" viewBox="0 0 8 14" display="m-l-5" />
				</div>
				<div class="col-md-3"><spring:theme code="text.lists.recommended.listName" /></div>
				<div class="col-md-3 col-md-15pe"><spring:theme code="text.lists.recommended.setting" /></div>
				<div class="col-md-3 col-md-15pe"><spring:theme code="text.lists.recommended.owner" /></div>
				<div class="col-md-2"><spring:theme code="text.lists.recommended.actions" /></div>
			</div>
			<div class="listitems">
				<c:forEach items="${recommendedList}" var="entry" varStatus="index">
					<div class="row margin0 p-l-15 p-r-15 p-y-20 no-padding-xs flex-center flex-wrap-xs add-border-radius text-default f-s-15 font-14-xs recommended-table bg-white m-t-10" data-date="${entry.modifiedTime}" data-name="${entry.categoryName}">
						<div class="col-md-2 col-md-14pe p-l-25 hidden-xs hidden-sm">
							<fmt:formatDate value="${entry.modifiedTime}" type="date" timeStyle="long" />
						</div>
						<a href="${homelink}savedList/recommendedListDetails?categoryName=${entry.categoryName}" class="col-md-3 col-xs-12 text-primary text-capitalize bold-text pointer b-b-grey-xs p-y-15-xs rec-list-link">
							<span class="hidden-xs hidden-sm"><spring:theme code="text.lists.recommended.listNameYour" />&nbsp;${not empty entry.categoryName && entry.categoryName ne null? entry.categoryName : '-'}&nbsp;<spring:theme code="text.lists.recommended.listNameList" /></span>
							<span class="hidden-md hidden-lg bold-xs f-s-16-xs-px font-Geogrotesque"><spring:theme code="text.lists.recommended.listNameYour" />&nbsp;${not empty entry.categoryName && entry.categoryName ne null? entry.categoryName : '-'}&nbsp;<spring:theme code="text.lists.recommended.listNameList" /></span>
						</a>
						<div class="col-xs-6 p-15-xs b-b-grey-xs hidden-md hidden-lg">
							<span class="bold text-gray text-uppercase f-s-10"><spring:theme code="text.lists.recommended.dateupdated" /></span>
							<p class="m-b-0 f-w-n-xs font-14-xs"><fmt:formatDate value="${entry.modifiedTime}" type="date" timeStyle="long" /></p>
						</div>
						<div class="col-md-3 col-md-15pe text-capitalize col-xs-6 p-15-xs b-b-grey-xs b-l-grey-xs">
							<span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.lists.recommended.setting" /></span>
							<p class="m-b-0 f-w-n-xs"><spring:theme code="text.lists.recommended.curated" /></p>
						</div>
						<div class="col-md-3 col-md-15pe col-sm-3 col-xs-12 p-15-xs b-b-grey-xs">
							<span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.lists.recommended.owner" /></span>
							<p class="m-b-0 f-w-n-xs"><spring:theme code="text.lists.recommended.siteOne" /></p>
						</div>
						<div class="col-md-3 p-15-xs col-xs-12 flex-center-xs justify-center">
							<button class="bg-white font-16 font-Arial p-l-30 text-primary recommended-list-btn" onclick="ACC.savedlist.recommendedCopyList('${entry.categoryName}','${entry.productCount}', this)"><spring:theme code="text.lists.recommended.copytoMyLists" /></button>
						</div>
					</div>
				</c:forEach>
			</div>
			<common:copyToListPopUp pageName="rec" className="recommendedlist"/>
			<div class="font-16 font-Arial p-t-25 text-gray"><spring:theme code="text.lists.recommended.bottomNote" /></div>
		</c:when>
		<c:otherwise>
			<div class="row p-t-50 p-b-25 p-l-30">
				<div class="col-md-12 f-s-28 font-Geogrotesque f-w-500 text-default f-s-20-xs-px p-t-60"><spring:theme code="text.lists.recommended.titleRecommended" /></div>
				<div class="col-md-12 m-t-30 emptyRecommended">
					<div class="bg-white flex-center flex-dir-column justify-center emptySavedListSection show-xs">
						<p class="f-s-28 f-w-600 flex-center font-Geogrotesque m-b-0 padding-bottom-15 p-t-50 text-primary show-xs p-10-xs"><spring:theme code="text.lists.recommended.emptySavedListHeader" /></p>
						<div class="flex-center savedListBorder font-Geogrotesque show-xs">
							<div class="b-r-gray flex pad-lft-45 p-r-100 p-y-20 b-r-0-xs p-x-10-xs">
								<div class="flex-center savedListCircle bg-white carticon">
									<common:globalIcon iconName="savedlist-carticon" width="24" height="21" viewBox="0 0 24 21" />
								</div>
								<div class="m-l-20 text-default">
			
									<div class="f-s-18 f-w-600"><spring:theme code="text.lists.recommended.emptyShopProducts" /></div>
									<div class="f-s-16 f-w-500"><spring:theme code="text.lists.recommended.toBeginPurchaseHistory" /></div>
								</div>
							</div>
			
							<div class="flex pad-lft-45 p-r-75 p-y-20  p-x-10-xs">
								<div class="flex-center savedListCircle bg-white saveicon">
									<common:globalIcon iconName="savedlist-saveicon" width="17" height="17" viewBox="0 0 17 17" />
								</div>
								<div class="m-l-20 text-default">
									<div class="f-s-18 f-w-600"><spring:theme code="text.lists.recommended.saveItemsLists" /></div>
									<div class="f-s-16 f-w-500"><spring:theme code="text.lists.recommended.commonlyOrderedMaterial" /></div>
								</div>
			
							</div>
						</div>
						<div class="col-md-5 m-t-30 m-b-55 empty-list-button-wrapper-create">
							<a class="" href="${homelink}">
								<button class="btn-primary shopping-btn">
									<spring:theme code="text.lists.recommended.startShoppingEnableButton" />
								</button>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>	
</div>
</template:page>