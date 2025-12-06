ACC.project = {
    _autoload: [
        ["bindPageEvents", $('.page-projectDashboard').length > 0]
    ],
    expandCheckMannual: false,
    biddingRequesting: false,
    hiddenRequesting: false,
    favoriteRequesting: false,
    biddingAjax: false,
    hiddenAjax: false,
    favoriteAjax: false,
    bindPageEvents: function (e) {
        $('[data-toggle="popover"]').popover({
            placement: "top",
            trigger: "hover",
            parent: ".js-manage-drf"
        });
        $(".project-accordion-button").on("click", function () { ACC.project.isAllExpand() });
        ACC.project.getProject(true);
    },
    getProject: function (load) {
        $.ajax({
            type: 'POST',
            url: ACC.config.encodedContextPath + '/projectservices/project-dashboard',
            data: {
                pageSize: '50',
                page: '1',
                sortBy: 'pr.ProjectNumber'
            },
            success: function (response) {
                if (response) {
                    ACC.project.createProject(load, response.info);
                }
                else {
                    ACC.myquotes.quotePopupError();
                }
            },
            error: function (response) {
                ACC.myquotes.quotePopupError();
            }
        });
    },
    createProject: function (load, response) {
        let projectHTML = "";
        for (let i = 0; i < response.length; i++) {
            projectHTML += '<div class="col-md-12 p-y-25 p-x-20 b-b-grey"><div class="row"><div class="col-xs-8 text-green"><a class="text-green font-Geogrotesque no-text-decoration" href="' + ACC.config.encodedContextPath + '/projectservices/dashboard/' + response[i].projectID + '">' + response[i].projectName + '</a> <span class="badge bg-white border-primary m-l-5 border-radius-3 border-red f-s-12 f-w-700 text-default hidden">Quote Only</span></div><div class="col-xs-4 text-right"><div class="btn-group" role="group" aria-label="..."><button type="button" class="btn btn-primary btn-small btn-bold m-x-10 hidden">Priced Materials Ready</button><button type="button" class="btn btn-grey btn-small btn-iconlink border-radius-3-imp m-r-10 text-green ' + (response[i].isHiddenFromDashboard ? 'selected' : '') + '" onclick="ACC.project.hiddenProject(this)" data-prid="' + response[i].projectID + '" data-value="' + (response[i].isHiddenFromDashboard ? 'open' : 'close') + '" data-toggle="popover" data-content="' + (response[i].isHiddenFromDashboard ? 'Show this Project' : 'Hide this Project') + '" ><span class="glyphicon glyphicon-eye-close f-s-12 ' + (response[i].isHiddenFromDashboard ? 'hidden' : '') + '"></span><span class="glyphicon glyphicon-eye-open f-s-12 ' + (response[i].isHiddenFromDashboard ? '' : 'hidden') + '"></span></button><button type="button" class="btn btn-grey btn-small btn-iconlink border-radius-3-imp text-green ' + (response[i].isFavorite ? 'selected' : '') + '" onclick="ACC.project.favoriteProject(this)" data-prid="' + response[i].projectID + '" data-value="' + (response[i].isFavorite ? 'up' : 'down') + '" data-toggle="popover" data-content="' + (response[i].isFavorite ? 'Remove from Favorite' : 'Make a Favorite') + '" ><span class="glyphicon glyphicon-thumbs-up f-s-12"></span></button></div></div></div><div class="row m-t-5 m-b-10 flex-center text-default f-s-12"><div class="col-xs-9"><div class="row"><div class="col-xs-2">Plan Name:</div><div class="col-xs-10 bold">' + response[i].planName + '</div></div><div class="row"><div class="col-xs-2">Address:</div><div class="col-xs-10 bold">' + response[i].cityStateZip + '</div></div></div><div class="col-xs-3 p-t-5 text-right"><div class="form-group m-b-0">Bidding<label class="radio-inline m-l-10"><span class="colored-radio"><input onchange="ACC.project.biddingProject(this)" type="radio" name="inlineRadioOptions' + response[i].projectID + '" id="inlineRadio' + response[i].projectID + '_1" data-alert="Are you sure you want to request a bid for this project?" data-prid="' + response[i].projectID + '" value="yes" ' + (response[i].isBidding ? 'checked' : '') + '>Yes</span></label><label class="radio-inline"><span class="colored-radio"><input onchange="ACC.project.biddingProject(this)" type="radio" name="inlineRadioOptions' + response[i].projectID + '" id="inlineRadio' + response[i].projectID + '_2" data-alert="Are you sure you want to cancel the bid for this project?" data-prid="' + response[i].projectID + '" value="no" ' + (response[i].isBidding ? '' : 'checked') + '>No</span></label></div></div></div><div class="row flex-center text-default f-s-12"><div class="col-xs-6 p-r-0"><div class="row margin0 bg-light-gray p-y-5 l-h-15  border-radius-3"><div class="col-xs-4">Bid Date: <span class="bold">' + response[i].bidDate + '</span></div><div class="col-xs-4">Due Date: <span class="bold">' + response[i].projectDueDate + '</span></div><div class="col-xs-4 padding0">Submitted Date: <span class="bold">' + response[i].createdOn + '</span></div></div></div><div class="col-xs-2 l-h-15"><span class="bg-lightgreen p-y-5 p-x-10 border-radius-3 text-align-center display-block">PS Number: <span class="bold">' + response[i].projectNumber + '</span></span></div><div class="col-xs-2 l-h-15 padding0"><span class="bg-lightgreen p-y-5 p-x-10 border-radius-3 text-align-center display-block">Project Status: <span class="bold">' + response[i].projectStatus + '</span></span></div><div class="col-xs-2 text-right"><a href="' + ACC.config.encodedContextPath + '/projectservices/dashboard/' + response[i].projectID + '" class="btn btn-default btn-block btn-small btn-bold">View Project Info</a></div></div></div>';
        }
        $(".js-project-data-holder").html(projectHTML);
        $('.js-project-data-holder [data-toggle="popover"]').popover({
            placement: "top",
            trigger: "hover"
        });
    },
    expandCheck: function (e) {
        if (!ACC.project.expandCheckMannual) {
            let ref = $(e);
            let refStatus = ref.prop('checked');
            let targetText = refStatus ? 'close' : 'open';
            $('.project-accordion-' + targetText).trigger("onclick");
        }
    },
    isAllExpand: function () {
        ACC.project.expandCheckMannual = true;
        let openedAccordian = $(".project-accordion-open").length;
        let closedAccordian = $(".project-accordion-close").length;
        let expendCheck = $(".circle-radio-input");
        if (!closedAccordian && !expendCheck.prop("checked")) {
            expendCheck.prop("checked", true);
        }
        else {
            expendCheck.prop("checked", false);
        }
        ACC.project.expandCheckMannual = false;
    },
    clearFilter: function () {
        let statusChange;
        $(".project-group-item-check:checked").each(function () {
            statusChange = true;
            $(this).prop("checked", false);
        });
        if (statusChange) {
            ACC.project.filterOptionsStatus();
        }
    },
    filterOptionsCheck: function (e, num, cat, type) {
        ACC.project.filterOptionsStatus();
    },
    filterOptionsStatus: function () {
        let optionLength = $(".project-group-item-check:checked").length;
        if (optionLength > 1) {
            $(".project-filter-s").show();
        }
        else {
            $(".project-filter-s").hide();
        }
        $(".project-filter-num").html(optionLength);
    },
    biddingProject: function (e, currentStatus) {
        if (!ACC.project.biddingRequesting) {
            ACC.project.biddingRequesting = true;
            let ref = $(e);
            let refName = ref.attr("name");
            let refValue = ref.val();
            let refAlert = ref.data("alert");
            let refPrId = ref.data("prid");
            $('[name="' + refName + '"]').prop("disabled", true);
            ACC.colorbox.open(refAlert, {
                html: '<div class="row p-t-20"><div class="col-xs-6 text-right"><button class="btn btn-default btn-block border-radius-3-imp bold-text" onclick="ACC.colorbox.close()">Go Back</button></div><div class="col-xs-6"><button class="btn btn-primary btn-block border-radius-3-imp bold-text" onclick="ACC.project.biddingProjectRequest(this,\'' + refValue + '\',\'' + refPrId + '\',\'' + refName + '\')">Yes</button></div></div>',
                width: 500,
                className: "project-bid-popup text-align-center",
                escKey: false,
                overlayClose: false,
                onComplete: function () {
                    //setTimeout($.colorbox.resize, 500);
                    $(".project-bid-popup #cboxContent").addClass("border-radius-3");
                },
                onClosed: function () {
                    if (!ACC.project.biddingAjax) {
                        $('[name="' + refName + '"]').each(function () {
                            let tempRef = $(this);
                            if (tempRef.val() != refValue) {
                                tempRef.prop("checked", true);
                            }
                            tempRef.prop("disabled", false);
                        });
                        ACC.project.biddingRequesting = false;
                    }
                }
            });
        }
    },
    biddingProjectRequest: function (e, val, prid, refName) {
        ACC.project.biddingAjax = true;
        ACC.colorbox.close();
        $.ajax({
            type: 'GET',
            url: ACC.config.encodedContextPath + '/projectservices/project-bidding',
            dataType: "json",
            data: { "projectId": prid, "isBidding": (val == 'yes' ? true : false) },
            success: function (response) {
                if (response == "success") {
                    ACC.project.biddingProjectToast(refName);
                }
                else {
                    ACC.myquotes.quotePopupError();
                }
            },
            error: function () {
                ACC.myquotes.quotePopupError();
            }
        });
    },
    biddingProjectToast: function (refName) {
        let targetNum = ACC.global.toastShow('success', ['Updating', 'Updated'], ['fadeInUp', 'fadeOutUp']);
        let target = $("#js-toast-" + (targetNum));
        setTimeout(function () {
            ACC.global.toastActions(target, 2, 'success');
            ACC.project.biddingAjax = false;
        }, 2000);
        setTimeout(function () {
            ACC.global.toastActions(target, 3, 'success');
        }, 4000);
        $('[name="' + refName + '"]').prop("disabled", false);
        ACC.project.biddingRequesting = false;
    },
    hiddenProject: function (e) {
        if (!ACC.project.hiddenRequesting) {
            ACC.project.hiddenRequesting = true;
            let ref = $(e);
            let refValue = ref.data("value");
            let refPrId = ref.data("prid");
            ref.addClass("disabled").prop("disabled", true);
            ACC.project.hiddenAjax = true;
            let targetNum = ACC.global.toastShow('success', ['Updating', 'Updated'], ['fadeInUp', 'fadeOutUp']);
            $.ajax({
                type: 'GET',
                url: ACC.config.encodedContextPath + '/projectservices/project-hidden',
                dataType: "json",
                data: { "projectId": refPrId, "isHidden": (refValue == 'close' ? true : false) },
                success: function (response) {
                    if (response == "success") {
                        ACC.project.hiddenProjectToast(ref, refValue, targetNum);
                    }
                    else {
                        //ACC.project.hiddenProjectToast(ref, refValue, targetNum);
                        ACC.myquotes.quotePopupError();
                        let target = $("#js-toast-" + (targetNum));
                        ACC.global.toastActions(target, 4);
                        ref.removeClass("disabled").prop("disabled", false);
                        ACC.project.hiddenRequesting = false;
                    }
                },
                error: function () {
                    ACC.myquotes.quotePopupError();
                    let target = $("#js-toast-" + (targetNum));
                    ACC.global.toastActions(target, 4);
                    ref.removeClass("disabled").prop("disabled", false);
                    ACC.project.hiddenRequesting = false;
                }
            });
        }
    },
    hiddenProjectToast: function (ref, refValue, targetNum) {
        ACC.project.hiddenAjax = false;
        let target = $("#js-toast-" + (targetNum));
        ACC.global.toastActions(target, 2, 'success');
        setTimeout(function () {
            ACC.global.toastActions(target, 3, 'success');
        }, 4000);
        refValue = refValue == 'close' ? 'open' : 'close';
        ref.attr('data-content', (refValue == 'close' ? 'Hide this Project' : 'Show this Project'));
        ref.removeClass("disabled selected").addClass(refValue == 'open' ? 'selected' : '').prop("disabled", false).data("value", refValue).children('.glyphicon').addClass('hidden');
        ref.children('.glyphicon-eye-' + refValue).removeClass('hidden');
        ACC.project.hiddenRequesting = false;
    },
    favoriteProject: function (e) {
        if (!ACC.project.favoriteRequesting) {
            ACC.project.favoriteRequesting = true;
            let ref = $(e);
            let refValue = ref.data("value");
            let refPrId = ref.data("prid");
            ref.addClass("disabled").prop("disabled", true);
            ACC.project.favoriteAjax = true;
            let targetNum = ACC.global.toastShow('success', ['Updating', 'Updated'], ['fadeInUp', 'fadeOutUp']);
            $.ajax({
                type: 'GET',
                url: ACC.config.encodedContextPath + '/projectservices/project-favorite',
                dataType: "json",
                data: { "projectId": refPrId, "isFav": (refValue == 'up' ? false : true) },
                success: function (response) {
                    if (response == "success") {
                        ACC.project.favoriteProjectToast(ref, refValue, targetNum);
                    }
                    else {
                        //ACC.project.favoriteProjectToast(ref, refValue, targetNum);
                        ACC.myquotes.quotePopupError();
                        let target = $("#js-toast-" + (targetNum));
                        ACC.global.toastActions(target, 4);
                        ref.removeClass("disabled").prop("disabled", false);
                        ACC.project.favoriteRequesting = false;
                    }
                },
                error: function () {
                    ACC.myquotes.quotePopupError();
                    let target = $("#js-toast-" + (targetNum));
                    ACC.global.toastActions(target, 4);
                    ref.removeClass("disabled").prop("disabled", false);
                    ACC.project.favoriteRequesting = false;
                }
            });
        }
    },
    favoriteProjectToast: function (ref, refValue, targetNum) {
        ACC.project.favoriteAjax = false;
        let target = $("#js-toast-" + (targetNum));
        ACC.global.toastActions(target, 2, 'success');
        setTimeout(function () {
            ACC.global.toastActions(target, 3, 'success');
        }, 4000);
        refValue = refValue == 'up' ? 'down' : 'up';
        ref.attr('data-content', (refValue == 'up' ? 'Remove from Favorite' : 'Make a Favorite'));
        ref.removeClass("disabled selected").addClass(refValue == 'up' ? 'selected' : '').prop("disabled", false).data("value", refValue).children('.glyphicon');
        ACC.project.favoriteRequesting = false;
    }
}