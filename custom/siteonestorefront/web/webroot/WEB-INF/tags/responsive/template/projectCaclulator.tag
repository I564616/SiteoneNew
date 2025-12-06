<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="type" required="true" rtexprvalue="true" %>
<%@ attribute name="title" required="false" rtexprvalue="true" %>

<div id="${type}" class="col-md-10 col-md-offset-1">
    <div id="columnOnerd">

        <div id="projectCalcInput" class="flex m-b-25 inputField" style="margin-bottom: 4rem;">
            <label for="height" style="width: inherit !important;"
                class="col-md-4 control-label margin0">Length</label>
            <input type="text" class="col-md-8 length height unitInput" autocomplete="off" placeholder="Enter length in feet" id="height" step="any" min="0"
                data-metric="feet" value="" data-dropdownmetric="squarefeet" data-suffix="feet" data-dropdownchange="false" oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.dropdownToggleConvertion('');" onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
            <div id="projectCalcBtnGroup" class="dropdown padding0 flex">
                <button class="" type="button" data-metric="height" data-previous="feet"
                    data-suffix="in" data-unit="inches" > in</button>
                <button class="active" type="button" data-metric="height" data-previous="in"
                    data-suffix="feet" data-unit="feet" > ft </button>
            </div>
        </div>
        <div id="projectCalcInput" class="flex m-b-25 inputField" style="margin-bottom: 4rem;">
            <label for="width" style="width: inherit !important;"
                class="col-md-4 control-label margin0">Width</label>
            <input type="text" class="col-md-8 width unitInput" autocomplete="off" placeholder="Enter width in feet" id="width" step="any" min="0"
                data-metric="feet" value="" data-dropdownmetric="cubicfeet" data-suffix="feet" data-dropdownchange="false" oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.dropdownToggleConvertion('');" onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
            <div id="projectCalcBtnGroup" class="dropdown padding0 flex">
                <button class="" type="button" data-metric="width" data-previous="feet"
                    data-suffix="in" data-unit="inches"> in </button>
                <button class="active" type="button" data-metric="width" data-previous="in"
                    data-suffix="feet" data-unit="feet"> ft </button>
            </div>
        </div>
        <c:if
            test="${type == 'decorativeRockCalc' or type == 'topSoilCalc' or type == 'roadBasefillCalc' or type == 'barkMulchCalc'}">
            <div id="projectCalcInput" class="flex m-b-25 inputField">
                <label for="depth" style="width: inherit !important;"
                    class="col-md-4 control-label margin0">Depth</label>
                <input type="text" class="col-md-8 depth unitInput" autocomplete="off" placeholder="Enter depth in inches" id="depth" step="any" min="0"
                    data-metric="in" value="" data-dropdownmetric="pounds"  data-suffix="inches" data-dropdownchange="false" oninput="ACC.calculator.formatNumberInput(this);ACC.calculator.dropdownToggleConvertion('');" onkeydown="return ACC.calculator.restrictInput(event)" onblur="ACC.calculator.handleSuffix(this, true)" onfocus="ACC.calculator.handleSuffix(this, false)">
                <div id="projectCalcBtnGroup" class="dropdown padding0 flex">
                    <button class="active" type="button" data-metric="depth"
                        data-previous="feet" data-suffix="in" data-unit="inches" >
                        in
                    </button>
                    <button class="" type="button" data-metric="depth" data-previous="in"
                        data-suffix="feet" data-unit="feet">
                        ft
                    </button>
                </div>
            </div>
        </c:if>
    </div>
    <div id="columnTwo" class="flex-center calc-wrapper">
        <div id="areaDropdownComponent">
            <div class="dropdown new-dropdown">
                <button class="areaDropdownButton btn btn-default dropdown-toggle text-uppercase" type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="squarefeet" value="squarefeet">
					 Square Feet
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="tableMenuoptionarea" class="dropdown-menu dropdown-menu-cal" >
					<li class="selected"><span class=""><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="areaDropdownOption" data-value="squarefeet" onClick="ACC.calculator.dropdownToggleConvertion('squarefeet');">Square Feet</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="areaDropdownOption m-l-15" data-value="squareinches" onClick="ACC.calculator.dropdownToggleConvertion('squareinches');">Square Inches</a></li>
                    <li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="areaDropdownOption m-l-15" data-value="squareyards" onClick="ACC.calculator.dropdownToggleConvertion('squareyards');">Square Yards</a></li>
				</ul>
            </div>
            <div  id="value"></div>
        </div>
        <div id="volumeDropdownComponent">
            <div class="dropdown new-dropdown">
                <button class="volumeDropdownButton btn btn-default dropdown-toggle text-uppercase" type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"  data-value="cubicfeet" value="cubicfeet">
					CUBIC FEET
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="tableMenuoptionVolume" class="dropdown-menu dropdown-menu-cal" >
					<li class="selected"><span class=""><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="volumeDropdownOption" data-value='cubicfeet' onClick="ACC.calculator.dropdownToggleConvertion('cubicfeet');">Cubic Feet</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="volumeDropdownOption m-l-15" data-value='cubicyards' onClick="ACC.calculator.dropdownToggleConvertion('cubicyards');">Cubic Yards</a></li>
				</ul>
            </div>
            <div  id="value"></div>
        </div>
        <div id="weightDropdownComponent">
            <div class="dropdown new-dropdown">
                   <button class="weightDropdownButton btn btn-default dropdown-toggle text-uppercase " type="button" id="select-option"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"  data-value="pounds" value="pounds">
					POUNDS
					<span class="glyphicon glyphicon-chevron-down"></span>
				</button>
				<ul id="tableMenuoptionweight" class="dropdown-menu dropdown-menu-cal" >
					<li class="selected"><span class=""><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="weightDropdownOption" data-value='pounds' onClick="ACC.calculator.dropdownToggleConvertion('pounds');">Pounds</a></li>
					<li class="borderTop"><span class="hidden"><common:Calculatorselectdropdown></common:Calculatorselectdropdown></span><a class="weightDropdownOption m-l-15" data-value='tons' onClick="ACC.calculator.dropdownToggleConvertion('tons');">Tons</a></li>
				</ul>
            </div>
           <div  id="value"></div>
        </div>
    </div>
    <div id="disclaimer">
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