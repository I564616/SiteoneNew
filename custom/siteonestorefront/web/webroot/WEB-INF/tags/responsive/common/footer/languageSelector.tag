<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="languages" required="true" type="java.util.Collection"%>
<%@ attribute name="currentLanguage" required="true" type="de.hybris.platform.commercefacades.storesession.data.LanguageData"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>  
<c:if test="${cmsPage.uid ne 'choosePickupDeliveryMethodPage' && cmsPage.uid ne 'orderSummaryPage' && cmsPage.uid ne 'multiStepCheckoutSummaryPage' && cmsPage.uid ne 'orderConfirmationPage'}">
	<c:url value="/_s/language" var="setLanguageActionUrl" />
	<form:form action="${setLanguageActionUrl}" method="post" id="lang-form">
		<div class="">
			<spring:theme code="text.language" var="languageText" />
			<label class="control-label sr-only" for="lang-selector">${languageText}</label>
			<input type="hidden" name="code" value=''></input>
			<input type="hidden" name="currentLanguage" value="${currentLanguage.isocode}" />
			<div class="language-value">
				<div class="pointer flex-center">
				<span class="hidden-sm hidden-xs">${currentLanguage.nativeName}</span>
				<span class="hidden-lg hidden-md font-14-xs l-h-14 text-uppercase">${currentLanguage.isocode}</span>
				<span class="glyphicon glyphicon-chevron-down f-s-7 f-s-9-xs-px f-s-9-sm-px p-l-5"></span></div>
				<ul class="margin0 f-s-14-xs-px f-s-14-sm-px language-dropdown">
					<c:forEach items="${languages}" var="lang">
						<c:choose>
							<c:when test="${lang.isocode == currentLanguage.isocode}">
								<li data-global-linkname="language: ${lang.nativeName}" data-value="${lang.isocode}" class="b-b-grey lang-item flex-center" lang="${lang.isocode}"><common:headerIcon iconName="flag-us" iconFill="none" iconColor="#ffffff" iconColorSecond="#fff" width="20" height="16" display="m-r-10" viewBox="0 0 170.08 113.39" />${lang.nativeName}</li>
								<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
							</c:when>
							<c:otherwise>
								<li data-global-linkname="language: ${lang.nativeName}" data-value="${lang.isocode}" lang="${lang.isocode}" class="b-b-grey lang-item flex-center"><common:headerIcon iconName="flag-us" iconFill="none" iconColor="#ffffff" iconColorSecond="#fff" width="20" height="16" display="m-r-10" viewBox="0 0 170.08 113.39" />${lang.nativeName}</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<li class="country-ca">
						<a data-global-linkname="language: Canada" class="canada-text flex-center" style="color: inherit;" href="https://www.siteone.ca"><common:headerIcon iconName="flag-canada" iconFill="none" iconColor="#ffffff" iconColorSecond="#fff" width="20" height="16" display="m-r-10" viewBox="0 0 170.08 113.39" />Canada</a>
					</li>
				</ul>
			</div>
		</div>
	</form:form>
</c:if>