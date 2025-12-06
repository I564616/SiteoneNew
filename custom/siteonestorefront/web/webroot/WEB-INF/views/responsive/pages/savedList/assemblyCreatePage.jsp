<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<h1 class="headline" style="font-size: 28pt"><spring:theme code="assemblyCreatePage.create.assembly" /></h1>
	<div id="globalMessages"><common:globalMessages/></div>
<p class="marginBottom40"><spring:theme code="text.assembly.header"/></p>
<c:set var="/assembly/createList" value="createAssembly"/>
<form:form action="${createAssembly}" method="post" modelAttribute="siteoneAssemblyCreateForm" id="siteoneAssemblyCreateForm">
<div class="col-md-4 col-sm-12 col-xs-12">
<div class="row">
    <div class="form-group">
   <form:label path="name"><spring:theme code="assemblyCreatePage.assembly.name" /></form:label>
   <form:input path="name" maxlength="200" placeholder="Assembly Name" class="form-control" id="name"/> 
 
   <div id="nameError" class="create-assembly-error bg-danger"></div>
   
 </div>
 
 <p><spring:theme code="text.savedList.date"/>&nbsp;${createdDate}</br>
   <spring:theme code="text.assembly.currentUser"/>&nbsp;${currentUser}
   </p>
   <br/>
    <div class="form-group">
   <form:label path="description" for="message"><spring:theme code="assemblyEditPage.desc" /></form:label>
   <form:textarea path="description" maxlength="200" rows="5" cols="30" class="form-control" id="message"/>
   <span id="message_feedback" style="color:#999;"></span>
   	<!-- <div class="margin20">Maximum 200 characters</div> -->
</div>
 </div>
 </div>
 <div class="cl"></div>
 <div class="col-md-6 col-sm-12 col-xs-12">

</div>
 <div class="cl"></div>
 <div class="col-md-6 col-sm-12 col-xs-12">
<div class="row">
		
	<div id="saved_list_item"></div>
	
</div>
</div>	
 <div class="cl"></div>
	 <div class="col-md-6 col-sm-12 col-xs-12">
<div class="row">	
	<form:hidden path="product" id="product_list"/>
	 <div id="base_product_error" style="display: none;" class="bg-danger"><p class="text-danger panel-body"><spring:theme code="saved.list.product.base.variant"/></p></div>
	 <div id="uom_product_error" style="display: none;" class="bg-danger" ><p class="text-danger panel-body"><spring:theme code="saved.list.product.uom"/></p></div>
	<div id="uom_hidden_error" style="display: none;" class="bg-danger" ><p class="text-danger panel-body"><spring:theme code="saved.list.product.uom.hidden"/></p></div> 
	<div id="invalid_code" style="display: none;" class="bg-danger"><p class="text-danger panel-body"><spring:theme code="saved.list.product.not.found"/></p></div>
	 <div class="col-md-8 col-sm-7 col-xs-12 padding0"><div id="invalid_code_empty" style="display: none;"><p class="text-danger panel-body" color:red;padding-left: 0;><spring:theme code="saved.list.product.code.empty"/></p></div></div>
		<div id="duplicate_code" style="display: none;" class="bg-danger"><p class="text-danger panel-body"><spring:theme code="saved.list.product.code.duplicate"/></p></div>
	<div class="col-md-4 col-sm-5 col-xs-12 padding0"><div id="empty_quantity" style="display: none;" class="" ><p class="panel-body" style="color:red;"><spring:theme code="saved.list.product.quantity.empty"/></p></div></div>
		<span id="invalid_code" style="display: none;"><spring:theme code="assemblyCreatePage.prodcode.invalid" /></span>
	
		<div class="row">
		<div class="col-md-8 col-sm-7 col-xs-8">
		<div class="form-group">
				<form:label path="product" for="savedListSearch"><span id="assemblyheader">
				<b><spring:theme code="text.savedAssembly.addProduct"/></b><!-- Add Products to Assembly --></b></span></form:label>
				<div class="cl"></div>
				<input type="text" name="serachBox" id="savedListSearch" class="form-control" placeholder='<spring:theme code="assemblyDetailsPage.enter.product" />'/> 
		</div>
		</div>
		<div class="col-md-2 col-sm-3 col-xs-4">
		<div class="form-group">
		<input type="hidden" id="sessionStoreList" value="${sessionStore.address.phone}"/>
		<form:label path="product" for="quantityId"><spring:theme code="assemblyCreatePage.quantity" /></form:label>
				<div class="cl"></div>
				
			<div class="col-md-12 col-sm-12 col-xs-12 create-assembly-qty-container">
			<div class="row">
			<input type="text" name="quantity" id="quantityId" class="form-control rightAlign" maxlength="5" value="1"/> </div>
			</div>
	</div>
	</div>
	<div class="col-md-2 col-sm-2 col-xs-12 marginBottom20"><label>&nbsp;</label><input type="button" value='<spring:theme code="basket.add.to.cart" />' class="btn btn-primary btn-block" id="add_Product_to_savedList"></div>
	</div>
 

 <div class="row">
 	<div class="margin20">
   		<div class="col-md-4 col-sm-6 col-xs-12"><input type="button" value='<spring:theme code="assemblyCreatePage.create.assembly" />' id="createAssembly" class="btn btn-primary btn-block"/></div>
 		 <div class="col-md-3 col-sm-6 col-xs-12"><input type="button" value='<spring:theme code="basket.save.cart.action.cancel" />' class="btn btn-block btn-default" onclick="window.location='/assembly'"/></div>
   </div>
   </div>
</div>
</div>	 
   </form:form>
 
  
