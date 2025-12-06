<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<spring:url value="/cart/uploadcartcsv" var="cartList" htmlEscape="false"/>
<div style="display:none;">
	<div class="cart-import-popup" >
		<div class='shoppingCartListUpload PopupBox hidden-xs'>
			<div class='shoppingListFileupload hidden-xs'>
				<form:form action='${cartList}' method='post' modelAttribute='siteoneCartUploadForm' id='siteoneCartUploadForm' enctype='multipart/form-data'>
					<div class='shopping-cart-list-upload'>
						<h1 class='csvUploadPanelHeadline headline2 marginTop20 text-align-center font-Geogrotesque'>
							<spring:theme code="js.cart.shoppingCartUploadHeadline" />
						</h1>
						<div class='shopping-cart-csvupload-description text-align-center'>
							<spring:theme code="js.cart.shoppingCartUploadDescription" />
						</div>
						<div class='download_list text-align-center marginbottomp30'><a href='/cart/downloadCartCSVFile' target='_blank'><span style='color:#999999' class='glyphicon glyphicon-download-alt'></span>
							</a>&nbsp;
							<a href='/cart/downloadCartCSVFile' target='_blank'>Download Template</a>
						</div>
						<div class='file-browse-holder'>
							<div id='fileBrowseContainer' class='fileBrowseContainer'>
								<div class='file-browse-container flex justify-center'>
									<input type='file' onclick="ACC.adobelinktracking.cartCsvUpload('Choose File','','checkout: cart: upload csv popup');" onchange='ACC.cart.uploadCsvCart(this)' name='csvFile' id='csvFileCart' data-file-max-size='${fn:escapeXml(csvFileMaxSize)}' accept='text/csv' class='btn btn-primary btn-block js-file-upload__input csvFile' value='Select file to upload' style='position:absolute; opacity: -1;visibility: hidden;' />
									<label for='csvFileCart' class='browse-btn p-a-10 f-w-700 f-s-16 text-align-center'>
										<spring:theme code="js.cart.shoppingCartUploadChooseFile" />
									</label>
									<span class='select-filename m-l-10 text-align-left p-l-28'></span>
								</div>
								<div id='fileTypeErrorInfo' class='filetype-error-info marginTop12 text-align-center'>
									<span class='upload-type-fail-label f-w-700'>Upload Failed:</span>
									<span class='upload-type-fail-msg p-l-5'>Incorrect File type</span>
								</div>
							</div>
						</div>
						<div id='fileType' class='filetype-info text-align-center m-t-15'>
							<span style='font-weight: 700; font-size: 18px;'>File Type:</span><span style='font-size: 18px; padding-left: 5px;'>CSV</span>
						</div>
						<div style='display:flex; justify-content: center; margin-top:35px; margin-bottom:5px;'>
							<input id='csvUploadbtn' onclick="ACC.cart.formSubmitCsvCart(this);ACC.adobelinktracking.cartCsvUpload('Upload','','checkout: cart: upload csv popup');" class='btn btn-primary btn-center csv-upload-btn csvUploadbtn' type='button' value='Upload' disabled />
						</div>
						<div style='text-align:center;padding-top:3px;'>
							<input onclick='ACC.colorbox.close();' id='cancelpopup' class='cancelpopup' type='button' style='background-color: transparent; border: none; text-decoration: underline;color:#999999;' value='Cancel' />
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="cart-import-popupsucess" >
		<div class='shoppingCartListUpload PopupBox hidden-xs'>
			<div class='shoppingListFileupload hidden-xs'>
				<form:form action='${cartList}' method='post' modelAttribute='siteoneCartUploadForm' id='siteoneCartUploadForm' enctype='multipart/form-data'>
					<div class='fileuploadsucess'>
						<h1 class='csvUploadPanelHeadline headline2 text-align-center'></h1>
						<div style='display:flex;  justify-content: center; margin-top: 27px;'><span class='select-filename p-l-28'></div>
						<div class="csvUploadInfo">
							<h1 class='csvUploadInfoHeadline headline2 text-align-center'></h1>
							<div class="csvUploadInfoDetails">
							</div>
						</div>
						<div style='text-align:center;'>
							<a onclick='ACC.colorbox.close();' style='width:169px; margin:20px auto;' class='btn btn-block btn-primary'>Go to Cart</a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="cart-import-popupexceeded" >
		<div class='shoppingCartListUpload PopupBox hidden-xs'>
			<div class='shoppingListFileupload hidden-xs'>
				<form:form action='${cartList}' method='post' modelAttribute='siteoneCartUploadForm' id='siteoneCartUploadForm' enctype='multipart/form-data'>
					<div class='file-exceeded-error'">
						<h1 style='color: #E40101;' class='csv-upload-error margin-top-20 text-align-center font-Geogrotesque headline2'>
							Upload Failed
						</h1>
						<h1 class='csv-upload-error csv-upload-error-dec marginTop35 m-l-40 m-r-40  headline2 text-align-center'>
							<spring:theme code="csvfile.limit.exceed.msgcart" />
						</h1>
						<div class='text-align-center'><p class='selectFileUploadCart btn btn-primary marginTop20'>Select File to Upload</p></div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="cart-import-popupuploaderror" >
		<div class='shoppingCartListUpload PopupBox hidden-xs'>
			<div class='shoppingListFileupload hidden-xs'>
				<form:form action='${cartList}' method='post' modelAttribute='siteoneCartUploadForm' id='siteoneCartUploadForm' enctype='multipart/form-data'>
					<div class='file-response-error'>
						<h1 style='color: #E40101;' class='csv-upload-error margin-top-20 text-align-center font-Geogrotesque headline2'>
							<spring:theme code="js.cart.shoppingCartUploadErrorTitle" />
						</h1>
						<h1 class='csv-upload-error csv-upload-error-dec marginTop35 m-l-40 m-r-40  headline2 text-align-center'>
							<spring:theme code="js.cart.shoppingCartUploadErrorDec" />
						</h1>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>