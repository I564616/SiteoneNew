<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<input type="hidden" id="unitId" name="unitId" value="${fn:escapeXml(unitId)}">
<c:set var="quotesNum" value="${quoteData.itemDetails.size()}" />
<c:set var="quoteWriter" value="${not empty quoteData.writer && quoteData.writer ne null && quoteData.writer ne '' ? true : false}" />
<c:set var="hasDeliveryDetails" value="${not empty quoteData.deliveryDetails && quoteData.deliveryDetails ne null? true : false}" />
<c:set var="isFullApproval" value="${quoteData.isFullApproval}" />
<input type="hidden" id="hasDeliveryDetails" value="${hasDeliveryDetails}" />
<input id="quoteShipToAccount" class="hidden" type="radio" name="quoteShipToAccount" value="${quoteData.customerNumber}" checked />
<!-- Quote Details Page -->
<c:choose>
    <c:when test="${quotesFeatureSwitch eq true}">
        <div class="orders-tabs-container f-s-18 f-s-12-xs-pt f-s-12-sm-pt font-Geogrotesque">
            <div class="row container-lg margin0 p-l-0-xs p-b-10-xs p-t-5-xs p-l-0-sm p-b-10-sm p-t-5-sm">
                <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab active-account-tab" data-key="quotepage" href="${homelink}my-account/my-quotes"><spring:theme code="myquotes.quotes"/></a>
                <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="orderhistorypage" href="${homelink}my-account/orders/${sessionShipTo.uid}"><spring:theme code="homepage.myOrder" /></a>
                <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="purchasedproducts" href="${homelink}my-account/buy-again/${sessionShipTo.uid}"><spring:theme code="text.account.buyagain"/></a>
                <c:if test="${InvoicePermission}">
                    <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="invoicespage" data-active="invoicesTab" href="${homelink}my-account/invoices/${sessionShipTo.uid}"><spring:theme code="text.account.invoices"/></a>
                </c:if>
            </div>
            <div class="tab-wrapper-border"></div>
        </div>
        <div class="p-t-80 p-r-0-xs quotedetails-page">
            <div class="row margin0 p-y-50 no-padding-hrz-xs p-t-0-xs p-b-15-xs displayflex flex-center">
                <div class="col-md-6 col-xs-10 f-s-28 f-s-20-xs-px p-l-0-xs text-default font-Geogrotesque"><spring:theme code="myquotes.qdetails"/></div>
                <div class="col-xs-2 hidden-md hidden-lg">
                    <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        <common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5" viewBox="0 0 23 5" display="m-r-15 m-r-10-xs" />
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <button type="button" class="close btn btn-primary" data-dismiss="dropdown" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <li class="hidden"><button class="btn btn-block btn-primary">
                            <common:globalIcon iconName="print" iconFill="none" iconColor="#FFFFFF" width="13" height="13" viewBox="0 0 13 13" display="m-r-15 m-r-10-xs" />
                            <spring:theme code="myquotes.print"/></button></li>
                        <li class="hidden"><button class="btn btn-block btn-primary">
                            <common:globalIcon iconName="email" iconFill="none" iconColor="#FFFFFF" width="14" height="12" viewBox="0 0 14 12" display="m-r-15 m-r-10-xs" />
                            <spring:theme code="myquotes.email"/></button></li>
                        <li class="hidden"><button class="btn btn-block btn-primary">
                            <common:globalIcon iconName="share" iconFill="none" iconColor="#FFFFFF" width="12" height="13" viewBox="0 0 12 13" display="m-r-15 m-r-10-xs" />
                            <spring:theme code="myquotes.share"/></button></li>
                        <li><button class="btn btn-block btn-primary" onclick="ACC.myquotes.quotesDetailsPDF()">
                            <common:globalIcon iconName="download" iconFill="none" iconColor="#FFFFFF" width="12" height="13" viewBox="0 0 12 13" display="m-r-15 m-r-10-xs" />
                            <spring:theme code="myquotes.download"/></button></li>
                        <li class="hidden"><button class="btn btn-block btn-primary">
                            <common:globalIcon iconName="send" iconFill="none" iconColor="#FFFFFF" width="11" height="11" viewBox="0 0 11 11" display="m-r-15 m-r-10-xs" />
                            PS #2352353</button>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4 col-md-offset-2 p-r-0 hidden-xs hidden-sm">
                    <div class="btn-group btn-group-sm pull-right" role="group" aria-label="...">
                        <button type="button" class="btn btn-gp-link hidden" data-toggle="popover" data-content="Print Quote Details">
                            <common:globalIcon iconName="print" iconFill="none" iconColor="#50A0C5" width="13" height="13" viewBox="0 0 13 13" />
                        </button>
                        <button type="button" class="btn btn-gp-link hidden" data-toggle="popover" data-content="Email Quote Details">
                            <common:globalIcon iconName="email" iconFill="none" iconColor="#50A0C5" width="14" height="12" viewBox="0 0 14 12" />
                        </button>
                        <button type="button" class="btn btn-gp-link hidden" data-toggle="popover" data-content="Share Quote Details">
                            <common:globalIcon iconName="share" iconFill="none" iconColor="#50A0C5" width="12" height="13" viewBox="0 0 12 13" />
                        </button>
                        <button type="button" class="btn btn-gp-link" data-toggle="popover" data-content="Download PDF" onclick="ACC.myquotes.quotesDetailsPDF()">
                            <common:globalIcon iconName="download" iconFill="none" iconColor="#50A0C5" width="12" height="13" viewBox="0 0 12 13" />
                        </button>
                    </div>
                </div>
                <div class="col-md-2 p-r-0 hidden hidden-xs hidden-sm">
                    <button type="button" class="btn btn-gp-link btn-block btn-sm bold" data-toggle="popover" data-content="Project Services Link">PS #2352353
                        <common:globalIcon iconName="send" iconFill="none" iconColor="#50A0C5" width="11" height="11" viewBox="0 0 11 11" />
                    </button>
                </div>
            </div>
            <div class="row margin0 bg-lightGrey border-grey text-default f-s-15 hidden-xs hidden-sm">
                <div class="col-md-${hasDeliveryDetails?'4':'6'} padding-30"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.branch"/></span>
                    <p class="m-b-0"><span class="company-address quote-pdf-branch-address1">${quoteData.branchInfo.name}</span></br>
                        <span class="company-address quote-pdf-branch-address2">${quoteData.branchInfo.address.line1}<c:if test="${quoteData.branchInfo.address.line2}">, ${quoteData.branchInfo.address.line2}</c:if></span><br><span class="company-address quote-pdf-branch-address3">${quoteData.branchInfo.address.town}, ${quoteData.branchInfo.address.region.isocodeShort}&nbsp;${quoteData.branchInfo.address.postalCode}</span>
                    </p>
                </div>
                <div class="col-md-${hasDeliveryDetails?'4':'6'} padding-30 b-l-grey"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.to"/></span>
                    <p class="m-b-0"><span class="company-address quote-pdf-to-address1">${not empty quoteData.accountInfo.title && quoteData.accountInfo.title ne null?quoteData.accountInfo.title:'-'}</span></br>
                        <span class="company-address quote-pdf-to-address2">${quoteData.accountInfo.line1}<c:if test="${quoteData.accountInfo.line2}">, ${quoteData.accountInfo.line2}</c:if></span><br><span class="company-address quote-pdf-to-address3">${quoteData.accountInfo.town}, ${quoteData.accountInfo.region.isocodeShort}&nbsp;${quoteData.accountInfo.postalCode}</span>
                    </p>
                </div>
                <c:if test="${hasDeliveryDetails eq true}">
                    <div class="col-md-4 padding-30 b-l-grey"><span class="bold text-gray text-uppercase f-s-10 quote-pdf-shipto-title"><spring:theme code="myquotes.delivery.address"/></span>
                        <p class="m-b-0"><span class="company-address quote-pdf-shipto-address1"></span>
                            <span class="company-address quote-pdf-shipto-address2">${quoteData.deliveryDetails.deliveryStreet}</span><br><span class="company-address quote-pdf-shipto-address3">${quoteData.deliveryDetails.deliveryCity}, ${quoteData.deliveryDetails.deliveryState}&nbsp;${quoteData.deliveryDetails.deliveryZip}</span>
                        </p>
                    </div>
                </c:if>
            </div>
            <div class="row margin0 p-a-15 border-light-grey bg-white hidden-md hidden-lg quote-accordion-open" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'quote-accordion')" data-acconum="1">
                <div class="col-xs-10 p-l-0 text-gray font-Geogrotesque"><spring:theme code="myquotes.branch"/></div>
                <div class="col-xs-2 p-r-0 text-right"> 
                    <span class="glyphicon glyphicon-minus green-title"></span> 
                </div>
                <div class="col-xs-12 padding0 m-t-10 font-size-14 text-default quote-accordion-data-1">
                    ${quoteData.branchInfo.name}</br>
                    <span class="company-address">${quoteData.branchInfo.address.line1}<c:if test="${quoteData.branchInfo.address.line2}">, ${quoteData.branchInfo.address.line2}</c:if></span><br><span class="company-address">${quoteData.branchInfo.address.town}, ${quoteData.branchInfo.address.region.isocodeShort}&nbsp;${quoteData.branchInfo.address.postalCode}</span>
                </div>
            </div>
            <div class="row margin0 p-a-15 border-light-grey b-t-0 bg-white hidden-md hidden-lg" onclick="ACC.global.openCloseAccordion(this,'close', 2, 'quote-accordion')" data-acconum="2">
                <div class="col-xs-10 p-l-0 text-gray font-Geogrotesque"><spring:theme code="myquotes.to"/></div>
                <div class="col-xs-2 p-r-0 text-right"> 
                    <span class="glyphicon glyphicon-plus green-title"></span> 
                </div>
                <div class="col-xs-12 padding0 m-t-10 font-size-14 text-default quote-accordion-data-2" style="display:none;">
                    ${not empty quoteData.accountInfo.title && quoteData.accountInfo.title ne null?quoteData.accountInfo.title:'-'}</br>
                    <span class="company-address">${quoteData.accountInfo.line1}<c:if test="${quoteData.accountInfo.line2}">, ${quoteData.accountInfo.line2}</c:if></span><br><span class="company-address">${quoteData.accountInfo.town}, ${quoteData.accountInfo.region.isocodeShort}&nbsp;${quoteData.accountInfo.postalCode}</span>
                </div>
            </div>
            <c:if test="${hasDeliveryDetails eq true}">
                <div class="row margin0 p-a-15 border-light-grey b-t-0 bg-white hidden-md hidden-lg" onclick="ACC.global.openCloseAccordion(this,'close', 3, 'quote-accordion')" data-acconum="3">
                    <div class="col-xs-10 p-l-0 text-gray font-Geogrotesque"><spring:theme code="myquotes.delivery.address"/></div>
                    <div class="col-xs-2 p-r-0 text-right"> 
                        <span class="glyphicon glyphicon-plus green-title"></span> 
                    </div>
                    <div class="col-xs-12 padding0 m-t-10 font-size-14 text-default quote-accordion-data-3" style="display:none;">
                        <span class="company-address">${quoteData.deliveryDetails.deliveryStreet}</span><br><span class="company-address">${quoteData.deliveryDetails.deliveryCity}, ${quoteData.deliveryDetails.deliveryState}&nbsp;${quoteData.deliveryDetails.deliveryZip}</span>
                    </div>
                </div>
            </c:if>
            <div class="row margin0 no-margin-xs bg-white border-grey b-t-0 text-default f-s-15 font-14-xs quote-details-data" data-remainingBidTotal="${quoteData.remainingBidTotal}" data-quoteId="${quoteData.quoteId}" data-writer="${quoteData.writer}" data-quoteNumber="${quoteData.quoteNumber}" data-jobName="${quoteData.jobName}" data-accountManager="${quoteData.accountManager}" data-dateSubmitted="${quoteData.dateSubmitted}" data-expDate="${quoteData.expDate}" data-status="${quoteData.status}" data-bidTotal="${quoteData.bidTotal}" data-accountManagerEmail="${quoteData.accountManagerEmail}" data-branchManagerEmail="${quoteData.branchManagerEmail}" data-writerEmail="${quoteData.writerEmail}" data-pricerEmail="${quoteData.pricerEmail}" data-storeid="${quoteData.branchInfo.storeId}" data-customernumber="${quoteData.customerNumber}" data-qwriter="${quoteWriter}" data-isFullApproval="${quoteData.isFullApproval}">
                <img src="/_ui/responsive/theme-lambda/images/s-logo.png" class="quote-pdf-logo hidden" alt="quote-pdf-logo" />
                <img src="/_ui/responsive/theme-lambda/images/checkmark.png" class="quote-pdf-check hidden" alt="quote-pdf-check" />
                <div class="col-md-2 col-md-${quoteWriter ne false? '10' : '12'}pe col-xs-6 padding-20 p-15-xs b-b-grey-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.number"/></span>
                    <p class="m-b-0 l-h-20 quote-number">${not empty quoteData.quoteNumber && quoteData.quoteNumber ne null ?quoteData.quoteNumber:'-'}</p>
                </div>
                <div class="${quoteWriter ne false ? 'col-md-2' : 'col-md-4 col-md-20pe'} col-xs-6 padding-20 p-15-xs b-l-grey b-b-grey-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.po.job"/></span>
                    <p class="m-b-0 l-h-20 quote-jobname">${not empty quoteData.jobName && quoteData.jobName ne null ?quoteData.jobName:'-'}</p>
                </div>
                <div class="${quoteWriter ne false ? 'col-md-2' : 'col-md-4 col-md-20pe'} col-xs-12 padding-20 p-15-xs b-l-grey b-b-grey-xs b-l-0-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.acc.manager"/></span>
                    <p class="m-b-0 l-h-20 quote-manager">${not empty quoteData.accountManager && quoteData.accountManager ne null ?quoteData.accountManager:'SiteOne Sales'}</p>
                </div>
                <div class="col-md-2 col-xs-12 ${quoteWriter ne false ? '' : 'hidden'} padding-20 p-15-xs b-l-grey b-b-grey-xs b-l-0-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.writer"/></span>
                    <p class="m-b-0 l-h-20 quote-writer">${quoteWriter ne false ? quoteData.writer : ''}</p>
                </div>
                <div class="col-md-2 col-md-${quoteWriter ne false ? '10' : '12'}pe col-xs-6 padding-20 p-15-xs b-l-grey b-b-grey-xs b-l-0-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.submitted"/></span>
                    <p class="m-b-0 l-h-20 quote-submitted-date">${not empty quoteData.lastModfDate && quoteData.lastModfDate ne null ?quoteData.lastModfDate:'-'}</p>
                </div>
                <div class="col-md-2 col-md-${quoteWriter ne false ? '10' : '12'}pe col-xs-6 padding-20 p-15-xs b-l-grey b-b-grey-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.exp.date"/></span>
                    <p class="m-b-0 l-h-20 quote-expiration-date">${not empty quoteData.expDate && quoteData.expDate ne null ?quoteData.expDate:'07/19/2024'}</p>
                </div>
                <div class="col-md-2 col-md-${quoteWriter ne false ? '10' : '12'}pe col-xs-6 padding-20 p-15-xs b-l-grey b-l-0-xs quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.total"/></span>
                    <p class="m-b-0 l-h-20 bold quotePrice quote-detailstotal">$<fmt:formatNumber value="${not empty quoteData.bidTotal && quoteData.bidTotal ne null?quoteData.bidTotal:'0'}" minFractionDigits="2" maxFractionDigits="2" /></p>
                </div>
                <div class="col-md-2 col-md-${quoteWriter ne false ? '10' : '12'}pe col-xs-6 b-l-grey p-15-xs padding-20 quote-height-adjust"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="myquotes.remaining"/></span>
                    <p class="m-b-0 l-h-20 bold quote-remainingtotal">$<fmt:formatNumber value="${not empty quoteData.remainingBidTotal && quoteData.remainingBidTotal ne null?quoteData.remainingBidTotal:'0'}" minFractionDigits="2" maxFractionDigits="2" /></p>
                </div>
            </div>
            <div class="bg-danger border-grey border-radius-3 border-color-darkred flex-center justify-center flex-dir-column-xs flex-dir-column-sm bold font-14-xs m-t-10-xs margin-top-20 p-x-20-xs p-y-15-xs p-y-25 text-align-center text-dark-red hidden quote-expiration-date-show"><common:globalIcon iconName="bell" iconFill="none" iconColor="#BC0000" width="22" height="25" viewBox="0 0 22 25" display="m-r-15 m-r-0-xs m-r-0-sm"/><span class="display-block-xs display-block-sm m-t-10-xs m-t-10-sm"><spring:theme code="quotes.expired"/></span><button onclick="ACC.myquotes.updateExpiredQuote()" class="btn btn-default m-l-20 m-l-0-xs m-l-0-sm m-t-20-xs m-t-20-sm"><spring:theme code='myquotes.expired.request' /></button></div>
            <section class="quote-stickey-header ${quotesNum eq 0? 'hidden' : ''}">
                <div>
                    <div class="row margin0 p-t-20 padding-bottom-15 no-margin-xs m-b-10-xs no-padding-xs">
                        <div class="col-xs-6 p-l-0 p-r-5 hidden-md hidden-lg quote-expiration-date-hide">
                            <button class="btn btn-border bg-white m-t-15 transition-3s text-default bold-text flex-center justify-center f-s-12-imp quote-edit-start${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'request')"><span class="glyphicon glyphicon-edit text-green f-s-16 m-r-10"></span> <span class="text-default">Modify Quantity</span></button>
                            <button class="btn btn-border bg-white btn-block transition-3s text-default bold-text flex-center justify-center f-s-12-imp quote-edit-request${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'cancel')" style="display: none;"><span class="glyphicon glyphicon-edit text-green f-s-16 m-r-10"></span> <span class="text-default">Request Quantity Edits</span></button>
                            <button class="btn btn-border bg-white btn-block transition-3s text-default bold-text flex-center justify-center f-s-12-imp quote-edit-cancel${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'start')" style="display: none;"><span class="glyphicon glyphicon-edit text-green f-s-16 m-r-10"></span> <span class="text-default p-t-3">Cancel Edits</span></button>
                        </div>
                        <div class="col-xs-${isFullApproval ? '12' : '6'} p-r-0 p-l-5 m-t-5 hidden-md hidden-lg">  
                            <button class="btn btn-border bg-white btn-block transition-3s text-default bold-text flex-center justify-center f-s-12-imp" onclick="ACC.myquotes.messageQuote(this, '<spring:theme code="myquotes.contact.heading1"/>&nbsp;~~&nbsp;<spring:theme code="myquotes.contact.heading2"/> #${quoteData.quoteNumber}.','<spring:theme code="myquotes.contact.message"/>','<spring:theme code="myquotes.contact.button"/>','~~','${quoteData.quoteNumber}')">
                                <common:globalIcon iconName="chat" iconFill="none" iconColor="#78A22F" width="20" height="20" viewBox="0 0 22 22" display="m-r-10-xs"/>
                                <spring:theme code="myquotes.contact"/>&nbsp;Seller
                            </button>
                        </div>
                        <div class="col-md-4 p-l-0 no-padding-xs hidden-xs hidden-sm">
                            <div class="f-s-22 f-w-600 font-Geogrotesque text-default js-edit-quote-note hidden">Update editable quantity(s) below.</div>
                            <div class="btn-group quote-expiration-date-hide ${quotesNum < 2 or isFullApproval? ' hidden' : ''}" role="group" aria-label="...">
                                <button type="button" class="btn btn-green-link bold-text p-l-0" onclick="ACC.myquotes.itemsSelections('select-all', false)"><spring:theme code="myquotes.select.all"/></button>
                                <button type="button" class="btn btn-green-link bold-text">|</button>
                                <button type="button" class="btn btn-green-link bold-text" onclick="ACC.myquotes.itemsSelections('deselect-all', false)"><spring:theme code="myquotes.deselect.all"/></button>
                            </div>
                        </div>
                        <div class="col-md-8 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-md-offset-4 col-xs-12 col-xs-offset-0 no-padding-xs p-r-0 quote-expiration-date-hide">
                                    <button class="bg-white bold-text btn width-100-pe btn-border transition-3s quote-message quote-edit-start hidden-xs hidden-sm${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'request')"><span class="text-green">Modify Quantity</span></button>
                                    <button class="bg-white bold-text btn width-100-pe btn-border transition-3s quote-message quote-edit-request hidden-xs hidden-sm${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'cancel')" style="display: none;"><span class="text-green">Request Quantity Edits</span></button>
                                    <button class="bg-white bold-text btn width-100-pe btn-border transition-3s quote-message quote-edit-cancel hidden-xs hidden-sm${isFullApproval ? ' hidden' : ''}" onclick="ACC.myquotes.editQuote(this, 'start')" style="display: none;"><span class="text-green">Cancel Edits</span></button>
                                </div>
                                <div class="col-md-4 col-xs-12 p-r-0 no-padding-xs hidden quotes-all-item-hide quote-expiration-date-hide">
                                    <button class="btn btn-primary btn-block bold-text transition-3s approve-quote-btn" onclick="ACC.myquotes.approveQuote(this, '', '<spring:theme code="myquotes.approve.heading1"/> ', ' <spring:theme code="myquotes.approve.heading2"/>&nbsp;~~&nbsp;<spring:theme code="myquotes.approve.heading3"/> #${quoteData.quoteNumber}.', '<spring:theme code="myquotes.approve.message"/>', '<spring:theme code="myquotes.approve.button"/>','${quoteData.quoteNumber}', '<spring:theme code="myquotes.approve.requested"/>', '<spring:theme code="myquotes.approve.optional"/>','<spring:theme code="text.quote.overlay.optionnote"/>','<spring:theme code="text.quote.overlay.moboptionnote"/>')"><spring:theme code="myquotes.approve.items"/>&nbsp;<span class="qutoe-choose-items-num"></span>&nbsp;<spring:theme code="myquotes.for.order"/></button>
                                    <p class="m-t-10 margin0 text-center font-size-14 text-italic js-edit-quote-note hidden ${quotesNum < 2 or isFullApproval?' hidden':''}">With Quantity Edits</p>
                                </div>
                                <div class="col-md-4 col-xs-12 p-r-0 no-padding-xs quotes-all-item-show quote-expiration-date-hide">
                                    <button class="btn btn-primary btn-block bold-text transition-3s" onclick="ACC.myquotes.approveQuote(this, 'all', '<spring:theme code="myquotes.approve.heading1"/> ', ' <spring:theme code="myquotes.approve.heading4"/> #${quoteData.quoteNumber}&nbsp;<spring:theme code="myquotes.approve.heading5"/>&nbsp;~~.', '<spring:theme code="myquotes.approve.message"/>', '<spring:theme code="myquotes.approve.button"/>','${quoteData.quoteNumber}', '<spring:theme code="myquotes.approve.requested"/>', '<spring:theme code="myquotes.approve.optional"/>','<spring:theme code="text.quote.overlay.optionnote"/>','<spring:theme code="text.quote.overlay.moboptionnote"/>')"><spring:theme code="myquotes.approve.quote"/></button>
                                    <p class="m-t-10 margin0 text-center font-size-14 text-italic ${quotesNum < 2 or isFullApproval?' hidden':''}"><spring:theme code="myquotes.approve.note"/></p>
                                </div>
                                <div class="col-md-1 p-r-0 pull-right text-align-right hidden-xs hidden-sm">
                                    <button class="btn btn-border bg-white flex-center transition-3s text-default quote-message" onclick="ACC.myquotes.messageQuote(this, '<spring:theme code="myquotes.contact.heading1"/>&nbsp;~~&nbsp;<spring:theme code="myquotes.contact.heading2"/> #${quoteData.quoteNumber}.','<spring:theme code="myquotes.contact.message"/>','<spring:theme code="myquotes.contact.button"/>','~~','${quoteData.quoteNumber}')" data-toggle="popover" data-content="<spring:theme code="myquotes.contact.heading1"/>&nbsp;${not empty quoteData.accountManager && quoteData.accountManager ne null ?quoteData.accountManager:'SiteOne Sales'}">
                                        <common:globalIcon iconName="chat" iconFill="none" iconColor="#78A22F" width="20" height="20" viewBox="0 0 22 22" />
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 p-l-0 no-padding-xs text-center hidden-md hidden-lg quote-expiration-date-hide ${quotesNum < 2 or isFullApproval?' hidden':''}">
                            <div class="btn-group" role="group" aria-label="...">
                                <button type="button" class="btn btn-green-link bold-text" onclick="ACC.myquotes.itemsSelections('select-all', false)"><spring:theme code="myquotes.select.all"/></button>
                                <button type="button" class="btn btn-green-link bold-text">|</button>
                                <button type="button" class="btn btn-green-link bold-text" onclick="ACC.myquotes.itemsSelections('deselect-all', false)"><spring:theme code="myquotes.deselect.all"/></button>
                            </div>
                        </div>
                    </div>
                    <div class="row margin0 p-l-15 p-r-15 p-y-20 bg-off-grey add-border-radius br-br-0 br-bl-0 text-white bold-text f-s-12 text-uppercase flex-center justify-center show-xs hidden-xs hidden-sm">
                        <div class="col-md-1 col-md-6pe text-left quote-expiration-date-hide${quotesNum < 2 or isFullApproval?' hidden':''}"><spring:theme code="myquotes.select"/></div>
                        <div class="col-md-3 col-md-11pe col-md-selecion"><spring:theme code="myquotes.item"/> #</div>
                        <div class="col-md-5 ${quotesNum < 2 or isFullApproval?'':'col-md-33pe'}"><spring:theme code="myquotes.description"/></div>
                        <div class="col-md-1 col-md-10pe text-center"><spring:theme code="myquotes.qty"/></div>
                        <div class="col-md-2 col-md-11pe text-right"><spring:theme code="myquotes.unit.price"/></div>
                        <div class="col-md-1 col-md-6pe text-right"><spring:theme code="myquotes.uom"/></div>
                        <div class="col-md-2 col-md-11pe text-right"><spring:theme code="myquotes.ext.price"/></div>
                        <div class="col-md-1 col-md-12pe padding0 text-center"><spring:theme code="myquotes.approval"/></div>
                    </div>
                </div>
            </section>
            <section class="flex flex-dir-column">
            <c:forEach items="${quoteData.itemDetails}" var="entry" varStatus="index">
                <c:set var="quotesST" value="false" />
                <c:set var="quotesComment" value="false" />
                <c:if test="${index.index+1 == 9999}">
                <div class="row margin0 padding-20 bg-lightGrey bold f-s-16 text-center" var="i" begin="1" end="15">
                    <div class="col-md-12 col-xs-12">Title of Category</div>
                </div>
                </c:if>
                <c:if test="${not empty entry.itemNumber && entry.itemNumber ne null && entry.itemNumber eq 'ST'}">
                    <c:set var="quotesST" value="true" />
                </c:if>
                <c:if test="${not empty entry.itemNumber && entry.itemNumber ne null && entry.itemNumber eq 'C'}">
                    <c:set var="quotesComment" value="true" />
                </c:if>
                <div class="row margin0 p-l-15 p-r-15 p-y-15 border-light-grey ${quotesST || quotesComment?'bg-lightGrey':'bg-white'} quote-table-entry${index.index>999?' hidden':''}" data-index="${index.index}" data-itemNumber="${entry.itemNumber}" data-itemDescription="${entry.itemDescription}" data-quantity="${entry.quantity}" data-unitPrice="${entry.unitPrice}" data-UOM="${entry.UOM}" data-extPrice="${entry.extPrice}" data-notes="${entry.notes}" data-approved="${false}" data-st="${quotesST}" data-comment="${quotesComment}" data-quoteDetailId="${entry.quoteDetailID}" data-lineNumber="${entry.lineNumber}" style="order:${entry.lineNumber}">
                    <div class="col-xs-1 padding0 hidden-md hidden-lg quote-expiration-date-hide${quotesNum < 2 or isFullApproval || quotesST?' hidden':''}" data-opacity="${quotesComment?'no':''}">
                        <button class="btn btn-link p-l-0 p-t-0-xs transition-3s"><input class="form-control green-check ${quotesST || quotesComment?'':'qutoe-choose-items'} mob-item" type="checkbox" onchange="ACC.myquotes.itemSelect(${index.index+1})" /></button>
                    </div>
                    <div class="col-md-12 col-xs-11 padding0 col-md-selecion">
                        <div class="row margin0 pad-xs-lft-10 flex-center justify-center show-xs quote-table b-t-0 text-default f-s-15 font-small-xs col-md-selecion">
                            <div class="col-md-1 col-md-6pe p-l-0 hidden-xs hidden-sm text-center quote-expiration-date-hide${quotesNum < 2 or isFullApproval?' hidden':''}" data-opacity="${quotesST || quotesComment?'no':''}"><button class="btn btn-link p-l-0 p-t-0-xs transition-3s"><input class="form-control green-check ${quotesST || quotesComment?'':'qutoe-choose-items'} web-item" type="checkbox" onchange="ACC.myquotes.itemSelect(${index.index+1})" /></button></div>
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.item"/> #:</div>
                            <div class="col-md-3 col-md-11pe col-xs-8 p-r-0-xs font-small-xs bold-text text-default col-md-selecion p-l-0-xs p-l-0-sm ${quotesComment?' p-l-0-xs':''}">
                                <button class="btn-link p-l-0 no-padding-xs font-small-xs transition-3s bold-text text-default text-left quoteNum" <c:if test="${quotesST == false and quotesComment == false }"> onclick="location.href='/p/${not empty entry.skuId && entry.skuId ne null?entry.skuId:00}'"</c:if>>
                                    <c:choose>
                                        <c:when test="${quotesComment eq true}"><spring:theme code="myquotes.comment"/></c:when>
                                        <c:otherwise>${not empty entry.itemNumber && entry.itemNumber ne null ?entry.itemNumber:'-'}</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.desc"/>:</div>
                            <div class="${quotesComment? 'col-md-10 col-xs-12 p-l-0-xs' : quotesNum < 2 or isFullApproval? 'col-md-5 col-xs-8' : 'col-md-4 col-md-33pe col-xs-8'} p-r-0-xs p-l-0-xs p-l-0-sm">
                                <span class="quote-itemdescription">${not empty entry.itemDescription && entry.itemDescription ne null ?entry.itemDescription:'-'}</span>
                                <c:if test="${not empty entry.notes && entry.notes ne null}">
                                    <p class="f-s-13 m-t-10 text-italic hidden-sm hidden-xs"><span class="bold-text m-r-10 text-green"><spring:theme code="myquotes.notes"/>:</span>${entry.notes}</p>
                                </c:if>
                            </div>
                            <c:if test="${not empty entry.notes && entry.notes ne null}">
                                <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-green text-italic bold-text f-s-11-xs-px m-t-5-xs m-b-5-xs text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.notes"/>:</div>
                                <div class="col-md-1 col-xs-8 p-l-0-xs p-l-0-sm hidden-md hidden-lg text-center text-italic text-dark-gray m-t-5-xs m-b-5-xs xs-left${quotesComment?' hidden':''}" data-opacity="${quotesST?'no':''}">
                                    <span class="quote-notes">${entry.notes}</span>
                                </div>
                            </c:if>
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 p-t-10 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.qty"/>:</div>
                            <div class="col-md-1 col-md-10pe col-xs-8 padding0 p-r-0-xs p-l-0-xs p-l-0-sm text-center quote-quantity flex-center-xs flex-center-sm xs-left${quotesComment?' hidden':''}" data-opacity="${quotesST?'no':''}">
                                <div class="input-group width-100-pe width-60-pe-xs width-60-pe-sm m-b-0-xs m-b-0-sm">
                                    <span class="input-group-btn hidden">
                                        <button class="bg-white bold-text btn btn-small b-r-l-3-imp transition-3s" onclick="ACC.savedlist.quantityHandler('minus','#js-add-entry-quantity-input-${index.index+1}');ACC.myquotes.editQuoteQtyCheck(this);"><span class="text-green">-</span></button>
                                    </span>
                                    <input type="number" min="1" max="999999999" maxlength="9" class="text-center text-left-xs text-left-sm bg-white border-none width-100-px js-number-input-event-bind" id="js-add-entry-quantity-input-${index.index+1}" value='${entry.quantity}' data-default='<fmt:formatNumber value="${entry.quantity}" type="number" groupingUsed="false" />' disabled oninput="ACC.myquotes.editQuoteQtyCheck(this)">
                                    <span class="input-group-btn hidden">
                                        <button class="bg-white bold-text btn btn-small b-r-r-3-imp transition-3s" onclick="ACC.savedlist.quantityHandler('plus','#js-add-entry-quantity-input-${index.index+1}');ACC.myquotes.editQuoteQtyCheck(this);"><span class="text-green">+</span></button>
                                    </span>
                                </div>
                                <div class="f-s-14 f-s-12-xs-px f-s-12-sm-px js-default-entry-quantity p-l-5-xs p-l-5-sm width-45-pe-xs width-45-pe-sm hidden">Orig. Quoted: <span class="text-orange"><fmt:formatNumber value="${entry.quantity}" type="number" groupingUsed="false" /></span></div>
                            </div>
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.unit.price"/>:</div>
                            <div class="col-md-2 col-md-11pe col-xs-8 p-r-0-xs p-l-0-xs p-l-0-sm text-right quote-unit-price xs-left${quotesComment?' hidden':''}" data-opacity="${quotesST?'no':''}">$<fmt:formatNumber value="${not empty entry.unitPrice && entry.unitPrice ne null ?entry.unitPrice:'0'}" minFractionDigits="2" maxFractionDigits="2" /></div>
                            
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.uom"/>:</div>
                            <div class="col-md-1 col-md-6pe col-xs-8 p-r-0-xs  p-l-0-xs p-l-0-sm text-right xs-left${quotesComment?' hidden':''}" data-opacity="${quotesST?'no':''}">${not empty entry.UOM && entry.UOM ne null ?entry.UOM:'-'}</div>
                            
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.ext.price"/>:</div>
                            <div class="col-md-2 col-md-11pe col-xs-8 p-r-0-xs p-l-0-xs p-l-0-sm text-right quote-ext-price xs-left${quotesST?' bold':''} ${quotesComment?' hidden':''}" data-opacity="${quotesComment?'no':''}">$<fmt:formatNumber value="${not empty entry.extPrice && entry.extPrice ne null ?entry.extPrice:'0'}" minFractionDigits="2" maxFractionDigits="2" /></div>
                            <div class="col-xs-4 hidden-md hidden-lg p-l-0 text-gray bold-text f-s-11-xs-px text-uppercase${quotesST || quotesComment?' hidden':''}"><spring:theme code="myquotes.approval"/>:</div>
                            <div class="col-md-1 col-md-12pe col-xs-8 p-l-0-xs p-l-0-sm text-center xs-left${quotesComment?' hidden':''}" data-opacity="${quotesST?'no':''}">
                                <c:choose>
  									<c:when test="${entry.approvalHistoryCount == 0 || entry.approvalHistoryCount == '0'}">
										<button class="btn btn-link transition-3s p-t-0 p-b-0-imp no-padding-xs ${not empty entry.approvalDate && entry.approvalDate ne null ? '':'hidden' }" data-toggle="popover" data-content="<spring:theme code="myquotes.approved"/>&nbsp;${entry.approvalDate}">
											<common:globalIcon iconName="circle-check" iconFill="none" iconColor="#78A22F" width="18" height="19" viewBox="0 0 18 18" />
										</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-black-gray transition-3s position-relative selected-item-btn ${not empty entry.approvalDate && entry.approvalDate ne null ? '':'hidden' }" onclick="ACC.myquotes.quoteHistoryPopup(this, '${entry.quoteDetailID}', '${entry.quantity}', '${entry.UOM}')" data-historypopup="hide">
											<span class="f-s-15 text-dark-gray valign-t">${entry.approvalHistoryCount}</span>
											<common:globalIcon iconName="history" iconFill="none" iconColor="#78A22F" width="18" height="19" viewBox="0 0 18 18" display="" />
										</button>
									</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            </section>
            <c:if test="${quotesNum > 999}">
                <div class="row">
                    <div class="col-sm-12">
                        <button class="btn btn-blue btn-block bold-text m-t-15 quote-entries-show" onclick="ACC.myquotes.toggleEntries(this)" data-quotesNum="${quotesNum}" data-showText='<spring:theme code="variant.text.show" />&nbsp;${quotesNum-7}&nbsp;<spring:theme code="variant.text.more.items"/>' data-hideText='<spring:theme code="variant.text.show.less" />&nbsp;<spring:theme code="variant.text.items"/>' ><spring:theme code="variant.text.show" />&nbsp;${quotesNum-7}&nbsp;<spring:theme code="variant.text.more.items"/></button>
                    </div>
                </div>
            </c:if>
        </div>
        <!-- PDF Modal -->
        <div class="quote-modal hidden">
            <div class="row">
                <div class="col-sm-12 padding-top-20">
                    <spring:theme code="quote.success.msg" />
                </div>
                <div class="col-sm-12 padding-top-20">
                    <button onClick="colorbox.close()" class="btn btn-default font-Geogrotesque-bold quote-modal-cancel">
                        <spring:theme code="quote.return.quote" />
                    </button>
                    <a href="${returnListUrl}${savedListData.code}" class="btn btn-primary font-Geogrotesque-bold">
                        <spring:theme code="quote.return" />
                    </a>
                </div>
            </div>
        </div> <!-- ./PDF Modal -->
        <!-- Edit Quote Modal -->
        <div class="quote-edit-popup hidden">
            <div class="row">
                <div class="col-sm-12 padding-top-20">
                    <section class="f-s-16 text-dark-gray text-align-left">
                        <p class="flex flex-align-start"><span class="badge bg-white border-primary-2 m-r-15 border-radius-50pe text-green text-align-center">1</span>Choose the item quantity you want to approve for your order.</p>
                        <p class="flex flex-align-start"><span class="badge bg-white border-primary-2 m-r-15 border-radius-50pe text-green text-align-center">2</span>Pricing is based on the originally quoted quantity and may be updated by your seller if the quantity changes.</p>
                        <p class="flex flex-align-start"><span class="badge bg-white border-primary-2 m-r-15 border-radius-50pe text-green text-align-center">3</span>Find quantity approval history in the approval history column of your quote.</p>
                    </section>
                </div>
                <div class="col-xs-12 padding-top-20 hidden-md hidden-lg">
                    <button onClick="ACC.myquotes.editQuote('.quote-edit-request', 'cancel');" class="btn btn-primary btn-block font-Geogrotesque-bold">Confirm & Proceed</button>
                </div>
                <div class="col-sm-5 col-sm-offset-1 col-xs-12 col-xs-offset-0 padding-top-20 p-t-0-xs">
                    <button onClick="ACC.colorbox.close();" class="btn btn-default btn-block font-Geogrotesque-bold">Cancel Request</button>
                </div>
                <div class="col-sm-5 padding-top-20 hidden-sm hidden-xs">
                    <button onClick="ACC.myquotes.editQuote('.quote-edit-request', 'cancel');" class="btn btn-primary btn-block font-Geogrotesque-bold">Confirm & Proceed</button>
                </div>
            </div>
        </div> <!-- ./Edit Quote Modal -->
        <!-- Approve Quote success Modal -->
        <div id="quote-modal-success" class="hidden">
            <div class="row">
                <div class="col-sm-12 flex justify-center icon-row">
                    <common:checkmarkIcon iconColor="#78A22F" width="56" height="56"/>
                </div>
                <div class="col-sm-12 msg-row">
                    <p class="f-s-28 text-default font-Geogrotesque "><spring:theme code="quotes.popup.success.msg" /></p>
                    <p class="f-s-16 margin0"><spring:theme code="quotes.popup.success.msg2" /></p>
                </div>
                <div class="row flex justify-center flex-dir-column-xs flex-dir-column-sm">
                    <div class="col-sm-12 col-md-5 margin-bot-10-sm">
                        <a onClick="window.location.href = ACC.config.encodedContextPath + '/my-account/my-quotes';" class="btn btn-block btn-default bold-text">
                            <spring:theme code="quotes.popup.success.btn1" />
                        </a>
                    </div>
                    <div class="col-sm-12 col-md-5">
                        <button onClick="window.location.href=window.location.href;" class="btn btn-block btn-primary bold-text">
                            <spring:theme code="quotes.popup.success.btn2" />
                        </button>
                    </div>
                </div>
            </div>
        </div><!-- ./Approve Quote success Modal -->
        <!-- Quote History Popup -->
		<div class="row bg-white border-light-grey border-radius-3 box-shadow-10 p-t-20 p-b-10 p-x-10 f-s-14 f-s-12-xs-px f-s-12-sm-px position-absolute z-i-1000 quote-history-popup" style="display: none;">
			<button class="btn btn-link btn-small pointer text-green popup-close" onclick="$('[data-historypopup=\'show\']').trigger('click')">
				<span class="f-s-12 glyphicon glyphicon-remove"><span class="sr-only">History Close</span></span>
			</button>
            <div class="col-xs-12">
				<ul></ul>
			</div>
        </div><!-- ./Quote History Popup -->
        <!-- Update Expired Quote Modal -->
        <common:updateExpiredQuote />
        <!-- ./Update Expired Quote Modal -->
        <button class="btn btn-primary glyphicon glyphicon-arrow-up transition-3s btn-page-scroll" onclick="ACC.myquotes.pageScrollTop('.quotedetails-page')" style="display: none !important;"></button>
        <div class="row">
            <div class="col-md-12 col-sm-12 m-t-25 text-gray js-legal-disclaimer-1"><spring:theme code="quotes.legal.disclaimer1" /></div>
            <div class="col-md-12 col-sm-12 m-t-25 text-gray js-legal-disclaimer-2" data-legalDisclaimer="${quoteData.legalDisclaimer}">
                <c:if test="${not empty quoteData.legalDisclaimer && quoteData.legalDisclaimer ne null && quoteData.legalDisclaimer eq true}">
                    <spring:theme code="quotes.legal.disclaimer2.1" />&nbsp;<a href="${homelink}<spring:theme code="quotes.legal.disclaimer2.2" />"><spring:theme code="quotes.legal.disclaimer2.4" />${homelink}<spring:theme code="quotes.legal.disclaimer2.2" /></a>&nbsp;<spring:theme code="quotes.legal.disclaimer2.3" />
                </c:if>
            </div>
        </div>
        <script src="/_ui/responsive/common/js/jspdf.umd.min.js"></script>
	</c:when>
	<c:otherwise>
		<div class="row">
            <div class="col-md-12 col-sm-12 m-t-100 text-gray js-legal-disclaimer-1"><spring:theme code="quotes.legal.disclaimer1" /></div>
            <div class="col-md-12 col-sm-12 m-t-25 text-gray js-legal-disclaimer-2" data-legalDisclaimer="${quoteData.legalDisclaimer}">
                <c:if test="${not empty quoteData.legalDisclaimer && quoteData.legalDisclaimer ne null && quoteData.legalDisclaimer eq true}">
                    <spring:theme code="quotes.legal.disclaimer2.1" />&nbsp;<a href="${homelink}<spring:theme code="quotes.legal.disclaimer2.2" />"><spring:theme code="quotes.legal.disclaimer2.4" />${homelink}<spring:theme code="quotes.legal.disclaimer2.2" /></a>&nbsp;<spring:theme code="quotes.legal.disclaimer2.3" />
                </c:if>
            </div>
        </div>
	</c:otherwise>
</c:choose>
<!-- ./ Quote Details Page -->