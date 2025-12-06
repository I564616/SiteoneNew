ACC.promotions = {
	_autoload: [
		"appliedProductpromotions",
        "bindPromotionBanner"
	],
	
	appliedProductpromotions : function ()
	{
		$('input[id^="productPromotion"]').each(function(i, e) {
			ACC.promotions.calculatePromotionPrice($(this), null);
		});
		
	},
	
	calculatePromotionPrice : function(item, productCode) 
	{
		var index;
		if (item.selector) {
			if(productCode != null) {
				index = item.selector.split("productPromotion")[1];
			} else {
				index = item[0].id.split("productPromotion")[1];
			}
		}
		
		var csp = $('#customerSpecificPriceValue'+index).val();
		var price;
		
		if($(item).data('price')) {
			price = $(item).data('price');
			/*if(productCode != null) {
				$('#basePrice' + index).html("<span class='black-title'><b>"+($(item).data('price'))+"</b></span>");
				$('#salePrice' + index).html('');
			}*/
		}
		
		if(csp != "" && csp != undefined) 
		 {
			price = csp;
		 }
		
		var promotion = $(item).val() ? $(item).val().split('||')[0] : '';
		var discount = $(item).val() ? $(item).val().split('||')[1] : '';
		var description =$(item).val() ? $(item).val().split('||')[2] : '';
		var discountPrice;
		var finalPrice;
		var basePriceHtml = $('#basePrice' + index).html();
		if(price) {
			if(promotion == "y_order_entry_fixed_discount")
			{
			  finalPrice=(price-discount).toFixed(2);
			  ACC.promotions.strikePrice(finalPrice, basePriceHtml, index, productCode,description);
			}
			if(promotion == "y_order_entry_fixed_price")
			{
				finalPrice=(discount/1).toFixed(2);
				ACC.promotions.strikePrice(finalPrice, basePriceHtml, index, productCode,description);
			}
			if(promotion == "y_order_entry_percentage_discount")
			{
				finalPrice=(price - (price*(discount/100))).toFixed(2);
				ACC.promotions.strikePrice(finalPrice, basePriceHtml, index, productCode,description);
			}
			if(promotion =="y_partner_order_entry_percentage_discount")
			{
				$('#description' + index).html('<span class="promo">'+ description +'</span>');
			}
			if(promotion =="y_partner_order_entry_fixed_price")
			{
				$('#description' + index).html('<span class="promo">'+ description +'</span>');
			}
			if(promotion =="y_target_bundle_price")
			{
				$('#description' + index).html('<span class="promo">'+ description +'</span>');
			}
			
		}
		
	},
	
	strikePrice : function(finalPrice, basePriceHtml, index, productCode,description)
	{
		 var formatter = new Intl.NumberFormat('en-US', {
          	  style: 'currency',
          	  currency: 'USD',
          	  minimumFractionDigits: 2,
       	 });
		if(!finalPrice.includes('-')){
		  if(productCode == null) {
			$('#salePrice' + index).html(formatter.format(finalPrice));
			basePriceHtml = "<span><del>" + basePriceHtml + "</del></span>";
			$('#basePrice' + index).html(basePriceHtml);
			description="<br><br><span class='promo'>"+ description +"</span>";
			$('#description' + index).html(description);
		} else {
			var cspPriceHtml = $('#customerSpecificPrice' + productCode).html();
			$('#cspSalePrice' + index).html(formatter.format(finalPrice));
			cspPriceHtml = "<span><del><b>" + cspPriceHtml + "</b></del></span>";
			$('#customerSpecificPrice' + productCode).html(cspPriceHtml);
			description="<br><br><span class='promo'>"+ description +"</span>";
			$('#description' + index).html(description);
		     }
		}
	},
	
	bindPromotionBanner : function ()
	{
		if($('.promotionName').length > 0) {
			var url;
			var bannerUrl = $('.promotionName').first().data("url");
			var notes = $('.promotionName').first().data("notes");
    		var code = $('.promotionName').first().data("promoCode");
    		var promoTitle = $('.promotionName').first().data("promoTitle");
    		var endDate =$('.promotionName').first().data("endDate");
    		var currentDate = $('.promotionName').first().data("currentDate");
    		var description = $('.promotionName').first().data("description");
    		var buttonLabel = $('.promotionName').first().data("buttonlabel");

    		var listOfCoupons = JSON.parse($(".listOfCoupons").first().val());
    		$.each(listOfCoupons,function(index,coupon){
    			if((coupon.type.indexOf("ItemType(AbstractCoupon)"))>0){
    				var couponCode = coupon.value;
    				imageHtml = '<div class="promo-banner-heading" style="width:100%;">&nbsp;&nbsp;'+ promoTitle +' </div><div class="promo-banner-heading2">Vivamus eleifend auctor odio integer</div><a href="#" data-end="' + endDate + '" data-description="' + description +  '" data-current="' + currentDate + '" data-coupon="' + couponCode + '" data-promo-notes="' + notes + '" data-promo-url="' + bannerUrl + '" " data-promo-title="' + promoTitle + '" id="buyToday"><button class="btn btn-primary promo-detail-btn">'+buttonLabel+'</button></a><img src="' + bannerUrl + '" width="500" height="300"/>';
    				return false;
    			} else {
    				imageHtml = '<div class="promo-banner-heading">&nbsp;&nbsp;'+ promoTitle +'</div><div class="promo-banner-heading2">Vivamus eleifend auctor odio integer</div><a href="' +  ACC.config.encodedContextPath + '/search/?q=%3Arelevance%3AsoallPromotions%3A' + code + '"><button class="btn btn-primary promo-detail-btn">'+buttonLabel+'</button></a><img src="' + bannerUrl + '" width="500" height="300"/>';
    			}
    		});
    		$('.promotionBanner').first().html(imageHtml);
    		$('#notes').html(notes);
		}
		$('.promotionName').on('click',function ()
    	{
    		var bannerUrl = $(this).data("url");
    		var notes = $(this).data("notes");
    		var code = $(this).data("promoCode");
    		var promoTitle = $(this).data("promoTitle");
    		var endDate = $(this).data("end");
    		var currentDate = $(this).data("current");
    		var description = $(this).data("description");
    		var buttonLabel = $(this).data("buttonlabel");
    		$('#notes').html(notes);
    		var imageHtml;
    		var listOfCouponsField = $(this).nextAll('input').first().val();

    		var listOfCoupons = JSON.parse(listOfCouponsField);
    		$.each(listOfCoupons,function(index,coupon){
    			if((coupon.type.indexOf("ItemType(AbstractCoupon)"))>0){
    				var couponCode = coupon.value;
    				imageHtml = '<div class="promo-banner-heading">&nbsp;&nbsp;'+ promoTitle +'</div><div class="promo-banner-heading2">Vivamus eleifend auctor odio integer</div><a href="#" data-coupon="' + couponCode + '" data-description="' + description +'" data-promo-notes="' + notes + '" data-promo-url="' + bannerUrl + '" data-promo-title="' + promoTitle + '" data-end="' + endDate + '" data-current="' + currentDate + '"  id="buyToday"><button class="btn btn-primary promo-detail-btn">'+buttonLabel+'</button></a><img src="' + bannerUrl + '" width="500" height="300"/>';
    				return false;
    			} else {
    				imageHtml = '<div class="promo-banner-heading">&nbsp;&nbsp;'+ promoTitle +'</div><div class="promo-banner-heading2">Vivamus eleifend auctor odio integer</div><a href="' +  ACC.config.encodedContextPath + '/search/?q=%3Arelevance%3AsoallPromotions%3A' + code + '"><button class="btn btn-primary promo-detail-btn">'+buttonLabel+'</button></a><img src="' + bannerUrl + '" width="500" height="300"/>';
    			}
    		});
    		$('.promotionBanner').first().html(imageHtml);
    		
    	});
		
		$('.mobilePromotionName').on('click',function (e)
		    	{
					e.preventDefault();
					var hasCoupon;
			        var code = $(this).data("mobilePromoCode");
			        var notes = $(this).data("mobileNotes");
					var url = $(this).data("mobileUrl");
					var title = $(this).data("mobilePromoTitle");
					var description = $(this).data("mobileDescription");
					var listOfCouponsField = $(this).nextAll('input').first().val();

		    		var listOfCoupons = JSON.parse(listOfCouponsField);

		    		$.each(listOfCoupons,function(index,coupon){
		    			if((coupon.type.indexOf("ItemType(AbstractCoupon)"))>0){
		    				var couponCode = coupon.value;
				    		/*if(currentDate > endDate)
				    			{
				    			$('#expiryMessage').text("This offer has expired. View current promotions");
				    			
				    			}*/
							var imageHtml;
							/*if(currentDate < endDate){*/
							$("#availableCouponCode").text(couponCode);
							$("#availableCouponTitle").text(title);
							$("#availableCouponNotes").text(notes);
							$("#availableCouponDescription").text(description);
							$("#promoComponent").addClass('hidden'); 
							$("#couponPage").removeClass('hidden');
							$(".promoPage").addClass('hidden');
							$(".tab-content").addClass('hidden');
							$("#notes").addClass('hidden');
							$(".promotionLine").addClass('hidden');
							$('#promoDetails').addClass('hidden');
							$("#promoPdfComponent").addClass('hidden');
							imageHtml='<img src="' + url + '" width="500" height="150"/><br/>'; 
							$('.couponPromotionBanner').html(imageHtml);
							hasCoupon = true;
							return false;
							/*}*/
		    			}
		    			else {
		    				hasCoupon = false;
		    			}
		    				
		    		});
					if(!hasCoupon) {
						window.location.href =  ACC.config.encodedContextPath +"/search/?q=%3Arelevance%3AsoallPromotions%3A" + code;
					}
		    	});
				
		$(document).on('click', '#buyToday', function ()
    	{
			
			var couponCode = $(this).data("coupon");
			var notes = $(this).data("promoNotes");
			var url = $(this).data("promoUrl");
			var title = $(this).data("promoTitle");
			var endDate = $(this).data("end");
			var description = $(this).data("description");
    		var currentDate = $(this).data("current");
    		/*if(currentDate > endDate)
    			{
    			$('#expiryMessage').text("This offer has expired. View current promotions");
    			
    			}*/
			var imageHtml;
			/*if(currentDate < endDate){*/
			$("#availableCouponCode").text(couponCode);
			$("#availableCouponTitle").text(title);
			$("#availableCouponNotes").text(notes);
			$("#availableCouponDescription").text(description);
			$("#promoComponent").addClass('hidden'); 
			$("#couponPage").removeClass('hidden');
			$(".promoPage").addClass('hidden');
			$(".tab-content").addClass('hidden');
			$(".promotionLine").addClass('hidden');
			$("#notes").addClass('hidden');
			$('#promoDetails').addClass('hidden');
			$("#promoPdfComponent").addClass('hidden');
			imageHtml='<img src="' + url + '" width="500" height="150"/><br/>'; 
			$('.couponPromotionBanner').html(imageHtml);
			$(".breadcrumbChange").html("<b>Monthly Specials</b>");
			/*}*/
    	});
		
		if($('.categoryName').length > 0) 
		{
			var uid = $('.categoryName').first().data("uid");
			var promoDiv = '#promotionTitle' +  uid;
			var promoHtml = $(promoDiv).html();
    		$('#promoDetails').html(promoHtml);
		}
		
		$('.categoryName').on('click',function ()
    	{
			var uid = $(this).data("uid");
			var promoDiv = '#promotionTitle' +  uid;
			var promoHtml = $(promoDiv).html();
    		$('#promoDetails').html(promoHtml);
    	});
		
		$(document).on('click', '#categoryLink', function ()
		{
			var promoCategory = $(this).data("promoCategory");
			var promoCategoryCode = $(this).data("promoCategoryCode");
			var promoCategoryDescription = $(this).data("promoCategoryDescription");
			var categoryPromoTitle = $(this).data("categoryPromoTitle");
			var url = $(this).data("categoryUrl");
			var notes=$(this).data("categoryNotes");
			var categoryEndDate = $(this).data("categoryEndDate");
			var categoryCurrentDate = $(this).data("categoryCurrentDate");
			
			/*if(categoryCurrentDate > categoryEndDate)
			{
			$('.expiryMessage').html("This offer has expired. View current promotions");
			
			}*/
			/*if(categoryCurrentDate < categoryEndDate){*/

			var listOfCategoryCoupons = JSON.parse($("#listOfCategoryCoupons").val());
			$.each(listOfCategoryCoupons,function(index,coupon){
			if((coupon.type.indexOf("ItemType(AbstractCoupon)"))>0){
					var couponCode = coupon.value;
						$("#promoComponent").addClass('hidden'); 
						$("#couponPage").removeClass('hidden');
						$(".promoPage").addClass('hidden');
						$('#promoDetails').addClass('hidden');
						$("#notes").addClass('hidden');
						$(".promotionLine").addClass('hidden');
     					$(".tab-content").addClass('hidden');
						$("#promoPdfComponent").addClass('hidden');
						$(".breadcrumbChange").html("<b>Monthly Specials</b>");
						/*$("#shopDeal").attr("href", "/search/?q=%3Arelevance%3AallCategories%3A" + promoCategory + "%3AsoallPromotions%3A" + promoCategoryCode);*/
						}
			$("#availableCouponCode").text(couponCode);
			$("#availableCouponTitle").text(categoryPromoTitle);
			$("#availableCouponDescription").text(promoCategoryDescription);
			imageHtml='<img src="' + url + '" width="500" height="150"/><br/>'; 
			$('.couponPromotionBanner').html(imageHtml);
			$("#availableCouponNotes").text(notes);			
		});
		/*}*/
		});	
		 $(document).on('click', '#noCategoryLink', function ()
			{
			 var promoCategoryCode = $(this).data("promoNoCategoryCode");
			 var categoryPromoTitle = $(this).data("noCategoryPromoTitle");
			 var notes=$(this).data("noCategoryNotes");
			 var promoCategoryDescription = $(this).data("noPromoCategoryDescription");
			 var url = $(this).data("promoNoCategoryUrl");
			 /*if(currentDate > endDate)
 			{
 			$('.expiryMessage').html("This offer has expired. View current promotions");
 			
 			}*/

			 var listOfCategoryCoupons = JSON.parse($("#listOfCategoryCoupons").val());
			$.each(listOfCategoryCoupons,function(index,coupon){
			   if((coupon.type.indexOf("ItemType(AbstractCoupon)"))>0){
					var couponCode = coupon.value;
					$("#promoComponent").addClass('hidden'); 
					$("#couponPage").removeClass('hidden');
					$(".promoPage").addClass('hidden');
					$('#promoDetails').addClass('hidden');
					$("#notes").addClass('hidden');
					$(".tab-content").addClass('hidden');
					$(".promotionLine").addClass('hidden');
					$("#promoPdfComponent").addClass('hidden');
					$("#availableCouponCode").text(couponCode);
					$(".breadcrumbChange").html("<b>Monthly Specials</b>");
					/*$("#shopDeal").attr("href", "/search/?q=%3Arelevance%3AsoallPromotions%3A" + promoCategoryCode);*/
					
					
				}
			   $("#availableCouponTitle").text(categoryPromoTitle);
			   imageHtml='<img src="' + url + '" width="500" height="150"/><br/>'; 
			    $('.couponPromotionBanner').html(imageHtml);
			    $("#availableCouponNotes").text(notes);	
			    $("#availableCouponDescription").text(promoCategoryDescription);
			    
			  
									
			});	
									
		});
      
		
	}

}