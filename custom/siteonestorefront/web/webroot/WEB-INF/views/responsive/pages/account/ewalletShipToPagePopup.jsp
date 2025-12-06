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
<div class="shipToUserPagePopup">
<div class="col-md-7">
<div class="row">
<div class="input-group-shipTo">

 <spring:theme code="shiptosearch.placeholder" var="shiptosearchPlaceholder" />   
  <input type="text" id="search-ship-to-popup-ewallet" name="searchParam" value="${searchParam}" onfocus="this.placeholder = ''" onblur="this.placeholder = '${shiptosearchPlaceholder}'"
                    aria-label="productSearch" maxlength="100" placeholder="${shiptosearchPlaceholder}" class="form-control ship-to-search"/> 
<span class="ship-tosearchbtn"><button class="btn btn-link ship-to-search-btn " type="button" id="searchBoxButton"><span class="icon-search-grey"></span></button></span>

</div>
   
   </div>  
</div>
<input type="hidden" name="sortShipTo" id="sortShipToInvoice" value="${sortShipToPopupInvoice}"/>
<div class="cl"></div>
	<c:choose>
		<c:when test="${not empty searchPageData.results}">	
		<br/>
		<p><spring:theme code="shipTo.linkedto.account"/></p>	
		</c:when>
		<c:otherwise>
		<br/>
		<p><spring:theme code="shipTo.not.linkedto.account" htmlEscape="false" /></p>	
		</c:otherwise>
	</c:choose> 

 <span class="hidden-xs hidden-sm"><br/></span>
      <%--  <c:if test="${not empty searchPageData.results}">
            <div class="account-section-content">
                <div class="account-orderhistory-pagination">
                   <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
                                        additionalParams="${additionalParams}"  numberPagesShown="${numberPagesShown}"/> --%>
            <c:choose>
            <c:when test="${not empty searchPageData.results}">
            	<c:if test="${not empty searchPageData.results}">
	                <div class="account-section-content shipto-pagination pagination-hidden">
	                    <div class="account-orderhistory-pagination-ewallet">
	                        <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
	                                        searchPageData="${searchPageData}" hideRefineButton="true"
	                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
	                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
	                    </div>
	                </div>
                </c:if>
                  <div class="col-md-12 col-sm-12 title-bar hidden-xs">
                	<div class="col-md-4 col-sm-4"><spring:theme code="text.company.manageUnit.column.id"/></div>
                	<div class="col-md-4 col-sm-4"><div class="col-md-12"><spring:theme code="text.company.manageUnit.column.id.name"/></div></div>
                	<div class="col-md-4 col-sm-4"><div class="col-md-12" style="white-space: nowrap;"><spring:theme code="text.company.manageUnit.column.id.date"/></div></div>
                </div>
                
                
                <c:forEach items="${searchPageData.results}" var="result">
           
             <spring:url value="/my-account/ewallet/${result.displayId}_US?shiptounit=${result.displayId}_US&viewtype=All&searchParam=&pagesize=10&sort=byName"
			         var="viewShipToDetailsUrlEwallet" htmlEscape="false">
			         </spring:url>
             <div class="row margin20 shipTo-border">
			      	<div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="col-xs-6 hidden-sm hidden-md hidden-lg zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-sm-12 col-xs-6 zeropadding">
				          <a  href="${viewShipToDetailsUrlEwallet}"  data-url="${viewShipToDetailsUrlEwallet}" id="ewalletPopup"">${fn:escapeXml(result.displayId)}</a>
				           <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toname"/>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toId"/>
				         
				         </div>
			         </div>
			         <div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="hidden-sm hidden-md hidden-lg col-xs-6 zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id.name"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-sm-12 col-xs-6 zeropadding name-align">
				         <a href="${viewShipToDetailsUrlEwallet}">${fn:escapeXml(result.name)}</a>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toname"/>
				         <input type="hidden" value="${fn:escapeXml(result.displayId)}" id="ship-toId"/>
				         </div>
			         </div>
			         <div class="col-xs-12 col-md-4 col-sm-4 zeropadding">
				         <div class="hidden-sm hidden-md hidden-lg col-xs-6 zeropadding">
				            <b><spring:theme code="text.company.manageUnit.column.id.date"/></b>
				         </div>
				         <div class="responsive-table-cell col-md-12 col-xs-6 col-sm-12 zeropadding">
				            <fmt:formatDate type = "date"  dateStyle = "long"  value = "${result.modifiedTime}"/>
			             </div>
		             </div>
			      </div>
               
        </c:forEach>
                
                           <%-- <span class="hidden-xs"><br/><br/> </span>                                                               
                <div class="account-overview-table open-order-table">
                  
                    <table class="responsive-table">
                 
                        <tr class="account-orderhistory-table-head responsive-table-head hidden-xs hidden-sm">
                            <th>
                                <spring:theme code="text.company.manageUnit.column.id"/>
                            </th>
                            <th>
                                <spring:theme code="text.company.manageUnit.column.id.name"/>
                            </th>
                            <th>
                                <spring:theme code="text.company.manageUnit.column.id.date"/>
                            </th>
                        </tr>
                            <c:forEach items="${searchPageData.results}" var="result">
			      <spring:url value="/my-account/ship-to/${result.uid}"
			         var="viewShipToDetailsUrl" htmlEscape="false">
			      </spring:url>
                           
                        
                            <tr class="responsive-table-item account-table-md table-border">
                                <td class="hidden-md hidden-lg"><b class="black-title"><spring:theme code="text.company.manageUnit.column.id"/></b></td>
                                <td class="responsive-table-cell text-right-td">
                                   <a href="${viewShipToDetailsUrl}">${fn:escapeXml(result.displayId)}</a>
                                </td>
                                <td class="hidden-md hidden-lg"><b class="black-title"><spring:theme code="text.company.manageUnit.column.id.name"/></b></td>
                                <td class="responsive-table-cell text-right-td">
                                        <a href="${viewShipToDetailsUrl}">${fn:escapeXml(result.name)}</a>
                                </td>
                                 <td class="hidden-md hidden-lg"><b class="black-title"><spring:theme code="text.company.manageUnit.column.id.date"/></b></td>
                                <td class="responsive-table-cell">
                                       <fmt:formatDate type = "date"  dateStyle = "long"  value = "${result.modifiedTime}"/>
                                       
                            
                             </tr>
                                 </c:forEach>  
                             </table>  
                                  
                             </div> 
                         
                             --%>
                             
                             
                              <div class="account-orderhistory-pagination-ewallet sorting-hidden ">
                        <nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                        searchPageData="${searchPageData}" hideRefineButton="true"
                                        searchUrl="${searchUrl}" msgKey="text.company.manageUnit.pageAll"
                                        additionalParams="${additionalParams}" numberPagesShown="${numberPagesShown}"/>
                    </div>
              
                         </c:when>
                         <c:otherwise>
                <%-- <div class="row">
                    <div class="col-md-6 col-md-push-3">
                        <div class="account-section-content content-empty">
                            <span class="h2" align="left"><spring:theme code="text.company.manageUnit.noUser"/></span>
                        </div>
                    </div>
                </div> --%>
            </c:otherwise>
                         </c:choose>
                             
</div>                               
