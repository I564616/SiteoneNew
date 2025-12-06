<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:forEach items="${breadcrumbs}" var="breadcrumb">
<%-- 	${breadcrumb.name} --%>
</c:forEach>

<c:if test="${cookie['uploadFail'].getValue() eq 'uploadEmpty'}"></c:if>
<c:if test="${cookie['uploadFail'].getValue() eq 'uploadSuccess'}"></c:if>
<c:if test="${cookie['uploadFail'].getValue() eq 'uploadError'}"></c:if>

<input type="hidden" id="csvList" value="${csvContent}"/>
<input type="hidden" id="uploadListCode" value="${listCode}"/>
<h1 class="headline create_list"><spring:theme code="text.savedList.createList" /></h1>

<div id="import-csv-alerts"></div>
<div id="listglobalMessages" style="display:none">
	<div class="global-alerts">	
		
		<div class="alert alert-box alert-dismissable alert-msg-section">
					
					<div class="alert-msg-text"><spring:theme code="saved.list.duplicate" /></div>
					<div class="cl"></div>
				</div>
			</div>
</div>
<p><spring:theme code="text.savedList.header"/></p><br> 
<c:set var="/savedList/createList" value="createList"/>
   <form:form action="${createList}" method="post" modelAttribute="siteoneSavedListCreateForm" id="siteoneSavedListCreateForm" enctype="multipart/form-data">
<div class="col-md-4 col-sm-12 col-xs-12">
<div class="row">
   <div class="form-group">
   <form:label path="name"><spring:theme code="addToSavedList.listName"/></form:label>
   <spring:message code="addToSavedList.listName" var="ListName"/>
   <form:input path="name" placeholder="Enter list name" class="form-control listName savedListName" id="name" maxlength="200"/> 

   <div id="nameError" class="create-list-error bg-danger"></div>
   </div>
   <p><spring:theme code="text.savedList.date"/>&nbsp;${createdDate}</br>
    
   <spring:theme code="text.savedList.currentUser"/>&nbsp;${currentUser}
   </p>
   <br>
 	 <div class="form-group">
   <form:label path="description" for="message" ><spring:theme code="savedListCreatePage.desc" /></form:label>
   <form:textarea path="description" maxlength="200" class="form-control listDesc" rows="5" cols="10" style="resize:none;" id="message"/>
   <!--  <div class="margin20">Maximum 200 Characters</div> -->
   <span id="message_feedback" style="color:#999;"></span>
	 </div>
	
</div>
   </div>
   <div class="cl"></div>
    <h3 class="store-specialty-heading"><label for="savedListSearch"><span class="bold-text"><spring:theme code="text.savedList.addProduct"/></span></label></h3>
    <p>Select a method:</p>
   <div class="cl"></div>
   <div class="cl"></div>
   <div class="col-md-4 col-sm-12 col-xs-12">
   <div class="row"> 
   <div class="row"> 
    <div class="label-column2"><div class="label-highlight"><span class="colored-radio"><input type="radio" id="addProducts" checked/></span> <label>Enter Product #</label></div></div>
    <div class="cl hidden-md hidden-lg marginTop35">&nbsp;</div>
    <div class="label-column2"><div class="label-highlight"><span class="colored-radio"><input type="radio" id="uploadList"/></span> <label>Upload a .csv</label></div></div>
   </div>
   </div>
   </div>
    <div class="cl"></div>
    <div class="col-md-6 col-sm-12 col-xs-12 adding-products">
