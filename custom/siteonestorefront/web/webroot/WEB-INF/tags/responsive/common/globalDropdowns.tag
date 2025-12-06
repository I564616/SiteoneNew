<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="type" required="true" rtexprvalue="true" %>
<%@ attribute name="title" required="true" rtexprvalue="true" %>

<div id="thicknessDropdownComponent">
    <c:choose>
    <c:when test="${type == 'stoneWallCalc'}">
            <label for="height" style="width: inherit !important;" class="floating-label col-md-4 control-label margin0">
                ${title}
            </label>
            <div class="dropdown new-dropdown">
                <button class="thicknessDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="1">
                    1 Foot Boulders, 1 Foot
                    Thick Wall
                    <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0" width="15"
                            height="9">
                            <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                        </mask>
                        <g mask="url(#mask0_2_117)">
                            <path
                                d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                fill="#414244" />
                        </g>
                    </svg>
                </button>
                <ul id="tableMenu" class="thicknessDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                    <li data-value='1' class="thicknessDropdownOption selected">
                        1 Foot Boulders, 1
                        Foot Thick Wall
                    </li>
                    <li data-value='2' class="thicknessDropdownOption">
                        1-2 Foot Boulders, 2
                        Foot Thick Wall</li>
                    <li data-value='0.666' class="thicknessDropdownOption">
                        5"-12" Rip-Rap, 8
                        Inch Thick Wall</li>
                </ul>
            </div>
            <input number id="value" readonly="true" value="1" hidden="true" />  
        </c:when>
        <c:when test="${type == 'flagStoneCalc'}">
            <label for="height" class="floating-label col-md-4 control-label margin0">Flagstone
                Thickness</label>
            <div class="dropdown new-dropdown">
                <button class="thicknessDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="1">
                    1" Thick
                    <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0" width="15"
                            height="9">
                            <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                        </mask>
                        <g mask="url(#mask0_2_117)">
                            <path
                                d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                fill="#414244" />
                        </g>
                    </svg>
                </button>
                <ul id="tableMenu" class="thicknessDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                    <li data-value='1' class="thicknessDropdownOption selected"> 1" Thick</li>
                    <li data-value='1.5' class="thicknessDropdownOption">1.5" Thick</li>
                    <li data-value='2' class="thicknessDropdownOption">2" Thick</li>
                    <li data-value='2.5' class="thicknessDropdownOption">2.5" Thick</li>
                </ul>
            </div>
            <input number id="value" readonly="true" value="1" hidden="true" />
        </c:when>
        <c:when test="${type == 'topSoilCalc'}">
            <div class="col-md-12 col-xs-12 flex-center padding0 matCalpart1">
                <img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/topSoil_calculator.png">
                <div class="row overlay-bcontent col-md-offset-1">
                    <div class="">
                        <common:calculatoricon /><span class="matcalpart-text">How much should I
                            get?</span>
                    </div>
                    <h1 class="matcal-heading">{title}</h1>
                    <div class="col-md-6 padding0 matcal-desc">This calculator will help you figure out
                        how much product you need to complete your project.</div>
                </div>
            </div>
            <div class="cl"></div>
            <div id="flagStoneCalc">
                <div id="columnOne">
                    <div id="thicknessDropdownComponent">
                        <label for="height" class="floating-label col-md-4 control-label margin0">
                            Type of Soil</label>
                        <div class="dropdown new-dropdown">
                            <button class="thicknessDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="410">
                                AA Topsoil
                                <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0"
                                        width="15" height="9">
                                        <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                                    </mask>
                                    <g mask="url(#mask0_2_117)">
                                        <path
                                            d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                            fill="#414244" />
                                    </g>
                                </svg>
                            </button>
                            <ul id="tableMenu" class="thicknessDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                                <li data-value'2150' class="thicknessDropdownOption selected"> AA Topsoil </li>
                                <li data-value'2150' class="thicknessDropdownOption"> A Topsoil</li>
                                <li data-value'2040' class="thicknessDropdownOption"> Top/Comp 3-1 </li>
                                <li data-value'2040' class="thicknessDropdownOption"> Top/Cow 3-1 </li>
                                <li data-value'1900' class="thicknessDropdownOption"> Tri Mix III </li>
                                <li data-value'1150' class="thicknessDropdownOption"> Planter's Mix
                                </li>
                                <li data-value'2040' class="thicknessDropdownOption"> Planting Bed Mix
                                </li>
                                <li data-value'750'> class="thicknessDropdownOption" Aged / Aspen Humus
                                </li>
                                <li data-value'1300' class="thicknessDropdownOption"> Cow Manure </li>
                                <li data-value'1300' class="thicknessDropdownOption"> Compost (Biocomp)
                                </li>
                            </ul>
                        </div>
                        <input number id="value" readonly="true" value="1" hidden="true" />
                    </div>
                    <div class="flex m-b-25" style="margin-bottom: 4rem;">
                        <label for="height" class="col-md-4 control-label margin0">Height</label>
                        <input type="number" class="col-md-8 unitInput" id="height" required="" min="0" data-metric="feet"
                            value="1">
                        <div class="dropdown padding0 flex">
                            <button class="col-md-12 heightUnitDropdown unitDropdown btn btn-default dropdown-toggle" type="button"
                                id="heightUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" value="in">
                                in
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                            <button class="col-md-12 widthUnitDropdown unitDropdown btn btn-default dropdown-toggle active"
                                type="button" id="widthUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"
                                value="feet">
                                ft
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                        </div>
                    </div>
                    <div class="flex m-b-25" style="margin-bottom: 4rem;">
                        <label for="width" class="col-md-4 control-label margin0">Width</label>
                        <input type="number" class="col-md-8 unitInput" id="width" required="" min="0" data-metric="feet" value="1">
                        <div class="dropdown padding0 flex">
                            <button class="col-md-12 widthUnitDropdown unitDropdown btn btn-default dropdown-toggle" type="button"
                                id="widthUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" value="in">
                                in
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                            <button class="col-md-12 widthUnitDropdown unitDropdown btn btn-default dropdown-toggle active"
                                type="button" id="widthUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"
                                value="feet">
                                ft
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                        </div>
                    </div>
                    <div class="flex m-b-25">
                        <label for="depth" class="col-md-4 control-label margin0">Depth</label>
                        <input type="number" class="col-md-8 unitInput" id="depth" required="" min="0" data-metric="in"
                            value="0.25">
                        <div class="dropdown padding0 flex">
                            <button class="col-md-12 depthUnitDropdown unitDropdown btn btn-default dropdown-toggle active"
                                type="button" id="widthUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"
                                value="in">
                                in
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                            <button class="col-md-12 depthUnitDropdown unitDropdown btn btn-default dropdown-toggle" type="button"
                                id="widthUnit" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" value="feet">
                                ft
                                <span class="glyphicon glyphicon-chevron-down"></span>
                            </button>
                        </div>
                    </div>
                </div>
                <div id="columnTwo" class="flex-center calc-wrapper">
                    <div id="areaDropdownComponent">
                        <div class="dropdown new-dropdown">
                            <button class="areaDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="sqft">
                                SQUARE FEET
                                <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0"
                                        width="15" height="9">
                                        <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                                    </mask>
                                    <g mask="url(#mask0_2_117)">
                                        <path
                                            d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                            fill="#414244" />
                                    </g>
                                </svg>
                            </button>
                            <ul id="tableMenu" class="areaDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                                <li data-value='sqft' class="areaDropdownOption selected">SQUARE FEET
                                </li>
                                <li data-value='sqin' class="areaDropdownOption">SQUARE INCHES</li>
                                <li data-value='sqyd' class="areaDropdownOption">SQUARE YARDS</li>
                            </ul>
                        </div>
                        <input number id="value" readonly="true" value="1" />
                    </div>
                    <div id="volumeDropdownComponent">
                        <div class="dropdown new-dropdown">
                            <button class="volumeDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="cbft">
                                CUBIC FEET
                                <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0"
                                        width="15" height="9">
                                        <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                                    </mask>
                                    <g mask="url(#mask0_2_117)">
                                        <path
                                            d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                            fill="#414244" />
                                    </g>
                                </svg>
                            </button>
                            <ul id="tableMenu" class="volumeDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                                <li data-value='cbft' class="volumeDropdownOption selected">CUBIC FEET
                                </li>
                                <li data-value='cbyd' class="volumeDropdownOption">CUBIC YARDS</li>
                            </ul>
                        </div>
                        <input number id="value" readonly="true" value="1728" />
                    </div>
                    <div id="weightDropdownComponent">
                        <div class="dropdown new-dropdown">
                            <button class="weightDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="pnds">
                                POUNDS
                                <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0"
                                        width="15" height="9">
                                        <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                                    </mask>
                                    <g mask="url(#mask0_2_117)">
                                        <path
                                            d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                            fill="#414244" />
                                    </g>
                                </svg>
                            </button>
                            <ul id="tableMenu" class="weightDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                                <li data-value='pnds' class="weightDropdownOption selected">POUNDS</li>
                                <li data-value='tns' class="weightDropdownOption">TONS</li>
                            </ul>
                        </div>
                        <input number id="value" readonly="true" value="0.04" />
                    </div>
                </div>
                <div id="disclaimer">
                    <p>
                        Calculations are estimates and do not account for compaction. Changes in
                        landscape elevation and the
                        amount
                        of moisture
                        present may affect coverage and the weight of the material.
                    </p>
                </div>
            </div>
        </c:when>
        <c:when test="${type == 'barkMulchCalc'}">
            <label for="height" class="floating-label col-md-4 control-label margin0">
                Type of Mulch</label>
            <div class="dropdown new-dropdown">
                <button class="thicknessDropdownButton btn btn-default dropdown-toggle" type="button" id="select-option"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" data-value="410">
                    Average
                    <svg width="15" height="9" viewBox="0 0 15 9" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <mask id="mask0_2_117" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0" width="15"
                            height="9">
                            <path d="M15 6.55671e-07L0 0L-3.70585e-07 8.478L15 8.47801L15 6.55671e-07Z" fill="white" />
                        </mask>
                        <g mask="url(#mask0_2_117)">
                            <path
                                d="M7.50037 6.11674L13.3306 0.28656C13.514 0.103079 13.7629 6.01595e-07 14.0224 6.12937e-07C14.2818 6.24279e-07 14.5307 0.103079 14.7142 0.28656C14.8977 0.470042 15.0007 0.718896 15.0007 0.978377C15.0007 1.23786 14.8977 1.48671 14.7142 1.67019L8.19219 8.19219C8.10136 8.28309 7.99351 8.35519 7.87481 8.40439C7.7561 8.45358 7.62887 8.4789 7.50037 8.4789C7.37188 8.4789 7.24464 8.45358 7.12593 8.40439C7.00723 8.35519 6.89938 8.28309 6.80855 8.19219L0.28656 1.67019C0.103079 1.48671 -5.68426e-08 1.23786 -4.27662e-08 0.978376C-2.86898e-08 0.718895 0.103079 0.470041 0.28656 0.28656C0.470041 0.103079 0.718895 4.87168e-08 0.978376 5.7325e-08C1.23786 6.59332e-08 1.48671 0.103079 1.67019 0.28656L7.50037 6.11674Z"
                                fill="#414244" />
                        </g>
                    </svg>
                </button>
                <ul id="tableMenu" class="thicknessDropdownButtonContent col-md-12 dropdown-menu dropdown-menu-cal">
                    <li data-value='410' class="thicknessDropdownOption selected">Average</li>
                    <li data-value='480' class="thicknessDropdownOption">Cascade</li>
                    <li data-value='480' class="thicknessDropdownOption">Cedar</li>
                    <li data-value='460' class="thicknessDropdownOption">Coffee Metro</li>
                    <li data-value='420' class="thicknessDropdownOption">Red, Gold, Brown Metro</li>
                    <li data-value='280' class="thicknessDropdownOption">Playground Chips</li>
                </ul>
            </div>
            <input number id="value" readonly="true" value="1" hidden="true" />
        </c:when>
        <c:when test="next">
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</div>