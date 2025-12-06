ACC.productVariant = {
    _autoload: [
        "bindVariantProducts"
    ],

    bindVariantProducts: function() {
        if ($(".plpviewtype").val() === "list") {
            const hasVariantProducts = $('.product-item[data-variantcount!=""]').length > 0;
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
                    if ($(this).data('variantcount') !== "" && $(this).data('variantcount') > 1) {
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
                    data: JSON.stringify({ viewType: "list", "count": 2, "variantSkus": payload }),//{ "count": 2, "variantSkus": requestData },
                    success: function (response) {
                        /* console.log("**************** START");
                        console.log(response.variants);
                        console.log("**************** END"); */
                        const variantsHTML = $("<div>").html($("<div>").html(response.variants).text());
                        Object.keys(payload).forEach(function (productCode) {
                            $.each(payload[productCode], function (index, variantCode) {
                                const variantContent = variantsHTML.find(`.variant-item[data-variant-code="${variantCode}"]`);
                                if (variantContent.length) {
                                    $(`#variants-${productCode}`).append(variantContent);
                                }
                            });
                        });
                        loadVariantsBatch(startIndex + batchSize);
                    },
                    error: function () {
                        console.error('Failed to load initial variants for Product ID:', Object.keys(payload));
                        console.error('Failed to load initial variants for Products in batch starting at index:', startIndex);
                        loadVariantsBatch(startIndex + batchSize);
                        if (startIndex + batchSize >= totalProducts) {
                            loader.stop();
                        }
                    }
                });
            }

            $(document).on("click", '.toggle-variants', function () {
                const button = $(this);
                const productCode = button.data('product-id');
                const variantCount = button.data('variant-count');
                const variantCodes = button.data('variant-codes');
                const variantCodesInArrOfStr = variantCodes.join(',').split(',');
                const isLoaded = button.data('loaded');

                const displayedVariantCodes = $(`#variants-${productCode} .variant-item`).map(function () {
                    return $(this).data('variant-code').toString();
                }).get();

                const remainingVariantCodes = variantCodesInArrOfStr.filter(code => !displayedVariantCodes.includes(code));

                if (!isLoaded && remainingVariantCodes.length > 0) {
                    let variantsToFetch;
                    if (displayedVariantCodes.length === 2) {
                        variantsToFetch = remainingVariantCodes.length > 8 ? remainingVariantCodes.slice(0, 8) : remainingVariantCodes;
                    } else {
                        variantsToFetch = remainingVariantCodes;
                    }
                    /* const data = {[productCode]: remainingVariantCodes};
                    const requestData = Object.entries(data).map(([key, values]) => `${key}~${values.join(",")}`).join("|"); */
                    const requestPayload = {};
                    requestPayload['viewType'] = "list";
                    requestPayload['count'] = variantsToFetch.length;
                    requestPayload['variantSkus'] = {};
                    requestPayload['variantSkus'][productCode] = remainingVariantCodes;
                    loading.start();
                    $.ajax({
                        url: ACC.config.encodedContextPath + '/c/getVariantProducts',
                        type: 'POST',
                        cache: false,
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        data: JSON.stringify(requestPayload), //{"count": variantsToFetch.length, "variantSkus": requestData },
                        success: function (response) {
                            /* console.log("**************** Button Click START");
                            console.log(response.variants);
                            console.log("**************** Button Click END"); */
                            var decoded = $('<div>').html(response.variants).text();
                            $(`#variants-${productCode}`).append(decoded);
                            /* console.log(displayedVariantCodes.length + variantsToFetch.length);
                            console.log(variantCount); */
                            if (displayedVariantCodes.length + variantsToFetch.length >= variantCount) {
                                button.find('.option-label').text('Hide Extra');
                                button.find('.toggle-down, .toggle-up').toggle();
                                button.data('loaded', true);
                            } else {
                                button.find('.option-label').text('Show All (' + variantCount + ')');
                                button.data('loaded', false);
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log('Error: ', textStatus, errorThrown);
                            /* console.log('Response text: ', jqXHR.responseText); */
                            console.error(`Failed to load variants for Product ID: ${productCode}`);
                        }
                    }).always(function () {
                        loading.stop();
                    });
                } else {
                    const isHidden = $(`#variants-${productCode}`).find('.variant-item:gt(1)').is(':visible');
                    /* console.log(isHidden); */
                    if (isHidden) {
                        $(`#variants-${productCode}`).find('.variant-item:gt(1)').hide();
                        button.find('.option-label').text('Show All (' + variantCount + ')');
                    } else {
                        $(`#variants-${productCode}`).find('.variant-item:gt(1)').show();
                        button.find('.option-label').text('Hide Extra');
                    }
                    button.find('.toggle-up, .toggle-down').toggle();
                    $(window).scrollTop($(`#variants-${productCode}`).offset().top-200);
                }
            });
        }
    },
    handleVariantAddToCart: function(e, button) {
        ACC.product.bindToAddToCartForm();
        var variantItem = $(button).closest('.variant-item');
        var variantItemCategory=$(button).closest('.product-item-box').find('#ga4-categoryLeve1').val();
        if (variantItem.find('.stock-section-instock').length) {
            if ((variantItem.find(".js-variant-qty").val().trim() <= variantItem.find('.stock-section-instock').data("available-qty"))) {
                variantItem.parent().siblings(".plp-commonerror-section").addClass("hidden");
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                $(button).closest('form').submit();
                variantItem.find(".js-variant-qty").val("1");
            } else {
                variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
                e.preventDefault();
            }
        } else if(variantItem.find('.stock-section-instock-nearby').length) {
             if ((variantItem.find(".js-variant-qty").val().trim() <= variantItem.find('.stock-section-instock-nearby').data("available-qty"))) {
                variantItem.parent().siblings(".plp-commonerror-section").addClass("hidden");
	            variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
	            $(button).closest('form').submit();
	            variantItem.find(".js-variant-qty").val("1");
            } else {
                variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
                e.preventDefault();
            }
        } else if(variantItem.find('.stock-section-backorder').length && variantItemCategory=='Nursery') {
              $(button).closest('form').submit();
               variantItem.find(".js-variant-qty").val("1");
        } 
        else {
            /* variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden"); */
            e.preventDefault();
        }
    },
    validateErrorCase: function(element) {
        var variantItem = $(element).closest('.variant-item');
        let getAQuoteFlag = variantItem.find(".getAQuoteFlagForB2BUser").val();
        if (variantItem.find('.stock-section-instock').length) {
            if (variantItem.find(".js-variant-qty").val().trim() !== "" && variantItem.find(".js-variant-qty").val().trim() <= variantItem.find('.stock-section-instock').data("available-qty")) {
                variantItem.parent().siblings(".plp-commonerror-section").addClass("hidden");
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                variantItem.find('.variant-login-to-buy_atc').removeAttr("disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").addClass("hidden");
                    variantItem.find(".variant-atc-wrapper-section").removeClass("hidden");
                }
            } else {
                variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
                variantItem.parent().siblings(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.enterValidQuantity);
                variantItem.find('.variant-login-to-buy_atc').attr("disabled", "disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").removeClass("hidden");
                    variantItem.find(".variant-atc-wrapper-section").addClass("hidden");
                    variantItem.parent().siblings(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.adjustQuantityToAddtoCart);
                }
            }
        } else if(variantItem.find('.stock-section-instock-nearby').length) {
           if (variantItem.find(".js-variant-qty").val().trim() !== "" && variantItem.find(".js-variant-qty").val().trim() <= variantItem.find('.stock-section-instock-nearby').data("available-qty")) {
                variantItem.parent().siblings(".plp-commonerror-section").addClass("hidden");
                variantItem.find('.qty-hidden-variant').val(variantItem.find(".js-variant-qty").val().trim());
                variantItem.find('.variant-login-to-buy_atc').removeAttr("disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").addClass("hidden");
                    variantItem.find(".variant-atc-wrapper-section").removeClass("hidden");
                }
            } else {
                variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
                variantItem.parent().siblings(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.enterValidQuantity);
                variantItem.find('.variant-login-to-buy_atc').attr("disabled", "disabled");
                if(getAQuoteFlag == "true") {
                    variantItem.find(".getAQuoteSection").removeClass("hidden");
                    variantItem.find(".variant-atc-wrapper-section").addClass("hidden");
                    variantItem.parent().siblings(".plp-commonerror-section").find(".plp-commonerror").text(ACC.config.adjustQuantityToAddtoCart);
                }
            }
        } /* else {
            variantItem.parent().siblings(".plp-commonerror-section").removeClass("hidden");
        } */
    },
    decreaseQuantity: function(button) {
        var input = $(button).siblings(".js-variant-qty");
        var qty = parseInt(input.val(), 10) || 1;
        if(qty > 1) {
            input.val(qty - 1).trigger("input");
        }
        input.blur();
        input.focus();
        ACC.productVariant.validateErrorCase(button);
    },
    increaseQuantity: function(button) {
        var input = $(button).siblings(".js-variant-qty");
        var qty = parseInt(input.val(), 10) || 1;
        if(qty < 99999) {
            input.val(qty + 1).trigger("input");
        }
        input.blur();
        input.focus();
        ACC.productVariant.validateErrorCase(button);
    },
    isNumberKey: function(evt) {
        var charCode = evt.which ? evt.which : evt.keyCode;
        if(charCode < 48 || charCode > 57) {
            return false;
        }
        return true;
    },
    updateQty: function(input) {
        input.value = input.value.replace(/\D/g, "");
        if(input.value === '0') {
            input.value = "1";
        }
        if(input.value !== "" && parseInt(input.value, 10) > 99999) {
            input.value = "99999";
        }        
        var form = $(input).closest(".variant-item").find(".add_to_cart_form");
        form.find(".js-qty-selector-input").val(input.value || '1');
        ACC.productVariant.validateErrorCase(input);
    },
    setDefaultValue: function(input) {
        if(input.value.trim() === "") {
            input.value = "1";
        }
        var form = $(input).closest(".variant-item").find(".add_to_cart_form");
        form.find(".js-qty-selector-input").val(input.value);
        ACC.productVariant.validateErrorCase(input);
    }
};