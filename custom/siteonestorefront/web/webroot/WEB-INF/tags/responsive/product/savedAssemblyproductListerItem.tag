<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="quantity" required="true" type="java.lang.Integer" %>
<%@ attribute name="code" required="false" type="java.lang.Long" %>
<%@ attribute name="comment" required="false" type="java.lang.String" %>
<%@ attribute name="uomid" required="false" type="java.lang.Integer" %>
<%@ attribute name="indexvalue" required="false" type="java.lang.String" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:if test="${orderOnlinePermissions ne true}">
	<c:set var="ATCOOId" value="orderOnlineATC" />
	<cms:pageSlot position="OnlineOrderSL" var="feature">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
</c:if>

<input type="hidden" value="${product.code}" class="productID_assemblyList"/> 
<spring:url value="/" var="homelink" htmlEscape="false"/>
<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>
<input type="hidden" id="variantisForceinstock" value="${product.isForceInStock}"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>
<c:choose>
				<c:when	test="${product.productType eq 'Nursery' and (quantity gt product.stock.stockLevel) and (product.inStockImage)}">
					<c:set var="showErrorMsg" value="hide" />
					<c:set var="disableAtc" value="" />
					<c:set var="redBorder" value=""/>
					
				</c:when>
				<c:otherwise>
					<c:set var="showErrorMsg" value="hide" />
					<c:set var="disableAtc" value="" />
					<c:set var="redBorder" value=""/>
				</c:otherwise>
			</c:choose>
	<ycommerce:testId code="test_searchPage_wholeProduct">
	<div class="js-my-order-qty-error js-cart-qty-error marginBottom20 text-red ${showErrorMsg}" >
        							<img class="icon-red-exclamation cart-qty-alert" src="${themeResourcePath}/images/Exclamation-point.svg" alt""/><spring:theme code="text.product.qty.alert" arguments="${product.stock.stockLevel}" />
        					  </div>
		<div class="col-sm-4 col-xs-6 assembly-list-image-wrapper">
			<div class="colored">
				<span>
					<input  aria-label="Checkbox"  id="selectedProdType${indexvalue}" class ="select_product_checkbox"  type="checkbox"   value="SelectedProduct" data-role="${indexvalue}"> <BR>
				</span>
			</div>
			<a class="list-img-sec" href="${productUrl}" title="${fn:escapeXml(product.name)}">
				<product:productPrimaryImage product="${product}" format="product"/>
			</a>
		</div>
		<div class="col-sm-5 col-xs-6 print-row-auto">
			<ycommerce:testId code="searchPage_productName_link_${product.code}">
				<a class="green-title" href="${productUrl}"><span class="bold-text">${fn:escapeXml(product.name)}</span></a>  
				<div class="cl"></div>

				<div> <c:out value="${product.itemNumber}" /></div>
				<c:if test="${product.productType eq 'Nursery' && product.isForceInStock ne true}">
				    <p class="bold marginTop10"><spring:theme code="text.variantProduct.available"/> : ${product.stock.stockLevel}</p> 
				</c:if>
				<div class="message-center marginTop10">
				<c:if test="${product.inStockImage}">
					<c:choose>					
						<c:when test="${product.isStockInNearbyBranch}">
							<common:checkmarkIcon iconColor="#ef8700"/>
						</c:when>
						<c:otherwise>
							<common:checkmarkIcon iconColor="#78a22f"/>
						</c:otherwise>	
					</c:choose>
					<p><spring:theme text="${product.storeStockAvailabilityMsg}"/></p><br/>
				</c:if>
				<c:if test="${product.notInStockImage}">
					<common:exclamatoryIcon iconColor="#ed8606"/><p><spring:theme text="${product.storeStockAvailabilityMsg}"/></p><br/>
				</c:if>
				<c:if test="${product.outOfStockImage}">
					<common:crossMarkIcon iconColor="#5a5b5d"/><p><spring:theme text="${product.storeStockAvailabilityMsg}"/></p><br/>
				</c:if>
				</div>



			</ycommerce:testId>
		 
			<c:if test="${not empty product.summary}"> 
				<div class="product__listing-description">${fn:escapeXml(product.summary)}</div>
			</c:if>
		 
			<br/>
		</div>
		
			<c:set var="/savedList/addProductComment" value="productComment"/>
			<div class="commentOverlay" id="commentOverlay_${product.code}">
				<button type="button" class="comment-overlay" id="cboxClose"><span class="glyphicon glyphicon-remove"></span></button>

		     	<form:form action="${homelink}savedList/addProductComment" method="post" modelAttribute="siteOneProductCommentForm" class="siteOneProductCommentForm save-list-detail-page" >
					<h3 class="headline save-list-detail-page"><spring:theme code="saveListDetailsPage.add.or.edit.comment"/></h3>
					<div class="saved-list-sec">
						<div class="col-md-4 col-sm-12 col-xs-5 product-image-wrapper">
							<a class="product__list--thumb" href="#" title="${fn:escapeXml(product.name)}">
					            <product:productPrimaryImage product="${product}" format="thumbnail"/>
					        </a>
					    </div>
					    <div class="col-md-6 col-sm-5 col-xs-7 product-description-wrapper">
					    	<p class="green-title">${product.name}</p>
					        <form:hidden path="listCode" value="${code}"/>
					        <form:hidden path="productCode" value="${product.code}"/>				         
					        <div class="cl"></div>				         
					    </div>
					    <div class="col-md-8 col-sm-12 col-xs-12 save-list-detail-comment-box">
					    	<form:label path="comment"><spring:theme code="savedListProductListerItem.comment"/> </form:label> 
					      	<form:textarea path="comment" maxlength="200" class="list-comment"></form:textarea>
					        <div class="margin20 text-remaining"></div>
					    </div>
					    <div class="cl"></div>
					</div>
				   	<div class="row save-list-detail-button-wrapper">
				    	<div class="col-md-3 col-sm-4 save-btn-wrapper"><input type="submit" class="btn btn-primary btn-block  product_comment_Save" value='<spring:theme code="text.button.save" text="Save" />' /></div>
				      	<!-- <input type="button" class="btn btn-primary cancelBtn" value="Delete Comment" /> -->
				      	<div class="col-md-5 col-sm-6"><c:url value="/savedList/removeProductComment?productCode=${product.code}&listcode=${code}" var="saveFormUrl" /><a href="${saveFormUrl}" class="btn btn-block btn-default"><spring:theme code="savedListProductListerItem.deleteComment" text="Delete Comment" /></a></div>
				   </div>
				</form:form>
			</div>

			<div class="col-xs-12 hidden-lg hidden-md  hidden-sm hidden-xs"></div>
			<div class="col-xs-6 hidden-lg hidden-md  hidden-sm">
			</div>
			<div class="col-xs-6 commentLinkWrapper hidden-lg hidden-md  hidden-sm">

		        <c:choose>
				<c:when test="${empty comment}">
				  <span class="hidden-xs"> <br/> <br/></span>
				 <div class="col-xs-5"></div>
				 <div class="col-xs-12 addCommentLink"><a class="saved_list_comment" href="#" id="${index.index}" data-productCode="${product.code}|${comment}"><spring:theme code="assemblyDetailsPage.add.comment"/></a></div>
				</c:when>
				<c:otherwise>
				<div class="col-xs-12 comment-text-key editCommentLinkAlign"><b><spring:theme code="savedListProductListerItem.comment"/></b></div>
				<div class="col-xs-12 comment-text-value editCommentLinkAlign" title="${comment}">&quot;${comment}&quot;</div>
				<div class="col-xs-12 editCommentLink editCommentLinkAlign"><a class="saved_list_comment" href="#" id="${index.index}" data-productCode="${product.code}|${comment}" ><spring:theme code="assemblyDetailsPage.edit.comment"/></a></div>
				</c:otherwise>
				</c:choose>
			</div>
	<%-- 	<div class="product__list--price-panel">
			<c:if test="${not empty product.potentialPromotions}">
				<div class="product__listing--promo">
					<c:forEach items="${product.potentialPromotions}" var="promotion">
						${promotion.description}
					</c:forEach>
				</div>
			</c:if>

			<ycommerce:testId code="searchPage_price_label_${product.code}">
				<div class="product__listing--price"><product:productListerItemPrice product="${product}"/></div>
			</ycommerce:testId>
		</div> --%>

		

			<div class="col-md-3 col-sm-3 col-xs-12 quantityInputSection">
		<div class="qtyBorderTopBottom">
			<div class="row">
				<div class="col-md-5 col-sm-5 col-xs-5 quantitypn"><label class="marginlt" style="line-height:34pt;" for="quantity_${indexvalue}"><spring:theme code="savedListProductListerItem.quantity" /></label></div>
				<div class="col-md-7 col-sm-7 col-xs-7 quantityinput">
					<input type="hidden" id="productCodeQtyChange"  value="${product.code}"/>	
						<input type="hidden" id="listCodeQtyChange"  value="${code}"/>
					<button class="js-update-entry-quantity-list-btn  minusQty minusQtyBtn hidden-lg hidden-sm hidden-md " type="button" >-</button>
					<input type="text" name="qty" value="${quantity}" class="form-control js-list-add-to-cart js-update-entry-quantity-input qtyChanged qtyId js-qty-updateOne ${redBorder}" id="quantity_${indexvalue}" maxlength="5" data-role="prodQty_${product.code}" data-available-qty="${product.stock.stockLevel}" data-is-nursery="${product.productType eq 'Nursery'}"/>
					<button class="js-update-entry-quantity-list-btn plusQty hidden-lg hidden-sm hidden-md " type="button" >+</button>
				</div>
			</div>
		</div>
		<!-- <br/> -->
		<c:set var="product" value="${product}" scope="request"/>
		<c:set var="addToCartText" value="${addToCartText}" scope="request"/>
		<c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/>
