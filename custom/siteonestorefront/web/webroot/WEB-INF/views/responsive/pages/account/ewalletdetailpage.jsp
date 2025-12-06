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
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>



<spring:htmlEscape defaultHtmlEscape="true" />



<spring:url value="/my-account/account-dashboard" var="myaccountUrl"></spring:url>
 <c:set var="searchUrl" value="/my-account/ewallet/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<input type="hidden" id="cmspageuid" value="${cmsPage.uid}">
<input type="hidden" id="unitIdVal" value="${unitId}">

<template:page pageTitle="${pageTitle}">

<div class="user-datasearch">

        
       <div class="col-md-9 col-sm-9 col-xs-12 ewallet_title"><div class="row"><h1 class="headline"><spring:theme code="text.company.ewallet.label"/></h1>
	</div>
	</div>
<div class="col-md-3 col-sm-12 col-xs-12 btn-margin ewallet-add-button">
		
			<div class="row"><div class="margin-label"><button  class="btn btn-primary col-xs-12  col-sm-12 " id="ewallet-add-credit-card"><spring:theme code="addCreditCard.search.go" /></button></div>
			</div></div>
	
	<form:form  id="EwalletsearchForm" method='GET' modelAttribute="EwalletsearchForm">
		<div class="invoice-serach-sec col-md-12 col-xs-12 padding0" >
			<label class="print-hidden"><spring:theme code="ewalletpage.search.card" /></label>
			<label class="print-visible hidden"></label>
					<div class="cl hidden-md hidden-lg print-hidden"></div>
<div id="Mangerusers">
					<div class="col-md-4 col-sm-6 col-xs-9 print-hidden">	
						<input type="text" type="text" id="ewallet-page-card" name="searchParam" value="${fn:escapeXml(searchParam)}" placeholder="<spring:theme 
						code="text.name.card.name"/>" class="form-control"/></div></div>
			<div class="col-md-6 col-xs-12 search-manager">
<div class="row">
				<div class="col-md-1 col-xs-1 print-hidden"><div class="margin-label"><button class="btn btn-primary eWallet-user-Button"><spring:theme code="invoicelistingpage.search.go" /></button></div></div>
				</div></div>
			</div>

