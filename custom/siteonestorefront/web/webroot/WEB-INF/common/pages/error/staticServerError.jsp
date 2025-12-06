<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<c:set var="url" value="${request.contextPath}"/>
<c:if test="${fn:contains(url,'/en')}"><c:redirect url="/en/error"/></c:if>
<c:if test="${fn:contains(url,'/es')}"><c:redirect url="/es/error"/></c:if>
