<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<input type="hidden" id="unitId" name="unitId" value="${fn:escapeXml(unitId)}">
<spring:url value="/" var="homelink" htmlEscape="false" />
<c:set var="quotesPage" value="25" />
<!-- Quote Page -->
<div class="orders-tabs-container f-s-18 f-s-12-xs-pt f-s-12-sm-pt font-Geogrotesque">
    <div class="row container-lg margin0 p-l-0-xs p-b-10-xs p-t-5-xs p-l-0-sm p-b-10-sm p-t-5-sm">
        <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab active-account-tab" data-key="quotepage" href="${homelink}my-account/my-quotes"><spring:theme code="myquotes.quotes" />
        </a>
        <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="orderhistorypage" href="${homelink}my-account/orders/${sessionShipTo.uid}"><spring:theme code="homepage.myOrder" />
        </a>
        <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="purchasedproducts" href="${homelink}my-account/buy-again/${sessionShipTo.uid}"><spring:theme code="text.account.buyagain" />
        </a>
        <c:if test="${InvoicePermission}">
            <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="invoicespage" data-active="invoicesTab" href="${homelink}my-account/invoices/${sessionShipTo.uid}"><spring:theme code="text.account.invoices" /></a>
        </c:if>
    </div>
    <div class="tab-wrapper-border"></div>
