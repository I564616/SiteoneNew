<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="shipToAccount" required="true" type="java.lang.String" %>

<c:forEach items="${listOfShipTos}" var="shipToacc" varStatus="loop">
<c:if test="${loop.index == 0}">
<c:set var="shipToSelected" value="${shipToacc}" />
</c:if>
</c:forEach>

<c:set var="shipToSelectedAcc" value="${shipToAccount ne '' ? shipToAccount : shipToSelected}" />

			
<div class="flex order-tab justify-between m-b-10 hidden-sm hidden-xs">
<div class="left-tabs">
	<div class="btn btn-black-gray tabClick" data-target="search-dropdown">
	<span class="valign-m"><common:headerIcon iconName="search" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 17 17" /></span>
	<span class="f-s-16 f-w-400">Search By</span>
	</div>
	<div class="search-dropdown padding-20 hidden">
	<input type="hidden" id="dateSort" value="by90days"/>
	<input type="hidden" id="accountShipTo" name="accountShiptos" value="${shipToSelectedAcc}"/>
	<input type="hidden" name="paymentType" value="ALL"/>
	<input type="hidden" name="sort" value="byOrderNumber"/>
	<input type="hidden" name="sortOrder" value="desc"/>
	<div class="black-title f-s-20-xs-px f-w-600 font-Geogrotesque hidden-lg hidden-md text-center-xs">Search</div>
	<hr class="hidden-lg hidden-md"/>
	<div class="black-title f-s-16 f-w-500 font-Geogrotesque m-b-10-xs f-s-20-xs-px">Search by Order, Invoice or PO #</div>
	<hr class="m-y-5 hidden-sm hidden-xs"/>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" checked onclick="ACC.order.orderDropdownSearchType(this, 'Order', 'oSearchParam', '.searchOrderHistory')" /></span></label>Order</div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" onclick="ACC.order.orderDropdownSearchType(this, 'Invoice', 'iSearchParam', '.searchOrderHistory')"/></span></label>Invoice</div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" onclick="ACC.order.orderDropdownSearchType(this, 'PO', 'pnSearchParam', '.searchOrderHistory')"/></span></label>PO</div>
	<div class="form-group m-t-10"><input type="text" placeholder="Enter order, invoice, or PO#" id="searchOrderHistory" name="oSearchParam" value="${searchParam}" class="form-control searchOrderHistory" /></div>
	<hr class="m-y-5"/>
	<div class="black-title f-s-16 f-w-500 font-Geogrotesque m-y-10 f-s-20-xs-px">Search by Date</div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by30days" /></span></label><spring:theme code="order.datesort30" /></div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by60days"/></span></label><spring:theme code="order.datesort60" /></div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by90days" checked /></span></label><spring:theme code="order.datesort90" /></div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by184days"/></span></label><spring:theme code="order.datesort183" /></div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by365days"/></span></label><spring:theme code="order.datesort365" /></div>
	<div class="m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" class="sbyData" name="dateSort" data-datesort="${dateSort}" value="by730days"/></span></label><spring:theme code="order.datesort730" /></div>
	<hr class="m-b-5"/>
	<div class="flex flex-align-center justify-between hidden-sm hidden-xs">
	<input type="reset" value="Clear all" class="btn btn-link text-gray article-link text-hover-primary"/>
	<input type="submit" class="btn btn-primary btn-submit f-s-14-imp" value="Search">
	</div>
	<div class="flex flex-align-center hidden-lg hidden-md justify-between m-t-20-xs">
	<div class="col-xs-6">
	<input type="reset" value="Clear All" class="btn btn-default full-width"/>
	</div>
	<div class="col-xs-6">
	<input type="submit" class="btn btn-primary full-width" value="Search">
	</div>
	</div>
	</div>
	<div class="btn btn-black-gray hidden">
	<span class="valign-m"><common:headerIcon iconName="user" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 23 24" /></span>
	<span class="f-s-16 f-w-400">Contact</span>
	</div>
	<div class="btn btn-black-gray hidden">
	<span class="valign-m"><common:headerIcon iconName="order" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 23 24" /></span>
	<span class="f-s-16 f-w-400">Order Status</span>
	</div>
	<div class="btn btn-black-gray hidden">
	<span class="valign-m"><common:deliveryIcon iconColor="#77A12E" width="30" height="15" /></span>
	<span class="f-s-16 f-w-400">Fulfillment Type</span>
	</div>
</div>
<div class="right-tab">
<common:orderlisting-right-tabs shipToSelectedAcc="${shipToSelectedAcc}" shipToradio="shipToAc"/>
</div>

</div>	
<div class="row m-x-5-neg-sm hidden-lg hidden-md">
	<div class="col-xs-6 p-l-0 p-r-5">
	<div class="bg-white btn btn-white full-width mob-searchby">
		<span class="valign-m"><common:headerIcon iconName="search" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 17 17" /></span>
		<span class="f-s-16 f-w-700">Search By</span>
		</div>
	</div>
	<div class="col-xs-6 p-l-5 p-r-0">
	<div class="bg-white btn btn-white full-width hidden">
		<span class="valign-m"><common:globalIcon iconName="search-filter" iconFill="none" iconColor="#77A12E" width="16" height="18" viewBox="0 0 16 18" /></span>
		<span class="f-s-16 f-w-700">Filters</span>
		</div>
	</div>
</div>	
<div class="row m-t-10-xs m-t-10-sm m-x-5-neg-sm hidden-lg hidden-md">
<common:orderlisting-right-tabs shipToSelectedAcc="${shipToSelectedAcc}" shipToradio="shipToAc-m"/>
</div>	