ACC.mixedcartcheckout = {
	_autoload: [
		"bindCheckO",
		"deliveryModeSelection",
		"bindAddPhone",
		"binbOrderSubmitMethod",
		"addNewAddressCheckout",
		"bindAddressValidations",
		"bindAddAddressSubmit",
		"bindSelectBranch",
		"checkHomeOwnerCase",
		"bindContactAddressValidations",
		"bindEditFulFilmentDetails",
		"checkoutStepEmailValidation",
		"bindSingleCheckout",
    	"paymentPOCheck",
    	"yourItemsScroll"
	],
	checkIfFlorida :function(id, errorid, errorPath, errMsg, errStateMsg){
		
        ACC.formvalidation.vaildateShippingState(id, errorid, errorPath, errMsg, errStateMsg);

	},
	removeErrorClassForCheckoutForm: function (input, error, errorText)
	{
		$(error).html('');
		$(input).removeAttr('style');
		$(error).removeAttr('style');
		$(input).css("background", "#aaaaaaa");
		$(input).css("border-color", "#cccccc");
		$(input + "\\.errors").hide();
		$(errorText).html('');
	},
	bindCheckO: function ()
	{
		var cartEntriesError = false;
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
		
	},
	bindSingleCheckout: function(){
		var stepnumber = 1;
		var addressnumber=1;
		let deliveryNumber=1;
		 $('.contact-data-box').each(function() {
		    $(this).find(".numberCircle-div").append('<span class="numberCircle"> ' + stepnumber + ' </span>');
		    stepnumber++;
		});
		if($('.pickup-mixedcart-location').length > 1){
			$('.pickup-mixedcart-location').each(function() {
				$(this).find(".address-number").append('<span class="numberorder">'+" #"+addressnumber+' </span>');
					addressnumber++;
			});
		}
		if($('.delivery-mixedcart-location').length > 1){
			$('.delivery-mixedcart-location').each(function() {
				$(this).find(".address-number").append('<span class="numberorder">'+" #"+deliveryNumber+' </span>');
					deliveryNumber++;
			});
		}
		$(document).on("click", ".submit-ordertype-data", function(e)
		{
			var target = $(this);
			let targetNum = Number(target.attr("data-aconum"));
			let targetType = target.attr("data-type");
			let error;
			var guestUser=$(".isguestuser").val();
			if(guestUser == "true"){ //checking for shipping same state
				if(targetType == "shipping" && $(".mixedcart-"+ targetType +"-check").prop("checked") && $("#mixedcart-"+ targetType +"statedata").html() != _AAData.storeState){
					$(".same-"+ targetType +"-state-error").removeClass("hidden");
					error = true;
				}
			}
			else
			{
				if($("."+ targetType +"Error").length && targetType == "shipping" && $("."+ targetType +"-region").html() != _AAData.storeState){
					ACC.mixedcartcheckout.checkIfFlorida('#MixedcartShippingAddress', '#errorShippingAddressRadio', '#MixedcartShippingAddress\\.errors', ERRORMSG.global.address, ERRORMSG.global.shippingStates);
					error = true;
				}
			}
			if(!error){
				
				var bigbagInstruction=$(".isbigBagInstruction").val() || '';

				var checkoutData=$('#siteOneOrderTypeForm').serialize().split('&').map((y)=>{
					if(bigbagInstruction.length){
					var yData = y.split("=")[1] || '';
					return y.startsWith('specialInstruction=') ? 'specialInstruction='+ bigbagInstruction + ' '+ yData : y
					}
					else{
						return y;
					}
					}).join('&');
					
			$.ajax({
				url: ACC.config.encodedContextPath + "/checkout/multi/saveFulfilmentOptions", 
				method:"POST",
				data:checkoutData,	
				
				success: function(response)
				{
					
					var taxAmount = response.cartData.totalTax.formattedValue;
					$("#taxValue").html(taxAmount);
					
					var totalAmountWithTax = response.cartData.totalPriceWithTax.formattedValue;
					$("#totalPriceWithTax").html(totalAmountWithTax);
					var deliveryFees = response.cartData.deliveryFreight;
					if(deliveryFees != null){
						$("#deliveryfees").html("$"+deliveryFees);
						$("#delivery_cost").html("$"+deliveryFees);
					}else{
						$("#deliveryfees").html("$0.00");
						$("#delivery_cost").html("$0.00");
					}
					if(targetType == "pickup"){
						ACC.checkout.showProp65Popup(targetType);
						$("#pickup_Details").show();
						$(".direction-link").hide();
						$("#pickup-delivery").hide();
						$("#pickup_Location_Div").hide();
						$(".edit-pickup-information").show();
					}
					else if(targetType == "delivery"){
						ACC.checkout.showProp65Popup(targetType);
						$(".direction-link").show();
						$(".shipping_fee").show();
					}
					else{ //(targetType == "shipping")
					
					}
					
					
					if(guestUser== 'true'){
						ACC.mixedcartcheckout.transportKeyGCGenerateMxedCart();
						ACC.mixedcartcheckout.openNextBlockAccordion(targetType, targetNum, "#iframe_Popup_guest");
					}
					else if(guestUser== 'false'){
						paymenthB2BGenerate();
						ACC.mixedcartcheckout.openNextBlockAccordion(targetType, targetNum, ".choosepaymentB2b_GC");
						 bindSelectBranch();
						 loading.stop();
					}
				}
			});
			
			
			}
			
			});

		
		$(document).on("click", "#mixedCartEditPickup", function(){
			ACC.mixedcartcheckout.closeOpenBloc("pickup");
		});
	},
	populateContactAddress: function(contact,mode){
		    if(contact != null && contact != '' && contact != undefined)
					{
						var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
						if(mode=="shipping")
						{
								jQuery.each(customers, function(i, val)
						{
							if(val.displayUid === contact)
							{
								if(val.contactNumber != null && val.contactNumber != '' && val.contactNumber != undefined)
								{
									$(".shipping-phone").html("m: <a class='tel-phone' href='tel:"+val.contactNumber+"'>"+val.contactNumber+"</a>");
								}
								else
								{
									$(".shipping-phone").html("m: NA");
								}
							}
						 });
						
						}
						else{
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
						
						
					}
		    },
		    
		    
	deliveryModeSelection: function()
    {	
		if($(".mixedcart-checkout").length)
		{
		if($("#pageId").val() == 'siteOneCheckoutPage')
		{
			var contact = null;
			if(null != $('#contactId').val() || $('#contactId').val() != "")
			{
			  contact = $('#contactId').val();
			}
			else
			{
			  contact = JSON.parse(document.getElementsByClassName("currentCustomer")[0].value).displayUid;
			}

			ACC.mixedcartcheckout.populateContactDetails(contact,null);

			
			var selectedValue = $('#MixedcartdeliveryAddress').find(":selected").val();
	         var selectedValue1 = $('#MixedcartShippingAddress').find(":selected").val();
			  if(selectedValue != 'selectDefault')
			  {
					$('#deliveryAddressId').val(selectedValue);
				  	ACC.mixedcartcheckout.populateAddressDetails($('#deliveryAddressId').val(),$('#MixedcartdeliveryAddress').data('mode'));
					$('#shippingAddressId').val(selectedValue1);
				  ACC.mixedcartcheckout.populateAddressDetails($('#shippingAddressId').val(),$("#MixedcartShippingAddress").data('mode'));
				  $('#addressId').val(selectedValue);
				  ACC.mixedcartcheckout.populateAddressDetails($('#addressId').val(),null);
			  }
			  else
			  {
				  $('.delivery-phone').html('');
		          $('.shipping-phone').html('');
			  }

			ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#deliveryAddressRadio', '#errorDeliveryAddressRadio', '#deliveryAddressRadio\\.errors');
		}

		
		
		$(document.body).on("input blur","#poNumberReq",function()
				{
					ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#poNumberReq', '#errorPONumberRequired', '#poNumberReq\\.errors');
				});
	
		$(document.body).on("change",".requested-time",function()
		{
			ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#meridianRadio', '#errorMeridianRadio', '#meridianRadio\\.errors');
		});
		
		$(document.body).on("click",".saveCustomerSelection",function()
		{
			var selectedValue = $('#customers').find(":selected").val();
			$('#contactId').val(selectedValue);
			ACC.mixedcartcheckout.populateContactDetails(selectedValue,null);
			ACC.colorbox.close();
		});
		
		
		$(document.body).on("click",".saveCustomerSelectionB2bUserMixedCart",function(e)
				{
					var selectedValue = $('#customers').find(":selected").val();
				    var deliveryMode=$(this).data('mode');
					  e.preventDefault();
			    	    $.ajax({
			    	            url : ACC.config.encodedContextPath + "/checkout/multi/update-contact?uid=" + selectedValue+"&deliveryMode="+deliveryMode,
			    	            method:"POST",
			    	          
			    	            success : function(response)
			    	            {
	              			                      if(deliveryMode==null){
										 $('#contactId').val(selectedValue);
										}
										  else
										{ 
										      if(deliveryMode=="pickup"){
										          	    $('#contactId').val(selectedValue);
										      }else if(deliveryMode=="standard-net"){
										      	$('#deliveryContactId').val(selectedValue);
										      }else{
										      	$('#shippingContactId').val(selectedValue);
										      }
				                        }
			    	            	
			    					ACC.mixedcartcheckout.populateContactDetails(selectedValue,deliveryMode);
			    					ACC.colorbox.close();
			    					$(".data-update").hide();
			    	            }
			    	    });
				});
		
		$(document).on("change","#MixedcartdeliveryAddress",function()
		{	
			var selectedValue = $('#MixedcartdeliveryAddress').val();
			var deliveryMode = $(this).data('mode');
			$('#deliveryAddressId').val(selectedValue);
			ACC.mixedcartcheckout.populateAddressDetails(selectedValue, deliveryMode);
			ACC.mixedcartcheckout.populateContactAddress($('#deliveryContactId').val(), deliveryMode);
			
		});
		$(document).on("change","#MixedcartShippingAddress",function()
		{
			var deliveryMode = $(this).data('mode');
			var address = $('#MixedcartShippingAddress').val();
		 	$('#shippingAddressId').val(address);
			ACC.mixedcartcheckout.populateAddressDetails(address, deliveryMode);
			if($(".shipping-region").html() != _AAData.storeState){
				ACC.mixedcartcheckout.checkIfFlorida('#MixedcartShippingAddress', '#errorShippingAddressRadio', '#MixedcartShippingAddress\\.errors', ERRORMSG.global.address, ERRORMSG.global.shippingStates)
			}
			else{
				ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#MixedcartShippingAddress', '#errorShippingAddressRadio', '#MixedcartShippingAddress\\.errors');
			}
			ACC.mixedcartcheckout.populateContactAddress($('#shippingContactId').val(), deliveryMode);
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
		});
		
		$(document.body).on("click",".mixCartChangeContact",function(e)
		{
			e.preventDefault();
			try{
				 _satellite.track('contactDetails');
				 } catch (e) {}
			if(document.getElementsByClassName("allCustomers")[0].value != "")
			{
			    var customers = JSON.parse(document.getElementsByClassName("allCustomers")[0].value);
				var option = null;
				var selectedCustomerVal = null;
				var selectedCustomer = $("#contactId").val();
				var orderType = this.id;
				var deliveryMode=null;
				var orderTypeDisplayText = "";
				if(orderType == 'pickUpChangeContact') {
					orderTypeDisplayText = "Pickup contact1";
					deliveryMode="pickup"
				} else if(orderType == 'deliveryChangeContact') {
					orderTypeDisplayText = "Delivery contact";
					deliveryMode="standard-net";
				}else{
					orderTypeDisplayText ="Shipping Contact"
					deliveryMode="shipping";
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
						"<button type='button' id='save' class='saveCustomerSelectionB2bUserMixedCart btn btn-ConfirmMessage btn-primary btn-block' data-mode="+deliveryMode+">"+ACC.config.save+"</button>" +
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
				
		});
		
		$(document.body).on("click",".changeContactb2b",function(e)
				{
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
								"<button type='button' id='save' class='saveCustomerSelectionB2bUserMixedCart btn btn-ConfirmMessage btn-primary btn-block'>"+ACC.config.save+"</button>" +
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
				});
				
		
		$(document.body).on("change","#termsAndConditions",function()
		{
			ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#termsAndConditions', '#errorTermsAndConditionsCheckBox', '#termsAndConditions\\.errors');
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
		 }
    },
    populateContactDetails: function(contact,deliveryMode)
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
	       if(deliveryMode==null)
	       {
		      	$(".contact-details").html(details);
				$(".delivery-contact-details").html(details);
			    $(".shipping-contact-details").html(details);
				$("#deliveryContactId").val(selectedCustomer.displayUid);
	 			$("#shippingContactId").val(selectedCustomer.displayUid);
			}
	         else
	        { 
	              if(deliveryMode=="pickup")
	              {
	              	$(".contact-details").html(details);
	              }
	              else if(deliveryMode=="standard-net"){
	              	 $(".delivery-contact-details").html(details);
	              }
	              else{
	              	 $(".shipping-contact-details").html(details);
	              }
    	    
	    	    $("#addPhoneMessage").show();
	    	    $("#isContactRequirePhoneNumber").val("Y");
	    	    $("#isAddPhoneNumberUsed").val("N");
	    	    if($("#addressId").val() != null && $("#addressId").val() != undefined && $("#addressId").val() != "")
	    	    {
	    	    	$(".delivery-phone").html("MNA");
	    	    }
    	    }
        }
        else
        {
	        var details = selectedCustomer.name + "<BR>M <a class='textDecorationNone tel-phone' href='tel:"+selectedCustomer.contactNumber+"'>"+selectedCustomer.contactNumber+"</a><BR>" + "<a class='textDecorationNone' href='mailto:"+selectedCustomer.displayUid+"'>"+selectedCustomer.displayUid+"</a>";
		 if(deliveryMode==null){
	      $(".contact-details").html(details);
			 $(".delivery-contact-details").html(details);
		    $(".shipping-contact-details").html(details);
            $("#deliveryContactId").val(selectedCustomer.displayUid);
             $("#shippingContactId").val(selectedCustomer.displayUid);
		}else{ 
        
             if(deliveryMode=="pickup"){
                  	    $(".contact-details").html(details);
              }else if(deliveryMode=="standard-net"){
              	 $(".delivery-contact-details").html(details);
              }else{
              	 $(".shipping-contact-details").html(details);
              }
		    $("#isContactRequirePhoneNumber").val("N");
		    $("#addPhoneMessage").hide();		
		    if($("#addressId").val() != null && $("#addressId").val() != undefined && $("#addressId").val() != "")
    	    {
		    	$(".delivery-phone").html("M <a class='tel-phone' href='tel:"+selectedCustomer.contactNumber+"'>"+selectedCustomer.contactNumber+"</a>");
    	    }
           }
		    
        }
	},
	populateAddressDetails: function(address,mode)
    {
		var selectedAddress = null;
		var addresses=[];
		try{
			addresses= JSON.parse(document.getElementsByClassName("deliveryAddresses")[0].value);
		}catch(e){
			addresses=[];
		}
        
        jQuery.each(addresses, function(i, val)
		{
        	if(val.id === address)
	       	{
	       		selectedAddress = val;
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
	      var details = fullName
	      			  + selectedAddress.line1 + "<BR>" 
	      			  + selectedAddress.town + ", "
	      			  + region + " " + selectedAddress.postalCode + "<BR>";
	      
	      ACC.mixedcartcheckout.populateRegionDetails(region,mode);
          if(mode=="shipping"){
	        $(".shipping-address").html(details);
            }
         else{
         	  $(".delivery-address").html(details);
            }
	    }
	},
	
	populateRegionDetails: function(region,mode)
    {
	var regionValue = region;
		if(mode=="shipping"){
	       $(".shipping-region").html(regionValue);
            }
         else{
         	 $(".delivery-region").html(regionValue);
            }
		
		
    },
	
    populateNewRegionAddressForm: function(response){
		var region="";
		 if(response.addressData.region && response.addressData.region.isocodeShort){
		    	region= response.addressData.region.isocodeShort ;
		    }

		return region;
	},

	populateNewAddressForm: function(response){
		var details =  response.addressData.line1 + "<BR>"
	      			  + response.addressData.line2 + "<BR>"  
				+ response.addressData.town + ", "
				+ ACC.mixedcartcheckout.populateNewRegionAddressForm(response) + " " + response.addressData.postalCode + "<BR>";

		return details;
	},
	populateNewContactForm: function(response){
		var details =  response.addressData.firstName + " " + response.addressData.lastName + "<br>" 
				+ response.addressData.phone + "<br>"
				+ response.addressData.email;

		return details;
	},
	deliveryNewAddressShowForm: function(){
	  $(".delivery-Newaddress").hide();
   	  $(".delivery-Newregion").hide();
   	  $("#mixedcart-deliveryaddressdata").show();
   	  $("#mixedcart-deliveryaddressdat1").show();
   	  $("#mixedcart-deliverycitydata").show();
   	  $("#mixedcart-deliverystatedata").show();
   	  $("#mixedcart-deliveryzipdata").show();
	},
	deliveryNewAddressHideForm: function(){
		 $(".delivery-Newaddress").show();
		 $(".delivery-Newregion").show();
	  $("#mixedcart-deliveryaddressdata").hide();
	  $("#mixedcart-deliveryaddressdat1").hide();
	  $("#mixedcart-deliverycitydata").hide();
	  $("#mixedcart-deliverystatedata").hide();
	  $("#mixedcart-deliveryzipdata").hide();
	},
	saveAlternateDeliveryContactForm: function(){
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/saveAlternateContactDetails", 
    		method:"POST",
    		data:$('#mixedcart-deliveryContactForm :input').serialize(),	
    		
    		success: function(response)
    		{
    		  	$(".delivery-Newregion").html(ACC.mixedcartcheckout.populateNewRegionAddressForm(response));
				$("#delivery_location").val(ACC.mixedcartcheckout.populateNewRegionAddressForm(response));
	 		    $(".delivery-Newaddress").html(ACC.mixedcartcheckout.populateNewAddressForm(response));
	 		    $(".delivery-address").html($(".delivery-Newaddress").html());
	 		    $(".delivery-contact-details").html(ACC.mixedcartcheckout.populateNewContactForm(response));
	 		    ACC.mixedcartcheckout.deliveryNewAddressHideForm();
    			$("#mixedcart-deliveryContactForm").hide();
				$(".mixedcartDeliveryBoxGC").show();
    			$("#delivery_content2").show();
    			$("#termsPODiv").show();
    			$("#pickup-delivery").show();
    			$(".payment_cbtn").show();
    		}
			});
	},
	saveShippingAlternateContactForm: function() {
		
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/saveAlternateContactDetails", 
    		method:"POST",
    		data:$('#mixedcart-shipmentForm :input').serialize(),	
    		
    		success: function(response)
    		{
    			$(".mixedcart-shipping-Newregion").html(ACC.mixedcartcheckout.populateNewRegionAddressForm(response));
	 			$(".mixedcart-shipping-Newaddress").html(ACC.mixedcartcheckout.populateNewAddressForm(response));
	 			$(".shipping-address").html($(".mixedcart-shipping-Newaddress").html());
	 			$(".shipping-contact-details").html(ACC.mixedcartcheckout.populateNewContactForm(response));
    			$("#mixedcart-shipmentForm").hide();
				$(".mixedcartShippingBoxGC").show();
    			$("#shipping_div").show();
    			$("#shipping_btn").show();			        			
    			$("#mixedcart-shippingaddressdata").hide();
    			$("#mixedcart-shippingaddressdata").hide();
  	    	    $("#mixedcart-shippingaddressdat1").hide();
  	    	    $("#mixedcart-shippingcitydata").hide();
  	    	    $("#mixedcart-shippingstatedata").hide();
  	    	    $("#mixedcart-shippingzipdata").hide();
    			$(".mixedcart-florida-error-msg").addClass("hidden");
    		   
    			}
			});
	},
	briteVerifyEmailvalidation: function(emailId, status, orderType){
		var briteverify= document.getElementById('briteVerifyEnable').value;
		 if(briteverify=="true"){
			 url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+emailId;
			 $.ajax({
	                url: url,
	                type: "GET",
	                timeout: 5000,
		            success: function (response)
		            {
			            if(response == "invalid")
			            {
			            	if(orderType == "PARCEL_SHIPPING"){
			            			$("#errorShippingEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>Please enter a valid email address.</div></font></div>");
			            	}else{
			            		$("#errordeliveryEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>Please enter a valid email address.</div></font></div>");   
			            	}
			            }
			            else
			            {
				            if(status)
				            {
				            	if(orderType == "PARCEL_SHIPPING"){
				            		$.ajax({
						        		url:ACC.config.encodedContextPath + "/checkout/multi/validate-address", 
						        		method:"POST",
						        		data:$('#mixedcart-shipmentForm :input').serialize(),		        	
						        		success: function(addressVerificationData){
				                           ACC.address.isAddressChanged = false;
				                           ACC.address.isAddressVerified = true;
						        			if((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404')
						        			{
						        				ACC.mixedcartcheckout.saveShippingAlternateContactForm();
						        			}
						        			else
						        			{		        				        			
							        			var popupContent = ACC.mixedcartcheckout.createShippingPopupContentMixedCart(addressVerificationData);
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
						               
					        		});
				            		
				            	}else{
				            		$.ajax({
						        		url:ACC.config.encodedContextPath + "/checkout/multi/validate-address", 
						        		method:"POST",
						        		data:$('#mixedcart-deliveryContactForm :input').serialize(),		        	
						        		success: function(addressVerificationData){
				                           ACC.address.isAddressChanged = false;
				                           ACC.address.isAddressVerified = true;
						        			if((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404')
						        			{
						        				ACC.mixedcartcheckout.saveAlternateDeliveryContactForm();
						        			}
						        			else
						        			{	        				        			
							        			var popupContent = ACC.mixedcartcheckout.createDeliveryPopupContentMixedCart(addressVerificationData)
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
						               
					        		});
				            		
				            	}
				            }
			            }
		            },
	           
		            error: function(response, textstatus, message) 
		            {
		        	  if(textstatus == "timeout" || textstatus == "error") 
                       {
		        		  if(orderType == "PARCEL_SHIPPING"){
		        			  $("#errorShippingEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
		        		  }else{
		        			  $("#errordeliveryEmailAddress").html("<div class=''><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
		        		  }
                       } 
		            }
			 });
		 }else{
			 if(orderType == "PARCEL_SHIPPING"){
				 ACC.mixedcartcheckout.saveShippingAlternateContactForm();
			 }else{
				 ACC.mixedcartcheckout.saveAlternateDeliveryContactForm();
			 }
		 }
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
		
		$(document).on("click", ".adddeliveryFormSubmitMixedCart", function(e)
				{
			e.preventDefault();
			var status = true;
				ACC.formvalidation.vaildateFields('#checkoutdeliveryfirstName','#errordeliveryFirstName', '#checkoutdeliveryfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
				ACC.formvalidation.vaildateFields('#checkoutdeliverylastName','#errordeliveryLastName', '#checkoutdeliverylastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);	
				var msg = ERRORMSG.global.address;
		        ACC.formvalidation.vaildateFields('#checkoutdeliveryaddressLine1', '#errordeliveryAddressLine1', '#checkoutdeliveryaddressLine1\\.errors', msg);
		        ACC.formvalidation.vaildateFields('#checkoutdeliverystate', '#errordeliveryState', '#checkoutdeliverystate\\.errors', ERRORMSG.global.state);
					
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
					 ACC.formvalidation.removeErrorClassForRegForm('#checkoutdeliveryemail', '#errordeliveryEmailAddress', '#checkoutdeliveryemail\\.errors', ERRORMSG.invalid.email);
				}
				else
				{
					status = false;
					 ACC.formvalidation.addErrorClassForRegForm('#checkoutdeliveryemail', '#errordeliveryEmailAddress', '#checkoutdeliveryemail\\.errors', ERRORMSG.invalid.email);
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
					 
					 ACC.mixedcartcheckout.briteVerifyEmailvalidation(deliveryEmail,status)
					 
				 }
			
	});
	},
	bindAddAddressSubmit: function(e){
	
  		$(document).on("change",".mixedcart-popup #checkoutAddress\\.region", function(e){
			if($(this).attr("data-mode") === "shipping"){
				ACC.checkout.b2bIsFlorida($(this).val(),e.target);
			}
		});
		
		$(document).on("click", ".mixedcart-addAddressFormSubmit", function(e){
			e.preventDefault();
			var status = true;
			var deliveryMode=$(this).data('mode');
	        ACC.formvalidation.vaildateFields('#cboxLoadedContent #checkoutAddress\\.line1', '.errorline1', '#line1\\.errors', ERRORMSG.global.address);
	        ACC.formvalidation.validateFieldBasedOnRegex(e,'#cboxLoadedContent #checkoutAddress\\.townCity', '.errorTownCity', '#townCity\\.errors',ACC.config.checkoutCityMay,ERRORMSG.global.city, 'city');
	        
	        if(deliveryMode === "shipping"){
				ACC.checkout.b2bIsFlorida($("#cboxLoadedContent #checkoutAddress\\.region").val(),$("#cboxLoadedContent #checkoutAddress\\.region"));
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
		        
	        if(status)	{
			$.ajax({
				url: ACC.config.encodedContextPath + "/checkout/multi/order-type/add-address", 
        		method:"POST",
        		data:$('#cboxLoadedContent #siteOneAddressForm').serialize(),	
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
	                        if(deliveryMode=="shipping")
                               {
                               	 $("#MixedcartShippingAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
                     	 	         
                     	 		     $(".deliveryAddresses").val(response[1]);
                     	 		     $( "#MixedcartShippingAddress" ).change();
                               }
                               else
                               {
                     	  		     $("#MixedcartdeliveryAddress").append( '<option value="'+response[0].id+'" selected>Address: '+response[0].line1+'</option>' );
                     	 	         
                     	 		     $(".deliveryAddresses").val(response[1]);
                     	 		     $( "#MixedcartdeliveryAddress" ).change();
                                 }        
                      				} else {
        					 var region="";
        					 if(response[0].region && response[0].region.isocodeShort){
        					    	region= response[0].region.isocodeShort ;
        					    }
        					 var details =  response[0].line1 + "<BR>" 
       	      			  				+ response[0].town + ", "
       	      			  				+ region + " " + response[0].postalCode + "<BR>";
     
        					 ACC.mixedcartcheckout.populateRegionDetails(region,deliveryMode);
                              if(deliveryMode=="shipping")
                               {
	                            $('#MixedcartShippingAddress option')[0].selected = true;
        					 $(".shipping-address").html(details);
        					$('#shippingAddressId').val(response[0].id);
                                }
                                else
                                {
                                	$('#MixedcartdeliveryAddress option')[0].selected = true;
        				    	 $(".delivery-address").html(details);
        					    $('#deliveryAddressId').val(response[0].id);
                                }
        						var contact = $('#contactId').val();
        						
        						ACC.mixedcartcheckout.populateContactAddress(contact,deliveryMode);
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
	       }
		});
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
                    	 ACC.mixedcartcheckout.bindAddPhoneSuccess();
                    	 ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#phoneNo', '#errorPhoneNo', '#phoneNo\\.errors');
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
				loading.stop();
				var contactNumber = $("#user\\.phonenumber").val();
				$(".checkout_addPhoneNumber").replaceWith(data);				
				ACC.colorbox.resize();
				if ($(data).hasClass("addPhoneNumberSucess"))
				{
					ACC.checkout.bindPopulatePhoneNumber(contactNumber,$("#contactId").val());			
				}				
				ACC.mixedcartcheckout.bindAddPhoneSuccess();
			}
		});		 
	},
	addNewAddressCheckout: function()
    {
    	
    	$(document).on("click", ".newaddress-mixedcart", function(e)
    	{
    		e.preventDefault();
    		var ordertype = $(this).attr("data-mode");
	    	ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#deliveryAddressRadio', '#errorDeliveryAddressRadio', '#deliveryAddressRadio\\.errors');
	    	$("#MixedcartdeliveryAddress").removeClass("error-div");
           $("#MixedcartShippingAddress").removeClass("error-div");
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.companyName', '.errorCompanyName', '#companyName\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.line1', '.errorline1', '#line1\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.region', '.errorRegion', '#region\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.townCity', '.errorTownCity', '#townCity\\.errors');
	    	ACC.formvalidation.removeErrorClassForRegForm('#cboxLoadedContent #checkoutAddress\\.postcode', '.errorPostCode', '#postcode\\.errors');
	    	$("#colorbox").addClass("addAddress-checkout");
	    	$("#colorbox").removeClass("failedgeoLocation-box");
	    	if(ordertype == "shipping"){
	    		ACC.colorbox.open("", {
                    html: $("#addresscheckout").html(),
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
	                    html: $("#addresscheckout").html(),
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
	    	$(".mixedcart-addAddressFormSubmit").attr("data-mode", ordertype);
	    	$("#checkoutAddress\\.region").attr("data-mode", ordertype);
	    	$("#cboxLoadedContent").addClass("mixedcart-popup");
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
    	$(document).on('input blur', ".mixedcart-popup #checkoutAddress\\.region", function(e)
    	{
    		if($(this).attr("data-mode") === "shipping"){
				    ACC.checkout.b2bIsFlorida($(this).val(), e.target);
			}
			else{
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
	showAddressForm: function() {
		$(".mixedcart-shipping-check").attr("checked", false);
		$('.shipping-mixedcart-form').show();
		$(".mixedcartShippingBoxGC").hide();
		$("#shipping_div").hide();
		$("#shipping_btn").hide();
	},
	saveContacForm: function(){
		$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/saveContactDetails", 
    		method:"POST",
    		data:$('#SiteOneGCContactForm').serialize(),	
    		
    		success: function(response)
    		{
    			window.scrollTo(0,0);
    			
    			if(response.customerData || response.addressData){
    			var orderType;
    			let modes = ["PICKUP","DELIVERY","SHIPPING"];
    			for(let i=0; i<modes.length; i++){
					if($('[data-aconum="1"]').parents("." + modes[i] + "-section").length){
						orderType = modes[i];
					}
				}
    			
    			$(".contactcheckout").hide();
    			
    			$(".edit-contact-information").show();
    			$(".edit-contact-information_guest").show();
    			
    			$(".checkout-form-data").show();
    			
    			$("#siteOneOrderTypeForm").show();
    		
    			var firstname = response.customerData.firstName;
    			$("#mixedcart-data").html(firstname);
    			
    			var lastname = response.customerData.lastName;
    			$("#mixedcart-lastNamedata").html(lastname);
    			
    			var phonedata = response.customerData.contactNumber;
    			$("#mixedcart-phonedata").html(phonedata);
    			
    			var emaildata = response.customerData.displayUid;
    			$("#mixedcart-emaildata").html(emaildata);
    			
    			var addressdata = response.addressData.line1;
    			$("#mixedcart-addressdata").html(addressdata);
    			
    			var addressdata1 = response.addressData.line2;
    			$("#mixedcart-addressdat1").html(addressdata1);
    			
    			var citydata = response.addressData.town;
    			$("#mixedcart-citydata").html(citydata);
    			
    			var statedata = response.addressData.district;
    			$("#mixedcart-statedata").html(statedata);
				$("#delivery_location").val(statedata);
    			
    			var zipdata = response.addressData.postalCode;
    			$("#mixedcart-zipdata").html(zipdata);
    			
    			if(orderType == "PICKUP"){
    				$(".delivery--content").hide();
		        	$(".pickup--content").show();
		        	$(".common-content-pickupDelivery").show();
		        	$(".delivery-chargesMsg").hide();
		        	$(".icon-delivery-charges").hide();
		        	$(".message-delivery").hide();
		            $(".errorPickupDelivery").hide();
		            $(".delivery-details").hide();
		            $(".pickup-title").show();
    			}else if(orderType == "DELIVERY"){
    				var isSameAsContactInfo = response.isSameAsContactInfo;
    				if(isSameAsContactInfo){
    					$(".mixedcart-delivery-check").attr("checked", true);
    					$('.deliveryContact-form').hide();
						$(".mixedcartDeliveryBoxGC").show();
    					 $("#termsPODiv").show();
        $("#delivery_content1").show();
    			    	  $("#delivery_content2").show();
    			    	  $("#pickup-delivery").show();
           $(".payment_cbtn").show();
    			    	  $("#mixedcart-deliveryaddressdata").html(response.addressData.line1);
			        		$("#mixedcart-deliveryaddressdat1").html(response.addressData.line2);
			        		$("#mixedcart-deliverycitydata").html(response.addressData.town);
			        		$("#mixedcart-deliverystatedata").html(response.addressData.district);
							    $("#delivery_location").val(response.addressData.district);
			        		$("#mixedcart-deliveryzipdata").html(response.addressData.postalCode);
    				}else{
    					$(".mixedcart-delivery-check").attr("checked", false);
    					//$('.deliveryContact-form').show();
						$(".mixedcartDeliveryBoxGC").hide();
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
    				if(isSameAsContactInfo){
    					if(state != _AAData.storeState){
        					$(".mixedcart-shipping-check").attr("disabled", true);
        					ACC.mixedcartcheckout.showAddressForm();
			        		$(".mixedcart-florida-error-msg").removeClass("hidden");
			        		//$(".mixedcart-shipping-check").css("pointer-events","none");
        				}else{
        					$(".mixedcart-shipping-check").attr("disabled", false);
        					$(".mixedcart-shipping-check").attr("checked", true);
    					 	$('.shipping-mixedcart-form').hide();
							$(".mixedcartShippingBoxGC").show();
    					 	$("#shipping_div").show();
		        			$("#shipping_btn").show();
		        			$(".mixedcart-florida-error-msg").addClass("hidden");
		        			$("#mixedcart-shippingaddressdata").html(response.addressData.line1);
			        		$("#mixedcart-shippingaddressdat1").html(response.addressData.line2);
			        		$("#mixedcart-shippingcitydata").html(response.addressData.town);
			        		$("#mixedcart-shippingstatedata").html(response.addressData.district);
							$("#delivery_location").val(response.addressData.district);
			        		$("#mixedcart-shippingzipdata").html(response.addressData.postalCode);
        				}
        				
    			 }

			      else{
			    	  if(state != _AAData.storeState){
        					$(".mixedcart-shipping-check").attr("disabled", true);
        					ACC.mixedcartcheckout.showAddressForm();
			        		$(".mixedcart-florida-error-msg").removeClass("hidden");
        				}else{
        					$(".mixedcart-shipping-check").attr("disabled", false);
        					ACC.mixedcartcheckout.showAddressForm();
			        		$(".mixedcart-florida-error-msg").addClass("hidden");
        				}
        				
			    }
    				
    				
    			}
    			$(".direction-link").show();
    			$(".edit-pickup-information").hide();
    		    
    		   //for guest user next fulfillment checking
				if($(".isguestuser").val() == "true"){
					ACC.mixedcartcheckout.openNextBlockAccordion('mixedcart-contact', 0);
					$(".mixedcart-delivery-check").prop("checked", $(".mixedcart-sameaddress-delivery").prop("checked"));
					
					if(!$(".mixedcart-shipping-check").prop("disabled")){
						$(".mixedcart-shipping-check").prop("checked", $(".mixedcart-sameaddress-shipping").prop("checked"));
					}
					
					if($(".mixedcart-delivery-check").prop("checked")){
						ACC.mixedcartcheckout.copySameDataFor('delivery', firstname,lastname,phonedata,emaildata,addressdata,addressdata1,citydata,statedata,zipdata);
						$(".deliveryContact-form").hide();
					}
					else{
						$(".deliveryContact-form").show();
					}
					
					if($(".mixedcart-shipping-check").prop("checked")){
						ACC.mixedcartcheckout.copySameDataFor('shipping', firstname,lastname,phonedata,emaildata,addressdata,addressdata1,citydata,statedata,zipdata);
						$(".shipping-mixedcart-form").hide();
					}
					else{
						$(".shipping-mixedcart-form").show();
					}
				}
    		
    			}
    		} //./success
		})
	},
	checkoutStepEmailValidation: function(){
					
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
						$(".addContactFormSubmitMixCart").prop('disabled', true);
						}
						else{
						$(".exitEmail-guestCheckout-form").hide();
						$(".exitEmail-guestCheckout-formerror").hide();
						$(".addContactFormSubmitMixCart").prop('disabled', false);
						}
						}else {
						$(".exitEmail-guestCheckout-formerror").show();
						$(".exitEmail-guestCheckout-form").hide();
						$(".addContactFormSubmitMixCart").prop('disabled', true);
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
		
		$(".edit-mixedcart-contact-information").click(function() {
			
			grecaptcha.reset();
				$("#iframe_Popup_guest").slideUp();
			$(".checkout-form-data").hide();
			$(".mixCartChangeContact").removeClass("hidden").show();
			if($(".contactPersonfirstName").length){
			$(".edit-contact-information_guest").hide();
			var firstname=$(".contactPersonfirstName").val();
			$("#checkoutContactfirstName").val(firstname);
			var lastname=$(".contactPersonlastName").val();
			$("#checkoutContactlastName").val(lastname);
			var company=$(".contactPersoncompanyName").val();
				$("#checkoutContact\\.companyName").val(company);
			var number=$(".contactPersoncontactNumber").val();
			$("#checkoutContactphone").val(number);
			var email=$(".contactPersondisplayUid").val();
			$("#checkoutContactemail").val(email);
			var line1=$(".addressDataline1").val();
			$("#checkoutContactaddressLine1").val(line1);
			var line2=$(".addressDataline2").val();
			$("#checkoutContact\\.addressLine2").val(line2);
			var town=$(".addressDatatown").val();
			$("#checkoutContactcity").val(town);
			var district=$(".addressDatadistrict").val();
			$("#checkoutContactstate").val(district);
			var postalcode=$(".addressDatapostalCode").val();
			$("#checkoutContactzip").val(postalcode);
		}
			ACC.mixedcartcheckout.closeOpenBloc("mixedcart-contact");
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
						ACC.mixedcartcheckout.checkoutStepEmailValidation();
						ACC.formvalidation.removeErrorClassForRegForm('#checkoutContactemail', '#errorEmailAddress', '#checkoutContactemail\\.errors', ERRORMSG.invalid.email);
					}
					else {
						$(".exitEmail-guestCheckout-form").hide();
						$(".exitEmail-guestCheckout-formerror").hide();
						$(".addContactFormSubmitMixCart").prop('disabled', false);
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
				
				
				$(document).on("click", ".addContactFormSubmitMixCart", function(e)
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
									        				ACC.mixedcartcheckout.saveContacForm();
									        			}
									        			else
									        			{		        				        			
										        			var popupContent = ACC.mixedcartcheckout.createPopupContent(addressVerificationData);
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
							 ACC.mixedcartcheckout.saveContacForm();
						 }
					 }
						
						});
	},
	bindEditFulFilmentDetails: function() {
		$(document).on("change", "#checkoutShippingstate", function(e){
			var msg = ERRORMSG.global.address;
			var stateMsg = ERRORMSG.global.shippingStates;
			ACC.mixedcartcheckout.checkIfFlorida('#checkoutShippingstate', '#errorShippingState', '#checkoutShippingstate\\.errors', msg, stateMsg);
		});
		
		
		
		$(document).on("click", ".mixedcart-addShippingFormSubmit", function(e)
		{
			e.preventDefault();
			var status = true;
			var guestmsg = ERRORMSG.global.address;
			ACC.formvalidation.vaildateFields('#checkoutShippingfirstName','#errorShippingFirstName', '#checkoutShippingfirstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
			ACC.formvalidation.vaildateFields('#checkoutShippinglastName','#errorShippingLastName', '#checkoutShippinglastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);
			
	        ACC.formvalidation.vaildateFields('#checkoutShippingaddressLine1', '#errorShippingAddressLine1', '#checkoutShippingaddressLine1\\.errors', guestmsg);
			ACC.mixedcartcheckout.checkIfFlorida('#checkoutShippingstate', '#errorShippingState', '#checkoutShippingstate\\.errors', guestmsg, ERRORMSG.global.shippingStates);				
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
				 	ACC.mixedcartcheckout.briteVerifyEmailvalidation(shippingEmail , status, "PARCEL_SHIPPING");
					ACC.checkout.showProp65Popup();
			 }
						
			
	});
		
		$(document).on("click", "#mixedCartEditShipping", function(e){
			$("#parcelShipping_div").show();
			$("#iframe_Popup_guest").hide();
			 $(".choosepaymentB2b_GC").hide();
			var isGuestUser = $(".isguestuser").val();
			var state = $("#mixedcart-statedata").html();
			if(isGuestUser == "true"){
				if($(".mixedcart-shipping-check").prop('checked')==true){
					if(state != _AAData.storeState || state != 'US-'+_AAData.storeState){
    					$(".mixedcart-shipping-check").attr("disabled", true);
    					$(".mixedcart-shipping-check").attr("checked", false);
    					$('.shipping-mixedcart-form').show();
						$(".mixedcartShippingBoxGC").hide();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
    				}else{
    					$(".mixedcart-shipping-check").attr("disabled", false);
    					$(".mixedcart-shipping-check").attr("checked", true);
    					$('.shipping-mixedcart-form').hide();
						$(".mixedcartShippingBoxGC").show();
  					  	$("#shipping_div").show();
  						$("#shipping_btn").show();
  						
    				}
					  
				}
				else{
					if(state != _AAData.storeState || state != 'US-'+_AAData.storeState){
    					$(".mixedcart-shipping-check").attr("disabled", true);
    					$(".mixedcart-shipping-check").attr("checked", false);
    					$('.shipping-mixedcart-form').show();
						$(".mixedcartShippingBoxGC").hide();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
    				}else{
    					$(".mixedcart-shipping-check").attr("disabled", false);
    					$(".mixedcart-shipping-check").attr("checked", false);
    					$('.shipping-mixedcart-form').show();
						$(".mixedcartShippingBoxGC").hide();
				    	 $("#shipping_div").hide();
		        		$("#shipping_btn").hide();
    				}
			      }
				$("#mixedcart-shipping_Details").hide();
				$("#mixedcart-shipment_div2").show();
			}else{
				$("#shipping_div").show();
				$("#mixedcart-shipment_div2").show();
				$("#shipping_btn").show();
				$("#mixedcart-shipping_Details").hide();
			}
			$(".direction-link").show();
			ACC.mixedcartcheckout.closeOpenBloc("shipping");
		});
		
		
		
		$(document).on("click", "#mixedCartEditDelivery", function(e){
			$("#delivery_fee").hide();
			$("#delivery_Details").hide();
			
          
			var isGuestUser = $(".isguestuser").val();
			if(isGuestUser == "true"){
				if($(".mixedcart-delivery-check").prop('checked')==true){
					  $('.deliveryContact-form').hide();
					  $(".mixedcartDeliveryBoxGC").show();
					  $("#delivery_content1").show();
					  $("#termsPODiv").show();
			    	  $("#delivery_content2").show();
			    	  $("#pickup-delivery").show();
			    	  $(".payment_cbtn").show();
			    		$("#iframe_Popup_guest").hide();
			    		 $(".choosepaymentB2b_GC").hide();
			    		 
			    		 ACC.mixedcartcheckout.deliveryNewAddressShowForm();
				  }
	
			      else{
			    	  $('.deliveryContact-form').show();
					 $(".mixedcartDeliveryBoxGC").hide();
			    	  $("#termsPODiv").hide();
			    	  $("#delivery_content1").show();
			    	  $("#delivery_content2").hide();
			    	  $("#pickup-delivery").hide();
			    	  $(".payment_cbtn").hide();
			    		$("#iframe_Popup_guest").hide();
			    		 $(".choosepaymentB2b_GC").hide();
			    		 ACC.mixedcartcheckout.deliveryNewAddressHideForm();
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
			 
			ACC.mixedcartcheckout.closeOpenBloc("delivery");
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
	},
	
	paymentPOCheck: function () {
		$(document.body).on("click", ".js-ponumber-continue", function () {

			var poNumber = $(".js-payment-po-number").val();
			var currentElement = $(this)
			if (poNumber) {
				$.ajax({
					url:ACC.config.encodedContextPath + "/checkout/multi/order-type/poNumber?poNumber=" + poNumber,
					method: "GET",
					success: function (response) {
						if ($(currentElement).data("isporequired") === true) {
							if (response == true) {
								$(".js-mixed-cart-payment-section").removeClass("hidden")
								$(".js-po-error-msg").addClass("hidden")
								$(".js-payment-po-number").removeClass("po-error").prop("disabled",true)
								$(currentElement).addClass("hidden")
							}
							else {
								$(".js-mixed-cart-payment-section").addClass("hidden");
								$(".js-po-error-msg").removeClass("hidden");
								$(".js-payment-po-number").addClass("po-error").prop("disabled",false)
								$(currentElement).removeClass("hidden")
							}
						}

					}

				});
			}
			else {
				$(".js-mixed-cart-payment-section").addClass("hidden");
				$(".js-po-error-msg").removeClass("hidden");
				$(".js-payment-po-number").addClass("po-error").prop("disabled",false)
				$(currentElement).removeClass("hidden")
			}
			
		})
		$(document.body).on("input", ".js-payment-po-number", function () {
			$(".js-po-error-msg").addClass("hidden")
			$(".js-payment-po-number").removeClass("po-error");			
		});
		$(document.body).on("blur", ".js-optional-po-txt-box", function () {
			var poNumber = $(this).val();
			if (poNumber) {
				$.ajax({
					url: ACC.config.encodedContextPath + "/checkout/multi/order-type/poNumber?poNumber=" + poNumber,
					method: "GET",
					success: function (response) {
						$(".js-payment-po-number").removeClass("po-error").prop("disabled",true)
					}
				})
			}
		})
		
	},
	checkoutAccordian: function(checkAco, defaultAco)
    {
		let firstBox;
		let counter=0;
		let guestUser = $(".isguestuser").val();
		let contactFilled = $("#mixedcart-data").html();
		if(guestUser == "true" && contactFilled == ""){ //guest user without address
			firstBox = true;
			$(".mixCartChangeContact").show();
		}
		else if(guestUser == "true" && contactFilled != ""){ //guest user with address
			$(".edit-mixedcart-contact-information, .saved-mixedcart-contact-details").show();
		}
		 if($(".page-siteOneCheckoutPage").length > 0){
		 	try{
					 _satellite.track('contactDetails');
					} catch (e) {}
		}
		for (let i=0; i<checkAco.length; i++){
			let target = $("." + checkAco[i]);
			var type = checkAco[i].split("-")[0];
			
			if(target.length){
				$(".addAddressFormSubmit").addClass("mixedcart-addAddressFormSubmit").removeClass("addAddressFormSubmit");
				counter++;
				let targetData = $("." + type + "-section");
				if(!firstBox){
					for (let j=0; j<defaultAco.length; j++){
						if(type == defaultAco[j]){
							if((type == "delivery" || type == "shipping") && $("." + type + "-region").html() == ""){ //updating saved data for closed accordion
								$("." + type + "-address").html($("#mixedcart-" + type + "addressdata").html()+"<br>"+$("#mixedcart-" + type + "addressdat1").html()+"<br>"+$("#mixedcart-" + type + "citydata").html()+", "+$("#mixedcart-" + type + "statedata").html() +" " + $("#mixedcart-" + type + "zipdata").html());
								$("." + type + "-region").html($("#mixedcart-" + type + "statedata").html());
							}
							targetData.removeClass("hidden");
							firstBox = true;
							
							if(type=="pickup"){
								 try{
									 _satellite.track('branchPickup');
									 } catch (e) {}
								}
							else if(type=="delivery"){
								 try{
									 _satellite.track('localDelivery');
									 } catch (e) {}
							}
			
							else if(type=="shipping"){
								 try{
									 _satellite.track('shippingDetails');
									 } catch (e) {}
							}
							
							break;
						}
					}
				}
				else{
					targetData.hide().removeClass("hidden");
				}
				targetData.find(".submit-ordertype-data").attr({"data-type": type, "data-aconum":  counter});
				$(".mixedcart-sameaddress-" + type).attr("data-mode", type).on("change", ACC.mixedcartcheckout.isSameAddress);
			}
			else{
				$(".mixedcart-sameaddress-" + type).parents(".mixedcart-sameaddress").hide();
			}
		}
	},
	closeOpenBloc: function(target){ //on edit button open current block
		let modes = ["mixedcart-contact","pickup","delivery","shipping"];
		for(let i=0; i<modes.length; i++){
			if(modes[i] != target && $("."+ modes[i] + "-section").length && $("."+ modes[i] + "-section").css("display") == "block"){
				$("."+ modes[i] + "-box").find(".saved-"+ modes[i] +"-details, .edit-"+ modes[i] +"-information").show();
				$("."+ modes[i] + "-box").find("." + modes[i] +"_fee").hide();
				$("."+modes[i] + "-section").hide();
			}
			if(target == "mixedcart-contact"){
				$("."+ modes[i] + "-box").find(".edit-"+ modes[i] +"-information").hide();
			}
			
		}
		//Opening New Block
		$(".mixedcart-checkout").children("#siteOneOrderTypeForm").show();
		$("."+ target + "-box").find(".saved-"+ target +"-details, .edit-"+ target +"-information").hide();
		$("."+ target + "-box").find("." + target +"_fee").show();
		$("." + target + "-section").slideDown(function(){
			$(window).scrollTop($("." + target + "-box").offset().top);
			if($(".mixedcart-" + target + "-check").prop("disabled")){
				$(".mixedcart-" + target + "-check").prop("checked", false);
				$("."+ target + "-mixedcart-form").show();
			}
		});
		if(target=="mixedcart-contact"){
			 try{
				 _satellite.track('contactDetails');
				 } catch (e) {}
			}
		else if(target=="pickup"){
			 try{
				 _satellite.track('branchPickup');
				 } catch (e) {}
			}
				else if(target=="delivery"){
					 try{
						 _satellite.track('localDelivery');
						 } catch (e) {}
				}

			else if(target=="shipping"){
				 try{
					 _satellite.track('shippingDetails');
					 } catch (e) {}
			}
		
	},
	openNextBlockAccordion: function(type, num, paymentBlock){ //on continue open next block
		let NextBtn = $("[data-aconum="+ (num+1) +"]");
		let modes = ["pickup","delivery","shipping"];
		//closing current Block
		if((type == "delivery" || type == "shipping")){ //updating saved data for closed accordion
			if($(".mixedcart-" + type + "-check").prop("checked")){
				$("." + type + "-address").html($("#mixedcart-" + type + "addressdata").html()+"<br>"+$("#mixedcart-" + type + "addressdat1").html()+"<br>"+$("#mixedcart-" + type + "citydata").html().split(",")[0] + ", " + $("#mixedcart-" + type + "statedata").html() +" " + $("#mixedcart-" + type + "zipdata").html());
			}
			else{
				$("." + type + "-address").html($("." + type + "-Newaddress").html());
			}
		}
		$(".saved-" + type + "-details").show();
		$(".edit-" + type + "-information").show();
		$("." + type +"_fee").hide();
		$("." + type +"-section").hide();
		
		if(NextBtn.length){
			for(let i=0; i<modes.length; i++){
				if(NextBtn.parents("." + modes[i] + "-section").length){ //opening new Block
					$(".saved-" + modes[i] + "-details").hide();
					$(".edit-" + modes[i] + "-information").hide();
					$("." + modes[i] +"_fee").show();
					if($(".isguestuser").val() == "true"){
						if(modes[i] == "shipping" && $(".mixedcart-"+ modes[i] +"-check").prop("checked") && $("#mixedcart-"+ modes[i] +"statedata").html() != _AAData.storeState){
							$(".mixedcart-"+ modes[i] +"-check").prop("checked", false)
						}
						$(".mixedcart-"+ modes[i] +"-check").trigger("change")
					}
					$("." + modes[i] + "-section").slideDown(function(){
						$(window).scrollTop($("." + modes[i] + "-box").offset().top);
					});
					
					if(modes[i]=="pickup"){
							 try{
								 _satellite.track('branchpickup');
								 } catch (e) {}
						}
					else if(modes[i]=="delivery"){
							 try{
								 _satellite.track('localDelivery');
								 } catch (e) {}
						}
					else if(modes[i]=="shipping"){
								 try{
									 _satellite.track('shippingDetails');
									 } catch (e) {}
							}
					break;
				}
			}
			$("#iframe_Popup_guest").hide();
		}
		else{
			$(paymentBlock).css({overflow: (paymentBlock == ".choosepaymentB2b_GC")?"visible": "hidden"}).slideDown(function(){
				$(window).scrollTop($(paymentBlock).offset().top-60);
				try {
					 _satellite.track("paymentDetails");
					} catch (e) {}
			});
		}
		
	},
	copySameDataFor: function(type, firstname,lastname,phonedata,emaildata,addressdata,addressdata1,citydata,statedata,zipdata ){
		//saved Data section
		$("." + type + "-address").html(addressdata+"<br>"+addressdata1+"<br>"+citydata+", "+statedata +" " + zipdata);
		$("." + type + "-contact-details").html(firstname+" "+lastname+"<br>"+phonedata+"<br>"+emaildata);
		
		//same address data section
		$("#mixedcart-" + type + "addressdata").html(addressdata);
		$("#mixedcart-" + type + "addressdat1").html(addressdata1);
		$("#mixedcart-" + type + "citydata").html(citydata + ", ");
		$("#mixedcart-" + type + "statedata").html(statedata);
		$("#mixedcart-" + type + "zipdata").html(zipdata);
	},
	isSameAddress: function(){
		let ref = $(this);
		let mode = ref.attr("data-mode");

		if($(".mixedcart-contact-section").css("display") == "block"){
			if(!$(".mixedcart-" +mode+ "-check").prop("disabled")){
				$(".mixedcart-" +mode+ "-check").prop("checked", ref.prop("checked"));
			}
		}
	},
	transportKeyGCGenerateMxedCart: function (){
	$.ajax({
			url: ACC.config.encodedContextPath + "/checkout/multi/siteone-payment/getBoardCardTransportKey?isCheckout="+true,
			method: "POST",
	
			success: function (transportKey) {
	
				if (transportKey != null) {
					loading.stop();
					var redirecturl = 'https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey=' + transportKey;
			
					$(".Pop-up-myIframe ").attr('src', redirecturl );
				}
			}
		});
}, 
createDeliveryPopupContentMixedCart: function(addressVerificationData){
	
	var popupContent = "";	
	
	if(addressVerificationData.isAddressCorrected){
		popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#mixedcart-deliveryContactForm #checkoutdeliveryaddressLine1").val() +"</span><div class='cl'></div>"
		  
		if($("#mixedcart-deliveryContactForm #checkoutdelivery\\.addressLine2").val() != ""){
			popupContent = popupContent +  $("#mixedcart-deliveryContactForm #checkoutdelivery\\.addressLine2").val() +"<div class='cl'></div>";
		  }
		
		   popupContent = popupContent + $("#mixedcart-deliveryContactForm #checkoutdeliverycity").val() +"<div class='cl'></div>"+ $("#mixedcart-deliveryContactForm #checkoutdeliverystate option:selected").text() +"<div class='cl'></div>"+ $("#mixedcart-deliveryContactForm #checkoutdeliveryzip").val() +" </div></div>";
		
		popupContent = popupContent+"<div class='col-md-6 col-xs-12 margin-top-add'><span class='hidden-md hidden-lg'><br/><br/></span><span class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.suggestedAddress+"</b></span><div class='cl'></div><p></p>";
		
			popupContent = popupContent+"<div class='add-address-sec'><span id='suggestedAddress.line1'  data-line1='"+addressVerificationData.street+"'>"+ addressVerificationData.street +"</span>";
		
			popupContent = popupContent+"<span id='suggestedAddress.line2' data-line2='"+addressVerificationData.street2+"'>"+ addressVerificationData.street2+"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.city' data-city='"+addressVerificationData.city+"'>"+ addressVerificationData.city +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.county' data-county='"+addressVerificationData.county+"'>"+ addressVerificationData.county +"</span> <div class='cl'></div>";
			
			popupContent = popupContent+"<span id='suggestedAddress.state'  data-state='"+addressVerificationData.state+"'>"+ addressVerificationData.state +"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.zipcode'  data-zipcode='"+addressVerificationData.zipcode+"'>"+ addressVerificationData.zipcode +"</span>";	        						
        					
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidDeliveryAddressSubmitMixedCart'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";		        				    
			
	}else if(!addressVerificationData.isAddressValid){
		popupContent = "<p style='margin-top:-18px;'>"+ACC.config.veryfyAddressError+"</p> <br> <div class='row'><div class='col-md-6 col-xs-12  col-sm-12'><input type='button' value='Proceed Anyway'  class='btn btn-primary btn-block' id='proceedDeliveryOriginalAddressMixedCart'/></div> <span class='hidden-xs hidden-md hidden-lg visible-sm'><div class='cl'></div><br/></span><div class='col-md-6 col-xs-12 col-sm-12'><input type='button' value='Edit Address' class='btn btn-default btn-block' id='editOriginalAddress'/></div></div>";
		
	}
	
return popupContent;
},
copySuggestedValidDeliveryAddressToForm: function()
{
	$("#mixedcart-deliveryContactForm #checkoutdeliveryaddressLine1").val($("#suggestedAddress\\.line1").data("line1"));
    $("#mixedcart-deliveryContactForm #checkoutdelivery\\.addressLine2").val($("#suggestedAddress\\.line2").data("line2"));
    $("#mixedcart-deliveryContactForm #checkoutdeliverycity").val($("#suggestedAddress\\.city").data("city"));
    $("#mixedcart-deliveryContactForm #checkoutdeliverystate").val($("#suggestedAddress\\.state").data("state"));
    $("#mixedcart-deliveryContactForm #checkoutdeliveryzip").val($("#suggestedAddress\\.zipcode").data("zipcode"));
},
createShippingPopupContentMixedCart: function(addressVerificationData){
	
	var popupContent = "";	
	
	if(addressVerificationData.isAddressCorrected){
		popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#mixedcart-shipmentForm #checkoutShippingaddressLine1").val() +"</span><div class='cl'></div>"
		  
		if($("#mixedcart-shipmentForm #checkoutShipping\\.addressLine2").val() != ""){
			popupContent = popupContent +  $("#mixedcart-shipmentForm #checkoutShipping\\.addressLine2").val() +"<div class='cl'></div>";
		  }
		
		   popupContent = popupContent + $("#mixedcart-shipmentForm #checkoutShippingcity").val() +"<div class='cl'></div>"+ $("#mixedcart-shipmentForm #checkoutShippingstate option:selected").text() +"<div class='cl'></div>"+ $("#mixedcart-shipmentForm #checkoutShippingzip").val() +" </div></div>";
		
		popupContent = popupContent+"<div class='col-md-6 col-xs-12 margin-top-add'><span class='hidden-md hidden-lg'><br/><br/></span><span class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.suggestedAddress+"</b></span><div class='cl'></div><p></p>";
		
			popupContent = popupContent+"<div class='add-address-sec'><span id='suggestedAddress.line1'  data-line1='"+addressVerificationData.street+"'>"+ addressVerificationData.street +"</span>";
		
			popupContent = popupContent+"<span id='suggestedAddress.line2' data-line2='"+addressVerificationData.street2+"'>"+ addressVerificationData.street2+"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.city' data-city='"+addressVerificationData.city+"'>"+ addressVerificationData.city +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.county' data-county='"+addressVerificationData.county+"'>"+ addressVerificationData.county +"</span> <div class='cl'></div>";
			
			popupContent = popupContent+"<span id='suggestedAddress.state'  data-state='"+addressVerificationData.state+"'>"+ addressVerificationData.state +"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.zipcode'  data-zipcode='"+addressVerificationData.zipcode+"'>"+ addressVerificationData.zipcode +"</span>";	        						
        					
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidShippingAddressSubmitMixedCart'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";		        				    
			
	}else if(!addressVerificationData.isAddressValid){
		popupContent = "<p style='margin-top:-18px;'>"+ACC.config.veryfyAddressError+"</p> <br> <div class='row'><div class='col-md-6 col-xs-12  col-sm-12'><input type='button' value='Proceed Anyway'  class='btn btn-primary btn-block' id='proceedShippingOriginalAddressMixedCart'/></div> <span class='hidden-xs hidden-md hidden-lg visible-sm'><div class='cl'></div><br/></span><div class='col-md-6 col-xs-12 col-sm-12'><input type='button' value='Edit Address' class='btn btn-default btn-block' id='editOriginalAddress'/></div></div>";
		
	}
	
return popupContent;
},
copySuggestedValidShippingAddressToForm: function()
{
	$("#mixedcart-shipmentForm #checkoutShippingaddressLine1").val($("#suggestedAddress\\.line1").data("line1"));
    $("#mixedcart-shipmentForm #checkoutShipping\\.addressLine2").val($("#suggestedAddress\\.line2").data("line2"));
    $("#mixedcart-shipmentForm #checkoutShippingcity").val($("#suggestedAddress\\.city").data("city"));
    $("#mixedcart-shipmentForm #checkoutShippingstate").val($("#suggestedAddress\\.state").data("state"));
    $("#mixedcart-shipmentForm #checkoutShippingzip").val($("#suggestedAddress\\.zipcode").data("zipcode"));
},
createPopupContent: function(addressVerificationData){
	
	var popupContent = "";	
	
	if(addressVerificationData.isAddressCorrected){

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

			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidAddressSubmitMixCart'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";

		} else {

			popupContent ="<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#SiteOneGCContactForm #checkoutContactaddressLine1").val() +"</span><div class='cl'></div>"
		  
			if($("#SiteOneGCContactForm #checkoutContact\\.addressLine2").val() != ""){
				popupContent = popupContent +  $("#SiteOneGCContactForm #checkoutContact\\.addressLine2").val() +"<div class='cl'></div>";
			}
		
		   popupContent = popupContent + $("#SiteOneGCContactForm #checkoutContactcity").val() +"<div class='cl'></div>"+ $("#SiteOneGCContactForm #checkoutContactstate option:selected").text() +"<div class='cl'></div>"+ $("#SiteOneGCContactForm #checkoutContactzip").val() +" </div></div>";
		
			popupContent = popupContent+"<div class='col-md-6 col-xs-12 margin-top-add'><span class='hidden-md hidden-lg'><br/><br/></span><span class='colored-radio'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.suggestedAddress+"</b></span><div class='cl'></div><p></p>";
		
			popupContent = popupContent+"<div class='add-address-sec'><span id='suggestedAddress.line1'  data-line1='"+addressVerificationData.street+"'>"+ addressVerificationData.street +"</span>";
		
			popupContent = popupContent+"<span id='suggestedAddress.line2' data-line2='"+addressVerificationData.street2+"'>"+ addressVerificationData.street2+"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.city' data-city='"+addressVerificationData.city+"'>"+ addressVerificationData.city +"</span> <div class='cl'></div>";

			popupContent = popupContent+"<span id='suggestedAddress.county' data-county='"+addressVerificationData.county+"'>"+ addressVerificationData.county +"</span> <div class='cl'></div>";
			
			popupContent = popupContent+"<span id='suggestedAddress.state'  data-state='"+addressVerificationData.state+"'>"+ addressVerificationData.state +"</span> <div class='cl'></div>";
		
			popupContent = popupContent+"<span id='suggestedAddress.zipcode'  data-zipcode='"+addressVerificationData.zipcode+"'>"+ addressVerificationData.zipcode +"</span>";	        						
        					
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidAddressSubmitMixCart'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";
		}


			
	}else if(!addressVerificationData.isAddressValid){
		popupContent = "<p style='margin-top:-18px;'>"+ACC.config.veryfyAddressError+"</p> <br> <div class='row'><div class='col-md-6 col-xs-12  col-sm-12'><input type='button' value='Proceed Anyway'  class='btn btn-primary btn-block' id='proceedOriginalAddressMixedCart'/></div> <span class='hidden-xs hidden-md hidden-lg visible-sm'><div class='cl'></div><br/></span><div class='col-md-6 col-xs-12 col-sm-12'><input type='button' value='Edit Address' class='btn btn-default btn-block' id='editOriginalAddress'/></div></div>";
		
	}
	
		return popupContent;
	},
	yourItemsScroll: function(){
		let target = $(".mixedcart-ordersummary");
		let itemHeight = target.eq(0).height();
		let isIOS = ACC.mixedcartcheckout.iOS();
		itemHeight = (itemHeight == 0)? target.eq(1).height() : itemHeight;
		if(itemHeight > 1200 || itemHeight == 0){
			target.each(function(){
				$(this).addClass((!isIOS)? "notIOS" : "").parent().addClass("scroll-r");
			})
		}
	},
	iOS: function() {
	  return [
	    'iPad Simulator',
	    'iPhone Simulator',
	    'iPod Simulator',
	    'iPad',
	    'iPhone',
	    'iPod'
	  ].includes(navigator.platform)
	  // iPad on iOS 13 detection
	  || (navigator.userAgent.includes("Mac") && "ontouchend" in document)
	}
};   
$( document ).ready(function() {
	
	ACC.mixedcartcheckout.checkoutStepEmailValidation();
	
    $(document.body).on("click",".delivery-mode",function()
		{
			ACC.mixedcartcheckout.bindOrderType();
			ACC.mixedcartcheckout.removeErrorClassForCheckoutForm('#orderTypeRadio', '#errorOrderTypeRadio', '#orderTypeRadio\\.errors')
			
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
				$(".mixedcart-checkout #siteOneOrderTypeForm").show();
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
	

	if($("body").hasClass("page-orderConfirmationPage")){
		sessionStorage.removeItem("IsProp65Shown");
	}
	$('input[name=requestedMeridian]').click(function () {
        $('input[name=requestedMeridian]:not(:checked)').closest(".radio-wrapper").removeClass("selected-time");
        $('input[name=requestedMeridian]:checked').closest(".radio-wrapper").addClass("selected-time");
    });
	
	$("#iframe_Popup_guest").hide();
	 $(".choosepaymentB2b_GC").hide();
	$('.mixedcart-shipping-check').change(function() {
		$(".mixedcart-sameaddress-shipping").prop("checked", this.checked);
		$(".same-shipping-state-error").addClass("hidden");
		  if(this.checked){
			  $('.shipping-mixedcart-form').hide();
			$(".mixedcartShippingBoxGC").show();
			  $("#shipping_div").show();
  			$("#shipping_btn").show();
  			$(".mixedcart-shipping-Newaddress").hide();
	    	  $(".mixedcart-shipping-Newregion").hide();
	    	  $("#mixedcart-shippingaddressdata").show();
	    	  $("#mixedcart-shippingaddressdat1").show();
	    	  $("#mixedcart-shippingcitydata").show();
	    	  $("#mixedcart-shippingstatedata").show();
	    	  $("#mixedcart-shippingzipdata").show();
	    	  let firstname = $("#mixedcart-data").html();
	    	  let lastname = $("#mixedcart-lastNamedata").html();
	    	  let phonedata = $("#mixedcart-phonedata").html();
	    	  let emaildata = $("#mixedcart-emaildata").html();
	    	  let addressdata = $("#mixedcart-addressdata").html();
	    	  let addressdata1 = $("#mixedcart-addressdat1").html();
	    	  let citydata = $("#mixedcart-citydata").html();
	    	  let statedata = $("#mixedcart-statedata").html();
	    	  let zipdata = $("#mixedcart-zipdata").html();
	    	  
	    	  $(".shipping-address").html(addressdata+"<br>"+addressdata1+"<br>"+citydata+", "+statedata +" " + zipdata);
				$(".shipping-contact-details").html(firstname+" "+lastname+"<br>"+phonedata+"<br>"+emaildata);
		  
		  }

	      else{
	    	  $('.shipping-mixedcart-form').show();
			$(".mixedcartShippingBoxGC").hide();
	    	  $("#shipping_div").hide();
  			$("#shipping_btn").hide();
  			$(".mixedcart-shipping-Newaddress").show();
	    	 $(".mixedcart-shipping-Newregion").show();
	    	  $("#mixedcart-shippingaddressdata").hide();
	    	  $("#mixedcart-shippingaddressdat1").hide();
	    	  $("#mixedcart-shippingcitydata").hide();
	    	  $("#mixedcart-shippingstatedata").hide();
	    	  $("#mixedcart-shippingzipdata").hide();
	      }

		 });
	
	$('.mixedcart-delivery-check').change(function() {
		$(".mixedcart-sameaddress-delivery").prop("checked", this.checked)
		  if(this.checked){
			  $('.deliveryContact-form').hide();
			  $(".mixedcartDeliveryBoxGC").show();
			  $("#termsPODiv").show();
	    	  $("#delivery_content2").show();
	    	  $("#pickup-delivery").show();
	    	  $(".payment_cbtn").show();
	    	  ACC.mixedcartcheckout.deliveryNewAddressShowForm();
	    	  let firstname = $("#mixedcart-data").html();
	    	  let lastname = $("#mixedcart-lastNamedata").html();
	    	  let phonedata = $("#mixedcart-phonedata").html();
	    	  let emaildata = $("#mixedcart-emaildata").html();
	    	  let addressdata = $("#mixedcart-addressdata").html();
	    	  let addressdata1 = $("#mixedcart-addressdat1").html();
	    	  let citydata = $("#mixedcart-citydata").html();
	    	  let statedata = $("#mixedcart-statedata").html();
	    	  let zipdata = $("#mixedcart-zipdata").html();
	    	  
	    	  $(".delivery-address").html(addressdata+"<br>"+addressdata1+"<br>"+citydata+", "+statedata +" " + zipdata);
				$(".delivery-contact-details").html(firstname+" "+lastname+"<br>"+phonedata+"<br>"+emaildata);
	    	  

		  }else{
	    	  $('.deliveryContact-form').show();
			  $(".mixedcartDeliveryBoxGC").hide();
	    	  $("#termsPODiv").hide();
	    	  $("#delivery_content2").hide();
	    	  $("#pickup-delivery").hide();
	    	  ACC.mixedcartcheckout.deliveryNewAddressHideForm();
	      }

		 });
		 
		 $(document).on("click", "#suggestedValidAddressSubmitMixCart", function()
		{			
			var addressOption = $("input[name='address\\.addressSuggestionOption']:checked").val();
		 	try{
					 _satellite.track('contactDetails');
					} catch (e) {}
			if(addressOption == "originalAddress")
			{
				if($(".pagename").val() == "Add Edit Address Page"){
					ACC.colorbox.close();
					$("#siteOneAddressForm" ).submit();
				}else{
					ACC.colorbox.close();
					ACC.mixedcartcheckout.saveContacForm();
				}

			}
			
            if(addressOption == "suggestedAddress")
            {
            	if($(".pagename").val() == "Add Edit Address Page"){
					copySuggestedValidAddressToForm();
					ACC.colorbox.close();
				} else {
					copySuggestedValidAddressToForm();
					ACC.colorbox.close();
					ACC.mixedcartcheckout.saveContacForm();
				}

			}
		});
$(document).on("click", "#proceedDeliveryOriginalAddressMixedCart", function()
		{							
			ACC.mixedcartcheckout.saveAlternateDeliveryContactForm();
			ACC.colorbox.close();
	
		});

$(document).on("click", "#proceedOriginalAddressMixedCart", function()
		{							
			if($(".pagename").val() != "Add Edit Address Page"){
				ACC.mixedcartcheckout.saveContacForm();
				ACC.colorbox.close();
			}

		});

$(document).on("click", "#proceedShippingOriginalAddressMixedCart", function()
		{							
			ACC.mixedcartcheckout.saveShippingAlternateContactForm();
			ACC.colorbox.close();
		});

$(document).on("click", "#suggestedValidShippingAddressSubmitMixedCart", function()
		{			
			var addressOption = $("input[name='address\\.addressSuggestionOption']:checked").val();
			if(addressOption == "originalAddress")
			{
				  ACC.colorbox.close();
				ACC.mixedcartcheckout.saveShippingAlternateContactForm();
			}
			
            if(addressOption == "suggestedAddress")
            {
            	ACC.mixedcartcheckout.copySuggestedValidShippingAddressToForm();
            	  ACC.colorbox.close();
            	ACC.mixedcartcheckout.saveShippingAlternateContactForm();
			}
		});
		
$(document).on("click", "#suggestedValidDeliveryAddressSubmitMixedCart", function()
		{			
			var addressOption = $("input[name='address\\.addressSuggestionOption']:checked").val();
			if(addressOption == "originalAddress")
			{
				  ACC.colorbox.close();
				ACC.mixedcartcheckout.saveAlternateDeliveryContactForm();
			}
			
            if(addressOption == "suggestedAddress")
            {
            	ACC.mixedcartcheckout.copySuggestedValidDeliveryAddressToForm();
            	  ACC.colorbox.close();
            	ACC.mixedcartcheckout.saveAlternateDeliveryContactForm();
			}
		});

        $(document).on("click", "#editOriginalAddress", function()
        {
	 		ACC.colorbox.close();		
	    });
        
        $(document).on("click", "#suggestedAddressCancel", function()
	    {
	 	   ACC.colorbox.close();		
	    });
		 	
});