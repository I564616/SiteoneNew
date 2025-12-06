<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<template:page pageTitle="${pageTitle}">
 <c:if test="${eventData.eventExpiryMessage eq null}">
<div class=Event_Details>
<c:if test="${eventData.image ne null}">
<img title="${eventData.image.altText}" alt="${eventData.image.altText}"
				src="${eventData.image.url}" width="100%" height="auto">
				</c:if>
	<div class="row">	
<div class="col-md-12">
	<div class="row">
	<div class="col-md-10">	
<h1 class="headline">${eventData.name}</h1>
<div class="headline3 event-sub-heading marginBottom40"><span class="bold-text">${eventData.type}</span></div>

<c:choose>
	<c:when test="${eventData.eventStartDate ne eventData.eventEndDate}">
		<span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.date" /></span> ${eventData.eventStartDate} - ${eventData.eventEndDate}<BR>
	</c:when>
	<c:otherwise>
		<span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.date" />:</span> ${eventData.eventStartDate}<BR>
	</c:otherwise>
</c:choose>
<span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.time" />:</span> ${eventData.time}<BR>
<span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.location" /></span> ${eventData.location}<BR>
<span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.venue" /></span> ${eventData.venue}<BR><br/>

<c:if test="${eventData.isPartnerProgramEvent}"> 
<div>

 <c:if test="${eventData.document.url ne null}"> 
<span class="hidden-lg hidden-md"></span><span class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.document" /></span><a href="${eventData.document.url}" download="${eventData.document.url}"><spring:theme code="siteOneEventDetailPage.download" /></a><br><br> 
 
</c:if> 
 
 <p class="bold-text"><spring:theme code="siteOneEventDetailPage.text1" />.<span class="hidden-xs"><br/></span><spring:theme code="siteOneEventDetailPage.text2" /><br/><span class="hidden-md hidden-lg"><br/></span><a href="${eventData.learnMoreUrl}" > <spring:theme code="siteOneEventDetailPage.learn.more" /> &#8594;</a></p> 
				
</div>
</c:if>
</div>
 
<div class="col-md-2 btn-margin">

<p class="sales-price">${eventData.registrationMessage}</p>
<c:choose>
    <c:when test = "${eventData.isRegistrationOpen && not empty eventData.eventRegistrationUrl}">
        <a href="${eventData.eventRegistrationUrl}" class="btn btn-primary btn-block" target="_blank" ><spring:theme code="siteOneEventDetailPage.register" /></a>
    </c:when>
 <c:otherwise>
    <button  class="btn  btn-block btn-disable" disabled ="disabled"><spring:theme code="siteOneEventDetailPage.register" /></button>
 </c:otherwise> 
 </c:choose>
                    </div>
                    </div>
</div>
</div>

 
 
<c:choose>
											<c:when
												test="${! empty (eventData.longDescription)}">
<div class="col-md-7 row">
${eventData.longDescription}  
  
</div>

</c:when>
</c:choose>



<div class="cl"></div>
<div class="col-md-7 margin20">
<div class="row">
<c:choose>
				<c:when test="${! empty (eventData.typeGroupName)}">
 <span class="black-title bold-text">About ${eventData.typeGroupName}</span>
 <div class="cl"></div>
 
 ${eventData.description}
 </c:when>
 </c:choose>
 </div>
 </div>
<div class="cl"></div>
<div> 
<div class="cl"></div>
<!-- <b>Have a question about this event?<a href=<c:url value="/contactus"/>"> Contact Us</a></b> -->
 
<div class="black-title bold-text"><spring:theme code="siteOneEventDetailPage.question" /> </div>
<a href="<c:url value="/contactus"/>"> <spring:theme code="siteOneEventDetailPage.contact.us" /> &#8594;</a>

</div>
</div>
</c:if>



<c:if test="${eventData.eventExpiryMessage ne null}">
 ${eventData.eventExpiryMessage}
</c:if>

 
                
                    
</template:page>
