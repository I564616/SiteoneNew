<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="login-section">
            ${component.content}
             <br>
			 <div class="login-btn col-md-4 learn-more-btn account-req-btn">
			 <div class="row"><c:url value="${component.buttonURL}" var="compButtonUrl"/>
				<a href="${compButtonUrl}" class="btn btn-default btn-block" onclick="">${component.buttonLabel}</a> 
				</div>
			</div>
			</div>
	<div class="cl visible-sm visible-xs"><br/></div>