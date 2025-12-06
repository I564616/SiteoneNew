<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="priceData" required="true" type="de.hybris.platform.commercefacades.product.data.PriceData" %>
<%@ attribute name="displayFreeForZero" required="false" type="java.lang.Boolean" %>
<%@ attribute name="unitPrice" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- Tag to render a currency formatted price. Includes the currency symbol for the specific currency. --%>
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator reset to current locale in the end of page --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\" currency.unitprice.formattedDigits\")%>"/>
<c:choose>
    <c:when test="${unitPrice}">
        <c:choose>
            <c:when test="${priceData.value > 0}">
                <span class="atc-price-analytics" data-from="1">$
                    <fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${priceData.value}"/>
                </span>
            </c:when>
            <c:otherwise>
                <c:if test="${displayFreeForZero}">
					<!-- if 1 -->
                    <spring:theme code="text.free" text="FREE"/>
                </c:if>
                <c:if test="${not displayFreeForZero}">
                    <span class="atc-price-analytics" data-from="2">$
                        <fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${priceData.value}"/>
                    </span>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${priceData.value > 0}">
                <span class="atc-price-analytics" data-from="3">
                    <c:set var="formattedValue" value="${priceData.formattedValue}"/>
                    <c:set var="valueWithDollar" value="$${priceData.value}"/>
                    ${not empty formattedValue ? formattedValue : valueWithDollar}
                </span>
                <input type="hidden" name="formattedValue" value="${formattedValue}"/>
                <input type="hidden" name="valueWithDollar" value="${valueWithDollar}"/>
            </c:when>
            <c:otherwise>
                <c:if test="${displayFreeForZero}">
					<!-- if 2 -->
                    <spring:theme code="text.free" text="FREE"/>
                </c:if>
                <c:if test="${not displayFreeForZero}">
                    <c:set var="formattedValue" value="${priceData.formattedValue}"/>
                    <c:set var="valueWithDollar" value="$${priceData.value}"/>
                    <span class="atc-price-analytics" data-from="4">${not empty formattedValue ? formattedValue : valueWithDollar}</span>
                    <input type="hidden" name="formattedValue" value="${formattedValue}"/>
                    <input type="hidden" name="valueWithDollar" value="${valueWithDollar}"/>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>