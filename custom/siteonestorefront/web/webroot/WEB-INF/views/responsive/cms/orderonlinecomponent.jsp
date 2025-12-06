<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<div id="orderOnlinePopup" style="display: none">	    			
<div class='Billing-pop-up'><div class='ship-to-header'><span  style='color:black;font-size: 22px;font-family: Geogrotesque-Medium;'>${component.title}</span></div>
<div class='PopupBox popup'><span  style='font-size: 12pt;'><p>${component.orderOnlineText}</p></span>
<br><div class='col-sm-4 col-md-4 col-xs-12 cart-paddingrt add-cart-padding add-cart-continue-btn' style='padding:0'><a class='btn btn-primary btn-block' href='${component.buttonURL}'>${component.buttonLabel}</a>
</div></div></div>
</div>			

 

