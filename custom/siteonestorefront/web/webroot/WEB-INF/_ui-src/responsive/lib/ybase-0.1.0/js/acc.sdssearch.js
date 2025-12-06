ACC.sdssearch = {
	_autoload: [
		"showSearchResultsWithLazyLoading",
        "showProductDescription",
        "sdsFormSubmit",
        "sdsinputkeypress",
        ["SDSLabelSearch", $('#sds-search-button').length!= 0]
	],
	
	
	SDSLabelSearch: function(){
			var SDSsearchKeyword = $("#sds-search-input").val();
			var SDSNoOfSearchResults = $("#sdsSearchCount").val();
			if (SDSsearchKeyword !='')
				{
				
				if(SDSNoOfSearchResults  == '0') {
						SDSNoOfSearchResults= "Zero"
					}
				else{
					SDSNoOfSearchResults= SDSNoOfSearchResults
				    }
				digitalData.eventData={
						SDSsearchKeyword: SDSsearchKeyword,
						SDSNoOfSearchResults:SDSNoOfSearchResults
							}
					try {
					    _satellite.track("SDSLabelSearch");
					} catch (e) {}	
				}
				

	},
	
	
	sdsinputkeypress : function () {
		// $(document).ready(function() {
			$(document).on("keyup","#sds-search-input",function() {
				var inp_len = $('#sds-search-input').val().length;
				if(inp_len == 0) {
					$('#sds-search-button').addClass('button-grey');
				}
				else {
					$('#sds-search-button').removeClass('button-grey');
				}
			})
		// })
	},
	
	sdsFormSubmit : function ()
	{
		$(document).on("keypress","#sds-search-input",function(e) {
			if(e.keyCode ==13) {
				if($('#sds-search-input').val() != '' &&  $('#sds-search-input').val() != undefined &&  ( $('#sds-search-input').val().trim() ) != '') {
					 var searchTerm = $("#sds-search-input").val();
				     var q = searchTerm + ":relevance:soIsSDSAvailable:true";
				     $("#q").val(q);
				     $('#sdsSearchForm').attr('action', ACC.config.encodedContextPath+"/sdssearch").submit();
				} else {
					$('#sdsSearchResults').html('<br><b>'+ACC.config.sdsEmptySearch+'</b>');
					$('#productDescription').addClass('hidden');
					
				}
				
				return false;
			}
			else {
				return true;
			}
			 
		});
		
		$(document).on("click","#sds-search-button",function()
				{	
					if($('#sds-search-input').val() != '' &&  $('#sds-search-input').val() != undefined &&  ( $('#sds-search-input').val().trim() ) != '') {
						 var searchTerm = $("#sds-search-input").val();
					     var q = searchTerm + ":relevance:soIsSDSAvailable:true";
					     $("#q").val(q);
					     $('#sdsSearchForm').attr('action', ACC.config.encodedContextPath+"/sdssearch").submit();
					} else {
						$('#sdsSearchResults').html('<br><b>'+ACC.config.sdsEmptySearch+'</b>');
						$('#productDescription').addClass('hidden');
						
					}
				});
	},
	showSearchResultsWithLazyLoading : function ()
    {	
		//var searchParams = new URLSearchParams(window.location.search); //?anything=123
		var searchText=$('#sds-search-input').val();
		var page = 0;
		var loading = false;//to prevent duplicate
		
		var winTop = $(window).scrollTop();
		var docHeight = $(document).height();
		var winHeight = $(window)[0].innerHeight;
		/*//scroll to PAGE's bottom
		if  ((winTop / (docHeight - winHeight)) > 0.95) {       
		    if(!loading){
		        loading = true;
		        loadNewContent();//call function to load content when scroll reachs PAGE bottom                
		    }       
		}*/
		
		
	   $("#sdsSearchResults").on('scroll', function (){
		    	
		 if($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
			 
			 if(!loading){
					loading = true;
					/*alert("Botton Reached");*/
					loadNewContent();//call function to load content when scroll reachs DIV bottom    
						
		        	}   
				  }
		    });
	   
	   $(document).ready(function ()
		{
			if($('#sds-search-input').length) {
				var inp_len = $('#sds-search-input').val().length;
				if(inp_len == 0) {
					$('#sds-search-button').addClass('button-grey');
				}
				else {
					$('#sds-search-button').removeClass('button-grey');
				}
				if($('#sdsSearchResults').html() && $('#sdsSearchResults').html().indexOf('productLongDesc') == -1 && window.location.href.indexOf('/results') == -1) {
					$('#sdsSearchResults').html('<br><b>'+ACC.config.sdsSearchNoResult+'</b><br><p class="sds_noDataFnd"><br><b>'+ACC.config.sdsSearchCantFind+'</b></p><p><a href="'+ACC.config.encodedContextPath+'/contactus" style="color: #50a0c5;text-decoration: underline;">'+ACC.config.contactUs+' &rarr;</a></p>');
					$('#sds-search-button').removeClass('button-grey');
				}
			}
		});
		
	 
	   	
		function loadNewContent(){
			page++;
			/*alert("searchText="+searchText+"page="+page);*/
			var query = searchText + ":relevance:soIsSDSAvailable:true";
		    $.ajax({
		        type:'GET',
		        url: ACC.config.encodedContextPath + '/sdssearch', 

		        cache: false,
		        data: {"query":query,"page" : page,"text":searchText
		        },
		        success:function(searchPageDataResults){
		        	
		            if(searchPageDataResults.results != ""){
		            	
		                loading = false;
		                
		                $.each(searchPageDataResults.results , function( index, searchPageData ) 
			        	    	{
			        	    		var searchResultCode = searchPageData.itemNumber;
			        	    		var searchResultDescription = searchPageData.productShortDesc;
			        	    		
			        	    		var searchResultSDS = searchPageData.sds;
			        	    		var searchResultLabel = searchPageData.label;
			        	    		var productCode = searchPageData.code;
			        	    		index = (page > 1) ? (index + (page-1)*24) : index;
			        	    		content = "<table class='table product-compare'><tbody><tr class=''><td><div id='productLongDesc' class='productLongTitleLazyList proClass2' data-code='"+searchResultCode+"' data-description='"+escapeXml(searchResultDescription)+"' data-sds='"+searchResultSDS+"' data-label='"+searchResultLabel+"' data-index='"+index+"'data-codeforurl='"+productCode+"'>"+escapeXml(searchResultDescription)+"</div>";
			        	    		content += "<div class='productLongTitleLazyList proClass2' data-code='"+searchResultCode+"' data-description='"+escapeXml(searchResultDescription)+"' data-sds='"+searchResultSDS+"' data-label='"+searchResultLabel+"' data-index='"+index+"' data-codeforurl='"+productCode+"'>"+searchResultCode+"</div></td></tr></tbody></table>";
			        	    		content += "<div id='productDescriptionMobileLazyList"+index+"' class='hidden proClassDesc col-md-7 margin20 hidden-md hidden-lg'>";
			        	    		content += "<div class='panel panel-default'><table class='table product-compare-info'>"
												+"<tr><td colspan='2'><b>Product Information</b></td></tr>"
												+"<tr class='bg-lightGrey'><td>Item description</td><td><span class='description' class='content-product-title'></span></td></tr>"
												+"<tr><td>Item number</td><td><span class='productCode'></span></td></tr>"
												+"<tr><td><b>Product SDS</b></td></tr>"
												+"<tr class='bg-lightGrey'><td>SDS</td><td><div class='sdsList' style='text-decoration:underline;'></div><span class='noSds' class='collapse'> No SDS data is available</span></td></tr>"
												+"<tr><td><b>Product Labels</b></td><td>&nbsp;</td></tr>"
												+"<tr class='bg-lightGrey'><td>Label</td><td><div class='labelList' style='text-decoration:underline;'></div><span class='noLabel' class='collapse'>No label data is currently available</span></td></tr>"
												+"<tr><td colspan='2'><a class='itemInfoLink' href=''>See full product information</a></td></tr>"
												+"</table>"
												+"</div></div>";
			        	    		
			                        $('#searchResultList').append(content);
			        	   });  
		            }
		        }
		    });
		}
		function escapeXml(unsafe) {
			if(unsafe != null) {
				return unsafe.replace(/&/g, '&amp;')
	             .replace(/</g, '&lt;')
	             .replace(/>/g, '&gt;')
	             .replace(/"/g, '&quot;')
	             .replace(/'/g, '&apos;');
			}
			return null; 
		}
		
    },
    showProductDescription: function ()
    {
    	$('.productLongTitle').on('click', function()
    	//$(document).on("click",".productLongTitle",function()
    			{	
		    		for(var i=0; i<$('#sdsSearchResults').children('table').length;i++ ) {
						$('#productDescriptionMobile'+i).addClass("hidden");
					}
		    		for(var i=0; i<$('#searchResultList').children('table').length;i++ ) {
						$('#productDescriptionMobileLazyList'+i).addClass("hidden");
					}
    				$(this).closest("#sdsSearchResults").find("table tr").removeClass("activeGrey");
    				$(this).closest("#searchResultList").find("table tr").removeClass("activeGrey");
    				
    				var oldTr = $(this).closest('[data-index]').data('index');
    				$('#productDescriptionMobile'+oldTr).removeClass("hidden");
    				$(this).closest("tr").addClass("activeGrey");
    				$('#productDescription').removeClass("hidden");    				
    				$('.productCode').empty();
    				$('.productCode').html($(this).data('code'));
    				$('.description').empty();
    				$('.noSds').hide();
    				$('.noLabel').hide();
    				$('.sdsList').empty();
    				$('.labelList').empty();
    				$('.description').html("<a href='" + ACC.config.encodedContextPath + "/p/" + $(this).data('codeforurl') + "'> " + $(this).data('description') + "</a>");
    				var productSDSJSON = '#mySDSList' + $(this).data('index');
    				if($(productSDSJSON).val() != ''){

    					var productSDSList = JSON.parse($(productSDSJSON).val());
    					var sdsElement = '';
    					$.each(productSDSList,function(index, sds) 
		        	    	{	
        	    				sdsElement= sdsElement + "<li><a href='"+sds.split('||')[1]+"' target='_blank'><span class='icon-pdf-invoice'></span>"+ ACC.config.sdsSearchPageSDS +"</a></li>";
		        	    	});
    					$('.sdsList').html(sdsElement);
    				}
    				else{
    					$('.noSds').show();
    				}
    				var productLabelJSON = '#myLabelList' + $(this).data('index');
    				if($(productLabelJSON).val() != ''){

    					var productLabelList = JSON.parse($(productLabelJSON).val());
    					var labelElement = '';
    					$.each(productLabelList,function(index, label) 
		        	    	{	
    							labelElement= labelElement + "<li><a href='"+label.split('||')[1]+"' target='_blank'><span class='icon-pdf-invoice'></span>"+ ACC.config.sdsSearchPageLabel +"</a></li>";
		        	    	});
    					$('.labelList').html(labelElement);
    				}
    				else
					{
						$('.noLabel').show();
					}
    				$('.itemInfoLink').attr("href",  ACC.config.encodedContextPath+"/p/" + $(this).data('codeforurl'));
    				
    			})
    			$(document).on("click",".productLongTitleLazyList",function()
    			{	
    				for(var i=0; i<$('#sdsSearchResults').children('table').length;i++ ) {
						$('#productDescriptionMobile'+i).addClass("hidden");
					}
		    		for(var i=0; i<$('#searchResultList').children('table').length;i++ ) {
						$('#productDescriptionMobileLazyList'+i).addClass("hidden");
					}
    				$(this).closest("#searchResultList").find("table tr").removeClass("activeGrey");
    				$(this).closest("#sdsSearchResults").find("table tr").removeClass("activeGrey");
    				
    				var oldTr = $(this).closest('[data-index]').data('index');
    				$('#productDescriptionMobileLazyList'+oldTr).removeClass("hidden");
    				$(this).closest("tr").addClass("activeGrey");
    				$('#productDescription').removeClass("hidden");    			
    				$('.productCode').empty();
    				$('.productCode').html($(this).data('code'));
    				$('.description').empty();
    				$('.noSds').hide();
    				$('.noLabel').hide();
    				$('.sdsList').empty();
    				$('.labelList').empty();
    				$('.description').html("<a href='" + ACC.config.encodedContextPath + "/p/" + $(this).data('codeforurl') + "'> " + $(this).data('description') + "</a>");
    				
    				var productSDSLazyList = $(this).data('sds');
    				if(productSDSLazyList != '' && productSDSLazyList != null){
    					var productSDSJSONLazyList = productSDSLazyList.split(',');
    					var sdsElement = '';
    					$.each(productSDSJSONLazyList,function(index, sds) 
		        	    	{	
        	    				sdsElement= sdsElement + "<li><a href='"+sds.split('||')[1]+"' target='_blank'>" + sds.split('||')[0] +"</a></li>";
		        	    	});
    					$('.sdsList').html(sdsElement);
    				}
    				else{
    					$('.noSds').show();
    				}
    				
    				var productLabelLazyList = $(this).data('label');
    				if(productLabelLazyList != '' && productLabelLazyList != null){
    					var productLabelJSONLazyList = productLabelLazyList.split(',');
    					var labelElement = '';
    					$.each(productLabelJSONLazyList,function(index, label) 
		        	    	{	
    						labelElement= labelElement + "<li><a href='"+label.split('||')[1]+"' target='_blank'>" + label.split('||')[0] +"</a></li>";
		        	    	});
    					$('.labelList').html(labelElement);
    				}
    				else{
    					$('.noLabel').show();
    				}
    				$('.itemInfoLink').attr("href", ACC.config.encodedContextPath + "/p/" + $(this).data('codeforurl'));
    			})
    }

}

