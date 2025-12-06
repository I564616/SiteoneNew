<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ attribute name="checkoutSteps" required="true" type="java.util.List" %>
<%@ attribute name="progressBarId" required="true" type="java.lang.String" %>
<%@ attribute name="paymentType" required="false" type="java.lang.String" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<!--  

<ycommerce:testId code="checkoutSteps">
    <div class="checkout-steps ${cssClass}">
        <c:forEach items="${checkoutSteps}" var="checkoutStep" varStatus="status">
            <c:url value="${checkoutStep.url}" var="stepUrl"/>
            <c:choose>
                <c:when test="${progressBarId eq checkoutStep.progressBarId}">
                    <c:set scope="page"  var="activeCheckoutStepNumber"  value="${checkoutStep.stepNumber}"/>
                    <a href="${stepUrl}" class="step-head js-checkout-step active step-checkout-title col-md-3" data-stepsCount="${fn:length(checkoutSteps)}" data-stepNo="${checkoutStep.stepNumber}">
                        <div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
                    </a>
                   <%--  <div class=""><jsp:doBody/></div> --%>
                </c:when>
                <c:when test="${checkoutStep.stepNumber > activeCheckoutStepNumber}">
                    <a href="${stepUrl}" class="step-head js-checkout-step step-head-checkout test col-md-3 " data-stepsCount="${fn:length(checkoutSteps)}" data-stepNo="${checkoutStep.stepNumber}">
                        <div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${stepUrl}" class="step-head js-checkout-step col-md-3 active "  data-stepsCount="${fn:length(checkoutSteps)}" data-stepNo="${checkoutStep.stepNumber}">
                        <div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
                        <!-- <div class="edit">
                            <span class="glyphicon glyphicon-pencil"></span>
                        </div> 
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

</ycommerce:testId>



-->


	<div class=""><jsp:doBody/></div>
					
					
