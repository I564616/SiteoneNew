import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.google.common.collect.Lists;

import com.siteone.pimintegration.util.ProductUtil;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedPropertyModel;

final FlexibleSearchService flexibleSearchService = spring.getBean("flexibleSearchService");
final UserService userService = spring.getBean("userService");
final ModelService modelService = spring.getBean("modelService");
final ProductUtil productUtilObj = new ProductUtil();

productUtilObj.setFlexibleSearchService(flexibleSearchService);
productUtilObj.setUserService(userService);

final String queryString = "SELECT {pk} FROM {SolrIndexedProperty} WHERE {solrindexedtype} = 8796093057183 AND {facet} = 1 AND {name} NOT IN ('soproductBrandNameFacet','price','sosavingsCenter','socouponFacet','allCategories','soIsStockAvailableInStores','soisShippable','socategoryPath','socategory','soavailableInStores','soallPromotions') ORDER BY {displayname} DESC";

FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);

final SearchResult<SolrIndexedPropertyModel> solrIndexedPropertyResult = flexibleSearchService.search(fQuery);
final List<SolrIndexedPropertyModel> solrIndexedProperties = solrIndexedPropertyResult.getResult();

println("solrIndexedProperties Size - " + solrIndexedProperties.size());

int priority = 1000;

for (SolrIndexedPropertyModel solrIndexedProperty : solrIndexedProperties) {
    println("Updating SolrIndexedProperty: " + solrIndexedProperty.getPk() + " - " + solrIndexedProperty.getName());
    solrIndexedProperty.setPriority(priority);
    priority = priority + 1;
}

modelService.saveAll(solrIndexedProperties);

println("Update complete for all facets");