ACC.productcardvariant = {
    _autoload: [
        "bindCardVariantProducts"
    ],

    bindCardVariantProducts: function() {
	   if ($(".plpviewtype").val() === "card") {
		   const hasVariantProducts = $('.product-item[data-cardvariantcount!=""]').length > 0;
            if (!hasVariantProducts) {
                return;
            }
      	const products = $(".product-item");
        const totalProducts = products.length;
        const batchSize = 4;
    
        if (totalProducts > 4) {
            loadVariantsBatch(4);
        }
    
        function loadVariantsBatch(startIndex) {
			if (startIndex >= totalProducts) {
                    //loading.stop();
                    return;
            }
            const batch = products.slice(startIndex, startIndex + batchSize);
            const payload = {};
            batch.each(function () {
				if ($(this).data('cardvariantcount') !== "" && $(this).data('cardvariantcount') > 1) {
                        const productCode = $(this).data('product-id');
                        const variantCodes = $(this).find('.variant-codes').attr('value');
                        if (variantCodes) {
                            const variantCodesInArrOfStr = JSON.parse(variantCodes).map(num => num.toString());
                            payload[productCode] = variantCodesInArrOfStr;
                        }
                }
            });
            if (Object.keys(payload).length === 0) {
                    loadVariantsBatch(startIndex + batchSize);
                    return;
            }
                /* const requestData = Object.entries(payload).map(([key, values]) => {
                    return `${key}~${values}`;
                }).join("|"); */
            if (startIndex === 4) {
                    //loading.start();
            }
            $.ajax({
                url: ACC.config.encodedContextPath + '/c/getVariantProducts',
                type: 'POST',
                cache: false,
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify({ "count": 4, "variantSkus": payload, viewType: "card"}),//{ "count": 2, "variantSkus": requestData },
                success: function (response) {
                    /* console.log("**************** START");
                    console.log(response.variants);
                    console.log("**************** END"); */
                    const variantsHTML = $("<div>").html($("<div>").html(response.cardvariants).text());
                    Object.keys(payload).forEach(function (productCode) {	
                            const productContent = variantsHTML.find(`.cardview-variant-item[data-product-code="${productCode}"]`);
                            if (productContent.length) {
                                $(`#cardview-variants-${productCode}`).append(productContent);
                            }
                            const productListContent = variantsHTML.find(`.cardview-variant-item-list[data-product-code="${productCode}"]`);
                            if (productListContent.length) {
                                $(`#cardview-variants-list-${productCode}`).append(productListContent);
                            }
                    });
                    loadVariantsBatch(startIndex + batchSize);
                },
                error: function (jqXHR, textStatus, errorThrown) {
					console.log('Error: ', textStatus, errorThrown);
                    console.error('Failed to load initial variants for Product ID:', Object.keys(payload));
                    loadVariantsBatch(startIndex + batchSize);
                        if (startIndex + batchSize >= totalProducts) {
                            loader.stop();
                     }
                }
            });
        }
      }
    },
     cardvariantdecreaseQuantity: function(button) {
        var input = $(button).siblings(".js-variant-qty");
        var qty = parseInt(input.val(), 10) || 1;
        if(qty > 1) {
            input.val(qty - 1).trigger("input");
        }
        input.blur();
        input.focus();
        ACC.productcardvariant.cardvariantvalidateErrorCase(button);
    },
    cardvariantisNumberKey: function(evt) {
        var charCode = evt.which ? evt.which : evt.keyCode;
        if(charCode < 48 || charCode > 57) {
            return false;
        }
        return true;
    },
    cardvariantupdateQty: function(input) {
        input.value = input.value.replace(/\D/g, "");
        if(input.value === '0') {
            input.value = "1";
        }
        if(input.value !== "" && parseInt(input.value, 10) > 99999) {
            input.value = "99999";
        }        
        var form = $(input).closest(".card-variant-item").find(".add_to_cart_form");
        form.find(".js-qty-selector-input").val(input.value || '1');
        ACC.productcardvariant.cardvariantvalidateErrorCase(input);
    },
    cardvariantsetDefaultValue: function(input) {
        if(input.value.trim() === "") {
            input.value = "1";
        }
        var form = $(input).closest(".card-variant-item").find(".add_to_cart_form");
        form.find(".js-qty-selector-input").val(input.value);
        ACC.productcardvariant.cardvariantvalidateErrorCase(input);
    },
    cardvariantincreaseQuantity: function(button) {
        var input = $(button).siblings(".js-variant-qty");
        var qty = parseInt(input.val(), 10) || 1;
        if(qty < 99999) {
            input.val(qty + 1).trigger("input");
        }
        input.blur();
        input.focus();
        ACC.productcardvariant.cardvariantvalidateErrorCase(button);
    },
    cardvarianthandleVariantAddToCart: function(e, button) {
        ACC.product.bindToAddToCartForm();
        var variantItem = $(button).closest('.card-variant-item');
        var variantItemCategory=$(button).closest('.product-item-box').find('#ga4-categoryLeve1').val();
        if (variantItem.find('.stock-section-instock').length) {
            if ((Number(variantItem.find(".js-variant-qty").val().trim()) <= variantItem.find('.stock-section-instock').data("available-qty"))) {
                variantItem.find(".plp-commonerror-section").addClass("hidden")
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                $(button).closest('form').submit();
                variantItem.find(".js-variant-qty").val("1");
            } else {
                variantItem.find(".plp-commonerror-section").removeClass("hidden");
                e.preventDefault();
            }
        }else if(variantItem.find('.stock-section-instock-nearby').length) {
			if ((Number(variantItem.find(".js-variant-qty").val().trim()) <= variantItem.find('.stock-section-instock-nearby').data("available-qty"))) {
                variantItem.find(".plp-commonerror-section").addClass("hidden");
	            variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
	            $(button).closest('form').submit();
	            variantItem.find(".js-variant-qty").val("1");
            } else {
                variantItem.find(".plp-commonerror-section").removeClass("hidden");
                e.preventDefault();
            }
        }else if(variantItem.find('.stock-section-backorder').length && variantItemCategory=='Nursery') {
             $(button).closest('form').submit();
             variantItem.find(".js-variant-qty").val("1");
        } else {
            /* variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden"); */
            e.preventDefault();
        }
    },
    cardvariantvalidateErrorCase: function(element) {
        var variantItem = $(element).closest('.card-variant-item');
        let getAQuoteFlag = variantItem.find(".getAQuoteFlagForB2BUser").val();
        if (variantItem.find('.stock-section-instock').length) {
            if (Number(variantItem.find(".js-variant-qty").val().trim()) !== "" && Number(variantItem.find(".js-variant-qty").val().trim())  <= variantItem.find('.stock-section-instock').data("available-qty")) {
                variantItem.find(".plp-commonerror-section").addClass("hidden")
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                variantItem.find('.variant-login-to-buy_atc').removeAttr("disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").addClass("hidden");
                    variantItem.find(".cardvariant-atc-wrapper-section").removeClass("hidden");
                }
            } else {
                variantItem.find(".plp-commonerror-section").removeClass("hidden");
                variantItem.find(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.enterValidQuantity);
                variantItem.find('.variant-login-to-buy_atc').attr("disabled", "disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").removeClass("hidden");
                    variantItem.find(".cardvariant-atc-wrapper-section").addClass("hidden");
                    variantItem.find(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.adjustQuantityToAddtoCart);
                }
            }
        } else if(variantItem.find('.stock-section-instock-nearby').length) {
			 if (Number(variantItem.find(".js-variant-qty").val().trim()) !== "" && Number(variantItem.find(".js-variant-qty").val().trim())  <= variantItem.find('.stock-section-instock-nearby').data("available-qty")) {
                variantItem.find(".plp-commonerror-section").addClass("hidden")
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                variantItem.find('.variant-login-to-buy_atc').removeAttr("disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").addClass("hidden");
                    variantItem.find(".cardvariant-atc-wrapper-section").removeClass("hidden");
                }
            } else {
                variantItem.find(".plp-commonerror-section").removeClass("hidden");
                variantItem.find(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.enterValidQuantity);
                variantItem.find('.variant-login-to-buy_atc').attr("disabled", "disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").removeClass("hidden");
                    variantItem.find(".cardvariant-atc-wrapper-section").addClass("hidden");
                    variantItem.find(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.adjustQuantityToAddtoCart);
                }
            }
        }  /* else {
            variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
        } */
    }
};