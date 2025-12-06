<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="isAdminPermission" required="true" type="java.lang.Boolean" %>
<%@ attribute name="ytdSales" required="true" type="java.lang.String" %>
<%@ attribute name="lastYtdSales" required="true" type="java.lang.String" %>
<%@ attribute name="twelveMonthSales" required="true" type="java.lang.String" %>
<div class="col-md-4 p-x-0-imp">
	<div class="bg-white border-grey border-radius-4 p-x-20 patners-point-box dashboard-card" data-maxheight="338" data-minheight="65">
		<div class="patners-point-box-header">
			<c:if test="${isAdminPermission eq true}">
				<div class="patners-point-box-header-left">
					<span>
						<common:globalIcon iconName="purchasesummeryIcon" iconFill="none" iconColor="#77A12E" width="20" height="28" viewBox="0 0 20 28" />
					</span>
					<span class="font-Geogrotesque f-w-600 f-s-20 p-l-5"><spring:theme code="purchase.Psummery" /></span>
				</div>
			</c:if>
			<c:if test="${isAdminPermission eq false}">
				<div class="patners-point-box-header-left">
					<span>
						<common:headerIcon iconName="calculator" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="20" height="28" display="valign-m" viewBox="0 0 20 28" />
					</span>
					<span class="font-Geogrotesque f-w-600 f-s-20 p-l-5">
						<spring:theme code="purchase.Calculators" />
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
							<spring:theme code="purchase.YTDPurchases" />
						</div>
						<div class="purchase-summery-content-right">$${ytdSales}</div>
					</div>
					<div class="purchase-summery-content">
						<div class="purchase-summery-content-left f-w-400 f-s-14">
							<spring:theme code="purchase.LastYTDPurchases" />
						</div>
						<div class="purchase-summery-content-right">$${lastYtdSales}</div>
					</div>
					<div class="purchase-summery-content">
						<div class="purchase-summery-content-left f-w-400 f-s-14">
							<spring:theme code="purchase.12MonthPurchases" />
						</div>
						<div class="purchase-summery-content-right">$${twelveMonthSales}</div>
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
			<div class="m-b-20 patners-point-learn-container content-container hidden">
				<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/projectcalculators/voltagedrop" class="black-title">
						<spring:theme code="purchase.VDCalculators" />
					</a></div>
				<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/projectcalculators/topsoil" class="black-title">
						<spring:theme code="purchase.TFCalculators" />
					</a></div>
				<div class="patners-point-learn-row f-w-400 f-s-14"><a href="/projectcalculators/holidaylighting" class="black-title">
						<spring:theme code="purchase.HLCalculators" />
					</a></div>
				<div class="patners-point-learn-viewall">
					<a href="/projectcalculators" class="bg-white btn btn-white f-s-18 f-w-700 full-width">
						<spring:theme code="partners.nopermission.viewall" />
					</a>
				</div>
			</div>
		</c:if>
	</div>
</div>