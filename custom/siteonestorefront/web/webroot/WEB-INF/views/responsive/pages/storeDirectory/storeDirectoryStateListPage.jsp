<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>


<template:page pageTitle="${pageTitle}">
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="row">
				<div class="col-md-10 col-xs-12">
					<h1 class="headline">
						<spring:theme code="store.directory.name.label" />
					</h1>
					<p>
						<spring:theme code="store.directory.branch.message" />
					</p>
				</div>


				<div class="col-md-2 col-xs-12">
				<c:url value="/store-finder" var="storefinder"/>
					<form action="${storefinder}">

						<button type="submit" class="btn btn-block btn-primary">
							<spring:theme code="store.directory.branch.label" />
						</button>
					</form>
				</div>
			</div>
			<div class="cl"></div>

			<br />
			<br />
			<br />
			<div class="store-specialty-heading">
				<b>
					<c:choose>
						<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
							<spring:theme code="store.directory.country.label.ca" />
						</c:when>
						<c:otherwise>
							<spring:theme code="store.directory.country.label" />
						</c:otherwise>
					</c:choose>
				</b>
			</div>
			<div class="item-sec-border"></div>
			<br/>
			<c:set var="totalLength" value="${regions.size()}" />

			<div class="col-md-3 col-xs-12">
				<c:forEach var="region" items="${regions}" begin="0"
					end="${totalLength gt 13 ? 12 : totalLength }">
					<div class="row">
						<c:choose>

							<c:when test="${region.value eq true}">
								<a href="<c:url value="/store-directory/${region.key.isocode}"/>">${region.key.name}
									(${region.key.storeCount})</a>
							</c:when>
							<c:otherwise>
                  ${region.key.name} (0)
                  </c:otherwise>
						</c:choose>
					</div>


				</c:forEach>
			</div>




			<div class="col-md-3 col-xs-12">
				<c:forEach var="region" items="${regions}" begin="13"
					end="${totalLength gt 26 ? 25 : totalLength }">

					<div class="row">
						<c:choose>

							<c:when test="${region.value eq true}">
								<a href="<c:url value="/store-directory/${region.key.isocode}"/>">${region.key.name}
									(${region.key.storeCount})</a>
							</c:when>
							<c:otherwise>
                  ${region.key.name} (0)
                  </c:otherwise>
						</c:choose>
					</div>


				</c:forEach>

			</div>



			<div class="col-md-3 col-xs-12">
				<c:forEach var="region" items="${regions}" begin="26"
					end="${totalLength gt 39 ? 38 : totalLength }">

					<div class="row">
						<c:choose>

							<c:when test="${region.value eq true}">
								<a href="<c:url value="/store-directory/${region.key.isocode}"/>">${region.key.name}
									(${region.key.storeCount})</a>
							</c:when>
							<c:otherwise>
                  ${region.key.name} (0)
                  </c:otherwise>
						</c:choose>
					</div>


				</c:forEach>

			</div>

			<div class="col-md-3 col-xs-12">
				<c:forEach var="region" items="${regions}" begin="39"
					end="${totalLength gt 52 ? 51 : totalLength }">

					<div class="row">
						<c:choose>

							<c:when test="${region.value eq true}">
								<a href="<c:url value="/store-directory/${region.key.isocode}"/>">${region.key.name}
									(${region.key.storeCount})</a>
							</c:when>
							<c:otherwise>
                  ${region.key.name} (0)
                  </c:otherwise>
						</c:choose>
					</div>


				</c:forEach>

			</div>








			<div class="cl"></div>
		</div>
		<div class="cl"></div>
	</div>
</template:page>