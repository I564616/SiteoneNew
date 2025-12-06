ACC.productDetail = {

    _autoload: [
        "initPageEvents",
        "bindVariantOptions",
        "bindCSPForCarousal",
		"bindAddtoList",
		"hideEmptyTabs",
		"AddReadMore",
		"regulatedLabel",
		"multipleUomItemSelector",
		"hardscapesStonePDPAlert",
		"landscapeNotePDPAlert",
		"pdpBulletPointLoad"
		
    ],

	BulkUomCode: '',
	varientProductQTY: '',
	plpProductQTY: '',
	plpCommonBtn: "false",
	plpMobileAddListCode: '',
	hideEmptyTabs: function(){
		$(".tab-pane").each(function(){ 
			if($(this).children().length == 0){
				var thisID = $(this).attr("id");
				var thisLink = $(".nav-tabs li a[href$='" + thisID + "']");
				if(thisLink.parent().hasClass('active')){
					thisLink.parent().next().addClass('active');
				}
				if($(this).hasClass('active')){
					$(this).removeClass('active');
					$(this).next().addClass('active');
				}
				thisLink.hide();
			}
		 });
	},
	
	regulatedLabel: function() {
		if($('.regulatedItemLabel').hasClass('hide'))
			{
			    $('.regulatedItemwithoutprice').show();
			    $('.regulatedItemwithprice').hide();
		    }
	},
	
	AddReadMore: function() {
        var maxLmt = 400;
        var readMoreTxt = " Read More";
        var readLessTxt = " Read Less";
        $(".addReadMore").each(function() {
            var allstr = $(this).html();
            if (allstr.length > maxLmt) {
                var firstSet = allstr.substring(0, maxLmt);
                var secdHalf = allstr.substring(maxLmt, allstr.length);
                var appendContent ="<div class=' bottom-overflow-fade'>" + firstSet+ "<span class=' SecSec'>" + secdHalf + "</span></div><br><button class='col-md-3 btn-secondary  readMore bottom-btn'>" + readMoreTxt + "<i class='downArrow'></i><br></button><button class='col-md-3 btn-secondary  readLess bottom-btn' >" + readLessTxt + "<i class='upArrow'></button>";
                $(this).html(appendContent);
                $('.bottom-overflow-fade').css('-webkit-mask-image', 'linear-gradient(to bottom, black 50%, transparent 100%)');
            }
        });
        $(document).on("click", ".readMore,.readLess", function() {
            $(this).closest(".addReadMore").toggleClass("showlesscontent showmorecontent");
        });
        $(document).on("click", ".readMore", function() {
            $('.bottom-overflow-fade').css('-webkit-mask-image', 'none');
        });
        $(document).on("click", ".readLess", function() {
            $('.bottom-overflow-fade').css('-webkit-mask-image', 'linear-gradient(to bottom, black 50%, transparent 100%)');
        });
    },

    checkQtySelector: function (self, mode) {
        var input = $(self).parents(".js-qty-selector").find(".js-qty-selector-input");
        var inputVal = parseInt(input.val());
        var max = input.data("max");
        var minusBtn = $(self).parents(".js-qty-selector").find(".js-qty-selector-minus");
        var plusBtn = $(self).parents(".js-qty-selector").find(".js-qty-selector-plus");

        $(self).parents(".js-qty-selector").find(".btn").removeAttr("disabled");

        if (mode == "minus") {
            if (inputVal != 1) {
                ACC.productDetail.updateQtyValue(self, inputVal - 1)
                if (inputVal - 1 == 1) {
                    minusBtn.attr("disabled", "disabled")
                }

            } else {
                minusBtn.attr("disabled", "disabled")
            }
        } else if (mode == "reset") {
            ACC.productDetail.updateQtyValue(self, 1)

        } else if (mode == "plus") {
            if(max == "FORCE_IN_STOCK") {
                ACC.productDetail.updateQtyValue(self, inputVal + 1)
            } else if (inputVal <= max) {
                ACC.productDetail.updateQtyValue(self, inputVal + 1)
                if (inputVal + 1 == max) {
                    plusBtn.attr("disabled", "disabled")
                }
            } else {
                plusBtn.attr("disabled", "disabled")
            }
        } else if (mode == "input") {
            if (inputVal == 1) {
                minusBtn.attr("disabled", "disabled")
            } else if(max == "FORCE_IN_STOCK" && inputVal > 0) {
                ACC.productDetail.updateQtyValue(self, inputVal)
            } else if (inputVal == max) {
                plusBtn.attr("disabled", "disabled")
            } else if (inputVal < 1) {
                ACC.productDetail.updateQtyValue(self, 1)
                minusBtn.attr("disabled", "disabled")
				ACC.productDetail.multipleUomTotalPriceUpdater()
            } else if (inputVal > max) {
                ACC.productDetail.updateQtyValue(self, max)
                plusBtn.attr("disabled", "disabled")
            }
        } else if (mode == "focusout") {
        	var reg = new RegExp('[^0-9]','g');
            if (reg.test(inputVal) || isNaN(inputVal)){
                $("#qtyError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>"+ACC.config.productDetail+"</font></div></div> ");
                ACC.productDetail.updateQtyValue(self, 1);
                minusBtn.attr("disabled", "disabled");
            } else if(inputVal >= max) {
                plusBtn.attr("disabled", "disabled");
            }
        }

    },

    updateQtyValue: function (self, value) {
        var input = $(self).parents(".js-qty-selector").find(".js-qty-selector-input");
        var addtocartQty = $(self).parents(".addtocart-component").find("#addToCartForm").find(".js-qty-selector-input");
        var configureQty = $(self).parents(".addtocart-component").find("#configureForm").find(".js-qty-selector-input");
        input.val(value);
        addtocartQty.val(value);
        configureQty.val(value);
        var addtocartQtyVariant = $(self).parents(".qty-atc-wrapper").find("#addToCartForm").find(".js-qty-selector-input");
        addtocartQtyVariant.val(value);
    },
    
    plpMobileAddList: function (productCode) {
			ACC.productDetail.plpCommonBtn = "false";
			ACC.productDetail.plpMobileAddListCode = productCode;
			$('.pdplistpopupoption').removeClass('selected');
			$(".pdplistpopup1").removeClass('hidden');
			$(".pdp-newlist-popup-error-text").hide();
			$(".pdp-emptynewlist-popup-error-text").hide();
			$('.listpopupsavebtn').removeClass("disabled");
			$('.createnewlistbtn').removeClass("disabled");
			$(".pdp-atl-popup").colorbox.resize();
			if($('.loginstatus').val()!=''){
				ACC.colorbox.open("", {
					html:  $("#listpopup").html(),
					width: "500px",
					className: "pdp-atl-popup",
					onComplete:function(){
					$(document).on("click",".listpopupclosebtn", function(e){
						ACC.colorbox.close();
					});
					}
				})
			}
    },

    initPageEvents: function () {
		
		$(document).on("click", '.js-qty-selector .js-qty-selector-minus', function () {
            ACC.productDetail.checkQtySelector(this, "minus");
        })

        $(document).on("click", '.js-qty-selector .js-qty-selector-plus', function () {
            ACC.productDetail.checkQtySelector(this, "plus");
        })

        $(document).on("keydown", '.js-qty-selector .js-qty-selector-input', function (e) {

            if (($(this).val() != " " &&  ((e.which >= 48 && e.which <= 57 ) || (e.which >= 96 && e.which <= 105 ))  ) || e.which == 8 || e.which == 46 || e.which == 37 || e.which == 39 || e.which == 9) {
            }
            else if (e.which == 38) {
                ACC.productDetail.checkQtySelector(this, "plus");
            }
            else if (e.which == 40) {
                ACC.productDetail.checkQtySelector(this, "minus");
            }
            else {
                e.preventDefault();
            }
            $('#qtyError').html('');
        })

        $(document).on("keyup", '.js-qty-selector .js-qty-selector-input', function (e) {
            ACC.productDetail.checkQtySelector(this, "input");
            ACC.productDetail.updateQtyValue(this, $(this).val());

        })
        
        $(document).on("focusout", '.js-qty-selector .js-qty-selector-input', function (e) {
            ACC.productDetail.checkQtySelector(this, "focusout");
            ACC.productDetail.updateQtyValue(this, $(this).val());
        })
        
                  $('#sellableUoms').show();
        if($("#sellableUoms").length!=0)
        	{
        		$('#optionAddToList').hide();
        		$('#showOptionAddToList').show();
        	}
        else
        	{
        	$('#optionAddToList').show();
    		$('#showOptionAddToList').hide();
        	}
                $('#orderingAccountMsg').hide();
       
		ACC.productDetailBulkUomCode = $('.bulk-uom').text().trim().slice(0,20);
        $(document).on("change", ".js-uom-selector-input", function (e) 
        {
            var basePrice=$("#listPrice").val();
            var customerPrice=$("#customerPrice").val();
            if(basePrice != "" || customerPrice != "" )
             {
                
            $('#inventoryUomId').val($(this).val());
            $('#inventoryUOMIDVal').val($(this).val());
            
            if($(this).val() == "select"){
                window.location.reload(); 
                  $('#sellableUoms').show();
                  if($("#sellableUoms").length!=0)
              	{
              		$('#optionAddToList').hide();
              		$('#showOptionAddToList').show();
              	}
              else
              	{
              	$('#optionAddToList').show();
          		$('#showOptionAddToList').hide();
              	}
                  $('#showAddtoCartUom').hide();
                  $('#orderingAccountMsg').hide();
            } else {
                var productCode = $('#productcode').val(); 
				var productUom = $('.bulk-uom').text().trim().slice(0,20);
                var formatter = new Intl.NumberFormat('en-US', {
                      style: 'currency',
                      currency: 'USD',
                      minimumFractionDigits: 2,
                 });
                var inventoryMultiplier= $(this).find(":selected").data("inventory");
				        var uomMeasure = $(this).find(":selected").text();
                   $('#showAddtoCartUom').show();
                   $('#orderingAccountMsg').show();
                   $('#sellableUoms').hide(); 
                   $('#optionAddToList').show();
           		$('#showOptionAddToList').hide();
				url= ACC.config.encodedContextPath +'/p/getUOMPrice';
                    $.ajax({
                        url: url,
                        cache: false,
                        type: "get",
                        dataType: 'json',
                        contentType: 'application/json',
                        data : 
                        {
                            "productCode": productCode,
                            "inventoryMultiplier":inventoryMultiplier
                        },
                       success: function (productData) 
                       {
                          if(null != productData)
                          {
                              if(productData.customerPrice != null && $("#uomPrice > .yourPrice" ).length)
                              {
									$("#uomPrice  .siteOneListPrice").nextAll(".price_variant").addClass("hide");
									
									
									$("#uomPrice  .siteOneListPrice" ).next().html('<del>'+formatter.format(productData.price.value) +' / '+uomMeasure +'</del>');
									
									if(inventoryMultiplier > 1){
										$("#uomPrice  .yourPriceValue" ).html('<div class="row"><div class="col-md-10 col-sm-12 bulk-price">'+formatter.format(productData.customerPrice.value)+' / '+uomMeasure +'</div><div class="col-md-2 col-sm-12 bulk-uom">'+ACC.productDetailBulkUomCode+'</div></div>');
									}else{
										productUom = ''
										$("#uomPrice  .yourPriceValue" ).html('<div class="csp-uom-change">'+formatter.format(productData.customerPrice.value)+' / '+uomMeasure +'</div>');
									}
									$(".list-uom-change").html()
									
									                     
                              }
                              else
                              {
								
								$("#uomPrice  .siteOneListPrice").nextAll(".price_variant").addClass("hide");
								if($(".PdpUomListPrice").length===0){
							    $("#uomPrice  .siteOneListPrice" ).after('<div class="PdpUomListPrice row">');
								}
								if(inventoryMultiplier > 1){
									$(".PdpUomListPrice").html('<div class="col-md-10 col-sm-12 bulk-price-guest">'+formatter.format(productData.price.value) +' / '+uomMeasure + '  '+ '</div><div class="col-md-2 col-sm-12 bulk-uom-guest">'+productUom+'</div>'+'</div>')
								}else{
									productUom = ''
									$(".PdpUomListPrice").html(formatter.format(productData.price.value) +' / '+uomMeasure+'</div>')
								}	
                              }
                          }
                       },
                    });
					  $('#sellableUoms').hide(); 
                      $('#optionAddToList').show();
              		  $('#showOptionAddToList').hide();
                      $('#showAddtoCartUom').show();
                    
            }
                }

			else {
					  $('#sellableUoms').hide(); 
                      $('#optionAddToList').show();
              		  $('#showOptionAddToList').hide();
                      $('#showAddtoCartUom').show();
				}
    }),
        

        $("#Size").change(function () {
            changeOnVariantOptionSelection($("#Size option:selected"));
        });

        $("#variant").change(function () {
            changeOnVariantOptionSelection($("#variant option:selected"));
        });

        $(".selectPriority").change(function () {
            window.location.href = $(this[this.selectedIndex]).val();
        });

        function changeOnVariantOptionSelection(optionSelected) {
            window.location.href = optionSelected.attr('value');
        }
    },

    bindVariantOptions: function () {
        ACC.productDetail.bindCurrentStyle();
        ACC.productDetail.bindCurrentSize();
        ACC.productDetail.bindCurrentType();
    },
    
   
    
    bindCSPForCarousal: function() 
    {
        
                
   $('a[id^="customerPrice"]').click(function() 
      {
        var code = $(this).data("code");
        var IsBuyable=$(this).data('isBuyable');
        //var index = $(this).data('index');
        var phoneNumber = $("#sessionStorePhoneNumber").val();
        loading.start();
        $.ajax({
          url : ACC.config.encodedContextPath + "/p/salecustomerprice?productCode="+code+"&quantity=1",
          method:"POST",
          success : function(siteOneCspWithSaleResponse)
          {
              //console.log(siteOneCspWithSaleResponse.salePrice);
            var siteOneCspResponse = siteOneCspWithSaleResponse.cspResponse;
            if(siteOneCspResponse != null && siteOneCspResponse.isSuccess)
            {
                $("#cspSection"+code).show();
                if(siteOneCspWithSaleResponse.salePrice != '$0.00') 
                {
                    if(IsBuyable)
                    {
                     $("#carosuelPageAddToCart_"+code).attr("disabled", false);
                    }
                
                }
                if(siteOneCspWithSaleResponse.salePrice != null) {
                    var salePriceString=siteOneCspWithSaleResponse.salePrice;
                    var salePrice=parseInt(salePriceString).toFixed(2);
                    //$("#cspSection"+code).show();
                    $("#customerPrice"+code).hide();
                    //var salePrice=siteOneCspWithSaleResponse.salePrice.toFixed(2);
                    $("#csp"+code).html("$"+ salePrice +"<br>"+ "<del>" + siteOneCspResponse.price.formattedValue + "</del>")
                } else {
                    //alert(siteOneCspResponse.price.formattedValue);
                    $("#customerPrice"+code).hide();
                    $("#csp"+code).html(siteOneCspResponse.price.formattedValue);
                }
                
                
                
             /* $("#customerSpecificPriceLink"+productCode).hide();
              $("#customerSpecificPriceSection"+productCode).show();
              $("#customerSpecificPrice"+productCode).html(siteOneCspResponse.price.formattedValue);
              $("#customerSpecificPriceValue"+index).val(siteOneCspResponse.price.value);
              ACC.promotions.calculatePromotionPrice($("#productPromotion"+index), productCode);
              $("#listPageAddToCart_"+productCode).attr("disabled", false);
              $("#priceUnavailabilityMsg"+index).addClass('hidden');*/
            }
            else
            {
                $("#customerPrice"+code).hide();
                $("#customerPriceError"+code).html("Price not available. Please contact store <a class='tel-phone' href='tel:"+phoneNumber+"'>"+phoneNumber+"</a>");
                $("#priceUnavailabilityMsg"+code).addClass('hidden');
                $("#priceUnavailable"+code).addClass('hidden');
              /*if($('#customerSpecificPrice' + productCode == ""))
              {
                  $("#basePrice"+index).show();
                    $('#salePrice' + index).show();
              }else{
                  $("#basePrice"+index).hide();
                    $('#salePrice' + index).hide();
              }
                     
                    $("#cspError"+index).html("Price not available. Please contact store " + phoneNumber);
                
                    $("#priceUnavailabilityMsg"+index).addClass('hidden');*/
            }
          }
        }).always(function() {
          loading.stop();
        });
          
      }); 
    },
    

    bindCurrentStyle: function () {
        var currentStyle = $("#currentStyleValue").data("styleValue");
        var styleSpan = $(".styleName");
        if (currentStyle != null) {
            styleSpan.text(": " + currentStyle);
        }
    },

    bindCurrentSize: function () {
        var currentSize = $("#currentSizeValue").data("sizeValue");
        var sizeSpan = $(".sizeName");
        if (currentSize != null) {
            sizeSpan.text(": " + currentSize);
        }
    },

    bindCurrentType: function () {
        var currentSize = $("#currentTypeValue").data("typeValue");
        var sizeSpan = $(".typeName");
        if (currentSize != null) {
            sizeSpan.text(": " + currentSize);
        }
    },
    
    reAlignColorBoxToPage:function(){
        var window_height = $(window)[0].innerHeight;
        var top_position = 0;
         $.colorbox.resize();
         var colorbox_height = $('#colorbox').height();
         if(window_height > colorbox_height) {
            top_position = (window_height - colorbox_height) / 2;
        }
         $('#colorbox').css({'top':top_position, 'position':'fixed'});
    },
popupHtmlCreateNewList: function(successmsg, wishlistCode){
	let htmlCode = "<div class='PopupBox'><h1 class='headline'>"+successmsg+"</h1><div class='row marginTop40 mob-list-confo'><div class='col-md-6 col-xs-12'><input class='btn btn-primary btn-block successListCreation' type='button' value='"+ACC.config.continueShopping+"'></div><div class='col-md-6 col-xs-12'><a href='"+ACC.config.encodedContextPath+'/savedList/listDetails?code=' + wishlistCode +"' class='btn btn-block btn-default'>"+ACC.config.viewlistBtn+"</a></div></div></div>";
	return htmlCode;
},
popupHtmlExistingList: function(successmsg, wishlistCode){
	let htmlCode = "<div class='PopupBox'><h1 class='headline'>"+successmsg+"</h1><div class='row marginTop40 mob-list-confo'><div class='col-md-6 col-xs-12'><input class='btn btn-primary btn-block successListCreation' type='button' value='"+ACC.config.continueShopping+"'></div><div class='col-md-6 col-xs-12'><a href='"+ACC.config.encodedContextPath+'/savedList/listDetails?code=' + wishlistCode +"' class='btn btn-block btn-default'>"+ACC.config.viewlistBtn+"</a></div></div></div>";
	return htmlCode;
},
pdppopupHtmlExistingList: function(successmsg, wishlistCode){
	let htmlCode = "<div class='PopupBox'><h1 class='headline'>"+successmsg+"</h1><div class='row marginTop40 mob-list-confo'><div class='col-md-6 col-xs-12'><input class='btn btn-primary btn-block successListCreation' type='button' value='"+ACC.config.continueShopping+"'></div><div class='col-md-6 col-xs-12'><a href='"+ACC.config.encodedContextPath+'/savedList/listDetails?code=' + wishlistCode +"' class='btn btn-block btn-default'>"+ACC.config.viewlistBtn+"</a></div></div></div>";
	return htmlCode;
},
    bindAddtoList:function(){
    	
    	function validateProductAndQty(isEmptyProduct,isInvalidqty){
    		if(isEmptyProduct){
      	    	$("#empty_code").show()
      	    }
      	    else{
      	    	$("#empty_code").hide();
      	    }
      	    
      	    if(isInvalidqty){
      		  $("#empty_quantity").show();
    	    }
    	    else{
    	    	$("#empty_quantity").hide();
    	    }
    	}
    	

  	$(".editList").click(function() {
  	    var size = $("#listSize").val();
  	     console.log(size);
  	    if (size >= 1) {
  	    $(this).next(".fetchWishList").toggle();
  	    }

  	    });
		
  	$('.qtyChanged').on('change', function() {

  	    var quantity = $(this).val();
  	    console.log("qty"+quantity);
  	    var productCode = $(this).siblings("#productCodeQtyChange").val();
  	    console.log("proCode"+productCode);
  	    var listCode = $("#listCodeQtyChange").val();
  	    console.log("listCode"+listCode);
  	     $.ajax({
  	          type: 'POST',
  	          url: ACC.config.encodedContextPath + "/savedList/add",
  	          datatype: "json",
  	          data: {
  	              productCode: productCode,
  	              wishListCode: listCode,
  	              quantity:quantity,
  	              prodQtyFlag: true
  	          },
  	          success: function(result) {
  	              window.location.reload(true);
  	          },
  	          error: function(xhr, ajaxOptions, thrownError) {
  	              console.log('data failure');
  	          }
  	      }); 
  	});

  	$('.moveToSaveList').on('click', function() {
  	       var moveWishList=$(this).attr("id");
  	       var prodCode=$(this).siblings('#addproductCode').val();
  	       var quantity=$(this).siblings('#list_qty_').val();
  	       var inventryUOM=$(this).parents(".saved-list-sec").find("#inventoryUomId").val();
  	       console.log(quantity);
  	       var savedListName=$(this).siblings('#savedListName').val();
  	       digitalData={
    					 	event: 'add product to list',
    						item: $(this).siblings('#productID').val(),
    						listName:$(this)[0].outerText
    				   }				
    	   try {
    					_satellite.track('productAddedToList');
    		   }catch(e){} 
  	       $.ajax({
  	              type: 'POST',
  	              url: ACC.config.encodedContextPath + "/savedList/add",
  	              datatype: "json",
  	              data: {
  	                  productCode: prodCode,
  	                  wishListCode: moveWishList,
  	                  quantity:quantity,
  	                  inventoryUOMId :inventryUOM
  	              },
  	              success: function(result) {
  	                  
  	                  $.ajax({
  	                      type: 'POST',
  	                      url: ACC.config.encodedContextPath + "/savedList/moveSavedList",
  	                      datatype: "json",
  	                      data: {
  	                          productCode: prodCode,
  	                          wishListCode: savedListName,
  	                          quantity:quantity
  	                      },
  	                      success: function(result) {
  	                    	ACC.colorbox.open("", {
  		 	                    html: ACC.productDetail.popupHtmlExistingList(ACC.config.listaddedMsg,moveWishList),
  		 	                    width: "500px",
  		 	                    onComplete: function(){
  		 	                        $('#colorbox').removeClass('add-to-list-box');
  		 	                        $('#colorbox').addClass('item-add-list-box');
  		 	                        $(document).on("click",".successListCreation", function(e){
  		 	                            ACC.colorbox.close();
  		 	                        });
  		 	                    },
  		 	                    onClosed: function() {
  		 	                        $('#colorbox').removeClass('item-add-list-box');
  		 	                        $('body').css("overflow-y","auto");
 		 	                        window.location.reload(true);
  		 	                    } 
  		 	                });
  	                          
  	                      },
  	                      error: function(xhr, ajaxOptions, thrownError) {
  	                          console.log('data failure');
  	                      }
  	                  }); 
  	              },
  	              error: function(xhr, ajaxOptions, thrownError) {
  	                  console.log('data failure');
  	              }
  	          });
  	         
  	    });


  	$(document).on('click', '.addToWhishlistpopupcart', function() {
  	    var wishlistCode = $("#cboxContent .popupWishlist").val();
  	    var productCode = $("#productCode").val();
  	    var quantity=$("#qty").val();
  	    var cartuomId= $(".uomId_popup").val();

  	    $.ajax({
  	        type: 'POST',
  	        url: ACC.config.encodedContextPath + "/savedList/add",
	        datatype: "json",
  	        data: {
  	            productCode: productCode,
  	            wishListCode: wishlistCode,
  	            quantity:quantity,
                inventoryUOMId :cartuomId
  	        },
  	        success: function(result) {
  	            ACC.colorbox.open("", {
  	               html: ACC.productDetail.popupHtmlExistingList(ACC.config.listaddedMsg, wishlistCode),
  	                width: "500px",
  	                onComplete: function(){
  	                    $('#colorbox').removeClass('add-assembly-to-list-box');
  	                    $('#colorbox').removeClass('add-item-to-list-box');
  	                    $('#colorbox').addClass('item-add-list-box');
  	                    $('body').css("overflow-y", "hidden");
  	                    $(document).on("click", ".successListCreation", function(){
  	                        ACC.colorbox.close();
  	                     });
  	                },
  	                onClosed: function() {
  	                    $('body').css("overflow-y","auto");
  	                    $('#colorbox').removeClass('item-add-list-box');
  	                }
  	            }); 
  	        },
  	        error: function(xhr, ajaxOptions, thrownError) {
  	            console.log('data failure');
  	        }
  	    });
  	    
  	    
  	    });

	$(document).on("click", "#pdpPromotion", function (e){

    	var productCode = $("#productcode").val();
         var isPdpPromotion=false;
if($(this).prop("checked") == true){
isPdpPromotion=true;
}
$("#isCouponEnabled").val(isPdpPromotion);
$("#promoProductCode").val(productCode);

});
function addingOptionToList(wishlistCode){
	var wishlistCode = $('.pdplistpopupoption.selected').data('value');
	var wishListname = ($('.pdplistpopupoption.selected').text());
	var productCode = ($('.pdplistpopupoption.selected').data('productcode'));
	var listQty;
	var inventryUOM;
	if($('.product-variant-table').length > 0){
		 listQty=ACC.productDetail.varientProductQTY;
		 inventryUOM = '';
	}
	else{
		 listQty=getListQuantity(this);
		 inventryUOM = $("#inventoryUOMIDVal").val();
	}
	ACC.productDetail.pdpAddListAjax(wishlistCode, productCode, inventryUOM, wishListname,listQty);
};

$(document).on("click", ".pdpAddtoListPopupBtn", function (e){
	ACC.productDetail.plpCommonBtn = "false";
	ACC.productDetail.plpMobileAddListCode = '';
	$('.pdplistpopupoption').removeClass('selected');
	$(".pdplistpopup1").removeClass('hidden');
	$(".pdp-newlist-popup-error-text").hide();
	$(".pdp-emptynewlist-popup-error-text").hide();
	$('.listpopupsavebtn').removeClass("disabled");
	$('.createnewlistbtn').removeClass("disabled");
	$(".pdp-atl-popup").colorbox.resize();
	if($('.loginstatus').val()!=''){
		ACC.colorbox.open("", {
			html:  $("#listpopup").html(),
			width: "500px",
			className: "pdp-atl-popup",
			onComplete:function(){
			$(document).on("click",".listpopupclosebtn", function(e){
				ACC.colorbox.close();
			});
			}
		})
	}
});

$(document).on("click", ".pdpAddtoListCommonBtn", function (e){
	ACC.productDetail.plpCommonBtn = "true";
	ACC.productDetail.plpMobileAddListCode = '';
	$('.pdplistpopupoption').removeClass('selected');
	$(".pdplistpopup1").removeClass('hidden');
	$(".pdp-newlist-popup-error-text").hide();
	$(".pdp-emptynewlist-popup-error-text").hide();
	$('.listpopupsavebtn').removeClass("disabled");
	$('.createnewlistbtn').removeClass("disabled");
	$(".pdp-atl-popup").colorbox.resize();
	if($('.loginstatus').val()!=''){
		ACC.colorbox.open("", {
			html:  $("#listpopup").html(),
			width: "500px",
			className: "pdp-atl-popup",
			onComplete:function(){
			$(document).on("click",".listpopupclosebtn", function(e){
				ACC.colorbox.close();
			});
			}
		})
	}
});

$(document).on("click",".listpopupsavebtn", function(e){
	var listpopupsavebtn = $(this);
	var pdppopuplistname=$("#pdppopupinput").val().trim();
	var optionList = '';
	var exit = true;
	var id;
	if(pdppopuplistname !='' && pdppopuplistname != null){
		$.ajax({
			type: 'POST',
			url: ACC.config.encodedContextPath + "/savedList/createWishlistAndFetch",
			datatype: "json",
			data: {
				wishListName: pdppopuplistname
			},
			success: function (result) {
				console.log(result);
				if (result.isDuplicate == true) {
					$('.pdp-newlist-popup-error-text').show();
					$('.listpopupsavebtn').addClass("disabled");
					$('.createnewlistbtn').addClass("disabled");
					$('input.createnewlistinput').keyup(function (e) {
						$(".pdp-newlist-popup-error-text").hide();
						$(".pdp-atl-popup").colorbox.resize();
						$('.listpopupsavebtn').removeClass("disabled");
						$('.createnewlistbtn').removeClass("disabled");
					});
					$(".pdp-atl-popup").colorbox.resize();
				}
				else {
					$('.pdp-newlist-popup-error-text').hide();
					$('.listpopupsavebtn').removeClass("disabled");
					$('.createnewlistbtn').removeClass("disabled");
					$(".pdp-atl-popup").colorbox.resize();
					if (result.allWishlist.length > 0) {
						$('.popupsavedlistoptions').parent().removeClass('hidden');
					}
					var item = "";
					if($('.product-variant-table').length > 0){
						productitemNumber = listpopupsavebtn.data('productcode');
					}
					else{
						productitemNumber = $("#productitemNumber").attr('value');
					}
					if (ACC.savedlist.isRecommendedListPage) {
						let recommendedCategoryName = $(".recommendCatName").val();
						let btnName = $(".reccomendBtnName").val();
						result.allWishlist.forEach(addtolistpopup => {
							item += '<div class="recommendedlistname wish-item hidden-xs" buttonname="' + btnName + '" categoryname="' + recommendedCategoryName + '" id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" onclick="ACC.savedlist.recCopyToMyListPopup(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
							item += '<div class="recommendedlistmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.savedlist.recCopyToMyList(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
						})
						$(".popupoptionlistitem").empty().append(item);
						var wishlistCode = $(".recommendedlistname:first").data('value');
						ACC.productDetail.pdpAddListAjax(wishlistCode)
						$(".createnewlistinput").val("");
					} else {
						if($('.product-variant-table').length > 0){
							result.allWishlist.forEach(addtolistpopup => {
								if(exit){
									id =  addtolistpopup.code + productitemNumber;
									exit = false;
								}
								item += '<div class="pdplistpopupoption hidden-xs" id="' + addtolistpopup.code +  productitemNumber +'" data-value="' + addtolistpopup.code + '" data-productcode="' + productitemNumber + '">' + addtolistpopup.name + '</div>'
								item += '<div class="pdplistpopupoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.productDetail.triggerpdplistpopupoption(\'' + addtolistpopup.code +  productitemNumber + '\')">' + addtolistpopup.name + '</div>'
							});
						}
						else{
							result.allWishlist.forEach(addtolistpopup => {
								if(exit){
									id =  addtolistpopup.code;
									exit = false;
								}
								item += '<div class="pdplistpopupoption hidden-xs" id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" data-productcode="' + productitemNumber + '">' + addtolistpopup.name + '</div>'
								item += '<div class="pdplistpopupoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.productDetail.triggerpdplistpopupoption(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
							});
						}
						console.log(item);		
						if(productitemNumber) productitemNumber = productitemNumber.replace(".","\\.");		
						optionList =  '.popupoptionlistitem' + productitemNumber;
						if($('.product-variant-table').length > 0){
							$(optionList).empty().append(item);
							var option = document.getElementById(id);
							option.click();
						}
						else{
							$('.popupoptionlistitem').empty().append(item);
							$(".pdplistpopupoption:first").addClass('selected');
							addingOptionToList();
						}				
						$('.createnewlistinput').val('');
						$(".pdp-atl-popup").colorbox.resize();
					}
				}
			},
			error: function (xhr, ajaxOptions, thrownError) {
				console.log('data failure');
			}
		});
		}
		else{
			$('.pdp-emptynewlist-popup-error-text').show();
			$('.listpopupsavebtn').addClass("disabled");
			$('.createnewlistbtn').addClass("disabled");
			$('input.createnewlistinput').keyup(function(e){
				$(".pdp-emptynewlist-popup-error-text").hide();
				$(".pdp-atl-popup").colorbox.resize();
				$('.listpopupsavebtn').removeClass("disabled");
				$('.createnewlistbtn').removeClass("disabled");
			});
			$(".pdp-atl-popup").colorbox.resize();		   
			$(".pdplistpopupoption:first").addClass('selected');
		}
	});

$(document).on("change",".createnewlistinput", function(e){
	$("#pdppopupinput").val($(this).val())
})
function createNewListPDP(pdppopuplistname){
	var productCode = $("#productcode").attr('value');
		var inventryUOM = $("#inventoryUOMIDVal").val();
		var qty = $("#qty").val();
  		    if(qty == null || qty == "undefined"){
  		    	qty = 1;
  		    }
		$(".pdplistnoname").addClass("hidden");
		$.ajax({
			type: 'POST',
			url: ACC.config.encodedContextPath + "/savedList/createWishlist",
			datatype: "json",
			data: {
				productCode:productCode,
				qty : qty,
				wishListName: pdppopuplistname,
			  	inventoryUom: inventryUOM

			},
			success: function(result) {
				$(".pdplistpopupoption:first").val(pdppopuplistname);
				$(".pdplistpopupoption:first").addClass('selected');
				addingOptionToList(result.wishListCode);
			},
			error: function(xhr, ajaxOptions, thrownError) {
				console.log('data failure');
			}
		})
}
$(document).on("click",".createnewlistbtn", function(e){
	var createnewlistbtn = $(this);
	var pdppopuplistname=$("#pdppopupinput").val().trim();
	var optionList = '';
	if(pdppopuplistname !='' && pdppopuplistname != null){
	$.ajax({
		type: 'POST',
		url: ACC.config.encodedContextPath + "/savedList/createWishlistAndFetch",
		datatype: "json",
		data: {
			wishListName: pdppopuplistname
		},
		success: function(result) {
			if(result.isDuplicate==true){
				$('.pdp-newlist-popup-error-text').show();
				$('.listpopupsavebtn').addClass("disabled");
				$('.createnewlistbtn').addClass("disabled");
				$('input.createnewlistinput').keyup(function(e){
					$(".pdp-newlist-popup-error-text").hide();
					$(".pdp-atl-popup").colorbox.resize();
					$('.listpopupsavebtn').removeClass("disabled");
					$('.createnewlistbtn').removeClass("disabled");
			   });
			   $(".pdp-atl-popup").colorbox.resize(); 
			}
			else{
				$('.pdp-newlist-popup-error-text').hide();
				$(".pdp-atl-popup").colorbox.resize(); 
				$('.listpopupsavebtn').removeClass("disabled");
				$('.createnewlistbtn').removeClass("disabled");
				if(result.allWishlist.length>0){
					$('.popupsavedlistoptions').parent().removeClass('hidden');
				} 
				var item="";
				if($('.product-variant-table').length > 0){
					productitemNumber = createnewlistbtn.data('productcode');
				}
				else{
					productitemNumber = $("#productitemNumber").attr('value');
				}
				//RecommendedList flow
				if (ACC.savedlist.isRecommendedListPage) {
					let recommendedCategoryName = $(".recommendCatName").val();
					let btnName = $(".reccomendBtnName").val();
					result.allWishlist.forEach(addtolistpopup => {
						item += '<div class="recommendedlistname wish-item hidden-xs" buttonname="' + btnName + '" categoryname="' + recommendedCategoryName + '" id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" onclick="ACC.savedlist.recCopyToMyListPopup(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
						item += '<div class="recommendedlistmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.savedlist.recCopyToMyList(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
					})
				}
				else if ($('.page-order').length > 0) {
					result.allWishlist.forEach(addtolistpopup => {
						item += '<button type="button" class="list-group-item  Ordlistoption font-size-14 f-w-400 wish-item text-default m-b-10-xs  hidden-xs"  id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '"onclick="ACC.order.moveItemsToList(\'' + addtolistpopup.code + '\')">' + addtolistpopup.name + '</button>'
						item += '<button type="button" class="list-group-item Ordlistoptionmobile hidden-md hidden-lg hidden-sm" id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" onclick="ACC.order.moveItemsToList(\'' + addtolistpopup.code + '\')">' + addtolistpopup.name + '</button>'
					});
				}
				else {
					if($('.product-variant-table').length > 0){
						result.allWishlist.forEach(addtolistpopup => {
							item += '<div class="pdplistpopupoption hidden-xs" id="' + addtolistpopup.code + productitemNumber + '" data-value="' + addtolistpopup.code + '" data-productcode="' + productitemNumber + '">' + addtolistpopup.name + '</div>'
							item += '<div class="pdplistpopupoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.productDetail.triggerpdplistpopupoption(\'' + addtolistpopup.code + productitemNumber + '\')">' + addtolistpopup.name + '</div>'
						});
					}
					else{
						result.allWishlist.forEach(addtolistpopup => {
							item += '<div class="pdplistpopupoption hidden-xs" id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" data-productcode="' + productitemNumber + '">' + addtolistpopup.name + '</div>'
							item += '<div class="pdplistpopupoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.productDetail.triggerpdplistpopupoption(' + addtolistpopup.code + ')">' + addtolistpopup.name + '</div>'
						});
					}		
				}
				if(productitemNumber) productitemNumber = productitemNumber.replace(".","\\.");
				optionList =  '.popupoptionlistitem' + productitemNumber;
				if($('.product-variant-table').length > 0){
					$(optionList).empty().append(item);
				}
				else{
					$('.popupoptionlistitem').empty().append(item);
				}						
				$('.createnewlistinput').val('');
				$(".pdp-atl-popup").colorbox.resize(); 
			}
			
		},
		error: function(xhr, ajaxOptions, thrownError) {
			console.log('data failure');
		}
	

	})
}else{
	$('.pdp-emptynewlist-popup-error-text').show();
				$('.listpopupsavebtn').addClass("disabled");
				$('.createnewlistbtn').addClass("disabled");
				$('input.createnewlistinput').keyup(function(e){
					$(".pdp-emptynewlist-popup-error-text").hide();
					$(".pdp-atl-popup").colorbox.resize();
					$('.listpopupsavebtn').removeClass("disabled");
				$('.createnewlistbtn').removeClass("disabled");
			   });
			   $(".pdp-atl-popup").colorbox.resize({innerWidth: "362px"}); 
}
});

$(document).on("click", ".pdplistpopupoption", function (e){
	$('.pdplistpopupoption').removeClass('selected');
	$(this).addClass('selected');
	addingOptionToList();
});

$(document).on("click", "#pdpPromotionVariant", function (e){

    	var productCode = $(this).closest(".variant-promotion").find("#productcodevariant").val();
         var isPdpPromotion=false;
if($(this).prop("checked") == true){
isPdpPromotion=true;
}
$(this).closest(".product-variant-table__values").find("#isCouponEnabled").val(isPdpPromotion);
$(this).closest(".product-variant-table__values").find("#promoProductCode").val(productCode);


});
  	    $(document).on('click', '.addAssemblyToWishListpopup', function(event) {
  	        $("#colorbox").removeClass("add-assembly-to-list-box");
  	        var wishlistCode = $("#cboxContent #popupAddToAssembly").val();
  	        var productCodes = $(".productCodes").val();
  	      

  	        console.log(wishlistCode+" "+productCodes);
  	        $.ajax({
  	            type: 'POST',
  	            url: ACC.config.encodedContextPath + "/assembly/addAssemblyToList",
  	            datatype: "json",
  	            data: {
  	                productCodes: productCodes,
  	                wishListCode: wishlistCode
  	                
  	            },
  	            success: function(result) {
  	                
  	                
  	                ACC.colorbox.open("", {
	 					html: ACC.productDetail.popupHtmlExistingList(result.message,wishlistCode),
  	                    width: "500px",
  	                    onComplete: function(){
  	                        $('#colorbox').addClass('item-add-list-box');
  	                        $('body').css("overflow-y", "hidden");
  	                        $(document).on("click", ".successListCreation", function(){
  	                            ACC.colorbox.close();
  	                         });
  	                    },
						  onClosed: function() {
							$('#colorbox').removeClass('item-add-list-box');
							$('body').css("overflow-y","auto");
							$.cookie("quantity", null);
							$.cookie("prodCode", null);
							$.cookie("inventryUOM", null);
							$.cookie("cartEntry", null);
						}
  	                    
  	                });
  	            
  	                
  	               
  	            },
  	            error: function(xhr, ajaxOptions, thrownError) {
  	                console.log('data failure');
  	            }
  	        });
  	        
  	        
			  });
			  

		function addListAjax(wishlistCode, productCode, inventryUOM,wishListname, quantity){
			
			if(quantity == null || quantity == undefined){
				quantity=1;
			}
			if ($('.loginstatus').val() != ''){
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + "/savedList/add",
				datatype: "json",
				data: {
					productCode: productCode,
					wishListCode: wishlistCode,
					quantity:quantity,
					inventoryUOMId :inventryUOM
				},
				success: function(result) {
					ACC.colorbox.open("", {
						html:  ACC.productDetail.popupHtmlExistingList(ACC.config.listaddedMsg, wishlistCode),
						width: "500px",
						onComplete: function(){
							$('#colorbox').removeClass('add-assembly-to-list-box');
							$('#colorbox').removeClass('add-item-to-list-box');
							$('#colorbox').addClass('item-add-list-box');
							$('body').css("overflow-y", "hidden");
							if($('body').hasClass("template-pages-layout-siteOneCuratedPlpLayout")){
								digitalData.eventData={
									linktype: "product-grid",
									linkName:"add-to-list",
									onClickPageName: $(".pagename").val() 
								   }
							}else{
								digitalData.eventData={
									linktype: "add-to-list",
									linkName:wishListname,
									onClickPageName: $(".siteonepagename").val() 
								}
							}
							try{
								_satellite.track('linkClicks');
							}
							catch(err){}
							$(document).on("click", ".successListCreation", function(){
								ACC.colorbox.close();
								});
						},
						onClosed: function() {
							$('#colorbox').removeClass('item-add-list-box');
							$('body').css("overflow-y","auto");
							$.cookie("quantity", null);
							$.cookie("prodCode", null);
							$.cookie("inventryUOM", null);
							$.cookie("cartEntry", null);
						}
					});					   
				},
				error: function(xhr, ajaxOptions, thrownError) {
					console.log('data failure');
				}
			});
		  }
		}
  	    
		function getListQuantity(elem){
			if($('.product-variant-table').length > 0) {
				var qty = $(elem).parents('.product-variant-table__values').find('.pdp-variant-qty').val();
				return qty;
			}
			
			if($("body").hasClass("page-productDetails")){
				return $(".js-pdp-add-to-cart").val()
			}
			
			else if($("body").hasClass("page-accountOrdersPage")){
				return $(elem).parents(".bia-table-row").find("#bia-qty").val();
			}
			else {
				return $(elem).closest(".product-item-box").find(".js-plp-qty").val();
			}
		}
		
  	    $(document).on('click', '.addToWhishlistpopup', function(event) {
  	        var wishlistCode = $("#cboxContent .popupWishlist").val();
  	        var productCode = $("#productCode").val();
  	        var inventryUOM = $("#inventoryUOMIDVal").val();
  	        
  		    if(productCode == null || productCode == undefined){
  		    	var productcodeid = 'wishlist_productCode'+$(this).data('productcode');
  		    	productCode = $('#'+productcodeid).val();
  		    }
  		    var quantity=$("#qty").val();
  		    if(quantity == null || quantity == undefined){
  		    	quantity=1;
  		    }
  	        addListAjax(wishlistCode, productCode, inventryUOM);
  	        });
		
		$(document).on('click', '.js-wishListItem', function(event) {
			digitalData.eventData = {
				linktype: "product-grid",
				linkName: "Add to Cart",
				onClickPageName: $(".pagename").val()
			}
			var inventryUOM;
			var selectedItem = $(this).data("itemcode");
			if(selectedItem === "createNewListLinkProduct") {
				$("empty_listName").hide();
				var elemId = ".js-createNewSaveList-popup";
				var prodCode = $(this).data('productcode');
				if ($(this).data('productcode') != undefined) {
					elemId = ".js-createNewSaveList-popup" + prodCode;
				} else {
					inventryUOM = $(this).parents(".product-item-box").find("#inventoryUOMIDVal").val();
				}
				ACC.colorbox.open(ACC.config.addToNewList, {
					html: $(elemId).html(),
					width: "650px",
					onComplete: function () {
						$("#cboxContent #productCodelist").val(prodCode);
						if (inventryUOM) {
							$("#cboxContent #inventoryUOMIDVal").val(inventryUOM);
						}
						$('#colorbox').removeClass('add-assembly-to-list-box');
						$('#colorbox').removeClass('add-item-to-list-box');
						if ($("body").hasClass("page-productGrid") || $("body").hasClass("page-searchGrid") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout")) {
						 	ACC.productDetail.plpProductQTY = $('#productPLPPostQty_' + prodCode).val();
						}
						else{
							var listQty = getListQuantity(this);
							$('#colorbox').append('<input type="hidden" id="qty" value="' + listQty + '" />');
						}
						$('body').css("overflow-y", "hidden");
						$('#colorbox').addClass('add-to-list-box');
					},
					onClosed: function () {
						$('#colorbox').removeClass('add-to-list-box');
						$('body').css("overflow-y", "auto");
						location.reload();
					}
				});
				ACC.productDetail.reAlignColorBoxToPage();
			} else {
				let ele = $(this).children(":first");
				ele.prop("checked", "checked");
				$(this).parent().children().removeClass('selected');
				$(this).addClass("selected");
				var wishlistCode = $(this).data('itemcode');
				var wishListname = $(this).find("wishlist-item-name").text();
				var productCode = $(this).data('productcode');
				var productCode1 = $(this).data('productcode1');
				var inventryUOM = ($(".page-productGrid, .page-searchGrid, .template-pages-layout-siteOneCuratedPlpLayout, .template-pages-category-categoryLandingPage").length > 0) ? $(this).parents(".product-item-box").find("#inventoryUOMIDVal").val() : $("#inventoryUOMIDVal").val();
				var listQty;
				if ($("body").hasClass("page-productGrid") || $("body").hasClass("page-searchGrid") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout")) {
					listQty = $('#productPLPPostQty_' + productCode1).val();
				}
				else{
					listQty = getListQuantity(this);
				}
				addListAjax(wishlistCode, productCode, inventryUOM, wishListname, listQty);
				digitalData = {
					event: 'add product to list',
					item: productCode,
					listName: wishListname
				}
				try {
					_satellite.track('productAddedToList');
				} catch (e) { }
			}
		});

		$(document).on("show.bs.dropdown", ".addtolistdropdown", function() {
			$(this).find(".glyphicon-chevron-down").hide();
			$(this).find(".glyphicon-chevron-up").show();
		});
		$(document).on("hide.bs.dropdown", ".addtolistdropdown", function() {
			$(this).find(".glyphicon-chevron-down").show();
			$(this).find(".glyphicon-chevron-up").hide();
		});
  	    
  	    $(document).on('change', '#popupWishlist', function(event) {
			digitalData.eventData={
				linktype:"product-grid",
				linkName:"Add to Cart",
				onClickPageName: $(".pagename").val()
			}
			var inventryUOM;
			var listValue = $('option:selected', this).val();
			if (listValue === "createNewListLinkProduct") {
				$("empty_listName").hide();
				var listQty = getListQuantity(this);
				var elemId = ".js-createNewSaveList-popup";
				if ($(this).data('productcode') != undefined) {
					var prodCode = $(this).data('productcode');
					elemId = ".js-createNewSaveList-popup" + prodCode;
				} else {
					var prodCode = $('option:selected', this).data('productcode');
					inventryUOM = $(this).parents(".product-item-box").find("#inventoryUOMIDVal").val();
				}
				ACC.colorbox.open(ACC.config.addToNewList, {
					html: $(elemId).html(),
					width: "650px",
					onComplete: function () {
						$("#cboxContent #productCodelist").val(prodCode);
						if (inventryUOM) {
							$("#cboxContent #inventoryUOMIDVal").val(inventryUOM);
						}
						$('#colorbox').removeClass('add-assembly-to-list-box');
						$('#colorbox').removeClass('add-item-to-list-box');
						if ($("body").hasClass("page-productGrid") || $("body").hasClass("page-searchGrid") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout")) {
							$('#colorbox').append('<input type="hidden" id="qty" value="' + listQty + '" />');
						}
						$('body').css("overflow-y", "hidden");
						$('#colorbox').addClass('add-to-list-box');
					},
					onClosed: function () {
						$('#colorbox').removeClass('add-to-list-box');
						$('body').css("overflow-y", "auto");
						location.reload();
					}
				});
				ACC.productDetail.reAlignColorBoxToPage();
			} else {
				var wishlistCode = ($('option:selected', this).val());
				var wishListname = ($('option:selected', this).text());
				var productCode = ($('option:selected', this).data('productcode'));
				var inventryUOM = ($(".page-productGrid, .page-searchGrid, .template-pages-layout-siteOneCuratedPlpLayout, .template-pages-category-categoryLandingPage").length > 0) ? $(this).parents(".product-item-box").find("#inventoryUOMIDVal").val() : $("#inventoryUOMIDVal").val();
				var listQty = getListQuantity(this);
				addListAjax(wishlistCode, productCode, inventryUOM, wishListname, listQty);
				digitalData = {
					event: 'add product to list',
					item: productCode,
					listName: wishListname
				}
				try {
					_satellite.track('productAddedToList');
				} catch (e) { }
			}
		});
		  
  	  $(document).on('click', '.createNewListLinkProduct', function(event) { 
	        $("empty_listName").hide();
	        var elemId = ".js-createNewSaveList-popup";
	    	if($(this).data('productcode') != undefined)
	    	{
	    		var prodCode=$(this).data('productcode');
	    		elemId = ".js-createNewSaveList-popup"+prodCode;
	    	}

	         ACC.colorbox.open(ACC.config.addToNewList, {
	               html: $(elemId).html(),
	               width: "650px",
	               onComplete: function(){
	                $('#colorbox').removeClass('add-assembly-to-list-box');
	                $('#colorbox').removeClass('add-item-to-list-box');
	                $('body').css("overflow-y", "hidden");
	                $('#colorbox').addClass('add-to-list-box');
	                },
	               onClosed: function() {
	                $('#colorbox').removeClass('add-to-list-box');
	                    $('body').css("overflow-y","auto");
	                }
	              
	           });
	        
	         ACC.productDetail.reAlignColorBoxToPage();
	    });
  	    
  	  	$(document).on("keypress", ".wishListName", function(event) {
			if (event.keyCode == 13) {
				return false;
			} else {
				return true;
			}
		});
  	    $(document).on('click', '.createWishlist', function(event) {
  	        var wishlistName = $("#cboxContent .wishListName").val();
  	        var qty;
  	       if($(".page-cartPage").length >0){
				var productCode = $("#productCode").val();
				var inventryUOM = $(".uomId_popup").val();
				var itemCode = $("#itemCode").val();
			  	productCode = itemCode
			}	

			else{
				var productCode = $("#cboxContent #productCodelist").val();
				var inventryUOM = $("#cboxContent #inventoryUOMIDVal").val();
			}
			  
  		    if(productCode == null || productCode == undefined){
  			    var productcodeid = 'createwishlist_productCode'+$(this).data('productcode');
  			    productCode = $('#'+productcodeid).val();
  		    }
			 if(ACC.global.productCutatedplpList){
				digitalData={
					event: 'create list',
				   item: productCode,
				   listName: wishlistName
			   }	
				
				digitalData.eventData={
				linktype:"product-grid",
				linkName:"Add to Cart",
				onClickPageName: $(".pagename").val()
				}
			}
			else{
  		    digitalData={
    					 	event: 'create list',
    						item: productCode,
    						listName: wishlistName
    					}	
					}			
    		try {
    				_satellite.track('listCreated');
    			}catch(e){}
  		    if ($("body").hasClass("page-productGrid") || $("body").hasClass("page-searchGrid") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout") || $("body").hasClass("template-pages-layout-siteOneCuratedPlpLayout")) {
				qty = ACC.productDetail.plpProductQTY;
			}
			else{
				qty = $("#qty").val();
			}
  		    if(qty == null || qty == "undefined"  || qty == '' ){
  		    	qty = 1;
  		    }
  	        if (wishlistName == "") {           
  	             $("#cboxLoadedContent #empty_listName").show();
  	        } else {
  	            $("#empty_listName").hide();
  	        $.ajax({
  	            type: 'POST',
  	            url: ACC.config.encodedContextPath + "/savedList/createWishlist",
  	            datatype: "json",
  	            data: {
  	                productCode: productCode,
  	                qty : qty,
  	                wishListName: wishlistName,
	                inventoryUom: inventryUOM
  	                
  	            },
  	            success: function(result) {
  	            if(result.isDuplicate==false){
  	                ACC.colorbox.open("", {
  	                    html: ACC.productDetail.popupHtmlCreateNewList(ACC.config.listCreatedMsg,result.wishListCode),
  	                    width: "500px",
  	                    onComplete: function(){
  	                        $('#colorbox').removeClass('add-to-list-box');
  	                        $('#colorbox').addClass('item-add-list-box');
  	                        $(document).on("click",".successListCreation", function(e){ 
  	                            ACC.colorbox.close();
  	                        });
  	                    },
  	                    onClosed: function() {
							window.location.reload(true);
  	                    }
  	                });
  	               }
				   else{
						$(".existing-listname").show();
						  $('input.wishListName').keyup(function(e){
							 $(".existing-listname").hide();
						});
					}
  	            },
  	            error: function(xhr, ajaxOptions, thrownError) {
  	                console.log('data failure');
  	            }
  	        }); }
  	    });
  	    
  	    
  	    
  	    $(document).on('click', '#createWishlistFromAssembly', function(event) {
  	        var wishlistName = $("#cboxContent #wishListName").val();
  	        var productList = $(".productLists").val();
  	        if (wishlistName == "") {           
  	             $("#cboxLoadedContent #empty_listName").show();
  	        } else {
  	            $("#empty_listName").hide();

  	        $.ajax({
  	            type: 'POST',
  	            url: ACC.config.encodedContextPath + "/savedList/createWishlist",
  	            datatype: "json",
  	            data: {
  	                productList: productList,             
  	                wishListName: wishlistName
  	            },
  	            success: function(result) {
  	               if(result.isDuplicate==false){
  	                ACC.colorbox.open("", {
						html:ACC.productDetail.popupHtmlCreateNewList(ACC.config.listCreatedMsg,result.wishListCode),
  	                    width: "500px",
  	                    onComplete: function(){
  	                        $('#colorbox').removeClass('add-to-list-box');
  	                        $('#colorbox').addClass('item-add-list-box');
  	                        $(document).on("click",".successListCreation", function(e){
  	                            ACC.colorbox.close();
  	                        });
  	                    },
  	                    onClosed: function() {
  	                        $('#colorbox').removeClass('item-add-list-box');
  	                        $('body').css("overflow-y","auto");
  	                    }
  	                    
  	                });
  	                
  	             }
					else{
						$(".existing-listname").show();
						  $('input.newlistname').keyup(function(e){
							 $(".existing-listname").hide();
						});
					}
  	            },
  	            error: function(xhr, ajaxOptions, thrownError) {
  	                console.log('data failure');
  	            }
  	        });
  	        }
  	    });
  	    
  	    
  	    $(".wishlistAddProLink").click(function(e) {
  	        e.preventDefault();
  	        var listrefrence=$(this);
  	        var inventryUOM = $("#inventoryUOMIDVal").val(); 
  	        var prodCode;
  	        var quantity;
			var itemProductCode;
  	        var uomID=$(this).attr("data-uomValue");
  	        var cartFlag =  $(".moveTolistCart").val();
  	        if (cartFlag) {
  	            $(".currentForm").val($(this).attr('id').split("_")[1]);
  	            var code  =$(this).attr("data-role");
  	            console.log(code);
  	            var arr = code.split("|");
  	            console.log(arr);
  	        
  	            prodCode = arr[0];
  	            quantity = arr[1];
				itemProductCode = arr[2];
  	            $(".currentForm").val($(this).attr('id').split("_")[1]);
  	            $.cookie("cartEntry",$(".currentForm").val());
  	        } else {
  	            prodCode = $(this).siblings('#addproCode').val() ;
  	            quantity = 1;
  	        }
  	        if ($("#isCurrentPLPUser").length != 0) {           
  	        var wishlistVal=$(".wishlist").val();    	    
  	        var createWishList=$(".createWishList").val();
  	        addToWishList(quantity, prodCode, wishlistVal, createWishList, inventryUOM,uomID, itemProductCode);
  	        }
     	        else    	           
	            {   
     	         $.cookie("quantity","1");
     	    	 $.cookie("prodCode", prodCode);
     	    	 $.cookie("inventryUOM", inventryUOM);
     	      	localStorage.setItem("moveToListpopup", $(this).parent(".wishlistAddProLink-wrapper").find(".js-moveToWhislist-popup").html());
       	    	localStorage.setItem("createListPopup", $(this).parent(".wishlistAddProLink-wrapper").find(".js-createNewSaveList-popup").html());
	            
	            }
	        

	        });
	    

  	        function addToWishList(quantity, prodCode, wishlistVal, createWishList, inventryUOM,uomID, itemProductCode) {
  	        	var cartFlag =  $(".moveTolistCart").val();
  	        	var cartEntry=$.cookie("cartEntry");
  	        if( createWishList)
  	            {
  	        	$("empty_listName").hide();
  	        	var elemId = ".js-createNewSaveList-popup";
  	        	if($(".moveTolistCart").length == 0)
      	    	{
      	    		elemId = ".js-createNewSaveList-popup"+prodCode;
      	    		
      	    	}
  	        	
  	        	var flag=true;
	        	if($(elemId) != null && $(elemId).length == 0){
	        		 flag=false;
	        	}
  	        	
  	            ACC.colorbox.open(ACC.config.addToNewList, {
  	                    html: flag ? $(elemId).html():localStorage.getItem("createListPopup"),
  	                    width: "650px",
  	                    onComplete: function(){
  	                        $('body').css("overflow-y", "hidden");
  	                        $('#colorbox').addClass('add-to-list-box');
  	                    },
  	                    onClosed: function() {
  	                        $('#colorbox').removeClass('add-to-list-box');
  	                        $('body').css("overflow-y","auto");
	                        $.cookie("quantity", null);
  	                	  $.cookie("prodCode", null);
  	                	  $.cookie("inventryUOM", null);
  	                	  $.cookie("cartEntry", null);
  	                	  localStorage.removeItem("createListPopup");
  	                    }
  	                   
  	                });
  	            
  	             ACC.productDetail.reAlignColorBoxToPage();
  	             $(".js-moveToWhislist-popup").find("#productCode").val(prodCode);
  	                $(".js-moveToWhislist-popup").find("#qty").val(quantity);
					  $(".js-moveToWhislist-popup").find("#itemCode").val(itemProductCode);
  	            }
  	        
  	        
  	        
  	        else
  	            {
  	        	var elemId = ".js-moveToWhislist-popup";
  	        	if($(".moveTolistCart").length == 0)
  	        	{
  	        /*var prodCode=$(this).siblings('#addproCode').val();	*/	
  	        		elemId = ".js-moveToWhislist-popup"+prodCode;
  	        	}
  	        	
  	        	var flag=true;
	        	if($(elemId) != null && $(elemId).length == 0){
	        		 flag=false;
	        	}
  	             ACC.colorbox.open(ACC.config.addToListCart, {
  	            	    html: flag ? $(elemId).html():localStorage.getItem("moveToListpopup"),
  	                    width: "700px",
  	                    height: "325px",
  	                    onComplete: function(){
  	                        $('body').css("overflow-y", "hidden");
  	                      if(!flag){
	                        	$(document).find("#colorbox select.popupWishlist").html($(".wishlistForm").first().find("select").html());
	                        }
  	                        $('#colorbox').addClass('add-item-to-list-box');
							$(".uomId_popup").val(uomID);
  	                    },
  	                    onClosed: function() {
  	                        $('body').css("overflow-y","auto");
  	                        $('#colorbox').removeClass('add-item-to-list-box');
  	                      $.cookie("quantity", null);
	                	  $.cookie("prodCode", null);
	                	  $.cookie("inventryUOM", null);
	                	  $.cookie("cartEntry", null);
	                	  localStorage.removeItem("moveToListpopup");
  	                    }
  	                   
  	                });     
  	             ACC.productDetail.reAlignColorBoxToPage();
  	             $(".js-moveToWhislist-popup").find("#productCode").val(prodCode);
  	                $(".js-moveToWhislist-popup").find("#qty").val(quantity);
					  $(".js-moveToWhislist-popup").find("#itemCode").val(itemProductCode);
  	             
  	            }  
  	            
  	        }




  	    $("#add_Product_to_Assembly").click(function() {
  	        
  	          var proCode=$("#savedListSearch").val();
  	          var quantity = $("#quantityId").val();              
  	          var savedListName=$("#wishListListName").val();
  	          var regex = new RegExp("^[0-9]+$");
  	          
  	          $("#base_product_error").hide();
  	          $("#uom_product_error").hide();
  	          $("#invalid_code").hide();
  	          
  	          var isEmptyProduct=(proCode === "");
  	          var isInvalidqty=(quantity === ""|| !regex.test(quantity));
  	          validateProductAndQty (isEmptyProduct,isInvalidqty);
  	        if(!isEmptyProduct && !isInvalidqty){
  	              $.ajax({
  	              type: 'POST',
  	              url: ACC.config.encodedContextPath + "/assembly/add",
  	              datatype: "json",
  	              data: {
  	                  productCode: proCode,
  	                  quantity:quantity,
  	                  wishListCode: savedListName
  	              },
  	              success: function(wishlistAddResponseData) {
  	                  $("#empty_code").hide();
  	                  $("#empty_quantity").hide();
  	                  if (wishlistAddResponseData.message == 'baseproduct') {
  	                      $("#invalid_code").hide();
  	                      $("#base_product_error").show();
  	                  }
  	                  else if(wishlistAddResponseData.message == 'uomproduct')
  	            	  {
  	            	  $("#invalid_code").hide();
  	                  $("#uom_product_error").show();
  	            	  }
  	                  else if (wishlistAddResponseData.message == null) {
  	                      $("#base_product_error").hide();
  	                      $("#uom_product_error").hide();
  	                      $("#invalid_code").show();
  	                  } else{
  	                  window.location.reload(true);
  	                  }
  	              },
  	              error: function(xhr, ajaxOptions, thrownError) {
  	                  console.log('data failure');
  	                  $("#empty_code").hide();
  	                  $("#empty_quantity").hide();
  	                  $("#invalid_code").show();
  	              }
  	          });
  	          }});

  	    $(".editAssembly").click(function() {       
  	        $(this).next(".fetchWishList").toggle();        

  	        });

  	    $(".deleteProdFromAssembly").click(function() {
  	          var prodCode=$(this).siblings('#addproductCode').val();
  	           var quantity=$("#qty").val();           
  	          var savedListName=$(this).siblings('#savedListName').val();
  	        $.ajax({
  	            type: 'POST',
  	            url: ACC.config.encodedContextPath + "/assembly/moveSavedList",
  	            datatype: "json",
  	            data: {
  	                productCode: prodCode,
  	                wishListCode: savedListName,
  	                quantity:quantity
  	            },
  	            success: function(result) {
  	              window.location.reload(true);
  	            },
  	            error: function(xhr, ajaxOptions, thrownError) {
  	                console.log('data failure');
  	            }
  	        });              

  	        });
  	    
  	    $('.moveToAssembly').on('change', function() {
  	          
  	           var moveWishList=$(this).val();
  	           var prodCode=$(this).siblings('#addproductCode').val();
  	           var quantity=$("#assembly_qty_"+prodCode).val();
  	           console.log(quantity);
  	           
  	          var savedListName=$(this).siblings('#savedListName').val();

  	           
  	         
  	           $.ajax({
  	                  type: 'POST',
  	                  url: ACC.config.encodedContextPath + "/assembly/add",
  	                  datatype: "json",
  	                  data: {
  	                      productCode: prodCode,
  	                      wishListCode: moveWishList,
  	                      quantity:quantity
  	                  },
  	                  success: function(result) {
  	                      
  	                      $.ajax({
  	                          type: 'POST',
  	                          url: ACC.config.encodedContextPath + "/assembly/moveSavedList",
  	                          datatype: "json",
  	                          data: {
  	                              productCode: prodCode,
  	                              wishListCode: savedListName,
  	                              quantity:quantity
  	                          },
  	                          success: function(result) {
  	                              window.location.reload(true);
  	                          },
  	                          error: function(xhr, ajaxOptions, thrownError) {
  	                              console.log('data failure');
  	                          }
  	                      }); 
  	                  },
  	                  error: function(xhr, ajaxOptions, thrownError) {
  	                      console.log('data failure');
  	                  }
  	              });
  	             
  	        });

  	    
  	    
  	    
  	$(".addAssemblyToList").click(function(e) {
  	    //e.preventDefault();
  	        
  	        var wishlistVal=$("#wishListId").val();
  	        console.log(wishlistVal);
  	        var productCodes = $(".productCodes").val();        
  	        console.log(productCodes);
  	        
  	        var createWishList=$(".createWishListVal").val();



  	        if( createWishList)
  	            {
  	                    
  	             ACC.colorbox.open(ACC.config.addToNewList, {
  	                    html: $(".js-createNewSaveList-popup").html(),
  	                    width: "650px",
  	                    onComplete: function(){
  	                        $('body').css("overflow-y", "hidden");
  	                        $('#colorbox').addClass('add-to-list-box');
  	                    },
  	                    onClosed: function() {
  	                        $('#colorbox').removeClass('add-to-list-box');
  	                        $('body').css("overflow-y","auto");
  	                    }
  	                   
  	                });
  	            
  	            ACC.productDetail.reAlignColorBoxToPage();
  	            
  	            }
  	        
  	        
  	        
  	        else if(wishlistVal=="")
  	            {
  	             ACC.colorbox.open("Add to List", {
  	                    html: $(".js-moveToWhislist-popup").html(),
  	                    width: "650px",
  	                    height: "325px",
  	                    onComplete: function(){
  	                        $('body').css("overflow-y", "hidden");
  	                        $('#colorbox').addClass('add-assembly-to-list-box');
  	                      $.ajax({
  	            	    	  type: 'GET',
  	            	    	  url: ACC.config.encodedContextPath + "/assembly/getAllLists",
  	            	    	  success: function(response){
  	            	    		 $(".allwishlist").empty().append(response);
  	            	    	  }
  	            	      });
  	                    },
  	                    onClosed: function() {
  	                        $('#colorbox').removeClass('add-assembly-to-list-box');
  	                        $('body').css("overflow-y","auto");
  	                    }
  	                   
  	                });     
  	            ACC.productDetail.reAlignColorBoxToPage();
  	            
  	             
  	            }
  	        else
  	            {
  	            $.ajax({
  	                type: 'POST',
  	                url: ACC.config.encodedContextPath + "/assembly/addAssemblyToList",
  	                datatype: "json",
  	                data: {
  	                    productCodes: productCodes,
  	                    wishListCode: wishlistVal
  	                    
  	                },
  	                success: function(result) {
  	                    
  	                    
  	                    ACC.colorbox.open("", {
  	                        html: "<div class='PopupBox'><h1 class='headline'>"+result.message+"</h1><div class='row marginTop40 mob-list-confo'><div class='col-md-6 col-xs-12'><input class='btn btn-primary btn-block successListCreation' type='button' value='Continue Shopping'></div><div class='col-md-6 col-xs-12'><a href='"+ACC.config.encodedContextPath+'/savedList/listDetails?code=' + wishlistVal +"' class='btn btn-block btn-default'>View List</a></div></div></div>",
  	                        width: "500px",
  	                        onComplete: function(){
  	                            $('#colorbox').addClass('item-add-list-box');
  	                            $('body').css("overflow-y", "hidden");
  	                            $(document).on("click", ".successListCreation", function(){
  	                                ACC.colorbox.close();
  	                             });
  	                        },
  	                        onClosed: function() {
  	                            $('body').css("overflow-y","auto");
  	                            $('#colorbox').removeClass('item-add-list-box');
  	                        }
  	                        
  	                    });
  	                    
  	                   
  	                },
  	                error: function(xhr, ajaxOptions, thrownError) {
  	                    console.log('data failure');
  	                }
  	            });
  	            }
  	               
  	            
  	        
  	});
  		$(document).ready(function() {
  	    $('.list-check').on('click', function() {
  	    	var input = $(this).children('input[type=checkbox]');
  	    	if ($(input).prop('checked') == true) {
  	      		$(input).parent().css('background', '#78a22f');
				$(this).closest('.saved-list-sec').css('border', '1px solid #79a22f');
  	    	} 
  	    	else {
  	      		$(input).parent().css('background', '#f1f2f2');
				$(this).closest('.saved-list-sec').css('border', '1px solid #ccc');
  	    	}
			if($(".page-detailsSavedListPage").length){ACC.productDetail.checkForTransferableEntry();}
  	  	});
		
	  	  $('.colored').on('click', function() {
	  	  var input = $(this).children('input[type=checkbox]');
	  	  if ($(input).prop('checked') == true) {
	  	  	$('.select_Listall').css('background', '#aaaaaa');
		  }
		  else {
	  	    $('.select_Listall').css('background', '#919191');
	  	  }
	  	  });
		  
		  $('.controls-mobile').on('click', function() {
			  	$('.list-filters-row').toggle();
		  		$('.list-filters-row').removeClass('hidden');
				let offTarget = $('.recomannded-filter-box');
				if(offTarget.length && offTarget.is(":visible")){
					offTarget.hide();
				}	  		
		   });
  		var quantity=$.cookie("quantity");
  		var prodCode=$.cookie("prodCode");
  		var inventryUOM=$.cookie("inventryUOM");
  		var cartEntry=$.cookie("cartEntry");
  		
  		if ($("#isCurrentPLPUser").length != 0) {
  	    var wishlistVal=$(".wishlist").val();

  	    var createWishList=$(".createWishList").val();
  	    if(quantity!=null&&prodCode!=null)
  	    	{

  	  addToWishList(quantity, prodCode, wishlistVal, createWishList, inventryUOM);

  	    	}
  	    
  	    }
		// Temporary fix for Empty Features adding in Product. This has to be removed once it is resolved in backend
		$(".js-pdp-specification").each(function(){
			if($(this).find(".pdp-specification__value").text().trim().length == 0) {
				$(this).addClass("hidden");
			} else {
				$(this).removeClass("hidden");
			}
		})
	  });
	  
		// Per 7880, alignment issues are present on PLP pages where the CSP is overlapping the ATC button
		// The code below is not the recommended approach, but we could not target via CSS
		// As browser features are not similar between the browsers.
		
		function apply7880Fix() {
			shouldApply = false; // Set default to false

			// Check if Chrome 79 or newer
			var isChrome79 = navigator.userAgent.match(/chrom(?:e|ium)\/(\d+)\./i);
			var isChrome79 = parseInt(isChrome79[1], 10);
			if (isChrome79 >= 79){ shouldApply = true; }; // Set true if Chrome 79 or newer 

			var isSafari = /^((?!chrome|android).)*safari/i.test(navigator.userAgent);
			if (isSafari){ shouldApply = true; }; // Set true if Safari 

			if (/Edge/.test(navigator.userAgent)) { shouldApply = true; }

			if(shouldApply){ // If any browser matches, apply the override
				var elems = document.getElementsByClassName("addtocart");
					var index = 0,
						length = elems.length;
					for (; index < length; index++) {
						elems[index].setAttribute("style", "height: auto !important;");
				}
			}

		}
		apply7880Fix();
  	
  },
  checkForTransferableEntry: function(){
	let addListToCartsDisable;
	let checkCounter=0;
	$('.list-check').each(function() {
		let ref = $(this);
		let target = ref.children('input[type=checkbox]');
		let listBlock = ref.closest('.saved-list-sec');
		if(target.prop("checked")){
			checkCounter++;
			if(listBlock.data("nontransferableflag")){
				addListToCartsDisable = true;
			}
		}
	});
	if(addListToCartsDisable || !checkCounter){
		ACC.savedlist.disableEnableButon($(".SavedSelectedProduct"), true);
	}
	else{
		ACC.savedlist.disableEnableButon($(".SavedSelectedProduct"), false);
	}
  },
  
  //MultipleUoM to select the first Uom OnLoad
  multipleUomItemSelector: function () {
	let firstItemRow = $(".multipleUomContents").children(":first");
	if (firstItemRow.length) {
		firstItemRow.addClass("selected-uom-item");
		firstItemRow.find('input[type="radio"').prop("checked", true);
	}
    let qty = $('#pdpAddtoCartInput').val();
    $(".multipleUomItem").attr("data-qty",qty)
	ACC.product.checkMinQuantity($('#pdpAddtoCartInput'),0);
	$(window).on('pageshow', function() {
		firstItemRow.find('input[type="radio"').prop("checked", true).trigger('change');
	});
  },
  updatePriceForUOMDropdownSelection: function(elem) {
	let productCode=elem.attr("data-code");
	let inventoryUomId= elem.attr("data-value");
	let quantity=elem.attr("data-qty") || 1;
	let inventoryUOMDesc=elem.attr("data-uom");
	let inventoryUOMmul=elem.attr("data-inventory");
	let outOfStockFlag = elem.data('outofstockflag');
	if ($(".hardscapeProd_" + productCode).length && $(".getAQuoteFlagForB2BUserPDP").val() == "true") {
		let atcButton = $(".mobile-pdp-sticky").find(".js-atc-update");
		if (outOfStockFlag) {
			atcButton.addClass("hidden");
			atcButton.nextAll(".reqAQuoteForOutOfStock").first().removeClass("hidden");
		} else {
			atcButton.removeClass("hidden");
			atcButton.nextAll(".reqAQuoteForOutOfStock").first().addClass("hidden");
		}
	}
	if (quantity > 0 && productCode != undefined && productCode != null && productCode != "") {
		$.ajax({
			type: 'GET',
			url: ACC.config.encodedContextPath + '/p/customerpriceforuom',
			data: {
				productCode: productCode,
				quantity: quantity,
				inventoryUomId: inventoryUomId
			},
			success: function (result) {
				$(".multipleuom-retailprice").html(result.totalPrice.formattedValue);
				$(".muomunitprice").html(result.unitPrice.formattedValue);
				if (inventoryUOMmul == '1') {
					$('#redesignmultipleuomdesc').html(inventoryUOMDesc + '?');
				} else {
					$('#redesignmultipleuomdesc').html(inventoryUOMDesc + 'S?');
				}
				if (result.noOfUnits == 1) {
					let text_conmuom = (ACC.config.encodedContextPath == '/es') ? 'Total de la unidad' : 'Unit Total';
					$(".muomqty").html(result.noOfUnits + ' ' + text_conmuom);
				} else {
					let text_conmuom1 = (ACC.config.encodedContextPath == '/es') ? 'Total de la unidades' : 'Units Total';
					$(".muomqty").html(result.noOfUnits + ' ' + text_conmuom1);
				}
				$('#addToCartForm').find('#inventoryUomId').val(inventoryUomId);
				$('[id="inventoryUOMIDVal"]').each(function () { $(this).val(inventoryUomId) });
			},
			error: function (xhr, ajaxOptions, thrownError) {
				console.log('data failure');
			}
		});
	}
  },
  multipleUomTotalPriceUpdater: function(){
	  	let productCode=$(".selected-uom-item").attr("data-productcode");
		let inventoryUomId=$(".selected-uom-item").attr("data-inventoryid");
		let quantity=$(".selected-uom-item").attr("data-qty");
		let inventoryUOMDesc=$(".selected-uom-item").attr("data-inventoryUOMDesc");
		let inventoryUOMmul=$(".selected-uom-item").attr("data-inventorymul1");
		let outOfStockFlag = $(".selected-uom-item").attr("data-outofstockflag");
		let stockLevel = $(".selected-uom-item").attr("data-stocklevel");
		let atcButton = $(".product-main-info").find(".js-atc-update");
	  if ((outOfStockFlag == 'true' && $(".selected-uom-item").find(".stock-section-outofstock").length > 0)
		  || (Number(quantity) > Number(stockLevel) && ($(".selected-uom-item").find(".stock-section-instock").length > 0
			  || $(".selected-uom-item").find(".stock-section-instock-nearby").length > 0))) {
		  if ($(".hardscapeProd_" + productCode).length && $(".getAQuoteFlagForB2BUserPDP").val() == "true") {
			  atcButton.addClass("hidden");
			  atcButton.parents(".pdpSubmitBtnSection").find(".reqAQuoteForOutOfStock").first().removeClass("hidden");
			  $(".product-main-info").find(".pdp-warning_info_" + productCode).find(".pdp-commonerror").text(ACC.config.changeUOMForAvailability);
		  } else {
			  $(".product-main-info").find(".pdp-warning_info_" + productCode).find(".pdp-commonerror").text(ACC.config.expectDelay);
		  }
		  $(".product-main-info").find(".pdp-warning_info_" + productCode).removeClass("hidden");
	  } else {
		if ($(".hardscapeProd_" + productCode).length && $(".getAQuoteFlagForB2BUserPDP").val() == "true") {
			atcButton.removeClass("hidden");
			atcButton.parents(".pdpSubmitBtnSection").find(".reqAQuoteForOutOfStock").first().addClass("hidden");
		}
		$(".product-main-info").find(".pdp-warning_info_" + productCode).addClass("hidden");
	  }
		if(!$(".selected-uom-item").length){
			productCode = $(".singleUom-ProductCode").val();
			inventoryUomId = "";
			quantity = $('#pdpAddtoCartInput').val();
		}
		if(quantity>0 &&  productCode!= undefined && productCode!=null && productCode!="" ){
		$.ajax({
	     	      type: 'GET',
	     	      url: ACC.config.encodedContextPath + '/p/customerpriceforuom',
	     	      data: {
					   productCode:productCode,
					   quantity:quantity,
					   inventoryUomId:inventoryUomId
				   },
				   success: function(result){
					  $(".multipleuom-retailprice").html(result.totalPrice.formattedValue);
					 $(".muomunitprice").html(result.unitPrice.formattedValue);
					 if( inventoryUOMmul == '1'){
						$('#redesignmultipleuomdesc').html(inventoryUOMDesc+'?');
					 }
					 else{
						$('#redesignmultipleuomdesc').html(inventoryUOMDesc+'S?');
					 }
					 if( result.noOfUnits == 1 ){
						let text_conmuom = (ACC.config.encodedContextPath == '/es')? 'Total de la unidad' : 'Unit Total';
						$(".muomqty").html(result.noOfUnits+' '+text_conmuom) ;
					 }
					 else{
						let text_conmuom1 = (ACC.config.encodedContextPath == '/es')? 'Total de la unidades' : 'Units Total';
						$(".muomqty").html(result.noOfUnits+' ' +text_conmuom1);
					 }	
					 $('#addToCartForm').find('#inventoryUomId').val(inventoryUomId);
					 $('[id="inventoryUOMIDVal"]').each(function(){$(this).val(inventoryUomId)});
				   },
				   error: function(xhr, ajaxOptions, thrownError) {
	  	              console.log('data failure');               
	
	  	          }
	     	    })
	     	    }
  },
  hardscapesStonePDPAlert: function(){
		if($(".page-productDetails").length && $(".hardscape-pdp-stone-alert").length){
			let target = $(".hardscape-pdp-stone-alert");
			let l1Category = (ACC.config.encodedContextPath == '/es')? 'Materiales duros & Vida al Aire Libre' : 'Hardscapes & Outdoor Living';
			let landscapeCategory = (ACC.config.encodedContextPath == '/es') ? 'Material de Jardinería' : 'Landscape Supply';
			let cosumableCategory = (ACC.config.encodedContextPath == '/es') ? 'Consumibles' : 'Consumables';
			if(target.data('level1category') == l1Category || (target.data('level1category') == landscapeCategory && target.data('level2category') == cosumableCategory)){
				let itemUOMList = [ACC.config.uomCubicYard, ACC.config.uomNetTon, ACC.config.uomNetTon2000LB];
				let itemUOMParent = $(".product-main-info .product-uomMeasure");
				let itemUOMCheck;
				let itemUOMFound;
				for (let i = 0; i < itemUOMParent.length; i++) {
					itemUOMCheck = $.trim(itemUOMParent.eq(i).val());
					if($.inArray(itemUOMCheck, itemUOMList) != -1){
						itemUOMFound = true;
						break;
					}
				}
				if(itemUOMFound && !$(".callBranchForPrice").is(":visible") && !$(".requestQuoteBtnPDP").is(":visible")){
					$(".hardscape-price-alert").removeClass("hidden");
				}
				$(".hardscape-note-alert").slideDown();
			} else {
				$(".hardscape-note-alert").slideUp();
			}
		}
	},
	landscapeNotePDPAlert: function () {
		if ($(".page-productDetails").length && $(".landscape-note-alert").length) {
			let l1Category = $(".landscape-note-alert").attr('data-level1Category');
			if (l1Category == 'Landscape Supply' || l1Category == 'Material de Jardinería') {
				let level3Category = $('#breadcrumbName3').val();
				let level4Category = $('#breadcrumbName4').val();
				if (
					( level3Category== 'Mulch' || level3Category=='Mantillo' || level3Category=='Soil' || level3Category=='Suelo' ||level3Category=='Soil Amendments' || level3Category=='Mejoras del Suelo' || level3Category=='Straw' ||level3Category=='Paja') &&
					( level4Category== 'Bulk Mulch' || level4Category=='Bagged Mulch' || level4Category== 'Bagged Soil Amendments' || level4Category== 'Bulk Soil Amendments'|| level4Category== 'Bagged Soil' || level4Category== 'Bulk Soil' ||  level4Category=='Pine Straw'||level4Category== 'Wheat Straw' || level4Category=='Synthetic Straw')
					)
				{
					$('.landscape-note-alert').removeClass('hidden')
				}
			}
		}
	},
	pdpBulletPointLoad: function () {
			if(document.getElementById('pdpBulletPointSection') != null){
				var pdpBPSimpleProduct = document.getElementById('pdpBulletPointProductType').value;
				if(pdpBPSimpleProduct == 'true'){
					var pdpBPSection = document.getElementById('pdpBulletPointSection');
					var pdpBP = pdpBPSection.getElementsByTagName('li');
					var pdpBPCalLink = document.getElementById('pdpBulletPointCalLink');
					var pdpBPLength = pdpBP.length;
					if(pdpBPLength > 0){
						var pdpBPDisplayCount = 2;
						if(pdpBPCalLink != null){
							pdpBPDisplayCount = 1;
						}
						for(count=(pdpBPLength-1); count>pdpBPDisplayCount; count--){
								pdpBP[count].remove();
						}
						if(pdpBPDisplayCount == 1 && pdpBPLength > 2){
							$('.pdpBulletPointMoreDetails').removeClass("hidden");
						}
						if(pdpBPDisplayCount == 2 && pdpBPLength > 3){
							$('.pdpBulletPointMoreDetails').removeClass("hidden");
						}
					}
					$('.pdpBulletPointLoaderSection').addClass("hide");
					$('#pdpBulletPointSection').removeClass("hidden");
					$('#pdpBulletPointCalLink').removeClass("hidden");
				}			
			}
	},
	triggerpdplistpopupoption: function(id){
			var option = document.getElementById(id);
			option.click();
	},
	triggerPDPBulletPointMoreDetails: function(){
			$('html,body').animate({
                    scrollTop: $("#pdpSimpleProductDesktopDescription").offset().top - 180 },
            'slow');
	},
	pdpAddListAjax: function(wishlistCode, productCode, inventryUOM, wishListname, quantity) {
	 if(($(".plpviewtype").val() == "card" || $(".plpviewtype").val() == "list") && (ACC.productDetail.plpCommonBtn == "true")){
			ACC.product.addSelectedProductsToList(wishlistCode);
	 }else if($(".plpviewtype").val() == "card" && ACC.productDetail.plpCommonBtn == "false" && ACC.productDetail.plpMobileAddListCode != ''){
		 	ACC.product.plpMobileAddListFunction(wishlistCode,ACC.productDetail.plpMobileAddListCode);
	 }
	 else{
		 if (quantity == null || quantity == undefined) {
			quantity = 1;
		}
		if ($('.loginstatus').val() != '') {
			let params = {
				productCode: productCode,
				wishListCode: wishlistCode,
				quantity: quantity,
				inventoryUOMId: inventryUOM,
			};
			let apiname = "/savedList/add";
			if (ACC.savedlist.isRecommendedListPage) { //for recommended List flow
				apiname = "/savedList/addSelectedToSavedWishlist";
				let buttonName = $("#" + wishlistCode).attr("buttonName");
				if (buttonName == "selectedCopyList") {
					params = {
						wishListCode: wishlistCode,
						productItemNumbers: productItemNumbers,
					};
				}
				else {
					let recommendedCategoryName = $("#" + wishlistCode).attr("categoryname");
					params = {
						wishListCode: wishlistCode,
						categoryName: recommendedCategoryName,
					};
				}
			}
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
					console.log('data failure');
				}
			})
		}
	 }
	},
	varientProductAddList: function(num,elem) {
		ACC.productDetail.plpCommonBtn = "false";
		ACC.productDetail.plpMobileAddListCode = '';
		ACC.productDetail.varientProductQTY = ACC.productDetail.getVarientProductListQuantity(elem);
		$('.pdplistpopupoption').removeClass('selected');
		$(".pdplistpopup1").removeClass('hidden');
		$(".pdp-newlist-popup-error-text").hide();
		$(".pdp-emptynewlist-popup-error-text").hide();
		$('.listpopupsavebtn').removeClass("disabled");
		$('.createnewlistbtn').removeClass("disabled");
		$(".pdp-atl-popup").colorbox.resize();
		var listpopup = "#listpopup" + num ;
		if($('.loginstatus').val()!=''){
			ACC.colorbox.open("", {
				html:  $(listpopup.replace(".","\\.")).html(),
				width: "500px",
				className: "pdp-atl-popup",
				onComplete:function(){
					$(document).on("click",".listpopupclosebtn", function(e){
						ACC.colorbox.close();
					});
				}
			})
		}
	},
	getVarientProductListQuantity: function(elem){
		var qty;
		if($('.product-variant-table').length > 0) {
			if($(elem).closest('.product-variant-table__values')[0].querySelector('.pdp-variant-qty') != null){
				qty = $(elem).closest('.product-variant-table__values')[0].querySelector('.pdp-variant-qty').value;
				return qty;
			}
			else{
				qty = 1;
				return qty;
			}
		}
	},
	productHighlightsButton: function(){
		$('.productHighlightsClick').click();
	}
};

