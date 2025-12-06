<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty redirect_url}"><script>window.location.replace(ACC.config.encodedContextPath + "${redirect_url}");</script></c:if>
<c:if test="${not empty customerReload}"><script>location.reload();</script></c:if>
