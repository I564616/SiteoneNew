<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="quickorder"
	tagdir="/WEB-INF/tags/responsive/quickorder"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="/cart" var="cartUrl" />
<spring:url value="/cart/addQuickOrder" var="quickOrderAddToCartUrl"
	htmlEscape="false" />
<spring:theme code="text.quickOrder.product.quantity.max"
	var="maxProductQtyMsg" />
<spring:theme code="text.quickOrder.form.product.exists"
	var="productExistsInFormMsg" />
<spring:theme code="text.quickOrder.product.not.purchaseable"
	var="productNotPurchaseble" />

<%-- <div id="TopOrderingAccountMsg" class="pull-right" hidden><span>${orderingAccountMsg}</span></div> --%>
<div class="js-quick-order-container"
	data-quick-order-min-rows="${quickOrderMinRows}"
	data-quick-order-max-rows="${quickOrderMaxRows}"
	data-quick-order-add-to-cart-url="${quickOrderAddToCartUrl}"
	data-product-exists-in-form-msg="${productExistsInFormMsg}"
	data-product-not-purchasable="${productNotPurchaseble}">
	<div class="success-msg-box">
		<div id="addToCartMobileMesage" class="hidden">
			<h2>
				<spring:theme code="basket.added.to.Basket" />
			</h2>
		</div>
		<div id="itemCountForMobile" class="hidden"></div>
		<br>
		<div id="ForMobileLink" class="hidden">
			<a href="${cartUrl}"><spring:theme code="quickOrderListRows.viewCart" /></a>
		</div>
	</div>
	<ul
		class="item__list item__list__cart quick-order__list js-ul-container">
		<li class="hidden-xs hidden-sm">
			<ul class="item__list--header" style="padding: 0px;">
				<li class="item__sku__input"><spring:theme
						code="text.quickOrder.page.product" /></li>
				<li class="item__image"></li>
				<li class="item__info item__info_new"></li>
				<li class="item__price item__price_new"><spring:theme
						code="text.quickOrder.page.price" /></li>
				<li class="item__quantity item__quantity_new"><spring:theme
						code="text.quickOrder.page.qty" /></li>
				<li class="item__total--column"><spring:theme
						code="text.quickOrder.page.total" /></li>
				<li class="item__remove"></li>
			</ul>
		</li>

		<c:forEach begin="1" end="${quickOrderMinRows}">
			<li class="item__list--item js-li-container">
				<div class="item__sku__input js-sku-container">

					<input type="text" placeholder="<spring:theme code="tag.quickorderListRows.enter.prod.no" />"
						class="js-sku-input-field form-control alignWarning" /> <input
						type="hidden" class="js-hidden-sku-field" /> <input type="hidden"
						class="js-hidden-productcategory-field" /> <input type="hidden"
						class="js-hidden-productsubcategory-field" />
						<input type="hidden" class="js-hidden-inventoryUOMID-field" /> 
					<div
						class="js-sku-validation-container help-block quick-order__help-block">
						<b></b>
					</div>
				</div>
				<div class="item__remove">

					<button class="btn js-remove-quick-order-row hidden" tabindex="-1">
						<span class="icon-close"></span>
					</button>
				</div>
			</li>
		</c:forEach>
	</ul>
	<div class="col-md-6 col-sm-6 paddinglt margintop20">
		<a id="link" href="#"><spring:theme code="quickOrderListRows.addRows" /></a>
		<div class="cl"></div>
		<quickorder:quickorderResetButton
			resetBtnId="js-reset-quick-order-form-btn-bottom"
			resetBtnClass="quick-order__reset-link" />
	</div>
</div>

<input type="hidden" id="sessionStoreisocode"
	value="${sessionStore.address.region.isocode}">
