ACC.forgottenpassword = {

	_autoload: [
		"bindLink",
		"forgotpasswordNew"
	],

	bindLink: function(){
		$(document).on("click",".js-password-forgotten,#lock-resetPassword",function(e){
			e.preventDefault();
			ACC.colorbox.open("", {
					href: $(this).data("link"),
					//title:"<div class='headline'><span class='headline-text'>"+ACC.config.error+"</span></div>",
					width:"480px",
					className: "overlay-forgot-pwd",
					onOpen: function()
					{
						$('#validEmail').remove();
					},
					onComplete: function(){ 
						_AAData.popupPageName= ACC.config.forgotpswrdpathingPageName;
						_AAData.popupChannel= ACC.config.myaccountpathingChannel;
        			 	try {
        				    	 _satellite.track('popupView');
        		        } catch (e) {}
						$(document).on("blur","#cboxContent #forgottenPwdForm input[name=email]",function(){   
							$(this).val($(this).val().trim());
						});
						ACC.forgottenpassword.setEvents();
					}
				}
			);
			
		});
		
		/*document.addEventListener('dragleave', function(e){
		    var top = e.pageY;
		    var right = document.body.clientWidth - e.pageX;
		    var bottom = document.body.clientHeight - e.pageY;
		    var left = e.pageX;
		    if(top < 10 || right < 20 || bottom < 10 || left < 10){
		        console.log('Mouse has moved out of window');
		    }
		});*/
		
		/*$(document).on('mouseleave','body',function(){
			CONDITIONS HERE
			alert("SCREEN LEAVE");
		});*/
		
	},
	setEvents : function() {
	
		$("#forgottenPwdForm input[name=email]").attr("placeholder",ACC.config.placeHolderEmail);
		$('form#forgottenPwdForm').ajaxForm({
			beforeSend: function () {
				var submitForm=true;
				loading.start();
				var briteverify = document.getElementById('briteVerifyEnable').value;
				if (ACC.formvalidation.validateEmailPattern($("#forgottenPwdForm .js-forgotpwd-input").val().trim())) {
					
					if (briteverify == "true") {
						var bUrl = ACC.config.encodedContextPath + '/request-account/briteVerifyValidate?email=' + $("#forgottenPwdForm .js-forgotpwd-input").val().trim();
						loading.start();
						$.ajax({
							url: bUrl,
							type: "get",
							timeout: 5000,
							async:false,
							success: function (response) {

								if (response == "invalid") {
									loading.stop();
									$("#forgotPwdEmailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>" + ACC.config.emailAddressError1 + "</div></font></div>");
									ACC.colorbox.resize();
									submitForm= false;
								}

							}
						})
					}
				}
				else {
					loading.stop();
					$("#forgotPwdEmailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>" + ACC.config.dashboardValidEmail + "</div></font></div>");
					ACC.colorbox.resize();
					submitForm= false;
				}
				return submitForm;

			},

			success: function(data)
			{
			
			//	$("#forgottenPwdForm input[name=email]").attr("placeholder",ACC.config.placeHolderEmail);
			//	var  email=$("#forgottenPwdForm input[name=email]").val();
				//var correctEmail=email.trim();
				//$("#forgottenPwdForm input[name=email]").val(correctEmail);
				//console.log(correctEmail);
							loading.stop();
			     			$(".forgotten-password").replaceWith(data);
			     			ACC.forgottenpassword.setEvents();
							if ($(data).closest('#fgtPwdResponseSection').length)
							{
								
										$("#cboxTitle .headline-text").html(ACC.config.forgotPasswordMailSent);

							}
							ACC.colorbox.resize();
				
			}
			
			
			
		});
	},
	
	forgotpasswordNew:function(){
		 $(document.body).on('click','.forgotPass',function(e){
			 
			 if(($('#forgottenPwd-emailNew').val()).trim() == ''){
				 $(".help-block").show();
				 $("#forgottenPwd-emailNew").css({"background-color": "#fec3c3", "border-color": "#fd7b7b"});
			   }
			 
			 else{
			 $.colorbox({
					html: $("#fgtPwdResponseSection").html(),
					maxWidth:"450px",
					width:"auto",
					maxHeight:"100%",
					overflow:"auto",
					className:"newclass",
					title:"<h1 class='headline'>"+ACC.config.sentEmail+"</h1>",
					close:'<span class="glyphicon glyphicon-remove"></span>',
					onComplete: function(){
						
						$("#cboxClose").click(function(){
								  $("#forgottenPwd-emailNew").val('');
								});
		 				 
						}
				});
			 } 
			 
		}) 
	},

};