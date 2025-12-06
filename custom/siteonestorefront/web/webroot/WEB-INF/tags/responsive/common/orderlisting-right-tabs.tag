<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="shipToSelectedAcc" required="true" type="java.lang.String" %>
<%@ attribute name="shipToradio" required="true" type="java.lang.String" %>

<c:forEach items="${listOfShipTos}" var="shipTo" begin = "0">
<c:set var="shipToCode" value="${fn:split(shipTo,' ')}"/>
<c:if test="${fn:contains(shipToSelectedAcc,shipToCode[0])}">
<c:set var="shipToName" value="${shipTo}"/>
</c:if>
</c:forEach>
<div class="btn btn-black-gray flex justify-between shipto-minwidth tabClick" data-target="ship-to-dropdown">
	<div class="overflow-hidden">
	<span class="valign-m"><common:headerIcon iconName="shipping" iconFill="none" iconColor="#77A12E" width="22" height="15" viewBox="0 0 22 16" /></span>
	<span class="f-s-14-xs-px f-s-16 f-w-400">Ship-to : ${shipToName}</span>
	</div>
	<span class="valign-m"><common:globalIcon iconName="down-arrow" iconFill="none" iconColor="#77A12E" width="13" height="8" viewBox="0 0 13 8" /></span>
</div>
<div class="ship-to-dropdown hidden">
<div class="black-title f-s-16 f-w-500 font-Geogrotesque m-t-10 ship-to-header"><spring:theme code="text.account.orderHistory.page.shipTo"/></div>
<hr class="m-y-10"/>
<div class="ship-to-accounts">
<c:forEach items="${listOfShipTos}" var="shipTo" begin = "0" end = "2">
<c:set var="shipToCode" value="${fn:split(shipTo,' ')}"/>
<div class="f-w-400 f-s-14">
<span class="colored-radio"><input type="radio" name="${shipToradio}" ${fn:contains(shipToSelectedAcc,shipToCode[0]) ? 'checked="checked"':''} value="${shipTo}"></span>
${shipTo}
</div>
</c:forEach>

</div>
<hr class="m-y-10"/>
<div class="ship-to-footer text-align-right">
<c:if test="${listOfShipTos.size() gt 3}">
<button type="button" class="font-size-14-btn bg-white btn btn-white btn-small f-s-14 f-w-700 moreShipTos"><spring:theme code="text.account.orderHistory.page.selectmoreShipTo"/></button>
</c:if>
<button type="button" class="font-size-14-btn btn btn-primary btn-small f-s-14 f-w-700 shipToAppBtn"><spring:theme code="text.account.orderHistory.page.ShipToApply"/></button>
</div>
</div>
