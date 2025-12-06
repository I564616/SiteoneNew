<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product"
	tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<c:set var="multipleuompdpredesigncnt" value="0" />
<c:forEach items="${product.sellableUoms}" var="sellableUom">
<c:set var="multipleuompdpredesigncnt" value="${multipleuompdpredesigncnt+1}" />
</c:forEach>

<c:set var="multipleuompdpredesign" value="false" />
<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
       <c:set var="multipleuompdpredesign" value="true" />
</c:if>
<c:set var="showUOMDropdown" value="false" />
<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
	<c:set var="showUOMDropdown" value="true" />
</c:if>
<c:if test="${product.level1Category eq 'Hardscapes & Outdoor Living' || product.level1Category eq 'Materiales duros & Vida al Aire Libre'}">
	<input type="hidden" class="hardscapeProd_${product.code}" name="isHardscapeProduct" value="true" />
</c:if>

<div class="product-details ${isMixedCartEnabled? 'mixedcart-products' : ''}">
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="formElement"
		tagdir="/WEB-INF/tags/responsive/formElement"%>

	<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
	<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
    <input type="hidden" class="trackProductCode" name="trackProductCode" value="${product.code}">
	<input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${product.price.value}">
	<input type="hidden" class="trackCSP" name="trackCSP" value="${product.customerPrice.value}">
	<input type="hidden" class="isForceinstockUOM" name="isForceinstockUOM" value="${product.isForceInStock}">
	<input type="hidden" class="secretSku" name="secretSku" value="${product.secretSku}">
	<input type="hidden" id="orderQtyInterval" name="orderQtyInterval" value="${product.orderQuantityInterval}">
	<input type="hidden" class="isNurseryBuyingGroupBranchpdp" value="${isNurseryBuyingGroupBranch}">
	<c:set var="hideList" value="${product.hideList}" />
	<c:if test="${isAnonymous and hideList}">
		<c:set var="hidelistCheck" value="true" />
	</c:if>
	<c:set var="hideCSP" value="${product.hideCSP}" />
	<input type="hidden" value="${method}" class="referrermethod"/>
	<input type="hidden" value="${methodMetaData}" class="referrermethoddata"/>
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
		<c:when test="${not product.multidimensional}">
		<c:set var="imageContainerSpacing" value="col-xs-12 col-sm-3 col-sm-push-0 col-md-10  image-border"/>
		<c:set var="middleContainerSpacing" value="col-sm-9 col-md-6" />
		<c:set var="isSimple" value="true" />
		<c:set var="iconColor" value="#CCCCCC" />
		</c:when>
		<c:otherwise>
		<c:set var="imageContainerSpacing" value="col-xs-12 col-sm-4 col-sm-push-0 col-md-10"/>
		<c:set var="middleContainerSpacing" value="col-sm-8 col-md-7" />
		<c:set var="isSimple" value="false" />
		<c:set var="iconColor" value="#CCCCCC" />
		</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="rowClass" value="row"/>
	</c:when>
	<c:otherwise>
			<c:set var="rowClass" value=""/>
	</c:otherwise>
	</c:choose>
	<c:set var="hideIfVariant" value="hide" />	
	<c:set var="showinlineifvariant" value="showinlineifvariant" />
	<c:set var="alignLinkRight" value="" />
	
	<c:if test="${product.multidimensional eq false}">
        <c:set var="imgborder" value="pdpredesign-imgborder" />    
    </c:if>
	<c:set var="hardscapeMoreOnWayMsg" value="false"/>
	<c:if test="${!isAnonymous and product.isEligibleForBackorder eq true and product.inventoryCheck eq true and (fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' ||
	fn:escapeXml(product.level2Category) == 'Outdoor Living' || fn:escapeXml(product.level2Category) == 'Vida al Aire Libre')}">
		<c:set var="hardscapeMoreOnWayMsg" value="true"/>
	</c:if>
	<c:set var="notPurchasable" value="false" />
    <input type="hidden" class="isSellableForB2BUnitInputPDP" value="${product.isSellableForB2BUnit}" />
	<c:if test="${not empty product.isSellableForB2BUnit && product.isSellableForB2BUnit ne null && product.isSellableForB2BUnit eq false}">
       <c:set var="notPurchasable" value="true" />
	</c:if>
	<input type="hidden" class="notPurchasable_PDP" value="${notPurchasable}" />
	<div class="row product-content rightDivArea ${not product.multidimensional?'pdpredesign-wrapper':'pdpvariantredesign-wrapper'}">
	<div class="row col-xs-12 hidden-sm hidden-md hidden-lg  mobile-pdp-qty-error"> 
		<div class="col-xs-6 intervalQtyError hidden"> 
        	<div class="mobile-error-margin"><spring:theme code="text.valid.quantity"/></div>
        </div> 
		<div class="col-xs-6 availableQtyError availQtyError1 hidden"> 
        	<div class="mobile-error-margin"><spring:theme code="text.available.quantity.error1" arguments="${product.stock.stockLevel}"/></div>
        </div> 
		<div class="col-xs-6 availableQtyError availQtyError2 hidden"> 
        	<div class="mobile-error-margin"><spring:theme code="text.available.quantity.error2" arguments="${product.stock.stockLevel}"/></div>
        </div> 
        <c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval ne '' && product.orderQuantityInterval ne '1'}">
        <div class=" col-xs-6 intervalQtyInfo intervalQtyInfo-mobile"> 
        	<div class="mobile-error-margin" style="display: flex;"><span style="margin-top: -1px;"><common:info-circle iconColor="#78a22f"/></span>&nbsp;        
            <spring:theme code="text.minimum.value"/>&nbsp;${product.orderQuantityInterval}          
			</div>
        </div>
        </c:if>
		<c:if test="${product.minOrderQuantity > 1}">
        <div class=" col-xs-6 minQtyInfo intervalQtyInfo-mobile"> 
        	<div class="mobile-error-margin" style="display: flex;"><span style="margin-top: -1px;"><common:info-circle iconColor="#78a22f"/></span>&nbsp;	 
                <spring:theme code="text.minimum.info"/>&nbsp;${product.minOrderQuantity}
			</div>
        </div>
        </c:if>
	</div>
	<div class="row col-xs-12 hidden-sm hidden-md hidden-lg mobile-pdp-sticky">
		<%-- <c:if test="${showUOMDropdown}">
			<c:set var="callForPricingFlag" value="false" />
			<div class="col-xs-12 flex-center ${isAnonymous && product.hideList ? 'hide': ''}">
				<c:if test="${isAnonymous}">
					<c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
						<c:choose>
							<c:when test="${not empty sellableUom.price && sellableUom.price.value ne '0.0'}">
								<div class="text-capitalize multipleuom-retailpricetext m-r-10 m-y-10">
									<spring:theme code="pdp.new.uom.redesign.your.total" />
								</div>
								&nbsp;<div class="multipleuom-retailprice m-r-10 m-y-10">
									${sellableUom.price.formattedValue}
								</div>
							</c:when>
							<c:otherwise>
								<c:set var="callForPricingFlag" value="true" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				<c:if test="${!isAnonymous && !hideCSP}">
					<div class="text-capitalize multipleuom-retailpricetext m-r-10 m-y-10">
						<spring:theme code="pdp.new.uom.redesign.your.total" />
					</div>
					&nbsp;<div class="multipleuom-retailprice m-r-10 m-y-10">
						<c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
							<c:choose>
								<c:when test="${not empty sellableUom.customerPrice && sellableUom.customerPrice.value ne '0.0'}">
									${sellableUom.customerPrice.formattedValue}
								</c:when>
								<c:otherwise>
									<c:set var="callForPricingFlag" value="true" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${(isAnonymous || (!isAnonymous && !hideCSP)) && !callForPricingFlag}">
					<c:forEach items="${product.sellableUoms}" var="sellableUom1" begin="0" end="0">
						<c:set var="inventoryMul"><fmt:formatNumber value="${sellableUom1.inventoryMultiplier}" maxFractionDigits="0" /></c:set>
						<div class="multipleUomPriceText m-y-10"><span class="muomqty">${inventoryMul}&nbsp;
								<spring:theme code="multiple.uom.redesign.desktop.unit.total" />
							</span> (<span class="muomunitprice">${sellableUom1.unitPrice.formattedValue}</span>/
							<spring:theme code="multiple.uom.redesign.desktop.unit" />)
						</div>
					</c:forEach>
				</c:if>
			</div>
		</c:if> --%>
		<div class="col-xs-12 ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && hidelistCheck?'':'mobile-price'} 10a  ${showUOMDropdown?'pdpRedesignuom-sticky':''} ${hardscapeMoreOnWayMsg? ' hidden ': ''}">				
		</div>
		<div class="col-xs-12 mobile-addcart${notPurchasable? ' p-l-0-xs p-r-0-xs': ''}">	
			<div class="col-xs-2 showBulkCalculatorEntrypoint hidden-md hidden-lg hidden" id="showBulkCalculatorMob">
				<common:globalBulkCalcIcons iconName="mobileCalculatorIcon" />
			</div>
			<div id="mobile-addcart-qty-section" class="${notPurchasable? 'hide ': ''}col-xs-6 count-section ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 && hidelistCheck?'countsection-margin':''}"></div>
			<div class="col-xs-6 btn-section${notPurchasable? ' hide': ''}"></div>
        	<div class="flex-center restricted-purchase-alert text-gray-1${notPurchasable? '': ' hide'}">
            	This item is unavailable for purchase under your account due to company purchasing restrictions.
			</div>
		</div>
	</div>
	<div class="row hidden-xs pdp-sticky">
		<div class="row product-title-container">
			<div class="col-md-12">						
						<div class="${showUOMDropdown && empty product.bulkUOMPrice?'col-md-5':'col-md-7'} pdp-innerdiv ${showUOMDropdown ?'p-r-0':''}">	
							<div class="col-xs-12 brand-itemnumber"><strong>${fn:toUpperCase(product.productBrandName)} ${product.productBrandName ne null?'&nbsp; : &nbsp;':''}</strong>${product.itemNumber}</div>
							<ycommerce:testId
								code="productDetails_productNamePrice_label_${product.code}">
								<div class="col-md-12 ">
									<div class="pdp-product-name">
										<span>${product.name}</span>
									</div>
								</div>
							</ycommerce:testId>
					  </div>
					  <div class="${showUOMDropdown && empty product.bulkUOMPrice?'col-md-7 ':'col-md-5'} price-addcart-detail  ${showUOMDropdown?'price-addcart-detail-uom':''} ">
					  		<div class="col-md-5 hidden emptDivforspace"></div>
					  		<div class="${showUOMDropdown && empty product.bulkUOMPrice?'col-md-8 ':'col-md-7'} price-detail ${showUOMDropdown?'pdpRedesignuom-sticky':''} ${hardscapeMoreOnWayMsg? ' hidden ': ''}"></div>					  		
					  		<div class="addtocart-detail ${notPurchasable? ' hide ': ' '} ${showUOMDropdown && empty product.bulkUOMPrice?'col-md-3 padding0':'col-md-5 '}"></div>
					  </div>
				</div>
							<div class="cl"></div>
							
							<div class="col-md-12 pdp-navhead">
								<div class="col-md-7">
									<nav class="pdp-nav">
								<ul class="pdp-ul">
											<li class="overview active"><a href="JavaScript:void(0);" class="nav-link"><spring:theme code="product.product.details.overview"/></a></li>
											<li class="spec"><a href="JavaScript:void(0);" class="nav-link"><spring:theme code="product.product.details.spec"/></a></li>
											<li class="info"><a href="JavaScript:void(0);" class="nav-link productHighlightsClick"><spring:theme code="product.product.details.information"/></a></li>
								</ul>            
					</nav>
								</div>
								<div class="col-md-5 pdpStoreDetail text-right">
								</div>
							</div>
							

						</div>
		
	</div>

	<input type="hidden" value="${product.code}" id="productcode" />
	<input type="hidden" value="${product.itemNumber}" id="productitemNumber" /> 
	<input type="hidden" value="${product.level1Category}" id="level1" />
	<div class="${not product.multidimensional?'col-md-6':'col-md-5'} image-popup">
		<div class="row">
			<product:productGalleryThumbnail galleryImages="${galleryImages}" />
			<div class="${imageContainerSpacing} main-image-wrapper no-padding-xs ${not product.multidimensional?'p-l-0':''}"
				oncontextmenu="return false;">
				<!-- mobile only title -->

				<div class="row no-margin hidden-sm hidden-md hidden-lg">
					<div class="col-lg-12 pdp-title-wrapper ${isSimple? 'padding0': ''}">
						<div class="product-details ">
							<div class="row">
								<div
								class="${alignLinkRight} col-xs-12 product-link text-align-right">
								<span class="${alignLinkRight} product-link">
									<a
										href="javascript:void(0)" onclick="window.print()">
										<span
											class="glyphicon glyphiconRightAlign">
											<common:printIcon  iconColor="${iconColor}"/>
										</span>
									</a>
								</span>
								<a href="#" class="shareproductemail">
									<span class="glyphicon glyphiconRightAlign pad-lft-10 no-padding-lft-sm">
										<common:envelopeIcon iconColor="${iconColor}"/>
									</span>
									<span class="share-link hide">
										<spring:theme code="product.share.share" text="Share" />
									</span>
								</a>
								</div>
							</div>
							<div class="row product-title-container variant-pdt-title">
								<div class="col-xs-12 brand-itemnumber padding0 variant-pdt-number"><strong>${fn:toUpperCase(product.productBrandName)} ${product.productBrandName ne null?'&nbsp; : &nbsp;':''}</strong>${product.itemNumber}
								</div>
								<ycommerce:testId
									code="productDetails_productNamePrice_label_${product.code}">
									<div class="col-xs-12 padding0">
										<div class="pdp-product-name ${isSimple? 'product-name-color': ''}  pdp-productname">
											<span>${product.name}</span>
										</div>
									</div>
									<div
										class="col-xs-8 col-sm-6 col-lg-8 col-md-8  ${hideIfVariant}">${product.itemNumber}</div>

								</ycommerce:testId>

								
								<div class="description-wrapper">
									<span class="description">${fn:escapeXml(product.summary)}</span>
								</div>
								<div class="cl"></div>

							</div>
							</div>

						</div>
				</div>
				<!-- mobile only title ends -->

				<product:productImagePanel galleryImages="${galleryImages}"
					product="${product}" />
			</div>
			<div class="hidden-xs hidden-sm col-md-12 m-t-10 ${not product.multidimensional?'p-l-0':''}">
				<product:productDetailsHighlights />
			</div>
		</div>
	</div>
		<div class="clearfix hidden-sm hidden-md hidden-lg"></div>
		<div class=" ${middleContainerSpacing}  redesign-rgt-content">
		<div class="row m-b-10-xs hidden-md hidden-lg">
	<div class="col-md-12 col-xs-12 flex-center justify-center paddingTopB10 pdpAddtoListPopupBtn removesignInlink signInOverlay hidden-xs hidden-sm"><span class="m-r-5 pdp-popup-atl-btn"><spring:theme code="cartItems.addToList"/></span><common:filter-chevron-down iconColor="#4492B6" /></div>
	</div>
			<div class="product-main-info">

				<input type="hidden" name="createWishList" id="createWishList"
					value="${createWishList}" />
				<div class="row hidden-xs">
					<div class="col-lg-12 pdp-title-wrapper ${isSimple? 'padding0': ''}">
						<div class="product-details ">
						 <div class="row">
							<div class="col-xs-12 col-md-6 brand-itemnumber "><strong>${fn:toUpperCase(product.productBrandName)} ${product.productBrandName ne null?'&nbsp; : &nbsp;':''}</strong>${product.itemNumber}
						</div>
							<div
								class="${alignLinkRight} col-xs-12 col-md-6 product-link text-align-right">
								<span class="${alignLinkRight} product-link">
									<a
										href="javascript:void(0)" onclick="window.print()">
										<span
										class="glyphicon glyphiconRightAlign">
										<common:printIcon iconColor="${iconColor}"/>
										</span>
									</a>
								</span>
								<a href="#" class="shareproductemail">
									<span class="glyphicon glyphiconRightAlign ">
										<common:envelopeIcon iconColor="${iconColor}"/>
									</span>
									<span class="share-link hide">
										<spring:theme code="product.share.share" text="Share" />
									</span>
								</a>
							</div>
							</div>
							<div class="row product-title-container">
							<div class="col-xs-12 col-md-6 brand-itemnumber ${isSimple? 'hide': 'hide'}"><strong>${fn:toUpperCase(product.productBrandName)} ${product.productBrandName ne null?'&nbsp; : &nbsp;':''}</strong>${product.itemNumber}
							</div>
							<ycommerce:testId
								code="productDetails_productNamePrice_label_${product.code}">
									<div class="col-md-12 no-left-padding">
									<div class="padding0 pdp-product-name ${isSimple? 'product-name-color col-md-11': 'col-md-10'}">
										<h1 class="pdp-productNameTitle">${product.name}</h1>
									</div>
								</div>
								<div class="col-md-12 tab-details no-left-padding">
									<div class="pdpBulletPointLoaderSection tableRowLoader ${isSimple? '': 'hide'}"></div>
									<div class="featureBullets hidden" id="pdpBulletPointSection">
									<input type="hidden" id="pdpBulletPointProductType" name="pdpBulletPointProductType" value="${isSimple}">
										<c:choose>
											<c:when
												test="${not empty product.salientBullets}">
												${product.salientBullets}
											</c:when>
											<c:otherwise>
												${product.featureBullets}
											</c:otherwise>
										</c:choose>
									</div>
									<c:choose>
											<c:when
												test="${not empty product.holidayLightingCalcType}">
													<div class="featureBullets cal-link-top-mobile hidden" id="pdpBulletPointCalLink">	
														<ul>
															<li><a href="/en/projectcalculators/holidaylighting" target="_blank"><spring:theme code="pdp.holiday.calculator.link"/></a></li>
										 				</ul>
													</div>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty product.calcUrl}">
													<div class="featureBullets cal-link-top-mobile hidden" id="pdpBulletPointCalLink">	
														<ul>
															<li><a href="${product.calcUrl}" target="_blank"><spring:theme code="pdp.calculator.link"/></a></li>
										 				</ul>
													</div>
												</c:if>
											</c:otherwise>
									</c:choose>
									<div class="pdpBulletPointMoreDetails hidden" onclick="ACC.productDetail.triggerPDPBulletPointMoreDetails();"><spring:theme code="pdp.bulletpoint.moredetails"/></div>
								</div>
								<div
									class="col-xs-8 col-sm-6 col-lg-8 col-md-8  ${hideIfVariant}">${product.itemNumber}</div>

							</ycommerce:testId>

							
							<div class="description-wrapper">
								<span class="description">${fn:escapeXml(product.summary)}</span>
							</div>
							<div class="cl"></div>

						</div>
						</div>

					</div>
				</div>

				<div class="row ${showUOMDropdown?'pdpRedesignuom-wrapper':''}" id="${showUOMDropdown?'pdpRedesignuom-wrapper':''}">


					<div class="col-sm-12 col-md-12 col-lg-12 no-padding-xs ${isSimple? 'no-padding-pdp': ''}">
						<c:if test="${product.multidimensional}">
							<product:productVariantSelector />
						</c:if>
						<c:if test="${not product.multidimensional}">
							<product:productSimpleProduct />
						</c:if>

						<input type="hidden" id="measureUom" class="cartUomMeasure">
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-4">
								<c:set var="hideUomSelect" value="false" />
								<c:forEach items="${product.sellableUoms}" var="sellableUom1">
									<c:if test="${product.hideUom eq true}">
										<c:set var="hideUomSelect" value="true" />
										<c:set var="newuomDescription"
											value="${sellableUom2.inventoryUOMDesc}" />
										<c:set var="uomMeasure" value="${sellableUom1.measure}" />
									</c:if>
								</c:forEach>
								<c:if test="${product.singleUom eq true}">
									<c:set var="singleUom" value="true" />
									<c:set var="uomDescription"
										value="${product.singleUomDescription}" />
									<c:set var="uomMeasure" value="${product.singleUomMeasure}" />
								</c:if>
								<c:if
									test="${not empty product.sellableUoms && hideUomSelect ne true}">
									<div class="product-detail-uom">
										<label class="variant-label-name hide" for="uom-options"><b><spring:theme
													code="productDetailsPanel.unitOfMeasure" /></b></label>
									</div>
									<select id="uom-options" class="js-uom-selector-input hide"
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
								</c:if>
							</div>
						</div>
						<c:choose>
							<c:when
								test="${product.stock.stockLevelStatus.code eq 'inStock' and empty product.stock.stockLevel}">
								<c:set var="maxQty" value="FORCE_IN_STOCK" />
							</c:when>
							<c:otherwise>
								<c:set var="maxQty" value="${product.stock.stockLevel}" />
							</c:otherwise>
						</c:choose>
						
						<div class="hidden-md hidden-lg">
						<c:if test="${product.isRegulatoryLicenseCheckFailed eq false}">
							<div class="col-md-12 f-s-12 marginTop10" style="position:realtive;"><span class="text-orange"><spring:theme code="restricted.license.msg.pdp" /></span> 
									<span class="js-info-tootip blueLink" rel="custom-tooltip"><span class="tooltip-content hide">
											<span class=" f-s-12 bold"> <spring:theme code="restricted.license.msg.tooltip1" /> <br/>
											 <spring:theme code="restricted.license.msg.tooltip2" /></span>
										</span>
										<spring:theme code="show.details.msg" />
									</span>
									</div>
						</c:if>
						</div>					
						
						
						
						<div class="${rowClass} ${not product.multidimensional?'marginTop20':''}">
				<c:if test="${product.isRegulateditem}">
					<c:set var="statesCount" value="${fn:length(regulatoryStates)}" />
					<c:if test="${statesCount ne '0' && hideCSP ne true}">
						<span class="price-unavailable-wrapper"><span><spring:theme
									code="productDetailsPanel.notAvailable" /> <c:forEach
									var="regulatoryState" items="${regulatoryStates}"
									varStatus="loopStatus">
									<c:out value="${regulatoryState}" />
									<c:choose>
										<c:when test="${loopStatus.index eq statesCount - 2}"> and </c:when>
										<c:otherwise>
											<c:if test="${!loopStatus.last}">, </c:if>
										</c:otherwise>
									</c:choose>
								</c:forEach>. </span></span>
					</c:if>
				</c:if>
			</div>
						

						<div class="cl"></div>
						
						<div class="row">
							<div class="col-md-9 col-xs-12">
								<c:if test="${isHrscpDisclaimer eq 'true'}">
									<p>
										<spring:theme code="hrdscp.disclaimer.text" />
									</p>
								</c:if>
							</div>
						</div>


					</div>

				</div>
			</div>
			

			
		</div>
		<div class="hidden-md hidden-lg cl"></div>

