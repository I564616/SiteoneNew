<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="noBorder" value=""/>
<c:if test="${not empty pointOfServiceData}">
    <c:set var="noBorder" value="no-border"/>
</c:if>
 
<div class="${noBorder}">
   <%--   <h6><a href="<c:url value="/my-account/account-dashboard"/>"><spring:theme code="header.link.account"/></a></h6> --%>
     <div class="row">
   <div class="col-md-10"> <h1 class="headline"><%-- <spring:theme code="text.account.myStores"/> --%> <spring:theme code="accountMyStoresPage.mybranches" /></h1>
   
   </div>
    <div class="col-md-2 col-xs-12 btn-margin">
		<a href="<c:url value="/store-finder"/>" class="btn btn-primary btn-block"><spring:theme code="accountMyStoresPage.add" /></a>
	</div>
	</div>
</div>

<span class="hidden-xs"><br/><br/>  </span>
<div class="marginTop35 visible-xs">
<div class="cl visible-xs item-sec-border"></div>
 </div>
 <div class="cl marginTop35 visible-xs"></div>
<div class="account-addressbook account-list">
    <c:if test="${not empty pointOfServiceData}">
	    <div class="account-cards card-select">
	    <input id="listSize" type="hidden" value="${pointOfServiceData.size()}">
			<div class="row">
			<c:forEach items="${pointOfServiceData}" var="store" varStatus="loop">
				<c:if test="${store.isPreferredStore}">
					<div class="col-xs-12 col-sm-6 col-md-4">
					  <div class="address-box">
						<ul class="pull-left">
							<li>
							
							<c:if test="${null != store.address}">
							
							<a href="<c:url value="/store"/>/${store.storeId}" style="text-decoration:none;" class="black-title store-name-title">
							<div class="green-title"><spring:theme code="mystore.default"/></div>
							<%-- <span>${store.address.town}</span>,&nbsp;
							<c:if test="${null != store.address.region}">
							<span id="isocodeShort">${store.address.region.isocodeShort}</span>&nbsp;
							</c:if> --%>
							<span class="heading">${store.name}</span>&nbsp;<br>
							<c:if test="${not empty store.title}">
							${store.title}&nbsp;<br>
							</c:if>
							<!-- #${store.storeId}<br/> -->
							
							</a>
						 <br/>
							    <span id="line2">${store.address.line2}</span>
								<span id="line1">${store.address.line1}</span><br>
								<span id="town">${store.address.town}</span>,
								<span id="isocodeShort">${store.address.region.isocodeShort}</span>
								
								<span id="postalCode">${store.address.postalCode}</span><br>
								<a class="tel-phone" href="tel:${store.address.phone}"><span id="phone">${store.address.phone}</span></a><br>
								<i><span id="status${loop.index}"></span></i><br>
								
								<input id="storeLat${loop.index}" type="hidden" value="${store.geoPoint.latitude}">
								<input id="storeLong${loop.index}" type="hidden" value="${store.geoPoint.longitude}">
								<input id="storeIdForStatus${loop.index}" type="hidden" value="${store.storeId}">
								 
                   			</c:if>
							</li>
							<li>
								<div class="account-cards-actionss margin20 col-sm-12">
								<div class="col-sm-6 row">	
								<div class="row">								
									<a href="#" id="${store.storeId}" class="removeStore" data-title="${store.title}" data-name="${store.name}" data-line1="${store.address.line1}" data-line2="${store.address.line2}" data-town="${store.address.town}" data-region-code="${store.address.region.isocodeShort}" data-postal-code="${store.address.postalCode}"><spring:theme code="accountMyStoresPage.remove" /></a>
								</div>
								</div>	
								</div>
							</li>
						</ul>
						<div class="cl"></div>
						</div>
						
					</div>
					</c:if>
					</c:forEach>
					
					<c:forEach items="${pointOfServiceData}" var="store" varStatus="loop">
					<c:if test="${!store.isPreferredStore}">
					<div class="col-xs-12 col-sm-6 col-md-4">
					<div class="address-box">
						<ul class="pull-left">
							<li>
							<c:if test="${null != store.address}">
							<input type="hidden" value="${store.name}" id="defaultbranch"/>
							<a href="<c:url value="/store"/>/${store.storeId}" class="heading" style="text-decoration:none;">
						
							${store.name}&nbsp;<br>
							<c:if test="${not empty store.title}">
							    ${store.title}<br>
							</c:if>
							</a>
							<br/>
								<span id="line1">${store.address.line1}</span>, 
								<span id="line2">${store.address.line2}</span><br>
								<span id="town">${store.address.town}</span>,
								<span id="isocodeShort">${store.address.region.isocodeShort}</span>
								<span id="postalCode">${store.address.postalCode}</span><br>
								<a class="tel-phone" href="tel:${store.address.phone}"><span id="phone">${store.address.phone}</span></a><br> 
								<i><span id="status${loop.index}"></span></i><br>
								
								<input id="storeLat${loop.index}" type="hidden" value="${store.geoPoint.latitude}">
								<input id="storeLong${loop.index}" type="hidden" value="${store.geoPoint.longitude}">
								<input id="storeIdForStatus${loop.index}" type="hidden" value="${store.storeId}">
								
                   			</c:if>
							</li>
							
							<li>
								<div id="store_details" class="account-cards-actionss col-sm-12 col-md-12 margin20">
									<%-- <form:form method="get" action="">
										<input type="submit"  value="View Store Details">
									</form:form> --%>
							<%-- <a href="<c:url value="/store"/>/${store.storeId}" id="viewDetails" class='btn btn-primary' data-latitude="${store.geoPoint.latitude}" data-longitude="${store.geoPoint.longitude}" data-miles="${store.formattedDistance}" data-name="${store.name}">View Details</a> --%>
									
									 <div class="row">
									<div class="col-sm-3 col-xs-6">	
									<div class="row">
									<a href="#" id="${store.storeId}" class="removeStore" data-title="${store.title}"data-name="${store.name}" data-line1="${store.address.line1}" data-line2="${store.address.line2}" data-town="${store.address.town}" data-region-code="${store.address.region.isocodeShort}" data-postal-code="${store.address.postalCode}"><spring:theme code="accountMyStoresPage.remove" /></a>
									 </div>
									 </div>
									 <div class="col-sm-4 col-xs-6">	
									 <div class="row">
									<a href="<c:url value="/my-account/make-my-store"/>/${store.storeId}" class="makeMyBranchDefault"><spring:theme code="accountMyStoresPage.make.default" /></a>
									</div>
									</div>
									</div>
								</div>
							</li>
						</ul>
						<div class="cl"></div>
						</div>
					</div>
					</c:if>
				</c:forEach>
			</div>
			
	    </div>
    </c:if>
</div>