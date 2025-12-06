<div class="request-quote-popup" style="display:none;">
	<form id="requestQuoteSubmitStep1">
		<div class="row request-quote-step1">
			<div class="black-title col-md-12 f-s-28 f-s-22-xs-px font-Geogrotesque text-center">Request A Quote</div>
			<div class="black-title col-md-12 margin20 m-t-15-xs m-b-15-xs f-s-18 font-14-xs info-text-semi text-center">Enter additional information to convert <span class="request-list-title"></span> to a quote.</div>
			<div class="col-md-12 font-required m-b-25 m-b-20-xs text-center text-dark-gray">
				<span class="text-green">*</span>Required
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="jobName">Job Name<span class="text-green">*</span></label>
					<input type="text" name="jobName" class="form-control quote-jobname js-script-input-event-bind" maxlength="100" onchange="ACC.savedlist.requestInputReset(this)" />
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="jobStartDate">Job Start Date<span class="text-green">*</span></label>
					<input type="text" name="jobStartDate" class="form-control bg-white-imp quote-jobstartdate" onchange="ACC.savedlist.requestInputReset(this)" readonly="readonly" />
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label for="branch">Branch<span class="text-green">*</span></label>
					<select name="branch" class="custom-select-arrow quote-branch" data-defaultstorename="${sessionStore.name}" data-defaultstoreid="${sessionStore.storeId}"></select>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label for="jobDescription">Job Description</label>
					<input type="text" name="jobDescription" maxlength="200" class="form-control quote-jobdescription js-script-input-event-bind" />
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label for="notes">Additional Notes</label>
					<textarea name="notes" maxlength="900" class="form-control quote-notes js-script-input-event-bind" rows="3"></textarea>
				</div>
			</div>
			<div class="col-md-12">
				<hr />
				<label for="addnotes">Add Items Not Available Online (optional)</label>
			</div>
			<section class="col-md-12 items-scroll">
				<div class="row form-group m-b-5 items-container input-container">
					<div class="col-md-5 col-xs-11 p-r-0">
						<input type="text" name="productDescription" onkeyup="ACC.savedlist.requestQuoteInputDesc(this)" maxlength="900" class="form-control input-prod-des js-script-input-event-bind" placeholder="Product Description" />
					</div>
					<div class="col-md-4 col-xs-5 ipt-itemno">
						<input type="text" name="itemNumber" class="form-control b-t-0-xs b-r-0-xs input-prod-num js-script-input-event-bind" maxlength="25" placeholder="Item #" />
					</div>
					<div class="col-md-1 col-xs-3 padding0">
						<input type="text" name="qty" class="form-control b-t-0-xs input-prod-qty js-script-input-event-bind" onkeyup="if(/\D/g.test(this.value)) this.value=this.value.replace(/\D/g,'')" placeholder="Qty" />
					</div>
					<div class="col-md-2 col-xs-3 pad-lft-10 p-l-0-xs p-r-0-xs">
						<div class="btn btn-default btn-block bold-text add-row-product disabled" onclick="ACC.savedlist.listAddRowProduct(this)">Add</div>
					</div>
				</div>
			</section>
			<div class="col-md-4 col-md-offset-4 col-xs-8 col-xs-offset-2 m-t-35 m-t-25-xs padding0 text-center">
				<div onclick="ACC.savedlist.requestQuoteOverlaySave(this);ACC.adobelinktracking.requestQuote(this,'','save and continue','my account : lists: request a quote popup');" class="btn btn-primary btn-block bold-text f-s-16 save-continue-btn">Save & Continue</div>
			</div>
		</div>
		<div class="row request-quote-step2 text-center hidden">
			<div class="black-title col-md-12 f-s-28 f-s-22-xs-px font-Geogrotesque">You've requested a quote for <span class="requested-quote-num">4</span> products to be sent to <span class="requested-quote-branch">Branch Name #123</span>.</div>
			<div class="black-title col-md-12 f-s-18 font-14-xs m-t-25 info-text-semi">You'll be notified by email when your quote is ready for your review.</div>
			<div class="col-md-12 marginTop30 m-b-15">
				<div class="form-group">
					<textarea maxlength="900" class="form-control quote-comment js-script-input-event-bind" rows="5" id="" placeholder="Would you like to add a note?"></textarea>
				</div>
			</div>
			<div class="col-md-3 col-md-offset-3 col-xs-6">
				<div class="btn btn-default f-s-16 btn-block bold-text" onclick="ACC.colorbox.close();ACC.adobelinktracking.requestQuote(this,'','cancel','my account : lists: request a quote popup');">Cancel</div>
			</div>
			<div class="col-md-3 col-xs-6">
				<div onclick="ACC.savedlist.requestQuoteOverlayFormSubmit(this);" class="btn btn-primary f-s-16 btn-block bold-text">Submit</div>
			</div>
		</div>
	</form>
</div>