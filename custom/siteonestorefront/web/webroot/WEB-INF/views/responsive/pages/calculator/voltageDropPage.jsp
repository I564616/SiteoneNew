<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<template:page pageTitle="${pageTitle}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div id="materialsCalculators" class=" hidden-xs hidden-sm materialsCalculators">
	<div class="col-md-12 col-xs-12 flex padding0 matCalpart1">
		<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/voltage_drop_calculator.png">
		<div class="row overlay-bcontent col-md-offset-1 m-t-50">
			<div class="">
				<common:calculatoricon /><span class="matcalpart-text">How much should I get?</span>
			</div>
			<h1 class="matcal-heading">Voltage Drop Calculator</h1>
			<div class="col-md-4 padding0 matcal-desc">It's crucial to consider voltage drop when installing low-voltage
				outdoor lighting. Our calculator will help you determine what gauge of wire you should use for your project.
			</div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-md-10 col-md-offset-1 materialCalcSection">
		<div class="col-md-6 calc-left-section">
			<div>
				<div class="row input-heading-label">
					<label>Run Details</label>
				</div>
				<div class="row m-b-25 dropdown">
					<label class="input-label">Wire Gauge</label>
					<button class="col-md-12 select-option btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true" id="guage" value="">
						Select Wire Gauge
						<span class="glyphicon glyphicon-chevron-down"></span>
					</button>
					<ul id="guageListDropdown" class="col-md-12 dropdown-menu dropdown-menu-voltagedrop">
						<li><a data-value="0.3951">6 AWG</a></li>
						<li><a data-value="0.6282">8 AWG</a></li>
						<li><a data-value="0.9989">10 AWG</a></li>
						<li><a data-value="1.588">12 AWG</a></li>
						<li><a data-value="2.525">14 AWG</a></li>
						<li><a data-value="4.016">16 AWG</a></li>
						<li><a data-value="6.385">18 AWG</a></li>
						<li><a data-value="10.15">20 AWG</a></li>
					</ul>
				</div>
				<div class="row m-b-25 dropdown">
					<label class="input-label">Voltage Tap</label>
					<button class="col-md-12 select-option btn btn-default dropdown-toggle" type="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" id="voltageTap" value="">
						Select Voltage Tap
						<span class="glyphicon glyphicon-chevron-down"></span>
					</button>
					<ul id="voltageTapListDropdown" class="col-md-12 dropdown-menu dropdown-menu-voltagedrop">
						<li><a data-value="10">10 volts</a></li>
						<li><a data-value="11">11 volts</a></li>
						<li><a data-value="12">12 volts</a></li>
						<li><a data-value="13">13 volts</a></li>
						<li><a data-value="14">14 volts</a></li>
						<li><a data-value="15">15 volts</a></li>
						<li><a data-value="16">16 volts</a></li>
					</ul>
				</div>
				<div class="row m-b-25">
					<label class="input-label">Run Length (Feet)</label>
					<input type="text" class="col-md-12 calc-left-section-input" placeholder="Enter Run Length" id="lengthValue"
						data-suffix="feet"
						oninput="ACC.calculator.validateAndCalculate(this);ACC.calculator.voltageDropCalculator(this);"
						onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)"
						onfocus="ACC.calculator.handleSuffix(this, false)">
				</div>
				<div class="row m-b-25">
					<label class="input-label">Total VA</label>
					<input type="text" class="col-md-12 calc-left-section-input" placeholder="Enter Total VA" id="totalVA"
						data-suffix="volt-amps" oninput="ACC.calculator.validateAndCalculate(this);ACC.calculator.voltageDropCalculator(this);"
						onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)"
						onfocus="ACC.calculator.handleSuffix(this, false)">
				</div>
			</div>
			<div class="triangle-right"></div>
		</div>
		<div class="col-md-6 calc-right-section">
			<div class="row font-Arial output-section">
				<div class="output-heading-label">Voltage<br />Drop</div>
				<div class="f-w-400 f-s-60 text-align-right output-1 voltageDropVal">0.00v</div>
				<div class="f-w-400 f-s-40 text-align-right voltageDropPctVal">0.00%</div>
			</div>
			<div class="row font-Arial output-section">
				<div class="output-heading-label">Voltage at<br />last fixture</div>
				<div class="f-w-400 f-s-60 text-align-right output-2 voltageAtLastFixtureVal">0.00v</div>
				<div class="status-section">
					<div class="f-w-400 f-s-24 flex-center good hidden">
						<common:checkmarkIcon width="25" height="25" iconColor="#78a22f" /><span class="m-l-5">Good!</span>
					</div>
					<div class="f-w-400 f-s-24 flex-center ok hidden">
						<common:checkmarkIcon width="25" height="25" iconColor="#EF8700" /><span class="m-l-5">OK</span>
					</div>
					<div class="f-w-400 f-s-24 flex-center low hidden">
						<common:exclamatoryIcon width="25" height="25" iconColor="#E40101" /><span class="m-l-5">Voltage is too low!</span>
					</div>
				</div>
			</div>
			<div class="row font-Arial low-voltage-msg hidden">
				We recommend you use a larger<br/> wire gauge or a higher voltage tap.
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1">
		<div class="chart-container top--80 hidden">
			<h2 class="voltagedrop-heading text-align-center f-s-32 text-default">Wire Gauge Comparison</h2>
			<h3 class="font-Geogrotesque text-align-center m-t-0 m-b-25 text-gray-1">Voltage at the Last Fixture</h3>
			<div class="chart-area">
				<canvas id="wireGuageComparisonChart" class="voltageDropBarChartDesk"></canvas>
			</div>
			<p class="text-align-center text-gray-1 m-b-0 f-w-400 h5">The voltage at the last fixture should be at least 10V.</p>
			<p class="text-align-center m-t-5 h6">We recommend at least 10.5V.</p>
		</div>
		<div class="row">
			<div class="col-md-6">
				<h2 class="voltagedrop-heading m-b-25 text-align-center f-s-32 text-default">How did we calculate this?</h2>
				<p>We used the standard voltage drop formula, where the drop is calculated by considering the wire size and material, the current of the circuit, the voltage of the transformer, and the length of the wire run. We then compare your selected wire gauge to the four closest wire gauges to determine the best wire for your project.</p>
				<h2 class="formula-lbl m-t-25 m-b-25 text-default h1">Vd = (2 x K x I x L) / C</h2>
				<div>
					Where:
					<ul class="m-l-15">
						<li><b>Vd</b> = Voltage Drop (in volts).</li>
						<li><b>K</b> = Resistance of the wire material and gauge per 1,000 feet.</li>
						<li><b>I</b> = Current (in amps), which is Total VA divided by the Transformer Voltage (I = VA / Vt).</li>
						<li><b>L</b> = Length of the wire run (in feet).</li>
						<li><b>C</b> = Circular mils (CM), a measure of wire cross-sectional area.</li>
					</ul>
					All of these calculations are based on copper wire.
				</div>
			</div>
			<div class="col-md-6">
				<h2 class="voltagedrop-heading m-b-25 text-align-center f-s-32 text-default h1">Terminology</h2>
				<div>
					<div class="m-b-25">
						<h3 class="f-s-18 f-w-700 text-gray-1">Transformer Voltage:</h3>
						<p>This is the voltage supplied by the transformer to power the lighting system. For low-voltage outdoor lighting, the standard transformer voltage is typically 12 volts.</p>
					</div>
					<div class="m-b-25">
						<h3 class="f-s-18 f-w-700 text-gray-1">Wire Gauge:</h3>
						<p>The wire gauge refers to the thickness of the wire. Thicker wires have lower resistance and thus experience less voltage drop. Common wire gauges for outdoor lighting range from 10 AWG to 16 AWG.</p>
					</div>
					<div class="m-b-25">
						<h3 class="f-s-18 f-w-700 text-gray-1">Total VA (Volt-Amps):</h3>
						<p>Total VA represents the total power consumed by all the lighting fixtures in the system. It's calculated by summing the VA ratings of the individual fixtures.</p>
					</div>
					<div class="m-b-25">
						<h3 class="f-s-18 f-w-700 text-gray-1">Length:</h3>
						<p>This refers to the total length of the wire run from the transformer to the farthest fixture and back. Longer wire runs result in higher voltage drop.</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row m-t-100 m-b-25">
			<div class="col-md-6 outdoor-lighting-section">
				<h2>Outdoor Lighting<br>Solutions</h2>
				<p class="m-t-25">SiteOne is your one stop shop for <br>everything outdoor lighting.</p>
				<a class="btn btn-primary m-t-25" href="<c:url value="/lighting/c/sh11"/>">Shop Lighting</a>
			</div>
			<div class="col-md-6 p-l-0 p-r-0 outdoor-lighting-image-section">
				<img src="/_ui/responsive/theme-lambda/images/outdoor_lighting.png" alt="">
			</div>
		</div>
		<div class="row m-t-60 m-b-25">
			<h2 class="voltagedrop-heading padding-30 text-align-center f-s-32 text-default">Learn more about landscape lighting with SiteOne University</h2>
			<div class="row">
				<div class="product-item col-md-4 article-box">
					<div class="product-item-box">
						<div class="details">
							<img src="/_ui/responsive/theme-lambda/images/voltagedrop_learnmore1.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h3 class="store-specialty-heading article-heading two-line-text" title="Why You Should Use a Landscape Lighting Demo Kit">Why You Should Use a Landscape Lighting Demo Kit</h3>
								<div class="four-line-text" title="Outdoor lighting opens a different revenue stream and can be an easy way to upsell. Read more on the benefits of conducting outdoor lighting demos with SiteOne.">
									Outdoor lighting opens a different revenue stream and can be an easy way to upsell. Read more on the benefits of conducting outdoor lighting demos with SiteOne.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="<c:url value="/articles/article/Why-You-Should-Use-a-Landscape-Lighting-Demo-Kit"/>">Learn More</a>
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
							<img src="/_ui/responsive/theme-lambda/images/voltagedrop_learnmore2.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h3 class="store-specialty-heading article-heading two-line-text" title="The Magic of Landscape Lighting and Outdoor Audio">The Magic of Landscape Lighting and Outdoor Audio</h3>
								<div class="four-line-text" title="Landscape lighting and outdoor audio installations are a growing trend, and for good reason.">
									Landscape lighting and outdoor audio installations are a growing trend, and for good reason.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="<c:url value="/articles/article/Add-Landscape-Lighting-And-Outdoor-Audio-As-Additional-Service"/>">Learn More</a>
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
							<img src="/_ui/responsive/theme-lambda/images/voltagedrop_learnmore3.png" alt="alt">
							<div class="cl"></div>
							<div class="product-item-detail">
								<h3 class="store-specialty-heading article-heading two-line-text" title="Enhancing Water Features with Landscape Lighting">Enhancing Water Features with Landscape Lighting</h3>
								<div class="four-line-text" title="Waterfalls, pools, and creeks can become enchanting outdoor scenes with the addition of outdoor lighting.">
									Waterfalls, pools, and creeks can become enchanting outdoor scenes with the addition of outdoor lighting.
									<div class="cl"></div>
								</div>
								<div class="col-md-8 article-learn-more-btn">
									<a class="btn btn-primary btn-block col-md-12 col-xs-12 col-sm-12" href="<c:url value="/articles/article/Essential-Tools-for-Landscape-Lighting-Installation"/>">Learn More</a>
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

