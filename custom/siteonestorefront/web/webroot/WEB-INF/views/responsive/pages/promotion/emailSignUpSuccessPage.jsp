<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page pageTitle="${pageTitle}">
          	<h1 class="headline hidden-xs"><spring:theme code="emailSignUpSuccessPage.thankyou.sign" />!</h1> 
	<h1 class="headline3 hidden-sm hidden-md hidden-lg"><spring:theme code="emailSignUpSuccessPage.thankyou.sign" />!</h1> 
	<div>
		<p><spring:theme code="emailSignUpSuccessPage.check.inbox" />.</p>
	</div>
	<br/><br/>
	<div class="row">
		  <div class="col-sm-4">
		             <a href="<c:url value="/"/>" class="btn btn-primary">
                    <spring:theme code="signup.page.continue"/> </a>
          </div>
          </div>
</template:page>