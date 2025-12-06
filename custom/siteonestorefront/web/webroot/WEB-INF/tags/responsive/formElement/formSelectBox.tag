<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="idKey" required="true" type="java.lang.String" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="path" required="true" type="java.lang.String" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemValue" required="false" type="java.lang.String" %>
<%@ attribute name="parentB2BUnitName" required="false" type="java.lang.String" %>
<%@ attribute name="itemLabel" required="false" type="java.lang.String" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelCSS" required="false" type="java.lang.String" %>
<%@ attribute name="selectCSSClass" required="false" type="java.lang.String" %>
<%@ attribute name="skipBlank" required="false" type="java.lang.Boolean" %>
<%@ attribute name="skipBlankMessageKey" required="false" type="java.lang.String" %>
<%@ attribute name="selectedValue" required="false" type="java.lang.String" %>
<%@ attribute name="tabindex" required="false" rtexprvalue="true" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="parentUnitId" required="false" type="java.lang.String" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="shipToopenPopup" value="${idKey == 'user.unit.title' or idKey == 'childUnit' }" />

<template:errorSpanField path="${path}">
	<ycommerce:testId code="LoginPage_Item_${idKey}">
			<label class="control-label ${labelCSS}" for="${idKey}">
				<spring:theme code="${labelKey}"/>
				<c:if test="${mandatory != null && mandatory == true}">
					<span class="mandatory">
						<spring:theme code="login.required" var="loginRequiredText" />
					</span>
				</c:if>
					<span class="skip">
						<form:errors path="${path}"/>
					</span>
			</label>
			<c:forEach var="item" items="${items}" varStatus="status">
			</c:forEach>
			
			
			
			<div class="control select-grey">
				<form:select id="${idKey}" path="${path}" cssClass="${selectCSSClass} ${ shipToopenPopup ? 'ship-TosSelect' : '' }" tabindex="${tabindex}" disabled="${disabled}">
					 
					<c:if test="${skipBlank == null || skipBlank == false}">
					<c:choose>
					  <c:when test="${not empty parentUnitId && not empty parentB2BUnitName}">
					    <option value="${parentUnitId}">
					       <spring:theme code='${parentB2BUnitName}'/>
						</option>
					  </c:when>
					<c:otherwise>
						<option value="" disabled="disabled" ${empty selectedValue ? 'selected="selected"' : ''}>
							<spring:theme code='${skipBlankMessageKey}'/>
						</option>
					</c:otherwise>
					</c:choose>
					</c:if>
					 	<c:choose>
					<c:when test= "${(idKey == 'user.unit.title' or idKey == 'childUnit')}">
					<c:forEach var="item" items="${items}" varStatus="status">					
					<c:if test="${status.index le 3}"> 
					<option value="${not empty itemValue ? itemValue : item.code}" >${not empty label ? label :item.name}</option>
					 </c:if>
					 </c:forEach>
					</c:when>
					<c:otherwise>
					<form:options items="${items}" itemValue="${not empty itemValue ? itemValue :'code'}" itemLabel="${not empty itemLabel ? itemLabel :'name'}"/>
					</c:otherwise>					
					</c:choose>
                    
				<c:if test="${shipToopenPopup && items.size() gt 4}">
				<option value="shipToopenPopup" class="ship-to-link removeSortbySession">Search more Ship-To's</option>
				</c:if>
				 
				</form:select>
			</div>
	</ycommerce:testId>
</template:errorSpanField>