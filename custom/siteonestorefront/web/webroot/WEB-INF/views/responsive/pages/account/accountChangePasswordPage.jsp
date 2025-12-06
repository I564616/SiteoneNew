<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>

 
	 
		<!-- <div class="container-lg col-md-6"> -->
			 <h1 class="headline hidden-xs"><spring:theme code="text.account.profile.changePassword"/></h1>
			 <h1 class="headline2 profile-headline hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.profile.changePassword"/></h1>
		<!-- </div> -->
	 
 
<br/>
	<div class="col-md-12 row">
	<div class="row">
		 <form:form action="${action}" method="post" modelAttribute="updatePasswordForm" id="siteoneChangePasswordForm" class="row">
			
			<div class="col-md-4 col-lg-4">
			<div class="row">
				<div class="col-lg-10 col-md-11">
	
					<formElement:formPasswordBox idKey="currentPassword"
												 labelKey="profile.currentPassword" path="currentPassword" inputCSS="form-control"
												 mandatory="true" />
					<formElement:formPasswordBox idKey="newPassword"
												 labelKey="profile.newPassword" path="newPassword" inputCSS="form-control"
												 mandatory="true" />
					<formElement:formPasswordBox idKey="checkNewPassword"
												 labelKey="profile.checkNewPassword" path="checkNewPassword" inputCSS="form-control"
												 mandatory="true" />
				</div>
</div>
		 
				</div>


				<div class="col-md-5  col-md-push-1 col-lg-push-0">
					<br/>
					<p class="black-title bold-text"><spring:theme code="accountChangePasswordPage.password" /></p>
					<div class="update-password"><span id="atleast8Char" class="icon-check-gray"></span><span><spring:theme code="updatePwd.instruction.point1"/></span></div>
					<div class="update-password"><span id="uppercase" class="icon-check-gray"></span><span><spring:theme code="updatePwd.instruction.point2"/></span></div>
					<div class="update-password"><span id="lowercase" class="icon-check-gray"></span><span><spring:theme code="updatePwd.instruction.point3"/></span></div>
					<div class="update-password"><span id="numeric" class="icon-check-gray"></span><span><spring:theme code="updatePwd.instruction.point4"/></span></div>
					<div class="update-password"><span id="symbol" class="icon-check-gray"></span><span><spring:theme code="updatePwd.instruction.point6"/></span></div>
					<!-- <div class="update-password"><span id="lastpasswords" class="icon-check-gray2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span><spring:theme code="updatePwd.instruction.point5"/></span></div> -->
					</div>



					<div class="cl"></div>
						<br/>
					<div class="col-md-6 row">
					<div class="col-sm-6 col-md-3 col-lg-3 col-xs-6">
								<button type="submit" class="btn btn-primary btn-block">
									<spring:theme code="updatePwd.submit" text="Update Password" />
								</button>
						</div>
					
						<div class="col-sm-6 col-md-3  col-lg-3  col-xs-6">
								<button type="button" class="btn btn-default btn-block changePwdbackToHome">
									<spring:theme code="text.button.cancel" text="Cancel" />
								</button>
						</div>
						
					</div>
				</form:form>
				<div class="cl"></div>
			  
		 
	</div>
	
					
	
</div>