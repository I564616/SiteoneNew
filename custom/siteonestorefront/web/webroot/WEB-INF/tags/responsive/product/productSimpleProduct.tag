<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="productDetails" tagdir="/WEB-INF/tags/responsive/product/pdp"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<input class="isShippingHubOnlyPDP" type="hidden" value='${product.stockAvailableOnlyHubStore}'>
<input class="isNationalShippingPDP" type="hidden" value='${product.isNationalShippingPDP}'>
<c:if test="${product.isRegulateditem eq true}">
<div class="row regulatedItemwithprice marginTop10 hidden-sm hidden-xs">
<common:regulatedIcon width="84" height="21"></common:regulatedIcon>
</div>
</c:if>
<c:set var="multipleuompdpredesigncnt" value="0" />
<c:forEach items="${product.sellableUoms}" var="sellableUom">
<c:set var="multipleuompdpredesigncnt" value="${multipleuompdpredesigncnt+1}" />
</c:forEach>

<c:set var="multipleuompdpredesign" value="false" />
<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
       <c:set var="multipleuompdpredesign" value="true" />
</c:if>

<c:set var="notPurchasable" value="false" />
<input type="hidden" class="isSellableForB2BUnitInputPDP1" value="${product.isSellableForB2BUnit}" />
<c:if test="${not empty product.isSellableForB2BUnit && product.isSellableForB2BUnit ne null && product.isSellableForB2BUnit eq false}">
      <c:set var="notPurchasable" value="true" />
</c:if>
<input type="hidden" class="isSellableForB2BUnitInputPDP2" value="${notPurchasable}" />

<c:set var="regulatedAndNotSellableProduct" value="false" />
<c:if test="${not empty product.isRegulatedAndNotSellable && product.isRegulatedAndNotSellable ne null && product.isRegulatedAndNotSellable ne false}">
	<c:set var="regulatedAndNotSellableProduct" value="true" />
</c:if>
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo"
			value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>" />
	</c:when>	
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="noFlex" value=""/>
		<c:set var="fulfillmentHeading" value="col-xs-12 col-md-12 col-sm-12 simple-fulfillment-method__mixedcartheading"/>
		<c:set var="fulfillmentOPtion" value="col-md-12 col-sm-12 col-xs-12 no-padding-xs"/>
		<c:set var="mixCartBorder" value="mixedcart-border"/>
		<c:set var="mixCartfulfillment" value="mixCartfulfillment"/>
		<c:set var="mixCartStoreMessage" value="col-md-7 col-xs-9"/>
	</c:when>
	<c:otherwise>
			<c:set var="noFlex" value="flex" />
			<c:set var="fulfillmentHeading" value="hide"/>
			<c:set var="fulfillmentOPtion" value="col-md-12 col-sm-12 col-xs-12 no-padding-xs"/>
			<c:set var="mixCartBorder" value="mixedcart-border"/>
			<c:set var="mixCartfulfillment" value=""/>
			<c:set var="mixCartStoreMessage" value=""/>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${product.askAnExpertEnable && product.outOfStockImage}">
	<c:set var="hideHardscape" value="hide"/>
	</c:when>
	<c:otherwise>
	<c:set var="hideHardscape" value=""/>
	</c:otherwise>
</c:choose>
<input type="hidden" class="RedesignPDP" name="RedesignPDP" value="${product.multidimensional}"/>
<input type="hidden" class="askAnExpertEnable" name="askAnExpertEnable" value="${product.askAnExpertEnable}"/>
<input type="hidden" class="askAnExpert-outofstock" name="askAnExpert-outofstock" value="${product.outOfStockImage}"/>
<c:set var="hideList" value="${product.hideList}" />
<c:set var="hidelistCheck" value="false" />
<c:if test="${isAnonymous and hideList}">
	<c:set var="hidelistCheck" value="true" />
</c:if>
<c:set var="hideCSP" value="${product.hideCSP}" />
<c:set var="hideCSPCheck" value="false" />
<c:if test="${!isAnonymous && hideCSP}">
	<c:set var="hideCSPCheck" value="true" />
