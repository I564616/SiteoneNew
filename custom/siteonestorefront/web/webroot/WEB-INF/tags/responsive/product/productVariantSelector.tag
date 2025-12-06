<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:set var="allWishlist" value="${allWishlist}" scope="session" />
<c:set var="wishListName" value="${wishListName}" scope="session" />
<input class="isShippingHubOnlyPDP" type="hidden" value='${product.stockAvailableOnlyHubStore}'>
<input class="isNationalShippingPDP" type="hidden" value='${product.isNationalShippingPDP}'>
<fmt:setLocale value="en_US" scope="session" />
<input type="hidden" class="variantproduct-name" value="${product.name}"/>
<input type="hidden" class="pdp-itemnumber" value="${product.itemNumber}"/>

<c:set value="true" var="isBranchPickupAvailable"></c:set>
<c:set value="true" var="isBranchDeliveryAvailable"></c:set>
<c:set value="true" var="isBranchShippingAvailable"></c:set>

<c:if test="${not empty sessionStore.pickupfullfillment && sessionStore.pickupfullfillment ne null}" >
	<c:set value="${sessionStore.pickupfullfillment}" var="isBranchPickupAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.deliveryfullfillment && sessionStore.deliveryfullfillment ne null}" >
	<c:set value="${sessionStore.deliveryfullfillment}" var="isBranchDeliveryAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.shippingfullfillment && sessionStore.shippingfullfillment ne null}" >
	<c:set value="${sessionStore.shippingfullfillment}" var="isBranchShippingAvailable"></c:set>
</c:if>

<c:set var="regulatedAndNotSellableProduct" value="false" />
<c:if test="${not empty product.isRegulatedAndNotSellable && product.isRegulatedAndNotSellable ne null && product.isRegulatedAndNotSellable ne false}">
	<c:set var="regulatedAndNotSellableProduct" value="true" />
</c:if>

<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>" />
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="hideFulfillment" value="hidden" />
	</c:when>
	<c:when test="${isMixedCartEnabled eq false}">
		<c:set var="hideFulfillment" value="" />
	</c:when>
</c:choose>


<c:choose>
	<c:when
		test="${productType ne 'Nursery' and parentVariantCategoryNameList.size() eq 2 }">
		<c:set var="adjustWidthLocation" value="col-md-4 varient1" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:when
		test="${productType ne 'Nursery' and parentVariantCategoryNameList.size() eq 1 }">
		<c:set var="adjustWidthLocation" value="col-md-4 varient2" />
		<c:set var="adjustWidthVariant" value="col-md-2" />
	</c:when>
	<c:when
		test="${productType eq 'Nursery' and parentVariantCategoryNameList.size() eq 1 }">
		<c:set var="adjustWidthLocation" value="col-md-3 varient3" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:when
		test="${productType eq 'Nursery' and parentVariantCategoryNameList.size() eq 3 }">
		<c:set var="adjustWidthLocation" value="col-md-3 varient4" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:otherwise>
		<c:set var="adjustWidthLocation" value="col-md-4 varient5" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:otherwise>
</c:choose>
<c:set var="isStockInNearby" value="true" />
<c:forEach items="${sessionVariantOpts}" var="variantProduct" varStatus="loop">
	<c:if test="${variantProduct.isStockInNearbyBranch ne true && variantProduct.outOfStockImage eq null}">
		<c:set var="isStockInNearby" value="false" />
	</c:if>
</c:forEach>
<c:set var="disableIcons" value="false" />
<c:if test="${isStockInNearby eq true && product.isTransferrable eq false}">
	<c:set var="disableIcons" value="true" />
</c:if>
<div class="cl"></div>
	<div class="hidden-md hidden-lg col-md-12 no-padding-xs productDetailsHighlights-VariantSection">
		<product:productDetailsHighlights />
	</div>
