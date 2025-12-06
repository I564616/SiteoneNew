ACC.sharecart = {

		_autoload: [

		            "bindShareCartPopup"

		            ],
		            bindShareCartPopup: function()
		            {
		            	$('.shareCart').on('click', function()
	            		{
		            		window.scrollTo(0,0);
	            			ACC.sharecart.shareCartPopup();
	            			_AAData.popupPageName= ACC.config.emailcart;
	            			_AAData.popupChannel= ACC.config.checkoutpathingChannel;
	        			 	try {
	        				    	 _satellite.track('popupView');
	        		        } catch (e) {}
	            		});

		            	$(document).on("click", "#cartEmailSubmit", function()
            			{
		            		$("#emailError").html("");
		            		 
			        		if($("#emailIdCart").val() == "" || $("#emailIdCart").val() == null)
			        		{
			        			status = false;
			        			$("#emailError").html("<div class='bg-danger'><div class='panel-body'><font color='red'>"+ACC.config.emailAddressError+"</font></div></div>");
			        		}
			        		else
			        		{
			        			ACC.briteverify.checkToEmails($("#emailIdCart").val());
			        		}
            			});
		            	
		            },
		            
		            cartEmail: function(email,cartCode)
		            {
		            	loading.start();
		            	$.ajax({
		                    	type: "GET",
		                    	url: ACC.config.encodedContextPath + "/shareCartEmail/" + cartCode,
		                    	cache: false,
		                    	data : {"email": email},
		                    	dataType: "json",
		                    	success: function (response) 
		                    	{
		                    		$('#colorbox').removeClass('share-cart-email-box');
		                    		ACC.global.successPopup();
		                        }
							}).always(function() {
								loading.stop();
							});
		            },

		            shareCartPopup: function()
		        	{ 
		        		ACC.colorbox.open("", {
		                html : ["<div class='cartShareEmail PopupBox'>",
		                        "<h1 class='cartHeadlineEmail headline2 hidden-xs hidden-sm'>"+ACC.config.shareByEmail+"</h1>",
		                        "<div class='share-cart-email-description'>"+ACC.config.shareCartTell+"</div>",
		                        "<label for='emailIdCart' style='color:inherit; font-size:14px;'>"+ACC.config.emailTo+"</label>",
		                        "<textarea class='col-md-12 col-sm-12 col-xs-12 form-control marginBottom20 textarea' type='textarea' id='emailIdCart' rows='6' cols='10' placeholder='"+ACC.config.enterEmail+"'></textarea>",
		                        "<div class='cl'></div><div id = 'emailError'></div>",
		                        "<input id='cartEmailSubmit' class='btn btn-primary btn-center margin20' type='button' value='"+ACC.config.contactUsEmail+"'>",
		                    "</div>"].join(''),
		                    width:"430px",
		                    className:"cartEmail",
		            		close:'<span class="glyphicon glyphicon-remove" id="close"></span>',
		                    title: "<h4 class='hidden-md hidden-lg share-cart-email-title headline'><span class='headline-text'>"+ACC.config.shareByEmail+"</span></h4>",
		                    	onComplete: function() {
		                    	    $('#colorbox').addClass('share-cart-email-box');
		                    		$("body").css("overflow-y", "hidden");
		                    	},
		                    	onClosed: function() {
		                    		$('body').css("overflow-y","auto");
		                    		$('#colorbox').removeClass('share-cart-email-box');
		                    	}
		        		});
		        }
};