</div>
<div class="p-t-80 quote-page">
    <div class="row margin0 p-y-25 no-padding-hrz-xs p-y-15-xs">
        <div class="col-md-12 col-xs-8 p-l-0 p-r-0-sm p-r-0-xs m-b-15 m-b-0-xs m-b-0-sm f-s-28 f-s-20-xs-px text-bold text-default font-Geogrotesque"><span class="text-green quote-results-num"></span> <spring:theme code="myquotes.results.quotes" /></div>
        <div class="col-lg-3 col-md-4 bg-white border-grey border-radius-3 hidden-sm hidden-xs p-a-5">
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle quotes-pill-toggle-open" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','open')"><spring:theme code="myquotes.pill.open" /></button>
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','full')"><spring:theme code="myquotes.pill.approved" /></button>
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','expired')"><spring:theme code="myquotes.pill.expired" /></button>
            <button class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle quotes-pill-toggle-all disabled" disabled><spring:theme code="myquotes.pill.all" /></button>
        </div>
        <div class="col-lg-3 col-lg-offset-2 col-md-4 col-md-offset-1 col-md-30pe col-xs-4 col-xs-offset-0 padding0 text-right quote-filter-popup">
            <button title="Account" type="button" class="btn btn-block btn-white-border transition-3s quote-filter-popup-button" onclick="ACC.myquotes.toggleOffElems(this, '.quote-filter-popup-box' , 'active' , '.quote-filter-popup-close')">
                <span class="flex-center hidden-lg hidden-md">
                    <common:globalIcon iconName="filter" iconFill="#f90" iconColor="#78a22f" width="13" height="15" viewBox="0 0 16 17" display="m-r-5" />
                    <span class="bold text-primary">
                        <spring:theme code="myquotes.filter" />
                    </span>
                </span>
                <span class="hidden-sm hidden-xs">
                    <span class="bold"><spring:theme code="myquotes.pill.filterBy" /> (<span data-stext='<spring:theme code="myquotes.pill.all" />' class="quote-selected-account-count"><spring:theme code="myquotes.pill.all" /></span>)</span>
                    <span class="f-s-12 glyphicon glyphicon-chevron-down p-t-3 pull-right text-primary"></span>
                </span>
            </button>
            <div class="bg-white border-grey quote-filter-popup-box scroll-bar" style="display: none;">
                <p class="f-s-20 f-w-600 font-geogrotesque hidden-lg hidden-md p-y-15 b-b-grey text-center text-default"><spring:theme code="myquotes.pill.filterBy" /></p>
                <button class="btn btn-link text-green hidden-md hidden-lg quote-filter-popup-close" onclick="ACC.myquotes.toggleOffElems(this, '.quote-filter-popup-box' , 'active', '.quote-filter-popup-button')">
                    <common:globalIcon iconName="close" iconFill="none" iconColor="#78A22F" width="15" height="16" viewBox="0 0 12 12" display="" />
                </button>
                <div class="col-md-12 hidden">
                    <select data-quotefilter="" name="options" class="form-control custom-select-arrow">
                        <option value="" class="hidden" selected><spring:theme code="myquotes.time" /></option>
                        <option value="30"><spring:theme code="myquotes.past.day30" /></option>
                        <option value="60"><spring:theme code="myquotes.past.day60" /></option>
                        <option value="90"><spring:theme code="myquotes.past.day90" /></option>
                        <option value="180"><spring:theme code="myquotes.past.day180" /></option>
                        <option value="365"><spring:theme code="myquotes.prev.year1" /></option>
                        <option value="730"><spring:theme code="myquotes.prev.year2" /></option>
                        <option value="99999" selected>All</option>
                    </select>
                    <select data-quotefilter="" name="options" class="form-control custom-select-arrow m-t-15 m-b-25 m-y-10-xs">
                        <option value="" selected><spring:theme code="myquotes.status" /></option>
                    </select>
                    <hr>
                </div>
                <div class="col-md-12 hidden">
                    <div class="quotes-show" data-quotefilter=""></div>
                    <hr>
                </div>
                <div class="col-md-12 hidden">
                    <spring:theme code="myquotes.sort" />
                    <select data-quotefilter="" name="options" class="form-control custom-select-arrow m-t-10 m-b-25 m-y-10-xs">
                        <option value="date" selected><spring:theme code="myquotes.submitted" /></option>
                        <option value="edate"><spring:theme code="myquotes.exp.date" /></option>
                        <option value="quote" class="hidden"><spring:theme code="myquotes.number" /></option>
                        <option value="job" class="hidden"><spring:theme code="myquotes.po.job" /></option>
                        <option value="price"><spring:theme code="myquotes.total.price" /></option>
                    </select>
                    <hr>
                </div>
                <div class="col-md-12 hidden">
                    <spring:theme code="myquotes.view" />
                    <select data-quotefilter="" name="options" class="form-control custom-select-arrow m-t-10 m-b-25 m-y-10-xs">
                        <option value="${quotesPage}" selected>${quotesPage}</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </div>
                <div class="col-md-12 padding0">
                    <div class="m-b-10 p-t-15 m-b-15 p-b-10 p-t-0-xs p-t-0-sm list-group quote-filter-popup-accounts"></div>
                </div>
                <div class="col-xs-12 bg-white quote-filter-popup-footer">
                    <div class="row b-t-grey p-b-20 p-t-20 p-t-10-xs p-t-10-sm">
                        <div class="col-xs-6 p-l-20 p-r-10">
                            <button class="btn btn-default btn-block font-Geogrotesque-bold" onclick="ACC.myquotes.resetQuoteFilter('all')"><spring:theme code="myquotes.clear.all.filter" /></button>
                        </div>
                        <div class="col-xs-6 p-r-20 p-l-10">
                            <button class="btn btn-primary btn-block font-Geogrotesque-bold" onclick="ACC.myquotes.updateQuoteFilter()"><spring:theme code="myquotes.enter" /></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 m-y-15 bg-white border-grey border-radius-3 p-a-5 flex-center justify-space-around-sm justify-space-around-xs hidden-lg hidden-md">
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle quotes-pill-toggle-open" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','open')"><spring:theme code="myquotes.pill.open" /></button>
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','full')"><spring:theme code="myquotes.pill.approved" /></button>
            <button type="button" class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle" onclick="ACC.myquotes.pillToggle(this,'.quotes-pill-toggle','expired')"><spring:theme code="myquotes.pill.expired" /></button>
            <button class="bold btn text-dark-gray bg-white transition-3s quotes-pill-toggle quotes-pill-toggle-all disabled" disabled><spring:theme code="myquotes.pill.all" /></button>
        </div>
        <div class="col-lg-3 col-md-4 col-md-28pe col-xs-12 col-xs-offset-0 p-r-0 p-l-0-xs p-l-0-sm text-right quotes-pill-toggle-input-box">
            <div class="input-group from-input">
                <input type="text" data-quotefilter="" name="name" class="form-control transition-3s border-grey-imp b-r-l-3 quotes-pill-toggle-input" autocomplete="off" value="" maxlength="200" aria-describedby="basic-addon1">
                <label class="from-input-label">
                    <spring:theme code="myquotes.search.quote" />
                </label>
                <label class="from-input-label">
                    <spring:theme code="myquotes.search.job" />
                </label>
				<div class="b-r-r-3 bg-green input-group-btn text-center">
					<button onclick="ACC.myquotes.quoteSearch('.quotes-pill-toggle-input','.quotes-pill-toggle','all')" class="btn btn-primary input-group-addon transition-3s">
						<common:headerIcon iconName="search" iconFill="#FFFFFF" iconColor="#FFFFFF" width="19" height="20" viewBox="0 0 19 20" display="" />
					</button>
				</div>
            </div>
        </div>
    </div>
    <div class="flex-center-xs flex-xs hidden-lg hidden-md margin0 no-padding-hrz-xs row text-dark-gray">
        <div class="col-xs-12 padding0 quotes-shipto-info"></div>
    </div>
    <div class="row hidden-xs margin0 p-l-15 p-r-15 p-y-20 bg-off-grey add-border-radius text-white bold-text f-s-12 text-uppercase flex-center justify-center">
        <div class="col-md-3 col-md-11pe"><spring:theme code="myquotes.number" /></div>
        <div class="col-md-2"><spring:theme code="myquotes.po.job" /></div>
        <div class="col-md-2"><spring:theme code="myquotes.acc.name" /></div>
        <button class="col-md-3 col-md-13pe flex-stretch pointer bg-black bg-transparent text-white text-uppercase border-none quote-sort-button" data-quotesort="date" onclick="ACC.myquotes.updateQuoteFilter(false, 'date', 999999, this)"><spring:theme code="myquotes.submitted" /><common:globalIcon iconName="sort" iconFill="#f90" iconColor="#fff" width="8" height="14" viewBox="0 0 8 14" display="m-l-5" /></button>
        <button class="col-md-3 col-md-13pe flex-stretch pointer bg-black bg-transparent text-white text-uppercase border-none quote-sort-button" data-quotesort="edate" onclick="ACC.myquotes.updateQuoteFilter(false, 'edate', 999999, this)"><spring:theme code="myquotes.exp.date" /><common:globalIcon iconName="sort" iconFill="#f90" iconColor="#fff" width="8" height="14" viewBox="0 0 8 14" display="m-l-5" /></button>
        <button class="col-md-1 col-md-10pe padding0 flex-stretch pointer bg-black bg-transparent text-white text-uppercase border-none quote-sort-button" data-quotesort="price" onclick="ACC.myquotes.updateQuoteFilter(false, 'price', 999999, this)"><spring:theme code="myquotes.total.price" /><common:globalIcon iconName="sort" iconFill="#f90" iconColor="#fff" width="8" height="14" viewBox="0 0 8 14" display="m-l-5" /></button>
        <div class="col-md-1 col-md-10pe text-center"><spring:theme code="myquotes.approved" /></div>
        <div class="col-md-1 col-md-9pe p-l-0 text-center"><spring:theme code="myquotes.actions" /></div>
    </div>
    <div class="row m-t-25 hidden quote-no-filter-results">
        <div class="col-md-offset-1 col-md-10">
            <div class="alert alert-dismissible text-center text-default" role="alert">
                <p class="bold font-Geogrotesque f-s-24 f-s-20-xs-px f-s-20-sm-px"><spring:theme code="myquotes.alert" /></p>
                <p class="f-s-20 f-s-16-xs-px f-s-16-sm-px"><spring:theme code="myquotes.no.quotes" /></p>
                <a class="btn btn-primary m-t-20" href="${homelink}my-account/my-quotes"><spring:theme code="myquotes.here" /></a>
            </div>
        </div>
    </div>
</div>
<nav class="m-t-35 text-center quote-pagination hidden" aria-label="Page navigation">
    <ul class="pagination pagination-sm">
        <li class="quote-pagination-prev disabled">
            <button onclick="ACC.myquotes.showingPage(this, 'prev')" aria-label="Previous"><span aria-hidden="true">&larr;</span>
                <spring:theme code="myquotes.previous" />
            </button>
        </li>
        <li class="quote-pagination-next">
            <button onclick="ACC.myquotes.showingPage(this, 'next')" aria-label="Next">
                <spring:theme code="myquotes.next" /> <span aria-hidden="true">&rarr;</span>
            </button>
        </li>
    </ul>
</nav>
<button class="btn btn-primary glyphicon glyphicon-arrow-up transition-3s btn-page-scroll" onclick="ACC.myquotes.pageScrollTop('.breadcrumb-section')" style="display: none !important;"></button>
<!-- Update Expired Quote Modal -->
<common:updateExpiredQuote /> 
<!-- ./Update Expired Quote Modal -->
<!-- ./ quote Page -->