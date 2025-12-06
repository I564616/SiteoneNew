<%@ attribute name="regions" required="true" type="java.util.List"%>
<%@ attribute name="country" required="false" type="java.lang.String"%>
<%@ attribute name="tabIndex" required="false" type="java.lang.Integer"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<c:choose>
	<c:when test="${country == 'US'}">
	 <formElement:formInputBox idKey="address.projectName" labelKey="text.address.nickname" path="projectName"  inputCSS="form-control" mandatory="true" maxlength="20"/> 
		<%--  <formElement:formSelectBox idKey="address.title" labelKey="address.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.title.pleaseSelect" items="${titles}" selectedValue="${addressForm.titleCode}" selectCSSClass="form-control"/>
		<span class="errorTitle"></span>  --%>
		 <formElement:formSelectBox idKey="childUnit" skipBlank="false"
                                                   labelKey="text.company.user.account.title" path="unitId" selectCSSClass="form-control"
                                                   mandatory="true" items="${childUnits}" disabled="${not empty param.unit and not empty param.role}"
                                                   skipBlankMessageKey="form.select.empty"/>
                                                   <span class="errorchildUnit"></span>
		<%-- <formElement:formInputBox idKey="address.firstName" labelKey="address.firstName" path="firstName" inputCSS="form-control" mandatory="true" />
		<span class="errorFirstName"></span>
		<formElement:formInputBox idKey="address.surname" labelKey="address.surname" path="lastName" inputCSS="form-control" mandatory="true" />
		<span class="errorSurName"></span> --%>
		<formElement:formInputBox idKey="address.companyName" labelKey="text.address.companyName" path="companyName"  inputCSS="form-control" mandatory="true" />
		<span class="errorCompanyName"></span> 
		<formElement:formInputBox idKey="address.line1" labelKey="text.address.line1" path="line1" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
		<span class="errorline1"></span>
		<formElement:formInputBox idKey="address.line2" labelKey="text.address.line2" path="line2" inputCSS="form-control" mandatory="false" placeholder="text.address.maxFifty" maxlength="50" />
		<formElement:formInputBox idKey="address.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
		<span class="errorTownCity"></span><spring:message code="request.form.select.empty" var="select"/>
		<formElement:formSelectBox idKey="address.region" labelKey="address.state" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="${select}" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control"/>
		<span class="errorRegion"></span>
		<formElement:formInputBox idKey="address.postcode" labelKey="address.zipcode" path="postcode" inputCSS="form-control" mandatory="true" />
		<span class="errorPostCode"></span>
        <formElement:formInputBox  idKey="address.phone" labelKey="address.phone" path="phone" inputCSS="form-control" mandatory="false" />
        <span class="errorPhoneNumber"></span>
        <BR>
         <%-- <label><spring:theme code="address.deliveryinstructions"/></label>
        <textarea rows="4" cols="50" id="deliveryInstructions" style="overflow:hidden;resize: none;" class="form-control">
        </textarea> --%>
        <%-- <formElement:formTextArea idKey="address.deliveryInstructions" labelKey="address.deliveryinstructions" path="deliveryInstructions"  inputCSS="form-control" mandatory="true"  /> 
         --%> <form:hidden id="address.district" path="district"  />
	</c:when>
	<c:when test="${country == 'CA'}">
		<formElement:formInputBox idKey="address.projectName" labelKey="text.address.nickname" path="projectName"  inputCSS="form-control" mandatory="true" maxlength="20"/> 
		<%--  <formElement:formSelectBox idKey="address.title" labelKey="address.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.title.pleaseSelect" items="${titles}" selectedValue="${addressForm.titleCode}" selectCSSClass="form-control"/>
		<span class="errorTitle"></span>  --%>
		 <formElement:formSelectBox idKey="childUnit" skipBlank="false"
                                                   labelKey="text.company.user.account.title" path="unitId" selectCSSClass="form-control"
                                                   mandatory="true" items="${childUnits}" disabled="${not empty param.unit and not empty param.role}"
                                                   skipBlankMessageKey="form.select.empty"/>
                                                   <span class="errorchildUnit"></span>
		<%-- <formElement:formInputBox idKey="address.firstName" labelKey="address.firstName" path="firstName" inputCSS="form-control" mandatory="true" />
		<span class="errorFirstName"></span>
		<formElement:formInputBox idKey="address.surname" labelKey="address.surname" path="lastName" inputCSS="form-control" mandatory="true" />
		<span class="errorSurName"></span> --%>
		<formElement:formInputBox idKey="address.companyName" labelKey="text.address.companyName" path="companyName"  inputCSS="form-control" mandatory="true" />
		<span class="errorCompanyName"></span> 
		<formElement:formInputBox idKey="address.line1" labelKey="text.address.line1" path="line1" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
		<span class="errorline1"></span>
		<formElement:formInputBox idKey="address.line2" labelKey="text.address.line2" path="line2" inputCSS="form-control" mandatory="false" placeholder="text.address.maxFifty" maxlength="50" />
		<formElement:formInputBox idKey="address.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" placeholder="text.address.maxFifty" maxlength="50" />
		<span class="errorTownCity"></span>
		<formElement:formSelectBox idKey="address.region" labelKey="${currentBaseStoreId eq 'siteone' ? 'address.state' : 'homeOwnerComponent.province' }" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="Select" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control"/>
		<span class="errorRegion"></span>
		<formElement:formInputBox idKey="address.postcode" labelKey="${currentBaseStoreId eq 'siteone' ? 'address.zipcode' : 'text.postcode.homeowner' }" path="postcode" inputCSS="form-control" mandatory="true" />
		<span class="errorPostCode"></span>
        <formElement:formInputBox  idKey="address.phone" labelKey="address.phone" path="phone" inputCSS="form-control" mandatory="false" />
        <span class="errorPhoneNumber"></span>
        <%-- <label><spring:theme code="address.deliveryinstructions"/></label>
        <textarea rows="4" cols="50" id="deliveryInstructions" style="overflow:hidden" class="form-control">
        </textarea> --%>
        <form:hidden id="address.district" path="district"  />
	</c:when>
	<c:when test="${country == 'CN'}">
		<formElement:formInputBox idKey="address.postcode" labelKey="address.postalcode" path="postcode" inputCSS="form-control" mandatory="true" />
	    <span class="errorPostCode"></span>
		<formElement:formSelectBox idKey="address.region" labelKey="address.province" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectProvince" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control"/>
		
		<formElement:formInputBox idKey="address.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" />
		<span class="errorSurName"></span>
		<formElement:formInputBox idKey="address.line1" labelKey="address.street" path="line1" inputCSS="form-control" mandatory="true" />
		<span class="errorline1"></span>
		<formElement:formInputBox idKey="address.line2" labelKey="address.building" path="line2" inputCSS="form-control" mandatory="true"/>
		<formElement:formInputBox idKey="address.surname" labelKey="address.surname" path="lastName" inputCSS="form-control" mandatory="true" />
		<span class="errorTownCity"></span>
		<formElement:formInputBox idKey="address.firstName" labelKey="address.firstName" path="firstName" inputCSS="form-control" mandatory="true" />
		<span class="errorFirstName"></span>
		<formElement:formSelectBox idKey="address.title" labelKey="address.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.title.pleaseSelect" items="${titles}" selectedValue="${addressForm.titleCode}" selectCSSClass="form-control"/>
		<span class="errorTitle"></span>
        <formElement:formInputBox idKey="address.phone" labelKey="address.phone" path="phone" inputCSS="form-control" mandatory="true" />
        <span class="errorPhoneNumber"></span>
	</c:when>
	<c:when test="${country == 'JP'}">
		<formElement:formSelectBox idKey="address.title" labelKey="address.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.title.pleaseSelect" items="${titles}" selectedValue="${addressForm.titleCode}" selectCSSClass="form-control"/>
		<span class="errorTitle"></span>
		<formElement:formInputBox idKey="address.surname" labelKey="address.surname" path="lastName" inputCSS="form-control" mandatory="true" />
		<span class="errorFirstName"></span>
		<formElement:formInputBox idKey="address.firstName" labelKey="address.firstName" path="firstName" inputCSS="form-control" mandatory="true" />
		<span class="errorSurName"></span>
		<formElement:formInputBox idKey="address.line1" labelKey="address.furtherSubarea" path="line1" inputCSS="form-control" mandatory="true" />
		<span class="errorline1"></span>
		<formElement:formInputBox idKey="address.line2" labelKey="address.subarea" path="line2" inputCSS="form-control" mandatory="false"/>
		<formElement:formInputBox idKey="address.townCity" labelKey="address.townJP" path="townCity" inputCSS="form-control" mandatory="true" />
		<span class="errorTownCity"></span>
		<formElement:formSelectBox idKey="address.region" labelKey="address.prefecture" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectPrefecture" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control"/>
		
		<formElement:formInputBox idKey="address.postalcode" labelKey="address.postcode" path="postcode" inputCSS="form-control" mandatory="true" />
		<span class="errorPostCode"></span>
        <formElement:formInputBox idKey="address.phone" labelKey="address.phone" path="phone" inputCSS="form-control" mandatory="true" />
        <span class="errorPhoneNumber"></span>
	</c:when>
	<c:otherwise>
		<formElement:formSelectBox idKey="address.title" labelKey="address.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.title.pleaseSelect" items="${titles}" selectedValue="${addressForm.titleCode}" selectCSSClass="form-control"/>
		<span class="errorTitle"></span>
		<formElement:formInputBox idKey="address.firstName" labelKey="address.firstName" path="firstName" inputCSS="form-control" mandatory="true" />
		<span class="errorFirstName"></span>
		<formElement:formInputBox idKey="address.surname" labelKey="address.surname" path="lastName" inputCSS="form-control" mandatory="true" />
		<span class="errorSurName"></span>
		<formElement:formInputBox idKey="address.line1" labelKey="address.line1" path="line1" inputCSS="form-control" mandatory="true" />
		<span class="errorline1"></span>
		<formElement:formInputBox idKey="address.line2" labelKey="address.line2" path="line2" inputCSS="form-control" mandatory="true"/>
		<formElement:formInputBox idKey="address.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" />
		<span class="errorTownCity"></span>
		<formElement:formInputBox idKey="address.postcode" labelKey="address.postcode" path="postcode" inputCSS="form-control" mandatory="true" />
		<span class="errorPostCode"></span>
        <formElement:formInputBox idKey="address.phone" labelKey="address.phone" path="phone" inputCSS="form-control" mandatory="true" />
        <span class="errorPhoneNumber"></span>
	</c:otherwise>
</c:choose>

