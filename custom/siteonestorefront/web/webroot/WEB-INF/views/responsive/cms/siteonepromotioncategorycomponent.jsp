<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${date}" pattern="yyyy-mm-dd" />
 <%-- <fmt:formatDate var="promoEndDate" value="${component.promotionList.endDate}" pattern="yyyy-mm-dd"/>  --%>
	
	<!-- Mobile  category  section starts-->
	<div class="visible-xs visible-sm hidden-md-hidden-lg">  
<div class="card faq-sec promo-sec">
   <div class="card-header panel panel-default faq-accordion promo-accordion" role="tab">
     <div class="promo-mb-links panel-heading faq-panel promo-panel">
       <div class="panel-heading  faq-panel">
      
        <c:set var="categoryCount" value="${fn:length(component.promotionList)}"/>
<c:choose>
<c:when test="${categoryCount eq 0}">
<div class="disable-category">
	 <a data-toggle="collapse" href="#${component.uid}" aria-expanded="true" aria-controls="collapseOne"> <img src="${component.image.url}" class="promo-mb-img">
	<div class="promo-category-name"><span>${component.categoryName}</span>	<c:out value="${fn:length(component.promotionList)}"/></div>
	<div class="pull-right faq-arrow" style="margin-top:-10px;"><span class="glyphicon glyphicon-minus arrowDown" style="display:none"></span>
							<span class="glyphicon glyphicon-plus arrowRight"></span>
							</div>
	
	</a>
	</div>
	</c:when>
	<c:otherwise>
	 <a data-toggle="collapse" href="#${component.uid}" aria-expanded="true" aria-controls="collapseOne"> <img src="${component.image.url}" class="promo-mb-img">
	<div class="promo-category-name"><span>${component.categoryName}</span>	<c:out value="${fn:length(component.promotionList)}"/></div>
	<div class="pull-right faq-arrow" style="margin-top:-10px;"><span class="glyphicon glyphicon-minus arrowDown" style="display:none"></span>
							<span class="glyphicon glyphicon-plus arrowRight"></span>
							</div>
	
	</a>
	</c:otherwise>
	</c:choose>
        
        
        
       
       </div>
     </div>
   </div>

   <div id="${component.uid}" class="collapse" role="tabpanel" aria-labelledby="headingOne" data-parent="#accordion">
     <div class="promo-category-mb panel-body">
      <c:forEach items="${component.promotionList}" var="promotion"  varStatus="loopStatus">
		 
			
		 
				<c:set var="coupon" value="${promotion.rule.ruleParameters}"/>
			<c:choose>
				  <c:when test="${component.promotionCategory.code ne null}">
				    <c:choose>
				      <c:when test="${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
				       <c:set var="couponAvailable" value="true"/>
			             <input type="hidden" id="listOfCategoryCoupons" value='${promotion.rule.ruleParameters}'/>
			             <c:if test="${component.promotionCategory.code ne null}">
			             <fmt:formatDate var="categoryPromoEndDate" value="${promotion.endDate}" pattern="yyyy-mm-dd"/>
				          <div class="col-md-3 margin20">
				         <a href="#" id="categoryLink" data-promo-category="${component.promotionCategory.code}" data-promo-category-code="${promotion.code}" data-promo-category-description="${promotion.description}" data-category-promo-title="${promotion.name}" data-category-url="${promotion.image.url}" data-category-end-date="${categoryPromoEndDate}" data-category-current-date="${currentDate}" data-category-notes="${promotion.notes}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				         	   </div>
				         </c:if>
			
				     </c:when>
				     <c:otherwise>
				     <div class="col-md-3 margin20">
				         <a href="<c:url value="/search/?q=%3Arelevance%3AsoallPromotions%3A"/>${promotion.code}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				        </div> 
				    </c:otherwise>
				  </c:choose>
				 </c:when>
				  <c:otherwise>
				<c:choose>
				  <c:when test="${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
				  <a href="#" id="noCategoryLink" data-no-category-notes="${promotion.notes}" data-promo-no-category-code="${promotion.code}" data-promo-no-category-description="${promotion.description}" data-promo-no-category-url="${promotion.image.url}" data-no-category-promo-title="${promotion.name}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				  </c:when>
				<c:otherwise>
				    <a href="<c:url value="/search/?q=%3Arelevance%3AsoallPromotions%3A"/>${promotion.code}"><b>${promotion.code}</b><br>${promotion.name}<br></a>
				</c:otherwise>
			 </c:choose>
			</c:otherwise>
		</c:choose>
				
				<%-- <%-- <c:if test = "${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
				  <c:set var="couponAvailable" value="true"/>
			        <input type="hidden" id="listOfCategoryCoupons" value='${promotion.rule.ruleParameters}'/>
			    <c:if test="${component.promotionCategory.code ne null}">
			    <fmt:formatDate var="categoryPromoEndDate" value="${promotion.endDate}" pattern="yyyy-mm-dd"/>
				    <a href="#" id="categoryLink" data-promo-category="${component.promotionCategory.code}" data-promo-category-code="${promotion.code}" data-promo-category-description="${promotion.description}" data-category-promo-title="${promotion.title}" data-category-url="${promotion.image.url}" data-category-end-date="${categoryPromoEndDate}" data-category-current-date="${currentDate}" data-category-notes="${promotion.notes}"> <b>${promotion.code}</b><br>${promotion.title}<br></a></c:if>
				</c:if>
			    
				<c:if test="${component.promotionCategory.code eq null && couponAvailable ne 'true'}">
				<a href="<c:url value="/search/?q=%3Arelevance%3AsoallPromotions%3A"/>"><b>${promotion.code}</b><br>${promotion.title}<br></a></c:if>
				<c:if test="${component.promotionCategory.code eq null && couponAvailable eq 'true'}">
				 <a href="#" id="noCategoryLink" data-no-category-notes="${promotion.notes}" data-promo-no-category-code="${promotion.code}" data-promo-no-category-description="${promotion.description}" data-promo-no-category-url="${promotion.image.url}" data-no-category-promo-title="${promotion.title}"> <b>${promotion.code}</b><br>${promotion.title}<br></a></c:if> --%> 
				
			 
		</c:forEach>
     </div>
   </div>
 </div>
	  
