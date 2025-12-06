<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<%-- JS configuration --%>
	<script type="text/javascript">
		/*<![CDATA[*/
		<%-- Define a javascript variable to hold the content path --%>
		var ACC = { config: {} };
			ACC.config.contextPath = "${contextPath}";
			ACC.config.encodedContextPath = "${encodedContextPath}";
			ACC.config.commonResourcePath = "${commonResourcePath}";
			ACC.config.themeResourcePath = "${themeResourcePath}";
			ACC.config.siteResourcePath = "${siteResourcePath}";
			//<c:url var="siteurl" value="${siteRootUrl}"/>
			ACC.config.rootPath = "${siteRootUrl}";	
			ACC.config.CSRFToken = "${CSRFToken.token}";
			ACC.pwdStrengthVeryWeak = '<spring:theme code="password.strength.veryweak" />';
			ACC.pwdStrengthWeak = '<spring:theme code="password.strength.weak" />';
			ACC.pwdStrengthMedium = '<spring:theme code="password.strength.medium" />';
			ACC.pwdStrengthStrong = '<spring:theme code="password.strength.strong" />';
			ACC.pwdStrengthVeryStrong = '<spring:theme code="password.strength.verystrong" />';
			ACC.pwdStrengthUnsafePwd = '<spring:theme code="password.strength.unsafepwd" />';
			ACC.pwdStrengthTooShortPwd = '<spring:theme code="password.strength.tooshortpwd" />';
			ACC.pwdStrengthMinCharText = '<spring:theme code="password.strength.minchartext"/>';
			ACC.accessibilityLoading = '<spring:theme code="aria.pickupinstore.loading"/>';
			ACC.accessibilityStoresLoaded = '<spring:theme code="aria.pickupinstore.storesloaded"/>';
			ACC.config.googleApiKey="${googleApiKeyForClientSide}";
			ACC.config.googleApiVersion="${googleApiVersion}";
			ACC.config.trackRetailCSPPrice="${trackRetailCSPPricing}";
			ACC.config.storeSpeciality="${storeSpeciality}";
			

//CART
 	ACC.config.failedMsg ='<spring:theme code="js.cart.failedMsg" />';
	ACC.config.shoppingCartUploadHeadline='<spring:theme code="js.cart.shoppingCartUploadHeadline" />';
	ACC.config.shoppingCartUploadDescription='<spring:theme code="js.cart.shoppingCartUploadDescription" />';
	ACC.config.shoppingCartUploadChooseFile='<spring:theme code="js.cart.shoppingCartUploadChooseFile" />';
	ACC.config.shoppingCartUploadErrorTitle='<spring:theme code="js.cart.shoppingCartUploadErrorTitle" />';
	ACC.config.shoppingCartUploadErrorDec='<spring:theme code="js.cart.shoppingCartUploadErrorDec" />';
	ACC.config.shoppingCartUploadSuccess='<spring:theme code="js.cart.shoppingCartUploadSuccess" />';
	ACC.config.shoppingCartUploadSuccessSingle='<spring:theme code="js.cart.shoppingCartUploadSuccessSingle" />';
	ACC.config.shoppingCartUploadTitle='<spring:theme code="js.cart.shoppingCartUploadTitle" />';
 	//ACCOUNT DASHBOARD
	ACC.config.selectShipTo = '<spring:theme code="js.accountdashboard.selectShipTo" />';
ACC.config.orderingAccountError=	'<spring:theme code="js.accountdashboard.orderingAccountError" />';	
ACC.config.orderingAccountSelect	=	'<spring:theme code="js.accountdashboard.orderingAccountSelect" />';
ACC.config.sorryMsg		=		'<spring:theme code="js.accountdashboard.sorryMsg" />';	
ACC.config.emailAddressError =		'<spring:theme code="js.accountdashboard.emailAddressError" />';	
ACC.config.promotionsMsg =			'<spring:theme code="js.accountdashboard.promotionsMsg" />';	
ACC.config.landscapeProfessional=	'<spring:theme code="js.accountdashboard.landscapeProfessional" />';	
ACC.config.emailCommunication =	'<spring:theme code="js.accountdashboard.emailCommunication" />';	
ACC.config.signUp =				'<spring:theme code="js.accountdashboard.signUp" />';	
ACC.config.thankYou =				'<spring:theme code="js.accountdashboard.thankYou" />';	
ACC.config.emailConfirmationMsg	='<spring:theme code="js.accountdashboard.emailConfirmationMsg" />';	
ACC.config.shareViaEmail	=		'<spring:theme code="js.accountdashboard.shareViaEmail" />';	
ACC.config.tellOthers		=		'<spring:theme code="js.accountdashboard.tellOthers" />';	
ACC.config.shareByEmail =				'<spring:theme code="js.accountdashboard.shareByEmail" />';
ACC.config.shareOrderDetailViaEmail	='<spring:theme code="js.accountdashboard.shareOrderDetailViaEmail" />';
ACC.config.enterEmail 		=			'<spring:theme code="js.accountdashboard.enterEmail" />';
ACC.config.updatedPreferences		=	'<spring:theme code="js.accountdashboard.updatedPreferences" />';

