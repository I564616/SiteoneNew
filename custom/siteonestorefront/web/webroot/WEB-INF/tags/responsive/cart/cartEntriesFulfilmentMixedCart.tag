<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="isUserIdentified" required="true" type="java.lang.Boolean" %>
<%@ attribute name="isDeliveryThresholdNotMet" required="true" type="java.lang.Boolean" %>
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
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>


<div class="row cart-item-icon-container cart-item-icon-container--mixedcart" data-entrynumber="${entry.entryNumber}" >
			<c:if test="${isUserIdentified}">
				<div class="col-xs-12 padding0 cart-select-fulfillment">
					<spring:theme code="cart.lineitem.fulfillment.select"/>
				</div>
			</c:if>
			<div class="item-fulfillment-container row marginTop20 flex-md">
            
            	<div data-delivery-mode="pickuphome" data-storeid="${entry.product.pickupHomeStoreInfo.storeId}" data-prodcode="${entry.product.code}" class="col-md-3 col-xs-12 cart-item-col cart-item-col--mixedcart  js-line-item-fullfilment ${(entry.product.pickupHomeStoreInfo.isEnabled &&(isUserIdentified)) ? 'enabled':''} ${(entry.product.pickupHomeStoreInfo.isEnabled) ? '':'hidden-xs hidden-sm'} ">
            		
            			<div class="cart-item-icon cart-item-icon--mixedcart first-icon flex flex-center ${(entry.defaultFulfillmentType eq 'pickuphome') && (isUserIdentified eq true) ? 'selected-fulfillment':''}">
            				<common:pickUpIcon height="18" width="30" iconColor="${entry.product.pickupHomeStoreInfo.isEnabled ? '#000' : '#ccc'}" greenFill="${(entry.defaultFulfillmentType eq 'pickuphome') && (isUserIdentified eq true)? 'green-fill': ''}"/>
            				<div class="pad-lft-10 ${entry.product.pickupHomeStoreInfo.isEnabled ?'black-title':'text-grey'}">
            					 ${entry.product.pickupHomeStoreInfo.stockAvailablityMessage}
            				</div>
            				
            			</div>
            			
            		
            	</div>
           
                <div data-delivery-mode="pickupnearby" data-storeid="${entry.product.pickupNearbyStoreInfo.storeId}" data-prodcode="${entry.product.code}" class="col-md-3 col-xs-12 cart-item-col cart-item-col--mixedcart  js-line-item-fullfilment ${(entry.product.pickupNearbyStoreInfo.isEnabled &&(isUserIdentified)) ? 'enabled':''} ${(entry.product.pickupNearbyStoreInfo.isEnabled) ? '':'hidden-xs hidden-sm'}" >
            		
            			<div class="cart-item-icon cart-item-icon--mixedcart flex flex-center ${(entry.defaultFulfillmentType eq 'pickupnearby' ) && (isUserIdentified eq true) ? 'selected-fulfillment':''} ${(entry.defaultFulfillmentType eq 'pickuphome' &&(isUserIdentified))?'border-left-none-md':''}" >
            				<common:pickUpIcon height="18" width="30" iconColor="${entry.product.pickupNearbyStoreInfo.isEnabled ?  '#000': '#ccc'}" greenFill="${(entry.defaultFulfillmentType eq 'pickupnearby') && (isUserIdentified eq true)? 'green-fill': '' }"/>
            				<div class="pad-lft-10 ${entry.product.pickupNearbyStoreInfo.isEnabled ?'black-title':'text-grey'}">
            					${entry.product.pickupNearbyStoreInfo.stockAvailablityMessage}
            				</div>
            				
            			</div>
            			
            			
            		</div>
            	
           
            	<div data-delivery-mode="delivery" data-storeid="${entry.product.deliveryStoreInfo.storeId}" data-prodcode="${entry.product.code}" class="col-md-3 col-xs-12 cart-item-col cart-item-col--mixedcart  js-line-item-fullfilment ${(entry.product.deliveryStoreInfo.isEnabled &&(isUserIdentified)) ? 'enabled':''} ${(entry.product.deliveryStoreInfo.isEnabled) ? '':'hidden-xs hidden-sm'}" >
            		
            			<div class="cart-item-icon js-delivery-option cart-item-icon--mixedcart flex flex-center ${(entry.defaultFulfillmentType eq 'delivery') && (isUserIdentified eq true) ? 'selected-fulfillment':''} ${isDeliveryThresholdNotMet ?'error-fulfillment':''} ${(entry.defaultFulfillmentType eq 'pickupnearby' &&(isUserIdentified))?'border-left-none-md':''}" >
            				<common:deliveryIcon height="18" width="30" iconColor="${entry.product.deliveryStoreInfo.isEnabled ? '#000':'#ccc'}" greenFill="${(entry.defaultFulfillmentType eq 'delivery') && (isUserIdentified eq true) && isDeliveryThresholdNotMet ne true ? 'green-fill' : ''}"/>
            				<div class="pad-lft-10 ${entry.product.deliveryStoreInfo.isEnabled ?'black-title':'text-grey'}">
            					${entry.product.deliveryStoreInfo.stockAvailablityMessage}
            				</div>
            				
            			</div>
            			
            		
            	</div>	
            	
              <c:choose>
              <c:when test="${entry.product.shippingStoreInfo.isEnabled}">
            	<div data-delivery-mode="shipping" data-storeid="${entry.product.shippingStoreInfo.storeId}" data-prodcode="${entry.product.code}" class="col-md-3 col-xs-12 cart-item-col cart-item-col--mixedcart first-icon  js-line-item-fullfilment ${(isUserIdentified)? 'enabled' : '' } ${(entry.defaultFulfillmentType eq 'delivery' &&(isUserIdentified))?'border-left-none-md':''}">
            		<div data-prodcode="${entry.product.code}" class="cart-item-icon cart-item-icon--mixedcart flex flex-center last-icon js-shipping-fulfillment ${(entry.defaultFulfillmentType eq 'shipping') && (isUserIdentified eq true) ? 'selected-fulfillment ':''} ${not empty entry.maxShippingMessage?'error-fulfillment':''}">
            			<common:parcelIcon height="18" width="30" iconColor="#000" greenFill="${(entry.defaultFulfillmentType eq 'shipping') && (isUserIdentified eq true) && empty entry.maxShippingMessage? 'green-fill' :''}"/>
            			<div class="pad-lft-10 black-title">
          					<div><spring:theme code="cart.shipping.available" /></div>
            			</div>
            			
            		</div>            		
            	</div>
             </c:when>
             <c:otherwise>
                 <div class="col-md-3 col-xs-12 cart-item-col cart-item-col--mixedcart js-line-item-fullfilment hidden-xs hidden-sm">
                 	<div class="cart-item-icon last-icon cart-item-icon--mixedcart flex flex-center ${(entry.defaultFulfillmentType eq 'delivery' &&(isUserIdentified))?'border-left-none-md':''}">
                 		<common:parcelIcon height="18" width="30" iconColor="#ccc"/>
                 		<div class="pad-lft-10 text-grey">          					
          					<div><spring:theme code="cart.shipping.unavailable" /></div>
            			</div>
            			
                 	</div>
                 	
                 </div>
             </c:otherwise> 
             </c:choose>
             
            </div>
            </div>