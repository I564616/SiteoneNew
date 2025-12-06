/**
 *
 */
package com.siteone.core.promotion.dao;

import de.hybris.platform.commerceservices.promotion.dao.impl.DefaultCommercePromotionDao;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 1129929
 *
 */
public class DefaultPromotionRuleVersionDao extends DefaultCommercePromotionDao
{

	private final static String FIND_PROMOTION_FOR_CODE = "SELECT {" + RuleBasedPromotionModel.PK + "} FROM {"
			+ RuleBasedPromotionModel._TYPECODE + "} WHERE {" + RuleBasedPromotionModel.CODE + "} = ?code  AND {"
			+ RuleBasedPromotionModel.IMMUTABLEKEYHASH + "} is null  AND {" + RuleBasedPromotionModel.RULEVERSION
			+ "} = ({{ SELECT max ({ " + RuleBasedPromotionModel.RULEVERSION + " }) FROM {" + RuleBasedPromotionModel._TYPECODE
			+ "} WHERE {" + RuleBasedPromotionModel.CODE + "} = ?code }})";


	@Override
	public List<AbstractPromotionModel> findPromotionForCode(final String code)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		return doSearch(FIND_PROMOTION_FOR_CODE, params, AbstractPromotionModel.class);
	}

	@Override
	protected <T> List<T> doSearch(final String query, final Map<String, Object> params, final Class<T> resultClass)
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		if (params != null)
		{
			fQuery.addQueryParameters(params);
		}

		fQuery.setResultClassList(Collections.singletonList(resultClass));

		final SearchResult<T> searchResult = search(fQuery);
		return searchResult.getResult();
	}
}
