/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.platform.cmsfacades.cmsitems.attributevalidators;


import static de.hybris.platform.cmsfacades.common.validator.ValidationErrorBuilder.newValidationErrorBuilder;
import static de.hybris.platform.cmsfacades.constants.CmsfacadesConstants.INVALID_MEDIA_CODE_L10N;

import de.hybris.platform.cmsfacades.cmsitems.AttributeContentValidator;
import de.hybris.platform.cmsfacades.cmsitems.attributevalidators.BaseMediaAttributeContentValidator;
import de.hybris.platform.cmsfacades.languages.LanguageFacade;
import de.hybris.platform.cmsfacades.validator.data.ValidationError;
import de.hybris.platform.commercefacades.storesession.data.LanguageData;
import de.hybris.platform.core.model.type.AttributeDescriptorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Base Media attribute content validator adds validation errors when media formats are not present.
 */
public class SiteOneBaseMediaAttributeContentValidator extends BaseMediaAttributeContentValidator
{
	
	private LanguageFacade languageFacade;
	
	@Override
	public List<ValidationError> validate(final Map<String, String> value, final AttributeDescriptorModel attribute)
	{
		final List<ValidationError> errors = new ArrayList<>();
		// Since media formats is optional, no validation is made
		return errors;
	}



	protected LanguageFacade getLanguageFacade()
	{
		return languageFacade;
	}

	public void setLanguageFacade(final LanguageFacade languageFacade)
	{
		this.languageFacade = languageFacade;
	}
}