</c:if>
<c:set var="hideUomSelect" value="false" />
<c:set var="nonTransferrable" value="false" />
<c:set var="showUOMDropdown" value="false" />
<c:set var="firstUOMPrice" value="" />
<c:set var="firstUOMRetailPrice" value="" />
<c:if test="${product.isTransferrable eq false && product.isStockInNearbyBranch eq true}">
	<c:set var="nonTransferrable" value="true" />
</c:if>
<c:set var="quoteUomMeasure" value="${false}" />
<c:set var="quoteUomMeasureDesc" value="${false}" />
<c:set var="entryFound" value="${false}" />
<c:forEach items="${product.sellableUoms}" var="sellableUom1">
	<c:if test="${entryFound eq false}">
		<c:set var="entryFound" value="${true}" />
		<c:set var="quoteUomMeasureDesc" value="${sellableUom1.inventoryUOMDesc}" />
		<c:set var="quoteUomMeasure" value="${sellableUom1.measure}" />
	</c:if>
	<c:if test="${product.hideUom eq true}">
		<c:set var="hideUomSelect" value="true" />
		<c:set var="newuomDescription"
			value="${sellableUom2.inventoryUOMDesc}" />
		<c:set var="uomMeasure" value="${sellableUom1.measure}" />
	</c:if>
</c:forEach>
<c:if test="${not empty product.sellableUoms && hideUomSelect ne true && ((isAnonymous && !hideList) || (!isAnonymous && !hideCSP))}">
	<c:set var="showUOMDropdown" value="true" />
</c:if>
<c:if test="${product.singleUom eq true}">
	<c:set var="quoteUomMeasureDesc" value="${product.singleUomDescription}"/>
	<c:set var="quoteUomMeasure" value="${product.singleUomMeasure}"/>
	<c:set var="singleUom" value="true" />
	<c:set var="uomDescription" value="${product.singleUomDescription}" />
	<c:set var="uomMeasure" value="${product.singleUomMeasure}" />
</c:if>
<c:if test="${product.singleUom eq true}">
    <c:set var="singleUomProductCode" value="${product.code}"/>
</c:if>
<c:set var="hardscapeMoreOnWayMsg" value="false"/>
<c:if test="${!isAnonymous and product.isEligibleForBackorder eq true and product.inventoryCheck eq true and(fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' || fn:escapeXml(product.level2Category) == 'Outdoor Living' || fn:escapeXml(product.level2Category) == 'Vida al Aire Libre')}">
	<c:set var="hardscapeMoreOnWayMsg" value="true"/>