<div class="hidden" id="listpopup">
		<div class="pdplistpopupnew">
			<div class=pdplistpopup1>
				<div class='pdp-atlpopupnew-list p-b-20'><spring:theme code="cartItems.addToList"/></div>
					<div class="border-grey margin-bot-10-md m-b-10-xs ${not empty allWishlist?'':'hidden'}">
					<div class="popupsavedlistoptions">
						<div class="popupoptionlistitem">
							<c:forEach var="wishlists" items="${allWishlist}">
								<div class="pdplistpopupoption hidden-xs" id="${wishlists.code}" data-value="${wishlists.code}" data-productcode="${product.itemNumber}">${wishlists.name}</div>
								<div class="pdplistpopupoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.productDetail.triggerpdplistpopupoption(${wishlists.code})">${wishlists.name}</div>
							</c:forEach>
						</div>
					</div>
					</div>
					
				<div class='input-group margin-bot-10-md m-b-10-xs'>
					<input class='form-control createnewlistinput' id="pdppopupinput" placeholder='<spring:theme code="addToSavedList.createList" />'/>
					<div class='input-group-btn'>
						<button class="bg-primary createnewlistbtn" value="createNewListLinkProduct" data-productcode="${product.code}">+</button>
					</div>
				</div>
				<div class="pdp-newlist-popup-error pdp-newlist-popup-error-text"><spring:theme code="saved.list.duplicate"/></div>
				<p class="pdp-emptynewlist-popup-error pdp-emptynewlist-popup-error-text"><spring:theme code="saved.list.empty"/></p>
				<div class="row listpopupbtn">
					<div class="col-md-6 col-xs-5">
						<button class="btn-default listpopupclosebtn"><spring:theme code="text.cart.removeAllPopup.cancel"/></button>
					</div>
					<div class="col-md-6 col-xs-7 mob-list-save-button">
						<button class="btn-primary listpopupsavebtn"><spring:theme code="pdp.addtolist.popup.save.and.continue"/></button>
					</div>
				</div>
			</div>
        </div>
		
	</div>
	</div>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 3, fn:length(req.requestURI)), req.contextPath)}" />
