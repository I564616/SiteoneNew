/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.order.services.SiteOneOrderService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.OrderService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author BS
 *
 */
public class BuyItAgainProductUOMValueResolver extends AbstractValueResolver<OrderEntryModel, Object, Object>
{

	private static final Logger LOG = Logger.getLogger(BuyItAgainProductUOMValueResolver.class);
	@Resource
	private OrderService orderService;

	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource
	private CatalogVersionService catalogVersionService;

	@Resource
	ProductService productService;

	@Override
	protected void addFieldValues(final InputDocument document, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final OrderEntryModel orderEntryModel,
			final ValueResolverContext<Object, Object> valueResolverContext) throws FieldValueProviderException
	{
		InventoryUPCModel inventoryUPCModel=null;
		try{
			if(orderEntryModel.getInventoryUOM()==null){
					ProductModel productModel= orderEntryModel.getProduct();
					if(productModel!=null && !productModel.getUpcData().isEmpty()){
						for(InventoryUPCModel upcModel:productModel.getUpcData()){
							if(!upcModel.getHideUPCOnline()){ 
								inventoryUPCModel=upcModel;
								break;
							}
						}

				}
			}else {
				inventoryUPCModel=orderEntryModel.getInventoryUOM();
			}
		}catch (Exception e){
			LOG.error("error indexing product uom", e);
		}


		if(inventoryUPCModel!=null){
			document.addField(indexedProperty, inventoryUPCModel.getInventoryUPCID()+"|"+inventoryUPCModel.getCode()+"|"+inventoryUPCModel.getInventoryUPCDesc()
							+"|"+orderEntryModel.getOrder().getCode(),
					valueResolverContext.getFieldQualifier());
		}
	}

}
