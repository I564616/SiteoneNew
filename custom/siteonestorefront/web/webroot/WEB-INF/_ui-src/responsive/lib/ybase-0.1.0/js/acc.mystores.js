ACC.mystores = {

	_autoload: [
		"removeMyStore",
		"bindConfirmButton",
        "bindHeaderStoreOverlay",
        ["bindGeoLocatedStore", $('#User_Agent').val()!="SiteOneEcomApp"],
		    "productToggle",
        "makeMyBranchClick",
        "defaultBranch",
        "bindfooterSearchStore",
		"bindNearbyOverlayPDP",
		"bindNearbyOverlayPLP",
		"bindNearbyOverlayData",
		"bindNearbyOverlayCartEntry",
		"nearbyBranchPopupCall",
       	["bindpopupStoreOverlay", $('#User_Agent').val()!="SiteOneEcomApp"]
	],
	selectPLPProduct:"",
	selectBaseCode:"",
	isPLPPage:"false",
	inStockFilterflag: true ,
	
	bindfooterSearchStore:function(){
		 $(document.body).on('click','.js-footer-search-store',function(e){
			try {
				_AAData.storeLocatorTerm= $(".footer-store-value").val();
				_AAData.storeLocatorMethod ="Search-Text";
				_AAData.eventType="search-location";
				_satellite.track("dtm-event-search");
			}catch(e){}
		}) 
	},
 callBackGeo:function(element){
	 $(element).html(ACC.config.divClass);
		$('.headerLabel').css('display','none');
		$('.line1').css('display','none');
		$('.town').css('display','none');
		

		$(document).ready(function() {
			if ($(".page-savedListPage,.page-assemblyPage,.page-cartPage,.page-quickOrderPage,.page-productDetails,.page-productGrid, .template-pages-category-categoryLandingPage, .page-searchGrid").length > 0){
				
				$(".main-container").click(function(event){
					event.preventDefault();
					ACC.mystores.failedGeoPopUp();
				   });
			     }
			});
		
		
		$(document).ready(function() {
				var showPopup = false;
				if($.cookie("gls") == null)
				{
					showPopup = true;
				}
				
				if(($.cookie("gls") != null))
				{
					showPopup = false;
				}
				
				if(showPopup)
				{
					if ($(".page-savedListPage,.page-assemblyPage,.page-cartPage,.page-quickOrderPage,.page-productDetails,.page-productGrid, .template-pages-category-categoryLandingPage, .page-searchGrid").length > 0){
						ACC.mystores.failedGeoPopUp();
					}
				}
		});
	 
 },
	failedGeoPopUp:function(){
		$.ajax({
		    success: function(response){
		    $("#colorbox").addClass("failedgeoLocation-box");
		    	ACC.colorbox.open("", {
					html: $("#failedgeolocationOverlay").html(),
					maxWidth:"100%",
					width:"650",
					escKey: false,
		 		    overlayClose: false,
		 			onComplete: function(e) {
		 				$("#cboxClose").click(function(){
		 					window.location = "/"; 
		 				 });
		 				
		 				},
					})
				  }
			   })
	},
	
	
	defaultBranch: function(branch)
	{
		$(document.body).on("click",".makeMyBranch",function(e)
		{
			var namestore = $(".store_details_name.headline").text();
		
			
			ACC.mystores.makeMyBranchClick(namestore);
			
		});
		$(document.body).on("click",".makeMyBranchDefault",function(e)
				{
			
					var namestore = $("#defaultbranch").val();
					//$(this).find("input#defaultbranch").val(namestore);
					ACC.mystores.makeMyBranchClick(namestore);
					
				});
		
	},
	
	makeMyBranchClick: function(namestore){
		var storedetail = namestore.split('#');
		var id = storedetail[1];
		var name = storedetail[0];
		var storeName = id + " : " + name ;
		
		digitalData.eventData={
				branch: storeName
				}
				  console.log("digitalData" + JSON.stringify(digitalData));
					try {
						 _satellite.track("makeMyBranchClick");
							} catch (e) {} 
						
	},
	
	removeMyStore: function()
	{
		$(document.body).on("click",".removeStore",function(e)
		{
			e.preventDefault();

			var html = ["<div class='PopupBox'>",
							"<h3>", $(this).data('name'), "</h3>", 
							"<h4>", $(this).data('title'), "</h4>",
							$(this).data('line1'), " ", 
							$(this).data('line2'), "<br/>",
							$(this).data('town'), ", ", $(this).data('regionCode'), " ", $(this).data('postalCode'),
							"<br/><br/>",
						"</div>",
						"<div class='confrimbtnBox'>",
							"<div class='col-xs-12 col-sm-6'>",
								"<a href='"+ ACC.config.encodedContextPath +"/my-account/remove-my-store/", $(this).attr("Id"), "'>",
									"<button type='button' id='delete' class='btn btn-ConfirmMessage btn-primary btn-block remBranch'>"+ACC.config.remove+"</button>",
								"</a>",
							"</div>",
							"<div class='col-xs-12 col-sm-6'>",
								"<button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >"+ACC.config.cancel+"</button>",
							"</div>",
						"</div>"].join('');

				ACC.colorbox.open(ACC.config.deleteBranch, {
					html: html,
					width:"500px",
					onOpen: function() {
						$('#colorbox').addClass('remove-branch-overlay');
					}
				});
		});
	},
	
	
	
	bindConfirmButton: function () 
	{
		if($("#sessionStore").val()==$.cookie("gls"))
		{
			$('.geolocatedLabel').css('display', 'block');
		}
		
		var show = false;
		
		if($('#isPreferredStore').val() == "false" && null == $.cookie("csc"))
		{
			show = true;
		}

		if(show)
		{
			$('.confirmStoreButton').css('display', 'block');
		}
	},
	changebranchPopupFn: function() {
		ACC.mystores.isPLPPage = "false";
		$(window).scrollTop(0);
		var currentStoreName= $("#sessionStoreName").val();
		var currentLine1 = $("#sessionStoreLine1").val();
		var currentLine2 = $("#sessionStoreLine2").val();
		var currentRegion = $("#sessionStoreRegion").val();
		var currentPostal = $("#sessionStorePostal").val();
		var currentPhone = $("#sessionStorePhone").val();
		var currentTown= $("#sessionStoreTown").val();
		var currentStoreId = $("#sessionStore").val();
		var popup = $(".changebranch_popup").clone();
		if (currentStoreId) {
			popup.find("#sessionStore").val(currentStoreId);
		}
		popup.find("#analyticsstore").val(currentStoreName);
		popup.find(".branch_item_name, .storeDisplayName_popup .m-b-5").text(currentStoreName);
		popup.find(".line1").text(currentLine1);
		popup.find(".line2").text(currentLine2);
		popup.find(".town").text(currentTown);
		popup.find(".region").text(currentRegion);
		popup.find(".postalCode").text(currentPostal);
		popup.find(".tel-phone").attr("href", "tel:" + currentPhone).text(currentPhone);
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function (position) {
				$('.branchGeoLocation').removeClass("hidden");
			}
			);
		}
		$("#colorbox").addClass("myBranch-box");
		ACC.colorbox.open("", {
			html: popup.html(),
			width: ACC.global.wWidth >= 1024 ? '389px' : '100%',
			height: '100vh',
			top: 0,
			open: true,
			left: "-400px",
			className: "changebranch_border",
			onComplete: function () {
				$(".changebranch_border").hide();
				$(".changebranch_border #cboxLoadedContent").addClass("scroll-bar-5");
				ACC.mystores.changeBranchPopupCall();
				$("#cboxContent select#milesSelector").change(function () {
					ACC.mystores.changeBranchPopupCall();
				});
				_AAData.popupPageName = ACC.config.storepopupPageName;
				_AAData.popupChannel = ACC.config.storepopuppathingChannel;
				try {
					_satellite.track('popupView');
				} catch (e) { }
			},
			onClosed: function () {
				$('.changebranch_border').animate({ 'left': '0px' }, 'slow');
				$("#colorbox").removeClass("myBranch-box");
			}
		})
	},
	
	changeBranchPopupCall: function() {
		$('.nearby_container').show();
		$('.searched_container').hide();
		if(($('.product-grid-row').is(':visible') || $('.product-search-row').is(':visible')) && (ACC.mystores.isPLPPage == "true")){
			var baseCode = ACC.mystores.selectBaseCode;
			var url = $('#checkbranch-imgurl-' + baseCode).val();
			var name = $('#checkbranch-productname-' + baseCode).val();
			var itemnumber = $('#checkbranch-itemnumber-' + baseCode).val();
			var item = "";
			$('.branchSearch').addClass("hidden");
			$('.nearby_container').addClass("nearby_container_top");
			$('.yourshopping').addClass("hidden");
			$('.yourshopping_plp').removeClass("hidden");
			item += '<div class="col-xs-12 checkstore">Check Other Stores</div><div class="col-xs-12 m-b-15"><div class="col-xs-4"><img class="product_info_img" src="'+url+'" alt="'+name+'" title=""></div><div class="col-xs-8 productname"><span class="productcode">'+itemnumber+'</span><br>'+name+'</div></div>';
			$('.yourshopping_plp_productinfo').html(item);
		}
		else{
			$('.branchSearch').removeClass("hidden");
			$('.nearby_container').removeClass("nearby_container_top");
			$('.yourshopping_plp').addClass("hidden");
			$('.yourshopping').removeClass("hidden");
		}
		$(".branchSearchBox").on('keyup', function (e) {
			if (e.key === 'Enter' || e.keyCode === 13) {
				ACC.mystores.storeSearch();
			}
		});
		var isAnonymous = document.getElementById('isAnonymous').value;
		if (isAnonymous == 'false') {
			$.cookie("csc", null, { path: '/' });
		}
		var pageName = $(".pagename").val();
		if(pageName == 'Product Grid' || pageName == 'Product Details' || pageName == 'Search Results'||$('.template-pages-layout-siteOneCuratedPlpLayout').is(':visible')){
			var getMiles = 100;
		}
		else{
			var getMiles = $("#cboxContent select#milesSelector").val();
		}
		if(ACC.mystores.inStockFilterflag == false && pageName != 'Product Details'){
			ACC.refinements.inStockfilter = $('#instockcheckbox').prop('checked',false);
			$("input[type='checkbox'][id='instockcheckbox']").removeAttr('checked');
		}
		else{
			$("input[type='checkbox'][id='instockcheckbox']").attr("checked", true);
		}
		var getlatitude_val = $('.latitude_val').val();
		var getlongitude_val = $('.longitude_val').val();
		var nearby_listitems = "", saved_listitems = "";
		var getproduct_code = '';
		var inStockfiltermiles = false;
		var isNursery =false;
		if(($('#breadcrumbName1').val() == "Nursery" || $('#breadcrumbName1').val() =="Vivero" ) && ((($(".pagename").val() == 'Product Grid' && $('input[name="isNurseryBuyingGroupBranch"]').val() == 'true')) || ($(".pagename").val() == 'Product Details' && $(".isNurseryBuyingGroupBranchpdp").val() == 'true'))){
			isNursery=true;
		}
		varobjData = {};
		if ($("body").hasClass("page-productDetails") && !$(".branchpopup_click").hasClass("pdpvarianttopLink")) {
			if ($('.pdpredesign-container-variant').is(':visible')) {
				getproduct_code = $('.selectedVariantCode').siblings('.variantProductCodeUpdate').val();
			}
			else {
				getproduct_code = $('.trackProductCode').val();
			}
			objData = { "miles": getMiles, "latitude": getlatitude_val, "longitude": getlongitude_val, "productCode": getproduct_code,"isNurseryCategory":isNursery };
			}
			else{
				if(($('.product-grid-row').is(':visible') || $('.product-search-row').is(':visible')) && (ACC.mystores.isPLPPage == "true")){
					getproduct_code = ACC.mystores.selectPLPProduct;
					objData = {"miles":getMiles,"latitude":getlatitude_val,"longitude":getlongitude_val,"productCode": getproduct_code,"isNurseryCategory":isNursery};
				}else{
					objData = {"miles":getMiles,"latitude":getlatitude_val,"longitude":getlongitude_val,"isNurseryCategory":isNursery};
				}
		}
		if(getproduct_code){
			$('.milesSelectorSection').hide();
			$('.inStockfilterSection').show();	
			inStockfiltermiles = false;	
		}
		else{
			$('.inStockfilterSection').hide();
			$('.milesSelectorSection').show();
			inStockfiltermiles = true;	
		}
		$('.nearby_arrow, .saved_arrow').hide();
		
		$.ajax({
			url: ACC.config.encodedContextPath + '/store-finder/stores',
			method: "GET",
			datatype: "json",
			data: objData,
			success: function (data) {
				if (data) {
					var responseData = JSON.parse(data);
					let nearbyarrow_count = 0;
					if (responseData) {
						if (responseData['nearBy']) {
							
							if(ACC.refinements.inStockfilter && (inStockfiltermiles == false) && (ACC.mystores.inStockFilterflag != false)){
								for (i = 0; i < responseData.nearBy.data.length; i++) {
									stockData = responseData.nearBy.data[i].stockDetail.toLowerCase();
									if (stockData == "instock"){
									nearby_listitems += ACC.mystores.createbranchtemHtml(responseData.nearBy.data[i],nearbyarrow_count, 'nearby');
									nearbyarrow_count = nearbyarrow_count + 1;
									}
								}
									if (nearbyarrow_count > 5) $('.nearby_arrow').show();
							}
							else{
								if (responseData.nearBy.data.length > 5) $('.nearby_arrow').show();
								for (i = 0; i < responseData.nearBy.data.length; i++) {
									nearby_listitems += ACC.mystores.createbranchtemHtml(responseData.nearBy.data[i], i, 'nearby');
								}
							}
							$('.nearby_branches').html(nearby_listitems);
							if(nearby_listitems){
								ACC.refinements.inStockfilter = $('#instockcheckbox').prop('checked',false);
							}
						}
						else {
							$('.nearby_branches_list').remove();
						}
						if (responseData['savedBranches']) {
							$('.saved_container').show();
							if (responseData.savedBranches.data.length > 3) $('.saved_arrow').show();
							for (i = 0; i < responseData.savedBranches.data.length; i++) {
								saved_listitems += ACC.mystores.createbranchtemHtml(responseData.savedBranches.data[i], i, 'saved');
							}
							if (responseData.savedBranches.data.length == 0) {
								var login_user = '<div class="col-sm-12 savebranch_empty text-left">' + ACC.config.nosavedbranches + '</div>';
								$('.saved_branches').html(login_user);
								$('.saved_arrow').hide();
							}
							else {
								$('.saved_branches').html(saved_listitems);
							}
						}
						$('.nearby_more_items, .saved_more_items').hide();
						
						
						$('.nearby_arrow').click(function () {
							$('.nearby_more_items').show();
							$('.nearby_arrow').hide();
						});
						$('.saved_arrow').click(function () {
							$('.saved_more_items').show();
							$('.saved_arrow').hide();
						});
					}
					$('.changebranch_border').animate({ 'left': '0px' }, 'slow').show();
				}
				else {
					console.log('No nearby branches for this selected miles');
				}
			},
			error: function (data) {
				$('.changebranch_border').animate({ 'left': '0px' }, 'slow').show();
			}
		});
	},
	createbranchtemHtml: function(data, id, branchName) {
    var index = id + 1;
    var item = "";
    if (data) {
        var getMiles = '';
        if (data.formattedDistance) {
            getMiles = data.formattedDistance.split(' ')[0];
        }

        if (branchName === 'nearby') {
            item += `<div class="row bg-light-grey border-radius-5 p-t-10 margin0 m-b-10 branch_item nearby_branches_list ${id < 5 ? '' : 'nearby_more_items'}">`;
        } else {
            item += `<div class="row bg-light-grey border-radius-5 p-t-10 margin0 m-b-10 branch_item ${id < 3 ? '' : 'saved_more_items'}">`;
        }

        item += `<div class="col-md-12 f-s-20 f-w-600 font-Geogrotesque p-r-45 text-default branch_item_name">${data.name}</div>`;

        
        if (data.enableOnlineFulfillment === false || data.enableOnlineFulfillment === "false") {
            item += `<div class="col-md-12 inBranchOnly-label">
                        ${ACC.config.branchShopping}
                     </div>`;
        }

       if ($("body").hasClass("page-productDetails") && !$(".branchpopup_click").hasClass("pdpvarianttopLink") || (($('.product-grid-row').is(':visible') || $('.product-search-row').is(':visible')) && (ACC.mystores.isPLPPage == "true")))    
         {
            let stockDetail = (data.stockDetail || '').toLowerCase();
            let stockRef = $('#cboxContent .header_' + stockDetail + '_status');
            if (stockRef && stockRef.html()) {
                item += `<div class="col-xs-12 p-y-5 font-size-14 flex-center ${stockRef.attr("class")}">${stockRef.html()}</div>`;
            }
        }

        if (getMiles) {
            item += `<div class="col-md-12 f-w-400 font-size-14 text-blue text-gray-1">
                        <a class="underline-text" href="${ACC.config.encodedContextPath}/store-finder">
                            ${getMiles} ${ACC.config.storeFinderPopupDistanceUnit}
                        </a>
                     </div>`;
        }

        item += `<div class="col-md-12 m-t-10 font-size-14 branch_item_address">
                    <span class="line1">${data.line1 || ''}</span>&nbsp;<span class="line2">${data.line2 || ''}</span>
                    <p class="address_area">
                        <span class="town">${data.town || ''}</span>,
                        <span class="region">${data.regionCode || ''}</span>&nbsp;
                        <span class="postalCode">${data.postalCode || ''}</span>
                    </p>
                 </div>
                 <div class="col-md-12 m-b-10 f-w-400 font-size-14 text-blue text-gray-1 margin-ph">
                    <a class="underline-text" href="tel:${data.phone || ''}">${data.phone || ''}</a>
                 </div>`;

        if (data.storeSpecialityDetails && data.storeSpecialityDetails.length > 0) {
            item += `<div class="col-xs-12"><div class="row margin0">`;
            data.storeSpecialityDetails.forEach(speciality => {
                item += `<div class="col-xs-6 f-s-12 flex-center p-l-5 p-r-5 padding0 store-speciality-details">
                            <img class="m-r-5 speciality-icon" alt="${speciality.name}" src="${speciality.icon}"/>
                            <span>${speciality.name}</span>
                         </div>`;
            });
            item += `</div></div>`;
        }

        item += `<div class="col-xs-12 m-y-15">
                    <div class="row margin0 p-l-5">
                        <div class="col-xs-5 padding0">
                            <a class="btn btn-default btn-small btn-block storeDetails_branches"
                               href="${ACC.config.encodedContextPath}/store/${data.storeId}">
                               ${ACC.config.branchDetails}
                            </a>
                        </div>`;

        if (data.enableOnlineFulfillment === true || data.enableOnlineFulfillment === "true") {
            item += `<div class="col-xs-7 p-r-0">
                        <form method="get" action="${ACC.config.encodedContextPath}/store-finder/make-my-store/${data.storeId}">
                            <input type="submit" value="${ACC.config.makeMyBranch}" 
                                   class="btn btn-primary btn-small btn-block abtn">
                        </form>
                     </div>`;
        }
        item += `</div></div></div>`;
    }

    return item;
},
	storeSearch: function(type) {
		var storeId = $("#cboxContent #query").val();
		var getMiles = '100';
		var getlatitude_val = $('.latitude_val').val();
		var getlongitude_val = $('.longitude_val').val();
		var getproduct_code = '';
		var objData = {};
		if (storeId != '' || type == 'location') {
			if(type == 'location' && navigator.geolocation){
				navigator.geolocation.getCurrentPosition(
					function (position)
					{
						ACC.storefinder.coords = position.coords;
						getlatitude_val = ACC.storefinder.coords.latitude;
						getlongitude_val = ACC.storefinder.coords.longitude;
					},
					function (error)
					{
						console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
					}
				);
			}
			if ($("body").hasClass("page-productDetails") && !$(".branchpopup_click").hasClass("pdpvarianttopLink")) {
				if ($('.pdpredesign-container-variant').is(':visible')) {
					getproduct_code = $('.selectedVariantCode').siblings('.variantProductCodeUpdate').val();
				}
				else {
					getproduct_code = $('.trackProductCode').val();
				}
				objData = { "miles": getMiles, "latitude": getlatitude_val, "longitude": getlongitude_val, "productCode": getproduct_code };
			}
			else {
				if(($('.product-grid-row').is(':visible') || $('.product-search-row').is(':visible')) && (ACC.mystores.isPLPPage == "true")){
					getproduct_code = ACC.mystores.selectPLPProduct;
					objData = {"miles":getMiles,"latitude":getlatitude_val,"longitude":getlongitude_val,"storeId":storeId,"productCode": getproduct_code};
				}else{
					objData = {"miles":getMiles,"latitude":getlatitude_val,"longitude":getlongitude_val,"storeId":storeId}; 
				}
			}
			if(type != 'location'){
				objData.storeId = storeId;
			}
			else{
				objData.page = 0;
				objData.q = '';
			}
			$.ajax({
				url: ACC.config.encodedContextPath + '/store-finder/stores',
				method: "GET",
				datatype: "json",
				data: objData,
				success: function (data) {
					var responseData = JSON.parse(data);
					if (responseData.searchedStore.data != undefined) {
						$('.searched_branch').html(ACC.mystores.createbranchCard(responseData.searchedStore.data[0], storeId));
						$('.searched_container').show();
						$('.nearby_container').hide();
					}
					else {
						window.location.href = ACC.config.encodedContextPath + '/store-finder/footer?query=' + storeId + '&miles=100'
					}
				},
				error: function (data) {
					storeFinderRedirect.click();
				}
			});
		}
	},
	createbranchCard: function(data, storeId) {
    var item = "";
    if (data) {
        var getMiles = '';
        if (data.formattedDistance) getMiles = data.formattedDistance.split(' ')[0];

        item += '<div class="row bg-light-grey border-radius-5 p-t-10 margin0 m-b-10 branch_item">';
        item += '<div class="col-md-12 f-s-20 f-w-600 font-Geogrotesque p-r-45 text-default branch_item_name">' + data.name + '</div>';

        if (data.enableOnlineFulfillment === false || data.enableOnlineFulfillment === "false") {
            item += `<div class="col-md-12 inBranchOnly-label">
                        ${ACC.config.branchShopping}
                     </div>`;
        }
        if ($("body").hasClass("page-productDetails") && !$(".branchpopup_click").hasClass("pdpvarianttopLink") || (($('.product-grid-row').is(':visible') || $('.product-search-row').is(':visible')) && (ACC.mystores.isPLPPage == "true"))) 
		{
            let stockDetail = (data.stockDetail || '').toLowerCase();
            let stockRef = $('#cboxContent .header_' + stockDetail + '_status');
            if (stockRef && stockRef.html()) {
                item += '<div class="col-xs-12 p-y-5 font-size-14 flex-center ' + stockRef.attr("class") + '">' + stockRef.html() + '</div>';
            }
        }
        if (getMiles) {
            item += '<div class="col-md-12 f-w-400 font-size-14 text-blue text-gray-1">' +
                    '<a class="underline-text" href="' + ACC.config.encodedContextPath + '/store-finder">' +
                    getMiles + ' ' + ACC.config.storeFinderPopupDistanceUnit +
                    '</a></div>';
        }
        item += '<div class="col-md-12 m-t-10 font-size-14 branch_item_address">' +
                    '<span class="line1">' + (data.line1 || '') + '</span>&nbsp;<span class="line2">' + (data.line2 || '') + '</span>' +
                    '<p class="address_area">' +
                        '<span class="town">' + (data.town || '') + '</span>,' +
                        '<span class="region">' + (data.regionCode || '') + '</span>&nbsp;' +
                        '<span class="postalCode">' + (data.postalCode || '') + '</span>' +
                    '</p>' +
                 '</div>' +
                 '<div class="col-md-12 m-b-10 f-w-400 font-size-14 text-blue text-gray-1 margin-ph">' +
                    '<a class="underline-text" href="tel:' + (data.phone || '') + '">' + (data.phone || '') + '</a>' +
                 '</div>';
        if (data.storeSpecialityDetails && data.storeSpecialityDetails.length > 0) {
            item += '<div class="col-xs-12"><div class="row margin0">';
            data.storeSpecialityDetails.forEach(speciality => {
                item += '<div class="col-xs-6 f-s-12 flex-center p-l-5 p-r-5 padding0 store-speciality-details">' +
                            '<img class="m-r-5 speciality-icon" alt="' + speciality.name + '" src="' + speciality.icon + '"/>' +
                            '<span>' + speciality.name + '</span>' +
                        '</div>';
            });
            item += '</div></div>';
        }
        item += '<div class="col-xs-12 m-y-15"><div class="row margin0 p-l-5">' +
                    '<div class="col-xs-5 padding0">' +
                        '<a class="btn btn-default btn-small btn-block storeDetails_branches" href="' + ACC.config.encodedContextPath + '/store/' + storeId + '">' +
                            ACC.config.branchDetails +
                        '</a>' +
                    '</div>';
        if (data.enableOnlineFulfillment === true || data.enableOnlineFulfillment === "true") {
            item += '<div class="col-xs-7 p-r-0">' +
                        '<form method="get" action="' + ACC.config.encodedContextPath + '/store-finder/make-my-store/' + storeId + '">' +
                            '<input type="submit" value="' + ACC.config.makeMyBranch + '" class="btn btn-primary btn-small btn-block abtn">' +
                        '</form>' +
                    '</div>';
        }

        item += '</div></div></div>'; 
    }

    return item;
},
	changebranchPLPPopupFn: function(productCode, baseCode,inStockFilter){
		ACC.mystores.selectPLPProduct = productCode;
		ACC.mystores.selectBaseCode = baseCode;
		ACC.mystores.isPLPPage = "true";
		if($(".pagename").val() == 'Product Details'){
			ACC.mystores.inStockFilterflag = true;
		}
		else{
			ACC.mystores.inStockFilterflag = inStockFilter;
		}
		$(window).scrollTop(0);
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function (position) {
				$('.branchGeoLocation').removeClass("hidden");
			}
			);
		}
		$("#colorbox").addClass("myBranch-box");
		ACC.colorbox.open("", {
			html: $(".changebranch_popup").html(),
			width: ACC.global.wWidth >= 1024 ? '389px' : '100%',
			height: '100vh',
			top: 0,
			open: true,
			left: "-400px",
			className: "changebranch_border",
			onComplete: function () {
				$(".changebranch_border").hide();
				$(".changebranch_border #cboxLoadedContent").addClass("scroll-bar-5");
				ACC.mystores.changeBranchPopupCall();
				$("#cboxContent select#milesSelector").change(function () {
					ACC.mystores.changeBranchPopupCall();
				});
				_AAData.popupPageName = ACC.config.storepopupPageName;
				_AAData.popupChannel = ACC.config.storepopuppathingChannel;
				try {
					_satellite.track('popupView');
				} catch (e) { }
			},
			onClosed: function () {
				$('.changebranch_border').animate({ 'left': '0px' }, 'slow');
				$("#colorbox").removeClass("myBranch-box");
			}
		})
	},
	bindHeaderStoreOverlay: function ()
	{
		$('.Icon-StoreSpeciality div').each(function () { if ($(this).text().trim() == 0) { $(this).parent().hide(); } });
		if($('body').hasClass('page-storefinderPage')){
			$(".branchpopup_click").addClass('noCursor');
		}
		$(".changebranchpopup-mobile").on('click',function ()
 		{		
			if($("body").hasClass("page-productDetails") && $('.pdpredesign-container-variant').is(':visible')){
					$(".branchpopup_click").addClass('pdpvarianttopLink');
			}
			ACC.mystores.changebranchPopupFn();	
		});
		$(".branchpopup_click").on('click',function ()
 		{		
			
			if($("body").hasClass("page-productDetails") && $('.pdpredesign-container-variant').is(':visible')){
				$(".branchpopup_click").addClass('pdpvarianttopLink');
			}
			if(!$('body').hasClass('page-storefinderPage')){
				ACC.mystores.changebranchPopupFn();	
			}	
		});
	},
	
	bindpopupStoreOverlay: function ()
	{
		$(document).on("submit",'#siteOneStorePopupForm', function(e){
			e.preventDefault()
		var q = $("#cboxContent .js-geo-finder-search-input ").val();
		var miles = $("#cboxContent #geostorelocatormiles-query ").val();
		var cscStore = $.cookie("csc");
		if(q.length>0)
		{
			if(ACC.mystores.validateSearchTerm(q))
			{
				$.ajax({
					url: ACC.config.encodedContextPath + '/store-finder/GeoPOS' ,
					method : "POST",
					data:{"miles":miles,"q":q },
					success: function (response) 
					{
						if(null != response && response !="")
							{
							var html="";
							html+="<div id='popupstoreOverlay'><div id='storeOverlay' class='storeDetails-overlay col-sm-12'><div class='row'><div class='store-display-wrapper col-xs-12 col-sm-6 col-md-6'><span class='storeDisplayName'><a href='"+ ACC.config.encodedContextPath +"/store/"+ response.storeId +"' class='black-title'><input type='hidden' id='analyticsstore' value="+response.name+"/><div class='title'>"+response.name+"</div><div class='title'>"+response.title+"</div></a></span><BR><span class='line1'>"+response.address.line1+"</span>&nbsp;"
							 if(response.address.line2 != null)
								 {
							      html+="<span class='line2'>"+response.address.line2+"";
								 }
							  
							  html+="</span><BR><span class='town'>"+response.address.town+"</span><span class='region'>"+response.address.region.isocodeShort+"</span>&nbsp;<span class='postalCode'>"+response.address.postalCode+"</span><BR><span class='phone'><a class='tel-phone' href='tel:"+response.address.phone+"'>"+response.address.phone+"</a></span><BR><br><span class='storeDirection'><a id='getDirection' href='' data-url='"+response.address.line1+","+response.address.line2+","+response.address.town+","+response.address.region.isocodeShort+","+response.address.postalCode+"'>Directions &#8594;</a></span></div><div class='store-hours-overlay col-xs-12 col-sm-6 col-md-6'><span class='hidden-sm hidden-md hidden-lg'><br/></span><div class='title'>Branch Hours</div><span class='storeStatus'>"+response.storeStatus+"</span><div class='store-timings'>";
							if(response.openingHours.weekDayOpeningList != null)
								{
							  for( i = 0; i < response.openingHours.weekDayOpeningList.length; i++){

								var weekDayOpening = response.openingHours.weekDayOpeningList[i];
								html+="<span class='day'>"+weekDayOpening.weekDay+"";
								if(!weekDayOpening.closed){
									if(weekDayOpening.openingTime != null)
								       {
								        var openingTime=weekDayOpening.openingTime.formattedHour;
								       }
									 if(weekDayOpening.closingTime != null)
									   {
									    var closingTime= weekDayOpening.closingTime.formattedHour;
									   }
									
									html+="<span class='closetime'>"+openingTime+" - "+closingTime+"</span>";
								}
								else{
									html+="<span class='closetime'>"+ACC.config.closed+"</span>"
								}
								
								}
								}
							  html+=" </span></div></div></div><div class='cl'></div><span class='geolocatedLabel col-md-12'>"+ACC.config.closestBranch+"</span>";
						      html+="<div class='col-sm-12 store-locator-actions'>";
									 if(!response.isPreferredStore)
										 {
										 if(null == cscStore || (null != cscStore && cscStore.value != response.storeId))
											 {
											html+="<span class='confirmStoreButton'><form method='get' action='"+ ACC.config.encodedContextPath +"/store-finder/make-my-store/"+response.storeId+"' class='confirmStoreId'><input type='submit' value='"+ACC.config.confirmBranch+"' class='makeMyStore branch-margin remove-decoration bg-transparent border-none'></form></span>";
											 }
										 }
							  html+="<a href='"+ACC.config.encodedContextPath +"/store-finder' class='changeStoreLink'>"+ACC.config.changeBranch+"</a></div>";
							
					 			$.colorbox({
					 				html: html,
					 				maxWidth:"100%",
					 				width:"650",
									title:false,
									close:'<span class="glyphicon glyphicon-remove"></span>',
									escKey: false,
						 		    overlayClose: false,
						 			onComplete: function(e) {
						 				$(".glyphicon-remove").click(function(){
						 					window.location = "/"; 
						 				 });
						 				
						 				},
								});
							}
						else
							{
							$("#cboxContent #noGeoStoreResultsMsg").show();
		                    $("#cboxContent #geostoreResultsMsg").html('');
		                    $("#cboxContent #geocopyContent").hide();
		                    $("#cboxContent #geo-store-heading").hide();
		                    $("#cboxContent #geoSearchAgain").show();
		                    if(q != '') {
		                        $("#cboxContent #noGeoStoreResultsMsg").html(" <div class='headline2'> "+ACC.config.noLocationsFound+" </div><div class='col-md-12 col-sm-12 col-xs-12 noStoreResults'><p></p> " + ACC.config.noBranchFound + "&nbsp;" + miles +" miles of " + q + ".&nbsp;"+ ACC.config.noBranchFound1
		                            + " <span class='bold-text'><a class='tel-phone' href='tel:(1-800)748-3663'>1-800-748-3663</a> (1-800-SITE-ONE)</span>.</div><br/>");
		                    } else {
		                        $("#cboxContent #noGeoStoreResultsMsg").html(" <div class='headline2'> "+ACC.config.noLocationsFound+" </div><div class='col-md-12 col-sm-12 col-xs-12' style='padding-left:0px'><p></p> "+ACC.config.noBranchFound + miles +" miles of " + q +ACC.config.noBranchFound1
		                            + " <span class='bold-text'><a class='tel-phone' href='tel:(1-800)748-3663'>1-800-748-3663</a> (1-800-SITE-ONE)</span>.</div><br/>");
		                    }

		                    $(".js-store-finder").hide();
		                    $("#cboxContent #geostoreResultsMsg").html('');

							}
					}
					});
			}
		}else{
			$(".alert").remove();
			if($(".js-storefinder-alert").length<1){
				$("#cboxContent #geostoreResultsMsg").html('');
				var emptySearchMessage = ACC.config.invalidDetail
				$(".js-store-finder").hide();
				$("#cboxContent #storeFinder_search").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + emptySearchMessage + '</div>');
			}
		}
		 
		})
	},
	
	validateSearchTerm : function(input){
		$(".alert").remove();
		var regexForDigits = /^[\d-]+$/;
		var lengthOfInput =  $("#cboxContent .js-geo-finder-search-input ").val().length;
		var regexValidateZeros = new RegExp("^[0]+$");
		var invalidZipCodeMessage =ACC.config.invalidDetail
			
		//Check for Valid Branch Number and ZipCode
		if(regexForDigits.test(input) && lengthOfInput < 3){
			if(regexValidateZeros.test(input)){
			$("#cboxContent #geostoreResultsMsg").html('');
			$(".js-store-finder").hide();
			$("#cboxContent #storeFinder_search").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
			return false;
			}
		}else if(regexForDigits.test(input) && lengthOfInput >= 3){
			var regex1 = new RegExp("^([0-9]{5})(-)([0-9]{4})$");
			var regex2 = new RegExp("^([0-9]{5})$");
			var regex3 = new RegExp("^([0-9]{3})$");
			if(regexValidateZeros.test(input) || (!regex1.test(input) && !regex2.test(input) && !regex3.test(input)))
			{
				$("#cboxContent #geostoreResultsMsg").html('');
				$(".js-store-finder").hide();
				$("#cboxContent #storeFinder_search").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
				return false;
			}
		}else{
			//Check for Valid State Or City
			if(/^[a-zA-Z0-9-,'\s]*$/.test(input) == false || lengthOfInput < 2) {
				$("#cboxContent #geostoreResultsMsg").html('');
				$(".js-store-finder").hide();
				$("#cboxContent #storeFinder_search").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
				return false;
			}
		}
		return true;
	},
	
	bindGeoLocatedStore: function ()
	{
		var geoSuccess = function(position) {
		if(null == $.cookie("gls") || $.cookie("allowBlockAccess") == "blocked" || $.cookie("allowBlockAccess") == "none")
		{				
			$.cookie("allowBlockAccess","allowed", { path: '/' });
				$.ajax({
					url: ACC.config.encodedContextPath + '/store-finder/neareststore' ,
					method : "POST",
					data:{"latitude":position.coords.latitude,"longitude":position.coords.longitude,"userLocationConsent":true},
					success: function (data) 
					{	
						if(data && data != ""){
						$(".js-store-address").removeClass("hidden");
						$(".js-select-location").addClass("hidden");
						$.cookie("gls", data.storeId, { path: '/' });
						if(data.address.town === 'undefined'){
							data.address.town = null;
						};
						ACC.mystores.setStoreDetails(data);
					if(window.location.href.indexOf("/p/")!=-1 || window.location.href.indexOf("/search/")!=-1)
						{
						window.location.reload();
						}
						}
					},
					error: function (data)
					{
						console.log(data);
					}
				  });
		}
	  };
	  
	  var geoError = function(error) {
		if (null == $.cookie("gls")	|| $.cookie("allowBlockAccess") == "allowed") {
				$.cookie("allowBlockAccess", "blocked", { path: '/' });
				ACC.mystores.accessDenied("userBlocked");
			}
		};

	  navigator.geolocation.getCurrentPosition(geoSuccess, geoError);	  
},

	accessDenied: function(whenBlocked)
	{
		var url = $("#ipGeolocationUrl").val();
	      var key = $("#ipGeolocationKey").val();
		if (url && url != "" && key && key != "") {
		  var request = new XMLHttpRequest();
		  request.open('GET', url+"?key="+key);
		  request.setRequestHeader('Accept', 'application/json');
		  request.onreadystatechange = function () {
		  if (this.readyState === 4) {
		       var dataObject = JSON.parse(this.responseText);
		       if(dataObject.status === 'success'){
					
		       $.ajax({				 					
		 			url: ACC.config.encodedContextPath + '/store-finder/neareststore' ,
					method : "POST",
					data:{"latitude":dataObject.lat,"longitude":dataObject.lon,"userLocationConsent":false},
					success: function (data) 
					{	$(".js-store-address").removeClass("hidden");
						$(".js-select-location").addClass("hidden");
						if(data.address.town === 'undefined'){
							data.address.town = null;
						};
						ACC.mystores.setStoreDetails(data);
					},
					error: function (data)
					{
						console.log(data);
					}
		     	 });
				}
				else{
					
					if($("body").hasClass("page-storefinderPage")|| $("body").hasClass("page-siteonestoredetailsPage")){
						return false;
					}
					else{
					if(whenBlocked!=="blockedOnLoad" && $("#isHeadSessionName").val()==="true"){
					// Geolocation block storefinder popup
					$("#colorbox").addClass("geo-location-blocked-overlay");
						ACC.colorbox.open("", {
							html: `<div>
							 		<h3 class='header-signinoverlay'>${ACC.config.overlayBlockHeading}</h3>
									<div>${ACC.config.overlayBlockBody}</div>
									<div class='marginTop20'><a href="${ACC.config.encodedContextPath}/store-finder" class='btn btn-primary'>${ACC.config.overlayBlockNav}</a></div>
					 				</div>`,
							width: "600px",
							closeButton: false,
							escKey: false,
							overlayClose: false,

						});
				}
				}
			}
		      }
		    };
		    request.send();
		}
	},

	setStoreDetails: function(data)
{
    var sessionStore = document.getElementById('sessionStore');
    if (data && data != "" && sessionStore && sessionStore != "")
    {
        _AAData.storeLocatorTerm = data.storeId;										
        var displayData = "<a href='"+  ACC.config.encodedContextPath +"/store/"+ data.storeId +"'><div class='title'>"+ data.address.town +",&nbsp;" + data.address.region.isocodeShort +"&nbsp;#"+ data.storeId +"</div></a></span>";
        
        sessionStore.value = data.storeId;
        $("#sessionStore").val(data.storeId);

        $(".storeDisplayName").html(displayData);
        $(".line1").html(data.address.line1);
        $(".line2").html(data.address.line2);
        $(".town").html(data.name);
        $(".region").html(data.address.region.isocodeShort);
        $(".postalCode").html(data.address.postalCode);
        $(".phone").html("<a class='tel-phone' href='tel:"+data.address.phone+"'>"+data.address.phone+"</a>");

        ACC.mystores.storeStatusApiCall(data.geoPoint.latitude, data.geoPoint.longitude, data.storeNotes, data.storeId, ".storeStatus");

        var direction ="<a id='getDirection' href='' data-url='" 
            + data.address.line1 + "," 
            + data.address.line2 + "," 
            + data.address.town + "," 
            + data.address.region.isocodeShort + "," 
            + data.address.postalCode + "'>"+ACC.config.directions+"</a>";
        $(".storeDirection").html(direction);

		$("#sessionStore").val(data.storeId);
        $("#sessionStoreName").val(data.name);
        $("#sessionStoreLine1").val(data.address.line1);
        $("#sessionStoreLine2").val(data.address.line2);
        $("#sessionStoreTown").val(data.address.town);
        $("#sessionStoreRegion").val(data.address.region.isocodeShort);
        $("#sessionStorePostal").val(data.address.postalCode);
        $("#sessionStorePhone").val(data.address.phone);

        if(null != data.openingHours.weekDayOpeningList)
        {
            $.each($('span[class^="weekDay"]'), function( index, value ) 
            {
                if(index < data.openingHours.weekDayOpeningList.length)
                {
                    var weekdayId=".weekDay"+index;
                    $(weekdayId).html(data.openingHours.weekDayOpeningList[index].weekDay);
                }
            });
                    
            $.each($('span[class^="storeTime"]'), function( index, value ) 
            {
                var weekDaysTiming=".storeTime"+index;
                if(index < data.openingHours.weekDayOpeningList.length)
                {
                    if (!data.openingHours.weekDayOpeningList[index].closed) 
                    {
                        var timing = data.openingHours.weekDayOpeningList[index].openingTime.formattedHour + "-" + data.openingHours.weekDayOpeningList[index].closingTime.formattedHour;
                        $(weekDaysTiming).html(timing) ;
                    }
                    else 
                    {
                        $(weekDaysTiming).html(ACC.config.directions);
                    }
                }
            });
        }
        
        $(".confirmStoreId").attr("action",ACC.config.encodedContextPath + "/store-finder/make-my-store/"+data.storeId);
        
        if($("#sessionStore").val()==$.cookie("gls"))
        {
            $('.geolocatedLabel').css('display', 'block');
        }
        if($("#sessionStore").val() != $.cookie("csc"))
        {
            $.cookie("csc", null, { path: '/' });
            $('.confirmStoreButton').css('display', 'block');
        }
    }
},
	productToggle: function ()
	{
		  $('.tabheading').on('click', function(e) {
			  $(".tabheading").removeClass("seletedTab");
			  $(this).addClass("seletedTab");
		     
		      e.preventDefault();
		    });
	
	},

	updateStoreStatus: function () 
	{
		sessionStorage.removeItem(document.getElementById('sessionStore').value);
	},
	
	checkStoreStatus: function (status) 
	{
		if(status.includes("Closed")||status.includes("Closing")
		||status.includes("Open")||status.includes("Opening")){
			return true;
		}else{
			return false;
		}
	},

	storeStatusApiCall: function (latitude , longitude, storeNotes, storeId, element)
	{
		if(null != storeNotes && storeNotes != "")
		{
			$(element).html(storeNotes);
			return null;
		}
		else
		{
			var status = sessionStorage.getItem(storeId);
			if(sessionStorage.getItem(storeId)){
				if(sessionStorage.getItem("lang")!=ACC.config.encodedContextPath){
					status = "";
				}else{
					if(ACC.config.encodedContextPath == "/es" && ACC.mystores.checkStoreStatus(status)){
						status = "";
					}else if(ACC.config.encodedContextPath == "/en" && !ACC.mystores.checkStoreStatus(status)){
						status = "";
					}
				}
			}

			if(sessionStorage.getItem(storeId) && status != "")
			{
				$(element).html(sessionStorage.getItem(storeId));
				return sessionStorage.getItem(storeId);
			}
			else
			{
				var storeDetailsUrl=ACC.config.encodedContextPath + "/store/storeDetails/"+storeId;
				$.ajax({
					url: storeDetailsUrl,
					cache: false,
					type: "get",
					async:false,
					success: function (response)
					{
						if(response.length==0)
						{
							$(element).html(ACC.config.unavailable);
							return ACC.config.unavailable;
						}
						else
						{
							$(element).html(response);
							sessionStorage.setItem(storeId, response);
							sessionStorage.setItem("lang",ACC.config.encodedContextPath);
							return response;
						}
					},
					error: function()
					{
						 $(element).html(ACC.config.unavailable);
						 return ACC.config.unavailable;
					}
				});
			}
		}
	},
	
	
	bindNearbyOverlayPDP : function(){
		$(document).on("click", "#js-nearby-pdp-overlay", function(e){
			e.preventDefault();
			var productCode= $('.trackProductCode').val();
			var productName=$(".pdp-productname").text().trim();
			var imagePath= $(".image-inner-wrapper img").attr('src');
			var uomId=$("#inventoryUOMIDVal").val();
			$.ajax({
			        type: 'GET',
			        url: ACC.config.encodedContextPath + "/p/showNearbyOverlay/?code="+productCode,
					datatype: "json",
					async:false,			
					success: function(result) {				  
						ACC.mystores.bindNearbyOverlayData(result,productName,imagePath,uomId,productCode,"","","","","");
						if(!result){
							ACC.mystores.bindNearByNullData();
							
						}
					}
			});
		});
		
		$(document).on("click", ".js-nearby-pdp-variant-overlay", function(e){
			e.preventDefault();
			let ref = $(this);
			var productCode= ref.attr("data-variant");
			var productName=$(".variantproduct-name").val();
			var imagePath= $(".image-inner-wrapper img").attr('src');
			var uomId=" ";
			var pdpItemnumber=$(".pdp-itemnumber").val();
			var uomName= ref.parents(".product-variant-table__values").find(".product-variant-table__data:eq(1) .uom-name").val();
			var uomValue= ref.parents(".product-variant-table__values").find(".product-variant-table__data:eq(1) .uom-parameter").val();
			$.ajax({
		        type: 'GET',
		        url: ACC.config.encodedContextPath + "/p/showNearbyOverlay/?code="+productCode,
				datatype: "json",
				async:false,			
				success: function(result) {				  
					ACC.mystores.bindNearbyOverlayData(result,productName,imagePath,uomId,productCode,pdpItemnumber,uomName,uomValue,"","");
					if(!result){
						ACC.mystores.bindNearByNullData();
					}
				}
			});
		});
		
	},
	
	bindNearbyOverlayPLP : function(){
		
		$(document).on("click", "#js-nearby-plp-overlay", function(e){
			e.preventDefault();
			var productCode =$(this).data("value");
			var productName=$(this).parents(".product-item-box").find("a.name").text();
			var imagePath= $(this).parents(".product-item-box").find("a.thumb img").attr('src');
			var uomId=$(this).parents(".product-item-box").find("#inventoryUOMIDVal").val();
			
			$.ajax({
			        type: 'GET',
			        url: ACC.config.encodedContextPath + "/c/showNearbyOverlay/?code="+productCode,
					datatype: "json",
					async:false,			
					success: function(result) {				  
						ACC.mystores.bindNearbyOverlayData(result,productName,imagePath,uomId,productCode,"","","","","");
						if(!result){
							ACC.mystores.bindNearByNullData();
							
						}
					}
			});
		});
	
	},
	
	bindNearByNullData:function(){
							ACC.colorbox.open("", {
		                         html:$("#nearbyOverlayNodata").html(),
		                         width: "800px",
								 className:"nearby-overlay",
		                         close:'<span class="glyphicon glyphicon-remove"></span>',
			                         onComplete:function(){
											if(imagePath) $(".product-info img").attr('src', imagePath);
											$(".product-title").html(productName);
				     					},
 								onClosed: function() {
		    							window.location.reload();
		    						},
		                     });
		
		
	},
	bindNearbyOverlayCartEntry : function(){

		$(document).on("click", "#js-nearby-cartentry-overlay", function(e){
			e.preventDefault();
			var entryNumber = $(this).find('input[name=entryNumber]').val();
			var productCode = $(this).data("prodcode");
			var productName= $(this).data("prodname");
			var imagePath= $("#js-nearby-cartentry-overlay").parents('.cart-items-left-pane').find('.cart-img img').attr('src');
			var uomId= $(this).find('input[name=inventoryUOMID]').val();
			var productCategory =  $(this).data("category");
			var productSubCategory = $(this).data("subcategory");
			$.ajax({
			        type: 'GET',
			        url: ACC.config.encodedContextPath + "/cart/showNearbyOverlay/?code="+productCode,
					datatype: "json",
					async:false,			
					success: function(result) {				  
						ACC.mystores.bindNearbyOverlayData(result,productName,imagePath,uomId,productCode,"","","",productCategory,productSubCategory);
						if(!result){
							ACC.mystores.bindNearByNullData();

						}
					}
			});
		});

	},
	
	bindAnalyticProductdata :function(productCode,productName,productCategory,productSubCategory,productQty,productStockStatus,productBackOrder){
		
		if ($('.loginstatus').val() != ''){
			var cartId= $("#cartID").val();
		}
		else{
			var cartId= $("#anonymousCartId").val();
		}
		
		if ($('.page-productDetails, .page-productGrid').length > 0) {
			var productCode= $('.trackProductCode').val();
			var productName=$(".nearby-overlay .product-title").text().trim();
			var productCategory=$('#breadcrumbName1').val();
			var productSubCategory=$('#breadcrumbName2').val();
		}
			
			var product = [];
			cartLocation(_AAData.eventType);
			function cartLocation(addToCartLocation){
        		product.push({
        	       productInfo: {
        	                "productID": productCode,
							"productName":productName,
							"productCategory":productCategory,
							"productSubCategory":productSubCategory,
							"productQty":productQty,
							"productPrice":"",
							"productStockStatus":productStockStatus,
							"productBackOrder":productBackOrder,
        	                "addToCartLocation": "nearby-overlay",       	                     	           
        	            }
        	   });
        	  digitalData.eventData = {
								 "linktype":"",
        	        			 "linkName" : "Add to Cart",
        	        			 "onClickPageName" : addToCartLocation
        	      		 }
        	  if($(".nav-cart .js-mini-cart-count .nav-items-total:visible").text().trim() === "0" )
	        	{ 
	        		digitalData.cart = {
	        				cartId: cartId,	
	        				event :  "scOpen, scAdd"
	            	};
	        	}
        	  else
	        	{
	        		digitalData.cart = {
	        				cartId:  cartId,	
	            			event :  "scAdd"
	           	    };	
	            }
        	 digitalData.product = product;
        	 try {
            	    _satellite.track("Add Cart");
            	} catch (e) {}
            }
		
		
		
	},
	
	
	bindNearbyOverlayData: function(result,productName,imagePath,uomId,productCode,pdpItemnumber,uomName,uomValue,productCategory,productSubCategory){
					var nearbyStore = "<div class='scroll-bar scroll nearby-data'><table class='table'>";
					var tr="";
					
					var productInfo=$("<div class='col-md-12 padding0 product-info'><div class='flex-center'><div class='col-md-1 col-sm-2 col-xs-3 padding0'><img class='img-responsive' src=''></div><div class='col-md-4 col-sm-4 col-xs-8'><span class='product-title'></span></div><div class='cl'></div><div class='col-md-3 col-xs-6 col-sm-6 pdp-itemnumber-code'><span></span><p></p></div><div class='col-md-3 pdp-uom-details col-xs-6 col-sm-6'><span></span><p></p></div></div></div><div class='cl'></div>");
					  if(screen.width>=1024){
							for (var data of result) {
		                        tr += "<td width='11%'><div class='hidden backorderCheck'>"+ data.isEligibleForBackorder+"</div><div class='plp-qty-container flex-center'><div class='qtyHeading'>"+ACC.config.nearbyQtyText+"</div><input type='text' maxlength='5' value='1' class='form-control nearby-qty-box'/></div><div class='qty-empty-error' style='display:none;'>"+ACC.config.nearbyqtyErrorMsg+"</div></td><td colspan='2' width='45%'> <span class='storeid'>" + data.stockAvailablityMessage + "</span></td><td align='right' width='20%'><span class='miles-text'>" + data.distanceFromHomebranch + " "+ACC.config.milesAway+"</span></td><td width='24%'><button class='btn btn-primary btn-block addtocart-nearbyoverlay'>"+ACC.config.nearbyAtc+"</button></td></tr>";
		                    }
 						}
					else{
						
						for (var data of result) {
		                        tr += "<td><div class='hidden backorderCheck'>"+ data.isEligibleForBackorder+"</div><span class='storeid'>" + data.stockAvailablityMessage + "</span><div class='plp-qty-container flex-center'><div class='qtyHeading'>"+ACC.config.nearbyQtyText+"</div><input type='text' maxlength='5' value='1' class='form-control nearby-qty-box'/></div><div class='qty-empty-error' style='display:none;'>"+ACC.config.nearbyqtyErrorMsg+"</div></td><td><span class='miles-text'>" + data.distanceFromHomebranch + " "+ACC.config.milesAway+"</span><div class='cl'></div><div class='btn-div'><button class='btn btn-primary btn-block addtocart-nearbyoverlay'>"+ACC.config.nearbyAtc+"</button></div></td></tr>";
		                    }
					}
					nearbyStore += tr +"</table></div><div class='col-md-8 col-md-offset-2 addedToCartSection marginTop35 hidden' ><div class='col-md-6'><a href='' class='btn btn-primary btn-block js-mini-cart-close-button btn-popup-overlay-width'>"+ACC.config.continueShopping+"</a></div><div class='col-md-6'><a href='"+ ACC.config.encodedContextPath +"/cart' class='btn btn-default btn-block add-to-cart-button btn-popup-overlay-width'> "+ACC.config.nearbyGoTocart+"</a></div></div>";
							ACC.colorbox.open("", {
		                         html:$("#nearbyOverlay").html(nearbyStore),
		                         width: "900px",
								 top: "5%",
								 className:"nearby-overlay",
		                         close:'<span class="glyphicon glyphicon-remove"></span>',
			                         onComplete:function(){
											$(productInfo).insertBefore(".nearby-data");
											$(".product-info img").attr('src', imagePath);
											$(".product-title").html(productName);
											
											
											if($(".pdp-itemnumber").length>0){
												$(".pdp-itemnumber-code span").html(ACC.config.nearbyItemText);
												$(".pdp-uom-details span").html(uomName);
												$(".pdp-uom-details").css("border-left","solid 1px #ccc");
												$(".pdp-itemnumber-code").css("border-left","solid 1px #ccc");
											}
											
											if(pdpItemnumber){
												$(".pdp-itemnumber-code p").html(pdpItemnumber);
											}
											else {
												$(".pdp-itemnumber-code").remove();
											}
											if(uomValue){
												$(".pdp-uom-details p").html(uomValue);
											}
											else {
												$(".pdp-uom-details").remove();
											}
											
											$(document).on("click", ".addtocart-nearbyoverlay", function(){
												var productStockStatus=$(this).parents('tr').find('.storeid').text();
												var elm = $(this);
												var productQty= $(this).parents('tr').find('.nearby-qty-box').val();
												var backorderCheck=$(this).parents('tr').find('.backorderCheck').text();
												if(backorderCheck=="true"){
													var productBackOrder=productQty;
												}
												else{
													var productBackOrder="";
												}
												ACC.mystores.bindAnalyticProductdata(productCode,productName,productCategory,productSubCategory,productQty,productStockStatus,productBackOrder);
												var storeId= $(this).parents('tr').find('.storeid').text().split('#')[1];
												storeId = storeId.replace(/[^0-9]/g, '');
												var qtyCheck =$(this).parents('tr').find(".nearby-qty-box").val();
												 if(qtyCheck == "" || qtyCheck == null){
													$(this).parents('tr').find(".qty-empty-error").show();
												
													}
											else{
												$(this).parents('tr').find(".qty-empty-error").hide();
													$.ajax({
													        type: 'GET',
													        url: ACC.config.encodedContextPath + "/cart/addFromOverlay/?code="+productCode+"&quantity="+productQty+"&storeId="+storeId+"&inventoryUOMId="+uomId,
															datatype: "json",
															async:false,
															success: function(result) {
																if(result || result == "true")
																{				  
																	$(".addedToCartSection").removeClass("hidden");
														 			elm.parents('tr').addClass('disable-row');
														 			elm.text(ACC.config.nearbyAtcAdded);
																}
															}
													});
												}
											});
											
											$('.nearby-qty-box').keyup(function () { 
											    this.value = this.value.replace(/[^0-9\.]/g,'');
												if (this.value.substring(0, 1) == "0") {
											        this.value = this.value.replace(/^0+/g, '1');
											      }
											});
											$('.nearby-qty-box').keypress(function(e){ 
											   if (this.value.length == 0 && e.which == 48 ){
											      return false;
											   }
											});
											$('body').css("overflow-y", "hidden");
											if(ACC.mixedcartcheckout.iOS() && screen.width < 1024){
												$(window).scrollTop($("#colorbox").offset().top - 20);
											}
			                         },
 								onClosed: function() {
		    							window.location.reload();
										$('body').css("overflow-y", "auto");
		    						},
		                     });
		
	},
	
};


