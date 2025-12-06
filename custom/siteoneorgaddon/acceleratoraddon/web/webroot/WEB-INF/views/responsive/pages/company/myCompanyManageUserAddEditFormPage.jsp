<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common" %>
<%@ taglib prefix="customFormElement" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/customFormElement" %>
<c:set var="memberPermissionOrderApproval" value='${featureSwitch}'></c:set>
<spring:htmlEscape defaultHtmlEscape="true" />

<c:if test="${empty saveUrl}">
	<c:choose>
		<c:when test="${not empty b2BCustomerForm.uid}">
			<spring:url value="/my-company/organization-management/manage-users/edit" var="saveUrl" htmlEscape="false">
				<spring:param name="unit" value="${unit}" />
				<spring:param name="user" value="${b2BCustomerForm.uid}"/>
			</spring:url>
		</c:when>
		<c:otherwise>
			<spring:url value="/my-company/organization-management/manage-users/create" var="saveUrl" htmlEscape="false">
				<spring:param name="unit" value="${unit}" />
			</spring:url>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${empty cancelUrl}">
	<c:choose>
		<c:when test="${not empty b2BCustomerForm.uid}">
			<spring:url value="/my-company/organization-management/manage-users/details" var="cancelUrl" htmlEscape="false">
				<spring:param name="unit" value="${unit}" />
				<spring:param name="user" value="${b2BCustomerForm.uid}"/>
			</spring:url>
		</c:when>
		<c:otherwise>
			<spring:url value="/my-company/organization-management/manage-users/${unit}" var="cancelUrl" htmlEscape="false"/>
		</c:otherwise>
	</c:choose>
</c:if>

