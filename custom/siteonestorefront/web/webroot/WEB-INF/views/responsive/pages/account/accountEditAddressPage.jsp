<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>

<spring:url value="/my-account/address-book/${unitId}" var="addressBookUrl"/>

<c:choose>
	<c:when test="${edit eq true }">
        <c:set var="headline"><spring:theme code="text.account.addressBook.updateAddress" text="Edit an Address"/></c:set>
	</c:when>
	<c:otherwise>
        <c:set var="headline"><spring:theme code="text.account.addressBook.addAddress" text="New Address"/></c:set>
	</c:otherwise>
</c:choose>

    <input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteoneCA' ? 'CA' : 'US'}"/>
    <div class="row">
        <div class="col-md-6">
           <%--  <button type="button" class="addressBackBtn" data-back-to-addresses="${addressBookUrl}">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </button> --%>
            <h1  class="headline">${headline}</h1>
        </div>
    </div>
 <br/> 
<div class="row">
    <div class="col-md-4">
        <div class="row">
             
                <address:addressFormSelector supportedCountries="${countries}" regions="${regions}" cancelUrl="/my-account/address-book/${unitId}" addressBook="true" isDefaultAddress="${isDefaultAddress}" />
            
        </div>
    </div>
</div>

