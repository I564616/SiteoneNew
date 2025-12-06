ACC.invoicepage = {

	_autoload: [
	    "getInvoiceCSV",
	    "getinvoicedaterange",
	    "bindShipToInvoiceOverlay",
	    "getOrderPagination",
	    "bindShipToOrderOverlay",
	    "getUserPagination",
	    "bindShipToPopupValue",
	    "getEwalletPagination"
	    ],

		bindShipToPopupValue:function(){
			$(document).on('click','#cboxWrapper #sortOptions1',function(){
				var sortType1 = $("#cboxWrapper #sortOptions1").val();
			  sessionStorage.setItem('selectedDropdownValueSession',sortType1);
			});
		},
		
		
		getInvoiceCSV:function(){
		if($("body").hasClass("page-invoicelistingpage")){
				$.cookie("accountPageId", "invoicespage", { path: '/' });
				sessionStorage.setItem("activeTab", "invoicesTab");
		}	
		if(sessionStorage.getItem("selectedInvoiceSession")){
		 $( ".ship-TosInvoice-Selected").val(sessionStorage.getItem("selectedInvoiceSession"));
		}
		sessionStorage.removeItem('selectedInvoiceSession');
		var currentTab = sessionStorage.getItem("activeTab");
		if (currentTab) $(".orders-tabs-container").find("[data-active='" + currentTab + "']").addClass("active-account-tab").removeClass("tab-font-color");
		sessionStorage.removeItem("activeTab");
		
		var searchParam = $("#search-invoice").val();
		var dateFrom= $("#dateFrom").val();
		var dateTo= $("#dateTo").val();
		var invoiceShiptos = $("#shipToSelected_inv").val();
		var viewpagination =$("#pageSizeInvoice1").val();
		
		
		$.each($(".invoice-pagination .pagination li a"), function(i, aTag){
			$(aTag).prop('href',$(aTag).prop('href')+"&invoiceShiptos="+invoiceShiptos+"&searchParam="+searchParam+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&pagesize="+viewpagination);
		});
		//$(".invoice-pagination .pagination li a").prop('href',$(".invoice-pagination .pagination li a").prop('href')+"&invoiceShiptos="+invoiceShiptos+"&searchParam="+searchParam+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&pagesize="+viewpagination);
		
		
		$(document).on("click", ".downloadCSV", function()
				{
					var unitId=$(this).data('unitid');
					var totalResults=$(this).data('total-results');
					var searchParam="";var dateFrom="";var dateTo="";var sort="";var invoiceShiptos=""; var invoiceShipTo="";
			        var href= ACC.config.encodedContextPath + "/my-account/invoicesCSV/"+unitId;
			        var createcsv= $(this).attr("id");
			        
			        searchParam = ACC.invoicepage.getParamForInvoiceCSV('searchParam');
			        dateFrom=ACC.invoicepage.getParamForInvoiceCSV('dateFrom');
			        dateTo=ACC.invoicepage.getParamForInvoiceCSV('dateTo');
			        sort=ACC.invoicepage.getParamForInvoiceCSV('sort');
			        invoiceShiptos=ACC.invoicepage.getParamForInvoiceCSV('invoiceShiptos');
			        if(invoiceShiptos)
			        	{
			         invoiceShipTo = invoiceShiptos.replace(/\+/g," ");
			        	}
			        if(searchParam===undefined && dateFrom===undefined && dateTo===undefined && sort===undefined){
			        	href=href+'?sort=byDate'+'&totalresults='+totalResults+'&createcsv='+createcsv;
			        }
			        else{
				        if(sort===undefined){
				        	sort="byDate";
				        }
			        href=href+'?invoiceShiptos='+ invoiceShipTo +'&searchParam='+searchParam+'&dateFrom='+dateFrom+'&dateTo='+dateTo+'&sort='+sort+'&totalresults='+totalResults+'&createcsv='+createcsv;
			        }
			        $(this).attr('href',href);
			        
			    });
	},
	getEwalletPagination:function(){
		if(sessionStorage.getItem("selectedewalletSession")){
			 $( ".ship-ToseWallet-Selected").val(sessionStorage.getItem("selectedewalletSession"));
			}
			
			
		
		
		var searchParam = $("#ewallet-page-card").val();
			var viewpagination =$("#pageSizeeWallet1").val();
			var shiptounit = $("#shipToSelected_eWallet").val();
		
			 
			$.each($(".ewallet-pagination .pagination li a"), function(i, aTag){
				$(aTag).prop('href',$(aTag).prop('href')+"&shiptounit="+shiptounit+"&searchParam="+searchParam+"&pagesize="+viewpagination);
			});
		
	},
	getOrderPagination:function(){
		if(sessionStorage.getItem("selectedOrderSession")){
			 $( ".ship-TosOrder-Selected").val(sessionStorage.getItem("selectedOrderSession"));
			}
			
					var searchParam = $("#searchOrderHistory").val();
		var dateSort= $("#dateSort").val();
		var accountShiptos = $("#shipToSelected_order").val();
		var viewpagination =$("#pageSizeOrder1").val();
		var paymentType=$("#paymentType").val();
			
			$.each($(".order-pagination .pagination li a"), function(i, aTag){
				$(aTag).prop('href',$(aTag).prop('href')+"&accountShiptos="+accountShiptos+"&searchParam="+searchParam+"&paymentType="+paymentType+"&dateSort="+dateSort+"&pagesize="+viewpagination);
			});
	
	},
	getUserPagination:function(){
	if(sessionStorage.getItem("selecteduserSession")){
		 $( ".ship-TosUser-Selected").val(sessionStorage.getItem("selecteduserSession"));
		}
		
		
	var searchParam = $("#manager-user-voice").val();
	var viewpagination =$("#pageSizeManagerUser1").val();
	var shiptounit = $("#shipToSelected_user").val();
	var adminCheck = $('#admin_User').val();
	
	if(document.getElementById('admin_User').checked) {
		 $('#admin_User').prop('checked', true);
		$.each($(".user-pagination .pagination li a"), function(i, aTag){
			$(aTag).prop('href',$(aTag).prop('href')+"&shiptounit="+shiptounit+"&searchParam="+searchParam+"&pagesize="+viewpagination+'&filterAdmin=true');
		});
	}else{
   	 $('#admin_User').prop('checked', false);
   	$.each($(".user-pagination .pagination li a"), function(i, aTag){
		$(aTag).prop('href',$(aTag).prop('href')+"&shiptounit="+shiptounit+"&searchParam="+searchParam+"&pagesize="+viewpagination+'&filterAdmin=false');
	});
	}
},
	getParamForInvoiceCSV: function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;
	
	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	
	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	  },
	  
	  
	  getinvoicedaterange:function(){
		  
			$(document).on("click", ".blue-text", function()
					{
						var datarange=$(this).attr("title");
				        var href= ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim();
				        
				        href=href+'?datarange='+datarange;
				        $(".blue-text").attr('href',href);
				        
				    });
		},
		invoiceSortBy: function (sort) {
			if (sessionStorage.getItem("sInvoice") == "desc" && sessionStorage.getItem("sInvoiceField") == sort) {
				sessionStorage.setItem("sInvoice", "asc");
				sessionStorage.removeItem("sInvoiceField");
				$('#invoicesearchForm').find("input#sort-direction").val("asc");
			}
			else {
				sessionStorage.setItem("sInvoice", "desc");
				sessionStorage.setItem("sInvoiceField", sort);
				$('#invoicesearchForm').find("input#sort-direction").val("desc");
			}
			var sortDirection = $("#sort-direction").val();
			$('#invoicesearchForm').find("input#sortinvoice").val(sort);
			$('#invoicesearchForm').find("input#sort-direction").val(sortDirection);
			$('#invoicesearchForm').submit();
			$('$search-invoice').text($('$search-invoice-id').val());
		},
		invoiceSortFilterApply: function () {
			let checkedVal = $("[name='filter-sort']:checked").val();
			ACC.invoicepage.invoiceSortBy(checkedVal);
			ACC.myquotes.filterPopup('hide', 500, '.invoice-filter-popup');
		},
	  bindShipToInvoiceOverlay: function() { 
		  
			$(document).on('click','#cboxContent #ShipToInvoice_popup', function(){ 
				 
				 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
				 var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
				if($(".invoice-shiptopopup").length > 0){
					if ($(".ship-TosInvoice option").eq(0).val() == (unitId)
							|| $(".ship-TosInvoice option").eq(1)
									.val() == (unitId)
							|| $(".ship-TosInvoice option").eq(2)
									.val() == (unitId)
							|| $(".ship-TosInvoice option").eq(3)
									.val() == (unitId)
						    || $(".ship-TosInvoice option").eq(4)
					                .val() == (unitId)
							) {
							}else{
								$(".ship-TosInvoice option").eq(3).val(unitId).text(name);
							}
					 		$(".ship-TosInvoice").val(unitId);
					 		ACC.colorbox.close();
					 		
					 		
					 		return false;
						}
	               
			});
			
			
			$('.ship-TosInvoice').on('click',function(){
				if($(".ship-TosInvoice").val() == "shipToopenPopupNew"){
					$(this).val('');
				}
			});
			
			$(document).on('change', 'body.page-invoicelistingpage .ship-TosInvoice', function () {	
			 
				var selectedInvoice = $(this).val();
				
					sessionStorage.setItem('selectedInvoiceSession',selectedInvoice);
					// previous = $(this).val();	
		    //}).on('change',function(e){
				
		    	var dateFromPopup =$("#dateFrom").data('datefrom');
                var dateToPopup =$("#dateTo").data('dateto');
               
					var currentSelect = $(".ship-TosInvoice");
					var data = {
							dateFromPopup:dateFromPopup,
							dateToPopup:dateToPopup
						}
					
					var $form = $("#sortForm1");
					if (!ACC.paginationsort.downUpPressed)
					{
						var searchParam = $("#search-invoice").val();
						var dateFrom= $("#dateFrom").val();
						var dateTo= $("#dateTo").val();
						var viewpagination =$("#pageSizeInvoice1").val();
						var invoiceShiptos = $("#shipToSelected_inv").val();
						var searchParam = $("#search-ship-to").val();
						$form.find("input#search-shipto").val(searchParam);
						$form.find("input#shipToSelected_inv").val(invoiceShiptos);
					$form.find("input#search-invoicenew").val(searchParam);
					$form.find("input#dateFromnew").val(dateFrom);
					$form.find("input#dateTonew").val(dateTo);
					$form.find("input#ViewPaginationInvoice").val(viewpagination);
					$form.find("input#invoiceShiptos").val(invoiceShiptos);
					//if($(".ship-TosInvoice").val() != "shipToopenPopupNew"){
						
					//}												
					}
					
					
					if("shipToopenPopupNew" != $(".ship-TosInvoice").val()){
						if (!ACC.paginationsort.downUpPressed){
							$form.submit();
						}
						ACC.paginationsort.downUpPressed = false;
					}else if($(".ship-TosInvoice").val() == "shipToopenPopupNew"){
						removeShipToSortbySessionValue();
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim()+'?searchParam=';	
						 $.ajax({
					            url: shipToPopupURL,
					            data:data,
					            method: "GET",		
					            success: function(searchPageData) {
					                ACC.colorbox.open('Search or Select a Ship-To', {
					                    html: searchPageData,
					                    width: '800px',
					                    className:"shipto-popup-mb invoice-shiptopopup",
					                    overlayClose: false,
					                    onComplete: function(e) {
					                    	sessionStorage.removeItem('selectedInvoiceSession');
					                    	$(".ship-TosInvoice").val('shipToopenPopupNew');
					        				
					        				checkShipToPopUpInvoice();
					        				$(document).on('click keyup', '#search-ship-to-popup-invoice', function(){
					                    		checkShipToPopUpInvoice();
					                    	});
					                    },
					                    onClosed: function() {
					                    	sessionStorage.setItem('selectedInvoiceSession',"All");
				        					location.reload(true);
				                    		window.location.href= ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim()+'?invoiceShiptos=All&viewtype=All&searchParam=&dateFrom='+ ($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
				        				
					                    }
					                });
					            }
					        });
					}
		    });
			
			$(document).on('change', 'body.page-invoicelistingpage .sortSelect.js-page-sort', function () {	
					
					var $form = $("#sortForm1");
					if (!ACC.paginationsort.downUpPressed)
					{
						var searchParam = $("#search-invoice").val();
						var dateFrom= $("#dateFrom").val();
						var dateTo= $("#dateTo").val();
						var viewpagination =$("#pageSizeInvoice1").val();
						var invoiceShiptos = $("#shipToSelected_inv").val();
						var searchParam = $("#search-ship-to").val();
						$form.find("input#search-shipto").val(searchParam);
						$form.find("input#shipToSelected_inv").val(invoiceShiptos);
					$form.find("input#search-invoicenew").val(searchParam);
					$form.find("input#dateFromnew").val(dateFrom);
					$form.find("input#dateTonew").val(dateTo);
					$form.find("input#ViewPaginationInvoice").val(viewpagination);
					$form.find("input#invoiceShiptos").val(invoiceShiptos);
					$form.submit();											
					}
					
						ACC.paginationsort.downUpPressed = false;
			});
			
			/*$(document).on('click','.responsive-table-cell a',function(){				
				sessionStorage.removeItem('selectedInvoiceSession');
			});*/
			
			
					
					$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-invoice a",function(e){
						 var dateFromPopup =$("#dateFrom").data('datefrom');
                         var dateToPopup =$("#dateTo").data('dateto');
						var page='';
						previous = $(this).value;
									e.preventDefault();	
									if($(this).attr('rel') == "prev"){
										page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
										}
										else if($(this).attr('rel') == "next"){
										    page = $(this).parents("ul").find("li.active").text().split("(")[0]
										}
										else{
										page= $(this).text().trim()-1;
										}
									var data = {
											searchParam : ($("#search-ship-to-popup-invoice-new").val()).trim(),
											viewtype : 'All',
											page : page,
											dateFromPopup:dateFromPopup,
											dateToPopup:dateToPopup,
											sort : $("#sortShipToInvoice").val()
										}
									var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim();
									var currentSelect = $(".ship-TosInvoice");
									var poupClass = $("#cboxOverlay").prop("class");
										$.ajax({
											url : shipToPopupURL,
											method : "GET",
											data : data,
											success : function(searchPageData) {
												ACC.colorbox.open('Search or Select a Ship-To', {
													html : searchPageData,
													width : '800px',
													className : poupClass,
													overlayClose : false,
													onComplete:function(){
														$(".ship-TosInvoice").val('shipToopenPopupNew');
								        				
								        				retrieveShipToSortbySessionValue();
								        				checkShipToPopUpInvoice();
													},
													onClosed: function() {
														
														sessionStorage.setItem('selectedInvoiceSession',"All");
							        					location.reload(true);
							                    		window.location.href= ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim()+'?invoiceShiptos=All&viewtype=All&searchParam=&dateFrom='+ ($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
							        				
								                    }
												});
											}
										});
									
							});
					
				 
				$(document).on("blur", "#cboxWrapper #search-ship-to-popup-invoice",function(e) {
					 removeShipToSortbySessionValue();
					 var dateFromPopup =$("#dateFrom").data('datefrom');
                     var dateToPopup =$("#dateTo").data('dateto');
						 	var key = e.which;
						 	 previous = $(this).value;
						    
									var data = {
											searchParam : ($("#search-ship-to-popup-invoice").val()).trim(),
											dateFromPopup:dateFromPopup,
											dateToPopup:dateToPopup	
										}
									var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim();
									var currentSelect = $(".ship-TosInvoice");
									var poupClass = $("#cboxOverlay").prop("class");
										$.ajax({
											url : shipToPopupURL,
											method : "GET",
											data : data,
											success : function(searchPageData) {
												ACC.colorbox.open('Search or Select a Ship-To', {
													html : searchPageData,
													width : '800px',
													className : poupClass,
													overlayClose : false,
													onComplete:function(){
													 	$(".ship-TosInvoice").val('shipToopenPopupNew');
								        				
								        				checkShipToPopUpInvoice();
								                    
													},
													onClosed: function() {
														
														sessionStorage.setItem('selectedInvoiceSession',"All");
							        					location.reload(true);
							                    		window.location.href= ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim()+'?invoiceShiptos=All&viewtype=All&searchParam=&dateFrom='+ ($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
							        				
								                    }
												});
											}
										});
									
							});	
					
					
					
					
					
					
							$(document).on("change", "#cboxWrapper .shipToInvoicePagePopup #sortOptions1", function(e) {
								var sortItem = $("#cboxWrapper #sortOptions1").val();
                                var dateFromPopup =$("#dateFrom").data('datefrom');
                                var dateToPopup =$("#dateTo").data('dateto');
								var data = {
									searchParam : ($("#search-ship-to-popup-invoice-new").val()).trim(),
									viewtype : 'All',
									sort : sortItem,
									dateFromPopup:dateFromPopup,
									dateToPopup:dateToPopup
								}
								var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim();
								var poupClass = $("#cboxOverlay").prop("class");
								var currentSelect = $(".ship-TosInvoice");
								$.ajax({
									url : shipToPopupURL,
									method : "GET",
									data : data,
									success : function(searchPageData) {
										ACC.colorbox.open('Search or Select a Ship-To', {
											html : searchPageData,
											width : '800px',
											className : $("#cboxOverlay").prop("class"),
											overlayClose : false,
											onComplete:function(){
											 	$(".ship-TosInvoice").val('shipToopenPopupNew');
						        				
						        				retrieveShipToSortbySessionValue();
						        				checkShipToPopUpInvoice();
						                    
											},
											onClosed: function() {
												sessionStorage.setItem('selectedInvoiceSession',"All");
					        					location.reload(true);
					                    		window.location.href= ACC.config.encodedContextPath + '/my-account/invoices/'+($("#unitUid").val()).trim()+'?invoiceShiptos=All&viewtype=All&searchParam=&dateFrom='+($("#dateFrom").val()).trim()+'&dateTo='+($("#dateTo").val()).trim()+'&pagesize=25&sort=byDate';
					        				
						                    }
										});
									}
								});

							});
			
			 
		 		
		},
		
		bindShipToOrderOverlay:function(){
			
			$(document).on('click','#cboxContent #ShipToOrder_popup', function(){ 
				 
				 var unitId= $(this).siblings("#ship-toId").val()+"_US"; 
				 var name= $(this).parents(".shipTo-border").children(".zeropadding").eq(1).find("a").text();
				if($(".invoice-shiptopopup").length > 0){
					if ($(".ship-TosOrder option").eq(0).val() == (unitId)
							|| $(".ship-TosOrder option").eq(1)
									.val() == (unitId)
							|| $(".ship-TosOrder option").eq(2)
									.val() == (unitId)
							|| $(".ship-TosOrder option").eq(3)
									.val() == (unitId)
						    || $(".ship-TosOrder option").eq(4)
					                .val() == (unitId)
							) {
							}else{
								$(".ship-TosOrder option").eq(3).val(unitId).text(name);
							}
					 		$(".ship-TosOrder").val(unitId);
					 		ACC.colorbox.close();
					 		
					 		
					 		return false;
						}
	               
			});
			
			
			$('.ship-TosOrder').on('click',function(){
				if($(".ship-TosOrder").val() == "shipToopenPopupNew"){
					$(this).val('');
				}
			});
			
			$(document).on('change', 'body.page-accountOrdersPage .ship-TosOrder', function () {	
			 
				var selectedOrder = $(this).val();
				
					sessionStorage.setItem('selectedOrderSession',selectedOrder);
					 //previous = $(this).val();	
		    //}).on('change',function(e){
				
					var dateSort= $("#dateSort").val();
					var paymentType=$("#paymentTypeOrder").val();
              
					var currentSelect = $(".ship-TosOrder");
					var data = {
							dateSort:dateSort,
							paymentType:paymentType
						}
					
					var $form = $("#sortForm1");
					if (!ACC.paginationsort.downUpPressed)
					{
						var searchParam = $("#searchOrderHistory").val();
						var dateSort= $("#dateSort").val();
						var viewpagination =$("#pageSizeOrder1").val();
						var accountShiptos = $("#shipToSelected_order").val();
						var searchParam = $("#search-ship-to").val();
						var paymentType= $("#paymentTypeOrder").val();
						$form.find("input#search-shipto").val(searchParam);
						$form.find("input#shipToSelected_order").val(accountShiptos);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#dateSortOrder").val(dateSort);
					$form.find("input#ViewPaginationOrder").val(viewpagination);
					$form.find("input#accountShiptos").val(accountShiptos);
					$form.find("input#paymentTypeOrder").val(paymentType);
					
					//if($(".ship-TosInvoice").val() != "shipToopenPopupNew"){
						
					//}												
					}
					
					
					if("shipToopenPopupNew" != $(".ship-TosOrder").val()){
						if (!ACC.paginationsort.downUpPressed){
							$form.submit();
						}
						ACC.paginationsort.downUpPressed = false;
					}else if($(".ship-TosOrder").val() == "shipToopenPopupNew"){
						let searchVal = ($("#search-ship-to-popup-order").length)?($("#search-ship-to-popup-order").val()).trim():"";
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim()+'?searchParam='+searchVal;	
						removeShipToSortbySessionValue();
						$.ajax({
					            url: shipToPopupURL,
					            data:data,
					            method: "GET",		
					            success: function(searchPageData) {
					                ACC.colorbox.open('Search or Select a Ship-To', {
					                    html: searchPageData,
					                    width: '800px',
					                    className:"shipto-popup-mb invoice-shiptopopup",
					                    overlayClose: false,
					                    onComplete: function(e) {
					                    	sessionStorage.removeItem('selectedOrderSession');
					                    	$(".ship-TosOrder").val('shipToopenPopupNew');
					        				
					        				checkShipToPopUpOrder();
					        				$(document).on('click keyup', '#search-ship-to-popup-order', function(){
					                    		checkShipToPopUpOrder();
					                    	});
					                    },
					                    onClosed: function() {
					                    	sessionStorage.setItem('selectedOrderSession','All');
				        					location.reload(true);
				        					window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';
				        				
					                    }
					                });
					            }
					        });
					}
		    });
			
			$(document).on('change', 'body.page-accountOrdersPage .sortSelect.js-page-sort', function () {	
					
					var $form = $("#sortForm1");
					if (!ACC.paginationsort.downUpPressed)
					{
						var searchParam = $("#searchOrderHistory").val();
						var dateSort= $("#dateSort").val();
						var viewpagination =$("#pageSizeOrder1").val();
						var accountShiptos = $("#shipToSelected_order").val();
						var searchParam = $("#search-ship-to").val();
						$form.find("input#search-shipto").val(searchParam);
						$form.find("input#shipToSelected_order").val(accountShiptos);
					$form.find("input#search-ordernew").val(searchParam);
					$form.find("input#dateSortOrder").val(dateSort);
					$form.find("input#ViewPaginationOrder").val(viewpagination);
					$form.find("input#accountShiptos").val(accountShiptos);
					$form.submit();											
					}
					
						ACC.paginationsort.downUpPressed = false;
			});
			
			/*$(document).on('click','.responsive-table-cell a',function(){				
				sessionStorage.removeItem('selectedInvoiceSession');
			});*/
			
			
					
					$(document).on("click", "#cboxWrapper .account-orderhistory-pagination-order a",function(e){
						var dateSortPopup= $("#dateSort").val();
						var page='';
						previous = $(this).value;
									e.preventDefault();	
									if($(this).attr('rel') == "prev"){
										page = $(this).parents("ul").find("li.active").text().split("(")[0]-2
										}
										else if($(this).attr('rel') == "next"){
										    page = $(this).parents("ul").find("li.active").text().split("(")[0]
										}
										else{
										page= $(this).text().trim()-1;
										}
									var data = {
											searchParam : ($("#search-ship-to-popup-order-new").val()).trim(),
											viewtype : 'All',
											page : page,
											dateSortPopup:dateSortPopup,
											sort : ($("#sortOptions1").length)?$("#sortOptions1").val() : $("#sortShipToOrder").val()
										}
									var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim() + '?paymentType=ALL';
									var currentSelect = $(".ship-TosOrder");
									var poupClass = $("#cboxOverlay").prop("class");
										$.ajax({
											url : shipToPopupURL,
											method : "GET",
											data : data,
											success : function(searchPageData) {
												ACC.colorbox.open('Search or Select a Ship-To', {
													html : searchPageData,
													width : '800px',
													className : poupClass,
													overlayClose : false,
													onComplete:function(){
														$(".ship-TosOrder").val('shipToopenPopupNew');
								        				
								        				retrieveShipToSortbySessionValue();
								        				checkShipToPopUpOrder();
													},
													onClosed: function() {
														sessionStorage.setItem('selectedOrderSession','All');
							        					location.reload(true);
							        					window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';
							        				
								                    }
												});
											}
										});
									
							});
					
				 
				$(document).on("blur", "#cboxWrapper #search-ship-to-popup-order",function(e) {
					 var dateSortPopup =$("#dateSort").data('datesort');
						 	var key = e.which;
						 	 previous = $(this).value;
						    
									var data = {
											searchParam : ($("#search-ship-to-popup-order").val()).trim(),
											dateSortPopup:dateSortPopup	
										}
									var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim() + '?paymentType=ALL';
									var currentSelect = $(".ship-TosOrder");
									removeShipToSortbySessionValue();
									var poupClass = $("#cboxOverlay").prop("class");
										$.ajax({
											url : shipToPopupURL,
											method : "GET",
											data : data,
											success : function(searchPageData) {
												ACC.colorbox.open('Search or Select a Ship-To', {
													html : searchPageData,
													width : '800px',
													className : poupClass,
													overlayClose : false,
													onComplete:function(){
													 	$(".ship-TosOrder").val('shipToopenPopupNew');
								        												        				
								        				checkShipToPopUpOrder();
								                    
													},
													onClosed: function() {
														
														sessionStorage.setItem('selectedOrderSession','All');
							        					location.reload(true);
							        					window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';
							        				
								                    }
												});
											}
										});
									
							});	
					
					
					
					
					
					
							$(document).on("change", "#cboxWrapper .shipToOrderPagePopup #sortOptions1", function(e) {
								var sortItem = $("#cboxWrapper #sortOptions1").val();
                               var dateSortPopup =$("#dateSort").data('datesort');
								var data = {
									searchParam : ($("#search-ship-to-popup-order").val()).trim(),
									viewtype : 'All',
									sort : sortItem,
									dateSortPopup:dateSortPopup
								}
								var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-orders/'+($("#unitUid").val()).trim() + '?paymentType=ALL';
								var poupClass = $("#cboxOverlay").prop("class");
								var currentSelect = $(".ship-TosOrder");
								$.ajax({
									url : shipToPopupURL,
									method : "GET",
									data : data,
									success : function(searchPageData) {
										ACC.colorbox.open('Search or Select a Ship-To', {
											html : searchPageData,
											width : '800px',
											className : $("#cboxOverlay").prop("class"),
											overlayClose : false,
											onComplete:function(){
											 	$(".ship-TosOrder").val('shipToopenPopupNew');
						        				
						        				retrieveShipToSortbySessionValue();
						        				checkShipToPopUpOrder();
						                    
											},
											onClosed: function() {
												sessionStorage.setItem('selectedOrderSession','All');
					        					location.reload(true);
					        					//window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';
					        				
												
						                    }
										});
									}
								});

							});
		
		},
		
		invmoreShipTos: function(){

			var data = {
							dateFromPopup:$("#dateFrom").val(),
							dateToPopup:$("#dateTo").val()
						}
					let searchVal = ($("#search-ship-to-popup-order").length)?($("#search-ship-to-popup-order").val()).trim():"";
						var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim()+'?searchParam='+searchVal;	
						removeShipToSortbySessionValue();
						var wShiptoPopup = '800px';
						var shipToPopupTitle = 'Search or Select a Ship-To';
						if(ACC.global.wWidth < 500){
							wShiptoPopup = '95%';
							shipToPopupTitle = 'Ship-to';
						}
						$.ajax({
					            url: shipToPopupURL,
					            data:data,
					            method: "GET",		
					            success: function(searchPageData) {
					                ACC.colorbox.open(shipToPopupTitle, {
					                    html: searchPageData,
					                    width: wShiptoPopup,
					                    className:"shipto-popup-order-mb invoice-shiptopopup",
					                    overlayClose: false,
					                    onComplete: function(e) {
					                    	//sessionStorage.removeItem('selectedOrderSession');
					                    	$(".ship-TosOrder").val('shipToopenPopupNew');

					        				checkShipToPopUpOrder();
					        				$(document).on('click keyup', '#search-ship-to-popup-order', function(){
					                    		checkShipToPopUpOrder();
					                    	});
					                    },
					                    onClosed: function() {
					                    	//sessionStorage.setItem('selectedOrderSession','All');
				        					location.reload(true);
				        					//window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';

					                    }
					                });
					            }
					        });	

		},
		invshipToAppBtn: function() {
			var currentSelect = $("input[name='shipToAc-m']:checked").val();
			if (ACC.global.wWidth > 1023) {
				currentSelect = $("input[name='shipToAc']:checked").val();
			}
			if(!currentSelect || currentSelect =="" || currentSelect == "All"){
				let unitId =  $("#sessionShipToId").length && $("#sessionShipToId").val() != "" ? $("#sessionShipToId").val() : $("#parentUnitId").length ? $("#parentUnitId").val() : "";
				unitId = !unitId || unitId == "" ? $("#unitId").val() : unitId;
				window.location.href = ACC.config.encodedContextPath + "/my-account/invoices/" + $.trim(unitId);
			}
			else{
				var $form = $("#invoicesearchForm");
				var searchParam = $("#search-invoice").val();
				var dateFrom = $("#dateFrom").val();
				var dateTo = $("#dateTo").val();
				var viewpagination = $("#pageSizeInvoice1").val();
				var searchParam = $("#search-ship-to").val();
				$form.find("input#search-shipto").val(searchParam);
				$form.find("input#search-invoicenew").val(searchParam);
				$form.find("input#dateFromnew").val(dateFrom);
				$form.find("input#dateTonew").val(dateTo);
				$form.find("input#ViewPaginationInvoice").val(viewpagination);
				$form.find("input#invShipTo").val(currentSelect);
				$form.submit();
			}
		},

		invShipToPopUpOnblur: function(){
			if(ACC.global.wWidth > 1023){
			ACC.invoicepage.invShipToNewPopUpAjax();
			}
		},

		invmoreShipToSearch: function(){
			ACC.invoicepage.invShipToNewPopUpAjax(true);
		},

		invmoreShipToClear: function(){
			$("input[name='sortByRadio']").prop('checked', false);
			$("#search-ship-to-popup-invoice-new").val('');
		},

		invshipToURLRadio: function(){
			var redURL = $("input[name='shiptoURL']:checked").val();
			window.location.href = redURL;
		},

		invShipToNewPopUpAjax: function(shortBy=false){

		var sortParam;

		if(shortBy == true){
			sortParam = $("input[name='sortByRadio']:checked").val();
			sessionStorage.setItem('selectedRadioValueSession',sortParam);
		}
		var wShiptoPopup = '800px';
		var shipToPopupTitle = 'Search or Select a Ship-To';
		if(ACC.global.wWidth < 500){
			wShiptoPopup = '95%';
			shipToPopupTitle = 'Ship-to';
		}

		var data = {
							searchParam : ($("#search-ship-to-popup-invoice-new").val()).trim(),
							dateFromPopup:$("#dateFrom").val(),
							dateToPopup:$("#dateTo").val(),
							sort:sortParam
						}
		let searchVal = ($("#search-ship-to-popup-order").length)?($("#search-ship-to-popup-order").val()).trim():"";
		var shipToPopupURL = ACC.config.encodedContextPath + '/my-account/all-ship-to-popup-invoice/'+($("#unitUid").val()).trim()+ '?paymentType=ALL';
		var poupClass = $("#cboxOverlay").prop("class");
		$.ajax({
			url : shipToPopupURL,
			method : "GET",
			data : data,
			success : function(searchPageData) {
			ACC.colorbox.open(shipToPopupTitle, {
			html : searchPageData,
			width : wShiptoPopup,
			className : poupClass,
			overlayClose : false,
			onComplete:function(){
				$(".ship-TosOrder").val('shipToopenPopupNew');
				checkShipToPopUpOrder();
				var getShortByRadio = sessionStorage.getItem("selectedRadioValueSession");
				if(getShortByRadio){
					$("input[name='sortByRadio'][value='"+getShortByRadio+"']").prop('checked', true);
					sessionStorage.removeItem("selectedRadioValueSession");
				}

			},
			onClosed: function() {

					sessionStorage.setItem('selectedOrderSession','All');
					location.reload(true);
					//window.location.href= ACC.config.encodedContextPath + '/my-account/orders/'+($("#unitUid").val()).trim()+'?accountShiptos=All&viewtype=All&searchParam=&dateSort='+($("#dateSort").val()).trim()+'&paymentType=ALL&pagesize=25&sort=byDate';

				}
			});
			}
			});
	}
	  
	  
};

function checkShipToPopUpInvoice(){
	var searchShipToPopupInvoiceVal = $('#search-ship-to-popup-invoice').val();
	disableShipToSearch(searchShipToPopupInvoiceVal);
}

function checkShipToPopUpOrder(){
	var searchShipToPopupOrderVal = $('#search-ship-to-popup-order').val();
	disableShipToSearch(searchShipToPopupOrderVal);
}

function disableShipToSearch(searchShipToVal){
	if (typeof searchShipToVal !== 'undefined') {
		if (searchShipToVal.size == 0 || searchShipToVal == '') {
			$('button[id="shipToSearchBoxButton"]').attr('disabled', 'disabled');
		}
		else {
			$('button[id="shipToSearchBoxButton"]').removeAttr("disabled");
		}
	}
}

function retrieveShipToSortbySessionValue() {
	if(sessionStorage.getItem("selectedDropdownValueSession")){
		$("#cboxWrapper .sortSelectSpan").val(sessionStorage.getItem("selectedDropdownValueSession"));
	}
}

function removeShipToSortbySessionValue() {
	sessionStorage.removeItem("selectedDropdownValueSession");
}

$( document ).ready(function() {
	var dataCount=$("#filterCount").val();

	if(null == dataCount || dataCount <= 0){
		$(".invoice-filter select").prop("disabled", true);
		$(".pageSizeDropDown  select").prop("disabled", true);
		$(".bia-sort-filters-container  select").prop("disabled", true);
		$(".ewallet_changes  select").prop("disabled", true);
		$("#shipToSelected_inv").prop("disabled", false);
		$("#shipToSelected_user").prop("disabled", false);
		$(".ship-TosOrder").prop("disabled", false); 
		$(".bia-show-filter").prop("disabled", false);
		$(".ship-ToseWallet").prop("disabled", false);
	}
});
