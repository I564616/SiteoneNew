<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<div id="manage-ewallet" >

			<div class="manage-card-ewallet">
	<div
					class=" ewallet_manage_details col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden  manage_credit_title"><spring:theme code="text.ewallet.column.name.number" />:</label>
					<div class="col-md-6 col-sm-6 col-xs-5 ">
						XX<span id="ewallet-manage-card-number"></span>

					</div>
				</div>
				<div
					class=" ewallet_manage_details col-sm-12 col-xs-12 col-md-12  ">
					<label class="print-hidden  manage_credit_title"><spring:theme code="text.ewallet.column.name.name" />:</label>
					<div class="col-md-6 col-sm-6 col-xs-5 ">
						<span id="ewallet-manage-card-name"></span>

					</div>
				</div>

				<div
					class=" ewallet_manage_details col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden manage_credit_title"><spring:theme code="text.ewallet.column.name.date" />:</label>
					<div class="col-md-6 col-sm-6 col-xs-5 ">
						<span id="ewallet-manage-card-date"></span>

					</div>
				</div>

				<div
					class="ewallet_manage_details col-sm-12 col-xs-12 col-md-12">
					<label class="print-hidden  manage_credit_title"><spring:theme code="text.ewallet.column.name.type" />:</label>
					<div class="col-md-6 col-sm-6 col-xs-5 ">
						<span id="ewallet-manage-card-type"></span>

					</div>
				</div>

				<div
					class="ewallet_manage_details  col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden  manage_credit_title"><spring:theme code="text.ewallet.column.name.nickName" />:</label>
					<div class="col-md-6 col-sm-6 col-xs-5 ">
						<span id="ewallet-manage-card-nick-name"></span>

					</div>
				</div>
				</div>
				<div class="ewallet_manage_details  col-sm-12 col-xs-12 col-md-12">
					
					<div class="revoke_access">


					</div>
				</div>

				<div
					class="ewallet_manage_details  col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden revoke_member ">
					<spring:theme code="text.ewallet.column.name.message" />:</label>

				</div>

  <div class="radiobuttons_revokeselection">
	       		 <span class="grant-revoke-mode">
		        	 <div class="radio-wrapper col-xs-12 col-sm-12 ">
		                	<div class="colored-radio ">
		                		<input type="radio" id="grantAccess"
											name="radio-group" value="Assign">
		                	</div>
		                	<label for="grantAccess">
		                		<spring:theme code="text.ewallet.column.name.grant.access" />
		                	</label>
		                </div>
		                <div class="radio-wrapper col-xs-12 col-sm-12 ">
		                	<div class="colored-radio ">
		                		<input type="radio" id="revokeAccess"
											name="radio-group" value="Revoke">
		                	</div>
		                	<label for="revokeAccess">
		                		<spring:theme code="text.ewallet.column.name.revoke.access" />
		                	</label>
		                </div>



			     </span>
	        </div>
	       
	        
<div class="grantAccessData row">

<div
					class="ewallet_manage_grantRevoke  col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden revoke_member ">
					<spring:theme code="text.ewallet.column.name.message.grant.team.member" /></label>

				</div>
				
            <c:if test="${not empty searchPageData.results}">
<div class="form-grant-access grant_revoke_access col-sm-12">
<c:forEach items="${searchPageData.results}" var="user">
	       <div class=" col-sm-12 grant-border">
	       
	       <div class="grant_customer_name col-sm-6 col-xs-5"> 
	       <label class="manage_revoke_table col-sm-6 col-xs-6">${user.name}</label> 
	       
	       
		                	<div class="colored revoke_icon col-sm-6 col-xs-6">
		                		 <input type="checkbox" id="grant_box" name="grant" class="grant-revoke-button-val" value="${user.displayUid}">
		                	
		                	<label  class="manage_revoke_table " for="grantAccess">
		                		<spring:theme code="text.ewallet.column.name.grant.access" />	
		                	</label>
		               
		                </div>
	        </div>
	     
	      </div>
	      </c:forEach>
	       
		
			
			 <div class="account-orderhistory-pagination-ewallet sorting-hidden  grant-revoke-pagination">
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    </div>
			
				
			<div class="row">
						<div class="col-md-8 col-sm-8 col-xs-10 grant-confirm-btn ">
							<button class="btn btn-primary btn-block margin20 "
								id="grant_confirmation_ewallet" disabled="disabled"> <spring:theme code="text.ewallet.column.name.grant.access.button" />
							</button>
						</div>
					</div>
					</div>
					</c:if>
					</div>
			
			
			<div class="revokeAccessData row">
			
            	
			<div class="ewallet_manage_grantRevoke  col-sm-12 col-xs-12 col-md-12 ">
					<label class="print-hidden revoke_member ">
					<spring:theme code="text.ewallet.column.name.message.team.member" /></label>

				</div>
			
            <c:if test="${not empty searchPageData.results}">
			<div class="form-grant-access grant_revoke_access col-sm-12">
			<c:forEach items="${searchPageData.results}" var="user">
	       <div class=" col-sm-12 revoke-border">
	       
	       <div class=" revoke_customer_name col-sm-6 col-xs-5"> 
	       <label class="manage_revoke_table col-sm-6 col-xs-6">${user.name}</label> 
	       
		                	<div class="colored revoke_icon col-sm-6 col-xs-6">
		                		 <input type="checkbox" id="grant_revoke_box " class=" grant-revoke-button-val grant_revoke_box" name="grant" value="${user.displayUid}">
		                	
		                	<label class="manage_revoke_table" for="revokeAccess">
		                	<spring:theme code="text.ewallet.column.name.revoke.access" />
		                			
		                	</label>
		               
		                </div>
	        
	      </div>
	      </div>
	      </c:forEach>
	     
			
			 
                              <div class="account-orderhistory-pagination-ewallet sorting-hidden  grant-revoke-pagination ">
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    </div>
              
                    
                         
                        
 		<div class="row">
						<div class="col-md-8 col-sm-8 col-xs-10   grant-confirm-btn" >
							<button class="btn btn-primary btn-block margin20 " 
								id="grant_confirmation_ewallet" disabled="disabled"><spring:theme code="text.ewallet.column.name.revoke.access.button" /> 
							</button>
						</div>
					</div>
					</div>
					     </c:if>
					</div>
			
			<input type="hidden" id="editVaultToken">


		

<div id="ewallet-revoke-grant-success" class="hide">

			<div class="edit-card-ewallet-message">

				<div class="row">
					<div class="col-md-12 ">
					<br>
						<p>
							<spring:theme code="text.company.ewallet.successMessage" />
							
						</p>
						
						<strong>${userEmail}</strong>

					</div>
				</div>
			</div>

		</div>
		<div id="ewallet-revoke-grant-failure" class="hide">

			<div class="edit-card-ewallet-message">

				<div class="row">
					<div class="col-md-12 ">
						<p>
							
							<spring:theme code="text.ewallet.add.error.messsage"/>
						</p>
						

					</div>
				</div>
			</div>

		</div>
	

       </div>     