<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTopTotals" required="false" type="java.lang.Boolean" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="additionalParams" required="false" type="java.util.HashMap" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="showCurrentPageInfo" required="false" type="java.lang.Boolean" %>
<%@ attribute name="hideRefineButton" required="false" type="java.lang.Boolean" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ attribute name="promotions" required="false" type="java.lang.Boolean" %>
<%@ attribute name="footer" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<div class="productResults">
<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
 <div class="pagination-bar ${(top)?"top":"bottom"}">
 <div class="pagination-toolbar">
                <div class="helper clearfix hidden-md hidden-lg"></div>
                       <div class="row">
                         <div class="col-xs-12">
                        <div class="col-xs-12 col-sm-5 col-md-5 pagination-wrap">
                            <pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
                                                                numberPagesShown="${numberPagesShown}"
                                                                themeMsgKey="${themeMsgKey}"/>
                        </div>
				</div>
           
        </div>
</div>
</div>
</c:if>
</div>
   
 
 