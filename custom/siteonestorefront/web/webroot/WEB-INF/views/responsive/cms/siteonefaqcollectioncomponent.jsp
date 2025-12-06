<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<div class="col-md-12">
<div class="row faq-sec">
	<h3 class="store-specialty-heading faq-content-heading"><b>${component.heading}</b></h3>
 
	<div class="panel-group" id="accordion">
		<c:forEach items="${component.faq}" var="faqComponents"	varStatus="loop">
			<div class="panel panel-default faq-accordion">
				<div class="panel-heading faq-panel">
					<div class="panel-title h3-heading no-padding">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#${faqComponents.uid}" style="text-decoration: none;"> <span class="panel-ques">
							${faqComponents.question}</span>
							
							<span class="pull-right faq-arrow"><span class="glyphicon glyphicon-triangle-bottom arrowDown" style="display:none"></span>
							<span class="glyphicon glyphicon-triangle-right arrowRight"></span>
							</span>
						 
							</a>
							
					</div>
				</div>
				<div id="${faqComponents.uid}" class="panel-collapse collapse">
				 <c:set var="mime" value="${faqComponents.image.mime }"/>
				 <c:set var="type" value ="${fn:split(mime, '/')[0]}" />
				<p>${faqComponents.answer}
				<c:if test="${not empty faqComponents.image}">
				<c:choose>
					<c:when test="${type eq 'image'}">
						<br/><br/><br/><img src="${faqComponents.image.url}" height="auto" width="100%" alt="${faqComponents.image.altText}"/><br/><br/>
					</c:when>
					<c:otherwise>
					<video width="500" height="200" controls>
 						 <source src="${faqComponents.image.url}" type="${faqComponents.image.mime}">
					</video>
					</c:otherwise>
					</c:choose>
					</c:if>
					</p>
					
				</div>
			</div>
			
		</c:forEach>
		<br>
	</div>
	</div>
</div>