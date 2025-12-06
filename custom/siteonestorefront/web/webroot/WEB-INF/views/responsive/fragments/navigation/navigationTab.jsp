<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



<ul>
<c:forEach var="level1" items="${categories}" >
<li><a href="${level1.url}">${level1.name}</a></li><ul>
    <c:forEach var="level2" items="${level1.subCategories}">
       <li> <a href="${level2.url}">${level2.name}</a></li><ul>
     	<c:forEach var="level3" items="${level2.subCategories}">
<li><a href="${level3.url}">${level3.name}</a></li>
    		</c:forEach></ul>
    	</c:forEach>
    	</ul>
</c:forEach>
</ul>

</body>
</html>