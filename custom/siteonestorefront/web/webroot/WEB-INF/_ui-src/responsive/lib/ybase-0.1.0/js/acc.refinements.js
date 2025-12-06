ACC.refinements = {

	_autoload: [
		"setCityInSearchHeading",
		"bindMoreStoresToggles",
		["bindMoreLessToggles", $(".js-facet-form").length != 0],
		["init", $(".js-facet-form").length != 0],
		"filtersearch",
		["bindSearch", $(".js-facet-form").length != 0],"eventsNoResultsMsg",
		"categoryTiles",
		"refinementmorewaystofilter",
		"bindFiltersCount",
		"nearbyInstockFilterSection"
	],


	coords:{},
	storeSearchData:{},
	inStockfilter : true,

	init:function(){
		navigator.geolocation.getCurrentPosition(
			function (position){
				ACC.refinements.coords = position.coords;
			},
			function (error)
			{
				console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
			}
		);

	},


	bindSearch:function(){

		$(document).on("submit",'#user_location_form', function(e){
			e.preventDefault()
			var q = $(".js-shop-stores-facet .js-shop-store-search-input").val();
			 if(q.length>0){
				 ACC.refinements.getInitStoreData(q);				
			 }
		})


		$(document).on("click",'#findStoresNearMeAjax', function(e){
			e.preventDefault()
			ACC.refinements.getInitStoreData(null,ACC.refinements.coords.latitude,ACC.refinements.coords.longitude);
		})


	},


	getInitStoreData: function(q,latitude,longitude){
		$(".alert").remove();
		data ={
			"q":"" ,
			"page":"0"
		}

		if(q != null){
			data.q = q;
		}
		

		if(latitude != null){
			data.latitude = latitude;
		}

		if(longitude != null){
			data.longitude = longitude;
		}

		ACC.refinements.storeSearchData = data;
		ACC.refinements.getStoreData();
	},


	getStoreData: function(){
		url= $(".js-facet-form").data("url");
		$.ajax({
			url: url,
			cache: false,
			data: ACC.refinements.storeSearchData,
			type: "get",
			success: function (response){
				window.location.reload();
			}
		});
	},
	
	bindMoreLessToggles: function (){

		$(document).on("click",".js-shop-stores-facet .js-facet-change-link",function(e){
			e.preventDefault();
			$(".js-shop-stores-facet .js-facet-container").hide();
			$(".js-shop-stores-facet .js-facet-form").show();
		})


		$(document).on("change",".js-product-facet .js-facet-checkbox",function(){
			if($(this).not(".js-facet-checkbox-stores")){
				loading.start();
				$(this).parents("form").submit();
			} else {
				if($(".js-facet-checkbox-stores".prop('checked') == true)){
					$(".nearby_store_list").find("input[type=checkbox]").each(function() {
						$(this).prop('checked', true);
					})
				} else {
					$(".nearby_store_list").find("input[type=checkbox]").each(function() {
						$(this).prop('checked', false);
					})
				}
				loading.start();
				$(this).parents("form").submit(function(e){
					e.preventDefault();
					e.stopPropagation();
					var submitted = $.post($(this).action, $(this).serialize());
				
					submitted.done(function(result) {
						$(".nearby_store_form").submit();
					});
				});
				
			}
			
		});

		//show loading on click of catagories in facet
		//[href] - would filter "show less" links which are <a>
		$('#product-facet a[href]').on('click', function() {
			if(!$(this).hasClass("hideShowMoreLink")) {
				loading.start();
			}
		});
		
		$(document).on("click",".js-product-facet .js-more-facet-values-link",function(e){
			e.preventDefault();
			$(this).parents(".js-facet").find(".js-facet-top-values").hide();
			$(this).parents(".js-facet").find(".js-facet-list-hidden").show();

			$(this).parents(".js-facet").find(".js-more-facet-values").hide();
			$(this).parents(".js-facet").find(".js-less-facet-values").show();
		})

		$(document).on("click",".js-product-facet .js-less-facet-values-link",function(e){
			e.preventDefault();
			$(this).parents(".js-facet").find(".js-facet-top-values").show();
			$(this).parents(".js-facet").find(".js-facet-list-hidden").hide();

			$(this).parents(".js-facet").find(".js-more-facet-values").show();
			$(this).parents(".js-facet").find(".js-less-facet-values").hide();
		})
	},

	setCityInSearchHeading: function()
	{
		$(document).ready(function () {
			var storeCheckbox = $('.js-facet-checkbox-stores');
			if(storeCheckbox.prop('checked')){
				$('.cityInSearchText').show();	
			} else {
				$('.cityInSearchText').hide();
			}
		});
	},

	bindMoreStoresToggles: function ()
	{
		//Curatedplp In-stock Toggle start
		var curatedplpToggle =  $("#curatedplptoggle").val();
		var curatedplpPageName =  $("#curatedplppagename").val();
		var linkCurated = location.pathname.split("/")[2];
		var curatedplpUrl = $(location). attr("href").split("/")[4];

		if(localStorage.getItem("curatedplpinstockToggle") == undefined) localStorage.setItem("curatedplpinstockToggle", true);

		if((curatedplpPageName.split(',').indexOf(curatedplpUrl) != -1) && localStorage.getItem("curatedplpinstockToggle") == 'false'){
			localStorage.setItem("curatedplpinstockToggle", true)
		}

		if(localStorage.getItem("curatedplpinstockToggle") == 'true' && (curatedplpPageName.split(',').indexOf(linkCurated) != -1)){
			if ($('.js-facet-checkbox').parent().hasClass('storesDisabled')) {
				localStorage.setItem("instockTogglestatus", false);
				$("input[name=inStock]").prop('checked', false);

			}
			else{
				localStorage.setItem("instockTogglestatus", true);
				$("input[name=inStock]").prop('checked', true);
			}
				
		}
		$(document).on("click",".applied-filter .glyphicon-remove",function(e){
			if(localStorage.getItem("instockTogglestatus") == 'true' && localStorage.getItem("curatedplpinstockToggle") == 'true'){
				$('input[name=inStock]').prop('checked', false);
				localStorage.setItem("curatedplpinstockToggle", false);
			}
		
		});
		//Curatedplp In-stock Toggle End
		
		$(document).ready(function () {
			if ($.trim($(".product__listing.product__grid").html()) =='') {
				$("#productFacets").addClass("hidden");		  				
			}
		});

		$(document).on("click",".js-shop-stores-facet .js-more-stores-facet-values",function(e){
			e.preventDefault();
			$(".js-shop-stores-facet ul.js-facet-list li.hidden").slice(0, 5).removeClass('hidden').first().find('.js-facet-checkbox').focus();
			if($(".js-shop-stores-facet ul.js-facet-list li.hidden").length==0){
				$(".js-shop-stores-facet .js-more-stores-facet-values").hide()
			}
		});

		function cleanCSV(value) {
			return (value || '').split(',').filter(Boolean).filter(function(item, index, all) {
				return (index === all.indexOf(item));
			});
		}

		function updateNearbyStoreList(){
			$(".nearby_store_list li:not(.search_nearby_enabled)").find("input").each(function(){
				thisStoreID = $(this).val();
				$('input[name=selectedNearbyStores]').val(function(index, value) {
					return this.value + ',' + thisStoreID;
				});

				$('input[name=selectedNearbyStores]').val(function(index, value) {
					return value.replace(',,', ',');
				 });
			});
			var removeFirstComma = $('input[name=selectedNearbyStores]').val().replace(/^,/, '');
			
			$('input[name=selectedNearbyStores]').val(removeFirstComma);

		}

		$(document).on("click",".search_nearby_enabled",function(e){
			$(this).find("input").each(function(){
				if($(this).prop('checked')){
					$(this).prop('checked', false);
					$(this).parent().parent().find("li").not(".search_nearby_enabled").find("input").prop('checked', false);
					$(this).parent().parent().find("li").not(".search_nearby_enabled").find(".facet__list__mark").css({'opacity': '1', 'pointer-events': 'none', 'cursor': 'not-allowed'});
					$('input[name=selectedNearbyStores]').val("");
					$('input[name=nearbyDisabled]').val('on');
				} else {
					$('input[name=nearbyDisabled]').val('');
					$(this).prop('checked', true);
					$(this).parent().parent().find("li").not(".search_nearby_enabled").find("input").prop('checked', true);
					$(this).parent().parent().find("li").not(".search_nearby_enabled").find(".facet__list__mark").css({'opacity': '1', 'pointer-events': 'auto', 'cursor': 'default'});
					updateNearbyStoreList();
				}
			});
		});

		$(document).on("click","input[name=inStock]",function(e){
			// Curatedplp In-stock toggle localStorage variable set to false
			localStorage.setItem("curatedplpinstockToggle", false);
			if($(this).prop('checked')){
				$('input[name=nearby]').prop('checked', true);
				updateNearbyStoreList();
			}
			setTimeout(function(){
				$(".storeFilterForm").submit();
			}, 500);
		});
		
		$(document).on("click","input[name=expressShipping]",function(e){
			if($(this).prop('checked')){
				$(this).prop('checked', true);		
			}
			setTimeout(function(){
				$(".storeFilterForm").submit();
			}, 500);
		});

		$(document).on("click","#nearby_apply_btn",function(e){
		setTimeout(function(){
			$(".storeFilterForm").submit();
		}, 500);
		});


		$(document).on("click",".nearby_store_list li:not(.search_nearby_enabled)",function(e){
			e.preventDefault();

			$(this).find("input").each(function(){
				thisStoreID = $(this).val();
				nearbyStoresInput = $('input[name=selectedNearbyStores]');
				if($(this).prop('checked')){
					$(this).prop('checked', false);
					nearbyStoresInput.val(function(index, value) {
						return value.replace(thisStoreID, '');
					});

					digitalData.eventData = {
						branchName: $(this).parent().data("branch-name")
					}
					try {
						_satellite.track("deselectNearbyBranch");
					} catch (e) { }
				} else {
					$(this).prop('checked', true);
					$('input[name=nearbyDisabled]').val('');
					nearbyStoresInput.val(function(index, value) {
						return this.value + ',' + thisStoreID;
					});
					if(!$(".search_nearby_enabled input").prop('checked')){
						$(".search_nearby_enabled input").prop('checked', true)
					}
					digitalData.eventData = {
						branchName: $(this).parent().data("branch-name")
					}
					try {
						_satellite.track("selectNearbyBranch");
					} catch (e) { }
				}
				nearbyStoresInput.val(cleanCSV(nearbyStoresInput.val()));
				
				if(nearbyStoresInput.val() == "" || nearbyStoresInput.val() == "#"){
					$('input[name=nearby]').prop('checked', false);
					$('input[name=nearbyDisabled]').val('on');
				}
			})	
		});


		$('.page-searchGrid form[method="get"], .page-productGrid form[method="get"]').submit(function(){
			$(this).find(':input').each(function() {
				var allInputs = $(this);
				if (!allInputs.val()) {
					allInputs.prop('disabled', true);
				}
			});
		});
		
		$('.template-pages-layout-siteOneCuratedPlpLayout form[method="get"], .template-pages-layout-siteOneCuratedPlpLayout form[method="get"]').submit(function(){
			$(this).find(':input').each(function() {
				var allInputs = $(this);
				if (!allInputs.val()) {
					allInputs.prop('disabled', true);
				}
			});
		});



		 
		
		 $(".nearby_locale").click(function(e) {
			if(!$(".js-change-branch-btn").hasClass("active")){
		    e.preventDefault(e);
			$(".nearby_more_locations").show();
			$(".nearby_locale").addClass("active");
			 return false;   
			}
			else{
				$('.nearby_more_locations').hide();
				$(".nearby_locale").removeClass("active");
			}
    	});
	},
	
	eventsNoResultsMsg:function(){
		var results = $(".eventsTotalResults").data("results");
		if(results == 0){
			$("#eventsFiltersMsg").addClass('hidden');
			$("#allEvents").addClass('hidden');
		}
	
	},
	
	filtersearch: function(){
		$('.js-facet-checkbox, .js__facet__expressShipping').change(function () {
	  	    if ($(this).is(':checked')) {
	  	    	 var spanValue = $(this).parent().find(".facet__list__text").text();
	  	    	 var spanValue2 = $(this).parent().find(".facet__list__text").text().replace(/\s+/g, "");
	  	    	 var newvalue= spanValue2.split('(');
	  	    	 var filterLabel = newvalue[0];
	  	    	 var filterlabelVal=$(this).attr("name");
	  	    	 var linkType = "";
	  	    	 if($("body").hasClass("page-productGrid")){
						var linkType= "plp-filter";
					}
	  	    	 else if($("body").hasClass("page-searchGrid")){
						var linkType= "search-filter";
					}
				 else if($('body').hasClass('template-pages-layout-siteOneCuratedPlpLayout')){
						linkType = "curated-plp filter";
				 }			 
	  	        digitalData.eventData={
						linkType:linkType,
						linkName: filterLabel,
                		onClickPageName: _AAData.pathingPageName,
	  	        		filterLabel: $(this).attr("name"),
	  					filterValue: filterLabel,
	  					filter: filterlabelVal + " | " + filterLabel						
	  			}
	  	        try {
	  			    _satellite.track("productFilter");
	  			} catch (e) {}
	  	    }
	  	});

	   	$(document.body).on('click','.namevalue',function(e){
	  		var catergoryName=$(this).text();  
		    var productFilterValue = catergoryName;
			digitalData.eventData={
					filterLabel: "Show results for",
					filterValue:productFilterValue,
					filter: "Show results for" + " | " + productFilterValue,
						}
				try {
				    _satellite.track("productFilter");
				} catch (e) {}
	  	});

		if($(".js-storeavailable-count").text()== "0"){
			$(".store-count").hide();
			$(".nearby_locale").addClass("disable-click");
		}
		if($(".js-shippingavailable-count").text()== "0"){
			$(".shipping-count").hide();
		}
	},

	categoryTiles: function (){
		let count_loop=1;	
		$(document).on("click",".category-tile-prev, .category-tile-next",function(){
		  if($("#recommendedProductBanner").length >= 1){
			let target = $("#recommendedProductBanner");
			let scrollMultiplier1 = Math.round(target.outerWidth() / target.find(".category-tile-item").outerWidth());		
			let productLen = Math.ceil((target.find('#carouselProduct').val() / scrollMultiplier1));
			
			var catTileContainer = $(this).parent(),
				tileWidth = $(this).parent().find(".category-tile-item").outerWidth(),
				scrollMultiplier = Math.round(catTileContainer.width() / tileWidth), // scroll however many tiles are displayed at current viewport width
				leftPos = $(catTileContainer).scrollLeft();
			if($(this).hasClass("category-tile-next")){
				if(count_loop<productLen){
					$(catTileContainer).animate({scrollLeft: leftPos + (tileWidth * scrollMultiplier)}, 800);
					count_loop=count_loop+1;
                }						
			} 
			else {
				$(catTileContainer).animate({scrollLeft: leftPos - (tileWidth * scrollMultiplier)}, 800);
				count_loop=count_loop-1;
			}
		  }
		  else{
			var catTileContainer = $(this).parent(),
			tileWidth = $(this).parent().find(".category-tile-item").outerWidth()-5,
			scrollMultiplier = Math.floor(catTileContainer.width() / tileWidth), // scroll however many tiles are displayed at current viewport width
			leftPos = $(catTileContainer).scrollLeft();
			if($(this).hasClass("category-tile-next")){
				$(catTileContainer).animate({scrollLeft: leftPos + (tileWidth * scrollMultiplier)}, 800);
			} else {
				$(catTileContainer).animate({scrollLeft: leftPos - (tileWidth * scrollMultiplier)}, 800);
			}
		  }
		});	
		let count_loops=1;	
		$(document).on("click",".category-tile-prevrr1, .category-tile-nextrr1",function(){
		  if($("#recommendedProductBannerrr1").length >= 1){
			let target = $("#recommendedProductBannerrr1");
			let scrollMultiplier1 = Math.round(target.outerWidth() / target.find(".category-tile-item").outerWidth());		
			let productLen = Math.ceil((target.find('#carouselProduct').val() / scrollMultiplier1));
			
			var catTileContainer = $(this).parent(),
				tileWidth = $(this).parent().find(".category-tile-item").outerWidth(),
				scrollMultiplier = Math.round(catTileContainer.width() / tileWidth), // scroll however many tiles are displayed at current viewport width
				leftPos = $(catTileContainer).scrollLeft();
			if($(this).hasClass("category-tile-nextrr1")){
				if(count_loops<productLen){
					$(catTileContainer).animate({scrollLeft: leftPos + (tileWidth * scrollMultiplier)}, 800);
					count_loops=count_loops+1;
                }						
			} 
			else {
				$(catTileContainer).animate({scrollLeft: leftPos - (tileWidth * scrollMultiplier)}, 800);
				count_loops=count_loops-1;
			}
		  }
		  else{
			var catTileContainer = $(this).parent(),
			tileWidth = $(this).parent().find(".category-tile-item").outerWidth()-5,
			scrollMultiplier = Math.floor(catTileContainer.width() / tileWidth), // scroll however many tiles are displayed at current viewport width
			leftPos = $(catTileContainer).scrollLeft();
			if($(this).hasClass("category-tile-nextrr1")){
				$(catTileContainer).animate({scrollLeft: leftPos + (tileWidth * scrollMultiplier)}, 800);
			} else {
				$(catTileContainer).animate({scrollLeft: leftPos - (tileWidth * scrollMultiplier)}, 800);
			}
		  }
		});	
		$(document).on("click",".curated-horizontal-prev, .curated-horizontal-next",function(){
			
			  var catTileContainer = $(this).parent(),
			  tileWidth = $(this).parent().find(".category-tile-item").outerWidth()-5,
			  scrollMultiplier = Math.floor(catTileContainer.width() / tileWidth), // scroll however many tiles are displayed at current viewport width
			  leftPos = $(catTileContainer).scrollLeft();
			  if($(this).hasClass("curated-horizontal-next")){
				  $(catTileContainer).animate({scrollLeft: leftPos + (tileWidth * scrollMultiplier)}, 800);
			  } else {
				  $(catTileContainer).animate({scrollLeft: leftPos - (tileWidth * scrollMultiplier)}, 800);
			  }
			
		  });		
	  

	},
	refinementmorewaystofilter: function (){
        let headerMorewaytofilters = $('.morewaytofilters').next('.facet.js-facet').length;
        if(headerMorewaytofilters == '0'  || headerMorewaytofilters == 0) {
            $('.morewaytofilters').hide();
        }
    },

	bindFiltersCount: function() {
		let selectedFiltersCount = $("#productFacets input[type='checkbox']:checked").length;
		if(selectedFiltersCount);
		$(".selFiltersCount").text(selectedFiltersCount);
	},
	nearbyInstockFilterSection: function(){
		$(document).on("click","#instockcheckbox",function(e){
		   ACC.refinements.inStockfilter = $(this).prop('checked');
		   if(ACC.refinements.inStockfilter == true && ACC.mystores.inStockFilterflag == false){
			ACC.mystores.inStockFilterflag = true;
		   }
		   else{
			if($(".pagename").val() == 'Product Details'){
				ACC.mystores.inStockFilterflag = true;
			}else{
			ACC.mystores.inStockFilterflag = false;
		   }
		}
		ACC.mystores.changeBranchPopupCall();
		  
        });
	}
	
	
};