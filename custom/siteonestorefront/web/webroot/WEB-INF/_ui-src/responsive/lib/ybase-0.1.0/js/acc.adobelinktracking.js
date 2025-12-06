ACC.adobelinktracking = {

	_autoload: [
		"linkClicks",
	],

linkClicks : function(){
        $(document.body).on('click','.article-learn-more-btn, a',function(e){
			let ref = $(this);
			let globalHeaderBtn = ref.data("global-linkname");
			if(!globalHeaderBtn || globalHeaderBtn == ""){
        	if(digitalData.pfm)
        	{
        	  delete digitalData.pfm;
        	  delete digitalData.pfmdetails;
        	}
            var pageName= $(".pagename").val();
            var onClickPageName= $(".siteonepagename").val();
            var aClick = $(this);
            if(aClick.is("a") && aClick.find("img").length > 0){
				if (pageName == 'Calculator Landing Page') {
                    let calciIdentifier = aClick.attr("href").split('/').slice(-1)[0];
                    if (calciIdentifier == 'stonewalls') {
                        $(this).find('img').attr("alt", 'Stone Walls Calculator')
                    }
                    else if (calciIdentifier == 'flagstone') {
                        $(this).find('img').attr("alt", 'Flagstone Calculator')
                    }
                    else if (calciIdentifier == 'roadbasefill') {
                        $(this).find('img').attr("alt", 'Road Base Calculator')
                    }
                    else if (calciIdentifier == 'barkmulch') {
                        $(this).find('img').attr("alt", 'Bark Mulch Calculator')
                    }
                    else if (calciIdentifier == 'decorativerock') {
                        $(this).find('img').attr("alt", 'Decorative Rock Calculator')
                    }
                    else if (calciIdentifier == 'topsoil') {
                        $(this).find('img').attr("alt", 'Topsoil Fill Calculator')
                    } else {
                        $(this).find('img').attr("alt", 'No Calculator')
                    }
                }
                //var linkName="img/banner";

				var Alt_text = $(this).find('img').attr("alt");
                if(Alt_text !== "") {
                    var linkName = Alt_text;
                }else{
                    var linkName = "img/banner";
                }
                
                if($(this).hasClass("recomm-analytics")){
                    digitalData.pfm = "recommendation";
                    digitalData.pfmdetails = _AAData.page.pageName;
                }
                if($(this).hasClass("recom-popup-tiles")){
                    digitalData.pfm = "recommendation";
                    digitalData.pfmdetails = "checkout: item added to cart popup";
                }
            }
            else if($(this).hasClass("recomm-analytics")){
            	if($(this).hasClass("recomm-multivar"))
            		{
            			var linkName=$(this).text().trim();
            		}
            	else
            		{
            			var linkName= "product:" + $(this).attr('title'); 
            			
            			digitalData.pfm = "recommendation";
            			digitalData.pfmdetails = _AAData.page.pageName;
            		}
				
			}
            else{
                if($(this).text())
                	
                {
                	var linkName=$(this).text().trim();
                	if(ACC.global.productCutatedplpPagination){
                		var linkName="pagination:"+$(this).text().trim();
                	}
                }
                else
                {
                    var linkName=$(this).attr('title'); 
                }
            }
            if($(this).hasClass("arrow-guest")){
            	var linkName= "guest checkout";
            }
            
			if($(this).closest("ul").hasClass("pagination")){
				 if(ACC.global.productCutatedplpPagination){
					 var linkType= "product-grid";
					 onClickPageName = pageName;
				 }else{
					 var linkType= "pagination";
				 }
				
			}
			else if($(this).hasClass("js-related-article")){
				var linkType= "related articles";
			}
			else if($(this).hasClass("js-more-article-btn")){
				var linkType= "more content"; 
			}
			else if($(this).hasClass("recomm-analytics")){
				var linkType= "recommendation"; 
			}
            else if($(this).hasClass("quickbook_btn")){
            	var linkName= "connect-to-quickbooks"; 
            	var linkType= "invoice-links";
            	onClickPageName = pageName;
            }
            else if($(this).hasClass("payonline_btn")){
            	var linkName= "pay-bill";
            	var linkType= "invoice-links";
            	onClickPageName = pageName;
            }
            else if(ACC.global.productCutatedplpLink){
                var linkName= curatedplpLinkName;
                var linkType = CutatedplpName;
                onClickPageName = pageName;
            }
			else if($(this).hasClass("mini-cart-link")){
				var linkType= "cart-icon";
				}
			else if($(this).hasClass("linktracking-breadcrumb")){
				var linkType= "breadcrumb";
				}	
			else if($(this).hasClass("linktracking-product") && $("body").hasClass("page-productGrid")){
				var linkType= "plp";
				}
			else if($(this).hasClass("linktracking-product") && $("body").hasClass("page-searchGrid")){
				var linkType= "search-results-listing-page";
				}		
			else if($(this).parents(".linktracking-header").length){
				var linkType= "header-link";
			}
			else if($(this).parents(".linktracking-footer").length){
			var linkType= "footer-link";
			}
			else if($(this).hasClass("js-cart-linktracking")){
			var linkType= "cart-page";
			}
			
			else{
				var linkType= "";
			}
			
			if($(this).hasClass("accept-delay")){
            	var linkName= "accept delay";
            }
			if($(this).hasClass("dontaccept-delay")){
            	var linkName= "decline delay";
            }
			if($(this).hasClass("article-learn-more-btn")){
				var linkType= "article-link";
            	var linkName= "learn more";
            }
            digitalData.eventData={
				linktype:linkType,
                linkName: linkName,
                onClickPageName:onClickPageName
            }
            try {
				      if($(this).closest("ul").hasClass("ui-autocomplete")===false){
      					_satellite.track("linkClicks");
      				}
				      if($(this).hasClass("dontaccept-delay")){
				    	  _satellite.track('nearbyDecline')
				      }
				      else if($(this).hasClass("accept-delay")){
				    	  _satellite.track('nearbyAcceptance')
						}
            } catch (e) {}
		}
        });

    },
	approveOrder: function(ref, linkType, linkName, pageName, comment){
			if($(ref).hasClass("disabled")) return;
			let commentText = (comment)? $("#cboxContent .comment-modal #comments").val() : "";
	
			digitalData.eventData={
				linktype: linkType,
				linkName: linkName + commentText,
				onClickPageName: pageName
			}
			_satellite.track("linkClicks");
		},
	requestQuote: function(ref, linkType, linkName, pageName) {
		let linkText = (linkName && linkName != "") ? ": " + linkName : "";
		digitalData.eventData = {
			linktype: linkType,
			linkName: 'request a quote' + linkText,
			onClickPageName: pageName
		}
		try {
			_satellite.track("linkClicks");
		} catch (e) { }
		if (linkName == "") {
			_AAData.page.pageName = "my account : lists: request a quote popup";
			_AAData.pathingChannel = "my account";
			_AAData.pathingPageName = "my account : lists: request a quote popup";
			try {
				_satellite.track('pageload');
			} catch (e) { }
		}
	},
	requestQuotePLP: function(ref, linkType, linkName, pageName, flagfullPagepath) {
		let linkText = (linkName && linkName != "") ? ": " + linkName : "";
		digitalData.eventData = {
			linktype: linkType,
			linkName: 'Request a Quote' + linkText,
			onClickPageName: (flagfullPagepath == 'true') ? $(".siteonepagename").val() : pageName
		}
		try {
			_satellite.track("linkClicks");
		} catch (e) { }
		if (linkName == "") {
			_AAData.page.pageName = pageName +": request a quote popup";
			_AAData.pathingChannel = "products";
			_AAData.pathingPageName = pageName +": request a quote popup";
			try {
				_satellite.track('pageload');
			} catch (e) { }
		}
	},
	
	quotesView: function(linkType, linkName, pageName) {
		digitalData.eventData = {
			linktype: linkType,
			linkName: linkName,
			onClickPageName: (pageName == 'pageName') ? _AAData.pathingPageName : pageName
		}
		_AAData.companyID = $("#parentUnitId").val();
		try {
			_satellite.track("linkClicks");
		} catch (e) { }
	},
	
	cartCsvUpload: function(linkName, linkType, onClickPageName, pageName, popupChannel, popupPageName) {
		if (onClickPageName && onClickPageName != "") {
			digitalData.eventData = {
				linkName: linkName,
				linktype: linkType,
				onClickPageName: onClickPageName
			}
			try {
				_satellite.track("linkClicks");
			} catch (e) { }
		}
		if (pageName && pageName != "") {
			_AAData.page.pageName = pageName;
			_AAData.pathingChannel = popupChannel;
			_AAData.pathingPageName = popupPageName;
			_AAData.popupChannel = popupChannel;
			_AAData.popupPageName = popupPageName;
			try {
				_satellite.track('popupView');
			} catch (e) { }
		}
	}
};