</form:form>
     	 
     
                   <div class="account-section-content invoice_table">
                <div class="account-orderhistory">
             
            <div class="cl"></div>
             <br/>         	                    	            
                        
                    <div class=" account-orderhistory-pagination invoiceTop-section invoiceTop-pagination ewallet-pagination">
                        <pag:invoiceListPagePagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" 
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="${messageKey}"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
    	                    	 <br/>  
    	                    	 <c:choose>
            <c:when test="${not empty searchPageData.results }">   
                    	<div class="total_count" id="total_count_card">

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
						
      				
					  <span class="hidden-xs hidden-sm"></span>
                    <div class="data-table ">
                    
                     <div class="title-bar hidden-xs hidden-sm">
						<div class="col-md-1"><spring:theme code="text.ewallet.column.name.number"/></div>
						<div class="col-md-2"><spring:theme code="text.ewallet.column.name.name"/></div>
						<div class="col-md-2"><spring:theme code="text.ewallet.column.name.date"/></div>
						<div class="col-md-1"><spring:theme code="text.ewallet.column.name.type"/></div>
						<div class="col-md-2"><spring:theme code="text.ewallet.column.name.nickName"/></div>
						<div class="col-md-2"><spring:theme code="text.company.ewallet.ShipTO"/></div>
						<div class="col-md-2"><spring:theme code="text.ewallet.column.name.action"/></div>
						<div class="cl"></div>
					</div>
                    
                    
                            <c:forEach items="${searchPageData.results}" var="user">
                               <div class="data-row marginLeft-mobile responsive-table-ewallet">
                               	<div class="col-xs-12 col-md-1 col-sm-12 padding-LeftZero">
					           		<div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.number"/></div>
					                <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
										 <input type="hidden" id="vaultToken" value="${fn:escapeXml(user.valutToken)}">
                                 		 <input type="hidden" id="lastdigitalcard" value="${fn:escapeXml(user.last4CreditcardDigits)}">
                                         <ycommerce:testId code="my-company_username_label"> 
                                            <span class="responsive-table-link">XX${fn:escapeXml(user.last4CreditcardDigits)}
                                            </span>
                                       </ycommerce:testId>  
					                 </div>
					           </div>
                               
                               <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.name" /></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
					                <input type="hidden" id="ewalletName" value="${fn:escapeXml(user.nameOnCard)}">
                                         <ycommerce:testId code="my-company_username_label"> 
                                             ${fn:escapeXml(user.nameOnCard)}
                                       </ycommerce:testId>
                                       </div>
					           </div>
                               
                               <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.date" /></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
					                 <ycommerce:testId code="my-company_username_label"> 
                                            <c:set var = "string1" value = "${user.cardExpirationDate }"/>
										<c:set var = "string2" value = "${fn:substring(string1, 0, 2)}" />
									<c:set var = "string3" value = "${fn:substring(string1, 2, 4)}" />
									<input type="hidden" id="ewalletExpe" value="${string2}-${string3}">
										 ${string2}-${string3}
                                       </ycommerce:testId>
                                       </div>
					           </div>
					           
					           
					           <div class="col-xs-12 col-md-1 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.type" /></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
					                <input type="hidden" id="ewalletCardType" value="${fn:escapeXml(user.creditCardType)}">
                                         <ycommerce:testId code="my-company_username_label"> 
                                            ${fn:escapeXml(user.creditCardType)}
                                       </ycommerce:testId> 
                                       </div>
					           </div>
                               
                                <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.nickName" /></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
					                <input type="hidden" id="ewalletNickName" value="${fn:escapeXml(user.nickName)}">
                                         <ycommerce:testId code="my-company_username_label"> 
                                             ${fn:escapeXml(user.nickName)}
                                       </ycommerce:testId> 
                                  </div>
					           </div>
                               
                               
                                <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.company.ewallet.ShipTO" /></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin">
					                <ycommerce:testId code="my-company_username_label"> 
                                             ${userUnitId} - ${fn:escapeXml(user.unitName)}
                                       </ycommerce:testId> 
                                       </div>
					           </div>
                               
                               
                                
                               
                               <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero">
					           <div class="hidden-md hidden-lg col-sm-6 col-xs-6  data-title"><spring:theme code="text.ewallet.column.name.action"/></div>
					            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin padding0">
					                 <ycommerce:testId code="my-company_username_label"> 
                                             <a href="#" id="ewallet-edit-card"><spring:theme code="text.company.ewallet.edit.link"/></a>
                                             |
                                            <a href="#" id="ewallet-delete-card"><spring:theme code="text.company.ewallet.delete.link"/>
													</a> | <a href="#" id="ewallet-assgin"><spring:theme code="text.company.ewallet.manage.link"/></a>
                                            
                                       </ycommerce:testId>
                                       </div>
					           </div>
                               
                               <div class="cl"></div>
                               </div>
                               
                                
                            </c:forEach>
                         
                    </div>
 
                     <div class="account-orderhistory-pagination invoiceBottom-section  ewallet-pagination">
                               <div class="row">
                          
                        
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="${messageKey}"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    
                    </div>
                    </div>
                    
                 </c:when>
                 </c:choose>
                  </div>
               </div>
               </div>
              
                 
        <div class="add-success-card-ewallet hide">
     
      <p><spring:theme code="text.company.ewallet.successMessage"/>:</p>
     <strong>${userEmail}</strong>
        </div>
        <div class="add-eroor-card-ewallet hide">
			<div class="row">
				<div class="col-md-12 manage-card-ewallet-add">
				<h4 class='payment-pop-up-title headline' style="font-size: 26pt;">
					<span class='headline-text'>${ewalletAddError}</span></h4>
					<p><spring:theme code="text.ewallet.add.error.messsage"/></p>
				</div>
			</div>
		</div>
      
        <input type="hidden" id="add-ewallet-success" value="${ewalletAddSuccess}">
        <input type="hidden" id="add-ewallet-failure" value="${ewalletAddError}">
        <div id="iframe_Popup" class="hide" style="margin-top: 21px;">
        <div id="Payment_name">
										<strong><spring:theme code="checkout.multi.ewallet.nickName" /></strong>
										</div>
						<input type="text" name="nickName" 
											value="${nickName}"  id="card_holder_nickname" />
						
        <div class="myIframe-main">
    								<iframe id="myIframe" title="myIframe" class="Pop-up-myIframe" name="myIframe" onLoad="ewalletLoadContent(this)" src="https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey="></iframe>
								</div>
								
								
								
								</div>

		<div id="ewallet-delete" class="hide">
		
			<div class="delete-card-ewallet">
			<p><spring:theme code="text.company.ewallet.delete.message"/></p>
				<div class="row">
				
					<div class="col-md-4 ">
					
						<a class="btn btn-primary btn-block margin20 delete_button"  id="delete_confirmation_ewallet">
							<spring:theme code="text.quote.yes.button.label" />
						</a>


					</div>
					<div class="col-md-4 ">
						<a class="btn btn-default btn-block margin20 delete_button" id="cancel">
							<spring:theme code="text.quote.no.button.label" />
						</a>

					</div>
				</div>
			</div>
			<input type="hidden" id="editVaultToken">
		</div> 
			<div id="ewallet-delete-succes" class="hide">
		
			<div class="delete-card-ewallet-message">
			
				<div class="row">
				<div class="col-md-12 ">
				<p><spring:theme code="text.company.ewallet.successMessage"/></p>
				<strong>${userEmail}</strong>
				<br>
				
					</div>
				</div>
			</div>
			
		</div> 
		
		<div id="ewallet-delete-failure" class="hide">
		
			<div class="delete-card-ewallet-message">
			
				<div class="row">
				<div class="col-md-12 ">
				<br>
				<p><spring:theme code="text.company.ewallet.failure.content"/>.</p>
				<br>
				
					</div>
				</div>
			</div>
			
		</div> 
	

		<div id="ewallet-edit" class="hide">
			
				<div class="edit-card-ewallet">
					<div class="expirydate"><spring:theme code="text.ewallet.edit.error.message"/></div>
					<div class="error_updated"><spring:theme code="text.ewallet.edit.error"/></div>
					<div class="expiry_year_size"><spring:theme code="text.ewallet.edit.error.month"/></div>
					<div class="expirydatesame"><spring:theme code="text.ewallet.edit.sameerror.message"/></div>
					
					<div
						class="ewallet-serach-sec col-sm-12 col-xs-12 col-md-12 ">
						<label class="print-hidden ewallet-edit-title"><spring:theme code="text.ewallet.column.name.nickName" />:</label>
						<div class="ewallet-serach col-md-6 col-sm-6 col-xs-12 ">
							<input  type="text" id="ewallet-edit-page-card" name="searchParam" class="form-control" />

						</div>
					</div>
					<div
						class=" ewallet-serach-sec col-sm-12 col-xs-12 col-md-12  ">
						<label class="print-hidden ewallet-edit-title"><spring:theme code="text.ewallet.column.name.date" />:</label>
						<div class="ewallet-serach col-md-6 col-sm-6 col-xs-12 " >

							<input  type="text" id="ewallet-card-expiration" maxlength="5"  placeholder="MM-YY"  name="searchParam" class="form-control" />


						</div>
					</div>
					<div class="row">
						<div class="col-md-6 col-sm-6 col-xs-6 ">
							<button class="btn btn-primary btn-block margin20 delete_button" 
								id="edit_confirmation_ewallet"> <spring:theme
									code="checkout.multi.hostedOrderPostPage.button.submit" />
							</button>
						</div>
					</div>
				</div>
				<input type="hidden" id="editVaultToken">

			
		</div>

		<div id="ewallet-edit-success" class="hide">

			<div class="edit-card-ewallet-message">

				<div class="row">
					<div class="col-md-12 ">
						<p>
							<spring:theme code="text.company.ewallet.successMessage" />
						</p>
						<strong>${userEmail}</strong> <br>

					</div>
				</div>
			</div>

		</div>
		


			<c:choose>
             
