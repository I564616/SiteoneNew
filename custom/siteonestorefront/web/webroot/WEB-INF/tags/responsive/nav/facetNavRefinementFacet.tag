<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>
<%@ attribute name="searchType" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="currentUrl" value="${pageContext.request.queryString}"/>
<c:if test="${not (facetData.code eq 'soproductBrandNameFacet' || facetData.code eq 'price' || facetData.code eq 'socategory' || facetData.code eq 'sopriorityBrandNameFacet' || facetData.code eq 'socouponFacet')}">
	<ycommerce:testId code="facetNav_title_${facetData.name}">
		<div class="facet js-facet ${facetData.code eq 'socouponFacet'?'active':''}" style="overflow:auto">
			<c:if test="${facetData.code ne 'socouponFacet'}">
				<div class="facet__name js-facet-name" data-toggle="" data-target="#facets${facetData.code}">
					<c:choose>
						<c:when test="${facetData.code eq 'soisShippable'}">
							<spring:theme code="facet.navigation.page.shipping" />
						</c:when>
						<c:otherwise>
							${facetData.name}
						</c:otherwise>
					</c:choose>
					<span class="filter-toggle-down pull-right"><common:filter-chevron-down iconColor="#000" /></span>
					<span class="filter-toggle-up pull-right"><common:filter-chevron-up iconColor="#000" /></span>
				</div>
			</c:if>
			<div class="facet__values js-facet-values js-facet-form" id="facets${facetData.code}">
				<ul role="group" aria-label="${facetData.code}" class="facet__list js-facet-list">
					<c:set var="filterNameLength" value="0"></c:set>
					<c:forEach items="${facetData.values}" var="facetValue" varStatus="loop">
						<c:if test="${loop.index < 10}">
							<c:if test="${facetData.multiSelect}">
								<li>
									<ycommerce:testId code="facetNav_selectForm">
										<form action="#" method="get">
											<input type="hidden" name="q" value="${facetValue.query.query.value}" />
											<input type="hidden" name="searchtype" value="${fn:contains(currentUrl, 'searchtype=content') ? 'content' : 'product'}" />
											<input type="hidden" name="text" value="${searchPageData.freeTextSearch}" />
											<input type="hidden" class="viewtype" name="viewtype" value="All">
											<input type="hidden" name="nearby" value="${param.nearby}">
											<input type="hidden" name="inStock" value="${param.inStock}">
											<input type="hidden" name="sort" value="${param.sort}">
											<label>
												<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : '' } name="${facetData.name}"
													class="facet__list__checkbox js-facet-checkbox js-facet-store-checkbox sr-only" />
												<span class="facet__list__label">
													<span class="facet__list__mark"></span>
													<span class="facet__list__text">
														<span class="facetWordOverflow">
															<span>${fn:escapeXml(facetValue.name)}</span>
															<ycommerce:testId code="facetNav_count">
																<span class="facet__value__count">
																	${facetValue.count}
																</span>
															</ycommerce:testId>
														</span>
													</span>
												</span>
											</label>
										</form>
									</ycommerce:testId>
								</li>
							</c:if>
						</c:if>
						<c:if test="${loop.index >= 10}">
							<c:if test="${facetData.multiSelect}">
								<li class="hidden normal">
									<ycommerce:testId code="facetNav_selectForm">
										<form action="#" method="get">
											<input type="hidden" name="q" value="${facetValue.query.query.value}" />
											<input type="hidden" name="searchtype" value="${fn:contains(currentUrl, 'searchtype=content') ? 'content' : 'product'}" />
											<input type="hidden" name="text" value="${searchPageData.freeTextSearch}" />
											<input type="hidden" class="viewtype" name="viewtype" value="All">
											<input type="hidden" name="nearby" value="${param.nearby}">
											<input type="hidden" name="inStock" value="${param.inStock}">
											<input type="hidden" name="sort" value="${param.sort}">
											<label>
												<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : '' } name="${facetData.name}"
													class="facet__list__checkbox js-facet-checkbox sr-only" />
												<span class="facet__list__label">
													<span class="facet__list__mark"></span>
													<span class="facet__list__text">
														<span class="facetWordOverflow">
															<span>${fn:escapeXml(facetValue.name)}</span>
															<ycommerce:testId code="facetNav_count">
																<span class="facet__value__count">
																	${facetValue.count}
																</span>
															</ycommerce:testId>
														</span>
													</span>
												</span>
											</label>
										</form>
									</ycommerce:testId>
								</li>
							</c:if>
						</c:if>
						<c:if test="${loop.index < 10}">
							<c:if test="${not facetData.multiSelect}">
								<li>
									<c:url value="${facetValue.query.url}" var="facetValueQueryUrl" />
									<span class="facet__text">
										<a href="${facetValueQueryUrl}&viewtype=All" class="namevalue">${facetValue.name}</a>&nbsp;
										<ycommerce:testId code="facetNav_count">
											<span class="facet__value__count">
												${facetValue.count}
											</span>
										</ycommerce:testId>
									</span>
								</li>
							</c:if>
						</c:if>
						<c:if test="${loop.index >= 10}">
							<c:if test="${not facetData.multiSelect}">
								<li class="hidden normal1">
									<c:url value="${facetValue.query.url}" var="facetValueQueryUrl" />
									<span class="facet__text">
										<a href="${facetValueQueryUrl}&viewtype=All">${facetValue.name}</a>&nbsp;
										<ycommerce:testId code="facetNav_count">
											<span class="facet__value__count">
												${facetValue.count}
											</span>
										</ycommerce:testId>
									</span>
								</li>
							</c:if>
						</c:if>
						<c:if test="${loop.index eq 9}">
							<c:if test="${not empty facetValue.name}">
								<c:choose>
									<c:when test="${fn:length(facetValue.name) gt 30}">
										<c:set var="filterNameLength" value="30"></c:set>
									</c:when>
									<c:when test="${fn:length(facetValue.name) gt 25}">
										<c:set var="filterNameLength" value="25"></c:set>
									</c:when>
									<c:when test="${fn:length(facetValue.name) gt 20}">
										<c:set var="filterNameLength" value="20"></c:set>
									</c:when>
									<c:when test="${fn:length(facetValue.name) gt 17}">
										<c:set var="filterNameLength" value="17"></c:set>
									</c:when>
								</c:choose>
							</c:if>
						</c:if>
						<c:if test="${loop.index eq 10}">
							<c:set var="showMoreSearchFilter" value="true"></c:set>
						</c:if>
					</c:forEach>
				</ul>
				<c:if test="${showMoreSearchFilter}">
					<a class="hideShowMoreLink c${filterNameLength}" href="#" data-state="expand">
						<spring:theme code="review.show.all" />
					</a>
				</c:if>
			</div>
		</div>
	</ycommerce:testId>
</c:if>
