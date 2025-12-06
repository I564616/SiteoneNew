<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
	
	<div class="dropdown2 hidden-xs hidden-sm">
		<div class="dropbtn2">
			<a href="${component.url}" class ="nav-click">${component.code}</a>
		</div> 
		<div class="dropdown-content2">
			<c:forEach items="${component.navSubCategory}" var="category">
			<a href="${category.url}" class ="nav-click">${category.name}</a>
			</c:forEach> 
		</div>
	</div>
	<div class="main-navbar-cplp hidden-md hidden-lg">
		<div class="mobile-navbar-angle dropdown2 hidden">
			<button onclick="ACC.product.navigateButton(-1)" class="btn btn-default cplp-angle-down"><common:angle-down></common:angle-down></button>
			<div class="dropbtn2">
				<a href="${component.url}" class ="nav-click">${component.code}</a>
			</div> 
			<div class="dropdown-content2">
				<c:forEach items="${component.navSubCategory}" var="category">
				<a href="${category.url}" class ="nav-click">${category.name}</a>
				</c:forEach> 
			</div>
			<button onclick="ACC.product.navigateButton(1)" class="bth bth-default cplp-angle-up"><common:angle-up></common:angle-up>	</button>
		</div>	
	</div>
	
	