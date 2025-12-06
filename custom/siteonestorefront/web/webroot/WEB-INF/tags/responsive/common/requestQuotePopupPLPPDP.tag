<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="request-quote-popupPLPPDP" style="display:none;">
	<form id="requestQuoteSubmitStep1">
		<div class="row request-quote-step1">
			<div class="black-title col-md-12 f-s-28 f-s-22-xs-px font-Geogrotesque text-center"><spring:theme code="request.quote.popup.request.text1" /></div>
			<div class="black-title col-md-12 margin20 m-t-15-xs m-b-15-xs f-s-18 font-14-xs info-text-semi text-center"><spring:theme code="request.quote.popup.request.text2" />&nbsp;<span class="request-list-title bold"></span>.</div>
			<div class="col-md-12 font-required m-b-25 m-b-20-xs text-dark-gray">
				<span class="text-green">*</span><spring:theme code="request.quote.popup.request.text3" />
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="jobName"><spring:theme code="request.quote.popup.request.text4" /><span class="text-green">*</span></label>
					<input type="text" name="jobName" class="form-control quote-jobname js-script-input-event-bind"  maxlength="100" onchange="ACC.savedlist.requestInputReset(this)" />
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="jobStartDate"><spring:theme code="request.quote.popup.request.text5" /><span class="text-green">*</span></label>
					<input type="text" name="jobStartDate" class="form-control bg-white-imp quote-jobstartdate" onchange="ACC.savedlist.requestInputReset(this)" readonly="readonly" />
				</div>
			</div>
            <div class="col-md-6">
				<div class="form-group">
					<label for="jobName"><spring:theme code="request.quote.popup.request.text6" /><span class="text-green">*</span></label>
					<input type="text" name="qty" id="requestQuoteButtonQty" class="form-control b-t-0-xs input-prod-qty" onkeyup="if(/\D/g.test(this.value)) this.value=this.value.replace(/\D/g,'')" placeholder="1" />
                    
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="branch"><spring:theme code="request.quote.popup.request.text7" /><span class="text-green">*</span></label>
					<select name="branch" class="custom-select-arrow quote-branch" data-defaultstorename="${sessionStore.name}" data-defaultstoreid="${sessionStore.storeId}"></select>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label for="jobDescription"><spring:theme code="request.quote.popup.request.text8" /></label>
					<input type="text" name="jobDescription" maxlength="200" class="form-control quote-jobdescription js-script-input-event-bind" />
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label for="notes"><spring:theme code="request.quote.popup.request.text9" /></label>
					<textarea name="notes" maxlength="900" class="form-control quote-notes js-script-input-event-bind" rows="3"></textarea>
				</div>
			</div>
			
			<div class="col-md-4 col-md-offset-4 col-xs-8 col-xs-offset-2 m-t-35 m-t-25-xs padding0 text-center">
				<div onclick="ACC.savedlist.requestQuoteOverlaySaveplp(this);ACC.adobelinktracking.requestQuotePLP(this,'','Save and Continue','${cmsPage.name} : request a quote popup','');" class="btn btn-primary btn-block bold-text f-s-16 save-continue-btn"><spring:theme code="request.quote.popup.request.text10" /></div>
			</div>
		</div>
		<div class="row request-quote-step2 text-center hidden">
			<div class="black-title col-md-12 f-s-28 f-s-22-xs-px font-Geogrotesque"><spring:theme code="request.quote.popup.request.text11" /> <span class="requested-quote-num">1</span> <spring:theme code="request.quote.popup.request.text12" /> <span class="requested-quote-branch">Branch Name #123</span>.</div>
			<div class="black-title col-md-12 f-s-18 font-14-xs m-t-25 m-b-25 info-text-semi"><spring:theme code="request.quote.popup.request.text13" /></div>
			<div class="col-md-3 col-md-offset-3 col-xs-6">
				<div class="btn btn-default f-s-16 btn-block bold-text" onclick="ACC.colorbox.close();ACC.adobelinktracking.requestQuotePLP(this,'','Save and Continue: Cancel','${cmsPage.name} : request a quote popup: cancel','');"><spring:theme code="request.quote.popup.request.text14" /></div>
			</div>
			<div class="col-md-3 col-xs-6">
				<div onclick="ACC.savedlist.requestQuoteOverlayFormSubmitplp(this);" class="btn btn-primary f-s-16 btn-block bold-text"><spring:theme code="request.quote.popup.request.text15" /></div>
			</div>
		</div>
	</form>
</div>