<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true"
	type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="tab-details">
	 <ycommerce:testId code="productDetails_content_label"> 

            <div class="productLongDesc2">
                ${product.productLongDesc}
                <div class="featureBullets">
                    <c:choose>
                        <c:when
                            test="${product.isNurseryProduct}">
                                <c:choose>
                                    <c:when
                                        test="${not empty product.featureBullets}">
                                             ${product.featureBullets}
                                </c:when>
                                <c:otherwise>
                                             ${product.salientBullets}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                 <c:when
                                    test="${not empty product.salientBullets}">
                                         ${product.salientBullets}
                                </c:when>
                                <c:otherwise>
                                         ${product.featureBullets}
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose> 
                </div>
                <c:if test="${product.isRegulateditem}">
                <div class="regulatory-disclaimer"><spring:theme code="text.product.regulatedItem.disclaimer"/></div>
                </c:if>
            </div>

    </ycommerce:testId>
</div>