<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ attribute name="totalContent" required="false" type="String" %>
<c:choose>
<c:when test="${totalContent ge 5 }">
<c:set var="showItems" value="5"/>
</c:when>
<c:otherwise>
<c:set var="showItems" value="${totalContent}"/>
</c:otherwise>
</c:choose>
	<div class="row no-margin article-header">
		<div class="col-xs-8 col-sm-8 col-md-9 col-lg-9 related-article-style">
		    <a href="#" class="show-related-article"
				data-state="expand" data-show-items="${showItems}">
				<span class='plus-circle'>
					<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25"><defs><style>.a{fill:#fff;}.b{fill:#78a22f;}.c,.d{stroke:none;}.d{fill:#e0e0e0;}</style></defs><g transform="translate(-495 -1215)"><g class="a" transform="translate(495 1215)"><path class="c" d="M 12.5 24.5 C 9.294679641723633 24.5 6.281219959259033 23.25177955627441 4.01471996307373 20.98527908325195 C 1.748219966888428 18.71878051757812 0.5 15.70532035827637 0.5 12.5 C 0.5 9.294679641723633 1.748219966888428 6.281219959259033 4.01471996307373 4.01471996307373 C 6.281219959259033 1.748219966888428 9.294679641723633 0.5 12.5 0.5 C 15.70532035827637 0.5 18.71878051757812 1.748219966888428 20.98527908325195 4.01471996307373 C 23.25177955627441 6.281219959259033 24.5 9.294679641723633 24.5 12.5 C 24.5 15.70532035827637 23.25177955627441 18.71878051757812 20.98527908325195 20.98527908325195 C 18.71878051757812 23.25177955627441 15.70532035827637 24.5 12.5 24.5 Z"/><path class="d" d="M 12.5 1 C 9.428239822387695 1 6.54033088684082 2.196210861206055 4.368270874023438 4.368270874023438 C 2.196210861206055 6.54033088684082 1 9.428239822387695 1 12.5 C 1 15.5717601776123 2.196210861206055 18.45967102050781 4.368270874023438 20.63172912597656 C 6.54033088684082 22.80379104614258 9.428239822387695 24 12.5 24 C 15.5717601776123 24 18.45967102050781 22.80379104614258 20.63172912597656 20.63172912597656 C 22.80379104614258 18.45967102050781 24 15.5717601776123 24 12.5 C 24 9.428239822387695 22.80379104614258 6.54033088684082 20.63172912597656 4.368270874023438 C 18.45967102050781 2.196210861206055 15.5717601776123 1 12.5 1 M 12.5 0 C 19.40356063842773 0 25 5.596439361572266 25 12.5 C 25 19.40356063842773 19.40356063842773 25 12.5 25 C 5.596439361572266 25 0 19.40356063842773 0 12.5 C 0 5.596439361572266 5.596439361572266 0 12.5 0 Z"/></g><path class="b" d="M6.708,66.917H4.083V64.292A.292.292,0,0,0,3.792,64H3.208a.292.292,0,0,0-.292.292v2.625H.292A.292.292,0,0,0,0,67.208v.583a.292.292,0,0,0,.292.292H2.917v2.625A.292.292,0,0,0,3.208,71h.583a.292.292,0,0,0,.292-.292V68.083H6.708A.292.292,0,0,0,7,67.792v-.583A.292.292,0,0,0,6.708,66.917Z" transform="translate(504 1160)"/></g></svg>
				</span>
			</a>
			<a class="bold grid-related-articles-text" onclick="event.preventDefault()"><spring:theme code="text.related.article.search.content"/></a>
		</div>
		<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3 hide-items-container">
			<a href="#" target="_blank" class="tms-pill content-show-hide-items" onclick="event.preventDefault()"><spring:theme code="text.related.content.show.items" arguments="${showItems}"/></a>
		</div>
	</div>
	 	<div class="related-article-block row hidden" >
	 	 	 <content:contentListerGrid/>
	 	 	 <div class="col-md-12 padding-top-30 pad-xs-hrz-40">
			<a href="/articles" class="btn no-margin btn-primary btn-block search-article-button js-more-article-btn"><spring:theme code="text.related.article.more.content"/></a>
	     </div>
	</div>
