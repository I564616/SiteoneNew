				<%@ page trimDirectiveWhitespaces="true"%>
				<%@ taglib prefix="c" uri="jakarta.tags.core"%>
				<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
				<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
				<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
				<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
				<%@ taglib prefix="formElement"
					tagdir="/WEB-INF/tags/responsive/formElement"%>
				<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
				<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
				
			<input type="hidden" id="promo" value="${emailPromo}" />
	<input type="hidden" id="promo2" value="${emailTopic}" />
	<input type="hidden" id="preference" value='${emailTopicPreference}' />
	<input type="hidden" id="promo1" value="${orderPromo}"/>
	<h1 class="headline"><spring:theme code="siteOneaccountPreference.email.preferences" /></h1>
	<div class="row">
		<div id="success" class="col-md-12"></div>
	</div>
	<div class="cl"></div>
	
<%--  <form id="SaveCustomerEmailPrefernce"> --%>
	<p class="margin20">
		<spring:theme code="siteOneaccountPreference.text.1" /> <span
			class="hidden-xs hidden-sm"><br /></span><spring:theme code="siteOneaccountPreference.text.2" /> <a href="<c:url value="/privacypolicy"/>"><spring:theme code="siteOneaccountPreference.text.3" /></a>
	</p>
	<br />
<div class="emailsubscribe" style="display: none;" ><p class="panel-body" style="color:red; margin-left: 13px;">
	<spring:theme code="text.preference.errorMsg"/></p>
	</div>
	<div class="col-md-12 row">
		<div class="colored-radio">
			<input type="radio" id="subscribeEmail" class="subscribeEmail"
				name="subsEmail" value="Yes" />
		</div>
		<label for="subscribeEmail"  class="black-title"><spring:theme code="siteOneaccountPreference.text.4" /></label>
	</div>
	<div class="cl"></div>
	 <br/>
	<div class="col-md-12 row">
		<div class="colored-radio">
			<input type="radio" id="unSubscribeEmail" class="subscribeEmail"
				name="subsEmail" value="No">
		</div>
		<label for="unSubscribeEmail" class="black-title"><spring:theme code="siteOneaccountPreference.text.5" /></label>
	</div>
	<div class="cl"></div>
	 <br/>
	<p class="black-title margin20">
		<b><spring:theme code="siteOneaccountPreference.text.6" /></b>
	</p>

	<c:forEach var="topic" items="${emailTopic}" varStatus="status">
		<div class="col-md-4 row">
			<c:set var="eTopic" value="${topic}" />

			<label><span class="colored"><input type="checkbox"
					class="ads_Checkbox" id="ads_Checkbox${status.index}"
					value="${topic}"></span>
					<c:if test="${topic eq 'All categories'}">
						 &nbsp;<spring:theme code="account.preferences.allCategories"/> 
					</c:if>
					<c:if test="${topic eq 'Landscape supplies'}">
						 &nbsp;<spring:theme code="account.preferences.landscape"/> 
					</c:if>
					<c:if test="${topic eq 'Pest solutions'}">
						 &nbsp;<spring:theme code="account.preferences.pestSolutions"/> 
					</c:if>
					<c:if test="${topic eq 'Irrigation'}">
						 &nbsp;<spring:theme code="account.preferences.irrigation"/> 
					</c:if>
					<c:if test="${topic eq 'Fertilizer & seed'}">
						 &nbsp;<spring:theme code="account.preferences.fertilizer&seed"/> 
					</c:if>
					<c:if test="${topic eq 'Ice melt / winter products '}">
						 &nbsp;<spring:theme code="account.preferences.icemelt"/> 
					</c:if>
					<c:if test="${topic eq 'Outdoor lighting '}">
						 &nbsp;<spring:theme code="account.preferences.outdoorLighting"/> 
					</c:if>
					<c:if test="${topic eq 'Tools / safety / equipment'}">
						 &nbsp;<spring:theme code="account.preferences.toolsSafetyEquipment"/> 
					</c:if>
					<c:if test="${topic eq 'Nursery'}">
						 &nbsp;<spring:theme code="account.preferences.nursery"/> 
					</c:if>
					<c:if test="${topic eq 'Hardscapes / outdoor living'}">
						 &nbsp;<spring:theme code="account.preferences.hardscapes"/> 
					</c:if>
					<c:if test="${topic eq 'Golf'}">
						 &nbsp;<spring:theme code="account.preferences.golf"/> 
					</c:if>
				</label> 
		</div>
	</c:forEach>
<%-- 	<c:if test="${isAdmin}">
		<div class="col-md-12 row">
			<p class="black-title margin20">I would like to receive the
				following admin emails:</p>
		</div>
		<div class="col-md-12 row">
			<label> <span class="colored"> <input type="checkbox"
					name="emailOrderOption" id="emailOrderOption" /></span>All order
				notification emails for this account
			</label>
		</div>
	</c:if> --%>
	<div class="cl"></div>
	<div class="row">
		<div class="col-md-12">
			<button id="savePref" class="btn btn-primary btn-block"><spring:theme code="siteOneaccountPreference.text.7" /></button>
		</div>
	</div>
	<div class="cl"></div>
	<div class="cl"></div>
<%-- </form> --%>