<div id="addToCartTitle" class="display-none">
    <div class="add-to-cart-header">
        <div class="headline">
            <span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
        </div>
    </div>
</div>
	<%-- 	<div id="savelist_add_to_cart_${product.code}"> --%>
	
		<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${homelink}cart/add">
<c:if test="${product.purchasable}">
	<input type="hidden" id="quantity" name="qty" value="${quantity}">
	<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${uomid}"> 
</c:if>
<input type="hidden" name="productCodePost" value="${product.code}"/>
<input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
<c:if test="${empty showAddToCart ? true : showAddToCart}">
	<c:set var="buttonType">button</c:set>
	<c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
		<c:set var="buttonType">submit</c:set>
	</c:if>
	 <div id="addToCartSection" class="row">
	<c:choose>
		<c:when test="${fn:contains(buttonType, 'button')}">
		
                    <c:choose>
                        <c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
                            <div class="variantButton col-md-6 col-lg-7 col-xs-12">
                                <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" >
                                    <spring:theme code="product.base.select.options"/>
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                        <ycommerce:testId code="addToCartButton">
                                <button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn" disabled="disabled">
                                <spring:theme code="basket.add.to.basket" />
                                </button>
                                </ycommerce:testId>
                        </c:otherwise>
                    </c:choose>
                     
			</c:when>
			<c:otherwise>
			  <ycommerce:testId code="addToCartButton">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-6 list-button-row mb-margin15">
			<div id="productSelect">
			 <c:choose>
            	<c:when test="${product.sellableUomsCount == 0}"> 
            		<c:choose>
		            	<c:when test="${(!product.isSellable)}"> 
		                    <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span class ="listOrderingMsg">${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn"
		                            disabled="disabled" ${disableAtc}><spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                </c:otherwise>
		            </c:choose>
            	</c:when>
            	<c:otherwise>
            	<%-- 	 <button type="button" id="sellableUoms" class="btn btn-primary btn-block" style="display:none;">
                       <spring:theme code="product.base.select.options"/>
                    </button>  --%>
                    <c:choose>
		            	<c:when test="${(!product.isSellable)}"> 
		                    <button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block" style="display:none;margin-top:0px"
		                            aria-disabled="true" disabled="disabled" ${disableAtc}> <spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span class ="listOrderingMsg">${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn" style="margin-top:0px"
		                            disabled="disabled" ${disableAtc}><spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                </c:otherwise>
		            </c:choose>
            	</c:otherwise>
            </c:choose>
            </div>
            </div>
            <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
	<input type="hidden" name="isCurrentUser" id="isCurrentUser"
		value="true" />
</sec:authorize>

				</ycommerce:testId>
			</c:otherwise>
			
	</c:choose>
	 </div>
</c:if>
</form:form>
		</div>
		<!-- </div> -->
		
		<%-- <div id="savelist_remove_from_list_${product.code}" style="display:none">
		<a href="#" class="btn btn-primary" id="saved_remove_to_cart_btn">Delete</a>
		</div> --%>
</ycommerce:testId>