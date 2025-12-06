ACC.accountdashboard = {

	_autoload: [
		["bindAccountDashboardEvents", $('.page-accountDashboardPage').length],
	    "bindShipToListApiCall",
	    "bindUserCreate",
        "billingAccountOverlay",
	    "shipToListApiCall",
	    "bindPartnerProgram",
	    "emailSignUp",
	    "bindInvoiceEmail",
	    "bindOrderDetailsEmail",
	    "buildEditUserPage",
	    "bindShipToeOverlay",
	    "bindShipToeOverlayOpen",
	    "bindShipToSearchOverlay",
	    "bindShipToValueOverlay",
	    "bindShipToAccountOverview",
	    "showTeamRole",
	    "requestaccountFormCutomerType",
	    "resetDropdown",
	    "shiptoPopupManageUsers",
	    "shiptoPopupEwalletpage",
	    "bindDeleteEwalletPopup",
	    "bindDeleteMessageEwalletPopup",
	    "bindEditEwalletPop",
	    "bindAddEwalletPopup",
	    "bindBoardCard",
	    "bindEditSubmitEwallet",
	    "bindNickNameCards",
	    "ewalletLoadContent",
	    "bindManageEwalletPopUp",
	    "bindSelectAccess",
	    "bindManageEwalletPopUpConfirm",
	    "bindGrantRevokeEnabled",
    	"bindEditEnabled",
	   "bindAnayticDataOrderApproval",
	   "bindSelfServeForm",
	   "showShiptoMsg",
	   "showPartnersPoint"
	],
	bindAccountDashboardEvents: function(){
		ACC.accountdashboard.getQuotesData('open');	//Get Quotes
		ACC.accountdashboard.getRecommendedData('BuyAgainPage', 'buyagain', true); //Get recommended roduct
	},
	setPageShipTos: 0,
	showShipTos: function (e) {
		ACC.global.wHeight = $(window)[0].innerHeight;
		var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim();
		var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/' + ($("#unitUid").val()).trim() + '?searchParam=' + searchParam;
		var currentSelect = $(".ship-TosSelect");
		$(window).scrollTop(0);
		$.ajax({
			url: shipToPopupURL,
			method: "GET",
			success: function (searchPageData) {
				if (searchPageData != "") {
					$(".js-document-shipto-box").html(searchPageData);
					if (searchParam == "") {
						ACC.accountdashboard.shipToSlide('', '.js-document-shipto-box', 0, 500);
					}
					else {
						$("#search-ship-to-popup").val(searchParam);
					}
					let target = $(".js-shipto-main-data");
					let mUid = target.find(".ship-to-uid").val();
					let optionValues = localStorage.getItem(mUid).split("|")[0].split(":");
					let isMainUid = $.trim($(".js-shipto-main-check").text()).split("-").length == 1 ? true : false;
					if ($(".addresspopup .page-shipToPage").length) { 
						$(".js-shipto-primary, .js-shipto-current").addClass("hidden");
						$(".js-shipto-primary + .shipto-box").addClass("hidden");
					}
					else if (isMainUid || optionValues[0].split("-").length != 1) {
						$(".js-shipto-primary").removeClass("hidden");
						$(".js-shipto-current").addClass("hidden");
					}
					else {
						$(".js-shipto-primary").addClass("hidden");
						$(".js-shipto-current").removeClass("hidden");
					}
					target.attr("href", target.attr("href") + mUid);
					target.find(".ship-to-name").val(optionValues[1]);
					target.find(".ship-to-display-id").val(optionValues[2]);
					target.find(".js-shipto-main-name").html(optionValues[1]);
					target.find(".js-shipto-main-uid").html(optionValues[2]);
					$(".js-document-shipto-scroll").height(ACC.global.wHeight - ($("#search-ship-to-popup").offset().top + 118));
					$("#search-ship-to-popup").on('keyup', function (e) {
						if (e.keyCode == 13) {
							$("#shipToSearchBoxButton").trigger("click");
						}
					});
				}
				else {
					ACC.myquotes.quotePopupError();
					// onComplete
					$('#cboxWrapper #cboxClose').on('click', function () {
						$(currentSelect).val(previous);
					});
					checkShipToPopUp();
					$(document).on('click keyup', '#search-ship-to-popup', function () {
						checkShipToPopUp();
					});
					// onClosed
					if ($('#shipToSelected, #shipto-selected').length) {
						window.location.reload();
					}
				}
			},
			error: function (e) {
				ACC.myquotes.quotePopupError();
			}
		});
	},
	getMoreShipTos: function (e) {
		ACC.accountdashboard.setPageShipTos++;
		var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim();
		var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-shipto-popup-more/' + ($("#unitUid").val()).trim() + '?searchParam=' + searchParam + "&page=" + ACC.accountdashboard.setPageShipTos;
		$.ajax({
			url: shipToPopupURL,
			type: "get",
			success: function (data) {
				let shipToHtml = "";
				for (let i = 0; i < data.length; i++) {
					let shitpNum = data[i].displayId;
					let shitpName = data[i].name;
					let shitpUid = data[i].uid;
					shipToHtml += '<a class="p-a-15 bg-light-grey flex-center m-b-10 shipto-box ship-to-link" href="' + ACC.config.encodedContextPath + '/my-account/ship-to/' + shitpUid + '"><div class="bg-white border-grayish m-r-10 ship-to-link-circle"></div><input type="hidden" value="' + shitpNum + '" class="ship-to-name"><input type="hidden" value="' + shitpNum + '" class="ship-to-display-id"><input type="hidden" value="' + shitpUid + '" class="ship-to-uid"><div class=""><p class="m-b-0 f-s-16 text-gray-1">' + shitpName + '</p><p class="m-b-0 f-s-12 text-dark-gray">' + shitpNum + '</p></div></a>';
				}
				$(shipToHtml).insertBefore(e);
				if (!data.length || data.length < 10) {
					$(e).remove();
				}
			},
			error: function (e) {
				ACC.myquotes.quotePopupError();
			}
		});
	},
	shipToSlide: function (e, ref, leftRef, time) {
		ACC.global.wHeight = $(window)[0].innerHeight;
		let target = $(ref);
		let leftCal = typeof (leftRef) == 'number' ? leftRef : $(leftRef).offset().left;
		if ($(".addresspopup .page-shipToPage").length) {
			$('[name="parentB2BUnit"]').prop("selectedIndex", 0);
			ACC.colorbox.close();
		}
		else if (target.hasClass("showing-menu")) {
			$('.shipto-overlay').fadeOut();
			target.removeClass("showing-menu").animate({ left: -target.outerWidth() - 20 }, time);
			$("#search-ship-to-popup").val('');
		}
		else {
			if (ACC.global.wWidth > 1024 && $('.global-popup-box').is(":visible")) {
				$('.global-popup-box').hide();
				$(".js-document-global-btn.active").removeClass("active");
			}
			if ($('.language-dropdown').is(":visible")) {
				$('.language-value').trigger("click");
			}
			$(".L1CatLinks").removeClass("active");
			target.addClass("showing-menu").animate({ left: leftCal }, time);
			$('.shipto-overlay').fadeIn();
		}
	},
	bindShipToValueOverlay: function() { 
		$(document).on('click', '.page-shipToPage .ship-to-link', function () { // global header
			let ref = $(this);
			let unitId = ref.children(".ship-to-uid").val();
			let name = ref.children(".ship-to-name").val();
			if ($(".addresspopup .page-shipToPage").length) {
				let indexFound;
				$(".ship-TosSelect option").each(function (e) {
					if ($(this).val() == unitId) {
						$(".ship-TosSelect").prop("selectedIndex", e);
						indexFound = e;
					}
				});
				if (!indexFound) {
					$(".ship-TosSelect option").eq(0).remove();
					$(".ship-TosSelect").prepend('<option value="' + unitId + '">' + name + '</option>');
					$(".ship-TosSelect").prop("selectedIndex", 0);
				}
				ACC.colorbox.close();
				return false;
			}
			else {
				ACC.global.updateShipToCall(unitId);
				return false;
			}
		});
		$(document).on('click','#cboxContent .ship-to-link', function(){
			if(!$(".page-organizationManagement").length){
			 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
			 var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
			if($(".addresspopup").length > 0){
				if ($(".ship-TosSelect option").eq(0).val() == (unitId)
						|| $(".ship-TosSelect option").eq(1)
								.val() == (unitId)
						|| $(".ship-TosSelect option").eq(2)
								.val() == (unitId)
						|| $(".ship-TosSelect option").eq(3)
								.val() == (unitId)
					    || $(".ship-TosSelect option").eq(4)
				                .val() == (unitId)
						) {
						}else{
							$(".ship-TosSelect option").eq(3).val(unitId).text(name);
						}
				 		$(".ship-TosSelect").val(unitId);
				 		ACC.colorbox.close();
				 		return false;
					}
			else if(!$(".account-overview-search").length){
				//Do Nothing
			}
			else if(!$(".accountpopup").length){	
				ACC.global.updateShipToCall(unitId);
				return false;
			}	
			}     
		});
		$(document).on('change', '.ship-TosSelect', function () {
		 
	        previous = $(this).value;
	    }).on('change',function(e){
				var currentSelect = $(".ship-TosSelect");
				var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim()
				var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+$("#unitUid").val().trim()+'?searchParam='+searchParam;
				if($(".ship-TosSelect").val() === 'shipToopenPopup'){
					 $.ajax({
				            url: shipToPopupURL,
				            method: "GET",		
				            success: function(searchPageData) {
				                ACC.colorbox.open(ACC.config.selectShipTo, {
				                    html: searchPageData,
				                    width: '800px',
				                    className:"shipto-popup-mb addresspopup",
				                    onComplete: function(e) {
				        				$('#cboxWrapper #cboxClose').on('click',function(){
				        					$(currentSelect).val(previous);
				        				});
										$(".js-shipto-primary, .js-shipto-current").addClass("hidden");
										$(".js-shipto-primary + .shipto-box").addClass("hidden");
				        				retrieveShipToSortbySessionValue();
				        				checkShipToPopUp();
				                    	$(document).on('click keyup', '#search-ship-to-popup', function(){
				                    		checkShipToPopUp();
				                    	});
				                    },
				                    onClosed: function() {
				                    	if($('#shipToSelected,#shipto-selected').length)
										{
										window.location.reload();
										}
				                    }
				                });
				            }
				        });
				}
				
				$(document).on("click", "#cboxWrapper .account-orderhistory-pagination a",function(e){
					var page='';
					previous = $(this).value;
								e.preventDefault();	
								if($(this).attr('rel') == "prev"){
									page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
									}
									else if($(this).attr('rel') == "next"){
									    page = $(this).parents("ul").find("li.active").text().split("(")[0]
									}
									else{
									page= $(this).text().trim()-1;
									}
								var data = {
										searchParam : $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim(),
										viewtype : 'All',
										page : page,
										sort : $("#sortShipTo").val()
									}
								var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim();
								var currentSelect = $(".ship-TosSelect");
								var poupClass = $("#cboxOverlay").prop("class");
									$.ajax({
										url : shipToPopupURL,
										method : "GET",
										data : data,
										success : function(searchPageData) {
											ACC.colorbox.open(ACC.config.selectShipTo, {
												html : searchPageData,
												width : '800px',
												className : poupClass,
												onComplete:function(){
							        				$('#cboxWrapper #cboxClose').on('click',function(){
							        					$(currentSelect).val(previous);
							        				});
							        				retrieveShipToSortbySessionValue();
							        				checkShipToPopUp();
												},
												onClosed: function() {
													if($('#shipToSelected,#shipto-selected').length)
													{
													window.location.reload();
													}
							                    }
											});
										}
									});
								
						});
				
			 
			$(document).on("change", "#cboxWrapper #search-ship-to-popup",function(e) {
						 removeShipToSortbySessionValue();
					 	 var key = e.which;
					 	 previous = $(this).value;
					    
								var data = {
										searchParam : $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim(),
									}
								var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim();
								var currentSelect = $(".ship-TosSelect");
								var poupClass = $("#cboxOverlay").prop("class");
									$.ajax({
										url : shipToPopupURL,
										method : "GET",
										data : data,
										success : function(searchPageData) {
											ACC.colorbox.open(ACC.config.selectShipTo, {
												html : searchPageData,
												width : '800px',
												className : poupClass,
												onComplete:function(){
							        				$('#cboxWrapper #cboxClose').on('click',function(){
							        					$(currentSelect).val(previous);
							        				});
							        				
							        				checkShipToPopUp();
						                    		
												},
												onClosed: function() {
													if($('#shipToSelected,#shipto-selected').length)
													{
													window.location.reload();
													}
							                    }
											});
										}
									});
								
						});	
				
				
				
				
				
				
						$(document).on("change", "#cboxWrapper .page-shipToPage #sortOptions1", function(e) {
							var sortItem = $("#cboxWrapper #sortOptions1").val();

							var data = {
								searchParam : $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim(),
								viewtype : 'All',
								sort : sortItem
							}
							var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim();
							var poupClass = $("#cboxOverlay").prop("class");
							var currentSelect = $(".ship-TosSelect");
							$.ajax({
								url : shipToPopupURL,
								method : "GET",
								data : data,
								success : function(searchPageData) {
									ACC.colorbox.open(ACC.config.selectShipTo, {
										html : searchPageData,
										width : '800px',
										className : $("#cboxOverlay").prop("class"),
										onComplete:function(){
											$('#cboxWrapper #cboxClose').on('click',function(){
					        					$(currentSelect).val(previous);
					        				});
											retrieveShipToSortbySessionValue();
					        				checkShipToPopUp();
					                    
										},
										onClosed: function() {
											if($('#shipToSelected,#shipto-selected').length)
												{
												window.location.reload();
												}
					                    }
									});
								}
							});

						});
			});
		 
		$(document).bind('cbox_complete', function(){
		if ($('.input-group-shipTo').length > 0){
					_AAData.popupPageName= ACC.config.shiptopopuppathingPageName;
					_AAData.popupChannel= ACC.config.myaccountpathingChannel;
		 	try {
			    	 _satellite.track('popupView');
	        } catch (e) {} 
		}
		
		if ($('.login-error').val() == "true" && $("#colorbox").hasClass("unlock-account") != true){
			_AAData.popupPageName= ACC.config.loginerrorPageName;
			_AAData.popupChannel= ACC.config.myaccountpathingChannel;
			try {
			    	 _satellite.track('popupView');
	       } catch (e) {} 
		}
		
		});
	 		
	},
	
	
	
	bindShipToeOverlay: function() {
		$(document).on('click','#cboxWrapper #sortOptions1',function(){
			var sortType1 = $("#cboxWrapper #sortOptions1").val();
		  sessionStorage.setItem('selectedDropdownValueSession',sortType1);
		});
		$(document).on("keyup", "#cboxWrapper #search-ship-to-popup",function(e) {
			 
		 	var key = e.which;
		    if(key == 13) {
		    	$("#cboxWrapper #searchBoxButton").trigger('click');		    }
					
			});	
	
	},
	
	bindShipToSearchOverlay: function() {
		
		$(document).ready(function () {
			checkShipToPage();
		});
		$('#search-ship-to').on('click keyup', function () {
			checkShipToPage();
		});
		
		$(".shipToSearch").on('click',function () {	
					var shiptoSearch = $("#search-ship-to").val() == undefined ? "" : $("#search-ship-to").val().trim()
			        var shipToSearchURL = ACC.config.encodedContextPath + '/my-account/all-ship-to/'+($("#unitUid").val()).trim()+'?searchParam='+shiptoSearch;
			        var shiptosearchForm = $('#shiptosearchForm');
			        shiptosearchForm.submit();
			    });
			},
	
	bindShipToeOverlayOpen : function() {
		$(document).on("click", "#cboxWrapper .account-orderhistory-pagination  a",function(e){
			var page='';
				// var page=[];
				e.preventDefault();	
				/*var pagiantion= $(this).parents("li").siblings("#current_span").val();
				page = pagiantion.split("=");*/
				
				if($(this).attr('rel') == "prev"){
				page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
				}
				else if($(this).attr('rel') == "next"){
				    page = $(this).parents("ul").find("li.active").text().split("(")[0]
				}
				else{
				page= $(this).text().trim()-1;
				}
				var data = {
						searchParam : $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim(),
						viewtype : 'All',
						page : page,
						sort : $("#sortShipTo").val()
					}
					var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim();
				var poupClass = $("#cboxOverlay").prop("class");
				var currentSelect = $(".ship-TosSelect");
					$.ajax({
						url : shipToPopupURL,
						method : "GET",
						data : data,
						success : function(searchPageData) {
							ACC.colorbox.open(ACC.config.selectShipTo, {
								html : searchPageData,
								width : '800px',
								className : $("#cboxOverlay").prop("class"),
								 onComplete: function(e) {
									 $('#cboxWrapper #cboxClose').on('click',function(){
				        					$(currentSelect).val(previous);
									 });
									 retrieveShipToSortbySessionValue();
									 checkShipToPopUp();
				                    },
				                    onClosed: function() {
				                    	if($('#shipToSelected,#shipto-selected').length)
										{
										window.location.reload();
										}
				                    }
							});
						}
					});
				
		});
		$(document).on("change", "#cboxWrapper .page-shipToPage #sortOptions1", function(e) {
			var sortItem = $("#cboxWrapper #sortOptions1").val();

			var data = {
				searchParam : $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim(),
				viewtype : 'All',
				sort : sortItem
			}
			var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim();
			var poupClass = $("#cboxOverlay").prop("class");
			var currentSelect = $(".ship-TosSelect");
			$.ajax({
				url : shipToPopupURL,
				method : "GET",
				data : data,
				success : function(searchPageData) {
					ACC.colorbox.open(ACC.config.selectShipTo, {
						html : searchPageData,
						width : '800px',
						className : $("#cboxOverlay").prop("class"),
						 onComplete: function(e) {
							 $('#cboxWrapper #cboxClose').on('click',function(){
		        					$(currentSelect).val(previous);
		        				});
							 retrieveShipToSortbySessionValue();
							 checkShipToPopUp();
		                    },
		                    onClosed: function() {
		                    	if($('#shipToSelected,#shipto-selected').length)
								{
								window.location.reload();
								}
		                    }
					});
				}
			});

		});
	},
    mobMenuSlide: function (e, ref, btn, time) {
		ACC.global.wWidth = $(window).width();
		ACC.global.wHeight = $(window)[0].innerHeight;
		let target = $(ref);
		if (target.hasClass("showing-menu")) {
			target.removeClass("showing-menu").animate({ left: -target.outerWidth() - 20 }, time);
			$(btn).removeClass("active");
			$(".after-overlay").fadeOut();
		}
		else {
			$(window).scrollTop(0);
			let topHatHeight = $(".header-top-hat").outerHeight();
            $(".global-menu-section").css({ height: ACC.global.wHeight - topHatHeight, top: topHatHeight });
			$(".m2CategoryDashboard").css({ left: '100%' });
			if ($('.language-dropdown').is(":visible")) {
				$('.language-value').trigger("click");
			}
			target.addClass("showing-menu").animate({ left: 0 }, time);
			$(btn).addClass("active");
			$(".after-overlay").fadeIn();
		}
	},
	categories: function (e, targetNum, targetParent) {
		let target = $(e);
		$(targetParent).empty();
		if(target.hasClass("active")){
			$(targetParent).fadeOut();
			target.removeClass("active");
		}
		else{
			$(".m1Links").removeClass("active");
			let targetHTML = "";
			let targetText = target.text();
			targetHTML += '<li class="categoriesBack hidden"><button class="transition-3s btn btn-block bold text-align-left" onclick="$(\'' + targetParent + '\').animate({ left: \'100%\' });" alt="' + targetText + '">' + targetText + '</button></li>';
			target.addClass("active");
			$('[aria-labelledby="btnGroupAccount_' + targetNum + '"] li').each(function () {
				if(targetNum === 5 && $(this).hasClass('shipToPopup')){
                    targetHTML += '<li class="m3Categories shipToPopup" data-global-linkname="ship-to" onclick="ACC.accountdashboard.showShipTos(this)">' + $(this).html() + '</li>';
                }
				else{
					targetHTML += '<li class="m3Categories">' + $(this).html() + '</li>';
				}
			});
			$(targetParent).append(targetHTML);
			$(".m3Categories a").addClass('btn btn-block text-align-left m2Links text-default f-w-500 f-s-18 b-b-grey transition-3s');
			$(targetParent).fadeIn();
		}
	},
	showListData: function (response) {
		let itemName = ["savedlist", "recommended"];
		let monthNames = [
		    "January", "February", "March",
		    "April", "May", "June", "July",
		    "August", "September", "October",
		    "November", "December"
		  ];
		for (let i = 0; i < itemName.length; i++) {
			let ref = i == 0 ? response.savedLists : response.recommendedLists;
			let refHTML = "";
			for (let j = 0; j < ref.length; j++) {
				let refPath = i == 0 ? "" : ref[j].categoryName.replace("&", "%26");
				refPath = ACC.config.encodedContextPath + (i == 0 ? '/savedList/listDetails?code=' + ref[j].code : '/savedList/recommendedListDetails?categoryName=' + refPath);
				let refName = i == 0 ? ref[j].name : ref[j].categoryName;
				let refDate = new Date(ref[j].modifiedTime ? ref[j].modifiedTime : '');
				refHTML += '<a href="' + refPath + '" class="article-link b-l-0 b-r-0 b-t-0 f-s-14-sm-px f-s-14-xs-px f-s-18 l-h-18 list-group-item m-b-0 min-orderqty-errmsg p-l-0-sm p-l-0-xs p-r-0-sm p-r-0-xs p-x-20 p-y-5-xs p-y-5-sm p-y-25 transition-3s">' + refName + '<span class="col-md-3 col-xs-12 f-s-14 p-l-5 f-s-12-xs-px p-l-0-xs p-r-0-xs text-gray-xs text-gray-sm f-s-12-sm-px p-l-0-sm p-r-0-sm pull-right">' + monthNames[refDate.getMonth()] + ' ' + refDate.getDate() + ', ' + refDate.getFullYear() + '</span></a>';
				if(j == 2){
					break;
				}
			}
			$(".list-dashboard-item-" + itemName[i]).append(refHTML);
			if ($(".list-dashboard-item-" + itemName[i] + " .list-group-item").length) {
				$(".dashboard-" + itemName[i] + "-empty").addClass("hidden");
				$(".dashboard-" + itemName[i] + "-filled").removeClass("hidden");
			}
			else {
				$(".dashboard-" + itemName[i] + "-filled").addClass("hidden");
				$(".dashboard-" + itemName[i] + "-empty").removeClass("hidden");
			}
		}
		ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
	},
	getRecommendedData: function (type, target, onload) {
        let targetParent = $("#dash-tab-" + target);
        if (!targetParent.find('.dashboard-carousel-' + target).length) {
			if(!onload){
				loading.start();
			}
            $.ajax({
                type: "GET",
                url: ACC.config.encodedContextPath + "/recommendedProducts",
                data: { placementPage: type },
                success: function (data) {
                    //let data = (target ==  'buyagain') ? rec.buyagain : (target ==  'recently') ? rec.recently : rec.recommended;
					//console.log(data);
                    if (data.recommendedProductsLayer && data.recommendedProductsLayer != "") {
                        targetParent.html(data.recommendedProductsLayer);
                        $(".dashboard-widget-recommended").removeClass("hidden");
                        $('.dashboard-carousel-' + target).owlCarousel({
                            loop: true,
                            margin: 20,
                            items: 7,
							itemsDesktop : [1199, 5],
							itemsDesktopSmall : [979, 4],
							itemsTablet : [768, 3],
							itemsMobile : [479, 2],
                            slideSpeed : 200,
							paginationSpeed : 800,
							rewindSpeed : 1000,
							autoPlayTime: 10000,
							autoPlay: 7000,
                            stopOnHover: true,
                            navigation: true,
                            navigationText: ["", ""],
                            scrollPerPage: true,
                            pagination: true,
                            paginationNumbers: false
                        });
                        ACC.product.enableAddToCartButton();
                        ACC.product.bindToAddToCartForm();
                        ACC.accountdashboard.checkCSP();
						loading.stop();
                    }
					else{
						loading.stop();
					}
                },
                error: function (a, b, e) {
					loading.stop();
                    //console.log(a);
                    //ACC.myquotes.quotePopupError();
                }
            });
        }
    },
	checkCSP: function () {
		$('.productcspCode').each(function () {
			var productCode = $(this).val();
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
			//console.log(csp, isOrderingAccount, isMyStoreProduct, 'isRegulateditem', isProductSellable, 'isRup', isLicensed, isRup, isRegulateditem, inventoryCheck, isEligibleForBackorder);
			if (csp > 0 && isOrderingAccount && isMyStoreProduct && ((isRegulateditem && isProductSellable && ((isRup && isLicensed) || !isRup)) || !isRegulateditem) && !(inventoryCheck && isEligibleForBackorder)) {
				var eleId = "listPageAddToCart_" + productCode;
				$('[id=' + eleId + ']').each(function () {
				$(this).attr("disabled", false);
				});
				var eleId1 = "orderOnlineATC_" + productCode;
				$('[id=' + eleId1 + ']').each(function () {
				$(this).attr("disabled", false);
				});
				//console.log(eleId, eleId1);
			}
		});
	},
	getQuotesData: function (type) {
		if ($(".list-dashboard-item-" + type).length){
			if(!$(".list-dashboard-item-" + type + " .list-group-item").length) {
				$.ajax({
					url: ACC.config.encodedContextPath + "/my-account/shipping-Quotes",
					type: "GET",
					dataType: 'json',
					data:
					{
						customerNumber: $(".current-customer-number").text(),
						skipCount: 0,
						toggle: type,
						fromAD: true
					},
					beforeSend: function(){
						$(".dashboard-" + type + "-filled").addClass("hidden");
						$(".dashboard-" + type + "-empty").addClass("hidden");
						$(".dashboard-" + type + "-loader").removeClass("hidden");
					},
					success: function (response) {
						$(".dashboard-" + type + "-loader").addClass("hidden");
						
						if (response && typeof (response) == "object") {
							ACC.accountdashboard.showQuotesData(response, type);
						}
						else {
							$(".dashboard-" + type + "-filled").addClass("hidden");
							$(".dashboard-" + type + "-empty").removeClass("hidden");
							ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
						}
					},
					error: function (error) {
						$(".dashboard-" + type + "-filled").addClass("hidden");
						$(".dashboard-" + type + "-empty").removeClass("hidden");
						$(".dashboard-" + type + "-loader").addClass("hidden");
						ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
					}
				});
			}
			else{
				$(".dashboard-" + type + "-empty").addClass("hidden");
				$(".dashboard-" + type + "-filled").removeClass("hidden");
				ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
			}
		}
		else{
			ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
		}
	},
	showQuotesData: function (response, type) {
		let monthNames = [
			"January", "February", "March",
			"April", "May", "June", "July",
			"August", "September", "October",
			"November", "December"
		];
		let refHTML = "";
		let count = 0;
		for (let j = 0; j < response.length; j++) {
			if(response[j].quoteId && response[j].quoteId != ""){
				let refPath = ACC.config.encodedContextPath + '/my-account/my-quotes/' + response[j].quoteId;
				let refDate = new Date(type == 'expired' ? response[j].expirationDate : response[j].dateSubmitted);
				refHTML += '<a href="' + refPath + '" class="article-link b-l-0 b-r-0 b-t-0 f-s-14-sm-px f-s-14-xs-px f-s-18 flex-center l-h-18 list-group-item m-b-0 min-orderqty-errmsg p-l-0-sm p-l-0-xs p-r-0-sm p-r-0-xs p-x-20 p-y-15 p-y-5-sm p-y-5-xs transition-3s"><span class="col-md-8 padding0"><span class="f-s-16 l-h-20 text-gray text-muted">#' + response[j].quoteId + '</span><br><span class="text-default">' + response[j].jobName + '</span></span><span class="col-md-4 col-xs-12 f-s-12-sm-px f-s-12-xs-px f-s-14 p-r-0 p-l-0-sm p-l-0-xs pull-right text-gray-sm text-gray-xs">' + monthNames[refDate.getMonth()] + ' ' + refDate.getDate() + ', ' + refDate.getFullYear() + '</span></a>';
				count++;
			}
			if (count == 4) {
				break;
			}
		}
		$(".list-dashboard-item-" + type).html(refHTML);
		if ($(".list-dashboard-item-" + type + " .list-group-item").length) {
			$(".dashboard-" + type + "-empty").addClass("hidden");
			$(".dashboard-" + type + "-filled").removeClass("hidden");
		}
		else {
			$(".dashboard-" + type + "-filled").addClass("hidden");
			$(".dashboard-" + type + "-empty").removeClass("hidden");
		}
		ACC.global.elemHeightEqual('.js-dashboard-widget', 1024);
	},
	dashboardListCreateForm: function () {
		let status = true;
		let target = $(".dashboard-create-list-input");
		let targetVal = $.trim(target.val());
		target.val(targetVal);
		$(".dashboard-create-list-error").css("display", "none").empty();
		if (!targetVal || targetVal == "") {
			status = false;
			$(".dashboard-create-list-error").html("Enter a list Name");
		}
		else {
			$.ajax({
				type: 'GET',
				url: ACC.config.encodedContextPath + "/savedList/listExists?wishListName=" + targetVal,
				datatype: "json",
				async: false,
				success: function (result) {
					if (result) {
						status = false;
						$(".dashboard-create-list-error").html("List name already exisits");
					}
				},
				error: function () {
					status = false;
				}
			});
		}
		if (status) {
			$("#siteoneSavedListCreateForm").submit();
		}
		else{
			$(".dashboard-create-list-error").css("display", "block");
		}
	},
	billingAccountOverlay: function () 
    {		
    		var pageId = $("#pageId").val();
    		var isOrderingAccount =  null;
    		
    		if($("#isOrderingAccount").val())
    		{
    			isOrderingAccount =  $("#isOrderingAccount").val();
    		}
    		
    		var sessionShipToId = null;
    		if($("#sessionShipToId").val())
    		{
    			sessionShipToId = $("#sessionShipToId").val();
    		}
    		
    		if(pageId == 'cartPage' && isOrderingAccount == 'false')
    		{
    			if(null != $("#listOfShipTos").val() || $("#listOfShipTos").val() != "")
		    	{
			    	var listOfShipTo = JSON.parse($("#listOfShipTos").val());
			    	       
			    	var selectBox = "<select id='shipToSelectionOptions' class='shipToSelectionInPopup' style='width:100%'>";
		               
			    	$.each(listOfShipTo,function(index, shipTo) 
		    		{ 	
			    		if(shipTo.isOrderingAccount)
			    		{
				    		if(shipTo.name !== null && shipTo.name !== '')
				    		{
				    			selectBox = selectBox + '<option value="' + shipTo.uid + '" selected>' +  shipTo.name + ' : ' + shipTo.displayId + '</option>';
				    		}
				    		else
				    		{
				    			selectBox = selectBox + '<option value="' + shipTo.uid + '">' + shipTo.displayId + '</option>';
				    		}
			    		}
		    		});
		    	    selectBox = selectBox + "</select>";
		    	}
    	
    			$.colorbox({
	    			html: "<div class='Billing-pop-up'><div class='ship-to-header'><span  style='color:black;font-size: 22px;font-family: Geogrotesque-Medium;'>"+ACC.config.orderingAccountSelect+"</span></div><div class='PopupBox popup'><span  style='font-size: 12pt;'><p>"+ACC.config.sorryMsg 
	    				+ACC.config.orderingAccountError+"</p></span><br><label for='shipToSelectionOptions' style='color:inherit;'>"+ACC.config.selectedAccount+"</label></div>"+selectBox+"<br>"
	    				+"<br><button type='button' class='Billing-pop-up-button btn-block btn btn-primary' id='changeShipTo'>"+ACC.config.updateShipTo+"</button></div>",
	    			maxWidth:"100%",
	    			width:"auto",
	    			maxHeight:"100%",
					overflow:"auto",
					opacity:0.7,
					className:"addNotFound",
					title:false,
					close:'<span class="glyphicon glyphicon-remove"></span>',
					onComplete: function()
					{
						$("#cboxTitle").css("display","none");
						$("#cboxLoadedContent").attr('style','margin-top:40px !important');
						$("body").css("overflow-y", "hidden");
					},
					onClosed: function() {
	    				$("body").css("overflow-y", "auto");
	    			}
    			   
    			});
    		}
        
    	
        $(document).on("click","#changeShipTo", function () 
        {
        	var unitId=$('.shipToSelectionInPopup').find(":selected")[0].value;
        	ACC.global.updateShipToCall(unitId);
        });
    
    },
    bindShipToListApiCall: function ()
    {
		var uid=$("#parentUnitId").val();
		
		if(null != uid && uid != "")
		{
			if(localStorage.getItem(uid))
			{
				ACC.accountdashboard.bindShiptoDropdown(localStorage.getItem(uid));
			}
			else
			{
				$.ajax({
			        type: 'GET',
			        url: ACC.config.encodedContextPath + '/my-account/listofshipto',
			        cache: false,
			        dataType: "json",
			        data: {"uid":uid},
			        success: function(shipTos)
			        {
			        	if(shipTos.length > 0)
		        		{
			        		var localStorageValue = "";
				        	
				        	$.each(shipTos, function( index, shipTo ) 
		        	    	{
		        	    		var id = shipTo.uid;
		        	    		var optionValue = shipTo.uid + ":" + shipTo.name + ":" + shipTo.displayId;
		        	    		if("" != localStorageValue)
		        				{
		        	    			localStorageValue = localStorageValue + '|' + optionValue;
		        				}
		        	    		else
		        	    		{
		        	    			localStorageValue = optionValue;
		        	    		}
		        	    	});  
		        			localStorage.setItem(uid, localStorageValue);
				        	
				        	ACC.accountdashboard.bindShiptoDropdown(localStorageValue);
		        		}
			        	
			        	else
			        	{
			        		$('#shipToSelected').attr('disabled', 'disabled');
			        	}
			        }
			        
			    });
			}
			if($('#shipToSelected').length) {
				$('#shipToSelected').editableSelect();
				$(".shipto-dropdwon .es-visible").append("<hr/>");
				$(".shipto-dropdwon .es-visible").last().find("hr").remove();
			
				$('#shipToSelected').on('select.editable-select', function(e, el){
					if ($("#shipToSelected").val()=="Search More Ship-To's" || $("#shipToSelected").val()=="Buscar más envíos"){
						removeShipToSortbySessionValue();
						var currentSelect = this;
						previous = $(this).value;
						var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim()
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim()+'?searchParam='+searchParam;
						$.ajax({
							url: shipToPopupURL,
							method: "GET", 
							success: function(searchPageData) {
								if(searchPageData != ""){
									$(".js-document-shipto-box").html(searchPageData);
									ACC.accountdashboard.shipToSlide(e, '.js-document-shipto-box', 0, 500);
									$(".js-document-shipto-scroll").height(ACC.global.wHeight - ($("#search-ship-to-popup").offset().top + 118));
								}
								else{
									ACC.myquotes.quotePopupError();
									// onComplete
									$('#cboxWrapper #cboxClose').on('click', function () {
										$(currentSelect).val(previous);
									});
									checkShipToPopUp();
									$(document).on('click keyup', '#search-ship-to-popup', function () {
										checkShipToPopUp();
									});
									// onClosed
									if ($('#shipToSelected, #shipto-selected').length) {
										window.location.reload();
									}
								}
							},
							error: function (e) {
								ACC.myquotes.quotePopupError();
							}
						});
					}
					else{
						
					window.location.href= ACC.config.encodedContextPath +"/my-account/ship-to/"+ $(el).data('value');
					$('#shipToSelected').off();
					}
				});
			}
			
		}
		
		$(".logout").on('click', function () 
		{
			var jusername=$.cookie("j_username");
			if(jusername != "" && jusername != null) {
				$.cookie('jusername', null,{path:'/'});
			}
			var userfirstname=$.cookie("newfirstname");
			if(userfirstname != "" && userfirstname != null) {
				$.cookie('newfirstname', null,{path:'/'});
			}
			localStorage.clear();
			sessionStorage.clear();
			window.location.href= ACC.config.encodedContextPath +"/logout";
		});
		
    },

    bindShipToAccountOverview: function ()
    {
		var uid=$("#parentUnitId").val();
		
		if(null != uid && uid != "")
		{
			if(localStorage.getItem(uid))
			{
				ACC.accountdashboard.bindShiptoDropdown(localStorage.getItem(uid));
			}
			else
			{
				$.ajax({
			        type: 'GET',
			        url: ACC.config.encodedContextPath + '/my-account/listofshipto',
			        cache: false,
			        dataType: "json",
			        data: {"uid":uid},
			        success: function(shipTos)
			        {
			        	if(shipTos.length > 0)
		        		{
			        		var localStorageValue = "";
				        	
				        	$.each(shipTos, function( index, shipTo ) 
		        	    	{
		        	    		var id = shipTo.uid;
		        	    		var optionValue = shipTo.uid + ":" + shipTo.name + ":" + shipTo.displayId;
		        	    		if("" != localStorageValue)
		        				{
		        	    			localStorageValue = localStorageValue + '|' + optionValue;
		        				}
		        	    		else
		        	    		{
		        	    			localStorageValue = optionValue;
		        	    		}
		        	    	});  
		        			localStorage.setItem(uid, localStorageValue);
				        	
				        	ACC.accountdashboard.bindShiptoDropdown(localStorageValue);
		        		}
			        	
			        	else
			        	{
			        		$('#shipto-selected').attr('disabled', 'disabled');
			        	}
			        }
			        
			    });
			}
			if($('#shipto-selected').length) {
				$('#shipto-selected').editableSelect();
				$(".shipto-dropdwon .es-visible").append("<hr/>");
				$(".shipto-dropdwon .es-visible").last().find("hr").remove();
			
				$('#shipto-selected').on('select.editable-select', function(e, el){
					if ($("#shipto-selected").val()=="Search More Ship-To's" || $("#shipto-selected").val()=="Buscar más envíos"){
						var currentSelect = this;
						 previous = $(this).value;
						 var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim()
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim()+'?searchParam='+searchParam;
						$.ajax({
						            url: shipToPopupURL,
						            method: "GET", 
						            success: function(searchPageData) {
						                ACC.colorbox.open('Search or Select a Ship-To', {
						                    html: searchPageData,
						                    width: '800px',
						                    className:"shipto-popup-mb accountpopup",
						                    onComplete: function(e) {
						                    	
						                    	$(document).on('click','#cboxContent .ship-to-link', function(){ 
						               			 
						               			 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
						               			 //var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
						               			 var shipToUrl = ACC.config.encodedContextPath + "/my-account/account-overview?accountId="+unitId;
						               			 $("a.ship-to-link").prop("href",shipToUrl);
						                    	});
						                    	
						                    	checkShipToPopUp();
						                    	$(document).on('click keyup', '#search-ship-to-popup', function(){
						                    		checkShipToPopUp();
						                    	});
						                    },
						                    onClosed: function() {	
												                   	
											}
						                });
						            }
						        });
						
						
						
						     }
					else{
						
						window.location.href= ACC.config.encodedContextPath +"/my-account/account-overview?accountId="+ $(el).data('value');
						$('#shipto-selected').off();
						 
					}
					
				});
			}
			
		}
		
		$(".logout").on('click', function () 
		{
			var jusername=$.cookie("j_username");
			if(jusername != "" && jusername != null) {
				$.cookie('jusername', null);
			}
			var userfirstname=$.cookie("newfirstname");
			if(userfirstname != "" && userfirstname != null) {
				$.cookie('newfirstname', null);
			}
			localStorage.clear();
			sessionStorage.clear();
			window.location.href= ACC.config.encodedContextPath +"/logout";
		});
		
    },
    
    
    
    shipToListApiCall: function ()
    {
 	   	if($('#shipto-selected').length) {
 			$('#shipto-selected').editableSelect();
 			$(".shipto-dropdwon .es-visible").append("<hr/>");
			$(".shipto-dropdwon .es-visible").last().find("hr").remove();
 		
 			$('#shipto-selected').on('select.editable-select', function(e, el){
 				if ($("#shipto-selected").val()=="Search More Ship-To's" || $("#shipto-selected").val()=="Buscar más envíos"){
					var currentSelect = this;
					 previous = $(this).value;
					 var searchParam = $("#search-ship-to-popup").val() == undefined ? "" : $("#search-ship-to-popup").val().trim()
					var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup/'+($("#unitUid").val()).trim()+'?searchParam='+searchParam;
					$.ajax({
					            url: shipToPopupURL,
					            method: "GET", 
					            success: function(searchPageData) {
					                ACC.colorbox.open('Search or Select a Ship-To', {
					                    html: searchPageData,
					                    width: '800px',
					                    className:"shipto-popup-mb accountpopup",
					                    onComplete: function(e) {
					                    	
					                    	$(document).on('click','#cboxContent .ship-to-link', function(){ 
					               			 
					               			 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
					               			 //var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
					               			 var shipToUrl = ACC.config.encodedContextPath + "/my-account/account-overview?accountId="+unitId;
					               			 $("a.ship-to-link").prop("href",shipToUrl);
					                    	});
					                    	 retrieveShipToSortbySessionValue();
					                    	checkShipToPopUp();
					                    },
					                    onClosed: function() {	
											                   	
										}
					                });
					            }
					        });
															
					     }
				else{

 				window.location.href= ACC.config.encodedContextPath +"/my-account/getshiptoinfo/"+ $(el).data('value');
 				$('#shipto-selected').off();
 				}
 			});
 		}
     },
    
	bindShiptoDropdown: function (localStorageValue)
    { 
		var isLinkAvailable=false;
		var optionValues = localStorageValue.split("|");
		$.each(optionValues, function(index, val) 
    	{   
			if(index >=300){
				isLinkAvailable=true;
				return false;
				
			}
    		var optionValue = val.split(":");
    		$('#shipToSelected, #shipto-selected').append("<option data-value='"+ optionValue[0] +"' value='" + optionValue[2] + "-" + optionValue[1] + "'>"+ optionValue[2] + "-" + optionValue[1] +"</option>"); 
    	}); 
		  
		
		if (isLinkAvailable===true) {
			$('#shipToSelected, #shipto-selected').append("<option value='SearchMoreShipToLink' style='color: #50a0c5;text-decoration: underline;'>"+ACC.config.searchMore+"</option>"); 
			}
    },

	  
    bindPartnerProgram : function() {
		$(document).on("click", "#partnerProgramLink", function(e) {
			e.preventDefault();
			var partnerMessage=$(this).data("partner-message");
			var ppurlStatus=$(this).data("ppurl");
			var urlToOpen = ACC.config.encodedContextPath +"/my-account/partner-program";
				if(ppurlStatus){
					window.open(urlToOpen, '_blank');
				}
				else{
					$.colorbox({
					html:  "<div class='PopupBox col-md-12 col-xs-12'><div>"+partnerMessage+"<br/><span class='hidden-xs'><br/></span><div class='row'><div class='col-md-4 col-xs-4'><button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >OK</button></div></div></div></div>",
					width:"550px",                                         	
					maxHeight:"100%",
					overflow:"auto",
					opacity:0.7,
					className: "PartnerProgram",
					title: false,
					closeButton: false,
					onComplete: function() {
						_AAData.popupPageName= ACC.config.redeempointsPageName;
						_AAData.popupChannel= ACC.config.myaccountpathingChannel; 
						try {
									_satellite.track('popupView');
						} catch (e) {}
						}
					});
				}
		});
	},
	
    
    emailSignUp: function()
    {
        /**
        * Suppressing the emailsignup popup functionality.
        $(document).on("click", ".notNow", function()
		{
			$.cookie("emailSignUp", "done", { expires: 90 });
			ACC.colorbox.close();
		});
    	
    	var showPopup = false;
    	
    	if($.cookie("emailSignUp") == null)
    	{
    		showPopup = true;
    	}
    	
    	if($("#hasSignedUp").val() != "" && $("#hasSignedUp").val() == 'true')
    	{
    		showPopup = false;
    	}
    	
    	if($("#pageId").val() =='siteonehomepage' && showPopup)
    	{
    		ACC.accountdashboard.signUpPopup();
    	}
    	*/
    	
    	$(document).on("blur", "#emailId", function()
		{
    		if(ACC.formvalidation.validateEmailPattern($("#emailId").val()))
		    {
				 ACC.formvalidation.removeErrorClassForRegForm('#emailId', '#emailError', '#emailId\\.errors', ERRORMSG.invalid.email);
			}
			else
			{
				 ACC.formvalidation.addErrorClassForRegForm('#emailId', '#emailError', '#emailId\\.errors', ERRORMSG.invalid.email);
			}
	        
	    });
    	
    	$(document).on("click", "#agree", function()
    	{
    		if($("#agree").prop("checked") == false)
    		{
    			$("#agreeError").html("<div class='bg-danger '><div class='panel-body'><font color='red'>"+ACC.config.pleaseCheckBox+"</font></div></div>");
    		}
    		else
    		{
    			$("#agreeError").html("");
    		}
    		
    	});
    	
    	$(document).on("click", "#signUp", function (e) 
		{
    		var status = true;
    		
    		/*if($("#emailId").val() == "" || $("#emailId").val() == null)
    		{
    			//$(".emailError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>Please enter email address.</font></div></div> ");
        		ACC.formvalidation.addErrorClassForRegForm('#emailId', '#emailError', '#emailId\\.errors', ERRORMSG.global.email);
    			status = false;
    		}*/
    		if(ACC.formvalidation.validateEmailPattern($("#emailId").val()))
    		{
    			ACC.formvalidation.removeErrorClassForRegForm('#emailId', '#emailError', '#emailId\\.errors', ERRORMSG.invalid.email);
    		}
			else
			{
				 status = false;
				 ACC.formvalidation.addErrorClassForRegForm('#emailId', '#emailError', '#emailId\\.errors', ERRORMSG.invalid.email);
			}
    		
    		if(ACC.formvalidation.validatezipcode(e, $("#zipcode").val()))
    		{
    			//$("#errorZipcode").html("<font color='red'>Postal Code for US must be numeric and with length 5 or 10 with a ' - ' after 5 digits.</font>");
    			ACC.formvalidation.removeErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors',ACC.config.dashboardValidZip);
    		}
    		else
    		{
    			status = false;
    			ACC.formvalidation.addErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors', ACC.config.dashboardValidZip);
    			
    		}
    		
    		if($("#agree").prop("checked") == false)
    		{
    			$("#agreeError").html(" <font color='red'>"+ACC.config.pleaseCheckBox+"</font> ");
    			status = false;
    		}
    		else
    		{
    			$("#agreeError").html("");
    		}
    		
    		var briteverify= document.getElementById('briteVerifyEnable').value;
    		
    		if(status)
    		{
    			if(briteverify == "true")
        		{
    				 url = ACC.config.encodedContextPath +'/request-account/briteVerifyValidate?email='+$('#emailId').val();

                    $.ajax({
         				url: url,
         				type: "get",
         				timeout: 5000,
         				success: function (response)
         				{
         					if(response !="invalid")
         					{
         					   ACC.briteverify.emailSubscription($("#emailId").val());
         					}
         				    else
         				    {
         				    	$("#emailError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>>"+ACC.config.dashboardValidEmail+"</font></div></div>");
         				    }
         				},
         				
         				error: function(response, textstatus, message) 
    					{
    					  if(textstatus == "timeout" || textstatus == "error") 
    					  {
    						 $("#emailError").html("<div class='bg-danger' style='margin:10px 0px;'><font color='red'><div class='panel-body'>"+ACC.config.unableDashboardValidate+"</div></font></div>");
    					    } 
    					}
    				});
    				
        		}
        		else
        		{
        		    ACC.briteverify.emailSubscription($("#emailId").val());
        		}
    		}
		});
    	
    	$(document).on("focusin", "#emailId",function(e)
		{
			$(".emailError").empty();
		});
    	
    },
    
    
    bindInvoiceEmail: function()
    {
    	$(document).on("click", "#invoiceEmail", function()
		{
    		window.scrollTo(0,0);
    		ACC.accountdashboard.invoicePopup();
		});
    	
    	$(document).on("click", "#invoiceEmailSubmit", function()
		{
    		$("#emailError").html("");
		    if($("#emailIdInvoice").val() == "" || $("#emailIdInvoice").val()  == null)
		    {
			   status = false;
			   $("#emailError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>"+ACC.config.emailAddressError+"</font></div></div>");
		    }
		    else
		    {
			   ACC.briteverify.checkToEmails($("#emailIdInvoice").val());
		    }
		});
    	
    },
    
    bindOrderDetailsEmail: function()
    {
    	$(document).on("click", "#orderEmail", function()
		{
    		ACC.accountdashboard.orderDetailsPopup();
		});
    	
    	$(document).on("click", "#orderEmailSubmit", function()
		{
    		$("#emailError").html("");
    		if($("#emailIdOrder").val() == "" || $("#emailIdOrder").val() == null)
    		{
    			status = false;
    			$("#emailError").html("<div class='bg-danger' style='margin:10px 0px;'><div class='panel-body'><font color='red'>"+ACC.config.emailAddressError+"</font></div></div>");
    		}
    		else
    		{
       		   ACC.briteverify.checkToEmails($("#emailIdOrder").val());
    		}
		});
    	
    },
    
    buildEditUserPage: function() {
    	$( document ).ready(function() {
    		var userPermissionCount = $(".userPermissionText").length;
    		
    		for(i=0;i<userPermissionCount-1;i++) {
    			$(".userPermissionText")[i].append(",");
    		}
    	});
    },
    
    
    sendOrderDetailEmail: function(email,orderCode,invoiceCode,uid)
	{
		loading.start();
		var data = {
					email : email ,
					invoiceCode : invoiceCode,
					uid : uid ,
				}
		$.ajax({
		     	type: "GET",
		     	url: ACC.config.encodedContextPath +"/sendorderdetails/"+orderCode,
		     	cache: false,
		     	data : data,
		     	dataType: "json",
		     	success: function (response) 
		     	{
		     		$("#colorbox").removeClass("order-details-email-pop-up");
		     		ACC.global.successPopup();	
		        }
		     	
			}).always(function() {
				loading.stop();
			});
					
	},
	
	
	sendInvoiceEmail: function(email,invoiceNumber,accountNumber)
	{
		loading.start();
		var data = {
					email : email ,
					uid : accountNumber ,
				}
		$.ajax({
         	type: "GET",
         	url: ACC.config.encodedContextPath + "/invoiceDetailsEmail/"+invoiceNumber,
         	cache: false,
         	data : data,
         	dataType: "json",
         	success: function (response) 
         	{
         		$("#colorbox").removeClass("email-invoice-popup");
         		ACC.global.successPopup();	
             }
			}).always(function(){
				loading.stop();
			});
	},
	
    
    signUpPopup: function()
	{
		var html = ["<div class='PopupBox col-md-12'>",
		            "<div class='row'>",
		            "<div class='email-signup-heading'>Email Signup</div>",
						"<p>"+ACC.config.promotionsMsg+"</p>", 

						"<div id = 'emailError'></div>",
						"<div id='errorZipcode'></div>" +
						"<div id='agreeError'></div><br/>",
						"<label for='emailId'>Email address </label><br>",
						"<input type='text' class='form-control' id='emailId'> <br>",
						"<label for='zipcode'>ZIP code </label><br><input type='text' id='zipcode' class='form-control'><br/><div>",
						"<p></p><label class='email-pop-label'>I am a:</label> <p class='cl'></p><div class='row'>",
						"<div class='label-columns'>",
							"<label class='label-highlight' style='color:#5a5b5d;display:block'>",
								"<div class='colored-radio'>",
									"<input type='radio' name='role' value='Contractor' checked>",
								"</div>&nbsp;"+ACC.config.landscapeProfessional+"",
							"</label>",
						"</div>",
						"<div class='visible-xs visible-sm hidden-md hidden-lg'>",
						"<div class='cl'>",
						"</div>",
						"<br/>",
						"</div>",
						"<div class='label-columns'> ",
							"<label class='label-highlight' style='color:#5a5b5d;display:block'>",
								"<div class='colored-radio'>",
									"<input type='radio' name='role' value='Homeowner'>",
								"</div>&nbsp;Homeowner",
							"</label> ",
						"</div></div>",
						"<div class='cl'></div>",
						"<div class='email-signup-text'>",
						"<div class='row'>",
						"<div class='colored col-md-12'>",
							"<input type='checkbox' name='agree' id='agree'> <span class='small-font'><label for='agree'>"+ACC.config.emailCommunication+"</span></label>",
						"</div>",
						"<div class='cl hidden-xs hidden-sm'>",
						"</div>",
						"</div>",
						"</div>",
						"<div class='row'>",
						"<div class='col-md-12 col-sm-12'>",
						"<div class='row'>",
						"<div class='cl'>",
						"</div>",
						"<div class='col-md-4 col-sm-6 col-xs-12'>",
						"<input id='signUp' class='btn btn-primary btn-block' type='button' value='"+ACC.config.signUp+"'>",
						"</div>",
						"<div class='col-md-3 col-sm-3  col-xs-12  text-center' style='line-height:12pt;'>",
						"<br/><a href class='notNow'><u>Not now</u></a>",
						"</div>",
						"</div>",
						"</div>",
						"<div class='cl'>",
						"</div>",
						"</div>",
					"</div>"].join("");
		
			ACC.colorbox.open("<div class='hidden-md hidden-lg hidden-sm mobile-header'>Email Signup</div>", {
				html: html,
				width:"630px",
				maxHeight:"100%",
				overflow:"auto",
				opacity:0.7,
				className:"email-signup",
				onClosed: function() {
					$.cookie("emailSignUp", "done", { expires: 90 });
				}
			})
	},
	
	signUpSuccessPopup: function()
	{
			$.colorbox({
			html:  "<div class='PopupBox-inner col-md-12 col-xs-12'><div class='row'><p class='email-signup-headingSec'>"+ACC.config.thankYou+"</p><p><br/>"+ACC.config.emailConfirmationMsg+"</p><br/><span class='hidden-xs'><br/></span><div class='row'><div class='col-md-6 col-xs-12'><button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >Close</button></div></div></div></div>",
			width:"475px",
			maxHeight:"100%",
			overflow:"auto",
			opacity:0.7,
			className:"email-signup-popup",
			title:"<div class='hidden-md hidden-lg hidden-sm mobile-headerpart'>Email Signup</div>",
			close:'<span class="glyphicon glyphicon-remove"></span>',
			onComplete: function(){
				$("#colorbox").removeClass("email-signup");
				window.scrollTo(0,0);
			},
			onClosed: function(){
				$("#colorbox").removeClass("email-signup-popup");
			}
		});
	},
	
	signUpFailurePopup: function(msg)
	{
			$.colorbox({
				html: "<div class='PopupBox col-md-12 col-xs-12'><div class='hidden-xs cl'><br/></div><div class='row'><p>"+msg+"</p><br/><span class='hidden-xs'><br/></span><div class='row'><div class='col-md-6 col-xs-12'><button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >Close</button></div></div></div></div>",
				width:"460px",
				maxHeight:"100%",
				overflow:"auto",
				opacity:0.7,
				className:"email-signup-popup",
				title:"<div class='hidden-md hidden-lg hidden-sm mobile-headerpart'>Email Signup</div><h1 class='email-signup-headingpart headline'>Sorry !</h1>",
				close:'<span class="glyphicon glyphicon-remove"></span>',
				onComplete: function(){
				}
			});
	},
	
	invoicePopup: function()
	{
			$.colorbox({
			html: "<div class='PopupBox'><h1 class='headline2 invoice-headline'>"+ACC.config.shareViaEmail+"</h1><div class='margin20 share-invoice-email-description'>"+ACC.config.tellOthers+"</div><label for='emailIdInvoice' style='font-size:14px; color:inherit;'>"+ACC.config.emailTo+"</label><textarea class='col-md-12 col-sm-12 col-xs-12 form-control' rows='6' cols='10'  type='textarea' placeholder='"+ACC.config.emailToPlaceHolder+"' id='emailIdInvoice'></textarea><br><div id='emailError' style='margin:30px 0px'></div><br><input class='btn btn-primary margin20' type='button' value='"+ACC.config.contactUsEmail+"'  id='invoiceEmailSubmit'></div>",
			maxWidth:"650px",
			width:"430px",
			top:"0",
			maxHeight:"100%",
			overflow:"auto",
			opacity:0.7,
			className:"addNotFound",
			title: "<h4 class='hidden-md hidden-lg share-invoice-email-title headline'><span class='headline-text'>"+ACC.config.shareByEmail+"</span></h4>",
			close:'<span class="glyphicon glyphicon-remove" id="close"></span>',
			onComplete: function(){
				$("#colorbox").addClass("email-invoice-popup");
				$("body").css("overflow-y", "hidden");
			},
			onClosed: function(){
				$("#colorbox").removeClass("email-invoice-popup");
				$("body").css("overflow-y", "auto");
			}
		});
	},
	
	orderDetailsPopup: function()
	{
			$.colorbox({
			html: "<div class='PopupBox'>" +
						"<h3 class='headline3 order-title' style='margin-top:-10px;'>"+ACC.config.shareOrderDetailViaEmail+"</h3>" +
						"<div class='margin20  share-order-details-email-description'>"+ACC.config.dashboardTellOthers+"</div>"+
						"<label for='emailIdOrder' style='color:inherit;'>"+ACC.config.emailTo+"</label>" +
						"<div class='order-email-input'>" +
						"<textarea rows='5' cols='38' placeholder='"+ACC.config.enterEmail+"' id='emailIdOrder'></textarea>" +
						"</div>" +
						"<div id = 'emailError'></div> <br>" +
						"<div class='order-email-submit col-xs-5 col-md-4'><input  class='btn btn-primary btn-block' type='button' value='"+ACC.config.contactUsEmail+"' id='orderEmailSubmit'></div>" +
					"</div>",
			maxWidth:"auto",
			width:"450px",
			height:"auto",
			top:"0",
			maxHeight:"100%",
			overflow:"auto",
			opacity:0.7,
			title:"<h3 class='order-details-email-title-mobile hidden-md hidden-lg'>"+ACC.config.shareViaEmail+"</h3>",
			className:"addNotFound",
			close:'<span class="glyphicon glyphicon-remove" id="close"></span>',
			onOpen: function() {
                 $('#colorbox').addClass('order-details-email-pop-up');
               },
			onComplete: function(){
				$("#colorbox").addClass("order-details-email-pop-up");
				$("body").css("overflow-y", "hidden");
			},
			onClosed: function(){
				$("#colorbox").removeClass("order-details-email-pop-up");
				$("body").css("overflow-y", "auto");
			}
		});
	},
	
	
	
	showTeamRole: function(){
		$(document).ready(function () {

		    if ($('#Roled_b2bcustomergroup').is(':checked')) {
		        $('.team-role').show();
		    } else {
		        $('.team-role').hide();
		    }

		    
		});
		
		$("#Role_b2bcustomergroup,#Roled_b2bcustomergroup").click(function () {
			$(".team-role").show("slow");
		});
		
		$("#Role_b2badmingroup, #Roled_b2badmingroup").click(function () {
			$(".team-role").hide('slow');
		});

		$('input:radio[name="roles"]').change(function () {
				if ($("#Role_b2badmingroup, #Roled_b2badmingroup").is(':checked')) {
					$(".team-role").hide('slow');
					$('.role-box input:checkbox[name="invoicePermissions"]').prop('checked', true);
					$('.role-box input:checkbox[name="accountOverviewForParent"]').prop('checked', true);
					$('.role-box input:checkbox[name="accountOverviewForShipTos"]').prop('checked', true);
					$('.role-box input:checkbox[name="placeOrder"]').prop('checked', true);
					$('.role-box input:checkbox[name="payBillOnline"]').prop('checked', true);
				} else {
					$('.role-box input:checkbox').prop('checked', false);
					$(".team-role").show("slow");
				}
		});
			
		$("div.team-role").each(function () {
	        var $this = $(this);
	        $this.html($this.html().replace(/&nbsp;/g, ''));
	    });
		
	},
	
	requestaccountFormCutomerType: function(){
		if($(".page-requestaccount").length){
			ACC.global.numberInputEvents("#contrEmpCount", 1, 99999, 1);
		}
		$(document).ready(function() {
			$(window).scrollTop(0);
			if($("input:radio[id='typeOfCustomer1']").is(":checked")) {
		    		 $('.homeownerSec, .verification-icon-fill,.confirmation-icon-fill').hide();
		    		 $('.contratorSec,.business-icon,.verification-icon,.confirmation-icon').show();
		    	 }
		        
			 $('#typeOfCustomer1').click(function () {
			        $('.firstName').attr('disabled',true);
					$('.lastName').attr('disabled',true);
					$('.emailaddress').attr('disabled',true);
					$('#firstName').attr('disabled',false);
					$('#lastName').attr('disabled',false);
					$('#emailaddress').attr('disabled',false);
			        $('.homeownerSec').hide();
			        $('.contratorSec').show();
			        $('.banner-icon-wrapper, .btn-footr').removeClass("hidden");
					$('.RTA_wrapper,.businessDetails').show();
					$('#accountSelect').hide();
					$('.next_goback_wrapper').show();
					$('#realtime_submit').addClass('requestAccountFormSubmit');
					$('.companyphone').attr('disabled',false);
					$('#phoneNumber').attr('disabled',true);
					if(ACC.config.encodedContextPath == '/es'){
						$('#languagePreference2').click();
						$("#languagePreference2").parent().addClass("spanishPage-radio-circle");
					}else{
						$('#languagePreference1').click();
					}
			    });	   

		      $('#typeOfCustomer2').click(function () {
		    	    if($(".page-contactUs").length || $(".page-contactUs-ca").length){
		    		  $('#firstName').attr('disabled',false);
		    		  $('#lastName').attr('disabled',false);
		    	    }else{
		    	      $('#firstName').attr('disabled',true);
			    	  $('#lastName').attr('disabled',true);
		    	    }
					$('#emailaddress').attr('disabled',true);
					$('.firstName').attr('disabled',false);
					$('.lastName').attr('disabled',false);
					$('.emailaddress').attr('disabled',false);
			        $('.contratorSec').hide();
			        $('.homeownerSec').show();
			        $('.banner-icon-wrapper, .btn-footr').removeClass("hidden");
			    	$("#contrYearsInBusiness").val("");
			    	$("#contrEmpCount").val("");
			    	$("#contrPrimaryBusiness").val("");
			    	$("#contrPrimaryBusinessL1").val(ACC.config.select);
			    	$("#companyName").val("");
			    	//$('.banner-icon-wrapper-homeowner').show();
					$('.RTA_wrapper,.retailDetails').show();
					$('#accountSelect').hide();
					$('.next_goback_wrapper').show();
					$('#realtime_submit').addClass('requestAccountFormSubmit');
					$('.companyphone').attr('disabled',true);
					$('#phoneNumber').attr('disabled',false);
					if(ACC.config.encodedContextPath == '/es'){
						$('#homelanguagePreference2').click();
					}else{
						$('#homelanguagePreference1').click();
					}
		    	
		    });
		    $('.gb-footer-btn').click(function () {
				$(window).scrollTop(0);
				$('.RTA_wrapper,.businessDetails,.retailDetails').hide();
				$('#accountSelect').show();
				$('.next_goback_wrapper').hide();
				$('#realtime_submit').removeClass('requestAccountFormSubmit');
				if($("input:radio[id='typeOfCustomer1']").is(":checked") && !$(".verification-container-step2").hasClass('hidden')) {
					$('.RTA_wrapper,.businessDetails').show();
					$('.verification-container, .verification-container-step2').addClass("hidden");
					$('#accountSelect,.businessDetails,.retailDetails').hide();
					$('.next_goback_wrapper').show();
				}
				window.location.reload(true);
			});	
		});
	},
	
	resetDropdown:function(){

		if (performance.navigation.type == 2) {
		
			var cmspageid = $("#cmspageuid").val();
			if (cmspageid == 'my-company'){
				sessionStorage.setItem('selecteduserSession','All');
				window.location.href= ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=&filterAdmin=false';
			}else if(cmspageid == 'orders'){
				sessionStorage.setItem('selectedOrderSession','All');
				window.location.href = ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateFrom='+($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
			}else if(cmspageid == 'invoicelistingpage'){
				sessionStorage.setItem('selectedInvoiceSession','All');
				window.location.href = ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim()+'?invoiceShiptos=All&viewtype=All&searchParam=&dateFrom='+ ($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
			}
			else if (cmspageid == 'ewalletdetailspage'){
				sessionStorage.setItem('selectedewalletSession','All');
				window.location.href= ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=';
			}
		}
	},
	
	shiptoPopupManageUsers: function(){
		$(document).on('click','#cboxContent #ShipToUsers_popup', function(){ 

			 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
			 var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
			if($(".invoice-shiptopopup").length > 0){
				if ($(".ship-TosUser option").eq(0).val() == (unitId)
						|| $(".ship-TosUser option").eq(1)
								.val() == (unitId)
						|| $(".ship-TosUser option").eq(2)
								.val() == (unitId)
						|| $(".ship-TosUser option").eq(3)
								.val() == (unitId)
					    || $(".ship-TosUser option").eq(4)
				                .val() == (unitId)
						) {
						}else{
							$(".ship-TosUser option").eq(3).val(unitId).text(name);
						}
				 		$(".ship-TosUser").val(unitId);
				 		ACC.colorbox.close();


				 		return false;
					}

		});
		$('.ship-TosUser').on('click',function(){
			if($(".ship-TosUser").val() == "shipToopenPopupNew"){
				$(this).val('');
			}
		});

		$(document).on('change', 'body.page-my-company .ship-TosUser', function () {	

			var selecteduser = $(this).val();

				sessionStorage.setItem('selecteduserSession',selecteduser);
				 //previous = $(this).val();	

		/* }).on('change',function(e){*/

				var currentSelect = $(".ship-TosUser");


				var $form = $("#sortForm1");
				if (!ACC.paginationsort.downUpPressed)
				{
					var searchParam = $("#manager-user-voice").val();
					var viewpagination =$("#pageSizeManagerUser1").val();
					var shiptounit = $("#shipToSelected_user").val();
					var adminCheck = $('#admin_User').val();
					$form.find("input#search-shipto").val(searchParam);
					$form.find("input#shipToSelected_user").val(shiptounit);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#ViewPaginationManager").val(viewpagination);
					$form.find("input#admin_User").val(adminCheck);

				//if($(".ship-TosInvoice").val() != "shipToopenPopupNew"){

				//}												
				}


				if("shipToopenPopupNew" != $(".ship-TosUser").val()){
					if (!ACC.paginationsort.downUpPressed){
						$form.submit();
					}
					ACC.paginationsort.downUpPressed = false;
				}else if($(".ship-TosUser").val() == "shipToopenPopupNew"){
					var adminFilter = $('#admin_User').val();
					removeShipToSortbySessionValue();
					var shipToPopupURL = ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/all-ship-to-popup/'+($("#unitUid").val()).trim() +'?searchParam=&filterAdmin='+adminFilter;  
					$.ajax({
				            url: shipToPopupURL,

				            method: "GET",		
				            success: function(searchPageData) {
				                ACC.colorbox.open('Search or Select a Ship-To', {
				                    html: searchPageData,
				                    width: '800px',
				                    className:"shipto-popup-mb invoice-shiptopopup",
				                    overlayClose: false,
				                    onComplete: function(e) {
				                    	sessionStorage.removeItem("selecteduserSession");
				                    	$(".ship-TosUser").val('shipToopenPopupNew');
				        				
				        				checkShipToPopUpUser();
				                    	$(document).on('click keyup', '#search-ship-to-popup-user', function(){
				                    		checkShipToPopUpUser();
				                    	});
				                    },
				                    onClosed: function() {
				                    	sessionStorage.setItem("selecteduserSession","All");
			        					location.reload(true);
			        					window.location.href= ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName&filterAdmin='+adminFilter;
			        				
				                    }
				                });
				            }
				        });
				}
		 });

		 $(document).on('change','body.page-my-company .sortSelect.js-page-sort', function () {	

				var $form = $("#sortForm1");
				if (!ACC.paginationsort.downUpPressed)
			{
					var searchParam = $("#manager-user-voice").val();
					var viewpagination =$("#pageSizeManagerUser1").val();
					var shiptounit = $("#shipToSelected_user").val();
					var adminCheck = $('#admin_User').val();
					$form.find("input#search-shipto").val(searchParam);
					$form.find("input#shipToSelected_user").val(shiptounit);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#ViewPaginationManager").val(viewpagination);
					$form.find("input#admin_User").val(adminCheck);

			//if($(".ship-TosInvoice").val() != "shipToopenPopupNew"){

			//}												


					ACC.paginationsort.downUpPressed = false;

		}
		});

		/*$(document).on('click','.responsive-table-cell a',function(){				
			sessionStorage.removeItem('selectedInvoiceSession');
		});*/



				$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-user a",function(e){

					var page='';
					previous = $(this).value;
								e.preventDefault();	
								if($(this).attr('rel') == "prev"){
									page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
									}
									else if($(this).attr('rel') == "next"){
									    page = $(this).parents("ul").find("li.active").text().split("(")[0]
									}
									else{
									page= $(this).text().trim()-1;
									}
								var adminFilter = $('#admin_User').val();
								var data = {
										searchParam : $("#search-ship-to-popup-user").val() == undefined ? "" : $("#search-ship-to-popup-user").val().trim(),
										viewtype : 'All',
										page : page,
										filterAdmin : adminFilter,

									}
								var shipToPopupURL =ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/all-ship-to-popup/'+($("#unitUid").val()).trim();  
								var currentSelect = $(".ship-TosUser");
								var poupClass = $("#cboxOverlay").prop("class");
									$.ajax({
										url : shipToPopupURL,
										method : "GET",
										data : data,
										success : function(searchPageData) {
											ACC.colorbox.open('Search or Select a Ship-To', {
												html : searchPageData,
												width : '800px',
												className : poupClass,
												overlayClose : false,
												onComplete:function(){
													$(".ship-TosUser").val('shipToopenPopupNew');
							        				
							        				retrieveShipToSortbySessionValue();
							        				checkShipToPopUpUser();
												},
												onClosed: function() {

													sessionStorage.setItem("selecteduserSession","All");
						        					location.reload(true);
						        					window.location.href= ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName&filterAdmin='+adminFilter;
						        				
							                    }
											});
										}
									});

						});


			$(document).on("blur", "#cboxWrapper #search-ship-to-popup-user",function(e) {
						 removeShipToSortbySessionValue();
						 var key = e.which;
					 	 previous = $(this).value;
					 	 var adminFilter = $('#admin_User').val();
								var data = {
										searchParam : $("#search-ship-to-popup-user").val() == undefined ? "" : $("#search-ship-to-popup-user").val().trim(),
										filterAdmin : adminFilter

									}
								var shipToPopupURL =ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/all-ship-to-popup/'+($("#unitUid").val()).trim()+'searchParam=';
								var currentSelect = $(".ship-TosUser");
								var poupClass = $("#cboxOverlay").prop("class");
									$.ajax({
										url : shipToPopupURL,
										method : "GET",
										data : data,
										success : function(searchPageData) {
											ACC.colorbox.open('Search or Select a Ship-To', {
												html : searchPageData,
												width : '800px',
												className : poupClass,
												overlayClose : false,
												onComplete:function(){
												 	$(".ship-TosUser").val('shipToopenPopupNew');
							        				
							        				checkShipToPopUpUser();
						                    		
												},
												onClosed: function() {

													sessionStorage.setItem("selecteduserSession","All");
						        					location.reload(true);
						        					window.location.href= ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName&filterAdmin='+adminFilter;
						        				

							                    }
											});
										}
									});

						});	






						$(document).on("change", "#cboxWrapper .shipToUserPagePopup #sortOptions1", function(e) {
							var sortItem = $("#cboxWrapper #sortOptions1").val();
                        ;
                        var adminFilter = $('#admin_User').val();
							var data = {
								searchParam : $("#search-ship-to-popup-user").val() == undefined ? "" : $("#search-ship-to-popup-user").val().trim(),
								viewtype : 'All',
								sort : sortItem,
								filterAdmin : adminFilter,
							}
							var shipToPopupURL =ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/all-ship-to-popup/'+($("#unitUid").val()).trim() ;
							var poupClass = $("#cboxOverlay").prop("class");
							var currentSelect = $(".ship-TosUser");
							$.ajax({
								url : shipToPopupURL,
								method : "GET",
								data : data,
								success : function(searchPageData) {
									ACC.colorbox.open('Search or Select a Ship-To', {
										html : searchPageData,
										width : '800px',
										className : $("#cboxOverlay").prop("class"),
										overlayClose : false,
										onComplete:function(){
										 	$(".ship-TosUser").val('shipToopenPopupNew');
					        				
					        				retrieveShipToSortbySessionValue();
					        				checkShipToPopUpUser();
										},
										onClosed: function() {

											sessionStorage.setItem("selecteduserSession","All");
				        					location.reload(true);
				        					window.location.href= ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName&filterAdmin='+adminFilter;
				        				
					                    }
									});
								}
							});

						});




	},
	shiptoPopupEwalletpage: function(){
		$(document).on('click','#cboxContent #ShipToeWallet_popup', function(){ 

			 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
			 var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
			if($(".invoice-shiptopopup").length > 0){
				if ($(".ship-ToseWallet option").eq(0).val() == (unitId)
						|| $(".ship-ToseWallet option").eq(1)
								.val() == (unitId)
						|| $(".ship-ToseWallet option").eq(2)
								.val() == (unitId)
						|| $(".ship-ToseWallet option").eq(3)
								.val() == (unitId)
					    || $(".ship-ToseWallet option").eq(4)
				                .val() == (unitId)
						) {
						}else{
							$(".ship-ToseWallet option").eq(3).val(unitId).text(name);
						}
				 		$(".ship-ToseWallet").val(unitId);
				 		ACC.colorbox.close();


				 		return false;
					}

		});
		$('.ship-ToseWallet').on('click',function(){
			if($(".ship-ToseWallet").val() == "shipToopenPopupNew"){
				$(this).val('');
			}
		});
		$(document).on('change', 'body.page-ewalletdetailspage .ship-ToseWallet', function () {	

			var selectedewallet = $(this).val();

				sessionStorage.setItem('selectedewalletSession',selectedewallet);
				

				var currentSelect = $(".ship-ToseWallet");


				var $form = $("#sortForm1");
				if (!ACC.paginationsort.downUpPressed)
				{
					var searchParam = $("#ewallet-page-card").val();
					var viewpagination =$("#pageSizeeWallet1").val();
					var shiptounit = $("#shipToSelected_eWallet").val();
					$form.find("input#search-shipto").val(searchParam);
					$form.find("input#shipToSelected_eWallet").val(shiptounit);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#ViewPaginationeWallet").val(viewpagination);
					

															
				}


				if("shipToopenPopupNew" != $(".ship-ToseWallet").val()){
					if (!ACC.paginationsort.downUpPressed){
						$form.submit();
					}
					ACC.paginationsort.downUpPressed = false;
				}else if($(".ship-ToseWallet").val() == "shipToopenPopupNew"){
						removeShipToSortbySessionValue();
						var searchParam = $("#search-ship-to-popup-ewallet").val() == undefined ? "" : $("#search-ship-to-popup-ewallet").val().trim()
					var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-eWallet-popup/'+($("#unitUid").val()).trim() +'?searchParam='+searchParam; 
					$.ajax({
				            url: shipToPopupURL,

				            method: "GET",		
				            success: function(searchPageData) {
				                ACC.colorbox.open('Search or Select a Ship-To', {
				                    html: searchPageData,
				                    width: '800px',
				                    className:"shipto-popup-mb invoice-shiptopopup",
				                    overlayClose: false,
				                    onComplete: function(e) {
				                    	sessionStorage.removeItem("selectedewalletSession");
				                    	$(".ship-ToseWallet").val('shipToopenPopupNew');
				                    	checkShipToPopUpEwallet();
				                    			$(document).on('click keyup', '#search-ship-to-popup-ewallet', function(){
						                    		checkShipToPopUpEwallet();
						                    	});
				        				
				                    },
				                    onClosed: function() {
				                    	sessionStorage.setItem('selectedewalletSession','All');
				        					location.reload(true);
				        					window.location.href= ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim()+'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName';
				                    }
				                });
				            }
				        });
				}
		 });
		$(document).on('change','body.page-ewalletdetailspage .sortSelect.js-page-sort', function () {	

			var $form = $("#sortForm1");
			if (!ACC.paginationsort.downUpPressed)
		{
				var searchParam = $("#ewallet-page-card").val();
				var viewpagination =$("#pageSizeeWallet1").val();
				var shiptounit = $("#shipToSelected_eWallet").val();
				$form.find("input#search-shipto").val(searchParam);
				$form.find("input#shipToSelected_eWallet").val(shiptounit);
				$form.find("input#search-ordernew").val(searchParam);
				$form.find("input#ViewPaginationeWallet").val(viewpagination);

													


				ACC.paginationsort.downUpPressed = false;

	}
	});
		$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-ewallet a",function(e){

			var page='';
			previous = $(this).value;
						e.preventDefault();	
						if($(this).attr('rel') == "prev"){
							page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
							}
							else if($(this).attr('rel') == "next"){
							    page = $(this).parents("ul").find("li.active").text().split("(")[0]
							}
							else{
							page= $(this).text().trim()-1;
							}
						var data = {
								searchParam : $("#search-ship-to-popup-ewallet").val() == undefined ? "" : $("#search-ship-to-popup-ewallet").val().trim(),
								viewtype : 'All',
								page : page,


							}

						
							
						 if($(".ship-ToseWallet").val() == "shipToopenPopupNew"){
						var shipToPopupURL =ACC.config.encodedContextPath + '/my-account/all-ship-to-eWallet-popup/'+($("#unitUid").val()).trim() ;
						var currentSelect = $(".ship-ToseWallet");
						var poupClass = $("#cboxOverlay").prop("class");
							$.ajax({
								url : shipToPopupURL,
								method : "GET",
								data : data,
								success : function(searchPageData) {
									ACC.colorbox.open('Search or Select a Ship-To', {
										html : searchPageData,
										width : '800px',
										className:"shipto-popup-mb invoice-shiptopopup",
										overlayClose : false,
										onComplete:function(){
											$(".ship-ToseWallet").val('shipToopenPopupNew');
											retrieveShipToSortbySessionValue();
					        				checkShipToPopUpEwallet();

										},
										onClosed: function() {
										
										sessionStorage.setItem('selectedewalletSession','All');
				        					location.reload(true);
				        					window.location.href= ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim()+'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName';					              
										      }
									});
								}
							});
						}
				});
		
		$(document).on("blur", "#cboxWrapper #search-ship-to-popup-ewallet",function(e) {
						 removeShipToSortbySessionValue();
		 	var key = e.which;
		 	 previous = $(this).value;

					var data = {
							searchParam : $("#search-ship-to-popup-ewallet").val() == undefined ? "" : $("#search-ship-to-popup-ewallet").val().trim()

						}
					var shipToPopupURL =ACC.config.encodedContextPath + '/my-account/all-ship-to-eWallet-popup/'+($("#unitUid").val()).trim() ;
					var currentSelect = $(".ship-ToseWallet");
					var poupClass = $("#cboxOverlay").prop("class");
						$.ajax({
							url : shipToPopupURL,
							method : "GET",
							data : data,
							success : function(searchPageData) {
								ACC.colorbox.open('Search or Select a Ship-To', {
									html : searchPageData,
									width : '800px',
									className : poupClass,
												overlayClose : false,
									onComplete:function(){
									 	$(".ship-ToseWallet").val('shipToopenPopupNew');
				        				
									 	checkShipToPopUpEwallet();


									},
									onClosed: function() {

									sessionStorage.setItem('selectedewalletSession','All');
				        					location.reload(true);
										
				        					window.location.href= ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim()+'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName';


				                    }
								});
							}
						});

			});	

		$(document).on("change", "#cboxWrapper .shipToUserPagePopup #sortOptions1", function(e) {
			var sortItem = $("#cboxWrapper #sortOptions1").val();
        ;
			var data = {
				searchParam : $("#search-ship-to-popup-ewallet").val() == undefined ? "" : $("#search-ship-to-popup-ewallet").val().trim(),
				viewtype : 'All',
				sort : sortItem,

			}
			var shipToPopupURL =ACC.config.encodedContextPath + '/my-account/all-ship-to-eWallet-popup/'+($("#unitUid").val()).trim() ;
			
			$.ajax({
				url : shipToPopupURL,
				method : "GET",
				data : data,
				success : function(searchPageData) {
					ACC.colorbox.open('Search or Select a Ship-To', {
						html : searchPageData,
						width : '800px',
						overlayClose : false,
						className : $("#cboxOverlay").prop("class"),
						onComplete:function(){
						 	$(".ship-ToseWallet").val('shipToopenPopupNew');
	        			

					        				retrieveShipToSortbySessionValue();
					        				checkShipToPopUpEwallet();
										},
										onClosed: function() {

											sessionStorage.setItem('selectedewalletSession','All');
				        					location.reload(true);
										
				        					window.location.href= ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim()+'?shiptounit=All&viewtype=All&searchParam=&pagesize=10&sort=byName';

	                    }
					});
				}
			});

		});

		
	},
	bindDeleteEwalletPopup: function()
	  {
		  $(document).on("click", "#ewallet-delete-card", function()	{
			  _satellite.track('deletecard');
			  $(this).addClass("edit-delete");
			  var vaultToken = $('#cboxLoadedContent #editVaultToken').val();
			  window.scrollTo(0,0);
	  		ACC.colorbox.open("", {
	  			    html: $("#ewallet-delete").html(),
	                width: '480px',
	               height:'auto',
	                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.eWalletDelete+"</span></h4>",
	                close:'<span class="glyphicon glyphicon-remove"></span>',
	                onComplete: function() {
	                	_AAData.popupPageName= ACC.config.deletecardPageName;
	                	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
        			 	try {
        				    	 _satellite.track('popupView');
        		        } catch (e) {}
	            		$("body").css("overflow-y", "hidden");
	            		$("#cboxLoadedContent #editVaultToken").val($(".edit-delete").closest(".responsive-table-ewallet").find("#vaultToken").val());
	            		$('#cboxWrapper #cboxClose, #cboxWrapper #cancel').on('click',function(){
							window.location.reload();
        				});
	            	},
	            	onClosed: function() {
	            		$('body').css("overflow-y","auto");
	            		
	            		window.location.reload();
	            		
	            		
	            	}	
	  		  });
			});
			
		  
			

	  },
	  bindDeleteMessageEwalletPopup: function()
	  {
		
		  $(document).on("click",  "#cboxLoadedContent #delete_confirmation_ewallet", function(e)
	    	        {
			 
			  
			  var vaultToken = $('#cboxLoadedContent #editVaultToken').val();
			 
	    	  e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath + "/my-account/delete-card",
	    	            method:"POST",
	    	            data: {"vaultToken":vaultToken},
	    	            success : function(response)
	    	            {
	    	            	if(response != null && response == "SUCCESS" ){
	    	            		_satellite.track('cardDeleted');
	    	            		 window.scrollTo(0,0);
	    	            		ACC.colorbox.open("", {
	    	    	  			    html: $("#ewallet-delete-succes").html(),
	    	    	                width: '550px',
	    	    	                top: 'auto',
	    	    	                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.eWalletDeleteMessage+".</span></h4>",
	    	    	                close:'<span class="glyphicon glyphicon-remove"></span>',
	    	    	                onComplete: function() {
	    	    	                	_AAData.popupPageName=ACC.config.carddeletedPageName;
	    	    	                	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
	                    			 	try {
	                    				    	 _satellite.track('popupView');
	                    		        } catch (e) {}
	    	    	                	$('#colorbox').addClass('Pop-up-carts-delete');
	    	    	                	$('#cboxLoadedContent .close_window').click(function(){
    	          	            			window.location.reload();
    	          	                		
    	          	                		});
	    	    	            		$("body").css("overflow-y", "hidden");
	    	    	            		
	    	    	            	},
	    	            		onClosed: function() {
	    	    	          	  	window.location.reload();
	    	    	          	  	}
	    	            		});
	    	            		
	    	            	}
	    	            	else{
	    	            		
	    	          	  		ACC.colorbox.open("", {
	    	          	  			    html: $("#ewallet-delete-failure").html(),
	    	          	                width: '480px',
	    	          	              height:'auto',
	    	          	                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.eWalletTechnicalError+"</span></h4>",
	    	          	                close:'<span class="glyphicon glyphicon-remove"></span>',
	    	          	              onComplete: function() {
		    	    	                	$('#colorbox').addClass('Pop-up-delete');
		    	    	                	$('#cboxLoadedContent .close_window').click(function(){
	    	          	            			window.location.reload();
	    	          	                		
	    	          	                		});
		    	    	            		$("body").css("overflow-y", "hidden");
		    	    	            		
		    	    	            	},
	    	          	            	
	    	          	  	onClosed: function() {
	    	          	  	$('#colorbox').removeClass('Pop-up-delete');
	    	          	  	window.location.reload();
	    	          	  	}
	    	          	  	
	    	          	  		  });	    	            	    	            	
	    	            }
	    	            }
	    	        });
	    	            
	    	        });
		  

	  },
	  bindBoardCard:function(){
	      $(document).on("click", "#ewallet-add-credit-card", function(e)
	    	        {
	    	  e.preventDefault();
	    	  loading.start();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath + "/my-account/getBoardCardTransportKey",
	    	            method:"GET",
	    	            success : function(transportKey)
	    	            {
	    	            	_satellite.track('addNewcard');
	    	            	if(transportKey !=null){
	    	            		loading.stop();
	    	            var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey='+transportKey;
	    	            window.scrollTo(0,0);
	    	            ACC.colorbox.open("", {
	    	    			html: $("#iframe_Popup").html(),
	    	    			width: '550px',
	    	    		
	    	    			title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.eWalletAdd+"</span></h4>",
	    	    			close:'<span class="glyphicon glyphicon-remove"></span>',
	    	    			onComplete: function() {
	    	    				_AAData.popupPageName= ACC.config.addnewcardPageName;
	    	    				_AAData.popupChannel= ACC.config.myaccountpathingChannel;
                			 	try {
                				    	 _satellite.track('popupView');
                		        } catch (e) {}
	    	    			$('#colorbox').addClass('Pop-up-carts');
	    	    			$('#cboxLoadedContent').addClass('pop-up-contain');
	    	    			$('#cboxWrapper').addClass('pop-up-contain-value');
	    	    			
	    	    			$("body").css("overflow-y", "hidden");
	    	    			 $('#cboxContent #cboxClose').click(function(){
	    	    				  window.location.reload();
	    	    				  });
	    	    		
	    	    			},
	    	    			onClosed: function() {
	    	    			$('body').css("overflow-y","auto");
	    	    			$('#cboxLoadedContent').removeClass('pop-up-contain');
	    	    			$('#cboxWrapper').removeClass('pop-up-contain-value');
	    	    			$('#colorbox').removeClass('Pop-up-carts');
	    	    			window.location.reload();
	    	    		
	    	    			}
	    	    		});
	    	            $("#cboxLoadedContent ").find('.Pop-up-myIframe').attr('src', redirecturl );
	    	           
	    	            }
	    	            }
	    	        });
	    	            
	    	        });
	  },
	 
		
