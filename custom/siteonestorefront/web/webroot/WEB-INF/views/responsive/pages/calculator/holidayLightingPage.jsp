<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<template:page pageTitle="${pageTitle}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<spring:url value="/lighting-holiday/c/sh1113" var="shopHolidayLighting" htmlEscape="false"/>
<spring:url value="/articles/lighting" var="articleLighting" htmlEscape="false"/>
<spring:url value="/articles/article/" var="articleDetails" htmlEscape="false"/>
<div id="materialsCalculators" class=" hidden-xs hidden-sm materialsCalculators">
	<div class="col-md-12 col-xs-12 flex padding0 matCalpart1">
		<img alt="SiteOne Logo" src="${themeResourcePath}/images/holiday_lighting_calculator.png">
		<div class="row overlay-bcontent col-md-offset-1 m-t-50">
			<div class="">
				<common:calculatoricon /><span class="matcalpart-text">How much should I get?</span>
			</div>
			<h1 class="matcal-heading">Holiday Lighting</h1>
			<div class="col-md-4 padding0 matcal-desc">It's crucial to consider voltage drop when installing low-voltage outdoor
				lighting. Our calculator will help you determine what gauge of wire you should use for your project.
			</div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-md-10 col-md-offset-1 materialCalcSection">
		<div class="col-md-6 calc-left-section hl">
			<div>
				<div class="row input-heading-label">
					<span>Lighting Details</span>
				</div>
				<div class="row m-b-25 dropdown">
					<label for="typeOfWrap" class="input-label">Type of Wrap</label>
					<button class="col-md-12 select-option btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true" id="typeOfWrap" value="tree" name="typeOfWrap">
						Christmas Tree
						<span class="glyphicon glyphicon-chevron-down"></span>
					</button>
					<ul id="typeOfWrapDropdown" class="col-md-12 dropdown-menu dropdown-menu-hl">
						<li class="selected"><a data-type="tree">Christmas Tree</a></li>
						<li><a data-type="canopy">Tree Canopy</a></li>
						<li><a data-type="trunk">Tree Trunk</a></li>
						<li><a data-type="branch">Tree Branch</a></li>
					</ul>
				</div>
				<div class="row" id="inputFields">
					<div class="wrap-fields tree">
						<div class="m-b-25">
							<label for="height" class="input-label">Height of the Tree</label> (Not Including the Trunk)
							<div class="hl-input-section flex">
								<input type="text" name="height" class="calc-left-section-input form-control height" placeholder="Enter height in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="width" class="input-label">Width of the Tree</label>
							<div class="hl-input-section flex">
								<input type="text" name="width" class="calc-left-section-input form-control width" placeholder="Enter width in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap-fields canopy hidden">
						<div class="m-b-25">
							<label for="height" class="input-label">Height of the Tree Canopy</label>
							<div class="hl-input-section flex">
								<input type="text" name="height" class="calc-left-section-input form-control height" placeholder="Enter height in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="width" class="input-label">Width of the Canopy</label>
							<div class="hl-input-section flex">
								<input type="text" name="width" class="calc-left-section-input form-control width" placeholder="Enter width in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap-fields trunk hidden">
						<div class="m-b-25">
							<label for="height" class="input-label">Height of the Tree Trunk</label>
							<div class="hl-input-section flex">
								<input type="text" name="height" class="calc-left-section-input form-control height" placeholder="Enter height in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="circumference" class="input-label">Average Circumference of the Tree Trunk</label>
							<div class="hl-input-section flex">
								<input type="text" name="circumference" class="calc-left-section-input form-control circumference" placeholder="Enter circumference in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="spacing" class="input-label">Spacing Between Strands of Lights</label>
							<div class="hl-input-section flex">
								<input type="text" name="spacing" class="calc-left-section-input form-control spacing" placeholder="Enter spacing in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap-fields branch hidden">
						<div class="m-b-25">
							<label for="branches" class="input-label">Number of Branches</label>
							<input type="text" name="branches" class="calc-left-section-input form-control branches" placeholder="Enter number"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
						</div>
						<div class="m-b-25">
							<label for="length" class="input-label">Average Branch Length</label>
							<div class="hl-input-section flex">
								<input type="text" name="length" class="calc-left-section-input form-control length" placeholder="Enter length in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="diameter" class="input-label">Average Branch Diameter</label>
							<div class="hl-input-section flex">
								<input type="text" name="diameter" class="calc-left-section-input form-control diameter" placeholder="Enter diameter in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
						<div class="m-b-25">
							<label for="spacing" class="input-label">Spacing Between Rings of Lights</label>
							<div class="hl-input-section flex">
								<input type="text" name="spacing" class="calc-left-section-input form-control spacing" placeholder="Enter spacing in feet"
								oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
								onkeydown="return ACC.calculator.restrictInput(event)">
								<div class="hl-input-btn-grp flex">
									<button class="btn btn-primary toggle-unit" data-unit="inches">in</button>
									<button class="btn btn-primary m-l-3 toggle-unit active" data-unit="feet">ft</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="triangle-right hl"></div>
		</div>
		<div class="col-md-6 calc-right-section hl">
			<div class="row text-align-center">
				<img src="${themeResourcePath}/images/tree-christmas.png" alt="" class="typeOfWrapImage">
			</div>
			<div class="row font-Arial output-section hl">
				<div class="col-md-3 output-heading-label">TOTAL LENGTH NEEDED</div>
				<div class="col-md-9 hl-totalLength text-gray-1 totalLength">0.00 ft</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1 top--80">
		<div class="row m-b-25">
			<div class="col-md-6 holiday-lighting-section">
				<h2>Holiday Lighting Solutions</h2>
				<p class="m-t-25">SiteOne is your complete source <br>for Holiday Lighting & Accessories.</p>
				<a class="btn btn-primary m-t-25" href="${shopHolidayLighting}">View Featured Collection</a>
			</div>
			<div class="col-md-6 p-l-0 p-r-0 outdoor-lighting-image-section">
				<img src="${themeResourcePath}/images/holiday_lighting.png" alt="">
			</div>
		</div>
		<div class="row m-t-25 m-b-25">
			<h1 class="voltagedrop-heading padding-30 text-align-center f-s-32 text-default">Learn more about Holiday lighting</h1>
			<div class="row">
				<div class="product-item col-md-4 article-box">
					<div class="product-item-box">
						<div class="details">
							<img src="${themeResourcePath}/images/holidaylighting_learnmore1.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h2 class="store-specialty-heading article-heading two-line-text" title="Helpful Holiday Lighting Selling Points">Helpful Holiday Lighting Selling Points</h2>
								<div class="four-line-text" title="Need to drum up business as autumn begins to end? Add holiday lighting services to your service offering.">
									Need to drum up business as autumn begins to end? Add holiday lighting services to your service offering.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="${articleDetails}Helpful-Holiday-Selling-Points">Learn More</a>
								</div>
							</div>
						</div>
						<div class="cl"></div>
					</div>
					<div class="cl"></div>
				</div>
				<div class="product-item col-md-4 article-box">
					<div class="product-item-box">
						<div class="details">
							<img src="${themeResourcePath}/images/holidaylighting_learnmore2.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h2 class="store-specialty-heading article-heading two-line-text" title="Creating a Holiday Lighting Sales Portfolio">Creating a Holiday Lighting Sales Portfolio</h2>
								<div class="four-line-text" title="Use professional holiday lighting portfolio to close your holiday lighting sale.">
									Use professional holiday lighting portfolio to close your holiday lighting sale.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="${articleDetails}Utilizing-a-Portfolio-to-Close-Holiday-Lighting-Sales">Learn More</a>
								</div>
							</div>
						</div>
						<div class="cl"></div>
					</div>
					<div class="cl"></div>
				</div>
				<div class="product-item col-md-4 article-box">
					<div class="product-item-box">
						<div class="details">
							<img src="${themeResourcePath}/images/holidaylighting_learnmore3.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h2 class="store-specialty-heading article-heading two-line-text" title="Adding Holiday Lighting to Your Services Menu">Adding Holiday Lighting to Your Services Menu</h2>
								<div class="four-line-text" title="Holiday lighting installations are a great way to earn income during the slower winter months.">
									Holiday lighting installations are a great way to earn income during the slower winter months.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="${articleDetails}Adding-Holiday-Lighting-to-Your-Services-Menu">Learn More</a>
								</div>
							</div>
						</div>
						<div class="cl"></div>
					</div>
					<div class="cl"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row mobile-materialCal-wrapper hl hidden-md hidden-lg">
	<div class="m-b-15 flex hl mob-range-wrapper">
		<div class="col-xs-2 mob-range-text">Total Length Needed</div>
		<div class="col-xs-10 mob-output-total mob hl text-align-right totalLength">0.00 ft</div>
	</div>
	<div class="cl"></div>
	<div class="m-b-5 flex">
		<label for="typeOfWrap" class="mob-hl margin0">Type of Wrap</label>
		<select class="mob-calc-unitSelect hl" id="typeOfWrap" name="typeOfWrap" onchange="ACC.calculator.updateWrapSection(this.value)">
			<option value="tree">Christmas Tree</option>
			<option value="canopy">Tree Canopy</option>
			<option value="trunk">Tree Trunk / Column</option>
			<option value="branch">Tree Branch</option>
		</select>
	</div>
	<div class="cl"></div>
	<div id="inputFields">
		<div class="wrap-fields tree">
			<div class="m-b-5 flex">
				<label for="height" class="mob-hl m-b-0">Height</label>
				<div class="hl-input-section flex">
					<input type="text" name="height" class="mob-input-hl form-control height" placeholder="Enter height"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="width" class="mob-hl m-b-0">Width</label>
				<div class="hl-input-section flex">
					<input type="text" name="width" class="mob-input-hl form-control width" placeholder="Enter width"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="hl-calc-details-section">
				<div><b>Height: </b>Height of the tree without trunk</div>
				<div><b>Width: </b>Width of the tree at the base</div>
			</div>
		</div>
		<div class="wrap-fields canopy hidden">
			<div class="m-b-5 flex">
				<label for="height" class="mob-hl m-b-0">Height</label>
				<div class="hl-input-section flex">
					<input type="text" name="height" class="mob-input-hl form-control height" placeholder="Enter height"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="width" class="mob-hl m-b-0">Width</label>
				<div class="hl-input-section flex">
					<input type="text" name="width" class="mob-input-hl form-control width" placeholder="Enter width"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="hl-calc-details-section">
				<div><b>Height: </b>Height of the tree canopy</div>
				<div><b>Width: </b>Width of the tree canopy</div>
			</div>
		</div>
		<div class="wrap-fields trunk hidden">
			<div class="m-b-5 flex">
				<label for="height" class="mob-hl m-b-0">Height</label>
				<div class="hl-input-section flex">
					<input type="text" name="height" class="mob-input-hl form-control height" placeholder="Enter height"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="circumference" class="mob-hl m-b-0">Circumference</label>
				<div class="hl-input-section flex">
					<input type="text" name="circumference" class="mob-input-hl form-control circumference" placeholder="Enter width"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="spacing" class="mob-hl m-b-0">Spacing</label>
				<div class="hl-input-section flex">
					<input type="text" name="spacing" class="mob-input-hl form-control spacing" placeholder="Enter spacing"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="hl-calc-details-section">
				<div><b>Height: </b>Height of the tree trunk</div>
				<div><b>Width: </b>Average circumference</div>
				<div><b>Spacing: </b>Spacing between strands of lights</div>
			</div>
			<div class="flex-center">
				<common:checkmarkIcon width="25" height="25" iconColor="#78a22f" />
				<div class="m-l-10">
					<div class="voltagedrop-recommend">Use a tape measure or a piece of rope to measure the average circumference.</div>
					<div class="f-s-12">It's always good to err on the side of caution.</div>
				</div>
			</div>
		</div>
		<div class="wrap-fields branch hidden">
			<div class="m-b-5 flex">
				<label for="branches" class="mob-hl m-b-0">No. of Branches</label>
				<input type="text" name="branches" class="mob-input-hl form-control branches" placeholder="Enter number"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
			</div>
			<div class="m-b-5 flex">
				<label for="length" class="mob-hl m-b-0">Branch Length</label>
				<div class="hl-input-section flex">
					<input type="text" name="length" class="mob-input-hl form-control length" placeholder="Enter length"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="diameter" class="mob-hl m-b-0">Branch Diameter</label>
				<div class="hl-input-section flex">
					<input type="text" name="diameter" class="mob-input-hl form-control diameter" placeholder="Enter diameter"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="m-b-5 flex">
				<label for="spacing" class="mob-hl m-b-0">Spacing</label>
				<div class="hl-input-section flex">
					<input type="text" name="spacing" class="mob-input-hl form-control spacing" placeholder="Enter spacing"
					oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.checkAndCalculateLength();"
					onkeydown="return ACC.calculator.restrictInput(event)">
					<div class="hl-input-btn-grp flex">
						<button class="btn btn-primary mob toggle-unit" data-unit="inches">in</button>
						<button class="btn btn-primary m-l-3 mob toggle-unit active" data-unit="feet">ft</button>
					</div>
				</div>
			</div>
			<div class="hl-calc-details-section">
				<div><b>No. of Branches: </b>Number of Branches</div>
				<div><b>Branch Length: </b>Average branch length</div>
				<div><b>Branch Diameter: </b>Average branch diameter</div>
				<div><b>Spacing: </b>Spacing between strands of lights</div>
			</div>
			<div class="flex-center">
				<common:checkmarkIcon width="25" height="25" iconColor="#78a22f" />
				<div class="m-l-10">
					<div class="voltagedrop-recommend">Don't worry about perfect measurements.</div>
					<div class="f-s-12">Use your best guess for the branch length and diameter. It's always good to err on the side of caution.</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row typeOfWrap-image-section">
		<img src="${themeResourcePath}/images/tree-christmas.png" alt="" class="typeOfWrapImage">
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 promotions-section">
		<img src="${themeResourcePath}/images/holidaylighting_learnmore3.png" alt="alt">
		<div class="overlay">
			<h4 class="f-s-18 f-w-700 m-b-0-xs">Holiday Lighting<br/>Solutions</h4>
			<h6 class="m-t-5-xs">View our featured<br/>holiday products.</h6>
		</div>
		<div class="button-wrapper">
			<a class="btn btn-primary" href="${shopHolidayLighting}">Shop</a>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 promotions-section">
		<img src="${themeResourcePath}/images/holiday_lighting.png" alt="alt">
		<div class="overlay">
			<h4 class="f-s-18 f-w-700 m-b-0-xs">Expand Into Holiday<br/>Lighting</h4>
			<h6 class="m-t-5-xs">Grow your business<br/>during the winter.</h6>
		</div>
		<div class="button-wrapper">
			<a class="btn btn-primary" href="${articleLighting}">Learn More</a>
		</div>
	</div>
</div>
</template:page>