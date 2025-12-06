<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:choose>
		<c:when test="${not product.multidimensional}">
		<c:set var="isSimple" value="true" />
		</c:when>
		<c:otherwise>
		<c:set var="isSimple" value="false" />
		</c:otherwise>
</c:choose>

<div class="row pdp-bottom-section" style="position:relative;">
  <div class="emailAndInfo col-sm-12 col-md-12 col-lg-12 share-product-link hidden">
    <div class="row showinlineifvariant">
      <span class=" product-link"><a href="#" onClick="OOo.oo_launch(event, 'oo_product')"><span
            class="glyphicon glyphiconRightAlign glyphicon-comment flipIcon"></span><span class="product-details-link">
            <spring:theme code="productDetailsPanel.helpUs" /></span></a></span> </span>
    </div>

  </div>
  <c:if test="${algonomyRecommendationEnabled}">
  <div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotPDPTop">
	</div>
	</c:if>
	
	<c:if test="${product.code eq '122148'}">
	<div class="cl"></div>
	<div class="col-md-12 rich-content hidden-xs hidden-sm">
		<div class="col-md-2 m-r-15 padding0">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_exposure_full_shade.png" width="205px" height="201px">
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.full.sun" /></div>
		</div>
		<div class="col-md-2 m-r-15 padding0">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_soil_tolerance_drought.png" width="205px" height="201px">
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.soil.tolerance.drought" /></div>
		</div>
		<div class="col-md-2 m-r-15 padding0">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_soil_moisture_moderate.png" width="205px" height="201px">
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.soil.moisture.moderate" /></div>
		</div>
		<div class="col-md-2 m-r-15 padding0">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_growth_fast.png" width="205px" height="201px">
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.fast.growing" /></div>
		</div>
		<div class="col-md-2 m-r-15 padding0">
			<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_maintenance_low.png" width="205px" height="201px">
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.maintenance.low" /></div>
		</div>
		<div class="col-md-2 padding0">
			<div class="rich-content-zone-wrapper">
			<div class="rich-content-zone-text"><spring:theme code="text.rich.content.zone" /></div>
			<div class="rich-content-zone-num"><spring:theme code="text.rich.content.zone.number" /></div>
			</div>
			<div class="rich-content-climate-text"><spring:theme code="text.rich.content.warm.climate" /></div>
		</div>
	</div>
	<div class="cl"></div>
	</c:if>
	
  <c:if test="${not empty product.productLongDesc || not empty mapConfigurableAttributes || not empty product.dataSheetList}">
  <div class="tab-content col-md-12">
  <div class="tab-content__content">
	<%-- <c:if test="${not empty product.productLongDesc}">
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
	</c:if> --%>

    
      <c:if test="${not empty mapConfigurableAttributes}">
      	<div class="tab-content__content-section">
		<div class="product-feedback-label hidden-sm hidden-xs"><spring:theme code="product.product.feedback.label" /></div>
        <div class="tab-content__content__title flex-center justify-between specDiv">
			<div><spring:theme code="product.product.spec" /></div>
			<div class="suggest-an-edit">
				<a href='/productfeedback?entry=%7B"Item":"${product.itemNumber}"%7D' target="_blank">
					<span class="glyphicon glyphicon-edit m-r-5"></span><span><spring:theme code="product.product.suggest.edit" /></span>
				</a>
			</div>
        </div>
        <div class="row">
          <div class="col-md-12 col-lg-12 padding0">
            <div class="tab-container">
              <product:productDetailsClassifications product="${product}" />
            </div>
          </div>
        </div>
        </div>
      </c:if>

     

	<c:if test="${not empty product.dataSheetList}">
    <div class="tab-content__content-section">
		
       <c:set var="isPdf" value="false" />
       <c:forEach items="${product.dataSheetList}" var="dataSheetMap" varStatus="loop">
			<c:forEach items="${dataSheetMap.value}" var="dataSheet" varStatus="loop">
		          <c:if test="${dataSheet.mimeType eq 'application/pdf'}">
		            <c:set var="isPdf" value="true" />
		          </c:if>
        	</c:forEach>
        </c:forEach>

        <c:if test="${isPdf}">
          <div class="tab-content__content__title flex-center infoDiv">
              <spring:theme code="product.product.informationAndGuides" />
          </div>
         
          <div class="tab-container">
            <product:productDetailsInformationAndGuides product="${product}" />           
          </div>
  
         </c:if>
         
         <c:forEach items="${product.dataSheetList}" var="dataSheetMap" varStatus="loop">
			<c:forEach items="${dataSheetMap.value}" var="dataSheet" varStatus="loop">
          <c:if test="${dataSheet.mimeType eq 'application/octet-stream' || dataSheet.mimeType eq 'video/mp4'}">
            <div class="specifications-video-container">
              <video id="sampleMovie" width="640" height="360" preload controls>
                <source type="video/mp4" src="${dataSheet.url}" />
              </video>
            </div>
          </c:if>        
        </c:forEach>
        </c:forEach>
         </div>
       </c:if>


	



</div>
</div>
</c:if>

<c:if test="${product.code eq '122148'}">
<div class="cl"></div>
<div class="col-md-12 hidden-xs hidden-sm rich-content-climate-zone-wrapper">
	<div class="rich-content-climate-zone-text"><spring:theme code="text.rich.content.climate.zones" /></div>
	<div class="col-md-8">
		<img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich_full_zones_map.jpg" class="rich-content-climate-zone-img" width="774px" height="540px">
	</div>
	<div class="col-md-4"><img alt="SiteOne Logo" src="/_ui/responsive/theme-lambda/images/rich-content.png" class="rich-content-climate-zone-temp" width="194px" height="400px"></div>
</div>
<div class="cl"></div>
</c:if>
	
</div>