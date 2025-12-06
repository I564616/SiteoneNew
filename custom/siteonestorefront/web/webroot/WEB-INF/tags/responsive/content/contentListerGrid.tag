<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="contentSearch" required="false"
	type="java.util.ArrayList"%>

	
		<div class=" search-related-article-section">

<c:if test="${contentSearchPageData.results.size() eq 3}">
<c:set var="isTotalItemsThree" value="isTotalItemsThree"/>
</c:if>

	<div class="row no-margin">
<c:forEach items="${contentSearchPageData.results}" var="content" varStatus="status">


		<c:if test="${status.index le 1 }">	
		<div class="col-xs-12 col-md-6 pad-xs-hrz-40 padding-top-30 serp-large-image-container">
			<a href="${content.url}" class="js-related-article content-link" "> 
				<span class="${content.contentType ne 'PROMOTION_PAGE' ?'img-gradient':''}">
					<c:choose>
						<c:when test="${null != content.previewImage}"> 
							<img class="img-responsive" src="${content.previewImage.url}"  alt="${content.title}" title="${content.previewTitle}"/>
							
						</c:when>
						<c:otherwise>
							<img class="img-responsive"  src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" alt="${content.title}" title="${content.previewTitle}" />
			
						</c:otherwise>
					</c:choose>

					<c:if test="${content.contentType ne 'PROMOTION_PAGE'}">
						<span class="icon-box-arrow"></span>				

						<span class="image-description serp-large-image">
							<span class="font-metronic-headline">
								<span class="hidden-xs hidden-sm">${content.previewTitle}</span>
								<span class="hidden-lg hidden-md">
									<c:choose>
										<c:when test="${fn:length(content.previewTitle) > 45}">
											${fn:substring(content.previewTitle, 0, 45)}...
										</c:when>
										<c:otherwise>
											${content.previewTitle}
										</c:otherwise>
									</c:choose>
								</span>
							</span>
							<span class="font-Geogrotesque-bold image-more-description"><spring:theme code="text.more.read.article"/>
							<span class="pad-lft-15">
								<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
									<defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
								</svg>
							</span>
							</span>
						</span>
					</c:if>
				</span>
				

			</a>
		</div>
		</c:if>

</c:forEach>
		
	</div>


	<div class="row grid-article-slider-xs mobile-only-slider ${isTotalItemsThree}">
	<c:forEach items="${contentSearchPageData.results}" var="content" varStatus="status">

		<c:if test="${status.index ge 2 and status.index le 4}">	
		<div class="slide col-md-4 padding-top-30">
			<a href="${content.url}" class="js-related-article" title="${content.previewTitle}"> 
				<span class="${content.contentType ne 'PROMOTION_PAGE' ?'img-gradient':''}"> 
					<c:choose>
						<c:when test="${null != content.previewImage}"> 
							<img class="img-responsive" src="${content.previewImage.url}"  alt="${content.title}" title="${content.previewTitle}"/>
						</c:when>
						<c:otherwise>
							<img class="img-responsive" src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" alt="${content.title}" title="${content.previewTitle}" />
						</c:otherwise>
					</c:choose>

					<c:if test="${content.contentType ne 'PROMOTION_PAGE'}">
						<span class="icon-box-arrow"></span>		

						<span class="image-description serp-slide-image">
							<span class="font-metronic-headline">
								<span class="font-metronic-headline">
								<span class="hidden-xs hidden-sm">${content.previewTitle}</span>
								<span class="hidden-lg hidden-md">
									<c:choose>
										<c:when test="${fn:length(content.previewTitle) > 45}">
											${fn:substring(content.previewTitle, 0, 45)}...
										</c:when>
										<c:otherwise>
											${content.previewTitle}
										</c:otherwise>
									</c:choose>
								</span>
							</span>
							</span>
							<span class="font-Geogrotesque-bold image-more-description"><spring:theme code="text.more.read.article"/>
							<span class="pad-lft-15">
								<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
									<defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
								</svg>
							</span>
							</span>
						</span>
					</c:if>
					
				</span>
			</a>
		</div>
		</c:if>

		</c:forEach>


	</div>
	</div>	
	
	

	
	

