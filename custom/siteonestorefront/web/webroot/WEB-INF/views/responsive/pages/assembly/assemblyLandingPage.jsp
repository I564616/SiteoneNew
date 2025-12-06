<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
 
 <template:page pageTitle="${pageTitle}">

	<div class="col-md-10 col-sm-8 row">
		<h1 class="headline hidden-xs"><spring:theme code="assemblyLandingPage.assemblies" /></h1>
		 <h2 class="headline3 hidden-sm hidden-md hidden-lg"><spring:theme code="assemblyLandingPage.assemblies" /></h2>
	</div>
		
	
	<c:choose>
<c:when test="${not empty savedListData}">


<div class="col-md-2 col-sm-4">
	<div class="row">
		<a href="<c:url value="/assembly/createList"/>" class="btn btn-primary btn-block create-list-btn">
		<spring:theme code="text.assembly.createList" /></a>
	</div>
</div>
		<c:if test="${isListDeleted eq 'success'}">
 		<div class="row">
				<div id="success" class="col-md-12">
				<div class="margin40">
					<br/>
					<span class="icon-success col-xs-4"></span>
					<p class="deleted-msg black-title col-md-5 col-xs-8""><b><spring:theme code="assemblyLandingPage.success" />!</b><br/><spring:theme code="assemblyLandingPage.assembly.deleted" />.</p><p class="cl"></p>
				</div>
				</div>
		 </div>
		 </c:if>
			 <div class="cl"></div>
			 
<div class="hidden-lg hidden-md hidden-sm">
<hr  class="assemblyLandingPage"></hr>
<c:forEach items="${savedListData}" var="savedLists" varStatus="index">
<div class="row listLandingPage">
	<div class="col-xs-12">
		<div class="col-xs-6"><b><spring:theme code="assemblyLandingPage.assembly.name" /></b></div>
		<div class="col-xs-6 listData"><a href="<c:url value="/assembly/listDetails?code="/>${savedLists.code}">${savedLists.name}</a></div>
	</div>
	<div class="col-xs-12">
		<div class="col-xs-6"><b><spring:theme code="assemblyLandingPage.date.updated" /></b></div>
		<div class="col-xs-6 listData"><fmt:formatDate value ="${savedLists.modifiedTime}" type="date" timeStyle="long"/></div>
	</div>
	<div class="col-xs-12">
		<div class="col-xs-6"><b><spring:theme code="assemblyLandingPage.shared.private" /></b></div>
		<div class="col-xs-6 listData">${savedLists.isShared}</div>
	</div>
	<div class="col-xs-12">
		<div class="col-xs-6"><b><spring:theme code="assemblyLandingPage.assembly.owner" /></b></div>
		<div class="col-xs-6 listData">${savedLists.user}</div>
	</div>
	<div  class="col-xs-12">		
				<c:if test="${savedLists.isModified eq true}" >
				<a href="<c:url value="/assembly/deleteList?code="/>${savedLists.code}"><spring:theme code="assemblyLandingPage.delete" /></a>
			</c:if>
		
	</div>
</div>
<hr  class="listLandingPage"></hr>
</c:forEach>
</div>
<div class="hidden-xs">
<nav:listPagination top="true" msgKey="text.savedList.page.currentPage" showCurrentPageInfo="true" 
supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" 
searchUrl="${searchUrl}"  numberPagesShown="${numberPagesShown}" /></div>
<div class="col-md-12 col-sm-12 hidden-xs zero-left-padding">
	<div class="col-md-12 col-sm-12 title-bar">
		<div class="col-md-2 col-sm-3"><spring:theme code="assemblyLandingPage.last.udated" /><c:if test="${sortOrder eq 'desc'}"><a href="/assembly?sortOrder=asc" > <span  class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span></a></c:if>
		<c:if test="${sortOrder eq 'asc'}"><a href="/assembly?sortOrder=desc"> <span  class="glyphicon glyphicon-triangle-top" aria-hidden="true"></span></a></c:if></div>
		<div class="col-md-3 col-sm-3"><spring:theme code="assemblyLandingPage.name" /></div>
		<div class="col-md-3 col-sm-2"><spring:theme code="assemblyLandingPage.setting" /></div>
		<div class="col-md-4 col-sm-4"><spring:theme code="assemblyLandingPage.owner" /></div>
	</div>
	<c:forEach items="${savedListData}" var="savedLists" varStatus="index">
		<div class="col-md-12 col-sm-12 assembly-lists-container">
			<div class="col-md-2 col-sm-3"><fmt:formatDate value ="${savedLists.modifiedTime}" type="date" timeStyle="long"/></div>
			<div class="col-md-3 col-sm-3 list-name"><a href="<c:url value="/assembly/listDetails?code="/>${savedLists.code}">${savedLists.name}</a></div>
			<div class="col-md-3 col-sm-2">${savedLists.isShared}</div>
			<div class="col-md-3 col-sm-3">${savedLists.user}</div>
			<%-- <td><a href="/savedList/deleteList?name=${savedLists.name}">X</a></td> --%>
			<div class="text-right col-md-1 col-sm-1 delete-list">				
						<c:if test="${savedLists.isModified eq true}">	
						<a href="<c:url value="/assembly/deleteList?code="/>${savedLists.code}"><span class="icon-close"></span></a>					
						</c:if>
			</div>
		</div>
	</c:forEach>
</div>
</c:when>
<c:otherwise>
<div class="row margin20"></div>
<div class="cl"></div>
<p class="black-title"><b><spring:theme code="text.assembly.empty"/></b></p>
		<c:if test="${isListDeleted eq 'success'}">
 		<div class="row">
				<div id="success" class="col-md-12">
				<div class="margin40">
					<br/>
					<span class="icon-success col-xs-4"></span>
					<p class="deleted-msg black-title col-md-5 col-xs-8""><b><spring:theme code="assemblyLandingPage.success" />!</b><br/><spring:theme code="assemblyLandingPage.assembly.deleted" />.</p><p class="cl"></p>
				</div>
				</div>
		 </div>
		 </c:if>
<div class="cl"></div>
<p class="col-md-6 row noAssemblyText"><spring:theme code="assemblyLandingPage.text1" />.<br class="hidden-xs"/> 
<spring:theme code="assemblyLandingPage.text2" /><br class="hidden-xs"/>
<spring:theme code="assemblyLandingPage.text3" /></p>
<div class="cl"></div>
<div class="margin20">
<div class="row margin20"></div>
<div class="row">
<div class="col-md-3 col-sm-6 col-xs-12 assemblyCrtBtn"><a href="<c:url value="/assembly/createList"/>" class="btn btn-primary btn-block"><spring:theme code="text.assembly.createAssembly"/></a></div>
<div class="col-md-3 col-sm-6 col-xs-12 assemblyMyAcctBtn"><a href="<c:url value="/my-account/account-dashboard"/>" class="btn btn-default btn-block"><spring:theme code="text.savedList.myAccount"/></a></div>
</div>
</div>
</c:otherwise>
</c:choose>

<nav:listPagination top="false" msgKey="text.savedList.page.currentPage" showCurrentPageInfo="true" 
supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" 
searchUrl="${searchUrl}"  numberPagesShown="${numberPagesShown}" />
<p class="col-md-12 col-sm-12 assembly_desc margin20"><spring:theme code="text.assembly.desc1"/></p>
</template:page>