<input type="hidden" id="isOrderingAccount" value="${isOrderingAccount}">
<script id="quickOrderRowTemplate" type="text/x-jquery-tmpl">
	<div class="item__image js-product-info prdct-img cart-mb-img">
		<a href="${request.contextPath}{{= url}}" tabindex="-1">
			{{if images != null && images.length > 0}}
				<img src="{{= images[2].url}}" alt="{{= name }}" title="{{= name }}" />
			{{else}}
				<theme:image code="img.missingProductImage.responsive.thumbnail"/>
				<img src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" alt="{{= name }}" title="{{= name }}" />
			{{/if}}
		<input type="hidden" class="js-hidden-skuname-field" value="{{= name }}"/>
		</a>
	</div>

	<div class="item__info js-product-info">
		<a href="${request.contextPath}{{= url }}" tabindex="-1">
			<span class="item__name">{{= name }}</span>

		</a> 
		<div class="item__stock_{{= code}} hidden-xs message-center">
			{{= stockAvailabilityMessage }} 
          </div><br/>
		 

{{if customerPrice != null && customerPrice.formattedValue != '$0.00'}}
 
	{{if salePrice != null}}
		<div class="item__price js-product-price page-cartPage js-product-info" data-product-price="{{= salePrice}}">
				<div class="cart-mb-border col-xs-12 row">
				<span class="visible-xs visible-sm col-xs-6">
					
                 <b class="black-title"><spring:theme code="basket.page.itemPrice"/></b>
				</span>
				
					<del><span class="col-xs-6 row"><b>{{= customerPrice.formattedValue}}</b></span><b>{{= sellableUomDesc}}</b></del>
						<span class="col-xs-6 row" style="color: red;"><b>{{= salePrice}}</b></span><b>{{= sellableUomDesc}}</b>
				</div>
				<div class="cl"></div>
			 
   {{else}}
     <span class="hidden-sm hidden-xs">
       		 {{if hideList}}
                  {{if hideCSP && stockAvailabilityMessage == 'In stock'}}
                       <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
                  {{else}}
                      {{if inventoryFlag}}
                        <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
					  {{else}}
						{{if hideCSP}}
							<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
						{{else}}	
				 	 	<div class="cart-mb-border col-xs-12">
                	 	<p class="yourPrice"><spring:theme code="product.grid.yourPrice"/><br>
                	 	<span class="col-xs-6 row"><b>{{= customerPrice.formattedValue}}</b></span></p><b>{{= sellableUomDesc}}</b>		
						</div>
						{{/if}}
					  {{/if}}	
 				  {{/if}}
       		{{else}}
                 {{if hideCSP && stockAvailabilityMessage == 'In stock'}}
                	<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
{{/if}}
{{if hideCSP}}
  
                 {{else}}
                        {{if inventoryFlag}}
                            <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
                        {{else}}
							{{if price.formattedValue >  customerPrice.formattedValue}}
                           	<p class="siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
						  	{{if hideUom}}
            	      			<div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
	  				  			 
						 	{{else}}
                    			{{if singleUom}}
                          			<div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
                          			 
                    			{{else}}
                      				<div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= sellableUomDesc}}</b>
                    			{{/if}}
						 	{{/if}}
							{{/if}}
           					<div class="cart-mb-border col-xs-12">
                 			<p class="yourPrice"><spring:theme code="product.grid.yourPrice"/><br>
                			<span class="col-xs-6 row js-your-price"><b>{{= customerPrice.formattedValue}}</b></span></p><b>{{= sellableUomDesc}}</b> 		
							</div>
                 		{{/if}}
				{{/if}}
			{{/if}}
			<div class="cl"></div>
			 
   	</span>
  {{/if}}
</div>
{{/if}}
<!--code-->
  {{if salePrice != null}}
        {{if potentialPromotions != null && potentialPromotions.length > 0 }}
            <span class="promo addPromotionDesc hidden-xs hidden-sm">{{= potentialPromotions[0].description}}</span>
        {{/if}}
    {{/if}}
	</div>
	<div class="cl visible-xs visible-sm"></div>
 
 	<div class="item__stock js-product-info page-cartPage hidden-md hidden-lg">
 		<div class="quickImageBorder cart-mb-border col-xs-12 row">
 			<span class="visible-xs visible-sm col-xs-6">
 				<b class="black-title"><spring:theme code="basket.page.availability"/></b>
 			</span>
              {{if hideList}}
                  {{if hideCSP}}
				   <b><span class="item__stock_{{= code}} col-xs-6 row"><p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P></span></b> 
                  {{else}}
				   <b><span class="item__stock_{{= code}} col-xs-6 row message-center padding-rightZero">{{= stockAvailabilityMessage}}</span></b>
 {{/if}}
        {{else}}
                 {{if hideCSP}}
 			   <b><span class="item__stock_{{= code}} col-xs-6 row">{{= stockAvailabilityMessage}}
                           <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice.quickOrder" arguments="${sessionStore.address.phone}"/></P>
                                 </span></b>
                  {{else}}
                           <b><span class="item__stock_{{= code}} col-xs-6 row message-center padding-rightZero">{{= stockAvailabilityMessage}}</span></b>
                  {{/if}}
    {{/if}}
 		</div>		
  	</div>		 
