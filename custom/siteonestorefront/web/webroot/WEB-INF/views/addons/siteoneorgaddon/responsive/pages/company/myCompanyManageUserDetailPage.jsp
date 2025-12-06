<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="company" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/company"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common"%>
<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-company/organization-management/manage-users/${unit}" var="backToManageUsersUrl" htmlEscape="false" />
<spring:url value="/my-company/organization-management/manage-users/edit" var="editUserUrl" htmlEscape="false">
    <spring:param name="unit" value="${unit}" />
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/disable" var="disableUserUrl" htmlEscape="false">
    <spring:param name="unit" value="${unit}" />
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/enable" var="enableUserUrl" htmlEscape="false">
    <spring:param name="unit" value="${unit}" />
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/resetpassword" var="resetPasswordUrl" htmlEscape="false">
    <spring:param name="unit" value="${unit}" />
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/approvers" var="approversUrl" htmlEscape="false">
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/permissions" var="permissionsUrl" htmlEscape="false">
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/usergroups" var="usergroupsUrl" htmlEscape="false">
    <spring:param name="user" value="${customerData.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-units/details" var="unitDetailsUrl" htmlEscape="false">
    <spring:param name="unit" value="${unit}" />
    <spring:param name="unit" value="${customerData.unit.uid}" />
</spring:url>

