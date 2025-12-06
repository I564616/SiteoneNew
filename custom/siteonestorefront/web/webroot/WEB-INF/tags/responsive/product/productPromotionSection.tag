<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>


<div class="bundle col-md-12 pdp-details">
	<ycommerce:testId code="productDetails_promotion_label">
		<c:if test="${not empty product.potentialPromotions}">
		<c:choose>
				<c:when test="${not empty product.potentialPromotions[0].couldFireMessages}">
				<c:choose>
					<c:when test="${not empty product.couponCode}">
				<div class="cl"></div>
				<div class="promotion col-md-12 col-xs-12">
				<div class="flex-sec">
				<div class="col-md-3 paddingTB10 col-xs-5 padding0 dashed-border colored message-center">
						<span class="col-xs-5 col-md-3"><input type="checkbox" id="pdpPromotion" name="pdpPromotion" value="${product.couponCode}"></span>
						<span class="green-title col-xs-9 padding0"><spring:theme code="text.coupon.title" /></span>
				</div>
				<div class="triangle-top"></div>
				<div class="triangle-bottom"></div>
				<div class="col-md-9 paddingTB10 col-xs-7 message-center">
				<div class="col-md-11 col-xs-8">
				 ${product.potentialPromotions[0].couldFireMessages[0]}
				</div>
				</div>
				</div>
				<div class="cl"></div>
				 </div>
				</c:when>
				<c:otherwise>
				<div class="promotion"> <div class="paddingTB10">${product.potentialPromotions[0].couldFireMessages[0]}</div></div>
				</c:otherwise>
				</c:choose>
				</c:when>
		 <c:otherwise>
					<c:forEach items="${product.potentialPromotions}" var="promotion" varStatus="loop">
					 <c:if test="${loop.last}"> 
			<c:choose>
				<c:when test="${not empty product.couponCode}">
						<div class="cl"></div>
						<div class="promotion col-md-12 col-xs-12">
						<div class="flex-sec">
						<div class="col-md-3 paddingTB10 col-xs-5 padding0 dashed-border colored message-center">
						<span class="col-xs-5 col-md-3"><input type="checkbox" id="pdpPromotion" name="pdpPromotion" value="${product.couponCode}"></span>
						<span class="green-title col-xs-9 padding0"><spring:theme code="text.coupon.title" /></span>
						</div>
						<div class="triangle-top"></div>
						<div class="triangle-bottom"></div>
						<div class="col-md-9 paddingTB10 col-xs-7 message-center">
							<div class="col-md-11 col-xs-8">${promotion.description}</div>	
						 <a class="promotooltip pull-right"><spring:theme code="text.details.title" /> 
  						<span class="tooltiptext">${product.promoDetails}</span>
							</a>
							</div>
							</div>
							<div class="cl"></div>
						</div>					
				</c:when>
				<c:otherwise>
				<c:if test="${product.isPromoDescriptionEnabled}">
					<div class="promotion"> <div class="paddingTB10">${promotion.description}</div></div>
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