</c:if>
<div class=" row simple-product-pdp">
	<input class="singleUom-ProductCode" type="hidden" value='${singleUomProductCode}'>
	<c:if test="${product.productType eq 'Nursery'}">
		<div class="simple-product-pdp__row  col-md-12 col-sm-12 col-xs-12 hidden">
			<div
				class="simple-product-pdp__title col-md-3 col-sm-3 col-xs-4 bold">
				<spring:theme code="text.simpleProduct.available" />
			</div>
			<div
				class="col-md-9 col-sm-9 col-xs-8 simple-product-pdp__value js-pdp-availability">
				${product.stock.stockLevel}</div>
		</div>
	</c:if>
	
	<!-- Pricing Logic swap based on Anonymous User -->
	<c:choose>
		<c:when test="${isAnonymous}">
			<c:set var="showRetailPrice" value="simple-product-pdp__value--retail-price pdp-anonymous"/>
			<c:set var="showYourPrice" value="simple-product-pdp__value--your-price pdp-anonymous"/>
			<c:set var="showTitle" value="hide"/>
		</c:when>
		<c:when test="${!isAnonymous}">
			<input class="quoteUom-MeasureDesc" type="hidden" value='${quoteUomMeasureDesc}'>
			<input class="quoteUom-Measure" type="hidden" value='${quoteUomMeasure}'>
			<input class="quoteUom-CustomerPrice" type="hidden" value='${not empty product.customerPrice ? product.customerPrice.value : '0.00'}'>
			<input class="quoteUom-Price" type="hidden" value='${not empty product.price ? product.price.value : '0.00'}'>
			<input class="quoteUom-code" type="hidden" value='${product.code}'>
			<c:set var="showRetailPrice" value="simple-product-pdp__value--your-price pdp-non-anonymous"/>
			<c:set var="showYourPrice" value="simple-product-pdp__value--retail-price pdp-non-anonymous"/>
			<c:set var="showTitle" value=""/>
		</c:when>
	</c:choose>
	
	<!-- Pricing Logic swap based on Anonymous User Ends -->
	<input type="hidden" id="mulUOMRedesignpdp" value="${showUOMDropdown && product.isRegulateditem && isAnonymous}"/>
	
	<c:if test="${showUOMDropdown}">
		<c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
			<c:set var="inventoryMul">&nbsp;[<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0" />]</c:set>
			<c:if test="${isAnonymous}">
				<c:if test="${firstUOMPrice eq ''}">
					<c:set var="firstUOMPrice" value="${sellableUom.price.formattedValue}&nbsp;/&nbsp;${sellableUom.inventoryUOMDesc}${inventoryMul}" />
				</c:if>
			</c:if>
			<c:if test="${!isAnonymous}">
				<c:if test="${firstUOMPrice eq ''}">
					<c:set var="firstUOMPrice" value="${sellableUom.customerPrice.formattedValue}&nbsp;/&nbsp;${sellableUom.inventoryUOMDesc}${inventoryMul}" />
				</c:if>
				<c:if test="${firstUOMRetailPrice eq ''}">
					<c:set var="firstUOMRetailPrice" value="${sellableUom.price.formattedValue}" />
				</c:if>
			</c:if>
		</c:forEach>
		<productDetails:productDetailsUOMDropdownOptions />
	</c:if>
	<productDetails:productDetailsUOMFullfillment nonTransferrable="${nonTransferrable}"  regulatedAndNotSellableProduct="${regulatedAndNotSellableProduct}" multipleuompdpredesign="${multipleuompdpredesign}"  
		multipleuompdpredesigncnt="${multipleuompdpredesigncnt}" hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" />
	<div class="simple-product-pdp__row simple-price-wrapper ${not product.multidimensional? 'simple-price-wrapper-uom': ''}  col-md-12 col-sm-12 col-xs-12 pdpredsg-price hidden-xs  hidden-sm">
		<div class="row  retail-your-price-container">
		<!-- Regulated label start -->
		<c:if test="${product.isRegulateditem eq true && showUOMDropdown}">
		<div class="col-md-12 regulatedItemwithoutprice padding0 m-t-10 hidden-sm hidden-xs">
		<common:regulatedIconnew></common:regulatedIconnew>
		<span class="regulatednewicon"><spring:theme code="regulatedIconnew.text.desktop"/></span>
		</div>

		<c:if test="${product.isRegulateditem eq true && showUOMDropdown eq false}">
		<div class="col-md-7 regulatedItemwithoutprice padding0 m-t-10 hidden-sm hidden-xs">
		<common:regulatedIcon width="120" height="28"></common:regulatedIcon>
		</div>
		</c:if>
		</c:if>
		<!-- Regulated label end -->
		<input type="hidden" value="${empty product.bulkUOMPrice}" id="emptyproductbulkUOMPric"/>
		<input type="hidden" value="${not empty firstUOMRetailPrice}" id="notemptyfirstMulUOMRetailPrice"/>
		<c:set var="emptyproductbulkUOMPrice" value="${empty product.bulkUOMPrice}"/>
			<div class="${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && hidelistCheck && !hideCSP ?'':'hide-loginbtn'} 10a col-md-12 col-xs-12 col-sm-12 bulkuom-stickyheader"><a href="/en/login" class="bulkUOMStickyHeader-logInToSeeYourPrice signInOverlay">Log in to see your price</a></div>
			<div class="${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && !hidelistCheck && !hideCSPCheck ?'redesignhide':''} 11a ${showUOMDropdown && !isAnonymous && empty product.bulkUOMPrice && not empty firstUOMRetailPrice ? 'col-md-9' : 'col-md-7'}  col-sm-6 ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && isAnonymous ?'hide':''} col-xs-7 ${(isAnonymous and (hideList eq true || empty product.price ) and hideCSP ne true) or (isAnonymous and isGuestCheckoutEnabled eq false) ?'hide':''} ${(hideList && hideCSP) ? ' show-callforpricing' : ''}  regulatedItemLabel 1a ${showUOMDropdown ? " custom-dropdown-button uom-dropdown-button\" onclick=\"ACC.global.dropDownOpenClose(this,'open','uom-dropdown')\"" : "\""} style="${hidelistCheck && !hideCSP ?'display:none!important':''}">
				<div class="simple-product-pdp__value ${showRetailPrice}">
					<div class="${not product.multidimensional?'row':'flex-column'} redesignpdp ${hardscapeMoreOnWayMsg? ' hidden ': ''} ${hideHardscape} ${(not product.multidimensional && !isAnonymous)?' m-t-10':''}">
						<div class="${not product.multidimensional?'col-md-1 padding0':''} 1b ${(showUOMDropdown && !isAnonymous)?'p-l-15 simple-your-price-title-uom':''} simple-your-price-title ${showTitle}"><spring:theme code="text.simpleProduct.yourPrice" /></div>
						<div class="col-md-10 mar-top-5 ${(showUOMDropdown && isAnonymous)?'full-width':''}">
							<product:productpricing isListPrice="true" reDesignPrice="${firstUOMPrice}" firstMulUOMRetailPrice="${firstUOMRetailPrice}" />
						</div>
					</div>
				</div>
			</div>
			<div class="${!showUOMDropdown && !emptyproductbulkUOMPrice && isAnonymous ?'redesignbulkhide':''} bulkuomVar ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && !emptyproductbulkUOMPrice && !isAnonymous?'redesignhide':'redesign-p-l-0'} 22a ${showUOMDropdown && !isAnonymous && empty product.bulkUOMPrice && not empty firstUOMRetailPrice ? 'col-md-3' : 'col-md-5'} col-sm-6 ${(!isAnonymous && empty firstMulUOMRetailPrice)?'col-md-5':''} col-xs-5 ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 ?'hide':''} text-align-right 2a ${(not product.multidimensional && !isAnonymous)?'padding0':''}">
				<div class="${hideHardscape} ${hardscapeMoreOnWayMsg? ' hidden ': ''} simple-product-pdp__value ${(showUOMDropdown && !isAnonymous)?'float-left':''} right-price-section ${showYourPrice}">
					<product:productpricing isListPrice="false" reDesignPrice="false" firstMulUOMRetailPrice="${firstUOMRetailPrice}" />
				</div>
			</div>
		</div>
	</div>
	<div class="cl"></div>
	<div class="product-mesg-uom-0">
	</div>
	<div class="cl"></div>
	<div class="product-mesg-uom-2 simple-product-pdp__row simple-price-wrapper pdpredesignDescription ${not product.multidimensional? 'simple-price-wrapper-uom': ''} col-md-12 col-sm-12 col-xs-12">
	<div class="row  retail-your-price-container">
				<!-- Hardscapes & Outdoor Living | TN | CY -->
				<productDetails:productDetailsAlertMessaging hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" type="mobile" />
	</div>
	</div>
	<div class="cl"></div>
	<div style="display:none;" class="product-mesg-uom-1">
	<div class="simple-product-pdp__row simple-price-wrapper pdpredesignDescription simple-price-wrapper-uom col-md-12 col-sm-12 col-xs-12">
		<div class="row  retail-your-price-container">
			<div class="col-md-12 col-sm-12 col-xs-12  mixedcart-border simple-product-pdp__value product-padding-adjust fulfillment-border-grey simple-product-pdp__value--availability">
				<div class="row store-border">
					<div class="col-md-12 col-xs-12 flex-center store-margin">
					<common:bigCrossIcon height="23" width="23"/>
					<div class="col-md-12 pad-lft-10 ">
							<input type="hidden" name="productAvailabilityStatus" value="regular">
							<span class="pdp-store-detail js-pdp-store-detail-1 col-md-6"><strong><spring:theme code="text.multiple.stock.uom.special.order.message" /></strong></span>
								<div class="simple-check-near-stores col-md-6 hidden-xs top02">
								<a class="no-text-decoration pdp-store-link"  href="javascript:void(0)"><spring:theme code="productDetailsPanel.changeBranch" /></a>
								
								</div>
							</div>
							
					</div>
				</div>
				
				<div class="simple-check-near-stores mob-stores row hidden-md hidden-lg">
                    <a class="no-text-decoration pdp-store-link"  href="javascript:void(0)"><spring:theme code="productDetailsPanel.changeBranch" /></a>
					    
                </div>
             </div>
		</div>
	</div>
	</div>
	<div class="cl"></div>
	<div class="col-xs-12 pdp-checkotherstore hidden">
	<div class="simple-check-near-stores col-md-6 col-xs-12 hidden-lg hidden-md visible-xs">
										<a class="no-text-decoration pdp-store-link"  href="javascript:void(0)"><spring:theme code="productDetailsPanel.changeBranch" /></a>
														
							</div>
	</div>
		<div class="cl"></div>
		<div class="hidden-md hidden-lg col-md-12 no-padding-xs">
			<product:productDetailsHighlights />
		</div>
		<div class="cl"></div>
		<c:if test="${not empty product.productLongDesc}">
		<div class="row">
	      	<div class="tab-content__content-section simple-tab-content hidden-lg hidden-md pdp-accordion-open padtopbtm15" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'pdp-accordion')" data-acconum="1">
				<div class="col-xs-8 tab-content__content__title pdp-fntstyle flex-center" >
					<spring:theme code="product.product.description" />
				</div>
				<div class="col-xs-4 hidden-lg visible-xs text-align-right">
					<span class="glyphicon glyphicon-minus green-title hidden-lg visible-xs" ></span> 
				</div>
				<div class="cl"></div>
				<div class="row pdp-accordion-data-1">
					<div class="col-md-12 col-lg-12">
						<div class="tab-container">
							<div class="tab-details">
							<ycommerce:testId code="productDetails_content_label"> 
								<div class="featureBullets ${isSimple? '': 'hide'}">${product.featureBullets}</div>
								<c:choose>
											<c:when
												test="${not empty product.holidayLightingCalcType}">
													<div class="featureBullets ${isSimple? 'cal-link-top-mobile': ''}">	
															<ul>
																<li><a href="/en/projectcalculators/holidaylighting" target="_blank"><spring:theme code="pdp.holiday.calculator.link"/></a></li>
										 					</ul>
													</div>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty product.calcUrl}">
														<div class="featureBullets ${isSimple? 'cal-link-top-mobile': ''}">	
															<ul>
																<li><a href="${product.calcUrl}" target="_blank"><spring:theme code="pdp.calculator.link"/></a></li>
										 					</ul>
														</div>
												</c:if>
											</c:otherwise>
								</c:choose>
								<div>${product.productLongDesc}</div>
								<c:if test="${product.isRegulateditem}">
								<div class="regulatory-disclaimer"><spring:theme code="text.product.regulatedItem.disclaimer"/></div>
								</c:if>	
							</ycommerce:testId>
							</div>
						</div>
					</div>
				</div>  
	        </div> 
		</div>   
		</c:if>
