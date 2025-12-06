<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:forEach var="brandBanner" items="${sortedBannerList}" varStatus="loop">
<c:if test="${loop.index eq 0}">
		<div class="col-md-6 col-xs-12">
					<a href="${brandBanner.buttonURL}">
											
							<span class="img-banner">
							<c:choose>
								<c:when test="${null != brandBanner.media}">
									<img title="${brandBanner.media.altText}" alt="${brandBanner.media.altText}" class="img-responsive related-article-big-image" src="${brandBanner.media.url}">
									<span class="media-icon"><img title="${brandBanner.icon.altText}" alt="${brandBanner.icon.altText}" class="img-responsive" src="${brandBanner.icon.url}"></span>
									<span class="image-description  padding-30">
										<span class="font-metronic-headline related-article-big-text">${brandBanner.title}</span>
										<span class="image-more-description font-Geogrotesque-bold">
										${brandBanner.buttonLabel}
										<span class="pad-lft-15">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
											<defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
										</svg>
										</span>
										</span>
									</span>
							
								</c:when>
								<c:otherwise>
									<img src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" class="img-responsive related-article-big-image" alt="${content.title}" title="${content.title}" />
								
								</c:otherwise>
							</c:choose>
							
							</span>

					</a>
				</div>
	
</c:if>
	
</c:forEach>	

<div class="col-md-6 col-xs-12 ">
	<div class="row mobile-only-slider clp-related-articles-small-image">
		<c:forEach var="brandBanner" items="${sortedBannerList}" varStatus="loop">
			
			<c:if test="${loop.index ge 1}">
				<div class="col-md-6 marginBottom30 slide">
					<a href="${brandBanner.buttonURL}">
						<span class="img-banner">
							<c:choose>
								<c:when test="${null != brandBanner.media}">
									<img title="${brandBanner.media.altText}" alt="${brandBanner.media.altText}" class="img-responsive" src="${brandBanner.media.url}">
									<span class="media-icon"><img title="${brandBanner.icon.altText}" alt="${brandBanner.icon.altText}" class="img-responsive" src="${brandBanner.icon.url}"></span>
									<span class="image-description padding-30">
										<span class="font-metronic-headline">${brandBanner.title}</span>
										<span class="font-Geogrotesque-bold image-more-description">${brandBanner.buttonLabel}
										<span class="pad-lft-15">
											<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
												<defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
											</svg>
										</span>
										</span>
									</span>
								</c:when>
								<c:otherwise>
									<img src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" class="img-responsive" alt="${content.title}" title="${content.title}" />
								
								</c:otherwise>
							</c:choose>						
							</span>				
				   </a>
					
				</div>
			</c:if>	
		</c:forEach>
			</div>	
		</div>	
<c:if test="${not empty sortedBannerList}">
	<div class="col-xs-12 col-md-6 col-md-offset-3 padding-top-30">
	<a href="/en/articles" class="btn btn-primary btn-block"><spring:theme code="text.clp.bottom.button.label"/></a>
	</div>
</c:if>		

