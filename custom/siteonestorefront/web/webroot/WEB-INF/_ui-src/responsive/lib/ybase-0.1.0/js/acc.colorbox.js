//Makes Color Box Responsive
var cboxOptions = {
	width: '95%',
	height: '95%',
	maxWidth: '960px',
	maxHeight: '960px',
}
var windowWidth = $(window).width();

$('.cbox-link').colorbox(cboxOptions);

$(window).resize(function(){

	//iOS should not trigger resize when scrolling
	if ($(window).width() != windowWidth) {
		windowWidth = $(window).width();
	} else {
		return;
	}

    if(!$('#colorbox').hasClass('variantSelectMobile')){
    	if($('#colorbox').find(".pickOrDelivery-changeContact").length){
    		ACC.colorbox.resizeTo("560","530");
    	} else if($('#colorbox').hasClass("add-to-cart-overlay")){
    		ACC.colorbox.resizeTo("650","550");
    	} else if($('#colorbox').hasClass("order-details-email-pop-up")){
    		ACC.colorbox.resizeTo("450","720");
    	} else if($('#colorbox').hasClass("share_By_Email_Popup")){
    		ACC.colorbox.resizeTo("450","479");
    	} else if($('#colorbox').hasClass("item-add-list-box")){
    		ACC.colorbox.resizeTo("621","150");
    	} else if($('#colorbox').hasClass("share-list-email-box")){
    		ACC.colorbox.resizeTo("430","500");
    	} else if($('#colorbox').hasClass("list_item_added")){
    		ACC.colorbox.resizeTo("650","224");
    	}
    	else{
	        $.colorbox.resize({
	            width: window.innerWidth > parseInt(cboxOptions.maxWidth) ? cboxOptions.maxWidth : cboxOptions.width,
	            height: window.innerHeight > parseInt(cboxOptions.maxHeight) ? cboxOptions.maxHeight : cboxOptions.height
	        });
    	}
    }
});


ACC.colorbox = {
	config: {
		maxWidth:"100%",
		opacity:0.7,
		width:"auto",
		transition:"none",
		close:'<span class="icon-close"></span>',
		title:'<div class="headline"><span class="headline-text">{title}</span></div>',
		onComplete: function() {
			$.colorbox.resize();
			ACC.common.refreshScreenReaderBuffer();
		},
		onClosed: function() {
			ACC.common.refreshScreenReaderBuffer();
		}
	},

	open: function(title,config){
		var config = $.extend({},ACC.colorbox.config,config);
		config.title = config.title.replace(/{title}/g,title);
		return $.colorbox(config);
	},

	resize: function(){
	
		$.colorbox.resize();
	},
	
	resizeTo: function(wdth,ht){
		if(wdth &&  ht){
			$.colorbox.resize({width:wdth,height:ht});
		}else if(wdth && !ht){
			$.colorbox.resize({width:wdth,height:"max-content"});
		}else if(!wdth && ht){
			$.colorbox.resize({height:ht});
		}
		
	},
	getSize: function(){
		var wdth=$("#colorbox").css("width");
		wdth=wdth.replace("px","");
		wdth=wdth.replace("rem","");
		wdth=wdth.replace("pt","");
		return wdth;
		
	},

	close: function(){
		$.colorbox.close();
	}
};

$(document).on('click', '#colorbox input, #colorbox textarea', function() {
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		$(window).off('resize');
	}
});