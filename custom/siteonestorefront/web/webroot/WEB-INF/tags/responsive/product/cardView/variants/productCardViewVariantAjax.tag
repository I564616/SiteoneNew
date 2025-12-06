<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="productcode" required="true" type="java.lang.String" %>
<%@ taglib prefix="productItemVariant" tagdir="/WEB-INF/tags/responsive/product/cardView/variants" %>
<%@ taglib prefix="productItem" tagdir="/WEB-INF/tags/responsive/product/cardView" %>
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

<c:set var="variantsimple" value="false" />
<div class="cardview-variant-item-list" data-product-code="${productcode}">
		<c:set var="variantshowhide" value="true" />
        <c:forEach items="${baseVariantMap[productcode]}" var="variantProduct" varStatus="loop">
			<div class="row compare-and-atl flex-center-md productVariantSection-${productcode}-${variantProduct.code} a1 ${variantshowhide == true ? '' : 'hidden'}">
				<div class="col-xs-6 col-md-6">
                    <c:if test="${!isAnonymous}">
                        <div class="checkbox-container hidden-sm hidden-xs">
                            <input type="checkbox" class="custom-checkbox" />
                            <span class="checkbox-label">Select</span>
                        </div>
                    </c:if>
                </div>
				<div class="col-xs-6 col-md-6 plp-wish-list-container wishlistAddProLink-wrapper signInOverlay">
					<productItemVariant:productCardViewAddToSaveList product="${variantProduct}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
				</div>
			</div>
            <c:set var="variantshowhide" value="false" />
        </c:forEach>
</div>

<div class="cardview-variant-item" data-product-code="${productcode}">
<c:set var="variantshowhide" value="true" />

        <c:forEach items="${baseVariantMap[productcode]}" var="variantProduct" varStatus="loop">
            <input type="hidden" name="variantProd" value="${variantProduct.code}" />
            <input type="hidden" name="variant-codes-cardDropdown-${productcode}" class="variant-codes-cardDropdown-${productcode}" value="${variantProduct.code}" />
            <productItemVariant:productCardViewStockDataPrice productCode="${productcode}" variantProduct="${variantProduct}" variantShowHide="${variantshowhide}" variantSimple="${variantsimple}" />
            <c:set var="variantshowhide" value="false" />
        </c:forEach>
</div>