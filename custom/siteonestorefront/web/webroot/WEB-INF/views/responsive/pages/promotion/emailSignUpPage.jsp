<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page pageTitle="${pageTitle}">

	 <h1 class="headline"><spring:theme code="emailSignUpPage.signup.for.emails" />!</h1>
	 
  
  
		<p style="margin-top:20px;"><spring:theme code="emailSignUpPage.text.1" /><br>
		   <spring:theme code="emailSignUpPage.text.2" />
		  </p> 
 
		<form:form id="siteOneEmailSignUpForm" modelAttribute="siteOneEmailSignUpForm" action="emailsignup" method="POST">
				<!-- <div class="bg-danger"><span id="emailError"></span></div> -->
					 <span id="emailErrors"></span> 
					 <div class="cl"></div>
					 <span id="errorZipcode"></span> 
					 <div class="cl"></div>
					 <span id="agreeError"></span>
				 <br/>
				<div class="col-md-5">
				<div class="row">
					<formElement:formInputBox idKey="emailIdSignUp" labelKey="signUpForm.Email" path="Email" mandatory="true" />
				 
									 	<formElement:formInputBox idKey="zipcode" labelKey="signUpForm.postalcode" path="postalcode" mandatory="true" />
									 	
									 	<div class="cl"></div>
									 
									 	<label class="form-group" style="margin:10px 0px 25px 0px;"><spring:theme code="signUpForm.userType"/></label>
									 
									  
									 	<div class="cl"></div>
									 	 	<div class="row">
									 	<div class="label-column"><div class="label-highlight" role="radiogroup" aria-label="radio-contractor"><span class="colored-radio"><input type='radio' name='role' value='Contractor' checked></span><spring:theme code="emailSignUpPage.landscape.prof" /> </div></div>
									 	<div class="cl hidden-md hidden-lg"><br/></div>
									 	<div class="label-column"><div class="label-highlight"><span class="colored-radio"><input type='radio' role="radiogroup" aria-label="radio-homeowner" name='role' value='Homeowner'></span> <spring:theme code="emailSignUpPage.homeowner" /></div></div>
									 	<%-- <formElement:formInputBox idKey="signUpUserType" labelKey="signUpForm.userType" path="userType" mandatory="true" /> --%>
										<div class="cl"></div>
										<br/><p></p>
									 
										<div class="colored col-md-12">
					                    <input type="checkbox" role="agree" aria-label="radio-agree" name="agree" id="agree">&nbsp;<spring:theme code="emailSignUpPage.text.3" /><br>
									 	<!-- <button type="button" id="signUpPage" class="btn btn-primary signup" disabled>Sign Up</button> -->
										</div>
										 	</div>
										<br/><p></p>
									 	<div class="col-md-4">
									 	<div class="row">
									 	<input  type="button" id="signUpPage" class="btn btn-primary btn-block" value="Sign Up">
									 	</div>
									 	</div>
				</div>

				</div>
				<div class="cl"></div>
		 </form:form>
	 
</template:page>

<!-- 
  <input id="check" name="checkbox" type="checkbox">
  <label for="check">Some Text Here</label>for must be the id of input
</div>
<input type="submit" name="anmelden" class="button" id="btncheck" value="Send" /> -->