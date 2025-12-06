/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import org.apache.log4j.Logger;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexPurchasedQuantityDAO;
import com.siteone.core.search.solrfacetsearch.index.dao.IndexPurchasedProductCountDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexPurchasedProductCountContainer;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexPurchasedQuantityContainer;


/**
 * @author pelango
 *
 */

public abstract class BuyItAgainProductCountCacheValueProvider extends AbstractPropertyFieldValueProvider
{

	private static final Logger LOG = Logger.getLogger(BuyItAgainProductCountCacheValueProvider.class);

	private IndexPurchasedProductCountDAO indexPurchasedProductCountDAO;
	
	private IndexPurchasedQuantityDAO indexPurchasedQuantityDAO;

	private FieldNameProvider fieldNameProvider;


	protected final static ThreadLocal<IndexPurchasedProductCountContainer> threadLocalCache = ThreadLocal.withInitial(() -> {
		final IndexPurchasedProductCountContainer cache = new IndexPurchasedProductCountContainer();
		return cache;
	});
	
	protected final static ThreadLocal<IndexPurchasedQuantityContainer> threadLocalCaches = ThreadLocal.withInitial(() -> {
		final IndexPurchasedQuantityContainer cache = new IndexPurchasedQuantityContainer();
		return cache;
	});


	protected void loadProductCache(final OrderEntryModel orderEntry) 
	{

		if (!threadLocalCache.get().getAllB2Bunit().contains(orderEntry.getOrder().getOrderingAccount().getUid()))
		{
			LOG.error("BuyItAgainProductCountCacheValueProvider");
			final IndexPurchasedProductCountContainer cont = this.indexPurchasedProductCountDAO.loadPurchasedProductCount();
			threadLocalCache.set(cont);
		}
	}
	
	protected void loadPurchasedQuantityCache(final OrderEntryModel orderEntry)
	{

		if (!threadLocalCaches.get().getAllB2Bunit().contains(orderEntry.getOrder().getOrderingAccount().getUid()))
		{
			LOG.error("BuyItAgainProductCountCacheValueProvider");
			final IndexPurchasedQuantityContainer cont = this.indexPurchasedQuantityDAO.loadPurchasedQuantity();
			threadLocalCaches.set(cont);
		}
	}


	/**
	 * @return the indexPurchasedProductCountDAO
	 */
	public IndexPurchasedProductCountDAO getIndexPurchasedProductCountDAO()
	{
		return indexPurchasedProductCountDAO;
	}

	/**
	 * @param indexPurchasedProductCountDAO
	 *           the indexPurchasedProductCountDAO to set
	 */
	public void setIndexPurchasedProductCountDAO(final IndexPurchasedProductCountDAO indexPurchasedProductCountDAO)
	{
		this.indexPurchasedProductCountDAO = indexPurchasedProductCountDAO;
	}
	
	public IndexPurchasedQuantityDAO getIndexPurchasedQuantityDAO()
	{
		return indexPurchasedQuantityDAO;
	}

	/**
	 * @param indexPurchasedQuantityDAO the indexPurchasedQuantityDAO to set
	 */
	public void setIndexPurchasedQuantityDAO(IndexPurchasedQuantityDAO indexPurchasedQuantityDAO)
	{
		this.indexPurchasedQuantityDAO = indexPurchasedQuantityDAO;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	/**
	 * @return the threadLocalCache
	 */
	public ThreadLocal<IndexPurchasedProductCountContainer> getThreadLocalCache()
	{
		return threadLocalCache;
	}




}
