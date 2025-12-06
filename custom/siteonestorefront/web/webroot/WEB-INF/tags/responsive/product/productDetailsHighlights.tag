<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<c:if test="${not empty highlights}"> 
<c:set var="highlightsListCount" value="${fn:length(highlights)}" />
	<div class="productDetailsHeighlights">
		<div class="headlineRow">
			<div class="headline">Product Highlights&nbsp;&nbsp;</div>
			<!--<common:productHighlightsIcon />-->
			<div class="viewSpecSheet hidden"><button class="viewSpecSheetButton" onclick="ACC.productDetail.productHighlightsButton();"><spring:theme code="pdp.view.info.guides"/>
			</button></div>
		</div>
		<div class="specRow hidden-md hidden-lg">
		    <c:forEach var="mobileData" items="${highlights}" varStatus="loopMobileData">
		    	<c:if test="${highlightsListCount eq 6 || highlightsListCount eq 4}"> 
		    		<div class="specColumn width47">
						<span class="specHeading">${mobileData.name}</span>
						<span class="specData">${mobileData.value}</span>
					</div> 
		    	</c:if>
		    	<c:if test="${highlightsListCount eq 2 || highlightsListCount eq 1}"> 
		    		<div class="specColumn width97">
						<span class="specHeading">${mobileData.name}</span>
						<span class="specData">${mobileData.value}</span>
					</div> 
		    	</c:if>  
		    	<c:if test="${highlightsListCount eq 5 || highlightsListCount eq 3}">
		    		<c:if test="${!loopMobileData.last}">  
			    		<div class="specColumn width47">
							<span class="specHeading">${mobileData.name}</span>
							<span class="specData">${mobileData.value}</span>
						</div> 
					</c:if>  
					<c:if test="${loopMobileData.last}"> 
						<div class="specColumn width97">
						<span class="specHeading">${mobileData.name}</span>
						<span class="specData">${mobileData.value}</span>
						</div> 
					</c:if>  
		    	</c:if>  
	   		 </c:forEach>
		</div>
		<div class="specRow hidden-xs hidden-sm">
		    <c:forEach var="desktopData" items="${highlights}" varStatus="loopDesktopData">
		    	<c:if test="${highlightsListCount eq 6}"> 
		    		<div class="specColumn width31">
						<span class="specHeading">${desktopData.name}</span>
						<span class="specData">${desktopData.value}</span>
					</div> 
		    	</c:if>
		    	<c:if test="${highlightsListCount eq 4 || highlightsListCount eq 2}"> 
		    		<div class="specColumn width47">
						<span class="specHeading">${desktopData.name}</span>
						<span class="specData">${desktopData.value}</span>
					</div> 
		    	</c:if>  
		    	<c:if test="${highlightsListCount eq 1}">
		    		<div class="specColumn width97">
						<span class="specHeading">${desktopData.name}</span>
						<span class="specData">${desktopData.value}</span>
					</div> 
		    	</c:if> 
		    	<c:if test="${highlightsListCount eq 3}">
		    		<c:if test="${!loopDesktopData.last}">  
			    		<div class="specColumn width47">
							<span class="specHeading">${desktopData.name}</span>
							<span class="specData">${desktopData.value}</span>
						</div> 
					</c:if>  
					<c:if test="${loopDesktopData.last}"> 
						<div class="specColumn width97">
						<span class="specHeading">${desktopData.name}</span>
						<span class="specData">${desktopData.value}</span>
						</div> 
					</c:if>  
		    	</c:if>  
		    	<c:if test="${highlightsListCount eq 5}">
		    		<c:if test="${loopDesktopData.index < 3}">  
			    		<div class="specColumn width31">
							<span class="specHeading">${desktopData.name}</span>
							<span class="specData">${desktopData.value}</span>
						</div> 
					</c:if>  
					<c:if test="${!(loopDesktopData.index < 3)}"> 
						<div class="specColumn width47">
						<span class="specHeading">${desktopData.name}</span>
						<span class="specData">${desktopData.value}</span>
						</div> 
					</c:if>  
		    	</c:if>  
	   		 </c:forEach>
		</div>
	</div>
</c:if>