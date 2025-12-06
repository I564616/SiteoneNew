<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<script src="${commonResourcePath}/js/jquery-editable-select.min.js"></script>
<div class="add-success-card-ewallet">
       <p> ${ewalletAddSuccess}</p>
 </div>
<div class="add-eroor-card-ewallet">
       <p> ${ewalletAddError}</p>
</div>
      <input type="hidden" id="add-ewallet-success" value="${ewalletAddSuccess}">
      <input type="hidden" id="add-ewallet-failure" value="${ewalletAddError}">
   