<div class="cl"></div>
<div class="row">
		<c:if test="${not empty product.productLongDesc}">
		<div class="row hidden-md hidden-lg">
	      	<div class="tab-content__content-section simple-tab-content hidden-lg hidden-md pdp-accordion-open padtopbtm15" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'pdp-accordion')" data-acconum="1">
				<div class="col-xs-8 tab-content__content__title pdp-fntstyle flex-center" >
					<spring:theme code="product.product.description" />
				</div>
				<div class="col-xs-4 hidden-lg visible-xs text-align-right resign-description-icon">
					<span class="glyphicon glyphicon-minus green-title hidden-lg visible-xs" ></span> 
				</div>
				<div class="cl"></div>
				<div class="row pdp-accordion-data-1">
					<div class="col-md-12 col-lg-12 padding-xs-lft-25">
						<div class="tab-container">
							<div class="tab-details">
							<ycommerce:testId code="productDetails_content_label">
								<div>${product.productLongDesc}</div>
								<div class="featureBullets">
									<c:choose>
                                    	<c:when test="${not empty product.featureBullets}">
                                             ${product.featureBullets}
                                		</c:when>
                                		<c:otherwise>
                                             ${product.salientBullets}
                                		</c:otherwise>
                            		</c:choose>
								</div>
								<c:choose>
											<c:when
												test="${not empty product.holidayLightingCalcType}">
													<div class="featureBullets cal-link-top-mobile">	
														<ul>
															<li><a href="/en/projectcalculators/holidaylighting" target="_blank"><spring:theme code="pdp.holiday.calculator.link"/></a></li>
										 				</ul>
													</div>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty product.calcUrl}">
													<div class="featureBullets cal-link-top-mobile">	
														<ul>
															<li><a href="${product.calcUrl}" target="_blank"><spring:theme code="pdp.calculator.link"/></a></li>
										 				</ul>
													</div>
												</c:if>
											</c:otherwise>
								</c:choose>
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

