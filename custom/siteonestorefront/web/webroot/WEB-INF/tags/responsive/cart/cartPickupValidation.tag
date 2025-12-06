<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<div class="alert negative display-none" id="noStoreSelected" tabindex="0">
<div class="gc-error-msg marginTop20 js-gc-problem-error flex-center row">	
	<div class="col-xs-12">
		<div class="flex-center gc-cart-global-error-msg">
	<spring:theme code="basket.error.no.pickup.location"/>
	</div>
	</div>
	</div>
</div>