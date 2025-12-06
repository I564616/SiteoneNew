<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>


<c:set var="productQuantity_plp" value="1"/> 
<c:if test="${product.orderQuantityInterval eq null || product.orderQuantityInterval eq '0' || product.orderQuantityInterval lt '0' || product.orderQuantityInterval eq ''}">
    <c:set var="productQuantity_plp" value="1" />
</c:if>
<c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval gt '0' && product.orderQuantityInterval ne ''}">
    <c:set var="productQuantity_plp" value="${product.orderQuantityInterval}" />
</c:if>
<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne ''}">
    <c:set var="productQuantity_plp" value="${product.minOrderQuantity}" />
</c:if>


  	<ycommerce:testId code="listplpqtysection">
                    <div class="listplp-qtysection">
                        <div class="plp-qty-container flex-center" id="productPLPPostQtysection_${product.code}">
                            <button class="minusQty flex-center border-rad-left" type="button" id="minusQty_${product.code}" onclick="ACC.product.plpPlusMinusQty(${product.code}, 'minus')"><common:minusIcon iconColor="#5A5B5D" /></button>
                                <input type="text" name="productPostPLPQty" id="productPLPPostQty_${product.code}" value="${productQuantity_plp}"
                                    class="form-control js-qty-selector-input js-qty-updateOne js-plp-qty js-plp-uomqtyinterval ${productQuantity_plp > product.nearestStoreStockLevel && product.eeee ? 'ohhandqty' : '' }"
                                    size="1" maxlength="5" data-productcode="${product.code}" data-nurseryproduct="${product.isNurseryProduct}"
                                    data-maxqty="${product.nearestStoreStockLevel}" data-min-qty="${product.orderQuantityInterval}"
                                    data-eeeevalue="${product.eeee}" data-min-orderqty="${product.minOrderQuantity}" data-isbaseuomproduct="${product.sellableUoms[0].parentInventoryUOMID}"/>
                            <button class="plusQty flex-center border-rad-right" type="button" id="plusQty_${product.code}" onclick="ACC.product.plpPlusMinusQty(${product.code}, 'plus')"><common:plusIcon iconColor="#5A5B5D" /></button>
                        </div>   
                    </div>     
    </ycommerce:testId>
    