<c:when test="${searchParam ne null && searchParam ne '' && empty  searchPageData.results }">
	<div class="account-error ">
		<div class="error-border">
			<spring:theme code="search.page.no.result.filter" />
		</div>
		<h1 class="headline ewallet-title-error"><spring:theme code="search.no.results" />"${fn:escapeXml(searchParam)}."<spring:theme code="search.page.no.result.later" /> </h3>
		
	
	
	<div class="row col-xs-12 col-md-12 contact-center">
			<h5 class="store-specialty-heading margin20"><strong><spring:theme code="errorNotFoundPage.help" /></strong></h5>
			<ul>
			
				<li><spring:theme code="search.page.no.result.call" />&nbsp; <a href="tel:(800)748-3663"><spring:theme code="search.page.no.result" /></a></li>
				
				<li><a href="<c:url value="/contactus"/>"><spring:theme code="searchEmptyPage.contact.here" /> &rarr;</a></li>
			</ul>
		</div>
		
</div>
</c:when>
<c:when test="${ empty  searchPageData.results && empty searchParam }">
<div class="account-error-result">
<h1 class="headline"><spring:theme code="ewallet.no.card.text" /> </h3>
<div class="row col-xs-12 col-md-12 contact-center">
			<h5 class="store-specialty-heading margin20"><strong><spring:theme code="errorNotFoundPage.help" /></strong></h5>
			<ul>
			
				<li><spring:theme code="search.page.no.result.call" />&nbsp; <a href="tel:(800)748-3663"><spring:theme code="search.page.no.result" /></a></li>
				
				<li><a href="<c:url value="/contactus"/>"><spring:theme code="searchEmptyPage.contact.here" /> &rarr;</a></li>
			</ul>
		</div>
		</div>
</c:when>
</c:choose>

</template:page>

