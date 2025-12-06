<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>


<template:page pageTitle="${pageTitle}">

		<div class="row">
			<div class="cl margin20"></div>
			<div class="col-md-12">
				<cms:pageSlot position="BodyContent" var="feature" element="div" class="">
					<cms:component component="${feature}" element="div" class="" />
				</cms:pageSlot>

				<br>
				
				<cms:pageSlot position="CustInfo" var="feature" element="div" class="">
					<cms:component component="${feature}" element="div" class="" />
				</cms:pageSlot>
			</div>
			<div class="col-sm-12 col-md-9"></div>
			<div class="cl margin20"></div>
			<div class="col-sm-12 col-md-2 col-sm-4 col-xs-12 homeowner-success-button">
				<button class="homeowner btn btn-primary btn-block  js-continue-shopping-button " 
					data-continue-shopping-url="/"
					>
                <spring:theme code="return.to.homepage"/>
            </button>
			</div>
		</div>
</template:page>
