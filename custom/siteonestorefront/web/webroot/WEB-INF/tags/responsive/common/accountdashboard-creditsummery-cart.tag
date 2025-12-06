<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="isAdminPermission" required="true" type="java.lang.Boolean" %>
<%@ attribute name="creditLimit" required="true" type="java.lang.String" %>
<%@ attribute name="creditBalance" required="true" type="java.lang.String" %>
<%@ attribute name="creditOTB" required="true" type="java.lang.String" %>
<div class="col-md-4 p-x-0-imp">
	<div class="bg-white border-grey border-radius-4 p-x-20 patners-point-box dashboard-card" data-maxheight="338" data-minheight="65">
		<div class="patners-point-box-header">
			<c:if test="${isAdminPermission eq true}">
				<div class="patners-point-box-header-left">
					<span>
						<common:headerIcon iconName="credit-resources" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="33" height="23" display="valign-m" viewBox="0 0 33 23" />
					</span>
					<span class="font-Geogrotesque f-w-600 f-s-20"><spring:theme code="credit.cinformation" /></span>
				</div>
			</c:if>
			<c:if test="${isAdminPermission eq false}">
				<div class="patners-point-box-header-left">
					<span>
						<common:headerIcon iconName="events" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="30" height="28" display="valign-m" viewBox="0 0 28 30" />
					</span>
					<span class="font-Geogrotesque f-w-600 f-s-20">
						<spring:theme code="credit.event" />
					</span>
				</div>
			</c:if>
			<div class="f-w-700 f-s-14 patners-point-box-header-right show-partnerscontent eyeopen" data-icon="eyeopen">
				<common:eyeopenIcon /><span>SHOW</span>
			</div>
			<div class="f-w-700 f-s-14 patners-point-box-header-right show-partnerscontent eyeclose hidden" data-icon="eyeclose">
				<common:eyecloseIcon /><span>HIDE</span>
			</div>
		</div>
		<c:if test="${isAdminPermission eq true}">
			<div class="m-b-20 patners-point-contain content-container hidden">
				<div class="patners-body">
					<div class="purchase-summery-content">
						<div class="purchase-summery-content-left f-w-400 f-s-14">
							<spring:theme code="credit.climit" />
						</div>
						<div class="purchase-summery-content-right">$${creditLimit}</div>
					</div>
					<div class="purchase-summery-content">
						<div class="purchase-summery-content-left f-w-400 f-s-14">
							<spring:theme code="credit.cbalance" />
						</div>
						<div class="purchase-summery-content-right">$${creditBalance}</div>
					</div>
					<div class="purchase-summery-content">
						<div class="purchase-summery-content-left f-w-400 f-s-14">
							<spring:theme code="credit.otb" />
						</div>
						<div class="purchase-summery-content-right">$${creditOTB}</div>
					</div>
				</div>
				<div class="patners-point-programoverview">
					<a href="/my-account/account-overview/" class="bg-white btn btn-white f-s-18 f-w-700 full-width">
						<spring:theme code="partners.nopermission.viewall" />
					</a>
				</div>
			</div>
		</c:if>
		<c:if test="${isAdminPermission eq false}">
			<div class="m-b-20 patners-point-notenrolled-container content-container hidden">
					<div class="patners-point-notenrolled-title font-Geogrotesque f-w-600 f-s-20 black-title"><spring:theme code="credit.uevent" /></div>
					<div class="patners-point-notenrolled-text f-w-400 f-s-16 p-b-20">
					<spring:theme code="credit.edesc" /> 
					</div>
					<div class="patners-point-notenrolled-learnmore">
					<a href="/events" class="bg-white btn btn-white f-s-18 f-w-700 full-width"><spring:theme code="partners.nopermission.viewall" /> </a>
					</div>
				</div>
		</c:if>

	</div>
</div>