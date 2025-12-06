<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="iconName" required="false" type="java.lang.String" %>
<%@ attribute name="price" required="true" type="java.lang.String" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<c:set var="productPrice" value="${price}" />
<c:set var="object" value="${product}" />
<div id="calculator-overlay">
    <div id="show-bulk-calculator" data-price="${productPrice}" class="pdp-bulk-sidebar">
        <div class="header">
            <div class="icon-container">
                <span id="closeBulkCalculator"></span>
            </div>
            <div class="content-container">
                <common:globalBulkCalcIcons iconName="largeCalculatorIcon" />
                <h2 class='text-center'>Bulk Material Calculator</h2>
            </div>
        </div>
        <div class="content">
            <div id="calculatorMenu">
                <div class="button-group">
                    <span>Calculate by measurements or square feet?</span>
                    <label class="m-b-0 text-capitalize">
                        <input type="radio" id="measurements" name="calculate"
                            checked>
                        <span>Measurements</span>
                    </label>
                    <label class="m-b-0 text-capitalize">
                        <input type="radio" id="sqft" name="calculate">
                        <span>Square Feet</span>
                    </label>
                </div>
            </div>
            <div id="calculatorButtons">
                <common:globalBulkCalcIcons iconName="rectangleButton" />
                <common:globalBulkCalcIcons iconName="circleButton" />
                <common:globalBulkCalcIcons iconName="rightTriangleButton" />
                <common:globalBulkCalcIcons iconName="trapezoidButton" />
                <common:globalBulkCalcIcons iconName="squareButton" />
                <common:globalBulkCalcIcons iconName="ovalButton" />
                <common:globalBulkCalcIcons iconName="eqTriangleButton" />
                <common:globalBulkCalcIcons iconName="parallelogramButton" />
            </div>
            <div id="calculatorImages">
                <common:globalBulkCalcIcons iconName="rectangleImage" />
                <common:globalBulkCalcIcons iconName="circleImage" />
                <common:globalBulkCalcIcons iconName="rightTriangleImage" />
                <common:globalBulkCalcIcons iconName="trapezoidImage" />
                <common:globalBulkCalcIcons iconName="squareImage" />
                <common:globalBulkCalcIcons iconName="ovalImage" />
                <common:globalBulkCalcIcons iconName="eqTriangleImage" />
                <common:globalBulkCalcIcons iconName="parallelogramImage" />
            </div>
            <div id="calculatorInputs">
                <div id="measurementsGroup" class="input-container group">
                    <p>What are the dimensions of your area?</p>
                    <div id="lengthInput" class="input active">
                        <label for="lengthValue">Length</label>
                        <input type="text" id="lengthValue" name="lengthValue" placeholder="Enter length" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="widthInput" class="input active">
                        <label>Width</label>
                        <input type="text" placeholder="Enter width" id="widthValue" name="widthValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="sideInput" class="input">
                        <label>Side</label>
                        <input type="text" placeholder="Enter side length" id="sideValue" name="sideValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="diameterInput" class="input">
                        <label>Diameter</label>
                        <input type="text" placeholder="Enter length" id="diameterValue" name="diameterValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="sideA" class="input">
                        <label>Side A</label>
                        <input type="text" placeholder="Enter length" id="sideAValue" name="sideAValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="sideB" class="input">
                        <label>Side B</label>
                        <input type="text" placeholder="Enter width" id="sideBValue" name="sideBValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                    <div id="heightInput" class="input">
                        <label>Height</label>
                        <input type="text" placeholder="Enter height" id="heightValue" name="heightValue" data-suffix="feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button><div>in</div></button>
                            <button class="selected"><div>ft</div></button>
                        </div>
                    </div>
                </div>
                <div id="sqftGroup" class="input input-container group sqft">
                    <p>How many square feet?</p>
                    <div id="areaInput" class="input always-active">
                        <label for="areaValue">Area</label>
                        <input type="text" id="areaValue" name="areaValue" placeholder="Enter square feet" data-suffix="square feet"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">square feet</span>
                        <div class="buttons"></div>
                    </div>
                </div>
                <div class="input-container">
                    <p>How deep do you want your fill?</p>
                    <div id="depthInput" class="input always-active">
                        <label>Depth</label>
                        <input type="text" placeholder="Enter depth" id="depthValue" name="depthValue" data-suffix="inches"
                            oninput="ACC.calculator.formatNumberInput(this);" onkeydown="return ACC.calculator.restrictInput(event)" />
                        <span class="metricLabel">feet</span>
                        <div class="buttons">
                            <button class="selected"><div>in</div></button>
                            <button class=""><div>ft</div></button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="calculatorWaste">
                <div id="pdpBulkAddWasteCost" class="form-group">
                    <input type="checkbox" id="html" checked="true" type="number" min="0">
                    <label for="html"></label>
                </div>
                <div id="tooltip">
                    We recommend ordering an additional 10% for a margin of error.
                    It's always better to have a little extra than not enough.
                </div>
                <p>Include an extra 10% for waste or extra</p>
                <common:globalBulkCalcIcons iconName="infoIcon" />
            </div>
            <div id="calculatorDisplay">
                <div>
                    <div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">Calculated Sub Total:</span>
                            </div>
                            <div class="result">
                                <span id="subTotalValue" class="ellipsis" ></span>
                                <label>cubic yards</label>
                            </div>
                        </div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">
                                    <span>10%</span> Extra:
                                </span>
                            </div>
                            <div class="result">
                                <span id="extraWasteValue" class="ellipsis" ></span>
                                <label>cubic yards</label>
                            </div>
                        </div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">Total:</span>
                            </div>
                            <div class="result">
                                <span id="totalCubicYards" class="ellipsis"></span>
                                <label>cubic yards</label>
                            </div>
                        </div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">Rounds To:</span>
                            </div>
                            <div class="result">
                                <span id="roundsToValue" class="ellipsis"></span>
                                <label>cubic yards</label>
                            </div>
                        </div>
                        <div class="divider"></div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">Area:</span>
                            </div>
                            <div class="result">
                                <span id="area" class="ellipsis"></span>
                                <label>square feet</label>
                            </div>
                        </div>
                        <div class="lineitem">
                            <div>
                                <span class="line-item-label">Weight:</span>
                            </div>
                            <div class="result">
                                <span id="weightValue" class="ellipsis"></span>
                                <label>tons</label>
                            </div>
                        </div>
                    </div>
                    <div class="totals">
                        <p class="qty-warning-message hidden">
                            The maximum quantity that can be added to your cart is 99,999 units. Please contact your local branch if you need a
                            greater quantity.
                        </p>
                        <div class="total">
                            <div id="finalCubicYardsLabel">CUBIC YARDS</div>
                            <div id="finalCubicYards"></div>
                        </div>
                        <div class="total">
                            <span>TOTAL</span>
                            <span id="finalTotal"></span>
                        </div>
                    </div>
                </div>
            </div>
            <p id="disclaimer" class="hidden">
                This is an estimate of the amount of material needed
                and does not account for compaction. Changes in
                landscape
                elevation and the amount of moisture present may affect
                the weight of the material.
            </p>
        </div>
        <div class="footer">
            <div id="calculatorAddToCart" class="addtocart plp-ordermultiplesaddtocart">
            </div>
        </div>
    </div>
</div>