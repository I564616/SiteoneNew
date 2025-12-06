<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="primaryColor" value="#78A22F" />
<div class="col-md-12 padding0 f-s-16 f-s-11-xs-px black-title m-t-5 hardscape-PA-orderConfirm hidden">
   <div class="flex-center">
      <div><common:exclamation-circle width="24" height="24" /></div>
      <div class="p-l-15">
         <div>
            <span class="bold">
               <spring:theme code="productDetails.hardscape.stone.price" />&nbsp;
            </span>
            <span class="hidden-sm hidden-xs">
               <spring:theme code="productDetails.hardscape.stone.weight.cart" />&nbsp;<spring:theme code="productDetails.hardscape.stone.received" />
            </span>
         </div>
         <div class="col-md-12 padding0 hidden-lg hidden-md">
            <spring:theme code="productDetails.hardscape.stone.weight.cart" />&nbsp;<spring:theme code="productDetails.hardscape.stone.received" />
         </div>
      </div>
   </div>
</div>