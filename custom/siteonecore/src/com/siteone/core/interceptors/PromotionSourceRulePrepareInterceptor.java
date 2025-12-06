/**
 *
 */
package com.siteone.core.interceptors;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author 1085284
 *
 */
public class PromotionSourceRulePrepareInterceptor implements PrepareInterceptor<PromotionSourceRuleModel>
{
	private final static Logger LOG = Logger.getLogger(PromotionSourceRulePrepareInterceptor.class.getName());

	@Override
	public void onPrepare(final PromotionSourceRuleModel model, final InterceptorContext context)
	{

		LOG.info("Promotion Source Rule Prepare interceptor");

		if (StringUtils.isBlank(model.getCouponCode()))
		{
			model.setCouponCode(model.getCode());
		}

	}
}