<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "Product",
  "name": "${fn:replace(fn:replace(fn:replace(fn:trim(product.name), '\\', '\\\\'),'\"', '\\\"'),'\'', '\\\\\'')}",
  "sku": "${product.code}",
  "brand": {
        "@type": "Brand",
        "name": "${product.productBrandName}",
		"logo": "https://www.siteone.com/medias/sitelogo.svg?context=bWFzdGVyfGltYWdlc3w5MTI4fGltYWdlL3N2Zyt4bWx8aW1hZ2VzL2hkYy9oZDMvODc5NjY1MDE3NjU0Mi5zdmd8YzliMzhlNjY1NTgwNjMwNWQyNmEwNTQzN2RiYzZlZjliODdkNmVjMDMxYzBlMWY4NTA4OGU5NGI3ODYxOGEzZQ"
   },
   <c:if test="${not empty product.productLongDesc}">
  "description": "${fn.replace(fn:replace(product.productLongDesc, '\"', '\\\"'), '\'', '\\\\\'')}",
  </c:if>
  "url": "${baseURL}${product.url}",
  "image": "https://www.siteone.com${!galleryImages.isEmpty() ? galleryImages.get(0).thumbnail.url : ''}",
 "offers":{
 <c:choose>
 <c:when test="${product.variantCount == 0}">
	"@type": "Offer",
     <c:choose>
     <c:when test="${empty product.customerPrice}">
		 "price": ${product.price.formattedValue != null ? fn:replace(fn:replace(product.price.formattedValue, "$", ""), ",", "") : ""},
     </c:when>
     <c:otherwise>
		 "price": ${ not empty product.customerPrice.value ? product.customerPrice.value : ""},
     </c:otherwise>
     </c:choose>
 </c:when>
 <c:otherwise>
 <c:if test="${product.priceRange.minPrice != null}">
	"@type": "AggregateOffer",
    "lowPrice": "${product.priceRange.minPrice != null ? product.priceRange.minPrice.value : product.price.value}",
    "highPrice": "${product.priceRange.maxPrice != null ? product.priceRange.maxPrice.value : product.price.value}",
</c:if>
 </c:otherwise>
 </c:choose>	 
 	"priceCurrency": "USD",
	"seller": {
		"@type": "Organization",
		"name": "SiteOne",
		"url": "https://www.siteone.com"
	},
	"category": "${superCategory}", 
	"availability": "${product.stock.stockLevelStatus}"
   }
}
</script>
<common:nearbyOverlay/>
<common:hardscapeOverlay/>
<common:requestQuotePopupPLPPDP />