ACC.formvalidation = {

	_autoload: [
		"requestAccountValidations",
		"createUserValidations",
		"addressCreationValidations",
		"placeOrdersApproval"
	],

	addGlobalError: function ()
	{
		
		//var result = containsAny("div.alert", ["Please", "Por"]);
		
		

		if (($('.global-alerts > div.alert:contains("Please")').length == 0 ) && ($('.global-alerts > div.alert:contains("Por")').length == 0))
		{
			
			//$(".global-alerts").show();
			$(".breadcrumb-section").append("<div class='global-alerts container-lg container-fluid'>	<div class='alert alert-box alert-dismissable edit-user-alert'>" +
			 
			"<div class='msg alert-msg-text'>"+ACC.config.globalError+"</div></div></div>");
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

	
	addErrorClassForRegForm: function (input, error, errorText, msg)
	{
		$(error).html(msg);
		$(input).css("cssText", "background-color: #fec3c3 !important");
		$(input).css("border-color", "#fd7b7b");
		$(input).parent().css("margin-bottom", "5px");
		$(error).css("color", "#fe0303");
		$(error).css("font-weight", "normal");
		$(errorText).html('');
	},
	
	removeErrorClassForRegForm: function (input, error, errorText)
	{
		$(error).html('');
		$(input).removeAttr('style');
		$(error).removeAttr('style');
		$(input).css("background", "#ffffff");
		$(input).css("border-color", "#cccccc");
		$(input + "\\.errors").hide();
		$(errorText).html('');
	},
	
	vaildateFields: function (input, error, errorText, msg)
	{
		var valid = false;
		
	    if($(input).val()==null || $(input).val() == "" || $(input).val() =="Select")
	    {
	    	ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, msg);
	    }
	    else
	    {
	    	ACC.formvalidation.removeErrorClassForRegForm(input, error, errorText);
	    	valid = true;
	    }
	    return valid;
	},

	vaildateShippingState: function (input, error, errorText, msg, stateMsg)
	{
		var valid = false;
		let isNationalShipping = ($(".isNationalShipping").length)? $(".isNationalShipping").val() : 'false';
	    if($(input).val()==null || $(input).val() == "" || $(input).val() =="Select")
	    {
	    	ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, msg);
	    }
	    else if (isNationalShipping == 'false' && $(input).val() != _AAData.storeState)
		{
			ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, stateMsg);
		}
		else
	    {
	    	ACC.formvalidation.removeErrorClassForRegForm(input, error, errorText);
			$('.same-shipping-state-error').hide();
	    	valid = true;
	    }
	    return valid;
	},
	
	handlePhoneNumberOnInput: function (input, error, errorText)
	{
		var val = $(input).val();
	    	if(isNaN(val)){
	         	val = val.replace(/[^0-9\.]/g,'');
	         	if(val.split('.').length>2) val =val.replace(/\.+$/,"");
	   	 }
	   	 $(input).val(val);
		
		var length = $(input).val().length;
		if(length >= 10)
    	{
			if(ACC.formvalidation.validatePhoneNumber($(input).val()))
			{	
				ACC.formvalidation.removeErrorClassForRegForm(input, error, errorText);
				if(length == 10)
				{
					var a = $(input).val();
  		    		var b = "-";
  		    		var position1 = 3;
  		    		var position2 = 7;
  		    		var output1 = [a.slice(0, position1), b, a.slice(position1)].join('');
  		    		var output2 = [output1.slice(0, position2), b, output1.slice(position2)].join('');
  		    		$(input).val(output2);
      		    	$(input).attr('maxlength', '12');
				}
				else
				{
					return true;
				}
			}
			else
			{	
				ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, ACC.config.phoneNumberError);
			}
    	}
	},
	
	validatePhoneNumber: function (input)
	{
		var regex1 = new RegExp("^([0-9]{10})$");
		var regex2 = new RegExp("^([0-9]{3})(-)([0-9]{3})(-)([0-9]{4})$");
		if(regex1.test(input) || regex2.test(input))
		{
			return true;
		}
		else
		{
			 return false;
		}
	},
	
	validatezipcode: function (e, zipcode) {
		var regex1 = new RegExp("^([0-9]{5})(-)([0-9]{4})$"); // US ZIP+4 (12345-6789)
		var regex2 = new RegExp("^([0-9]{5})$"); // US Standard ZIP (12345)
		var canadaRegex = new RegExp("^[A-Za-z][0-9][A-Za-z] ?[0-9][A-Za-z][0-9]$"); // Canada (M6H 2C3 or M6H2C3)
		//var zipcode = $("#zipcode").val();
		if (zipcode == null || zipcode == "") {
			return false;
		} else {
			var keystobepassedout = "BackspaceTabDelete";
			if (keystobepassedout.indexOf(e.key) != -1) {
				return true;
			}
			var countryIsoCode = $('.currentBaseStoreId').val();
			if (countryIsoCode === "US") {
				if (regex1.test(zipcode) || regex2.test(zipcode)) {
					return true;
				}
			} else if (countryIsoCode === "CA") {
				if (canadaRegex.test(zipcode)) {
					return true;
				}
			}
			return false;
		}
	},
	validateEmailPattern: function (email)
	{
		var mailformat = new RegExp("^[a-zA-Z0-9._%'-]*@[a-zA-Z0-9.-]*(\\.[A-Za-z]{2,20})$");
		if (email == null || email == "" || email == undefined) 
		{
			 return false;
        }
		else
		{  
        	if(mailformat.test(email))
        	{
        		return true;
        	}
        	else
        	{	
        		return false;
        	}
        }
	},
	validateYearsInBusiness: function (contrYearsInBusiness){
		
		var regex1 = new RegExp("^\\s*(?=.*[0-9])\\d{0,3}(?:\\.\\d{1,2})?\\s*$");
		
		if (contrYearsInBusiness == null || contrYearsInBusiness == "" || contrYearsInBusiness == undefined) 
		{
			 return false;
        }
		else
		{ 
			if(regex1.test(contrYearsInBusiness))
        	{
        		return true;
        	}
        	else
        	{	
        		return false;
        	}
        }
	},
	
	validateAccountNumber: function (e)
	{
		var regex = new RegExp("^[0-9 -]+$");
        var radioValue = $("input[name='hasAccountNumber']:checked").val();
        if (regex.test($('#accountNumber').val())) 
        {
        	ACC.formvalidation.removeErrorClassForRegForm('#accountNumber', '#errorAccountNumber', '#accountNumber\\.errors');
            return true;
        }
        else if(radioValue == 'yes' && $('#accountNumber').val() != "" && !regex.test($('#accountNumber').val()))
        {
	        ACC.formvalidation.addErrorClassForRegForm('#accountNumber', '#errorAccountNumber', '#accountNumber\\.errors', ACC.config.accountNumberErrorMsg+'<div class="cl"></div><br/><br/>');
	        e.preventDefault();
	        return false;
        }
        else if(radioValue == 'yes' && $('#accountNumber').val() == "" && !regex.test($('#accountNumber').val()))
        {
        	ACC.formvalidation.addErrorClassForRegForm('#accountNumber', '#errorAccountNumber', '#accountNumber\\.errors', ACC.config.pleaseEnterAccountNumber+'<div class="cl"></div><br/><br/>');
	        e.preventDefault();
	        return false;
        }
	},
	
	validateFieldBasedOnRegex: function (e,input, error, errorText, msg, globalMsg, fieldType)
	{
		if(fieldType === undefined){
			var regex = new RegExp("^([A-Za-z/'-]*)$");
		} else if(fieldType == 'city') {
			var regex = new RegExp("^[a-zA-Z .-]*$");
		}
		else{
			var regex = new RegExp("^[a-zA-Z .-]*$");
		}
		
		
        if ($(input).val() != "" && regex.test($(input).val())) 
        {
        	ACC.formvalidation.removeErrorClassForRegForm(input, error, errorText);
            return true;
        }
        else if($(input).val() != "" && !regex.test($(input).val()))
        {	
        	ACC.formvalidation.addErrorClassForRegForm(input, error, errorText,msg);
 	        e.preventDefault();
 	        return false;
        }
        else
        {
	        ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, globalMsg);
	        e.preventDefault();
	        return false;
        }
	},
	
	
	
	requestAccountValidations: function ()
	{
		function validateFirstAndLastName(e,id,errorId,errorText,nullErrMsg,msg, globalMsg){
		var status = '';
			if(ACC.formvalidation.vaildateFields(id,errorId,errorText,nullErrMsg)){
				status = ACC.formvalidation.validateFieldBasedOnRegex(e,id, errorId, errorText,msg ,globalMsg);
				}
			else{
				status = ACC.formvalidation.vaildateFields(id,errorId,errorText,nullErrMsg);
			}
			return status;
		}
		$("#recaptcha-border").removeClass('recaptcha-error-border');
		  if($('#recaptchaChallengeAnswered').val() == 'false')
			{
			 $("#recaptcha-error").removeClass('hidden');
			 $("#recaptcha-border").addClass('recaptcha-error-border');
			}
		$(document).on("input blur", "#companyName", function()
		{
	        var msg = ERRORMSG.global.company;
	        ACC.formvalidation.vaildateFields('#companyName', '#errorCompanyName', '#companyName\\.errors', msg);
	    });
		
		$(document).on("input blur", "#contrYearsInBusiness", function()
		{
			var contrYearsInBusiness = $(this).val();
			if(contrYearsInBusiness != null && contrYearsInBusiness != '' && contrYearsInBusiness != undefined)
		    {
		        if (!ACC.formvalidation.validateYearsInBusiness(contrYearsInBusiness))
                {
		        	var msg = strings['js.request.contryearsInBusiness.invalid'];
                    ACC.formvalidation.addErrorClassForRegForm('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors', msg);
                }
                else
                {
                    ACC.formvalidation.removeErrorClassForRegForm('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors');
                }
		    }
		    else
		    {
            	ACC.formvalidation.addErrorClassForRegForm('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors', strings['js.request.contryearsInBusiness.empty']);
		    }
		});
				
		$(document).on("input blur", "#contrEmpCount", function()
		{
			var msg = strings['js.request.contrempcount.empty'];
			ACC.formvalidation.vaildateFields('#contrEmpCount', '#errorContrEmpCount', '#contrEmpCount\\.errors', msg);
		});
		$(document).on("input blur", "#contrPrimaryBusiness", function()
		{
			var msg = strings['js.request.primarybusiness.empty'];
			ACC.formvalidation.vaildateFields('#contrPrimaryBusiness', '#errorContrPrimaryBusiness', '#contrPrimaryBusiness\\.errors', msg);
		});
		$(document).on("input change", "#contrPrimaryBusinessL1", function()
		{
			var msg = strings['js.request.primarybusiness.empty'];
			ACC.formvalidation.vaildateFields('#contrPrimaryBusiness', '#errorContrPrimaryBusiness', '#contrPrimaryBusiness\\.errors', msg);
		});
		
		$(document).on("input blur", "#landscapingIndustry", function()
		{
			var msg = strings['js.request.landscapingindustry.empty'];
			ACC.formvalidation.vaildateFields('#landscapingIndustry', '#errorlandscapingIndustry', '#landscapingIndustry\\.errors', msg);
		});


		$(document).on("blur", "#accountNumber", function()
		{
			var radioValue = $("input[name='hasAccountNumber']:checked").val();
			if(radioValue == 'yes')
			{
				var msg = ACC.config.pleaseEnterAccountNumber;
		        ACC.formvalidation.vaildateFields('#accountNumber', '#errorAccountNumber', '#accountNumber\\.errors', msg);
			}
		});
		
		$(document).on('input blur', "#accountNumber", function(e)
		{
			ACC.formvalidation.validateAccountNumber(e);
	    });
		
		
		$("input[name=hasAccountNumber]:radio").change(function () 
		{
			var radioValue = $("input[name='hasAccountNumber']:checked").val();
			if(radioValue == 'no')
			{
				ACC.formvalidation.removeErrorClassForRegForm('#accountNumber', '#errorAccountNumber', '#accountNumber\\.errors');
			}
		});
		
		$(document).on('input blur', "#firstName", function(e)		{	
			validateFirstAndLastName(e,'#firstName','#errorFirstName','#firstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
			
	    });
		
		
		$(document).on('input blur', "#lastName", function(e){
			validateFirstAndLastName(e,'#lastName','#errorLastName','#lastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);

	    });
		
		$(document).on('input blur', ".firstName", function(e)		{	
			validateFirstAndLastName(e,'.firstName','.errorFirstName','.firstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
			
	    });
		
		
		$(document).on('input blur', ".lastName", function(e){
			validateFirstAndLastName(e,'.lastName','.errorLastName','.lastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);

	    });
		
		$(document).on("input blur", "#addressLine1", function()
		{
	        var msg = ERRORMSG.global.address;
	        ACC.formvalidation.vaildateFields('#addressLine1', '#errorAddressLine1', '#addressLine1\\.errors', msg);
	    });
		
		$(document).on('input blur', "#city", function(e)
		{
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#city', '#errorCity', '#city\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'city');
	    });
		
		$(document).on("input blur", "#state", function()
		{
	        var msg = ERRORMSG.global.state;
	        ACC.formvalidation.vaildateFields('#state', '#errorState', '#state\\.errors', msg);
	    });
		
		$(document).on("input blur", "#zipcode", function(e)
		{	
			if(ACC.formvalidation.validatezipcode(e, $("#zipcode").val()))
			{
				ACC.formvalidation.removeErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors');
			}
			else
			{
				ACC.formvalidation.addErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors',ACC.config.zipCodeError);
			}
	    });

		$(document).on("blur", "#phoneNumber", function(e)
		{
			var phoneNumber = $(this).val();
			if(phoneNumber != null && phoneNumber != '' && phoneNumber != undefined)
		    {
		        if (!ACC.formvalidation.validatePhoneNumber(phoneNumber))
                {
                    var msg= ACC.config.phoneNumberError ;
                    ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors', msg);
                }
                else
                {
                    ACC.formvalidation.removeErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors');
                }
		    }
		    else
		    {
            	ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
		    }
	    });
		
		$(document).on("input", "#phoneNumber", function(e)
		{
			ACC.formvalidation.handlePhoneNumberOnInput('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors');
	    });

		$(document).on("blur", ".companyphone", function(e)
		{
			var phoneNumber = $(this).val();
			if(phoneNumber != null && phoneNumber != '' && phoneNumber != undefined)
		    {
		        if (!ACC.formvalidation.validatePhoneNumber(phoneNumber))
                {
                    var msg= ACC.config.phoneNumberError ;
                    ACC.formvalidation.addErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors', msg);
                }
                else
                {
                    ACC.formvalidation.removeErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors');
                }
		    }
		    else
		    {
            	ACC.formvalidation.addErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors', ERRORMSG.global.phone);
		    }
	    });
		
		$(document).on("input", ".companyphone", function(e)
		{
			ACC.formvalidation.handlePhoneNumberOnInput('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors');
	    });
		
		$(document).on("input", "#contrYearsInBusiness", function(e)
		{
			match = (/(\d{0,3})[^.]*((?:\.\d{0,2})?)/g).exec(this.value.replace(/[^\d.]/g, ''));
			this.value = match[1] + match[2];
		});
		
		$(document).on("input blur", "#emailaddress", function(e)
		{
			ACC.formvalidation.retainValueformHomeowner();
			if(ACC.formvalidation.validateEmailPattern($("#emailaddress").val()))
		    {
				 ACC.formvalidation.removeErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
				 //ACC.formvalidation.validateEmailWithBriteVerifyOnBlur('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.global.email, $('#emailaddress').val(), '#siteOneRequestAccountForm');
			}
			else
			{
				 ACC.formvalidation.addErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
			}
		});
		
		$(document).on("input blur", ".emailaddress", function(e)
		{
			ACC.formvalidation.retainValueformContractor();
			if(ACC.formvalidation.validateEmailPattern($(".emailaddress").val()))
		    {
				 ACC.formvalidation.removeErrorClassForRegForm('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.invalid.email);
				 //ACC.formvalidation.validateEmailWithBriteVerifyOnBlur('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.global.email, $('.emailaddress').val(), '#siteOneRequestAccountForm');
			}
			else
			{
				 ACC.formvalidation.addErrorClassForRegForm('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.invalid.email);
			}
		});
	
		$(document).on("click", ".requestAccountFormSubmit", function(e)
		{
			e.preventDefault();
			loading.start();
			var status = true;
			ACC.formvalidation.noAccountCreationErrorFlag = true;
			ACC.formvalidation.isEmailValidBriteVerifyFlag = true;
			document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
			ACC.briteverify.reCaptcha(grecaptcha.getResponse());
			var radioValue = $("input[name='hasAccountNumber']:checked").val();
			if(radioValue == 'yes')
			{
				var validField = ACC.formvalidation.validateAccountNumber(e);
				if(validField == false)
				{
					status = false;
				}
			}
			
			var radioValue = $("input[id='typeOfCustomer1']:checked").val();
			if(radioValue == 'Contractor')
			{
				ACC.formvalidation.vaildateFields('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors', strings['js.request.contryearsInBusiness.empty']);
				var msg = strings['js.request.contryearsInBusiness.invalid'];
				if(null != $("#contrYearsInBusiness").val() && $("#contrYearsInBusiness").val() != '')
				{
					if(!ACC.formvalidation.validateYearsInBusiness($("#contrYearsInBusiness").val()))
					{
			    		 status = false;
			    		 ACC.formvalidation.addErrorClassForRegForm('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors', msg);
					}
					else
					{
						ACC.formvalidation.removeErrorClassForRegForm('#contrYearsInBusiness', '#errorContrYearsInBusiness', '#contrYearsInBusiness\\.errors');
					}
				}
				if($("#landscapingIndustry").val() == 'select'){
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('#landscapingIndustry', '#errorlandscapingIndustry', '#landscapingIndustry\\.errors', strings['js.request.landscapingindustry.empty']);
				}
				else{
					ACC.formvalidation.removeErrorClassForRegForm('#landscapingIndustry', '#errorlandscapingIndustry', '#landscapingIndustry\\.errors');
				}
				ACC.formvalidation.vaildateFields('#contrEmpCount', '#errorContrEmpCount', '#contrEmpCount\\.errors', strings['js.request.contrempcount.empty']);
				ACC.formvalidation.vaildateFields('#contrPrimaryBusiness', '#errorContrPrimaryBusiness', '#contrPrimaryBusiness\\.errors', strings['js.request.primarybusiness.empty']);
				ACC.formvalidation.vaildateFields('#companyName', '#errorCompanyName', '#companyName\\.errors', ERRORMSG.global.company);
			}
			
			var ContractorfirstName = validateFirstAndLastName(e,'#firstName','#errorFirstName','#firstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
			var ContractorlastName = validateFirstAndLastName(e,'#lastName','#errorLastName','#lastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);
			var HomeownerfirstName = validateFirstAndLastName(e,'.firstName','.errorFirstName','.firstName\\.errors',ACC.config.enterFirstName,ACC.config.nameErrorMsg, ERRORMSG.global.firstname);
			var HomeownerlastName = validateFirstAndLastName(e,'.lastName','.errorLastName','.lastName\\.errors',ACC.config.enterLastName,ACC.config.nameErrorMsg, ERRORMSG.global.lastname);
			if(radioValue == 'Contractor')
			{
				status = (ContractorfirstName && ContractorlastName);
			}
			else
			{
				status = (HomeownerfirstName && HomeownerlastName);
			}
			ACC.formvalidation.vaildateFields('#addressLine1', '#errorAddressLine1', '#addressLine1\\.errors', ERRORMSG.global.address);
			ACC.formvalidation.vaildateFields('#city', '#errorCity', '#city\\.errors',ERRORMSG.global.city);
			//ACC.formvalidation.validateFieldBasedOnRegex(e,'#city', '#errorCity', '#city\\.errors',ACC.config.cityErrorMsg,ERRORMSG.global.city, 'city');
			ACC.formvalidation.vaildateFields('#state', '#errorState', '#state\\.errors', ERRORMSG.global.state);
			
			if(null != $("#phoneNumber").val() && $("#phoneNumber").val() != '')
			{
				if(!ACC.formvalidation.validatePhoneNumber($("#phoneNumber").val()))
				{
		    		 status = false;
		    		 ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
				}
				else
				{
					ACC.formvalidation.removeErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors');
				}
			}
			else
			{
				status = false;
				ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#errorPhoneNumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
			}

			if(null != $(".companyphone").val() && $(".companyphone").val() != '')
			{
				if(!ACC.formvalidation.validatePhoneNumber($(".companyphone").val()))
				{
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors', ERRORMSG.global.phone);
				}
				else
				{
					ACC.formvalidation.removeErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors');
				}
			}
			else
			{
				status = false;
				ACC.formvalidation.addErrorClassForRegForm('.companyphone', '.errorPhoneNumber', '.companyphone\\.errors', ERRORMSG.global.phone);
			}
			
			 
			if(ACC.formvalidation.validatezipcode(e, $("#zipcode").val()))
		    {
				 ACC.formvalidation.removeErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors');
			}
			else
			{
				 status = false;
				 ACC.formvalidation.addErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors', ACC.config.zipCodeError);
			}
			
			if(radioValue == 'Contractor')
			{
				if(ACC.formvalidation.validateEmailPattern($("#emailaddress").val()))
				{
					ACC.formvalidation.removeErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
					ACC.formvalidation.validateEmailWithBriteVerifyOnBlur('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.global.email, $('#emailaddress').val(), '#siteOneRequestAccountForm');
				}
				else
				{
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
				}
			}
			else
			{
				if(ACC.formvalidation.validateEmailPattern($(".emailaddress").val()))
				{
					ACC.formvalidation.removeErrorClassForRegForm('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.invalid.email);
					ACC.formvalidation.validateEmailWithBriteVerifyOnBlur('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.global.email, $('.emailaddress').val(), '#siteOneRequestAccountForm');
				}
				else
				{
					status = false;
					ACC.formvalidation.addErrorClassForRegForm('.emailaddress', '.errorEmailAddress', '.emailaddress\\.errors', ERRORMSG.invalid.email);
				}
			}
			$('*[id*=error]:visible').each(function() 
			{
				if($(this).html() != '')
				{
					status = false;
				}
			});
			if(!status)	
			{
				ACC.formvalidation.noAccountCreationErrorFlag = false;
				ACC.formvalidation.addGlobalError();
				loading.stop();
			}
		 });
		$(".requestToolTip").attr('title', ACC.config.accountNumberProvided);
	},
	statePrefix: "",
	noAccountCreationErrorFlag: true,
	isEmailValidBriteVerifyFlag: true,
	accountCreationFormSubmit: function(isEmailValid, input, error, errorText, msg, textstatus){
		grecaptcha.reset();
		if(!isEmailValid){	//BriteVerify email respose check
			ACC.briteverify.vaildateEmailFields(input, error, errorText, msg, textstatus);
			//ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, msg);
			ACC.formvalidation.addGlobalError();
			loading.stop();
		}
		else if(ACC.formvalidation.noAccountCreationErrorFlag){
			let stateSelect = $("#siteOneRequestAccountForm #state option:selected");
			let stateVal = stateSelect.val().split("-");
			ACC.formvalidation.statePrefix = (stateVal.length!=1)? stateVal[0] : ACC.formvalidation.statePrefix;
			stateSelect.val(stateVal[stateVal.length-1]); //removing the US from the state to validate for melissa
			if ($("input:radio[id='typeOfCustomer2']").is(":checked")) {
			$("#siteOneRequestAccountForm #landscapingIndustry option:selected").val(false);
			}
			$.ajax({
				url: ACC.config.encodedContextPath + "/request-account/validate-address",
				method: "POST",
				data: $('#siteOneRequestAccountForm').serialize(),
				success: function (addressVerificationData) {
					ACC.address.isAddressChanged = false;
					ACC.address.isAddressVerified = true;
					stateSelect = $("#siteOneRequestAccountForm #state option:selected");
					stateVal = stateSelect.val().split("-");
					if(ACC.formvalidation.statePrefix != "" && stateVal.length == 1){
						stateSelect.val(ACC.formvalidation.statePrefix + "-" + stateVal[0]); //restoring the State value
					}

					if ((addressVerificationData.isAddressValid && !addressVerificationData.isAddressCorrected) || addressVerificationData.status.errorCode == '404') {
						ACC.formvalidation.validateEmailInHybrisResponse();
					}
					else {
						loading.stop();
						$.colorbox({
							html: ACC.formvalidation.createReqPopupContent(addressVerificationData),
							maxWidth: "100%",
							width: "550px",
							maxHeight: "100%",
							overflow: "auto",
							opacity: 0.7,
							title: false,
							className: "invalidOriginalAddress",
							close: '<span class="icon-close"></span>'
						});
					}
				}
			});
		}
	},
	validateEmailInHybrisResponse:function(){
		$.ajax({
		    url : ACC.config.encodedContextPath + "/request-account/isEmailAlreadyExists",
		    method:"POST",
		    data:$('#siteOneRequestAccountForm').serialize(),	
		    success: function(response) {
		        if(response != null && response == true){ // existing email
		            $('.RTA_wrapper, .businessDetails, .retailDetails').hide();
		            $('.business-fill-icon, .branch-icon, .branch-fill-icon, .confirmation-icon, .next_goback_wrapper').addClass('hidden');
        			$('.business-success-icon, .branch-success-icon, .confirmation-fill-icon, .shop_siteone_wrap, .verification-container, .verification-container-step4').removeClass('hidden');
		            ACC.formvalidation.analyticsDataSet(false);
					loading.stop();
		        }
				else {
		            ACC.accountdashboard.homeBranchSelection();
		        }
		    }
		});
	},
	validateEmailWithBriteVerifyOnBlur: function (input, error, errorText, msg, value, formId) {
		var briteverify = document.getElementById('briteVerifyEnable').value;
		var briteverifytimeout = document.getElementById('briteVerifyTimeout').value;
		var msgAjax = msg;
		var responseAjax = "";
		if (briteverify == "true") {
			$.ajax({
				url: ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email=' + value,
				type: "GET",
				crossDomain: true,
				timeout: briteverifytimeout,
				success: function (response) {
					if (response == "invalid") {
						ACC.formvalidation.isEmailValidBriteVerifyFlag = false;
						msgAjax = ACC.config.emailAddressError1;
						responseAjax = response;
					}
					if(formId == "#siteOneRequestAccountForm"){ //request account
						ACC.formvalidation.accountCreationFormSubmit(ACC.formvalidation.isEmailValidBriteVerifyFlag, input, error, errorText, msgAjax, responseAjax);
					}
					else{ //self serve
						ACC.accountdashboard.selfServeFormSubmit(ACC.formvalidation.isEmailValidBriteVerifyFlag, input, error, errorText, msgAjax, responseAjax);
					}
				},
				error: function (response, textstatus, message) {
					if (textstatus == "timeout" || textstatus == "error") {
						ACC.formvalidation.isEmailValidBriteVerifyFlag = false;
						msgAjax = ACC.config.emailAddressUnable;
						responseAjax = textstatus;
					}
					if(formId == "#siteOneRequestAccountForm"){ //request account
						ACC.formvalidation.accountCreationFormSubmit(ACC.formvalidation.isEmailValidBriteVerifyFlag, input, error, errorText, msgAjax, responseAjax);
					}
					else{ //self serve
						ACC.accountdashboard.selfServeFormSubmit(ACC.formvalidation.isEmailValidBriteVerifyFlag, input, error, errorText, msgAjax, responseAjax);
					}
				}
			});
		}
	},
	createUserValidations : function ()
	{
		$(document).find("#user\\.phonenumber").prop('type', 'tel');
		$(document).find("#user\\.email").prop('type', 'email');
		$(document).on("input focus", "#user\\.phonenumber", function(e)
      	{	
			ACC.formvalidation.handlePhoneNumberOnInput('#user\\.phonenumber', '#errorPhoneNumber', '#user\\.phonenumber\\.errors');
      	});
		
		$(document).on("input blur", "#user\\.title", function()
		{
	        var msg = ERRORMSG.global.title;
	        ACC.formvalidation.vaildateFields('#user\\.title', '#errortitle', '#title\\.errors', msg);
	    });

		$(document).on("input blur", "#user\\.firstName", function(e)
		{
	        var msg = ERRORMSG.global.firstname;
	        ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\.firstName', '#errorFirstName', '#firstName\\.errors',ACC.config.nameErrorMsg,msg);
	     	 
	    });
				
		$(document).on("input blur", "#user\\.lastName", function(e)
		{
	        var msg = ERRORMSG.global.lastname;
	        ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\.lastName', '#errorLastName', '#lastName\\.errors',ACC.config.nameErrorMsg,msg);
	    });
	
		$(document).on("input focus", "#user\\.email", function()
		{
						if(ACC.formvalidation.validateEmailPattern($("#user\\.email").val()))
		    {
				 ACC.formvalidation.removeErrorClassForRegForm('#user\\.email', '#errorEmailAddress', '#emailaddress\\.errors');
				 ACC.formvalidation.removeErrorClassForRegForm('#user\\.email', '#email\\.errors', '#emailaddress\\.errors');
			}
			else
			{
				 ACC.formvalidation.addErrorClassForRegForm('#user\\.email', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
			}
	    });
		
		
		$(document).on("input blur", "#user\\.unit\\.title", function()
		{
	        var msg = ACC.config.selectShipTo;
	        ACC.formvalidation.vaildateFields('#user\\.unit\\.title', '#errorparentunit', '#parentunit\\.errors', msg);
	    });
		
		$(document).on("click", ".createUserSubmit", function(e)
		{
			e.preventDefault();
			var status = true;
			
			var validateEmail = true;
			if(window.location.pathname.indexOf("/edit") != -1)
			{
				validateEmail = false;
			}

			ACC.formvalidation.vaildateFields('#user\\.title', '#errortitle', '#title\\.errors', ACC.config.selectTitle);
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\.firstName', '#errorFirstName', '#firstName\\.errors',ACC.config.nameMay,ERRORMSG.global.firstname);
			ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\.lastName', '#errorLastName', '#lastName\\.errors',ACC.config.nameMay,ERRORMSG.global.lastname);
			
			if(validateEmail)
			{
				if(ACC.formvalidation.validateEmailPattern($("#user\\.email").val()))
			    {
					 ACC.formvalidation.removeErrorClassForRegForm('#user\\.email', '#errorEmailAddress', '#emailaddress\\.errors');
					 ACC.formvalidation.removeErrorClassForRegForm('#user\\.email', '#email\\.errors', '#emailaddress\\.errors');
				}
				else
				{
					 status = false;
					 ACC.formvalidation.addErrorClassForRegForm('#user\\.email', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
				}
			}
			
			ACC.formvalidation.vaildateFields('#user\\.unit\\.title', '#errorparentunit', '#parentunit\\.errors', ERRORMSG.global.parentunit);
			
	        if (!ACC.formvalidation.validatePhoneNumber($("#user\\.phonenumber").val()))  
	        {
	        	status = false;
	        	ACC.formvalidation.addErrorClassForRegForm('#user\\.phonenumber', '#errorPhoneNumber', '#user\\.phonenumber\\.errors', ERRORMSG.global.phone);
	        }
	        else
	        {
	        	ACC.formvalidation.removeErrorClassForRegForm('#user\\.phonenumber', '#errorPhoneNumber', '#user\\.phonenumber\\.errors');
	        }
	        
	        
			$('*[id*=error]:visible').each(function() 
			{
				/*if($(this).html() != '' &&  $(this).html().toLowerCase().indexOf("An account already exists") == 0)*/
				if($(this).html() != '')
				{
					status = false;
				}
			});
			
			if(status)	
			{
				if($("#placeOrdersApproval").length)
				{ //if not the old scenario
                    ACC.formvalidation.ordersApproval(); // Checking Orders Approval
                }
				if(validateEmail)
				{
					ACC.briteverify.validateEmailWithBriteVerify('#user\\.email', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.global.email, $('#user\\.email').val(), '#siteOneCreateUserForm');
				}
				else
				{
					
					$('#siteOneCreateUserForm').submit();
				}
			}
			else
			{
				ACC.formvalidation.addGlobalError();
			}
		});				
					
	},
	retainValueformContractor: function () {
		$("#firstName").val($(".firstName").val());
		$("#lastName").val($(".lastName").val());
		$("#emailaddress").val($(".emailaddress").val());
		$(".companyphone").val($("#phoneNumber").val());
	},
	retainValueformHomeowner: function () {
		$(".firstName").val($("#firstName").val());
		$(".lastName").val($("#lastName").val());
		$(".emailaddress").val($("#emailaddress").val());
		$("#phoneNumber").val($(".companyphone").val());
	},
	createReqPopupContent: function(addressVerificationData) {
		var popupContent;
		if (!addressVerificationData.isAddressValid) {
			popupContent = "<p class='m-b-15'>" + ACC.config.veryfyAddressError + "</p><button class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.editAddress + "</button>";
		}
		else if (addressVerificationData.isAddressCorrected) {
			popupContent = "<form id='addressSuggestionForm' class='row addressSuggestionradio' style='width:450px;'><div class='col-md-6 col-xs-12 no-padding-xs'><label class='colored-radio bold flex-center'><input type='radio' value='originalAddress' name='address.addressSuggestionOption'/><span class='m-t-5'>" + ACC.config.originalAddress + "</span></label><div class='cl'></div><div class='pad-lft-25 marginTop10'><p class='m-b-0'>" + $("#siteOneRequestAccountForm #addressLine1").val() + "</p>";
			if ($("#siteOneRequestAccountForm #addressLine2").val() != "") {
				popupContent += "<p class='m-b-0'>" + $("#siteOneRequestAccountForm #addressLine2").val() + "</p>";
			}
			popupContent += "<p class='m-b-0'>" + $("#siteOneRequestAccountForm #city").val() + "</p><p class='m-b-0'>" + $("#siteOneRequestAccountForm #state option:selected").text() + "</p><p class='m-b-0'>" + $("#siteOneRequestAccountForm #zipcode").val() + "</p></div></div>";
			popupContent += "<div class='col-md-6 col-xs-12 m-t-20-xs no-padding-xs'><label class='colored-radio bold flex-center'><input type='radio' value='suggestedAddress' checked='checked'  name='address.addressSuggestionOption'/><span class='m-t-5'>" + ACC.config.suggestedAddress + "</span></label><div class='cl'></div>";
			popupContent += "<div class='pad-lft-25 marginTop10'><p class='m-b-0'>" + addressVerificationData.street + "</p>";
			popupContent += "<p class='m-b-0'>" + addressVerificationData.street2 + "</p>";
			popupContent += "<p class='m-b-0'>" + addressVerificationData.city + "</p>";
			popupContent += "<p class='m-b-0'>" + addressVerificationData.state + "</p>";
			popupContent += "<p class='m-b-0 hidden'>" + addressVerificationData.county + "</p>";
			popupContent += "<p class='m-b-0'>" + addressVerificationData.zipcode + "</p></div></div>";
			popupContent += "<div class='cl'></div><div class='col-md-offset-2 col-md-4 col-sm-6 col-xs-4 margin-top-20 m-t-10-xs'><div class='btn btn-primary btn-block' onclick='ACC.formvalidation.suggestedValidReqAddressSubmit(\"" + addressVerificationData.street + "\",\"" + addressVerificationData.street2 + "\",\"" + addressVerificationData.city + "\",\"" + addressVerificationData.state + "\",\"" + addressVerificationData.zipcode + "\")'>" + ACC.config.save + "</div></div><div class='col-md-4 col-sm-6 col-xs-4 margin-top-20 m-t-10-xs'><div class='btn btn-default btn-block' onclick='ACC.colorbox.close()'>" + ACC.config.cancel + "</div></div></form>";
		}
		return popupContent;
	},
	suggestedValidReqAddressSubmit: function(line1, line2, city, state, zip) {
		if ($("input[name='address\\.addressSuggestionOption']:checked").val() == "suggestedAddress") {
			$("#siteOneRequestAccountForm #addressLine1").val(line1);
			$("#siteOneRequestAccountForm #addressLine2").val(line2);
			$("#siteOneRequestAccountForm #city").val(city);
			$("#siteOneRequestAccountForm #state").val("US-" + state);
			$("#siteOneRequestAccountForm #zipcode").val(zip);
		}
		ACC.colorbox.close();
		loading.start();
		ACC.formvalidation.validateEmailInHybrisResponse();
	},
	analyticsDataSet: function(isSuccess, from) {
		let uuid = $(".application-uuid").val();
		_AAData.pathingChannel = "company service";
		_AAData.pathingPageName = "company service: online account request confirmation";
		_AAData.eventType = "acct-request-confirmation";
		_AAData.accountType = "Account type requested -Homeowner/Retail or Contractor";
		_AAData.applicationuuid = (isSuccess && $.trim(uuid) != "") ? uuid : "";
		try {
			_satellite.track('acct-create-confirmation');
			_satellite.track('pageload');
	
		} catch (e) { }
		$(window).scrollTop(0);
	},
	placeOrdersApproval: function(){
		if($("#placeOrdersWithApproval").val() == "false"){	//Place Orders
			$("#placeOrders").prop("checked", true);
		}
		else if($("#placeOrdersWithApproval").val() == "true"){	//Place Orders with Approval
			$("#placeOrdersApproval").prop("checked", true);
		}
		else {	//Cannot Place Orders
			$("#placeOrdersCanNot").prop("checked", true);
		}
		 
	},
	ordersApproval: function(){
		if($("#placeOrdersApproval").prop("checked")){	//Order with approval
			$("[id='Place orders']").prop("checked", false);
			$("#placeOrdersWithApproval").val("true");
		}
		else{	//without approval
			$("[id='Place orders']").prop("checked", ($("#placeOrders").prop("checked")?true:false));
			$("#placeOrdersWithApproval").val("false");
		}
		$("#placeOrdersApproval").prop("checked", false);
		$("#placeOrders").prop("checked", false);
		$("#placeOrdersCanNot").prop("checked", false);
	}
};
var captcha_onclick = function(response) {
	if (recaptcha.length == 0)
    {
	 $("#recaptcha-error").removeClass('hidden');
	 $("#recaptcha-border").addClass('recaptcha-error-border');
    }
    else 
    {
      $("#recaptcha-error").addClass('hidden');
      $("#recaptcha-border").removeClass('recaptcha-error-border');
        
    }
};