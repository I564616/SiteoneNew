<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>

<c:set var="curatedplpHScroll" value="false" />
<div id="desktophorizondalscroll" class="product__list--wrapper hidden-xs hidden-sm">
	<div id="productTab">
		<div class="row">
			<div class="col-md-12 paddingTop flex">
				<c:if test="${not empty productBannerDesktop.url}">
					<div class="col-xs-12 col-5th col-sm-6 curatedplp-horizontal-banner paddingTop flex">
						<img src="${productBannerDesktop.url}" class="img-responsive"
							alt="${productBannerDesktop.altText}" />
						<div class="curatedplp-horizontal-text">
							<c:if test="${not empty title}">
								<div class="col-md-12 curatedplp-horizontal-title">${title}</div>
							</c:if>
							<c:if test="${not empty headline}">
								<div class="col-md-12 curatedplp-horizontal-headline">${headline}</div>
							</c:if>
							<c:if test="${not empty description}">
								<div class="col-md-12 curatedplp-horizontal-description">
									<c:choose>
										<c:when test="${fn:length(description) > 210}">
											<c:out value="${fn:substring(description, 0, 210)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${description}" />
										</c:otherwise>
									</c:choose>
								</div>
							</c:if>
						</div>
						<c:if test="${not empty buttonLabel}">
							<div class="col-md-12 curatedplp-horizontal-buttonLabel">
								<c:url value="${buttonURL}" var="buttonURLnew" />
								<a href="${buttonURLnew}" class="btn btn-primary btn-block stoneCenter-btn curated-promo-btn" onclick="">${buttonLabel}</a>
							</div>
						</c:if>
					</div>
				</c:if>
				<div class="col-sm-12 col-4-5th block curated-scroll-plp">
					<div class="paddingTop row">
						<div class="product__listing product__grid block ${product.multidimensional && product.variantCount > 1?'curatedplp-horizontal-banner-wrapper':''}">
							<c:forEach var="productItem" items="${productList}" varStatus="status">
								<div class="category-tile-item category-tile-item-curatedplp ${status.index eq 0?'':'margin-l-5'}">
									<div class="product-item print col-xs-12 col-sm-6 col-md-12" data-product-id="${product.code}" data-cardvariantcount="${product.variantCount}">
										<div class="plp-card product-item-box">
											<product:productListerGridItem product="${productItem}" loop="${status.index}" />
											<c:if test="${status.index > 3}">
												<c:set var="curatedplpHScroll" value="true" />
											</c:if>
										</div>
									</div>
								</div>
							</c:forEach>
							<c:if test="${curatedplpHScroll}">
								<div class="curated-horizontal-prev active">
									<svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
										<circle cx="14" cy="14" r="14" transform="matrix(-1 0 0 1 28 0)" fill="#78A22F" />
										<path d="M11.0302 13.1671L14.595 10.0931C14.8387 9.88296 15.2358 9.88296 15.4794 10.0931L16.0751 10.6067C16.3187 10.8168 16.3187 11.1592 16.0751 11.3694L13.5481 13.5484L16.0751 15.7274C16.3187 15.9375 16.3187 16.28 16.0751 16.4901L15.4794 17.0037C15.2358 17.2138 14.8387 17.2138 14.595 17.0037L11.0212 13.9297C10.7775 13.7196 10.7775 13.3772 11.0212 13.1671H11.0302Z" fill="white" />
									</svg>
								</div>
								<div class="curated-horizontal-next active">
									<svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
										<circle cx="14" cy="14" r="14" fill="#78A22F" />
										<path d="M16.9698 13.1671L13.405 10.0931C13.1613 9.88296 12.7642 9.88296 12.5206 10.0931L11.9249 10.6067C11.6813 10.8168 11.6813 11.1592 11.9249 11.3694L14.4519 13.5484L11.9249 15.7274C11.6813 15.9375 11.6813 16.28 11.9249 16.4901L12.5206 17.0037C12.7642 17.2138 13.1613 17.2138 13.405 17.0037L16.9788 13.9297C17.2225 13.7196 17.2225 13.3772 16.9788 13.1671H16.9698Z" fill="white" />
									</svg>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test="${not empty productBannerDesktop.url}">
	<div class="col-xs-12 col-sm-12 col-5th promo-wrapper hidden-md hidden-lg">
		<c:if test="${not empty productBannerDesktop.url}">
			<div class="col-xs-12 col-sm-12 curatedplp-horizontal-banner paddingTop">
				<img src="${productBannerMobile.url}" class="img-responsive" alt="${productBannerMobile.altText}" />
				<div class="curatedplp-horizontal-text">
					<c:if test="${not empty title}">
						<div class="col-xs-12 col-sm-12 curatedplp-horizontal-title">${title}</div>
					</c:if>
					<c:if test="${not empty headline}">
						<div class="col-xs-12 col-sm-12 curatedplp-horizontal-headline">${headline}</div>
					</c:if>
					<c:if test="${not empty description}">
						<div class="col-xs-12 col-sm-12 curatedplp-horizontal-description">
							<c:choose>
								<c:when test="${fn:length(description) > 100}">
									<c:out value="${fn:substring(description, 0, 100)}..." />
								</c:when>
								<c:otherwise>
									<c:out value="${description}" />
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
				<c:if test="${not empty buttonLabel}">
					<div class="col-xs-12 col-sm-12 curatedplp-horizontal-buttonLabel">
						<c:url value="${buttonURL}" var="buttonURLnew" />
						<a href="${buttonURLnew}" class="btn btn-primary btn-block stoneCenter-btn curated-promo-btn" onclick="">${buttonLabel}</a>
					</div>
				</c:if>
			</div>
		</c:if>
	</div>

	<div class="col-xs-12 col-sm-12 col-4-5th block curated-scroll-plp hidden-lg hidden-md">
		<div class="paddingTop row">
			<div class="col-xs-12 product__listing product__grid block">
				<c:forEach var="productItem" items="${productList}" varStatus="status">
					<div class="category-tile-item category-tile-item-curatedplp ${status.index eq 0?'':'m-l-15-xs'}">
						<div class="product-item print col-xs-12 col-sm-6 col-md-12" data-product-id="${product.code}" data-cardvariantcount="${product.variantCount}">
							<div class="plp-card product-item-box">
								<product:productListerGridItem product="${productItem}" loop="${status.index}" />
								<c:if test="${status.index > 3}">
									<c:set var="curatedplpHScroll" value="true" />
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
				<c:if test="${curatedplpHScroll}">
					<div class="category-tile-prev active hidden">
						<svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
							<circle cx="14" cy="14" r="14" transform="matrix(-1 0 0 1 28 0)" fill="#78A22F" />
							<path d="M11.0302 13.1671L14.595 10.0931C14.8387 9.88296 15.2358 9.88296 15.4794 10.0931L16.0751 10.6067C16.3187 10.8168 16.3187 11.1592 16.0751 11.3694L13.5481 13.5484L16.0751 15.7274C16.3187 15.9375 16.3187 16.28 16.0751 16.4901L15.4794 17.0037C15.2358 17.2138 14.8387 17.2138 14.595 17.0037L11.0212 13.9297C10.7775 13.7196 10.7775 13.3772 11.0212 13.1671H11.0302Z" fill="white" />
						</svg>
					</div>
					<div class="category-tile-next active hidden">
						<svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
							<circle cx="14" cy="14" r="14" fill="#78A22F" />
							<path d="M16.9698 13.1671L13.405 10.0931C13.1613 9.88296 12.7642 9.88296 12.5206 10.0931L11.9249 10.6067C11.6813 10.8168 11.6813 11.1592 11.9249 11.3694L14.4519 13.5484L11.9249 15.7274C11.6813 15.9375 11.6813 16.28 11.9249 16.4901L12.5206 17.0037C12.7642 17.2138 13.1613 17.2138 13.405 17.0037L16.9788 13.9297C17.2225 13.7196 17.2225 13.3772 16.9788 13.1671H16.9698Z" fill="white" />
						</svg>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</c:if>