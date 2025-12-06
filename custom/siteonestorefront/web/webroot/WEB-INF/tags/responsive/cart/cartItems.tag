<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%--
    ~ /*
    ~  * [y] hybris Platform
    ~  *
    ~  * Copyright (c) 2000-2017 SAP SE or an SAP affiliate company.
    ~  * All rights reserved.
    ~  *
    ~  * This software is the confidential and proprietary information of SAP
    ~  * ("Confidential Information"). You shall not disclose such Confidential
    ~  * Information and shall use it only in accordance with the terms of the
    ~  * license agreement you entered into with SAP.
    ~  *
    ~  */
--%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set value="false" var="isNationalShippingInCart"></c:set>
<c:if test="${!cartData.isTampaBranch && !cartData.isLABranch}" >
    <c:set value="true" var="isNationalShippingInCart"></c:set>
</c:if>
<spring:htmlEscape defaultHtmlEscape="true" />
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>

<c:choose>
    <c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
        <c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>
    </c:when>
    <c:otherwise>
        <c:set var="contactNo" value="${sessionStore.address.phone}"/>
    </c:otherwise>
</c:choose>
<input type="hidden" class="isMixedCartEnabled" value="${isMixedCartEnabled}"/>
<c:if test="${fn:length(cartData.entries) > 0}">
	<input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
	</c:if>
 <c:choose>
	<c:when test="${orderOnlinePermissions eq true}">
		<c:set var="enableCheckout" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="enableCheckout" value="false"/>
	</c:otherwise>
</c:choose>
	<c:set var="count" value="0" />
	<c:set var="isShippingHubOnlyCart" value="false"/>
	<c:forEach var="entry" items="${cartData.entries}">
		<c:if test="${entry.product.stockAvailableOnlyHubStore}">
			<c:set var="isShippingHubOnlyCart" value="true"/>
		</c:if>
		<c:if test="${guestUsers eq 'guest' && entry.product.hideList && count eq '0'}">
			<c:set var="count" value="1" />
			<c:set var="disableHideListGuestCheckout" value="true" />
		</c:if>		
	</c:forEach>
	<input type="hidden" id="disableHideListGuestCheckout" value="${disableHideListGuestCheckout}"/>
	<jsp:useBean id="list" class="java.util.ArrayList"/>
    <jsp:useBean id="list1" class="java.util.ArrayList"/>
   	<jsp:useBean id="list2" class="java.util.ArrayList"/>
 		
 		
	<c:forEach items="${cartData.entries}" var="entry1" varStatus="loop">
 	     <c:choose>
 	           <c:when test="${entry1.product.inStockImage}">
 	           	<c:choose>
 	           		<c:when test="${entry1.product.isStockInNearbyBranch}">
 	              			<c:set var="stockNearbyEnteries" value="${list1.add(entry1)}"/>
 	           		</c:when>
 	           		<c:otherwise>
 	           			<c:set var="stockEnteries" value="${list.add(entry1)}"/>
 	           		</c:otherwise>
 	           	</c:choose> 	             
 	          </c:when> 	           
 	          <c:otherwise>
 	          	<c:set var="backorderEnteries" value="${list2.add(entry1)}"/>
 	          </c:otherwise>
 	     </c:choose>
 	</c:forEach>
<div class="non-parcelShipping">
<c:set var="showPriceLoader" value="${not empty cartPageSize and cartPageSize ne null? cartPageSize : 50}" />
<c:set var="cartPriceLoader" value="${(!isAnonymous && cartData.entries.size() > showPriceLoader)? true: false}" />
<c:if test="${not empty list}">
    <c:if test="${isMixedCartEnabled ne true and (isShippingHubOnlyCart eq false or isNationalShippingInCart eq true)}">
    	<cart:cartEntries entries="${list}" cartPriceLoader="${cartPriceLoader}"/>
    </c:if>
    <c:if test="${isMixedCartEnabled eq true}">
    	<cart:cartEntriesMixedCart entries="${list}" />
    </c:if>
</c:if>
<c:if test="${not empty list1}">
   <c:if test="${isMixedCartEnabled ne true and (isShippingHubOnlyCart eq false or isNationalShippingInCart eq true)}">
    	<cart:cartEntries entries="${list1}" loopIndex="${fn:length(list)}" cartPriceLoader="${cartPriceLoader}"/>
    </c:if>
    <c:if test="${isMixedCartEnabled eq true}">
    	<cart:cartEntriesMixedCart entries="${list1}" loopIndex="${fn:length(list)}" />
    </c:if>
</c:if>
<c:if test="${not empty list2}">
    <c:if test="${isMixedCartEnabled ne true and (isShippingHubOnlyCart eq false or isNationalShippingInCart eq true)}">
    	<cart:cartEntries entries="${list2}" loopIndex="${fn:length(list) + fn:length(list1)}" cartPriceLoader="${cartPriceLoader}"/>
    </c:if>
    <c:if test="${isMixedCartEnabled eq true}">
    	<cart:cartEntriesMixedCart entries="${list2}" loopIndex="${fn:length(list) + fn:length(list1)}" />
    </c:if>
</c:if>
</div>

