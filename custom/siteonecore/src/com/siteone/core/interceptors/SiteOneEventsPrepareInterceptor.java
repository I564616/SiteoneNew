package com.siteone.core.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;

import com.siteone.core.model.SiteOneEventModel;


public class SiteOneEventsPrepareInterceptor implements PrepareInterceptor
{

	private ModelService modelService;
	private KeyGenerator keyGenerator;

	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model instanceof SiteOneEventModel)
		{
			final SiteOneEventModel siteOneEvent = (SiteOneEventModel) model;
			if (modelService.isNew(siteOneEvent))
			{
				final String code = (String) keyGenerator.generate();
				siteOneEvent.setCode(code);
				siteOneEvent.setTitle("event");
			}
		}
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public void setKeyGenerator(final KeyGenerator keyGenerator)
	{
		this.keyGenerator = keyGenerator;
	}


}