bindEditEwalletPop: function(){
		  
		  $(document).on("click", "#ewallet-edit-card", function() {
			  _satellite.track('editcard');
		  $(this).addClass("edit-cards");
		  
		  ACC.colorbox.open("", {
		  html: $("#ewallet-edit").html(),
		  width: '480px',
		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.EditCard+"</span></h4>",
		  close:'<span class="glyphicon glyphicon-remove"></span>',
		  onComplete: function() {
			  _AAData.popupPageName= ACC.config.editcardPageName;
			  _AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {}
			  $('#colorbox').addClass('Pop-up-edit-card-title');
		  $('#cboxLoadedContent').addClass('Pop-up-edit-card');
		  $('#cboxContent').addClass('Pop-up-edit-box');
		  $("#cboxLoadedContent .expirydate").hide();
		  $("#cboxLoadedContent .expirydatesame").hide();
		  $("#cboxLoadedContent .error_updated").hide();
		  
		  $("#cboxLoadedContent .expiry_year_size").hide();
		  $("#cboxLoadedContent #ewallet-edit-page-card").val($(".edit-cards").closest(".responsive-table-ewallet").find("#ewalletNickName").val());
		  $("#cboxLoadedContent #ewallet-card-expiration").val($(".edit-cards").closest(".responsive-table-ewallet").find("#ewalletExpe").val());
		  $("#cboxLoadedContent #editVaultToken").val($(".edit-cards").closest(".responsive-table-ewallet").find("#vaultToken").val());
		  
		  $("body").css("overflow-y", "hidden");
		  $('#cboxContent #cboxClose').click(function(){
		  window.location.reload();
		  });
		  
		  $("#cboxLoadedContent #ewallet-card-expiration").keyup(function(e){
			  if (e.keyCode != 8){
              if ($(this).val().length == 2){
                      $(this).val($(this).val() + "-");
              }
			  }   
             
         });
		  
		  $('#cboxLoadedContent #ewallet-card-expiration').keypress(function (e) {
			    var regex = new RegExp("^[0-9-]+$");
			    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
			    if (regex.test(str)) {
			        return true;
			    }

			    e.preventDefault();
			    return false;
			});

		
		  
		  },
		  onClosed: function() {
		  $('body').css("overflow-y","auto");
		  window.location.reload();
		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
		  $(this).removeClass("edit-cards");
		  }
		  });
		  });
	  },
	  
	  
	  bindEditSubmitEwallet: function()
	  {
		 
		  $(document).on("click",  "#cboxLoadedContent #edit_confirmation_ewallet", function(e){
			  var nickname = $('#cboxLoadedContent #ewallet-edit-page-card').val();
			  var vaultToken = $('#cboxLoadedContent #editVaultToken').val();
			  var expiryDate = $('#cboxLoadedContent #ewallet-card-expiration').val();
			  var expDate = expiryDate.split("-");
			  var expMonth = expDate[0];
			  var expYear = expDate[1];
			   if(expMonth <1  ||expMonth > 12){
			  $("#cboxLoadedContent .expiry_year_size").show();
			  $("#cboxLoadedContent .expirydate").hide();
			  $("#cboxLoadedContent .expirydatesame").hide();
			  $("#cboxLoadedContent .error_updated").hide();
			  $("#cboxLoadedContent .expiry_empty").hide();
			 
			  $('#cboxLoadedContent #ewallet-card-expiration').addClass('error-message-ewallet');
			  }else if(expYear.length > 2){
			  $("#cboxLoadedContent .expiry_year_size").show();
			  $("#cboxLoadedContent .expirydate").hide();
			  $("#cboxLoadedContent .expirydatesame").hide();
			  $("#cboxLoadedContent .error_updated").hide();
			  $("#cboxLoadedContent .expiry_empty").hide();
			
			  $('#cboxLoadedContent #ewallet-card-expiration').addClass('error-message-ewallet');
			  }
			  else{
			  loading.start();
			  e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath + "/my-account/editEwallet",
	    	            method:"POST",
	    	            data: {"vaultToken":vaultToken, "nickName":nickname,"expDate": expiryDate},
	    	            success : function(response)
	    	            {
	    	            	
	    	            	loading.stop();
	    	            	if(response != null && response == "SUCCESS"){
		    	            	_satellite.track('cardEdited');
	    	            		
	    	            		ACC.colorbox.open("", {
	     	    	  			    html: $("#ewallet-edit-success").html(),
	    	    	                width: '550px',
	    	    	               
	    	    	                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.EditCardMessage+".</span></h4>",
	    	    	                close:'<span class="glyphicon glyphicon-remove"></span>',
	    	    	                onComplete: function() {
	    	    	                	_AAData.popupPageName= ACC.config.cardeditedPageName;
	    	    	                	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
	                    			 	try {
	                    				    	 _satellite.track('popupView');
	                    		        } catch (e) {}
	    	    	                	 $('#cboxContent #cboxClose').click(function(){
	    	    	           			  window.location.reload();
	    	    	           			  });
	    	    	            		$("body").css("overflow-y", "hidden");
	    	    	            		
	    	    	            	},
	    	    	            	 onClosed: function() {
	    	    	            		 window.location.reload();
	    	    	            		 $('body').css("overflow-y","auto");
	    	    	            	 }
	    	    	            	
	    	            		});
	    	            		
	    	            	}else if(response != null && response == "EXPFAILURE"){
	    	            		
	    	           		  $("#cboxLoadedContent .error_updated").hide();
	    	           		  $("#cboxLoadedContent .expiry_empty").hide();
	    	           		  $("#cboxLoadedContent .expiry_year_size").hide();
	    	            		$("#cboxLoadedContent .expirydate").show();
	    	            		 $("#cboxLoadedContent .expirydatesame").hide();
	    	   				  $('#cboxLoadedContent #ewallet-card-expiration').addClass('error-message-ewallet');
	    	            	}else if(response != null &&( response == "SAME")){
	    	            		
		    	           		  $("#cboxLoadedContent .error_updated").hide();
		    	           		  $("#cboxLoadedContent .expiry_empty").hide();
		    	           		  $("#cboxLoadedContent .expiry_year_size").hide();
		    	            		$("#cboxLoadedContent .expirydate").hide();
		    	            		$("#cboxLoadedContent .expirydatesame").show();
		    	   				  $('#cboxLoadedContent #ewallet-card-expiration').addClass('error-message-ewallet');
		    	            	}else{
	    	            		
	    	           		  $("#cboxLoadedContent .error_updated").show();
	    	           		  $("#cboxLoadedContent .expiry_empty").hide();
	    	           		  $("#cboxLoadedContent .expiry_year_size").hide();
	    	            		$("#cboxLoadedContent .expirydate").hide();
	    	            		 $("#cboxLoadedContent .expirydatesame").hide();
	    	            	}
	    	            	
	    	            }
	    	   
	    	        });
			  }
	    	            
	    	        });
		  
	  },
	  bindNickNameCards:function(){
		  $(document).on("blur", "#cboxLoadedContent #card_holder_nickname", function(e)
	    	        { 
	    	  var nickName=$("#cboxLoadedContent #card_holder_nickname").val(); 
	      	  e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath +'/checkout/multi/siteone-payment/setSaveCardInSession?nickName='+nickName+'&saveCard=true',
	    	            method:"POST",
	    	            success : function()
	    	            {
	    	            	
	    	            }
	    	   
	    	        });
		      
	    	        });
	  },

	  bindEditEnabled:function(){
		  $(document).on("keyup", "#cboxLoadedContent #ewallet-card-expiration", function(e){
			  if($(this).val().length > 4){
		            $('#cboxLoadedContent #edit_confirmation_ewallet').attr('disabled', false);
		        }
		        else
		        {
		            $('#cboxLoadedContent #edit_confirmation_ewallet').attr('disabled', true);        
		        }
			    
			});
		  
		 
	    	        },

	    	        bindManageEwalletPopUp: function(){

	    	  		  $(document).on("click", "#ewallet-assgin", function() {
	    	  			  var assignRevokePopupURL = ACC.config.encodedContextPath + '/my-account/manageEwallet';
	    	  			  $(this).addClass("manage-cards");
	    	  			  var vaultToken = $(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val();
	    	  			  var unitId =  $('#unitIdVal').val();
	    	  			loading.start();
	    	  			  $.ajax({
	    	    	            url : assignRevokePopupURL,
	    	    	            method:"GET",
	    	    	            data: {"vaultToken":vaultToken,"unitId":unitId},
	    	  	            success : function(response)
	    	  	            {
	    	  	            	_satellite.track('assigneWalletPopup')
	    	  		  window.scrollTo(0,0);
	    	  		loading.stop();
	    	  		  ACC.colorbox.open("", {
	    	  		  html: response,
	    	  		  width: '480px',
	    	  		  height:'auto',
	    	  		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.manageCard+"</span></h4>",
	    	  		  close:'<span class="glyphicon glyphicon-remove"></span>',
	    	  		  onComplete: function() {
	    	  			_AAData.popupPageName= ACC.config.managecardpathingPageName;
	    	  			_AAData.popupChannel= ACC.config.myaccountpathingChannel;
        			 	try {
        				    	 _satellite.track('popupView');
        		        } catch (e) {}
	    	  		bindGrantRevokedData();
	    	  		  $("#cboxLoadedContent .grantAccessData").hide();
	    	  			$("#cboxLoadedContent .revokeAccessData").hide();
	    	  			  $('#cboxLoadedContent input:radio[name="radio-group"]').change(function() {
	    	  			        if ($(this).val() == 'Assign') {
	    	  			        	$('#cboxLoadedContent  .grant_revoke_box').prop('checked', false);
	    	  			        	bindGrantRevokeEnabledPagination();
	    	  			        	$('#cboxLoadedContent  #grantAccess').prop('checked', true);
	    	  			        	bindPaginationGrant();
	    	  		                
	    	  			        } else if($(this).val() == 'Revoke') {
	    	  			        	$("#cboxLoadedContent .grantAccessData").hide();
	    	  						$("#cboxLoadedContent .revokeAccessData").show();
	    	  						bindRevokeEnabledPagination();
	    	  						 $('#cboxLoadedContent  #grant_box').prop('checked', false);
	    	  						 bindPagination();
	    	  			        }
	    	  			    });
	    	  			  $("body").css("overflow-y", "hidden");
	    	  		  $('#cboxContent #cboxClose').click(function(){
	    	  		  window.location.reload();

	    	  		  });
	    	  		  },
	    	  		  onClosed: function() {
	    	  		  $('body').css("overflow-y","auto");
	    	  		  window.location.reload();
	    	  		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
	    	  		  $(this).removeClass("manage-cards");
	    	  		  }
	    	  		  });
	    	  	            }
	    	  			  });
	    	  			  });
	    	  	  },
	  
	  bindGrantRevokeEnabled:function(){
		  $(document).on("click", "#cboxLoadedContent #grant_box", function(e){
			  $("#cboxLoadedContent #grant_confirmation_ewallet").attr("disabled", !$("#grant_box:checked").length);
			    
			});
		  
		  $(document).on("click", "#cboxLoadedContent .grant_revoke_box", function(e){
			  $("#cboxLoadedContent #grant_confirmation_ewallet").attr("disabled", !$(".grant_revoke_box:checked").length);
			    
			});
		  
	    	        },
	  
	  bindManageEwalletPopUpConfirm: function(){
	  $(document).on("click", "#cboxLoadedContent #grant_confirmation_ewallet", function(e){
		  var vaultToken = $('#cboxLoadedContent #editVaultToken').val();
		  var nickname = [];
		  $.each($("input[name='grant']:checked"), function(){
	    	  nickname.push($(this).val());
	      });
	     var listOfCustIds=  nickname.join(",");
	     var grant_revoke = [];
		  $.each($('#cboxLoadedContent input:radio[name="radio-group"]:checked'), function(){
			  grant_revoke.push($(this).val());
	      });
	     var ot=  grant_revoke.join(", ");
		
		  var vaultToken = $('#cboxLoadedContent #editVaultToken').val();
		  
		  e.preventDefault();
		  loading.start();
		  $.ajax({
		  url :ACC.config.encodedContextPath +  "/my-account/assign-revoke-ewallet",
		  method:"POST",
		  data: {"listOfCustIds":listOfCustIds,"vaultToken":vaultToken,"operationType": ot},
		  success : function(response)
		  {
		  loading.stop();
		  if(response != null && response == "SUCCESS"){
			  
			  
			  ACC.colorbox.open("", {
			    html: $("#ewallet-revoke-grant-success").html(),
                width: '550px',
               
                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.ewalletAccessPrivilege+".</span></h4>",
                close:'<span class="glyphicon glyphicon-remove"></span>',
                onComplete: function() {
                	_AAData.popupPageName= ACC.config.privilegeupdatedpathingPageName;
			  		_AAData.popupChannel= ACC.config.myaccountpathingChannel;
    			 	try {
    				    	 _satellite.track('popupView');
    		        } catch (e) {}
                	 $('#cboxContent #cboxClose').click(function(){
           			  window.location.reload();
           			  });
            		$("body").css("overflow-y", "hidden");
            		
            	},
            	 onClosed: function() {
            		 window.location.reload();
            		 $('body').css("overflow-y","auto");
            	 }
            	
    		});
		  }
		  else if(response != null && response == "FAILURE")
			  ACC.colorbox.open("", {
				    html: $("#ewallet-revoke-grant-failure").html(),
	                width: '550px',
	               
	                title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.ewalletAccessFailure+".</span></h4>",
	                close:'<span class="glyphicon glyphicon-remove"></span>',
	                onComplete: function() {
	                	 $('#cboxContent #cboxClose').click(function(){
	           			  window.location.reload();
	           			  });
	            		$("body").css("overflow-y", "hidden");
	            		
	            	},
	            	 onClosed: function() {
	            		 window.location.reload();
	            		 $('body').css("overflow-y","auto");
	            	 }
	            	
	    		});
			  
			  }

		  });
		  });
	},
    specialties: [],	// available specialtis filters
    lastZipCode: 0,	//last zipcode searched
    lastMiles: 100,	//last miles searched
    homeBranchSelection: function () {	// showing Branch slection Page
		let zipCode = $("#zipcode").val();
        $("#branch-finder-zip").val(zipCode);
        $('.RTA_wrapper, #realtime_submit, .gb-footer-btn, .retailDetails, .businessDetails, .business-fill-icon, .branch-icon').addClass('hidden');
        $('.branchDetails, .branch-wrapper, .branch-selection-back, .branch-selection-next, .business-success-icon, .branch-fill-icon').removeClass('hidden');
        ACC.accountdashboard.updateStores("");
        ACC.accountdashboard.lastZipCode = zipCode;
    },
	showSpecialtiesOption: function () {	//Toggle specialties dropdown
        let target = $('.specialties-option');
        if (target.hasClass('showing')) {
            target.removeClass('showing').slideUp();
        }
        else {
            target.addClass('showing').slideDown();
        }
    },
    updateStores: function (e) {	//checking new branches
        var zipcode = $("#branch-finder-zip").val();
        var miles = $("#branch-finder-miles").val();
        if (ACC.formvalidation.validatezipcode(e, zipcode) && zipcode.length >= 5)	//zipcode !error
        {
            $(".branch-not-found, .specialties").addClass("hidden");
            $(".specialties-option").removeClass('showing').slideUp();
            $(".branch-result-zipcode").html(zipcode);
            $(".branch-result-miles").html(miles);
			ACC.formvalidation.removeErrorClassForRegForm('#branch-finder-zip', '#errorInZipcode', '#branch-finder-zip\\.errors', ACC.config.dashboardValidZip);
            if (ACC.accountdashboard.lastZipCode != zipcode || Number(miles) > ACC.accountdashboard.lastMiles) {	//getting new branches
                ACC.accountdashboard.lastZipCode = zipcode;
                ACC.accountdashboard.lastMiles = Number(miles);
                loading.start();
                $(".branch-card").remove();
                ACC.accountdashboard.getStoreData(zipcode, miles);
            }
            else if (ACC.accountdashboard.lastZipCode == zipcode && $(".branch-card").length) {	//applying filters
                ACC.accountdashboard.createFilters();
            }
			else{
				$(".branch-result-found, .branch-result-not-found, .branch-show-more, .branch-selected-text").addClass("hidden");
                $(".branch-not-found").removeClass("hidden");
				$("#storeid").val("");
				loading.stop();
			}
        }
        else {	//zipcode error
            ACC.formvalidation.addErrorClassForRegForm('#branch-finder-zip', '#errorInZipcode', '#branch-finder-zip\\.errors', ACC.config.dashboardValidZip);
        }
    },
    getStoreData: function (zipcode, miles) {	//geting new Store Data
        $.ajax({
            url: ACC.config.encodedContextPath + "/request-account/getStoreData", // "/store-finder",
            cache: false,
            data: { "zipcode": zipcode, "miles": miles },
            type: "get",
            success: function (response) {
                let data = response;
                let dataLength = data.length;
                let branchHTML = "";
                let specialtiesName = [ACC.config.agronomics, ACC.config.delivery, ACC.config.hardscape, ACC.config.irrigation, ACC.config.pestmanagement, ACC.config.nursery, ACC.config.outdoor];
                let specialtiesTags = ['Agronomics', 'Delivery', 'Hardscapes', 'Irrigation', 'Pesticide', 'Nursery', 'Lighting'];
                if (dataLength) {	//found some stores
                    for (let i = 0; i < dataLength; i++) {
                        let specialtyHTML = "";
                        let specialtyArray = [];
						let distance=(data[i].formattedDistance)? data[i].formattedDistance.split(" ")[0] : 0;
                        if (data[i].storeSpecialityDetails) {
                            for (let j = 0; j < specialtiesTags.length; j++) {	//tagging specialites
                                let specialtyText = specialtiesName[j];
                                let specialtyTag = specialtiesTags[j];
                                let l = -1;
                                for (let k = 0; k < data[i].storeSpecialityDetails.length; k++) {
                                    if (data[i].storeSpecialityDetails[k].code.indexOf(specialtyTag) != -1) {
                                        l = k;
                                        break;
                                    }
                                }
                                if (l != -1) {
                                    specialtyArray.push(specialtyTag);
                                    specialtyHTML += '<div class="col-md-4 col-xs-6 padding0 p-r-15 bold-text f-s-12 flex-center specialty"><img class="m-r-15" alt="' + specialtyText + '" title="' + specialtyText + '" src="/_ui/responsive/theme-lambda/images/branchspecialty_' + specialtyTag + '.png"/><span>' + specialtyText + '</span></div>';
                                }
                            }
                        }
                        branchHTML += '<div class="row margin0 marginBottom10 padding-20 text-default transition-3s branch-card hidden" data-filter="' + specialtyArray.join("~") + '" data-storeid="' + data[i].storeId + '" data-branch="' + data[i].name + '" data-distance="' + distance + '" onclick="ACC.accountdashboard.makeMyBranch(\'' + data[i].storeId + '\')"><div class="branch-selected-icon"><img alt="branch selected" title="branch selected" src="/_ui/responsive/theme-lambda/images/branchselected.png"/></div><div class="col-md-7 col-xs-12 padding0 m-b-15 font-size-14"><p class="bold-text f-s-18 m-b-5">' + data[i].name + '</p><p class="bold-text m-b-5">' + data[i].title + '</p><p class=" m-b-0">' + data[i].address.line1 + ((data[i].address.line2)? ', ' + data[i].address.line2 :  '') + '</p><p class="m-b-0">' + data[i].address.town + ' ' + data[i].address.region.isocodeShort + ' ' + data[i].address.postalCode + '</p><p class=" m-b-0">' + data[i].address.phone + '</p></div><div class="col-md-5 col-xs-12 p-r-0 text-right branch-info-extra"><div class="row"><div class="col-md-12 col-xs-6 p-l-0"><button class="btn bold-text font-size-14 selected-branch-txt">' + ACC.config.selectedHomeBranch + '</button><button data-idx="" class="btn bg-white bold-text  font-size-14 make-branch-link">' + ACC.config.makeMyHomeBranch + '</button></div><div class="col-md-12 col-xs-6 text-right branch-miles">' + data[i].formattedDistance + '</div></div></div><div class="cl"></div>' + specialtyHTML + '</div>';
                    }
                    $(".branch-wrapper .branch-results").append(branchHTML);
                    ACC.accountdashboard.createFilters();	//checking filters
                }
                else {	//no store found
                    $(".branch-result-found, .branch-result-not-found, .branch-show-more, .branch-selected-text").addClass("hidden");
                    $(".branch-not-found").removeClass("hidden");
					$("#storeid").val("");
                }
				window.scrollTo({ top: $(".branchDetails").offset().top, left: 0, behavior: 'smooth' });
                loading.stop();
            }
        }).always(function () { loading.stop(); });
    },
    createFilters: function () {	// creating filter button and clear button
        let specialtiesHTML = "";
        ACC.accountdashboard.specialties = [];
        $('.choose-specialty').each(function () {
            let target = $(this);
            let selectedoption = target.parents(".specialty-name").find("img").attr("title");
            let specialtyIcon = target.parents(".specialty-name").find("img").attr("src");
            let filter = target.prop("id");
            if (target.is(":checked")) {
                specialtiesHTML += '<div class="col-md-4 col-xs-6 marginBottom10 specialties-name"><span class="selected-specialties"> <img src=' + specialtyIcon + '><span class="font-size-14 font-small-xs">' + selectedoption + '</span><button class="btn bg-white pull-right" onclick="ACC.accountdashboard.removeFilter(this,\'' + filter + '\')" ><span class="delete-specialty"></span></button></span></div>';
                ACC.accountdashboard.specialties.push(filter);
            }
        });
        if (specialtiesHTML != "") {
            specialtiesHTML = '<button class="hidden-sm hidden-md hidden-lg col-xs-4 btn bg-white clear-specialties" onclick="ACC.accountdashboard.clearFilters()">' + ACC.config.clearAll + '</button>' + specialtiesHTML + '<button class="col-md-2 hidden-xs btn bg-white clear-specialties" onclick="ACC.accountdashboard.clearFilters()">' + ACC.config.clearAll + '</button>';
            $(".specialties").html(specialtiesHTML).removeClass("hidden");
        }
        ACC.accountdashboard.displayBranches();	// getting filterred Branches
    },
    removeFilter: function (e, id) {	// removing Branch filter button
        let target = $(e);
        target.parents(".specialties-name").remove();
        $("#" + id).prop("checked", false);
        if (!$(".specialties-name").length) {
            $(".clear-specialties").remove();
        }
        ACC.accountdashboard.specialties.splice(ACC.accountdashboard.specialties.indexOf(id), 1);
        ACC.accountdashboard.displayBranches();	// getting filterred Branches
    },
    clearFilters: function () {	// clearing all Branch filters
        $(".clear-specialties, .specialties-name").remove();
        $('.choose-specialty').prop("checked", false);
        ACC.accountdashboard.specialties = [];
        ACC.accountdashboard.displayBranches();	// getting all Branches
    },
    displayBranches: function () {	// showing filtered Branches
        let dataLength = 0;
		let specialtiesLength = ACC.accountdashboard.specialties.length;
		let filtrMiles = $("#branch-finder-miles").val();
		let branchResultFound;
        $(".branch-card, .branch-result-found, .branch-result-not-found, .branch-show-more, .branch-selected-text").addClass("hidden");
        $(".branch-card").each(function (index) {
            let target = $(this);
			let distance = Number(target.data("distance"));
			target.removeClass("branch-result-matched");
            if (specialtiesLength) {	// filter selected for Branches
				let filter = target.data("filter").split("~");
				let matchFound = true;
                for (let i = 0; i < specialtiesLength; i++) {	//checking && condition for specialties
                    if (filter.indexOf(ACC.accountdashboard.specialties[i]) == -1) {
                        matchFound = false;
                        break;
                    }
                }
                if (matchFound && distance <= filtrMiles) {	//Match Found
                    ACC.accountdashboard.selectingBranch(dataLength, target);	// selecting Home Branch
                    dataLength++;
                }
            }
            else if(distance <= filtrMiles) {	// no filter selected for Branches
                ACC.accountdashboard.selectingBranch(dataLength, target);
                dataLength++;
            }
        });
        if (!dataLength) {	// no filtered Branches found
            for (let i = 0; i < $(".branch-card").length; i++) {
				let target = $(".branch-card").eq(i);
				let distance = Number(target.data("distance"));
				if(distance <= filtrMiles) {	// no filter selected for Branches
					ACC.accountdashboard.selectingBranch(dataLength, target);	// selecting Home Branch
					dataLength++;
					branchResultFound = true;
				}
            }
        }
		$(".branch-result-found, .branch-result-not-found, .branch-not-found").addClass("hidden");
		if (!dataLength) {	// showing no Branch
			$(".specialties").html("");
			$(".branch-show-more, .branch-selected-text").addClass("hidden");
			$(".branch-not-found").removeClass("hidden");
			$("#storeid").val("");
        }
		else if(branchResultFound){	// showing within selected miles Branches
			$(".branch-result-not-found").removeClass("hidden");
		}
        else {	// showing all the Branches
            $(".branch-result-found").removeClass("hidden");
        }
		loading.stop();
    },
    selectingBranch: function (dataLength, target) {	// selecting Home Branch
        if (dataLength < 5) {
            if (dataLength == 0) {
                ACC.accountdashboard.makeMyBranch(target.data("storeid"));
            }
            target.removeClass("hidden");
        }
        else {
            $(".branch-show-more").removeClass("hidden");
        }
		target.addClass("branch-result-matched");
        $(".branch-result-number").html(dataLength + 1);
    },
    makeMyBranch: function (branch) {	// setting new Home Branch
		let target = $("[data-storeid='" + branch + "']");
		$(".branch-result-branch").html(target.data("branch"));
		$(".branch-selected-text").removeClass("hidden");
        $(".branch-selected").removeClass("branch-selected");
        target.addClass("branch-selected");
		$("#storeid").val(branch);
    },
    showMoreBranches: function (e) {	// show more Branch button
        $(".branch-result-matched").removeClass("hidden");
        $(e).addClass("hidden");
    },
    nextCreateCustomer: function (e) {	// procedding to confirmation page
		$(e).removeAttr("onclick");
		loading.start();
        $('.branchDetails, .branch-wrapper, .branch-selection-back, .branch-selection-next, .branch-selected-text, .branch-fill-icon, .confirmation-icon').addClass('hidden');
		$('.branch-success-icon, .confirmation-fill-icon').removeClass('hidden');
		if ($("input:radio[id='typeOfCustomer1']").is(":checked")) {
			$('.verification-container, .verification-container-step1').removeClass('hidden');
		}
		if ($("input:radio[id='typeOfCustomer2']").is(":checked")) {
			$("input[name='enrollInPartnersProgram']").prop('checked', false);
		}
		$.ajax({
			url: ACC.config.encodedContextPath + "/request-account/createCustomer",
			method: "POST",
			data: $('#siteOneRequestAccountForm').serialize(),
			success: function (response) {
				$('.RTA_wrapper, .businessDetails, .retailDetails, .next_goback_wrapper').hide();
				$('#realtime_submit').removeClass('requestAccountFormSubmit');
				if (response == "SUCCESS") {
					if ($("input:radio[id='typeOfCustomer1']").is(":checked")) {    //contract success
						$('.verification-container-step1').addClass("hidden");
						$('.verification-container-step2').removeClass('hidden');
						setTimeout(function () {
							$('#multiNavigateHomePage2, .shop_siteone_wrap, .verification-container-step5').removeClass("hidden");
							$('#multiNavigateHomePage1, .verification-container').addClass("hidden");
						}, 7000);
					}
					else {	// homeowner success - active acc
						$('#multiNavigateHomePage2, .shop_siteone_wrap, .verification-container-step5').removeClass("hidden");
						$('#multiNavigateHomePage1').addClass("hidden");
					}
					ACC.formvalidation.analyticsDataSet(true);
				}
				else {	// Failure - homeowner and contractor
					$('.verification-container-step1').addClass("hidden");
					$('.verification-container, .verification-container-step3, .shop_siteone_wrap').removeClass("hidden");
					ACC.formvalidation.analyticsDataSet(false);
				}
				loading.stop();
			},
			error: function () {	// Error - homeowner and contractor
				$('.RTA_wrapper, .businessDetails, .retailDetails, .next_goback_wrapper').hide();
				$('.verification-container-step1').addClass("hidden");
				$('.verification-container, .verification-container-step3, .shop_siteone_wrap').removeClass("hidden");
				$('#realtime_submit').removeClass('requestAccountFormSubmit');
				ACC.formvalidation.analyticsDataSet(false);
				loading.stop();
			}
		});
    },
    backCreateCustomer: function () {
        $('.branchDetails, .branch-wrapper, .branch-selection-back, .branch-selection-next, .branch-selected-text').addClass('hidden');
        $('.RTA_wrapper, #realtime_submit, .gb-footer-btn, .retailDetails, .businessDetails').removeClass('hidden');
        $('.business-success-icon, .branch-fill-icon').addClass('hidden');
        $('.business-fill-icon, .branch-icon').removeClass('hidden');
		$("#storeid").val("");
		$("#branch-finder-miles").prop("selectedIndex",0);
		$(".choose-specialty").prop("checked", false);
    },

	bindAnayticDataOrderApproval:function(){
		try {
			if($(".page-order-approval-details").length > 0){
				_AAData.orderNumber=$(".orderapproval-number").text();
				_AAData.orderAmount=$(".orderapproval-amount").text().replace("$", "");
				_AAData.orderStatus=$(".orderapproval-status").text();
			}
		}catch(e){}
	},
	selfServeStatus: true,
    bindSelfServeForm: function () {
        let pageVal = $(".pagename").val();
        if (pageVal == "Account Request Online") {
            let pageName = "my account : validation";
            let pathingChannel = "my account";
            ACC.accountdashboard.selfServePageAnalytics(pageName, pathingChannel, pageName);
        }
        $(".selfServe-formsubmit").on("click", function (e) {
            e.preventDefault();
            loading.start();
            document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
            ACC.briteverify.reCaptcha(grecaptcha.getResponse());
            ACC.accountdashboard.selfServeStatus = true;
            let emailText = $("#selfServeEmail").val();
            let isEmailValid = ACC.formvalidation.validateEmailPattern(emailText);
            if (ACC.formvalidation.validateAccountNumber(e) == false) //account number check
            {
                ACC.accountdashboard.selfServeStatus = false;
            }
            if (isEmailValid) //email id check
            {
                ACC.formvalidation.removeErrorClassForRegForm('#selfServeEmail', '#errorEmailAddress', '#selfServeEmail\\.errors', ERRORMSG.invalid.email);
            }
            else {
                ACC.accountdashboard.selfServeStatus = false;
                ACC.formvalidation.addErrorClassForRegForm('#selfServeEmail', '#errorEmailAddress', '#selfServeEmail\\.errors', ERRORMSG.invalid.email);
            }
            $('*[id*=error]:visible').each(function () {
                if ($(this).html() != '') {
                    ACC.accountdashboard.selfServeStatus = false;
                }
            });
            if (ACC.accountdashboard.selfServeStatus) {
                ACC.accountdashboard.selfServeEventAnalytics('', 'Continue', 'my account : validation', 'linkClicks');
                ACC.formvalidation.validateEmailWithBriteVerifyOnBlur('#selfServeEmail', '#errorEmailAddress', '#selfServeEmail\\.errors', ERRORMSG.global.email, emailText, '#siteOneRequestOnlineAccessForm');
            }
            else {
                loading.stop();
            }
        });
    },
    selfServeFormSubmit: function (isEmailValid, input, error, errorText, msg, textstatus) {
        if (!isEmailValid) {	//BriteVerify email respose check
            ACC.briteverify.vaildateEmailFields(input, error, errorText, msg, textstatus);
            loading.stop();
        }
        else {
            $.ajax({
                url: ACC.config.encodedContextPath + "/request-account/onlineAccessResponse",
                method: "POST",
                data: $('#siteOneRequestOnlineAccessForm').serialize(),
                success: function (response) {
                    if (response) {
                        isEmailValid = (response == "SUCCESS") ? 1 : (response == "accountNumberNotExists") ? 2 : (response == "isEmailExist") ? 3 : 2;
                        $(".selfServe-form").addClass("hidden");
                        if (isEmailValid == 1 && ACC.global.wWidth < 774) {
                            $(".selfServe-home").removeClass("hidden");
                            $(".selfServe-siteone").addClass("hidden");
                        }
                        $(".selfServe-response-" + isEmailValid).removeClass("hidden");
                        let pageName = (isEmailValid == 1) ? "my account : validation success" : (isEmailValid == 2) ? "my account : error: no or invalid email" : (isEmailValid == 3) ? "my account : error : existing account w/online access" : "my account : error: no or invalid email";
                        let pathingChannel = "my account";
                        let trackId = "pageload";
                        ACC.accountdashboard.selfServePageAnalytics(pageName, pathingChannel, pageName, trackId);
                    }
                    loading.stop();
                },
                error: function () {	// Error
                    ACC.accountdashboard.selfServeEventAnalytics('', 'Something went wrong', 'my account : validation', 'linkClicks');
                    ACC.accountdashboard.error500Popup("Something went wrong!", "An unexpected error has occurred", "Retry", { pageName: "my account : validation - something went wrong", pathingChannel: "my account", pathingPageName: "my account : validation - something went wrong", trackId: "pageload" }, { linktype: "", linkName: "Retry", onClickPageName: "my account : validation - something went wrong", trackId: "linkClicks" }, { pageName: "my account : validation", pathingChannel: "my account", pathingPageName: "my account : validation", trackId: "pageload" });
                    loading.stop();
                }
            });
        }
    },
    backSelfServe: function () {
        loading.start();
        $(".selfServe-form").removeClass("hidden");
        $(".selfServe-response-1, .selfServe-response-2, .selfServe-response-3").addClass("hidden");
        grecaptcha.reset();
        loading.stop();
        ACC.accountdashboard.selfServePageAnalytics("my account : validation", "my account", "my account : validation", "pageload");
    },
    accountNumberHelp: function () {
        $.colorbox({
            html: "<img width='100%' src='/_ui/responsive/theme-lambda/images/tool-tip-image.png' />",
            maxWidth: "100%",
            width: "1226px",
            height: (ACC.global.wWidth < 774) ? "285px" : "745px",
            maxHeight: "100%",
            overflow: "auto",
            title: false,
            className: "accountNumberHelp",
            close: '<span class="icon-close"></span>'
        });
    },
    error500Popup: function (heading, text, btn, pageAnalytics, eventAnalytics, pageAnalytics2) {
        $.colorbox({
            html: '<div class="row"><div class="col-md-12 text-center"><svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 126 126" fill="#ccc" ><path d="M63,0a63,63,0,1,0,63,63A63,63,0,0,0,63,0Zm0,119.35A56.35,56.35,0,1,1,119.35,63,56.35,56.35,0,0,1,63,119.35Z"/><path d="M59.91,76.87,57.06,47.8V34.37H68.94V47.8L66.13,76.87ZM57.53,91.63v-11h11v11Z"/></svg><h3 class="bold">' + heading + '</h3><p>' + text + '</p><button class="btn btn-primary btn-block" onclick=\'ACC.colorbox.close();' + (pageAnalytics ? 'ACC.accountdashboard.selfServePageAnalytics("' + pageAnalytics.pageName + '", "' + pageAnalytics.pathingChannel + '", "' + pageAnalytics.pathingPageName + '", "' + pageAnalytics.trackId + '"); ' : '') + (eventAnalytics ? 'ACC.accountdashboard.selfServeEventAnalytics("' + eventAnalytics.linktype + '", "' + eventAnalytics.linkName + '", "' + eventAnalytics.onClickPageName + '", "' + eventAnalytics.trackId + '"); ' : '') + (pageAnalytics2 ? 'ACC.accountdashboard.selfServePageAnalytics("' + pageAnalytics2.pageName + '", "' + pageAnalytics2.pathingChannel + '", "' + pageAnalytics2.pathingPageName + '", "' + pageAnalytics2.trackId + '");' : '') + '\'>' + btn + '</button></div></div>',
            width: "340px",
            height: "235px",
            title: false,
            className: "accountNumberHelp",
            close: '<span class="icon-close"></span>'
        });
    },
    selfServeEventAnalytics: function (linktype, linkName, onClickPageName, trackId) {
        digitalData.eventData = {
            linktype: linktype,
            linkName: linkName,
            onClickPageName: onClickPageName
        }
        if (trackId) {
            try {
                _satellite.track(trackId);
            } catch (e) { }
        }
    },
    selfServePageAnalytics: function (pageName, pathingChannel, pathingPageName, trackId) {
        _AAData.page.pageName = pageName;
        _AAData.pathingChannel = pathingChannel;
        _AAData.pathingPageName = pathingPageName;
        if (trackId) {
            try {
                _satellite.track(trackId);
            } catch (e) { }
        }
    },
    openApprovalPOUpdateContainer: function(){
    	var poValue = $('.js-po-value').attr('data-value');
    	var mode = poValue.length ? 'saveDelete': 'save';
    	if(mode=="save"){
    		$(".js-po-container").addClass("col-md-6").removeClass("col-md-4");
    		$(".po-info").addClass("hidden");
    		$(".po-update-container").removeClass("hidden");
    		$(".js-po-cancel").removeClass("hidden");
    		$(".js-po-delete").addClass("hidden");
    		$(".po-input").val(poValue);
    	}
    	
    	if(mode=="saveDelete"){
    		$(".js-po-container").addClass("col-md-6").removeClass("col-md-4");
    		$(".po-info").addClass("hidden");
    		$(".po-update-container").removeClass("hidden");
    		$(".js-po-cancel").addClass("hidden");
    		$(".js-po-delete").removeClass("hidden");
    		$(".po-input").val(poValue);
    	}
    },
    validationMessagePOUpdate: function(mode, msg) {
    	if(mode == "show"){
    		$(".po-input").css({
				'border-color': "#EF8701",
				'background-color': "#FEF3E5"
			});
    		$(".js-po-err-msg").removeClass("hidden").text(msg);
    	}
    	
    	if(mode == "hide"){
    		$(".po-input").removeAttr("style");
    		$(".js-po-err-msg").addClass("hidden");
    	}
    },
    onchangePOUpdate: function(){
    	var poNumberRequired = $("#poNumberRequired").val();
    	var po = $('.po-input').val();
    	
    	if(poNumberRequired == "true" && po.length <1){
    		ACC.accountdashboard.validationMessagePOUpdate("show", $(".js-po-err-msg-1").text());
    	}else{
    		ACC.accountdashboard.validationMessagePOUpdate("hide");
    	}
    },
    cancelApprovalPOUpdate: function() {
    	$(".js-po-container").addClass("col-md-4").removeClass("col-md-5");
    	$(".po-info").removeClass("hidden");
    	$(".po-update-container").addClass("hidden");
    },
    saveApprovalPOUpdate: function(){
    	var poNumberRequired = $("#poNumberRequired").val();
    	var po = $('.po-input').val();
    	
    	if(poNumberRequired == "true" && po.length <1){
    		ACC.accountdashboard.validationMessagePOUpdate("show");
    		return;
    	}else{
    		ACC.accountdashboard.validationMessagePOUpdate("hide");
    	}
    	
    	var data = {
			orderNumber: $('.orderapproval-number').text(),
			PO: po,
			status: "update"
    	};
    	ACC.accountdashboard.ajaxPOUpdate(data, function(res){
    		if(res == true){
	    		ACC.accountdashboard.cancelApprovalPOUpdate();
	    		$('.js-po-value').text(po.length ? po : 'NA');
	    		$('.js-po-value').attr('data-value', po.length ? po : '');
	    		ACC.accountdashboard.validationMessagePOUpdate("hide");
    		}else {
    			ACC.accountdashboard.validationMessagePOUpdate("show", $(".js-po-err-msg-1").text());
    		}
    	})
    },
    deleteApprovalPOUpdate: function(){
    	var poNumberRequired = $("#poNumberRequired").val();
    	var po = $('.po-input').val();
    	
    	if(poNumberRequired == "true"){
    		ACC.accountdashboard.validationMessagePOUpdate("show", $(".js-po-err-msg-2").text());
    		return;
    	}else {
    		ACC.accountdashboard.validationMessagePOUpdate("hide");
    	}
    	
    	var data = {
			orderNumber: $('.orderapproval-number').text(),
			PO: po,
			status: "delete"
    	};
    	ACC.accountdashboard.ajaxPOUpdate(data, function(res){
    		if (res == true){
	    		ACC.accountdashboard.cancelApprovalPOUpdate();
	    		$('.js-po-value').text('NA');
	    		$('.js-po-value').attr('data-value','');
	    		ACC.accountdashboard.validationMessagePOUpdate("hide");
    		}else {
    			ACC.accountdashboard.validationMessagePOUpdate("show");
    		}
    	})
    },
    ajaxPOUpdate: function(data, successcb, errorcb) {
    	$.ajax({
            url: ACC.config.encodedContextPath + "/my-account/order/updatePO",
            method: "POST",
            data: data,
            success: function (response) {
                successcb(response);
            },
            error: function () {
            	errorcb();
            }
        });
    },
	showShiptoMsg: function() {
		if ($(".js-show-shipto-msg").data('has-child-units') === true) {
			$(".js-show-shipto-msg").removeClass("hidden");
		}
		else {
			$(".js-show-shipto-msg").addClass("hidden");
		}
	},
	showPartnersPoint: function() {
		
	    var duration = 2000,
	    easing = 'swing';
		$('.show-partnerscontent').click(
		    function(){
				var el = $(this).closest(".dashboard-card");
				var toggleMinHeight = el.attr('data-minheight');
				var toggleMaxHeight = el.attr('data-maxheight');
		        var curH = el.height();
		        var contnt = el.find(".content-container");
					
		        if (el.is(':animated')){
		            return false;
		        }
		        else if (curH <= toggleMaxHeight && curH >= 300) {
		            el.animate(
		                {
		                    'height' : toggleMinHeight
		                }, duration, easing,function(){
							$(this).find(".eyeopen").removeClass("hidden");
							$(this).find(".eyeclose").addClass("hidden");
						});
		                contnt.slideUp(duration);
		        }
		        else if (curH <= toggleMinHeight){
					contnt.removeClass("hidden");
		            el.animate(
		                {
		                    'height' : toggleMaxHeight
		                }, duration, easing,function(){
							$(this).find(".eyeclose").removeClass("hidden");
							$(this).find(".eyeopen").addClass("hidden");
						});
		                contnt.slideDown();
		        }
		    });
		
	},
	moveToHome: function() {
		sessionStorage.setItem('updateAccount', 'true');
		window.location.href = ACC.config.encodedContextPath;
	},
	orderPrintHeaderHeight: function(){
		let defaultHeightClass = 150; //"print-min-h-165-imp";
		var msgText = $.trim($(".order-height-adjust-grey").eq(3).children("p").html());
		let minHeight = 75;
		while(msgText.length > minHeight){
			minHeight += 30;
			defaultHeightClass += 30;
		}
		defaultHeightClass = (defaultHeightClass < 165) ? 165 : defaultHeightClass;
		$(".order-height-adjust-grey").addClass("print-min-h-" + defaultHeightClass + "-imp");
	}
};
function bindPaginationGrant(){
$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-ewallet a",function(e){
		
		if($('#cboxLoadedContent input:radio[name="radio-group"]:checked').val() == "Assign")
      {
		var page='';
		previous = $(this).value;
		
		if($(this).attr('rel') == "prev"){
		page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
		}
		else if($(this).attr('rel') == "next"){
		page = $(this).parents("ul").find("li.active").text().split("(")[0]
		}
		else{
		page= $(this).text().trim()-1;
		}
		var grant_revoke = [];
		$.each($('#cboxLoadedContent input:radio[name="radio-group"]:checked'), function(){
		grant_revoke.push($(this).val());
		});
		var ot= grant_revoke.join(", ");
		
		$(this).addClass("manage-cards");
		  var vaultToken = $(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val();
		  var unitId =  $('#unitIdVal').val();
		var data = {

		page : page,
		vaultToken:vaultToken,
		unitId:unitId,
		action:ot
		}
		var poupClass = $("#cboxOverlay").prop("class");
			var assignRevokePopupURL = ACC.config.encodedContextPath + '/my-account/assignRevoke-users';
		
		  $.ajax({
	            url : assignRevokePopupURL,
	            method:"GET",
	            Class:poupClass,
	            overlayClose : false,
	            data: data,
            success : function(response)
            {
            	 window.scrollTo(0,0);
  	  		  ACC.colorbox.open("", {
  	  		  html: response,
  	  		overlayClose : false,
  	  		  width: '480px',
  	  		  height:'auto',
  	  		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.manageCard+"</span></h4>",
  	  		  close:'<span class="glyphicon glyphicon-remove"></span>',
  	  		  onComplete: function() {
  	  		
  	  		 $('#cboxLoadedContent  #grant_box').prop('checked', true);
  	  		  bindGrantRevokedData();
  	  		  $("#cboxLoadedContent .grantAccessData").hide();
  	  			$("#cboxLoadedContent .revokeAccessData").hide();
  	  			$('#cboxLoadedContent  #grantAccess').prop('checked', true);
  	  			bindGrantRevokePagination();
  	  			$("#cboxLoadedContent .grantAccessData").show();
  	  			$('#cboxLoadedContent  #grant_box').prop('checked', false);
  	  			$("body").css("overflow-y", "hidden");
  	  		  $('#cboxContent #cboxClose').click(function(){
  	  		  window.location.reload();

  	  		  });
  	  		  },
  	  		  onClosed: function() {
  	  		  $('body').css("overflow-y","auto");
  	  		  window.location.reload();
  	  		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
  	  		  $(this).removeClass("manage-cards");
  	  		  }
  	  		  });
            }
		  });
	}
	
		})
		}
