<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="quickorder" tagdir="/WEB-INF/tags/responsive/quickorder" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:theme code="product.grid.confirmQtys.message" var="gridConfirmMessage"/>

<template:page pageTitle="${pageTitle}">
	<div id="quickOrder" class="account-section" data-grid-confirm-message="${gridConfirmMessage}">
        <div class="account-section-content">
            <div class="headline">
                <spring:theme code="text.quickOrder.header" />
            </div>

            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-7 col-lg-6">
                    <p class="marginBottom40 quickorderinfo">
                        <%-- <cms:pageSlot position="TopContent" var="feature">
                            <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                        </cms:pageSlot> --%>
                        <spring:theme code="text.quickOrder.instruction.first"/>
						<span class="hidden-xs hidden-sm"></br></span> <spring:theme code="text.quickOrder.instruction.second"/>
                    </p>
                </div>
  			 <input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
                <product:addToCartTitle/>
               <%--  <div class="col-xs-12 col-md-5 col-lg-6 pull-rightt">
                    <div class="row quick-order__actions">
                        <div class="pull-right col-sm-3 col-md-6 col-lg-5 quick-order__add-to-cart-btn">
                            <product:productFormAddToCartButton addToCartBtnId="js-add-to-cart-quick-order-btn-top" />
                        </div>
                        <div class="pull-right col-sm-4 col-md-6 col-lg-5 text-right">
                            <quickorder:quickorderResetButton resetBtnId="js-reset-quick-order-form-btn-top" resetBtnClass="quick-order__reset-link"/>
                        </div>
                    </div>
                </div> --%>
              
                 
                
            </div>
			<div id ="quickOrderQtyError"></div>
			<quickorder:quickorderListRows/>

            <div class="row">
                <div class="col-xs-12 col-md-5 col-sm-5 col-lg-6 pull-right">
                    <div class="row quick-order__actions">
                        <div class="pull-right col-sm-12 col-md-5 col-lg-5 quick-order__add-to-cart-btn">
                        	
                        	<c:choose>
                        	<c:when test="${orderOnlinePermissions eq true}">
                        	
                            <product:productFormAddToCartButton addToCartBtnId="js-add-to-cart-quick-order-btn-bottom" />
                            
                            </c:when>
                        	<c:otherwise>
                        		<button type="submit" id="orderOnlineATC" class="btn btn-primary pull-left btn-block" disabled="disabled" style="margin-top: 11px;">
		                      		      <spring:theme code="basket.add.to.basket" />
		                 		</button>
		                 		<cms:pageSlot position="OnlineOrderQO" var="feature">
									<cms:component component="${feature}"/>
								</cms:pageSlot>
                        	</c:otherwise>
                        	</c:choose>  
                            
                        </div>
                       
                        <div class="pull-right col-sm-4 col-md-6 col-lg-5 text-right checkAcc_msg">
                        <%-- <quickorder:quickorderResetButton resetBtnId="js-reset-quick-order-form-btn-bottom" resetBtnClass="quick-order__reset-link"/> --%>
                         <div id="BottomOrderingAccountMsg" class="pull-right" hidden><span>${orderingAccountMsg}</span></div>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</template:page>