<!--Price-->	
{{if customerPrice != null && customerPrice.formattedValue != '$0.00'}}
	{{if salePrice != null}}
		<div class="item__price js-product-price page-cartPage js-product-info" data-product-price="{{= salePrice}}">
				<div class="cart-mb-border col-xs-12 row">
				<span class="visible-xs visible-sm col-xs-6">
					<b class="black-title"><spring:theme code="basket.page.itemPrice"/></b>
				</span>
				<span class="col-xs-6 col-md-12 row">
					<del><b>{{= customerPrice.formattedValue}}</b> </del><del><b>{{= sellableUomDesc}}</b></del>
						<b  style="color: red;">{{= salePrice}}</b></span><b>{{= sellableUomDesc}}</b>
				</div>
				<div class="cl"></div>
			</div>
	{{else}}
            
			<div class="item__price js-product-price page-cartPage js-product-info" data-product-price="{{= customerPrice.formattedValue}}">
				<div class="cart-mb-border col-xs-12 row">
				<span class="visible-xs visible-sm col-xs-6">
					<b class="black-title"><spring:theme code="basket.page.itemPrice"/></b>
				</span>
				<span class="col-xs-6 row">

    <span class="hidden-md hidden-lg">
          {{if hideList}}
                {{if hideCSP}}
					<spring:theme code="text.product.callforpricing"/>
                {{else}}
                    {{if inventoryFlag}}
                       <spring:theme code="text.product.callforpricing"/>
                    {{else}}
				  		<div class="col-xs-12 row">
                 		<p class="yourPrice"><spring:theme code="product.grid.yourPrice"/><br>
                 		<span class="col-xs-6 row js-your-price"><b>{{= customerPrice.formattedValue}}</b></span></p><b>{{= sellableUomDesc}}</b>		
						</div>
                    {{/if}}
         		{{/if}}
          {{else}}
                {{if hideCSP}}
                   <spring:theme code="text.product.callforpricing"/> 
                {{else}}
                   {{if inventoryFlag}}
                       <spring:theme code="text.product.callforpricing"/>
                   {{else}}
					{{if price.formattedValue >  customerPrice.formattedValue}}
                       <p class="siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
                      {{if hideUom}}
            	    	  <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
	  				  	 
					  {{else}}
                    	 {{if singleUom}}
                         	 <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
                          	          
                    	 {{else}} 
                      		 <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= sellableUomDesc}}</b>
                    	 {{/if}}
					  {{/if}}
					{{/if}}
	               {{/if}}
               {{/if}}
          {{/if}}
    
				<div class="cl"></div>

    </span>
				<span class="hidden-xs hidden-sm js-unit-price">
              {{if hideList}}
                  {{if hideCSP}}
 			         <spring:theme code="text.product.callforpricing"/>
                  {{else}}
                     {{if inventoryFlag}}
                    	<spring:theme code="text.product.callforpricing"/>
                     {{else}}
					   	<b>{{= customerPrice.formattedValue}}</b></span><b>{{= sellableUomDesc}}</b>
                  	 {{/if}}
                  {{/if}}
              {{else}}
                  {{if hideCSP}}
					{{if inventoryFlag}}
						<spring:theme code="text.product.callforpricing"/>
					{{else}}
					  <p class="quick-order-page-price siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
					  <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
 			          <spring:theme code="text.product.callforpricing"/>
					{{/if}}
                  {{else}}
                      {{if inventoryFlag}}
                         <spring:theme code="text.product.callforpricing"/>
                      {{else}}
                      	 <b>{{= customerPrice.formattedValue}}</b></span><b>{{= sellableUomDesc}}</b>
                      {{/if}}
                  {{/if}}
              {{/if}}
       </span> 
			</div>
				<div class="cl"></div>
			</div>
   		   {{/if}}
	{{else}}
     {{if salePrice != null}}
		<div class="item__price js-product-price page-cartPage js-product-info" data-product-price="{{= salePrice}}">
				<div class="cart-mb-border col-xs-12 row">
				<span class="visible-xs visible-sm col-xs-6">
					<b class="black-title"><spring:theme code="basket.page.itemPrice"/></b>
				</span>
					<span class="col-xs-6 row"><del><b>{{= price.formattedValue}}</b></del><del><b>{{= sellableUomDesc}}</b></del>
						<div class="cl"></div>
						<span class="col-xs-6 row" style="color: red;"><b>{{= salePrice}}</b></span><b>{{= sellableUomDesc}}</b>
                        </span>
						</div>
						<div class="cl"></div>
					</div>	
	 {{else}}

			{{if price != null}}
			<div class="item__price js-product-price page-cartPage js-product-info" data-product-price="{{= price.formattedValue}}">
				<div class="cart-mb-border col-xs-12 row">
				<span class="visible-xs visible-sm col-xs-6">
					<b class="black-title"><spring:theme code="basket.page.itemPrice"/></b>
				</span>		   
            
				<span class="col-xs-6 col-md-12 row">
					{{if hideList}}
                    	{{if hideCSP}}
								<p class="quick-order-page-price siteOneListPrice"></p>
								<div class=siteOneListPriceValue><b><spring:theme code="text.product.callforpricing"/></b></div><b></b>
								
						{{else}}
							{{if inventoryFlag}}
						  		<div class=siteOneListPriceValue><b><spring:theme code="text.product.callforpricing"/></b></div><b></b>
							{{else}}
								{{if price.formattedValue >  customerPrice.formattedValue}}					
							  	<p class="quick-order-page-price siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
                                   {{if hideUom}}
                                     <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
			                        {{else}}
                                    	{{if singleUom}}
                                            <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
                                          
                                   		{{else}}
                                        <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= sellableUomDesc}}</b>
                                    	{{/if}}
			                        {{/if}}
								{{/if}}
						    {{/if}}
						{{/if}}
                    {{else}}
						  {{if hideCSP}}
                            {{if inventoryFlag}}
						       <p class="quick-order-page-price siteOneListPrice"></p>
						       	<div class=siteOneListPriceValue><b><spring:theme code="text.product.callforpricing"/></b></div><b></b>
						        
					        {{else}}
						          <p class="quick-order-page-price siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
                               {{if hideUom}}
            	                  <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
	  				              
				               {{else}}
                                 {{if singleUom}}
                                 	<div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
                                	 
                                 {{else}}
                                  	<div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= sellableUomDesc}}</b>
									 
                                 {{/if}}
				               {{/if}}
								 
								 
							       <div class=siteOneListPriceValue><b><spring:theme code="text.product.callforpricing"/></b></div>
								 
				            {{/if}}
             
			              {{else}} 
                               {{if inventoryFlag}}
                                  <p class="quick-order-page-price siteOneListPrice"></p>
                                  	<div class=siteOneListPriceValue><b><spring:theme code="text.product.callforpricing"/></b></div><b></b>
                                  
                               {{else}}
								{{if price.formattedValue >  customerPrice.formattedValue}}
					                <p class="quick-order-page-price siteOneListPrice"><spring:theme code="text.product.siteOnelistprice"/></p>
                                  {{if hideUom}}
            	                       <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
	  			                 	   
				                  {{else}}
                                    {{if singleUom}}
                                       <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= singleUomMeasure}}</b>
                                        
                                    {{else}}
                                      <div class=siteOneListPriceValue><b>{{= price.formattedValue}}</b></div><b>{{= sellableUomDesc}}</b>
                                    {{/if}}
				                  {{/if}}
				                 {{/if}} 
                               {{/if}}
				          {{/if}}
                    {{/if}}
				<div class="cl"></div>
				</span>
			</div>