ACC.config.accountShipToName		=	'<spring:theme code="js.accountdashboard.accountShipToName" />';
ACC.config.selectedAccount		=	'<spring:theme code="js.accountdashboard.selectedAccount" />';
ACC.config.updateShipTo		=	'<spring:theme code="js.accountdashboard.updateShipTo" />';
ACC.config.searchMore		=	'<spring:theme code="js.accountdashboard.searchMore" />';
ACC.config.loyal		=	'<spring:theme code="js.accountdashboard.loyal" />';
ACC.config.pleaseCheckBox		=	'<spring:theme code="js.accountdashboard.pleaseCheckBox" />'; 

ACC.config.dashboardValidZip		=	'<spring:theme code="js.accountdashboard.dashboardValidZip" />';
ACC.config.dashboardValidEmail		=	'<spring:theme code="js.accountdashboard.dashboardValidEmail" />';
ACC.config.unableDashboardValidate		=	'<spring:theme code="js.accountdashboard.unableDashboardValidate" />';
ACC.config.dashboardTellOthers		=	'<spring:theme code="js.accountdashboard.dashboardTellOthers" />';
ACC.config.selectedHomeBranch	= '<spring:theme code="js.accountdashboard.selectedHomeBranch" />';
ACC.config.makeMyHomeBranch	= '<spring:theme code="js.accountdashboard.makeMyHomeBranch" />';
ACC.config.agronomics	= '<spring:theme code="storeDetailsContent.agronomics" />';
ACC.config.delivery	= '<spring:theme code="storeDetailsContent.delivery" />';
ACC.config.hardscape	= '<spring:theme code="storeDetailsContent.hardscape.tier" />';
ACC.config.irrigation	= '<spring:theme code="storeDetailsContent.irrigation" />';
ACC.config.pestmanagement = '<spring:theme code="storeDetailsContent.pestmanagement" />';
ACC.config.nursery	= '<spring:theme code="storeDetailsContent.nursery" />';
ACC.config.outdoor = '<spring:theme code="storeDetailsContent.outdoor" />';


	//ADDRESS
			ACC.config.failedAddressBook		=	'<spring:theme code="js.address.failedAddressBook" />';	
			ACC.config.failedCart		=		'<spring:theme code="js.address.failedCart" />';
			ACC.config.failedDeliveryAddress		=	'<spring:theme code="js.address.failedDeliveryAddress" />';
			ACC.config.cityMsg		=					'<spring:theme code="js.address.cityMsg" />';
			ACC.config.shipToError		=				'<spring:theme code="js.address.shipToError" />';
			ACC.config.zipCodeError		=				'<spring:theme code="js.address.zipCodeError" />';
			ACC.config.titleError		=				'<spring:theme code="js.address.titleError" />';
			ACC.config.cityError		=				'<spring:theme code="js.address.cityError" />';
			ACC.config.orderingAccountError		=		'<spring:theme code="js.address.orderingAccountError" />';
			ACC.config.originalAddress		=			'<spring:theme code="js.address.originalAddress" />';
			ACC.config.suggestedAddress		=			'<spring:theme code="js.address.suggestedAddress" />';
			ACC.config.veryfyAddressError		=		'<spring:theme code="js.address.veryfyAddressError" />';
			ACC.config.editAddress		=		'<spring:theme code="checkout.multi.deliveryAddress.editAddress" />';
			ACC.config.emailInvalid		=		'<spring:theme code="request.email.invalid" />';
			//autocomplete
				ACC.config.searchItems    = '<spring:theme code="js.autoComplete.searchItems" />';
				ACC.config.suggestedItems	= '<spring:theme code="js.autoComplete.suggestedItems" />';
				ACC.config.searchRelatedArticle ='<spring:theme code="js.autoComplete.searchRelatedArticle" />';
				//BRITEVERIFY
				ACC.config.emailAddressError1=	'<spring:theme code="js.briteverify.emailAddressError1" />'
					ACC.config.emailAddressUnable='<spring:theme code="js.briteverify.emailAddressUnable" />'	
					ACC.config.enterContactName=	'<spring:theme code="js.briteverify.enterContactName" />'
					ACC.config.addressInfo=		'<spring:theme code="js.briteverify.addressInfo" />'
					ACC.config.shippingStates=		'<spring:theme code="js.briteverify.shippingStates" />'
					ACC.config.briteCityMay=			'<spring:theme code="js.briteverify.cityMay" />'
					ACC.config.validZip=	'<spring:theme code="js.briteverify.validZip" />'
					<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
					ACC.config.validZip=	'<spring:theme code="js.briteverify.validPostCode" />'
				    </c:if>
					ACC.config.selectState=		'<spring:theme code="js.briteverify.selectState" />'
					<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
					ACC.config.selectState=		'<spring:theme code="js.briteverify.selectProvince" />'
				    </c:if>
					ACC.config.enterFirstName=		'<spring:theme code="js.briteverify.enterFirstName" />'
					ACC.config.enterLastName=		'<spring:theme code="js.briteverify.enterLastName" />'
						
					ACC.config.enterCompany=		'<spring:theme code="js.briteverify.enterCompany" />'
					ACC.config.enterAccountNumber=	'<spring:theme code="js.briteverify.enterAccountNumber" />'
					ACC.config.phoneNumberError=	'<spring:theme code="js.briteverify.phoneNumberError" />'
				 	ACC.config.validPhoneNumber=	'<spring:theme code="js.briteverify.validPhoneNumber" />'
					ACC.config.enterCity=			'<spring:theme code="js.briteverify.enterCity" />'
					ACC.config.validZipPostal=		'<spring:theme code="js.briteverify.validZipPostal" />'
					ACC.config.purchaseDate=		'<spring:theme code="js.briteverify.purchaseDate" />'
					ACC.config.productDescription=	'<spring:theme code="js.briteverify.productDescription" />'
					ACC.config.serialNumber=		'<spring:theme code="js.briteverify.serialNumber" />'
					ACC.config.invoiceCost=		'<spring:theme code="js.briteverify.invoiceCost" />'
					ACC.config.nameMayInclude=		'<spring:theme code="js.briteverify.nameMayInclude" />'
					ACC.config.chooseCustomerType=	'<spring:theme code="js.briteverify.chooseCustomerType" />'
					ACC.config.confirmationMail=	'<spring:theme code="js.briteverify.confirmationMail" />'
					ACC.config.pleaseTry=	'<spring:theme code="js.briteverify.pleaseTry" />'
					ACC.config.selectReason = '<spring:theme code="js.briteverify.selectReason" />'
						ACC.config.selectRequest='<spring:theme code="js.briteverify.selectRequest" />'
					ACC.config.quickAdd='<spring:theme code="js.quickOrder.add" />'
					ACC.config.quickCart='<spring:theme code="js.quickOrder.addItems" />'
					
				//checkout
 				ACC.config.cancerHarm	=     '<spring:theme code="js.checkout.cancerHarm" />';
				ACC.config.proceedToCheckout	=		'<spring:theme code="js.checkout.proceedToCheckout" />';		
				ACC.config.californiaCustomers	=		'<spring:theme code="js.checkout.californiaCustomers" />';		
				ACC.config.fullMethod	=		'<spring:theme code="js.checkout.fulfillmentMethod" />';		
				ACC.config.selectDeliveryPickup	=	'<spring:theme code="js.checkout.selectDeliveryPickup" />';		
				ACC.config.selectedPastPickupDate	=	'<spring:theme code="js.checkout.selectedPastPickupDate" />';
				ACC.config.selectedPastDeliveryDate	=	'<spring:theme code="js.checkout.selectedPastDeliveryDate" />';
				ACC.config.selectMeridian	=			'<spring:theme code="js.checkout.selectMeridian" />';		
				ACC.config.selectContactPerson	=		'<spring:theme code="js.checkout.selectContactPerson" />';		
				ACC.config.selectDeliveryAddressToContinue =	'<spring:theme code="js.checkout.selectDeliveryAddressToContinue" />';
				ACC.config.messageForBranchAndDeliveryRequired = '<spring:theme code="js.checkout.messageForBranchAndDeliveryRequired" />';
				ACC.config.selectDeliveryAddress	=			'<spring:theme code="js.checkout.selectDeliveryAddress" />';
				ACC.config.termsAndConditions	=				'<spring:theme code="js.checkout.termsAndConditions" />';
				ACC.config.phoneNumberError	=				'<spring:theme code="js.checkout.phoneNumberError" />';
				ACC.config.globalCorrectErrors	=				'<spring:theme code="js.checkout.globalCorrectErrors" />';
				ACC.config.selectAnotherContact	=			'<spring:theme code="js.checkout.selectAnotherContact" />';
				ACC.config.cancel	=					'<spring:theme code="js.checkout.cancel" />';
				ACC.config.proceedToCheckout	=		'<spring:theme code="js.checkout.proceedToCheckout" />';
				ACC.config.remaining	='<spring:theme code="js.checkout.remaining" />';
				ACC.config.characters	='<spring:theme code="js.checkout.characters" />';
				ACC.config.save	='<spring:theme code="js.checkout.save" />';
				ACC.config.checkoutCityMay	='<spring:theme code="js.checkout.checkoutCityMay" />';
				ACC.config.checkoutValidZip	='<spring:theme code="js.checkout.checkoutValidZip" />';
				ACC.config.addressCreated	='<spring:theme code="js.checkout.addressCreated" />';
				ACC.config.unableToAdd	='<spring:theme code="js.checkout.unableToAdd" />';
				ACC.config.ponumbererror	='<spring:theme code="js.checkout.ponumbererror" />';
				ACC.config.iframePopUp ='<spring:theme code="checkout.multi.option.branch.title" />';
				ACC.config.cayanTitle='<spring:theme code="text.payment.error.name.cayan.title"/>';
				ACC.config.cayan='<spring:theme code="text.payment.error.name.cayan"/>';
				//forgottenpassword
					ACC.config.error = '<spring:theme code="js.forgottenpassword.error" />';
					ACC.config.sentEmail ='<spring:theme code="js.forgottenpassword.sentEmail" />';
