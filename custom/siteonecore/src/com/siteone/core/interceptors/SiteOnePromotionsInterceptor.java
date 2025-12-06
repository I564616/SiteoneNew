/**
 *
 */
package com.siteone.core.interceptors;

import de.hybris.platform.promotionengineservices.model.RuleBasedPromotionModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.promotion.dao.SiteOnePromotionSourceRuleDao;


/**
 * @author 1124932
 *
 */
public class SiteOnePromotionsInterceptor implements PrepareInterceptor<RuleBasedPromotionModel>
{
	private final static Logger LOG = Logger.getLogger(SiteOnePromotionsInterceptor.class.getName());
	@Resource(name = "siteOnePromotionSourceRuleDao")
	private SiteOnePromotionSourceRuleDao siteOnePromotionSourceRuleDao;

	@Override
	public void onPrepare(final RuleBasedPromotionModel model, final InterceptorContext context) throws InterceptorException
	{
		LOG.info("Promotion interceptor");
		final RuleBasedPromotionModel promotion = siteOnePromotionSourceRuleDao.getPromotionByCode(model.getCode());
		if (null != promotion && promotion.getRuleVersion() != model.getRuleVersion())
		{
			if (null != promotion.getNotes())
			{
				model.setNotes(promotion.getNotes());
				LOG.info("Promotion notes:" + model.getNotes());
			}
			if (null != promotion.getImage())
			{
				model.setImage(promotion.getImage());
				LOG.info("Promotion image:" + model.getImage().getURL());
			}

			if (null != promotion.getName())
			{
				model.setName(promotion.getName());
			}


		}

	}

}


