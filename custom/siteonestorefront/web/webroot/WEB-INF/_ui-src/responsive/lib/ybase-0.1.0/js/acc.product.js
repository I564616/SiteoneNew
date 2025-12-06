ACC.product = {

  _autoload: [
    "bindToAddToCartForm",
    "enableStorePickupButton",
    "enableVariantSelectors",
    "bindFacets",
    "bindRetailPrice",
    "bindCustomerSpecificPrice",
    "bindProductCompare",
    "bindCompareSpan",
    "imageResize",
    "searchSuggestion",
    "orderOnlinePopup",
    "getProductCodeName",
    "disableRightclick",
    "checkProductImagePdp",
    "getsortValue",
    "pdpPlusMinusQty",
    "showMoreAndLess",
    "productVariant",
    "checkAvlQuantity",
    "checkMinQuantity",
    "checkMinQuantityPLP",
    "checkMaxQuantityonhandPLP",
    "CheckMinintervalPLP",
    "bindRelatedArticle",
    "checkPdpStoreDetail",
    "curatedplp",
    "cplpredirectUrl",
    "constructCompareProducts",
    "bindSelectedProducts",
    "bindSelectedProductslistview",
    ["bindCompareRemoveDisable", $('.remove_compare_product').length >= 3]
    /* ,"handleLeftFacetSectionHeight" */
  ],
  selectedProductsToList: [],

  showMoreAndLess: function (thisElement, state, element, contentMore, contentLess, numberOfitems) {
    switch (state) {
      case "expand": {
        if(element){ 
        element.slice(numberOfitems).removeClass("hidden");
        }
		
		if($(thisElement).hasClass("pdp-variant-link")){
			$(thisElement).text(contentLess+' '+ACC.config.variantItems);
		}
		else if(!$(thisElement).hasClass("add-cart_selected_filter")){
        	$(thisElement).text(contentLess);
		} 
        if ($(thisElement).hasClass("show-tracking-details")) {
          $(thisElement).prepend("<span class='icon-minus-circle'></span>&nbsp;");
        }
        $(thisElement).data('state', 'collapse');
        $(window).scrollTop(window.scrollY-10);
        windowimageScroll();
        if($(element).parent().hasClass("js-facet-list")) {
          $(thisElement).remove();
      }
        break;
      }
      case "collapse": {
      	if(element){ 
        element.slice(numberOfitems).addClass("hidden");
        }
		if($(thisElement).hasClass("pdp-variant-link")){
			$(thisElement).text(contentMore+' '+$(thisElement).data("remaining")+' '+ACC.config.variantMoreitems);
		}
		else if(!$(thisElement).hasClass("add-cart_selected_filter")){
        	$(thisElement).text(contentMore);
		}
        if ($(thisElement).hasClass("show-tracking-details")) {
          $(thisElement).prepend("<span class='icon-plus-circle'></span>&nbsp;");
        }
        $(thisElement).data('state', 'expand');
        windowimageScroll();
        break;
      }
      case undefined: break;
    }
  },
  validateShippingQty: function(cardelement){
    if($(".pagename").val()=='Product Grid' || $(".pagename").val()== 'Search Results'){ //search handled
    let enteredQty = $(cardelement).val();
    let maxShipQty =$(cardelement).data("maxqty");
    let productCode = $(cardelement).data("productcode");
    let isShippingOnlyPLP = $(`#shipping_${productCode}`).data("shippingonly");
    if(isShippingOnlyPLP==="shippingonly" && enteredQty>maxShipQty){
        $(`#plp-commonshippingonlyqtyerror-${productCode}`).removeClass('hidden');
        $(`.js-atc-${productCode}`).prop("disabled", true).addClass("disabled");
        // console.log("qty exceeds");
    }else{
        $(`#plp-commonshippingonlyqtyerror-${productCode}`).addClass('hidden');
        $(`.js-atc-${productCode}`).prop("disabled", false).removeClass("disabled");
        //  console.log("qty ok");
    }
    }
},
  bindSelectedProducts: function () {
    $(document).on("change", ".custom-checkbox", function(e) {
      if (($(".plpviewtype").val() == "card")) {
        let checkboxLabel = $(this).siblings(".checkbox-label");
        let productItem = $(this).closest('.plp-card');
        if($(this).is(":checked")) {
          checkboxLabel.text("Selected");
          productItem.addClass("selected");
        } else {
          checkboxLabel.text("Select");
          productItem.removeClass("selected");
        }
        const checkedCount = $(".custom-checkbox:checked").length;
         $(".add-multiple-to-cart-btn").text(`Add (${checkedCount}) to Cart`);
         $(".add-multiple-to-list-btn").text(`Add (${checkedCount}) to List`);
        if(checkedCount > 0) {
          $(".add-multiple-to-cart-btn").removeAttr("disabled");
          $(".add-multiple-to-list-btn").removeAttr("disabled");
        } else {
          $(".add-multiple-to-cart-btn").attr("disabled", "disabled");
          $(".add-multiple-to-list-btn").attr("disabled", "disabled");
        }
      }
      ACC.product.restrictProductsToCart();
    });
    $(".add-multiple-to-cart-btn").click(function() {
      const selectedProducts = [];
      $(".custom-checkbox:checked").each(function() {
        let productCode = $(this).closest(".product-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.product-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".product-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if (selectedProducts.indexOf(`${productCode}|${quantity}|${uomId}`) == -1) {
          selectedProducts.push(`${productCode}|${quantity}|${uomId}`);
        }
      });
      $(".custom-checkboxplp:checked").each(function() {
        let productCode = $(this).closest(".product-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.product-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".product-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if (selectedProducts.indexOf(`${productCode}|${quantity}|${uomId}`) == -1) {
          selectedProducts.push(`${productCode}|${quantity}|${uomId}`);
        }
      });
      $(".custom-checkboxplpvariant:checked").each(function() {
        let productCode = $(this).closest(".variant-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.variant-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".variant-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if (selectedProducts.indexOf(`${productCode}|${quantity}|${uomId}`) == -1) {
          selectedProducts.push(`${productCode}|${quantity}|${uomId}`);
        }
      });
      if(selectedProducts.length && selectedProducts.length > 0) {
        ACC.product.addSelectedProductsToCart(selectedProducts.join(" "));
      }
    });
    $(".add-multiple-to-list-btn").click(function() {
	  ACC.product.selectedProductsToList = [];
      $(".custom-checkbox:checked").each(function() {
        let productItemNumber = $(this).closest(".product-item").find('.plp-item-number[value!=""]').first().val();
        let productCode = $(this).closest(".product-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.product-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".product-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if(ACC.product.selectedProductsToList.indexOf(`${productItemNumber}|${quantity}|${uomId}`) == -1) {
          ACC.product.selectedProductsToList.push(`${productItemNumber}|${quantity}|${uomId}`);
        }
      });
      $(".custom-checkboxplp:checked").each(function() {
        let productItemNumber = $(this).closest(".product-item").find('.plp-item-number[value!=""]').first().val();
        let productCode = $(this).closest(".product-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.product-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".product-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if(ACC.product.selectedProductsToList.indexOf(`${productItemNumber}|${quantity}|${uomId}`) == -1) {
          ACC.product.selectedProductsToList.push(`${productItemNumber}|${quantity}|${uomId}`);
        }
      });
      $(".custom-checkboxplpvariant:checked").each(function() {
        let productItemNumber = $(this).closest(".variant-item").find('.plp-item-number[value!=""]').first().val();
        let productCode = $(this).closest(".variant-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($(this).closest('.variant-item').hasClass("card-variant-item")) {
          quantity = Number($(this).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $(this).closest(".variant-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        if(ACC.product.selectedProductsToList.indexOf(`${productItemNumber}|${quantity}|${uomId}`) == -1) {
          ACC.product.selectedProductsToList.push(`${productItemNumber}|${quantity}|${uomId}`);
        }
      });
    });
  },
  bindSelectedProductslistview: function (){
    $(document).on("change", ".custom-checkboxlistplp", function(e) {
     
    if (($(".plpviewtype").val() == "list")) {
      if($(this).is(":checked")) {
        $(this).closest('.product-list-view').addClass("selected");
      } else {
        $(this).closest('.product-list-view').removeClass("selected");
      }
      const checkedCount = $(".custom-checkboxlistplp:checked").length;
       $(".add-multiple-to-cart-btn").text(`Add (${checkedCount}) to Cart`);
       $(".add-multiple-to-list-btn").text(`Add (${checkedCount}) to List`);
      if(checkedCount > 0) {
        $(".add-multiple-to-cart-btn").removeAttr("disabled");
        $(".add-multiple-to-list-btn").removeAttr("disabled");
      } else {
        $(".add-multiple-to-cart-btn").attr("disabled", "disabled");
        $(".add-multiple-to-list-btn").attr("disabled", "disabled");
      }
    }
    ACC.product.restrictProductsToCart();
  });
  
  },
  addSelectedProductsToCart: function (prodCodes) {
    loading.start();
    $.ajax({
      type: 'POST',
      url: ACC.config.encodedContextPath + "/savedList/addSelectedItemsToCart",
      dataType: "json",
      data: {
        wishListCode: '',
        productCodes: prodCodes,
      },
      success: function (result) {
        //console.log('---->', result);
        if(result) {
          window.location.href = ACC.config.encodedContextPath +"/cart";
        } else {
          loading.stop();
          window.location.reload(true);
        }
      },
      error: function (jqXHR, textStatus, errorThrown) {
        loading.stop();
        //console.log('Error: ', textStatus, errorThrown);
      }
    });
  },
  addSelectedProductsToList: function (wishlistCode) {
	  const productList = ACC.product.selectedProductsToList.join(" ");
	  let params = {
		  		wishListCode: wishlistCode,
				productItemNumbers: productList,
				currentWishlist: '',
			};
			let apiname = "/savedList/addSelectedToSavedWishlist";
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + apiname,
				datatype: "json",
				data: params,
				success: function (result) {
					ACC.colorbox.open("", {
						html: ACC.productDetail.pdppopupHtmlExistingList(ACC.config.listaddedMsg, wishlistCode),
						width: "500px",
						onComplete: function () {
							$('#colorbox').addClass('item-add-list-box');
							$('body').css("overflow-y", "hidden");
							$(document).on("click", ".successListCreation", function () {
								ACC.colorbox.close();
							});
						},
						onClosed: function () {
							$('body').css("overflow-y", "auto");
							$('#colorbox').removeClass('item-add-list-box');
						}
					})
				},
				error: function (xhr, ajaxOptions, thrownError) {
					//console.log('data failure');
				}
			})
  },
  restrictProductsToCart: function () {
		var notAvailable,notAvailable1,callBranchForPrice,changeBranch,changeBranch1,commonATCRestrict,minProductCount = false;
      	$(".custom-checkbox:checked").each(function() {
			  minProductCount = true;
			  notAvailable = $(this).closest(".product-item").find('.stock-section-notavailable-banner').is(":visible");
			  notAvailable1 = $(this).closest(".product-item").find('.stock-section-notavailableonline-listview').is(":visible");
			  callBranchForPrice = $(this).closest(".product-item").find('.callBranchForPrice').is(":visible");
			  changeBranch  = $(this).closest(".product-item").find('.change-contact-branch-section').is(":visible");
			  changeBranch1  = $(this).closest(".product-item").find('.changeBranchSection').is(":visible");
			  if(notAvailable || callBranchForPrice || changeBranch || changeBranch1 || notAvailable1){
				  commonATCRestrict = true;
			  }
		});
		$(".custom-checkboxplp:checked").each(function() {
			  minProductCount = true;
			  notAvailable = $(this).closest(".product-item").find('.stock-section-notavailable-banner').is(":visible");
			  notAvailable1 = $(this).closest(".product-item").find('.stock-section-notavailableonline-listview').is(":visible");
			  callBranchForPrice = $(this).closest(".product-item").find('.callBranchForPrice').is(":visible");
			  changeBranch  = $(this).closest(".product-item").find('.change-contact-branch-section').is(":visible");
			  changeBranch1  = $(this).closest(".product-item").find('.changeBranchSection').is(":visible");
			  if(notAvailable || callBranchForPrice || changeBranch || changeBranch1 || notAvailable1){
				  commonATCRestrict = true;
			  }
		});
		$(".custom-checkboxplpvariant:checked").each(function() {
			  minProductCount = true;
			  notAvailable = $(this).closest(".variant-item").find('.stock-section-notavailable-banner').is(":visible");
			  notAvailable1 = $(this).closest(".variant-item").find('.stock-section-notavailableonline-listview').is(":visible");
			  callBranchForPrice = $(this).closest(".variant-item").find('.callBranchForPrice').is(":visible");
			  changeBranch  = $(this).closest(".variant-item").find('.change-contact-branch-section').is(":visible");
			  changeBranch1  = $(this).closest(".variant-item").find('.changeBranchSection').is(":visible");
			  if(notAvailable || callBranchForPrice || changeBranch || changeBranch1 || notAvailable1){
				  commonATCRestrict = true;
			  }
    	});
    	if(commonATCRestrict){
			 $(".add-multiple-to-cart-btn").attr("disabled", "disabled");
			 $('.common-atc-tooltipmsg').removeClass('remove');
		}else{
			if(minProductCount){
				$(".add-multiple-to-cart-btn").removeAttr("disabled");
			}
			$('.common-atc-tooltipmsg').addClass('remove');
		}
  },
  plpMobileAddListFunction: function (wishlistCode,id) {
        let productItemNumber = $('.plpAddToListMobile-' + id).closest(".product-item").find('.plp-item-number[value!=""]').first().val();
        let productCode = $('.plpAddToListMobile-' + id).closest(".product-item").find('.plp-item-code[value!=""]').first().val();
        let quantity = Number($('#productPLPPostQty_' + productCode).val() || 1);
        if($('.plpAddToListMobile-' + id).closest('.product-item').hasClass("card-variant-item")) {
          quantity = Number($('.plpAddToListMobile-' + id).closest('.card-variant-item').find('.js-variant-qty').val() || 1);
        }
        let uomId = $('.plpAddToListMobile-' + id).closest(".product-item").find('.productInventoryUOMID_' + productCode + '[value!=""]').first().val() || '';
        const productData = `${productItemNumber}|${quantity}|${uomId}`;
	  let params = {
		  		wishListCode: wishlistCode,
				productItemNumbers: productData,
				currentWishlist: '',
			};
			let apiname = "/savedList/addSelectedToSavedWishlist";
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + apiname,
				datatype: "json",
				data: params,
				success: function (result) {
					ACC.colorbox.open("", {
						html: ACC.productDetail.pdppopupHtmlExistingList(ACC.config.listaddedMsg, wishlistCode),
						width: "500px",
						onComplete: function () {
							$('#colorbox').addClass('item-add-list-box');
							$('body').css("overflow-y", "hidden");
							$(document).on("click", ".successListCreation", function () {
								ACC.colorbox.close();
							});
						},
						onClosed: function () {
							$('body').css("overflow-y", "auto");
							$('#colorbox').removeClass('item-add-list-box');
						}
					})
				},
				error: function (xhr, ajaxOptions, thrownError) {
					//console.log('data failure');
				}
			})
  },
  bindFacets: function () {
	
	$(document).on("keyup", '.js-plp-qty', function (e) {
		/*let ref = $(this);
		var plpQty=ref.val();
		var isNurseryProduct = ref.data("nurseryproduct");
	 	var nearestStoreStockLevel = ref.data("maxqty");
		if(isNurseryProduct == true && plpQty > nearestStoreStockLevel){
			ref.parents(".product-item-box").find(".qtyerror-plp").removeClass("hidden");
			ref.parents(".product-item-box").find(".btn-primary").attr("disabled", true);
			ref.parents(".product-item-box").find(".icon-red-exclamation").removeAttr("style");
			ref.parents(".product-item-box").find(".plp-qtysection").addClass("plp-qtyBoxError");
	}
	else{
		ref.parents(".product-item-box").find(".qtyerror-plp").addClass("hidden");
		ref.parents(".product-item-box").find(".btn-primary").attr("disabled", false);
		ref.parents(".product-item-box").find(".plp-qtysection").removeClass("plp-qtyBoxError");
	}*/
 	if($(".inventoryCheck_Atc").val() == 'true' && $(".isEligibleForBackorder_Atc").val() == 'true' && $(".item-isForceInStock").val() != "true") {
   		$(".inventoryCheck-and-isEligibleForBackorder").attr("disabled","disabled");
  	}
 });
    $(document).ready(function () {
	horizontalScroll();
      $(window).on("resize", function (e) {
        if(!ACC.product.expandableFacetsFlag){
          expandableFacetsOnMobile();
        }
      });


      $(document).on("click", ".hideShowMoreLink", function(a){
        a.preventDefault();
        var state = $(this).data('state');
        var element = $(this).parent().find('ul.js-facet-list li');
        ACC.product.showMoreAndLess(this, state, element, ACC.config.showAll, ACC.config.showLess, 10);
      });

      expandableFacetsOnMobile();

      function expandableFacetsOnMobile() {
        var newWindowWidth = $(window).width();
          ACC.product.expandableFacetsFlag = true;
          $(document).on("click", ".js-show-facets", function (e) {
            e.preventDefault();
            $('.js-product-facet:not(.category-tiles)').show();
            $('.js-product-facet:not(.category-tiles)').addClass("facet-overflow");
            $('.js-product-facet:not(.category-tiles)').css("position", "fixed");
            $('.js-product-facet:not(.category-tiles)').css("overflow-y", "scroll");
            $('body').addClass("body-overflow");
          });

          $(document).on("click", ".js-product-facet:not(.category-tiles) .js-facet-name", function (e) {
            e.preventDefault();
            if ($(this).parents(".js-facet").hasClass("active")) {
              $(this).parents(".js-facet").removeClass("active");
              $(this).parents(".js-facet").find(".js-facet-values").slideUp();
            } else {
              $(this).parents(".js-facet").addClass("active");
              $(this).parents(".js-facet").find(".js-facet-values").attr("style", "height:max-content");
              $(this).parents(".js-facet").find(".js-facet-values").slideDown();
            }
          });
          $('.refinement-titleWrapper .glyphicon-leanChevron-left').on('click', function () {
            $('.js-product-facet:not(.category-tiles)').removeClass("facet-overflow");
            $('body').removeClass("body-overflow");
            $('.js-product-facet:not(.category-tiles)').hide("slide", { direction: "left" });
          });
          if (newWindowWidth < 481) {
          enquire.register("screen and (min-width:" + screenSmMax + ")", function () {
            $("#cboxClose").trigger('click');
          });
          }
      }
    });

    $(window).on('load', function () {
    });
  },

  expandableFacetsFlag: false,
  //resize images which are not 300 x 300 in PLP
  imageResize: function () {

    $(window).on('load', function () {
      $('.product-item-box img:not(.inventory-message-icon)').each(function () {
        if ($(this).width() != $(this).height()) {
          $(this).css('height', $(this).width());
          $(this).css('width', '100%');
        }
      });
    });
  },

  searchSuggestion: function () {
    var searchText = $("#search").val();
    var suggestion = $("#suggestion").val();
    if (searchText != suggestion) {
      $(".searchSpellingSuggestionPrompt").show();
    }
  },
  bindProductCompare: function () {
    var productCodes = "";
    var categoryCode = $("#categoryCode").val();
    function constructCompareProducts(elem,productCode,productImage){

        if ($(elem).is(':checked')) {
        $(".compareBox .product__compare__checkbox").append("<div class='col-md-4 p" + productCode + "'><div class='img-sec'><div class='delete_product'style='position: absolute; right:13px; top:2px; cursor:pointer;' data-role=" + productCode + ">X</div><img src=" + productImage + "></div></div>");
        productCodes = productCodes.concat(productCode);
        productCodes = productCodes.concat(",");
        if ($(".product__compare__checkbox:checked").length > 1) {
            $(".compareBox").css("display", "block");
          } else {

            $(".compareBox").css("display", "none");
          }
        if ($(".product__compare__checkbox:checked").length >= 3) {
          $(".product__compare__checkbox").not(":checked").attr("disabled", true);

        }

      }
      else {

        if (null != productCode) {
          var selector = ".p" + productCode;
          $(selector).remove();
        }

        var x = productCode + ",";
        productCodes = productCodes.replace(x, "");
        if ($(".product__compare__checkbox:checked").length < 3) {
          $(".product__compare__checkbox").not(":checked").attr("disabled", false);
        }
      }
        
        return productCodes
        
    }
    
    $(".product__compare__checkbox:checked").each(function () {
        var selectedId = $(this).attr('data-role');
        var selectedProductCode = $('#prodCode' + selectedId).val();
        var selectedProductImage = $('#prodImage' + selectedId).val();
        productCodes=constructCompareProducts(this,selectedProductCode,selectedProductImage)
      })
      
      $(document).on("click", ".product__compare__checkbox", function (e) {
          var id = $(this).attr('data-role');
          var productCode = $('#prodCode' + id).val();
          var productImage = $('#prodImage' + id).val();
          if ($(".product__compare__checkbox:checked").length > 1) {
            $(".compareBox").css("display", "block");
          } else {

            $(".compareBox").css("display", "none");
          }
          productCodes=constructCompareProducts(this,productCode,productImage)

        });
        $(document).on("click", ".delete_product", function (e) {
          var productCode = $(this).parent("div").attr("class").split('p')[1];          
          $("input[value=" + productCode + "][name=code]").parent("span").children().closest(".product__compare__checkbox").trigger('click');         

        });
        
        $(document).on("click", "#removeAllCompare", function (e) {
            $(".product__compare__checkbox:checked").trigger('click');

        });

    $(document).on("click", "#btn_Compare", function (e) {
    	
    	if(digitalData.pfm)
    	{
    	  delete digitalData.pfm;
    	  delete digitalData.pfmdetails;
    	}
    	
      if($('body').hasClass("template-pages-layout-siteOneCuratedPlpLayout")){
        digitalData.eventData = {
          linktype:"product-grid",
          linkName:"Add to Cart",
          onClickPageName: $(".pagename").val()
        }
      }else{
        digitalData.eventData = {
          linktype:"catalog",
          linkName:"Add to Cart",
          onClickPageName: $(".pagename").val()
        }
      }
      try {
        _satellite.track("linkClicks");
      } catch (e) { }
      var url = ACC.config.encodedContextPath + "/compare" + "?productCodes=" + productCodes + "&categoryCode=" + categoryCode
      window.location.href = url;
    });


  },
  bindCompareRemoveDisable: function () {

    $(document).on("click", ".remove_compare_product", function (e) {
      e.preventDefault();
      var productCode = $(this).siblings(".details").children("input").attr("value");
      var productCodes = $("#productCodes").val();
      var categoryCode = $("#compare_category_code").val();
      var products = productCodes.split(",");
      var productCodes = '';
      products.forEach(function (e) {
        if (e != productCode) {

          productCodes = productCodes + e + ",";
        }
      });

      var url = ACC.config.encodedContextPath + "/compare" + "?productCodes=" + productCodes + "&categoryCode=" + categoryCode

      window.location.href = url;

    });
  },
 
  enableAddToCartButton: function () {
    $('.js-enable-btn').each(function () {
      if (!($(this).hasClass('outOfStock') || $(this).hasClass('out-of-stock') || ($(this).hasClass('inventoryCheck-and-isEligibleForBackorder')) && ($(".isForceinstockUOM").val() != 'true')) && $(".shippingOnlyDisabled").val() != "true") { 
        $(this).removeAttr("disabled");
      }
      if ($("body").hasClass("page-detailsAssemblyPage") || $("body").hasClass("page-detailsSavedListPage")) {     
        
        var element = $(this).closest(".saved-list-sec").find(".js-update-entry-quantity-input");
        ACC.product.checkAvlQuantity(element);
        }
	  if ($("body").hasClass("page-order")) {
			if($(this).closest(".item__list--header").find(".js-cart-qty-error").is(":visible")){
				 $(this).attr("disabled","disabled")
			}
		}
	  if ($(".account-section-content").hasClass("my-order")) {
			if($(this).closest(".singleorder-product-section").find(".js-cart-qty-error").is(":visible")){
				 $(this).attr("disabled","disabled")
			}
		}
    });
	$("#pdpAddtoCartInput").each(function () {      
	      var $input =$(this);
	        ACC.product.checkMinQuantity($input,0);
	    });
  },
  enableVariantSelectors: function () {
    $('.variant-select').removeAttr("disabled");
  },

  checkCSP: function () {
    $('.productcspCode').each(function () {
      var productCode = $(this).val();
      if ($('#mycspDiv' + productCode).is(':visible')) {
        var isStockAvailable = ($(".isStockAvailablecsp" + productCode).val() == 'true');
        var isLicensed = ($(".isLicensed" + productCode).val() == 'true');
        var isMyStoreProduct = ($(".isMyStoreProduct" + productCode).val() == "true");
        var isRegulateditem = ($(".isRegulateditemcsp" + productCode).val() == "true");
        var isOrderingAccount = ($(".isOrderingAccount" + productCode).val() == "true");
        var csp = $(".csp" + productCode).val();
        var isRup = ($("#isRup" + productCode).val() == 'true');
        var isProductSellable = ($("#isProductSellable" + productCode).val() == 'true');
        let inventoryCheck = ($(".inventoryCheck_" + productCode).val() == "true");
        let isEligibleForBackorder = ($(".isEligibleForBackorder_" + productCode).val() == "true");
        if (csp > 0 && isOrderingAccount && isMyStoreProduct && ((isRegulateditem && isProductSellable && ((isRup && isLicensed) || !isRup)) || !isRegulateditem) && !(inventoryCheck && isEligibleForBackorder)) {
          var eleId="listPageAddToCart_" + productCode;
		  $('[id='+eleId+']').each(function(){
			$(this).attr("disabled", false);
		 });
		  var eleId1="orderOnlineATC_" + productCode;
		  $('[id='+eleId1+']').each(function(){
			$(this).attr("disabled", false);
		 });
        }
      }
    });
  },

  bindToAddToCartForm: function () {
    
   

    var addToCartForm = $('.add_to_cart_form');
    if ((window.location.href.indexOf('/p/') != -1) && $('.js-qty-selector-input').val() != null && $('.js-qty-selector-input').val() != '' && $('.js-qty-selector-input').val() != 0) {
      $(".js-qty-selector-input").attr("value", $('.js-qty-selector-input').val());
    }

    addToCartForm.ajaxForm({
      beforeSubmit: ACC.product.showRequest,
      success: function (data, statusText, xhr, formElement) {
        var productId = formElement.find("[name=productCodePost]").val();
        var productName = formElement.find("[name=productNamePost]").val();
		  if (data != null && data.cartAnalyticsData != null) {
			  var productCategory = data.cartAnalyticsData.productCategory;
			  var productSubCategory = data.cartAnalyticsData.productSubCategory;
			  cartAddAnalytics(productId, productName, productCategory, productSubCategory, _AAData.eventType);
	      if($('.cart-page-container').is( ":visible" )){
	    	  /***For GA4 Analytics add_to_cart Start***/
	    	  ACC.ga4analytics.add_to_cart(
	    			productId,
	    			productName,
    	     		parseFloat(data.cartAnalyticsData.productPostPrice),
    	     		parseInt(formElement.find("[name=productPostPLPQty]").val()),
    	     		'',
    	     		data.cartAnalyticsData.productCategory,
    	     		data.cartAnalyticsData.productSubCategory,
    	     		'',
    	     	);
          rdt("track", "AddToCart", {
            currency: "USD",
            itemCount: 1,
            value: parseFloat(data.cartAnalyticsData.productPostPrice),
            products: [
              {
                id: productId,
                name: productName,
                category: data.cartAnalyticsData.productCategory
              }
            ]
          });
	    	  /***For GA4 Analytics add_to_cart end***/
          window.location.href = ACC.config.encodedContextPath + '/cart';
        }
        else{
          ACC.product.displayAddToCartPopup(data);
        }     
		  }
		else{
				$.cookie("code",$('#productcode').val());
				$.cookie("qty",$(".add_to_cart_form").find('.js-qty-selector-input').val());
				 window.location.reload();
		} 
		
        loading.stop();
      }
    });
    setTimeout(function () {
      $ajaxCallEvent = true;
    }, 2000);

    

    $(document).on("keypress", '.js-qty-updateOne,.js-qty-selector-input', function (e) {
	  if (($(this).val() != " " && ((e.which >= 48 && e.which <= 57) || e.which == 8 ))) {
      }
      else {
        e.preventDefault();
      }
    });


    $(".js-qty-updateOne").keyup(function (e) {
      if (this.value.substring(0, 1) == "0") {
        this.value = this.value.replace(/^0+/g, '1');
      }
    });
    $(".js-qty-updateOne").keyup(function (e) {
	  var productcode = $(e.target).data('productcode');
      ACC.product.checkMinQuantityPLP(('#productPLPPostQty_' +productcode), '');
      $('#productPostPLPQty_' + productcode).val($('#productPLPPostQty_' + productcode).val());
      //ACC.global.qtyTotalPriceUpdaterPLP(productcode);
      if($(".hardscapeProd_" + productcode).length) {
        ACC.product.handleQtyChangeForHardscapeProducts(productcode);
      } else {
        let qtyTarget = $('#productPLPPostQty_' + productcode);
        if (qtyTarget.length) {
          ACC.product.handleQtyChangeForProducts(qtyTarget, productcode);
        }
      }
    });

	  let prevQty = $.cookie("qty");
	  let prevCode = $.cookie("code");
	  let prevForm = $(".add_to_cart_form");
	  if(prevQty && $.trim(prevQty) != "" && prevCode && $.trim(prevCode) ){
					$.cookie("code", '');
	  $.cookie("qty", '');
	  if (prevForm.length == 1) {
		  prevForm.find('.js-qty-selector-input').val(prevQty);
		  prevForm.find('.js-atc-' + prevCode).eq(0).trigger("click");

	  }
  }
  },
  showRequest: function (arr, $form, options) {
    if ($ajaxCallEvent) {
      loading.start();
      $ajaxCallEvent = false;
      return true;
    }
    return false;

  },

  bindToAddToCartStorePickUpForm: function () {
    var addToCartStorePickUpForm = $('#colorbox #add_to_cart_storepickup_form');
    addToCartStorePickUpForm.ajaxForm({ success: ACC.product.displayAddToCartPopup });
  },

  enableStorePickupButton: function () {
    $('.js-pickup-in-store-button').removeAttr("disabled");
  },

   addToModal: function (cartResult) {
		var popupClass="addtocart-bg add-to-cart-overlay-mixedcart";
		var popupWidth="350px";
		var productPriceNum = 0;
		if(cartResult.isSellable){ //recommendation ATC
			popupClass="addtocart-bg add-to-cart-overlay-mixedcart recommendation-overlay";
			popupWidth="445px";
			productPriceNum = 1;
		}
		else { //standard ATC
			if(!$(".customerSpecificPrice").length){
				productPriceNum = 1;
			}
		}
		
		
    ACC.colorbox.open("", {
      html: cartResult.addToCartLayer,
      width: popupWidth,
      open: true,
      right: "0px",
      className: popupClass,
      onComplete: function () {
        (function () {
            const addedFromPDPBulkCalc = $("#showBulkCalculator").is(":visible")
            if (addedFromPDPBulkCalc) {
              const elements = $(".atc-price-analytics")
              const bulkCalcPrice = $(elements[0]).text();
              $(elements[1]).text(bulkCalcPrice);
            }
          })()

		if($(".atc-backorderMsg").val()=="true"){
			var productBackOrder=$(".atc-productQty").val();
		}
		else{
			var productBackOrder="";
		}
		var productUnitPrice = (!$(".add-to-cart-overlay-mixedcart .atc-price-analytics").length)? "$00.00": ($(".add-to-cart-overlay-mixedcart .atc-price-analytics").length == 1)? $(".add-to-cart-overlay-mixedcart .atc-price-analytics").eq(0).text(): $(".add-to-cart-overlay-mixedcart .atc-price-analytics").eq(productPriceNum).text();
     	digitalData.product = [{
		    productInfo: {
			  productQty:$(".atc-productQty").val(),
			  productID:$(".atc-prodCode").val(),
		      productName: $(".atc-prodName").val(),
			  productCategory:$(".atc-categoryLeve1").val(),
			  productSubCategory:$(".atc-categoryLeve2").val(),
			  productStockStatus:$(".atc-storeStock").val().replace(/<(.|\n)*?>/g, ''),
			  productBackOrder:productBackOrder,
			  addToCartLocation: (ACC.global.productCutatedplp == true ? $(".pagename").val() : (ACC.global.productAnalyticsRecAtc == true ? "recommendation" : _AAData.eventType)),
		 	  productPrice: $.trim(productUnitPrice).replace('$', ""),
		 	  productID:$(".atc-prodCode").val()
		    }
		  }];
     	
     	/***For GA4 Analytics add_to_cart Start***/
     	ACC.ga4analytics.add_to_cart(
     		$(".atc-prodCode").val(),
     		$(".atc-prodName").val(),
     		parseFloat($.trim(productUnitPrice).replace('$', "")),
     		parseInt($(".atc-productQty").val()),
     		$(".atc-prodBrand").val(),
     		$(".atc-categoryLeve1").val(),
     		$(".atc-categoryLeve2").val(),
     		$(".atc-categoryLeve3").val(),
     	);
     	/***For GA4 Analytics add_to_cart end***/

       rdt("track", "AddToCart", {
        currency: "USD",
        itemCount: parseInt($(".atc-productQty").val()),
        value: parseFloat($.trim(productUnitPrice).replace('$', "")),
        products: [
          {
            id: $(".atc-prodCode").val(),
            name: $(".atc-prodName").val(),
            category: $(".atc-categoryLeve1").val()
          }
        ]
       });

     	if($("body").hasClass("page-productDetails")){
            var getUOMval = '';
  	        if($('.pdpRedesignuom-wrapper').is(':visible')){	            
  	            if($('.pdpRedesignuom-wrapper .bulk-price').hasClass('uom-dropdown-text')){
  	                if($('.pdpRedesignuom-wrapper .bulk-price.uom-dropdown-text').text()){
                      const isUOMCaseSelected = $('.pdpRedesignuom-wrapper .selected-uom-item input')[0].checked;
  	                  getUOMval = isUOMCaseSelected ? $('.pdpRedesignuom-wrapper .selected-uom-item input')[1].value : $('.pdpRedesignuom-wrapper .bulk-price.uom-dropdown-text:first').text().split('/')[1];
  	                }
  	            }
  	            else{
  	              if($('.pdpRedesignuom-wrapper .bulk-price .price').text() && $('.pdpRedesignuom-wrapper .bulk-price .price').text().trim().split('/')[1]){
  	                getUOMval = $('.pdpRedesignuom-wrapper .bulk-price .price').text().trim().split('/')[1].trim();
  	              }                     
  	            }	            
  	        }
            else{
              if($('.redesign-rgt-content .retail-your-price-container .bulk-price .price').text() && $('.redesign-rgt-content .retail-your-price-container .bulk-price .price').text().trim().split('/').length){
                var len = $('.redesign-rgt-content .retail-your-price-container .bulk-price .price').text().trim().split('/').length;
                getUOMval = $('.redesign-rgt-content .retail-your-price-container .bulk-price .price').text().trim().split('/')[len-1].trim();
              }
              else{
                var len = $('.redesign-rgt-content .retail-your-price-container .bulk-price').text().trim().split('/').length;
                getUOMval = $('.redesign-rgt-content .retail-your-price-container .bulk-price').text().trim().split('/')[len-1].trim();
              } 
            }
            digitalData.product[0].productInfo['ProductUoM'] = getUOMval;
            if(ACC.global.atcButtonClickEvent == undefined) ACC.global.atcButtonClickEvent = 'body'
            digitalData.product[0].productInfo['addToCartTracking'] = ACC.global.atcButtonClickEvent;
            ACC.global.atcButtonClickEvent = 'body';
  	    }

    	  _AAData.popupPageName= ACC.config.atcPageName;
    	  _AAData.popupChannel= ACC.config.checkoutpathingChannel;
    	  
    	  if(digitalData.pfm)
	      	{
	      	  delete digitalData.pfm;
	      	  delete digitalData.pfmdetails;
	      	}
    	  
    	  if(ACC.global.productAnalyticsRecAtc)
    		  {
		      
		      digitalData.pfm = "recommendation";
		 	  digitalData.pfmdetails = _AAData.page.pageName;
		     
		      
		      digitalData.eventData={
		    		    linktype:"recommendation",
		                linkName:"Add to Cart",
		                onClickPageName: $(".siteonepagename").val()
		            }
    		  
    		  }
		 	try {
			    	 _satellite.track('popupView');
	        } catch (e) {}
        
          var getURL = window.location.href;
          var urlCase = getURL.toLowerCase();

          if (urlCase.indexOf('isrc=featured') > 0) {
            
            try {
            _AAData.method = "recommendation";
            _AAData.methodMetaData = "featured";
            }catch(e){}
            
            digitalData.eventData={
              linkName: 'recommendation',
              linkType: 'Add to Cart',
              onClickPageName: $(".pagename").val()
            }
            digitalData.product[0].productInfo['addToCartLocation'] = "recommendation";
            
          }
          if (urlCase.indexOf('isrc=featuredpopup') > 0) {
            
            if(digitalData.pfm)
            {
              delete digitalData.pfm;
              delete digitalData.pfmdetails;
            }
            try {
              if(_AAData.method){
              delete _AAData.method;
              delete _AAData.methodMetaData ;
              }
            }catch(e){}
              
            _AAData.pfm= "recommendation";
            _AAData.pfmDetails= $(".pagename").val();
           

            digitalData.eventData={
              linkName: 'recommendation',
              linkType: 'Add to Cart',
              onClickPageName: $(".pagename").val()
            }
            
          digitalData.product[0].productInfo['addToCartLocation'] = "recommendation";

          }

        if ($(".orderhistoryDetails-singleOrder").hasClass("sub-section")) {
          $(".qty-title-atc").text(($(".selected-btn").closest(".sub-section").find(".qty-buyagain").text()).trim());
        }
        else if ($("#bia-qty").hasClass("bia-qty-txt-field")) {
          $(".qty-title-atc").text(($(".selected-btn").closest(".qty-button-wrapper").find("#bia-qty").val()).trim());

        }
        else {
          $(".qty-title-atc").text(($(".selected-btn").closest(".item__list--header").find(".qtyValue").text()).trim());

        }
        $('.page-quickOrderPage .js-mini-cart-close-button').text(ACC.config.returnToQuick);
        $('.page-quickOrderPage .add-to-cart-button').text(ACC.config.viewFullCart);
        $('.page-quickOrderPage .minicart-title').text(ACC.config.productsAdded);
        $('.page-quickOrderPage #colorbox').addClass('quick-order-colorbox');
        $('body').css("overflow-y", "hidden");
        let target = $(".add-to-cart-overlay-mixedcart");
        if(target.find(".product-recs-wrapper").length){
          let tempHeight = $(window).outerHeight() - $("#addToCartLayer").outerHeight() - $(".cartpage-popup_btn").outerHeight()-35;
          target.find(".product-recs-wrapper").height(tempHeight);
        }
      },
      onClosed: function () {       
        ACC.global.productAnalyticsRecAtc = false;
	        
      }
    });
  },
  displayAddToCartPopup: function (cartResult, statusText, xhr, formElement) {

    $ajaxCallEvent = true;
    $('#addToCartLayer').remove();
    if (typeof ACC.minicart.updateMiniCartDisplay == 'function') {
      ACC.minicart.updateMiniCartDisplay();
    }
    var titleHeader = $('#addToCartTitle').html();
    if (($('.page-quickOrderPage').length > 0) && (/Android|webOS|iPhone|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent))) {

      var elements = $(cartResult.addToCartLayer);
      var numberItemsInCart = $('#numberItemsInCart', elements).val();

      if (numberItemsInCart > 1) {
        $("#itemCountForMobile").html("You have " + numberItemsInCart + " items in your cart");
      }
      else {
        $("#itemCountForMobile").html("You have " + numberItemsInCart + " item in your cart");
      }
      $("#itemCountForMobile").removeClass('hidden');
      $("#addToCartMobileMesage").removeClass('hidden');
      $("#ForMobileLink").removeClass('hidden');
      window.scrollTo(0, 0);

    } else {

      ACC.product.addToModal(cartResult);


    }
    var productCode = $('[name=productCodePost]', formElement).val();
    var quantityField = $('[name=qty]', formElement).val();

    var quantity = 1;
    if (quantityField != undefined) {
      quantity = quantityField;
    }

    var cartAnalyticsData = cartResult.cartAnalyticsData;

    var cartData = {
      "cartCode": cartAnalyticsData.cartCode,
      "productCode": productCode, "quantity": quantity,
      "productPrice": cartAnalyticsData.productPostPrice,
      "productName": cartAnalyticsData.productName
    };
    ACC.track.trackAddToCart(productCode, quantity, cartData);
  },

  bindRetailPrice: function () {
    $(document).on("click", ".retailPriceLink", function (e) {
      var productCode = $(this).data('productcode');
      var isOrderingAccount = $(this).data('isOrderingaccount');
      var isRegulatedItem = $(this).data('isRegulateditem');
      var isStoreproduct = $(this).data('isStoreproduct');
      var isLicensed = $(this).data('isLicensed');
      var isProductsellable = $(this).data('isProductsellable');
      var index = $(this).data('index');
      var isRup = ($("#isRup" + productCode).val() == 'true');
      var phoneNumber = $("#sessionStorePhoneNumber").val();
      loading.start();
      $.ajax({
        url: ACC.config.encodedContextPath + "/p/branchRetailPrice?productCode=" + productCode + "&quantity=1",
        method: "POST",
        success: function (siteOneCspResponse) {
          if (siteOneCspResponse.isSuccess) {
            $("#retailPriceLink" + productCode).hide();
            $("#retailPriceSection" + productCode).show();
            $("#callForPriceEnabled" + productCode).hide();
            if ((siteOneCspResponse.price != null) && (siteOneCspResponse.price != undefined)) {
              $("#retailPrice" + productCode).html(siteOneCspResponse.price.formattedValue);
              $("#retailPriceValue" + index).val(siteOneCspResponse.price.value);
            } else {
              var minPrice = siteOneCspResponse.priceRange.minPrice.formattedValue;
              var maxPrice = siteOneCspResponse.priceRange.maxPrice.formattedValue;
              $("#retailPrice" + productCode).html(minPrice + " - " + maxPrice);
              $("#retailPriceValue" + index).val(minPrice + " - " + maxPrice);
            }
            ACC.promotions.calculatePromotionPrice($("#productPromotion" + index), productCode);
            $("#retailPriceLink" + productCode).parent().siblings(".price").children().find(".add_price").css("text-decoration", "line-through")
            if (isOrderingAccount && isStoreproduct && ((isRegulatedItem && isProductsellable && (!isRup || (isRup && isLicensed))) || !isRegulatedItem)) {
              $("#listPageAddToCart_" + productCode).attr("disabled", false);
              $("#orderOnlineATC_" + productCode).attr("disabled", false);
            }
          } else {
            $("#retailPriceLink" + productCode).hide();
            $("#callForPriceEnabled" + productCode).hide();
            if ($('#retailPrice' + productCode == "")) {
              $("#basePrice" + index).show();
              $('#salePrice' + index).show();
            } else {
              $("#basePrice" + index).hide();
              $('#salePrice' + index).hide();
            }
            if (siteOneCspResponse.errorMessageToDisplay == "true") {
              $("#callForPriceEnabled" + productCode).show();
            }
          }
        }
      }).always(function () {
        loading.stop();
      });
    });
  },

  bindCustomerSpecificPrice: function () {
    $(document).on("click", ".customerSpecificPriceLink", function (e) {
      //alert("customerSpecificPriceLink clicked");
      var productCode = $(this).data('productcode');
      var isOrderingAccount = $(this).data('isOrderingaccount');
      var isRegulatedItem = $(this).data('isRegulateditem');
      var isStoreproduct = $(this).data('isStoreproduct');
      var isLicensed = $(this).data('isLicensed');
      var isProductsellable = $(this).data('isProductsellable');
      var index = $(this).data('index');
      var isRup = ($("#isRup" + productCode).val() == 'true');
      var phoneNumber = $("#sessionStorePhoneNumber").val();
      loading.start();
      $.ajax({

        url: ACC.config.encodedContextPath + "/p/customerprice?productCode=" + productCode + "&quantity=1",
        method: "POST",

        success: function (siteOneCspResponse) {
          if (siteOneCspResponse.isSuccess) {
            $("#customerSpecificPriceLink" + productCode).hide();
            $("#customerSpecificPriceSection" + productCode).show();
		        $("#callForPriceEnabled" + productCode).hide();

            if ((siteOneCspResponse.price != null) && (siteOneCspResponse.price != undefined)) {
              $("#customerSpecificPrice" + productCode).html(siteOneCspResponse.price.formattedValue);
              $("#customerSpecificPriceValue" + index).val(siteOneCspResponse.price.value);
            } else {
              var minPrice = siteOneCspResponse.priceRange.minPrice.formattedValue;
              var maxPrice = siteOneCspResponse.priceRange.maxPrice.formattedValue;
              $("#customerSpecificPrice" + productCode).html(minPrice + " - " + maxPrice);
              $("#customerSpecificPriceValue" + index).val(minPrice + " - " + maxPrice);
            }

            ACC.promotions.calculatePromotionPrice($("#productPromotion" + index), productCode);
            /*$('.strikk > span').css("text-decoration","line-through");*/
            $("#customerSpecificPriceLink" + productCode).parent().siblings(".price").children().find(".add_price").css("text-decoration", "line-through")

            if (isOrderingAccount && isStoreproduct && ((isRegulatedItem && isProductsellable && (!isRup || (isRup && isLicensed))) || !isRegulatedItem)) {
              $("#listPageAddToCart_" + productCode).attr("disabled", false);
              $("#orderOnlineATC_" + productCode).attr("disabled", false);
            }

            $("#priceUnavailabilityMsg" + index).addClass('hidden');
            $("#cspError" + index).hide();
          }
          else {
            $("#customerSpecificPriceLink" + productCode).hide();
	          $("#callForPriceEnabled" + productCode).hide();
            if ($('#customerSpecificPrice' + productCode == "")) {
              $("#basePrice" + index).show();
              $('#salePrice' + index).show();
            } else {
              $("#basePrice" + index).hide();
              $('#salePrice' + index).hide();
            }
            if(siteOneCspResponse.errorMessageToDisplay == "true")
               {
                  $("#callForPriceEnabled" + productCode).show();
               }
            $("#cspError" + index).html("Price not available. Please contact store <a href='tel:" + phoneNumber + "'>" + phoneNumber + "</a>").show();

            $("#priceUnavailabilityMsg" + index).addClass('hidden');
          }
        }
      }).always(function () {
        loading.stop();
      });

    });
  },

  orderOnlinePopup: function () {
    $(document).on("click", "#orderOnlineATC", function () {
      ACC.colorbox.open("", {
        html: $("#orderOnlinePopup").html(),
        width: '520px',
        height: 'auto',
        close: '<span class="glyphicon glyphicon-remove"></span>',
      });

    });
    $(document).on("click", "button[name='atclistoo']", function () {
      ACC.colorbox.open("", {
        html: $("#orderOnlinePopup").html(),
        width: '520px',
        height: 'auto',
        close: '<span class="glyphicon glyphicon-remove"></span>',
      });

    });
  },

  getProductCodeName: function () {
    $(document.body).on('click', '.name', function (e) {
      var productNameFirst = $(this).text();
      var productName = productNameFirst.trim();
      var productCode = $(this).parent().find(".productCodeValue").val();
      var productDetail = productCode + " | " + productName;
      try {
        _AAData.searchresultClicked = productDetail;
        _satellite.track("searchresultclicked");
      } catch (e) { }
    });
  },


  getsortValue: function () {
    $('#sortOptions1').on('change', function (e) {
    
    	if(digitalData.pfm)
    	{
    	  delete digitalData.pfm;
    	  delete digitalData.pfmdetails;
    	}
    	
      var optionSelected = $("option:selected", this);
      var sortName = this.value;
      digitalData.eventData = {
        sortName: sortName,
      }
      try {
        _satellite.track("sortByClick");
      } catch (e) { }
    });

  },

  disableRightclick: function () {
    $(document).ready(function (e) {
      $(".js-qty-selector-input, .js-qty-updateOne, .js-quick-order-qty").on("contextmenu", function (e) {
        return false;
      });
    });

  },
  checkProductImagePdp: function () {
    if ($(".page-productDetails").find(".product-thumb").length > 0) {
      $(".js-gallery-image").addClass("image-gallery-box");
    }
  },
  checkNurseryBackorder: function (lineItem) {
      var variantProductFullfilledStoreType = lineItem.find(".variantProductFullfilledStoreType").val();
      var variantProductType = $("#level1").val();
      return variantProductFullfilledStoreType === 'InventoryHit' && variantProductType === 'Nursery';
  },
  productVariant: function () {
    $(".product-variant-table .pdp-variant-qty").on('input', function (e) {
      var lineItem = $(this).closest('.product-variant-table__values');
      $(this).removeClass("variant-qty-error-message");
      lineItem.find(".qty-alert").css("display", "none");
      lineItem.find('.js-available-qty').removeClass("variant-qty-error-message");
      lineItem.find(".product-variant-table__data--atcbtn").removeAttr("disabled");
      if ($(this).val() === "0") {
        $(this).val("1");
      }
      else {

        if ($('.js-available-qty').length && lineItem.find(".pdp-variant-qty").val().trim() > lineItem.find(".product-variant-table__data--atcbtn").data("available-qty") && !ACC.product.checkNurseryBackorder(lineItem)) {
          lineItem.find(".qty-alert").css("display", "inline");
          lineItem.find(".product-variant-table__data--atcbtn").attr("disabled", "disabled");
          $(this).addClass("variant-qty-error-message");
          lineItem.find('.js-available-qty').addClass("variant-qty-error-message")
        }

      }
    });

    $(".product-variant-table .product-variant-table__data--atcbtn").on('click', function (e) {

      var lineItem = $(this).closest('.product-variant-table__values');

      if ($('.js-available-qty').length && lineItem.find(".pdp-variant-qty").val().trim() > $(this).data("available-qty") && !ACC.product.checkNurseryBackorder(lineItem)) {
        e.preventDefault();
      }
      else {
        lineItem.find('.qty-hidden-variant').val(lineItem.find(".pdp-variant-qty").val().trim());
        $(this).closest('form').submit();
        lineItem.find(".qty-alert").css("display", "none");
        lineItem.find(".pdp-variant-qty").removeClass("variant-qty-error-message");
        $(lineItem.find('.js-available-qty')).removeClass("variant-qty-error-message")
        lineItem.find(".pdp-variant-qty").val("1");
      }
    });

    $(".viewMoreLess").on("click", function (a) {
      a.preventDefault();
      ACC.product.showMoreVariant(this);
      //var state = $(this).data('state');
      //var element = $(this).parent().parent().find('.product-variant-table__values');
      //ACC.product.showMoreAndLess(this, state, element, ACC.config.variantShow, ACC.config.variantHide, 7);
    });
    $(".qty-alert").tooltip();

  },
  checkShippingOnlyPdp : function(element){
    let shippingOnlyProduct = $('.trackProductCode').val();
    if ($(element).val().trim() > $(element).data("available-qty")) {
      $(".shippingOnlyDisabled").val("true");
      $('.js-atc-'+shippingOnlyProduct).attr("disabled", "disabled");
      $(".intervalQtyError").removeClass("hidden");
    }
    else{
      $(".shippingOnlyDisabled").val("false");
      $('.js-atc-'+shippingOnlyProduct).attr("disabled", false);
      $(".intervalQtyError").addClass("hidden");
    }
  },
   checkAvlQuantity :function(element) {

    let shippingOnly = $('.isShippingHubOnlyPDP').val();
    if(shippingOnly == 'true') {
      ACC.product.checkShippingOnlyPdp(element);
    }
	  var variantisForceInStock = $('#variantisForceinstock').val();
	  if ($(element).data('is-nursery') === true && !variantisForceInStock) {
      if ($(element).val().trim() > $(element).data("available-qty")) {
        $(element).addClass("add-border border-red text-red variant-qty-error-message");
        if ($(element).hasClass("js-list-add-to-cart")) {
          $(element).closest(".saved-list-sec").find(".js-my-order-qty-error").removeClass("hide");
          $(element).closest(".saved-list-sec").find(".js-enable-btn").attr("disabled", "disabled");
          ACC.savedlist.bindEnableList();
        }

        else {
          $("#qtyError").html('<img class="icon-red-exclamation cart-qty-alert" src="/_ui/responsive/theme-lambda/images/Exclamation-point.svg" alt=""/>' + $(element).data("availableQty") + " " + ACC.config.quantityExceeded).css("display", "block");
          $(".js-pdp-availability").addClass("text-red");
          $('.pdp-btn #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
 		 if($(element).hasClass("pdp-variant-qty")){
			$(element).closest(".product-variant-table__values").find(".js-available-qty").addClass("variant-qty-error-message");
		  }
        }



      }
      else {
        if ($(element).hasClass("js-list-add-to-cart")) {
          $(element).closest(".saved-list-sec").find(".js-my-order-qty-error").addClass("hide");
          $(element).closest(".saved-list-sec").find(".js-enable-btn").removeAttr("disabled");
          $(element).removeClass("add-border border-red text-red variant-qty-error-message");
          ACC.savedlist.bindEnableList();
        }
        else {
          $("#qtyError").css("display", "none");
		  if($(element).hasClass("pdp-variant-qty")){
			$(element).closest(".product-variant-table__values").find(".js-available-qty").removeClass("variant-qty-error-message");
		  }
          $(".js-pdp-availability").removeClass("text-red");
          $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $(element).removeClass("add-border border-red text-red variant-qty-error-message");
        }

      }
    }
  },
  
  checkMinQuantity :function(element,bulkuomselection) {
    var UOMRestrict = true;
    $(".intervalQtyInfo").removeClass("hidden");
    if ($('.multipleUomContents').length > 0) {
      if (!$('.pdp-slider-text').is(':Visible')) {
        for (let UOMoption = 0; UOMoption < $('.multipleUomItem ').length; UOMoption++) {
          if ($('.multipleUomItem ').eq(UOMoption).hasClass("selected-uom-item") && Number($('.multipleUomItem ').eq(UOMoption).data('inventorymultiplier')) > 1) {
            UOMRestrict = false;
            break;
          }
        }
      }
      if (Number($("#inventoryQty").val()) > 1 && $('.pdp-slider-text').is(':Visible')) {
            UOMRestrict = false;
      }
      if(Number($(element).data('min-qty')) > 1 || Number($(element).data('minimum-qty')) > 1){
        if(bulkuomselection == 1 && !UOMRestrict){
          $('.js-qty-selector-input').val(1);
          $(".multipleUomItem").attr("data-qty", 1);
          ACC.productDetail.multipleUomTotalPriceUpdater();
        }
        else if(bulkuomselection == 1 && UOMRestrict && Number($(element).data('min-qty')) > 1 ){
          $('.js-qty-selector-input').val(Number($(element).data('min-qty')));
          $(".multipleUomItem").attr("data-qty", Number($(element).data('min-qty')));
          ACC.productDetail.multipleUomTotalPriceUpdater();
        }
        else if(bulkuomselection == 1 && UOMRestrict && Number($(element).data('minimum-qty')) > 1 ){
          $('.js-qty-selector-input').val(Number($(element).data('minimum-qty')));
          $(".multipleUomItem").attr("data-qty", Number($(element).data('minimum-qty')));
          ACC.productDetail.multipleUomTotalPriceUpdater();
        }
      }
    }
    if (UOMRestrict) {
      var minqtyerror = 0;
      $(".intervalQtyInfo-mobile").removeClass("intervalQtyInfo-bottom");
      if ($(element).data('min-qty') != null && $(element).data('min-qty') != "0" && $(element).data('min-qty') != "1" && $(element).data('min-qty') != '') {
        $(".mobile-pdp-sticky").addClass("mobile-pdp-box-removal");
        if ((Number($(element).val().trim())!=0)&&(Number($(element).val().trim()) % $(element).data('min-qty')) == 0 && $(element).val().trim() != '') {
          $(".intervalQtyError").addClass("hidden");
          $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.addtocart-detail #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.mobile-addcart #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $(".js-qty-selector-input").removeClass("qtyBoxError");
          minqtyerror = 0;
        }
        else {
          $(".intervalQtyError").removeClass("hidden");
          $('.pdp-btn #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $('.addtocart-detail #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $('.mobile-addcart #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $(".js-qty-selector-input").addClass("qtyBoxError");
          minqtyerror = 1;
        }
      }
      else {
        $(".mobile-pdp-sticky").removeClass("mobile-pdp-box-removal");
      }
      if ($(".page-detailsSavedListPage").length) {
        $("#selectedProdType" + $(element).attr("id").split("_")[1]).data("listqty", $(element).val());
      }
      else {
        if ($(pdpAddtoCartInput).data('available-qty') != null && $(pdpAddtoCartInput).data('available-qty') != '' && $(pdpAddtoCartInput).data('eeee')) {
          if (Number(document.getElementById('pdpAddtoCartInput').value.trim()) > $(pdpAddtoCartInput).data('available-qty')) {
            $(".mobile-pdp-sticky").addClass("mobile-pdp-box-removal");
            $(".availQtyError").removeClass("hidden");
            $(".availQtyError1").removeClass("hidden");
            $(".intervalQtyError").addClass("hidden");
            $('.pdp-btn #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
            $('.addtocart-detail #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
            $('.mobile-addcart #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
            $(".js-qty-selector-input").addClass("qtyBoxError");
            if ($(pdpAddtoCartInput).data('min-qty') != null && $(pdpAddtoCartInput).data('min-qty') != "0" && $(pdpAddtoCartInput).data('min-qty') != "1" && $(pdpAddtoCartInput).data('min-qty') != '') {
              $(".availQtyError").addClass("hidden");
              $(".availQtyError1").addClass("hidden");
              $(".availQtyError2").removeClass("hidden");
              $(".intervalQtyInfo-mobile").addClass("intervalQtyInfo-bottom");

            }
          } else {
            $(".availQtyError").addClass("hidden");
            $(".availQtyError1").addClass("hidden");
            $(".availQtyError2").addClass("hidden");
            if ($(element).data('min-qty') == null && $(element).data('min-qty') == "0" && $(element).data('min-qty') == "1" && $(element).data('min-qty') == '') {
              $(".mobile-pdp-sticky").removeClass("mobile-pdp-box-removal");
            }
            if (minqtyerror == 0) {
              $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
              $('.addtocart-detail #productSelect').find('.js-enable-btn').removeAttr("disabled");
              $('.mobile-addcart #productSelect').find('.js-enable-btn').removeAttr("disabled");
              $(".js-qty-selector-input").removeClass("qtyBoxError");
            }
          }
        }
      }
    }
    else {
      if(($(element).data('min-qty') != null && $(element).data('min-qty') != "0" && $(element).data('min-qty') != "1" && $(element).data('min-qty') != '') || 
        ($(pdpAddtoCartInput).data('available-qty') != null && $(pdpAddtoCartInput).data('available-qty') != '' && $(pdpAddtoCartInput).data('eeee'))){
          $(".intervalQtyInfo").addClass("hidden");
          $(".intervalQtyError").addClass("hidden");
          $(".availQtyError").addClass("hidden");
          $(".availQtyError1").addClass("hidden");
          $(".availQtyError2").addClass("hidden");
          $(".js-qty-selector-input").removeClass("qtyBoxError");
          $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.addtocart-detail #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.mobile-addcart #productSelect').find('.js-enable-btn').removeAttr("disabled");
        }
    }
    if (UOMRestrict){
      if(Number($(element).data('minimum-qty')) > 1){
        $(".minQtyInfo").removeClass("hidden");
        $(".mobile-pdp-sticky").addClass("mobile-pdp-box-removal");
        if ((Number($(element).val().trim()) < $(element).data('minimum-qty')) && $(element).val().trim() != '') {
          $(".intervalQtyError").removeClass("hidden");
          $('.pdp-btn #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $('.addtocart-detail #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $('.mobile-addcart #productSelect').find('.js-enable-btn').attr("disabled", "disabled");
          $(".js-qty-selector-input").addClass("qtyBoxError");
        }
        else{
          $(".intervalQtyError").addClass("hidden");
          $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.addtocart-detail #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $('.mobile-addcart #productSelect').find('.js-enable-btn').removeAttr("disabled");
          $(".js-qty-selector-input").removeClass("qtyBoxError");
        }
      } 
    }
    else{
       if(Number($(element).data('minimum-qty')) > 1){
        $(".minQtyInfo").addClass("hidden");
        $(".mobile-pdp-sticky").removeClass("mobile-pdp-box-removal");
        $(".intervalQtyError").addClass("hidden");
        $('.pdp-btn #productSelect').find('.js-enable-btn').removeAttr("disabled");
        $('.addtocart-detail #productSelect').find('.js-enable-btn').removeAttr("disabled");
        $('.mobile-addcart #productSelect').find('.js-enable-btn').removeAttr("disabled");
        $(".js-qty-selector-input").removeClass("qtyBoxError");
       }
    }
  },
  CheckMinintervalPLP : function() {
   /* if($('.js-plp-uomqtyinterval').parent().next().find('.orderAccountingMsgPLP').hasClass("orderAccountingMsgPLP")){  
      $('.js-plp-uomqtyinterval').closest('.plp-ordermultiplesaddtocart').siblings('.plp-message2').addClass("plpintervalmargintop");
      $('.js-plp-uomqtyinterval').closest('.plp-ordermultiplesaddtocart').siblings('.plp-message1').addClass("plpintervalmargintop");
        
    }*/
  },
  checkMaxQuantityonhandPLP :function() {
	  /*if ($('.ohhandqty').data('eeeevalue') && $('.ohhandqty').val() > $('.ohhandqty').data('maxqty')){
      $('.ohhandqty').closest('.plp-ordermultiplesaddtocart').siblings('.plp-message3').removeClass("hidden");
      $('.ohhandqty').addClass("plp-qtyBoxError");
      $('.ohhandqty').parent().next().find('.js-enable-btn').addClass("PLPaddtocartmessage");
      $('.ohhandqty').parent().next().find('.js-plp-uomqtyinterval').addClass("PLPaddtocartmessage");
      $('.ohhandqty').closest('.plp-ordermultiplesaddtocart').siblings('.plp-message1').removeClass("hidden");
      $('.ohhandqty').closest('.plp-ordermultiplesaddtocart').siblings('.plp-message2').addClass("hidden");
    }*/
  },
  checkMinQuantityPLP: function (element, option) {
    var productCode = $(element).data('productcode');
    var baseUOM = sessionStorage.getItem("baseUOM_"+productCode);
    var qtyBox = $('#productPLPPostQty_'+productCode);
    var minqty = Number($(element).data('min-qty'));
    var minorderqty = Number($(element).data('min-orderqty'));
    var maxqty = Number($(element).data('maxqty'));
    var isIntervalQtyError = false;
    if(baseUOM == "true"){
	if(option == 'dropdown'){
		if (minqty != null && minqty != 0 && minqty > 0 && minqty != 1 && minqty != ''){
			qtyBox.val(minqty);
		}
		else if (minorderqty != null && minorderqty != 0 && minorderqty > 0 && minorderqty != 1 && minorderqty != ''){
			qtyBox.val(minorderqty);
		}
		else{
			qtyBox.val(1);
		}
	}
    if (minqty != null && minqty != 0 && minqty > 0 && minqty != 1 && minqty != '') {
      if ((Number($(element).val().trim()) % minqty) == 0 && Number($(element).val().trim()) != '') {
        $('.plp-message2-'+productCode).addClass("hidden");
        $('.plp-message1-'+productCode).removeClass("hidden");
        $('.plp-message3-'+productCode).addClass("hidden");
        $('#productPLPPostQtysection_'+productCode).removeClass("plp-qtyBoxError");
        //$(e.target).removeClass("plp-qtyBoxError");
        $('#listPageAddToCart_'+productCode).removeClass("plp-disable");
        $('#orderOnlineATC_'+productCode).removeClass("plp-disable");
        $('#plp-commonerror-'+productCode).addClass("hidden");
        /*$(e.target).parent().next().find('.js-enable-btn').removeClass("PLPaddtocartmessage");
          $(e.target).parent().next().find('.js-plp-uomqtyinterval').removeClass("PLPaddtocartmessage");
          if ($(e.target).parent().next().find('.orderAccountingMsgPLP').hasClass("orderAccountingMsgPLP")) {
          $(e.target).closest('.plp-ordermultiplesaddtocart').siblings('.plp-message2').addClass("plpintervalmargintop");
        }*/
      }
      if (!((Number($(element).val().trim()) % minqty) == 0 && Number($(element).val().trim()) != '')) {
		isIntervalQtyError = true;
        $('.plp-message2-'+productCode).removeClass("hidden");
        $('.plp-message1-'+productCode).addClass("hidden");
        $('.plp-message3-'+productCode).addClass("hidden");
        $('#productPLPPostQtysection_'+productCode).addClass("plp-qtyBoxError");
        //$(e.target).addClass("plp-qtyBoxError");
        $('#listPageAddToCart_'+productCode).addClass("plp-disable");
        $('#orderOnlineATC_'+productCode).addClass("plp-disable");
        $('#plp-commonerror-'+productCode).removeClass("hidden");
        /*$(e.target).parent().next().find('.js-enable-btn').addClass("PLPaddtocartmessage");
          $(e.target).parent().next().find('.js-plp-uomqtyinterval').addClass("PLPaddtocartmessage");
          if ($(e.target).parent().next().find('.orderAccountingMsgPLP').hasClass("orderAccountingMsgPLP")) {
          $(e.target).closest('.plp-ordermultiplesaddtocart').siblings('.plp-message1').addClass("plpintervalmargintop");
        }*/
      }
    } else {
      $(".mobile-pdp-sticky").removeClass("mobile-pdp-box-removal");
    }

    if (minorderqty != null && minorderqty != '') {
      if (Number($(element).val().trim()) != '') {
        if ((Number($(element).val().trim()) < minorderqty)) {
        $('.plp-message4-'+productCode).removeClass("hidden");
         $('.plp-message5-'+productCode).addClass("hidden");
         $('#listPageAddToCart_'+productCode).addClass("plp-disable");
         $('#orderOnlineATC_'+productCode).addClass("plp-disable");
         /*$(e.target).parent().next().find('.js-enable-btn').addClass("PLPaddtocartmessage");
          $(e.target).parent().next().find('.js-plp-uomqtyinterval').addClass("PLPaddtocartmessage");*/
          //$(e.target).addClass("plp-qtyBoxError");
          $('#productPLPPostQtysection_'+productCode).addClass("plp-qtyBoxError");
          $('#plp-commonerror-'+productCode).removeClass("hidden");
        } else {
          $('.plp-message4-'+productCode).addClass("hidden");
          $('.plp-message5-'+productCode).removeClass("hidden");
          $('#listPageAddToCart_'+productCode).removeClass("plp-disable");
          $('#orderOnlineATC_'+productCode).removeClass("plp-disable");
          /*$(e.target).parent().next().find('.js-enable-btn').removeClass("PLPaddtocartmessage");
          $(e.target).parent().next().find('.js-plp-uomqtyinterval').removeClass("PLPaddtocartmessage");*/
          //$(e.target).removeClass("plp-qtyBoxError");
          $('#productPLPPostQtysection_'+productCode).removeClass("plp-qtyBoxError");
          $('#plp-commonerror-'+productCode).addClass("hidden");
        }
      } else {
        $('.plp-message4-'+productCode).removeClass("hidden");
        $('.plp-message5-'+productCode).addClass("hidden");
        $('#listPageAddToCart_'+productCode).addClass("plp-disable");
        $('#orderOnlineATC_'+productCode).addClass("plp-disable");
        /*$(e.target).parent().next().find('.js-enable-btn').addClass("PLPaddtocartmessage");
        $(e.target).parent().next().find('.js-plp-uomqtyinterval').addClass("PLPaddtocartmessage");*/
        //$(e.target).addClass("plp-qtyBoxError");
        $('#productPLPPostQtysection_'+productCode).addClass("plp-qtyBoxError");
        $('#plp-commonerror-'+productCode).removeClass("hidden");
      }
    }

    if (maxqty != null && maxqty != 0 && maxqty > 0 && maxqty != 1 && maxqty != '' && $(element).data('eeeevalue') == true) {
      if (Number($(element).val().trim()) > maxqty && Number($(element).val().trim()) != '' && $(element).data('eeeevalue') == true) {

        $('.plp-message3-'+productCode).removeClass("hidden");
        //$(e.target).addClass("plp-qtyBoxError");
        $('#productPLPPostQtysection_'+productCode).addClass("plp-qtyBoxError");
        $('#plp-commonerror-'+productCode).removeClass("hidden");
        $('#listPageAddToCart_'+productCode).addClass("plp-disable");
        $('#orderOnlineATC_'+productCode).addClass("plp-disable");
        /*$(e.target).parent().next().find('.js-plp-uomqtyinterval').addClass("PLPaddtocartmessage");
        $(e.target).parent().next().find('.js-enable-btn').addClass("PLPaddtocartmessage");*/
        $('.plp-message1-'+productCode).removeClass("hidden");
        $('.plp-message2-'+productCode).addClass("hidden");
      }else{
		$('.plp-message3-'+productCode).addClass("hidden");
        //$(e.target).addClass("plp-qtyBoxError");
        if(isIntervalQtyError == true){
			$('#productPLPPostQtysection_'+productCode).addClass("plp-qtyBoxError");
	        $('#plp-commonerror-'+productCode).removeClass("hidden");
	        $('#listPageAddToCart_'+productCode).addClass("plp-disable");
	        $('#orderOnlineATC_'+productCode).addClass("plp-disable");
	        $('.plp-message1-'+productCode).addClass("hidden");
	        $('.plp-message2-'+productCode).removeClass("hidden");
		}else{
			$('#productPLPPostQtysection_'+productCode).removeClass("plp-qtyBoxError");
	        $('#plp-commonerror-'+productCode).addClass("hidden");
	        $('#listPageAddToCart_'+productCode).removeClass("plp-disable");
	        $('#orderOnlineATC_'+productCode).removeClass("plp-disable");
	        $('.plp-message1-'+productCode).removeClass("hidden");
	        $('.plp-message2-'+productCode).addClass("hidden");
		}
	  }
    }
  }else{
	  if(option == 'dropdown'){
      $('.plp-message1-'+productCode).addClass("hidden");
      $('.plp-message2-'+productCode).addClass("hidden");
      $('.plp-message3-'+productCode).addClass("hidden");
      $('.plp-message4-'+productCode).addClass("hidden");
      $('.plp-message5-'+productCode).addClass("hidden");
      $('#productPLPPostQtysection_'+productCode).removeClass("plp-qtyBoxError");
      $('#plp-commonerror-'+productCode).addClass("hidden");
      $('#listPageAddToCart_'+productCode).removeClass("plp-disable");
      $('#orderOnlineATC_'+productCode).removeClass("plp-disable");
      qtyBox.val(1);
      }
    }
  },
    
  bindRelatedArticle: function () {
    $(".related-article-container").on("click", function(e){
      if($(this).find(".show-related-article>span").hasClass("plus-circle")){
        $(this).find(".show-related-article").click()
      }
    })
    $(".show-related-article").on("click", function (a) {
      a.preventDefault();
      a.stopPropagation();
      var state = $(this).data('state');
      var element = $(this).closest(".related-article-container").find(".related-article-block");
      ACC.product.showMoreAndLess(this,state, element,"", "", 0);
      switch (state) {
      case "expand": {
    	  $(this).prepend('<span><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25"><defs><style>.minus-a{fill:#fff;stroke:#78a22f;}.minus-b{fill:#78a22f;}.minus-c{stroke:none;}.minus-d{fill:none;}</style></defs><g transform="translate(-139 -1306)"><g class="minus-a" transform="translate(139 1306)"><circle class="minus-c" cx="12.5" cy="12.5" r="12.5"/><circle class="minus-d" cx="12.5" cy="12.5" r="12"/></g><path class="minus-b" d="M6.5,208H.5a.5.5,0,0,0-.5.5v.5a.5.5,0,0,0,.5.5h6A.5.5,0,0,0,7,209v-.5A.5.5,0,0,0,6.5,208Z" transform="translate(148 1110)"/></g></svg></span>');
    	  $('.content-show-hide-items').text(ACC.config.relatedarticlehideTextname+" "+$(this).data('show-items')+" "+ACC.config.relatedarticleitemTextname);
    	  break;
      }
      case "collapse": {
	
		   $(this).prepend('<span class="plus-circle"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25"><defs><style>.plus-a{fill:#fff;}.plus-b{fill:#78a22f;}.plus-c,.d{stroke:none;}.plus-d{fill:#e0e0e0;}</style></defs><g transform="translate(-495 -1215)"><g class="plus-a" transform="translate(495 1215)"><path class="plus-c" d="M 12.5 24.5 C 9.294679641723633 24.5 6.281219959259033 23.25177955627441 4.01471996307373 20.98527908325195 C 1.748219966888428 18.71878051757812 0.5 15.70532035827637 0.5 12.5 C 0.5 9.294679641723633 1.748219966888428 6.281219959259033 4.01471996307373 4.01471996307373 C 6.281219959259033 1.748219966888428 9.294679641723633 0.5 12.5 0.5 C 15.70532035827637 0.5 18.71878051757812 1.748219966888428 20.98527908325195 4.01471996307373 C 23.25177955627441 6.281219959259033 24.5 9.294679641723633 24.5 12.5 C 24.5 15.70532035827637 23.25177955627441 18.71878051757812 20.98527908325195 20.98527908325195 C 18.71878051757812 23.25177955627441 15.70532035827637 24.5 12.5 24.5 Z"/><path class="plus-d" d="M 12.5 1 C 9.428239822387695 1 6.54033088684082 2.196210861206055 4.368270874023438 4.368270874023438 C 2.196210861206055 6.54033088684082 1 9.428239822387695 1 12.5 C 1 15.5717601776123 2.196210861206055 18.45967102050781 4.368270874023438 20.63172912597656 C 6.54033088684082 22.80379104614258 9.428239822387695 24 12.5 24 C 15.5717601776123 24 18.45967102050781 22.80379104614258 20.63172912597656 20.63172912597656 C 22.80379104614258 18.45967102050781 24 15.5717601776123 24 12.5 C 24 9.428239822387695 22.80379104614258 6.54033088684082 20.63172912597656 4.368270874023438 C 18.45967102050781 2.196210861206055 15.5717601776123 1 12.5 1 M 12.5 0 C 19.40356063842773 0 25 5.596439361572266 25 12.5 C 25 19.40356063842773 19.40356063842773 25 12.5 25 C 5.596439361572266 25 0 19.40356063842773 0 12.5 C 0 5.596439361572266 5.596439361572266 0 12.5 0 Z"/></g><path class="plus-b" d="M6.708,66.917H4.083V64.292A.292.292,0,0,0,3.792,64H3.208a.292.292,0,0,0-.292.292v2.625H.292A.292.292,0,0,0,0,67.208v.583a.292.292,0,0,0,.292.292H2.917v2.625A.292.292,0,0,0,3.208,71h.583a.292.292,0,0,0,.292-.292V68.083H6.708A.292.292,0,0,0,7,67.792v-.583A.292.292,0,0,0,6.708,66.917Z" transform="translate(504 1160)"/></g></svg></span>');
    	  $('.content-show-hide-items').text(ACC.config.relatedarticleshowTextname+" "+$(this).data('show-items')+" "+ACC.config.relatedarticleitemTextname);
    	    break;
      }
      case undefined: break;
    }
    });
  },
  pdpPlusMinusQty: function () {
    $('.pdp-qtyBtn').on("click", function (e) {
      var $target = $(e.target);
      var qty = $(this).siblings('#pdpAddtoCartInput').val();
      var orderQtyInterval = Number($('#orderQtyInterval').val());
      if (qty && !isNaN(qty)) {
        qty = Number(qty);
      } else {
        qty = 0;
      }
      if ($('.intervalQtyInfo').is(":Visible")) {
        if ($target.hasClass("plusQty")) {
          if (qty < 9999) {
            var currentMultiple = Math.floor(qty / orderQtyInterval);
            var nextMultiple = currentMultiple + 1;
            qty = nextMultiple * orderQtyInterval;
          }
        } else {
          if (qty > 1) {
            var isMultiple = qty % orderQtyInterval === 0;
            var previousMultiple = isMultiple ? qty - orderQtyInterval : qty - (qty % orderQtyInterval);
            qty = Math.max(orderQtyInterval, previousMultiple);
          }
        }
        qty = Math.max(orderQtyInterval, qty);
        $('.js-pdp-add-to-cart').val(qty);
        $(".multipleUomItem").attr("data-qty", qty);
        ACC.productDetail.multipleUomTotalPriceUpdater();
      } else {
        if ($target.hasClass("plusQty")) {
          if (qty < 9999) {
            $('.js-pdp-add-to-cart').val(qty + 1);
            $(".multipleUomItem").attr("data-qty", qty + 1);
            ACC.productDetail.multipleUomTotalPriceUpdater();
          }
        } else {
          if (qty > 1) {
            $('.js-pdp-add-to-cart').val(qty - 1);
            $(".multipleUomItem").attr("data-qty", qty - 1);
            ACC.productDetail.multipleUomTotalPriceUpdater();
          }
        }
      }
      $(this).siblings('#pdpAddtoCartInput').blur();
      $(this).siblings('#pdpAddtoCartInput').focus();
      $(this).trigger("qtyUpdated");
      return false;
    });

    $(".js-pdp-add-to-cart,.js-list-add-to-cart").on("input", function (e) {
      if ($(this).hasClass("js-pdp-add-to-cart")) {
        $('.js-pdp-add-to-cart').val($(this).val());
      }
      ACC.product.checkAvlQuantity(this);
      ACC.product.checkMinQuantity(this,0);
    });
    $(document).on("qtyUpdated", function (e) {
      var elem = '';
      if ($(e.target).hasClass("js-update-entry-quantity-list-btn")) {
        elem = $(e.target).siblings(".js-update-entry-quantity-input");
      }
      else {
        elem = $(e.target).siblings("#pdpAddtoCartInput");
      }
      ACC.product.checkAvlQuantity(elem);
      ACC.product.checkMinQuantity(elem,0);
    });
    $(document).ready(function () {
      var stockCheck = $('.product_stockCheck').val();
      if (stockCheck == "true") {
        $(".stock-icon").addClass("outstock");
        $(".stock-icon").removeClass("instock");
      } else {
        $(".stock-icon").addClass("instock");
        $(".stock-icon").removeClass("outstock");
      }
      if ($(".image-inner-wrapper").length == 1) {
        $(".owl-pagination").addClass("hidden");
      }
    });
    $(document).ready(function () {
      if ($(".js-gallery-carousel").length == 0) {
        $(".image-gallery__image .owl-item").removeClass("owl-itemSec");
      } else {
        $(".image-gallery__image .owl-item").addClass("owl-itemSec");
      }
    });
  },
  
  checkPdpStoreDetail: function(){
	  $(document.body).on('click','.pdp-store-link',function(e){
      $(".branchpopup_click").removeClass('pdpvarianttopLink');
      $('.pdp-store-link').removeClass('selectedVariantCode');
      $(e.target).addClass('selectedVariantCode');
      ACC.mystores.changebranchPopupFn();
      /*var newWindowWidth = $(window).width();
  			if (newWindowWidth > 481) {
          $(e.target).addClass('selectedVariantCode');
          ACC.mystores.changebranchPopupFn();
        }
        else{
          window.location.href= ACC.config.encodedContextPath + "/store-finder";          
        }*/
		  if(digitalData.pfm)
	      	{
	      	  delete digitalData.pfm;
	      	  delete digitalData.pfmdetails;
	      	}
		  var productSKU= $("#productcode").val();
		  var productAvailabilityStatus = $("input[name=productAvailabilityStatus]").val();
		  var storeNo = null;
		  if($(".pdp-store-detail").text().includes('#')){
			  storeNo= $(".pdp-store-detail").text().split('#')[1].split('.')[0];
		   }
		    digitalData.eventData={
				    productSKU: productSKU,
				  	storeNo: storeNo,
				  	productAvailabilityStatus: productAvailabilityStatus
		  	}
		  try {
              _satellite.track("checkStoreWhenonPDP");
          } catch (e) {}
	  });
  },
  
  curatedplp: function(){
	  ACC.product.showSlides(ACC.product.slideIndex);  
  },
  
  	slideIndex: 1,
	navigateButton: function(numb){
		$('.dropdown-content2').fadeIn('fast');
		ACC.product.showSlides(ACC.product.slideIndex += numb);
	},
	showSlides: function(numb){
	   let slides = $('.mobile-navbar').find('.mobile-navbar-angle');
	   
	   if (numb > slides.length) {ACC.product.slideIndex = 1}    
	   if (numb < 1) {ACC.product.slideIndex = slides.length}
	   slides.addClass('hidden');
	   slides.eq(ACC.product.slideIndex-1).removeClass('hidden');
	},
	cplpredirectUrl: function () {
		if(window.location.href.indexOf("redirectedlink")!= -1){
			   var cplpCart = $(".branch-selection").siblings('#cplpnumberitemcart').val();
			   var cplpCntstore = $(".branch-selection").siblings('#cplp-sessionstore').val();
			   var storeId =window.location.href.split("redirectedlink")[1];
			   var url =window.location.href.split("?")[0];
			   $('#cplp-storeid').val(storeId);
			   var cplpStoreid = $(".branch-selection").siblings('#cplp-storeid').val();

			  if (cplpCntstore!=cplpStoreid){
          setTimeout(function(){
            ACC.colorbox.open("", {
              html: $(".branch-selection").html(),
              width: "900px",
              className:"branch-selection-popup",
              close:'<span class="icon-close"></span>',
              onComplete:function(){
                $(document).on("click",".cplp-homebranchnotupdated", function(e){
                        ACC.colorbox.close();
                    });
                history.pushState(null, null, url+'?');
              },
            }); 
          },1000); 
			  }	
		  }	   
	},
	cplpredirectUrlBranchlink:function(){
		var cplpStoreid1 = $(".branch-selection").siblings('#cplp-storeid').val();
		 window.location.href= ACC.config.encodedContextPath + "/store-finder/make-my-store/"+cplpStoreid1;

	},

	cplpredirectUrlPopup: function(url,storeid){
		window.location=url+"?redirectedlink"+storeid;
	},

  handleLeftFacetSectionHeight: function () {
    var productListHeight = $(".prod-list-section").outerHeight();
    var leftSectionMainFiltersHeight = $(".main-facet-nav-filters").outerHeight();
    var currentHeight = $("#plpproductcountviewDesktop").outerHeight(true) + leftSectionMainFiltersHeight + $(".morewaytofilters").outerHeight(true);
    var moreFiltersBtnFlag = false;
    $('.morewaytofilters').nextAll('.facet.js-facet').each(function () {
      currentHeight += $(this).outerHeight();
      if (currentHeight > productListHeight) {
        $(this).addClass("hidden-md hidden-lg");
        if (!moreFiltersBtnFlag) {
          moreFiltersBtnFlag = true;
          $(".more-filters-btn-section").removeClass("hidden");
        }
      }
    });
  },

  plpPlusMinusQty: function (productcode, type) {
      var qtyBox = $('#productPLPPostQty_' + productcode);
      var qty = qtyBox.val();
      var orderQtyInterval = qtyBox.data('min-qty');
      if (qty && !isNaN(qty)) {
        qty = Number(qty);
      } else {
        qty = 0;
      }
      if (orderQtyInterval > 1) {
        if (type == "plus") {
          if (qty < 9999) {
            var currentMultiple = Math.floor(qty / orderQtyInterval);
            var nextMultiple = currentMultiple + 1;
            qty = nextMultiple * orderQtyInterval;
          }
        } else {
          if (qty > 1) {
            var isMultiple = qty % orderQtyInterval === 0;
            var previousMultiple = isMultiple ? qty - orderQtyInterval : qty - (qty % orderQtyInterval);
            qty = Math.max(orderQtyInterval, previousMultiple);
          }
        }
        qty = Math.max(orderQtyInterval, qty);
        qtyBox.val(qty);
      } else {
        if (type == "plus") {
          if (qty < 9999) {
            qtyBox.val(qty + 1);
          }
        } else {
          if (qty > 1) {
            qtyBox.val(qty - 1);
          }
        }
      } 
      $('#productPostPLPQty_' + productcode).val($('#productPLPPostQty_' + productcode).val());
      //ACC.global.qtyTotalPriceUpdaterPLP(productcode);
      ACC.product.checkMinQuantityPLP($('#productPLPPostQty_' +productcode), '');
      if($(".hardscapeProd_" + productcode).length) {
        ACC.product.handleQtyChangeForHardscapeProducts(productcode);
      } else {
        let qtyTarget = $('#productPLPPostQty_' + productcode);
        if (qtyTarget.length) {
          ACC.product.handleQtyChangeForProducts(qtyTarget, productcode);
        }
      }
      qtyBox.blur();
      qtyBox.focus();
      //qtyBox.trigger("qtyUpdated");
  },

  handleQtyChangeForHardscapeProducts: function (productCode) {
    let target = $(".hardscapeProd_" + productCode);
    let stockDataSection = ($(".plpviewtype").val() == "card") ? target.closest('.product-item').find('.cardstocksection') : target.closest('.product-item').find('.stock-section');
    let atcSection = ($(".plpviewtype").val() == "card") ? target.closest('.product-item').find(".card-atc-wrapper-section") : target.closest('.product-item').find('.list-atc-wrapper-section');
    let getAQuoteSection = target.closest('.product-item').find(".getAQuoteSection");
    if (stockDataSection.length && stockDataSection.is(":visible")) {
      if (stockDataSection.find(".stock-section-instock").length || stockDataSection.find(".stock-section-instock-nearby").length) {
        let stockDataCount = Number(stockDataSection.find('.bg-color').html());
        let qtyInput = Number($('#productPLPPostQty_' + productCode).val() || 1);
        let hardscapeForceStock = $(".hardscapeForceStock_" + productCode).val();
        if (qtyInput > stockDataCount && hardscapeForceStock !== "true") {
          if (target.closest('.product-item').find(".getAQuoteFlagForB2BUser").val() == "true") {
            atcSection.addClass('hidden');
            getAQuoteSection.removeClass("hidden");
            let multipleUOMEle = target.closest('.product-item').find('.uom-wrapper-plp');
            if (multipleUOMEle.length && multipleUOMEle.is(":visible")) {
              target.closest('.product-item').find(".plp-warning_info_" + productCode).find('.plp-commonerror').text(ACC.config.changeUOMForAvailability);
              target.closest('.product-item').find(".plp-warning_info_" + productCode).removeClass("hidden");
            }
          } else {
            target.closest('.product-item').find(".plp-warning_info_" + productCode).find('.plp-commonerror').text(ACC.config.expectDelay);
            target.closest('.product-item').find(".plp-warning_info_" + productCode).removeClass("hidden");
          }
        } else {
          if (target.closest('.product-item').find(".getAQuoteFlagForB2BUser").val() == "true") {
            atcSection.removeClass('hidden');
            getAQuoteSection.addClass("hidden");
          }
          target.closest('.product-item').find(".plp-warning_info_" + productCode).addClass("hidden");
        }
      }
    }
  },

  handleQtyChangeForProducts: function (target, productCode) {
    let multipleUOMEle = target.closest('.product-item').find('.uom-wrapper-plp');
    if (multipleUOMEle.length && multipleUOMEle.is(":visible")) {
      let stockDataSection = ($(".plpviewtype").val() == "card") ? target.closest('.product-item').find('.cardstocksection') : target.closest('.product-item').find('.stock-section');
      if (stockDataSection.length && stockDataSection.is(":visible")) {
        if (stockDataSection.find(".stock-section-instock").length || stockDataSection.find(".stock-section-instock-nearby").length) {
          let stockDataCount = Number(stockDataSection.find('.bg-color').html());
          let qtyInput = Number($('#productPLPPostQty_' + productCode).val() || 1);
          if (qtyInput > stockDataCount) {
            target.closest('.product-item').find(".plp-warning_info_" + productCode).find('.plp-commonerror').text(ACC.config.expectDelay);
            target.closest('.product-item').find(".plp-warning_info_" + productCode).removeClass("hidden");
            if (target.closest('.product-item').find("#plp-commonerror-" + productCode).is(":visible")) {
              target.closest('.product-item').find("#plp-commonerror-" + productCode).addClass("hidden");
            }
          } else {
            target.closest('.product-item').find(".plp-warning_info_" + productCode).addClass("hidden");
          }
        }
      }
    }
  },
  isVariantsExpanded: false,
  isLoading: false,
  showMoreVariant: function(a) {
      if(ACC.product.isLoading){ 
        return;
      } 
      var state = $(a).data('state');
      var element = $(a).parent().parent().find('.product-variant-table__values');
      if(ACC.product.isVariantsExpanded){
        ACC.product.showMoreAndLess(a, state, element, ACC.config.variantShow, ACC.config.variantHide, 7);
      }
      else{
        ACC.product.bindPDPVariant(a, state, element, ACC.config.variantShow, ACC.config.variantHide, 7);
      }
  },
  bindPDPVariant: function(thisElement, state, element, contentMore, contentLess, numberOfitems) {   
      ACC.product.isLoading = true;
    $.ajax({

        url: ACC.config.encodedContextPath + '/p/getPDPVariantProducts',
        type: 'GET',
        cache: false,
        async: true,
        success: function (response) {
            var decoded = $('<div>').html(response.variants).text();
            $('.product-variant-table__values').last().after(decoded); 
            element = $(thisElement).parent().parent().find('.product-variant-table__values');
            ACC.product.isLoading = false;
            if( !ACC.product.isVariantsExpanded ){
              ACC.product.showMoreAndLess(thisElement, state, element, contentMore, contentLess, numberOfitems);
              ACC.product.isVariantsExpanded= true ; 
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //console.log('Error: ', textStatus, errorThrown); 
        }
    }); 

},
//SEO
  addPlpSchema: function(){
    const viewType = $(".plpviewtype").val();
    const productElm = Array.from(document.querySelectorAll(".product-item")).slice(0,10);
    const itemListElement = [];
    var offersElement = [];
    productElm.forEach((productitem, index) =>{
      const name = $(productitem).find("#ga4-productName").val().trim();
      const image = $(productitem).find(".thumb img").attr("src");
      const url = $(productitem).find(".linktracking-product").attr("href");
      var price = $(productitem).find(".atc-price-analytics").text().trim().replace("$", "");
      const sku = productitem.dataset.productId;
      var variantProducts;
      if(viewType == "list"){
        variantProducts = productitem.querySelectorAll(".variant-item");
      }
      else{
        variantProducts = productitem.querySelectorAll(".card-variant-item"); 
      }
      
      if(variantProducts.length){
        variantProducts.forEach((elements, indexv) => {
          if(price !== undefined && price !== null && price !== ""){
            offersElement.push(ACC.product.schemaOffers(elements, sku, url));
          } 
        });
      }
      else if($(productitem).find(".product-row").length){
        if(price !== undefined && price !== null && price !== ""){
          offersElement.push(ACC.product.schemaOffers($(productitem).find(".product-row"),sku, url));
        }       
      }
      else if($(productitem).find(".product-item-box").length){
        if(price !== undefined && price !== null && price !== ""){
          offersElement.push(ACC.product.schemaOffers($(productitem).find(".product-item-box"),sku, url));
        } 
      }
      if(name && url){
        const item = {       
            "@type": "Product",       
            "name": name,       
            "url": url,
            "image":  image
         }
         if(offersElement.length> 0){
          item["offers"] = offersElement.length === 1 ? offersElement[0] : offersElement;
         }
         itemListElement.push({
          "@type": "ListItem",       
          "position": index+1,
          "item": item
         });
        offersElement = [];
      }
    });
    if(itemListElement.length > 0){
      const plpSchema ={
        "@context": "http://schema.org",
        "@type": "ItemList",    
        "itemListElement": itemListElement 
    }
    const schemaScriptPlp = document.getElementById("plp-schema");
    const newSchema = document.createElement("script");
    newSchema.type = "application/Id+json";
    newSchema.id="plp-schema";
    newSchema.textContent = JSON.stringify(plpSchema, null, 2);
    if(schemaScriptPlp){
      schemaScriptPlp.parentNode.replaceChild(newSchema, schemaScriptPlp);
    }
    }
    
 },
 schemaOffers: function(element, sku, url){
    var offersElement;
    var price = $(element).find(".atc-price-analytics").text().trim().replace("$", "");
    let priceAsNumber;
    if(price !== undefined && price !== null && price !== ""){
      priceAsNumber = parseFloat(price);
    } 
    const currency = $("main").data('currencyIsoCode') || "USD";
    var availability;
    if($(element).find(".stock-section-instock").length == 1 || $(element).find(".stock-section-instock-nearby").length == 1){
      availability = "InStock";
    } 
    else{
      availability = "OutOfStock";
    }
    offersElement ={
      "@type": "Offer",
      "price": priceAsNumber,
      "priceCurrency": currency,
      "availability": availability,
      "url": url,
      "sku": sku
    };
  return offersElement;
 }
};

$(document).ready(function () {     
  $ajaxCallEvent = true;
  ACC.product.enableAddToCartButton();



  $(".qtyChanged").on('blur', function () {
    $(this).parents(".quantityInputSection").find(".add_to_cart_form #quantity").val($(this).val());
    $(this).parents(".saved-list-sec").find(".fetchWishList #assembly_qty").val($(this).val());
    $(this).parents(".saved-list-sec").find(".fetchWishList #list_qty").val($(this).val());

  });





  $('input[id^=regulated]').each(function () {
  	var thisRegulated=$(this);
    var productCode = this.id.split('regulated')[1];
    var isRegulatory = ($(this).val() == 'true');
    if (isRegulatory) {
      var isProductSellable = ($("#isProductSellable" + productCode).val() == 'true');
      var isSellableInventoryHit = ($("#isSellableInventoryHit_" + productCode).val() == 'true');
      var isStockAvailable = ($("#isStockAvailable_" + productCode).val() == 'true');
      var isLicensed = ($("#isLicensed" + productCode).val() == 'true');
      var isMyStoreProduct = ($("#isMyStoreProduct" + productCode).val() == "true");
      if (isProductSellable && isMyStoreProduct) {
        $.ajax({
          url: ACC.config.encodedContextPath + "/cart/rupcheck",
          data: { "productCode": productCode, "isRegulatory": isRegulatory },
          type: "GET",
          success: function (result) {
            $("#isRup" + productCode).val(result);

            if ((result && !isLicensed)) {
              $('#listPageAddToCart_' + productCode).attr('disabled', 'disabled');
              $('#orderOnlineATC_' + productCode).attr('disabled', 'disabled');
              $('#regulatoryLicenceCheck_' + productCode).removeClass('hidden');
              thisRegulated.closest(".product-item-box").find(".js-login-to-buy").attr('disabled', 'disabled');
              
            }
            else {
              $('#regulatoryMessage_' + productCode).removeClass('hidden');
              $('#regulatoryLicenceCheck_' + productCode).addClass('hidden');
            }
            
            /* unused code - commenting
            else if((!result || (result && isLicensed)) && isSellableInventoryHit)
            {
                $('#regulatoryMessage_' + productCode).removeClass('hidden');
                $('#regulatoryLicenceCheck_' + productCode).addClass('hidden');
            }
            else if((!result || (result && isLicensed)) && !isSellableInventoryHit)
            {
                $('#regulatoryUnavailablemsg_' + productCode).removeClass('hidden');
                $('#regulatoryLicenceCheck_' + productCode).addClass('hidden');
            }*/
          }
        });
      }
      if (!isProductSellable && isMyStoreProduct) {
        $('#listPageAddToCart_' + productCode).attr('disabled', 'disabled');
        $('#orderOnlineATC_' + productCode).attr('disabled', 'disabled');
        $('#regulatoryMessage_' + productCode).removeClass('hidden');
        $('#regulatoryLicenceCheck_' + productCode).addClass('hidden');
        thisRegulated.closest(".product-item-box").find(".js-login-to-buy").attr('disabled', 'disabled');
      }
    }
  });

  window.addEventListener("pageshow", function (event) {
    if($(".page-productGrid, .page-searchGrid, .template-pages-layout-siteOneCuratedPlpLayout, .template-pages-category-categoryLandingPage").length) {
      $(".custom-checkbox").prop("checked", false).trigger('change');
	  $(".custom-checkboxlistplp").prop("checked", false).trigger('change');
      $(".js-qty-updateOne").each(function () {
        var e = { target: this };
        var productCode = $(e.target).data('productcode');
        ACC.product.checkMinQuantityPLP($('#productPLPPostQty_' +productCode), '');
	    ACC.product.handleQtyChangeForHardscapeProducts(productCode);
      });
    }
  });

  $(".more-filters-btn").on('click', function () {
    let hiddenItems = $('.morewaytofilters').nextAll('.facet.js-facet.hidden-md.hidden-lg');
    if (hiddenItems && hiddenItems.length) {
      hiddenItems.each(function (i) {
        $(this).removeClass("hidden-md hidden-lg");
      });
    }
    $(".more-filters-btn-section").remove();
  });


});
function plpBorderAlignment(){
if ($(window).width() >= 1024) {
if($('body').hasClass("page-searchGrid") && $('.product-item-box').length>4){
	$('.product-item-box').slice(4).css('border-bottom', '0');
}
else{
$('.product-item-box').slice(0).css('border-bottom', '0');
}
$('.product-item-box').slice(-4).css('border-bottom', '1px solid #e0e0e0');
$('.product-item').filter(function(i) {
    
      return (i + 1) % 4 == 0
   
   
}).find('.product-item-box').each(function(){
  if(!$(this).hasClass("unset_border")){
    $(this).css('border-right', '1px solid #e0e0e0');
  }
})
}

else if ($(window).width() <= 1023 && $(window).width()>=640) {
if($('body').hasClass("page-searchGrid") && $('.product-item-box').length>2){
$('.product-item-box').slice(4).css('border-bottom', '0');
}
else{
$('.product-item-box').slice(0).css('border-bottom', '0');
}
$('.product-item-box').slice(-2).css('border-bottom', '1px solid #e0e0e0');
$('.product-item').filter(function(i) {
    
      return (i + 1) % 2 == 0
   
   
}).find('.product-item-box').css('border-right', '1px solid #e0e0e0')
}
else if ($(window).width()<640){
	if ($('body').hasClass("page-searchGrid") && $('.product-item-box').length>2){
		$('.product-item-box').eq(1).css('border-bottom', '1px solid #e0e0e0');
	}
}
}
$(document).ready(function () {
  plpBorderAlignment();
  if($(".page-productGrid, .page-searchGrid").length) {
    ACC.product.addPlpSchema();
  }
$(window).resize(function(){
	plpBorderAlignment();
});

// Realign article images in CLP starts
function bottomArticleRealign(){
if(screen.width>=1024){
	$(".related-article-big-image").height($(".clp-related-articles-small-image").height()-30)
}
}
var imgLen = $(".clp-related-articles-small-image .slide img").length,
    counter = 0;
function incrementImageCounter(){
  counter++;
  if ( counter === imgLen ) {
    bottomArticleRealign();
  }
}
$(".clp-related-articles-small-image .slide img").each(function() {
  if(this.complete) {
    incrementImageCounter()
  }
  else{
      this.addEventListener( 'load', incrementImageCounter, false );
  }
});

$(window).resize(function(){
  bottomArticleRealign();
});
// Realign article images in CLP ends
  if($('.simple-product-pdp__value--retail-price #uomPrice').children(':visible').length === 0){
    
    $('.simple-product-pdp__value--retail-price').parent().addClass("hide");
	$('.simple-product-pdp__value--your-price').removeClass("right-price-section");
	$('.simple-product-pdp__value--your-price').parent().removeClass("col-md-2 col-sm-6 col-xs-6 text-align-right").addClass("col-md-6 col-sm-12 col-xs-12 text-center");
    }
  
  if($('.simple-product-pdp__value--your-price #uomPrice').children(':visible').length === 0 && $('.askAnExpertEnable').val() == false){
    $('.simple-product-pdp__value--your-price').parent().addClass("hide");
	$('.simple-product-pdp__value--retail-price').parent().removeClass("col-md-2 col-sm-6 col-xs-6 ").addClass("col-md-6 col-sm-12 col-xs-12");
    }
  if(($('.simple-product-pdp__value--retail-price #uomPrice').children(':visible').length === 0) && ($('.simple-product-pdp__value--your-price #uomPrice').children(':visible').length === 0)){
		$('.simple-product-pdp__value--availability').css('border-left','0');
	}   
  if($(".simple-product-pdp__value--your-price").hasClass("pdp-anonymous")){
	$(".simple-product-pdp__value--your-price").find(".callBranchForPrice").addClass("hide")
  }  
  if($(".simple-product-pdp__value--retail-price").hasClass("pdp-non-anonymous")){
	$(".simple-product-pdp__value--retail-price").find(".callBranchForPrice").addClass("hide")
  }
  if($(".simple-product-pdp__value--your-price").find(".callBranchForPrice").length){
	$(".simple-your-price-title").addClass("hide");
  }
  $(document).on("click", ".update-qty-btn", function (e) {
    var updateQtyUrl = ACC.config.encodedContextPath + "/cart/updateQuantities"
    var updateQty = $(".order-qtybox").val();
    $.ajax({
      url: updateQtyUrl,
      cache: false,
      data: {
        productQty: updateQty,
        productCode: $(".product-image-cart-popup").data("prdcode"),

      },
      type: 'POST',
      success: function (data) {
        if (data === true) {
          $(".status-message").text("Product has been added to your cart.").removeClass("alert-error").addClass("alert-info");
        }
        else {
          $(".status-message").text("Problem occured while adding to your cart.").removeClass("alert-info").addClass("alert-error");
        }
      },
      error: function (error) {

      }
    })

  })
  $(document).on("click", ".js-click-tracking", function(){
		var clickTrackingURL = $(this).parents('.produc-recomm-item').find('#clickTrackingURL').val();
		if(clickTrackingURL!=''){
			$.ajax({
				async: false,
				url: clickTrackingURL
			});
		}
  })
  $(document).on("click", ".js-addtocart-productrecs", function(){
	  	var currentparent = $(this);
      var categoryProductlength = $('.js-addtocart-productrecs').parents('.category-tile-item').length;
      currentparent.removeClass("js-addtocart-productrecs");
		var clickTrackingURL = $(this).parents('.popupproductrecomment-container').find('#clickTrackingURL').val();
		$.ajax({
			async: false,
			url: clickTrackingURL
		});
		var productrecscode = $(this).parents('.popupproductrecomment-container').find('#productrecscode').val();
		var productrecsNamePost = $(this).parents('.popupproductrecomment-container').find('#productrecsNamePost').val();
		var productrecsPostPrice = $(this).parents('.popupproductrecomment-container').find('#productrecsPostPrice').val();
		var productrecsqty = $(this).parents('.popupproductrecomment-container').find('#productrecsqty').val();
		var productrecsstoreId = $(this).parents('.popupproductrecomment-container').find('#productrecsstoreId').val();
		var productrecsBrand = $(this).parents('.popupproductrecomment-container').find('#productrecsPostBrand').val();
		var productrecsPostCategoryLeve1 = $(this).parents('.popupproductrecomment-container').find('#productrecsPostCategoryLeve1').val();
		var productrecsPostCategoryLeve2 = $(this).parents('.popupproductrecomment-container').find('#productrecsPostCategoryLeve2').val();
		var productrecsPostCategoryLeve3 = $(this).parents('.popupproductrecomment-container').find('#productrecsPostCategoryLeve3').val();
		var productrecsPostPriceFromHtml = $(this).parents('.popupproductrecomment-container').find('.atc-price-analytics').text().trim().replace('$','').replaceAll(',','');
		
			$.ajax({
			        type: 'POST',
			        url: ACC.config.encodedContextPath + "/cart/add",
			        data: { 
			        	"productCodePost":  productrecscode, 
			        	"productNamePost": productrecsNamePost,
			        	"productPostPrice": productrecsPostPrice,
			        	"storeId": productrecsstoreId,
			        	"productPostPLPQty": productrecsqty
			        	},
					success: function(result) {
						currentparent.parents('.popupproductrecomment-container').find('.productrecs-Itemadded').removeClass('hidden').fadeIn("fast");
						setTimeout(function(){currentparent.parents('.category-tile-item').remove()}, 1000);
						if(categoryProductlength == '1'){
							$('.product-recs-wrapper').remove();
						}
						ACC.minicart.updateMiniCartDisplay();
						ACC.ga4analytics.add_to_cart(
							productrecscode,
							productrecsNamePost,
							parseFloat(productrecsPostPriceFromHtml),
							parseInt(productrecsqty),
							productrecsBrand,
							productrecsPostCategoryLeve1,
							productrecsPostCategoryLeve2,
							productrecsPostCategoryLeve3,
						);
            rdt("track", "AddToCart", {
              currency: "USD",
              itemCount: 1,
              value: productrecsPostPrice,
              products: [
                {
                  id: productrecscode,
                  name: productrecsNamePost,
                  category: productrecsPostCategoryLeve1
                }
              ]
             });
					}
			});
			
	//Adobe Analytics
	
	if ($('#isAnonymous').val() == "false") {
		var currentCartId = $("#currentCartId").val();
	}
	else {
		  var currentCartId = $("#anonymousCartId").val();
	}
    
	_AAData.cartId= currentCartId;
	
	 digitalData.pfm = "recommendation";
	 digitalData.pfmdetails = "checkout: item added to cart popup";
	 
	 digitalData.eventData={
			 linktype:"recommendation",
			 linkName:"Add to Cart",
             onClickPageName: "checkout: item added to cart popup"
         }
	 
	 
	 try {
		 _satellite.track('Add Cart');
	 } catch (e) {}
	 
	});
  
   //Adobe Analytics	
  
  $(document).on("click", ".recomm-popupcart-analytics", function(){
	  
	  if ($('#isAnonymous').val() == "false") {
			var currentCartId = $("#currentCartId").val();
		}
		else {
			  var currentCartId = $("#anonymousCartId").val();
		}
	  	_AAData.cartId= currentCartId;
		
		digitalData.pfm = "recommendation";
		digitalData.pfmdetails = "checkout: item added to cart popup";
		 
		 digitalData.eventData={
				 linktype:"recommendation",
				 linkName:"product:"+digitalData.eventData.linkName,
	             onClickPageName: "checkout: item added to cart popup"
	         }
		 
  });

  if ($("body").hasClass("pageType-ProductPage") || $("body").hasClass("pageType-CategoryPage") || $("body").hasClass("page-savedListPage") || $("body").hasClass("page-compareProductPage") || $("body").hasClass("page-searchGrid")) {
    $.cookie("accountPageId", "", { path: '/' });
  }
  if ($('.product-item').length <= 2) {
    $(".remove_compare_product").hide();
  }

  ACC.product.checkCSP();
});

function cartAddAnalytics() {
	
if(digitalData.pfm)
	{
	  delete digitalData.pfm;
	  delete digitalData.pfmdetails;
	}

  var cartItems = $(".mini-cart-link").data("miniCartItemsText");
  var miniCartRefreshUrl = $(".mini-cart-link").data("miniCartRefreshUrl");
  $.ajax({
    url: miniCartRefreshUrl,
    cache: false,
    type: 'GET',
    success: function (jsonData) {
	if(ACC.global.productAnalyticsRecAtc){
		
		digitalData.pfm = "recommendation";
		digitalData.pfmdetails = _AAData.page.pageName;
		
		digitalData.eventData={
		linktype:"recommendation",
		linkName:"Add to Cart",
		onClickPageName: $(".siteonepagename").val()
		}
	}
  else if(ACC.global.productCutatedplp){ 

      digitalData.eventData={
      linktype:"product-grid",
      linkName:"Add to Cart",
      onClickPageName: $(".pagename").val()
      }
    }
	else{

		digitalData.eventData={
		linktype:_AAData.eventType,
		linkName:"Add to Cart",
		onClickPageName: $(".siteonepagename").val()
		}
    var getURL = window.location.href;
    var urlCase = getURL.toLowerCase();
    if (urlCase.indexOf('isrc=featured') > 0 || urlCase.indexOf('isrc=featuredpopup') > 0) {
        digitalData.eventData={
        linkName: 'recommendation',
        linkType: 'Add to Cart',
        onClickPageName: $(".pagename").val()
        }
      }
      
	}
	
      if ($('#isAnonymous').val() == "false") {
        var currentCartId = $("#currentCartId").val();
      }
      else {
        var currentCartId = $("#anonymousCartId").val();
      }
      if (jsonData.miniCartCount === 1 && $(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0") {
        digitalData.cart = {
          cartId: currentCartId,
          event: "scOpen, scAdd",

        };
      }
      else {
        digitalData.cart = {
          cartId: currentCartId,
          event: "scAdd",
        };
      }

      try {
        _satellite.track("Add Cart");
      } catch (e) { }
    }


  });

}

function windowimageScroll(){
  var fixImgTop = ($('.product-main-info').length)?$('.product-main-info').offset().top : 0;
  let target =  $('.image-popup');
  let targetWidth = target.outerWidth();
  let removeSticky = ($(".rightDivArea").length)?$(".rightDivArea").outerHeight()-$(".rightDivArea").offset().top - 30: 0;
  $(window).scroll(function() 
  {           
      let scrollTop = $(this).scrollTop();
      if (scrollTop > fixImgTop && scrollTop < removeSticky){
          target.addClass('img-sticky').css({width: targetWidth});
      }
      else {
          target.removeClass('img-sticky').removeAttr("style");
      }
  });
}


function horizontalScroll(){
  var newWindowWidth = $(window).width();
  //if (newWindowWidth > 481) {
    var slider = '';
    if($('body').hasClass("template-pages-layout-siteOneCuratedPlpLayout")){      
       slider = $('.category_tiles')[1];
    }
    else{
       slider = document.querySelector('.category_tiles');
    }
    
    let isDown = false;
    let startX;
    let scrollLeft;
    if (slider) {
      slider.addEventListener('mousedown', (e) => {
        isDown = true;
        slider.classList.add('active');
        startX = e.pageX - slider.offsetLeft;
        scrollLeft = slider.scrollLeft;
      });
      slider.addEventListener('mouseleave', () => {
        isDown = false;
        slider.classList.remove('active');
      });
      slider.addEventListener('mouseup', () => {
        isDown = false;
        slider.classList.remove('active');
      });
      slider.addEventListener('mousemove', (e) => {
        if(!isDown) return;
        e.preventDefault();
        const x = e.pageX - slider.offsetLeft;
        const walk = (x - startX) * 3; //scroll-fast
        slider.scrollLeft = scrollLeft - walk;
        //console.log(walk);
      });
    }
  //}
}


$(".js-plp-qty").on("input", function(){
    ACC.product.validateShippingQty(this)
})

$(".plusQty, .minusQty").on("click", function(){
    if($(".pagename").val()=='Product Grid' || $(".pagename").val()== 'Search Results'){
    let productCode = this.id.split("_")[1];
    let inputElement = $(`#productPLPPostQty_${productCode}`);
     ACC.product.validateShippingQty(inputElement)
}
})