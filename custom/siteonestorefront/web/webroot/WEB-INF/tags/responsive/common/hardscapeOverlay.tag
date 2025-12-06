
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<input type="hidden" id="hardscapeisSellable" name="hardscapeisSellable" value="${product.isSellable}"/>
<input type="hidden" id="hardscapenearby" name="hardscapenearby" value="${product.isStockInNearbyBranch}"/>
<div style ="display: none;" class="hardscape_content row">

	<div class=" hardscape_card_one col-md-6">
	
		<div class="hardscape_product_instock ">
			<div class="circle-harscape ${product.isSellable eq true || (product.isSellable ne true && product.isStockInNearbyBranch eq true) ? 'instockproduct-bg':'orderinstock-bg'}">
				<div class="hardscape-rotate-wrap">
					<p class="hardscape-rotate">..........</p>
					<c:choose>
						<c:when test="${product.isSellable eq true || (product.isSellable ne true && product.isStockInNearbyBranch eq true)}">
							<p class="hardscape-rotate">
							<span class="hardscape-test1"><spring:theme code="hardscape.in.stock.product.text" /></span>
							<span class="hardscape-test2"><spring:theme code="hardscape.products.text" /></span></p>
						</c:when>
						<c:otherwise>
							<p class="hardscape-rotate">
							<span class="hardscape-test1"><spring:theme code="hardscape.order.in.products.text" /></span>
							<span class="hardscape-test2"><spring:theme code="hardscape.products.text" /></span></p>
						</c:otherwise>
					</c:choose>
					<p class="hardscape-rotate-text">..........</p>
				</div>
			</div>
		</div>
		<div class="hardscape_text"><spring:theme code="hardscape.is.right.for.you.text" /> </div>
		
		<div class="hardscape_text_li">
			<c:choose>
				<c:when test="${product.isSellable eq true || (product.isSellable ne true && product.isStockInNearbyBranch eq true)}">
					<li><spring:theme code="hardscape.instock.product.text1" /></li>
					<li><spring:theme code="hardscape.instock.product.text2" /></li>
					<li><spring:theme code="hardscape.instock.product.text3" /></li>
				</c:when>
				<c:otherwise>
					<li><spring:theme code="hardscape.order.in.stock.text1" /></li>
					<li><spring:theme code="hardscape.order.in.stock.text2" /></li>
					<li><spring:theme code="hardscape.order.in.stock.text3" /></li>
				</c:otherwise>
			</c:choose>	
		</div>
		
		<div class="hardscape_text_middle">
			<c:choose>
				<c:when test="${product.isSellable eq true || (product.isSellable ne true && product.isStockInNearbyBranch eq true)}">
					<spring:theme code="hardscape.instock.product.important" />
				</c:when>
				<c:otherwise>
					<spring:theme code="hardscape.order.in.stock.important" />
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="hardscape_siteone_logo  hidden-xs">
			<img class="img-responsive" src="/_ui/responsive/theme-lambda/images/siteonelogo-hardscapeoverlay.png" alt="Siteonelogo" >
		</div>
		
	</div>
	
	<div class=" overlay_card_second col-md-6">
	  	
	  	<div class="row hardscape_box">
			<div class="hardscape_box_img col-md-12 col-xs-5">
				<a class="thumb " >
					 <img class="hardscape_box__img" src=""/>
				</a>
			</div>
			<div class="col-md-12  col-xs-7">
				<div class="hardscape_product_id"></div>
				<div class="hardscape_product_name">
				</div>
			</div>
		</div>
		
		<div class="row hardscape-btn-wrap">
			<div class="btnclass hardscape-submit-btn">
				<button  class="col-xs-12 btn btn-primary btn-hardscape-first margin-top-20"><spring:theme code="hardscape.questions.to.expert" /></button>
			</div>
			
			<div class="btnclass hardscape-continue-btn">
				<button  class="col-xs-12 btn btn-primary btn-hardscape-second margin-top-20 hardscapeContinueShopping"><spring:theme code="hardscape.continue.shopping" /></button>
			</div>
		</div>
	</div>

</div>