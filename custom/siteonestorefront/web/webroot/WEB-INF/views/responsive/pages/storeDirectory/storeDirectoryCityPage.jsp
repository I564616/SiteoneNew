<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<template:page pageTitle="${pageTitle}">
	<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="row">
		<div class="col-md-12 col-xs-12">

			<div class="row">
				<div class="col-md-9 col-sm-9"> <h1 class="headline">${stateName}</h1>
				<div class="cl"></div>
				 
				<div class="margin20">
						<p><spring:theme code="store.directory.city.label" /></p>
				</div>
				 </div>
 
				<div class="col-md-2 col-sm-3 col-xs-12 col-md-offset-1"><form action="${homelink}store-finder">
					 <button type="submit" class="btn btn-primary btn-block btn-margin"><spring:theme code="storeDirectoryCityPage.find.branch" /></button> 
				</form>
				</div>
			</div>
			<div class="cl"></div>
			
				<div class="cl"></div>
				<br/>
			 <div class="marginTopBVottom30">
				
			<div class="item-sec-border"></div>
			</div>
			<div class="row">
			<c:set var="alphabet"
				value="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z" />

          


			<c:forTokens var="letter" items="${alphabet}" delims=",">
				<c:choose>

					<c:when test="${not empty storeCitiesGroups[letter]}">
						<div class="col-xs-12 store-alphabet">
							<b><a class="" href="#${letter}">${letter}</a></b>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-xs-6 store-alphabet">
							<b>${letter}</b></div>
					</c:otherwise>
				</c:choose>

			</c:forTokens>
			<div class="cl"></div>
			<br/><br/>
			<c:set var="storeCitiesCount" value="${storeCitiesGroups.size()}" />
			<c:set var="largeCount" value="0" />
			<c:set var="largeColumnsBfrFrmt" value="${storeCitiesCount/4}"/>
			<fmt:formatNumber var="largeColumnsFmt" value="${largeColumnsBfrFrmt}" maxFractionDigits="0" />
			<c:set var="largeColumns" value="${largeColumnsFmt}"/>
			 <c:if test="${largeColumnsBfrFrmt > largeColumnsFmt}">
                 <c:set var="largeColumns" value="${largeColumns + 1}"/>
             </c:if>
             <c:set var="indexLarge" value="0"/>
             <div class="col-md-3 col-xs-12">
 			<c:forEach var="storeCitiesGroup" items="${storeCitiesGroups}">
 				<c:choose>
 					<c:when test="${(storeCitiesCount != 5) && (storeCitiesCount != 6) && (storeCitiesCount != 7) && (storeCitiesCount != 9)}">
	 					<c:set var="startoverLarge" value="${indexLarge % largeColumns}"/>
	 				
		 				<c:choose>
		 					<c:when test="${startoverLarge == 0 && indexLarge != 0}">
		 						</div>
			                      <div class="col-md-3 col-xs-12">
			                      <div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                      <c:forEach var="city" items="${storeCitiesGroup.value}">
										<div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								 </c:forEach>
								 <BR>
			                      </div>
		 					</c:when>
		 					<c:otherwise>
		 						<div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                           <c:forEach var="city" items="${storeCitiesGroup.value}">
									   <div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								</c:forEach>
								<BR>
			                       </div>
		 					</c:otherwise>
		 				</c:choose>
	 					<c:set var="indexLarge" value="${indexLarge+1}"/>
 					</c:when>
 					
	 				<c:otherwise>
	 					 <c:if test="${storeCitiesCount == 5}">
	 					 	<c:set var="startoverLarge" value="${indexLarge % largeColumns}"/>
	 					 		<c:choose>
	                                <c:when test="${(startoverLarge == 0 && indexLarge != 0) || indexLarge == 3}">
	                                 </div>
	                                 <div class="col-md-3 col-xs-12">
			                      <div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                      <c:forEach var="city" items="${storeCitiesGroup.value}">
										<div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								 </c:forEach>
								  <BR>
			                      </div>
		 							</c:when>
		 							<c:otherwise>
		 							<div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                           <c:forEach var="city" items="${storeCitiesGroup.value}">
									   <div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								</c:forEach>
								 <BR>
			                       </div>
		 						 </c:otherwise>
	                           </c:choose>
	                           <c:set var="indexLarge" value="${indexLarge+1}"/>
	 					 </c:if>
	 					 
	 					 <c:if test="${storeCitiesCount == 6}">
	 					 	<c:set var="startoverLarge" value="${indexLarge % largeColumns}"/>
	 					 		<c:choose>
	 					 			<c:when test="${(startoverLarge == 0 && indexLarge != 0) || indexLarge == 5}">
	 					 			</div>
	 					 				<div class="col-md-3 col-xs-12">
			                      <div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                      <c:forEach var="city" items="${storeCitiesGroup.value}">
										<div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								 </c:forEach>
								 <BR>
			                      </div>
	 					 			</c:when>
	 					 			<c:otherwise>
		 							<div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                           <c:forEach var="city" items="${storeCitiesGroup.value}">
									   <div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								</c:forEach>
								<BR>
			                       </div>
		 						 </c:otherwise>
	 					 		</c:choose>
	 					 		<c:set var="indexLarge" value="${indexLarge+1}"/>
	 					 </c:if>
	 					 
	 					 <c:if test="${storeCitiesCount == 7}">
	 					 	<c:set var="startoverLarge" value="${indexLarge % largeColumns}"/>
	 					 		<c:choose>
	 					 			<c:when test="${(startoverLarge == 0 && indexLarge != 0) || indexLarge == 6}">
	 					 			</div>
	 					 				<div class="col-md-3 col-xs-12">
			                      <div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                      <c:forEach var="city" items="${storeCitiesGroup.value}">
										<div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								 </c:forEach>
								 <BR>
			                      </div>
	 					 			</c:when>
	 					 			<c:otherwise>
		 							<div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                           <c:forEach var="city" items="${storeCitiesGroup.value}">
									   <div><a id="store_directory" href="<c:url value="${state}/${city.key}"/>">${city.key}
										(${city.value})</a></div>
								</c:forEach>
								<BR>
			                       </div>
		 						 </c:otherwise>
	 					 		</c:choose>
	 					 		<c:set var="indexLarge" value="${indexLarge+1}"/>
	 					 </c:if>
	 					 
	 					 <c:if test="${storeCitiesCount == 9}">
	 					 	<c:set var="startoverLarge" value="${indexLarge % largeColumns}"/>
	 					 		<c:choose>
	 					 			<c:when test="${(startoverLarge == 0 && indexLarge != 0 && indexLarge != 6) || indexLarge == 5 || indexLarge == 7 || indexLarge == 8}">
	 					 			</div>
	 					 				<div class="col-md-3 col-xs-12">
			                      <div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                      <c:forEach var="city" items="${storeCitiesGroup.value}">
										<div><a id="store_directory" href="${state}/${city.key}">${city.key}
										(${city.value})</a></div>
								 </c:forEach>
								 <BR>
			                      </div>
	 					 			</c:when>
	 					 			<c:otherwise>
		 							<div>
								<b><span id="${storeCitiesGroup.key}">${storeCitiesGroup.key}
								</span></b>
			                           <c:forEach var="city" items="${storeCitiesGroup.value}">
									   <div><a id="store_directory" href="${state}/${city.key}">${city.key}
										(${city.value})</a></div>
								</c:forEach>
								<BR>
			                      </div>
		 						 </c:otherwise>
	 					 		</c:choose>
	 					 		<c:set var="indexLarge" value="${indexLarge+1}"/>
	 					 </c:if>
	 				</c:otherwise>
 				</c:choose>
			</c:forEach>
			</div>
<div class="cl"></div>
<br/> 
</div>
			<div class="col-md-12">
				<div class="row"><div class="item-sec-border"></div>
				<div style="margin-top:-5px;"><spring:theme code="store.directory.city.callus.message" /></div>
				</div>
			</div>
			 <div class="cl"></div>
		</div>
  <div class="cl"></div>
  <br/>

</div>
</template:page>