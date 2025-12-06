<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="productcode" required="true" type="java.lang.String" %>
<%@ taglib prefix="productItemVariant" tagdir="/WEB-INF/tags/responsive/product/cardView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="loop" type="java.lang.String" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

		<c:forEach items="${baseVariantMap[productcode]}" var="variantProduct" varStatus="loop" begin="0" end="0">
            <c:set var="variantOptionObj" value="${variantProduct.variantProductOptions}" />
            <c:if test="${not empty variantOptionObj}">
              	<c:set var="splitValues" value="${fn:split(variantOptionObj, '|')}" />      
            </c:if>
            <c:set var="variantItemObj" value="${variantProduct.itemNumber}" />
        </c:forEach>
        <c:set var="option" value="OPTIONS" />
		<div class="row flex-center variantdropdown-plp variantdropdown-plp_${productcode} m-b-5">
			<div class="col-md-12 col-sm-12 col-xs-12 padding0">
				<div class="btn-group col-md-12 col-sm-12 col-xs-12 padding0 variant-wrapper-plp" role="group">
					<input type="hidden" class="DropdownClassAction_${productcode}" value=".popup-box-variant" />
					<input type="hidden" class="DropdownClassActive_${productcode}" value="active" />
					<div  id="js-btn-variant" class="col-md-3 col-sm-3 col-xs-3 js-btn-variant js-btn-variant_${productcode} btn btn-dark-gray flex-center font-Arial transition-3s variant-mesg-wrapper" onclick="ACC.global.toggleOffElemsVariantplp(this,${productcode})">
						${option} 
					</div>
					<div  id="js-btn-variant" class="col-md-9 col-sm-9 col-xs-9 js-btn-variant js-btn-variant_${productcode} btn btn-dark-gray flex-center font-Arial transition-3s variant-text-wrapper custom-dropdown-button" onclick="ACC.global.toggleOffElemsVariantplp(this,${productcode})">
						 <c:choose>
						        <c:when test="${not empty splitValues}">
						          <span class="span-text">${splitValues[1]}</span> 
						        </c:when>
						        <c:otherwise>
						           <span class="span-text">${variantItemObj}</span> 
						        </c:otherwise>
						   </c:choose>
					</div>
					<div class="row f-s-15 font-Arial popup-box-variant popup-box-variant_${productcode} js-document-global-box ">
						<div class="col-md-12 col-sm-12 col-xs-12 padding0">
							 <div onclick="ACC.global.redirectPDP(${productcode})"
									 class="list-group-item flex-center transition-3s variant-dropdown-option showmore_${productcode} hidden">
										<div class="icon-box m-r-10"></div>
							            <span class="span-text">Show More</span> 
							</div>								
							  <c:forEach items="${baseVariantMap[productcode]}" var="variantProduct" varStatus="loop">
							  		<input type="hidden" class="variantitemnumber_${variantProduct.code}" value="${variantProduct.itemNumber}" />
									<input type="hidden" class="variantitemdesc_${variantProduct.code}" value="${variantProduct.productShortDesc}" />
							  		<c:choose>
									<c:when test="${not empty variantProduct.variantProductOptions}">
											<input type="hidden" class="variantname_${variantProduct.code}" value="${variantProduct.variantProductOptions}" />         
											<input type="hidden" class="variantnameavailability_${variantProduct.code}" value="true" />  
									</c:when>
									<c:otherwise>
											 <input type="hidden" class="variantname_${variantProduct.code}" value="${variantProduct.itemNumber}" />           
											 <input type="hidden" class="variantnameavailability_${variantProduct.code}" value="false" /> 
									</c:otherwise>
									</c:choose>
									<div onclick="ACC.global.variantSelector(this,${productcode},${variantProduct.code},${variantProduct.sellableUoms[0].inventoryUOMID})"
									 class="list-group-item transition-3s variant-dropdown-option variant-dropdown-option_${variantProduct.code} variantDropdownSelectedOption ${loop.index eq 0 ?'selected':''}">
										<div class="icon-box m-r-10"></div>
							             <c:choose>
										        <c:when test="${not empty variantProduct.variantProductOptions}">
										           <c:set var="splitValues" value="${fn:split(variantProduct.variantProductOptions, '|')}" />
							              			<span class="span-text">${splitValues[1]}</span> 
										        </c:when>
										        <c:otherwise>
										            <span class="span-text">${variantProduct.itemNumber}</span>
										        </c:otherwise>
										   </c:choose>
										   <div class="variant-dropdown-option-stocksection a1 variantDropdownStockSection_${variantProduct.code}">
										   </div>
									</div>
								</c:forEach>	
						</div>
					</div>
				</div>
			</div>
		</div>	  
	