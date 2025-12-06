<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="search-by flex-1-xs">
    <div class="btn btn-black-gray js-document-invoice-btn hidden-xs hidden-sm" onclick="ACC.global.toggleOffElems(this, '.search-dropdown-inv' , 'active' , '.global-popup-box');">
        <span class="valign-m">
            <common:headerIcon iconName="search" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 17 17" />
        </span>
        <span class="f-s-16 f-w-400">Search By</span>
    </div>
    <div class="btn btn-white btn-block js-document-invoice-btn no-margin hidden-lg hidden-md" onclick="ACC.myquotes.filterPopup('show', '-110%', '.search-dropdown-inv');ACC.global.popupHeightSet(this, '.search-dropdown-inv');">
        <span class="valign-m">
            <common:headerIcon iconName="search" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 17 17" />
        </span>
        <span class="f-s-16 f-w-700">Search By</span>
    </div>
    <div class="row search-dropdown-inv js-document-global-box" style="display: none;">
        <div class="b-b-grey black-title-head col-xs-12 f-s-20 f-w-600 font-Geogrotesque hidden-lg hidden-md m-b-15 p-b-20 text-center-xs">Search</div>
        <div class="b-b-0-xs b-b-grey black-title-head col-xs-12 f-s-16 f-s-20-xs-px f-w-500 font-Geogrotesque m-b-15 p-b-0-xs p-b-10 p-x-20-xs p-x-20-sm">Search by Order, Invoice or PO #</div>
        <div class="btn btn-link search-dropdown-close hidden-lg hidden-md" onclick="ACC.myquotes.filterPopup('hide', '-105%', '.search-dropdown-inv')"><span class="icon-close"></span></div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" checked onclick="ACC.order.invoicesDropdownSearchType(this, 'Order', 'oSearchParam', '#search-invoice')" /></span></label>Order
        </div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" onclick="ACC.order.invoicesDropdownSearchType(this, 'Invoice', 'iSearchParam', '#search-invoice')" /></span></label>Invoice
        </div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm m-b-10 f-w-400 f-s-14 f-s-18-xs-px mob-flex-reverse"><label class="radio-inline"><span class="colored-radio"><input type="radio" name="s-by" onclick="ACC.order.invoicesDropdownSearchType(this, 'PO', 'pnSearchParam', '#search-invoice')" /></span></label>PO
        </div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm form-group m-t-10">
            <input type="text" id="search-invoice" name="oSearchParam" value="${searchParam}" placeholder="Enter order, invoice, or PO#" class="form-control" />
        </div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm b-t-grey black-title-head f-s-16 f-s-20-xs-px f-w-500 font-Geogrotesque p-y-10">Search by Custom Date Range</div>
        <div class="col-xs-12 p-x-20-xs p-x-20-sm date-search">
            <div class="row">
                <div class="col-xs-6 invoice-label">
                    <div class="p-relative date-wrap dash">
                        <input id="dateFrom" name="dateFrom" value="${dateFrom}" data-datefrom="${dateFrom}" autocomplete="off" placeholder="From" class="form-control datepickerinvoiceFrom previous-invoice-calendar" type="text" />
                        <span id="dateFrom" class="valign-m date-icon p-absolute">
                            <common:headerIcon iconName="calendar" iconFill="none" iconColor="#77A12E" width="20" height="20" viewBox="0 0 23 24" />
                        </span>
                    </div>
                </div>
                <div class="col-xs-6 invoice-label">
                    <div class="p-relative date-wrap">
                        <input id="dateTo" name="dateTo" value="${dateTo}" data-dateto="${dateTo}" type="text" autocomplete="off" placeholder="To" class="form-control datepickerinvoiceTo" id="currentdate" />
                        <span id="dateTo" class="valign-m date-icon p-absolute">
                            <common:headerIcon iconName="calendar" iconFill="none" iconColor="#77A12E" width="20" height="20" viewBox="0 0 23 24" />
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="b-t-grey col-xs-12 flex flex-align-center hidden-sm hidden-xs justify-between m-t-15 p-x-20-xs p-x-20-sm p-t-15">
            <input type="reset" value="Clear all" class="btn btn-link p-l-0 p-t-0-imp bold text-gray article-link text-hover-primary" />
            <input type="submit" class="btn btn-primary invoiceButton btn-submit f-s-14-imp" value="Search">
        </div>
        <div class="col-xs-12 padding-20 search-dropdown-footer hidden-md hidden-lg">
            <div class="row">
                <div class="col-xs-6">
                    <input type="reset" value="Clear All" class="btn btn-default full-width" />
                </div>
                <div class="col-xs-6">
                    <input type="submit" class="btn btn-primary invoiceButton full-width" value="Search">
                </div>
            </div>
        </div>
    </div>
</div>