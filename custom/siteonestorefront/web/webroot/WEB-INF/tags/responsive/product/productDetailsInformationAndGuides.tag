<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<c:if test="${not empty product.dataSheetList}">
		
 		<div class="product-info-guidelines">
	 			
					<c:forEach items="${product.dataSheetList}" var="dataSheetMap" varStatus="loop">
					 
					
					 <c:forEach items="${dataSheetMap.value}" var="dataSheet" varStatus="loop">
					 <c:if test="${dataSheet.mimeType eq 'application/pdf'}">
						<c:set var="label" value="${fn:replace(dataSheet.label,'/', '-')}" />
				 		<c:set var="productCode" value="${product.code.toLowerCase()}"/>
				 		<div class="row guidelines-row">
				 		
				          
					 		<div class="col-xs-12">
				    			<div class="row guideline-item-container">
				    				 <div class="pdf-icon text-center bg-light-grey">
							          	<span class="hidden-xs hidden-sm"><common:pdfIcon width="31.2" height="36"/></span>
							          	<span class="hidden-md hidden-lg"><common:pdfIcon width="15.6" height="18"/></span>				          	
				          			</div>
						             <c:choose>
						           	 <c:when test="${not empty dataSheet.sds}">
							           <div class="col-xs-9 col-md-9 flex-dir-column flex bg-light-grey  pad-md-lft-30 justify-center">
							           			<c:if test="${product.multidimensional}"><div class="bold">${dataSheetMap.key}</div></c:if>
								              	<span id="${dataSheetMap.key}" >${dataSheet.sds}</span>
							          	</div>
							          	<div class="col-xs-3 col-md-3 flex-center bg-light-grey download-icon-container">
								              	<a href="${dataSheet.url}" target="_blank" dtm="pdf-${dataSheet.sds}">
									              	<span id="${dataSheetMap.key}" class="download-icon"> 
									              		<span class="hidden-md hidden-lg"><common:downloadIcon/></span> 
									              		<span class="hidden-xs hidden-sm" style="padding-top:5px;"><common:downloadIcon width="11.45" height="17"/></span> 
													</span>
												</a>
							          	</div>  
						          	</c:when>
						          	<c:otherwise>
							          	<div class="col-xs-9 col-md-9 flex-dir-column flex bg-light-grey  pad-md-lft-30 justify-center">
							          			<c:if test="${product.multidimensional}"><div class="bold">${dataSheetMap.key}</div></c:if>
								              	<span id="${dataSheetMap.key}"><spring:theme code="sdsSearchPage.label" /></span>
							          	</div> 
							          	<div class="col-xs-3 col-md-3 flex-center bg-light-grey download-icon-container">
								              	<a href="${dataSheet.url}" target="_blank" dtm="pdf-${dataSheet.sds}">
								              		<span id="${dataSheetMap.key}" class="download-icon"> 
								              			<span class="hidden-md hidden-lg"><common:downloadIcon/></span> 
								              			<span class="hidden-xs hidden-sm" style="padding-top:5px;"><common:downloadIcon width="11.45" height="17"/></span> 
								              		</span>
								              	</a>
							          	</div> 
						          	</c:otherwise>
						          	</c:choose>
						          </div>
				          	</div>
				          	</div>				        
			        </c:if>
					</c:forEach>					
				</c:forEach>		
	</div>
</c:if>