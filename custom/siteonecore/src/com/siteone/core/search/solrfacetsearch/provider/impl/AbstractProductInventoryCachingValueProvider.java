/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexProductInventoryDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductInventoryContainer;


/**
 * @author i849388
 *
 */

public abstract class AbstractProductInventoryCachingValueProvider extends AbstractPropertyFieldValueProvider
{

	private IndexProductInventoryDAO indexProductInventoryDAO;

	private FieldNameProvider fieldNameProvider;


	protected final static ThreadLocal<IndexProductInventoryContainer> threadLocalCache = ThreadLocal.withInitial(() -> {
		final IndexProductInventoryContainer cache = new IndexProductInventoryContainer();
		return cache;
	});


	protected void loadProductCache(final ProductModel baseProduct)
	{
		if (!baseProduct.getCode().equals(threadLocalCache.get().getBaseProductId()))
		{

			final IndexProductInventoryContainer cont = this.indexProductInventoryDAO.loadProductInventoryFromActivePO(baseProduct);
			threadLocalCache.set(cont);

		}
	}

	/**
	 * @return the indexProductInventoryDAO
	 */
	public IndexProductInventoryDAO getIndexProductInventoryDAO()
	{
		return indexProductInventoryDAO;
	}

	/**
	 * @param indexProductInventoryDAO
	 *           the indexProductInventoryDAO to set
	 */
	public void setIndexProductInventoryDAO(final IndexProductInventoryDAO indexProductInventoryDAO)
	{
		this.indexProductInventoryDAO = indexProductInventoryDAO;
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
	public ThreadLocal<IndexProductInventoryContainer> getThreadLocalCache()
	{
		return threadLocalCache;
	}




}
