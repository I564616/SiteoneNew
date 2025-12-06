<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<fmt:formatDate value="${articleContent.modifiedtime}" pattern="yyyy-MM-dd" var="formattedDate" />
<div class="col-md-7 row">
    <input type="hidden" name="article-content-title" value="${articleContent.previewTitle}"/>
    <input type="hidden" name="article-content-description" value="${articleContent.description}"/>
    <input type="hidden" name="article-content-modifiedtime" value="${articleContent.modifiedtime}"/>
    <input type="hidden" name="article-content-url" value="https://www.siteone.com${articleContent.previewImage.url}"/>
    <input type="hidden" name="article-content-locale" value="${currentLanguage.isocode eq 'es' ? 'es_US' : 'en_US'}"/>
    

    <h1 class="headline">${articleContent.previewTitle}</h1>
    <br/>
    <p>${articleContent.description}</p>
    <p class="article-publish-detail"><span><fmt:formatDate value ="${articleContent.modifiedtime}" type="date" timeStyle="long" dateStyle="long"/></span></p>
    <div class="article-icons-container col-md-12 col-xs-12">
        <div class="row text-right">
            <c:choose>
        <c:when test="${currentBaseStoreId eq 'siteone'}">
            <a href="https://www.facebook.com/SiteOneLandscape/" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Facebook.svg" alt="Facebook" title="Facebook" class="article-icon"></a>
            <a href="https://x.com/SiteOneSupply" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Twitter.svg"  alt="Twitter" title="Twitter" class="article-icon"></a>
            <a href="mailto:customersupport@siteone.com" class="glyphicon glyphicon-envelope article-icons email-icon article-email-icon" aria-hidden="true" title="Email Us" title="Email Us"></a>
      </c:when>
      <c:otherwise>
             <a href="https://www.facebook.com/siteonesupplycanada" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Facebook.svg" alt="Facebook" title="Facebook" class="article-icon"></a>
            <a href="https://x.com/i/flow/login?redirect_after_login=%2FSiteOneCanada" class="article-icons"><img src="${themeResourcePath}/images/Footer_Social_Twitter.svg"  alt="Twitter" title="Twitter" class="article-icon"></a>
            <a href="mailto:customersupport@siteone.com" class="glyphicon glyphicon-envelope article-icons email-icon article-email-icon" aria-hidden="true" title="Email Us" title="Email Us"></a>
      </c:otherwise>
       </c:choose></div>
    </div>
</div>
<script type="application/ld+json">
    {
  "@context": "https://schema.org/",
  "@type": "BlogPosting",
  "mainEntityOfPage": {
    "@type": "WebPage",
    "@id": "https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}"
  },
  "headline": "${articleContent.previewTitle}",
  "description": "${articleContent.description}",
  "image": {
    "@type": "ImageObject",
    "url": "https://www.siteone.com${articleContent.previewImage.url}"
  },
  "author": {
    "@type": "Organization",
    "name": "SiteOne Landscape Supply"
  },
  "datePublished": "${formattedDate}"
}
</script>
