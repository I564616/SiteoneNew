ACC.facets = {
    _autoload: [
       // "bindFacets"
    ],
    showMoreFacet: function(event) {
        var facetTotalCount = $("#totalFacetSize").val();
        var limit = $("#facetLimit").val();
        var currentOffset = parseInt($(event).data('offset'), 10);
        ACC.facets.bindFacets(currentOffset, limit);
        currentOffset += parseInt(limit);
        if(currentOffset >= parseInt(facetTotalCount)){
            $(event).parent().hide();
        } 
    },
    bindFacets: function(currentOffset, limit) {   
        var pageId = $("#pageId").val();
        var categoryCode = "";
        var queryParam = "";
        var apiPath = ACC.config.encodedContextPath;
        var url = window.location.href;
        var spliturl = url.split("/");
        var intCat = spliturl[spliturl.length-1];
        var _queryString = intCat.split("?");
        if(_queryString.length > 1){
            queryParam =  _queryString[1];
        }
        if (pageId == 'searchGrid' ){
            apiPath = apiPath + '/search/facets' + '?facetOffset=' + currentOffset + '&' + queryParam ;
        }
        if (pageId == 'productGrid' ){
            categoryCode = $("#categoryCodePR").val();
            var _param = "";
            if(queryParam.includes('?')){
                _param = '?facetOffset=' + currentOffset + '&' + queryParam;
            } 
            else{
                _param = '?facetOffset=' + currentOffset;
            } 
            apiPath = apiPath + '/c/'+ categoryCode +'/facets' + _param;
        }  
        $.ajax({
            url: apiPath,
            type: 'GET',
            cache: false,
            async: true,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (response) {
                var decoded = $('<div>').html(response.facets).text();
                $('#productFacets').append(decoded);  
                $("#showMoreFilters").data("offset", parseInt(currentOffset) + parseInt(limit));       
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error: ', textStatus, errorThrown); 
            }
        });  
    }
};