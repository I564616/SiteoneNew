/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.platform.cmsfacades.cmsitems.validator;

import static de.hybris.platform.cmsfacades.common.validator.ValidationErrorBuilder.newValidationErrorBuilder;
import static de.hybris.platform.cmsfacades.constants.CmsfacadesConstants.FIELD_REQUIRED_L10N;
import static java.util.Objects.isNull;

import de.hybris.platform.cms2lib.model.components.BannerComponentModel;
import de.hybris.platform.cmsfacades.common.function.Validator;
import de.hybris.platform.cmsfacades.common.validator.ValidationErrorsProvider;
import de.hybris.platform.cmsfacades.cmsitems.validator.DefaultBannerComponentValidator;
import de.hybris.platform.cmsfacades.languages.LanguageFacade;
import de.hybris.platform.commercefacades.storesession.data.LanguageData;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import java.util.function.Function;


/**
 * Default implementation of the validator for {@link BannerComponentModel}
 */
public class SiteOneDefaultBannerComponentValidator extends DefaultBannerComponentValidator
{

	private ValidationErrorsProvider validationErrorsProvider;
	private LanguageFacade languageFacade;
	private CommonI18NService commonI18NService;

	@Override
	public void validate(final BannerComponentModel validatee)
	{
		validateField((languageData) -> validatee.getContent(getCommonI18NService().getLocaleForIsoCode(languageData.getIsocode())),
				BannerComponentModel.CONTENT);

		validateField(
				(languageData) -> validatee.getHeadline(getCommonI18NService().getLocaleForIsoCode(languageData.getIsocode())),
				BannerComponentModel.HEADLINE);
	}

	/**
	 * Validates a field using the getter function
	 *
	 * @param value
	 *           the getter function by language Data
	 * @param field
	 *           the field being validated
	 */
	protected void validateField(final Function<LanguageData, Object> value, final String field)
	{
		getLanguageFacade().getLanguages().stream() //
				.filter(LanguageData::isRequired) //
				.forEach(languageData -> {
					if (isNull(value.apply(languageData)))
					{
						getValidationErrorsProvider().getCurrentValidationErrors().add(newValidationErrorBuilder() //
								.field(field) //
								.language(languageData.getIsocode()).errorCode(FIELD_REQUIRED_L10N) //
								.errorArgs(new Object[]
						{ languageData.getIsocode() }) //
								.build());
					}
				});
	}

	protected ValidationErrorsProvider getValidationErrorsProvider()
	{
		return validationErrorsProvider;
	}

	public void setValidationErrorsProvider(final ValidationErrorsProvider validationErrorsProvider)
	{
		this.validationErrorsProvider = validationErrorsProvider;
	}

	protected LanguageFacade getLanguageFacade()
	{
		return languageFacade;
	}

	public void setLanguageFacade(final LanguageFacade languageFacade)
	{
		this.languageFacade = languageFacade;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
}
