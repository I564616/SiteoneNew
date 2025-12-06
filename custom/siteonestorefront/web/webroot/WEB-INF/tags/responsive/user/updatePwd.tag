<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:url value="/" var="homelink" htmlEscape="false"/>     
		<div class="container-lg col-md-12">
			 <h1 class="headline"><spring:theme code="resetPwd.title"/></h1>
		</div>
 
	
	<br/><br/>
          	<div class="col-md-12">
	
                <form:form method="post" modelAttribute="siteOneUpdatePwdForm" id="siteOneUpdatePwdForm" action="${homelink}login/pw/change">
                    <div class="col-md-4 row">
                        <div class="form-group">
                            <formElement:formPasswordBox idKey="password" labelKey="updatePwd.pwd" path="pwd"
                                                         inputCSS="form-control" mandatory="true"/>
                        </div>
                        <div class="form-group">
                            <formElement:formPasswordBox idKey="updatePwd.checkPwd" labelKey="updatePwd.checkPwd"
                                                         path="checkPwd" inputCSS="form-control" mandatory="true"/>
                        </div>
                        <form:hidden path="token" />
 
                        </div>
                        
                        
                        	<div class="col-md-5  col-md-push-1">
                        	<br/>
					
					<p><b><spring:theme code="updatePwd.errorMsg" />:</b></p>
		 
					<div class="update-password"><span id="atleast8Char" class="icon-check-gray"></span><span><spring:theme code="updatePwd.errorMsg1" /> </span></div>
					<div class="update-password"><span id="uppercase" class="icon-check-gray"></span><span><spring:theme code="updatePwd.errorMsg2" /></span></div>
					<div class="update-password"><span id="lowercase" class="icon-check-gray"></span><span><spring:theme code="updatePwd.errorMsg3" /></span></div>
					<div class="update-password"><span id="numeric" class="icon-check-gray"></span><span><spring:theme code="updatePwd.errorMsg4" /></span></div>
					<div class="update-password"><span id="symbol" class="icon-check-gray"></span><span><spring:theme code="updatePwd.errorMsg5" /></span></div>
					</div>
                       
                       	<div class="cl"></div>
                       	 	<br/>
                       <div class="col-sm-12 col-md-3 col-xs-12 row">
                            
                                <button type="submit" class="btn btn-primary btn-block">
                                    <spring:theme code="resetPwd.submit"/>
                                </button>
                        
<!--                             <div class="col-sm-6"> -->
<!--                                 <button type="button" class="btn btn-default btn-block backToHome"> -->
<%--                                     <spring:theme code="text.button.cancel"/> --%>
<!--                                 </button> -->
<!--                             </div> -->
                        </div>
                    
                    
                    
              
                </form:form>
           
            
            
            </div>
            
       

