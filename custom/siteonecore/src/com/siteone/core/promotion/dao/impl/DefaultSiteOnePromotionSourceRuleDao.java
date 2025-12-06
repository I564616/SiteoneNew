package com.siteone.core.promotion.dao.impl;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.PromotionProductCategoryModel;
import com.siteone.core.promotion.dao.SiteOnePromotionSourceRuleDao;


public class DefaultSiteOnePromotionSourceRuleDao extends AbstractItemDao implements SiteOnePromotionSourceRuleDao
{

	@Override
	public PromotionSourceRuleModel getPromotionSourceRuleByCode(final String code)
	{
		final String queryString = "SELECT {" + Item.PK + "} " + "FROM   {" + "PromotionSourceRule" + "}" + " WHERE {code}= ?code";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);
		final List<PromotionSourceRuleModel> promotionSourceRuleList = getFlexibleSearchService()
				.<PromotionSourceRuleModel> search(query).getResult();
		if (CollectionUtils.isNotEmpty(promotionSourceRuleList))
		{
			return promotionSourceRuleList.get(0);
		}
		return null;
	}

	@Override
	public RuleBasedPromotionModel getPromotionByCode(final String code)
	{
		final String queryString = "SELECT {pk} from {rulebasedpromotion} where {code} = ?code and {ruleversion} in ({{select max({ruleversion}) from {rulebasedpromotion} where {code} = ?code}})";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);
		final List<RuleBasedPromotionModel> result = getFlexibleSearchService().<RuleBasedPromotionModel> search(query).getResult();
		if (CollectionUtils.isNotEmpty(result))
		{
			return result.get(0);
		}
		return null;
	}

	@Override
	public RuleBasedPromotionModel getPromotionForPreviousRule(final String title)
	{
		final String queryString = "SELECT {pk} from {rulebasedpromotion} where {title}= ?title  and {ruleversion} in({{select max({ruleversion}) from {rulebasedpromotion} where {title} = ?title  and  {ruleversion} not in ({{select max({ruleversion}) from {rulebasedpromotion} where {title} = ?title  }})}})";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("title", title);
		final List<RuleBasedPromotionModel> promotionSourceRuleList = getFlexibleSearchService()
				.<RuleBasedPromotionModel> search(query).getResult();
		if (CollectionUtils.isNotEmpty(promotionSourceRuleList))
		{
			return promotionSourceRuleList.get(0);
		}
		return null;
	}


	@Override
	public PromotionProductCategoryModel findPromotionCategoryForCode(final String code)
	{
		final String queryString = "SELECT {" + Item.PK + "} " //
				+ "FROM {" + PromotionProductCategoryModel._TYPECODE + "} " //
				+ "WHERE {" + PromotionProductCategoryModel.CODE + "}=?code";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);
		final SearchResult<PromotionProductCategoryModel> result = getFlexibleSearchService().search(query);
		final int resultCount = result.getTotalCount();
		if (resultCount == 0)
		{
			return null;
		}
		else
		{
			return result.getResult().get(0);
		}
	}











}
