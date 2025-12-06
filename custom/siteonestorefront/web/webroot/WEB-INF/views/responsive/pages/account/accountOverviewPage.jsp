<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>

<template:page pageTitle="${pageTitle}">
<script src="${commonResourcePath}/js/jquery-editable-select.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/jquery-editable-select.min.css"/>
<c:choose>
	<c:when test="${unit.isBillingAccount}">
      <c:set var="billing" value="Yes"/>
	</c:when>
	<c:otherwise>
	<c:set var="billing" value="No"/>
	</c:otherwise>
</c:choose>
<c:choose> 
	<c:when test="${unit.isOrderingAccount}">
      <c:set var="ordering" value="Yes"/>
	</c:when>
	<c:otherwise>
	<c:set var="ordering" value="No"/>
	</c:otherwise>
</c:choose>
<h1 class="headline"><spring:theme code="accountOverviewPage.account.overview" /></h1><br />

<div class="row">
	<div class="col-md-10 col-sm-12 col-xs-12">
		 <div class="headline2">${unit.name}</div> 
	</div>
		<product:payBillOnline />
	</div>

<div class="cl"></div>

			<div class="italic-text marginTop10 js-show-shipto-msg hidden" data-has-child-units="${childUnits.size() gt 1}"><spring:theme code="account.shipto.select" arguments="${encodedContextPath}"/></div>
<p class="store-specialty-heading m-t-15"><b><spring:theme code="accountOverviewPage.account.info" /></b></p>
 <br/>
<table class="product-compare account-overview">
<tr>
<td><spring:theme code="accountOverviewPage.name" /></td><td> ${unit.name}          </td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.account.no" /></td>   <td> ${unit.displayId}   </td>

</tr>
<tr>
 <td><spring:theme code="accountOverviewPage.billing.acc" /></td><td>  ${billing} </td>
 </tr>
 <tr>        
 <td><spring:theme code="accountOverviewPage.ordering.acc" /></td>   <td>${ordering}  </td>
 </tr>     

<tr>
<td> <spring:theme code="accountOverviewPage.address" /> </td>
<td> 
			<c:forEach items="${unit.addresses}" var="address">
	            <c:if test="${address.billingAddress}">
		            <p>
			            ${address.line1}  
			           
		           </p>
	          </c:if>
             </c:forEach> 
             
              <c:forEach items="${unit.addresses}" var="address">
	            <c:if test="${address.billingAddress}">
 					${address.town}, &nbsp;${address.region.isocodeShort }&nbsp;${address.postalCode}
   			    </c:if>
             </c:forEach> 
 </td>
 
 </tr>
 
 <tr>		
<td><spring:theme code="accountOverviewPage.phone.number" /></td><td><a class="tel-phone" href="tel:${unit.phoneNumber}">${unit.phoneNumber}</a>  
 
<%--   <c:forEach items="${unit.addresses}" var="address"> --%>
<%-- 	     <c:if test="${address.billingAddress}"> --%>
		     
<%-- 		    <a class="tel-phone" href="tel:${address.phone}">${address.phone}</a> --%>
		    
<%--           </c:if> --%>
         
<%-- </c:forEach> --%>
 </td>
</tr>
</table>
<br/><br/> 
<p class="store-specialty-heading"><b><spring:theme code="accountOverviewPage.sales.summary" /></b></p>
<br/> 
<table class="product-compare account-overview">

<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerSalesInfo.ytdSales}" var="ytdSales" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerSalesInfo.lastYtdSales}" var="lastYtdSales" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerSalesInfo.twelveMonthSales}" var="twelveMonthSales" />

<tr>
<td><spring:theme code="accountOverviewPage.YTD.sales" /></td><td> <c:out value="${currency}"></c:out><c:out value="${ytdSales}"></c:out></td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.last.YTD.sales" /></td><td><c:out value="${currency}"></c:out><c:out value="${lastYtdSales}"></c:out></td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.12.sales" /></td><td><c:out value="${currency}"></c:out><c:out value="${twelveMonthSales}"></c:out></td>
</tr>

<%-- <c:choose>
	<c:when test="${custInfoData.customerRewardsPointsInfo.enrolledInCurrentYearProgram}">
	  <h3>Enrolled in <c:out value="${currentYear}" /> Partner Program: yes </h3>
	</c:when> 
	<c:otherwise>
	  <h3>Enrolled in <c:out value="${currentYear}" /> Partner Program: no </h3>
	</c:otherwise>
</c:choose>

