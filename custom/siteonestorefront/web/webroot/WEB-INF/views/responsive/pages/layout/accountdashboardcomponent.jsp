<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <c:set var="authenticated" value="false"/>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<%-- <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
<c:set var="authenticated" value="true"/>
<c:set var="noBorder" value=""/>
<div class="links">
<div class="headline">
   <spring:theme code="header.link.account"/>
</div>

<div>
		<p><a href = "<c:url value = "/my-account/account-dashboard"/>"><spring:theme code="homepage.accountDashboard"/></a></p>
		<p><a href = "<c:url value = "/my-account/update-siteoneprofile"/>"><spring:theme code="homepage.personalDetails"/></a></p>
		<p><a href = "<c:url value = "/my-account/update-siteonepreference"/>"><spring:theme code="homepage.preferences"/></a></p>
		<p><a href = "<c:url value = "/my-account/orders/${sessionShipTo.uid}"/>"><spring:theme code="homepage.orderHistory"/></a></p>
		<p><a href = "<c:url value = "/savedList"/>"><spring:theme code="homepage.myLists"/></a></p>
</div>
</div>
</sec:authorize> --%>


<div class="links" style="border-radius:6px">
	<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
		<c:set var="authenticated" value="true"/>
		<c:set var="noBorder" value=""/>

		<h1 class="headline">
		   <spring:theme code="header.link.account"/>
		</h1>

		<div>
				<p><a href = "<c:url value = "/my-account/account-dashboard"/>"><spring:theme code="homepage.accountDashboard"/></a></p>	
				<p><a href = "<c:url value = "/my-account/update-siteoneprofile"/>"><spring:theme code="homepage.personalDetails"/></a></p>			
				<p><a href = "<c:url value = "/my-account/orders/${sessionShipTo.uid}"/>" class="account-history"><spring:theme code="homepage.orderHistory"/></a></p>
				<p><a href = "<c:url value = "/savedList"/>"><spring:theme code="homepage.myLists"/></a></p>
				<p><a href = "<c:url value = "/my-account/purchased-products/${sessionShipTo.uid}"/>"><spring:theme code="homepage.purchasedProducts"/></a></p>
		</div>
	</sec:authorize>

	<c:if test="${authenticated eq 'false'}">
		<form:form id="loginForm" action="${homelink}j_spring_security_check" method="post">
		<div class="form-group">
			<div class="pod-signin">
				<h1 class="headline">
				   <spring:theme code="accountdashboardcomponent.signin" />
				</h1>
					<!-- <input type="text" placeholder="Email Address"/>
					<input type="password" name="" placeholder="password"> -->
					<input id="j_username" name="j_username" type="text" value="" placeholder="Email" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Email'" autocomplete="off"/>
					<input id="j_password" name="j_password" type="password" value="" autocomplete="off" placeholder="<spring:theme code="text.preference.password"/>" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'" >
					<div class="col-md-12 rememberSec"><div class="row">
                       <span class="colored"> <input type="checkbox" class="remember-checkbox" tabindex="3" value="remember" id="remember"> </span><spring:theme code="accountdashboardcomponent.remember.me" />
                    </div></div>
					<div class="row">
						<div class="col-md-6 col-lg-6">
							<button type="submit" class="btn btn-primary btn-block margin20" id="loginsubmit">
								<spring:theme code="accountdashboardcomponent.signin" /></button>
						</div>
					</div>
					<div class="forgot-password"><a href="#" data-link="/login/pw/request" class="js-password-forgotten" data-cbox-title="Send Reset Email">
							<spring:theme code="accountdashboardcomponent.forgot.password" /></a></div>
							
					<!-- <a class="btn btn-default" href=<c:url value="/request-account/form"/>">Request an Accountt</a> -->
					<div class="request-account-wrapper">
						<a href="<c:url value="/request-account/form"/>" class="desktop-request-account"><spring:theme code="accountdashboardcomponent.request.account" /></a>
						<a href="<c:url value="/request-account/form"/>" class="mobile-request-account"><spring:theme code="accountdashboardcomponent.request.account" /></a>
					</div>
				</div>
			</div>
		</form:form>
	</c:if>

</div>


 

