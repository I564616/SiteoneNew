ACC.briteverify = {

    _autoload: [
        "bindEmailValidation",
        "pointsForEquipmentFormSubmit",
        "contactUsFormSubmit",
        "leadFormSubmit",
        "requestForAccountFormSubmit",
        "emailSignUpPage",
        "siteOneHomeOwnerForm",
        "inspireForm",
        "privacyRequestFormSubmit"
    ],
    
    vaildateEmailFields: function (input, error, errorText, msg, response)
{
    var valid = false;
    if(response == "invalid" || response == "timeout" || response == "error")
    {
    ACC.formvalidation.addErrorClassForRegForm(input, error, errorText, msg);
    ACC.formvalidation.addGlobalError();
    }
    else
    {
    ACC.formvalidation.removeErrorClassForRegForm(input, error, errorText);
    valid = true;
    }
    return valid;
},
reCaptcha: function (response)
{
if (response.length == 0)
    {
$("#recaptcha-error").removeClass('hidden');
$("#recaptcha-border").addClass('recaptcha-error-border');
        }
else if($('#recaptchaChallengeAnswered').val() == 'false')
{
$("#recaptcha-error").removeClass('hidden');
$("#recaptcha-border").addClass('recaptcha-error-border');
}
        else 
        {
          $("#recaptcha-error").addClass('hidden');
          $("#recaptcha-border").removeClass('recaptcha-error-border');
        
        }
},
    
    requestForAccountFormSubmit: function ()
    {
        $('.page-requestaccount [data-toggle="tooltip"]').tooltip();
         
        $(document).find("#emailaddress").prop('type', 'email');
          $(document).find("#phoneNumber").prop('type', 'tel');
           
    },
    
    validateEmailWithBriteVerify: function (input, error, errorText, msg, value, formId)
{
   
    var briteverify= document.getElementById('briteVerifyEnable').value;
var briteverifytimeout= document.getElementById('briteVerifyTimeout').value;
if(briteverify=="true")
{
loading.start();
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+value;
            
$.ajax({
url: url,
type: "GET",
crossDomain: true,
    timeout:briteverifytimeout,
success: function (response)
{
if(response == "invalid")
{
loading.stop();
var msg = ACC.config.emailAddressError1;
ACC.briteverify.vaildateEmailFields(input, error, errorText, msg, response);
ACC.formvalidation.addGlobalError();
} 
else
  {
//ACC.briteverify.vaildateEmailFields(input, error, errorText, msg, response.status);
if(formId != null && formId != '' && formId != undefined)
{
if(formId == '#siteOneLeadGenerationForm')
{
ewt.trackFormSubmit({name:'lead_generation_form_submit',type:'lead_generation',form:$('#siteOneLeadGenerationForm')});
}
$(formId).submit();

/* For Harscape Analytics*/
var harscapeContactPathname = window.location.pathname;

if(harscapeContactPathname == '/en/contactus/hardscapes')
	 {
digitalData.event="contact us form submit";
digitalData.eventData={
		formName:"Hardcapes"  
    }
try {
	_satellite.track("contactUsFormSubmit");
	}catch(e){} 

	 }

}
  }
},
error: function(response, textstatus, message) 
{
  if(textstatus == "timeout" || textstatus == "error") 
  {
  loading.stop();
  var msg = ACC.config.emailAddressUnable;
  ACC.briteverify.vaildateEmailFields(input, error, errorText, msg, textstatus);
  ACC.formvalidation.addGlobalError();
      } 
}
});
}
else
{
$(formId).submit();
}
},
    
    bindEmailValidation: function () 
    {
    $(document).on("focusin", "#emailFooter",function(e)
     {
$(".message").empty();
     });

    $(document).on("click", "#signUpFooter",function(e)
         {
    var briteverify= document.getElementById('briteVerifyEnable').value;

    $(".message").html("");
    if($("#emailFooter").val() == "" || $("#emailFooter").val()  == null)
    {
    $(".message").html("<div class='bg-danger'><div class='panel-body'><font color='red'>" + ERRORMSG.global.email + "</font></div></div>");
    }

    else if(briteverify=="true")
{
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+$('#emailFooter').val();

                    loading.start();
$.ajax({
      url: url,
      type: "get",
      timeout: 5000,
      success: function (response)
      {
      if(response !="invalid")
      {
      ACC.briteverify.emailSignUpFooter($("#emailFooter").val()).always(function(){
                                    loading.stop();
                                });
      }
          else
          {
                                loading.stop();
                                $("#emailFooter").val('');
          $(".message").html("<div class='bg-danger'><div class='panel-body'><font color='red'>" + ERRORMSG.invalid.email + "</font></div></div>");
          }
      },

      error: function(response, textstatus, message)
  {
                            loading.stop();
  if(textstatus == "timeout" || textstatus == "error")
  {
$(".message").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
      }
  }
});
}
    else
    {
                    loading.start();
    ACC.briteverify.emailSignUpFooter($("#emailFooter").val()).always(function(){
                        loading.stop();
                    });
    }

        });

   
    $(document).on("submit", "#emailSignUp",function(e)
    {
        e.preventDefault(); 
        var briteverify= document.getElementById('briteVerifyEnable').value;

    $(".message").html("");
    if($("#emailFooter").val() == "" || $("#emailFooter").val()  == null)
    {
    $(".message").html("<div class='bg-danger'><div class='panel-body'><font color='red'>" + ERRORMSG.global.email + "</font></div></div>");
    }

    else if(briteverify=="true")
{
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+$('#emailIdSignUp').val();
                    
    loading.start();
$.ajax({
      url: url,
      type: "GET",
      timeout: 5000,
      success: function (response)
      {if(response !="invalid")
      {
      ACC.briteverify.emailSignUpFooter($("#emailFooter").val()).always(function(){
                                loading.stop();
                            });
      }
          else
          {

                              loading.stop();
                            $("#emailFooter").val('');
          $(".message").html("<div class='bg-danger'><div class='panel-body'><font color='red'>" + ERRORMSG.invalid.email + "brnksy" + "</font></div></div>");
          }
      },

      error: function(response, textstatus, message)
  {
        loading.stop();
  if(textstatus == "timeout" || textstatus == "error")
  {
$(".message").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
      }
  }
});
}
    else
    {
   
                    loading.start();
    ACC.briteverify.emailSignUpFooter($("#emailFooter").val()).always(function(){
                        loading.stop();
                    });
    }
    });

    },
    
    pointsForEquipmentFormSubmit:function()
    {
    var briteverify= document.getElementById('briteVerifyEnable').value;
      $(document).find("#emailAddress").prop('type', 'email');
      $(document).find("#phoneNumber").prop('type', 'tel');
      $(document).find("#faxNumber").prop('type', 'tel');
       $(document).on('input blur', "#dealerName", function(e)
           {
        ACC.formvalidation.vaildateFields('#dealerName', '#dealerNameError', '#dealerName\\.errors', ACC.config.dealerErrorMsg);
           });
       
       $(document).on('input blur', "#dealerContactName", function(e)
    {
       ACC.formvalidation.vaildateFields('#dealerContactName', '#dealerContactNameError', '#dealerContactName\\.errors', ACC.config.enterContactName+'<div class="cl"></div><br/><br/>');
         
    });
       
       $(document).on('input blur', "#dealerAddressLine1", function(e)
      {
       ACC.formvalidation.vaildateFields('#dealerAddressLine1', '#dealerAddressLine1Error', '#dealerAddressLine1\\.errors', ACC.config.addressInfo+'<div class="cl"></div><br/><br/>');
      });
       
       $(document).on('input blur', "#emailAddress", function(e)
        {
               if(ACC.formvalidation.validateEmailPattern($("#emailAddress").val()))
        {
    ACC.formvalidation.removeErrorClassForRegForm('#emailAddress', '#emailAddressError', '#emailAddress\\.errors');
    }
    else
    {
    ACC.formvalidation.addErrorClassForRegForm('#emailAddress', '#emailAddressError', '#emailAddress\\.errors', ERRORMSG.invalid.email);
    }
       //ACC.formvalidation.vaildateFields('#emailAddress', '#emailAddressError', '#emailAddress\\.errors', 'Please enter an email address.<div class="cl"></div><br/><br/>');
        });
       $(document).on('input blur', "#dealerCity", function(e)
         {
       //  ACC.formvalidation.vaildateFields('#dealerCity', '#dealerCityError', '#dealerCity\\.errors', 'ACC.config.enterCity.<div class="cl"></div><br/><br/>');
         ACC.formvalidation.validateFieldBasedOnRegex(e,'#dealerCity', '#dealerCityError', '#dealerCity\\.errors',ACC.config.briteCityMay,ERRORMSG.global.city);
        
         });
       $(document).on('input blur', "#dealerZipCode", function(e)
        {
       if((ACC.formvalidation.validatezipcode(e, $("#dealerZipCode").val())))
       {
    ACC.formvalidation.removeErrorClassForRegForm('#dealerZipCode', '#dealerZipCodeError', '#dealerZipCode\\.errors');
     }
  else
     {
    ACC.formvalidation.addErrorClassForRegForm('#dealerZipCode', '#dealerZipCodeError', '#dealerZipCode\\.errors', ACC.config.validZip);
     }
       });
       $(document).on('input blur', "#dealerState", function(e)
         {
       ACC.formvalidation.vaildateFields('#dealerState', '#dealerStateError', '#dealerState\\.errors', ACC.config.selectState); 

         });
       
       $(document).on('input blur', "#customerContactName", function(e)
        {
        ACC.formvalidation.vaildateFields('#customerContactName', '#customerContactNameError', '#customerContactName\\.errors', ACC.config.enterFirstName+'<div class="cl"></div><br/><br/>');
        });
           
       $(document).on('input blur', "#companyName", function(e)
        {
              ACC.formvalidation.vaildateFields('#companyName', '#companyNameError', '#companyName\\.errors', ACC.config.enterCompany+'<div class="cl"></div><br/><br/>');
        });
           
       $(document).on('input blur', "#jdlAccountNumber", function(e)
        {
      //ACC.formvalidation.validateAccountNumber(e);
        ACC.formvalidation.validateAccountNumber(e,'#jdlAccountNumber', '#jdlAccountNumberError', '#jdlAccountNumber\\.errors', ACC.config.enterAccountNumber+'<div class="cl"></div><br/><br/>');
        });
           
       $(document).on('input blur', "#customerAddressLine1", function(e)
        {
       ACC.formvalidation.vaildateFields('#customerAddressLine1', '#customerAddressLine1Error', '#customerAddressLine1\\.errors', ACC.config.addressInfo+'<div class="cl"></div><br/><br/>');
        });
       
       $(document).on('input blur', "#customerCity", function(e)
        {
       //ACC.formvalidation.vaildateFields('#customerCity', '#customerCityError', '#customerCity\\.errors', 'Please enter a city.<div class="cl"></div><br/><br/>');
       ACC.formvalidation.validateFieldBasedOnRegex(e,'#customerCity', '#customerCityError', '#customerCity\\.errors',ACC.config.briteCityMay,ERRORMSG.global.city,"city");
   
        });
       
       $(document).on('input blur', "#customerZipCode", function(e)
        {
            if((ACC.formvalidation.validatezipcode(e, $("#customerZipCode").val())))
         {
        ACC.formvalidation.removeErrorClassForRegForm('#customerZipCode', '#customerZipCodeError', '#customerZipCode\\.errors');
      }
          else
      {
      ACC.formvalidation.addErrorClassForRegForm('#customerZipCode', '#customerZipCodeError', '#customerZipCode\\.errors', ACC.config.validZip);
      }
       });       
       $(document).on('input blur', "#customerState", function(e)
        {
        ACC.formvalidation.vaildateFields('#customerState', '#customerStateError', '#customerState\\.errors', ACC.config.selectState); 

        });
       
    $(document).on("blur", "#phoneNumber", function(e)
    {
    var phoneNumber = $(this).val();
    if(phoneNumber != null && phoneNumber != '' && phoneNumber != undefined)
        {
            if (!ACC.formvalidation.validatePhoneNumber(phoneNumber))
                    {
                        var msg=ACC.config.phoneNumberError;
                        ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#phoneNumberError', '#phoneNumber\\.errors', msg);
                    }
                    else
                    {
                        ACC.formvalidation.removeErrorClassForRegForm('#phoneNumber', '#phoneNumberError', '#phoneNumber\\.errors');
                    }
        }
        else
        {
                ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#phoneNumberError', '#phoneNumber\\.errors', ERRORMSG.global.phone);
        }
        });
   
    $(document).on("input blur", "#phoneNumber", function(e)
        {
    $("#phoneNumber").prop('type', 'tel');
         ACC.formvalidation.handlePhoneNumberOnInput('#phoneNumber', '#phoneNumberError', '#phoneNumber\\.errors');
          });
   
    $(document).on("input blur", "#faxNumber", function(e)
            {
       
             ACC.formvalidation.handlePhoneNumberOnInput('#faxNumber', '#faxNumberError', '#faxNumber\\.errors');
              });
       
    /* $(document).on('input blur', "#dateOfPurProduct"+i, function(e)
        {
    ACC.formvalidation.vaildateFields('#dateOfPurProduct'+i, '#dateOfPurProductError'+i, '#customerState\\.errors', 'Please enter date of purchase.<div class="cl"></div><br/><br/>');
     
        });
    $(document).on('input blur', "#itemDescProduct"+i, function(e)
        {
    ACC.formvalidation.vaildateFields('#itemDescProduct'+i, '#itemDescProductError'+i, '#itemDescProduct\\.errors', 'Please enter description of product.<div class="cl"></div><br/><br/>');
     
        });
    $(document).on('input blur', "#serialNumberProductError"+i, function(e)
        {
    ACC.formvalidation.vaildateFields('#serialNumberProduct'+i, '#serialNumberProductError'+i, '#serialNumberProduct\\.errors', 'Please enter serial number of product.<div class="cl"></div><br/><br/>');
     
        });
    $(document).on('input blur', "#invoiceCostProductError"+i, function(e)
         {
    ACC.formvalidation.vaildateFields('#invoiceCostProduct'+i, '#invoiceCostProductError'+i, '#invoiceCostProduct\\.errors', 'Please enter invoice cost product.<div class="cl"></div><br/><br/>');
     
    });
           
    */
    $(document).on("click", ".pointsForEquipment", function (e)
        {
        e.preventDefault();
        var status = true;
        document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
        ACC.briteverify.reCaptcha(grecaptcha.getResponse());
        if($("#dealerContactName").val() == "" || $("#dealerContactName").val() == null)
        {
        status = false;
        //$("#dealerContactNameError").html("ACC.config.enterContactName.");
        ACC.formvalidation.addErrorClassForRegForm('#dealerContactName', '#dealerContactNameError', '#dealerContactName\\.errors', ACC.config.enterContactName+'<div class="cl"></div><br/><br/>');
             
        }
        else
        {
        $("#dealerContactNameError").empty();
        }
       
        if($("#dealerName").val() == "" || $("#dealerName").val() == null)
        {
        status = false;
        //$("#dealerNameError").html("<div class='bg-danger' style='margin-top:20px;'><div class='panel-body'><font color='red'>Please enter dealer name.</font></div></div>");
        ACC.formvalidation.addErrorClassForRegForm('#dealerName', '#dealerNameError', '#dealerName\\.errors', ACC.config.dealerErrorMsg+'<div class="cl"></div><br/><br/>');
               
        }
        else
    {
    $("#dealerNameError").empty();
    }
   
       
        if($("#dealerAddressLine1").val() == "" || $("#dealerAddressLine1").val() == null)
        {
        status = false;
        //$("#dealerAddressLine1Error").html(ERRORMSG.global.address);
        ACC.formvalidation.addErrorClassForRegForm('#dealerAddressLine1', '#dealerAddressLine1Error', '#dealerAddressLine1\\.errors', ACC.config.addressInfo+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#dealerAddressLine1Error").empty();
    }
          
        if($("#phoneNumber").val() == "" || $("#phoneNumber").val() == null)
        {
        status = false;
        //$("#phoneNumberError").html(ERRORMSG.global.phone);
        ACC.formvalidation.addErrorClassForRegForm('#phoneNumber', '#phoneNumberError', '#phoneNumber\\.errors', ACC.config.validPhoneNumber+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#phoneNumberError").empty();
    }
        if($("#emailAddress").val() == "" || $("#emailAddress").val() == null)
        {
        status = false;
        //$("#emailAddressError").html(ERRORMSG.global.email);
        ACC.formvalidation.addErrorClassForRegForm('#emailAddress', '#emailAddressError', '#emailAddress\\.errors', ACC.config.dashboardValidEmail+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#emailAddressError").empty();
    }
        if($("#dealerCity").val() == "" || $("#dealerCity").val() == null)
        {
        status = false;
        //$("#dealerCityError").html(ERRORMSG.global.city);
        ACC.formvalidation.addErrorClassForRegForm('#dealerCity', '#dealerCityError', '#dealerCity\\.errors', ACC.config.enterCity+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#dealerCityError").empty();
    }
        if($("#dealerZipCode").val() == "" || $("#dealerZipCode").val() == null)
        {
        status = false;
        //$("#dealerZipCodeError").html(ERRORMSG.global.zip);
        ACC.formvalidation.addErrorClassForRegForm('#dealerZipCode', '#dealerZipCodeError', '#dealerZipCode\\.errors', ACC.config.validZipPostal+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#dealerZipCodeError").empty();
    }
       
        if($("#dealerState").val() == "" || $("#dealerState").val() == null)
        {
        status = false;
        ACC.formvalidation.addErrorClassForRegForm('#dealerState', '#dealerStateError', '#dealerState\\.errors', 'Please select a state.<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#dealerStateError").empty();
    }
       
       
        if($("#customerContactName").val() == "" || $("#customerContactName").val() == null)
        {
        status = false;
        //$("#customerContactNameError").html(ERRORMSG.global.firstname);
        ACC.formvalidation.addErrorClassForRegForm('#customerContactName', '#customerContactNameError', '#customerContactName\\.errors', ACC.config.enterFirstName+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#customerContactNameError").empty();
    }
        if($("#companyName").val() == "" || $("#companyName").val() == null)
        {
        status = false;
        //$("#companyNameError").html("ACC.config.enterCompany");
        ACC.formvalidation.addErrorClassForRegForm('#companyName', '#companyNameError', '#companyName\\.errors', ACC.config.enterCompany+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#companyNameError").empty();
    }
        if($("#jdlAccountNumber").val() == "" || $("#jdlAccountNumber").val() == null)
        {
        status = false;
        //$("#jdlAccountNumberError").html("ACC.config.enterAccountNumber);
        //ACC.formvalidation.validateAccountNumber(e);
        ACC.formvalidation.addErrorClassForRegForm('#jdlAccountNumber', '#jdlAccountNumberError', '#jdlAccountNumber\\.errors', ACC.config.enterAccountNumber+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#jdlAccountNumberError").empty();
    }
        if($("#customerAddressLine1").val() == "" || $("#customerAddressLine1").val() == null)
        {
        status = false;
        //$("#customerAddressLine1Error").html(ERRORMSG.global.address);
        ACC.formvalidation.addErrorClassForRegForm('#customerAddressLine1', '#customerAddressLine1Error', '#customerAddressLine1\\.errors', ACC.config.addressInfo+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#customerAddressLine1Error").empty();
    }
        if($("#customerCity").val() == "" || $("#customerCity").val() == null)
        {
        status = false;
        //$("#customerCityError").html(ERRORMSG.global.city);
        ACC.formvalidation.addErrorClassForRegForm('#customerCity', '#customerCityError', '#customerCity\\.errors', ACC.config.enterCity+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#customerCityError").empty();
    }
        if($("#customerZipCode").val() == "" || $("#customerZipCode").val() == null)
        {
        status = false;
        //$("#customerZipCodeError").html("<div class='bg-danger' style='margin-top:20px;'><div class='panel-body'><font color='red'>" + ERRORMSG.global.zip + "</font></div></div>");
        ACC.formvalidation.addErrorClassForRegForm('#customerZipCode', '#customerZipCodeError', '#customerCity\\.errors', ACC.config.dashboardValidZip+'<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#customerZipCodeError").empty();
    }
       
        if($("#customerState").val() == "" || $("#customerState").val() == null)
        {
        status = false;
        ACC.formvalidation.addErrorClassForRegForm('#customerState', '#customerStateError', '#customerState\\.errors', 'Please select a state.<div class="cl"></div><br/><br/>');
        }
        else
    {
    $("#customerStateError").empty();
    }
        $('div.product_info:visible').each(function(i, el){
        i=i+1;
        var dateOfPurchase=$(this).find("#dateOfPurProduct"+i).val();
        var itemDescription=$(this).find("#itemDescProduct"+i).val();
        var serialNumber=$(this).find("#serialNumberProduct"+i).val();
        var invoiceCost=$(this).find("#invoiceCostProduct"+i).val();
        if(dateOfPurchase == "" || dateOfPurchase == null)
            {
            status = false;
            //$("#dateOfPurProductError"+i).html("Please enter date of purchase.");
            ACC.formvalidation.addErrorClassForRegForm('#dateOfPurProduct'+i, '#dateOfPurProductError'+i, '#customerState\\.errors', ACC.config.purchaseDate+'<div class="cl"></div><br/><br/>');
               
           
            }
            else
        {
        $("#dateOfPurProductError"+i).empty();
        }
            if(itemDescription == "" || itemDescription== null)
            {
            status = false;
            //$("#itemDescProductError"+i).html("Please enter description of product");
            ACC.formvalidation.addErrorClassForRegForm('#itemDescProduct'+i, '#itemDescProductError'+i, '#itemDescProduct\\.errors', ACC.config.productDescription+'<div class="cl"></div><br/><br/>');
               
           
            }
            else
        {
        $("#itemDescProductError"+i).empty();
        }
                   
            if(serialNumber == "" || serialNumber == null)
            {
            status = false;
            //$("#serialNumberProductError"+i).html("Please enter serial number of product.");
            ACC.formvalidation.addErrorClassForRegForm('#serialNumberProduct'+i, '#serialNumberProductError'+i, '#serialNumberProduct\\.errors', ACC.config.serialNumber+'<div class="cl"></div><br/><br/>');
               
            }
            else
        {
        $("#serialNumberProductError"+i).empty();
        }
           
            if(invoiceCost == "" || invoiceCost == null)
            {
            status = false;
            //$("#invoiceCostProductError"+i).html("Please enter invoice cost product.");
            ACC.formvalidation.addErrorClassForRegForm('#invoiceCostProduct'+i, '#invoiceCostProductError'+i, '#invoiceCostProduct\\.errors', ACC.config.invoiceCost+'<div class="cl"></div><br/><br/>');
               
            }
            else
        {
        $("#invoiceCostProductError"+i).empty();
        }
        });
       
           
        if(status)
        {
                loading.start();
        if(briteverify=="true")
        {
        url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+$('#emailAddress').val();
        $.ajax({
                url: url,
                type: "GET",
            timeout: 5000,
            success: function (response)
            {
            if(response == "invalid")
            {
                          loading.stop();
            $("#emailAddressError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>Please enter a valid email address.</div></font></div>");   
            }
            else
            {
            if(status)
            {
            $("#siteOnePointsForEquipmentForm").submit();
            }
            }
            },
           
            error: function(response, textstatus, message) 
          {
                        loading.stop();
          if(textstatus == "timeout" || textstatus == "error") 
          {
        $("#emailAddressError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
              } 
          }
                });
        }
        else
    {
    $("#siteOnePointsForEquipmentForm").submit();
   
    }
        }
        else
    {
        /*window.scrollTo(500, 0);*/
            ACC.formvalidation.addGlobalError();
    }
        });
       
    },
    
    contactUsFormSubmit: function ()
    {
        $(document).find("#phonenumbercontact").prop('type', 'tel');
        $(document).find("#contactUsFormEmail").prop('type', 'email');
        $(document).on("input blur", "#phonenumbercontact", function(e)
        {

            ACC.formvalidation.handlePhoneNumberOnInput('#phonenumbercontact', '#errorphonenumber', '#phoneNumber\\.errors');
        });
      
        $(document).on('input blur', "#user\\#firstName", function(e)
        {
            ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\#firstName', '#errorfirstname', '#firstName\\.errors',ACC.config.nameMayInclude,ACC.config.firstName);
        });
      
        $(document).on("keypress","#streetAddress, #contact-us-city, #message", function(e) {
            if (e.which === 32 && !this.value.length)
            e.preventDefault();
        });
      
        $(document).on('input blur', "#user\\#lastName", function(e)
        {
            ACC.formvalidation.validateFieldBasedOnRegex(e,'#user\\#lastName', '#errorlastname', '#lastName\\.errors',ACC.config.nameMayInclude,ACC.config.lastName);
        });
      
        $(document).on('input blur', "#message", function(e)
        {
            ACC.formvalidation.vaildateFields('#message', '#errormessage', '#message\\.errors',ACC.config.plsEnterMsg);
        });
        $(document).on('input blur', "#reasonForContact", function(e)
        {
            ACC.formvalidation.vaildateFields('#reasonForContact', '#errorselectreason', '#reasonForContact\\.errors', ACC.config.selectReason); 
        }); 
        var oldphaseof=$('#inPhaseOf').clone().html();
        var oldproject =$('#myProject').clone().html();
	    $(document).on('change', "#reasonForContact", function(e) {
	
            if ( $('#contactusHardscapes').val()=== "false" && this.selectedIndex==2) {
                window.location.href = ACC.config.encodedContextPath + '/request-account/form';
            }
            // SE-29401 Nursery
            if ( $('#contactusHardscapes').val()=== "false" && this.selectedIndex==3) {
                window.location.href = ACC.config.encodedContextPath + '/nursery-form';
            }
            if ( $('#contactusHardscapes').val()=== "false" && this.selectedIndex==11) {
                window.location.href = ACC.config.encodedContextPath + '/contactus/hardscapes';
            }
            if($('.lightingEmailContactUs').val()==="true" && this.selectedIndex==3) {
            
                $.ajax({
                    type: "GET",
                    url: ACC.config.encodedContextPath + "/contactus/lighting/outdoor",
                    dataType: "json",
                    success: function (response)
                    {
                        console.log(response);
                        var outdoorPhaseof = response.phaseOf;
                        var outdoorProject = response.project;
                        
                        var $el = $('#inPhaseOf');
                        var $ele = $('#myProject');
                        $el.empty();
                        $ele.empty();
                        $el.append($("<option></option>").attr("value","select").text("Select"));
                        $ele.append($("<option></option>").attr("value","select").text("Select"));
                        $.each(outdoorPhaseof, function(key,value){
                            $el.append($("<option></option>").attr("value",value).text(value));
                        });
                        $.each(outdoorProject, function(key, value){
                            $ele.append($("<option></option>").attr("value",value).text(value));
                        });
                    },
                    error: function (error)
                    {
                        console.log("failed");
                    }
                });
            }
            else {
                var $el = $('#inPhaseOf');
                var $ele = $('#myProject');
                $el.empty();
                $ele.empty();
                $el.append(oldphaseof);
                $ele.append(oldproject);
            }
	    }); 
     
        $(document).on("input blur", "#contactUsFormEmail", function()
        {
      
            if(ACC.formvalidation.validateEmailPattern($("#contactUsFormEmail").val()))
            {
                ACC.formvalidation.removeErrorClassForRegForm('#contactUsFormEmail', '#erroremail', '#contactUsFormEmail\\.errors');
            }
            else
            {
                ACC.formvalidation.addErrorClassForRegForm('#contactUsFormEmail', '#erroremail', '#contactUsFormEmail\\.errors', ERRORMSG.invalid.email);
            }
        
        });


        $(document).on("input blur", "#contact-us-city", function(e)
        {
     
            if(ACC.formvalidation.vaildateFields('#contact-us-city'))
            {
    
                ACC.formvalidation.removeErrorClassForRegForm('#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors');
                ACC.formvalidation.validateFieldBasedOnRegex(e, '#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors', ACC.config.briteCityMay, ERRORMSG.global.city,"city");

            }
            else
            {
                ACC.formvalidation.addErrorClassForRegForm('#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors', ERRORMSG.global.city);
            }
                   
        });
      
	  
	    if ($('#siteOneContactUsForm').length > 0) {
            $(document).on("input blur", "#streetAddress", function()
            {
                var msg = ERRORMSG.global.address;
                ACC.formvalidation.vaildateFields('#streetAddress', '#errorStreetAddress', '#streetAddress\\.errors', msg);
            });
        }
        $(document).on("blur", "#customerState", function()
        {

            if(ACC.formvalidation.vaildateFields('#customerState'))
            {
                ACC.formvalidation.removeErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors');
            }
            else
            {
                ACC.formvalidation.addErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors', ACC.config.selectState);
            }

        });
        $(document).on("click", ".contactUs", function (e)
        {
            var status = true;
            e.preventDefault();
            document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
            ACC.briteverify.reCaptcha(grecaptcha.getResponse());
        
            ACC.formvalidation.vaildateFields('#reasonForContact', '#errorselectreason', '#reasonForContact\\.errors', ACC.config.selectReason); 
            ACC.formvalidation.vaildateFields('#firstName', '#errorfirstname', '#firstName\\.errors',ERRORMSG.global.firstname);
            ACC.formvalidation.vaildateFields('#lastName', '#errorlastname', '#lastName\\.errors',ERRORMSG.global.lastname);
            if ($('#siteOneContactUsForm').length > 0) {
                ACC.formvalidation.vaildateFields('#streetAddress', '#errorStreetAddress', '#streetAddress\\.errors', ERRORMSG.global.address);
            }
            ACC.formvalidation.vaildateFields('#message', '#errormessage', '#message\\.errors', ACC.config.plsEnterMsg);
 
            if(null != $("#phonenumbercontact").val() && $("#phonenumbercontact").val() != '')
            {
                if(!ACC.formvalidation.validatePhoneNumber($("#phonenumbercontact").val()))
                {
                    status = false;
                    ACC.formvalidation.addErrorClassForRegForm('#phonenumbercontact', '#errorphonenumber', '#phoneNumber\\.errors', ERRORMSG.global.phone);
                }
                else
                {
                    ACC.formvalidation.removeErrorClassForRegForm('#phonenumbercontact', '#errorphonenumber', '#phoneNumber\\.errors');
                }
            }
            else
            {
                status = false;
                ACC.formvalidation.addErrorClassForRegForm('#phonenumbercontact', '#errorphonenumber', '#phoneNumber\\.errors', ACC.config.phoneNumber);
            }
 
            if(ACC.formvalidation.validateEmailPattern($("#contactUsFormEmail").val()))
            {
                ACC.formvalidation.removeErrorClassForRegForm('#contactUsFormEmail', '#erroremail', '#contactUsFormEmail\\.errors', ERRORMSG.invalid.email);
            }
            else
            {
                status = false;
                ACC.formvalidation.addErrorClassForRegForm('#contactUsFormEmail', '#erroremail', '#contactUsFormEmail\\.errors', ERRORMSG.invalid.email);
            }
 
            if ($('.contactus-form').length > 0) {
                if(!$(".typeOfCustomer").is(':checked')){
                    status = false;
                    $("#typeOfCustomerError").html(ACC.config.chooseCustomerType);
                }
                else{
                    $("#typeOfCustomerError").empty();
                } 
            }  
            $('*[id*=error]:visible').each(function(){
                if($(this).html() != '')
                {
                    status = false;
                }
            });
   
    
      
			var currentBaseStoreId = $('.currentBaseStoreId').val();
     
			if(currentBaseStoreId == 'US'){
            if(ACC.formvalidation.validatezipcode(e, $("#projectZipcode").val())){
                ACC.formvalidation.removeErrorClassForRegForm('#projectZipcode', '#errorprojectZipcode', '#projectZipcode\\.errors');
            }
            else{
                status = false;
                ACC.formvalidation.addErrorClassForRegForm('#projectZipcode', '#errorprojectZipcode', '#projectZipcode\\.errors', ACC.config.validZip);
            }
        
            if (ACC.formvalidation.vaildateFields('#customerState')) {
                ACC.formvalidation.removeErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors');
            }
            else {
                status = false;
                ACC.formvalidation.addErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors', ACC.config.selectState);
            }
     		}

            if (ACC.formvalidation.vaildateFields('#contact-us-city')){
                ACC.formvalidation.removeErrorClassForRegForm('#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors');
                ACC.formvalidation.validateFieldBasedOnRegex(e, '#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors', ACC.config.briteCityMay, ERRORMSG.global.city,"city");

            }
            else{
                status = false;
                ACC.formvalidation.addErrorClassForRegForm('#contact-us-city', '#errorCustomerCity', '#contact-us-city\\.errors', ERRORMSG.global.city);
            }
       

          
     
     
     
    
            if(status)
            {
                ACC.briteverify.validateEmailWithBriteVerify('#contactUsFormEmail', '#erroremail', '#email\\.errors', ERRORMSG.global.email, $('#contactUsFormEmail').val(), '#siteOneContactUsForm');
            }
            else
            {
                /*window.scrollTo(500, 0);*/
                ACC.formvalidation.addGlobalError();
            }
   
        });
    
    
   	    $(document).on("input blur", "#projectZipcode", function(e)
        {	
            if(ACC.formvalidation.validatezipcode(e, $("#projectZipcode").val()))
            {
                ACC.formvalidation.removeErrorClassForRegForm('#projectZipcode', '#errorprojectZipcode', '#projectZipcode\\.errors');
            }
            else
            {
                ACC.formvalidation.addErrorClassForRegForm('#projectZipcode', '#errorprojectZipcode', '#projectZipcode\\.errors',ACC.config.zipCodeError);
            }
        });
  
   
    },
    
    
    leadFormSubmit: function()
    {
    $(document).on("input blur", "#email", function(e)
{
if(ACC.formvalidation.validateEmailPattern($("#email").val()))
    {
ACC.formvalidation.removeErrorClassForRegForm('#email', '#errorEmail', '#emailaddress\\.errors');
}
else
{
ACC.formvalidation.addErrorClassForRegForm('#email', '#errorEmail', '#emailaddress\\.errors', ERRORMSG.invalid.email);
}
});
    $(document).on("input blur", "#zipCode", function(e)
    {
    if(ACC.formvalidation.validatezipcode(e, $("#zipCode").val()))
{
ACC.formvalidation.removeErrorClassForRegForm('#zipCode', '#errorZipCode', '#zipCode\\.errors');
}
else
{
ACC.formvalidation.addErrorClassForRegForm('#zipCode', '#errorZipCode', '#zipCode\\.errors', ACC.config.validZip);
}
    });
    $(document).on("input", "#leadPhone", function(e)
    {
    ACC.formvalidation.handlePhoneNumberOnInput('#leadPhone', '#errorPhone', '#phone\\.errors');
    });
   
    $(document.body).on("change","#optings",function()
        {
      if($("#optings").prop("checked") == false)
      {
    $("#optingsError").html("<font color='red'>Please check the box to confirm your interest.</font><br/>");
      }
    else
      {
    $("#optingsError").html("");
      }
        });
     
$(document).on("click",".unlockmore",function(e)
        {
var status = true;
 
var briteverify= document.getElementById('briteVerifyEnable').value;
 
var briteverifytimeout= document.getElementById('briteVerifyTimeout').value;
ACC.formvalidation.validateFieldBasedOnRegex(e,'#firstName', '#errorFirstName', '#firstName\\.errors',ACC.config.nameMayInclude,ERRORMSG.global.firstname);
ACC.formvalidation.validateFieldBasedOnRegex(e,'#lastName', '#errorLastName', '#lastName\\.errors',ACC.config.nameMayInclude,ERRORMSG.global.lastname);
ACC.formvalidation.vaildateFields('#companyName', '#errorCompanyName', '#company\\.errors', "Please enter a company name");
 
if(ACC.formvalidation.validateEmailPattern($("#email").val()))
     {
ACC.formvalidation.removeErrorClassForRegForm('#email', '#errorEmail', '#emailaddress\\.errors');
}
else
{
status = false;
ACC.formvalidation.addErrorClassForRegForm('#email', '#errorEmail', '#emailaddress\\.errors', ERRORMSG.invalid.email);
}
 
if(null != $("#leadPhone").val() && $("#leadPhone").val() != '' && !ACC.formvalidation.validatePhoneNumber($("#leadPhone").val()))
{
    status = false;
    ACC.formvalidation.addErrorClassForRegForm('#leadPhone', '#errorPhone', '#phone\\.errors', ERRORMSG.global.phone);
}
else
{
ACC.formvalidation.removeErrorClassForRegForm('#leadPhone', '#errorPhone', '#phone\\.errors');
}
 
if(ACC.formvalidation.validatezipcode(e, $("#zipCode").val()))
{
ACC.formvalidation.removeErrorClassForRegForm('#zipCode', '#errorZipCode', '#zipCode\\.errors');
}
else
{
status = false;
ACC.formvalidation.addErrorClassForRegForm('#zipCode', '#errorZipCode', '#zipCode\\.errors', ACC.config.validZip);
}
 
$("#optingsError").html("");
if(!$("#optings").is(':checked'))
{
status = false;
$("#optingsError").html("<font color='red'>Please check the box to confirm your interest.</font><br/>");
}
else
{
ACC.formvalidation.removeErrorClassForRegForm('#optings', '#optingsError', '#optings\\.errors');
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
ACC.briteverify.validateEmailWithBriteVerify('#email', '#errorEmail', '#Email\\.errors', ERRORMSG.global.email, $('#email').val(), '#siteOneLeadGenerationForm');
}
else
{
ACC.formvalidation.addGlobalError();
}
 
        });
    },

    emailSignUpPage: function()
    {
        $(document).on("input blur", "#emailIdSignUp", function()
{
        if(ACC.formvalidation.validateEmailPattern($("#emailIdSignUp").val()))
    {
ACC.formvalidation.removeErrorClassForRegForm('#emailIdSignUp', '#emailErrors', '#emailIdSignUp\\.errors');
}
else
{
ACC.formvalidation.addErrorClassForRegForm('#emailIdSignUp', '#emailErrors', '#emailIdSignUp\\.errors', ERRORMSG.invalid.email);
}
        
    });
    $(document).on("click", "#signUpPage", function (e) 
{
    var status = true;
   
    if(ACC.formvalidation.validatezipcode(e,$('#zipcode').val()))
    {
    ACC.formvalidation.removeErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors', ACC.config.validZip);
    }
    else
    {
    status = false;
    ACC.formvalidation.addErrorClassForRegForm('#zipcode', '#errorZipcode', '#zipcode\\.errors', ACC.config.validZip);
    }
   
    if(ACC.formvalidation.validateEmailPattern($("#emailIdSignUp").val()))
    {
ACC.formvalidation.removeErrorClassForRegForm('#emailIdSignUp', '#emailErrors', '#emailIdSignUp\\.errors');
}
else
{
status = false;
ACC.formvalidation.addErrorClassForRegForm('#emailIdSignUp', '#emailErrors', '#emailIdSignUp\\.errors', ERRORMSG.invalid.email);
}
   
    $("#agreeError").html("");
    if(!$("#agree").is(':checked'))
    {
    status = false;
    $("#agreeError").html("<font color='red'>Please check the box to confirm your interest.</font><br/>");
    }
   
var briteverify= document.getElementById('briteVerifyEnable').value;
var briteverifytimeout= document.getElementById('briteVerifyTimeout').value;
   
if(status)
{
if(briteverify == "true")
    {
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+$('#emailIdSignUp').val();

                    loading.start();
$.ajax({
      url: url,
      type: "GET",
      timeout: briteverifytimeout,
      success: function (response)
      {
      if(response !="invalid")
      {
          ewt.trackFormSubmit({name:'emailsignup _form_submit',type:'email_sign_up',form:$("#siteOneEmailSignUpForm")});
      $("#siteOneEmailSignUpForm").submit();
      }
          else
          {
                            loading.stop();
          $("#emailErrors").html("<div class='bg-danger'><div class='panel-body'><font color='red'>" + ERRORMSG.invalid.email + "</font></div></div>");
          }
      },
      
      error: function(response, textstatus, message) 
{
                      loading.stop();
  if(textstatus == "timeout" || textstatus == "error") 
  {
$("#emailErrors").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
    } 
  
}
});
    }
    else
    {
                loading.start();
                ewt.trackFormSubmit({name:'emailsignup _form_submit',type:'email_sign_up',form:$("#siteOneEmailSignUpForm")});
    $("#siteOneEmailSignUpForm").submit();
    }
}
   
   
});

    $(document).on("focusin", "#emailIdSignUp",function(e)
{
    $("#emailError").empty();
});

    },
    
    siteOneHomeOwnerForm:function()
    {
    var briteverify= document.getElementById('briteVerifyEnable').value;
   
      $(document).on("input", "#homeownerphone", function(e)
        {
      ACC.formvalidation.handlePhoneNumberOnInput('#homeownerphone', '#errorhomeownerphone', '#phone\\.errors');
        });
     
      $(document).on("blur", "#emailAddr", function()
  {
      if(ACC.formvalidation.validateEmailPattern($("#emailAddr").val()))
      {
  ACC.formvalidation.removeErrorClassForRegForm('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors');
  }
  else
  {
  ACC.formvalidation.addErrorClassForRegForm('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors', ERRORMSG.invalid.email);
  }
          
      });
     
      $(document).on("blur", "#customerCity", function()
      {
     
          if(ACC.formvalidation.vaildateFields('#customerCity'))
          {
    
      ACC.formvalidation.removeErrorClassForRegForm('#customerCity', '#errorcustomercity', '#customerCity\\.errors');
      }
      else
      {
      ACC.formvalidation.addErrorClassForRegForm('#customerCity', '#errorcustomercity', '#customerCity\\.errors', ERRORMSG.global.city);
      }
              
          });
     
      $(document).on("blur", "#customerState", function()
      {
     
          if(ACC.formvalidation.vaildateFields('#customerState'))
          {
      ACC.formvalidation.removeErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors');
      }
      else
      {
      ACC.formvalidation.addErrorClassForRegForm('#customerState', '#errorcustomerstate', '#customerState\\.errors', ACC.config.selectState);
      }
              
          });
     
      $(document).on("blur", "#serviceType", function()
      {
     
          if(ACC.formvalidation.vaildateFields('#serviceType'))
          {
      ACC.formvalidation.removeErrorClassForRegForm('#serviceType', '#errorsrevicetype', '#serviceType\\.errors');
      }
      else
      {
      ACC.formvalidation.addErrorClassForRegForm('#serviceType', '#errorsrevicetype', '#serviceType\\.errors', "Please select the type of service needed.");
      }
              
          });
     
      $(document).on("blur", "#referalsNo", function()
      {
     
          if(ACC.formvalidation.vaildateFields('#referalsNo'))
          {
      ACC.formvalidation.removeErrorClassForRegForm('#referalsNo', '#errorreferalno', '#referalsNo\\.errors');
      }
      else
      {
      ACC.formvalidation.addErrorClassForRegForm('#referalsNo', '#errorreferalno', '#referalsNo\\.errors', "Please specify the number of referrals you would like.");
      }
              
          });
     
     
      $(document).on("blur", "#customerZipCode", function(e)
      {
          if(ACC.formvalidation.validatezipcode(e,$("#customerZipCode").val()))
          {
      ACC.formvalidation.removeErrorClassForRegForm('#customerZipCode', '#errorcustomerzipcode', '#customerZipCode\\.errors');
      }
      else
      {
      ACC.formvalidation.addErrorClassForRegForm('#customerZipCode', '#errorcustomerzipcode', '#customerZipCode\\.errors', ACC.config.validZip);
      }
              
          });
   
   
    $(document).on("click", "#homeowner", function (e)
        {
        e.preventDefault();
        var status = true;
		var currentBaseStoreId = $('.currentBaseStoreId').val();
        document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
        ACC.briteverify.reCaptcha(grecaptcha.getResponse());
       
ACC.formvalidation.vaildateFields('#firstName', '#errorfirstname', '#firstName\\.errors',ACC.config.firstName);
ACC.formvalidation.vaildateFields('#lastName', '#errorlastname', '#lastName\\.errors',ACC.config.firstName); 
//ACC.formvalidation.vaildateFields('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors', ERRORMSG.global.email);
ACC.formvalidation.vaildateFields('#homeownerphone', '#errorhomeownerphone', '#phone\\.errors', ACC.config.firstName);
ACC.formvalidation.vaildateFields('#customerCity', '#errorcustomercity', '#customerCity\\.errors', ERRORMSG.global.city);

ACC.formvalidation.vaildateFields('#customerZipCode', '#errorcustomerzipcode', '#customerZipCode\\.errors', ACC.config.validZip);
ACC.formvalidation.vaildateFields('#customerState', '#errorcustomerstate', '#customerState\\.errors', ACC.config.selectState);
ACC.formvalidation.vaildateFields('#serviceType', '#errorsrevicetype', '#serviceType\\.errors',ACC.config.selectService);
ACC.formvalidation.vaildateFields('#referalsNo', '#errorreferalno', '#referalsNo\\.errors',ACC.config.specifyReferrals);
 
if(null != $("#homeownerphone").val() && $("#homeownerphone").val() != '')
{
if(!ACC.formvalidation.validatePhoneNumber($("#homeownerphone").val()))
{
    status = false;
    ACC.formvalidation.addErrorClassForRegForm('#homeownerphone', '#errorhomeownerphone', '#phone\\.errors', ERRORMSG.global.phone);
}
else
{
ACC.formvalidation.removeErrorClassForRegForm('#homeownerphone', '#errorhomeownerphone', '#phone\\.errors');
}
}
else
{
status = false;
    ACC.formvalidation.addErrorClassForRegForm('#homeownerphone', '#errorhomeownerphone', '#phone\\.errors',ACC.config.phoneNumber);
}
 
if(ACC.formvalidation.validatezipcode(e, $("#customerZipCode").val()))
{
ACC.formvalidation.removeErrorClassForRegForm('#customerZipCode', '#errorcustomerzipcode', '#customerZipCode\\.errors');
}
else
{
status = false;
ACC.formvalidation.addErrorClassForRegForm('#customerZipCode', '#errorcustomerzipcode', '#customerZipCode\\.errors',ACC.config.validZip);
}
 
if(ACC.formvalidation.validateEmailPattern($("#emailAddr").val()))
      {
  ACC.formvalidation.removeErrorClassForRegForm('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors');
  }
  else
  {
  status = false;
  ACC.formvalidation.addErrorClassForRegForm('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors', ERRORMSG.invalid.email);
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
        ACC.briteverify.validateEmailWithBriteVerify('#emailAddr', '#erroremailaddr', '#emailAddr\\.errors', ERRORMSG.global.email, $('#emailAddr').val(), '#siteOneHomeOwnerForm');
        }
        else
        {
        ACC.formvalidation.addGlobalError();
        }
        });
           
       
       
        //});
       
    },
 privacyRequestFormSubmit: function ()
    {
   
        
      $(document).on("input blur", "#phonenumbercontact", function(e)
{

     ACC.formvalidation.handlePhoneNumberOnInput('#phonenumbercontact', '#errorphonenumber', '#phoneNumber\\.errors');
});
      
     $(document).on('input blur', "#firstName,#lastName,#customerCity,#privacyRequestType,#state,#accountNumber,#emailaddress,#customerZipCode", function (e) {
         var thisId = $(this).attr('id');
         if (thisId === "firstName") {
             ACC.formvalidation.validateFieldBasedOnRegex(e, '#firstName', '#errorfirstname', '#firstName\\.errors', ACC.config.nameMayInclude, ACC.config.enterFirstName);
         }
         else if (thisId === "lastName") {
             ACC.formvalidation.validateFieldBasedOnRegex(e, '#lastName', '#errorlastname', '#lastName\\.errors', ACC.config.nameMayInclude, ACC.config.enterLastName);
         }
         else if (thisId === "customerCity") {
             ACC.formvalidation.validateFieldBasedOnRegex(e, '#customerCity', '#customerCityError', '#customerCity\\.errors', ACC.config.briteCityMay, ERRORMSG.global.city,"city");
         }
         else if (thisId === "privacyRequestType") {
             ACC.formvalidation.vaildateFields('#privacyRequestType', '#errorselectrequest', '#privacyRequestType\\.errors', ACC.config.selectRequest);
         }
         else if (thisId === "state") {
        	 ACC.formvalidation.vaildateFields('#state', '#errorcustomerstate', '#state\\.errors', ACC.config.selectState);
         }
         else if (thisId === "accountNumber") {
             ACC.formvalidation.validateAccountNumber(e, '#accountNumber', '#accountNumberError', '#accountNumber\\.errors', ACC.config.enterAccountNumber + '<div class="cl"></div><br/><br/>');
         }
         else if (thisId === "emailaddress") {
             if (ACC.formvalidation.validateEmailPattern($("#emailaddress").val())) {
                 ACC.formvalidation.removeErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors');
             }
             else {
                 ACC.formvalidation.addErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
             }
         }
         else if (thisId === "customerZipCode") {
             if ((ACC.formvalidation.validatezipcode(e, $("#customerZipCode").val()))) {
                 ACC.formvalidation.removeErrorClassForRegForm('#customerZipCode', '#customerZipCodeError', '#customerZipCode\\.errors');
             }
             else {
                 ACC.formvalidation.addErrorClassForRegForm('#customerZipCode', '#customerZipCodeError', '#customerZipCode\\.errors', ACC.config.validZip);
             }
         }

     });     
       
      
      $(document).on("change", "#state", function()
    	      {
    	     
    	          if(ACC.formvalidation.vaildateFields('#state'))
    	          {
    	      ACC.formvalidation.removeErrorClassForRegForm('#state', '#errorcustomerstate', '#state\\.errors');
    	      }
    	      else
    	      {
    	      ACC.formvalidation.addErrorClassForRegForm('#state', '#errorcustomerstate', '#state\\.errors', ACC.config.selectState);
    	      }
    	              
                  });
    function emptyValidationCcpa(id,errorId,errorText,globalMsg){
        if($(id).val() == "" || $(id).val() == null)
        {
        status = false;
        ACC.formvalidation.addErrorClassForRegForm(id, errorId, errorText, globalMsg);
        }
        else
        {
        $(errorId).empty();
        }
    }
    
    $(document).on('.page-ccpa input blur', "#firstName", function(e){
    	emptyValidationCcpa('#firstName', '#errorfirstname', '#firstName\\.errors', ACC.config.enterFirstName);
    });
    
    $(document).on('.page-ccpa input blur', "#lastName", function(e){
    	emptyValidationCcpa('#lastName', '#errorlastname', '#lastName\\.errors', ACC.config.enterLastName);
    });
    
    $(document).on("click", ".privacyRequest", function (e)
    {
    var status = true;
    e.preventDefault();
    document.getElementById('recaptchaValidator').value = grecaptcha.getResponse();
    ACC.briteverify.reCaptcha(grecaptcha.getResponse());
  
    ACC.formvalidation.vaildateFields('#privacyRequestType', '#errorselectrequest', '#privacyRequestType\\.errors', ACC.config.selectReason); 
    ACC.formvalidation.validateFieldBasedOnRegex(e,'#firstName', '#errorfirstname', '#firstName\\.errors',ACC.config.nameMayInclude,ERRORMSG.global.firstname);
    ACC.formvalidation.validateFieldBasedOnRegex(e,'#lastName', '#errorlastname', '#lastName\\.errors',ACC.config.nameMayInclude,ERRORMSG.global.lastname);
    emptyValidationCcpa('#state', '#errorcustomerstate', '#state\\.errors', ACC.config.selectState);
    emptyValidationCcpa('#firstName', '#errorfirstname', '#firstName\\.errors', ACC.config.enterFirstName);
    emptyValidationCcpa('#lastName', '#errorlastname', '#lastName\\.errors', ACC.config.enterLastName);
    emptyValidationCcpa('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
   
             

if(ACC.formvalidation.validateEmailPattern($("#emailaddress").val()))
    {
ACC.formvalidation.removeErrorClassForRegForm('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.invalid.email);
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
    ACC.briteverify.validateEmailWithBriteVerify('#emailaddress', '#errorEmailAddress', '#emailaddress\\.errors', ERRORMSG.global.email, $('#emailaddress').val(), '#siteoneCCPAForm');
    }
    else
{
    /*window.scrollTo(500, 0);*/
       ACC.formvalidation.addGlobalError();
}
   
    });
   
    },
    
    inspireForm:function()
    {
    $(document).on("change","#pageSize",function()
    {
    $('#inspireForm').submit();
    });
    },
    
    enableDisableSendEmailAction :function(length, action,textstatus)
    {
   
    if((null != length && length == 0 && $('#invoiceNumber').val() != undefined) || (null != length && length == 0 && $('#orderCode').val() != undefined) || (null != length && length == 0 && $('#cartCode').val() != undefined ) || (null != length && length == 0 && $('#productcode').val() != undefined) )
{
$('#emailSubmit').removeAttr('disabled');
}
   
  
    if(null != action && action == 'disable')
{
    $('#emailSubmit').attr('disabled', 'disabled');
}
   
    if(null != action && action == 'disable')
{
    $('#signUpPage').attr('disabled', 'disabled');
}
    },
    
    sendEmails :function(emails, map)
    {
    var status = true;
   
    var emailsList = emails.split(",");
var length = emailsList.length;
   
    if(null != map)
    {
    if(Object.keys(map).length == length)
    {
    $.each(emailsList,function(i)
{
if(!map[emailsList[i]])
{
status = false;
}
});
    }
    else
    {
    status = false;
    }
    }
   
    var productcode = $('#productcode').val();
    
    var cartCode = $('#cartCode').val();
    
    var invoiceNumber=$('#invoiceNumber').val();
    
    var uid=$('#uid').val();
    
     var invoiceCode = $('#invoiceCode').val();
    
    var orderCode = $('#orderCode').val();
    
    var savedListCode=$("#savedListCode").val();
    
    var assemblyCode=$("#assemblyCode").val();
	
	var accountNumber=$("#accountNumber").val();
    
    if(status)
    {
    if(productcode!= undefined)
{
   ACC.shareproductemail.productEmail(emails,productcode);
}
if(cartCode!= undefined)
{
   ACC.sharecart.cartEmail(emails,cartCode);
}
if(invoiceNumber!= undefined)
{
   ACC.accountdashboard.sendInvoiceEmail(emails,invoiceNumber,accountNumber);
}
if(orderCode!= undefined)
{
   ACC.accountdashboard.sendOrderDetailEmail(emails,orderCode,invoiceCode,uid);
}
if(savedListCode!=undefined)
{
ACC.sharelistemail.listEmail(emails,savedListCode);
}
if(assemblyCode!=undefined)
{
ACC.sharelistemail.assemblyEmail(emails,assemblyCode);
}
    }
   
    },
    
checkToEmails:function(emails)
{
var emailsList = emails.split(",");
var length = emailsList.length;
var briteverify= document.getElementById('briteVerifyEnable').value;
var briteverifytimeout= document.getElementById('briteVerifyTimeout').value;
var status = true;
var verificationMap = new Object();
if(briteverify=="true")
{
$.each(emailsList,function(i)
{
//var email=emailsList[i]
verificationMap[emailsList[i]] = false;
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+ emailsList[i];

                    loading.start();
$.ajax({
url: url,
type: "get",
timeout: briteverifytimeout,
success: function (response)
{
if(response == "invalid")
{
                            loading.stop();
$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressError1+"</div></font></div>");
}
else
{
verificationMap[emailsList[i]] = true;
ACC.briteverify.sendEmails(emails, verificationMap);
}
},
error: function(response, textstatus, message) 
{
  loading.stop();
  if(textstatus == "timeout" || textstatus == "error") 
  {
$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
ACC.briteverify.enableDisableSendEmailAction(null, "disable");
      } 
}
});
});
$.each(emailsList,function(i)
{
if(!verificationMap[emailsList[i]])
{
status = false;
}
});
if(status)
{
ACC.briteverify.sendEmails(emails, null);
}
}
else
{
ACC.briteverify.sendEmails(emails, null);
}
    },
    
    checkToEmailsOnKeyUp:function(emails,eventType)
{
var emailsList = emails.split(",");
var length = emailsList.length;
var briteverify= document.getElementById('briteVerifyEnable').value;
$.each(emailsList,function(i)
{
var email=emailsList[i];
if(briteverify=="true")
{
url = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email='+email;

                    $.ajax({
url: url,
type: "get",
  timeout: 5000,
success: function (response)
{
if(response  == "invalid")
{
if("keyup"!=eventType){
$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>" + ERRORMSG.invalid.email + "</div></font></div>");
/*ACC.briteverify.enableDisableSendEmailAction(null, "disable");*/
}else{
ACC.briteverify.enableDisableSendEmailAction(length, "disable");
}
} 
else
{ 
length = length - 1;
ACC.briteverify.enableDisableSendEmailAction(length, null);
}
},
error: function(response, textstatus, message) 
  {
  if(textstatus == "timeout" || textstatus == "error") 
  {
if("keyup"!=eventType){
$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressUnable+"</div></font></div>");
}
ACC.briteverify.enableDisableSendEmailAction(null, "disable");
      } 
  }
});
}
});
    },
    emailSubscription: function(email)
    {
    var zipCode=$('#zipcode').val();
var role=$("input[name='role']:checked").val();
var data = {"email": email , "zipCode":zipCode , "role":role};
        loading.start();
$.ajax({ 
              type: "GET",
              url: ACC.config.encodedContextPath + "/emailsubscription",
              cache: false,
              data : {"email": email , "zipCode":zipCode , "role":role},
              dataType: "json",
              success: function (response) 
              {
              if(response == 'SUCCESS')
              {
              
              $.cookie("emailSignUp", "done", { expires: 90 });
                  ACC.accountdashboard.signUpSuccessPopup();
                  ewt.trackFormSubmit({name:'emailsignup _form_submit',type:'email_sign_up',form:data});
              }
              else if(response == 'ERROR')
                {
              ACC.accountdashboard.signUpFailurePopup(ACC.config.pleaseTry);
                }
              else
            {
              ACC.accountdashboard.signUpFailurePopup(ACC.config.alreadySignedError1 +" "+ response +" "+ ACC.config.alreadySignedError2);
            }
                },
                error: function (error)
                {
                ACC.accountdashboard.signUpFailurePopup(ACC.config.pleaseTry);
                }
}).always(function() {
        loading.stop();
      });
    },
    
    emailSignUpFooter: function(email)
    {
        var data = {"email": email};
    return $.ajax({
    type: "GET",
            url: ACC.config.encodedContextPath + "/emailsubscription",
            cache: false,
            data : {"email": email},
            dataType: "json",
            success: function (response) 
            {
            if(response == 'SUCCESS')
            {
            $(".message").html("<div><b>"+ACC.config.confirmationMail+"</b></div>"); 
                $.cookie("emailSignUp", "done", { expires: 90 });                
                    
                ewt.trackFormSubmit({name:'emailsignup_footer_form_submit',type:'email_sign_up_footer',form:data});
            }
            else if(response == 'ERROR')
            {
            $(".message").html("<div><b>"+ACC.config.pleaseTry+"</b></div>"); 
            }
            else
        {
            $(".message").html("<div class='sales-price'>"+ACC.config.alreadySignedError1+" "  + response + " "+ACC.config.alreadySignedError2 +"</div>");
        }
            },
            error: function (error) 
            {
            $(".message").html("<div><b>"+ACC.config.pleaseTry+"</b></div>");      
            }
            
    });
            
    }
    
    
    

}
