 ACC.paginationsort = {
		
	downUpKeysPressed: false,
	 bindAll: function ()
	{
		this.bindPaginationSort();
		this.changePageSize();
		this.changeinvoicesort();
		this.chooseTab();
		this.changeUserSort();
		
	},
	bindPaginationSort: function ()
	{
		let sortClass = ACC.global.wWidth < 1024 ? 'web' : 'mob';
		$(".js-plp-sort-" + sortClass).remove();
		with (ACC.paginationsort)
		{
			
			ACC.paginationsort.chooseTab();
			bindSortForm($('#sortForm1'));
			bindSortForm($('#invoicesearchForm'));
			bindSortForm($('#searchForm'))
			bindSortForm($('#sortForm2'));
			bindMobileSortForm($('#mobileSortForm1'));
			bindMobileSortForm($('#mobileSortForm2'));
			bindContentSortForm($('#contentSortForm1'));
			bindContentSortForm($('#contentSortForm2'));
			var url = window.location.href;
			if(((url.indexOf('content')>0)) && $("#pageId").val() == 'searchGrid')
				{
					
				$('#productTab').addClass('hidden');
				$('.productResults .pagination-bar').first().addClass('hidden');
				$('#selectedProductTab').removeClass('seletedTab');
				$('#selectedContentTab').addClass('seletedTab');
				if(url.indexOf("type=content") < 0) {
					$('#contentSearchType').trigger("click");
				}
				
			}
			else {
					
				$('#contentTab').addClass('hidden');
				$('#selectedContentTab').removeClass('seletedTab');
				$('#selectedProductTab').addClass('seletedTab');
				
			}
			
		}
		
		
	},
	bindSortForm: function (sortForm)
	{

		
			
			$("#sortOptions1").on('change', function(){
				var sortType1 = $("#sortOptions1").val();
				sessionStorage.setItem('selectedDropdownValueSession',sortType1);
			});
			$("#sortOptions2").on('change', function(){
				var sortType2 = $("#sortOptions2").val();
				sessionStorage.setItem('selectedDropdownValueSession',sortType2);
			});
			$("#dateSort").on('change', function(){
				var sortType3 = $("#dateSort").val();
				sessionStorage.setItem('selectedDropdownValueSession',sortType3);
				sessionStorage.setItem('selectedDateSort',sortType3)
			});
			$(".mobile-sort").on('change', function(){
				sessionStorage.setItem('mobileSortSelection', $(".mobile-sort").val());
			});
			sortForm.on('change',function  ()
					{
			
            function submitSortForm1(thisForm){

            	var searchParam = $("#search-invoice").val();
                var dateFrom = $("#dateFrom").val();
                var dateTo = $("#dateTo").val();
                var viewpagination = $("#pageSizeInvoice1").val();
                var invoiceShiptos = $("#shipToSelected_inv").val();
                var searchParam = $("#search-ship-to").val();
                $(thisForm).find("input#search-shipto").val(searchParam);
                $(thisForm).find("input#shipToSelected_inv").val(invoiceShiptos);
                $(thisForm).find("input#search-invoicenew").val(searchParam);
                $(thisForm).find("input#dateFromnew").val(dateFrom);
                $(thisForm).find("input#dateTonew").val(dateTo);
                $(thisForm).find("input#ViewPaginationInvoice").val(viewpagination);
                $(thisForm).find("input#shipToSelected_inv").val(invoiceShiptos);

               
                $(thisForm).submit();
            }
            function submitSortForm2(thisForm){
            	 var searchParam = $("#searchOrderHistory").val();
                 var dateSort = $("#dateSort").val();
                 var viewpagination = $("#pageSizeOrder1").val();
                 var accountShiptos = $("#shipToSelected_order").val();
                 var searchParam = $("#search-ship-to").val();
                 $(thisForm).find("input#search-shipto").val(searchParam);
                 $(thisForm).find("input#shipToSelected_order").val(accountShiptos);
                 $(thisForm).find("input#search-ordernew").val(searchParam);
                 $(thisForm).find("input#dateSortOrder").val(dateSort);
                 $(thisForm).find("input#ViewPaginationOrder").val(viewpagination);
                 $(thisForm).find("input#shipToSelected_order").val(accountShiptos);
                 
                 $(thisForm).submit();
            }
            function submitSortForm3(thisForm){
            	var searchParam = $("#manager-user-voice").val();
                var viewpagination = $("#pageSizeManagerUser1").val();
                var shiptounit = $("#shipToSelected_user").val();
                var adminCheck = $('#admin_User').val();
                $(thisForm).find("input#search-shipto").val(searchParam);
                $(thisForm).find("input#shipToSelected_user").val(shiptounit);
                $(thisForm).find("input#search-ordernew").val(searchParam);
                $(thisForm).find("input#ViewPaginationManager").val(viewpagination);
                $(thisForm).find("input#admin_User").val(adminCheck);

               
                $(thisForm).submit();
            }
            
            function submitSortForm4(thisForm){
            	var searchParam = $("#ewallet-page-card").val();
				var viewpagination =$("#pageSizeeWallet1").val();
				var shiptounit = $("#shipToSelected_eWallet").val();				
				$(this).find("input#search-shipto").val(searchParam);
				$(this).find("input#shipToSelected_eWallet").val(shiptounit);
				$(this).find("input#ViewPaginationeWallet").val(viewpagination);
				


               
                $(thisForm).submit();
            }
            if (!$("body.page-invoicelistingpage").length) {
                if (!$("body.page-accountOrdersPage").length || $("div.open-order").length) {
                    if (!$("body.page-my-company").length) {
                    	if(!$("body.page-ewalletdetailspage").length){

                        if (!ACC.paginationsort.downUpPressed) {
                        	if(!$("div.open-order").length) {
                        		submitSortForm1(this);
                        		submitSortForm2(this);
                        		submitSortForm3(this);
                        		submitSortForm4(this);
                        	} else {this.submit();}


                        }
                        ACC.paginationsort.downUpPressed = false;
                       
                    }
                }
                }     
            }

        });

    },
	
	
	bindMobileSortForm: function (mobileSortForm)
	{
//  browser.msie has been removed from jQuery as of version 1.9. Modernizr is recommended as a replacement
//	issue created
//		if ($.browser.msie)
//		{
// 			this.sortFormIEFix($(sortForm).children('select'), $(sortForm).children('select').val());
//		}

		mobileSortForm.on('change',function  ()
		{
//			if (!$.browser.msie)
//			{
//				this.submit();
//			}
//			else
//			{
				if (!ACC.paginationsort.downUpPressed)
				{
					this.submit();
				}
				ACC.paginationsort.downUpPressed = false;
//			}
				
		});
		
	},
	
	
	
	
	bindContentSortForm: function (contentSortForm)
	{
//  browser.msie has been removed from jQuery as of version 1.9. Modernizr is recommended as a replacement
//	issue created
//		if ($.browser.msie)
//		{
// 			this.sortFormIEFix($(sortForm).children('select'), $(sortForm).children('select').val());
//		}

		contentSortForm.on('change',function  ()
		{
//			if (!$.browser.msie)
//			{
//				this.submit();
//			}
//			else
//			{
			
				if (!ACC.paginationsort.downUpPressed)
				{
					
					this.submit();
				}
				ACC.paginationsort.downUpPressed = false;
				
				
//			}
		});
		
	},
	sortFormIEFix: function (sortOptions, selectedOption)
	{
		sortOptions.keydown(function (e)
		{
			// Pressed up or down keys
			if (e.keyCode === 38 || e.keyCode === 40)
			{
				ACC.paginationsort.downUpPressed = true;
			}
			// Pressed enter
			else if (e.keyCode === 13 && selectedOption !== $(this).val())
			{
				$(this).parent().submit();
			}
			// Any other key
			else
			{
				ACC.paginationsort.downUpPressed = false;
			}
		});
	},
	changePageSize: function ()
	{	
		$(document).on("change","#pageSize1",function()
		{
		     $('#changePageSizeForm1').submit();
			 
		})
		
		$(document).on("change","#pageSizeInvoice1",function() 
		{    
		var searchParam = $("#search-invoice").val();
		var dateFrom= $("#dateFrom").val();
		var dateTo= $("#dateTo").val();
		var viewpagination =$("#pageSizeInvoice1").val();
		var invoiceShiptos = $("#shipToSelected_inv").val();
		var sortType = $("#sortOptions1").val();
		$('#changePageSizeFormInvoice1').find("input#search-invoicenew").val(searchParam);
		$('#changePageSizeFormInvoice1').find("input#dateFromnew").val(dateFrom);
		$('#changePageSizeFormInvoice1').find("input#dateTonew").val(dateTo);
		$('#changePageSizeFormInvoice1').find("input#ViewPaginationInvoice").val(viewpagination);
		$('#changePageSizeFormInvoice1').find("input#sortType").val(sortType);
		$('#changePageSizeFormInvoice1').find("input#shipToSelected_inv").val(invoiceShiptos);
		$('#changePageSizeFormInvoice1').submit();
		})
		
		$(document).on("change","#pageSizeInvoice2",function() 
		{    
		var searchParam = $("#search-invoice").val();
		var dateFrom= $("#dateFrom").val();
		var dateTo= $("#dateTo").val();
		var viewpagination =$("#pageSizeInvoice2").val();
		var invoiceShiptos = $("#shipToSelected_inv").val();
		$('#changePageSizeFormInvoice2').find("input#search-invoicenew").val(searchParam);
		$('#changePageSizeFormInvoice2').find("input#dateFromnew").val(dateFrom);
		$('#changePageSizeFormInvoice2').find("input#dateTonew").val(dateTo);
		$('#changePageSizeFormInvoice2').find("input#ViewPaginationInvoice").val(viewpagination);
		$('#changePageSizeFormInvoice2').find("input#shipToSelected_inv").val(invoiceShiptos);
		$('#changePageSizeFormInvoice2').submit();
		})
		
		$(document).on("change","#pageSizeOrder1",function() 
		{    
		var searchParam = $("#searchOrderHistory").val();
		var dateSort= $("#dateSort").val();
		var viewpagination =$("#pageSizeOrder1").val();
		var accountShiptos = $("#shipToSelected_order").val();
		var sortType = $("#sortOptions1").val();
		$('#changePageSizeFormOrder1').find("input#search-ordernew").val(searchParam);
		$('#changePageSizeFormOrder1').find("input#dateSortOrder").val(dateSort);
		$('#changePageSizeFormOrder1').find("input#ViewPaginationOrder").val(viewpagination);
		$('#changePageSizeFormOrder1').find("input#sortType").val(sortType);
		$('#changePageSizeFormOrder1').find("input#shipToSelected_order").val(accountShiptos);
		$('#changePageSizeFormOrder1').submit();
		})
		
		$(document).on("change","#pageSizeOrder2",function() 
		{    
		var searchParam = $("#searchOrderHistory").val();
		var dateSort= $("#dateSort").val();
		var viewpagination =$("#pageSizeOrder2").val();
		var accountShiptos = $("#shipToSelected_order").val();
		$('#changePageSizeFormOrder2').find("input#search-ordernew").val(searchParam);
		$('#changePageSizeFormOrder2').find("input#dateSortOrder").val(dateSort);
		$('#changePageSizeFormOrder2').find("input#ViewPaginationOrder").val(viewpagination);
		$('#changePageSizeFormOrder2').find("input#shipToSelected_order").val(accountShiptos);
		$('#changePageSizeFormOrder2').submit();
		})
			$(document).on("change","#pageSizeManagerUser1",function() 
		{    
		var searchParam = $("#manager-user-voice").val();
		var viewpagination =$("#pageSizeManagerUser1").val();
		var shiptounit = $("#shipToSelected_user").val();
		var sortType = $("#sortOptions1").val();
		var adminCheck = $('#admin_User').val();
		$('#changePageSizeFormManagerUser1').find("input#manager-user-voice").val(searchParam);
		$('#changePageSizeFormManagerUser1').find("input#ViewPaginationmanager").val(viewpagination);
		$('#changePageSizeFormManagerUser1').find("input#sortType").val(sortType);
		$('#changePageSizeFormManagerUser1').find("input#shipToSelected_user").val(shiptounit);
		$('#changePageSizeFormManagerUser1').find("input#admin_User").val(adminCheck);
		$('#changePageSizeFormManagerUser1').submit();
		})
		
			$(document).on("change","#pageSizeManagerUser2",function() 
		{    
		var searchParam = $("#manager-user-voice").val();
		var viewpagination =$("#pageSizeManagerUser2").val();
		var shiptounit = $("#shipToSelected_user").val();
		var adminCheck = $('#admin_User').val();
		$('#changePageSizeFormManagerUser2').find("input#manager-user-voice").val(searchParam);
		$('#changePageSizeFormManagerUser2').find("input#ViewPaginationmanager").val(viewpagination);
		$('#changePageSizeFormManagerUser2').find("input#sortType").val(sortType);
		$('#changePageSizeFormManagerUser2').find("input#shipToSelected_user").val(shiptounit);
		$('#changePageSizeFormManagerUser1').find("input#admin_User").val(adminCheck);
		$('#changePageSizeFormManagerUser2').submit();
		})
		$(document).on("change","#pageSizeeWallet1",function() 
		{    
		var searchParam = $("#ewallet-page-card").val();
		var viewpagination =$("#pageSizeeWallet1").val();
		var shiptounit = $("#shipToSelected_eWallet").val();
		var sortType = $("#sortOptions1").val();
		
		$('#changePageSizeFormEwallet1').find("input#ewallet-page-card").val(searchParam);
		$('#changePageSizeFormEwallet1').find("input#ViewPaginationeWallet").val(viewpagination);
		$('#changePageSizeFormEwallet1').find("input#sortType").val(sortType);
		$('#changePageSizeFormEwallet1').find("input#shipToSelected_eWallet").val(shiptounit);	
		$('#changePageSizeFormEwallet1').submit();
		})
		
			$(document).on("change","#pageSizeeWallet2",function() 
		{    
		var searchParam = $("#ewallet-page-card").val();
		var viewpagination =$("#pageSizeeWallet2").val();
		var shiptounit = $("#shipToSelected_eWallet").val();
		
		$('#changePageSizeFormEwallet2').find("input#ewallet-page-card").val(searchParam);
		$('#changePageSizeFormEwallet2').find("input#ViewPaginationeWallet").val(viewpagination);
		$('#changePageSizeFormEwallet2').find("input#sortType").val(sortType);
		$('#changePageSizeFormEwallet2').find("input#shipToSelected_eWallet").val(shiptounit);
		$('#changePageSizeFormEwallet2').submit();
		})
	

		$(document).on("change","#pageSize2",function()
		{
		     $('#changePageSizeForm2').submit();
			 
		})
		
		$(document).on("change","#pageSizeContent1",function()
		{
			$('#changePageSizeContentForm1').submit();
		})
		$(document).on("change","#pageSizeContent2",function()
		{
			$('#changePageSizeContentForm2').submit();
		})
		
        $(document).on("change", "#pageSizeBuyagain1", function() {
	
		var viewpagination =$("#pageSizeBuyagain1").val();
		$('#changePageSizebuyAgainForm1').find("input#ViewPaginationbuyagain").val(viewpagination);
            
        		$('#changePageSizebuyAgainForm1').submit();
        })
        $(document).on("change", "#pageSizeBuyagain2", function() {
	
	
		var viewpagination =$("#pageSizeBuyagain2").val();
		$('#changePageSizebuyAgainForm2').find("input#ViewPaginationbuyagain").val(viewpagination);
            
        		$('#changePageSizebuyAgainForm2').submit();
        })

        
		$("#filterByDateBuyagain, #pageSizeBuyagain1").on("change",function()
		{
			
		var viewpagination =$("#pageSizeBuyagain1").val();
		$('#changePageSizebuyAgainForm1').find("input#ViewPaginationbuyagain").val(viewpagination);
			$('#changePageSizebuyAgainForm1').submit();
		});
			
	},
	
	changeinvoicesort: function ()
	{
		 var cmspageid = $("#cmspageuid").val();
		$(document).on("change",".invoice_sort",function() 
		{  
		  if(cmspageid != 'orders')
	    {
			var searchParam = $("#search-invoice").val();
			var dateFrom= $("#dateFrom").val();
			var dateTo= $("#dateTo").val();
			var viewpagination =$("#pageSizeInvoice1").val();
			var invoiceShiptos = $("#shipToSelected_inv").val();
			var sortType = $("#sortOptions1").val();
			$('#sortForm1').find("input#search-invoice").val(searchParam);
			$('#sortForm1').find("input#dateFromnew").val(dateFrom);
			$('#sortForm1').find("input#dateTonew").val(dateTo);
			$('#sortForm1').find("input#sortOptions1").val(sortType);
			$('#sortForm1').find("input#ViewPaginationInvoice").val(viewpagination);
			$('#sortForm1').submit();
			$('$search-invoice').text($('$search-invoice-id').val());
		 }
		  else
			  {
			  var searchParam = $("#searchOrderHistory").val();
				var dateSort= $("#dateSort").val();
				var viewpagination =$("#pageSizeOrder1").val();
				var accountShiptos = $("#shipToSelected_order").val();
				var sortType = $("#sortOptions1").val();
				$('#sortForm1').find("input#searchOrderHistory").val(searchParam);
				$('#sortForm1').find("input#dateSortOrder").val(dateSort);
				$('#sortForm1').find("input#sortOptions1").val(sortType);
				$('#sortForm1').find("input#ViewPaginationOrder").val(viewpagination);
				$('#sortForm1').submit();
				$('$searchOrderHistory').text($('$search-order-id').val());
				
				
			  }
		})
		$(document).on("change","#sortOptions2",function() 
		{    
			if(cmspageid != 'orders')
		    {
			var searchParam = $("#search-invoice").val();
			var dateFrom= $("#dateFrom").val();
			var dateTo= $("#dateTo").val();
			var viewpagination =$("#pageSizeInvoice2").val();
			var invoiceShiptos = $("#shipToSelected_inv").val();
			var sortType = $("#sortOptions1").val();
			$('#sortForm2').find("input#search-invoicenew").val(searchParam);
			$('#sortForm2').find("input#dateFromnew").val(dateFrom);
			$('#sortForm2').find("input#dateTonew").val(dateTo);
			$('#sortForm2').find("input#ViewPaginationInvoice").val(viewpagination);
			$('#sortForm2').find("input#shipToSelected_inv").val(invoiceShiptos);
			$('#sortForm2').find("input#sortOptions1").val(sortType);
			$('#sortForm2').submit();
			$('$search-invoice').text($('$search-invoice-id').val());
		    }
			else
			  {
			  var searchParam = $("#searchOrderHistory").val();
			  var dateSort= $("#dateSort").val();
				var viewpagination =$("#pageSizeOrder2").val();
				var accountShiptos = $("#shipToSelected_order").val();
				var sortType = $("#sortOptions1").val();
				$('#sortForm2').find("input#searchOrderHistory").val(searchParam);
				$('#sortForm2').find("input#dateSortOrder").val(dateSort);
				$('#sortForm2').find("input#sortOptions1").val(sortType);
				$('#sortForm2').find("input#ViewPaginationOrder").val(viewpagination);
				$('#sortForm2').find("input#shipToSelected_order").val(accountShiptos);
				$('#sortForm2').submit();
				$('$searchOrderHistory').text($('$search-order-id').val());
			  }
		})
	},
	changeUserSort : function(){
		 var cmspageid = $("#cmspageuid").val();
			$(document).on("change",".user_sort",function() 
			{  
			  if(cmspageid == 'my-company')
		    {
				  var searchParam = $("#manager-user-voice").val();
				  var viewpagination =$("#pageSizeManagerUser1").val();
				  var shiptounit = $("#shipToSelected_user").val();
				  var adminCheck = $('#admin_User').val();
				  var sortType = $("#sortOptions1").val();
				  $('#sortForm1').find("input#manager-user-voice").val(searchParam);
				  $('#sortForm1').find("input#ViewPaginationmanager").val(viewpagination);
				  $('#sortForm1').find("input#sortType").val(sortType);
				  $('#sortForm1').find("input#shipToSelected_user").val(shiptounit);
				  $('#sortForm1').find("input#admin_User").val(adminCheck);
				  $('#sortForm1').submit();
				  $('$manager-user-voice').text($('$manager-user-voice').val());
			 }
			})
			  $(document).on("change","#sortOptions2",function() {    
						if(cmspageid == 'my-company')
				{
				  var searchParam = $("#manager-user-voice").val();
				  var viewpagination =$("#pageSizeManagerUser1").val();
				  var shiptounit = $("#shipToSelected_user").val();
				  var adminCheck = $('#admin_User').val();
				  var sortType = $("#sortOptions2").val();
				  $('#sortForm2').find("input#manager-user-voice").val(searchParam);
				  $('#sortForm2').find("input#ViewPaginationmanager").val(viewpagination);
				  $('#sortForm2').find("input#sortType").val(sortType);
				  $('#sortForm2').find("input#shipToSelected_user").val(shiptounit);
				  $('#sortForm2').find("input#admin_User").val(adminCheck);
				  $('#sortForm2').submit();
				  $('$manager-user-voice').text($('$manager-user-voice').val());
					
					
				  }
			})
		
	},
	chooseTab : function ()
	{	
		$(document).on("click","ul.tabs li",function(e)
		{
			e.preventDefault();
			var freeText='';
			var tabId = $(this).attr('data-tab');
			var url = window.location.href;
			var textParamStr = url.split('&')[1];
			var textParamValue='';
			if(textParamStr != ""){
				textParamValue =  textParamStr.substring(textParamStr.indexOf('=') + 1);
			}
			if(window.location.href.indexOf('content') > 0){
				$('#type').val('content');
				freeText = $('#contentSearchType').data("freeText");
			}
			else{
				$('#type').val('product');
				freeText = textParamValue;
			}
						
			if(tabId == 'productTab') {
				$('#js-site-search-input').val(freeText);
				$('#type').val('product');
				$('#searchBox').submit();
				tabId = 'product';
			}
			else {
				$('#js-site-search-input').val(freeText);
				$('#type').val('content');
				$('#searchBox').submit();
				$('#productTab').addClass('hidden');
				$('.productResults .pagination-bar').first().addClass('hidden');
				tabId = 'content';
				
			}
			if(window.location.href.indexOf('content') < 0){
				$('#type').val('content');
			}
			else{
				$('#type').val('product');
			}
			
		})
	},	
};

 $(document).ready(function ()
{
	ACC.paginationsort.bindAll();
	if($('#type')) {
		if(window.location.href.indexOf('content') > 0) {
			$('#type').val('content');
		} else {
			$('#type').val('product');
		}
		
	}
	if(sessionStorage.getItem("selectedDropdownValueSession")){
		$(".sortSelectSpan option").each(function(){
			if($(this).val()==sessionStorage.getItem("selectedDropdownValueSession")){ 
	            $(this).prop('selected', true);   
	        }
			else if(!$(".page-accountOrdersPage").length){
				$(this).prop('selected', false); 
			}
		});
		}
	if(sessionStorage.getItem("selectedDateSort")){
		$(".date-sort-my-orders option").each(function(){
			if($(this).val()==sessionStorage.getItem("selectedDateSort")){ 
	            $(this).prop('selected', true);    
	        }
			else{
				$(this).prop('selected', false); 
			}
		});
		}
	if(sessionStorage.getItem("mobileSortSelection")){
		$(".mobile-sort option").each(function(){
			
			if($(this).val()==sessionStorage.getItem("mobileSortSelection")){ 
				$(this).prop('selected', true);   
			}
			else{
				$(this).prop('selected', false); 
			}
		});
		}
	
	 
});