</div>
<c:if test="${multipleuompdpredesign && multipleuompdpredesigncnt > 1}">
		<productDetails:productDetailsUOMTable hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" /> 
</c:if>
<c:choose>
	<c:when test="${product.outOfStockImage || hardscapeMoreOnWayMsg}">
		<c:if test="${!isAnonymous && quotesFeatureSwitch && !product.askAnExpertEnable}">
			<div class="row pdp-commonerror-section flex-center m-r-0 m-t-10 hidden-sm hidden-xs">
				<common:exclamation-circle width="25" height="25" />
				<div class="p-l-15">
					<span class="bold font-size-14"><spring:theme code="pdp.new.request.a.quote.item.not.available" /></span>
					<span><spring:theme code="pdp.new.request.a.quote.item.not.available.msg" /></span>
				</div>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="row pdp-warning_info_${product.code} pdp-commonerror-section flex-center m-r-0 m-t-10 hidden-sm hidden-xs hidden">
			<common:exclamation-circle width="25" height="25" /><span class="pdp-commonerror p-l-15"><spring:theme code="text.plp.expect.delay.for.fullorder" /></span>
		</div>
	</c:otherwise>
</c:choose>
<!-- Hardscapes & Outdoor Living | TN | CY -->
<productDetails:productDetailsAlertMessaging hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" type="desktop" />
<div class="row hidden">
	<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
		<div class="simple-product-pdp__row col-md-12 col-sm-12 col-xs-12">
			<div
				class="simple-product-pdp__title col-md-3 col-sm-3 col-xs-4 bold">
				<div class="product-detail-uom">
					<label class="variant-label-name" for="uom-options"><b><spring:theme
								code="productDetailsPanel.unitOfMeasure" /></b></label>
				</div>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-8 simple-product-pdp__value">
				<div class="col-xs-12 col-sm-12 col-md-6 padding0">
					<select id="uom-options" class="js-uom-selector-input "
						<c:if test="${product.variantCount > 0}">disabled</c:if>>
						<option value="select" id="pleaseSelect"><spring:theme
								code="requestaccount.select" /></option>
						<c:forEach items="${product.sellableUoms}" var="sellableUom">
							<c:set var="uomDescription"
								value="${sellableUom.inventoryUOMDesc}" />
							<c:set var="uomMeasure" value="${sellableUom.measure}" />

							<option value="${sellableUom.inventoryUOMID}"
								data-inventory="${sellableUom.inventoryMultiplier}">${sellableUom.inventoryUOMDesc}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</c:if>
	
