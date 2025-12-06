<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<template:page pageTitle="${pageTitle}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div id="materialsCalculators" class=" hidden-xs hidden-sm materialsCalculators">
	<div class="col-md-12 col-xs-12 flex padding0 matCalpart1">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/flagstone.png" >
			<div class="row overlay-bcontent col-md-offset-1 m-t-50">
				<div class="">
				<common:calculatoricon />
				<span class="matcalpart-text" >How much should I get?</span></div>
				<h1 class="matcal-heading">flagstone patio/walkway</h1>
				<div class="col-md-4 padding0 matcal-desc">This calculator
					will help you figure out how much product you need to complete your
					project.</div>
			</div>
	</div>
	

 <div class="cl"></div>
  <div id="flagStoneCalc" class="col-md-10 col-md-offset-1">
    <div id="flagstone_left_section">
        <div class="row m-b-35 dropdown" style="margin-bottom: 35px">
        <label for="thickness" style="width: inherit !important;"
                class="col-md-4 control-label margin0">Flagstone Thickness</label>
         <button class="col-md-12 select-option btn btn-default dropdown-toggle" style="padding-left:24px" type="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true" id="thickness" value="1">1" Thick
			<span class="glyphicon glyphicon-chevron-down"></span>
        </button>
            <ul id="thicknessDropdown" class="col-md-12 dropdown-menu dropdown-menu-fs" >
                <li class="selected"><a data-value='1'>1" Thick</a></li>
                <li><a data-value='1.5'>1.5" Thick</a></li>
                <li><a data-value='2'>2" Thick</a></li>
                <li><a data-value='2.5'>2.5" Thick </a></li>
            </ul>
         </div>
	    <div id="projectCalcInput" class="flex m-b-25 inputField" style="margin-bottom: 4rem;">
            <label for="length" style="width: inherit !important;"
                class="col-md-4 control-label margin0">Length</label>
            <input type="text" class="col-md-8 length unitInput" autocomplete="off" placeholder="Enter length in feet" id="length" step="any" min="0"
                data-metric="feet" value="" data-dropdownmetric="squarefeet" data-suffix="feet" data-dropdownchange="false" oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.CalculateFlagstone('');" onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
            <div id="projectCalcBtnGroup" class="dropdown padding0 flex">
                <button class="toggle-unit" type="button" data-metric="length" data-previous="feet"
                    data-suffix="inches" data-unit="inches" > in</button>
                <button class="toggle-unit active" type="button" data-metric="length" data-previous="inches"
                    data-suffix="feet" data-unit="feet" > ft </button>
            </div>
        </div>
        <div id="projectCalcInput" class="flex m-b-25 inputField" style="margin-bottom: 4rem;">
            <label for="width" style="width: inherit !important;"
                class="col-md-4 control-label margin0">Width</label>
            <input type="text" class="col-md-8 width unitInput" autocomplete="off" placeholder="Enter width in feet" id="width" step="any" min="0"
                data-metric="feet" value="" data-dropdownmetric="cubicfeet" data-suffix="feet" data-dropdownchange="false" oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.CalculateFlagstone('');" onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
            <div id="projectCalcBtnGroup" class="dropdown padding0 flex">
                <button class="toggle-unit" type="button" data-metric="width" data-previous="feet"
                    data-suffix="inches" data-unit="inches"> in </button>
                <button class="toggle-unit active" type="button" data-metric="width" data-previous="inches"
                    data-suffix="feet" data-unit="feet"> ft </button>
            </div>
        </div>
</div>
		<div id="flagstone_right_section">
        <div id="areaDropdown">
            <div class="dropdown new-dropdown">
                <button class="areaDropdownBtn btn btn-default dropdown-toggle text-uppercase" type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="sqft" >
					 Square Feet
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="Menuoptionarea" class="dropdown-menu dropdown-menu-cal" >
					<li class="selected"><span class=""><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="areaDropdownOption" data-value="sqft" onClick="ACC.calculator.CalculateFlagstone('sqft');">Square Feet</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="areaDropdownOption m-l-15" data-value="sqin" onClick="ACC.calculator.CalculateFlagstone('sqin');">Square Inches</a></li>
                    <li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="areaDropdownOption m-l-15" data-value="sqyd" onClick="ACC.calculator.CalculateFlagstone('sqyd');">Square Yards</a></li>
				</ul>
            </div>
            <div  id="value"></div>
        </div>
        <div id="volumeDropdown">
            <div class="dropdown new-dropdown">
                <button class="volumeDropdownBtn btn btn-default dropdown-toggle text-uppercase" type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"  data-value="cbft">
					CUBIC FEET
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="MenuoptionVolume" class="dropdown-menu dropdown-menu-cal" >
                   <li class="selected"><span class=""><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="volumeDropdownOption m-l-15" data-value='cbft' onClick="ACC.calculator.CalculateFlagstone('cbft');">Cubic Feet</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="volumeDropdownOption" data-value='cbyd' onClick="ACC.calculator.CalculateFlagstone('cbyd');">Cubic Yards</a></li>
		
				</ul>
            </div>
            <div  id="value"></div>
        </div>
        <div id="weightDropdown">
            <div class="dropdown new-dropdown">
                   <button class="weightDropdownBtn btn btn-default dropdown-toggle text-uppercase " type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"  data-value="pnds">
					POUNDS
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="Menuoptionweight" class="dropdown-menu dropdown-menu-cal" >
                    <li class="selected"><span class=""><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="weightDropdownOption" data-value='pnds' onClick="ACC.calculator.CalculateFlagstone('pnds');">Pounds</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown height="14" width="14"></common:Calculatorselectdropdown></span><a class="weightDropdownOption m-l-15" data-value='tns' onClick="ACC.calculator.CalculateFlagstone('tns');">Tons</a></li>

				</ul>
            </div>
           <div  id="value"></div>
        </div>
		</div>
   
    <div class="pscal">
        <p>
            Calculations are estimates and
            do not account for compaction.
            Changes in
            landscape elevation and the
            amount
            of moisture
            present may affect coverage and
            the weight of the material.
        </p>
    </div>
</div>
</div>
 </template:page>