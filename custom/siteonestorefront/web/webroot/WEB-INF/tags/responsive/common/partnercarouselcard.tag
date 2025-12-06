<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ attribute name="card" type="java.lang.String"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="item${card==1?' active':''}" id="slide-${card}">
	<div class="carousel-caption caption-hide">
		<div class="marginTop30">
			<img src="/_ui/responsive/theme-lambda/images/ppSlider${card}.svg" height="100" alt=""/>
		</div>
		<p class="f-s-28 f-s-16-xs-px font-MetronicSlabProBold bold marginTop20">
			<spring:theme code="partners.cardheader-${card}" />
		</p>
		<p class="f-s-18 m-b-0 font-14-xs">
			<spring:theme code="partners.card-details-${card}" />
			<c:if test="${card eq 7}">
				<p><a class="text-white" href="${homelink}events"><spring:theme code="partners.perks.event" /></a></p>
			</c:if>
		</p>
	</div>
</div>