<div class="row ${hideFulfillment} pdp-variant marginBottom20">
	<div class="col-md-12 variant-top-fulfillment-method" onclick="ACC.global.openCloseAccordion(this,'close', 2, 'pdp-accordion')" data-acconum="2">
		<div class ="pdp-fulfillment-heading hidden-xs visible-md visible-lg pdp-fulfillment-heading"><spring:theme code="pdp.text.fulfillment.title"/></div>
			<input type="hidden" class="branch-pickupAvailable-pdp-variant" value="${isBranchPickupAvailable}"/>
			<input type="hidden" class="branch-deliveryAvailable-pdp-variant" value="${isBranchDeliveryAvailable}"/>
			<input type="hidden" class="branch-shippingAvailable-pdp-variant" value="${isBranchShippingAvailable}"/>
			<c:set value="false" var="isDeliveryOnlyBranchPdpVariant"/>
			<c:if test="${!isBranchPickupAvailable && isBranchDeliveryAvailable && !isBranchShippingAvailable}" >
				<c:set value="true" var="isDeliveryOnlyBranchPdpVariant"/>
			</c:if>
			<input type="hidden" class="isDeliveryOnlyBranchPdpVariant" value="${isDeliveryOnlyBranchPdpVariant}"/>
		<div class="col-xs-12">
			<div class="col-xs-7 pdp-fulfillment-heading hidden-md visible-xs"><spring:theme code="pdp.redesign.fulfillment.Options"/></div>
			<div class="col-xs-4 pdp-fullfillment-cart hidden-md visible-xs padding0"><spring:theme code="pdp.redesign.select.at.cart"/></div>
			<div class="col-xs-1 hidden-lg visible-xs text-align-right padding0"> 
				<span class="glyphicon glyphicon-plus green-title visible-xs hidden-lg"></span> 
			</div>
		 </div>
		 <div class="cl"></div>
		<div class="row flex-xs marginBottom10 pdp-accordion-data-2" data-isLABranch="${cartData.isLABranch}" data-isTampaBranch="${cartData.isTampaBranch}" data-stockAvailableOnlyHubStore="${product.stockAvailableOnlyHubStore}" data-isNationalShippingPDP="${product.isNationalShippingPDP}">
			<c:if test="${isDeliveryOnlyBranchPdpVariant}" >
				<c:set value="false" var="isPickupEnabled"/>
			</c:if>
			<div class="col-md-12 col-sm-12 col-xs-12 no-padding-xs">
				<div class="row no-margin flex-sm flex-md">
					<div class="col-xs-12 col-md-4 variant-top-fulfillment-method__tile no-padding-rgt-md">
						<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
							<div class="tooltip-content hide">
								<common:FulfillmentTooltipContent fulfillment="pickUp"/>
							</div> 
							<common:pdp-info-icon />
						</div>
						<div class="branch-flex">
							<div class="col-md-2 col-xs-2 p-l-0 margin-top-8">
								<common:pickUpIcon height="17" width="20" iconColor="${(isPickupEnabled && !disableIcons && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct))? '#000': '#999999'}" />
							</div>
							<div class="col-md-10 col-xs-9 pdp-Variant-fullfilment varient-paddingTopB10 p-l-0 ${(isPickupEnabled && !disableIcons && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct)) ? '': 'text-grey' }">
								<c:choose>
									<c:when test="${isPickupEnabled && !disableIcons}">
										<spring:theme code="pdp.text.fulfillment.pickup" arguments="${storeName},${storeId}"/>
									</c:when>
									<c:otherwise>
										<spring:theme code="pdp.text.fulfillment.pickup.unavailable" arguments="${sessionStore.name}"/>                    									
									</c:otherwise>																	
								</c:choose>
							</div>
						</div>
					</div>

					<div class="col-xs-12 col-md-4 variant-top-fulfillment-method__tile">
						<c:set var="enableVarDelivery" value="${isDeliveryEnabled && product.isDeliverable && !disableIcons}" />
						<c:if test="${isBranchDeliveryAvailable == false }">
							<c:set var="enableVarDelivery" value="false" />
						</c:if>
						<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
							<div class="tooltip-content hide">
								<common:FulfillmentTooltipContent fulfillment="delivery"/>
							</div> 
							<common:pdp-info-icon />
						</div>
						<div class="branch-flex">
							<div class="col-md-2 col-xs-2 p-l-0 margin-top-8">
								<common:deliveryIcon height="15" width="30" iconColor="${(enableVarDelivery && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct))? '#000': '#999999'}" />
							</div>
							
							<div class="col-md-10 col-xs-9 pdp-Variant-fullfilment varient-paddingTopB10 ${(enableVarDelivery && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct))? '#000': 'text-grey' }">
							<c:choose>
								<c:when test="${enableVarDelivery}">
									<spring:theme code="pdp.text.fulfillment.available" arguments="${storeName},${product.stock.storeId}"/>
								</c:when>
								<c:otherwise>
									<spring:theme code="pdp.text.fulfillment.unavailable" arguments="${sessionStore.name}" />
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</div>

					<input type="hidden" class="productvariantselector-segmentLevelShippingEnabled" value="${segmentLevelShippingEnabled}"/>
					<div class="col-xs-12 col-md-4 variant-top-fulfillment-method__tile">
						<c:if test="${isDeliveryOnlyBranchPdpVariant or !segmentLevelShippingEnabled}" >
							<c:set value="false" var="isBranchShippingAvailable"/>
						</c:if>
						<div class="info-tooltip js-info-tootip${product.isNationalShippingPDP == true and isBranchShippingAvailable == false? ' hidden' : ''}"  rel="custom-tooltip">
							<div class="tooltip-content hide">
								<common:FulfillmentTooltipContent fulfillment="parcel"/>
							</div> 
							<common:pdp-info-icon />
						</div>
						<div class="branch-flex">
							<div class="col-md-2 col-xs-2 p-l-0 margin-top-8">
								<common:parcelIcon height="17" width="25" iconColor="${(isBranchShippingAvailable && product.isShippable && !disableIcons && variantisForceinstockpdp && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct)) ? '#000': '#999999' }" />
							</div>
							
							<div class="col-md-10 col-xs-9 pdp-Variant-fullfilment product-xs-pad-lft-0 margin-top-8 ${(isBranchShippingAvailable && product.isShippable && !disableIcons || isDeliveryOnlyBranchPdpVariant && (!regulatedAndNotSellableProduct || isregulatedAndNotSellableProduct)) ? '': 'text-grey' }">
							<c:choose>
								<c:when test="${isBranchShippingAvailable && product.isShippable && !disableIcons}">
									<span class='bold'><spring:theme code="pdp.text.fulfillment.shipping"/></span>
									<span><spring:theme code="pdp.text.fulfillment.shippingavailable"/></span>
								</c:when>
								<c:otherwise>
									<spring:theme code="pdp.text.fulfillment.unavailable1" />
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<!--Variant table starts-->