$(document).on("click", ".successListCreation", function(){
	if($('.product-variant-table').length > 0) {
		window.location.reload(true);
	}
 });

//Select the different UoM and get response 

$(document).on("click", ".multipleUomItem", function () {
	let qty = $('#pdpAddtoCartInput').val();
	$(".multipleUomItem").attr("data-qty",qty);
	$(".multipleUomItem").removeClass("selected-uom-item");
	$(this).addClass("selected-uom-item");
	let ele = $(this).children().children().children(":first");
	ele.prop("checked", "checked");
	$(".quoteUom-Measure").val($(".selected-uom-item").attr("data-inventoryuomdesc"));
	let customerPrice = $(".selected-uom-item").find(".multipleUomItemYourPrice").text().trim();
	let retailPrice = $(".selected-uom-item").find(".multipleUomItemUnitPrice").text().trim()
	$(".quoteUom-CustomerPrice").val(customerPrice ? customerPrice.replace(/[^0-9.]/g, "") : "");
	$(".quoteUom-Price").val(retailPrice ? retailPrice.replace(/[^0-9.]/g, "") : "");
	ACC.product.checkMinQuantity($('#pdpAddtoCartInput'),1);	
	ACC.productDetail.multipleUomTotalPriceUpdater();
});

//Updating quantity on changing quantity input