function bindPagination(){
$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-ewallet a",function(e){
	if($('#cboxLoadedContent input:radio[name="radio-group"]:checked').val() == "Revoke")
      {
		var page='';
		previous = $(this).value;
		
		if($(this).attr('rel') == "prev"){
		page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
		}
		else if($(this).attr('rel') == "next"){
		page = $(this).parents("ul").find("li.active").text().split("(")[0]
		}
		else{
		page= $(this).text().trim()-1;
		}
		var grant_revoke = [];
		$.each($('#cboxLoadedContent input:radio[name="radio-group"]:checked'), function(){
		grant_revoke.push($(this).val());
		});
		var ot= grant_revoke.join(", ");
		
		$(this).addClass("manage-cards");
		  var vaultToken = $(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val();
		  var unitId =  $('#unitIdVal').val();
		var data = {

		page : page,
		vaultToken:vaultToken,
		unitId:unitId,
		action:ot
		}
		
		var poupClass = $("#cboxOverlay").prop("class");
			var assignRevokePopupURL = ACC.config.encodedContextPath + '/my-account/assignRevoke-users';
		
		  $.ajax({
	            url : assignRevokePopupURL,
	            method:"GET",
	            Class:poupClass,
	            overlayClose : false,
	            data: data,
            success : function(response)
            {
            	 window.scrollTo(0,0);
  	  		  ACC.colorbox.open("", {
  	  		  html: response,
  	  		overlayClose : false,
  	  		  width: '480px',
  	  		  height:'auto',
  	  		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.manageCard+"</span></h4>",
  	  		  close:'<span class="glyphicon glyphicon-remove"></span>',
  	  		  onComplete: function() {
  	  		   $('#cboxLoadedContent  #grant_box').prop('checked', true);
  	  		   bindGrantRevokedData();
  	  		  $("#cboxLoadedContent .grantAccessData").hide();
  	  		  $('#cboxLoadedContent  #revokeAccess').prop('checked', true);
  	  		  $("#cboxLoadedContent .revokeAccessData").show();
  	  		  $('#cboxLoadedContent  #grant_box').prop('checked', false);
  	  		bindGrantRevokePagination();
  	  		$("body").css("overflow-y", "hidden");
  	  		  $('#cboxContent #cboxClose').click(function(){
  	  		  window.location.reload();
  	  		  });
  	  		  },
  	  		  onClosed: function() {
  	  		  $('body').css("overflow-y","auto");
  	  		  window.location.reload();
  	  		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
  	  		  $(this).removeClass("manage-cards");
  	  		  }
  	  		  });
            }
		  });
		  
		}
		})
		}
