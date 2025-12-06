ACC.partner = {
	_autoload: [
		"popupEnable",
		"PartnerPageAnalyticData"
	],
	dashboardPage: ($(".page-accountPartnerProgramPage").length) ? true : false,
	contentPage: ($(".page-pilotPartnersProgramPage").length) ? true : false,
	perksPage: ($(".page-partnerPerksPage").length) ? true : false,
	popupEnable: function () { //creating partners enroll popup's HTML
		if (ACC.partner.contentPage || ACC.partner.dashboardPage || ACC.partner.perksPage) {
			let popupHtml = '<div class="enroll-popup-content hidden"><div class="row text-default font-Geogrotesque text-center"><div class="col-md-offset-2 col-md-8 col-xs-offset-2 col-xs-8 m-b-20-xs"><img src="/_ui/responsive/theme-lambda/images/partner_logo.png" alt="partners-program" title="partners-program" /></div><div class="col-md-12 enroll-confirm"><h4 class="bold">' + ACC.config.partnerBecome + '</h4><p>' + ACC.config.partnerGet1 + '</p></div><div class="col-md-12 enroll-done"><h4 class="bold">' + ACC.config.partnerCongratulations + '</h4><h4 class="bold">' + ACC.config.partnerPPMember + '</h4><p>' + ACC.config.partnerEarningRewards + '</p></div><div class="col-md-6 marginTop40 no-margin-xs hidden-xs hidden-sm enroll-confirm enroll-done"><button class="btn btn-default btn-block enroll-close enroll1">' + ACC.config.partnerCancel + '</button></div><div class="col-md-6 marginTop40 enroll-confirm enroll-done"><button class="btn btn-primary btn-block enroll-confirm" onclick="ACC.partner.enrollModal(\'progress\',\'false\')">' + ACC.config.partnerEnroll + '</button><button class="btn btn-primary btn-block enroll-done enroll-tracking" onclick="location.href=\'' + ACC.config.encodedContextPath + '/my-account/accountPartnerProgram\'">' + ACC.config.partnerPoints + '</button></div><div class="col-md-6 marginTop40 no-margin-xs hidden-md hidden-lg enroll-confirm enroll-done"><button class="btn btn-default btn-block enroll-close enroll2">' + ACC.config.partnerCancel + '</button></div><div class="col-md-12 col-xs-12 enroll-progress"><div id="floatBarsG"><div id="floatBarsG_1" class="floatBarsG"></div><div id="floatBarsG_2" class="floatBarsG"></div><div id="floatBarsG_3" class="floatBarsG"></div><div id="floatBarsG_4" class="floatBarsG"></div><div id="floatBarsG_5" class="floatBarsG"></div><div id="floatBarsG_5" class="floatBarsG"></div><div id="floatBarsG_6" class="floatBarsG"></div><div id="floatBarsG_7" class="floatBarsG"></div><div id="floatBarsG_8" class="floatBarsG"></div></div><h4 class="bold marginTop40">' + ACC.config.partnerHang + '</h4></div></div></div>';
			$(".page-pilotPartnersProgramPage, .page-accountPartnerProgramPage, .page-partnerPerksPage").append(popupHtml);
			if ($(".partner-enrolled-true").length && ACC.partner.dashboardPage) {
				ACC.partner.partnersEnrollStatus();
			}
			
			_AAData.pathingPageName= "Contractor Services: Partners";
			_AAData.pathingChannel= "Contractor Services";
		}
		if(ACC.partner.dashboardPage && $(".partner-section").attr("data-pilot") == "true"){
			$(".global-alerts").addClass("hidden");
		}
		if(ACC.partner.perksPage){
			ACC.global.partnerCarouselIndicators("#partnerCarousel", "#perksCarousel", 100);
		}
	},
	enrollModal: function (mode, close, conentPage) {	//opening partners enroll popup
		let modalClose = (close == 'true') ? true : false;
		$(".enroll-popup-content").find(".enroll-confirm, .enroll-progress, .enroll-done").addClass("hidden");
		$(".enroll-popup-content .enroll-" + mode).removeClass("hidden");
		$(".enroll-popup-content .enroll-close").attr("onclick", (conentPage) ? "location.href=\'" + ACC.config.encodedContextPath + "/Partners\'" : (modalClose) ? "ACC.colorbox.close()" : "location.href=\'" + ACC.config.encodedContextPath + "/my-account/accountPartnerProgram\'");
		ACC.colorbox.open("", {
			html: $(".enroll-popup-content").html(),
			width: "450px",
			escKey: false,
			overlayClose: modalClose,
			closeButton: modalClose,
			className: "enroll-popup"
		});
		if (mode == "progress") {
			$.ajax({
				url: ACC.config.encodedContextPath + '/my-account/enrollCustomerInTalonOne',
				type: 'post',
				success: function (response) {
					if (response) {
						ACC.partner.enrollModal('done', 'false', ACC.partner.contentPage);
					}
				},
				error: function (response) {
					if (response) {
						ACC.partner.enrollModal('done', 'false', ACC.partner.contentPage);
					}
				}
			});
		}
	},
	partnersEnrollStatus: function () {	//checking the Talon One Enroll Status
		$.ajax({
			url: ACC.config.encodedContextPath + '/my-account/getTalonOneLoyaltyStatus',
			type: 'get',
			success: function (response) {
				if (response == true) {
					$(".partner-enrolled-true").removeClass("hidden");
				}
				else {
					$(".partner-enrolled-false").removeClass("hidden");
				}
			},
			error: function () {
				$(".partner-enrolled-false").removeClass("hidden");
			}
		});
	},
	PartnerPageAnalyticData:function(){
		$(document).on("click", ".enroll-tracking, button.enroll-confirm", function(e){
			var linkName= $(this).text();
			digitalData.eventData={
				  linktype:"",
				  linkName:linkName,
				  onClickPageName: "Contractor Services: Partners"
			}
			try {
				_satellite.track('linkClicks');
			}catch(e){}
		})
		
		$(document).on("click", "button.enroll-close", function(){
			console.log("lll");
			if($(this).attr("onClick")=="ACC.colorbox.close()"){
				 var linkName="Cancel: Enroll";
			}
			if($(this).attr("onClick")=="location.href=\'" + ACC.config.encodedContextPath + "/Partners\'"){
				 var linkName="Cancel: see your points";
			}
			
			digitalData.eventData={
				  linktype:"",
				  linkName:linkName,
				  onClickPageName: "Contractor Services: Partners"
			}
			try {
				_satellite.track('linkClicks');
			}catch(e){}
		})
		
		 
	}
}