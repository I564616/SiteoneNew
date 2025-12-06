package com.siteone.integration.jobs.promotion.dao.impl;

import java.util.List;

import com.siteone.integration.jobs.promotion.dao.PromotionFeedCronJobDao;

import de.hybris.platform.couponservices.model.SingleCodeCouponModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

public class DefaultPromotionFeedCronJobDao extends AbstractItemDao implements PromotionFeedCronJobDao {

	public List<RuleBasedPromotionModel> getPromotions() {
		
		String query = "Select {RBP:PK} " + " from {RuleBasedPromotion as RBP " + " JOIN AbstractRule as AR "
				+ " ON {RBP:title}={AR:code} " + " JOIN RuleStatus as RS " + " ON {RS:pk} = {AR:status}} " + " where "
				+ " {modifiedtime} > ({{ " + " Select max({lastExecutionTime}) " + " From {PromotionFeedCronJob as PFC "
				+ " JOIN CronJobStatus as CS " + " ON {CS:pk} = {PFC:status}} " + " }})  "
				+ " AND {modifiedtime} <= SYSDATETIME() " + " AND {ruleVersion} = ({{ " + " Select max({ruleVersion}) "
				+ " from {RuleBasedPromotion as RP} " + " where {RP:title}={RBP:title} " + " }}) "
				+ " AND {RS:code}='PUBLISHED'";
		
				
		return getFlexibleSearchService().<RuleBasedPromotionModel> search(query).getResult();			
	}
	
	public PromotionSourceRuleModel getPromotionSourceRule(String title){
		String queryString = "SELECT {" + Item.PK + "} " + "FROM   {" + "PromotionSourceRule" + "}" + " WHERE {code}= ?code";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", title);		
		List<PromotionSourceRuleModel> promotionSourceRuleModel= getFlexibleSearchService().<PromotionSourceRuleModel> search(query).getResult();
		if(null!=promotionSourceRuleModel && !promotionSourceRuleModel.isEmpty()){
			return promotionSourceRuleModel.get(0);
		}
		return null;	    

	}
	
	public SingleCodeCouponModel getCouponByCode(String title) {
		String queryString = "SELECT {" + Item.PK + "} " + "FROM   {" + "SingleCodeCoupon" + "}" + " WHERE {couponId}= ?code";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", title);	
		List<SingleCodeCouponModel> singleCodeCouponModel= getFlexibleSearchService().<SingleCodeCouponModel> search(query).getResult();
		if(null!=singleCodeCouponModel && !singleCodeCouponModel.isEmpty()){
			return singleCodeCouponModel.get(0);
		}
		return null;	    
		
	}

	

}
