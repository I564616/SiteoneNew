<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="enrolled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="minthreshold" required="true" type="java.lang.Float" %>
<%@ attribute name="threshold" required="true" type="java.lang.Float" %>
<%@ attribute name="tapoint" required="true" type="java.lang.Float" %>
<%@ attribute name="tvapoint" required="true" type="java.lang.Float" %>
<%@ attribute name="tapointFlag" required="true" type="java.lang.Boolean" %>
<%@ attribute name="partnerProgramPermission" required="true" type="java.lang.Boolean" %>
<%@ attribute name="isPartnerProgramAdminEmail" required="true" type="java.lang.Boolean" %>
<spring:url value="/" var="homelink"/>
	
<div class="col-md-4 p-x-0-imp">
			<div class="bg-white border-grey border-radius-4 p-x-20 patners-point-box dashboard-card" data-maxheight="338" data-minheight="65">
				<div class="patners-point-box-header">
				<c:if test="${partnerProgramPermission eq true}">
				<div class="patners-point-box-header-left">
				<span><common:headerIcon iconName="partnersprogram" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="39" height="23" display="valign-m" viewBox="0 0 39 23" /></span>
				<span class="font-Geogrotesque f-w-600 f-s-20"><spring:theme code="partners.programadmin.partners-point" /></span>
				</div>
				</c:if>
				<c:if test="${partnerProgramPermission eq false}">
				<div class="learnandexplore-box-header-left">
				<span><common:specs /></span>
				<span class="font-Geogrotesque f-w-600 f-s-20"><spring:theme code="partners.nopermission.learn" /></span>
				</div>
				</c:if>
				<div class="f-w-700 f-s-14 patners-point-box-header-right show-partnerscontent eyeopen" data-icon="eyeopen"><common:eyeopenIcon/><span>SHOW</span></div>
				<div class="f-w-700 f-s-14 patners-point-box-header-right show-partnerscontent eyeclose hidden" data-icon="eyeclose"><common:eyecloseIcon/><span>HIDE</span></div>
				</div>
				<c:if test="${enrolled eq true && partnerProgramPermission eq true}">
				<div class="m-b-20 patners-point-contain content-container hidden">
				<div class="patners-body">
					<div class="patners-point-redemm">
						<div class="patners-point-redemm-left">
						<span class="font-Geogrotesque f-w-600 f-s-20 black-title">
						<c:choose>
									<c:when test="${tapointFlag}">
										${tapoint}			
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${tapoint}" type="number" minFractionDigits="0" maxFractionDigits="0" />
									</c:otherwise>
						</c:choose>
						</span><span class="font-Geogrotesque f-w-500 f-s-12 black-title display-block"><spring:theme code="partners.programadmin.totalavailable" /></span></div>
						<div class="patners-point-redemm-right"><a href="/PartnerPerks" class="btn btn-primary btn-small f-s-14-imp"><spring:theme code="partners.programadmin.programperks" /></a></div>
					</div>
					<div class="patners-point-redemm-value">
						<div class="patners-point-redemm-value-left"><span class="font-Geogrotesque f-w-600 f-s-20 black-title"><fmt:formatNumber value="${tvapoint}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" /></span><span class="font-Geogrotesque f-w-500 f-s-12 black-title display-block"><spring:theme code="partners.programadmin.valueavailable" /></span></div>
						<div class="patners-point-redemm-value-right">
							<a href="${homelink}Promotions" class="btn btn-primary btn-small f-s-14-imp p-x-25-imp promotion-btn-padding"><spring:theme code="global.header.promotion" /></a>
						</div>
					</div>
					<div class="patners-point-redemm-threshold">
						<div class="progress-threshold full-width bg-light-gray">
							<c:set var="thresold-radius-partners" value="border-rad-left"/>
							<c:if test="${threshold eq 100}">
							<c:set var="thresold-radius-partners" value="border-radius-4" />
							</c:if>
							<span class="bg-primary ${thresold-radius-partners}" style="width:${threshold}%;"></span>
						</div>
						<div class="black-title f-s-12 f-w-600 font-Geogrotesque text-align-center"><spring:theme code="partners.programadmin.progress-toward" /> <fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" /> <spring:theme code="partners.programadmin.progress-toward-threshold" /></div>
					</div>
					</div>
					<div class="patners-point-programoverview">
					<a href="/my-account/accountPartnerProgram" class="bg-white btn btn-white f-s-18 f-w-700 full-width"><spring:theme code="partners.programadmin.programoverview" /></a>
					</div>
				</div>
				</c:if>
				<c:if test="${enrolled eq false && partnerProgramPermission eq true}">
				<div class="m-b-20 patners-point-notenrolled-container content-container hidden">
					<div class="patners-point-notenrolled-title font-Geogrotesque f-w-600 f-s-20 black-title"><spring:theme code="partners.nonuser.startearningrewards" /></div>
					<div class="patners-point-notenrolled-text f-w-400 f-s-16 p-b-20"><spring:theme code="partners.nonuser.desc" /> 
					</div>
					<div class="patners-point-notenrolled-learnmore">
					<a href="/Partners" class="bg-white btn btn-white f-s-18 f-w-700 full-width"><spring:theme code="partners.nonuser.learnmore" /> </a>
					</div>
				</div>
				</c:if>
				<c:if test="${partnerProgramPermission eq false}">
				<div class="m-b-20 patners-point-learn-container content-container hidden">
					<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/articles/business-tips" class="black-title"> <spring:theme code="partners.nopermission.tips" /></a></div>
					<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/articles/hardscapes-outdoor-living" class="black-title"><spring:theme code="partners.nopermission.hardscape" /></a></div>
					<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/articles/turf-care" class="black-title"><spring:theme code="partners.nopermission.turf" /></a></div>
					<div class="patners-point-learn-viewall">
						<a href="/articles" class="bg-white btn btn-white f-s-18 f-w-700 full-width"><spring:theme code="partners.nopermission.viewall" /></a>
					</div>
				</div>
				</c:if>
			</div>
		</div>
