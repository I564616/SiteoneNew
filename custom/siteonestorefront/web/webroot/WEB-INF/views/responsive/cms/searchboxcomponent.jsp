<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>    
<c:url value="/search/" var="searchUrl" />
<c:url value="/search/autocomplete/${component.uid}" var="autocompleteUrl" />
<div class="ui-front">
	<form name="search_form_${component.uid}" method="get"
		action="${searchUrl}" id="searchBox">
		<div class="input-group">
			<spring:theme code="search.placeholder" var="searchPlaceholder" />

			<ycommerce:testId code="header_search_input">
				<input type="hidden" name="searchtype" id="type" value=""/>
				<input type="text" id="js-site-search-input"
					class="form-control js-site-search-input" name="text" value=""
					onfocus="this.placeholder = ''" onblur="this.placeholder = '${searchPlaceholder}'"
                    aria-label="productSearch" maxlength="100" placeholder="${searchPlaceholder}" dtm="${searchAttribute}" 
					data-options='{"autocompleteUrl" : "${autocompleteUrl}","minCharactersBeforeRequest" : "${component.minCharactersBeforeRequest}","waitTimeBeforeRequest" : "${component.waitTimeBeforeRequest}","displayProductImages" : ${component.displayProductImages}}'>
			</ycommerce:testId>

			<span class="input-group-btn"> 
			<ycommerce:testId code="header_search_button">
					<button class="btn btn-link js_search_button" type="button" id="searchBoxButton" dtm-product-search="product-search">
						<span class="sr-only">Product Search</span>
						<span class="hidden-xs hidden-sm"><common:headerIcon iconName="search" iconFill="#FFFFFF" iconColor="#77A12E" width="19" height="20" viewBox="0 0 19 20" display="" /></span>
						<span class="hidden-md hidden-lg"><common:headerIcon iconName="search" iconFill="#FFFFFF" iconColor="#FFFFFF" width="19" height="20" viewBox="0 0 19 20" display="" /></span>
					</button>
			</ycommerce:testId>
			</span>
	 </div>
	</form>

</div>