</div>
<input type="hidden" name="bulkCalculatorType" class="bulkCalculatorType" value="${product.bulkCalculatorType}" />
<input type="hidden" name="sellableUomsFlag" class="sellableUomsFlag" value="${fn:length(product.sellableUoms) gt 1}" />
<input type="hidden" name="loggedInFlag" class="loggedInFlag" value="${isAnonymous}" />
<div id="materialsCalculators" class="row showBulkCalculatorEntrypoint col-xs-12 bulk hidden-sm hidden-xs hidden">
	<div class="bulk-calculator">
		<common:globalBulkCalcIcons iconName="largeCalculatorIcon"/>
		<div class="content">
			<div class="flex flex-column">
				<p class="header">Not sure how much to get?</p>
				<p class="tool-tip">Enter your dimensions to estimate your price.</p>
			</div>
			<button id="showBulkCalculator">
				<common:globalBulkCalcIcons iconName="smallerCalculatorIcon" />
				Material Calculator
			</button>
		</div>
	</div>
</div>
<common:bulkCalculatorOverlay price='${not empty product.price ? product.price.value : "0.00"}'/>
<div class="row hidden-xs hidden-sm">
<c:if test="${product.isRegulatoryLicenseCheckFailed eq false}">
<div class="col-md-12 f-s-12 marginTop10"><span class="text-orange"><spring:theme code="restricted.license.msg.pdp" /></span> 
<span class="js-info-tootip blueLink" rel="custom-tooltip"><span class="tooltip-content hide">
											<span class=" f-s-12 bold"> <spring:theme code="restricted.license.msg.tooltip1" /> <br/>
											 <spring:theme code="restricted.license.msg.tooltip2" /></span>
										</span>
										<spring:theme code="show.details.msg" />
									</span>
									</div>