<!-- for shipping entry only -->
<c:if test="${not empty inStockparcelShippableProduct}">
	<div class="parcelShippingFulfillment hidden">
		<c:if test="${not empty inStockparcelShippableProduct and isMixedCartEnabled ne true}">
		     <cart:cartEntries entries="${inStockparcelShippableProduct}" hubStoreNumber="${hubStoreId}" cartPriceLoader="${cartPriceLoader}"/>
		</c:if>
		<c:if test="${not empty notInStockparcelShippableProduct and isMixedCartEnabled ne true}">    
		    <cart:cartEntries entries="${notInStockparcelShippableProduct}" loopIndex="${fn:length(inStockparcelShippableProduct)}" hubStoreNumber="${hubStoreId}" cartPriceLoader="${cartPriceLoader}"/>
		</c:if>
	</div>
</c:if>

<input type="hidden" id=cartID value="${cartData.code}"/>
<input type="hidden" id="recentCartIds" name="recentCartIds" value="${cartData.b2bCustomerData.recentCartIds}">
<input type="hidden" id="currentCartId" name="currentCartId" value="${cartData.b2bCustomerData.currentCartId}">






<div class="js-moveToWhislist-popup clearfix" style="display:none;">
    <div class="row">
        <div><spring:theme code="list.desc.text1"/><spring:theme code="list.desc.text2"/></div>
        <br></br>
        <input
                type="hidden" name="productCode" id="productCode" />
        <input type="hidden"  id="qty" value="1"/>
        <input type="hidden"  class="uomId_popup" value=""/>
        <input type="hidden"  name="itemCode" id="itemCode" />
        <form:form id="wishlistForm" class="wishlistForm" method="post" action="${homelink}savedList/add">
            <div class="col-sm-12 clearfix">

                <label for="popupWishlist"><spring:theme code="assemblyDetailsPage.select.list" /></label> <!-- <input
				type="hidden" name="productCode" id="productCode" /> -->
                <input type="hidden"
                       class="wishListName" name="wishListName" />
                <input type="hidden" name="wishlist" class="wishlist"
                       value="${wishlistName}" />

                <!-- <input type="hidden"  id="qty" value="1"/> -->
                <select
                        class="form-control popupWishlist" name="whishlist">
                    <!--option will generated by loop -->
                    <option value="" selected><spring:theme code="assemblyDetailsPage.list.name" /></option>
                    <c:forEach var="wishlists" items="${allWishlist}">
                        <option value="${wishlists.code}" label="${wishlists.name }">${wishlists.name}</option>
                    </c:forEach>

                </select>
                <div class="col-sm-12 margin20">
                    <div class="row">
                        <a href="#" class="createNewListLinkProduct"><u><spring:theme code="cartItems.createList" />&#8594</u></a>
                    </div>
                </div>
                <div class="col-sm-6 margin20">
                    <div class="row">
                        <input type="button" class="buttonCartMove btn btn-block btn-primary addToWhishlistpopupcart" value='<spring:theme code="cartItems.addToList" />' />
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

<input type="hidden" class="currentForm" value="">
<input type="hidden" name="createWishList" class="createWishList"
       value="${createWishList}" />


<div class="js-createNewSaveList-popup" style="display: none">
    <!--content of 2nd popup-->

    <p id="status"></p>
    <span><spring:theme code="cartItems.enterName" /></span>

    <%-- <input type="hidden" name="wishlist" class="wishlist"
value="${wishlistName}" /> --%>

    <c:url value="/savedList/createWishlist" var="createWishlist" />
    <form:form id="createWishlistForm" class="createWishlistForm" method="post"
               action="${createWishlist}">
        <!--give the name of controller-->
        <div id="empty_listName" style="display: none"><p class="panel-body" style="margin-bottom:-13px; color:red;padding-left:0px;"><spring:theme code="saved.list.empty"/></p></div>
        <label class="col-sm-12" for="newWishlist"><spring:theme code="cartItems.newListName" /></label>
        <div class="col-sm-12 clearfix">
              <input type="hidden" name="productCode" id="productCode"/>
                <input class="form-control wishListName" name="wishListName" />
            <input type="hidden" id="inventoryUOMIDVal"  value="${inventoryUOMIDParam}"/>
            <div class="has-error padding10 margin-top-20 bg-danger existing-listname" style="display:none;">
            	<span class="help-block"><spring:theme code="saved.list.duplicate"/></span>
            </div>
        </div>
        <div class="col-sm-12 margin-top clearfix create-wish-list-btn">
            <button type="button" class="createWishlist btn btn-default"><spring:theme code="cartItems.addToNewList" /></button>
        </div>
    </form:form>
</div>


<div id="loginId" style="display: none">


    <c:url value="/login/auth" var="oktaLoginActionUrl" />
    <c:url value="/j_spring_security_check" var="loginActionUrl" />
    <div class="login-section">

        <user:login actionNameKey="login.login" action="${loginActionUrl}" />

    </div>

</div>
<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
    <input type="hidden" name="isCurrentPLPUser" id="isCurrentPLPUser"
           value="true" />
</sec:authorize>


<input type="hidden" id="enableCheckout" value="${enableCheckout}"/>
<product:productOrderFormJQueryTemplates />
<storepickup:pickupStorePopup />
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>