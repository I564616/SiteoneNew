ACC.storefinder = {

	_autoload: [
		["init", $(".js-store-finder").length != 0],
		["bindStoreChange", $(".js-store-finder").length != 0],
		["bindSearch", $(".js-store-finder").length != 0],
		["bindSearchCurrentLocation", $(".js-store-finder").length != 0],
		"bindPagination",
		["initGoogleMap", $('#User_Agent').val()!="SiteOneEcomApp"],
		"bindToStoreFinderFooterForm",
		"removeMyStore",
		["bindStoreDetails" , $("#store_details").length != 0],
		"bindCurrentLocation",
		"bindStoreStatusForMyStores",
		"storeNumberCss",
		"clickGetDirectionsLink",
		"bindBackButton",
		"makeMyBranchClick",
		"bindSpecialtyStoreList",
		"adjustSelectBoxLocations",
		"hideSpecialtiesOption"
		
	],
	storeData:"",
	storeDataTemp:{},
	storeId:"",
	coords:{},
	storeSearchData:{},
	sourceLatitude:"",
	sourceLongitude:"",
	specialties: [],
	
	makeMyBranchClick: function()
	{
		$(document.body).on("click",".makeMyStore",function(e)
		{
			var namestore = $(this).parents(".list__entry").find(".store-city").text();
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
		});
	},
	
	createListItemHtml: function (data,id,searchText,selectedMiles,isStoreSpeciality,lat,lng,j){
        var index=id+1;
		var item="";
		var cscStore = $.cookie("csc");
		var storeSpecialityNumberColorMap = { 0: "green-circle", 1: "blue-circle", 2:"gray-circle" }
		
		var searchInput = $(".js-store-finder-search-input").val();
		if(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId ){
		item+='<li class="list__entry">';
		}
		else{
			item+='<li class="list__entry list-bg">';
		}
		item+='<label for="store-filder-entry-'+id+'" class="js-select-store-label branch-card">';
		var mystoreIdListData = null;
		
		if(null != $("#myStoresIdList").val() && $("#myStoresIdList").val() != "")
		{

			mystoreIdListData = JSON.parse($("#myStoresIdList").val());
		}
		item+='<div class="row col-md-12 col-xs-12">'
		if(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId){
			item+='<div class="branch-index-img" style="background-image: url(/_ui/responsive/theme-lambda/images/index-grey.png); background-size: cover;"><span class="branch-index" style="color: #5a5b5d">'+index+'</span></div>'
		}
		else{
			item+='<div class="branch-index-img" style="background-image: url(/_ui/responsive/theme-lambda/images/index-green.png); background-size: cover;"><span class="branch-index" style="color: #fff">'+index+'</span></div>'
		}
		if(data.enableOnlineFulfillment === true || data.enableOnlineFulfillment === "true"){
			item+='</div><div class="row col-md-12 col-xs-12 branch-row"><span class="branch-row-right">';
			if(data.isPreferredStore.toString() == "false" && jQuery.inArray(data.storeId , mystoreIdListData) != -1)
			{
				item+='<form method="get" action="'+ACC.config.encodedContextPath+'/store-finder/make-my-store/' + data.storeId + '"><input type="submit" value="'+ACC.config.makeMyBranch+'" class="makeMyStore branch-margin remove-decoration bg-transparent border-none"></form>';
			}
			if(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId)
			{
				item+='<form method="get" action="'+ACC.config.encodedContextPath+'/store-finder/make-my-store/' + data.storeId + '"><input type="submit" value="'+ACC.config.makeMyBranch+'" class="makeMyStore branch-margin remove-decoration bg-transparent border-none"></form>';
			}
			item+='</div><div class="row col-md-12 col-xs-12 branch-row1"><span class="branch-row-right">';
			
			if(data.isPreferredStore.toString() == "true" || cscStore == data.storeId)
			{
				item+='<div class="my-branch">'+ACC.config.myBranch+'</div>';
			}
			else if(jQuery.inArray(data.storeId , mystoreIdListData) != -1)
			{
				item+='<div class="my-branch">'+ACC.config.savedBranch+'</div>';
			}
			if(!(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId))
			{
				item+='<a href="#" id="removeStore" data-store-id="' + data.storeId + '" data-name="' + data.name + '" data-line1="' + (isStoreSpeciality?data.address.line1:data.line1) + '" data-line2="' + (isStoreSpeciality?data.address.line2:data.line2) +
				'" data-town="' + (isStoreSpeciality?data.address.town:data.town) + '" data-region-code="' + (isStoreSpeciality?data.address.region.isocodeShort:data.regionCode) + '" data-postal-code="' + (isStoreSpeciality?data.address.postalCode:data.postalCode) + '""  class="branch-margin remove-decoration">'+ ACC.config.removeMyBranch +'</a></div>';
			}
			item+='</span></div>';
		}
		item+='<div class="row col-md-12 col-xs-12 branch-row2"><span class="branch-row-right"><span>'
		if(!isStoreSpeciality || (isStoreSpeciality && (null != data.formattedDistance && data.formattedDistance != "")))
		{
		item+='<span>'+data.formattedDistance.toLowerCase()+'</span><BR></span>';
		}
		if((isStoreSpeciality && (lat != undefined && lat != "" && lng != "" && lng != undefined)) ||  (!isStoreSpeciality && (ACC.storefinder.coords.latitude != undefined && ACC.storefinder.coords.longitude != undefined)))
		{
			item+='<span class="branch-direction"><a href="https://www.google.com/maps?hl=en&saddr='+(isStoreSpeciality? lat+' '+lng+'&daddr='+data.address.line1+' '+data.address.town+' '+ data.address.region.isocodeShort + ' '+ data.address.postalCode : ACC.storefinder.coords.latitude+' '+ACC.storefinder.coords.longitude+'&daddr='+data.line1+' '+data.line2+' '+data.town+' '+ data.regionCode + ' '+ data.postalCode) + '" target="_blank" style="text-decoration: none;">'+ACC.config.directions+'</a></span><div class="hidden-md hidden-lg visible-xs visible-sm"><br/></div>';
	    }
		
		else
		{
			item+='<span class="branch-direction"><a href="https://www.google.com/maps?hl=en&daddr='+(isStoreSpeciality?data.address.line1+' '+data.address.town+' '+ data.address.region.isocodeShort + ' '+ data.address.postalCode: data.line1+' '+data.line2+' '+data.town+' '+ data.regionCode + ' '+ data.postalCode) + '" target="_blank" style="text-decoration: none;">'+ACC.config.directions+'</a></span><div class="hidden-md hidden-lg visible-xs visible-sm"><br/></div>';
		}
		item+='</span></div>';
		item+='<span class="entry__info">';
		
		if(searchInput == null || searchInput == '' || searchInput== undefined)
		{
			if(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId)
			{
				if(!isStoreSpeciality)	{
					if(null != data.title && data.title != "")
				        item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId + (isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ':'/"> ')+data.name+ '</span></span> <span class="branch-card-entry-name"> '+data.title+' </span></a>'+'<div class="hidden-md hidden-lg hidden-sm cl"></div>';
					else
						item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId + (isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ':'/"> ')+data.name+ '</span></span></a>'+'';
				}
				else{
					if(null != data.title && data.title != "")
				        item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId + (isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ':'/"> ')+data.name+ '</span></span> </a>'+'<div class="hidden-md hidden-lg hidden-sm cl"></div>';
					else
						item+='<span class="entry__city">  <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId + (isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ':'/"> ')+data.name+ '</span></span></a>'+'';
				}
			}
			else
			{
				data.showGreenIcon = true;
				if(!isStoreSpeciality)	{
					if(null != data.title && data.title != "")
					     item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +(isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ': '/"> ')+data.name+ ' </span></span> <span class="branch-card-entry-name"> '+data.title+' </span></a>'+'';
					else
						item+='<span class="entry__city"> <span class="number-circle green-circle">'+index+'</span> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +(isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ': '/"> ')+data.name+ ' </span></span></a>'+'';
				}
			else{
				if(null != data.title && data.title != "")
					     item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +(isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ': '/"> ')+data.name+ ' </span></span></a>'+'';
					else
						item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +(isStoreSpeciality?'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> ': '/"> ')+data.name+ ' </span></span></a>'+'';
			
				
				}
			}
		}
		else
		{
		  if(!isStoreSpeciality)	{
			if(data.isPreferredStore.toString() == "false" && (jQuery.inArray(data.storeId , mystoreIdListData)==-1) && cscStore != data.storeId)
			{
					if(null != data.title && data.title != "")
				        item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'"href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> '+data.name+ '</span></span> <span class="branch-card-entry-name"> '+data.title+' </span></a>'+'<div class="hidden-md hidden-lg hidden-sm cl"></div>';
					else
						item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> '+data.name+ '</span></span></a>'+'';
	
			}
			else
			{
					data.showGreenIcon = true;
					if(null != data.title && data.title != "")
					     item+='<span class="entry__city"> <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> '+data.name+ ' </span></span> <span class="branch-card-entry-name"> '+data.title+' </span></a>'+'';
					else
						item+='<span class="entry__city">  <span class="store-city"><a class="store-name" title="'+data.name+'" href="'+ACC.config.encodedContextPath+'/store/' + data.storeId +'/?searchtext='+searchText+'&miles='+selectedMiles+ '"> '+data.name+ ' </span></span></a>'+'';
			}
		  }
			
		}
		
		if(data.enableOnlineFulfillment === false || data.enableOnlineFulfillment === "false"){
			item+='<div class="row col-md-12 col-xs-12 m-b-10 m-t-10"><span class="inBranchShoppingOnly">'+ACC.config.branchShopping+'</span></div>';
		}
		item+='<span class="col-md-12 col-xs-12 entry__address small-text p-l-0">'+(isStoreSpeciality?data.address.line1:data.line1+' '+data.line2)+'</span>';
		item+='<span class="entry__city small-text">'+(isStoreSpeciality?data.address.town+ ',  ' + data.address.region.isocodeShort + '  ' +data.address.postalCode:data.town+ ',  ' + data.regionCode+ '  ' + data.postalCode) + '</span>';
		item+='<span class="entry__phone small-text"><a class="tel-phone remove-decoration" href="tel:'+(isStoreSpeciality?data.address.phone+'"> '+data.address.phone:data.phone+'"> '+data.phone)+'</a></span></br>';
		item+='<span class="'+element+' ">'+(isStoreSpeciality?data.storeStatus:status)+'</span>';
		var element = "entry__status-"+data.storeId;
		item+='<span class="'+element+' small-text" data-lat="'+(isStoreSpeciality? data.geoPoint.latitude +'" data-long="'+ data.geoPoint.longitude: data.latitude +'" data-long="'+ data.longitude) +'"></span>';
		item+='</span>';
		if(null!=data.nurseryOfferURL && data.nurseryOfferURL !="") {
			item += '<span class="view-offer-btn"><a href=' + data.nurseryOfferURL + ' target="t" onClick="" class="btn-primary btn">View Local Offers</a></span>';
		}
		if(data.storeSpecialityDetails != null){
			item+= '<div class="row col-md-12 col-xs-12 branch-row3">'
			item+= '<div onClick="ACC.storefinder.expandSpeciality(expandspeciality'+index+', expandicon'+index+', expandname'+index+', hideicon'+index+', hidename'+index+')"><img id="expandicon'+index+'" class="branch-card-expand-icon" alt="" src="/_ui/responsive/theme-lambda/images/plus-icon.svg"/><img id="hideicon'+index+'" class="branch-card-expand-icon hidden" alt="" src="/_ui/responsive/theme-lambda/images/hide-icon.svg"/></div><div id="expandname'+index+'" class="hidden-xs branch-card-expand-name">'+ACC.config.showBranchSpecialties+'</div><div id="hidename'+index+'" class="hidden-xs branch-card-expand-name hidden">'+ACC.config.hideSpecialties+'</div>';
			item+= '<div class="col-md-12 col-xs-12 branch-row4">';
			data.storeSpecialityDetails.forEach(speciality => {
				item+=  '<div><img class="branch-card-img" alt=""' +speciality.name+ '" src="' +speciality.icon+ '"/></div>';
			});
			item+= '</div></div>'
			item+= '<div class="row col-md-12 col-xs-12 expand-container hidden" id="expandspeciality'+index+'">'
			data.storeSpecialityDetails.forEach(speciality => {
				item+=  '<div class="col-md-3 col-xs-6 branch-container-item"><img class="branch-container-img" alt=""' +speciality.name+ '" src="' +speciality.icon+ '"/><span>' +speciality.name+ '</span></div>';
			});
			item+= '</div>'
		}
		item+='</label>';
		item+='</li>';
		return item;

	},

	removeMyStore: function ()
	{
		$(document).on("click","#removeStore",function(e)
		{
			e.preventDefault();
			$.cookie("csc", null, { path: '/' });
			window.location.href = ACC.config.encodedContextPath + "/store-finder/remove-my-store/" + $(this).data("storeId");
		})
	},
	
	expandSpeciality: function (expandspeciality, expandicon, expandname, hideicon, hidename)
	{
		
		if($(expandspeciality).is(":hidden")){
			$(expandspeciality).removeClass("hidden");
			$(hideicon).removeClass("hidden");
			$(hidename).removeClass("hidden");
			$(expandicon).addClass("hidden");
			$(expandname).addClass("hidden");
		}
		else{
			$(expandspeciality).addClass("hidden");
			$(hideicon).addClass("hidden");
			$(hidename).addClass("hidden");
			$(expandicon).removeClass("hidden");
			$(expandname).removeClass("hidden");
		}
	},

	refreshNavigation: function (){
		
		var listitems = "";
		data = ACC.storefinder.storeData
		var searchText = ACC.storefinder.storeSearchData.q;
		var selectedMiles = ACC.storefinder.storeSearchData.miles;
		var msg;
		  if(ACC.storefinder.storeSearchData.q != '') {
			  
				if(data.total == 1)
					{
					  	if(ACC.storefinder.storeSearchData.q == ACC.storefinder.storeData.data[0].storeId || ACC.storefinder.storeSearchData.q.toLowerCase() == ACC.storefinder.storeData.data[0].regionCode.toLowerCase() || ACC.storefinder.storeSearchData.q.toLowerCase() == ACC.storefinder.storeData.data[0].regionName.toLowerCase())
						 {
					  		msg = data.total+" " +  ACC.config.branchFound;
						 }
					  	else
					  	{
					  		msg = data.total+" " +   ACC.config.branchWithin+" " + ACC.storefinder.storeSearchData.miles+" " + ACC.config.milesOf+" " + ACC.storefinder.storeSearchData.q;
					  	}
					}
				else{
					
					if(ACC.storefinder.storeSearchData.q.toLowerCase() == ACC.storefinder.storeData.data[0].regionCode.toLowerCase() || ACC.storefinder.storeSearchData.q.toLowerCase() == ACC.storefinder.storeData.data[0].regionName.toLowerCase())
						{
						msg = data.total+" " + ACC.config.branchesFound;
						}
					else
						{
						msg = data.total+" " +   ACC.config.branchesWithin+" " + ACC.storefinder.storeSearchData.miles+" " + ACC.config.milesOf+" " + ACC.storefinder.storeSearchData.q;
						}
					
				}

			} else {
			
				if(data.total == 1)
				{
					msg = data.total+" " +  ACC.config.branchWithin+" " + ACC.storefinder.storeSearchData.miles+" " + ACC.config.miles;
				}
			else{
				msg = data.total+" " +  ACC.config.branchesWithin+" " + ACC.storefinder.storeSearchData.miles+" " + ACC.config.miles;
			    }

			}
		//$("#storeResultsMsg").html(msg);
		$(".no-Store").show();
		if(data){
			for(i = 0;i < data["data"].length;i++){
				listitems += ACC.storefinder.createListItemHtml(data["data"][i],i,searchText,selectedMiles)
			}

			$(".js-store-finder-navigation-list").html(listitems);

			// select the first store
			var firstInput= $(".js-store-finder-input")[0];
			$(firstInput).trigger('click');
			if(document.getElementById('branchlist').childElementCount > 1){
			  $('.specialties-option').removeClass("hidden");
			  $('.specialties-dropdown').removeClass("hidden");
			  $('.specialties-option').removeClass('showing').slideUp();
			}
			else{
			  $('.specialties-option').addClass("hidden");
			  $('.specialties-dropdown').addClass("hidden");
			  $('.specialties-option').removeClass('showing').slideUp();
			}
		}

	},

	bindPagination:function ()
	{

		$(document).on("click",".js-store-finder-details-back",function(e){
			e.preventDefault();

			$(".js-store-finder").removeClass("show-store");
		})

		$(document).on("click",".js-store-finder-pager-prev",function(e){
			e.preventDefault();
			var page = ACC.storefinder.storeSearchData.page;
			ACC.storefinder.getStoreData(page-1)
			checkStatus(page-1);
		})

		$(document).on("click",".js-store-finder-pager-next",function(e){
			e.preventDefault();
			var page = ACC.storefinder.storeSearchData.page;
			ACC.storefinder.getStoreData(page+1,true)
			checkStatus(page+1);
		})

		function checkStatus(page){
			if(page==0){
				$(".js-store-finder-pager-prev").attr("disabled","disabled")
			}else{
				$(".js-store-finder-pager-prev").removeAttr("disabled")
			}

			if(page == Math.floor(ACC.storefinder.storeData.total/10)){
				$(".js-store-finder-pager-next").attr("disabled","disabled")
			}else{
				$(".js-store-finder-pager-next").removeAttr("disabled")
			}
		}
	},

	bindCurrentLocation:function()
	{

		$(document).on("click","#getDirection",function(e)
		{
			if (navigator.geolocation)
			{
		          navigator.geolocation.getCurrentPosition(function(position)
		          {
		            var pos = {
		              lat: position.coords.latitude,
		              lng: position.coords.longitude
		            };
		            ACC.storefinder.sourceLatitude=pos.lat;
		            ACC.storefinder.sourceLongitude=pos.lng;

		            window.sourceLatitude=pos.lat;
		            window.sourceLongitude=pos.lng;

		            console.log('currentLocation');
			})

			}
			e.preventDefault();
			var desturl=$(this).attr("data-url");
			if("" != ACC.storefinder.sourceLatitude && "" !=  ACC.storefinder.sourceLongitude)
				var url="https://www.google.com/maps?hl=en&saddr="+sourceLatitude+","+sourceLongitude+"&daddr="+desturl;
			else
				var url="https://www.google.com/maps?hl=en"+"&daddr="+desturl;
			window.open(url,'_blank');
		});
	},

	bindStoreChange:function()
	{
		$(document).on("change",".js-store-finder-input",function(e){
			e.preventDefault();
			storeData=ACC.storefinder.storeData["data"];

			var storeId=$(this).data("id");

			var $ele = $(".js-store-finder-details");

			$.each(storeData[storeId],function(key,value){
				if(key=="image"){
					if(value!=""){
						$ele.find(".js-store-image").html('<img src="'+value+'" alt="" />');
					}else{
						$ele.find(".js-store-image").html('');
					}
				}else if(key=="productcode"){
					$ele.find(".js-store-productcode").val(value);
				}
				else if(key=="openings"){
					if(value!=""){
						var $oele = $ele.find(".js-store-"+key);
						var openings = "";
						$.each(value,function(key2,value2){
							openings += "<dt>"+key2+"</dt>";
							openings += "<dd>"+value2+"</dd>";
						});

						$oele.html(openings);

					}else{
						$ele.find(".js-store-"+key).html('');
					}

				}
				else if(key=="specialOpenings"){}
				else if(key=="features"){
					var features="";
					$.each(value,function(key2,value2){
						features += "<li>"+value2+"</li>";
					});

					$ele.find(".js-store-"+key).html(features);

				}
				else{
					if(value!=""){
						$ele.find(".js-store-"+key).html(value);
					}else{
						$ele.find(".js-store-"+key).html('');
					}
				}

			})


			ACC.storefinder.storeId = storeData[storeId];
			//ACC.storefinder.initGoogleMap();

		})

		// $(document).on("click",".js-select-store-label",function(e){
		// 	$(".js-store-finder").addClass("show-store")
		// })

		$(document).on("click",".js-back-to-storelist",function(e){
			$(".js-store-finder").removeClass("show-store")
		})

	},


	bindStoreDetails:function()
	{
		var latitude=$("#lat").attr("value");
		var longitude=$("#lng").attr("value");
		var storeId=$("#storeId").attr("value");
		var storeNotes = '';//$("#storeNotes").val();

		if(storeNotes == '')
		{
			if(latitude != undefined && longitude != undefined && storeId != '')
			{
				ACC.mystores.storeStatusApiCall(latitude, longitude, storeNotes, storeId,"#result");
			}
		}
		else if(storeNotes != '')
		{
			$('#result').html(storeNotes);
		}
		else
		{
			$('#result').html('Currently Unavailable');
		}
	},


	initGoogleMap:function(){
		if($('.js-toggle-sm-navigation').is(':visible')) {
			$('.js-store-finder-details').insertBefore('.store__finder--navigation');
		}

		if($(".js-store-finder-map").length > 0 || $("#googleMap").length > 0){
			ACC.global.addGoogleMapsApi("ACC.storefinder.loadGoogleMap");
		}
	},



	loadGoogleMap: function(isStoreSpeciality,response){
		var hardscapeStoreSpecialitites = [];
		var startIndex = 0;
		if(!isStoreSpeciality){
			if($("#googleMap").length > 0){
				ACC.storefinder.storeDetailMap();
			}
		}
		if((isStoreSpeciality && $("#hardscapegoogleMap").length > 0) || (!isStoreSpeciality && ACC.storefinder.storeData["data"])) {
			var locations = [];
			if(!isStoreSpeciality)	{
				$.each(ACC.storefinder.storeData["data"], function(key, value){
					locations.push([value.name, value.latitude, value.longitude, key,value.line1,value.line2,value.phone,value.town,value.regionCode,value.postalCode,value.PreferredStore,value.storeId]);
				});
			} else {
				var count=0;
				$.each(response, function(index, element){
					$.each(element, function( key, value ) {
					count++;
					locations.push([value.name, value.geoPoint.latitude, value.geoPoint.longitude, key,value.address.line1,value.address.line2,value.address.phone,value.address.town,value.address.region.isocodeShort,value.address.postalCode,value.isPreferredStore,value.storeId]);
					});
					hardscapeStoreSpecialitites.push([index,count]);
					if(count==0)
						startIndex = startIndex + 1;
						
				});
				
			}

			if($(".js-store-finder-map").length > 0)
			{
				$(".js-store-finder-map").attr("id","store-finder-map");
				
				var centerPoint = new google.maps.LatLng(isStoreSpeciality?(response[hardscapeStoreSpecialitites[startIndex][0]][0].geoPoint.latitude,response[hardscapeStoreSpecialitites[startIndex][0]][0].geoPoint.longitude):(ACC.storefinder.storeData["data"][0].latitude, ACC.storefinder.storeData["data"][0].longitude));

				var mapOptions = {
					zoom: 13,
					zoomControl: true,
					panControl: true,
					streetViewControl: false,
					mapTypeId: google.maps.MapTypeId.ROADMAP,
					center: centerPoint
				}

				var map = new google.maps.Map(document.getElementById("store-finder-map"), mapOptions);
				var latlngbounds = new google.maps.LatLngBounds();

				 var marker, i;
				for (i = 1; i <= locations.length; i++) {
					/*var storeLoc = locations[i-1][1],locations[i-1][2];*/
						var iconObj = {
						        position: new google.maps.LatLng(locations[i-1][1],locations[i-1][2]),
						        map: map,
						        label: {text: String(i), color: '#ffffff', fontWeight: 'bold'},
						        icon: {
									scaledSize: new google.maps.Size(50, 50), // scaled size
								    origin: new google.maps.Point(0,0), // origin
								}
						      };
						var indexX, indexY;
						if(isStoreSpeciality && $(".page-plantsonmoveLander").length == 0)
						{
							if(i<= hardscapeStoreSpecialitites[0][1]) {
								indexX = hardscapeStoreSpecialitites[0][0];
								indexY = i;
							}
							if(i> hardscapeStoreSpecialitites[0][1] && i<= hardscapeStoreSpecialitites[1][1]) {
								indexX = hardscapeStoreSpecialitites[1][0];
								indexY = i - hardscapeStoreSpecialitites[0][1];
							}
							if(i> hardscapeStoreSpecialitites[1][1] && i<= hardscapeStoreSpecialitites[2][1]) {
								indexX = hardscapeStoreSpecialitites[2][0];
								indexY = i - hardscapeStoreSpecialitites[1][1];
							}
						}
						
						if((isStoreSpeciality && $(".page-plantsonmoveLander").length == 0 && (response[indexX][indexY-1].isPreferredStore || (response[indexX][indexY-1].storeId == $.cookie("gls") || response[indexX][indexY-1].storeId == $.cookie("csc")))) || (!isStoreSpeciality && ACC.storefinder.storeData["data"][i-1].showGreenIcon)) {
							iconObj.icon.url = "/_ui/responsive/theme-lambda/images/location-star-green.png";
							anchor: new google.maps.Point(0, 0) // anchor
						} else {
							if(isStoreSpeciality && $(".page-plantsonmoveLander").length == 0)
							{
								if(i<= hardscapeStoreSpecialitites[0][1])
									iconObj.icon.url = "/_ui/responsive/theme-lambda/images/pinpoint-green.png";
								if(i> hardscapeStoreSpecialitites[0][1] && i<= hardscapeStoreSpecialitites[1][1])
									iconObj.icon.url = "/_ui/responsive/theme-lambda/images/pinpoint-blue.png";
								if(i> hardscapeStoreSpecialitites[1][1] && i<= hardscapeStoreSpecialitites[2][1])
									iconObj.icon.url = "/_ui/responsive/theme-lambda/images/pinpoint-gray.png";
							}
							 
							else
							{
								iconObj.icon.url = "/_ui/responsive/theme-lambda/images/pinpoint-gray.png";
							}
						}
				      marker = new google.maps.Marker(iconObj);

				      //For desktop on hover of markers
				      google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
				        return function() {
				        	if(!locations[i-1][5] || locations[i-1][5] == null )
				        	{
				        	   infowindow.setContent('<div style="overflow: hidden;"><a href=' + ACC.config.encodedContextPath + "/store/"+locations[i-1][11] + ' style="font-weight:bold">' + locations[i-1][0] + '</a><br/>' +  locations[i-1][4] +'<br/>'  +' '+  locations[i-1][7]+', ' +  locations[i-1][8]+' ' +  locations[i-1][9]+'<br/><a class="tel-phone" href="tel:' +locations[i-1][6]+'">'+locations[i-1][6]+'</a><br/>'+ ACC.config.getDirectionFrom +' </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + locations[i-1][4] + ' ' + locations[i-1][5] + ' ' + locations[i-1][7] + ' ' + locations[i-1][8] + ' ' + locations[i-1][9] +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
				        	}
				        	else
				        	{
				        		infowindow.setContent('<div style="overflow: hidden;"><a href=' + ACC.config.encodedContextPath + "/store/"+locations[i-1][11] + ' style="font-weight:bold">' + locations[i-1][0] + '</a><br/>' +  locations[i-1][4] +'<br/>'  + locations[i-1][5] +'<br/>' +  locations[i-1][7]+', ' +  locations[i-1][8]+' ' +  locations[i-1][9]+'<br/><a class="tel-phone" href="tel:' +locations[i-1][6]+'">'+locations[i-1][6]+'</a><br/>'+ ACC.config.getDirectionFrom +'  </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + locations[i-1][4] + ' ' + locations[i-1][5] + ' ' + locations[i-1][7] + ' ' + locations[i-1][8] + ' ' + locations[i-1][9] +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
				        	}
				        	infowindow.open(map, marker);
				        }
				      })(marker, i));

				      //for mobile on click of markers
				      google.maps.event.addListener(marker, 'mousedown', (function(marker, i) {
				        return function() {
				        	if(!locations[i-1][5])
				        	{
				        	   infowindow.setContent('<div style="overflow: hidden;"><a href=' + ACC.config.encodedContextPath + "/store/"+locations[i-1][11] + ' style="font-weight:bold">' + locations[i-1][0] + '</a><br/>' +  locations[i-1][4] +'<br/>'  +' '+  locations[i-1][7]+', ' +  locations[i-1][8]+' ' +  locations[i-1][9]+'<br/><a class="tel-phone" href="tel:' +locations[i-1][6]+'">'+locations[i-1][6]+'</a><br/>'+ ACC.config.getDirectionFrom +' </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + locations[i-1][4] + ' ' + locations[i-1][5] + ' ' + locations[i-1][7] + ' ' + locations[i-1][8] + ' ' + locations[i-1][9] +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
				        	}
				        	else
				        	{
				        		infowindow.setContent('<div style="overflow: hidden;"><a href=' + ACC.config.encodedContextPath + "/store/"+locations[i-1][11] + ' style="font-weight:bold">' + locations[i-1][0] + '</a><br/>' +  locations[i-1][4] +'<br/>'  + locations[i-1][5] +'<br/>' +  locations[i-1][7]+', ' +  locations[i-1][8]+' ' +  locations[i-1][9]+'<br/><a class="tel-phone" href="tel:' +locations[i-1][6]+'">'+locations[i-1][6]+'</a><br/>'+ ACC.config.getDirectionFrom +' </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + locations[i-1][4] + ' ' + locations[i-1][5] + ' ' + locations[i-1][7] + ' ' + locations[i-1][8] + ' ' + locations[i-1][9] +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
				        	}
				        	infowindow.open(map, marker);
				        }
				      })(marker, i));

				      latlngbounds.extend(marker.position);
				      var bounds = new google.maps.LatLngBounds();
						map.setCenter(latlngbounds.getCenter());
						if(locations.length > 1) {
							map.fitBounds(latlngbounds);
						}
				    }
				var infowindow = new google.maps.InfoWindow({
						content: (isStoreSpeciality?(response[hardscapeStoreSpecialitites[startIndex][0]][0].name + response[hardscapeStoreSpecialitites[startIndex][0]][0].address.line1 + response[hardscapeStoreSpecialitites[startIndex][0]][0].address.line2 + response[hardscapeStoreSpecialitites[startIndex][0]][0].address.phone):(ACC.storefinder.storeData["data"][0].name + ACC.storefinder.storeData["data"][0].line1 + ACC.storefinder.storeData["data"][0].line2 + ACC.storefinder.storeData["data"][0].phone)),
						disableAutoPan: true
				});
				google.maps.event.addListener(marker, 'click', function (){
					infowindow.open(map, marker);
				});
			}
		}

		if(!isStoreSpeciality){
		$('span[class^=entry__status]').each(function()
		{
			var storeId = $(this).attr('class').split(' ')[0].split('-')[1]
			var element = ".entry__status-"+storeId;
			var status = ACC.mystores.storeStatusApiCall($(this).data('lat'), $(this).data('long'), null, storeId, element);
			if(status != null && status != undefined)
			{
				$(element).html();
				$(element).html(status);
			}
		});
		}

	},
	
	clickGetDirectionsLink : function() {
		$(document).on("click", '.getDirectionsLink', function(e){
			e.preventDefault();
			var ref = this.href + "&saddr=" + $('#getdirections').val();
			window.open(ref,'_blank');
		});
	},
	
	validateSearchTerm : function(input){
		$(".alert").remove();
		var regexForDigits = /^[\d-]+$/;
		var lengthOfInput = $(".js-store-finder-search-input").val().length;
		var regexValidateZeros = new RegExp("^[0]+$");
		var invalidZipCodeMessage = ACC.config.invalidDetail;
			
		//Check for Valid Branch Number and ZipCode
		if(regexForDigits.test(input) && lengthOfInput < 3){
			if(regexValidateZeros.test(input)){
			$("#storeResultsMsg").html('');
			$(".js-store-finder").hide();
			$("#storeFinder").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
			return false;
			}
		}else if(regexForDigits.test(input) && lengthOfInput >= 3){
			var regex1 = new RegExp("^([0-9]{5})(-)([0-9]{4})$");
			var regex2 = new RegExp("^([0-9]{5})$");
			var regex3 = new RegExp("^([0-9]{3})$");
			var regex4 = new RegExp("^([0-9]{4})$");
			if(regexValidateZeros.test(input) || (!regex1.test(input) && !regex2.test(input) && !regex3.test(input) && !regex4.test(input)))
			{
				$("#storeResultsMsg").html('');
				$(".js-store-finder").hide();
				$("#storeFinder").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
				return false;
			}
		}else{
			//Check for Valid State Or City
			if(/^[a-zA-Z0-9-,'\s]*$/.test(input) == false || lengthOfInput < 2) {
				$("#storeResultsMsg").html('');
				$(".js-store-finder").hide();
				$("#storeFinder").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + invalidZipCodeMessage + '</div>');
				return false;
			}
		}
		return true;
	},
	
	bindSearch:function(){
			  $('.specialties-option').addClass("hidden");
			   $('.specialties-dropdown').addClass("hidden");
			  $('.specialties-option').removeClass('showing').slideUp();
		$(document).on('click','#store-finder-button', function(e){
			e.preventDefault()
		    $(".clear-specialties, .specialties-name").remove();
       	    $('.choose-specialty').prop("checked", false);
            ACC.storefinder.specialties = [];
			var q = $(".js-store-finder-search-input").val();
			var miles = ACC.storefinder.getMiles();
			if(q.length>0)
			{
				if(ACC.storefinder.validateSearchTerm(q))
				{
					$(".alert").remove();
					ACC.storefinder.getInitStoreData(q,null,null,miles, ACC.storefinder.specialties);
				}
			}else{
				$(".alert").remove();
				if($(".js-storefinder-alert").length<1){
					$("#storeResultsMsg").html('');
					var emptySearchMessage =  ACC.config.invalidDetail;
					$(".js-store-finder").hide();
					$("#storeFinder").before('<div class="js-storefinder-alert alert alert-danger alert-dismissable" ><button class="close" type="button" data-dismiss="alert" aria-hidden="true">×</button>' + emptySearchMessage + '</div>');
				}
			}
			try {
				_AAData.storeLocatorMethod ="Search-Text";
				_AAData.storeLocatorTerm=q;
				_AAData.eventType="search-location";
				_satellite.track("dtm-event-search");
			}catch(e){}
		})
		
	},

	bindSearchCurrentLocation:function(){
		$(document).on('click','#findStoresNearMe',function(e){
		$(".clear-specialties, .specialties-name").remove();
       	$('.choose-specialty').prop("checked", false);
        ACC.storefinder.specialties = [];
        document.getElementById("storelocator-query").value = null
		$(".js-store-finder").hide();
			var miles = ACC.storefinder.getMiles();
			$(".alert").remove();
			ACC.storefinder.getInitStoreData(null,ACC.storefinder.coords.latitude,ACC.storefinder.coords.longitude,miles,ACC.storefinder.specialties);
			ACC.storefinder.loadGoogleMap();
			try {
				 
				var p=$("#analyticsstore").val();
				 _AAData.storeLocatorMethod = "Current Location";
				_AAData.storeLocatorTerm="";
				_AAData.eventType="search-location";
				_satellite.track("dtm-event-search");
			}catch(e){}
	})
	},

	bindToStoreFinderFooterForm: function () {
        var storeFinderFooterForm = $('.store_finder_form_footer');
        storeFinderFooterForm.ajaxForm({
        	beforeSubmit:ACC.product.showRequest,
        	success: ACC.storefinder.displayStoreFinderPopup
         });
        setTimeout(function(){
        	$ajaxCallEvent  = true;
        	//$(document).find(".saved-store").parents("li").addClass("list-bg");
         }, 2000);

        $(document.body).on("click",".removeMyStore",function()
				{
					$.colorbox({
						html: "<div class='PopupBox'><b>The branch will be deleted<br/><br/> " + $(this).data('town') + ", " + $(this).data('regionCode') + "</b> #" + $(this).attr("Id") +
							"<br/><b>" + $(this).data('name') + "</b><br/><br/>" + $(this).data('line1') + " " + $(this).data('line2') + "<br/>" +
							$(this).data('town') + ", " + $(this).data('regionCode') + " " + $(this).data('postalCode') +
							"<br/><br/></div><div class='confrimbtnBox'><div class='col-xs-12 col-sm-6 col-sm-push-6'><a href='"+ ACC.config.encodedContextPath + "/store-finder/remove-my-store/" + $(this).attr("Id") + "'" +
							"'><button type='button' id='delete' class='btn btn-ConfirmMessage btn-primary btn-block'>"+ACC.config.removeBranch+"</button></a></div><div class='col-xs-12 col-sm-6 col-sm-pull-6'><button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >"+ACC.config.cancelBranch+"</button></div></div>",
						maxWidth:"100%",
						width:"auto",
						maxHeight:"100%",
						overflow:"auto",
						opacity:0.7,
						className:"addNotFound",
						title:false,
						close:'<span class="glyphicon glyphicon-remove"></span>',
						onComplete: function(){
						}
					});
				});
        //$(document).find(".saved-store").parents("li").addClass("list-bg");
     },

     displayStoreFinderPopup: function (response, statusText, xhr, formElement) {
     	$ajaxCallEvent=true;
         /*$('#addToCartLayer').remove();
         if (typeof ACC.minicart.updateMiniCartDisplay == 'function') {
             ACC.minicart.updateMiniCartDisplay();
         }*/
         var titleHeader = "Find a branch";
         var data = JSON.parse(response);
         var listitems = "";
         if(data){
 			for(i = 0;i < data["data"].length;i++){
 				listitems += ACC.storefinder.createListItemHtml(data["data"][i],i,data.q,data.miles)
 			}

 			//$(".js-store-finder-navigation-list").html(listitems);


 		}
         ACC.colorbox.open(titleHeader, {
             html: listitems,
             width: "460px"
         });


     },


    getStoreData: function(page,seemore){
       // $("#noStoreResultsMsg").css("display","none");
        $(".js-store-finder").hide();
        $("#storeResultsMsg").html('');
        $(".store-spinner").show();
        ACC.storefinder.storeSearchData.page = page;
        loading.start();
        url = "/store-finder";
        $.ajax({
            url: url,
            cache: false,
            data: ACC.storefinder.storeSearchData,
            type: "get",
            success: function (response){
            	loading.stop();
                $(".store-spinner").hide();
                if(response.indexOf('<html') == -1){
                    $("#storeResultsMsg").html('');
                    $("#noStoreResultsMsg").html('');
                    $(".js-store-finder").show();
                    $(".js-store-finder-pager-next").show();
                    $('#findStoresNearMe').show();
                    $("#store-heading").show();
                    $("#searchAgain").hide();
                    $('.breadcrumb').hide();
                    $(".text-or").removeClass("hidden");
                    ACC.storefinder.storeDataTemp = ACC.storefinder.storeData;

                    ACC.storefinder.storeData = JSON.parse(response);
                    if(ACC.storefinder.storeDataTemp != "") {
                        var isExists = false;
                        $.each(ACC.storefinder.storeDataTemp.data, function( key, value ) {
                                if(value.name == ACC.storefinder.storeData.data[0].name) {
                                    isExists = true;
                                }
                            }
                        );
                        if(!isExists && seemore) {
                            $.merge(ACC.storefinder.storeDataTemp.data, ACC.storefinder.storeData.data);
                            ACC.storefinder.storeData.data = ACC.storefinder.storeDataTemp.data;
                        }
                    }
                    ACC.storefinder.refreshNavigation();
					/*if(ACC.storefinder.storeData.total - ACC.storefinder.storeData.data.length <= 10){
					 $(".js-store-finder-pager-next").hide();
					 $("#showAllStores").show();
					 }*/
                    if(ACC.storefinder.storeData.total == ACC.storefinder.storeData.data.length) {
                        $(".js-store-finder-pager-next").hide();
                    }
                    ACC.storefinder.loadGoogleMap();

                } else {
                    $("#noStoreResultsMsg").show();
                    $("#storeResultsMsg").html('');
                    $("#searchAgain").show();
                    if(ACC.storefinder.storeSearchData.q != '') {
                        $("#noStoreResultsMsg").html("<div class='headline2'> "+ACC.config.noLocationsFound+" </div><div class='col-md-6 col-sm-12 col-xs-12 noStoreResults'><p></p> "+ACC.config.noBranchFound+" " + ACC.storefinder.storeSearchData.miles +" "+ ACC.config.milesOf +" " + ACC.storefinder.storeSearchData.q +"."+ACC.config.noBranchFound1+" "
                            + " <span class='bold-text'><a class='tel-phone' href='tel:(800)748-3663'> 1-800-748-3663</a> (1-800-SITEONE)</span>.</div><br/>");
                        $(".text-or").addClass("hidden");
                        $(".no-Store").hide();
                    } else {
                        $("#noStoreResultsMsg").html("<div class='headline2'> "+ACC.config.noLocationsFound+" </div><div class='col-md-6 col-sm-12 col-xs-12' style='padding-left:0px'><p></p> "+ACC.config.noBranchFound +" " + ACC.storefinder.storeSearchData.miles +" "+ ACC.config.milesOf +"."+ACC.config.noBranchFound1+" "
                            + " <span class='bold-text'><a class='tel-phone' href='tel:(800)748-3663'>800-748-3663</a> (800-SITE-ONE)</span>.</div><br/>");
                        $(".text-or").addClass("hidden");
                        $(".no-Store").hide();
                    }

                    $(".js-store-finder").hide();
                    $("#storeResultsMsg").html('');
                    $('#printOption').hide();
                    $('.breadcrumb').show();

                }
            }
        }).always(function(){
        	loading.stop();
        });
    },

	getInitStoreData: function(q,latitude,longitude,miles,specialties){
		var tempstoreSpecialities;
		data ={
			"q":"" ,
			"miles":"",
			"page":0
		}
		if(miles != null){
			data.miles = miles;
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
		
		if(specialties.length != 0){
			var i =0;
        	specialties.forEach(function(item)
            	{ if(i == 0){
							tempstoreSpecialities = item;
							i++;
					}
				   else{
                  			tempstoreSpecialities =  tempstoreSpecialities + "," + item;
                    }
            	});
           data.storeSpecialities = tempstoreSpecialities;
		}

		ACC.storefinder.storeSearchData = data;
		ACC.storefinder.getStoreData(data.page,false);
		$(".js-store-finder-pager-prev").attr("disabled","disabled")
		$(".js-store-finder-pager-next").removeAttr("disabled")
	},

	storeDetailMap:function(){

		var sdlatitude=$("#storedetailMap").attr("data-myLatitude");
		var sdlongitude=$("#storedetailMap").attr("data-mylongitude");
			var mapProp= {
				zoom: 13,
				zoomControl: true,
				panControl: true,
				streetViewControl: false,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				center: new google.maps.LatLng(sdlatitude,sdlongitude),
			};
			var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
			var latlngbounds = new google.maps.LatLngBounds();
			 var marker, i;
			 	var name=$("#storedetailMap").attr("data-name");
			 	var line1=$("#storedetailMap").attr("data-line1");
			 	var line2=$("#storedetailMap").attr("data-line2");
			 	var town=$("#storedetailMap").attr("data-town");
			 	var phone=$("#storedetailMap").attr("data-phone");
			 	var regioncode=$("#storedetailMap").attr("data-regioncode");
			 	var postalcode=$("#storedetailMap").attr("data-postalcode");

				      marker = new google.maps.Marker({
				        position: new google.maps.LatLng(sdlatitude,sdlongitude),
				        map: map,
				        icon : "/_ui/responsive/theme-lambda/images/StoreLocator_Star_Green.png"
				      });

				      google.maps.event.addListener(marker, 'click', (function(marker, i) {
				        return function() {
				        	if(!line2)
				        		{
				                     infowindow.setContent('<div style="overflow: hidden;"><a href="" style="font-weight:bold">' + name + '</a><br/>' +  line1 +'<br/>'  +' '+  town +', ' +  regioncode+' ' +  postalcode+'<br/><a class="tel-phone" href="tel:' +phone+'">'+phone+'</a><br/>Get Directions From: </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + line1 + ' ' + line2 + ' ' + town + ' ' + regioncode + ' ' + postalcode +'"   class="blue getDirectionsLink" style="font-weight:bold">Get Directions</a></div>');
				        		}
				        	else
				        		{
				        		     infowindow.setContent('<div style="overflow: hidden;"><a href="" style="font-weight:bold">' + name + '</a><br/>' +  line1 +'<br/>'  + line2 +'<br/>' +  town +', ' +  regioncode+' ' +  postalcode+'<br/><a class="tel-phone" href="tel:' +phone+'">'+phone+'</a><br/>'+ ACC.config.getDirectionFrom +' </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + line1 + ' ' + line2 + ' ' + town + ' ' + regioncode + ' ' + postalcode +'"   class="blue getDirectionsLink" style="font-weight:bold">Get Directions</a></div>');
				        		}
				          infowindow.open(map, marker);
				        }
				      })(marker, i));

				      google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
					        return function() {
					        	if(!line2)
					        	  {
					                infowindow.setContent('<div style="overflow: hidden;"><a href="" style="font-weight:bold">' + name + '</a><br/>' +  line1 +'<br/>'  +' '+  town +', ' +  regioncode+' ' +  postalcode+'<br/><a class="tel-phone" href="tel:' +phone+'">'+phone+'</a><br/>'+ ACC.config.getDirectionFrom +' </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + line1 + ' ' + line2 + ' ' + town + ' ' + regioncode + ' ' + postalcode +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
					        	  }
					        	else
					        		{
					        		infowindow.setContent('<div style="overflow: hidden;"><a href="" style="font-weight:bold">' + name + '</a><br/>' +  line1 +'<br/>'  + line2 +'<br/>' +  town +', ' +  regioncode+' ' +  postalcode+'<br/><a class="tel-phone" href="tel:' +phone+'">'+phone+'</a><br/>Get Directions From: </br><input id="getdirections" type="text"><br/><a href="https://www.google.com/maps?hl=en&daddr=' + line1 + ' ' + line2 + ' ' + town + ' ' + regioncode + ' ' + postalcode +'"   class="blue getDirectionsLink" style="font-weight:bold">'+ACC.config.getDirection+'</a></div>');
					        		}
					          infowindow.open(map, marker);
					        }
					      })(marker, i));

				      latlngbounds.extend(marker.position);
				      var bounds = new google.maps.LatLngBounds();
						map.setCenter(latlngbounds.getCenter());
				        //map.fitBounds(latlngbounds);
				    	var infowindow = new google.maps.InfoWindow({
							//content: ACC.storefinder.storeData["data"][0].name + ACC.storefinder.storeData["data"][0].line1 + ACC.storefinder.storeData["data"][0].line2 + ACC.storefinder.storeData["data"][0].phone,
							disableAutoPan: true
						});
						google.maps.event.addListener(marker, 'click', function (){
							infowindow.open(map, marker);
						});
	},


	bindStoreStatusForMyStores: function ()
	{
		if(document.getElementById('listSize') != null)
		{
			var size = document.getElementById('listSize').value;
			for(i = 0 ; i < size ; i++)
			{
				var latitude = document.getElementById('storeLat' + i).value;
				var longitude = document.getElementById('storeLong' + i).value;
				var storeId = document.getElementById('storeIdForStatus' + i).value;
				ACC.mystores.storeStatusApiCall(latitude, longitude,null, storeId, "#status" + i);
			}
		}
	},
	
	init:function(){
		ACC.storefinder.specialties = [];
	    $("#findStoresNearMe").addClass("hidden");
		
		$.urlParam = function(queryparam){
		    var results = new RegExp('[\?&]' + queryparam + '=([^&#]*)').exec(window.location.href);
		    if (results==null){
		       return null;
		    }
		    else
		    {
		       return decodeURI(results[1]);
		    }
		}
		
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(
				function (position)
				{
					ACC.storefinder.coords = position.coords;
					console.log(ACC.storefinder.coords);
					$("#findStoresNearMe").removeClass("hidden");

					var miles = ACC.storefinder.getMiles();
					//var searchParams = new URLSearchParams(window.location.search);
					var searchText = $.urlParam('searchtext'); 
					var selectedMiles = $.urlParam('distance'); 
					
					if(searchText == '' || searchText == null || searchText == undefined)
					{
						if(window.location.href.indexOf('/store-finder/footer?query=&miles=100') != -1 || (window.location.href.indexOf('/store-finder') != -1 && window.location.href.indexOf('footer') == -1))
						{
							ACC.storefinder.getInitStoreData(null,ACC.storefinder.coords.latitude,ACC.storefinder.coords.longitude,miles,ACC.storefinder.specialties);
						}
					}
					else
					{
						$(".js-store-finder-search-input").val(searchText);
						$('#storelocatormiles-query option[value='+selectedMiles+']').attr('selected','selected');
						$('#storelocatormiles-query-mobile option[value='+selectedMiles+']').attr('selected','selected');
						ACC.storefinder.getInitStoreData(searchText,null,null,selectedMiles,ACC.storefinder.specialties);
					}
				},
				function (error)
				{
					console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
				}
				
			);
		}
		if($("#query")) {
			var q = $("#query").val();
			$("#query").val('');
			$(".js-store-finder-search-input").val(q);
			if(q.length>0){
				if(ACC.storefinder.validateSearchTerm(q))
				{
					ACC.storefinder.getInitStoreData(q,null,null,100,ACC.storefinder.specialties);
				}
			}
		}
	},
	
	storeNumberCss: function ()
	{
		 $(document).ready(function(){			  
		     $("span.store_details_name.headline").html(function(_, html){
		        return html.replace(/(\#\w+)/g, '<span class="store-number-class">$1</span>');   
		     });		 
		 });
	},
	
	bindBackButton: function ()
	{
		var showBackButton = true;
		if((window.location.href.indexOf('/store-finder') != -1) && (document.referrer.indexOf('/p') != -1) || (document.referrer.indexOf('/my-account/my-stores') != -1 && window.location.href.indexOf('/store-finder') != -1))
		{
			sessionStorage.setItem("prevReferrer",document.referrer);
		}
		
		if((sessionStorage.getItem('prevReferrer').indexOf('/p') == -1 && window.location.href.indexOf('/p') != -1) || (sessionStorage.getItem('prevReferrer').indexOf('/my-account/my-stores') != -1 && window.location.href.indexOf('/my-account/my-stores') != -1))
		{
			showBackButton = false;
		}
		
		if($('.alert-dismissable')[0] != undefined)
		{
			if(($('.alert-dismissable')[0].innerText).indexOf("Your default branch has been updated.") != -1 || ($('.alert-dismissable')[0].innerText).indexOf("Branch removed successfully.") != -1)
			{
				if(showBackButton == true && sessionStorage.getItem('prevReferrer') != null && sessionStorage.getItem('prevReferrer') != "" && sessionStorage.getItem('prevReferrer') != undefined)
				{
					var back =" <a href=" + sessionStorage.getItem('prevReferrer') + ">Click here</a> to go back.";
					$('.alert-dismissable').html($('.alert-dismissable').html() + back);
				}
			}
		}
		
		if((sessionStorage.getItem('prevReferrer').indexOf('/p') != -1 && window.location.href.indexOf('/p') != -1) || (sessionStorage.getItem('prevReferrer').indexOf('/my-account/my-stores') != -1 && window.location.href.indexOf('/my-account/my-stores') != -1))
		{
			sessionStorage.removeItem('prevReferrer');
		}
	},
	getCategoryStoreList:function(specialityStoreData,lat,lng){
		var listitems = "";
		loading.start();
		var page = $(".pagename").val().toLowerCase();
		$.ajax({
		   url: ACC.config.encodedContextPath + "/"+page+"/state?state="+specialityStoreData+"",
		           method: 'POST',
		           datatype: "json",
		           success: function(response){
		        	   loading.stop();
		                    var data=response;
		               if(data){
		            	  ACC.storefinder.loadGoogleMap(true,response);
		       	var i=0,j=0;
				$.each(data, function(index, element){
					$.each(element, function( key, value ) {
		          listitems += ACC.storefinder.createListItemHtml(value,i,specialityStoreData,100,true,lat,lng,j);
				  i++;	
				});
				j++;
				});
		        $(".js-store-finder-navigation-list").html(listitems);
		        $(".store__finderSpecialty").css("display", "block");
		        $(".green-background").css("display", "none");
		        $(".grey_bg").css("display", "none");
		        $(".pageSectionA").css("display", "none");
		        $(".map-store-containerSpecialty").css("display", "block");
		        $(".content").css("display", "none");
		        $(".select-location-btn").css("display", "none");
		        $(".content-area").css("display", "none");
		        $(".pom-data").css("display", "none");
		        // select the first store
		        
		        var firstInput= $(".js-store-finder-input")[0];
		        $(firstInput).trigger('click');
		           }
		           }
		});
		
	},
	bindSpecialtyStoreList: function(){
		$('.whereToBuy a').click(function(){
		   $('#selected').text($(this).text());
		 });

		$(".map-store-containerSpecialty").css("display", "none");
		$(document).on('click', '#stateData', function(){
		var hardscapeStoreData = $(this).text();
		if($('#User_Agent').val()=="SiteOneEcomApp"){
			var page = $(".pagename").val().toLowerCase();
			window.location.href = ACC.config.encodedContextPath + "/"+page+"/state?state="+hardscapeStoreData+"";
		}
		$(".state_title").html(hardscapeStoreData);
		$(".locationtitle").css("display", "block");
		$(".pom-headline").css("display", "block");
		$(".pom-banner").css("display", "block");
		var lat;
		var lng;
		if (navigator.geolocation)				
		{
	          navigator.geolocation.getCurrentPosition(function(position)			         
	          {			          
	        	  lat = position.coords.latitude;			        	  
	              lng = position.coords.longitude;			
	              ACC.storefinder.getCategoryStoreList(hardscapeStoreData,lat, lng);
	          })
	          ACC.storefinder.getCategoryStoreList(hardscapeStoreData,lat, lng);  
		}
		else{
		ACC.storefinder.getCategoryStoreList(hardscapeStoreData,lat, lng);
		}
		
		if (window.history && window.history.pushState) {
			var page = $(".pagename").val().toLowerCase();
		    window.history.pushState('forward', null, './'+page);

		    $(window).on('popstate', function() {
		     window.location.href=ACC.config.encodedContextPath +"/"+page;
		    });

		  }
		
		
		});
			if($(".page-plantsonmoveLander").length > 0){
				$(".pom-data").find(".banner-container").nextAll(".select-location-btn").addClass("second-select-btn");
				$(".map-store-containerSpecialty").eq(1).remove();
				$(".locationtitle").eq(1).remove();
				$(".column70").parent(".main-container").addClass("content-area");
				$(".store__finder--details").eq(1).remove();
			}
		},

		adjustSelectBoxLocations: function() {

			if($(".page-plantsonmoveLander").length > 0 && $('.smallsubhead').length > 0){
				var selectHTML = $('.smallsubhead').parents('.main-container').find('.select-location-btn').hide().html();
				$('.smallsubhead').after(selectHTML).parent().addClass('text-center');
				$('.banner-container .column80').attr('style', 'width: 100% !important;').after('<div class="column20">' + selectHTML + '</div>');
			}
		},
		bannerStoreFinder: function(num) {
			console.log("data-speciality",num);
			let targetLength = $("[data-speciality]").length;
			for(let i=0; i< targetLength; i++){
				let target = $("[data-speciality='"+ (i+1) +"']");
				if(i < num-1){
					target.addClass("hidden");
				}
				else{
					target.removeClass("hidden");
				}
			}
		},
		showSpecialtiesOption: function () {	
        	if(!($('.specialties-option').hasClass('hidden'))){
				if ($('.specialties-option').hasClass('showing')) {
           	 		$('.specialties-option').removeClass('showing').slideUp();
        		}
       	   		else {
             		  $('.specialties-option').addClass('showing').slideDown();
           		}
			}
    	},
      createFilters: function () {
			let target = $('.specialties-option');	
			target.removeClass('showing').slideUp();
        	let specialtiesHTML = "";
        	ACC.storefinder.specialties = [];
        	$('.choose-specialty').each(function () {
           	 	let target = $(this);
            	let selectedoption = target.parents(".specialty-name").find("img").attr("title");
            	let specialtyIcon = target.parents(".specialty-name").find("img").attr("src");
            	let filter = target.prop("name");
            	if (target.is(":checked")) {
               	 	specialtiesHTML += '<div class="col-md-3 col-xs-6 marginBottom10 specialties-name"><span class="selected-specialties"> <img src=' + specialtyIcon + '><span class="font-size-14 font-small-xs">' + selectedoption + '</span><button class="btn bg-white pull-right" onclick="ACC.storefinder.removeFilter(this,\'' + filter + '\')" ><span class="delete-specialty"></span></button></span></div>';
                	ACC.storefinder.specialties.push(filter);
            	}
        	});
        	if (specialtiesHTML != "") {
           		 specialtiesHTML = '<button class="hidden-sm hidden-md hidden-lg col-xs-4 btn bg-white clear-specialties" onclick="ACC.storefinder.clearFilters()">' + ACC.config.clearAll + '</button>' + specialtiesHTML + '<button class="col-md-1 hidden-xs visible-sm visible-md visible-lg bg-white clear-specialties" onclick="ACC.storefinder.clearFilters()">' + ACC.config.clearAll + '</button>';
           		 $(".specialties").html(specialtiesHTML).removeClass("hidden");
        	}
       	ACC.storefinder.specialtiesFilter();	
    },
    removeFilter: function (e, id) {	
        let target = $(e);
        target.parents(".specialties-name").remove();
        $("#" + id).prop("checked", false);
        if (!$(".specialties-name").length) {
            $(".clear-specialties").remove();
        }
        ACC.storefinder.specialties.splice(ACC.storefinder.specialties.indexOf(id), 1);
        ACC.storefinder.specialtiesFilter();	
    },
     clearFilters: function () {	
        $(".clear-specialties, .specialties-name").remove();
        $('.choose-specialty').prop("checked", false);
        ACC.storefinder.specialties = [];
        ACC.storefinder.specialtiesFilter();
    },
    specialtiesFilter: function () {
			var q = $(".js-store-finder-search-input").val();
			var miles = ACC.storefinder.getMiles();
			if(q.length>0)
			{
				ACC.storefinder.getInitStoreData(q,null,null,miles,ACC.storefinder.specialties);
			
			}
			else{
					ACC.storefinder.getInitStoreData(null,ACC.storefinder.coords.latitude,ACC.storefinder.coords.longitude,miles,ACC.storefinder.specialties);
			}
	},
	hideSpecialtiesOption: function(){
		$(document).click(function(){
			if($('.page-storefinderPage').length && !($('.specialties-option').hasClass('hidden'))){
				if ($('.specialties-option').hasClass('showing')) {
           	 		$('.specialties-option').removeClass('showing').slideUp();
        		}
			}
		});
		$('.store-finder-specialties-dropdown').click(function(e){
			e.stopPropagation();
		});
		$(document.body).on("click",'.Icon-StoreSpeciality',function(e)
        {
                    $(".Icon-StoreSpeciality-border").removeClass("Icon-StoreSpeciality-border");
                    $(this).find("img").addClass("Icon-StoreSpeciality-border");
        });
	},
	getMiles:function(){
		if($("#storelocatormiles-query").val() != "100"){
			return $("#storelocatormiles-query").val();
		}
		else{
			return $("#storelocatormiles-query-mobile").val();
		}
	},
	nearbyStoreGeoLocation:function(addressline,town,isocode,postalcode){
		var latitude =  null;
		var longitude = null;
		ACC.mystores.bindGeoLocatedStore();
		if (navigator.geolocation)
		{
		          navigator.geolocation.getCurrentPosition(function(position)
		          {
					latitude = position.coords.latitude;
					longitude = position.coords.longitude;
					window.open('https://www.google.com/maps?hl=en&saddr='+latitude+' '+longitude+'&daddr='+addressline+' '+town+' '+isocode+' '+postalcode, '_blank');
			     })
		}
		setTimeout(function(){
			if(latitude == null && longitude == null){
				window.open('https://www.google.com/maps?hl=en&daddr='+addressline+' '+town+' '+isocode+' '+postalcode, '_blank');
			}
		},700);
	},
};
