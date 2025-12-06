ACC.address = {

	_autoload: [
		"bindToChangeAddressButton",
		"bindCreateUpdateAddressForm",
		"bindSuggestedDeliveryAddresses",
		"bindCountrySpecificAddressForms",
		"showAddressFormButtonPanel",
		"bindViewAddressBook",
		"bindToColorboxClose",
		"showRemoveAddressFromBookConfirmation",
		"backToListAddresses",
		"bindAddressValidations"
	],

	spinner: $("<img src='" + ACC.config.commonResourcePath + "/images/spinner.gif' />"),
	addressID: '',
	
	handleChangeAddressButtonClick: function ()
	{

		ACC.address.addressID = ($(this).data("address")) ? $(this).data("address") : '';
		$('#summaryDeliveryAddressFormContainer').show();
		$('#summaryOverlayViewAddressBook').show();
		$('#summaryDeliveryAddressBook').hide();


		$.getJSON(getDeliveryAddressesUrl, ACC.address.handleAddressDataLoad);
		return false;
	},

	handleAddressDataLoad: function (data)
	{
		ACC.address.setupDeliveryAddressPopupForm(data);

		// Show the delivery address popup
		ACC.colorbox.open("",{
		 	inline: true,
			href: "#summaryDeliveryAddressOverlay",
			overlayClose: false,
			onOpen: function (){
				// empty address form fields
				ACC.address.emptyAddressForm();
				$(document).on('change', '#saveAddress', function ()
				{
					var saveAddressChecked = $(this).prop('checked');
					$('#defaultAddress').prop('disabled', !saveAddressChecked);
					if (!saveAddressChecked)
					{
						$('#defaultAddress').prop('checked', false);
					}
				});
			}
		});

	},

	setupDeliveryAddressPopupForm: function (data)
	{
		// Fill the available delivery addresses
		$('#summaryDeliveryAddressBook').html($('#deliveryAddressesTemplate').tmpl({addresses: data}));
		// Handle selection of address
		$('#summaryDeliveryAddressBook button.use_address').on('click',ACC.address.handleSelectExistingAddressClick);
		// Handle edit address
		$('#summaryDeliveryAddressBook button.edit').on('click',ACC.address.handleEditAddressClick);
		// Handle set default address
		$('#summaryDeliveryAddressBook button.default').on('click',ACC.address.handleDefaultAddressClick);
	},

	emptyAddressForm: function ()
	{
		var options = {
			url: getDeliveryAddressFormUrl,
			cache: false,
			data: {addressId: ACC.address.addressID, createUpdateStatus: ''},
			type: 'GET',
			success: function (data)
			{
				$('#summaryDeliveryAddressFormContainer').html(data);
				ACC.address.bindCreateUpdateAddressForm();
			}
		};

		$.ajax(options);
	},

	handleSelectExistingAddressClick: function ()
	{
		var addressId = $(this).attr('data-address');
		$.postJSON(setDeliveryAddressUrl, {addressId: addressId}, ACC.address.handleSelectExitingAddressSuccess);
		return false;
	},

	handleEditAddressClick: function ()
	{

		$('#summaryDeliveryAddressFormContainer').show();
		$('#summaryOverlayViewAddressBook').show();
		$('#summaryDeliveryAddressBook').hide();

		var addressId = $(this).attr('data-address');
		var options = {
			url: getDeliveryAddressFormUrl,
			cache: false,
			data: {addressId: addressId, createUpdateStatus: ''},
			target: '#summaryDeliveryAddressFormContainer',
			type: 'GET',
			success: function (data)
			{
				ACC.address.bindCreateUpdateAddressForm();
				ACC.colorbox.resize();
			},
			error: function (xht, textStatus, ex)
			{
				alert(ACC.config.failedCart+" [" + xht + ", " + textStatus + ", " + ex + "]");
			}
		};
		
		
		$(this).ajaxSubmit(options);
		return false;
	},

	handleDefaultAddressClick: function ()
	{
		var addressId = $(this).attr('data-address');
		var options = {
			url: setDefaultAddressUrl,
			cache: false,
			data: {addressId: addressId},
			type: 'GET',
			success: function (data)
			{
				ACC.address.setupDeliveryAddressPopupForm(data);
			},
			error: function (xht, textStatus, ex)
			{
				alert(ACC.config.failedAddressBook+" [" + xht + ", " + textStatus + ", " + ex + "]");
			}
		};

		$(this).ajaxSubmit(options);
		return false;
	},

	handleSelectExitingAddressSuccess: function (data)
	{
		if (data != null)
		{
			ACC.refresh.refreshPage(data);
			ACC.colorbox.close();
		}
		else
		{
			alert(ACC.config.failedDeliveryAddress);
		}
	},

	bindCreateUpdateAddressForm: function ()
	{
		$('.create_update_address_form').each(function ()
		{
			var options = {
				type: 'POST',
				beforeSubmit: function ()
				{
					$('#checkout_delivery_address').block({ message: ACC.address.spinner });
				},
				success: function (data)
				{
					$('#summaryDeliveryAddressFormContainer').html(data);
					var status = $('.create_update_address_id').attr('status');
					if (status != null && "success" === status.toLowerCase())
					{
						ACC.refresh.getCheckoutCartDataAndRefreshPage();
						ACC.colorbox.close();
					}
					else
					{
						ACC.address.bindCreateUpdateAddressForm();
						ACC.colorbox.resize();
					}
				},
				error: function (xht, textStatus, ex)
				{
					alert(ACC.config.failedCart+" [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					$('#checkout_delivery_address').unblock();
				}
			};

			$(this).ajaxForm(options);
		});
	},

	refreshDeliveryAddressSection: function (data)
	{
		$('.summaryDeliveryAddress').replaceWith($('#deliveryAddressSummaryTemplate').tmpl(data));

	},

	bindSuggestedDeliveryAddresses: function ()
	{
		var status = $('.add_edit_delivery_address_id').attr('status');
		if (status != null && "hasSuggestedAddresses" == status)
		{
			ACC.address.showSuggestedAddressesPopup();
		}
	},

	showSuggestedAddressesPopup: function ()
	{

		ACC.colorbox.open("",{
			href: "#popup_suggested_delivery_addresses",
			inline: true,
			overlayClose: false,
			width: 525,
		});
	},

	bindCountrySpecificAddressForms: function ()
	{
		if($("#pageId").val() == 'add-edit-address' && window.location.href.indexOf('add-address') != -1 && document.referrer.indexOf('add-address') == -1)
        {
           	//$(document).on("load",'#countrySelector select', function ()
        	//{
        	//Hardcoding now as there is only US in the country selection.
				var countryIsoCode = $('.currentBaseStoreId').val();
        		var options = {
        			'addressCode': '',
        			'countryIsoCode': countryIsoCode
        		};
        		ACC.address.displayCountrySpecificAddressForm(options, ACC.address.showAddressFormButtonPanel);
        		//Selecting US by default
        		$("#address\\.country").find('option[value='+ countryIsoCode + ']').attr('selected','selected');
        		$(".errorAddressCountry").html("");

        	//});
        }

	},

	showAddressFormButtonPanel: function ()
	{
		if ($('#countrySelector :input').val() !== '')
		{
			$('#addressform_button_panel').show();
		}
	},

	bindToColorboxClose: function ()
	{
		$(document).on("click", ".closeColorBox", function ()
		{
			ACC.colorbox.close();
		})
	},
	
	bindAddressValidations: function ()
	{	
		$(document).on("blur", "#address\\.phone", function(e)
		{ 
	        var phoneNumber = $(this).val();
	        if (phoneNumber != "" && !ACC.formvalidation.validatePhoneNumber(phoneNumber))  
	        {
	        	ACC.formvalidation.addErrorClassForRegForm('#address\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
	        }
	        else
	        {
	        	ACC.formvalidation.removeErrorClassForRegForm('#address\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	        }
	    });
		$(document).on('input blur', "#address\\.townCity", function(e)
		{
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#address\\.townCity', '.errorTownCity', '#townCity\\.errors',ACC.config.cityError,ERRORMSG.global.city, 'city');
		});
		$(document).on('input blur', "#childUnit", function(e)
				{
					ACC.formvalidation.vaildateFields('#childUnit', '.errorchildUnit', '#childUnitKeyError',ACC.config.shipToError+'<div class="cl"></div><br/><br/>');
				});
		$(document).on('input blur', "#address\\.companyName", function(e)
				{
					ACC.formvalidation.vaildateFields('#address\\.companyName', '.errorCompanyName', '#companyName\\.errors',ERRORMSG.global.company);
				});
		$(document).on('input blur', "#address\\.line1", function(e)
				{
					ACC.formvalidation.vaildateFields('#address\\.line1', '.errorline1', '#line1\\.errors',ERRORMSG.global.address);
				});
		$(document).on('input blur', "#address\\.region", function(e)
				{
					ACC.formvalidation.vaildateFields('#address\\.region', '.errorRegion', '#region\\.errors', ERRORMSG.global.state);
				});
		$(document).on("input blur", "#address\\.postcode", function(e)
				{	
					if(ACC.formvalidation.validatezipcode(e, $("#address\\.postcode").val()))
					{
						ACC.formvalidation.removeErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors');
					}
					else
					{
						ACC.formvalidation.addErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors', ACC.config.zipCodeError);
					}
			    });
	
	
		$(document).on("click", ".siteOneAddressForm", function(e)
		{
		
			e.preventDefault();
			var status = true;
			 ACC.formvalidation.vaildateFields('#address\\.country', '.errorAddressCountry', '#country\\.errors', ERRORMSG.global.country);
	         ACC.formvalidation.vaildateFields('#address\\.title', '.errorTitle', '#title\\.errors', ACC.config.titleError);	        
	         ACC.formvalidation.vaildateFields('#address\\.surname', '.errorSurName', '#surname\\.errors', ERRORMSG.global.lastname);
	         ACC.formvalidation.vaildateFields('#address\\.line1', '.errorline1', '#line1\\.errors', ERRORMSG.global.address);
	         ACC.formvalidation.validateFieldBasedOnRegex(e,'#address\\.townCity', '.errorTownCity', '#townCity\\.errors',ACC.config.cityError,ERRORMSG.global.city, 'city');
	 		
	         //ACC.formvalidation.vaildateFields('#address\\.townCity', '.errorTownCity', '#townCity\\.errors', ERRORMSG.global.city);
	         ACC.formvalidation.vaildateFields('#address\\.region', '.errorRegion', '#region\\.errors', ERRORMSG.global.state);
	         //ACC.formvalidation.vaildateFields('#address\\.postcode', '.errorPostCode', '#postcode\\.errors', ERRORMSG.global.zip);
	         ACC.formvalidation.vaildateFields('#address\\.companyName', '.errorCompanyName', '#companyName\\.errors', ERRORMSG.global.company);
	         ACC.formvalidation.vaildateFields('#childUnit', '.errorchildUnit', '#childUnitKeyError', ACC.config.shipToError);

	        
	         if($("#address\\.phone").val() != null && $("#address\\.phone").val() != '' && !ACC.formvalidation.validatePhoneNumber($("#address\\.phone").val()))
	         {
	                status = false;
	                ACC.formvalidation.addErrorClassForRegForm('#address\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
	         }
	         else
	         {
	            ACC.formvalidation.removeErrorClassForRegForm('#address\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	         }
	         
	         if(ACC.formvalidation.validatezipcode(e, $('#address\\.postcode').val()))
			 {
				 ACC.formvalidation.removeErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors');
			 }
			 else
			 {
				 status = false;
				 ACC.formvalidation.addErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors', ACC.config.zipCodeError);
			 }
		        
	        $('*[id*=error]:visible').each(function() 
			{
				if($(this).html() != '')
				{
					status = false;
				}
			});
	        
	       
	        if(status)
			{
	        	 //Call to Address Verification
	        	
	        		$.ajax({
		        		url: ACC.config.encodedContextPath + "/my-account/validate-address", 
		        		method:"post",
		        		data:$('#siteOneAddressForm').serialize(),		        	
		        		success: function(addressVerificationData){
                           //Testing only will remove hardcoded value
                          // addressVerificationData = {"addressType":"H","city":"Roswell","county":"Fulton","description":"Highrise","errorDesc":"No Error","isAddressValid":true,"parsedStreetName":"Colonial Center","state":"GA","street":"300 Colonial Center Pkwy","street2":"","zipCode":"30076-4899"};
                           
                           ACC.address.isAddressChanged = false;
                           ACC.address.isAddressVerified = true;
                           console.log(addressVerificationData);
		        			if((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404')
		        			{
		        				$("#siteOneAddressForm" ).submit();
		        			}
		        			else
		        			{
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
		                /* error: function (e)		 				
		                 {
		                	 $("#siteOneAddressForm" ).submit();
		 				 }*/
	        		});
	        	        		        	
	        }	
	        else
			{
				ACC.formvalidation.addGlobalError();
				
			}
	        
	    });
		
		$(document).on("click", "#proceedOriginalAddress", function()
		{							
			$("#siteOneAddressForm" ).submit();
		});
		
		$(document).on("click", "#suggestedAddressSubmit", function()
		{			
			var addressOption = $("input[name='address\\.addressSuggestionOption']:checked").val();
			if(addressOption == "originalAddress")
			{
				 //$("#siteOneAddressForm" ).submit();
			}
			
            if(addressOption == "suggestedAddress")
            {
            	    copySuggestedAddressToForm()
            	    //$("#siteOneAddressForm" ).submit();
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
		
		$(document).on("change", "#address\\.country", function()
		{
	        var msg = ERRORMSG.global.country;
	        ACC.formvalidation.vaildateFields('#address\\.country', '.errorAddressCountry', '#country\\.errors', msg);
	    });
	    
		$(document).on("blur", "#address\\.companyName", function()
		{
	        var msg = ERRORMSG.global.company;
	        ACC.formvalidation.vaildateFields('#address\\.companyName', '.errorCompanyName', '#companyName\\.errors', msg);
	    });
			    
		$(document).on("blur", "#address\\.region", function()
		{
	        var msg = ERRORMSG.global.state;
	        ACC.formvalidation.vaildateFields('#address\\.region', '.errorRegion', '#region\\.errors', msg);
	    });
		
	    $(document).on("blur", "#address\\.title", function()
		{
	        var msg = ERRORMSG.global.title;
	        ACC.formvalidation.vaildateFields('#address\\.title', '.errorTitle', '#title\\.errors', msg);
	    });
	    
	    $(document).on("blur", "#childUnit", function()
		{
	        var msg = ACC.config.shipToError;
	        ACC.formvalidation.vaildateFields('#childUnit', '.errorchildUnit', '#childUnitKeyError', msg);
	    });
	    	    
	    $(document).on("blur", "#address\\.firstName", function()
		{
	        var msg = ERRORMSG.global.firstname;
	        ACC.formvalidation.vaildateFields('#address\\.firstName', '.errorFirstName', '#firstName\\.errors', msg);
	    });
	    
	    $(document).on("blur", "#address\\.surname", function()
		{
	        var msg = ERRORMSG.global.lastname;
	        ACC.formvalidation.vaildateFields('#address\\.surname', '.errorSurName', '#surname\\.errors', msg);
	    });
	    
	    $(document).on("blur", "#address\\.line1", function()
		{
	        var msg = ERRORMSG.global.address;
	        ACC.formvalidation.vaildateFields('#address\\.line1', '.errorline1', '#line1\\.errors', msg);
	    });
	    
	    $(document).on("blur", "#address\\.townCity", function(e)
		{
	        ACC.formvalidation.validateFieldBasedOnRegex(e,'#address\\.townCity', '.errorTownCity', '#townCity\\.errors', ACC.config.cityError, ERRORMSG.global.city, 'city');
	    });
	    
	    $(document).on("blur", "#address\\.postcode", function(e)
		{
	        //var msg = ERRORMSG.global.zip;
	        //ACC.formvalidation.vaildateFields('#address\\.postcode', '.errorPostCode', '#postcode\\.errors', msg);
	        if(ACC.formvalidation.validatezipcode(e, $("#address\\.postcode").val()))
			{
				ACC.formvalidation.removeErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors');
			}
			else
			{
				ACC.formvalidation.addErrorClassForRegForm('#address\\.postcode', '.errorPostCode', '#postcode\\.errors', ACC.config.zipCodeError);
			}
	    });
	    
	    $(document).on("input", "#address\\.phone", function(e)
	    {
	    	ACC.formvalidation.handlePhoneNumberOnInput('#address\\.phone', '.errorPhoneNumber', '#phoneNumber\\.errors');
	    });
	    
	},

	displayCountrySpecificAddressForm: function (options, callback)
	{
		$.ajax({
			url: ACC.config.encodedContextPath + '/my-account/addressform',
			cache: false,
			async: true,
			data: options,
			dataType: "html",
			beforeSend: function ()
			{
				$("#i18nAddressForm").html(ACC.address.spinner);
			}
		}).done(function (data)
				{
					$("#i18nAddressForm").html($(data).html());
					if (typeof callback == 'function')
					{
						callback.call();
					}
				});
	},

	bindToChangeAddressButton: function ()
	{
		$(document).on("click", '.summaryDeliveryAddress .editButton', ACC.address.handleChangeAddressButtonClick);
	},

	bindViewAddressBook: function ()
	{

		$(document).on("click",".js-address-book",function(e){
			e.preventDefault();

			ACC.colorbox.open("Saved Addresses",{
				href: "#addressbook",
				inline: true,
				width:"500px"
			});
			
		})

		
		$(document).on("click", '#summaryOverlayViewAddressBook', function ()
		{
			$('#summaryDeliveryAddressFormContainer').hide();
			$('#summaryOverlayViewAddressBook').hide();
			$('#summaryDeliveryAddressBook').show();
			ACC.colorbox.resize();
		});
	},
	
	showRemoveAddressFromBookConfirmation: function ()
	{
		$(document).on("click", ".removeAddressFromBookButton", function ()
		{
			var addressId = $(this).data("addressId");

			ACC.colorbox.open("",{
				inline: true,
				height: false,
				width: 560,
				href: "#popup_confirm_address_removal_" + addressId,
				onComplete: function ()
				{

					$(this).colorbox.resize();
				}
			});

		})
	},

	backToListAddresses: function(){
		$(".addressBackBtn").on("click", function(){
			var sUrl = $(this).data("backToAddresses");
			window.location = sUrl;
		});
	}
};


function copySuggestedAddressToForm()
{

	$("#siteOneAddressForm #address\\.line1").val($("#suggestedAddress\\.line1").data("line1"));
	$("#siteOneAddressForm #address\\.line2").val($("#suggestedAddress\\.line2").data("line2"));
	$("#siteOneAddressForm #address\\.townCity").val($("#suggestedAddress\\.city").data("city"));
	$("#siteOneAddressForm #address\\.region").val($("#siteOneAddressForm #address\\.country").val()+"-"+$("#suggestedAddress\\.state").data("state"));
	$("#siteOneAddressForm #address\\.postcode").val($("#suggestedAddress\\.zipcode").data("zipcode"));
	$("#siteOneAddressForm #address\\.district").val($("#suggestedAddress\\.county").data("county"));
	
}

function createPopupContent(addressVerificationData){
	
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
        					
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedValidAddressSubmit'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";	
			
		} else {
			popupContent ="<form id='addressSuggestionForm' class='row' style='width:450px;'><div class='col-md-6 col-xs-12 margin-top-add'> <span class='colored-radio'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/></span> <span class='black-title'><b>"+ACC.config.originalAddress+"</b></span><div class='cl'></div><p></p><div class='add-address-sec'> <span>"+ $("#siteOneAddressForm #address\\.line1").val() +"</span><div class='cl'></div>"
		
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
							
			popupContent = popupContent+"</div></div>  <div class='cl'></div><br><br> <div class='col-md-4 col-sm-6 col-xs-4'><input type='button' class='btn btn-primary btn-block' value='"+ACC.config.save+"' id= 'suggestedAddressSubmit'/></div><div class='col-md-4 col-sm-6 col-xs-4'> <input type='button' class='btn btn-default btn-block' value='"+ACC.config.cancel+"' id= 'suggestedAddressCancel'/> </div></form>";
		}		        				    
		
	}else if(!addressVerificationData.isAddressValid){
		popupContent = "<p style='margin-top:-18px;'>"+ACC.config.veryfyAddressError+"</p> <br> <div class='row'><div class='col-md-6 col-xs-12  col-sm-12'><input type='button' value='Proceed Anyway'  class='btn btn-primary btn-block' id='proceedOriginalAddress'/></div> <span class='hidden-xs hidden-md hidden-lg visible-sm'><div class='cl'></div><br/></span><div class='col-md-6 col-xs-12 col-sm-12'><input type='button' value='Edit Address' class='btn btn-default btn-block' id='editOriginalAddress'/></div></div>";
		
	}
	
return popupContent;
}
