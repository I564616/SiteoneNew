<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ attribute name="url" required="true" type="java.lang.String"%>
<%@ attribute name="labelKey" required="true" type="java.lang.String"%>
<%@ attribute name="urlTestId" required="false" type="java.lang.String"%>
<%@ attribute name="label" required="true" type="java.lang.String"%>
<spring:htmlEscape defaultHtmlEscape="true" />

 
	<div class="col-md-10 col-sm-9 col-xs-6"><div class="row"><h1 class="headline"><spring:theme code="${labelKey}"/></h1>
	</div>
	</div>
<div class="col-md-2 col-sm-12 col-xs-12 btn-margin">
		<ycommerce:testId code="${urlTestId}">
			<div class="row"><a href="${url}" class="btn btn-primary btn-block"><spring:theme code="text.company.addNew.button"/></a></div>
		</ycommerce:testId>
	</div>
 

<div class="account-section-header">
	<spring:theme code="${label}"/>
	<div class="account-section-header-add pull-right">
		<%-- <ycommerce:testId code="${urlTestId}">
			<a href="${url}" class="button add"><spring:theme code="text.company.addNew.button"/></a>
		</ycommerce:testId> --%>
	</div>
</div>