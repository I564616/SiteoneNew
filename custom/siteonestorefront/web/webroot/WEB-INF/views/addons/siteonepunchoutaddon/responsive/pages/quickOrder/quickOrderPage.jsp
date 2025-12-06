<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%-- <%@ taglib prefix="template" tagdir="/WEB-INF/tags/addons/siteonepunchoutaddon/responsive/template" %> --%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="quickorder" tagdir="/WEB-INF/tags/responsive/quickorder" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:theme code="product.grid.confirmQtys.message" var="gridConfirmMessage"/>

<template:page pageTitle="${pageTitle}">
	<div id="quickOrder" class="account-section" data-grid-confirm-message="${gridConfirmMessage}">
        <div class="account-section-content">
            <div class="headline">
                <spring:theme code="text.quickOrder.header" />
            </div>

            <div class="row">
                <div class="col-xs-12 col-md-7 col-lg-6">
                    <div class="marginBottom40 quickorderinfo">
                        Enter product numbers below to quickly add products to your cart. <span class="hidden-xs hidden-sm"><br/></span> You can put up to 25 products on your Quick Order list.
                      </div>  
                </div>

                <product:addToCartTitle/>
                <div class="col-xs-12 col-md-5 col-lg-6 pull-rightt hidden">
                    <div class="row quick-order__actions">
                        <div class="pull-right col-sm-3 col-md-6 col-lg-5 quick-order__add-to-cart-btn">
                            <product:productFormAddToCartButton addToCartBtnId="js-add-to-cart-quick-order-btn-top" />
                        </div>
                        <div class="pull-right col-sm-4 col-md-6 col-lg-5 text-right hidden">
                            <quickorder:quickorderResetButton resetBtnId="js-reset-quick-order-form-btn-top" resetBtnClass="quick-order__reset-link"/>
                        </div>
                    </div>
                </div>
            </div>
			
			<quickorder:quickorderListRows/>

            <div class="row">
                <div class="col-xs-12 col-md-5 col-lg-6 pull-right">
                    <div class="row quick-order__actions">
                        <div class="pull-right col-sm-3 col-md-6 col-lg-5 quick-order__add-to-cart-btn">
                            <product:productFormAddToCartButton addToCartBtnId="js-add-to-cart-quick-order-btn-bottom" />
                        </div>
                        <div class="pull-right col-sm-4 col-md-6 col-lg-5 text-right hidden">
                            <quickorder:quickorderResetButton resetBtnId="js-reset-quick-order-form-btn-bottom" resetBtnClass="quick-order__reset-link"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template:page>