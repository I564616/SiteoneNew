<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<script language="javascript" type="text/javascript">
	function printPage() {
		$(".showPrint").css("display", "block");
		var now = new Date();
		var month = now.getMonth() + 1;
		var timestamp = ((((month) < 10) ? ("0" + month) : (month))
				+ '/'
				+ ((now.getDate() < 10) ? ("0" + now.getDate()) : (now
						.getDate()))
				+ '/'
				+ now.getFullYear().toString().substr(2, 2)
				+ " "
				+ ((now.getHours() > 12) ? (now.getHours() - 12) : (now
						.getHours()))
				+ ':'
				+ ((now.getMinutes() < 10) ? ("0" + now.getMinutes()) : (now
						.getMinutes())) + ' ' + ((now.getHours() > 11) ? ("p.m.")
				: ("a.m.")));
		document.getElementById("printedTime").innerHTML = timestamp;
		window.print();
	}
</script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$(document.body).on('change','.js-assembly-qty',function(event){
		var assemblyCount = $(".js-assembly-qty").val();
	    if(!isNormalInteger(assemblyCount)){    
		    $(".js-assembly-qty").val("1");
	    }
	});
});

function isNormalInteger(str) {
    str = str.trim();
    if (!str) {
        return false;
    }
    str = str.replace(/^0+/, "") || "0";
    var n = Math.floor(Number(str));
    return n !== Infinity && String(n) === str && n > 0;
}
</script>

