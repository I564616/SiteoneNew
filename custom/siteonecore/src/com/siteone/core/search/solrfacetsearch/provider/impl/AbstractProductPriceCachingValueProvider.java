/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexProductPriceDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceContainer;


/**
 * @author i849388
 *
 */

public abstract class AbstractProductPriceCachingValueProvider extends AbstractPropertyFieldValueProvider
{

	private IndexProductPriceDAO indexProductPriceDAO;

	private FieldNameProvider fieldNameProvider;


	protected final static ThreadLocal<IndexProductPriceContainer> threadLocalCache = ThreadLocal.withInitial(() -> {
		final IndexProductPriceContainer cache = new IndexProductPriceContainer();
		return cache;
	});


	protected void loadProductCache(final ProductModel baseProduct)
	{
		if (!baseProduct.getCode().equals(threadLocalCache.get().getBaseProductId()))
		{
			final IndexProductPriceContainer cont = this.indexProductPriceDAO.loadProductPricesFromActivePO(baseProduct);
			threadLocalCache.set(cont);
		}
	}

	/**
	 * @return the indexProductPriceDAO
	 */
	public IndexProductPriceDAO getIndexProductPriceDAO()
	{
		return indexProductPriceDAO;
	}

	/**
	 * @param indexProductPriceDAO
	 *           the indexProductPriceDAO to set
	 */
	public void setIndexProductPriceDAO(final IndexProductPriceDAO indexProductPriceDAO)
	{
		this.indexProductPriceDAO = indexProductPriceDAO;
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
	public ThreadLocal<IndexProductPriceContainer> getThreadLocalCache()
	{
		return threadLocalCache;
	}




}