<div class="row mobile-materialCal-wrapper hidden-md hidden-lg">
	<h1 class="sr-only">Voltage Drop Calculator</h1>
	<div class="col-xs-12 m-b-15">
		<div class="mob-squarefeet-wrapper">
			<div class="flex">
				<div class="col-xs-4 mob-squarefeet-text">Voltage Drop</div>
				<div class="col-xs-8 mob-output-total text-align-right voltageDropVal"></div>
			</div>
			<div class="mob-voltagedropPct text-align-right voltageDropPctVal font-Arial"></div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 m-b-25">
		<div class="flex mob-range-wrapper">
			<div class="col-xs-5 mob-range-text">Voltage at last fixture</div>
			<div class="col-xs-7 mob-output-total text-align-right voltageAtLastFixtureVal"></div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 m-b-5 flex">
		<label for="guage" class="col-xs-3 mob-voltagedrop margin0">Gauge</label>
		<select class="col-xs-9 mob-calc-unitSelect" id="guage" name="guage" onchange="ACC.calculator.voltageDropCalculator()">
			<option selected disabled>Select wire gauge</option>
			<option value="0.3951">6 AWG</option>
			<option value="0.6282">8 AWG</option>
			<option value="0.9989">10 AWG</option>
			<option value="1.588">12 AWG</option>
			<option value="2.525">14 AWG</option>
			<option value="4.016">16 AWG</option>
			<option value="6.385">18 AWG</option>
			<option value="10.15">20 AWG</option>
		</select>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 m-b-5 flex">
		<label for="voltageTap" class="col-xs-3 mob-voltagedrop margin0">Voltage Tap</label>
		<select class="col-xs-9 mob-calc-unitSelect" id="voltageTap" name="voltageTap" onchange="ACC.calculator.voltageDropCalculator()">
			<option selected disabled>Select voltage tap</option>
			<option value="10">10 volts</option>
			<option value="11">11 volts</option>
			<option value="12">12 volts</option>
			<option value="13">13 volts</option>
			<option value="14">14 volts</option>
			<option value="15">15 volts</option>
			<option value="16">16 volts</option>
		</select>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 m-b-5 flex">
		<label for="lengthValue" class="col-xs-3 mob-voltagedrop margin0">Length</label>
		<input type="text" class="col-xs-9 mob-input-voltagedrop" id="lengthValue" placeholder="Enter length in feet"
			data-suffix="feet"
			oninput="ACC.calculator.validateAndCalculate(this);ACC.calculator.voltageDropCalculator(this);"
			onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)"
			onfocus="ACC.calculator.handleSuffix(this, false)">
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 m-b-5 flex">
		<label for="totalVA" class="col-xs-3 mob-voltagedrop margin0">Total VA</label>
		<input type="text" class="col-xs-9 mob-input-voltagedrop" id="totalVA" placeholder="Enter total VA"
			data-suffix="VA"
			oninput="ACC.calculator.validateAndCalculate(this);ACC.calculator.voltageDropCalculator(this);"
			onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)"
			onfocus="ACC.calculator.handleSuffix(this, false)">
	</div>
	<div class="cl"></div>
	<div class="chart-container hidden">
		<h2 class="voltagedrop-heading text-align-center f-s-20 text-default">Wire Gauge Comparison</h2>
		<h3 class="font-Geogrotesque font-size-14 text-align-center m-t-0 m-b-25">Voltage at the Last Fixture</h3>
		<div class="chart-area">
			<canvas id="wireGuageComparisonChart" class="voltageDropBarChartMob"></canvas>
		</div>
		<p class="text-align-center font-size-14 text-gray-1 m-b-0-xs f-w-400 h5">The voltage at the last fixture should be at least 10V.</p>
		<p class="text-align-center f-s-12 m-t-5-xs h6">We recommend at least 10.5V.</p>
		<div class="p-x-15-xs m-y-25-xs">
			<div class="flex-center m-b-25">
				<common:exclamatoryIcon width="25" height="25" iconColor="#EF8700" />
				<div class="m-l-10">
					<div class="voltagedrop-recommend">Voltage drop should not exceed 5%.</div>
					<div class="f-s-12">We recommend no more than 3%.</div>
				</div>
			</div>
			<div class="flex-center m-b-15">
				<common:checkmarkIcon width="25" height="25" iconColor="#78a22f" />
				<div class="m-l-10">
					<div class="voltagedrop-recommend">Use thicker wire for longer distances.</div>
					<div class="f-s-12">Minimize voltage drop for peak performance.</div>
				</div>
			</div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 accordion-section m-t-15">
		<div class="accordion-header" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true">
			<h2 class="voltagedrop-heading m-b-15 f-s-20 text-default">Terminology</h2>
			<span class="glyphicon glyphicon-chevron-up"></span>
			<span class="glyphicon glyphicon-chevron-down"></span>
		</div>
		<div id="collapseOne" class="collapse in accordion-content">
			<div class="m-b-25 m-t-25">
				<h3 class="f-s-12 f-w-700 text-gray-1">Wire Gauge (AWG)</h3>
				<p class="f-s-12">The wire gauge refers to the thickness of the wire. Thicker wires have lower resistance and thus
					experience less voltage drop.</p>
			</div>
			<div class="m-b-25">
				<h3 class="f-s-12 f-w-700 text-gray-1">Tap - Transformer Voltage (Vt)</h3>
				<p class="f-s-12">This is the voltage supplied by the transformer to power the lighting system. For low-voltage outdoor
					lighting, the standard transformer voltage is typically 12 volts (12V).</p>
			</div>
			<div class="m-b-25">
				<h3 class="f-s-12 f-w-700 text-gray-1">Total Volt-Amps (VA)</h3>
				<p class="f-s-12">Total VA represents the total power consumed by all the lighting fixtures in the system. It’s
					calculated by summing the VA ratings of all the individual fixtures.</p>
			</div>
			<div class="m-b-25">
				<h3 class="f-s-12 f-w-700 text-gray-1">Circuit Length</h3>
				<p class="f-s-12">This refers to the total length of the wire run from the transformer to the farthest fixture and back.
					Longer wire runs result in higher voltage drop.</p>
			</div>
		</div>
	</div>
	<div class="col-xs-12 accordion-section m-b-25">
		<div class="accordion-header" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true">
			<h2 class="voltagedrop-heading m-b-15 f-s-20 text-default">How did we calculate this?</h2>
			<span class="glyphicon glyphicon-chevron-up"></span>
			<span class="glyphicon glyphicon-chevron-down"></span>
		</div>
		<div id="collapseTwo" class="collapse in accordion-content">
			<p class="f-s-12">We used the standard voltage drop formula, where the drop is calculated by considering the wire size
				and material, the current of the circuit, the voltage of the transformer, and the length of the wire run. We then
				compare your selected wire gauge to the four closest wire gauges to determine the best wire for your project.</p>
			<h2 class="formula-lbl m-t-25 m-b-25 f-s-24 text-default h1">Vd = (2 x R x I x L) / C</h2>
			<div class="m-b-25 f-s-12">
				Where:
				<ul class="m-l-15">
					<li><b>Vd</b> = Voltage Drop (in volts).</li>
					<li><b>R</b> = Resistance of the wire material and gauge per 1,000 feet.</li>
					<li><b>I</b> = Current (in amps), which is Total VA divided by the Transformer Voltage (I = VA / Vt).
					</li>
					<li><b>L</b> = Length of the wire run (in feet).</li>
					<li><b>C</b> = Circular mils (CM), a measure of wire cross-sectional area.</li>
				</ul>
				All of these calculations are based on copper wire.
			</div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 promotions-section">
		<img src="/_ui/responsive/theme-lambda/images/voltagedrop_learnmore4.png" alt="alt">
		<div class="overlay">
			<h2 class="f-s-18 f-w-700 m-b-0-xs h4">Shop Light Fixtures</h2>
			<h3 class="m-t-5-xs h6">Shine a new light on<br />your landscape.</h3>
		</div>
		<div class="button-wrapper">
			<a class="btn btn-primary" href="<c:url value="/lighting/c/sh11"/>">Shop</a>
		</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 promotions-section">
		<img src="/_ui/responsive/theme-lambda/images/voltagedrop_learnmore3.png" alt="alt">
		<div class="overlay">
			<h2 class="f-s-18 f-w-700 m-b-0-xs h4">Learn & Explore</h2>
			<h3 class="m-t-5-xs h6">Learn more about<br />outdoor lighting.</h3>
		</div>
		<div class="button-wrapper">
			<a class="btn btn-primary" href="<c:url value="/articles/lighting"/>">Learn More</a>
		</div>
	</div>
	<!-- <div class="cl"></div>
	<div class="col-xs-12 m-t-50 text-align-center">
		<div class="f-s-12 f-w-400 m-b-15">What do you think of the new Voltage Drop Calculator?</div>
		<div class="feedback-icon-section">
			<common:thumbsup />
			<common:thumbsup />
		</div>
	</div> -->
</div>
</template:page>