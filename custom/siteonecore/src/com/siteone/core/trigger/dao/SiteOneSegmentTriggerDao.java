package com.siteone.core.trigger.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.personalizationservices.dao.impl.AbstractCxDao;
import de.hybris.platform.personalizationservices.model.CxSegmentTriggerModel;
import de.hybris.platform.personalizationservices.model.CxVariationModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.model.SiteOneRegionSegmentModel;
import com.siteone.core.model.SiteOneTradeClassSegmentModel;


public class SiteOneSegmentTriggerDao extends AbstractCxDao<CxSegmentTriggerModel>
{
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	//Query to get the variations based on personalization rules

	public static final String FIND_VARIATIONS_QUERY = "SELECT {s2t.source}  FROM {CxSegmentToTrigger as s2t   JOIN "
			+ CxSegmentTriggerModel._TYPECODE + " as t on {t.pk} = {s2t.source}   JOIN " + CxVariationModel._TYPECODE
			+ " as v on {t.variation} = {v.pk} } where {s2t.target} in ({{select {pk} from {"
			+ SiteOneTradeClassSegmentModel._TYPECODE + "} where {" + SiteOneTradeClassSegmentModel.SUBTRADECLASSCODE
			+ "}=?subTradeClassCode }}) AND {v.catalogVersion} = ?catalogVersion  AND {t.catalogVersion} = ?catalogVersion";

	public static final String FIND_VARIATIONS_QUERY_ANONYMOUS = "SELECT {s2t.source}  FROM {CxSegmentToTrigger as s2t   JOIN "
			+ CxSegmentTriggerModel._TYPECODE + " as t on {t.pk} = {s2t.source}   JOIN " + CxVariationModel._TYPECODE
			+ " as v on {t.variation} = {v.pk} } where {s2t.target} in ({{select {pk} from {" + SiteOneRegionSegmentModel._TYPECODE
			+ "} where {" + SiteOneRegionSegmentModel.ISANONYMOUSCUSTOMER
			+ "}=?isAnonymousCustomer}}) AND {v.catalogVersion} = ?catalogVersion  AND {t.catalogVersion} = ?catalogVersion";

	public SiteOneSegmentTriggerDao()
	{
		super(CxSegmentTriggerModel._TYPECODE);
	}

	public Collection<CxVariationModel> findApplicableVariations(final CatalogVersionModel catalogVersion, final String region,
			final B2BCustomerModel customer)
	{
		ServicesUtil.validateParameterNotNull(catalogVersion, "catalogVersion must not be null");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("catalogVersion", catalogVersion);

		FlexibleSearchQuery query = null;

		if (customer != null && customer.getDefaultB2BUnit() != null)
		{
			final String subtradeClassCode = customer.getDefaultB2BUnit().getSubTradeClass();
			params.put("subTradeClassCode", subtradeClassCode != null ? subtradeClassCode : "");
			query = new FlexibleSearchQuery(FIND_VARIATIONS_QUERY, params);
		}
		else
		{
			params.put("isAnonymousCustomer", Boolean.TRUE);
			query = new FlexibleSearchQuery(FIND_VARIATIONS_QUERY_ANONYMOUS, params);
		}

		final List<CxSegmentTriggerModel> queryResult = getFlexibleSearchService().search(query).getResult().stream()
				.map(r -> (CxSegmentTriggerModel) r).collect(Collectors.toList());

		final List<CxVariationModel> variationList = new ArrayList<>();
		variationList.addAll(filterVariations(queryResult, region));
		variationList.addAll(findRegionBasedVariations(catalogVersion, region));

		return variationList;
	}

	public Collection<CxVariationModel> findRegionBasedVariations(final CatalogVersionModel catalogVersion, final String region)
	{
		final String FIND_COMMON_VARIATIONS = "SELECT {s2t.source}  FROM {CxSegmentToTrigger as s2t   JOIN "
				+ CxSegmentTriggerModel._TYPECODE + " as t on {t.pk} = {s2t.source}   JOIN " + CxVariationModel._TYPECODE
				+ " as v on {t.variation} = {v.pk} } where {s2t.target} in ({{select {pk} from {"
				+ SiteOneRegionSegmentModel._TYPECODE + "} where {" + SiteOneRegionSegmentModel.CHECKONLYREGION
				+ "}=?checkOnlyRegion}}) AND {v.catalogVersion} = ?catalogVersion  AND {t.catalogVersion} = ?catalogVersion";

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("catalogVersion", catalogVersion);
		params.put("checkOnlyRegion", Boolean.TRUE);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_COMMON_VARIATIONS, params);

		final PointOfServiceData sessionStore = sessionService.getAttribute("sessionStore");

		//SelectedRegion will be used for Agronomics Product Selector Tool use-case
		//where Anonymous user modifies his selected branch

		String selectedRegion = region;
		if (userFacade.isAnonymousUser() && sessionStore != null && StringUtils.isNotEmpty(sessionStore.getStoreId()))
		{
			selectedRegion = sessionStore.getStoreId();
		}
		final List<CxSegmentTriggerModel> queryResult = getFlexibleSearchService().search(query).getResult().stream()
				.map(r -> (CxSegmentTriggerModel) r).collect(Collectors.toList());

		return filterVariations(queryResult, selectedRegion);

	}

	public Collection<CxVariationModel> filterVariations(final List<CxSegmentTriggerModel> queryResult, final String region)
	{
		if (queryResult != null)
		{
			final List<CxVariationModel> list = new ArrayList<>();
			for (final CxSegmentTriggerModel model : queryResult)
			{
				if (region != null && model.getSegments().stream().anyMatch(r -> (r.getClass().equals(SiteOneRegionSegmentModel.class)
						&& Arrays.asList(((SiteOneRegionSegmentModel) r).getRegion().split(",")).contains(region))))
				{
					list.add(model.getVariation());
				}

			}
			return list;
		}
		return new ArrayList<>();
	}



}
