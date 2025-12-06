
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="variantProduct" required="false" type="de.hybris.platform.commercefacades.product.data.VariantOptionData" %>
<%@ attribute name="product" required="false"
	type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ attribute name="loopIndex" required="false" type="java.lang.Integer"%>
<%@ attribute name="parentVariantCategoryNameList" required="false" type="java.util.List"%>
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
	<c:when
		test="${product.productType ne 'Nursery' and parentVariantCategoryNameList.size() eq 2 }">
		<c:set var="adjustWidthLocation" value="col-md-4 varient1" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:when
		test="${product.productType ne 'Nursery' and parentVariantCategoryNameList.size() eq 1 }">
		<c:set var="adjustWidthLocation" value="col-md-4 varient2" />
		<c:set var="adjustWidthVariant" value="col-md-2" />
	</c:when>
	<c:when
		test="${product.productType eq 'Nursery' and parentVariantCategoryNameList.size() eq 1 }">
		<c:set var="adjustWidthLocation" value="col-md-3 varient3" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:when
		test="${product.productType eq 'Nursery' and parentVariantCategoryNameList.size() eq 3 }">
		<c:set var="adjustWidthLocation" value="col-md-3 varient4" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:when>
	<c:otherwise>
		<c:set var="adjustWidthLocation" value="col-md-4 varient5" />
		<c:set var="adjustWidthVariant" value="col-md-1" />
	</c:otherwise>
</c:choose>
<c:set var="isStockInNearby" value="true" />

