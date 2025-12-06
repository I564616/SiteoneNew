/**
 *
 */
package com.siteone.core.promotion.service;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.promotion.dao.CommercePromotionDao;
import de.hybris.platform.commerceservices.promotion.impl.DefaultCommercePromotionService;
import de.hybris.platform.promotions.model.AbstractPromotionModel;

import java.util.List;


/**
 * @author 1129929
 *
 */
public class DefaultPromotionRuleVersionService extends DefaultCommercePromotionService
{
	private CommercePromotionDao commercePromotionDao;

	@Override
	public AbstractPromotionModel getPromotion(final String code)
	{
		validateParameterNotNull(code, "Parameter code must not be null");
		final List<AbstractPromotionModel> promotions = getCommercePromotionDao().findPromotionForCode(code);
		return promotions.get(0);
	}

	@Override
	public CommercePromotionDao getCommercePromotionDao()
	{
		return commercePromotionDao;
	}

	@Override
	public void setCommercePromotionDao(final CommercePromotionDao commercePromotionDao)
	{
		this.commercePromotionDao = commercePromotionDao;
	}


}
