ACC.passwordrules = {

	_autoload: [
		"passwordValidation",
	],
	
	passwordValidation: function()
	{
		
		$("#siteoneChangePasswordForm #newPassword,#siteOneUpdatePwdForm #password,#siteOneSetPwdForm #password").on('keyup',function (){
			
			var password = $("#siteoneChangePasswordForm #newPassword,#siteOneUpdatePwdForm #password,#siteOneSetPwdForm #password").val();
			var uppercase = /[A-Z]/g;
			var lowercase = /[a-z]/g;
			var numbers = /[ 0-9]/g;
			var symbols = /[_$!@./#%&+-]/g;
			
			
			if(password.length>=8){
				$("#atleast8Char").removeClass("tick-mark-color icon-check-red  icon-check-gray");
				$("#atleast8Char").removeClass("error-mark-color").addClass("tick-mark-color  icon-check-green1");
			}else{
				$("#atleast8Char").removeClass("tick-mark-color icon-check-green1 icon-check-gray");
				$("#atleast8Char").removeClass("tick-mark-color").addClass("error-mark-color  icon-check-red");
			}
			
			if(password.match(uppercase)){
				$("#uppercase").removeClass("error-mark-color icon-check-red  icon-check-gray");
				$("#uppercase").removeClass("error-mark-color").addClass("tick-mark-color  icon-check-green1");
			}else{
				$("#uppercase").removeClass("tick-mark-color").removeClass("icon-check-green1 icon-check-gray");
				$("#uppercase").removeClass("tick-mark-color").addClass("error-mark-color  icon-check-red");
			}
			
			if(password.match(lowercase)){
				$("#lowercase").removeClass("error-mark-color icon-check-red  icon-check-gray");
				$("#lowercase").removeClass("error-mark-color").addClass("tick-mark-color  icon-check-green1");
			}else{
				$("#lowercase").removeClass("tick-mark-color icon-check-red  icon-check-gray");
				$("#lowercase").removeClass("tick-mark-color").addClass("error-mark-color  icon-check-red");
			}
			
			if(password.match(numbers)){
				$("#numeric").removeClass("error-mark-color").removeClass("icon-check-red  icon-check-gray");
				$("#numeric").removeClass("error-mark-color").addClass("tick-mark-color  icon-check-green1");
			}else{
				$("#numeric").removeClass("tick-mark-color").removeClass("icon-check-green1 icon-check-gray");
				$("#numeric").removeClass("tick-mark-color").addClass("error-mark-color  icon-check-red");
			}
			
			if(password.match(symbols)){
				$("#symbol").removeClass("error-mark-color").removeClass("icon-check-red  icon-check-gray");
				$("#symbol").removeClass("error-mark-color").addClass("tick-mark-color  icon-check-green1");
			}else{
				$("#symbol").removeClass("tick-mark-color").removeClass("icon-check-green1 icon-check-gray");
				$("#symbol").removeClass("tick-mark-color").addClass("error-mark-color  icon-check-red");
			}
		
		    
		});
		
	}

};