$(document).on("input","#pdpAddtoCartInput",function(){
	    if ($(".pdp-variant-qty").length) {
        let quantity = $(this).val();
        let productCode = $(this).attr('data-productcode');
        let inventoryUOMId = "";
		if(quantity>0){
			$.ajax({
				type: 'GET',
				url: ACC.config.encodedContextPath + '/p/customerpriceforuom',
				data: {
					productCode: productCode,
					quantity: quantity,
					inventoryUomId: inventoryUOMId
				},
				success: function (result) {
					$('#addToCartForm').find('#inventoryUomId').val(inventoryUomId);
					$('[id="inventoryUOMIDVal"]').each(function(){$(this).val(inventoryUomId)});
				},
				error: function(xhr, ajaxOptions, thrownError) {
					console.log('data failure');  
				}
			})
		}
    } else{
		let qty = $('#pdpAddtoCartInput').val();
		 $(".multipleUomItem").attr("data-qty",qty);
		 ACC.productDetail.multipleUomTotalPriceUpdater();
	}
	
})

// ajax function to update unit and total price pdp 

$( function()
{
    var targets = $( '[rel~=pdp-tooltip]' ),
        target  = false,
        newTooltip = false,
        title   = false;
 
    targets.bind( 'mouseenter', function()
    {
        target  = $( this );
        tip     = target.find(".tooltip-content").html();
        newTooltip = $( '<div id="custom-tooltip"></div>' );
 
        if( !tip || tip == '' )
            return false;
 
        target.removeAttr( 'title' );
        newTooltip
               .html( tip )
               .appendTo( 'body' );
 
        var init_tooltip = function()
        {
            if( $( window ).width() < newTooltip.outerWidth() * 1.5 )
                newTooltip.css( 'max-width', 250 );
            else
                newTooltip.css( 'max-width', 340 );
 
            var pos_left = target.offset().left + ( target.outerWidth() / 2 ) - ( newTooltip.outerWidth() / 2 ),
                pos_top  = target.offset().top - newTooltip.outerHeight() - 20;
              
            if( pos_left < 0 )
            {
                pos_left = target.offset().left + target.outerWidth() / 2 - 20;
                newTooltip.addClass( 'left' );
            }
            else
                newTooltip.removeClass( 'left' );
 
            if( pos_left + newTooltip.outerWidth() > $( window ).width() )
            {
                pos_left = target.offset().left - newTooltip.outerWidth() + target.outerWidth() / 2 + 20;
                newTooltip.addClass( 'right' );
            }
            else
                newTooltip.removeClass( 'right' );
 
            if( pos_top < 0 )
            {
                var pos_top  = target.offset().top + target.outerHeight();
                newTooltip.addClass( 'top' );
            }
            else
                newTooltip.removeClass( 'top' );
 
            newTooltip.css( { left: pos_left, top: pos_top } )
                   .fadeIn( "slow");
        };
 
        init_tooltip();
        $(newTooltip).siblings('#custom-tooltip').each(function(){
            $(this).remove();
        })
        // $( window ).resize( init_tooltip );
 
        var remove_tooltip = function()
           {   
        	setTimeout(function(){
                 if(newTooltip.hasClass("active")===false){
                    newTooltip.css( "display","none")
                  }
            },500);   
            //target.attr( 'title', tip );  
            };
 
        target.bind( 'mouseleave',remove_tooltip);
        newTooltip.bind( 'click', remove_tooltip );
    });
    
    $(document).on("mouseenter","#custom-tooltip",function(){
        $(this).fadeIn("fast").addClass("active")

    })
    $(document).on("mouseleave","#custom-tooltip",function(){
        $(this).fadeOut("fast").removeClass("active")
        if(newTooltip.hasClass("active")===false){
            $(this).css( "display","none");     
        }
    })
});