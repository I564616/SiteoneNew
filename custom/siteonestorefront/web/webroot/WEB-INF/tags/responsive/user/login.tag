<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<c:set var="hideDescription" value="checkout.login.loginAndCheckout" />

 
<div class="headline"><spring:theme code="login.title" /></div>
<br/>
<p><b><spring:theme code="login.subtitle" /></b></p>
<%-- <h1 class="headline3 mobile hidden-md hidden-lg hidden-sm" style="margin-top:0px;"><spring:theme code="login.title" /></h1> --%>
  <br/>
<%-- <c:if test="${actionNameKey ne hideDescription}">
	<p>
		<spring:theme code="login.description" />
	</p>
</c:if> --%>
 
 
<form:form action="${action}" method="post" modelAttribute="loginForm">
	<c:if test="${not empty message}">
		<span class="has-error"> <spring:theme code="${message}" />
		
		</span>
	</c:if>	
	
	<div class="login-inputs">
		<formElement:formInputBox idKey="j_username" labelKey="login.email"
			path="j_username" mandatory="true" autocomplete="off"/>
		<formElement:formPasswordBox idKey="j_password"
			labelKey="login.password" path="j_password" inputCSS="form-control"
			mandatory="true" />
	 </div>
	 <div class="col-md-7  hidden-sm hidden-xs marginBottom20">
			<div class="forgot-password row">
				<ycommerce:testId code="login_forgotPassword_link">
					<a href="#" data-link="<c:url value='/login/pw/request'/>" class="js-password-forgotten forgottenPassword" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
						<spring:theme code="login.link.forgottenPwd" />
					</a>
					
				</ycommerce:testId>
			</div>
		</div>
	<div class="col-md-12 hidden"><div class="row">
            <span class="colored"> <input type="checkbox" tabindex="3"   class="remember-checkbox" value="remember" id="remember"/></span><spring:theme code="login.remember.me" /> 
      </div></div>
      <div class="cl"></div>
	<!-- <div class="col-md-12"><div class="row">
	<span class="colored"><input type="checkbox"/></span> Remember Me
	
	</div></div> -->
	<span class="hidden-xs hidden-sm"><br/></span>
	

	<div class="login-btn col-md-3 login-checkout">
		<div class="row">
	<ycommerce:testId code="loginAndCheckoutButton">
			<button type="submit" class="btn btn-primary btn-block" id="loginsubmit">
				<spring:theme code="${actionNameKey}" />
			</button>
		</ycommerce:testId>
	</div>
	</div>
		
	
	<div class="col-xs-12 hidden-md hidden-lg">
			<div class="forgot-password">
			<div class="row">
			<div class="cl"></div>
				<ycommerce:testId code="login_forgotPassword_link">
					<span class="hidden-xs"><br/></span><a href="#" data-link="<c:url value='/login/pw/request'/>" class="js-password-forgotten forgottenPassword" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
						<spring:theme code="login.link.forgottenPwd" />
					</a>
					
				</ycommerce:testId>
				</div>
			</div>
		</div>
		
	<c:if test="${expressCheckoutAllowed}">
		<button type="submit" class="btn btn-default btn-block expressCheckoutButton"><spring:theme code="text.expresscheckout.header" text="Express Checkout"/></button>
		<input id="expressCheckoutCheckbox" name="expressCheckoutEnabled" type="checkbox" class="form left doExpressCheckout display-none" />
	</c:if>
</form:form>

 
 