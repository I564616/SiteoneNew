<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:url value="/sdssearch" var="sdsSearchUrl" />
 
<div>
	<h1 class="headline sdsHeaderLabel"><spring:theme code="sdsSearchPage.safety.datasheets" /> </h1>
	<p>
		
		<spring:theme code="sdsSearchPage.enter.product.name" />
	</p>
	<br><br>
	<input type="hidden" id="sdsSearchCount" value="${sdsSearchCount}"/>
	<div class="sdsSearch-wrapper">
		<div  class="row sdsSearch-fields">
		<form name="sdsSearchForm" id="sdsSearchForm" method="get" action="" style="padding:0px;">
			<div class="col-md-4 col-sm-9 col-xs-8 alignSearchXs"><input type="text" role="search" aria-label="sdssearch" name="text" value="${fn:escapeXml(searchText)}" class="form-control" id="sds-search-input"></div>
			<div class="col-md-2 col-sm-3 col-xs-4 alignSearchXs"><button id="sds-search-button" type="button" class="btn btn-primary button-grey" id="dtm-sds-label-search" dtm="sds-label-search"><spring:theme code="sdsSearchPage.search" /></button></div>
			<input type="hidden" name="q" value="" id="q"/> 
		</form>
	</div>
	
	<div id="sdsSearchResults" class="col-md-5  border0" onscroll="">
		<c:forEach items="${searchPageData.results}" var="product" varStatus="loopStatus">
			<c:if test="${!(product.sds == null && product.label == null)}">
					<table class="table product-compare">
				 	
					<input id="mySDSList${loopStatus.index}" type="hidden" value='${ycommerce:getJSONFromList(product.sds)}'/>
					<input id="myLabelList${loopStatus.index}" type="hidden" value='${ycommerce:getJSONFromList(product.label)}'/>  
					 
					<tr>
					<td><div id="productLongDesc" class='productLongTitle proClass${loopStatus.index}' data-code="${product.itemNumber}" data-index="${loopStatus.index}" data-description="${fn:escapeXml(product.productShortDesc)}" data-codeforurl="${product.code}">${fn:escapeXml(product.productShortDesc)}</div>
					 
					<div class='productLongTitle proClass${loopStatus.index}' data-code="${product.itemNumber}" data-index="${loopStatus.index}" data-description="${fn:escapeXml(product.productShortDesc)}" data-codeforurl="${product.code}">${product.itemNumber}</div>
					 </td>
					</tr>
				</table>
				
			</c:if>
			<div id="productDescriptionMobile${loopStatus.index}" class="hidden proClassDesc col-md-7 margin20 hidden-md hidden-lg">
	
	<div class="panel panel-default">
	
	
	<table class="table product-compare-info">
				<tr><td colspan="2"><b><spring:theme code="sdsSearchPage.product.info" /></b></td></tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.item.description" /></td>
					<td><span class="description" class="content-product-title"></span></td>
				</tr>
				<tr>
					<td><spring:theme code="sdsSearchPage.item.no" /></td>
					<td><span class="productCode"></span></td>
				</tr>
				<tr>
					<td><b><spring:theme code="sdsSearchPage.product.sds" /></b></td>
				</tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.sds" /> 
					</td>
					<td><div class="sdsList" style="text-decoration:underline;"></div>
						<span class="noSds" class="collapse"> <spring:theme code="sdsSearchPage.no.sds" /></span></td>
				</tr>
				<tr>
					<td><b><spring:theme code="sdsSearchPage.product.labels" /></b></td>
					<td>&nbsp;</td>
				</tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.label" /> 
					</td>
					<td><div class="labelList" style="text-decoration:underline;"></div>
						<span class="noLabel" class="collapse"><spring:theme code="sdsSearchPage.no.label" /></span></td>
				</tr>
				<tr>
					<td colspan="2"><a class="itemInfoLink" href=""><spring:theme code="sds.search.full.product.information"/></a></td>
				</tr>
			</table>
	</div>
	
	</div>
		</c:forEach>
		<dl id="searchResultList">
			  
		</dl>
	<div class="cl"></div>
	</div>

	<div id="productDescription" class="hidden col-md-7 margin20 hidden-sm hidden-xs">
	
	<div class="panel panel-default">
	
	
	<table class="table product-compare-info">
				<tr><td colspan="2"><b><spring:theme code="sdsSearchPage.product.info" /></b></td></tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.item.description" /></td>
					<td><span class="description" class="content-product-title"></span></td>
				</tr>
				<tr>
					<td><spring:theme code="sdsSearchPage.item.no" /></td>
					<td><span class="productCode"></span></td>
				</tr>
				<tr>
					<td><b><spring:theme code="sdsSearchPage.product.sds" /></b></td>
				</tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.sds" />  
					</td>
					<td><div class="sdsList" style="text-decoration:underline;"></div>
						<span class="noSds" class="collapse"> <spring:theme code="sdsSearchPage.no.sds" /></span></td>
				</tr>
				<tr>
					<td><b><spring:theme code="sdsSearchPage.product.labels" /></b></td>
					<td>&nbsp;</td>
				</tr>
				<tr class="bg-lightGrey">
					<td><spring:theme code="sdsSearchPage.label" /> 
					</td>
					<td><div class="labelList" style="text-decoration:underline;"></div>
						<span class="noLabel" class="collapse"><spring:theme code="sdsSearchPage.no.label" /></span></td>
				</tr>
				<tr>
					<td><a class="itemInfoLink" href=""><spring:theme code="sds.search.full.product.information"/></a></td>
					<td>&nbsp;</td>
				</tr>
			</table>
	</div>
	
	</div>
		<div class="cl"></div> 
		</div> 
		<div class="cl"></div> 
	</div>
	<div class="cl"></div>
	
	<div id="sdsSearchFeedback">
		<a href="/en/contactus" target="_blank"><spring:theme code="sdsSearchPage.feedback" /></a>
	</div>
</div>
