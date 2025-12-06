<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="enrolled" required="false" %>
<%@ attribute name="ppemail" required="false" %>
<%@ attribute name="retail" required="false" %>
<%@ attribute name="partnerMessage" required="false" %>
<%@ attribute name="ppurl" required="false" %>
<c:choose>
	<c:when test="${enrolled eq true}">
		<c:choose>
			<c:when test="${ppemail eq true}">
				<p><spring:theme code="partners.point.currentyear" />: <span class="bold"><spring:theme code="partners.point.enrolled" /></span></p>
				<button id="partnerProgramLink" class="btn btn-primary btn-block" data-partner-message="${partnerMessage}" data-ppurl="${ppurl}"><spring:theme code="partners.point.redeem" /></button>			
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${currentBaseStoreId eq 'siteoneCA' }" >
						<p><spring:theme code="partners.point.currentyear" />: <span class="bold"><spring:theme code="partners.point.enrolled" /></span></p>
						<a class="btn btn-primary btn-block" href="${caPartnersPointsReedem}"><spring:theme code="partners.point.redeem" /></a>
					</c:when>
					<c:otherwise>
						<button class="btn btn-block margin-top-20 gray-disabled-btn js-info-tootip flex-center justify-center" rel="custom-tooltip">		
							<common:exclamatoryIcon iconColor="#fff" width="20" height="20" />
							<span class="pad-lft-10"><spring:theme code="partners.point.redeem" /></span>
							<span class="tooltip-content hide"><spring:theme code="redeem.point.tooltip.text" /></span>
						</button>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${retail eq true}">
				<button class="btn btn-block margin-top-20 gray-disabled-btn js-info-tootip flex-center justify-center" rel="custom-tooltip">
					<common:exclamatoryIcon iconColor="#fff" width="20" height="20" />
					<span class="pad-lft-10"><spring:theme code="partners.program.enroll" /></span>
					<span class="tooltip-content hide"><spring:theme code="hide.enroll.ppbtn" /></span>
				</button>
			</c:when>
			<c:otherwise>
				<c:if test="${currentBaseStoreId ne 'siteoneCA' }" >
				<button class="btn btn-primary btn-block marginTop20" onclick="ACC.partner.enrollModal('confirm','true')"><spring:theme code="partners.point.enroll" /></button>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