<template:page pageTitle="${pageTitle}">
	<spring:url value="/" var="homelink" htmlEscape="false"/>
	<c:set var="assemblyNameMob" value="${savedListData.name}" />
	<h1 class="headline hidden-md hidden-lg">${assemblyNameMob}</h1>
		<div class="pull-right col-sm-12 col-xs-12 col-md-6 linkActions">
			<div class="row">

			<!--Links on details page  -->

			<span class="list-links"> <a href="#" onclick="printPage()"><span
					class="glyphicon glyphicon-print"></span><span
					class="assembly-link-name"><spring:theme
							code="assemblyDetailsPage.print" /></span> </a>
			</span> <span class="list-links"> <a href="#" id="shareassemblyemail"><span
					class="glyphicon glyphicon-envelope"></span><span
					class="assembly-link-name"><spring:theme
							code="assemblyDetailsPage.email" /></span> </a> <input type="hidden"
				value="${savedListData.code}" id="assemblyCode" />
			</span>
			<c:choose>
				<c:when test="${savedListData.isModified}">
					<span class="list-links"> <a href="#" id="share_Saved_List"><span
							class="glyphicon glyphicon-share"></span><span
							class="assembly-link-name"><spring:theme
									code="assemblyDetailsPage.share" /></span> </a>
		</span>
		<span class="list-links">
						<a href="/assembly/editList?code=${savedListData.code}"><span
							class="glyphicon glyphicon-edit"></span><span class="assembly-link-name">Edit</span>
						</a>
					</span>
				</c:when>
				<c:otherwise>
					<span class="list-links">
						<a href="#" id=""><span
							class="disableOption glyphicon glyphicon-share"></span><span class="assembly-link-name">Share</span>
						</a>
					</span>
					<span class="list-links">
						<a href="#"
							class="disableOption"> <span class="glyphicon glyphicon-edit"></span><span class="assembly-link-name">Edit</span>
						</a>
					</span>
				</c:otherwise>
			</c:choose><!--Links on detais page  -->
			</div>
		</div>
		<c:set var="assemblyName" value="${savedListData.name}" />
		<h1 class="headline hidden-sm hidden-xs assembly-name">${assemblyName}</h1>

		<div class="cl"></div>
		<input type="hidden" value="${savedListData.code}"
			id="wishListListName" />

		<div class="row addProductContainer col-md-6 pull-right">

			<div id="base_product_error" style="display: none;"
				class="col-md-7 col-sm-9 col-xs-9 ">
				<p class="panel-body" style="color: red;">
					<spring:theme code="saved.list.product.base.variant" />
				</p>
			</div>
			<div id="uom_product_error" style="display: none;"
				class="col-md-7 col-sm-9 col-xs-9 ">
				<p class="panel-body" style="color: red;">
					<spring:theme code="saved.list.product.uom" />
				</p>
			</div>
			<div id="invalid_code" style="display: none;"
				class="col-md-7 col-sm-9 col-xs-9">
				<p class="panel-body" style="margin-bottom: 2px; color: red;">
					<spring:theme code="saved.list.product.not.found" />
				</p>
			</div>
			<!-- <span id="invalid_code" style="display: none;">Product Code is not Valid</span> -->
			<div class="col-md-7 col-sm-7 col-xs-12 padding0">
			<div id="empty_code" style="display: none;">
				<p class="panel-body" style="margin-bottom: 2px; color: red;">
					<spring:theme code="saved.list.product.code.empty" />
				</p>
			</div>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-12 padding0">
			<div id="empty_quantity" style="display: none;">
				<p class="panel-body" style="margin-bottom: 2px; color: red;">
					<spring:theme code="saved.list.product.quantity.empty" />
				</p>
			</div>
			</div>
			<!-- <span id="empty_code" style="display: none;">Please enter Product Code</span> -->

			<div class="col-sm-7 col-xs-9 searchproductInput">

				<div class="form-group">
					<label for="savedListSearch"><spring:theme
							code="text.assembly.addheader" /></label>
					<div class="cl"></div>
					<c:choose>
						<c:when test="${savedListData.isModified}">
							<input type="text" class="form-control" name="serachBox"
								placeholder="<spring:theme code="assemblyDetailsPage.enter.product" />"
								id="savedListSearch" />
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" disabled="disabled"
								name="serachBox"
								placeholder="<spring:theme code="assemblyDetailsPage.enter.product" />"
								id="savedListSearch" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div class="col-sm-2 col-xs-3 quantityInputWrapper">
				<div class="">
					<div class="form-group">
						<label for="quantityId" class="quantityLabel"><spring:theme
								code="assemblyDetailsPage.quantity" /> </label>
						<div class="cl"></div>
						<div class="quantity-text-box-wrapper">
							<c:choose>
								<c:when test="${savedListData.isModified}">
									<input class="form-control qtyId" type="text" name="quantity"
										id="quantityId" maxlength="5" value="1" />
								</c:when>
								<c:otherwise>
									<input class="form-control qtyId" type="text"
										disabled="disabled" name="quantity" id="quantityId"
										maxlength="5" value="1" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>

			</div>

			<div class="col-sm-3 col-xs-12 add-to-assembly-wrapper">
				<label class="hidden-xs">&nbsp;</label>
				<div class="cl"></div>
				<c:choose>
					<c:when test="${savedListData.isModified}">
						<input type="button"
							value='<spring:theme code="basket.add.to.cart" />'
							class="btn btn-primary btn-block saved_add_to_cart_btn"
							id="add_Product_to_Assembly">
					</c:when>
					<c:otherwise>
						<input type="button"
							value='<spring:theme code="basket.add.to.cart" />'
							class="btn btn-primary btn-block saved_add_to_cart_btn disabled"
							id="add_Product_to_Assembly">
					</c:otherwise>
				</c:choose>

			</div>

		</div>


		<div class="cl"></div>
		<div class="row">
			<div class="col-md-3 col-sm-12 col-xs-12 hidden-xs assembDesc">

				<div class="saved-list-left">
					<div class="margin20">
						<p>
							<b><spring:theme code="text.savedList.description" /></b>
						</p>
						<div class="margin20">
							<c:if test="${not empty savedListData.description}">
				${savedListData.description}
				</c:if>
							<c:if test="${empty savedListData.description}">
								<a
									href="<c:url value="/assembly/editList?code="/>${savedListData.code}"><spring:theme
										code="assemblyDetailsPage.add.desc" /></a>
							</c:if>
							<p></p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-5 printedTimeWrapper" style="display: none;">
				<span class="showPrint"><b><spring:theme
							code="assemblyDetailsPage.printed" />:</b></span> <span id="printedTime"></span>
				<div class="printdesc">
					<spring:theme code="text.savedList.print.desc" />
				</div>
			</div>


			<div class="product__list col-md-8 col-sm-12 col-xs-12 pull-right">

	<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">

				<c:forEach items="${savedListData.entries}" var="products"
					varStatus="index">
					<c:set var="productList"
						value="${index.first ? '' : productList} ${products.product.itemNumber}|${products.qty}|${products.inventoryUom}" />
						<c:set var="productLists"
						value="${index.first ? '' : productLists} ${products.product.code}|${products.qty}|${products.inventoryUom}" />
					<c:set var="/assembly/addProductComment" value="productComment" />
					<div class="commentOverlay"
						id="commentOverlay_${products.product.code}">
						<button type="button" class="comment-overlay" id="cboxClose">
							<span class="glyphicon glyphicon-remove"></span>
						</button>
						<form:form action="${homelink}assembly/addProductComment" method="post"
							modelAttribute="siteOneProductCommentForm"
							class="siteOneProductCommentForm">
							<h3 class="headline save-list-detail-page">
								<spring:theme code="assemblyDetailsPage.add.edit" />
							</h3>
							<div class="saved-list-sec comment-popup">
								<div class="col-md-4 col-sm-7 col-xs-5">
									<a class="product__list--thumb" href="#"
										title="${fn:escapeXml(products.product.name)}"> <product:productPrimaryImage
											product="${products.product}" format="product" /></a>
								</div>
								<div
									class="col-md-6 col-sm-5 col-xs-7 product-description-wrapper">
									<p class="green-title">
										<b>${products.product.name}</b>
									</p>
									<form:hidden path="listCode" value="${code}" />
									<form:hidden path="productCode"
										value="${products.product.code}" />
								</div>
								<div
									class="col-md-8 col-sm-12 col-xs-12 save-list-detail-comment-box">
									<form:label path="comment">
										<spring:theme code="assemblyDetailsPage.comment" />:</form:label>
									<div class="cl"></div>
									<form:textarea path="comment" rows="1" cols="60"
										maxlength="200" class="list-comment"></form:textarea>
									<div class="margin20 text-remaining" style="color: #999;"></div>
								</div>

								<div class="cl"></div>
							</div>
							<div class="hidden-xs">
								<br />
							</div>
							<div class="row">
								<div class="col-md-3">
									<input type="submit"
										class="btn btn-primary btn-block product_comment_Save"
										value='<spring:theme code="text.button.save" text="Save" />' />
								</div>
								<!-- <input type="button" class="btn btn-primary cancelBtn" value="Delete Comment" /> -->
								<div class="col-md-5">
									<a
										href="<c:url value="/assembly/removeProductComment?productCode="/>${products.product.code}&listcode=${savedListData.code}"
										class="btn btn-block btn-default"><spring:theme
											code="assemblyDetailsPage.delete.comment" /></a>
								</div>

							</div>

						</form:form>
					</div>
					<div class="">
						<div class="saved-list-sec">
							<div class="row box-effect">
								<input id="prodCode${index.index}" type="hidden" name="code"
									value="${products.product.code}"> <input
									id="prodQty_${products.product.code}" type="hidden" name="qty"
									value="${products.qty}" class="	ValueId">  

								<product:savedAssemblyproductListerItem
									product="${products.product}" quantity="${products.qty}"
									comment="${products.entryComment}" code="${savedListData.code}"
									indexvalue="${index.index}" />
								<input type="hidden" id="listSize"
									value="${fn:length(savedLists)}" />


								<div id="editListBtn"
									class="col-md-3  col-sm-3  col-xs-12 pull-right">
									<c:if test="${fn:length(savedLists) gt 0}">
										<button id="editList"
											class="edit-list-btn editList <c:if test='${!isOrderingAccount}'>edit-btn-for-billing</c:if>">
											<spring:theme code="assemblyDetailsPage.edit" />
											<span class="caret pull-right"></span>
										</button>

										<div class="fetchWishList" style="display: none">
											<div class="col-sm-12 clearfix">
												<label id="moveToList" for="saveWishlist"><b><spring:theme
															code="assemblyDetailsPage.move.assembly" /></b></label> <input
													type="hidden" name="addproductCode" id="addproductCode"
													value="${products.product.itemNumber}" /> <input type="hidden"
													name="savedListName" id="savedListName"
													value="${savedListData.code}" /> <input type="hidden"
													name="qty" id="assembly_qty_${products.product.itemNumber}"
													value="${products.qty}" /> <select size="5"
													class="form-control moveToAssembly" name="savelist"
													id="saveWishlist">
													<!--option will generated by loop -->
													<c:forEach var="wishlists" items="${savedLists}">
														<option value="${wishlists.code}"
															label="${wishlists.name}">${wishlists.name}</option>
													</c:forEach>

												</select> <a href="#" class="deleteProdFromAssembly"><spring:theme
														code="assemblyDetailsPage.delete" /></a>

											</div>

										</div>

									</c:if>
									<c:if test="${fn:length(savedLists) lt 1}">
										<input type="hidden" name="addproductCode" id="addproductCode"
											value="${products.product.itemNumber}" />
										<input type="hidden" name="savedListName" id="savedListName"
											value="${savedListData.code}" />
										<input type="hidden" name="qty" id="qty"
											value="${products.qty}" />

										<a href="#" class="deleteProdFromAssembly"><spring:theme
												code="assemblyDetailsPage.delete" /></a>
									</c:if>
								</div>




								<div
									class="col-md-5 col-sm-4 col-xs-12 commentLinkWrapper hidden-xs">
									<c:choose>
										<c:when test="${empty products.entryComment}">
											<!-- <span class="hidden-sm"><br /> <br /></span>
								<span class="hidden-xs"> <br /> <br /></span> -->
											<br />
											<br />
											<div class="col-md-12 col-sm-12 col-xs-12 addCommentLink">
												<a class="saved_list_comment" href="#" id="${index.index}"
													data-productCode="${products.product.code}|${fn:escapeXml(products.entryComment)}"><spring:theme
														code="assemblyDetailsPage.add.comment" /></a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-12 col-sm-12 col-xs-12 comment-text-key">
												<b><spring:theme code="assemblyDetailsPage.comment" />:</b>
											</div>
											<div class="col-md-12 col-sm-12 col-xs-12 comment-text-value"
												title="${fn:escapeXml(products.entryComment)}">
												&quot;${fn:escapeXml(products.entryComment)}&quot;</div>
											<div class="col-md-12 col-sm-12 col-xs-12 editCommentLink">
												<a class="saved_list_comment" href="#" id="${index.index}"
													data-productCode="${products.product.code}|${fn:escapeXml(products.entryComment)}"><spring:theme
														code="assemblyDetailsPage.edit.comment" /></a>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="cl"></div>
							</div>
						</div>


					</div>
				</c:forEach>

				<div class="margin20">
					<c:if test="${not empty savedListData.entries}">
						<div class="col-md-5 col-sm-4 hidden-xs"></div>
						<div class="col-xs-12 col-md-3 col-sm-4">
							<div class="row">
								<div class="pull-left assembly-label">
									<label><b><spring:theme
												code="assemblyDetailsPage.quantity" /></b></label>
								</div>
								<div class="col-xs-5 col-md-8 col-sm-8">
									<input type="text"
										class="form-control text-right qtyId js-assembly-qty"
										maxlength="5" />
								</div>
							</div>
							<div class="cl hidden-md hidden-lg">
								<br />
							</div>
						</div>
						<div class="col-xs-12 col-md-4 col-sm-4">
							<div class="row add-assembly-to-cart-btn">
								<c:if test="${not empty savedListData.entries}">
									<input type="hidden" class="saveList" value="${productList}">
									<a href="<c:url value="/assembly/addAssemblyToCart?wishListCode="/>"
										class="btn btn-primary btn-block pull-right addAssemblyListToCartLink"><spring:theme
											code="assemblyDetailsPage.add.cart" /></a>
								</c:if>
							</div>

						</div>
						<div class="cl hidden-xs hidden-sm">
							<p></p>
						</div>
						<div class="col-md-8 col-sm-8 hidden-xs"></div>
						<div
							class="col-xs-12 col-md-4 col-sm-4   hidden-xs hidden-sm add-assembly-to-list-btn">
							<div class="row">
								<a href="#" class="pull-right addAssemblyToList"><spring:theme
										code="assemblyDetailsPage.add.list" /></a>
							</div>
						</div>
						<div class="cl hidden-md hidden-lg hidden-sm">
							<br />
						</div>
						<div class="col-md-8 col-sm-8 hidden-xs"></div>
						<div
							class="col-xs-12 col-md-4 col-sm-4 pull-left hidden-lg hidden-md add-assembly-to-list-btn">
							<div class="row">
								<a href="#" class="pull-left addAssemblyToList"><spring:theme
										code="assemblyDetailsPage.add.list" /></a>
							</div>
						</div>
					</c:if>
				</div>
			</div>

			<div class="col-xs-12 hidden-lg hidden-md hidden-sm assembDesc">

				<div class="saved-list-left assembly-description-mobile">
					<div>
						<p>
							<b><spring:theme code="text.savedList.description" /></b>
						</p>
						<div class="margin20">
							<c:if test="${not empty savedListData.description}">
				${savedListData.description}
				</c:if>
							<c:if test="${empty savedListData.description}">
								<a
									href="<c:url value="/assembly/editList?code="/>${savedListData.code}"><spring:theme
										code="assemblyDetailsPage.add.desc" /></a>
							</c:if>
							<p></p>
						</div>
					</div>
				</div>
			</div>


		</div>
		<div class="js-moveToWhislist-popup clearfix" style="display: none;">
			<div class="row">
				<div>
					<spring:theme code="list.desc.text1" />
					<spring:theme code="list.desc.text2" />
				</div>
				<br>
				<form:form class="wishlistForm">
					<div class="col-sm-12 clearfix wishlist-input-wrapper">
						<input type="hidden" class="productCodes" value="${productList}" />

						<label for="popupAddToAssembly"><spring:theme
								code="assemblyDetailsPage.select.list" /></label> <select
							class="form-control allwishlist" name="whishlist" id="popupAddToAssembly">
							<%-- Options are being fetched thorough ajax call using listOfListsPage --%>
						</select>
						<div class="col-sm-12 margin20">
							<div class="row">
								<a href="#" class="createNewListLinkProduct"><u><spring:theme
											code="assemblyDetailsPage.create.new.list" /></u></a>
							</div>

						</div>
						<div class="col-sm-6 margin20">
							<div class="row">
								<input type="button"
									class="btn btn-block btn-default addAssemblyToWishListpopup"
									value='<spring:theme code="saveListDetailsPage.add.to.list" />' />
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>


		<input type="hidden" name="createWishList" class="createWishListVal"
			value="${createWishList}" /> <input type="hidden" name="wishlist"
			id="wishListId" value="${wishlistName}" />


		<div class="js-createNewSaveList-popup" style="display: none">
			<!--content of 2nd popup-->

			<p id="status"></p>
			<span><spring:theme code="assemblyDetailsPage.text1" /></span>
			<form:form class="createWishlistForm" action="#">
				<!--give the name of controller-->
				<div id="empty_listName" style="display: none">
					<p class="panel-body"
						style="margin-bottom: -13px; color: red; padding-left: 0px">
						<spring:theme code="saved.list.empty" />
					</p>
				</div>
				<label class="col-sm-12" for="newWishlist"><spring:theme
						code="assemblyDetailsPage.new.list.name" /></label>
				<div class="col-sm-12 clearfix">
					<input class="form-control" id="wishListName" name="wishListName" class="newlistname"/>
					<input type="hidden" class="productLists" value="${productLists}" />

				</div>
				<div class="cl"></div>
				<div class="has-error padding10 margin-top-20 bg-danger existing-listname" style="display:none;"><span class="help-block"><spring:theme code="saved.list.duplicate"/></span></div>
				<div class="cl"></div>
				<div class="col-sm-12 margin-top clearfix create-wish-list-btn">
					<button type="button" id="createWishlistFromAssembly"
						class="btn btn-default">
						<spring:theme code="assemblyDetailsPage.add.new.list" />
					</button>
				</div>
			</form:form>
		</div>

		<!--division for share functionality  -->
		<div id="share_overlay">
				<form:form modelAttribute="siteoneShareSavedListForm"
					id="siteoneShareSavedListForm">
					<button type="button" class="comment-overlay" id="cboxClose">
						<span class="glyphicon glyphicon-remove"></span>
					</button>
					<form:hidden path="code" value="${code}" />
					<form:hidden path="listname" value="${assemblyName}" />
					<div class="headline">
						<form:label path="users">
							<spring:theme code="assemblyDetailsPage.share.assembly" />
						</form:label>
					</div>
					<p class="share-assembly-description content-margin">
						<spring:theme code="assemblyDetailsPage.text2" />
					</p>
					<p class="assembly-name-title black-title">
						<b>${assemblyName}</b>
					</p>
					<p class="share-assembly-title">
						<spring:theme code="assemblyDetailsPage.share.with" />
					</p>
					 <div class="textBox">
					 <div class="share-list-user js-share-email-error hidden"><spring:theme code="register.email.invalid"/></div>
					<input class="inputTextBox js-assembly-share-input form-control" type='text' onkeydown="return (event.keyCode!=13);" required="required"/>
		   			 <ul id="listValues"></ul>
		   <form:hidden path="users" id="users" value=""/>
		 </div>
					
					<div class="form-group margin20 text-area-container">
						<form:label path="note">
							<spring:theme code="assemblyDetailsPage.add.note" />
						</form:label>
						<form:textarea path="note" rows="5" cols="30" maxlength="200"
							class="form-control share-comment"
							style="min-height:145px;resize:none;" />
					</div>
					<div class="margin20 text-area-disclaimer"></div>
					<div class="row share-assembly-actions-wrapper">
						<div class="col-md-6 col-sm-6 col-xs-12">
							<input type="button"
								value='<spring:theme code="basket.save.cart.action.cancel" />'
								class="btn btn-default btn-block cancelBtn" />
						</div>
						<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="button"
								value='<spring:theme code="saveListDetailsPage.share" />'
								class="btn btn-primary btn-block shareAssemblyDetailBtn" disabled="disabled" />
						</div>
					</div>
				</form:form>
		</div>
		<div class="overlayBackground"></div>
		<!--division for share functionality  -->
</template:page>