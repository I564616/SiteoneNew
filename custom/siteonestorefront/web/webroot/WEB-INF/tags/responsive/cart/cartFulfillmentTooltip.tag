<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="mode" required="false" %>

<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
	 <div class="tooltip-content hide">
		  <common:FulfillmentTooltipContent fulfillment="${mode}"/>
	 </div> 
	 <common:info-circle-nofill />
</div>