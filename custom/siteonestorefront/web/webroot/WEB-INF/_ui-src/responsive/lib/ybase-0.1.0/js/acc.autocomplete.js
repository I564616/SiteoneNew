var indexAutoSuggestion= 1;
var indexProductResult= 1;
var indexRelatedArticle=1;
ACC.autocomplete = {
	_autoload: [
		"bindGetPriceValue",
		"bindSearchAutocomplete",
		"bindDisableSearch",
		"checkForEmptySearchBox",
		"onTextClick"
	],
	bindGetPriceValue: function(obj) {
        if (null != obj && typeof obj != undefined) {
            if (null != obj.price && null != obj.price.formattedValue) {
                return obj.price.formattedValue;
            } else if (null != obj.priceRange && null != obj.priceRange.maxPrice && null != obj.priceRange.minPrice && null != obj.priceRange.maxPrice.formattedValue && null != obj.priceRange.minPrice.formattedValue) {
                return obj.priceRange.minPrice.formattedValue + " - " + obj.priceRange.maxPrice.formattedValue;
            } else {
                return "";
            }
        } else {
            return "";
        }
    },
	bindSearchAutocomplete: function ()
	{	function promoElement(url,imgUrl){
		return `			    
		    <div class="col-md-3 col-xs-6 promo-search-img">
		    <a href="${url}"><img style="width:100%" src="${imgUrl}"/></a>
		    </div>
		    `;
		}
		// extend the default autocomplete widget, to solve issue on multiple instances of the searchbox component
		$.widget( "custom.yautocomplete", $.ui.autocomplete, {
			_create:function(){
				
				// get instance specific options form the html data attr
				var option = this.element.data("options");
				// set the options to the widget
				this._setOptions({
					minLength: option.minCharactersBeforeRequest,
					displayProductImages: option.displayProductImages,
					delay: option.waitTimeBeforeRequest,
					autocompleteUrl: option.autocompleteUrl,
					source: this.source,
					change: function(){
						indexAutoSuggestion = 1;
						indexProductResult = 1;
						indexRelatedArticle=1;
					}
				});

				// call the _super()
				$.ui.autocomplete.prototype._create.call(this);

			},
			options:{
				cache:{}, // init cache per instance
				focus: function (){return false;}, // prevent textfield value replacement on item focus
				select: function (event, ui){
					if (ui.item.url != null){
					window.location.href = ui.item.url;
					} else {
						return false;
					}
				}
			},
			_renderItem : function (ul, item){
				
				if (item.type == "autoSuggestion"){
					var renderHtml = "<a class='auto-suggestion-container' href='"+ item.url + "' ><div class='name titlename' data-index='"+ indexAutoSuggestion +"'>" + item.value + "</div></a>";
					renderHtml = $("<li class='auto-suggestion-name' style='border-top:0px'>")
						.data("item.autocomplete", item)
						.append(renderHtml)
						.appendTo(ul);
					indexAutoSuggestion++;
					return renderHtml;
					
				}
				else if (item.type == "productResult"){

					var renderHtml = "<a class='product-results-wrapper titlename' href='" + item.url + "'  data-index='"+ indexProductResult +"'>";

					if (item.image != null){
						renderHtml += "<div class='product-results-image-wrapper thumb'><img class='product-results-image' src='" + item.image + "' title='" + item.value +"'></div>";
					}
					
					renderHtml += 	"<div class='name product-results-name'> "+ item.value +"</div>";
					/*renderHtml += 	"<div class='price'>" + item.price +"</div>";*/
					renderHtml += 	"</a>";
					
					indexProductResult++;

					return $("<li style='border-top:0px'>").data("item.autocomplete", item).append(renderHtml).appendTo(ul);
				}
				else if(item.type == "SuggestionRelatedArticle"){
					
					var renderHtml = `<a class="ui-menu-item-wrapper titlename suggestion-related-article" href="${item.articleUrl}" data-index=${indexRelatedArticle} ><div class="suggested_RelatedArticle row">
				    <div class="col-xs-2 related-article-icon"><svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 50 50"><defs><style>.a.icon-box{fill: #F1F2F2;opacity:0.9;}.b{fill:#929497;}</style></defs><g transform="translate(-930 -1274)"><rect class="a icon-box" width="50" height="50" transform="translate(930 1274)"/><g transform="translate(920 1264)"><rect class="b icon-path" width="9.778" height="2.667" transform="translate(37.222 30.111)"/><rect class="b icon-path" width="24" height="2.667" transform="translate(23 23)"/><rect class="b icon-path" width="24" height="2.667" transform="translate(23 44.333)"/><rect class="b icon-path" width="9.778" height="2.667" transform="translate(37.222 37.222)"/><path class="b icon-path" d="M23,48.778h9.778V39H23Zm2.667-7.111h4.444v4.444H25.667Z" transform="translate(0 -8.889)"/></g></g></svg></div>
				    <div class="col-xs-10 related-articles-text">${item.articleName}</div></div></a>`;
					
					indexRelatedArticle++;
					return $("<li style='border-top:0px'>").data("item.autocomplete", item).append(renderHtml).appendTo(ul);
					
				}
				else if (item.type == "suggestionPromo") {
                    var promoItems = $();
                    var promoImg = 1;
                    
                    $.each(item.contents, function (i, obj) {
                    	if(obj.contentType == "PROMOTION_PAGE"){
	                    	if (window.matchMedia("(max-width:" + screenXsMax + ")").matches) {
	                    		if(promoImg <= '2'){
	                                promoItems = promoItems.add(promoElement(obj.url,obj.previewImage.url));
	        					}
	                        }
	                    	else { 
	                        	if(promoImg <= '4'){
	                                promoItems = promoItems.add(promoElement(obj.url,obj.previewImage.url));
	        					}
	                        }
                    	promoImg++;
                    	}
                    })
					var promoRow=$('<div class="row promo-search-wrap"></div>').append(promoItems)
                    return $("<li class='promo-padding0' style='border-top:0px'>").data("item.autocomplete", item).append(promoRow).appendTo(ul);

                }
				
				else if (item.type == "autoSuggestionText"){
					var renderHtml = "<div class='auto-suggestion-text-container'>" + item.value + "</div>"; 
					renderHtml  = $("<li class='auto-suggestion-text-name' style='cursor: default;' onmouseover='this.style.background=\"#FFFFFF\";'>")
						.append(renderHtml)
						.appendTo(ul);
					return renderHtml.css("color", "#999999");
				}
				else if (item.type == "autoSuggestionTextForProduct"){
					var renderHtml = "<div class='suggested_text'>" + item.value + "</div>"; 
					renderHtml  = $("<li class='suggested-items-title' style='cursor: default;' onmouseover='this.style.background=\"#FFFFFF\";'>")
						.append(renderHtml)
						.appendTo(ul);
					return renderHtml.css("color", "#999999");
				}
				else if(item.type == "autoSuggestionRelatedArticle"){
					var renderHtml = "<div class='suggested_RelatedArticle'>" + item.value + "</div>"; 
					renderHtml  = $("<li class='suggested-RelatedArticle-text' style='cursor: default;' onmouseover='this.style.background=\"#FFFFFF\";'>")
					.append(renderHtml)
					.appendTo(ul);
					return renderHtml.css("color", "#999999");
				}
				
			},
			source: function (request, response)
			{
				var self=this;
				var term = request.term.toLowerCase();
				if (term in self.options.cache)
				{
					return response(self.options.cache[term]);
				}

				$.getJSON(self.options.autocompleteUrl, {term: request.term}, function (data)
				{
					var autoSearchData = [];
					if(data.suggestions != null && data.suggestions.length != 0){
						autoSearchData.push({
							value: ACC.config.searchItems,
							type: "autoSuggestionText"
						});
						$.each(data.suggestions, function (i, obj)
						{
							autoSearchData.push({
								value: obj.term,
								url: ACC.config.encodedContextPath + "/search?text=" + obj.term,
								type: "autoSuggestion"
							});
						});
					}
					if(data.products != null && data.products.length != 0 ){
						autoSearchData.push({
							value: ACC.config.suggestedItems,
							type: "autoSuggestionTextForProduct"
						});
						$.each(data.products, function (i, obj)
						{
							
							var prodDesc=obj.description;
							if(!prodDesc){
								prodDesc=obj.productShortDesc;
							}
							autoSearchData.push({
								value: ACC.autocomplete.escapeHTML(prodDesc),
								code: obj.code,
								desc: prodDesc,
								manufacturer: obj.manufacturer,
								url:  ACC.config.encodedContextPath + obj.url,
								price: ACC.autocomplete.bindGetPriceValue(obj),
								type: "productResult",
								image: (obj.images!=null && self.options.displayProductImages) ? obj.images[0].url : null // prevent errors if obj.images = null
							});
						});
					}
					 if (data.contents != null && data.contents.length != 0) {
	                        autoSearchData.push({value: ACC.config.searchRelatedArticle, type: "autoSuggestionRelatedArticle"});
	                        $.each(data.contents, function (i, obj) {
	                            if (obj.contentType == "ARTICLE_PAGE") {
	                                autoSearchData.push({
	                                    articleName: obj.previewTitle,
	                                    articleUrl: ACC.config.encodedContextPath + obj.url,
	                                    type: "SuggestionRelatedArticle"
	                                });
	                            }
								

	                        });
							autoSearchData.push({contents: data.contents, type: "suggestionPromo"});
	                    }
					self.options.cache[term] = autoSearchData;
					return response(autoSearchData);
					
				});
			}

		});


		$search = $(".js-site-search-input");
		if($search.length>0){
			$search.yautocomplete();
		}

	},

	bindDisableSearch: function ()
	{
		$('#js-site-search-input').on('keyup',function (){
			$('#js-site-search-input').val($('#js-site-search-input').val().replace(/^\s+/gm,''));
			indexAutoSuggestion = 1;
			indexProductResult = 1;
			indexRelatedArticle=1;
		})
	},
	checkForEmptySearchBox : function ()
	{	
		$(document).on("keypress", "#js-site-search-input", function(event) { 
			if($('.errorMessage').show())
			{
				$('.errorMessage').hide();
			}
		    if(event.which == 13 && !$('#js-site-search-input').val()){
		    	$('.errorMessage').show();
		    	event.preventDefault(event);
		    }

			//reset sort selection once the user enters the search keyword			
			if(event.which == 13 && $('#js-site-search-input').val()){
				sessionStorage.removeItem('selectedDropdownValueSession');
			}
		    indexAutoSuggestion = 1;
			indexProductResult = 1;
			indexRelatedArticle=1;
		})
		$(document).on("click","#searchBoxButton",function()
                {                   
                    if(!$('#js-site-search-input').val())
                        {
                            $('.errorMessage').show();
                        }
                        else
                        {   sessionStorage.removeItem('mobileSortSelection');
                            $('#searchBox').submit();
                        }
                })
	},
	onTextClick: function(){
			$('.ui-menu').on('click', '.titlename', function(e) {
	        e.stopImmediatePropagation();
			var searchproductIndex= $(this).data('index');
			var productSearchTerm= $(this).text();
			if ($(this).hasClass("product-results-wrapper")) {
				var suggestedSearchType=$(".suggested-items-title").text();
			}
			else if($(this).hasClass("suggestion-related-article")){
				var suggestedSearchType="suggested articles"
				productSearchTerm=$(this).find(".related-articles-text").text();
			}
		 
			else{
				 var suggestedSearchType=$(".auto-suggestion-text-name").text();
			}
			
			try {
				_AAData.method = "internal search";
				_AAData.methodMetaData = productSearchTerm;
			}catch(e){} 
	  
			digitalData.searchInfo={
					searchproductIndex: searchproductIndex,
					productSearchTerm:productSearchTerm,
					suggestedSearchType: suggestedSearchType 
	   				}
			try {
				_satellite.track('suggested-search');
			}
			catch(e){}
			
   				 
   		 });
		
		 
		
		jQuery(function($) {
			  var input = $('#js-site-search-input');
			  input.on('keydown', function() {
			    var key = event.keyCode || event.charCode;

			    if( key == 8 || key == 46 )
			    	indexAutoSuggestion = 1;
					indexProductResult = 1;
					indexRelatedArticle=1;
			  });
			});
    	
    },
	

	escapeHTML: function (input) {
		return input.replace(/&/g,'&amp;')
			.replace(/</g,'&lt;')
			.replace(/>/g,'&gt;');
	}

};