</div>
			{{else}}
				<h5 class="price-unavailable-msg" id="priceUnavailabilityMsg{{= code}}"><spring:theme text="${priceUnavailableMsg}" arguments="${sessionStore.address.phone}" htmlEscape="false"/></h5>
			{{/if}}	
	{{/if}}
{{/if}}
</div>
	<div class="item__quantity js-product-info page-cartPage">
		<div class="cart-mb-border col-xs-12 row">
			<span class="visible-xs visible-sm col-xs-6">
				<b class="black-title"><spring:theme code="basket.page.quantity"/></b>
			</span>
			<span class="col-xs-6 row qtybutton marginlt-quickOrder marginlt">
				<button class="js-update-entry-quantity-list-btn minusQty minusQtyBtn hidden-lg hidden-md " type="button" id="quickorderminusqtybtn_{{= code}}">-</button>
			   {{if hideList}}
                  {{if hideCSP}}
 			          <input type="text" class="form-control js-quick-order-qty" value="0" maxlength="5" size="1" disabled="disabled"
                  {{else}}
                     <input type="text" class="form-control js-quick-order-qty" value="1" maxlength="5" size="1"
                  {{/if}}
               {{else}}
					{{if hideCSP}}
 			          <input type="text" class="form-control js-quick-order-qty" value="0" maxlength="5" size="1" disabled="disabled"
                 	 {{else}}
                 	<input type="text" class="form-control js-quick-order-qty" value="1" maxlength="5" size="1"
					{{/if}}
               {{/if}}
				data-max-product-qty="{{= stock.stockLevel}}" data-stock-level-status="{{= stock.stockLevelStatus.code}}" data-is-nursery="{{= productType}}"/>
				<button class="js-update-entry-quantity-list-btn plusQty hidden-lg hidden-md " type="button" id="quickorderplusqtybtn_{{= code}}">+</button>
			
