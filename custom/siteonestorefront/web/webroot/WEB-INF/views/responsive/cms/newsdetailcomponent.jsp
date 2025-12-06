<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>


	<div class="col-sm-12">
	<div class="row">
	<br/><br/>
	<h1 class="headline"><spring:theme code="news.detail.component"/></h1><br><br/>
	<div class="row">
		<div class="col-md-6">
		<img src="${newsData.newsMedia.url}" style="height:auto; width:100%;" alt="" title="title"/></div>
		</div>
		<div class="cl"></div><br/><br/>
		<div class="row">
		<div class="margin20 col-md-6">
			 <p><b class="store-specialty-heading"> ${newsData.title}</b> <br/>
			 <span class="black-title"><b><fmt:formatDate type="date" dateStyle="long" timeStyle="long" value="${newsData.newsPublishDate}" /></b></span>
			 </p>
			 <br/>
			 <p> ${newsData.longDescription} </p>
			 <br> 
		</div>
		</div>
		<div class="cl"></div>	 
		 
		<div class="item-sec-border"></div>
		<br/><br/>
	</div>
</div>
