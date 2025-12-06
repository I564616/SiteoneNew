ACC.myquotes = {
    _autoload: [
        "myquotesPage"
    ],
    qNumber: [],
    qJobName: [],
    qSubmittedDate: [],
    qExpirationDate: [],
    qStatus: [],
    qAccount: [],
    qUnitId: [],
    qUnitName: [],
    qTotalPrice: [],
    qAccountQuotes: [],
    defaultPage: 25,
    pageShowing: 0,
    resultFound: 0,
    totalPage: 0,
    filterView: false,
    unitID: '',
    unitName: '',
    myquotesPage: function () {
        if ($(".page-my-quotes").length) {
            let unitID = $("#unitUid");
            let unitName = $(".shipto-header");
            ACC.myquotes.unitID = unitID.length && unitID.val() != '' ? $.trim(unitID.val()).split("_")[0] : 9999999999;
            ACC.myquotes.unitName = unitName.length && unitName.attr("title") != '' ? $.trim(unitName.attr("title")) : '';
            $('.quotes-show').prepend('<div class="quoteShowLabel hidden" ><label class="f-s-18" for="shipto_account_0">' + ACC.myquotes.unitName + '</label><span class="radio-button-circle" ><input type="radio" id="shipto_account_0" class="quote-account-radio" name="quoteShipToAccount" value="' + ACC.myquotes.unitID + '" data-accname="' + ACC.myquotes.unitName + '" checked="checked" /></span></div>');
            ACC.myquotes.qAccount.push(ACC.myquotes.unitID);
            ACC.myquotes.getShipToQuotes(true);
            $(".from-input input").on("focusout", function () {
                let target = $(this);
                if (target.val() == "") {
                    $(".from-input-label").fadeIn();
                }
                else {
                    $(".from-input-label").fadeOut();
                }
            });
            for (let j = 0; j < ACC.myquotes.qStatus.length; j++) {
                $('[data-quotefilter]').eq(2).append('<option value="' + ACC.myquotes.qStatus[j] + '">' + ACC.myquotes.qStatus[j] + '</option>')
            }
            ACC.myquotes.quotesPageView('my account: quotes', 'my account', 'my account: quotes');
            $(".quotes-pill-toggle-input").on('keyup', function (e) {
                if (e.keyCode == 13) {
                    ACC.myquotes.quoteSearch('.quotes-pill-toggle-input','.quotes-pill-toggle','all');
                }
            });
        }
        if ($(".page-quote-detail").length) {
            if(!$(".quote-table-entry").length){
                $(".quote-stickey-header").remove();
            }
            let quoteExDateElem = $(".quote-expiration-date");
            let quoteExDate = quoteExDateElem.html().split('/');
            let quoteExDateVal = ACC.myquotes.isDateExpired(quoteExDate[2], quoteExDate[0], quoteExDate[1]);
            if (ACC.global.wWidth < 720) {
                for (let j = 0; j < 1; j++) {
                    let target1 = $(".quote-height-adjust").eq(j);
                    let target2 = $(".quote-height-adjust").eq(j + 1);
                    if (target1.height() > target2.height()) {
                        target2.height(target1.height());
                    }
                    else if (target2.height() > target1.height()) {
                        target1.height(target2.height());
                    }
                }
                $(".quote-table-entry").find('[data-opacity]').each(function () {
                    let ref = $(this);
                    if (ref.data("opacity") == 'no') {
                        ref.addClass("hidden");
                    }
                });
            }
            else {
                let maxHeightAdjust = 0;
                for (let j = 0; j < 6; j++) {
                    let target = $(".quote-height-adjust").eq(j);
                    maxHeightAdjust = (maxHeightAdjust < target.height()) ? target.height() : maxHeightAdjust;
                }
                $(".quote-height-adjust").height(maxHeightAdjust);
                $(".quote-table-entry").find('[data-opacity]').each(function () {
                    let ref = $(this);
                    if (ref.data("opacity") == 'no') {
                        ref.css("opacity", "0");
                    }
                });
            }
            if (quoteExDateVal) {
                quoteExDateElem.css("color", "#E40101");
                $(".quote-expiration-date-show").removeClass("hidden");
                $(".quote-expiration-date-hide").remove();
                $(".col-md-selecion").removeClass("col-md-11pe col-xs-11 pad-xs-lft-10 col-md-selecion");
            }
            ACC.myquotes.stickeyHeaderTop = $(".quote-stickey-header").length ? $(".quote-stickey-header").offset().top : 0;
            ACC.myquotes.detilsToatlShow();
            $(".quotedetails-page").css("opacity", 1);
            ACC.myquotes.quotesPageView('my account: quotes: quote detail: ' + $(".quote-details-data").data("quotenumber"), 'my account', 'my account: quotes: quote detail: ' + $(".quote-details-data").data("quotenumber"));
        }
        if ($(".page-my-quotes").length || $(".page-quote-detail").length) {
            $(window).on("scroll", function () {
                let scrollTop = $(this).scrollTop();
                if (scrollTop > 700) {
                    $(".btn-page-scroll").attr("style", "display: block !important;");
                }
                else {
                    $(".btn-page-scroll").attr("style", "display: none !important;");
                }
                if ($(".page-quote-detail").length && ACC.global.wWidth > 700) {
                    ACC.myquotes.stickeyHeader(this);
                }
                if (ACC.global.wWidth < 1200) { //xs and SM
                    ACC.myquotes.pillToggleSticky(scrollTop);
                }
            });
            $('[data-toggle="popover"]').popover({ placement: 'top', trigger: 'hover', container: 'body' });
        }
    },
    pillSelected: 'open',
    pillToggle: function (ref, className, type) {
        let target = $(ref);
        ACC.myquotes.quoteSearchMode = false;
        if (!target.hasClass("active")) {
            $(className).removeClass("active");
            target.addClass("active");
            $(".quotes-pill-toggle-all").addClass("disabled").prop("disabled", true);
            $(".quote-filter-popup-button").removeClass("disabled active hidden").prop("disabled", false);
            $(".quotes-pill-toggle-input").val(""); //no search mode
            $(".from-input-label").show();
            ACC.myquotes.pillSelected = type;
            ACC.myquotes.showAjaxPage = 1;
            $(".quote-selected-account").each(function(){
                if($(this).prop("checked")){
                    $(this).prop("checked", false);
                }
            });
            $(".quote-selected-account-count").html($(".quote-selected-account-count").data("stext"));
            ACC.myquotes.getShipToQuotes(false, true);
        }
    },
    quoteSearchMode: false,
    quoteSearch: function (ref, className, type) {
        let target = $(ref);
        if (target.val() && target.val() != "") {
            if(!ACC.myquotes.quoteSearchMode){
                ACC.myquotes.quoteSearchMode = true;
                $(className).removeClass("active");
                target.removeClass("border-red");
                $(".quotes-pill-toggle-all").removeClass("disabled").prop("disabled", false).addClass("active");
                $(".quote-filter-popup-button").addClass("hidden disabled").prop("disabled", true);
                ACC.myquotes.pillSelected = type;
                ACC.myquotes.showAjaxPage = 1;
                ACC.myquotes.getShipToQuotes(false, true);
            }
            else{
                ACC.myquotes.showAjaxPage = 1;
                ACC.myquotes.updateQuoteFilter(true);
            }
        }
        else {
            target.addClass("border-red");
        }
    },
    getShipToAccounts: function () {
        $.ajax({
            url: ACC.config.encodedContextPath + "/my-account/shiptoQuote",
            type: "get",
            success: function (a) {
                let qAccountData = a.itemDetails; //a.accounts;
                let quotesFound = 0;
                if (qAccountData && qAccountData.length) {
                    for (let j = 1; j <= qAccountData.length; j++) {
                        let customerNumber = qAccountData[j - 1].customerNumber ? $.trim(qAccountData[j - 1].customerNumber) : '';
                        let customerName = qAccountData[j - 1].customerName ? $.trim(qAccountData[j - 1].customerName) : '';
                        if (qAccountData[j - 1].quoteCount != 0 && $.inArray(customerNumber, ACC.myquotes.qAccount) == -1) {
                            ACC.myquotes.qAccount.push(customerNumber);
                            $('.quotes-show').append('<div class="quoteShowLabel"><label class="f-s-18" for="shipto_account_' + j + '">' + customerName + ' #' + customerNumber + ' (' + qAccountData[j - 1].quoteCount + ') </label><span class="radio-button-circle"><input type="radio" id="shipto_account_' + j + '" class="quote-account-radio" data-accname="' + customerName + '" name="quoteShipToAccount" value="' + customerNumber + '" /></span></div>');
                            quotesFound++;
                        }
                    }
                    if (quotesFound) {
                        $(".quotes-shipto-btn").removeClass("hidden");
                    }
                    else {
                        $(".quotes-shipto-btn").addClass("hidden");
                    }
                }
                else {
                    //ACC.myquotes.quotePopupError();
                }
            },
            error: function () {
                ACC.myquotes.quotePopupError();
            }
        });
    },
    getShipToQuotes: function (onload, pageClick) {
        if (!pageClick) {
            ACC.myquotes.showAjaxPage = 1;
            $('.quotes-pill-toggle').removeClass("active");
            $('.quotes-pill-toggle-open').addClass("active");
            ACC.myquotes.pillSelected = 'Open';
        }
        loading.start();
        ACC.myquotes.quotesAjaxValue = (ACC.myquotes.showAjaxPage - 1) * ACC.myquotes.perPageAjaxQuotes;
        let target = $("input[name=quoteShipToAccount]:checked");
        let accountNumber = target.val();
        $(".quote-table").remove();
        target.data("fatch", false); //always restting as we need fresh data
        var shipToBtnText = '<span class="text-green bold">Select Ship-To</span>';
        if (!target.data("fatch")) {
            if (!$("#shipto_account_0").prop('checked')) {
                if (ACC.global.wWidth > 1200) {
                    shipToBtnText = '<span class="text-green bold">Ship-To: </span><span class="text-dark-gray">' + accountNumber + ' ' + target.data('accname') + '</span>';
                }
                else {
                    $(".quotes-shipto-info").html('<span class="bold">Ship-To: </span><span>' + accountNumber + ' ' + target.data('accname') + '</span><hr>');
                }
            }
            else {
                $(".quotes-shipto-info").html("");
            }
            $('.quotes-shipto-btn').html(shipToBtnText);
            $.ajax({
                url: ACC.config.encodedContextPath + "/my-account/shipping-Quotes",
                type: "get",
                data: { customerNumber: accountNumber, skipCount: ACC.myquotes.quotesAjaxValue, toggle: ACC.myquotes.pillSelected },
                success: function (qAccountData) {
                    ACC.myquotes.createQuotesLineItems(qAccountData);
                },
                error: function () {
                    ACC.myquotes.updateQuoteFilter(true);
                }
            });
        }
        else {
            ACC.myquotes.updateQuoteFilter(true);
        }
    },
    createQuotesLineItems: function (qAccountData) {
        ACC.myquotes.totalAjaxQuotes = 0;
        //let qAccountData = ACC.myquotes.pillSelected == 'open' || ACC.myquotes.pillSelected == 'Open' ? rawData.open : ACC.myquotes.pillSelected == 'full' ? rawData.full : ACC.myquotes.pillSelected == 'all' ? rawData.all : rawData.expired;
        let qAccountDataHTML = "";
        if (qAccountData) {
            ACC.myquotes.qUnitId = [];
            ACC.myquotes.qUnitName = [];
            ACC.myquotes.qAccountQuotes = [];
            for (let j = 0; j < qAccountData.length; j++) {
                if (j < qAccountData.length - 1) {
                    let quoteId = qAccountData[j].quoteId;
                    let quoteNum = qAccountData[j].quoteNumber;
                    let quoteJob = qAccountData[j].jobName;
                    let quoteAccount = qAccountData[j].accountName ? $.trim(qAccountData[j].accountName) : '';
                    let quoteSubDate = qAccountData[j].lastModfDate;
                    let quoteExpDate = qAccountData[j].expirationDate;
                    let quotePrice = qAccountData[j].totalPrice.value;
                    let quoteNote = qAccountData[j].notes ? 1 : 0;
                    let quoteNoteText = qAccountData[j].notes;
                    let quoteApprove = qAccountData[j].isQuoteApproved ? 1 : 0;
                    let isFullQuoteApprove = qAccountData[j].isFullQuoteApproved ? 1 : 0;
                    let customerNumber = qAccountData[j].accountNumber ? $.trim(qAccountData[j].accountNumber) : '';
                    let viewLink = "this,'" + quoteId + "','" + quoteNum + "','" + quotePrice + "'";
                    let adobeLink = "'','View Quote','my account: quotes'";
                    qAccountDataHTML += '<div class="row margin0 p-l-15 p-r-15 p-y-20 no-padding-xs flex-center justify-center flex-wrap-xs add-border-radius text-default f-s-15 font-14-xs quote-table bg-white" data-quote="' + quoteNum + '" data-account="' + customerNumber + '" data-totalp="' + quotePrice + '" style="display: none;"><div class="col-md-3 col-md-11pe col-xs-12 b-b-grey-xs"><div class="row"><div class="col-md-12 col-xs-6 p-15-xs p-b-0-xs"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Quote Number</p><button class="btn btn-link p-l-0 p-t-0-xs transition-3s bold-text text-green quoteNum" onclick="ACC.myquotes.detilsView(' + viewLink + ');ACC.adobelinktracking.quotesView(' + adobeLink + ');">' + quoteNum + '</button></div><div class="col-xs-6 p-15-xs p-b-0-xs b-l-grey-xs hidden-md hidden-lg"><p class="margin0 pad-xs-lft-15 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Approved</p><button class="btn btn-link transition-3s no-margin-xs p-t-5-xs p-b-0-xs" data-toggle="popover" data-content="' + (isFullQuoteApprove ? 'All Items Approved' : 'Select Items Approved') + '" style="opacity:' + quoteApprove + '"><svg xmlns="http://www.w3.org/2000/svg" width="18" height="19" viewBox="0 0 18 18" fill="none" class=""><circle cx="9" cy="9" r="9" fill="#78A22F"></circle><path d="M7.39097 12.7555L4.60526 9.90546C4.43383 9.73403 4.43383 9.45545 4.60526 9.28403L5.21597 8.6626C5.37668 8.49117 5.64454 8.49117 5.81597 8.6626L7.69097 10.5805L11.7088 6.47688C11.8695 6.30546 12.1374 6.30546 12.3088 6.47688L12.9195 7.09831C13.091 7.26974 13.091 7.54831 12.9195 7.71974L7.99097 12.7555C7.83026 12.9269 7.5624 12.9269 7.39097 12.7555Z" fill="white"></path></svg></button></div></div></div><div class="col-md-2 col-xs-12 p-15-xs text-capitalize b-b-grey-xs"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Job Name</p><span class="no-margin-xs quoteJob">' + quoteJob + '</span></div><div class="col-md-2 col-xs-12 p-15-xs b-b-grey-xs"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Account Name</p><span class="no-margin-xs quoteAccount">' + quoteAccount + '</span></div><div class="col-md-3 col-md-13pe col-xs-12 b-b-grey-xs"><div class="row"><div class="col-md-12 col-xs-6 p-15-xs"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Last Modified</p><span class="no-margin-xs quote-submitted-date">' + quoteSubDate + '</span></div><div class="col-xs-6 p-15-xs b-l-grey-xs hidden-md hidden-lg"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Expiration Date</p><p class="no-margin-xs quote-expiration-date">' + quoteExpDate + '</p></div></div></div><div class="col-md-3 col-md-13pe hidden-xs hidden-sm quote-expiration-date">' + quoteExpDate + '</div><div class="col-md-1 col-md-10pe col-xs-12 padding0 pad-xs-lft-15 pad-xs-rgt-15"><div class="row"><div class="col-md-12 col-xs-6 p-15-xs"><p class="m-b-0 bold text-gray text-uppercase f-s-10 hidden-md hidden-lg">Total</p><span class="no-margin-xs f-w-b-xs quotePrice">$' + ACC.savedlist.formatNumberComma(quotePrice) + '</span></div><div class="col-xs-6 p-15-xs hidden-md hidden-lg"><button class="btn btn-primary btn-block no-margin-xs transition-3s" onclick="ACC.myquotes.detilsView(' + viewLink + ');ACC.adobelinktracking.quotesView(' + adobeLink + ');">View</button></div></div></div><div class="col-md-1 col-md-10pe text-center hidden-xs hidden-sm"><button class="btn btn-link transition-3s approve-btn ' + (quoteApprove ? '' : 'hidden') + '" data-toggle="popover" data-content="' + (isFullQuoteApprove ? 'All Items Approved' : 'Select Items Approved') + '" style="opacity:' + quoteApprove + '"><svg xmlns="http://www.w3.org/2000/svg" width="18" height="19" viewBox="0 0 18 18" fill="none" class=""><circle cx="9" cy="9" r="9" fill="#78A22F"></circle><path d="M7.39097 12.7555L4.60526 9.90546C4.43383 9.73403 4.43383 9.45545 4.60526 9.28403L5.21597 8.6626C5.37668 8.49117 5.64454 8.49117 5.81597 8.6626L7.69097 10.5805L11.7088 6.47688C11.8695 6.30546 12.1374 6.30546 12.3088 6.47688L12.9195 7.09831C13.091 7.26974 13.091 7.54831 12.9195 7.71974L7.99097 12.7555C7.83026 12.9269 7.5624 12.9269 7.39097 12.7555Z" fill="white"></path></svg></button></div><div class="col-md-1 col-md-9pe p-l-0 hidden-xs hidden-sm"><button class="btn btn-primary btn-block transition-3s" onclick="ACC.myquotes.detilsView(' + viewLink + ');ACC.adobelinktracking.quotesView(' + adobeLink + ');">View</button></div></div>';
                    let unitIndex = $.inArray(customerNumber, ACC.myquotes.qUnitId);
                    if (customerNumber != "" && unitIndex == -1) {
                        ACC.myquotes.qUnitId.push(customerNumber);
                        ACC.myquotes.qUnitName.push(quoteAccount);
                        ACC.myquotes.qAccountQuotes.push(1);
                    }
                    else if (customerNumber != "") {
                        ACC.myquotes.qAccountQuotes[unitIndex] = ACC.myquotes.qAccountQuotes[unitIndex] + 1;
                    }
                    ACC.myquotes.totalAjaxQuotes++;
                }
                else {
                    ACC.myquotes.perPageAjaxQuotes = qAccountData[j].pageSize;
                }
            }
            $(qAccountDataHTML).insertBefore('.quote-no-filter-results');
            if (onload) {
                $("input[name=quoteShipToAccount]").eq(0).prop("checked", true);
            }
            ACC.myquotes.quotesFilterAccount(); //creating quotes account filter
            ACC.myquotes.arangeQuoteResults();
            $('[data-toggle="popover"]').popover({ placement: 'top', trigger: 'hover', container: 'body' });
            ACC.myquotes.updateQuoteFilter(true);
        }
        else {
            ACC.myquotes.quotePopupError();
        }
    },
    quotesFilterAccount: function () {
        let refHTML = "";
        let foundFilter = 0;
        let target = $(".quote-filter-popup");
        let textmaxNum = Math.round(((ACC.global.wWidth > 1024 ? target.width() : ACC.global.wWidth) - 80) / 7.2);
        for (let i = 0; i < ACC.myquotes.qUnitId.length; i++) {
            let hiddenClass = " hidden";
            if (ACC.myquotes.qUnitId[i].indexOf("-") != -1) {
                hiddenClass = "";
                foundFilter++;
            }
            let accText = ACC.myquotes.qUnitName[i].length > textmaxNum ? ACC.myquotes.qUnitName[i].slice(0, textmaxNum - 2) + "..." : ACC.myquotes.qUnitName[i];
            refHTML += '<label class="flex-center font-size-14 p-y-5 p-l-20 p-r-30 l-h-18 pointer text-align-left text-dark-gray transition-3s list-group-item' + hiddenClass + '" for="selected-account-' + i + '"><span class="colored-primary hidden-print p-r-5"><input onchange="ACC.myquotes.quotesFilterAccountClick()" aria-label="Checkbox" id="selected-account-' + i + '" class="text-align-center list-group-item-check quote-selected-account" type="checkbox" value="' + ACC.myquotes.qUnitName[i] + '" data-role="' + i + '" data-account="' + ACC.myquotes.qUnitName[i] + '" data-unit="' + ACC.myquotes.qUnitId[i] + '"></span>' + accText + '<span class="text-gray list-group-item-badge" data-rdefault="' + i + '" data-rcount="' + ACC.myquotes.qAccountQuotes[i] + '">' + ACC.myquotes.qAccountQuotes[i] + '</span></label>';
        }
        if (ACC.global.wWidth <= 1024) {
            let headerHeight = $(".global-header-sticky").height();
            $(".quote-filter-popup-box").css({ top: headerHeight, height: ACC.global.wHeight - headerHeight });
            $(".quote-filter-popup-accounts").height(ACC.global.wHeight - headerHeight - 160);
        }
        $(".quote-filter-popup-accounts").html(refHTML);
        if (foundFilter) {
            target.css({ opacity: 1 });
        }
        else {
            target.css({ opacity: 0 });
        }
    },
    quotesFilterAccountClick: function () {
        let checkNumber = 0;
        $(".quote-selected-account").each(function () {
            if ($(this).prop("checked")) {
                checkNumber++;
            }
        });
        if (checkNumber) {
            $(".quote-selected-account-count").html(checkNumber);
        }
        else {
            $(".quote-selected-account-count").html($(".quote-selected-account-count").data("stext"));
        }
    },
    toggleOffElems: function (e, eElem, className, offTarget) {
        let target = $(eElem);
        let ref = (e) ? $(e) : target;
        if (target.is(":visible")) {
            $(eElem + '-overlay').hide();
            target.hide();
            $(eElem + '-button').removeClass(className);;
            ref.removeClass(className);
        }
        else {
            if (offTarget) {
                $(offTarget + '.active').removeClass("active");
            }
            $(eElem + '-overlay').show();
            target.show();
            ref.addClass(className);
        }
    },
    arangeQuoteResults: function () {
        ACC.myquotes.qNumber = [];
        ACC.myquotes.qJobName = [];
        ACC.myquotes.qSubmittedDate = [];
        ACC.myquotes.qExpirationDate = [];
        ACC.myquotes.qStatus = [];
        ACC.myquotes.qTotalPrice = [];
        $(".quote-table").each(function (e) {
            let target = $(this);
            let quoteNum = target.find(".quoteNum");
            let quoteJob = target.find(".quoteJob");
            let quoteStatus = target.find(".quoteStatus");
            let totalPrice = Number(target.attr("data-totalP"));
            let num = $.trim(quoteNum.text());
            let numOld = num;
            let job = $.trim(quoteJob.text()) + '--' + num;
            let status = $.trim(quoteStatus.text());
            let quoteDateElem = target.find(".quote-submitted-date");
            let quoteDate = quoteDateElem.html().split('/');
            let quoteDateVal = ACC.myquotes.isDateExpired(quoteDate[2], quoteDate[0], quoteDate[1], true);
            let sDate = quoteDateVal[1] + '--' + num;
            let quoteExDateElem = target.find(".quote-expiration-date");
            let quoteExDate = quoteExDateElem.eq(0).html().split('/');
            let quoteExDateVal = ACC.myquotes.isDateExpired(quoteExDate[2], quoteExDate[0], quoteExDate[1], true);
            let eDate = quoteExDateVal[1] + '--' + num;
            let dDate = quoteDateVal[2];
            num += '--' + totalPrice;
            totalPrice += numOld * .000000001;
            target.attr({ "data-quote": num, "data-job": job, "data-date": sDate, "data-edate": eDate, "data-day": dDate, "data-status": status, "data-price": totalPrice });
            ACC.myquotes.qNumber.push(num);
            ACC.myquotes.qJobName.push(job);
            ACC.myquotes.qSubmittedDate.push(sDate);
            ACC.myquotes.qExpirationDate.push(eDate);
            ACC.myquotes.qTotalPrice.push(totalPrice);
            if (quoteExDateVal[0]) {
                quoteExDateElem.html('<button onclick="ACC.myquotes.updateExpiredQuote(this,'+ numOld +',\''+ target.find(".quotePrice").text() +'\')" data-toggle="popover" data-content="Request an updated quote." class="border-none p-y-5 border-radius-3-imp quote-update-expired-btn"><span class="f-s-15 f-w-700" style="color: #E40101;">'+ quoteExDate.join('/') +'</span></button>');
            }
        });
        $(".quote-update-expired-btn").popover({ placement: 'top', trigger: 'hover', container: 'body' });
        ACC.myquotes.qNumber.sort();
        ACC.myquotes.qJobName.sort();
        ACC.myquotes.qSubmittedDate.sort().reverse();
        ACC.myquotes.qExpirationDate.sort().reverse();
        ACC.myquotes.qTotalPrice.sort(function (a, b) { return b - a });
    },
    generateRandomNum: function (min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    },
    generateRandomStatus: function (text) {
        return text[ACC.myquotes.generateRandomNum(1, text.length) - 1];
    },
    isDateExpired: function (year, month, date, time) {
        let today = new Date();
        var date = new Date(year, (month - 1), date, 23, 59, 0, 0);
        let isDateExpired = (today > date) ? true : false;
        let days = Math.floor((today.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
        return (time) ? [isDateExpired, date.getTime(), days] : isDateExpired;
    },
    updateExpiredQuote: function (event, quote, quotePrice) {
        let ref = $(event);
        //console.log(ref, quote, quotePrice);
        quote = !quote ? $(".quote-number").text() : quote;
        quotePrice = !quotePrice ? $(".quotePrice").text() : quotePrice;
        ACC.colorbox.open("", {
            html: $(".quote-update-expired").html(),
            width: ACC.global.wWidth < 1024 ? 412 : 725,
            className: "quotes-popup-expired text-center",
            onComplete: function () {
                $(".quote-update-expired-number").html(quote);
                $("#cboxLoadedContent").append('<span class="hidden quotePrice">'+ quotePrice +'</span>');
                //console.log('<span class="quotePrice">'+ quotePrice +'</span>');
            }
        });
    },
    sendExpiredQuote: function () {
        let quoteNumber = $(".quotes-popup-expired .quote-update-expired-number").text();
        let customerNumber = $("input[name=quoteShipToAccount]:checked").val();
        let notes = $(".quotes-popup-expired .quote-update-expired-input").val();
        let quoteTotal = $(".quotes-popup-expired .quotePrice").text();
        $('.quotes-popup-expired [onclick="ACC.myquotes.sendExpiredQuote()"]').removeAttr("onclick");
        //console.log(quoteNumber, customerNumber, notes, quoteTotal);
        if(quoteNumber && quoteNumber != ""){
            customerNumber = customerNumber && customerNumber != "" ? customerNumber : "";
            notes = notes && notes != "" ? notes : "";
            quoteTotal = quoteTotal && quoteTotal != "" ? quoteTotal : "$0.00";
            $.ajax({
                url: ACC.config.encodedContextPath + "/my-account/updateExpiredQuote",
                type: "post",
                data: { quoteNumber: quoteNumber, customerNumber: customerNumber, notes: notes, quoteTotal: quoteTotal},
                success: function (resp) {
                    if(resp == 'Success' || resp == 'success'){
                        ACC.colorbox.close();
                    }
                    else{
                        ACC.myquotes.quotePopupError();
                    }
                },
                error: function () {
                    ACC.myquotes.quotePopupError();
                }
            });
        }
        else{
            ACC.myquotes.quotePopupError();
        }
    },
    filterPopup: function (mode, pos, ref, bodyScroll, showFor) { // filter popup show/hide
        let target = $(ref);
        pos = pos.toString().indexOf("-") != -1 ? pos : -pos;
        let popupRight = ($('.page-detailsSavedListPage').length || $('.page-invoicelistingpage').length) ? 15 : 0;
        if (mode == "show") {
            if ($('.showing-popup').length) {
                $('.showing-popup').removeClass("showing-popup").animate({ right: pos }, 333);
            }
            else if (bodyScroll) {
                $('body, html').css({ overflow: "hidden" });
            }
            $('.quote-popup-section').addClass("hidden");
            $('.' + showFor).removeClass("hidden");
            target.addClass("showing-popup").animate({ right: popupRight }, 333);
        }
        else if (target.hasClass("showing-popup")) {
            target.removeClass("showing-popup").animate({ right: pos }, 333);
            if (bodyScroll) {
                $('body, html').css({ overflow: "hidden" });
            }
        }
    },
    updateQuoteFilter: function (pageload, sort, all) { //checking for filter updates
        if (pageload == undefined) {
            ACC.myquotes.filterView = true;
            $(".reverse-sorting").removeClass("reverse-sorting");
        }
        let newFilters = {
            search: "",
            time: "",
            status: "",
            account: [],
            sort: "",
            view: ""
        };
        let filterChanged = false;
        $(".quote-selected-account").each(function (e) {
            let ref = $(this);
            if (ref.prop("checked")) {
                newFilters.account.push(ref.data("unit"));
            }
        });
        $("[data-quotefilter]").each(function (e) {
            let target = $(this);
            let defaultVal = target.data('quotefilter');
            let targetInput = target.is('input');
            let currentVal = (targetInput) ? $.trim(target.val().toLowerCase()) : target.find(":selected").val();
            if (currentVal != "") {
                filterChanged = true;
                loading.start();
                if (e == 0) {
                    newFilters.time = (!ACC.myquotes.filterView && all) ? all : currentVal;
                }
                else if (e == 1) {
                    newFilters.status = currentVal.toLowerCase();
                }
                else if (e == 2) {
                    //newFilters.account = '';
                }
                else if (e == 3) {
                    newFilters.sort = (sort) ? sort : currentVal.toLowerCase();
                }
                else if (e == 5) {
                    newFilters.search = currentVal.toLowerCase();
                }
                else {
                    currentVal = ACC.myquotes.perPageAjaxQuotes; //depends on the AJAX response
                    newFilters.view = currentVal;
                    ACC.myquotes.defaultPage = currentVal
                }
                target.data("quotefilter", currentVal);
            }
        });
        if (filterChanged) { //filter updated
            ACC.myquotes.applyQuoteFilter(newFilters, pageload);
        }
        loading.stop();
    },
    quotesAjaxValue: 0, //AJAX input
    perPageAjaxQuotes: 25, //AJAX response
    totalAjaxQuotes: 125, //AJAX response
    showAjaxPage: 1, //default is 1 or pagination num
    totalAjaxPages: 1, //default is 1 or depends on AJAX response
    applyQuoteFilter: function (newFilters, pageload) { //resetting the page as per the new filter
        ACC.myquotes.resultFound = 0;
        ACC.myquotes.totalPage = 1;
        ACC.myquotes.pageShowing = 1;
        let pagination = $(".quote-pagination");
        let noFilter = $(".quote-no-filter-results");
        let sortParent = $("[data-quotesort='" + newFilters.sort + "']");
        let reverse = false;
        $(".quote-sort-button").children("svg").css("opacity", .4);
        sortParent.children("svg").css("opacity", 1).children("path").eq(1);
        if (pageload || !sortParent.hasClass("reverse-sorting")) {
            $(".quote-sort-button").removeClass("reverse-sorting");
            sortParent.addClass("reverse-sorting");
        }
        else {
            reverse = true;
            $(".quote-sort-button").removeClass("reverse-sorting");
        }
        $(".quote-table").each(function (e) {
            let target = $(this);
            let qNumber = target.data("quote").toLowerCase().split("--")[0];
            let qName = target.data("job").toLowerCase().split("--")[0];
            let qStatus = target.data("status").toLowerCase();
            let qAccount = target.data("account");
            let qDay = target.data("day");
            let filterMatched = 'yes';
            let qOrder = $.inArray(target.data(newFilters.sort), (newFilters.sort == "date") ? ACC.myquotes.qSubmittedDate : (newFilters.sort == "edate") ? ACC.myquotes.qExpirationDate : (newFilters.sort == "price") ? ACC.myquotes.qTotalPrice : (newFilters.sort == "quote") ? ACC.myquotes.qNumber : ACC.myquotes.qJobName) + 1;
            qOrder = (reverse) ? ACC.myquotes.qNumber.length - qOrder + 1 : qOrder;
            if (newFilters.search != "" && qNumber.toString().search(newFilters.search) == -1 && qName.search(newFilters.search) == -1) {
                filterMatched = 'search';
            }
            else if (newFilters.status != "" && newFilters.status != qStatus) {
                filterMatched = 'status';
            }
            else if (newFilters.account.length && $.inArray(qAccount, newFilters.account) == -1) {
                filterMatched = 'account';
            }
            else if (newFilters.time != "" && newFilters.time < qDay) {
                filterMatched = 'qDay';
            }
            if (filterMatched == "yes") {
                if (ACC.myquotes.resultFound != 0 && ACC.myquotes.resultFound % ACC.myquotes.defaultPage == 0) {
                    ACC.myquotes.totalPage++;
                }
                ACC.myquotes.resultFound++;
            }
            target.css({ order: qOrder, display: "none" }).attr({ "data-filter": filterMatched, "data-order": qOrder, "data-pagenum": 0 });
        });
        if (!ACC.myquotes.resultFound) { //no results found
            noFilter.removeClass("hidden");
            pagination.addClass("hidden");
        }
        else { //result can be shown in page
            noFilter.addClass("hidden");
            pagination.addClass("hidden");
            ACC.myquotes.addingPageNum();
            ACC.myquotes.totalAjaxPages = Math.ceil(ACC.myquotes.resultFound / ACC.myquotes.perPageAjaxQuotes);
            if (ACC.myquotes.totalAjaxPages > 1) {
                $(".quote-page-num").remove();
                ACC.myquotes.createAjaxPageNumber(pagination, pageload);
            }
            else {
                $("[data-pagenum='1']").css("display", "flex");
            }
        }
        $(".quote-results-num").html(ACC.myquotes.resultFound); //updating result number
        $(".quote-page").css("opacity", 1);
        $(".quote-filter-popup-box").hide();
    },
    createAjaxPageNumber: function (pagination, pageload) {
        for (let index = 1; index <= ACC.myquotes.totalAjaxPages; index++) {
            $('<li class="quote-page-num"><button onclick="ACC.myquotes.showingPage(this, ' + index + ')">' + index + '</button></li>').insertBefore($(".quote-pagination-next"));
        }
        ACC.myquotes.showingPage(false, 1, pageload);
        // ACC.myquotes.showingAjaxPage(false, 1);  if the data is from the ajax
        pagination.removeClass("hidden");
    },
    showingAjaxPage: function (e, num) {
        let targetParent = $(e).parent("li");
        if (!e) {
            $(".quote-pagination-prev, .quote-pagination-next").removeClass("disabled");
            if (ACC.myquotes.showAjaxPage == 1) { //1st page
                $(".quote-pagination-prev").addClass("disabled");
            }
            else if (ACC.myquotes.showAjaxPage == ACC.myquotes.totalAjaxPages) { //last page
                $(".quote-pagination-next").addClass("disabled");
            }
            $(".quote-page-num").eq(ACC.myquotes.showAjaxPage - 1).addClass("active");
        }
        else if (e && !targetParent.hasClass("disabled") && !targetParent.hasClass("active")) {
            if (num == "prev") {
                ACC.myquotes.showAjaxPage--;
            }
            else if (num == "next") {
                ACC.myquotes.showAjaxPage++;
            }
            else {
                ACC.myquotes.showAjaxPage = num;
            }
            ACC.myquotes.pageScrollTop(".breadcrumb-section");
            ACC.myquotes.getShipToQuotes(false, true);
        }
    },
    addingPageNum: function () {
        let filterMatched = 0;
        for (let i = 1; i <= ACC.myquotes.qNumber.length; i++) {
            let target = $("[data-order='" + i + "']");
            if (target.attr("data-filter") == "yes") {
                filterMatched++;
                target.attr("data-pagenum", Math.ceil(filterMatched / ACC.myquotes.defaultPage))
            }
        }
    },
    showingPage: function (e, num, pageload) {
        let targetParent = $(e).parent("li");
        if (!e || (!targetParent.hasClass("disabled") && !targetParent.hasClass("active"))) {
            loading.start();
            if (num == "prev") {
                ACC.myquotes.pageShowing--;
            }
            else if (num == "next") {
                ACC.myquotes.pageShowing++;
            }
            else {
                ACC.myquotes.pageShowing = num;
            }
            $("[data-order]").css("display", "none");
            $("[data-pagenum='" + ACC.myquotes.pageShowing + "']").css("display", "flex");
            $(".quote-page-num").removeClass("active");
            $(".quote-page-num").eq(ACC.myquotes.pageShowing - 1).addClass("active");
            if (ACC.myquotes.totalPage == 1) {
                $(".quote-pagination-prev, .quote-pagination-next").addClass("disabled");
            }
            else if (ACC.myquotes.pageShowing == 1) { //1st page
                $(".quote-pagination-prev").addClass("disabled");
                $(".quote-pagination-next").removeClass("disabled");
            }
            else if (ACC.myquotes.pageShowing == ACC.myquotes.totalPage) { //last page
                $(".quote-pagination-prev").removeClass("disabled");
                $(".quote-pagination-next").addClass("disabled");
            }
            else { //any other page
                $(".quote-pagination-prev, .quote-pagination-next").removeClass("disabled");
            }
            if (!pageload) {
                ACC.myquotes.pageScrollTop(".breadcrumb-section");
                $(".quote-filter-popup-box").hide().removeClass("active");
            }
            loading.stop();
        }
    },
    resetQuoteFilter: function (reset) {
        $("[data-quotefilter]").each(function (e) {
            let target = $(this);
            if (target.is('input')) {
                target.val("");
            }
            else {
                target.prop("selectedIndex", 0);
            }
        });
        $(".quote-selected-account").each(function () {
            if ($(this).prop("checked")) {
                $(this).prop("checked", false);
            }
        });
        if (reset == 'shipto') {
            $("#shipto_account_0").prop('checked', true);
        }
        $(".from-input-label").fadeIn();
        ACC.myquotes.showAjaxPage = 1;
        ACC.myquotes.pillSelected = 'open';
        $('.quotes-pill-toggle').removeClass("active");
        $('.quotes-pill-toggle-open').addClass("active");
        ACC.myquotes.filterView = true;
        ACC.myquotes.getShipToQuotes(false);
        ACC.myquotes.quotesFilterAccountClick();
    },
    pageScrollTop: function (top, left) {
        $("html, body").animate({ scrollLeft: (left ? $(left).offset().left : 0), scrollTop: (top ? $(top).offset().top : 0) });
    },
    detilsView: function (ref, quoteId, qNumber) {
        let qTotal = $(ref).parents(".quote-table").find(".quotePrice").html();
        localStorage.setItem("qTotal" + qNumber, qTotal);
        window.location.href = ACC.config.encodedContextPath + '/my-account/my-quotes/' + qNumber;
    },
    formatter: new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        // These options are needed to round to whole numbers if that's what you want.
        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
        maximumFractionDigits: 2 // (causes 2500.99 to be printed as $2,501)
    }),
    detilsToatlShow: function () {
        let qTarget = $('.quote-table-entry');
        var extPrice = 0;
        if ($(".quote-table-entry").length) {
            for (let i = 0; i < qTarget.length; i++) {
                let target = qTarget.eq(i);
                let targetSt = target.data("st");
                let targetComment = target.data("comment");
                if(!targetSt && !targetComment){
                    extPrice = extPrice + Number(target.data("extprice"));
                }
                let unitPrice = Number(target.data("unitprice"));
                $(".quote-table-entry .quote-table .quote-unit-price").eq(i).html(ACC.myquotes.formatter.format(unitPrice));

                let extPriceRow = Number(target.data("extprice"));
                $(".quote-table-entry .quote-table .quote-ext-price").eq(i).html(ACC.myquotes.formatter.format(extPriceRow));

                let quantityRow = Number(target.data("quantity"));
                $(".quote-table-entry .quote-table .quote-quantity").eq(i).find("input").val(quantityRow);
            }
        }

        if($(".quote-details-data").length){
            let target = $('.quote-details-data');
            let remainingBidTotal = Number(target.data("remainingbidtotal"));
            $(".quote-remainingtotal").html(ACC.myquotes.formatter.format(remainingBidTotal));
        }

        $(".quote-detailstotal").html(ACC.myquotes.formatter.format(extPrice));
        
    },
    approveQuote: function (e, num, text, text2, msg, btnText, quoteNumber, requested, optional, noteoption, mobnoteoption) {
        let adobeText = 'Approve Full Quote for Order';
        let target = $(".quote-details-data");
        let accountManager = target.data("accountmanager");
        text2 = text2.replace("~~", accountManager);
        let modifiedQty = 0;
        let aprroveTotal = 0;
        let checkIteration = '';
        let editQtyRows = '';
        let qutoeChooseItems = $(".quote-details-data").data("isfullapproval") || $(".qutoe-choose-items").length < 4 ? '.qutoe-choose-items.web-item' : '.qutoe-choose-items:visible';
        if (num != "all") {
            num = $(".qutoe-choose-items:checked").length;
            adobeText = 'Approve ' + num + ' Items for Order';
            checkIteration = ':checked';
        }
        $(qutoeChooseItems + checkIteration).each(function () {
            let target = $(this);
            let targetParent = target.parents(".quote-table-entry");
            let targetUnitPrice = targetParent.data("unitprice");
            let targetQty = targetParent.data("quantity");
            let targetTitle = targetParent.data("itemdescription");
            let stringBreak = ACC.global.wWidth < 1024 ? 66 : 130;
            targetTitle = (targetTitle.length > stringBreak) ? targetTitle.substring(0, stringBreak) + "..." : targetTitle;
            if (targetParent.find(".js-default-entry-quantity:visible").length) {
                targetQty = targetParent.find(".js-number-input-event-bind").val();
                editQtyRows += "<div class='row b-b-grey b-l-grey b-r-gray b-t-grey-xs b-t-grey-sm f-s-14 f-s-11-xs-px f-s-11-sm-px text-left-xs text-left-sm margin0 p-y-10'><div class='col-xs-3 bold text-gray hidden-md hidden-lg'>ITEM</div><div class='col-md-8 col-xs-9 text-align-left p-l-0-xs p-l-0-sm'>" + targetTitle + "</div><div class='col-xs-3 text-gray hidden-md hidden-lg bold'>QTY</div><div class='col-md-4 col-xs-9 p-l-0'><p class='margin0 bold display-ib-xs'>Requested " + targetQty + "</p><span class='hidden-md hidden-lg'> / </span><p class='margin0 display-ib-xs'>Orig. Quoted " + Number(targetParent.data("quantity")).toFixed(0) + "</p></div></div>";
                modifiedQty++;
            }
            aprroveTotal += Number(targetUnitPrice * targetQty);
        });
        aprroveTotal = ACC.estimate.formatter.format(aprroveTotal);
        ACC.adobelinktracking.quotesView('', adobeText, 'my account: quotes: quote detail: ' + quoteNumber);
        ACC.colorbox.open("", {
            html: "<div class='row'><div class='col-md-12 marginTopBVottom30 m-b-0-xs m-b-0-sm'><p class='f-s-28 f-s-20-xs-px text-default font-Geogrotesque'>" + text + (num != "all" ? num : '') + text2 + "</p><p class='f-s-22 f-s-16-xs-px text-default font-Geogrotesque flex-center justify-center edit-quote-popover'>" + msg + ": <span class='p-l-3'>" + aprroveTotal + "</span><span class='pointer f-s-12 glyphicon glyphicon-exclamation-sign p-b-10 p-x-5 text-green m-t-neg-5' data-toggle='popover' data-content='Applicable sales tax and any additional fees will be applied at the time of invoicing.'></span></p></div><div class='col-md-12 col-xs-12 col-xs-offset-0 marginBottom30 m-y-15-xs m-y-15-sm'><label class='control-label float-left f-s-12-xs-px f-s-12-sm-px text-dark-gray'><span class='hidden-md hidden-lg'>*</span>" + requested + " #:</label><input type='text' class='form-control' name='inputPO' id='inputPO' placeholder='" + optional + "' maxlength='25' /></div><div class='col-md-12 col-xs-12 col-xs-offset-0 marginBottom30 m-b-15-xs m-b-15-sm'><label for='optionNote' class='control-label f-s-12-xs-px f-s-12-sm-px float-left text-dark-gray'><span>" + noteoption + "</span>:</label><textarea type='text' class='form-control optionNote' name='optionalNote' id='optionalNote' placeholder='Enter your comment' maxlength='200'></textarea></div><div class='col-md-12 col-xs-12 col-xs-offset-0 m-t-0-xs m-b-0-xs" + (modifiedQty ? '' : ' hidden') + "'><label class='control-label f-s-12-xs-px f-s-12-sm-px float-left text-dark-gray'>Modified quantities (" + modifiedQty + "):</label></div><div class='col-md-12 col-xs-12 col-xs-offset-0 marginBottom30 scroll-bar f-s-12 m-b-5-xs m-b-5-sm" + (modifiedQty ? '' : ' hidden') + "' style='max-height: 30vh; overflow-y: auto;'><div class='row hidden-xs hidden-sm bg-light-grey border-grey bold margin0 p-y-10 text-default'><div class='col-md-8 col-xs-7 text-align-left'>ITEM</div><div class='col-md-4 col-xs-5 p-l-0'>QTY</div></div>" + editQtyRows + "</div><div class='col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1'><button class='btn btn-primary btn-block bold-text' onclick=\"ACC.myquotes.saveSelectedItems('" + num + "','" + quoteNumber + "');\">" + btnText + "</button></div></div>",
            title: "",
            width: ACC.global.wWidth < 1024 ? 412 : 725,
            className: "approveQuotePopup text-center",
            onComplete: function () {
                $('.approveQuotePopup [data-toggle="popover"]').popover({ placement: 'top', trigger: 'hover', container: '.edit-quote-popover' });
            }
        });
    },
    messageQuote: function (e, heading, text, btnText, qSeller, quoteNumber) {
        let target = $(".quote-details-data");
        let accountManager = target.data("accountmanager");
        heading = heading.replace("~~", accountManager);
        accountManager = accountManager.replace("'", "\\'");
        qSeller = qSeller.replace("~~", accountManager);
        ACC.adobelinktracking.quotesView("", "Contact " + qSeller, "my account: quotes: quote detail: " + quoteNumber);
        ACC.colorbox.open("", {
            html: "<div class='row'><div class='col-md-12 marginTopBVottom30'><p class='f-s-28 f-s-20-xs-px text-default font-Geogrotesque'>" + heading + "</p><textarea class='form-control quote-comments' rows='8' placeholder='" + text + "'></textarea></div><div class='col-md-4 col-md-offset-4 col-xs-6 col-xs-offset-3'><button class='btn btn-primary btn-block bold-text' onclick=\"ACC.myquotes.sendQuoteComment('" + qSeller + "','" + quoteNumber + "');\">" + btnText + "</button></div></div>",
            width: "650px",
            className: "approveQuotePopup text-center"
        });
    },
    itemSelectTrigger: false,
    itemSelect: function () {
        if (!ACC.myquotes.itemSelectTrigger) {
            ACC.myquotes.itemSelectTrigger = true;
            let deviceAt = (ACC.global.wWidth < 700) ? "mob-item" : "web-item";
            let elems = $(".qutoe-choose-items." + deviceAt).length;
            let enableApproveItems = 0;
            enableApproveItems = $(".qutoe-choose-items." + deviceAt + ":checked").length;
            if (enableApproveItems == elems || !enableApproveItems) {
                $(".quotes-all-item-show").removeClass("hidden");
                $(".quotes-all-item-hide").addClass("hidden");
            }
            else {
                $(".qutoe-choose-items-num").html(enableApproveItems);
                $(".quotes-all-item-hide").removeClass("hidden");
                $(".quotes-all-item-show").addClass("hidden");
            }
            ACC.myquotes.itemSelectTrigger = false;
        }
    },
    itemsSelections: function (triggerType) {
        if (!ACC.myquotes.itemSelectTrigger) {
            loading.start();
            ACC.myquotes.itemSelectTrigger = true;
            let deviceAt = (ACC.global.wWidth < 700) ? "mob-item" : "web-item";
            let elems = $(".qutoe-choose-items." + deviceAt).length;
            for (let i = 0; i < elems; i++) {
                let target = $(".qutoe-choose-items." + deviceAt).eq(i);
                let targetChecked = target.prop("checked");
                if (triggerType == "select-all" && !targetChecked) {
                    target.prop("checked", true);
                }
                else if (triggerType == "deselect-all" && targetChecked) {
                    target.prop("checked", false);
                }
            }
            $(".quotes-all-item-show").removeClass("hidden");
            $(".quotes-all-item-hide").addClass("hidden");
            loading.stop();
            ACC.myquotes.itemSelectTrigger = false;
        }
    },
    saveSelectedItems: function (num, quoteNumber) {
        let productList = [];
        let deviceAt = (ACC.global.wWidth < 700) ? "mob-item" : "web-item";
        let quoteRef = $(".quote-details-data");
        let target = $(".quote-table-entry");
        for (let i = 0; i < target.length; i++) {
            let iNumber = target.eq(i).data("itemnumber");
            let iDeription = $.trim(target.eq(i).find(".quote-itemdescription").text());
            let iQty = Number(target.eq(i).data("quantity")).toFixed(0);
            let unitPrice = Number(target.eq(i).data("unitprice"));
            let totalPrice = Number(target.eq(i).data("extprice"));
            let uom = target.eq(i).data("uom");
            let quoteDetailId = target.eq(i).data("quotedetailid");
            let isQtyMedefied = target.eq(i).find(".js-default-entry-quantity:visible").length ? true : false;
            let modefiedQty = Number(target.eq(i).find(".js-number-input-event-bind").val()).toFixed(0);
            let quoteLineNum = target.eq(i).data("linenumber");
            iNumber = (iNumber == "") ? '-' : iNumber;
            iDeription = (iDeription == "") ? '-' : iDeription;
            iQty = (iQty == "" || !iQty) ? '1' : iQty;
            modefiedQty = (modefiedQty == "" || !modefiedQty) ? iQty : modefiedQty;
            unitPrice = (unitPrice == "") ? '0.00' : unitPrice;
            totalPrice = isQtyMedefied ? (unitPrice*modefiedQty).toFixed(3) : iQty == 1 ? unitPrice : totalPrice == "" || totalPrice == 0 ? (unitPrice*iQty).toFixed(3) : (totalPrice).toFixed(3);
            uom = (uom == "") ? '-' : uom;
            quoteDetailId = (quoteDetailId == "") ? '-' : quoteDetailId;
            if (num == "all" || target.eq(i).find("." + deviceAt + ".qutoe-choose-items").prop("checked")) {
                productList.push(iNumber + "^" + iDeription + "^" + iQty + "^" + unitPrice + "^" + uom + "^" + totalPrice + "^" + quoteDetailId + "^" + isQtyMedefied + "^" + modefiedQty + "^" + quoteLineNum);
            }
        }
        let approveData = {
            quoteId: quoteRef.attr("data-quoteid"),
            quoteNumber: quoteRef.attr("data-quotenumber"),
            itemCount: num == "all" ? "full" : num,
            productList: productList.join("|"),
            writer: quoteRef.attr("data-writer"),
            accountManager: quoteRef.data("accountmanager"),
            accountManagerEmail: quoteRef.attr("data-accountManagerEmail"),
            branchManagerEmail: quoteRef.attr("data-branchManagerEmail"),
            writerEmail: quoteRef.attr("data-writerEmail"),
            pricerEmail: quoteRef.attr("data-pricerEmail"),
            poNumber: $("#inputPO").val(),
            optionalNotes: $("#optionalNote").val(),
            quotesBr: quoteRef.data("storeid"),
            customerNumber: quoteRef.data("customernumber")
        }
        $.ajax({
            url: ACC.config.encodedContextPath + '/my-account/quoteListEmail',
            type: 'post',
            data: approveData,
            success: function (response) {
                if (response) {
                    ACC.myquotes.quotePopupSuccess();
                }
                else {
                    ACC.myquotes.quotePopupError();
                    ACC.pendo.captureEvent("APPROVEQUOTEFAIL", {'quoteNumber':quoteNumber});
                }
            },
            error: function () {
                ACC.myquotes.quotePopupError();
                ACC.pendo.captureEvent("APPROVEQUOTEFAIL", {'quoteNumber':quoteNumber});
            }
        });
        ACC.adobelinktracking.quotesView('', 'Confirm: Approval of Quote for Order', 'my account: quotes: quote detail: ' + quoteNumber + ' : confirm approval quote for order popup');
    },
    sendQuoteComment: function (qSeller, quoteNumber) {
        let quoteComments = $.trim($('.quote-comments').val().replace(/\n/g, " ").replaceAll("|", "-"));
        if (!quoteComments.length) {
            $('.quote-comments').addClass("alert-danger border-red");
            $('.quote-comments').keyup(function () {
                if ($('.quote-comments').hasClass("alert-danger")) {
                    $('.quote-comments').removeClass("alert-danger border-red");
                }
            });
        }
        else {
            let quoteRef = $(".quote-details-data");
            let commentData = {
                quoteId: quoteRef.attr("data-quoteid"),
                quoteNumber: quoteRef.attr("data-quotenumber"),
                writer: quoteRef.attr("data-writer"),
                quoteComments: quoteComments,
                accountManager: quoteRef.data("accountmanager"),
                accountManagerEmail: quoteRef.attr("data-accountManagerEmail"),
                branchManagerEmail: quoteRef.attr("data-branchManagerEmail"),
                writerEmail: quoteRef.attr("data-writerEmail"),
                pricerEmail: quoteRef.attr("data-pricerEmail"),
                quotesBr: quoteRef.data("storeid"),
                customerNumber: quoteRef.data("customernumber")
            }
            $.ajax({
                url: ACC.config.encodedContextPath + '/my-account/contactSellerEmail',
                type: 'post',
                data: commentData,
                success: function (response) {
                    if (response) {
                        ACC.colorbox.close();
                    }
                    else {
                        ACC.myquotes.quotePopupError();
                    }
                },
                error: function () {
                    ACC.myquotes.quotePopupError();
                }
            });
            ACC.adobelinktracking.quotesView('', 'Submit : Contact ' + qSeller, 'my account: quotes: quote detail: ' + quoteNumber + ' : submit contact the seller popup');
        }
    },
    quotePopupError: function () {
        ACC.colorbox.open("", {
            html: "<div class='row'><div class='col-md-12 marginTopBVottom30'><p class='f-s-28 f-s-20-xs-px text-default font-Geogrotesque'>Something went wrong</p></div><div class='col-md-4 col-md-offset-4 col-xs-6 col-xs-offset-3'><button class='btn btn-default btn-block bold-text' onclick='ACC.colorbox.close()'>Try Again</button></div></div>",
            width: "600px",
            className: "approveQuotePopup text-center"
        });
    },
    quotePopupSuccess: function () {
        ACC.colorbox.open("", {
            html: $("#quote-modal-success").html(),
            width: ACC.global.wWidth < 1024 ? 412 : 725,
            className: "quotesPopupSuccess text-center",
            onComplete: function () {
                1024 > ACC.global.wWidth ? ACC.colorbox.resizeTo(ACC.global.wWidth, 570) : ACC.colorbox.resizeTo(725, 430);
            },
            onClosed: function () {
                window.location.href = window.location.href;
            }
        });
    },
    stickeyHeaderTop: 0,
    stickeyHeader: function (e) {
        let ref = $(".quote-stickey-header");
        let refHgt = ref.outerHeight();
        let refTop = ACC.myquotes.stickeyHeaderTop;
        let wScroll = $(e).scrollTop();
        let hScroll = ($(".linktracking-footer").length) ? $(".linktracking-footer").offset().top - refHgt : ACC.global.wHeight - refHgt;
        if (wScroll < hScroll && wScroll > refTop) {
            $(".quote-details-data").css({ marginBottom: refHgt });
            ref.css({ position: "fixed", left: 0, top: 0 }).children("div").addClass("container-lg container-fluid");
        }
        else {
            $(".quote-details-data").css({ marginBottom: 0 });
            ref.removeAttr("style").children("div").removeClass("container-lg container-fluid");
        }
    },
    pillToggleSticky: function (top) {
        let ref = $(".quotes-pill-container");
        let refHeight = ref.height();
        ref.css({ minHeight: refHeight });
        if (top > ref.offset().top + refHeight + 20) {
            ref.children().addClass("quotes-pill-box");
        }
        else {
            ref.children().removeClass("quotes-pill-box");
        }
    },
    quoteHistoryPopup: function (e, qId, qQty, qUnit) {
        let ref = $(e);
        let page = $(".page-quote-detail");
        let target = $(".quote-history-popup");
        if (ref.attr("data-historypopup") == "hide") {
            ACC.myquotes.getQuoteHistory(ref, qId, qQty, qUnit, target); //AJAX quote item history 
            page.find("[data-historypopup='show']").attr("data-historypopup", "hide").removeAttr("style");
            ref.attr("data-historypopup", "show").css({ backgroundColor: "#F1F6E9" });
        }
        else {
            ref.attr("data-historypopup", "hide").removeAttr("style");
            target.slideUp(function () {
                $(this).addClass("hidden").removeClass("showing-popup");
            });
        }
    },
    getQuoteHistory: function (ref, qId, qQty, qUnit, target) {
        $.ajax({
            type: "GET",
            url: ACC.config.encodedContextPath + "/my-account/quoteApprovalHistory",
            datatype: "json",
            data: {
                quoteDetailID: qId
            },
            success: function(data) {
                let result = data.itemDetails;
                //let result = ACC.myquotes.getQuoteHistoryData;
                let refHTML = '';
                if(data && result.length){
                    for (let i = 0; i < result.length; i++) {
                        refHTML += '<li class="no-border"><span class="bold">'+ result[i].approvalDate + '</span> - ' + Number(result[i].approvedQty).toFixed(0) + ' ' + qUnit + ' (of ' + Number(qQty).toFixed(0) + ') approved for order</li>';
                    }
                    target.find("ul").html(refHTML);
                    target.addClass("showing-popup").removeClass("hidden").css({ left: (ref.offset().left - 310 < 35) ? 35 : ref.offset().left - 310, top: ref.offset().top + 44}).slideDown();
                }
                else{
                    ACC.myquotes.quotePopupError();
                }
            },
            error: function(data, b, e) {
                ACC.myquotes.quotePopupError();
            }
        });
    },
    getQuoteHistoryData: [{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: '11.00'},{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: 37},{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: 17},{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: '3.00'},{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: 37},{quoteDeatilId: '', approvalDate: '26-05-2025', approvedQty: 17}],
    getHistoryDate: function (qDate) {
        let tempDate = qDate.split('T')[0].split('-');
        return tempDate[2] + '/' + tempDate[1] + '/' + tempDate[0].substring(2);
    },
    toggleEntries: function (e) {
        let ref = $(e);
        if (ref.hasClass("quote-entries-show")) {
            ref.removeClass("quote-entries-show");
            ref.html(ref.data("hidetext"));
            $(".quote-table-entry").removeClass("hidden");
        }
        else {
            $(".quote-table-entry").each(function (index) {
                if (index > 6) {
                    $(this).addClass("hidden");
                }
            });
            ref.html(ref.data("showtext"));
            ref.addClass("quote-entries-show");
        }
    },
    quotesPageView: function (pageName, pathingChannel, pathingPageName) {
        _AAData.page.pageName = pageName;
        _AAData.pathingChannel = pathingChannel;
        _AAData.pathingPageName = pathingPageName;
        _AAData.companyID = $("#parentUnitId").val();
    },
    quotesDetailsPDF: async function () {
        var jsPDF = window.jspdf.jsPDF;
        if (jsPDF && jsPDF.version) {
            $('#dversion').text('Version ' + jsPDF.version);
        }
        var doc = new jsPDF({ putOnlyUsedFonts: true, orientation: "portrait" });
        var posY = 4;
        let quoteNumber = $(".quote-number").text();
        let jobName = $(".quote-jobname").text();
        let accountManager = $(".quote-manager").text();
        let quoteWriter = $(".quote-writer").text();
        let hasDeliveryDetails = $("#hasDeliveryDetails").val() == 'true' ? true : false;
        let textBreak = { width: 30, length: 24 };
        let noOfDefaultLines = 2;
        doc.setDrawColor(0);
        doc.setFillColor(57, 58, 60);
        doc.rect(0, 0, 300, 20, "F");
        doc.addImage($(".quote-pdf-logo").prop("src"), 'png', 5, posY, 40, 11);
        posY += 27;

        doc.setFontSize(18);
        doc.text("Quotes Details", 5, posY);
        posY += 8;
        doc.setDrawColor(217, 217, 217);
        doc.setFillColor(238, 238, 238);
        doc.setLineWidth(0.2);
        doc.setFontSize(6);
        doc.setTextColor(153, 153, 153);
        doc.rect(5, posY, 200, 23, "FD"); //branch gray backgorund
        doc.line(70, posY, 70, posY + 23);  //branch right line
        doc.line(135, posY, 135, posY + 23);    //to right line
        posY += 6;

        doc.text("BRANCH", 8, posY);
        doc.text("TO", 73, posY);
        doc.text(hasDeliveryDetails ? $(".quote-pdf-shipto-title").text() : '', 138, posY);
        doc.setTextColor(0);
        doc.setFontSize(8);
        doc.text($(".quote-pdf-branch-address1").text(), 8, posY + 5);
        doc.text($(".quote-pdf-branch-address2").text(), 8, posY + 9);
        doc.text($(".quote-pdf-branch-address3").text(), 8, posY + ($(".quote-pdf-branch-address2").text() != "" ? 13 : 9));
        doc.text($(".quote-pdf-to-address1").text(), 73, posY + 5);
        doc.text($(".quote-pdf-to-address2").text(), 73, posY + 9);
        doc.text($(".quote-pdf-to-address3").text(), 73, posY + ($(".quote-pdf-to-address2").text() != "" ? 13 : 9));
        //doc.text(hasDeliveryDetails?$(".quote-pdf-shipto-address1").text():'', 138, posY + 5);
        doc.text(hasDeliveryDetails ? $(".quote-pdf-shipto-address2").text() : '', 138, posY + 5);
        doc.text(hasDeliveryDetails ? $(".quote-pdf-shipto-address3").text() : '', 138, posY + (hasDeliveryDetails && $(".quote-pdf-shipto-address2").text() != "" ? 9 : 5));
        posY += 23;

        doc.setTextColor(153, 153, 153);
        doc.setFontSize(6);
        doc.text("QUOTE NUMBER", 8, posY);
        doc.text("JOB NAME", 31, posY);
        doc.text("ACCOUNT MANAGER", (quoteWriter != "" ? 55 : 70), posY);
        if (quoteWriter != "") { doc.text("QUOTE WRITER", 83, posY) };
        doc.text("LAST MODIFIED", 108, posY);
        doc.text("EXPIRATION DATE", 133, posY);
        doc.text("TOTAL", 158, posY);
        doc.text("REMAINING", 183, posY);
        doc.setTextColor(0);
        doc.setFontSize(8);
        doc.text(quoteNumber, 8, posY + 5);
        if (quoteWriter != "") {
            textBreak = { width: 21, length: 14 };
        }

        //Job Name 2 line logic
        noOfDefaultLines = ACC.myquotes.pdfTextLineBreak(doc, jobName, textBreak.width, textBreak.length, 31, posY, noOfDefaultLines);
        //Account Manager 2 line logic
        noOfDefaultLines = ACC.myquotes.pdfTextLineBreak(doc, accountManager, textBreak.width, textBreak.length, (quoteWriter != "" ? 55 : 70), posY, noOfDefaultLines);
        //Quote Writer 2 line logic
        noOfDefaultLines = ACC.myquotes.pdfTextLineBreak(doc, quoteWriter, textBreak.width, textBreak.length, 83, posY, noOfDefaultLines);

        doc.text($(".quote-submitted-date").text(), 108, posY + 5);
        doc.text($(".quote-expiration-date").text(), 133, posY + 5);
        doc.setFont("helvetica", "bold");
        doc.text($(".quote-detailstotal").text(), 158, posY + 5);
        doc.text($(".quote-remainingtotal").text(), 183, posY + 5);

        tempPosY = posY - 6;
        posY += 6 + 3 * noOfDefaultLines;
        doc.setDrawColor(218, 217, 217);
        doc.setFillColor(255, 255, 255);
        doc.line(5, tempPosY, 5, posY);
        doc.line(28, tempPosY, 28, posY);
        if (quoteWriter != "") {
            doc.line(52, tempPosY, 52, posY);
            doc.line(80, tempPosY, 80, posY);
        }
        else {
            doc.line(67, tempPosY, 67, posY);
        }
        doc.line(105, tempPosY, 105, posY);
        doc.line(130, tempPosY, 130, posY);
        doc.line(155, tempPosY, 155, posY);
        doc.line(180, tempPosY, 180, posY);
        doc.line(205, tempPosY, 205, posY);
        doc.line(5, posY, 205, posY);

        posY += 6;
        doc.setFillColor(93, 93, 93);
        doc.setFont("helvetica", "normal");
        doc.rect(5, posY, 200, 10, "F");
        posY += 6;

        doc.setTextColor(255, 255, 255);
        doc.text("ITEM #", 8, posY);
        doc.text("DESCRIPTION", 30, posY);
        doc.text("QTY", 110, posY);
        doc.text("UNIT PRICE", 120, posY);
        doc.text("UOM", 145, posY);
        doc.text("EXT. PRICE", 160, posY);
        doc.text("APPROVED", 185, posY);
        posY += 12;

        doc.setTextColor(0);
        for (let i = 0; i < $(".quote-table-entry").length; i++) {
            let target = $(".quote-table-entry").eq(i);
            let targetItem = target.data('itemnumber').toString();
            let targetDescription = $.trim(target.find('.quote-itemdescription').text());
            let targetNote = $.trim(target.find('.quote-notes').text());
            let targetQuantity = target.data('quantity');
            let targetUnitprice = ACC.savedlist.formatNumberComma(target.data('unitprice'));
            let targetUOM = target.data('uom');
            let targetExtprice = ACC.savedlist.formatNumberComma(target.data('extprice'));
            let targetApproved = target.data('approved');
            let targetSt = target.data('st');
            let targetComment = target.data('comment');
            let lineBrNum = 125;
            let lineBrChr = 105;
            //console.log(i, targetItem, targetDescription, targetQuantity, targetUnitprice, targetUOM, targetExtprice, targetApproved, targetSt, targetComment);
            targetItem = (targetItem == "") ? "-" : targetItem;
            targetDescription = (targetDescription == "") ? "-" : targetDescription;
            targetQuantity = (targetQuantity == "") ? "1" : Number(targetQuantity).toFixed(0);
            targetUnitprice = (targetUnitprice == "") ? "$0.00" : "$" + targetUnitprice;
            targetUOM = (targetUOM == "") ? "-" : targetUOM;
            targetExtprice = (targetExtprice == "") ? "$0.00" : "$" + targetExtprice;

            doc.setFont("helvetica", "bold");
            if (targetSt) {
                doc.setDrawColor(217, 217, 217);
                doc.setFillColor(238, 238, 238);
                doc.rect(5, posY - 8, 200, 14, "F");
                doc.text("ST", 8, posY);
                doc.text(targetExtprice, 160, posY);
            }
            else if (targetComment) {
                doc.setFillColor(248, 248, 248);
                doc.rect(5, posY - 8, 200, 14, "F");
                doc.text("Comment", 8, posY);
            }
            else {
                //Item Number 2 line logic
                //console.log(doc.getTextWidth(targetItem));
                let textForWidth = (doc.getTextWidth(targetItem) > 20) ? targetItem.substr(0, 10) : targetItem;
                doc.text(textForWidth, 8, posY);
                targetItem = $.trim(targetItem.substr(10));
                if (targetItem != "") {
                    doc.text(targetItem, 8, posY + 3);
                }
            }

            doc.setFont("helvetica", "normal");

            if (!targetSt && !targetComment) {
                doc.text(targetQuantity, 110, posY);
                doc.text(targetUnitprice, 120, posY);
                doc.text(targetUOM, 145, posY);
                doc.text(targetExtprice, 160, posY);
                doc.text("-", 193, posY);
                if (targetApproved) {
                    doc.addImage($(".quote-pdf-check").prop("src"), 192, posY - 2, 4, 4);
                }
                lineBrNum = 75;
                lineBrChr = 54;
            }
            //Item Description  2 line logic
            while (targetDescription != "") {
                textForWidth = (doc.getTextWidth(targetDescription) > lineBrNum) ? targetDescription.substr(0, lineBrChr) + "-" : targetDescription;
                doc.text(textForWidth, 30, posY);
                //console.log(doc.getTextWidth(textForWidth));
                posY += 3;
                targetDescription = $.trim(targetDescription.substr(lineBrChr));
            }

            if (targetNote && targetNote != "") {
                doc.setFontSize(7);
                doc.setTextColor(120, 162, 147);
                posY += 1;
                doc.text("Note:", 30, posY);
                doc.setTextColor(153, 153, 153);
                let tempX = 38;
                while (targetNote != "") {
                    textForWidth = (doc.getTextWidth(targetNote) > lineBrNum) ? targetNote.substr(0, lineBrChr) + "-" : targetNote;
                    doc.text(textForWidth, tempX, posY);
                    //console.log(doc.getTextWidth(textForWidth));
                    tempX = 30;
                    posY += 3;
                    targetNote = $.trim(targetNote.substr(lineBrChr));
                }
                posY += 1;
                doc.setFontSize(8);
                doc.setTextColor(0);
            }
            doc.line(5, posY + 3, 205, posY + 3);
            posY += 11;
            if (posY > 280) { doc.addPage(); posY = 10; }
        }
        doc.setTextColor(90, 91, 93);
        doc.setFontSize(9);
        for (let i = 1; i < 3; i++) {
            posY += 5;
            let legalDisclaimer = $.trim($('.js-legal-disclaimer-' + i).text());
            while (legalDisclaimer != "") {
                let textForWidth = (doc.getTextWidth(legalDisclaimer) > 190) ? legalDisclaimer.substr(0, 180 - i * 38) + "-" : legalDisclaimer;
                doc.text(textForWidth, 5, posY);
                //console.log(i, doc.getTextWidth(textForWidth));
                posY += 5;
                legalDisclaimer = $.trim(legalDisclaimer.substr(180 - i * 38));
            }
        }
        doc.save('quotes_' + quoteNumber);
    },
    pdfTextLineBreak: function (doc, textString, width, length, posX, posY, noOfDefaultLines) {
        let textForWidth, counter = 0;
        while (textString != "") {
            textForWidth = (doc.getTextWidth(textString) > width) ? textString.substr(0, length) : textString;
            doc.text(textForWidth, posX, posY + 5);
            textString = $.trim(textString.substr(length));
            posY += 3;
            counter++;
        }
        noOfDefaultLines = noOfDefaultLines > counter ? noOfDefaultLines : counter;
        return noOfDefaultLines;
    },
    editQuote: function (e, type) {
        $(e).hide();
        $(".quote-edit-" + type).show();
        //console.log("editQuote: ", $(e).attr("class"), type);
        if (type == "request") {
            ACC.myquotes.editQuotePopup(type);
        }
        else if (type == "cancel") {
            ACC.colorbox.close();
            ACC.myquotes.enableEditQuote(type);
        }
        else {
            ACC.myquotes.disableEditQuote(type);
        }
    },
    editQuotePopup: function (type) {
        ACC.colorbox.open("<span class='f-s-28'>Modify Quote</span>", {
            html: $(".quote-edit-popup").html(),
            width: ACC.global.wWidth < 1024 ? 412 : 725,
            className: "quote-edit-popup text-center",
            onComplete: function () {
                //do nothing
            },
            onClosed: function () {
                if ($(".quote-edit-request").is(":visible")) {
                    $(".quote-edit-request").hide();
                    $(".quote-edit-start").show();
                };
            }
        });
    },
    enableEditQuote: function (type) {
        $(".quote-table-entry").each(function (e) {
            let ref = $(this);
            let target = ref.find(".quote-quantity");
            target.children(".input-group").addClass("border-primary border-radius-3 m-b-20");
            target.find(".js-number-input-event-bind").addClass("form-control b-l-green-1-imp b-r-green-1-imp f-s-20 f-s-16-xs-px f-s-16-sm-px font-Geogrotesque text-dark-gray").removeClass("border-none width-100-px text-left-xs textleft-sm").prop("disabled", false);
            target.find(".input-group-btn").removeClass("hidden");
            $(".js-edit-quote-note").removeClass("hidden");
        });
    },
    disableEditQuote: function (type) {
        $(".quote-table-entry").each(function (e) {
            let ref = $(this);
            let target = ref.find(".quote-quantity");
            let targetInput = target.find(".js-number-input-event-bind");
            target.children(".input-group").removeClass("border-primary border-radius-3 m-b-20");
            targetInput.removeClass("form-control b-l-green-1-imp b-r-green-1-imp f-s-20 f-s-16-xs-px f-s-16-sm-px font-Geogrotesque text-dark-gray").addClass("border-none width-100-px text-left-xs textleft-sm");
            targetInput.val(targetInput.data("default")).prop("disabled", true);
            target.find(".input-group-btn, .js-default-entry-quantity").addClass("hidden");
            $(".js-edit-quote-note").addClass("hidden");
        });
    },
    editQuoteQtyCheck: function (e) {
        let refParent = $(e).parents(".quote-quantity");
        let target = refParent.find(".js-number-input-event-bind");
        if (target.data('default') != target.val()) {
            refParent.find(".input-group").removeClass("m-b-20");
            refParent.find(".js-default-entry-quantity").removeClass("hidden");
        }
        else {
            refParent.find(".input-group").addClass("m-b-20");
            refParent.find(".js-default-entry-quantity").addClass("hidden");
        }
    }
};