// 					//formvalidation		
								ACC.config.globalError ='<spring:theme code="js.formvalidation.globalError" />';
								ACC.config.phoneNumberError ='<spring:theme code="js.formvalidation.phoneNumberError" />';		
								ACC.config.accountNumberErrorMsg =	'<spring:theme code="js.formvalidation.accountNumberErrorMsg" />';
								ACC.config.pleaseEnterAccountNumber ='<spring:theme code="js.formvalidation.pleaseEnterAccountNumber" />';
								ACC.config.nameErrorMsg =			'<spring:theme code="js.formvalidation.nameErrorMsg" />';
								ACC.config.cityErrorMsg =			'<spring:theme code="js.formvalidation.cityErrorMsg" />';
								ACC.config.zipCodeError =			'<spring:theme code="js.formvalidation.zipCodeError" />';
								<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
								ACC.config.zipCodeError =			'<spring:theme code="js.formvalidation.postalCodeError" />';
							    </c:if>
								ACC.config.accountNumberProvided =	'<spring:theme code="js.formvalidation.accountNumberProvided" />';
								ACC.config.selectTitle= '<spring:theme code="js.formvalidation.selectTitle" />';
							ACC.config.selectShipTo= '<spring:theme code="js.formvalidation.selectShipTo" />';
							ACC.config.nameMay= '<spring:theme code="js.formvalidation.nameMay" />';
