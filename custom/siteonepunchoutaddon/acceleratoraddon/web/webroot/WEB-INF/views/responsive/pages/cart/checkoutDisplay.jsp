<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="addoncart" tagdir="/WEB-INF/tags/addons/siteonepunchoutaddon/responsive/cart" %>
<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="/cxml/requisition" context="${originalContextPath}/punchout" var="requisitionUrl"/>
<c:url value="/cxml/cancel" context="${originalContextPath}/punchout" var="cancelUrl"/>
</div>
<c:if test="${isInspectOperation ne true}"> 
	<div class="col-md-3 pull-left shopping-linkBottom">
		 
			<a href="${continueShoppingUrl}">&#8592; <spring:theme
					code="cart.page.continue" />
			</a>
		 
	</div>
	</c:if>
<div class="cart__actions col-md-5 col-xs-12  col-sm-6 pull-right">
    <div class="row-basket-bottom-punchout">
        <div class="row">
            <c:choose>
                <c:when test="${isInspectOperation}">
                    <div class="col-sm-4 col-md-4 ">
                        <addoncart:returnButton url="${requisitionUrl}" />
                    </div>
                </c:when>
                <c:otherwise>
                     
                    <div class="col-sm-5 col-md-4 col-md-offset-1">
                        <addoncart:cancelButton url="${cancelUrl}" />
                    </div>
                    <div class="col-sm-7 col-md-7">
                        <addoncart:returnButton url="${requisitionUrl}" />
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<div class="cl"></div>