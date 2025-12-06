<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
	
<h1 class="headline"><spring:theme code="assemblyEditPage.edit.assembly" /></h1>
<div class="col-md-12 col-xs-12">
<div class="row">
<c:set var="/assembly/editList" value="editList"/>
   	<form:form action="${editList}" method="post" modelAttribute="siteOneEditSavedListForm" id="siteOneEditSavedListForm">   	
		<div class="col-md-4 edit-assembly-container">
	   	<div class="row edit-assembly-content">
		    <label class="assembly-name-title"><form:label path="name"><spring:theme code="assemblyEditPage.assembly.name" /></form:label></label>
		    <div class="cl"></div>
		 	<div class="form-group assembly-name-content"> 
		 		<form:input path="name" maxlength="200" cssClass="form-control"/>
		 		  <div id="nameError" class="create-assembly-error bg-danger"></div>
		 	</div>
		   	<div class="form-group">
		   		<form:hidden path="listName"/>
		   	</div>
		   	<label class="assembly-description-title" for="message">
		   		<form:label path="description" for="message"><spring:theme code="assemblyEditPage.desc" /></form:label>
		   	</label>
		   	<div class="cl"></div>
		  	<div class="form-group assembly-description-content">
		  		<form:textarea path="description" maxlength="200" rows="5" cssClass="form-control" cols="30" id="message"/>
		  			<!-- <div class="margin20">Maximum 200 characters</div> -->
		  			 <span id="message_feedback" style="color:#999;"></span>
		  	</div>
		  	<label class="assembly-owner-title" >
		  		<form:label path="owner"><spring:theme code="assemblyEditPage.assembly.owner" /></form:label>
		  	</label>
		  	<div class="cl"></div>
		  	<div class="form-group assembly-owner-content">
		  		<form:input path="owner" cssClass="form-control" disabled="true"/>
		  	</div>
	  	</div>
  	</div>
  	<div class="col-md-9 margin20 edit-assembly-actions-wrapper">
	  	<div class="row btnmobile">
		  	<div class="col-md-3 col-xs-12 btnmargin">
		  		<input type="button" value='<spring:theme code="assemblyListPages.saveChanges" />' class="btn btn-primary btn-block modifyAssembly"/>
		  	</div>
		    <div class="col-md-2 col-xs-12">
		    	<input type="button" value='<spring:theme code="basket.save.cart.action.cancel" />' class="btn btn-default btn-block" onclick="window.location='/assembly'"/>
		    </div>
	   		<div class="col-md-3 col-md-offset-2 col-xs-12">
	   			<input type="button" value='<spring:theme code="assemblyListPages.deleteAssembly" />' class="btn btn-default btn-block pull-right" onclick="window.location='/assembly/deleteList?code=${siteOneEditSavedListForm.code}'"/>
	  		</div>
	  	</div>
	</div>
</form:form>
   
   </div>
   </div>