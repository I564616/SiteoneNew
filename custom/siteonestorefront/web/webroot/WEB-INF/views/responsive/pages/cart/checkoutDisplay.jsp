<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<c:url value="/cart/checkout" var="checkoutUrl" scope="session"/>
<c:set var="enableCheckoutIfOrderingAccount" value="true"/>
<%-- <div class="row">
    <div class="col-xs-12 col-sm-10 col-md-7 col-lg-6 pull-right cart-actions--print">
        <div class="express-checkout">
            <div class="headline"><spring:theme code="text.expresscheckout.header"/></div>
            <strong><spring:theme code="text.expresscheckout.title"/></strong>
            <ul>
                <li><spring:theme code="text.expresscheckout.line1"/></li>
                <li><spring:theme code="text.expresscheckout.line2"/></li>
                <li><spring:theme code="text.expresscheckout.line3"/></li>
            </ul>
            <sec:authorize access="isFullyAuthenticated()">
                <c:if test="${expressCheckoutAllowed}">
                    <div class="checkbox">
                        <label>
                            <c:url value="/checkout/multi/express" var="expressCheckoutUrl" scope="session"/>
                            <input type="checkbox" class="express-checkout-checkbox" data-express-checkout-url="${expressCheckoutUrl}">
                            <spring:theme text="I would like to Express checkout" code="cart.expresscheckout.checkbox"/>
                        </label>
                     </div>
                </c:if>
           </sec:authorize>
        </div>
    </div>
</div> --%>

<div class="col-md-12 col-sm-12 margin20 print-hidden">
    <div class="row">
    <div class="mobileButtonCart col-xs-12 col-sm-12 col-md-12 cartPaddingrt">
           
             <span class="visible-sm"><br/></span>
             
            <c:choose>
            <c:when test="${orderOnlinePermissions eq true}"> 
            
            <ycommerce:testId code="checkoutButton">
                <button class="btn btn-primary btn-block btn--continue-checkout cart-checkout-btn  js-continue-checkout-button" data-checkout-url="${checkoutUrl}">
                    <spring:theme code="checkout.checkout"/>
                </button>
            </ycommerce:testId>
            
            </c:when>
            <c:otherwise>
               <cms:pageSlot position="OnlineOrderCO" var="feature">
							<cms:component component="${feature}"/>
				</cms:pageSlot>
               <button class="btn btn-primary btn-block btn--continue-checkout cart-checkout-btn js-continue-checkout-buttonoo" type="button" id="orderOnlineATC" >
                    <spring:theme code="checkout.checkout"/>
                </button>
             </c:otherwise> 
             </c:choose>
              
             <button class="btn btn-default btn-block btn--continue-shopping cart-checkout-btn js-continue-shopping-button " data-continue-shopping-url="${continueShoppingUrl}">
                <spring:theme code="cart.page.continue"/>
            </button>
            <c:if test="${!isOrderingAccount}">
            	   <c:set var="enableCheckoutIfOrderingAccount" value="false"/>
            	   <div class="cl"></div>
            	   <br/>
		           <div class="col-md-8 text-left col-md-offset-2" style="color:red;">${orderingAccountMsg}</div> 
		    </c:if>
      </div>
      
        
	<%-- <div class="col-xs-12 col-sm-12 col-md-6 cartPaddingrt">
            <button class="btn btn-default btn-block btn--continue-shopping js-continue-shopping-button mb-margin" data-continue-shopping-url="${continueShoppingUrl}">
                <spring:theme code="cart.page.continue"/>
            </button>
      </div>
      <div class="col-xs-12 col-sm-12 col-md-6 cartPaddingrt">
        <span class="visible-sm"><br/></span>
            <ycommerce:testId code="checkoutButton">
                <button class="btn btn-primary btn-block btn--continue-checkout mb-margin  js-continue-checkout-button" data-checkout-url="${checkoutUrl}">
                    <spring:theme code="checkout.checkout"/>
                </button>
            </ycommerce:testId>
            <c:if test="${!isOrderingAccount}">
            	   <c:set var="enableCheckoutIfOrderingAccount" value="false"/>
		           <span>${orderingAccountMsg}</span> 
		    </c:if>
     </div>  --%> 
     <input type="hidden" value="${sessionStore.address.region.isocodeShort}"  id="california_location"/>
        <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
            <c:if test="${not empty siteQuoteEnabled and siteQuoteEnabled eq 'true'}">
                <div class="col-sm-12 col-md-6">
                    <button class="btn btn-default btn-block btn--continue-shopping mb-margin js-continue-shopping-button"    data-continue-shopping-url="${createQuoteUrl}">
                        <spring:theme code="quote.create"/>
                    </button>
                </div>
            </c:if>
        </sec:authorize>

       
    </div>
</div>

  <div class="visible-sm hidden-md hidden-lg visible-xs print-hidden">
         
            <div class="store-specialty-heading margin20"><b><spring:theme code="checkoutDisplay.help" />:</b></div>
            <%--    <p><spring:theme code="basket.helptext" arguments="${sessionStore.address.phone}"/></p> --%>
            <ul>
            <li><spring:theme code="checkoutDisplay.call" /> <a class="tel-phone" style="text-decoration:none;" href="tel:(800)748-3663">1-800-SITEONE (1-800-748-3663)</a></li>
            <li><a href="mailto:${siteoneSupportEmail}">${siteoneSupportEmail}</a>
            </ul>
    </div>
    
    </br>
    
    <div class="row">
     <div class="col-xs-12 col-md-6 col-lg-6 hidden-lg hidden-md print-hidden">
            <cart:cartVoucher cartData="${cartData}" voucherDisplay="mobile"/>
           </div>
           </div>
    
<%-- <c:if test="${showCheckoutStrategies && not empty cartData.entries}" >
    <div class="cart__actions">
        <div class="row">
            <div class="col-xs-12 col-sm-5 col-md-3 col-lg-2 pull-right">
                <input type="hidden" name="flow" id="flow"/>
                <input type="hidden" name="pci" id="pci"/>
                <select id="selectAltCheckoutFlow" class="doFlowSelectedChange form-control">
                    <option value="select-checkout"><spring:theme code="checkout.checkout.flow.select"/></option>
                    <option value="multistep"><spring:theme code="checkout.checkout.multi"/></option>
                    <option value="multistep-pci"><spring:theme code="checkout.checkout.multi.pci"/></option>
                </select>
                <select id="selectPciOption" class="display-none">
                    <option value=""><spring:theme code="checkout.checkout.multi.pci.select"/></option>
                    <c:if test="${!isOmsEnabled}">
                        <option value="default"><spring:theme code="checkout.checkout.multi.pci-ws"/></option>
                        <option value="hop"><spring:theme code="checkout.checkout.multi.pci-hop"/></option>
                    </c:if>
                    <option value="sop"><spring:theme code="checkout.checkout.multi.pci-sop" text="PCI-SOP" /></option>
                </select>
            </div>
        </div>
    </div>
</c:if> --%>
<input type="hidden" id="enableCheckoutIfOrderingAccount" value="${enableCheckoutIfOrderingAccount}"/>