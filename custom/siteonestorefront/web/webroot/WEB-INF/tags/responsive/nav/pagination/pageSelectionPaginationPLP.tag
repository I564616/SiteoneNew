<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="numberPagesShown" required="true" type="java.lang.Integer" %>
<%@ attribute name="themeMsgKey" required="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<c:set var="hasPreviousPage" value="${searchPageData.pagination.currentPage > 0}"/>
<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}"/>

<c:choose>
    <c:when test="${cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'searchGrid'}">
        <c:set var="urlParameters" value="&viewtype=All${not empty param.sort ? '&sort=' : ''}${param.sort}${not empty param.inStock ? '&inStock=on' : ''}${not empty param.nearby ? '&nearby=on' : ''}${not empty param.expressShipping ? '&expressShipping=on' : ''}"/>
    </c:when>
    <c:otherwise>
        <c:set var="urlParameters" value="&viewtype=All"/>
    </c:otherwise>
</c:choose>

<c:if test="${(searchPageData.pagination.numberOfPages > 1)}">
    <ul class="pagination">
        <c:if test="${hasPreviousPage}">
            <li class="pagination-prev">
                <spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
                    <spring:param name="page" value="${searchPageData.pagination.currentPage - 1}"/>
                </spring:url>
                <ycommerce:testId code="searchResults_previousPage_link">
                <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage' }">
                    <a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link"><span class="glyphicon glyphicon-chevron-left"></span> <span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.previous" /></span></a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                      <a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link"><span class="glyphicon glyphicon-chevron-left"></span> <span class=""><spring:theme code="pageSelectionPagination.previous" /></span></a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'detailsSavedListPage'}">
                    	<a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link saved-list-previous-link"><span class="glyphicon glyphicon-chevron-left"></span></a>
                    </c:when>
                    <c:otherwise>
                    <a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link"><span class="glyphicon glyphicon-chevron-left"></span></a>
                    </c:otherwise>
               </c:choose>
                    
                </ycommerce:testId>
            </li>
        </c:if>

        <c:if test="${!hasPreviousPage}">
               <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                      <li class="pagination-prev disabled previous-link"><span class="glyphicon glyphicon-chevron-left"></span> <span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.previous" /></span></li>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <li class="pagination-prev disabled previous-link"><span class="glyphicon glyphicon-chevron-left"></span> <span class=""><spring:theme code="pageSelectionPagination.previous" /></span></li>
                    </c:when>
                    <c:otherwise>
                      <li class="pagination-prev disabled previous-link"><span class="glyphicon glyphicon-chevron-left"></span></li>
                    </c:otherwise>
               </c:choose>
        </c:if>

        <c:set var="limit" value="${numberPagesShown}"/>
        <c:set var="halfLimit"><fmt:formatNumber value="${limit/2}" maxFractionDigits="0"/></c:set>
        <c:set var="beginPage">
            <c:choose>
                <%-- Limit is higher than number of pages --%>
                <c:when test="${limit gt searchPageData.pagination.numberOfPages}">1</c:when>
                <%-- Start shifting page numbers once currentPage reaches halfway point--%>
                <c:when test="${searchPageData.pagination.currentPage + halfLimit ge limit}">
                    <c:choose>
                        <c:when test="${searchPageData.pagination.currentPage + halfLimit lt searchPageData.pagination.numberOfPages}">
                            ${searchPageData.pagination.currentPage + 1 - halfLimit}
                        </c:when>
                        <c:otherwise>${searchPageData.pagination.numberOfPages + 1 - limit}</c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>1</c:otherwise>
            </c:choose>
        </c:set>
        <c:set var="endPage">
            <c:choose>
                <c:when test="${limit gt searchPageData.pagination.numberOfPages}">
                    ${searchPageData.pagination.numberOfPages}
                </c:when>
                <c:when test="${hasNextPage}">
                    ${beginPage + limit - 1}
                </c:when>
                <c:otherwise>
                    ${searchPageData.pagination.numberOfPages}
                </c:otherwise>
            </c:choose>
        </c:set>
        <c:forEach begin="${beginPage}" end="${endPage}" var="pageNumber">
            <c:set var="linkClass" value=""/>
            <c:choose>
                <c:when test="${searchPageData.pagination.currentPage + 1 ne pageNumber}">
                    <spring:url value="${searchUrl}" var="pageNumberUrl" htmlEscape="true">
                        <spring:param name="page" value="${pageNumber - 1}"/>
                    </spring:url>
                    

                    <c:choose>
                        <c:when test="${ (searchPageData.pagination.currentPage + 1 eq beginPage) or (searchPageData.pagination.currentPage + 1 eq endPage) }">
                            <c:if test="${searchPageData.pagination.currentPage + 1 eq beginPage}">
                                <c:if test="${pageNumber gt searchPageData.pagination.currentPage + 3}">
                                    <c:set var="linkClass" value="hidden-xs"/>
                                </c:if>
                            </c:if>

                            <c:if test="${searchPageData.pagination.currentPage + 1 eq endPage}">
                                <c:if test="${pageNumber lt searchPageData.pagination.currentPage - 1}">
                                    <c:set var="linkClass" value="hidden-xs"/>
                                </c:if>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${pageNumber lt searchPageData.pagination.currentPage}">
                                <c:set var="linkClass" value="hidden-xs"/>
                            </c:if>

                            <c:if test="${pageNumber gt searchPageData.pagination.currentPage + 2}">
                                <c:set var="linkClass" value="hidden-xs"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <ycommerce:testId code="pageNumber_link">
                        <input type="hidden" id="current_span" value="${pageNumberUrl}"/>
                         <input type="hidden" id="url-parameters" value="${urlParameters}"/> 
                        	<c:choose>
                        	<c:when test="${cmsPage.uid eq 'detailsSavedListPage'}">
                        	<li class="${linkClass}">
                        		<input type="hidden" id="current_span" value="${pageNumberUrl}"/>
                         		<input type="hidden" id="url-parameters" value="${urlParameters}"/> 
                        		<a class="selectedProductRetainOfPagination" href="#">${pageNumber}</a>
                        	</li>
                        	</c:when>
                        	<c:otherwise>
                        	<li class="${linkClass}"><a href="${pageNumberUrl}${urlParameters}">${pageNumber}</a></li>
                        	</c:otherwise>
                        	</c:choose>
                    </ycommerce:testId>
                </c:when>
                <c:otherwise>
                    <li class="active"><span>${pageNumber} <span class="sr-only">(current)</span></span></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${hasNextPage}">
        <c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'productGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'accountOrdersPage' || cmsPage.uid eq 'ewalletdetailspage'}">
            <spring:url value="${searchUrl}" var="endPageUrl" htmlEscape="true">
                <spring:param name="page" value="${searchPageData.pagination.numberOfPages - 1}"/>
            </spring:url>
            <li class="empty-pagination">...</li>
            <li><a class="" href="${endPageUrl}${urlParameters}">${searchPageData.pagination.numberOfPages}</a></li>
            </c:if>
            <li class="pagination-next">
                <spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
                    <spring:param name="page" value="${searchPageData.pagination.currentPage + 1}"/>
                </spring:url>
                <ycommerce:testId code="searchResults_nextPage_link">
                    <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                    <a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link"><span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.next" /></span> <span class="glyphicon glyphicon-chevron-right"></span></a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link"><span class=""><spring:theme code="pageSelectionPagination.next" /></span> <span class="glyphicon glyphicon-chevron-right"></span></a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'detailsSavedListPage'}">
                    	<a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link saved-list-next-link"><span class="glyphicon glyphicon-chevron-right"></span></a>
                    </c:when>
                    <c:otherwise>
                    <a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link"><span class="glyphicon glyphicon-chevron-right"></span></a>
                    </c:otherwise>
               </c:choose>
                    
                </ycommerce:testId>
            </li>
        </c:if>

        <c:if test="${!hasNextPage}">
                 <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                     <li class="pagination-next disabled next-link"><span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.next" /></span> <span class="glyphicon glyphicon-chevron-right"></span></li>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <li class="pagination-next disabled next-link"><span><spring:theme code="pageSelectionPagination.next" /></span> <span class="glyphicon glyphicon-chevron-right"></span></li>
                    </c:when>
                    <c:otherwise>
                     <li class="pagination-next disabled next-link"><span class="glyphicon glyphicon-chevron-right"></span> </li>
                    </c:otherwise>
               </c:choose>
           
 
        </c:if>
    </ul>
    
    
	
</c:if>

