ACC.recommendedlist = {
	_autoload: [
		"bindPageLoadFunctions"
	],
	$productCodeField: '.productID_assemblyList',
	$assemblyproductcode: '.product-codedetail',
	bindPageLoadFunctions: function () {
		if ($(".page-savedListPage").length || $(".page-recommendedListPage").length || $(".page-detailsSavedListPage").length) {
			setTimeout(function () {
				$('[data-toggle="popover"]').each(function () {
					let target = $(this);
					if (target.hasClass("addListToCarts_filter") && target.attr("disabled") == "disabled") {
						target.popover({ placement: 'top', trigger: 'hover', container: 'body' });
						target.on('shown.bs.popover', function () {
							$(".popover").addClass("bg-danger");
						});
					}
					else if (!target.hasClass("addListToCarts_filter")) {
						target.popover({ placement: 'top', trigger: 'hover', container: 'body' });
					}
				});
			}, 1000);
		}
        if ($(".page-detailsRecommendedListPage").length) {
            ACC.recommendedlist.createFilter();
        }
	},
	recListUomSelecion: function(e) {
		let target = $(e);
		let targetParent = target.parents('.saved-list-sec');
		let productCode = targetParent.find(".productcode").val();
		let oldUomValue = targetParent.find("#inventoryUomId").val();
		let oldUomUnit = targetParent.find(".uomvalue").val();
		let selectedUomValue = target.find(":selected").data("inventory");
		let selectedUomUnit = target.find(":selected").data("uomdecs");
        let targetATCBtn = targetParent.find("#addToCartForm").find("button");
        let btnFound;
        if(targetParent.find("#addToCartForm").length && targetATCBtn.length){
            for (let i = 0; i < targetATCBtn.length; i++) {
                if(targetATCBtn.eq(i).is(":visible") && !targetATCBtn.eq(i).prop("disabled")){
                    targetATCBtn = targetATCBtn.eq(i);
                    btnFound = true;
                    break;
                }
            }
        }
		if (selectedUomUnit != oldUomUnit) {
			target.prop("disabled", true);
            if(btnFound){targetATCBtn.prop("disabled", true);}
			$.ajax({
				url: ACC.config.encodedContextPath + '/savedList/updateUOMforRecommendedListEntries',
				cache: false,
				type: "GET",
				dataType: 'json',
				contentType: 'application/json',
				data:
				{
					"productCode": productCode,
					"inventoryUomId": selectedUomValue
				},
				success: function (data) {
					target.prop("disabled", false);
                    if(btnFound){targetATCBtn.prop("disabled", false);}
					if (data && data.customerPrice) {
						let newYPrice = data.customerPrice.formattedValue;
						let newMultiplier = data.orderQuantityInterval? data.orderQuantityInterval : 1;
						//'Add List To Cart' Update
						if($(".saveList").length){
							let productList = $(".saveList").val().trim().split(" ");
							for (var i = 0; i < productList.length; i++) {
								if (productList[i].split("|")[0] == productCode) {
									productList[i] = productCode + "|" + productList[i].split("|")[1] + "|" + selectedUomValue;
									break;
								}
							}
							$(".saveList").val(productList.join(" "));
						}

						//Prie Section update
						let priceTarget = $('[data-uomid="' + oldUomValue + '"]');
						priceTarget.attr({ 'data-uomid': selectedUomValue, 'data-uomdesc': selectedUomUnit, 'data-uom': selectedUomUnit, 'data-inventorymultiplier': newMultiplier, 'data-customerprice': data.customerPrice.value }).data({ customerprice: data.customerPrice.value });
						priceTarget.find(".slp-yourprice-style").html(newYPrice + ' / ' + selectedUomUnit);
						priceTarget.find(".js-customer-price").val(data.customerPrice.value);
						// ./ 
						targetParent.find(".uomvalue").val(selectedUomUnit);
						targetParent.find("#inventoryUomId").val(selectedUomValue);	//Add To Cart Update
						targetParent.find(".inventryuom").val(selectedUomValue);	//Item select checkbox
						targetParent.find(".js-update-entry-quantity-input").trigger("keyup");
                        ACC.savedlist.listQtyUpdate(targetParent.find(".js-update-entry-quantity-input"), "recListUomSelecion");
						//ACC.savedlist.checkMinQuantityList(targetParent.parent().find(".quantity_updated"), false);
					}
					else {
						ACC.myquotes.quotePopupError();
					}
				},
				error: function () {
					ACC.myquotes.quotePopupError();
					target.prop("disabled", false);
                    if(btnFound){targetATCBtn.prop("disabled", false);}
				}
			});
		}
	},
	listDetailTotalCalculate: function(ref, onload) {
		let target = $(ref);
		let targetParent = target.parents(".saved-list-sec"), targetMinVal;
		let targetVal = target.val();
		let targetUOM = targetParent.find('.js-uom-selector').eq(0);
		if(onload && (targetParent.find('.intervalQtyInfo_list').length || targetParent.find('.minOrderQtyInfo_list').length) && (!targetUOM.length || (targetUOM.length && Number(targetUOM.find(":selected").data("inventorymultiplier")) == 1))){
			targetMinVal = target.data('min-qty') != "" ? Number(target.data('min-qty')) : 1;
			if (targetMinVal && targetMinVal > 1) {
				target.val(targetMinVal);
			}
			targetMinVal = target.data('min-orderqty') != "" ? Number(target.data('min-orderqty')) : 1;
			if (targetMinVal && targetMinVal > 1) {
				target.val(targetMinVal);
			}
		}
		else if(targetVal == "" || isNaN(targetVal) || !targetVal || Number(targetVal) == 0){
			target.val(1);
		}
		target.removeClass("text-white");
		targetVal = target.val();
		let price;
		targetParent.find(".js-qty-selector-input, .ValueId, .productqtyItem").val(targetVal);
		if (targetParent.find(".slp-yourprice-style").length) {
			price = targetParent.find(".print-row-price");
			price = price.data("customerprice") ? price.data("customerprice") : price.data("price");
			targetParent.find(".tot-amt-list").html(ACC.estimate.formatter.format(price * targetVal));
		}
		else {
			targetParent.find(".tot-amt-list").html("-");
		}
		ACC.recommendedlist.checkMinQuantityList(target);
	},
	checkMinQuantityList: function (ref) { //min-qty && min-orderqty
		let targetParent = ref.parents(".saved-list-sec");
		let target = targetParent.find(".js-update-entry-quantity-input");
		let targetVal = target.val() != "" ? Number(target.val().trim()) : 1;
		let targetUOM = targetParent.find('.js-uom-selector').eq(0);
		let targetMinVal;
		let targetErrorElem = targetParent.find('.intervalQtyInfo_list').length ? targetParent.find('.intervalQtyInfo_list') : targetParent.find('.minOrderQtyInfo_list');
		if (targetErrorElem.length && (!targetUOM.length || (targetUOM.length && Number(targetUOM.find(":selected").data("inventorymultiplier")) == 1))) {
			targetMinVal = target.data('min-qty') != "" ? Number(target.data('min-qty')) : 0;
			if (targetMinVal && targetMinVal > 1) {
				ACC.savedlist.toggleErrorState(targetParent, target, !(targetVal % targetMinVal == 0));
			}
			targetMinVal = target.data('min-orderqty') != "" ? Number(target.data('min-orderqty')) : 0;
			if (targetMinVal && targetMinVal > 1) {
				ACC.savedlist.toggleErrorState(targetParent, target, !(targetVal >= targetMinVal));
			}
			targetErrorElem.removeClass("hidden");
		}
		else{
			ACC.savedlist.toggleErrorState(targetParent, target, false);
			targetErrorElem.addClass("hidden");
		}
        ACC.savedlist.listQtyUpdate(ref, "checkMinQuantityList rec");
	},
    filterCheckBoxes: ["brand", "category"],
    optionText: [],
    optionCount: [],
    optionChecked: [],
    optionHandeling: [],
    oneBrandChecked: false,
    oneCategoryChecked: false,
    filterCheckFlag: false,
    createFilter: function () {
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            ACC.recommendedlist.optionText.push([]);
            ACC.recommendedlist.optionCount.push([]);
            ACC.recommendedlist.optionChecked.push([]);
            ACC.recommendedlist.optionHandeling.push([]);
        }
        $(".saved-list-sec").each(function (e) {
            let ref = $(this);
            let brandBlank;
            for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
                let filterType = ACC.recommendedlist.filterCheckBoxes[i];
                let filterText = ref.data('r' + filterType);
                let arrayIndex = $.inArray(filterText, ACC.recommendedlist.optionText[i]);
                let arrayRefLength;
                if (arrayIndex == -1) {
                    ACC.recommendedlist.optionText[i].push(filterText);
                    ACC.recommendedlist.optionCount[i].push(1);
                    ACC.recommendedlist.optionChecked[i].push(false);
                    ACC.recommendedlist.optionHandeling[i].push([]);
                    arrayRefLength = ACC.recommendedlist.optionText[i].length;
                    let target = $("#r-" + filterType);
                    target.find('.list-group').append('<label class="font-size-14 flex-center pointer transition-3s list-group-item" data-label' + filterType + '="' + filterText + '" for="selected-' + filterType + arrayRefLength + '"><span class="colored-primary hidden-print p-r-10"><input aria-label="Checkbox" id="selected-' + filterType + arrayRefLength + '" class="text-align-center list-group-item-check" onclick="ACC.recommendedlist.filterOptionsCheck(this, ' + arrayRefLength + ' , \'' + filterType + '\' , \'' + filterText + '\')" type="checkbox" value="' + filterText + '" data-role="' + arrayRefLength + '" data-' + filterType + '="' + filterText + '"></span>' + filterText + '<span class="list-group-item-badge" data-rdefault="1" data-rcount="1">(1)</span></label>');
                    if (filterText == "" || filterText == "No Brand") {
                        brandBlank = filterType;
                    }
                }
                else {
                    arrayRefLength = ACC.recommendedlist.optionCount[i][arrayIndex];
                    arrayRefLength++;
                    ACC.recommendedlist.optionCount[i][arrayIndex] = arrayRefLength;
                    let target = $('[data-label' + filterType + '="' + filterText + '"]');
                    target.find('.list-group-item-badge').html("(" + arrayRefLength + ")").data({ "rdefault": arrayRefLength, "rcount": arrayRefLength });
                }
            }
            if(brandBlank){
                $("#r-" + brandBlank + " .list-group").addClass("flex flex-dir-column");
                $('[data-label' + brandBlank + '="No Brand"]').css({order: 100});
            }
        });
        ACC.recommendedlist.filterTotalCount(true);
    },
    filterTotalCount: function (clickStatus) {
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let target = $('[for="selected-' + ACC.recommendedlist.filterCheckBoxes[i] + '0"]');
            let isParentVisible = $("#r-" + ACC.recommendedlist.filterCheckBoxes[i]).hasClass("active");
            let arrayRefLength = 0;
            if (clickStatus) {
                for (let j = 0; j < ACC.recommendedlist.optionText[i].length; j++) {
                    arrayRefLength += $('[data-label' + ACC.recommendedlist.filterCheckBoxes[i] + '="' + ACC.recommendedlist.optionText[i][j] + '"]').find(".list-group-item-badge").data('rdefault');
                }
                target.find('.list-group-item-badge').html("(" + arrayRefLength + ")").data({ "rdefault": arrayRefLength, "rcount": arrayRefLength });
            }
            else if (!isParentVisible || (isParentVisible && $.inArray(true, ACC.recommendedlist.optionChecked[i]) == -1)) {
                for (let j = 0; j < ACC.recommendedlist.optionCount[i].length; j++) {
                    arrayRefLength += ACC.recommendedlist.optionCount[i][j];
                }
                target.find('.list-group-item-badge').html("(" + arrayRefLength + ")").data({ "rdefault": arrayRefLength, "rcount": arrayRefLength });
            }
        }
    },
    filterSelectAllCheck: function (e, num, type, text) { //filter Select All clicked
        if (!ACC.recommendedlist.filterCheckFlag) {
            loading.start();
            let target = $(e);
            let targetChecked = target.prop("checked");
            ACC.recommendedlist.filterCheckFlag = true;
            $('[data-' + type + ']').each(function (e) {
                let ref = $(this);
                if (!$('[data-label' + type + ']').eq(e).hasClass("hidden") && ref.prop("checked") != targetChecked) {
                    ref.prop("checked", targetChecked);
                }
            });
            ACC.recommendedlist.filterItemShowChecking('all');
            ACC.recommendedlist.filterCheckFlag = false;
        }
    },
    filterOptionsCheck: function (e, num, type, text) { //filter Options clicked
        if (!ACC.recommendedlist.filterCheckFlag) {
            loading.start();
            ACC.recommendedlist.filterCheckFlag = true;
            ACC.recommendedlist.filterItemShowChecking('single');
            ACC.recommendedlist.filterCheckFlag = false;
        }
    },
    filterItemShowChecking: function (clicked) {
        ACC.recommendedlist.oneBrandChecked = false;
        ACC.recommendedlist.oneCategoryChecked = false;
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let tempData = [];
            let ref = $('[data-' + ACC.recommendedlist.filterCheckBoxes[i] + ']');
            let refLength = ref.length;
            for (let j = 0; j < refLength; j++) {
                let target = ref.eq(j);
                let targetVal = target.data(ACC.recommendedlist.filterCheckBoxes[i]); //brand or category text
                let targetIndex = $.inArray(targetVal, ACC.recommendedlist.optionText[i]);
                ACC.recommendedlist.optionHandeling[i][targetIndex] = [];
                if (target.prop("checked")) {
                    tempData.push(targetVal);
                    ACC.recommendedlist.optionChecked[i][targetIndex] = true;
                    ACC.recommendedlist.oneBrandChecked = (i == 0) ? true : ACC.recommendedlist.oneBrandChecked;
                    ACC.recommendedlist.oneCategoryChecked = (i == 1) ? true : ACC.recommendedlist.oneCategoryChecked;
                }
                else {
                    ACC.recommendedlist.optionChecked[i][targetIndex] = false;
                }
            }
        }
        if (ACC.recommendedlist.oneBrandChecked || ACC.recommendedlist.oneCategoryChecked) {
            let lastParent;
            $(".saved-list-sec").each(function (e) {
                let ref = $(this);
                let refBrand = ref.data('r' + ACC.recommendedlist.filterCheckBoxes[0]);
                let refCategory = ref.data('r' + ACC.recommendedlist.filterCheckBoxes[1]);
                let brandFlag = !ACC.recommendedlist.oneBrandChecked ? 1 : false; //if no brand selected
                let categoryFlag = !ACC.recommendedlist.oneCategoryChecked ? 1 : false; //if no category selected
                let brandIndex = $.inArray(refBrand, ACC.recommendedlist.optionText[0]);
                let categoryIndex = $.inArray(refCategory, ACC.recommendedlist.optionText[1]);
                if (ACC.recommendedlist.oneBrandChecked && ACC.recommendedlist.optionChecked[0][brandIndex]) {
                    brandFlag = 2;
                    lastParent = !lastParent ? ref : lastParent;
                }
                if (ACC.recommendedlist.oneCategoryChecked && ACC.recommendedlist.optionChecked[1][categoryIndex]) {
                    categoryFlag = 2;
                    lastParent = !lastParent ? ref : lastParent;
                }
                if (brandFlag && categoryFlag) { //shoing items
                    ref.removeClass("hidden");
                }
                else {
                    let refCheck = ref.find(".select_product_checkbox");
                    if (refCheck.prop("checked")) {
                        refCheck.trigger("click");
                    }
                    ref.addClass("hidden"); //hiding items
                }
                if (brandFlag == 2 && !ref.hasClass("hidden") && $.inArray(refCategory, ACC.recommendedlist.optionHandeling[0][brandIndex]) == -1) { //!ref.hasClass("hidden") need to check
                    ACC.recommendedlist.optionHandeling[0][brandIndex].push(refCategory);
                }
                if (categoryFlag == 2 && !ref.hasClass("hidden") && $.inArray(refBrand, ACC.recommendedlist.optionHandeling[1][categoryIndex]) == -1) {
                    ACC.recommendedlist.optionHandeling[1][categoryIndex].push(refBrand);
                }
            });
            ACC.recommendedlist.filterOptionShow();
        }
        else {	//showing all filter option and Items
            $(".saved-list-sec").removeClass("hidden");
            $('.list-group-item').each(function (e) {
                let target = $(this);
                if(!target.hasClass("list-group-item-hidden")){
                    target.removeClass("hidden");
                }
            });
        }
        ACC.recommendedlist.filterCountUpdate(!ACC.recommendedlist.oneBrandChecked && !ACC.recommendedlist.oneCategoryChecked); //updating filter count and total count
        if (clicked != "all") {
            ACC.recommendedlist.isfilterAllOptionCheck(clicked); //checking for Selection ALL selction
        }
    },
    filterOptionShow: function () {
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let refIndex = (i == 0) ? 1 : 0;
            let refText = ACC.recommendedlist.filterCheckBoxes[refIndex];
            let isParentVisible = $("#r-" + refText).hasClass("active");
            if (!isParentVisible || (isParentVisible && $.inArray(true, ACC.recommendedlist.optionChecked[refIndex]) == -1)) {
                let ref = $('[data-label' + refText + ']');
                if (i == 0 && !ACC.recommendedlist.oneBrandChecked) {
                    ref.removeClass("hidden");
                }
                else if (i == 1 && !ACC.recommendedlist.oneCategoryChecked) {
                    ref.removeClass("hidden");
                }
                else {
                    ref.removeClass("hidden");
                    ref.data("hidden", true);
                    for (let j = 0; j < ACC.recommendedlist.optionHandeling[i].length; j++) {
                        for (let k = 0; k < ACC.recommendedlist.optionHandeling[i][j].length; k++) {
                            $('[data-label' + refText + '="' + ACC.recommendedlist.optionHandeling[i][j][k] + '"]').data("hidden", false);
                        }
                    }
                    ref.each(function (e) {
                        let target = $(this);
                        if (target.data("hidden")) {
                            target.addClass("hidden");
                            if (target.find(".list-group-item-check").prop("checked")) {
                                target.find(".list-group-item-check").prop("checked", false);
                            }
                        }
                    });
                }
            }
        }
    },
    isfilterAllOptionCheck: function () {
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let type = ACC.recommendedlist.filterCheckBoxes[i];
            let target = $("#selected-" + type + "0");
            if ($("[data-label" + type + "]").length - $("[data-label" + type + "].hidden").length == $("[data-" + type + "]:checked").length) {
                if (!target.prop("checked")) {
                    target.prop("checked", true);
                }
            }
            else if (target.prop("checked")) {
                target.prop("checked", false);
            }
        }
    },
    filterCountUpdate: function (clickStatus) {
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let parentCheckedCount=0;
            ACC.recommendedlist.optionCount[i] = [];
            let isParentVisible = $("#r-" + ACC.recommendedlist.filterCheckBoxes[i]).hasClass("active");
            for (let j = 0; j < ACC.recommendedlist.optionText[i].length; j++) {
                ACC.recommendedlist.optionCount[i][j] = 0;
            }
            if (clickStatus) {
                for (let j = 0; j < ACC.recommendedlist.optionText[i].length; j++) {
                    let target = $('[data-label' + ACC.recommendedlist.filterCheckBoxes[i] + '="' + ACC.recommendedlist.optionText[i][j] + '"]');
                    let typeCount = target.find(".list-group-item-badge").data('rdefault');
                    target.find('.list-group-item-badge').html("(" + typeCount + ")").data({ "rcount": typeCount });
                }
            }
            else if (!isParentVisible || (isParentVisible && $.inArray(true, ACC.recommendedlist.optionChecked[i]) == -1)) {
                $(".saved-list-sec:visible").each(function (e) {
                    let ref = $(this);
                    let refType = ref.data('r' + ACC.recommendedlist.filterCheckBoxes[i]);
                    let refIndex = $.inArray(refType, ACC.recommendedlist.optionText[i]);
                    let typeCount;
                    let refCheckbox = ref.find(".select_product_checkbox");
                    if(refCheckbox.prop("checked")){
                        parentCheckedCount++;
                    }
                    typeCount = ACC.recommendedlist.optionCount[i][refIndex];
                    typeCount++;
                    ACC.recommendedlist.optionCount[i][refIndex] = typeCount;
                });
                for (let j = 0; j < ACC.recommendedlist.optionText[i].length; j++) {
                    let target = $('[data-label' + ACC.recommendedlist.filterCheckBoxes[i] + '="' + ACC.recommendedlist.optionText[i][j] + '"]');
                    if (!target.hasClass("hidden") && ACC.recommendedlist.optionCount[i][j] == 0) {
                        ACC.recommendedlist.optionCount[i][j] = target.find(".list-group-item-badge").data('rdefault');
                    }
                    let typeCount = ACC.recommendedlist.optionCount[i][j];
                    target.find('.list-group-item-badge').html("(" + typeCount + ")").data({ "rcount": typeCount });
                }
            }
            if($(".saved-list-sec:visible").length == parentCheckedCount && !$(".select_product_checkbox_all").prop("checked")){
                $(".select_product_checkbox_all").prop("checked", true);
            }
            else if($(".saved-list-sec:visible").length != parentCheckedCount && $(".select_product_checkbox_all").prop("checked")) {
                $(".select_product_checkbox_all").prop("checked", false);
            }
        }
        ACC.recommendedlist.filterTotalCount(clickStatus);
        ACC.recommendedlist.createFilterTags();
    },
    createFilterTags: function () {
        let targetParent = $('.filter-tags');
        targetParent.empty();
        let finalHTML = "";
        for (let i = 0; i < ACC.recommendedlist.filterCheckBoxes.length; i++) {
            let targetElem = ACC.recommendedlist.filterCheckBoxes[i];
            $("[data-" + targetElem + "]").each(function (e) {
                let target = $(this);
                let targetVal = target.data(targetElem);
                if (target.prop("checked")) {
                    finalHTML += ACC.recommendedlist.returnFilterTag(e, "checkbox", targetVal, targetElem);
                }
            });
        }
        if (finalHTML != "") {
            finalHTML += ACC.recommendedlist.returnFilterTag(0, "button", 'Clear All Filters');
            targetParent.append(finalHTML);
        }
        loading.stop();
    },
    returnFilterTag: function (index, type, text, targetElem) {
        let targetText = type == 'input' ? 'Text: "' + text + '"' : text;
        if (type == 'input' || type == 'checkbox') {
            return "<div class='text-default font-size-14 p-r-15 add-border-radius bg-light-gray border-light-grey transition-3s filter-tag'><span class='bold display-ib p-b-5 p-l-15 p-t-5 pad-rgt-10 transition-3s pointer' onclick=\"ACC.recommendedlist.clearFilterTag('" + index + "','" + type + "','" + targetElem + "','" + targetText + "')\">x</span><span>" + targetText + "</span></div>";
        } else {
            return '<button class="text-white font-size-14 transition-3s clearAllTagBtn" onclick="ACC.recommendedlist.resetListFilter()">' + targetText + '</button>';
        }
    },
    clearFilterTag: function (index, type, targetElem) {
        if (type == 'input') {
            $("[data-" + targetElem + "]").eq(index).val('');
        } else {
            $("[data-" + targetElem + "]").eq(index).trigger('click');
        }
    },
    resetListFilter: function () {
        for (let i = 0; i < ACC.recommendedlist.optionChecked.length; i++) {
            for (let j = 0; j < ACC.recommendedlist.optionChecked[i].length; j++) {
                if (ACC.recommendedlist.optionChecked[i][j]) {
                    $("[data-" + ACC.recommendedlist.filterCheckBoxes[i] + "]").eq(j).trigger('click');
                }
            }
        }
    }
}