<c:set var="showBulkUom" value="false" />
<c:forEach items="${variantProduct.sellableUoms}" var="sellableUom">
<c:if test="${sellableUom.inventoryUOMDesc eq variantProduct.singleUomMeasure and sellableUom.inventoryMultiplier gt 1}">
	<c:set var="showBulkUom" value="true" />
</c:if>
</c:forEach>

<div class="col-md-12 product-variant-table">
	<!-- Variant Heading -->
	<div class="product-variant-table__header row hidden-xs hidden-sm">
		<div class="product-variant-table__heading col-md-2 col-sm-2  ${isMixedCartEnabled? '': 'product-variant-table__heading--item'}">
			<spring:theme code="text.variantProduct.item" />
		</div>
		<c:set var="itemsAvailable" value="0" />
		<c:forEach items="${parentVariantCategoryNameList}" var="name">
			<c:choose>
				<c:when test="${!isMixedCartEnabled}">
					<div class="product-variant-table__heading ${adjustWidthVariant} col-sm-1 expand-size hidden">${name}</div>
				</c:when>
				<c:otherwise>
					<c:set var="itemsAvailable" value="${itemsAvailable +1 }" />
					<div class="product-variant-table__heading col-md-2 col-sm-1 expand-size hidden" style="display: ${itemsAvailable ==1 ? 'block' : 'none'}" >${name}</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${productType eq 'Nursery'}">
			<div class="product-variant-table__heading col-md-1 col-sm-1 product-variant-table__heading--available variant-md-padding0 ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true ? 'hide':''}" style="display:${isMixedCartEnabled? 'none': 'block'};">
				<spring:theme code="text.variantProduct.available" />
			</div>
		</c:if>
		<c:choose>
			<c:when test="${!isMixedCartEnabled}">
				<div class="product-variant-table__heading ${adjustWidthLocation} variant-md-pad-lft-0 col-sm-3 product-variant-table__heading--location">
					<spring:theme code="text.variantProduct.location" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="product-variant-table__heading col-md-3 col-sm-3 product-variant-table__heading--location variant-md-pad-lft-0">
					<spring:theme code="product.grid.availability" />
				</div>
			</c:otherwise>
		</c:choose>
		
		<div class="product-variant-table__heading ${!isAnonymous ? 'padding0':''} col-sm-1 product-variant-table__heading--price  ${isMixedCartEnabled? 'col-md-2': 'col-md-2 product-variant-table__data--price'}">
			<spring:theme code="text.variantProduct.Price" />
		</div>
		<div class="product-variant-table__heading col-md-3 col-sm-2" style="display:${isMixedCartEnabled? 'none': 'block'};"> </div>
	</div>
<!-- ./Variant Heading -->

	<input type="hidden" id="totalsize" value="${sessionVariantOpts.size()}"/>
	<c:set var="variantSize" value="6"/>
		<c:forEach items="${sessionVariantOpts}" var="variantProduct" varStatus="loop" begin="0" end="${variantSize}">
			<product:productVariantDisplay variantProduct="${variantProduct}" product="${product}" loopIndex="${loop.index}"/>
		</c:forEach>
	<div class="row variant show-more-container ${sessionVariantOpts.size() le variantSize?'hide':''}">
		<c:set var="remainingItems" value="${sessionVariantOpts.size()- variantSize - 1}"/>
		<a href="#" class="viewMoreLess pdp-variant-link ${hideLink}" data-state="expand" data-remaining="${remainingItems}"><spring:theme code="variant.text.show" />&nbsp;${remainingItems}&nbsp;<spring:theme code="variant.text.more.items"/></a>
	</div>
	<c:if test="${not empty product.productLongDesc}">
	<div class="row m-t-15">
		<div class="tab-content col-md-12 hidden-xs hidden-sm ">
			<div class="tab-content__content">
				<div class="tab-content__content-section ${isSimple? 'hide': ''}">
					<div class="tab-content__content__title flex-center">
					<spring:theme code="product.product.description" />
					</div>
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="tab-container">
							<product:productDetailsTab product="${product}" />
							</div>
						</div>
					</div>  
				</div> 
			</div>   
		</div>   
	</div>
	</c:if>
</div>
<!--Variant table ends-->

