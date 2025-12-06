<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>
<%@ attribute name="searchType" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<jsp:useBean id="StringUtils" class="org.apache.commons.lang3.StringUtils" />

<c:set var="currentUrl" value="${pageContext.request.queryString}"/>


<c:if test="${(not empty facetData.values) && facetData.code eq 'socategory'}">
    <ycommerce:testId code="facetNav_title_${facetData.name}"> 
        <div class="category-tiles product__facet js-product-facet">
            <div class="row">
                <div class="col-lg-2 col-md-2 col-xs-5 category-tiles-heading">
                    ${facetData.name}<br />
                    <span><strong>${categoryFacetSelectionCount}</strong> Selected</span>
                </div>

                <div class="col-lg-10 col-md-10 col-xs-7">
                    <div class="facet js-facet" style="overflow:auto">
                    
                        <div class='facet__values js-facet-values js-facet-form' id="facets${facetData.code}">

                            <div role="group" aria-label="${facetData.code}" class="facet__list js-facet-list category_tiles curated-plp-tiles">
                            <c:set var="filterNameLength" value="0"></c:set>
                                <c:forEach items="${facetData.values}" var="facetValue" varStatus="loop">
                                    <c:if test="${(not empty facetValue.name) && (facetData.multiSelect)}">
                                        <div class="category-tile-item">
                                            <c:set var = "prodCount" value = "${facetValue.count}" />
                                            <c:set var = "fmtProdCount" value = "${fn:replace(prodCount, '.', ',')}" />
                                            <ycommerce:testId code="facetNav_selectForm">
                                            <form action="#" method="get">
                                                <input type="hidden" name="q" value="${facetValue.query.query.value}"/>
                                                <input type="hidden" name="searchtype" value="${fn:contains(currentUrl, 'searchtype=content') ? 'content' : 'product'}"/>
                                                <input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
                                                <input type="hidden" class="viewtype" name="viewtype" value="All">
                                                <input type="hidden" name="nearby" value="${param.nearby}">
                                                <input type="hidden" name="inStock" value="${param.inStock}">
                                                <input type="hidden" name="sort" value="${param.sort}">
                                                <input type="hidden" name="expressShipping" value="${expressShipping}">
                                                <label >
                                                    <input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} name="${facetData.name}" class="facet__list__checkbox js-facet-checkbox sr-only" />
                                                    <span class="facet__list__label">
                                                    <div class="category-tile-item-image ${facetValue.selected ? 'selected' : ''}">
                                                        <c:if test="${not empty facetValue.imageUrl}">
                                                            <img src="${facetValue.imageUrl}" alt="" class="img-responsive">
                                                        </c:if>
                                                    </div>
                                                        <span class="facet__list__text">
                                                            <div class="category-tile-item-heading"><c:out value="${StringUtils.abbreviate(facetValue.name, 28) }" /></div>
                                                            
                                                            <%-- <ycommerce:testId code="facetNav_count">
                                                                <div class="category-tile-item-count"><strong><spring:theme code="search.nav.facetValueCountPlain" arguments="${fmtProdCount}"/></strong>&nbsp;<spring:theme code="product.grid.itemsText"/></div>
                                                            </ycommerce:testId> --%>
                                                        </span>
                                                    </span>
                                                    
                                                </label>
                                            </form>
                                            </ycommerce:testId>
                                        
                                        </div></c:if>

                                </c:forEach>
                                <div class="category-tile-prev active">
                                    <svg width="6" height="9.29" viewBox="0 0 6 9.29" class="bi bi-chevron-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M4.154,5.8.2,1.846A.694.694,0,0,1,.2.861L.861.2a.694.694,0,0,1,.985,0L4.645,3,7.445.2a.694.694,0,0,1,.985,0l.656.656a.694.694,0,0,1,0,.985L5.136,5.8A.691.691,0,0,1,4.154,5.8Z" transform="translate(6) rotate(90)"/>
                                    </svg>
                                </div>
                                <div class="category-tile-next active">
                                    <svg width="6" height="9.29" viewBox="0 0 6 9.29" class="bi bi-chevron-right" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M4.154.2.2,4.154a.694.694,0,0,0,0,.985L.861,5.8a.694.694,0,0,0,.985,0L4.645,3l2.8,2.8a.694.694,0,0,0,.985,0l.656-.656a.694.694,0,0,0,0-.985L5.136.2A.691.691,0,0,0,4.154.2Z" transform="translate(6) rotate(90)"/>
                                    </svg>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            
            
            
        </div>
    </ycommerce:testId>
</c:if>
