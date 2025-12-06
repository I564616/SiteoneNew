<%@ attribute name="supportedCountries" required="true" type="java.util.List"%>
<%@ attribute name="regions" required="true" type="java.util.List"%>
<%@ attribute name="country" required="false" type="java.lang.String"%>
<%@ attribute name="cancelUrl" required="false" type="java.lang.String"%>
<%@ attribute name="addressBook" required="false" type="java.lang.String"%>
<%@ attribute name="isDefaultAddress" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:if test="${not empty deliveryAddresses}">
	<button type="button" class="btn btn-default btn-block js-address-book">
		<spring:theme
			code="checkout.checkout.multi.deliveryAddress.viewAddressBook"
			text="Address Book" />
	</button>
	<br>
</c:if>
<c:if test="${empty addressFormEnabled or addressFormEnabled}">
	<form:form method="post" modelAttribute="siteOneAddressForm">
		<form:hidden path="addressId" class="add_edit_delivery_address_id"
			status="${not empty suggestedAddresses ? 'hasSuggestedAddresses' : ''}" />
		<input type="hidden" name="bill_state" id="address.billstate" />
	
		 <div id="countrySelector" data-address-code="${addressData.id}"
			data-country-iso-code="${addressData.country.isocode}"
			class="form-group hidden">
			<formElement:formSelectBox idKey="address.country"
				labelKey="" path="countryIso" mandatory="true"
				skipBlank="false" skipBlankMessageKey="address.country"
				items="${supportedCountries}" itemValue="isocode"
				selectedValue="${addressForm.countryIso}"
				selectCSSClass="hidden" />
		</div>
		<span class=errorAddressCountry></span>
		<div id="i18nAddressForm" class="i18nAddressForm">
			<c:if test="${not empty country}">
				<address:addressFormElements regions="${regions}"
					country="${country}" />
				 <span class="errorAddressCountry"></span>
			</c:if>
		</div>
		<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
			<c:choose>
				<c:when test="${showSaveToAddressBook}">
				<div class="checkbox">
					<formElement:formCheckbox idKey="saveAddressInMyAddressBook"
						labelKey="checkout.summary.deliveryAddress.saveAddressInMyAddressBook"
						path="saveInAddressBook" inputCSS="add-address-left-input"
						labelCSS="add-address-left-label" mandatory="true" />
				</div>
				</c:when>
			</c:choose>
		</sec:authorize>
		<div class="checkbox marginBottom30 m-t-0">
		<c:choose>
			<c:when test="${isDefaultAddress}">
    			<label class="control-label add-address-left-label" for="defaultAddressInMyAddressBook">
        		<span class="colored"><input id="defaultAddressInMyAddressBook" name="defaultAddress" class="add-address-left-input" checked="true" type="checkbox" disabled="true" value="true"></span>
            	<span class="pad-lft-10 text-gray"><spring:theme code="text.account.makeDefault" text="Default address" /></span>
        		</label>
			</c:when>
			<c:otherwise>
    			<label class="control-label add-address-left-label" for="defaultAddressInMyAddressBook">
        		<span class="colored"><input id="defaultAddressInMyAddressBook" name="defaultAddress" class="add-address-left-input" unchecked="true" type="checkbox" value="true"></span>
            	<span class="pad-lft-10"><spring:theme code="text.account.makeDefault" text="Default address" /></span>
        		</label>
			</c:otherwise>
		</c:choose>
        </div>
		
		  <!-- <div class="mb-text">
			<span class="colored"><input type="checkbox" /></span> Make this my default address
		</div>  -->		
		<div class="cl"></div>
	  
		<div class="row">
		<div id="addressform_button_panel" class="form-actions col-md-10 col-sm-12 col-xs-12">
			<c:choose>
				<c:when test="${edit eq true && not addressBook}">
					<ycommerce:testId code="multicheckout_saveAddress_button">
						<button
							class="positive right change_address_button show_processing_message"
							type="submit" id="saveButton">
							<spring:theme code="checkout.multi.saveAddress"
								text="Save address" />
						</button>
					</ycommerce:testId>
				</c:when>
				<c:when test="${addressBook eq true}">
					<div class=" row">
						 	<div class="col-sm-6   col-xs-6 accountButtons">
							<ycommerce:testId code="editAddress_saveAddress_button">
									<button class="btn btn-primary btn-block change_address_button show_processing_message siteOneAddressForm"
											type="button">
										<spring:theme code="text.button.save"
													  text="Save" />
									</button>
								</ycommerce:testId>
								
							</div>
							<div class="col-sm-6   col-xs-6 accountButtons">
								<ycommerce:testId code="editAddress_cancelAddress_button">
									<c:url value="${cancelUrl}" var="cancel"/>
									<a class="btn btn-block btn-default" href="${fn:escapeXml(cancel)}">
										<spring:theme code="text.button.cancel"
													  text="Cancel" />
									</a>
								</ycommerce:testId>
							</div>
						
						 
					</div>
				</c:when>
			</c:choose>
		</div>
		</div>
	</form:form>
</c:if>