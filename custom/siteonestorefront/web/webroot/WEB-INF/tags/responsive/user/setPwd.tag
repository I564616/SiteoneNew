<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="account-section-header no-border"><h1 class="headline"><spring:theme code="setPwd.title"/></h1></div><br>
 <p><spring:theme code="setPwd.create" /></p>
	<div class="account-section-content row">
		<form:form method="post" modelAttribute="siteOneSetPwdForm" id="siteOneSetPwdForm" action="${homelink}login/pw/setPassword">
			<div class="col-md-4 col-lg-4 col-sm-12 xol-xs-12">
    			<div class="form-group">
                	<formElement:formPasswordBox idKey="password" labelKey="setPwd.pwd" path="pwd"
                                                 inputCSS="form-control" mandatory="true"/>
                </div>
               	<div class="form-group">
                    <formElement:formPasswordBox idKey="setPwd.checkPwd" labelKey="setPwd.checkPwd"
                                                 path="checkPwd" inputCSS="form-control" mandatory="true"/>
                </div>
                <form:hidden path="token" />
                
                <div class="row login-form-action">
                     <div class="col-sm-6 col-xs-12">
                           <button type="submit" class="btn btn-primary btn-block">
                                  <spring:theme code="setPwd.submit"/>
                           </button>
                     </div>
                </div>
            </div>
        </form:form>
        
       	<div class="col-md-5  col-md-push-1 col-lg-push-0">
					<span class="hidden-md hidden-lg"><br/></span>
					<p><b><spring:theme code="setPwd.passwordMustInclude" />:</b></p>
										
					<div class="update-password"><span id="atleast8Char" class="icon-check-gray"></span><span><spring:theme code="setPwd.passwordMustInclude1" /></span></div>
					<div class="update-password"><span id="uppercase" class="icon-check-gray"></span><span><spring:theme code="setPwd.passwordMustInclude2" /></span></div>
					<div class="update-password"><span id="lowercase" class="icon-check-gray"></span><span><spring:theme code="setPwd.passwordMustInclude3" /></span></div>
					<div class="update-password"><span id="numeric" class="icon-check-gray"></span><span><spring:theme code="setPwd.passwordMustInclude4" /></span></div>
					<div class="update-password"><span id="symbol" class="icon-check-gray"></span><span><spring:theme code="setPwd.passwordMustInclude5" /></span></div>
					</div>
    </div>
 
