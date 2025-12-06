<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
 
<c:set var="noBorder" value=""/>
<c:if test="${not empty addressData}">
    <c:set var="noBorder" value="no-border"/>
</c:if>

 <div class="${noBorder} row"> 
	
   <c:choose>
   <c:when test="${isBilling}">
   	<div class="col-md-9 col-sm-9"><h1 class="headline hidden-xs"><spring:theme code="text.account.billingaddress"/></h1>
   	<h1 class="headline2 visible-xs"><spring:theme code="text.account.billingaddress"/></h1>
   	<div class="col-md-9 col-sm-9">
   	<div class="row"><spring:theme code="text.account.billingaddressmessage"/></div>
   	</div>
   	</div>
   </c:when>
   	<c:otherwise>
   		<div class="col-md-10 col-sm-9 col-xs-12"><h1 class="headline hidden-xs"><spring:theme code="text.account.addressBook"/></h1>
   		<h1 class="headline2 visible-xs"><spring:theme code="text.account.addressBook"/></h1>
   		</div>
   				
   	</c:otherwise>
   </c:choose>
   <c:set var="isAdmin" value="false" />
   <sec:authorize access="hasAnyRole('ROLE_B2BADMINGROUP')">
   <c:set var="isAdmin" value="true" />
   </sec:authorize>
   <c:if test="${not empty addressData}">
   <div class="col-md-2 col-sm-12 col-xs-12 btn-margin">
   <c:if test="${!isBilling}">
    <c:if test="${enableAddModifyDeliveryAddress || isAdmin}">
        <ycommerce:testId code="addressBook_addNewAddress_button">
            <div class="account-section-header-add">
            	<span class="hidden-xs hidden-md hidden-lg"><br/></span>
                <a href="<c:url value="/my-account/add-address"/>/${fn:escapeXml(unitId)}" class="btn btn-primary btn-block">
                  <spring:theme code="text.account.addressBook.addAddress"/>
                </a>
            </div>
        </ycommerce:testId>
    </c:if>
    </c:if>
        
    </div>
    </c:if>
    
     </div> 
    
