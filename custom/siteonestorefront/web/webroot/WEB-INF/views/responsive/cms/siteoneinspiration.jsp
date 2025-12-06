<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
 <div class="pageSizeDropDown  sort-refine-bar"> 
 <div class="row">
 <div class="col-md-3">
	<form action="${homelink}inspire" method="get" id="inspireForm" class="inspireForm">
	<label style="float:left;margin-right:5px;"><spring:theme code="siteoneInspiration.sortBy" /> &nbsp</label>
	<div class="col-md-8 row">
	<select id="sortOrder" name="sortOrder" style="float:left;"
			class="inspireForm inspireSortOrder form-control">
			<c:if test="${sortBy eq 'FEATURED'}">
			<option value="FEATURED" selected><spring:theme code="siteoneInspiration.featured" /></option>
			</c:if>
			<c:if test="${sortBy ne 'FEATURED'}">
			<option value="FEATURED"><spring:theme code="siteoneInspiration.featured" /></option>
			</c:if>
			<c:if test="${sortBy eq 'ASC'}">
			<option value="ASC" selected>A-Z</option>	
			</c:if>		
			<c:if test="${sortBy ne 'ASC'}">
			<option value="ASC">A-Z</option>	
			</c:if>		
			<c:if test="${sortBy eq 'DESC'}">
			<option value="DESC" selected>Z-A</option>
			</c:if>
			<c:if test="${sortBy ne 'DESC'}">
			<option value="DESC">Z-A</option>
			</c:if>
			</select>
			</div>
	</div>	
	<div class="pull-right col-md-9 col-xs-12 row">
	
	 <div class="col-md-3">Projects ${startInspiration}-${endInspiration} of ${inspiration_total} </div>
		
			
	<div class="col-md-2">
	<label style="float:left;margin-right:5px;">View:  </label>
	<!-- <input type="hidden" name="sortOrder" value="ASC"/>-->
	<div class="col-md-9 row">
		<select id="pageSize" name="pageSize" 
			class="inspireForm pageSize form-control">			
			<option value="24"
				<c:if test="${pageSize == 24}"><c:out value="selected='selected'"/></c:if>>24</option>
			<option value="48"
				<c:if test="${pageSize == 48}"><c:out value="selected='selected'"/></c:if>>48</option>
			<option value="72"
				<c:if test="${pageSize == 72}"><c:out value="selected='selected'"/></c:if>>72</option>
		</select>
		</div>
		</div>
		
		 
	 <nav:inspirationPagination top="false"  supportShowPaged="${inspiration_isShowPageAllowed}" supportShowAll="${inspiration_isShowAllAllowed}"  searchPageData="${inspirationSearchPageData}" searchUrl="${inspirationQueryUrl}"  numberPagesShown="${inspiration_numberPagesShown}"/>
 
	
		</div>
	</form>
		</div>
 </div>
	<br />	<br />
	<div class="cl"></div>
<div class="product__listing product__grid">
	<c:forEach items="${inspirationPageData.results}" var="insList">
		<div class="col-xs-6 col-sm-4">
			<div class="product-item-box3">
			
				<c:if test="${not empty insList.inspirationMedia.url }">
					<img src="${insList.inspirationMedia.url}" title="Image:${insList.title}" style="width:100%;height:auto;"/>
				</c:if>
				<div class="product-item-detail">
				  <div class="store-specialty-heading margin20"><b>${insList.title}</b></div>
				Location:${insList.location}<br /> Designed
				By:${insList.designedBy}<br />
				<br />
				<p class="two-line-text">"${insList.snippetOfTheStory}"</p>
				<br /><br />
				<a href="<c:url value="/inspire/"/>${insList.inspirationCode}"
					class="btn btn-primary btn-block mb-btn">View Project</a>


</div>
			</div>
		</div>
	</c:forEach>

	
</div>
	<br />	<br />
	<div class="cl"></div>
<form action="${homelink}inspire" method="get" id="inspireForm" class="inspireForm">
		 <nav:inspirationPagination top="false"  supportShowPaged="${inspiration_isShowPageAllowed}" supportShowAll="${inspiration_isShowAllAllowed}"  searchPageData="${inspirationSearchPageData}" searchUrl="${inspirationQueryUrl}"  numberPagesShown="${inspiration_numberPagesShown}"/>
	View: &nbsp
	
		<select id="pageSize" name="pageSize" 
			class="inspireForm pageSize">			
			<option value="24"
				<c:if test="${pageSize == 24}"><c:out value="selected='selected'"/></c:if>>24</option>
			<option value="48"
				<c:if test="${pageSize == 48}"><c:out value="selected='selected'"/></c:if>>48</option>
			<option value="72"
				<c:if test="${pageSize == 72}"><c:out value="selected='selected'"/></c:if>>72</option>
		</select>
	</form>