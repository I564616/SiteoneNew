/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider.FieldType;
import de.hybris.platform.solrfacetsearch.provider.Qualifier;
import de.hybris.platform.solrfacetsearch.provider.QualifierProvider;
import de.hybris.platform.solrfacetsearch.provider.QualifierProviderAware;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.impl.DefaultFieldNameTranslator;


/**
 * @author 1229803
 *
 */
public class DefaultSiteOneFieldNameTranslator extends DefaultFieldNameTranslator
{
	private static final String SESSION_STORE = "sessionStore";

	private SessionService sessionService;

	@Override
	protected String translateFromProperty(final SearchQuery searchQuery, final IndexedProperty indexedProperty,
			final FieldType fieldType)
	{
		final IndexedType indexedType = searchQuery.getIndexedType();
		String fieldQualifier = null;
		final String valueProviderId = getValueProviderSelectionStrategy().resolveValueProvider(indexedType, indexedProperty);
		final Object valueProvider = getValueProviderSelectionStrategy().getValueProvider(valueProviderId);
		final QualifierProvider qualifierProvider = valueProvider instanceof QualifierProviderAware
				? ((QualifierProviderAware) valueProvider).getQualifierProvider() : null;
		if (qualifierProvider != null && qualifierProvider.canApply(indexedProperty))
		{
			final Qualifier qualifier = qualifierProvider.getCurrentQualifier();
			fieldQualifier = qualifier != null ? qualifier.toFieldQualifier() : null;
		}
		else if (indexedProperty.isLocalized())
		{
			fieldQualifier = searchQuery.getLanguage();
		}
		else if (indexedProperty.isCurrency())
		{
			fieldQualifier = searchQuery.getCurrency();
			if (indexedProperty.getName().equalsIgnoreCase("price")
					&& null != ((PointOfServiceData) getSessionService().getAttribute(SESSION_STORE)))
			{
				fieldQualifier = ((PointOfServiceData) getSessionService().getAttribute(SESSION_STORE)).getStoreId().toLowerCase()
						+ "_" + searchQuery.getCurrency();
			}
		}
		else if (indexedProperty.getName().equalsIgnoreCase("priceLowSortValue")
				&& null != ((PointOfServiceData) getSessionService().getAttribute(SESSION_STORE)))
		{

			fieldQualifier = ((PointOfServiceData) getSessionService().getAttribute(SESSION_STORE)).getStoreId().toLowerCase();

		}
		else if (indexedProperty.getName().equals("isSellable"))
		{
			String storeId = null;
			if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
			{
				storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
				fieldQualifier = storeId.toLowerCase();
			}
		}
		return getFieldNameProvider().getFieldName(indexedProperty, fieldQualifier, fieldType);
	}


	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
}