function bindGrantRevokePagination(){
$('#cboxLoadedContent input:radio[name="radio-group"]').change(function() {
        if ($(this).val() == 'Assign') {
        	$("#cboxLoadedContent .grantAccessData").show();
        	bindGrantRevokeEnabledPagination();
        	$('#cboxLoadedContent  #grantAccess').prop('checked', true);
        	bindPaginationGrant();
              
              $("#cboxLoadedContent .revokeAccessData").hide();
              $('#cboxLoadedContent  .grant_revoke_box').prop('checked', false);
        } else {
        	$("#cboxLoadedContent .grantAccessData").hide();
        	$('#cboxLoadedContent  #revokeAccess').prop('checked', true);
        	bindRevokeEnabledPagination();
			$("#cboxLoadedContent .revokeAccessData").show();
			 $('#cboxLoadedContent  #grant_box').prop('checked', false);
			 bindPagination();
			
        }
    });
}
function bindGrantRevokedData(){
$('#cboxLoadedContent').addClass('Pop-up-edit-card');
  $('#cboxContent').addClass('Pop-up-edit-box');

  $("#cboxLoadedContent #ewallet-manage-card-number").text($(".manage-cards").closest(".responsive-table-ewallet").find("#lastdigitalcard").val());
  $("#cboxLoadedContent #ewallet-manage-card-nick-name").text($(".manage-cards").closest(".responsive-table-ewallet").find("#ewalletNickName").val());
  $("#cboxLoadedContent #ewallet-manage-card-date").text($(".manage-cards").closest(".responsive-table-ewallet").find("#ewalletExpe").val());
  $("#cboxLoadedContent #ewallet-manage-card-name").text($(".manage-cards").closest(".responsive-table-ewallet").find("#ewalletName").val());
  $("#cboxLoadedContent #ewallet-manage-card-type").text($(".manage-cards").closest(".responsive-table-ewallet").find("#ewalletCardType").val());
  $("#cboxLoadedContent #editVaultToken").val($(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val());
}
function bindGrantRevokeEnabledPagination(){
  	var assignRevokePopupURL = ACC.config.encodedContextPath + '/my-account/assignRevoke-users';
		  $(this).addClass("manage-cards");
		  var vaultToken = $(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val();
		  var unitId =  $('#unitIdVal').val();
		var grant_revoke = [];
		$.each($('#cboxLoadedContent input:radio[name="radio-group"]:checked'), function(){
		grant_revoke.push($(this).val());
		});
		var ot= grant_revoke.join(", ");
		
		var data = {
		vaultToken:vaultToken,
		unitId:unitId,
		action:ot

		}
		 $.ajax({
            url : assignRevokePopupURL,
            method:"GET",
            data: data,
          success : function(response)
          {
          	 window.scrollTo(0,0);
	  		  ACC.colorbox.open("", {
	  		  html: response,
	  		  width: '480px',
	  		  height:'auto',
	  		overlayClose : false,
	  		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.manageCard+"</span></h4>",
	  		  close:'<span class="glyphicon glyphicon-remove"></span>',
	  		  onComplete: function() {
	  		bindGrantRevokedData();
	  		  $("#cboxLoadedContent .grantAccessData").hide();
	  			$("#cboxLoadedContent .revokeAccessData").hide();
	  			$('#cboxLoadedContent  #grantAccess').prop('checked', true);
	  			$("#cboxLoadedContent .grantAccessData").show();
	  			$('#cboxLoadedContent  #grant_box').prop('checked', false);
	  			bindGrantRevokePagination();
	  		$("body").css("overflow-y", "hidden");
	  		  $('#cboxContent #cboxClose').click(function(){
	  		  window.location.reload();

	  		  });
	  		  },
	  		  onClosed: function() {
	  		  $('body').css("overflow-y","auto");
	  		  window.location.reload();
	  		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
	  		  $(this).removeClass("manage-cards");
	  		  }
	  		  });
          }
		  });
}

function bindRevokeEnabledPagination(){
  	var assignRevokePopupURL = ACC.config.encodedContextPath + '/my-account/assignRevoke-users';
		  $(this).addClass("manage-cards");
		  var vaultToken = $(".manage-cards").closest(".responsive-table-ewallet").find("#vaultToken").val();
		  var unitId =  $('#unitIdVal').val();
		var grant_revoke = [];
		$.each($('#cboxLoadedContent input:radio[name="radio-group"]:checked'), function(){
		grant_revoke.push($(this).val());
		});
		var ot= grant_revoke.join(", ");
		
		var data = {
		vaultToken:vaultToken,
		unitId:unitId,
		action:ot

		}
		  $.ajax({
            url : assignRevokePopupURL,
            method:"GET",
            data: data,
          success : function(response)
          {
          	 window.scrollTo(0,0);
	  		  ACC.colorbox.open("", {
	  		  html: response,
	  		  width: '480px',
	  		  height:'auto',
	  		overlayClose : false,
	  		  title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.manageCard+"</span></h4>",
	  		  close:'<span class="glyphicon glyphicon-remove"></span>',
	  		  onComplete: function() {
	  		
	  		 $('#cboxLoadedContent  #grant_box').prop('checked', true);
	  		bindGrantRevokedData();
	  		  $("#cboxLoadedContent .grantAccessData").hide();
	  			$('#cboxLoadedContent  #revokeAccess').prop('checked', true);
	  			$('#cboxLoadedContent  #grant_box').prop('checked', false);
	  			bindGrantRevokePagination();
	  			$("body").css("overflow-y", "hidden");
	  		  $('#cboxContent #cboxClose').click(function(){
	  		  window.location.reload();

	  		  });
	  		  },
	  		  onClosed: function() {
	  		  $('body').css("overflow-y","auto");
	  		  window.location.reload();
	  		  $('#cboxLoadedContent').removeClass('Pop-up-edit-card');
	  		  $(this).removeClass("manage-cards");
	  		  }
	  		  });
          }
		  });
}


function checkShipToPage(){
	var searchShipToVal = $('#search-ship-to').val();
	disableShipToSearch(searchShipToVal);
}

function checkShipToPopUp(){
	var searchShipToPopupVal = $('#search-ship-to-popup').val();
	disableShipToSearch(searchShipToPopupVal);
}

function checkShipToPopUpUser(){
	var searchShipToPopupUserVal = $('#search-ship-to-popup-user').val();
	disableShipToSearch(searchShipToPopupUserVal);
}

function checkShipToPopUpEwallet(){
	var searchShipToPopupEwallet = $('#search-ship-to-popup-ewallet').val();
	disableShipToSearch(searchShipToPopupEwallet);
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

$(document).ready(function (){
	
	var emailPromoSubscribe = $("#emailSubscribe").val();
	
	if(emailPromoSubscribe=='Yes')
		{
		$("#emailOptIn").attr('checked', true);
		}
	else
		{
		$("#emailOptIn").attr('checked', false);
		}
});

$("#emailOptIn").bind("change",function(){
	var type='POST'
	var url;
    if($(this).is(":checked"))
        url= ACC.config.encodedContextPath +"/my-account/updateEmailSubscribe?emailSubscribe=Yes";
    else
    	url= ACC.config.encodedContextPath +"/my-account/updateEmailSubscribe?emailSubscribe=No";
    
    
    $.ajax({
        type: type,
        /*contentType : 'application/json; charset=utf-8',*/ //use Default contentType
        dataType : 'json',
       
      
        url:url ,
        //data: JSON.stringify(search),
       // Note it is important without stringifying
        success :function(result) {
        // do what ever you want with data
        
        if (result)
        	{
        	 //console.log("Success");
        	}
        }
        });
});


$(document).ready(function() {
	$( "#requsetTooltip" ).tooltip({ content: '<img width="500px" height="500px" src="/_ui/responsive/theme-lambda/images/tool-tip-image.png" />' })
	});


$('.role-box').on('click',function  () {
	var role_code=$(this).find("#role_code").val();
	$(this).find('#Role_'+role_code).siblings(".colored-radio").find("#Roled_"+role_code).prop('checked', true);
	   /*$(this).find('#Roled_'+role_code).siblings(".colored-radio").find("#Role_"+role_code).prop('checked', true);*/
	   //$(this).find("#Roled_b2badmingroup").siblings(".colored-radio").find("#Role_b2badmingroup").prop('checked', true);
	if ($("#Role_b2badmingroup, #Roled_b2badmingroup").is(':checked')) {
		$(".team-role").hide('slow');
	} else {
		$(".team-role").show("slow");
	}
	   
	});

$( document ).ready(function() {
 	var emailTopicPreference=[];
 	var list = $("#promo").val();
 
 	if(list=='Yes')
 		{
 		$("#subscribeEmail").attr('checked', true);
 		}
 	else
 	{
 		$("#unSubscribeEmail").attr('checked', true);
 	}
 	
 	var orderVal = $("#promo1").val();
 	if (orderVal =='Yes') {
 		$("#emailOrderOption").attr('checked', true);
 	} 
 	
 	$("#unSubscribeEmail").on('click',function () {
 		$(".ads_Checkbox").each(function() {
 			$(this).attr('checked', false);
 		});
 	});

$(".ads_Checkbox").on('click',function () {	
	$("#unSubscribeEmail").attr('checked', false);
	});
 	
 	emailTopicPreference = $('#preference').val();
 	
 	if (emailTopicPreference != undefined) {
 	
 	var emailPref=emailTopicPreference.split(','); 		
 	
 		for ( var i = 0, l = emailPref.length; i < l; i++ ) {
 		$(".ads_Checkbox").each(function() {
 			var idvalue=$(this).val();
 			var emailval=emailPref[i].replace('[','').replace(']','').replace('"','').replace('"','');
 			
 			if(idvalue== emailval)
 				{
 				$(this).attr('checked', true);
 				}
 			
 	});
 			
 	
 		
 	}
 	}
 	
 	$("input[name='subsEmail']").on('click',function (){
 		if (list == 'New') {
 			list = $("input[name='subsEmail']:checked").val();
 		}
 		$(".emailsubscribe").hide();
 	});
 	
 	
 	
 	$("#savePref").on('click',function (){
 		var unsubs = $("#unSubscribeEmail:checked").val();
 		var subs = $("#subscribeEmail:checked").val();
 		
 		if (unsubs == undefined && subs == undefined) {
 			$(".emailsubscribe").show();
 		} else {
 		
 	    
 	    var emailPromo;
 	    var emailTopicPreference="";
 	    var orderOption =  $("#emailOrderOption:checked").val() 
 	 
 	    if (orderOption=='on'){
 	    	orderOption='Yes';
 	    } else{
 	    	orderOption='No';
 	    }
 	  
 	    	emailPromo = $("input[name='subsEmail']:checked").val();
 	    	
 	 
 	    
     $('.ads_Checkbox:checked').each(function(){       
     	
 	        var values = $(this).val();
 	   
        if( typeof values !='undefined' && values !="" ){
         emailTopicPreference += values+"|";	
 	        
 	       }
 	    });
     
 	    	
 	    	
 	    var search = {}
 		search["emailType"] = emailPromo;
 		search["emailTopicPreference"] = emailTopicPreference;
 	     var type="POST";
 	  var url= ACC.config.encodedContextPath +"/my-account/updatePrefernce";
 	   //console.log(url);
 	    $.ajax({
 	        type: type,
 	        dataType : 'json',
         data: "emailType=" + emailPromo + "&emailTopicPreference=" + encodeURIComponent(emailTopicPreference)+"&emailPromo="+orderOption,
 	        url:url ,
 	        success :function(result)
 	        {
 	            if (result)
 	        	{
             	//console.log("msg");
             	$('#success').html('<div class="margin40"><br/><span class="icon-success" style="margin-left:20px;"></span><p class="black-title col-md-5 col-xs-7" style="margin-top: 15px;"><b>'+ACC.config.success+'</b><br/>'+ACC.config.updatedPreferences+'</p><p class="cl"></p></div>');
 	        	  ewt.trackFormSubmit({name:'pref_center_form_submit',type:'email_preference',form:$('')});
         	}
         }
         });
 		}
 	});
 	
 	$(".printOrderDetails").on('click',function  (e) {
 		e.preventDefault();
		ACC.accountdashboard.orderPrintHeaderHeight();
 		window.print();
 	});
	
 	$(function () { // wait for page to load.
 	    var primaryBusinessL2Dropdown = $("#contrPrimaryBusiness"),
 	        primaryBusinessL1Dropdown = $('<select id="contrPrimaryBusinessL1"></select>') // create a PrimaryBusinessL1 dropdown
 	    
 	    // parse the nested dropdown
 	   primaryBusinessL2Dropdown.children().each(function () {
 	        var group = $(this),
 	            primaryBusinessL1Name = group.attr('label'),
 	            primaryBusinessL1code = group.attr('value'),
 	            option;
 	        
 	        // create an option for the PrimaryBusinessL1
 	        option = $('<option></option>').val(primaryBusinessL1code).text(primaryBusinessL1Name);
 	        
 	        // store the associated PrimaryBusinessL2 options
 	        option.data('contrPrimaryBusiness', group.children());
 	        
 	       // add the PrimaryBusinessL1 to the dropdown
 	       primaryBusinessL1Dropdown.append(option);
 	    });
 	    
 	    // add the PrimaryBusinessL1 dropdown to the page
		$(".business-dropdown .business-dropdown-primary").append(primaryBusinessL1Dropdown);
 	    
 	    // this function updates the PrimaryBusinessL2 dropdown based on the selected PrimaryBusinessL1
 	    function updatePrimaryBusinessL2() {
 	    	//alert("caleed");
 	        var primaryBusinessL1 = primaryBusinessL1Dropdown.find(':selected');
 	        primaryBusinessL2Dropdown.empty().append(primaryBusinessL1.data('contrPrimaryBusiness'));
 	        $('#contrPrimaryBusiness option[value="Select"]').prop("selected",true).change();
 	    }
 	    
 	    // call the function to set the initial cities
 	   updatePrimaryBusinessL2();
 	    
 	    // and add the change handler
 	  primaryBusinessL1Dropdown.on('change', updatePrimaryBusinessL2);
 	});
 	
 }); 


function retrieveShipToSortbySessionValue() {
	if(sessionStorage.getItem("selectedDropdownValueSession")){
		$("#cboxWrapper .sortSelectSpan").val(sessionStorage.getItem("selectedDropdownValueSession"));
	}
}

function removeShipToSortbySessionValue() {
	sessionStorage.removeItem("selectedDropdownValueSession");
}

$( document ).ready(function() {
	
	var succesAddEwallet=$("#add-ewallet-success").val();
	var errorAddEwallet=$("#add-ewallet-failure").val();
	var unitId = $('#unitIdVal').val();
	if(succesAddEwallet != null && succesAddEwallet != ""){
		_satellite.track('newCardAdded');
		 ACC.colorbox.open("", {
			   html: $(".add-success-card-ewallet").html(),
	       width: '480px',
	       height:'auto',
	       title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.eWalletAddMessage+"</span></h4>",
	       close:'<span class="glyphicon glyphicon-remove"></span>',
	       onComplete: function() {
	    	   	_AAData.popupPageName= ACC.config.newcardaddedPageName;
	    	   	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {}
	    	   $('#cboxContent #cboxClose').click(function(){
        			  window.location.reload();
        			  });
	    		$("body").css("overflow-y", "hidden");
	    		
	    	 
	    	},
	    	onClosed: function() {
	    		$('body').css("overflow-y","auto");
	    		window.location.href =ACC.config.encodedContextPath +"/my-account/ewallet/" +unitId;
	    	 
	    		}
			  });
	}

	if(errorAddEwallet != null && errorAddEwallet != ""){
		   ACC.colorbox.open("", {
			   html: $(".add-eroor-card-ewallet").html(),
	       width: '480px',
	       height:'auto',
	       
	       close:'<span class="glyphicon glyphicon-remove"></span>',
	       onComplete: function() {
	    	   $('#cboxContent #cboxClose').click(function(){
        			  window.location.reload();
        			  });
	    		$("body").css("overflow-y", "hidden");
	    		
	    	 
	    	},
	    	onClosed: function() {
	    		$('body').css("overflow-y","auto");
	    		window.location.href =ACC.config.encodedContextPath +"/my-account/ewallet/" +unitId;
	    	 
	    		}
			  });
	}
	
	
	
	});
function ewalletLoadContent(e) {
	var unitId = $('#unitIdVal').val();
	
	var oIframe = $("#cboxLoadedContent ").find("#myIframe")[0];
	
	 var oDoc = oIframe.contentWindow || oIframe.contentDocument;
	
	if (oDoc.document) {
		
	$('#colorbox').css('display', 'none');
	
	

	} 
	 loading.start();
	 window.location.href =ACC.config.encodedContextPath +"/my-account/ewallet/" +unitId; 
	}

	
