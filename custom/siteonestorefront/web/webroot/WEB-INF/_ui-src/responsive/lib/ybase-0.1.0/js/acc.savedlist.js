ACC.savedlist = {
		
		_autoload: [
			"bindPageLoadFunctions",
		            "bindAddProductToList",
		            "bindShareSavedList",
		            "bindShareUsers",
		            "bindProductComment",
		            "bindCreateForm",
		            "bindEditForm",
		            "bindSelectedProduct",
		            "bindEnableList",
		            "bindEntireList",
		            "bindPagination",
		            "printListPage",
		            "movedEntireList",
					"checkMinQuantityList",
		            "checkingAddListToCart",
		            "setQtyIdDataVal",
		            "bindSaveListBeforeunload",
		            "onLoadRecommendedListSort",
	],
	isFilterPopupSelectAll: false,
	recommendedListDateArray: [],
	isRecommendedListPage: false,
	onLoadRecommendedListSort: function () {
		if ($(".page-savedListPage").length || $(".page-recommendedListPage").length) {
			$(".recommended-table").each(function (index) {
				let target = $(this);
				//Mon Jan 22 23:34:19 IST 2024
				let listDateString = target.attr("data-date").split(" ");
				let listDate = new Date(listDateString[1] + ' ' + listDateString[2] + ' ' + listDateString[5]);
				let listMilliSeconds = listDate.getTime();
				ACC.savedlist.recommendedListDateArray.push(listMilliSeconds + '--' + index);
				target.attr("data-time", listMilliSeconds + '--' + index);
				target.attr("data-order", index);
			});
			ACC.savedlist.recommendedListDateArray.sort();
			ACC.savedlist.getSortedDate();
		}
	},
	getSortedDate: function () {
			ACC.savedlist.recommendedListDateArray.reverse();
			//console.log(ACC.savedlist.recommendedListDateArray)
			$(".recommended-table").each(function (index) {
				let target = $(this);
				let targetIndex = $.inArray(target.data("time"), ACC.savedlist.recommendedListDateArray)
				target.css({"order":targetIndex})
			});
	},
  tabChange: function (tabvalue) {
	$(".recommendedlist-tab").removeClass("active-list-tab");
	$(".tab-list-data").addClass('hidden');
	$(".tab-" + tabvalue).addClass("active-list-tab");
	$('.tab-data-' + tabvalue).removeClass('hidden');
},
recommendedCopyList: function (categoryName, itemCount, btnName, ref) {
	let target = $(ref);
	if(!target.hasClass("disabled") && $('.loginstatus').val() != '') {
		$(".recCategory").html(' <input type="hidden" value="' + categoryName + '" class="recommendCatName" /> <input type="hidden" value="' + btnName + '" class="reccomendBtnName" /> ');
		if (btnName == "selectedCopyList") {
			itemNum = $(".list-item-counter").html();
			$("#recommendedlistcount").html(itemNum);
		}
		else {
			$("#recommendedlistcount").html(itemCount);
		}
		ACC.colorbox.open("", {
			html: $("#recommendedlistpopup").html(),
			width: "615px",
			className: "pdp-atl-popup",
			onComplete: function () {
				$(".recommendedlistname").attr("categoryname", categoryName);
				$(".recommendedlistname").attr("buttonName", "");
				if (btnName == "selectedCopyList") {
					$(".recommendedlistname").attr("buttonName", btnName);
				}
				$(document).on("click", ".listpopupclosebtn", function (e) {
					ACC.colorbox.close();
				});
			},
		});
		$('.list-filters-row').fadeOut();
	}
},
recCopyToMyListPopup: function (wishListCode) {
	let recommendedCategoryName = $("#" + wishListCode).attr("categoryname");
	let buttonName = $("#" + wishListCode).attr("buttonName");
	//console.log(recommendedCategoryName);
	let params;
	if (buttonName == "selectedCopyList") {
		params = {
			wishListCode: wishListCode,
			productItemNumbers: productItemNumbers
		}
	} else {
		params = {
			wishListCode: wishListCode,
			categoryName: recommendedCategoryName
		};
	}
	$.ajax({
		type: "POST",
		url:
			ACC.config.encodedContextPath + "/savedList/addSelectedToSavedWishlist",
		dataType: "json",
		data: params,
		success: function (result) {
			//console.log(result);
			ACC.colorbox.open("", {
				html: ACC.productDetail.pdppopupHtmlExistingList(
					ACC.config.listaddedMsg,
					wishListCode
				),
				width: "500px",
				onComplete: function () {
					$("#colorbox").addClass("item-add-list-box");
					$("body").css("overflow-y", "hidden");
					$(document).on("click", ".successListCreation", function () {
						ACC.colorbox.close();
					});
				},
				onClosed: function () {
					$("body").css("overflow-y", "auto");
					$("#colorbox").removeClass("item-add-list-box");
				},
			});
		},
		error: function (xhr, ajaxOptions, thrownError) {
			//console.log("data failure");
			ACC.myquotes.quotePopupError();
		},
	});
},
$productCodeField: '.productID_assemblyList',
$assemblyproductcode: '.product-codedetail',
listPageCartLink: '',
bindPageLoadFunctions: function() {
	if ($(".page-savedListPage").length || $(".page-recommendedListPage").length || $(".page-detailsSavedListPage").length) {
		setTimeout(function () {
			$('[data-toggle="popover"]').each(function () {
				let target = $(this);
				if (target.hasClass("addListToCarts_filter") && target.attr("disabled") == "disabled") {
					target.popover({ placement: 'top', trigger: 'hover', container: 'body' });
					target.on('shown.bs.popover', function () {
						$(".popover").addClass("bg-danger");
					});
					ACC.savedlist.listPageCartLink = target.attr("href");
					target.attr("href","#");
				}
				else if (!target.hasClass("addListToCarts_filter")) {
					target.popover({ placement: 'top', trigger: 'hover', container: 'body' });
				}
			});
		}, 1000);
		$(".list-filter-search").on("focusout", function () {
			let target = $(this);
			if (target.val() == "") {
				$(".from-input-label").fadeIn();
			}
			else {
				$(".from-input-label").fadeOut();
			}
		});
	}
	//Applicable for recommended list flow
	if ($(".page-recommendedListPage").length || $(".page-savedListPage").length || $(".page-detailsRecommendedListPage").length) {
		ACC.savedlist.isRecommendedListPage = true;
		$(".js-update-entry-quantity-input").each(function(index){
			ACC.recommendedlist.listDetailTotalCalculate(this, true);
		});
	}
	if ($(".page-recommendedListPage").length || $(".page-savedListPage").length) { //both Landing page
		$(".rec-list-link").each(function () {
			let target = $(this).attr("href");
			$(this).attr("href", target.replace("&", "%26"));
		});
	}
	if($(".page-detailsRecommendedListPage").length || $(".page-detailsSavedListPage").length){ //both details page
		//
	}
	if ($(".page-detailsSavedListPage").length) {
		if (window.innerWidth < 768) {
			if(ACC.config.encodedContextPath == '/es'){
				$("#add_Product_to_wishList").val("Agregar artículo a la lista");
			}else{
				$("#add_Product_to_wishList").val("Add Item to List");
			}
		} else {
			if(ACC.config.encodedContextPath == '/es'){
				$("#add_Product_to_wishList").val("Agregar");
			}
			else{
				$("#add_Product_to_wishList").val("Add");
			}
		}
		$("#savedListSearch").on('keyup', function(e){if(e && e.keyCode == 13){ $(".saved_add_to_cart_btn").trigger('click');}});
		if ($(".saved-list-singleProduct").length) {
			$(".empty-listfilter-hide").removeClass("hidden");
			ACC.savedlist.updateListFilter(true);
			$(".list-filter-search").trigger("focusout");
		}
		else if(!$("#filter-Brand-1").length && $(".list-page-counter").text() != 0){
			$(".empty-listfilter-show").removeClass("hidden");
		}
		$("#add_Product_to_wishList").click(function () {
			var proCode = $("#savedListSearch").val().replace(/\s/g, '');
			var quantity = $("#quantityId").val();
			var regex = new RegExp("^[0-9]+$");
			$(".list-add-product-error").hide();
			var isEmptyProduct = (proCode === "");
			var isInvalidqty = (quantity === "" || !regex.test(quantity));
			var savedListName = $("#wishListListName").val();
			if (isEmptyProduct ) {
				$("#empty_code, .list-add-product-error-box").show();
			}
			else if (isInvalidqty) {
				$("#empty_quantity, .list-add-product-error-box").show();
			}
			else {
				$.ajax({
					type: 'POST',
					url: ACC.config.encodedContextPath + "/savedList/add",
					datatype: "json",
					data: {
						productCode: proCode,
						quantity: quantity,
						wishListCode: savedListName
					},
					success: function (wishlistAddResponseData) {
						digitalData = {
							event: 'add product to list',
							item: proCode,
							listName: $('.list-headline').text()
						}
						try {
							_satellite.track('productAddedToList');
						} catch (e) { }
						if (wishlistAddResponseData.message == 'Product not found') {
							$("#empty_code, .list-add-product-error-box").show();
						}
						else if (wishlistAddResponseData.message == 'baseproduct') {
							$("#base_product_error, .list-add-product-error-box").show();
						}
						else if (wishlistAddResponseData.message == 'uomproduct') {
							$("#uom_product_error, .list-add-product-error-box").show();
						}
						else if (wishlistAddResponseData.message == ACC.config.pdpnouommsg) {
							$("#uom_hidden_error, .list-add-product-error-box").show();
						}
						else if (wishlistAddResponseData.message == null) {
							$("#invalid_code, .list-add-product-error-box").show();
						} else {
							loading.start();
							window.location.reload(true);
						}
					},
					error: function (xhr, ajaxOptions, thrownError) {
						$("#invalid_code, .list-add-product-error-box").show();
					}
				});
			}
		});
		$(".deleteProFromList").click(function () {
			var prodCode = $(this).siblings('#addproductCode').val();
			var quantity = $("#qty").val();
			var savedListName = $(this).siblings('#savedListName').val();
			var productsku = $(this).siblings('#product_code').val();
			var price = $(this).closest(".qty-list-flex").find(".js-customer-price").val() || 0;
			var qty = $(this).closest(".qty-list-flex").find(".qtyId").val() || 0;
			loading.start();
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + "/savedList/moveSavedList",
				datatype: "json",
				data: {
					productCode: prodCode,
					wishListCode: savedListName,
					quantity: quantity
				},
				success: function (result) {
					loading.stop();
					var productPrice = Number(price).toFixed(2) * Number(qty);
					var cumulativeTotPrice = Number($('.cumulative-tot-price').find('strong').text().replaceAll(/[$,]/g, '') || 0);
					cumulativeTotPrice -= (productPrice).toFixed(2);
					$('.cumulative-tot-price').find('strong').text("$" + ACC.savedlist.formatNumberComma(cumulativeTotPrice.toFixed(2)));
					let leftItemCount = Number($(".list-page-counter").html() - 1);
					$(".list-page-counter").html(leftItemCount);
					let leftCheckedItemCount = Number($(".list-item-counter").html() - 1);
					$(".list-item-counter").html(leftCheckedItemCount);
					if (leftCheckedItemCount === 0) {
						$(".list-item-counter-text").addClass("hidden");
					}
					if (leftItemCount === 0) {
						window.location.reload();
					}
					if ($(".saved-list-singleProduct").length > 1) {
						$(".sl_singleProduct_" + productsku).slideUp("slow", function () {
							$(this).remove();
							ACC.savedlist.checkingAddListToCart();
						});
					} else {
						$(".list-filters-row, .controls-mob, .product__list").slideUp("slow", function () {
							$(".remove-noproduct").removeClass("hidden");
						});
					}
				},
				error: function (xhr, ajaxOptions, thrownError) {
					loading.stop();
					//console.log('data failure');
				}
			});
		});
	}
},
	listQtyUpdate: function(e, from){
		let ref = $(e);
		let refVal = Number(ref.val());
		let refMax = Number(ref.data("available-qty"));
		let refParent = ref.parents(".saved-list-sec");
		let refSelect = refParent.find(".js-uom-selector:visible");
		let refMultilier = 1;
		if(refVal && refMax && refMax > 0 && refVal > 0){
			if(refSelect.length){ /* Multiple uom and atleast UOM visible */
				refMultilier = refSelect.find(":selected").data("inventorymultiplier");
			}
			else if(!refSelect.length) { /* single uom if other than each */
				refMultilier = ref.data("inventorymultiplier");
			}
			else { /*  Multiple uom but only 1 UOM visible  */
				refMultilier = 1;
			}
			if(refMultilier && refMultilier > 0){
				if(refVal*refMultilier > refMax){
					refParent.find(".list-warning-section").show();
				}
				else{
					refParent.find(".list-warning-section").hide();
				}
				if(refMultilier > refMax){
					refParent.find(".list-outofstock-section").show();
					refParent.find(".list-instock-section").hide();
				}
				else{
					refParent.find(".list-outofstock-section").hide();
					refParent.find(".list-instock-section").show();
				}
				refParent.find(".list-instock-section span").html(Math.floor(refMax/refMultilier));
			}
		}
		//console.log(from, refVal , refMax, refMultilier);
	},	
    bindAddProductToList:function()
	{
    	var productcode=[];
    	$("#add_Product_to_savedList").on('click', function (){
    		loading.start();
    		$(this).addClass('disabled');
    		var btnstatus=$(this);
    	var productCodes=$("#savedListSearch").val().replace(/\s/g, '');
    	var quantity = $("#quantityId").val();
    	var regex = new RegExp("^[0-9]+$");
    	var isMyStoreProduct=false;
    	var list_products=$("#save_list_product_"+productCodes).val();
    	var list_product_number = $("#save_list_product_number"+productCodes.toUpperCase()).val();
    	if(productCodes!=undefined)
    		{
    	productCodes=productCodes.replace(/\s/g, '');
    		}
    	if(list_products!=undefined)
    		{
    	list_products = list_products.replace(/\s/g, '');
    		}
    	if (list_product_number !=undefined ) {
    		list_product_number = list_product_number.replace(/\s/g, '');
    		list_product_number = list_product_number.toUpperCase();
    	}
    	var imageUrl=null;
    	
    	var url= ACC.config.encodedContextPath +"/savedList/displayProduct?productCodes="+productCodes;
    	var str = productCodes.toUpperCase();
    	
    	var productExist = list_product_number == str;
    	
    	var type="POST";
    		$.ajax({
    			url:url,
    			type:type,
    			beforeSend:function(xhr)
    			{
    				$("#base_product_error").hide();
    				$("#uom_product_error").hide();
    				$("#uom_hidden_error").hide();
    				$("#invalid_code").hide(); 		
    				if(list_products==productCodes || productExist)
    				{
    					$("#invalid_code_empty").hide();    					
    					$("#empty_quantity").hide();    					
    					$("#duplicate_code").show();
    					xhr.abort();
    					btnstatus.removeClass('disabled');
    					loading.stop();
    				}
    				var isEmptyProduct=(productCodes === "");
    		  	    var isInvalidqty=(quantity === ""|| !regex.test(quantity));
    		  	    if(isEmptyProduct){
    	      	    	$("#invalid_code_empty").show()
    	      	    	xhr.abort();
						btnstatus.removeClass('disabled');
						loading.stop();
    	      	    }
    	      	    else{
    	      	    	$("#invalid_code_empty").hide();
    	      	    }
    	      	    
    	      	    if(isInvalidqty){
    	      		  $("#empty_quantity").show();
    	      		xhr.abort();
					btnstatus.removeClass('disabled');
					loading.stop();
    	    	    }
    	    	    else{
    	    	    	$("#empty_quantity").hide();
    	    	    }
    		  	    
    		  	    
    		  	     
    				
    				
    				
    			

    			}, 
    			success:function(productData)
    			{
    				if(productData!==''&&productData!==null)
    					{
    					
    					 var productID = $("#savedListSearch").val();
    					 digitalData.eventData={
    							 productID: productID
    							}
    					 try {
    							_satellite.track("productAddToAssembly");
    						}catch(e){}
    						
    					$("#invalid_code_empty").hide();
    					$("#invalid_code").hide(); 		
    					$("#empty_quantity").hide();
    					$("#duplicate_code").hide();
    					//productcode+=productCodes+"|"+quantity+",";
    					
    					if (productData.variantType == 'GenericVariantProduct' && productData.variantCount != 1) {
    						$("#base_product_error").show();
    					
    					}
    					else if(productData.sellableUomsCount == 0 && (productData.singleUom) && productData.hideUom)
    					{
    						$("#uom_hidden_error").show();
    					}
    					else {
    					
    					$("#product_list").val(	$("#product_list").val()+(productData.code+"|"+quantity+","));
    					
    					
    					if(productData.name!=null)
    						{
    						name=productData.name;
    						}
    					if(productData.code!=null)
						{
						sku=productData.code;
						}
    					if(productData.itemNumber!=null)
    						{
    						code=productData.itemNumber;
    						}
    					if(productData.images!=null)
    						{
    						image=productData.images[0]
    						if(image!=undefined)
    							{
    						imageUrl=image.url
    							}
    						else
    							{
    							imageUrl= ACC.config.encodedContextPath +"/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg";
    							}
    						}
    					else
    						{
    						imageUrl= ACC.config.encodedContextPath +"/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg";
    						}

    				var msgfinal = productData.storeStockAvailabilityMsg;

					if(productData.inStockImage==true){
						msgfinal = '<img class="inventory-message-icon" src="/_ui/responsive/common/images/S1-in-stock-checkmark.svg"" alt=""/>' + msgfinal;
					} else if(productData.notInStockImage==true){
						msgfinal = '<img class="inventory-message-icon" src="/_ui/responsive/common/images/S1-nearby-location.svg"" alt=""/>' + msgfinal;
					} else if(productData.outOfStockImage==true){
						msgfinal = '<img class="inventory-message-icon" src="/_ui/responsive/common/images/S1-out-of-stock-Xg.svg"" alt=""/>' + msgfinal;
					} else if(productData.isEligibleForBackorder==true){
						msgfinal = '<img class="inventory-message-icon" src="/_ui/responsive/common/images/S1-nearby-location.svg"" alt=""/>' + msgfinal;
					} 
   				$("#saved_list_item").append("<div class='saved-list-data' id='product"+productData.code+"'><input type='hidden' class='product-codedetail' id='save_list_product_"+productData.code+"' value='"+productData.code+"'><input type='hidden' id='save_list_product_number"+productData.itemNumber+"' value='"+productData.itemNumber+"'><div class='margin20'><div class='row'><div class='col-md-4 col-sm-4 col-xs-4'><img src="+imageUrl+" alt='"+name+"'  title='"+name+"' style='width:100%;margin-bottom: 10px;'/></div><div class='col-md-4 col-sm-4 col-xs-8'><b><a href=" + ACC.config.encodedContextPath + productData.url+" class='green-title'>"+name+"</a></b><br/><span class='hidden-xs'><br/><br/><br/><br/></span><span class='visible-lg'><br/><br/></span>"+msgfinal+"<br/>"+code+"</div><div class='visible-xs col-xs-12 hidden-sm- hidden-md hidden-lg'><hr></div><div class='col-md-3 col-sm-4 col-xs-12 create-list-remove-button'><input type='button' data-code='"+sku+"'class='btn btn-default btn-block savedlist_remove_product' value='Remove'/></div></div><div class='cl'></div></div></div>");
    				$("#savedListSearch").val().replace(/\s/g, '');
    				$("#quantityId").val("1");
    				$("#invalid_code").hide();
    				$("#invalid_code_empty").hide();
    				$("#listheader").html("Add more products:");
    				$("#assemblyheader").html("Add more products:");
    				$(document).on('click', '.savedlist_remove_product', function(event) { 
    					$("#invalid_code_empty").hide();
    					$("#invalid_code").hide(); 		
    					$("#empty_quantity").hide();
    					$("#duplicate_code").hide();
    			/*	$(".savedlist_remove_product").on('click', function (){*/
    					var products=$("#product_list").val().split(",");
       					var product=$(this).attr("data-code");
       					var j=-1;
       					for(var i=0;i<products.length;i++){
       						if(product== products[i].split("|")[0]){
       							j=i;
       							break;
       						}
       						
       					}
       					/*products.forEach(function(e){
       						
       						var status=e.includes(product);
       						if(!status) {
       							pro =
       							
       							
       						}
       						i++;
       						
       					});*/
       					if(j!= -1){
       					products.splice(j,1);
       					}
    					$("#product_list").val(products);
    					//$(this).parent("div").parent("div").remove();
    					$("#product"+product).remove();
    					$(this).find("#saved_list_item").hide();
    					if($(".saved-list-data").length == 0){
    						$("#listheader").html("");
    					}
    					$("#assemblyheader").html(ACC.config.assemblyHeader);
       					
    				});
    				} }
    				else
    					{
    									
        					$("#duplicate_code").hide();
        					$("#empty_quantity").hide();
    						$("#invalid_code_empty").hide();
    						$("#base_product_error").hide();
    						$("#uom_product_error").hide();
    						$("#uom_hidden_error").hide();
    					$("#invalid_code").show();
    						
    					}
    				btnstatus.removeClass('disabled');
    				loading.stop();
    				
    			},
    		error: function(xhr,status,error)
			{
    			btnstatus.removeClass('disabled');
    			loading.stop();
			}
    		
		
	});
    	
	});
	},
	
	bindShareSavedList:function()
	{
		$("#share_Saved_List").on('click', function (){
			$(".overlayBackground").fadeIn();
			$("#siteoneShareSavedListForm").fadeIn();
			if ($('.page-detailsSavedListPage').length > 0){
				       _AAData.popupPageName= ACC.config.sharelistpathingPageName;
				       _AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {} 
			}
			if ($('.page-detailsAssemblyPage').length > 0){
				_AAData.popupPageName= ACC.config.shareassemblypathingPageName;
				_AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {} 
			}
		});
		
		$("#siteoneShareSavedListForm .cancelBtn").on('click', function (){
			$("#siteoneShareSavedListForm").fadeOut();
			$(".overlayBackground").fadeOut();
			$('body').css('overflow-y', 'auto'); 
			
	    	_AAData.popupPageName= "";
	    	_AAData.popupChannel= "";
		 	 
		});
		
		$(".shareListDetailBtn").on('click', function (){		
			var jsonData = {
					"code" : $("#code").val(),
					"listname" : $("#listname").val(),
					"users" : $("#users").val(),
					"note" : $("#note").val(),
					"isViewEdit": JSON.parse($("input[name='allow-label']:checked").val())
			}
			$.ajax({
		        type: 'POST',
		        url: ACC.config.encodedContextPath + "/savedList/shareList",
		        contentType : 'application/json; charset=utf-8',
		        datatype: "json",
		        data: JSON.stringify(jsonData),
		        success: function(result) {
	    			digitalData.eventData={
	   					 shareMedium:"Share",
	   					}
		   			 try {
		   					_satellite.track("shareList");
	   				}catch(e){}	
		            ACC.colorbox.open("", {
		                html: ACC.config.shareList,
		                width: "650px",
		                height:"224px",
		                onComplete: function(){
		                	_AAData.popupPageName= ACC.config.sharelistsuccesspathingPageName;
			    			_AAData.popupChannel= ACC.config.myaccountpathingChannel;
						 	try {
							    	 _satellite.track('popupView');
					        } catch (e) {} 
		                	$("#siteoneShareSavedListForm").fadeOut();
		        			$(".overlayBackground").fadeOut();
		        			$('body').css("overflow-y","hidden");
		        			$('#colorbox').addClass('list_item_added');
		    			},
		                onClosed: function() {
		    				$('body').css("overflow-y","auto");
		    				$('#colorbox').removeClass('list_item_added');
		    				window.location.reload();
		    			}
		            });
		        
		        	
		           
		        },
		        error: function(xhr, ajaxOptions, thrownError) {
		            //console.log('data failure');
		        }
		    });
			
		});
		
		
		$(".shareAssemblyDetailBtn").on('click', function (){		
			$.ajax({
		        type: 'POST',
		        url: ACC.config.encodedContextPath + "/assembly/shareList",
		        datatype: "json",
		        data: 
		        	 $("#siteoneShareSavedListForm").serialize()
		        ,
		        success: function(result) {
		            ACC.colorbox.open("", {
		                html:  ACC.config.shareAssembly,
		                width: "650px",
		                className:"sharedassembly-successmsg",
		                onComplete: function(){
		                	 _AAData.popupPageName= ACC.config.shareassemblysuccesspathingPageName;
		 	            	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
		 				 	try {
		 					    	 _satellite.track('popupView');
		 			        } catch (e) {} 
		                	$("#siteoneShareSavedListForm").fadeOut();
		        			$(".overlayBackground").fadeOut();
		        			$('body').css('overflow-y', 'auto'); 
		    			},
		                onClosed: function() {
		    				$('body').css("overflow-y","auto");
		    				window.location.reload();
		    			}
		            });
		        
		        	
		           
		        },
		        error: function(xhr, ajaxOptions, thrownError) {
		            //console.log('data failure');
		        }
		    });
			
		});
	},
	
	bindProductComment:function()
	{
		$(".saved_list_comment").on('click',function (e){
			var productCodeData=$(this).attr("data-productCode");
			var arrdata = productCodeData.split("|");
			var productCode = arrdata[0];
			//console.log(arrdata[1]);
			var commentData = arrdata[1];
			$(".overlayBackground").fadeIn();
			$("#commentOverlay_"+productCode).fadeIn();
			$(".list-comment").val(commentData);
			 var text_max = 200;
				$(".list-comment").each(function( index ) {
						
					if($(this).length!=0)
				 	{
						 var text_length = $(this).val().length;
						 var data_remaining = text_max - text_length;
				 	}
				 		if(text_length!=0)
				 	{
				 		$('.text-remaining').html(ACC.config.remaining +' '+data_remaining + '' + ACC.config.characters);
				 		if(data_remaining==0)
				 		{
				 			$('.text-remaining').css("color","red");
				 		}
				 		else
				 			{
				 			$('.text-remaining').css("color","#999");
				 			}
				 	}
				 else
				 	{
					 $('.text-remaining').css("color","#999");
					 $(".text-remaining").html(ACC.config.remaining +''+text_max+ '' + ACC.config.characters);
				 	}
						});

		});
		
		
	},
	
	bindCreateForm:function()
	{
			$(document).on('change', "#csvFile", function(e){
				e.preventDefault();
	    		var status = true;
	    		
	    		if($("#name").val() == "" || $("#name").val() == null)
	    		{
	    			status = false;
	    			
	    			var selectedFile = document.getElementById('csvFile').files[0];
		            if (ACC.csvimport.isSelectedFileValid(selectedFile)) {
		            	document.getElementById('csvFile').value = '';
		            }
	    			ACC.formvalidation.addErrorClassForRegForm('#name', '#nameError', '#name\\.errors', 'Enter a list name.<div class="cl"></div><br/><br/>');
	
	    		}
	    		else
	    			{
	    			$("#nameError").empty();
	    			}
	
						var selectedFile = document.getElementById('csvFile').files[0];
			            if (!ACC.csvimport.isSelectedFileValid(selectedFile)) {
			            	$("#globalMessages").hide();
			                return;
			            }
			            if(status)
						{
					    document.getElementById("siteoneSavedListCreateForm").submit();
						}
			});
		$(document).on('input blur', "#name:visible", function (e) {
			var listPage = $('#nameError').hasClass('create-list-error');
			if (listPage) {
				ACC.formvalidation.vaildateFields('#name', '#nameError', '#name\\.errors', ACC.config.listHeader);
			}
			var assemblyPage = $('#nameError').hasClass('create-assembly-error');
			if (assemblyPage) {
				ACC.formvalidation.vaildateFields('#name', '#nameError', '#name\\.errors', ACC.config.assemblyHeader);
			}
		});
		
		$('#message').on('input blur',function () {
	       
			$('#message').css("background-color","#fff");
			$('#message').css("border-color","#ccc");
	    });
		 
		$(document).on("click", "#createList", function (e) {
			e.preventDefault();
			var status = true;
			let globalHeaderBtn = $(e.target).data("global-linkname");
			if ($("#name").val() == "" || $("#name").val() == null) {
				status = false;
				ACC.formvalidation.addErrorClassForRegForm('#name', '#nameError', '#name\\.errors', ACC.config.savedEnter);
			}
			else {
				$.ajax({
					type: 'GET',
					url: ACC.config.encodedContextPath + "/savedList/listExists?wishListName=" + $(".listName").val().trim(),
					datatype: "json",
					async: false,
					success: function (result) {
						$("#listglobalMessages").css("display", "none");
						if (!result) {
							//console.log(result);
							status = true;
							if (!globalHeaderBtn || globalHeaderBtn == "") {
								digitalData = {
									event: 'create list',
									item: $('#product_list').val().trim().split(" "),
									listName: $(".listName").val().trim()
								}
								try {
									_satellite.track('listCreated')
								}
								catch (e) { }
							}
						}
						else {
							$("#listglobalMessages").css("display", "block");
							status = false;
						}
					},
					error: function (err) {
						status = false;
					}
				});
				$("#nameError").empty();
				if ($(".pagename").val() == 'Saved List Page') {
					$(".listname").val("");
				}
			}
			if (status) {
				if (!globalHeaderBtn || globalHeaderBtn == "") {
					$("#siteoneSavedListCreateForm").submit();
				} else {
					$("#siteoneSavedListHeaderCreateForm").submit();
				}
			}
		});
		$(document).on("click", "#createAssembly", function (e)
		    	{
			       $(document).on('input blur', "#name", function(e)
				  	   {
				  	      ACC.formvalidation.vaildateFields('#name', '#nameError', '#name\\.errors', ACC.config.assemblyHeader);
				  	   });
		    		e.preventDefault();
		    		var status = true;
		    		if($("#name").val() == "" || $("#name").val() == null)
		    		{
		    			status = false;
		    			$("#nameError").html("<div class='panel-body name-error-msg margin20'>"+ACC.config.assemblyHeader+"</div>");
		    		}
		    		else
		    			{
		    			$("#nameError").empty();
		    			}
		    		if(status)
					{
		    			
		    			var products = [];
			        	$(ACC.savedlist.$assemblyproductcode).each(function() {
			        		var productIDAssembly = $(this).val();
			        	    var totalcode = Number($(this).val());
			        	    if (totalcode > 0) {
			        	        products.push({
			        	            productInfo: {
			        	                "productID": productIDAssembly,
			        	            }
			        	        });
			        	  }
			           });
			        	
			        	_AAData.productAddedtoAssembly= products;
			        	
			        	var isDuplicate;
			        	$.ajax({
		    		        type: 'POST',
		    		        url: ACC.config.encodedContextPath + "/assembly/checkDuplicate",
		    		        datatype: "json",
		    		        data: 
		    		        	 $("#siteoneAssemblyCreateForm").serialize()
		    		        ,
		    		        success: function(isDuplicate) {
		    		        	$("#siteoneAssemblyCreateForm").submit();
		    		        	if(isDuplicate=='false') {
		    		        		try {
		    							_satellite.track("assemblyCreated");
		    						}catch(e){}
		    		        	}
		    		           
		    		        },
		    		        error: function(xhr, ajaxOptions, thrownError) {
		    		        	//console.log('data failure');
		    		        }
		    		    });
			        	
			        	
					}
		    		
		    	});
		
		
	},
	
	bindEditForm:function()
	{
		$(document).on("click", ".modifyList", function (e)
		    	{
		    		e.preventDefault();
		    		var status = true;
		    		if($("#name").val() == "" || $("#name").val() == null)
		    		{
		    			status = false;
		    			$("#nameError").html("<div class='panel-body name-error-msg margin20'>"+ACC.config.listHeader+"</div>");
		    		}
		    		else
		    			{
		    			$("#nameError").empty();
		    			}
		    		if(status)
					{
						$("#siteOneEditSavedListForm").submit();
					}
		    		
		    	});
		$(document).on("click", ".modifyAssembly", function (e)
		    	{
		    		e.preventDefault();
		    		var status = true;
		    		if($("#name").val() == "" || $("#name").val() == null)
		    		{
		    			status = false;
		    			$("#nameError").html("<div class='panel-body name-error-msg margin20'>"+ACC.config.assemblyHeader+"</div>");
		    		}
		    		else
		    			{
		    			$("#nameError").empty();
		    			}
		    		if(status)
					{
						$("#siteOneEditSavedListForm").submit();
					}
		    		
		    	});
	},
	
	productCheckboxAllClicked: 0,
	bindSelectedProduct:function()
	{
		$(document).on("click", ".select_product_checkbox_all", function(e){
			ACC.savedlist.productCheckboxAllClicked = 1;
			productItemNumbers = "";
			productCodes = "";
			if($('.select_product_checkbox_all').prop("checked")) {
				$('.saved-list-sec:visible input:checkbox').not(this).prop("checked",false).trigger("click");
			} else {
				$('.saved-list-sec:visible input:checkbox').not(this).prop("checked",true).trigger("click");
			}
			ACC.savedlist.productCheckboxAllClicked = 0;
		});
		
		$(document).on("click", ".selectedProductRetainOfPagination", function(e){
			e.preventDefault();
			//console.log("I am working", xList);
			var pagenumberUrl = $(this).parent().find('#current_span').val();
			var pageurlparameter = $(this).parent().find('#url-parameters').val();
			var ViewPaginationListnumber = $('#ViewPaginationSavedListDetail').val();
			var url = pagenumberUrl + pageurlparameter + "&pagesize="+ ViewPaginationListnumber;

			window.location.href = url;
			});
		
		$(document).on("click", ".saved-list-previous-link, .saved-list-next-link", function(e){
			e.preventDefault();
			var href = $(this).attr('href');
			var url = new URL(href);

			window.location.href = url;
		});
		
		$(document).on("click", ".select_product_button_all", function(e){
			$('.select_product_checkbox_all').trigger("click");
		});
		$(document).on("click", ".select_product_checkbox", function (e) {
			var target = $(this);
			var id = target.attr('data-role');
			var productCode = $('#prodCode' + id).val();
			var qty = $("#prodQty_" + productCode).val();
			var uomId = target.parents(".saved-list-sec").find("#inventoryUomId").val();
			var productItemNumber = $('#prodItemNumber' + id).val();
			var qtyItem = document.getElementById("prodQtyItem_" + productItemNumber).value;
			var selectedProdList = productCode + "~" + productItemNumber + "~" + qty + "~" + uomId;
			let itemTotalCount = $(".saved-list-sec:visible .select_product_checkbox").length;
			let targetParent = target.parents(".saved-list-sec");
			let targetCheck = targetParent.find(".list-delete-style");
			if (!target.prop("checked")) {
				var x = productItemNumber + "|" + qtyItem + "|" + uomId;
				productItemNumbers = productItemNumbers.replace(x, "");
				var deselect = selectedProdList;
				xList = xList.filter(function (item) {
					return item !== deselect;
				});
				var x = productCode + "|" + qty + "|" + uomId;
				productCodes = productCodes.replace(x, "");
				if (!ACC.savedlist.productCheckboxAllClicked) {
					$(".select_product_checkbox_all").prop("checked", false);
				}
				if($(".page-detailsSavedListPage").length && ACC.global.wWidth < 1024){
					targetCheck.find("path").attr("fill", "#78a22f");
				}
			}
			else {
				productItemNumbers = productItemNumbers.concat(productItemNumber + "|" + qtyItem + "|" + uomId);
				productItemNumbers = productItemNumbers.concat(" ");
				if (!xList.includes(selectedProdList)) {
					xList.push(selectedProdList);
				}
				productCodes = productCodes.concat(productCode + "|" + qty + "|" + uomId);
				productCodes = productCodes.concat(" ");
				if($(".page-detailsSavedListPage").length && ACC.global.wWidth < 1024){
					targetCheck.find("path").attr("fill", "#fff");
				}
			}
			//console.log(productItemNumbers, ' | ', "xList:-:" , xList , ' | ',productCodes);
			if (!ACC.savedlist.productCheckboxAllClicked || (ACC.savedlist.productCheckboxAllClicked && itemTotalCount == ACC.savedlist.productCheckboxAllClicked)) {
				ACC.savedlist.listItemCheckStatus();
			}
			if(ACC.savedlist.productCheckboxAllClicked){
				ACC.savedlist.productCheckboxAllClicked++;
			}
			ACC.savedlist.checkingAddListToCart(e);
		});
		
		$(document).on("click", ".SavedSelectedProduct", function(e){
			if(!$(e.target).hasClass("disabled")){
				var products = [];
				products.push({
							productInfo: {
								"productID": productCodes,
								"addToCartLocation": _AAData.eventType,
							}
				});
				digitalData.eventData = {
									"linktype" : "",
									"linkName" : "Add to Cart",
									"onClickPageName" : _AAData.eventType
							}
				if($(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0" )
					{ 
						digitalData.cart = {
								cartId:  $("#currentCartId").val(),	
								event :  "scOpen, scAdd",
						};
					}

				else
					{
						digitalData.cart = {
								cartId:  $("#currentCartId").val(),
								event :  "scAdd",
						};	
					}

				digitalData.product = products;
				digitalData.listName = $('.list-headline').text();
				try {
						_satellite.track('Add Cart');
					} catch (e) {}
					
				var wishListCode = $("#listCodeQtyChange").val(); 
				var pcodes = "";
				$.each(xList, function(index, value){
					var prodcutDetail=value.split('~');
					if(prodcutDetail.length>1){
						var uom=prodcutDetail[3];
						if(uom==='undefined'){
							uom='';
						}
						pcodes=pcodes+prodcutDetail[0]+"|"+prodcutDetail[2]+"|"+uom+" ";
					}		
				});
				pcodes = (pcodes || '').trim();
				loading.start();
				$.ajax({
					type: 'POST',
					url: ACC.config.encodedContextPath+"/savedList/addSelectedItemsToCart",
					dataType: "json",
					data: {
						wishListCode: wishListCode,
						productCodes: pcodes,
					},
					success: function(result) {
						if(result){
							ACC.savedlist.savedListSessionStorage("remove");
							window.location.href = ACC.config.encodedContextPath +"/cart";
						}else {
							var selectedProducts = $.grep(xList, function(x){
								return (x || '').length;
							});
							
							ACC.savedlist.savedListSessionStorage("set", selectedProducts.join(" "));
							window.location.href = ACC.config.encodedContextPath +"/savedList/listDetails?code="+wishListCode;
						}
					},
					error: function(xhr, ajaxOptions, thrownError) {
						loading.stop();
						//console.log('data failure');
					}
				});
			}
	  	});
		
		 $(document).on("click", ".remove_selected_filter", function(e){
			if(!$(e.target).hasClass("disabled")){
				e.preventDefault();
				var currentWishlist= $("#listCodeQtyChange").val();
				$.ajax({
					type: 'POST',
					url: ACC.config.encodedContextPath+"/savedList/RemoveSelectedFromSavedList",
					dataType: "json",
					data: {
					wishListCode: currentWishlist,
					productItemNumbers: productItemNumbers
					},
					success: function(result) {
							digitalData={
    					 			event: 'delete product from list',
    							    item: productCodes,
    								listName: $('.list-headline').text()
    						    }
							try{
									_satellite.track('deleteProductFromList');
						       }  
							catch(e){} 
							window.location.href = ACC.config.encodedContextPath +"/savedList/listDetails?code="+currentWishlist;
							$(".delete_entireList_msg").show();
					},
					error: function(xhr, ajaxOptions, thrownError) {
						//console.log('data failure');
							}
						});
				return false;
			}
		});
		
	
	
	/* Dropdown list click function*/
	 
	 $(document).on("click", ".listname", function(e){ 
			e.preventDefault();
			var currentWishlist= $("#listCodeQtyChange").val();
			var listcodeqtyChangeval= $(this).attr("id");
			var productID = $(".saveList").val().trim().split(" ");
    		digitalData={
    					 	event: 'add product to list',
    						item: productID,
    						listName: $(this)[0].outerText     
    					}				
    		try {
    					_satellite.track('productAddedToList');
    			}catch(e){}
			$.ajax({
	             type: 'POST',
	             url: ACC.config.encodedContextPath+"/savedList/addSelectedToSavedWishlist",
	             dataType: "json",
	             data: {
	           	  wishListCode: listcodeqtyChangeval,
	           	  productItemNumbers: productItemNumbers,
	           	  currentWishlist: currentWishlist
	             },
	             success: function(result){
		              ACC.colorbox.open("", {
	 	                    html: ACC.productDetail.popupHtmlExistingList(ACC.config.listaddedMsg,listcodeqtyChangeval),
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
	 	                       window.location.href = ACC.config.encodedContextPath +"/savedList/listDetails?code="+currentWishlist;
	 	                    } 
	 	                });   
	             },
	             
	             error: function(xhr, ajaxOptions, thrownError) {
	                //console.log('data failure');
	             }
	         });
        return false;
	 });  

	/* Create new list function*/

	 $(document).on('click', '#createWishlistforentireList', function(event) {
 	        var wishListName = $("#newWishListName").val();
 	        var currentWishlist= $("#listCodeQtyChange").val();
 	        var tmpproductCodes = (productCodes || "").trim().replace(/ +/g, ' ');
	        
 	        if (wishListName == "") {           
 	             $("#empty_listName").show();
 	            $('.entirelist-name-alreadyexits-error').hide();
 	        } else {
 	            $("#empty_listName").hide();
 	        $.ajax({
 	            type: 'POST',
 	            url: ACC.config.encodedContextPath + "/savedList/addSelectedToNewWishlist",
 	            dataType: "json",
 	            data: {
 	            	wishListName:wishListName,
 	            	productCodes: tmpproductCodes,
 	            	currentWishlist: currentWishlist
 	            },
 	            success: function(result) {
 	                var productID = $(".saveList").val().trim().split(" ");
    				digitalData={
    					 			event: 'create list',
    								item: productID,
    								listName: wishListName   
    							}
					try{
						_satellite.track('listCreated')
					   }
					catch(e){}
 	            	$('.entirelist-name-alreadyexits-error').hide();
 	            	if (result != "Listname exists"){
	 	                ACC.colorbox.open("", {
	 	                    html:ACC.productDetail.popupHtmlCreateNewList(ACC.config.listCreatedMsg,result),
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
	 	                       window.location.href = ACC.config.encodedContextPath +"/savedList/listDetails?code="+currentWishlist;
	 	                    } 
	 	                });
 	            	}
	            	else{
	            		$('.entirelist-name-alreadyexits-error').show();
	            		 $("#empty_listName").hide();
	            	}
 	            },
 	            error: function(xhr, ajaxOptions, thrownError) {
 	                //console.log('data failure');
 	            }
 	        });
 	        return false;
 	        }
 	    });
/*create a new list in Item Level*/
	    $(document).on('click', '#createWishlistforentireList_il', function(event) {
	    	
	        var wishListName = $(this).parent().parent().parent().find('#newWishListName_il').val()
	        var currentWishlist= $("#listCodeQtyChange").val();
	        var createitemlevel_prodCode=$(this).siblings('#createitemlevel-productcode').val();
	        var createitemlevel_qty=$(this).siblings('#createitemlevel-qty').val();
	        var createitemlevel_productCodes=createitemlevel_prodCode+"|"+createitemlevel_qty+"|";
	        //console.log("createitemlevel-productCodes:",createitemlevel_productCodes);
	         
	        if (wishListName == "") {           
	        	$(this).parent().parent().parent().parent().find('#empty_listName_il').show();
	        	$('.listName-alreadyexits-error').hide();
	        } 
	        else {
	        	$(this).parent().parent().parent().parent().find('#empty_listName_il').hide();
	        }
	        $.ajax({
	            type: 'POST',
	            url: ACC.config.encodedContextPath + "/savedList/addSingleProductToNewWishlist",
	            dataType: "json",
	            data: {
	            	wishListName:wishListName,
	            	productCodes:createitemlevel_productCodes,
	            	currentWishlist: currentWishlist,
	            },

	            success: function(result) {
	            	if (result != "Listname exists"){
	            		$('.listName-alreadyexits-error').hide();
 	                ACC.colorbox.open("", {
 	                    html:ACC.productDetail.popupHtmlCreateNewList(ACC.config.listCreatedMsg,result),
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
 	                       window.location.href = ACC.config.encodedContextPath +"/savedList/listDetails?code="+currentWishlist;
 	                    }
 	                });
	            	}
	            	else{
	            		$('.listName-alreadyexits-error').show();
	            		$('#empty_listName_il').hide();
	            	}
	            	digitalData={
    					 			event: 'create list',
    								item: createitemlevel_prodCode,
    								listName:wishListName
    							}
					try{
									_satellite.track('listCreated')
						}
					catch(e){}
	            },
	            error: function(xhr, ajaxOptions, thrownError) {
	                //console.log('data failure');
	            }
	        });
	        return false;      
	   });
	},
	disableEnableButon: function(ref, disable){
		if(disable){
			ref.addClass("disabled").attr("disabled","disabled");
		}
		else{
			ref.removeClass("disabled").removeAttr("disabled","disabled");
		}
	},
	bindEnableList:function()
	{
		let addListToCartsDisable;
		$(".saved-list-sec").each(function(){
			let target = $(this);
			if(target.data("nontransferableflag") && target.find("#showAddtoCart").length){
				ACC.savedlist.disableEnableButon(target.find("#showAddtoCart"), true);
				addListToCartsDisable = true;
			}
		});
		if(addListToCartsDisable){
			$(".addListToCarts_filter").attr("disabled", "disabled");
		}

		if($("#notSellable").length>0 || $(".js-enable-btn").is(':disabled')>0 )
			{
			$(".addlistCartlink").addClass("disabled");
			$(".addlistCartlink2").addClass("disabled");
			$(".addAssemblyToCartLink").addClass("disabled");
			$(".addAssemblyListToCartLink").addClass("disabled");
			}
		else
			{
			$(".addlistCartlink").removeClass("disabled");
			$(".addlistCartlink2").removeClass("disabled");
			$(".addAssemblyToCartLink").addClass("disabled");
			$(".addAssemblyListToCartLink").removeClass("disabled");
			}
	},
	
	bindEntireList:function()
	{

		$(".quantityInputSection .qtyChanged").on("change", function(e){
			var qty=$(this).val();
			var productId=$(this).attr("data-role").split("_")[1];	
			var productList =$(".saveList").val().trim().split(" ");
			
			for(var i=0; i< productList.length; i++){
				var product= productList[i].split("|")[0];
				if(product == productId){
					productList[i]=productId+"|"+qty;
					break;
				}
			}
			$(".saveList").val(productList.join(" "));
			$(".productList").val(productList.join(" "));
			$(".productCodes").val(productList.join(" "));
		});
		
		$(document.body).on('click','.addAssemblyListToCartLink',function(event){
			event.preventDefault();
			var assemblyCount = $(".js-assembly-qty").val();
			$(this).attr("href",$(this).attr("href")+$("#listCodeQtyChange").val()+'&assemblyCount='+assemblyCount);
			var products = [];
			cartLocation(_AAData.eventType);
			function cartLocation(addToCartLocation){
        	$(ACC.savedlist.$productCodeField).each(function() {
        		var productID = $(this).val();
        	    var qty = Number($(this).val());
        	    if (qty > 0) {
        	        products.push({
        	            productInfo: {
        	                "productID": productID,
        	                "addToCartLocation": addToCartLocation
        	            }
        	        });

	        	  if($(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0" )
	        	  { 
	        		  digitalData.cart = {
		        		event :  "scOpen, scAdd",
	        		  };
	        	  }	 
	        	  else
	        	  {
	        		  digitalData.cart = {
	        			event :  "scAdd",
	        		  };	
	        	  }
        	    }
           });

           digitalData.product = products;

           try {
        		 _satellite.track("Add Cart");
        	 } catch (e) {}
		   }    
           window.location.href = $(this).attr('href');         
		});
		
		$(document).on("click","#deleteList", function(e){
    					 	digitalData={
    					 				   event: "delete list",
    									   listName: $(this).parent().parent().parent().find('.list-name')[0].innerText
    									}
    						try {
    									_satellite.track("listDeleted");
    							}catch(e){}
	 	 });
		


		
		$(document.body).on('click','.addListToCarts_filter',function(event){
			event.preventDefault();
			if ($(this).attr("disabled") != "disabled") {
			var assemblyCount = $(".js-assembly-qty").val();
			$(this).attr("href",$(this).attr("href")+$("#listCodeQtyChange").val());
			var products = [];
			cartLocation(_AAData.eventType);
			function cartLocation(addToCartLocation){
        		products.push({
        	       productInfo: {
        	                "productID": productCodes,
        	                "addToCartLocation": addToCartLocation,       	                     	           
        	            }
        	   });
        	  digitalData.eventData = {
        	        			 "linktype" : "",
        	        			 "linkName" : "Add to Cart",
        	        			 "onClickPageName" : addToCartLocation
        	      		 }
        	  if($(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0" )
	        	{ 
	        		digitalData.cart = {
	        				cartId:  $("#currentCartId").val(),	
	        				event :  "scOpen, scAdd"
	            	};
	        	}
        	  else
	        	{
	        		digitalData.cart = {
	        				cartId:  $("#currentCartId").val(),	
	            			event :  "scAdd"
	           	    };	
	            }
        	 digitalData.product = products;
        	 digitalData.listName = $('.list-headline').text();
        	 try {
            	    _satellite.track("addListToCart");
            	} catch (e) {}
            }
            window.location.href = $(this).attr('href');
			}
		});
		
		$(document.body).on('click','.clearQuantitieslink',function(event){
			event.preventDefault();
			var url= ACC.config.encodedContextPath +"/savedList/clearQuantities?wishListCode="+$("#listCodeQtyChange").val();
			window.location.href = url;
		});
		
	},
	
	bindPagination:function(){
		
		var viewpagination =$("#pageSizeSavedListDetail1").val();
	
		$.each($(".savedlistdetails-pagination .pagination li a"), function(i, aTag){
			$(aTag).prop('href',$(aTag).prop('href')+"&pagesize="+viewpagination);
		});
	
	},
	
		printListPage:function() {
			$(document.body).on('click','.print-list',function(e){
				e.preventDefault();
				 digitalData.eventData={
						 shareMedium:"Print",
						}
				 try {
						_satellite.track("printList");
					}catch(e){}
		 		$(".showPrint").css("display","block");
		 		let now = new Date().toString().split(" ");
		 		document.getElementById("printedTime").innerHTML = now[1] + " " + now[2] + ", " + now[3];
		 		window.print();
			});
	 	},
	
	 	movedEntireList:function(){
	 			$(".moveEntireSaveList_list").on('click',function (){
					if(!$('.moveselected_list_filter').hasClass("disabled")){
						if(screen.width<=700){
							$('.movedselectedtolist').removeClass('hidden');
							$("#cboxWrapper").css('overflow','initial');
							$("#cboxLoadedContent").css('margin-top','0px !important');
							ACC.colorbox.open("", {
								html: $(".movedselectedtolist").show(),
								width: "1200px",
								escKey: false,
								className:"list-mobileOption",
								overlayClose: false,
								onComplete: function(e) {
								   $(document).on("click",".icon-close", function(e){
									   window.location.reload(true);
								   });
								},
						   });
						}
						else{
							var state = $(this).data('state');
							var element = $(this).closest(".dropdown-entirelist").find(".movedselectedtolist");
							//console.log('State',state,'element',element);
							ACC.product.showMoreAndLess(this,state, element,"", "", 0);
							switch (state) {
								case "expand": {
								$('.moveselected_list_filter').css("background-color","#F1F2F2");
									break;
								}
								case "collapse": {
								$('.listName-alreadyexits-error').hide();
									$('#empty_listName_il').hide();
									$('.entirelist-name-alreadyexits-error').hide();
									$("#empty_listName").hide();
									$('.moveselected_list_filter').css("background-color","#FFFFFF");
									break;
								}
								case undefined: break;
							}
						}
					}
		        });
				$(document).on("click", function(e){
					let target = $(e.target);
					if(!target.hasClass("dropdown-movetolist") && !target.parents(".dropdown-movetolist").length){
						$('[onclick="ACC.savedlist.showMoveToListPopup(this,\'collapse\')"]').trigger("click");
					}
				});
			},
			showMoveToListPopup: function(ref, state) {
				let target = $(ref);
				let movetoListWrapper = target.next('.movetoList-wrapper');
				if (screen.width <= 700) {
				  $("#cboxWrapper").css('overflow', 'initial');
				  $("#cboxLoadedContent").css('margin-top', '0px !important');
				  ACC.colorbox.open("", {
					html: movetoListWrapper.show(),
					width: "1200px",
					escKey: false,
					className: "list-mobileOption",
					overlayClose: false,
					onComplete: function (e) {
					  $(document).on("click", ".icon-close", function (e) {
						window.location.reload(true);
					  });
					}
				  });
				}
				else {
				  //ACC.product.showMoreAndLess(ref, state, movetoListWrapper, "", "", 0);
				  if (state == "expand") {
					$('[onclick="ACC.savedlist.showMoveToListPopup(this,\'collapse\')"]').trigger("click");
					target.attr("onclick", "ACC.savedlist.showMoveToListPopup(this,'collapse')");
					target.find(".movetolist-angle").css({ transform: "rotate(180deg)" });
					movetoListWrapper.slideDown('hidden');
				  }
				  else {
					target.attr("onclick", "ACC.savedlist.showMoveToListPopup(this,'expand')");
					target.find(".movetolist-angle").css({ transform: "rotate(0deg)" });
					movetoListWrapper.slideUp('hidden');
				  }
				  $('.listName-alreadyexits-error').hide();
				  $('#empty_listName_il').hide();
				  $('.entirelist-name-alreadyexits-error').hide();
				  $("#empty_listName").hide();
				}
			},
	checkingAddListToCart: function(){
		if($(".page-detailsSavedListPage").length){	//checking Saved List Page
			let disabled = false;
			let ref = $(".add_to_cart_form");
			for(let i=0; i<ref.length; i++){
				if(ref.eq(i).find("#productSelect").children("button").is(":disabled")){
					disabled = true;
					break;
				}
				// Check if Request Quote button exists (by class)
				if (ref.find(".requestQuoteBtnPDP").length > 0) {
					disabled = true;
					break;
				}
			}
			let target = $(".addListToCart_filter_sec");
			if(disabled){
				target.addClass("addlisttocart-disabled");
				target.children(".addListToCarts_filter").attr("disabled", "disabled");
				target.children(".addListToCarts_filter").popover({ placement: 'top', trigger: 'hover', container: 'body' });
					target.children(".addListToCarts_filter").on('shown.bs.popover', function () {
						$(".popover").addClass("bg-danger");
					});
			}
			else{
				target.removeClass("addlisttocart-disabled");
				target.children(".addListToCarts_filter").removeAttr("disabled");
				target.children(".addListToCarts_filter").popover('destroy');
				if(ACC.savedlist.listPageCartLink && ACC.savedlist.listPageCartLink != ''){
					target.children(".addListToCarts_filter").attr("href", ACC.savedlist.listPageCartLink);
				}
			}
		}
	},
	setQtyIdDataVal: function (){
		$(".qtyId").each(function(e){
    		$(this).attr("data-val", $(this).val());
    	});
	},
	formatNumberComma: function(n) {
		return (Number(n) || 0).toLocaleString('en',{minimumFractionDigits: 2, maximumFractionDigits:2});
	},
	updatePrice: function($target, qtyEle) {
		var targetParent = $target.parents(".saved-list-sec");
		targetParent.find('#update_qty_list').text("Quantity Updated").fadeIn().fadeOut(1000);
    	var price = targetParent.find(".js-customer-price").val() || 0;
    	var quantity = targetParent.find(".qtyId").val();
    	var totalPrice = (Number(price).toFixed(2) * quantity).toFixed(2);
    	var totalPriceStr = targetParent.find(".tot-amt-list").html("$"+ ACC.savedlist.formatNumberComma(totalPrice));
    	var cumulativeTotPrice = Number($('.cumulative-tot-price').find("strong").text().replaceAll(/[$,]/g, '') || 0);
    	var prevquantity = Number(qtyEle.attr('data-val') || 0);
    	if(quantity > prevquantity){
    		var diffquantity = quantity - prevquantity;
    		var priceInc =  diffquantity * price;
    		cumulativeTotPrice += priceInc;
    	}else if(quantity < prevquantity){
    		var diffquantity = prevquantity - quantity;
    		var priceDec = diffquantity * price;
    		cumulativeTotPrice -= priceDec;
    	}
    	qtyEle.attr('data-val', quantity);
    	targetParent.find("#quantity").val(quantity);
    	var id = targetParent.find('.select_product_checkbox').attr('data-role');
    	var productCode = $('#prodCode'+id).val();
		$("#prodQty_"+productCode).val(quantity);
    	var productItemNumber = $('#prodItemNumber'+id).val();
		document.getElementById("prodQtyItem_" + productItemNumber).value = quantity;
    	$('.cumulative-tot-price, .cumulative-tot-price-print').find("strong").text("$" + ACC.savedlist.formatNumberComma(cumulativeTotPrice.toFixed(2)));
	},
	toggleErrorState: function (refParent, getInput, hasError) {
		var getStatus = getInput.siblings('.outofstockStatus').val();
		refParent.find('.intervalQtyError_list').toggleClass("hidden", !hasError);
		refParent.find(".list-item-qty").toggleClass("border-red", hasError);
		if (!getStatus) {
			refParent.find('#productSelect button').attr("disabled", hasError ? "disabled" : null);
		}
		getInput.toggleClass("qtyBoxError", hasError);
	},
	checkMinQuantityPageload: true,
	checkMinQuantityList: function (element, curstatus) {
		var intervalQTYRestrict = true;
		if (ACC.savedlist.checkMinQuantityPageload && ($(".intervalQtyInfo_list").length || $(".minOrderQtyInfo_list").length)) {
			var leng = $('.product__list .saved-list-singleProduct').length;
			for (var i = 0; i < leng; i++) {
				var refParent = $('.product__list .saved-list-singleProduct').eq(i);
				var getInput = refParent.find('.quantity_updated');
				var refIntervalQtyInfo = refParent.find('.intervalQtyInfo_list');
				var refMinOrderQtyInfo = refParent.find('.minOrderQtyInfo_list');
				if (refIntervalQtyInfo.length || refMinOrderQtyInfo.length) {
					if (refParent.find('.js-uom-selector').eq(0).length > 0) {
						if (Number(refParent.find('.js-uom-selector').eq(0).find(":selected").data("inventorymultiplier")) != 1) {
							intervalQTYRestrict = false;
							refIntervalQtyInfo.addClass("hidden");
							refMinOrderQtyInfo.addClass("hidden");
							ACC.savedlist.toggleErrorState(refParent, getInput, false);
						} else {
							if (getInput.data('min-qty') && getInput.data('min-qty') != "0" && getInput.data('min-qty') != "1" && getInput.data('min-qty') != '') {
								refIntervalQtyInfo.removeClass("hidden");
							}
							if (getInput.data('min-orderqty') && getInput.data('min-orderqty') != "0" && getInput.data('min-orderqty') != "1"
								&& getInput.data('min-orderqty') != '') {
								refMinOrderQtyInfo.removeClass("hidden");
							}
						}
					}
					if (intervalQTYRestrict) {
						if (refIntervalQtyInfo.is(':visible')) {
							var validateQtyInterval = (Number(getInput.val().trim()) % getInput.data('min-qty')) == 0 && getInput.val().trim() != '';
							ACC.savedlist.toggleErrorState(refParent, getInput, !validateQtyInterval);
						}
						if (refMinOrderQtyInfo.is(':visible')) {
							var minOrderQty = getInput.data('min-orderqty');
							if (minOrderQty && minOrderQty != "0" && minOrderQty != "1" && minOrderQty != '') {
								var validateMinOrderQty = Number(getInput.val().trim()) >= getInput.data('min-orderqty') && getInput.val().trim() != '';
								ACC.savedlist.toggleErrorState(refParent, getInput, !validateMinOrderQty);
							}
						}
					}
					intervalQTYRestrict = true;
				}
				ACC.savedlist.listQtyUpdate(getInput, "checkMinQuantityList if");
			}
		}
		else if (ACC.savedlist.checkMinQuantityPageload){
			var leng = $('.product__list .saved-list-singleProduct').length;
			for (var i = 0; i < leng; i++) {
				var refParent = $('.product__list .saved-list-singleProduct').eq(i);
				var getInput = refParent.find('.quantity_updated');
				ACC.savedlist.listQtyUpdate(getInput, "checkMinQuantityList else");
			}
		}
		ACC.savedlist.checkMinQuantityPageload = false;
		var getE = element;
		intervalQTYRestrict = true;
		if (curstatus) {
			getE = element.siblings(".js-update-entry-quantity-input");
		} else {
			getE = element;
		}
		var targetParent = getE.parents(".saved-list-sec");
		if (targetParent.find('.js-uom-selector').eq(0).length > 0) {
			if (Number(targetParent.find('.js-uom-selector').eq(0).find(":selected").data("inventorymultiplier")) != 1) {
				intervalQTYRestrict = false;
			}
		}
		if (intervalQTYRestrict) {
			if (getE.data('min-qty') && getE.data('min-qty') != "0" && getE.data('min-qty') != "1" && getE.data('min-qty') != '') {
				var validateQtyInterval = (Number(getE.val().trim()) % getE.data('min-qty')) == 0 && getE.val().trim() != '';
				ACC.savedlist.toggleErrorState(targetParent, getE, !validateQtyInterval);
			}
			if (getE.data('min-orderqty') && getE.data('min-orderqty') != "0" && getE.data('min-orderqty') != "1"
				&& getE.data('min-orderqty') != '') {
				var validateMinOrderQty = Number(getE.val().trim()) >= getE.data('min-orderqty') && getE.val().trim() != '';
				ACC.savedlist.toggleErrorState(targetParent, getE, !validateMinOrderQty);
			}
		}
		ACC.savedlist.listQtyUpdate(getE, "checkMinQuantityList all");
		ACC.savedlist.checkingAddListToCart();
	},
	  bindSaveListBeforeunload: function(){
		  if($('body').hasClass('page-detailsSavedListPage')){
			  $(window).bind("beforeunload", function(){
					var selectedProducts = $.grep(xList, function(x){
						return (x || '').length;
					});
					
					ACC.savedlist.savedListSessionStorage("set", selectedProducts.join(" "));
			  });
		  }
	  },
	  savedListSessionStorage: function(mode, data){
		  var sessionKey = "savedList_selectedProduct";
		  if(mode == "set"){
			  return sessionStorage.setItem(sessionKey, data);
		  }
		  
		  if(mode == "get"){
			  return sessionStorage.getItem(sessionKey);
		  }
		  
		  if(mode == "remove"){
			  return sessionStorage.removeItem(sessionKey);
		  }
	  },
	  deSelectAllList: function(ref){
		let target = $(ref);
		if(!target.hasClass("disabled")){
			if($('.select_product_checkbox_all').prop("checked")){
				$('.select_product_checkbox_all').trigger("click");
			}
			else{
				$(".saved-list-sec:visible .select_product_checkbox").each(function (index) {
					let ref = $(this);
					if (ref.prop("checked")) {
						ref.trigger("click");
					}
				});
			}
			$('.list-filters-row').toggle();
		}
	  },
	  selectAllList: function(ref){
		if(!$('.select_product_checkbox_all').prop("checked")){
			$('.select_product_checkbox_all').trigger("click");
		}
		else{
			$(".saved-list-sec:visible .select_product_checkbox").each(function (index) {
				let ref = $(this);
				if (!ref.prop("checked")) {
					ref.trigger("click");
				}
			});
		}
		$('.list-filters-row').toggle();
	  },
	  listItemCheckStatus: function(alreadySelected) {
		let enableATCFlag = true;
		let isAllChecked = true;
		let itemCounter = 0;
		$(".saved-list-sec:visible .select_product_checkbox").each(function (index) {
			let ref = $(this);
			let refParent = ref.parents(".saved-list-sec");
			if (alreadySelected && $.inArray(refParent.find(".productSelectionCode").val(), alreadySelected) != -1) { //onload pre select check
				ref.prop("checked", true);
				ref.parent().css('background', '#78a22f');
				refParent.css('border', '1px solid #79a22f');
			}
			if (ref.prop("checked")) {
				itemCounter++;
				if (refParent.find("#notSellable").length || refParent.find("#hideCSP").length || refParent.data("nontransferableflag") || refParent.data("outofstockimage")) {
					enableATCFlag = false;
				}
			}
			else {
				isAllChecked = false;
			}
		});
	
		if (itemCounter) {
			if (enableATCFlag) {
				$(".SavedSelectedProduct").removeClass("disabled");
				$(".SavedSelectedProduct2").removeClass("disabled");
				$(".list_addlisttocart_wrapper").removeClass("disabled");
			}
			else {
				$(".SavedSelectedProduct").addClass("disabled");
				$(".SavedSelectedProduct2").addClass("disabled");
				$(".list_addlisttocart_wrapper").addClass("disabled");
			}
			$(".remove_selected_filter").removeClass("disabled");
			$(".moveselected_list_filter").removeClass("disabled");
			$(".de_selected_filter").removeClass("disabled");
			$(".list-item-counter-text").removeClass("text-white hidden");
			$(".list-item-counter").html(itemCounter);
			$(".list-item-counter-s").html(itemCounter > 1 ? 's' : '');
			if (ACC.savedlist.isRecommendedListPage) {
				$(".recomDetailSelectedCopyListBtn").removeClass("hidden");
				$(".recomDetailCopyListBtn").addClass("hidden");
			}
		}
		else {
			$(".list-item-counter-text").addClass($(".page-detailsSavedListPage").length && ACC.global.wWidth < 1024 ? "text-white": "hidden");
			$(".SavedSelectedProduct").addClass("disabled");
			$(".SavedSelectedProduct2").addClass("disabled");
			$(".remove_selected_filter").addClass("disabled");
			$(".moveselected_list_filter").addClass("disabled");
			$(".list_addlisttocart_wrapper").addClass("disabled");
			$(".de_selected_filter").addClass("disabled");
			if (ACC.savedlist.isRecommendedListPage) {
				$(".recomDetailSelectedCopyListBtn").addClass("hidden");
				$(".recomDetailCopyListBtn").removeClass("hidden");
			}
		}
		if (isAllChecked) {
			$(".select_product_checkbox_all").prop("checked", true);
		}
		$(".movedselectedtolist").addClass("hidden");
		$('.moveselected_list_filter').css("background-color", "#FFFFFF");
	},
	//PLP changes for Request Quote Button Start.
	
	productsList: [],
	quoteParent: false,
	quoteCode: false,
	requestQuotePopupplp: function (e,parentClass, skuCode) {
		let productDescription= $(e).data("productDescription");
		ACC.savedlist.quoteParent = $(e).parents("." + parentClass);
		ACC.savedlist.quoteCode = skuCode;
		ACC.savedlist.productsList = [];
		ACC.colorbox.open("", {
			html: $(".request-quote-popupPLPPDP").html(),
			width: "903px",
			height: 'auto',
			className: "request-quote-overlay",
			onComplete: function (e) {
				$('.request-quote-overlay .items-scroll').css('height', 'auto');
				$('.request-quote-overlay .quote-jobstartdate').addClass('quote-jobstartdate-new');
				$('.request-quote-overlay .save-continue-btn').css('visibility', 'hidden');
				if(!$('.product-variant-table__values').is(':visible')){
					$('.request-quote-overlay .request-list-title').html(productDescription);
				}else{
					$('.request-quote-overlay .request-list-title').html($('.variantproduct-name').val());
				}		
				$.colorbox.resize();
				$('.quote-jobstartdate-new').datepicker({ minDate: 0,  dateFormat: 'yy-mm-dd' });
				ACC.savedlist.getNearByStores();
			}
		});
	},
	requestQuoteOverlaySaveplp: function(e) {
		var is_valid = true;
		if ($('.request-quote-overlay .quote-jobname').val().trim() == "") {
			$('.request-quote-overlay .quote-jobname').parent().addClass('has-error');
			is_valid = false;
		}
		if ($('.request-quote-overlay .quote-jobstartdate').val().trim() == "") {
			$('.request-quote-overlay .quote-jobstartdate').parent().addClass('has-error');
			is_valid = false;
		}
		if (is_valid) {
			let formParent = $('.request-quote-overlay');
			ACC.savedlist.productsList = [];
			let branch = formParent.find(".quote-branch").find(":selected").html();
			let targetQty = $('.request-quote-overlay').find(".input-prod-qty").val();
			let targetPri, targetUom, targetCod, targetNum, targetDec;
			if (targetQty == '') {
				targetQty = 1;
			}
			if(!$('.product-variant-table__values').is(':visible')){
				targetNum = ACC.savedlist.quoteParent.find('#requestQuoteButtonItemnumber').val();
				targetDec = ACC.savedlist.quoteParent.find('#requestQuoteButtonDesc').val();
				if ($(".pdpRedesignuom-sticky .custom-dropdown-label").length) {
					if($(".multipleUomTable").length) {
						targetPri = ACC.savedlist.quoteParent.find('.quoteUom-CustomerPrice').val() == '' ? ACC.savedlist.quoteParent.find('.quoteUom-Price').val() : ACC.savedlist.quoteParent.find('.quoteUom-CustomerPrice').val();
						targetPri = Number(targetPri).toFixed(2);
						targetUom = ACC.savedlist.quoteParent.find('.quoteUom-Measure').val();
						targetCod = ACC.savedlist.quoteCode ? ACC.savedlist.quoteCode : ACC.savedlist.quoteParent.find('.quoteUom-code').val();
					} else {
						let target = $(".pdpRedesignuom-sticky .custom-dropdown-label").eq(0);
						let quoteMeasure = target.text().split('/');
						targetPri = quoteMeasure[0].split('$')[1];
						targetUom = quoteMeasure[1].split('[')[0];
						targetCod = target.data("code");
					}
				} else if (ACC.savedlist.quoteParent.find(".csp-price-format").length) {
					let quoteMeasure = ACC.savedlist.quoteParent.find(".csp-price-format");
					targetPri = quoteMeasure.find(".check_price").eq(0).text().split('$')[1];
					let uom = quoteMeasure.find(".check_price").eq(1).text();
					targetUom = uom.includes("/") ? uom.split('/')[1] : uom;
					if (ACC.savedlist.quoteParent.find(".card-variant-item").length) {
						let visibleCardVariantIem = ACC.savedlist.quoteParent.find(".card-variant-item:visible");
						targetPri = visibleCardVariantIem.find(".csp-price-format").find(".check_price").eq(0).text().split('$')[1];
						let cardVariantPriceUOM = visibleCardVariantIem.find(".csp-price-format").find(".check_price").eq(1).text();
						targetUom = cardVariantPriceUOM.includes("/") ? cardVariantPriceUOM.split('/')[1] : cardVariantPriceUOM;
					}
					targetCod = ACC.savedlist.quoteCode;
				} else {
					targetPri = ACC.savedlist.quoteParent.find('.quoteUom-CustomerPrice').val() == '' ? ACC.savedlist.quoteParent.find('.quoteUom-Price').val() : ACC.savedlist.quoteParent.find('.quoteUom-CustomerPrice').val();
					targetPri = Number(targetPri).toFixed(2);
					targetUom = ACC.savedlist.quoteParent.find('.quoteUom-Measure').val();
					targetCod = ACC.savedlist.quoteCode ? ACC.savedlist.quoteCode : ACC.savedlist.quoteParent.find('.quoteUom-code').val();
				}
			} else {				
					targetPri = ACC.savedlist.quoteParent.find('.trackCSP').val() == '' ? ACC.savedlist.quoteParent.find('.trackRetailPrice').val() : ACC.savedlist.quoteParent.find('.trackCSP').val();
					targetPri = Number(targetPri).toFixed(2);
					targetUom = ACC.savedlist.quoteParent.find('.quoteUomMeasure').val();
		     		targetCod = ACC.savedlist.quoteParent.find('.trackProductCode').val();
					targetNum = $('.pdp-itemnumber').val();
					targetDec = $('.variantproduct-name').val();
			}			
			targetNum = !targetNum ? '' : targetNum;
			targetDec = !targetDec ? '' : targetDec;
			targetQty = !targetQty ? '1' : targetQty;
			targetPri = !targetPri || targetPri =='false' || targetPri == 'NaN' ? '0.00' :  $.trim(targetPri.replaceAll(',',''));
			targetUom = !targetUom || targetUom =='false' ? 'Each' : $.trim(targetUom);
			targetCod = !targetCod ? '' : targetCod;
			let testRow = targetNum + "^" + targetDec + "^" + targetQty + "^" + targetPri + "^" + targetUom + '^' + targetCod;
			ACC.savedlist.productsList.push(testRow);
			ACC.savedlist.productsList = ACC.savedlist.productsList.join("|");
			formParent.find(".requested-quote-num").html(targetQty);
			formParent.find(".requested-quote-branch").html(branch);
			formParent.find(".request-quote-step2").removeClass('hidden');
			formParent.find(".request-quote-step1").addClass('hidden');
			$.colorbox.resize();
		}
	},
	requestQuoteOverlayFormSubmitplp: function(e) {
		if(!$(e).hasClass("disabled")){
			$(e).addClass("disabled");
			let formParent = $('.request-quote-overlay');
			let jobName = formParent.find(".quote-jobname").val();
			let jobStartDate = formParent.find(".quote-jobstartdate").val();
			let branch = formParent.find(".quote-branch").val();
			let jobDescription = formParent.find(".quote-jobdescription").val();
			let PagenamePLP = $(".pagename").val();
			let notes = $.trim(formParent.find(".quote-notes").val().replace(/\n/g," ").replaceAll("|","-"));
			let quoteData = { jobName: jobName, jobStartDate: jobStartDate, branch: branch, jobDescription: jobDescription, notes: notes, selectedProductList: ACC.savedlist.productsList,productsList:''};
			$.ajax({
				url: ACC.config.encodedContextPath + '/my-account/requestQuote',
				method: "POST",
				datatype: "json",
				data: quoteData,
				success: function (data) {
					if(data.toLowerCase() == "success"){
						ACC.colorbox.close();
					}
					else{
						ACC.savedlist.quotePopupError(1000, 'close');
						ACC.pendo.captureEvent("REQUESTQUOTEFAIL", {'listCode':listCode});
					}
				},
				error: function () {
					ACC.savedlist.quotePopupError(1000, 'close');
					ACC.pendo.captureEvent("REQUESTQUOTEFAIL", {'listCode':listCode});
				}
			});
			ACC.adobelinktracking.requestQuotePLP(e,'','Save and Continue: Submit',PagenamePLP+': request a quote popup: submit','');
		
			}
	},
	//PLP changes for Request Quote Button End.

	requestQuoteListNum: "",
	requestQuoteListLength: 0,
	productsList: [],
	requestQuotePopup: function(e, listId, num) {
		let listTitle = $(e).data("listtitle");
		ACC.savedlist.requestQuoteListNum = listId;
		ACC.savedlist.requestQuoteListLength = Number(num);
		ACC.savedlist.productsList = [];
		ACC.colorbox.open("", {
			html: $(".request-quote-popup").html(),
			width: "903px",
			height: 'auto',
			className: "request-quote-overlay",
			onComplete: function (e) {
				$('.request-quote-overlay .items-scroll').css('height', 'auto');
				$('.request-quote-overlay .quote-jobstartdate').addClass('quote-jobstartdate-new');
				$('.request-quote-overlay .save-continue-btn').css('visibility', 'hidden');
				$('.request-quote-overlay .request-list-title').html(listTitle);
				$('.quote-jobstartdate-new').datepicker({ minDate: 0,  dateFormat: 'yy-mm-dd' });
				ACC.savedlist.getNearByStores();
				$(".request-quote-overlay .js-script-input-event-bind").each(function(){
					$(this).on({ "keydown": ACC.global.scriptInputKeyDown, "keyup": ACC.global.scriptInputKeyUp, "paste": ACC.global.scriptInputPaste, "blur": ACC.global.scriptInputBlur });
				});
			}
		});
	},
	generateLabelSheetPopup: function () {
		let checkItemStatus = $("span.list-item-counter-text").hasClass("hidden");
		if (checkItemStatus) {
			$("#generateLabelSheetcount").html(
				"[" + $(".list-page-counter").html() + "]"
			);
		} else {
			$("#generateLabelSheetcount").html(
				"[" + $(".list-item-counter").html() + "]"
			);
		};
		ACC.colorbox.open("", {
		html: $(".generate-label-popup").html(),
		width: "803px",
		
		className: "generate-label-overlay",
			onComplete: function (e) {
				$(document).on("click", ".close-generatelabel-btn", function (e) {
					e.preventDefault();
					ACC.colorbox.close();
				});
			},
		});
  	},
	generatelabelImageandText: function (el) {
		var value = el.value; 
		var visibleIds = ['previewTexts' + value, 'labelPreview' + value];
		if (value === '10') {
			visibleIds.push('extraInfo10');
		}
		$('.generate-preview-text, .label-preview-container').each(function () {
			var $element = $(this);
			var elementId = $element.attr('id');
			if (visibleIds.includes(elementId)) {
				$element.addClass('visible');
			} else {
				$element.removeClass('visible');
			}
		});
	},
	getGenerateLabels: function() {
		let checkItemStatus = $("span.list-item-counter-text").hasClass("hidden");
		$(".generatelabel-btn").attr('disabled', true);
		let checkedItems = [];
		$("input[name='selectedItems']:checked").each(function () {
			checkedItems.push($(this).val());
		});
		
		var href= ACC.config.encodedContextPath + "/savedList/getListQrPdf";
		let selectedItems = checkedItems.length === 0 ? '""' : checkedItems.join(",");
		let  label = $("input[name='label']:checked").val();
		
		if (checkItemStatus) {
			let wishListCode = $("#wishListListName").val().toString();
			href=href+'?selectedItems='+ selectedItems +'&label='+label+'&wishListCode='+wishListCode;
		} else {
			var checkedProductIds = [];
			$(".select_product_checkbox:checked").each(function () {
				checkedProductIds.push($(this).data("listcod"));
			});
			let productCode = checkedProductIds.join(",");
			href=href+'?selectedItems='+ selectedItems +'&label='+label+'&productCode='+productCode;
		}
		var $download = $("<a>")
        .attr("href", href);
		$download[0].click();
		ACC.colorbox.close();
	},
	getNearByStores: function() {
		$.ajax({
			url: ACC.config.encodedContextPath + '/store-finder/stores',
			method: "GET",
			datatype: "json",
			data: { "miles": 100, "latitude": $('.latitude_val').val(), "longitude": $('.longitude_val').val() },
			success: function (data) {
				var responseData = JSON.parse(data);
				if (responseData) {
					let target = $(".request-quote-overlay .quote-branch");
					if (responseData['nearBy']) {
						target.html('<option value="' + target.data("defaultstoreid") + '">' + target.data("defaultstorename") + '</option>');
						for (let i = 0; i < responseData['nearBy']['data'].length; i++) {
							target.append('<option value="' + responseData['nearBy']['data'][i].storeId + '">' + responseData['nearBy']['data'][i].town + " #" + responseData['nearBy']['data'][i].storeId + '</option>');
							if (i == 7) {
								break;
							}
						}
						target.prop('selectedIndex', 0);
						$('.request-quote-overlay .save-continue-btn').css('visibility', 'visible');
					}
					else {
						target.html('<option value="' + target.data("defaultstoreid") + '">' + target.data("defaultstorename") + '</option>');
						target.prop("disabled", "disabled");
						target.css({ background: "#fff", opacity: 1 });
						target.addClass('text-default');
						$('.request-quote-overlay .save-continue-btn').css('visibility', 'visible');
					}
				}
				else {
					ACC.savedlist.quotePopupError(1000, 'close');
				}
			},
			error: function () {
				ACC.savedlist.quotePopupError(1000, 'close');
			}
		});
	},
	requestQuoteInputDesc: function(e) {
		let target = $(e);
		if (target.val().length) {
			target.parents('.input-container').find('.add-row-product').removeClass('disabled');
		}
		else {
			target.parents('.input-container').find('.add-row-product').addClass('disabled');
		}
	},
	listAddRowProduct: function(e) {
		let target = $(e);
		let targetParent = target.parents(".input-container");
		let inputDesc = targetParent.find(".input-prod-des").val();
		let inputQty = targetParent.find(".input-prod-qty").val();
		if (target.hasClass("btn-gray")) {
			targetParent.slideUp(function () {
				$(this).remove();
			});
		}
		else if (!target.hasClass("btn-gray") && !inputDesc == "") {
			target.addClass("btn-gray").removeClass("btn-default").html("<i class='glyphicon glyphicon-ok'></i>");
			targetParent.find(".input-prod-qty").val(inputQty == "" ? 1 : inputQty);
			let refHTML = '<div class="row form-group m-b-5 items-container input-container"><div class="col-md-5 col-xs-11 p-r-0"><input type="text" name="productDescription" onkeyup="ACC.savedlist.requestQuoteInputDesc(this)" class="form-control input-prod-des js-script-input-event-bind" maxlength="900" placeholder="Product Description" /></div><div class="col-md-4 col-xs-5 ipt-itemno"><input type="text" name="itemNumber" class="form-control b-t-0-xs b-r-0-xs input-prod-num js-script-input-event-bind" maxlength="25" placeholder="Item #" /></div><div class="col-md-1 col-xs-3 padding0"><input type="text" name="qty" class="form-control b-t-0-xs input-prod-qty js-script-input-event-bind" onkeyup="if(/\D/g.test(this.value)) this.value=this.value.replace(/\D/g,\'\')" placeholder="Qty" /></div><div class="col-md-2 col-xs-3 pad-lft-10 p-l-0-xs p-r-0-xs"><div class="btn btn-default btn-block bold-text add-row-product disabled" onclick="ACC.savedlist.listAddRowProduct(this)">Add</div></div></div>';
			$(refHTML).insertAfter(targetParent);
			$(".request-quote-overlay .js-script-input-event-bind").each(function(){
				$(this).on({ "keydown": ACC.global.scriptInputKeyDown, "keyup": ACC.global.scriptInputKeyUp, "paste": ACC.global.scriptInputPaste, "blur": ACC.global.scriptInputBlur });
			});
			$('.request-quote-overlay .items-scroll').scrollTop($('.request-quote-overlay .items-scroll').height());
			setTimeout(function () {
				target.html('<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 12 12" fill="none"><path d="M7.76107 6.19202L10.8882 3.06488C11.0725 2.88055 11.176 2.63057 11.176 2.36992C11.176 2.10926 11.0725 1.85928 10.8882 1.67495L10.1929 0.979594C10.0085 0.795302 9.75855 0.691772 9.49789 0.691772C9.23724 0.691772 8.98726 0.795302 8.80293 0.979594L5.67579 4.10674L2.54864 0.979594C2.36432 0.795302 2.11433 0.691772 1.85368 0.691772C1.59303 0.691772 1.34305 0.795302 1.15872 0.979594L0.463359 1.67495C0.279068 1.85928 0.175537 2.10926 0.175537 2.36992C0.175537 2.63057 0.279068 2.88055 0.463359 3.06488L3.5905 6.19202L0.463359 9.31917C0.279068 9.5035 0.175537 9.75348 0.175537 10.0141C0.175537 10.2748 0.279068 10.5248 0.463359 10.7091L1.15872 11.4045C1.34305 11.5887 1.59303 11.6923 1.85368 11.6923C2.11433 11.6923 2.36432 11.5887 2.54864 11.4045L5.67579 8.27731L8.80293 11.4045C8.98726 11.5887 9.23724 11.6923 9.49789 11.6923C9.75855 11.6923 10.0085 11.5887 10.1929 11.4045L10.8882 10.7091C11.0725 10.5248 11.176 10.2748 11.176 10.0141C11.176 9.75348 11.0725 9.5035 10.8882 9.31917L7.76107 6.19202Z" fill="#78A22F"/></svg>');
			}, 2000);
		}
	},
	requestInputReset: function(e) {
		let target = $(e);
		if (target.val() != "") {
			target.parent('.has-error').removeClass('has-error');
		}
	},
	//Products added via input form
	inputFormProductList:[],
	requestQuoteOverlaySave: function(e) {
		var is_valid = true;
		if ($('.request-quote-overlay .quote-jobname').val().trim() == "") {
			$('.request-quote-overlay .quote-jobname').parent().addClass('has-error');
			is_valid = false;
		}
		if ($('.request-quote-overlay .quote-jobstartdate').val().trim() == "") {
			$('.request-quote-overlay .quote-jobstartdate').parent().addClass('has-error');
			is_valid = false;
		}
		if (is_valid) {
			let formParent = $('.request-quote-overlay');
			ACC.savedlist.productsList = [];
			ACC.savedlist.inputFormProductList = [];
			let listItems = $(".select_product_checkbox:checked");
			let listLength;
			let branch = formParent.find(".quote-branch").find(":selected").html();
			formParent.find(".input-container").each(function () {
				let target = $(this);
				let targetNum = target.find(".input-prod-num").val().trim().substring(0, 25);
				let targetDec = target.find(".input-prod-des").val();
				let targetQty = target.find(".input-prod-qty").val();
				let testRow = targetNum + "^" + targetDec + "^" + targetQty;
				if (targetDec != "") {
					ACC.savedlist.inputFormProductList.push(testRow);
				}
			});
			ACC.savedlist.inputFormProductList = ACC.savedlist.inputFormProductList.join('|')
			listLength = ACC.savedlist.requestQuoteListLength + ACC.savedlist.productsList.length;
			if (listItems.length) {
				listCode = "";
				listItems.each(function () {
					let target = $(this);
					let targetParent = target.parents(".saved-list-sec").find(".print-row-price");
					let targetCod = target.data("listcod");
					let targetNum = target.data("listnum");
					let targetDec = target.data("listdes");
					let targetQty = target.data("listqty");
					let targetPri = Number(targetParent.data("customerprice") != '' ?targetParent.data("customerprice"):0).toFixed(2);
					let targetUom = targetParent.data("uom");
					let testRow = targetNum + "^" + targetDec + "^" + targetQty + "^" + targetPri + '^' + targetUom + '^' + targetCod;
					ACC.savedlist.productsList.push(testRow);
				});
				listLength = ACC.savedlist.productsList.length;
			}
			ACC.savedlist.productsList = ACC.savedlist.productsList.join("|");
			formParent.find(".requested-quote-num").html(listLength);
			formParent.find(".requested-quote-branch").html(branch);
			formParent.find(".request-quote-step2").removeClass('hidden');
			formParent.find(".request-quote-step1").addClass('hidden');
			$.colorbox.resize();
		}
	},
	requestQuoteOverlayFormSubmit: function(e) {
		if(!$(e).hasClass("disabled")){
			$(e).addClass("disabled");
			let formParent = $('.request-quote-overlay');
			let jobName = formParent.find(".quote-jobname").val().trim().substring(0, 100);
			let jobStartDate = formParent.find(".quote-jobstartdate").val();
			let branch = formParent.find(".quote-branch").val();
			let jobDescription = formParent.find(".quote-jobdescription").val();
			let notes = $.trim(formParent.find(".quote-notes").val().replace(/\n/g," ").replaceAll("|","-"));
			let comment = $.trim(formParent.find(".quote-comment").val().replace(/\n/g," ").replaceAll("|","-"));
			let listCode = ($(".select_product_checkbox:checked").length) ? "" : ACC.savedlist.requestQuoteListNum;
			let quoteData = { jobName: jobName, jobStartDate: jobStartDate, branch: branch, jobDescription: jobDescription, notes: notes, comments: comment, productsList: ACC.savedlist.inputFormProductList , listCode: listCode, selectedProductList:ACC.savedlist.productsList};
			$.ajax({
				url: ACC.config.encodedContextPath + '/my-account/requestQuote',
				method: "POST",
				datatype: "json",
				data: quoteData,
				success: function (data) {
					if(data.toLowerCase() == "success"){
						ACC.colorbox.close();
					}
					else{
						ACC.savedlist.quotePopupError(1000, 'close');
						ACC.pendo.captureEvent("REQUESTQUOTEFAIL",{'listCode':listCode });
					}
				},
				error: function () {
					ACC.savedlist.quotePopupError(1000, 'close');
					ACC.pendo.captureEvent("REQUESTQUOTEFAIL",{'listCode':listCode });
				}
			});
			ACC.adobelinktracking.requestQuote(e,'','submit','my account : lists: request a quote popup');
		}
	},
	quotePopupError: function (num, close) {
		if(close){ACC.colorbox.close();}
		setTimeout(function () { 
			ACC.colorbox.open("", {
				html: "<div class='row'><div class='col-md-12 marginTopBVottom30'><p class='f-s-28 f-s-20-xs-px text-red font-Geogrotesque'>"+ ACC.config.requestQuotetext+ "</p><p class='f-s-28 f-s-20-xs-px text-default font-Geogrotesque m-t-35'>"+ACC.config.requestQuoteerrormesg+"</p></div></div>",
				width: "600px",
				className: "requestQuotePopupError text-center"
			});
		}, num);
	},
	listDelete: function(e, code) {
		let listName = $(e).data("listtitle");
		ACC.colorbox.open("", {
			html: '<div class="row text-center m-t-15"><div class="col-md-12 text-default text-center font-Geogrotesque f-s-28 f-s-18-xs-px m-b-15">' + ACC.config.listDeleteMsg + '<br>"' + listName + '?"</div><div class="col-md-3 col-md-offset-3 col-xs-6 col-xs-offset-0"><button class="btn btn-default btn-block" onclick="ACC.colorbox.close()">' + ACC.config.listCancel + '</button></div><div class="col-md-3 col-xs-6"><a class="btn btn-primary btn-block delete-list" href="' + ACC.config.encodedContextPath + '/savedList/deleteList?code=' + code + '">' + ACC.config.listDelete + '</a></div></div>',
			width: "600px",
			className: "requestQuotePopupError text-center"
		});
	},
	listShare: function(e, code) {
		let listName = $(e).data("listtitle");
		let target = $("#siteoneShareSavedListForm");
		target.find("#code").val(code);
		target.find("#listname").val(listName);
		target.find(".share-list-name").html(listName);
		$(".overlayBackground").fadeIn();
		if(ACC.global.wWidth < 1200){
			$(window).scrollTop(0);
		}
		$("#siteoneShareSavedListForm").fadeIn();
		if ($('.page-detailsSavedListPage').length > 0) {
			_AAData.popupPageName = ACC.config.sharelistpathingPageName;
			_AAData.popupChannel = ACC.config.myaccountpathingChannel;
			try {
				_satellite.track('popupView');
			} catch (e) { }
		}
	},
	filterCategory: ['agronomic', 'irrigation', 'landscape', 'lighting', 'nursery', 'tools', 'hardscapes'],
	filterPopupSelect: function (pEvent, type, group) {
		let ref = $(pEvent);
		let refStatus = ref.prop("checked");
		let target = $('[id^="filter-' + group + '-"]');
		if (type == "all" && !ACC.savedlist.isFilterPopupSelectAll) { //select All clicked
			for (let i = 0; i < target.length; i++) {
				if (target.eq(i).prop("checked") != refStatus) {
					target.eq(i).prop("checked", refStatus);
				}
			}
		}
		else if ($('[id^="filter-' + group + '-"]:checked').length == $('[id^="filter-' + group + '-"]').length) { //if all items checked
			ACC.savedlist.isFilterPopupSelectAll = true;
			$('#filter-' + group).prop("checked", true);
			ACC.savedlist.isFilterPopupSelectAll = false;
		}
		else if (!$('[id^="filter-' + group + '-"]:checked').length || (!refStatus && $('#filter-' + group).prop("checked"))) { //if all items unchekced
			ACC.savedlist.isFilterPopupSelectAll = true;
			$('#filter-' + group).prop("checked", false);
			ACC.savedlist.isFilterPopupSelectAll = false;
		}
	},
	listFilterUpdated: false,
	listFilterOptions: [],
	updateListFilter: function (pageload) { //checking for filter updates
		ACC.savedlist.listFilterUpdated = false;
		let newFilters = {
			search: "",
			brand: [],
			availability: "",
			agronomic: [],
			irrigation: [],
			landscape: [],
			lighting: [],
			nursery: [],
			tools: [],
			hardscapes: []
		};
		$("[data-listfilter]").each(function (e) {
			let target = $(this);
			let currentVal = target.prop("type") == 'text' ? target.val().toLowerCase() : target.prop("checked");
			if (pageload) {
				ACC.savedlist.listFilterOptions.push(currentVal);
			}
			if (currentVal === true || (target.prop("type") == 'text' && currentVal != "")) {
				let checkType = target.attr("id").split("-")[1].toLowerCase();
				if (e == 0) {
					newFilters.search = currentVal;
				}
				else if (target.prop("type") == 'radio') {
					newFilters.availability = currentVal;
				}
				else if (checkType == 'brand') {
					newFilters.brand.push(e);
				}
				else if (checkType == 'sh13') {
					newFilters.agronomic.push(e);
				}
				else if (checkType == 'sh14') {
					newFilters.irrigation.push(e);
				}
				else if (checkType == 'sh12') {
					newFilters.landscape.push(e);
				}
				else if (checkType == 'sh11') {
					newFilters.lighting.push(e);
				}
				else if (checkType == 'sh16') {
					newFilters.nursery.push(e);
				}
				else if (checkType == 'sh17') {
					newFilters.tools.push(e);
				}
				else if (checkType == 'sh15') {
					newFilters.hardscapes.push(e);
				}
			}
			if (target.data("status") != currentVal) {
				ACC.savedlist.listFilterUpdated = true;
				//console.log(e, target.data("status"));
			}
			target.data("status", currentVal);
			//console.log(e, target.data("status"), currentVal, target.data('listfilter'));
		});
		ACC.savedlist.applyListFilter(newFilters, pageload);
	},
	applyListFilter: function (newFilters, pageload) {
		let pageLoadRequired;
		if (!pageload && ACC.savedlist.listFilterUpdated) { //Expecting a pageLoad ith new filters
			pageLoadRequired = true;
			let daatString = "";
			let finalUrl = "";
			ACC.myquotes.filterPopup('hide', 500, '.list-filter-popup');
			loading.start();
			daatString += ACC.savedlist.dataStringFilter(newFilters.agronomic, 'agronomic');
			daatString += ACC.savedlist.dataStringFilter(newFilters.hardscapes, 'hardscapes');
			daatString += ACC.savedlist.dataStringFilter(newFilters.irrigation, 'irrigation');
			daatString += ACC.savedlist.dataStringFilter(newFilters.landscape, 'landscape');
			daatString += ACC.savedlist.dataStringFilter(newFilters.lighting, 'lighting');
			daatString += ACC.savedlist.dataStringFilter(newFilters.nursery, 'nursery');
			daatString += ACC.savedlist.dataStringFilter(newFilters.tools, 'tools');
			daatString += ACC.savedlist.dataStringFilter(newFilters.brand, 'brand');
			if (daatString == "" && newFilters.search == "") {
				finalUrl = ACC.config.encodedContextPath + "/savedList/listDetails?code=" + $("#savedListCode").val();
			}
			else {
				finalUrl = ACC.config.encodedContextPath + "/savedList/listDetails?q="+ newFilters.search +"%3Adate-asc" + daatString + "&searchtype=product&text=&viewtype=All&nearby=&inStock=&sort=&Filter+by+Category=on#";
			}
			//console.log("Expecting a pageLoad", newFilters, finalUrl);
			window.location = finalUrl;
		}
		if (!pageLoadRequired) {
			if (ACC.savedlist.listFilterUpdated) { //Creating the tags
				loading.start();
				ACC.savedlist.checkFilterTag('listfilter', '.list-filter-popup', '.filter-tags', pageload);
				//console.log("Updating Filter Tags", newFilters);
			}
			else {
				ACC.myquotes.filterPopup('hide', 500, '.list-filter-popup');
				//console.log("No Update", newFilters);
			}
		}
	},
	resetListFilter: function (target) {
		if($("#filter-Brand-1").length){
			$("[data-" + target + "]").each(function (e) {
				let target = $(this);
				if (target.prop('type') == "text" && target.val() != "") {
					target.val("");
				}
				else if (target.prop("checked")) {
					target.prop("checked", false);
				}
			});
			$(".from-input-label").fadeIn();
			ACC.savedlist.updateListFilter();
		}
		else{
			loading.start();
			window.location = ACC.config.encodedContextPath + "/savedList/listDetails?code=" + $("#savedListCode").val();
		}
	},
	dataStringFilter: function (targetArray, dataKey) {
		let daatString = "";
		if (targetArray.length) {
			for (let i = 0; i < targetArray.length; i++) {
				daatString += (dataKey == 'brand') ? '%3AsoproductBrandNameFacet%3A' : '%3Asocategory%3A';
				daatString += $("[data-listfilter]").eq(targetArray[i]).data('name').replaceAll(' ', '%2B');
			}
		}
		return daatString;
	},
	checkFilterTag: function (targetElem, popup, targetClass, pageload) { //Create parent in JSP with targetClass
		let targetParent = $(targetClass);
		targetParent.empty();
		$("[data-" + targetElem + "]").each(function (e) {
			let target = $(this);
			let targetInput = target.is('input');
			let currentVal = (targetInput) ? target.val().toLowerCase() : target.prop("checked");
			let targetCategory = target.attr("id");
			if (e == 0 && currentVal != "") {
				ACC.savedlist.createFilterTag("input", currentVal, '', e, targetElem, popup, targetClass);
			}
			else if (target.prop("checked")) {
				ACC.savedlist.createFilterTag("checkbox", target.data(targetElem), targetCategory, e, targetElem, popup, targetClass);
			}
		});
		if ($(".filter-tag").length) {
			ACC.savedlist.createFilterTag("button", 'Clear All Filters', '', '', targetElem, popup, targetClass);
			targetParent.slideDown();
			loading.stop();
		}
		ACC.myquotes.filterPopup('hide', 500, popup);
	},
	clearFilterTag: function (type, id, index, targetElem, popup) {
		if (type == 'input') {
			$("[data-" + targetElem + "]").eq(index).val('');
		} else {
			let category = id.split("-");
			$("[data-" + targetElem + "]").eq(index).prop('checked', false);
			if (!category[2]) {
				$('[id^="filter-' + category[1] + '-"]').prop('checked', false);
			}
			else if (category[2]) {
				$('[id="filter-' + category[1] + '"]').prop('checked', false);
			}
		}
		ACC.savedlist.updateListFilter();
	},
	createFilterTag: function (type, text, id, index, targetElem, popup, targetClass) {
		let targetParent = $(targetClass);
		let targetText = type == 'input' ? 'Text: "' + text + '"' : text;
		if (type == 'input' || type == 'checkbox') {
			targetParent.append("<div class='text-default font-size-14 p-r-15 add-border-radius bg-light-gray border-light-grey transition-3s filter-tag'><span class='bold display-ib p-b-5 p-l-15 p-t-5 pad-rgt-10 transition-3s pointer' onclick=\"ACC.savedlist.clearFilterTag('" + type + "','" + id + "','" + index + "','" + targetElem + "','" + popup + "')\">x</span><span>" + targetText + "</span></div>");
		} else {
			targetParent.append('<button class="text-white font-size-14 transition-3s clearAllTagBtn" onclick="ACC.savedlist.resetListFilter(\'listfilter\', \'.list-filter-popup\')">' + text + '</button>')
		}
	},
	listUomSelecion: function(e) {
		let target = $(e);
		let targetParent = target.parents('.saved-list-sec');
		let productCode = targetParent.find(".productcode").val();
		let oldUomValue = targetParent.find("#inventoryUomId").val();
		let oldUomUnit = targetParent.find(".uomvalue").val();
		let selectedUomValue = target.find(":selected").data("inventory");
		let selectedUomUnit = target.find(":selected").data("uomdecs");
		let wishListCode = $("#wishListListName").val();
		let targetATCBtn = targetParent.find("#addToCartForm").find("button");
        let btnFound;
        if(targetParent.find("#addToCartForm").length && targetATCBtn.length){
            for (let i = 0; i < targetATCBtn.length; i++) {
                if(targetATCBtn.eq(i).is(":visible") && !targetATCBtn.eq(i).prop("disabled")){
                    targetATCBtn = targetATCBtn.eq(i);
                    btnFound = true;
                    break;
                }
            }
        }
		if (selectedUomUnit != oldUomUnit) {
			target.prop("disabled", true);
			if(btnFound){targetATCBtn.prop("disabled", true);}
			$.ajax({
				url: ACC.config.encodedContextPath + '/savedList/updateUOMforEntries',
				cache: false,
				type: "GET",
				dataType: 'json',
				contentType: 'application/json',
				data:
				{
					"wishListCode": wishListCode,
					"productCode": productCode,
					"inventoryUomId": selectedUomValue
				},
				success: function (data) {
					target.prop("disabled", false);
					if(btnFound){targetATCBtn.prop("disabled", false);}
					if (data) {
						let newYPrice = (data.customerPrice)?data.customerPrice.formattedValue:data.price.formattedValue;
						let newRPrice = data.price.formattedValue;
						let newTPrice = data.totalPrice.formattedValue;
						let newListTotal = ACC.savedlist.formatNumberComma(data.listTotalPrice);
						let newMultiplier = data.orderQuantityInterval;
						//your price/retail price/item level total/list total/ATC UOM/Adobe Data/MOB UI 
						//'Add List To Cart' Update
						let productList = $(".saveList").val().trim().split(" ");
						for (var i = 0; i < productList.length; i++) {
							if (productList[i].split("|")[0] == productCode) {
								productList[i] = productCode + "|" + productList[i].split("|")[1] + "|" + selectedUomValue;
								break;
							}
						}
						$(".saveList").val(productList.join(" "));
						// ./ 
						//Prie Section update
						let priceTarget = $('[data-uomid="' + oldUomValue + '"]');
						priceTarget.attr({ 'data-uomid': selectedUomValue, 'data-uomdesc': selectedUomUnit, 'data-uom': selectedUomUnit, 'data-inventorymultiplier': newMultiplier });
						priceTarget.find(".slp-yourprice-style").html(newYPrice + ' / ' + selectedUomUnit);
						priceTarget.find(".slp-retailprice-style del").html(newRPrice + ' / ' + selectedUomUnit);
						priceTarget.find(".js-customer-price").val(data.customerPrice?data.customerPrice.value:data.price.value);
						// ./ 
						targetParent.find(".uomvalue").val(selectedUomUnit);
						targetParent.find("#inventoryUomId").val(selectedUomValue);	//Add To Cart Update
						targetParent.find(".inventryuom").val(selectedUomValue);	//Item select checkbox
						targetParent.find(".tot-amt-list").html(newTPrice);	//Item Total Update
						$(".cumulative-tot-price").find("strong").html('$' + newListTotal);	//List Total update
						ACC.savedlist.checkMinQuantityList(targetParent.parent().find(".quantity_updated"), false);
					}
					else {
						ACC.myquotes.quotePopupError();
					}
				},
				error: function () {
					ACC.myquotes.quotePopupError();
					target.prop("disabled", false);
					if(btnFound){targetATCBtn.prop("disabled", false);}
				}
			});
		}
	},
	quantityHandler: function(type, ref, page) {
		let target = $(ref);
		let targetVal = Number(target.val());
		var max_qty = [];
		for (let i = 0; i < target.prop("maxlength"); i++) {
			max_qty.push("9");
		};
		max_qty = max_qty.join("");
		var targetParent = target.parents(".saved-list-sec");
		var orderQtyInterval = targetParent.find(".orderQtyInterval").val();
		let targetUOM = targetParent.find('.js-uom-selector').eq(0);
		if (targetParent.find('.intervalQtyInfo_list').length && orderQtyInterval && orderQtyInterval != "" && (!targetUOM.length || (targetUOM.length && Number(targetUOM.find(":selected").data("inventorymultiplier")) == 1))) {
			if (type == 'plus') {
				if (targetVal < max_qty) {
					var currentMultiple = Math.floor(targetVal / orderQtyInterval);
					var nextMultiple = currentMultiple + 1;
					targetVal = Math.max(orderQtyInterval, nextMultiple * orderQtyInterval);
					target.val(targetVal);
				}
			} else {
				if (targetVal > 1) {
					var isMultiple = targetVal % orderQtyInterval === 0;
					var previousMultiple = isMultiple ? targetVal - orderQtyInterval : targetVal - (targetVal % orderQtyInterval);
					targetVal = Math.max(orderQtyInterval, Math.max(orderQtyInterval, previousMultiple));
					target.val(targetVal);
				}
			}
		}
		else {
			let newVal = type == 'plus' && targetVal < max_qty ? 1 : type == 'minus' && targetVal >= 2 ? -1 : 0;
			if (newVal && !target.prop("disabled")) {
				targetVal += newVal;
				target.val(targetVal);
			}
		}
		if (page) {
			ACC.recommendedlist.listDetailTotalCalculate(ref);
		}
		ACC.savedlist.listQtyUpdate(ref, "quantityHandler");
	}
};
$(function () {
    $(".comment-overlay").on('click', function() {
        $('.commentOverlay').hide();
        $('.overlayBackground').hide();
        $('#siteoneShareSavedListForm').hide();
        $('body').css('overflow-y', 'auto'); 
        
    	_AAData.popupPageName= "";
    	_AAData.popupChannel= "";
    });
});

$(document).ready(function(){
	$(".disableOption").on('click',function (e){
		$(".disableOption").css({"pointer-events": "none", "cursor": "default"});
		e.preventDefault();
	})
	$(document).on("change", ".qtyId", function(e){
		var data=$(this).attr("data-role");
		var qtyId="#"+data;
		var idval=$(this).parents().find(".saved-list-sec").find(qtyId);
		$(idval).val($(this).val());
		//console.log($(idval).val());
  	});
	

	$(document).on("keyup", ".quantity_updated", function(e) {
		
		var $target=$(e.target);
	    var max_qty=99999;
	    var quantity=$(this).val();
	    $(this).closest("div.box-effect").find("input[type=checkbox]").prop('checked', true);
	    if(quantity<=max_qty && quantity!=0) {
	        var listCode = $("#listCodeQtyChange").val();
	        var productCode = $(this).siblings("#productCodeQtyChange").val();
	        $(this).parent().parent().parent().next(".quantityInputSection").find("input#quantity").val(quantity);
	        $.ajax({
	            type: 'POST',
	            url: ACC.config.encodedContextPath + "/savedList/updateProductQuantity",
	            datatype: "json",
	            data: {
	                productCode: productCode,
	                wishListCode: listCode,
	                quantity:quantity
	            },
	            success: function(result) {					
					ACC.savedlist.checkMinQuantityList($target,false);					
					ACC.savedlist.updatePrice($target, $target);					            	
	            },
	            error: function(xhr, ajaxOptions, thrownError) {
               		 //console.log('data failure');
	            }
	        });
	    }
		else{
			ACC.savedlist.checkMinQuantityList($target,false);
		}
	});

	$('.js-update-entry-quantity-list-btn').on("click", ACC.global.throttleFunction(function (e) {
		var quantity_updated = false;
		var max_qty = 9999;
		var $target = $(e.target);
		var targetParent = $target.parents(".saved-list-sec");
		var targetInput = targetParent.find(".qtyId");
		var qty = targetInput.val();
		var intervalQtySection = targetParent.find('.intervalQtyInfo_list');
		var orderQtyInterval = targetParent.find(".orderQtyInterval").val();
		var listCode = targetParent.find("#listCodeQtyChange").val();
		var productCode = targetParent.find("#productCodeQtyChange").val();
		$target.closest("div.box-effect").find("input[type=checkbox]").prop('checked', true);
		if (qty && !isNaN(qty)) {
			qty = Number(qty);
		} else {
			qty = 0;
		}
		if (orderQtyInterval && orderQtyInterval != "" && intervalQtySection.is(':visible')) {
			if ($target.hasClass("plusQty")) {
				if (qty < max_qty) {
					var currentMultiple = Math.floor(qty / orderQtyInterval);
					var nextMultiple = currentMultiple + 1;
					qty = Math.max(orderQtyInterval, nextMultiple * orderQtyInterval);
					targetInput.val(qty);
				}
			} else {
				if (qty > 1) {
					var isMultiple = qty % orderQtyInterval === 0;
					var previousMultiple = isMultiple ? qty - orderQtyInterval : qty - (qty % orderQtyInterval);
					qty = Math.max(orderQtyInterval, Math.max(orderQtyInterval, previousMultiple));
					targetInput.val(qty);
				}
			}
			quantity_updated = true;
			ACC.savedlist.checkMinQuantityList($target, true);
		} else {
			if ($target.hasClass("plusQty")) {
				if (qty < max_qty) {
					targetInput.val(1 + qty);
					quantity_updated = true;
					ACC.savedlist.checkMinQuantityList($target, true);
				}
			} else {
				if (qty > 1) {
					targetInput.val(qty - 1);
					quantity_updated = true;
					ACC.savedlist.checkMinQuantityList($target, true);
				}
			}
		}
		ACC.savedlist.listQtyUpdate(targetInput, "js-update-entry-quantity-list-btn");
		targetInput = targetParent.find(".qtyId");
		targetInput.focus();
		if (quantity_updated == true) {
			qty = targetInput.val();
			targetParent.find(".quantityInputSection").find("input#quantity").val(qty);
			$.ajax({
				type: 'POST',
				url: ACC.config.encodedContextPath + "/savedList/updateProductQuantity",
				datatype: "json",
				data: {
					productCode: productCode,
					wishListCode: listCode,
					quantity: qty
				},
	            success: function(result) {
					ACC.savedlist.updatePrice($target, targetInput);
				},
	            error: function(xhr, ajaxOptions, thrownError) {
					//console.log('data failure');
				}
			});
			$target.trigger("qtyUpdated");
			return false;
		}
	}, 450));

	 var text_max = 200;
	    $(".list-comment").on('keyup',function () {
	        var text_length = $(this).val().length;
	        var data_remaining = text_max - text_length;
	        if(data_remaining==0)
	    	{
	    	 $('.text-remaining').css("color","red");
	    	}
	    else
	    	{
	    		$('.text-remaining').css("color","#999");
	    	}
	        $(".text-remaining").html(ACC.config.remaining +' '+data_remaining + ' '+ ACC.config.characters);
	    });

	 var text_max = 200;
	 if($('.share-comment').length!=0)
 	{
		 var text_length = $('.share-comment').val().length;
		 var text_remaining = text_max - text_length;
 	}
	 if(text_length!=0)
 	{
 		$('.text-area-disclaimer').html(ACC.config.remaining +' '+text_remaining + ' '+ ACC.config.characters);
 		if(text_remaining==0)
 		{
 			$('.text-area-disclaimer').css("color","red");
 		}
 	}
 else
 	{
	 $(".text-area-disclaimer").html(ACC.config.remaining +' '+text_max+ ''+ ACC.config.characters);
 	}
	    

	    $(".share-comment").on('keyup',function () {
	        var text_length = $(".share-comment").val().length;
	        var data_remaining = text_max - text_length;
	        if(data_remaining==0)
	    	{
	    	 $('.text-area-disclaimer').css("color","red");
	    	}
	    else
	    	{
	    		$('.text-area-disclaimer').css("color","#999");
	    	}

	        $(".text-area-disclaimer").html(ACC.config.remaining +' '+data_remaining + ' Characters');
	    });
	    
	    var assemblyCount = $(".js-assembly-qty").val();
	    if(assemblyCount==null || assemblyCount==""){
	    	$(".js-assembly-qty").val("1");
		}
		if ($(".pagename").val() == 'Saved List Page') {
			$('.savedListName').on('input', function () {
				$('.headerListName').val($(".savedListName").val())
			})
			$('.headerListName').on('input', function () {
				$('.savedListName').val($(".headerListName").val())
			})
		}
});

$(document).on("keydown", ".qtyId", function(e){
	if (!((e.which == 8) || (e.which >= 35 && e.which <= 57) || (e.which >= 96 && e.which <= 105))) {
		e.preventDefault();
		return false;
	}
});

$(".js-assembly-qty").keypress(function (e) {
	var regex = new RegExp("^[0-9-]+$");
	var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);   
	var ignoredKeys = [8,0];
        if (ignoredKeys.indexOf(e.which) >=0 || regex.test(str)) {   
	       return true;
	    }       	    
	    e.preventDefault();
	    return false;
});


$(document).on("click", ".fstSelected , .fstChoiceItem", function(e){
	if($(".fstResultItem").length == $(".fstChoiceItem").length){
		$(".fstResults").hide();
	}
	else{
		$(".fstResults").show();
		}
});

$(".inputTextBox").keyup(function(){
	var users = $(".inputTextBox").val();
	
	var elem=$(this)
	if ($(".inputTextBox").val().length >= 2){
		$("#listValues").addClass('listValuesActive');
		//$(".inputTextBox").val('');
	
	var a = [];
		//console.log()
	 $.ajax({
	        url : ACC.config.encodedContextPath + "/savedList/getShareListUsers?searchTerm="+users,
	        method : "GET",
	        success :function(response)
	        {
       		 $("#listValues").empty();
       		$(".listValuesActive").css("cssText", "display: inline !important;");
       		 if(response.length!=0){
       			  	$.each(response,function(index,repo){
	        		//console.log(repo);
	        		$("#listValues").append(`<li data-id='${repo.text}' data-value='${repo.value}'class='fstResultItem'>${repo.text}</li>`);
		        });
       		 }
	                
       		 else{
       				$("#listValues").append(`<li> No results </li>`);
       			 }
	        	
	       }
	 });
	}
});

var dataValuesShow = []
var dataValueArray = [];
$(document).on("click", ".removeuser",function(e){
	let name=$(this).attr("data-name");
    let email = $(this).attr("data-email");
	
	 $(this).closest(".selecteduserList").remove();
	 dataValueArray.splice($.inArray(email, dataValueArray), 1);
	 dataValuesShow.splice($.inArray(name, dataValuesShow), 1);
	 $("#users").val(dataValueArray);
	 if(dataValueArray.length == 0){
	  $(".shareAssemblyDetailBtn, .shareListDetailBtn").prop("disabled",true);
	 }
});
$(document).on("click", ".inputTextBox",function(e){

	$('#listValues li').remove();
	$(".inputTextBox").val('');
	$(".inputTextBox").removeClass("border-red")
	$(".js-share-email-error").addClass("hidden");
});
$(document).on("click", "#listValues li",function(e){
	let dataName=$(this).attr("data-id");
    let dataValue = $(this).attr("data-value");
    if(dataName!=undefined && dataValue!=undefined){
		$(".shareAssemblyDetailBtn, .shareListDetailBtn").prop("disabled",false);
		$(".inputTextBox").removeClass("border-red");
		$(".js-share-email-error").addClass("hidden");
	if(jQuery.inArray(dataName, dataValuesShow) == -1) {
		dataValuesShow.push(dataName);
		dataValueArray.push(dataValue);
		//console.log(dataValuesShow)
	    $("#users").val(dataValueArray);
		$(".textBox").prepend(`<div class="selecteduserList fstChoiceItem" data-name="${dataName}" data-email="${dataValue}">${dataName}</div>`);
		$(".selecteduserList").addClass("removeuser");
		$('#fstResultItem').fadeOut();
		
		 $('#listValues li:contains('+dataName+')').remove();
		

		var select = $("#listValues li").val();
	    if(select>=0){
	    	$(".listValuesActive").css("cssText", "display: inline !important;");
	    }else{
	    	$(".listValuesActive").css("cssText", "display: none !important;");
	    }
	}
	}
	else{
		 if(dataValueArray.length == 0){
	 		$(".shareAssemblyDetailBtn, .shareListDetailBtn").prop("disabled",true);
		 }
		$(".js-share-email-error").removeClass("hidden");
		$(".inputTextBox").addClass("border-red");

	}
});
var xList = [];
var productCodes="";
var productItemNumbers="";
$(document).ready(function(e){
	
	/*for list page add to cart non-stock item message error*/ 
	if ($('.saved-list-sec').length){
		$('.dropdown-movetolist').parent().find('#notSellable').siblings('span').closest('div').addClass("page-details-notSellable");
		
		if($('#orderingAccountMsg').css('display') == 'none'){
			$('.alert_msg').parents().find('#addToCartForm').css("margin-bottom","0px");
		}
		if($('#notSellable')){
			$('.alert_msg').parents().find('#addToCartForm').css("margin-bottom","-6px !important");
		}
		if( $('#sellableUoms') && $('#orderingAccountMsg').css('display') == 'none' && $('#notSellable') != true){
			$('.alert_msg').parents().find('#addToCartForm').css("padding-bottom","15px");
		}
		if($('#isSellable').val() == false && $('.index-listproduct').val() == '0'){
			$('#isSellable').parent('#addToCartForm').css("margin-bottom","15px !important");
		}
	}
	
	
	$('#showImage').on('change', function() {
		if($("#showImage").val()==="listWithNoImages")
		{
			$(".assembly-list-image-wrapper").hide();
			$(".listproduct-info").addClass("detailSec");
		}
		else
		{
			$(".assembly-list-image-wrapper").show();
			$(".listproduct-info").removeClass("detailSec");
		}
	})
	
	$("#quantityId").keyup(function(e){ 
		if(this.value.substring(0,1) == "0")
	    {
	       this.value = this.value.replace(/^0+/g, '1');       	     
	    }          
	});
	
	if ($('.product__list').length){
		var fixmeTop = $('.product__list').offset().top; 
		let target = $('.js-sticky-listpage');
		$(window).scroll(function() 
		{
				 if ($(this).scrollTop() > fixmeTop && $(this).scrollTop()+100 < $(".linktracking-footer").offset().top)
				 {
					target.addClass("sticky_header stickyheader-listpage-mobile-option");
					target.children(".container-lg").addClass((ACC.global.wWidth>1440)?"p-l-15 p-r-15 stickyheader-listpage-border":"stickyheader-listpage-border");
				  $('.list_addlisttocart_wrapper').addClass("list_addlisttocart_wrapper-scrollbar");
				 }
				 else
				 {
					target.removeClass("sticky_header stickyheader-listpage-mobile-option");
					target.children(".container-lg").removeClass("stickyheader-listpage-border p-l-15 p-r-15");
				  $('.list_addlisttocart_wrapper').removeClass("list_addlisttocart_wrapper-scrollbar");
				 }
		});
		}
	var selectedproductcode =  $('.selectedProducts').val();
	var selectedproductSessionVal = ACC.savedlist.savedListSessionStorage("get");
	if(selectedproductSessionVal && selectedproductSessionVal !=''){
		selectedproductcode = selectedproductSessionVal;
	}
	ACC.savedlist.savedListSessionStorage("remove");
	if(selectedproductcode != null && selectedproductcode != 'undefined'){
		if(selectedproductcode.length){
			xList=selectedproductcode.split(' ');
		}
	}
	var pcList=[];
	$.each(xList, function(index, value){
		var prodcutDetail=value.split('~');
		pcList.push(prodcutDetail[0]);
		if(prodcutDetail.length>1){
			var uom=prodcutDetail[3];
			if(uom==='undefined'){
				uom='';
			}
			productCodes=productCodes+prodcutDetail[0]+"|"+prodcutDetail[2]+"|"+uom+" ";
			productItemNumbers=productItemNumbers+prodcutDetail[1]+"|"+prodcutDetail[2]+"|"+uom+" ";
		}		
	});	
	ACC.savedlist.listItemCheckStatus(pcList);

$(document).on("change","#pageSizeSavedListDetail1",function() 
		{    
		var code = $("#code").val();
		var viewpagination =$("#pageSizeSavedListDetail1").val();
		$('#changePageSizeFormSavedListDetail1').find("input#ViewPaginationList").val(viewpagination);
		$('#changePageSizeFormSavedListDetail1').find("input#code").val(code);
		var sproduct="";
		for(var i =0; i< xList.length; i++){
			 sproduct = sproduct + xList[i]+" ";
			}
		$('#changePageSizeFormSavedListDetail1').find("input#selectedProducts").val(sproduct);
	$('#changePageSizeFormSavedListDetail1').submit();
		})

		$(document).on("change","#pageSizeSavedListDetail2",function() 
		{    
			var code = $("#code").val();
			var viewpagination =$("#pageSizeSavedListDetail2").val();
			$('#changePageSizeFormSavedListDetail2').find("input#ViewPaginationList").val(viewpagination);
			$('#changePageSizeFormSavedListDetail1').find("input#code").val(code);
			var sproduct="";
			for(var i =0; i< xList.length; i++){
				 sproduct = sproduct + xList[i]+" ";
				}
			$('#changePageSizeFormSavedListDetail1').find("input#selectedProducts").val(sproduct);
			$('#changePageSizeFormSavedListDetail2').submit();
			})
});



