<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<spring:url value="/my-company/organization-management/manage-users/create" var="manageUsersUrl" htmlEscape="false">
	<spring:param name="unit" value="${unit}" />
</spring:url>
<spring:url value="/my-account/account-dashboard" var="myaccountUrl"></spring:url>
 

<c:set var="searchUrl" value="/my-company/organization-management/manage-users/${unit}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>


<jsp:useBean id="additionalParams" class="java.util.HashMap"/>
<c:set target="${additionalParams}" property="user" value="${param.user}" />
<input type="hidden" name="searchParam" id="manager-user-voice" value="${searchParam}"/>
<input type="hidden" id="cmspageuid" value="${cmsPage.uid}">


<template:page pageTitle="${pageTitle}">

<div class="user-datasearch">

        <org-common:listHeadline url="${manageUsersUrl}" labelKey="text.company.manageusers.label"  label=""  urlTestId="User_AddUser_button"/>
	
	 <form:form  id="MangerusersearchForm" method='GET' modelAttribute="MangerusersearchForm">
		<div class="invoice-serach-sec col-md-12 padding0" id="Mangerusers">
			<label class="print-hidden"><spring:theme code="Userolepage.search.invoices" /></label>
			<label class="print-visible hidden"></label>
					<div class="cl hidden-md hidden-lg print-hidden"></div>
					<div id="Mangerusers">
					
					<div class="col-md-3 col-sm-6 col-xs-9 print-hidden">	
						<input type="text" type="text" id="manager-user-voice" name="searchParam" value="${searchParam}" placeholder="<spring:theme 
						code="text.name.admin.team"/>" class="form-control"/></div></div>
			<div class="col-md-6 col-xs-12 search-manager">
				<div class="row">
					<div class="col-md-1 col-xs-1 print-hidden"><div class="margin-label"><button class="btn btn-primary manager-user-Button"><spring:theme code="invoicelistingpage.search.go" /></button></div></div>
				</div>
			</div>
		</div>
