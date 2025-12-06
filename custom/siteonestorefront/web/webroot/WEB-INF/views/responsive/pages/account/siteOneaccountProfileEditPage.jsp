<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>



	<h1 class="headline hidden-xs hidden-sm">
	<spring:theme code="text.account.profile.updatePersonalDetails" />
</h1>
	<h1 class="headline2 hidden-lg hidden-md profile-headline">
	<spring:theme code="text.account.profile.updatePersonalDetails" />
</h1>
	<c:if test="${!updateProfileForm.isAdmin}">
	<br/>
	   	<p><spring:theme code="siteOneaccountProfileEditPage.contact.admin" /> </p>
	</c:if>
	

 
<div class="personal-detail-sec	col-md-12 row">
	<div class="row">
		<form:form action="update-siteoneprofile" method="get"
			modelAttribute="updateProfileForm">

			<%--             <formElement:formSelectBox idKey="profile.title" labelKey="profile.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="form.select.empty" items="${titleData}" selectCSSClass="form-control"/>
 --%>
  
 	<input type="hidden" id="emailSubscribe" value="${emailSubscribe}" />
 			
 		<c:if test="${isPunchOutAccount ne true }">  
			<div class="detail-panel">
			<div class="row">
				<div class="col-md-4 col-sm-8 col-xs-5">
				 <label><span class="bold-text"><spring:theme code="text.preference.password" />:</span></label>
					<div style="font-size:7pt;">&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;</div>
				</div>
				<div class="col-md-2 col-sm-4  col-xs-7">
				<a href="<c:url value="/my-account/update-password"/>" class="btn btn-primary password-btn" ><spring:theme code="siteOneaccountProfileEditPage.change.password" /></a>					
				</div>
				<%-- <spring:theme code="siteoneprofile.Password" />  --%>
				<div class="cl"></div>
				<%--  ${updateProfileForm.password} --%>
			</div>
			</div>
			</c:if>
			
			
			<div class="detail-panel">
			<div class="row">
			<div class="col-md-5 col-xs-12">
					 <label><span class="bold-text"><spring:theme code="text.preference.name"/>:</span></label>
					<div>${updateProfileForm.name}</div>
					
					<c:if test="${!updateProfileForm.isAdmin}">
	                   <div class="gray-italic"><spring:theme code="siteOneaccountProfileEditPage.update.name" /></div>
	               </c:if>
					
				</div>
			</div>
				<%-- <spring:theme code="siteoneprofile.Name" />
				${updateProfileForm.name} --%>
				<div class="cl"></div>
			</div>
				

			<div class="detail-panel no-bottom-border">
			<div class="row">
			<div class="col-md-6 col-xs-12">
			<label><span class="bold-text"><spring:theme code="text.preference.email"/>:</span></label>	
				<div>${updateProfileForm.email}</div>
				    
					    <c:if test="${!updateProfileForm.isAdmin}">
	                      <div class="gray-italic"> <spring:theme code="siteOneaccountProfileEditPage.update.email" /></div>
			            </c:if>
			      
				 <div class="margintop20">
				 <!-- <span class="colored">
		 			<input  type="checkbox" name="emailOptIn" value="" id="emailOptIn">  
        		</span>  I would like to receive email messages from SiteOne.To change this, -->
        		 <a href="<c:url value="/my-account/update-siteonepreference"/>"><spring:theme code="siteOneaccountProfileEditPage.set.email" /> &#8594;</a>	
			 </div>
			 </div>
			 </div>
			 <div class="cl"></div>
			 </div>
			 	<div class="detail-panel no-bottom-border">
			 	<div class="row">
			<div class="col-md-6 col-xs-12">
			<label><span class="bold-text"><spring:theme code="siteOneaccountProfileEditPage.mobile.no" /></span></label>
				<div><a class="tel-phone" href="tel:${updateProfileForm.contactNumber}">${updateProfileForm.contactNumber}</a></div>
				    
					    <c:if test="${!updateProfileForm.isAdmin}">
	                      <div class="gray-italic"> <spring:theme code="siteOneaccountProfileEditPage.update.mobile.no" /></div>
			            </c:if>
			       
				 <div class="margintop20">					
			 </div>
			 </div>
			 </div>
			 <div class="cl"></div>
			 </div>
			 
		<%-- 	<spring:theme code="siteoneprofile.Email" />	      
            
            ${updateProfileForm.email}
            <br>
			
			<br>
 --%>
			<%-- <input type="checkbox" name="emailOptIn" value="emailOptIn" checked>I would like to receive email messages from SiteOne<br>
			<a href="<c:url value="/my-account/update-siteonepreference"/>">Update Email
				Preference</a>

			<br>  
            ${updateProfileForm.contactNumber}
           <br> --%>


			 
		 
	<!-- 	<span class="prefrence-msg">*Messages and data rates apply. Mobile Internet access required. Up to 4 msg/month. Text  STOP to *** to cancel.</br>
		<a href="">Term, Conditions and Privacy.</a>
		</span> -->	
			
			
			
			
			
			<!-- <a href="<c:url value="/my-account/update-siteonepreference"/>">Update SMS
				Preference</a> -->
		<%-- 	<div class="accountActions clearfix">
				<div class="col-sm-6 col-sm-push-6  accountButtons">
					<ycommerce:testId code="personalDetails_savePersonalDetails_button">
						<button type="submit" class="btn btn-primary btn-block">
							<spring:theme code="text.account.profile.saveUpdates"
								text="Save Updates" />
						</button>
					</ycommerce:testId>
				</div>
				<div class="col-sm-6 col-sm-pull-6  accountButtons">
					<ycommerce:testId
						code="personalDetails_cancelPersonalDetails_button">
						<button type="button" class="btn btn-default btn-block backToHome">
							<spring:theme code="text.account.profile.cancel" text="Cancel" />
						</button>
					</ycommerce:testId>
				</div>
			</div> --%>
		</form:form>
		 <c:url value="/my-account/update-language" var="updateLanguage"/>
		<form:form id="siteOneRequestAccountForm" action="${updateLanguage}" method="POST" modelAttribute="siteOneRequestAccountForm">
	<div class="detail-panel no-bottom-border">
	<div class="row">
	<div class="col-md-4">
	<label for="yourPreferredLanguage"><spring:theme code="requestaccount.preferredLang" /></label>
 					<div class="cl"></div>
 					<div class="row">

 						<div class="label-column2">
 							<div class="label-highlight">
 								<span class="colored-radio"> <form:radiobutton name="languagePreference" path="languagePreference" value="English" idKey="languagePreference1"/> </span>&nbsp;<label for="languagePreference1">English</label>
 							</div>
 						</div>
 						<div class="cl hidden-md hidden-lg"><br/></div>
 						<div class="label-column2">
 							<div class="label-highlight">
 								<span class="colored-radio"> <form:radiobutton name="languagePreference" path="languagePreference" value="Spanish" idKey="languagePreference2" /> </span> &nbsp;<label for="languagePreference2">Español</label>
 							</div>
 						</div>
 					</div>
 					<div class="cl"></div>
 					<br/>
 		<button type="submit" class="btn btn-primary  margin20">		
 		<spring:theme code="requestaccount.submit" />
		</button>
 		</div>
 		</div>
 		</div>
			</form:form>

	</div>
	<div class="cl"></div>
</div>
<div style="clear: both"></div>