$(document).ready(function() {
	$(".failed-geolocationPopup").click(function(){
		ACC.mystores.failedGeoPopUp();
    });
 });
 
$(document).ready(function() {
	$("#GoeStoreOverLay").click(function(){
    $.ajax({
     
     success: function(response){
 		 ACC.colorbox.open("", {
 			html: $("#branch-detail").html(),
 			maxWidth:"100%",
 			width:"650",
 			onComplete: function(e) {
	 				$(document).ready(function(){
	 		 			$("#colorbox").addClass("myBranch-box");
	 		 			ACC.colorbox.open("", {
	 		 				html: $("#popupstoreOverlay").html(),
	 		 				maxWidth:"100%",
	 		 				width:"650",
	 		 				onClosed: function() {
	 		 					$("#colorbox").removeClass("myBranch-box");
	 		 				}
	 		 			})
	 		 		
	 				});
 				},
 		 	})
     	  }
       })
    });
 });
 
 

$(document).ready(function() 
{
	var isAnonymous = document.getElementById('isAnonymous').value;
	if($.cookie("csc") && (window.location.href.lastIndexOf('/login')) == (window.location.href.length - '/login'.length) != 1 && isAnonymous==false ) 
	{
		$.ajax({
			url: ACC.config.encodedContextPath + "/make-my-store/" + $.cookie("csc"),
			cache: false,
			data: ACC.storefinder.storeSearchData,
			type: "get",
			success: function (response){
				$.removeCookie("csc");
			}
		});
	}
});

$(document).ready(function() 
{	
	if($.cookie("allowBlockAccess") != "allowed" && $.cookie("allowBlockAccess") != "blocked" && $.cookie("allowBlockAccess") != "none" )
	{
		$.cookie("allowBlockAccess","none", { path: '/' });
		ACC.mystores.accessDenied("blockedOnLoad");
	}
});