</div>

<!-- Mobile  category  section ends-->



<!-- Desktop category  section -->
<div class="hidden-xs hidden-sm visible-md visible-lg">
<div class="promotion-category">
<c:set var="categoryCount" value="${fn:length(component.promotionList)}"/>
<c:choose>
<c:when test="${categoryCount eq 0}">
<div class="disable-category">
	<a href="#" class="categoryName"  onclick="return false;" data-uid="${component.uid}"> <img src="${component.image.url}">
	<span class="">${component.categoryName}</span><br/>(<c:out value="${fn:length(component.promotionList)}"/>)
	</a>
	</div>
	</c:when>
	<c:otherwise>
	<a href="#" class="categoryName" data-uid="${component.uid}"> <img src="${component.image.url}">
	<span class="promotion-category-title">${component.categoryName}</span><br/>(<c:out value="${fn:length(component.promotionList)}"/>)
	</a>
	</c:otherwise>
	</c:choose>
</div>

<div id="promotionTitle${component.uid}" class="collapse hidden-xs hidden-sm">
<div class="row">

<c:if test="${not empty component.promotionList}"> 
	<div class="col-md-12 promotion-filter promotionCategory hidden-xs hidden-sm">
	
	</c:if>
	<c:if test="${empty component.promotionList}"> 
	<div class="col-md-12 hidden-xs hidden-sm" >
	</c:if>
	<c:set var="largeCount" value="0" />
		<c:forEach items="${component.promotionList}" var="promotion"  varStatus="loopStatus">
		 
			<c:set var="largeCount" value="${largeCount+1}" />
		 
				<c:set var="coupon" value="${promotion.rule.ruleParameters}"/>
			<c:choose>
				  <c:when test="${component.promotionCategory.code ne null}">
				    <c:choose>
				      <c:when test="${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
				       <c:set var="couponAvailable" value="true"/>
			             <input type="hidden" id="listOfCategoryCoupons" value='${promotion.rule.ruleParameters}'/>
			             <c:if test="${component.promotionCategory.code ne null}">
			             <fmt:formatDate var="categoryPromoEndDate" value="${promotion.endDate}" pattern="yyyy-mm-dd"/>
				          <div class="col-md-3 margin20">
				         <a href="#" id="categoryLink" data-promo-category="${component.promotionCategory.code}" data-promo-category-code="${promotion.code}" data-promo-category-description="${promotion.description}" data-category-promo-title="${promotion.name}" data-category-url="${promotion.image.url}" data-category-end-date="${categoryPromoEndDate}" data-category-current-date="${currentDate}" data-category-notes="${promotion.notes}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				         	   </div>
				         </c:if>
			
				     </c:when>
				     <c:otherwise>
				     <div class="col-md-3 margin20">
				         <a href="/search/?q=%3Arelevance%3AsoallPromotions%3A${promotion.code}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				        </div> 
				    </c:otherwise>
				  </c:choose>
				  <c:otherwise>
					<c:choose>
					  	<c:when test="${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
					   		<div class="col-md-3 margin20">
					  			<a href="#" id="noCategoryLink" data-no-category-notes="${promotion.notes}" data-promo-no-category-code="${promotion.code}" data-promo-no-category-description="${promotion.description}" data-promo-no-category-url="${promotion.image.url}" data-no-category-promo-title="${promotion.name}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
					  		</div>
					  	</c:when>
						<c:otherwise>
					 		<div class="col-md-3 margin20">
					   			<a href="/search/?q=%3Arelevance%3AsoallPromotions%3A${promotion.code}"><b>${promotion.code}</b><br>${promotion.name}<br></a>
							</div>
						</c:otherwise>
			 		</c:choose>
			</c:otherwise>
				 </c:when>
		</c:choose>
				
				 <c:if test = "${fn:contains(coupon, 'ItemType(AbstractCoupon)')}">
				  <c:set var="couponAvailable" value="true"/>
			        <input type="hidden" id="listOfCategoryCoupons" value='${promotion.rule.ruleParameters}'/>
			    <c:if test="${component.promotionCategory.code ne null}">
			    <fmt:formatDate var="categoryPromoEndDate" value="${promotion.endDate}" pattern="yyyy-mm-dd"/>
			     <div class="col-md-3 margin20">
				    <a href="#" id="categoryLink" data-promo-category="${component.promotionCategory.code}" data-promo-category-code="${promotion.code}" data-promo-category-description="${promotion.description}" data-category-promo-title="${promotion.name}" data-category-url="${promotion.image.url}" data-category-end-date="${categoryPromoEndDate}" data-category-current-date="${currentDate}" data-category-notes="${promotion.notes}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				    </div>
				    </c:if>
				
				</c:if>
			    
				<c:if test="${component.promotionCategory.code eq null && couponAvailable ne 'true'}">
				 <div class="col-md-3 margin20">
				<a href="/search/?q=%3Arelevance%3AsoallPromotions%3A${promotion.code}"><b>${promotion.code}</b><br>${promotion.name}<br></a>
				 </div>
				</c:if>
				<c:if test="${component.promotionCategory.code eq null && couponAvailable eq 'true'}">
				 <div class="col-md-3 margin20">
				 <a href="#" id="noCategoryLink" data-no-category-notes="${promotion.notes}" data-promo-no-category-code="${promotion.code}" data-promo-no-category-description="${promotion.description}" data-promo-no-category-url="${promotion.image.url}" data-no-category-promo-title="${promotion.name}"> <b>${promotion.code}</b><br>${promotion.name}<br></a>
				 </div>
				 </c:if> 
				
						     <c:if test="${largeCount eq '4' }">
						     <div  class="visible-md visible-lg hidden-sm hidden-xs">
	                		  		<div class="cl"></div>
									<c:set var="largeCount" value="0" />
										</div>
								</c:if>
						
			 
		</c:forEach>
	 
	 </div> 
	 
</div>	 
	 
</div>

</div>





	


 