<template:page pageTitle="${pageTitle}">
    <div class="account-section">
            <%-- <div>
                <org-common:headline url="${backToManageUsersUrl}" labelKey="text.company.manageUser.userDetails" />
            </div>
     --%>
        <div class="row">
            <div class="col-md-8 col-xs-12">
                <h1 class="headline"><span>

                                        ${fn:escapeXml(customerData.firstName)}&nbsp;${fn:escapeXml(customerData.lastName)}
                                    </span></h1>
            </div>
            <div class="col-md-4 col-xs-12 btn-margin">
                <div class="row">
                    <div class="marginRight">
                        <div class="col-md-6 col-xs-12">
                            <a href="${editUserUrl}" class="button edit btn btn-block btn-primary">
                                <spring:theme code="text.company.manageUser.button.edit" />
                            </a>
                        </div>
                        <div class="hidden-xs hidden-md hidden-lg cl"><br></div>
                        <div class="col-md-6 col-xs-12 marginLeft">


                            <c:choose>
                                <c:when test="${customerData.active}">
                                    <div class="js-action-confirmation-modal disable-link">
                                        <a class="btn btn-block btn-default" href="#" data-action-confirmation-modal-title="<spring:theme code="text.company.manageusers.disableUser"/>"
                                           data-action-confirmation-modal-id="disable" class="last">
                                            <spring:theme code="text.company.manageusers.button.disableuser" />
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${customerData.unit.active}">
                                            <div class="enable-link">
                                                <form:form action="${enableUserUrl}">
                                                    <button type="submit" class="btn btn-block btn-primary">
                                                        <spring:theme code="text.company.manageusers.button.enableuser" />
                                                    </button>
                                                </form:form>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="enable-link">
                                                <button type="button" disabled class="btn btn-block btn-default">
                                                    <spring:theme code="text.company.manageusers.button.enableuser" />
                                                </button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <p class="hidden-xs"></p>
        <div style="height:20px;></div>
        <div class="account-section-content"><br/>
        <div class="row">
            <div class="col-md-12 col-xs-12 col-no-padding">
                <div class="row">
                    <div class="col-sm-12">

                        <div class="detail-panel">
                            <div class="row">
                                <div class="col-md-2 col-xs-6">
                                    <label><span class="bold-text"><spring:theme code="text.company.user.userpassword" />:</span</label>
                                    <div class="cl"></div>
                                    <div style="font-size:7pt;">&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;&#9733;</div>
                                </div>

                                    <%--   <div class="col-md-2 col-xs-6">
                                      <a class="btn btn-block btn-default reset-pwd-btn" href="${resetPasswordUrl}"><spring:theme code="text.company.user.resetPassword"/></a>
                                      </div> --%>
                                <div class="cl"></div>
                            </div>
                        </div>
                        <%--<div class="detail-panel">--%>
                            <%--<label><span class="bold-text"><spring:theme code="text.company.manage.units.user.title" />:</span></label>--%>
                            <%--<div class="cl"></div>--%>
                            <%--<span>--%>

                                    <%--${fn:escapeXml(customerData.title)}--%>
                            <%--</span>--%>
                        <%--</div>--%>
                        <div class="detail-panel">
                            <label><span class="bold-text"><spring:theme code="text.company.manage.units.user.name" />:</span></label>
                            <div class="cl"></div>
                            <span>

                                        ${fn:escapeXml(customerData.firstName)}&nbsp;${fn:escapeXml(customerData.lastName)}
                                    </span>
                        </div>
                        <div class="detail-panel">
                            <label><span class="bold-text"><spring:theme	code="text.company.user.email" />:</span></label>
                            <div class="cl"></div>
                            <span>
                                    ${fn:escapeXml(customerData.displayUid)} </span>

                        </div>

                        <c:if test="${not empty customerData.contactNumber}">
                            <div class="detail-panel">
                                <label>
                                    <span class="bold-text"><spring:theme code="text.company.unit.contactNumber" /><span class="hidden-xs hidden-sm">:</span></span></label>
                                <div class="cl"></div>
                                <span class="hidden-xs hidden-sm">
                                        ${fn:escapeXml(customerData.contactNumber)}
                                </span>
                                <span class="hidden-lg hidden-md">
                                            <a class="tel-phone" href="tel:${fn:escapeXml(customerData.contactNumber)}">${fn:escapeXml(customerData.contactNumber)}</a>
                                    </span>
                            </div>
                        </c:if>
                        <div class="detail-panel">
                            <label>
                                <span class="bold-text"> <spring:theme code="text.company.user.account.title" />:</span></label>
                            <div class="cl"></div>
                            <span>
                                       <c:choose>
                                           <c:when test="${null != customerData.unit.reportingOrganization && customerData.unit.uid eq customerData.unit.reportingOrganization.uid}">
                                               ${fn:escapeXml(customerData.unit.name)} (<spring:theme code="accountDashboardPage.main" />)
                                           </c:when>
                                           <c:otherwise>
                                               ${fn:escapeXml(customerData.unit.name)}
                                           </c:otherwise>
                                       </c:choose>
                                    </span>
                        </div>

                        <div class="detail-panel">
                            <label>
                                <span class="bold-text"><spring:theme code="text.company.manageUser.roles" />:</span></label>
                            <div class="cl"></div>
                            <span>
                                    <c:forEach items="${customerData.roles}" var="group">
                                        <spring:theme code="b2busergroup.${group}.name" /> <br/>
                                    </c:forEach>
                                </span>
                        </div>
                        <div class="detail-panel">
                            <label>
                                <span class="bold-text"><spring:theme code="text.company.manageUser.usercansee" /></span></label>
                            <div class="cl"></div>

                            <div>
                                <c:if  test="${customerData.partnerProgramPermissions}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.partnerprogrampermissions" /></span>
                                </c:if>
                                <c:if  test="${customerData.invoicePermissions}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.invoicepermissions" /></span>
                                </c:if>
                                <c:if  test="${customerData.accountOverviewForParent}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.accountOverviewForParent" /></span>
                                </c:if>
                                <c:if  test="${customerData.accountOverviewForShipTos}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.accountOverviewForShipTos" /></span>
                                </c:if>
                                <c:if  test="${customerData.payBillOnline}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.payBillOnline" /></span>
                                </c:if>
                                  <c:if  test="${customerData.placeOrder eq true && customerData.needsOrderApproval eq false}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.placeOrder" /></span>
                                </c:if>
                                <c:if  test="${customerData.placeOrder eq true && customerData.needsOrderApproval eq true}">
                                    <span class="userPermissionText"><spring:theme code="order.approval.withapproval" /></span>
                                </c:if>
                                <c:if  test="${customerData.placeOrder eq false}">
                                    <span class="userPermissionText"><spring:theme code="order.approval.cannotplace" /></span>
                                </c:if>
                                 <c:if  test="${customerData.enableAddModifyDeliveryAddress}">
                                    <span class="userPermissionText"><spring:theme code="text.company.user.enableAddModifyDeliveryAddress" /></span>
                                </c:if>
                            </div>

                        </div>
                            <%--  <div class="detail-panel">
                                <label>
                                       <spring:theme code="text.company.user.assigned.shipto" /></label>
                               <div class="cl"></div>
                                   <span>
                                   <c:forEach items="${customerData.assignedShipTos}" var="shipTo">
                                       ${shipTo}<br/>
                                   </c:forEach>
                               </span>
                                  </div> --%>

                        <div class="detail-panel no-bottom-border hidden-xs hidden-sm">
                            <label>
                                <span class="bold-text"> <spring:theme code="text.company.user.userEnabledStatus" /></span>
                            </label>
                            <div class="cl"></div>
                            <span>
                                    <c:choose>
                                        <c:when test="${customerData.active}">
                                            <spring:theme code="text.company.manage.unit.user.enable" />
                                        </c:when>
                                        <c:otherwise>
                                            <spring:theme code="text.company.manage.unit.user.disable" />
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                        </div>

                        <div class="detail-panel hidden-md hidden-lg">
                            <label>
                                <span class="bold-text"><spring:theme code="text.company.user.userEnabledStatus" /></span>
                            </label>
                            <div class="cl"></div>
                            <span>
                                    <c:choose>
                                        <c:when test="${customerData.active}">
                                            <spring:theme code="text.company.manage.unit.user.enable" />
                                        </c:when>
                                        <c:otherwise>
                                            <spring:theme code="text.company.manage.unit.user.disable" />
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                        </div>
                    </div>
                </div>
                    <%--  <div class="col-sm-2">
                         <div class="item-action">
                             <a href="${editUserUrl}" class="button edit btn btn-block btn-primary">
                                 <spring:theme code="text.company.manageUser.button.edit" />
                             </a>
                         </div>
                     </div>  --%>
            </div>
            <div class="accountActions-link">


            </div>
            <company:actionConfirmationModal id="disable" targetUrl="${disableUserUrl}" messageKey="text.company.manageuser.disableuser.confirmation" messageArguments="${customerData.name}"/>
                <%-- <div class="account-list">
                     Approvers
                    <org-common:selectEntityHeadline url="${approversUrl}" labelKey="text.company.manage.units.header.approvers" />
                    <c:if test="${not empty customerData.approvers}">
                        <div class="account-cards">
                            <div class="row">
                                <c:forEach items="${customerData.approvers}" var="user">
                                    <spring:url value="/my-company/organization-management/manage-users/details" var="approverUrl" htmlEscape="false">
                                        <spring:param name="user" value="${user.email}" />
                                    </spring:url>
                                    <div class="col-xs-12 col-sm-6 col-md-4 card">
                                        <ul class="pull-left">
                                            <li>
                                                <ycommerce:testId code="user_name_link_details">
                                                    <a href="${approverUrl}">${fn:escapeXml(user.name)}</a>
                                                </ycommerce:testId>
                                            </li>
                                            <li>
                                                <ycommerce:testId code="user_email">
                                                    ${fn:escapeXml(user.email)}
                                                </ycommerce:testId>
                                            </li>
                                        </ul>
                                        <div>
                                            <span class="js-action-confirmation-modal remove">
                                                <a href="#" data-action-confirmation-modal-title="<spring:theme code="text.company.users.remove.confirmation.title.b2bapprovergroup"/>"
                                                   data-action-confirmation-modal-id="removeApprover-${ycommerce:normalizedCode(user.uid)}">
                                                    Remove
                                                  </a>
                                            </span>
                                            <spring:url value="/my-company/organization-management/manage-users/approvers/remove/" var="removeApproverUrl" htmlEscape="false">
                                                <spring:param name="user" value="${customerData.uid}" />
                                                <spring:param name="approver" value="${user.uid}" />
                                            </spring:url>
                                            <company:actionConfirmationModal id="removeApprover-${ycommerce:normalizedCode(user.uid)}" targetUrl="${removeApproverUrl}"
                                                                             messageKey="text.company.users.remove.confirmation.b2bapprovergroup" messageArguments="${user.uid}, ${customerData.uid}"
                                                                             actionButtonLabelKey="text.company.delete.button" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    Permissions
                    <org-common:selectEntityHeadline url="${permissionsUrl}" labelKey="text.company.manageUser.permission.title" />
                    <c:if test="${not empty customerData.permissions}">
                        <div class="account-cards">
                            <div class="row">
                                <c:forEach items="${customerData.permissions}" var="permission">
                                    <div class="col-xs-12 col-sm-6 col-md-4 card">
                                        <company:permissionCardDetails permission="${permission}" action="permission" listCSSClass="pull-left"/>
                                        <div>
                                            <span class="js-action-confirmation-modal remove">
                                                <a href="#" data-action-confirmation-modal-title="<spring:theme code="text.company.users.remove.confirmation.title.permission"/>"
                                                   data-action-confirmation-modal-id="removePermission-${ycommerce:normalizedCode(permission.code)}">
                                                   Remove
                                                </a>
                                            </span>
                                            <spring:url value="/my-company/organization-management/manage-users/permissions/remove/" var="removePermissionUrl" htmlEscape="false">
                                                <spring:param name="user" value="${customerData.uid}" />
                                                <spring:param name="permission" value="${permission.code}" />
                                            </spring:url>
                                            <company:actionConfirmationModal id="removePermission-${ycommerce:normalizedCode(permission.code)}" targetUrl="${removePermissionUrl}"
                                                                             messageKey="text.company.users.remove.confirmation.permission" messageArguments="${permission.code}, ${customerData.uid}"
                                                                             actionButtonLabelKey="text.company.delete.button" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    User Groups
                    <org-common:selectEntityHeadline url="${usergroupsUrl}" labelKey="text.company.manageUser.usergroups.title" />
                    <c:if test="${not empty customerData.permissionGroups}">
                        <div class="account-cards">
                            <div class="row">
                                <c:forEach items="${customerData.permissionGroups}" var="group">
                                    <div class="col-xs-12 col-sm-6 col-md-4 card">
                                        <ul class="pull-left">
                                            <li>
                                                <ycommerce:testId code="permissiongroup_id_link">
                                                    <spring:url value="/my-company/organization-management/manage-usergroups/details/" var="permissionGroupUrl" htmlEscape="false">
                                                        <spring:param name="usergroup" value="${group.uid}" />
                                                    </spring:url>
                                                    <c:choose>
                                                        <c:when test="${group.editable}">
                                                            <a href="${permissionGroupUrl}">${fn:escapeXml(group.uid)}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${fn:escapeXml(group.uid)}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ycommerce:testId>
                                            </li>
                                            <li>
                                                <ycommerce:testId code="permissiongroup_name_link">
                                                    ${fn:escapeXml(group.name)}
                                                </ycommerce:testId>
                                            </li>
                                            <li>
                                                <ycommerce:testId code="permissiongroup_parentunit_link">
                                                    <spring:url value="/my-company/organization-management/manage-units/details" var="parentUnitUrl" htmlEscape="false">
                                                        <spring:param name="unit" value="${group.unit.uid}" />
                                                    </spring:url>
                                                    <c:choose>
                                                        <c:when test="${group.editable}">
                                                            <a href="${parentUnitUrl}">${fn:escapeXml(group.unit.name)}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${fn:escapeXml(group.unit.name)}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ycommerce:testId>
                                            </li>
                                        </ul>
                                        <div >
                                            <span class="js-action-confirmation-modal remove">
                                                <a href="#" data-action-confirmation-modal-title="<spring:theme code="text.company.users.remove.confirmation.title.usergroup"/>"
                                                   data-action-confirmation-modal-id="removeUserGroup-${ycommerce:normalizedCode(group.uid)}">
                                                    Remove
                                                </a>
                                            </span>
                                            <spring:url value="/my-company/organization-management/manage-users/usergroups/remove/" var="removeUserGroupUrl" htmlEscape="false">
                                                <spring:param name="user" value="${customerData.uid}" />
                                                <spring:param name="usergroup" value="${group.uid}" />
                                            </spring:url>
                                            <company:actionConfirmationModal id="removeUserGroup-${ycommerce:normalizedCode(group.uid)}" targetUrl="${removeUserGroupUrl}"
                                                                             messageKey="text.company.users.remove.confirmation.permission" messageArguments="${group.uid}, ${customerData.uid}"
                                                                             actionButtonLabelKey="text.company.delete.button" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>  --%>
                <%--   <div class="row">
                      <div class="col-xs-12 col-sm-5 col-md-4">
                          <div class="accountActions-bottom">
                              <org-common:back cancelUrl="${backToManageUsersUrl}" displayTextMsgKey="text.company.manageUsers.back.button" />
                          </div>
                      </div>
                  </div> --%>
        </div>
        <div class="cl"></div>
    </div>
    </div>
    <div class="cl"></div>
</template:page>