<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<template:page pageTitle="${pageTitle}">
<script>
$( document ).ready(function(){
	var carouselImageElements = $('[class^="PromotionPageCorsalComp_"]');

	$.each(carouselImageElements, function(key, element) {
		$(element).addClass('active');
		return false;
	});
	   $(".promopage").carousel();
	    $(".promo1").click(function(){
	    	$(".promoPage").carousel(0);
	    });
	    $(".promo2").click(function(){
	        $(".promoPage").carousel(1);
	    });
	    $(".promo3").click(function(){
	        $(".promoPage").carousel(2);
	    });
	    $(".promo4").click(function(){
	        $(".promoPage").carousel(3);
	    });
    
	});
</script>
<div>
<!--  <h1 class="headline">Monthly Specials</h1> -->
 <br/>
<div class="cl"></div>

<div  class="promoPage tabs-left hidden-xs hidden-sm">
	<ul class="promo-tabs">
	 	<cms:pageSlot position="SectionA" var="feature" >
			<cms:component component="${feature}" />
	 	</cms:pageSlot>
	</ul>
</div>
<div  class="promoPage carousel slide hidden-md hidden-lg" data-ride="carousel">
	
 	 <div class="promo-tabs carousel-inner hidden-md hidden-lg">
 		<cms:pageSlot position="SectionA" var="feature">
			 <div class="PromotionPageCorsalComp_${feature.uid} item">
		 		<cms:component component="${feature}"/>
			 </div>
		</cms:pageSlot>
	 </div>	
		 <ol class="carousel-indicators">
	     	 <li data-target=".promoPage" data-slide-to="0" class="promo1 active"></li>
	     	 <li data-target=".promoPage" data-slide-to="1" class="promo2"></li>
	      	 <li data-target=".promoPage" data-slide-to="2" class="promo3"></li>
	     	 <li data-target=".promoPage" data-slide-to="3" class="promo4"></li>
	     </ol>
    </div>
	<div class="tab-content"> 
<div  style="width:100%;margin-top:0px;position:relative;" class="promotionBanner hidden-xs hidden-sm">
</div>
 
</div>	 
  <div class="cl"></div>
  <br/><br/><br/>
  <div class="promotionLine item-sec-border"></div>
    <br/><br/>
<div id="promoComponent">
 
  <p class="store-specialty-heading hidden-xs hidden-sm"><b><spring:theme code="promotionPage.filter.by.category" /></b></p><br/>
  <div class="panel-group" id="accordion" role="tablist">
		 <cms:pageSlot position="SectionB" var="feature" >
			    <cms:component component="${feature}" />
		 </cms:pageSlot>
		 </div>
		  <div class="cl"></div>
		 </div>
</div>		
<div id="promoDetails">
</div> 

<div class="margin20 promotionsPicBottom" id="promoPdfComponent">
<div class="row">
<cms:pageSlot position="SectionC" var="feature" >
			    <cms:component component="${feature}" />
 </cms:pageSlot>
 </div>
 <div class="cl"></div>
 </div>
 <div class="cl"></div>
<div id="notes"></div>
<div id="couponPage" class="hidden">
<div>
	    <div id="availableCouponTitle"></div>
		<p class="couponMessage" style="margin-bottom: 100px"><spring:theme code="promotionPage.apply.coupon.code" /> <span id="availableCouponCode"></span> <spring:theme code="promotionPage.at.checkout" />.<br/>
		<c:if test="${component.promotion.shopDealUrl ne null}">
		<a href="${component.promotion.shopDealUrl}" id="shopDeal"><spring:theme code="promotionPage.shop.deal" /></a> </c:if>
		<c:if test="${component.promotion.shopDealUrl eq null}">
	  	<!--<a href="#">Shop the deal</a></c:if></p>--->
	
		<div style="position: relative;bottom: 50px; left: -20px;" class="couponPromotionBanner">
	
	</div>
		<div id="availableCouponUrl"></div>
		<div id="availableCouponDescription"></div><br><br><br><br><br>
		<div id="availableCouponNotes"></div>

</div>
</template:page>