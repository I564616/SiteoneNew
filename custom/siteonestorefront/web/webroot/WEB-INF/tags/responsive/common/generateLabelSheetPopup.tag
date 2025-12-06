<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="className" required="false" type="java.lang.String" %>

<div class="generate-label-popup" style="display:none;">
		<div class="row request-quote-step1">
			<div class="black-title col-md-12 f-s-28 font-Geogrotesque text-center  m-b-40">
			<span id="${className}count" class="p-r-10 text-dark-gray"></span><spring:theme code="text.lists.generateLabel.itemSelected" /></div>
				<div class="col-md-7">
				<div class="black-title f-s-24 f-w-500 font-Geogrotesque f-s-20-xs-px m-b-20">
					<spring:theme code="text.lists.generateLabel.dataOptions" />
					<span class="text-dark-gray"><spring:theme code="text.lists.generateLabel.dataOptions.apply" /></span>
				</div>
				<div class="message-center">
					<div class="checkbox-container m-b-10"">
						<input type="checkbox" class="custom-checkbox m-b-0-imp m-r-0-imp" name="selectedItems" value="itemnumber"/>
						<span class="checkbox-label p-l-5"><spring:theme code="text.lists.generateLabel.itemNumber" /></span>
					</div>
				</div>
				<div class="message-center">
					<div class="checkbox-container m-b-10">
						<input type="checkbox" class="custom-checkbox m-b-0-imp m-r-0-imp" name="selectedItems" value="description"/>
						<span class="checkbox-label p-l-5"><spring:theme code="text.lists.generateLabel.description" /></span>
					</div>
				</div>
				<div class="message-center b-b-grey">
					<div class="checkbox-container m-b-20"">
						<input type="checkbox" class="custom-checkbox m-b-0-imp m-r-0-imp" name="selectedItems" value="image"/>
						<span class="checkbox-label p-l-5"><spring:theme code="text.lists.generateLabel.image" /></span>
					</div>
				</div>
				<div class="black-title f-s-24 f-w-500 font-Geogrotesque m-t-20 m-b-20 f-s-20-xs-px"><spring:theme code="text.lists.generateLabel.sizeSelection" /><span class="text-dark-gray"><spring:theme code="text.lists.generateLabel.sizeSelection.choose" /></span></div>
				<div class="radio-container p-x-20-xs p-x-20-sm m-b-20 f-w-400 f-s-14 f-s-18-xs-px">
					<label class="radio-inline">
						<span class="colored-radio">
							<input type="radio" name="label" class="label-radio" value="10" checked onchange="ACC.savedlist.generatelabelImageandText(this)" />
						</span>
					</label>
					<span class="radio-label p-l-20">2" x 4" (10 <spring:theme code="text.lists.generateLabel.labelSheet" />)</span>
				</div>
				<div class="radio-container p-x-20-xs p-x-20-sm m-b-20 f-w-400 f-s-14 f-s-18-xs-px">
					<label class="radio-inline">
						<span class="colored-radio">
							<input type="radio" name="label" class="label-radio" value="15" onchange="ACC.savedlist.generatelabelImageandText(this)"  />
						</span>
					</label> 
					<span class="radio-label p-l-20">2" x 2-5/8" (15 <spring:theme code="text.lists.generateLabel.labelSheet" />)</span>
				</div>
				<div class="radio-container p-x-20-xs p-x-20-sm m-b-20 f-w-400 f-s-14 f-s-18-xs-px">
					<label class="radio-inline">
						<span class="colored-radio">
							<input type="radio" name="label" class="label-radio" value="30" onchange="ACC.savedlist.generatelabelImageandText(this)"  />
						</span>
					</label>
					<span class="radio-label p-l-20">1" x 2-5/8" (30 <spring:theme code="text.lists.generateLabel.labelSheet" />)</span>
				</div>
			</div>
			<div class="col-md-5">
				<div class="section-title black-title f-s-24 f-w-500 font-Geogrotesque">
					<spring:theme code="text.lists.generateLabel.preview" />
				</div>
				<div class="preview-box m-t-25">
					<div id="labelPreview10" class="label-preview-container visible">
						<div class="preview-box size-10 border-none" data-count="10">
							<c:forEach begin="1" end="10">
								<div class="label-preview-box"></div>
							</c:forEach>
						</div>
					</div>
					<div id="labelPreview15" class="label-preview-container">
						<div class="preview-box size-15 border-none" data-count="15">
							<c:forEach begin="1" end="15">
								<div class="label-preview-box"></div>
							</c:forEach>
						</div>
					</div>
					<div id="labelPreview30"  class="label-preview-container">
						<div class="preview-box size-15 border-none">
							<c:forEach begin="1" end="30">
								<div class="label-preview-box"></div>
							</c:forEach>
						</div>
					</div>
				</div>			
			</div>
		</div>
		<div class="row b-t-grey m-t-50 margin0">
			<div class="col-md-12 black-title f-s-24 m-t-20 f-w-500 p-l-0 m-b-15 font-Geogrotesque">
				<spring:theme code="text.lists.generateLabel.print" />				
			</div>
			<div class="print-info text-dark-gray l-h-30">
				<span class=" f-s-18 f-w-700">
					<spring:theme code="text.lists.generateLabel.print.info" />
				</span>
				
				<span class="generate-preview-text visible f-w-400" id="previewTexts10">
					<spring:theme code="text.lists.generateLabel.recommendedWeight.10" />					
				</span>				
				<span class="generate-preview-text f-w-400" id="previewTexts15">
					<spring:theme code="text.lists.generateLabel.recommendedWeight.15" />	
				</span>
				<span class="generate-preview-text f-w-400" id="previewTexts30">
					<spring:theme code="text.lists.generateLabel.recommendedWeight.30" />	
				</span>
				<div class ="generate-preview-text extra-info visible text-dark-gray text-italic m-t-10" id="extraInfo10">
					<span class="text-dark-gray f-w-700">
						<spring:theme code="text.lists.generateLabel.recommendedWeight.10.tip" />
					</span>
					<span class="f-w-400">
						<spring:theme code="text.lists.generateLabel.recommendedWeight.10.tip.info" />
					</span>
				</div>
			</div>
			
		</div>
		<div class="row popupbtns m-t-30">
				<div class="col-md-6">
					<button class="btn btn-default  f-w-700 bold btn-block close-generatelabel-btn">
						<spring:theme code="text.lists.generateLabel.cancel" />
					</button>
				</div>
				<div class="col-md-6">
					<button class="btn btn-primary f-w-700 bold btn-block" onclick="ACC.savedlist.getGenerateLabels()">
						<spring:theme code="text.lists.generateLabel.generateLabelSheet" />
					</button>
				</div>
			</div>
</div>