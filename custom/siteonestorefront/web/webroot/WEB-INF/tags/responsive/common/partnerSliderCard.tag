<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ attribute name="card" type="java.lang.String"%>
<%@ attribute name="heading" type="java.lang.String"%>

<div class="${(card%2!=0) ? 'm-t-25 ' : ''}m-r-50 m-r-15-xs bg-white text-left border-grey category-tile-item">
	<img class="full-width" src="/_ui/responsive/theme-lambda/images/perksSlider${card}.png" alt="${heading}">
	<div class="caption-details">
		<h3 class="f-s-22 f-s-16-xs-px text-default bold padding-bottom-15 margin0 "><spring:theme code="partners.perks.slider.title${card}" /></h3>
		<p class="margin0 f-s-16 font-14-xs"><spring:theme code="partners.perks.slider.heading${card}" /></p>
		<ul class="p-t-25 f-s-16 font-14-xs">
			<li><spring:theme code="partners.perks.slider.li1${card}" /></li>
			<li class="p-t-25"><spring:theme code="partners.perks.slider.li2${card}" /></li>
		</ul>
	</div>
</div>