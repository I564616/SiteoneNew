ACC.sharelistemail = {

	_autoload: [
	    "bindShareListEmail"
	],
	
	bindShareListEmail: function()
    {	
    	$(document).on("click", "#sharelistemail, #shareassemblyemail", function()
		{
    		dataValueArray = [];
			if($(this).attr("id")==="sharelistemail"){
				ACC.sharelistemail.shareEmailListPopup();
			}
			else{
				ACC.sharelistemail.shareListPopup();
			}
    		
    		if ($('.page-detailsSavedListPage').length > 0){
				        _AAData.popupPageName= ACC.config.emaillistpathingPageName;
				        _AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {} 
    		}
    		if ($('.page-detailsAssemblyPage').length > 0){
    			_AAData.popupPageName= ACC.config.emailassemblypathingPageName;
    			_AAData.popupChannel= ACC.config.myaccountpathingChannel;
			 	try {
				    	 _satellite.track('popupView');
		        } catch (e) {} 
    		}
		});
    
    	$(document).on("click", ".share-list-email-box #listEmailSubmit, .share-list-email-box #assemblyEmailSubmit", function()
		{
    		$(".share-list-email-box #emailError").html("");
    		if($(".share-list-email-box #users").val() == "" || $(".share-list-email-box #users").val() == null)
    		{
    			status = false;
    			$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressError+"</div></font></div>");
    		}
    		else
    		{
	    			
    			digitalData.eventData={
   					 shareMedium:"Email",
   					}
	   			 try {
	   					_satellite.track("emailList");
   				}catch(e){}	
   				
    	    ACC.briteverify.checkToEmails(($(".share-list-email-box #users").val()).toString());
    		
    		}
		});
		
    	$(document).on("click", "#shareListEmailSubmit",function(e){
			digitalData.eventData={
				shareMedium:"Email",
			   }
			try {
				   _satellite.track("emailList");
		   }catch(e){}	
		   
			ACC.briteverify.checkToEmails($("#listEmailIdCart").val());

		});

		$(document).on("input", "#listEmailIdCart",function(e){
			if($(this).val() !=""){
				var emailArray=$(this).val().split(',').filter(function(entry) { return entry.trim() != ''; });				
				$(".no-of-emails").text(emailArray.length);
				if(emailArray.length>10){
					$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.maxEmail+"</div></font></div>");
					$(this).addClass("border-red");
					$("#shareListEmailSubmit").attr("disabled","disabled");
				}
				else{
					$("#emailError").html("");
					$(this).removeClass("border-red");
					$("#shareListEmailSubmit").removeAttr("disabled");

				}
				
			}
			else{
				$(".no-of-emails").text(ACC.config.listMinEmails);
			}
		})
		
    },
    
    
    listEmail: function(emails,savedlistcode)
	{	
		var isListPrice=$(".js-share-retail-price-btn").is(':checked')?true:false;
		var isYourPrice=$(".js-share-your-price-btn").is(':checked')?true:false;
		loading.start();
    	$.ajax({
         	type: "GET",
         	url: ACC.config.encodedContextPath + "/shareListEmail/" +savedlistcode,
         	cache: false,
         	data : {
				 	"email": emails,
					"rprice":isListPrice,
					"cprice":isYourPrice
					},
         	dataType: "json",
         	success: function (response) 
         	
         	{	
         	    $('#colorbox').removeClass('share-list-email-box share-list-email-popup');
         		ACC.global.successPopup();
            }
             
			}).always(function() {
				loading.stop();
			});
	},
	shareEmailListPopup: function(){
		var greenTickIcon="<span class='flex-center'><svg xmlns='http://www.w3.org/2000/svg' width='25' height='25' viewBox='0 0 34 34'><defs><style>.share-email-g,.share-email-circle2{fill:none;}.share-email-g{stroke-width:4px;}.share-email-circle1{stroke:none;}</style></defs>  <g transform='translate(-851 -522)'>       <path class='email-list-path' fill='#e0e0e0' d='M5.349,76.863.231,71.633a.817.817,0,0,1,0-1.138l1.114-1.138a.776.776,0,0,1,1.114,0L5.906,72.88l7.385-7.546a.776.776,0,0,1,1.114,0l1.114,1.138a.817.817,0,0,1,0,1.138L6.463,76.863a.776.776,0,0,1-1.114,0Z' transform='translate(860 467.902)'></path>       <g class='share-email-g' stroke='#e0e0e0' transform='translate(851 522)'>          <circle class='share-email-circle1' cx='17' cy='17' r='17'></circle>          <circle class='share-email-circle2' cx='17' cy='17' r='15.5'></circle>       </g>    </g> </svg>     </span>"
		        		ACC.colorbox.open("", {
		                html : ["<div class='PopupBox'>",
		                        "<div class='share-cart-email-description'>"+ACC.config.shareListToTell+"</div>",
		                        "<div class='margin20'>     <p class='bold marginBottom10 font-size-14'>"+ACC.config.include+"</p>"+     "<div class='flex justify-between flex-dir-column-xs'>         <div class='bold flex-center margin-bot-10-xs share-cart-price-buttons'>"+"<input id='list-email-retail-checkbox-btn' class='list-email-checkbox share-cart-retail-price js-share-retail-price-btn' type='checkbox'>	<label class='bold flex-center email-price-label' for='list-email-retail-checkbox-btn' type='checkbox'>"+greenTickIcon+"<span class='pad-lft-10'>"+ACC.config.retailPrice+"</span></label></div>         <div class='bold flex-center share-cart-price-buttons'>"+"<input id='list-email-csp-checkbox-btn' class='list-email-checkbox share-cart-your-price js-share-your-price-btn' type='checkbox'>	<label class='bold flex-center email-price-label' for='list-email-csp-checkbox-btn' type='checkbox'>"+greenTickIcon+"<span class='pad-lft-10'>"+ACC.config.emailyourPrice+"<span></label></div>     </div> </div>",
								"<label for='listEmailIdCart' class='flex marginBottom10 justify-between' style='color:inherit; font-size:14px;'>"+"<span class='bold'>"+ACC.config.sendTo+"</span>"+"<span class='italic-text'>"+"<span class='no-of-emails bold'>"+ACC.config.listMinEmails+"</span>&nbsp;"+ACC.config.of+"&nbsp;<span class='bold'>"+ACC.config.listMaxEmails+"</span>&nbsp;"+ACC.config.emails+"</span>"+"</label>",
								"<textarea class='col-md-12 col-sm-12 col-xs-12 form-control marginBottom20 textarea' type='textarea' id='listEmailIdCart' rows='4' cols='10' placeholder='"+ACC.config.enterEmail+"'></textarea>",
		                        "<div class='cl'></div><div id = 'emailError'></div>",
		                        "<input id='shareListEmailSubmit' class='btn share-email-submit btn-primary btn-center margin20' type='button' value='"+ACC.config.contactUsEmail+"'>",
		                    "</div>"].join(''),
		                    width:"600px",
							height:"auto",
							title: ACC.config.shareByEmail,
							
								onComplete: function() {
									$("#cboxTitle").addClass("share-email-list-title");
									$('#colorbox').addClass('share-list-email-popup');
								},
								onClosed: function() {
									$('body').css("overflow-y","auto");
									$('#colorbox').removeClass('share-list-email-popup');
								}
						});

	},
	shareListPopup: function()
	{ 
		ACC.colorbox.open("", {
        html : ["<form:form  commandName='siteoneShareSavedListForm' id='siteoneEmailSavedListForm'>",
    			"<form:hidden path='code' id='code' value="+$("#savedListCode").val()+"/>",
        		"<div class='PopupBox'>",
                "<h1 class='headline2'>"+ACC.config.shareByEmail+"</h1>",
                "<div class='margin20 share-list-email-description'>"+ACC.config.shareCartTell+"</div>",
                "<div id = 'emailError'></div>",
                "<label for='emailIdList'><h5>"+ACC.config.emailTo+"</h5></label>",
                "<div class='textBox'>",
       		 	"<div class='cl'></div>",
                "<input class='inputTextBox form-control' type='text' onkeydown='return (event.keyCode!=13);' required='required'/>",
                "<ul id='listEmailValues'></ul>",
                "<input id='listEmailSubmit' class='btn btn-primary btn-center margin20 emailListDetailBtn' type='button' value='"+ACC.config.contactUsEmail+"'>",
                "<form:hidden path='users' id='users' value=''/>",
                "</div>",
            "</div>"].join(''),
            width:"600px",
            height:"auto",
            title: "",
            	onComplete: function() {
            		$('#colorbox').addClass('share-list-email-box');
            	},
            	onClosed: function() {
            		$('body').css("overflow-y","auto");
            	    $('#colorbox').removeClass('share-list-email-box');
            	}
		});
	},
	
	assemblyEmail: function(emails,assemblycode)
	{
		loading.start();
		$.ajax({
	     	type: "GET",
	     	url: ACC.config.encodedContextPath + "/shareAssemblyEmail/" +assemblycode,
	     	cache: false,
	     	data : {"email": emails},
	     	dataType: "json",
	     	success: function (response) 
	        {
                $('#colorbox').removeClass('share-list-email-box');
	     		ACC.global.successPopup();
	        }
	         
			}).always(function(){
				loading.stop();
			});
	},

	
	
};

