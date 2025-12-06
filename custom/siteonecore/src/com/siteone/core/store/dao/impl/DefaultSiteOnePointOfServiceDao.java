/**
 *
 */
package com.siteone.core.store.dao.impl;


import de.hybris.platform.commerceservices.model.storelocator.StoreLocatorFeatureModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.exception.GeoLocatorException;
import de.hybris.platform.storelocator.exception.PointOfServiceDaoException;
import de.hybris.platform.storelocator.impl.DefaultPointOfServiceDao;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.core.store.dao.SiteOnePointOfServiceDao;


/**
 * @author 965504
 *
 */
public class DefaultSiteOnePointOfServiceDao extends DefaultPointOfServiceDao implements SiteOnePointOfServiceDao
{

	private static final String  STORE_SPECIALTY_RELATION = " JOIN storelocation2storelocatorfeature  AS relation ON {relation.source}  = {p.pk} "
			+ "JOIN storelocatorfeature AS sp ON {sp.pk}  = {relation.target}";
	private static final String  STORE_SPECIALTY_INPUT = " AND {sp.name} IN (?storeSpecialities)";
	private static final String  LATITUDE = "latitude";
	private static final String  LONGITUDE = "longitude";
	private static final String IS_DCBRANCH = "?isDCBranch";

	@Override
	public PointOfServiceModel getStoreForId(final String storeId)
	{

		final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
				+ " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.STOREID + "}=?storeId AND {p:" + PointOfServiceModel.ISACTIVE
				+ "}=?isActive";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("storeId", storeId);
		query.addQueryParameter("isActive", Boolean.TRUE);
		final List<PointOfServiceModel> results = getFlexibleSearchService().<PointOfServiceModel> search(query).getResult();

		if (CollectionUtils.isNotEmpty(results))
		{
			return results.get(0);
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public List<PointOfServiceModel> getStoreForMetroStatArea(final String metroStatLocation)
	{
		List<PointOfServiceModel> results = null;
		if (StringUtils.isNotEmpty(metroStatLocation))
		{
			final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
					+ " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.METROSTATAREA + "}=?metroStatArea AND {p:"
					+ PointOfServiceModel.ISACTIVE + "}=?isActive AND {p:" + PointOfServiceModel.ISDCBRANCH +"} = " + IS_DCBRANCH;

			final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
			query.addQueryParameter("metroStatArea", metroStatLocation);
			query.addQueryParameter("isActive", Boolean.TRUE);
			query.addQueryParameter("isDCBranch", Boolean.FALSE);
			results = getFlexibleSearchService().<PointOfServiceModel> search(query).getResult();
			return results;
		}
		return null;

	}

	@Override
	public FlexibleSearchQuery buildQuery(final GPS center, final double radius, final BaseStoreModel baseStore)
			throws PointOfServiceDaoException
	{
		return buildQueryWithStoreSpecialities(center, radius, baseStore, null);
	}


	@Override
	public List<PointOfServiceModel> getPointOfServiceForCityAndState(final String city, final String state)
	{



		final String query = "SELECT {" + PointOfServiceModel.PK + "}" + " FROM {" + PointOfServiceModel._TYPECODE + " AS p"
				+ " JOIN " + AddressModel._TYPECODE + " AS a  " + "ON {p:" + PointOfServiceModel.ADDRESS + "} = {a:" + AddressModel.PK
				+ "}" + " JOIN " + RegionModel._TYPECODE + " AS r " + "ON {a:" + AddressModel.REGION + "} = {r:" + RegionModel.PK
				+ "}}" + " WHERE {a:" + AddressModel.TOWN + "} = ?city AND {r:" + RegionModel.ISOCODE + "} = ?state  " + " AND {p:"
				+ PointOfServiceModel.ISACTIVE + "}= ?isActive " +" AND {p:"+ PointOfServiceModel.ISDCBRANCH + "}=  " + IS_DCBRANCH 
				+ " ORDER BY  {p:" + PointOfServiceModel.NAME + "} ASC ";


		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("city", city);
		fQuery.addQueryParameter("state", state);
		fQuery.addQueryParameter("isActive", Boolean.TRUE);
		fQuery.addQueryParameter("isDCBranch", Boolean.FALSE);
		return getFlexibleSearchService().<PointOfServiceModel> search(fQuery).getResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.store.dao.SiteOnePointOfServiceDao#getPointOfServiceForState(java.lang.String)
	 */
	@Override
	public List<PointOfServiceModel> getPointOfServiceForState(final String state, final List<String> storeSpecialities)
	{
		final String query = "SELECT DISTINCT {p:" + PointOfServiceModel.PK + "}" + " FROM {" + PointOfServiceModel._TYPECODE + " AS p"
				+ " JOIN " + AddressModel._TYPECODE + " AS a  " + "ON {p:" + PointOfServiceModel.ADDRESS + "} = {a:" + AddressModel.PK
				+ "}" + " JOIN " + RegionModel._TYPECODE + " AS r " + "ON {a:" + AddressModel.REGION + "} = {r:" + RegionModel.PK+"}";
		
		final String whereClause = "} WHERE (UPPER({r:" + RegionModel.NAME + "}) like UPPER(?state)   OR UPPER({r:" + RegionModel.ISOCODESHORT
				+ "}) like UPPER(?state))  " + " AND {p:" + PointOfServiceModel.ISACTIVE + "}= ?isActive "
				+ " AND {p:" + PointOfServiceModel.ISDCBRANCH + "}= " + IS_DCBRANCH;
		
		FlexibleSearchQuery fQuery = null;
		if(CollectionUtils.isEmpty(storeSpecialities)){
			fQuery = new FlexibleSearchQuery(createQuery(query, whereClause));
		}else{
			fQuery = new FlexibleSearchQuery(createQuery(query,STORE_SPECIALTY_RELATION, whereClause,STORE_SPECIALTY_INPUT));
			fQuery.addQueryParameter("storeSpecialities", storeSpecialities);
		}
		fQuery.addQueryParameter("state", state);
		fQuery.addQueryParameter("isActive", Boolean.TRUE);
		fQuery.addQueryParameter("isDCBranch", Boolean.FALSE);
		
		return getFlexibleSearchService().<PointOfServiceModel> search(fQuery).getResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.store.dao.SiteOnePointOfServiceDao#getStoreCitiesForState(java.lang.String)
	 */
	@Override
	public List<String> getStoreCitiesForState(final String state)
	{
		// YTODO Auto-generated method stub
		final String query = "Select {a.town} from {" + AddressModel._TYPECODE + " as a join " + PointOfServiceModel._TYPECODE
				+ " as p on {p:" + PointOfServiceModel.ADDRESS + "} = {a:" + AddressModel.PK + "} join " + RegionModel._TYPECODE
				+ " as r on {r:" + RegionModel.PK + "} = {a:" + AddressModel.REGION + "}} where {r:" + RegionModel.ISOCODE
				+ "} = ?state" + " AND {p:" + PointOfServiceModel.ISACTIVE + "}= ?isActive "
				+ " AND {p:" + PointOfServiceModel.ISDCBRANCH + "}= " + IS_DCBRANCH;

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("state", state);
		fQuery.addQueryParameter("isActive", Boolean.TRUE);
		fQuery.addQueryParameter("isDCBranch", Boolean.FALSE);
		fQuery.setResultClassList(Collections.singletonList(String.class));
		return getFlexibleSearchService().<String> search(fQuery).getResult();
	}

	@Override
	public List<String> findStoreRegions()
	{
		final String queryString = "Select distinct {r:" + RegionModel.ISOCODE + "} , {p:" + PointOfServiceModel.NAME + "} "
				+ "from {" + PointOfServiceModel._TYPECODE + " As p JOIN " + AddressModel._TYPECODE + " as a " + "on {p:"
				+ PointOfServiceModel.ADDRESS + "} = {a:" + AddressModel.PK + "} JOIN " + RegionModel._TYPECODE + " as r " + "ON {a:"
				+ AddressModel.REGION + "} = {r." + RegionModel.PK + "} } WHERE {p:" + PointOfServiceModel.ISACTIVE + "}= ?isActive "
				+ " AND {p:" + PointOfServiceModel.ISDCBRANCH + "}= " + IS_DCBRANCH;

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("isActive", Boolean.TRUE);
		query.addQueryParameter("isDCBranch", Boolean.FALSE);
		query.setResultClassList(Arrays.asList(String.class, String.class));
		final SearchResult<List> result = getFlexibleSearchService().search(query);

		final List<String> resultList = new ArrayList<String>();

		for (final List row : result.getResult())
		{
			resultList.add((String) row.get(0));

		}
		return resultList;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.store.dao.SiteOnePointOfServiceDao#getAllActivePos()
	 */
	@Override
	public Collection<PointOfServiceModel> getAllActivePos()
	{

		final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
				+ " AS p} " + "WHERE " + " {p:" + PointOfServiceModel.ISACTIVE + "}=?isActive"
				+ " AND {p:" + PointOfServiceModel.ISDCBRANCH + "}= " + IS_DCBRANCH;
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter("isActive", Boolean.TRUE);
		fQuery.addQueryParameter("isDCBranch", Boolean.FALSE);
		final SearchResult result = this.search(fQuery);
		return result.getResult();
	}

	@Override
	public List<PointOfServiceModel> getListOfPointOfService(final List<String> storeIds)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {pointofservice} where {isActive}=?isActive and {storeId} IN (?storeIds) and {isDCBranch}=?isDCBranch");
		query.addQueryParameter("isActive", Boolean.TRUE);
		query.addQueryParameter("storeIds", storeIds);
		query.addQueryParameter("isDCBranch", Boolean.FALSE);
		final SearchResult<PointOfServiceModel> result = getFlexibleSearchService().search(query);
		return result.getResult();

	}
	
	@Override
	public List<StoreLocatorFeatureModel> getStoreSpecialtyDetails(){
		final String FETCH_STORE_FEATURE_QUERY = "SELECT { " + StoreLocatorFeatureModel.PK + "} FROM {" + StoreLocatorFeatureModel._TYPECODE+ " } " ;
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FETCH_STORE_FEATURE_QUERY);
		final SearchResult<StoreLocatorFeatureModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
	
	
	public FlexibleSearchQuery buildQueryWithStoreSpecialities(final GPS center, final double radius, final BaseStoreModel baseStore,
			final List<String> storeSpecialities) throws PointOfServiceDaoException
	{
		try
		{
			final double radiusKms = 1.60934 * radius;
			final List e = GeometryUtils.getSquareOfTolerance(center, radiusKms);
			if (e != null && !e.isEmpty() && e.size() == 2)
			{
				final Double latMax = Double.valueOf(((GPS) e.get(1)).getDecimalLatitude());
				final Double lonMax = Double.valueOf(((GPS) e.get(1)).getDecimalLongitude());
				final Double latMin = Double.valueOf(((GPS) e.get(0)).getDecimalLatitude());
				final Double lonMin = Double.valueOf(((GPS) e.get(0)).getDecimalLongitude());

				
					String query = "SELECT DISTINCT { p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE+" AS p ";
					
					StringBuilder	whereClause = new StringBuilder();
					whereClause.append(" } WHERE {").append(LATITUDE)
						.append("} is not null AND {").append(LONGITUDE).append("} is not null AND {").append(LATITUDE)
						.append("} >= ?latMin AND {").append(LATITUDE).append("} <= ?latMax AND {").append(LONGITUDE)
						.append("} >= ?lonMin AND {").append(LONGITUDE).append("} <= ?lonMax AND {").append("isActive")
						.append("} = ?isActive AND {").append("isDCBranch").append("} = ?isDCBranch");

				if (baseStore != null)
				{
					whereClause.append(" AND {").append("baseStore").append("} = ?baseStore");
					
				}

				FlexibleSearchQuery fQuery = null;
				if(CollectionUtils.isEmpty(storeSpecialities)){
					fQuery = new FlexibleSearchQuery(createQuery(query, whereClause.toString()));
				}else{
					fQuery = new FlexibleSearchQuery(createQuery(query,STORE_SPECIALTY_RELATION, whereClause.toString(),STORE_SPECIALTY_INPUT));
					fQuery.addQueryParameter("storeSpecialities", storeSpecialities);
				}
				fQuery.addQueryParameter("latMax", latMax);
				fQuery.addQueryParameter("latMin", latMin);
				fQuery.addQueryParameter("lonMax", lonMax);
				fQuery.addQueryParameter("lonMin", lonMin);
				
				if (baseStore != null)
				{
					fQuery.addQueryParameter("baseStore", baseStore);
				}
				fQuery.addQueryParameter("isActive", Boolean.TRUE);
				fQuery.addQueryParameter("isDCBranch", Boolean.FALSE);

				return fQuery;
			}
			else
			{
				throw new PointOfServiceDaoException("Could not fetch locations from database. Unexpected neighborhood");
			}
		}
		catch (final GeoLocatorException arg11)
		{
			throw new PointOfServiceDaoException("Could not fetch locations from database, due to :" + arg11.getMessage(), arg11);
		}
	}
	
	
	@Override
	public Collection<PointOfServiceModel> getAllGeocodedPOSWithStoreSpecialities(final GPS center, final double radius, final BaseStoreModel baseStore,
			final List<String> storeSpecialities)
	{
		final FlexibleSearchQuery fQuery = buildQueryWithStoreSpecialities(center, radius, baseStore, storeSpecialities);
		final SearchResult<PointOfServiceModel> result = this.search(fQuery);
		return result.getResult();
	}
	
	protected String createQuery(final String... queryClauses)
	{
		final StringBuilder queryBuilder = new StringBuilder();

		for (final String queryClause : queryClauses)
		{
			queryBuilder.append(queryClause);
		}

		return queryBuilder.toString();
	}
	

	@Override
	public PointOfServiceModel getStoreDetailForId(final String storeId)
	{
		
		final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
				+ " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.STOREID + "}=?storeId AND {p:" + PointOfServiceModel.ISACTIVE
				+ "}=?isActive AND {p:" + PointOfServiceModel.ISDCBRANCH +"} = " + IS_DCBRANCH;

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("storeId", storeId);
		query.addQueryParameter("isActive", Boolean.TRUE);
		query.addQueryParameter("isDCBranch", Boolean.FALSE);
		final List<PointOfServiceModel> results = getFlexibleSearchService().<PointOfServiceModel> search(query).getResult();

		if (CollectionUtils.isNotEmpty(results))
		{
			return results.get(0);
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public List<PointOfServiceModel> getNurseryNearbyBranches(final String nbGroup)
	{
		
		final String queryString = "SELECT {p:" + PointOfServiceModel.PK + "}" + "FROM {" + PointOfServiceModel._TYPECODE
				+ " AS p} " + "WHERE " + "{p:" + PointOfServiceModel.NURSERYBUYINGGROUP + "}=?nbGroup AND {p:" + PointOfServiceModel.ISACTIVE
				+ "}=?isActive AND {p:" + PointOfServiceModel.ISDCBRANCH +"} = " + IS_DCBRANCH;

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("nbGroup", nbGroup);
		query.addQueryParameter("isActive", Boolean.TRUE);
		query.addQueryParameter("isDCBranch", Boolean.FALSE);
		return getFlexibleSearchService().<PointOfServiceModel> search(query).getResult();
	}

}
