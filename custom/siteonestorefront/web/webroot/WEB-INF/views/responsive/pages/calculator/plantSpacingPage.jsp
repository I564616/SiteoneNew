<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<template:page pageTitle="${pageTitle}">
	<spring:url value="/nursery/c/sh16" var="shopPlantSpacing" htmlEscape="false"/>
	<div id="materialsCalculators" class="hidden-xs hidden-sm materialsCalculators">
		<div class="col-md-12 col-xs-12 flex padding0 matCalpart1">
			<img alt="SiteOne Logo" src="${themeResourcePath}/images/plant_spacing_calculator.png">
			<div class="row overlay-bcontent col-md-offset-1 m-t-50">
				<div class="">
					<common:calculatoricon /><span class="matcalpart-text">How much should I get?</span>
				</div>
				<h1 class="matcal-heading ps">Plant Spacing<br>Calculator</h1>
			</div>
		</div>
		<div class="cl"></div>
		<div class="col-md-10 col-md-offset-1 materialCalcSection">
			<div class="col-md-6 calc-left-section ps">
				<div class="calc-left-section-area">
					<div class="m-b-25 modeButtons">
						<button class="btn btn-primary active m-r-10" data-mode="dimensions"
							onclick="ACC.calculator.handleModeButtonClick(this);">Dimensions</button>
						<button class="btn btn-primary" data-mode="sqft"
							onclick="ACC.calculator.handleModeButtonClick(this);">Square Feet</button>
					</div>
					<div class="row" id="inputFields">
						<div class="m-b-25 field" id="field-length">
							<label for="length" class="input-label">Length</label>
							<div class="hl-input-section flex">
								<input type="text" name="length" class="calc-left-section-input form-control length" autocomplete="off"
									placeholder="Enter length in feet" data-suffix="feet"
									oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
									onkeydown="return ACC.calculator.restrictInput(event)"
									onblur="ACC.calculator.handleSuffix(this, true)"
									onfocus="ACC.calculator.handleSuffix(this, false)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active"
										data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25 field" id="field-width">
							<label for="width" class="input-label">Width</label>
							<div class="hl-input-section flex">
								<input type="text" name="width" class="calc-left-section-input form-control width" autocomplete="off"
									placeholder="Enter width in feet" data-suffix="feet"
									oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
									onkeydown="return ACC.calculator.restrictInput(event)"
									onblur="ACC.calculator.handleSuffix(this, true)"
									onfocus="ACC.calculator.handleSuffix(this, false)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active"
										data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25 field hidden" id="field-sqft">
							<label for="sqft" class="input-label">Square Feet</label>
							<div class="hl-input-section flex">
								<input type="text" name="sqft" class="calc-left-section-input form-control sqft" autocomplete="off"
									placeholder="Enter area in square feet" data-suffix="square feet"
									oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
									onkeydown="return ACC.calculator.restrictInput(event)"
									onblur="ACC.calculator.handleSuffix(this, true)"
									onfocus="ACC.calculator.handleSuffix(this, false)">
							</div>
						</div>
						<div class="m-b-25 field" id="field-spacing">
							<label for="spacing" class="input-label">Spacing Between Plants</label>
							<div class="hl-input-section flex">
								<input type="text" name="spacing" class="calc-left-section-input form-control spacing" autocomplete="off"
									placeholder="Enter spacing in inches" data-suffix="inches"
									oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
									onkeydown="return ACC.calculator.restrictInput(event)"
									onblur="ACC.calculator.handleSuffix(this, true)"
									onfocus="ACC.calculator.handleSuffix(this, false)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit active" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
					</div>
					<div class="row layoutButtons">
						<label class="input-label m-b-10">Arrangement</label>
						<div class="arrangement-btn-section">
							<button class="btn btn-primary active" data-layout="square"
								onclick="ACC.calculator.handleLayoutButtonClick(this);">Square</button>
							<button class="btn btn-primary m-x-10" data-layout="triangular"
								onclick="ACC.calculator.handleLayoutButtonClick(this);">Triangular</button>
							<button class="btn btn-primary" data-layout="row"
								onclick="ACC.calculator.handleLayoutButtonClick(this);">Row</button>
						</div>
					</div>
				</div>
				<div class="triangle-right ps"></div>
			</div>
			<div class="col-md-6 calc-right-section ps">
				<div class="noOfPlantsHeaderlabel">Number of Plants Needed:</div>
				<div class="font-Arial output-section ps">
					<div class="layout-img-section m-r-20">
						<div class="layout-heading m-b-5">Square<br>Spacing</div>
						<img src="${themeResourcePath}/images/layout_square.png" alt="" class="typeOfLayout">
					</div>
					<div class="layout-result-section">
						<div class="plantCount"></div>
						<div class="plants"></div>
					</div>
				</div>
				<div class="start-position"></div>
			</div>
		</div>
		<div class="col-md-10 col-md-offset-1 top--80 p-l-0 p-r-0 layout-desc">
			<div class="col-md-4">
				<h2>Triangular Spacing</h2>
				<p>Triangular spacing will create a denser coverage of groundcover plants that will fill an area much
					more quickly. The staggered spacing also creates a more dynamic appearance.</p>
			</div>
			<div class="col-md-4">
				<h2>Square Spacing</h2>
				<p>Square spacing is perfect when you want to completely cover an area equally but save on bulb or plant
					costs. The clean, neat rows are also very visually appealing.</p>
			</div>
			<div class="col-md-4">
				<h2>Row Spacing</h2>
				<p>Don't forget to consider the minimum width of plants that are planted in a row or hedge. Their roots
					need adequate room to grow.</p>
			</div>
		</div>
		<p class="disclaimer ps col-md-10 col-md-offset-1">Calculations are an estimate and do not account for
			<br>variations in landscape elevation.
		</p>
	</div>
	<div class="row mobile-materialCal-wrapper ps hidden-md hidden-lg">
		<div class="m-b-5 flex ps mob-range-wrapper">
			<div class="col-xs-2 mob-range-text">
				<div class="layout-heading mob m-b-5">Square<br>Spacing</div>
				<img src="${themeResourcePath}/images/layout_square.png" alt="" class="typeOfLayout">
			</div>
			<div class="col-xs-10 mob-output-total mob ps text-align-right">
				<div class="plantCount"></div>
				<div class="plants"></div>
			</div>
		</div>
		<div class="m-b-15 flex first-row mob-range-wrapper">
			<div class="col-xs-2 mob-range-text">First<br/>Row</div>
			<div class="col-xs-10 mob ps text-align-right start-position"></div>
		</div>
		<div class="cl"></div>
		<div class="m-b-25 modeButtons">
			<button class="btn btn-primary active m-r-10" data-mode="dimensions"
				onclick="ACC.calculator.handleModeButtonClick(this);">Dimensions</button>
			<button class="btn btn-primary" data-mode="sqft" onclick="ACC.calculator.handleModeButtonClick(this);">Square
				Feet</button>
		</div>
		<div id="inputFields">
			<div class="m-b-5 field flex" id="field-length">
				<label for="length" class="input-label mob-ps m-b-0">Length</label>
				<div class="hl-input-section ps flex">
					<input type="text" name="length" class="mob-input-ps form-control length" placeholder="Length in feet"
						data-suffix="feet" autocomplete="off"
						oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
						onkeydown="return ACC.calculator.restrictInput(event)"
						onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 field flex" id="field-width">
				<label for="width" class="input-label mob-ps m-b-0">Width</label>
				<div class="hl-input-section ps flex">
					<input type="text" name="width" class="mob-input-ps form-control width" placeholder="Width in feet"
						data-suffix="feet" autocomplete="off"
						oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
						onkeydown="return ACC.calculator.restrictInput(event)"
						onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 field flex hidden" id="field-sqft">
				<label for="sqft" class="input-label mob-ps m-b-0">Square Feet</label>
				<div class="hl-input-section ps flex">
					<input type="text" name="sqft" class="mob-input-ps form-control sqft" placeholder="Area in square feet"
						data-suffix="square feet" autocomplete="off"
						oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
						onkeydown="return ACC.calculator.restrictInput(event)"
						onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
				</div>
			</div>
			<div class="m-b-25 field flex" id="field-spacing">
				<label for="spacing" class="input-label mob-ps m-b-0">Spacing</label>
				<div class="hl-input-section ps flex">
					<input type="text" name="spacing" class="mob-input-ps form-control spacing"
						placeholder="Spacing in inches" data-suffix="inches" autocomplete="off"
						oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculatePlants();"
						onkeydown="return ACC.calculator.restrictInput(event)"
						onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit active" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
		</div>
		<div class="m-b-25 layoutButtons">
			<div class="arrangement-btn-section">
				<button class="btn btn-primary active" data-layout="square"
					onclick="ACC.calculator.handleLayoutButtonClick(this);">Square</button>
				<button class="btn btn-primary m-x-10" data-layout="triangular"
					onclick="ACC.calculator.handleLayoutButtonClick(this);">Triangular</button>
				<button class="btn btn-primary" data-layout="row"
					onclick="ACC.calculator.handleLayoutButtonClick(this);">Row</button>
			</div>
		</div>
		<div class="m-b-25 layout-desc">
			<div>
				<h2>Square Spacing</h2>
				<p>Square spacing is perfect when you want to completely cover an area but save on bulb or plant costs. The clean, neat
					rows are also visually appealing.</p>
			</div>
			<div>
				<h2>Triangular Spacing</h2>
				<p>Triangular spacing will create a denser coverage of groundcover plants that will fill an area much more quickly. The
					staggered spacing also creates a more dynamic appearance.</p>
			</div>
			<div>
				<h2>Row Spacing</h2>
				<p>Don't forget to consider the minimum width of plants that are planted in a row or hedge. Their roots need adequate
					room to grow.</p>
			</div>
		</div>
		<div class="cl"></div>
		<div class="col-xs-12 promotions-section m-b-25">
			<img src="${themeResourcePath}/images/shop_plant_spacing.png" alt="alt">
			<div class="overlay">
				<h4 class="f-s-18 f-w-700 m-b-10-xs m-t-0-xs ps">SiteOne Nursery</h4>
				<h6 class="m-t-5-xs l-h-16-xs ps">If you grow,<br />you know.</h6>
			</div>
			<div class="button-wrapper">
				<a class="btn btn-primary m-b-10-xs" href="${shopPlantSpacing}">Shop</a>
			</div>
		</div>
		<p class="disclaimer ps">Calculations are an estimate and do not account for variations in landscape elevation.</p>
	</div>
</template:page>