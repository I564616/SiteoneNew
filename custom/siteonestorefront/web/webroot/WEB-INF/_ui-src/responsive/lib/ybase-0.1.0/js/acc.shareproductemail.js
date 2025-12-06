ACC.shareproductemail = {

	_autoload: [
	    
	    "bindSharedProductEmail"
	    
	],
	
	
	
	bindSharedProductEmail: function()
    {
    	$(document).on("click", ".shareproductemail", function(e)
		{
    	    e.preventDefault();
            window.scrollTo(0,0);
    		ACC.shareproductemail.sharedproductPopup();
		});
    
      
    	$(document).on("click", "#productEmailSubmit", function()
    			{
    		$("#emailError").html("");
    		if($("#emailIdProduct").val() == "" || $("#emailIdProduct").val() == null)
    		{
    			status = false;
    			$("#emailError").html("<div class='bg-danger'><font color='red'><div class='panel-body'>"+ACC.config.emailAddressError+"</div></font></div>");
    			
    		}
    		else
    		{
    	
    			ACC.briteverify.checkToEmails($("#emailIdProduct").val());
    			
    		}
	    	    		
    			});
    },
    
    
    productEmail: function(emails,productcode)
	{
        loading.start();
    	$.ajax({
         	type: "GET",
         	url: ACC.config.encodedContextPath + "/shareProductEmail/" +productcode,
         	cache: false,
         	data : {"email": emails},
         	dataType: "json",
         	success: function (response) 
         	
         	{
        		$('#colorbox').removeClass('share_By_Email_Popup');
         		ACC.global.successPopup();
         		ACC.colorbox.resizeTo(null,300);
            }
             
			}).always(function() {
                loading.stop();
            });
	},
	
	sharedproductPopup: function()
	{
        ACC.colorbox.open("", {
            html : "<div class='PopupBox'>"+
                        "<h3 class='shareEmail_heading product-headline-email'> "+ACC.config.shareByEmail+"</h3>"+
                        "<div class='margin20 share-product-email-description'>"+ACC.config.shareCartTell+"</div>"+
                        "<label for='emailIdProduct'><h5>"+ACC.config.emailTo+"</h5></label>"+
                        "<textarea class='col-md-12 col-sm-12 col-xs-12 form-control textarea' type='textarea' id='emailIdProduct' rows='5' cols='10' placeholder='"+ACC.config.enterEmail+"' ></textarea>"+
                        "<div id = 'emailError' style='margin:30px 0px'></div>"+
                        "<input id='productEmailSubmit' class='btn btn-primary btn-center margin20' type='button' value='"+ACC.config.contactUsEmail+"'>"+
                    "</div>",
            width:"450px",
            /*title: "<h4 class='hidden-md hidden-lg hidden-sm share-product-email-title headline'><span class='headline-text'>Share By Email</span></h4>",*/
            onComplete: function() {
             	$('#colorbox').addClass('share_By_Email_Popup');
            },
            onClosed: function() {
        		$('body').css("overflow-y","auto");
        		$('#colorbox').removeClass('share_By_Email_Popup');
        	}
        });
	}
};

$('.share-link').on('click',function () {
	$('body').css("overflow-y", "hidden");	
});

/*
$('#close').trigger("Close",["close", Event]);

$('#close').on("Close", function() {
	$('body').css("overflow-y", "auto");	
});*/



