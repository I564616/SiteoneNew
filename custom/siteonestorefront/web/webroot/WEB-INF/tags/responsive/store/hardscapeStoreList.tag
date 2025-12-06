<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>


<div class="map-store-containerSpecialty margin20">
<div class="store__finder store__finderSpecialty">
<div class="store__finder--navigation scroll-bar col-md-5">
<input id="myStoresIdList" type="hidden" value='${ycommerce:getJSONFromList(myStoresIdList)}'>
                        <ul class="store__finder--navigation-list js-store-finder-navigation-list">
                            
                        </ul>
                        <div>
                            <button class="btn btn-primary js-store-finder-pager-next" type="button" style="display:none">
                                <spring:theme code="storeFinder.pagination.next" text="See More"></spring:theme>
                            </button>
                        </div>
                       
                    </div>
                     <div class="col-xs-12 store__finder--details js-store-finder-details col-md-7">
                    <div class="visible-xs visible-sm store-specialty-heading"><span class="bold-text">Map</span></div>
                        <div class="store__finder--map js-store-finder-map" id="store-finder-map"></div>
                         <div id="hardscapegoogleMap"></div>
                    </div>
                        
                    </div>
                    <div class="cl"></div>
                    </div>