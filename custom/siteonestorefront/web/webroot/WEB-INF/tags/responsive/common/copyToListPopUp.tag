<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="pageName" required="false" type="java.lang.String" %>
<%@ attribute name="className" required="false" type="java.lang.String" %>
<%@ attribute name="productCount" required="false" type="java.lang.String"%>
<%@ attribute name="recomendedDetailPage" required="false" type="java.lang.Boolean"%>
<c:set var="primaryColor" value="${iconColor ne null?iconColor:'#78A22F'}" />


<div class="hidden" id="${className}popup">
<c:set var="wishListData" value="${savedListData}" />
<span class="recCategory"></span>
<c:if test="${recomendedDetailPage eq true}">
	<c:set var="wishListData" value="${savedLists}" />
</c:if>

	<div class="pdplistpopupnew">
		<div class=pdplistpopup1>
			<div class='pdp-atlpopupnew-list p-b-20 recommended-popup-headline'>
				<spring:theme code="text.lists.recommended.copytoMyLists.headlineCopy" /> <span id="${className}count"></span> <spring:theme code="text.lists.recommended.copytoMyLists.headline" /> 
			</div>
			<div class="f-s-18 text-center marginBottom20 recommDesc"><spring:theme code="text.lists.recommended.copytoMyLists.recommDesc" /></div>
			<div class="border-grey margin-bot-10-md m-b-10-xs ${not empty wishListData?'':'hidden'}">
				<div class="popupsavedlistoptions">
					<div class="popupoptionlistitem">
						<c:forEach var="wishlists" items="${wishListData}">
							<div class="font-size-14 wish-item text-default m-b-10-xs ${className}name hidden-xs" id="${wishlists.code}"
								data-value="${wishlists.code}"
								data-productcode="${product.itemNumber}" onclick="ACC.savedlist.recCopyToMyListPopup(${wishlists.code})">${wishlists.name}</div>
							<div class="font-size-14 text-default m-b-10-xs ${className}namemobile hidden-md hidden-lg hidden-sm"
								 onclick="ACC.savedlist.recCopyToMyListPopup(${wishlists.code})">
								${wishlists.name}</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class='input-group margin-bot-10-md m-b-10-xs'>
				<input class='form-control createnewlistinput' id="pdppopupinput"
					placeholder='<spring:theme code="addToSavedList.createList" />' />
				<div class='input-group-btn'>
					<button class="bg-primary createnewlistbtn" value="createNewListLinkProduct"
						data-productcode="${product.code}">+</button>
				</div>
			</div>
			<div class="pdp-newlist-popup-error pdp-newlist-popup-error-text">
				<spring:theme code="saved.list.duplicate" />
			</div>
			<p class="pdp-emptynewlist-popup-error pdp-emptynewlist-popup-error-text">
				<spring:theme code="saved.list.empty" />
			</p>
			<div class="row listpopupbtn">
				<div class="col-md-6 col-xs-5">
					<button class="btn-default listpopupclosebtn">
						<spring:theme code="text.lists.recommended.copytoMyLists.continueShopping" />
					</button>
				</div>
				<div class="col-md-6 col-xs-7 mob-list-save-button">
					<button class="btn-primary listpopupsavebtn text-capitalize">
						<spring:theme code="text.lists.recommended.copytoMyLists.saveAndList" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>