<h3>Available Points:${custInfoData.customerRewardsPointsInfo.availablePoints}</h3>
<h3>Expiring Points:${custInfoData.customerRewardsPointsInfo.expiringPoints}</h3>
<h3>Total Available Points:${custInfoData.customerRewardsPointsInfo.totalAvailablePoints}</h3>
<h3>Pending Points:${custInfoData.customerRewardsPointsInfo.pendingPoints}</h3>
<h3>Last Year Pending:${custInfoData.customerRewardsPointsInfo.lastYearPendingPoints}</h3> --%>

</table>

<br/><br/> 
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.balance}" var="balance" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.openToBuy}" var="openToBuy" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.maxOpenToBuy}" var="maxOpenToBuy" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.creditLimit}" var="creditLimit" />

<p class="store-specialty-heading"><b><spring:theme code="accountOverviewPage.credit.info" /> </b></p>
<br/> 
<table class="product-compare account-overview">
<tr>
<td>
<spring:theme code="accountOverviewPage.credit.limit" />
</td><td><c:out value="${creditLimit}"></c:out></td>
</tr>

<tr>
<td>
<spring:theme code="accountOverviewPage.credit.terms" />
</td><td><c:out value="${custInfoData.customerCreditInfo.creditTermDescription}"></c:out></td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.current.balance" /></td><td><c:out value="${currency}"></c:out><c:out value="${balance}"></c:out></td>
</tr>

 <tr>
<td>
<spring:theme code="accountOverviewPage.credit.opentobuy" />
</td><td><c:out value="${openToBuy}"></c:out></td>
</tr>

<tr>
<td>
<spring:theme code="accountOverviewPage.credit.repname" />
</td><td><c:out value="${custInfoData.customerCreditInfo.creditRepName}"></c:out></td>
</tr>

<tr>
<td>
<spring:theme code="accountOverviewPage.credit.repphone" />
</td><td><c:out value="${custInfoData.customerCreditInfo.credRepContact}"></c:out></td>
</tr>
</table>





<br/><br/> 
<p class="store-specialty-heading"><b><spring:theme code="accountOverviewPage.aging.info" /></b></p>
<br/> 
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.futureAR}" var="futureAR" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.arFor0to30Days}" var="arFor0to30Days" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.arFor30to60Days}" var="arFor30to60Days" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.arFor60to90Days}" var="arFor60to90Days" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.arFor90to120Days}" var="arFor90to120Days" />
<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.arForOver120Days}" var="arForOver120Days" />

<table class="product-compare account-overview">

<tr>
<td><spring:theme code="accountOverviewPage.future.ar" /></td>
<td>

    <c:choose>
    	<c:when test="${futureAR ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${futureAR}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>
   
</td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.0to30.days" /></td>
<td>

    
     <c:choose>
    	<c:when test="${arFor0to30Days ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${arFor0to30Days}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>



			</td>
</tr>



<tr>
<td><spring:theme code="accountOverviewPage.30to60.days" /></td>

<td>

    <c:choose>
    	<c:when test="${arFor30to60Days ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${arFor30to60Days}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>
    
</td>
 </tr>
<tr>
<td><spring:theme code="accountOverviewPage.60to90.days" /></td>
 
<td>
    
    <c:choose>
    	<c:when test="${arFor60to90Days ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${arFor60to90Days}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>
    
</td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.90to120.days" /></td>
<td>
    <c:choose>
    	<c:when test="${arFor90to120Days ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${arFor90to120Days}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>
    
</td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.120.days" /></td>
<td>
 

    <c:choose>
    	<c:when test="${arForOver120Days ne '0.00'}">
	    <%-- <a href="#" class="blue-text" title="0to30">$<c:out value="${arFor0to30Days}"></c:out> </a> --%>
	    $${arForOver120Days}
	    </c:when>
	    <c:otherwise>
	   $ 0.00
	    </c:otherwise>
    </c:choose>
    
    
</td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.last.payment" /></td>
<td>
<c:choose>
    	<c:when test="${not empty custInfoData.customerCreditInfo.lastPaymentReceivedOn}">
	  <c:out value="${custInfoData.customerCreditInfo.lastPaymentReceivedOn}"></c:out>
	    </c:when>
	    <c:otherwise>
	    <c:out value=""></c:out>
	    </c:otherwise>
    </c:choose>
 </td>
</tr>
<tr>
<td><spring:theme code="accountOverviewPage.last.invoice" /></td>
<td>

<c:choose>
    	<c:when test="${not empty custInfoData.customerCreditInfo.lastInvoiceOn}">
	  	<a href="#" class="last-invoice"><c:out value="${custInfoData.customerCreditInfo.lastInvoiceOn}"></c:out></a>
	    </c:when>
	    <c:otherwise>
	    <c:out value=""></c:out>
	    </c:otherwise>
    </c:choose>
</td>
</tr>

 
</table>




</template:page>