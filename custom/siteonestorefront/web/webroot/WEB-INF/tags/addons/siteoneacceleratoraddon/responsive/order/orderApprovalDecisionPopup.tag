<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="orderApprovalData" required="true" type="de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData"%>
<%@ attribute name="orderApprovalDecisionForm" required="true" type="de.hybris.platform.siteoneacceleratoraddon.forms.OrderApprovalDecisionForm"%>
<%@ attribute name="orderApprovalDecisionURL" required="true" type="java.lang.String"%>
<%@ attribute name="decision" required="true" type="java.lang.String"%>
<%@ attribute name="actionButtonLabel" required="true" type="java.lang.String"%>
<%@ attribute name="actionButtonClass" required="true" type="java.lang.String"%>
<%@ attribute name="modalPopupClass" required="true" type="java.lang.String"%>
<%@ attribute name="commentLabel" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="commentMaxChars" value="255" />
<c:set var="popupOpenedFor" value="accept" />

<div>
    <form:form method="post" cssClass="orderApprovalDecisionForm" modelAttribute="orderApprovalDecisionForm"
        action="${orderApprovalDecisionURL}">
        <c:choose>
            <c:when test="${orderApprovalData.approvalDecisionRequired}">
                <div>
                    <form:input type="hidden" name="workflowActionCode" path="workFlowActionCode" value="${orderApprovalData.workflowActionModelCode}" />
                    <form:input type="hidden" name="approverSelectedDecision" path="approverSelectedDecision" id="approverSelectedDecision" />

                    <button class="${actionButtonClass}" type="button">
                        <spring:theme code="${actionButtonLabel}" />
                    </button>
                </div>
                <div style="display: none">
                    <div class="${modalPopupClass} comment-modal">
                     <c:if test="${decision eq 'APPROVE'}">
                     <div class="headline"><strong><spring:theme code="text.account.orderApproval.approve.reason1"/></strong><spring:theme code="text.account.orderApproval.approve.reason2"/></div>
                      </c:if>  
                      <c:if test="${decision eq 'REJECT'}">
                      <c:set var="popupOpenedFor" value="reject" />
                     <div class="headline"><strong><spring:theme code="text.account.orderApproval.reject.reason1"/></strong></div>
                      </c:if>
                        <div class="headline">
                            <spring:theme code="${commentLabel}" />
                        </div>
                        <form:textarea path="comments" maxlength="255" onkeyup="ACC.approval.approveOrder('${decision}')"></form:textarea>
                        <div class="form-actions clearfix cl">
                        	<div class="row">
	                        	<div class="col-sm-8 text-red approverDecisionRejectErrorMsg hidden">
	                        		<spring:theme code="text.account.orderApproval.addApproverComments" />
	                        	</div>
	                            <div class="col-sm-4 help-block float-right">
	                                <spring:theme code="responsive.checkout.summary.requestApproval.maxchars" arguments="${commentMaxChars}" />
	                            </div>
                            </div>

                            <div class="form-actions clearfix row flex-dir-column-reverse-xs cl">
                                <div class="col-sm-6">
                                    <button type="button" onclick="ACC.adobelinktracking.approveOrder(this, '', '${popupOpenedFor} order - cancel', 'my account: approve orders', false)" class="btn btn-default btn-block cancelOrderApprovalCommentButton">
                                        <spring:theme code="checkout.summary.requestApproval.cancel" />
                                    </button>
                                </div>
                                <div class="col-sm-6">
                                    <button type="button" onclick="ACC.adobelinktracking.approveOrder(this, '', '${popupOpenedFor} order - ', 'my account: approve orders', true)" class="btn btn-primary btn-block approverDecisionButton col-md-4 pull-right" data-decision="${decision}">
                                        <spring:theme code="${actionButtonLabel}" />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </form:form>
</div>