<div class="row">	 
	
	
	
	<div id="saved_list_item"></div>
	
	
	
	 <form:hidden path="product" id="product_list"/>
	 <div id="base_product_error" style="display: none;" class="bg-danger marginTop35"><p class="text-danger panel-body"><spring:theme code="saved.list.product.base.variant"/></p></div>
	 <div id="uom_product_error" style="display: none;" class="bg-danger marginTop35"><p class="text-danger panel-body"><spring:theme code="saved.list.product.uom"/></p></div>
	 <div id="uom_hidden_error" style="display: none;" class="bg-danger marginTop35" ><p class="text-danger panel-body"><spring:theme code="saved.list.product.uom.hidden"/></p></div>
		<div id="invalid_code" style="display: none;" class="bg-danger marginTop35"><p class="text-danger panel-body"><spring:theme code="saved.list.product.not.found"/></p></div>
		<div class="col-md-8 col-sm-7 col-xs-12"><div id="invalid_code_empty" style="display: none;" class="bg-danger"><p class="text-danger panel-body"><spring:theme code="saved.list.product.code.empty"/></p></div></div>
		<div id="duplicate_code" style="display: none;" class="bg-danger"><p class="text-danger panel-body"><spring:theme code="saved.list.product.code.duplicate"/></p></div>
		<div class="col-md-4 col-sm-5 col-xs-12 empty-qty-container"><div id="empty_quantity" style="display: none;" class="" ><p class="panel-body" style="color:red;"><spring:theme code="saved.list.product.quantity.empty"/></p></div></div>
		<div class="col-md-8 col-sm-7 col-xs-12 add-product-to-list-wrapper marginTop20">
		<div class="row">
		<div class="col-md-6 col-sm-7 col-xs-8 productField">
		<div class="form-group">
				<form:label path="product" for="savedListSearch"> <span id="listheader">Product #<!-- Add To List --></span> </form:label>
				<div class="cl"></div>
				<input type="text" name="serachBox" id="savedListSearch" class="form-control" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />'/> 
		</div>
		</div>
		<input type="hidden" id="sessionStoreList" value="${sessionStore.address.phone}"/>
		<div class="col-md-3 col-sm-3 col-xs-4 qty-wrapper">
			<div class="form-group">
				<form:label path="product" for="quantityId"><spring:theme code="savedListCreatePage.quantity" /></form:label>
				<div class="cl"></div>
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="row"><input type="text" name="quantity" id="quantityId" maxlength="5" class="form-control" value="1"/> </div>
					</div>
			</div>
		</div>
		<div class="col-md-3 col-sm-2 col-xs-12 add_Product_to_savedList_wrapper">
		<div class="mb-btn-border">
		<p>&nbsp;</p>
			<input type="button" value='<spring:theme code="basket.add.to.cart" />' class="btn btn-primary btn-block" id="add_Product_to_savedList">
		</div>
		</div>
	
	
	<!-- <div class="visible-xs hidden-sm hidden-md hidden-lg"><div class="col-xs-12"><input type="button" value="<spring:theme code="basket.add.to.cart" />" class="btn btn-primary btn-block" id="add_Product_to_savedList"></div></div> -->
	</div>
	</div>
</div>
<div class="row">
 	<div class="margin20">
 	<div class="row"> 
   		<div class="col-md-3 col-sm-6 col-xs-12 create-list-button-wrapper"><input type="button" value='<spring:theme code="savedListCreatePage.create.list" />' id="createList" class="btn btn-primary btn-block"/></div>
 		 <div class="col-md-3 col-sm-6 col-xs-12"><input type="button" value='<spring:theme code="basket.save.cart.action.cancel" />' class="btn btn-block btn-default" onclick="window.location='/savedList'"/></div>
   </div>
   </div>
   </div>
   </div>
  
  <div class="cl"></div> 
<div class="col-md-4 col-sm-12 col-xs-12 uploadList-sec" style="display:none;">

<div class="row">	 
  
		<%-- <input class="uploadempty"  value="${errorEmptyFile}" autocomplete='off'/>
		<input class="uploaderror"  value="${isUploadError}"/>
		<input class="uploadsuccess" value="${isUploadSuccess}"/> --%>
    	<p>Use the template below to format your list</p>
    	 <div class="download_list">
                	 <a href="/savedList/downloadCSVFile" target="_blank"><span class="glyphicon glyphicon-download-alt"></span></a>&nbsp; 
                     <a href="/savedList/downloadCSVFile" target="_blank">Download template as Excel (.CSV file)</a>
                	 </div>
	 <div class="row">
 	<div class="margin20">
   		<div class="col-md-6 col-sm-6 col-xs-12 create-list-button-wrapper">
               <input type="file" name="csvFile" id="csvFile" data-file-max-size="${fn:escapeXml(csvFileMaxSize)}" accept="text/csv" class="btn btn-primary btn-block js-file-upload__input" value="Select file to upload" style="position: absolute;
    opacity: -1;"/>
               <div class="btn btn-primary btn-block">Select file to upload</div>
              <!--  <button type="submit" id="uploadCsvFile" class="btn btn-primary btn-block">Select file to upload</button>style="position: absolute;color: transparent;border: none;width: 100%;opacity: -1;" -->
         
          </div>
           
          <div class="col-md-3 col-sm-6 col-xs-12">
 		 <input type="button"  value="Cancel" class="btn btn-block btn-default" onclick="window.location='/savedList'"/>
 		 </div>
   </div>
   </div>
  
   </div>

   
   	
</div>

 
     </form:form>
   
   