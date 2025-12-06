<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<h1 class="headline"><spring:theme code="savedListEditPage.edit.list"/></h1>
<div class="col-md-12 col-xs-12 margin20 edit-list-form-wrapper">
<div class="row">
<c:set var="/savedList/editList" value="editList"/>

   <form:form action="${editList}" method="post" modelAttribute="siteOneEditSavedListForm" id="siteOneEditSavedListForm">
     <div class="col-md-4">
 <div class="form-group edit-list-form-label">
   <form:label path="name"><spring:theme code="addToSavedList.listName"/></form:label>
   </div>
   <div class="form-group edit-list-form-value">
   <form:input path="name" maxlength="200" cssClass="form-control"/> 
      <div id="nameError" class="create-list-error bg-danger"></div>
   </div>
   <div class="form-group edit-list-form-label">
   <form:hidden path="listName"/>   
   <form:label path="description" for="message"><spring:theme code="assemblyEditPage.desc" /></form:label>   
   </div>
   	<div class="form-group edit-list-form-value">
  	<form:textarea path="description" rows="5" cols="0" maxlength="200" cssClass="form-control" style="resize:none;" id="message"/>
  	 <!-- <div class="margin20">Maximum 200 Characters</div> -->
  	  <span id="message_feedback" style="color:#999;"></span>
  	</div>
  	<div class="form-group edit-list-form-label">
  	<form:label path="owner"><spring:theme code="savedListEditPage.list.owner" /></form:label>
  	</div>
  	<div class="form-group edit-list-form-value">
  	<form:input path="owner" disabled="true" cssClass="form-control"/>
  	</div>
  	</div>
  	 <div class="col-md-9 margin20 edit-list-action-wrapper">
  	 <div class="row">
  	 <div class="col-md-3 col-xs-12 save-btn-container"><input type="button" value='<spring:theme code="assemblyListPages.saveChanges" />' class="btn btn-primary btn-block modifyList"/></div>
     <div class="col-md-2 col-xs-12 cancel-btn-conatiner"><input type="button" value='<spring:theme code="basket.save.cart.action.cancel" />' class="btn btn-default btn-block" onclick="window.location='/savedList'"/></div>
     <div class="col-md-2 col-md-offset-2 col-xs-12 delete-btn-conatiner"><input type="button" value='<spring:theme code="assemblyListPages.deleteList" />' class="btn btn-default btn-block" onclick="window.location='/savedList/deleteList?code=${siteOneEditSavedListForm.code}'"/></div>
   </div>
   </div>
   </form:form>
 </div>
  </div>
  <div class="cl"></div>