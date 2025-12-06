<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<div class="checkout_addPhoneNumber forgotten-password">
<c:choose>
 <c:when test ="${not empty isPhoneNumberUpdated &&  isPhoneNumberUpdated eq false}">  
 <div class="alert negative">
      <spring:theme code="phoneNumber.update.service.down"/>
 </div>  
 </c:when> 
 <c:otherwise>
	<form:form method="post" modelAttribute="siteOneAddPhoneNumberForm">
		<div class="control-group">		
			<ycommerce:testId code="checkout_addPhoneNumber_input">
				<formElement:formInputBox idKey="user.phonenumber"  labelKey="" path="phoneNumber"   mandatory="true"/>			
			</ycommerce:testId>	
			<form:hidden path="emailId" id="addPhoneNumberEmailId"/>
			<span id="errorPhoneNumber"></span>
			<ycommerce:testId code="checkout_addPhoneNumber_submit">
				<button class="btn btn-primary btn-block add-phoneNumberBtn" type="submit">
					<spring:theme code="addPhoneNumber.submit"/>					 
				</button>
			</ycommerce:testId>
		</div>
	</form:form>
 </c:otherwise>
	</c:choose>
</div>
