<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="locationQuery" required="false" type="java.lang.String" %>
<%@ attribute name="geoPoint" required="false" type="de.hybris.platform.commerceservices.store.data.GeoPoint" %>
<%@ attribute name="numberPagesShown" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>

<c:url value="/store-finder" var="storeFinderFormAction" />

<div class="store__finder js-store-finder" data-url="${storeFinderFormAction}">
    <div class="row">
        <ycommerce:testId code="storeFinder">
            <div class="col-lg-12">
                <%-- <div class="store__finder--pagination">
                    <div class="pull-right">
                        <button class="btn btn-default js-store-finder-pager-prev" type="button">
                            <spring:theme code="storeFinder.pagination.previous" text="Previous"></spring:theme>
                        </button>
                        <button class="btn btn-default js-store-finder-pager-next" type="button">
                            <spring:theme code="storeFinder.pagination.next" text="See More"></spring:theme>
                        </button>
                    </div>
                    <span class="js-store-finder-pager-item-from"></span>-
                    <span class="js-store-finder-pager-item-to"></span>
                    <spring:theme code="storeFinder.pagination.from" text="from"></spring:theme>
                    <span class="js-store-finder-pager-item-all"></span>
                    <spring:theme code="storeFinder.pagination.stores" text="stores found"></spring:theme>
                </div> --%>

                <div class="store__finder--panel">

                    <div class="store__finder--navigation scroll-bar col-md-5">
                        <ul id="branchlist" class="store__finder--navigation-list js-store-finder-navigation-list">
                            <li class="loading"><span class="glyphicon glyphicon-repeat"></span></li>
                        </ul>
                        <div style="text-align: center;margin-top: 20px;">
                            <button class="btn btn-primary js-store-finder-pager-next" type="button" style="display:none">
                                <spring:theme code="storeFinder.pagination.next" text="See More"></spring:theme>
                            </button>
                        </div>
                        
                    </div>
                    <div class="col-xs-12 store__finder--details js-store-finder-details col-md-7">
                    	<div class="visible-xs visible-sm store-specialty-heading"><span class="bold-text">Map</span></div>
                        <div class="store__finder--map js-store-finder-map"></div>
                    </div>
                   
                </div>

               <%--  <div class="store__finder--pagination">
                    <div class="pull-left">
                        <button class="btn btn-default js-store-finder-pager-prev" type="button" >
                            <spring:theme code="storeFinder.pagination.previous" text="Previous"></spring:theme>
                        </button>
                        <button class="btn btn-default js-store-finder-pager-next" type="button" >
                            <spring:theme code="storeFinder.pagination.next" text="See More"></spring:theme>
                        </button>
                    </div>
                    <span class="js-store-finder-pager-item-from"></span>
                    <span class="js-store-finder-pager-item-to"></span>
                    <spring:theme code="storeFinder.pagination.from" text="from"></spring:theme>
                    <span class="js-store-finder-pager-item-all"></span>
                    <spring:theme code="storeFinder.pagination.stores" text="stores found"></spring:theme>
                </div> --%>
            </div>

        </ycommerce:testId>
    </div>
</div>