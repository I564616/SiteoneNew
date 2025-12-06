<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:if test="${listOfShipTo.size() gt 2}">
    <c:set var="shipToopenPopup" value="${idKey == 'shipToSelectionAccountType' }" />
</c:if>
<c:set var="temp" value="true" />
<c:set var="hardscapeToggle" value="${hardscapeFeatureSwitch}" />
<c:forEach items="${sessionNearbyStores}" var="nearbyStoreValue" varStatus="loop" step="1" begin="1">
    <c:if test="${nearbyStoreValue.isNearbyStoreSelected}">
        <c:set var="nearbyStoresToggle" value="${loop.first?'' : nearbyStoresToggle}${nearbyStoreValue.storeId}${loop.last?'' :','}" />
    </c:if>
</c:forEach>
<c:if test="${component.visible}">
	<input id="pageType" type="hidden" value="${cmsPage.name}" />
	<ul class="L1CategoryHeader margin0 padding0 full-height scroll-bar-5">
		<c:forEach items="${globalProductNodes}" var="level1">
			<c:if test="${(not empty level1) && (level1 != null) && (level1.visible)}">
				<li class="L1Categories">
					<c:url var="levela" value="${level1.url}" />
					<c:if test="${(not empty level1.category.productCount) && (level1.category.productCount != null) && (level1.category.productCount gt 0)}">
						<button data-global-linkname="categories: ${level1.category.name}" onclick="ACC.global.categories(this, '${level1.category.code}', '.L2CategoryHeader')" data-code="${level1.category.code}" class="btn btn-block text-align-left L1CatLinks text-default f-w-500 f-s-18 b-b-grey transition-3s" data-href="${levela}">${level1.category.name}</button>
					</c:if>
				</li>
				<li class="L1Categories-data hidden">
					<c:forEach var="level2" items="${level1.children}">
						<c:if test="${(not empty level2) && (level2 != null) && (level2.visible)}">
							<c:if test="${(not empty level2.category.productCount) && (level2.category.productCount != null) && (level2.category.productCount gt 0)}">
								<c:url var="levelb" value="${level2.url}" />
								<c:set var="hardscapecategorytoggle" value="false" />
								<c:forTokens items="${hardscapeFeatureSwitch}" delims="," var="hardscapecategory">
									<c:if test="${fn:contains(level2.category.code,hardscapecategory)}">
										<c:set var="hardscapecategorytoggle" value="true" />
									</c:if>
								</c:forTokens>
								<c:choose>
									<c:when test="${hardscapecategorytoggle}">
										<span class="L2CatLinks" data-${level1.category.code}="${level2.category.code}" data-href="${levelb}/?q=%3Arelevance&viewtype=All&inStock=on&nearby=on&selectedNearbyStores=${nearbyStoresToggle}" data-children="${not empty level2.children}">${level2.category.name}</button>
									</c:when>
									<c:otherwise>
										<span class="L2CatLinks" data-${level1.category.code}="${level2.category.code}" data-href="${levelb}" data-children="${not empty level2.children}">${level2.category.name}</span>
									</c:otherwise>
								</c:choose>
								<c:if test="${not empty level2.children}">
									<c:forEach var="level3" items="${level2.children}" varStatus="i">
										<c:if test="${not empty level3 && (level3 != null) && (level3.visible)}">
											<c:if test="${(not empty level3.category.productCount) && (level3.category.productCount != null) && (level3.category.productCount gt 0)}">
												<c:url var="levelc" value="${level3.url}" />
												<c:set var="hardscapecategorytoggle" value="false" />
												<c:forTokens items="${hardscapeFeatureSwitch}" delims="," var="hardscapecategory">
													<c:if test="${fn:contains(level3.category.code,hardscapecategory)}">
														<c:set var="hardscapecategorytoggle" value="true" />
													</c:if>
												</c:forTokens>
												<c:choose>
													<c:when test="${hardscapecategorytoggle}">
														<span class="L3CatLinks" data-${level2.category.code}="${level3.category.code}" data-href="${levelc}/?q=%3Arelevance&viewtype=All&inStock=on&nearby=on&selectedNearbyStores=${nearbyStoresToggle}">${level3.category.name}</span>
													</c:when>
													<c:otherwise>
														<span class="L3CatLinks" data-${level2.category.code}="${level3.category.code}" data-href="${levelc}">${level3.category.name}</span>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:if>
									</c:forEach>
								</c:if>
							</c:if>
						</c:if>
					</c:forEach>
				</li>
			</c:if>
		</c:forEach>
	</ul>
</c:if>
