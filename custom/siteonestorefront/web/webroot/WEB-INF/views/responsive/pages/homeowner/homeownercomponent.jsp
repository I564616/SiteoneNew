<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="" style="width: 80%; margin: 10; margin-left: .5cm">

	<form:form action="${homelink}homeowner/form" method="POST"
		modelAttribute="siteOneHomeOwnerForm">
		<br />

		<div style="width: 80%; margin: 10; margin-left: .5cm">
		
			<b><spring:theme code="homeownercomponent.customer.info" /></b>
			<br />
			<br />
			<form:label path="firstName">*<spring:theme code="homeownercomponent.first.name" /></form:label>
			<br />
			<form:input idKey="firstName" path="firstName" />
			<br /> <span id="firstNameError" style="color: red;"></span> <br />
			<br />

			<form:label path="lastName">*<spring:theme code="homeownercomponent.last.name" /></form:label>
			<br />
			<form:input idKey="lastName" path="lastName" />
			<br /> <span id="lastNameError" style="color: red;"></span> <br />
			<br />

			<form:label path="emailAddr">*<spring:theme code="homeownercomponent.email" /></form:label>
			<br />
			<form:input idKey="emailAddr" path="emailAddr" autocomplete="off"/>
			<br /> <span id="emailAddrError" style="color: red;"></span> <br />
			<br />

			<form:label path="phone">*<spring:theme code="homeownercomponent.phone.num" /></form:label>
			<br />
			<form:input idKey="phone" path="phone" />
			<br /> <span id="phoneError" style="color: red;"></span> <br /> <br />

			<form:label path="address"><spring:theme code="homeownercomponent.address" /></form:label>
			<br />
			<form:input idKey="address" path="address" />
			<br /> <span id="addressError" style="color: red;"></span> <br /> <br />

			<form:label path="customerCity">*<spring:theme code="homeownercomponent.city" /></form:label>
			<br />
			<form:input idKey="customerCity" path="customerCity" />
			<br /> <span id="customerCityError" style="color: red;"></span> <br />
			<br />

			<form:label path="customerState">*<spring:theme code="homeownercomponent.state" /></form:label>
			<br />
			<form:select idKey="customerState" path="customerState"
				items="${states}" />
			<br /> <span id="customerStateError" style="color: red;"></span> <br />
			<br />

			<form:label path="customerZipCode">*<spring:theme code="homeownercomponent.zip.code" /></form:label>
			<br />
			<form:input idKey="customerZipCode" path="customerZipCode" />
			<br /> <span id="customerZipCodeError" style="color: red;"></span> <br />
			<br /> <br />
		</div>

		<tr>
			<td colspan="2" class="form_divider">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" class="form_divider">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" class="form_sub_heading" style="font-weight: bold"><b><spring:theme code="homeownercomponent.request.info" /></b></td>
		</tr>
		<tr>
			<td colspan="2" class="form_divider">&nbsp;</td>
		</tr>
		<br>
		<br>

		<!-- Request information section -->
		<tr>
			<td class="form_label_cell_top" id="lbl_best_time" colspan='2'><span
				class="form_label"><spring:theme code="homeownercomponent.text1" />:</span></td>
		</tr>
		<tr>
			<td class="form_textfield_cell" colspan='2'><form:select
					idKey="bestTimeToCall" path="bestTimeToCall"
					items="${bestTimeToCall}" /></td>
		</tr>


		<tr>
			<td colspan='2'><br />
				<div class="form_rule">&nbsp;</div></td>
		</tr>

		<tr>
			<td class="form_label_cell_top" id="lbl_service_type" colspan='2'><span
				class="form_label">*<spring:theme code="homeownercomponent.text2" />:</span></td>
		</tr>
		<tr>
			<td class="form_textfield_cell" colspan='2'><form:select
					idKey="serviceType" path="serviceType" items="${serviceType}" /></td>

		</tr>
		<tr>
			<td><span id="serviceTypeError" style="color: red;"></span> <br />
			</td>
		</tr>

		<!-- seperating line -->
		<tr>
			<td colspan='2'><br />
				<div class="form_rule">&nbsp;</div></td>
		</tr>

		<tr>
			<td class="form_label_cell_top" id="lbl_referral_number" colspan='2'><span
				class="form_label">*<spring:theme code="homeownercomponent.text3" /></span></td>

		</tr>
		<tr>
			<td><form:select idKey="referalsNo" path="referalsNo"
					items="${referralNo}" /></td>
		</tr>
		<tr>
			<td class="form_label_cell_top" id="lbl_contractor_type" colspan='2'><span
				class=""><br /><spring:theme code="homeownercomponent.text4" />:</span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.landscaping" var="landscaping" /><form:checkbox path="lookingFor" value="${landscaping}" /><spring:theme code="homeownercomponent.landscaping" /></span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.lawn.maintenance" var="maintenance" /><form:checkbox path="lookingFor" value="${maintenance}" /><spring:theme code="homeownercomponent.lawn.maintenance" /></span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.sprinkler" var="sprinkler" /><form:checkbox path="lookingFor"
					value="${sprinkler}" /><spring:theme code="homeownercomponent.sprinkler" /></span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.lighting" var="lighting" /><form:checkbox path="lookingFor" value="${lighting}" /><spring:theme code="homeownercomponent.lighting" /></span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.kitchen" var="kitcher" /><form:checkbox path="lookingFor" value="${kitcher}" /><spring:theme code="homeownercomponent.kitchen" /></span></td>
		</tr>
		<tr>
			<td><spring:message code="homeownercomponent.text5" var="paverwalls" /><form:checkbox path="lookingFor"
					value="${paverwalls}" /><spring:theme code="homeownercomponent.text5" /></span></td>
		</tr>


		<tr>
			<td class="form_label_cell_top" id="lbl_other" colspan='2'><span
				class="form_label"><spring:theme code="homeownercomponent.other" />: </span> <input class=""
				id="lookingForOthers" name="lookingForOthers" type="text" /></td>
		</tr>

		<!-- seperating line -->
		<tr>
			<td colspan='2'><br />
				<div class="form_rule">&nbsp;</div></td>
		</tr>



		<tr style="align: center">
			<td style="align: center">
				<button type="submit" class="homeowner btn btn-primary btn-block"
					id="homeowner" style="width: 25%; align: center"><spring:theme code="homeownercomponent.submit.request" /></button>
			</td>
		</tr>

	</form:form>

</div>
<div class="cl"></div>
<div class="cl"></div>