<c:choose>
			<c:when test="${variantProduct.stockAvailableOnlyHubStore eq true}">
				<c:set var="variantStockLevel" value="${variantProduct.hubStoreStockLevel}"/>
			</c:when>
			<c:otherwise>
				<c:set var="variantStockLevel" value="${variantProduct.stock.stockLevel}"/>			
			</c:otherwise>
		</c:choose>
		<input type="hidden" id="variantisForceinstockpdp" value="${variantProduct.isForceInStock}"/>
		<c:if test="${variantProduct.isForceInStock eq true}">
		<c:set var="variantStockLevel" value="9999"/>	
		</c:if>
		
		<c:set var="uomMeasure" value="${variantProduct.singleUomMeasure}"/>

		<c:choose>
			<c:when test="${empty uomMeasure}">
				<c:set var="uomMeasureslash" value="" />
			</c:when>
			<c:otherwise>
				<c:set var="uomMeasureslash" value="/" />
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${loopIndex lt 7 }">
				<c:set var="hideRecords" value="" />
				<c:set var="hideLink" value="hide" />
			</c:when>
			<c:otherwise>
				<c:set var="hideLink" value="" />
				<c:set var="hideRecords" value="hidden" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${variantProduct.isAddToCartDisabled}">
				<c:set var="disableVariantatc" value="disabled" />
			</c:when>
			<c:otherwise>
				<c:choose>
			<c:when test="${variantProduct.hideList eq true}">
			   <c:if test="${variantProduct.hideCSP eq true}">
			  <c:set var="disableVariantatc" value="disabled" />
			  </c:if>
			</c:when>
			<c:otherwise>
			<c:choose>
			    <c:when test="${!isAnonymous && variantProduct.hideCSP eq true}">
			  <c:set var="disableVariantatc" value="disabled" />
			  </c:when>
			  <c:otherwise>
				<c:set var="disableVariantatc" value="" />
			</c:otherwise>
			</c:choose>
				</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<input type="hidden" class="variantProductCode" value="${variantProduct.code}"/>
		<c:set var="nonTransferrable" value="false" />
		<c:if test="${product.isTransferrable eq false && variantProduct.isStockInNearbyBranch eq true}">
			<c:set var="nonTransferrable" value="true" />
		</c:if>
		
		<c:set var="showBulkUom" value="false" />
		<c:forEach items="${variantProduct.sellableUoms}" var="sellableUom">
		<c:if test="${sellableUom.inventoryUOMDesc eq variantProduct.singleUomMeasure and sellableUom.inventoryMultiplier gt 1}">
			<c:set var="showBulkUom" value="true" />
		</c:if>
		</c:forEach>
		
		<div class="product-variant-table__values row ${hideRecords}">
			<c:choose>
				<c:when test="${isMixedCartEnabled}">
				  	<!-- Variant Number -->
					<div class="product-variant-table__data col-md-2 col-xs-6">
						<div class="product-variant-table__data--mobileHeader">
							<span class="hidden-md hidden-lg"><spring:theme code="text.variantProduct.item" />:</span>
							${variantProduct.itemNumber}
						</div>
					</div>
					<!-- Variant Size -->
					<c:set var="itemsAvailable" value="0" />
					<c:forEach items="${parentVariantCategoryNameList}" var="name" varStatus="loop">
						<c:set var="itemsAvailable" value="${itemsAvailable +1 }" />
						<div class="product-variant-table__data col-md-2 col-xs-6 no-padding-lft-xs" style="display:${(itemsAvailable == 1)? 'block': 'none'};">
							<div class="product-size-qty"><span class="hidden-md hidden-lg">${name}:</span>
								<input type="hidden" class="uom-name" value="${name}"/>
								<input type="hidden" class="uom-parameter" value="${variantProduct.variantValueCategory.get(name)}"/>
								<c:choose>
									<c:when test="${not empty variantProduct.variantValueCategory.get(name)}">${variantProduct.variantValueCategory.get(name)}</c:when>
									<c:otherwise>NA</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
				</c:when>
			  <c:otherwise>
			  <div class="col-sm-12 col-xs-12 product-redsg-varient-name-wrapper hidden-lg hidden-md product-xs-pad-lft-0">
				  <div class="product-variant-table__data col-md-3 col-sm-6 col-xs-6 product-variant-table__heading--item product-xs-pad-lft-0">
					<c:forEach items="${parentVariantCategoryNameList}" var="name" varStatus="loop">
							<div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-5 col-xs-5 bold">${name}</div>
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding-lg no-padding-md no-padding-sm product-size-qty product-xs-pad-lft-0"> 
							<input type="hidden" class="uom-name" value="${name}"/>
							<input type="hidden" class="uom-parameter" value="${variantProduct.variantValueCategory.get(name)}"/>
								<c:choose>
									<c:when test="${not empty variantProduct.variantValueCategory.get(name)}">${variantProduct.variantValueCategory.get(name)}</c:when>
									<c:otherwise>NA</c:otherwise>
								</c:choose>
							</div>
					</c:forEach>
						<div class="product-variant-table__data--mobileHeader">
							<div class="hidden-md hidden-lg hidden-sm hidden-xs col-sm-5 col-xs-5 bold">
								<spring:theme code="text.variantProduct.item" />
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding-lg no-padding-md no-padding-sm product-variant-table__data-itemnumber product-xs-pad-lft-0">${variantProduct.itemNumber}</div>
						</div>
					</div>
					<c:if test="${product.productType eq 'Nursery'}">
						<div class="product-variant-table__data col-md-1 col-sm-6 col-xs-6 product-variant-table__data--available product-redsg-variant-availble-value">
							<div class="col-md-12 col-sm-6 col-xs-6 product-xs-pad-lft-0 no-padding-lg no-padding-md no-padding-sm product-variant-table__data--availablevalue variant-available-right js-available-qty">${variantProduct.stock.stockLevel}</div>
							<div class="hidden-md hidden-lg col-sm-6 col-xs-6 variant-xs-pad-lft-5">
								<spring:theme code="text.variantProduct.available" />
							</div>
						</div>
					</c:if>
				</div>
			  	<!-- Variant Number -->
				<div class="product-variant-table__data col-md-3 col-sm-12 col-xs-6 product-variant-table__heading--item hidden-xs hidden-sm">
				<c:forEach items="${parentVariantCategoryNameList}" var="name" varStatus="loop">
						<div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-5 col-xs-5 bold">${name}</div>
						<div class="col-md-12 col-sm-7 col-xs-12 no-padding-lg no-padding-md no-padding-sm product-size-qty"> 
						<input type="hidden" class="uom-name" value="${name}"/>
						<input type="hidden" class="uom-parameter" value="${variantProduct.variantValueCategory.get(name)}"/>
							<c:choose>
								<c:when test="${not empty variantProduct.variantValueCategory.get(name)}">${variantProduct.variantValueCategory.get(name)}</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
						</div>
				</c:forEach>
					<div class="product-variant-table__data--mobileHeader">
						<div class="hidden-md hidden-lg hidden-sm hidden-xs col-sm-5 col-xs-5 bold">
							<spring:theme code="text.variantProduct.item" />
						</div>
						<div class="col-md-12 col-sm-7 col-xs-12 no-padding-lg no-padding-md no-padding-sm product-variant-table__data-itemnumber">${variantProduct.itemNumber}</div>
					</div>
					
					
					<div class="carousel ${not product.multidimensional?'gallery-carousel':'variant-product-gallery launch-image-popup-variant'} js-gallery-carousel hidden-xs hidden">
						
						<c:set var="remaining" value="0"/>
						<c:set var="first" value="1"/>
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop">                        
							<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
							<c:set var="remaining" value="${remaining+1}"/>	
							</c:if>
						</c:forEach>
						<c:set var="superZoomImg" value="0"/>
						<c:set var="largeImg" value="0"/>
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop"> 							
							<c:if test="${first eq 1 }">	
								<c:if test="${container.format eq 'superZoom' and container.imageType eq 'GALLERY'}">
									<c:set var="superZoomImg" value="${container.url}"/>
								</c:if>	
								<c:if test="${container.format eq 'pdpIcon' and container.imageType eq 'GALLERY'}">
									<c:set var="largeImg" value="${container.url}"/>
								</c:if>						
								<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
									<c:set var="first" value="0"/>	
									<a href="javascript:void(0)" class="item m-l-0 image-pdpvariant-humbnail"> 
										<img class="lazyOwl product-thumb pdp-thumbnail variant-img" src="${container.url}" data-src="${superZoomImg}" data-large-image="${largeImg}" data-zoom-image="${superZoomImg}" alt="${container.altText}" title="${container.altText}">
										<c:if test="${remaining gt 1 }">
											<span class="overlay-text-visible count-pdp-thumbnail">
												+${remaining - 1}
											</span>
										</c:if>              
									</a> 
								</c:if>
							</c:if>
						</c:forEach>
						
					<div class="hidden photoThumbImage">
						<c:set var="superZoom" value="0"/>
						<c:set var="thumbnail" value="0"/>     
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop">   
							                
							<c:if test="${container.format eq 'superZoom' and container.imageType eq 'GALLERY'}">
								<c:set var="superZoom" value="${container.url}"/>
							</c:if>
							<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
								<c:set var="thumbnail" value="${container.url}"/>
							</c:if>
							<c:if test="${container.format eq 'pdpIcon' and container.imageType eq 'GALLERY'}">
								<div class="col-xs-4 marginBottom10">
									<a href="#" class="popup-thumbnails"> 
										<img class="popup-product-thumb selected" src="${thumbnail}" data-src="${thumbnail}" data-large-image="${container.url}"
											data-zoom-image="${superZoom}"  alt="${container.altText}" data-title="${container.altText}"/>
									</a>
								</div>
							</c:if>
						</c:forEach>
					</div>
						<div class="hidden gallery-popup-container-variant">
							<product:productGalleryVariantPopup galleryImages="${variantProduct.images}"/>
						</div>
                    </div>
                    
				

				</div>
				<!-- Variant Size -->
				<c:forEach items="${parentVariantCategoryNameList}" var="name" varStatus="loop">
					<div class="hidden product-variant-table__data ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true ? 'col-md-1': adjustWidthVariant} col-sm-12 col-xs-12">
						<div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-5 col-xs-5 bold">${name}</div>
						<div class="col-md-12 col-sm-7 col-xs-7 no-padding-lg no-padding-md no-padding-sm product-size-qty"> 
						<input type="hidden" class="uom-name" value="${name}"/>
						<input type="hidden" class="uom-parameter" value="${variantProduct.variantValueCategory.get(name)}"/>
							<c:choose>
								<c:when test="${not empty variantProduct.variantValueCategory.get(name)}">${variantProduct.variantValueCategory.get(name)}</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
			
				<!-- Variant Quantity -->
				<c:if test="${product.productType eq 'Nursery'}">
					<div class="product-variant-table__data col-md-1 col-sm-6 col-xs-6 product-variant-table__data--available hidden-xs hidden-sm variant-md-pad-lft-0  ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true? 'hide':''}">
						<div class="col-md-12 col-sm-6 col-xs-6 no-padding-lg no-padding-md no-padding-sm product-variant-table__data--availablevalue js-available-qty">
						
						<c:if test="${variantProduct.isForceInStock ne true}">
						${variantProduct.stock.stockLevel}	
						</c:if>
						</div>
						<div class="hidden-md hidden-lg col-sm-6 col-xs-6 bold">
							<spring:theme code="text.variantProduct.available" />
						</div>
					</div>
				</c:if>
			  </c:otherwise>
			</c:choose>
			<!-- Variant Location -->
			<div class="product-variant-table__data ${isMixedCartEnabled? 'col-md-3': adjustWidthLocation} col-sm-12 col-xs-12 variant-md-padding0 product-variant-table__data--location variant-md-pad-rgt-0 product-xs-pad-lft-0">
				<div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-5 col-xs-5 bold ${isMixedCartEnabled? 'hidden-xs hidden-sm': ''}">
					<spring:theme code="text.variantProduct.location" />
				</div>
				<div class="${isMixedCartEnabled? 'col-md-12 col-xs-12 padding0': 'col-md-12 col-sm-12 col-xs-12 no-padding-lg no-padding-md no-padding-sm product-xs-pad-lft-0'}">
				<input type="hidden" class="variantProductFullfilledStoreType" value="${variantProduct.stock.fullfilledStoreType}"/>
				<c:set var="isVariantProductIsHomeStoreInventoryHit" value="${not empty variantProduct.stock.isHomeStoreInventoryHit and variantProduct.stock.isHomeStoreInventoryHit ne null ? variantProduct.stock.isHomeStoreInventoryHit : false}"/>
				<c:set var="isVariantProductIsForceInStock" value="${not empty variantProduct.isForceInStock and variantProduct.isForceInStock ne null ? variantProduct.isForceInStock : false}"/>
				<c:set var="isVariantProductIsStockInNearbyBranch" value="${not empty variantProduct.isStockInNearbyBranch and variantProduct.isStockInNearbyBranch ne null ? variantProduct.isStockInNearbyBranch : false}"/>
				<input type="hidden" class="variantProductIsForceInStock" value="${isVariantProductIsForceInStock}"/>
				<input type="hidden" class="variantProductIsHomeStoreInventoryHit" value="${isVariantProductIsHomeStoreInventoryHit}"/>
				<input type="hidden" class="variantProductIsStockInNearbyBranch" value="${isVariantProductIsStockInNearbyBranch}"/>
					<div class="${isMixedCartEnabled? 'flex-start-center-xs': 'flex-center'}">
						<c:choose>
							<c:when test="${variantProduct.inStockImage}">
								<c:choose>
									<c:when test="${variantProduct.isStockInNearbyBranch}">
										<common:checkmarkIcon iconColor="${isMixedCartEnabled? '#78a22f': '#ef8700'}" width="25"/>
									</c:when>
									<c:otherwise>
										<common:checkmarkIcon iconColor="#78a22f" width="25"/>
									</c:otherwise>
	
								</c:choose>
								<c:set var="storeName" value="${variantProduct.stock.storeName}"/>
								<c:set var="isNotInStock" value="false"/>
							</c:when>
							<c:when test="${variantProduct.notInStockImage}">
								<common:exclamatoryIcon iconColor="#ef8700" width="25"/>
								<c:set var="isNotInStock" value="false"/>
								<c:set var="storeName" value="${sessionStore.name}"/>
							</c:when>
							<c:when test="${variantProduct.outOfStockImage}">
								<common:crossMarkIcon iconColor="#5a5b5d" width="25"/>
								<c:set var="isNotInStock" value="true"/>
							</c:when>
							<c:when test="${(isVariantProductIsHomeStoreInventoryHit eq true || isVariantProductIsForceInStock eq true) && (isVariantProductIsStockInNearbyBranch eq false)}">
								<span class="icon1"><common:globalIcon iconName="exclamatoryIcon" iconFill="none" iconColor="#ed8606" width="24" height="23" viewBox="0 0 35 35" display="m-r-10-xs" /></span>
							</c:when>
							<c:otherwise>
								<c:set var="isNotInStock" value="false"/>
							</c:otherwise>
						</c:choose>
						<span class="pad-lft-10 pad-rgt-10">${variantProduct.stockAvailabilityMessage}</span>
					</div>
				</div>
				<div class="simple-check-near-stores col-md-10 col-xs-12 col-sm-12 product-xs-pad-lft-0 variant-md-pad-rgt-0 ${isMixedCartEnabled?'col-xs-3 padding0 flex-center':' ' }">
					<input type="hidden" class="variantProductCodeUpdate" value="${variantProduct.code}"/>
					<c:choose>
						<c:when test="${isMixedCartEnabled ne true}">
							<a class="no-text-decoration pdp-store-link"  href="javascript:void(0)"><spring:theme code="productDetailsPanel.changeBranch" /></a>
						</c:when>
						<c:otherwise>
							<c:if test="${!(variantProduct.hideList eq true && variantProduct.hideCSP eq true)}">
								<a class="no-text-decoration pdp-store-link js-nearby-pdp-variant-overlay" data-variant="${variantProduct.code}" href="javascript:void(0)"><spring:theme code="productDetailsPanel.changeBranch" /></a>
								<span class="hidden-md hidden-lg"><common:angleRight height="10.34" width="16" /></span>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-12 hidden-lg hidden-md variant-xs-padding0 hidden">
				<div class="carousel ${not product.multidimensional?'gallery-carousel':'variant-product-gallery launch-image-popup-variant'} js-gallery-carousel">
						
						<c:set var="remaining" value="0"/>
						<c:set var="first" value="1"/>
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop">                        
							<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
							<c:set var="remaining" value="${remaining+1}"/>	
							</c:if>
						</c:forEach>
						<c:set var="superZoomImg" value="0"/>
						<c:set var="largeImg" value="0"/>
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop"> 							
							<c:if test="${first eq 1 }">
								<c:if test="${container.format eq 'superZoom' and container.imageType eq 'GALLERY'}">
									<c:set var="superZoomImg" value="${container.url}"/>
								</c:if>	
								<c:if test="${container.format eq 'pdpIcon' and container.imageType eq 'GALLERY'}">
									<c:set var="largeImg" value="${container.url}"/>
								</c:if>
								<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
									<c:set var="first" value="0"/>	
									<a href="javascript:void(0)" class="item m-l-0 image-pdpvariant-humbnail"> 
										<img class="lazyOwl product-thumb pdp-thumbnail variant-img" src="${container.url}" data-src="${superZoomImg}" data-large-image="${largeImg}" data-zoom-image="${superZoomImg}" alt="${container.altText}" title="${container.altText}">
										<c:if test="${remaining gt 1 }">
											<span class="overlay-text-visible count-pdp-thumbnail">
												+${remaining - 1}
											</span>
										</c:if>              
									</a> 
								</c:if>
							</c:if>
						</c:forEach>						
                    
					<div class="hidden photoThumbImage">
						<c:set var="superZoom" value="0"/>
						<c:set var="thumbnail" value="0"/> 
						<c:forEach items="${variantProduct.images}" var="container" varStatus="loop">   
							                    
							<c:if test="${container.format eq 'superZoom' and container.imageType eq 'GALLERY'}">
								<c:set var="superZoom" value="${container.url}"/>
							</c:if>
							<c:if test="${container.format eq 'thumbnail' and container.imageType eq 'GALLERY'}">
								<c:set var="thumbnail" value="${container.url}"/>
							</c:if>
							<c:if test="${container.format eq 'pdpIcon' and container.imageType eq 'GALLERY'}">
								<div class="col-xs-4 marginBottom10">
									<a href="#" class="popup-thumbnails"> 
										<img class="popup-product-thumb" src="${thumbnail}" data-src="${thumbnail}" data-large-image="${container.url}"
											data-zoom-image="${superZoom}"  alt="${container.altText}" data-title="${container.altText}"/>
									</a>
								</div>
							</c:if>
						</c:forEach>
					</div>
                    <div class="hidden gallery-popup-container-variant">
							<product:productGalleryVariantPopup galleryImages="${variantProduct.images}"/>
						</div>
				</div>
			</div>
				<c:if test="${not empty variantProduct.stockAvailExtendedMessage && !nonTransferrable}">
					<div class="margin20 hidden-md hidden-lg col-xs-12 padding0 ${isMixedCartEnabled?'hidden-xs hidden-sm':'' }">
				 		<div class="availability-additional-message ${isNotInStock?'grey-bg hidden':'' }">${variantProduct.stockAvailExtendedMessage}</div>
					</div>
				</c:if>
				<c:if test="${nonTransferrable}">
					<div class="margin20 hidden-md hidden-lg col-xs-12 padding0">
				 		<div class="availability-additional-message">
							<spring:theme code="pdp.transfer.notavailable" />
							<p class="bold-text margin0"><span class="marginrgt5"><spring:theme code="pdp.transfer.purchaseNearby" /></span><span class="marginrgt5">${variantProduct.stock.storeName}</span></p>
						</div>
					</div>
				</c:if>
			</div>
			<c:if test="${not empty variantProduct.stockAvailExtendedMessage && isMixedCartEnabled}">
				<div class="product-variant-table__data hidden-md hidden-lg">
					<div class="margin20 hidden-md hidden-lg col-xs-12 padding0">
				 		<div class="availability-additional-message ${isNotInStock?'grey-bg hidden':'' }">${variantProduct.stockAvailExtendedMessage}</div>
					</div>
				</div>
			</c:if>
			<!-- Pricing Logic starts -->
				
						
			<div class="${product.productType eq 'Nursery' ? '' : 'varient-non-nursery-price'} varient-price1 ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true ? 'product-variant-bulk__price': 'product-variant-table__data'} col-sm-12 ${!isAnonymous ? 'pad-left-0':''} col-xs-12 ${(isMixedCartEnabled || not empty variantProduct.bulkUOMPrice  and showBulkUom eq true) ? 'col-md-2': 'col-md-2 product-variant-table__data--price'}">
				<div class="hidden-md hidden-lg hidden-sm hidden-xs col-sm-5 col-xs-5 bold ${isMixedCartEnabled? 'hidden-xs hidden-sm': ''}">
					<spring:theme code="text.variantProduct.Price" />
				</div>
				<input type="hidden" class="trackProductCode" name="trackProductCode" value="${variantProduct.code}">
	            <input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${variantProduct.priceData.value}">
	            <input type="hidden" class="trackCSP" name="trackCSP" value="${variantProduct.customerPrice.price}">
				<input type="hidden" class="quoteUomMeasure" name="quoteUomMeasure" value="${uomMeasure} ">
				<div class="${isMixedCartEnabled? 'price-block-heading': 'col-md-12 col-sm-12 col-xs-12 no-padding-lg no-padding-md no-padding-sm redsg-price-wrapper'}">
					<c:choose>
						<c:when test="${isAnonymous}">
						<!-- Anonymous scenario -->
							<c:choose>
								<c:when test="${variantProduct.hideList eq true and variantProduct.hideCSP eq true}">
									<div class="callBranchForPrice"> <spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}" />
									</div>
								</c:when>
								<c:otherwise>
								
								<c:if test="${variantProduct.hideList ne true and variantProduct.priceData ne null and variantProduct.inventoryFlag ne true}">
										<div class="col-xs-8 p-l-0 hidden-md hidden-lg visible-sm visible-xs ${isMixedCartEnabled? 'mixedcart-retail-price': ''}">
											<div class="col-xs-2 bold pdp-varient-retailprice"><spring:theme code="text.variantProduct.retailPrice"/></div> 
											<div class="col-xs-10 pdp-varient-retailpricevalue ${isMixedCartEnabled? 'bold price-each': ''} ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true? ' bold': ''}">
											$${variantProduct.priceData.value} ${uomMeasureslash} ${uomMeasure} 
											<span class="bulkuom-var3 bulkuomvariant">
											<c:if test="${not empty variantProduct.bulkUOMPrice and showBulkUom eq true}">
														(${variantProduct.bulkUOMPrice} / ${variantProduct.bulkUOMCode})
														
													</c:if></span></div>
										</div>
									</c:if>
									<c:if test="${variantProduct.hideList ne true and variantProduct.priceData ne null and variantProduct.inventoryFlag ne true}">
										<div class="p-l-0 hidden-xs hidden-sm visible-lg visible-md ${isMixedCartEnabled? 'mixedcart-retail-price': ''}">
											<div class="bold pdp-varient-retailprice"><spring:theme code="text.variantProduct.retailPrice"/></div> 
											<div class="pdp-varient-retailpricevalue ${isMixedCartEnabled? 'bold price-each': ''} ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true? ' bold': ''}">
											$${variantProduct.priceData.value} ${uomMeasureslash} ${uomMeasure} 
											<span class="bulkuom-var3 bulkuomvariant">
											<c:if test="${not empty variantProduct.bulkUOMPrice and showBulkUom eq true}">
														(${variantProduct.bulkUOMPrice} / ${variantProduct.bulkUOMCode})
														
													</c:if></span></div>
										</div>
									</c:if>
									<c:choose>
									<c:when test="${variantProduct.hideCSP ne true}">
									<c:choose>
									<c:when test="${variantProduct.inventoryFlag ne true}">
										<a href="<c:url value="/login"/>"
										class="logInToSeeYourPrice signInOverlay 1 col-xs-4 col-md-12 col-lg-12 variant-md-padding0 varient-mar-top-10"><spring:theme
											code="text.product.logInToSeeYourPrice" /></a>
									</c:when>
									<c:otherwise>
										<div class="callBranchForPrice">
											<spring:theme code="text.product.callBranchForPrice"
												arguments="${contactNo}" />
										</div>
									</c:otherwise>
									</c:choose>
									</c:when>
									<c:otherwise>
										<div class="callBranchForPrice">
											<spring:theme code="text.product.callBranchForPrice"
												arguments="${contactNo}" />
										</div>
									</c:otherwise>
									</c:choose>
									
									
								</c:otherwise>
							</c:choose>		
							<!-- Anonymous scenario ends-->					
						</c:when>
						<c:otherwise>
							<!-- Logged in scenario -->
							<c:choose>
								<c:when test="${variantProduct.hideList ne true}">
									<c:choose>
										<c:when test="${variantProduct.hideCSP ne true}">
											<c:choose>
												<c:when test="${variantProduct.inventoryFlag}">
													<div class="callBranchForPrice">
														<spring:theme code="text.product.callBranchForPrice"
															arguments="${contactNo}" />
													</div>
												</c:when>
												<c:otherwise>
													<div class="${isMixedCartEnabled? 'mixedcart-product-price': 'variant-product-retailprice-wrapper col-xs-6 col-sm-6 col-md-12 col-lg-12 padding0'}">
														<div class="col-xs-2 col-sm-2 col-md-12 col-lg-12 bold pdp-varient-retailprice padding0"><spring:theme code="text.variantProduct.yourPrice"/></div>
														<div class="col-xs-10 col-sm-10 col-md-12 col-lg-12 variant-md-padding0 pdp-varient-yourpricevalue ${isMixedCartEnabled? 'bold price-each': ''} ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true? ' bold': ''}">$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${variantProduct.customerPrice.price}" /> ${uomMeasureslash} ${uomMeasure} 
														<span class="bulkuom-var4 bulkuomvariant">
														<c:if test="${not empty variantProduct.bulkUOMPrice  and showBulkUom eq true}">
														(${variantProduct.bulkUOMCustomerPrice} / ${variantProduct.bulkUOMCode})
													
													</c:if></span></div>
													</div>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<div class="callBranchForPrice">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}" />
											</div>
										</c:otherwise>
									</c:choose>
									
									<!-- retail price -->
									<c:if test="${variantProduct.priceData ne null and variantProduct.inventoryFlag ne true}">
										<c:if test="${variantProduct.hideCSP eq true || (variantProduct.priceData.value gt variantProduct.customerPrice.price)}">
											<div class="varient-login-retail-price col-xs-6 col-sm-6 col-md-12 col-lg-12 padding0 ${isMixedCartEnabled? 'mixedcart-retail-price': ''}">
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 bold pdp-varient-retailprice padding0"><spring:theme code="text.variantProduct.retailPrice"/></div>
												<div class="${isMixedCartEnabled? 'bold price-each ': ''} ${variantProduct.hideCSP eq true?'':'text-strike-through col-xs-12 col-sm-12 col-md-12 col-lg-12 variant-md-padding0 pdp-varient-retailpriceloginvalue'} ${not empty variantProduct.bulkUOMPrice and showBulkUom eq true? ' bold': ''}">
												$${variantProduct.priceData.value} ${uomMeasureslash} ${uomMeasure}
												</div>
											</div>
										</c:if>
									</c:if>
									<!-- retail price ends -->	
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${variantProduct.hideCSP ne true}">
											<c:choose>
												<c:when test="${variantProduct.inventoryFlag}">
													<div class="callBranchForPrice">
														<spring:theme code="text.product.callBranchForPrice"
															arguments="${contactNo}" />
													</div>
												</c:when>
												<c:otherwise>
													<div class="variant-product-retailprice-wrapper col-xs-6 col-sm-6 col-md-12 col-lg-12 padding0">
														<div class="col-xs-2 col-sm-2 col-md-12 col-lg-12 bold pdp-varient-retailprice padding0"><spring:theme code="text.variantProduct.yourPrice"/></div>
														<div class="col-xs-10 col-sm-10 col-md-12 col-lg-12 variant-md-padding0 pdp-varient-yourpricevalue ${not empty variantProduct.bulkUOMPrice  and showBulkUom eq true? ' bold': ''}">
														$
															<fmt:formatNumber maxFractionDigits="${totalpriceDigits}"
																minFractionDigits="${totalpriceDigits}"
																value="${variantProduct.customerPrice.price}" /> ${uomMeasureslash} ${uomMeasure}
																<span class="bulkuom-var2 bulkuomvariant">
																<c:if test="${not empty variantProduct.bulkUOMPrice  and showBulkUom eq true}">
														(${variantProduct.bulkUOMCustomerPrice} / ${variantProduct.bulkUOMCode})
													
													</c:if></span>
														</div>
													</div>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<div class="callBranchForPrice">
												<spring:theme code="text.product.callBranchForPrice"
													arguments="${contactNo}" />
											</div>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<!-- Logged in scenario -->
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!-- Pricing Logic ends -->
			<div
				class="product-variant-table__data col-md-3 col-xs-12 col-sm-12 product-variant-table__data--atc">
				<div class="qty-atc-wrapper">
					<div class="hidden-xs">
						<img class="icon-red-exclamation qty-alert"
							src="${themeResourcePath}/images/Exclamation-point.svg"
							title="<spring:theme code="text.product.qty.alert" arguments="${variantProduct.stock.stockLevel}" />"
							alt="" style="display: none" />
					</div>
			<c:choose>
				<c:when test="${isNotInStock}">
					<div class="col-xs-12 col-sm-12 hidden col-md-12 padding0 m-b-15-xs m-t-10-xs">
							<div class="flex-center justify-center text-center call-branch-container  bg-lightGrey ${isNotInStock?'contact-notinstock':'' }">
								
								<div class="">
									<div class="call-branch-icon-text"><spring:theme code="text.contact.your.branch"/></div>
									<div class="call-branch-icon-number">${contactNo}</div>
								</div>
							</div>
					</div>
														
				</c:when>
				<c:when test="${nonTransferrable}">
					<div class="col-xs-12 padding10 text-center text-green bold-text f-s-13 bg-lightGrey m-b-15-xs m-t-10-xs">
						<spring:theme code="text.change.your.branch"/>
					</div>								
				</c:when>
				<c:otherwise>
					<div class="hidden-lg hidden-md hidden-sm"><div id="qtyError" style="display:none"></div></div>
					<div class="pdp-qty-atc-container col-sm-6 col-xs-6 p-l-0">
            		<div class="vertical-label hidden-xs"><spring:theme code="text.account.savedCart.qty" /></div> 
		            	<div class="xs-qty-container js-qty-selector">
		            	
		            	<button class="minusQty pdp-qtyBtn hidden-sm hidden-md hidden-lg" type="button" id="minusQty_${variantProduct.code}" ${disableVariantatc}><common:minusIcon iconColor="#77A12E" /></button>
        				<input type="text" maxlength="5" class="form-control js-qty-selector-input pdp-variant-qty"
							size="1" value="1" name="pdpAddtoCartInput" data-available-qty="${variantStockLevel}" data-is-nursery="${product.productType eq 'Nursery'}"
							id="pdpAddtoCartInput" ${disableVariantatc}  data-productcode="${variantProduct.code}"/>
						<button class="plusQty pdp-qtyBtn hidden-sm hidden-md hidden-lg" type="button" id="plusQty_${variantProduct.code}" ${disableVariantatc}><common:plusIcon iconColor="#77A12E" /></button>
						</div>
					</div>
					<c:choose>
						<c:when test="${(isAnonymous and (variantProduct.hideList eq true or empty variantProduct.priceData) and variantProduct.hideCSP ne true) or (isAnonymous and isGuestCheckoutEnabled eq false)}">
							<div class="col-sm-6 col-xs-6 col-md-8 variant-pad-rgt-0 variant-md-padding0">
							<button type="submit" data-prod-code="${variantProduct.code}" class="btn btn-primary btn-block product-variant-table__data--atcbtn js-login-to-buy"><spring:theme code="login.to.buy" /></button>
							</div>
						</c:when>
						
						<c:otherwise>
							<div
								class="product-variant-atc-wrapper col-sm-6 col-xs-6">
								<c:url value="/cart/add" var="addToCartUrl" />
			
								<form:form method="post" id="addToCartForm"
									class="add_to_cart_form" action="${addToCartUrl}">
									<input type="hidden" maxlength="3" size="1" id="qty" name="qty"
										class="qty js-qty-selector-input qty-hidden-variant" value="1">
									<input type="hidden" name="productCodePost"
										value="${variantProduct.code}" />
										<input type="hidden" name="storeId" value="${variantProduct.stock.fullfillmentStoreId}"/>
										<input type="hidden" id="isCouponEnabled" name="isCouponEnabled" value="false">	
										<c:set var="newuominventoryuomid" value=""/>									
										<c:forEach items="${variantProduct.sellableUoms}" var="sellableUom2">
											<c:if test="${variantProduct.hideUom eq true}">
												<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
											</c:if>
										</c:forEach>
										<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
			                       <input type="hidden" id="promoProductCode" name="promoProductCode" value="">
									<button type="submit" id="showAddtoCart" ${disableVariantatc}
										class="btn btn-primary btn-block product-variant-table__data--atcbtn js-atc-${variantProduct.code}"
										data-available-qty="${variantStockLevel}">
										<spring:theme code="basket.add.to.basket" />
									</button>
								</form:form>
								
							</div>	
						</c:otherwise>
					</c:choose>				
				</c:otherwise>
				</c:choose>
					
				</div>
				<div class="variantWishList">
				<c:if test="${quotesFeatureSwitch && variantProduct.outOfStockImage}">
					<button class="btn btn-primary btn-varientQuote" data-product-description="" onclick="ACC.savedlist.requestQuotePopupplp(this,'product-variant-table__values','${variantProduct.code}')"><spring:theme code="request.quote.popup.request.text" /></button>
				</c:if>
				<c:set var="producVarData" value="${variantProduct}" scope="session" />
					<product:addToSaveListVariant product="${variantProduct}"
							wishlist="${not empty allWishlist ? allWishlist : sessionScope.allWishlist}" wishlistName="${not empty wishlistName ? wishlistName : sessionScope.wishlistName}"/>
					</div>
			</div>
			<c:if test="${not empty variantProduct.stockAvailExtendedMessage && !nonTransferrable}">
				<div class="marginTop10 text-center  hidden-xs hidden-sm col-xs-12 p-r-0 p-l-0">
				 		<div class="availability-additional-message ${isNotInStock?'grey-bg hidden':'' }">${variantProduct.stockAvailExtendedMessage}</div>
				</div>
			</c:if>
			<c:if test="${nonTransferrable}">
				<div class="hidden-xs col-xs-12 marginTop10 text-center padding0">
					<div class="availability-additional-message">
						<spring:theme code="pdp.transfer.notavailable" />
						<p class="bold-text margin0"><span class="marginrgt5"><spring:theme code="pdp.transfer.purchaseNearby" /></span><span class="marginrgt5">${variantProduct.stock.storeName}</span></p>
					</div>
				</div>
			</c:if>
			<div class="cl"></div>
			<div class="bundle variant-promotion col-md-12">
			<input type="hidden" value="${variantProduct.code}" id="productcodevariant" />
				<ycommerce:testId code="productDetails_promotion_label">
					<c:if test="${not empty variantProduct.potentialPromotions}">
					<c:choose>
							<c:when test="${not empty variantProduct.potentialPromotions[0].couldFireMessages}">
							<c:choose>
								<c:when test="${not empty variantProduct.couponCode}">
							<div class="cl"></div>
							<div class="promotion col-md-12 col-xs-12">
								<div class="flex-sec">
									<div class="col-md-3 paddingTB10 col-xs-5 padding0 dashed-border colored message-center">
									<span class="col-xs-5 col-md-3"><input type="checkbox" id="pdpPromotionVariant" name="pdpPromotion" value="${variantProduct.couponCode}"></span>
									<span class="green-title col-xs-9"><spring:theme code="text.coupon.title" /></span>
									</div>
									<div class="triangle-top"></div>
									<div class="triangle-bottom"></div>
									<div class="col-md-9 paddingTB10 col-xs-7 message-center"> ${product.potentialPromotions[0].couldFireMessages[0]}</div>
								</div>
								<div class="cl"></div>
							</div>
							</c:when>
							<c:otherwise>
							<div class="promotion"> <div class="paddingTB10"> ${variantProduct.potentialPromotions[0].couldFireMessages[0]}</div></div>
							</c:otherwise>
							</c:choose>
							</c:when>
					<c:otherwise>
								<c:forEach items="${variantProduct.potentialPromotions}" var="promotion" varStatus="loop">
								<c:if test="${loop.last}"> 
								<c:choose>
								<c:when test="${not empty variantProduct.couponCode}">
									<div class="cl"></div>
									<div class="promotion col-md-12 col-xs-12">
									<div class="flex-sec">
									<div class="col-md-3 paddingTB10 col-xs-5 padding0 dashed-border colored message-center">
									<span class="col-xs-5 col-md-3"><input type="checkbox" id="pdpPromotionVariant" name="pdpPromotion" value="${variantProduct.couponCode}"></span>
									<span class="green-title col-xs-9 padding0"><spring:theme code="text.coupon.title" /></span>
									</div>
									<div class="triangle-top"></div>
									<div class="triangle-bottom"></div>
									<div class="col-md-9 paddingTB10 col-xs-7 message-center">
										<div class="col-md-11 col-xs-8">${promotion.description}	</div>	
									<a class="promotooltip pull-right"><spring:theme code="text.details.title" /> 
									<span class="tooltiptext">${variantProduct.promoDetails}</span>
										</a>
										</div>
										</div>
										<div class="cl"></div>
									</div>					
							</c:when>
							<c:otherwise>
							<c:if test="${variantProduct.isPromoDescriptionEnabled}">
								<div class="cl"></div><div class="promotion"> <div class="paddingTB10">${promotion.description} </div><div class="cl"></div></div>
								</c:if>
							</c:otherwise>
						</c:choose>
						</c:if>
								</c:forEach>
					</c:otherwise>
				</c:choose>
					</c:if>
				</ycommerce:testId>
			</div>
</div>
