<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">
<script>
$( document ).ready(function(){
	var carouselImageElements = $('[class^="LeadPageCorsalComp_"]');

	$.each(carouselImageElements, function(key, element) {
		$(element).addClass('active');
		return false;
	});
	   $(".promopage").carousel();
	    $(".promo1").click(function(){
	    	$(".promoPage").carousel(0);
	    });
	    $(".promo2").click(function(){
	        $(".promoPage").carousel(1);
	    });
	    $(".promo3").click(function(){
	        $(".promoPage").carousel(2);
	    });
	    $(".promo4").click(function(){
	        $(".promoPage").carousel(3);
	    });
    
	});
</script>	
<spring:url value="/" var="homelink" htmlEscape="false"/>	
<div>
	<h1 class="headline"><spring:theme code="leadGenerationPage.monthly.specials" /></h1>
 <br/><br/>
<div class="cl"></div>
	
<div id="promoPage" class="leadPagePadd col-md-4 tab-leadeGnerationPage promoPage tabs-left hidden-sm hidden-xs">
  <ul class="tabLeadGeneration promo-tabs">
		 <cms:pageSlot position="SectionA" var="feature" >
				<cms:component component="${feature}" />
		 </cms:pageSlot>
	</ul>	 
	</div>
<div class="promoPage carousel slide hidden-md hidden-lg" data-ride="carousel">
    <div class="promo-tabs carousel-inner hidden-md hidden-lg">
 		 <cms:pageSlot position="SectionA" var="feature" >
 		  <div class="LeadPageCorsalComp_${feature.uid} item">
				<cms:component component="${feature}" />
	     </div>
		 </cms:pageSlot>
     </div>	
		  <ol class="carousel-indicators">
		      <li data-target=".promoPage" data-slide-to="0" class="promo1 active"></li>
		      <li data-target=".promoPage" data-slide-to="1" class="promo2"></li>
		      <li data-target=".promoPage" data-slide-to="2" class="promo3"></li>
		      <li data-target=".promoPage" data-slide-to="3" class="promo4"></li>
		   </ol>
 </div> 
		<div class="tab-content"> 
<div  style="width:100%;margin-top:0px;position:relative;" class="promotionBanner hidden-sm hidden-xs">
</div>
 
</div>	 
  <div class="cl"></div>
  <br/><br/><br/>
  <div class="promotionLine item-sec-border hidden-md"></div>
      <br/><br/><br/>
  <div class="leadGenerationPageContent col-xs-12" >
		  <cms:pageSlot position="SectionB" var="feature" >
				<cms:component component="${feature}" />
		  </cms:pageSlot>
  </div>
  
  <div class="lead-generation-content col-xs-12">
  		 <div class="leadFormPage-content col-md-4">
 		 <cms:pageSlot position="SectionC1" var="feature" >
			<cms:component component="${feature}" />
		 </cms:pageSlot>
		   </div>
		 <div class="lead-generation-inst col-md-4">
		 <cms:pageSlot position="SectionC2" var="feature" >
			<cms:component component="${feature}" />
		 </cms:pageSlot>
		   </div>
		 <div class="lead-generation-paragraphs col-md-4">
		 <cms:pageSlot position="SectionC3" var="feature" >
			<cms:component component="${feature}" />
		 </cms:pageSlot>
		   </div>
  </div>
 
    <br/><br/>
<%-- <div>
		 <cms:pageSlot position="SectionC" var="feature" >
				<cms:component component="${feature}" />
		 </cms:pageSlot>
</div> --%>	 
	


 
</div>	 
  <div class="cl"></div>
  <br/><br/><br/>
  
	     
	         <c:choose>
	        	<c:when test="${success eq 'success'}">
	        	
	        	<div class="col-md-12 col-xs-12 col-sm-12 lead-generation-form-container successLead ">
	       
					<div class="col-md-12 col-xs-12 col-sm-12 lead-generation-form successForm">
						<div class="col-md-12 col-xs-12 col-sm-12">
							<div class="col-md-2 col-xs-4 col-sm-4 successIcon"></div>
			 				<div class="col-md-10 col-xs-8 col-sm-8 successMessage">
			 					<p class="success-msg-header"><b>Success!</b></p>
			 					<p class="messageSuccess" ><spring:theme code="leadGenerationPage.text1" />.</p>
			 		    		<a class="returnTo" href ="<c:url value="/lead"/>/${siteOneLeadGenerationForm.promotionId}"><spring:theme code="leadGenerationPage.return.form" /> &#8594</a>
		 		    		</div>
	 		    		</div>
		 			</div>
		 		</div>
		 			
				 </c:when>
			  <c:otherwise>
					
					<div class="col-md-12 lead-generation-form-container">
					<div class="lead-generation-form">
		
					 <div class="lead-generation-heading"><h2><span class="bold-text"><spring:theme code="leadGenerationPage.lets.connect" /></span></h2></div>
					 <div class="lead-generation-title"><spring:theme code="leadGenerationPage.signup.specials" />.</div>
					
					   <form:form id="siteOneLeadGenerationForm" modelAttribute="siteOneLeadGenerationForm" action="${homelink}lead/${siteOneLeadGenerationForm.promotionId}" method="post" autocomplete="off">
					   
					   		<div class="col-md-5">
					   		
					   		
					   				<formElement:formInputBox idKey="firstName" labelKey="leadForm.firstName" path="firstname" mandatory="true" />
								 	<span id="errorFirstName"></span>
								 	
								 	<formElement:formInputBox idKey="lastName" labelKey="leadForm.lastName" path="lastname" mandatory="true" />
								 	<span id="errorLastName"></span>
								 	
								 	<formElement:formInputBox idKey="companyName" labelKey="leadForm.companyName" path="company" mandatory="true" />
								 	<span id="errorCompanyName"></span>
								 	
								 	<formElement:formInputBox idKey="email" labelKey="leadForm.email" path="Email" mandatory="true" />
								 	<span id="errorEmail"></span>
								 	
								 	<formElement:formInputBox idKey="leadPhone" labelKey="leadForm.phone" path="phone" mandatory="false" />
								 	<span id="errorPhone"></span>
								 	
								 	<formElement:formInputBox idKey="zipCode" labelKey="leadForm.zip" path="postalcode" mandatory="true" />
								 	<span id="errorZipCode"></span>
								 	
								 	<formElement:formInputBox idKey="AccountNo" labelKey="leadForm.accno" path="accountNo" mandatory="false" />
								 	<div class="leadGeneration-div ">
									 	<label>
			 						    <input type="checkbox" name="optings" id="optings">
			 						    <span class="lg"><i class="leadeCrIcon glyphicon glyphicon-ok"></i></span>
			 						    <span class="optional-txt"><spring:theme code="leadGenerationPage.text.2" />.</span><br>
									 	</label><br>
									 	<span id="optingsError"></span>
								 	</div>
								 	<input type="hidden" class="promotionId" value="${siteOneLeadGenerationForm.promotionId}">
								 	
								 	<button type="button" id="unlockmore" class="button-lead-generation-form  btn btn-primary unlockmore" ><spring:theme code="leadGenerationPage.sign.up" /></button>
							</div>
					  </form:form> 
				</div> 
				</div>
			</c:otherwise>
			</c:choose>
			 <div class="cl"></div>
<div id="notes"></div>
		</div>
		
</template:page>