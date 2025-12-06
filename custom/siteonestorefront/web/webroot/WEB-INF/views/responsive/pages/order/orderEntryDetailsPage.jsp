<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<c:forEach items="${orderData.entries}" var="entry" varStatus="loop">	                       
	<order:accountOrderEntryDetails orderEntry="${entry}" order="${orderData}" itemIndex="${loop.index}"/> 
</c:forEach> 