<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="styleClass" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="hideArrowSpan" required="false" type="java.lang.Boolean" %>
<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:theme code="search.nav.selectRefinements.title" var="selectRefinements"/>

<button class="${styleClass}" data-select-refinements-title="${selectRefinements}" style="    background: transparent;
    border: none;
    color: #414244;
    height: 40px;
    font-size: 12pt;
    float: left;
    padding: 0px;
    width: auto;
    font-weight:bold;">
    <spring:theme code="search.nav.refine.button"/><c:if test="${true ne hideArrowSpan}"><span class="icon-refine-arrow"></span></c:if>
</button>