$('#sharelistemail , #shareassemblyemail').click(function() {
	$('body').css("overflow-y", "hidden");	
});

$(document).on('keyup','.share-list-email-box .inputTextBox',function(){
	var users = $(".share-list-email-box .inputTextBox").val();
	
	if ($(".share-list-email-box .inputTextBox").val().length >= 2){
		$(".share-list-email-box #listEmailValues").addClass('listValuesActive');
	
	 console.log()
	 $.ajax({
	        url : ACC.config.encodedContextPath + "/savedList/getShareListUsers?searchType=Email&searchTerm="+users,
	        method : "GET",
	        success :function(response)
	        {
       		$(".share-list-email-box #listEmailValues").empty();
       		$(".share-list-email-box .listValuesActive").css("cssText", "display: inline !important;");
       		 if(response.length!=0){
       			  	$.each(response,function(index,repo){
	        		console.log(repo);
	        		$(".share-list-email-box #listEmailValues").append(`<li data-id='${repo.text}' data-value='${repo.value}'class='fstResultItem'>${repo.value}</li>`);
		        });
       		 }
	                
       		 else{
       				$(".share-list-email-box #listEmailValues").append(`<li> No results </li>`);
       			 }
	        	
	       }
	 });
	}
});

var dataValueArray = [];
$(document).on("click", ".share-list-email-box #listEmailValues li",function(e){
    let dataValue = $(this).attr("data-value");
    if(dataValue!=undefined && dataValue!="") {
	if(jQuery.inArray(dataValue, dataValueArray) == -1) {
		dataValueArray.push(dataValue);
		console.log(dataValueArray)
	  $(".share-list-email-box #users").val(dataValueArray);
		var dataValueFiltered = dataValue.replace('.', "").replace('@', "");
		$(".share-list-email-box .textBox").prepend(`<div class="selecteduserList fstChoiceItem" id="${dataValueFiltered}">${dataValue}</div>`);
		$(".share-list-email-box .selecteduserList").addClass("removeuser");
		$('.share-list-email-box #fstResultItem').fadeOut();
		var x= document.getElementById(dataValueFiltered);
		$(x).click(function(){
			 $(this).closest(".share-list-email-box .selecteduserList").remove();
			 dataValueArray.splice($.inArray(dataValue, dataValueArray), 1);
			 $(".share-list-email-box #users").val(dataValueArray);
		});
		
		 $('.share-list-email-box #listEmailValues li:contains('+dataValue+')').remove();
		 $(".share-list-email-box .inputTextBox").click(function(){
			$('.share-list-email-box #listEmailValues li').remove();
			$(".share-list-email-box .inputTextBox").val('');
		});

		var select = $(".share-list-email-box #listEmailValues li").val();
	    if(select>=0){
	    	$(".share-list-email-box .listValuesActive").css("cssText", "display: inline !important;");
	    }else{
	    	$(".share-list-email-box .listValuesActive").css("cssText", "display: none !important;");
	    }
	}
    }
});  




