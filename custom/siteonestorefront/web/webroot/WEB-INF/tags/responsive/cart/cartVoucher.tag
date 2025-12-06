<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ attribute name="voucherDisplay" required="false" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:url value="/cart/voucher/apply" var="applyVoucherAction"/>
<spring:url value="/cart/voucher/remove" var="removeVoucherAction"/>

<c:set var="containerClass">
    <c:choose>
        <c:when test="${not empty errorMsg}">has-error</c:when>
        <c:when test="${not empty successMsg}">has-success</c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
</c:set>
<c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
<c:set var="promotionApplied" value="true" />
</c:forEach>
<c:if test="${empty cartData.quoteData}">
<div class="form-group js-voucher-respond cartmargin ${containerClass}">
    <spring:theme code="text.voucher.apply.input.placeholder" var="voucherInputPlaceholder"/>
    <label class="control-label cart-voucher__label" for="js-voucher-code-text_${voucherDisplay}"><spring:theme
            code="text.voucher.apply.input.label"/></label>
     <input type="hidden" class="voucherDisplay" value="${voucherDisplay}" />
    <form:form id="applyVoucherForm_${voucherDisplay}" class="applyVoucherForm" action="${applyVoucherAction}" method="post" modelAttribute="voucherForm">
       <div class="col-md-6 col-xs-8 voucher-mobile">
       <div class="row">
      
             		 <form:input cssClass="js-voucher-code   form-control input-sm js-voucher-code-text" name="voucher-code"
		                    id="js-voucher-code-text_${voucherDisplay}" maxlength="100" placeholder="${voucherInputPlaceholder}"
		                    path="voucherCode" disabled="${disableUpdate}"/>
           </div>         
             </div>       
		<c:if test="${not disableUpdate}">
		
			 <div class="col-md-3 col-xs-4 apply-mobile"> <button type="button"  class="btn btn-default margin-zero  btn-block cart-voucher__btn js-voucher-apply-btn">
	            <spring:theme code="text.voucher.apply.button.label"/></button></div>
		  
		</c:if>
    </form:form>
<div class="cl"></div>
    <div class="js-voucher-validation-container help-block cart-voucher__help-block">
        ${errorMsg}
        ${successMsg}
    </div>
</div>


<ul id="js-applied-vouchers" class="selected_product_ids clearfix voucher-list row">
    <c:forEach items="${cartData.appliedVouchers}" var="voucher" varStatus="loop">
    <c:set var="hideVoucher" value="hide" />
    <c:if test="${not empty showVoucherList}">
   		 <c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
   		  <c:choose>
    		<c:when test="${voucher eq showVoucher}">
    			<c:set var="hideVoucher" value="" />
   		 	 </c:when>
   		 	  </c:choose>
    	</c:forEach>
    	</c:if>
        <li class="voucher-list__item ${hideVoucher}">
            <form:form id="removeVoucherForm${loop.index}_${voucherDisplay}" action="${removeVoucherAction}" method="post"
                       modelAttribute="voucherForm">
                <span class="js-release-voucher voucher-list__item-box" id="voucher-code-${voucher}">
                     ${voucher}
                     <form:input hidden="hidden"  id ="voucherCode_${voucherDisplay}" value="${voucher}" path="voucherCode"/>
                    <span class="glyphicon glyphicon-remove js-release-voucher-remove-btn voucher-list__item-remove"></span>
                </span>
            </form:form>
        </li>
        
    </c:forEach>
</ul>
</c:if>
