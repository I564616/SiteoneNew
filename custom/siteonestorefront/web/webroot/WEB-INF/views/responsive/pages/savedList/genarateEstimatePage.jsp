<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<template:page pageTitle="${pageTitle}">
<!-- Estimate Page -->
<div class="pad-rgt-10 padding-top-20 bg-white estimate-page">
	<spring:url value="/savedList/listDetails?code=" var="returnListUrl" htmlEscape="false"/>
    <a href="${returnListUrl}${savedListData.code}" class="return-link"><span class="glyphicon glyphicon-arrow-left marginrgt5" aria-hidden="true"></span><spring:theme code="estimate.return" /></a>
    <div class="row flex-center margin40">
        <div class="col-md-8 padding0">
            <span class="heading estimate-title">${savedListData.name}&nbsp;<spring:theme code="estimate.title" /></span>
        </div>
        <div class="col-md-4 text-right">
            <span class="text-muted font-Geogrotesque-bold estimate-total-text"><spring:theme code="estimate.total" /></span>
            <span class="estimate-total heading pad-lft-25">$${savedListData.listTotalPrice.value}</span>
        </div>
    </div>
    <hr>
    <div class="row flex-center justify-center contractor-section">
	    <div class="col-md-3">
	        <button class="btn btn-block bg-white text-default transition-3s upload-logo-btn" style="display:${(not empty customerData.unit.customerLogo.url?'none': 'block')};" onclick="ACC.estimate.popupEffect('show','slide','.logo-popup')">
	        	<div class="glyphicon glyphicon-open bg-lightGrey flex-center br-3 upload-logo-icon" aria-hidden="true"></div>
		        <div class="font-Geogrotesque-bold text-left"><spring:theme code="estimate.yourlogo" /></div>
		        <div class="bold-text text-muted text-left"><spring:theme code="estimate.dimensions" /></div>
		        <div class="text-muted text-left">1200px x 400px (<spring:theme code="estimate.max" />)</div>
	        </button>
	        <button class="btn btn-block bg-white text-default transition-3s contractor-logo-btn" style="display:${(not empty customerData.unit.customerLogo.url?'block': 'none')};" onclick="ACC.estimate.editLogo()">
	        	<div class="glyphicon glyphicon-pencil bg-lightGrey text-default br-3 padding5 transition-3s edit-logo-icon" aria-hidden="true"></div>
		        <img class="margin0 padding0 contractor-logo" src="${customerData.unit.customerLogo.url}" alt="contractor-logo" title="contractor-logo" />
	        </button>
	    </div>
	    <div class="row margin0 bg-lightGrey padding-30 logo-popup">
            <div class="col-md-12 padding0 text-center">
	                <label for="uploadedImage" class="btn btn-block text-default transition-3s upload-logo-btn">
			        	<span class="bg-lightGrey br-3 upload-logo-icon">
			        		<span class="glyphicon glyphicon-open text-muted pad-rgt-10" aria-hidden="true"></span><spring:theme code="estimate.upload" />
			        	</span>
                		<span class="text-red hidden estimate-type-error"><span class="bold-text pad-rgt-10"><spring:theme code="estimate.failed" />:</span><spring:theme code="estimate.typeerror" />.</span>
                		<span class="text-red hidden estimate-size-error"><span class="bold-text pad-rgt-10"><spring:theme code="estimate.failed" />:</span><spring:theme code="estimate.sizeerror" />.</span>
			        </label>
		        <button class="btn btn-block padding0 text-white transition-3s delete-logo-btn" onclick="ACC.estimate.logoPopupMode('delete')">
		        	<img class="margin0 padding0 contractor-logo" src="${customerData.unit.customerLogo.url}" alt="contractor-logo" title="contractor-logo" />
		        	<div class="bg-lightGrey paddingTopB10 br-3"><span class="glyphicon glyphicon-open pad-rgt-10" aria-hidden="true"></span><spring:theme code="estimate.delete" /></div>
		        </button>
		        <p class="text-center text-muted marginTop10"><span class="bold-text pad-rgt-10"><spring:theme code="estimate.dimensions" />:</span>1200px x 400px (<spring:theme code="estimate.max" />)</p>
		        <p class="text-center text-muted"><span class="bold-text pad-rgt-10"><spring:theme code="estimate.filetype" />:</span>JPG, JPEG, PNG, GIF</p>
            </div>
            <div class="col-md-6 p-l-0">
                <button class="btn btn-default btn-block font-Geogrotesque-bold" onclick="ACC.estimate.popupEffect('hide','drop','.logo-popup')">
                    <spring:theme code="estimate.cancel" />
                </button>
            </div>
            <div class="col-md-6 padding0 estimate-logo-upload" style="display:${(not empty customerData.unit.customerLogo.url?'block': 'none')};">
 				<spring:url value="/savedList/uploadImage" var="generateEstimatePageUrl" htmlEscape="true"/>
	            <form:form id="uploadImage" modelAttribute="SiteoneSavedListLogoUploadForm" method="post" action="${generateEstimatePageUrl}" enctype="multipart/form-data">	            
	            	<input  type="file" accept="image/*" name="uploadedImage" maxSize="1" class="hidden" id="uploadedImage" onchange="ACC.estimate.uploadLogo(this)" />
					<input type="hidden" name="wishListCode" value="${savedListData.code}"/>
	                <button class="btn btn-primary btn-block font-Geogrotesque-bold" type="submit" onclick="ACC.estimate.saveLogo()">
	                    <spring:theme code="estimate.save" />
	                </button>
	    		</form:form>
            </div>
            <div class="col-md-6 padding0 estimate-logo-delete" style="display:${(not empty customerData.unit.customerLogo.url?'none': 'block')};">
	            <button class="btn btn-primary btn-block font-Geogrotesque-bold" onclick="ACC.estimate.saveLogo('delete')">
                    <spring:theme code="estimate.save" />
                </button>
            </div>
	    </div>
	    <div class="col-md-5 transition-3s edit-customer">
	        <div class="row estimate-popup">
	            <div class="col-md-2 estimate-popup-txt">
	                <spring:theme code="estimate.popup.companyname" />
	            </div>
	            <div class="col-md-10">
	                <input type="text" id="cname" name="company name" value="${unit.name}" />
	                <label for="Caddress">
	                    <spring:theme code="estimate.popup.street" />
	                </label>
	            </div>
	            <div class="col-md-2 estimate-popup-txt">
	                <spring:theme code="estimate.popup.companyaddress" />
	            </div>
	            <div class="col-md-10">
	                <input type="text" id="Caddress" name="address" value="${billingAddress.line1}" data-default="${billingAddress.line1}" />
	            </div>
	            <div class="col-md-offset-2 col-md-10">
	                <label for="apartment">
	                    <spring:theme code="estimate.popup.apartment" />
	                </label><br>
	                <input type="text" id="apartment" name="apartment" value="${billingAddress.line2}" data-default="${billingAddress.line2}" />
	            </div>
	            <div class="col-md-offset-2 col-md-5">
	                <label for="estimateCity">
	                    <spring:theme code="estimate.popup.city" />
	                </label><br>
	                <input type="text" id="estimateCity" name="City" value="${billingAddress.town}" data-default="${billingAddress.town}" />
	            </div>
	            <div class="col-md-5">
	                <label for="state">
	                    <spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.popup.state' : 'homeOwnerComponent.province' }" />
	                </label><br>
	                <select name="options" class="form-control" id="estimateState" data-default="${billingAddress.region.isocodeShort}">
	                    <c:forEach items="${states}" varStatus="index">
	                        <c:choose>
	                            <c:when test="${billingAddress.region.isocodeShort == states[index.index].isocodeShort}">
	                                <option value="${index.index}" selected>${states[index.index].isocodeShort}</option>
	                            </c:when>
	                            <c:otherwise>
	                                <option value="${index.index}">${states[index.index].isocodeShort}</option>
	                            </c:otherwise>
	                        </c:choose>
	                    </c:forEach>
	                </select>
	            </div>
	            <div class="col-md-offset-2 col-md-5">
	                <label for="zipcode">
	                    <spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.popup.zipcode' : 'text.postcode.homeowner' }" />
	                </label><br>
	                <input type="text" id="estimateZipcode" name="zipcode" value="${billingAddress.postalCode}" data-default="${billingAddress.postalCode}" />
	            </div>
	            <div class="clearfix"></div>
	            <div class="col-md-2 estimate-popup-txt">
	                <spring:theme code="estimate.popup.phonenumber" />
	            </div>
	            <div class="col-md-10">
	                <input type="text" id="estimatePhone" name="estimatePhone" value="${customerData.contactNumber}" />
	            </div>
	            <div class="col-md-2 estimate-popup-txt">
	                <spring:theme code="estimate.popup.emailaddress" />
	            </div>
	            <div class="col-md-10">
	                <input type="text" id="estimateEmail" name="emailadd" value="${customerData.uid}" />
	            </div>
	            <div class="clearfix"></div>
	            <div class="col-md-offset-2 col-md-5">
	                <button class="btn btn-default btn-block font-Geogrotesque-bold" onclick="ACC.estimate.popupEffect('hide','fold','.estimate-popup')">
	                    <spring:theme code="estimate.cancel" />
	                </button>
	            </div>
	            <div class="col-md-5">
	                <button class="btn btn-primary btn-block font-Geogrotesque-bold" onclick="ACC.estimate.updateCustomerDetails()">
	                    <spring:theme code="estimate.update" />
	                </button>
	            </div>
	        </div>
	        <button class="btn btn-block edit-customer-btn" onclick="ACC.estimate.editCustomerDetails()"><span class="glyphicon glyphicon-pencil text-success" aria-hidden="true"></span></button>
	        <span class="company-name font-Geogrotesque-bold">${unit.name}</span>
	        <div class="row">
	            <div class="col-md-6"><span class="company-address">${billingAddress.line1}<c:if test="${billingAddress.line2}">, ${billingAddress.line2}</c:if></span><br><span class="company-address">${billingAddress.town}, ${billingAddress.region.isocodeShort}&nbsp;${billingAddress.postalCode}</span></div>
	            <div class="col-md-6"><span class="company-phone">${customerData.contactNumber}</span><br><span class="company-email">${customerData.uid}</span></div>
	        </div>
	    </div>
		<div class="col-md-2">
           <div class="form-group margin0">
               <label for="estimateDate" class="control-label">
                   <spring:theme code="estimate.date" />
               </label>
               <input type="text" class="form-control estimate-date" id="estimateDate" placeholder="Estimate Date" disabled="disabled">
           </div>
       </div>
       <div class="col-md-2">
           <div class="form-group margin0">
               <label for="estimateNumber" class="control-label">
                   <spring:theme code="estimate.number" />
               </label>
               <input type="number" class="form-control estimate-number" id="estimateNumber" placeholder="<spring:theme code="estimate.number" />">
           </div>
       </div>
	</div>
    <hr>
    <div class="row p-y-15 br-3 margin40 customer-info">
        <h3 class="col-md-2 text-primary bold-text margin0"><spring:theme code="estimate.information" /></h3>
        <div class="col-md-4">
            <div class="row">
	           <div class="col-md-12 form-group">
			    <label for="customerName" class="control-label bold-text"><spring:theme code="estimate.name" /></label>
			    <input type="text" class="form-control customer-name" id="customerNamePDF" placeholder="<spring:theme code="estimate.name" />">
			  </div>
			  <div class="col-md-12 form-group">
			    <label for="customerEmail" class="control-label bold-text"><spring:theme code="estimate.email" /></label>
			    <input type="email" class="form-control customer-email" id="customerEmailPDF" placeholder="<spring:theme code="estimate.email" />">
			  </div>
			  <div class="col-md-9 form-group">
			    <label for="customerPhone" class="control-label bold-text"><spring:theme code="estimate.phone" /></label>
			    <input type="number" class="form-control customer-phone" id="customerPhonePDF" placeholder="<spring:theme code="estimate.phone" />">
			  </div>
            </div>
        </div>
        <div class="col-md-6">
         <div class="row m-r-0">
	           <div class="col-md-12 form-group">
			    <label for="customerAddressLine1" class="control-label bold-text"><spring:theme code="estimate.address.line1" /></label>
			    <input type="text" class="form-control customer-address-line1" id="customerAddressLine1PDF" placeholder="<spring:theme code="estimate.address.line1" />">
			  </div>
			  <div class="col-md-12 form-group">
			    <label for="customerAddressLine2" class="control-label bold-text"><spring:theme code="estimate.address.line2" /></label>
			    <input type="text" class="form-control customer-address-line2" id="customerAddressLine2PDF" placeholder="<spring:theme code="estimate.address.line2" />">
			  </div>
			  <div class="col-md-4 form-group">
			    <label for="customerCity" class="control-label bold-text"><spring:theme code="estimate.city" /></label>
			    <input type="text" class="form-control customer-city" id="customerCityPDF" placeholder="<spring:theme code="estimate.city" />">
			  </div>
			  <div class="col-md-4 form-group">
			    <label for="customerState" class="control-label bold-text"><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.state' : 'homeOwnerComponent.province' }" /></label>
			    <select name="options" class="form-control br-3 customer-state" id="customerStatePDF">
			    	<option data-translation="15" value="<spring:theme code="estimate.customer.state" />" selected disabled><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.customer.state' : 'estimate.customer.province' }" /></option>
                    <c:forEach items="${states}" varStatus="index">
                        <option value="${index.index}" >${states[index.index].isocodeShort}</option>
                    </c:forEach>
                </select>
			  </div>
			  <div class="col-md-4 form-group">
			    <label for="customerZip" class="control-label bold-text"><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.zip' : 'text.postcode.homeowner' }" /></label>
			    <input type="text" class="form-control customer-zip" id="customerZipPDF" placeholder="<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'estimate.zip' : 'text.postcode.homeowner' }" />">
			  </div>
            </div>
        </div>
    </div>
    <div class="row margin0 p-y-20 text-center flex-center justify-center bg-gray text-white br-3 bold-text" data-rows="2">
        <div class="col-md-1">#</div>
        <div class="col-md-3 text-left" data-translation="9"><spring:theme code="estimate.table.product" /></div>
        <div class="col-md-2 text-left"><spring:theme code="estimate.category" /></div>
        <div class="col-md-1" data-translation="1"><spring:theme code="estimate.table.sku" /></div>
        <div class="col-md-1" data-translation="2"><spring:theme code="estimate.table.price" /></div>
        <div class="col-md-1" data-translation="3"><spring:theme code="estimate.table.quantity" /></div>
        <div class="col-md-1" data-translation="4"><spring:theme code="estimate.table.markup" /></div>
        <div class="col-md-1 text-right" data-translation="11"><spring:theme code="estimate.table.total" /></div>
        <div class="col-md-1"> </div>
    </div>
    <c:forEach items="${savedListData.entries}" var="entry" varStatus="index">
		<div class="row margin0 br-3 text-center flex-center justify-center estimate-table ${(index.index%2 == 0)? 'bg-white' : 'bg-offwhite'}">
	        <div class="col-md-1 estimate-table-row-num font-Geogrotesque-bold">${index.index+1}</div>
	        <div class="col-md-3 padding0">
	            <input type="text" class="form-control estimate-product" placeholder="<spring:theme code="estimate.enter.product" />" value="${entry.product.name}">
	        </div>
	        <div class="col-md-2">
			    <button onclick="ACC.estimate.customCategoryView(this, '<spring:theme code='estimate.materials' />')" type="button" class="movetoList-text custom-category-text transition-3s" data-categorypopup="hide"><span class="category-text"><spring:theme code="estimate.materials" /></span><span class="badge transition-3s"><svg xmlns="http://www.w3.org/2000/svg" width="8" height="5.167" viewBox="0 0 8 5.167"><path d="M3.527,157.74l-3.4-3.4a.6.6,0,0,1,0-.848l.565-.565a.6.6,0,0,1,.848,0l2.411,2.411,2.411-2.411a.6.6,0,0,1,.848,0l.565.565a.6.6,0,0,1,0,.848l-3.4,3.4A.6.6,0,0,1,3.527,157.74Z" transform="translate(0.05 -152.75)" fill="#50a0c5"></path></svg></span>
			    </button>
			</div>
	        <div class="col-md-1 padding0">
	            <input type="text" class="form-control text-center estimate-sku" placeholder="<spring:theme code="estimate.table.sku" />" value="${entry.product.itemNumber}">
	        </div>
	        <div class="col-md-1 padding0 after-price">
	            <input type="number" min="0" max="9999999999" data-hidePrice="${entry.hidePrice}" class="form-control estimate-table-input estimate-price" placeholder="<spring:theme code="estimate.table.price" />" value="${entry.hidePrice? '0.00' : entry.totalPrice.value/entry.qty}">
	        </div>
	        <div class="col-md-1 padding0">
	              <input type="number" min="1" max="9999999999"  class="form-control text-center estimate-table-input estimate-quantity" placeholder="<spring:theme code="estimate.table.quantity" />" value="${entry.qty}">
	        </div>
	        <div class="col-md-1 padding0 after-prcentage">
	            <input type="number" min="0" max="9999999999"  class="form-control text-right estimate-table-input estimate-markup" placeholder="<spring:theme code="estimate.table.markup" />" value="">
	        </div>
	        <div class="col-md-1 padding0">
	            <input type="text" class="form-control text-right estimate-table-input estimate-table-total" disabled>
	        </div>
	        <div class="col-md-1 padding0"><button class="btn btn-red font-Geogrotesque-bold" onclick="ACC.estimate.removeEstimateRow(this)">x</button></div>
	    </div>
	</c:forEach>
    <button class="btn btn-primary btn-block marginTop20 transition-3s font-Geogrotesque-bold" onclick="ACC.estimate.addEstimateRow(this)">+ <spring:theme code="estimate.additem" /></button>
    <hr>
    <div class="row flex-center margin40">
        <div class="col-md-6 padding0">
            <div class="form-group">
                <label for="addMessage"><spring:theme code="estimate.message" /></label>
                <textarea class="form-control add-message" id="addMessage" placeholder="<spring:theme code="estimate.message" />" rows="3"></textarea>
            </div>
        </div>
        <div class="col-md-6 padding0 text-right">
            <span class="text-muted font-Geogrotesque-bold estimate-total-text" data-translation="13"><spring:theme code="estimate.total" /></span>
            <span class="estimate-total heading pad-lft-25">$${savedListData.listTotalPrice.value}</span>
        </div>
    </div>
    <hr>
	<div class="row ">
	    <div class="col-md-offset-5 col-md-7">
	        <div class="row margin0">
	            <div class="col-md-4 padding0">
	                <label class="radio-inline" for="category_details">
	                    <span class="colored-radio">
	                        <input type="radio" name="estimate-generate-option" value="category_details" id="category_details" onchange="ACC.estimate.bindEstimateGenerationOptions(this)" checked />
	                    </span>
	                    &nbsp;<spring:theme code="estimate.radio.option1" />
	                </label>
	            </div>
	            <div class="col-md-4 text-right">
	                <label class="radio-inline" for="category_only">
	                    <span class="colored-radio">
	                        <input type="radio" name="estimate-generate-option" value="category_only" onchange="ACC.estimate.bindEstimateGenerationOptions(this)" id="category_only" />
	                    </span>
	                    &nbsp;<spring:theme code="estimate.radio.option2" />
	                </label>
	            </div>
	            <div class="col-md-4 padding0 text-right">
	                <label class="radio-inline" for="details_only">
	                    <span class="colored-radio">
	                        <input type="radio" name="estimate-generate-option" value="details_only" onchange="ACC.estimate.bindEstimateGenerationOptions(this)" id="details_only" />
	                    </span>
	                    &nbsp;<spring:theme code="estimate.radio.option3" />
	                </label>
	            </div>
	            <div class="col-md-12 padding0 marginTop20 bold-text estimate-generate-option-txt" id="category_details_txt">
	                <spring:theme code="estimate.radio.option1.info.line1" />
	                <br />
	                <spring:theme code="estimate.radio.option1.info.line2" />
	            </div>
	            <div class="col-md-12 padding0 marginTop20 bold-text estimate-generate-option-txt hidden" id="category_only_txt">
	                <spring:theme code="estimate.radio.option2.info.line1" />
	                <br />
	                <spring:theme code="estimate.radio.option2.info.line2" />
	            </div>
	            <div class="col-md-12 padding0 marginTop20 bold-text estimate-generate-option-txt hidden" id="details_only_txt">
	                <spring:theme code="estimate.radio.option3.info.line1" />
	                <br />
	                <spring:theme code="estimate.radio.option3.info.line2" />
	            </div>
	            <div class="col-md-12 padding0">
	                <div class="text-muted estimate-info">
	                    <spring:theme code="estimate.info" />
	                </div>
	            </div>
	            <div class="col-md-5 padding0 marginTop20">
	                <a href="${returnListUrl}${savedListData.code}" class="btn btn-default btn-block font-Geogrotesque-bold"><spring:theme code="estimate.cancel" /></a>
	            </div>
	            <div class="col-md-7 marginTop20">
	                <button class="btn btn-primary btn-block font-Geogrotesque-bold" onclick="ACC.estimate.estimatePDF('category_details')"><spring:theme code="estimate.generate" /></button>
	            </div>
	        </div>
	    </div>
	</div>
    <!-- Custom Category List -->
	<div class="custom-category col-md-2 p-y-15 bg-white hidden">
	    <div class="movetoList-wrapper m-b-15">
		    <div class="scroll-bar">
		        <div class="list-group margin0 mode-view"><!-- Category List will show here --></div>
		        <div class="form-group m-b-5 mode-edit">
		            <input type="text" class="form-control custom-category-value" data-val="<spring:theme code="estimate.materials"/>" value="<spring:theme code="estimate.materials" />" disabled />
		        </div>
		        <div class="input-group m-b-5 mode-edit">
		            <input type="text" class="form-control custom-category-value" data-val="<spring:theme code="estimate.labor" />" value="<spring:theme code="estimate.labor" />">
		            <span class="input-group-btn br-3">
		                <button class="btn btn-red bold-text" onclick="ACC.estimate.customCategoryRemove(this, '<spring:theme code='estimate.labor' />')" type="button">x</button>
		            </span>
		        </div>
		    </div>
	    </div>
	    <div class="input-group m-b-15 mode-view">
	        <input type="text" class="form-control custom-category-add" placeholder="<spring:theme code="estimate.create.category" />">
	        <span class="input-group-btn">
	            <button class="btn btn-primary" type="button" onclick="ACC.estimate.customCategoryAdd()"><spring:theme code="estimate.ok" /></button>
	        </span>
	    </div>
	    <button class="btn btn-primary btn-block font-Geogrotesque-bold m-b-5 marginTop10 mode-edit" onclick="ACC.estimate.customCategoryUpdate()"><spring:theme code="estimate.update.category" /></button>
	    <button class="btn btn-default btn-block font-Geogrotesque-bold m-b-5 mode-edit" onclick="ACC.estimate.customCategoryMode('view')"><spring:theme code="estimate.cancel" /></button>
	    <button class="movetoList-text btn-block mode-view br-3" onclick="ACC.estimate.customCategoryMode('edit')"><span class="glyphicon glyphicon-pencil pad-rgt-10" aria-hidden="true"></span><spring:theme code="estimate.edit.category" /></button>
	</div> <!-- ./Custom Category List -->
