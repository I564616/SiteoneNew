ACC.global = {

    _autoload: [
        ["passwordStrength", $('.password-strength').length > 0],
        "globalHeaderInitial",
        "bindToggleOffcanvas",
        "bindToggleXsSearch",
        "bindHoverIntentMainNavigation",
        "initImager",
        "backToHome",
        "bindHeaderWelcomeOverlay",
        "playVideo",
        ["countrySelection",  $('#User_Agent').val()!="SiteOneEcomApp"],
        "verticalNavigation",
        "bindShipToSelection",
        "disableImageRightClick",
        "pdfClicks",
        "successPopupbinding",
        "showToolTip",
        "disableClickToCall",
        "bindInvoiceCalendar",
        "bindcurrentdate",
        "bindInvoice",
        "bindpreviousdate",
        "bindCalendarCurrentdate",
        "bindCalendarpreviousdate",
        "bindInvoiceFormData",
        "bindShipToFormData",
        "getinvoicelastdate",
        "rememberMe",
               "rememberMePopup",
        "signInOverlay",
        "carouselClickEvent",
        "carouselClickEventPDP",
        "handleFlyoutIpad",
        "bindManagerUser",
        "userCheckboxMethods",
        "ndspage",
        "galleryboxpopup",
        "signInoverlayOpen",
        "getUserFirstName",
         "resetShiptoDropDown",
         "signInoverlayOpen",
		 "openMediaInPopup",
 	    "bindEwallet",
        "footerCurrentYear",
	    "analyticsPageIndentifier",
	    "analyticProductFindingMethod",
	    "analyticsExternalReferrer",
	    "analyticSearchEmpty",
	    "analyticProductRecommendation",
		"hardScapeDatePicker",
		"signInOverlay1",
		"hidecspSignInOverlay",
		"resetSortOnCategoryselect",
		"hardscapePLPLink",
		"hardscapePDPLink",
		"analytichardscapes",
        "analyticCuratedplp",
		"showRecommendedProduct",
		"passwordShowHideBtn",
		"passwordShowHideBtnOnCreatePasswordPage",
        "showContent",
        "showContent",
        "showPdpSticky",
        "pdpimageGalleryCarousel",
        "uomDropdowninventoryId",
        "bindLinkToPayDetails",
        "bindLinkToPayConfirmation",
        "bindsetactivetab",
        "hardscapeProductsAlertMsg",
        "analyticsProjectCalculator",
        "checkConsent",
        "pageIdSetforOrder",
        "getAllCategory",
        "addOgTags",
		"plpMobileViewResponse",
		["bindNumberInputEvent", $('.js-number-input-event-bind').length],
        ["bindScriptInputEvent", $('.js-script-input-event-bind').length]
    ],
    productAnalyticsRecAtc: false,
    productCutatedplp: false,
    productCutatedplpLoginToBuy: false,
    productCutatedplpList: false,
    productCutatedplpLink: false,
    productCutatedplpPagination: false,
    CutatedplpName: " ",
    curatedplpLinkName: " ",
    wWidth: $(window).width(),
    wHeight: $(window)[0].innerHeight,
    globalHeaderHeight: 0,
    windowScrollTop: $(window).scrollTop(),
    toastCounter: 1,
    toastShow: function (type, text, animateClasses) {
        if(!$(".toast-main-section").length){
            $("body").append('<section class="toast-main-section animated transition-3s"></section>');
        }
        $(".toast-main-section").append('<div type="'+ type +'" id="js-toast-'+ ACC.global.toastCounter +'" data-toastcounter="'+ ACC.global.toastCounter +'" data-text1="'+ text[0] +'" data-text2="'+ text[1] +'" data-class1="'+ animateClasses[0] +'" data-class2="'+ animateClasses[1] +'" class="row animated-3s '+ animateClasses[0] +' bg-dark-black text-white border-radius-3 flex-center p-y-20 position-relative m-b-10 toast-parent"><div class="col-md-3 sending-circle-loading hidden"><span></span></div><div class="col-md-3 f-s-8 sending-circle-confirm text-center"><span class="glyphicon glyphicon-ok"></span><p class="bg-dark-black transition-10s"></p></div><div class="col-md-9 f-s-12 l-h-14 p-l-35 text-uppercase ajax-text-success">' + text[0] + '</div><button class="close f-s-12 f-w-400 p-a-10 p-x-10 text-white" onclick="ACC.global.toastClose(this)">x</button></div>');
        return ACC.global.toastCounter++;
    },
    toastActions: function (target, num, type) {
        if(num == 1){
            target.find(".sending-circle-loading").addClass("hidden");
            target.find(".sending-circle-confirm").removeClass("hidden");
        }
        else if (num == 2){
            target.find(".sending-circle-confirm p").addClass("ajax-confirmed");
            target.find(".ajax-text-" + type).html(target.data("text2"));
        }
        else if (num == 3){
            target.removeClass(target.data("class1")).addClass(target.data("class2")).fadeOut();
        }
        else if (num == 4){
            target.fadeOut();
        }
    },
    toastClose: function (e) {
        $(e).parent('.toast-parent').fadeOut();
    },
    bindScriptInputEvent: function () {
        $(".js-script-input-event-bind").each(function () {
            $(this).on({ "keydown": ACC.global.scriptInputKeyDown, "keyup": ACC.global.scriptInputKeyUp, "paste": ACC.global.scriptInputPaste, "blur": ACC.global.scriptInputBlur });
        });
    },
    scriptInputKeyDown: function (pEvent) { // Remove disallowed characters (like HTML/JS code symbols)
        let refVal = pEvent.key ? pEvent.key.replace(/[<>{}\/\\;:`]/g, "") : "";
        if (refVal == "") {
            pEvent.preventDefault();
        }
    },
    scriptInputKeyUp: function (pEvent) {
        let ref = $(pEvent.currentTarget);
        ref.prop("value", ref.prop("value").replace(/[<>{}\/\\;:`]/g, " "));
    },
    scriptInputPaste: function (pEvent) {
        let ref = $(pEvent.currentTarget);
        ref.prop("value", ref.prop("value").replace(/[<>{}\/\\;:`\n]/g, " ").replace(/[\^|~]/g, "-").trim());
    },
    scriptInputBlur: function (pEvent) {
        let ref = $(pEvent.currentTarget);
        let refVal = ref.prop("value").replace(/[<>{}\/\\;:`\n]/g, " ").replace(/\^|~/g, "-").trim().replace(/\t/g, '');
        let newrefval = "";
        for (let i = 0; i < refVal.length; i++) {
            if(i < refVal.length -1 && refVal[i] == " " && refVal[i+1] == " "){
                newrefval += "";
            }
            else{
                newrefval += refVal[i];
            }
        }
        ref.prop("value", newrefval);
    },
    globalHeaderInitial: function () {
        ACC.global.headreResize();
        ACC.global.globalHeaderHeight = $(".linktracking-header").height();
        $(".linktracking-header").addClass('global-header-sticky');
        $(".js-mainHeader").css({ paddingTop: ACC.global.globalHeaderHeight });
        $(".new-vertical-categories").css({ height: Math.round(ACC.global.wHeight - ACC.global.globalHeaderHeight), top: Math.round(ACC.global.globalHeaderHeight) });
        ACC.global.fetchListData();
        ACC.global.removeFormHeader();
        $("[data-global-linkname]").on("click", function () {
            let linkName = $(this).data("global-linkname");
            ACC.global.globalHeaderV2("", linkName, "v2: home");
        });
        $(document).on("click", function (e) {
            let target = $(e.target);
            if (ACC.global.wWidth > 1023 && $(".js-document-global-btn").hasClass("active") && !target.hasClass("js-document-global-btn") && !target.parents().hasClass("js-document-global-btn")) {
                if (!target.parents(".js-document-global-box").length ) {
                    $(".js-document-global-btn.active").trigger("click");
                }
            }
            if ($(".js-document-invoice-btn").hasClass("active") && !target.hasClass("js-document-invoice-btn") && !target.parents().hasClass("js-document-invoice-btn")) {
                if (!target.parents(".js-document-global-box").length && !$(".ui-datepicker").is(":visible")) {
                    $(".js-document-invoice-btn.active").trigger("click");
                }
            }
            if($(".js-plp-uom-btn").hasClass("active") && !target.hasClass("js-plp-uom-btn") && !target.parents().hasClass("js-plp-uom-btn"))
            {
                $('.popup-box-muom').hide();

            }
            if($(".js-change-branch-btn").hasClass("active") && !target.hasClass("js-change-branch-btn") && !target.parents().hasClass("js-change-branch-btn") && !target.hasClass("facet__list__mark") && !target.hasClass("list-of-nearby-stores-plp") && !target.hasClass("search_nearby_enabled"))
                {
                    $('.nearby_more_locations').hide();
                    $(".nearby_locale").removeClass("active");
    
                }
            if($(".js-btn-variant").hasClass("active"))
            {
               if(!target.hasClass("js-btn-variant") && !target.parents().hasClass("js-btn-variant")){
				    $('.popup-box-variant').hide();  
				    $(".js-btn-variant").removeClass("active");
			   }
            }
        });
        if (ACC.global.wWidth > 1023) { //WEB 
            $(document).on('click', '.language-value', function (e) {
                if ($('.global-popup-box').is(":visible")) {
                    $('.global-popup-box').hide();
                    $(".js-document-global-btn.active").removeClass("active");
                }
                if ($(".global-category-section").hasClass("active")) {
                    $(".global-category-section").trigger("click");
                }
            });
        }
        else{ //Mobile WEB 
            $(".mob-store-details-block").fadeOut();
        }
    },
    removeFormHeader: function () {
        let pageName = $(".pagename").val();
        if ((pageName == "Saved List Page" && $(".pdpredesign-container #siteoneSavedListCreateForm").length) || pageName == "Edit Saved List Page" || (pageName == "Assembly Page List Page" && $(".pdpredesign-container #siteoneAssemblyCreateForm").length) || pageName == "Edit assembly Page") {
            $(".global-savedlist-box #siteoneSavedListHeaderCreateForm").remove();
        }
    },
    fetchListData: function () {
        if ($(".global-savedlist-box").length && !$(".list-group-item-savedlist .list-group-item").length && !$(".list-group-item-recommended .list-group-item").length) {
            let foundOneList;
            $.ajax({
                url: ACC.config.encodedContextPath + "/savedList/getAllLists",
                type: "GET",
                dataType: 'json',
                data:
                {
                    "numberOfLists": 5
                },
                success: function (response) {
                    let itemName = ["savedlist", "recommended"];
                    let itemLinkName = ["most recent", "recommended"];
                    if (response && typeof (response) == "object") {
                        if($(".page-accountDashboardPage").length){
                            ACC.accountdashboard.showListData(response);
                        }
                        for (let i = 0; i < itemName.length; i++) {
                            let ref = i == 0 ? response.savedLists : response.recommendedLists;
                            for (let j = 0; j < ref.length; j++) {
                                let refPath = i == 0 ? "" : ref[j].categoryName.replace("&", "%26");
                                refPath = ACC.config.encodedContextPath + (i == 0 ? '/savedList/listDetails?code=' + ref[j].code : '/savedList/recommendedListDetails?categoryName=' + refPath);
                                let refName = i == 0 ? ref[j].name : ref[j].categoryName;
                                let linkname = itemLinkName[i] + ": " + refName.toLowerCase();
                                $(".list-group-item-" + itemName[i]).append('<a data-global-linkname="list: ' + linkname + '" href="' + refPath + '" class="list-group-item transition-3s">' + refName + '</a>');
                            }
                            if ($(".list-group-item-" + itemName[i] + " .list-group-item").length) {
                                $(".global-" + itemName[i] + "-empty").addClass("hidden");
                                $(".global-" + itemName[i] + "-filled").removeClass("hidden");
                                foundOneList = true;
                            }
                            else {
                                $(".global-" + itemName[i] + "-filled").addClass("hidden");
                                $(".global-" + itemName[i] + "-empty").removeClass("hidden");
                            }
                        }
                    }
                    else {
                        for (let i = 0; i < itemName.length; i++) {
                            $(".global-" + itemName[i] + "-filled").addClass("hidden");
                            $(".global-" + itemName[i] + "-empty").removeClass("hidden");
                        }
                    }
                    if(!foundOneList && ACC.global.wWidth < 1024){
                        for (let i = 0; i < itemName.length; i++) {
                            $(".global-" + itemName[i] + "-empty").addClass("hidden");
                        }
                        $(".global-all-empty").removeClass("hidden");
                    }
                },
                error: function (response) {
                    let itemName = ["savedlist", "recommended"];
                    if(ACC.global.wWidth < 1024){
                        for (let i = 0; i < itemName.length; i++) {
                            $(".global-" + itemName[i] + "-filled, .global-" + itemName[i] + "-empty").addClass("hidden");
                        }
                        $(".global-all-empty").removeClass("hidden");
                    }
                    else{
                        for (let i = 0; i < itemName.length; i++) {
                            $(".global-" + itemName[i] + "-filled, .global-" + itemName[i] + "-empty").removeClass("hidden");
                            $(".global-" + itemName[i] + "-filled, .global-" + itemName[i] + "-filled").addClass("hidden");
                        }
                    }
                    
                }
            });
            $(".global-savedlist-box [data-global-linkname]").on("click", function () {
                let linkName = $(this).data("global-linkname");
                ACC.global.globalHeaderV2("", linkName, "v2: home");
            });
        }
    },
    categorySlide: function (e, ref, leftRef, time) {
        ACC.global.wWidth = $(window).width();
        ACC.global.wHeight = $(window)[0].innerHeight;
        let target = $(ref);
        let leftCal = typeof (leftRef) == 'number' ? leftRef : Math.floor($(leftRef).offset().left);
        if (target.hasClass("showing-menu")) {
            $('html').css({ 'overflow-y': "auto", paddingRight: 0 });
            $('.linktracking-header').width('100%');
            $('.L2CategoryHeader, .L3CategoryHeader').animate({ left: 0 }, function () {
                $(this).css("display", 'none');
            });
            $('.categories-overlay').fadeOut();
            $('.global-category-section').removeClass("active");
            $('.ham-icon').removeClass("icon-rotate");
            target.removeClass("showing-menu").animate({ left: -target.outerWidth() - 20 }, time);
        }
        else {
            $(".new-vertical-categories").css({ height: Math.round(ACC.global.wHeight - ACC.global.globalHeaderHeight) });
            if ($('.global-popup-box').is(":visible")) {
                $('.global-popup-box').hide();
                $(".js-document-global-btn.active").removeClass("active");
            }
            if ($('.language-dropdown').is(":visible")) {
                $('.language-value').trigger("click");
            }
            $(".L1CatLinks").removeClass("active");
            target.addClass("showing-menu").animate({ left: leftCal }, time);
            $('html').css({ overflow: "hidden", paddingRight: 17 });
            $('.linktracking-header').width(ACC.global.wWidth);
            $('.categories-overlay').fadeIn();
            $('.ham-icon').addClass("icon-rotate");
            $('.global-category-section').addClass("active");
        }
    },
    mobMenuSlide: function (e, ref, btnRef, time) {
        ACC.global.wWidth = $(window).width();
        ACC.global.wHeight = $(window)[0].innerHeight;
        let target = $(ref);
        if (target.hasClass("showing-menu")) {
            $(".new-vertical-categories").animate({ left: "-100%" });
            $('.L2CategoryHeader, .L3CategoryHeader').animate({ left: 0 }, function () {
                $(this).css("display", 'none');
            });
            $(".after-overlay").fadeOut();
            target.removeClass("showing-menu").animate({ left: -target.outerWidth() - 20 }, time);
        }
        else {
            $(window).scrollTop(0);
            let topHatHeight = $(".header-top-hat").outerHeight();
            $(".global-menu-section").css({ height: ACC.global.wHeight - topHatHeight, top: topHatHeight });
            if ($('.global-popup-box').is(":visible")) {
                $('.global-popup-box').hide();
                $(".js-document-global-btn.active").removeClass("active");
            }
            if ($('.language-dropdown').is(":visible")) {
                $('.language-value').trigger("click");
            }
            $(".L1CatLinks").removeClass("active");
            target.addClass("showing-menu").animate({ left: 0 }, time);
            let categoryTop = Math.round($(".global-categories-btn").offset().top + $(".global-categories-btn").outerHeight());
            $(".new-vertical-categories").css({ height: ACC.global.wHeight - categoryTop, top: categoryTop, display: "block" });
            $(".new-vertical-categories").eq(2).animate({ left: 0 }, time);
            $(".new-vertical-categories").eq(1).css({ left: "100%" });
            $(".new-vertical-categories").eq(0).css({ left: "100%" });
            $(btnRef + ', .global-categories-btn').addClass("active");
            $(".after-overlay").fadeIn();
        }
    },
    globalHeaderSticky: function (e, scrollTop) {
        let target = $(".linktracking-header");
        if (scrollTop < ACC.global.globalHeaderHeight + 20 || ACC.global.windowScrollTop > scrollTop) {
            target.removeClass('animateInUp');
            target.addClass('animateInDown');
        }
        else {
            target.addClass('animateInUp');
            target.removeClass('animateInDown');
        }
        ACC.global.windowScrollTop = scrollTop;
    },
    categories: function (e, type, targetParent) {
        let refNextTarget;
        let target = $(e);
        type = type.toLowerCase();
        let linkname = target.data("global-linkname");
        $(targetParent).empty();
        let targetClass = "L3";
        let targetLink = "L2";
        let targetHTML = "";
        let targetText = target.text();
        if (targetParent == ".L2CategoryHeader") {
            refNextTarget = '.L3CategoryHeader';
            targetClass = "L2";
            targetLink = "L1";
            $(refNextTarget).animate({ left: 0 }, function () {
                $(this).css("display", 'none');
            });
        }
        targetHTML += '<li class="categoriesBack hidden-md hidden-lg"><button class="transition-3s btn btn-block bold text-align-left" onclick="ACC.global.categoriesBack(this,\'' + targetParent + '\',\'' + targetText + '\')" alt="' + targetText + '">' + targetText + '</button></li>';
        targetHTML += '<li class="' + targetClass + 'Categories"><a data-global-linkname="' + linkname + ': shop all" class="L3CatLinks transition-3s bold shop-all" href="' + ACC.config.encodedContextPath + target.data("href") + '" alt="' + targetText + '">Shop All</a></li>';
        $("." + targetLink + "CatLinks").removeClass("active");
        target.addClass("active");
        $('[data-' + type + ']').each(function () {
            let ref = $(this);
            let refCode = ref.data(type).toLowerCase();
            refHref = ref.data("href");
            let refText = ref.text();
            if (ref.data("children") && targetParent == ".L2CategoryHeader") {
                targetHTML += '<li class="L2Categories"><button data-global-linkname="' + linkname + ': ' + refText.toLowerCase() + '" onclick="ACC.global.categories(this,\'' + refCode + '\',\'' + refNextTarget + '\')" data-code="' + refCode + '" class="btn btn-block text-align-left L2CatLinks text-default transition-3s" data-href="' + refHref + '" title="' + refText + '">' + refText + '</button></li>';
            }
            else if(!ref.hasClass('L2Images')) {
                targetHTML += '<li class="L3Categories"><a data-global-linkname="' + linkname + ': ' + refText.toLowerCase() + '" class="L3CatLinks transition-3s" href="' + ACC.config.encodedContextPath + refHref + '" alt="' + refText + '">' + refText + '</a></li>';
            }
        });
        
        if(targetParent == ".L3CategoryHeader"){
            $(".L2Images[data-" + type + "]").each(function () {
                let ref = $(this);
                let refHref = ref.data(type);
                let refSrc = ref.data("href");
                let refText = ref.text();
                targetHTML += '<li class="L3Categories L2banner"><a data-global-linkname="' + linkname + ': ' + refText.toLowerCase() + '" class="L3CatLinks transition-3s" href="' + refHref + '" alt="' + refText + '"><img class="border-radius-3 L3Images display-block" src="' + refSrc + '" alt="' + refText + '" /></a></li>';
            });  
        }
        
        $(targetParent).append(targetHTML);
        $(targetParent + " [data-global-linkname]").on("click", function () {
            let linkName = $(this).data("global-linkname");
            ACC.global.globalHeaderV2("", linkName, "v2: home");
        });
        $(targetParent).css("display", 'block').animate({ left: ACC.global.wWidth < 1024 ? '-100%' : targetParent == ".L2CategoryHeader" ? 330 : 634 });
    },
    categoriesBack: function (e, type, targetParent) {
        let target = $(type);
        target.animate({ left: 0 });
    },
    headreResize: function () {
        ACC.global.wWidth = $(window).outerWidth();
        if(ACC.global.wWidth < 1024){ //ipad iphone
            $(".global-search-section").removeAttr('style');
            $(".global-category-section, .global-logo-section, .global-search-section, .global-menu-section").removeClass("width-auto");
        }
        else{
            $(".global-category-section, .global-logo-section, .global-menu-section").addClass("width-auto");
            $(".global-menu-section").css({ height: 'auto' });
            var searchWidth = $("#header-store-links").outerWidth() - $(".global-category-section").outerWidth() - $(".global-logo-section").outerWidth() - $(".global-menu-section").outerWidth();
            $(".global-search-section").width(searchWidth-87);
        }
    },
	globalHeaderV2: function(linkType, linkName, onClickPageName) {
		if (onClickPageName && onClickPageName != "") {
			digitalData.eventData = {
				linktype: linkType,
				linkName: linkName,
				onClickPageName: _AAData.pathingPageName && _AAData.pathingPageName != "" ? _AAData.pathingPageName : _AAData.page.pageName && _AAData.page.pageName != "" ? _AAData.page.pageName : onClickPageName
			}
			try {
				_satellite.track("linkClicks");
			} catch (e) { }
		}
	},
    functionalCarouselIndicators: function(ref) {
        let target = $(ref);
        let slideTo = target.attr('data-slide-to');
        let parentId = target.data('target');
        target.parents(parentId).carousel(parseInt(slideTo) - 1);
    },
  	popupHeightSet: function (e, target) {  // Invoice page Redesign Start
		$(target).css({height: ACC.global.wHeight - ACC.global.globalHeaderHeight, top: ACC.global.globalHeaderHeight + 1});
	},
    hardscapeProductsAlertMsg: function() {
    if ($(".page-orderSummaryPage").length || $(".page-orderConfirmationPage").length) {
        let hardscapeProductFound = false;
        $(".orderDetailUom").each(function () {
            let target = $(this);
            let uom = target.data("uom");
            let l1Category = (ACC.config.encodedContextPath == '/es') ? 'Materiales duros & Vida al Aire Libre' : 'Hardscapes & Outdoor Living';
            let landscapeCategory = (ACC.config.encodedContextPath == '/es') ? 'Material de Jardinería' : 'Landscape Supply';
			let cosumableCategory = (ACC.config.encodedContextPath == '/es') ? 'Consumibles' : 'Consumables';
            if ((target.data('level1category') == l1Category || (target.data('level1category') == landscapeCategory && target.data('level2category') == cosumableCategory )) && (uom.includes("Net Ton") || uom.includes("Cubic Yard"))) {
                hardscapeProductFound = true;
            }
            //Refundable pallet message for pickup and delivery
            let orderTypeRefund = $("#orderType").val();
            if ((orderTypeRefund == 'PICKUP' || orderTypeRefund == 'DELIVERY') && (target.data('level1category') == 'Materiales duros & Vida al Aire Libre' || target.data('level1category')== 'Hardscapes & Outdoor Living') && (uom.includes("Net Ton") || uom.includes("Pallet"))) {
                $(this).closest(".item__list--header").find(".refundableMsg").removeClass('hidden')
            }
        });
        if (hardscapeProductFound) {
            $(".hardscape-PA-orderConfirm").removeClass('hidden');
        }
    }
},
    
	openMediaInPopup:function(){
		function popupMedia(thisHtml){
			ACC.colorbox.open("", {
      			html: thisHtml,
      			width: "750px",
      			open: true,
				onComplete: function () {
					setTimeout($.colorbox.resize, 500);
          $("#cboxContent").addClass("trio-mobile-size");
				},
				onClosed: function () {
					$(".trio-video").attr("src","");
				},
			})
		}
		$(document).on("click", ".promo-banner-media", function(e){
			e.preventDefault();
			var popupContent="";
			if($(this).data("youtube-url")===undefined ||$(this).data("youtube-url")==="" ||$(this).data("youtube-url")===null ){
				
				if($(this).data("target-value")==="_self")
				{
					window.location.href=$(this).data("encoded-url");
				}
			else
				{
					window.open($(this).data("encoded-url"),"_blank");
				}			
			
			}
			else{
				trioVideoId=$(this).data("youtube-url");
				popupContent=`<div class="video-responsive"><iframe class="trio-video" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" 
        frameborder="0"  src="https://www.youtube.com/embed/${trioVideoId}"></iframe></div>`;
				popupMedia(popupContent)
				}
		})
	},
	//for spanish account creation form spanish radio btn is selected
	 
	
	uomDropdowninventoryId: function() {
        let pdpinventoryUomId = $('.custom-dropdown-label:first').data("value");
        if(pdpinventoryUomId){
            $('[id="inventoryUomId"]').each(function(){$(this).val(pdpinventoryUomId)});
            $('[id="inventoryUOMIDVal"]').each(function(){$(this).val(pdpinventoryUomId)});
        }
    },
    
    handleFlyoutIpad: function() {
        $('.article-box:nth-child(3n+3)').after('<div class="clearfix"></div>');

        $('.L1CatLinks').on('touchstart', function(e) {
            var touched = $(this).closest('.L1Categories').find('.L2Categories').is(':visible');
            $(this).attr('touched', touched);
        }).on('click', function(e) {
            if ($(this).attr('touched') == 'false') {
                e.preventDefault();
            }
        });

        $('.L2CatLinks').on('touchstart', function(e) {
            var touched = $(this).closest('.L2Categories').find('.L3Categories').is(':visible');
            $(this).attr('touched', touched);
        }).on('click', function(e) {
            if ($(this).attr('touched') == 'false') {
                e.preventDefault();
            }
        });
    },
	resetSortOnCategoryselect: function(){
		//reset sort selection once the user clicks on any category
		$(document).on("click", ".L1CatLinks,.L2CatLinks,.L3CatLinks", function(e){
		 
			sessionStorage.removeItem('selectedDropdownValueSession');
		})
        $(document).on("click", ".mobile-l1,.mobile-l2,.mobile-l3", function(e){
            sessionStorage.removeItem('mobileSortSelection');
        })
	},
    galleryboxpopup: function(){
        $('a.gallery').colorbox({
            arrowKey: true,
            rel:'gallery',
            className:"gallerypopup",

            scalePhotos: true ,
            opacity: 0.7,
            close:'<span class="glyphicon glyphicon-remove"></span>',
        });

        var x = window.matchMedia("(max-width: 1023px)")
        popresize(x)
        x.addListener(popresize)
        function popresize(x) {
            if (x.matches) {
                $('a.gallery').colorbox({
                    maxWidth:"100%",
                    width:"100%",
                });
            }

            else{
                $(window).resize(function(){
                    $.colorbox.resize({
                        maxWidth:"auto",
                        width:95+'%',
                    });
                });
            }
        }

        $(document).bind('cbox_complete', function(){
            $("#cboxTitle").addClass("headline");
            var gallerytitle = $('#cboxTitle').text();
            var src=$("#cboxContent img.cboxPhoto").attr("src");
            var count=$("a.gallery[href='"+src+"']").data("count")+1;
            var size=$("a.gallery[href='"+src+"']").data("size")+1;
            var title=$("a.gallery[href='"+src+"']").attr("title");

            if (isNaN(count) && isNaN(size)) {
                $('.gallerypopup #cboxCurrent').prepend('<span id="popup_title"> '+ title + ' Gallery</span>');
            }
            else{
                $('.gallerypopup #cboxCurrent').prepend('<span id="popup_title"> '+ title + ' image '+count+' of '+size+'</span>');
            }
        });
    },
    
    /***ProjectCalculator***/
    analyticsProjectCalculator: function () {
        let projectCalculatorName = $(".pagename").val();

        if ($('.page-stoneWallsCalculatorPage').length > 0 || $('.page-flagStoneCalculatorPage').length > 0 || $('.page-roadBasefillCalculatorPage').length > 0 || $('.page-barkMulchCalculatorPage').length > 0 || $('.page-decorativeRockCalculatorPage').length > 0 || $('.page-topSoilCalculatorPage').length > 0) {
            _AAData.page.pageName = 'content: project calculators: ' + projectCalculatorName;
            _AAData.pathingChannel = 'content';
            _AAData.pathingPageName='content: project calculators: ' + projectCalculatorName;
        }
    },

    /***LinkToPay*******/
    linkToPayClick: function () {
        loading.start();
        //Cayan iFrame cancel button event check
        if (sessionStorage.getItem("cayancancel")) {
          sessionStorage.setItem("cayancancel", 0);
        }
        var kountSessionId = document.getElementById("kountSessionIdLink").value;
        var orderNumber = document.getElementById("orderNumberLink").value;
        var orderAmount = document.getElementById("orderAmountLink").value;
        if (!orderNumber || !orderAmount || !kountSessionId || orderNumber == "" || orderAmount == "" || kountSessionId == "") {
            console.log("AJAX Prams: ", orderNumber , orderAmount , kountSessionId);
          ACC.global.linkToPayError('payment-not-process', 'link-payment-error');
        }
        else {
          $.ajax({
            url: ACC.config.encodedContextPath + "/link-to-pay/fetchOrderDetails",
            type: "GET",
            dataType: 'json',
            contentType: 'application/json',
            data:
            {
              "orderNumber": orderNumber,
              "orderAmount": orderAmount,
              "kountSessionId": kountSessionId
            },
            success: function (response) {
              transportKeyLink = response;
              if (transportKeyLink != null && transportKeyLink != 'Decline') {
                var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey=' + transportKeyLink;
                $(".linktopay-iframe").html('<iframe id="myIframeLink" title="myIframeLink" class="Pop-up-myIframe-Link" src="'+ redirecturl +'" name="myIframeLink" onLoad="ACC.global.paymentConfirmationLoad(this)"></iframe>');
                $(".token-valid").css("display", "none");
                $(".cayan-iframe").css("display", "block");
                _AAData.pathingChannel = "link2pay";
                _AAData.pathingPageName = "link2pay: cardInfo";
                _AAData.orderNumber = orderNumber;
                _AAData.orderAmount = orderAmount;
                _satellite.track("pageload");
                loading.stop();
              }
              else if (transportKeyLink != null && transportKeyLink == 'Decline') {
                ACC.global.linkToPayError('payment-not-process', 'link-payment-error');
              }
              else {
                ACC.global.linkToPayError('payment-not-process', 'link-payment-error');
              }
            },
            error: function (response) {
              ACC.global.linkToPayError('payment-not-process', 'link-payment-error');
            }
          });
        }
        ACC.global.linkToPayAdobeTracking("", "pay now", "link2pay: verification", "linkClicks");
    },
    mycayanInterval: '',
    cayanIframeClose: function()
	{
	    clearInterval(ACC.global.mycayanInterval);
	},
    paymentConfirmationLoad: function() { // LinkToPay //
        try {
            var oIframe = document.getElementById('myIframeLink');
            var oDoc = oIframe.contentWindow || oIframe.contentDocument; // for compatibility
            if (oDoc.document) {
                console.log("payment Function: document found");
                var oDoc = oDoc.document;
                var sPageURL = oDoc.location.search.substring(1);
                var sURLVariables = sPageURL.split('&');
                if (sURLVariables && sURLVariables != "") {
                    loading.start();
                    $("#myIframeLink").hide();
                    ACC.global.cayanIframeClose();
                    console.log("payment Function: having parameters");
                    for (var i = 0; i < sURLVariables.length; i++) {
                        var sParameterName = sURLVariables[i].split('=');
                        console.log("payment Function: checking ", sParameterName[1]);
                        if (sParameterName[1] && sParameterName[1] != "") {
							   var pathArray = oDoc.location.search.substring();
                            if (sParameterName[1] == 'APPROVED') {                             
                                console.log("payment Function: approved");
                                $.ajax({
                                    url: ACC.config.encodedContextPath + "/link-to-pay/paymentDetails" + pathArray,
                                    method: "GET",
                                    async: false,
                                    success: function (response) {
                                        if (typeof response == 'string' && response.toLowerCase() == 'success') {
                                            window.parent.location.href = ACC.config.encodedContextPath + "/linkpayconfirmation";
                                        } else {
                                            $(".cayan-iframe, .token-valid").css("display", "none");
                                            $(".cayan-payment-failed").css("display", "block");
                                            ACC.pendo.captureEvent("3XLINK2PAY");
                                            loading.stop();
                                        }
                                    },
                                    error: function () {
                                        window.parent.location.href = ACC.config.encodedContextPath + "/linkpayconfirmation";
                                    }
                                });
                                $("#myIframeLink").remove();
                                ACC.global.linkToPayAdobeTracking("", "cardInfo: submit", "link2pay: cardInfo", "linkClicks");
                                break;
                            }
                            else {
                                if (sParameterName[1] == 'User_Cancelled') {
                                    console.log("payment Function: canceled");
                                    var cayanctn = parseInt(sessionStorage.getItem("cayancancel"));
                                    cayanctn = cayanctn + 1;
                                    sessionStorage.setItem("cayancancel", cayanctn);
                                    $("#myIframeLink").remove();
                                    if (sessionStorage.getItem("cayancancel") == 1) {
                                        ACC.global.linkToPayAdobeTracking("", "cardInfo: cancel", "link2pay: cardInfo", "linkClicks", "link2pay", "link2pay: verification");
                                    }
                                    $(".try-again-btn button").trigger("click");
                                    break;
                                }
                                else {
									 $.ajax({
                                    url: ACC.config.encodedContextPath + "/link-to-pay/paymentDetails" + pathArray,
                                    method: "GET",
                                    async: false,
                                    success: function (response) {
                                        if (typeof response == 'string' && response.toLowerCase() == 'success') {
                                         alert('/link-to-pay/paymentDetails wrong response');
                                        } else {
                                            $(".cayan-iframe, .token-valid").css("display", "none");
                                            $(".cayan-payment-failed").css("display", "block");
                                            ACC.pendo.captureEvent("3XLINK2PAY");
                                            loading.stop();
                                        }
                                    },
                                    error: function () {
                                      $(".cayan-iframe, .token-valid").css("display", "none");
                                            $(".cayan-payment-failed").css("display", "block");
                                            loading.stop();
                                    }
                                });
                                    $("#myIframeLink").remove();
                                    console.log("payment Function: declined");
                                    setTimeout(function () {
                                        $(".cayan-iframe").css("display", "none");
                                        $(".cayan-payment-failed").css("display", "block");
                                        $(".token-valid").css("display", "none");
                                        $(".try-again-btn").removeClass("hidden");
                                        loading.stop();
                                        ACC.global.linkToPayAdobeTracking("", "cardInfo: submit", "link2pay: cardInfo", "linkClicks", "link2pay", "link2pay: payment decline", orderNumber, orderAmount);
                                    }, 2000);
                                }
                            }
                        }
                    }
                }
            }
        } catch (err) {
        }
    },
    linkToPayAdobeTracking: function(type, name, page, click, pChannel, pPage, oNumber, oAmount) {
        digitalData.eventData = {
            "linktype": type,
            "linkName": name,
            "onClickPageName": page
        }
        try {
            _satellite.track(click);
        } catch (e) { }
        if (pChannel) {
            _AAData.pathingChannel = pChannel;
            _AAData.pathingPageName = pPage;
        }
        if (oNumber) {
            _AAData.orderNumber = oNumber;
            _AAData.orderAmount = oAmount;
        }
    },
    linkToPayError: function (ref, className) {
        $('#colorbox').addClass(className);
        var targetHTML = $("." + ref).html();
        ACC.colorbox.open("", {
          html: '<div class="' + ref + '">' + targetHTML + '</div>',
          width: 700,
          escKey: false,
          overlayClose: false,
          onComplete: function () {
            digitalData.eventData = {
              "linktype": "",
              "linkName": "error: payment not procesed",
              "onClickPageName": "link2pay: verification"
            }
            try {
              _satellite.track('linkClicks');
            } catch (e) { }
            loading.stop();
          }
        });
        ACC.pendo.captureEvent("LINK2PAYFAIL");
    },
    bindLinkToPayDetails: function(){
    	
    	 var orderNumber = document.getElementById("orderNumberLink").value;
         var orderAmount = document.getElementById("orderAmountLink").value;
    	
    	if(orderNumber){
            _AAData.pathingChannel="link2pay";
            _AAData.pathingPageName="link2pay: verification";
            _AAData.orderNumber=orderNumber;
            _AAData.orderAmount=orderAmount;
           
        }else{
            _AAData.pathingChannel="link2pay";
            _AAData.pathingPageName="link2pay: token expired";
           
        }
    	
        
        
        },
        
        bindLinkToPayConfirmation: function(){
        	if($(".page-linktopayverification").length){
                loading.start();
                ACC.global.mycayanInterval = setInterval(ACC.global.paymentConfirmationLoad, 1000);
            }
            if($(".page-linkConfirmationPage").length){
                $("#cDate").html(ACC.global.getDate());
                $("#cTime").html(ACC.global.getTime());
        	 $(document).on("click",".print-linktopay",function(e)
                     {
             			window.print();
             			
             			digitalData.eventData={
 		                          "linktype": "",
 		                          "linkName": "print",
 		                          "onClickPageName": "link2pay: payment confirmation"
 		                      }
 		                                      
 		                      try {
 		                      _satellite.track('linkClicks');
 		                      }catch(e){}
                   	
                     });
        	 
        	 $(document).on("click",".share-email-linktopay",function(e)
                     {
                   	 $('#colorbox').addClass('link-share-by-mail');
                   	 var targetHTML = $(".share-by-mail").html();
	       	   		  ACC.colorbox.open("", {
	       						 html: '<div class="share-by-mail">'+targetHTML+'</div>',
	       						 className: 'email-share-link2pay',
	       		                 escKey: false,
	       		                 maxWidth: 700,
	       		                 overlayClose: false,
	       		                 onComplete: function(){
	       		                	digitalData.eventData={
	       		                          "linktype": "",
	       		                          "linkName": "send Email",
	       		                          "onClickPageName": "link2pay: payment confirmation"
	       		                      }
	       		                                      
	       		                      try {
	       		                      _satellite.track('linkClicks');
	       		                      }catch(e){}
	       		                	 
	       		                 }
	       		                
	       		            });
                   	
                     });
             
             $(document).on("click",".linktopay-email",function(e)
                     {
                   	
             		let emailId = $(".email-share-link2pay #linktopay-share-email").val();
     	        	 $.ajax({
     	                 type: 'GET',
     	                 url: ACC.config.encodedContextPath + '/sendOrderConfirmationEmail',
     	                 cache: false,
     	                 dataType: "json",
     	                 data: {"email":emailId},
     	                 success: function()
     	                 {
     	                	 ACC.colorbox.close();
     	                 }
     	             });
                   	
                     });
                     
            }
        },
        bindsetactivetab: function() {
        	$(document).on("click", ".js-setactivetab", function(){
        		var pageId = $(this).data("key");
    			$.cookie("accountPageId", pageId, { path: '/' });
        		sessionStorage.setItem("activeTab", $(this).data("active"));
        		sessionStorage.setItem("selectedDropdownValueSession", ACC.config.defaultSortValue);
        	})
        },
    getinvoicelastdate:function(){
        $(document).on("click", ".last-invoice", function()
        {

            var datarange=$(".last-invoice").text();
            var href= ACC.config.encodedContextPath +'/my-account/invoices/'+($("#unitUid").val()).trim();

            href=href+'?datarange='+datarange;
            $(".last-invoice").attr('href',href);

        });
    },

    bindShipToFormData: function getData() {
        $(".shipto-pagination .pagination li a").on('click',function(){
            var searchParam = $("#search-ship-to").val();
            var sort = $("#sortShhipTo").val();
            $(this).prop('href',$(this).prop('href')+"&searchParam="+searchParam+"&sort="+sort);
        });
    },


    bindInvoiceCalendar: function() {

        $('.datepickerinvoiceFrom').datepicker({
            onSelect: function (selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate() + 1);
                $("#dateTo").datepicker("option", "minDate", dt);
            }
        });

        $('.datepickerinvoiceTo').datepicker({
            maxDate: '0',
            onSelect: function (selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate() - 1);
                $("#dateFrom").datepicker("option", "maxDate", dt);
            }
        });
       
    },


    bindInvoice: function() {
        $(".invoiceButton").on('click',function () {
            var invoiceSearchURL = ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim();

	    var selectedInvoice = $("#shipToSelected_inv").val();
	    sessionStorage.setItem('selectedInvoiceSession',selectedInvoice);
            $('#invShipTo').val(selectedInvoice);	
            var invoicesearchForm = $('#invoicesearchForm');
            invoicesearchForm.submit();
        });
    },

    bindOrderHistory: function() {
        $(".orderHistoryButton").on('click',function () {
            var searchParam = '/my-account/orders/'+($("#unitUid").val()).trim() +($("#searchOrderHistory").val()).trim();
            var searchForm = $('#searchForm');
            searchForm.submit();
        });
    },
    bindBuyItAgain: function(e) {
        let qPage = $("#filterByDateBuyagain option:selected").val().split(':');
        let qval = ($("#searchBuyItAgain").val()).trim() + ":" + qPage[1] + ":" + qPage[2] + ":" + qPage[3];
        ($("#unitUid").val()).trim();
        $("#searchTermWithQueryParam").val(qval);
        $('#searchForm').submit();
    },
    bindManagerUser:function(){
        $(".manager-user-Button").on('click',function () {
            var searchParam = ACC.config.encodedContextPath + '/my-company/organization-management/manage-users/'+($("#unitUid").val()).trim() +($("#manager-user-voice").val()).trim();
            var MangerusersearchForm = $('#MangerusersearchForm');
            MangerusersearchForm.submit();
        });

    },
   
    bindEwallet:function(){
		$(".eWallet-user-Button").on('click',function () {	 
		       var searchParam = ACC.config.encodedContextPath + '/my-account/ewallet/'+($("#unitUid").val()).trim() +($("#ewallet-page-card").val()).trim();
		       var EwalletsearchForm = $('#EwalletsearchForm');
		       EwalletsearchForm.submit();
		   });

	},

    userCheckboxMethods:function(){
        $("#admin_User").on('click',function () {

            var viewpagination =$(".user-manager").val();
            var searchParam = $("#manager-user-voice").val();
            var unitid=$("#unitUid").val();
            var sort =$(".user_sort").val();
            var shipto =$(".ship-TosUsers").val();
            var adminCheck = $('#admin_User').val();

            if(document.getElementById('admin_User').checked) {
                $('#admin_User').prop('checked', true);
                window.location.href ='?searchParam='+searchParam+'&shiptounit='+shipto+'&sort='+sort+'&pagesize='+viewpagination+'&filterAdmin=true'
            }else{
                $('#admin_User').prop('checked', false);
                window.location.href ='?searchParam='+searchParam+'&shiptounit='+shipto+'&sort='+sort+'&pagesize='+viewpagination+'&filterAdmin=false'
            }
        })
    },

  resetShiptoDropDown:function(e){
		$(document).on("click", "#userPopup", function()
        {

			var selecteduser =this.innerText +'_US';  
			sessionStorage.setItem("selecteduserSession",selecteduser);

        });
		$(document).on("click", "#orderPopup", function()
        {

			var selectedOrder =$(this).attr('data-index')+' '+$(this).attr('data-name');
			sessionStorage.setItem('selectedOrderSession',selectedOrder);

        });
		$(document).on("click", "#invoicePopup", function()
        {
			var selectedInvoice =$(this).attr('data-index')+' '+$(this).attr('data-name');
			console.log('selectedInvoice');
			sessionStorage.setItem('selectedInvoiceSession',selectedInvoice);

        });
		$(document).on("click", "#ewalletPopup", function()
		        {

					var selectedewallet =this.innerText +'_US';  
					sessionStorage.setItem("selectedewalletSession",selectedewallet);

		        });

	},
  
  bindcurrentdate: function() {

        $('#dateTo').datepicker({
            changeMonth: false,
            stepMonths: 0,
            dateFormat: "mm/dd/yy",
            firstDay: 1,
        }).datepicker("setDate",$("#hidDateTo").val()!= ''? $("#hidDateTo").val() : "+0d" );
        
  	},

    bindpreviousdate: function(){

        $( "#dateFrom" ).datepicker( "setDate", ($("#hidDateFrom").val()!= ''? $("#hidDateFrom").val() : "-90d") );
        

    },

    bindCalendarCurrentdate: function() {
        $( ".current-invoice-calendar" ).datepicker({
            showOn: "button",
            buttonImage: "/_ui/responsive/theme-lambda/images/siteone-calendar.png",
            buttonImageOnly: true,
            buttonText: "Select date"
        });

    },


    bindCalendarpreviousdate: function() {
        var daysArgs=document.getElementById("daysArgs").value;
        if(daysArgs=='over120'){
            $(".previous-invoice-calendar").datepicker({
                showOn: "button",
                buttonImage: "/_ui/responsive/theme-lambda/images/siteone-calendar.png",
                buttonImageOnly: true,
                buttonText: "Select date",
            }).datepicker("setDate", ''  );


        }
        else{
            $(".previous-invoice-calendar").datepicker({
                showOn: "button",
                buttonImage: "/_ui/responsive/theme-lambda/images/siteone-calendar.png",
                buttonImageOnly: true,
                buttonText: "Select date",
            }).datepicker("setDate", ($("#hidDateFrom").val()!= ''? $("#hidDateFrom").val() : "-90d")  );
            
        }
    },


    bindInvoiceFormData: function getData() {

        $(".account-invoice").on('click',function(){
            sessionStorage.removeItem('selectedInvoiceSession');
        });

        $(".account-history, .orderHistoryButton").on('click',function(){
            sessionStorage.removeItem('selectedOrderSession');
        });

        $(".manager-user-Button, .account-manage-user").on('click',function(){
            sessionStorage.removeItem('selecteduserSession');
        });

        $(".selected-dropdown-value, .removeSortbySession").on('click',function(){
            sessionStorage.removeItem('selectedDropdownValueSession');
        });
        
        $("#cboxLoadedContent .card_holder_nickname").on('blur',function(){
            sessionStorage.removeItem('selectedNicknameValueSession');
        });
        
        $(".orderTypeFormSubmit, .payment-to-branch, .payment-to-online").on('click',function(){
            sessionStorage.removeItem('selectedPaymentSession');
        });
        	
        $(".eWallet-user-Button, .account-invoice").on('click',function(){
            sessionStorage.removeItem('selectedewalletSession');
        });
    },

    successPopupbinding : function()
    {

        $(document).on("click", "#successemailSubmit", function()
        {
            ACC.colorbox.close();
        });

    },

    showToolTip: function ()
    {
        var toolTip=false;
        $(".showToolTip").on("click", function () {
            if(!toolTip){
                $(".account-req").addClass("showTip");
                toolTip = !toolTip;
                event.stopPropagation();
            }
            else{
                $(".account-req").removeClass("showTip");
            }
        });
        $(document).on('click',function (){
            if(toolTip){
                toolTip = !toolTip;
                $(".account-req").removeClass("showTip");
            }
        });
    },

	setCookie: function (cname, cvalue, exdays) {
	    const d = new Date();
	    d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    let expires = "expires="+ d.toUTCString();
	    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	},

	// Delete cookie
	 deleteCookie: function (cname) {
	    const d = new Date();
	    d.setTime(d.getTime() + (24*60*60*1000));
	    let expires = "expires="+ d.toUTCString();
	    document.cookie = cname + "=;" + expires + ";path=/";
	},
    successPopup: function()
    {
    	if(digitalData.pfm)
    	{
    	  delete digitalData.pfm;
    	  delete digitalData.pfmdetails;
    	}
    	
    	if ($('.loginstatus').val() != ''){
			var cartID= $("#cartID").val();
		}
		else{
			var cartID= $("#anonymousCartId").val();
		}
        $("#colorbox").addClass("success-msg-email-pop-up");
        ACC.colorbox.open("", {
            html : "<div class='PopupBox'><h1 class='headline2'>"+ACC.config.emailMessage+"!</h1><div class='submit-btn-wrapper' style='width:100%;'><input id='successemailSubmit' class='btn btn-primary' type='button' value='"+ACC.config.continueShopping+"'></div></div>",
            width: '500px',
            height: 'auto',
            onComplete: function(){
            	if ($('.page-detailsSavedListPage').length > 0){
			        _AAData.popupPageName= ACC.config.emaillistsuccesspathingPageName;
			        _AAData.popupChannel= ACC.config.myaccountpathingChannel;
				 	try {
					    	 _satellite.track('popupView');
			        } catch (e) {} 
				}
				if ($('.page-detailsAssemblyPage').length > 0){
					_AAData.popupPageName= ACC.config.emailassemblysuccesspathingPageName;
					_AAData.popupChannel= ACC.config.myaccountpathingChannel;
				 	try {
					    	 _satellite.track('popupView');
			        } catch (e) {} 
				}
				if ($('.page-cartPage').length > 0){

					_AAData.popupPageName= ACC.config.emailcartsent;
        			_AAData.popupChannel= ACC.config.checkoutpathingChannel;
    			 	try {
    				    	 _satellite.track('popupView');
    		        } catch (e) {}
					digitalData.eventData={
        	                cartID: cartID,
        	            }
        	            try {
        	            	  _satellite.track("emailCart");
        	            } catch (e) {}
				}
                $('body').css("overflow-y", "hidden");
                $("#colorbox").removeClass("email-invoice-popup");
            },
            onClosed: function() {
                $("#colorbox").removeClass("success-msg-email-pop-up");
                $('body').css("overflow-y","auto");
            }
        });

    },

    passwordStrength: function () {
        $('.password-strength').pstrength({
            verdicts: [ACC.pwdStrengthTooShortPwd,
                ACC.pwdStrengthVeryWeak,
                ACC.pwdStrengthWeak,
                ACC.pwdStrengthMedium,
                ACC.pwdStrengthStrong,
                ACC.pwdStrengthVeryStrong],
            minCharText: ACC.pwdStrengthMinCharText
        });
    },

    bindHeaderWelcomeOverlay: function ()
    {
        $(".headerWelcome ").on('click',function (e)
        {
            e.preventDefault();
            e.stopPropagation();
            $("#welcomeOverlay").show();
 
            _AAData.popupPageName= ACC.config.selectorderaccountPageName;
		     	_AAData.popupChannel= ACC.config.myaccountpathingChannel;
		 	try {
			    	 _satellite.track('popupView');
	        } catch (e) {}
 
	        if($("#welcomeOverlay").is(":visible")== false){
            	_AAData.popupPageName= "";
    			_AAData.popupChannel= "";
            	
            }
        });

        $("body").on("click",function(e){

            if($(e.target).parents("#welcomeOverlay").length  == 0 && !$(e.target).hasClass("headerShow")){

                $("#welcomeOverlay").css("display","none");
                
            }
            else{
                console.log("off box");
            }
        });

    },


    playVideo: function () {
        $("#playvideo").on('click',function ()
        {
            //window.location.href = document.getElementById("video").src;
        });

    },
    showContent: function () {
        $(".bottom-conent-show-more").on('click',function ()
        {
            $(".bottom-conent-show-more").css("display","none");
            $(".bottom-conent-hidden-content").css("display","block");
        });

        $(".bottom-content-store-details").on('click',function ()
        {
            $(".bottom-content-show-more").css("display","none");
            $(".bottom-content-hidden").css("display","block");
        });
    },
    pdpimageGalleryCarousel: function(){
        //active border for Image
        if($(".gallery-thumbnail .galleyImageItem").eq(0)) $(".gallery-thumbnail .galleyImageItem").eq(0).addClass('hactive'); 
            $(".galleyImageItem").click(function(){
            $('.galleyImageItem').removeClass('hactive');
            $(this).addClass('hactive')
        });
    },
    
    showPdpSticky: function(){
        var isFirst =  0;
        var fixImgTop = $('.product-main-info').length ? $('.product-main-info').offset().top : 0;
        let target =  $('.image-popup');
        let targetWidth = target.length ? target.outerWidth() : 0;        
        $(window).scroll(function() 
        {         
            let scrollTop = $(this).scrollTop();
            if($('.page-productDetails').length){ 
            let removeSticky = $(".rightDivArea").outerHeight()-$(".rightDivArea").offset().top - 82;
            if(!($('.productDetailsHeighlights').is(':visible'))){
	            if (scrollTop > fixImgTop && scrollTop < removeSticky){
	                target.addClass('img-sticky').css({width: targetWidth});
	            }
	            else {
	                target.removeClass('img-sticky').removeAttr("style");
	            }
			 }
                if($('.pdpredesignDescription').is(':visible')){
                    var fixmeTop = $('.pdpredesignDescription').offset().top;
                    if ($(this).scrollTop() > fixmeTop && $(".product-content").find('.product-variant-table').length <= 0)
                    {
                       $('.pdp-sticky').show();    
                       $('.pdp-sticky').addClass("pdpSticky_header");    
                       isFirst++;                          
                     }
                      else
                    {
                       $('.pdp-sticky').removeClass("pdpSticky_header");
                       $('.pdp-sticky').hide();                   
                     }
                }                
                  
                 if(isFirst == 1){                    
                     if($(".price-detail .callBranchForPrice").is(":visible")){
                         if($(".pdpRedesignuom-wrapper .retail-your-price-container .simple-product-pdp__value--retail-price.pdp-non-anonymous").is(':visible')){
                             $($(".price-detail").children()[0]).addClass('calforbranchDesign');
                             if($(".price-detail").children().length > 0) $($(".price-detail").children()[1]).find('.callBranchForPrice').remove()
                         }
                         else {
                             $(".price-detail").children()[1].remove();
                         }
                     }
                     if($(".price-detail .logInToSeeYourPrice").is(":visible") && $(".price-detail .simple-your-price-title").hasClass("hide")){
                         $(".price-detail .logInToSeeYourPrice").addClass('pdpmarg-left');
                     }
                     if($(".redesign-rgt-content .retail-your-price-container").find('.callBranchForPrice').is(":visible")){
                         if($(".price-detail").find('.callBranchForPrice').hasClass('hide')){
                             $(".price-detail").find('.callBranchForPrice').removeClass('hide');
                         }
                     }
                      if($(".simple-product-pdp__value--your-price").hasClass("pdp-anonymous")){
                        $(".simple-product-pdp__value--your-price").find(".callBranchForPrice").addClass("hidden");
                        $(".simple-product-pdp__value--your-price").find(".callBranchForPrice").parent().parent().addClass("hidden");
                    }  
                    if($(".simple-product-pdp__value--retail-price").hasClass("pdp-non-anonymous")){
                        $(".simple-product-pdp__value--retail-price").find(".callBranchForPrice").addClass("hidden");
                        $(".simple-product-pdp__value--retail-price").find(".callBranchForPrice").parent().parent().addClass("hidden");
                    }
                 }

                 if($('.addtocart-detail').find('.bg-lightGrey').hasClass('hidden')){                    
                    if($(".price-detail").find('.callBranchForPrice').is(':visible')){
                        $('.price-addcart-detail').addClass('text-right');
                    }
                }
            }
            if(ACC.global.wWidth > 1023){
                ACC.global.globalHeaderSticky(this, scrollTop);
            }
        });        
        $('.simple-product-pdp__value--your-price').parent().removeClass("col-md-6 col-sm-12 col-xs-12 text-center");
        $(".pdp-ul li").click(function(){
            $(".pdp-ul li").removeClass("active");
            $(this).addClass("active");
            if($(this).text() == "Specifications" || $(this).text() ==  "Especificaciones"){
                $('html,body').animate({
                    scrollTop: $(".specDiv").offset().top - 170},
                'slow');
            }
            else if($(this).text() == "Information & Guides" || $(this).text() == "información y guías"){
                if($('.mob-stores').is(":visible")){
					$('html,body').animate({
	                    scrollTop: $(".infoDiv").offset().top - 230},
	                'slow');
				}else{
					$('html,body').animate({
                   	    scrollTop: $(".infoDiv").offset().top - 170},
                	'slow');
				}
            }
            else{
                $('html,body').animate({
                    scrollTop: 0},
                'slow');
            }
            
        });

        $(".pdpStoreDetail").html($(".pdp-store-detail").html());
        
        if($("#addToCartSection").html()){
            $(".addtocart-detail").html($("#addToCartSection").html());    
        }                    
        else{
            $(".addtocart-detail").html($(".bg-lightGrey").parent().html());
            $(".addtocart-detail .bg-lightGrey").find('svg').remove();
            $(".addtocart-detail").children().eq(1).remove();
        }
            
        
        if($(".addtocart-detail").find(".select-grey")) $(".addtocart-detail").find(".select-grey").remove();

        if($('.addtocart-detail').find('.bg-lightGrey').hasClass('hidden')){
            $('.emptDivforspace').removeClass('hidden');            
        }
        else{
            $('.emptDivforspace').addClass('hidden');            
        }
        
        $(".addtocart-detail #showAddtoCart").click(function(){
            ACC.global.atcButtonClickEvent='header';
            $("#addToCartForm").submit();
            
        });        
        
        if($('.pdpRedesignuom-wrapper .uom-dropdown-button').is(':visible')){
            $(".price-detail").html('');
            $(".price-detail").append($('.custom-dropdown-option').clone());
           

            if($(".pdpRedesignuom-wrapper .logInToSeeYourPrice").is(":visible")){
                $(".price-detail").append($($('.retail-your-price-container').children()[1]).html());
                if($('#mulUOMRedesignpdp').val() == 'true'){
                	$('.price-detail').find('.simple-product-pdp__value:first').addClass('hide');
                }
            }
            $(".price-detail").append($(".uom-dropdown-button").clone());
        }
        else{
            $(".price-detail").html($(".retail-your-price-container").html());
            $(".price-detail").append($('.custom-dropdown-option').clone());
            $('#pdpRedesignuom-wrapper').find('.custom-dropdown-option').addClass('hidden');
        }
       
        
        $(".info,.spec").hide();
        if($('.specDiv').is(':visible')){
            $(".spec").show();
        }
        if($('.infoDiv').is(':visible')){
            $(".info").show();
        }        
        
        if($('.pdpRedesignuom-wrapper .uom-dropdown-button').length > 0){
            $(".mobile-price").html('');
            $(".mobile-price").append($('.pdpRedesignuom-wrapper .custom-dropdown-option').clone());
            $(".mobile-price").append($(".pdpRedesignuom-wrapper .uom-dropdown-button").clone());

            if($(".pdpRedesignuom-wrapper .logInToSeeYourPrice").length > 0){
                $(".mobile-price").append($($('.retail-your-price-container').children()[1]).html());
            }
        }
        else{
            $(".mobile-price").html($(".retail-your-price-container").html());
        }
        //$(".mobile-price").html($(".retail-your-price-container").html());
        
        if($(".mobile-price .callBranchForPrice").is(":visible")){
            $(".mobile-price").children()[1].remove();
        }
        $(".count-section").html($(".qty-Box").html());
        //$(".btn-section").html($("#addToCartSection").html());    
        
        if($("#addToCartSection").html()){
            $(".btn-section").html($("#addToCartSection").html());    
        }                    
        else{
            $(".mobile-addcart").html($(".bg-lightGrey").parent().html());
            $(".mobile-addcart .bg-lightGrey").find('svg').remove();
            $(".mobile-addcart").children().eq(1).remove();
        }    
        
        $(".btn-section #showAddtoCart").click(function(){
            $('#qty').val($('.js-qty-selector-input').val());
            $("#addToCartForm").submit();
        });
        
        if($('.mobile-price .callBranchForPrice').is(":visible") || $('.mobile-price .simple-your-price-title').hasClass('hide')){
            $('.mobile-price .simple-your-price-title').remove();
        }
        $('.mobile-price').find('.uom-dropdown-option').removeClass('hidden');        

        if($('.mobile-pdp-sticky').is(':visible')){
            $('.pdpRedesignuom-wrapper .uom-dropdown-option').remove();
            var getH = Math.ceil($('.mobile-pdp-sticky').outerHeight() + 30);
            $('.pdpredesignfooter').css('padding-bottom',getH+'px');
            $('.featureBullets').removeClass('hide');
            if($(".simple-product-pdp__value--your-price").hasClass("pdp-anonymous")){
                $(".simple-product-pdp__value--your-price").find(".callBranchForPrice").addClass("hidden");
                $(".simple-product-pdp__value--your-price").find(".callBranchForPrice").parent().parent().addClass("hidden");
            }  
            if($(".simple-product-pdp__value--retail-price").hasClass("pdp-non-anonymous")){
                $(".simple-product-pdp__value--retail-price").find(".callBranchForPrice").addClass("hidden");
                $(".simple-product-pdp__value--retail-price").find(".callBranchForPrice").parent().parent().addClass("hidden");
            }
            
        }
        let pdpspecLen = $('.product-classifications .pdp-specification .pdp-specification__value').length;
        let paraTxt = $('.product-classifications .pdp-specification .pdp-specification__value p');
        for(var i=0;i<pdpspecLen;i++){
            var getTxt = paraTxt.eq(i).text();
            if(paraTxt.eq(i).text().trim().length > 110){
                paraTxt.eq(i).addClass('spec-two-line-text');
                paraTxt.eq(i).attr('title',getTxt);
            }
        }

    },
    bindToggleOffcanvas: function () {
        $(document).on("click", ".js-toggle-sm-navigation", function (e) {
            e.preventDefault();
            e.stopPropagation();
            ACC.global.toggleClassState($("main"), "offcanvas");
            ACC.global.toggleClassState($("html"), "offcanvas");
            ACC.global.toggleClassState($("body"), "offcanvas");
            $(window).scrollTop(0);
            ACC.global.toggleClassState($(".store-head"), "hidden");
            ACC.global.toggleClassState($("#header-store-links"), "hidden");
            ACC.global.resetXsSearch();
        });
    },

    bindToggleXsSearch: function () {
        $(document).on("click", ".js-toggle-xs-search", function () {
            ACC.global.toggleClassState($(".site-search"), "active");
            ACC.global.toggleClassState($(".js-mainHeader .navigation--middle"), "search-open");
        });
    },

    resetXsSearch: function () {
        $('.site-search').removeClass('active');
        $(".js-mainHeader .navigation--middle").removeClass("search-open");
    },

    toggleClassState: function ($e, c) {
        $e.hasClass(c) ? $e.removeClass(c) : $e.addClass(c);
        return $e.hasClass(c);
    },
    bindHoverIntentMainNavigation: function () {

        enquire.register("screen and (min-width:" + screenMdMin + ")", {

            match: function () {
                // on screens larger or equal screenMdMin (1024px) calculate position for .sub-navigation
                $(".js-enquire-has-sub").hoverIntent(function () {
                    var $this = $(this),
                        itemWidth = $this.width();
                    var $subNav = $this.find('.js_sub__navigation'),
                        subNavWidth = $subNav.outerWidth();
                    var $mainNav = $('.js_navigation--bottom'),
                        mainNavWidth = $mainNav.width();

                    console.log($subNav);

                    // get the left position for sub-navigation to be centered under each <li>
                    var leftPos = $this.position().left + itemWidth / 2 - subNavWidth / 2;
                    // get the top position for sub-navigation. this is usually the height of the <li> unless there is more than one row of <li>
                    var topPos = $this.position().top + $this.height();

                    if (leftPos > 0 && leftPos + subNavWidth < mainNavWidth) {
                        // .sub-navigation is within bounds of the .main-navigation
                        $subNav.css({
                            "left": leftPos,
                            "top": topPos,
                            "right": "auto"
                        });
                    } else if (leftPos < 0) {
                        // .suv-navigation can't be centered under the <li> because it would exceed the .main-navigation on the left side
                        $subNav.css({
                            "left": 0,
                            "top": topPos,
                            "right": "auto"
                        });
                    } else if (leftPos + subNavWidth > mainNavWidth) {
                        // .suv-navigation can't be centered under the <li> because it would exceed the .main-navigation on the right side
                        $subNav.css({
                            "right": 0,
                            "top": topPos,
                            "left": "auto"
                        });
                    }
                    $this.addClass("show-sub");
                }, function () {
                    $(this).removeClass("show-sub")
                });
            },

            unmatch: function () {
                // on screens smaller than screenMdMin (1024px) remove inline styles from .sub-navigation and remove hoverIntent
                $(".js_sub__navigation").removeAttr("style");
                $(".js-enquire-has-sub").hoverIntent(function () {
                    // unbinding hover
                });
            }

        });
    },

    initImager: function (elems) {
        elems = elems || '.js-responsive-image';
        this.imgr = new Imager(elems);
    },

    reprocessImages: function (elems) {
        elems = elems || '.js-responsive-image';
        if (this.imgr == undefined) {
            this.initImager(elems);
        } else {
            this.imgr.checkImagesNeedReplacing($(elems));
        }
    },

    
    addGoogleMapsApi: function (callback) {
        if (callback != undefined && $(".js-googleMapsApi").length == 0) {
            $('head').append('<script class="js-googleMapsApi" type="text/javascript" src="//maps.googleapis.com/maps/api/js?key=' + ACC.config.googleApiKey + '&sensor=false&callback=' + callback + '"></script>');
        } else if (callback != undefined) {
            eval(callback + "()");
        }
    },

    backToHome: function () {
        $(".backToHome").on("click", function () {
            var sUrl =  ACC.config.encodedContextPath;
            window.location = sUrl;
        });

        $(".changePwdbackToHome").on("click", function () {
            var myAccountUrl =  ACC.config.encodedContextPath + "/my-account/account-dashboard";
            window.location = myAccountUrl;
        });
    },

    verticalNavigation: function ()
    {
        var dropdown=false;
        $(".btn-ProductFlyout").on("click", function () {
            $(".showCategories").toggle();
            dropdown = !dropdown;
        });
        $(document).on('click',function (){
            if(dropdown){
                dropdown = !dropdown;
                $(".showCategories").toggle();
            }
        });
    },

    countrySelection: function()
    {
        if(!sessionStorage.getItem("country"))
        {
            navigator.geolocation.getCurrentPosition(function(position)
            {
                var GEOCODING = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + position.coords.latitude + '%2C' + position.coords.longitude + '&language=en';

                $.getJSON(GEOCODING).done(function(location)
                {
                    if(location.results.length != 0)
                    {
                        var currentCountry = location.results[0].address_components[location.results[0].address_components.length - 2].long_name;
                        if(currentCountry.toLowerCase() == document.getElementById('popupcountry').value.toLowerCase())
                        {
                            ACC.global.countryPopup();
                        }
                        sessionStorage.setItem("country", currentCountry.toLowerCase());
                    }
                });
            });
        }
        ACC.global.countrySelectEvents();

    },
    countrySelectEvents : function() {
        $('.country-dropdown').on('click', function() {
            ACC.global.countryPopup();
        });

        $(document.body).on("click","#countrySelect",function()
        {
            var radioValue = $("input[name='country']:checked").val();
            if(radioValue == 'ca')
            {
                $('.country-dropdown').html('CA');
                window.location = "https://www.siteone.ca";
            }
            else if(radioValue == 'us')
            {
                $('.country-dropdown').html('US');
                window.location = "";
            }
        });
    },

    countryPopup: function()
    {
        $.colorbox({
            html: "<div class='PopupBox'><p class='countrypopup-text'>"+ACC.config.selectCountry+"</p><label class='country-select'><div class='border-div'><label><span class='colored-radio'><input type='radio' name='country' value='us' checked></span><span class='icon-flag-us'></span><span>US</span></label></div></label><br> <label class='country-select'><div class='border-div'><label><span class='colored-radio'><input type='radio' name='country' value='ca'></span><span class='icon-flag-canada'></span><span>Canada</span></label></div></label><br/><div class='row'><div class='col-md-5 countrySelect-btn-wrapper'><input id='countrySelect' class='btn btn-primary btn-block' type='button' value='"+ACC.config.select+"'></div></div>",
            /*maxWidth:"100%",*/
            width:"500px",
            maxHeight:"100%",
            overflow:"auto",
            opacity:0.7,
            className:"countrypopup",
            title:"<h1 class='headline'>Country Selector</h1>",
            close:'<span class="glyphicon glyphicon-remove"></span>',
            onComplete: function(){
            }
        });
    },


    bindShipToSelection: function ()
    {
        //desktop

        $('.headerShipToOptions a').on('click', function(e) {
            e.preventDefault();
            var unitId = $(this).data('uid');
            ACC.global.updateShipToCall(unitId);
        });
        //mobile


        var previous;
        $('.shipToSelection').on('focus', function () {
            // Store the current value on focus and on change
            previous = $(this).val();
        }).on('change',function () {
            // Do something with the previous value after the change
            if($(this).val() === 'shipToMore')
            {
                $(".searchMoreShip-TosLink.shipToPopup").click();
                $(this).val(previous);

            }else {
                var unitId=$('.shipToSelection').find(":selected")[0].value;
                ACC.global.updateShipToCall(unitId);
            }

            // Make sure the previous value is updated

        });

    },
    rememberMe: function () {
        $('#loginsubmit').click(function()
        {
            if($('#remember').is(':checked'))
            {
                var email=$('#j_username').val();
                $.cookie("j_username", email, { expires: 365 });
                $.cookie("j_username", email, { secure: true });
            }
            else{
                $.cookie('j_username', null);
            }
        });
        var j_username=$.cookie("j_username");
        if(j_username!=undefined)
        {
            $('#j_username').val(j_username);
            $('#remember').prop('checked', true);
        }

    },
    rememberMePopup: function () {
        $(document).on("click", "#cboxLoadedContent #loginsubmitPopup",function(e)
        {

            if($('#cboxLoadedContent #rememberPopup').is(':checked'))
            {
                var email=$('#cboxLoadedContent #usernamePopup').val();
                $.cookie("j_username", email, { expires: 365 });
                $.cookie("j_username", email, { secure: true });
            }
            else{
                $.cookie('j_username', null);
            }


        });
        var j_username=$.cookie("j_username");
        if(j_username!=undefined)
        {
            $('#usernamePopup').val(j_username);
            $('#rememberPopup').prop('checked', true);

        }

    },
    signInoverlayOpen : function(){
        $(document).ready(function(){
            if ($('.loginstatus').val() != ''){
                $(".removesignInlink").removeClass("signInOverlay");
                $(".logInToSeeYourPrice").removeClass("signInOverlay");
                $(".hideLogIn").hide();
                $(".wishlistAddProLink-wrapper").removeClass("signInOverlay");
                $(".wishlistAddProLink").removeClass("signInOverlay");
                $(".addToListPosition").removeClass("signInOverlay");
            }
        });
    },
    signInOverlay1: function(){

    	$(document).on("click", ".signInOverlayCheckout_guest", function(e){
    			e.preventDefault();
                     ACC.colorbox.open("", {
                         html: $("#signinId-checkout").html(),
                         width: "1200px",                    
                         close:'<span class="glyphicon glyphicon-remove"></span>',
                         className: "login-product-detail",
                         onComplete:function(){
                        	 _AAData.popupPageName= ACC.config.signinpathingPageName;
                        	 _AAData.popupChannel= ACC.config.myaccountpathingChannel;
                			 	try {
                				    	 _satellite.track('popupView');
                		        } catch (e) {} 
                		        ACC.global.singoverlayOncompleteData(e);
                		        setTimeout(function(){
                		        	$('.login-product-detail').eq(1).css({opacity: 1, display: 'none'}).fadeIn();
                		        }, 300)
                         },
                     });
            });
        },
        
    hidecspSignInOverlay : function(){
    	$(document).on("click", ".js-login-to-buy", function(e){
			e.preventDefault();
              sessionStorage.setItem("loginToBuyATC",$(this).data("prod-code"));
                 ACC.colorbox.open("", {
                     html: $("#signinId-hidecsp").html(),
                     width: "1320px",                    
                     close:'<span class="glyphicon glyphicon-remove"></span>',
                     className: "hidecsp-overlay",
					 height: "420px",
                     onComplete:function(){
            		        ACC.global.singoverlayOncompleteData(e);
                     }
                 });
        });
    },
      hardscapePDPLink: function(){
        $(document).on("click", ".hardscape_overlay_Pdp", function(e){
            e.preventDefault();
            var hardscape_productCode= $(".col-xs-12 .brand-itemnumber").text().trim();
			var hardscape_productName= $(".pdp-productname").text().trim().slice(0,85);
			var hardscape_imagePath= $(".image-inner-wrapper img").attr('src');
            ACC.global.hardscapesOverlay(hardscape_imagePath,hardscape_productName,hardscape_productCode);
        });
    },
    carouselIndicators: function(type){
        let target = $('#recommendedProductBanner').nextAll('.carousel-indicators').find('span.carouselA');
        if(type=='prev' && target.prev("span").length){
            target.removeClass('carouselA');
            target.prev("span").addClass('carouselA');
        }
        else if(type=='next' && target.next("span").length){
            target.removeClass('carouselA');
            target.next("span").addClass('carouselA');
        }

    },
    carouselIndicatorsPDP: function(type){
        let target = $('#recommendedProductBannerrr1').nextAll('.carousel-indicators').find('span.carouselA');
        if(type=='prevrr1' && target.prev("span").length){
            target.removeClass('carouselA');
            target.prev("span").addClass('carouselA');
        }
        else if(type=='nextrr1' && target.next("span").length){
            target.removeClass('carouselA');
            target.next("span").addClass('carouselA');
        }

    },

    hardscapePLPLink : function(){
        $(document).on("click", ".hardscape_overlay_Plp", function(e){
            e.preventDefault();
            var hardscape_productName=$(this).parents(".product-item-box").find("a.name").text().slice(0,85);
            var hardscape_productCode =$(this).parents(".product-item-box").find("span.item-number-format").text();
            var hardscape_imagePath= $(this).parents(".product-item-box").find("a.thumb img").attr('src');
            ACC.global.hardscapesOverlay(hardscape_imagePath,hardscape_productName,hardscape_productCode);
        });
    },

    hardscapesOverlay:function(hardscape_imagePath,hardscape_productName,hardscape_productCode){
            ACC.colorbox.open("", {
	            html: $(".hardscape_content").html(),
	            width: "710px",
	            className:"hardscape__overlay",
	            close:'<span class="icon-close"></span>',
	            onComplete:function(){
                    $(".hardscape_product_id").html(hardscape_productCode);
                    $(".hardscape_box_img img").attr('src', hardscape_imagePath);
                    $(".hardscape_product_name").html(hardscape_productName);
                    
	            	$(document).on("click",".hardscapeContinueShopping", function(e){
	                    ACC.colorbox.close();
	                });
	            	
	            	$(document).on("click",".btn-hardscape-first", function(e){
	            		
	            		if(digitalData.pfm)
	                	{
	                	  delete digitalData.pfm;
	                	  delete digitalData.pfmdetails;
	                	}
	            		
	            		var onClickPageName= $(".siteonepagename").val();
	            		
	            		digitalData.eventData={
				    		    linktype:"Hardscapes page",
				                linkName:"Submit Question to Expert",
				                onClickPageName:onClickPageName
				            }
				      
				       try {
				    	   _satellite.track('linkClicks');
				    	   window.open("/en/contactus/hardscapes", '_blank');
						}catch(e){} 
						
	                    
	                });
	            },
            
         });  
    },
    
    
    uomdropdownwidth:function(){
        let dropwidth=$(".pdpRedesignuom-sticky").width();
        $(".uom-dropdown-option").width(dropwidth);

    }, 
    dropDownOpenClose(elem, status, dropdownTag) {
        let target = $(elem);
        let offTarget = $("." + dropdownTag + "-option");
        if((dropdownTag="uom-dropdown")&& (screen.width<480) ){

            ACC.global.uomdropdownwidth();
        }
        if($(".uom-dropdown-button").parent('.pdpRedesignuom-sticky') && (screen.width>600)){
            let newwidth=$(".price-detail").width();
            $('.pdpRedesignuom-sticky').children('.uom-dropdown-option').width(newwidth-3);
        }
        let targetText = target.find("." + dropdownTag + "-text:first").text();
        if (status == "open") {
            target.attr("onclick", "ACC.global.dropDownOpenClose(this,'close','" + dropdownTag + "')");
            target.addClass("dropdown-open");
            $("[data-" + dropdownTag + "]").each(function () {
                let optionElem = $(this);
                let optionVal = optionElem.data(dropdownTag);
                if (optionVal == targetText) {
                    optionElem.hide();
                } else {
                    optionElem.show();
                }
            });
            offTarget.slideDown();
        }
        else {
            target.attr("onclick", "ACC.global.dropDownOpenClose(this,'open','" + dropdownTag + "')");
            target.removeClass("dropdown-open");
            offTarget.slideUp();
        }
    },
    
    dropDownSelection(elem, dropdownTag) {
        let target = $(elem);
        let targetBtn = $("." + dropdownTag + "-button");
        let uomVal = target.data("value");
        /* if($(".mobile-pdp-sticky").length && $(".mobile-pdp-sticky").is(":visible")) {
            ACC.productDetail.updatePriceForUOMDropdownSelection(target);
        } */
        $('[id="inventoryUomId"]').each(function(){$(this).val(uomVal)});
        $('[id="inventoryUOMIDVal"]').each(function(){$(this).val(uomVal)});
        $('[id="inventoryQty"]').each(function(){$(this).val(target.data("inventory"))});
        if($('#pdpAddtoCartInput').length) {
            ACC.product.checkMinQuantity($('#pdpAddtoCartInput'),1);
        }
        targetBtn.attr("onclick", "ACC.global.dropDownOpenClose(this,'open','" + dropdownTag + "')");
        targetBtn.removeClass("dropdown-open");
        $('.custom-dropdownselect').hide();
        $("." + dropdownTag + "-text").html(target.data(dropdownTag));
        $("." + dropdownTag + "-text1").html(target.data("retail-price"));
        $("." + dropdownTag + "-option").slideUp();
        if (ACC.global.wWidth < 1240) {
            ACC.global.findSelectedUom(uomVal);
        }
    },
    findSelectedUom: function(uomVal){
        if(uomVal != $(".selected-uom-item").data("inventoryid")){
            $(".multipleUomItem[data-inventoryid='" + uomVal + "']").trigger("click");  
        }
    },
    openCloseAccordion(elem, status, num, accordionTag, icon, keepAll) {
        let target = $(elem);
        if (!target.hasClass("disabled")) {
            let iconOpen = (icon && icon != "" && icon != "undefined") ? icon.split(",")[0] : 'plus';
            let iconClose = (icon && icon != "" && icon != "undefined") ? icon.split(",")[1] : 'minus';
            if (status == "open") {
                $("." + accordionTag + "-data-" + num).slideUp();
                target.removeClass("" + accordionTag + "-open");
                target.addClass("" + accordionTag + "-close");
                target.attr("onclick", "ACC.global.openCloseAccordion(this,'close', " + num + ",'" + accordionTag + "','" + icon + "', " + keepAll + ")");
                target.find(".glyphicon-" + iconClose).addClass("glyphicon-" + iconOpen).removeClass("glyphicon-" + iconClose);
            }
            else {
                if (!keepAll) {
                    let offTarget = $("." + accordionTag + "-open");
                    let offNum = offTarget.data("acconum");
                    $("." + accordionTag + "-data-" + offNum).slideUp();
                    offTarget.removeClass("" + accordionTag + "-open");
                    offTarget.attr("onclick", "ACC.global.openCloseAccordion(this,'close',  " + offNum + ",'" + accordionTag + "','" + icon + "', " + keepAll + ")");
                    offTarget.find(".glyphicon-" + iconClose).addClass("glyphicon-" + iconOpen).removeClass("glyphicon-" + iconClose);
                }
                $("." + accordionTag + "-data-" + num).slideDown();
                target.removeClass("" + accordionTag + "-close");
                target.addClass("" + accordionTag + "-open");
                target.attr("onclick", "ACC.global.openCloseAccordion(this,'open',  " + num + ",'" + accordionTag + "','" + icon + "', " + keepAll + ")");
                target.find(".glyphicon-" + iconOpen).addClass("glyphicon-" + iconClose).removeClass("glyphicon-" + iconOpen);
            }
        }
    },
       
    signInOverlay: function(){
    	$(document).on("click", ".signInOverlay", function(e){
    			e.preventDefault();
                     ACC.colorbox.open("", {
                         html: $("#signinId").html(),
                         width: "1200px",
                         close:'<span class="glyphicon glyphicon-remove"></span>',
                         className: "login-product-detail",
						 height: "420px",
                         onComplete:function(){
                        	 _AAData.popupPageName= ACC.config.signinpathingPageName;
                        	 _AAData.popupChannel= ACC.config.myaccountpathingChannel;
                			 	try {
                				    	 _satellite.track('popupView');
                		        } catch (e) {} 
                			 
                		        ACC.global.singoverlayOncompleteData(e);
                		        setTimeout(function(){
                		        	$('.login-product-detail').eq(1).css({opacity: 1, display: 'none'}).fadeIn();
                		        }, 300)
                         },
                         
                     });
                     
           
            });


        },
        
        singoverlayOncompleteData:function(e){
        	var url=e.currentTarget.href;
       	 	var windowLocation=window.location.href;
       	 	if(url != null ){
	       		 if(url.includes("account-dashboard") || url.includes("savedList") || url.includes("pay-account-online") || url.includes("buy-again") || url.includes("orders") || url.includes("accountPartnerProgram"))
	     	       {
	     	    	  $.cookie("urllogin", url, { path: '/' });
	     	       }
				  
	     	      else
	     	      {
	     	    	  $.cookie("urllogin",null, { path: '/' });
	     	      }
     		 }
     	 else
     	    {
     		 $.cookie("urllogin",null, { path: '/' });
     	    }
       	 
       	 if( $.cookie("urllogin")==null)
       	    {
       		  	$.cookie("urllogin",windowLocation, { path: '/' });                      		
       	    }	 
       	 var j_username=$.cookie("j_username");
     	   	 if(j_username!=undefined)
     	     	{
                  $('#cboxLoadedContent #usernamePopup').val(j_username);  
                  $('#cboxLoadedContent #rememberPopup').prop('checked', true);  
     		    }
        	
        },
    getUserFirstName : function(){
        $(document).ready(function(){
            var userFirstName = $("#userfname").val();
            if (userFirstName != '' || userFirstName != null){
                var j_username=$.cookie("j_username");
                var userfirstname=$.cookie("newfirstname");
                if(j_username!=undefined && userfirstname==undefined && (userFirstName != "" || userFirstName != null) ){
                    $.cookie("newfirstname", userFirstName, { expires: 365 , path:'/', secure: true });
               }
            }

        });

    },
  updateShipToCall: function (unitId)
    {
        loading.start();
        $.ajax({
            type: 'GET',
            url: ACC.config.encodedContextPath + '/change-ship-to',
            cache: false,
            dataType: "json",
            data: {"unitId":unitId},
            success: function()
            {
                if($(".page-accountOverviewPage").length){
                    
                    window.location.href= ACC.config.encodedContextPath +"/my-account/account-overview?accountId="+ unitId;
                }
                else {
                    location.reload();
                }
            }
        });
    },
    checkConsent: function () {
        let cookie_consent = $.cookie("user_cookie_consent");
        let targetGUID = _AAData.GUID ? _AAData.GUID : '';
        let target = document.getElementById("cookieNotice");
        let targetCookieNoticeShow = localStorage.getItem("cookieNotice" + targetGUID);
        if (target) {
            if (targetCookieNoticeShow || (cookie_consent && cookie_consent != "")) {
                target.style.display = "none";
            }
            else {
                target.style.display = "block";
                localStorage.setItem("cookieNotice" + targetGUID, true);
            }
        }
    },
    pageIdSetforOrder: function () {
        $(document).on("click", ".account-list-group .account-pageid", function () {
			var pageId = $(this).data("key");
			if(pageId){
			$.cookie("accountPageId", pageId, { path: '/' });
			sessionStorage.setItem("activeTab", $(this).data("active"));
			}

		});
    },
 
 //Category Ajax call Start
    
    getAllCategory: function(){
		
		$.ajax({
            type: 'GET',
            url: ACC.config.encodedContextPath + '/c/getCategoryDataAll',
            cache: false,
            dataType: "json",
            success: function(data)
            {
               ACC.global.generateCategoryList(data);
            }
        });
	},
	
	generateCategoryList: function(data){
		var targetHTML = "";
		var pageName = $(".pagename").val();
		var catObj = data.subCategories;
		
		targetHTML += '<input id="pageType" type="hidden" value="'+ pageName +'" />';
		
		if(catObj){
		targetHTML += '<ul class="L1CategoryHeader margin0 padding0 full-height scroll-bar-5">';
		var navL1Cat = $.trim($('#navL1Cat').html());
		var navL2Cat = $.trim($('#navL2Cat').html());
		var navL3Cat = $.trim($('#navL3Cat').html());
		var navL2Images = $.trim($('#navL2Images').html());
		
		var nearbyStoresToggle = ($("#nearbyStoresToggle").val() || '');
		var hardscapeFeatureSwitch = ($("#hardscapeFeatureSwitch").val() || '').split(',');
		var catCode = ["SH13","SH14","SH12","SH11","SH16","SH17","SH15","SH18"];
		
		catObj = catObj.map(function(cObj){
			cObj.sequence = catCode.findIndex((x) => x == cObj.code)
			
			return cObj;
		});
		
		catObj = catObj.sort(function(a,b){return a.sequence - b.sequence});
		
		
		var hardScapeUrl = '/?q=%3Arelevance&viewtype=All&inStock=on&nearby=on&selectedNearbyStores='+nearbyStoresToggle;
		//L1 Category
		$.each(catObj,function(indx,obj){
			
			if(obj.productCount != null && obj.productCount > 0){
			var x = navL1Cat.replace(/{{name}}/ig, obj.name).replace(/{{code}}/ig, obj.code).replace(/{{url}}/ig, obj.url);
			
			var subCat = '';
			
			//L2 Category
			obj.subCategories = obj.subCategories.sort(function(a,b){return a.sequence - b.sequence});
			
			$.each(obj.subCategories,function(indx,obj2){
				
				var urlL2 = obj2.url;
				if(hardscapeFeatureSwitch.includes(obj2.code))
				{
					urlL2 += hardScapeUrl;
				}
				
				if(obj2.productCount != null && obj2.productCount > 0){
				var y = navL2Cat.replace(/{{L1code}}/ig, obj.code).replace(/{{code}}/ig, obj2.code).replace(/{{url}}/ig, urlL2)
				.replace(/{{name}}/ig, obj2.name).replace(/{{havechildren}}/ig, !!obj2.subCategories.length);
				}
				subCat += y;
				
				//L3 Category
				obj2.subCategories = obj2.subCategories.sort(function(a,b){return a.sequence - b.sequence});
				
				$.each(obj2.subCategories,function(indx,obj3){
					
					var urlL3 = obj3.url;
					if(hardscapeFeatureSwitch.includes(obj3.code))
					{
						urlL3 += hardScapeUrl;
					}
					
					if(obj3.productCount != null && obj3.productCount > 0){
					var z = navL3Cat.replace(/{{L2code}}/ig, obj2.code).replace(/{{code}}/ig, obj3.code).replace(/{{url}}/ig, urlL3)
					.replace(/{{name}}/ig, obj3.name);
					}
					
					subCat += z;
				
				});
				
				var imgaltText = '';
				
				if(obj2.marketingBannerList){
					var arrImgs = Array.isArray(obj2.marketingBannerList) ? obj2.marketingBannerList : [obj2.marketingBannerList];
					$.each(arrImgs,function(indx,objImg){
						if(objImg.altText != null)
						{
							imgaltText = objImg.altText;
						}
						else{
							imgaltText = "#";
						}
				
						var bImg = navL2Images.replace(/{{L2code}}/ig, obj2.code).replace(/{{imageUrl}}/ig, objImg.url).replace(/{{url}}/ig, imgaltText)
						.replace(/{{name}}/ig, obj2.name).replace(/{{havechildren}}/ig, !!obj2.subCategories.length);
						
						subCat += bImg;
					});
					
				}
				
			});
			x = x ? x.replace(/{{L2Cat}}/ig, subCat) : "";
			targetHTML += x;
			}
		});
		
		targetHTML += '</ul>';
		}
		
		$(".nav-categories-ajx").append(targetHTML);
	},
	
	//Category Ajax call End
	
    updateConsent: function () {
        var consent = "Approved";
        ACC.global.deleteCookie('user_cookie_consent');
        ACC.global.setCookie('user_cookie_consent', 1, 1);
        document.getElementById("cookieNotice").style.display = "none";
        $.ajax({
            type: 'GET',
            url: ACC.config.encodedContextPath + '/consentForm',
            cache: false,
            dataType: "json",
            data: { "consent": consent },
            success: function (res) {
                if (res == "success") {
                    $(".addonbottompanel").hide();
                }
                //location.reload();
            }
        });
    },
    disableImageRightClick : function() {
        $('img').on('contextmenu', function(e){e.preventDefault()});
    },
    pdfClicks : function() {
        $('[dtm^=pdf-]').on('click', function(){
            try{
                _AAData.fileName = $(this).attr('dtm').split("pdf-")[1];
            } catch(e) {console.log("analytics: filename exception", e);}
        });
    },
    
    disableClickToCall : function() {
        if ($(window).width() > 1023 && !navigator.userAgent.match(/iPad/i)) {       // if width is less than 600px
            $('a[href^=tel]').on('click',function(e){
                e.preventDefault();
            });
        }
    },
    flyoutIpad : function() {
        $('.L1CatLinks').on('touchstart', function(e) {
            var touched = $(this).closest('.L1Categories').find('.L2Categories').is(':visible');
            $(this).attr('touched', touched);
        }).on('click', function(e) {
            if ($(this).attr('touched') == 'false') {
                e.preventDefault();
            }
        });

        $('.L2CatLinks').on('touchstart', function(e) {
            var touched = $(this).closest('.L3category').find('.L3categoryli').is(':visible');
            $(this).attr('touched', touched);
        }).on('click', function(e) {
            if ($(this).attr('touched') == 'false') {
                e.preventDefault();
            }
        });
    },

    carouselClickEvent : function() {
        $('.carousel-indicators li').click(function(e){
            e.stopPropagation();
            $('#siteoneHomepageBanner').carousel($(this).data('slide-to')-1);
        });
        function realignRotatingCarousal(){
        if(screen.width>=1024){
        	$("#siteoneHomepageBanner .img-container img").height($(".top-promo-banner-slot").height());
        }
        }

        var topImgLen = $("#siteoneHomepageBanner .img-container img").length,
            counter = 0;
        function incrementTopImageCounter() {
            counter++;
            if (counter === topImgLen) {
                realignRotatingCarousal();
            }
        }
        $("#siteoneHomepageBanner .img-container img").each(function () {
            if (this.complete) {
                incrementTopImageCounter()
            }
            else {
                this.addEventListener('load', incrementTopImageCounter, false);
            }
        });
        
        $(window).resize(function () {
            realignRotatingCarousal();
            ACC.global.headreResize();
        });
        if($(".desktop-carousal .item").length == 1){
        	$(".desktop.carousel-indicators").hide();
        }
        if($(".mobile-carousal .item").length == 1){
        	$(".mobile.carousel-indicators").hide();
        }
        $('.category-tile-prev').on("click", function(e){
            ACC.global.carouselIndicators('prev')
                        
                    });
        $('.category-tile-next').on("click", function(e){
            ACC.global.carouselIndicators('next')
            
                    });
    },
     carouselClickEventPDP : function() {
        $('.carousel-indicators li').click(function(e){
            e.stopPropagation();
            $('#siteoneHomepageBanners').carousel($(this).data('slide-to')-1);
        });
        function realignRotatingCarousal(){
        if(screen.width>=1024){
        	$("#siteoneHomepageBanners .img-container img").height($(".top-promo-banner-slot").height());
        }
        }

        var topImgLen = $("#siteoneHomepageBanners .img-container img").length,
            counter = 0;
        function incrementTopImageCounter() {
            counter++;
            if (counter === topImgLen) {
                realignRotatingCarousal();
            }
        }
        $("#siteoneHomepageBanners .img-container img").each(function () {
            if (this.complete) {
                incrementTopImageCounter()
            }
            else {
                this.addEventListener('load', incrementTopImageCounter, false);
            }
        });
        
        $(window).resize(function () {
            realignRotatingCarousal();
            ACC.global.headreResize();
        });
        if($(".desktop-carousal .item").length == 1){
        	$(".desktop.carousel-indicators").hide();
        }
        if($(".mobile-carousal .item").length == 1){
        	$(".mobile.carousel-indicators").hide();
        }
        $('.category-tile-prevrr1').on("click", function(e){
            ACC.global.carouselIndicatorsPDP('prevrr1')
                        
                    });
        $('.category-tile-nextrr1').on("click", function(e){
            ACC.global.carouselIndicatorsPDP('nextrr1')
            
                    });
    },

    ndspage: function(){
        $(document).ready(function(){
        	if(digitalData.pfm)
        	{
        	  delete digitalData.pfm;
        	  delete digitalData.pfmdetails;
        	}
            $(document).on("click", "#viewpdf", function(){
                digitalData.eventData=   {
                    CTAname: "ndsPDF",
                }
                try {
                    _satellite.track('ndsCTAClick');
                } catch (e) {}
            });

            $(document).on("click", "#getapp", function(){
                digitalData.eventData= {
                    CTAname: "ndsAPP",
                }
                try {
                    _satellite.track('ndsCTAClick');
                } catch (e) {}
            });
        });


        $(document).ready(function(){
            $(document).on("click", ".title-name", function(){
            	if(digitalData.pfm)
            	{
            	  delete digitalData.pfm;
            	  delete digitalData.pfmdetails;
            	}
                var productName=$(this).text();
                var productHeading=$(this).closest(".column3").find(".product-title").text();
                var productPosition=productHeading.trim();
                digitalData.eventData= {
                    productName: productName,
                    productPosition: productPosition,
                }
                try {
                    _satellite.track('ndsProductClick');
                } catch (e) {}
            });
        });

    },

    footerCurrentYear: function() {
    	document.getElementById("current_year").innerHTML = new Date().getFullYear();    
    },
    
    
    analyticProductFindingMethod :function(){
    	
    	if ($('.page-detailsSavedListPage').length > 0){
			 try {
					_AAData.method = "list";
					_AAData.methodMetaData =$("h1").text();
				}catch(e){}
		 }
    	
		 if ($('.page-detailsAssemblyPage').length > 0){
			 try {
					_AAData.method = "assembly";
					_AAData.methodMetaData =$(".assembly-name").text();
				}catch(e){}
		 }
		 
		 
		 if ($('.page-productDetails, .template-pages-category-categoryLandingPage, .page-productGrid ').length > 0) {
			 
			 if (window.location.href.indexOf("cid") > -1){
					    if (window.location.href.indexOf("icid") > -1){
					    	var methodData= window.location.href.split("icid=");
							 var methodDataVal= methodData[1];
					    	try {
								_AAData.method = "internal campaign";
								_AAData.methodMetaData = methodDataVal;
							}catch(e){} 
					    } 
					    else {
					    	var methodData2= window.location.href.split("cid=");
							var methodDataVal2= methodData2[1];
					    	try {
								_AAData.method = "external campaign";
								_AAData.methodMetaData = methodDataVal2;
							}catch(e){} 
					    }
				}
		 }
			 
			 if ($('.buy-it-again-container').length > 0) {
				 try {
						_AAData.method = "previous orders";
						_AAData.methodMetaData = "buy-again";
					}catch(e){} 
			 }
			 
			 if ($('.page-quickOrderPage').length > 0) {
				 try {
						_AAData.method = "previous orders";
						_AAData.methodMetaData = "quick-order";
					}catch(e){} 
			 }
			 
			 if ($('.page-order').length > 0) {
				 try {
						_AAData.method = "previous orders";
						_AAData.methodMetaData = "order-detail";
					}catch(e){} 
			 }
			 
			 if ($('.page-orderConfirmationPage').length > 0) {
				 try {
						_AAData.method = "previous orders";
						_AAData.methodMetaData = "order-confirmation";
					}catch(e){} 
			 }
			 
		 
		 
		 if ($('.page-searchGrid').length > 0){
			 
			 try {
					_AAData.method = "internal search";
					_AAData.methodMetaData = _AAData.productSearchTerm;
				}catch(e){}
			 
		 }
		 
		 
		 
    },
    
    analyticsExternalReferrer: function(){
    	if($(".referrermethod").val()== "naturalReferrer"){
	    	try {
	    	_AAData.method = "natural referrer";
	    	_AAData.methodMetaData = $(".referrermethoddata").val();
	    	}catch(e){}
    	}
    	
    	if($(".referrermethod").val()== "browse"){
	    	try {
	    	_AAData.method = "browse";
	    	_AAData.methodMetaData = "navigation";
	    	}catch(e){}
	    }
    	
    	if ($('.page-productDetails').length > 0) {
            let inventorystockPDP = $('.custom-dropdown-label').data("stock");
            let inventorymulPDP =  $('.custom-dropdown-label').data("inventory");
			 
			 if (window.location.href.indexOf("isrc") > -1){
					    	var methodData= window.location.href.split("isrc=");
							 var methodDataVal= methodData[1];
					    	try {
								_AAData.method = "browse";
								_AAData.methodMetaData = methodDataVal;
								
								//Product Recommendation
								
								digitalData.pfm = "recommendation";
							 	digitalData.pfmdetails = localStorage.getItem("pageNameRecc");
							}catch(e){} 
    	
			 }
		
		try {
			if($('.loginstatus').val() != ''){
				var PDPproductprice=$(".simple-product-pdp__value--retail-price .yourPriceValue").text().replace(/[^0-9\.]/g, '');
			}
			else{
				var PDPproductprice=$(".simple-product-pdp__value--retail-price .price").text().replace(/[^0-9\.]/g, '');
			}
			if( inventorystockPDP < inventorymulPDP){
                _AAData.productStockStatus = $(".js-pdp-store-detail-1 ").text().trim();
                _AAData.productprice = PDPproductprice; 
            }else{
                _AAData.productStockStatus = $(".js-pdp-store-detail").text().trim();
                _AAData.productprice = PDPproductprice;
            }
	    	}catch(e){}
    	}
   	},
    
     analyticsPageIndentifier :function(){
		let currentPage;
		let pageName = $(".pagename").val();
		if($('.loginstatus').val() == ''){
			sessionStorage.setItem("loginstatus", false);
		}
		
    	if ($('.loginstatus').val() != '' && (!sessionStorage.getItem("loginstatus") || sessionStorage.getItem("loginstatus") == 'false')){
			currentPage = 'login-success';
			sessionStorage.setItem("loginstatus", true);
        }
  		else if ($('.page-searchGrid').length > 0){
			currentPage = 'search-product-search';
  		 }
  		else if ($('.page-productDetails').length > 0){
  			currentPage = 'prod-details';
  		 }
  		else if ($('.template-pages-category-categoryLandingPage').length > 0 && _AAData.eventType=="search-product"){
			currentPage = 'search-product-category';
  		 }
  		  

         if(pageName == "HomePage"){
            _AAData.page.pageName = "v2: home";
            _AAData.pathingChannel = "home";
            _AAData.pathingPageName = "v2: home"; 
         }
         
             if (currentPage) {
                 try {
                     _satellite.track(currentPage);
                 } catch (e) { }

             }
             if (fireSearchLandingPage) {
                 try {
                     _satellite.track("search landing page");
                 } catch (e) { }
             }
             try {
                 _satellite.track("pageload");
             } catch (e) { }

             if ($(".page-siteOneCheckoutPage").length > 0) {
                 let checkAco = ["pickup-box", "delivery-box", "shipping-box"];
                 let defaultAco = ["pickup", "delivery", "shipping"];
                 ACC.mixedcartcheckout.checkoutAccordian(checkAco, defaultAco);//mixed cart check
                 if ($('.orderTypeFormSubmit, .addShippingMainFormSubmit, .addShippingFormSubmit').is(":visible") == true) {//non-mixed cart check
                     console.log('fullfillmentDetails');
                     try {
                         _satellite.track("fullfillmentDetails");
                     } catch (e) { }
                 }
             }
             if (_AAData.page.pageName == "Checkout:Order Confirmation") {
                 var productData = [];
                 var itemCount = 0;
                 $(".item__image-wrapper").each(function () {
                     productData.push({
                         id: $(this).find(".item__code").eq(0).text(),
                         name: $(this).find(".item__name-wrapper").text(),
                         category: ""
                     });
                     itemCount++;
                 });
                 rdt('track', 'Purchase', {
                     "currency": "USD",
                     "itemCount": itemCount,
                     "transactionId": _AAData.orderNumber,
                     "value": _AAData.totalPurchaseAmount,
                     "products": productData
                 });
             }
            if($('.multipleUomContents').length > 0){
                $("#inventoryQty").val($('.multipleUomItem').eq(0).data('inventorymultiplier'));
                ACC.product.checkMinQuantity($('#pdpAddtoCartInput'),1);
            }
            if($('.inventoryDownload').is(":visible") && localStorage.getItem("inventorydownload") == 'true'){
                var URL = "/my-account/downloadNurseryInventoryCSV?storeId=" + $('#storeId').val();
                window.location = URL;
                localStorage.setItem("inventorydownload", false);
            }
            if($('#plpcategoryName').is(":visible")){
                var plpCategoryList = '';
                if($('#breadcrumbName3').val() != undefined){
                  plpCategoryList = $('#breadcrumbName1').val() +' '+$('#breadcrumbName3').val();
                }
                else{
                    if($('#breadcrumbName2').val() != undefined){
                    plpCategoryList = $('#breadcrumbName1').val() +' '+$('#breadcrumbName2').val();
                    }
                    else{
                        plpCategoryList =  $('#breadcrumbName1').val();
                    }
                }
                if($('#breadcrumbName1').val() == undefined){
                  plpCategoryList = $('#breadcrumbName0').val();
                  //document.getElementById('plpcategorynameAnalyticss').innerText = plpCategoryList;
                }
                document.getElementById('plpproductcountviewDesktop').innerText = $('#plpproductCount').val();
                document.getElementById('plpcategorynameAnalytics').innerText = plpCategoryList;
                if(!$('.category-tiles-heading').is(":visible")){
                    $('#plpproductcountviewMobile').removeClass('plpfilterbycategoryMobile');
                }
                
            }
            if($('.product-variant-table').length > 0){
                $("#addToListMobile").hide();
            }    
            $(document).ready(function(){
				if($('.productDetailsHeighlights').is(":visible")){
					if($('.infoDiv').is(":visible")){
						$('.viewSpecSheet').removeClass('hidden');
					}
					if($('.productDetailsHighlights-VariantSection').is(":visible")){
						$('.productDetailsHeighlights').addClass('productDetailsHeighlightsVariant');
					}
				}
                if($('.js-login-to-buy').is(":visible") && $('#mobile-addcart-qty-section').is(":visible")){
                    $('#mobile-addcart-qty-section').addClass('countsection-margin');
                    if($('.22').is(":visible")){
                        $('.22').addClass('login-to-buy-simpleproduct');
                    }
                }
                //PLP Toggle
                if (($(".plpviewtype").val() == "card")) {  
                   /* $('.card-toggle').addClass("active");
                    $('.list-toggle').removeClass("active");*/
                    var list = document.querySelectorAll('.qty-section-productcode');
                    for (let count = 0; count < list.length; count++) {
                        var qty = $(list[count]).val();
                        if(!(($('#listPageAddToCart_'+qty).is(":visible")) || ($('#orderOnlineATC_'+qty).is(":visible")))){                             
                            $('.cardview-errorpart-'+qty).remove();
                       }
                        sessionStorage.setItem("baseUOM_"+qty, 'true');
                    }   
                }
             else {   
                   /* $('.list-toggle').addClass("active");
                    $('.card-toggle').removeClass("active");*/
                    var list = document.querySelectorAll('.qty-section-productcode');
                    for (let count = 0; count < list.length; count++) {
                        var qty = $(list[count]).val();
                        /*if(!(($('#listPageAddToCart_'+qty).is(":visible")) || ($('#orderOnlineATC_'+qty).is(":visible")))){                             
                             $('.qty-section_'+qty).remove();
                        }*/
                        sessionStorage.setItem("baseUOM_"+qty, 'true');
                    }     
                } 
            });
    },
    analyticSearchEmpty: function(){

   	 $('#searchBoxempty').submit(function() {
   		   if($('.search-empty-input').val() == ''){
   		      $(".errormessage").removeClass("hidden");
   		      $('.search-empty-input').addClass("input-error");
   		      return false;
   		   }
   		 });
            $('.pseudo-search-icon').click(function(e) {
                if(!$(e.target).hasClass('search-empty-input')){
                    $('#searchBoxempty').submit(); 
                }                          
            });
             $('.search-empty-input').blur(function(){
                         if( !$(this).val() ) {
                             $(".errormessage").removeClass("hidden");
                             $(this).addClass("input-error");
                         }
                      else{
                        $(".errormessage").addClass("hidden");
                        $(this).removeClass("input-error");
                      }
               });
   },
   
    analyticCuratedplp: function(){
        if($('body').hasClass('template-pages-layout-siteOneCuratedPlpLayout')){
        	
        	if(digitalData.pfm)
        	{
        	  delete digitalData.pfm;
        	  delete digitalData.pfmdetails;
        	}
        	
            _AAData.pfm= "curated-plp";
            _AAData.pfmDetails= $(".pagename").val();
            
            
            
            $('.curated-analytics').on("click",function() {
                ACC.global.productCutatedplp = true;
            });
            $('.js-login-to-buy').click(function() {

                digitalData.eventData={
                     linkName: 'product-grid',
                     linkType: 'login to buy',
                    onClickPageName: $(".pagename").val()
                     }
               
                try {
                    _satellite.track('linkClicks');
                 }catch(e){} 
                 
               });
            $('.curatedplp-list').on("click",function() {
                ACC.global.productCutatedplpList = true;
            });
            $('.curatedplp-branch').on("click",function() {
                CutatedplpName = $(this).parent(".store").find(".storeName").text();
                curatedplpLinkName = 'visit branch page'
                ACC.global.productCutatedplpLink = true;
            });
            $('.curatedplp-promo').on("click",function() {
                CutatedplpName = $(this).parent(".promo-wrapper").find(".headline2").text();
                curatedplpLinkName = 'Contact us'
                ACC.global.productCutatedplpLink = true;
            });
            $('.curated-promo-btn').on("click",function() {
                CutatedplpName = 'button'
                curatedplpLinkName =$(this).text().trim();
                ACC.global.productCutatedplpLink = true;
            }); 
            $('.logInToSeeYourPrice').on("click",function() {
                CutatedplpName = 'product-grid'
                curatedplpLinkName ='login to se your price';
                ACC.global.productCutatedplpLink = true;
            });
            $('.name').on("click",function() {
                CutatedplpName = 'product-grid'
                curatedplpLinkName =$(this).parents(".product-item-box").find("a.name").text().slice(0,85).trim();
                ACC.global.productCutatedplpLink = true;
            });
            $('.thumb').on("click",function() {
                CutatedplpName = 'product-grid'
                curatedplpLinkName =$(this).parents(".product-item-box").find("a.name").text().slice(0,85).trim();
                ACC.global.productCutatedplpLink = true;
            });
            $('.banner-click').on("click",function() {
                CutatedplpName = 'banner'
                curatedplpLinkName =$('img', this).attr('title');
                ACC.global.productCutatedplpLink = true;
            });
            $('.nav-click').on("click",function() {
                CutatedplpName = 'ribbon-menu'
                curatedplpLinkName =$(this).text().trim();
                ACC.global.productCutatedplpLink = true;
            });
            $('.cplp-pagination').on("click",function() {
                ACC.global.productCutatedplpPagination = true;
            });
        }
            
    },

	analyticProductRecommendation: function(){
		   
		   $('.recomm-analytics').click(function() {
			 
			   localStorage.setItem("pageNameRecc",  _AAData.page.pageName);
		       
		       digitalData.pfm = "recommendation";
			   digitalData.pfmdetails = _AAData.page.pageName;
		       
		       
		       digitalData.eventData={
		    		    linktype:linkType,
		                linkName:linkName,
		                onClickPageName:onClickPageName
		            }
		      
		       try {
		    	   _satellite.track('linkClicks');
				}catch(e){} 
				

				
			  });
		   
		   $('.btn-carousel_addtocart').on("click",function() {
			   
			   ACC.global.productAnalyticsRecAtc = true;
		   });
		   	
		   },
		   
	analytichardscapes : function(){
	
		var onClickPageName= $(".siteonepagename").val();
		
		    	$('.askexp-anlytcs').click(function() {
		    		
		    		if(digitalData.pfm)
		        	{
		        	  delete digitalData.pfm;
		        	  delete digitalData.pfmdetails;
		        	}

				       digitalData.eventData={
				    		    linktype:"Hardscapes page",
				                linkName:"Ask an Expert",
				                onClickPageName:onClickPageName
				            }
				      
				       try {
				    	   _satellite.track('linkClicks');
						}catch(e){} 
						
					  });
		    	
		    },	   

	hardScapeDatePicker: function(){
		$(".projectDatepicker").datepicker();
		  $('.pointer .icon-calendar').click(function() {
		    $(".projectDatepicker").focus();
		  });
		 
		$('#typeOfCustomer').on('change', function() {
            var typeofcustomer= $(this).val();
            let labelParent = $('.contractor-mycompany-name');
            labelParent.children("label").html(labelParent.data("companyname"));
            if (typeofcustomer == ACC.config.contactUsidentityoption1) {
                labelParent.children("label").html(labelParent.data("contractorname"));
                labelParent.show();
            }
            else if (typeofcustomer == ACC.config.contactUsidentityoption2) {
                labelParent.show();
            }
            else {
                labelParent.hide();
            }
        });
		 
	},
	showRecommendedProduct: function(){
		var pageId = $("#pageId").val();
		if(pageId=='productGrid' || pageId=='searchGrid' || pageId=='searchEmpty' || pageId.includes('CategoryLandingPage') || pageId=='cartPage' || 
			pageId.includes('orderConfirmationPage') || pageId=='productDetails' || pageId=='siteonehomepage'){
		var requestData;
		if (pageId=='productGrid' || pageId.includes('CategoryLandingPage')){
			var categoryId = $("#categoryCodePR").val();
			if(!categoryId){
				var url = window.location.href;
				var spliturl = url.split("/");
				var intCat = spliturl[spliturl.length-1];
				var splitIntCat = intCat.split("?");
				var categoryId = splitIntCat[0];
			}
			var productcode = '';
            var productCount=0;
            $(".productCodeValue").each(function(){
                if(productCount<15){
                    if(productCount==0){
                        productcode=productcode+$(this).val();
                    }
                    if(productCount>0){
                        productcode=productcode+'|'+$(this).val();
                    }
                }
                productCount++;
            });
			requestData={"placementPage":"categoryPage","categoryId":categoryId,"productId":productcode};
		}
		if (pageId.includes('orderConfirmationPage')){
			var orderId = $(".orderId").text();
			requestData= {"placementPage":"purchaseCompletePage","orderId":orderId};
		}
		if (pageId=='productDetails'){
			var productcode = $("#productcode").val();
			requestData= {"placementPage":"itemPage","productId":productcode};
		}
        if (pageId=='siteonehomepage'){
			requestData= {"placementPage":"homePage"};
		}		
		if (pageId=='searchGrid' || pageId=='searchEmpty'){
            var searchTerm = $(".highlightedSearchText").text();
            if(pageId=='searchEmpty'){
                var searchTerm = $(".emptySearchTerm").val();
            }
			var productcode = '';
            var productCount=0;
            $(".productCodeValue").each(function(){
                if(productCount<15){
                    if(productCount==0){
                        productcode=productcode+$(this).val();
                    }
                    if(productCount>0){
                        productcode=productcode+'|'+$(this).val();
                    }
                }
                productCount++;
            });
			requestData= {"placementPage":"searchPage","searchTerm":searchTerm,"productId":productcode};
		}
		if (pageId=='cartPage'){
			var productcode = '';
            var productCount=0;
            $(".productCodeValue").each(function(){
                    if(productCount==0){
                        productcode=productcode+$(this).val();
                    }
                    if(productCount>0){
                        productcode=productcode+'|'+$(this).val();
                    }
                productCount++;
            });
			requestData= {"placementPage":"cartPage","productId":productcode};
		}
		$(document).ready(function(){
				$.ajax({
				type: 'GET',
				url: ACC.config.encodedContextPath + '/recommendedProducts',
				cache: false,
				dataType: "json",
				data: requestData,
				success: function(response) {
						if (pageId=='productGrid' || pageId=='searchGrid' || pageId=='searchEmpty' || pageId.includes('CategoryLandingPage')){
							$("#RecommendedProductSlotCategory").html(response.recommendedProductsLayer);
						}
						if (pageId.includes('orderConfirmationPage')){
							$("#RecommendedProductCheckoutSlot").html(response.recommendedProductsLayer);
						}
						if (pageId.includes('cartPage')){
							$("#RecommendedProductCartSlot").html(response.recommendedProductsLayer);
						}
						if (pageId=='productDetails'){
							$("#RecommendedProductSlotPDPTop").html(response.recommendedProductsLayer1);
							$("#RecommendedProductSlotPDPBottom").html(response.recommendedProductsLayer2);
						}
                        if (pageId=='siteonehomepage'){
							$("#RecommendedProductSlotHomePageTop").html(response.recommendedProductsLayer1);
							$("#RecommendedProductSlotHomePageBottom").html(response.recommendedProductsLayer2);
						}
						ACC.global.getRecommendedProducts(response);
		            }
	        	});
			});
		}
	},
    getRecommendedProducts: function(res) {
	    if(res) {
            ACC.global.createCarouselIndicators();
            ACC.global.createCarouselIndicatorsPDP();
            $ajaxCallEvent = true;
            ACC.product.enableAddToCartButton();
            ACC.product.bindToAddToCartForm();
            ACC.global.carouselClickEvent();
            ACC.global.carouselClickEventPDP();
            ACC.product.checkCSP();

            $('.btn-carousel_addtocart').on("click",function() {
               ACC.global.productAnalyticsRecAtc = true;
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
	    }
	},
    createCarouselIndicators: function(){
		let target = $("#recommendedProductBanner");
		let scrollMultiplier = Math.round(target.outerWidth() / target.find(".category-tile-item").outerWidth());
        let productLen ;
        if(target.find('#carouselProduct').val()<=4)		
        {
            productLen = 1;
        }
        else{
            productLen = Math.ceil((target.find('#carouselProduct').val() / scrollMultiplier));
        }
		
        productLen = (productLen == 0)? 1 : productLen;       
		for(let i=0; i < productLen ; i++){
		target.next(".carousel-indicators").append('<span data-target="#siteoneHomepageBanner" data-slide-to="'+ (i+1) +'" class="'+ (i==0? "carouselA" : "") +'"></span>');
		}
	},
    createCarouselIndicatorsPDP: function(){
		let target = $("#recommendedProductBannerrr1");
		let scrollMultiplier = Math.round(target.outerWidth() / target.find(".category-tile-item").outerWidth());
        let productLen ;
        if(target.find('#carouselProduct').val()<=4)		
        {
            productLen = 1;
        }
        else{
            productLen = Math.ceil((target.find('#carouselProduct').val() / scrollMultiplier));
        }
		
        productLen = (productLen == 0)? 1 : productLen;       
		for(let i=0; i < productLen ; i++){
		target.next(".carousel-indicators").append('<span data-target="#siteoneHomepageBanners" data-slide-to="'+ (i+1) +'" class="'+ (i==0? "carouselA" : "") +'"></span>');
		}
	},
    carouselTileMoveFlag: false,
    carouselTileAutoMove: [],
	partnerCarouselIndicators: function (parentId, targetId, autoMove) {
        let target = $(parentId);
        let itemWidth = target.find(".category-tile-item").outerWidth();
        target.find(".category-tile-item").width(itemWidth);
        let scrollMultiplier = Math.floor(target.outerWidth() / itemWidth);
        let productLen = Math.ceil(target.find('#carouselProduct').val() / scrollMultiplier);
        scrollMultiplier = (ACC.global.wWidth < 1000)? scrollMultiplier : 2;
        let tileWidth = Math.round((target.find(".category-tile-item").eq(scrollMultiplier).length)? target.find(".category-tile-item").eq(scrollMultiplier).position().left-(ACC.global.wWidth < 1000?0:45) : 0);
        productLen = (productLen == 0) ? 1 : productLen;
        for (let i = 0; i < productLen; i++) {
            target.next(".carousel-indicators").append('<span onclick="ACC.global.carouselTileMove(' + (i + 1) + ','  + productLen + ','  + tileWidth + ',\'' + parentId + '\')" data-target="' + targetId + '" data-slide-to="' + (i + 1) + '" class="pointer' + (i == 0 ? " carouselA" : "") + '"></span>');
        }
        target.find(".category-tile-prev").removeClass("category-tile-prev").addClass("carousel-prev").attr("onclick", 'ACC.global.carouselTileMove(1,' + productLen + ','  + tileWidth + ',\'' + parentId + '\')');
        target.find(".category-tile-next").removeClass("category-tile-next").addClass("carousel-next").attr("onclick", 'ACC.global.carouselTileMove(2,' + productLen + ','  + tileWidth + ',\'' + parentId + '\')');
        if(autoMove){
            ACC.global.carouselTileAutoMove = [parentId, 1, productLen];
            setInterval(function(){
                if(ACC.global.carouselTileAutoMove[1] == ACC.global.carouselTileAutoMove[2]){
                    ACC.global.carouselTileAutoMove[1] = 1;
                }
                else{
                    ACC.global.carouselTileAutoMove[1]++;
                }
                $(ACC.global.carouselTileAutoMove[0]).next(".carousel-indicators").find("[data-slide-to='"+ ACC.global.carouselTileAutoMove[1] +"']").trigger("click");
            }, autoMove*1000 );
        }
    },
    carouselTileMove: function (num, slides, tileWidth, parentId) {
        if(!ACC.global.carouselTileMoveFlag){
            ACC.global.carouselTileMoveFlag = true;
            let target = $(parentId);
            let pervNum = (num != 1)? num -1: 1;
            let nextNum = (num != slides)? num + 1 : slides;
            target.animate({scrollLeft: tileWidth * (num - 1)}, 800, function(){ACC.global.carouselTileMoveFlag = false});
            target.find(".carousel-prev").attr("onclick", 'ACC.global.carouselTileMove(' + pervNum + ',' + slides + ','  + tileWidth + ',\'' + parentId + '\')');
            target.find(".carousel-next").attr("onclick", 'ACC.global.carouselTileMove(' + nextNum + ','  + slides + ','  + tileWidth + ',\'' + parentId + '\')');
            target.next(".carousel-indicators").find(".carouselA").removeClass("carouselA");
            target.next(".carousel-indicators").find("[data-slide-to='"+ num +"']").addClass("carouselA");
        }
    },
	passwordShowHideBtn: function(){
		 $('input[type=password][id=j_password]').wrap('<div class="pswrd-container"/>');
		 $('.pswrd-container').append('<span class="glyphicon glyphicon-eye-open showhide-pswrd hidden"></span>');
		 $('.pswrd-container').append('<span class="glyphicon glyphicon-eye-close showhide-pswrd hidden"></span>');
		 
		 $(document).on("input", "input[id=j_password]", function() {
			var ele = $(this).parent(".pswrd-container").find('.showhide-pswrd');
			
			if($(this).val() !== "") {			
				var iconIdx = $(this).attr('type') == 'password' ? 0: 1;
				ele.eq(iconIdx).removeClass("hidden");
			}else {
				ele.addClass("hidden");
				$(this).attr('type', 'password');
			}
		 });
		 		 		 
		 $(document).on("click", ".showhide-pswrd", function (e) {
			 var pwd = $(this).parent(".pswrd-container").find("#j_password");
			 const type = pwd.attr('type') === 'password' ? 'text' : 'password';
			 var ele = $(this).parent(".pswrd-container").find('.showhide-pswrd');
			 
			 if(type === 'text'){
				 ele.addClass("hidden");
				 ele.eq(1).removeClass("hidden");
			 }else{
				 ele.addClass("hidden");
				 ele.eq(0).removeClass("hidden");
			 }
			 
			 pwd.attr('type', type);
		 })
	},
	passwordShowHideBtnOnCreatePasswordPage: function(){
		if($('body').hasClass('page-setPasswordPage') || $('body').hasClass('page-updatePassword')){
			var funAttachEyeIcon = function (targetName){
				$('input[type=password][name='+targetName+']').wrap('<div class="pswrd-container"/>');
				
				var targetParent = $('input[type=password][name='+targetName+']').parent(".pswrd-container");
				
				targetParent.append('<span class="glyphicon glyphicon-eye-open showhide-pswrd pswrdCreatePage hidden"></span>');
				targetParent.append('<span class="glyphicon glyphicon-eye-close showhide-pswrd pswrdCreatePage hidden"></span>');
				
				$(document).on("input", 'input[name='+targetName+']', function() {
					var ele = $(this).parent(".pswrd-container").find('.showhide-pswrd');

					if($(this).val() !== "") {			
						var iconIdx = $(this).attr('type') == 'password' ? 0: 1;
						ele.eq(iconIdx).removeClass("hidden");
					}else {
						ele.addClass("hidden");
						$(this).attr('type', 'password');
					}
				 });

				
			}
			
			funAttachEyeIcon('pwd');
			funAttachEyeIcon('checkPwd');
			
			 $(document).on("click", ".pswrd-container > .pswrdCreatePage", function (e) {
				 var pwd = $(this).parent(".pswrd-container").find('input').first();
				 const type = pwd.attr('type') === 'password' ? 'text' : 'password';
				 var ele = $(this).parent(".pswrd-container").find('.showhide-pswrd');

				 if(type === 'text'){
					 ele.addClass("hidden");
					 ele.eq(1).removeClass("hidden");
				 }else{
					 ele.addClass("hidden");
					 ele.eq(0).removeClass("hidden");
				 }

				 pwd.attr('type', type);
			 });
		}
	},
	throttleFunction: (func, delay)=> {
      let prev = 0;
      return (...args) => {
        let now = new Date().getTime();
        
        if(now - prev>= delay) {
          prev = now;
          return func(...args); 
        }
      }
	},
    //Local Storage value set for inventory download
    gustInventoryDownload(){
        localStorage.setItem("inventorydownload", true);
    },
    getDate: function() {
        const month = ["January","February","March","April","May","June","July","August","September","October","November","December"];
        const d = new Date();
        var cDay = d.getDate();
        var cMonth = month[d.getMonth()];
        var cYear = d.getFullYear();
        return cMonth + " " + cDay + ", " + cYear;
    },
    getTime: function() {
        const d = new Date();
        var hours = d.getHours();
        var minutes = d.getMinutes();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        return hours + ":" + minutes + " " + ampm;
    },
    numberInputEvents: function (ref, min, max, step) {
        let target = $(ref);
        target.prop({ type: "number", min: min, max: max, step: step }).on({ "keydown": ACC.global.numberInputKeyDown, "keyup": ACC.global.numberInputKeyup, "focus": ACC.global.numberInputFocus, "blur": ACC.global.numberInputBlur, "paste": function (e) { e.preventDefault(); } });
    },
    numberInputKeyDown: function (pEvent) {
        let ref = $(pEvent.currentTarget);
        let refVal = ref.prop("value");
        if ((ref.prop("step") == 1 && pEvent.key == ".") || pEvent.key == "e" || pEvent.key == "E" || pEvent.key == "-" || pEvent.key == "+" || (ref.prop("maxlength") <= refVal.length && ((pEvent.keyCode > 47 && pEvent.keyCode < 58) || (pEvent.keyCode > 95 && pEvent.keyCode < 106)))) {
            pEvent.preventDefault();
        }
    },
    numberInputKeyup: function (pEvent) {
        let ref = $(pEvent.currentTarget);
        let refVal = ref.prop("value");
        if (Number(ref.prop("max")) < refVal) {
            ref.val(refVal.slice(0, -1));
        }
    },
    numberInputFocus: function (pEvent) {
        var ref = $(pEvent.currentTarget);
        let refVal = ref.prop("value");
        if (Number(ref.prop("max")) < refVal) {
            ref.val(refVal.slice(0, -1));
        }
        else if (Number(ref.prop("min")) > refVal) {
            ref.val("");
        }
    },
    numberInputBlur: function (pEvent) {
        var ref = $(pEvent.currentTarget);
        let refVal = ref.prop("value");
		if(refVal && refVal != ""){
	        if (Number(ref.prop("max")) < refVal) {
	            ref.val(refVal.slice(0, -1));
	            ref.trigger('blur');
	        }
	        else if (Number(ref.prop("min")) > refVal) {
	            ref.val("");
	            ref.trigger('blur');
	        }
		}
    },
    toggleOffElems: function (e, eElem, className, offTarget) {
        let target = $(eElem);
        let ref = (e) ? $(e) : target;
        if (!ref.hasClass("signInOverlay") && !(ACC.global.wWidth < 1024 && ref.hasClass("active"))) {
            $(".global-categories-btn.active").removeClass("active");
            if (target.is(":visible")) {
                $(eElem + '-overlay').hide();
                target.hide();
                $(eElem + '-button').removeClass(className);;
                ref.removeClass(className);
            }
            else {
                if (eElem == ".global-savedlist-box") {
                    ACC.global.fetchListData();
                    $(".global-savedlist-box .listName").removeAttr("style").next();
                    $(".global-savedlist-box #nameError").empty();
                }
                if (offTarget && $(offTarget).is(":visible")) {
                    $(offTarget).hide();
                    $(".js-document-global-btn.active").removeClass("active");
                }
                if ($(".global-category-section").hasClass("active")) {
                    $(".global-category-section").trigger("click");
                }
                if ($('.language-dropdown').is(":visible")) {
                    $('.language-value').trigger("click");
                }
                if (ACC.global.wWidth < 1024 && $(".js-document-global-box").is(":visible")) {
                    $(".js-document-global-box").hide();
                }
                $(eElem + '-overlay').show();
                target.show();
                ref.addClass(className);
            }
        }
    },
	elemHeightEqual: function(className, wWidth) {
        wWidth = wWidth ? wWidth : 0;
        let target = $(className);
        if (target.length > 1 && ACC.global.wWidth > wWidth) {
            var minHeight = 0;
            target.each(function () {
                let tempHeight = $(this).height();
                $(this).height("auto");
                minHeight = minHeight > tempHeight ? minHeight : tempHeight;
                minHeight = minHeight > $(this).height() ? minHeight : $(this).height();
            });
            target.height(minHeight);
        }
    },
    toggleOffElemsplp: function (e, eElem, className,pCode) {
        let target = $(eElem);
        let ref = (e) ? $(e) : target;
        let dropdownFlag = false;
        let offTarget = $(eElem + "_" + pCode);
            if($(".popup-box-muom").hasClass("active") && !$(".popup-box-muom_"+pCode).hasClass("active")){
                $(".popup-box-muom").hide();
                ref.removeClass("dropdown-open");
                ref.removeClass("muomdropdownopen");
                ref.removeClass(className);
                $('.popup-box-muom').removeClass(className);
                dropdownFlag = true;
                 offTarget.show();
                        ref.addClass(className);
                        $('.popup-box-muom_'+pCode).addClass(className);
                        ref.addClass("muomdropdownopen");
                        ref.addClass("dropdown-open");
                        $(".popup-box-muom.active").addClass("clickactive");
                        
            }
       
            if (target && target.is(":visible") && $('[id="js-btn-muom"]').hasClass('active') && !dropdownFlag) {
                
                    if($('[id="js-btn-muom"]').hasClass('muomdropdownopen')){
                    offTarget.hide();
                    ref.removeClass("dropdown-open");
                    ref.removeClass("muomdropdownopen");
                    ref.removeClass(className);
                    $('.popup-box-muom_'+pCode).removeClass(className);
                    }
                }
                else{
                    if(!dropdownFlag){
                        offTarget.show();
                        ref.addClass(className);
                        $('.popup-box-muom_'+pCode).addClass(className);
                        ref.addClass("muomdropdownopen");
                        ref.addClass("dropdown-open");
                        $(".popup-box-muom.active").addClass("clickactive");
                        dropdownFlag = false;
                    }   
                }
    },
	toggleOffElemsVariantplp: function (e, pCode) {
		//Stock Data
		var codeData;
		var stockMessage;
		var stockCount;
		var stockLocation;
		var htmlCodes;
		for(i=0; i<4; i++){
			htmlCodes = '';
			if($('.variant-codes-cardDropdown-' +pCode)[i] != undefined){
					var codeData =	$('.variant-codes-cardDropdown-' +pCode)[i].value;
					stockMessage = Number($('.stockMessageShown_' +codeData).val());
					stockCount = Number($('.stockCountShown_' +codeData).val());
					stockLocation = $('.stockLocationData_' +codeData).val();
					if(stockMessage == 13 || stockMessage == 16 || stockMessage == 17 || stockMessage == 18 || stockMessage == 27){
						htmlCodes =  '<span class="vdropdownInStock">'+stockCount+'&nbsp;'+ACC.config.vdropdownInStock+'</span>';
					}
					else if(stockMessage == 14){
						htmlCodes =  '<span class="vdropdownInStock">'+stockCount+'&nbsp;'+ACC.config.vdropdownInStock+'&nbsp;<span class="vdropdownForShippingOnly">'+ACC.config.vdropdownForShippingOnly+'</span></span>';
					}
					else if(stockMessage == 15 || stockMessage == 24 || stockMessage == 25 || stockMessage == 26){
						htmlCodes =  '<span class="vdropdownBackorder">-&nbsp;'+ACC.config.vdropdownBackorder+'</span>';
					}
					else if(stockMessage == 19 || stockMessage == 20 || stockMessage == 21 || stockMessage == 28){
						htmlCodes =  '<span class="vdropdownInStockNearby">'+stockCount+'&nbsp;'+ACC.config.vdropdownInStockNearby+'&nbsp;Branch #'+stockLocation+'</span>';
					}
					else if(stockMessage == 22 || stockMessage == 23){
						htmlCodes =  '<span class="vdropdownMoreOnTheWay">!&nbsp;'+ACC.config.vdropdownMoreOnTheWay+'</span>';
					}
					else if(stockMessage == 29 || stockMessage == 30 || stockMessage == 31 || stockMessage == 32){
						htmlCodes =  '<span class="vdropdownNotAvailable">'+ACC.config.vdropdownNotAvailable+'</span>';
					}
					$('.variantDropdownStockSection_' +codeData).html(htmlCodes);
			}
		}
		//Stock Data
		if($(".js-btn-variant").hasClass("active")){
			   $('.popup-box-variant').hide(); 
			   $(".js-btn-variant").removeClass("active");
		}else{
		   var eElem = $('.DropdownClassAction_' +pCode).val();
		   var className = $('.DropdownClassActive_' +pCode).val();
	       let target = $(eElem);
	       let ref = $('.js-btn-variant_' +pCode);
	       let offTarget = $(eElem + "_" + pCode);
	       var variantcount = $('.product-data-variant-count-' +pCode).val();
	       if(variantcount > 4){
			    $('.showmore_' +pCode).removeClass("hidden");
		   }else{
			    $('.showmore_' +pCode).addClass("hidden");
		   }
	       if (target && target.is(":visible") && $('[id="js-btn-muom"]').hasClass('active')) {
	          
	           if($('[id="js-btn-muom"]').hasClass('muomdropdownopen')){
	            offTarget.hide();
	            //ref.removeClass("dropdown-open");
	           }
	        }
	        else{
	           if(target.is(":visible")){
	               //hide 
	               offTarget.hide();
	               ref.removeClass(className);
	               //ref.removeClass("dropdown-open");
	           }
	           else{
	               //show
	               offTarget.show();
	               ref.addClass(className);
	               //ref.addClass("muomdropdownopen");
	               //ref.addClass("dropdown-open");
	           }
	        }
		}
	   },
	   redirectPDP: function(pCode){ 
		     var redirectURL = $('.product-data-variant-redirect-' +pCode).val();
		     window.location.href = redirectURL;
	},
    qtyTotalPriceUpdaterPLP: function(pCode){  
        var inventoryUomId = $('#plpselectedUOM_' +pCode).val();
        var identifier = inventoryUomId+ '-' +pCode
        let target = $('.qtyprice-' +identifier);
        let productCode=target.data("productcode");
        let quantity=$('#productPLPPostQty_' +productCode).val();
        let uomDesc=target.data("uom");
        let viewPage = $(".plpviewtype").val();
        let login = $(".isloggeduser").val(); 
        let uomCode =target.data("desccode"); 

        //let offTarget = $(".popup-box-muom_" +pCode);

     $.ajax({
                 type: 'GET',
                 url: ACC.config.encodedContextPath + '/p/customerpriceforuom',
                 data: {
                     productCode:productCode,
                     quantity:quantity,
                     inventoryUomId:inventoryUomId
                 },
                 success: function(result){
                    if(viewPage == "card"){
                        if(login == "true"){
                            console.log('Loginif:',login); 
                            target.parents().find('.multipleuom-price-update'+'_'+pCode).find('.csp-price-format').html("<span class='check_price'>"+result.totalPrice.formattedValue+"</span><span class='check_price'><br><sup>"+uomCode+"</sup></span>");
                        }
                        else{
                            console.log('Loginelse:',!login); 
                            target.parents().find('.multipleuom-price-update'+'_'+pCode).find('.list-price-format').html("<span class='black-title b-price add_price atc-price-analytics'>"+result.totalPrice.formattedValue+"</span><br><sup>"+uomCode+"</sup>");
                    
                        }
                        target.parents(".multipleuom-plp").next().find('#inventoryUomId').val(inventoryUomId);
                      
                        target.parents().find('.multipleuom-price-update'+'_'+pCode).parents().find('.multiplpuomatlist'+'_'+pCode).find('#inventoryUOMIDVal').val(inventoryUomId);

                        
                    }
                    else{
                        if(login == "true"){
                            console.log('Loginif:',login); 
                            target.parents('.multipleuom-plp').nextAll('.priceSection'+'_'+pCode).find('.csp-price-format').html("<span class='check_price'>"+result.totalPrice.formattedValue+"</span><span class='check_price'><br><sup>"+uomCode+"</sup></span>");
                        }
                        else{
                            console.log('Loginelse:',!login); 
                            target.parents('.multipleuom-plp').nextAll('.priceSection'+'_'+pCode).find('.list-price-format').html("<span class='black-title b-price add_price atc-price-analytics'>"+result.totalPrice.formattedValue+"</span><br><sup>"+uomCode+"</sup>");
                    
                        }
                        target.parents(".multipleuom-plp").nextAll('.atc-section').find('#inventoryUomId').val(inventoryUomId);
                        target.parents('.multipleuom-plp').nextAll('.listSection'+'_'+pCode).find('#inventoryUOMIDVal').val(inventoryUomId);
                        
                    }
                    //offTarget.hide();
                    //target.parents('.uom-wrapper-plp').find('.uom-text-wrapper').html(uomDesc);
                    //target.parents('.uom-wrapper-plp').find('.uom-text-wrapper').removeClass("dropdown-open");
                    //target.parents('.multipleuom-plp'+'_'+pCode).nextAll('.plp-ordermultiplesaddtocart').find('[id="inventoryUomId"]').val(inventoryUomId);
                    $('#plpselectedUOM_' +productCode).val(inventoryUomId);
                    console.log('data success:',result);  
                 },
                 error: function(xhr, ajaxOptions, thrownError) {
                      console.log('data failure');               

                  },


               })

    },
    multipleUomTotalPriceUpdaterPLP: function (elem, pCode, login, uomCode, viewPage) {
        let target = $(elem);
        let productCode = target.data("productcode");
        let inventoryUomId = target.data("inventoryid");
        let quantity = target.data("qty");
        let uomDesc = target.data("uom");
        let offTarget = $(".popup-box-muom_" + pCode);
        let baseUOM = target.data("baseuom");
        sessionStorage.setItem("baseUOM_" + pCode, baseUOM);
        $('.js-btn-muom.active').removeClass("active");
        let inventoryMultiplier = target.data("inventorymultiplier");
        let stockDataSection = (viewPage == "cardView") ? target.closest('.product-item').find('.cardstocksection'): target.closest('.product-item').find('.stock-section') 
        if (stockDataSection.length && stockDataSection.is(":visible")) {
            if (stockDataSection.find(".stock-section-instock").length || stockDataSection.find(".stock-section-instock-nearby").length || stockDataSection.find(".stock-section-outofstock").length) {
                let stockData = target.data("stockdata");
                if (stockData < inventoryMultiplier) {
                    stockDataSection.find(".stock-section-outofstock").prev().addClass("hidden");
                    stockDataSection.find(".stock-section-outofstock").removeClass("hidden");
                    stockDataSection.find('.bg-color').text(0);
                    if($(".hardscapeProd_" + productCode).length && target.closest('.product-item').find(".getAQuoteFlagForB2BUser").val() == "true") {
                        let atcSection = (viewPage == "cardView") ? target.closest('.product-item').find(".card-atc-wrapper-section") : target.closest('.product-item').find('.list-atc-wrapper-section');
                        let getAQuoteSection = target.closest('.product-item').find(".getAQuoteSection");
                        atcSection.addClass('hidden');
                        getAQuoteSection.removeClass("hidden");
                        if((".change-branch-section-"+ productCode).length){
                            let changeBranchSection = (viewPage == "cardView") ? target.closest('.product-item').find(".card-change-branch-section") : target.closest('.product-item').find('.list-change-branch-section');
                            let changeBranchGetAQuoteSection = target.closest('.product-item').find(".changeBranchGetAQuoteSection");
                            changeBranchSection.addClass('hidden');
                            changeBranchGetAQuoteSection.removeClass("hidden");
                         }
                        target.closest('.product-item').find(".plp-warning_info_" + productCode).find('.plp-commonerror').text(ACC.config.changeUOMForAvailability);
                    } else { 
                        target.closest('.product-item').find(".plp-warning_info_" + productCode).find('.plp-commonerror').text(ACC.config.expectDelay);
                    }
                    target.closest('.product-item').find(".plp-warning_info_" + productCode).removeClass("hidden");
                    if (target.closest('.product-item').find("#plp-commonerror-" + productCode).is(":visible")) {
                        target.closest('.product-item').find("#plp-commonerror-" + productCode).addClass("hidden");
                    }
                } else {
                    stockDataSection.find(".stock-section-outofstock").prev().removeClass("hidden");
                    stockDataSection.find(".stock-section-outofstock").addClass("hidden");
                    stockDataSection.find('.bg-color').text(Math.floor(Number(stockData) / Number(inventoryMultiplier)));
                    target.closest('.product-item').find(".plp-warning_info_" + productCode).addClass("hidden");
                    if($(".hardscapeProd_" + productCode).length && target.closest('.product-item').find(".getAQuoteFlagForB2BUser").val() == "true") {
                        let atcSection = (viewPage == "cardView") ? target.closest('.product-item').find(".card-atc-wrapper-section") : target.closest('.product-item').find('.list-atc-wrapper-section');
                        let getAQuoteSection = target.closest('.product-item').find(".getAQuoteSection");
                        atcSection.removeClass('hidden');
                        getAQuoteSection.addClass("hidden");
                        if((".change-branch-section-"+ productCode).length){
                            let changeBranchSection = (viewPage == "cardView") ? target.closest('.product-item').find(".card-change-branch-section") : target.closest('.product-item').find('.list-change-branch-section');
                            let changeBranchGetAQuoteSection = target.closest('.product-item').find(".changeBranchGetAQuoteSection");
                            changeBranchSection.removeClass('hidden');
                            changeBranchGetAQuoteSection.addClass("hidden");
                         }
                    }
                }
            }
        }
        $.ajax({
            type: 'GET',
            url: ACC.config.encodedContextPath + '/p/customerpriceforuom',
            data: {
                productCode: productCode,
                quantity: quantity,
                inventoryUomId: inventoryUomId
            },
            success: function (result) {
                if (viewPage == "cardView") {
                    if (login == "true") {
                        target.parents().find('.multipleuom-price-update' + '_' + pCode).find('.csp-price-format').html("<span class='check_price'>" + result.uomPrice.formattedValue + "</span><span class='check_price'><br><sup>" + uomCode + "</sup></span>");
                        if(result.retailPrice && result.retailPrice.formattedValue) {
                            target.parents().find('.multipleuom-price-update' + '_' + pCode).find('.list-price-format').find(".black-title.b-price.add_price.atc-price-analytics").text(result.retailPrice.formattedValue);
                        }
                    } else {
                        target.parents().find('.multipleuom-price-update' + '_' + pCode).find('.list-price-format').html("<span class='black-title b-price add_price atc-price-analytics'>" + result.uomPrice.formattedValue + "</span><br><sup>" + uomCode + "</sup>");
                    }
                    target.parents(".multipleuom-plp").next().find('#inventoryUomId').val(inventoryUomId);
                    target.parents().find('.multipleuom-price-update' + '_' + pCode).parents().find('.multiplpuomatlist' + '_' + pCode).find('#inventoryUOMIDVal').val(inventoryUomId);
                    target.closest('.product-item').find(".productInventoryUOMID_" + productCode).val(inventoryUomId);
                } else {
                    if (login == "true") {
                        target.parents('.multipleuom-plp').nextAll('.qty-price-section').find('.priceSection' + '_' + pCode).find('.csp-price-format').html("<span class='check_price'>" + result.uomPrice.formattedValue + "</span><span class='check_price'><br><sup>" + uomCode + "</sup></span>");
                    } else {
                        target.parents('.multipleuom-plp').nextAll('.qty-price-section').find('.priceSection' + '_' + pCode).find('.list-price-format').html("<span class='black-title b-price add_price atc-price-analytics'>" + result.uomPrice.formattedValue + "</span><br><sup>" + uomCode + "</sup>");
                    }
                    target.parents(".multipleuom-plp").nextAll('.list-atc-section').find('.atc-section').find('#inventoryUomId').val(inventoryUomId);
                    target.parents('.multipleuom-plp').nextAll('.list-atc-section').find('.listSection' + '_' + pCode).find('#inventoryUOMIDVal').val(inventoryUomId);
                    target.closest('.product-item').find(".productInventoryUOMID_" + productCode).val(inventoryUomId);
                }
                offTarget.hide();
                target.parents('.uom-wrapper-plp').find('.uom-text-wrapper').html('<span title="' + uomDesc + '">' + uomDesc + '</span>');
                target.parents('.uom-wrapper-plp').find('.uom-text-wrapper').removeClass("dropdown-open");
                $('.popup-box-muom_' + pCode).removeClass('active');
                target.parents('.multipleuom-plp' + '_' + pCode).nextAll('.plp-ordermultiplesaddtocart').find('[id="inventoryUomId"]').val(inventoryUomId);
                $('#plpselectedUOM_' + productCode).val(inventoryUomId);
                ACC.product.checkMinQuantityPLP($('#productPLPPostQty_' + productCode), 'dropdown');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log('data failure');
            },
        });
    },
    variantSelector: function(elem, pCode, selectedVariantCode, inventoryUomId){  
        let targetElement = $(elem);
        let target = $(".variant-dropdown-option_" +selectedVariantCode);
        var variantOptionName = $('.variantname_' +selectedVariantCode).val();
        var variantOptionAvailability = $('.variantnameavailability_' +selectedVariantCode).val();
        var selectedVariantItem = $('.variantitemnumber_' +selectedVariantCode).val();
        var selectedVariantItemDesc = $('.variantitemdesc_' +selectedVariantCode).val();
        let offTarget = $(".popup-box-variant_" +pCode);
        $('.js-btn-variant.active').removeClass("active");
        offTarget.hide();
        let checkboxEle = target.closest(".product-item").find(".custom-checkbox");
        $(checkboxEle).each(function() {
            $(this).prop("checked", false).trigger('change');
        });
		let checkboxList = target.closest(".product-item").find(".custom-checkboxlistplp");
		       $(checkboxList).each(function() {
		           $(this).prop("checked", false).trigger('change');
		       });
        if(variantOptionAvailability == 'true'){
			   var splitValues = variantOptionName.split('|');
        	   var selectedData = splitValues[1];
		}
     	else{
			   var selectedData = variantOptionName;
		 }
         $('.popup-box-variant_'+pCode).find('.variant-dropdown-option.selected').removeClass('selected');
         $('.variant-dropdown-option_'+selectedVariantCode).addClass('selected');	
        target.parents('.variant-wrapper-plp').find('.variant-text-wrapper').find('.span-text').html(selectedData);
        target.parents('.variant-wrapper-plp').find('.variant-text-wrapper').removeClass("dropdown-open");
        $('.productVariantSection-' +pCode +'-' +selectedVariantCode).prev('.productInventoryUOMID_' + selectedVariantCode).val(inventoryUomId);    
        //Hide show
        $('.productVariantSection-' +pCode +'-' +selectedVariantCode).removeClass("hidden");
        $('.productInventoryUOMID_' + selectedVariantCode).prevAll(".plp-item-code").first().val(selectedVariantCode);
        $('.productInventoryUOMID_' + selectedVariantCode).prevAll(".plp-item-number").first().val(selectedVariantItem);
        $('.productInventoryUOMID_' + selectedVariantCode).closest(".product-item").find('#requestQuoteButtonItemnumber').val(selectedVariantItem);
        $('.productInventoryUOMID_' + selectedVariantCode).closest(".product-item").find('#requestQuoteButtonDesc').val(selectedVariantItemDesc);
        var variantData = $('.variant-codes-' +pCode).val();
        var variantList = variantData.split(',');
        var variantId;
        var Id;
        for (let count = 0; count < variantList.length; count++) {
			  if(count == 0){
				 Id = variantList[count].split('[');
				 variantId = Id[1];
			  }
			  else if(count == (variantList.length - 1)){
				 Id = variantList[count].split(']');
				 variantId = Id[0];
			  }else{
				 var variantId = variantList[count];
			  }
              if(variantId.trim() != selectedVariantCode){
				   $('.productVariantSection-' +pCode +'-' +variantId.trim()).addClass("hidden");  
                   $('.productVariantSection-' +pCode +'-' +variantId.trim()).prev('.productInventoryUOMID_' + variantId.trim()).val('');
                   $('.productInventoryUOMID_' + variantId.trim()).prevAll(".plp-item-code").first().val('');
                   $('.productInventoryUOMID_' + variantId.trim()).prevAll(".plp-item-number").first().val('');
			  }                                                       
        }   
    },
    dropDownOpenCloseuom(elem, status, dropdownTag) {
        let target = $(elem);
        let offTarget = $("." + dropdownTag + "-option");
        if((dropdownTag="uom-dropdown")&& (screen.width<480) ){

            ACC.global.uomdropdownwidth();
        }
        let targetText = target.find("." + dropdownTag + "-text:first").text();
        if (status == "open") {
            target.attr("onclick", "ACC.global.dropDownOpenClose(this,'close','" + dropdownTag + "')");
            target.addClass("dropdown-open");
            $("[data-" + dropdownTag + "]").each(function () {
                let optionElem = $(this);
                let optionVal = optionElem.data(dropdownTag);
                if (optionVal == targetText) {
                    optionElem.hide();
                }
                else {
                    optionElem.show();
                }
            });
            offTarget.slideDown();
        }
        else {
            target.attr("onclick", "ACC.global.dropDownOpenClose(this,'open','" + dropdownTag + "')");
            target.removeClass("dropdown-open");
            offTarget.slideUp();
        }
        target.removeClass("selected-uom-items");
    },
    plpMobileViewResponse: function(){
        if (window.matchMedia("(max-width:" + screenXsMax + ")").matches) {
            let pageName = $(".pagename").val();
            let viewType = $("#ViewType").val();
            if(pageName == "Product Grid"  || pageName == "Search Results"){
                mobileView = "mobileView";  
                $.ajax({
                    type: 'POST',
                    url: ACC.config.encodedContextPath + "/search/setView",
                    dataType: "json",
                    data: {
                        mobileView:mobileView,
                        viewType: viewType
                    },
                    success: function () {
                        console.log(mobileView);
                    },
                })   
            }   
        } 
    },
    //PLP-Toggle Function
    plpToggleCard: function(){     
        sessionStorage.setItem("plp-view", "card");
        ACC.global.plpToggleFunctionality();
    },
    plpToggleList: function(){
        sessionStorage.setItem("plp-view", "list");
        ACC.global.plpToggleFunctionality();
    },
    plpToggleFunctionality: function(){    
            var view = sessionStorage.getItem("plp-view");   
            $.ajax({
                url: ACC.config.encodedContextPath + "/search/setView?viewType=" +view,
                method: 'POST',
                success: function (response) {
                    window.location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // log the error to the console
                    console.log("The following error occured: " + textStatus, errorThrown);
                }
            });
        },
    toggleGlobalElems: function (e, eElem, offTarget) {
        let target = $(eElem);
        let ref = $(e);
        if (!ref.hasClass("signInOverlay")) {
            if (target.is(":visible")) {
                target.slideUp();
                if (offTarget) {
                    $(offTarget).show();
                }
            }
            else {
                target.slideDown();
                if (offTarget) {
                    $(offTarget).hide();
                }
            }
            ref.hide();
        }
 },
 //SEO 
 addOgTags: function(){
    var currentUrl = window.location.pathname;
    const match = currentUrl.match(/^\/(?:([a-z]{2})\/)?articles\/([^\/]+)\/([^\/]+)\/?/);
    if(match){
        let cTitle = $('[name="article-content-title"]').val();
        let cDesc = $('[name="article-content-description"]').val();
        let cImageUrl = $('[name="article-content-url"]').val();
        let cLocale = $('[name="article-content-locale"]').val();
        var ogMetaTags = $(
            '<meta property="og:title" content="'+ cTitle +'">' +
            '<meta property="og:description" content="'+ cDesc +'">' +
            '<meta property="og:image" content="'+ cImageUrl +'">' +
            '<meta property="og:url" content="'+ currentUrl +'">' +
            '<meta property="og:type" content="article">' +
            '<meta property="og:site_name" content="SiteOne Landscape Supply">' +
            '<meta property="og:locale" content="'+ cLocale +'"></meta>'
        );         
        var existingMeta = $('head meta[name="robots"]');
        if(existingMeta.length){
            existingMeta.after(ogMetaTags);
        }
        else{
            $("head").append(ogMetaTags);
        }   
    }
},
bindNumberInputEvent: function(){
    $(".js-number-input-event-bind").each(function(){
        ACC.global.numberInputEvents('#' + $(this).attr("id"), 1, 999999, 1);
    });
}
};

ERRORMSG = {
    global : {
        firstname     : ACC.config.enterFirstName,
        lastname      : ACC.config.enterLastName,
        email         : ACC.config.dashboardValidEmail,
        phone         : ACC.config.validPhoneNumber,
        city          : ACC.config.enterCity,
        state         : ACC.config.selectState,//enter not select
        zip           : ACC.config.validZipPostal,
        address       : ACC.config.addressInfo,
        country       : "Please enter a country.",
        company       : ACC.config.enterCompanyName,
        title         : ACC.config.selectTitle,
        parentunit    : ACC.config.selectShipTo,
        shippingStates: ACC.config.shippingStates,
    },
    invalid : {
        email       : ACC.config.dashboardValidEmail
    },
    atcButtonClickEvent: 'body'
};

$('.faq-panel a').on('click',function () {
    $('.faq-panel').removeClass('active');
    $(this).parents('.faq-panel').addClass('active');
    $(this).parent().addClass('active');
    var url=$(this).attr("href")
    if($(url).hasClass('in'))
    {

        $(this).find(".arrowDown").hide();
        $(this).find(".arrowRight").show();
        $(this).parents('.faq-panel').removeClass('active');
        $(this).parents('.panel-title').removeClass('active');
    }
    else{
        $('.faq-sec a span.arrowDown').hide();
        $('.faq-sec a span.arrowRight').show();
        $(".faq-accordion .panel-title").removeClass("active");
        $(".faq-accordion .panel-collapse.in").removeClass("in");
        $(this).find(".arrowRight").hide();
        $(this).find(".arrowDown").show();

    }


});
$(".product_view").on('click',function (){
    var currDiv=$(this).siblings(".product_info.hidden").first();
    if(currDiv !=undefined){
        $(currDiv).removeClass("hidden");
        $(this).attr('href','#'+$(currDiv).attr("id"));
    }
    else{
        $(this).hide();

    }
});

$(".promotion-category").on('click',function(e){
    if($(this).find(".disable-category").length==0){
        $(".promotion-category").removeClass("active-promotion-category");
        $(this).addClass("active-promotion-category");
        e.preventDefault();
    }

});

$(document).bind('cbox_complete', function(){
	$.colorbox.resize();
});

///   window load function for sign in overlay
$( window ).on( "load", function() {
    var loginErrorMsg = $("#loginErrorMsg").val();
    let creditCodeError = $("#creditCodeError");
    let localizedBranch = $("#localizedBranch");
    let trackBranch = $("#trackBranch").val();
    let localizedstore = $("#localized-store").val();
    if (creditCodeError.length) {
        ACC.colorbox.open($("#logo-svg").html(), {
            html: creditCodeError.html(),
            width: "900px",
            escKey: false,
            overlayClose: false,
            close: '',
            className: "text-center",
            onComplete: function (e) { },
            onClosed: function (e) { window.location.href = ACC.config.encodedContextPath + "/"; }
        });
    }
    else if (localizedBranch.length && trackBranch != "" && localizedstore != "" && trackBranch != localizedstore) {
        setTimeout(function(){
        ACC.colorbox.open('', {
            html: localizedBranch.html(),
            width: "600px",
            escKey: true,
            overlayClose: true,
            close: '<span class="glyphicon glyphicon-remove"></span>',
            className: "text-center localized-branch",
            onComplete: function (e) { }
        });
    }, 500)
    }
    else if(loginErrorMsg != null && loginErrorMsg != "" && loginErrorMsg !== undefined )
    {
        if(loginErrorMsg != " " && sessionStorage.getItem("logintobuy")==="false")
        {
            ACC.colorbox.open("", {
                html: $("#signinId").html(),
                width: "1200px",
                escKey: false,
                overlayClose: false,
                close:'<span class="glyphicon glyphicon-remove"></span>',
                className: "login-product-detail",
                onComplete: function(e) {
                	setTimeout(function(){
    		        	$('.login-product-detail').eq(1).css({opacity: 1, display: 'none'}).fadeIn();
    		        }, 300)
    		        
                    $("#cboxClose").click(function(){
                        window.location = "/";
                    });
                },
            });
        }
		else if(loginErrorMsg != " " && sessionStorage.getItem("logintobuy")==="true"){
              ACC.colorbox.open("", {
            	html: $("#signinId-hidecsp").html(),
            	width: "1320px",                    
            	close:'<span class="glyphicon glyphicon-remove"></span>',
                escKey: false,
                overlayClose: false,
                className: "hidecsp-overlay",
				height: "420px",
                onComplete: function(e) {
                    $("#cboxClose").click(function(){
                        window.location = "/";
                    });
                },
				onClosed: function(e){
					$("#colorbox").removeClass("hidecsp-overlay");
				}
            });
		}
    }
    /// link to anchor tag on load
    if(location.hash) location.href = location.hash;
    
    if($(".page-linktopayverification").length){    //link 2 pay loading
        setTimeout(function () {
            loading.stop();
        }, 2000);
        $(".step-body").css("opacity", 1);
    }
});


$(".promotionName").on('click',function(){

    $(".promotionName").parents("li").removeClass("active");
    $(this).parents("li").addClass("active");


});
$(".promotion-category").first().addClass("active-promotion-category");
$(".promotionName").first().parents("li").addClass("active");


$(function() {
    $('.faq-links').each(function() {
        if($(this).find('a').attr('href') == window.location.pathname) {
            $(this).addClass('active-link');
        }
    });
});

$('.inspireSortOrder').on('change', function() {
    var sortOrder = $(this).val();
    console.log(sortOrder);
    var url = ACC.config.encodedContextPath + "/inspire?sortOrder="+sortOrder;
    window.location.href = url;

});

$(document).ready(function() {
    var text_max;
    if($('#contactUsMessage').length!=0){
        var text_max= $('#contactUsMessage').val();
    }
    else {
        var text_max = 200;
    }
    if($('#message').length!=0)
    {
        var text_length = $('#message').val().length;
        var text_remaining = text_max - text_length;
    }
    if(text_length!=0)
    {
        $('#message_feedback').html(ACC.config.remaining +text_remaining + ACC.config.characters);
        if(text_remaining==0)
        {
            $('#message_feedback').css("color","red");
        }
    }
    else
    {
        $('#message_feedback').html(ACC.config.remaining +text_max + ACC.config.characters);
    }

    $('#message').on('keyup',function () {
        var text_length = $('#message').val().length;
        var text_remaining = text_max - text_length;
        if(text_remaining==0)
        {
            $('#message_feedback').css("color","red");
        }
        else
        {
            $('#message_feedback').css("color","#999");
        }
        $('#message_feedback').html(ACC.config.remaining + text_remaining + ACC.config.characters);
    });
		if($('#User_Agent').val()==="SiteOneEcomApp") {
			$(".hideelementforapp").addClass("hidden");
		}
});

loading = {
    start : function() {
        if($('#floatBarsG').length) {
            return;
        }
        var loadingHtml = ['<div id="floatBarsG">',
            '<div id="floatBarsG_1" class="floatBarsG"></div>',
            '<div id="floatBarsG_2" class="floatBarsG"></div>',
            '<div id="floatBarsG_3" class="floatBarsG"></div>',
            '<div id="floatBarsG_4" class="floatBarsG"></div>',
            '<div id="floatBarsG_5" class="floatBarsG"></div>',
            '<div id="floatBarsG_6" class="floatBarsG"></div>',
            '<div id="floatBarsG_7" class="floatBarsG"></div>',
            '<div id="floatBarsG_8" class="floatBarsG"></div>',
            '</div>',
            '<div id="loadingOverlay"></div>'].join('');
        $('body').append(loadingHtml);
    },
    stop: function() {
        $('#floatBarsG, #loadingOverlay').remove();
    }
}

function loginSubmitOnce(){
    $("form[action*='j_spring_security_check']").on("submit", function(e){
        var submitButton = $(this).find('button');
        submitButton.attr('disabled', 'disabled');
        setTimeout(function(){
            submitButton.removeAttr('disabled');
        }, 30000);
    });
}

function addToCartLTB(){
	if ("true" === $.cookie("loginToBuyCk") && $(".js-atc-"+sessionStorage.getItem("loginToBuyATC")).is(":disabled")===false){
        $(".js-atc-"+sessionStorage.getItem("loginToBuyATC")).click();
		$.cookie("loginToBuyCk", "", { path: '/' });
      }
}
$(document).ready(function() {
    loginSubmitOnce();
    setTimeout(function(){loginSubmitOnce();}, 750);
    setTimeout(function(){addToCartLTB();}, 850); 
});
$(document).ready(function() {
    if($('body').hasClass('page-siteonehomepage') && sessionStorage.getItem('updateAccount')==='true'){
        sessionStorage.removeItem('updateAccount') 
        if(screen.width>=1024){
            setTimeout(function(){
                $('.headerWelcome').click()
                setTimeout(function(){
                    $('#headerShipToBtn').click()   
                },500)
            },500)
        }
        else{
            setTimeout(function(){                
                $('.mobile__nav__row--btn').trigger('click')
                setTimeout(function(){                   
                    $('.js_nav__link--drill__down').click()   
                },500)
            },500)
        
        }       
    }
    
})
$(document).on("click", "#cboxLoadedContent #loginsubmitPopup", function (e) {
    sessionStorage.setItem("logintobuy", $(e.target).closest("#colorbox").hasClass("hidecsp-overlay"));
    var url = sessionStorage.getItem("checkoutlogin");
    if (url != null) {
        $('#loginForm').attr('action', sessionStorage.getItem('checkoutlogin'));
        sessionStorage.removeItem('checkoutlogin');
    }
});
$(document).on("click", ".signInOverlay", function(){
    loginSubmitOnce();
    setTimeout(function(){loginSubmitOnce();}, 750);
});

$(document).ready(function() {
    /** Curatedplp Horizondal Scroll start**/
    if(document.getElementById("desktophorizondalscroll") != null && document.getElementById("desktophorizondalscroll") != undefined){
        if(screen.width <= 639){
          var curatedplphsmobile = 10;
          while(curatedplphsmobile > 0){
            const element = document.getElementById("desktophorizondalscroll"); 
            if(element != null && element != undefined){
            element.remove();
            curatedplphsmobile = curatedplphsmobile - 1;
            }
            else{
              break;
            }
          }
        }
      }
        /** Curatedplp Horizondal Scroll End**/
  if(screen.width>=1024 && $(".bottom-banner .bottom-video-container").length){
  $(".bottom-banner-img").css("height",$(".bottom-banner .bottom-video-container").height())
  }
  var getURL = window.location.href;
  var urlCase = getURL.toLowerCase();

  // App suggestion
  var isIOS = ( navigator.userAgent.match(/(iPad|iPhone)/i) ? true : false );
  var isAndroid = navigator.userAgent.indexOf("Android") > -1;
       

  if((isIOS || isAndroid) && sessionStorage.getItem("showAppSuggestion") !=="false" && $('#User_Agent').val()!="SiteOneEcomApp"
	  && $.cookie('isMobileApp') != "true"){
      if ($.cookie('banner_shown') == null && urlCase.indexOf('cid=') == -1) {
          $.cookie('banner_shown', 'yes', { expires: 14, path: '/' });
          $('.native-banner').removeClass("hide");
          $(document).on("click", ".app-download", function () {
            var anchorTag = $(this).find('a');
              if (isIOS) {
                var iosURL = $('#appStoreUrlId').val();
                anchorTag.attr('href', iosURL);
              } else if (isAndroid) {
                var androidURL = $('#playStoreUrlId').val();
                anchorTag.attr('href', androidURL);
              }
              anchorTag[0].click();
              sessionStorage.setItem("showAppSuggestion", "false");
              $('.native-banner').addClass("hide");
          });
      }
                }

});

$(document).ready(function() {
    document.cookie = "clientURL=" + window.location.href;
    !function (w, d) {
        if (!w.rdt) {
            var p = w.rdt = function () {
                p.sendEvent ? p.sendEvent.apply(p, arguments) : p.callQueue.push(arguments)
            };
            p.callQueue = [];
            var t = d.createElement("script");
            t.src = "https://www.redditstatic.com/ads/pixel.js", t.async = !0;
            var s = d.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(t, s)
        }
    }(window, document);
    rdt('init', 'a2_eag8k29zexvh');
    rdt('track', 'PageVisit');
});

//Custom tool tip

$( function()
{
    var targets = $( '[rel~=custom-tooltip]' ),
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
        $( window ).resize( init_tooltip );
 
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
 
//Custom tool tip ends

// Function to fire click tracking url on click of recommended products
function clickTracking(obj){
	var clickTrackingURL = $(obj).find('#clickTrackingURL').val();
	if(clickTrackingURL!=''){
		$.ajax({
			async: false,			
			url: clickTrackingURL
		});
	}
	return true;
}
