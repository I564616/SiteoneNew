<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${cmsPage.uid eq 'productDetails' || cmsPage.uid eq 'sds-search'}">
	<script language="javascript" type="text/javascript" charset="windows-1252" src="${commonResourcePath}/onlineopinionV5/oo_product_inline.js"></script>
</c:if>