// 						//mystores	
									ACC.config.closestBranch =			'<spring:theme code="js.mystores.closestBranch" />';
										ACC.config.confirmBranch =		'<spring:theme code="js.mystores.confirmBranch" />';	
										ACC.config.noLocationsFound =		'<spring:theme code="js.mystores.noLocationsFound" />';
										ACC.config.noBranchFound =			'<spring:theme code="js.mystores.noBranchFound" />';
									ACC.config.noBranchFound1 =			'<spring:theme code="js.mystores.noBranchFound1" />';
										ACC.config.invalidDetail =			'<spring:theme code="js.mystores.invalidDetail" />';
										ACC.config.unavailable =	'<spring:theme code="js.mystores.unavailable" />';
					//variant Dropdown - Stock Data
					ACC.config.vdropdownInStock = '<spring:theme code="stock.section.in.stock" />';
					ACC.config.vdropdownForShippingOnly = '<spring:theme code="stock.section.for.shipping.only" />';
					ACC.config.vdropdownBackorder = '<spring:theme code="stock.section.backorder" />';
					ACC.config.vdropdownInStockNearby =	'<spring:theme code="stock.section.in.stock2" />';
					ACC.config.vdropdownMoreOnTheWay = '<spring:theme code="stock.section.more.on.the.way" />';
					ACC.config.vdropdownNotAvailable = '<spring:theme code="stock.section.not.available" />';
					//End
					ACC.config.directions =	'<spring:theme code="js.mystores.directions" />';
					ACC.config.closed =	'<spring:theme code="js.mystores.closed" />';
					ACC.config.nearestStore =	'<spring:theme code="js.mystores.nearestStore" />';
					ACC.config.divClass =	'<spring:theme code="js.mystores.divClass" />';
					ACC.config.remove =	'<spring:theme code="js.mystores.remove" />';
					ACC.config.cancel =	'<spring:theme code="js.mystores.cancel" />';
					ACC.config.deleteBranch =	'<spring:theme code="js.mystores.deleteBranch" />';
					ACC.config.changeBranch =	'<spring:theme code="js.mystores.changeBranch" />';
					ACC.config.directions='<spring:theme code="js.storefinder.direction" />';
					//global
					ACC.config.enterLastName=		'<spring:theme code="js.global.enterLastName" />';
				    ACC.config.branchWithin = '<spring:theme code="js.storefinder.branch.found.within" />';
				    ACC.config.branchesWithin = '<spring:theme code="js.storefinder.branches.found.within" />';
				    ACC.config.branchFound= '<spring:theme code="js.storefinder.branch.found" />';
				    ACC.config.branchesFound= '<spring:theme code="js.storefinder.branches.found" />';
				    ACC.config.milesOf= '<spring:theme code="js.storefinder.miles.of" />';
				    <c:if test="${currentBaseStoreId eq 'siteoneCA'}">
				    ACC.config.milesOf= '<spring:theme code="js.storefinder.km.of" />';
				    </c:if>
				    
		            ACC.config.miles =  '<spring:theme code="js.storefinder.miles" />';
		            ACC.config.getDirection=  '<spring:theme code="header.getDirections" />';
		            ACC.config.makeMyBranch = '<spring:theme code="storeDirectoryListPage.make.branch" />';
					ACC.config.storeDetails = '<spring:theme code="headerstoreoverlay.storedetails"/>';
					ACC.config.branchDetails = '<spring:theme code="headerstoreoverlay.branchDetails"/>';
		            ACC.config.removeMyBranch = '<spring:theme code="storeDirectoryListPage.remove.branch" />';
		            ACC.config.selectedBranch = '<spring:theme code="js.storefinder.selected.branch" />';
		            ACC.config.myBranch = '<spring:theme code="js.storefinder.my.branch" />';
		            ACC.config.showBranchSpecialties = '<spring:theme code="js.storefinder.show.branch.specialties" />';
		            ACC.config.hideSpecialties = '<spring:theme code="js.storefinder.hide.specialties" />';
		            ACC.config.savedBranch = '<spring:theme code="js.storefinder.saved.branch" />';
		            ACC.config.getDirectionFrom = '<spring:theme code="js.storefinder.get.direction.from" />';
		            ACC.config.removeBranch = '<spring:theme code="js.storefinder.remove.branch" />';
		            ACC.config.cancelBranch = '<spring:theme code="js.storefinder.cancel.branch" />';
		            ACC.config.assemblyHeader = '<spring:theme code="text.assembly.addheader" />';
		            ACC.config.listHeader = '<spring:theme code="js.savedlist.enter.list.name" />';
		            ACC.config.listselectall = '<spring:theme code="js.savedlist.select.all" />';
		            ACC.config.listdeselectall = '<spring:theme code="js.savedlist.deselect.all" />';
		            ACC.config.savedEnter='<spring:theme code="js.savedlist.enter.name" />';
		            ACC.config.shareAssembly='<spring:theme code="js.savedlist.share.assembly" />'; 
		            ACC.config.selectCountry='<spring:theme code="js.global.select" />';
		            ACC.config.select='<spring:theme code="requestaccount.select" />';
		            ACC.config.firstName='<spring:theme code="js.briteverify.enterFirst" />'; 
		            ACC.config.phoneNumber='<spring:theme code="js.briteverify.phoneNumber" />';
		            ACC.config.plsEnterMsg='<spring:theme code="js.briteverify.pleaseEnterMsg" />';   
		            ACC.config.addToNewList='<spring:theme code="assemblyDetailsPage.create.new.list" />';  
		            ACC.config.addToListCart='<spring:theme code="cartItems.addToList" />';  
		          	//sdsSearch
		            ACC.config.sdsEmptySearch='<spring:theme code="js.sdsSearchResults.emptySearch.msg" />';
		            ACC.config.sdsSearchNoResult='<spring:theme code="js.sdsSearchResults.noResult.msg" />';
		            ACC.config.sdsSearchCantFind='<spring:theme code="js.sdsSearchResults.cantFind.msg" />';
		            ACC.config.sdsSearchPageSDS = '<spring:theme code="js.sdsSearchPage.sds" />';
		            ACC.config.sdsSearchPageLabel = '<spring:theme code="js.sdsSearchPage.label" />';
		            ACC.config.enterCompanyName='<spring:theme code="js.requestAccount.enterCompany.msg" />';
		            ACC.config.forgotPasswordMailSent='<spring:theme code="js.forgottenPwd.sentEmail.msg" />';
		            ACC.config.forgotPassword='<spring:theme code="js.forgottenPwd.forgotPassword.msg" />';
					//missed entires
			    ACC.config.showMore='<spring:theme code="review.show.more" />';
			    ACC.config.showLess='<spring:theme code="review.show.less" />';
			    ACC.config.showAll='<spring:theme code="review.show.all" />';
			    ACC.config.viewMore='<spring:theme code="product.variants.viewMore" />';
			    ACC.config.viewLess='<spring:theme code="product.variants.viewLess" />';
			    ACC.config.quantityExceeded='<spring:theme code="text.product.quantity.exceeded"/>';
			   	ACC.config.enterValidQuantity='<spring:theme code="text.valid.quantity" />';
				ACC.config.adjustQuantityToAddtoCart='<spring:theme code="text.plp.adjust.qty.to.addtocart" />';
				ACC.config.expectDelay='<spring:theme code="text.plp.expect.delay.for.fullorder" />';
				ACC.config.changeUOMForAvailability='<spring:theme code="text.plp.changeuom.for.better.availability" />';
				ACC.config.uomCubicYard='<spring:theme code="pdp.new.uom.cubic.yard" />';
				ACC.config.uomNetTon='<spring:theme code="pdp.new.uom.net.ton" />';
				ACC.config.uomNetTon2000LB='<spring:theme code="pdp.new.uom.net.ton.2000lb" />';
		            ACC.config.productDetail='<spring:theme code="js.productDetail.qty" />';
		            ACC.config.shareCartTell='<spring:theme code="js.shareCart.tell" />';
		            ACC.config.retailPrice='<spring:theme code="text.variantProduct.retailPrice" />';
		            ACC.config.emailyourPrice='<spring:theme code="text.variantProduct.yourPrice" />';
			    ACC.config.include='<spring:theme code="text.include" />';
			    ACC.config.sendTo='<spring:theme code="text.sendto" />';
		            ACC.config.shareByEmail='<spring:theme code="product.share.share" />';
		            ACC.config.contactUsEmail='<spring:theme code="contactUsForm.sendEmail" />';
		            ACC.config.emailMessage='<spring:theme code="js.globalEmail.msg" />';    
		            ACC.config.continueShopping='<spring:theme code="cart.page.continue" />';
		            ACC.config.productsAdded='<spring:theme code="basket.added.to.Basket" />'; 
		            ACC.config.viewFullCart='<spring:theme code="cart.goTocartcheckout" />'; 
		            ACC.config.returnToQuick='<spring:theme code="cart.page.return" />'; 
		            ACC.config.enterNames= '<spring:theme code="assemblyDetails.enterNames" />'; 
		             ACC.config.placeHolderEmail=  '<spring:theme code="forgottenPwd.email" />'; 
		             ACC.config.emailTo= '<spring:theme code="js.email.to" />';
		             ACC.config.emailToPlaceHolder= '<spring:theme code="js.emailToPlaceHolder"/>';
		             ACC.config.selectService= '<spring:theme code="js.briteverify.selectService" />';    
		             ACC.config.specifyReferrals= '<spring:theme code="js.briteverify.specifyReferrals" />'; 
		             ACC.config.allProducts='<spring:theme code="siteOneProductBreadCrumbBuilder.allProducts" />';
		           //language translation error message
		           ACC.config.alreadySignedError1='<spring:theme code="homepage.signedup.errror.msg.part1" />'; 
		           ACC.config.alreadySignedError2='<spring:theme code="homepage.signedup.errror.msg.part2" />'; 
		           ACC.config.contactUs= '<spring:theme code="homePageCustomerEventsComponent.contact" />';
		           ACC.config.shareViaEmail='<spring:theme code="js.shareViaEmail" />';
		           ACC.config.submit='<spring:theme code="homeownercomponent.submit" />';
		           ACC.config.success='<spring:theme code="assemblyLandingPage.success" />';
		           ACC.config.shareList='<spring:theme code="js.savedlist.share.list" />';  
		           ACC.config.pdpMsg='<spring:theme code="​js.productDetails.listMsg" />';
		           ACC.config.partnerprogramnotice='<spring:theme code="js.account.partnerProgramNotice"/>';
		           ACC.config.deliverylocationpop='<spring:theme code="delivery.location.pop"/>';
		           ACC.config.shippinglocationpop='<spring:theme code="shipping.location.pop"/>';
		           ACC.config.orEmailUs='<spring:theme code="js.accountdashboard.emailUs" />';
		           ACC.config.forAssistance='<spring:theme code="js.accountdashboard.assistance"/>';
		           ACC.config.dealerErrorMsg= '<spring:theme code="footer.partnerProgram.dealer.error" />';
		           ACC.config.returnToQuick='<spring:theme code="cart.page.return" />'; 
		           ACC.config.listPrice='<spring:theme code="text.product.siteOnelistprice" />';
		           ACC.config.loginToSeePrice='<spring:theme code="text.product.logInToSeeYourPrice" />';
		           ACC.config.yourPrice='<spring:theme code="text.product.your.price" />';
		            <c:if test="${request.secure}"><c:url value="/search/autocompleteSecure"  var="autocompleteUrl"/></c:if>
					<c:if test="${not request.secure}"><c:url value="/search/autocomplete"  var="autocompleteUrl"/></c:if>
					ACC.autocompleteUrl = '${autocompleteUrl}';

					<c:url value="/login" var="loginUrl"/>
					ACC.config.loginUrl = '${loginUrl}';

					<c:url value="/authentication/status" var="authenticationStatusUrl"/>
					ACC.config.authenticationStatusUrl = '${authenticationStatusUrl}';
					ACC.config.defaultSortValue ='<spring:theme code="myorders.default.sort.value" />';
					<c:forEach var="jsVar" items="${jsVariables}">
						<c:if test="${not empty jsVar.qualifier}" >
							ACC.${jsVar.qualifier} = '${jsVar.value}';
						</c:if>
					</c:forEach>
					
					//Ewallet Changes
					ACC.config.EditCard = '<spring:theme code="text.ewallet.edit.card" />';
					ACC.config.EditCardMessage = '<spring:theme code="test.ewallet.edit.card.message" />';
					ACC.config.manageCard = '<spring:theme code="text.ewallet.manage.card" />';
					ACC.config.ewalletAccessPrivilege = '<spring:theme code="text.ewallet.access.previlege" />';
					ACC.config.ewalletAccessFailure = '<spring:theme code="text.ewallet.access.failure" />';
					ACC.config.eWalletDelete = '<spring:theme code="text.ewallet.delete.card"/>';
					ACC.config.eWalletDeleteMessage = '<spring:theme code="text.ewallet.delete.card.message"/>';
					ACC.config.eWalletAdd = '<spring:theme code="text.ewallet.add.card"/>';
					ACC.config.eWalletAddMessage = '<spring:theme code="text.ewallet.add.success"/>';
					ACC.config.eWalletTechnicalError = '<spring:theme code="text.company.ewallet.failureMessage"/>';
					ACC.config.storeFinderHeadling='<spring:theme code="text.header.storefinder"/>';
					 
					
					
					ACC.config.signinpathingPageName='<spring:theme code="analytics.signIn.pathingPageName"/>';
					ACC.config.myaccountpathingChannel='<spring:theme code="analytics.myaccount.pathingChannel"/>';
					ACC.config.emaillistpathingPageName='<spring:theme code="analytics.emaillist.pathingPageName"/>';
					ACC.config.emaillistsuccesspathingPageName='<spring:theme code="analytics.emaillistsuccess.pathingPageName"/>';
					ACC.config.sharelistpathingPageName='<spring:theme code="analytics.sharelist.pathingPageName"/>';
					ACC.config.sharelistsuccesspathingPageName='<spring:theme code="analytics.sharelistsuccess.pathingPageName"/>';
					ACC.config.emailassemblypathingPageName='<spring:theme code="analytics.emailassembly.pathingPageName"/>';
					ACC.config.emailassemblysuccesspathingPageName='<spring:theme code="analytics.emailassemblysuccess.pathingPageName"/>';
					ACC.config.shareassemblypathingPageName='<spring:theme code="analytics.shareassembly.pathingPageName"/>';
					ACC.config.shareassemblysuccesspathingPageName='<spring:theme code="analytics.shareassemblysuccess.pathingPageName"/>';
					ACC.config.shiptopopuppathingPageName='<spring:theme code="analytics.shiptopopup.pathingPageName"/>';
					ACC.config.unlockuserPageName='<spring:theme code="analytics.unlockuser.pathingPageName"/>';
					ACC.config.unlockuserinstructionPageName='<spring:theme code="analytics.unlockuserinstruction.pathingPageName"/>';
					ACC.config.checkoutpathingChannel='<spring:theme code="analytics.checkout.pathingChannel"/>';
					ACC.config.atcPageName='<spring:theme code="analytics.atc.pathingPageName"/>';
					ACC.config.storepopuppathingChannel='<spring:theme code="analytics.storepop.pathingChannel"/>';
					ACC.config.storepopupPageName='<spring:theme code="analytics.storepop.pathingPageName"/>';
					ACC.config.forgotpswrdpathingPageName='<spring:theme code="analytics.forgotpswrd.pathingPageName"/>';
					ACC.config.managecardpathingPageName='<spring:theme code="analytics.managecard.pathingPageName"/>';
					ACC.config.addnewcardPageName='<spring:theme code="analytics.addnewcard.pathingPageName"/>';
					ACC.config.newcardaddedPageName='<spring:theme code="analytics.newcardadded.pathingPageName"/>';
					ACC.config.deletecardPageName='<spring:theme code="analytics.deletecard.pathingPageName"/>';
					ACC.config.editcardPageName='<spring:theme code="analytics.editcard.pathingPageName"/>';
					ACC.config.cardeditedPageName='<spring:theme code="analytics.cardedited.pathingPageName"/>';
					ACC.config.selectorderaccountPageName= '<spring:theme code="analytics.selectorderaccount.pathingPageName"/>';
					ACC.config.privilegeupdatedpathingPageName='<spring:theme code="analytics.privilegeupdated.pathingPageName"/>';
					ACC.config.paywithcardPageName= '<spring:theme code="analytics.paywithcard.pathingPageName"/>';
					ACC.config.redeempointsPageName= '<spring:theme code="analytics.redeempoints.pathingPageName"/>';
					ACC.config.emailcart= '<spring:theme code="analytics.emailcart.pathingPageName"/>';
					ACC.config.emailcartsent= '<spring:theme code="analytics.emailcartsent.pathingPageName"/>';
					ACC.config.checkoutChangeContact= '<spring:theme code="analytics.checkoutchangecontact.pathingPageName"/>';
					
					ACC.config.addNewLocationPopup= '<spring:theme code="analytics.checkout.addNewLocationPopup"/>';
					ACC.config.addNewLocationPopup2= '<spring:theme code="analytics.checkout.addNewLocationPopup2"/>';
					ACC.config.orderDetails='<spring:theme code="accountDashboardPage.details"/>';
			

					ACC.config.carddeletedPageName= '<spring:theme code="analytics.carddeleted.pathingPageName"/>';
					ACC.config.loginerrorPageName= '<spring:theme code="analytics.loginerror.pathingPageName"/>';
					ACC.config.youTubeQueryParams='?autoplay=1&enablejsapi=1'
					/*Related Article Page*/
					ACC.config.relatedarticleshowTextname='<spring:theme code="text.related.content.show"/>';
					ACC.config.relatedarticlehideTextname='<spring:theme code="text.related.content.hide"/>';
					ACC.config.relatedarticleitemTextname='<spring:theme code="text.related.content.items"/>';
					
					ACC.config.contactUsidentityoption1='<spring:theme code="help.contactUs.identity.option1"/>';
					ACC.config.contactUsidentityoption2='<spring:theme code="help.contactUs.identity.option2"/>';
					
					ACC.config.variantShow='<spring:theme code="variant.text.show"/>'
					ACC.config.variantHide='<spring:theme code="variant.text.show.less"/>'
					ACC.config.variantMoreitems='<spring:theme code="variant.text.more.items"/>'
					ACC.config.variantItems='<spring:theme code="variant.text.items"/>'
					ACC.config.of='<spring:theme code="review.number.of"/>'	
					ACC.config.emails='<spring:theme code="text.emails"/>'
					ACC.config.listMaxEmails=10
					ACC.config.listMinEmails=0
					ACC.config.maxEmail='<spring:theme code="max.email.exceeded"/>'
					ACC.config.shareListToTell='<spring:theme code="text.share.list.to.tell"/>'
					ACC.config.overlayBlockHeading='<spring:theme code="geo.blocked.overlay.heading"/>'
					ACC.config.overlayBlockBody='<spring:theme code="geo.blocked.overlay.body"/>'
					ACC.config.overlayBlockNav='<spring:theme code="geo.blocked.overlay.nav"/>'
					ACC.config.nearbyContinueheading='<spring:theme code="nearby.text.heading"/>';
					ACC.config.nearbyContinueheading='<spring:theme code="nearby.text.heading"/>'
					ACC.config.nearbyItemText='<spring:theme code="nearby.item.text"/>'
					ACC.config.nearbyqtyErrorMsg='<spring:theme code="nearby.qty.errormsg"/>'
					ACC.config.milesAway='<spring:theme code="nearby.qty.milesaway"/>'
					ACC.config.nearbyAtc='<spring:theme code="nearby.qty.atc"/>'
					ACC.config.nearbyAtcAdded='<spring:theme code="nearby.qty.atcAdded"/>'
					ACC.config.nearbyGoTocart='<spring:theme code="nearby.qty.goToCart"/>'
					ACC.config.nearbyQtyText='<spring:theme code="text.account.savedCart.qty"/>'
					// upload list
					ACC.config.csvfailedUploadTitle='<spring:theme code="csvfile.size.upload.failed.title"/>';
					ACC.config.csvfileSizeExceeds='<spring:theme code="csvfile.size.exceed.msg"/>';
					ACC.config.csvfileLimitExceeds='<spring:theme code="csvfile.limit.exceed.msg"/>';
					ACC.config.csvfileEmpty='<spring:theme code="csvfile.file.empty.msg"/>';
					ACC.config.csverrorTitle='<spring:theme code="csvfile.file.errortitle"/>';
					ACC.config.csverrorMsg='<spring:theme code="csvfile.file.errormsg"/>';
					ACC.config.csverrorBtn='<spring:theme code="csvfile.file.errorbtnreport"/>';
					ACC.config.pdpnouommsg='<spring:theme code="pdp.nonuom.message"/>';
					ACC.config.clearAll='<spring:theme code="productGridPage.clear.all"/>';
					ACC.config.csvbtnviewList='View List';
					ACC.config.csvsuccessTitle='Your file upload was successful';
					ACC.config.csvbtnErrorlist='<spring:theme code="csvfile.file.error.btn.erroreport"/>';
					ACC.config.viewlistBtn='<spring:theme code="csvfile.file.btnviewlist"/>';
					ACC.config.listaddedMsg='<spring:theme code="js.productDetails.listMsg"/>';
					ACC.config.nosavedbranches="<spring:theme code='headerstoreoverlay.no.savedbranches'/>";
					ACC.config.listCreatedMsg='<spring:theme code="text.list.created"/>';
					ACC.config.partnerBecome='<spring:theme code="partner.enroll.become"/>';
					ACC.config.partnerGet1='<spring:theme code="partner.enroll.get1"/>';
					ACC.config.partnerCongratulations='<spring:theme code="partner.enroll.congratulations"/>';
					ACC.config.partnerPPMember='<spring:theme code="partner.enroll.PPMember"/>';
					ACC.config.partnerEarningRewards='<spring:theme code="partner.enroll.earningRewards"/>';
					ACC.config.partnerCancel='<spring:theme code="partner.enroll.cancel"/>';
					ACC.config.partnerEnroll='<spring:theme code="partner.enroll.enroll"/>';
					ACC.config.partnerPoints='<spring:theme code="partner.enroll.points"/>';
					ACC.config.partnerHang='<spring:theme code="partner.enroll.hang"/>';
					ACC.config.cartPriceLoaderErrorTitle='<spring:theme code="cart.price.loader.title"/>';
					ACC.config.cartPriceLoaderErrorHeading='<spring:theme code="cart.price.loader.heading"/>';
					ACC.config.cartPriceLoaderErrorMessage='<spring:theme code="cart.price.loader.message"/>';
					ACC.config.cartPriceLoaderErrorRefresh='<spring:theme code="cart.price.loader.refresh"/>';
					ACC.config.cartPriceLoaderErrorContact='<spring:theme code="cart.price.loader.contact"/>';
					ACC.config.branchShopping='<spring:theme code="global.header.branchShopping"/>';
					ACC.config.requestQuotetext='<spring:theme code="request.quote.popup.request.text16"/>';
					ACC.config.requestQuoteerrormesg='<spring:theme code="request.quote.popup.request.text17"/>';
					ACC.config.listDelete='<spring:theme code="savedListLandingPage.delete"/>';
					ACC.config.listCancel='<spring:theme code="savedListLandingPage.cancel"/>';
					ACC.config.listDeleteMsg='<spring:theme code="savedListLandingPage.delete.msg"/>';
					ACC.config.gpApiKey="${globalPaymentApiKey}";
					ACC.config.gpApiEnv="${globalPaymentApiEnv}";
					ACC.config.storeFinderPopupDistanceUnit="mi";
					<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
					ACC.config.storeFinderPopupDistanceUnit="km";
				    </c:if>

				/*]]>*/
			</script>
			<template:javaScriptAddOnsVariables/>			
			<%-- generated variables from commonVariables.properties --%>
			<%-- <script type="text/javascript" src="${sharedResourcePath}/js/generatedVariables.js"></script> --%>