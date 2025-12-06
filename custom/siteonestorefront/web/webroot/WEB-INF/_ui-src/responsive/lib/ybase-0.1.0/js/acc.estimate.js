ACC.estimate = {
    _autoload: [
        "generateEstimatePage"
    ],
    generateEstimatePage: function () {
        if ($(".estimate-page").length) {
            _AAData.pathingChannel = "my account";
            _AAData.pathingPageName = "my account: savedList: generateEstimate:";
            let nowTime = new Date().toString().split(" ");
            $(".estimate-date").val(nowTime[1] + " " + nowTime[2] + ", " + nowTime[3]);
            ACC.estimate.calculateEstimateTotal();
            $(".estimate-table-input").on("change", ACC.estimate.calculateEstimateTotal);
            $(".estimate-table-input").on("keyup", ACC.estimate.calculateEstimateTotal);
            document.addEventListener('visibilitychange', function (event) { //re setting popup position
                if (!document.hidden && $(".estimate-successmsg").length && $(".estimate-successmsg").css("display") != "none") {
                    ACC.estimate.estimatePDFPopup();
                }
            });
            $(window).on("resize", function () {
                if ($(".estimate-successmsg").length && $(".estimate-successmsg").css("display") != "none") {
                    ACC.estimate.estimatePDFPopup();
                }
            });
            $("#uploadImage").ajaxForm({
                beforeSubmit: function() {loading.start();},
                success: function() {
                    $(".contractor-logo").eq(0).attr('src', $(".contractor-logo").eq(1).attr('src'));
                    $(".delete-logo-btn, .contractor-logo-btn").show();
                    $(".upload-logo-btn").hide();
                    ACC.estimate.popupEffect("hide","drop",".logo-popup");
                    loading.stop();
                }
            });
        }
    },
    formatter: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }),
    pdfTextArray: function (num) {
        return $("[data-translation='" + num + "']").text();
    },
    estimatePDFPopup: function () {
        ACC.colorbox.open("", {
            html: $(".estimate-modal").html(),
            width: "550px",
            className: "estimate-successmsg"
        });
    },
    removeEstimateRow: function (target) {
        let ref = $(target);
        ref.parents(".estimate-table").slideUp(function () {
            $(this).remove();
            $(".custom-category").addClass("hidden");
            ACC.estimate.calculateEstimateTableRow();
        });
    },
    addEstimateRow: function (target) {
        let ref = $(target);
        let defaultText = $(".custom-category .custom-category-value").eq(0).val();
        let tableRow = $(".estimate-table");
        let rowNum = (tableRow.eq(tableRow.length - 1).hasClass('bg-offwhite') ? 'bg-white' : 'bg-offwhite');
        let newRow = '<div class="row margin0 br-3 text-center flex-center justify-center estimate-table ' + rowNum + '" style="display:none;"><div class="col-md-1 estimate-table-row-num"></div><div class="col-md-3 padding0"><input type="text" class="form-control estimate-product" placeholder="' + ACC.estimate.pdfTextArray(0) + '"></div><div class="col-md-2"><button onclick="ACC.estimate.customCategoryView(this, \'' + defaultText + '\')" type="button" class="movetoList-text custom-category-text transition-3s" data-categorypopup="hide"><span class="category-text">' + defaultText + '</span><span class="badge transition-3s"><svg xmlns="http://www.w3.org/2000/svg" width="8" height="5.167" viewBox="0 0 8 5.167"><path d="M3.527,157.74l-3.4-3.4a.6.6,0,0,1,0-.848l.565-.565a.6.6,0,0,1,.848,0l2.411,2.411,2.411-2.411a.6.6,0,0,1,.848,0l.565.565a.6.6,0,0,1,0,.848l-3.4,3.4A.6.6,0,0,1,3.527,157.74Z" transform="translate(0.05 -152.75)" fill="#50a0c5"></path></svg></span></button></div><div class="col-md-1 padding0"><input type="text" class="form-control text-center estimate-sku" placeholder="' + ACC.estimate.pdfTextArray(1) + '"></div><div class="col-md-1 padding0 after-price"><input type="number" min="0" max="9999999999" class="form-control estimate-table-input estimate-price" placeholder="' + ACC.estimate.pdfTextArray(2) + '"></div><div class="col-md-1 padding0"><input type="number" min="1" max="9999999999" class="form-control text-center estimate-table-input estimate-quantity" placeholder="' + ACC.estimate.pdfTextArray(3) + '"></div><div class="col-md-1 padding0 after-prcentage"><input type="number" min="0" max="9999999999" class="form-control text-right estimate-table-input estimate-markup" placeholder="' + ACC.estimate.pdfTextArray(4) + '"></div><div class="col-md-1 padding0"><input type="text" class="form-control text-right estimate-table-input estimate-table-total"></div><div class="col-md-1 padding0"><button class="btn btn-red" onclick="ACC.estimate.removeEstimateRow(this)"><strong>x</strong></button></div></div>';
        $(newRow).insertBefore(ref);
        $(".estimate-table").eq(tableRow.length).slideDown();
        $(".estimate-table-input").on("change", ACC.estimate.calculateEstimateTotal);
        $(".estimate-table-input").on("keyup", ACC.estimate.calculateEstimateTotal);
        ACC.estimate.calculateEstimateTableRow();
    },
    estimatePDF: async function (type) {
        var jsPDF = window.jspdf.jsPDF;
        if (jsPDF && jsPDF.version) {
            $('#dversion').text('Version ' + jsPDF.version);
        }
        var doc = new jsPDF({ putOnlyUsedFonts: true, orientation: "portrait" });
        
        var logoSrc= $(".contractor-logo").eq(0).attr("src");
        
        var haveLogo = logoSrc != "";
        if(haveLogo){
        	var imageWidthHeight = await ACC.estimate.calculateImageWidthHeight(logoSrc);
        	
        	doc.addImage(logoSrc, 'JPEG', 10, 6, imageWidthHeight.width, imageWidthHeight.height)
        }
        var y=10;
        var ca = 24;
        if(haveLogo){
        	var logoHeight = imageWidthHeight.height;
        	y = y+ 9 + logoHeight;
        	ca = ca + 5 + logoHeight;
        }
        var textForWidth;
        doc.setFontSize(18);
        doc.text($(".estimate-title").text(), 10, y); // left line 1 10
        doc.setFontSize(12);
        doc.setTextColor(150);
        textForWidth = ACC.estimate.pdfTextArray(5);
        doc.text(textForWidth, 126 - doc.getTextWidth(textForWidth), 10);
        textForWidth = ACC.estimate.pdfTextArray(6).toUpperCase();
        doc.text(textForWidth, 126 - doc.getTextWidth(textForWidth), 16);
        textForWidth = ACC.estimate.pdfTextArray(7);
        doc.text(textForWidth, 126 - doc.getTextWidth(textForWidth), ca); // 24
        textForWidth = ACC.estimate.pdfTextArray(8);
        ca+=6;
        doc.text(textForWidth, 126 - doc.getTextWidth(textForWidth), ca); //30
        doc.setTextColor(0);
        doc.setFont("helvetica", "bold");
        y +=10;
        doc.text($(".company-name").text(), 10, y); // left line 2 24
        ca-=6
        doc.text($(".customer-name").val(), 130, ca); // 24
        doc.setFont("helvetica", "normal");
        doc.text($(".estimate-date").val(), 130, 10);
        textForWidth = $(".estimate-number").val();
        let fileName = "estimate" + textForWidth + ".pdf";
        doc.text(textForWidth, 130, 16);
        y +=6;
        doc.text($(".company-address").eq(0).text(), 10, y);  // left line 3 30
        y +=6;
        doc.text($(".company-address").eq(1).text(), 10, y);  // left line 4 36
        y +=6;
        doc.text($(".company-phone").text(), 10, y);  // left line 5 42
        y +=6;
        doc.text($(".company-email").text(), 10, y);  // left line 6 48
        
        let posY = 35;
        let nextAddress = [];
        let address = $.trim($(".customer-address-line1").val()); //chekking address lines
        let addressLine = (doc.getTextWidth(address) > 72) ? address.substr(0, 36) : address;
        ca+=6;
        doc.text(addressLine, 130, ca); // 30
        address = $.trim(address.substr(36));
        if(address != ""){
			nextAddress.push(address+",");
		};
        address = $.trim($(".customer-address-line2").val());
		if(address != ""){
			nextAddress.push(address+",");
		};
		address = $.trim($(".customer-city").val());
		if(address != ""){
			nextAddress.push(address);
		};
        address = $.trim($(".customer-state option:selected").text());
		if(address != ACC.estimate.pdfTextArray(15)){
			nextAddress.push(address);
		};
        address = $.trim($(".customer-zip").val());
		if(address != ""){
			nextAddress.push(address);
		};
        address = nextAddress.join(" ");
        addressLine = (doc.getTextWidth(address) > 72) ? address.substr(0, 36) : address;
        ca +=5;
        doc.text(addressLine, 130, ca); // 35
        nextAddress = [];
        address = $.trim(address.substr(36));
        if(address != ""){
			posY += 5;
			ca +=5;
			doc.text(address, 130, ca);
		};
        
		ca +=5;
        doc.text($(".customer-phone").val(), 130, ca);
        ca +=5;
        doc.text($(".customer-email").val(), 130, ca);
        doc.setFillColor(153, 153, 153);
        var line1Y=55;
        if(haveLogo){
        	line1Y=y+7;
        }
        doc.line(10, line1Y, 200, line1Y, "F"); // 55
        doc.setFillColor(65, 66, 68);
        line1Y+=5;
        doc.roundedRect(10, line1Y, 190, 10, 1, 1, "F"); // 60
        doc.setTextColor(255, 255, 255);
        line1Y+=7;
        doc.text(ACC.estimate.pdfTextArray(9), 13, line1Y);
        doc.text(ACC.estimate.pdfTextArray(11), 187, line1Y);
        posY = 82;
        if(haveLogo){
        	posY=line1Y+15;
        }
        let categoryArray = [];
        var catTotal;
        var catTemp;
        if (type == "category_details") {
            doc.text(ACC.estimate.pdfTextArray(10), 135, line1Y);
            doc.setTextColor(0, 0, 0);
            for (let i = 0; i < $(".estimate-table").length; i++) {
                let ref = $(".estimate-table").eq(i);
                let cat = ref.find(".category-text").text();
                if ($.inArray(cat, categoryArray) == -1) {
                    doc.setTextColor(0);
                    doc.setFont("helvetica", "bold");
                    categoryArray.push(cat);
                    if (i != 0) {
                        doc.setFontSize(10);
                        doc.text(catTemp + " " + ACC.estimate.pdfTextArray(14), 14, posY);
                        catTotal = ACC.estimate.formatter.format(catTotal);
                        doc.text(catTotal, 196 - doc.getTextWidth(catTotal), posY);
                        posY += 12;
                    }
                    catTemp = cat;
                    catTotal = 0;
                    if (posY > 265) { doc.addPage(); posY = 10; }
                    doc.setFontSize(11);
                    doc.text(cat, 10, posY);
                    posY += 5;
                    doc.setFillColor(0, 0, 0);
                    doc.line(10, posY, 200, posY, "F");
                    doc.setFont("helvetica", "normal");
                    posY += 6;
                    for (let j = 0; j < $(".estimate-table").length; j++) {
                        let target = $(".estimate-table").eq(j);
                        let targetCat = target.find(".category-text").text();
                        let sku = target.find(".estimate-sku").val();
                        let product = target.find(".estimate-product").val();
                        let qty = target.find(".estimate-quantity").val();
                        let total = target.find(".estimate-table-total").val().replace("$","");
                        qty = (qty < 1) ? "1" : qty;
                        if (cat == targetCat) {
                            if (posY == 21 || posY > 270) {
                                if (posY > 270) { doc.addPage(); posY = 10; }
                                doc.setFillColor(65, 66, 68);
                                doc.roundedRect(10, posY, 190, 10, 1, 1, "F");
                                posY += 6;
                                doc.setFontSize(12);
                                doc.setTextColor(255, 255, 255);
                                doc.text(ACC.estimate.pdfTextArray(9), 13, posY);
                                doc.text(ACC.estimate.pdfTextArray(10), 135, posY);
                                doc.text(ACC.estimate.pdfTextArray(11), 187, posY);
                                posY += 15;
                            }
                            catTotal += Number(total);
                            doc.setFontSize(10);
                            doc.setTextColor(150);
                            doc.text(sku, 14, posY);
                            posY += 5;
                            doc.setTextColor(0);
                            product = (doc.getTextWidth(product) > 110) ? product.substr(0, 66) + "..." : product;
                            doc.text(product, 14, posY);
                            doc.text(qty, 135, posY - 3);
                            doc.text(ACC.estimate.formatter.format(total), 196 - doc.getTextWidth(total), posY - 3);
                            posY += 5;
                            doc.setFillColor(153, 153, 153);
                            doc.line(10, posY, 200, posY, "F");
                            posY += 6;
                        }
                    }
                }
            }
            doc.setTextColor(0);
            doc.setFont("helvetica", "bold");
            doc.setFontSize(10);
            doc.text(catTemp + " " + ACC.estimate.pdfTextArray(14), 14, posY);
            catTotal = ACC.estimate.formatter.format(catTotal);
            doc.text(catTotal, 196 - doc.getTextWidth(catTotal), posY);
            posY += 12;
        }
        else if (type == "category_only") {
            doc.setTextColor(0, 0, 0);
            for (let i = 0; i < $(".estimate-table").length; i++) {
                let ref = $(".estimate-table").eq(i);
                let cat = ref.find(".category-text").text();
                if ($.inArray(cat, categoryArray) == -1) {
                    categoryArray.push(cat);
                    catTotal = 0;
                    if (posY > 260) {
                        doc.addPage();
                        posY = 10;
                        doc.setFillColor(65, 66, 68);
                        doc.roundedRect(10, posY, 190, 10, 1, 1, "F");
                        posY += 6;
                        doc.setFontSize(12);
                        doc.setTextColor(255, 255, 255);
                        doc.text(ACC.estimate.pdfTextArray(9), 13, posY);
                        doc.text(ACC.estimate.pdfTextArray(11), 187, posY);
                        posY += 15;
                    }
                    for (let j = 0; j < $(".estimate-table").length; j++) {
                        let target = $(".estimate-table").eq(j);
                        let targetCat = target.find(".category-text").text();
                        let total = target.find(".estimate-table-total").val().replace("$","");
                        if (cat == targetCat) {
                            catTotal += Number(total);
                        }
                    }
                    doc.setTextColor(0);
                    doc.setFont("helvetica", "bold");
                    doc.setFontSize(10);
                    doc.text(cat, 14, posY);
                    catTotal = ACC.estimate.formatter.format(catTotal);
                    doc.text(catTotal, 196 - doc.getTextWidth(catTotal), posY);
                    posY += 5;
                    doc.setFillColor(153, 153, 153);
                    doc.line(10, posY, 200, posY, "F");
                    posY += 6;
                }
            }
            posY += 6;
        }
        else if (type == "details_only") {
            doc.text(ACC.estimate.pdfTextArray(10), 135, line1Y);
            doc.setTextColor(0, 0, 0);
            for (let i = 0; i < $(".estimate-table").length; i++) {
                if (posY > 270) {
                    doc.addPage();
                    posY = 10;
                    doc.setFillColor(65, 66, 68);
                    doc.roundedRect(10, posY, 190, 10, 1, 1, "F");
                    posY += 6;
                    doc.setFontSize(12);
                    doc.setTextColor(255, 255, 255);
                    doc.text(ACC.estimate.pdfTextArray(9), 13, posY);
                    doc.text(ACC.estimate.pdfTextArray(10), 135, posY);
                    doc.text(ACC.estimate.pdfTextArray(11), 187, posY);
                    posY += 11;
                }
                let target = $(".estimate-table").eq(i);
                let sku = target.find(".estimate-sku").val();
                let product = target.find(".estimate-product").val();
                let qty = target.find(".estimate-quantity").val();
                let total = target.find(".estimate-table-total").val().replace("$","");
                qty = (qty < 1) ? "1" : qty;
                doc.setFontSize(10);
                doc.setTextColor(150);
                doc.text(sku, 12, posY);
                posY += 5;
                doc.setTextColor(0);
                product = (doc.getTextWidth(product) > 120) ? product.substr(0, 70) + "..." : product;
                doc.text(product, 12, posY);
                doc.text(qty, 135, posY - 3);
                doc.text(ACC.estimate.formatter.format(total), 196 - doc.getTextWidth(total), posY - 3);
                posY += 5;
                doc.setFillColor(153, 153, 153);
                doc.line(10, posY, 200, posY, "F");
                posY += 8;
            }
        } // ./table row loop
        if (posY > 280) { doc.addPage(); posY = 10; }
        doc.setTextColor(150);
        doc.text(ACC.estimate.pdfTextArray(12), 10, posY);
        textForWidth = ACC.estimate.pdfTextArray(13);
        doc.text(textForWidth, 198 - doc.getTextWidth(textForWidth), posY);
        posY += 10;
        doc.setFontSize(18);
        doc.setTextColor(0);
        textForWidth = $(".estimate-total").eq(0).text();
        doc.text(textForWidth, 198 - doc.getTextWidth(textForWidth), posY);
        doc.setTextColor(0);
        doc.setFont("helvetica", "normal");
        doc.setFontSize(10);
        let addMessage = $(".add-message").val().split("\n");
        for (let i = 0; i < addMessage.length; i++) {
            for (let j = 0; j < 7000; j += 80) {
                let messageLine = $.trim(addMessage[i].substring(j, j + 80));
                if (messageLine != "") {
                    if (posY > 280) { doc.addPage(); posY = 10; }
                    doc.text(messageLine, 10, posY);
                    posY += 5;
                }
                else {
                    break;
                }
            }
        }
        doc.save(fileName);
        setTimeout(function () {
            ACC.estimate.estimatePDFPopup()
        }, 1000);
    },
    calculateEstimateTableRow: function () {
        $(".estimate-table").each(function (index) {
            var ref = $(this);
            ref.children(".estimate-table-row-num").html('<strong>' + (index + 1) + '</strong>');
        });
        ACC.estimate.calculateEstimateTotal();
    },
    calculateEstimateTotal: function () {
        let pageTotal = 0;
        $(".estimate-table").each(function () {
            let ref = $(this);
            let p = Number(ref.find(".estimate-table-input").eq(0).val());
            let q = Number(ref.find(".estimate-table-input").eq(1).val());
            let m = Number(ref.find(".estimate-table-input").eq(2).val());
            p = (isNaN(p) || p == "" || p < 0) ? 0 : p;
            q = (isNaN(q) || q == "" || q <= 0) ? 1 : q;
            m = (isNaN(m) || m == "" || m < 0) ? 0 : m;
            let t = p * q + p * q * m / 100;
            ref.find(".estimate-table-input").eq(1).val(q)
            ref.find(".estimate-table-input").eq(3).val("$" + t.toFixed(2));
            pageTotal += t;
        });
        $(".estimate-total").html(ACC.estimate.formatter.format(pageTotal));
    },
    customCategoryView: function (e, val) { //opening the custom category popup
        let ref = $(e);
        let page = $(".estimate-page");
        let target = $(".custom-category");
        ACC.estimate.popupEffect("hide","drop",".logo-popup");
        ACC.estimate.popupEffect("hide","fold",".estimate-popup");
        ACC.estimate.customCategoryList(val); //creating category lists
        if (ref.attr("data-categorypopup") == "hide") {
            page.find("[data-categorypopup='show']").attr("data-categorypopup", "hide").removeAttr("style").children(".badge").removeAttr("style");
            ref.attr("data-categorypopup", "show").css({ backgroundColor: "#f1f2f2" }).children(".badge").css("transform", "rotate(180deg)");
            ACC.estimate.customCategoryMode("view");
            target.removeClass("hidden").css({ left: ref.offset().left - page.offset().left, top: ref.offset().top - page.offset().top + 42 }).slideDown();
        }
        else {
            ref.attr("data-categorypopup", "hide").removeAttr("style").children(".badge").removeAttr("style");
            target.slideUp(function () {
                $(this).addClass("hidden")
            });
        }
    },
    customCategoryList: function (val) { //creating category lists
        let target = $(".custom-category");
        var lists = "";
        target.find(".custom-category-value").each(function () {
            let ref = $(this);
            let refVal = ref.attr("data-val");
            ref.val(refVal);
            lists += '<button onclick="ACC.estimate.customCategorySet(\'' + refVal + '\')" class="list-group-item ' + ((refVal == val) ? 'active' : '') + '">' + refVal + '</button>';
        });
        target.find(".list-group").html(lists);
    },
    customCategoryMode: function (type) { //changing the mode of the popup
        let offType = (type == "edit") ? "view" : "edit";
        let target = $(".custom-category");
        target.find(".custom-category-value").data("removeme", false).parent(".mode-edit").removeAttr("style");
        target.find(".mode-" + type).removeClass("hidden");
        target.find(".mode-" + offType).addClass("hidden");
    },
    customCategorySet: function (val) { //setting the new category
        $(".custom-category .list-group .list-group-item").each(function () {
            let target = $(this);
            let page = $(".estimate-page").find("[data-categorypopup='show']");
            if (target.html() == val) {
                target.addClass("active");
                page.removeAttr("style").children(".badge").removeAttr("style");
                page.attr({ onclick: "ACC.estimate.customCategoryView(this, '" + val + "')", "data-categorypopup": "hide" }).children(".category-text").html(val);
                $(".custom-category").slideUp(function () {
                    $(this).addClass("hidden")
                });
            }
            else {
                target.removeClass("active");
            }
        });
    },
    customCategoryAdd: function () { //adding a new category
        let ref = $(".custom-category");
        let val = $.trim(ref.find(".custom-category-add").val());
        ref.find(".custom-category-add").val("");
        if (val != "") {
            let foundMatch;
            $(".custom-category .list-group .list-group-item").each(function () {
                let target = $(this);
                if (target.html().toLowerCase() == val.toLowerCase()) {
                    foundMatch = true;
                    target.animate({ color: '#ff0000', backgroundColor: "#f2dcdc" }, 1000).effect('shake', 500, function () {
                        $(this).removeAttr("style");
                    });
                }
            });
            if (!foundMatch) {
                ref.find(".list-group").append('<button onclick="ACC.estimate.customCategorySet(\'' + val + '\')" class="list-group-item">' + val + '</button>');
                ref.find(".scroll-bar").append('<div class="input-group m-b-5 mode-edit hidden"><input type="text" class="form-control custom-category-value" data-val="' + val + '" value="' + val + '"><span class="input-group-btn br-3"><button class="btn btn-red bold-text" onclick="ACC.estimate.customCategoryRemove(this, \'' + val + '\')" type="button">x</button></span></div>');
            }
        }
    },
    customCategoryRemove: function (e) { //adding element to remove list
        $(e).parents(".mode-edit").effect('drop', 500, function () {
            $(this).children(".custom-category-value").data("removeme", true);
        });
    },
    customCategoryUpdate: function () { //updating custom category lists
        let defaultval;
        $(".custom-category .custom-category-value").each(function (index) {
            let target = $(this);
            let oldValue = target.attr("data-val");
            let newText;
            defaultval = (index == 0) ? target.val() : defaultval;
            if (target.data("removeme")) { //removing element
                target.parent(".input-group").remove();
                newText = defaultval;
            }
            else if (target.attr("data-val") != target.val()) { //upaiting text
                newText = target.val();
                target.attr("data-val", newText);
            }
            if (newText) {
                $(".estimate-page").find(".custom-category-text").each(function () {
                    let ref = $(this);
                    let refChild = ref.children(".category-text");
                    if (oldValue == refChild.html()) {
                        refChild.html(newText);
                        ref.attr({ onclick: "ACC.estimate.customCategoryView(this, '" + newText + "')" })
                    }
                });
            }
        });
        ACC.estimate.customCategoryList(""); //creating category lists
        ACC.estimate.customCategoryMode("view"); //changing to view mode
    },
    editCustomerDetails: function () {
        $("#cname").val($(".company-name").text());
        $("#Caddress").val($("#Caddress").attr("data-default"));
        $("#apartment").val($("#apartment").attr("data-default"));
        $("#estimateCity").val($("#estimateCity").attr("data-default"));
        var arg = $("#estimateState").attr("data-default");
        $("#estimateState > option").each(function () {
            if ($(this).text() == arg) {
                $(this).parent('select').val($(this).val());
            }
        });
        $("#estimateZipcode").val($("#estimateZipcode").attr("data-default"));
        $("#estimatePhone").val($(".company-phone").text());
        $("#estimateEmail").val($(".company-email").text());
        ACC.estimate.popupEffect("show","fade",".estimate-popup");
    },
    updateCustomerDetails: function () {
        $(".company-name").text($("#cname").val());
        $(".company-address").eq(0).html($("#Caddress").val() + ", " + $("#apartment").val());
        $(".company-address").eq(1).html($("#estimateCity").val() + ", " + $("#estimateState").find(":selected").text() + "&nbsp;" + $("#estimateZipcode").val());
        $("#Caddress").attr("data-default", $("#Caddress").val());
        $("#apartment").attr("data-default", $("#apartment").val());
        $("#estimateCity").attr("data-default", $("#estimateCity").val());
        $("#estimateState").attr("data-default", $("#estimateState").find(":selected").text());
        $("#estimateZipcode").attr("data-default", $("#estimateZipcode").val());
        $(".company-phone").text($("#estimatePhone").val());
        $(".company-email").text($("#estimateEmail").val());
        ACC.estimate.popupEffect("hide","fold",".estimate-popup");
    },
    bindEstimateGenerationOptions: function (ref) { // change text on click on estimate generation options
    	var value = $(ref).val();
        $(".estimate-generate-option-txt").addClass("hidden");
        $("#" + value + "_txt").removeClass("hidden");
        $('[onclick^="ACC.estimate.estimatePDF"]').attr("onclick", "ACC.estimate.estimatePDF('" + value + "')");
    },
    popupEffect: function (mode, type, ref) {
		let target = $(ref);
		if(mode == "show"){
			if($('.showing-popup').length){
				$('.showing-popup').removeClass("showing-popup").effect('fold', 500);
			}
			target.addClass("showing-popup").show(type, 500);
		}
		else if(target.hasClass("showing-popup")){
        	target.removeClass("showing-popup").effect(type, 500);
		}
    },
    uploadLogo: function () { //uploading contractor logo
		let target = $(".logo-popup");
		let imgFileData = new FormData();
		let imgFile = $("#uploadedImage")[0].files[0];
		imgFileData.append('file', imgFile);
		target.find(".upload-logo-btn").removeClass("upload-logo-error");
		target.find(".estimate-size-error").addClass("hidden");
		target.find(".estimate-type-error").addClass("hidden");
		if(imgFile.type != 'image/png' && imgFile.type != 'image/jpg' && imgFile.type != 'image/jpeg' && imgFile.type != 'image/gif'){
			target.find(".estimate-type-error").removeClass("hidden");
			target.find(".upload-logo-btn").addClass("upload-logo-error");
			target.find("#uploadedImage").prop("value", "");
			return;
		}
		var reader = new FileReader();
		reader.readAsDataURL(imgFile);
        reader.onload = function (e) {
			var image = new Image(); //Set the Base64 string return from FileReader as source.
            image.src = e.target.result; //Validate the File Height and Width.
            image.onload = function () {
                if (this.width > 1200 || this.height > 400) {
                    target.find(".estimate-size-error").removeClass("hidden");
					target.find(".upload-logo-btn").addClass("upload-logo-error");
					target.find("#uploadedImage").prop("value", "");
                    return false;
                }
                else{
           			$(".contractor-logo").eq(1).attr({src: image.src ,"data-type": imgFile.type});
					ACC.estimate.logoPopupMode('upload');
				}
            };
        }
    },
    saveLogo: function (type) { //saving/deleting contractor logo
		let hideClasses = ".delete-logo-btn, .contractor-logo-btn";
    	let showClasses = ".upload-logo-btn";
		if(type == "delete"){
	    	let imageName = $(".contractor-logo").eq(1).attr("src");
	    	imageName = (imageName && imageName != "")? imageName.slice(imageName.lastIndexOf("/")+1) : "";
			$.ajax({
				url: ACC.config.encodedContextPath + '/savedList/deleteImage',
				type: 'post',
				success: function(response){
					if(response){
						$(".contractor-logo").attr('src', "");
						$(hideClasses).hide();
						$(showClasses).show();
					}
				},
				error: function(response){
					if(response){
						$(".contractor-logo").attr('src', "");
						$(hideClasses).hide();
						$(showClasses).show();
					}
				}
			});
            ACC.estimate.popupEffect("hide","drop",".logo-popup");
		}
    },
    logoPopupMode: function (hide) {
        let target = $(".logo-popup");
        let show = (hide == 'upload')? 'delete' : 'upload';
        let type = (hide == 'upload')? 'logo' : '';
        target.find("." + hide + "-logo-btn, .estimate-logo-" + show).hide();
        target.find("." + show + "-logo-btn, .estimate-logo-" + hide).show();
        if(type != "logo"){
        	target.find("#uploadedImage").prop("value", "");
		}
    },
    editLogo: function () { //edit the LOGO
        ACC.estimate.logoPopupMode('upload');
        ACC.estimate.popupEffect("show","slide",".logo-popup");
    },
    calculateImageWidthHeight: function(src, maxWidth=60, maxHeight=20) {
    	return new Promise((resolve, reject) => {
    	
    		function loadImageAndCal(src) {
	    		var image = new Image();
	    		
	    		image.onload = function () {
	    			var imageRation = ACC.estimate.calculateAspectRatioFit(this.width, this.height, maxWidth, maxHeight);
	    			
	    			resolve({
	    				width: imageRation.width, 
	    				height: imageRation.height
	    			});
	    		};
	    		image.src = src;
	    	} 
	    	
	    	if(src.startsWith("data:image/")){
	    		loadImageAndCal(src)
	    	} else {
	    		fetch(src)
	    		  .then(response => response.blob())
	    		  .then(blob => {
	    			  var reader = new FileReader();
	    			  reader.onload = function (e) {
	    				  loadImageAndCal(e.target.result);
	    			  }
	    			  reader.readAsDataURL(blob);
				  })
	    		
	    	}
    	})
    },
    calculateAspectRatioFit: function(srcWidth, srcHeight, maxWidth, maxHeight) {
        var ratio = Math.min(maxWidth / srcWidth, maxHeight / srcHeight);

        return { width: srcWidth*ratio, height: srcHeight*ratio };
    }
};