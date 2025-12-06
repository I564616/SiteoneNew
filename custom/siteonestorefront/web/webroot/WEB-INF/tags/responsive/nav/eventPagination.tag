<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="com.siteone.facade.EventSearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTopTotals" required="false" type="java.lang.Boolean" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="additionalParams" required="false" type="java.util.HashMap" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="showCurrentPageInfo" required="false" type="java.lang.Boolean" %>
<%@ attribute name="hideRefineButton" required="false" type="java.lang.Boolean" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="eventFooter" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>

<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:if test="${eventFooter ne 'true'}">
<c:if test="${searchPageData.pagination.totalNumberOfResults == 0}"><div><spring:theme code="eventPagination.text" /></div></c:if></c:if>
<span class="eventsTotalResults" data-results="${searchPageData.pagination.totalNumberOfResults}"></span>
<c:if test="${not hideRefBtn and searchPageData.pagination.totalNumberOfResults ne 0}">
	                            <div class="col-xs-4 col-sm-2 col-md-4 hidden-md hidden-lg mb-sorting-sec">
	                                <ycommerce:testId code="searchResults_refine_button">
	                                    <product:productRefineButton styleClass="btn btn-default js-show-facets"/>
	                                </ycommerce:testId>
	                            </div>
                        	</c:if>
<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
<div class="col-xs-12 col-sm-6 col-md-5 pagination-wrap">

                            <pagination:eventPageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
                                                                numberPagesShown="${numberPagesShown}"
                                                                themeMsgKey="${themeMsgKey}"/>
                        </div>
   
</c:if>
