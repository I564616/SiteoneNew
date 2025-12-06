var ACC = ACC || {}; // make sure ACC is available
var liClone;

if ($("#quickOrder").length > 0) {
    ACC.quickorder = {
        _autoload: [
            "bindClearQuickOrderRow",
            "bindAddSkuInputRow",
            "bindResetFormBtn",
            "bindAddToCartClick",
            "addMoreRow",
			"checkQuantity"
        ],

        $quickOrderContainer: $('.js-quick-order-container'),
        $quickOrderMinRows: Number($('.js-quick-order-container').data('quickOrderMinRows')),
        $quickOrderMaxRows: Number($('.js-quick-order-container').data('quickOrderMaxRows')),
        $productExistsInFormMsg: $('.js-quick-order-container').data('productExistsInFormMsg'),
        $productNotPurchaseble: $('.js-quick-order-container').data('productNotPurchaseble'),
        $quickOrderLeavePageMsg: $('#quickOrder').data('gridConfirmMessage'),
        $hiddenSkuInput: 'input.js-hidden-sku-field',
        $hiddenSkuNameInput: 'input.js-hidden-skuname-field',
        $hiddenProductCategoryInput: 'input.js-hidden-productcategory-field',
        $hiddenProductSubCategoryInput: 'input.js-hidden-productsubcategory-field',
        $hiddeninventoryUOMIDInput: 'input.js-hidden-inventoryUOMID-field',
        $addToCartBtn: $('#js-add-to-cart-quick-order-btn-top, #js-add-to-cart-quick-order-btn-bottom'),
        $resetFormBtn: $('#js-reset-quick-order-form-btn-top, #js-reset-quick-order-form-btn-bottom'),
        $productInfoContainer: '.js-product-info',
        $skuInputField: '.js-sku-input-field',
        $qtyInputField: '.js-quick-order-qty',
        $jsLiContainer: 'li.js-li-container',
        $removeQuickOrderRowBtn: '.js-remove-quick-order-row',
        $skuValidationContainer: '.js-sku-validation-container',
        $qtyValidationContainer: '.js-qty-validation-container',
        $productItemTotal: '.js-quick-order-item-total',
        $classHasError: 'has-error',
        $productText: '.productText',
        productDatalist: {},

        bindResetFormBtn: function () {
            ACC.quickorder.$resetFormBtn.on("click", ACC.quickorder.clearForm);
            
        },

        bindAddToCartClick: function () {
            ACC.quickorder.$addToCartBtn.on("click", ACC.quickorder.addToCart);
        },

        bindAddSkuInputRow: function () {
            $(ACC.quickorder.$skuInputField).on("focusin", ACC.quickorder.addInputRow).on("focusout keydown", ACC.quickorder.handleFocusOutOnSkuInput);
        },

        bindClearQuickOrderRow: function () {
            $(ACC.quickorder.$removeQuickOrderRowBtn).on("mousedown", ACC.quickorder.clearQuickOrderRow);
        },

        addToCart: function () {
            $('#quickOrderQtyError').html("");
            loading.start();

            $.ajax({
                url: ACC.quickorder.$quickOrderContainer.data('quickOrderAddToCartUrl'),
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: ACC.quickorder.getJSONDataForAddToCart(),
                success: function (response) { 
                	var products = [];
                	$(ACC.quickorder.$qtyInputField).each(function() {
                	    var qty = Number($(this).val());
                	    if (qty > 0) {
                	        var sku = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddenSkuInput).val()).trim();
                	        var skuName = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddenSkuNameInput).val()).trim();
                	        var productCategory = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddenProductCategoryInput).val()).trim();
                	        var productSubCategory = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddenProductSubCategoryInput).val()).trim();
                	        products.push({
                	            productInfo: {
                	                "productID": sku,
                	                "productName": skuName,
                	                "productCategory": productCategory,
                	                "productSubCategory": productSubCategory,
                	                "addToCartLocation": "quick-order"
                	            }
                	        });
                	    }
                	});
                	
                	var cartItems = $(".mini-cart-link").data("miniCartItemsText");
         	        var miniCartRefreshUrl = $(".mini-cart-link").data("miniCartRefreshUrl");
         	        $.ajax({
         	            url: miniCartRefreshUrl,
         	            cache: false,
         	            type: 'GET',
         	            success: function(jsonData){
         	            	if ($('#isAnonymous').val() == "false"){
         	       			var currentCartId= $("#currentCartId").val();
         	            	}
         	          	  else{
         	          		var currentCartId= $("#anonymousCartId").val();
         	          	  }
         	            	 if($(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0"){ 
         	            		digitalData.cart = {
         	            				cartId: currentCartId,
         	            				event :  "scOpen, scAdd",
         	                			
         	                	};
         	            	}
         	            	else
         	            	{
         	            		digitalData.cart = {
         	            				cartId: currentCartId,
         	            				event :  "scAdd",
         	                			
         	                	};	
         	            	}
         	            	 try {
         	                	    _satellite.track("Add Cart");
         	                	} catch (e) {} 
         	               }
         	           
         	        });
                	
         	        digitalData.product = products;                	
                	
         	        ACC.quickorder.handleAddToCartSuccess(response);

                    var productsForRDT = $.map(products, function(product) {
                        return {
                            id: product.productInfo.productID,
                            name: product.productInfo.productName,
                            category: product.productInfo.productCategory
                        }
                    });
                    rdt("track", "AddToCart", {
                        currency: "USD",
                        itemCount: products.length,
                        value: '',
                        products: productsForRDT
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // log the error to the console
                    console.log("The following error occurred: " + textStatus, errorThrown);
                }
            }).always(function(){
                loading.stop();
            });
        },

        handleAddToCartSuccess: function (response) {
            if ($(response.quickOrderErrorData).length > 0) {
                ACC.quickorder.disableBeforeUnloadEvent();
            }
            var lookup = {};
            response.quickOrderErrorData.forEach(function (el) {
                lookup[el.sku] = el.errorMsg;
            });

            $(ACC.quickorder.$qtyInputField).each(function () {
                var parentLi = ACC.quickorder.getCurrentParentLi(this);
                var sku = ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuInputField).val();
                var errorMsg = lookup[sku];
				if (errorMsg) {
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(errorMsg);
                }
                else {
                    	//ACC.quickorder.findElement(parentLi, ACC.quickorder.$removeQuickOrderRowBtn).trigger("mousedown");
                    	ACC.quickorder.findElement(parentLi, ACC.quickorder.$productInfoContainer).remove();
                        ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
                        ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuInputField).val('');
                        ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val('');
                        //ACC.quickorder.findElement(parentLi, ACC.quickorder.$removeQuickOrderRowBtn).remove();
                        $(parentLi).find(ACC.quickorder.$removeQuickOrderRowBtn).addClass('hidden');
                        $('.price-unavailable-msg').addClass('hidden');
                        ACC.quickorder.enableDisableAddToCartBtn();
                        ACC.quickorder.handleBeforeUnloadEvent();
                        
                        var quickOrderMinRows = ACC.quickorder.$quickOrderMinRows;
                        if ($('.js-ul-container li.js-li-container').length > quickOrderMinRows) {
                            parentLi.remove();
                            ACC.quickorder.bindClearQuickOrderRow();
                        }
                    
                }
            });

            ACC.quickorder.handleBeforeUnloadEvent();
            $(window).scrollTop(0);
            ACC.product.displayAddToCartPopup(response);
        },

        getJSONDataForAddToCart: function () {
            var skusAsJSON = [];
            $(ACC.quickorder.$qtyInputField).each(function () {
                var qty = Number($(this).val());
                if (qty > 0) {
                    var sku = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddenSkuInput).val()).trim();
                    var inventoryUOMID = (ACC.quickorder.findElementInCurrentParentLi(this, ACC.quickorder.$hiddeninventoryUOMIDInput).val()).trim();
                    
                    skusAsJSON.push({"product": {"code": sku}, "quantity": qty, "uomId":inventoryUOMID});
                }
            });
            return JSON.stringify({"cartEntries": skusAsJSON});
        },

        handleFocusOutOnSkuInput: function (event) {

            var key = event.charCode ? event.charCode : event.keyCode ? event.keyCode : 0;
            if (key == 13) {
                $(event.target).focusout();
            }
            if (event.type == "focusout")
            {
                if($(event.target).val()!='') 
                {
                    loading.start();
                }
                var parentLi = ACC.quickorder.getCurrentParentLi(event.target);
                var productCode = (event.target.value).trim();
                if(productCode != null && productCode != undefined && productCode != '') 
                {
                    if(ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val() == '') 
                    {
                        ACC.quickorder.handleGetProduct(event);
                        ACC.quickorder.handleBeforeUnloadEvent();
                    } else {
                        loading.stop();
                    }
                }
                else
                {
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
                }

            }
        },

        handleFocusOutOnQtyInput: function (event) {
            $('#quickOrderQtyError').html("");
            var key = event.charCode ? event.charCode : event.keyCode ? event.keyCode : 0;
            if (key == 13) {    
                event.preventDefault();
                var parentLi = ACC.quickorder.getCurrentParentLi(event.target);             
                parentLi.next().find(ACC.quickorder.$skuInputField).focus();
                $(event.target).focusout();
            }
            if (event.type == "focusout") {
                ACC.quickorder.validateAndUpdateItemTotal(event);
                ACC.quickorder.enableDisableAddToCartBtn();
            }
        },

        clearForm: function () {
            $(document).ready(function(){
                window.location.reload();
                $(this).scrollTop(0);
            });
        },

        validateAndUpdateItemTotal: function (event) {
            var parentLi = ACC.quickorder.getCurrentParentLi(event.target);
            var qtyValue = (ACC.productorderform.filterSkuEntry($(event.target).val())).trim();
            if (isNaN(qtyValue) || qtyValue == "") 
            {
                if(ACC.quickorder.$classHasError == "has-error")
                {
                    $("#quickOrderQtyError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>"+ACC.config.productDetail+"</font></div></div> ");
                }
                qtyValue = 0;
                $(event.target).removeClass(ACC.quickorder.$classHasError);
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyValidationContainer).text('');
                $(event.target).val(0);
            }
            else 
            {
                qtyValue = Number(qtyValue);
                $(event.target).val(qtyValue);
                /*var maxQty = (ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyInputField).data('maxProductQty')).trim();*/
                var maxQty = "";
                var stockLevelStatus = (ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyInputField).data('stockLevelStatus')).trim();
                maxQty = ($.isEmptyObject(maxQty)) ? "FORCE_IN_STOCK" : Number(maxQty);
                if (!isNaN(maxQty) && qtyValue > maxQty) 
                {
                    $(event.target).addClass(ACC.quickorder.$classHasError);
                    var qtyValidationContainer = ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyValidationContainer);
                    qtyValidationContainer.text(qtyValidationContainer.data('maxProductQtyMsg'));
                    qtyValue = maxQty;
                    $(event.target).val(maxQty);
                }
                else 
                {
                    $(event.target).removeClass(ACC.quickorder.$classHasError);
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyValidationContainer).text('');
                }
            }

            if (qtyValue > 0) {
                 var formatter = new Intl.NumberFormat('en-US', {
                  style: 'currency',
                  currency: 'USD',
                  minimumFractionDigits: 2,
                 });
                 
                var price = (ACC.quickorder.findElement(parentLi, '.js-product-price').data('productPrice')).split('$')[1].replace(",", "");
                var itemPrice = parseFloat(price);
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$productItemTotal)
                    .html(formatter.format(itemPrice * qtyValue));
                    
            }
            else {
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$productItemTotal).text('');
            }
        },

        clearQuickOrderRow: function () {
            var quickOrderMinRows = ACC.quickorder.$quickOrderMinRows;
            var parentLi = ACC.quickorder.getCurrentParentLi(this);
            if ($('.js-ul-container li.js-li-container').length > quickOrderMinRows) {
                parentLi.remove();
                ACC.quickorder.bindClearQuickOrderRow();
            }
            else {
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$productInfoContainer).remove();
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuInputField).val('');
                ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val('');
            }
            $(parentLi).find(ACC.quickorder.$removeQuickOrderRowBtn).addClass('hidden');
            $('.price-unavailable-msg').addClass('hidden');
            $(parentLi).find(ACC.quickorder.$productText).removeClass('productText');
            ACC.quickorder.enableDisableAddToCartBtn();
            ACC.quickorder.handleBeforeUnloadEvent();
        },

        addInputRow: function (event) {

            if ($('.js-quick-order-container li.js-li-container:last-child').find(ACC.quickorder.$skuInputField).is($(event.target)) &&
                $(ACC.quickorder.$jsLiContainer).length < ACC.quickorder.$quickOrderMaxRows) {
                
            	liClone = $('.js-quick-order-container li.js-li-container').first().clone();
                $(liClone).find(ACC.quickorder.$removeQuickOrderRowBtn).addClass('hidden');
                ACC.quickorder.findElement(liClone, ACC.quickorder.$productInfoContainer).remove();
                ACC.quickorder.findElement(liClone, ACC.quickorder.$skuValidationContainer).text('');
                ACC.quickorder.findElement(liClone, ACC.quickorder.$hiddenSkuInput).val('');
                var currentSkuInputField = ACC.quickorder.findElement(liClone, ACC.quickorder.$skuInputField);
                currentSkuInputField.val('');
                currentSkuInputField.focusin(ACC.quickorder.addInputRow).focusout(ACC.quickorder.handleFocusOutOnSkuInput).keydown(ACC.quickorder.handleFocusOutOnSkuInput);
                ACC.quickorder.findElement(liClone, ACC.quickorder.$removeQuickOrderRowBtn).click(ACC.quickorder.clearQuickOrderRow);
              
                $(".item__list--item:last-child").click(function() {
                	
                	$('.js-ul-container').append(liClone);

                });
               
                
               
            }
        },

        handleGetProduct: function (event) {
            var parentLi = ACC.quickorder.getCurrentParentLi(event.target);
            var productCode = (event.target.value).trim();
            $(event.target).val(productCode);
            if (!ACC.quickorder.isCurrentSkuSameAsPrevious(parentLi, productCode)) {
                if (productCode.length > 0) {
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$productInfoContainer).remove();

                   /* if (ACC.quickorder.isDuplicateSku(event.target, productCode)) 
                    {
                        ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(ACC.quickorder.$productExistsInFormMsg);
                    }
                    else 
                    {*/
                        ACC.quickorder.getAndDisplayProductInfo(event, parentLi, productCode);
                   // }
                    //ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val(productCode);
                }
                else 
                {
                    $(event.target).removeClass(ACC.quickorder.$classHasError);
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$productInfoContainer).remove();
                }
            }
        },

        isCurrentSkuSameAsPrevious: function (parentLi, productCode) {
            return ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val() == productCode;
        },

        isDuplicateSku: function (currentInput, productCode) {
            var exists = false;
            $(ACC.quickorder.$hiddenSkuInput).each(function () {
                if ($(this).val() == productCode && !$(this).is($(currentInput))) {
                    exists = true;
                    return false
                }
            });
            return exists;
        },
        
        

        getAndDisplayProductInfo: function (event, parentLi, productCode) {
            if(!$(event.target).prop("disabled")){
            var url = ACC.config.encodedContextPath + '/quickOrder/productInfo?code=' + encodeURIComponent(productCode);
            $.getJSON(url, function (result) 
            {
                loading.stop();
                $(event.target).prop("disabled",false);
                if (result.errorMsg != null && result.errorMsg.length > 0) 
                {
                    $(event.target).addClass(ACC.quickorder.$classHasError);
                    ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(result.errorMsg);
                }
                else
                {                   
                    var currentHiddenInputSku = ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput);
                    if(result.productDataList.length == 1)
                    {
						if (ACC.quickorder.isDuplicateSku(currentHiddenInputSku, result.productDataList[0].code)) 
                        {
                            ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(ACC.quickorder.$productExistsInFormMsg);
                        } else {
							ACC.quickorder.populateProductData(event,parentLi,result.productDataList[0]);
                            
                        }
                    }
                    else
                    {
                        ACC.quickorder.productDatalist = result.productDataList;
                        var productList = ACC.quickorder.productDatalist;
                        var popupContent = "<div class='PopupBox'>";
                        for(var i =0;i<productList.length;i++)
                        {
                            popupContent = popupContent + "<a class='popupProduct' data-code='" + i + "' data-index='" + $('.js-li-container').index(parentLi) + "' href='#'>" + (productList[i].itemNumber) + "<a/><br/>";
                        }
                        popupContent = popupContent + "</div>";
                        var title = "Select the Product";
                         ACC.colorbox.open(title, {
                                 html: popupContent,
                                 maxWidth:"100%",
                                 width:"600px",
                                 initialWidth :"380px"
                                 });
                        
                        
                    }
                }
                    ACC.quickorder.enableDisableAddToCartBtn();
            });
            }else{
                loading.stop();
                ACC.quickorder.enableDisableAddToCartBtn();
                return false;
            }
        },

        populateProductData: function (event, parentLi, productData) 
        {
	        if(null != productData.salePrice && productData.salePrice !=undefined)
            {
            var formatter = new Intl.NumberFormat('en-US', {
                  style: 'currency',
                  currency: 'USD',
                  minimumFractionDigits: 2,
             });
            productData.salePrice=formatter.format(productData.salePrice);
            }
            
            if(null != productData.storeStockAvailabilityMsg && productData.storeStockAvailabilityMsg != undefined)
            {
                 var status = productData.storeStockAvailabilityMsg;
            }

           if(productData.inStockImage==true){
        	   if(productData.isStockInNearbyBranch==true)
        	   {
        		   status = '<img src="/_ui/responsive/theme-lambda/images/nearby-icon.svg" alt=""/>' + '<p>' + status + '</p>';
        	   }
        	   else
        	   {
        		   status = '<img src="/_ui/responsive/theme-lambda/images/in-stock-icon.svg" alt=""/>' + '<p>' + status + '</p>';
        	   }
                
            } else if(productData.notInStockImage==true){
                status = '<img src="/_ui/responsive/theme-lambda/images/back-order-icon.svg" alt=""/>' + '<p>' + status + '</p>';
            } else if(productData.outOfStockImage==true){
                status = '<img src="/_ui/responsive/theme-lambda/images/non-stock-icon.svg" alt=""/>' +  '<p>' + status + '</p>';
            }
            $(event.target).removeClass(ACC.quickorder.$classHasError);
            $(event.target).addClass('productText');
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
            $('#quickOrderRowTemplate').tmpl(productData).insertAfter(ACC.quickorder.findElement(parentLi, '.js-sku-container'));
            
            if(null != status && status != undefined)
            {
                var element = '.item__stock_'+productData.code;
                $(element).html(status);
            }
            
            var qtyInputField = ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyInputField);
            qtyInputField.focusout(ACC.quickorder.handleFocusOutOnQtyInput).keydown(ACC.quickorder.handleFocusOutOnQtyInput);
            var stockLevelStatus = productData.stock.stockLevelStatus.code;
            if (!productData.isSellable) 
            {
                qtyInputField.val(0);
                qtyInputField.prop('disabled', true);
                $('#quickorderminusqtybtn_'+productData.code).prop('disabled', true);
                $('#quickorderplusqtybtn_'+productData.code).prop('disabled', true);
            }
            else 
            {
                qtyInputField.focus().select();
            }
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val(productData.code);
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenProductCategoryInput).val(productData.level1Category);
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenProductSubCategoryInput).val(productData.level2Category); 
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddeninventoryUOMIDInput).val(productData.sellableUoms[0].inventoryUOMID);
            ACC.quickorder.enableDisableAddToCartBtn();
            $(parentLi).find(ACC.quickorder.$removeQuickOrderRowBtn).removeClass('hidden');
            
        },
        
        handleBeforeUnloadEvent: function () {
            if (ACC.quickorder.isAnySkuPresent()) {
                ACC.quickorder.disableBeforeUnloadEvent();
                ACC.quickorder.enableBeforeUnloadEvent();
            }
            else {
                ACC.quickorder.disableBeforeUnloadEvent();
            }
        },

        disableBeforeUnloadEvent: function () {
            $(window).off('beforeunload', ACC.quickorder.beforeUnloadHandler);
        },

        enableBeforeUnloadEvent: function () {
            $(window).on('beforeunload', ACC.quickorder.beforeUnloadHandler);
        },

        beforeUnloadHandler: function () {
            return ACC.quickorder.$quickOrderLeavePageMsg;
        },

        enableDisableAddToCartBtn: function () {
	        var addToCartButtonEnabled = ACC.quickorder.shouldAddToCartBeEnabled();
            var isOrderingAccount =$('#isOrderingAccount').val();
            
            if(isOrderingAccount == 'false'){
                ACC.quickorder.$addToCartBtn.attr('disabled', 'disabled');
                $('#TopOrderingAccountMsg').show();
                $('#BottomOrderingAccountMsg').show();
            }
            
            // if there are no items to add, disable addToCartBtn, otherwise, enable it
            if (addToCartButtonEnabled) {
                ACC.quickorder.$addToCartBtn.removeAttr('disabled');
            } else {
                ACC.quickorder.$addToCartBtn.attr('disabled', 'disabled');
            }
        },
        
        shouldAddToCartBeEnabled: function () {
            var sum = 0;
            var enable = true;
            var totalItems = 0;
            var isQtyExceeded=false


            $(ACC.quickorder.$qtyInputField).each(function () {
                var qty = Number($(this).val());
                if (qty > 0)
                {
                    totalItems = totalItems + 1;
                }
            });
            
            $(ACC.quickorder.$qtyInputField).each(function () {
                var qty = Number($(this).val());
				if (($(this).data('stock-level-status') === "outOfStock") && totalItems==0){
					enable=false;
				}
                if (qty > 0)
                {
                var str = this.value.trim();
                // .trim() may need a shim
                if (str) 
                {   // don't send blank values to `parseInt`
                    sum += parseInt(str, 10);
                        if (str == 0) {
                        var result=totalItems-1;
                        if(result==0)
                        {
                            ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+ACC.config.quickCart);
                        }
                        else if(result>0)
                        {
                            ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+ result +" "+ACC.config.quickCart);
                        }
                        else
                        {
                            ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+ totalItems +" "+ACC.config.quickCart);
                        }
                     
                    }
                    
                    ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+"["+ totalItems +"]"+" "+ACC.config.quickCart);  

                    }
                    if ($(this).data('is-nursery') === "Nursery") {
                        if ($(this).val().trim() > $(this).data("max-product-qty")) {
                            isQtyExceeded = true;
                                                   }
                    }
                    if (sum === 0 || isQtyExceeded) {
                        enable = false;
                       
                    }
                }
            });
            
            if(!enable && totalItems == 0)
            {
                ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+ACC.config.quickCart);
            } else if(totalItems == 0){
				enable = false;
				ACC.quickorder.$addToCartBtn.html(ACC.config.quickAdd+" "+ACC.config.quickCart);
			}

            
            return enable;
        },

        isAnySkuPresent: function () {
            var present = false;
            $(ACC.quickorder.$skuInputField).each(function () {
                var str = (this.value).trim();  // .trim() may need a shim
                if (str) {
                    present = true;
                    return false;
                }
            });
            return present;
        },

        getCurrentParentLi: function (currentElement) {
            return $(currentElement).closest(ACC.quickorder.$jsLiContainer);
        },

        findElement: function (currentElement, toFind) {
            return $(currentElement).find(toFind);
        },
		checkQuantity:function(){
            function validateQuantityQuickOrder(elem){
	            $(elem).closest(".cart-mb-border").find(".qorder-qty-alert").addClass("hide");
			    $(elem).removeClass("text-red border-red");
			    if ($(elem).data('is-nursery')==="Nursery") {	
				if ($(elem).val().trim() > $(elem).data("max-product-qty")) {
                    $(elem).closest(".cart-mb-border").find(".qorder-qty-alert").removeClass("hide");
				$(elem).addClass("text-red border-red");
				 ACC.quickorder.$addToCartBtn.attr('disabled', 'disabled');
					}
				else{
					ACC.quickorder.enableDisableAddToCartBtn();
				}
			}
            }
			$(document).on("input",ACC.quickorder.$qtyInputField,function() {
                validateQuantityQuickOrder(this);
        })
        $(document).on("updated-qty", function (e) {
            var element=$(e.target).siblings('.js-quick-order-qty');
            validateQuantityQuickOrder(element);
        })
		},
		        
        addMoreRow: function ()
        {
            $(document).on("click","#link",function() 
            {  
                 var min=$('.js-quick-order-container li.js-li-container').first();
                 var minRows = ACC.quickorder.$quickOrderMinRows;
                 var rowCount = $(ACC.quickorder.$skuInputField).length;
                  
                 if(rowCount < 25) {
                	 
                	 if(rowCount > 20) {
                		 minRows = 25 - rowCount;
                	 }
                     for (var min = 0; min < minRows ; min++) 
                     {
                     var liClone = $('.js-quick-order-container li.js-li-container').first().clone();
                     $(liClone).find(ACC.quickorder.$removeQuickOrderRowBtn).addClass('hidden');
                     ACC.quickorder.findElement(liClone, ACC.quickorder.$productInfoContainer).remove();
                     ACC.quickorder.findElement(liClone, ACC.quickorder.$skuValidationContainer).text('');
                     ACC.quickorder.findElement(liClone, ACC.quickorder.$hiddenSkuInput).val('');
                     var currentSkuInputField = ACC.quickorder.findElement(liClone, ACC.quickorder.$skuInputField);
                     currentSkuInputField.val('');
                     currentSkuInputField.focusin(ACC.quickorder.addInputRow).focusout(ACC.quickorder.handleFocusOutOnSkuInput).keydown(ACC.quickorder.handleFocusOutOnSkuInput);
                     ACC.quickorder.findElement(liClone, ACC.quickorder.$removeQuickOrderRowBtn).click(ACC.quickorder.clearQuickOrderRow);
                     $('.js-ul-container').append(liClone);
                 }
                 }
                 
            });
            
            $(document).on("click",".popupProduct",function() 
                    { 
                        var productCodeIndex = $(this).data("code");
                        var index = $(this).data("index");
                        var parentLi = $('.js-li-container')[index];
                        var event = jQuery.Event("focusout");
                        var target = $('.js-sku-input-field')[index];
                        event.target = target;
                        var product = ACC.quickorder.productDatalist[productCodeIndex];
                        var liClone = $('.js-quick-order-container li.js-li-container').first().clone();
                        var currentSkuInputField = ACC.quickorder.findElement(liClone, ACC.quickorder.$skuInputField);
                        ACC.colorbox.close();
                        if (ACC.quickorder.isDuplicateSku(event.target, product.code)) {
                            ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(ACC.quickorder.$productExistsInFormMsg);
                        } else {
                            ACC.quickorder.findElement(parentLi, ACC.quickorder.$hiddenSkuInput).val(product.code);
                            if(product.purchasable==true)
                            {
                                ACC.quickorder.populateProductData(event,parentLi,product);
                            }else
                            {
                                 ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text("Product cannot be purchased. Please enter another SKU");
                            }
                        }
                        
                    });
        },

        findElementInCurrentParentLi: function (currentElement, toFind) {
            return $(currentElement).closest(ACC.quickorder.$jsLiContainer).find(toFind);
        }
    };
    
    $(document).on("keydown", '.js-quick-order-qty', function (e) {

        if (($(this).val() != " " && ((e.which >= 48 && e.which <= 57 ) || (e.which >= 96 && e.which <= 105 ))  ) || e.which == 8 || e.which == 46 || e.which == 37 || e.which == 39 || e.which == 9) {
        }
 
        else {
            e.preventDefault();
        }
    })
    
    
    $(document).on("click", '.js-update-entry-quantity-list-btn', function (e) {

        var $target=$(e.target);
        var qty=$(this).siblings('.js-quick-order-qty').val();
        if(qty && !isNaN(qty) ){
            qty=Number(qty);
        }else{
            qty=0;
        }
        if($target.hasClass("plusQty")){
            if(qty<9999){
            	$(this).siblings('.js-quick-order-qty').val(qty+1);
            }
        }else{
            if(qty>0){
            	$(this).siblings('.js-quick-order-qty').val(qty-1);
            }
        }
        $(this).siblings('.js-quick-order-qty').blur();
        $(this).siblings('.js-quick-order-qty').focus();
        $(this).trigger('updated-qty');
        return false;
    })
}
 