</form:form>

        
                <div class="account-section-content invoice_table">
                <div class="account-orderhistory">
              
            <div class="cl"></div>
             <br/>         	                    	            
                 <c:if test="${not empty searchPageData.results}">  
            <div class="Admin_user_filter">
         <c:if test ="${filterAdmin eq true}">         
         		<span class="colored"><form:checkbox name="filterAdmin" path="filterAdmin" id="admin_User" checked="true" value="${filterAdmin}" style=" margin-top: 0px;"/></span><label  class="accnt-text-owner bold-text"><spring:theme code="manage.user.filter.admin" /></label>
			 </c:if>
			 <c:if test ="${filterAdmin eq false}">         
         		<span class="colored"><form:checkbox name="filterAdmin" path="filterAdmin" id="admin_User" unchecked="true" value="${filterAdmin}" style=" margin-top: 0px;"/></span><label  class="accnt-text-owner bold-text"><spring:theme code="manage.user.filter.admin" /></label>
			 </c:if>
			 </div>
              </c:if>
                    <div class=" account-orderhistory-pagination invoiceTop-section invoiceTop-pagination user-pagination">
                        <pag:invoiceListPagePagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" 
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUser.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
    	                    	 <br/>    
                    	<div class="total_count">

                     		<c:choose>
           				 		<c:when test = "${not empty searchParam}">
           				 		<c:if test="${totalCount>1 }">
									 ${totalCount}&nbsp;<spring:theme code="text.company.manageUser.results"/>&nbsp;"${searchParam}"
								</c:if>
								<c:if test="${totalCount == 1 }">
									 ${totalCount}&nbsp;<spring:theme code="text.company.manageUser.result"/>&nbsp;"${searchParam}"
								</c:if>
								</c:when>
								<c:otherwise>
								<c:if test="${totalCount>1 }">
			 						 ${totalCount}&nbsp; <spring:theme code="text.company.manageUser.results.role"/> 
			 						 </c:if>
			 						 <c:if test="${totalCount == 1 }">
			 						 ${totalCount}&nbsp; <spring:theme code="text.company.manageUser.result.role"/> 
			 						 </c:if>
								</c:otherwise>
					 		</c:choose >
						</div>
						</div>
      				
					  <span class="hidden-xs hidden-sm"></span>
					  <c:choose>
            <c:when test="${not empty searchPageData.results}">
					  <div class="data-table">
					  
					  <div class="title-bar hidden-xs hidden-sm">
						<div class="col-md-3"><spring:theme code="text.company.column.name.name"/></div>
						<div class="col-md-3"><spring:theme code="text.company.column.roles.name"/></div>
						<div class="col-md-3"><spring:theme code="text.company.status.title"/></div>
						<div class="col-md-3"><spring:theme code="text.company.column.shipto.name"/></div>
					</div>
					  
					  
                    <div class="account-overview-table">
                        
                           
                            <c:forEach items="${searchPageData.results}" var="user">
                            <div class="data-row">
                                 <spring:url value="/my-company/organization-management/manage-users/details/"
                                            var="viewUserUrl" htmlEscape="false">
                                    <spring:param name="unit" value="${unit}" />
                                    <spring:param name="user" value="${user.uid}"/>
                                </spring:url>
                                <spring:url value="/my-company/organization-management/manage-units/details/"
                                            var="viewUnitUrl" htmlEscape="false">
                                    <spring:param name="unit" value="${unit}" />
                                </spring:url> 

                                 
                                
                                  <div class="col-xs-12 col-md-3 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.company.column.name.name"/></div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                             <ycommerce:testId code="my-company_username_label"> 
                                            <a href="${viewUserUrl}" class="responsive-table-link">${fn:escapeXml(user.name)}
                                            </a>
                                       </ycommerce:testId>
		                            </div>
		                            </div>
                                     
                                    
                                    <div class="col-xs-12 col-md-3 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.company.column.roles.name"/></div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                             <ycommerce:testId code="my-company_user_roles_label">
                                            <c:forEach items="${user.roles}" var="role">
                                                <spring:theme code="b2busergroup.${role}.name"/><br/>
                                            </c:forEach>
                                        </ycommerce:testId>
		                            </div>
		                            </div>
                                    
                                    
                                    <div class="col-xs-12 col-md-3 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"> <spring:theme code="text.company.status.title"/></div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                             <ycommerce:testId code="costCenter_status_label">
                                            <c:choose>
                                                <c:when test="${user.active}">
                                                    <span><spring:theme code="text.company.status.active.true"/></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="account-status-inactive"><spring:theme code="text.company.status.active.false"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </ycommerce:testId>
		                            </div>
		                            </div>
                                    
                                    
                                    <div class="col-xs-12 col-md-3 col-sm-12 padding-LeftZero">
		                            <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"> <spring:theme code="text.company.column.shipto.name"/></div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
		                             <ycommerce:testId code="my-company_user_unit_label">
                                            <span  class="responsive-table-link">${fn:escapeXml(user.unit.name)}</span>
                                        </ycommerce:testId>
		                            </div>
		                            </div>
                                  <div class="cl"></div>
                        </div>    
                            </c:forEach>
                           
                         <div class="cl"></div>
                    </div>
                     <div class="cl"></div>
				</div>
                     <div class="account-orderhistory-pagination sorting-hidden user-pagination">
                        
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUser.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    </div>
                </div>
                 </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-6 col-md-push-3  col-sm-12 view-account-users ">
                        <div class="account-section-content content-empty">
                        <p class="h2"><spring:theme code="text.company.manageUser.button.errorMessage.myAccount"/></p>
                        <br>
                        <p><spring:theme code="text.company.manageUser.noUser"/></p>
                         <a href="${myaccountUrl}" class="button edit btn btn-block btn-default">
                                <spring:theme code="text.company.manageUser.button.goto.myaccount" />
                            </a>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
	</br>
 </div>

</template:page>

