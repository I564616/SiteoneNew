var analyticsPaymentMethodCheck= false;
ACC.checkout = {

	_autoload: [
		["bindCheckoutEvents", $('.page-siteOneCheckoutPage').length],
		"bindCheckO",
		"bindForms",
		"bindSavedPayments",
		"checkPickUpInfoOrDate",
		"bindOrderType",
		"deliveryModeSelection",
		"bindOrderTypeFormSubmit",
		"countWordsForInstructions",
		"bindAddPhone",
		"bindCaliforniaWarning",
		"binbOrderSubmitMethod",
		"addNewAddressCheckout",
		"bindAddressValidations",
		"bindAddAddressSubmit",
		"bindSelectBranch",
		"checkHomeOwnerCase",
		"bindSearchpopups",
		"bindSearchpopup",
		"bindSaveCard",
		"bindSearchValue",
		"paymentContentLoad",
		"bindSaveCardEnabled",
		"bindSavedCardpopup",
		"bindPayOnAccountValue",
		"bindContactAddressValidations",
		"bindBranchPickupDetails",
		"bindEditFulFilmentDetails",
		"bindGuestUserValue",
		"checkoutStepEmailValidation",
		"nationalShippingChanges"
	],
	bindCheckoutEvents: function(){
		if($("#isGuestCCDisbaled").val() == 'Decline_Limit_Over'){
			ACC.checkout.isGuestCCDisbaled = true;
		}
		ACC.checkout.guestCCDisbale();
	},
	isGuestCCDisbaled: false,
	guestCCDisbale: function(){
		if(ACC.checkout.isGuestCCDisbaled){
			loading.stop();
			ACC.colorbox.open("", {
				html: $(".declineCcGuestError").html(),
				width: "600",
				escKey: false,
				overlayClose: false,
				className: "ccdisable-popup",
				onComplete: function(){

				},
				onClosed: function(){
					window.location.href = ACC.config.encodedContextPath + "/cart";
				}
			});
		}
	},
	b2bAddy1:'',b2bAddy2:'',b2bCity:'',b2bSiteoneState:'',b2bZip:'',siteoneAddJSONData:{},
	checkoutAddressNotSaved: false,
	checkIfFlorida :function(id, errorid, errorPath, errMsg, errStateMsg){
		
        ACC.formvalidation.vaildateShippingState(id, errorid, errorPath, errMsg, errStateMsg);

	},
	addErrorClassForCheckoutForm: function (input, error, errorText, msg)
	{
		$(error).html(msg);
		$(input).css("background", "#fec3c3");
		$(input).css("border-color", "#fd7b7b");
		$(input).parent().css("margin-bottom", "5px");
		$(error).css("color", "#fe0303");
		$(error).css("font-weight", "normal");
		$(errorText).html('');
	},
	
	removeErrorClassForCheckoutForm: function (input, error, errorText)
	{
		//alert("hi");
		$(error).html('');
		$(input).removeAttr('style');
		$(error).removeAttr('style');
		$(input).css("background", "#aaaaaaa");
		$(input).css("border-color", "#cccccc");
		$(input + "\\.errors").hide();
		$(errorText).html('');
	},
	bindSpecialInstruction: function(){
		var ref = $('#specialinstruction');
		ACC.checkout.bigbagSpecialInstruction();
		if (ref.length) {
			let text_length = ref.val().length;
			let text_allowed = ref.prop("maxlength");
			text_allowed = (text_allowed && text_allowed != "")? text_allowed : 300;
			if (text_length && text_length <= text_allowed ) {
				$('#wordcountstaticmessage').html(ACC.config.remaining + (text_allowed - text_length) + ' ' + ACC.config.characters);
				ACC.checkout.removeErrorClassForCheckoutForm('#specialinstruction', '#errorSpecialinstruction', '#specialinstruction\\.errors');
			}
			else if(text_length && text_length > text_allowed ){
				$('#wordcountstaticmessage').html(ACC.config.remaining + (0) + ' ' + ACC.config.characters);
				ACC.checkout.addErrorClassForCheckoutForm('#specialinstruction', '#errorSpecialinstruction', '#specialinstruction\\.errors', 'Special Instruction Error');
			}
			else {
				$('#wordcountstaticmessage').html(ACC.config.remaining + text_allowed + ' ' + ACC.config.characters);
				ACC.checkout.removeErrorClassForCheckoutForm('#specialinstruction', '#errorSpecialinstruction', '#specialinstruction\\.errors');
			}
		}
	},
	countWordsForInstructions: function() {
		$('#specialinstruction').on('keyup', function () {
			ACC.checkout.bindSpecialInstruction();
		});
		ACC.checkout.bindSpecialInstruction();
	},
	checkPONumberReq : function() {
		return new Promise((resolve, reject) => {
			if($("#poNumberReq").length){
				var poNumber = document.getElementById("poNumberReq").value;
				
				$.ajax({
					url: ACC.config.encodedContextPath+"/checkout/multi/order-type/poNumber?poNumber="+poNumber,
					method: "GET",
					success : function(response) {
						if(response == true) {
							$("#isPOValidated").val("true");
						} else {
							$("#isPOValidated").val("false");
						}
						resolve();
					}
				});
			}else{
				resolve();
			}
		});
	},
	bindForms:function(){
		$(document).on("click","#addressSubmit",function(e){
			e.preventDefault();
			$('#addressForm').submit();	
		})
		
		$(document).on("click","#deliveryMethodSubmit",function(e){
			e.preventDefault();
			$('#selectDeliveryMethodForm').submit();	
		})

	},
	
	
	bindSavedPayments:function(){
		$(document).on("click",".js-saved-payments",function(e){
			e.preventDefault();

			var title = $("#savedpaymentstitle").html();

			$.colorbox({
				href: "#savedpaymentsbody",
				inline:true,
				maxWidth:"100%",
				opacity:0.7,
				//width:"320px",
				title: title,
				close:'<span class="glyphicon glyphicon-remove"></span>',
				onComplete: function(){
				}
			});
		})
	},
	
	bindCheckO: function ()
	{
		var cartEntriesError = false;
		
		// Alternative checkout flows options
		$('.doFlowSelectedChange').on('change',function  ()
		{
			if ('multistep-pci' == $('#selectAltCheckoutFlow').val())
			{
				$('#selectPciOption').show();
			}
			else
			{
				$('#selectPciOption').hide();

			}
		});



		$('.js-continue-shopping-button').on('click',function  ()
		{
			var checkoutUrl = $(this).data("continueShoppingUrl");
			window.location = checkoutUrl;
		});
		
		$('.js-create-quote-button').on('click',function  ()
		{
			$(this).prop("disabled", true);
			var createQuoteUrl = $(this).data("createQuoteUrl");
			window.location = createQuoteUrl;
		});

		
		$('.expressCheckoutButton').on('click',function ()
				{
					document.getElementById("expressCheckoutCheckbox").checked = true;
		});
		
		$(document).on("input",".confirmGuestEmail,.guestEmail",function(){
			  
			  var orginalEmail = $(".guestEmail").val();
			  var confirmationEmail = $(".confirmGuestEmail").val();
			  
			  if(orginalEmail === confirmationEmail){
			    $(".guestCheckoutBtn").removeAttr("disabled");
			  }else{
			     $(".guestCheckoutBtn").attr("disabled","disabled");
			  }
		});
		
		$('.js-continue-checkout-button').on('click',function (e)
		{
			if ($('.loginstatus').val() == ''){
				$(".js-continue-checkout-button").addClass("signInOverlayCheckout_guest");
				sessionStorage.setItem('checkoutlogin',$(this).data("checkoutUrl"));
				ACC.cart.userType = "Checkout-Option";
   		} else {
			sessionStorage.removeItem('checkoutlogin');
			var californiaLocation= $("#california_location").val();
			ACC.cart.userType = "Checkout-Signed In";
			try { ACC.cart.cartPageTotal = $(".cart-totals").text().trim().replace('$', '').replaceAll(',', ''); }
			catch { ACC.cart.cartPageTotal = 0 }
			if(californiaLocation === 'CA' && $(".nearbyContinue").val()=="true") {
			
				var checkoutUrl = $(this).data("checkoutUrl");
				$.ajax({
			        success: function(result) {
			        	ACC.cart.prop65Overlay(checkoutUrl);
			        },
			    });
				return false;
			}
			if($(".nearbyContinue").val()=="false" && $(".checkmixedcart").val()=="false" && ($(".changed_selectedFilfillment").val()!='PARCEL_SHIPPING')){
					e.stopImmediatePropagation();
					ACC.cart.nearbyContinue();
					return false;
				}
			else{		
			var checkoutUrl = $(this).data("checkoutUrl");
			var cartEntriesError = ACC.pickupinstore.validatePickupinStoreCartEntires();
			if (!cartEntriesError)
			{
				ACC.cart.ga4AdobeAnalytics(ACC.cart.userType , $(".pagename").val(), [], "scCheckout", $("#cartID").val(), $("#recentCartIds").val(), ACC.cart.cartPageTotal, "signed-in");
				var expressCheckoutObject = $('.express-checkout-checkbox');
				
				if(expressCheckoutObject.is(":checked"))
				{
					window.location = expressCheckoutObject.data("expressCheckoutUrl");
				}
				else
				{
					var flow = $('#selectAltCheckoutFlow').val();
					if ( flow == undefined || flow == '' || flow == 'select-checkout')
					{
						// No alternate flow specified, fallback to default behaviour
						window.location = checkoutUrl;
					}
					else
					{
						// Fix multistep-pci flow
						if ('multistep-pci' == flow)
						{
						flow = 'multistep';
						}
						var pci = $('#selectPciOption').val();

						// Build up the redirect URL
						var redirectUrl = checkoutUrl + '/select-flow?flow=' + flow + '&pci=' + pci;
						window.location = redirectUrl;
					}
				}
			}
			return false;
			
			}
		}});

	},
	pickUpDateClass: '.show-pickupDate-picker',
	checkPickUpInfoOrDate: function(){
		let items = $('.isPickupDateRequired');
		if(items.length && $("#orderType").val() == 'PICKUP'){
			for (let i = 0; i < items.length; i++) {
				if (items.eq(i).val() == 'true') {
					ACC.checkout.pickUpDateClass = '.show-pickupDate-picker'; //reverted from show-pickupDate-message
					break;
				}
			}
			if (ACC.checkout.pickUpDateClass == '.show-pickupDate-picker') { 
				$('.isPickupDateRequired-date').fadeIn();
			}
		}
	},
	bindOrderType:function()
	{
		var radioValue = $("#orderType").val();
		
        if(radioValue == 'PICKUP')
        {
        	$(".delivery--content").hide();
        	$(".pickup--content").show();
			$(ACC.checkout.pickUpDateClass).show();
        	$(".common-content-pickupDelivery").show();
        	$(".delivery-chargesMsg").hide();
        	$(".icon-delivery-charges").hide();
        	$(".message-delivery").hide();
            $(".errorPickupDelivery").hide();
            $(".delivery-details").hide();
            $(".pickup-title").show();
            $(".charges-sec").hide();
            $(".delivery-fee").hide();
            $("#delivery_Details").hide();
            $("#pickup_Details").hide();
            
        }
        else if(radioValue == 'DELIVERY')
        {
        	$(".pickup--content").hide();
			$(ACC.checkout.pickUpDateClass).hide();
        	$(".delivery--content").show();
        	$(".common-content-pickupDelivery").show();
        	$(".delivery-chargesMsg").hide();
        	$(".delivery-details").show();
        	$(".icon-delivery-charges").show();
        	$(".message-delivery").show();
        	$(".errorPickupDelivery").hide();
        	$(".delivery-title").show();
        	$(".pickup-delivery").hide();
        	$(".pick-up-details").hide();
        	$(".delivery-info").hide();
        	$("#delivery_Details").hide();
        	$("#pickup").hide();
        	
        }else{
        	$("#shipping_Details").hide();
        	$(".delivery--content").hide();
        }

        $(".input-dateWrapper").off();
        $(".input-dateWrapper").on("click",function(){$(".input-dateWrapper #date").trigger("focus");});
       
	},

	validateCurrentDate: function()
	{
		var formDate = document.getElementById("date").value;
		var currdate =new Date();
		var formattedCurrDate = ACC.checkout.formatDate(new Date(currdate), 'mdy')
		var today = (formattedCurrDate === formDate);
		if(today)
		{
			if(!ACC.checkout.isAfternoonCheckoutAllowed() )
			{
				document.getElementById("date").value = null;
			}
		}
	},

	validateStoreStatus: function()
	{
		var schedule = JSON.parse(document.getElementsByClassName("openingSchedule")[0].value);
		if(schedule != undefined)
		{
			var weekday = ["Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"];
			jQuery.each(schedule.weekDayOpeningList, function(i, val)
			{
				if(val.closed)
				{
					if(document.getElementById("date").value != undefined)
					{
						if(val.weekDay == weekday[new Date(document.getElementById("date").value).getDay()])
						{
							document.getElementById("date").value = null;
						}
					}

				}
			});
		}
	},
	

	validateCheckoutFields: function(status)
	{
		var submit = true;
		
		    var orderType = $("#orderType").val();
		    var guestUser = $(".isguestuser").val();
		    if(null == orderType || orderType == "")
		    {
		    	ACC.checkout.addGlobalErrorForPickupDelivery();
		    }
		    else{
		    	
		    	var formDate = document.getElementById("date").value;
				var currdate =new Date();
				var formattedCurrDate = ACC.checkout.formatDate(new Date(currdate), 'mdy');
				var today = (formattedCurrDate === formDate);
				
				if(today)
				{
					if(!ACC.checkout.isAfternoonCheckoutAllowed())
					{
						$('#AM[value="AM"]').removeAttr('checked', 'checked');
						$('#AM[value="AM"]').attr('disabled', 'disabled');
					}
				}
				else
				{
					$('#AM[value="AM"]').removeAttr('disabled', 'disabled');
				}

				ACC.checkout.validateCurrentDate();

				if(orderType == 'PICKUP')
				{
					ACC.checkout.validateStoreStatus();
				}
				
				var date = document.getElementById("date").value;
				var isPastDate = false;
				if(null == date || date == "")
				{
				   	ACC.checkout.addErrorClassForCheckoutForm('#date', '#errorDate', '#date\\.errors', ACC.config.selectDeliveryPickup+'<div class="cl"></div>');
			    
				}
				else if (new Date(formDate) < new Date(formattedCurrDate)) {
					isPastDate = true;
					if(orderType == "PICKUP") {
						ACC.checkout.addErrorClassForCheckoutForm('#date', '#errorDate', '#date\\.errors', ACC.config.selectedPastPickupDate+'<div class="cl"></div>');
					}
					else if(orderType == "DELIVERY") {
						ACC.checkout.addErrorClassForCheckoutForm('#date', '#errorDate', '#date\\.errors', ACC.config.selectedPastDeliveryDate+'<div class="cl"></div>');
					}
				}
			    else{
			    	ACC.checkout.removeErrorClassForCheckoutForm('#date', '#errorDate', '#date\\.errors');
				}
				var isPONumberRequired = document.getElementById("poNumberRequired").value;
				if(isPONumberRequired == "true")
				{
					var poNumber = document.getElementById("poNumberReq").value;
					if(null == poNumber || poNumber == "")
					{
						ACC.checkout.addErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors', "Please enter a valid PO number. Call your company purchasing department if you need assistance.");
					}
					else
					{
						var isPOValidated = $("#isPOValidated").val();
						if(null != isPOValidated && isPOValidated == "true" )
						{
							ACC.checkout.removeErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors');

						}
						else
						{
							ACC.checkout.addErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors', "Please enter a valid PO number. Call your company purchasing department if you need assistance.");
						}
					}
				}

				var radioValue = $("input[name='requestedMeridian']:checked").val();
				if(null == radioValue)
			    {
			       	ACC.checkout.addErrorClassForCheckoutForm('#meridianRadio', '#errorMeridianRadio', '#meridianRadio\\.errors', ACC.config.selectMeridian+'<div class="cl"></div>');
			    }
			    else{
			    	ACC.checkout.removeErrorClassForCheckoutForm('#meridianRadio', '#errorMeridianRadio', '#meridianRadio\\.errors');
				}
			
				if($("#termsAndConditions").prop("checked") == false)
		        {
		        	$(".cr").addClass("error-div");
			    	$(".cr").click(function(){
			    		$(".cr").removeClass("error-div");
			    		});
		        	ACC.checkout.addErrorClassForCheckoutForm('#termsAndConditions', '#errorTermsAndConditionsCheckBox', '#termsAndConditions\\.errors', '<div class="cl"></div>'+ACC.config.termsAndConditions+'<div class="cl"></div>');
			    }
			    else
			    {
			    	ACC.checkout.removeErrorClassForCheckoutForm('#termsAndConditions', '#errorTermsAndConditionsCheckBox', '#termsAndConditions\\.errors');
			    	
				}
				
				if(guestUser == 'false' && orderType == "DELIVERY"){
					var address = $('#deliveryAddress').find(":selected").val();
		        	if(!ACC.checkout.checkoutAddressNotSaved && $("#deliveryAddress").val()== "selectDefault")
		            {
		        		$("#deliveryAddress").addClass("error-div");
				    	$("#deliveryAddress").click(function(){
				    		$("#deliveryAddress").removeClass("error-div");
				    		});
		            	ACC.checkout.addErrorClassForCheckoutForm('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors', ACC.config.selectDeliveryAddressToContinue+'.<div class="cl"></div><br/><br/>');
				    }
				    else{
				    	ACC.checkout.removeErrorClassForCheckoutForm('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors');
					}
		         }
				 else{
					if(guestUser == "false" && orderType == "PICKUP"){
						if(!ACC.checkout.checkoutAddressNotSaved && $("#splitDeliveryAddress").val()== "selectDefault")
							{
								$("#splitDeliveryAddress").addClass("error-div");
								$("#splitDeliveryAddress").click(function(){
									$("#splitDeliveryAddress").removeClass("error-div");
								});
								ACC.checkout.addErrorClassForCheckoutForm(".deliveryError",".deliveryError",'.deliveryError\\.errors', ACC.config.selectDeliveryAddressToContinue + '<div class="cl"></div>');
							}
							else{
								ACC.checkout.removeErrorClassForCheckoutForm('.deliveryError','.deliveryError','.deliveryError\\.errors');
							}
					}
					
				 }
				if(isPONumberRequired == "true")
				{
					if(orderType == "DELIVERY" && guestUser == 'false'){
						
						if((date != "" && !isPastDate)&&(null != radioValue)&& (null != isPOValidated && isPOValidated == "true") && (ACC.checkout.checkoutAddressNotSaved || $("#deliveryAddress").val()!= "selectDefault") && ($("#termsAndConditions").prop("checked") != false))
							{
									submit=false;
							}
						
					}else{
						if((date != "" && !isPastDate)&&(null != radioValue)&& (null != isPOValidated && isPOValidated == "true") && (ACC.checkout.checkoutAddressNotSaved || $("#splitDeliveryAddress").val()!= "selectDefault") && ($("#termsAndConditions").prop("checked") != false))
							{
									submit=false;
							}
					}
						
					}
					else{
						if(orderType == "DELIVERY" && guestUser == 'false'){
							
							
							if((date != "" && !isPastDate)&&(null != radioValue) && (ACC.checkout.checkoutAddressNotSaved || $("#deliveryAddress").val()!= "selectDefault") && ($("#termsAndConditions").prop("checked") != false))
							{
									submit=false;
							}
							
						}else{
							if((date != "" && !isPastDate)&&(null != radioValue) && (ACC.checkout.checkoutAddressNotSaved || $("#splitDeliveryAddress").val()!= "selectDefault") &&($("#termsAndConditions").prop("checked") != false))
							{
										submit=false;
							}
						}
						
					}
				// Bulk Delivery message check in checkout page start
				if($("#showAllDayOptions").val() == "true" && $("#specialinstruction").val().trim()==""){
					submit=true;
					ACC.checkout.addErrorClassForCheckoutForm('#specialinstruction', '#errorSpecialinstruction', '#specialinstruction\\.errors', ACC.config.messageForBranchAndDeliveryRequired);
				}
				// Bulk Delivery message check in checkout page end
				}
				
		    return submit; 
	},

	
	
	addGlobalErrorForPickupDelivery: function ()
	{
		if (($('.global-alerts > div.alert:contains("Please")').length == 0) && ($('.global-alerts > div.alert:contains("Cor")').length == 0))
		{
			
			$(".errorPickupDelivery").append("<div class='global-alerts container-lg container-fluid'>	<div class='alert alert-box alert-dismissable edit-user-alert'>" +
			 
			"<div class='msg alert-msg-text'>"+ACC.config.globalCorrectErrors+"</div></div></div>");
			$('html, body').animate({
			                scrollTop: $(".container").offset().top
			            }, 0);
		}
		else
		{
			$(".global-alerts").show();
			$('html, body').animate({
			                scrollTop: $(".container").offset().top
			            }, 0);
		}
	},
	bindBranchPickupDetails: function(){
		$(document).on("click","#branch_pickup_details_tab",function(e)
		{
			e.preventDefault();
    	    $.ajax({
    	            url : ACC.config.encodedContextPath +"/checkout/multi/branchPickUpDetails",
    	            method:"GET",
    	            success : function(response)
    	            {
    	            	$("#pickup-delivery").show();
	        			$("#termsPODiv").show();
	        			$(".direction-link").show();
	        			$(".edit-pickup-information").hide();
	        			 $(".input-dateWrapper").off();
	        		     $(".input-dateWrapper").on("click",function(){$(".input-dateWrapper #date").trigger("focus");});
    	            	window.location.href=ACC.config.encodedContextPath +response;
    	           
    	            }
    	    });
			
		});
	},
	formatDate:function (date, format)
	{
		  var monthNames = [
		    "January", "February", "March",
		    "April", "May", "June", "July",
		    "August", "September", "October",
		    "November", "December"
		  ];

		  var day = date.getDate();
		  var monthIndex = date.getMonth();
		  var month = date.getMonth() + 1;
		  var year = date.getFullYear();
		  var month = monthIndex + 1;

		  if(null != format && format == 'mdy')
		  {
			  return monthNames[monthIndex] + ' ' + day + ', ' + year;
		  }
		  else if(null != format && format == 'display')
		  {
			  return monthNames[monthIndex] + ' ' + day + ', ' + year;
		  }
		  else
		  {
			  return monthNames[monthIndex] + ' ' + day;
		  }
	},

	dateSchedule: function()
	{
		var dayArray=new Array();
		var schedule = JSON.parse(document.getElementsByClassName("openingSchedule")[0].value);
		if(schedule != undefined)
		{
			jQuery.each(schedule.weekDayOpeningList, function(i, val)
			{
				if(val.closed)
				{
					if(val.weekDay == "Sunday")
					{
						dayArray[0]=0;
					}
					else
					{
						dayArray[i+1]=i+1;
					}
				}
				else
				{
					if(val.weekDay == "Sunday")
					{
						dayArray[0]=9;
					}
					else
					{
						dayArray[i+1]=9;
					}
				}
			 });
			return dayArray;
		}
	},
	
	 populateContactAddress:function(contact){
		    if(contact != null && contact != '' && contact != undefined)
					{
						var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
						
						jQuery.each(customers, function(i, val)
						{
							if(val.displayUid === contact)
							{
								if(val.contactNumber != null && val.contactNumber != '' && val.contactNumber != undefined)
								{
									$(".delivery-phone").html("m: <a class='tel-phone' href='tel:"+val.contactNumber+"'>"+val.contactNumber+"</a>");
								}
								else
								{
									$(".delivery-phone").html("m: NA");
								}
							}
						 });
						
						
					}
		    }, 
	checkMissingBranchHours: function()
	{
		let isMissingBranchHours = true;
		var schedule = JSON.parse(document.getElementsByClassName("openingSchedule")[0].value);
		for (let i = 0; i < schedule.weekDayOpeningList.length; i++) {
			if(!schedule.weekDayOpeningList[i].closed){
				isMissingBranchHours = false;
				break;
			}
		}
		if(isMissingBranchHours){
			ACC.checkout.datepickerMinDate = 9;
			let newTiming = [true, false, false, false, false, true, true];
			document.getElementsByClassName("openingSchedule")[0].value = '{"code":"siteone-standard-hours-57","weekDayOpeningList":[{"weekDay":"Monday","closed":' + newTiming[0] + '},{"weekDay":"Tuesday","closed":' + newTiming[1] + '},{"weekDay":"Wednesday","closed":' + newTiming[2] + '},{"weekDay":"Thursday","closed":' + newTiming[3] + '},{"weekDay":"Friday","closed":' + newTiming[4] + '},{"weekDay":"Saturday","closed":' + newTiming[5] + '},{"weekDay":"Sunday","closed":' + newTiming[6] + '}],"specialDayOpeningList":[]}';
		}
	},
	datepickerMinDate: 1,
	deliveryModeSelection: function()
    {	
		// Parses "YYYY-MM-DD" as a local Date
		function parseLocalDate(isoDate) {
			const parts = isoDate.split('-'); // ["2025", "07", "03"]
			return new Date(Number(parts[0]), Number(parts[1]) - 1, Number(parts[2]));
		}
		// Backend holiday string (note it's a string, not an actual array)
		var HolidayDatesStr = $(".siteoneHolidays").val();
		// Wrap the dates in quotes and parse into an array of strings
		var HolidayDates = [];
		if(HolidayDatesStr){
			HolidayDates = JSON.parse(HolidayDatesStr.replace(/(\d{4}-\d{2}-\d{2})/g, '"$1"'));
		}
		// Create a Set of date strings in "YYYY-MM-DD" format
		var holidaySet = new Set(HolidayDates.map(function(dateStr) {
			const preferredLocalDate = parseLocalDate(dateStr);
			return preferredLocalDate.getFullYear() + '-' + (preferredLocalDate.getMonth() + 1).toString().padStart(2, '0') + '-' + preferredLocalDate.getDate().toString().padStart(2, '0');
		}));
		if($("#pageId").val() == 'siteOneCheckoutPage')
		{
			//ACC.checkout.checkMissingBranchHours();Removed checking missing branch hours logic as per SE-25190.
			var dateschedulearray = new Array();
			dateschedulearray = ACC.checkout.dateSchedule();
			var todaysDate = new Date();
			var tomorrow = new Date();
			var orderType = $("#orderType").val();
			tomorrow.setDate(tomorrow.getDate() + 1);
			if(orderType == 'PICKUP'){
				if(!ACC.checkout.isAfternoonCheckoutAllowed() || (!ACC.checkout.isMorningCheckoutAllowed() && ACC.checkout.isHalfDay(todaysDate))|| ((todaysDate.getDay() == 6 || todaysDate.getDay() == 0) && tomorrow.getDate() == 1) || (todaysDate.getDay() == 6 && tomorrow.getDate == 31)){
					ACC.checkout.datepickerMinDate = 1;
				}
				else if(ACC.checkout.isMorningCheckoutAllowed() || (ACC.checkout.isAfternoonCheckoutAllowed() && !ACC.checkout.isHalfDay(todaysDate))){
					ACC.checkout.datepickerMinDate = 0;
				}
				$("#date").datepicker({minDate: ACC.checkout.datepickerMinDate, maxDate:new Date(2100,11,31), dateFormat: "MM dd yy ",
					beforeShowDay: function (date) {
						var day = date.getDay();
						var dateStr = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
						var isHoliday = holidaySet.has(dateStr);
						var isClosed = (day == dateschedulearray[day]);

						// Disable the date if it's a closed weekday or a holiday
						return [!isClosed && !isHoliday];
					}
				});
			}
			else if (orderType == 'DELIVERY') { 
				let maxdate = new Date(2100, 11, 31);
				if ($("#bulkdelivery").val() == "true") {
					ACC.checkout.datepickerMinDate = 4;
					maxdate = 17;
				} else { 
					if (todaysDate.getDay() == 6) {
						ACC.checkout.datepickerMinDate = 2;
					}
					else if (todaysDate.getDay() == 0) {
						ACC.checkout.datepickerMinDate = 1;
					}
					else {
						if (ACC.checkout.isHalfDay(todaysDate)) {
							ACC.checkout.datepickerMinDate = 1;
						} else {
							if (ACC.checkout.isOrderPlacedBefore9()) {
								ACC.checkout.datepickerMinDate = 0;
								if (((todaysDate.getDay() == 6 || todaysDate.getDay() == 0) && tomorrow.getDate() == 1) || (todaysDate.getDay() == 6 && tomorrow.getDate == 31)) {
									ACC.checkout.datepickerMinDate = 1;
								}
							} else {
								ACC.checkout.datepickerMinDate = 1;
							}
						}
					}
				}
				
				$("#date").datepicker({minDate: ACC.checkout.datepickerMinDate, maxDate: maxdate, dateFormat: "MM dd yy ",
					beforeShowDay: function (date) {
						var day = date.getDay();
						var dateStr = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
						var isHoliday = holidaySet.has(dateStr);
						var isClosed = (day == dateschedulearray[day]);

						// Disable the date if it's a closed weekday or a holiday
						return [!isClosed && !isHoliday];
					}
				});
			}
			ACC.checkout.bindOrderType();

			var contact = null;
			if(null != $('#contactId').val() || $('#contactId').val() != "")
			{
			  contact = $('#contactId').val();
			}
			else
			{
			  contact = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value).displayUid;
			}

			ACC.checkout.populateContactDetails(contact);

			if(null != $('#addressId').val() && $('#addressId').val() != "")
			{
			  ACC.checkout.populateAddressDetails($('#addressId').val());
			}
			else
			{
			  var selectedValue = $('#deliveryAddress').find(":selected").val();
			  if(selectedValue != 'selectDefault')
			  {
				  $('#addressId').val(selectedValue);
				  ACC.checkout.populateAddressDetails($('#addressId').val());
			  }
			  else
			  {
				  $('.delivery-phone').html('');
			  }

			}
			ACC.checkout.removeErrorClassForCheckoutForm('#deliveryAddressRadio', '#errorDeliveryAddressRadio', '#deliveryAddressRadio\\.errors');
			if($('.isSplitCartCheckoutPickup').val() == 'true'){
				ACC.checkout.populateAddressDetails($('#splitDeliveryAddress').val());
			}
			
		}

		
		
		$(document.body).on("input blur","#poNumberReq",function()
				{
					ACC.checkout.removeErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors');
				});
	
		$(document.body).on("change",".requested-time",function()
		{
			ACC.checkout.removeErrorClassForCheckoutForm('#meridianRadio', '#errorMeridianRadio', '#meridianRadio\\.errors');
		});
		
		$(document.body).on("click",".saveCustomerSelection",function()
		{
			var selectedValue = $('#customers').find(":selected").val();
			$('#contactId').val(selectedValue);
			ACC.checkout.populateContactDetails(selectedValue);
			ACC.colorbox.close();
		});
		
		
		$(document.body).on("click",".saveCustomerSelectionB2bUser",function(e)
				{
					var selectedValue = $('#customers').find(":selected").val();
				
					  e.preventDefault();
			    	    $.ajax({
			    	            url : ACC.config.encodedContextPath + "/checkout/multi/update-contact?uid=" + selectedValue,
			    	            method:"POST",
			    	          
			    	            success : function(response)
			    	            {
			    	            	$('#contactId').val(selectedValue);
			    					ACC.checkout.populateContactDetails(selectedValue);
			    					ACC.colorbox.close();
			    					$(".data-update").hide();
			    	            }
			    	    })
					
					
					
					//ACC.checkout.enableDisableContinueButton();
				});
		
		$(document).on("change","#deliveryAddress",function()
		{	
			ACC.checkout.checkoutAddressNotSaved = false;
			ACC.checkout.removeErrorClassForCheckoutForm('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors');
			var selectedValue = $('#deliveryAddress').val();
			$('#addressId').val(selectedValue);
			ACC.checkout.populateAddressDetails($('#addressId').val());
			var orderType = $("#orderType").val();
			var state = $(".delivery-region").html();
			if(orderType === "PARCEL_SHIPPING" && (state!=_AAData.storeState)){
				var b2bSelectmsg = ERRORMSG.global.address;			
				var b2bSelectstateMsg = ERRORMSG.global.shippingStates;
				ACC.checkout.checkIfFlorida('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors', b2bSelectmsg, b2bSelectstateMsg)
			}
			
			var contact = $('#contactId').val();
			
			ACC.checkout.populateContactAddress(contact);
			//ACC.checkout.enableDisableContinueButton();
		});
 	   $(document).on("change","#splitDeliveryAddress",function(){
		var selectedValue = $('#splitDeliveryAddress').val();
		$('#shippingAddressId, #addressId').val(selectedValue);
		ACC.checkout.populateAddressDetails($('#shippingAddressId').val());
	   });
		$(document).on("change","#customers",function()
		{	
			var selectedValue = $('#customers').find(":selected").val();
			var selectedCustomer = null;
			if(document.getElementsByClassName("allCustomers")[0].value != "")
			{
				var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
				jQuery.each(customers, function(i, val)
				{
					if(val.displayUid === selectedValue)
					{
						selectedCustomer = val;
					}
				 });
			 }
			else
			{
					selectedCustomer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
			}
			
			if(null != selectedCustomer)
			{
				if(selectedCustomer.contactNumber == undefined)
				{
					var details = selectedCustomer.name + "<BR>m: " + "NA" + "<BR>" + selectedCustomer.displayUid;
					 $(".popup-contact-details").html(details);
				}
				else
				{
					 var details = selectedCustomer.name + "<BR>m: " + selectedCustomer.contactNumber + "<BR>" + selectedCustomer.displayUid;
					 $(".popup-contact-details").html(details);
				}
			}
			//ACC.checkout.enableDisableContinueButton();
		});
		
		$(document.body).on("click",".changeContact",function(e)
		{
			try{
					 _satellite.track('contactDetails');
					} catch (e) {}
			e.preventDefault();
			if(document.getElementsByClassName("allCustomers")[0].value != "")
			{
			    var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
				var option = null;
				var selectedCustomerVal = null;
				var selectedCustomer = $("#contactId").val();
				var orderType = this.id;
				var orderTypeDisplayText = "";
				if(orderType == 'pickUpChangeContact') {
					orderTypeDisplayText = "Pickup contact1";
				} else {
					orderTypeDisplayText = "Delivery contact";
				}
				
				jQuery.each(customers, function(i, val)
				{
					if(null != selectedCustomer && selectedCustomer != "" && val.email === selectedCustomer)
	            	{
						selectedCustomerVal = val;
						if(null != option)
						{
							option = option + '<option value="' + val.email + '"selected>' + val.name + '</option>';
						}
						else
						{
							option = '<option value="' + val.email + '"selected>' + val.name + '</option>';
						}
	            	}
					else
					{
						if(null != option)
						{
							option = option + '<option value="' + val.email + '">' + val.name + '</option>';
						}
						else
						{
							option = '<option value="' + val.email + '">' + val.name + '</option>';
						}
					}
				});
			}
			else
			{
				var customer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
				var option = null;
				var selectedCustomerVal = null;
				var selectedCustomer = customer;
				var orderType = this.id;
				var orderTypeDisplayText = "";
				if(orderType == 'pickUpChangeContact') {
					orderTypeDisplayText = "Pickup contact";
				} else {
					orderTypeDisplayText = "Delivery contact";
				}
				if(null != selectedCustomer && selectedCustomer != "" && customer.displayUid === selectedCustomer)
                {
						selectedCustomerVal = customer;
						if(null != option)
						{
							option = option + '<option value="' + customer.displayUid + '"selected>' + customer.name + '</option>';
						}
						else
						{
							option = '<option value="' + customer.displayUid + '"selected>' + customer.name + '</option>';
						}
			     }
				else
				{
					if(null != option)
					{
						option = option + '<option value="' + customer.displayUid + '">' + customer.name + '</option>';
					}
					else
					{
						option = '<option value="' + customer.displayUid + '">' + customer.name + '</option>';
					}
				}
			}
				var selectBox = "<select id='customers' class='form-control'>" + option + "</select>";
				if(null == selectedCustomerVal)
				{
					selectedCustomerVal = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
				}
				
				var details = selectedCustomerVal.name  + (selectedCustomerVal.contactNumber?"<BR>m: "+selectedCustomerVal.contactNumber:"<BR>m: NA " )+ "<BR>" +selectedCustomerVal.displayUid;
				if($(window).width()<=800){
					$("#colorbox #cboxMiddleLeft").remove();
					$("#colorbox #cboxMiddleRight").remove();
					
				}
				$.colorbox({
				html: "<div class='PopupBox pickOrDelivery-changeContact'>" +
						"<label for='customers'>" + orderTypeDisplayText  + "<br></label><div class='pickupContact'>" + selectBox +"</div><br/>" +
						"</div> " +
						"<div class='popup-contact-details'> "+ details + "</div>" +
						"<div class='row'>" +
						"<div class='confrimbtnBox col-xs-12 col-md-6 col-sm-12'>" +
						"<button type='button' id='save' class='saveCustomerSelection btn btn-ConfirmMessage btn-primary btn-block'>"+ACC.config.save+"</button>" +
						"</div>" +
						"<div class='confrimbtnBox  col-xs-12 col-md-6 col-sm-12'>" +
						"<button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >"+ACC.config.cancel+"</button>" +
						"</div>" +
						"</div>" ,
				maxWidth:"100%",
				width:"600px",
				maxHeight:"100%",
				overflow:"auto",
				opacity:0.7,
				title:"<h1 class='headline'>"+ACC.config.selectAnotherContact+"</h1>",
				close:'<span class="glyphicon glyphicon-remove"></span>',
				onComplete: function(){
					$("#colorbox").addClass("change-contact-colorbox");
					$("#colorbox").removeClass("addAddress-checkout");
					$("body").css("overflow-y", "hidden");
				},
				onClosed: function(){
					$("body").css("overflow-y", "auto");
					$("#colorbox").removeClass("change-contact-colorbox");
				}
				
				});
				
			//ACC.checkout.enableDisableContinueButton();
		});
		
		$(document.body).on("click",".changeContactb2b",function(e)
				{
			try{
					 _satellite.track('contactDetails');
					} catch (e) {}
					e.preventDefault();
					if(document.getElementsByClassName("allCustomers")[0].value != "")
					{
					    var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
						var option = null;
						var selectedCustomerVal = null;
						var selectedCustomer = $("#contactId").val();
						var orderType = this.id;
						var orderTypeDisplayText = "";
						if(orderType == 'pickUpChangeContact') {
							orderTypeDisplayText = "Pickup contact1";
						} else {
							orderTypeDisplayText = "Delivery contact";
						}
						
						jQuery.each(customers, function(i, val)
						{
							if(null != selectedCustomer && selectedCustomer != "" && val.email === selectedCustomer)
			            	{
								selectedCustomerVal = val;
								if(null != option)
								{
									option = option + '<option value="' + val.email + '"selected>' + val.name + '</option>';
								}
								else
								{
									option = '<option value="' + val.email + '"selected>' + val.name + '</option>';
								}
			            	}
							else
							{
								if(null != option)
								{
									option = option + '<option value="' + val.email + '">' + val.name + '</option>';
								}
								else
								{
									option = '<option value="' + val.email + '">' + val.name + '</option>';
								}
							}
						});
					}
					else
					{
						var customer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
						var option = null;
						var selectedCustomerVal = null;
						var selectedCustomer = customer;
						var orderType = this.id;
						var orderTypeDisplayText = "";
						if(orderType == 'pickUpChangeContact') {
							orderTypeDisplayText = "Pickup contact";
						} else {
							orderTypeDisplayText = "Delivery contact";
						}
						if(null != selectedCustomer && selectedCustomer != "" && customer.displayUid === selectedCustomer)
		                {
								selectedCustomerVal = customer;
								if(null != option)
								{
									option = option + '<option value="' + customer.displayUid + '"selected>' + customer.name + '</option>';
								}
								else
								{
									option = '<option value="' + customer.displayUid + '"selected>' + customer.name + '</option>';
								}
					     }
						else
						{
							if(null != option)
							{
								option = option + '<option value="' + customer.displayUid + '">' + customer.name + '</option>';
							}
							else
							{
								option = '<option value="' + customer.displayUid + '">' + customer.name + '</option>';
							}
						}
					}
						var selectBox = "<select id='customers' class='form-control'>" + option + "</select>";
						if(null == selectedCustomerVal)
						{
							selectedCustomerVal = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
						}
						
						var details = selectedCustomerVal.name  + (selectedCustomerVal.contactNumber?"<BR>m: "+selectedCustomerVal.contactNumber:"<BR>m: NA " )+ "<BR>" +selectedCustomerVal.displayUid;
						if($(window).width()<=800){
							$("#colorbox #cboxMiddleLeft").remove();
							$("#colorbox #cboxMiddleRight").remove();
							
						}
						$.colorbox({
						html: "<div class='PopupBox pickOrDelivery-changeContact'>" +
								"<label for='customers'>" + orderTypeDisplayText  + "<br></label><div class='pickupContact'>" + selectBox +"</div><br/>" +
								"</div> " +
								"<div class='popup-contact-details'> "+ details + "</div>" +
								"<div class='row'>" +
								"<div class='confrimbtnBox col-xs-12 col-md-6 col-sm-12'>" +
								"<button type='button' id='save' class='saveCustomerSelectionB2bUser btn btn-ConfirmMessage btn-primary btn-block'>"+ACC.config.save+"</button>" +
								"</div>" +
								"<div class='confrimbtnBox  col-xs-12 col-md-6 col-sm-12'>" +
								"<button type='button' id='cancel' class='btn btn-default btn-block closeColorBox' >"+ACC.config.cancel+"</button>" +
								"</div>" +
								"</div>" ,
						maxWidth:"100%",
						width:"600px",
						maxHeight:"100%",
						overflow:"auto",
						opacity:0.7,
						title:"<h1 class='headline'>"+ACC.config.selectAnotherContact+"</h1>",
						close:'<span class="glyphicon glyphicon-remove"></span>',
						onComplete: function(){
							$("#colorbox").addClass("change-contact-colorbox");
							$("#colorbox").removeClass("addAddress-checkout");
							$("body").css("overflow-y", "hidden");
						},
						onClosed: function(){
							$("body").css("overflow-y", "auto");
							$("#colorbox").removeClass("change-contact-colorbox");
						}
						
						});
						
					//ACC.checkout.enableDisableContinueButton();
				});
				
		
		$(document.body).on("change","#termsAndConditions",function()
		{
			//ACC.checkout.enableDisableContinueButton();
			ACC.checkout.removeErrorClassForCheckoutForm('#termsAndConditions', '#errorTermsAndConditionsCheckBox', '#termsAndConditions\\.errors');
		});
		
		 $(document).on("click","#termsAndConditionsPopup", function(e){
			    window.scrollTo(0,0);
			    e.preventDefault();
		        var url = ACC.config.encodedContextPath + "/checkout/multi/termsandcondition";
		        loading.start();
		        ACC.colorbox.open("",{
		            href: url,
		            maxWidth:"100%",
		            width:"621px",
		            overflow:"hidden",
		            height:"650px",
		            initialWidth :"380px",
		            onComplete:function(){ 
	                    loading.stop();
	                    $("#colorbox").addClass("termsAndConditionsBox");
	                },
	                onClosed: function(){
				       $("#colorbox").removeClass("termsAndConditionsBox");
					}
		        });
		    });
		 
		 $(document).on("click", ".termsAndConditionsClose", function()
		 {
	 			ACC.colorbox.close();
		 });
    },
    
   
    populateContactDetails:function(contact)
    {
    	var selectedCustomer = null;
    	if(document.getElementsByClassName("allCustomers")[0].value != "")
    	{
		var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
        
        jQuery.each(customers, function(i, val)
		{
	       	if(val.email === contact)
	       	{
	       		selectedCustomer = val;
	       	}
		});
    	}
    	else
    	{
    		selectedCustomer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
    	}
        
        if(null == selectedCustomer)
		{
        	selectedCustomer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
		}
        
        if(selectedCustomer.contactNumber == undefined)  
        {
        	var details = selectedCustomer.name + "<BR><span id='contactPhoneDetails'>M " + "NA" + "</span><BR>" + selectedCustomer.displayUid;
    	    $(".contact-details").html(details);
    	    
    	    $("#addPhoneMessage").show();
    	    $("#isContactRequirePhoneNumber").val("Y");
    	    $("#isAddPhoneNumberUsed").val("N");
    	    if($("#addressId").val() != null && $("#addressId").val() != undefined && $("#addressId").val() != "")
    	    {
    	    	$(".delivery-phone").html("MNA");
    	    }
    	    
        }
        else
        {
	        var details = selectedCustomer.name + "<BR>M <a class='tel-phone' href='tel:"+selectedCustomer.contactNumber+"'>"+selectedCustomer.contactNumber+"</a><BR>" + "<a href='mailto:"+selectedCustomer.displayUid+"'>"+selectedCustomer.displayUid+"</a>";
		    $(".contact-details").html(details);
		    $("#isContactRequirePhoneNumber").val("N");
		    $("#addPhoneMessage").hide();		
		    if($("#addressId").val() != null && $("#addressId").val() != undefined && $("#addressId").val() != "")
    	    {
		    	$(".delivery-phone").html("M <a class='tel-phone' href='tel:"+selectedCustomer.contactNumber+"'>"+selectedCustomer.contactNumber+"</a>");
    	    }
		    
        }
	},
	
	populateAddressDetails:function(address)
    {
		var selectedAddress = null;
		var addresses=[];
		var selectedCustomer = null;
		try{
			addresses= JSON.parse(document.getElementsByClassName("deliveryAddresses")[0].value);
			selectedCustomer = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value);
		}catch(e){
			addresses=[];
			console.log("JSON parse error");
		}
        
        jQuery.each(addresses, function(i, val)
		{
        	if(val.id === address)
	       	{
	       		selectedAddress = val;
				selectedCustomer = val;
	       	}
		});
	    if(null != selectedAddress)
	    {
	    var region="";
	    var fullName=""
	    if(selectedAddress.region && selectedAddress.region.isocodeShort){
	    	region= selectedAddress.region.isocodeShort ;
	    }
	    if(selectedAddress.title){
	    	fullName+=selectedAddress.title + ". ";
	    }
	    if(selectedAddress.firstName){
	    	fullName+=selectedAddress.firstName + " ";
	    }
	    if(selectedAddress.lastName){
	    	fullName+=selectedAddress.lastName;
	    }
	    if(fullName){
	    	fullName+=fullName+"<BR>";
	    }
	   // if(selectedAddress.phone){
	   // 	phone="m: " + "<a class='tel-phone' href='tel:" +selectedAddress.phone + "'>" + selectedAddress.phone + "</a>" + "<BR>";
	   // }
	      var details = fullName
	      			  + selectedAddress.line1 + "<BR>" 
	      			  + selectedAddress.town + ", "
	      			  + region + " " + selectedAddress.postalCode + "<BR>"
					  + "<div class='newDelivery-phone'>M: <a class='tel-phone' href='tel:"+selectedAddress.phone+"'>"+selectedAddress.phone+"</a></div>";
							
					 
					//else{
						$(".delivery-phone").hide();
					//}
	      			 // + phone
	      			 /* + "m: " + selectedCustomer.uid + "<BR>"*/
	      			  ;
	      
	      ACC.checkout.populateRegionDetails(region);
	      $(".delivery-address").html(details);
			if(!selectedCustomer.phone){
				$('.newDelivery-phone').hide();
			}
			else{
				$(".delivery-phone").hide();
			}
	    }
	},
	
	populateRegionDetails:function(region)
    {
		var regionValue = region;
		$(".delivery-region").html(regionValue);
    },
	
    populateNewRegionAddressForm:function(response){
		var region="";
		 if(response.addressData.region && response.addressData.region.isocodeShort){
		    	region= response.addressData.region.isocodeShort ;
		    }

		return region;
	},

	populateNewAddressForm:function(response){
		var details =  response.addressData.line1 + "<BR>" 
				+ response.addressData.town + ", "
				+ ACC.checkout.populateNewRegionAddressForm(response) + " " + response.addressData.postalCode + "<BR>";

		return details;
	},
	
	deliveryNewAddressShowForm:function(){
	  $(".delivery-Newaddress").hide();
   	  $(".delivery-Newregion").hide();
   	  $("#deliveryaddressdata").show();
   	  $("#deliveryaddressdat1").show();
   	  $("#deliverycitydata").show();
   	  $("#deliverystatedata").show();
   	  $("#deliveryzipdata").show();
	},
	
	deliveryNewAddressHideForm:function(){
	  $("#deliveryaddressdata").hide();
	  $("#deliveryaddressdat1").hide();
	  $("#deliverycitydata").hide();
	  $("#deliverystatedata").hide();
	  $("#deliveryzipdata").hide();
	},
	
	deliveryNewAddressDetailsShowForm:function(){
		  $(".delivery-Newaddress").show();
		  $(".delivery-Newregion").show();
	},
	briteVerifyEmailvalidation: function(emailId, status) {
		var briteverify = document.getElementById('briteVerifyEnable').value;
		var orderType = $("#orderType").val();
		let fullfillmentType = (orderType == "PARCEL_SHIPPING") ? "Shipping" : "Delivery";
		let isNationalShipping = ($(".isNationalShipping").length)? $(".isNationalShipping").val() : 'false';
		if (briteverify == "true") {
			if(!ACC.checkout.guestInterstatePilotShippingHubs && $(".isguestuser").val()== 'true' && orderType == "PARCEL_SHIPPING" && isNationalShipping == "false"){
				ACC.checkout.guestInterstatePilotShippingHubs = true;
			}
			url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email=' + emailId;
			$.ajax({
				url: url,
				type: "GET",
				timeout: 5000,
				success: function (response) {
					if (response == "invalid") {
						$("#error" + fullfillmentType + "EmailAddress").html("<div class=''><font color='red'><div class='panel-body'>" + ACC.config.emailInvalid + "</div></font></div>");
					}
					else if (status) {
						$.ajax({
							url: ACC.config.encodedContextPath + "/checkout/multi/validate-address",
							method: "POST",
							data: $('#SiteOneGC' + fullfillmentType + 'Form').serialize(),
							success: function (addressVerificationData) {
								ACC.address.isAddressChanged = false;
								ACC.address.isAddressVerified = true;
								if (addressVerificationData.status.errorCode == '404' || (addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || (ACC.checkout.guestInterstatePilotShippingHubs && addressVerificationData.isAddressValid && addressVerificationData.isAddressCorrected && addressVerificationData.state != _AAData.storeState)) { //proceed with the filled form address
									ACC.checkout.saveAlternateContactForm(fullfillmentType);
								}
								else { // create popup content address suggestion
									$.colorbox({
										html: ACC.checkout.createAddressPopupContent(addressVerificationData, fullfillmentType),
										maxWidth: "100%",
										width: "550px",
										maxHeight: "100%",
										overflow: "auto",
										opacity: 0.7,
										className: "addNotFound invalidOriginalAddress",
										title: false,
										close: '<span class="glyphicon glyphicon-remove"></span>'
									});
								}
							},
						});
					}
				},
				error: function (response, textstatus, message) {
					if (textstatus == "timeout" || textstatus == "error") {
						$("#error" + fullfillmentType + "EmailAddress").html("<div class=''><font color='red'><div class='panel-body'>" + ACC.config.emailAddressUnable + "</div></font></div>");
					}
				}
			});
		} else {
			ACC.checkout.saveAlternateContactForm(fullfillmentType);
		}
	},
	createAddressPopupContent: function(addressVerificationData, type) {
		var popupContent = "";
		if (!addressVerificationData.isAddressValid) {
			popupContent = "<p class='m-b-15'>" + ACC.config.veryfyAddressError + "</p><button class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.editAddress + "</button>";
		}
		else if (addressVerificationData.isAddressCorrected) {
			popupContent = "<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 no-padding-xs'><label class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/>" + ACC.config.originalAddress + "</label><div class='cl'></div><div class='pad-lft-25 marginTop10'><p class='m-b-0'>" + $("#SiteOneGC" + type + "Form #checkout" + type + "addressLine1").val() + "</p>";
			if ($("#SiteOneGC" + type + "Form #checkout" + type + "\\.addressLine2").val() != "") {
				popupContent += "<p class='m-b-0'>" + $("#SiteOneGC" + type + "Form #checkout" + type + "\\.addressLine2").val() + "</p>";
			}
			popupContent += "<p class='m-b-0'>" + $("#SiteOneGC" + type + "Form #checkout" + type + "city").val() + "</p><p class='m-b-0'>" + $("#SiteOneGC" + type + "Form #checkout" + type + "state option:selected").text() + "</p><p class='m-b-0'>" + $("#SiteOneGC" + type + "Form #checkout" + type + "zip").val() + "</p></div></div>";
			popupContent += "<div class='col-md-6 col-xs-12 m-t-20-xs no-padding-xs'><label class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/>" + ACC.config.suggestedAddress + "</label><div class='cl'></div>";
			popupContent += "<div class='pad-lft-25 marginTop10'><p id='suggestedAddress.line1' class='m-b-0'>" + addressVerificationData.street + "</p>";
			popupContent += "<p id='suggestedAddress.line2' class='m-b-0'>" + addressVerificationData.street2 + "</p>";
			popupContent += "<p id='suggestedAddress.city' class='m-b-0'>" + addressVerificationData.city + "</p>";
			popupContent += "<p id='suggestedAddress.state' class='m-b-0'>" + addressVerificationData.state + "</p>";
			popupContent += "<p id='suggestedAddress.county' class='m-b-0 hidden'>" + addressVerificationData.county + "</p>";
			popupContent += "<p id='suggestedAddress.zipcode' class='m-b-0'>" + addressVerificationData.zipcode + "</p></div></div>";
			popupContent += "<div class='cl'></div><div class='col-md-4 col-sm-6 col-xs-4 margin-top-20 m-t-10-xs'><div class='btn btn-primary btn-block' onclick='ACC.checkout.suggestedAddressSubmit(\"" + type + "\",\"" + addressVerificationData.street + "\",\"" + addressVerificationData.street2 + "\",\"" + addressVerificationData.city + "\",\"" + addressVerificationData.state + "\",\"" + addressVerificationData.zipcode + "\")' >" + ACC.config.save + "</div></div><div class='col-md-4 col-sm-6 col-xs-4 margin-top-20 m-t-10-xs'><div class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.cancel + "</div></div></form>";
		}
		return popupContent;
	},
	suggestedAddressSubmit: function(type, street, street2, city, state, zipcode) {
		if ($("input[name='address\\.addressSuggestionOption']:checked").val() == "suggestedAddress") {
			$("#SiteOneGC" + type + "Form #checkout" + type + "addressLine1").val(street);
            $("#SiteOneGC" + type + "Form #checkout" + type + "\\.addressLine2").val(street2);
            $("#SiteOneGC" + type + "Form #checkout" + type + "city").val(city);
            $("#SiteOneGC" + type + "Form #checkout" + type + "state").val(state);
            $("#SiteOneGC" + type + "Form #checkout" + type + "zip").val(zipcode);
		}
		ACC.colorbox.close();
		ACC.checkout.saveAlternateContactForm(type);
	},
	saveAlternateContactForm: function(type) {
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/saveAlternateContactDetails",
			method: "POST",
			data: $("#SiteOneGC" + type + "Form").serialize(),
			success: function (response) {
				let wTop = $("#SiteOneGCMain" + type + "Form").length ? $("#SiteOneGCMain" + type + "Form").offset().top : $("#siteOneOrderTypeForm").length ? $("#siteOneOrderTypeForm").offset().top : $("#SiteOneGC" + type + "Form").offset().top;
                wTop = (wTop - 10).toFixed(0);
				type = type.toLocaleLowerCase();
				$("." + type + "-Newregion").html(ACC.checkout.populateNewRegionAddressForm(response));
				$("." + type + "-Newaddress").html(ACC.checkout.populateNewAddressForm(response));
				if (type == "shipping") {
					$("#shipmentForm, #" + type + "addressdata, #" + type + "addressdat1, #" + type + "citydata, #" + type + "statedata, #" + type + "zipdata").hide();
					$("#" + type + "_div, #" + type + "_btn").show();
					$("." + type + "-ponumber").removeClass("hidden");
					$(".florida-error-msg").addClass("hidden");
				}
				else {
					$("#" + type + "_location").val(ACC.checkout.populateNewRegionAddressForm(response));
					ACC.checkout.deliveryNewAddressHideForm();
					$("#" + type + "ContactForm").hide();
					$("#" + type + "_content2, #termsPODiv, #pickup-" + type + ", .payment_cbtn").show();
				}
				$(window).scrollTop(wTop);
			}
		});
	},
	binbOrderSubmitMethod: function(e){
		
		$(document).on("blur", "#checkoutdeliveryphone", function(e)
				{
					var phoneNumber = $(this).val();
					if(phoneNumber != null && phoneNumber != '' && phoneNumber != undefined)
				    {
				        if (!ACC.formvalidation.validatePhoneNumber(phoneNumber))
		                {
		                    var msg= ACC.config.phoneNumberError ;
		                    ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors', msg);
		                }
		                else
		                {
		                    ACC.formvalidation.removeErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors');
		                }
				    }
				    else
				    {
		            	ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors', ERRORMSG.global.phone);
				    }
			    });
				
				$(document).on("input", "#checkoutdeliveryphone", function(e)
				{
					ACC.formvalidation.handlePhoneNumberOnInput('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors');
			    });

		$(document).on('input blur', "#checkoutdeliverycity", function(e)
		{
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutdeliverycity', '#errordeliveryCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutdeliverycity');
		});
		
		$(document).on("click", ".adddeliveryFormSubmit", function(e)
				{
			e.preventDefault();
			var status = true;
				ACC.formvalidation.vaildateFields('#checkoutdeliveryfirstName','#errordeliveryFirstName', '#checkoutdeliveryfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
				ACC.formvalidation.vaildateFields('#checkoutdeliverylastName','#errordeliveryLastName', '#checkoutdeliverylastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);	
				var msg = ERRORMSG.global.address;
		        ACC.formvalidation.vaildateFields('#checkoutdeliveryaddressLine1', '#errordeliveryAddressLine1', '#checkoutdeliveryaddressLine1\\.errors', msg);
		        ACC.formvalidation.vaildateFields('#checkoutDeliverystate', '#errordeliveryState', '#checkoutDeliverystate\\.errors', ERRORMSG.global.state);
				ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutdeliverycity', '#errordeliveryCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutdeliverycity');
					
				if(null != $("#checkoutdeliveryphone").val() && $("#checkoutdeliveryphone").val() != '')
			    {
			        if (!ACC.formvalidation.validatePhoneNumber($("#checkoutdeliveryphone").val()))
	                {
	                    var message= ACC.config.phoneNumberError ;
	                    status = false;
	                    ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors', message);
	                }
	                else
	                {
	                    ACC.formvalidation.removeErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors');
	                }
			    }
			    else
			    {
			    	status = false;
	            	ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryphone', '#errordeliveryPhoneNumber', '#checkoutdeliveryphone\\.errors', ERRORMSG.global.phone);
			    }
				if(!ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutdeliverycity', '#errordeliveryCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutdeliverycity'))
				{
					status = false;
				}
				if(ACC.formvalidation.validatezipcode(e, $("#checkoutdeliveryzip").val()))
				{
					ACC.formvalidation.removeErrorClassForRegForm('#checkoutdeliveryzip', '#errordeliveryZipcode', '#checkoutdeliveryzip\\.errors');
				}
				else
				{
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryzip', '#errordeliveryZipcode', '#checkoutdeliveryzip\\.errors',ACC.config.zipCodeError);
				}
				
				if(ACC.formvalidation.validateEmailPattern($("#checkoutdeliveryemail").val()))
			    {
					 ACC.formvalidation.removeErrorClassForRegForm('#checkoutdeliveryemail', '#errorDeliveryEmailAddress', '#checkoutdeliveryemail\\.errors', ERRORMSG.invalid.email);
				}
				else
				{
					status = false;
					 ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryemail', '#errorDeliveryEmailAddress', '#checkoutdeliveryemail\\.errors', ERRORMSG.invalid.email);
				}
				
				$('*[id*=error]:visible').each(function() 
						{
							if($(this).html() != '')
							{
								status = false;
							}
						});
				
				var deliveryEmail = $("#checkoutdeliveryemail").val();
				 if(status)	{
					 
					 ACC.checkout.briteVerifyEmailvalidation(deliveryEmail,status)
					 
				 }
			
	});
	},
	//Prop65 popup logic starts
	showProp65Popup:function(targetType){
		var californiaLocation= $("#california_location").val();
		var radioValue = $("#orderType").val();
		var isAddresshasCA; 
		
		 if(radioValue=='DELIVERY' || targetType == "delivery"){
			var stateValue = $(".delivery-region").length?$(".delivery-region").text():$("#delivery_location").val();
			var isAddresshasCA = (stateValue.indexOf("CA") != -1)?true:false;
		}
		
		else {
			
			  var pickupDetail = $("#pickup_location").val();
			  var isAddresshasCA = (pickupDetail.indexOf("CA") != -1)?true:false;
			
		 }
	
	
		
		if(sessionStorage.getItem("IsProp65Shown")!="true" && (californiaLocation == 'CA' || isAddresshasCA)) {
			$.ajax({
				success: function(result) {
					ACC.cart.prop65Overlay("#");
				},
				
			});
			
		
		}
	},	
	
	bigbagSpecialInstruction: function(){
	var specialinstructionTxt = $("#specialinstruction").val();
	var divider = ': Big Bag ';
	var bigbagLastIndex = specialinstructionTxt.split(divider);
	var txt = bigbagLastIndex.length > 1 ? bigbagLastIndex[1]: bigbagLastIndex[0];
	$("#specialinstruction").val(txt);
	},
	
	//Prop65 popup logic ends
	continueCheckoutProcess: function(e){
		ACC.checkout.poValueCheck();
		if($("#orderType").val() == 'PICKUP' || $("#orderType").val() == 'DELIVERY'){
			$("#termsAndConditions").trigger("click");
		}
		var submit = ACC.checkout.validateCheckoutFields();
		if(null != $("#requestedDateinUTC").val() && $("#requestedDateinUTC").val() != "")
		{
			var utcDateString = $("#requestedDateinUTC").val() + " " + "UTC";
			var localDate = new Date(utcDateString.toLocaleString());
			$("#requestedDateInLocal").html(ACC.checkout.formatDate(localDate, "display"));
		}
		
		if(submit)
		{
			ACC.checkout.addGlobalErrorForPickupDelivery();
			$(".errorPickupDelivery").show();
		}
		else
		{	
			var tt= $.trim($('#specialinstruction').val().replace(/\n/g," ").replaceAll("|","-"));
			$('#specialinstruction').val(tt);
			$(".orderTypeFormSubmit").removeAttr('disabled');
			if(null == $("#requestedDate").val() || $("#requestedDate").val() == "")
			{
				var reqDate = document.getElementById("date").value +" "+new Date().getFullYear();
				$("#requestedDate").val(new Date(reqDate));
			}
			
			var kount = sessionStorage.MercSessId;
			if($('.isSplitCartCheckoutPickup').val() == 'false'){
				e.preventDefault();
			}
			
			$.ajax({
				 url : ACC.config.encodedContextPath +'/checkout/multi/siteone-payment/setKountSessionId?kountSessionId='+kount,
					method:"POST"
									 
			});
			loading.start();
			var checkoutData;
			if($('.isSplitCartCheckoutPickup').val()=='true'){
				let splitOrderType = $("#orderType").val();
				let splitStoreId=$("#storeId").val();
				let splitContactId=$("#contactId").val();
				let splitAddressId= $("#addressId").val();
				let splitRequestedDate=$("#requestedDate").val();
				let splitDate=$("#date").val();
				let splitMeridianCode=$(".requested-time .selected-time input").val();
				let splitRequestedMeridian=$(".requested-time .selected-time input").val()
				let splitspecialInstruction=$("#specialinstruction").val();
				let splittermsAndConditions=$("#termsAndConditions").val();
				let splitkountSessionId=$("#kountSessionId").val();
				let shippingAddressId=$('#splitDeliveryAddress').val();
				let splitPurchaseOrderNumberOptionalSize = "";
				let splitPurchaseOrderNumberOptional=$("#PurchaseOrderNumber").val();
				if(splitPurchaseOrderNumberOptional != "" && splitPurchaseOrderNumberOptional != null){
					splitPurchaseOrderNumberOptionalSize = splitPurchaseOrderNumberOptional.length;
				}
				let splitPurchaseOrderNumberRequiredSize = "";
				let splitPurchaseOrderNumberRequired=$("#poNumberReq").val();
				if(splitPurchaseOrderNumberRequired != "" && splitPurchaseOrderNumberRequired != null){
					splitPurchaseOrderNumberRequiredSize = splitPurchaseOrderNumberRequired.length;
				}
				let splitPurchaseOrderNumber = "";
				if(splitPurchaseOrderNumberOptionalSize > 0) {
					splitPurchaseOrderNumber = splitPurchaseOrderNumberOptional;
				}
				else if(splitPurchaseOrderNumberRequiredSize > 0) {
					splitPurchaseOrderNumber = splitPurchaseOrderNumberRequired;
				}
				
				let bigbagInstruction=$(".isbigBagInstruction").val();
				if(bigbagInstruction)
				{
					splitspecialInstruction = bigbagInstruction.trim() + ' ' + splitspecialInstruction;
				}
				if($("#orderType").val() == "PICKUP"){
					checkoutData={
						orderType: splitOrderType,
						storeId: splitStoreId,
						contactId: splitContactId,
						addressId: splitAddressId,
						shippingAddressId:splitAddressId,
						requestedDate: splitRequestedDate,
						date: splitDate,
						meridiancode: splitMeridianCode,
						requestedMeridian: splitRequestedMeridian,
						specialInstruction: splitspecialInstruction,
						PurchaseOrderNumber: splitPurchaseOrderNumber,
						termsAndConditions: splittermsAndConditions,
						kountSessionId: splitkountSessionId
					}
				}
				if($("#orderType").val() == "DELIVERY"){
					checkoutData={
						orderType: splitOrderType,
						storeId: splitStoreId,
						contactId: splitContactId,
						deliveryAddressId: splitAddressId,
						shippingAddressId:splitAddressId,
						requestedDate: splitRequestedDate,
						date: splitDate,
						meridiancode: splitMeridianCode,
						requestedMeridian: splitRequestedMeridian,
						specialInstruction: splitspecialInstruction,
						PurchaseOrderNumber: splitPurchaseOrderNumber,
						termsAndConditions: splittermsAndConditions,
						kountSessionId: splitkountSessionId
					}
				}
			}else{
				var bigbagInstruction=($(".isbigBagInstruction").val() || '').trim();

				checkoutData=$('#siteOneOrderTypeForm').serialize().split('&').map((y)=>{
					if(bigbagInstruction.length){
					var yData = y.split("=")[1] || '';
					return y.startsWith('specialInstruction=') ? 'specialInstruction='+ bigbagInstruction + ' '+ yData : y
					}
					else{
						return y;
					}
					}).join('&');	
				
			}
			//creditcard Disable
			$.ajax({
				url: ACC.config.encodedContextPath + "/checkout/multi/saveFulfilmentOptions", 
				method:"POST",
				
				data:checkoutData,	
				
				success: function(response)
				{
					window.scrollTo(0,0);
					ACC.checkout.showProp65Popup();
					var orderType = $("#orderType").val();
					if(orderType == "PICKUP"){
						var requestedDate = new Date(response.cartData.requestedDate);
						
						var localDate = new Date(Date.UTC(requestedDate.getFullYear(), requestedDate.getMonth(), requestedDate.getDate(),  requestedDate.getHours(), requestedDate.getMinutes(), requestedDate.getSeconds(), requestedDate.getMilliseconds()));
						
						$("#requestedDateData").html(ACC.checkout.formatDate(localDate, 'mdy'));
						
						var requestedMeridian = response.cartData.requestedMeridian;
						if(requestedMeridian == "AM")
						{
							$("#requestedMeridianData").html("Morning");
						}else{
							$("#requestedMeridianData").html("Afternoon");
						}
						
						
						var taxAmount = response.cartData.totalTax.formattedValue;
						$("#taxValue").html(taxAmount);
						
						var totalAmountWithTax = response.cartData.totalPriceWithTax.formattedValue;
						$("#totalPriceWithTax").html(totalAmountWithTax);
						
						$("#pickup_Details").show();
						$("#requestedDateData").show();
						$("#requestedMeridianData").show();
						$(".direction-link").hide();
						$("#pickup-delivery").hide();
						$("#termsPODiv").hide();
						$("#pickup_Location_Div").hide();
						$(".edit-pickup-information").show();
						if($('.isSplitCartCheckoutPickup').val()=='true'){
						$(".splitShippingSection, .split-shipping-fee").addClass('hidden');
						$("#pickUpChangeContact, #PurchaseOrderNumber, #poNumberReq").addClass('hidden');
						$(".edit-split-shipping-information, .split-cart-contact-edit-info, .split-shipping-Details, .splitPurchaseOrderNumber").removeClass('hidden');
						var splitPONumber = '';
						if($("#PurchaseOrderNumber").val() != null && $("#PurchaseOrderNumber").val() != '')
						{
							splitPONumber = $("#PurchaseOrderNumber").val();
						}
						var splitPONumberReq = '';
						if($("#poNumberReq").val() != null && $("#poNumberReq").val() != '')
						{
							splitPONumberReq = $("#poNumberReq").val();
						}
						if(splitPONumber != "" && splitPONumber != null && splitPONumber.length > 0)
						{
							$(".splitPurchaseOrderNumber").html(splitPONumber);
						}
						else if(splitPONumberReq != "" && splitPONumberReq != null && splitPONumberReq.length > 0)
						{
							$(".splitPurchaseOrderNumber").html(splitPONumberReq);
						}
						}
					}else{
						 
						var requestedDate = new Date(response.cartData.requestedDate);
						var localDate = new Date(Date.UTC(requestedDate.getFullYear(), requestedDate.getMonth(), requestedDate.getDate(),  requestedDate.getHours(), requestedDate.getMinutes(), requestedDate.getSeconds(), requestedDate.getMilliseconds()));

						
						var requestedMeridian = response.cartData.requestedMeridian;
						if(requestedMeridian == "AM")
						{
							$("#requestedDeliveryDate").html(ACC.checkout.formatDate(localDate, 'mdy')+"| "+"Morning");
						}else{
							$("#requestedDeliveryDate").html(ACC.checkout.formatDate(localDate, 'mdy')+"| "+"Afternoon");
						}
						
						var addline1 = response.cartData.deliveryAddress.line1;
						var addline2 = response.cartData.deliveryAddress.line2;
						var city = response.cartData.deliveryAddress.town;
						var state = response.cartData.deliveryAddress.region.isocodeShort;
						$("#delivery_line1").html(addline1);
						$("#delivery_line2").html(addline2);
						$("#delivery_city").html(city);
						$("#delivery_state").html(state);
						$("#delivery_location").val(state);
						var postalCode = response.cartData.deliveryAddress.postalCode;
						$("#delivery_postalcode").html(postalCode);
						if($(".isSplitCartCheckoutPickupFE").val()=='true' && orderType == "DELIVERY"){
							var deliveryFees = response.cartData.deliveryFreight;
						}else{
							var deliveryFees = response.cartData.freight;
						}
						if(deliveryFees != null){
							$("#deliveryfees").html("$"+deliveryFees);
							$("#delivery_cost").html("$"+deliveryFees);
						}else{
							$("#deliveryfees").html("$0.00");
							$("#delivery_cost").html("$0.00");
							$(".delivery-fee-box").removeClass("hidden");
						}
						var taxAmount = response.cartData.totalTax.formattedValue;
						$("#taxValue").html(taxAmount);
						
						var totalAmountWithTax = response.cartData.totalPriceWithTax.formattedValue;
						$("#totalPriceWithTax").html(totalAmountWithTax);
						
						$("#delivery_fee").show();
					
						$("#delivery_content1").hide();
						$("#delivery_content2").hide();
						$("#pickup-delivery").hide();
						$("#delivery_Details").show();
						$(".edit-delivery-information").show();
						$("#termsPODiv").hide();
						$(".payment_cbtn").hide();
						if($('.isSplitCartCheckoutPickup').val()=='true' && $("#orderType").val() == "DELIVERY"){
							$("#deliveryChangeContact, #PurchaseOrderNumber, #poNumberReq").addClass('hidden');
							$(".split-cart-contact-edit-info, .splitPurchaseOrderNumber").removeClass('hidden');
							var splitPONumber = '';
							if($("#PurchaseOrderNumber").val() != null && $("#PurchaseOrderNumber").val() != '')
							{
								splitPONumber = $("#PurchaseOrderNumber").val();
							}
							var splitPONumberReq = '';
							if($("#poNumberReq").val() != null && $("#poNumberReq").val() != '')
							{
								splitPONumberReq = $("#poNumberReq").val();
							}
							if(splitPONumber != "" && splitPONumber != null && splitPONumber.length > 0)
							{
								$(".splitPurchaseOrderNumber").html(splitPONumber);
							}
							else if(splitPONumberReq != "" && splitPONumberReq != null && splitPONumberReq.length > 0)
							{
								$(".splitPurchaseOrderNumber").html(splitPONumberReq);
							}
						}
						
					}
					var guestUser=$(".isguestuser").val();
					var currentBaseStoreId=$(".currentBaseStoreId").val();
					
					if(guestUser== 'true'){
					transportKeyGCGenerate();
					
					$("#iframe_Popup_guest").show();
					if(currentBaseStoreId=='CA'){
						ACC.checkout.initGlobalPayment();			
					}
					 if(analyticsPaymentMethodCheck==false){
 						analyticsPaymentMethodCheck= true;
	 					try {
	 		            	  _satellite.track("paymentDetails");
	 		            } catch (e) {}
 					}
					}
					else if(guestUser== 'false'){
					paymenthB2BGenerate();
					
					 $(".choosepaymentB2b_GC").show();
					 if(analyticsPaymentMethodCheck==false){
						 analyticsPaymentMethodCheck= true;
						 try {
							  _satellite.track("paymentDetails");
						} catch (e) {}
					 }
					}
					
					if($('.currentBaseStoreId').val() == 'CA' && response.taxForCA ){
						var formatter = new Intl.NumberFormat('en-CA', {
						  style: 'decimal',
						  minimumFractionDigits: 2,
						  maximumFractionDigits: 2,
						});
						
						$(".vertex-item").remove();
						var vertexHtml = '';
						Object.keys(response.taxForCA).forEach(key => {
						        vertexHtml +='<div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sale-tax-summary vertex-item">';
			                    vertexHtml +='<div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">';
			                    vertexHtml +=key;
			                    vertexHtml +='</div>';
			                  	
			                    vertexHtml +='<div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">'
								vertexHtml +='<strong id="taxValue">$'+formatter.format(response.taxForCA[key])+'</strong>'
								vertexHtml +='</div>'
			                    vertexHtml +='</div>'
						})
						
						if(vertexHtml != ''){
							$(".sale-tax-summary").before(vertexHtml);
						}
					}
				}
			});

			if($('.isSplitCartCheckoutPickup').val()=='true'){
			$(document).on("click", "#split-shipping_Edit", function(e){
				$(".split-shipping-Details").addClass('hidden');
				$(".split-shipping-fee, .splitShippingSection").removeClass('hidden');
				$(".choosepaymentB2b_GC").hide();
			});
			$(document).on("click", "#split-cart-contact-edit", function(e){
				if($("#orderType").val() == "PICKUP") {
					$(".split-shipping-Details, .splitPurchaseOrderNumber").addClass('hidden');
					$("#pickUpChangeContact, #PurchaseOrderNumber, #poNumberReq").removeClass('hidden');
					$(".splitShippingSection, .split-shipping-fee").removeClass('hidden');
					//$(".split-shipping-fee").addClass('hidden');
					$(".choosepaymentB2b_GC").hide();
				}
				if($("#orderType").val() == "DELIVERY") {
					$(".splitPurchaseOrderNumber").addClass('hidden');
					$("#deliveryChangeContact, #PurchaseOrderNumber, #poNumberReq").removeClass('hidden');
					$(".choosepaymentB2b_GC").hide();
					$(".payment_cbtn").show();
				}
			});
		}
        }
	},
	initGlobalPayment: function(){
		var isGPInitialized = $("#isGPInitialized").val()
		
		if(isGPInitialized != '0'){
			return
		}
	   
	const cardForm = GlobalPayments.ui.form({
	      fields: {
	    	  "card-holder-name": {
	              placeholder: "Full Name",
	              target: "#GP-card-holder-name"
	           },
	         "card-number": {
	            placeholder:"●●●● ●●●● ●●●● ●●●●",
	            target: "#GP-card-number"
	         },
	         "card-expiration": {
	            placeholder: "MM / YYYY",
	            target: "#GP-card-expiration"
	         },
	         "card-cvv": {
	            placeholder: "●●●",
	            target: "#GP-card-cvv"
	         },
	         "submit": {
	            target: "#GP-submit-button",
	            text: "Submit"
	         }
	      },
	      styles: {
	    	    //adding styles to the global payment iframe
	    	    'input[type=text]': {
	    	      'margin-bottom': '10px !important',
	    	      padding: '12px',
	    	      border: '1px solid #ccc',
	    	      'border-radius': '10px',
	    	    },
	
	    	    //adding focus on the fields when selected
	    	    'input[type=text]:focus-visible, input[type=tel]:focus-visible': {
	    	      outline: 'none !important',
	    	      border: '1px solid #78a22f',
	    	      'box-shadow': '0 0 4px 0 #78a22f inset',
	    	    },
	
	    	    //adding a radius on all number inputs
	    	    'input[type=tel]': {
	    	      'margin-bottom': '10px !important',
	    	      padding: '12px',
	    	      border: '1px solid #ccc',
	    	      'border-radius': '10px',
	    	    },
	
	    	    //adding focus on the fields when selected
	    	    'button[type=button]:focus-visible': {
	    	      'background-color': '#78a22f',
	    	      outline: 'none !important',
	    	      border: '1px solid gray',
	    	      'box-shadow': '0 -1px 4px 0 gray inset',
	    	    },
	
	    	    //adding styling to the submit button
	    	    'button[type=button]': {
	    	      'background-color': '#78a22f',
	    	      color: 'white',
	    	      padding: '12px',
	    	      margin: '18px 10px 10px 10px',
	    	      border: 'none',
	    	      'border-radius': '10px',
	    	      cursor: 'pointer',
	    	      'font-size': '17px',
	    	    },
	
	    	    //adding hover color
	    	    'button[type=button]:hover': {
	    	      'background-color': '#78a22f',
	    	      'margin-bottom': '20px',
	    	    },
	
	    	    '.card-number.card-type-visa': {
	    	      background:
	    	        '#f3faff url(https://hps.github.io/token/gp-1.0.0/images/logo-visa@2x.png) no-repeat right',
	    	      'background-position-y': '0px',
	    	      'background-size': '70px 70px',
	    	    },
	    	    '.card-number.card-type-mastercard': {
	    	      background:
	    	        '#f3faff url(https://hps.github.io/token/gp-1.0.0/images/logo-mastercard@2x.png) no-repeat right',
	    	      'background-position-y': '5px',
	    	      'background-size': '60px 64px',
	    	    },
	    	    '.card-number.card-type-discover': {
	    	      background:
	    	        '#f3faff url(https://hps.github.io/token/gp-1.0.0/images/logo-discover@2x.png) no-repeat right',
	    	      'background-position-y': '-2px',
	    	      'background-size': '90px 80px',
	    	    },
	    	    '.card-number.card-type-jcb': {
	    	      background:
	    	        '#f3faff url(https://hps.github.io/token/gp-1.0.0/images/logo-jcb@2x.png) no-repeat right',
	    	      'background-position-y': '0px',
	    	      'background-size': '48px 82px',
	    	    },
	    	    '.card-number.card-type-amex': {
	    	      background:
	    	        '#f3faff url(https://hps.github.io/token/gp-1.0.0/images/logo-amex@2x.png) no-repeat right',
	    	      'background-position-y': '-2px',
	    	      'background-size': '55px 90px',
	    	    },
	
	    	    'input.invalid': {
	    	      '-webkit-box-shadow': 'inset 0px 0px 5px 0px #ff0a0a',
	    	      'box-shadow': 'inset 0px 0px 5px 0px #ff0a0a',
	    	      'border-color': '#ff8282',
	    	    },
	    	  },
	   		});
	
	
		cardForm.ready(()=> {
			$("#isGPInitialized").val('1');
		})
			
		GlobalPayments.configure({
		  'X-GP-Api-Key': ACC.config.gpApiKey,
	      'X-GP-Environment': ACC.config.gpApiEnv,
	      enableAutocomplete: true
	    });
	    
	    $(document).on("input blur", "#GP-b-streetAddress", function(e) {	
			ACC.formvalidation.vaildateFields('#GP-b-streetAddress', '', '', '')
	    });
	    
	    $(document).on("input blur", "#GP-b-zipCode", function(e) {
			var ele= $(this)	
			if(!ACC.formvalidation.validatezipcode(ele, ele.val())) {
				ele.parent().addClass("has-error");
			}else{
				ele.parent().removeClass("has-error");
			}
	    });
					    
		cardForm.on("token-success", (resp) => {
		   console.log("resp",resp)
			var isValid = true
			
			if(!ACC.formvalidation.vaildateFields('#GP-b-streetAddress', '', '', '')){
				isValid = false
			}
			
			var zipCodeEle = $("#GP-b-zipCode")
			
			if(!ACC.formvalidation.validatezipcode(zipCodeEle, zipCodeEle.val())) {
				isValid = false
				zipCodeEle.parent().addClass("has-error");
			}else{
				zipCodeEle.parent().removeClass("has-error");
			}
			
			if(!isValid) {
				return;
			}
			
		   loading.start();
	      $.ajax({
            url: ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/add-globalpayment",
            type: "GET",
            dataType: 'json',
            contentType: 'application/json',
            data: {
              "token": resp.temporary_token,
              "address": $('#GP-b-streetAddress').val(),
              "postalCode": zipCodeEle.val(),
              "cardType": resp.card.type,
          	  "cardNumber": resp.card.masked_card_number.slice(-4),
            },
            success: function (response) {
              loading.stop();
              if(response == "Success"){
               window.parent.location.href = ACC.config.encodedContextPath +"/checkout/multi/order-summary/view";
              }else{
				  $('#colorbox').addClass("gp-not-process");
			        var targetHTML = $(".gp-card-error").html();
			        ACC.colorbox.open("", {
			          html: '<div class="gp-card-error">' + targetHTML + '</div>',
			          width: 700,
			          escKey: true,
			          overlayClose: true,
			          onComplete: function () {
			            
			          }
			        });
			  }
            },
            error: function (response) {
				loading.stop();
               $('#colorbox').addClass("gp-not-process");
		        var targetHTML = $(".gp-card-error").html();
		        ACC.colorbox.open("", {
		          html: '<div class="gp-card-error">' + targetHTML + '</div>',
		          width: 700,
		          escKey: true,
		          overlayClose: true,
		          onComplete: function () {
		            
		          }
		        });
            }
          });
	   });
	
	   // add error handling if token generation is not successful
	   cardForm.on("token-error", (resp) => {
	      // TODO: Add your error handling
	      console.error(resp)
	   });
	
	
	},

	b2bIsFlorida:function(b2bState,currentTarget){
		let isNationalShipping = ($(".isNationalShipping").length)? $(".isNationalShipping").val() : 'false';
		if(isNationalShipping == 'false' && ((b2bState != _AAData.storeState && $(currentTarget).hasClass("new-address-region")===false) || b2bState != 'US-'+_AAData.storeState)){
			var stateMsg = ERRORMSG.global.shippingStates;
			ACC.formvalidation.addErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors', stateMsg);
		}else{
			ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors', stateMsg);
		}
	},
	bindAddAddressSubmit: function(e){
	
  $(document).on("change","#cboxLoadedContent #checkoutAddress\\.region", function(e){
			var orderType = $("#orderType").val();
			var state = $(this).val();
			if(orderType === "PARCEL_SHIPPING"){
				ACC.checkout.b2bIsFlorida(state,e.target);
			}
		});
		$(document).on("click", "#saveAddressInMyAddressBook", function (e) {
            if ($("#saveAddressInMyAddressBook").prop('checked')) {
                $("#defaultAddressInMyAddressBook").removeAttr('disabled');
                $(".default-address-left-label").removeClass('text-gray');
   
            } else {
				$("#defaultAddressInMyAddressBook").prop('checked', false)
                $("#defaultAddressInMyAddressBook").attr('disabled', 'true');
                $(".default-address-left-label").addClass('text-gray');
            }
		});
		
		$(document).on("click", ".addAddressFormSubmit", function(e)
		{
			
			e.preventDefault();

			var status = true;
			
	        ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.line1', '.errorline1', '#line1\\.errors', ERRORMSG.global.address);
	        ACC.formvalidation.validateFieldBasedOnRegex(e,'#cboxLoadedContent #checkoutAddress\\.townCity', '.errorTownCity', '#townCity\\.errors',ACC.config.checkoutCityMay,ERRORMSG.global.city, 'city');
	        
	        if($("#orderType").val() === "PARCEL_SHIPPING"){
	        	var state = $("#cboxLoadedContent #checkoutAddress\\.region").val();
				ACC.checkout.b2bIsFlorida(state,$("#cboxLoadedContent #checkoutAddress\\.region"));
			}else{
				ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors', ERRORMSG.global.state);
			}
	        ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.companyName', '.errorCompanyName', '#companyName\\.errors', ERRORMSG.global.company);
	        
	        if($("#cboxLoadedContent #checkoutAddress\\.phone").val() != null && $("#cboxLoadedContent #checkoutAddress\\.phone").val() != '' && !ACC.formvalidation.validatePhoneNumber($("#cboxLoadedContent #checkoutAddress\\.phone").val()))
	        {
	        	status = false;
	            ACC.formvalidation.addErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
	        }
	        else
	        {
	            ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	        }
	         
	        if(ACC.formvalidation.validatezipcode(e, $('#cboxLoadedContent #checkoutAddress\\.postcode').val()))
			{
				ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors');
			}
			else
			{
				status = false;
				ACC.formvalidation.addErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors', ACC.config.checkoutValidZip);
			}
	        $('span[class*="error"]').each(function() 
	    	    	{
	    	    		if($(this).html() != '')
	    	    		{
	    	    			status = false;
	    	    		}
			});
			ACC.checkout.b2bCompany=$("#siteOneAddressForm #checkoutAddress\\.companyName").val();
			ACC.checkout.b2bAddy1 = $("#siteOneAddressForm #checkoutAddress\\.line1").val();
			ACC.checkout.b2bAddy2 = $("#siteOneAddressForm #checkoutAddress\\.line2").val();
			ACC.checkout.b2bCity = $("#siteOneAddressForm #checkoutAddress\\.townCity").val();
			ACC.checkout.b2bSiteoneState = $("#siteOneAddressForm #checkoutAddress\\.region option:selected").text() 
			ACC.checkout.b2bZip = $("#siteOneAddressForm #checkoutAddress\\.postcode").val();
			ACC.checkout.b2bPhone = $("#siteOneAddressForm #checkoutAddress\\.phone").val();
			ACC.checkout.addressDataForm = $('#cboxLoadedContent #siteOneAddressForm').serializeArray();
			
			ACC.checkout.siteoneAddJSONData = {};
			$.each(ACC.checkout.addressDataForm, function () { ACC.checkout.siteoneAddJSONData[this.name] = this.value; })
			if (status) {
				
				
				$.ajax({
					url: ACC.config.encodedContextPath + "/checkout/multi/order-type/validate-address",
					method: "POST",
					data: ACC.checkout.siteoneAddJSONData,
					
					success: function (addressVerificationData) { 
						// addressVerificationData = {
						// 	"isAddressValid": true,
						// 	"isAddressCorrected": true,
						// 	"city": "Woodland Hills",
						// 	"county": "Los Angeles",
						// 	"state": "CA",
						// 	"street": "7 Olive Dr",
						// 	"street2": "",
						// 	"zipcode": "91364-5229",
						// 	"status": {
						// 		"code": " ",
						// 		"description": "",
						// 		"internalCode": "No Error"
						// 	}
						// }
						// console.log(addressVerificationData)
				
						ACC.address.isAddressChanged = false;
						ACC.address.isAddressVerified = true;
						 if((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404')
						 {
							 $.ajax({
								url: ACC.config.encodedContextPath + "/checkout/multi/order-type/add-address", 
								method:"POST",
								data: $('#cboxLoadedContent #siteOneAddressForm').serialize(),	
								beforeSend: function ()
								{
									loading.start();
								},
								success: function(response)
								{
									console.log(response)
									ACC.colorbox.close();
									if(null != response && response !="" && response != undefined) {
										$("#addAddressSuccess").html("<font color='green'><div class='panel-body'>"+ACC.config.addressCreated+"</div></font>");
										if ($("#cboxLoadedContent #saveAddressInMyAddressBook").is(":checked"))	{
											$("#deliveryAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
											if($('.isSplitCartCheckoutPickup').val() == 'true' && $("#orderType").val() == "PICKUP") {
												$("#splitDeliveryAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
											}
											$(".deliveryAddresses").val(response[1]);
											$("#deliveryAddress").change();
										} else {
											 var region="";
											 if(response[0].region && response[0].region.isocodeShort){
													region= response[0].region.isocodeShort ;
												}
											 var details =  response[0].line1 + "<BR>" 
															   + response[0].town + ", "
															   + region + " " + response[0].postalCode + "<BR>";
					 
											 ACC.checkout.populateRegionDetails(region);
											 $('#deliveryAddress option')[0].selected = true;
											 if($('.isSplitCartCheckoutPickup').val() == 'true' && $("#orderType").val() == "PICKUP") {
											 	$("#splitDeliveryAddress option")[0].selected = true;
											 }
											 $(".delivery-address").html(details);
											 
											 
												$('#addressId').val(response[0].id);
											if($('.isSplitCartCheckoutPickup').val() == 'true'  && $("#orderType").val() == "PICKUP") {
												$("#shippingAddressId").val(response[0].id);
											}
												var contact = $('#contactId').val();
												
												ACC.checkout.populateContactAddress(contact);
												ACC.checkout.checkoutAddressNotSaved = true;
										}
									}
									loading.stop();
				
								},
								error: function(response, textstatus, message) 
								{
								  loading.stop();
								  if(textstatus == "timeout" || textstatus == "error") 
								  {
									 $("#cboxLoadedContent .addAddressError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.unableToAdd+"</div></font></div>");
									} 
								},
								complete:function(response){
				
								}
							});
						 } else {
							 // create pop up content		        				        			
							 var popupContent = createPopupContent(addressVerificationData);			        			
							 //Popup for address suggestion
							 $.colorbox({
								 html: popupContent,					
								 maxWidth:"100%",
								 width:"550px",
								 maxHeight:"100%",
								 overflow:"auto",
								 opacity:0.7,
								 className:"addNotFound",
								 title:false,
								 close:'<span class="glyphicon glyphicon-remove"></span>',
								 onComplete: function(){
								 }
							 });
						 }
					},
					error: function() 
					{
						window.location.href = ACC.config.encodedContextPath + "/login";
					}
				})
	       }
		});
	},
	
	bindCaliforniaWarning: function(){
		$(document).on("click",".proceed-to-checkout-ca",function(e)
				{	e.preventDefault();
					$.colorbox.close();
				});
	},
	guestInterstatePilotShippingHubs: false,
	
	bindOrderTypeFormSubmit:function()
	{
		
	var orderType = $("#orderType").val();
		if ($(".isInDeliveryFeePilot").val() == "true") {
			$(".delivery-fee-box").addClass("hidden");
			if (orderType == "PARCEL_SHIPPING") {
				$(".checkout-shipping-fee-msg").addClass('hidden');
				if ($("#checkout-shipping-fee-msg-show").val() == 'true') { 
					$(".checkout-shipping-fee-msg").removeClass('hidden');
				}
	
			}
		}
		else if (orderType == "DELIVERY") {
			$(".delivery-fee-box").removeClass("hidden");
			$(".checkout-shipping-fee-msg").addClass('hidden');
		}
		else if (orderType == "PARCEL_SHIPPING") {
			$(".checkout-shipping-fee-msg").addClass('hidden');
			if ($("#checkout-shipping-fee-msg-show").val() == 'true') { 
				$(".checkout-shipping-fee-msg").removeClass('hidden');
			}

		}
		
		$(document).on("click",".addShippingMainFormSubmit",function(e)
		{
				
			ACC.checkout.guestInterstatePilotShippingHubs = false;
			ACC.checkout.checkPONumberReq()
				.then(() => {
			var status = true;
			let isNationalShipping = ($(".isNationalShipping").length)? $(".isNationalShipping").val() : 'false';
			let b2bmsg = ERRORMSG.global.address;
			let b2bstateMsg = ERRORMSG.global.shippingStates;
			if($(".isguestuser").val()== 'false'){
				var state = $(".delivery-region").html();
				let isPONumberRequired = $("#poNumberRequired").val();
				let poNumber = $("#poNumberReq").val();
				let isPOValidated = $("#isPOValidated").val();
				if(!ACC.checkout.checkoutAddressNotSaved && $("#deliveryAddress").val()== "selectDefault")
				{
					$("#deliveryAddress").addClass("error-div");
					$("#deliveryAddress").click(function(){
						$("#deliveryAddress").removeClass("error-div");
					});
					ACC.checkout.addErrorClassForCheckoutForm('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors', ACC.config.selectDeliveryAddressToContinue+'.<div class="cl"></div><br/><br/>');
					status = false;
				}
				else{
					ACC.checkout.removeErrorClassForCheckoutForm('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors');
				}
				if(isNationalShipping == 'false' && state != _AAData.storeState){
					ACC.checkout.checkIfFlorida('#deliveryAddress', '#errorDeliveryAddressRadio', '#deliveryAddress\\.errors', b2bmsg, b2bstateMsg)
			        status = false;
				}
				
				if (isPONumberRequired == "true") { //Shipping PO Number check
					if (null == poNumber || poNumber == "") {
						ACC.checkout.addErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors', ACC.config.ponumbererror);
						status = false;
					}
					else {
						if (null != isPOValidated && isPOValidated == "true") {
							ACC.checkout.removeErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors');
						}
						else {
							ACC.checkout.addErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors', ACC.config.ponumbererror);
							status = false;
						}
					}
					
				}
			}
			else if($("#orderType").val() == "PARCEL_SHIPPING" && isNationalShipping == 'false' && $('.shipmentcheck').prop("checked") && !ACC.formvalidation.vaildateShippingState('.addressDatadistrict', '#errorShippingState', '#checkoutShippingstate\\.errors', b2bmsg, '')){ //Guest can not Submit an interstate shipping order for Pilot Shipping Hubs (Tampa/LA)
				$(".shipmentcheck").attr("disabled", true).css("pointer-events","none");
				$(".florida-error-msg").removeClass("hidden");
				$("#shippingaddressdata, #shippingaddressdat1, #shippingcitydata, #shippingstatedata, #shippingzipdata").hide();
				ACC.checkout.showAddressForm();
				$("#checkoutShippingstate").val(_AAData.storeState);
				ACC.checkout.guestInterstatePilotShippingHubs = true;
				status = false;
			}
			if(status){
			loading.start();
			$.ajax({
				url: ACC.config.encodedContextPath + "/checkout/multi/saveFulfilmentOptions", 
				method:"POST",
	    		data:$('#SiteOneGCMainShippingForm').serialize(),	
	    		
	    		success: function(response)
	    		{
	    			window.scrollTo(0,0);
	    			ACC.checkout.showProp65Popup();
	    			var firstname = response.cartData.deliveryAddress.firstName;
        			$("#shipping_fname").html(firstname);
        			
        			var phonedata = response.cartData.deliveryAddress.phone;
        			$("#shipping_phone").html(phonedata);
        			
        			var emaildata = response.cartData.deliveryAddress.email;
        			$("#shipping_email").html(emaildata);
        			
        			var addressdata =  response.cartData.deliveryAddress.line1;
        			$("#shipping_line1").html(addressdata);
        			
        			var addressdata1 =  response.cartData.deliveryAddress.line2;
        			$("#shipping_line2").html(addressdata1);
        			
        			var citydata = response.cartData.deliveryAddress.town;
        			$("#shipping_city").html(citydata);
        			
        			var statedata = response.cartData.deliveryAddress.district;
        			$("#shipping_state").html(statedata);
					$("#delivery_location").val(statedata);
        			
        			var zipdata = response.cartData.deliveryAddress.postalCode;
        			$("#shipping_zipcode").html(zipdata);
        		
        			var shippingFees = response.cartData.freight;
        			if(shippingFees != null && shippingFees == "0.0"){
        				$("#shipping_cost").html("$0.00");
        			}else{
        				$("#shipping_cost").html("$"+shippingFees);
        			}
        			
        			var taxAmount = response.cartData.totalTax.formattedValue;
        			$("#taxValue").html(taxAmount);
        			
        			var totalAmountWithTax = response.cartData.totalPriceWithTax.formattedValue;
        			$("#totalPriceWithTax").html(totalAmountWithTax);
        			
        			
        			$("#shipping_div").hide();
        			$("#shipping_btn").hide();
        			$("#shipment_div2").hide();
        			$("#shipping_fee").hide();
        			$(".shipping-ponumber").addClass("hidden");
	    			$("#shipping_Details").show();
	    			$("#Shipping_Edit").show();
	    			$(".edit-shipping-information").show();
	    			
	    			var guestUser=$(".isguestuser").val();
        			if(guestUser== 'true'){
        			transportKeyGCGenerate();
        			
        			$("#iframe_Popup_guest").show();
        			 if(analyticsPaymentMethodCheck==false){
 						analyticsPaymentMethodCheck= true;
	 					try {
	 		            	  _satellite.track("paymentDetails");
	 		            } catch (e) {}
 					}
        			}
        			else if(guestUser== 'false'){
        			paymenthB2BGenerate();
        			 $(".choosepaymentB2b_GC").show();
        			 if(analyticsPaymentMethodCheck==false){
 						analyticsPaymentMethodCheck= true;
	 					try {
	 		            	  _satellite.track("paymentDetails");
	 		            } catch (e) {}
 					}
        		}
	    			
	    		}
	    	});
			}
			});
		});
		
		$(document).on("click",".orderTypeFormSubmit",function(e)
		{
			ACC.checkout.checkPONumberReq()
				.then(() => {
					ACC.checkout.continueCheckoutProcess(e);						
				});	
		})
	},
	
	bindAddPhone: function(){
		$(document).on("click",".contact-add-phoneNumber",function(e){
			e.preventDefault();
			$('#colorbox').addClass('addPhoneNumberNew');
			ACC.colorbox.open(
				$(this).data("cboxTitle"),
				{
					href: $(this).data("link"),
					width:"480px",
					onComplete: function(){  				
                    	 ACC.checkout.bindAddPhoneSuccess();
                    	 ACC.checkout.removeErrorClassForCheckoutForm('#phoneNo', '#errorPhoneNo', '#phoneNo\\.errors');
					}
				}
			);
		});
	},
	
	bindAddPhoneSuccess:function(){
		$("#addPhoneNumberEmailId").val($("#contactId").val());
		$("#siteOneAddPhoneNumberForm input[name=phoneNumber]").attr("placeholder","Phone Number");
		$('form#siteOneAddPhoneNumberForm').ajaxForm({
			beforeSend : function() {				
				loading.start();
			},
			success: function(data)
			{	
				$("#isAddPhoneNumberUsed").val("Y");
				$("#addPhoneMessage").hide();		
				//ACC.checkout.enableDisableContinueButton();
				loading.stop();
				var contactNumber = $("#user\\.phonenumber").val();
				$(".checkout_addPhoneNumber").replaceWith(data);				
				ACC.colorbox.resize();
				if ($(data).hasClass("addPhoneNumberSucess"))
				{
					ACC.checkout.bindPopulatePhoneNumber(contactNumber,$("#contactId").val());			
				}				
				ACC.checkout.bindAddPhoneSuccess();
			}
		});		 
	},
	
	bindPopulatePhoneNumber:function(contactNumber,contactId){
		$("#contactPhoneDetails").html("m: <a class='tel-phone' href='tel:"+contactNumber+"'>"+contactNumber+"</a>");
		var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
		jQuery.each(customers, function(i, customer)
		{	
			if(customer.displayUid === contactId)
			{				
				customer.contactNumber = contactNumber;
			}
			
		 });
		document.getElementsByClassName("allCustomers")[0].value = JSON.stringify(customers);
	},
	bindIsPhoneNumberChecked: function()
	{
		var isContactPhoneNumberRequired = $("#isContactRequirePhoneNumber").val();
		var isAddPhoneNumberUsed =  $("#isAddPhoneNumberUsed").val();
		if(isContactPhoneNumberRequired == "Y"){
			if(isAddPhoneNumberUsed == "Y"){
				return true;
			}else{
				return false
			}
		}else{
			return true;
		}
	},
	
	addNewAddressCheckout: function()
    {
		var addnewdeliveryaddresshtml = $('#addresscheckout').clone().html();
    	$(document).on("click", ".newaddress", function(e)
    	{
    		$('#addresscheckout').remove();
    		e.preventDefault();
    		var ordertype = $("#orderType").val();
	    	ACC.checkout.removeErrorClassForCheckoutForm('#deliveryAddressRadio', '#errorDeliveryAddressRadio', '#deliveryAddressRadio\\.errors');
	    	$("#deliveryAddress").removeClass("error-div");
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.companyName', '.errorCompanyName', '#companyName\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.line1', '.errorline1', '#line1\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.townCity', '.errorTownCity', '#townCity\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors');
	    	$("#colorbox").addClass("addAddress-checkout");
	    	$("#colorbox").removeClass("failedgeoLocation-box");
	    	if(ordertype == "PARCEL_SHIPPING" ||( $('.isSplitCartCheckoutPickup').val() == 'true'  && $("#orderType").val() == "PICKUP")){
	    		ACC.colorbox.open("", {
                    html: addnewdeliveryaddresshtml,
                    width: "550",
                    height: "auto",
                    title: "<div class='headline'>"+ACC.config.shippinglocationpop+"</div>",
                    close:'<span class="glyphicon glyphicon-remove"></span>',
                    
                    onComplete: function(){
		    			_AAData.popupPageName= ACC.config.addNewLocationPopup;
			        	_AAData.popupChannel= ACC.config.checkoutpathingChannel;
					 	try {
						    	 _satellite.track('popupView');
				        } catch (e) {}
		    		},
	    		});
	    	}else{
		    	ACC.colorbox.open("", {
	                    html: addnewdeliveryaddresshtml,
	                    width: "550",
	                    height: "auto",
	                    title: "<div class='headline'>"+ACC.config.deliverylocationpop+"</div>",
	                    close:'<span class="glyphicon glyphicon-remove"></span>',
	                    onComplete: function(){
			    			_AAData.popupPageName= ACC.config.addNewLocationPopup2;
				        	_AAData.popupChannel= ACC.config.checkoutpathingChannel;
						 	try {
							    	 _satellite.track('popupView');
					        } catch (e) {}
			    		},
	                     
	            });
	    	}
    	});
     },
	
	bindAddressValidations: function ()
	{
    	$(document).on("blur", "#cboxLoadedContent #checkoutAddress\\.phone", function(e)
    		{ 
    		    var phoneNumber = $(this).val();
    		    if (phoneNumber != "" && !ACC.formvalidation.validatePhoneNumber(phoneNumber))  
    		    {
    		       ACC.formvalidation.addErrorClassForRegForm('#cboxLoadedContent #checkoutAddress \\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
    		    }
    		    else
    		    {
    		    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
    		     }
    		 });
    	$(document).on('input blur', "#cboxLoadedContent #checkoutAddress\\.townCity", function(e)
    		{
    				ACC.formvalidation.validateFieldBasedOnRegex(e,'#cboxLoadedContent #checkoutAddress\\.townCity', '.errorTownCity', '#townCity\\.errors',ACC.config.checkoutCityMay,ERRORMSG.global.city, 'city');
    		});
    	$(document).on('input blur', "#cboxLoadedContent #checkoutAddress\.companyName", function(e)
    		{
    				ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.companyName', '.errorCompanyName', '#companyName\\.errors',ERRORMSG.global.company);
    		});
    	$(document).on('input blur', "#cboxLoadedContent #checkoutAddress\\.line1", function(e)
    		{
    				ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.line1', '.errorline1', '#line1\\.errors',ERRORMSG.global.address);
    		});
    	$(document).on('input blur', "#cboxLoadedContent #checkoutAddress\\.region", function(e)
    		{
    		var orderType = $("#orderType").val();
    		if(orderType === "PARCEL_SHIPPING"){
	        	var state = $("#cboxLoadedContent #checkoutAddress\\.region").val();
				    ACC.checkout.b2bIsFlorida(state,e.target);
			}else{
				ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors', ERRORMSG.global.state);
			}
    				
    		});
    	$(document).on("input blur", "#cboxLoadedContent #checkoutAddress\\.postcode", function(e)
    		{	
    				if(ACC.formvalidation.validatezipcode(e, $("#cboxLoadedContent #checkoutAddress\\.postcode").val()))
    				{
    					ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors');
    				}
    				else
    				{
    					ACC.formvalidation.addErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors', ACC.config.zipCodeError);
    				}
    		});

	},
	  
	
	  bindSaveCard:function(){
		  $(document).on("blur", ".card_holder_nickname", function(e)
	    	        { 
	    	  var nickName=$(".card_holder_nickname").val(); 
	      	  e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath +'/checkout/multi/siteone-payment/setSaveCardInSession?nickName='+nickName+'&saveCard=true',
	    	            method:"POST",
	    	            success : function(response)
	    	            {
	    	            	if (response == true) {
	    	            	$(".card_holder_nickname").val('');
			    	            $("#savecard_check" ).prop( "checked", false );
			    	            $( ".card_holder_nickname" ).prop( "disabled", true );
	    	            }
	    	            }
	    	   
	    	        });
		      
	    	        });
	  },
	  bindSaveCardEnabled:function(){
		  $(document).on("click", " #savecard", function(e)
	    	        {
			  $( ".card_holder_nickname" ).prop( "disabled", false );
	    	        });
	    	        },
	 

	bindSearchpopups: function () {

		$(document).on('click', '.new-credit-card', function(e) {
		      e.preventDefault();
		      e.stopImmediatePropagation()
					
						loading.start();
		     
						$.ajax({
							url: ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/getBoardCardTransportKey?isCheckout="+true,
							method: "POST",
		          
							success: function (transportKey) {
								
								if (transportKey != null && transportKey != "Decline") {
									loading.stop();
									var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey=' + transportKey;
									
									
			    	         
			    	            $(".Pop-up-myIframe").attr('src', redirecturl );
			    	            $("#iframe_Popup").show();
			    	            $("#paymentFailedGc").hide();
			    	            $("#cayanFailure").hide();
			    	          $(".card_holder_nickname").val('');
			    	          $( ".card_holder_nickname" ).prop( "disabled", true );
			    	            $("#savecard_check" ).prop( "checked", false );
			    	           
			    	            }
			    	            }
			    	        });
			    	            
			    	        });
	},
	
	paymentRadioButton: function (id,event) 
	{
		
			if (id.val() == 'PAY_ONLINE_WITH_CREDIT_CARD' && $("#asmSession").val() == 'false') {
				if($("#showCreditCard").val() == 'true'){
				$(".payment-to-branch").hide();
				$(".payment-to-online").show();
				$(".payment-to-acount").hide();
				}
				$('#POA_terms').hide();
				$("#payAtBranch").hide();
				$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineAccount-border").hide();
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .payOnlineCreditCard-border").show();
				$("#payOnline").removeClass("hidden");
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .card-selected").show();
				$("#iframe_Popup").hide();	
				$("#paymentFailedGc").hide();
				 $("#cayanFailure").hide();
				 $("#cardselectbox").val('');
				 $(".card_holder_nickname").val('');
 	            $("#savecard_check" ).prop( "checked", false );
 	           $( ".card_holder_nickname" ).prop( "disabled", true );
			   
			   var currentBaseStoreId=$(".currentBaseStoreId").val();
			   if(currentBaseStoreId=='CA'){
			   		ACC.checkout.initGlobalPayment();			
			   }
				
			} else if(id.val() == 'SITEONE_ONLINE_ACCOUNT'){
				var paymentType= id.val();			
				$('#paymentMethodValue').val(paymentType);
				$('#POA_terms').show();
				$(".payment-to-branch").hide();
				$(".payment-to-online").hide();
				$(".payment-to-acount").show();
				$('.payment-to-acount').attr('disabled', false);
				$(".step-head-checkout").show();
				$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineAccount-border").show();
				$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineCreditCard-border").hide();
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .payOnlineCreditCard-border").hide();
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .card-selected").hide();
				$("#paymentFailedGc").hide();
				 $("#cayanFailure").hide();
				loading.start();
				$.ajax({
    	            url : ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/getAccountInformation",
    	            method:"GET",
    	            success : function(response)
    	            {
    	            	if(null != response && response !="" && response != undefined) {
    	            		loading.stop();
				var opentobuyAmount = response.customerCreditInfo.openToBuy;
				opentobuyAmount = parseFloat(opentobuyAmount);
				$("#openToBuy").val(opentobuyAmount);
				var orderTotal = $('#orderTotal').val();
				orderTotal = parseFloat(orderTotal);
				var termsCode = response.customerCreditInfo.creditTermDescription;
				$("#termsCode").val(termsCode);
				var creditCode = $('#creditCode').val();
				var maxOpenToBuyAmount =  response.customerCreditInfo.maxOpenToBuy;
				$("#maxOpenToBuy").val(maxOpenToBuyAmount);
				if(event =='load')
				{	
					var openToBuyValue=$("input#openToBuy").val();
					$(".openToBuyValue").html(openToBuyValue);
					var termsCodeValue=$("input#termsCode").val();
					$(".termsCodeValue").html(termsCodeValue);
				}
				if(creditCode == "T" || response.customerCreditInfo.creditCode == "T"){
					$(".payment-to-acount").prop("disabled", false);
					$(".payOnline-account-failure").hide();
					$(".payOnline-account-failure-term").hide();
					$('.payment-openToBuy').removeClass('error-message-open-to-buy');
					$('.pay-account-text-error').removeClass('error-message-open-to-buy-text');
					$(".openToBuyValue").html("Open");
				} else if(orderTotal > maxOpenToBuyAmount){						
					$(".payment-to-acount").prop("disabled", true);
					$(".payOnline-account-failure").show();
					$(".payOnline-account-failure-term").hide();
					 $('.payment-openToBuy').addClass('error-message-open-to-buy');
					 $('.pay-account-text-error').addClass('error-message-open-to-buy-text');
				}else if(creditCode == "H" || creditCode == 'K'){
					$(".payment-to-acount").prop("disabled", true);
					$(".payOnline-account-failure-term").show();
					$(".payOnline-account-failure").hide();
					 $('.payment-openToBuy').removeClass('error-message-open-to-buy');
					 $('.pay-account-text-error').removeClass('error-message-open-to-buy-text');
				}
				else{
					$(".payment-to-acount").prop("disabled", false);
					$(".payOnline-account-failure").hide();
					$(".payOnline-account-failure-term").hide();
					$('.payment-openToBuy').removeClass('error-message-open-to-buy');
					$('.pay-account-text-error').removeClass('error-message-open-to-buy-text');

				}
    	            	}else{
    	            		loading.stop();
    	            		$(".payment-to-acount").prop("disabled", true);
    						$(".payOnline-account-failure").show();
    						$(".payOnline-account-failure-term").hide();
    						$(".payment-openToBuy").hide();
    						$(".pay-online-account-terms").hide();
    	            	}
    	            }
				});
			} else if(id.val() == 'PAY_AT_BRANCH') {
				$(".payment-to-branch").show();
				$(".payment-to-online").hide();
				$(".payment-to-acount").hide();
				$('#POA_terms').hide();
				$("#payAtBranch").show();
				$("#paymentFailedGc").hide();
				$("#cayanFailure").hide();
				$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineAccount-border").hide();
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .payOnlineCreditCard-border").hide();
				
				$("#div_PAY_ONLINE_WITH_CREDIT_CARD .card-selected").hide();
			}
		
	},

	  bindSearchValue:function(){
			$(document).on('click', '.continue-payment-btn', function(e) {
				 var SelectedValue= $("#div_PAY_ONLINE_WITH_CREDIT_CARD .cardselectedValue").val();
			
				e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/placeCustomerOrderWithEWallet?vaultToken=" + SelectedValue,
	    	            method:"GET",
	    	            success : function(response)
	    	            {
	    	            	
	    	            	if (response == 'Success') {
	    	            		window.location.href = ACC.config.encodedContextPath +"/checkout/multi/order-summary/view";
								}
	    	           
	    	            }
			});
		});
			$(document).on('click', '#div_PAY_ONLINE_WITH_CREDIT_CARD .cardselectedValue', function(e) {
				
				$("#iframe_Popup").hide();
				$("#paymentFailedGc").hide();
				 $("#cayanFailure").hide();
			})
			
	},
	
	bindGuestUserValue:function(){
		$(document).on('click', '.guest-Signin-overlay-popup', function(e) {
			 e.preventDefault();
			 var guestUser= "guest";
    	    $.ajax({
    	            url : ACC.config.encodedContextPath +"/cart/guestUser?guestUser=" + guestUser,
    	            method:"GET",
    	            success : function(response)
    	            {
	                    $(".cart-guest").removeClass("hidden");
    	            	window.location.href=ACC.config.encodedContextPath +"/cart";

    	            }

	});
		});

	},

	
	bindPayOnAccountValue:function(){
			$(document).on('click', '.continue-payment-poa-btn', function(e) {
				e.preventDefault();
	    	    $.ajax({
	    	            url : ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/placeCustomerOrderWithPOA",
	    	            method:"GET",
	    	            success : function(response)
	    	            {
	    	            	
	    	            	if (response == 'Success') {
	    	            		window.location.href = ACC.config.encodedContextPath +"/checkout/multi/order-summary/view";
								}
	    	            	
	    	           
	    	            }
			});
		});
			
	},
	
	  bindSavedCardpopup:function(){
		$('.cardselectedValue').on('change', function() {

			  $(".continue-payment-btn").attr('disabled', $('option:selected', this).val() == null || $('option:selected', this).val() == '');

			});
	  },

	  showAddressForm:function() {
			$(".shipmentcheck").attr("checked", false);
			$('.sipping-form').show();
			$("#shipping_div").hide();
			$("#shipping_btn").hide();
			$(".shipping-ponumber").addClass("hidden");
		},
	  
	saveContacForm:function(){
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/saveContactDetails", 
    		method:"POST",
    		data:$('#SiteOneGCContactForm').serialize(),	
    		
    		success: function(response)
    		{
				if(response && response.isCreditPaymentBlocked == 'Decline_Limit_Over'){
					loading.stop();
					ACC.checkout.isGuestCCDisbaled = true;
					ACC.checkout.guestCCDisbale();
				}
				else {
    			window.scrollTo(0,0);
    			
    			var orderType = $("#orderType").val();
    			
    			$(".contactcheckout").hide();
    			
    			$(".edit-contact-information").show();
    			$(".edit-contact-information_guest").show();
    			
    			$(".checkout-form-data").show();
    			
    			$("#siteOneOrderTypeForm").show();
    		
    			var firstname = response.customerData.firstName;
    			$("#data").html(firstname);
    			
    			var lastname = response.customerData.lastName;
    			$("#lastNamedata").html(lastname);
    			
    			var phonedata = response.customerData.contactNumber;
    			$("#phonedata").html(phonedata);
    			
    			var emaildata = response.customerData.displayUid;
    			$("#emaildata").html(emaildata);
    			
    			var addressdata = response.addressData.line1;
    			$("#addressdata").html(addressdata);
    			
    			var addressdata1 = response.addressData.line2;
    			$("#addressdata1").html(addressdata1);
    			
    			var citydata = response.addressData.town;
    			$("#citydata").html(citydata);
    			
    			var statedata = response.addressData.district;
    			$("#statedata").html(statedata);
				$("#delivery_location").val(statedata);
    			
    			var zipdata = response.addressData.postalCode;
    			$("#zipdata").html(zipdata);
    			
    			if(orderType == "PICKUP"){
    				$(".delivery--content").hide();
		        	$(".pickup--content").show();
					$(ACC.checkout.pickUpDateClass).show();
		        	$(".common-content-pickupDelivery").show();
		        	$(".delivery-chargesMsg").hide();
		        	$(".icon-delivery-charges").hide();
		        	$(".message-delivery").hide();
		            $(".errorPickupDelivery").hide();
		            $(".delivery-details").hide();
		            $(".pickup-title").show();
		            $(".charges-sec").hide();
    			}else if(orderType == "DELIVERY"){
    				var isSameAsContactInfo = response.isSameAsContactInfo;
    				if(isSameAsContactInfo){
    					$("#isSameAsContactInfo").attr("checked", true);
    					$('.deliveryContact-form').hide();
    					 $("#termsPODiv").show();
        $("#delivery_content1").show();
    			    	  $("#delivery_content2").show();
    			    	  $("#pickup-delivery").show();
           $(".payment_cbtn").show();
    			    	  $("#deliveryaddressdata").html(response.addressData.line1);
			        		$("#deliveryaddressdat1").html(response.addressData.line2);
			        		$("#deliverycitydata").html(response.addressData.town);
			        		$("#deliverystatedata").html(response.addressData.district);
							    $("#delivery_location").val(response.addressData.district);
			        		$("#deliveryzipdata").html(response.addressData.postalCode);
    				}else{
    					$("#isSameAsContactInfo").attr("checked", false);
    					$('.deliveryContact-form').show();
    					$("#termsPODiv").hide();
      	$("#delivery_content1").show();
    					$("#delivery_content2").hide();
    			    		$("#pickup-delivery").hide();
    					
    				}
    				$("#delivery_Details").hide();
    				$("#delivery_fee").hide();
		        		$(".edit-delivery-information").hide();
    			}else{
    				$("#parcelShipping_div").show();
    				$(".delivery--content").hide();
    				var isSameAsContactInfo = response.isSameAsContactInfo;
    				var state = response.addressData.district;
					let isNationalShipping = ($(".isNationalShipping").length)? $(".isNationalShipping").val() : 'false';
    				if(isSameAsContactInfo){
    					if(isNationalShipping == 'false' && state != _AAData.storeState){
        					$(".shipmentcheck").attr("disabled", true);
        					ACC.checkout.showAddressForm();
			        		$(".florida-error-msg").removeClass("hidden");
							$(".shipping-ponumber").addClass("hidden");
			        		$(".shipmentcheck").css("pointer-events","none");
        				}else{
        					$(".shipmentcheck").attr("disabled", false);
        					$(".shipmentcheck").attr("checked", true);
    					 	$('.sipping-form').hide();
    					 	$("#shipping_div").show();
		        			$("#shipping_btn").show();
		        			$(".florida-error-msg").addClass("hidden");
							$(".shipping-ponumber").removeClass("hidden");
		        			$("#shippingaddressdata").html(response.addressData.line1);
			        		$("#shippingaddressdat1").html(response.addressData.line2);
			        		$("#shippingcitydata").html(response.addressData.town);
			        		$("#shippingstatedata").html(response.addressData.district);
							$("#delivery_location").val(response.addressData.district);
			        		$("#shippingzipdata").html(response.addressData.postalCode);
        				}
        				
    			 }

			      else{
			    	  if(isNationalShipping == 'false' && state != _AAData.storeState){
        					$(".shipmentcheck").attr("disabled", true);
        					ACC.checkout.showAddressForm();
			        		$(".florida-error-msg").removeClass("hidden");
							$(".shipping-ponumber").addClass("hidden");
        				}else{
        					$(".shipmentcheck").attr("disabled", false);
        					ACC.checkout.showAddressForm();
			        		$(".florida-error-msg").addClass("hidden");
							$(".shipping-ponumber").removeClass("hidden");
        				}
        				
			    }
    				
    				
    			}
    			$(".direction-link").show();
    			$(".edit-pickup-information").hide();
    			$(".input-dateWrapper").off();
    		    $(".input-dateWrapper").on("click",function(){$(".input-dateWrapper #date").trigger("focus");});
    		    
				}
    		
    		}
		})
	},
	checkoutStepEmailValidation:function(){
					
						var selectedValue=$("#checkoutContactemail").val()
						if(selectedValue){
						$.ajax({
						url : ACC.config.encodedContextPath + "/checkout/multi/isEmailAlreadyExists?email=" + selectedValue,
						method:"GET",

						success : function(response) {
							if(response.isSuccess== true)
							{
							if(response.isMailAvailable== true){

						$(".exitEmail-guestCheckout-form").show();
						$(".addContactFormSubmit").prop('disabled', true);
						}
						else{
						$(".exitEmail-guestCheckout-form").hide();
						$(".exitEmail-guestCheckout-formerror").hide();
						$(".addContactFormSubmit").prop('disabled', false);
						}
						}else {
						$(".exitEmail-guestCheckout-formerror").show();
						$(".exitEmail-guestCheckout-form").hide();
						$(".addContactFormSubmit").prop('disabled', true);
						}
						}
						})
					}
						
				},
	bindContactAddressValidations: function (){
		
		$(".edit-contact-information").hide();
		$(".exitEmail-guestCheckout-form").hide();
		$(".exitEmail-guestCheckout-formerror").hide();
		$(".contactForm_guest-checkout-data").hide();
		
		$(document).on("click", ".edit-contact-information", function(e)
				{
			$(".edit-contact-information").hide();
			$("#siteOneOrderTypeForm").hide();
			$(".checkout-form-data").hide();
			$(".contactcheckout").show();
			$("#iframe_Popup_guest").hide();
			 $(".choosepaymentB2b_GC").hide();
			grecaptcha.reset();
				});
		
		$(".edit-contact-information_guest").click(function() {
			if(analyticsPaymentMethodCheck==false){
 						analyticsPaymentMethodCheck= true;
			try{
				 _satellite.track('contactDetails');
				 } catch (e) {}
			}	 
			$(".checkout-form-data").hide();
			$(".contactcheckout").show();
			$("#iframe_Popup_guest").hide();
			$("#siteOneOrderTypeForm").hide();
			grecaptcha.reset();
			$(".edit-contact-information_guest").hide();
			var firstname=$(".contactPersonfirstName").val();
			$("#checkoutContactfirstName").val(firstname);
			var lastname=$(".contactPersonlastName").val();
			$("#checkoutContactlastName").val(lastname);
			var number=$(".contactPersoncontactNumber").val();
			$("#checkoutContactphone").val(number);
			var email=$(".contactPersondisplayUid").val();
			$("#checkoutContactemail").val(email);
			var line1=$(".addressDataline1").val();
			$("#checkoutContactaddressLine1").val(line1);
			var line2=$(".addressDataline2").val();
			$("#checkoutContact.addressLine2").val(line2);
			var town=$(".addressDatatown").val();
			$("#checkoutContactcity").val(town);
			var district=$(".addressDatadistrict").val();
			$("#checkoutContactstate").val(district);
			var postalcode=$(".addressDatapostalCode").val();
			$("#checkoutContactzip").val(postalcode);
		
		});
		
		$(document).on('input blur', "#checkoutContactfirstName", function(e)
	    		{
	    				ACC.formvalidation.vaildateFields('#checkoutContactfirstName','#errorFirstName', '#checkoutContactfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
	    		});
		$(document).on('input blur', "#checkoutContactlastName", function(e)
	    		{
	    				ACC.formvalidation.vaildateFields('#checkoutContactlastName','#errorLastName', '#checkoutContactlastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);
	    		});
		
		$(document).on("blur", "#checkoutContactphone", function(e)
				{
					var phoneNumber = $(this).val();
					if(phoneNumber != null && phoneNumber != '' && phoneNumber != undefined)
				    {
				        if (!ACC.formvalidation.validatePhoneNumber(phoneNumber))
		                {
		                    var msg= ACC.config.phoneNumberError ;
		                    ACC.formvalidation.addErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors', msg);
		                }
		                else
		                {
		                    ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors');
		                }
				    }
				    else
				    {
		            	ACC.formvalidation.addErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors', ERRORMSG.global.phone);
				    }
			    });
				
				$(document).on("input", "#checkoutContactphone", function(e)
				{
					ACC.formvalidation.handlePhoneNumberOnInput('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors');
			    });
				
		    	      
				$(document).on("input blur", "#checkoutContactemail", function (e) {
					e.preventDefault();
					if (ACC.formvalidation.validateEmailPattern($("#checkoutContactemail").val())) {
						ACC.checkout.checkoutStepEmailValidation();
						ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactemail', '#errorEmailAddress', '#checkoutContactemail\\.errors', ERRORMSG.invalid.email);
					}
					else {
						$(".exitEmail-guestCheckout-form").hide();
						$(".exitEmail-guestCheckout-formerror").hide();
						$(".addContactFormSubmit").prop('disabled', false);
						ACC.formvalidation.addErrorClassForRegForm('#checkoutContactemail', '#errorEmailAddress', '#checkoutContactemail\\.errors', ERRORMSG.invalid.email);
					}
				});
				$(document).on("input blur", "#checkoutContactaddressLine1", function()
						{
					        var msg = ERRORMSG.global.address;
					        ACC.formvalidation.vaildateFields('#checkoutContactaddressLine1', '#errorAddressLine1', '#checkoutContactaddressLine1\\.errors', msg);
					    });
				$(document).on('input blur', "#checkoutContactcity", function(e)
						{
							ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutContactcity', '#errorCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutContactcity');
					    });
				$(document).on("input blur", "#checkoutContactzip", function(e)
						{	
							if(ACC.formvalidation.validatezipcode(e, $("#checkoutContactzip").val()))
							{
								ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactzip', '#errorZipcode', '#checkoutContactzip\\.errors');
							}
							else
							{
								ACC.formvalidation.addErrorClassForRegForm('#checkoutContactzip', '#errorZipcode', '#checkoutContactzip\\.errors',ACC.config.zipCodeError);
							}
					    });
				$(document).on("input blur", "#checkoutContactstate", function()
						{
					        var msg = ERRORMSG.global.state;
					        ACC.formvalidation.vaildateFields('#checkoutContactstate', '#errorState', '#checkoutContactstate\\.errors', msg);
					    });
				
				
				$(document).on("click", ".addContactFormSubmit", function(e)
						{
					e.preventDefault();
					var status = true;
					document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
					ACC.briteverify.reCaptcha(grecaptcha.getResponse());
					ACC.formvalidation.vaildateFields('#checkoutContactfirstName','#errorFirstName', '#checkoutContactfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
					ACC.formvalidation.vaildateFields('#checkoutContactlastName','#errorLastName', '#checkoutContactlastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);	
					var msg = ERRORMSG.global.address;
			        ACC.formvalidation.vaildateFields('#checkoutContactaddressLine1', '#errorAddressLine1', '#checkoutContactaddressLine1\\.errors', msg);
			        ACC.formvalidation.vaildateFields('#checkoutContactstate', '#errorState', '#checkoutContactstate\\.errors', ERRORMSG.global.state);
					ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutContactcity', '#errorCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutContactcity');
			        $(".edit-contact-information").show();
					$(".edit-contact-information_guest").addClass("hidden");	
					if(null != $("#checkoutContactphone").val() && $("#checkoutContactphone").val() != '')
				    {
				        if (!ACC.formvalidation.validatePhoneNumber($("#checkoutContactphone").val()))
		                {
		                    var msg= ACC.config.phoneNumberError ;
		                    status = false;
		                    ACC.formvalidation.addErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors', msg);
		                }
		                else
		                {
		                    ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors');
		                }
				    }
				    else
				    {
				    	status = false;
		            	ACC.formvalidation.addErrorClassForRegForm('#checkoutContactphone', '#errorPhoneNumber', '#checkoutContactphone\\.errors', ERRORMSG.global.phone);
				    }
					if(ACC.formvalidation.validatezipcode(e, $("#checkoutContactzip").val()))
					{
						ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactzip', '#errorZipcode', '#checkoutContactzip\\.errors');
					}
					else
					{
						status = false;
						ACC.formvalidation.addErrorClassForRegForm('#checkoutContactzip', '#errorZipcode', '#checkoutContactzip\\.errors',ACC.config.zipCodeError);
					}
					
					if(ACC.formvalidation.validateEmailPattern($("#checkoutContactemail").val()))
				    {
						 ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactemail', '#errorEmailAddress', '#checkoutContactemail\\.errors', ERRORMSG.invalid.email);
					}
					else
					{
						status = false;
						 ACC.formvalidation.addErrorClassForRegForm('#checkoutContactemail', '#errorEmailAddress', '#checkoutContactemail\\.errors', ERRORMSG.invalid.email);
					}
					
					$('*[id*=error]:visible').each(function() 
							{
								if($(this).html() != '')
								{
									status = false;
								}
							});
					
					
					 if(status)	{
						 var briteverify= document.getElementById('briteVerifyEnable').value;
						 if(briteverify=="true"){
							 url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+$("#checkoutContactemail").val();
							 $.ajax({
					                url: url,
					                type: "GET",
					                timeout: 5000,
						            success: function (response)
						            {
							            if(response == "invalid")
							            {
							            				grecaptcha.reset();
							                          $("#errorEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>Please enter a valid email address.</div></font></div>");   
							            }
							            else
							            {
								            if(status)
								            {
												$.ajax({
													url : ACC.config.encodedContextPath + "/checkout/multi/isEmailAlreadyExists?email=" + $("#checkoutContactemail").val(),
													method:"GET",
							
													success : function(response)
													{
														if(response.isSuccess== true)
														{
														if(response.isMailAvailable== true){
															$(".exitEmail-guestCheckout-form").show();
															$(e.target).prop('disabled', true);
														}
														else{
															$(".exitEmail-guestCheckout-form").hide();
															$(e.target).prop('disabled', false);
															$.ajax({
									        		url:ACC.config.encodedContextPath + "/checkout/multi/validate-address", 
									        		method:"POST",
									        		data:$('#SiteOneGCContactForm').serialize(),		        	
									        		success: function(addressVerificationData){
							                           ACC.address.isAddressChanged = false;
							                           ACC.address.isAddressVerified = true;
									        			if((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404')
									        			{
									        				ACC.checkout.saveContacForm();
									        			}
									        			else
									        			{ // create pop up content address suggestion
										        			$.colorbox({
																html: createPopupContent(addressVerificationData),					
																maxWidth:"100%",
																width:"550px",
																maxHeight:"100%",
																overflow:"auto",
																opacity:0.7,
																className:"addNotFound invalidOriginalAddress",
																title:false,
																close:'<span class="glyphicon glyphicon-remove"></span>'
															});
									        			}
									                 }
								        		});
														}
														}}})

								            	
								            	
								            	
								            }
							            }
						            },
					           
						          error: function(response, textstatus, message) 
						          {
						        	  grecaptcha.reset();
						                        if(textstatus == "timeout" || textstatus == "error") 
						                        {
						                        	$("#errorEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
						                        } 
						          }
							 });
						 }else{
							 ACC.checkout.saveContacForm();
							 try {
			      	          	  _satellite.track("fullfillmentDetails");
			      	          } catch (e) {}
						 }
					 }
						
						});
	},
	
	
	
	
	bindEditFulFilmentDetails: function() {
		
		
		
		$(document).on("change", "#checkoutShippingstate", function(e){
			var msg = ERRORMSG.global.address;
			var stateMsg = ERRORMSG.global.shippingStates;
			ACC.checkout.checkIfFlorida('#checkoutShippingstate', '#errorShippingState', '#checkoutShippingstate\\.errors', msg, stateMsg);
		});
		$(document).on('input blur', "#checkoutdeliverycity", function(e)
		{
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutShippingcity', '#errorShippingCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutdeliverycity');
		});
		
		$(document).on("click", ".addShippingFormSubmit", function(e)
				{
			e.preventDefault();
				var status = true;
				ACC.formvalidation.vaildateFields('#checkoutShippingfirstName','#errorShippingFirstName', '#checkoutShippingfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
				ACC.formvalidation.vaildateFields('#checkoutShippinglastName','#errorShippingLastName', '#checkoutShippinglastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);	
				var guestmsg = ERRORMSG.global.address;
				var gueststateMsg = ERRORMSG.global.shippingStates;
		        ACC.formvalidation.vaildateFields('#checkoutShippingaddressLine1', '#errorShippingAddressLine1', '#checkoutShippingaddressLine1\\.errors', guestmsg);
				ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutShippingcity', '#errorShippingCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutShippingcity');
				ACC.checkout.checkIfFlorida('#checkoutShippingstate', '#errorShippingState', '#checkoutShippingstate\\.errors', gueststateMsg);			
				if(null != $("#checkoutShippingphone").val() && $("#checkoutShippingphone").val() != '')
			    {
			        if (!ACC.formvalidation.validatePhoneNumber($("#checkoutShippingphone").val()))
	                {
	                    var message= ACC.config.phoneNumberError ;
	                    status = false;
	                    ACC.formvalidation.addErrorClassForRegForm('#checkoutShippingphone', '#errorShippingPhoneNumber', '#checkoutShippingphone\\.errors', message);
	                }
	                else
	                {
	                    ACC.formvalidation.removeErrorClassForRegForm('#checkoutShippingphone', '#errorShippingPhoneNumber', '#checkoutShippingphone\\.errors');
	                }
			    }
			    else
			    {
			    	status = false;
	            	ACC.formvalidation.addErrorClassForRegForm('#checkoutShippingphone', '#errorShippingPhoneNumber', '#checkoutShippingphone\\.errors', ERRORMSG.global.phone);
			    }
				if(ACC.formvalidation.validatezipcode(e, $("#checkoutShippingzip").val()))
				{
					ACC.formvalidation.removeErrorClassForRegForm('#checkoutShippingzip', '#errorShippingZipcode', '#checkoutShippingzip\\.errors');
				}
				else
				{
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('#checkoutShippingzip', '#errorShippingZipcode', '#checkoutShippingzip\\.errors',ACC.config.zipCodeError);
				}
				if(!ACC.formvalidation.validateFieldBasedOnRegex(e,'#checkoutShippingcity', '#errorShippingCity', '#checkoutContactcity\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'checkoutShippingcity'))
				{
					status = false;
				}
				if(ACC.formvalidation.validateEmailPattern($("#checkoutShippingemail").val()))
			    {
					 ACC.formvalidation.removeErrorClassForRegForm('#checkoutShippingemail', '#errorShippingEmailAddress', '#checkoutShippingemail\\.errors', ERRORMSG.invalid.email);
				}
				else
				{
					status = false;
					 ACC.formvalidation.addErrorClassForRegForm('#checkoutShippingemail', '#errorShippingEmailAddress', '#checkoutShippingemail\\.errors', ERRORMSG.invalid.email);
				}
				
				$('*[id*=error]:visible').each(function() 
						{
							if($(this).html() != '')
							{
								status = false;
							}
						});
				
				var shippingEmail = $("#checkoutShippingemail").val();
				 if(status)	{
					 	ACC.checkout.briteVerifyEmailvalidation(shippingEmail , status);
						ACC.checkout.showProp65Popup();
				 }
						
			
	});
		
		$(document).on("click", "#Shipping_Edit", function(e){
			$("#parcelShipping_div").show();
			analyticsPaymentMethodCheck= false;
			try {
	          	  _satellite.track("fullfillmentDetails");
	          } catch (e) {}

			$("#shipping_fee").hide();
			$("#iframe_Popup_guest").hide();
			 $(".choosepaymentB2b_GC").hide();
			var isGuestUser = $(".isguestuser").val();
			var state = $("#statedata").html();
			if(isGuestUser == "true"){
				if($(".shipmentcheck").prop('checked')==true){
					if(state != _AAData.storeState || state != 'US-'+_AAData.storeState){
    					$(".shipmentcheck").attr("disabled", true);
    					$(".shipmentcheck").attr("checked", false);
    					$('.sipping-form').show();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
						$(".shipping-ponumber").addClass("hidden");
    				}else{
    					$(".shipmentcheck").attr("disabled", false);
    					$(".shipmentcheck").attr("checked", true);
    					$('.sipping-form').hide();
  					  	$("#shipping_div").show();
  						$("#shipping_btn").show();
  						$(".shipping-ponumber").removeClass("hidden");
    				}
					  
				}
				else{
					if(state != _AAData.storeState || state != 'US-'+_AAData.storeState){
    					$(".shipmentcheck").attr("disabled", true);
    					$(".shipmentcheck").attr("checked", false);
    					$('.sipping-form').show();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
						$(".shipping-ponumber").addClass("hidden");
    				}else{
    					$(".shipmentcheck").attr("disabled", false);
    					$(".shipmentcheck").attr("checked", false);
    					$('.sipping-form').show();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
						$(".shipping-ponumber").addClass("hidden");
    				}
			      }
				$("#shipping_Details").hide();
				$("#shipment_div2").show();
			}else{
				$("#shipping_div").show();
				$("#shipment_div2").show();
				$("#shipping_btn").show();
				$("#shipping_Details").hide();
				$(".shipping-ponumber").removeClass("hidden");
			}
		});
		
		
		$(document).on("click", "#PickUp_Edit", function(e){
			analyticsPaymentMethodCheck= false;
			$(".delivery--content").hide();
	    	$(".pickup--content").show();
			$(ACC.checkout.pickUpDateClass).show();
	    	$(".common-content-pickupDelivery").show();
	    	$(".delivery-chargesMsg").hide();
	    	$(".icon-delivery-charges").hide();
	    	$(".message-delivery").hide();
	        $(".errorPickupDelivery").hide();
	        $(".delivery-details").hide();
	        $(".pickup-title").show();
	        $(".charges-sec").hide();
	        $(".input-dateWrapper").off();
	        $("#pickup_Location_Div").show();
	        $("#pickup_Details").hide();
	        $("#requestedDateData").hide();
	        $("#requestedMeridianData").hide();
	        $("#termsPODiv").show();
		    $(".input-dateWrapper").on("click",function(){$(".input-dateWrapper #date").trigger("focus");});
			$("#iframe_Popup_guest").hide();
			 $(".choosepaymentB2b_GC").hide();
			 if($(".isSplitCartCheckoutPickupPayment").val() == "true"){
				$(".split-shipping-Details").addClass('hidden');
				$(".split-shipping-fee, .splitShippingSection").removeClass('hidden');
				$(".choosepaymentB2b_GC").hide();
			 }
			 try {
           	  _satellite.track("fullfillmentDetails");
           } catch (e) {}
		    
		});
		$(document).on("click", "#Delivery_Edit", function(e){
			analyticsPaymentMethodCheck= false;
			$("#delivery_fee").hide();
			$("#delivery_Details").hide();
			try {
          	  _satellite.track("fullfillmentDetails");
          } catch (e) {}
          
			var isGuestUser = $(".isguestuser").val();
			if(isGuestUser == "true"){
				if($("#isSameAsContactInfo").prop('checked')==true){
					  $('.deliveryContact-form').hide();
					  $("#delivery_content1").show();
					  $("#termsPODiv").show();
			    	  $("#delivery_content2").show();
			    	  $("#pickup-delivery").show();
			    	  $(".payment_cbtn").show();
			    		$("#iframe_Popup_guest").hide();
			    		 $(".choosepaymentB2b_GC").hide();
			    		 
			    		 ACC.checkout.deliveryNewAddressShowForm();
				  }
	
			      else{
			    	  $('.deliveryContact-form').show();
			    	  $("#termsPODiv").hide();
			    	  $("#delivery_content1").show();
			    	  $("#delivery_content2").hide();
			    	  $("#pickup-delivery").hide();
			    	  $(".payment_cbtn").hide();
			    		$("#iframe_Popup_guest").hide();
			    		 $(".choosepaymentB2b_GC").hide();
			    		 ACC.checkout.deliveryNewAddressDetailsShowForm();
			    		 ACC.checkout.deliveryNewAddressHideForm();
			      }
			}
			else{
				$("#delivery_content1").show();
				  $("#termsPODiv").show();
		    	  $("#delivery_content2").show();
		    	  $("#pickup-delivery").show();
		    	  $(".payment_cbtn").show();
			}
			$("#iframe_Popup_guest").hide();
			 $(".choosepaymentB2b_GC").hide();
		});
	},
	
	checkHomeOwnerCase: function () {
		var tradeClass = $(".trade-class").val();
		var subTotalVal = $(".subTotal-class").val();
		var homeOwnerCode = $(".homeOwnerCode").val();
		var subtotalLimit= $(".subtotalLimit").val();
		if (tradeClass == homeOwnerCode && (parseFloat(subTotalVal) < parseFloat(subtotalLimit))) {
	       $('#DELIVERY').prop('disabled', true);
	       $(".homeOwner-msg").removeClass("hidden");
	       $("#DELIVERY").parent().parent().addClass('disable_section');
	       $("label[for='Delivery").addClass('cursor-notallowed');
	    }
		
		
		$(document.body).on("click","#PICKUP",function(){
			$(".homeOwner-msg").addClass("hidden");
		});
		
		if($(".homeOwner-msg").length > 0){
			$('#DELIVERY').prop('checked', false);
			$('#PICKUP').prop('checked', true);
			$(".delivery--content").hide();
			$(".pickup--content").hide();
			$(ACC.checkout.pickUpDateClass).hide();
			$(".common-content-pickupDelivery").show();
			$(".delivery-chargesMsg").hide();
			$(".icon-delivery-charges").hide();
			$(".message-delivery").hide();
			$(".errorPickupDelivery").show();
			}
	},
	isSameDayDeliveryEnabled: function(){
		var currentDate = new Date();
		var morningTimeLimit = new Date();
		let limitTime = $("#deliveryTimeLimit").val();
		limitTime = (limitTime && limitTime != '')? limitTime : '9:00';
		morningTimeLimit.setHours(parseInt(limitTime.split(":")[0]),parseInt(limitTime.split(":")[1]),0,0);
		return (morningTimeLimit > currentDate);
	},
	isAfternoonCheckoutAllowed : function() //before 2:30PM?
	{
		var currentDate = new Date();
		var afternoonTimeLimit = new Date();
		let limitTime = $("#afternoonTimeLimit").val();
		limitTime = (limitTime && limitTime != '')? limitTime : '03:30';
		afternoonTimeLimit.setHours(parseInt(limitTime.split(":")[0]),parseInt(limitTime.split(":")[1]),0,0);
		return (afternoonTimeLimit > currentDate);
	},
	
	isNoonCheckoutAllowed : function() //is AM?
	{
		var noon =12;
		var currentDate = new Date();
		var afternoonTimeLimit = new Date();
		afternoonTimeLimit.setHours(noon);
		afternoonTimeLimit.setMinutes(0);
		return (afternoonTimeLimit > currentDate);
	},
	isMorningCheckoutAllowed : function() //before 9:30AM?
	{
		var currentDate = new Date();
		var afternoonTimeLimit = new Date();
		let limitTime = $("#timeLimit").val();
		limitTime = (limitTime && limitTime != '')? limitTime : '11:00';
		afternoonTimeLimit.setHours(parseInt(limitTime.split(":")[0]),parseInt(limitTime.split(":")[1]),0,0);
		return (afternoonTimeLimit > currentDate);
	},
	dayReturn: function(date){
		let weekday = ["Sun", "Mon", "Tue","Wed", "Thu", "Fri", "Sat"];
		return weekday[date.getDay()];
	},
	isHalfDay: function (date) {
		var currentDate = date;
		var schedule = JSON.parse(document.getElementsByClassName("openingSchedule")[0].value);
		var currentDay = ACC.checkout.dayReturn(currentDate)
		switch (currentDay) {
			case "Mon":
			  num = 0;
			  break;
			case "Tue":
			  num = 1;
			  break;
			case "Wed":
		      num = 2;
			  break;
			case "Thu":
				num = 3;
			  break;
			case "Fri":
				num = 4;
			  break;
			case  "Sat":
				num = 5;
				break;
			case "Sun":
				num = 6;
				break;
		}
		var aftTimeLimit = $("#afternoonTimeLimit").val();
		var afternoonTimeLimitHours = aftTimeLimit.split(":")[0];
		var afternoonTimeLimitMinutes = aftTimeLimit.split(":")[1];
		if (!schedule.weekDayOpeningList[num].closed) {
			var branchCloseTime=schedule.weekDayOpeningList[num].formattedClosingTime
			var branchCloseTimeHr=branchCloseTime.split(':')[0]
			var branchCloseTimeMin = branchCloseTime.split(':')[1]
			if (branchCloseTimeHr < afternoonTimeLimitHours || (branchCloseTimeHr == afternoonTimeLimitHours && branchCloseTimeMin <= afternoonTimeLimitMinutes)) {
				return true;
			}
		}
		return false;
	},
	isOrderPlacedBefore9: function(){
		var currentDate = new Date();
		var morningTimeLimit = new Date();
		let limitTime = $("#deliveryTimeLimit").val();
		limitTime = (limitTime && limitTime != '')? limitTime : '9:00';
		morningTimeLimit.setHours(parseInt(limitTime.split(":")[0]),parseInt(limitTime.split(":")[1]),0,0);
		return (morningTimeLimit > currentDate);
	},
	setDefaultDate: function(){ //Setting default date for Pickup || Delivery
		if($(".page-siteOneCheckoutPage").length && !$(".mixedcart-checkout").length){
			let today = new Date();
			let day = ACC.checkout.dayReturn(today);
			let orderType = $("#orderType").val();
			let timeSlot = "AM";
			let num = 0;
			if(orderType == 'DELIVERY'){
				if(day == "Thu"){
					num = 4;
				}
				else if(day == "Fri"){
					num = 3;
				}
				else if(day == "Sat" || !ACC.checkout.isNoonCheckoutAllowed()){
					num = 2;
				}
				else {
					num = 1;
				}
			}
			else if(orderType == 'PICKUP'){
				if(day == "Sat"){
					num = 2;
				}
				else if(day == "Sun" || !ACC.checkout.isAfternoonCheckoutAllowed()){
					num = 1;
				}
				else if(!ACC.checkout.isMorningCheckoutAllowed()){
					timeSlot = "PM";
				}
			}
			today.setDate(today.getDate() + num);
			today = ACC.checkout.nextAvailableBranchDate(today);
			$("#date").val(ACC.checkout.formatDate(today, 'mdy'));
			$("#date").datepicker("setDate", today);
			$("#termsAndConditions").trigger("click");
			$("#date").trigger("change");
			$("#" + timeSlot).trigger("click");
			$("[for='"+ timeSlot +"']").trigger("click");
		}
	},
	nextAvailableBranchDate: function(today){
		var dateScheduleArray = new Array();
		dateScheduleArray = ACC.checkout.dateSchedule();
		var newDay = today.getDay();
		var newDate = today.getDate();
		while(newDay == dateScheduleArray[newDay] || ((newDay == 0 || newDay == 1) && newDate == 1) || (newDay == 0 && newDate == 31)){
			today.setDate(today.getDate() + 1);
			newDay = today.getDay();
			newDate = today.getDate();
		}
		return today;
	},
	// Finding next working day
	toFindNextWorkingDay: function() {
		let num = 0;
		var schedule = JSON.parse(document.getElementsByClassName("openingSchedule")[0].value);
		while (num <= 6) {
			if (!schedule.weekDayOpeningList[num].closed) {
			return ACC.checkout.toFindNextWorkingDate(schedule.weekDayOpeningList[num].weekDay);
		}
			num++;
		}
	},
	toFindNextWorkingDate: function (dayName) {
		const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
		let dayIndex = daysOfWeek.indexOf(dayName);

		let today = new Date();
		let currentDayIndex = today.getDay();
		let daysUntilNext = (dayIndex + 7 - currentDayIndex) % 7 || 7;
	 
		let nextDay = new Date(today);
		nextDay.setDate(today.getDate() + daysUntilNext);
		return nextDay;
	
	},
	poValueCheck: function(){ 
		var targetName, targetVal;
		if($("#poNumberReq").length){
			targetName = $("#poNumberReq");
		}
		else{
			targetName = $("#PurchaseOrderNumber");
		}
		targetVal = targetName.val().replaceAll("'","").replaceAll('"','');
		targetName.val(targetVal);
	},
	
	poValueCheckonKeyPress: function(event){ 
	 if(navigator.userAgent.match(/iPhone/i) && (event.keyCode=== 34  || event.keyCode === 39)){
		  event.returnValue=false;
	      event.preventDefault();
	      return false; 
      }
	},
	
	nationalShippingChanges: function() {
        if ($("#orderType").val() == "PARCEL_SHIPPING" || $(".isSplitCartCheckoutPickupPayment").val() == "true") {
            $("#div_PAY_AT_BRANCH").css({position: "relative"}).prepend('<div style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; z-index: 99; background: #fff; opacity: .5; width: 100%; height: 100%; cursor: default;"></div>');
			$("#PAY_AT_BRANCH").prop({disabled: true})
        }
    },
    creditcardDisable: function(){
		var s1Acc = $("input[name='radio-group'][id='SITEONE_ONLINE_ACCOUNT']");
		$("input[name='radio-group'][id='PAY_ONLINE_WITH_CREDIT_CARD']").prop('disabled', true);
		if(s1Acc.length > 0 && $("#div_SITEONE_ONLINE_ACCOUNT").is(':visible') == true){
			s1Acc.prop('checked', true);
		}
		else{
			$("input[name='radio-group'][id='PAY_AT_BRANCH']").prop('checked', true);
		}
		$('#div_PAY_ONLINE_WITH_CREDIT_CARD').addClass('disable-click');
	},

	creditcardDisableErrPopup: function(errmsg=null){
		var errSelecter;
		if(errmsg == 'PICKUP' || errmsg == 'PICKUPHOME' || errmsg == 'PICKUPNEARBY' || errmsg == 'FUTURE_PICKUP'){
			errSelecter = 'declinePickupError';
		}
		else if(errmsg == 'DELIVERY' || errmsg == 'STORE_DELIVERY' || errmsg == 'MULTIPLE_DELIVERIES'){
			errSelecter = 'declinePickupError';
		}
		else{
			errSelecter = 'declineShippingError';
		}
		ACC.colorbox.open("", {
				html: $("."+errSelecter).html(),
				width:"600",
				escKey: false,
				overlayClose: false,
				className:"ccdisable-popup",
				onComplete:function(){
				},
			});
	}
  
};   
$( document ).ready(function() {
	//checkout email validation on load
	ACC.checkout.checkoutStepEmailValidation();
	//checkout email validation on load
    $(document.body).on("click",".delivery-mode",function()
		{
			ACC.checkout.bindOrderType();
			ACC.checkout.removeErrorClassForCheckoutForm('#orderTypeRadio', '#errorOrderTypeRadio', '#orderTypeRadio\\.errors')
			
		});
    
    
    
   var failuretested=$("#cayanFailure").val();
  
   if(failuretested =='failure'){
	   ACC.colorbox.open("", {
		   html:  ["<div class='cartShareEmail PopupBox'>",
                  "<p>"+ACC.config.cayan+"</p>",
                   "</div>"].join(''),
          width: '480px',
          height:'auto',
          title: "<h4 class='payment-pop-up-title headline'><span class='headline-text'>"+ACC.config.cayanTitle+"</span></h4>",
          close:'<span class="glyphicon glyphicon-remove"></span>',
          onComplete: function() {
        	   $('#cboxLoadedContent #cboxClose').click(function(){
        		   window.location.href=ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/add";
             		
             		});
       		$("body").css("overflow-y", "hidden");
       		
       	 
       	},
       	onClosed: function() {
       		$('body').css("overflow-y","auto");
       		window.location.href=ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/add";
       	 
       		}
		  });
   }
});

$( document ).ready(function() {
	$("#paymentFailedGc").hide();
	 $("#iframe_Popup_guest").hide();
	var errorAddEwallet=$("#cayanFailure").val();
	
	var guestUser=$(".isguestuser").val();
	var orderType = $("#orderType").val();
	if(guestUser== 'true'){
		var customer= $(".currentCustomer").val();
		if(customer != null && customer != ""){
			if(orderType == "PARCEL_SHIPPING"){
				$("#parcelShipping_div").show();
			}else{
				$("#siteOneOrderTypeForm").show();
			}
		}else{
			if(orderType == "PARCEL_SHIPPING"){
				$("#parcelShipping_div").hide();
			}else{
				$("#siteOneOrderTypeForm").hide();
			}
		}
	}else{
		if(orderType == "PARCEL_SHIPPING"){
			$("#parcelShipping_div").show();
			 
		}else{
			$("#siteOneOrderTypeForm").show();
		}
	}
	

	if(errorAddEwallet != null && errorAddEwallet != ""){
		   ACC.colorbox.open("", {
			   html: $(".add-eroor-card-ewallet").html(),
	       width: '480px',
	       height:'auto',
	       
	       close:'<span class="glyphicon glyphicon-remove"></span>',
	       onComplete: function() {
	    	   $('#cboxLoadedContent #cboxClose').click(function(){
        		   window.location.href=ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/add";
             		
             		});
	    		$("body").css("overflow-y", "hidden");
	    		
	    	 
	    	},
	    	onClosed: function() {
	    		$('body').css("overflow-y","auto");
	    		window.location.href=ACC.config.encodedContextPath +"/checkout/multi/siteone-payment/add";
	    	 
	    		}
			  });
	}
	
	
	
	$(".panel-title-value ").click(function() {
		  $('.collapsed').slideToggle().toggleClass('active');
		  if ($('.collapsed').hasClass('active')) {
			  $('.panel-title-value').html('<span class="glyphicon glyphicon-chevron-up"></span> ');
		  } else {
		    $('.panel-title-value').html('<span class="glyphicon glyphicon-chevron-down"></span> ');
		  }
		});

	$(".hide-products").click(function() {
		 if(screen.width>=1024){
			  $('.checkout-product').slideToggle().toggleClass('active');
			  if ($('.checkout-product').hasClass('active')) {
				  $('.hide-products').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
			  } else {
			    $('.hide-products').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
			  }
		 }
		 else{
			 $('.checkout-product').slideToggle().toggleClass('active');
			  if ($('.checkout-product').hasClass('active')) {
				  $('.hide-products').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
			  } else {
			    $('.hide-products').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
			  }
			 
		 }
		 
		});
	
		$(".hide-pickup-product").click(function() {
			if(screen.width>=1024){
				 $('.checkout-pickup-product').slideToggle().toggleClass('active');
				 if ($('.checkout-pickup-product').hasClass('active')) {
					 $('.hide-pickup-product').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
				 } else {
				   $('.hide-pickup-product').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
				 }
			}
			else{
				$('.checkout-pickup-product').slideToggle().toggleClass('active');
				 if ($('.checkout-pickup-product').hasClass('active')) {
					 $('.hide-pickup-product').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
				 } else {
				   $('.hide-pickup-product').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
				 }
				
			}
			
		   });
		   
		   $(".hide-shipping-product").click(function() {
			if(screen.width>=1024){
				 $('.checkout-shipping-product').slideToggle().toggleClass('active');
				 if ($('.checkout-shipping-product').hasClass('active')) {
					 $('.hide-shipping-product').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
				 } else {
				   $('.hide-shipping-product').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
				 }
			}
			else{
				$('.checkout-shipping-product').slideToggle().toggleClass('active');
				 if ($('.checkout-shipping-product').hasClass('active')) {
					 $('.hide-shipping-product').html('<span class="glyphicon green-title glyphicon-minus"></span> ');
				 } else {
				   $('.hide-shipping-product').html('<span class="glyphicon green-title glyphicon-plus"></span> ');
				 }
				
			}
			
		   });
	});

function paymentContentLoad(e) {
	
	try {
		
		var oIframe =document.getElementById('myIframe');
    	// for compatibility
		
    	var oDoc = oIframe.contentWindow || oIframe.contentDocument;
    	
    	if (oDoc.document) {
    		loading.start();
    		 var  oDoc = oDoc.document;
    		var sPageURL =oDoc.location.search.substring(1); 
    		var sURLVariables = sPageURL.split('&');
    		 for (var i = 0; i < sURLVariables.length; i++) 
    		{ var sParameterName = sURLVariables[i].split('=');
    		 if (sParameterName[1] == 'DECLINED'  || sParameterName[1] == 'User_Cancelled' || sParameterName[1] == 'FAILED') { 
    			
    			 $.ajax({
						url:ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/getBoardCardTransportKey?isCheckout="+true,
						method: "POST",
	          
						success: function (transportKey) {
							if(transportKey && transportKey == 'Decline_Limit_Over'){
								loading.stop();
								ACC.checkout.isGuestCCDisbaled = true;
								ACC.checkout.guestCCDisbale();
								ACC.pendo.captureEvent("3XGUEST");
							}
							else if (transportKey != null && transportKey != "Decline") {
								loading.stop();
								var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey=' + transportKey;
								
								 $(".Pop-up-myIframe ").attr('src', redirecturl );
								 $("#iframe_Popup_guest").show();
								 $("#paymentFailedGc").show();
								 
								 
							}
							else{
								$("#paymentFailedGc").show();
							}
						},
						error: function(){
							$("#paymentFailedGc").show();
						}
					})
    			 
    		 } 
    		 else if(sParameterName[1] == 'APPROVED' ) { 
    			 var pathArray = oDoc.location.search.substring();
    			 $.ajax({
						url:ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/add-ewalletData" +pathArray,
						method: "GET",
	          
						success: function (response) {
							$("#iframe_Popup_guest").hide();
								
							if (response.isRedirectToOrderSummary == true) {
							window.parent.location.href = ACC.config.encodedContextPath +"/checkout/multi/order-summary/view";
							}
							else{
								$("#paymentFailedGc").show();
							}
						},
						error: function(){
							$("#paymentFailedGc").show();
						}
					})
    			 
    		 }
    		}
    	
    	}setTimeout(alertFunction, 10);
    	
    	loading.stop();
    } catch (err) {
	
	}


    


}

$(document).ready(function () {
		sessionStorage.removeItem("IsProp65Shown");
	$('input[name=requestedMeridian]').click(function () {
        $('input[name=requestedMeridian]:not(:checked)').closest(".radio-wrapper").removeClass("selected-time");
        $('input[name=requestedMeridian]:checked').closest(".radio-wrapper").addClass("selected-time");
    });
	
	$("#iframe_Popup_guest").hide();
	 $(".choosepaymentB2b_GC").hide();
	$('.shipmentcheck').change(function() {
		  if(this.checked){
			  $('.sipping-form').hide();
			  $("#shipping_div").show();
  			$("#shipping_btn").show();
  			$(".shipping-Newaddress").hide();
	    	  $(".shipping-Newregion").hide();
	    	  $("#shippingaddressdata").show();
	    	  $("#shippingaddressdat1").show();
	    	  $("#shippingcitydata").show();
	    	  $("#shippingstatedata").show();
	    	  $("#shippingzipdata").show();
			  $(".shipping-ponumber").removeClass("hidden");
		  }

	      else{
	    	  $('.sipping-form').show();
	    	  $("#shipping_div").hide();
  			$("#shipping_btn").hide();
  			$(".shipping-Newaddress").show();
	    	  $(".shipping-Newregion").show();
	    	  $("#shippingaddressdata").hide();
	    	  $("#shippingaddressdat1").hide();
	    	  $("#shippingcitydata").hide();
	    	  $("#shippingstatedata").hide();
	    	  $("#shippingzipdata").hide();
			  $(".shipping-ponumber").addClass("hidden");
	      }

		 });
	
	$('#isSameAsContactInfo').change(function() {
		  if(this.checked){
			  $('.deliveryContact-form').hide();
			  $("#termsPODiv").show();
	    	  $("#delivery_content2").show();
	    	  $("#pickup-delivery").show();
	    	  $(".payment_cbtn").show();
	    	  ACC.checkout.deliveryNewAddressShowForm();

		  }else{
	    	  $('.deliveryContact-form').show();
	    	  $("#termsPODiv").hide();
	    	  $("#delivery_content2").hide();
	    	  $("#pickup-delivery").hide();
	    	  ACC.checkout.deliveryNewAddressDetailsShowForm();
	    	  ACC.checkout.deliveryNewAddressHideForm();
	      }

		 });
	$(document.body).on("change","#date",function() {
		let target = $("#date");
		var selectedDate = new Date(target.datepicker('getDate'));
		var newDate = new Date(target.datepicker('getDate')).toString('mm/dd/YYYY');
		var currentDate = new Date();
		var tomorrowDate = new Date();
		tomorrowDate.setDate(tomorrowDate.getDate() + 1);
		var tomorrow=(tomorrowDate.toDateString() === selectedDate.toDateString())
		var today = (currentDate.toDateString() === selectedDate.toDateString());
		var nextWorkingday=ACC.checkout.toFindNextWorkingDay() //get date of nextworkingday
		var isNextWorkingday = (nextWorkingday.toDateString() === selectedDate.toDateString()); 
		var orderType = $("#orderType").val();
		var inputAM = $('#AM[value="AM"]');
		var inputPM = $('#PM[value="PM"]');
		var inputAllDay = $('#ANYTIME[value="ANYTIME"]');
		inputAllDay.attr('disabled', false).closest(".radio-wrapper").removeClass("elemoverlayparent").find(".elemoverlay").remove();
		inputAM.attr('disabled', false).closest(".radio-wrapper").removeClass("elemoverlayparent").find(".elemoverlay").remove();
		inputPM.attr('disabled', false).closest(".radio-wrapper").removeClass("elemoverlayparent").find(".elemoverlay").remove();
		if(inputAM.prop('checked')){
			inputAM.prop('checked', false).closest(".radio-wrapper").removeClass("selected-time");
		}
		if(inputPM.prop('checked')){
			inputPM.prop('checked', false).closest(".radio-wrapper").removeClass("selected-time");
		}
		if(inputAllDay.prop('checked')){
			inputAllDay.prop('checked', false).closest(".radio-wrapper").removeClass("selected-time");
		}

		$("#requestedDate").val(new Date(ACC.checkout.formatDate(new Date(newDate), 'mdy')));
		target.val(ACC.checkout.formatDate(new Date(newDate), 'mdy'));
		if (orderType == 'PICKUP') {
			if (today) {
				var hr = parseInt($("#timeLimit").val().split(":")[0]);
				var min = parseInt($("#timeLimit").val().split(":")[1]);
				if ((currentDate.getHours() > hr) || (currentDate.getHours() >= hr && currentDate.getMinutes() >= min)) {
					inputAM.attr('disabled', true).closest(".radio-wrapper").removeClass("selected-time").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
				}
				if (ACC.checkout.isHalfDay(selectedDate)) {
					inputPM.attr('disabled', true).closest(".radio-wrapper").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
				}
			} else {
				if (ACC.checkout.isHalfDay(selectedDate)) {
					inputPM.attr('disabled', true).closest(".radio-wrapper").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
				}
			}
		}
		else if (orderType == 'DELIVERY') {
			if (today) {
				inputAM.attr('disabled', true).closest(".radio-wrapper").removeClass("selected-time").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
			}
			else if (tomorrow && !ACC.checkout.isOrderPlacedBefore9()) {
				if (ACC.checkout.isHalfDay(selectedDate)) {
					$("#AM").click();
				} else {
					$("#PM").click();
				}
				$(".requested-time").addClass('hidden');
			} else {
				if (currentDate.getDay()==6 || currentDate.getDay()==0) {
                    if (isNextWorkingday) {
                        $(".requested-time").addClass('hidden');
						if (ACC.checkout.isHalfDay(selectedDate)) {
							$("#AM").click();
						} else {
							$("#PM").click();
						}
                    } else {
                        $(".requested-time").removeClass('hidden');
                    }
                } else {
				if (ACC.checkout.isHalfDay(selectedDate)) {
					inputPM.attr('disabled', true).closest(".radio-wrapper").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
					inputAllDay.attr('disabled', true).closest(".radio-wrapper").addClass("elemoverlayparent").append('<div class="elemoverlay"></div>');
				}
				$(".requested-time").removeClass('hidden');
			 }
			}
		}
		ACC.checkout.validateCurrentDate();
		if(orderType == 'PICKUP')
		{
			ACC.checkout.validateStoreStatus();
		}		
		ACC.checkout.removeErrorClassForCheckoutForm('#date', '#errorDate', '#date\\.errors');
	}); // ./change - #date function
});

$(document).on("click", "#suggestedValidAddressSubmit", function()
{			
	try{
		_satellite.track('contactDetails');
	} catch (e) {}
	var addressType = $("input[name='address\\.addressSuggestionOption']:checked").val();
	var pageName = $(".pagename").val();
	if(addressType == "suggestedAddress")
	{
		copySuggestedValidAddressToForm();
		if (pageName == 'SiteOne Checkout Page' && $(".isguestuser").val() == 'false') { 
			ACC.checkout.siteoneAddJSONData.line1 = $("#suggestedAddress\\.line1").text();
			ACC.checkout.siteoneAddJSONData.line2 =$("#suggestedAddress\\.line2").text()
			ACC.checkout.siteoneAddJSONData.townCity = $("#suggestedAddress\\.city").text();
			ACC.checkout.siteoneAddJSONData.postcode = $("#suggestedAddress\\.zipcode").text();
		}

		
	} 
	ACC.colorbox.close();
	if(pageName == "Add Edit Address Page" && (addressType == "suggestedAddress" || addressType == "originalAddress") ){
		$("#siteOneAddressForm" ).submit();
	}
	else if(pageName == 'SiteOne Checkout Page' && $(".isguestuser").val() == 'false'){
		
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/order-type/add-address", 
			method:"POST",
			data:ACC.checkout.siteoneAddJSONData,
			// Dummy data
			// {
			// 	addressId: '',
			// 	companyName: "Irrigation",
			// 	line1: b2bSAddyl1,
			// 	line2: "",
			// 	townCity: "Woodland Hills",
			// 	regionIso: "US-CA",
			// 	postcode: "91364",
			// 	phone: "",
			// 	countryIso: "US",
			// 	district: "",
			// 	saveInAddressBook:"true",
			// 	defaultAddress:"true",
			// 	_saveInAddressBook: "on",
			// 	},	
			beforeSend: function ()
			{
				loading.start();
			},
			success: function(response)
			{
				ACC.colorbox.close();
				if(null != response && response !="" && response != undefined) {
					$("#addAddressSuccess").html("<font color='green'><div class='panel-body'>"+ACC.config.addressCreated+"</div></font>");
					if ($("#cboxLoadedContent #saveAddressInMyAddressBook").is(":checked"))	{
						$("#deliveryAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
						if($('.isSplitCartCheckoutPickup').val() == 'true'  && $("#orderType").val() == "PICKUP") {
							$("#splitDeliveryAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
						}
							$(".deliveryAddresses").val(response[1]);
						$("#deliveryAddress").change();
						$(".delivery-address").html(details);
					} else {
						 var region="";
						 if(response[0].region && response[0].region.isocodeShort){
								region= response[0].region.isocodeShort ;
							}
						 var details =  response[0].line1 + "<BR>" 
										   + response[0].town + ", "
										   + region + " " + response[0].postalCode + "<BR>"
										   + "<div class='newDelivery-phone'> M: <a class='tel-phone' href='tel:"+response[0].phone+"'>"+response[0].phone+"</a></div>";
							
										   
										 
						 ACC.checkout.populateRegionDetails(region);
						 $('#deliveryAddress option')[0].selected = true;
						 if($('.isSplitCartCheckoutPickup').val() == 'true'  && $("#orderType").val() == "PICKUP") {
						 	$("#splitDeliveryAddress option")[0].selected = true;
						 }
							$(".delivery-address").html(details);
							if(!response[0].phone){
								$(".newDelivery-phone").hide();
							 }
						
							 $('.delivery-phone').hide();
						 
							$('#addressId').val(response[0].id);
						if($('.isSplitCartCheckoutPickup').val() == 'true' && $("#orderType").val() == "PICKUP") {
							$("#shippingAddressId").val(response[0].id);
						}
						var contact = $('#contactId').val();
							
							ACC.checkout.populateContactAddress(contact);
							ACC.checkout.checkoutAddressNotSaved = true;
					}
				}
				loading.stop();

			},
			error: function(response, textstatus, message) 
			{
			  loading.stop();
			  if(textstatus == "timeout" || textstatus == "error") 
			  {
				  $("#cboxLoadedContent .addAddressError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>" + ACC.config.unableToAdd + "</div></font></div>");
				  ACC.colorbox.open("", {
					html: "<div><font color='red'><div class='panel-body'>"+ACC.config.unableToAdd+"</div></font></div>",
                    width: "550",
                    height: "auto",
                    close:'<span class="glyphicon glyphicon-remove"></span>',
				})
				} 
				
			},
			complete:function(response){

			}
		});
	}
	else if(pageName != "Add Edit Address Page" && $(".isguestuser").val()=='true'){
		ACC.checkout.saveContacForm();
		try {
			_satellite.track("fullfillmentDetails");
		} catch (e) {}	
	}
});
        
function paymenthB2BGenerate(){
	$.ajax({
		url:ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/payment",
		method: "GET",

		success: function (response) {
			var asmData=response.asm;
			$("#asmSession").val(asmData);
		
			$(".descption-payment-details-B2b").hide();
			
			var ewalletCardNumber=response.ewallet.length;
			
			 for (var i=0; i< response.ewallet.length; i++){
				 $("#cardselectbox").append('<option value="' + response.ewallet[i]["valutToken"] +'">' + response.ewallet[i]["nickName"] +'</option>');
				 
			 }
			 if(ewalletCardNumber>0){
			 $('#cardselectbox').attr('disabled', false);
			 }
			 else{
					$('#cardselectbox').attr('disabled', true);
			 }
			if(response.paymentconfigData.isPOAEnabled == true && response.paymentconfigData.payWithEwallet == true && response.paymentconfigData.payWithCC == true)
				 {
				 if(response.isPOAEnabled == true){
				 $("#SITEONE_ONLINE_ACCOUNT").prop("checked", true);
				 $("#div_SITEONE_ONLINE_ACCOUNT").show();
				 $("#SITEONE_ONLINE_ACCOUNT").val(response.paymentOption[0].code);
					$("#PAY_ONLINE_WITH_CREDIT_CARD").val(response.paymentOption[1].code);
					$("#PAY_AT_BRANCH").val(response.paymentOption[2].code);	//CA Edit $(".currentBaseStoreId").val() == "CA" ? "" : 
					$("#payment_method_radio").val(response.paymentOption[0].code);
					$(".descption-payment-details-B2b").hide();
					
				 }
				 else{
					 $("#div_SITEONE_ONLINE_ACCOUNT").hide();
					 
					
						$("#PAY_ONLINE_WITH_CREDIT_CARD").val(response.paymentOption[0].code);
						$("#PAY_AT_BRANCH").val(response.paymentOption[1].code);	//CA Edit
						$("#payment_method_radio").val(response.paymentOption[0].code);
						$(".descption-payment-details-B2b").hide();
						$(".descption-payment-details").show();
						if($("#showCreditCard").val() == 'true' && $(".currentBaseStoreId").val() != "CA"){	//CA Modified
							$("#PAY_ONLINE_WITH_CREDIT_CARD").prop("checked", true);
							$(".card-selected").show();
						}
						else{
							$("#payment_method_radio").val(response.paymentOption[1].code);
						}
				 }
				 }
			else if(response.paymentconfigData.isPOAEnabled == true && response.paymentconfigData.payWithEwallet == null && response.paymentconfigData.payWithCC == null)
			 {
				if(response.isPOAEnabled == true){
				 $("#SITEONE_ONLINE_ACCOUNT").prop("checked", true);
				 $("#div_SITEONE_ONLINE_ACCOUNT").show();
				 $("#SITEONE_ONLINE_ACCOUNT").val(response.paymentOption[0].code);
					$("#PAY_ONLINE_WITH_CREDIT_CARD").val(response.paymentOption[1].code);
					$("#PAY_AT_BRANCH").val(response.paymentOption[2].code);	//CA Edit
					$("#payment_method_radio").val(response.paymentOption[0].code);
			 $(".descption-payment-details").hide();
			 $("#div_PAY_ONLINE_WITH_CREDIT_CARD").hide();
			
			 
			 $(".card-selected").hide();
			 	 $(".descption-payment-details-B2b").hide();
			 $("#POA_terms").show();
			 $(".descption-payment-details").show();
			 
			 $(".payment-to-branch").hide();
				$(".payment-to-online").hide();
				$(".payment-to-acount").show();
			
			
			 }
				else{
					 $("#PAY_AT_BRANCH").prop("checked", true);
						
					 $("#payment_method_radio").val(response.paymentOption[0].code);
					
						$("#PAY_AT_BRANCH").val(response.paymentOption[0].code);	//CA Edit
						 $("#div_SITEONE_ONLINE_ACCOUNT").hide();
					 $("#div_PAY_ONLINE_WITH_CREDIT_CARD").hide();
				
				 $(".descption-payment-details").show();
				 $(".card-selected").hide();
				 $(".payment-to-branch").show();
					$(".payment-to-online").hide();
					$(".payment-to-acount").hide();
					
				}
			 }
			 else if(response.paymentconfigData.isPOAEnabled == false && response.paymentconfigData.payWithEwallet == true && response.paymentconfigData.payWithCC == true)
			 {
				 
				 $("#div_SITEONE_ONLINE_ACCOUNT").hide();
				 if($(".currentBaseStoreId").val() != "CA"){	//CA Modified
				 $("#PAY_ONLINE_WITH_CREDIT_CARD").prop("checked", true);
				 
				 $(".card-selected").show();
				 $("#payment_method_radio").val(response.paymentOption[0].code);
				 }
				 else{
					$("#payment_method_radio").val(response.paymentOption[1].code);
				 }
					$("#PAY_ONLINE_WITH_CREDIT_CARD").val(response.paymentOption[0].code);
					$("#PAY_AT_BRANCH").val(response.paymentOption[1].code);	//CA Edit
					
					$(".descption-payment-details-B2b").hide();
					$(".descption-payment-details").show();
					if($("#showCreditCard").val() == 'true'){
					$(".payment-to-branch").hide();
					$(".payment-to-online").show();
					$(".payment-to-acount").hide();
					}
			 }
			 
			
			
			 else if(response.paymentconfigData.payWithEwallet == null && response.paymentconfigData.payWithCC == null && response.paymentconfigData.isPOAEnabled == false)
			 {
				 $("#PAY_AT_BRANCH").prop("checked", true);
				
				 $("#payment_method_radio").val(response.paymentOption[0].code);
				
					$("#PAY_AT_BRANCH").val(response.paymentOption[0].code);	//CA Edit
					 $("#div_SITEONE_ONLINE_ACCOUNT").hide();
				 $("#div_PAY_ONLINE_WITH_CREDIT_CARD").hide();
			
			 $(".descption-payment-details").show();
			 $(".card-selected").hide();
			 $(".payment-to-branch").show();
				$(".payment-to-online").hide();
				$(".payment-to-acount").hide();
			
			 
			 }
			bindSelectBranch();
			loading.stop();
			
			if(response.isCreditPaymentBlocked == true){
				ACC.checkout.creditcardDisable();
				ACC.pendo.captureEvent("3XAUTHENTICATED");
			}	
			
		},
		error: function () {
			ACC.myquotes.quotePopupError();
			loading.stop();	
		}
	})
}

function transportKeyGCGenerate(){
	$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/getBoardCardTransportKey?isCheckout="+true,
			method: "POST",
	
			success: function (transportKey) {
	
				if (transportKey != null && transportKey != "Decline") {
					loading.stop();
					var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey=' + transportKey;
			
					$(".Pop-up-myIframe ").attr('src', redirecturl );
					$("#iframe_Popup_guest").show();
					if(analyticsPaymentMethodCheck==false){
						analyticsPaymentMethodCheck= true;
					try {
		            	  _satellite.track("paymentDetails");
		            } catch (e) {}
					}
				}
			}
		});
}

function paymentContentLoadB2B(e) {
	
	try {
		
		var oIframe =document.getElementById('myIframe');
    	// for compatibility
		
    	var oDoc = oIframe.contentWindow || oIframe.contentDocument;
    	
    	if (oDoc.document) {
    		loading.start();
    		$("#iframe_Popup").hide();
    		 var  oDoc = oDoc.document;
    		 
    		 var pathArray = oDoc.location.search.substring();
			 $.ajax({
					url:ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/add-ewalletData" +pathArray,
					method: "GET",
          
					success: function (response) {
						loading.start();
						 $("#iframe_Popup").hide();
						if (response.isRedirectToOrderSummary == true) {
						window.parent.location.href = ACC.config.encodedContextPath +"/checkout/multi/order-summary/view";
						}
						
						else if(response.isRedirectToOrderSummary == false && response.isSuccessfull == false){
							ACC.checkout.creditcardDisable();
							window.scrollTo(0,0);
							ACC.checkout.creditcardDisableErrPopup(response.errorMessage); //creditcard Disable
							ACC.pendo.captureEvent("3XAUTHENTICATED");
						}						
						else if(!response.isSuccessfull)
							{
							var errror =response.errorMessage;
							$("#cayanFailure").html(errror);
							$("#cayanFailure").show();
							$("#paymentFailedGc").hide();
							  $(".card_holder_nickname").val('');
			    	            $("#savecard_check" ).prop( "checked", false );
			    	            $( ".card_holder_nickname" ).prop( "disabled", true );
							}
						loading.stop();
					}
				})
    		
    		 
    		}
    	loading.stop();
    	
    } catch (err) {
    	
	}


   


}
function bindSelectBranch() {
	
	
	 $("#iframe_Popup").hide();
	$(".payment-to-branch").show();
	$(".payment-to-online").hide();
	$("#payAtBranch").show();
	$(".card-selected").hide();
	  $(".payment-to-acount").hide();
	$('#POA_terms').hide();
	$("#paymentFailedGc").hide();
	 $("#cayanFailure").hide();
	 $(".descption-payment-details-B2b").hide();
	$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineAccount-border").hide();
	$("#div_SITEONE_ONLINE_ACCOUNT .payOnlineCreditCard-border").hide();
	$("#div_PAY_ONLINE_WITH_CREDIT_CARD .payOnlineCreditCard-border").hide();
	$("#div_PAY_ONLINE_WITH_CREDIT_CARD .card-selected").hide();
	$("#PAY_AT_BRANCH .payOnlineCreditCard-border ,#PAY_AT_BRANCH .payOnlineAccount-border").hide();

		var id= $('input:hidden[id="payment_method_radio"]');
		ACC.checkout.paymentRadioButton(id,'load');
	
		$('input:radio[name="radio-group"]').on('change', function(e) {
		ACC.checkout.paymentRadioButton($(this),'change');
	});


}

function createPopupContent(addressVerificationData){
	
	var popupContent = "";	
	
	if (!addressVerificationData.isAddressValid) {
		if ($(".isguestuser").val() == 'false' && $(".pagename").val() == "SiteOne Checkout Page") {
			popupContent ="<p class='m-b-15'>" + ACC.config.veryfyAddressError + "</p><button class='btn btn-default btn-block' onclick=\"$('.newaddress').click()\">" + ACC.config.editAddress + "</button>";
		 }
		else {
			popupContent ="<p class='m-b-15'>" + ACC.config.veryfyAddressError + "</p><button class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.editAddress + "</button>";
		}
		
	}
	else if(addressVerificationData.isAddressCorrected){

		if($(".pagename").val() == "Add Edit Address Page"){

			popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#siteOneAddressForm #address\\.line1").val() +"</span><div class='cl'></div>"

			if($("#siteOneAddressForm #address\\.line2").val() != ""){
				popupContent = popupContent +  $("#siteOneAddressForm #address\\.line2").val() +"<div class='cl'></div>";
			}

		   popupContent = popupContent + $("#siteOneAddressForm #address\\.townCity").val() +"<div class='cl'></div>"+ $("#siteOneAddressForm #address\\.region option:selected").text() +"<div class='cl'></div>"+ $("#siteOneAddressForm #address\\.postcode").val() +" </div></div>";

			popupContent = popupContent+"<div class='col-md-6 col-xs-12 margin-top-add'><span class='hidden-md hidden-lg'><br/><br/></span><span class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.suggestedAddress+"</b></span><div class='cl'></div><p></p>";

			popupContent = popupContent+"<div class='add-address-sec'><span id='suggestedAddress.line1'  data-line1='"+addressVerificationData.street+"'>"+ addressVerificationData.street +"</span>";

			popupContent = popupContent+"<span id='suggestedAddress.line2' data-line2='"+addressVerificationData.street2+"'>"+ addressVerificationData.street2+"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.city' data-city='"+addressVerificationData.city+"'>"+ addressVerificationData.city +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.county' data-county='"+addressVerificationData.county+"'>"+ addressVerificationData.county +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.state'  data-state='"+addressVerificationData.state+"'>"+ addressVerificationData.state +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.zipcode'  data-zipcode='"+addressVerificationData.zipcode+"'>"+ addressVerificationData.zipcode +"</span>";

			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidAddressSubmit'/></div><div class='col-md-4 col-sm-6 col-xs-4'><div class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.cancel + "</div></div></form>";

		} else {
			if ($(".isguestuser").val() == 'false') {
				popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ ACC.checkout.b2bAddy1 +"</span><div class='cl'></div>"
		  
			if($("#siteOneAddressForm #checkoutContact\\.addressLine2").val() != ""){
				popupContent = popupContent +  ACC.checkout.b2bAddy2 +"<div class='cl'></div>";
			}
		
		   popupContent = popupContent + ACC.checkout.b2bCity +"<div class='cl'></div>"+ ACC.checkout.b2bSiteoneState+"<div class='cl'></div>"+ ACC.checkout.b2bZip+" </div></div>";
		
			} else { 
				popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#SiteOneGCContactForm #checkoutContactaddressLine1").val() +"</span><div class='cl'></div>"
		  
				if($("#SiteOneGCContactForm #checkoutContact\\.addressLine2").val() != ""){
					popupContent = popupContent +  $("#SiteOneGCContactForm #checkoutContact\\.addressLine2").val() +"<div class='cl'></div>";
				}
		
		   		popupContent = popupContent + $("#SiteOneGCContactForm #checkoutContactcity").val() +"<div class='cl'></div>"+ $("#SiteOneGCContactForm #checkoutContactstate option:selected").text() +"<div class='cl'></div>"+ $("#SiteOneGCContactForm #checkoutContactzip").val() +" </div></div>";
		
			}
			
			popupContent = popupContent+"<div class='col-md-6 col-xs-12 margin-top-add'><span class='hidden-md hidden-lg'><br/><br/></span><span class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.suggestedAddress+"</b></span><div class='cl'></div><p></p>";
		
			popupContent = popupContent+"<div class='add-address-sec'><span id='suggestedAddress.line1'  data-line1='"+addressVerificationData.street+"'>"+ addressVerificationData.street +"</span>";
		
			popupContent = popupContent+"<span id='suggestedAddress.line2' data-line2='"+addressVerificationData.street2+"'>"+ addressVerificationData.street2+"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.city' data-city='"+addressVerificationData.city+"'>"+ addressVerificationData.city +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.county' data-county='"+addressVerificationData.county+"'>"+ addressVerificationData.county +"</span> <div class='cl'></div>";
			
			popupContent = popupContent+"<span id='suggestedAddress.state'  data-state='"+addressVerificationData.state+"'>"+ addressVerificationData.state +"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.zipcode'  data-zipcode='"+addressVerificationData.zipcode+"'>"+ addressVerificationData.zipcode +"</span>";	        						
        					
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidAddressSubmit'/></div><div class='col-md-4 col-sm-6 col-xs-4'><div class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.cancel + "</div></div></form>";
		}		
	}
	return popupContent;
}

function copySuggestedValidAddressToForm()
{
	if($(".pagename").val() == "Add Edit Address Page"){

		$("#siteOneAddressForm #address\\.line1").val($("#suggestedAddress\\.line1").data("line1"));
		$("#siteOneAddressForm #address\\.line2").val($("#suggestedAddress\\.line2").data("line2"));
		$("#siteOneAddressForm #address\\.townCity").val($("#suggestedAddress\\.city").data("city"));
		$("#siteOneAddressForm #address\\.region").val($("#siteOneAddressForm #address\\.country").val()+"-"+$("#suggestedAddress\\.state").data("state"));
		$("#siteOneAddressForm #address\\.postcode").val($("#suggestedAddress\\.zipcode").data("zipcode"));

	} else {
			$("#SiteOneGCContactForm #checkoutContactaddressLine1").val($("#suggestedAddress\\.line1").data("line1"));
			$("#SiteOneGCContactForm #checkoutContact\\.addressLine2").val($("#suggestedAddress\\.line2").data("line2"));
			$("#SiteOneGCContactForm #checkoutContactcity").val($("#suggestedAddress\\.city").data("city"));
			$("#SiteOneGCContactForm #checkoutContactstate").val($("#suggestedAddress\\.state").data("state"));
			$("#SiteOneGCContactForm #checkoutContactzip").val($("#suggestedAddress\\.zipcode").data("zipcode"));
			//$("#SiteOneGCContactForm #address\\.district").val($("#suggestedAddress\\.county").data("county"));
		
	}


}
