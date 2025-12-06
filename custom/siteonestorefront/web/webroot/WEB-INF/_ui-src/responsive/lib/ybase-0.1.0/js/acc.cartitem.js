ACC.cartitem = {

	_autoload: [
		"bindCartItem",
		"disableQtyButton",
		"cartInitiation",
		"cartDeletion",
		"cartIdDetails"
	],
	
	submitTriggered: false,
	disableQtyButton: function () 
    {	
		$('[id^="quantityBoxDisable_"]').each(function() 
		{
			var elementId = $(this).attr('id');
			var itemNumber = elementId.split('_')[1];
			if($('#'+elementId).val() == 'true')
			{
				$("#plusQty_"+itemNumber).prop("disabled", true);
				$("#minusQty_"+itemNumber).prop("disabled", true);
			}
		});
    },
	bindCartItem: function ()
	{
		$('.js-remove-entry-button').on("click", function ()
		{	
			loading.start();
			var entryNumber = $(this).attr('id').split("_");
			var form = $(this).closest("form");

			var productCode = form.find('input[name=productCode]').val();
			var initialCartQuantity = form.find('input[name=initialQuantity]');
			var cartQuantity = form.find('input[name=quantity]');
			
			var productName = form.find('input[name=productName]').val();
			var productCategory = form.find('input[name=productCategory]').val();
			var productSubCategory = form.find('input[name=productSubCategory]').val();	
			var productQty = form.find('input[name=initialQuantity]').val();
			var nearbyProduct= form.find('input[name=productnearbyItem]').val();
			 if(nearbyProduct === ""){
    						  var productNearBy= "true"
    					  }
    					  else{
    						  var productNearBy= "false"
    					  }
			var productfulfillment = $(".changed_selectedFilfillment").val();
			var productStore = $("#analyticsstore").val().split("#")[1];
			var productBackOrdercheck=form.find('input[name=analytics_backourderValueItem]').val();
			var remainingBackOrder= form.find('input[name=remainingQtyItem]').val();
			var productStockStatus=form.find('input[name=productstockStatusItem]').val().replace(/<(.|\n)*?>/g, '');
			if(productBackOrdercheck=="true"){
				var productBackOrder= productQty;
			}
			else if(remainingBackOrder > 0){
				var productBackOrder= remainingBackOrder;
			}
			else{
				var productBackOrder= "";
			}
			var productPrice =form.find('input[name=productpriceItem]').val();
			
			
			if(form.find('input[name=transferableFlagItem]').val()=="false" && form.find('input[name=isStockInNearbyBranchItem]').val()=="true"){
				var productError= "no fulfillment option";
			}
			else{
				var productError= "";
			}
			cartRemoveAnalytics(productCode, productName, productCategory, productSubCategory,productNearBy,productQty,productfulfillment,productStore,productBackOrder,productPrice,productStockStatus,productError);

			ACC.track.trackRemoveFromCart(productCode, initialCartQuantity.val());

			cartQuantity.val(0);
			initialCartQuantity.val(0);
			if ($('#isAnonymous').val() == "false"){
       			var deletedCartID= $("#currentCartId").val();
            	}
          	  else{
          		var deletedCartID= $("#anonymousCartId").val();
          	  }
			if($("input[id=cartItemsCountVar]").val() >=1) {
				digitalData.eventData = {
						deletedCartId: deletedCartID,
						linktype:"",
	                	linkName: "Remove",
	                	onClickPageName: "checkout:cart",
             	};
				if ($("input[id=cartItemsCountVar]").val() == 1) {
					try {
						_satellite.track("cartDelete");
					} catch (e) { }
				}
				else {
					try {
						_satellite.track("Remove Cart");
					} catch (e) { }
				}
			}
			form.submit();
		});
		$('.js-remove-entry-button a').on('click', function(e) {
			e.preventDefault();
		});
		$('.js-update-entry-quantity-btn').on("click",function(e){
			var max_qty=9999;
			var $target=$(e.target);
			var qty = $(this).parent().children('.js-update-entry-quantity-input').val();
			var form = $target.closest('form');
			var intervalQTY = form.find('input[name=uomintervalqtyCart]').val();
			var intervalValidation = form.find(".intervalQtyInfo_cart").is(":visible");
			if(qty && !isNaN(qty) ){
				qty=Number(qty);
			}else{
				qty=0;
			}
			if($target.hasClass("plusQty")){
				if(qty>=max_qty)
					{
					$target.addClass("disabled")
					}
				else
				{	
					if(intervalQTY > 1 && intervalValidation){
						var currentMultiple = Math.floor(qty / intervalQTY);
						var nextMultiple = currentMultiple + 1;
						qtyVal = nextMultiple * intervalQTY;
						if(qtyVal>=max_qty)
						{
							$target.addClass("disabled")
						}else{
							$(this).prev().val(qtyVal);
						}
					}				
					else{
						$(this).prev().val(qty+1);
					}			
				}
			}else{
				if(qty>0){
					if(intervalQTY > 1 && intervalValidation){
						var isMultiple = qty % intervalQTY === 0;
						var previousMultiple = isMultiple ? qty - intervalQTY : qty - (qty % intervalQTY);
						qtyVal = Math.max(intervalQTY, previousMultiple);
						if(qtyVal>0)
						{
							$(this).next().val(qtyVal);
						}
					}else{
						$(this).next().val(qty-1);
					}		
				}
			}
			$(this).parent().find('.js-update-entry-quantity-input').blur();
			return false;
		});
		$('.js-update-entry-quantity-input').on("blur", function (e)
		{
			ACC.cartitem.handleUpdateQuantity(this, e);

		}).on("keyup", function (e)
		{
			return ACC.cartitem.handleKeyEvent(this, e);
		}
		).on("keydown", function (e)
		{
			return ACC.cartitem.handleKeyEvent(this, e);
		}
		);
		

			  $(".js-update-entry-quantity-input, #quantityId").keypress(function (e) {
				  	var regex = new RegExp("^[0-9]+$");
		        	var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);   
		        	var ignoredKeys = [8,0];
		                if (ignoredKeys.indexOf(e.which) >=0 || regex.test(str)) {   
		        	       return true;
		        	    }       	    
		        	    e.preventDefault();
		        	    return false;
	        });
	        
	},

	handleKeyEvent: function (elementRef, event)
	{
		//console.log("key event (type|value): " + event.type + "|" + event.which);

		if (event.which == 13 && !ACC.cartitem.submitTriggered)
		{
			ACC.cartitem.submitTriggered = ACC.cartitem.handleUpdateQuantity(elementRef, event);
			return false;
		}
		else 
		{
			// Ignore all key events once submit was triggered
			if (ACC.cartitem.submitTriggered)
			{
				return false;
			}
		}

		return true;
	},

	handleUpdateQuantity: function (elementRef, event)
	{

		var form = $(elementRef).closest('form');

		var productCode = form.find('input[name=productCode]').val();
		var initialCartQuantity = form.find('input[name=initialQuantity]').val();
		var newCartQuantity = form.find('input[name=quantity]').val();

		if(initialCartQuantity != newCartQuantity)
		{
			ACC.track.trackUpdateCart(productCode, initialCartQuantity, newCartQuantity);
			
			//bigBagChecker AJAX call
			ACC.cart.ajaxBigboxchecker(productCode,false,"CHECKED",newCartQuantity);
			
			form.submit();

			return true;
		}

		return false;
	},
	
	cartInitiation : function(e){
		if ($(".page-cartPage").length > 0 && $('#cartID').length > 0 && $('#welcomeOverlay').length > 0){
			try {
				_satellite.track("Cart Initiation");
		    } catch (e) {}
		 
		}
	 },
	 
	 cartIdDetails: function(e){
		 
		 if ($('.loginstatus').val() != ''  || $(".page-siteOneCheckoutPage, .page-orderSummaryPage, .page-orderConfirmationPage").length > 0){
			 try {
					_AAData.recentCartId= $("#currentCartId").val();
					_AAData.cartId =$("#currentCartId").val();
				}catch(e){}
		 }
		 else{
			 _AAData.cartId= $("#anonymousCartId").val();
		 }
		  	
	 },
	 
	 
};

function cartRemoveAnalytics(productCode, productName, productCategory, productSubCategory,productNearBy,productQty,productfulfillment,productStore,productBackOrder,productPrice,productStockStatus,productError) {
    digitalData.cart = [{
        item: {
            productInfo: {
                productID: productCode,
                productName: productName,
                productCategory: productCategory,
                productSubCategory: productSubCategory,
				productSelectedBranch: productNearBy,
				productQty:productQty,
				productfulfillment:productfulfillment,
				productStore:productStore,
				productBackOrder:productBackOrder,
				productPrice:productPrice,
				productStockStatus:productStockStatus,
				productError:productError,
            }
        }
    }];
    console.log("digitalData" + JSON.stringify(digitalData));
}
