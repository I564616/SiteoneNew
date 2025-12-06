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
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<div class="shipToOrderPagePopup">
<div class="col-md-12">
<div class="row">
<div class="input-group-shipTo">
<hr class="visible-xs" />
 <c:set var="currentDivisionId" value="_US"/>
    <c:if test="${currentBaseStoreId eq 'siteoneCA'}">
    <c:set var="currentDivisionId" value="_CA"/>
    </c:if>
 <spring:theme code="shiptosearch.placeholder" var="shiptosearchPlaceholder" />   
  <input type="text" id="search-ship-to-popup-order-new" name="searchParam" value="${searchParam}" onfocus="this.placeholder = ''" onblur="this.placeholder = '${shiptosearchPlaceholder}'"
                    aria-label="productSearch" maxlength="100" placeholder="${shiptosearchPlaceholder}" class="form-control ship-to-search"/> 
<span class="ship-tosearchbtn hidden-xs"><button class="btn btn-link ship-to-search-btn " type="button" id="shipToSearchBoxButton"><common:headerIcon iconName="search" iconFill="none" iconColor="#77A12E" width="22" height="22" viewBox="0 0 22 22" /></button></span>

</div>
   
   </div>  
</div>
<input type="hidden" name="sortShipTo" id="sortShipToOrder" value="${sortShipToPopupInvoice}"/>
<div class="cl"></div>
	<c:choose>
		<c:when test="${not empty searchPageData.results}">	
		<p class="f-s-16 f-w-400 m-y-10 hidden-xs"><spring:theme code="shipTo.linkedto.account"/></p>	
		</c:when>
		<c:otherwise>
		<br/>
		<p><spring:theme code="shipTo.not.linkedto.account" htmlEscape="false" /></p>	
		</c:otherwise>
	</c:choose> 
      
            <c:choose>
            <c:when test="${not empty searchPageData.results}">
            	<c:if test="${not empty searchPageData.results}">
	                <div class="shipto-pagination pagination-hidden hidden-xs">
	                    <div class="account-orderhistory-pagination-order">
	                        <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
	                                        searchPageData="${searchPageData}" hideRefineButton="true"
	                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
	                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
	                    </div>
	                </div>
	                <div class="hidden-lg hidden-md">
	                <hr/>
	                <div class="black-title f-s-20-xs-px f-w-500 font-Geogrotesque m-y-10">Sort By</div>
	                <div class="black-title f-s-18-xs-px f-w-400 flex justify-between m-y-10">
	                <span>Date of Last Activity</span>
	                <span class="colored-radio"><input type="radio" name="sortByRadio" value="byDate"></span>
	                </div>
	                <div class="black-title f-s-18-xs-px f-w-400 flex justify-between m-y-10">
	                <span>Account Number</span>
	                <span class="colored-radio"><input type="radio" name="sortByRadio" value="byUnitId"></span>
	                </div>
	                <hr/>
	                 <div class="black-title f-s-20-xs-px f-w-500 font-Geogrotesque m-b-20-xs popup-boxshadow">Select Ship-to</div>
	                </div>
                </c:if>
                
                <div class="col-md-12 col-sm-12 f-s-12 f-w-700 title-bar m-b-0-imp hidden-xs">
                	<div class="col-md-4 col-sm-4"><spring:theme code="text.company.manageUnit.column.id"/></div>
                	<div class="col-md-4 col-sm-4"><div class="col-md-12"><spring:theme code="text.company.manageUnit.column.id.name"/></div></div>
                	<div class="col-md-4 col-sm-4"><div class="col-md-12" style="white-space: nowrap;"><spring:theme code="text.company.manageUnit.column.id.date"/></div></div>
                </div>
                <div class="cl"></div>
                   
                    <c:forEach items="${searchPageData.results}" var="result">
			         <c:set value="${dateSort}" var="dateSort"/>
                  
                  
                     <spring:url value="/my-account/orders/${result.displayId}${currentDivisionId}?accountShiptos=${fn:escapeXml(result.displayId)}+${fn:escapeXml(result.name)}&viewtype=All&searchParam=&dateSort=${dateSort}&paymentType=${paymentType}&pagesize=25&listOfShipTos=${fn:escapeXml(result.displayId)}+${fn:escapeXml(result.name)}&sort=byDate"
			         var="viewShipToDetailsUrlOrder" htmlEscape="false">
			      </spring:url>
			      <div class="b-l-grey b-r-gray m-l-0 m-r-0 row shipTo-border shipTopopup-row hidden-xs">
			      	<div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="col-xs-6 hidden-sm hidden-md hidden-lg zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-sm-12 col-xs-6 zeropadding">
				           <a class="bold-text text-green f-s-14" href="${viewShipToDetailsUrlOrder}" id="orderPopup" data-name="${fn:escapeXml(result.name)}" data-index="${fn:escapeXml(result.displayId)}">${fn:escapeXml(result.displayId)}</a>
				           <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toname"/>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toId"/>
				         
				         </div>
			         </div>
			         <div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="hidden-sm hidden-md hidden-lg col-xs-6 zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id.name"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-sm-12 col-xs-6 zeropadding name-align">
				         <a class="bold-text f-s-14 text-gray" href="${viewShipToDetailsUrlOrder}">${fn:escapeXml(result.name)}</a>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toname"/>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toId"/>
				         </div>
			         </div>
			         <div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="hidden-sm hidden-md hidden-lg col-xs-6 zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id.date"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-xs-6 col-sm-12 f-s-14 zeropadding">
				            <fmt:formatDate type = "date"  dateStyle = "long"  value = "${result.modifiedTime}"/>
			             </div>
		             </div>
			      </div>
			      <!----For Mobile---->
			       <div class="f-s-18-xs-px f-w-400 flex justify-between m-y-10 hidden-lg hidden-md">
	                <span>${fn:escapeXml(result.displayId)}&nbsp;${fn:escapeXml(result.name)}</span>
	                <span class="colored-radio"><input type="radio" class="shipToURLRadio" name="shiptoURL" value="${viewShipToDetailsUrlOrder}"></span>
	                </div>
			      
			 </c:forEach> 
			 
                              <div class="account-orderhistory-pagination-order sorting-hidden">
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    </div>
              
                         </c:when>
                         <c:otherwise>
            			</c:otherwise>
                         </c:choose>
                          <div class="hidden-lg hidden-md">
			              <hr/>
			              <div class="row">
			              <div class="col-xs-6">
							<input type="button" value="Clear All" class="btn btn-default full-width moreShipToClear"/>
							</div>
							<div class="col-xs-6">
							<input type="button" class="btn btn-primary full-width moreShipToSearch" value="Search">
							</div>
			              </div>
			              </div>
                             
</div>                               
                                         
                                 
                        
                            