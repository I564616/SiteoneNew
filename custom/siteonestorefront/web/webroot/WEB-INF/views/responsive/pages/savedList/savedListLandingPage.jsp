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
<template:page pageTitle="${pageTitle}">
	<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
	<div class="recommendedlists-tabs-container f-s-18 font-14-xs font-Geogrotesque">
		<div class="row container-lg margin0 p-l-0-xs p-y-15-xs">
			<a  href="${homelink}savedList" class="col-md-1 padding-20 p-15-xs recommendedlist-tab tab-mylist active-list-tab"><spring:theme code="text.lists.myLists" /> <span class="f-w-500 text-default bold-xs">&nbsp[${savedListCount}]</span> </a>
			<a  href="${homelink}savedList/recommendedList" class="col-md-1 padding-20 p-15-xs recommendedlist-tab tab-recommended"><spring:theme code="text.lists.recommended.titleRecommended" /> <span class=" f-w-500 text-default bold-xs">&nbsp[${recommendedList.size()}]</span>
			</a>
		</div>
		<div class="tab-wrapper-border"></div>
	</div>
<div class="tab-list-data tab-data-mylist">
	<div class="flex-center marginTopBVottom30 p-l-30 p-t-80 p-b-10 row">
		<div class="col-md-10 f-s-28 font-Geogrotesque f-w-500 text-default f-s-20-xs-px"><spring:theme code="text.lists.myLists" /></div>
		<div class="col-md-2 col-xs-5 p-l-0-xs create_list_btn${not empty savedListData? '' : ' hidden'}">
			<a href="${homelink}savedList/createList" class="btn btn-primary btn-block listLandingPage create-list-btn">
				<spring:theme code="text.savedList.createList" />
			</a>
		</div>
	</div>
	<c:if test="${isListDeleted eq 'success'}">
		<div class="row margin0">
			<div id="success" class="col-md-12 bg-white flex-center padding-30 m-b-25">
				<span class="icon-success" style="margin-left:20px;"></span>
				<p class="black-title col-md-5 col-xs-8" style="margin-top: 15px;">
					<b>
						<spring:theme code="assemblyLandingPage.success" />!
					</b><br />
					<spring:theme code="savedListLandingPage.list.deleted" />.
				</p>
			</div>
		</div>
	</c:if>
	<c:choose>
		<c:when test="${not empty savedListData}">
			<div class="hidden-xs ${isShowAllAllowed ? '' : ' hidden'}">
				<nav:listPagination top="true" msgKey="text.savedList.page.currentPage" showCurrentPageInfo="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}" />
			</div>
			<div class="row margin0 p-l-15 p-r-15 p-y-20 bg-off-grey add-border-radius text-white bold-text f-s-12 text-uppercase flex-center hidden-sm hidden-xs">
				<div class="col-md-2 col-md-12pe">
					<a class="text-white pointer no-text-decoration flex-stretch" href="${homelink}savedList?sortOrder=${sortOrder eq 'desc'? 'asc' : 'desc'}">
						<spring:theme code="assemblyLandingPage.date.updated" />
						<common:globalIcon iconName="sort" iconFill="#f90" iconColor="#fff" width="8" height="14" viewBox="0 0 8 14" display="m-l-5" /></span>
					</a>
				</div>
				<div class="col-md-4">
					<spring:theme code="listLandingPage.assembly.name" />
				</div>
				<div class="col-md-2 col-md-10pe">
					<spring:theme code="assemblyLandingPage.setting" />
				</div>
				<div class="col-md-2">
					<spring:theme code="assemblyLandingPage.owner" />
				</div>
				<div class="${quotesFeatureSwitch and account_classification ne 'retailer'?'col-md-5 pad-lft-45' : 'col-md-3 col-md-offset-2 p-l-30'}">&nbsp;&nbsp;&nbsp;<span class="hidden-md hidden-sm hidden-xs p-l-40"></span><spring:theme code="text.lists.recommended.actions" /><i class="glyphicon glyphicon-info-sign p-l-5 hidden" data-toggle="popover" data-content="Actions for Request a Quote and Convert to Estimate"></i></div>
			</div>
			<c:forEach items="${savedListData}" var="savedLists" varStatus="index">
				<div class="row margin0 p-l-15 p-y-20 m-t-10 m-t-15-xs no-padding-xs flex-center justify-center flex-wrap-xs add-border-radius border-grey text-default f-s-15 font-14-xs bg-white invoice-border savedlists-card">
					<div class="col-md-2 col-md-12pe hidden-sm hidden-xs">
						<fmt:formatDate value="${savedLists.modifiedTime}" type="date" timeStyle="long" />
					</div>
					<div class="col-md-4 col-sm-3 col-xs-12 flex-center p-15-xs b-b-grey-xs bold list-name">
						<a class="bold btn-green-link text-primary f-s-18-xs-px p-r-50-xs font-GeogrotesqueSemiBold no-text-decoration" href="${homelink}savedList/listDetails?code=${savedLists.code}">${savedLists.name}</a>
						<button class="btn btn-link m-l-5 pull-right hidden-md hidden-lg dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
							<common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5" viewBox="0 0 23 5" display="" />
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li style="display:${savedLists.isModified?'block':'none'};">
								<button data-listtitle="${savedLists.name}" class="btn btn-block bg-off-grey transition-3s" onclick="ACC.savedlist.listDelete(this, ${savedLists.code})">
									<common:globalIcon iconName="close" iconFill="none" iconColor="#ffffff" width="11" height="10" viewBox="0 0 14 12" display="m-r-10 m-r-10-xs deleteList" /><spring:theme code="savedListLandingPage.delete" />
								</button>
							</li>
							<li>
								<button data-listtitle="${savedLists.name}" onclick="ACC.savedlist.listShare(this, ${savedLists.code})" class="btn btn-block bg-off-grey transition-3s">
									<common:globalIcon iconName="share-arrow" iconFill="none" iconColor="#ffffff" width="11" height="10" viewBox="0 0 14 12" display="m-r-10 m-r-10-xs" /><spring:theme code="saveListDetailsPage.share" />
								</button>
							</li>
						</ul>
					</div>
					<div class="col-xs-6 p-15-xs b-b-grey-xs hidden-md hidden-lg">
						<span class="bold text-gray text-uppercase f-s-10"><spring:theme code="assemblyLandingPage.date.updated" /></span>
						<p class="m-b-0 f-w-n-xs">
							<fmt:formatDate value="${savedLists.modifiedTime}" type="date" timeStyle="long" />
						</p>
					</div>
					<div class="col-md-2 col-md-10pe col-xs-6 p-15-xs b-b-grey-xs b-l-grey-xs">
						<span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="assemblyLandingPage.setting" /></span>
						<p class="m-b-0 f-w-n-xs">${savedLists.isShared}</p>
					</div>
					<div class="col-md-2 col-sm-3 col-xs-12 p-15-xs b-b-grey-xs">
						<span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="assemblyLandingPage.owner" /></span>
						<p class="m-b-0 f-w-n-xs">${savedLists.user}</p>
					</div>
					<div class="col-md-5 col-xs-12 p-15-xs p-r-0 text-right">
						<a href="${homelink}savedList/generateEstimate/?wishListCode=${savedLists.code}" class="btn btn-white hidden-sm hidden-xs">
							<spring:theme code="text.savedList.estimate" />
						</a>
						<c:if test="${quotesFeatureSwitch and account_classification ne 'retailer'}">
							<button class="btn btn-primary m-l-20 hidden-sm hidden-xs" data-listtitle="${savedLists.name}" onclick="ACC.savedlist.requestQuotePopup(this,'${savedLists.code}','${savedLists.totalEntries}');ACC.adobelinktracking.requestQuote(this,'','','my account: lists')"><spring:theme code="quote.create" /></button>
							<button class="btn btn-white btn-block bold-text hidden-md hidden-lg" data-listtitle="${savedLists.name}" onclick="ACC.savedlist.requestQuotePopup(this,'${savedLists.code}','${savedLists.totalEntries}');ACC.adobelinktracking.requestQuote(this,'','','my account: lists')"><spring:theme code="quote.create" /></button>
						</c:if>
						<button class="btn btn-link m-l-5 hidden-sm hidden-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
							<common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5" viewBox="0 0 23 5" display="" />
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li style="display:${savedLists.isModified?'block':'none'};">
								<button data-listtitle="${savedLists.name}" class="btn btn-block bg-off-grey transition-3s" onclick="ACC.savedlist.listDelete(this, ${savedLists.code})">
									<common:globalIcon iconName="close" iconFill="none" iconColor="#ffffff" width="11" height="10" viewBox="0 0 14 12" display="m-r-10 m-r-10-xs deleteList" />
									<spring:theme code="savedListLandingPage.delete" />
								</button>
							</li>
							<li>
								<button data-listtitle="${savedLists.name}" onclick="ACC.savedlist.listShare(this, ${savedLists.code})" class="btn btn-block bg-off-grey transition-3s">
									<common:globalIcon iconName="share-arrow" iconFill="none" iconColor="#ffffff" width="11" height="10" viewBox="0 0 14 12" display="m-r-10 m-r-10-xs" />
									<spring:theme code="saveListDetailsPage.share" />
								</button>
							</li>
						</ul>
					</div>
				</div>
			</c:forEach>
			<div class="${isShowAllAllowed? '' : 'hidden'}">
				<nav:listPagination top="false" msgKey="text.savedList.page.currentPage" showCurrentPageInfo="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}" />
			</div>
			<p class="col-md-12 col-sm-12 margin20 list_footer_desc">
				<spring:theme code="text.savedList.desc1" />
			</p>
			<common:requestQuotePopup />
			<!--division for share functionality  -->
			<div id="share_overlay">
				<div class="">
					<form:form modelAttribute="siteoneShareSavedListForm" id="siteoneShareSavedListForm">
						<div class="modalpopup">
							<button type="button" class="comment-overlay" id="cboxClose"><span class="icon-close"></span></button>
							<form:hidden path="code" id="code" value="" />
							<form:hidden path="listname" id="listname" value="" />
							<div class="font-Geogrotesque text-center f-s-28 f-w-500 black-title p-y-15">
								<spring:theme code="saveListDetailsPage.shareList" />
							</div>
							<div class="share-assembly-description b-b-lighter-grey b-t-lighter-grey p-y-15 margin0 text-dark-gray text-align-center black-title">
								<spring:theme code="saveListDetailsPage.text.1" />
							</div>
							<p class="black-title bold f-s-18 share-list-name"></p>
							<p class="share-list-title">
								<spring:theme code="saveListDetailsPage.share.with" />
							</p>
							<div class="textBox">
								<div class="cl"></div>
								<div class="share-list-user js-share-email-error hidden">
									<spring:theme code="register.email.invalid" />
								</div>
								<input class="inputTextBox form-control" type='text' onkeydown="return (event.keyCode!=13);" required="required" />
								<ul id="listValues"></ul>
								<form:hidden path="users" id="users" value="" />
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
							<div class="form-group margin20 add-note-wrapper">
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
			</div>
			<div class="overlayBackground"></div>
			<!--division for share functionality  -->
		</c:when>
		<c:otherwise>
			<div class="row m-x-25">
				<div class="bg-white col-md-12 flex-center flex-dir-column justify-center emptySavedListSection show-xs">
					<p class="f-s-28 f-w-600 f-s-20-xs-px flex-center font-Geogrotesque m-b-0 p-b-20 p-t-50 text-primary"><spring:theme code="text.lists.mylists.emptySavedListHeader" /></p>
					<div class="flex-center savedListBorder font-Geogrotesque show-xs ">
						<div class="b-r-gray flex p-l-30 p-r-35 p-y-20 p-l-10-xs p-r-10-xs b-r-0-xs">
							<div class="flex-center savedListCircle bg-white saveicon"><common:globalIcon iconName="savedlist-saveicon" width="17" height="17" viewBox="0 0 17 17" /></div>
							<div class="m-l-20 text-default">
							<div class="f-s-18 f-w-600"><spring:theme code="text.lists.mylists.saveItemsToLists" /></div> 
							<div class="f-s-16 f-w-500"><spring:theme code="text.lists.mylists.forCommonlyOrderedMaterial" /></div>
							</div>
						</div>
						<div class="b-r-gray flex p-l-30 p-r-100 p-y-20 p-l-10-xs p-r-10-xs p-t-0-xs p-b-0-xs b-r-0-xs">
							<div class="flex-center savedListCircle bg-white quoteicon">
								<common:globalIcon iconName="savedlist-quoteicon" width="19" height="23" viewBox="0 0 19 23" />
							</div>
							<div class="m-l-20 text-default">
								<div class="f-s-18 f-w-600"><spring:theme code="text.lists.mylists.quote.create" /></div>
								<div class="f-s-16 f-w-500"><spring:theme code="text.lists.mylists.fromExistingList" /></div>
							</div>
						</div>
						<div class="flex p-l-30 p-r-75 p-y-20 p-l-10-xs p-r-10-xs">
							<div class="flex-center savedListCircle bg-white estimateicon">
								<common:globalIcon iconName="savedlist-estimateicon"  width="24" height="21" viewBox="0 0 24 21" />
							</div>
							<div class="m-l-20 text-default">
								<div class="f-s-18 f-w-600"><spring:theme code="text.lists.mylists.createEstimates" /></div>
								<div class="f-s-16 f-w-500"><spring:theme code="text.lists.mylists.freeAndCustomorFacing" /></div>
							</div>
						</div>
					</div>
					<div class="col-md-3 p-x-60 m-t-30 m-b-55 p-x-10-xs createListbtn empty-list-button-wrapper-create">
						<a href="${homelink}savedList/createList" class="btn btn-primary btn-block">
							<spring:theme code="text.savedList.createList" />
						</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
</template:page>