</c:if>

	<div class="product-uom-1 simple-product-pdp__row col-xs-12 p-l-0 no-padding-pdp ${product.askAnExpertEnable && product.outOfStockImage ? 'hide':''}">
		<div class="row no-margin">
		<c:set var="qtyInputSection">
			<div class="qty-selector input-group js-qty-selector flex qtyInputFieldPlaceHolder">
				<button class="minusQty pdp-qtyBtn border-rad-left" type="button" id="minusQty_${product.code}">
					<common:minusIcon iconColor="#77A12E" />
				</button>
				<input type="text" maxlength="5" class="form-control js-qty-selector-input js-pdp-add-to-cart" size="1"
					value="1" name="pdpAddtoCartInput" id="pdpAddtoCartInput" />
				<button class="plusQty pdp-qtyBtn border-rad-right" type="button" id="plusQty_${product.code}">
					<common:plusIcon iconColor="#77A12E" />
				</button>
			</div>
		</c:set>
		<c:choose>
			<c:when test="${nonTransferrable}">
				<div class="col-md-4 hidden-sm hidden-xs p-l-0">${qtyInputSection}</div>
				<div class="col-md-4 p-l-0 ${notPurchasable? ' hide ': ' '}">
					<div class="m-t-10 flex-center justify-center paddingTopB10 pdpAddtoListPopupBtn removesignInlink signInOverlay hidden-xs hidden-sm"><span class="m-r-5 pdp-popup-atl-btn"><spring:theme code="cartItems.addToList"/></span><common:filter-chevron-down iconColor="#4492B6" /></div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 p-l-0 pad-xs-lft-10 pad-xs-rgt-15">
					<div class="text-center p-y-15 pdp-changeBranch bg-lightGrey">
						<div onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);" class="call-branch-icon-text">
							<spring:theme code="pdp.new.text.change.branch" />
						</div>
					</div>
				</div>
			</c:when>
			<c:when test="${product.outOfStockImage}">
				<div class="col-md-4 hidden-sm hidden-xs p-l-0">${qtyInputSection}</div>
				<div class="col-md-4 p-l-0 ${notPurchasable? ' hide ': ' '}">
					<div class="m-t-10 flex-center justify-center paddingTopB10 pdpAddtoListPopupBtn removesignInlink signInOverlay hidden-xs hidden-sm"><span class="m-r-5 pdp-popup-atl-btn"><spring:theme code="cartItems.addToList"/></span><common:filter-chevron-down iconColor="#4492B6" /></div>
				</div>
				<div class="col-md-4 col-xs-12 p-l-0 m-b-15 hidden-sm hidden-xs ${notPurchasable? ' hide ': ' '}">
					<input id="requestQuoteButtonDesc" type="hidden" value='${product.name}'>
					<input id="requestQuoteButtonItemnumber" type="hidden" value='${product.itemNumber}'>
					<c:if test="${!isAnonymous && quotesFeatureSwitch}">
						<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtnPDP m-t-10"
							data-product-description="${fn:escapeXml(product.name)}"
							onclick="ACC.savedlist.requestQuotePopupplp(this,'product-main-info','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')">
							<spring:theme code="request.quote.popup.request.text" />
						</button>
					</c:if>
				</div>
			</c:when>
			<c:when test="${hardscapeMoreOnWayMsg}">
				<div class="col-md-4 hidden-sm hidden-xs p-l-0">${qtyInputSection}</div>
				<div class="col-md-4 p-l-0 ${notPurchasable? ' hide ': ' '}">
					<div class="m-t-10 flex-center justify-center paddingTopB10 pdpAddtoListPopupBtn removesignInlink signInOverlay hidden-xs hidden-sm"><span class="m-r-5 pdp-popup-atl-btn"><spring:theme code="cartItems.addToList"/></span><common:filter-chevron-down iconColor="#4492B6" /></div>
				</div>
				<div class="col-md-4 col-xs-12 p-l-0 m-b-15 ${notPurchasable? ' hide ': ' '}">
					<c:if test="${!isAnonymous && quotesFeatureSwitch}">
						<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtnPDP m-t-10"
							data-product-description="${fn:escapeXml(product.name)}"
							onclick="ACC.savedlist.requestQuotePopupplp(this,'product-main-info','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')">
							<spring:theme code="request.quote.popup.request.text" />
						</button>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-12 col-sm-12 col-xs-12 simple-product-pdp__value">
					<div class="row no-margin">
						<div class="col-xs-12 col-sm-12 col-md-12 padding0">
							<div class="row">
								<cms:pageSlot position="AddToCart" var="component" element="div"
									class="page-details-variants-select col-md-12 pdp-details ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 ?'m-r-0':''}">
									<cms:component component="${component}" element="div"
										class="yComponentWrapper page-details-add-to-cart-component" />
								</cms:pageSlot>
								<product:productPromotionSection product="${product}" />
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	</div>
</div>
<div class="row padding-bottom-15 m-b-15 pdp-addToList hidden-md hidden-lg hidden-xs">
    <div class="line m-r-0 pdp-marginLeft"></div>
    <div class="col-xs-5 col-md-3 padding0 not-instock-atl">
        <product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
    </div>
    <div class="line m-l-0 m-r-15 pdp-m-l-10"></div>