<template:page pageTitle="${pageTitle}">
    <div class="account-section">
       
        <h1 class="headline">
            <c:choose>
                <c:when test="${not empty b2BCustomerForm.uid}">
                 <spring:theme code="text.company.${action}.edit.title" />
                   <%--  <org-common:headline url="${cancelUrl}" labelKey="text.company.${action}.edit.title"
                                         labelArguments="${fn:escapeXml(b2BCustomerForm.parentB2BUnit)}"/> --%>
                </c:when>
                <c:otherwise>
                 <spring:theme code="text.company.${action}.users.new.title" />
                    <%-- <org-common:headline url="${cancelUrl}" labelKey="text.company.${action}.users.new.title"
                                         labelArguments="${fn:escapeXml(param.unit)}"/> --%>
                </c:otherwise>
            </c:choose>
            </h1>
        </div>
        <br>
       
  
        <div class="account-section-content ">
        <form:form id="siteOneCreateUserForm" action="${saveUrl}"  method="post" modelAttribute="b2BCustomerForm">
              
              <div class="row">
                    <div class="col-sm-12 col-md-4 new-user-error">
                        <formElement:formSelectBox idKey="user.title" labelKey="user.title" path="titleCode"
                                                   mandatory="false"
                                                   skipBlank="false"
                                                   skipBlankMessageKey="form.select.empty"
                                                   selectCSSClass="form-control"
                                                   items="${titleData}" />
                                                    <span id="errortitle"></span>
                    </div>
               </div> 
                    <form:input type="hidden" name="uid" path="uid" id="uid"/>
                    <form:input type="hidden" name="langPreference" path="langPreference" value="${not empty b2BCustomerForm.langPreference ?  b2BCustomerForm.langPreference : 'English' }" id="user.langPreference" style="display:none;"/>  
					<div class="row">
						<div class="col-sm-12 col-md-4 new-user-errorSec">
							<formElement:formInputBox idKey="user.firstName"
								labelKey="user.firstName" path="firstName" inputCSS="text"
								mandatory="true" />
								 <span id="errorFirstName"></span>
								
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-4">
							<formElement:formInputBox idKey="user.lastName"
								labelKey="user.lastName" path="lastName" inputCSS="text"
								mandatory="true" />
								 <span id="errorLastName" class="new-user-errorSpan"></span>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-4">
							<c:choose>
						<c:when test="${empty b2BCustomerForm.uid}">
							<formElement:formInputBox idKey="user.email"
								labelKey="user.email" path="email" inputCSS="text"
								mandatory="true" />
								 <span id="errorEmailAddress" class="new-user-errorSpan"></span>
						</c:when>
						<c:otherwise>
						<label class=""><spring:theme code="user.email" /></label><br>
						<label class="">${b2BCustomerForm.email}</label><br>
						<form:input type="hidden" name="email" path="email" id="user.email" style="display:none;"/>  
						<span id="errorEmailAddress" class="new-user-errorSpan"></span>
						</c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-4">
							<formElement:formInputBox idKey="user.phonenumber"
								labelKey="user.phonenumber" path="phoneNumber" inputCSS="text"
								mandatory="true" />
								 <span id="errorPhoneNumber" class="new-user-errorSpan"></span>
						</div>
					</div>

				<div class="row">
					<div class="col-sm-12 col-md-4 new-user-error">
						<formElement:formSelectBox idKey="user.unit.title"
												   skipBlank="false" labelKey="text.company.user.unit.title"
												   path="parentB2BUnit" parentB2BUnitName="${parentUnitName}" parentUnitId="${b2BCustomerForm.parentB2BUnit}" selectCSSClass="form-control"
												   mandatory="true" items="${b2bUnits}"
												   disabled="${not empty param.unit and not empty param.role}"
												   skipBlankMessageKey="form.select.empty"
												    /> 
							 <span id="errorparentunit" class="new-user-errorSpan"></span>
					</div>
						
					<%-- <div class="col-xs-12">
						<div class="col-sm-6">
						<b><spring:theme code="text.company.linkShipTos" /></b><br/>
						<customFormElement:formSelectCheckbox />
						</div>
					</div> --%>
						<div class="cl"></div>	
						<br/>
						<div class="col-md-6 col-sm-12 form-group">
							<%-- <customFormElement:formCheckboxes
								idKey="text.company.user.roles"
								labelKey="text.company.user.roles" path="roles"
								items="${roles}"
								disabled="${not empty param.unit and not empty param.role}" /> --%>
							 <label class="black-title control-label "><spring:theme code="text.company.user.roles" />:</label>
							 <div class="cl"></div>	
							 <span class="hidden-xs hidden-sm"><br/></span>
							  <div class="row mobile-role" aria-label="roleCheckBox" role="radiogroup">
							 <c:forEach items="${roles}" var="role">
						 <div class="col-md-6 role-div">
				               <div class="role-box"> <span class="colored-radio"> 
				           <input type="hidden" value="${role.code}" id="role_code" />
				              <b><form:radiobutton path="roles" id="Roled_${role.code}" value="${role.code}" label="${role.name}" /></b>
				               </span>
				                <c:choose>
				               <c:when test="${role.name == 'Admin'|| role.name == 'Administrador'}">
				              	 <br/> <div class="user-text" id="Role_${role.code}"><spring:theme code="user.addUser.admin.text" /></div>
				               </c:when>
				               <c:otherwise>
				               	<br/> <div class="user-text" id="Role_${role.code}"><spring:theme code="user.addUser.member.text" />
				               	
				               	
				               	<div class="team-role" style="display:none;">
				               	<div class="cl"></div>
				               	<br/>
				               	<span class="black-title col-md-12 col-xs-12 padding0"><spring:theme code="text.company.usercansee" /></span>
                    <div class="cl"></div>	
                    <p></p>
				
						<div class="col-md-12 col-xs-12 padding0 colored user-label">
							<formElement:formCheckbox idKey="text.company.user.partnerProgramPermissions"
								labelKey="text.company.user.partnerprogrampermissionsNew" path="partnerProgramPermissions" />
								</div>
								
									<div class="col-md-12 col-xs-12 padding0 colored user-label">
							<formElement:formCheckbox idKey="text.company.user.invoicepermissions"
								labelKey="text.company.user.invoicepermissionsNew" path="invoicePermissions" checkedStatus="true" />
						</div>
						
						<div class="col-md-12 col-xs-12 padding0 colored user-label">
							<formElement:formCheckbox idKey="text.company.user.accountOverviewForParent"
								labelKey="text.company.user.accountOverviewForParentNew" path="accountOverviewForParent" checkedStatus="true" />
								</div>
						<div class="col-md-12 col-xs-12 padding0 colored user-label">
							<formElement:formCheckbox idKey="text.company.user.accountOverviewForShipTos"
								labelKey="text.company.user.accountOverviewForShipTosNew" path="accountOverviewForShipTos" />
								</div>
						<c:if  test="${unitPayBillOnline && contPayBillOnline}">			
							<div class="col-md-12 col-xs-12 padding0 colored user-label">
								<formElement:formCheckbox idKey="text.company.user.payBillOnline"
									labelKey="text.company.user.payBillOnlineNew" path="payBillOnline" />
								</div>
						</c:if>
						<c:if  test="${memberPermissionOrderApproval ne true}">
							<div class="col-md-12 col-xs-12 padding0 colored user-label">
								<formElement:formCheckbox idKey="text.company.user.placeOrder" labelKey="text.company.user.placeOrderNew" path="placeOrder" checkedStatus="true" />
							</div>
						</c:if>
						<div class="col-md-12 col-xs-12 padding0 colored user-label">
							<formElement:formCheckbox idKey="text.company.user.enableAddModifyDeliveryAddress"
								labelKey="text.company.user.enableAddModifyDeliveryAddressNew" path="enableAddModifyDeliveryAddress" checkedStatus="true" />
						</div>
						<c:if  test="${memberPermissionOrderApproval eq true}">
							<div class="col-xs-12 hidden">
								<formElement:formCheckbox idKey="text.company.user.placeOrder" labelKey="text.company.user.placeOrderNew" path="placeOrder" checkedStatus="true" />
								<input id="placeOrdersWithApproval" value="${b2BCustomerForm.needsOrderApproval}" name="needsOrderApproval" type="hidden">
							</div>
							<div class="col-xs-12 padding0 m-y-15">
								<label class="control-label m-t-5 marginBottom10 text-grey f-s-12"><spring:theme code="order.approval.permission" /></label>
							</div>
							<div class="col-xs-12 padding0 colored-radio">
								<div class="radio">
									<label class="control-label" for="placeOrdersCanNot">
										<input id="placeOrdersCanNot" name="radioForOrder" type="radio" value="placeOrdersCanNot" >
										<span class="p-l-5"><spring:theme code="order.approval.cannotplace" /></span>
									</label>
								</div>
							</div>
							<div class="col-xs-12 padding0 colored-radio">
								<div class="radio">	
									<label class="control-label" for="placeOrders">
										<input id="placeOrders" name="radioForOrder" type="radio" value="placeOrders" >
										<span class="p-l-5"><spring:theme code="order.approval.withoutapproval" /></span>
									</label>
								</div>
							</div>
							<div class="col-xs-12 padding0 colored-radio">
								<div class="radio">
									<label class="control-label" for="placeOrdersApproval">
										<input id="placeOrdersApproval" name="radioForOrder" type="radio" value="placeOrdersApproval" >
										<span class="p-l-5"><spring:theme code="order.approval.withapproval" /></span>
									</label>
								</div>
							</div>
						</c:if>
				 <div class="cl"></div>
				               	</div>
				               	</div> 
				               </c:otherwise>
				               </c:choose>
				                <div class="cl"></div>	
				           		</div>
				           		 <div class="cl"></div>	
				           </div>
				           <div class="cl hidden-md hidden-lg"><br/></div>
				            </c:forEach>
				            </div>
						</div>
						<div class="cl"></div>	
					                  
                    	
			 
					   
                          <div class="accountActions-bottom  col-md-12">
                            
                                <div class="btn-usercreation">
                                
                                
                                    <ycommerce:testId code="User_Save_button">
                                        <button type="submit" class="usercreation btn btn-block btn-primary save createUserSubmit"><spring:theme code="text.company.save.button"/></button>
                                    </ycommerce:testId>
                                </div>
                               <%--  <div class="col-sm-3 ">
                                    <ycommerce:testId code="User_Cancel_button">
                                        <a href="${cancelUrl}" class="cancel">
                                            <button type="button" class="btn btn-block btn-default"><spring:theme code="text.company.cancel.button"/></button>
                                        </a>
                                    </ycommerce:testId>
                                </div> --%>
                           
                        </div>
                </div>
            </form:form>
        </div>
    </div>
</template:page>