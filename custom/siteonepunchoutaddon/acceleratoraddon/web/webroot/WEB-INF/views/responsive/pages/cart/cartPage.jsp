<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="template" tagdir="/WEB-INF/tags/addons/siteonepunchoutaddon/responsive/template"%> --%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
	
<template:page pageTitle="${pageTitle}">

	<cart:cartValidation/>
<div class="col-md-12 col-sm-12 col-xs-12 headline"><div class="row">My Shopping Cart</div> </div>
    <div class="cl"></div>
	<div class="cart-top-bar">
        <div class="col-md-3 text-right pull-right">
            <a href="" class="help js-cart-help" data-help="<spring:theme code="text.help" />"><spring:theme code="text.help" text="Help" />
                <span class="glyphicon glyphicon-info-sign"></span>
            </a>
            <div class="help-popup-content-holder js-help-popup-content">
                <div class="help-popup-content">
                    <div class="subtitle"><spring:theme code="text.shoppingcartMsg"/> <span>${cartData.code }</span></div>
                    <spring:theme code="basket.page.cartHelpContent" arguments="${sessionStore.address.phone}"/>
                </div>
            </div>
		</div>
		<div class="cl"></div>
	</div>

	<div>
        <div>
            <cms:pageSlot position="TopContent" var="feature">
                <cms:component component="${feature}"/>
            </cms:pageSlot>
        </div>
		 
	   <c:if test="${not empty cartData.entries}">
		   <cms:pageSlot position="CenterLeftContentSlot" var="feature">
			   <cms:component component="${feature}"/>
		   </cms:pageSlot>
		</c:if>
		
		 <c:if test="${not empty cartData.entries}">
			<cms:pageSlot position="CenterRightContentSlot" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
			<cms:pageSlot position="BottomContentSlot" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
		</c:if>
				
				
		<c:if test="${empty cartData.entries}">
			<cms:pageSlot position="EmptyCartMiddleContent" var="feature" element="div" class="content__empty">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
		</c:if>
	</div>
	 
	</div>
</template:page>