<div class="cl"></div>
<span class="visible-xs"><br></span>
<div class="visible-xs item-sec-border cl"></div><span><br></span>
<br/> 
<div class="account-addressbook account-list">
    <c:if test="${empty addressData}">
		<div>
			<spring:theme code="text.account.addressBook.noSavedAddresses" />
		</div> 
		<c:set var="isAdmin" value="false" />
		<sec:authorize access="hasAnyRole('ROLE_B2BADMINGROUP')">
		<c:set var="isAdmin" value="true" />
		</sec:authorize>
  <br/>   <br/><br/>
  <div class="col-md-2 col-sm-12 col-xs-12">
   <c:if test="${!isBilling}">
   <c:if test="${enableAddModifyDeliveryAddress || isAdmin}">
        <ycommerce:testId code="addressBook_addNewAddress_button">
            <div class="account-section-header-add">
               <div class="row"> <a href="<c:url value="/my-account/add-address"/>/${fn:escapeXml(unitId)}" class="btn btn-primary btn-block">
                  <spring:theme code="text.account.addressBook.addAddress"/>
                </a> 
                </div>
            </div><br/><br/><br/><br/><br/><br/>
        </ycommerce:testId>
    </c:if>
      </c:if>  
    </div>
    
    </c:if>

    <c:if test="${not empty addressData}">
	    <div class="card-select">
			<div class="row flex-wrap">
			<c:set var="largeCount" value="0" />
				<c:forEach items="${addressData}" var="address">
					<c:set var="largeCount" value="${largeCount+1}" />
					<div class="col-xs-12 col-sm-12 col-md-4 m-y-15 m-y-5-xs">
					<div class="address-box ${address.defaultUserAddress ? 'border-primary':''}">
						<c:if test="${address.defaultUserAddress}">
							<div  class="flex text-uppercase pad-rgt-10 m-t-5 defaultAddress">
							 	<span class="pad-rgt-10 hidden-xs"><spring:theme code="text.account.addressBook.default" /></span><common:checkmark-bg-green/>
							</div>

						</c:if>
						<ul class="pull-left" style="width:100%;">
						<c:if test="${not empty fn:escapeXml(address.projectName)}">
							<li><p class="store-specialty-heading"> ${fn:escapeXml(address.projectName)}</p></li>
						</c:if>
						
							<li>
								
								
								<c:choose>
											<c:when
												test="${! empty fn:escapeXml(address.firstName) or ! empty fn:escapeXml(address.lastName)}">
								<p class="name-title">
								 ${fn:escapeXml(address.title)} &nbsp;${fn:escapeXml(address.firstName)}
								&nbsp;${fn:escapeXml(address.lastName)}</p>
								<div class="cl"></div>
							 	 
								</c:when>
											<c:otherwise>
									<div class="h-41"></div>
								${fn:escapeXml(address.title)} ${fn:escapeXml(address.firstName)}
								 ${fn:escapeXml(address.lastName)}
								<div class="cl"></div>
								</c:otherwise>
										</c:choose>

							 
								
							</li>
							<li>${fn:escapeXml(address.line1)}</li>
							<c:if test="${not empty fn:escapeXml(address.line2)}">
								<li>${fn:escapeXml(address.line2)}</li>
							</c:if>
							<li>${fn:escapeXml(address.town)},&nbsp;${fn:escapeXml(address.region.isocodeShort)}&nbsp;${fn:escapeXml(address.postalCode)}</li>
							<li><a class="tel-phone" href="tel:${fn:escapeXml(address.phone)}">${fn:escapeXml(address.phone)}</a></li>
							<%-- <c:if test="${not empty fn:escapeXml(address.deliveryInstructions)}">
							<li><br/><strong>Delivery instructions:<br></strong>
							${fn:escapeXml(address.deliveryInstructions)}</li>
							</c:if> --%>
						</ul>
						<div class="cl"></div>
						  <c:set var="isAdmin" value="false" />
							<sec:authorize access="hasAnyRole('ROLE_B2BADMINGROUP')">
							<c:set var="isAdmin" value="true" />
							</sec:authorize>  
						 <br/>
						 <div class="row">
						 <c:if test="${enableAddModifyDeliveryAddress || isAdmin}">
					 	 <c:if test="${!address.billingAddress}"> 
							<ycommerce:testId code="addressBook_editAddress_button">
								<div class="col-md-2 col-sm-3 col-xs-3">
								<a class="action-links" href="<c:url value="/my-account/edit-address"/>/${address.unitId}/${address.id}">
								<spring:theme code="text.account.addressBook.edit" />
								</a>
								</div>
							</ycommerce:testId>
							  </c:if>
							<c:if test="${!address.billingAddress}">  
							<ycommerce:testId code="addressBook_removeAddress_button">
							<c:if test="${!address.defaultUserAddress}">
								<div class="col-md-3 col-xs-4 col-sm-4 b-l-grey b-r-grey text-center">
									<a href="#" class="action-links removeAddressFromBookButton" data-address-id="${address.id}">
									<spring:theme code="text.account.addressBook.remove" />
									</a>
								</div>
								<div class="col-md-7 col-xs-5 col-sm-5">
									<a class="action-links" href="<c:url value="/my-account/set-default-address"/>/${address.unitId}/${address.id}">
									<spring:theme code="text.account.addressBook.makedefault" />
									</a>
								</div>
							</c:if>
							</ycommerce:testId>
							  </c:if>  
							   </c:if> 
							  
							  
					 </div>
					   
					<div class="cl"></div>
					</div>

					</div>
				<c:if test="${largeCount eq '3' }">
						     <div  class="visible-md visible-lg hidden-sm hidden-xs">
	                		  		<div class="cl"></div>
									<c:set var="largeCount" value="0" />
										</div>
								</c:if>	
				</c:forEach>
				 
			</div>
			
			<c:forEach items="${addressData}" var="address">
		        <div class="display-none delete-add">
		       	 	<div id="popup_confirm_address_removal_${address.id}" class="account-address-removal-popup">
		        		<div class="addressItem">
		        			<h1 class="headline2 hidden-xs" style="position: relative;top:-75px;"><spring:theme code="text.address.remove.following" /></h1>
		        			<h1 class="headline2 hidden-sm hidden-md hidden-lg" style="position: relative;top: -75px;"><spring:theme code="text.address.remove.following" /></h1>
		       				<div style="margin-top:-60px;">
		       			<c:choose>
							<c:when test="${! empty fn:escapeXml(address.title) or ! empty fn:escapeXml(address.firstName)}">
								 
								<p class="name-title"> ${fn:escapeXml(address.projectName)} 
								 ${fn:escapeXml(address.title)} &nbsp;${fn:escapeXml(address.firstName)}
								&nbsp;${fn:escapeXml(address.lastName)}</p>
								<div class="cl"></div>
							</c:when>
							<c:otherwise>
								<p class="name-title">${fn:escapeXml(address.projectName)}</p>
								${fn:escapeXml(address.title)} ${fn:escapeXml(address.firstName)}
								 ${fn:escapeXml(address.lastName)}
								<div class="cl"></div>
							</c:otherwise>
						</c:choose>
		       				
						       <div class="cl"></div> 
						        ${fn:escapeXml(address.line1)}&nbsp;<div class="cl"></div>
						        ${fn:escapeXml(address.line2)}
						        <div class="cl"></div>
						        ${fn:escapeXml(address.town)},&nbsp;${fn:escapeXml(address.region.isocodeShort)}&nbsp;${fn:escapeXml(address.postalCode)}
						      <div class="cl"></div>
						        <a class="tel-phone" href="tel:${fn:escapeXml(address.phone)}">${fn:escapeXml(address.phone)}</a>
		       				</div>
					        <div class="row">
					        <div class="marginTop35 col-md-10">
                                <div class="row">
                                    <ycommerce:testId code="addressRemove_delete_button">
                                        <div class="col-xs-12 col-sm-12 col-md-6  col-sm-push-0">
							<a class="btn btn-primary btn-block"  data-address-id="${address.id}" href="<c:url value="/my-account/remove-address"/>/${address.id}/${address.unitId}">                                                <spring:theme code="text.address.delete" />
                                            </a>
                                        </div>
                                    </ycommerce:testId>
                                    <div class="hidden-md hidden-lg cl"><br/></div>
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-sm-push-0">
                                        <a class="btn btn-default btn-block closeColorBox addressAddRemove" data-address-id="${address.id}">
                                            <spring:theme code="text.button.cancel"/>
                                        </a>
                                    </div>
					       	    </div>
					       	</div>
					       	 	</div>
					       	 <div class="hidden-xs  cl"><br/></div>
		        		</div>
		        	</div>
		        </div>
		    </c:forEach>
	    </div>
    </c:if>
</div>