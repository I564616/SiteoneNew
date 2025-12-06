<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="multi-checkout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:set var="parcelData" value='${pageName}'></c:set>

<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<input class="isguestuser" type="hidden" value='${isGuestUser}'>
<spring:url value="/" var="homelink" htmlEscape="false"/>






	<form:form  id="siteOneAddressForm" modelAttribute="siteOneAddressForm" action="${homelink}checkout/multi/order-type/add-address" method="POST">
			     	
			     		<div class="row">
			     		<div class="col-md-12"><span class="addAddressError" ></span></div>
			     		<div class="col-md-12">
			     		<form:hidden path="addressId" class="add_edit_delivery_address_id"/>
			     		<formElement:formInputBox idKey="checkoutAddress.companyName" labelKey="text.address.companyName"  path="companyName"  inputCSS="form-control" mandatory="true" />
						<span class="errorCompanyName"></span> </div>
						<div class="cl"></div>
				
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.line1" labelKey="text.address.line1" path="line1" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
						<span class="errorline1"></span></div>
			     		<div class="cl"></div>
			    	     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.line2" labelKey="text.address.line2" path="line2" inputCSS="form-control" mandatory="false" placeholder="text.address.maxFifty" maxlength="50" />
			     		</div>
			     		<div class="cl"></div>
			     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
						<span class="errorTownCity"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
	  		 			<formElement:formSelectBox idKey="checkoutAddress.region" labelKey="${currentBaseStoreId eq 'siteone' ? 'address.state' : 'homeOwnerComponent.province' }" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="Select" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control new-address-region"/>
						<span class="errorRegion"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
  						<formElement:formInputBox idKey="checkoutAddress.postcode" labelKey="${currentBaseStoreId eq 'siteone' ? 'address.zipcode' : 'text.postcode.homeowner' }" path="postcode" inputCSS="form-control" mandatory="true" />
						<span class="errorPostCode"></span></div>
			     		<div class="cl"></div>
			     					     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.phone" labelKey="address.phone.name2" path="phone" inputCSS="form-control" mandatory="false"/>
			     		<span class="errorPhoneNumber"></span></div>
			     		<div class="cl"></div>
			     		<form:hidden id="checkoutAddress.countryIso" path="countryIso"  value="${currentBaseStoreId eq 'siteoneCA' ? 'CA' : 'US'}"/>
			     		<form:hidden id="checkoutAddress.district" path="district"  />
			     		<div class="col-md-12 colored"><formElement:formCheckbox idKey="saveAddressInMyAddressBook"
							labelKey="checkout.summary.deliveryAddress.saveAddressInMyAddressBook.name2"
							path="saveInAddressBook" inputCSS="add-address-left-input"
							labelCSS="add-address-left-label" mandatory="true" /></div>
						
						<div class="col-md-12 colored">
                            <div class="form-group">
                                <div class="checkbox m-t-0 marginBottom20">
                                    <label class="default-address-left-label control-label text-gray" for="defaultAddressInMyAddressBook">
                                    <input id="defaultAddressInMyAddressBook" name="defaultAddress" unchecked="true" type="checkbox" value="true" data-gtm-form-interact-field-id="0" disabled="true" class="add-address-left-input">
                                        <span class="pad-lft-10"><spring:theme code="checkout.summary.deliveryAddress.saveAddressInMyAddressBook.name3" /></span>
                                    </label>
                                </div>
                            </div>
                        </div>
			     	
			     		<div class="cl"></div>

			     		<div class="cl"></div>
			     		<div class="col-md-12"><button type="button" class="btn btn-primary addAddressFormSubmit"> <spring:theme code="choosePickupDeliveryMethodPage.add.location" /></button></div>
			     		</div>
			     				   
			     		
			     		
		     		

</form:form>