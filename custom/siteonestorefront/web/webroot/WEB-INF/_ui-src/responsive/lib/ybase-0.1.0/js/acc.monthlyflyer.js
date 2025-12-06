ACC.monthlyflyer = {
	_autoload: [
		"receiveMessage"
		
	],

	receiveMessage : function() {
		jQuery.receiveMessage(monthlyFlyerRecieveMessageHandler, 'https://www.siteonecatalog.com');
		
 	}
	
}
function monthlyFlyerRecieveMessageHandler(theMessage){	
		console.log("theMessage"+theMessage.data.action);
	    switch (theMessage.data.action)
	    {
	        case 'quickview':
	            // Call to your function to open your quickview window
	            //yourFunctionToOpenYourQuickViewWindow(theMessage.pid, theMessage.bookcode,theMessage.pagelabel));
				var productCode = theMessage.data.pid;

                loading.start();
                ACC.colorbox.open("",{
                    href: ACC.config.encodedContextPath + "/p/" + productCode + "/quickView",
                    maxWidth:"100%",
                    width:"621px",
                    overflow:"hidden",
                    initialWidth :"380px",
                    onComplete:function(){
                        loading.stop();
                    }
                });

				break;
	        default:
	            alert('Unexpected Message:' + theMessage.data.action);
	            break;
	    }
	}
