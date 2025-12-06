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


<c:set var="curatedplpInstocktoggle" value="false"/>
<c:forTokens items ="${isCMSPage}" delims = "," var = "curatedplpisCMSPage">
<c:if test = "${fn:contains(pageName,curatedplpisCMSPage)}">
<c:set var="curatedplpInstocktoggle" value="true"/>
</c:if>
</c:forTokens> 


<c:set var="hasPreviousPage" value="${searchPageData.pagination.currentPage > 0}"/>
<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}"/>

<c:set var="urlParameters" value="&viewtype=All${not empty param.sort ? '&sort=' : ''}${param.sort}${not empty param.inStock ? '&inStock=on' : ''}${not empty param.nearby ? '&nearby=on' : ''}${not empty param.expressShipping ? '&expressShipping=on' : ''}"/>
    
<c:set var="toggleurl" value="${fn:indexOf(urlParameters,'&inStock=on')}"/>
<c:if test="${(toggleurl > 1 && curatedplpInstocktoggle)}">
	<c:set var="urlParameters" value="&viewtype=All"/>
</c:if>
 
<c:set var="toggleurlnearby" value="${fn:indexOf(urlParameters,'&nearby=on')}"/>
<c:if test="${(toggleurlnearby > 1 && curatedplpInstocktoggle)}">
	<c:set var="curatedplpInstocktoggle" value="false"/>
</c:if>
 
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
                    <a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link">&#8592; <span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.previous" /></span></a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                      <a href="${previousPageUrl}${urlParameters}" rel="prev" class="previous-link">&#8592; <span class=""><spring:theme code="pageSelectionPagination.previous" /></span></a>
                    </c:when>
                    <c:otherwise>
                     <c:set var="curatedplpToggle" value=""/>
                          <c:choose>
	                        	<c:when test="${curatedplpInstocktoggle}">
	                        	<c:set var="curatedplpToggle" value="&inStock=on"/>
	                        	</c:when>
	                        	<c:otherwise>
	                        	<c:set var="curatedplpToggle" value=""/>
	                        	</c:otherwise>
                          </c:choose>
                    <a href="${previousPageUrl}${urlParameters}${curatedplpToggle}" rel="prev" class="previous-link">&#8592;</a>
                    </c:otherwise>
               </c:choose>
                    
                </ycommerce:testId>
            </li>
        </c:if>

        <c:if test="${!hasPreviousPage}">
               <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                      <li class="pagination-prev disabled previous-link">&#8592; <span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.previous" /></span></li>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <li class="pagination-prev disabled previous-link">&#8592; <span class=""><spring:theme code="pageSelectionPagination.previous" /></span></li>
                    </c:when>
                    <c:otherwise>
                      <li class="pagination-prev disabled previous-link">&#8592;</li>
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
                         
                         <c:set var="curatedplpToggle" value=""/>
                          <c:choose>
	                        	<c:when test="${curatedplpInstocktoggle}">
	                        	<c:set var="curatedplpToggle" value="&inStock=on"/>
	                        	</c:when>
	                        	<c:otherwise>
	                        	<c:set var="curatedplpToggle" value=""/>
	                        	</c:otherwise>
                          </c:choose>
                        
                        	<c:choose>
                        	<c:when test="${cmsPage.uid eq 'detailsSavedListPage'}">
                        	<li class="${linkClass}"><a class="selectedProductRetainOfPagination" href="#">${pageNumber}</a></li>
                        	</c:when>
                        	<c:otherwise>
                        	<li class="${linkClass}"><a href="${pageNumberUrl}${urlParameters}${curatedplpToggle}">${pageNumber}</a></li>
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
        <spring:url value="${searchUrl}" var="endPageUrl" htmlEscape="true">
                <spring:param name="page" value="${searchPageData.pagination.numberOfPages - 1}"/>
            </spring:url>
            <li class="empty-pagination">...</li>
            
            <c:set var="curatedplpToggle" value=""/>
            <c:choose>
	           	<c:when test="${curatedplpInstocktoggle}">
	           	<c:set var="curatedplpToggle" value="&inStock=on"/>
	           	</c:when>
	           	<c:otherwise>
	           	<c:set var="curatedplpToggle" value=""/>
	           	</c:otherwise>
            </c:choose>
            
            
            <li><a class="" href="${endPageUrl}${urlParameters}${curatedplpToggle}">${searchPageData.pagination.numberOfPages}</a></li>
          
            <li class="pagination-next">
                <spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
                    <spring:param name="page" value="${searchPageData.pagination.currentPage + 1}"/>
                </spring:url>
                <ycommerce:testId code="searchResults_nextPage_link">
                    <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                    <a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link"><span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.next" /></span> &#8594;</a>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <a href="${nextPageUrl}${urlParameters}" rel="next" class="next-link"><span class=""><spring:theme code="pageSelectionPagination.next" /></span> &#8594;</a>
                    </c:when>
                    <c:otherwise>
                     <c:set var="curatedplpToggle" value=""/>
                     <c:choose>
                    	<c:when test="${curatedplpInstocktoggle}">
                    	<c:set var="curatedplpToggle" value="&inStock=on"/>
                    	</c:when>
                    	<c:otherwise>
                    	<c:set var="curatedplpToggle" value=""/>
                    	</c:otherwise>
                     </c:choose>
                    
                    <a href="${nextPageUrl}${urlParameters}${curatedplpToggle}" rel="next" class="next-link">&#8594;</a>
                    </c:otherwise>
               </c:choose>
                    
                </ycommerce:testId>
            </li>
        </c:if>

        <c:if test="${!hasNextPage}">
                 <c:choose>
                    <c:when test="${cmsPage.uid eq 'accountOrdersPage'}">
                     <li class="pagination-next disabled next-link"><span class="hidden-xs hidden-sm"><spring:theme code="pageSelectionPagination.next" /></span> &#8594;</li>
                    </c:when>
                    <c:when test="${cmsPage.uid eq 'ewalletdetailspage'}">
                     <li class="pagination-next disabled next-link"><span><spring:theme code="pageSelectionPagination.next" /></span> &#8594;</li>
                    </c:when>
                    <c:otherwise>
                     <li class="pagination-next disabled next-link">&#8594; </li>
                    </c:otherwise>
               </c:choose>
           
 
        </c:if>
    </ul>
    
    
	
</c:if>

