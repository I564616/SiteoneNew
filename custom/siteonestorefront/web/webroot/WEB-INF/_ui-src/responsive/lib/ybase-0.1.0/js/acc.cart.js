ACC.cart = {

	_autoload: [
		"bindPageNameUrl",
		"bindHelp",
		"cartRestoration",
		"bindCartPage",
		"bindMultiDEntryRemoval",
		"bindMultidCartProduct",
		["bindApplyVoucher", $(".js-voucher-apply-btn").length != 0],
		["bindToReleaseVoucher", $("#js-applied-vouchers").length != 0],
		"cartUomIntervalMin",
		"bindAvailability",
		"disabledCheckoutGlobalMessage",
		"defaultStoreMessage",
		"cartpopupLink",
		"fullfillmentStatusCall",
		"checkIfCheckoutEnabled",
		"cartDeliveryMethod",
		"applyClipCoupon",
		"removeAllItems",
		"branchFullfillmentAvailablity",
		"hardscapesStoneCartAlert",
		"pickupCountFunction",
		"splitShippingEnable",
		"hideSplitShippingSection",
		"cartEntriesAppend",
		"bigBagChecker",
		"bigBagPopup",
		"bigBagHelpPopup",
		"splitCartHeaderControl"
	],

	cartflaginterval: false,
	//Split cart enabled branched function start
	// To show pickup and shipping count in header
	pickupCountFunction: function () {
		if($(".isSplitCartFulfilment").val()=="true") {
			let pickupCount = $(".pickup-active:visible").length;
			let deliveryCount = $(".delivery-active:visible").length;
			$(".pickup_count").html(pickupCount);
			$(".delivery_count").html(deliveryCount);
			let totalCount = $(".cart-total-items").html();
			if($(".changed_selectedFilfillment").val() == "PICKUP" || $(".changed_selectedFilfillment").val() == "PARCEL_SHIPPING"){
				let shippingonlyCount = totalCount - pickupCount;
				$(".shipping_count").html(shippingonlyCount);
			}
			if($(".changed_selectedFilfillment").val() == "DELIVERY" || $(".changed_selectedFilfillment").val() == "PARCEL_SHIPPING"){
				let shippingonlyCount = totalCount - deliveryCount;
				$(".shipping_count").html(shippingonlyCount);
			}
			
		}
	},
	// To disable Shipping in split cart branch when all shipping fulfillment is not available
	splitShippingEnable: function () {
		if($(".isSplitCartFulfilment").val()=="true"){
			
			let enableSplitShipping = true;
			$("[data-allfulfillmentenabled]").each(function () {
				let target = $(this);
				let shippingEnabled = target.data("shippingenabled");
				if(shippingEnabled != true){
					enableSplitShipping = false;
                	return
				}
			});
			if(enableSplitShipping == true){
				$(".parcelAvail").removeClass('hidden');
				$(".parcelUnavailable").addClass('hidden');
				$(".fullfillment-shipping").removeClass("disabled-fullfillment-method");
			}
			else{
				$(".parcelAvail").addClass('hidden');
				$(".parcelUnavailable").removeClass('hidden');
				$(".fullfillment-shipping").removeClass("js-fullfilment");
				$(".fullfillment-shipping").addClass("disabled-fullfillment-method");
			}
		}
	},
	//To hide split/mixed while shipping fulfillment is selected
	hideSplitShippingSection: function() {
		if($(".isSplitCartFulfilment").val()=="true" && $(".changed_selectedFilfillment").val() != "PARCEL_SHIPPING"){
			let isShippingOnlyProduct="false";
			$("[data-stockavailableonlyhubstore]:visible").each(function () {
				if ($(this).data("stockavailableonlyhubstore") === true) {
					isShippingOnlyProduct="true";
					return;
				}
			});
			if(isShippingOnlyProduct=="true") {
				$(".split-cart-shipping, .split-shipping-count, .split-cart-shipping-container").removeClass('hidden');
				$(".mobile-pickup-text").addClass("hidden");
				if($(".changed_selectedFilfillment").val() == "PICKUP"){
					$(".split-pickup-count-section").removeClass('hidden');
					$(".split-delivery-count-section").addClass('hidden');
				}
				if($(".changed_selectedFilfillment").val() == "DELIVERY"){
					$(".split-delivery-count-section").removeClass('hidden');
					$(".split-pickup-count-section").addClass('hidden');
				}
			}
			$(".splitShippingAppended").addClass("hide")
		}
		else {
			$(".split-cart-shipping, .split-shipping-count, .split-cart-shipping-container").addClass('hidden');
			$(".mobile-pickup-text").removeClass("hidden");
			$(".splitShippingAppended").removeClass("hide");
		}
	},
	splitCartHeaderControl: function() {
		if ($(".isSplitCart").val() === "true") {
			const isNotParcelShipping = $(".changed_selectedFilfillment").val() !== "PARCEL_SHIPPING";
			const splitShippingHeader = $(".splitShippingHeader");
			const splitCartHeader = $(".splitCartHeader");
			const splitPickupDeliveryHeader = $(".splitPickupDeliveryHeader");

			if (isNotParcelShipping) {
				splitShippingHeader.addClass("hidden");
				splitCartHeader.removeClass("m-t-12-md");
				splitPickupDeliveryHeader.removeClass("hidden");
			} else {
				splitPickupDeliveryHeader.addClass("hidden");
				splitCartHeader.addClass("m-t-12-md");
				splitShippingHeader.removeClass("hidden");
			}
		}
	},
	cartEntriesAppend: function() {
		if($(".isSplitCartFulfilment").val()=="true" && $(".changed_selectedFilfillment").val()!= "PARCEL_SHIPPING") {
			$("[data-stockavailableonlyhubstore]:visible").each(function () {
				if ($(this).data("stockavailableonlyhubstore") === true) {
					$(".split-cart-shipping, .split-shipping-count, .split-cart-shipping-container").removeClass("hidden");
					var clonedItem = $(this).clone(true);
					$(this).addClass("hide splitShippingAppended");
					$(".split-cart-shipping-container").append(clonedItem);
					if($(".changed_selectedFilfillment").val() == "PICKUP"){
						$(".split-pickup-count-section").removeClass('hidden');
						$(".split-delivery-count-section").addClass('hidden');
					}
					if($(".changed_selectedFilfillment").val() == "DELIVERY"){
						$(".split-delivery-count-section").removeClass('hidden');
						$(".split-pickup-count-section").addClass('hidden');
					}
				}
			});
		}
	},
	//Split cart enabled branched function End
	//BigBag checkbox
	ajaxBigboxchecker: function(productCode,needReload,ifChecked,newQuantity){
		
		if(ACC.global.wWidth < 700){
			var bigBagchck = $('input.mobbigbagcheck-'+productCode+'[type=checkbox]');
		}
		else{
			var bigBagchck = $('input.bigbagcheck-'+productCode+'[type=checkbox]');
		}	
		
		let isChecked = bigBagchck.is(':checked');
		let skuId = bigBagchck.attr("data-skuId");
		let quantity = bigBagchck.attr("data-quantity");
		let uom = bigBagchck.attr("data-uom");
		let bigBagPrice = bigBagchck.attr("data-bigbagprice");
		
		if(ifChecked == "CHECKED"){quantity=newQuantity;}
		if(ifChecked=="CHECKED" && !isChecked){return;}
		loading.start();	
		$.ajax({
				url: ACC.config.encodedContextPath + "/cart/bigBagChecker",
				data: { "isChecked": isChecked, "sku": skuId, "quantity": quantity, "bigBagPrice": bigBagPrice, "UOM": uom },
				type: "POST",
				success: function() {
							if(needReload){
								window.location.reload();
							}
				},
				error: function() {
					loading.stop();
					// console.log("bigBagChecker ajax call failed.");
				}
			});
	},
	bigBagChecker: function ()
	{
		$('.cart-bigbag .bigbag-check').on('change',function() {
		
		let skuId = $(this).attr("data-skuId");
		
		ACC.cart.ajaxBigboxchecker(skuId,true,"ALL");	
                
    	});
	},
	bigBagPopup: function(){
		$(document).on("click", '.bigbag-help', function() {
		$('#colorbox').addClass("bigbag-help-txt");
		var targetHTML = $(".bigbag-help-mob").html();
			ACC.colorbox.open("", {
		        html:  targetHTML,
		        width: 300,
		        height:250,
		        escKey: false
		      });
		});	
		
		$(document).on("click", '.bigbag-help-txt .bigbag-gotit', function() {
				ACC.colorbox.close();
		});
	},
	bigBagHelpPopup: function(){
		$(document).on("click", '.bigbag-selection-help', function() {
		$('#colorbox').addClass("bigbag-selection-help-txt");
		var targetHTML = $(".bigbag-help-web").html();
			ACC.colorbox.open("", {
		        html:  targetHTML,
		        width: 450,
		        height:500,
		        escKey: false
		      });
		});	
		
		$(document).on("click", '.bigbag-selection-help-txt .bigbag-gotit', function() {
				ACC.colorbox.close();
		});
	},
	//BigBag checkbox END
	cartUomIntervalMin: function () 
    {	
			if($('.page-cartPage').is(":visible")){
				$(".cart-items-left-pane").each(function(){
					var ProductCode = $(this).find(".QtyintervalProductCode").val();
					var intervalupdatedValue = Number($(this).find(".js-update-entry-quantity-input").val());
					var uomqtyintervalvalue = Number($("#uomintervalqtyCart_"+ProductCode).val());
					var uommultipliervalue = Number($("#uommultiplierCart_"+ProductCode).val());
					var minqtycartvalue = Number($("#minQtyCart_"+ProductCode).val());
					if(uommultipliervalue <= 1){
						if(uomqtyintervalvalue > 1){
							if((intervalupdatedValue % uomqtyintervalvalue == 0)){
								$(this).find('.intervalQtyError_cart').addClass('hidden');
								$(this).find(".js-update-entry-quantity-input").removeClass("qtyBoxError");
							}
							else{
								$(this).find('.intervalQtyError_cart').removeClass('hidden');
								$(this).find(".js-update-entry-quantity-input").addClass("qtyBoxError");
								ACC.cartitem.cartflaginterval = true;
							}	
						}
						if(minqtycartvalue > 1){
							if((intervalupdatedValue >= minqtycartvalue)){
								$(this).find('.intervalQtyError_cart').addClass('hidden');
								$(this).find(".js-update-entry-quantity-input").removeClass("qtyBoxError");
							}
							else{
								$(this).find('.intervalQtyError_cart').removeClass('hidden');
								$(this).find('.intervalQtyError_cart').addClass('minError_cart');
								$(this).find(".js-update-entry-quantity-input").addClass("qtyBoxError");
								ACC.cartitem.cartflaginterval = true;
							}
						}
					}
						if(ACC.cartitem.cartflaginterval){
							$('.js-continue-checkout-button').attr('disabled', true);
							$('.js-continue-checkout-buttonoo').attr('disabled', true);
							$('.js-continue-guestcheckout-button').attr('disabled', 'disabled').addClass('disable-checkout-btn');
						}
				});	
				if($(".isGuestCheckoutEnabled").val() == 'false'){
					$("#disableCheckoutMessage").removeClass("hidden");
					ACC.cartitem.cartflaginterval = true;
					ACC.cart.disableCheckoutButton();
				}
			}
    },
	checkIfCheckoutEnabled: function() {
		var IsCheckoutEnabled = true;
		var enableCheckoutElem = document.querySelectorAll("#enableCheckout")
		if ($(enableCheckoutElem).length) {

			$(enableCheckoutElem).each(function() {
				if ($(this).val() != 'true') {
					IsCheckoutEnabled = false
					return false;
				}

			});
		}
		return IsCheckoutEnabled;
	},
	applyClipCoupon: function() {
		$(document).on("click", "#clipCoupon", function(e) {
			var promoProductCode = $(this).parents(".promotion").find("#promoProductCode").val();
			var isPdpPromotion = false;
			if ($(this).prop("checked") == true) {
				isPdpPromotion = true;
			}
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + "/cart/apply/clipCoupon",
				datatype: "json",
				data: {
					isCouponEnabled: isPdpPromotion,
					promoProductCode: promoProductCode
				},
				success: function(result) {
					window.location.href = window.location.href;
				},

			});
		})
		$('.cart-items-left-pane').each(function() {
			var cartBox = $(this);
			if ($(this).find(".coupon-applied").length > 0) {
				$(this).find("#clipCoupon").prop('checked', true);
			}
			if(cartBox.find(".trasferable-flag").val()==='false' && cartBox.find(".isstockNearby-flag").val()==='true' ){
				ACC.cart.disableCheckoutButton();
				$(this).addClass("border-color-orange");
				$(".transferable-flag-msg").removeClass("hidden");
			}
		});
	},
	bindPageNameUrl: function() {
		var siteonePathingChannel = $(".siteonepathingchannel").val();
		var siteonePageName = $(".siteonepagename").val();

		try {
			_AAData.pathingPageName = siteonePageName;
			_AAData.pathingChannel = siteonePathingChannel;
		} catch (e) { }

		$(document).bind('cbox_closed', function() {
			try {
				_AAData.popupPageName = "";
				_AAData.popupChannel = "";
			} catch (e) { }
		});

	},

	disabledCheckoutGlobalMessage: function() {

		var isCheckoutErrorMessagePresent = $("#isCheckoutErrorMessagePresent").val();
		var isPriceChangeMessagePresent = $("#isPriceChangeMessagePresent").val();
		var cmspageid = $("#cmspageid").val();
		if (ACC.cart.checkIfCheckoutEnabled() == "false" && !(isCheckoutErrorMessagePresent == "true")) {
				$("#disableCheckoutMessage").removeClass('hidden');
		}
		if (isPriceChangeMessagePresent == "true" && cmspageid == 'cartPage') {
			$(".global-alerts .pricevaryText").closest(".alert-msg-section").addClass("hidden");
			$("#pricechangemessage").removeClass('hidden');

		}
	},

	bindHelp: function() {
		$(document).on("click", ".js-cart-help", function(e) {
			e.preventDefault();
			var title = $(this).data("help");
			ACC.colorbox.open(title, {
				html: $(".js-help-popup-content").html(),
				width: "550px"
			});
		});
		if($(".cartDeliveryDisabled").val()=="true"){
			$(".local-delivery").addClass("pickup-only-product");
		}
		else{
			$(".local-delivery").removeClass("pickup-only-product");
		}
		
		
		
	},

	cartRestoration: function() {
		$('.cartRestoration').on('click', function() {
			var sCartUrl = $(this).data("cartUrl");
			window.location = sCartUrl;
		});
	},

	bindCartPage: function() {
		// link to display the multi-d grid in read-only mode
		$(document).on("click", '.js-show-editable-grid', function(event) {
			ACC.cart.populateAndShowEditableGrid(this, event);
		});
	},

	bindMultiDEntryRemoval: function() {
		$(document).on("click", '.js-submit-remove-product-multi-d', function() {
			var itemIndex = $(this).data("index");
			var $form = $('#updateCartForm' + itemIndex);
			var initialCartQuantity = $form.find('input[name=initialQuantity]');
			var cartQuantity = $form.find('input[name=quantity]');
			var entryNumber = $form.find('input[name=entryNumber]').val();
			var productCode = $form.find('input[name=productCode]').val();

			cartQuantity.val(0);
			initialCartQuantity.val(0);

			ACC.track.trackRemoveFromCart(productCode, initialCartQuantity, cartQuantity.val());

			var method = $form.attr("method") ? $form.attr("method").toUpperCase() : "GET";
			$.ajax({
				url: $form.attr("action"),
				data: $form.serialize(),
				type: method,
				success: function(data) {
					location.reload();
				},
				error: function() {
					alert(ACC.config.failedMsg + " [" + xht + ", " + textStatus + ", " + ex + "]");
				}

			});

		});
	},


	populateAndShowEditableGrid: function(element, event) {
		var readOnly = $(element).data("readOnlyMultidGrid");
		var itemIndex = $(element).data("index");
		grid = $("#ajaxGrid" + itemIndex);

		var gridEntries = $('#grid' + itemIndex);
		var strSubEntries = gridEntries.data("sub-entries");
		var arrSubEntries = strSubEntries.split(',');
		var firstVariantCode = arrSubEntries[0].split(':')[0];

		$(element).toggleClass('open');

		var targetUrl = gridEntries.data("target-url");

		var mapCodeQuantity = new Object();
		for (var i = 0; i < arrSubEntries.length; i++) {
			var arrValue = arrSubEntries[i].split(":");
			mapCodeQuantity[arrValue[0]] = arrValue[1];
		}

		if (grid.children('#cartOrderGridForm').length > 0) {
			grid.slideToggle("slow");
		}
		else {
			var method = "GET";
			$.ajax({
				url: targetUrl,
				data: { productCode: firstVariantCode, readOnly: readOnly },
				type: method,
				success: function(data) {
					grid.html(data);
					$("#ajaxGrid").removeAttr('id');
					var $gridContainer = grid.find(".product-grid-container");
					var numGrids = $gridContainer.length;
					for (var i = 0; i < numGrids; i++) {
						ACC.cart.getProductQuantity($gridContainer.eq(i), mapCodeQuantity, i);
					}
					grid.slideDown("slow");
					ACC.cart.coreCartGridTableActions(element, mapCodeQuantity);
					ACC.productorderform.coreTableScrollActions(grid.children('#cartOrderGridForm'));
				},
				error: function(xht, textStatus, ex) {
					alert("Failed to get variant matrix. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}

			});
		}
	},


	coreCartGridTableActions: function(element, mapCodeQuantity) {
		ACC.productorderform.bindUpdateFutureStockButton(".update_future_stock_button");
		ACC.productorderform.bindVariantSelect($(".variant-select-btn"), 'cartOrderGridForm');
		var itemIndex = $(element).data("index");
		var skuQuantityClass = '.sku-quantity';

		var quantityBefore = 0;
		var grid = $('#ajaxGrid' + itemIndex + " .product-grid-container");

		grid.on('focusin', skuQuantityClass, function(event) {
			quantityBefore = (this.value).trim();

			$(this).parents('tr').next('.variant-summary').remove();
			if ($(this).parents('table').data(ACC.productorderform.selectedVariantData)) {
				ACC.productorderform.selectedVariants = $(this).parents('table').data(ACC.productorderform.selectedVariantData);
			} else {
				ACC.productorderform.selectedVariants = [];
			}

			if (quantityBefore == "") {
				quantityBefore = 0;
				this.value = 0;
			}
		});

		grid.on('focusout keypress', skuQuantityClass, function(event) {
			var code = event.keyCode || event.which || event.charCode;

			if (code != 13 && code != undefined) {
				return;
			}

			var quantityAfter = 0;
			var gridLevelTotalPrice = "";

			var indexPattern = "[0-9]+";
			var currentIndex = parseInt($(this).attr("id").match(indexPattern));

			this.value = ACC.productorderform.filterSkuEntry(this.value);

			quantityAfter = (this.value).trim();
			var variantCode = $("input[id='cartEntries[" + currentIndex + "].sku']").val();

			if (isNaN((this.value).trim())) {
				this.value = 0;
			}

			if (quantityAfter == "") {
				quantityAfter = 0;
				this.value = 0;
			}

			var $gridTotalValue = grid.find("[data-grid-total-id=" + 'total_value_' + currentIndex + "]");
			var currentPrice = $("input[id='productPrice[" + currentIndex + "]']").val();

			if (quantityAfter > 0) {
				gridLevelTotalPrice = ACC.productorderform.formatTotalsCurrency(parseFloat(currentPrice) * parseInt(quantityAfter));
			}

			$gridTotalValue.html(gridLevelTotalPrice);

			var _this = this;
			var priceSibling = $(this).siblings('.price');
			var propSibling = $(this).siblings('.variant-prop');
			var currentSkuId = $(this).next('.td_stock').data('sku-id');
			var currentBaseTotal = $(this).siblings('.data-grid-total');

			if (this.value != quantityBefore) {
				var newVariant = true;
				ACC.productorderform.selectedVariants.forEach(function(item, index) {
					if (item.id === currentSkuId) {
						newVariant = false;

						if (_this.value === '0' || _this.value === 0) {
							ACC.productorderform.selectedVariants.splice(index, 1);
						} else {
							ACC.productorderform.selectedVariants[index].quantity = _this.value;
							ACC.productorderform.selectedVariants[index].total = ACC.productorderform.updateVariantTotal(priceSibling, _this.value, currentBaseTotal);
						}
					}
				});

				if (newVariant && this.value > 0) {
					// update variantData
					ACC.productorderform.selectedVariants.push({
						id: currentSkuId,
						size: propSibling.data('variant-prop'),
						quantity: _this.value,
						total: ACC.productorderform.updateVariantTotal(priceSibling, _this.value, currentBaseTotal)
					});
				}
			}
			ACC.productorderform.showSelectedVariant($(this).parents('table'));
			if (this.value > 0 && this.value != quantityBefore) {
				$(this).parents('table').addClass('selected');
			} else {
				if (ACC.productorderform.selectedVariants.length === 0) {
					$(this).parents('table').removeClass('selected').find('.variant-summary').remove();

				}
			}

			if (quantityBefore != quantityAfter) {
				var method = "POST";
				$.ajax({
					url: ACC.config.encodedContextPath + '/cart/updateMultiD',
					data: { productCode: variantCode, quantity: quantityAfter, entryNumber: -1 },
					type: method,
					success: function(data, textStatus, xhr) {
						ACC.cart.refreshCartData(data, -1, quantityAfter, itemIndex);
						mapCodeQuantity[variantCode] = quantityAfter;
					},
					error: function(xhr, textStatus, error) {
						var redirectUrl = xhr.getResponseHeader("redirectUrl");
						var connection = xhr.getResponseHeader("Connection");
						// check if error leads to a redirect
						if (redirectUrl !== null) {
							window.location = redirectUrl;
							// check if error is caused by a closed connection
						} else if (connection === "close") {
							window.location.reload();
						}
					}
				});
			}
		});
	},

	refreshCartData: function(cartData, entryNum, quantity, itemIndex) {
		// if cart is empty, we need to reload the whole page
		if (cartData.entries.length == 0) {
			location.reload();
		}
		else {
			var form;

			if (entryNum == -1) // grouped item
			{
				form = $('.js-qty-form' + itemIndex);
				var productCode = form.find('input[name=productCode]').val();

				var quantity = 0;
				var entryPrice = 0;
				for (var i = 0; i < cartData.entries.length; i++) {
					var entry = cartData.entries[i];
					if (entry.product.code == productCode) {
						quantity = entry.quantity;
						entryPrice = entry.totalPrice;
						break;
					}
				}

				if (quantity == 0) {
					location.reload();
				}
				else {
					form.find(".qtyValue").html(quantity);
					form.parent().parent().find(".js-item-total").html(entryPrice.formattedValue);
				}
			}

			ACC.cart.refreshCartPageWithJSONResponse(cartData);
		}
	},

	refreshCartPageWithJSONResponse: function(cartData) {
		// refresh mini cart
		ACC.minicart.updateMiniCartDisplay();
		$('.js-cart-top-totals').html($("#cartTopTotalSectionTemplate").tmpl(cartData));
		$('div .cartpotproline').remove();
		$('div .cartproline').remove();
		$('.js-cart-totals').remove();
		$('#ajaxCartPotentialPromotionSection').html($("#cartPotentialPromotionSectionTemplate").tmpl(cartData));
		$('#ajaxCartPromotionSection').html($("#cartPromotionSectionTemplate").tmpl(cartData));
		$('#ajaxCart').html($("#cartTotalsTemplate").tmpl(cartData));
		ACC.quote.bindQuoteDiscount();
	},

	getProductQuantity: function(gridContainer, mapData, i) {
		var tables = gridContainer.find("table");

		$.each(tables, function(index, currentTable) {
			var skus = jQuery.map($(currentTable).find("input[type='hidden'].sku"), function(o) {
				return o.value
			});
			var quantities = jQuery.map($(currentTable).find("input[type='textbox'].sku-quantity"), function(o) {
				return o
			});
			var selectedVariants = [];

			$.each(skus, function(index, skuId) {
				var quantity = mapData[skuId];
				if (quantity != undefined) {
					quantities[index].value = quantity;

					var indexPattern = "[0-9]+";
					var currentIndex = parseInt(quantities[index].id.match(indexPattern));
					var gridTotalValue = gridContainer.find("[data-grid-total-id=" + 'total_value_' + currentIndex + "]");
					var gridLevelTotalPrice = "";
					var currentPrice = $("input[id='productPrice[" + currentIndex + "]']").val();
					if (quantity > 0) {
						gridLevelTotalPrice = ACC.productorderform.formatTotalsCurrency(parseFloat(currentPrice) * parseInt(quantity));
					}
					gridTotalValue.html(gridLevelTotalPrice);

					selectedVariants.push({
						id: skuId,
						size: $(quantities[index]).siblings('.variant-prop').data('variant-prop'),
						quantity: quantity,
						total: gridLevelTotalPrice
					});
				}
			});

			if (selectedVariants.length != 0) {
				$.tmpl(ACC.productorderform.$variantSummaryTemplate, {
					variants: selectedVariants
				}).appendTo($(currentTable).addClass('selected'));
				$(currentTable).find('.variant-summary .variant-property').html($(currentTable).find('.variant-detail').data('variant-property'));
				$(currentTable).data(ACC.productorderform.selectedVariantData, selectedVariants);
			}
		});

	},

	bindMultidCartProduct: function() {
		// link to display the multi-d grid in read-only mode
		$(document).on("click", '.showQuantityProduct', function(event) {
			ACC.multidgrid.populateAndShowGrid(this, event, true);
		});

		// link to display the multi-d grid in read-only mode
		$(document).on("click", '.showQuantityProductOverlay', function(event) {
			ACC.multidgrid.populateAndShowGridOverlay(this, event);
		});

	},

	bindApplyVoucher: function() {
		// Remove duplicate dom in Cart Page
		var win_wid = $(window).width();

		if (($('.page-cartPage').length > 0) && (win_wid <= 800)) {
			$('.desktop-promo').remove();
		}
		$(".js-voucher-apply-btn").on("click", function(e) {
			e.preventDefault();
			ACC.cart.handleApplyVoucher(e);
		});

		$(".js-voucher-code-text").on("keypress", function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13) {
				ACC.cart.handleApplyVoucher(e);
			}
		});
	},

	handleApplyVoucher: function(e) {
		var display = $(".voucherDisplay").val();
		var voucherCode = $(".js-voucher-code-text").val().trim();
		if (voucherCode != '' && voucherCode.length > 0) {
			$("#applyVoucherForm_" + display).submit();
			$(".cart-promo-msg").show();
		}
	},

	bindToReleaseVoucher: function() {
		$('.js-release-voucher-remove-btn').on("click", function(event) {
			$(this).closest('form').submit();
		});
	},
	/*disableCheckoutIfOrderingAccount: function () {
		if($('#enableCheckoutIfOrderingAccount').length > 0) 
		{
			if($('#enableCheckoutIfOrderingAccount').val() != 'true') 
			{
				$('.js-continue-checkout-button').attr('disabled','disabled');
			} 
			else 
			{
				$('.js-continue-checkout-button').removeAttr('disabled');
			}
		}
	},*/
	bindAnalyticCartdata:function(index, cartErrorText, parentClass){
						let productParent = (parentClass)? "." + parentClass + " " : "";
						var productCode = $(productParent + "#productCode" + index).val();
						var productName = $(productParent + "#productName" + index).val();
						var productCategory = $(productParent + "#productCategory" + index).val();
						var productSubCategory = $(productParent + "#productSubCategory" + index).val();
						var nearbyProduct = $(productParent + "#productnearby" + index).val();
						var productBackOrdercheck= $(productParent + ".analytics_backourderValue"+ index).val();
						var productPrice =$(productParent + "#productprice" + index).val();
						var productQty = $(productParent + "#productQty" + index).val();
						var remainingBackOrder= $(productParent + ".remainingQty"+ index).val();
						var productStockStatus=$(productParent + "#productstockStatus"+ index).val().replace(/<(.|\n)*?>/g, '');
						if(productBackOrdercheck=="true"){
							var productBackOrder= productQty;
						}
						else if(remainingBackOrder > 0){
						var productBackOrder= remainingBackOrder;
						}
					  	else{
							var productBackOrder= "";
						}
	
						if($(".checkmixedcart").val()=="true"){
							var productfulfillment = $(productParent + ".selectedFilfillment" + index).val();
							var productStore = $(productParent + ".selectedFilfillmentStore" + index).val();
						}
	
						 else{
							var productfulfillment = $(".changed_selectedFilfillment").val();
							var productStore = $("#analyticsstore").val().split("#")[1];
						}
    					  if(nearbyProduct === ""){
    						  var productNearBy= "true"
    					  }
    					  else{
    						  var productNearBy= "false"
    					  }
						
						digitalData.cart.item.push({
							"productID": productCode,
							"productName": productName,
							"productCategory": productCategory,
							"productSubCategory": productSubCategory,
							"productSelectedBranch": productNearBy,
							"productfulfillment": productfulfillment,
							"productStore" : productStore,
							"productBackOrder":productBackOrder,
							"productQty":productQty,
							"productPrice":productPrice,
							"productStockStatus":productStockStatus,
							"productError": (!cartErrorText)? "NA" : cartErrorText
						});
	},
	bindAvailability: function() {
		$('input[id^=entry]').each(function() {
			var quantity = 0;
			var productCode = $(this).val();
			var index = this.id.split('entry')[1];
			var nonstore = $(this).data("nonstore");
			if ($(this).data("quantity") !== undefined) {
				quantity = $(this).data("quantity");
			}
			$.ajax({
				url: ACC.config.encodedContextPath + "/cart/availability",
				data: { "productCode": productCode, "quantity": quantity },
				type: "GET",
				success: function(result) {
					var messageField;
					if (quantity != 0) {
						if (result) {
							messageField = "#sufficient" + index;
						} else {
							messageField = "#insufficient" + index;
							//$('#enableCheckout').val('false');
							$('.js-continue-checkout-button').attr('disabled', 'disabled');
							$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
						}
					} else {
						if (result) {
							if (nonstore === undefined) {
								messageField = "#inStock" + index;
							} else {
								messageField = "#notCarried" + index;
							}
						} else {
							messageField = "#outOfStock" + index;
							messageField = "#notCarried" + index;
							//$('#enableCheckout').val('false');
							$('.js-continue-checkout-button').attr('disabled', 'disabled');
							$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
						}
					}
					if ($(messageField)) {
						$(messageField).show();
					}
				}
			});

		});

		var item = [];
		if ($('#isAnonymous').val() == "false") {
			var recentCartId = $("#currentCartId").val();
		}
		else {
			var recentCartId = $("#anonymousCartId").val();
		}

		if ($('.loginstatus').val() != '') {
			var checkoutOptions = "signed-in"
		}
		else {
			var checkoutOptions = "guest"
		}

		if ($('input[id^=cartEntry]').length > 0) {
			digitalData.cart = {
				item: [],
				event: "scView",
				cartId: $("#cartID").val(),
				recentCartId: recentCartId,
				checkoutOptions: checkoutOptions,
			};
			window.addEventListener('load', function() {
				_satellite.track('cart-view');
			});
		}

		$('input[id^=cartEntry]').each(function() {
			if ($(this).parent().hasClass("hidden") === false) {
				var index = this.id.split('cartEntry')[1];
				var isRegulatory = $("#regulated" + index).val();
				var productCode = $(this).val();
				if (isRegulatory) {
					var isProductSellable = ($("#isProductSellable" + index).val() == 'true');
					var isLicensed = ($("#isLicensed" + index).val() == 'true');
					var isMyStoreProduct = ($("#isMyStoreProduct" + index).val() == 'true');
					if (isProductSellable && isMyStoreProduct) {
						$.ajax({
							url: ACC.config.encodedContextPath + "/cart/rupcheck",
							data: { "productCode": productCode, "isRegulatory": isRegulatory },
							type: "GET",
							success: function(result) {
								if (result && !isLicensed) {
									$('.js-continue-checkout-button').attr('disabled', 'disabled');
									$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
									$('#cartRegulatoryLicenceCheck_' + index).removeClass('hidden');
									$('#cartRegulatoryItemInSufficientStockMsg_' + index).addClass('hidden');
									$('#cartRegulatoryItemInStockMsg_' + index).addClass('hidden');
								}
							}
						});
					}
					if (!isProductSellable && isMyStoreProduct) {
						$('.js-continue-checkout-button').attr('disabled', 'disabled');
						$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
					}
				}
				var index = this.id.split('cartEntry')[1];
				 
					// console.log("digitalData" + JSON.stringify(digitalData));

					try {
						_satellite.track("View Cart");
					} catch (e) { }
				 
			}
		});
		if ($('#enableCheckout').length && !ACC.cartitem.cartflaginterval) {

			if (ACC.cart.checkIfCheckoutEnabled() === false || $('#enableCheckoutIfOrderingAccount').val() != 'true' || $(".enableFullfilment").val() == 'false') {
				$('.js-continue-checkout-button').attr('disabled', 'disabled');
				$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
			} else {
				$('.js-continue-checkout-button').removeAttr('disabled');
				$('.js-continue-checkout-buttonoo').removeAttr('disabled');
			}
			if (ACC.cart.checkIfCheckoutEnabled() === false || $('#disableHideListGuestCheckout').val() == 'true' || $(".enableFullfilment").val() == 'false') {
				$('.js-continue-guestcheckout-button').attr('disabled', 'disabled').addClass('disable-checkout-btn');
			}
			else {
				$('.js-continue-guestcheckout-button').removeAttr('disabled', 'disabled').removeClass('disable-checkout-btn');
			}

			if($(".isMixedCartEnabled").val() != 'true')
			{
				if ($(".branch-pickup").hasClass("fullfilment-select") || $(".local-delivery").hasClass("fullfilment-select") || $(".parcel-shipping").hasClass("fullfilment-select")) {
					if ($(".parcel-shipping").hasClass("fullfilment-select")) {
						$(".fullfillment-shipping").click();
						if(!$(".js-gc-error-msg").length > 0){
							$(".shippingThreshold-msg").removeClass("hidden");
							$(".split-shipping-threshold-free").removeClass("hidden");
							$(".split-nonshipping-threshold-free").addClass("hidden");
						}
					}
				}
				else {
					$('.js-continue-guestcheckout-button').attr('disabled', 'disabled').addClass('disable-checkout-btn');
				}
			}
			

		}

	},

	defaultStoreMessage: function() {
		var storeId = document.getElementById('sessionStore').value;
		if (null == $.cookie("gls") && storeId == '172') {
			$("#defaultStoreMessage").removeClass('hidden');
		}
	},

	cartpopupLink: function() {
		$(document).on("click", ".store_link", function(e) {
			e.preventDefault();
			$("#colorbox").addClass("failedgeoLocation-box");
			$("#colorbox").removeClass("addAddress-checkout");
			ACC.colorbox.open("", {
				html: $("#failedgeolocationOverlay").html(),
				maxWidth: "100%",
				width: "650",
				escKey: false,
				overlayClose: false,
			})

		})
	},
	disableCheckoutButton: function() {
		$('.js-continue-checkout-button').attr('disabled', 'disabled');
		$('.js-continue-checkout-buttonoo').attr('disabled', 'disabled');
		$('.js-continue-guestcheckout-button').attr('disabled', 'disabled').addClass('disable-checkout-btn');
	},
	enableCheckoutButton: function() {
		$('.js-continue-checkout-button').removeAttr('disabled', 'disabled');
		$('.js-continue-checkout-buttonoo').removeAttr('disabled', 'disabled');
		$('.js-continue-guestcheckout-button').removeAttr('disabled', 'disabled').removeClass('disable-checkout-btn');
	},
	isNationalShippingInCart: "false",
	fullfillmentStatusCall: function() {
		ACC.cart.isNationalShippingInCart = $(".isNationalShippingInCart").val();
		var cartSelectedOrderType = $(".cart-orderType").val();
		if ($(".branch-shippingAvailable").length && $(".branch-shippingAvailable").val() == 'false') { //Branch Level Fullfillment Availablity
			ACC.cart.isNationalShippingInCart = false;
		}

		function showAvailabilityNonShippable(cartOrderType) {

			$(".js-cart-availability-non-shippable").each(function() {
				if ((cartOrderType !== "PARCEL_SHIPPING") && $(this).data("is-user-not-anonymous") == true) {
					$(this).removeClass("hidden");
				}
				else {
					$(this).addClass("hidden");
				}
			})

		}

		function showParcelErrorMessages(cartOrderType) {
			//$(".js-gc-problem-error").removeClass("hidden");
			//$(".js-gc-error-msg").removeClass("hidden");
			//$(".national-shipping-nonship-element").removeClass("hidden");
			$(".js-max-qty-err-message").removeClass("hidden");
			$(".js-cart-items-row.maxQtyRed").addClass("border-color-orange");
			$(".js-cart-items-row.shippable").addClass("border-color-orange");
			$(".js-cart-outofstock.outofstock").addClass("border-color-orange");
			$(".js-parcelShippingFulfillment-oos").addClass("hidden");
			showAvailabilityNonShippable(cartOrderType)
		}
		function disableCheckout(cartOrderType) {
			if (($(".disable-parcel-checkout").length && cartOrderType === "PARCEL_SHIPPING") || $('#disableHideListGuestCheckout').val() == 'true' || $(".enableFullfilment").val() == 'false' || ($(".js-cart-items-row").hasClass("maxQtyRed") && cartOrderType === "PARCEL_SHIPPING") || $(".border-red-transferflag").length || ACC.cartitem.cartflaginterval) {
				ACC.cart.disableCheckoutButton();
			}
			else {
				ACC.cart.enableCheckoutButton();
			}
		}
		function showGlobalErrorForHidelistAndOutOfStock() {
			if ($('#disableHideListGuestCheckout').val() == 'true' || $(".enableFullfilment").val() == 'false') {
				$(".js-gc-problem-error").removeClass("hidden");
			}
			if ($(".parcel-shipping").hasClass("fullfilment-select")) {
				if($(".isQtyErrorMsg").val() =='true'){
					$(".js-gc-problem-error").removeClass("hidden");
				}else{
					$(".js-gc-problem-error").addClass("hidden");
				}
				$(".national-shipping-ship-element").addClass("hidden");
			}
			if ($(".enableFullfilment").val() == 'false') {
				$(".outofstock").addClass("border-color-orange");
			}
		}


		function inventoryCheckForParcelShipping(cartOrderType) {
			if($(".isMixedCartEnabled").val() != 'true' && $(".isSplitCartFulfilment").val()!="true")
			{
				if (cartOrderType == "PARCEL_SHIPPING") {
					$(".non-parcelShipping").addClass("hidden");
					$(".parcelShippingFulfillment").removeClass("hidden");
				}
				else {
					$(".non-parcelShipping").removeClass("hidden");
					$(".parcelShippingFulfillment, .national-shipping-nonship-element").addClass("hidden");
				}
			}
		}

		if (($(".js-cart-items-row").hasClass("shippable") || $(".js-cart-items-row").hasClass("maxQtyRed")) && cartSelectedOrderType === 'PARCEL_SHIPPING') {
			showParcelErrorMessages(cartSelectedOrderType);
			disableCheckout(cartSelectedOrderType);
		}
		
		if (cartSelectedOrderType === 'PARCEL_SHIPPING') {
			ACC.cart.splitShippingEnable();
			inventoryCheckForParcelShipping(cartSelectedOrderType);
			showAvailabilityNonShippable(cartSelectedOrderType);
			$(".cart-shipping-fee-msg").addClass('hidden')
				if ($("#cart-shipping-fee-msg-show").val() == 'true') {
					$(".cart-shipping-fee-msg").removeClass('hidden');
				}
		}
		showGlobalErrorForHidelistAndOutOfStock();
		$(document).on("click", ".js-line-item-fullfilment.enabled", function(e) {

			if ($(this).find('.cart-item-icon').hasClass('selected-fulfillment')) {
				return false;
			}
			var url = ACC.config.encodedContextPath + "/cart/updateDeliveryMode";
			var currentElement = $(this);
			var nextElement = $(this).next().find(".cart-item-icon")
			var availabilityMsgElem = $(this).closest(".js-cart-items-row").find(".js-availablility-msg-mixed-cart")
			$(this).find('.cart-item-icon').addClass("ease-in-transition selected-fulfillment")
			$(this).find('.cart-item-icon svg  .selected-fill').addClass("green-fill");
			$(this).siblings().find('.cart-item-icon').removeClass("ease-out-transition selected-fulfillment")
			$(this).siblings().find('.cart-item-icon svg  .selected-fill').removeClass("green-fill");
			var deliveryMode = $(this).data("delivery-mode");
			var cartItemProdCode = $(this).data("prodcode");
			var entryNumber = $(this).closest(".cart-item-icon-container").data("entrynumber");
			var storeId=$(this).data("storeid");
			
			 
			$(this).closest(".js-cart-items-row").find(".changed_selectedFilfillmentStore").val(storeId);
			$(this).closest(".js-cart-items-row").find(".changed_selectedFilfillment").val(deliveryMode);
			if (screen.width >= 1024) {
				$(this).parent().children().find(".cart-item-icon").removeClass("border-left-none-md");
				nextElement.addClass('border-left-none-md');
			}
			 
			//Availability Messages start
				if(deliveryMode==='pickuphome' && $(availabilityMsgElem).data("requestedqty") > $(availabilityMsgElem).data("homestoreqty") && $(availabilityMsgElem).data("isbackordarable") !=true){
					$(availabilityMsgElem).removeClass("hidden")
					$(availabilityMsgElem).find(".js-onhand-qty").text($(availabilityMsgElem).data("homestoreqty"))
					$(availabilityMsgElem).find(".js-remaining-qty").text($(availabilityMsgElem).data("requestedqty") - $(availabilityMsgElem).data("homestoreqty"))
				}
				else if(deliveryMode==='pickupnearby' && $(availabilityMsgElem).data("requestedqty") > $(availabilityMsgElem).data("nearbystoreqty") && $(availabilityMsgElem).data("isbackordarable") !=true){
					$(availabilityMsgElem).removeClass("hidden")
					$(availabilityMsgElem).find(".js-onhand-qty").text($(availabilityMsgElem).data("nearbystoreqty"))
					$(availabilityMsgElem).find(".js-remaining-qty").text($(availabilityMsgElem).data("requestedqty") - $(availabilityMsgElem).data("nearbystoreqty")) 
				}
				else if(deliveryMode==='delivery' && $(availabilityMsgElem).data("requestedqty") > $(availabilityMsgElem).data("deliverystoreqty") && $(availabilityMsgElem).data("isbackordarable") !=true){
					$(availabilityMsgElem).removeClass("hidden")
					$(availabilityMsgElem).find(".js-onhand-qty").text($(availabilityMsgElem).data("deliverystoreqty"))
					$(availabilityMsgElem).find(".js-remaining-qty").text($(availabilityMsgElem).data("requestedqty") - $(availabilityMsgElem).data("deliverystoreqty")) 
				}
				else if(deliveryMode==='shipping' && $(availabilityMsgElem).data("requestedqty") > $(availabilityMsgElem).data("shippingstoresqty") && $(availabilityMsgElem).data("isbackordarable") !=true){
					$(availabilityMsgElem).removeClass("hidden")
					$(availabilityMsgElem).find(".js-onhand-qty").text($(availabilityMsgElem).data("shippingstoresqty"))
					$(availabilityMsgElem).find(".js-remaining-qty").text($(availabilityMsgElem).data("requestedqty") - $(availabilityMsgElem).data("shippingstoresqty"))
				}
			else {
				$(availabilityMsgElem).addClass("hidden")
			}

			//Availability message ends
			$.ajax({
				url: url,
				cache: false,
				type: "get",
				dataType: 'json',
				contentType: 'application/json',
				data: {
					"deliveryMode": deliveryMode,
					"entryNumber": entryNumber,
					"storeId": $(this).data("storeid")
				},
				success: function(response) {
					//Item Level Price Handling
					if (response.basePrice) {
						$(currentElement).closest(".js-cart-items-row").find(".js-cart-unit-price").text(response.basePrice.formattedValue)
						$(currentElement).closest(".js-cart-items-row").find(".js-item-total").text(response.entryTotalPrice.formattedValue)
						$(".js-cart-unit-discount-price").text(response.discountBasePrice.formattedValue)
						$(".js-cart-subtotal").text(response.subTotal.formattedValue)
						$(".js-cart-total-with-tax").text(response.cartTotalPrice.formattedValue)
						$(".js-cart-total-discount").text(response.totalDiscounts.formattedValue)
					}
					else {
						ACC.cart.disableCheckoutButton()
					}
					//Item Level Price Handling Ends
					if(response.showDeliveryFeeMessage==true){
						$(".delivery-fee-msg").removeClass("hidden")
					}
					else{
						$(".delivery-fee-msg").addClass("hidden");
						$(".showDeliveryFeeMessage").val("false");
					}
					//Shipping threshold
					if (deliveryMode === 'shipping' && response.differenceAmountShipping && response.differenceAmountShipping > 0) {
						$(currentElement).closest(".js-cart-items-row").find(".js-shipping-threshold").removeClass("hidden")
						$(".js-shipping-difference-amount").text(response.differenceAmountShipping)
					}
					else {
						$(currentElement).closest(".js-cart-items-row").find(".js-shipping-threshold").addClass("hidden")
						$(".js-shipping-difference-amount").text(response.differenceAmountShipping)
						$(".js-shipping-fulfillment").each(function(e){
							if($(this).hasClass("selected-fulfillment") && response.differenceAmountShipping && response.differenceAmountShipping >0){
								$(this).closest(".js-cart-items-row").find(".js-shipping-threshold").removeClass("hidden")
								$(".js-shipping-difference-amount").text(response.differenceAmountShipping);
							}
							else{
								$(this).closest(".js-cart-items-row").find(".js-shipping-threshold").addClass("hidden")
							}
							})
					}
					//Shipping Threshold ends
					//Max Qty starts
					if (deliveryMode === 'shipping' && response.maxShippingMessage) {
						$(currentElement).closest(".js-cart-items-row").addClass("border-red maxqty");
						$(currentElement).find(".cart-item-icon").addClass("error-fulfillment");
						$(currentElement).find(".cart-item-icon svg .selected-fill").removeClass("green-fill");
						if ($(currentElement).closest(".js-cart-items-row").prev().hasClass("js-max-qty-err-message")) {
							$(currentElement).closest(".js-cart-items-row").prev().removeClass("hidden");
							$(currentElement).closest(".js-cart-items-row").prev().text(response.maxShippingMessage)
						}
						$(".js-shipping-fulfillment").each(function() {
							if ($(this).hasClass("selected-fulfillment") && $(this).data("prodcode") == cartItemProdCode) {
								$(this).closest(".js-cart-items-row").addClass("border-red maxqty");
								$(this).addClass("error-fulfillment");
								$(this).find("svg .selected-fill").removeClass("green-fill");
								if ($(this).closest(".js-cart-items-row").prev().hasClass("js-max-qty-err-message")) {
									$(this).closest(".js-cart-items-row").prev().removeClass("hidden");
									$(this).closest(".js-cart-items-row").prev().text(response.maxShippingMessage)
								}
							}
							else if ($(this).closest(".js-cart-items-row").hasClass("maxqty") === false) {
								$(this).closest(".js-cart-items-row").removeClass("border-red maxqty");
								$(this).removeClass("error-fulfillment");
								if ($(this).closest(".js-cart-items-row").prev().hasClass("js-max-qty-err-message")) {
									$(this).closest(".js-cart-items-row").prev().addClass("hidden");
								}
							}

						})
					}
					else {
						$(currentElement).closest(".js-cart-items-row").removeClass("border-red maxqty");
						$(currentElement).find(".cart-item-icon").removeClass("error-fulfillment");
						$(currentElement).find(".cart-item-icon svg  .selected-fill").addClass("green-fill");
						if ($(currentElement).closest(".js-cart-items-row").prev().hasClass("js-max-qty-err-message")) {
							$(currentElement).closest(".js-cart-items-row").prev().addClass("hidden");
						}
						$(".js-shipping-fulfillment").each(function() {
							if ($(this).hasClass("selected-fulfillment") && $(this).data("prodcode") == cartItemProdCode && response.isMaxShippingExceeded === false) {
								$(this).closest(".js-cart-items-row").removeClass("border-red maxqty");
								$(this).removeClass("error-fulfillment");
								if ($(this).closest(".js-cart-items-row").prev().hasClass("js-max-qty-err-message")) {
									$(this).closest(".js-cart-items-row").prev().addClass("hidden");
								}
							}
						})
					}

					//Max Qty Ends

					if (deliveryMode === 'delivery' && response.differenceAmount && response.differenceAmount > 0) {
						$(currentElement).find(".cart-item-icon").addClass("error-fulfillment");
						$(currentElement).find(".cart-item-icon svg  .selected-fill").removeClass("green-fill");
						$(".js-delivery-minimum-msg").removeClass("hidden")
						$(currentElement).closest(".js-cart-items-row").find(".js-delivery-threshold-item-level").removeClass("hidden")
						$(".js-delivery-difference-amount").text(response.differenceAmount);
						ACC.cart.disableCheckoutButton()
					}
					else {
						var isCheckoutDisabledOnDeliveryThreshold = false;
						if (deliveryMode !== 'shipping' && response.maxShippingMessage == null) {
							$(currentElement).find(".cart-item-icon").removeClass("error-fulfillment");
						}
						$(".js-delivery-difference-amount").text(response.differenceAmount);

						$(".js-delivery-threshold-item-level").each(function(e) {
							if ($(this).closest(".js-cart-items-row").find(".js-delivery-option").hasClass("selected-fulfillment") && response.differenceAmount && response.differenceAmount > 0) {
								ACC.cart.disableCheckoutButton();
								$(".js-delivery-minimum-msg").removeClass("hidden");
								$(this).closest(".js-cart-items-row").find(".js-delivery-option").addClass("error-fulfillment");
								$(this).removeClass("hidden");
								isCheckoutDisabledOnDeliveryThreshold = true;
							}
							else if (!isCheckoutDisabledOnDeliveryThreshold) {
								$(".js-delivery-minimum-msg").addClass("hidden");
								$(this).addClass("hidden");
								$(this).closest(".js-cart-items-row").find(".js-delivery-option").removeClass("error-fulfillment");
								if ($(".border-red").length === 0) {
									ACC.cart.enableCheckoutButton();									
								}
								else {
									ACC.cart.disableCheckoutButton();
								}
							}
							if ($(this).hasClass("hidden") === false && ($(this).closest(".js-cart-items-row").find(".js-delivery-option").hasClass("selected-fulfillment")!=true)) {
								$(this).addClass("hidden");
							}
							

						})

					}
				}
			})
		})
		var orderTypeDelivery = $(".fullfilment-select").data("fullfillment");
		if (orderTypeDelivery == "DELIVERY" && $(".showDeliveryFeeMessage").val()=="true" && ($(".isTampaBranch").val()== "false" || $(".isLABranch").val()== "false")) {
			$(".delivery-fee-msg-nonmixcart").removeClass("hidden");
			$(".cart-shipping-fee-msg").addClass('hidden');
			}
			else if($(".checkmixedcart").val()=="false"&& $(".page-cartPage").length > 0){
				$(".delivery-fee-msg-nonmixcart").addClass("hidden");
			}
		ACC.cart.deliveryInCanada();

		$(document).on("click", ".js-fullfilment", function(e) {
			if(ACC.cart.isNationalShippingInCart == "true"){loading.start();}
			var url = ACC.config.encodedContextPath + "/cart/fullfillmentStatus";
			var orderType = $(this).data("fullfillment");
			digitalData.cart.item = [];
			ACC.cart.cartEntryCheck(orderType);
			if (!$(this).hasClass("disabled-fullfillment-method")) {
				var pageName = $(".pagename").val();
				digitalData.eventData = {
					linkName: "delivery-options",
					onClickPageName: pageName
				}
				try {
					_satellite.track("linkClicks");
				} catch (e) { }
			}
			$(".cart-shipping-fee-msg").addClass('hidden')
			if ($(".parcel-shipping").hasClass("fullfilment-select")) { 
				$(".pickupOnly-error-msg").addClass("hidden");
				$(".national-shipping-nonship-element").removeClass("hidden");
			}
			else {
				$(".pickupOnly-error-msg").removeClass("hidden");
				$(".national-shipping-nonship-element").addClass("hidden");
			}
			$(".js-gc-problem-error").addClass("hidden");
			$(".js-gc-error-msg").addClass("hidden");
			$(".js-cart-items-row.shippable").removeClass("border-color-orange");
			$(".js-max-qty-err-message").addClass("hidden");
			$(".js-cart-items-row.maxQtyRed").removeClass("border-color-orange");
			$(".js-parcelShippingFulfillment-oos").removeClass("hidden");
			$(this).parents(".cart-top-fulfillment-method").find(".changed_selectedFilfillment").val(orderType);
			if ($(this).hasClass("fullfillment-delivery") || $(this).hasClass("fullfillment-pickup")) {
				showAvailabilityNonShippable("pickupOrDelivery");
			}
			
			if($(this).hasClass("fullfillment-shipping") && !$(".js-gc-error-msg").length > 0){
			    $(".shippingThreshold-msg").removeClass("hidden");
				$(".split-shipping-threshold-free").removeClass("hidden");
				$(".split-nonshipping-threshold-free").addClass("hidden");
			}
			else{
				$(".shippingThreshold-msg").addClass("hidden");
				$(".split-shipping-threshold-free").addClass("hidden");
				$(".split-nonshipping-threshold-free").removeClass("hidden");
			}
			
			$(this).addClass("fullfilment-select").siblings().removeClass("fullfilment-select");

			$(this).addClass("ease-in-transition fullfilment-select").siblings().removeClass(" ease-out-transition fullfilment-select");

			$.ajax({
				url: url,
				cache: false,
				type: "get",
				dataType: 'json',
				contentType: 'application/json',
				data: {
					"orderType": orderType,

				},
				success: function(data) {
					inventoryCheckForParcelShipping(orderType);
					disableCheckout(orderType);
					showGlobalErrorForHidelistAndOutOfStock();
				},
			})
			if (orderType == "PARCEL_SHIPPING") {
				showParcelErrorMessages(orderType)
				$(".pickupOnly-error-entry").addClass('hidden');
				$(".cart-items-left-pane").each(
					function(){
						var showmsg=false;
						if ($(this).attr('data-shippingenabled') == 'false') {
							showmsg = true;
							// console.log(showmsg);
						}
						if(showmsg){
					$('.national-shipping-nonship-element').removeClass('hidden');
					$('.pickupOnly-error-msg').addClass('hidden');
				}else{
					$('.national-shipping-nonship-element').addClass('hidden');
				}
					})
					$(".cart-shipping-fee-msg").addClass('hidden')
					if ($("#cart-shipping-fee-msg-show").val() == 'true') {
						$(".cart-shipping-fee-msg").removeClass('hidden');
					}
			}
			
			else {
				$('.national-shipping-nonship-element').addClass('hidden');
				$(".pickupOnly-error-entry").removeClass('hidden');
				$(".pickupOnly-error-msg").removeClass("hidden");
			}
			if (orderType == "DELIVERY" && $(".checkmixedcart").val()=="false" && ($(".isTampaBranch").val()== "false" || $(".isLABranch").val()== "false")) {
				$(".delivery-fee-msg-nonmixcart").removeClass("hidden");
				$(".showDeliveryFeeMessage").val("true");
				$(".cart-shipping-fee-msg").addClass('hidden')
			}
			else if($(".checkmixedcart").val()=="false" && $(".page-cartPage").length > 0){
				$(".showDeliveryFeeMessage").val("false");
				$(".delivery-fee-msg-nonmixcart").addClass("hidden");
			}
			ACC.cart.deliveryInCanada();
				$('.cart-items-left-pane').each(function() {
					var cartBox = $(this);
					if(cartBox.find(".trasferable-flag").val()==='false' && cartBox.find(".isstockNearby-flag").val()==='true'){
						ACC.cart.disableCheckoutButton();
						$(".transferflag-msg").parents(".cart-items-left-pane").addClass("border-color-orange");
						$(".transferable-flag-msg").removeClass("hidden");
						$(".disable-parcel-checkout").removeClass("hidden");
					}
				});
			if(ACC.cart.isNationalShippingInCart == "true"){
				ACC.cart.nationalShippingCartHandling(orderType);
			}
			ACC.cart.hideSplitShippingSection();
			ACC.cart.cartEntriesAppend();
			ACC.cart.splitCartHeaderControl();
		});
		if (cartSelectedOrderType !== "PARCEL_SHIPPING") {
			showAvailabilityNonShippable("pickupOrDelivery");
		}
		if(ACC.cart.isNationalShippingInCart == "true"){
			ACC.cart.nationalShippingCartHandling(cartSelectedOrderType, true);
		}
		$("[data-fullfillment]").each(function(){
			let target = $(this);
			let triggered;
			let tType = target.data('fullfillment'); // PICKUP | DELIVERY | PARCEL_SHIPPING
			tType = (tType == 'PICKUP')? 'pickUp' : (tType == 'DELIVERY')? 'delivery' : 'parcel';
			if(target.hasClass("disabled-fullfillment-method") && target.hasClass("fullfilment-select")){
				target.find('.' + tType + 'Avail').addClass('hidden');
				target.find('.' + tType + 'NotAvail, .' + tType + 'Unavailable').removeClass('hidden');
				for (let i = 0; i < 3; i++) {
					let offTarget = $("[data-fullfillment]").eq(i);
					if(!offTarget.hasClass("disabled-fullfillment-method") && !triggered){
						triggered = true;
						offTarget.trigger("click");
						break;
					}
				}
			}
		});
		ACC.cart.cartEntryCheck();
	},
	nationalShippingCartHandling: function(orderType, onload){
		$.ajax({
			url: ACC.config.encodedContextPath + "/cart/getCartToggleResponse?fulfillmentType="+orderType,
			type: "get",
			data: orderType,
			success: function(data) {
				if($(".isSplitCartFulfilment").val()!="true"){
					$(".pickupOnly-error-msg, .pickupOnly-error-entry").addClass('hidden');
				}
				$(".gc-cart-global-error-msg, .js-gc-error-msg, .js-cart-availability-non-shippable, .gc-error-msg, .mobile-pickup-text, .national-shipping-msg").addClass("hidden");
				$(".border-color-orange, .orange-border").removeClass("border-color-orange orange-border");
				if($(".isQtyErrorMsg").val()=="true"){
					$(".js-cart-qty-error").removeClass('hidden');
					$(".js-gc-problem-error, .gc-cart-global-error-msg").removeClass("hidden")
				}
				if($(".isSplitCartFulfilment").val()=="true" && $(".isQtyErrorMsg").val()=="true"){
					$(".js-cart-qty-error").removeClass('hidden');
					$(".js-gc-problem-error, .gc-cart-global-error-msg").removeClass("hidden");
				}
				if($(".cart-top-fulfillment-method").length){ //checkout option not selected
					if((data.enableDelivery || data.enablePickup) && orderType == "PARCEL_SHIPPING"){ //Shipping selected and only shipping items with other option
						$(".national-shipping-msg").removeClass("hidden");
						ACC.cart.cartEntryAdjustStyling(orderType,data.pickupOrDeliveryOnlyAlertEntries, onload);
					}
					else if((data.enableDelivery || data.enablePickup) && orderType != "PARCEL_SHIPPING"){ //Other option selected and only shipping items with other option
						$(".national-shipping-ship-element").removeClass("hidden");
						ACC.cart.cartEntryAdjustStyling(orderType,data.shippingOnlyAlertEntries, onload);
					}
					else{ //only shipping items
						$(".national-shipping-msg").removeClass("hidden");
						$("[data-cartentry]").each(function(){
							let target = $(this);
							if(Number(target.data("cartentry")) == -1){	//out of stock
								target.addClass("border-color-orange");
								target.find(".js-parcelShippingFulfillment-oos").removeClass("hidden");
							}
						});
					}
	
					if(data.showDeliveryThresholdMsg){
						$(".cartHomeOwner-msg").removeClass("hidden");
					}
					else{
						$(".cartHomeOwner-msg").addClass("hidden");
					}
				
					if(onload){ //showing cart item section
						if(data.enablePickup){
							$(".fullfillment-pickup").removeClass("disabled-fullfillment-method").addClass("js-fullfilment");
							$(".pickUpAvail").removeClass("hidden");
							$(".pickUpNotAvail").addClass("hidden");
						}
						if(data.enableDelivery){
							$(".fullfillment-delivery").removeClass("disabled-fullfillment-method").addClass("js-fullfilment");
							$(".deliveryAvail").removeClass("hidden");
							$(".deliveryNotAvail").addClass("hidden");
						}
					}
				}
				else if(!ACC.cartitem.cartflaginterval){
					$(".cart-checkout-btn").removeClass("disable-checkout-btn").removeAttr("disabled");
				}

				$(".cart-page-container").removeAttr("style");
				loading.stop();
			},
			error: function() {
				if(onload){
					$(".cart-page-container").removeAttr("style");
				}
				loading.stop();
			}
		});
	},
	cartEntryAdjustStyling: function(orderType, filterArray, onload){
		let pickupAndDelivery = false;
		let pickupOnlyItems = true;
		let allEntriesDeliverable = true;
		let allEntriesShippable = true;
		let hasOutOfStockProduct=false;
		let noStockTransferrable= false;
		$("[data-cartentry]").each(function(){
			let target = $(this);
			let entry = Number(target.data("cartentry"));
			let index = target.data("index");
			let pickupEnabled = target.data("pickupenabled");
			let deliveryEnabled = target.data("deliveryenabled");
			let shippingEnabled = target.data("shippingenabled");
			target.removeClass("border-color-orange orange-border mb-marginTop40");
			target.find(".national-shipping-element").addClass("hidden");
			if(entry == -1){	//out of stock
				target.addClass("border-color-orange");
				target.find(".js-parcelShippingFulfillment-oos").removeClass("hidden");
				$(".js-gc-error-msg").removeClass("hidden");
				hasOutOfStockProduct= true;
			}
			else if(entry == -2){	//non transferrable
				target.addClass("border-color-orange");
				target.find(".transferflag-msg").removeClass("hidden");
				noStockTransferrable= true;
			}
			else if(entry != -3 && $.inArray(entry, filterArray) != -1){
				target.addClass("orange-border");
				if($(".isSplitCartFulfilment").val()=="true"){
					target.removeClass("orange-border");
				}
				target.find(".national-shipping-element").removeClass("hidden");
				pickupAndDelivery = true;
			}
			if(deliveryEnabled == true || shippingEnabled == true){
				pickupOnlyItems = false;
			}
			if(deliveryEnabled != true){
				allEntriesDeliverable = false;
			}
			if(shippingEnabled != true){
				allEntriesShippable = false;
			}
		});
		if(pickupAndDelivery){
			if($(".isSplitCartFulfilment").val()=="false"){
				$(".cart-checkout-btn").addClass("disable-checkout-btn").attr("disabled","disabled").css({opacity:.5, background: '#ccc', borderColor: '#ccc'});
			}
		}
		else if(!pickupAndDelivery && orderType == "PARCEL_SHIPPING" && !ACC.cartitem.cartflaginterval && !hasOutOfStockProduct && !noStockTransferrable){
			$(".cart-checkout-btn").removeClass("disable-checkout-btn").removeAttr("disabled style");
		}
		if(orderType == "PARCEL_SHIPPING" && allEntriesShippable){
			$(".cart-alert-message").hide();
		}
		else{
			$(".cart-alert-message").show();
		}
	},
	cartEntryCheck: function(orderType){
		let pickupOnlyItems = true;
		let allEntriesDeliverable = true;
		let shippingOnlyItems = true;
		$("[data-cartentry]").each(function(){
			let target = $(this);
			let entry = Number(target.data("cartentry"));
			let index = target.data("index");
			let pickupEnabled = target.data("pickupenabled");
			let deliveryEnabled = target.data("deliveryenabled");
			let shippingEnabled = target.data("shippingenabled");
			let cartErrorText;
			if(deliveryEnabled == true || shippingEnabled == true){
				pickupOnlyItems = false;
			}
			if(deliveryEnabled != true){
				allEntriesDeliverable = false;
			}
			if (pickupEnabled != true && deliveryEnabled != true && shippingEnabled != true) {
				cartErrorText = "no fulfillment option";
			}
			else {
				cartErrorText = ACC.cart.checkErrorAvailable(target, pickupEnabled, deliveryEnabled, shippingEnabled, orderType);
			}
			if (target.parent().hasClass("hidden") === false && target.parent().hasClass("split-cart-shipping-container") === false) {
				ACC.cart.bindAnalyticCartdata(index, cartErrorText, target.parent().attr("class"), orderType);
			}
		});
		
			if(pickupOnlyItems || (!allEntriesDeliverable  && !shippingOnlyItems)){
				$(".cart-delivery-threshold-message").remove();
			}
	},
	checkErrorAvailable: function(ref, pickupEnabled, deliveryEnabled, shippingEnabled,orderType) {
		let erreText = "";
		if(ACC.cart.isNationalShippingInCart != "true"){
			ref.find("[data-analyticerror]").each(function () {
				let target = $(this);
				if (!target.hasClass("hidden")) {
					erreText = target.data("analyticerror");
				}
			});
		}
		else if(orderType && orderType != "PARCEL_SHIPPING"){
			if(pickupEnabled != true && deliveryEnabled != true && shippingEnabled == true){ //shipping only
				erreText = "Shipping Only";
			}
			else{
				erreText = "";
			}
		}
		else{ //national Shipping Case
			if(pickupEnabled == true && deliveryEnabled != true && shippingEnabled != true){ //pickup only
				erreText = "Pick-Up/Delivery Only";
			}
			else if(pickupEnabled == true && deliveryEnabled == true && shippingEnabled != true){ //pickup and delivery only
				erreText = "Pick-Up/Delivery Only";
			}
			else{
				erreText = "";
			}
		}
		return erreText;
	},
	branchFullfillmentAvailablity: function() {
		if ($(".page-cartPage").length) {
			let allFullfillment = ["pickup", "delivery", "shipping"];
			let allFullfillmentAvailablity = [];
			let isAnyFullfillmentAvailable = false;
			for (let i = 0; i < allFullfillment.length; i++) {
				let currentFullfillment = $(".fullfillment-" + allFullfillment[i]);
				let tType = (allFullfillment[i] == 'shipping') ? 'parcel' : allFullfillment[i];
				if ($(".branch-" + allFullfillment[i] + "Available").length && $(".branch-" + allFullfillment[i] + "Available").val() == 'false') { //Branch Level Fullfillment Availablity
					allFullfillmentAvailablity.push(false);
					$(".cart-" + allFullfillment[i] + "-threshold-message").addClass('hidden');
				}
				else {
					allFullfillmentAvailablity.push(true);
				}
				if (!currentFullfillment.hasClass("disabled-fullfillment-method")) { //Fullfillment Disabling check
					if (!allFullfillmentAvailablity[i]) {
						currentFullfillment.addClass("disabled-fullfillment-method")
						currentFullfillment.removeClass("fullfilment-select js-fullfilment");
						currentFullfillment.find('.' + tType + 'Avail').addClass('hidden');
						currentFullfillment.find('.' + tType + 'NotAvail, .' + tType + 'Unavailable').removeClass('hidden');
					}
					else {
						isAnyFullfillmentAvailable = true;
					}
				}
			}
			if (isAnyFullfillmentAvailable) {
				if ($('.isDeliveryOnlyBranch').val() == true) {
					$('.fullfillment-delivery').click();
				}
				
			}
			if ($(".cart-top-fulfillment-method").length && $.inArray(false, allFullfillmentAvailablity) != -1) {
				$(".js-cart-items-row ").each(function () {
					let target = $(this);
					let allIconDisabled = true;
					for (let j = 0; j < 2; j++) {
						let iconParent = target.find(".cart-item-icon-container").eq(j);
						for (let k = 0; k < allFullfillment.length; k++) {
							let iconEnabled = target.data(allFullfillment[k] + "enabled");
							if (!allFullfillmentAvailablity[k] && iconEnabled) {
								iconParent.find(".cart-item-col").eq(k).css({ opacity: .9 }).find("svg").css({ opacity: .2 });
								iconEnabled = false;
							}
							if (iconEnabled) {
								allIconDisabled = false;
							}
						}
					}
					if (!isAnyFullfillmentAvailable || allIconDisabled) {
						target.addClass("border-color-orange");
						target.find(".cart-availability-backorder-msg").remove();
						target.find(".js-update-entry-quantity-btn, .js-update-entry-quantity-input").addClass("disabled").attr("disabled", "disabled").css({ opacity: .5 });
						if (!$("#disableCheckoutMessage").length) {
							$(".cart-orderType").insertAfter('<div class="gc-error-msg marginTop20 js-gc-problem-error flex-center row hidden-print disable-parcel-checkout"><div class="col-xs-12"><div class="flex-center gc-cart-global-error-msg"><span><svg xmlns="http://www.w3.org/2000/svg" width="24" height="21.333" viewBox="0 0 24 21.333"><path fill="#bc0000" d="M23.73,18.334a2,2,0,0,1-1.732,3H2a2,2,0,0,1-1.732-3L10.267,1a2,2,0,0,1,3.465,0l10,17.334ZM12,14.75a1.917,1.917,0,1,0,1.917,1.917A1.917,1.917,0,0,0,12,14.75ZM10.18,7.861l.309,5.667a.5.5,0,0,0,.5.473h2.023a.5.5,0,0,0,.5-.473l.309-5.667a.5.5,0,0,0-.5-.527H10.679a.5.5,0,0,0-.5.527Z" transform="translate(0)"></path></svg></span><span class="pad-lft-10">There\'s a problem with your cart. Please correct any errors indicated below and try again.</span></div></div></div>');
						}
						$(".cart-checkout-btn").addClass("disable-checkout-btn").attr("disabled", "disabled").css({ opacity: .5, background: '#ccc', borderColor: '#ccc' });
						$("#disableCheckoutMessage").removeClass("hidden");
						$(".js-gc-error-msg").remove();
					}
					if($("#isAnonymous").val()=='true' && $(".isDeliveryOnlyBranch").val()== 'true'){
						if(!$(".cart-delivery-threshold-message").parent().parent().hasClass('hidden') && !$(".cartHomeOwner-msg").hasClass('hidden')){
							$(".gc-cart-global-error-msg").addClass('hidden');
							$(".js-cart-items-row ").each(function () {
								if($(this).attr('data-deliveryenabled')=='true'){
										 $(this).find(".cartQtyBox").removeClass("disabled");
										 $(this).find(".cartQtyBox").removeAttr("disabled").css({ opacity: 1 });
										 $(this).find(".cart-qty-btn").removeAttr("disabled").css({ opacity: 1 });
										$(this).removeClass('border-color-orange');
								}
								else {
									$(".gc-cart-global-error-msg").removeClass('hidden');
								}
														
							})
					 	}
					 }
				});
			}
			$(".cart-page-container").removeAttr("style");
		}
		
		if ($(".parcel-shipping").hasClass("fullfilment-select")) {
		$(".pickupOnly-error-msg").addClass("hidden");
		$(".pickupOnly-error-entry").addClass('hidden');
		$(".cart-items-left-pane").each(
			function(){
				var showmsg=false;
				if ($(this).attr('data-shippingenabled') == 'false') {
					showmsg = true;
					// console.log(showmsg);
				}
				if(showmsg){
			$('.national-shipping-nonship-element').removeClass('hidden');
			$('.pickupOnly-error-msg').addClass('hidden');
		}else{
			$('.national-shipping-nonship-element').addClass('hidden');
		}
			})}
			else {
				$('.national-shipping-nonship-element').addClass('hidden');
				$(".pickupOnly-error-entry").removeClass('hidden');
				$(".pickupOnly-error-msg").removeClass("hidden");
			}
			
	},
	nearbyContinue: function() {
		ACC.colorbox.open("", {
			html: $(".nearby-continue").html(),
			maxWidth: "100%",
			width: "600",
			escKey: false,
			overlayClose: false,
			close: "",
			title: "<h1 class='headline'>" + ACC.config.nearbyContinueheading + "</h1>",
			className: "nearby-continue",
			onComplete: function(e) {
				_AAData.popupChannel= "checkout";
				_AAData.popupPageName = "checkout: nearby-delay popup";
					try {
					    	 _satellite.track('popupView');
			        } catch (e) {} 
				},
		})
	},

	prop65Overlay: function(checkoutUrl) {
		ACC.colorbox.open("", {
			html: `<div class='row'>
							<div class='col-md-2 col-sm-2 col-xs-3'><span class='icon-warning'></span></div>
							<div class='col-md-10 col-sm-10 col-xs-9'><div class='row'>${ACC.config.cancerHarm} <span class='hidden-xs hiddem-sm'></br /></span><a
								class='prop65-url' href='https://www.P65warnings.ca.gov' target='_blank'>https://www.P65warnings.ca.gov</a>
								<div class='cl'></div></br>
								<div class='row'>
								<div class='col-md-8 col-xs-10'>
									<a href='${checkoutUrl}' class='prop65-btn btn btn-block btn-primary ${$('body').hasClass("page-siteOneCheckoutPage") ? 'proceed-to-checkout-ca' : ''}'>${ACC.config.proceedToCheckout}</a></div>
								</div>
							</div>
						</div>
					</div>`,
			title: "<div class='headline'>" + ACC.config.californiaCustomers + "</div>",
			width: "530px",
			className: "californiaPopup",
			onComplete: function() {
				sessionStorage.setItem("IsProp65Shown", "true");
				_AAData.popupChannel= "checkout";
				_AAData.popupPageName = "checkout: california popup";
					try {
					    	 _satellite.track('popupView');
			        } catch (e) {}
			},
			 onClosed: function() {
				sessionStorage.removeItem("IsProp65Shown");
			},
			
		});
	},
	cartDeliveryMethod: function() {
		$(document.body).on('click', '.exitEmail-guestCheckout-email', function(e) {
			var linkNmae = $(this).text().split(".a")[0].trim();
			var pageName = $(".pagename").val();
			digitalData.eventData = {
				linkName: linkName,
				onClickPageName: pageName
			}
			try {
				_satellite.track("linkClicks");
			} catch (e) { }
		});

		$(document).on('click', '.js-continue-guestcheckout-button', function(e) {
			e.preventDefault();
			ACC.cart.userType = "guest";
			try { ACC.cart.cartPageTotal = $(".cart-totals").text().trim().replace('$', '').replaceAll(',', ''); }
			catch { ACC.cart.cartPageTotal = 0 }
			if ($(".nearbyContinue").val() == "false" && $(".checkmixedcart").val()=="false" && ($(".changed_selectedFilfillment").val()!='PARCEL_SHIPPING')) {
				ACC.cart.nearbyContinue();
				return false;
			}
			var californiaLocation = $("#california_location").val();
			var checkoutUrl = $(this).attr('href');
			if (californiaLocation === 'CA' && $(".nearbyContinue").val() == "true") {
				$.ajax({
					success: function(result) {
						ACC.cart.prop65Overlay(checkoutUrl);
					},

				});
				return false;
			}
			var cartEntriesError = ACC.pickupinstore.validatePickupinStoreCartEntires();
			if (!cartEntriesError)
			{
				ACC.cart.ga4AdobeAnalytics($(this).text().trim(), $(".pagename").val(), [], "scCheckout", $("#anonymousCartId").val(), 0, ACC.cart.cartPageTotal, ACC.cart.userType);
				window.location.href = checkoutUrl;
			}
		});
		
		
		
		
		$(document).on('click', '.prop65-btn, .accept-delay', function(e) {
			e.preventDefault();
			cartEntriesError = ACC.pickupinstore.validatePickupinStoreCartEntires();
			if (!cartEntriesError) {
				ACC.cart.ga4AdobeAnalytics($(this).text().trim(), $(".pagename").val(), [], "scCheckout", $("#anonymousCartId").val(), 0, ACC.cart.cartPageTotal, ACC.cart.userType);
				window.location.href = $(this).attr('href');
				return false;
			}
		});
		
		
		$(document).on('click', '.removeCart', function(e) {
			if ($('#isAnonymous').val() == "false") {
				var deletedCartID = $("#currentCartId").val();
			}
			else {
				var deletedCartID = $("#anonymousCartId").val();
			}
			digitalData.eventData={
					deletedCartId: deletedCartID,
					linktype:"",
	                linkName: "Remove All",
	                onClickPageName: "checkout:cart",
	            }
			_satellite.track("linkClicks");
			var item = [];
			cartEntriesError = ACC.pickupinstore.validatePickupinStoreCartEntires();
			if (!cartEntriesError) {
				digitalData.cart = {
					item: [],
				}
				$('input[id^=cartEntry]').each(function() {
					if($(this).parent().hasClass("hidden")===false){
		            var index = this.id.split('cartEntry')[1];
					ACC.cart.bindAnalyticCartdata(index);
		            
		                // console.log("digitalData" + JSON.stringify(digitalData));
		                
					}
		        });
					try {
						_satellite.track("cartDelete");
					} catch (e) { }
				 
			}
			
		});
		

	},
	cartPageTotal: '0',
	userType: 'guest',
	ga4AdobeAnalytics: function(linkName, pageName, item, pEvent, cartId, recentCartIds, potentialRevenue, checkoutOptions) {
		var _satellite_track_Checkout;
		var CartItems = [];
		var entryParent = !$(".parcelShippingFulfillment").length || $(".parcelShippingFulfillment").hasClass("hidden") ? '.non-parcelShipping ' : '.parcelShippingFulfillment ';
		digitalData.eventData = {
			linktype: "",
			linkName: linkName,
			onClickPageName: pageName,
		}
		try {
			_satellite.track("linkClicks");
		} catch (e) { }
		digitalData.cart = {
			item: item,
			event: pEvent,
			cartId: cartId,
			potentialRevenue: potentialRevenue,
			checkoutOptions: checkoutOptions
		}
		if (recentCartIds) {
			digitalData.cart.recentCartIds = recentCartIds;
		}
		if ($('input[id^=cartEntry]').length) {
			$(entryParent + 'input[id^=cartEntry]').each(function () {
				var index = this.id.split('cartEntry')[1];
				if (!isNaN(index) && index != '') {
					ACC.cart.bindAnalyticCartdata(index);
					_satellite_track_Checkout = true;
					try {
						var ga4CartData = ACC.ga4analytics.generate_cart_item_payload(index, entryParent);
						if (ga4CartData) {
							CartItems.push(ga4CartData);
						}
					} catch (e) { }
				}
			});
		}
		if (_satellite_track_Checkout) {
			try {
				_satellite.track("Checkout");
				ACC.ga4analytics.begin_checkout(CartItems, parseFloat(potentialRevenue)); /***For GA4 Analytics begin_checkout ***/
			} catch (e) { }
		}
	},
	removeAllItems: function() {
		$(document).on("click", "#remove-all-items-cart", function(e) {
			ACC.colorbox.open("", {
	            html:  $(".remove-all-items-modal").html(),
				width: "580px",
	            className:"cart-remove-all-items-popup"
	        });
		});
	},
	cartPriceSKUArray: [],
	showPriceLoader: function () {
		let cartPageSize = $(".cartPageSize").val();
		let priceLoop = $(".cartPageCount").val();
		let priceDataArray = [];
		$(".cart-items-left-loader").each(function () {
			let target = $(this);
			let index = target.data("priceshowindex");
			let code = target.data("priceshowcode");
			priceDataArray[index] = code;
			ACC.cart.cartPriceSKUArray[index] = code;
		});
		let cspCalls = Math.ceil(priceDataArray.length / priceLoop);
		if (cartPageSize < priceDataArray.length) {
			for (let index = 0; index < cspCalls; index++) {
				let setData = priceDataArray.slice(index * priceLoop, (index + 1) * priceLoop);
				$.ajax({
					url: ACC.config.encodedContextPath + "/cart/fetchCspOnload",
					type: "get",
					data: {
						sequenceNo: index,
						sku: setData.join(",")
					},
					success: function (data) {
						if (data) {
							ACC.cart.showCartPrice(data, priceDataArray);
						}
						else { //error => page reload
							ACC.cart.showCartPrice(false);
						}
					},
					error: function () { //error => page reload
						ACC.cart.showCartPrice(false);
					}
				});
			}
		}
	},

	uploadCsvCart: function(e){
		var files = e.files;
		if(files[0].type != 'text/csv' ){
			$('.fileBrowseContainer').addClass('filetype-error-alert padding20');
			$(".filetype-info").show();
			$(".filetype-error-info").show();
			$(".upload-type-fail-msg").text('Incorrect File type');
		} else {
			$('.fileBrowseContainer').removeClass('filetype-error-alert padding20');
			$('.csvUploadbtn').removeAttr('disabled');
			$(".filetype-info").hide();
			$(".filetype-error-info").hide();
		}
		$('.select-filename').text(files[0].name);
	},
	formSubmitCsvCart: function (event) {
        $('.csvUploadbtn').prop("disabled", true);
        var formupload = document.getElementById("siteoneCartUploadForm");
        var fdata = new FormData(formupload);
        loading.start();
        $("<p style='margin: -100px auto auto auto; top: 50%;' class='text-align-center elemOverlayParent font-Geogrotesque f-s-28 text-default'>Hang tight, we're getting things ready for you</p><p style='margin: auto; top: 50%;' class='text-align-center elemOverlayParent font-Geogrotesque f-s-28 text-default'><span class='csv-cart-progress'>0</span>%</p>").appendTo('#loadingOverlay');
        ACC.cart.csvCartProgressStart(document.getElementById("csvFileCart").files[0].size);
        ACC.cart.csvCartProgressResponse = "";
        $.ajax({
            url: formupload.action,
            data: fdata,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            type: 'POST',
            success: function (response) {
                ACC.cart.csvCartProgressResponse = response;
            },
            error: function (response) {
                ACC.cart.csvCartProgressResponse = response.statusText;
            }
        });
    },
    csvCartProgressEstimatedTime: 99,
    csvCartProgressPerSecond: 99,
    csvCartProgressCounter: 0,
    csvCartProgressTimer: "",
    csvCartProgressResponse: "",
    csvCartProgressUpdate: function () {
        ++ACC.cart.csvCartProgressCounter;
        let currentPercentage = Math.ceil(ACC.cart.csvCartProgressCounter * ACC.cart.csvCartProgressPerSecond);
        //console.log("currentPercentage: ", currentPercentage," Response: ",  ACC.cart.csvCartProgressResponse," Counter: ",  ACC.cart.csvCartProgressCounter)
        if (ACC.cart.csvCartProgressResponse != "") {
            $(".csv-cart-progress").html(100);
            clearInterval(ACC.cart.csvCartProgressTimer);
            setTimeout(function () {
                loading.stop();
                ACC.cart.csvCartOpenAndResponseHandle(ACC.cart.csvCartProgressResponse);
            }, 600);
        }
        else if (currentPercentage < 99 && ACC.cart.csvCartProgressCounter < Math.ceil(ACC.cart.csvCartProgressEstimatedTime)) {
            $(".csv-cart-progress").html(currentPercentage);
        }
        else {
            $(".csv-cart-progress").html(99);
        }
    },
    csvCartProgressStart: function (fileSize) {
        if (fileSize) {
            ACC.cart.csvCartProgressEstimatedTime = (776.441 / 7284) * fileSize * ((fileSize + 90) * .0024); //static calculation SE- for total time
            ACC.cart.csvCartProgressPerSecond = 100 / ACC.cart.csvCartProgressEstimatedTime;
            ACC.cart.csvCartProgressCounter = 0;
            //console.log("EstimatedTime: ", ACC.cart.csvCartProgressEstimatedTime," PerSecond: ",  ACC.cart.csvCartProgressPerSecond," Counter: ",  ACC.cart.csvCartProgressCounter);
            ACC.cart.csvCartProgressTimer = setInterval(ACC.cart.csvCartProgressUpdate, 1000);
        }
    },
	csvCartOpenAndResponseHandle: function (response) {
		$("#loadingOverlay").empty();
		var selectFileUpload = false;
        var closeBtnSvg = '<span id="close"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="15" viewBox="0 0 16 15" fill="none"><path d="M10.8769 7.5L15.1412 3.23565C15.6645 2.71236 15.6645 1.86392 15.1412 1.3402L14.1935 0.392472C13.6702 -0.130824 12.8218 -0.130824 12.298 0.392472L8.03369 4.65682L3.76934 0.392472C3.24605 -0.130824 2.39761 -0.130824 1.87389 0.392472L0.926163 1.3402C0.402868 1.86349 0.402868 2.71193 0.926163 3.23565L5.19051 7.5L0.926163 11.7643C0.402868 12.2876 0.402868 13.1361 0.926163 13.6598L1.87389 14.6075C2.39719 15.1308 3.24605 15.1308 3.76934 14.6075L8.03369 10.3432L12.298 14.6075C12.8213 15.1308 13.6702 15.1308 14.1935 14.6075L15.1412 13.6598C15.6645 13.1365 15.6645 12.2881 15.1412 11.7643L10.8769 7.5Z" fill="#78A22F"/></svg></span>';
		if (response == 'uploadCartEmpty') {
            $('.select-filename').text('');
            $('.fileBrowseContainer').addClass('filetype-error-alert padding20');
            $(".filetype-info").show();
            $(".filetype-error-info").show();
            $(".upload-type-fail-msg").text('File is Blank');
			$(".csvFile").val(null);
        }
        else {
            var prodCount, popupHTML,numUploadissues;
			if (response.indexOf("uploadSuccess") > -1) {
				response = response.replace("[", '').replace("]", '');
                prodCount = response.split("|")[1];
                popupHTML = "sucess";
				var notAvailableProds=response.split(",");
				$(".csvUploadInfo").hide();
				totalUploaded=prodCount;
				if(response.split("|").length > 3){
					$(".csvUploadInfo").show();
					numUploadissues=notAvailableProds.length;
					totalUploaded=numUploadissues+Number(prodCount);
					if(numUploadissues==1){
						$(".csvUploadInfoHeadline").text("There was "+ numUploadissues +" problem:");

					}else{
						$(".csvUploadInfoHeadline").text("There were "+ numUploadissues +" problems:");
					}
					var firstProductWithSuccess = notAvailableProds[0].split("|");
					notAvailableProds[0]=firstProductWithSuccess[2]+"|"+firstProductWithSuccess[3];
					for(var i=0; i<notAvailableProds.length; i++){
						var prodcodeAndDec=notAvailableProds[i].split("|");
						$("<div>Item "+prodcodeAndDec[0]+" is "+prodcodeAndDec[1]+"</div>").appendTo('.csvUploadInfoDetails')
					}
					ACC.adobelinktracking.cartCsvUpload('','','','checkout: cart: upload csv popup: partial confirmation popup', 'checkout', 'checkout: cart: upload csv popup: partial confirmation popup');
				}else{
					ACC.adobelinktracking.cartCsvUpload('','','','checkout: cart: upload csv popup: confirmation popup', 'checkout', 'checkout: cart: upload csv popup: confirmation popup');
				}
				
            }
            else if (response == 'uploadCartExceeded') {
                popupHTML = "exceeded";
				ACC.adobelinktracking.cartCsvUpload('','','','checkout: cart: upload csv popup: error popup', 'checkout', 'checkout: cart: upload csv popup: error popup');
            }
			else if (response == 'openCart') {
				popupHTML = "";
			}
            else {
                popupHTML = "uploaderror";
				ACC.adobelinktracking.cartCsvUpload('','','','checkout: cart: upload csv popup: error popup', 'checkout', 'checkout: cart: upload csv popup: error popup');
            }
            ACC.colorbox.open("", {
                html: $(".cart-import-popup" + popupHTML).html(),
                width: "100%",
                maxWidth: "738px",
                className: "cart-import-csv",
                close: closeBtnSvg,
                title: "<h4 class='hidden-md hidden-lg headline'><span class='headline-text'>" + ACC.config.shoppingCartUploadTitle + "</span></h4>",
                onComplete: function () {
					if(popupHTML == ""){
						$('.cart-import-csv #colorbox').addClass('shopping-cart-list-upload');
						$("body").css("overflow-y", "hidden");
						$(".filetype-error-info, .filetype-info").hide();
						$('.select-filename').text("");
						$(".csvFile").val(null);
						$('.csvUploadbtn').prop("disabled",true);
						$('.fileBrowseContainer').removeClass('filetype-error-alert padding20');
					}
					if(popupHTML == "exceeded"){
						$(".selectFileUploadCart").on( "click", function() {
							selectFileUpload = true;
							ACC.colorbox.close();
						});
					}
                    if (popupHTML == "sucess") {
						if(prodCount==1){
							$('.csvUploadPanelHeadline').text(prodCount+" Of "+ totalUploaded + " " + ACC.config.shoppingCartUploadSuccessSingle);
						}else{
							$('.csvUploadPanelHeadline').text(prodCount+" Of "+ totalUploaded + " " + ACC.config.shoppingCartUploadSuccess);
						}
                    }
                    $("body").css("overflow-y", "hidden");
                },
                onClosed: function () {
                    $('body').css("overflow-y", "auto");
                    if (popupHTML == "sucess") {
                        loading.start();
                        window.location.href = ACC.config.encodedContextPath + "/cart";
                    }
					if(selectFileUpload){
						selectFileUpload = false;
						ACC.cart.csvCartOpenAndResponseHandle('openCart');
					}
                }
            });
        }
    },
	
  formatter: new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
	// These options are needed to round to whole numbers if that's what you want.
	minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
	maximumFractionDigits: 3 // (causes 2500.99 to be printed as $2,501)
	}),	
  showCartPrice: function (data, priceDataArray) {
    // console.log(data, priceDataArray);
    if (data) {
      for (let i = 0; i < data.products.length; i++) {
        let pCode = Number(data.products[i].productCode);
        let target = $('[data-priceshowcode="' + pCode + '"]');
        target.next(".cart-items-left-price-show").removeClass("hidden").find(".atc-price-analytics").html(ACC.cart.formatter.format( data.products[i].itemPrice));
        target.next(".cart-items-left-total-show").removeClass("hidden").find(".atc-price-analytics").html(ACC.cart.formatter.format( data.products[i].itemTotal));
        target.remove();
        ACC.cart.cartPriceSKUArray.splice($.inArray(pCode, ACC.cart.cartPriceSKUArray), 1);
        if (!ACC.cart.cartPriceSKUArray.length) {
          $(".cart-total-loader").remove();
          if ($('.js-cart-total-discount').length > 0) {
			let discountPrice = Number($(".js-cart-total-discount").find('.b-price').text().trim().replace(/[$,]/g, ''));
			let estimatedPrice = (data.subTotal-discountPrice).toLocaleString();
			$(".cart-total-loader-show").removeClass("hidden").children(".js-cart-total-with-tax").html('$' + estimatedPrice);
			$(".cart-total-loader-show").removeClass("hidden").children(".js-cart-subtotal").html(ACC.cart.formatter.format( data.subTotal));
		} else {
			$(".cart-total-loader-show").removeClass("hidden").children(".js-cart-total-with-tax, .js-cart-subtotal").html(ACC.cart.formatter.format( data.subTotal));
		}
          $(".cart-loader-button-overlay").removeClass("cart-loader-button-overlay");
        }
      }
    }
    else {
      ACC.colorbox.open("", {
        title: "<div class='text-center'>" + ACC.config.cartPriceLoaderErrorTitle + "</div>",
        html: '<div class="row text-center"><div class="col-md-12"><p class="bold f-s-18">' + ACC.config.cartPriceLoaderErrorHeading + '</p><p class="brown f-s-18">' + ACC.config.cartPriceLoaderErrorMessage + '</p></div><div class="col-md-6"><a class="btn btn-primary btn-block" href="' + ACC.config.encodedContextPath + '/cart">' + ACC.config.cartPriceLoaderErrorRefresh + '</a></div><div class="col-md-6"><a class="btn btn-primary btn-block" href="' + ACC.config.encodedContextPath + '/contactus">' + ACC.config.cartPriceLoaderErrorContact + '</a></div></div>',
        close: '',
        width: 700,
        escKey: false,
        overlayClose: false
      });
    }
  },
  hardscapesStoneCartAlert: function() {
		if ($(".page-cartPage").length && $(".cart-top-fulfillment-method").length) {
			let target = $(".cart-items-left-pane");
			let l1Category = (ACC.config.encodedContextPath == '/es') ? 'Materiales duros & Vida al Aire Libre' : 'Hardscapes & Outdoor Living';
			let hardscapesconsumablesProductFound;
			let landscapeCategory = (ACC.config.encodedContextPath == '/es') ? 'Material de Jardinería' : 'Landscape Supply';
			let cosumableCategory = (ACC.config.encodedContextPath == '/es') ? 'Consumibles' : 'Consumables';
			for (let i = 0; i < target.length; i++) {
				if ((target.eq(i).data('level1category') == l1Category || (target.eq(i).data('level1category') == landscapeCategory && target.eq(i).data('level2category') == cosumableCategory )) && (target.eq(i).data('uommeasure').indexOf("Net Ton") != -1 || target.eq(i).data('uommeasure').indexOf("Cubic Yard") != -1)) {
					hardscapesconsumablesProductFound = true;
					if (target.eq(i).hasClass("hardscape-highlight-border")) {
						target.eq(i).removeClass("hardscape-highlight-border");
					};
					target.eq(i).addClass("hardscape-highlight-border");
				}
			}
			if (hardscapesconsumablesProductFound) {
				$(".hardscape-stone-alert-box").removeClass("hidden");
			}
		}
	},
	deliveryInCanada: function() {
		var isDeliveryOrder = $(".fullfilment-select").data("fullfillment");
		if (isDeliveryOrder == "DELIVERY" && $(".siteoneCARegion").val() == "true") {
			$(".delivery-fee-msg-nonmixcart").removeClass("hidden");
		}
	}
};
$(document).ready(function() {
	$('.item-fulfillment-container').each(function () {
			var storeId=$(this).find(".selected-fulfillment").parent(".js-line-item-fullfilment").data("storeid");
			$(this).closest(".js-cart-items-row").find(".changed_selectedFilfillmentStore").val(storeId);
	});
	if($(".showDeliveryFeeMessage").val() =="true" && $(".checkmixedcart").val()=="true"){
		$(".delivery-fee-msg").removeClass("hidden")
			
	}
	 else{
		$(".delivery-fee-msg-nonmixcart").removeClass("hidden");
		$(".cart-shipping-fee-msg").addClass('hidden');
	}
	ACC.cart.deliveryInCanada();
	if ($(".page-orderSummaryPage").length > 0) {
		var tradeClass = $(".trade-class").val();
		var subTotalVal = $(".subTotal-class").val();
		var homeOwnerCode = $(".homeOwnerCode").val();
		var subtotalLimit = $(".subtotalLimit").val();
		var orderType = $("#orderType").val();
		if (tradeClass == homeOwnerCode && (parseFloat(subTotalVal) < parseFloat(subtotalLimit)) && orderType == "DELIVERY") {
			$(".btn-place-order").attr("disabled", true);
			$(".homeOwner-msg").removeClass("hidden");
		}
	}
	if ($(".page-cartPage").length > 0) {
		var tradeClass = $(".trade-class").val();
		var subTotalVal = $(".subTotal-class").val();
		var homeOwnerCode = $(".homeOwnerCode").val();
		var subtotalLimit = $(".subtotalLimit").val();
		var deliveryEligibleTotal = $(".deliveryEligibleTotal").val();
		var isDeliveryTampaGuest = $(".isDeliveryTampaBranch").val();
		var isDeliveryLAGuest = $(".isDeliveryLABranch").val();
		var isPickupaAllowedBranch = $(".isPickupaAllowedBranch").val();
		var isTampaBranch = $(".isTampaBranch").val();
		var isLABranch = $(".isLABranch").val();
		var isShippingHubOnly = $(".isShippingHubOnly").val();
		var cartDeliveryDisabled = $(".cartDeliveryDisabled").val();
		$(".deliveryAvail").removeClass("hidden");
		$(".parcelAvail").removeClass("hidden");
		var enableParcelShipping = $(".enableParcelShipping").val();

		if (enableParcelShipping != "true") {
			$(".parcel-shipping").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".parcelUnavailable").removeClass("hidden");
			$(".parcelAvail").addClass("hidden");
		}
		if (((isTampaBranch == "false")
			|| (isDeliveryTampaGuest != "" && (isDeliveryTampaGuest == "true"))
			|| (isLABranch == "false") 
			|| (isDeliveryLAGuest != "" && (isDeliveryLAGuest == "true"))) && (parseFloat(deliveryEligibleTotal) < parseFloat(subtotalLimit))) {
			if ($(".isStockAvalilableOnlyHub").val() != "true") {
				$(".cartHomeOwner-msg").removeClass("hidden");
			}
			$(".local-delivery").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".deliveryNotAvail").removeClass("hidden");
			$(".deliveryAvail").addClass("hidden");
		}
		
		if ((isDeliveryTampaGuest != "" && (isDeliveryTampaGuest == "false") && (isDeliveryLAGuest != "" && isDeliveryLAGuest == "false")) || (cartDeliveryDisabled == "true")) {
			$(".local-delivery").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".deliveryNotAvail").removeClass("hidden");
			$(".deliveryAvail").addClass("hidden");
		}
		
		if (isPickupaAllowedBranch != "" && isPickupaAllowedBranch == "false") {
			$(".branch-pickup").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".branch-pickup").removeClass("fullfilment-select");
			$(".pickUpAvail").addClass("hidden");
			$(".pickUpNotAvail").removeClass("hidden");
		}
		
		var orderType = $(".cart-orderType").val();
		if (orderType == 'PICKUP') {
			isShippingHubOnly = "false";
			$(".split-shipping-threshold-free").addClass("hidden");
				$(".split-nonshipping-threshold-free").removeClass("hidden");
		}
		if(orderType == 'DELIVERY' && $(".isSplitCartFulfilment").val()=='true'){
					isShippingHubOnly = "false";
					$(".split-shipping-threshold-free").addClass("hidden");
				$(".split-nonshipping-threshold-free").removeClass("hidden");
				}
		if(orderType == 'PARCEL_SHIPPING' && $(".isSplitCartFulfilment").val()=='true'){
			$("[data-cartentry]").each(function(){
				let target = $(this);
				let entry = Number(target.data("cartentry"));
				let index = target.data("index");
				let pickupEnabled = target.data("pickupenabled");
				let deliveryEnabled = target.data("deliveryenabled");
				if(deliveryEnabled == true || pickupEnabled == true){
					isShippingHubOnly = false;
				}
			})
			}
		if (isShippingHubOnly == "true") {
			$(".branch-pickup").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".branch-pickup").removeClass("fullfilment-select");
			$(".pickUpAvail").addClass("hidden");
			$(".pickUpNotAvail").removeClass("hidden");

			$(".local-delivery").addClass("disabled-fullfillment-method").removeClass("js-fullfilment");
			$(".deliveryNotAvail").removeClass("hidden");
			$(".deliveryAvail").addClass("hidden");

			$(".fullfillment-shipping").addClass("fullfilment-select");
			$(".fullfillment-shipping").click();
			if(!$(".js-gc-error-msg").length > 0){
				$(".shippingThreshold-msg").removeClass("hidden");
				$(".split-shipping-threshold-free").removeClass("hidden");
				$(".split-nonshipping-threshold-free").addClass("hidden");
			}
		}


	}
	if($(".page-cartPage").length){
		ACC.cart.showPriceLoader();
	}
	//To show the split/mixed header when shipping only product available  
	ACC.cart.cartEntriesAppend();
});