</span>
			<div class="js-product-info js-qty-validation-container help-block quick-order__help-block" data-max-product-qty-msg="${maxProductQtyMsg}"></div>
		<div class="col-xs-12  text-red qorder-qty-alert hide no-padding-lg no-padding-md"><img class="icon-red-exclamation "
							src="${themeResourcePath}/images/Exclamation-point.svg"
							 /> <spring:theme code="text.product.qty.alert" arguments="{{= stock.stockLevel}}" /></div>
</div>
	</div>
	
	</div>
	
	{{if customerPrice != null && customerPrice.formattedValue != '$0.00'}}
	<div class="item__total js-product-info page-cartPage">
		<div class="cart-mb-border col-xs-12 row">
			<span class="visible-xs visible-sm col-xs-6">
				<b class="black-title">Total</b>
			</span>
				{{if salePrice != null}}
					<b><span class="col-xs-6 row js-quick-order-item-total  marginlt-quickOrder  marginlt2">{{= salePrice}}</span></b>
				{{else}}
                  {{if hideCSP}}
 			          $0.00
                  {{else}}
					{{if inventoryFlag}}
					 	$0.00
					{{else}}
                    <b><span class="col-xs-6 row js-quick-order-item-total marginlt2">{{= customerPrice.formattedValue}}</span></b>
					{{/if}}
                  {{/if}}
				{{/if}}
		</div>
	</div>
	{{else}}
		<div class="item__total js-product-info page-cartPage">
		<div class="cart-mb-border col-xs-12 row">
			<span class="visible-xs visible-sm col-xs-6">
				<b class="black-title">Total</b>
			</span>
			{{if salePrice != null}}
					<b><span class="col-xs-6 row js-quick-order-item-total marginlt2">{{= salePrice}}</span></b>
			{{else}}
                      {{if hideCSP}}
 			         	$0.00
                       {{else}}
                    {{if inventoryFlag}}
					 	$0.00
					{{else}}
                    	<b><span class="col-xs-6 row marginlt-quickOrder2 js-quick-order-item-total marginlt2">{{= price.formattedValue}}</span></b>
						{{/if}}
                  {{/if}}
			{{/if}}
		</div>
	</div>
	{{/if}}
</script>