</div>

<!-- PDF Modal -->
<div class="estimate-modal hidden">
  <div class="row">
    <div class="col-sm-12 padding-top-20"><spring:theme code="estimate.success.msg" /></div>
    <div class="col-sm-12 padding-top-20">
      	<button onClick="ACC.colorbox.close()" class="btn btn-default font-Geogrotesque-bold estimate-modal-cancel" ><spring:theme code="estimate.return.estimate" /></button>
      	<a href="${returnListUrl}${savedListData.code}" class="btn btn-primary font-Geogrotesque-bold" ><spring:theme code="estimate.return" /></a>
     </div>
   </div>
 </div> <!-- ./PDF Modal -->
 
 <!-- Info Overlay Mobile -->
 <div class="estimate-mob-overlay hidden-md hidden-lg padding-30 flex-center justify-center text-center">
 <a class="btn text-white estimate-overlay-close" href="${returnListUrl}${savedListData.code}">X</a>
  <div class="row">
    <div class="col-xs-12">
	    <h2><spring:theme code="estimate.overlay.heading" /></h2>
	    <p class="padding-top-20"><spring:theme code="estimate.overlay.msg" /></p>
	    <a href="${returnListUrl}${savedListData.code}" class="btn text-white margin40 estimate-overlay-link" ><spring:theme code="estimate.overlay.close" /></a>
    </div>
   </div>
 </div> <!-- ./Info Overlay Mobile -->
 
 <!-- Translations -->
 <div class="hidden">
	 <span data-translation="0"><spring:theme code="estimate.enter.product" /></span>
	 <span data-translation="5"><spring:theme code="estimate.pdf.date" /></span>
	 <span data-translation="6"><spring:theme code="estimate.title" /> #</span>
	 <span data-translation="7"><spring:theme code="estimate.prepared" /></span>
	 <span data-translation="8"><spring:theme code="estimate.for" /></span>
	 <span data-translation="10"><spring:theme code="estimate.qty" /></span>
	 <span data-translation="12"><spring:theme code="estimate.msg" /></span>
	 <span data-translation="14"><spring:theme code="estimate.subtotal" /></span>
 </div><!-- ./Translations -->

<script src="/_ui/responsive/common/js/jspdf.umd.min.js"></script>
<!-- ./ Estimate Page -->
</template:page>