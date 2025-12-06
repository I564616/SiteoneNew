ACC.ga4analytics = {
	
	_autoload: [
		"bindSelectItemEvent",
		"bindSearchEvent"
	],
	
	bindSelectItemEvent: function() {
		$(document).on("click", ".product-item-box a.thumb, .product-item-box a.name, .product-item-box a.js-click-tracking, .product-item-box .atc-variant-button a", function(e){
			var parentTarget = $(this).parents(".product-item-box");
			
			ACC.ga4analytics.findAndPushSelectEventData(parentTarget);
		});
		
		$(document).on("click", ".category-tile-item .atc-variant-button a", function(e) {
			var parentTarget = $(this).parents(".category-tile-item");
			
			ACC.ga4analytics.findAndPushSelectEventData(parentTarget);
		});
	},
	findAndPushSelectEventData: function(ref) {
		var code = ref.find("#ga4-productCode").val();
		var name = ref.find("#ga4-productName").val();
		var brand_name = ref.find("#ga4-brandName").val();
		var level1_category = ref.find("#ga4-categoryLeve1").val();
		var level2_category = ref.find("#ga4-categoryLeve2").val();
		var level3_category = ref.find("#ga4-categoryLeve3").val();
		var multidimensional = ref.find("#ga4-multidimensional").val();
		
		var price = 0;
		
		if(ga4analytics.isloggedin && multidimensional == "false") {
			var priceText = ref.find(".csp-price-format").find(".check_price").text().trim().replace('$','').replaceAll(',','');
			
			if(priceText.length){
				price = parseFloat(priceText);
			}
		}
		
		ACC.ga4analytics.select_item(code,name,price,brand_name,level1_category,level2_category,level3_category);
	},
	bindSearchEvent: function() {
		$(document).on("submit", "#searchBox", function(e){
			var search_term = $('#js-site-search-input').val();
			ACC.ga4analytics.search(search_term);
		});		
	},
	checkNotEmpty: function(val) {
		if(typeof val === 'string'){
			return val.length > 0;
		}
		return val != null;
	},
	generate_item_payload: function(code,name,price,brand_name,level1_category,level2_category,level3_category) {
		var item = {};
		if(ACC.ga4analytics.checkNotEmpty(code)) {
			item.item_id = code;
		}
		if(ACC.ga4analytics.checkNotEmpty(name)) {
			item.item_name = name;
		}
		if(ACC.ga4analytics.checkNotEmpty(price)) {
			item.price = price;
		}
		if(ACC.ga4analytics.checkNotEmpty(brand_name)) {
			item.item_brand = brand_name;
		}

		if(ACC.ga4analytics.checkNotEmpty(level1_category)) {
			item.item_category= level1_category;
		}

		if(ACC.ga4analytics.checkNotEmpty(level2_category)) {
			item.item_category2 = level2_category;
		}

		if(ACC.ga4analytics.checkNotEmpty(level3_category)) {
			item.item_category3 = level3_category;
		}

		return item;
	},
	generate_cart_item_payload: function (index, entryParent) {
		var target = $(entryParent + '.retail-your-price-section .atc-price-analytics').eq(index);
		var targetVal = 0;
		try {
			targetVal = (target.length && target.text() && target.text() != "") ? target.text() : '0.0';
			targetVal = parseFloat(targetVal.replace('$','').replaceAll(',',''));
		} catch (e) {
			targetVal = 0;
		}
		return Object.assign({},
   			{
				'quantity' : parseInt(ACC.ga4analytics.valueCheck('#productQty', index,'1'))
			},
			ACC.ga4analytics.generate_item_payload(
				ACC.ga4analytics.valueCheck('#productCode', index,''),
				ACC.ga4analytics.valueCheck('#productName', index,''),
				targetVal,
				ACC.ga4analytics.valueCheck('#productBrand', index,''),
				ACC.ga4analytics.valueCheck('#productCategory', index,''),
				ACC.ga4analytics.valueCheck('#productSubCategory', index,''),
				ACC.ga4analytics.valueCheck('#productLevel3Category', index,'')
			)
		);
	},
	valueCheck: function (target,index,defaultVal) { 
		return ($(target + index).length && $(target + index).val() != "") ? $(target + index).val() : defaultVal;
	},
	login: function() {
		dataLayer.push({
			'event': 'login',
			'account_classification': ga4analytics.account_classification,
		});
	},
	search: function(searchText) {
		if(ACC.ga4analytics.checkNotEmpty(searchText)) {
			dataLayer.push({
				'event': 'search',
				'search_term': searchText,
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification
			});
		}
	},
	view_item: function(code,name,price,brand_name,level1_category,level2_category,level3_category) {
		if(
			ACC.ga4analytics.checkNotEmpty(code) && 
			ACC.ga4analytics.checkNotEmpty(name) &&
			ACC.ga4analytics.checkNotEmpty(price)
		) {
			var item = ACC.ga4analytics.generate_item_payload(code,name,price,brand_name,level1_category,level2_category,level3_category);
			
			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'view_item',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'value': price,
				'ecommerce': {
					'currency': ga4analytics.currency,
					'items': [item]				
				}
			});
		}
	},
	view_item_list: function(items) {
		if((items || []).length) {
			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'view_item_list',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'ecommerce': {
					'currency': ga4analytics.currency,
					'items': items			
				}
			});
		}
	},
	add_to_cart: function(code,name,price,quantity,brand_name,level1_category,level2_category,level3_category) {
		if(
			ACC.ga4analytics.checkNotEmpty(code) && 
			ACC.ga4analytics.checkNotEmpty(name) &&
			ACC.ga4analytics.checkNotEmpty(price)
		) {
			var item = Object.assign({},
	   			{
					'quantity' : parseInt(quantity),
				},
				ACC.ga4analytics.generate_item_payload(code,name,price,brand_name,level1_category,level2_category,level3_category)
			);

			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'add_to_cart',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'value': parseFloat(price) * parseInt(quantity),
				'ecommerce': {
					'currency': ga4analytics.currency,
					'items': [item]				
				}
			});
		}
	},
	begin_checkout: function(items,total_cart_value) {
		if((items || []).length && (total_cart_value > 0)) {
			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'begin_checkout',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'value': total_cart_value,
				'ecommerce': {
					'currency': ga4analytics.currency,
					'items': items			
				}
			});
		}
	},
	purchase: function(items,transaction_id,total_cart_value,tax,shipping,coupon) {
		if((items || []).length && (total_cart_value > 0)) {

			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'purchase',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'ecommerce': {
					'currency': ga4analytics.currency,
					'transaction_id': transaction_id,
					'value': total_cart_value,
					'tax': tax,
				    'shipping': shipping,
				    'coupon': coupon,
					'items': items			
				}
			});
		}
	},
	select_item: function(code,name,price,brand_name,level1_category,level2_category,level3_category) {
		if(
			ACC.ga4analytics.checkNotEmpty(code) && 
			ACC.ga4analytics.checkNotEmpty(name) &&
			ACC.ga4analytics.checkNotEmpty(price)
		) {
			var item = ACC.ga4analytics.generate_item_payload(code,name,price,brand_name,level1_category,level2_category,level3_category);
			
			dataLayer.push({ ecommerce: null });
			dataLayer.push({
				'event': 'select_item',
				'visitor_status_evar': ga4analytics.visitor_status,
				'account_classification': ga4analytics.account_classification,
				'value': price,
				'ecommerce': {
					'currency': ga4analytics.currency,
					'items': [item]				
				}
			});
		}
	},
};