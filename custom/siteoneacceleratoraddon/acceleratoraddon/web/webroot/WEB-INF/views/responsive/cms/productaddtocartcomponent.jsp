<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<div class="addtocart-component row">
 <c:set var="qtyMinus" value="1" />
 <c:if test="${empty showAddToCart ? true : showAddToCart}">
 
 <!--<h5><label class="pdp-qty-label bold-text" for="pdpAddtoCartInput"><spring:theme code="text.account.order.qty"/></label></h5>
         <div class="qty-selector input-group js-qty-selector">
            	<input type="text"  maxlength="5" class="form-control js-qty-selector-input" size="1" value="1" name="pdpAddtoCartInput"  id="pdpAddtoCartInput" />
        </div> -->
        <div class="col-md-4 col-sm-12 qty-Box col-xs-12">
        <div class="row">
        <div class="qty-selector input-group js-qty-selector flex">
         <button class="minusQty pdp-qtyBtn border-rad-left" type="button" id="minusQty_${product.code}">-</button>
         <input type="text"  maxlength="5" class="form-control js-qty-selector-input js-pdp-add-to-cart" size="1" value="1" name="pdpAddtoCartInput"  id="pdpAddtoCartInput" data-available-qty="${product.stock.stockLevel}" data-is-nursery="${product.productType eq 'Nursery'}"/>
         <button class="plusQty pdp-qtyBtn border-rad-right" type="button" id="plusQty_${product.code}">+</button>
        </div>
        </div>
        </div>
        </c:if>
        <div class="col-md-7 col-md-offset-1 col-sm-12 col-xs-12">
        <action:actions element="div"  parentComponent="${component}"/>
        </div>
    </div>
   <div id="qtyError" style="display:none"></div>