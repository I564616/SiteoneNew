<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<h1 class="headline"><spring:theme code="resetPwd.title" var="pageTitle"/></h1>
<template:page pageTitle="${pageTitle}">
	<user:updatePwd/>
</template:page>
