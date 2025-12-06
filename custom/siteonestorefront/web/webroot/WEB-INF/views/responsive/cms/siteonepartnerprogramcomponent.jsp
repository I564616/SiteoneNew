<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component"%>


<div class="col-md-4 col-sm-4 col-xs-12" style="position:relative;">
 
	 
			<img src="${component.image.url}" style="border:1px solid #ccc;width:100%;">	 
			<div  class="learnMore-description" style="padding: 20px;position: absolute;top: 10px;text-align: center; width: 92%;">
			<div class="homepage-banner-heading">${component.promotionalText} </div>
				<br><br>
				</div>
			 <div class="learnMore-promotionPage col-md-12 col-xs-12" style="padding: 20px;position: absolute;bottom:20%;left:22%; width: 60%;">
			<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a> 
			</div>
			 
 
</div>
