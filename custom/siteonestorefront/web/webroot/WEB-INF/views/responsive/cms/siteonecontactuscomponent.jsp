<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <div class="marginLeft-mobile">
 
  <c:if test="${cmsPage.uid ne 'ccpaConfirmation'}">
<p class="store-specialty-heading bold-text"><spring:theme code="text.siteoneContactUsComponent.emailUs"/></p>
<%-- <spring:theme code="text.corporate.email" arguments="${siteoneSupportEmail}"/> --%>
<p><spring:theme code="siteoneContactUsComponent.pleaseFill" /></p>
</c:if>
 
<div class="product-item-box3 confirm-msg">
<div  id="contactUs_confirmation">
<br/>
 <div class="icon-success"></div> 
<div class="col-md-7 col-sm-7 col-xs-8 ccp-msg">
<c:if test="${not empty component}">
<p class="black-title zeroMargin"><b>${component.headline}</b></p>
<p>${component.content}</p>
 <c:if test="${cmsPage.uid ne 'ccpaConfirmation'}">
<a href="<c:url value="/contactus/"/>"><spring:theme code="siteoneContactUsComponent.returnToForm" /> &#8594;</a>
</c:if>
</c:if>
<br/><br/>
<div class="cl"></div>
</div>

<div class="cl"></div>
</div>

<div class="cl"></div>
</div>
</div>