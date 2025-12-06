ACC.unlockuser = {

	_autoload: [
		"bindLink"
	],

	bindLink: function(){
		$(document).on("click",".js-unlock-user",function(e){
			e.preventDefault();

			ACC.colorbox.open(
				"",
				{
					href: ACC.config.encodedContextPath + $(this).data("link"),
					width:"680px",
					fixed: true,
					top: 80,
					className:"unlock-account",
					onOpen: function()
					{
						$('#validEmail').remove();
					},
					onComplete: function(){
						_AAData.popupPageName= ACC.config.unlockuserPageName;
						_AAData.popupChannel= ACC.config.myaccountpathingChannel;
					 	try {
						    	 _satellite.track('popupView');
				        } catch (e) {} 
						ACC.unlockuser.bindSuccess();
					}
				}
			);
		});
		
	},

   bindSuccess: function(){
	   $('form#siteOneUnlockUserForm').ajaxForm({
			success: function(data)
			{
						$(".unlock-user").replaceWith(data);
						ACC.unlockuser.bindSuccess();
						if ($(data).closest('#unlockUserResponseSection').length){
							//$("#cboxTitle .headline-text").html("<b>We've sent you an email</b>");
							_AAData.popupPageName= ACC.config.unlockuserinstructionPageName;
							_AAData.popupChannel= ACC.config.myaccountpathingChannel;
						 	try {
							    	 _satellite.track('popupView');
					        } catch (e) {}
						}
						
						ACC.colorbox.resize();
					//}
				//}
			}
		});
   }

};