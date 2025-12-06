ACC.order = {

	_autoload: [
		"backToOrderHistory",
		"bindMultidProduct",
		"bindPlaceOrder",
		"bindOrderTabs",
		"bindRequestNotesOverlay",
		"onOrderClick",
		"bindOrderQtyBia",
		"showTrackingDetails",
		"searchTabClick",
		["bindOrderDetailEvent", $('.page-order').length || $('.page-invoicedetailspage').length],
    	"tabClicked",
    	"removeSortsession",
    	"shipToApply"
	],

	backToOrderHistory: function () {
		$(".orderBackBtn > button").on("click", function () {
			var sUrl = $(this).data("backToOrders");
			window.location = sUrl;
		});

		if ($(".page-order").length && $(".add_to_cart_form").length) {
			$(".add_to_cart_form").each(function (e) {
				let ref = $(this);
				let target = ref.find("#showAddtoCartUom");
				let prodCode = ref.find('[name="productCodePost"]').val();
				if (target.prop("disabled") &&((ref.find('#stockLevel' + prodCode).val() >= 1 && ref.find('#inStockImage' + prodCode).val() == 'true' && ref.find('#outOfStockImage' + prodCode).val() != 'true' && ref.find("#doesHomeStoreAvail"+prodCode).val()=='true') || (ref.find('#toEnableBuyagain' + prodCode).val() == 'true'))){
					target.prop("disabled", false);
				}
			});
		}
	},

	bindMultidProduct: function () {
		// link to display the multi-d grid in read-only mode
		$(document).on("click", '.js-show-multiD-grid-in-order', function (event) {
			ACC.multidgrid.populateAndShowGrid(this, event, true);
			return false;
		});

		// link to display the multi-d grid in read-only mode
		$(document).on("click", '.showMultiDGridInOrderOverlay', function (event) {
			ACC.multidgrid.populateAndShowGridOverlay(this, event);
		});

	},
	showTrackingDetails: function(){
		if($(window).width()<1023){
			$(".acc-dashboard-mobile-tms .order-shipment-data, .my-orders-mobile-tms .order-shipment-data").removeClass("hidden");
		}
		
		$(document).on("click", '.show-tracking-details', function (event) {
		event.preventDefault();
        var state = $(this).data('state');
        var element = "";
        var productContainer = $(this).closest(".track-order-table__values").find(".transit-product-details");
        var stateText="";
        if($(this).hasClass("xs-product-details")){
        	stateText=ACC.config.orderDetails
        }
		ACC.product.showMoreAndLess(this, state, element, stateText, stateText, 0);
		switch (state) {
			case "expand": {
				
				if($(productContainer).find(".singleorder-product-section").length===0){
				loading.start();
				$(productContainer).removeClass("hidden");
				$.ajax({
					url: ACC.config.encodedContextPath + "/my-account/orderdetails/" + $(this).attr("data-order"),
					method: "GET",
					success: function (response) {
						loading.stop();
						$(productContainer).find(".transit-product-details__header").after(response);		
						ACC.product.enableAddToCartButton();
						ACC.product.bindToAddToCartForm();
					}
				});
				}
				else{
					$(productContainer).removeClass("hidden");
				}
				break;
			}
			 case "collapse": {
				 $(productContainer).addClass("hidden");
				 
				 break;
			 }
		}
		});
		
		$(document).on("click", '.close-product', function (event) {
			event.preventDefault();
			var row=$(this).closest(".track-order-table__values");			
        	row.find(".transit-product-details").addClass("hidden");
         
        	row.find(".xs-product-details").data('state','expand').html("<span class='icon-plus-circle'></span>&nbsp;" + ACC.config.orderDetails);
		   
          row.find(".md-product-details").data('state','expand').html("<span class='icon-plus-circle'></span>");
         
    });
	},
	bindPlaceOrder: function () {
		$(document).on("click", ".btn-place-order", function (e) {
			let target = e;
			$(".btn-place-order").attr('disabled', 'disabled');
			var kount = sessionStorage.MercSessId;
			console.log("order kount : " + kount);
			e.preventDefault();
			ACC.colorbox.open("", {
				html: $(".lodding-popup").html(),
				width: "450px",
				height: "400px",
				escKey: false,
				overlayClose: false,
				closeButton: false,
				className:"order-kount-error-popup"
			});
			$.ajax({
				url: ACC.config.encodedContextPath + "/checkout/multi/kount/evaluateInquiry?kountSessionId=" + kount,
				type: "POST",

				success: function (fraud) {
					console.log("Successful>>>" + fraud)
					
					if(typeof fraud  == 'string' && fraud.toUpperCase() == 'DECLINE'){
						ACC.colorbox.open("", {
							html: $(".order-error-popup").html(),
							width: "470px",
							height: "auto",
							escKey: false,
							overlayClose: false,
							closeButton: false,
							className:"order-kount-error-popup"
						});
					}else{
						if($(e.target).hasClass("orderapproval_btn")){
							ACC.adobelinktracking.approveOrder(target, '', 'submit for approval', 'checkout: order review', false);
						}
						else {
						ACC.adobelinktracking.approveOrder(target, '', 'place an order', 'checkout: order review', false);
						}
						$("#placeOrderForm1").submit();
					}
				},
				error: function (fraud) {
					console.log("UnSuccessful>>>" + fraud);
					$("#placeOrderForm1").submit();
				}
			});
		});

	},

	bindRequestNotesOverlay: function (e) {
		$(document).on("click", ".requestnotes", function (e) {
			e.preventDefault();
			$(".req_productId").html($(this).data("productid"));
			$(".req_productName").html($(this).data("productname"));
			$(".req_productPrice").html($(this).data("productprice"));
			$(".req_uom").html($(this).data("productuom"));
			$("#colorbox").addClass("addrequestnotes");
			ACC.colorbox.open("", {
				html: $("#requestordernotes").html(),
				width: "650px",
				height: "auto",
				close: '<span class="glyphicon glyphicon-remove"></span>',
			});

		});
	},

	onOrderClick: function () {
		$('.collapse').on('show.bs.collapse', function (e) {
			$('.collapse').not(e.target).removeClass('in');
			$(this).parent(".order-section").addClass("active-product");
			$(".order-section").removeClass("active-product");
			$(".icon-Expand").addClass("icon-plus-circle");
			$(".icon-Expand").removeClass("icon-minus-circle");
			$(".hidetext").addClass("hidden");
			$(".showtext").removeClass("hidden");
		});
		$('.close-collapse').click(function () {
			$(".order-section").removeClass("active-product");
			$(".icon-Expand").addClass("icon-plus-circle");
			$(".icon-Expand").removeClass("icon-minus-circle");
			$(".more-link").hide();
			$(".hidetext").addClass("hidden");
			$(".showtext").removeClass("hidden");
		});
		function showMoreLink(productSection){
			if ($(productSection+' '+".singleorder-product-section").length > 10) {
				$(".more-link").css("display", "block");
				$(productSection+' '+".singleorder-product-section").slice(10).css("display", "none");
			}
		}
		
		$(document).on("click", ".product-row", function (e) {
			if($(this).attr('aria-expanded') === "false"){
				$(this).closest(".order-section").removeClass("active-product");
				$(this).find(".icon-Expand").addClass("icon-plus-circle").removeClass("icon-minus-circle");
				$(this).find(".showtext").removeClass("hidden");
				$(this).find(".hidetext").addClass("hidden");
				$(".more-link").hide();
				//$(this).closest(".order-section").find(".consigmentList").addClass("hidden");
			}
			else {
				
				$(".more-link").css("display", "none");
				//$(this).closest(".order-section").addClass("active-product");
				$(this).find(".icon-Expand").removeClass("icon-plus-circle").addClass("icon-minus-circle");
				$(this).find(".hidetext").removeClass("hidden");
				$(this).find(".showtext").addClass("hidden");
				
				$(this).closest(".order-section").find(".consignment-code").each(function(){
					var consigment = $(this).val();
					var businessUnitId = $("#businessUnitId").val();
					console.log(consigment);
					loading.start();
					$.ajax({
						url: ACC.config.encodedContextPath + "/my-account/orderdetails/" + consigment + "?unitId=" + businessUnitId,
						method: "GET",
						success: function (response) {
							loading.stop();
							console.log("response==",response);
							if (response && response.orderData !=null) {
							$("."+consigment+"_trackingUrl").removeClass("hidden");
							$("."+consigment+"_trackingUrl").attr("href",response.orderData.trackingUrl);
							if(response.orderData.statusDisplay == "SHIPPED")
							{
								$("."+consigment+"_trackingUrl").text($("#trans_trackshiping").val());
							}
							else{
								$("."+consigment+"_trackingUrl").text($("#trans_trackdelivery").val());	
							}
							
							}
							
							
						}
					});
				});
				
			}
		});
		$(document).on("click", ".buy-again-atc", function (e) {
			$(".buy-again-atc").removeClass("selected-btn");
			$(this).addClass("selected-btn")
		});


	},
	bindOrderTabs: function () {
		if($("body").hasClass("page-accountDashboardPage") || $("body").hasClass("page-order")){
			$.cookie("accountPageId", "orderhistorypage", { path: '/' });
			sessionStorage.setItem("activeTab", "orderHistoryTab");
		}

		$(document).on("click", ".orders-tabs-container .orders-tab,a.orders-link", function () {
			var pageId = $(this).data("key");
			$.cookie("accountPageId", pageId, { path: '/' });
			sessionStorage.setItem("activeTab", $(this).data("active"));
			sessionStorage.setItem("selectedDropdownValueSession", ACC.config.defaultSortValue);

		});

		var currentTab = sessionStorage.getItem("activeTab");
		if (currentTab) $(".orders-tabs-container").find("[data-active='" + currentTab + "']").addClass("active-account-tab").removeClass("tab-font-color");
		sessionStorage.removeItem("activeTab");
		
		if ($(".page-accountOrdersPage .alert-danger").length == 1) {
			$(".invoice-serach-sec").addClass("order-search-sec");
			$(".orders-tab-content").addClass("order-tab-sec");
		}
		if ($('.buy-it-again-container').length > 0) {
			_AAData.eventType="Buy Again";
		}

		if($(".page-invoicelistingpage").length){
			$(".invoices-dropdown-box .dropdown-menu").width($(".invoices-dropdown-box button").outerWidth()-1);
		}
		
		if($(".page-accountOrdersPage").length){
			$(".order-dropdown-box .dropdown-menu").width($(".order-dropdown-box button").outerWidth()-2);
		}
		
	},
	
	orderDropdownSearchType: function (e, type, val, ref) {
		let target = $(e);
		if(!target.parent("li").hasClass("disabled")){
			$(".order-dropdown-box .dropdown-menu li").removeClass("disabled");
			target.parent("li").addClass("disabled");
			$(".order-dropdown-select").html(type + " #");
			$(ref).prop("name", val);
		}
	},

	invoicesDropdownSearchType: function (e, type, val, ref) {
		let target = $(e);
		if(!target.parent("li").hasClass("disabled")){
			$(".invoices-dropdown-box .dropdown-menu li").removeClass("disabled");
			target.parent("li").addClass("disabled");
			$(".invoices-dropdown-select").html(type + " #");
			$(ref).prop("name", val);
		}
	},

	bindOrderQtyBia: function () {
		$(document).on("input change", ".qty-container .bia-qty-txt-field", function () {
			var qtyInput = $(this).val();
			$(".qty-hidden-bia").val(qtyInput);
			$(this).closest(".bia-table-row").find(".js-bia-qty-error").addClass("hide");
			$(this).closest(".qty-button-wrapper").find('.js-enable-btn').removeAttr("disabled");
			$(this).removeClass("add-border border-red text-red");
			if ($(this).data('is-nursery') === true) {
				if ($(this).val().trim() > $(this).data("available-qty")) {
					$(this).addClass("add-border border-red text-red");
					$(this).closest(".bia-table-row").find(".js-bia-qty-error").removeClass("hide");
					$(this).closest(".qty-button-wrapper").find('.js-enable-btn').attr("disabled", "disabled");
				}
			}
		})
	},
	addOrderToList: [],
	bindOrderDetailEvent: function () {
		let heightElem = ["grey", "white"];
		for (let i = 0; i < heightElem.length; i++) {
			if (ACC.global.wWidth < 1024 & i != 0) {
				for (let j = 0; j < 1; j++) {
					let target1 = $(".order-height-adjust-" + heightElem[i]).eq(j);
					let target2 = $(".order-height-adjust-" + heightElem[i]).eq(j + 1);
					if (target1.height() > target2.height()) {
						target2.height(target1.height());
					}
					else if (target2.height() > target1.height()) {
						target1.height(target2.height());
					}
				}
			}
			else if (ACC.global.wWidth >= 1024) {
				let maxHeightAdjust = 0;
				for (let j = 0; j < 6; j++) {
					let target = $(".order-height-adjust-" + heightElem[i]).eq(j);
					maxHeightAdjust = (maxHeightAdjust < target.height()) ? target.height() : maxHeightAdjust;
				}
				$(".order-height-adjust-" + heightElem[i]).height(maxHeightAdjust);
			}
		}
		if (ACC.global.wWidth < 1024) {
			$(".order-accordion-parent").each(function (e) {
				let target = $(this);
				target.addClass("bg-white").removeClass("disabled");
				if (e+1 != 1) {
					target.find(".order-accordion-data-" + (e+1)).hide();
				}
			});
		}
		$(".item__list--header").each(function () {
			let orderCode = $(this).find(".product-code").val();
			let orderItemNumber = $("#checkbranch-itemnumber-" + orderCode).val();
			let quantity = Number($(this).find(".qtyValue").text().trim() || 1);

			let orderKey = `${orderItemNumber}|${quantity}|${orderCode}`;
			if (orderItemNumber && ACC.order.addOrderToList.indexOf(orderKey) === -1) {
				ACC.order.addOrderToList.push(orderKey);
			}
		});
		ACC.order.addOrderToList = ACC.order.addOrderToList.join(" ");
  	},
  	/*Order page Redesign Start */
  	
	tabClicked: function () {
		$(document).on("click", ".tabClick", function () {
			var targetClass = $(this).attr("data-target");
			$('.'+targetClass).removeClass("hidden").fadeIn("slow");
			
			$("body").click(function(e) {
			   let target = $(e.target);
			   if ($('.'+targetClass).is(":visible") && (ACC.global.wWidth > 1023 || ACC.global.wWidth < 576)) {
				   if (!target.parents('.'+targetClass).length) {
			       $('.'+targetClass).fadeOut();
			       }
			   }
			});
			
		});
		
		$(document).on("submit","#searchForm, #searchFormMob",function(){
			$('#searchForm input[name="s-by"]').removeAttr('name');
			$('#searchFormMob input[name="s-by"]').removeAttr('name');
			$('#searchForm input[name="accountShiptos"]').eq(1).removeAttr('name');
			$('#searchForm input[name="paymentType"]').eq(1).removeAttr('name');
		});
		
		ACC.order.sbyRadioUpdate();
		
	},
	
	orderSortBy: function(sort){
		if(sessionStorage.getItem("sOrder") == "desc" && sessionStorage.getItem("sOrderField") == sort)
		{
			sessionStorage.setItem("sOrder", "asc");
			sessionStorage.removeItem("sOrderField");
		}
		else{
			sessionStorage.setItem("sOrder", "desc");
			sessionStorage.setItem("sOrderField", sort);
		}
		
		var sByOrder = sessionStorage.getItem("sOrder");
		
		$('#searchForm input[name="sort"]').val(sort);
		$('#searchForm input[name="sortOrder"]').val(sByOrder);
		$('#searchFormMob input[name="sort"]').val(sort);
		if(ACC.global.wWidth > 1023){
			$('#searchForm').submit();
		}
	},
	
	removeSortsession: function(){
		if(!$("body").hasClass("page-accountOrdersPage") || $(".order-tab").length == 0){
			sessionStorage.removeItem("sOrder");
			sessionStorage.removeItem("sOrderField");
		}
	},
	
	sbyRadioUpdate: function(){
		
		$('.sbyData').on('change', function() {
			if ($(this).is(':checked')) {
			sessionStorage.setItem("sbyData", $(this).val());
			$('#dateSort').val($(this).val());
			}
		});
		
		var getsbyData = sessionStorage.getItem("sbyData");
		if(getsbyData){
		$('#dateSort').val(getsbyData);	
		$('input:radio[name="dateSort"][value="'+getsbyData+'"]').prop("checked",true);
		}
	
	},
	
	searchTabClick: function(){
		$(document).on("click", ".mob-searchby", function () {
			ACC.colorbox.open("", {
				html: '<form id="searchFormMob">'+$(".search-dropdown").html()+'</form>',
				width:"95%",
				escKey: false,
				overlayClose: false,
				className:"search-dropdown",
				onComplete:function(){
				ACC.order.sbyRadioUpdate();
				},
			});
		});
	},
	
	shipToApply: function(){
		$(document).on("click", ".shipToAppBtn", function () {	
					
					var currentSelect = $("input[name='shipToAc-m']:checked").val();	
					
					if(ACC.global.wWidth > 1023){
					currentSelect = $("input[name='shipToAc']:checked").val();
					}
					
				
					//sessionStorage.setItem('selectedOrderSession',currentSelect);

					var $form = $("#sortForm1");
					
						var searchParam = $("#searchOrderHistory").val();
						var dateSort= $("#dateSort").val();
						var viewpagination =$("#pageSizeOrder1").val();
						//var accountShiptos = $("#shipToSelected_order").val();
						var searchParam = $("#search-ship-to").val();
						var paymentType= $("#paymentTypeOrder").val();
						$form.find("input#search-shipto").val(searchParam);
						//$form.find("input#shipToSelected_order").val(accountShiptos);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#dateSortOrder").val(dateSort);
					$form.find("input#ViewPaginationOrder").val(viewpagination);
					//$form.find("input#accountShiptos").val(accountShiptos);
					$form.find("input#paymentTypeOrder").val(paymentType);
					$form.find("input#accountShiptos").val(currentSelect);
					
					$form.submit();
					
		});
		
		$(document).on("click", ".moreShipTos", function () {	
					
					var data = {
							dateSort:$("#dateSort").val(),
							paymentType:$("#paymentTypeOrder").val()
						}
					let searchVal = ($("#search-ship-to-popup-order").length)?($("#search-ship-to-popup-order").val()).trim():"";
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim()+'?searchParam='+searchVal;	
						removeShipToSortbySessionValue();
						var wShiptoPopup = '800px';
						var shipToPopupTitle = 'Search or Select a Ship-To';
						if(ACC.global.wWidth < 500){
							wShiptoPopup = '95%';
							shipToPopupTitle = 'Ship-to';
						}
						$.ajax({
					            url: shipToPopupURL,
					            data:data,
					            method: "GET",		
					            success: function(searchPageData) {
					                ACC.colorbox.open(shipToPopupTitle, {
					                    html: searchPageData,
					                    width: wShiptoPopup,
					                    className:"shipto-popup-order-mb invoice-shiptopopup",
					                    overlayClose: false,
					                    onComplete: function(e) {
					                    	//sessionStorage.removeItem('selectedOrderSession');
					                    	$(".ship-TosOrder").val('shipToopenPopupNew');
					        				
					        				checkShipToPopUpOrder();
					        				$(document).on('click keyup', '#search-ship-to-popup-order', function(){
					                    		checkShipToPopUpOrder();
					                    	});
					                    },
					                    onClosed: function() {
					                    	//sessionStorage.setItem('selectedOrderSession','All');
				        					location.reload(true);
				        					//window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';
				        				
					                    }
					                });
					            }
					        });
					
		});
		
				$(document).on("blur", "#search-ship-to-popup-order-new", function () {	
			if(ACC.global.wWidth > 1023){
			ACC.order.shipToNewPopUpAjax();
			}
		});

		$(document).on("click", ".moreShipToSearch", function () {	
			ACC.order.shipToNewPopUpAjax(true);
		});

		$(document).on("click", ".moreShipToClear", function () {	
			$("input[name='sortByRadio']").prop('checked', false);
			$("#search-ship-to-popup-order-new").val('');
		});

		$(document).on("click", ".shipToURLRadio", function () {	
			var redURL = $("input[name='shiptoURL']:checked").val();
			window.location.href = redURL;
		});
	},

	shipToNewPopUpAjax: function(shortBy=false){

		var sortParam;

		if(shortBy == true){
			sortParam = $("input[name='sortByRadio']:checked").val();
			sessionStorage.setItem('selectedRadioValueSession',sortParam);
		}
		var wShiptoPopup = '800px';
		var shipToPopupTitle = 'Search or Select a Ship-To';
		if(ACC.global.wWidth < 500){
			wShiptoPopup = '95%';
			shipToPopupTitle = 'Ship-to';
		}

		var dateSortPopup =$("#dateSort").data('datesort');

		var data = {
					searchParam : ($("#search-ship-to-popup-order-new").val()).trim(),
					dateSortPopup:dateSortPopup,
					sort:sortParam	
				}
		var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim() + '?paymentType=ALL';
		var poupClass = $("#cboxOverlay").prop("class");
		$.ajax({
			url : shipToPopupURL,
			method : "GET",
			data : data,
			success : function(searchPageData) {
			ACC.colorbox.open(shipToPopupTitle, {
			html : searchPageData,
			width : wShiptoPopup,
			className : poupClass,
			overlayClose : false,
			onComplete:function(){
				$(".ship-TosOrder").val('shipToopenPopupNew');
				checkShipToPopUpOrder();
				var getShortByRadio = sessionStorage.getItem("selectedRadioValueSession");
				if(getShortByRadio){
					$("input[name='sortByRadio'][value='"+getShortByRadio+"']").prop('checked', true);
					sessionStorage.removeItem("selectedRadioValueSession");
				}

			},
			onClosed: function() {

					sessionStorage.setItem('selectedOrderSession','All');
					location.reload(true);
					//window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';

				}
			});
			}
			});
	},
	/*Order page Redesign End */
	
	addOrderToListBtn: function () {
		ACC.productDetail.plpCommonBtn = "false";
		ACC.productDetail.plpMobileAddListCode = '';
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
	currentWishListCode: false,
    moveItemsToList: function (wishlistCode) {
        if (!ACC.order.currentWishListCode) {
            ACC.order.currentWishListCode = wishlistCode;
            console.log(wishlistCode);
            $.ajax({
                type: 'POST',
                url: ACC.config.encodedContextPath + "/savedList/addSelectedToSavedWishlist",
                datatype: "json",
                data: {
                    productItemNumbers: ACC.order.addOrderToList,
                    wishListCode: wishlistCode,
                    currentWishlist: ''
                },
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
                    });
                    console.log('success: ', wishlistCode, result);
                    ACC.order.currentWishListCode = false;
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log('error: ', wishlistCode, xhr);
                    ACC.order.currentWishListCode = false;
                    ACC.myquotes.quotePopupError();
                }
            });
        }
    },
    saveContinueList: function () {
        var pdppopuplistname = $("#pdppopupinput").val().trim();
        if (pdppopuplistname != '' && pdppopuplistname != null) {
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
                        //create new buttons 
                        //call new  function and pass whishlist code
                        var item = "";
                        if ($('.page-order').length > 0) {
                            result.allWishlist.forEach(addtolistpopup => {
                                item += '<button type="button" class="list-group-item Ordlistoption hidden-xs"  id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '"onclick="ACC.order.moveItemsToList(\'' + addtolistpopup.code + '\')">' + addtolistpopup.name + '</button>'
                                item += '<button type="button" class="list-group-item Ordlistoptionmobile hidden-md hidden-lg hidden-sm " id="' + addtolistpopup.code + '" data-value="' + addtolistpopup.code + '" onclick="ACC.order.moveItemsToList(\'' + addtolistpopup.code + '\')">' + addtolistpopup.name + '</button>'
                            });
                            $(".popupoptionlistitem").empty().append(item);
                            var wishlistCode = $(".Ordlistoption:first").data('value');
                            ACC.order.moveItemsToList(wishlistCode)
                            $(".createnewlistinput").val("");
                        }
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log('data failure');
                }
            });
        }
        else {
            $('.pdp-emptynewlist-popup-error-text').show();
            $('.listpopupsavebtn').addClass("disabled");
            $('.createnewlistbtn').addClass("disabled");
            $('input.createnewlistinput').keyup(function (e) {
                $(".pdp-emptynewlist-popup-error-text").hide();
                $(".pdp-atl-popup").colorbox.resize();
                $('.listpopupsavebtn').removeClass("disabled");
                $('.createnewlistbtn').removeClass("disabled");
            });
            $(".pdp-atl-popup").colorbox.resize();
            $(".pdplistpopupoption:first").addClass('selected');
        }
    }
};

function checkShipToPopUpInvoice(){
	var searchShipToPopupInvoiceVal = $('#search-ship-to-popup-invoice').val();
	disableShipToSearch(searchShipToPopupInvoiceVal);
}

function disableShipToSearch(searchShipToVal){
	if (typeof searchShipToVal !== 'undefined') {
		if (searchShipToVal.size == 0 || searchShipToVal == '') {
			$('button[id="shipToSearchBoxButton"]').attr('disabled', 'disabled');
		}
		else {
			$('button[id="shipToSearchBoxButton"]').removeAttr("disabled");
		}
	}
}