</div>
<div class="row padding-bottom-15 m-b-15 pdp-addToList hidden-xs hidden-sm">
<c:if test="${notPurchasable}">
	<div class="row">
        <div class="col-xs-12 m-b-5 m-t-5">
        	<div class="flex-center restricted-purchase-alert text-gray-1 m-r-15">
            	This item is unavailable for purchase under your account due to company purchasing restrictions.
			</div>
        </div>
    </div>
</c:if>
<div class="hidden">
    <div class="line m-r-0 pdp-marginLeft"></div>
    <div class="col-xs-5 col-md-3 padding0 not-instock-atl">
        <product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
    </div>
    <div class="line m-l-0 m-r-15 pdp-m-l-15"></div>
</div>
</div>
<!-- Hardscapes Experts on call and Ask An Expert -->

<c:if test="${product.askAnExpertEnable eq true}">
<div class="row ask-an-expert-warpper ${!product.outOfStockImage ? '':'m-t-10'}">
	<div class="simple-product-pdp__row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 ask-an-expert__value padding0">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 experts-call-wrapper padding0">
				<img class="img-responsive" src="/_ui/responsive/theme-lambda/images/askanexpertimage-mobile.png" alt="askanexpertimage" >
				<div class="experts-call-text ${product.outOfStockImage ? 'expertsbg':'expertsbg-hardscape'}" ><spring:theme code="hardscape.expert.on.call" /></div>
			</div>
			<c:choose>
				<c:when test="${product.outOfStockImage}">
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 ask-an-expert-link">
						<a class="help-text"><spring:theme code="hardscape.pdp.help.text" /></a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 ask-an-expert-link hardscape_overlay_Pdp" >
						<spring:theme code="hardscape.pdp.ask.an.expert.text" /><a class="hardspace-ask-text askexp-anlytcs"><spring:theme code="hardscape.ask.an.expert" /></a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

	<div class="row ask-an-expert-warpper ask-an-expert-list simple-product-pdp__row  ${!product.outOfStockImage ? 'hide':''}">
		<div class="col-md-12 ask-an-expert-button hardscape_overlay_Pdp padding0">
			<button class="btn btn-default btn-block askexp-anlytcs"><spring:theme code="hardscape.ask.an.expert" /></button>
		</div>
	</div>
 </c:if>

<c:if test="${not empty product.productLongDesc}">
	<div class="row m-r-0 tab-content__content-section simple-tab-content hidden-xs hidden-sm m-t-15" id="pdpSimpleProductDesktopDescription">
		<div class="tab-content__content__title flex-center">
			<spring:theme code="product.product.description" />
		</div>
		<div class="featureBullets">
			<c:choose>
				<c:when test="${not empty product.salientBullets}">
						${product.salientBullets}
				</c:when>
				<c:otherwise>
						${product.featureBullets}
				</c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="col-md-12 col-lg-12">
				<div class="tab-container">
					<div class="tab-details">
						<ycommerce:testId code="productDetails_content_label">
							<div class="productLongDesc1">${product.productLongDesc}
								<c:if test="${product.isRegulateditem}">
									<div class="regulatory-disclaimer">
										<spring:theme code="text.product.regulatedItem.disclaimer" />
									</div>
								</c:if>
							</div>
						</ycommerce:testId>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>