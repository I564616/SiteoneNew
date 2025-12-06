<%@ attribute name="display" required="false" type="java.lang.String" %>
<%@ attribute name="iconName" required="false" type="java.lang.String" %>
<%@ attribute name="iconFill" required="false" type="java.lang.String" %>
<%@ attribute name="iconColor" required="false" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>
<%@ attribute name="viewBox" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="iconColor" value="${iconColor ne null?iconColor:'#78A22F'}" />
<svg xmlns="http://www.w3.org/2000/svg" width="${width ne null?width:'23'}" height="${height ne null?height:'5'}" viewBox="${viewBox ne null?viewBox:'0 0 23 5'}" fill="${iconFill ne null?iconFill:'none'}" class="${display ne null?display:''}" >
   <c:choose>
      <c:when test="${iconName == ''}">
         
      </c:when>
      <c:otherwise>
            <circle cx="2.5" cy="2.5" r="2.5" transform="matrix(-1 0 0 1 5 0)" fill="#78A22F" />
            <circle cx="2.5" cy="2.5" r="2.5" transform="matrix(-1 0 0 1 14 0)" fill="#78A22F" />
            <circle cx="2.5" cy="2.5" r="2.5" transform="matrix(-1 0 0 1 23 0)" fill="#78A22F" />
      </c:otherwise>
   </c:choose>
</svg>